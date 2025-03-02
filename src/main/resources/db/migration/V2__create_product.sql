CREATE TABLE product
(
    id          UUID         NOT NULL,
    name        VARCHAR(255) NOT NULL,
    price       INTEGER      NOT NULL,
    category_id UUID,

    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category (id)
);