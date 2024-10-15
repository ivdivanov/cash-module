package com.fib.cash_desk_module.exception;

public class InsucificientTransactionNumberException extends RuntimeException{
    public InsucificientTransactionNumberException(String cashierName) {
        super("The cashier " + cashierName + " needs to make atleast 2 withdraws and 2 deposits before checking balance");
    }
}
