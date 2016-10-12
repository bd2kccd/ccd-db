insert into person (id,first_name,middle_name,last_name,email,workspace) values (1,'britney',null,'spears','bspears@localhost','/home/bspears')
insert into role_permission (id,name,description) values (1,'read','Read permission.')
insert into user_role (id,name,description) values (1,'user','User role.')
insert into user_role_role_permission_rel (user_role_id,role_permission_id) values (1,1)
insert into user_account (id,username,password,public_key,private_key,active,account_id,created_date,last_login_date,person_id) values (1,'bspears','hello',null,null,true,'123456789',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1)

-- author: Mark Silvis
-- Test data

-- Create Accesses
INSERT INTO access (id, name, description) VALUES (1, 'PUBLIC','Visable to all users'), (2, 'GROUP', 'Visible to members of specified group'), (3, 'PRIVATE','Visable only to creator');

-- Create Persons
INSERT INTO person (id, description, email, first_name, last_name, middle_name, workspace) VALUES (1, 'Physicist', 'isaac@example.com', 'Isaac', 'Newton', NULL, '~/ccd_workspace/'), (2, 'Computer Scientist', 'alan@example.com', 'Alan', 'Turing', NULL, '~/ccd_workspace/');

-- Create User Accounts
INSERT INTO user_account (id, activation_key, active, created_date, password, username, person_id, account) VALUES (1, 'abcd', b'1', '2016-03-24 15:37:06', '$2a$10$LTFYDfz3oBc96cKgaOkz2OIy8OYlT5o5TTYaJCRXr0s2.ahXAuAvK', 'isaac', 1, 'f0e4d573-e85a-4c52-8426-89fa55f36c7c'), (2, 'efgh', b'1', '2016-03-24 15:37:06', '$2a$10$ad0/roYTf7qhQlAjXl5m5.duYGtMw4dHgN.bauA/mi5eZV67937WS', 'alan', 2, '2a7dc1f4-a95c-42e1-a694-792acdc2e2c5');

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
INSERT INTO annotation_target (id, title, created, version, address, user_account_id) VALUES (1, 'Department of Biomedical Informatics', '2016-03-24 15:58:15', 0, 'http://dbmi.pitt.edu', 1);

-- Create Vocabularies
INSERT INTO vocabulary (id, description, name, version) VALUES (1, 'Text with no required structure', 'Plaintext', 0);

-- Create Attributes
INSERT INTO attribute (id, level, name, requirement_level, vocab_id) VALUES (1, NULL, 'text', NULL, 1);

-- Create Annotations with AnnotationData
-- Public
INSERT INTO annotation (id, redacted, created, version, access_control, parent_id, target_id, user_account_id, vocab_id) VALUES (1, b'0', '2016-10-09 15:58:15', 0, 1, NULL, 1, 1, 1), (2, b'0', '2016-10-11 13:00:00', 0, 1, 1, 1, 1, 1);
INSERT INTO annotation_data (id, value, annotation_id, attribute_id) VALUES (1, 'Public annotation', 1, 1), (2, 'Child annotation', 2, 1);

-- Group
INSERT INTO annotation (id, redacted, created, version, access_control, group_id, parent_id, target_id, user_account_id, vocab_id) VALUES (3, b'0', '2016-10-10 15:58:15', 0, 2, 1, NULL, 1, 1, 1);
INSERT INTO annotation_data (id, value, annotation_id, attribute_id) VALUES(3, 'Scientists group annotation', 3, 1);

-- Private
INSERT INTO annotation (id, redacted, created, version, access_control, parent_id, target_id, user_account_id, vocab_id) VALUES (4, b'0', '2016-10-12 16:00:00', 0, 3, NULL, 1, 1, 1);
INSERT INTO annotation_data (id, value, annotation_id, attribute_id) VALUES (4, 'Private annotation', 4, 1), (5, 'Private annotation with additional data', 4, 1);
