package com.example.hfspring.service.Impl;

import com.example.hfspring.Utils.ConstantUtils;
import com.example.hfspring.fabric.FabricManager;
import com.example.hfspring.demo.FabricStore;
import com.example.hfspring.demo.FabricUser;
import com.example.hfspring.service.TransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class TransactionServiceImp implements TransactionService {

    private Log logger = LogFactory.getLog(TransactionServiceImp.class);
    private FabricManager manager ;
    private FabricStore fabricStore;
    private FabricUser user;





    @Override
    public String initRelics(String relicId, String relicName, String ownerName) {
        try{
            return manager.invoke("initMarble",relicName,ownerName);
        }catch (Exception e){
            logger.error(e);
            return e.getMessage();
        }finally {
            manager.removeALL();
        }
    }

    @Override
    public String queryRelics(String relicId) {
        try{
            return manager.invoke(ConstantUtils.CC_QUERYBYID,relicId);
        }catch (Exception e){
            logger.error(e);
            return e.getMessage();
        }finally {
            manager.removeALL();
        }
    }

    @Override
    public String queryAllRelics(String ownerName) {
        try{
            return manager.invoke(ConstantUtils.CC_QUERYBYOWNER,ownerName);
        }catch (Exception e){
            return e.getMessage();
        }finally {
            manager.removeALL();
        }
    }

    @Override
    public String queryHistory(String id) {
        String ret = "";
        try{
            return manager.invoke(ConstantUtils.CC_QUERY_HISTORY,id);
        }catch (Exception e){
            return e.getMessage();
        }finally {
            manager.removeALL();
        }
    }

    @Override
    public String deleteRelics(String id) {
        try{
            return manager.invoke(ConstantUtils.CC_DELETE,id);
        }catch (Exception e){
            return e.getMessage();
        }finally {
            manager.removeALL();
        }
    }

    @Override
    public String invokeTransaction(String relicId, String newOwnerName) {
        try{
            return manager.invoke(ConstantUtils.CC_TRANSFER,relicId,newOwnerName);
        }catch (Exception e){
            return e.getMessage();
        }finally {
            manager.removeALL();
        }
    }

    @Override
    public boolean initialize(String userName, String orgName) {
        try{
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/_admin.properties");
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


}
