package com.upwork.expense_tracker.utils;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * The util class is responsible for generating key pairs (public and private keys).
 * */
public class KeyGeneratorUtil {

    /**
     * Generate RSA Key Pair with key size 2048 bits.
     *
     * @return The generated key pair.
     * */
    public static KeyPair generateKeyPair() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

}
