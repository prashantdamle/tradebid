-- Clear all tables
delete from bid;
delete from job;
delete from project;
delete from user;

-- Create users
insert into user (id, first_name, last_name, user_id, user_name, user_profile_name, user_type) values (1, 'Prashant', 'Damle', 'efbbbf61-3333-6564-3135-342d34383865', 'pdamle', 'prash_customer', 0);
insert into user (id, first_name, last_name, user_id, user_name, user_profile_name, user_type) values (2, 'John', 'Doe', 'cff45737-704f-4fa9-81a1-902d5ea05007', 'johndoe', 'john_plumber', 1);
insert into user (id, first_name, last_name, user_id, user_name, user_profile_name, user_type) values (3, 'Jane', 'Doe', 'cff45737-704f-4fa9-81a1-902d5ea05008', 'janedoe', 'jane_sparky', 1);
insert into user (id, first_name, last_name, user_id, user_name, user_profile_name, user_type) values (4, 'Cookie', 'Doe', 'cff45737-704f-4fa9-81a1-902d5ea05009', 'cookiedoe', 'cookie_other', 1);
