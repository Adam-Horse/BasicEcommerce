create table orders
(
    id          bigint auto_increment
        primary key,
    customer_id bigint         not null,
    status      varchar(20)    not null,
    created_at  datetime       not null,
    total_price decimal(10, 2) not null,
    constraint table_name_users_id_fk
        foreign key (customer_id) references users (id)
);

create table order_items
(
    id bigint auto_increment primary key,
    order_id bigint not null,
    product_id bigint not null,
    unit_price decimal(10, 2) not null,
    quantity int not null,
    total_price decimal(10, 2) not null,

    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
)