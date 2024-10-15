package com.fib.cash_desk_module.model;

import java.util.Arrays;
import java.util.List;

public enum Currency {
    EUR("Euro", Arrays.asList(
            CurrencyDenomination.EUR_5,
            CurrencyDenomination.EUR_10,
            CurrencyDenomination.EUR_20,
            CurrencyDenomination.EUR_50,
            CurrencyDenomination.EUR_100,
            CurrencyDenomination.EUR_200
    )),
    BGN("Bulgarian Lev", Arrays.asList(
            CurrencyDenomination.BGN_5,
            CurrencyDenomination.BGN_10,
            CurrencyDenomination.BGN_20,
            CurrencyDenomination.BGN_50,
            CurrencyDenomination.BGN_100
    ));

    private final String name;
    private final List<CurrencyDenomination> denominations;

    Currency(String name, List<CurrencyDenomination> denominations) {
        this.name = name;
        this.denominations = denominations;
    }

    public String getName() {
        return name;
    }

    public List<CurrencyDenomination> getDenominations() {
        return denominations;
    }
}