

CREATE TABLE apartment (
    id                SERIAL NOT NULL,
    name              VARCHAR(30) not null unique,
    address       VARCHAR(150)
);

ALTER TABLE apartment ADD CONSTRAINT apartment_pk PRIMARY KEY ( id );

CREATE TABLE resident (
    id              SERIAL NOT NULL,
    name            VARCHAR(30) not null unique,
    room      VARCHAR(30),
    apartment_id   INTEGER NOT NULL
);

ALTER TABLE resident ADD CONSTRAINT resident_pk PRIMARY KEY ( id );


CREATE TABLE users (
    id             SERIAL NOT NULL,
    name   VARCHAR(30) not null unique,
    password         VARCHAR(30),
    resident_id    INTEGER
);

ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY ( id );

ALTER TABLE users
    ADD CONSTRAINT users_resident_fk FOREIGN KEY ( resident_id )
        REFERENCES resident ( id );

ALTER TABLE resident
    ADD CONSTRAINT resident_apartment_fk FOREIGN KEY ( apartment_id )
        REFERENCES apartment ( id );
