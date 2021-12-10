package com.company.day9;

import com.company.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> inputString = Util.loadFile("src/com/company/day9/input.txt");
        Integer[][] map = new Integer[inputString.size()][inputString.get(0).length()];

        for (int i = 0; i < map.length; i++) {
            map[i] = Util.parseArrayInt(inputString.get(i).split(""));
        }

        int result1 = 0;
        int id = 1;
        Integer[][] basins = new Integer[inputString.size()][inputString.get(0).length()];


        for(int y = 0; y < map.length; y ++) {
            for(int x = 0; x < map[0].length; x++) {
                if(y-1 >= 0 && map[y-1][x] <= map[y][x]) {
                    continue;
                }
                if(y+1 <map.length && map[y+1][x] <= map[y][x]){
                    continue;
                }
                if(x+1 <map[0].length && map[y][x+1] <= map[y][x]){
                    continue;
                }
                if(x-1 >=0 && map[y][x-1] <= map[y][x]){
                    continue;
                }
                markBasins(x, y, id, map, basins);
                result1 += map[y][x] +1;
                id++;
            }
        }

        output(basins);

        List<Long> list = Arrays.stream(basins).flatMap(array -> Arrays.stream(array)).filter(Objects::nonNull).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values().stream().sorted().collect(Collectors.toList());

        System.out.println("Result 1: " + result1);

        System.out.println("Result 2: " + list.get(list.size()-1) * list.get(list.size()-2) * list.get(list.size()-3));
    }
    private static void output(Integer[][] array){
        for(int y = 0; y < array.length; y ++) {
            for(int x = 0; x < array[0].length; x++) {
                System.out.print(array[y][x]);
            }
            System.out.println();
        }
    }

    private static void markBasins(int x, int y, int id, Integer[][] map, Integer[][] basins) {
        if(map[y][x] >= 9) {
            return;
        }
        basins[y][x] = id;
        if(y-1 >= 0 && basins[y-1][x] == null) {
            markBasins(x, y-1, id, map, basins);
        }
        if(y+1 <map.length && basins[y+1][x] == null){
            markBasins(x, y+1, id, map, basins);
        }
        if(x+1 <map[0].length && basins[y][x+1] == null){
            markBasins(x+1, y, id, map, basins);
        }
        if(x-1 >=0 && basins[y][x-1] == null){
            markBasins(x-1, y, id, map, basins);
        }

    }

}
