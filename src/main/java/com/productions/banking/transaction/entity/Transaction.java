package com.productions.banking.transaction.entity;

import com.productions.banking.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fromAccountNumber;

    @Column(nullable = false)
    private String toAccountNumber;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal sourceBalanceBefore;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal sourceBalanceAfter;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal destinationBalanceBefore;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal destinationBalanceAfter;
}