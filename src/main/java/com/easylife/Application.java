package com.easylife;

import com.easylife.controller.PortFolioController;
import org.springframework.boot.SpringApplication;

/**
 * Created by cpe on 01/06/2015.
 */
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PortFolioController.class, args);
    }

}
