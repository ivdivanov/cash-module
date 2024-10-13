package com.fib.cash_desk_module;

import java.util.HashMap;
import java.util.Map;

public class Cashier {
    private final String name;
    private final Map<Currency, Map<CurrencyDenomination, Integer>> cashBalance;

    public Cashier(String name) {
        this.name = name;
        this.cashBalance = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<CurrencyDenomination, Integer> getCashBalance(Currency currency) {
        return cashBalance.getOrDefault(currency, new HashMap<>());
    }

    public void deposit(Currency currency, Map<CurrencyDenomination, Integer> depositAmount) {
        cashBalance.computeIfAbsent(currency, k -> new HashMap<>());
        Map<CurrencyDenomination, Integer> currentBalance = cashBalance.get(currency);
        for (Map.Entry<CurrencyDenomination, Integer> entry : depositAmount.entrySet()) {
            CurrencyDenomination denom = entry.getKey();
            int count = entry.getValue();
            currentBalance.put(denom, currentBalance.getOrDefault(denom, 0) + count);
        }
    }

    public boolean withdraw(Currency currency, Map<CurrencyDenomination, Integer> withdrawalRequest) {
        Map<CurrencyDenomination, Integer> currentBalance = cashBalance.get(currency);

        if (currentBalance == null) {
            return false;
        }

        for (Map.Entry<CurrencyDenomination, Integer> entry : withdrawalRequest.entrySet()) {
            CurrencyDenomination denomination = entry.getKey();
            int requestedAmount = entry.getValue();
            int availableAmount = currentBalance.getOrDefault(denomination, 0);

            if (availableAmount < requestedAmount) {
                return false;
            }
        }

        for (Map.Entry<CurrencyDenomination, Integer> entry : withdrawalRequest.entrySet()) {
            CurrencyDenomination denomination = entry.getKey();
            int requestedAmount = entry.getValue();
            currentBalance.put(denomination, currentBalance.get(denomination) - requestedAmount);
        }

        return true;
    }
}
