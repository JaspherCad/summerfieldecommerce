-- Inserting data into the `users` table
INSERT INTO users (id, full_name, email, password, created_at, phone_number, role)
VALUES 
    (1, 'JohnDoe', 'john.doe@example.com', 'password123', CURRENT_TIMESTAMP, '1234567890', 'SELLER'),
    (2, 'JaneSmith', 'jane.smith@example.com', 'password456', CURRENT_TIMESTAMP, '0987654321', 'SELLER');

-- Inserting data into the `room` table
INSERT INTO room (id, room_type, room_price, room_description)
VALUES 
    (1, 'Single', 100.00, 'A cozy single room'),
    (2, 'Double', 150.00, 'A comfortable double room');

-- Inserting data into the `bookings` table
INSERT INTO bookings (id, check_in_date, check_out_date, adult_count, child_count, total_guest_count, confirmation_code, user_id, room_id)
VALUES 
    (1, '2024-10-01', '2024-10-05', 2, 1, 3, 'CONFIRM123', 1, 1),
    (2, '2024-11-15', '2024-11-20', 1, 0, 1, 'CONFIRM456', 2, 2);

    
    
    
    
INSERT INTO store (id, do_delivery, gcash,  name, description, block, lot, phone_number, opening_time, closing_time, user_id, created_at) VALUES 
(1, true, false, 'John''s Electronics Store', 'Your one-stop shop for all things electronic', 1, 38, '1234567890', '09:00:00', '17:00:00', 1, CURRENT_TIMESTAMP),
(2, false, true, 'Jane''s Bookstore', 'A collection of bestsellers and classic reads', 2, 21, '0987654321', '10:00:00', '18:00:00', 2, CURRENT_TIMESTAMP);


    
    
    
    
    
    
    
    
-- Insert into product with isFeatured column
INSERT INTO product (id, price, cost, quantity, store_id, average_rating, img_src, category,description, name, created_at, is_featured) VALUES 
(1, 299.99, 200.00, 50, 1, 5.0, 'https://cdn.britannica.com/18/137318-050-29F7072E/rooster-Rhode-Island-Red-roosters-chicken-domestication.jpg', 'Electronics', 'High-quality wireless headphonesHigh-quality wireless headphonesHigh-quality wireless headphonesHigh-quality wireless headphonesv', 'AirBeats Pro', CURRENT_TIMESTAMP, true), 
(2, 199.99, 110.00, 100, 2, 5.0,'https://cdn.britannica.com/18/137318-050-29F7072E/rooster-Rhode-Island-Red-roosters-chicken-domestication.jpg', 'Books', 'Bestselling mystery novel', 'The Silent Patient', CURRENT_TIMESTAMP, false),
(3, 79.99, 50.25, 75, 1, 5.0,'https://cdn.britannica.com/18/137318-050-29F7072E/rooster-Rhode-Island-Red-roosters-chicken-domestication.jpg' ,'Home', 'Ergonomic office chair', 'ComfortSeat Deluxe', CURRENT_TIMESTAMP, true), 
(4, 49.99, 30.00, 200, 2, 5.0, 'https://cdn.britannica.com/18/137318-050-29F7072E/rooster-Rhode-Island-Red-roosters-chicken-domestication.jpg' ,'Toys', 'Educational building blocks for kids', 'Creative Bricks Set', CURRENT_TIMESTAMP, false);





-- Inserting data into the `tags` table
INSERT INTO tag (id, name) 
VALUES 
    (1, 'electronics'), 
    (2, 'books'), 
    (3, 'home'), 
    (4, 'toys'), 
    (5, 'wireless'), 
    (6, 'educational'), 
    (7, 'ergonomic');




-- Inserting data into the `product_tags` join table
INSERT INTO product_tag (product_id, tag_id) 
VALUES 
    -- AirBeats Pro is in Electronics and Wireless categories
    (1, 1), -- electronics
    (1, 3), -- wireless
    
    -- The Silent Patient is in Books category
    (2, 3), -- books
    
    -- ComfortSeat Deluxe is in Home and Ergonomic categories
    (3, 3), -- home
    (3, 7), -- ergonomic
    
    -- Creative Bricks Set is in Toys and Educational categories
    (4, 4), -- toys
    (4, 6); -- educational
    
    
    
    
    
    
    
    
    
    

-- Inserting sample data into the `reviews` table
INSERT INTO review (rating, comment, user_id, product_id, created_at) VALUES
    (5, 'Excellent product, high quality!', 1, 1, '2024-10-10 14:30:00'),
    (5, 'Great read, highly recommended.', 2, 2, '2024-10-15 09:00:00'),
    (5, 'Comfortable but could be cheaper.', 1, 3, '2024-10-18 12:45:00'),
    (5, 'My kids love this educational toy!', 2, 4, '2024-10-20 11:15:00'),
    (5, 'Good value for money.', 1, 2, '2024-10-21 16:00:00');

