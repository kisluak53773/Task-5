package com.company.threads.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Client extends Thread{
    private ReentrantLock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();
    private final static Logger logger= LogManager.getLogger();
    private final boolean lastFriend;
    private final boolean withFriends;
    private Bar bar=Bar.getInstance();
    private boolean visiting=true;
    private static AtomicBoolean flag=new AtomicBoolean(false);

    public Client(boolean lastFriend, boolean withFriends) {
        this.lastFriend = lastFriend;
        this.withFriends = withFriends;
    }

    private void withFriend(){
        logger.info("Smoke with friend");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Paying and leaving");
        visiting=false;
    }

    private void takeTable(){
        bar.takeHookah();
        logger.info("Smoking");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bar.payBill();
        logger.info("Paying and leaving");
        visiting=false;
    }

    private void takeLine(){
        bar.takeLine();
        logger.info("Waiting inside");
        try {
            condition.await(1,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bar.leaveQueue();
    }

    private void waitingOutside(){
        logger.info("Waiting outside");
        try {
            condition.await(1,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void enterTheBar(){
        try {
            lock.lock();
            while (visiting) {
                if (bar.getVisitors() < Bar.HOOKAH_MAX) {
                    if(withFriends && !lastFriend){
                        withFriend();
                    }else if(lastFriend){
                        logger.info("Last friend");
                        takeTable();
                    }else {
                        takeTable();
                    }
                } else if (bar.getQueue() < Bar.QUEUE_MAX_SIZE) {
                    takeLine();
                } else {
                    waitingOutside();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        enterTheBar();
    }
}
