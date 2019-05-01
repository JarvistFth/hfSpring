package com.example.hfspring.demo;

import com.example.hfspring.Model.Users;
import com.example.hfspring.fabric.FabricManager;
import com.example.hfspring.service.Impl.UserServiceImp;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
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
    public void testInsert(){
//        Users users = new Users();
//        users.setName("jjw");
        new Thread(){
            @Override
            public void run() {
                Users users = new Users();
                users.setName("jar");
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                Users users = new Users();
                users.setName("jjw");
            }
        }.start();

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

    @Test
    public void testUpdate(){
        Users users = new Users();
        users.setName("ojbk");
    }

    private FabricManager manager ;
    private FabricStore fabricStore;
    private FabricUser user;

//    @Test
//    public void testFabricManager(){
//        String userName = "admin";
//        HFConfig config = HFConfig.getConfig();
//        String orgName = config.getClientOrg().getName();
//        try{
//            File tmpStoreFile = new File("/root/project/hfspring/src/main/resources/_admin.properties");
//            fabricStore = new FabricStore(tmpStoreFile);
//            user = fabricStore.getMember(userName,orgName);
//            manager = new FabricManager(user);
////            manager.setFabricStore(fabricStore);
//            manager.invoke("getHistoryForMarble","2015212153");
//        }catch (Exception e){
//            logger.error(e);
//        }
//    }
}
