/**
* @author     weikai,zhangzheng
* @version    1.0.0.0
* 将输入的输入加密,返回一个8位纯数字的密码
*/
package com.sinosoft.lis.list;
import org.apache.log4j.Logger;

import java.security.*;
import javax.crypto.*;
import java.io.*;

public class DESPlus 
{
private static Logger logger = Logger.getLogger(DESPlus.class);

	private static String strDefaultKey = "mslife2008";

	private Cipher encryptCipher = null;

	private Cipher decryptCipher = null;

/**
   * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
   * hexStr2ByteArr(String strIn) 互为可逆的转换过程
   * 
   * @param arrB
   *             需要转换的byte数组
   * @return 转换后的字符串
   * @throws Exception
   *              本方法不处理任何异常，所有异常全部抛出
   */
public static String byteArr2HexStr(byte[] arrB) throws Exception {
   int iLen = arrB.length;
   // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
   StringBuffer sb = new StringBuffer(iLen * 2);
   for (int i = 0; i < iLen; i++) {
    int intTmp = arrB[i];
    // 把负数转换为正数
    while (intTmp < 0) {
     intTmp = intTmp + 256;
    }
    // 小于0F的数需要在前面补0
    if (intTmp < 16) {
     sb.append("0");
    }
    sb.append(Integer.toString(intTmp, 16));
   }
   return sb.toString();
}

/**
   * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
   * 互为可逆的转换过程
   * 
   * @param strIn
   *             需要转换的字符串
   * @return 转换后的byte数组
   * @throws Exception
   *              本方法不处理任何异常，所有异常全部抛出
   * @author abc   */
public static byte[] hexStr2ByteArr(String strIn) throws Exception {
   byte[] arrB = strIn.getBytes();
   int iLen = arrB.length;

   // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
   byte[] arrOut = new byte[iLen / 2];
   for (int i = 0; i < iLen; i = i + 2) {
    String strTmp = new String(arrB, i, 2);
    arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
   }
   return arrOut;
}

/**
   * 默认构造方法，使用默认密钥
   * 
   * @throws Exception
   */
public DESPlus() throws Exception 
{
   //this(strDefaultKey);
}


private void DESPlusInit(String strKey) throws Exception 
{
   //明确地安装SunJCE 供应程序
//   Security.addProvider(new com.sun.crypto.provider.SunJCE()); // can't be used for ibm jdk  by zhangzm 090616
   
   //创建一个密钥
   Key key = getKey(strKey.getBytes());

   //此类为加密和解密提供密码功能。它构成了 Java Cryptographic Extension (JCE) 框架的核心。使用模式和填充方案特定于提供者的默认值
   encryptCipher = Cipher.getInstance("DES");
   //用于将 Cipher 初始化为加密模式的常量
   encryptCipher.init(Cipher.ENCRYPT_MODE, key);

   //使用模式和填充方案特定于提供者的默认值
   decryptCipher = Cipher.getInstance("DES");
   //用于将 Cipher 初始化为解密模式的常量
   decryptCipher.init(Cipher.DECRYPT_MODE, key);
   
}

///**
//   * 指定密钥构造方法
//   * 
//   * @param strKey
//   *             指定的密钥
//   * @throws Exception
//   */
//public DESPlus(String strKey) throws Exception 
//{
//   //明确地安装SunJCE 供应程序
//   Security.addProvider(new com.sun.crypto.provider.SunJCE());
//   
//   //创建一个密钥
//   Key key = getKey(strKey.getBytes());
//
//   //此类为加密和解密提供密码功能。它构成了 Java Cryptographic Extension (JCE) 框架的核心。使用模式和填充方案特定于提供者的默认值
//   encryptCipher = Cipher.getInstance("DES");
//   //用于将 Cipher 初始化为加密模式的常量
//   encryptCipher.init(Cipher.ENCRYPT_MODE, key);
//
//   //使用模式和填充方案特定于提供者的默认值
//   decryptCipher = Cipher.getInstance("DES");
//   //用于将 Cipher 初始化为解密模式的常量
//   decryptCipher.init(Cipher.DECRYPT_MODE, key);
//}

/**
   * 加密字节数组
   * 
   * @param arrB
   *             需加密的字节数组
   * @return 加密后的字节数组
   * @throws Exception
   */
public byte[] encrypt(byte[] arrB) throws Exception {
   return encryptCipher.doFinal(arrB);
}

/**
   * 加密字符串
   * 
   * @param strIn
   *             需加密的字符串
   * @return 加密后的字符串
   * @throws Exception
   */
public String encrypt(String strIn) throws Exception 
{
   return byteArr2HexStr(encrypt(strIn.getBytes()));
}

/**
   * 解密字节数组
   * 
   * @param arrB
   *             需解密的字节数组
   * @return 解密后的字节数组
   * @throws Exception
   */
public byte[] decrypt(byte[] arrB) throws Exception {
   return decryptCipher.doFinal(arrB);
}

/**
   * 解密字符串
   * 
   * @param strIn
   *             需解密的字符串
   * @return 解密后的字符串
   * @throws Exception
   */
public String decrypt(String strIn) throws Exception {
   return new String(decrypt(hexStr2ByteArr(strIn)));
}

/**
   * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
   * 
   * @param arrBTmp
   *             构成该字符串的字节数组
   * @return 生成的密钥
   * @throws java.lang.Exception
   */
private Key getKey(byte[] arrBTmp) throws Exception {
   // 创建一个空的8位字节数组（默认值为0）
   byte[] arrB = new byte[8];

   //将原始字节数组转换为8位
   for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
    arrB[i] = arrBTmp[i];
   }

   // 生成密钥
   Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

   return key;
}


/**
* 密钥生成规则:将输入的号翻转,作为该输入号的独有密钥
*
*/
private static String reverse(String s){
  String result =null;
  byte[] bt =s.getBytes();
  int len =bt.length;
  byte tmp;
  for(int i=0;i<len/2;i++)
  {
	  //logger.debug(bt[i]+" ");
	  tmp =bt[i];
	  bt[i] =bt[len-i-1];
	  bt[len-i-1] =tmp;
  }
  result =new String(bt);
  return result;
 }


/**
* 获得密钥
* input:需要加密的号码
*
*/

private String getKeyRule(String x)
{
	String privateKey="";//密钥
	//logger.debug("***need encrypt no="+x);
	privateKey=reverse(x);
	//logger.debug(privateKey+" ");
	
	//privateKey="7599910060700212345678";
	return privateKey;
}


/**
* 根据数据的号码加密,返回一个加密后的8位密码
*
*/
public String getEncryptKey(String inputNo)
{
    String encryppassword="";//待返回的加密完的16密码
    String encryppassword2="";//直接从加密完的16进制数转为10进制数的24位数字截取的8位密码
	DESPlus des;
	String resultString="";
	
	String beginInputNo=inputNo;
	//真正的用于生成密码的卡号是把传入卡号的后8位流水号替换前八位固定号后拼成的卡单号!
	//inputNo=beginInputNo.substring(beginInputNo.length()-8,20)+beginInputNo.substring(8);
	
	inputNo=(Integer.parseInt(inputNo.substring(0,8))+Integer.parseInt(inputNo.substring(inputNo.length()-7,20)))+beginInputNo.substring(8);
	//logger.debug("beginInputNo:"+beginInputNo);
	//logger.debug("inputNo:"+inputNo);
	
	
	if(inputNo==null||inputNo.equals(""))
	{
		throw new ArithmeticException("传入的需要加密的号码为空"); 
	}
	else
	{
		try 
		{
			
			
			//生成密钥
			String privatepassword=getKeyRule(inputNo);
			
			//resultString=resultString+" "+beginInputNo;
			//resultString=resultString+" "+inputNo;
			//logger.debug(" "+privatepassword+" ");
			
			//resultString=resultString+" "+privatepassword;
			
			//已加密后的密钥初始化密码生成类
			DESPlusInit(privatepassword);
			
			String password= encrypt(inputNo);
			//resultString=resultString+" "+password;
			//logger.debug(password+"   ");

	    	for (int i=0;i<=password.length()-1;i+=2)
			{ 
	    		encryppassword=encryppassword+Long.parseLong(String.valueOf(password.charAt(i)),16)%10; 
			}
	    	
	    	//logger.debug(encryppassword+"   ");
	    	//resultString=resultString+" "+encryppassword;
	    	 	
	    	//直接从加密完的16进制数转为10进制数的24位数字截取的8位密码
	    	for (int i=0;i<=encryppassword.length()-1;i+=3)
			{ 
	    		encryppassword2=encryppassword2+Long.parseLong(String.valueOf(encryppassword.charAt(i)),16)%10; 
			}
	    	
	    	//resultString=resultString+" "+encryppassword2;
	    	resultString=encryppassword2;
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	return resultString;
}


public static void main(String[] args)
{
//	   // TODO Auto-generated method stub
//	   try 
//	   {
//		    String test = "861100000";
//		    
//		    java.io.FileOutputStream tFileOutputStream = new FileOutputStream("e:\\test.txt");
//		    
//		    
//		    for (int j=99999;j>=10000;j--)
//		    {
//		    	String temp = test+j;
//		    	logger.debug(""+temp+"     ");
//		    	//logger.debug("最后一位:"+Long.parseLong(String.valueOf(temp.charAt(temp.length()-1))));
//		    	String tempkey ="";
////		    	for(int k=temp.length()-1;k>=0;k--)
////		    	{
////		    		char t=temp.charAt(k);
////		    		tempkey=tempkey+t;
////		    	}
//		    	tempkey=reverse(temp);
//		    	logger.debug(""+tempkey+"    ");
//		    	
//		    	DESPlus des = new DESPlus(reverse(tempkey));
//		    	String tempword= des.encrypt(temp);
//		    	
//		    	logger.debug(tempword+"   ");
//		    	String result = "";
//		    	
//		    	//最后一位是偶数
//		    	//logger.debug("最后一位:"+Long.parseLong(String.valueOf(temp.length()-1)));
////		    	if(Long.parseLong(String.valueOf(temp.charAt(temp.length()-1)))%2==0)
////		    	{
////		    		for (int i=tempword.length()-1;i>=0;i-=2)
////				    { 
////				    	result=result+Long.parseLong(String.valueOf(tempword.charAt(i)),16)%10; 
////				    }
////		    	}
////		    	else
////		    	{
////		    		for (int i=0;i<=tempword.length()-1;i+=2)
////				    { 
////				    	result=result+Long.parseLong(String.valueOf(tempword.charAt(i)),16)%10; 
////				    }
////		    	}
//		    	
//		    	String tempresult="";
//		    	for (int i=0;i<=tempword.length()-1;i+=2)
//				{ 
//		    		tempresult=tempresult+Long.parseLong(String.valueOf(tempword.charAt(i)),16)%10; 
//				}
//		    	logger.debug(tempresult+"   ");
//		    	
//		    	for (int i=0;i<=tempresult.length()-1;i+=2)
//				{ 
//		    		result=result+Long.parseLong(String.valueOf(tempresult.charAt(i)),16)%10; 
//				}
//			    
//			    logger.debug(result+"   ");
//			    
//			    String tt="";
//			    tt=tt+temp+"  "+tempkey+"  "+tempword+"  "+tempresult+"  "+result;
//			    tFileOutputStream.write(tt.getBytes());
//			    tFileOutputStream.write('\n');
//			    tFileOutputStream.flush();
//		    }
//		    
//		    tFileOutputStream.close();
//		    //DESPlus des = new DESPlus();//默认密钥
////		    DESPlus des = new DESPlus("1000000000001168");//自定义密钥
////		    logger.debug("加密前的字符："+test);
////		    logger.debug("加密后的字符："+des.encrypt(test));
////		    //logger.debug("加密后的字符2："+des.encrypt(test.getBytes().toString()));
////		    logger.debug("解密后的字符："+des.decrypt(des.encrypt(test)));
////		    
////		    String aa=des.encrypt(test);
////		    String result = "";
////		    for (int i=0;i<aa.length();i+=2)
////		    { 
////		    	result=result+Long.parseLong(String.valueOf(aa.charAt(i)),16)%10; 
////		    }
////		    logger.debug("result:"+result);
//	   } 
//	   catch (Exception e) 
//	   {
//	      // TODO: handle exception
//	      e.printStackTrace();
//	   }
	}
}
