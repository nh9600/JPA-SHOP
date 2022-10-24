package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //하위 모든 걸 스캔
public class JpashopApplication {

    public static void main(String[] args) {

        SpringApplication.run(JpashopApplication.class, args);
    }

}
