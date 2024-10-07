
CREATE TYPE book_status AS ENUM (
    'FREE',
    'OCCUPIED',

);

CREATE TABLE IF NOT EXISTS books (
    id SERIAL PRIMARY KEY,
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    description TEXT,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    status book_status
);


INSERT INTO books (title, genre, author, description, isbn, status) VALUES
('1984', 'Dystopian', 'George Orwell', 'A dystopian novel about totalitarianism.', '1234567890123', 'FREE'),
('To Kill a Mockingbird', 'Fiction', 'Harper Lee', 'A novel about racial injustice in the Deep South.', '1234567890124', 'FREE'),
('The Great Gatsby', 'Classic', 'F. Scott Fitzgerald', 'A story about the American dream and the 1920s.', '1234567890125', 'FREE'),
('Moby Dick', 'Adventure', 'Herman Melville', 'The quest for revenge against the white whale.', '1234567890126', 'FREE'),
('Pride and Prejudice', 'Romance', 'Jane Austen', 'A romantic novel that critiques the British landed gentry.', '1234567890127', 'FREE');


CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username varchar(255),
    password varchar(255)
);
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    role varchar(10)
);


CREATE TABLE IF NOT exists library(
 id SERIAL PRIMARY KEY,
 book int,
 taken_at bigint,
 return_at bigint
);
