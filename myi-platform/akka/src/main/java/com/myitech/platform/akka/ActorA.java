package com.myitech.platform.akka;

import akka.actor.UntypedActor;

/**
 * 2017/2/17 9:40 <br>
 * Description:
 *
 * @author tonyan
 */
public class ActorA extends UntypedActor{

    public static enum Msg{
        GREET, DONE;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == Msg.GREET) {
            System.out.println("ActorA receive message = [" + message + "]");

            // 这里getSender()获取消息源Actor，并调用tell方法进行消息发送；
            getSender().tell(message, getSelf());
        } else {
            unhandled(message);
        }

    }
}
