USE `acmsdb`;

-- Users table
CREATE TABLE IF NOT EXISTS users (
                                     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
    );

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