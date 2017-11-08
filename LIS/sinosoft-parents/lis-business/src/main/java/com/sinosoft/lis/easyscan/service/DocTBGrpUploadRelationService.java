package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: 团体投保单上传后对于关联表的的操作</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author tuqiang
 * @version 1.0
 */

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.utility.CError;

public class DocTBGrpUploadRelationService extends BaseService {
private static Logger logger = Logger.getLogger(DocTBGrpUploadRelationService.class);

	public boolean deal() {
		ES_DOC_RELATIONSchema tES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		tES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
		tES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema.getBussType());
		tES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema.getSubType());
		tES_DOC_RELATIONSchema.setRelaFlag("0");
		tES_DOC_RELATIONSchema.setBussNoType("12");
		tES_DOC_RELATIONSchema.setBussNo(mES_DOC_MAINSchema.getDocCode());
		if (tES_DOC_RELATIONSchema != null) {
			// 返回数据map
			MMap map = new MMap();
			map.put(tES_DOC_RELATIONSchema, "INSERT");
			mResult.add(map);
		} else {
			CError tError = new CError();
			tError.moduleName = "DocTBWorkFlowService";
			tError.functionName = "Save";
			tError.errorNo = "-500";
			tError.errorMessage = "数据生成出错!";
			return false;
		}
		return true;
	}

}
