package com.example.se_backend.controller;


import com.example.se_backend.POJ.ClockPOJ;
import com.example.se_backend.dao.UserDao;
import com.example.se_backend.entity.UserBasicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClockController {
    @Autowired
    private UserDao user_dao;



    @RequestMapping("/getFriendClock")
    public List<ClockPOJ> getFriendClock(@RequestParam(value = "id")Integer id){


        return null;

    }


}
