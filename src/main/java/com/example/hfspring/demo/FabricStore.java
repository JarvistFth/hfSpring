package com.example.hfspring.demo;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.bouncycastle.util.encoders.Hex;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FabricStore {
    private String file;
    private Log logger = LogFactory.getLog(FabricStore.class);
    private CryptoSuite cryptoSuite;

    public FabricStore(File file) {
        this.file = file.getAbsolutePath();
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(file)) {
            properties.load(input);
            input.close();
        } catch (FileNotFoundException e) {
            logger.info(String.format("Could not find the file \"%s\"", file));
        } catch (IOException e) {
            logger.warn(String.format("Could not load keyvalue store from file \"%s\", reason:%s",
                    file, e.getMessage()));
        }

        return properties;
    }

    public void setValue(String name, String value){
        Properties properties = loadProperties();
        try(OutputStream outputStream = new FileOutputStream(file)){
            properties.setProperty(name,value);
            properties.store(outputStream,"");
            outputStream.close();
        }catch (Exception e){
            logger.warn(String.format("Could not save the keyvalue store, reason:%s", e.getMessage()));
        }
    }

    public String getValue(String name){
        Properties properties = loadProperties();
        return properties.getProperty(name);
    }

    public boolean hasValue(String name){
        Properties properties = loadProperties();
        return properties.containsKey(name);
    }

    private final Map<String, FabricUser> members = new HashMap<>();

    /**
     * Get the user with a given name
     *
     * @param name
     * @param org
     * @return user
     */
    public FabricUser getMember(String name, String org) {

        // Try to get the SampleUser state from the cache
        FabricUser fabricUser = members.get(FabricUser.toKeyValStoreName(name, org));
        if (null != fabricUser) {
            return fabricUser;
        }

        // Create the SampleUser and try to restore it's state from the key value store (if found).
        fabricUser = new FabricUser(name, org, this,cryptoSuite);
        fabricUser.saveState();
        return fabricUser;

    }

    public boolean hasMember(String name, String org){
        if(members.containsKey(FabricUser.toKeyValStoreName(name,org))){
            return true;
        }
        return FabricUser.isStored(name,org,this);
    }

    public FabricUser getMember(String name,String org, String mspId, File privateKeyFile, File certificateFile)
        throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        try {
            FabricUser fabricUser = members.get(FabricUser.toKeyValStoreName(name, org));
            if (fabricUser != null) {
                return fabricUser;
            }

            fabricUser = new FabricUser(name, org, this, cryptoSuite);
            fabricUser.setMspId(mspId);
            String certificate = new String(IOUtils.toByteArray(new FileInputStream(certificateFile)), "UTF-8");
            PrivateKey privateKey = getPrivateKeyFromBytes(IOUtils.toByteArray(new FileInputStream(privateKeyFile)));
            fabricUser.setEnrollment(new FabricStoreEnrollment(privateKey, certificate));
            fabricUser.saveState();
            return fabricUser;

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            throw e;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw e;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw e;
        }

    }

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    static PrivateKey getPrivateKeyFromBytes(byte[] data) throws IOException, NoSuchAlgorithmException,NoSuchProviderException,InvalidKeySpecException{
        final Reader pemReader = new StringReader(new String (new String(data)));
        final PrivateKeyInfo pemPair;
        try (PEMParser pemParser = new PEMParser(pemReader)) {
            pemPair = (PrivateKeyInfo)pemParser.readObject();
        }
        PrivateKey privateKey = new JcaPEMKeyConverter().setProvider(BouncyCastlePQCProvider.PROVIDER_NAME).getPrivateKey(pemPair);
        return privateKey;
    }

    static final class FabricStoreEnrollment implements Enrollment,Serializable{

        private final PrivateKey privateKey;
        private final String certificate;

        public FabricStoreEnrollment(PrivateKey privateKey, String certificate) {
            this.privateKey = privateKey;
            this.certificate = certificate;
        }

        @Override
        public PrivateKey getKey() {
            return privateKey;
        }

        @Override
        public String getCert() {
            return certificate;
        }
    }

    void saveChannel(Channel channel) throws IOException,InvalidArgumentException{
        setValue("channel." + channel.getName(), Hex.toHexString(channel.serializeChannel()));
    }

    Channel getChannel(HFClient client, String name) throws IOException,ClassNotFoundException,InvalidArgumentException{
        Channel ret = null;
        String channelHex = getValue("channel." + name);
        if(channelHex != null){
            ret = client.deSerializeChannel(Hex.decode(channelHex));

        }
        return ret;
    }


}
