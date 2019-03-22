package com.example.hfspring.fabric;

import org.hyperledger.fabric.sdk.TransactionRequest;

public class Config {
    public static final String relicChaincodeName = "relics";
    public static final String relicChaincodeVersion = "1.0";
    public static final String chaincodeDIR = "src/main/resources/chaincode/relicschaincode";
    public static final String chaincodeFilePath = "relicDemo";
    public static final String metaINFPath = "src/main/resources/MetaINFs";
    public static final TransactionRequest.Type CHAIN_CODE_LANG = TransactionRequest.Type.GO_LANG;
}
