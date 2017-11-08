package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.io.Serializable;

public class BqPrintBean implements Serializable{
private static Logger logger = Logger.getLogger(BqPrintBean.class);
	private byte[] bytes;

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
}
