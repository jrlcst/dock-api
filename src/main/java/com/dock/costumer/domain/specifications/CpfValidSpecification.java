package com.dock.costumer.domain.specifications;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CpfValidSpecification {

    public static boolean isSatisfiedBy(String digits) {
        if (digits == null) return false;
        String only = digits.replaceAll("\\D", "");
        if (only.length() != 11) return false;
        if (allSameDigits(only)) return false;
        int d1 = cpfCheckDigit(only, 9, 10);
        int d2 = cpfCheckDigit(only, 10, 11);
        return d1 == Character.getNumericValue(only.charAt(9)) && d2 == Character.getNumericValue(only.charAt(10));
    }

    private static boolean allSameDigits(String s) {
        char first = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != first) return false;
        }
        return true;
    }

    private static int cpfCheckDigit(String cpf, int len, int weightStart) {
        int sum = 0;
        for (int i = 0; i < len; i++) {
            int num = Character.getNumericValue(cpf.charAt(i));
            sum += num * (weightStart - i);
        }
        int mod = sum % 11;
        return (mod < 2) ? 0 : 11 - mod;
    }
}
