package com.company.day2;

import com.company.Util;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> inputString = Util.loadFile("src/com/company/day2/input.txt");
        int depth = 0;
        int pos = 0;
        for(String line : inputString) {
            String[] array = line.split(" ");
            int amount = Integer.parseInt(array[1]);
            switch (array[0]) {
                case "up":
                    depth -=amount;
                    break;
                case "down":
                    depth += amount;
                    break;
                case "forward":
                    pos += amount;
                    break;
                default:
                    System.out.println("invalid line " + line);
            }
        }
        System.out.println("depth: " + depth);
        System.out.println("pos " + pos);
        System.out.println("result " + (depth * pos));
    }
}
