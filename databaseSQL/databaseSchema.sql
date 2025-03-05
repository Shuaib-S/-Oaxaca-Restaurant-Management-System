/*
Our database is hosted on a server, so therefore there is no commits for it.
This is the sql queries fed to create the tables on the server

*/ -- The orders table

CREATE TABLE orders (id SERIAL PRIMARY KEY,
                                       table_number INT NOT NULL,
                                                        order_time TIMESTAMP NOT NULL);

-- Orders Item table to have multiple items to one order

CREATE TABLE orders_item_list
  (id SERIAL PRIMARY KEY,
                     order_id INT NOT NULL,
                                  item_list_id INT NOT NULL,
                                                   CONSTRAINT fk_order
   FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                                                          CONSTRAINT fk_item
   FOREIGN KEY (item_list_id) REFERENCES items(id) ON DELETE CASCADE);

--Items table

CREATE TABLE items (id INT PRIMARY KEY,
                                   title VARCHAR(255),
                                         description VARCHAR(1000),
                                                     price DECIMAL(10, 2),
                                                           category VARCHAR(255),
                                                                    calories INT, allergens VARCHAR(255));

-- Logins table

CREATE TABLE logins ( id SERIAL PRIMARY KEY,
                                        username VARCHAR(255) NOT NULL UNIQUE,
                                                                       password VARCHAR(255) NOT NULL);