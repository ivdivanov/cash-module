package com.fib.cash_desk_module.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fib.cash_desk_module.model.Currency;
import com.fib.cash_desk_module.model.CurrencyDenomination;
import com.fib.cash_desk_module.requests.CashOperationRequest;
import com.fib.cash_desk_module.service.CashDeskImpl;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CashController {

    private final CashDeskImpl cashDesk;

    public CashController(CashDeskImpl cashDesk) {
        this.cashDesk = cashDesk;
    }

    @PostMapping("/cashier/add")
    public String addCashOperator(@RequestParam String name) {
        cashDesk.addCashier(name);
        return "Cashier " + name + " added successfully!";
    }

    @PostMapping("/cash-operation")
    public ResponseEntity<String> performCashOperation(@RequestBody CashOperationRequest request) {
        return new ResponseEntity<>(cashDesk.performCashOperation(request), HttpStatus.ACCEPTED);
    }

    @GetMapping("/cash-balance")
    public ResponseEntity<Map<Currency, Map<CurrencyDenomination, Integer>>> getBalance(@RequestBody String cashierName) {
        return new ResponseEntity<>(cashDesk.getCashierBalance(cashierName.trim()), HttpStatus.OK);
    }

}
