package com.example.hfspring.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.sdk.NetworkConfig;

import java.io.File;

public class HFConfig {
    private static Log logger = LogFactory.getLog(HFConfig.class);
    private static String connectionFilePath = "src/main/resources/network-config.yaml";

    public NetworkConfig getNetworkConfig() {
        return networkConfig;
    }

    public NetworkConfig.OrgInfo getClientOrg() {
        return clientOrg;
    }

    public NetworkConfig.CAInfo getCaInfo() {
        return caInfo;
    }

    public NetworkConfig networkConfig;
    public NetworkConfig.OrgInfo clientOrg;
    public NetworkConfig.CAInfo caInfo;


//    private static String userName = "jar";
//    private static String secret = "123456";
//    private static String channelName = "mychannel";
//    private static String chaincodeName = "goodchaincode";
//    private static String chaincodeVersion = "0.1";
//

    private static HFConfig config;

    public HFConfig(){
        File file = new File(connectionFilePath);
        try{
            networkConfig = NetworkConfig.fromYamlFile(file);
            clientOrg = networkConfig.getClientOrganization();
            caInfo = clientOrg.getCertificateAuthorities().get(0);
        }catch (Exception e){
            logger.error(e);
            e.printStackTrace();
        }
    }


    public static HFConfig getConfig(){
        if(config == null){
            config = new HFConfig();
        }
        return config;
    }

    public void destroy(){
        config = null;
    }







}
