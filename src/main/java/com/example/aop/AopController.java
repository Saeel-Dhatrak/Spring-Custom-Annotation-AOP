package com.example.aop;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AopController {

    @Autowired
    private AopService aopService;

    @GetMapping("/get")
    public String doRandomThings(){
        return aopService.doRandomThings();
    }

}
