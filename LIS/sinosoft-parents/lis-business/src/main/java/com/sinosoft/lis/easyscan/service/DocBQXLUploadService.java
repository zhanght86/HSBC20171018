package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.utility.CError;

public class DocBQXLUploadService extends BaseService {
private static Logger logger = Logger.getLogger(DocBQXLUploadService.class);

	protected boolean deal() {
		logger.debug("====DocBQXLUploadService start====");
		MMap mMap = new MMap();
		ES_DOC_MAINDB tES_DOC_MAINDB=new ES_DOC_MAINDB();
		tES_DOC_MAINDB.setDocCode(mES_DOC_MAINSchema.getDocCode());
		tES_DOC_MAINDB.setSubType("BQ001");
		tES_DOC_MAINDB.setScanOperator(mES_DOC_MAINSchema.getScanOperator());
		if (tES_DOC_MAINDB.getCount() < 1) {
			// 事前扫描
			CError.buildErr(this, "未找到用户"+mES_DOC_MAINSchema.getScanOperator()+"的此受理号");
			return false;
		}

		ES_DOC_RELATIONSchema tES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		tES_DOC_RELATIONSchema.setBussNo(mES_DOC_MAINSchema.getDocCode());
		tES_DOC_RELATIONSchema.setDocCode(mES_DOC_MAINSchema.getDocCode());
		tES_DOC_RELATIONSchema.setBussNoType("31");
		tES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
		tES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema.getBussType());
		tES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema.getSubType());
		tES_DOC_RELATIONSchema.setRelaFlag("0");
		mMap.put(tES_DOC_RELATIONSchema.getSchema(), "DELETE&INSERT");
		mResult.add(mMap);

		return true;
	}
}
