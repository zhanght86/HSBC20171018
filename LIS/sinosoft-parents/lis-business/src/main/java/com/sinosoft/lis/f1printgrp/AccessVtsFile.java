/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1printgrp;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * Title:
 * </p>
 * 流数据存储
 * <p>
 * Description:
 * </p>
 * 将传入的流信息，存储到文件上
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SINOSOFT
 * </p>
 * 
 * @author 朱向峰
 * @version 1.0
 */
public class AccessVtsFile {
private static Logger logger = Logger.getLogger(AccessVtsFile.class);
	public AccessVtsFile() {
	}

	/**
	 * 把dataStream存储到磁盘文件
	 * 
	 * @param dataStream
	 *            ByteArrayOutputStream
	 * @param strVFPathName
	 *            String
	 */
	public static void saveToFile(ByteArrayOutputStream dataStream,
			String strVFPathName) {
		// store file of VTS to disc 333
		byte[] bArr = dataStream.toByteArray();
		try {
			OutputStream wFile = new FileOutputStream(strVFPathName);
			wFile.write(bArr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 读取文件，放到输出流中
	 * 
	 * @param dataStream
	 *            ByteArrayOutputStream
	 * @param strVFPathName
	 *            String
	 */
	public static void loadToBuffer(ByteArrayOutputStream dataStream,
			String strVFPathName) {
		// read file of VTS from disc
		try {
			InputStream rFile = new FileInputStream(strVFPathName);
			byte[] bArr = new byte[rFile.available()];
			rFile.read(bArr);
			dataStream.write(bArr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
