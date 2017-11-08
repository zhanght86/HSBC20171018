package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPCSpecDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 特约变更
 * </p>
 * 
 * <p>
 * Description: 特约变更批单数据生成
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * </p>
 * 
 * @author changpeng
 * @version 1.0
 */

public class PEdorSCPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorSCPrintBL.class);

	// 公共数据
	private VData mResult = new VData();

	// public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;

	private VData mInputData = new VData();

	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private ListTable tCTListTable = new ListTable();

	/** 保全保单结算计算类 */

	public PEdorSCPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("SC数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("SC数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("准备数据失败!");
			return false;
		}
		return true;
	}

	private boolean getInputData() {

		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		xmlexport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		if (mLPEdorAppSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTSC")) {
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet.size() < 1) {
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		return true;
	}

	private boolean dealData() {
		// xmlexport.addDisplayControl("displayHead1");
		BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		xmlexport.addDisplayControl("displaySC");

		ExeSQL tExeSQL = new ExeSQL();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}

		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名

		tCTListTable.setName("SC");

		LPCSpecDB tLPCSpecDB = new LPCSpecDB();
		LPCSpecSet tLPCSpecSet = new LPCSpecSet();

		String tSQL = "select * from LPCSpec where  SpecType not in ('hx','mn','nf','qt','sj','xi','xu','zx','ch') and contno='?contno?' and edorno='?edorno?' order by Serialno ";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(tSQL);
		sbv1.put("contno", mLPEdorItemSchema.getContNo());
		sbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
		String tCSQL = "select 1 from lwmission where activityid='0000000009'  and missionprop1='?missionprop1?' and  missionprop2='?missionprop2?'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(tCSQL);
		sbv2.put("missionprop1", mLPEdorItemSchema.getEdorAcceptNo());
		sbv2.put("missionprop2", mLPEdorItemSchema.getContNo());
		String tConfirmFLag = tExeSQL.getOneValue(sbv2);
		if ("1".equals(tConfirmFLag)) // 已经在保全确认节点。查询所有的P表特约，考虑到二核会增加特约
		{
			tSQL = "select * from LPCSpec where contno='?contno?' and edorno='?edorno?'";
			sbv1=new SQLwithBindVariables();
			sbv1.sql(tSQL);
			sbv1.put("contno", mLPEdorItemSchema.getContNo());
			sbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
		}

		tLPCSpecSet = tLPCSpecDB.executeQuery(sbv1);
		int tLength = tLPCSpecSet.size();
		if (tLength < 1) {
			mErrors.addOneError("打印生成数据时，取保单的特约信息失败!");
			return false;
		}
		String tSqlRisk = "select concat(concat((select riskname from lmrisk a where a.riskcode=b.riskcode),'--'),b.riskcode) from LCpol b where ContNo='?ContNo?' and polno=mainpolno";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(tSqlRisk);
		sbv3.put("ContNo", mLPEdorItemSchema.getContNo());
		String tMainRiskName = tExeSQL.getOneValue(sbv3);
		for (int i = 1; i <= tLength; i++) {
			LPCSpecSchema tLPCSpecSchmea = new LPCSpecSchema();
			tLPCSpecSchmea = tLPCSpecSet.get(i);
			String[] strSC = new String[3];

			strSC[0] = tMainRiskName;
			String tSql = "select (case when SpecContent is not null then SpecContent else '' end) from LCCSpec where ContNo='?ContNo?' and SerialNo='?SerialNo?'";
			SQLwithBindVariables sbv4=new SQLwithBindVariables();
			sbv4.sql(tSql);
			sbv4.put("ContNo", mLPEdorItemSchema.getContNo());
			sbv4.put("SerialNo", tLPCSpecSchmea.getSerialNo());
			String tOldSpecContent = tExeSQL.getOneValue(sbv4);
			if ("".equals(tOldSpecContent)) {
				strSC[1] = "（新增特约，原来为空）";
			} else {
				if ("del".equals(tLPCSpecSchmea.getBackupType())) {
					tOldSpecContent += "(删除特约)";
				}
				strSC[1] = tOldSpecContent;
			}
			strSC[2] = tLPCSpecSchmea.getSpecContent();
			tCTListTable.add(strSC);
		}

		String[] b_strCT = new String[3];
		xmlexport.addListTable(tCTListTable, b_strCT);
		BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema,
				xmlexport);
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
			return false;
		}
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
