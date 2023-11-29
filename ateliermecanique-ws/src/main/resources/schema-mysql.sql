USE `ateliermecanique-db`;

create table if not exists customers(
                            id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                            customer_account_id VARCHAR(36),

    customer_first_name VARCHAR(36),
    customer_last_name VARCHAR(36),
    customer_email VARCHAR(36),
    customer_phone_number VARCHAR(36),
    customer_password CHAR(60) -- for BCrypt hashing
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


