package com.example.hfspring.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.hfspring.Model.TXResponse;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Test
    public void testJson(){
        String json = "[{\"TxId\":\"0206837e1082b1a473fce29258688299af99df6c4a82d1f1ef4ae0ef9941a43f\", " +
                "\"Value\":{\"docType\":\"relic\",\"id\":\"2015212154\",\"name\":\"haochide\",\"owner\":\"jarvist\"}, " +
                "\"Timestamp\":\"2019-05-26 07:24:36.420216266 +0000 UTC\", \"IsDelete\":\"false\"}," +
                "{\"TxId\":\"80d9b04bc6f2e5af03085f7cd5d70ea7bfb89694aee0a3adfd678eddea0e82f5\", " +
                "\"Value\":{\"docType\":\"relic\",\"id\":\"2015212154\",\"name\":\"haochide\",\"owner\":\"huahua\"}, " +
                "\"Timestamp\":\"2019-05-26 14:44:30.306118308 +0000 UTC\", \"IsDelete\":\"false\"}]";
//        }
        List<TXResponse> list = JSONArray.parseArray(json,TXResponse.class);
//        String Txid = responses.getTxId();
        logger.info(list.get(0).getValue().getOwner());
    }

}

