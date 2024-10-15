package com.fib.cash_desk_module.exception;
public class CashierNotFoundException extends RuntimeException {
    public CashierNotFoundException(String cashierName) {
        super("The cashier does not exist in our records");
    }
}
