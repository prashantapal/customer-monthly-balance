package com.hcdl.controller;

import com.hcdl.sales.MonthlyBalanceApplication;
import com.hcdl.sales.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MonthlyBalanceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql({"/schema.sql", "/data.sql"})
public class AccountStatementControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    private TransactionRepository repository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testShouldReturnLastSixMonthsAccountStatements() throws Exception {
        String currentMonthFirstDay = LocalDate.now().with(firstDayOfMonth()).toString();
        String lastMonthFirstDay = LocalDate.now().minusMonths(3).with(firstDayOfMonth()).toString();
        mockMvc.perform(get("/account-statement"))
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
                .andExpect(jsonPath("$[1].cumulativeBalance").value(7000));
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
        mockMvc.perform(get("/account-statement/all"))
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
                .andExpect(jsonPath("$[2].cumulativeBalance").value(7000));
    }
}
