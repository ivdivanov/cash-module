package com.fib.cash_desk_module;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.fib.cash_desk_module.model.Currency;
import com.fib.cash_desk_module.model.CurrencyDenomination;

public class LogFormatter {

    public static String formatTransactionLog(String operation, String cashierName, Currency currency, Map<CurrencyDenomination, Integer> amounts) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder logEntry = new StringBuilder(LocalDateTime.now().format(formatter));
        logEntry.append(" - ").append(operation).append(": ").append(cashierName)
                .append(" (Currency: ").append(currency).append(", Denominations: ");

        amounts.forEach((denomination, count) -> logEntry.append(denomination).append(": ").append(count).append(", "));
        logEntry.setLength(logEntry.length() - 2);
        logEntry.append(")");

        return logEntry.toString();
    }

    public static String formatBalanceLog(String cashierName, Map<Currency, Map<CurrencyDenomination, Integer>> balance) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder logEntry = new StringBuilder(LocalDateTime.now().format(formatter));
        logEntry.append(" - Balance Check for Cashier: ").append(cashierName).append(" (");

        balance.forEach((currency, denominations) -> {
            logEntry.append(currency).append(": {");
            denominations.forEach((denomination, count) -> logEntry.append(denomination).append(": ").append(count).append(", "));
            logEntry.setLength(logEntry.length() - 2);
            logEntry.append("}, ");
        });
        logEntry.setLength(logEntry.length() - 2);
        logEntry.append(")");

        return logEntry.toString();
    }
}
