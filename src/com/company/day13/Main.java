package com.company.day13;

import com.company.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> inputString = Util.loadFile("src/com/company/day13/input.txt");
        Pattern pattern = Pattern.compile("fold along ([xy])=([0-9]+)");
        List<Fold> folds = new ArrayList<>();
        List<Point> points = new ArrayList<>();

        for (String string : inputString) {
            Matcher m = pattern.matcher(string);
            if (m.find()) {
                folds.add(new Fold(m.group(1), Integer.parseInt(m.group(2))));
            }
            else {
                String[] array = string.split(",");
                if(array.length == 2) {
                    points.add(new Point(Integer.parseInt(array[0]), Integer.parseInt(array[1])));
                }
            }
        }

        for(Fold fold : folds) {
            points = fold(fold, points);
        }

        Integer maxX = points.stream().map(point -> point.x).max(Comparator.naturalOrder()).orElseThrow();
        Integer maxY = points.stream().map(point -> point.y).max(Comparator.naturalOrder()).orElseThrow();

        int[][] output = new int[maxY+1][maxX+1];
        points.forEach(point -> output[point.y][point.x] = 7);

        output(output);

        System.out.println("Anzahl Punkte: " + points.size());

    }

    private static void output(int[][] array) {
        for(int[] array1 : array) {
            for( int integer: array1) {
                System.out.print(integer);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static List<Point> fold(Fold fold, List<Point>points) {
        return points.stream().peek(point -> {
            if(fold.direction.equalsIgnoreCase("x")){
                if(point.x > fold.position) {
                    point.x = (-1 * point.x + 2* fold.position);
                }
            }
            else {
                if(point.y > fold.position) {
                    point.y = (-1 * point.y + 2* fold.position);
                }
            }
        }).distinct().collect(Collectors.toList());
    }
}
