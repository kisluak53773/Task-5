package com.company.threads.entity;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Bar {
    public static final int HOOKAH_MAX=5;
    public static final int QUEUE_MAX_SIZE=10;
    private static Bar instance;
    private static AtomicBoolean flag=new AtomicBoolean(false);
    private static ReentrantLock lock=new ReentrantLock();
    private static int visitors=0;
    private static int queue=0;

    private Bar() {
    }

    public static Bar getInstance(){
        if(!flag.get()){
            try{
                lock.lock();
                if(instance==null){
                    instance=new Bar();
                    flag.set(true);
                }
            }finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public int getVisitors() {
        return visitors;
    }

    public int getQueue() {
        return queue;
    }

    public void takeHookah(){
        visitors++;
    }

    public void takeLine(){
        queue++;
    }

    public void payBill(){
        visitors--;
    }

    public void leaveQueue(){
        queue--;
    }
}
