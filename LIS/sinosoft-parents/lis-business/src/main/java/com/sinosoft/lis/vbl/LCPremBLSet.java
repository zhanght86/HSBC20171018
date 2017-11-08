/*
 * <p>ClassName: LCPremBLSet </p>
 * <p>Description: LCPremSetBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-04-01
 */
package com.sinosoft.lis.vbl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.vschema.LCPremSet;

public class LCPremBLSet extends LCPremSet {
private static Logger logger = Logger.getLogger(LCPremBLSet.class);
	// @Constructor
	public LCPremBLSet() {
	}

	/**
	 * 设置该集合中的所有保单
	 * 
	 * @param cPolNo
	 */
	public void setPolNo(String cPolNo) {
		int i, iMax;
		for (i = 1; i < this.size(); i++) {
			this.get(i).setPolNo(cPolNo);
		}
	}
}
