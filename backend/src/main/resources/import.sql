INSERT INTO users (id, email, first_name, last_name, password) VALUES (1,'a@gmail.com','Alex','Ainsworth','$2a$12$APe52JMHopqRLaUoPNzP9OozjYvPjEKwNAmZE3cAikxSyQiuoxCSe');
INSERT INTO user_role (id, role_name, user_id) VALUES (1,'ADMIN',1);
INSERT INTO user_role (id, role_name, user_id) VALUES (2,'USER',1);
INSERT INTO users (id, email, first_name, last_name, password) VALUES (2,'mariia@gmail.com','Mariia','Parakhina','$2a$12$APe52JMHopqRLaUoPNzP9OozjYvPjEKwNAmZE3cAikxSyQiuoxCSe');
INSERT INTO user_role (id, role_name, user_id) VALUES (3,'USER',2);
