# AirlineReservations, a FlyWithUs product !

## Features

* A simple User CRU(D), complete with input validation and exceptions
* An embedded H2 DB with a couple seed users
* A MonitoringAspect, with method calls duration and args/return values logging
* A Swagger UI
* Proper code coverage

## Usage

App is live on Heroku, check it out

https://fwu-airline-reservation.herokuapp.com/

Swagger-UI is available freely here

https://fwu-airline-reservation.herokuapp.com/api/swagger-ui/index.html#/

## Identified short-comings

- No security concerns except for input validation as it probably isn't in the scope of the test

No auth mechanism, every user has access to the same resources

- No Controller/WebLayer tests

I figured Service tests would be enough for the scope of the test

- The entity class is also the DTO

In a real-life situation, I would have used a DTO and a mapper.
This example is simple enough to not absolutely need it for it to be functional

- Service is a concrete class and not an implementation

I figured it would bring no value in the scenario

- The H2 DB is seeded by a data.sql file. Should probably switch to Liquidbase for more serious work.