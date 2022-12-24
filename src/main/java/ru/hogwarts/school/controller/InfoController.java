package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

@RestController
public class InfoController {
    @Autowired
    InfoService infoService;

    @GetMapping("/getPort")
    public ResponseEntity<Integer> getPort(){
        Integer port = infoService.getPort();
        return ResponseEntity.ok(port);
    }
}
