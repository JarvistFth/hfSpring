package com.example.hfspring.fabric;

import com.alibaba.fastjson.JSONObject;
import com.example.hfspring.Utils.ConstantUtils;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FabricChaincode {
    private ChaincodeID chaincodeID;
    private int proposalWaitTime = 200000;


    public void setChaincodeID() {
        this.chaincodeID = ChaincodeID.newBuilder()
                .setName(ConstantUtils.chaincodeName)
                .setPath(ConstantUtils.chaincodePath)
                .setVersion(ConstantUtils.chaincodeVersion)
                .build();
    }

    public ChaincodeID getChaincodeID() {
        return chaincodeID;
    }

//    JSONObject invoke(HFClient client, String fcn, String ...args)
//            throws InvalidArgumentException
//    {
//        TransactionProposalRequest transactionProposalRequest = client.newTransactionProposalRequest();
//        transactionProposalRequest.setChaincodeID(chaincodeID);
//        transactionProposalRequest.setFcn(fcn);
//        transactionProposalRequest.setArgs(args);
//        transactionProposalRequest.setProposalWaitTime(proposalWaitTime);
//        transactionProposalRequest.setChaincodeLanguage(TransactionProposalRequest.Type.GO_LANG);
//        Map<String,byte[]> tm2 = new HashMap<>();
//        tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
//        tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));
//        tm2.put("result", ":)".getBytes(UTF_8));
//        transactionProposalRequest.setTransientMap(tm2);
//
//        Collection<ProposalResponse> transactionPropResp =

//    }
}
