Фронт контроллер
----------------

В проекте используются:

- _Spring Web_
- _Spring RestTemplate_
- _Spring JMS_
- _Hazelcast_
- _Lombok_

___

Многомодульный проект:
- [controller](controller)
- [cache](cache)

___

### Требования

- JDK 17
- Docker 20

---

### Запуск
* Запуск используя Maven Spring Boot plugin
    ```bash
    ./mvnw spring-boot:run -pl controller
    ```
* Запуск JAR
  * Собрать проект
      ```bash
      ./mvnw clean install
      ```
  * Запустить
      ```bash
      java -jar controller/target/controller-0.1.0-SNAPSHOT.jar
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

[ActiveMQ панель администратора](http://localhost:8161/admin) (User Name: admin / Password: admin)