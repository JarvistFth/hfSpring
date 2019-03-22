package com.example.hfspring.service;



public interface TransactionService {

    //init relics
    //invoke transaction
    //query one relics
    //query History
    //query relics ByOwner
    //delete relics
    //query all relics
    public void initRelics(String relicId, String relicName, String ownerName);

    public void queryRelics(String relicId);

    public void queryAllRelics(String ownerName);

    public void queryHistory(String id);

    public void deleteRelics(String id);

    public void invokeTransaction(String relicId, String newOwnerName);

    public boolean initialize(String userName, String orgName);

    public void removeContext();
}
