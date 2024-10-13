package com.fib.cash_desk_module;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cash")
public class CashController {

    private final CashDesk cashDesk;

    public CashController(CashDesk cashDesk) {
        this.cashDesk = cashDesk;
    }

    @PostMapping("/cashier/add")
    public String addCashOperator(@RequestParam String name) {
        cashDesk.addCashOperator(name);
        return "Cashier " + name + " added successfully!";
    }

    @GetMapping("/balance")
    public Map<CurrencyDenomination, Integer> getBalance(@RequestParam String cashierName, @RequestParam Currency currency) {
        return cashDesk.getBalance(cashierName, currency);
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam String cashierName, @RequestParam Currency currency, @RequestBody Map<CurrencyDenomination, Integer> depositAmount) {
        return cashDesk.deposit(cashierName, currency, depositAmount);
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String cashierName, @RequestParam Currency currency, @RequestBody Map<CurrencyDenomination, Integer> withdrawalRequest) {
        return cashDesk.withdraw(cashierName, currency, withdrawalRequest);
    }
}
