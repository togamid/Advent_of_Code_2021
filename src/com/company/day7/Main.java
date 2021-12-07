package com.company.day7;

import com.company.Util;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
    List<String> inputString = Util.loadFile("src/com/company/day7/input.txt");
    List<Integer> input = Util.parseInt(inputString.get(0).split(","));

    input.sort(Comparator.naturalOrder());

    Integer median = input.get(input.size() / 2);
    System.out.println(input.size());

    int result = input.stream().map(integer -> Math.abs(integer - median)).mapToInt(Integer::intValue).sum();
    System.out.println(median);

    System.out.println("Result 1: " + result);

    // unschöne Lösung Teil 2
    int optimalSum = Integer.MAX_VALUE;
    int optimumSpot = -1;
    for(int i = input.get(0); i <= input.get(input.size()-1); i++ ) {
        int sum = 0;
        for(Integer integer : input) {
            int distance = Math.abs(integer - i);
            sum += (distance)*(distance + 1) / 2;
        }
        if(sum < optimalSum) {
            optimalSum = sum;
            optimumSpot = i;
        }
    }

    System.out.println("optimumSpot: " + optimumSpot);
    System.out.println("Result 2: " + optimalSum);
    }
}
