CREATE TABLE Users
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    last_name VARCHAR(256) NOT NULL,
    patronymic VARCHAR(256) NOT NULL,
    arrival_date timestamp NOT NULL,
    flight_number VARCHAR(256) NOT NULL,
    phone_number VARCHAR(256) NOT NULL,
    email VARCHAR(256),
    telegram_login VARCHAR(256),
    trip_comment VARCHAR(512)
)