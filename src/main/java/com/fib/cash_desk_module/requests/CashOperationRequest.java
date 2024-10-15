package com.fib.cash_desk_module.requests;
import java.util.Map;

import com.fib.cash_desk_module.model.Currency;
import com.fib.cash_desk_module.model.CurrencyDenomination;
import com.fib.cash_desk_module.model.OperationType;

public class CashOperationRequest {
    private String cashierName;
    private Currency currency;
    private OperationType operationType;
    private Map<CurrencyDenomination, Integer> denominations;

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Map<CurrencyDenomination, Integer> getDenominations() {
        return denominations;
    }

    public void setDenominations(Map<CurrencyDenomination, Integer> denominations) {
        this.denominations = denominations;
    }

}
