package com.example.cotransfer.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "trip_date")
    private String tripDate;

    @Column(name = "start_place")
    private String startPlace;

    @Column(name = "end_place")
    private String endPlace;

    @Column(name = "adults_amount")
    private Integer adultsAmount;

    @Column(name = "children_amount")
    private Integer childrenAmount;

    @Column(name = "auto_type")
    private String autoType;

    @JsonManagedReference
    @OneToMany(mappedBy = "transfer", cascade = CascadeType.ALL)
    private List<User> users;
}
