package com.example.hfspring.Model;

public class TXResponse {

    private String TxId  ;

    private TXValue Value;

    private String Timestamp ;

    private String IsDelete;

    public String getTxId() {
        return TxId;
    }

    public TXValue getValue() {
        return Value;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTxId(String txId) {
        TxId = txId;
    }

    public void setValue(TXValue Value) {
        this.Value = Value;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }
}
