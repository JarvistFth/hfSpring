package com.example.hfspring.service.Impl;

import com.example.hfspring.fabric.FabricManager;
import com.example.hfspring.demo.FabricStore;
import com.example.hfspring.demo.FabricUser;
import com.example.hfspring.service.TransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class TransactionServiceImp implements TransactionService {

    private Log logger = LogFactory.getLog(TransactionServiceImp.class);
    private FabricManager manager ;
    private FabricStore fabricStore;
    private FabricUser user;





    @Override
    public void initRelics(String relicId, String relicName, String ownerName) {
        try{
            manager.invoke("initMarble",relicName,ownerName);
        }catch (Exception e){
            logger.error(e);
        }finally {
            manager.removeALL();
        }
    }

    @Override
    public void queryRelics(String relicId) {

    }

    @Override
    public void queryAllRelics(String ownerName) {

    }

    @Override
    public void queryHistory(String id) {
        try{
            manager.invoke("getHistoryForMarble",id);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            manager.removeALL();
        }

    }

    @Override
    public void deleteRelics(String id) {

    }

    @Override
    public void invokeTransaction(String relicId, String newOwnerName) {

    }

    @Override
    public boolean initialize(String userName, String orgName) {
        try{
            File tmpStoreFile = new File("src/main/resources/_" + userName  +".properties");
            fabricStore = new FabricStore(tmpStoreFile);
            user = fabricStore.getMember(userName,orgName);
            manager = FabricManager.getInsatance();
            manager.setClient(user);
            manager.setChannel();
//            manager.setFabricStore(fabricStore);
        }catch (Exception e){
            logger.error(e);
        }
        return false;
    }

    @Override
    public void removeContext() {

    }
}
