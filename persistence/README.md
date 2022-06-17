Микросервис персистентного уровня
---------------------------------

_**Spring Data Rest | HSQL**_

### Запуск
* Запуск используя Maven Spring Boot plugin
    ```bash
    mvn spring-boot:run
    ```

* Запуск JAR
  * Собрать проект
      ```bash
      mvn clean install
      ```
  * Запустить
      ```bash
      java -jar target/persistence-0.0.1-SNAPSHOT.jar
      ```

### Просмотр запущенного микросервиса
[http://localhost:8081](http://localhost:8081)