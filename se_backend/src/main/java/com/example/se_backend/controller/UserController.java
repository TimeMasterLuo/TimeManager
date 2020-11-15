package com.example.se_backend.controller;


import com.example.se_backend.POJ.UserPOJ;
import com.example.se_backend.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserDao userdao;

    @RequestMapping("/getFriends")
    UserPOJ getFriends(@RequestParam(value = "id")Integer id)
    {

        System.out.print(id);


        return null;
    }

}
