-- author: Mark Silvis
-- Test data

-- Create Share Accesses
INSERT INTO share_access (id, name, description) VALUES (1, 'PUBLIC','Visable to all users'), (2, 'GROUP', 'Visible to members of specified group'), (3, 'PRIVATE','Visable only to creator');

-- Create Persons
INSERT INTO person (id, description, email, first_name, last_name, middle_name, workspace) VALUES (1, 'Physicist', 'isaac@example.com', 'Isaac', 'Newton', NULL, '~/ccd_workspace/'), (2, 'Computer Scientist', 'alan@example.com', 'Alan', 'Turing', NULL, '~/ccd_workspace/');

-- Create User Login
INSERT INTO user_login (id, login_date, login_location, last_login_date, last_login_location) VALUES (1, CURRENT_DATE, 323223552, CURRENT_DATE, 323223552), (2, CURRENT_DATE, 323223552, CURRENT_DATE, 323223552);

-- Create User Login Attempt
INSERT INTO user_login_attempt (id, attempt_date, attempt_location, attempt_count) VALUES (1, CURRENT_DATE, 323223552, 1), (2, CURRENT_DATE, 323223552, 1);

-- Create User Role
INSERT INTO user_role (id, name, description) VALUES (1, 'USER', 'Standard user'), (2, 'ADMIN', 'Administrator');

-- Create User Accounts
INSERT INTO user_account (id, person_id, user_login_id, user_login_attempt_id, user_role_id, username, password, active, disabled, registration_date, registration_location, account, activation_key) VALUES (1, 1, 1, 1, 2, 'isaac', '$2a$10$LTFYDfz3oBc96cKgaOkz2OIy8OYlT5o5TTYaJCRXr0s2.ahXAuAvK', b'1', b'0', CURRENT_DATE, 323223552, 'f0e4d573-e85a-4c52-8426-89fa55f36c7c', 'abcde'), (2, 2, 2, 2, 2, 'alan', '$2a$10$ad0/roYTf7qhQlAjXl5m5.duYGtMw4dHgN.bauA/mi5eZV67937WS', b'1', b'0', CURRENT_DATE, 323223552, '2a7dc1f4-a95c-42e1-a694-792acdc2e2c5', 'vwxyz');

-- Create Share Groups
INSERT INTO share_group (id, name, description, owner) VALUES (1, 'Scientists', 'Group of famous scientists', 1);

-- Create Share Group Membership mappings
INSERT INTO share_group_membership VALUES (1, 1);     -- User Isaac is a member

-- Create Share Group Requests mappings
INSERT INTO shaer_group_requests VALUES (1, 2);       -- User Alan is requesting Group access

-- Create Address for Annotation Target
INSERT INTO address (id, title, url, user_account_id) VALUES (1, 'Department of Biomedical Informatics', 'http://dbmi.pitt.edu', 1);

-- Create Annotation Target
INSERT INTO annotation_target (id, created_date, modified_date, modify_count, address, user_account_id) VALUES (1, '2016-03-24 15:58:15', NULL, 0, 1, 1);

-- Create Vocabularies
INSERT INTO vocabulary (id, description, name, created_date, version) VALUES (1, 'Text with no required structure', 'Plaintext', '2016-03-24 15:58:15', '20150514');

-- Create Attributes
INSERT INTO attribute (id, name, required, vocab_id) VALUES (1, 'text', 1, 1);

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
