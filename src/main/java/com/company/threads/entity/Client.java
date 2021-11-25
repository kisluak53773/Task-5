package com.company.threads.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    private ReentrantLock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();
    private final static Logger logger= LogManager.getLogger();
    private final int friendsAmount;
    private Bar bar=Bar.getInstance();


    public Client(int friendsAmount) {
        this.friendsAmount = friendsAmount;
    }

    private void withFriend(){
        logger.info("Smoke with friend");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Paying and leaving");
    }

    public void takeTable(){
        bar.takeHookah();
        logger.info("Smoking");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bar.payBill();
    }

    public void takeLine(){
        bar.takeLine();
        logger.info("Waiting inside");
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bar.leaveQueue();
    }

    public void waitingOutside(){
        logger.info("Waiting outside");
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
