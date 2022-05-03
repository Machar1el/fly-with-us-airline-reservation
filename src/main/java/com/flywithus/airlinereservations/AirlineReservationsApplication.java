package com.flywithus.airlinereservations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AirlineReservationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlineReservationsApplication.class, args);
	}
}
