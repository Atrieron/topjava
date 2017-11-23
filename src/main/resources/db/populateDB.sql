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

INSERT INTO meals (date_time, description, calories, user_id) VALUES
  ('2015-05-30 10:00:00', 'Р—Р°РІС‚СЂР°Рє', 500, 100000),
  ('2015-05-30 13:00:00', 'РћР±РµРґ', 1000, 100000),
  ('2015-05-30 20:00:00', 'РЈР¶РёРЅ', 500, 100000),
  ('2015-05-31 10:00:00', 'Р—Р°РІС‚СЂР°Рє', 500, 100000),
  ('2015-05-31 13:00:00', 'РћР±РµРґ', 1000, 100000),
  ('2015-05-31 20:00:00', 'РЈР¶РёРЅ', 510, 100000),
  ('2015-06-01 14:00:00', 'РђРґРјРёРЅ Р»Р°РЅС‡', 510, 100001),
  ('2015-06-01 21:00:00', 'РђРґРјРёРЅ СѓР¶РёРЅ', 1500, 100001);
