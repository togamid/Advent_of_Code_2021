package com.company.day1;

import com.company.Util;

import java.util.List;

public class Main {

    public static void main(String[] args) {
    List<String> inputString = Util.loadFile("src/com/company/day1/input.txt");
    List<Integer> input = Util.parseInt(inputString);

    int increased = 0;
    int currentDepth = input.get(0) + input.get(1) + input.get(2);
    for(int i = 1; i <input.size()-2; i++) {
        int nextDepth = input.get(i)+input.get(i+1)+input.get(i+2);
        if(nextDepth > currentDepth) {
            increased++;
        }
        currentDepth = nextDepth;
    }
        System.out.println(increased);
    }
}
