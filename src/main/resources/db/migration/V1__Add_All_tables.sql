

CREATE TABLE apartments (
    id                SERIAL NOT NULL,
    name              VARCHAR(30) not null unique,
    address       VARCHAR(150) not null
);

ALTER TABLE apartments ADD CONSTRAINT apartments_pk PRIMARY KEY ( id );


CREATE TABLE users (
    id             SERIAL NOT NULL,
    account   VARCHAR(30) not null unique,
    password         VARCHAR(30) not null,
    name VARCHAR(30) not null,
    room VARCHAR(30) not null,
    apartmentId    INTEGER not null
);

ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY ( id );

CREATE TABLE packages(
    id             SERIAL NOT NULL,
    shipNumber VARCHAR(200) not null unique,
    shipper VARCHAR(50) not null,
    deliveredDate TIMESTAMP not null,
    description   VARCHAR(200),
    status   INTEGER not null,
    arrangeDate TIMESTAMP,
    notes VARCHAR(200),
    userId INTEGER not null
);

ALTER TABLE packages ADD CONSTRAINT packages_pk PRIMARY KEY (id);

ALTER TABLE users
    ADD CONSTRAINT user_apartment_fk FOREIGN KEY ( apartmentId )
        REFERENCES users ( id );

ALTER TABLE packages
    ADD CONSTRAINT package_user_fk FOREIGN KEY ( userId )
        REFERENCES users ( id );
