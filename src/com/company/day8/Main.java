package com.company.day8;

import com.company.Util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

enum LineSegments {
    TOP, TOP_LEFT, TOP_RIGHT, MIDDLE, BOTTOM_LEFT, BOTTOM_RIGHT, BOTTOM;
}

public class Main {

    public static void main(String[] args) {
        List<String> inputString = Util.loadFile("src/com/company/day8/input.txt");
        List<String[][]> lines = inputString.stream().map(string -> string.split("\\|"))
                .map(strArr -> {
                    strArr[0] = strArr[0].strip();
                    strArr[1] = strArr[1].strip();
                    return new String[][]{strArr[0].split(" "), strArr[1].split(" ")};
                })
                .collect(Collectors.toList());

        long result1 = lines.stream().flatMap(strArr -> Arrays.stream(strArr[1])).filter(string -> Set.of(2, 4, 3,7).contains(string.length())).count();

        System.out.println("Result1: " + result1);

        long result2 = 0;

        for(String[][] line : lines) {
            Map<LineSegments, Character> connections = getLineSegments(line[0]);
            int number = getAsNumber(connections, line[1]);
            result2 += number;


        }
        System.out.print("Result2: " + result2);

    }

    private static int getAsNumber(Map<LineSegments, Character> connections, String[] digits) {

            StringBuilder numberBuilder = new StringBuilder();

            for(String digitString : digits) {
                if(digitString.length() == 2) {
                    numberBuilder.append(1);
                }else if(digitString.length() == 3) {
                    numberBuilder.append(7);
                } else if(digitString.length() == 4) {
                    numberBuilder.append(4);
                }
                else if(digitString.length() == 7) {
                    numberBuilder.append(8);
                }
                else if(digitString.length() == 6) {
                    if(digitString.indexOf(connections.get(LineSegments.MIDDLE)) == -1) {
                        numberBuilder.append(0);
                    }
                    else if(digitString.indexOf(connections.get(LineSegments.TOP_RIGHT)) == -1) {
                        numberBuilder.append(6);
                    }
                    else {
                        numberBuilder.append(9);
                    }
                }
                else if(digitString.length() == 5) {
                    if(digitString.indexOf(connections.get(LineSegments.TOP_RIGHT)) == -1) {
                        numberBuilder.append(5);
                    }
                    else if(digitString.indexOf(connections.get(LineSegments.BOTTOM_LEFT)) == -1) {
                        numberBuilder.append(3);
                    } else {
                        numberBuilder.append(2);
                    }
                }
            }
            return Integer.parseInt(numberBuilder.toString());
    }

    private static Map<LineSegments, Character> getLineSegments(String[] inputArray) {
        Map<LineSegments, Set<Character>> possibleChars = new HashMap<>();
        for(LineSegments segment : LineSegments.values()) {
            possibleChars.put(segment, Set.of('a', 'b', 'c', 'd', 'e', 'f', 'g'));
        }
        List<String> inputs = Arrays.asList(inputArray);
        List<Set<Character>> twoChars = inputs.stream().filter(string -> string.length() == 2).map(string -> string.chars()
                .mapToObj(c -> (char) c).collect(Collectors.toSet())).collect(Collectors.toList());
        List<Set<Character>> threeChars = inputs.stream().filter(string -> string.length() == 3).map(string -> string.chars()
                .mapToObj(c -> (char) c).collect(Collectors.toSet())).collect(Collectors.toList());
        // Das einzige Segment, dass nur in der 7 und nicht in der 1 enthalten ist, ist das Element oben
        possibleChars.put(LineSegments.TOP, threeChars.get(0).stream().filter(character -> !twoChars.get(0).contains(character)).collect(Collectors.toSet()));
        // Für die beiden rechten Elemente kommen nur die Elemente aus der 1 in Frage
        possibleChars.put(LineSegments.TOP_RIGHT, twoChars.get(0));
        possibleChars.put(LineSegments.BOTTOM_RIGHT, twoChars.get(0));
        List<Set<Character>> fourChars = inputs.stream().filter(string -> string.length() == 4).map(string -> string.chars()
                .mapToObj(c -> (char) c).collect(Collectors.toSet())).collect(Collectors.toList());

        //Die Elemente links oben und in der Mitte sind die einzigen, die ind er 4 aber nicht in der 1 enthalten sind
        possibleChars.put(LineSegments.TOP_LEFT, fourChars.get(0).stream().filter(character -> !twoChars.get(0).contains(character)).collect(Collectors.toSet()));
        possibleChars.put(LineSegments.MIDDLE,possibleChars.get(LineSegments.TOP_LEFT));

        List<Set<Character>> sixChars = inputs.stream().filter(string -> string.length() == 6).map(string -> string.chars()
                .mapToObj(c -> (char) c).collect(Collectors.toSet())).collect(Collectors.toList());

        // Die das mittlere Element ist nur in 6 und 9 enthalten, das oben Links in 0, 6 und 9
        possibleChars.put(LineSegments.MIDDLE, sixChars.stream().flatMap(Collection::stream)
                .filter(character -> possibleChars.get(LineSegments.MIDDLE).contains(character))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue() == 2L).map(entry -> entry.getKey() )
                .collect(Collectors.toSet()));
        possibleChars.put(LineSegments.TOP_LEFT, possibleChars.get(LineSegments.TOP_LEFT).stream()
                .filter(character -> !possibleChars.get(LineSegments.MIDDLE).contains(character)).collect(Collectors.toSet()));

        // Das rechte obere Element ist nur in 0 und 9 enthalten, das rechte untere in 0, 6 und 9
        possibleChars.put(LineSegments.TOP_RIGHT, sixChars.stream().flatMap(Collection::stream)
                .filter(character -> possibleChars.get(LineSegments.TOP_RIGHT).contains(character))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue() == 2L).map(entry -> entry.getKey() )
                .collect(Collectors.toSet()));
        possibleChars.put(LineSegments.BOTTOM_RIGHT, possibleChars.get(LineSegments.BOTTOM_RIGHT).stream()
                .filter(character -> !possibleChars.get(LineSegments.TOP_RIGHT).contains(character)).collect(Collectors.toSet()));

        List<Set<Character>> fiveChars = inputs.stream().filter(string -> string.length() == 5).map(string -> string.chars()
                .mapToObj(c -> (char) c).collect(Collectors.toSet())).collect(Collectors.toList());

        // Die 3 ist die einzige 5-Elementige Zahl, die alle rechten und oben und mitte und unten enthält
        // Der Buchstabe, der übrig bleibt, gehört zum untersten Element

        Character bottomChar = fiveChars.stream()
                .filter(set ->  set.contains(possibleChars.get(LineSegments.MIDDLE).stream().findAny().orElseThrow()))
                .filter(set ->  set.contains(possibleChars.get(LineSegments.TOP).stream().findAny().orElseThrow()))
                .filter(set ->  set.contains(possibleChars.get(LineSegments.TOP_RIGHT).stream().findAny().orElseThrow()))
                .filter(set ->  set.contains(possibleChars.get(LineSegments.BOTTOM_RIGHT).stream().findAny().orElseThrow()))
                .flatMap(set -> set.stream())
                .filter(character -> !possibleChars.get(LineSegments.MIDDLE).contains(character))
                .filter(character -> !possibleChars.get(LineSegments.TOP).contains(character))
                .filter(character -> !possibleChars.get(LineSegments.TOP_RIGHT).contains(character))
                .filter(character -> !possibleChars.get(LineSegments.BOTTOM_RIGHT).contains(character))
                .findAny().orElseThrow();

        possibleChars.put(LineSegments.BOTTOM, Set.of(bottomChar));

        // das letzte übrige Segment ist das unten links
        Character bottomLeftChar = Set.of('a', 'b', 'c', 'd', 'e', 'f', 'g').stream()
                .filter(character -> !possibleChars.get(LineSegments.MIDDLE).contains(character))
                .filter(character -> !possibleChars.get(LineSegments.TOP).contains(character))
                .filter(character -> !possibleChars.get(LineSegments.TOP_RIGHT).contains(character))
                .filter(character -> !possibleChars.get(LineSegments.BOTTOM_RIGHT).contains(character))
                .filter(character -> !possibleChars.get(LineSegments.TOP_LEFT).contains(character))
                .filter(character -> !possibleChars.get(LineSegments.BOTTOM).contains(character))
                .findAny().orElseThrow();

        possibleChars.put(LineSegments.BOTTOM_LEFT, Set.of(bottomLeftChar));

        Map<LineSegments, Character> resultTable = new HashMap<>();
        possibleChars.forEach((key, value) -> resultTable.put(key, value.stream().findAny().orElseThrow()));

        return resultTable;

    }

    private static void printMap(Map<LineSegments, Set<Character>> map) {
        for(Map.Entry<LineSegments, Set<Character>> entry : map.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            entry.getValue().forEach(character ->  System.out.print(character + " "));
        }
    }
}
