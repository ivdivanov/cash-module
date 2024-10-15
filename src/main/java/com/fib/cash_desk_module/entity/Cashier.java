package com.fib.cash_desk_module.entity;

import java.util.HashMap;
import java.util.Map;

import com.fib.cash_desk_module.model.Currency;
import com.fib.cash_desk_module.model.CurrencyDenomination;

public class Cashier {
    private final String name;
    private final Map<Currency, Map<CurrencyDenomination, Integer>> cashBalance;
    private int numberOfWithdraws = 0;
    private int numberOfDeposits = 0;

    public Cashier(String name) {
        this.name = name;
        this.cashBalance = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<Currency, Map<CurrencyDenomination, Integer>> getCashBalance() {
        return cashBalance;
    }

    public int getNumberOfWithdraws(){
        return numberOfWithdraws;
    }

    public void setNumberOfWithdraws(){
        numberOfWithdraws++;
    }

    public int getNumberOfDeposits(){
        return numberOfDeposits;
    }

    public void setNumberOfDeposits(){
        numberOfDeposits++;
    }
}
