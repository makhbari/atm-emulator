package com.asanpardakht.atmemulator.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "CUSTOMER")
@Getter
@Setter
public class Customer implements Serializable {
    private static final String SEQUENCE_NAME = "CUSTOMER_SEQ";

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
    private String nationalCode;

    @Column(unique = true, nullable = false)
    private String customerNo;

    @Column(nullable = false)
    private String fullName;

    @Column
    private String mobile;

    private String address;

    private String email;

    private Boolean enable;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Card> cards;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
