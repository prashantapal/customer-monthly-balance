package com.hcdl.sales.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION")
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TRANSACTION_ID")
    private Long id;
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    @Column(name = "TRANSACTION_DATE", nullable = false)
    private LocalDateTime date;
    @Column(name = "TRANSACTION_AMOUNT", nullable = false)
    private BigDecimal amount;
    @Column(name = "TRANSACTION_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    public static Sort sortById() {
        return Sort.by("TRANSACTION_ID").ascending();
    }
}
