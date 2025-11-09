package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.List;


import it.unibo.functional.Transformers;
import it.unibo.functional.api.Function;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }

    
    private enum Month{
        January(31),
        February(28),
        March(31),
        April(30),
        May(31),
        June(30),
        July(31),
        August(31),
        September(30),
        October(31),
        November(30),
        December(31);

        private final int days;

        private Month(int days) {
            this.days = days;
        }

        public int getDays(){
            return this.days;
        }

        public static Month fromString(String month){
            List<Month> list = Transformers.select(List.of(Month.values()), new Function<Month,Boolean>() {

                @Override
                public Boolean call(Month input) {
                    return input.toString().toLowerCase().startsWith(month.toLowerCase());
                }
                
            });
            if (list.size() != 1) {
                if (list.size() > 1) {
                    throw new IllegalArgumentException("Ambigous month");
                }else{
                    throw new IllegalArgumentException("No month with such name");
                }
            }
            return list.getFirst();
        }
    }

    private class SortByMonthOrder implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            Month m1 = Month.fromString(o1);
            Month m2 = Month.fromString(o2);
            return m1.compareTo(m2);
        }
    
    }

    private class SortByDate implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            Month m1 = Month.fromString(o1);
            Month m2 = Month.fromString(o2);
            if (m1.getDays() < m2.getDays()) {
                return -1;
            } else if (m1.days > m2.days){
                return 1;
            }
            return 0;
        }

    }
}
