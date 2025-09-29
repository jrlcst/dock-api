package com.dock.costumer.domain.specifications;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CnpjValidSpecification {

    public static boolean isSatisfiedBy(String digits) {
        if (digits == null) return false;
        String only = digits.replaceAll("\\D", "");
        if (only.length() != 14) return false;
        if (allSameDigits(only)) return false;
        int d1 = cnpjCheckDigit(only, 12);
        int d2 = cnpjCheckDigit(only, 13);
        return d1 == Character.getNumericValue(only.charAt(12)) && d2 == Character.getNumericValue(only.charAt(13));
    }

    private static boolean allSameDigits(String s) {
        char first = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != first) return false;
        }
        return true;
    }

    private static int cnpjCheckDigit(String cnpj, int len) {
        int[] weights = (len == 12)
                ? new int[]{5,4,3,2,9,8,7,6,5,4,3,2}
                : new int[]{6,5,4,3,2,9,8,7,6,5,4,3,2};
        int sum = 0;
        for (int i = 0; i < len; i++) {
            int num = Character.getNumericValue(cnpj.charAt(i));
            sum += num * weights[i];
        }
        int mod = sum % 11;
        return (mod < 2) ? 0 : 11 - mod;
    }
}
