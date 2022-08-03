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
    mvn -f front/pom.xml clean install -DskipTests
    docker-compose -f microservices.yml up --build
    ```

___

### Просмотр запущенного Front микросервиса
[http://localhost:8080/info](http://localhost:8080/info)