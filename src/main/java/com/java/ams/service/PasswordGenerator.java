package com.java.ams.service;

import java.security.SecureRandom;
import org.springframework.stereotype.Service;

@Service
public class PasswordGenerator {
	private static final SecureRandom RANDOM = new SecureRandom();
    public static String generatePassword(int length) {
        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        String numericChars = "0123456789";
        String specialChars = "!@#$%^&*";

        String allChars = uppercaseChars + lowercaseChars + numericChars + specialChars;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        password.append(uppercaseChars.charAt(random.nextInt(uppercaseChars.length())));
        password.append(lowercaseChars.charAt(random.nextInt(lowercaseChars.length())));
        password.append(numericChars.charAt(random.nextInt(numericChars.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        for (int i = 4; i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        char[] passwordArray = password.toString().toCharArray();
        for (int i = length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[index];
            passwordArray[index] = temp;
        }
        return new String(passwordArray);
    }
    public static int[] generateOTP(int length) {
        final int MIN_VALUE = 0;
        final int MAX_VALUE = 9;
        int[] otpArray = new int[length];
        for (int i = 0; i < length; i++) {
            int rndInt = RANDOM.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE;
            otpArray[i] = rndInt;
        }
        return otpArray;
    }
}
