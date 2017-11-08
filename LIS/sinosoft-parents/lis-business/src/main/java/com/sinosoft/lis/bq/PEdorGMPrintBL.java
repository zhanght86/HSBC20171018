package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: PEdorGMPrintBL.java
 * </p>
 * 
 * <p>
 * Description: 领取方式变更（GM）批单打印
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wangyan
 * 
 * @version 1.0
 */

public class PEdorGMPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorGMPrintBL.class);

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

	public PEdorGMPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("GM数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("GM数据传入无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("GM数据处理失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("GM数据准备失败!");
			return false;
		}

		logger.debug("End preparing the data to print ====>" + mOperate);
		return true;
	}

	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		xmlexport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTGM")) {
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
		xmlexport.addDisplayControl("displayGM");

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContDB.getSchema();

		BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema, mTextTag);
		; // 申请人的姓名
		ExeSQL mExeSQL = new ExeSQL();
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql("SELECT b.GetDutyKind"
				+ " FROM LCGet b,LMDutyGet c" + " WHERE b.PolNo='?PolNo?' and b.ContNo = '?ContNo?'"
				+ " and b.GetDutyCode=c.GetDutyCode" + " and c.GetType2='1'");
		sbv1.put("PolNo", mLPEdorItemSchema.getPolNo());
		sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
		String sb1 = mExeSQL.getOneValue(sbv1);

		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql("SELECT a.ParamsName"
				+ " FROM LMRiskParamsDef a,LCPol b" + " WHERE b.PolNo='?PolNo?' and b.ContNo = '?ContNo?'"
				+ " and a.RiskCode = b.RiskCode and a.ParamsCode = '?ParamsCode?' and a.paramstype = 'getdutykind'");
		sbv2.put("PolNo", mLPEdorItemSchema.getPolNo());
		sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
		sbv2.put("ParamsCode", sb1);
		String mode1 = mExeSQL.getOneValue(sbv2);

		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql("SELECT b.GetDutyKind"
				+ " FROM LPGet b,LMDutyGet c" + " WHERE b.PolNo='?PolNo?' and b.ContNo = '?ContNo?'" + " and b.EdorNo='?EdorNo?' and b.EdorType = '?EdorType?'"
				+ " and b.GetDutyCode=c.GetDutyCode" + " and c.GetType2='1'");
		sbv3.put("PolNo", mLPEdorItemSchema.getPolNo());
		sbv3.put("ContNo", mLPEdorItemSchema.getContNo());
		sbv3.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sbv3.put("EdorType", mLPEdorItemSchema.getEdorType());
		String sb2 = mExeSQL.getOneValue(sbv3);

		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql("SELECT a.ParamsName"
				+ " FROM LMRiskParamsDef a,LCPol b" + " WHERE b.PolNo='?PolNo?' and b.ContNo = '?ContNo?'"
				+ " and a.RiskCode = b.RiskCode and a.ParamsCode = '?ParamsCode?' and a.paramstype = 'getdutykind'");
		sbv4.put("PolNo", mLPEdorItemSchema.getPolNo());
		sbv4.put("ContNo", mLPEdorItemSchema.getContNo());
		sbv4.put("ParamsCode", sb2);
		String mode2 = mExeSQL.getOneValue(sbv4);

		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql("SELECT b.Actuget"
				+ " FROM LPGet b,LMDutyGet c" + " WHERE b.PolNo='?PolNo?' and b.ContNo = '?ContNo?'"
				+ " and b.GetDutyCode=c.GetDutyCode" + " and c.GetType2='1'"
				+ " and b.edorno = '?EdorNo?' and b.EdorType = '?EdorType?'");
		sbv5.put("PolNo", mLPEdorItemSchema.getPolNo());
		sbv5.put("ContNo", mLPEdorItemSchema.getContNo());
		sbv5.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sbv5.put("EdorType", mLPEdorItemSchema.getEdorType());
		String money = mExeSQL.getOneValue(sbv5);
		if (money == null || money.trim().equals("")) {
			SQLwithBindVariables sbv6=new SQLwithBindVariables();
			sbv5.sql("SELECT b.Actuget"
					+ " FROM LPGet b,LMDutyGet c" + " WHERE b.PolNo='?PolNo?' and b.ContNo = '?ContNo?'"
					+ " and b.GetDutyCode=c.GetDutyCode"
					+ " and c.GetType2='0'" + " and b.edorno = '?EdorNo?' and b.EdorType = '?EdorType?'");
			sbv6.put("PolNo", mLPEdorItemSchema.getPolNo());
			sbv6.put("ContNo", mLPEdorItemSchema.getContNo());
			sbv6.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv6.put("EdorType", mLPEdorItemSchema.getEdorType());
			money = mExeSQL.getOneValue(sbv6);
		}

		mTextTag.add("GMLCGet.GetIntv", mode1); // 原领取方式
		mTextTag.add("GMLPGet.GetIntv", mode2); // 新领取方式
		// ===del====zhangtao===2006-08-02=======去掉领取标准不用显示=========BGN===========
		// if(!mRiskCode.equals("00618000"))
		// {
		// if(money == null || money.trim().equals("")){
		// CError.buildErr(this,"查询领取标准失败!");
		// return false;
		// }
		// DecimalFormat df = new DecimalFormat("0.00");
		// double d = Double.parseDouble(money);
		// money = df.format(d);
		// mTextTag.add("GMLPGet.StandMoney",money); //新领取标准
		// xmlexport.addDisplayControl("displayGMGet");
		// }
		// ===del====zhangtao===2006-08-02=======去掉领取标准不用显示=========END===========

		// BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
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
