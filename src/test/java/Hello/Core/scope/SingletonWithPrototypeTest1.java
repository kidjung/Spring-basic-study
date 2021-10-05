package Hello.Core.scope;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;
import java.sql.SQLOutput;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientPrototype(){
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(1);
    }

//    @Scope("singleton") //생성시점에 주입완료
//    @RequiredArgsConstructor
//    static class ClientBean {
////        private final PrototypeBean prototypeBean;
//
//        //무식한 방법 DL (Dependency Lookup)
//        private final ApplicationContext ac;
//
////        public ClientBean(PrototypeBean prototypeBean) {
////            this.prototypeBean = prototypeBean;
////        }
//
//        public int logic() {
//            //무식한 방법 -> 항상 생성되길 바랄때
//            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
//            // ---------------
//            prototypeBean.addCount();
//            return prototypeBean.getCount();
//        }
//    }

    @Scope("singleton") //생성시점에 주입완료
    @RequiredArgsConstructor
    static class ClientBean {
//        private final PrototypeBean prototypeBean;

//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

        public int logic() {

//            PrototypeBean prototypeBean = prototypeBeanObjectProvider.getObject();
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
//            this.prototypeBean.addCount();
//            return this.prototypeBean.getCount();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount(){
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void close() {
            System.out.println("PrototypeBean.close");
        }
    }
}
