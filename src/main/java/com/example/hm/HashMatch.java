package com.example.hm;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Scanner;

public class HashMatch {
    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);

            System.out.print("Enter your password: ");
            String password = s.nextLine();

            System.out.print("Enter your encryption type (MD5/SHA-1/SHA-256): ");
            String encryptionType = s.nextLine().toUpperCase();

            String targetHash = hash(password, encryptionType);

            System.out.print("Enter wordlist location: ");
            String wordlistPath = s.nextLine();

            boolean found = false;
            for (String word : Files.readAllLines(Paths.get(wordlistPath))) {
                String wordHash = hash(word.trim(), encryptionType);
                if (wordHash.equalsIgnoreCase(targetHash)) {
                    System.out.println("Password found in wordlist: " + word);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Password not found in the wordlist.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String hash(String input, String algorithm) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] digest = md.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
