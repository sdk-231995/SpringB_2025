insert into products (name,price,quantity_available) values ('bed',199.99,12);
insert into products (name,price,quantity_available) values ('wardrobe',499.40,12);
insert into products (name,price,quantity_available) values ('couch',280.30,12);
insert into products (name,price,quantity_available) values ('table',119.99,12);
insert into products (name,price,quantity_available) values ('desk',250,12);

insert into cart_items (customer_id,quantity,product_id) values (4,2,4);
insert into cart_items (customer_id,quantity,product_id) values (4,2,3);

insert into orders (created_date,customer_name,customer_phone_number,total_amount) values ('2023-01-11','customer1','+31123123123',699.39);
insert into orders (created_date,customer_name,customer_phone_number,total_amount) values ('2023-01-12','customer2','+31345345343',449.99);
insert into orders (created_date,customer_name,customer_phone_number,total_amount) values ('2023-01-13','customer3','+31768686678',989.98);

insert into order_items (created_date,quantity,total_price,order_id,product_id) values ('2023-01-11',1,199.99,1,1);
insert into order_items (created_date,quantity,total_price,order_id,product_id) values ('2023-01-11',1,499.4,1,2);
insert into order_items (created_date,quantity,total_price,order_id,product_id) values ('2023-01-12',1,199.99,2,1);
insert into order_items (created_date,quantity,total_price,order_id,product_id) values ('2023-01-12',1,250.0,2,5);
insert into order_items (created_date,quantity,total_price,order_id,product_id) values ('2023-01-13',2,239.98,3,4);
insert into order_items (created_date,quantity,total_price,order_id,product_id) values ('2023-01-13',3,750.0,3,5);