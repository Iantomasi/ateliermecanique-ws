USE `ateliermecanique-db`;

create table if not exists customers(
                                        id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                        customer_id VARCHAR(36),
    first_name VARCHAR(36),
    last_name VARCHAR(36),
    username VARCHAR(36),
    email VARCHAR(36),
    phone_number VARCHAR(36),
    token VARCHAR(5000),
    password CHAR(60), -- for BCrypt hashing
    role VARCHAR (10)
);

create table if not exists vehicles(
                                        id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                        vehicle_id VARCHAR(36),
                                        customer_id VARCHAR(36),

    make VARCHAR(255),
    model VARCHAR(255),
    year VARCHAR(5),
    transmission_type VARCHAR(15),
    mileage VARCHAR(10)
);


create table if not exists appointments(
                                           id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                           appointment_id VARCHAR(36),
                                           customer_id VARCHAR(36),
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

    customer_name VARCHAR(36),
    invoice_datetime DATETIME,
    invoice_mechanicnotes VARCHAR(255),
    invoice_sumofservices DECIMAL(15,2)
);


