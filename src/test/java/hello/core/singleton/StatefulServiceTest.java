package hello.core.singleton;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
                TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA : A 사용자가 10000원을 주문
        int userAPrice = statefulService1.order("userA", 10000);

        // ThreadB : B 사용자가 20000원을 주문
        int userBPrice = statefulService2.order("useerB", 20000);

        // ThreadA : 사용자 A 가 주문 금액을 조회
//        int price = statefulService1.getPrice();  // 10000원이어야 하는데 20000원이 나옴
//        System.out.println("price = " + price);

        Assertions.assertThat(userAPrice).isEqualTo(10000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}