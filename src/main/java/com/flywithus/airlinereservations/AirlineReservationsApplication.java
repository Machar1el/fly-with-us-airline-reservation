package com.flywithus.airlinereservations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@OpenAPIDefinition(info =
@Info(title = "Airline Reservation API", description = "Documentation Airline Reservation API v1.0")
)
public class AirlineReservationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlineReservationsApplication.class, args);
	}
}
