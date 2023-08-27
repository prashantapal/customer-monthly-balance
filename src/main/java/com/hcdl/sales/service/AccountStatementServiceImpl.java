package com.hcdl.sales.service;

import com.hcdl.sales.model.Transaction;
import com.hcdl.sales.model.domain.AccountStatement;
import com.hcdl.sales.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.hcdl.sales.model.Transaction.sortById;
import static java.time.LocalDateTime.now;
import static java.time.LocalTime.MIDNIGHT;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;

@Service
@Transactional(readOnly = true)
public class AccountStatementServiceImpl implements AccountStatementService {

    private final TransactionRepository transactionRepository;
    private final AccountStatementConverter converter;

    private static final Logger logger = LoggerFactory.getLogger(AccountStatementServiceImpl.class);

    @Autowired
    public AccountStatementServiceImpl(TransactionRepository transactionRepository, AccountStatementConverter converter) {
        this.transactionRepository = transactionRepository;
        this.converter = converter;
    }

    /**
     * This method has been implemented to return last six months
     * account statement. This is specially designed to keep dataset
     * limited in the UI once customer opens the page. It will increase
     * the customer experience as scrolling up and down will not be
     * needed that much.
     * (The date range is starting from the first day of the month(i.e. current date - 6 months) to till date)
     */
    @Override
    public List<AccountStatement> fetchLastSixMonthsAccountStatement() {
        final LocalDateTime startDate = firstDayOfMonthBefore(6);
        List<Transaction> transactions = transactionRepository.findTransactionsAfterDate(startDate, sortById());
        return converter.convert(transactions);
    }

    /**
     * This method returns entire account statement present so far.
     * The UI for this method is out of scope. This is just for testing
     * purpose.
     */
    @Override
    public List<AccountStatement> fetchAllAccountStatement() {
        List<Transaction> transactions = transactionRepository.findAll(sortById());
        return converter.convert(transactions);
    }

    private LocalDateTime firstDayOfMonthBefore(int month) {
        return now().minusMonths(month).with(firstDayOfMonth()).toLocalDate().atTime(MIDNIGHT);
    }
}
