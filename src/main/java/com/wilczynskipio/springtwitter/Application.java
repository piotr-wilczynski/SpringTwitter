/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wilczynskipio.springtwitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Piotr Wilczynski
 */
@SpringBootApplication
public class Application {

    //@Autowired
    //UserDataDAO userDataDAO;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
