package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y){
        // TODO: Implement the logic here
        if ((x == null) || (y == null)) throw new IllegalArgumentException(); //check private cases
        if (x.size() != 0 && y.size() == 0) return false; //check private cases
        int lastFoundElementIndex = -1;
        for (int i = 0; i < x.size(); i++) {
            for (int j = ++lastFoundElementIndex; j < y.size(); j++ ) {
                if (y.contains(x.get(i)) && (y.indexOf(x.get(i)) >= lastFoundElementIndex)) {
                    lastFoundElementIndex = y.indexOf(x.get(i));
                    continue;
                } else return false;
            }
        }
        return true;

    }
}
