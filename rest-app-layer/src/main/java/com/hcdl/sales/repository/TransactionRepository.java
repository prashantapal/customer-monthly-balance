package com.hcdl.sales.repository;

import com.hcdl.sales.model.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select t from Transaction t where t.date >= :transactionDate")
    List<Transaction> findTransactionsAfterDate(@Param("transactionDate") LocalDateTime transactionDate, Sort sort);
}
