package com.hcdl.sales.controller;

import com.hcdl.sales.model.domain.AccountStatement;
import com.hcdl.sales.service.AccountStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account-statement")
public class AccountStatementController {

    private final AccountStatementService accountStatementService;

    @Autowired
    public AccountStatementController(AccountStatementService accountStatementService) {
        this.accountStatementService = accountStatementService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<AccountStatement> fetchAccountStatement() {
        return accountStatementService.fetchLastSixMonthsAccountStatement();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<AccountStatement> fetchAllAccountStatement() {
        return accountStatementService.fetchAllAccountStatement();
    }
}
