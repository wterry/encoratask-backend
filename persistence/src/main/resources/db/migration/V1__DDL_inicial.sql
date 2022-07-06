/*
 Database initialization. Creates basic Product table, also required to correctly start an application with a Flyway
 dependency (migration folder cannot be empty).
 */

create table Products (
  id int primary key identity (1, 1),
  sku varchar(30) not null,
  inventoryStock smallint,
  description varchar(100),
  lastModified date,
  price decimal(6, 2)
);