package com.flywithus.airlinereservations;

import com.flywithus.airlinereservations.controller.UserController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AirlineReservationsApplicationTests {

	@Autowired
	UserController userController;

	@Test
	@DisplayName("Context loads and UserController exists")
	void contextLoads() {
		Assertions.assertThat(userController).isNotNull();
	}

}
