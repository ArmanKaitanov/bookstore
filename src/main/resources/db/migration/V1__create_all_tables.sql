-- Create users table
CREATE TABLE users
(
    id         UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    is_active  BOOLEAN      NOT NULL
);

-- Create the books table
CREATE TABLE books
(
    id                 UUID PRIMARY KEY,
    title              VARCHAR(255)   NOT NULL,
    author             VARCHAR(255),
    genre              VARCHAR(50)    NOT NULL,
    price              DECIMAL(10, 2) NOT NULL CHECK (price > 0),
    quantity_available INT            NOT NULL CHECK (quantity_available >= 0)
);