package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication //하위 모든 걸 스캔
public class JpashopApplication {

    public static void main(String[] args) {

        SpringApplication.run(JpashopApplication.class, args);
    }

    @Bean
    Hibernate5Module hibernate5Module(){
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        //hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING,true); 쓰면 안 됨(엔티티 노출 금지)
        return hibernate5Module;
    }

}
