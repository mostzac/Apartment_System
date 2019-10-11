CREATE SEQUENCE apartment_id_seq START WITH 1;
CREATE SEQUENCE resident_id_seq START WITH 1;
CREATE SEQUENCE user_id_seq START WITH 1;


CREATE TABLE apartment (
    id                INTEGER NOT NULL default nextval('apartment_id_seq'),
    name              VARCHAR(30) not null unique,
    address       VARCHAR(150),
);

ALTER TABLE apartment ADD CONSTRAINT apartment_pk PRIMARY KEY ( id );

CREATE TABLE resident (
    id              INTEGER NOT NULL default nextval('resident_id_seq'),
    name            VARCHAR(30) not null unique,
    room      VARCHAR(30),
    apartment_id   INTEGER NOT NULL
);

ALTER TABLE resident ADD CONSTRAINT resident_pk PRIMARY KEY ( id );

CREATE TABLE user (
    id             INTEGER NOT NULL default nextval('user_id_seq'),
    name   VARCHAR(30) unique,
    password        NUMERIC(10, 2),
    resident_id    INTEGER
);

ALTER TABLE user ADD CONSTRAINT user_pk PRIMARY KEY ( id );

ALTER TABLE user
    ADD CONSTRAINT user_resident_fk FOREIGN KEY ( resident_id )
        REFERENCES resident ( id );

ALTER TABLE resident
    ADD CONSTRAINT resident_apartment_fk FOREIGN KEY ( apartment_id )
        REFERENCES apartment ( id );
