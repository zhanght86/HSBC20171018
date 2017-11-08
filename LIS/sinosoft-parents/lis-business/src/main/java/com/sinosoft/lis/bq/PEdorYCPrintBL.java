package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:保险期间变更批单数据生成
 * </p>
 * 
 * <p>
 * Description:生成客户资料批单打印所需要的数据
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class PEdorYCPrintBL implements EdorPrint {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PEdorYCPrintBL.class);

	// 公共数据
	// public CErrors mErrors = new CErrors();

	// 结果容器
	private VData mResult = new VData();

	// 全局变量
	private VData mInputData;

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private LPEdorItemSchema mLPEdorItemSchema;

	public PEdorYCPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("YC数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("YC传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("YC数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("YC准备数据失败!");
			return false;
		}

		return true;
	}

	private boolean getInputData() {

		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		xmlexport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			mErrors.addOneError("YC得到数据失败!");
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		if (!tLPEdorItemDB.getInfo()) {
			mErrors.addOneError("YC中查询保全数据失败!");
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
		return true;
	}

	private boolean checkData() {
		return true;
	}

	private boolean dealData() {
		/** 要显示的头和尾部分 */
		xmlexport.addDisplayControl("displayHead1");
		xmlexport.addDisplayControl("displayTail1");
		xmlexport.addDisplayControl("displayYC");

		String strSQL = " select  lc.insuyear ,lp.insuyear,lp.cvalidate,lp.enddate   ,lp.insuyearflag ,lp.insuredname "
				+ " from lppol  lp,lcpol lc where 1=1 and  "
				+ "  lp.edorno='?edorno?'  and lp.polno='?polno?'  and lp.contno='?contno?'  "
				+ " and lc.contno=lp.contno  and lc.polno=lp.polno ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("polno", mLPEdorItemSchema.getPolNo());
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());

		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);

		if (!(tSSRS != null || tSSRS.MaxRow > 0)) {
			CError.buildErr(this, "查询数据出错！");
			return false;
		}

		String tInsureYearUnit = "天";
		String tInsureYearFlag = tSSRS.GetText(1, 5);
		if (tInsureYearFlag.equals("A") || tInsureYearFlag.equals("Y")) {
			tInsureYearUnit = "年";
		} else if (tInsureYearFlag.equals("M")) {
			tInsureYearUnit = "月";
		} else {

		}
		mTextTag.add("PreInsuDays", tSSRS.GetText(1, 1) + tInsureYearUnit);
		mTextTag.add("NowInsuDays", tSSRS.GetText(1, 2) + tInsureYearUnit);
		mTextTag.add("InsuStartDate", tSSRS.GetText(1, 3));
		mTextTag.add("InsuEndDate", tSSRS.GetText(1, 4));

		mTextTag.add("InsuredName", tSSRS.GetText(1, 6));

		if (mLPEdorItemSchema.getGetMoney() > 0) {
			mTextTag.add("TFORAdd", "应补金额合计：");
		} else {
			mTextTag.add("TFORAdd", "应退金额合计：");
		}
		mTextTag.add("NeedMoneyCh", StrTool.toChinese(Math
				.abs(mLPEdorItemSchema.getGetMoney()))
				+ "元整");
		mTextTag.add("NeedMoneyEn", Math.abs(mLPEdorItemSchema.getGetMoney()));
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();

		/** 批单头部 */
		mTextTag.add("AppntName", tLCContSchema.getAppntName()); // 原投保人姓名
		mTextTag.add("InsuredName", tLCContSchema.getInsuredName()); // 被保人姓名
		mTextTag.add("EdorNo", mLPEdorItemSchema.getEdorNo()); // 批单号
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo()); // 保单号

		/** 批单尾部 */
		mTextTag.add("LPEdor.ValiDate", mLPEdorItemSchema.getEdorValiDate()); // 生效日
		mTextTag.add("Operator", CodeNameQuery.getOperator(mLPEdorItemSchema
				.getOperator())); // 经办人
		mTextTag.add("ApproveOperator", CodeNameQuery
				.getOperator(mLPEdorItemSchema.getApproveOperator())); // 复核人

		String[] allArr = BqNameFun.getAllNames(tLCContSchema.getAgentCode());
		mTextTag.add("ManageCom", allArr[BqNameFun.SALE_SERVICE]);
		mTextTag.add("LABranchGroup.Name", allArr[BqNameFun.DEPART]);
		mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		mTextTag.add("LAAgent.AgentCode", tLCContSchema.getAgentCode());
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
