

CREATE TABLE apartments (
    id                SERIAL NOT NULL,
    name              VARCHAR(30) not null unique,
    address       VARCHAR(150)
);

ALTER TABLE apartments ADD CONSTRAINT apartments_pk PRIMARY KEY ( id );


CREATE TABLE users (
    id             SERIAL NOT NULL,
    account   VARCHAR(30) not null unique,
    password         VARCHAR(30),
    name VARCHAR(30),
    room VARCHAR(30),
    apartmentId    INTEGER
);

ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY ( id );

CREATE TABLE packages(
    id             SERIAL NOT NULL,
    shipNumber VARCHAR(200),
    shipper VARCHAR(50),
    deliveredDate TIMESTAMP not null,
    description   VARCHAR(200),
    status   INTEGER ,
    arrangeDate TIMESTAMP,
    notes VARCHAR(200),
    userId INTEGER
);

ALTER TABLE packages ADD CONSTRAINT packages_pk PRIMARY KEY (id);

ALTER TABLE users
    ADD CONSTRAINT user_apartment_fk FOREIGN KEY ( apartmentId )
        REFERENCES users ( id );

ALTER TABLE packages
    ADD CONSTRAINT package_user_fk FOREIGN KEY ( userId )
        REFERENCES users ( id );
