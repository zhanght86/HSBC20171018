package com.tencent.qcloud.algr;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;


public class SignatureComputer {
    private String appid;
    private String secretId;
    private String secretKey;
    private java.util.Random rand;
    private static final String ALGORITHM = "HmacSHA1";
    private void initialize(String appid,
    		String secretId, String secretKey){
    	this.appid = appid;
    	this.secretId = secretId;
    	this.secretKey = secretKey;
    	this.rand = new java.util.Random(System.currentTimeMillis() + new java.util.Random(System.currentTimeMillis()).nextInt());
    }
    
    
    private byte[] hash_hmac(String srcStr, String secretKey) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException{
			Mac mac = Mac.getInstance(ALGORITHM);
			SecretKeySpec secret = new SecretKeySpec(secretKey.getBytes("UTF-8"), ALGORITHM);
			mac.init(secret);
			byte[] digest = mac.doFinal(srcStr.getBytes("UTF-8"));
			byte[] content = new byte[digest.length + srcStr.getBytes("UTF-8").length];
			System.arraycopy(digest, 0, content, 0, digest.length);
			System.arraycopy(srcStr.getBytes("UTF-8"), 0, content, digest.length, srcStr.getBytes("UTF-8").length);
			return content;
    }
    
    private String base64Encode(byte[] hash){
    	String result = new String(DatatypeConverter.printBase64Binary(hash));
    	return result;
    }
    private String getSrcStr(long current, long expired, String bucket){
    	return "a=" + this.appid + "&b=" + bucket +  "&k=" + this.secretId + "&e=" + expired / 1000 + "&t=" +  current / 1000 + "&r=" + (rand.nextInt(Integer.MAX_VALUE - 1) + 1) + "&f=";
    }
    
    private String getSrcStrOnce(long current, String fileId, String bucket){
    	return "a=" + this.appid + "&b=" + bucket + "&k=" + this.secretId + "&e=" + 0 + "&t=" + current / 1000 + "&r=" + (rand.nextInt(Integer.MAX_VALUE - 1) + 1) + "&f=" + fileId;
    }
    
    public SignatureComputer(String appid,
    		String secretId, String secretKey){
    	this.initialize(appid, secretId, secretKey);
    }
	public String toHexString(byte[] digest){
    	java.util.Formatter formatter = new java.util.Formatter();
    	for(int i = 0; i < digest.length; i++){
    		formatter.format("%02x", digest[i]);
    	}
    	String result = new String( formatter.toString());
    	formatter.close();
    	return result;
    }
    
    public String getSignStr(int expiresAfterSeconds, String bucket) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException{
    	long current = System.currentTimeMillis();
    	if(expiresAfterSeconds <= 0){
    		expiresAfterSeconds = 60;
    	}
    	long expired = current + ((long)expiresAfterSeconds * (long)1000);
    	String srcStr = this.getSrcStr(current, expired, bucket);
    	return base64Encode(hash_hmac(srcStr,this.secretKey));    	
    }
    
    public String getSignStrOnce(String filePath, String bucket) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException{
    	long current = System.currentTimeMillis();
    	while(filePath.length() > 0 && filePath.charAt(0) == '.'){
    		filePath = filePath.substring(1);
    	}
    	while(filePath.length() > 0 && filePath.charAt(0) == '/'){
    		filePath = filePath.substring(1);
    	}
    	String[] parts = filePath.split("/");
    	for(int i = 0; i < parts.length; i++){
    		parts[i] =  URLEncoder.encode(parts[i], "UTF-8").replaceAll("\\+", "%20");
    	}
    	filePath = parts[0];
    	for(int i = 1; i < parts.length; i++){
    		filePath += "/" + parts[i];
    	}
    	String a = URLEncoder.encode(this.appid, "UTF-8").replaceAll("\\+", "%20");
    	String b = URLEncoder.encode(bucket, "UTF-8").replaceAll("\\+", "%20");
    	String fileId = "/" + a + "/" + b + "/" + filePath;
    	String srcStrOnce = this.getSrcStrOnce(current, fileId, bucket);
    	return base64Encode(hash_hmac(srcStrOnce,this.secretKey));
    }
}
