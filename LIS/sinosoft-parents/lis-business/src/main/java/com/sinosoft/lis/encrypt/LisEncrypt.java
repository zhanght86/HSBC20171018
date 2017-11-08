/**
 * Copyright (c) 2010 Sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.encrypt;

import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.sinosoft.lis.encrypt.cipher.Hex;
import com.sinosoft.lis.encrypt.cipher.IDEA;
import com.sinosoft.lis.encrypt.des.DesEncrypt;
/**
 * 加密类，混合不可逆算法(MD5+IDEA)
 * 
 * @version 0.01
 * @author zhouyuan周渊 2011-06-02
 * 
 *         基于Cryptix 3.2.0 Copyright The Cryptix Foundation & Systemics Ltd
 */
public class LisEncrypt {
	private static Logger logger = Logger.getLogger(LisEncrypt.class);
	//16进制的key
	private final static String KEY = "9D4F75C103BD362AFB09E7AE6AB3050E";
	/**
	 * 密钥算法
	 * */
	public static final String KEY_ALGORITHM="IDEA";
	
	//private final static String UIKEY  = "lisnational";
	/**
	 * 将字符串密钥转换
	 * @param key 字符串密钥
	 * @return Key 密钥
	 * */
	public static Key toKey(byte[] key) throws Exception{
		//实例化DES密钥
		//生成密钥
		SecretKey secretKey=new SecretKeySpec(key,KEY_ALGORITHM);
		return secretKey;
	}
	/**
	 * 
	 * */
	public static String encrypt(String str)
	{
		//String tDecodeStr = new DesEncrypt().strDec(str, UIKEY, "", "");
		//修改 为用3DES 和 MD5 混合加密
		
		String p1 = encryptMD5(new DesEncrypt().strEnc(str));
		String p2 = encryptMD5(str);
		
		String result = p2.substring(8,16) + p1.substring(0,8) + p2.substring(0,8) + p2.substring(16);
		return result;
	}
	
	//32位MD5加密
	public static String encryptMD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			String result = buf.toString();
			return result;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";

		}

	}

	/**
	 * 对输入的字符串使用IDEA进行加密
	 * 
	 * @param 密码明文字符串
	 * @return 加密后的密码字符串
	 * @throws Exception
	 * */
	public static String encryptIDEA(String str) {
		if (str == null || str.equals(""))
			return "";
		str = formatString(str);
		try {
			byte encP[] = Hex.fromString(stringToHexString(str));
			byte decC[] = new byte[encP.length];
			Key k=toKey(Hex.fromString(KEY));
			IDEA idea = new IDEA(k);
			idea.encrypt(encP,decC);
			
			String a;
			a = Hex.toString(decC);
			//logger.debug(a);
			return a;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 对输入的IDEA字符串进行解密
	 * 
	 * @param 密码明文字符串
	 * @return 加密后的密码字符串
	 * @throws Exception
	 * */
	public static String decryptIDEA(String encryptStr) {
		if (encryptStr == null || encryptStr.equals(""))
			return "";
		try {
			byte encP[] = Hex.fromString(encryptStr);
			byte decC[] = new byte[encP.length];
			Key k=toKey(Hex.fromString(KEY));
			IDEA idea = new IDEA(k);
			idea.decrypt(encP,decC);
			
			String a;
			a = hexStringToString(Hex.toString(decC));
			//logger.debug(a);
			return a;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	private static String stringToHexString(String srcString) {
		String resultString = "";
		int srcLen = srcString.length();
		for (int pos = 0; pos < srcLen; pos++) {
			byte b = (byte) srcString.charAt(pos);
			int hexValue = (b & 0x0F);
			resultString += hexToAscii(hexValue);
			hexValue = ((b >> 4) & 0x0F);
			resultString += hexToAscii(hexValue);
		}
		//logger.debug(resultString);
		return resultString;
	}
	private static String hexStringToString(String hexString) {
		String resultString = "";
		int hexLen = hexString.length();
		for (int pos = 0; pos < hexLen; pos += 2) {
			char c1 = hexString.charAt(pos);
			char c2 = hexString.charAt(pos + 1);
			int hexvalue1 = asciiToHex(c1);
			int hexvalue2 = asciiToHex(c2);
			char c = (char) (hexvalue1 | hexvalue2 << 4);
			resultString += c;
		}

		return resultString.trim();
	}
	private static char hexToAscii(int h) {
		if ((h >= 10) && (h <= 15)) {
			return (char) ('A' + (h - 10));
		}
		if ((h >= 0) && (h <= 9)) {
			return (char) ('0' + h);
		}
		throw new Error("hex to ascii failed");
	}
	private static int asciiToHex(char c) {
		if ((c >= 'a') && (c <= 'f')) {
			return (c - 'a' + 10);
		}
		if ((c >= 'A') && (c <= 'F')) {
			return (c - 'A' + 10);
		}
		if ((c >= '0') && (c <= '9')) {
			return (c - '0');
		}
		throw new Error("ascii to hex failed");
	}
	//如果字符串不是8的整数倍则在后面补空格
	private static String formatString(String s)
	{
		int lenB = s.length() % 8;
		if(lenB != 0)
		{
			for(int i=0;i<8-lenB; i++)
				s = s + " ";
		}
		//logger.debug(">>" + s + "<<");
		return s;
	}

	public static void main(String[] args) {
		
		//logger.debug(LisEncrypt.encrypt("001"));
		//MDAx
//		logger.debug((new sun.misc.BASE64Encoder()).encode("FEFBC8073798ECF1".getBytes()));
//		try {
//			logger.debug(new String((new sun.misc.BASE64Decoder()).decodeBuffer("MDAx")));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//System.out.print(LisEncrypt.encryptMD5("hello world"));
		System.out.print(LisEncrypt.encrypt("001"));
		//2e34275e4a72bd4a67654b17cde4f5b3
//		logger.debug(LisEncrypt.decryptIDEA("daef50c10930726Bdc5c7986e02ab09b442ee34f"));
//		logger.debug(LisEncrypt.encryptMD5("IDEATest"));
//		logger.debug(LisEncrypt.encrypt("001"));
	}
}
