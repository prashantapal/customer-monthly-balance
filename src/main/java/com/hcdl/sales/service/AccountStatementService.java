package com.hcdl.sales.service;

import com.hcdl.sales.model.domain.AccountStatement;

import java.util.List;

public interface AccountStatementService {

    List<AccountStatement> fetchLastSixMonthsAccountStatement();
    List<AccountStatement> fetchAllAccountStatement();
}
