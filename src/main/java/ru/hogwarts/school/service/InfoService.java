package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.ServerSocket;

@Service
public class InfoService {
    Logger logger = LoggerFactory.getLogger(InfoService.class);

    @Value("${server.port}")
    private int port;
    public Integer getPort(){
        logger.info("The method {} was called. Port = {}", "getPort", port);
        return port;
    }
}
