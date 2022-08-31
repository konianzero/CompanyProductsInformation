Информационная система по продуктам компании
--------------------------------------------

- [Front микросервис](front) (Front Controller)
- [Persistence микросервис](persistence) (Persistence layer)
- [Remote микросервис](remote) (Remote service)

___

### Требования

- JDK 17
- Docker 20

___

### Запуск

* Запустить с maven wrapper и указанным JDK: [run.sh](run.sh)

* Запустить в терминале:
    ```shell
    mvn -f persistence/pom.xml clean install
    mvn -f remote/pom.xml clean install
    mvn -f front/pom.xml clean install
    docker-compose -f microservices.yml up --build
    ```

___

### Просмотр запущенного Front микросервиса

[http://localhost:8080/info](http://localhost:8080/info)
- Вы можете использовать HTTP-клиент IntelliJ IDEA с [endpoints.http](endpoints.http).
- ActiveMQ панель администратора (User Name: admin / Password: admin) [http://localhost:8161/admin](http://localhost:8161/admin)
- Мониторинг состояния Persistence микросервис [http://localhost:8081/monitor](http://localhost:8081/monitor)