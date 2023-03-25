package com.kovil.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class home {

    @RequestMapping("/")
    public String print(){
     return "Hello World";
    }

    @RequestMapping("/devtools")
    public String devtools(){
        return "Dev tools testing";
    }
}
