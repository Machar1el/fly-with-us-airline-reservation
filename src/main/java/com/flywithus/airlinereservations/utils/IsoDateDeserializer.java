package com.flywithus.airlinereservations.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.flywithus.airlinereservations.exception.user.exception.UserServiceException;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class IsoDateDeserializer extends JsonDeserializer<LocalDate> {

    @SneakyThrows
    @Override
    public LocalDate deserialize(JsonParser jsonParser,
                                 DeserializationContext deserializationContext) {

        try {
            return LocalDate.parse(jsonParser.getText(), DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new UserServiceException(null, UserServiceException.PROVIDED_DATE_MUST_MATCH_PATTERN_YYYY_MM_DD);
        }
    }
}
