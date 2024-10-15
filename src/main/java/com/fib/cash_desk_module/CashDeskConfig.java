package com.fib.cash_desk_module;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fib.cash_desk_module.entity.Cashier;
import com.fib.cash_desk_module.model.Currency;
import com.fib.cash_desk_module.model.CurrencyDenomination;
import com.fib.cash_desk_module.service.CashDeskImpl;

@Configuration
public class CashDeskConfig {

    @Autowired
    private CashDeskImpl cashDesk;

    @Bean
    public Cashier initializeCashiers() {
        Cashier martina = new Cashier("MARTINA");
            
        Map<CurrencyDenomination, Integer> bgnBalances = new HashMap<>();
        bgnBalances.put(CurrencyDenomination.BGN_10, 50);
        bgnBalances.put(CurrencyDenomination.BGN_50, 10);

        Map<CurrencyDenomination, Integer> eurBalances = new HashMap<>();
        eurBalances.put(CurrencyDenomination.EUR_10, 100);
        eurBalances.put(CurrencyDenomination.EUR_50, 20);

        martina.getCashBalance().put(Currency.BGN, bgnBalances);
        martina.getCashBalance().put(Currency.EUR, eurBalances);

        cashDesk.getCashiers().put("MARTINA", martina);

        return martina;
    }
}
