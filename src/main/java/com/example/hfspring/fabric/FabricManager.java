package com.example.hfspring.fabric;

import com.example.hfspring.Utils.ConstantUtils;
import com.example.hfspring.demo.FabricStore;
import com.example.hfspring.demo.FabricUser;
import com.example.hfspring.demo.HFConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.NetworkConfigurationException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FabricManager {
    private static Log logger = LogFactory.getLog(FabricManager.class);

    private static FabricManager insatance = new FabricManager();

    private HFClient client;

    private FabricStore fabricStore;

    public FabricStore getFabricStore() {
        return fabricStore;
    }

    public void setFabricStore(FabricStore fabricStore) {
        this.fabricStore = fabricStore;
    }

    public void setFabricStore(String username)throws IOException {
        File file = new File("src/main/resources/_" + username  +".properties");
        if(!file.exists()){
            file.createNewFile();
        }
        this.fabricStore = new FabricStore(file);
    }


    private HFConfig config;

    private Channel channel;

    private ChaincodeID chaincodeID;
    private int proposalWaitTime = 200000;

    private HFCAClient hfcaClient;

    public HFCAClient getHfcaClient() {
        return hfcaClient;
    }

    public void setHfcaClient(HFCAClient hfcaClient) {
        this.hfcaClient = hfcaClient;
    }
//    public FabricStore getFabricStore() {
//        return fabricStore;
//    }
//
//    public void setFabricStore(FabricStore fabricStore) {
//        this.fabricStore = fabricStore;
//    }


    private FabricManager(){
        config = HFConfig.getConfig();
    }

    public HFClient getClient() {
        return client;
    }

    public void setClient(FabricUser user) throws Exception{
        client = HFClient.createNewInstance();
        client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
        client.setUserContext(user);
    }


    //constructor
    public FabricManager(FabricUser user)
            throws Exception {
        config = HFConfig.getConfig();
        client = HFClient.createNewInstance();
        hfcaClient = HFCAClient.createNewInstance(config.caInfo);
        client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
        client.setUserContext(user);
        this.channel = client.loadChannelFromConfig(ConstantUtils.channelName, config.getNetworkConfig());
//        channel.registerBlockListener(blockEvent -> {
//            logger.info(String.format("Receive block event (number %s) from %s",
//                    blockEvent.getBlockNumber(), blockEvent.getPeer()));
//        });



    }

    public static FabricManager getInsatance(){
        return insatance;
    }


    public void setChaincodeID() {
        this.chaincodeID = ChaincodeID.newBuilder()
                .setName(ConstantUtils.chaincodeName)
                .setPath(ConstantUtils.chaincodePath)
                .setVersion(ConstantUtils.chaincodeVersion)
                .build();
    }



    public Channel getChannel() {
        return channel;
    }

    public void setChannel() throws InvalidArgumentException, NetworkConfigurationException, TransactionException {
        this.channel = client.loadChannelFromConfig(ConstantUtils.channelName, config.getNetworkConfig());
        if(!channel.isInitialized()){
            channel.initialize();
        }
        this.chaincodeID = ChaincodeID.newBuilder()
                .setName(ConstantUtils.chaincodeName)
                .setPath(ConstantUtils.chaincodePath)
                .setVersion(ConstantUtils.chaincodeVersion)
                .build();
    }

    public void removeALL(){
        if(client != null){
            client = null;

        }
        if(channel != null){
            channel.shutdown(false);
        }


    }

    public String query (String fcn, String ...args) throws
            InvalidArgumentException,ProposalException{
        QueryByChaincodeRequest queryByChaincodeRequest = client.newQueryProposalRequest();
        queryByChaincodeRequest.setFcn(fcn);
        queryByChaincodeRequest.setArgs(args);
        queryByChaincodeRequest.setChaincodeID(chaincodeID);
        queryByChaincodeRequest.setProposalWaitTime(proposalWaitTime);
        queryByChaincodeRequest.setChaincodeLanguage(QueryByChaincodeRequest.Type.GO_LANG);
        Map<String, byte[]> tm2 = new HashMap<>();
        tm2.put("HyperLedgerFabric", "QueryByChaincodeRequest:JavaSDK".getBytes(UTF_8));
        tm2.put("method", "QueryByChaincodeRequest".getBytes(UTF_8));
        queryByChaincodeRequest.setTransientMap(tm2);
        String payload = "";
        Collection<ProposalResponse> queryProposals = channel.queryByChaincode(queryByChaincodeRequest, channel.getPeers());
        for (ProposalResponse proposalResponse : queryProposals) {
            if (!proposalResponse.isVerified() || proposalResponse.getStatus() != ProposalResponse.Status.SUCCESS) {
                logger.info("Failed query proposal from peer " + proposalResponse.getPeer().getName() + " status: " + proposalResponse.getStatus() +
                        ". Messages: " + proposalResponse.getMessage()
                        + ". Was verified : " + proposalResponse.isVerified());
            } else {
                payload = proposalResponse.getProposalResponse().getResponse().getPayload().toStringUtf8();
                logger.info( payload);

                //"Query payload of b from peer %s returned %s", proposalResponse.getPeer().getName(),
            }
        }
        if(!payload.isEmpty()){
            return payload;
        }else{
            return "error";
        }
    }

    public String invoke(String fcn, String ...args)
            throws InvalidArgumentException, ProposalException {
        TransactionProposalRequest transactionProposalRequest = client.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeID(chaincodeID);
        transactionProposalRequest.setFcn(fcn);
        transactionProposalRequest.setArgs(args);
        transactionProposalRequest.setProposalWaitTime(proposalWaitTime);
        transactionProposalRequest.setChaincodeLanguage(TransactionProposalRequest.Type.GO_LANG);
        Map<String, byte[]> tm2 = new HashMap<>();
        tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
        tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));
        tm2.put("result", ":)".getBytes(UTF_8));
        transactionProposalRequest.setTransientMap(tm2);

        Collection<ProposalResponse> transactionPropResp =
                channel.sendTransactionProposal(transactionProposalRequest, channel.getPeers());
        Collection<ProposalResponse> successful = new LinkedList<>();
        Collection<ProposalResponse> failed = new LinkedList<>();
        String payload = "";
        for (ProposalResponse response : transactionPropResp) {

            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
                payload = new String(response.getChaincodeActionResponsePayload());
                logger.info(String.format("[√] Got success response from peer %s => payload: %s", response.getPeer().getName(), payload));
                successful.add(response);
                logger.info(String.format("payload is : " + payload));
            } else {
                String status = response.getStatus().toString();
                String msg = response.getMessage();
                logger.warn(String.format("[×] Got failed response from peer %s => %s: %s ", response.getPeer().getName(), status, msg));
                failed.add(response);
            }
        }
        if(!payload.isEmpty()){
            return payload;
        }else{
            return "sth is wrong.. plz try again";
        }

    }

    public synchronized  FabricUser getOrgAdmin(){
        FabricUser admin;
        File tmpStoreFile = new File("src/main/resources/_" + "admin"  +".properties");
//        if(tmpStoreFile.exists()){
//            tmpStoreFile.delete();
//            logger.info("file exists");
//        }
        FabricStore fs = new FabricStore(tmpStoreFile);
        admin = fs.getMember("admin",config.clientOrg.getName());
        return admin;
    }

    public boolean registerOnHF(String username,String affiliation,String password)
    {
        try {
            RegistrationRequest rr = new RegistrationRequest(username,affiliation);
            rr.setType(HFCAClient.HFCA_TYPE_USER);
            rr.setSecret(password);
            hfcaClient.register(rr,getOrgAdmin());
            return true;
        }catch (Exception e){

            return false;
        }
    }

    public boolean enrollOnHF(FabricUser user,String username,String password){
        try{
            user.setEnrollment(hfcaClient.enroll(username,password));
            user.setMspId(config.clientOrg.getMspId());
            return true;
        }catch (Exception e ){
            return false;
        }

    }




}
