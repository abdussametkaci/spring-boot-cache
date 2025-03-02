CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE category
(
    id    UUID         NOT NULL,
    name  VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);