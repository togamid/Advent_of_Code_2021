package com.company.day4;

import com.company.Util;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
    List<String> inputString = Util.loadFile("src/com/company/day4/input.txt");
    List<Integer> randomNumbers = Util.parseInt(inputString.get(0).split(","));

    List<Board> boards = new ArrayList<>();
    for(int i = 0; i < inputString.size(); i++) {
        if (inputString.get(i).equalsIgnoreCase("")) {
            Board board = new Board();
            board.loadBoard(inputString, i + 1);
            boards.add(board);
        }
    }

    for(int number : randomNumbers) {
        for(Board board : boards) {
            board.markNumber(number);
            if(board.checkBoard()) {
                System.out.println("Result: " + board.getSumUnmarked() * number);
                return;
            }
        }
    }

    }
}