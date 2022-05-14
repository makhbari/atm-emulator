package com.asanpardakht.atmemulator.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION", indexes = {@Index(name = "TRANS_PAN_IDX", columnList = "pan")})
@Getter
@Setter
public class Transaction {
    private static final String SEQUENCE_NAME = "TRANSACTION_SEQ";

    @Id
    @GeneratedValue(generator = SEQUENCE_NAME)
    @GenericGenerator(name = SEQUENCE_NAME, strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = SEQUENCE_NAME),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;

    @Column(nullable = false)
    private String pan;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    private Long value;

    @Column(nullable = false, unique = true)
    private String referenceNumber;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime dateTime;

    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL
    }

}
