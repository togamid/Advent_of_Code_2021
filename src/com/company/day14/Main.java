package com.company.day14;

import com.company.Util;
import com.company.day13.Fold;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        List<String> inputString = Util.loadFile("src/com/company/day14/input.txt");
        Pattern pattern = Pattern.compile("([A-Z]+) -> ([A-Z])");
        String startingString = inputString.get(0);
        Map<String, Set<String>> mappings = new HashMap<>();

        for (String string : inputString) {
            Matcher m = pattern.matcher(string);
            if (m.find()) {
                String string1 = m.group(2) + m.group(1).substring(1,2);
                String string2 = m.group(1).substring(0,1) + m.group(2);

               mappings.put(m.group(1), Set.of(string1, string2));
            }
        }


        Map<String, Long> occurancesOld = new HashMap<>();
        Map<String, Long> finalOccurancesOld = occurancesOld;
        Arrays.stream(startingString.split(""))
                .reduce((string, next) -> { if(finalOccurancesOld.get(string+next) == null){
                    finalOccurancesOld.put(string+next, 1L);} else {finalOccurancesOld.put(string+next, finalOccurancesOld.get(string+next) + 1 );}; return next;});


        for(int i = 0; i < 40; i++) {
            Map<String, Long>  occurances = new HashMap<>();
            for(Map.Entry<String, Long> entry : occurancesOld.entrySet()) {

                Long numberOccurancesOldComb = entry.getValue();
                String combination = entry.getKey();

                Set<String> nextStrings = mappings.get(combination);

                for(String string : nextStrings) {

                    Long numberOccNewComb = occurances.get(string);
                    if(numberOccNewComb == null) {
                        occurances.put(string, numberOccurancesOldComb);
                    }
                    else {
                        occurances.put(string, numberOccurancesOldComb + numberOccNewComb);
                    }
                }
            }
            occurancesOld = occurances;
        }

        // zählt jeden Buchstaben außer den ganz vorne und ganz hinten doppelt
        List<Long> numbersList = new ArrayList<>();
        HashMap<String, Long> occurancesLetter = new HashMap<>();
        for(Map.Entry<String, Long> entry : occurancesOld.entrySet()) {

            for(String string : List.of(entry.getKey().substring(0,1), entry.getKey().substring(1,2))) {
                //System.out.println(string + ": " + entry.getValue());
                Long integer = occurancesLetter.get(string);
                if(integer == null) {
                    occurancesLetter.put(string, entry.getValue());
                }
                else {
                    occurancesLetter.put(string, entry.getValue() + integer);
                }
            }
        }


        for (Map.Entry<String, Long> entry : occurancesLetter.entrySet()) {
            Long value = entry.getValue()/2;
            if(entry.getKey().equalsIgnoreCase(startingString.split("")[0]) || entry.getKey().equalsIgnoreCase(startingString.split("")[startingString.length()-1]) ) {
                value++;
            }
            numbersList.add(value);
            System.out.println(entry.getKey() + ": " + value);
        }

        Long max = numbersList.stream().max(Comparator.naturalOrder()).orElseThrow();
        Long min = numbersList.stream().min(Comparator.naturalOrder()).orElseThrow();

        System.out.println("Result1: " + (max - min));
    }

    private static void output(Map<String, Long> map) {
        for(Map.Entry<String, Long> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }


}
