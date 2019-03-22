package com.example.hfspring.demo;

import io.netty.util.internal.StringUtil;
import org.bouncycastle.util.encoders.Hex;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

import java.io.*;
import java.util.Set;

public class FabricUser implements User, Serializable {


    public FabricUser(String name, String organization, FabricStore fs, CryptoSuite cryptoSuite) {
        this.name = name;
        this.organization = organization;
        this.keyValStore = fs;
        this.keyValStoreName = toKeyValStoreName(this.name,organization);
        this.cryptoSuite = cryptoSuite;
        String memberStr = keyValStore.getValue(keyValStoreName);
        if (memberStr != null){
            restoreState();
        }else{
            saveState();
        }

    }


    public FabricUser(){

    }

    private String name;
    private String organization;
    private String mspId;
    private String account;
    private Set<String> roles;
    Enrollment enrollment;
    private String affiliation;

    public void setKeyValStore(FabricStore keyValStore) {
        this.keyValStore = keyValStore;
    }

    private transient FabricStore keyValStore;
    private String keyValStoreName;

    private transient CryptoSuite cryptoSuite;
    private String enrollmentSecret;


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        saveState();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
        saveState();
    }

    @Override
    public String getMspId() {
        return mspId;
    }

    public void setMspId(String mspId) {
        this.mspId = mspId;
        saveState();
    }

    @Override
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
        saveState();
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
        saveState();
    }

    @Override
    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
        saveState();
    }

    @Override
    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
        saveState();
    }

    public boolean isEnrolled(){
        return this.enrollment != null;
    }




    void saveState(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try{
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            keyValStore.setValue(keyValStoreName,Hex.toHexString(bos.toByteArray()));
            bos.close();
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    FabricUser restoreState(){
        String memberStr = keyValStore.getValue(keyValStoreName);
        if(memberStr != null){
            byte[] serialized = Hex.decode(memberStr);
            ByteArrayInputStream bis = new ByteArrayInputStream(serialized);
            try{
                ObjectInputStream ois = new ObjectInputStream(bis);
                FabricUser state = (FabricUser) ois.readObject();
                if(state != null){
                    this.name = state.name;
                    this.organization = state.organization;
                    this.mspId = state.mspId;
                    this.roles = state.roles;
                    this.account = state.account;
                    this.affiliation = state.affiliation;
                    this.enrollment = state.enrollment;
                    this.enrollmentSecret = state.enrollmentSecret;
                    return this;
                }
            }catch (Exception e){
                throw new RuntimeException(String.format("Could not restore state of member %s", this.name), e);
            }
        }
        return null;
    }


    public static String toKeyValStoreName(String name, String organization){
        return "user." + name + organization;
    }

    public String getEnrollmentSecret() {
        return enrollmentSecret;
    }

    public void setEnrollmentSecret(String enrollmentSecret) {
        this.enrollmentSecret = enrollmentSecret;
    }

    public boolean isRegistered(){
        return !StringUtil.isNullOrEmpty(enrollmentSecret);
    }

    static boolean isStored(String name, String org, FabricStore fs){
        return fs.hasValue(toKeyValStoreName(name,org));
    }
}
