package com.hcdl.sales;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MonthlyBalanceApplication {

    private static final Logger logger = LoggerFactory.getLogger(MonthlyBalanceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MonthlyBalanceApplication.class, args);
        logger.info(getSuccessMessage());
    }

    private static String getSuccessMessage() {
        return "\n #################################################################"
                + "\n # Monthly balance application started successfully ......."
                + "\n #################################################################";
    }
}

