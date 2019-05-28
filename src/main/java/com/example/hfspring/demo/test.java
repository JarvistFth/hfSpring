package com.example.hfspring.demo;


import com.example.hfspring.Utils.ConstantUtils;
import com.example.hfspring.fabric.ChaincodeExecuter;
import com.example.hfspring.fabric.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.HFCAInfo;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InfoException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

public class test {
    private static Log logger = LogFactory.getLog(test.class);
    private static String userName = "admin";
    private static String secret = "adminpw";
    private static String chaincodeName = "fabcar";
    private static String chaincodeVersion = "1.0";
    private static String channelName = "mychannel";
    public static Channel channel ;
    private static HFCAClient hfcaClient ;

    private static final String testUser = "Jarvist";
    private static final String testSecret = "123456";


    public static void main(String[] args){
        try {
            NetworkConfig networkConfig = NetworkConfig.fromYamlFile(new File(ConstantUtils.networkConfigYAMLPath));
            NetworkConfig.OrgInfo clientOrg = networkConfig.getClientOrganization();
            NetworkConfig.CAInfo caInfo = clientOrg.getCertificateAuthorities().get(0);
            hfcaClient = HFCAClient.createNewInstance(caInfo);
            File f = new File("src/main/resources/" + userName  +".properties");
            if(!f.exists()){
                f.createNewFile();
            }
            FabricStore fabricStore = new FabricStore(f);
            FabricUser admin = fabricStore.getMember(userName,clientOrg.getName());

            if(!admin.isEnrolled()){
                admin.setEnrollment(hfcaClient.enroll(userName,secret));
                admin.setMspId(clientOrg.getMspId());
            }

            HFClient client = HFClient.createNewInstance();
            client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
            client.setUserContext(admin);

            Channel channel = client.loadChannelFromConfig(channelName, networkConfig);

            //service discovery function.
            Iterator<Peer> iterator = channel.getPeers().iterator();
            while(iterator.hasNext()){
                logger.info(iterator.next().getName());
            }

            File f1 = new File("src/main/resources/" + testUser  +".properties");
            if(!f1.exists()){
                f1.createNewFile();
            }
            FabricStore fabricStore1 = new FabricStore(f1);
            FabricUser user1 = new FabricUser(testUser,clientOrg.getName(),fabricStore1,CryptoSuite.Factory.getCryptoSuite());
            if(!user1.isRegistered()){
                logger.info("registering !!!!!!!!!!!");
                RegistrationRequest rr = new RegistrationRequest(testUser,"org1.department1");
                rr.setSecret(testSecret);
                String ss = hfcaClient.register(rr,admin);
                logger.info("ss:" + ss);
                user1.setEnrollmentSecret(ss);
            }

            if(!user1.isEnrolled()){
                logger.info("enrolling !!!!!!!!!!!!");
                Enrollment enrollment =  hfcaClient.enroll(testUser,testSecret);
                user1.setEnrollment(enrollment);
                user1.setMspId(clientOrg.getMspId());
            }
            client.setUserContext(admin);


            logger.info("===========client context is : " + client.getUserContext().getName());


            //channel.removePeer(p);
            //channel.addPeer(p, Channel.PeerOptions.createPeerOptions().addPeerRole(Peer.PeerRole.SERVICE_DISCOVERY));
            //Collection<String> cc = channel.getDiscoveredChaincodeNames();

            channel.initialize();
//
            channel.registerBlockListener(blockEvent -> {
                logger.info(String.format("Receive block event (number %s) from %s", blockEvent.getBlockNumber(), blockEvent.getPeer()));
            });
            executeChaincode(client, channel);
            logger.info("Shutdown channel.");
            channel.shutdown(true);

        } catch (Exception e) {
            logger.error("exception", e);
        }

    }




    private static ChaincodeID buildChaincode(){
        ChaincodeID.Builder chaincodeIDBuilder = ChaincodeID.newBuilder().setName(Config.relicChaincodeName)
                .setVersion(Config.relicChaincodeVersion);
        if (null != Config.chaincodeFilePath) {
            chaincodeIDBuilder.setPath(Config.chaincodeFilePath);

        }
        return chaincodeIDBuilder.build();
    }
    private static FabricUser getFabricUser(NetworkConfig.OrgInfo clientOrg, NetworkConfig.CAInfo caInfo,FabricStore fabricStore)throws
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
        user.setName(testUser);
        user.setOrganization(clientOrg.getName());
        user.setEnrollment(enrollment);
        user.setKeyValStore(fabricStore);
//        user.saveState();
        return user;
    }

    private static void executeChaincode(HFClient client , Channel channel) throws
            ProposalException, InvalidArgumentException, UnsupportedEncodingException,InterruptedException, ExecutionException, TimeoutException
    {
        ChaincodeExecuter executer = new ChaincodeExecuter(chaincodeName,chaincodeVersion);
        executer.executeTransaction(client,channel,true,"getHistoryForMarble","2015212154");
    }

}
