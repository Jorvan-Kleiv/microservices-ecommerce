-- ============================================================
-- Flyway migration: V2__seed_data.sql
-- ============================================================

-- ------------------------------------------------------------
-- Categories
-- ------------------------------------------------------------
INSERT INTO category (id, name, description)
VALUES (nextval('category_seq'), 'Electronics', 'Devices, gadgets and accessories'),
       (nextval('category_seq'), 'Clothing', 'Apparel for men, women and children'),
       (nextval('category_seq'), 'Home & Kitchen', 'Furniture, cookware and décor'),
       (nextval('category_seq'), 'Books', 'Fiction, non-fiction and textbooks'),
       (nextval('category_seq'), 'Sports & Outdoors', 'Equipment for fitness and outdoor activities');

-- ------------------------------------------------------------
-- Products
-- category_id resolved by name to stay sequence-agnostic
-- ------------------------------------------------------------

-- Electronics
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Wireless Headphones', 'Noise-cancelling over-ear headphones', 120.0, 89.99,
        (SELECT id FROM category WHERE name = 'Electronics')),
       (nextval('product_seq'), 'USB-C Hub', '7-in-1 multiport adapter', 250.0, 34.50,
        (SELECT id FROM category WHERE name = 'Electronics')),
       (nextval('product_seq'), 'Mechanical Keyboard', 'Tenkeyless with Cherry MX Blue switches', 75.0, 119.00,
        (SELECT id FROM category WHERE name = 'Electronics')),
       (nextval('product_seq'), 'Smart Watch', 'Fitness tracker with heart rate monitor', 90.0, 149.99,
        (SELECT id FROM category WHERE name = 'Electronics')),
       (nextval('product_seq'), 'Portable Charger', '20000 mAh power bank with fast charging', 160.0, 45.00,
        (SELECT id FROM category WHERE name = 'Electronics'));

-- Clothing
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Men''s Running Jacket', 'Lightweight waterproof jacket', 60.0, 59.95,
        (SELECT id FROM category WHERE name = 'Clothing')),
       (nextval('product_seq'), 'Women''s Yoga Pants', 'High-waist stretchy leggings', 200.0, 44.00,
        (SELECT id FROM category WHERE name = 'Clothing')),
       (nextval('product_seq'), 'Cotton T-Shirt Pack', 'Pack of 3 classic crew-neck tees', 300.0, 24.99,
        (SELECT id FROM category WHERE name = 'Clothing')),
       (nextval('product_seq'), 'Denim Jeans', 'Slim-fit mid-rise jeans', 130.0, 49.95,
        (SELECT id FROM category WHERE name = 'Clothing')),
       (nextval('product_seq'), 'Winter Beanie', 'Knitted wool blend hat', 95.0, 14.99,
        (SELECT id FROM category WHERE name = 'Clothing'));

-- Home & Kitchen
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Cast Iron Skillet', '12-inch pre-seasoned skillet', 85.0, 39.95,
        (SELECT id FROM category WHERE name = 'Home & Kitchen')),
       (nextval('product_seq'), 'Bamboo Cutting Board', 'Extra-large with juice groove', 140.0, 27.00,
        (SELECT id FROM category WHERE name = 'Home & Kitchen')),
       (nextval('product_seq'), 'Desk Lamp', 'LED dimmable with USB charging port', 95.0, 49.00,
        (SELECT id FROM category WHERE name = 'Home & Kitchen')),
       (nextval('product_seq'), 'French Press', '1-litre borosilicate glass coffee press', 110.0, 32.00,
        (SELECT id FROM category WHERE name = 'Home & Kitchen')),
       (nextval('product_seq'), 'Non-Stick Cookware Set', '5-piece PFOA-free pan and pot set', 55.0, 89.99,
        (SELECT id FROM category WHERE name = 'Home & Kitchen'));

-- Books
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Clean Code', 'Robert C. Martin — software craftsmanship', 50.0, 35.00,
        (SELECT id FROM category WHERE name = 'Books')),
       (nextval('product_seq'), 'Designing Data-Intensive Apps', 'Martin Kleppmann — distributed systems', 40.0, 52.00,
        (SELECT id FROM category WHERE name = 'Books')),
       (nextval('product_seq'), 'Atomic Habits', 'James Clear — habit formation', 75.0, 18.99,
        (SELECT id FROM category WHERE name = 'Books')),
       (nextval('product_seq'), 'The Pragmatic Programmer', 'Hunt & Thomas — 20th anniversary edition', 35.0, 42.00,
        (SELECT id FROM category WHERE name = 'Books'));

-- Sports & Outdoors
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_seq'), 'Yoga Mat', 'Non-slip 6mm thick exercise mat', 180.0, 29.99,
        (SELECT id FROM category WHERE name = 'Sports & Outdoors')),
       (nextval('product_seq'), 'Resistance Bands', '5-level resistance, latex-free', 220.0, 18.50,
        (SELECT id FROM category WHERE name = 'Sports & Outdoors')),
       (nextval('product_seq'), 'Hiking Backpack', '40L waterproof with rain cover', 45.0, 79.00,
        (SELECT id FROM category WHERE name = 'Sports & Outdoors')),
       (nextval('product_seq'), 'Jump Rope', 'Speed rope with ball-bearing handles', 200.0, 12.99,
        (SELECT id FROM category WHERE name = 'Sports & Outdoors')),
       (nextval('product_seq'), 'Water Bottle', '1L insulated stainless steel bottle', 175.0, 22.50,
        (SELECT id FROM category WHERE name = 'Sports & Outdoors'));