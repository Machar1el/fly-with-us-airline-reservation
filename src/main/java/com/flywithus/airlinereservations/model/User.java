package com.flywithus.airlinereservations.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, length = 3)
    private String country;

    @Column(name = "phone_number", length = 25)
    private String phoneNumber;

    @Column(length = 1)
    private String gender;

    @Override
    public String toString() {
        return "ID : " + getId() + " / Username : " + getUsername();
    }
}
