DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

  INSERT INTO meals (description, calories, dateTime, user_id) VALUES
  ('Breakfast', 150, '20150530 10:00:00', 100000),
  ('Dinner', 250, '20150530 13:00:00', 100000),
  ('Supper', 225, '20150530 20:00:00', 100000),
  ('Breakfast', 175, '20150531 10:00:00', 100000),
  ('Supper', 225, '20150531 13:00:00', 100000),
  ('Late Supper', 500, '20150531 20:00:00', 100000),
  ('Admin breakfast', 175, '20150530 10:00:00', 100001),
  ('Admin dinner', 225, '20150530 15:00:00', 100001);