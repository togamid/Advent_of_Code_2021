package com.company.day3;

import com.company.Util;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> inputString = Util.loadFile("src/com/company/day3/input.txt");
        int numberOfBits = inputString.get(0).length();
        System.out.println("Number of Bits: "+ numberOfBits);
        System.out.println("Nuber of entries: " + inputString.size());
        int[] isOneMorePrevalent = new int[numberOfBits];

        for(String number : inputString) {
            for(int i = 0; i < number.length(); i++) {
                if(number.charAt(i) == '1'){
                    isOneMorePrevalent[i]++;
                }
                else {
                    isOneMorePrevalent[i]--;
                }
            }
        }

        char[] gammaBinary = new char[numberOfBits];

        int gamma = 0;
        int epsilon= 0;

        for(int i = 0; i< isOneMorePrevalent.length; i++) {
            int index = isOneMorePrevalent.length -i -1;
            if(isOneMorePrevalent[index] > 0) {
                gamma += 1<<i;
                gammaBinary[index] = '1';
                System.out.print("1");
            }
            else if(isOneMorePrevalent[index] < 0) {
                epsilon += 1<<i;
                gammaBinary[index] = '0';
                System.out.print("0");
            }
            else {
                System.out.println("Help! they're both equally common at pos: " + i);
            }
            System.out.println();
        }
        System.out.println("gamma: " + gamma);
        System.out.println("espilon:" + epsilon);
        System.out.println(gamma * epsilon);

        List<String> numbersForOxygen = Util.loadFile("src/com/company/day3/input.txt");
        int listPos = 0;
        for(int bitPos = 0; bitPos < numberOfBits; bitPos++) {

            char[] prevalentBits = getPrevalentBits(numbersForOxygen);
            while(listPos < numbersForOxygen.size()) {
                if(numbersForOxygen.get(listPos).charAt(bitPos) != prevalentBits[bitPos]) {
                    numbersForOxygen.remove(listPos);
                }
                else {
                    listPos++;
                }
            }
            listPos = 0;
            if(numbersForOxygen.size() == 1) {
                break;
            }
        }

        int oxygen = Integer.parseInt(numbersForOxygen.get(0), 2);
        System.out.println("Oxygen: " + oxygen);

        List<String> numbersForCO2 = Util.loadFile("src/com/company/day3/input.txt");
        listPos = 0;
        for(int bitPos = 0; bitPos < numberOfBits; bitPos++) {

            char[] prevalentBits = getPrevalentBits(numbersForCO2);
            while(listPos < numbersForCO2.size()) {
                if(numbersForCO2.get(listPos).charAt(bitPos) == prevalentBits[bitPos]) {
                    numbersForCO2.remove(listPos);
                }
                else {
                    listPos++;
                }
            }
            listPos = 0;
            if(numbersForCO2.size() == 1) {
                break;
            }
        }

        int co2 = Integer.parseInt(numbersForCO2.get(0), 2);
        System.out.println("CO2: " + co2);

        System.out.println("Result: " + oxygen * co2);

    }

    private static char[] getPrevalentBits(List<String> numbers) {
        char[] gammaBinary = new char[numbers.get(0).length()];
        int[] isOneMorePrevalent = new int[gammaBinary.length];

        for (String number : numbers) {
            for (int i = 0; i < number.length(); i++) {
                if (number.charAt(i) == '1') {
                    isOneMorePrevalent[i]++;
                } else {
                    isOneMorePrevalent[i]--;
                }
            }
        }

        for (int i = 0; i < isOneMorePrevalent.length; i++) {
            int index = isOneMorePrevalent.length - i - 1;
            if (isOneMorePrevalent[index] > 0) {
                gammaBinary[index] = '1';
            } else if (isOneMorePrevalent[index] < 0) {
                gammaBinary[index] = '0';
            } else {
                gammaBinary[index] = '1';
            }
        }
        return gammaBinary;
    }
}

