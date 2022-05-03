package com.flywithus.airlinereservations.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.flywithus.airlinereservations.utils.IsoDateDeserializer;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    @Column(nullable = false, name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = IsoDateDeserializer.class)
    private LocalDate birthDate;

    @Column(nullable = false, length = 3)
    private String country;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String gender;

    @Override
    public String toString() {
        return "ID : " + getId() + " / Username : " + getUsername();
    }
}
