package hello.core.scope;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import hello.core.scope.PrototypeTest.PrototypeBean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest1 {


    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        bean1.addCount();
        assertThat(bean1.getCount()).isEqualTo(1);

        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        bean2.addCount();

        assertThat(bean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUserPrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);

        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();

        assertThat(count2).isEqualTo(1);

    }

    @Scope("singleton")// 안해줘도 되지만 싱글톤 빈이라고 명시 (디폴트가 싱글톤 빈이기 때문에 안해도 된다.)
    static class ClientBean {
//        private final PrototypeBean prototypeBean; // 생성시점에 주입이 된다.
        // 다른 메서드를 호출해도 생성시점에 주입이 된 prototypeBean 인스턴스에서 호출이 된다.

//        @Autowired
//        ApplicationContext applicationContext;

        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
        private Provider<PrototypeBean> prototypeBeanProvider;

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            PrototypeBean prototypeBean = prototypeBeanProvider.get();

            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
//        public int logic() {
////            PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);
//            prototypeBean.addCount();
//            return prototypeBean.getCount();
//        }

    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("prototypeBean.init = " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }


    }
}
