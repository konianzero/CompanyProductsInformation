Микросервис персистентного уровня
---------------------------------

В проекте используются:

- _Spring Web_
- _Spring Data_
- _HSQLDB_
- _Lombok_

___

Многомодульный проект:
  - [model](model)
  - [repository](repository)

___

### Требования

- JDK 17
___

### Запуск
* Запуск используя Maven Spring Boot plugin
    ```bash
    ./mvnw spring-boot:run -pl repository
    ```

* Запуск JAR
  * Собрать проект
      ```bash
      ./mvnw clean install
      ```
  * Запустить
      ```bash
      java -jar repository/target/repository-0.1.0-SNAPSHOT.jar
      ```

### Просмотр запущенного микросервиса
[http://localhost:8081](http://localhost:8081)