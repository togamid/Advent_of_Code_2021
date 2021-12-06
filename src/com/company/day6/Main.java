package com.company.day6;

import com.company.Util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> inputString = Util.loadFile("src/com/company/day6/input.txt");
        List<Integer> input = Util.parseInt(inputString.get(0).split(","));

        Map<Integer, Long> fish = input.stream().collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));

        for(int i = 0; i <256; i++) {
            for(int j = 0; j <10; j++) {
                fish.put(9, 0L);
                fish.put(j-1, fish.get(j));
            }
            if(fish.get(-1) != null && fish.get(-1) != 0 ) {
                fish.put(8, fish.get(-1));
                fish.put(6, (fish.get(6) == null ? 0:fish.get(6)) + (fish.get(-1) == null ? 0:fish.get(-1)));
                fish.put(-1, 0L);
            }
        }

        System.out.println("Result: " + (Long) fish.values().stream().mapToLong(aLong -> aLong).sum());

        // Einfache/schönere Lösung
        long[] fishArray = new long[9];
        input.forEach(integer ->  fishArray[integer]++);

        for(int i = 0; i < 256; i++) {
            long buffer = fishArray[0];
            for(int j = 1; j<fishArray.length; j++) {
                fishArray[j-1] = fishArray[j];
            }
            fishArray[6] += buffer;
            fishArray[8] = buffer;
        }

        System.out.println(Arrays.stream(fishArray).sum());
    }
}
