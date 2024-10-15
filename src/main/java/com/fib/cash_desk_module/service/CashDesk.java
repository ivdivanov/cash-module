package com.fib.cash_desk_module.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.fib.cash_desk_module.model.Currency;
import com.fib.cash_desk_module.model.CurrencyDenomination;
import com.fib.cash_desk_module.requests.CashOperationRequest;

public interface CashDesk {
    void addCashier(String name);
    String performCashOperation(@RequestBody CashOperationRequest request);
    Map<Currency, Map<CurrencyDenomination, Integer>> getCashierBalance(String cashierName);
}
