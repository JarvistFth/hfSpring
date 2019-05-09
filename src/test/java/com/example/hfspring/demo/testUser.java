package com.example.hfspring.demo;

import com.example.hfspring.Model.Users;
import com.example.hfspring.fabric.FabricManager;
import com.example.hfspring.service.Impl.UserServiceImp;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.HFCAInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testUser {

    private static final Logger logger = Logger.getLogger(testUser.class);
    @Autowired
    private UserServiceImp userServiceImp;

    @Test
    public void testInsert() {


    }

//    @Test
//    public void testLogin(){
//        Users users = new Users();
//        users.setName("jjw");
//        users.setPassword(DigestUtils.sha1Hex("jjw".getBytes()));
//        boolean loginok = userServiceImp.loginVerified(users);
//        if(loginok){
//            LOGGER.info("correct!!!! login ok!");
//
//        }
//    }

}

