package com.hcdl.sales.controller;

import com.hcdl.sales.model.domain.AccountStatement;
import com.hcdl.sales.service.AccountStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@RestController
@RequestMapping("/account-statement")
public class AccountStatementController {

    private final AccountStatementService accountStatementService;

    @Autowired
    public AccountStatementController(AccountStatementService accountStatementService) {
        this.accountStatementService = accountStatementService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<AccountStatement> fetchAccountStatement(HttpServletRequest request) {
        return retrieveUserName(request)
                .map(accountStatementService::fetchLastSixMonthsAccountStatement)
                .orElse(emptyList());
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<AccountStatement> fetchAllAccountStatement(HttpServletRequest request) {
        return retrieveUserName(request)
                .map(accountStatementService::fetchAllAccountStatement)
                .orElse(emptyList());
    }

    public Optional<String> retrieveUserName(HttpServletRequest request) {
        if (request.getUserPrincipal() instanceof Authentication) {
            Authentication authentication = (Authentication) request.getUserPrincipal();
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return of(userDetails.getUsername());
            }
        }
        return empty();
    }
}
