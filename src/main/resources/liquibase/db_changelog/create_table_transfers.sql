CREATE TABLE Transfers
(
    id BIGSERIAL PRIMARY KEY,
    trip_date timestamp,
    start_place VARCHAR(256),
    end_place VARCHAR(256),
    adults_amount INTEGER,
    children_amount INTEGER ,
    auto_type varchar(256)
)