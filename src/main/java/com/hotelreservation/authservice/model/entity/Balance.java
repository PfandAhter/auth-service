package com.hotelreservation.authservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "balance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user.userId")
    private User user;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "moneyCode")
    private String moneyCode;
}
