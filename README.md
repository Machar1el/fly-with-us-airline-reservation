# AirlineReservations, a FlyWithUs product !

## Features

* A simple User CRU(D), complete with input validation and exceptions
* An embedded H2 DB with a couple seed users
* A MonitoringAspect, with method calls duration and args/return values logging
* A Swagger UI
* Proper code coverage
* Integration tests

## Usage

App is live on Heroku, check it out

https://fwu-airline-reservation.herokuapp.com/

Swagger-UI is available freely here

https://fwu-airline-reservation.herokuapp.com/api/swagger-ui/index.html#/

Use the postman collection in the postman directory as you see fit (however there is no delete option, so every action is permanent until I redeploy the app)

## Identified short-comings

- No security concerns except for input validation as it probably isn't in the scope of the test

No auth mechanism, every user has access to the same resources

- Not enough WebLayer tests/No Controller tests

I figured Service tests coverage and a few weblayer tests would be enough for the scope of the test

- Probably needs some fine-tuning here and there, in terms of functionnality

- I am not very familiar with weblayer/integration tests and probably got the runner configuration wrong

- The H2 DB is seeded by a data.sql file. Should probably switch to Liquidbase for more serious work.