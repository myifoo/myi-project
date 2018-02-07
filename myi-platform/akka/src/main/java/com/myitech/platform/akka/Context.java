package com.myitech.platform.akka;

import akka.actor.Props;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * 2017/2/20 18:34 <br>
 * Description:
 *
 * @author tonyan
 */
@Component("context")
public class Context implements ApplicationContextAware{
    private ApplicationContext applicationContext;

    public Props props(String actorBeanName, Object ... args) {
        return Props.create(ActorProducer.class, applicationContext, actorBeanName, args);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
