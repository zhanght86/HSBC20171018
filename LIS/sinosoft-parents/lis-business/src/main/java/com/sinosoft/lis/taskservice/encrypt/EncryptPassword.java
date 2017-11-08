/*
 * Created on 2005-12-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.sinosoft.lis.taskservice.encrypt;
import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */




/**taskservice加密类
 * @author sinosoft
 *
 */
public class EncryptPassword {
private static Logger logger = Logger.getLogger(EncryptPassword.class);
	//加密
	public String encrypt(String pass){
		String encrypt="";
		byte[] cleartext = pass.getBytes();
		EncryptTools ent = new EncryptTools();
		Base64 base64=new Base64();
		String sString=null;
		
		try {
			ent.init("taskserviceencrypt");
			byte[] ciphertext = ent.Encrypt(cleartext);
			encrypt=new String(base64.encodeForUrl(ciphertext));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return encrypt;
	}
	
	//解密
	public String decrypt(String encryptPass){
		String pass="";
		EncryptTools ent = new EncryptTools();
	    Base64 base64=new Base64();
    	try {
			ent.init("taskserviceencrypt");
		    byte[] passtext = encryptPass.getBytes();  
		    byte[] ciphertext=base64.decodeForUrl(encryptPass);//.decode(passtext);    
		    byte[] cleartext = ent.Decrypt(ciphertext);
			pass = new String(cleartext);
       }catch (Exception e) {
	    	//e.printStackTrace();
	   }
		return pass;
	}
    public static void main(String[] args) {
		EncryptPassword e= new EncryptPassword();
//    	//String a = "2006-2-15";
//    	//String b= e.encrypt(a);
//    	//logger.debug("b=="+b);
//    	String c= e.decrypt("XkWYdN/3ibxaslzlADkUWA==");
//    	logger.debug("c==="+c);

				//String a = "liudc$123456";
				//String b= e.encrypt(a);
				//logger.debug("b=="+b);
				String c= e.decrypt("FbvC8/uigL/+cwHi8lGq5Q==");
				//String c= e.decrypt("FbvC8/uigL/+cwHi8lGq5Q==");
				logger.debug("c==="+c);
      }
}

