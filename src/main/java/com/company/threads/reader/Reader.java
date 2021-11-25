package com.company.threads.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reader {
    private final static Logger logger= LogManager.getLogger();
    public List<String> read(URI path){
        List<String> lines = new ArrayList<>();
        try (Stream<String> lineStream = Files.newBufferedReader(Path.of(path)).lines()) {
            lines = lineStream.collect(Collectors.toList());
        }catch (IOException e){
            logger.error("IO Exception");
        }
        return lines;
    }
}
