DELETE FROM articles;
DELETE FROM products;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO products (name, description, implementation_cost) VALUES
    ('Продукт номер один', 'Описание первого продукта', 200000),
    ('Продукт номер два', 'Описание второго продукта', 250000);

INSERT INTO articles (product_id, name, content, date) VALUES
    (100000,
     'Статья-1 про первый продукт',
     'Содержание статьи-1 о первом продукте',
     DATE_SUB(NOW, 1 DAY)
    ),
    (100001,
     'Статья-1 про второй продукт',
     'Содержание статьи-1 о втором продукте',
     DATE_SUB(NOW, 1 DAY)
    ),
    (100000,
     'Статья-2 про первый продукт',
     'Содержание статьи-2 о первом продукте',
     NOW
    ),
    (100001,
     'Статья-2 про второй продукт',
     'Содержание статьи-2 о втором продукте',
     NOW
     );
