package com.example.hfspring.Utils;

import org.hyperledger.fabric.sdk.TransactionRequest;

public final class ConstantUtils {
    public final static String networkConfigYAMLPath = "network-config.yaml";
    public final static String endorsementPolicyYAMLPath = "chaincodeendorsementpolicy.yaml";

    public final static String chaincodePath = "chaincode/relicschaincode/src/relicDemo/goodchaincode.go";

    public final static String channelName = "mychannel";

    public final static String chaincodeName = "fabcar";

    public final static String chaincodeVersion = "1.0";

    public static final String relicChaincodeName = "relics";
    public static final String relicChaincodeVersion = "1.0";
    public static final String chaincodeDIR = "src/main/resources/chaincode/relicschaincode";
    public static final String chaincodeFilePath = "relicDemo";
    public static final String metaINFPath = "src/main/resources/MetaINFs";
    public static final TransactionRequest.Type CHAIN_CODE_LANG = TransactionRequest.Type.GO_LANG;


    public static final int StringRet = 100;
    public static final int JSONObjectRet = 101;
    public static final int JSONArrayRet = 102;
}
