package com.myitech.platform.akka;

import akka.actor.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 2017/2/17 9:34 <br>
 * Description:
 *
 * @author tonyan
 */
public class HelloAkka {
    private static AbstractApplicationContext context;

    public static void main(String[] args) {
        /**
         * ActorSystem是一个重量级的模块，底层会分配1——N个线程，一般是一个应用创建一个ActorSystem。
         */
        ActorSystem system = ActorSystem.create("HelloAkka");
        ActorRef actor = system.actorOf(Props.create(ActorB.class));
        system.actorOf(Props.create(HelloAkka.Terminator.class, actor), "terminator");

        context = new ClassPathXmlApplicationContext("/spring-application.xml");
    }

    public static class Terminator extends UntypedActor {
        private ActorRef actorRef = null;

        public Terminator(ActorRef ref) {
            System.out.println("Terminator init ...");
            actorRef = ref;
            getContext().watch(actorRef);
        }

        @Override
        public void onReceive(Object message) throws Exception {
            if (message instanceof Terminated) {
                System.out.println("Start to terminate ...");
                getContext().system().awaitTermination();
            } else {
                unhandled(message);
            }
        }
    }
}
