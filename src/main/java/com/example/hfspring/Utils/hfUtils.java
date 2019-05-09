package com.example.hfspring.Utils;

import com.example.hfspring.demo.FabricStore;
import com.example.hfspring.demo.FabricUser;
import com.example.hfspring.fabric.FabricManager;

import java.io.File;

public class hfUtils {

    private static FabricStore fabricStore;

    public static FabricManager getManager() {
        return manager;
    }

    private static FabricManager manager = new FabricManager();

    public static boolean initialize(String userName,String orgName){
        try{
            File tmpStoreFile = new File("src/main/resources/" + userName  +".properties");
            fabricStore = new FabricStore(tmpStoreFile);
            FabricUser txUser = fabricStore.getMember(userName,orgName);
            manager.setFabricStore(fabricStore);
            manager.setClient(txUser);
            manager.setChannel();
            return true;
        }catch (Exception e){
            return false;
        }
    }




}
