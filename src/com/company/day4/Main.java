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
            board.hasWon = board.checkBoard();
        }
        if(boards.size() > 1) {
            boards.removeIf(board -> board.hasWon);
        }
        else if(boards.get(0).hasWon) {
            System.out.println("Result: " + boards.get(0).getSumUnmarked() * number);
            return;
        }
    }

    }
}