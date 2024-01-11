
                    --------------EACH USERS PWD IS : Hello!---------------------



-- User 1 (John Doe)
INSERT INTO users (user_id, email, password, first_name, last_name, phone_number)
VALUES ('b7024d89-1a5e-4517-3gba-05178u7ar260', 'johndoe@example.com', '$2a$10$A/d6EXvgk/nd8PWGlbyUXeCv3qccFJEFKMHG/WkNye9cCychhn1lG', 'John', 'Doe', '123456789');

-- User 2 (Alice Smith)
INSERT INTO users (user_id, email, password, first_name, last_name, phone_number)
VALUES ('aebc4d79-2b6f-4527-3zda-05432x5ar321', 'alice.smith@example.com', '$2a$10$A/d6EXvgk/nd8PWGlbyUXeCv3qccFJEFKMHG/WkNye9cCychhn1lG', 'Alice', 'Smith', '987654321');

-- User 3 (Emma Johnson)
INSERT INTO users (user_id, email, password, first_name, last_name, phone_number)
VALUES ('cdff4g82-9e8h-7532-1qws-75321v5ar963', 'emma.johnson@example.com', '$2a$10$A/d6EXvgk/nd8PWGlbyUXeCv3qccFJEFKMHG/WkNye9cCychhn1lG', 'Emma', 'Johnson', '555666777');

-- User 4 (Michael Williams)
INSERT INTO users (user_id, email, password, first_name, last_name, phone_number)
VALUES ('eggh9i83-7j8k-4567-4tyu-98765z5ar741', 'michaelw@example.com', '$2a$10$A/d6EXvgk/nd8PWGlbyUXeCv3qccFJEFKMHG/WkNye9cCychhn1lG', 'Michael', 'Williams', '111222333');

-- User 5 (Sophia Brown)
INSERT INTO users (user_id, email, password, first_name, last_name, phone_number)
VALUES ('lmno8p45-3q6r-8791-2abc-96325t5ar159', 'sophia.b@example.com', '$2a$10$A/d6EXvgk/nd8PWGlbyUXeCv3qccFJEFKMHG/WkNye9cCychhn1lG', 'Sophia', 'Brown', '444555666');

-- User 6 (Oliver Garcia)
INSERT INTO users (user_id, email, password, first_name, last_name, phone_number)
VALUES ('mnop8q45-3r6s-8792-2abd-96326u6bs160', 'oliver.g@example.com', '$2a$10$A/d6EXvgk/nd8PWGlbyUXeCv3qccFJEFKMHG/WkNye9cCychhn1lG', 'Oliver', 'Garcia', '777888999');

-- User 7 (Ava Martinez)
INSERT INTO users (user_id, email, password, first_name, last_name, phone_number)
VALUES ('qrs8t45-3u6v-8793-2abe-96327v6bt161', 'ava.martinez@example.com', '$2a$10$A/d6EXvgk/nd8PWGlbyUXeCv3qccFJEFKMHG/WkNye9cCychhn1lG', 'Ava', 'Martinez', '123987456');

-- User 8 (Ethan Lopez)
INSERT INTO users (user_id, email, password, first_name, last_name, phone_number)
VALUES ('tuvw8x45-3y6z-8794-2abf-96328w6bu162', 'ethanl@example.com', '$2a$10$A/d6EXvgk/nd8PWGlbyUXeCv3qccFJEFKMHG/WkNye9cCychhn1lG', 'Ethan', 'Lopez', '654789321');

-- User 9 (Isabella Gonzalez)
INSERT INTO users (user_id, email, password, first_name, last_name, phone_number)
VALUES ('vwxy8z45-3a6b-8795-2abh-96329x6bv163', 'isabella.g@example.com', '$2a$10$A/d6EXvgk/nd8PWGlbyUXeCv3qccFJEFKMHG/WkNye9cCychhn1lG', 'Isabella', 'Gonzalez', '987654123');

-- User 10 (Mason Rodriguez)
INSERT INTO users (user_id, email, password, first_name, last_name, phone_number)
VALUES ('yzab8cd5-3e6f-8796-2abi-96330c6bw164', 'masonr@example.com', '$2a$10$A/d6EXvgk/nd8PWGlbyUXeCv3qccFJEFKMHG/WkNye9cCychhn1lG', 'Mason', 'Rodriguez', '321654987');

--   Vehicle 1 (John Doe)
INSERT INTO vehicles (vehicle_id, user_id, make, model, year, transmission_type, mileage)
VALUES ('132b41b2-2bec-4b98-b08d-c7c0e03fe33e', 'b7024d89-1a5e-4517-3gba-05178u7ar260', 'Chevrolet', 'Cruze', '2016', 'MANUAL', '50000');

-- Vehicle 2 (Alice Smith)
INSERT INTO vehicles (vehicle_id, user_id, make, model, year, transmission_type, mileage)
VALUES ('0f8c5e36-9e94-4c6d-921d-29d7e2e923b5', 'aebc4d79-2b6f-4527-3zda-05432x5ar321', 'Chevrolet', 'Cruze', '2015', 'MANUAL', '35000');

-- Vehicle 3 (Emma Johnson)
INSERT INTO vehicles (vehicle_id, user_id, make, model, year, transmission_type, mileage)
VALUES ('01aa1f26-9f9b-438e-8346-572c83c2f181', 'cdff4g82-9e8h-7532-1qws-75321v5ar963', 'Ford', 'Focus', '2011', 'AUTOMATIC', '15000');

-- Vehicle 4 (Michael Williams)
INSERT INTO vehicles (vehicle_id, user_id, make, model, year, transmission_type, mileage)
VALUES ('02bb2c37-9c99-4c8d-8d52-1c2e3e4f5b76', 'eggh9i83-7j8k-4567-4tyu-98765z5ar741', 'Toyota', 'Camry', '2018', 'AUTOMATIC', '25000');

-- Vehicle 5 (Sophia Brown)
INSERT INTO vehicles (vehicle_id, user_id, make, model, year, transmission_type, mileage)
VALUES ('03cc3d48-0dd1-49d2-9ed3-2d3e4e5f6c87', 'lmno8p45-3q6r-8791-2abc-96325t5ar159', 'Honda', 'Civic', '2017', 'MANUAL', '30000');

-- Vehicle 6 (Oliver Garcia)
INSERT INTO vehicles (vehicle_id, user_id, make, model, year, transmission_type, mileage)
VALUES ('04dd4e59-1ee2-58f3-9fe4-3f4e5f6g7h98', 'mnop8q45-3r6s-8792-2abd-96326u6bs160', 'Nissan', 'Altima', '2019', 'AUTOMATIC', '20000');

-- Vehicle 7 (Ava Martinez)
INSERT INTO vehicles (vehicle_id, user_id, make, model, year, transmission_type, mileage)
VALUES ('05ee5f6a-2ff3-67g4-agh5-4g5h6i7j8k99', 'qrs8t45-3u6v-8793-2abe-96327v6bt161', 'Ford', 'Escape', '2020', 'AUTOMATIC', '15000');

-- Vehicle 8 (Ethan Lopez)
INSERT INTO vehicles (vehicle_id, user_id, make, model, year, transmission_type, mileage)
VALUES ('06ff6g7b-3gg4-78h5-bih6-5h6i7j8k9l00', 'tuvw8x45-3y6z-8794-2abf-96328w6bu162', 'Hyundai', 'Elantra', '2015', 'AUTOMATIC', '40000');

-- Vehicle 9 (Isabella Gonzalez)
INSERT INTO vehicles (vehicle_id, user_id, make, model, year, transmission_type, mileage)
VALUES ('07gg7h8c-4hh5-89i6-cji7-6i7j8k9l1m11', 'vwxy8z45-3a6b-8795-2abh-96329x6bv163', 'BMW', '3 Series', '2021', 'AUTOMATIC', '10000');

-- Vehicle 10 (Mason Rodriguez)
INSERT INTO vehicles (vehicle_id, user_id, make, model, year, transmission_type, mileage)
VALUES ('08hh8i9d-5ii6-9aj7-dkj8-7j8k9l2n22', 'yzab8cd5-3e6f-8796-2abi-96330c6bw164', 'Volkswagen', 'Jetta', '2019', 'MANUAL', '22000');


-- Appointment 1 (John Doe)
INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('1508dc5c-d460-443f-8f37-a174284f868c', 'b7024d89-1a5e-4517-3gba-05178u7ar260', '132b41b2-2bec-4b98-b08d-c7c0e03fe33e', '2024-03-24 11:00', 'Preventive Maintenance', 'None', 'PENDING');

-- Appointment 2 (Alice Smith)
INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('2508dc5c-d460-443f-8f37-a174284f868d', 'aebc4d79-2b6f-4527-3zda-05432x5ar321', '0f8c5e36-9e94-4c6d-921d-29d7e2e923b5', '2024-01-02 16:00', 'Air conditioning', 'None', 'COMPLETED');

-- Appointment 3 (Emma Johnson)
INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('3508dc5c-d460-443f-8f37-a174284f868e', 'cdff4g82-9e8h-7532-1qws-75321v5ar963', '01aa1f26-9f9b-438e-8346-572c83c2f181', '2024-02-16 11:00', 'Muffler', 'None', 'PENDING');

-- Appointment 4 (Michael Williams)
INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('4508dc5c-d460-443f-8f37-a174284f868f', 'eggh9i83-7j8k-4567-4tyu-98765z5ar741', '02bb2c37-9c99-4c8d-8d52-1c2e3e4f5b76', '2024-01-02 11:00', 'End of Manufacturer Warranty', 'None', 'CANCELLED');

-- Appointment 5 (Sophia Brown)
INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('5508dc5c-d460-443f-8f37-a174284f868g', 'lmno8p45-3q6r-8791-2abc-96325t5ar159', '03cc3d48-0dd1-49d2-9ed3-2d3e4e5f6c87', '2024-01-21 10:00', 'Exhaust System', 'None', 'CONFIRMED');

-- Appointment 6 (Oliver Garcia)
INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('6508dc5c-d460-443f-8f37-a174284f868h', 'mnop8q45-3r6s-8792-2abd-96326u6bs160', '04dd4e59-1ee2-58f3-9fe4-3f4e5f6g7h98', '2024-05-17 12:00', 'Engine and Transmission Installation', 'None', 'PENDING');

-- Appointment 7 (Ava Martinez)
INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('7508dc5c-d460-443f-8f37-a174284f868i', 'qrs8t45-3u6v-8793-2abe-96327v6bt161', '05ee5f6a-2ff3-67g4-agh5-4g5h6i7j8k99', '2024-03-24 11:00', 'Steering & Suspension', 'None', 'PENDING');

-- Appointment 8 (Ethan Lopez)
INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('8508dc5c-d460-443f-8f37-a174284f868j', 'tuvw8x45-3y6z-8794-2abf-96328w6bu162', '06ff6g7b-3gg4-78h5-bih6-5h6i7j8k9l00', '2024-03-27 13:00', 'Injection', 'None', 'PENDING');

-- Appointment 9 (Isabella Gonzalez)
INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('9508dc5c-d460-443f-8f37-a174284f868k', 'vwxy8z45-3a6b-8795-2abh-96329x6bv163', '07gg7h8c-4hh5-89i6-cji7-6i7j8k9l1m11', '2024-06-30 08:00', 'Alignment', 'None', 'PENDING');

-- Appointment 10 (Mason Rodriguez)
INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('1008dc5c-d460-443f-8f37-a174284f868l', 'yzab8cd5-3e6f-8796-2abi-96330c6bw164', '08hh8i9d-5ii6-9aj7-dkj8-7j8k9l2n22', '2024-03-24 18:00', 'Brakes', 'None', 'PENDING');

-- Appointment 11 (Mason Rodriguez 2)
INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('1008dc5c-d460-443f-8f37-a174284f8682', 'yzab8cd5-3e6f-8796-2abi-96330c6bw164', '08hh8i9d-5ii6-9aj7-dkj8-7j8k9l2n22', '2024-04-16 10:00', 'Steering & Suspension', 'None', 'PENDING');

-- Appointment 12 (Mason Rodriguez 3)
INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('1008dc5c-d460-443f-8f37-a174284f8683', 'yzab8cd5-3e6f-8796-2abi-96330c6bw164', '08hh8i9d-5ii6-9aj7-dkj8-7j8k9l2n22', '2024-06-25 19:00', 'Alignment', 'None', 'PENDING');

-- Appointment 13 (Mason Rodriguez 4)
INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('1008dc5c-d460-443f-8f37-a174284f8684', 'yzab8cd5-3e6f-8796-2abi-96330c6bw164', '08hh8i9d-5ii6-9aj7-dkj8-7j8k9l2n22', '2024-09-25 19:00', 'Oil Change', 'None', 'PENDING');
INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('1008dc5c-d460-443f-8f37-a174284f868l', 'yzab8cd5-3e6f-8796-2abi-96330c6bw164', '08hh8i9d-5ii6-9aj7-dkj8-7j8k9l2n22', '2024-03-24 18:00', 'Brakes', 'None', 'CANCELLED');

INSERT INTO appointments (appointment_id, user_id, vehicle_id, appointment_date, services, comments, status) VALUE ('1008dc5c-d460-443f-8f37-a174284f868l', 'yzab8cd5-3e6f-8796-2abi-96330c6bw164', '08hh8i9d-5ii6-9aj7-dkj8-7j8k9l2n22', '2024-03-24 18:00', 'Brakes', 'None', 'CANCELLED');


--ROLES

INSERT INTO roles (name) VALUES ('ROLE_CUSTOMER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

-- ADMIN ACCOUNT
INSERT INTO users (user_id,email, password, first_name, last_name, phone_number)
VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d479','admin@example.com', '$2a$10$A/d6EXvgk/nd8PWGlbyUXeCv3qccFJEFKMHG/WkNye9cCychhn1lG', 'Jean', 'Jacques', '438-993-2345');

--ADDING USERS TO ROLES
INSERT INTO user_roles (user_id, role_id)

VALUES ((SELECT id FROM users WHERE email = 'admin@example.com'),
        (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'));

-- Adding user 1 (John Doe) to ROLE_CUSTOMER
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'johndoe@example.com'),
        (SELECT id FROM roles WHERE name = 'ROLE_CUSTOMER'));

-- Adding user 2 (Alice Smith) to ROLE_CUSTOMER
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'alice.smith@example.com'),
        (SELECT id FROM roles WHERE name = 'ROLE_CUSTOMER'));

-- Adding user 3 (Emma Johnson) to ROLE_CUSTOMER
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'emma.johnson@example.com'),
        (SELECT id FROM roles WHERE name = 'ROLE_CUSTOMER'));

-- Adding user 4 (Michael Williams) to ROLE_CUSTOMER
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'michaelw@example.com'),
        (SELECT id FROM roles WHERE name = 'ROLE_CUSTOMER'));

-- Adding user 5 (Sophia Brown) to ROLE_CUSTOMER
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'sophia.b@example.com'),
        (SELECT id FROM roles WHERE name = 'ROLE_CUSTOMER'));

-- Adding user 6 (Oliver Garcia) to ROLE_CUSTOMER
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'oliver.g@example.com'),
        (SELECT id FROM roles WHERE name = 'ROLE_CUSTOMER'));

-- Adding user 7 (Ava Martinez) to ROLE_CUSTOMER
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'ava.martinez@example.com'),
        (SELECT id FROM roles WHERE name = 'ROLE_CUSTOMER'));

-- Adding user 8 (Ethan Lopez) to ROLE_CUSTOMER
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'ethanl@example.com'),
        (SELECT id FROM roles WHERE name = 'ROLE_CUSTOMER'));

-- Adding user 9 (Isabella Gonzalez) to ROLE_CUSTOMER
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'isabella.g@example.com'),
        (SELECT id FROM roles WHERE name = 'ROLE_CUSTOMER'));

-- Adding user 10 (Mason Rodriguez) to ROLE_CUSTOMER
INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'masonr@example.com'),
        (SELECT id FROM roles WHERE name = 'ROLE_CUSTOMER'));

