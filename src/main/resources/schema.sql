-- Create the "user" table
CREATE TABLE IF NOT EXISTS customer (
    id VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Create the "account" table
CREATE TABLE  IF NOT EXISTS account (
    id VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
    balance DECIMAL(10, 2) NOT NULL,
    type VARCHAR(50) NOT NULL,
    owner VARCHAR NOT NULL,
    FOREIGN KEY (owner) REFERENCES customer(id)
);

-- Create the "transaction" table
CREATE TABLE  IF NOT EXISTS transaction (
    id VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
    owner VARCHAR NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (owner) REFERENCES customer(id)
);
