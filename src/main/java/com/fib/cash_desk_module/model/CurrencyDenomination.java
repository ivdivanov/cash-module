package com.fib.cash_desk_module.model;

public enum CurrencyDenomination {
    EUR_5(5), EUR_10(10), EUR_20(20), EUR_50(50), EUR_100(100), EUR_200(200),
    BGN_5(5), BGN_10(10), BGN_20(20), BGN_50(50), BGN_100(100);

    private final int value;

    CurrencyDenomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

