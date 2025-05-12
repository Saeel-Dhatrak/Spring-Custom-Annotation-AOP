package com.example.aop;

import org.springframework.stereotype.Service;

@Service public class AopService {

    @TimeMonitor
    public String doRandomThings(){
        for(long i=0; i< 100000000L; i++){

        }
        return "Something";
    }
}
