package com.easylife.controller;

import com.easylife.model.PortFolioV2;
import com.easylife.model.UserList;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cpe on 01/06/2015.
 */
@RestController
@EnableAutoConfiguration
public class PortFolioController {

    private final PortFolioV2 portFolioV2 = new PortFolioV2() ;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "EasyLife home !";
    }

    @RequestMapping("/user/list")
    UserList userList() {
        portFolioV2.setUserId("Christophe");
        return portFolioV2.getUserList(PortFolioV2.PreDefinedListName.todo) ;
    }
}
