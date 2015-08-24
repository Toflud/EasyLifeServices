package com.easylife.controller;

import com.easylife.model.PortFolioV2;
import com.easylife.model.UserIntent;
import com.easylife.model.UserList;
import org.apache.http.HttpHeaders;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Created by cpe on 01/06/2015.
 */
@RestController
@EnableAutoConfiguration
public class PortFolioController {

    private final PortFolioV2 portFolioV2 = new PortFolioV2() ; {
        portFolioV2.setUserId("Christophe");
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "EasyLife home !";
    }

    @RequestMapping(value = "/user/list/{listName}", method = RequestMethod.GET)
    UserList userList(@PathVariable String listName) {
        return portFolioV2.getUserList(PortFolioV2.PreDefinedListName.valueOf(listName)) ;
    }

    @RequestMapping(value="/user/list/{listName}", method = RequestMethod.POST)
    ResponseEntity newUserIntent(@PathVariable String listName, @RequestBody UserIntent intent) {
        portFolioV2.newItem(PortFolioV2.PreDefinedListName.valueOf(listName).name(), intent);
        // return something acknolede ?
        return new ResponseEntity<>(null, null, HttpStatus.CREATED);
    }

}
