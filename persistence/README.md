Микросервис персистентного уровня
---------------------------------

_**Spring Web | Spring Data | HSQL**_

___

Многомодульный проект:
  - [model](model)
  - [repository](repository)
  
В проекте используются:

- _Spring Boot_
- _HSQLDB_ для хранения данных
- _Lombok_ для кодогенерации

___

### Запуск
* Запуск используя Maven Spring Boot plugin
    ```bash
    mvn spring-boot:run -pl repository
    ```

* Запуск JAR
  * Собрать проект
      ```bash
      mvn clean install
      ```
  * Запустить
      ```bash
      java -jar repository/target/repository-0.1.0-SNAPSHOT.jar
      ```

### Просмотр запущенного микросервиса
[http://localhost:8081](http://localhost:8081)