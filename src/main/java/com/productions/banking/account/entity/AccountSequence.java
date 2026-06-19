package com.productions.banking.account.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account_sequences")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountSequence {

    @Id
    private Long id;

    @Column(nullable = false)
    private Long nextValue;
}