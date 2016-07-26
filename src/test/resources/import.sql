-- author: Mark Silvis
-- Test data

-- Create Accesses
INSERT INTO access (id, name, description) VALUES (1, 'PUBLIC','Visable to all users'), (2, 'GROUP', 'Visible to members of specified group'), (3, 'PRIVATE','Visable only to creator');

-- Create Persons
INSERT INTO person (id, description, email, first_name, last_name, middle_name, workspace) VALUES (1, 'Physicist', 'isaac@example.com', 'Isaac', 'Newton', NULL, '~/ccd_workspace/'), (2, 'Computer Scientist', 'alan@example.com', 'Alan', 'Turing', NULL, '~/ccd_workspace/');

-- Create User Login
INSERT INTO user_login (id, login_date, login_location, last_login_date, last_login_location) VALUES (1, CURRENT_DATE, 3232235521, CURRENT_DATE, 3232235521), (2, CURRENT_DATE, 3232235521, CURRENT_DATE, 3232235521);

-- Create User Login Attempt
INSERT INTO user_login_attempt (id, attempt_date, attempt_location, attempt_count) VALUES (1, CURRENT_DATE, 3232235521, 1), (2, CURRENT_DATE, 3232235521, 1);

-- Create User Accounts
INSERT INTO user_account (id, person_id, user_login_id, user_login_attempt_id, username, password, active, disabled, registration_date, registration_location, account, activation_key) VALUES (1, 1, 1, 1, 'isaac', '$2a$10$LTFYDfz3oBc96cKgaOkz2OIy8OYlT5o5TTYaJCRXr0s2.ahXAuAvK', b'1', b'0', CURRENT_DATE, 3232235521, 'f0e4d573-e85a-4c52-8426-89fa55f36c7c', 'abcde'), (2, 2, 2, 2, 'alan', '$2a$10$ad0/roYTf7qhQlAjXl5m5.duYGtMw4dHgN.bauA/mi5eZV67937WS', b'1', b'0', CURRENT_DATE, 3232235521, '2a7dc1f4-a95c-42e1-a694-792acdc2e2c5', 'vwxyz');

-- Create User Role
INSERT INTO user_role (id, name, description) VALUES (1, 'USER', 'Standard user'), (2, 'ADMIN', 'Administrator');

-- Create User Role User Account relationships
INSERT INTO user_account_user_role_rel (user_account_id, user_role_id) VALUES (1, 1), (1, 2), (2, 1);

-- Create Groups
INSERT INTO groups (id, name, description) VALUES (1, 'Scientists', 'Group of famous scientists');

-- Create Group_Membership mappings
INSERT INTO group_membership VALUES (1, 1);     -- User Isaac is a member

-- Create Group_Moderation mappings
INSERT INTO group_moderation VALUES (1, 1);     -- User Isaac is a moderator

-- Create Group_Requests mappings
INSERT INTO group_requests VALUES (1, 2);       -- User Alan is requesting Group access

-- Create Annotation Target
INSERT INTO annotation_target (id, title, created, modified, version, address, user_account_id) VALUES (1, 'Department of Biomedical Informatics', '2016-03-24 15:58:15', NULL, 0, 'http://dbmi.pitt.edu', 1);

-- Create Vocabularies
INSERT INTO vocabulary (id, description, name, version) VALUES (1, 'Text with no required structure', 'Plaintext', 0);

-- Create Attributes
INSERT INTO attribute (id, level, name, requirement_level, vocab_id) VALUES (1, NULL, 'text', NULL, 1);

-- Create Annotations with AnnotationData
-- Public
INSERT INTO annotation (id, redacted, version, access_control, parent_id, target_id, user_account_id, vocab_id) VALUES (1, b'0', 0, 1, NULL, 1, 1, 1), (2, b'0', 0, 1, 1, 1, 1, 1);
INSERT INTO annotation_data (id, value, annotation_id, attribute_id) VALUES (1, 'Public annotation', 1, 1), (2, 'Child annotation', 2, 1);

-- Group
INSERT INTO annotation (id, redacted, version, access_control, group_id, parent_id, target_id, user_account_id, vocab_id) VALUES (3, b'0', 0, 2, 1, NULL, 1, 1, 1);
INSERT INTO annotation_data (id, value, annotation_id, attribute_id) VALUES(3, 'Scientists group annotation', 3, 1);

-- Private
INSERT INTO annotation (id, redacted, version, access_control, parent_id, target_id, user_account_id, vocab_id) VALUES (4, b'0', 0, 3, NULL, 1, 1, 1);
INSERT INTO annotation_data (id, value, annotation_id, attribute_id) VALUES (4, 'Private annotation', 4, 1), (5, 'Private annotation with additional data', 4, 1);
insert into person (id,email,first_name,last_name,middle_name,workspace) values (1,'bspears@localhost','britney','spears',null,'/home/bspears')

insert into user_login(id,last_login_date,last_login_location,login_date,login_location) values (1,CURRENT_TIMESTAMP,2130706433,CURRENT_TIMESTAMP,2130706433)

insert into user_login_attempt (id,attempt_count,attempt_date,attempt_location) values (1,1,CURRENT_TIMESTAMP,2130706433)

insert into user_account(id,account,active,disabled,password,registration_date,registration_location,username,person_id,user_login_id,user_login_attempt_id) values (1,'56755429-351c-4fc5-888f-94679818d061',true,false,'$shiro1$SHA-256$500000$J/+mLlMDC7XRWVc8TfSl5w==$CrpVSMPiw8AGT7nGfbs3ajWCUZ8tjX1vmuOxXPGy6cM=',CURRENT_TIMESTAMP,2130706433,'bspears',1,1,1)

insert into user_role (id,name,description) values(1,'user','User role.')

insert into user_account_user_role_rel(user_account_id,user_role_id) values (1,1)
