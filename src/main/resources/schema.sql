DROP TABLE IF EXISTS TRANSACTION;
CREATE TABLE TRANSACTION(
    TRANSACTION_ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 100000) NOT NULL,
    DESCRIPTION VARCHAR(255) NOT NULL,
    TRANSACTION_DATE TIMESTAMP,
    TRANSACTION_AMOUNT NUMBER(10,2) NOT NULL,
    TRANSACTION_TYPE ENUM('CREDIT', 'DEBIT') NOT NULL DEFAULT 'CREDIT',
    PRIMARY KEY (TRANSACTION_ID)
);
CREATE INDEX transaction_date_idx ON TRANSACTION(TRANSACTION_DATE);