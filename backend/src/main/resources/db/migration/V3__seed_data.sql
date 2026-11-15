INSERT INTO recipes (title, author, cooking_time_minutes, servings) 
VALUES 
    ('Борщ', 'Бабушка', 60, 4),
    ('Пельмени', 'Мама', 90, 6),
    ('Оливье', 'Папа', 45, 8);

INSERT INTO ingredients (recipe_id, name, quantity, calories) 
VALUES 
    (1, 'Свекла', 2.0, 50),
    (1, 'Мясо', 500.0, 300),
    (1, 'Капуста', 200.0, 40);

INSERT INTO ingredients (recipe_id, name, quantity, calories) 
VALUES 
    (2, 'Мука', 500.0, 1500),
    (2, 'Фарш', 400.0, 800);

INSERT INTO ingredients (recipe_id, name, quantity, calories) 
VALUES 
    (3, 'Картофель', 300.0, 250),
    (3, 'Морковь', 150.0, 60),
    (3, 'Горошек', 100.0, 80);