package com.company.day11;

import com.company.Util;

import java.util.List;

public class Main {
    private static long flashCount = 0;

    public static void main(String[] args) {
        List<String> inputString = Util.loadFile("src/com/company/day11/input.txt");
        Integer[][] map = new Integer[inputString.size()][inputString.get(0).length()];

        for (int i = 0; i < map.length; i++) {
            map[i] = Util.parseArrayInt(inputString.get(i).split(""));
        }
        Integer[][] lastFlashRound = new Integer[inputString.size()][inputString.get(0).length()];

        for(int round = 0; round < 300; round++) {

            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[0].length; x++) {
                    map[y][x]++;
                }
            }


            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[0].length; x++) {
                    testFlash(x, y, round, map, lastFlashRound);
                }
            }
            int octopussesFlashedThisRound = 0;
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[0].length; x++) {
                    if(lastFlashRound[y][x] != null && lastFlashRound[y][x] == round) {
                        octopussesFlashedThisRound++;
                        map[y][x] = 0;
                    }
                }
            }
            if(octopussesFlashedThisRound == 100) {
                System.out.println("Result 2: " + (round + 1));
                break;
            }

        }
        System.out.println("Flash Count: " +flashCount);
    }
    private static void output(Integer[][] array){
        for(int y = 0; y < array.length; y ++) {
            for(int x = 0; x < array[0].length; x++) {
                System.out.print(array[y][x]);
            }
            System.out.println();
        }
    }

    private static void testFlash(int x, int y, int round, Integer[][] map, Integer[][] lastFlashRound) {
        if(map[y][x] <= 9 || (lastFlashRound[y][x] != null && lastFlashRound[y][x] == round)) {
            return;
        }
        flashCount++;
        lastFlashRound[y][x] = round;
        if(y-1 >= 0) {
            map[y-1][x]++;
            testFlash(x, y-1, round, map, lastFlashRound);
        }
        if(y+1 <map.length ){
            map[y+1][x]++;
           testFlash(x, y+1, round, map, lastFlashRound);
        }
        if(x+1 <map[0].length ){
            map[y][x+1]++;
            testFlash(x+1, y, round, map, lastFlashRound);
        }
        if(x-1 >=0 ){
            map[y][x-1]++;
            testFlash(x-1, y, round, map, lastFlashRound);
        }
        if(y-1 >= 0 && x-1 >=0) {
            map[y-1][x-1]++;
            testFlash(x-1, y-1, round, map, lastFlashRound);
        }
        if(y-1 >= 0 && x+1 <map[0].length) {
            map[y-1][x+1]++;
            testFlash(x+1, y-1, round, map, lastFlashRound);
        }
        if(y+1 <map.length && x+1 <map[0].length) {
            map[y+1][x+1]++;
            testFlash(x+1, y+1, round, map, lastFlashRound);
        }
        if(y+1 <map.length && x-1 >=0) {
            map[y+1][x-1]++;
            testFlash(x-1, y+1, round, map, lastFlashRound);
        }

    }

}
