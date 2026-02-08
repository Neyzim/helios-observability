package com.helios.helios.observability.infrastructure.controllers;

import com.helios.helios.observability.infrastructure.persistency.implementations.AlertRepositoryImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/alert")
public class AlertController {

    private final AlertRepositoryImpl alertRepository;


    public AlertController(AlertRepositoryImpl alertRepository) {
        this.alertRepository = alertRepository;
    }

    //TODO

}
