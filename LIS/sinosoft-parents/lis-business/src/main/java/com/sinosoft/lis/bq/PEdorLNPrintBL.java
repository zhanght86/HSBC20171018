package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPLoanDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPLoanSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:保单质押贷款批单数据生成
 * </p>
 * 
 * <p>
 * Description:生成保全项目:保单质押贷款的批单数据
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

public class PEdorLNPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(PEdorLNPrintBL.class);

	// 公共数据
	private VData mResult = new VData();
	//public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;
	private VData mInputData = new VData();
	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorAppSchema mLPEdorAppSchema =new LPEdorAppSchema();
	private XmlExportNew xmlexport = new XmlExportNew();
	private TextTag mTextTag = new TextTag();
	private ExeSQL tExeSQL = new ExeSQL();
	private LJAGetSet mLJAGetSet = new LJAGetSet();


	public PEdorLNPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据传输
		if (!getInputData()) {
			CError.buildErr(this, "LN数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			CError.buildErr(this, "LN数据校验失败!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			CError.buildErr(this, "LN数据提取失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			CError.buildErr(this, "LN数据生成失败!");
			return false;
		}
		return true;
	}

	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
	    mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName (
	            "LPEdorAppSchema",0) ;
		xmlexport = (XmlExportNew) mInputData
				.getObjectByObjectName("XmlExportNew", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLJAGetSet = (LJAGetSet)mInputData
		.getObjectByObjectName("LJAGetSet", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTLN")) {
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
		//xmlexport.addDisplayControl("displayHead1");
BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		xmlexport.addDisplayControl("displayLN");

	    BqNameFun.AddEdorHeadInfo(mLPEdorAppSchema,mTextTag); //申请人的姓名

		LPLoanDB tLPLoanDB = new LPLoanDB();
		LPLoanSchema tLPLoanSchema =null;
		tLPLoanDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPLoanDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPLoanDB.setPayOffFlag("0");
		tLPLoanDB.setLoanType("0");
		tLPLoanDB.setEdorType(mLPEdorItemSchema.getEdorType());

		tLPLoanSchema = tLPLoanDB.query().get(1);
		if(tLPLoanSchema==null)
		{
			CError.buildErr(this, "打印生成数据时，取借款信息失败!");
			return false;			
		}
		String tRequitalDate = tLPLoanSchema.getPayOffDate();
		if (tRequitalDate == null || tRequitalDate.trim().equals("")) {
			tRequitalDate = PubFun.calDate(mLPEdorItemSchema.getEdorValiDate(),
					6, "M", null);
			tRequitalDate = PubFun.calDate(tRequitalDate, -1, "D", null);
		}

		mTextTag.add("LNLOLoan.EndDate", tRequitalDate); // 贷款止期
		mTextTag.add("LNLOLoan.InterestRate", tLPLoanSchema.getInterestRate()); // 利率
		double sCurLoanMoney = tLPLoanSchema.getLeaveMoney();
		mTextTag.add("LNLOLoan.Corpus", PubFun.getChnMoney(sCurLoanMoney)+"(￥"+sCurLoanMoney+")"); // 本金

		BqPolBalBL tBqPolBalBL = new BqPolBalBL();
		if (!tBqPolBalBL.calLoanCorpus(
				tLPLoanSchema.getContNo(), mLPEdorItemSchema.getEdorAppDate())) {
			mErrors.copyAllErrors(tBqPolBalBL.mErrors);
			return false;
		}
		double tLastLoanMoney=tBqPolBalBL.getCalResult();
		mTextTag.add("LNLOLoan.PreCorpus",PubFun.getChnMoney(tLastLoanMoney)+"(￥"+tLastLoanMoney+")"); // 上次借款本金	
		if (!tBqPolBalBL.calLoanInterest(
				tLPLoanSchema.getContNo(), mLPEdorItemSchema.getEdorAppDate())) {
			mErrors.copyAllErrors(tBqPolBalBL.mErrors);
			return false;
		}
		double tLastLX=tBqPolBalBL.getCalResult();		
		mTextTag.add("LNLOLoan.PreLX", PubFun.getChnMoney(tLastLX)+"(￥"+tLastLX+")"); // 利息
		
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql("select getmoney from ljsgetendorse where endorsementno = '?endorsementno?' and feeoperationtype = '?feeoperationtype?' and contno = '?contno?' and subfeeoperationtype = '?subfeeoperationtype?'");
		sbv.put("endorsementno", mLPEdorItemSchema.getEdorNo());
		sbv.put("feeoperationtype", mLPEdorItemSchema.getEdorType());
		sbv.put("contno", mLPEdorItemSchema.getContNo());
		sbv.put("subfeeoperationtype", BqCode.Pay_Revenue);
		String tStampMoney = tExeSQL
				.getOneValue(sbv);
		if (tStampMoney == null || tStampMoney.trim().equals("")) {
			tStampMoney = "0.00";
		}
		mTextTag.add("LNLOLoan.StampMoney", BqNameFun.getRound(tStampMoney)); // 印花税金额
      //
	    //实退金额
//	    String strSQL = "SELECT abs(Sum(GetMoney))"
		String strSQL = "SELECT Sum((case getflag when '1' then GetMoney else -GetMoney end))"
	        + " FROM LJSGetEndorse"
	        + " where EndorsementNo='?EndorsementNo?'"
	        + " and FeeOperationType ='?FeeOperationType?'" ;
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(strSQL);
		sbv1.put("EndorsementNo", mLPEdorItemSchema.getEdorNo ());
		sbv1.put("FeeOperationType", mLPEdorItemSchema.getEdorType ());
	    String tSumActGetMoney=tExeSQL.getOneValue (sbv1);
	    if ("".equals(tSumActGetMoney))
	    {
	    	tSumActGetMoney="0.00";
	    }
	    mTextTag.add ("tSumActGetMoney",PubFun.getChnMoney(Double.parseDouble(tSumActGetMoney))+"(￥"+tSumActGetMoney+")") ;
		
	    BqNameFun.AddEdorGetInfo(this.mLJAGetSet, this.mLPEdorAppSchema, xmlexport, mTextTag);
	    //BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema,xmlexport);
         BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);

		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			return false;
		}
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "862100";
		tGlobalInput.Operator = "001";

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		XmlExport xmlexport = new XmlExport();
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		// 最好紧接着就初始化xml文档
		xmlexport.createDocument("PrtEndorsement.vts", "printer");
		// 一个保单出一张批单
		tLPEdorItemDB.setEdorNo("6020060629000005");
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet.size() < 1) {
			logger.debug("查询保全项目失败");
		}
		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			// 批单打印有各个项目的单独生成打印数据
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql("select edorname from lmedoritem where edorcode = '?edorcode?' and appobj = 'I'");
			sbv2.put("edorcode", tLPEdorItemSchema.getEdorType());
			String EdorName = tExeSQL
					.getOneValue(sbv2);
			try {
				Class tClass = Class.forName("com.sinosoft.lis.bq.PEdor"
						+ tLPEdorItemSchema.getEdorType() + "PrintBL");
				EdorPrint tEdorPrint = (EdorPrint) tClass.newInstance();
				VData aVData = new VData();
				aVData.add(tLPEdorItemSchema);
				aVData.add(xmlexport);
				aVData.add(tGlobalInput);
				if (!tEdorPrint.submitData(aVData, "PRINT"
						+ tLPEdorItemSchema.getEdorType())) {
					logger.debug("保全项目" + EdorName + "打印处理失败!");
				}
				VData cVData = new VData();
				cVData = tEdorPrint.getResult();
				xmlexport = (XmlExport) cVData.getObjectByObjectName(
						"XmlExport", 0);
			} catch (ClassNotFoundException ex) {
				logger.debug("未找到" + EdorName + "保全项目打印处理!");
			} catch (Exception ex) {
				logger.debug("保全项目" + EdorName + "打印处理失败!");
			}
			logger.debug("成功完成" + EdorName + "保全项目打印处理!");
		}
	}
}
