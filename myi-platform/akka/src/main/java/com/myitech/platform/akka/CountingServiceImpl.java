package com.myitech.platform.akka;

import org.springframework.stereotype.Service;

/**
 * 2017/2/20 19:59 <br>
 * Description:
 *
 * @author tonyan
 */
@Service("countingService")
public class CountingServiceImpl implements CountingService {
    public int increment(int count) {
        System.out.println("increase "+ count + "by 1.");
        return count + 1;
    }
}
