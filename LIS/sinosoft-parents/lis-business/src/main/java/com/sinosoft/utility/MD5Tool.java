package com.sinosoft.utility;
import org.apache.log4j.Logger;

/**
 *@author liurx
 *@create date 2006-11-28 15:30
 *@TODO:为一个文件或字符串生成信息摘要
 */
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Tool {
private static Logger logger = Logger.getLogger(MD5Tool.class);

	// 用来产生信息摘要的算法名
	private String alg;
	// 信息摘要字符串
	private String md;

	public MD5Tool() {
		// 如果不在构造子中指定算法名，默认将使用MD5算法
		this.alg = "MD5";
	}

	public MD5Tool(String alg) {
		this.alg = alg;
	}

	/**
	 * 对一个文件用指定算法生成字符串格式的信息摘要
	 * 
	 * @param fName
	 * @return
	 */
	public String getFileMD(String fName) {
		try {
			byte[] inByte = getFileBytes(fName);
			byte[] outByte = getMD(inByte);
			md = byteToStr(outByte);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return md;
	}

	/**
	 * 对一个字符串用指定算法生成字符串格式的信息摘要
	 * 
	 * @param str
	 * @return String
	 */
	public String getStrMD(String str) {
		if (str == null)
			return null;
		byte[] inByte = str.getBytes();
		byte[] outByte = getMD(inByte);
		md = byteToStr(outByte);
		return md;
	}

	/**
	 * 对一个字节流生成字节流格式的信息摘要
	 * 
	 * @param inByte
	 * @return byte[]
	 */
	public byte[] getMD(byte[] inByte) {
		if (inByte == null) {
			return null;
		}
		byte[] hash = null;
		MessageDigest tMessageDigest;
		try {
			tMessageDigest = MessageDigest.getInstance(alg);
			tMessageDigest.update(inByte);
			hash = tMessageDigest.digest();
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return hash;
	}

	/**
	 * 由文件名得到其二进制的输出
	 * 
	 * @param fName
	 * @return
	 * @throws IOException
	 */
	public byte[] getFileBytes(String fName) throws IOException {
		FileInputStream in = null;
		byte[] outByte = null;
		try {
			in = new FileInputStream(fName);
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int ch;
			while ((ch = in.read()) != -1)
				buffer.write(ch);
			outByte = buffer.toByteArray();
			return outByte;
		} catch (FileNotFoundException ex) {
			logger.debug("the specified file " + fName
					+ " does not exist.");
			return null;
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	/**
	 * 二进制转字符串函数
	 * 
	 * @param b
	 * @return
	 */
	public String byteToStr(byte[] b) {
		if (b == null)
			return null;
		String hs = "";
		for (int n = 0; n < b.length; n++) {
			int v = b[n] & 0xFF;
			if (v < 16)
				hs += "0";
			hs += Integer.toString(v, 16).toUpperCase();
		}
		return hs;
	}

	public String getAlg() {
		return this.alg;
	}
}
