package com.fib.cash_desk_module;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CashDesk cashDesk() {
        return new CashDesk();
    }
}

