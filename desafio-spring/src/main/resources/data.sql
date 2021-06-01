DROP TABLE IF EXISTS user;

CREATE TABLE user(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    email VARCHAR(150) NOT NULL,
    type VARCHAR(1) NOT NULL
);

INSERT INTO user (name, email, type) VALUES
    ('Gabriel Duarte', 'gabrie.duarte@mercadolivre.com', 'C'),
    ('Thais Duarte', 'thaisduarte@hotmail.com', 'S');