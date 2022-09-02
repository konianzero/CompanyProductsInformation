package org.company.front;

import org.company.cache.config.JmsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        //DataSourceTransactionManagerAutoConfiguration.class,
        //HibernateJpaAutoConfiguration.class
})
//@ComponentScan(basePackages = {"org.company.cache", "org.company.front"})
public class FrontController {

    public static void main(String[] args) {
        SpringApplication.run(FrontController.class, args);
    }

}
