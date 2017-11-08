/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1printgrp;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.sinosoft.utility.F1PrintParser;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * 格式化数据流
 * <p>
 * Description:
 * </p>
 * 格式化数据流并返回
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
public class CombineVts {
private static Logger logger = Logger.getLogger(CombineVts.class);
	private F1PrintParser f1p = null;
	private String strTemplatePath = null;

	/**
	 * 根据模板格式化数据流
	 * 
	 * @param ins
	 *            InputStream
	 * @param strPath
	 *            String
	 */
	public CombineVts(InputStream ins, String strPath) {
		try {
			strTemplatePath = strPath;
			if (ins == null) {
				XmlExport xmlExport = new XmlExport();
				xmlExport.createDocument("nofound.vts", "printer");
				f1p = new F1PrintParser(xmlExport.getInputStream(),
						strTemplatePath);
			} else {
				f1p = new F1PrintParser(ins, strTemplatePath);
			}
		} catch (Exception e) {
			logger.debug("F1PrintKernelJ1.jsp : fail to parse print data");
		}
	}

	/**
	 * 输出格式化后的数据流
	 * 
	 * @param dataStream
	 *            ByteArrayOutputStream
	 */
	public void output(ByteArrayOutputStream dataStream) {
		if (!f1p.output(dataStream)) {
			logger.debug("F1PrintKernelJ1.jsp : fail to parse print data");
		}
	}
}
