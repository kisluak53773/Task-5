package com.company.threads.main;

import com.company.threads.entity.Client;
import com.company.threads.filler.ClientCreator;
import com.company.threads.reader.Reader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private final static Logger logger= LogManager.getLogger();
    public static void main(String[] args) {
        ClassLoader loader=Main.class.getClassLoader();
        ExecutorService service= Executors.newFixedThreadPool(15);
        try {
            List<Client> clients=new ClientCreator().fill(new Reader().read(loader.getResource("data/info.txt").toURI()));
            for(Client object:clients){
                service.submit(object);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
