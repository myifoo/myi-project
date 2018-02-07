package com.myitech.platform.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * 2017/2/17 10:06 <br>
 * Description:
 *
 * @author tonyan
 */
public class ActorB  extends UntypedActor{
    @Override
    public void onReceive(Object message) throws Exception {
        if (message == ActorA.Msg.GREET) {
            getContext().stop(getSelf());
            System.out.println("ActorB receive message = [" + message + "]");
        } else {
            unhandled(message);
        }
    }

    @Override
    public void preStart() throws Exception {
        final ActorRef greeter = getContext().actorOf(Props.create(ActorA.class));

        System.out.println("ActorB preStart .... ");
        System.out.println("ActorB tell ActorA -- " + ActorA.Msg.GREET);
        greeter.tell(ActorA.Msg.GREET, getSelf());
    }

}
