-- settings
INSERT INTO application_settings (id) VALUES (1);

-- projects
INSERT INTO projects (id, name, app_setting_id) VALUES (1, 'GFA', 1);
INSERT INTO projects (id, name, app_setting_id) VALUES (2, 'Home', 1);

-- contexts
INSERT INTO contexts (id, name, app_setting_id) VALUES (1, 'Online', 1);
INSERT INTO contexts (id, name, app_setting_id) VALUES (2, 'Phone', 1);

-- users
-- decryptedPassword: 'a'
INSERT INTO users (id, active, verified, email, password, registration_date, roles, username) VALUES (1, 1, 1, 'admin@admin.hu', '$2a$10$7o2zZhsCXjFXmPOl9DncCedwgh.K.miI8y3Tzq/Fi4dKyUdDdJY..', '2020-07-15 12:00:00', 'ADMIN;USER', 'Admin');
-- decryptedPassword: 'r'
INSERT INTO users (id, active, verified, email, password, registration_date, roles, username) VALUES (2, 1, 1, 'riel@riel.hu', '$2a$10$1giKZqWAnYLI6vFCFF3m4OBy/OpxQ/o.l/MqQra1vKlHzK5D.iJ4e', '2020-07-15 12:00:00', 'USER', 'Riel');
-- decryptedPassword: 'USER'
INSERT INTO users (id, active, verified, email, password, registration_date, roles, username) VALUES (3, 1, 1, 'khanee@khanee.hu', '$2a$10$F3D33yzeEUpiTNjmWVd1iuNXkqkm2wHEWFHgf3tEIm7aKtTyuYppu', '2020-07-15 12:00:00', 'USER', 'Khanee');
-- decryptedPassword: 'gy'
INSERT INTO users (id, active, verified, email, password, registration_date, roles, username) VALUES (4, 1, 1, 'gyuri@gyuri.hu', '$2a$10$Loznd7Fp1MzKlVrG/aRp5uY87ilb4rQl/OzDQYTax1h24af0j5q0i', '2020-07-15 12:00:00', 'USER', 'Gyuri');

-- verification_tokens
INSERT INTO verification_tokens (id, expiry_date, token, user_id) VALUES (1, '2020-06-05 12:00:00', '589c5730-3bcb-4eb9-a971-4446f688ab9d', 1);
INSERT INTO verification_tokens (id, expiry_date, token, user_id) VALUES (2, '2020-06-05 12:00:00', 'cbdf035e-e2ce-11ea-87d0-0242ac130003', 2);
INSERT INTO verification_tokens (id, expiry_date, token, user_id) VALUES (3, '2020-06-05 12:00:00', '350c7fa4-e2d0-11ea-87d0-0242ac130003', 3);
INSERT INTO verification_tokens (id, expiry_date, token, user_id) VALUES (4, '2020-06-05 12:00:00', '3abbe192-e2d0-11ea-87d0-0242ac130003', 4);

-- users
-- TODO: consider using entities here
INSERT INTO user_settings (id, user_id, look_up_assignee_name, look_up_project_name, look_up_context_name, display_done) VALUES (1, 1, 'Riel', 'GFA', 'Online', 0);
INSERT INTO user_settings (id, user_id, look_up_assignee_name, look_up_project_name, look_up_context_name, display_done) VALUES (2, 2, 'Khanee', 'Home', 'Home', 0);

-- todos
insert into todos (id, title, assignee_id, creator_id, description, link, project_id, context_id, priority, status, is_deleted, is_public, is_instant, history, creation_date, due_date, completion_date ) values (1, 'Wash', 1, 2, 'fast', 'https://www.wash.hu', 1, 1, 1, 1, 0, 1, 0, 'none', '2020-06-05 12:00:00', '2020-06-05 12:00:00', '2020-06-05 12:00:00');
insert into todos (id, title, assignee_id, creator_id, description, link, project_id, context_id, priority, status, is_deleted, is_public, is_instant, history, creation_date, due_date, completion_date ) values (2, 'Clean', 2, 3, 'gentle', 'https://www.wash.hu', 2, 2, 2, 2, 0, 1, 0, 'none', '2020-06-05 12:00:00', '2020-06-05 12:00:00', '2020-06-05 12:00:00');
