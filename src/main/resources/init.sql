
CREATE TABLE IF NOT EXISTS book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(30),
    genre VARCHAR(30),
    author VARCHAR(30),
    description VARCHAR(500),
    isbn VARCHAR(13),
    status VARCHAR(10) CHECK (status IN ('FREE', 'OCCUPIED'))
);


INSERT INTO book (title, genre, author, description, isbn, status) VALUES
('1984', 'Dystopian', 'George Orwell', 'A dystopian novel about totalitarianism.', '1234567890123', 'FREE'),
('To Kill a Mockingbird', 'Fiction', 'Harper Lee', 'A novel about racial injustice in the Deep South.', '1234567890124', 'OCCUPIED'),
('The Great Gatsby', 'Classic', 'F. Scott Fitzgerald', 'A story about the American dream and the 1920s.', '1234567890125', 'FREE'),
('Moby Dick', 'Adventure', 'Herman Melville', 'The quest for revenge against the white whale.', '1234567890126', 'OCCUPIED'),
('Pride and Prejudice', 'Romance', 'Jane Austen', 'A romantic novel that critiques the British landed gentry.', '1234567890127', 'FREE');

CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    role varchar(10)
);
insert into roles (role) values ('ADMIN'),('USER');

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username varchar(255),
    password varchar(255)
);

CREATE TABLE IF NOT exists freeBooks(
 id SERIAL PRIMARY KEY,
 book int,
 taken_at varchar,
 return_at varchar
);
