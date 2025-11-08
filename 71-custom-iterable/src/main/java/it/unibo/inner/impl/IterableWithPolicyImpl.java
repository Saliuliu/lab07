package it.unibo.inner.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    private final T[] elements;
    private Predicate<T> predicate;

    public IterableWithPolicyImpl(T[] elements){
        this(elements, new Predicate<T>() {
            @Override
            public boolean test(T elem) {
                return true;
            }
            
        });
    }

    public IterableWithPolicyImpl(T[] elements, Predicate<T> filter){
        this.elements = elements;
        setIterationPolicy(filter);
    }

    @Override
    public Iterator<T> iterator() {
        return new PolicyIterator();
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.predicate = filter;
    }


    private class PolicyIterator implements Iterator<T>{
        private int current;

        private PolicyIterator(){
            current = 0;
        }
        @Override
        public boolean hasNext() {
            if (current >= elements.length) {
                return false;
            }
            if (predicate.test(elements[current])) {
                return true;
            }
            current++;
            return hasNext();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T n = elements[current];

            current++;
            return n;
        }
        
    }
    
}
