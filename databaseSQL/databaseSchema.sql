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

-- Logins table

CREATE TABLE logins (id SERIAL PRIMARY KEY,
                                       username VARCHAR(255) NOT NULL UNIQUE,
                                                                      password VARCHAR(255) NOT NULL);


CREATE TABLE table_assistance ( id SERIAL PRIMARY KEY,
                                                  table_No INT NOT NULL,
                                                               help BOOLEAN NOT NULL);

