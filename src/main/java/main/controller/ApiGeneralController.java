package main.controller;

import main.service.ApiGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGeneralController {

    @Autowired
    ApiGeneralService ags;

    @GetMapping("/api/init")
    public ResponseEntity getInit() {
        return new ResponseEntity<>(ags.getData(), HttpStatus.OK);
    }
}
