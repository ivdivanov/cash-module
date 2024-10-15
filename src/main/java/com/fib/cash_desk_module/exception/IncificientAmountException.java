package com.fib.cash_desk_module.exception;

public class IncificientAmountException extends RuntimeException{
    public IncificientAmountException() {
        super("Insufficient funds!");
    }
}
