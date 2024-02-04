package com.bento.btask.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    @PostMapping
    public void createNewUser(@RequestBody String name) {
        System.out.println("post request received on users micro service: "+ name);
    }

}
