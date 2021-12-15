package com.company.day15;

import com.company.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> inputString = Util.loadFile("src/com/company/day15/input.txt");
        List<String[]> temp = inputString.stream().map(string -> string.split("")).collect(Collectors.toList());

        Integer[][] floorMap = new Integer[temp.size()*5][temp.get(0).length*5];

        Integer[][] costToSpot = new Integer[temp.size()*5][temp.get(0).length*5];


        for(int modifierY = 0; modifierY < 5; modifierY++ ) {
            for(int modifierX = 0; modifierX < 5; modifierX++) {
                for(int i = 0; i < temp.size(); i++){
                    for(int j = 0; j < temp.get(0).length; j++ ) {
                        int value = (Integer.parseInt(temp.get(i)[j]) + modifierX + modifierY);
                        while(value > 9) {
                            value -= 9;
                        }

                        floorMap[i + modifierY*temp.size()][j+modifierX*temp.get(0).length] = value;
                    }
                }
            }
        }



        costToSpot[costToSpot.length - 1][costToSpot[0].length - 1] = floorMap[costToSpot.length - 1][costToSpot[0].length - 1];

        boolean changed = true;

        while(changed) {
            changed = false;

            for (int i = costToSpot.length - 1; i >= 0; i--) {
                for (int j = costToSpot[0].length - 1; j >= 0; j--) {

                    List<Integer> adjacentCosts = new ArrayList<>();

                    if (i > 0) {
                        adjacentCosts.add(costToSpot[i - 1][j]);
                    }
                    if (i < costToSpot.length - 1) {
                        adjacentCosts.add(costToSpot[i + 1][j]);
                    }
                    if (j > 0) {
                        adjacentCosts.add(costToSpot[i][j - 1]);
                    }
                    if (j < costToSpot[0].length - 1) {
                        adjacentCosts.add(costToSpot[i][j + 1]);
                    }

                    Integer minAdjacentCost = adjacentCosts.stream().filter(Objects::nonNull).min(Comparator.naturalOrder()).orElse(floorMap[costToSpot.length - 1][costToSpot[0].length - 1]);

                    if (costToSpot[i][j] == null || minAdjacentCost + floorMap[i][j] < costToSpot[i][j]) {
                        changed = true;
                        costToSpot[i][j] = minAdjacentCost + floorMap[i][j];
                    }
                }
            }
        }

        System.out.println("Result 1: " + (costToSpot[0][0] - floorMap[0][0]));

    }
}
