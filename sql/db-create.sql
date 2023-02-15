DROP database IF EXISTS librarydb;

CREATE database librarydb;

USE librarydb;

CREATE TABLE user_roles
(
    id   INTEGER PRIMARY KEY auto_increment,
    role VARCHAR(20) NOT NULL UNIQUE
);
INSERT INTO user_roles (role)
VALUES ('READER'),
       ('LIBRARIAN'),
       ('ADMINISTRATOR');

CREATE TABLE users
(
    id INT PRIMARY KEY auto_increment,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    registered timestamp DEFAULT now() NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    role_id INT  NOT NULL DEFAULT 1,
    FOREIGN KEY (role_id) references user_roles (id) ON DELETE RESTRICT

);

CREATE TABLE books
(
    id INT NOT NULL PRIMARY KEY auto_increment,
    name VARCHAR(50) NOT NULL,
    author VARCHAR(50) NOT NULL,
    publisher VARCHAR(50) NOT NULL,
    publication_date DATE NOT NULL,
    count INT NOT NULL,
    CHECK ( count > -1 ),
    CONSTRAINT book_uniqueness_constraint UNIQUE(name, author)
);

CREATE TABLE book_operations
(
    user_id INT NOT NULL ,
    book_id INT NOT NULL ,
    start_date timestamp DEFAULT now(),
    status VARCHAR(15) NOT NULL ,
    duration_days INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
        on delete cascade,
    FOREIGN KEY (book_id) REFERENCES books (id)
        on delete cascade,
    CONSTRAINT book_operations_uniqueness_constraint UNIQUE(user_id, book_id)
);


INSERT INTO users VALUES (DEFAULT, 'reader', 'reader@g.com', '${noop}123', DEFAULT, DEFAULT, 1);
INSERT INTO users VALUES (DEFAULT, 'admin', 'admin@g.com', '${noop}123', DEFAULT, DEFAULT, 3);
INSERT INTO users VALUES (DEFAULT, 'librarian', 'librarian@g.com', '${noop}123', DEFAULT, DEFAULT, 2);
INSERT INTO users VALUES (DEFAULT, 'test1', 'test1@g.com', '${noop}123', DEFAULT, DEFAULT, 1);
INSERT INTO users VALUES (DEFAULT, 'test2', 'test2@g.com', '${noop}123', DEFAULT, DEFAULT, 2);
INSERT INTO users VALUES (DEFAULT, 'test3', 'test3@g.com', '${noop}123', DEFAULT, DEFAULT, 3);

INSERT INTO books VALUES (DEFAULT, 'Vocabulary', 'John Smith1', 'Enigma1', '2000-01-01', 1);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary1', 'John Smith2', 'Enigma2', '2000-01-01', 2);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary2', 'John Smith3', 'Enigma3', '2000-01-01', 3);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary3', 'John Smith4', 'Enigma4', '2000-01-01', 4);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary4', 'John Smith4', 'Enigma4', '2000-01-01', 4);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary5', 'John Smith4', 'Enigma4', '2000-01-01', 4);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary6', 'John Smith4', 'Enigma4', '2000-01-01', 4);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary7', 'John Smith4', 'Enigma4', '2000-01-01', 4);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary8', 'John Smith4', 'Enigma4', '2000-01-01', 4);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary9', 'John Smith4', 'Enigma4', '2000-01-01', 4);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary11', 'John Smith4', 'Enigma4', '2000-01-01', 4);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary22', 'John Smith4', 'Enigma4', '2000-01-01', 4);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary3121', 'John Smith4', 'Enigma4', '2000-01-01', 4);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary44', 'John Smith4', 'Enigma4', '2000-01-01', 4);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary355', 'John Smith4', 'Enigma4', '2000-01-01', 4);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary356', 'John Smith4', 'Enigma4', '2000-01-01', 4);
INSERT INTO books VALUES (DEFAULT, 'Vocabulary32', 'John Smith4', 'Enigma4', '2000-01-01', 4);

INSERT INTO book_operations VALUES (1, 1, DEFAULT, 'order', 1);
INSERT INTO book_operations VALUES (2, 2, DEFAULT, 'reading room', 2);
INSERT INTO book_operations VALUES (3, 3, DEFAULT, 'subscription', 3);



SELECT * FROM users;
SELECT * FROM books WHERE books.count > 0 AND books.name = 'Vocabulary32';
SELECT * FROM book_operations


