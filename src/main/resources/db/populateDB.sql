DELETE FROM products;
DELETE FROM articles;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO products (name, description, implementation_cost) VALUES
    ('КMS Lighthouse', 'система управления знаниями', '200000'),
    ('Actimize', 'платформа противодействия финансовым преступлениям', '250000');

INSERT INTO articles (product_id, name, content, date) VALUES
    (100000,
     'Представляем вашему вниманию решение последнего поколения в области управления знаниями КMS Lighthouse',
     'Решение позволяет обеспечить быстрый доступ к необходимым данным всего за несколько секунд.
Клиентам и партнерам не придется долго ожидать ответа на интересующие вопросы, а сотрудникам – тратить время на
поиск информации.
Принципы, положенные в основу системы, являются необходимыми условиями для эффективной работы со знаниями в
организациях в целом и в особенности в клиентских службах, где важно быстро и без ошибок предоставить необходимую
информацию. KMS Lighthouse обладает развитыми интерфейсами интеграции с внешними системами, включая интеграцию с CRM,
корпоративным веб-сайтом и другими.',
     DATE_SUB(NOW, 1 DAY)
    ),
    (100001,
     'Financial Crime Platform – платформа противодействия финансовым преступлениям',
     'Рост внимания к противодействию финансовым преступлениям
Сегодня организации находятся под пристальным и растущим наблюдением медиа, широкой общественности и регуляторов в
отношении программ противодействия финансовым преступлениям. Фокус в настоящий момент находится в основном в области
обеспечения соответствия требованиям и покрытия для всей организации с особенным вниманием на то, каким образом
разрозненные системы и процессы работают совместно.
Обеспечьте целостный, всесторонний взгляд на риск финансовых преступлений для всего предприятия
Платформа Actimize предоставляет возможность предотвращать финансовые преступления и обеспечивать соответствие
регулятивным требованиям, выявляя подозрительную активность для различных сущностей –  счетов, устройств, физических
лиц и организаций.
Технологии Actimize позволяет защитить вашу организацию, предоставляя возможности генерации оповещений и ведения
расследований, обеспечивая обработку данных в реальном времени и в пакетном режиме, предоставляя простые в
использовании пользовательские инструменты для адаптации логики выявления, процессов обработки данных и политик
под изменяющиеся требования.',
     DATE_SUB(NOW, 1 DAY)
    ),
    (100000,
     'Выгоды от использования KMS Lighthouse',
     '1. Сокращение средней продолжительности разговора на 20%;
2. Рост доли вопросов, решаемых при первом обращении, на 30%;
3. Снижение количества эскалаций на 2-й уровень поддержки на 30%;
4. Сокращение сроков обучения новых агентов на 40%;
5. Рост использования портала самообслуживания клиентами на 20%.',
     NOW
    ),
    (100001,
     'Open Analytics – Открытая аналитика',
     'Гибкость и возможности адаптации критичны при постоянных изменениях угроз и регулятивных требований
В современных условиях организации сталкиваются с высококонкурентным бизнес-окружением, динамичным расширением и
ужесточением регулятивных требований и постоянно растущими и меняющимися угрозами.
Возможности открытой аналитики (Open analytics) дают пользователям возможности управлять процессом
Возможности открытой аналитики Actimize дают пользователям необходимые средства управления для поддержки быстрого
реагирования на изменения и обеспечение соответствия решения уникальным бизнес-требованиям организации.
Готовая (out-of-the-box) аналитика комбинируется с пользовательской (user-defined)  аналитикой, правилами,
политиками и рабочими процессами (workflow) для обеспечения необходимого всестороннего покрытия и требуемой гибкости.
Гибкая и масштабируемая платформа Actimize, включающая в себя простые в использовании инструменты разработки,
обеспечивает пользователям возможность взять в свои руки управление',
     NOW
     );
