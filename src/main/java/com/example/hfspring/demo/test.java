package com.example.hfspring.demo;


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
    private static HFCAClient hfcaClient;

    private static final String testUser = "Jarvist";
    private static final String testSecret = "123456";


    public static void main(String[] args){
//        while(true){

            HFConfig configHelper = new HFConfig();
            try{
                hfcaClient = HFCAClient.createNewInstance(configHelper.caInfo);

//                RegistrationRequest rr = new RegistrationRequest(userName,"org1.department1");
//                rr.setSecret(secret);
//                rr.setType(HFCAClient.HF);
//                hfcaClient.register()



            }catch (Exception e){
                logger.error(e.getMessage());
            }
            File tmpStoreFile = new File("src/main/resources/_" + userName  +".properties");
//        if(tmpStoreFile.exists()){
//            tmpStoreFile.delete();
//            logger.info("file exists");
//        }

            FabricStore fs = new FabricStore(tmpStoreFile);
            FabricUser admin = fs.getMember("admin",configHelper.clientOrg.getName());
            if(!admin.isEnrolled()){
                try{
                    logger.info("admin not enrolled!!!!");
                    admin.setEnrollment(hfcaClient.enroll(userName,secret));
                    admin.setMspId(configHelper.clientOrg.getMspId());

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            HFClient client = HFClient.createNewInstance();
            InstallProposalRequest installProposalRequest;
            final ChaincodeID chaincodeID = test.buildChaincode();
            try {
//                Thread.sleep(1000);
                if(admin != null){
//                    client.newInstallProposalRequest();
                    client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
                    client.setUserContext(admin);
                    logger.info("admin != null !!!!!");
                }
//                installProposalRequest = client.newInstallProposalRequest();
//                installProposalRequest.setChaincodePath()

                channel = client.loadChannelFromConfig(channelName,configHelper.networkConfig);
                channel.initialize();
//                channel.registerBlockListener(blockEvent -> {
//                    logger.info(String.format("Receive block event (number %s) from %s",
//                            blockEvent.getBlockNumber(), blockEvent.getPeer()));});
//                channel.registerChaincodeEventListener(Pattern.compile(".*"),Pattern.compile(Pattern.quote("event")),)
//                installProposalRequest.setChaincodeID(chaincodeID);
//                installProposalRequest.setChaincodeSourceLocation(Paths.get(Config.chaincodeDIR).toFile());
//                installProposalRequest.setChaincodeMetaInfLocation(new File(Config.metaINFPath + "/relics"));
//                installProposalRequest.setChaincodeVersion(Config.relicChaincodeVersion);
//                installProposalRequest.setChaincodeLanguage(Config.CHAIN_CODE_LANG);
//                Collection<Peer> peers = channel.getPeers();
//                Collection<ProposalResponse> successful = new LinkedList<>();
//                Collection<ProposalResponse> failed = new LinkedList<>();
//                Collection<ProposalResponse> responses = client.sendInstallProposal(installProposalRequest,peers);
//                for (ProposalResponse response : responses) {
//                    if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
//                        logger.info("Successful install proposal response.");
//                        successful.add(response);
//                    } else {
//                        failed.add(response);
//                        logger.error("install failed");
//                    }
//                }
//
//
//                //Initiate
//                InstantiateProposalRequest instantiateProposalRequest = client.newInstantiationProposalRequest();
//                instantiateProposalRequest.setProposalWaitTime(1200000);
//                instantiateProposalRequest.setChaincodeID(chaincodeID);
//                instantiateProposalRequest.setChaincodeLanguage(Config.CHAIN_CODE_LANG);
//                instantiateProposalRequest.setFcn("init");
//                instantiateProposalRequest.setArgs("");
//                Map<String, byte[]> tm = new HashMap<>();
//                tm.put("HyperLedgerFabric", "InstantiateProposalRequest:JavaSDK".getBytes(UTF_8));
//                tm.put("method", "InstantiateProposalRequest".getBytes(UTF_8));
//                instantiateProposalRequest.setTransientMap(tm);

//                ChaincodeEndorsementPolicy chaincodeEndorsementPolicy = new ChaincodeEndorsementPolicy();
//                chaincodeEndorsementPolicy.fromYamlFile(new File("src/main/resources/chaincodeendorsementpolicy.yaml"));
//                instantiateProposalRequest.setChaincodeEndorsementPolicy(chaincodeEndorsementPolicy);
//                successful.clear();failed.clear();
//                responses = channel.sendInstantiationProposal(instantiateProposalRequest,channel.getPeers());
//                for (ProposalResponse response : responses) {
//                    if (response.isVerified() && response.getStatus() == ProposalResponse.Status.SUCCESS) {
//                        successful.add(response);
//                        logger.info("instantiate successfully..");
//                    } else {
//                        failed.add(response);
//                        logger.error("installed failed");
//                    }
//                }
                executeChaincode(client,channel);
                channel.shutdown(false);

//                channel.unregisterChaincodeEventListener()
            }catch (Exception e){
                logger.error(e.getMessage());
            }
//        }

//        try{
//                if(admin != null){
//                    client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
//                    client.setUserContext(admin);
//                    channel = client.loadChannelFromConfig(channelName,configHelper.networkConfig);
//                    channel.initialize();
//                    channel.registerBlockListener(blockEvent -> {
//               logger.info(String.format("Receive block event (number %s) from %s", blockEvent.getBlockNumber(), blockEvent.getPeer()));
//            });
//
//                    executeChaincode(client,channel);
//                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
        }
//        FabricStore fs = new FabricStore(tmpStoreFile);
//        HFClient client = HFClient.createNewInstance();



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
        user.setName(userName);
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
        executer.executeTransaction(client,channel,false,"initMarble","201903251852","chuande","jar");
    }

}
