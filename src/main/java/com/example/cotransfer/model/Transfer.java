package com.example.cotransfer.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
@NoArgsConstructor
@Getter
@Setter
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private LocalDateTime tripDate;

    private String startPlace;

    private String endPlace;

    private Integer adultsAmount;

    private Integer childrenAmount;

    @OneToOne(fetch = FetchType.LAZY)
    private Auto auto;
}
