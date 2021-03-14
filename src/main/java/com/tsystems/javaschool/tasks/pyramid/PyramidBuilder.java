package com.tsystems.javaschool.tasks.pyramid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    public static int pyromidLength; //max array length

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid (List<Integer> inputNumbers) throws CannotBuildPyramidException{
        // TODO : Implement your solution here
        if (checkIfValuable(inputNumbers)) {
            Collections.sort(inputNumbers);
            List<ArrayList<Integer>> full = getArrays(inputNumbers);
            for (ArrayList<Integer> arr : full) {
                fillWithZeros(arr, pyromidLength);
            }
            return(backToArray(full));
        }
        else throw new CannotBuildPyramidException();
    }


    public static boolean checkIfValuable(List<Integer> list) {
        if (list.contains(null)) return false;
        int symbolsLeft = list.size();
        for (int i = 1;; i++) {
            symbolsLeft -= i;
            if (symbolsLeft == 0) return true;
            if (symbolsLeft < 0) return false;
        }
    }

    //Gets arraylists of 1, 2, 3... elements and put zero between elements
    public static List<ArrayList<Integer>> getArrays(List<Integer> list){
        int symbolsAdded = 0;
        List<ArrayList<Integer>> fullArray = new ArrayList<>();
        for (int i = 1; symbolsAdded < list.size() ; i++) {
            fullArray.add(new ArrayList<>());
            for (int j = 0; j < i; j++) {
                fullArray.get(i - 1).add(list.get(symbolsAdded));
                if ((j + 1) != i)fullArray.get(i - 1).add(0);
                symbolsAdded++;
            }
            pyromidLength = fullArray.get(i - 1).size();
        }
        return fullArray;
    }

    //fills arrays with zeros to make rectangular shape of 2D array result
    public static void fillWithZeros(ArrayList<Integer> list, int maxLength) {
        int zerosToAdd = maxLength - list.size();
        for (int i = 0; i < zerosToAdd / 2; i++) {
            list.add(0, 0);
            list.add(0);
        }
    }

    //creates 2D array out of list of lists
    public static int[][] backToArray(List<ArrayList<Integer>> fullList) {
        int [][] fullArray = new int[fullList.size()] [fullList.get(0).size()];
        for (int i = 0; i < fullList.size(); i++) {
            for(int j = 0; j < fullList.get(i).size(); j++) {
                fullArray[i][j] = fullList.get(i).get(j);
            }
        }
        return fullArray;
    }

}
