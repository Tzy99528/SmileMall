package com.smile.mall.admin.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class testController 
{
    @PostMapping("/test")
    public String test()
    {
        return "test";
    }
    
}
