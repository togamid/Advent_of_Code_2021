package com.company.day5;

import com.company.Util;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> inputString = Util.loadFile("src/com/company/day5/input.txt");
        List<Point[]> lines = inputString.stream()
                .map(string -> string.replace(" -> ", ","))
                .map(string -> string.split(","))
                .map((Util::parseArrayInt))
                .map(integers -> new Point[]{new Point(integers[0], integers[1]), new Point(integers[2], integers[3])})
                .collect(Collectors.toList());
        Integer maxX = lines.stream()
                .map(points -> List.of(points[0].x, points[1].x))
                .flatMap(Collection::stream)
                .max(Integer::compareTo).orElseThrow();
        Integer maxY = lines.stream()
                .map(points -> List.of(points[0].y, points[1].y))
                .flatMap(Collection::stream)
                .max(Integer::compareTo).orElseThrow();
        int[][] map = new int[maxX+1][maxY+1];

        for(Point[] points : lines) {
            if(points[0].x == points[1].x){
                for(int i = Math.min(points[0].y, points[1].y); i<= Math.max(points[0].y, points[1].y); i++ ) {
                    map[points[0].x][i]++;
                }
            }
            else if(points[0].y == points[1].y) {
                for(int i = Math.min(points[0].x, points[1].x); i<= Math.max(points[0].x, points[1].x); i++ ) {
                    map[i][points[0].y]++;
                }
            }
        }
        int result = 0;

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                if(map[j][i] >= 2) {
                    result++;
                }
                //System.out.print(map[j][i]);
            }
            //System.out.println();
        }

        System.out.println("Result: " + result);
    }

}
