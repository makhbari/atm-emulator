package com.asanpardakht.atmemulator.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "CARD")
@Getter
@Setter
public class Card {
    private static final String SEQUENCE_NAME = "CARD_SEQ";

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

    @Column(unique = true, nullable = false)
    private String pan;

    @Column(nullable = false)
    private String pin;

    @ManyToOne(targetEntity = Customer.class, optional = false)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @Column(nullable = false)
    private String expDate;

    @Column(nullable = false)
    private String cvv2;

    @Column(nullable = false)
    private String depositNumber;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Enumerated(EnumType.STRING)
    private CardType type;

    public enum CardStatus {OK, BLOCKED}

    public enum CardType {CREDIT, DEBIT, GIFT}


}
