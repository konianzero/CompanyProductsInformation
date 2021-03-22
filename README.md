Информационная система о продуктах компании
-------------------------------------------

Приложение позволяет создавать(получать/изменять/удалять) информацию о продуктах компании, а также
информационные статьи по продуктам.

Так же можно:
- сортировать продукты и статьи по определенному полю
- отфильтровать продукты по названию, описанию, стоимости внедрения (от - до)
- отфильтровать статьи по названию, контенту, дате создания (с - по)

---

**Запуск**
```
mvn clean package org.codehaus.cargo:cargo-maven2-plugin:1.8.2:run
```

URL: [http://localhost:8080/info](http://localhost:8080/info)

---

## Документация API

| API        | Method | Description                             | URL                                                                                                   |
|------------|--------|-----------------------------------------|-------------------------------------------------------------------------------------------------------|
| Products   | POST   | Создать информацию о продукте           | {URL}/products                                                                                        |
|            | GET    | Получить информацию о продукте          | {URL}/products/{id}                                                                                   |
|            | GET    | Получить информацию по всем продуктам   | {URL}/products?sortBy={sortBy}&filterBy={filterBy}&filter={filter}&costFrom={costFrom}&costTo={costTo}|
|            | PUT    | Обновить информацию о продукте          | {URL}/products/{id}                                                                                   |
|            | DELETE | Удалить информацию о продукте           | {URL}/products/{id}                                                                                   |
| Articles   | POST   | Создать статью                          | {URL}/articles                                                                                        |
|            | GET    | Получить статью                         | {URL}/articles/{id}                                                                                   |
|            | GET    | Получить все статьи                     | {URL}/articles?sortBy={sortBy}&filterBy={filterBy}&filter={filter}&fromDate={fromDate}&toDate={toDate}|
|            | PUT    | Обновление статью                       | {URL}/articles/{id}                                                                                   |
|            | DELETE | Удалить статью                          | {URL}/articles/{id}                                                                                   |

---

## Команды CURL

### Информация о продукте

**Создать информацию о продукте**
```
curl -s -X POST -d '{"name":"Новый продукт","description":"Описание Нового продукта","implementationCost":300000}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/info/products
```
**Получить информацию о продукте**
```
curl -s http://localhost:8080/info/products/100001
```
**Получить информацию по всем продуктам**
```
curl -s http://localhost:8080/info/products
```
**Обновить информацию о продукте**
```
curl -s -X PUT -d '{"id":100001,"name":"Обновленный продукт","description":"Описание обновленного продукта","implementationCost":450000}' -H 'Content-Type: application/json' http://localhost:8080/info/products/100001
```
**Удалить информацию о продукте**
```
curl -s -X DELETE http://localhost:8080/info/products/100001
```

### Статьи о продукте

**Создать статью**
```
curl -s -X POST -d '{"name":"Статья про НОВЫЙ продукт","productId":100000,"content":"Содержание статьи о НОВОМ продукте"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/info/articles
```
**Получить статью**
```
curl -s http://localhost:8080/info/articles/100004
```
**Получить все статьи**
```
curl -s http://localhost:8080/info/articles
```
**Обновить статью**
```
curl -s -X PUT -d '{"id":100004,"name":"Обновленная статья про второй продукт","productId":100000,"content":"Обновленное содержание статьи-2 о втором продукте"}' -H 'Content-Type: application/json' http://localhost:8080/info/articles/100004
```
**Удалить статью**
```
curl -s -X DELETE http://localhost:8080/info/articles/100004
```