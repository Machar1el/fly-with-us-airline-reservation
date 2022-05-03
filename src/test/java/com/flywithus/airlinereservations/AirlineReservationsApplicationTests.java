package com.flywithus.airlinereservations;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AirlineReservationsApplicationTests {

	@Test
	@DisplayName("Context loads")
	void contextLoads(ApplicationContext context) {
		Assertions.assertThat(context).isNotNull();
	}

}
