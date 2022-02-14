Информационная система по продуктам компании
--------------------------------------------

_**Spring Boot**_

Система позволяет создавать (получать/изменять/удалять) информацию о продуктах компании, а также
информационные статьи по продуктам.

Так же можно:
- сортировать продукты и статьи по определенному полю
- отфильтровать продукты по названию, описанию, стоимости внедрения (от - до)
- отфильтровать статьи по названию, контенту, дате создания (с - по)

Для фильтрации и сортировки репозитории (
[ProductRepository](src/main/java/org/company/product/info/repository/ProductRepository.java), 
[ArticleRepository](src/main/java/org/company/product/info/repository/ArticleRepository.java)) 
реализуют JpaSpecificationExecutor. Для создания Specification используется 
[JpaSpecificationUtil](src/main/java/org/company/product/info/util/JpaSpecificationUtil.java).

Для предотвращения зацикливания применяется аннотация [@JsonIgnoreProperties](https://stackoverflow.com/a/39573255) в 
[Article](src/main/java/org/company/product/info/model/Article.java).

---

В проекте используются:

- _Spring Boot_
- _HSQLDB_ для хранения данных
- _Lombok_ для кодогенерации

[Application Properties](src/main/resources/application.yaml)

---

### Требования

- JDK 15
- Maven 3

---

### Запуск
```
mvn spring-boot:run
```

URL: [http://localhost:8080/info](http://localhost:8080/info)

---

### Документация API

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

### Команды CURL

Можно использовать IntelliJ IDEA HTTP client с [endpoints.http](endpoints.http).

#### Информация о продукте

**Создать информацию о продукте**
```shell
curl -X POST -d '{"name":"Новый продукт","description":"Описание Нового продукта","implementationCost":300000}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/info/products
```
**Получить информацию о продукте**
```shell
curl -X GET http://localhost:8080/info/products/100001
```
**Получить информацию по всем продуктам**
```shell
curl -X GET http://localhost:8080/info/products
```
 - Фильтрация и сортировка(по умолчанию нисходящая)
```shell
curl -X GET http://localhost:8080/info/products?filterBy=name&filter=дин
```
```shell
curl -X GET http://localhost:8080/info/products?filterBy=implementationCost&costFrom=200100
```
```shell
curl -X GET http://localhost:8080/info/products?sortBy=implementationCost
```
**Обновить информацию о продукте**
```shell
curl -X PUT -d '{"id":100001,"name":"Обновленный продукт","description":"Описание обновленного продукта","implementationCost":450000}' -H 'Content-Type: application/json' http://localhost:8080/info/products/100001
```
**Удалить информацию о продукте**
```shell
curl -X DELETE http://localhost:8080/info/products/100001
```

#### Статьи о продукте

**Создать статью**
```shell
curl -X POST -d '{"name":"Статья про НОВЫЙ продукт","productId":100000,"content":"Содержание статьи о НОВОМ продукте"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/info/articles
```
**Получить статью**
```shell
curl -X GET http://localhost:8080/info/articles/100004
```
**Получить все статьи**
```shell
curl -X GET http://localhost:8080/info/articles
```
 - Фильтрация и сортировка(по умолчанию нисходящая)
```shell
curl -X GET http://localhost:8080/info/articles?filterBy=name&filter=пер
```
```shell
curl -X GET http://localhost:8080/info/articles?filterBy=date&toDate=2021-09-20
```
```shell
curl -X GET http://localhost:8080/info/articles?sortBy=id
```
**Обновить статью**
```shell
curl -X PUT -d '{"id":100004,"name":"Обновленная статья про второй продукт","productId":100000,"content":"Обновленное содержание статьи-2 о втором продукте"}' -H 'Content-Type: application/json' http://localhost:8080/info/articles/100004
```
**Удалить статью**
```shell
curl -X DELETE http://localhost:8080/info/articles/100004
```
