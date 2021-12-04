package com.company.day4;

import java.util.Arrays;
import java.util.List;

public class Board {
    private Integer[][] numbers = new Integer[5][5];
    private boolean[][] checked = new boolean[5][5];
    boolean hasWon = false;

    public void loadBoard(List<String> input, int firstLine) {
        for(int i = 0; i<5; i++) {
            numbers[i] = Arrays.stream(input.get(firstLine+i).split(" "))
                    .filter(string -> !string.isEmpty()).map(Integer::parseInt)
                    .toArray(Integer[]::new);
        }
    }

    public void markNumber(int number) {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(numbers[i][j] == number) {
                    checked[i][j] = true;
                }
            }
        }
    }
    public boolean checkBoard() {
        for(int i = 0; i < 5; i++) {
            boolean rowComplete = true;
            boolean columnComplete = true;
            for(int j = 0; j < 5; j++) {
                if(!checked[i][j]) {
                    rowComplete = false;
                }
                if(!checked[j][i]) {
                    columnComplete = false;
                }
            }
            if(rowComplete) {
                return true;
            }
            if(columnComplete) {
                return true;
            }
        }
        return false;
    }

    public int getSumUnmarked() {
        int sum = 0;
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(!checked[i][j]) {
                    sum += numbers[i][j];
                }
            }
        }
        return sum;
    }
}
