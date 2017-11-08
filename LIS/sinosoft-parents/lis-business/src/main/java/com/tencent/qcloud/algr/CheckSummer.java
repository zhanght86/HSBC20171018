package com.tencent.qcloud.algr;

import java.security.MessageDigest;

public class CheckSummer {
    public static String getChecksum(byte[] data)
    {
		MessageDigest sha1 = null;
		String fileHash = null;
		try{
		    sha1 = MessageDigest.getInstance("SHA1");
              
            if(sha1 == null){
            	return null;
            }
            int read = data.length; 
            sha1.update(data, 0, read);
            byte[] hashBytes = sha1.digest();
  
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < hashBytes.length; i++) {
              sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
         
            fileHash = sb.toString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
        return fileHash;
    }
}
