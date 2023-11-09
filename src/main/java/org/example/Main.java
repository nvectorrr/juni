package org.example;

import org.apache.commons.lang3.StringUtils;

import java.util.BitSet;

public class Main {

    public static String getBitSetAsBinStr(byte[] bytes) {
        String str = "";

        for (int i = 0; i < bytes.length; i++) {
            String s = String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0');
            str += s;
        }

        return str;
    }

    public static int getBitDifference(String str1, String str2) {
        int counter = 0;

        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                counter++;
            }
        }

        return counter;
    }

    public static String getBitsDistribution(String str) {
        int zeros = StringUtils.countMatches(str, "0");
        int ones = StringUtils.countMatches(str, "1");
        return zeros + " zeros | " + ones + " ones";
    }

    public static void main(String[] args) {

        byte[] randLocked = {-39, 79, -75, -80, -59, -76, -93, -7, -35, 28, 7, -64, 49, -98, -70, 9};
        byte[] keyLocked = {72, 72, 20, -64, 87, 37, 121, 68, 35, 83, 29, 53, 118, 46, -114, 26};

        BitSet locketRand = BitSet.valueOf(randLocked);
        BitSet locketKey = BitSet.valueOf(keyLocked);

        // reverting value of single bit
        locketKey.flip(7);

        byte[] randChanged = locketRand.toByteArray();
        byte[] keyChanged = locketKey.toByteArray();

        byte[] outputChanged = Comp128.v1(keyChanged, randChanged);
        byte[] outputLocked = Comp128.v1(keyLocked, randLocked);

        String binaryLockedString = getBitSetAsBinStr(outputLocked);
        String binaryChangedString = getBitSetAsBinStr(outputChanged);

        System.out.println("\n---------------------- SUMMARY ----------------------");
        System.out.println("Result bit sets sizes (L/C): " + binaryLockedString.length() + "/" + binaryChangedString.length());
        System.out.println("Bit difference (count) with 1 changed bit: " + getBitDifference(binaryLockedString, binaryChangedString) + " of 96");
        System.out.println("Bits distribution: " + getBitsDistribution(binaryLockedString));
        System.out.println("-------------------------------------------------------");
        System.out.println("Result bit str: " + binaryLockedString);
        System.out.println("Result bit str with one reverted: " + binaryChangedString);
        System.out.println("-------------------------------------------------------");
    }
}
