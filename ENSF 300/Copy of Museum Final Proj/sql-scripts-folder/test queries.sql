-- This is connected to the fixed database on Jacob's Computer Run Queries HERE:
select * from INFORMATION_SCHEMA.TABLES
WHERE TABLE_TYPE = 'BASE TABLE'
GO

select * from artist ;
SELECT * FROM artist ORDER BY id_no asc;

select * from artist where dateborn  = (select min(dateborn) from artist);


SELECT artist.id_no, borrowed.id_no,borrowed.collection
       FROM artist
       JOIN Borrowed
            ON artist.id_no = borrowed.id_no;



drop table if exists logs;
create table logs (update_occured nvarchar(50));

drop table if exists logs2;
create table logs2 (deletion_occured nvarchar(50));

DROP TRIGGER IF EXISTS artist_update;
DELIMITER  $$
CREATE TRIGGER artist_update
AFTER Update ON artist
FOR EACH ROW
BEGIN
insert into logs
values(CURRENT_TIMESTAMP);
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS artists_delete;
DELIMITER  $$
CREATE TRIGGER artists_delete
AFTER Update ON artist
FOR EACH ROW
BEGIN
insert into logs2
values(CURRENT_TIMESTAMP);
END$$
DELIMITER ;

DELETE FROM artist
WHERE id_no = '01';


UPDATE artist
SET Aname = 'Bob the builder', Dateborn = 1800
WHERE id_no = 6;
