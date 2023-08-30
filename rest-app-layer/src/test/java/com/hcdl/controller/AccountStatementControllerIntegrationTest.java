package com.hcdl.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcdl.sales.CustomerMonthlyRestBalanceApplication;
import com.hcdl.sales.model.TransactionType;
import com.hcdl.sales.model.domain.AccountStatement;
import com.hcdl.sales.model.domain.AccountTransaction;
import com.hcdl.sales.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.hcdl.sales.model.TransactionType.DEBIT;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.hcdl.sales.model.TransactionType.CREDIT;

@SpringBootTest(classes = CustomerMonthlyRestBalanceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql({"/schema.sql", "/data.sql"})
public class AccountStatementControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;
    @Autowired
    private TransactionRepository repository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testShouldReturnLastSixMonthsAccountStatements() throws Exception {
        String currentMonthFirstDay = LocalDate.now().with(firstDayOfMonth()).toString();
        String lastMonthFirstDay = LocalDate.now().minusMonths(3).with(firstDayOfMonth()).toString();
        MvcResult result = mockMvc.perform(get("/account-statement"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].month").value(currentMonthFirstDay))
                .andExpect(jsonPath("$[0].transactions", hasSize(3)))
                .andExpect(jsonPath("$[0].totalCredit").value(7000))
                .andExpect(jsonPath("$[0].totalDebit").value(4000))
                .andExpect(jsonPath("$[0].balance").value(3000))
                .andExpect(jsonPath("$[0].cumulativeBalance").value(3000))
                .andExpect(jsonPath("$[1].month").value(lastMonthFirstDay))
                .andExpect(jsonPath("$[1].transactions", hasSize(3)))
                .andExpect(jsonPath("$[1].totalCredit").value(7000))
                .andExpect(jsonPath("$[1].totalDebit").value(3000))
                .andExpect(jsonPath("$[1].balance").value(4000))
                .andExpect(jsonPath("$[1].cumulativeBalance").value(7000))
                .andReturn();
        assertLastSixMonthTransactions(result);
    }

    @Test
    public void testShouldReturnEmptyAccountStatements() throws Exception {
        repository.deleteAll();
        mockMvc.perform(get("/account-statement"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    public void testShouldReturnAllAccountStatements() throws Exception {
        String currentMonthFirstDay = LocalDate.now().with(firstDayOfMonth()).toString();
        String lastMonthFirstDay = LocalDate.now().minusMonths(3).with(firstDayOfMonth()).toString();
        String beforeSevenMonthsFirstDay = LocalDate.now().minusMonths(7).with(firstDayOfMonth()).toString();
        MvcResult result = mockMvc.perform(get("/account-statement/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].month").value(currentMonthFirstDay))
                .andExpect(jsonPath("$[0].balance").value(3000))
                .andExpect(jsonPath("$[0].cumulativeBalance").value(3000))
                .andExpect(jsonPath("$[1].month").value(lastMonthFirstDay))
                .andExpect(jsonPath("$[1].balance").value(4000))
                .andExpect(jsonPath("$[1].cumulativeBalance").value(7000))
                .andExpect(jsonPath("$[2].month").value(beforeSevenMonthsFirstDay))
                .andExpect(jsonPath("$[2].balance").value(0))
                .andExpect(jsonPath("$[2].cumulativeBalance").value(7000))
                .andReturn();
        assertAllTransactions(result);
    }

    private void assertLastSixMonthTransactions(MvcResult result) throws Exception {
        List<AccountStatement> statements = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<AccountStatement>>() {});
        assertAccountTransaction(statements.get(0).getTransactions().get(0), 100L, "test_description_100", LocalDate.now(), new BigDecimal("1000.00"), DEBIT);
        assertAccountTransaction(statements.get(0).getTransactions().get(1), 101L, "test_description_101", LocalDate.now(), new BigDecimal("3000.00"), DEBIT);
        assertAccountTransaction(statements.get(0).getTransactions().get(2), 102L, "test_description_102", LocalDate.now(), new BigDecimal("7000.00"), CREDIT);
        assertAccountTransaction(statements.get(1).getTransactions().get(0), 103L, "test_description_103", LocalDate.now().minusMonths(3), new BigDecimal("7000.00"), CREDIT);
        assertAccountTransaction(statements.get(1).getTransactions().get(1), 104L, "test_description_104", LocalDate.now().minusMonths(3), new BigDecimal("1000.00"), DEBIT);
        assertAccountTransaction(statements.get(1).getTransactions().get(2), 105L, "test_description_105", LocalDate.now().minusMonths(3), new BigDecimal("2000.00"),DEBIT);

    }

    private void assertAllTransactions(MvcResult result) throws Exception {
        assertLastSixMonthTransactions(result);
        List<AccountStatement> statements = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<AccountStatement>>() {});
        assertAccountTransaction(statements.get(2).getTransactions().get(0), 106L, "test_description_106", LocalDate.now().minusMonths(7), new BigDecimal("3000.00"), DEBIT);
        assertAccountTransaction(statements.get(2).getTransactions().get(1), 107L, "test_description_107", LocalDate.now().minusMonths(7), new BigDecimal("4000.00"), DEBIT);
        assertAccountTransaction(statements.get(2).getTransactions().get(2), 108L, "test_description_108", LocalDate.now().minusMonths(7), new BigDecimal("7000.00"), CREDIT);

    }

    private void assertAccountTransaction(AccountTransaction actualTransaction, long expectedId, String expectedDescription,
                                          LocalDate expectedTransactionDate, BigDecimal expectedAmount, TransactionType expectedType) {
        assertAll(
                "Assert account transaction",
                () -> assertThat(actualTransaction.getTransactionId()).isEqualTo(expectedId),
                () -> assertThat(actualTransaction.getTransactionDescription()).isEqualTo(expectedDescription),
                () -> assertThat(actualTransaction.getTransactionDate().toLocalDate()).isEqualTo(expectedTransactionDate),
                () -> assertThat(actualTransaction.getTransactionAmount()).isEqualTo(expectedAmount),
                () -> assertThat(actualTransaction.getTransactionType()).isEqualTo(expectedType)
        );
    }

    private void assertAccountTransaction(MvcResult result) throws UnsupportedEncodingException {
        System.out.println(result.getResponse().getContentAsString());
    }
}
