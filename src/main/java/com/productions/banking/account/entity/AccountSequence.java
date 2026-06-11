package com.productions.banking.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account_sequences")
@Getter
@Setter
public class AccountSequence {

    @Id
    private Long id;

    @Column(nullable = false)
    private Long nextValue;
}