CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) UNIQUE,  -- Email is NULL if created by an owner
    phone_number VARCHAR(20) UNIQUE,  -- Can be NULL for some users
    hashed_password VARCHAR(255) NOT NULL,
    user_role ENUM('Admin', 'Customer', 'Pharmacist', 'Delivery', 'Provider', 
                   'Pharmacist_Admin', 'Delivery_Admin', 'Provider_Admin') NOT NULL,
    owner_id INT NULL,  -- Links to an owner if created by admin
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE customers (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE pharmacists (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,  -- Username for login
    hashed_password VARCHAR(255) NOT NULL,  -- Password for login
    work_permit VARCHAR(255),
    location_coordinates POINT NOT NULL SRID 4326,
    owner_id INT,  -- Links to the pharmacy owner
    FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE providers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE, 
    hashed_password VARCHAR(255) NOT NULL,
    work_permit VARCHAR(255),
    location_coordinates POINT NOT NULL SRID 4326,
    owner_id INT,  
    FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE delivery_persons (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE, 
    hashed_password VARCHAR(255) NOT NULL,
    owner_id INT,
    FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE medicines (
    id INT PRIMARY KEY AUTO_INCREMENT,
    trade_name VARCHAR(100),
    price FLOAT NOT NULL,
    category VARCHAR(100) NOT NULL,
    form VARCHAR(100),
    active_ingredient TEXT,
    img_url TEXT
);

CREATE TABLE requests (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sender_id INT NOT NULL,
    location_coordinates POINT NOT NULL SRID 4326,
    receiver_ids JSON NOT NULL,
    request JSON NOT NULL,
    response JSON,
    request_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    request_type VARCHAR(50) NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    request_id INT NOT NULL,
    total_amount FLOAT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_delivery BOOLEAN NOT NULL,
    order_status VARCHAR(50) NOT NULL,
    FOREIGN KEY (request_id) REFERENCES requests(id) ON DELETE CASCADE
);

CREATE TABLE order_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    medicine_id INT NOT NULL,
    quantity INT NOT NULL,
    is_tape BOOLEAN,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (medicine_id) REFERENCES medicines(id) ON DELETE CASCADE
);



CREATE TABLE order_assignment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    pharmacist_id INT NOT NULL,
    delivery_id INT,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    location_coordinates POINT NOT NULL SRID 4326,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (pharmacist_id) REFERENCES pharmacists(id) ON DELETE CASCADE,
    FOREIGN KEY (delivery_id) REFERENCES delivery_persons(id) ON DELETE CASCADE
);

CREATE TABLE chat_messages (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    message_content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create Reports Table 
-- Create Notifcations Table  

ALTER TABLE pharmacists ADD SPATIAL INDEX (location_coordinates);
ALTER TABLE providers ADD SPATIAL INDEX (location_coordinates);
ALTER TABLE order_assignment ADD SPATIAL INDEX (location_coordinates);




-- Inserting Users 

INSERT INTO users (username, email, phone_number, hashed_password, user_role) VALUES
('admin1', 'admin1@example.com', '1234567890', 'hashed_password1', 'Admin'),
('admin2', 'admin2@example.com', '1234567891', 'hashed_password2', 'Admin');

INSERT INTO users (username, email, phone_number, hashed_password, user_role) VALUES
('pharma_admin1', 'pharma_admin1@example.com', '2234567890', 'hashed_password3', 'Pharmacist_Admin'),
('pharma_admin2', 'pharma_admin2@example.com', '2234567891', 'hashed_password4', 'Pharmacist_Admin');

INSERT INTO users (username, email, phone_number, hashed_password, user_role) VALUES
('customer1', 'customer1@example.com', '3234567890', 'hashed_password5', 'Customer'),
('customer2', 'customer2@example.com', '3234567891', 'hashed_password6', 'Customer');

-- Inserting Customers

SET @customer1_id = (SELECT id FROM users WHERE username = 'customer1');
SET @customer2_id = (SELECT id FROM users WHERE username = 'customer2');

INSERT INTO customers (id) VALUES (@customer1_id), (@customer2_id);


-- Inserting Pharmacists

SET @pharma_admin1_id = (SELECT id FROM users WHERE username = 'pharma_admin1');
SET @pharma_admin2_id = (SELECT id FROM users WHERE username = 'pharma_admin2');

INSERT INTO users (username, email, phone_number, hashed_password, user_role, owner_id) VALUES
('pharmacist1', 'pharmacist1@example.com', '4234567890', 'hashed_password7', 'Pharmacist', @pharma_admin1_id),
('pharmacist2', 'pharmacist2@example.com', '4234567891', 'hashed_password8', 'Pharmacist', @pharma_admin2_id);

SET @pharmacist1_id = (SELECT id FROM users WHERE username = 'pharmacist1');
SET @pharmacist2_id = (SELECT id FROM users WHERE username = 'pharmacist2');

INSERT INTO pharmacists (username, hashed_password, work_permit, location_coordinates, owner_id) VALUES
('pharmacist1', 'hashed_password7', 'Permit123', ST_GeomFromText('POINT(40.7128 -74.0060)', 4326), @pharma_admin1_id),
('pharmacist2', 'hashed_password8', 'Permit456', ST_GeomFromText('POINT(34.0522 -118.2437)', 4326), @pharma_admin2_id);

-- Inserting Providers 
SET @admin1_id = (SELECT id FROM users WHERE username = 'admin1');
SET @admin2_id = (SELECT id FROM users WHERE username = 'admin2');

INSERT INTO users (username, email, phone_number, hashed_password, user_role, owner_id) VALUES
('provider1', 'provider1@example.com', '5234567890', 'hashed_password9', 'Provider', @admin1_id),
('provider2', 'provider2@example.com', '5234567891', 'hashed_password10', 'Provider', @admin2_id);

SET @provider1_id = (SELECT id FROM users WHERE username = 'provider1');
SET @provider2_id = (SELECT id FROM users WHERE username = 'provider2');

INSERT INTO providers (username, hashed_password, work_permit, location_coordinates, owner_id) VALUES
('provider1', 'hashed_password9', 'Permit789', ST_GeomFromText('POINT(37.7749 -122.4194)', 4326), @admin1_id),
('provider2', 'hashed_password10', 'Permit987', ST_GeomFromText('POINT(51.5074 -0.1278)', 4326), @admin2_id);


-- Inserting Delivery Persons

INSERT INTO users (username, email, phone_number, hashed_password, user_role, owner_id) VALUES
('delivery1', 'delivery1@example.com', '6234567890', 'hashed_password11', 'Delivery', @admin1_id),
('delivery2', 'delivery2@example.com', '6234567891', 'hashed_password12', 'Delivery', @admin2_id);

SET @delivery1_id = (SELECT id FROM users WHERE username = 'delivery1');
SET @delivery2_id = (SELECT id FROM users WHERE username = 'delivery2');

INSERT INTO delivery_persons (username, hashed_password, owner_id) VALUES
('delivery1', 'hashed_password11', @admin1_id),
('delivery2', 'hashed_password12', @admin2_id);


 -- From Database

INSERT INTO medicines (trade_name, price, category, form, active_ingredient, img_url) VALUES
('1 2 3 (ONE TWO THREE) 20 F.C TABS.', 10.5, 'Painkiller', 'Tablet', 'Paracetamol', 'img1.jpg'),
('4 WET INTIMATE GEL 100 ML', 15.0, 'Antibiotic', 'Capsule', 'Amoxicillin', 'img2.jpg'),
('5-FLUOROURACIL-EBEWE 250MG/5ML I.V. VIAL', 20.0, 'Antihistamine', 'Syrup', 'Cetirizine', 'img3.jpg');

INSERT INTO requests (sender_id, location_coordinates, receiver_ids, request, request_status, request_type) VALUES
(2, ST_GeomFromText('POINT(40.7306 -73.9352)', 4326), '[3,4]', '{"medicine": "Paracetamol", "quantity": 2}', 'Pending', 'Medicine Order');


INSERT INTO orders (request_id, total_amount, is_delivery, order_status) VALUES
(1, 21.0, TRUE, 'processing');

INSERT INTO order_items (order_id, medicine_id, quantity, is_tape) VALUES
(1, 1, 2, FALSE);

INSERT INTO order_assignment (order_id, pharmacist_id, delivery_id, location_coordinates) 
VALUES (
    1, 
    2, 
    1, 
    (SELECT r.location_coordinates 
     FROM requests r 
     JOIN orders o ON r.id = o.request_id 
     WHERE o.id = 1) 
);



INSERT INTO chat_messages (order_id, sender_id, receiver_id, message_content) VALUES
(1, 2, 3, 'Hello, do you have Paracetamol in stock?'),
(1, 3, 2, 'Yes, we have it available.');



-- To Calculate the distance you can use that:
-- SELECT ST_Distance(
--     pharmacists.location_coordinates,
--     customers.location_coordinates
-- ) AS distance_units

-- To Calculate the distance you can use that:
-- SELECT ST_Distance(
--     pharmacists.location_coordinates,
--     customers.location_coordinates
-- ) AS distance_units

