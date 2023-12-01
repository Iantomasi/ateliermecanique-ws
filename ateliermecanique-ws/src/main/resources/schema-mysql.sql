USE `ateliermecanique-db`;

create table if not exists customers(
                            id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                            customer_id VARCHAR(36),

    customer_first_name VARCHAR(36),
    customer_last_name VARCHAR(36),
    customer_username VARCHAR(36),
    customer_email VARCHAR(36),
    customer_phone_number VARCHAR(36),
    customer_password CHAR(60) -- for BCrypt hashing
);

-- CREATE TABLE IF NOT EXISTS users (
--                                      id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
--
--                                      username VARCHAR(50) NOT NULL,
--     password VARCHAR(100) NOT NULL,
--     enabled BOOLEAN NOT NULL DEFAULT TRUE
--     );

-- Roles table
CREATE TABLE IF NOT EXISTS roles (
                                     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(50) NOT NULL
    );

-- Users_roles join table
CREATE TABLE IF NOT EXISTS users_roles (
                                           user_id INT NOT NULL,
                                           role_id INT NOT NULL,
                                           PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
    );

create table if not exists vehicles(
                                        id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                        vehicle_id VARCHAR(36),
                                        customer_id VARCHAR(36),

    vehicle_makeandmodel VARCHAR(255),
    vehicle_year VARCHAR(36),
    vehicle_transmission VARCHAR(36),
    vehicle_mileage VARCHAR(36)
);


create table if not exists appointments(
                                       id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       appointment_id VARCHAR(36),
                                       vehicle_id VARCHAR(36),
    appointment_datetime DATETIME,
    appointment_services VARCHAR(255),
    appointment_comments VARCHAR(255)
);


create table if not exists invoices(
                                           id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                           invoice_id VARCHAR(36),
                                           appointment_id VARCHAR(36),

    customer_name VARCHAR(36),
    invoice_datetime DATETIME,
    invoice_mechanicnotes VARCHAR(255),
    invoice_sumofservices DECIMAL(15,2)
);


