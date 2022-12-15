package com.example.cotransfer.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "auto")
@NoArgsConstructor
@Getter
@Setter
public class Auto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "auto_type")
    private String autoType;

    @Column(name = "amount_of_places")
    private Integer amountOfPlaces;

    @OneToOne(fetch = FetchType.LAZY)
    private Transfer transfer;
}
