package repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("model")
public class PersistenceApplication {
// TODO
//  - https://www.baeldung.com/spring-data-derived-queries
//  - https://www.baeldung.com/rest-api-search-language-spring-data-specifications
    public static void main(String[] args) {
        SpringApplication.run(PersistenceApplication.class, args);
    }
}
