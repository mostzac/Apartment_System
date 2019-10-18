

CREATE TABLE apartments (
    id                SERIAL NOT NULL,
    name              VARCHAR(30) not null unique,
    address       VARCHAR(150)
);

ALTER TABLE apartments ADD CONSTRAINT apartment_pk PRIMARY KEY ( id );

CREATE TABLE residents (
    id              SERIAL NOT NULL,
    name            VARCHAR(30) not null unique,
    room      VARCHAR(30),
    apartmentId   INTEGER NOT NULL
);

ALTER TABLE residents ADD CONSTRAINT resident_pk PRIMARY KEY ( id );


CREATE TABLE users (
    id             SERIAL NOT NULL,
    name   VARCHAR(30) not null unique,
    password         VARCHAR(30),
    residentId    INTEGER
);

ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY ( id );

ALTER TABLE users
    ADD CONSTRAINT users_resident_fk FOREIGN KEY ( residentId )
        REFERENCES residents ( id );

ALTER TABLE residents
    ADD CONSTRAINT resident_apartment_fk FOREIGN KEY ( apartmentId )
        REFERENCES apartments ( id );
