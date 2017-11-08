package unclediga.tut.spring.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AwareBean implements ApplicationContextAware,BeanNameAware,ApplicationEventPublisherAware{
    String name;
    ApplicationContext ctx;
    ApplicationEventPublisher eventPublisher;

    @PostConstruct
    public void init() {

        String simpleName = this.getClass().getSimpleName();
        System.out.println(simpleName + " > My name is " + name);

        if (ctx != null) {
            System.out.println(simpleName  +" > My context is "+ ctx.getClass().toString());
        } else {
            System.out.println(simpleName + " > My context is not set");
        }

        if (eventPublisher != null) {
            System.out.println(simpleName + " > My event publisher is "+ eventPublisher.getClass().toString());
        } else {
            System.out.println(simpleName + " > My event publisher is not set");
        }
    }

    @Override
    public void setBeanName(String name) {
        this.name = name;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

}
