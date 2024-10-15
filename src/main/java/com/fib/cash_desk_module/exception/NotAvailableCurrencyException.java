package com.fib.cash_desk_module.exception;

public class NotAvailableCurrencyException extends RuntimeException {
    public NotAvailableCurrencyException() {
        super("Currency not available.");
    }
}
