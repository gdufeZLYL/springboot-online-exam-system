package com.zzqnxx.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/exam/user")
public class StudentController {

    @RequestMapping("/index")
    public String index(){
        return "qexz want 学习";
    }
}
