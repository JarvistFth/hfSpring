package com.example.hfspring.fabric;

import com.example.hfspring.demo.FabricStore;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

public class FabricOrg {

    private Log logger = LogFactory.getLog(FabricOrg.class);

    private String username;

    private String orgMSP;

    private HFCAClient hfcaClient;

    private FabricStore fabricStore;

    private BlockListener blockListener;

    private HFClient client;

    private ChaincodeEventListener chaincodeEventListener;

    private Channel channel;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrgMSP() {
        return orgMSP;
    }

    public void setOrgMSP(String orgMSP) {
        this.orgMSP = orgMSP;
    }

    public FabricStore getFabricStore() {
        return fabricStore;
    }

    public void setFabricStore(FabricStore fabricStore) {
        this.fabricStore = fabricStore;
    }

    public HFClient getClient() {
        return client;
    }

    public void setClient(HFClient client) {
        this.client = client;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
