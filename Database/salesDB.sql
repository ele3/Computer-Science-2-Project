# Computer Science II
# CSCE156, Spring 2021
# 3/22/2021
#
# Name(s): 
# Eric Le & Brock Melvin
#
# Sales Database

-- Drop the table if it already exists
drop table if exists SaleItem;
drop table if exists Item;
drop table if exists Sale;
drop table if exists Store;
drop table if exists Email;
drop table if exists Person;
drop table if exists Address;
drop table if exists Country;
drop table if exists State;

-- Create the tables required for Sale information

-- This table stores a state's name.
create table if not exists State (
	stateId int not null primary key auto_increment,
    name varchar(255) not null
);

-- This table stores a country's name.
create table if not exists Country (
	countryId int not null primary key auto_increment,
    name varchar(255) not null
);

-- This table models an Address with storing its id, street, city, zipcode, state, and country.
create table if not exists Address (
	addressId int not null primary key auto_increment,
    street varchar(255) not null,
    city varchar(255) not null,
    zipcode varchar(255) not null,
    stateId int not null,
    countryId int not null,
    foreign key (stateId) references State(stateId),
    foreign key (countryId) references Country(countryId)
);

-- This table models a Person with storing their id, code, type, first & last name, and address.
create table if not exists Person (
	personId int not null primary key auto_increment,
    code varchar(255) not null unique,
    type varchar(255) not null,
    firstName varchar(255) not null,
    lastName varchar(255) not null,
    addressId int not null,
    foreign key (addressId) references Address(addressId),
    constraint allowedPersonType check (type = "E" or type = "C" or type = "P" or type = "G")
);

-- This table models an email and holds all the email addresses for a particular person
create table if not exists Email (
	emailId int not null primary key auto_increment,
    name varchar(255) not null,
    personId int,
    foreign key (personId) references Person(personId)
);

-- This table models a Store with storing its id, code, person, and address
create table if not exists Store (
	storeId int not null primary key auto_increment,
    code varchar(255) not null unique,
    personId int not null,
    addressId int not null,
    foreign key (personId) references Person(personId),
    foreign key (addressId) references Address(addressId)
);

-- This table models a Sale with storing its id, code, store, customer, and salesperson.
create table if not exists Sale (
	saleId int not null primary key auto_increment,
    code varchar(255) not null unique,
    storeId int not null,
    customerId int not null,
    salespersonId int not null,
    foreign key (storeId) references Store(storeId),
    foreign key (customerId) references Person(personId),
    foreign key (salespersonId) references Person(personId)
);

-- This table models an Item with storing its id, type, name, code, basePrice, hourlyRate, and annualFee.
create table if not exists Item (
	itemId int not null primary key auto_increment,
    type varchar(255) not null,
    name varchar(255) not null,
    code varchar(255) not null unique,
    basePrice double,
    hourlyRate double,
    annualFee double,
    constraint allowedItemType check (type = "PG" or type = "PU" or type = "PN" or type = "SV" or type = "SB")
);

-- This table acts as a simple join-table between Sale and Item.
-- This includes additional info such as quantity, employee for a service, service hours, subscription dates, 
-- and the set price for a giftcard.
create table if not exists SaleItem (
	saleitemId int not null primary key auto_increment,
    quantity int,
    personId int,
    numberOfHours double,
    beginDate varchar(11),
    endDate varchar(11),
    giftcardPrice double,
    saleId int,
    itemId int,
    foreign key (saleId) references Sale(saleId),
    foreign key (itemId) references Item(itemId),
    foreign key (personId) references Person(personId),
    constraint uniquePair unique index(itemId, saleId) -- Prevents duplicate items on a sale
);

-- Inserting non-trivial test data into tables
insert into Country (countryId, name) values (1, "USA");

insert into State (stateId, name) values 
	(1, "FL"),
    (2, "CA"),
    (3, "IL"),
    (4, "CO");

insert into Address (addressId, street, city, zipcode, stateId, countryId) values
    (1, "851 Havenside Junction", "Miami", "33255", 1, 1),
    (2, "184 Fletchers Way", "San Diego", "91945", 2, 1),
    (3, "12 Caesars Way", "Rockford", "61102", 3, 1),
    (4, "28 35th Alford Plac", "Miami", "33128", 1, 1),
    (5, "941 Sheen Road", "San Diego", "92111", 2, 1),
    (6, "9515 Schofield Lane", "Rockford", "61125", 3, 1),
    (7, "60 Spinney Hill Road", "Aspen", "81612", 4, 1),
    (8, "234 Consort Avenue", "Aspen", "81612", 4, 1);

insert into Address (addressId, street, city, zipcode, stateId, countryId) values
    (9, "4151 Triangle Drive", "Miami", "33238", 1, 1),
    (10, "1942 Potter Lane", "San Diego", "92093", 2, 1),
    (11, "851 Rocky Road Boulevard", "Rockford", "61125", 3, 1),
    (12, "312 Green Avenue", "Aspen", "81612", 4, 1);

insert into Person (personId, code, type, lastName, firstName, addressId) values
    (1, "qawm9j", "E", "Smith", "Liam", 1),
    (2, "oqdfba", "E", "Johnson", "Noah", 2),
    (3, "rrdz9q", "P", "Williams", "Oliver", 3),
    (4, "1kxxua", "G", "Jones", "Jay", 4),
    (5, "kmt1e5", "G", "Brown", "Alicia", 5),
    (6, "qoxu6n", "G", "Davis", "Anna", 6),
    (7, "2r0dav", "C", "Miller", "Benjamin", 7),
    (8, "rj3be0", "C", "Wilson", "Jessica", 8);

insert into Email (emailId, name, personId) values
	(1, "lsmith@lps.org", 1), (2, "lsmith@huskers.unl.edu", 1),
    (3, "njohnson@aol.com", 2), (4, "njohnson@yahoo.com", 2),
    (5, "owilliams@gmail.com", 3), (6, "owilliams@drop.com", 3),
    (7, "jjones@yahoo.com", 4),
    (8, "abrown@hotmail.com", 5),
    (9, "adavis@bing.com", 6), (10, "adavis@taobao.com", 6), (11, "adavis@lps.org", 6),
    (12, "bmiller@gmail.com", 7), (13, "bmiller@ebay.com", 7),
    (14, "jwilson@zoho.com", 8);

insert into Store (storeId, code, personId, addressId) values
    (1, "iseq2c", 1, 9),
    (2, "d08qbz", 2, 10),
    (3, "nk2mpe", 1, 11),
    (4, "7xtj2o", 1, 12);
    
insert into Sale (saleId, code, storeId, customerId, salespersonId) values
	(1, "sAle001", 1, 3, 1),
    (2, "3CXcrW", 1, 4, 1),
    (3,"thesale03", 1, 3, 1),
    (4,"s004", 2, 5, 2),
    (5,"SaLE005", 3, 7, 1);

insert into Item (itemId, type, name, code, basePrice) values
    (1, "PN", "Nintendo Switch", "8xz1ok", 299.99),
    (2, "PN", "PlayStation 5", "jc3jvi", 499.99),
    (3, "PN", "Breath of the Wild (Digital Copy)", "2lgcel", 59.99),
    (4, "PU", "GTA V", "b8u6od", 29.99);

insert into Item (itemId, type, name, code) values
    (5, "PG", "Riot Points", "b436dw");

insert into Item (itemId, type, name, code, hourlyRate) values
    (6, "SV", "Game Therapy", "cpi19p", 3.99);

insert into Item (itemId, type, name, code, annualFee) values
    (7, "SB", "Netflix", "5i1khh", 150.00);

insert into SaleItem (saleItemId, quantity, saleId, itemId) values
    (1, 1, 1, 1),
    (2, 2, 1, 3),
    (5, 2, 4, 2),
    (7, 3, 5, 4);

insert into SaleItem (saleItemId, giftCardPrice, saleId, itemId) values
    (3, 20, 2, 5),
    (8, 100, 5, 5);

insert into SaleItem (saleItemId, beginDate, endDate, saleId, itemId) values
    (4, "2020-02-25", "2021-01-01", 3, 7);

insert into SaleItem (saleItemId, personId, numberOfHours, saleId, itemId) values
    (6, 2, 4, 4, 6);