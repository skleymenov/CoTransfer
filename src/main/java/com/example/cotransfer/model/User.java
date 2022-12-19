package com.example.cotransfer.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "arrival_date")
    private String arrivalDate;

    @Column(name = "flight_number")
    private  String flightNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "passport")
    private String passport;

    @Column(name = "telegram_login")
    private String telegramLogin;

    @Column(name = "trip_comment")
    private String tripComment;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "transfer_id")
    private Transfer transfer;
}
