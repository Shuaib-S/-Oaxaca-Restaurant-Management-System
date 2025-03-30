/*
Our database is hosted on a server, so therefore there is no commits for it.
This is the sql queries fed to create the tables on the server

*/ -- The orders table

CREATE TABLE orders (
  id SERIAL PRIMARY KEY,
  table_number INT NOT NULL,
  order_time TIMESTAMP NOT NULL);
  
ALTER TABLE orders
ADD COLUMN status VARCHAR(255) NOT NULL DEFAULT 'pending';

ALTER TABLE orders
ADD COLUMN confirmed BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE orders
ADD COLUMN paid BOOLEAN NOT NULL DEFAULT FALSE;

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

CREATE TABLE items ( id INT PRIMARY KEY,
                                    title VARCHAR(255),
                                          description VARCHAR(1000),
                                                      price DECIMAL(10, 2),
                                                            category VARCHAR(255),
                                                                     calories INT, allergens VARCHAR(255));


INSERT INTO items (id, title, description, price, category, calories, allergens)
VALUES (1,
        'Beef Gringa',
        'Slow-cooked British grass-fed beef with grilled cheese and salsa fresca, in two soft corn tortillas.',
        7.95,
        'Tacos',
        625,
        'Dairy, gluten'), (2,
                           'Free Range Pork Pibil',
                           'Slow-cooked in citrus and spices, with pink pickled onions, in two soft corn tortillas.',
                           7.50,
                           'Tacos',
                           425,
                           NULL), (3,
                                   'Buttermilk Free Range Chicken',
                                   'Crispy fried in flour with pink pickled onions and spiced mayo, in two soft flour tortillas.',
                                   7.95,
                                   'Tacos',
                                   700,
                                   'Dairy, wheat, gluten, eggs'), (4,
                                                                   'Free Range Chicken Club',
                                                                   'With avocado, lettuce, melted cheese and chipotle mayo.',
                                                                   7.75,
                                                                   'Quesadillas',
                                                                   850,
                                                                   'Dairy, eggs'), (5,
                                                                                    'Black Bean & Three Cheeses',
                                                                                    'With smoky beans and avocado leaf.',
                                                                                    6.75,
                                                                                    'Quesadillas',
                                                                                    660,
                                                                                    'Dairy'), (6,
                                                                                               'Slow-Cooked Beef Burrito',
                                                                                               'British, grass-fed, beef with chipotle, ancho, herbs and spices.',
                                                                                               15.95,
                                                                                               'Burritos',
                                                                                               1195,
                                                                                               'Dairy, gluten'), (7,
                                                                                                                  'Free Range Chicken Burrito',
                                                                                                                  'With ancho rub.',
                                                                                                                  15.95,
                                                                                                                  'Burritos',
                                                                                                                  1045,
                                                                                                                  'Dairy, gluten'), (8,
                                                                                                                                     'Free Range Pork Pibil Burrito',
                                                                                                                                     'With pink pickled onions.',
                                                                                                                                     14.95,
                                                                                                                                     'Burritos',
                                                                                                                                     1150,
                                                                                                                                     'Dairy, gluten'), (9,
                                                                                                                                                        'Frijoles Chorizo',
                                                                                                                                                        'Creamy black beans with Mexican style chorizo.',
                                                                                                                                                        4.95,
                                                                                                                                                        'Sides',
                                                                                                                                                        380,
                                                                                                                                                        'Dairy'), (10,
                                                                                                                                                                   'Guacamole Dip',
                                                                                                                                                                   'Avocados, Lime and Coriander, served with crunchy tortilla chips.',
                                                                                                                                                                   4.95,
                                                                                                                                                                   'Sides',
                                                                                                                                                                   300,
                                                                                                                                                                   NULL), (11,
                                                                                                                                                                           'Classic Nachos',
                                                                                                                                                                           'With melted cheddar cheese. Served with fried beans, guacamole, pico de gallo, and sour topping.',
                                                                                                                                                                           10.95,
                                                                                                                                                                           'Sides',
                                                                                                                                                                           630,
                                                                                                                                                                           'Dairy'), (12,
                                                                                                                                                                                      'Churros',
                                                                                                                                                                                      'Traditional Mexican pastry dusted with cinnamon sugar, served with chocolate sauce.',
                                                                                                                                                                                      7.99,
                                                                                                                                                                                      'Desserts',
                                                                                                                                                                                      420,
                                                                                                                                                                                      'Dairy, wheat, gluten'), (13,
                                                                                                                                                                                                                'Ice Cream Scoop',
                                                                                                                                                                                                                'Your choice of Vanilla, Chocolate or Strawberry, with chocolate syrup available.',
                                                                                                                                                                                                                3.95,
                                                                                                                                                                                                                'Desserts',
                                                                                                                                                                                                                340,
                                                                                                                                                                                                                'Dairy'), (14,
                                                                                                                                                                                                                           'Soft drink',
                                                                                                                                                                                                                           'Pepsi, Fanta, 7up, Pepsi Max, Dr Pepper',
                                                                                                                                                                                                                           2.95,
                                                                                                                                                                                                                           'Drink',
                                                                                                                                                                                                                           NULL,
                                                                                                                                                                                                                           NULL), (15,
                                                                                                                                                                                                                                   'Beers',
                                                                                                                                                                                                                                   'Corona, Modelo, Lucky Saint',
                                                                                                                                                                                                                                   5.75,
                                                                                                                                                                                                                                   'Drink',
                                                                                                                                                                                                                                   150,
                                                                                                                                                                                                                                   NULL), (16,
                                                                                                                                                                                                                                           'Teas',
                                                                                                                                                                                                                                           'English Tea, Green Tea, Mint Tea',
                                                                                                                                                                                                                                           2.95,
                                                                                                                                                                                                                                           'Drink',
                                                                                                                                                                                                                                           1,
                                                                                                                                                                                                                                           NULL), (17,
                                                                                                                                                                                                                                                   'Juices',
                                                                                                                                                                                                                                                   'Orange and Mango, Pineapple, Guava',
                                                                                                                                                                                                                                                   4.95,
                                                                                                                                                                                                                                                   'Drink',
                                                                                                                                                                                                                                                   100,
                                                                                                                                                                                                                                                   NULL);


ALTER TABLE items ADD COLUMN active BOOLEAN DEFAULT TRUE;
-- Made the card titles shorter
UPDATE items
SET title = 'Chicken Club',
        description = 'Free range chicken club with avocado, melted cheese, and chipotle mayo'
WHERE id = 4;

UPDATE items
SET title = 'Beef Burrito',
        description = 'British, grass fed beef, slow cooked and combined with chipotle, ancho, herbs and spices'
WHERE id = 6;

UPDATE items 
SET title = 'Pork Pibil Burrito',
        description = 'Free range pork pibil with pink pickled onions in a burrito'
WHERE id = 8;

UPDATE items
SET title = 'Chicken Burrito',
        description = 'Free range chicken burrito with ancho rub'
WHERE id = 7;

UPDATE items
SET title = 'Beans & Cheeses',
        description = 'Smoky black beans and 3 cheeses with avocado leaf'
WHERE id = 5; 
-- Logins table

CREATE TABLE logins (id SERIAL PRIMARY KEY,
                                       username VARCHAR(255) NOT NULL UNIQUE,
                                                                      password VARCHAR(255) NOT NULL);


CREATE TABLE table_assistance ( id SERIAL PRIMARY KEY,
                                                  table_No INT NOT NULL,
                                                               help BOOLEAN NOT NULL);

-- The Stock table

CREATE TABLE stock (
  id SERIAL PRIMARY KEY,
  Quantity INT NOT NULL,
  category VARCHAR(255));

INSERT INTO stock (id, title, quantity, category)
VALUES (1, 'Chicken', 99, 'Meat'),
(2, 'Steak', 99, 'Meat'),
(3, 'Pork', 99, 'Meat'),
(4, 'Chorizo', 99, 'Meat'),
(5, 'Avocado', 99, 'Vegetable'),
(6, 'Black Beans', 99, 'Vegetable'),
(7, 'Tomato', 99, 'Vegetable'),
(8, 'White Onion', 99, 'Vegetable'),
(9, 'Red Onion', 99, 'Vegetable'),
(10, 'Three Cheese Mix', 99, 'Dairy'),
(11, 'Milk', 99, 'Dairy'),
(12, 'Buttermilk', 99, 'Dairy'),
(13, 'Sour Cream', 99, 'Dairy'),
(14, 'Vanilla Ice cream', 99, 'Dairy'),
(15, 'Chocolate Ice cream', 99, 'Dairy'),
(16, 'Strawberry Ice cream', 99, 'Dairy'),
(17, 'Tortilla', 99, 'Carbohydate'),
(18, 'Buns', 99, 'Carbohydate'),
(19, 'Churro Mix', 99, 'Carbohydate'),
(20, 'Nachos', 99, 'Carbohydrate'),
(21, 'Pineapple Juice', 99, 'Drink'),
(22, 'OJ & Mango Juice', 99, 'Drink'),
(23, 'Guava Juice', 99, 'Drink'),
(24, 'English Tea', 99, 'Drink'),
(25, 'Green Tea', 99, 'Drink'),
(26, 'Mint Tea', 99, 'Drink'),
(27, 'Pepsi', 99, 'Drink'),
(28, 'Diet Pepsi', 99, 'Drink'),
(29, 'Fanta', 99, 'Drink'),
(30, '7up', 99, 'Drink'),
(31, 'Pepsi Max', 99, 'Drink'),
(32, 'Dr Pepper', 99, 'Drink'),
(33, 'Corona', 99, 'Drink'),
(34, 'Modelo', 99, 'Drink'),
(35, 'Lucky Saint', 99, 'Drink'),
(36, 'Chipotle Sauce', 99, 'Sauce'),
(37, 'Chocolate Syrup', 99, 'Sauce');
