package com.example.demo.test;

import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[] args) {
        List<String> identifiers = new ArrayList<>();
        identifiers.add("a3-01");
        identifiers.add("a3-02");
        identifiers.add("a3-10");
        identifiers.add("a3-03");

        int maxNumber = findMaxNumber(identifiers);
        String newIdentifier = generateNewIdentifier(maxNumber + 1);

        System.out.println("The new identifier is: " + newIdentifier);
    }

    private static int findMaxNumber(List<String> identifiers) {
        int max = 0;
        for (String id : identifiers) {
            int number = extractNumber(id);
            if (number > max) {
                max = number;
            }
        }
        return max;
    }

    private static int extractNumber(String identifier) {
        // 去掉前缀 "a3-"
        String numberPart = identifier.substring(3);
        return Integer.parseInt(numberPart);
    }

    private static String generateNewIdentifier(int number) {
        // 生成新的编号，确保两位数格式
        String aa = "aa001";
        return String.format(aa+"-%02d", number);
    }
}
