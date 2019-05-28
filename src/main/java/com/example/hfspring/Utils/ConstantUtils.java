package com.example.hfspring.Utils;

import org.hyperledger.fabric.sdk.TransactionRequest;

public final class ConstantUtils {
    public final static String networkConfigYAMLPath = "src/main/resources/network-config.yaml";
    public final static String endorsementPolicyYAMLPath = "chaincodeendorsementpolicy.yaml";

    public final static String chaincodePath = "chaincode/relicschaincode/src/relicDemo/goodchaincode.go";

    public final static String channelName = "mychannel";

    public final static String chaincodeName = "fabcar";

    public final static String chaincodeVersion = "1.0";

    public final static String resourcesPath = "src/main/resources/";

    public static final String relicChaincodeName = "relics";
    public static final String relicChaincodeVersion = "1.0";
    public static final String chaincodeDIR = "src/main/resources/chaincode/relicschaincode";
    public static final String chaincodeFilePath = "relicDemo";
    public static final String metaINFPath = "src/main/resources/MetaINFs";
    public static final TransactionRequest.Type CHAIN_CODE_LANG = TransactionRequest.Type.GO_LANG;


    public static final int StringRet = 100;
    public static final int JSONObjectRet = 101;
    public static final int JSONArrayRet = 102;


    public static final String REQUEST_OK = "200";

    public static final String REQUEST_ERROR = "400";

    public static final String CC_INIT = "initMarble";
    public static final String CC_DELETE = "delete";
    public static final String CC_QUERY_HISTORY = "getHistoryForMarble";
    public static final String CC_TRANSFER = "transferMarble";
    public static final String CC_QUERYBYID = "readMarble";
    public static final String CC_QUERYBYOWNER = "queryMarblesByOwner";

//    public final static String AVATAR_PATH = "/home/jarvist/hfspringRes/avatar";
//    public final static String PIC_PATH = "/home/jarvist/hfspringRes/relicsPhoto";

    public final static String AVATAR_PATH = "/root/hfspringRes/avatar";
    public final static String PIC_PATH = "/root/hfspringRes/relicsPhoto";

}
