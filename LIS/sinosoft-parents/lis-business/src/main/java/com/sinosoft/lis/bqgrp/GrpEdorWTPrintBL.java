package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;



import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.bq.EdorPrint;

import com.sinosoft.lis.db.LCGrpContDB;

import com.sinosoft.lis.db.LPEdorAppDB;

import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:投保人基本信息变更批单数据生成
 * </p>
 * 
 * <p>
 * Description:生成保全项目被保险人基本信息变更的批单数据
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

public class GrpEdorWTPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorWTPrintBL.class);


	// 全局变量
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();
	// 全局变量
	private String mOperate;
	private VData mInputData = new VData();
	private TextTag mTextTag = new TextTag();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();
	private XmlExportNew xmlexport = new XmlExportNew();

	public GrpEdorWTPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据传输
		if (!getInputData()) {
			CError.buildErr(this, "WT数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			CError.buildErr(this, "WT数据提取失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			CError.buildErr(this, "WT数据生成失败!");
			return false;
		}
		return true;
	}

	private boolean getInputData() {
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
		.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		xmlexport = (XmlExportNew) mInputData
		.getObjectByObjectName("XmlExportNew", 0);
		if (mLPGrpEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTWT")) {
			return false;
		}
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemSet.size() < 1) {
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1));

		return true;
	}

	private boolean dealData() {
		// xmlexport.createDocument("PrtAppEndorsement.vts", "printer");

		xmlexport.addDisplayControl("displayHead");
		xmlexport.addDisplayControl("displayWT");

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "查询原保单信息失败!");
			return false;
		}
		tLCGrpContSchema = tLCGrpContDB.getSchema();
		
		mTextTag.add("GrpName", tLCGrpContSchema.getGrpName()); // 单位名称
		if(mLPEdorAppSchema != null){
			mTextTag.add("EdorNo", mLPEdorAppSchema.getEdorConfNo()); // 团体批单
		}else{
			mTextTag.add("EdorNo", mLPGrpEdorItemSchema.getEdorNo());
		}
		mTextTag.add("EdorAcceptNo", mLPGrpEdorItemSchema.getEdorAcceptNo()); // 保全受理号
		mTextTag.add("GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号
		mTextTag.add("EdorAppDate", mLPGrpEdorItemSchema.getEdorAppDate()); // 团体保单号

        

        ListTable tWTListTable = new ListTable();
        tWTListTable.setName("WT");
        String tSql = "Select m.RiskName, "
        			+ "'退费', "
        			+ "nvl((Select Sum(Prem) "
        			+ "from LCPol "
        			+ "where GrpPolNo = g.GrpPolNo "
        			+ " and appflag = 1),"
        			+ "0) "
        			+ "from LCGrpPol g, LMRisk m "
        			+ "where g.RiskCode = m.RiskCode "
        			+ "and g.AppFlag = '1'"
        			+ "and g.GrpContNo = '"+mLPGrpEdorItemSchema.getGrpContNo()+"'";
        			
		SSRS rSSRS = new SSRS();
		ExeSQL rExeSQL = new ExeSQL();
		rSSRS = rExeSQL.execSQL(tSql);
		if (rSSRS == null || rSSRS.MaxRow < 1) {
	       	CError.buildErr(this, "查询本次保全记录失败");
        	return false;
		}
		int tArrLen = rSSRS.MaxRow;
		for (int i = 1; i <= tArrLen; i++) {
			String[] strWT = new String[3] ;
			for(int k = 1;k<=strWT.length;k++){
				strWT[k-1] = rSSRS.GetText(i, k);
			}
			tWTListTable.add (strWT) ;
		}
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		mLPEdorAppSchema = tLPEdorAppDB.query().get(1);

		

		mTextTag.add("WTMONEY", mLPGrpEdorItemSchema.getGetMoney());
		String tUserCode = mLPEdorAppSchema.getOperator();
		mTextTag.add("EdorAppName", CodeNameQuery.getOperator(tUserCode));
		String tApproveOperator = mLPEdorAppSchema.getApproveOperator();
		String tApproveName ="";
		if(tApproveOperator == null || "".equals(tApproveOperator)){
			tApproveName ="";
		}else{
			tApproveName = CodeNameQuery.getOperator(tApproveOperator);
		}
		mTextTag.add("ApproveOperator", tApproveName);
		mTextTag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate());
		mTextTag.add("ApproveDate", mLPEdorAppSchema.getApproveDate());
		mTextTag.add("Operator", mLPGrpEdorItemSchema.getOperator());
		String[] b_strWT = new String[2];
		xmlexport.addListTable(tWTListTable, b_strWT);
		xmlexport.addTextTag(mTextTag);
		xmlexport.addDisplayControl("displayVD");
		xmlexport.addDisplayControl("displayTail");
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
	
	public static void main(String[] args){
		String tNewEndDate = "";//新的止期
		ExeSQL tExeSQL = new ExeSQL();
		String tSql = "select max(payenddate) from lpgrppol where grpcontno = '86110020090220000017' and edorno = '86000020090469000554'";
		tNewEndDate = tExeSQL.getOneValue(tSql);
		if (tNewEndDate == null || tNewEndDate.equals("")) {

		}
		String tOldEndDate = "";//旧的止期
		tSql = "select max(payenddate+1) from lcgrppol where grpcontno = '86110020090220000017'";
		tOldEndDate = tExeSQL.getOneValue(tSql);
		
		logger.debug(tNewEndDate.substring(0, 4));
		logger.debug(tOldEndDate.substring(0, 10));
		String[] sOldEndDate = tOldEndDate.substring(0, 10).split("-");
		logger.debug(sOldEndDate[0]+"年"+sOldEndDate[1]+"月"+sOldEndDate[2]+"日");
	}
}
