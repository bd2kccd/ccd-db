-- author: Mark Silvis
-- Test data

-- Create Accesses
INSERT INTO access (id, name, description) VALUES (1, 'PUBLIC','Visable by all users'), (2, 'PRIVATE','Visable only by you');

-- Create Persons
INSERT INTO person (id, description, email, first_name, last_name, middle_name, workspace) VALUES (1, 'Physicist', 'isaac@example.com', 'Isaac', 'Newton', NULL, '~/ccd_workspace/'), (2, 'Computer Scientist', 'alan@example.com', 'Alan', 'Turing', NULL, '~/ccd_workspace/');

-- Create User Accounts
INSERT INTO user_account (id, activation_key, active, created_date, password, username, person_id) VALUES (1, 'abcd', b'1', '2016-03-24 15:37:06', '$2a$10$LTFYDfz3oBc96cKgaOkz2OIy8OYlT5o5TTYaJCRXr0s2.ahXAuAvK', 'isaac', 1), (2, 'efgh', b'1', '2016-03-24 15:37:06', '$2a$10$ad0/roYTf7qhQlAjXl5m5.duYGtMw4dHgN.bauA/mi5eZV67937WS', 'alan', 2);

-- Create Groups
INSERT INTO groups (id, name, description) VALUES (1, 'Scientists', 'Group of famous scientists');

-- Create Group_Membership mappings
INSERT INTO group_membership VALUES (1, 1);

-- Create Group_Moderation mappings
INSERT INTO group_moderation VALUES (1, 1);

-- Create Group_Requests mappings
INSERT INTO group_requests VALUES (1, 2);

-- Create Uploads
INSERT INTO upload (id, title, created, version, address, user_account_id) VALUES (1, 'Department of Biomedical Informatics', '2016-03-24 15:58:15', 0, 'http://dbmi.pitt.edu', 1);

-- Create Vocabularies
INSERT INTO vocabulary (id, description, name, version) VALUES (1, 'Text with no required structure', 'Plaintext', 0);

-- Create Attributes
INSERT INTO attribute (id, level, name, requirement_level, vocab_id) VALUES (1, NULL, 'text', NULL, 1);

-- Create Annotations
INSERT INTO annotation (id, redacted, version, access_control, parent_id, upload_id, user_account_id, vocab_id) VALUES (1, b'0', 0, 1, NULL, 1, 1, 1), (2, b'0', 0, 1, 1, 1, 1, 1), (3, b'0', 0, 2, NULL, 1, 1, 1);

-- Create Annotation Data
INSERT INTO annotation_data (id, value, annotation_id, attribute_id) VALUES (1, 'Public annotation', 1, 1), (2, 'Child annotation', 2, 1), (3, 'Private annotation', 3, 1);
