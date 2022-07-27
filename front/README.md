Фронт контроллер
----------------

В проекте используются:

- _Spring Web_
- _Spring RestTemplate_
- _Lombok_

___

### Требования

- JDK 17

---

### Запуск
* Запуск используя Maven Spring Boot plugin
    ```bash
    ./mvnw spring-boot:run
    ```
* Запуск JAR
  * Собрать проект
      ```bash
      ./mvnw clean install
      ```
  * Запустить
      ```bash
      java -jar target/front-0.1.0-SNAPSHOT.jar
      ```
* Запустить контейнер
  ```shell
  docker build -t companiesinfo/front-controller .
  ```
  ```shell
  docker run --name frontController -d --rm -p 8080:8080 companiesinfo/front-controller:latest
  ```

___

### Просмотр запущенного микросервиса
[http://localhost:8080/info](http://localhost:8080/info)