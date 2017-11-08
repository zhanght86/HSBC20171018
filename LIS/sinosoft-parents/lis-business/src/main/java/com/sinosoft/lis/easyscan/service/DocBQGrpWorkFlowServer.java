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
 * Description: 团体保全单证上载工作流处理接口实现类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author niuzj,2006-10-31
 * @version 1.0
 */
public class DocBQGrpWorkFlowServer extends BaseService {
private static Logger logger = Logger.getLogger(DocBQGrpWorkFlowServer.class);
	public boolean deal() {
		logger.debug("====DocBQGrpWorkFlowServer start====");
		MMap mMap = new MMap();
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mES_DOC_MAINSchema.getDocCode());
		LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.query();
		String strEdorAcceptNo = "";
		if (tLPEdorAppSet == null || tLPEdorAppSet.size() < 1) {
			// 事前扫描
			logger.debug("事前扫描");
			String strLimit = PubFun.getNoLimit(mES_DOC_MAINSchema
					.getManageCom());
			strEdorAcceptNo = PubFun1.CreateMaxNo("EdorAcceptNo", strLimit);

			if (StrTool.compareString(strEdorAcceptNo, "")) {
				CError.buildErr(this, "生成保全受理号错误!");
				return false;
			}

			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("EdorAcceptNo", strEdorAcceptNo);
			tTransferData.setNameAndValue("DocID", String
					.valueOf(mES_DOC_MAINSchema.getDocID()));
			tTransferData.setNameAndValue("InputDate", mES_DOC_MAINSchema
					.getMakeDate());
			tTransferData.setNameAndValue("ScanOperator", mES_DOC_MAINSchema
					.getScanOperator());
			tTransferData.setNameAndValue("ManageCom", mES_DOC_MAINSchema
					.getManageCom());
			tTransferData.setNameAndValue("SubType", mES_DOC_MAINSchema
					.getSubType());
			// 必须先经过转化
			RelationConfig nRelationConfig = RelationConfig.getInstance();
			String sSubType = nRelationConfig
					.getBackRelation(mES_DOC_MAINSchema.getSubType());
			if (sSubType == null || sSubType.length() != 6) {
				CError.buildErr(this, "传入数据出错!");
				return false;
			}

			String DocType = sSubType.substring(4, 6);
			tTransferData.setNameAndValue("SubType", DocType);

			GlobalInput tGlobalInput = new GlobalInput();
			tGlobalInput.Operator = mES_DOC_MAINSchema.getScanOperator();
			tGlobalInput.ComCode = mES_DOC_MAINSchema.getManageCom();
			tGlobalInput.ManageCom = mES_DOC_MAINSchema.getManageCom();

			VData tVData = new VData();
			tVData.add(tGlobalInput);
			tVData.add(tTransferData);
			MMap tMap;
			try {
				ActivityOperator tActivityOpertor = new ActivityOperator();
				if (tActivityOpertor.CreateStartMission("0000000000",
						"0000008001", tVData)) {

					VData aVData = new VData();
					aVData = tActivityOpertor.getResult();
					tMap = (MMap) aVData.getObjectByObjectName("MMap", 0);
					if (tMap == null) {
						CError.buildErr(this, "接受保全工作流节点信息失败!");
						return false;
					} else {

					}
				} else {
					CError.buildErr(this, "保全扫描申请录入工作流节点生成失败!");
					return false;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				CError.buildErr(this, "保全扫描申请录入工作流节点生成失败!");
				return false;
			}
			mES_DOC_MAINSchema.setDocCode(strEdorAcceptNo);// 用受理号替换随机号码
			mMap.put(mES_DOC_MAINSchema.getSchema(), "DELETE&INSERT");
			mMap.add(tMap);

		} else {
			// 事后扫描
			CError.buildErr(this, "受理号已存在，请确认是否应为事后扫描");
			return false;
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
