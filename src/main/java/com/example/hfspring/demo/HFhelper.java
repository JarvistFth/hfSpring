package com.example.hfspring.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.HFCAInfo;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InfoException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class HFhelper{
    private static Log logger = LogFactory.getLog(HFhelper.class);
    private static String connectionFilePath = "src/main/resources/network-config.yaml";
    public static NetworkConfig networkConfig;
    public static NetworkConfig.OrgInfo clientOrg;
    public static NetworkConfig.CAInfo caInfo;

    private static String userName = "jar";
    private static String secret = "123456";
    private static String channelName = "mychannel";
    private static String chaincodeName = "goodchaincode";
    private static String chaincodeVersion = "0.1";



    public static void init(){
        File file = new File(connectionFilePath);
        try{
            networkConfig = NetworkConfig.fromYamlFile(file);
            clientOrg = networkConfig.getClientOrganization();
            caInfo = clientOrg.getCertificateAuthorities().get(0);
//            FabricUser user = getFabricUser(clientOrg,caInfo);
//            HFClient client = HFClient.createNewInstance();
//            client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
//            client.setUserContext(user);
//            Channel channel = client.loadChannelFromConfig(channelName,networkConfig);
//            channel.initialize();
//            channel.registerBlockListener(blockEvent -> {
//               logger.info(String.format("Receive block event (number %s) from %s", blockEvent.getBlockNumber(), blockEvent.getPeer()));
//            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static FabricUser getFabricUser(NetworkConfig.OrgInfo clientOrg,NetworkConfig.CAInfo caInfo)throws
            MalformedURLException, org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException, InfoException,
            EnrollmentException
    {
        HFCAClient hfcaClient = HFCAClient.createNewInstance(caInfo);
        HFCAInfo cainfo = hfcaClient.info();
        logger.info("CA name: " + cainfo.getCAName());
        logger.info("CA version: " + cainfo.getVersion());

        logger.info("Going to enroll user: " + userName);
        Enrollment enrollment = hfcaClient.enroll(userName, secret);
        logger.info("Enroll user: " + userName +  " successfully.");


        FabricUser user = new FabricUser();
        user.setMspId(clientOrg.getMspId());
        user.setName(userName);
        user.setOrganization(clientOrg.getName());
        user.setEnrollment(enrollment);
        return user;
    }

    private static void executeChaincode(HFClient client , Channel channel) throws
            ProposalException, InvalidArgumentException, UnsupportedEncodingException,InterruptedException, ExecutionException, TimeoutException
    {
        ChaincodeExecuter executer = new ChaincodeExecuter(chaincodeName,chaincodeVersion);
        executer.executeTransaction(client,channel,false,"getHistory","");
    }

}
