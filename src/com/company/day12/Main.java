package com.company.day12;

import com.company.Util;

import java.util.*;

public class Main {

    private static final Map<String, Set<String>> passages = new HashMap<>();
    private static final List<List<String>> allPaths = new ArrayList<>();

    public static void main(String[] args) {
    List<String> inputString = Util.loadFile("src/com/company/day12/input.txt");
    inputString.stream().map(string -> string.split("-")).forEach(Main::fillMap);
    List<String> startPath = new ArrayList<>(List.of("start"));
    continuePath("start", startPath, false);
    System.out.println("Number of Paths: " + allPaths.size());



    }
    // only works, if there are no possible circular paths in the dataset
    private static void continuePath(String currentCave, List<String> path, Boolean visitedSmallCaveTwice) {
        Set<String> possiblePassages = passages.get(currentCave);
        possiblePassages.forEach(cave -> {
            if(cave.equalsIgnoreCase("start")){}
            else if(cave.equalsIgnoreCase("end")) {
                path.add("end");
                allPaths.add(path);
            }
            else if(cave.matches("[A-Z]*")) {
                List<String> nextPath = new ArrayList<>(path);
                nextPath.add(cave);
                continuePath(cave, nextPath, visitedSmallCaveTwice);
            }
            else if(cave.matches("[a-z]*")) {
                if(!path.contains(cave)) {
                    List<String> nextPath = new ArrayList<>(path);
                    nextPath.add(cave);
                    continuePath(cave, nextPath, visitedSmallCaveTwice);
                }
                else if(!visitedSmallCaveTwice) {
                    List<String> nextPath = new ArrayList<>(path);
                    nextPath.add(cave);
                    continuePath(cave, nextPath, true);
                }
            }
            else {
                System.out.println("Something went wromng with cave: " + cave);
            }
        });
    }

    private static void fillMap(String[] array) {
        passages.putIfAbsent(array[0], new HashSet<>());
        passages.putIfAbsent(array[1], new HashSet<>());
        passages.get(array[0]).add(array[1]);
        passages.get(array[1]).add(array[0]);
    }
}
