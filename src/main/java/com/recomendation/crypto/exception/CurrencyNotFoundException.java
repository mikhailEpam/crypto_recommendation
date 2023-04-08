package com.recomendation.crypto.exception;

public class CurrencyNotFoundException extends RuntimeException {

    public CurrencyNotFoundException(String currency) {
        super("Not found or not supported currency: " + currency);
    }
}
