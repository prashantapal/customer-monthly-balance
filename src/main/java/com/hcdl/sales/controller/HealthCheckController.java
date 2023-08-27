package com.hcdl.sales.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Ok");
    }
}
