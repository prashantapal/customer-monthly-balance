package com.hcdl.sales.model.domain;

import com.hcdl.sales.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransaction {

    private long transactionId;
    private String transactionDescription;
    private LocalDateTime transactionDate;
    private BigDecimal transactionAmount;
    private TransactionType transactionType;
}
