package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:保单的批单数据生成
 * </p>
 * <p>
 * Description:生成保全项目:保单批单数据
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author PST
 * @version 1.0
 */

public class PEdorRSPrintBL implements EdorPrint {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PEdorRSPrintBL.class);

	// 公共数据
	private VData mResult = new VData();

	// public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;

	private VData mInputData = new VData();

	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	public PEdorRSPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据传输
		if (!getInputData()) {
			CError.buildErr(this, "RS数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			CError.buildErr(this, "RS数据校验失败!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			CError.buildErr(this, "RS数据提取失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			CError.buildErr(this, "RS数据生成失败!");
			return false;
		}
		return true;
	}

	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		xmlexport = (XmlExportNew) mInputData
				.getObjectByObjectName("XmlExportNew", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTRS")) {
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
		xmlexport.addDisplayControl("displayHead1");
		xmlexport.addDisplayControl("displayRS");
		xmlexport.addDisplayControl("displayTail1");

		// 保全操作保单信息
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();

		/*
		 * 设置displayHead1中的信息
		 */
		mTextTag.add("EdorNo", mLPEdorItemSchema.getEdorNo());// 批单号
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo());// 保单号
		mTextTag.add("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());// 保全受理号

		/*
		 * 设置displayPS中的信息
		 */
		String tDate = mLPEdorItemSchema.getEdorValiDate();
		tDate = BqNameFun.getFDate(tDate);
		mTextTag.add("LCCont.AppntName", tLCContSchema.getAppntName());
		mTextTag.add("Date", tDate);
		String tPerson = "";
		String tFlag = mLPEdorItemSchema.getStandbyFlag1();
		if ("1".equals(tFlag)) {

			tPerson = "投保人";
		} else if ("2".equals(tFlag)) {
			tPerson = "被保人";
		} else if ("".equals(tFlag) || tFlag == null) {
			tPerson = "";
		}
		mTextTag.add("SignPerson", tPerson);

		/*
		 * 设置displayTail1中的信息
		 */
		ExeSQL tExeSQL = new ExeSQL();
		String tSql = "select agentcode from lpedorapp where edoracceptno ='?edoracceptno?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("edoracceptno", mLPEdorItemSchema.getEdorAcceptNo());
		String agentCode = tExeSQL.getOneValue(sqlbv);

		mTextTag.add("LPEdor.ValiDate", BqNameFun.getFDate(mLPEdorItemSchema
				.getEdorValiDate()));
		mTextTag.add("PrintDate", BqNameFun.getFDate(PubFun.getCurrentDate()));
		mTextTag.add("Operator", CodeNameQuery.getOperator(mLPEdorItemSchema
				.getOperator()));
		mTextTag.add("ApproveOperator", CodeNameQuery
				.getOperator(mLPEdorItemSchema.getApproveOperator()));

		String[] allArr = BqNameFun.getAllNames(tLCContSchema.getAgentCode());
		mTextTag.add("ManageCom", allArr[BqNameFun.SALE_SERVICE]);
		mTextTag.add("MCCode", BqNameFun.getAnother(null,"laagent", "agentcode",
				tLCContSchema.getAgentCode(), "ManageCom"));
		mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		mTextTag.add("LAAgent.Code", tLCContSchema.getAgentCode());

		if (!"".equals(agentCode)) {
			String[] agentArr = BqNameFun.getAllNames(agentCode);
			mTextTag.add("AgentManageCom", agentArr[BqNameFun.SALE_SERVICE]);
			mTextTag.add("AMCCode", "("
					+ BqNameFun.getAnother(null,"laagent", "agentcode",
							tLCContSchema.getAgentCode(), "ManageCom") + ")");
			mTextTag.add("AgentName", agentArr[BqNameFun.AGENT_NAME]);
			mTextTag.add("AgentCode", "(" + agentCode + ")");
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			return false;
		}
		xmlexport.addTextTag(mTextTag);
		xmlexport.addTextTag(mTextTag);
		// xmlexport.outputDocumentToFile("d:\\", "PEdorSCPrint"); // 测试用
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
