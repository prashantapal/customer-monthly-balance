package com.hcdl.sales.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatement {

    private LocalDate month;
    private List<AccountTransaction> transactions;
    private BigDecimal totalCredit;
    private BigDecimal totalDebit;
    private BigDecimal balance;
    private BigDecimal cumulativeBalance;
}
