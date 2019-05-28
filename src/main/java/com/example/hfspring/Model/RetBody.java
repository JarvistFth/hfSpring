package com.example.hfspring.Model;

import org.springframework.transaction.config.TxNamespaceHandler;

public class RetBody {
    String TxId;
    String TimeStamp;
    String id;
    String name;
    String ownerName;

    public String getTxId() {
        return TxId;
    }

    public void setTxId(String txId) {
        TxId = txId;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
