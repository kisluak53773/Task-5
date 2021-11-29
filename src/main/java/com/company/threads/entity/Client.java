package com.company.threads.entity;

import com.company.threads.entity.impl.TakeTable;
import com.company.threads.entity.impl.WithFriends;
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
    private ClientStatus status;
    private Bar bar=Bar.getInstance();
    private boolean visiting=true;
    private static AtomicBoolean flag=new AtomicBoolean(false);

    public Client(boolean lastFriend, boolean withFriends) {
        this.lastFriend = lastFriend;
        this.withFriends = withFriends;
    }

    private void setStatus(ClientStatus status){
        this.status=status;
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
                        setStatus(new WithFriends());
                        doAction();
                        visiting=false;
                    }else if(lastFriend){
                        logger.info("Last friend");
                        setStatus(new TakeTable());
                        doAction();
                        visiting=false;
                    }else {
                        setStatus(new TakeTable());
                        doAction();
                        visiting=false;
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

    private void doAction(){
        status.doAction();
    }

    @Override
    public void run() {
        enterTheBar();
    }
}
