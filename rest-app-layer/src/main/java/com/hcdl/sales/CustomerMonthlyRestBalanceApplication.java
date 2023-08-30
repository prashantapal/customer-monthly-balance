package com.hcdl.sales;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerMonthlyRestBalanceApplication {

    private static final Logger logger = LoggerFactory.getLogger(CustomerMonthlyRestBalanceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CustomerMonthlyRestBalanceApplication.class, args);
        logger.info(getSuccessMessage());
    }

    private static String getSuccessMessage() {
        return "\n #############################################################################"
                + "\n # Customer monthly balance rest application started successfully ......."
                + "\n #############################################################################";
    }
}

