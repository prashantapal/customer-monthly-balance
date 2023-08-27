package com.hcdl.service;

import com.hcdl.sales.model.Transaction;
import com.hcdl.sales.model.TransactionType;
import com.hcdl.sales.model.domain.AccountStatement;
import com.hcdl.sales.model.domain.AccountTransaction;
import com.hcdl.sales.repository.TransactionRepository;
import com.hcdl.sales.service.AccountStatementConverter;
import com.hcdl.sales.service.AccountStatementService;
import com.hcdl.sales.service.AccountStatementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.hcdl.sales.model.TransactionType.CREDIT;
import static com.hcdl.sales.model.TransactionType.DEBIT;
import static java.time.LocalDateTime.now;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AccountStatementServiceImplTest {

    private TransactionRepository transactionRepository;
    private AccountStatementConverter converter;
    private AccountStatementService accountStatementService;

    @BeforeEach
    public void setup() {
        transactionRepository = mock(TransactionRepository.class);
        converter = new AccountStatementConverter();
        accountStatementService = new AccountStatementServiceImpl(transactionRepository, converter);
    }

    @Test
    public void shouldReturnLastSixMonthsAccountStatements() {
        given(transactionRepository.findTransactionsAfterDate(any(LocalDateTime.class), any(Sort.class)))
                .willReturn(createLastSixMonthsTransactions());
        List<AccountStatement> accountStatements = accountStatementService.fetchLastSixMonthsAccountStatement();
        assertThat(accountStatements).hasSize(1);
        assertThat(accountStatements).satisfiesExactly(accountStatement -> assertAccountStatement(accountStatement,
                LocalDate.now().with(firstDayOfMonth()), 3, new BigDecimal(400),
                new BigDecimal(300), new BigDecimal(100), new BigDecimal(100))
        );
        assertAccountTransaction(accountStatements.get(0).getTransactions().get(0), 1L, "test_description_1", new BigDecimal(100), CREDIT);
        assertAccountTransaction(accountStatements.get(0).getTransactions().get(1), 2L, "test_description_2", new BigDecimal(300), DEBIT);
        assertAccountTransaction(accountStatements.get(0).getTransactions().get(2), 3L, "test_description_3", new BigDecimal(300), CREDIT);
    }

    @Test
    public void shouldReturnAllAccountStatements() {
        given(transactionRepository.findAll(any(Sort.class)))
                .willReturn(createAllTransactions());
        List<AccountStatement> accountStatements = accountStatementService.fetchAllAccountStatement();
        assertThat(accountStatements).hasSize(3);
        assertThat(accountStatements).satisfiesExactly(accountStatement1 -> assertAccountStatement(accountStatement1,
                        LocalDate.now().with(firstDayOfMonth()), 3, new BigDecimal(400),
                        new BigDecimal(300), new BigDecimal(100), new BigDecimal(100)),

                accountStatement2 -> assertAccountStatement(accountStatement2,
                        LocalDate.now().minusYears(1).with(firstDayOfMonth()), 3, new BigDecimal(550),
                        new BigDecimal(800), new BigDecimal(250).negate(), new BigDecimal(150).negate()),

                accountStatement3 -> assertAccountStatement(accountStatement3,
                        LocalDate.now().minusYears(3).with(firstDayOfMonth()), 1, new BigDecimal(300),
                        BigDecimal.ZERO, new BigDecimal(300), new BigDecimal(150))
        );
        assertAccountTransaction(accountStatements.get(0).getTransactions().get(0), 1L, "test_description_1", new BigDecimal(100), CREDIT);
        assertAccountTransaction(accountStatements.get(0).getTransactions().get(1), 2L, "test_description_2", new BigDecimal(300), DEBIT);
        assertAccountTransaction(accountStatements.get(0).getTransactions().get(2), 3L, "test_description_3", new BigDecimal(300), CREDIT);
    }

    private void assertAccountStatement(AccountStatement actualStatement, LocalDate expectedDate, int expectedTransactionCount,
                                        BigDecimal expectedTotalCredit, BigDecimal expectedTotalDebit,
                                        BigDecimal expectedTotalBalance, BigDecimal expectedCumulativeBalance) {
        assertAll(
                "Assert account statement",
                () -> assertThat(actualStatement.getMonth()).isEqualTo(expectedDate),
                () -> assertThat(actualStatement.getTransactions()).hasSize(expectedTransactionCount),
                () -> assertThat(actualStatement.getTotalCredit()).isEqualTo(expectedTotalCredit),
                () -> assertThat(actualStatement.getTotalDebit()).isEqualTo(expectedTotalDebit),
                () -> assertThat(actualStatement.getBalance()).isEqualTo(expectedTotalBalance),
                () -> assertThat(actualStatement.getCumulativeBalance()).isEqualTo(expectedCumulativeBalance)
        );
    }

    private void assertAccountTransaction(AccountTransaction actualTransaction, long expectedId, String expectedDescription,
                                          BigDecimal expectedAmount, TransactionType expectedType) {
        assertAll(
                "Assert account transaction",
                () -> assertThat(actualTransaction.getTransactionId()).isEqualTo(expectedId),
                () -> assertThat(actualTransaction.getTransactionDescription()).isEqualTo(expectedDescription),
                () -> assertThat(actualTransaction.getTransactionAmount()).isEqualTo(expectedAmount),
                () -> assertThat(actualTransaction.getTransactionType()).isEqualTo(expectedType)
        );
    }

    private List<Transaction> createLastSixMonthsTransactions() {
        return asList(
                new Transaction(1L, "test_description_1", now(), new BigDecimal(100), CREDIT),
                new Transaction(2L, "test_description_2", now(), new BigDecimal(300), DEBIT),
                new Transaction(3L, "test_description_3", now(), new BigDecimal(300), CREDIT)
        );
    }

    private List<Transaction> createAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(createLastSixMonthsTransactions());
        transactions.addAll(asList(
                new Transaction(4L, "test_description_3", now().minusYears(1), new BigDecimal(550), CREDIT),
                new Transaction(5L, "test_description_3", now().minusYears(1), new BigDecimal(300), DEBIT),
                new Transaction(6L, "test_description_3", now().minusYears(1), new BigDecimal(500), DEBIT),
                new Transaction(7L, "test_description_3", now().minusYears(3), new BigDecimal(300), CREDIT)
        ));
        return transactions;
    }
}
