Удаленный сервис
----------------

В проекте используются:

- _Spring JMS_
- _Lombok_

___

### Требования

- JDK 17

---

### Запуск

* Запустить контейнер c ActiveMQ
    ```shell
   docker run --name activemq -d \
   -e ACTIVEMQ_NAME=amqp-srv0 \
   -e ACTIVEMQ_REMOVE_DEFAULT_ACCOUNT=true \
   -e ACTIVEMQ_ADMIN_LOGIN=admin \
   -e ACTIVEMQ_ADMIN_PASSWORD=admin \
   -e ACTIVEMQ_MIN_MEMORY=1024 \
   -e ACTIVEMQ_MAX_MEMORY=4096 \
   -e ACTIVEMQ_ENABLED_SCHEDULER=true \
   -p 8161:8161 \
   -p 61616:61616 \
   webcenter/activemq:5.14.3
   ```
  страница администратора (userId/password - admin/admin) - http://localhost:8161/admin/  


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
        java -jar target/remote-0.0.1-SNAPSHOT.jar
        ```

* Запустить контейнер
    ```shell
    docker build -t companiesinfo/remote .
    ```
    ```shell
    docker run --name remote -d --rm companiesinfo/remote:latest
    ```
     