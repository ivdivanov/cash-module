package com.fib.cash_desk_module;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CashDesk {
    private final Map<String, Cashier> operators = new HashMap<>();

    public CashDesk() {
        addCashOperator("MARTINA");
        initializeMartinaBalance();
    }

    public void addCashOperator(String name) {
        operators.putIfAbsent(name, new Cashier(name));
    }

    public Cashier getCashOperator(String name) {
        return operators.get(name);
    }

    private void initializeMartinaBalance() {
        Cashier martina = operators.get("MARTINA");
        Map<CurrencyDenomination, Integer> bgnBalances = new HashMap<>();
        bgnBalances.put(CurrencyDenomination.BGN_10, 50);
        bgnBalances.put(CurrencyDenomination.BGN_50, 10);

        Map<CurrencyDenomination, Integer> eurBalances = new HashMap<>();
        eurBalances.put(CurrencyDenomination.EUR_50, 10);

        martina.deposit(Currency.BGN, bgnBalances);
        martina.deposit(Currency.EUR, eurBalances);
    }

    public String deposit(String cashierName, Currency currency, Map<CurrencyDenomination, Integer> depositAmount) {
        Cashier cashier = operators.get(cashierName);
        if (cashier == null) {
            return "Cashier not found: " + cashierName;
        }
        cashier.deposit(currency, depositAmount);
        return "Deposit successful!";
    }

    public String withdraw(String cashierName, Currency currency, Map<CurrencyDenomination, Integer> withdrawalRequest) {
        Cashier cashier = operators.get(cashierName);
        if (cashier == null) {
            return "Cashier not found: " + cashierName;
        }
    
        boolean isSuccessful = cashier.withdraw(currency, withdrawalRequest);
        return isSuccessful ? "Withdrawal successful!" : "Insufficient funds or denomination not available.";
    }

    public Map<CurrencyDenomination, Integer> getBalance(String cashierName, Currency currency) {
        Cashier operator = operators.get(cashierName);
        if (operator == null) {
            return null;
        }
        return operator.getCashBalance(currency);
    }
}

