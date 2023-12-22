-- Customer 1
INSERT INTO customers(customer_id, first_name, last_name, username, email, phone_number, password)
VALUES ('b7024d89-1a5e-4517-3gba-05178u7ar260', 'John', 'Doe', 'johndoe','johndoe@example.com', '123456789', 'password1');

-- Customer 2
INSERT INTO customers(customer_id, first_name, last_name, username, email, phone_number, password)
VALUES ('aebc4d79-2b6f-4527-3zda-05432x5ar321', 'Alice', 'Smith', 'alicesmith','alice.smith@example.com', '987654321', 'password2');

-- Customer 3
INSERT INTO customers(customer_id, first_name, last_name, username, email, phone_number, password)
VALUES ('cdff4g82-9e8h-7532-1qws-75321v5ar963', 'Emma', 'Johnson', 'emma_j','emma.johnson@example.com', '555666777', 'password3');

-- Customer 4
INSERT INTO customers(customer_id, first_name, last_name, username, email, phone_number, password)
VALUES ('eggh9i83-7j8k-4567-4tyu-98765z5ar741', 'Michael', 'Williams', 'mikewill','michaelw@example.com', '111222333', 'password4');

-- Customer 5
INSERT INTO customers(customer_id, first_name, last_name, username, email, phone_number, password)
VALUES ('lmno8p45-3q6r-8791-2abc-96325t5ar159', 'Sophia', 'Brown', 'sophbrown','sophia.b@example.com', '444555666', 'password5');

-- Customer 6
INSERT INTO customers(customer_id, first_name, last_name, username, email, phone_number, password)
VALUES ('mnop8q45-3r6s-8792-2abd-96326u6bs160', 'Oliver', 'Garcia', 'oligarcia','oliver.g@example.com', '777888999', 'password6');

-- Customer 7
INSERT INTO customers(customer_id, first_name, last_name, username, email, phone_number, password)
VALUES ('qrs8t45-3u6v-8793-2abe-96327v6bt161', 'Ava', 'Martinez', 'avamart','ava.martinez@example.com', '123987456', 'password7');

-- Customer 8
INSERT INTO customers(customer_id, first_name, last_name, username, email, phone_number, password)
VALUES ('tuvw8x45-3y6z-8794-2abf-96328w6bu162', 'Ethan', 'Lopez', 'ethlopez','ethanl@example.com', '654789321', 'password8');

-- Customer 9
INSERT INTO customers(customer_id, first_name, last_name, username, email, phone_number, password)
VALUES ('vwxy8z45-3a6b-8795-2abh-96329x6bv163', 'Isabella', 'Gonzalez', 'isagonz','isabella.g@example.com', '987654123', 'password9');

-- Customer 10
INSERT INTO customers(customer_id, first_name, last_name, username, email, phone_number, password)
VALUES ('yzab8cd5-3e6f-8796-2abi-96330c6bw164', 'Mason', 'Rodriguez', 'masrod','masonr@example.com', '321654987', 'password10');


--   VEHICLES
INSERT INTO vehicles (vehicle_id, customer_id, make, model, year, transmission_type, mileage)
VALUES ('132b41b2-2bec-4b98-b08d-c7c0e03fe33e', 'lmno8p45-3q6r-8791-2abc-96325t5ar159', 'Chevrolet', 'Cruze', '2016', 'MANUAL', '50000');

-- Vehichle 2
INSERT INTO vehicles (vehicle_id, customer_id, make, model, year, transmission_type, mileage)
VALUES ('0f8c5e36-9e94-4c6d-921d-29d7e2e923b5', 'tuvw8x45-3y6z-8794-2abf-96328w6bu162', 'Chevrolet', 'Cruze', '2015', 'MANUAL', '35000');

-- Vehicle 3
INSERT INTO vehicles (vehicle_id, customer_id, make, model, year, transmission_type, mileage)
VALUES ('01aa1f26-9f9b-438e-8346-572c83c2f181', 'mnop8q45-3r6s-8792-2abd-96326u6bs160', 'Ford', 'Focus', '2011', 'AUTOMATIC', '15000');

-- Vehicle 4
INSERT INTO vehicles (vehicle_id, customer_id, make, model, year, transmission_type, mileage)
VALUES ('02bb2c37-9c99-4c8d-8d52-1c2e3e4f5b76', 'b7024d89-1a5e-4517-3gba-05178u7ar260', 'Toyota', 'Camry', '2018', 'AUTOMATIC', '25000');

-- Vehicle 5
INSERT INTO vehicles (vehicle_id, customer_id, make, model, year, transmission_type, mileage)
VALUES ('03cc3d48-0dd1-49d2-9ed3-2d3e4e5f6c87', 'eggh9i83-7j8k-4567-4tyu-98765z5ar741', 'Honda', 'Civic', '2017', 'MANUAL', '30000');

-- Vehicle 6
INSERT INTO vehicles (vehicle_id, customer_id, make, model, year, transmission_type, mileage)
VALUES ('04dd4e59-1ee2-58f3-9fe4-3f4e5f6g7h98', 'eggh9i83-7j8k-4567-4tyu-98765z5ar741', 'Nissan', 'Altima', '2019', 'AUTOMATIC', '20000');

-- Vehicle 7
INSERT INTO vehicles (vehicle_id, customer_id, make, model, year, transmission_type, mileage)
VALUES ('05ee5f6a-2ff3-67g4-agh5-4g5h6i7j8k99', 'aebc4d79-2b6f-4527-3zda-05432x5ar321', 'Ford', 'Escape', '2020', 'AUTOMATIC', '15000');

-- Vehicle 8
INSERT INTO vehicles (vehicle_id, customer_id, make, model, year, transmission_type, mileage)
VALUES ('06ff6g7b-3gg4-78h5-bih6-5h6i7j8k9l00', 'cdff4g82-9e8h-7532-1qws-75321v5ar963', 'Hyundai', 'Elantra', '2015', 'AUTOMATIC', '40000');

-- Vehicle 9
INSERT INTO vehicles (vehicle_id, customer_id, make, model, year, transmission_type, mileage)
VALUES ('07gg7h8c-4hh5-89i6-cji7-6i7j8k9l1m11', 'eggh9i83-7j8k-4567-4tyu-98765z5ar741', 'BMW', '3 Series', '2021', 'AUTOMATIC', '10000');

-- Vehicle 10
INSERT INTO vehicles (vehicle_id, customer_id, make, model, year, transmission_type, mileage)
VALUES ('08hh8i9d-5ii6-9aj7-dkj8-7j8k9l2n22', 'vwxy8z45-3a6b-8795-2abh-96329x6bv163', 'Volkswagen', 'Jetta', '2019', 'MANUAL', '22000');
