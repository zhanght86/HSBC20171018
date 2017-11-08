package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.easyscan.RelationConfig;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 保全单证上载工作流处理接口实现类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zhangtao
 * @ReWrite by zhangzm
 * @version 1.0
 */
public class DocBQWorkFlowAfterService extends BaseService {
private static Logger logger = Logger.getLogger(DocBQWorkFlowAfterService.class);
	public boolean deal() {
		logger.debug("====DocBQWorkFlowAfterService start====");
		MMap mMap = new MMap();
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mES_DOC_MAINSchema.getDocCode());
		LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.query();
		String strEdorAcceptNo = "";
		if (tLPEdorAppSet == null || tLPEdorAppSet.size() < 1) {
			// 事前扫描
			CError.buildErr(this, "未找到受理号，请确认是否应为事前扫描");
			return false;
		} else {
			// 事后扫描
			logger.debug("事后扫描");
			strEdorAcceptNo = mES_DOC_MAINSchema.getDocCode();
		}
		ES_DOC_RELATIONSchema mES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		mES_DOC_RELATIONSchema.setBussNo(strEdorAcceptNo);
		mES_DOC_RELATIONSchema.setDocCode(strEdorAcceptNo);
		mES_DOC_RELATIONSchema.setBussNoType("31");
		mES_DOC_RELATIONSchema.setDocID(mES_DOC_MAINSchema.getDocID());
		mES_DOC_RELATIONSchema.setBussType(mES_DOC_MAINSchema.getBussType());
		mES_DOC_RELATIONSchema.setSubType(mES_DOC_MAINSchema.getSubType());
		mES_DOC_RELATIONSchema.setRelaFlag("0");
		if (mES_DOC_RELATIONSchema != null) {
			logger.debug("ES_DOC_RELATIONSchema  ----- INSERT");
			mMap.put(mES_DOC_RELATIONSchema.getSchema(), "DELETE&INSERT");
		}
		if (mMap != null) {
			// 返回数据map
			mResult.add(mMap);
		} else {
			CError.buildErr(this, "数据生成出错!");
			return false;
		}

		return true;
	}

}
