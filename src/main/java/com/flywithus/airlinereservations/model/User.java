package com.flywithus.airlinereservations.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.flywithus.airlinereservations.utils.IsoDateDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;

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

    @Schema(description = "Users' username",
            example = "maxime.rochard", required = true, minLength = 3, maxLength = 30)
    @NotBlank(message = "Username cannot be null or empty")
    @Pattern(regexp ="^\\S{3,30}", message = "Users' username should be between 3 to 30 characters long, any non-whitespace characters are accepted")
    @Column(nullable = false, length = 100)
    private String username;

    @Schema(description = "Users' birthdate",
            example = "1995-09-25", required = true)
    @NotNull(message = "Users' birthdate must be provided. Pattern : yyyy-MM-dd")
    @Past(message = "User cannot be born in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = IsoDateDeserializer.class)
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;


    @Schema(description = "Users' country code",
            example = "FRA", pattern = "^[A-Z]{3}", required = true)
    @NotBlank(message = "Users' country code must be provided (on 3 characters as per ISO 3166-1 alpha-3)")
    @Pattern(regexp ="^[A-Z]{3}", message = "Users' country code must be provided (on 3 characters as per ISO 3166-1 alpha-3)")
    @Column(nullable = false, length = 3)
    private String country;

    @Schema(description = "Users' Phone number",
            example = "+33553821621")
    @Pattern(regexp ="^\\+[0-9]{10,13}", message = "Phone number must match regex pattern +[0-9]{10,13}")
    @Column(name = "phone_number", length = 25)
    private String phoneNumber;

    @Schema(description = "Users' Gender",
            example = "M")
    @Pattern(regexp ="^[A-Z]", message = "Users' gender must be a single uppercase character")
    @Column(length = 1)
    private String gender;

    @Override
    public String toString() {
        return "ID : " + getId() + " / Username : " + getUsername();
    }
}
