package com.sinosoft.easyscan5.util;

import org.apache.log4j.Logger;

import com.sinosoft.lis.maxnomanage.CreateMaxNoImp;


/**
 * <p>Title: EasyScan影像</p>
 * <p>Description: 系统号码管理 </p>
 * <p> Copyright: Copyright (c) 2013</p>
 * <p> Company: Sinosoft </p> 
 * @version 1.0
 */
public class SysMaxNoBasic {
	private static Logger logger = Logger.getLogger(SysMaxNoBasic.class);

	public String createDocId(String manageCom) {
		CreateMaxNoImp CreateMaxNo=new CreateMaxNoImp();
		String docid = CreateMaxNo.getMaxNo("DocID");
		return docid;
	}
	public String createPageId(String manageCom){
		CreateMaxNoImp CreateMaxNo=new CreateMaxNoImp();
		String pageId = CreateMaxNo.getMaxNo("PageID");
		return pageId;
	}
}
