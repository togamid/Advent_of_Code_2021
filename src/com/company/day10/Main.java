package com.company.day10;

import com.company.Util;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> inputString = Util.loadFile("src/com/company/day10/input.txt");

        Integer result1 = 0;
        List<Long> result2List = new ArrayList<>();

        Map<Character, Integer> value1 = Map.of(')', 3, ']', 57,'}',1197, '>', 25137);
        Map<Character, Integer> value2 = Map.of('(', 1, '[', 2,'{',3, '<', 4);

        Map<Character, Character> correspondingChar = Map.of( '(', ')',  '[',']','{', '}',  '<', '>');


        for(String line : inputString) {
            Stack<Character> stack = new Stack<>();
            Integer result1Line = 0;
            for(Character character : line.chars().mapToObj(character -> (char) character).collect(Collectors.toList())) {
                boolean endLoop = false;
                switch (character) {
                    case '(':
                    case '<':
                    case '{':
                    case '[':
                        stack.push(character);
                        break;
                    case ')':
                    case '>':
                    case '}':
                    case ']':
                        Character openingChar = stack.pop();
                        if (correspondingChar.get(openingChar) != character) {
                            result1Line += value1.get(character);
                            endLoop = true;
                        }
                        break;
                    default:
                        System.out.println(character);
                }
                if(endLoop) {
                    break;
                }
            }
            if(result1Line > 0) {
                result1 += result1Line;
            }
            else if(!stack.empty()) {
                Long result2 = 0L;
                while(!stack.empty()) {
                    result2 *= 5;
                    result2 += value2.get(stack.pop());
                }
                result2List.add(result2);
            }
        }
        System.out.println("Result1: "+ result1);
        result2List.sort(Comparator.naturalOrder());
        System.out.println("Result2: " + result2List.get(result2List.size()/2 ));
    }

}
