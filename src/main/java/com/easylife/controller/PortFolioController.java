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
@RequestMapping(value="/user/list/{listName}")
public class PortFolioController {

    private final PortFolioV2 portFolioV2 = new PortFolioV2() ; {
        portFolioV2.setUserId("Christophe");
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "EasyLife API home !";
    }

    @RequestMapping(method = RequestMethod.GET)
    UserList userList(@PathVariable String listName) {
        return portFolioV2.getUserList(PortFolioV2.PreDefinedListName.valueOf(listName)) ;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity newUserIntent(@PathVariable String listName, @RequestBody UserIntent intent) {
        portFolioV2.newItem(PortFolioV2.PreDefinedListName.valueOf(listName).name(), intent);
        // return something acknolede ?
        return new ResponseEntity<>(null, null, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity putUserList(@PathVariable String listName, @RequestBody UserList userList) {
        if(!userList.getListeName().equals(listName))
            return new ResponseEntity(HttpStatus.BAD_REQUEST) ;
        if(!userList.getUserId().equals(portFolioV2.getUserId()))
            return new ResponseEntity(HttpStatus.BAD_REQUEST) ;
        portFolioV2.putUserList(userList) ;
        return new ResponseEntity(HttpStatus.OK) ;
    }


}
