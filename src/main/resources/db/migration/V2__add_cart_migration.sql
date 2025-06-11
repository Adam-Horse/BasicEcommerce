CREATE TABLE carts (
    id           BINARY(16) PRIMARY KEY,
    date_created DATE NOT NULL
);

CREATE TABLE cart_items (
    cart        BINARY(16) NOT NULL,
    product_id  BIGINT NOT NULL,
    quantity    INT NOT NULL,
    PRIMARY KEY (cart, product_id),
    FOREIGN KEY (cart) REFERENCES carts(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);