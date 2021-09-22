INSERT INTO users (nombre, apellido_paterno, apellido_materno, genero_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at) VALUES('Exequiel Antonio', 'Guzmán', 'Fontecilla', 2, '13433025-2', 'guzman.exe@gmail.com', '981714236', 'eguzman', '$2a$10$p1rcEoYdVFQJob25wQbgeudD2DdDpe5HXv7i7k1ftUwUbAdxUHCuK', 1, 0, null, '2021-08-12 11:07:48');
INSERT INTO users (nombre, apellido_paterno, apellido_materno, genero_id, run, email, telefono, username, password, enabled, intentos, lockout_end, create_at) VALUES('Lorena Angélica', 'Concha', 'Hasember', 1, '10.970.239-0', 'lorehas@hotmail.com', '942342574', 'admin', '$2a$10$l9rGx2jKkbXs7AautgTM..kqYgf9XCIpspVxaowkFL.T.44j7rWyG', 1, 0, null, '2021-08-12 11:08:55');

INSERT INTO authorities (user_id, authority) VALUES(1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_ADMIN');