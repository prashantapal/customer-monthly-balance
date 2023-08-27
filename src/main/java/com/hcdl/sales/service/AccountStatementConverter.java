package com.hcdl.sales.service;

import com.hcdl.sales.model.Transaction;
import com.hcdl.sales.model.domain.AccountStatement;
import com.hcdl.sales.model.domain.AccountTransaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.hcdl.sales.model.TransactionType.CREDIT;
import static com.hcdl.sales.model.TransactionType.DEBIT;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Component
public class AccountStatementConverter {

    public List<AccountStatement> convert(List<Transaction> transactions) {
        List<AccountTransaction> accountTransactions = transactions.stream()
                .map(this::toAccountTransaction)
                .collect(toList());
        LinkedHashMap<LocalDate, List<AccountTransaction>> monthlyGroupedTransaction = groupByMonth(accountTransactions);
        return  monthlyAccountStatement(monthlyGroupedTransaction);
    }

    private AccountTransaction toAccountTransaction(Transaction transaction) {
        return new AccountTransaction(transaction.getId(),
                transaction.getDescription(),
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getType());
    }

    //Group the transactions per month and used LinkedHashMap to maintain the order
    private LinkedHashMap<LocalDate, List<AccountTransaction>> groupByMonth(List<AccountTransaction> accountTransactions) {
        return accountTransactions.stream().
                collect(groupingBy(accountTransaction -> accountTransaction.getTransactionDate().toLocalDate(),
                        LinkedHashMap::new,
                        toList()));
    }

    private List<AccountStatement> monthlyAccountStatement(LinkedHashMap<LocalDate, List<AccountTransaction>> monthlyGroupedTransaction) {
        List<AccountStatement> accountStatements = new ArrayList<>();
        BigDecimal cumulativeBalance = new BigDecimal(0);
        // The simple for loop maintains the stateful operation of the cumulative balance
        for (Map.Entry<LocalDate, List<AccountTransaction>> entry : monthlyGroupedTransaction.entrySet()) {
            accountStatements.add(getAccountStatement(entry.getKey(), entry.getValue(), cumulativeBalance));
        }
        return accountStatements;
    }

    private AccountStatement getAccountStatement(LocalDate transactionMonth, List<AccountTransaction> accountTransactions, BigDecimal cumulativeBalance) {
        final BigDecimal monthlyCredit = totalMonthlyCredit(accountTransactions);
        final BigDecimal monthlyDebit = totalMonthlyDebit(accountTransactions);
        final BigDecimal monthlyBalance = monthlyCredit.subtract(monthlyDebit);
        return new AccountStatement(transactionMonth, accountTransactions, monthlyCredit,
                monthlyDebit, monthlyBalance,
                cumulativeBalance.add(monthlyBalance));
    }

    private BigDecimal totalMonthlyCredit(List<AccountTransaction> accountTransactions) {
        return accountTransactions.stream()
                .filter(accountTransaction -> CREDIT == accountTransaction.getTransactionType())
                .map(AccountTransaction::getTransactionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal totalMonthlyDebit(List<AccountTransaction> accountTransactions) {
        return accountTransactions.stream()
                .filter(accountTransaction -> DEBIT == accountTransaction.getTransactionType())
                .map(AccountTransaction::getTransactionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
