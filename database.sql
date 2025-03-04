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
    id INT PRIMARY KEY,
    pharmacy_name VARCHAR(255) NOT NULL,
    work_permit VARCHAR(255),
    location_coordinates POINT NOT NULL SRID 4326,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE providers (
    id INT PRIMARY KEY,
    provider_name VARCHAR(255) NOT NULL,
    work_permit VARCHAR(255),
    location_coordinates POINT NOT NULL SRID 4326,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE delivery_persons (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
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
ALTER TABLE requests ADD SPATIAL INDEX (location_coordinates);
ALTER TABLE order_assignment ADD SPATIAL INDEX (location_coordinates);
