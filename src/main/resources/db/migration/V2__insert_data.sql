-- Insert into users table
INSERT INTO users (id, first_name, last_name, email, password, is_active)
VALUES ('1e7b6bd4-8a74-4b27-ae6e-1093252e5b2d', 'John', 'Doe', 'john.doe@example.com', 'password1', true),
       ('2b1db30c-9a2a-4b0c-84f2-a08e3e00127b', 'Jane', 'Doe', 'jane.doe@example.com', 'password2', true);

-- Insert into books table
INSERT INTO books (id, title, author, genre, price, quantity_available)
VALUES
    ('b2e68f2a-9e52-4bdb-b924-cd34334e8cd6', 'To Kill a Mockingbird', 'Harper Lee', 'CLASSIC', 10.99, 50),
    ('e92853f1-f8db-4a33-9b9e-5725f41c7c4d', 'Pride and Prejudice', 'Jane Austen', 'ROMANCE', 12.99, 30),
    ('1f6a5f60-4d92-472a-bfbd-3ab5721c3f87', 'The Great Gatsby', 'F. Scott Fitzgerald', 'CLASSIC', 14.99, 20),
    ('e44cf18c-70f1-408d-8a4e-6a34e801a397', 'Moby Dick', 'Herman Melville', 'ADVENTURE', 15.99, 15),
    ('246b706d-d587-4b72-b375-ffda1746a199', '1984', 'George Orwell', 'HORROR', 13.99, 25),
    ('7bb1f13b-8e98-46f1-b73f-3b360a883b4e', 'The Catcher in the Rye', 'J.D. Salinger', 'CLASSIC', 11.49, 40),
    ('b2b6e43c-6e7d-45e3-ae11-11c77c16d59d', 'The Hobbit', 'J.R.R. Tolkien', 'ADVENTURE', 16.99, 60),
    ('05f2c17b-bf62-4be7-9d0a-d9daaa2b6982', 'Frankenstein', 'Mary Shelley', 'HORROR', 10.49, 45),
    ('6b8da520-21fd-4c6a-b674-0a73de62ef3f', 'The Odyssey', 'Homer', 'CLASSIC', 9.99, 35),
    ('ad13e02c-41c3-402d-98f3-9b1f0f86c705', 'The Raven', 'Edgar Allan Poe', 'POETRY', 7.99, 25);