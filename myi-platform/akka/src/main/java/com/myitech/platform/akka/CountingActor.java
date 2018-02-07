package com.myitech.platform.akka;

import akka.actor.UntypedActor;

import javax.annotation.Resource;

/**
 * 2017/2/20 19:53 <br>
 * Description:
 *
 * @author tonyan
 */
public class CountingActor extends UntypedActor {
    public static class Count {

    }

    public static class Get {

    }

    @Resource
    private CountingService countingService;

    private int count = 0;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Count) {
            count = countingService.increment(count);
        } else if (message instanceof Get) {
            getSender().tell(count, getSelf());
        } else {
            unhandled(message);
        }
    }
}
