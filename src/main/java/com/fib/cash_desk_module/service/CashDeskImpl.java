package com.fib.cash_desk_module.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fib.cash_desk_module.LogFormatter;
import com.fib.cash_desk_module.entity.Cashier;
import com.fib.cash_desk_module.exception.CashierNotFoundException;
import com.fib.cash_desk_module.exception.IncificientAmountException;
import com.fib.cash_desk_module.exception.InsucificientTransactionNumberException;
import com.fib.cash_desk_module.exception.InvalidOperationTypeException;
import com.fib.cash_desk_module.exception.NotAvailableCurrencyException;
import com.fib.cash_desk_module.model.Currency;
import com.fib.cash_desk_module.model.CurrencyDenomination;
import com.fib.cash_desk_module.model.OperationType;
import com.fib.cash_desk_module.requests.CashOperationRequest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CashDeskImpl implements CashDesk {
    private final Map<String, Cashier> cashiers;

    public CashDeskImpl() {
        this.cashiers = new HashMap<>();
    }

    public Map<String, Cashier> getCashiers() {
        return cashiers;
    }

    @Override
    public void addCashier(String name) {
        if (!cashiers.containsKey(name)) {
            cashiers.put(name, new Cashier(name));
        }
    }

    @Override
    public String performCashOperation(@RequestBody CashOperationRequest request) {
        String cashierName = request.getCashierName();
        Currency currency = request.getCurrency();
        Map<CurrencyDenomination, Integer> denominations = request.getDenominations();
        OperationType operationType = request.getOperationType();
    
        if (operationType == OperationType.DEPOSIT) {
            return deposit(cashierName, currency, denominations);
        } else if (operationType == OperationType.WITHDRAW) {
            return withdraw(cashierName, currency, denominations);
        }
    
        throw new InvalidOperationTypeException();
    }

    @Override
    public Map<Currency, Map<CurrencyDenomination, Integer>> getCashierBalance(String cashierName) {
        Cashier cashier = getCashier(cashierName);
        if (cashier.getNumberOfDeposits() < 2 && cashier.getNumberOfWithdraws() < 2) {
            throw new InsucificientTransactionNumberException(cashierName);
        }
        Map<Currency, Map<CurrencyDenomination, Integer>> cashBalance = cashier.getCashBalance();
        String logEntry = LogFormatter.formatBalanceLog(cashierName, cashBalance);
        writeToLogFile(logEntry, "cash_balance_checks.log");
        return cashBalance;
    }

    public String deposit(String cashierName, Currency currency, Map<CurrencyDenomination, Integer> depositAmount) {
        Cashier cashier = getCashier(cashierName);
        Map<CurrencyDenomination, Integer> currentBalance = cashier.getCashBalance().getOrDefault(currency, new HashMap<>());

        for (Map.Entry<CurrencyDenomination, Integer> entry : depositAmount.entrySet()) {
            CurrencyDenomination denomination = entry.getKey();
            int count = entry.getValue();
            currentBalance.put(denomination, currentBalance.getOrDefault(denomination, 0) + count);
        }

        cashier.getCashBalance().put(currency, currentBalance);

        int totalDeposited = calculateTotalAmount(depositAmount);
        cashier.setNumberOfDeposits();
        String logEntry = LogFormatter.formatTransactionLog("Deposit", cashierName, currency, depositAmount);
        writeToLogFile(logEntry, "cash_operations.log");
        return "Deposit successful! Total amount deposited: " + totalDeposited + " " + currency;
    }

    public String withdraw(String cashierName, Currency currency, Map<CurrencyDenomination, Integer> withdrawAmount) {
        Cashier cashier = getCashier(cashierName);
        Map<CurrencyDenomination, Integer> currentBalance = cashier.getCashBalance().get(currency);
        if (currentBalance == null) {
            throw new NotAvailableCurrencyException();
        }

        for (Map.Entry<CurrencyDenomination, Integer> entry : withdrawAmount.entrySet()) {
            CurrencyDenomination denomination = entry.getKey();
            int count = entry.getValue();
            if (currentBalance.getOrDefault(denomination, 0) < count) {
                throw new IncificientAmountException();
            }
        }

        for (Map.Entry<CurrencyDenomination, Integer> entry : withdrawAmount.entrySet()) {
            CurrencyDenomination denomination = entry.getKey();
            int count = entry.getValue();
            currentBalance.put(denomination, currentBalance.get(denomination) - count);
        }

        int totalWithdrawn = calculateTotalAmount(withdrawAmount);
        cashier.setNumberOfWithdraws();
        String logEntry = LogFormatter.formatTransactionLog("Withdraw", cashierName, currency, withdrawAmount);
        writeToLogFile(logEntry, "cash_operations.log");
        return "Withdrawal successful! Total amount withdrawn: " + totalWithdrawn + " " + currency;
    }

    private int calculateTotalAmount(Map<CurrencyDenomination, Integer> denominations) {
        int totalAmount = 0;
        for (Map.Entry<CurrencyDenomination, Integer> entry : denominations.entrySet()) {
            CurrencyDenomination denomination = entry.getKey();
            int count = entry.getValue();
            totalAmount += denomination.getValue() * count;
        }
        return totalAmount;
    }

    private Cashier getCashier(String cashierName) {
        if (!cashiers.containsKey(cashierName)) {
            throw new CashierNotFoundException(cashierName);
        }
        Cashier cashier = cashiers.get(cashierName);
        return cashier;
    }

    private void writeToLogFile(String message, String logFileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }
}
