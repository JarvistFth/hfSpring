package com.example.hfspring.service;



public interface TransactionService {

    //init relics
    //invoke transaction
    //query one relics
    //query History
    //query relics ByOwner
    //delete relics
    //query all relics
    public String initRelics(String relicId, String relicName, String ownerName);

    public String queryRelics(String relicId);

    public String queryAllRelics(String ownerName);

    public String queryHistory(String id);

    public String deleteRelics(String id);

    public String invokeTransaction(String relicId, String newOwnerName);

    public boolean initialize(String userName, String orgName);

}
