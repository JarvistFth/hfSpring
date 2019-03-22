package com.example.hfspring.fabric;

import com.alibaba.fastjson.JSONObject;
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

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FabricManager {
    private static Log logger = LogFactory.getLog(FabricManager.class);

    private static FabricManager insatance = new FabricManager();

    private HFClient client;

//    private FabricStore fabricStore;


    private HFConfig config;

    private Channel channel;

    private ChaincodeID chaincodeID;
    private int proposalWaitTime = 200000;

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
        client = null;
        channel.shutdown(false);

    }


    public void invoke(String fcn, String ...args)
            throws InvalidArgumentException, ProposalException
    {
        TransactionProposalRequest transactionProposalRequest = client.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeID(chaincodeID);
        transactionProposalRequest.setFcn(fcn);
        transactionProposalRequest.setArgs(args);
        transactionProposalRequest.setProposalWaitTime(proposalWaitTime);
        transactionProposalRequest.setChaincodeLanguage(TransactionProposalRequest.Type.GO_LANG);
        Map<String,byte[]> tm2 = new HashMap<>();
        tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
        tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));
        tm2.put("result", ":)".getBytes(UTF_8));
        transactionProposalRequest.setTransientMap(tm2);

        Collection<ProposalResponse> transactionPropResp =
                channel.sendTransactionProposal(transactionProposalRequest,channel.getPeers());

    }

    public Object getPayloadJSON(){

        String payload = "";
        Collection<ProposalResponse> successful = new LinkedList<>();
        Collection<ProposalResponse> failed = new LinkedList<>();
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
            return JSONObject.parse(payload);
    }







}
