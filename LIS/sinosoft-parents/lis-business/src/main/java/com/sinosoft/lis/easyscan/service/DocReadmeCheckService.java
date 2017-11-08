package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;

public class DocReadmeCheckService extends BaseService {
private static Logger logger = Logger.getLogger(DocReadmeCheckService.class);

	protected boolean deal() {
		String sql = "select count(1) from es_doc_main where doccode='"
				+ "?doccode?"+ "' and subtype in ('UA001')";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("doccode", mES_DOC_MAINSchema.getDocCode());
		
		String r = new ExeSQL().getOneValue(sqlbv1);
		if ("0".equals(r)) {
			CError.buildErr(this, "请先上传投保单");
			return false;
		}
		return true;
	}

}
