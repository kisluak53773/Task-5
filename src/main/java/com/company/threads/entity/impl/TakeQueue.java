package com.company.threads.entity.impl;

import com.company.threads.entity.Bar;
import com.company.threads.entity.ClientStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class TakeQueue implements ClientStatus {
    private Bar bar= Bar.getInstance();
    private final static Logger logger= LogManager.getLogger();
    @Override
    public void doAction() {
        bar.takeLine();
        logger.info("Waiting inside");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bar.leaveQueue();
    }
}
