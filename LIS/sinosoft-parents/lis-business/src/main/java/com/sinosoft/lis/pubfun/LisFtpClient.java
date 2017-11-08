/**
 * Copyright (c) 2002 sinosoft Co. Ltd. All right reserved.
 */
package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.schema.LDSysVarSchema;
import com.sinosoft.lis.vschema.LDSysVarSet;

/**
 * <p>
 * Title: LIS系统Ftp专用类
 * </p>
 * <p>
 * Description: 为保单打印、个人凭证打印、发票打印提供Ftp服务
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author zhuxf
 * @version 1.0
 */
public class LisFtpClient {
private static Logger logger = Logger.getLogger(LisFtpClient.class);
	private String mErrMessage = "";
	private String FTPServerStr;

	public LisFtpClient() {
	}

	/**
	 * 上载文件到Ftp服务器
	 * 
	 * @param cFilePath
	 *            String
	 * @param cFileName
	 *            String
	 * @return boolean
	 */
	public boolean UpLoadFile(String cFilePath, String cFileName) {

		try {
			boolean success = true;
			String[] ftpstrs = getFTPServerStr().split(";");
			for (int i = 0; i < ftpstrs.length; i++) {
				String ftpstr = ftpstrs[i].trim();
				if (ftpstr.equals(""))
					continue;
				try {
					UploadToServer(cFilePath, cFileName, ftpstrs[i]);
				} catch (RuntimeException ex) {
					success = false;
					mErrMessage += ex.getMessage();
					ex.getCause().printStackTrace();
				}
			}
			return success;
		} catch (Exception ex) {
			mErrMessage += ex.getMessage();
			ex.getCause().printStackTrace();
			return false;
		}
	}

	public void UploadToServer(String cFilePath, String cFileName, String ftpstr) {
		String[] tString = ftpstr.split(",");
		if (tString.length != 4)
			throw new RuntimeException("Ftp字符串配置错误" + ftpstr);
		logger.debug("准备上传文件" + cFileName + "到" + tString[0]);

		// int port = 21;
		// String uid = "bitizxf";
		// String pwd = "biti78zxf";

		// 连接ftp服务器
//		FtpClient ftp = null;
//		try {
//			ftp = new FtpClient(tString[0], Integer.parseInt(tString[1]));
//			ftp.login(tString[2], tString[3]);
//			ftp.binary();
//		} catch (Exception ex) {
//			try {
//				ftp.closeServer();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			throw new RuntimeException("FtpServer " + tString[0] + "连接失败", ex);
//		}
		
		//jdk 1.7 
		FtpClient ftp = null;
		try {
			ftp =FtpClient.create(); 
			ftp.connect(new InetSocketAddress(tString[0], Integer.parseInt(tString[1]))); 
			ftp.login(tString[2], tString[3].toCharArray());
			ftp.setBinaryType();  
		} catch (Exception ex) {
			try {
				ftp.close();;
			} catch (IOException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("FtpServer " + tString[0] + "连接失败", ex);
		}

		InputStream sendFile = null;
		TelnetOutputStream outs = null;
		DataOutputStream outputs = null;
		try {
			sendFile = new FileInputStream(cFilePath);

//			outs = ftp.put(cFileName);
			outs = (TelnetOutputStream) ftp.putFileStream(cFileName, true);  //jdk 1.7
			outputs = new DataOutputStream(outs);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = sendFile.read(buffer)) >= 0) {
				outputs.write(buffer, 0, len);
			}
			logger.debug("上传文件" + cFileName + "成功");
		} catch (Exception ex) {
			throw new RuntimeException("文件上传到" + tString[0] + "失败", ex);
		} finally {
			try {
				outputs.close();
				outs.close();
				sendFile.close();
//				ftp.closeServer();
				ftp.close(); // jdk 1.7
			} catch (Exception ex) {
			}
		}

	}

	public String GetErrMessage() {
		return mErrMessage;
	}

	public static void main(String[] args) {
		// LisFtpClient tlisftpclient = new LisFtpClient();
		// tlisftpclient.UpLoadFile("E:\\lis\\abc.txt","/weblogic/app/datafile/temp/a.txt");
		String cFilePath = "E:\\lis\\abc.txt";
		String cFileName = "a.txt";
		String mErrMessage = "";

		String[] tString;

		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("FTPServer");
		if (!tLDSysVarDB.getInfo()) {
			logger.debug("没有配置Ftp服务器信息...");
			// mErrMessage = "没有配置Ftp服务器信息...";
			// return false;
		}
		tString = tLDSysVarDB.getSysVarValue().split(",");

		// int port = 21;
		// String uid = "bitizxf";
		// String pwd = "biti78zxf";

		// 连接ftp服务器
		FtpClient ftp = null;
		try {
			// ftp = new FtpClient(tString[0], Integer.parseInt(tString[1]));
			// ftp.login(tString[2], tString[3]);
			ftp =FtpClient.create(); 
			ftp.connect(new InetSocketAddress(tString[0], Integer.parseInt(tString[1]))); 
			//ftp.login("ftptest",null, "ftptest");
			ftp.login("ftptest","ftptest".toCharArray());
			ftp.setBinaryType();
		} catch (Exception ex) {
			logger.debug("FtpServer连接失败..." + ex.toString());
			mErrMessage = "FtpServer连接失败...";
			ex.printStackTrace();
			return;
		}

		try {
			RandomAccessFile sendFile = new RandomAccessFile(cFilePath, "r");
			sendFile.seek(0);

			int ch;
			TelnetOutputStream outs = (TelnetOutputStream) ftp.putFileStream(cFileName, true);
			DataOutputStream outputs = new DataOutputStream(outs);
			while (sendFile.getFilePointer() < sendFile.length()) {
				ch = sendFile.read();
				outputs.write(ch);
			}

			outs.close();
			sendFile.close();

			ftp.close();
		} catch (Exception ex) {
			logger.debug("文件上传失败" + ex.toString());
			mErrMessage = "文件上传失败";
			ex.printStackTrace();
			return;
		}
	}

	public String getFTPServerStr() {
		LDSysVarSet tLDSysVarSet = new LDSysVarSet();
		LDSysVarSchema tLDSysVarSchema = new LDSysVarSchema();
		if (FTPServerStr == null) {
			LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			tLDSysVarDB.setSysVar("FTPServer");
			tLDSysVarSet = tLDSysVarDB.query();
			if (tLDSysVarSet.size() > 0) {
				tLDSysVarSchema = tLDSysVarSet.get(1);
			} else {
				logger.debug("没有配置Ftp服务器信息...");
				mErrMessage += "没有配置Ftp服务器信息...";
				throw new RuntimeException("没有配置Ftp服务器信息");
			}

			// if (!tLDSysVarDB.getInfo()) {
			// logger.debug("没有配置Ftp服务器信息...");
			// mErrMessage += "没有配置Ftp服务器信息...";
			// throw new RuntimeException("没有配置Ftp服务器信息");
			// }
			FTPServerStr = tLDSysVarSchema.getSysVarValue().trim();
		}

		return FTPServerStr;
	}

	public void setFTPServerStr(String serverStr) {
		FTPServerStr = serverStr;
	}
}
