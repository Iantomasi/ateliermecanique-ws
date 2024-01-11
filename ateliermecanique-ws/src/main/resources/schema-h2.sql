USE `ateliermecanique-db`;


create table if not exists vehicles(
                                       id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       vehicle_id VARCHAR(36),
    user_id VARCHAR(36),

    make VARCHAR(255),
    model VARCHAR(255),
    year VARCHAR(5),
    transmission_type VARCHAR(15),
    mileage VARCHAR(10)
    );


create table if not exists appointments(
                                           id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                           appointment_id VARCHAR(36),
    user_id VARCHAR(36),
    vehicle_id VARCHAR(36),
    appointment_date DATETIME,
    services VARCHAR(255),
    comments VARCHAR(255),
    status VARCHAR(36)
    );


create table if not exists invoices(
                                       id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       invoice_id VARCHAR(36),
    appointment_id VARCHAR(36),

    user_id VARCHAR(36),
    invoice_datetime DATETIME,
    invoice_mechanicnotes VARCHAR(255),
    invoice_sumofservices DECIMAL(15,2)
    );

CREATE TABLE if not exists users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     user_id VARCHAR(36),
    email VARCHAR(50) NOT NULL,
    password VARCHAR(120) NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(40) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    UNIQUE (email)
    );

CREATE TABLE if not exists roles (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(20) NOT NULL
    );

CREATE TABLE if not exists user_roles (
                                          user_id INT,
                                          role_id INT,
                                          PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
    );


