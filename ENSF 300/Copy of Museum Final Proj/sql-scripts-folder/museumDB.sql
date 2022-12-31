DROP DATABASE IF EXISTS MUSEUM;
CREATE DATABASE MUSEUM;
USE MUSEUM;
 
DROP TABLE IF EXISTS ART_OBJECTS;
CREATE TABLE ART_OBJECTS(
	id_no                     integer not null,
    Artist                 varchar(45) not null,
    Title                   varchar(45) not null,
    Description     	varchar(45) not null,
    Year                   varchar(45) not null,
    Origin                               varchar(45) not null,
    Epoch                               varchar(45) not null,
    primary key (id_no)
);
 
INSERT INTO ART_OBJECTS(id_no, Artist, Title, Description, Year, Origin, Epoch)
VALUES
(01,'Benedetto da Rovezzano','Angel Bearing Candlestick','A bronze angel bearing a candlestick',1524,'Northern Italy', '-14045192635'),
(02,'Hans Holbein the Younger','Henry VIII','Portrait of Henry VIII','1536','England','-13666501435'),
(03,'Guillim Scrots', 'Edward VI','Portrait of Edward VI', '1547','England','-13319432635'),
(04,'Hans Holbein the Youngest', 'Basin', 'Basin made of silver', '1535','Germany','-13698123835'),
(05,'Pietro Torrigiano','Portrait Bust of John Fisher','Portrait of John Fisher','1532','England','-13698123835'),
(06,'Clodion','The Intoxication of Wine','Multiple people made of terracotta','1780','England','-5966533435'),
(07,'Juan Martínez Montañés','Saint John the Baptist','Baptist made of polychromed wood','1620','England','-11015663035'),
(08,'Jean-François Oeben', 'Mechanical table','Early execution of a Mechanical Table','1761','England','-6566149435');
 
DROP TABLE IF EXISTS PAINTING;
CREATE TABLE PAINTING (
                id_no                     integer not null,
    Paint_type  varchar(25) not null,
    Drawn_on    varchar(25) not null,
    Style                  varchar(25) not null,
    primary key(id_no),
    foreign key(id_no) references ART_OBJECTS(id_no)
);
 
INSERT INTO PAINTING(id_no, Paint_type, Drawn_on, Style)
VALUES
('02','Oil', 'Panel', 'Elizabethan'),
('03','Oil', 'Panel','Elizabethan');
 
DROP TABLE IF EXISTS SCULPTURE;
CREATE TABLE SCULPTURE (
	id_no                     integer not null,
    Material          varchar(25) not null,
	Height                   varchar(25) not null,
    Weight                             varchar(25) not null,
    primary key(id_no),
    foreign key(id_no) references ART_OBJECTS(id_no)
);
 
INSERT INTO SCULPTURE(id_no, Material, Height, Weight)
VALUES
    (01,'Bronze', '50 cm', '141 kg'),
    (05,'Polychrome terracotta', '65.7 cm', '28.1 kg');
   
DROP TABLE IF EXISTS STATUE;
CREATE TABLE STATUE (
	id_no                     integer not null,
    Material          varchar(25) not null,
	Height                   varchar(25) not null,
    Weight                             varchar(25) not null,
    primary key(id_no),
    foreign key(id_no) references ART_OBJECTS(id_no)
);
 
INSERT INTO STATUE(id_no, Material, Height, Weight)
VALUES
    (06,'Terracotta', '42.9 cm', '87.5 kg'),
    (07,'Polychromed wood','154 cm', '110.2 kg');
 
DROP TABLE IF EXISTS OTHER;
CREATE TABLE OTHER (
    id_no       integer not null,
    Type        varchar(25) not null,
    Style       varchar(25) not null,
    primary key(id_no),
    foreign key(id_no) references ART_OBJECTS(id_no)
);
 
INSERT INTO OTHER(id_no, Type, Style)
VALUES
	(04,'Decorative','guilded silver'),
    (08,'Woodwork-Furniture','Traditional');
 
DROP TABLE IF EXISTS ARTIST;
CREATE TABLE ARTIST (
    id_no               	integer not null,
	Aname                   varchar(25),
	Description             varchar(25),
    Main_style              char(25),
    Epoch                   varchar(25),
    Country_of_origin       char(25),
    Dateborn                varchar(25),
    Date_died              	varchar(25),
   
    primary key(Aname),
    foreign key(id_no) references ART_OBJECTS(id_no)
);
 
INSERT INTO ARTIST(Aname, Description, Main_style, Epoch, Country_of_origin, Dateborn, Date_died,id_no)
VALUES
    ('Benedetto da Rovezzano','Sculptor','Elizabethan','-15686438400','Italy','1472','1552',01),
    ('Hans Holbein the Younger','Painter','Late Medival','-14897520000', 'Germany','1497','1543',02),
    ('Hans Holbein the Youngest','Painter','Early Medival','-14897520000', 'Germany','1497','1543',04),
    ('Guillim Scrots','Painter','Reniassance','-13635302400', 'England','1537','1553',03),
    ('Pietro Torrigiano','Sculptor','Elizabethan','-15686438400', 'Italy','1472','1528',05),
    ('Clodion','Sculptor','Late Medival','-7292332800', 'France','1738','1814',06),
    ('Juan Martínez Montañés','Glass maker','Late Medival','-12656658235', 'Spain','1568','1649',07),
    ('Jean-François Oeben','Wood Designer','Modern Mid-century','-7828453435', 'Germany','1721','1763',08);
   
DROP TABLE IF EXISTS PERMANENT_COLLECTION;
CREATE TABLE PERMANENT_COLLECTION(
    id_no       integer not null,
    Status      varchar(25) not null,
    Rowperm         varchar(25) not null,
    Date_acquired   varchar(25) not null,
    Cost        varchar(25) not null,
    primary key(id_no),
    foreign key(id_no) references ART_OBJECTS(id_no)
);
 
INSERT INTO PERMANENT_COLLECTION(id_no, Status, Rowperm, Date_acquired, cost)
VALUES
    (02,'On Display','Row 5', 'January 3 2021', '$ 80000'),
	(05,'On Display','Row 9', 'March 4th 2019', '$ 95000'),
    (07,'On Display','Row 1', 'Dec 1 2022', '$ 654000');
 
DROP TABLE IF EXISTS BORROWED;
CREATE TABLE BORROWED(
    id_no           integer not null,
    Collection      varchar(25) not null,
    Date_borrowed   varchar(25) not null,
    Date_returned   varchar(25) not null,
    primary key(id_no),
    foreign key(id_no) references ART_OBJECTS(id_no)
 
);
 
INSERT INTO BORROWED(id_no, Collection, Date_borrowed, Date_returned)
VALUES
    (03, 'Collection 4','September 2 2022', 'October 23 2022'),
	(01, 'Collection 2','March 15 2021', 'April 1 2021'),
    (06, 'Collection 1','November 1 2022', 'December 2 2022'),
    (08, 'Collection 3','June 14 2021', 'July 1 2022');
 
DROP TABLE IF EXISTS EXHIBITION;
CREATE TABLE EXHIBITION(
    id_no       varchar(6) not null,
    eName       char(25) not null,
    Start_date  varchar(25) not null,
    End_date    varchar(25) not null,
 
    primary key(eName)
);
 
INSERT INTO EXHIBITION(id_no, eName, Start_date, End_date)
VALUES
    (01, 'Vivid Delight', 'March 1 2021', 'December 3 2022'),
    (02, 'Breaking illusion', 'December 1 2021', 'December 3 2022'),
    (03, 'Dawn of Perspective','February 14 2022','December 3 2022'), 
    (04, 'Decadent Properties', 'June 1 2022', 'December 3 2022'),
    (05, 'Historical Misfortune','August 21 2022', 'December 3 2022'),
    (06, 'Remixing Relavance','Feburary 1 2021', 'December 3 2022'),
    (07, 'Parsing Mediation','April 16 2022', 'December 3 2022'),
    (08, 'Raspberry Desert','January 21 2022', 'December 3 2022');
    
 
DROP TABLE IF EXISTS COLLECTIONS;
CREATE TABLE COLLECTIONS(
    id_no           integer not null,
    cName           varchar(25) not null,
    Phone_number    varchar(25) not null,
    Address         varchar(50) not null,
    Type            varchar(25),
	Description     varchar(100),
    primary key(cName),
    foreign key(id_no) references ART_OBJECTS(id_no)
);
 
INSERT INTO COLLECTIONS(id_no, cName, Phone_number, Address, Type, Description)
VALUES
    (01, 'Collection 2', '1234567890', '626 Burnary Rd, Edmonton AB', 'Borrow and trade', 'Relics, variety and classic feats'), 
	(03, 'Collection 4', '1234567890', '82 Greenspring Way, Calgary AB', 'Borrow', 'Vast collection'),
    (06, 'Collection 1', '1234567890', '8510 Acre St, Red Deer AB', 'Borrow, trade and sell', 'English feats, wide timescale'),
    (08, 'Collection 3', '1234567890', '523 Rivertrail Rd, Calgary AB', 'Borrow', 'Small yet unique startup collection');

DROP USER IF EXISTS administrator@localhost;
DROP USER IF EXISTS data_entry@localhost;
DROP USER IF EXISTS guest@localhost;

CREATE USER administrator@localhost IDENTIFIED BY 'admin';
CREATE USER data_entry@localhost IDENTIFIED BY 'user';
CREATE USER guest@localhost;


GRANT ALL ON *.* TO administrator@localhost;
GRANT INSERT, SELECT, UPDATE, DELETE ON MUSEUM.* TO data_entry@localhost;
GRANT Select ON MUSEUM.* TO guest@localhost;

SET DEFAULT ROLE ALL TO administrator@localhost;
SET DEFAULT ROLE ALL TO data_entry@localhost;
SET DEFAULT ROLE ALL TO guest@localhost;