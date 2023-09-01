package com.hcdl.sales;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerMonthlyBalanceRestApplication {

    private static final Logger logger = LoggerFactory.getLogger(CustomerMonthlyBalanceRestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CustomerMonthlyBalanceRestApplication.class, args);
        logger.info(getSuccessMessage());
    }

    private static String getSuccessMessage() {
        return "\n #############################################################################"
                + "\n # Customer monthly balance rest application started successfully ......."
                + "\n #############################################################################";
    }
}

