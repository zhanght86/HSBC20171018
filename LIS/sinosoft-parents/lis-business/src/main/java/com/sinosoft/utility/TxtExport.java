/**
 * Copyright (c) 2006 sinosoft  Co., Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;
import org.apache.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class TxtExport {
private static Logger logger = Logger.getLogger(TxtExport.class);
	private PrintStream m_ps = null;
	private String m_strEncoding = null;
	private String m_strFileName = null;

	public void createTxtDocument(String strFileName, String strEncoding) {

		if (strEncoding != null && !strEncoding.equals("")) {
			this.m_strEncoding = strEncoding;
		} else {
			this.m_strEncoding = "GBK";
		}

		try {
			this.m_ps = new PrintStream(new BufferedOutputStream(
					new FileOutputStream(strFileName)), true,
					this.m_strEncoding);

			this.m_strFileName = strFileName;

		} catch (Exception ex) {
			ex.printStackTrace();
			this.m_ps = null;
		}
	}

	public void outString(String name, String value) {
		outString(name + ":" + value);
	}

	public void outString(String txt) {
		m_ps.println(txt);
	}

	/**
	 * 将列表中的数据写入到TXT文件中，文件的格式为以"|"做为分隔符的文本文件。
	 * 
	 * @param listtable
	 *            ListTable
	 * @param colvalue
	 *            String[]
	 * @return Document
	 */
	public void outArray(String[] colvalues) {
		int col = colvalues.length;

		for (int i = 0; i <= col - 1; i++) {
			try {
				m_ps.print(colvalues[i]);
				if (i == col - 1) {
					m_ps.print("\n");
				} else {
					m_ps.print('|');
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public String getFileName() {
		if (this.m_ps != null) {
			try {
				m_ps.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				m_ps = null;
			}
		}

		String strNewFileName = this.m_strFileName
				.replaceAll(".txtpre", ".txt");

		File file = new File(this.m_strFileName);
		File newFile = new File(strNewFileName);
		try {
			if (!file.renameTo(newFile)) {
				FileInputStream fi = new FileInputStream(file);
				FileOutputStream fo = new FileOutputStream(newFile);
				int rlength = 0;
				byte[] b = new byte[4096];
				rlength = fi.read(b);
				while (rlength != -1) {
					fo.write(b, 0, rlength);
					rlength = fi.read(b);
				}
				fi.close();
				fo.flush();
				fo.close();
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strNewFileName;
	}

	public static void main(String args[]) {

		TxtExport xe = new TxtExport();

		xe.createTxtDocument("C:\\temp.txt", null);
		String[] str = { "one", "two", "three", "four" };

		xe.outArray(str);
		xe.getFileName();
	}
}
