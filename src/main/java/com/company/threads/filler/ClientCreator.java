package com.company.threads.filler;

import com.company.threads.entity.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientCreator {
    private static final String STRING_DELIMITER="\s";
    public List<Client> fill(List<String> clientData){
        List<Client> clientList=new ArrayList<>();
        List<String[]> buffer=new ArrayList<>();
        for(String str:clientData){
            buffer.add(str.split(STRING_DELIMITER));
        }
        for(int i=0,c=0;i<buffer.size();i++){
            clientList.add(new Client(Boolean.parseBoolean(buffer.get(i)[c]),Boolean.parseBoolean(buffer.get(i)[c+1])));
        }
        return clientList;
    }
}
