INSERT INTO categories (name)
VALUES ('Produce'),
       ('Dairy'),
       ('Bakery'),
       ('Meat & Seafood'),
       ('Beverages');

INSERT INTO products (name, price, description, category_id)
VALUES ('Organic Bananas', 0.59, 'Fresh USDA-certified organic bananas. Sold per lb.', 1),
       ('Whole Milk (1 Gallon)', 3.49, 'Pasteurized whole milk from local dairy farms.', 2),
       ('Sourdough Bread', 4.25, 'Artisan-style sourdough loaf baked fresh daily.', 3),
       ('Chicken Breast (Boneless, Skinless)', 6.99, 'Lean, boneless chicken breasts. Sold per lb.', 4),
       ('Atlantic Salmon Fillet', 9.99, 'Fresh Atlantic salmon fillet, skin-on. Sold per lb.', 4),
       ('Cheddar Cheese Block (8 oz)', 2.79, 'Sharp cheddar cheese block, aged 6 months.', 2),
       ('Apples (Fuji, 3 lb bag)', 4.99, 'Crisp and sweet Fuji apples, pre-packed in a 3 lb bag.', 1),
       ('Orange Juice (No Pulp)', 3.99, '100% orange juice, no pulp, 64 fl oz.', 5),
       ('Chocolate Croissant', 2.25, 'Buttery croissant with Belgian chocolate.', 3),
       ('Greek Yogurt (Strawberry, 5.3 oz)', 1.29, 'High-protein Greek yogurt with real strawberries.', 2);