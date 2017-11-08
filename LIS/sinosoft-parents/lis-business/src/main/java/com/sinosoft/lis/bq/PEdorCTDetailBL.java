/*
 * @(#)PEdorCTDetailBL.java	2005-07-07
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccClassFeeDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccFeeDB;
import com.sinosoft.lis.db.LCInsureAccFeeTraceDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LOBonusPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPBonusPolSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccFeeTraceSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LOBonusPolSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPBonusPolSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全- 保单退保变更明细保存处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：zhangtao pst
 * @version：1.0,2.0
 * @CreateDate：2005-07-07，2008-12-07
 */
public class PEdorCTDetailBL {
private static Logger logger = Logger.getLogger(PEdorCTDetailBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private MMap map = new MMap();
	
	private ExeSQL mExeSql = new ExeSQL();

	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPInsuredSet mLPInsuredSet = new LPInsuredSet();
	
	private LPContStateSet mLPContStateSet = new LPContStateSet();
	
	private Reflections mReflections = new Reflections();

	private LPPolSet mLPPolSet = new LPPolSet();
	private List mBomList = new ArrayList();

	private PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();

	private boolean mBomListFlag = false;
	/**主险保单数据*/
	private LPPolSchema mLPPolSchema = new LPPolSchema();
	
	private LPBnfSet mLPBnfSet = new LPBnfSet();

	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
	
	private double dZTMoney = 0.0; // 退保金额总和

	// 准备校验规则所需要的计算要素
	private BqCalBase mBqCalBase = new BqCalBase();

	/**
	 * 是否是帐户险
	 */
	private String tFlag = "0";

	private String mCurrentDate = PubFun.getCurrentDate();

	private String mCurrentTime = PubFun.getCurrentTime();

	/** 退保金(扣除了手续费的金额) */
	private String mZTMoney;
	private String mCurrency;

	/** 退保费用 */
	private double mZTMoneyFee;

	private boolean EdorQuery = true;// 判断是否查询LPEdorItem信息 add by lizhuo

	/**退保标志，确定是整单退还是只退附加险(0,只退附加险，1 退全部)*/
	private String mWTContFLag = "";

	private boolean EdorCal = false;

	public PEdorCTDetailBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 *            传入的数据对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if ("NOEdorQuery".equals(mOperate))
			EdorQuery = false;
		if ("EdorCal".equals(mOperate))
			EdorCal = true;
		if (!getInputData()) {
			return false;
		}
		if("EDORITEM|UPDATE".equals(mOperate))
		{
			if(!dealBnfData())
			{
				return false;
			}
		}
		else
		{
		// 进行业务处理
			if (!dealData()) {
				return false;
			}
		}

		// 准备往后台的数据
		if (!prepareData()) {
			return false;
		}

		return true;
	}
	
	private boolean dealBnfData()
	{
		if(this.mLPBnfSet==null||this.mLPBnfSet.size()<=0)
		{
			CError.buildErr(this, "传入实际领取数据有误!");
			return false;
		}
		LPBnfSet tLPBnfSet = new LPBnfSet();
		for(int j=1;j<=this.mLPBnfSet.size();j++)
		{
			LPBnfSchema tLPBnfSchema = mLPBnfSet.get(j);
			tLPBnfSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
			tLPBnfSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
			tLPBnfSchema.setPolNo("000000");
			tLPBnfSchema.setContNo(this.mLPEdorItemSchema.getContNo());
			tLPBnfSchema.setInsuredNo(this.mLPEdorItemSchema.getInsuredNo());
			tLPBnfSchema.setBnfType("0");
			tLPBnfSchema.setBnfGrade("1");
			tLPBnfSchema.setBnfNo(j);
			tLPBnfSchema.setMakeDate(mCurrentDate);
			tLPBnfSchema.setMakeTime(mCurrentTime);
			tLPBnfSchema.setModifyDate(mCurrentDate);
			tLPBnfSchema.setModifyTime(mCurrentTime);
			tLPBnfSchema.setOperator(mGlobalInput.Operator);
			tLPBnfSet.add(tLPBnfSchema);
		}
		map.put(tLPBnfSet, "DELETE&INSERT");
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mLPPolSet = (LPPolSet) mInputData.getObjectByObjectName("LPPolSet",
					0);
			mLPBnfSet  = (LPBnfSet)mInputData.getObjectByObjectName("LPBnfSet", 0);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收前台数据失败!");
			return false;
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null
				|| mLPPolSet == null) {
			CError.buildErr(this, "传入数据有误!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {


		// 保存需要退保的数据（保单，被保人，险种）到P表
		if (!savePData()) {
			return false;
		}

		// 计算退保金
		if (!calZTData()) {
			return false;
		}

		// 更新批改项目状态信息
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setModifyDate(mCurrentDate);
		mLPEdorItemSchema.setModifyTime(mCurrentTime);
		map.put(mLPEdorItemSchema, "UPDATE");
		return true;
	}

	/**
	 * 保存需要退保的数据（保单，被保人，险种）到P表
	 * 
	 * @return boolean
	 */
	private boolean savePData() {
		String sEdorReasonCode = mLPEdorItemSchema.getEdorReasonCode();
		String sEdorReason = mLPEdorItemSchema.getEdorReason();
		String sRelationToAppnt = mLPEdorItemSchema.getStandbyFlag2();
		mWTContFLag = mLPEdorItemSchema.getStandbyFlag3();
		String tFeeFlag=mLPEdorItemSchema.getStandbyFlag1();
		if (EdorQuery) {
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
			tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
			tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
			LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
			if (tLPEdorItemDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
				CError.buildErr(this, "查询批改项目信息失败！");
				return false;
			}
			mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
			mLPEdorItemSchema.setEdorReasonCode(sEdorReasonCode);
			mLPEdorItemSchema.setEdorReason(sEdorReason);
			mLPEdorItemSchema.setStandbyFlag2(sRelationToAppnt);
			mLPEdorItemSchema.setStandbyFlag1(tFeeFlag);
			mLPEdorItemSchema.setStandbyFlag3(mWTContFLag);
		}

		LPPolBL tLPPolBL;
		LPEdorItemSchema tmLPEdorItemSchema;
		double dContChgPrem=0,dContChgAmnt=0,dContChgSumPrem=0;
		for (int i = 1; i <= mLPPolSet.size(); i++) {

			tmLPEdorItemSchema = new LPEdorItemSchema();
			tmLPEdorItemSchema.setSchema(mLPEdorItemSchema);
			tmLPEdorItemSchema.setPolNo(mLPPolSet.get(i).getPolNo());
			tLPPolBL = new LPPolBL();
			tLPPolBL.setSchema(mLPPolSet.get(i));
			tLPPolBL.queryLPPol(tmLPEdorItemSchema);
			if (tLPPolBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLPPolBL.mErrors);
				CError.buildErr(this, "查询保单险种失败！");
				return false;
			}
			LPPolSchema tLPPolSchema=tLPPolBL.getSchema();
			tLPPolSchema.setAppFlag("4");
			tLPPolSchema.setModifyDate(mCurrentDate);
			tLPPolSchema.setModifyTime(mCurrentTime);
			tLPPolSchema
					.setOperator(mGlobalInput.Operator);
			mLPPolSet.get(i).setSchema(tLPPolSchema);
			
			// 修改保单状态为终止状态
			if (!changeContState(mLPEdorItemSchema.getContNo(), "Terminate", "1",
					mLPEdorItemSchema.getEdorAppDate(), mLPPolSet.get(i).getPolNo())) {
				return false;
			}
//			if (!changeContState(mLPEdorItemSchema.getContNo(), "Available", "1",
//					mLPEdorItemSchema.getEdorAppDate(), mLPPolSet.get(i).getPolNo())) {
//				return false;
//			}
			dContChgPrem += tLPPolSchema.getPrem();
			dContChgAmnt += tLPPolSchema.getAmnt();
			dContChgSumPrem+=tLPPolSchema.getSumPrem();

		}
		// 删除上次保存过的数据
		if (!delPData(mLPEdorItemSchema.getContNo())) {
			return false;
		}
		map.put(mLPContStateSet, "DELETE&INSERT");
		String sqlContWhere = " contno = '?contno?' and edorno = '?edorno?' and edortype = '?edortype?'";

        SQLwithBindVariables sbv1=new SQLwithBindVariables();
        sbv1.sql("delete from lpinsured where" + sqlContWhere);
        sbv1.put("contno", mLPEdorItemSchema.getContNo());
        sbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
        sbv1.put("edortype", mLPEdorItemSchema.getEdorType());
		map.put(sbv1, "DELETE");
        SQLwithBindVariables sbv2=new SQLwithBindVariables();
        sbv2.sql("delete from lpcont where" + sqlContWhere);
        sbv2.put("contno", mLPEdorItemSchema.getContNo());
        sbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
        sbv2.put("edortype", mLPEdorItemSchema.getEdorType());
		map.put(sbv2, "DELETE");	
		
		map.put(mLPPolSet, "DELETE&INSERT");
		
		Reflections ref = new Reflections();		
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (tLCContDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			CError.buildErr(this, "查询保单信息失败！");
			return false;
		}
		if(!tLCContDB.getInfo())
		{
			CError.buildErr(this, "查询保单信息失败！");
			return false;	
		}

		LPContSchema tLPContSchema=new LPContSchema();
		ref.transFields(tLPContSchema, tLCContDB.getSchema());
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setPrem(tLCContDB.getSchema().getPrem()-dContChgPrem);
		tLPContSchema.setAmnt(tLCContDB.getSchema().getAmnt()-dContChgAmnt);
		tLPContSchema.setSumPrem(tLCContDB.getSchema().getSumPrem()-dContChgSumPrem);
		tLPContSchema.setModifyDate(mCurrentDate);
		tLPContSchema.setModifyTime(mCurrentTime);
		tLPContSchema
				.setOperator(mGlobalInput.Operator);
		if ("1".equals(mWTContFLag)) {
			
			tLPContSchema.setAppFlag("4");
			// 修改保单状态为终止状态
			if (!changeContState(mLPEdorItemSchema.getContNo(), "Terminate", "1",
					mLPEdorItemSchema.getEdorAppDate(), "000000")) {
				return false;
			}
//			if (!changeContState(mLPEdorItemSchema.getContNo(), "Available", "1",
//					mLPEdorItemSchema.getEdorAppDate(), "000000")) {
//				return false;
//			}
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
			LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();

			for (int i = 1; i <= tLCInsuredSet.size(); i++) {
				LCInsuredSchema tLCInsuredSchema = tLCInsuredSet.get(i);
				LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
				ref.transFields(tLPInsuredSchema, tLCInsuredSchema);
				tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mLPInsuredSet.add(tLPInsuredSchema);
			}
			map.put(mLPInsuredSet, "DELETE&INSERT");
		}		
		map.put(tLPContSchema, "DELETE&INSERT");
		return true;
	}

	/**
	 * 删除上次保存过的数据
	 * 
	 * @return boolean
	 */
	private boolean delPData(String tContNo) {

		// 清除P表中上次保存过的数据
		String edorno = mLPEdorItemSchema.getEdorNo();
		String edortype = mLPEdorItemSchema.getEdorType();
		String sqlWhere = " contno = '?contno?' and edorno = '?edorno?' and edortype = '?edortype?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql("delete from lppol where" + sqlWhere);
		sbv1.put("contno", mLPEdorItemSchema.getContNo());
		sbv1.put("edorno", edorno);
		sbv1.put("edortype", edortype);
		map.put(sbv1, "DELETE");
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql("delete from lpcontstate where" + sqlWhere);
		sbv2.put("contno", mLPEdorItemSchema.getContNo());
		sbv2.put("edorno", edorno);
		sbv2.put("edortype", edortype);
		map.put(sbv2, "DELETE");
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql("delete from lpinsureaccfee where" + sqlWhere);
		sbv3.put("contno", mLPEdorItemSchema.getContNo());
		sbv3.put("edorno", edorno);
		sbv3.put("edortype", edortype);
		map.put(sbv3, "DELETE");
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql("delete from lpinsureaccclassfee where" + sqlWhere);
		sbv4.put("contno", mLPEdorItemSchema.getContNo());
		sbv4.put("edorno", edorno);
		sbv4.put("edortype", edortype);
		map.put(sbv4, "DELETE");
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql("delete from lpinsureaccfeetrace where" + sqlWhere);
		sbv5.put("contno", mLPEdorItemSchema.getContNo());
		sbv5.put("edorno", edorno);
		sbv5.put("edortype", edortype);
		map.put(sbv5, "DELETE");
		SQLwithBindVariables sbv6=new SQLwithBindVariables();
		sbv6.sql("delete from LPInsureAcc where" + sqlWhere);
		sbv6.put("contno", mLPEdorItemSchema.getContNo());
		sbv6.put("edorno", edorno);
		sbv6.put("edortype", edortype);
		map.put(sbv6, "DELETE");
		SQLwithBindVariables sbv7=new SQLwithBindVariables();
		sbv7.sql("delete from LPInsureAccClass where" + sqlWhere);
		sbv7.put("contno", mLPEdorItemSchema.getContNo());
		sbv7.put("edorno", edorno);
		sbv7.put("edortype", edortype);
		map.put(sbv7, "DELETE");
		SQLwithBindVariables sbv8=new SQLwithBindVariables();
		sbv8.sql("delete from LPInsureAccTrace where" + sqlWhere);
		sbv8.put("contno", mLPEdorItemSchema.getContNo());
		sbv8.put("edorno", edorno);
		sbv8.put("edortype", edortype);
		map.put(sbv8, "DELETE");
	//	map.put("delete from LPBonusPol where" + sqlWhere, "DELETE");
        SQLwithBindVariables sbv9=new SQLwithBindVariables();
        sbv9.sql(" delete from LJSGetEndorse " + " where EndorsementNo='?edorno?' and FeeOperationType='?edortype?' and contno = '?contno?'");
        sbv9.put("edorno", edorno);
        sbv9.put("edortype", edortype);
        sbv9.put("contno", mLPEdorItemSchema.getContNo());
		map.put(sbv9,
				"DELETE");
		SQLwithBindVariables sbv10=new SQLwithBindVariables();
		sbv10.sql("delete from lpbnf where "+sqlWhere);
		sbv10.put("contno", mLPEdorItemSchema.getContNo());
		sbv10.put("edorno", edorno);
		sbv10.put("edortype", edortype);
		map.put(sbv10,"DELETE");//添加实际领取人删除实际领取人

		return true;
	}

	/**
	 * 计算退保金额
	 * 
	 * @return boolean
	 */
	private boolean calZTData() {

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		EdorCalZT tEdorCalZT;
		VData rVData = new VData();
		Reflections tRef = new Reflections();

//		LOBonusPolSet tLOBonusPolSet = new LOBonusPolSet();
//		LPBonusPolSet tLPBonusPolSet = new LPBonusPolSet();

		LCPolSchema mLCPolSchema = null;
		String Sql = "SELECT * FROM LCPol WHERE ContNo='?ContNo?' and MainPolNo=PolNo order by polno";
		LCPolDB tLCPolDB = new LCPolDB();
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(Sql);
		sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
		LCPolSet rLCPolSet = tLCPolDB.executeQuery(sbv1);
		if (rLCPolSet.size() <= 0 || rLCPolSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoPayBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询主险号时产生错误！";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			mLCPolSchema = rLCPolSet.get(1);
		}
		tRef.transFields(mLPPolSchema, mLCPolSchema);
		mLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		mLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorType());
		

		boolean changCur = false;
		String tCurSql = "Select distinct Currency from lcpol where contno= '?contno?'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(tCurSql);
		sbv2.put("contno", mLPEdorItemSchema.getContNo());
		SSRS curSSRS = mExeSql.execSQL(sbv2);
		if(curSSRS == null ||curSSRS.getMaxRow()==0){
			CError.buildErr(this, "查询币种定义失败");
			return false;
		}
		if(curSSRS.getMaxRow()>1){
			tCurSql = "select codename from ldcode1 where codetype = 'currencyprecision' and code1 = (select sysvarvalue from ldsysvar where sysvar = 'nativeplace')";
			sbv2=new SQLwithBindVariables();
			sbv2.sql(tCurSql);
			mCurrency = mExeSql.getOneValue(sbv2);
			changCur = true;
		}else{
			mCurrency = curSSRS.GetText(1, 1);
		}
		String sCTType = "";
		double dPolZTMoney = 0.0;
		LDExch tLDExch = new LDExch();
		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
		for (int i = 1; i <= mLPPolSet.size(); i++) {
			tLPEdorItemSchema.setSchema(mLPEdorItemSchema);
			tLPEdorItemSchema.setPolNo(mLPPolSet.get(i).getPolNo());
			// 是否是帐户险
			String tCurrency = mLPPolSet.get(i).getCurrency();
			tFlag = BqNameFun.isAccRisk(mLPPolSet.get(i).getRiskCode());
			tEdorCalZT = new EdorCalZT(mGlobalInput);
			dPolZTMoney = tEdorCalZT.calZTData(tLPEdorItemSchema);
			if (dPolZTMoney == -1) {
				mErrors.copyAllErrors(tEdorCalZT.mErrors);
				return false;
			}
			if(changCur){
				dPolZTMoney = tLDExch.toBaseCur(tCurrency, mCurrency, mLPEdorItemSchema.getEdorValiDate(), dPolZTMoney);
				if(dPolZTMoney==-1){
					mErrors.copyAllErrors(tLDExch.mErrors);
					return false;
				}
			}
			sCTType = tEdorCalZT.getCTType();
//			if ("1".equals(sCTType)) {
//				CError.buildErr(this, "保单处于犹豫期内，请做犹豫期退保!");
//				return false;
//			}
			

			dZTMoney += dPolZTMoney; // 已经是扣除了手续费的金额
			LJSGetEndorseSet pLJSGetEndorseSet = tEdorCalZT
					.getLJSGetEndorseSet();			
			rVData.clear();
			rVData = tEdorCalZT.getResult();

					
			if("Y".equals(tFlag))
			{
			   if(!dealInAccRiskCode(rVData,mLPPolSet.get(i),pLJSGetEndorseSet,sCTType))
			   {
					CError.buildErr(this, "处理万能险帐户数据失败!");
					return false;
			   }
		    }
			else
		    {
				if(!dealBonusRiskCode(rVData,mLPPolSet.get(i),pLJSGetEndorseSet))
				{
							CError.buildErr(this, "处理分红险险帐户数据失败!");
							return false;
				}
		    }
//			String tSql = "select * from LOBonusPol b"
//				+" where BonusFlag='0' and polno='"+mLPPolSet.get(i).getPolNo()+"' and AGetDate is null";
//				LOBonusPolSet rLOBonusPolSet = new LOBonusPolSet();
//				LOBonusPolDB tLOBonusPolDB = new LOBonusPolDB();
//				rLOBonusPolSet=tLOBonusPolDB.executeQuery(tSql);
//				if (tLOBonusPolDB.mErrors.needDealError()) {
//					CError.buildErr(this, "保单红利信息查询失败!");
//					return false;
//				}
//			if (rLOBonusPolSet != null && rLOBonusPolSet.size() > 0) {
//						for (int m = 1; m <= rLOBonusPolSet.size(); m++) {
//							LPBonusPolSchema tLPBonusPolSchema = new LPBonusPolSchema();
//							tRef
//									.transFields(tLPBonusPolSchema, rLOBonusPolSet
//											.get(m));
//							tLPBonusPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//							tLPBonusPolSchema.setEdorType(mLPEdorItemSchema
//									.getEdorType());
//							tLPBonusPolSchema.setBonusFlag("1");
//							tLPBonusPolSchema.setAGetDate(mLPEdorItemSchema.getEdorAppDate());
//							tLPBonusPolSchema.setModifyDate(mCurrentDate);
//							tLPBonusPolSchema.setModifyTime(mCurrentTime);
//							tLPBonusPolSchema.setOperator(mGlobalInput.Operator);
//							tLPBonusPolSet.add(tLPBonusPolSchema);
//						}
//						map.put(tLPBonusPolSet, "DELETE&INSERT");
//		}				
		}
		
		//封装保全暂收费数据表，用于判断期合计是否小于零，如果小于零则冲减为0。
		if(!DealSumLJSgetEndorse(mLJSGetEndorseSet,mLPPolSchema))
		{
			CError.buildErr(this, "判断财务合计是否小于零失败");
			return false;
		}
		
		String lcappnt_sql=" select a.appntname,appntsex,  a.appntbirthday,idtype  ,a.idno,'','' ,a.bankcode,a.BankAccNo,a.AccName "
			+"  from lcappnt a where   contno='?contno?' ";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(lcappnt_sql);
		sbv3.put("contno", mLPEdorItemSchema.getContNo());
		LPBnfSchema tLPBnfSchema = new LPBnfSchema();
		ExeSQL tExeSQL = new ExeSQL();
		SSRS lcappnt_ssrs = tExeSQL.execSQL(sbv3);
		if(lcappnt_ssrs.getMaxRow()<=0)
		{
			CError.buildErr(this, "投保人信息获取失败!");
			return false;
		}
		tLPBnfSchema.setName(lcappnt_ssrs.GetText(1, 1));
		tLPBnfSchema.setSex(lcappnt_ssrs.GetText(1, 2));
		tLPBnfSchema.setBirthday(lcappnt_ssrs.GetText(1, 3));
		tLPBnfSchema.setIDType(lcappnt_ssrs.GetText(1, 4));
		tLPBnfSchema.setIDNo(lcappnt_ssrs.GetText(1, 5));
		if(lcappnt_ssrs.GetText(1, 8)==null||lcappnt_ssrs.GetText(1, 8).equals(""))
		{
			tLPBnfSchema.setRemark("1");
		}
		else
		{
			tLPBnfSchema.setRemark("4");
			tLPBnfSchema.setBankCode(lcappnt_ssrs.GetText(1, 8));
			tLPBnfSchema.setBankAccNo(lcappnt_ssrs.GetText(1, 9));
			tLPBnfSchema.setAccName(lcappnt_ssrs.GetText(1, 10));
		}
		
		tLPBnfSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
		tLPBnfSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
		tLPBnfSchema.setPolNo("000000");
		tLPBnfSchema.setContNo(this.mLPEdorItemSchema.getContNo());
		tLPBnfSchema.setInsuredNo(this.mLPEdorItemSchema.getInsuredNo());
		tLPBnfSchema.setBnfType("0");
		tLPBnfSchema.setBnfGrade("1");
		tLPBnfSchema.setBnfNo(1);
		tLPBnfSchema.setMakeDate(mCurrentDate);
		tLPBnfSchema.setMakeTime(mCurrentTime);
		tLPBnfSchema.setModifyDate(mCurrentDate);
		tLPBnfSchema.setModifyTime(mCurrentTime);
		tLPBnfSchema.setOperator(mGlobalInput.Operator);
		
		tLPBnfSchema.setBnfLot(1);

		map.put(tLPBnfSchema,"DELETE&INSERT");
		mTransferData.setNameAndValue("ZTMoney", String.valueOf(dZTMoney)); // 向前台页面返回退保金额
		mLPEdorItemSchema.setGetMoney(dZTMoney);
		mLPEdorItemSchema.setCurrency(mCurrency);
		return true;
	}
	
	/**处理万能险帐户信息
	 * sCTType=1，犹豫期退保
	 * sCTType=2，犹豫期外退保
	 * */
	private boolean dealInAccRiskCode(VData rVData,LPPolSchema tLPPolShema,LJSGetEndorseSet pLJSGetEndorseSet,String sCTType)
	{
		Reflections tRef = new Reflections();
		LCInsureAccSet rLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccClassSet rLCInsureAccClassSet = new LCInsureAccClassSet();
		LCInsureAccTraceSet rLCInsureAccTraceSet = new LCInsureAccTraceSet();

		LPInsureAccSet tLPInsureAccSet = new LPInsureAccSet();
		LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
		LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();
		LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
		LPInsureAccTraceSet tLPInsureAccTraceSet = new LPInsureAccTraceSet();

		
		
		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
		LJSGetEndorseSet isrtLJSGetEndorseSet = new LJSGetEndorseSet();
		isrtLJSGetEndorseSet = distinctWorkNoteFee(pLJSGetEndorseSet,"Y");
		tLJSGetEndorseSet.add(isrtLJSGetEndorseSet);
		
		// 费用信息
		LPInsureAccFeeSet tLPInsureAccFeeSet = new LPInsureAccFeeSet();
		LPInsureAccClassFeeSet tLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
		LPInsureAccFeeTraceSet tLPInsureAccFeeTraceSet = new LPInsureAccFeeTraceSet();
		//犹豫期外退保
		if("2".equals(sCTType))
		{
			rLCInsureAccSet = (LCInsureAccSet) rVData.getObjectByObjectName(
					"LCInsureAccSet", 0);
			rLCInsureAccClassSet = (LCInsureAccClassSet) rVData
					.getObjectByObjectName("LCInsureAccClassSet", 0);
			rLCInsureAccTraceSet = (LCInsureAccTraceSet) rVData
					.getObjectByObjectName("LCInsureAccTraceSet", 0);
			
			if (rLCInsureAccSet != null && rLCInsureAccSet.size() > 0) {
				for (int m = 1; m <= rLCInsureAccSet.size(); m++) {
					tLPInsureAccSchema = new LPInsureAccSchema();
					tRef
							.transFields(tLPInsureAccSchema, rLCInsureAccSet
									.get(m));
					tLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPInsureAccSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					tLPInsureAccSchema.setInsuAccBala(0); // 退保后余额置为0
					tLPInsureAccSchema.setModifyDate(mCurrentDate);
					tLPInsureAccSchema.setModifyTime(mCurrentTime);
					tLPInsureAccSchema.setOperator(mGlobalInput.Operator);
					tLPInsureAccSet.add(tLPInsureAccSchema);
				}
				map.put(tLPInsureAccSet, "DELETE&INSERT");
			}

			if (rLCInsureAccClassSet != null && rLCInsureAccClassSet.size() > 0) {
				for (int n = 1; n <= rLCInsureAccClassSet.size(); n++) {
					tLPInsureAccClassSchema = new LPInsureAccClassSchema();
					tRef.transFields(tLPInsureAccClassSchema,
							rLCInsureAccClassSet.get(n));
					tLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema
							.getEdorNo());
					tLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					tLPInsureAccClassSchema.setInsuAccBala(0); // 退保后余额置为0
					tLPInsureAccClassSchema.setModifyDate(mCurrentDate);
					tLPInsureAccClassSchema.setModifyTime(mCurrentTime);
					tLPInsureAccClassSchema.setOperator(mGlobalInput.Operator);
					tLPInsureAccClassSet.add(tLPInsureAccClassSchema);
				}
				map.put(tLPInsureAccClassSet, "DELETE&INSERT");
			}

			if (rLCInsureAccTraceSet != null && rLCInsureAccTraceSet.size() > 0) {
				double tCTMoney = 0.0;
				for (int k = 1; k <= rLCInsureAccTraceSet.size(); k++) // 累计计算退保金，退保金暂时放在Trace中
				{
					if ("TB".equals(rLCInsureAccTraceSet.get(k).getMoneyType())) {
						tCTMoney = rLCInsureAccTraceSet.get(k).getMoney();
					}

					if ("LX".equals(rLCInsureAccTraceSet.get(k).getMoneyType())) // 插入本次结算产生的保证利息
					{
						LPInsureAccTraceSchema pLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
						tRef.transFields(pLPInsureAccTraceSchema,
								rLCInsureAccTraceSet.get(k));
						pLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema
								.getEdorNo());
						pLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema
								.getEdorType());
						pLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema
								.getEdorValiDate());
						pLPInsureAccTraceSchema.setModifyDate(mCurrentDate);
						pLPInsureAccTraceSchema.setModifyTime(mCurrentTime);
						pLPInsureAccTraceSchema.setMakeDate(mCurrentDate);
						pLPInsureAccTraceSchema.setMakeTime(mCurrentTime);
						pLPInsureAccTraceSchema
								.setOperator(mGlobalInput.Operator);
						tLPInsureAccTraceSet.add(pLPInsureAccTraceSchema);
					}
				}
				// 将退记录拆分为退保金和退保手续费用 add by pst for mingsheng
				double tTBFee = getWorkNoteFee(tCTMoney, tLPPolShema
						.getRiskCode(),tLPPolShema.getCValiDate());
				if (tTBFee == -1) {
					CError.buildErr(this, "退保费用计算失败!");
					return false;
				}
				if(tTBFee>0)
				{
					LPInsureAccTraceSchema rLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
					LPInsureAccTraceSchema cLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
					tRef.transFields(rLPInsureAccTraceSchema, rLCInsureAccTraceSet
							.get(1));
					tRef.transFields(cLPInsureAccTraceSchema, rLCInsureAccTraceSet
							.get(1));
					String tLimit = PubFun.getNoLimit(tLPPolShema
							.getManageCom());
					String serNo1 = PubFun1.CreateMaxNo("SERIALNO", tLimit);

					rLPInsureAccTraceSchema.setSerialNo(serNo1);
					rLPInsureAccTraceSchema
							.setEdorNo(mLPEdorItemSchema.getEdorNo());
					rLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					rLPInsureAccTraceSchema.setMoneyType("TB");
					rLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema
							.getEdorValiDate());
					rLPInsureAccTraceSchema.setMoney(-(tCTMoney - tTBFee));
					rLPInsureAccTraceSchema.setModifyDate(mCurrentDate);
					rLPInsureAccTraceSchema.setModifyTime(mCurrentTime);
					rLPInsureAccTraceSchema.setMakeDate(mCurrentDate);
					rLPInsureAccTraceSchema.setMakeTime(mCurrentTime);
					rLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
					tLPInsureAccTraceSet.add(rLPInsureAccTraceSchema);

					String serNo2 = PubFun1.CreateMaxNo("SERIALNO", tLimit);
					cLPInsureAccTraceSchema.setSerialNo(serNo2);
					cLPInsureAccTraceSchema
							.setEdorNo(mLPEdorItemSchema.getEdorNo());
					cLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					cLPInsureAccTraceSchema.setMoneyType("TBFY");
					cLPInsureAccTraceSchema.setMoney(-tTBFee);
					cLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema
							.getEdorValiDate());
					cLPInsureAccTraceSchema.setModifyDate(mCurrentDate);
					cLPInsureAccTraceSchema.setModifyTime(mCurrentTime);
					cLPInsureAccTraceSchema.setMakeDate(mCurrentDate);
					cLPInsureAccTraceSchema.setMakeTime(mCurrentTime);
					cLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
					tLPInsureAccTraceSet.add(cLPInsureAccTraceSchema);				
				}			
				map.put(tLPInsureAccTraceSet, "DELETE&INSERT");
			}
		}else if("1".equals(sCTType))
		{
            //帐户险犹豫期退保
	        LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
            String strSql = "select * from LCInsureAcc where PolNo ='?PolNo?' and acctype='002'";
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql(strSql);
            sqlbv.put("PolNo", tLPPolShema.getPolNo());
            rLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv);
            if (tLCInsureAccDB.mErrors.needDealError())
            {
                CError.buildErr(this, "保单帐户查询失败!");
                return false;
            }
            if (rLCInsureAccSet == null || rLCInsureAccSet.size() < 1)
            {
                CError.buildErr(this, "保单没有帐户数据!");
                return false;
            }

            //万能险帐户结算结果

            if (rLCInsureAccSet != null && rLCInsureAccSet.size() > 0)
            {
                for (int m = 1; m <= rLCInsureAccSet.size(); m++)
                {
                	LPInsureAccSchema xLPInsureAccSchema = new LPInsureAccSchema();
                    tRef.transFields(xLPInsureAccSchema, rLCInsureAccSet.get(m));
                    xLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                    xLPInsureAccSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                    xLPInsureAccSchema.setInsuAccBala(0);  //退保后余额置为0
                    xLPInsureAccSchema.setModifyDate(mCurrentDate);
                    xLPInsureAccSchema.setModifyTime(mCurrentTime);
                    tLPInsureAccSet.add(xLPInsureAccSchema);
                }
				map.put(tLPInsureAccSet, "DELETE&INSERT");
            }

            LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
            String strSqlAccClass = "select * from LCInsureAccClass where PolNo ='?PolNo?' and acctype='002'";
            SQLwithBindVariables sbv=new SQLwithBindVariables();
            sbv.sql(strSqlAccClass);
            sbv.put("PolNo", tLPPolShema.getPolNo());
            rLCInsureAccClassSet = tLCInsureAccClassDB.executeQuery(sbv);
            if (tLCInsureAccClassDB.mErrors.needDealError())
            {
                CError.buildErr(this, "保单分类帐户查询失败!");
                return false;
            }
            if (rLCInsureAccClassSet == null || rLCInsureAccClassSet.size() < 1)
            {
                CError.buildErr(this, "保单没有分类帐户数据!");
                return false;
            }
            if (rLCInsureAccClassSet != null && rLCInsureAccClassSet.size() > 0)
            {
                for (int n = 1; n <= rLCInsureAccClassSet.size(); n++)
                {
                	LPInsureAccClassSchema xLPInsureAccClassSchema = new LPInsureAccClassSchema();
                    tRef.transFields(xLPInsureAccClassSchema, rLCInsureAccClassSet.get(n));
                    xLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                    xLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                    xLPInsureAccClassSchema.setInsuAccBala(0);  //退保后余额置为0
                    xLPInsureAccClassSchema.setModifyDate(mCurrentDate);
                    xLPInsureAccClassSchema.setModifyTime(mCurrentTime);
                    tLPInsureAccClassSet.add(xLPInsureAccClassSchema);
                }
				map.put(tLPInsureAccClassSet, "DELETE&INSERT");
            }

            LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
            String strSqlAccTrace = "select * from LCInsureAccTrace where PolNo ='?PolNo?'";
            SQLwithBindVariables sbv1=new SQLwithBindVariables();
            sbv1.sql(strSqlAccTrace);
            sbv1.put("PolNo", tLPPolShema.getPolNo());
            rLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sbv1);
            if (tLCInsureAccTraceDB.mErrors.needDealError())
            {
                CError.buildErr(this, "保单帐户轨迹查询失败!");
                return false;
            }
            if (rLCInsureAccTraceSet == null || rLCInsureAccTraceSet.size() < 1)
            {
                CError.buildErr(this, "保单没有帐户轨迹数据!");
                return false;
            }
            if (rLCInsureAccTraceSet != null && rLCInsureAccTraceSet.size() > 0)
            {
                for (int k = 1; k <= rLCInsureAccTraceSet.size(); k++)
                {
                	LPInsureAccTraceSchema xLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
                    String tLimit = PubFun.getNoLimit(rLCInsureAccTraceSet.get(k).getManageCom());
                    String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

                    tRef.transFields(xLPInsureAccTraceSchema, rLCInsureAccTraceSet.get(k));
                    xLPInsureAccTraceSchema.setSerialNo(serNo);
                    
                    xLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                    if("BF".equals(rLCInsureAccTraceSet.get(k).getMoneyType()))
                    {
                    	xLPInsureAccTraceSchema.setMoneyType("WT"); //应MS要求修改此类型,单独对ＢＦ类型加以区分
                    }else
                    {
                    	xLPInsureAccTraceSchema.setMoneyType(rLCInsureAccTraceSet.get(k).getMoneyType()); //应MS要求修改此类型
                    }

                    xLPInsureAccTraceSchema.setMoney(-rLCInsureAccTraceSet.get(k).getMoney());
                    xLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                    xLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
                    xLPInsureAccTraceSchema.setModifyDate(mCurrentDate);
                    xLPInsureAccTraceSchema.setModifyTime(mCurrentTime);
                    xLPInsureAccTraceSchema.setMakeDate(mCurrentDate);
                    xLPInsureAccTraceSchema.setMakeTime(mCurrentTime);
                    xLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
                    tLPInsureAccTraceSet.add(xLPInsureAccTraceSchema);
                }
				map.put(tLPInsureAccTraceSet, "DELETE&INSERT");
            }
		}			
		//费用信息 acc
		// modify by sunsx 2010-03-24 除犹豫期退保外的所有退保不再冲减管理费
		if(mLPEdorItemSchema.getEdorType().equals("WT")){
			LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
			LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
			String strSQL = "select * from LCInsureAccFee where PolNo ='?PolNo?' and acctype='002'";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(strSQL);
			sbv2.put("PolNo", tLPPolShema.getPolNo());
			tLCInsureAccFeeSet = tLCInsureAccFeeDB.executeQuery(sbv2);
			if (tLCInsureAccFeeDB.mErrors.needDealError()) {
				CError.buildErr(this, "保单帐户费用信息查询失败!");
				return false;
			}
			if (tLCInsureAccFeeSet != null && tLCInsureAccFeeSet.size() > 0) {
				for (int k = 1; k <= tLCInsureAccFeeSet.size(); k++) {
					LPInsureAccFeeSchema tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
					tRef.transFields(tLPInsureAccFeeSchema, tLCInsureAccFeeSet
							.get(k));
					tLPInsureAccFeeSchema.setEdorNo(mLPEdorItemSchema
							.getEdorNo());
					tLPInsureAccFeeSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					tLPInsureAccFeeSchema.setFee(0); //退保后费用置为0
					tLPInsureAccFeeSchema.setBalaDate(mLPEdorItemSchema
							.getEdorValiDate());
					tLPInsureAccFeeSchema.setModifyDate(mCurrentDate);
					tLPInsureAccFeeSchema.setModifyTime(mCurrentTime);
					tLPInsureAccFeeSet.add(tLPInsureAccFeeSchema);
				}
				map.put(tLPInsureAccFeeSet, "DELETE&INSERT");
			}

			//class
			LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
			LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
			String sqlStr = "select * from LCInsureAccClassFee where PolNo ='?PolNo?' and acctype='002'";
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(sqlStr);
			sbv3.put("PolNo", tLPPolShema.getPolNo());
			tLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB
			.executeQuery(sbv3);
			if (tLCInsureAccClassFeeDB.mErrors.needDealError()) {
				CError.buildErr(this, "保单帐户费用分类信息查询失败!");
				return false;
			}

			if (tLCInsureAccClassFeeSet != null
					&& tLCInsureAccClassFeeSet.size() > 0) {
				for (int n = 1; n <= tLCInsureAccClassFeeSet.size(); n++) {
					LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
					tRef.transFields(tLPInsureAccClassFeeSchema,
							tLCInsureAccClassFeeSet.get(n));
					tLPInsureAccClassFeeSchema.setEdorNo(mLPEdorItemSchema
							.getEdorNo());
					tLPInsureAccClassFeeSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					tLPInsureAccClassFeeSchema.setFee(0); //退保后余额置为0
					tLPInsureAccClassFeeSchema.setBalaDate(mLPEdorItemSchema
							.getEdorAppDate());
					tLPInsureAccClassFeeSchema.setModifyDate(mCurrentDate);
					tLPInsureAccClassFeeSchema.setModifyTime(mCurrentTime);
					tLPInsureAccClassFeeSet.add(tLPInsureAccClassFeeSchema);
				}
				map.put(tLPInsureAccClassFeeSet, "DELETE&INSERT");
			}

			//Trace
			LCInsureAccFeeTraceDB tLCInsureAccFeeTraceDB = new LCInsureAccFeeTraceDB();
			LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
			String sqlSt = "select * from LCInsureAccFeeTrace where PolNo ='?PolNo?'";
			SQLwithBindVariables sbv4=new SQLwithBindVariables();
			sbv4.sql(sqlSt);
			sbv4.put("PolNo", tLPPolShema.getPolNo());
			tLCInsureAccFeeTraceSet = tLCInsureAccFeeTraceDB
			.executeQuery(sbv4);
			if (tLCInsureAccFeeTraceDB.mErrors.needDealError()) {
				CError.buildErr(this, "保单帐户费用分类信息查询失败!");
				return false;
			}
			if (tLCInsureAccFeeTraceSet != null
					&& tLCInsureAccFeeTraceSet.size() > 0) {
				for (int l = 1; l <= tLCInsureAccFeeTraceSet.size(); l++) {
					LPInsureAccFeeTraceSchema tLPInsureAccFeeTraceSchema = new LPInsureAccFeeTraceSchema();

					String tLimit = PubFun.getNoLimit(tLCInsureAccFeeTraceSet.get(l).getManageCom());
					String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
					tRef.transFields(tLPInsureAccFeeTraceSchema,
							tLCInsureAccFeeTraceSet.get(l));
					tLPInsureAccFeeTraceSchema.setSerialNo(serNo);
					tLPInsureAccFeeTraceSchema.setEdorNo(mLPEdorItemSchema
							.getEdorNo());
					tLPInsureAccFeeTraceSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					tLPInsureAccFeeTraceSchema.setFee(-tLCInsureAccFeeTraceSet
							.get(l).getFee());
					tLPInsureAccFeeTraceSchema.setPayDate(mLPEdorItemSchema
							.getEdorAppDate());
					tLPInsureAccFeeTraceSchema
					.setMoneyType(tLCInsureAccFeeTraceSet.get(l)
							.getMoneyType()); //应MS要求修改此类型为新的类型
					tLPInsureAccFeeTraceSchema.setModifyDate(mCurrentDate);
					tLPInsureAccFeeTraceSchema.setModifyTime(mCurrentTime);
					tLPInsureAccFeeTraceSchema.setMakeDate(mCurrentDate);
					tLPInsureAccFeeTraceSchema.setMakeTime(mCurrentTime);
					tLPInsureAccFeeTraceSchema
					.setOperator(mGlobalInput.Operator);
					tLPInsureAccFeeTraceSet.add(tLPInsureAccFeeTraceSchema);
				}
				//add zhangyingfeng 2016-08-03
				//营改增 价税分离计算器
				TaxCalculator.calBySchemaSet(tLPInsureAccFeeTraceSet);
				//end zhangyingfeng 2016-08-03
				map.put(tLPInsureAccFeeTraceSet, "DELETE&INSERT");
			}
		}
		//营改增 add zhangyingfeng 2016-07-07
		//税价分离 计算器
		TaxCalculator.calBySchemaSet(tLJSGetEndorseSet);
		//end zhangyingfeng 2016-07-07
		map.put(tLJSGetEndorseSet, "DELETE&INSERT");
		mLJSGetEndorseSet.add(tLJSGetEndorseSet);

		return true;
}
	
	/**处理分红险等帐户信息*/
	private boolean dealBonusRiskCode(VData rVData,LPPolSchema tLPPolShema,	LJSGetEndorseSet tLJSGetEndorseSet)
	{
		Reflections tRef = new Reflections();
		LCInsureAccSet rLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccClassSet rLCInsureAccClassSet = new LCInsureAccClassSet();
		LCInsureAccTraceSet rLCInsureAccTraceSet = new LCInsureAccTraceSet();

		LPInsureAccSet tLPInsureAccSet = new LPInsureAccSet();
		LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
		LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();
		LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
		LPInsureAccTraceSet tLPInsureAccTraceSet = new LPInsureAccTraceSet();
		
		rLCInsureAccSet = (LCInsureAccSet) rVData.getObjectByObjectName(
				"LCInsureAccSet", 0);
		rLCInsureAccClassSet = (LCInsureAccClassSet) rVData
				.getObjectByObjectName("LCInsureAccClassSet", 0);
		rLCInsureAccTraceSet = (LCInsureAccTraceSet) rVData
				.getObjectByObjectName("LCInsureAccTraceSet", 0);
		
		if (rLCInsureAccSet != null && rLCInsureAccSet.size() > 0) {
			for (int m = 1; m <= rLCInsureAccSet.size(); m++) {
				tLPInsureAccSchema = new LPInsureAccSchema();
				tRef
						.transFields(tLPInsureAccSchema, rLCInsureAccSet
								.get(m));
				tLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsureAccSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				tLPInsureAccSchema.setInsuAccBala(0); // 退保后余额置为0
				tLPInsureAccSchema.setModifyDate(mCurrentDate);
				tLPInsureAccSchema.setModifyTime(mCurrentTime);
				tLPInsureAccSchema.setOperator(mGlobalInput.Operator);
				tLPInsureAccSet.add(tLPInsureAccSchema);
				
				//产生付的trace记录，作为领取记录
				LPInsureAccTraceSchema pLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
				String tLimit = PubFun.getNoLimit(rLCInsureAccSet.get(m)
						.getManageCom());
				String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
				//用LCInsureAccTraceSchmea记录复制到LPInsureAccTraceSchema
				for(int i=1;i<=rLCInsureAccTraceSet.size();i++)
				{
					LCInsureAccTraceSchema copy_LCInsureAccTraceSchmea = rLCInsureAccTraceSet.get(i);
					if(copy_LCInsureAccTraceSchmea.getInsuAccNo().equals(tLPInsureAccSchema.getInsuAccNo()))
					{
						tRef.transFields(pLPInsureAccTraceSchema, copy_LCInsureAccTraceSchmea);
					}
				}

				pLPInsureAccTraceSchema
						.setEdorNo(mLPEdorItemSchema.getEdorNo());
				pLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				pLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema
						.getEdorValiDate());
				pLPInsureAccTraceSchema.setSerialNo(serNo);
				pLPInsureAccTraceSchema.setMoney(-rLCInsureAccSet.get(m).getInsuAccBala());
				if (pLPInsureAccTraceSchema.getInsuAccNo().equals("000001")
						|| pLPInsureAccTraceSchema.getInsuAccNo().equals(
								"000007")
						|| pLPInsureAccTraceSchema.getInsuAccNo().equals(
								"000008"))
				{
					String trace_finType = BqCalBL.getFinType_HL_SC("HL", pLPInsureAccTraceSchema.getInsuAccNo(), "LQ");
					if(trace_finType.equals(""))
					{
						CError.buildErr(this, "红利领取时的财务类型获取失败！");
						return false;
					}
					pLPInsureAccTraceSchema.setMoneyType(trace_finType);
				}
				else if(pLPInsureAccTraceSchema.getInsuAccNo().equals("000005"))
				{
					String trace_finType = BqCalBL.getFinType_HL_SC("SC", pLPInsureAccTraceSchema.getInsuAccNo(), "LQ");
					if(trace_finType.equals(""))
					{
						CError.buildErr(this, "生存金领取时的财务类型获取失败！");
						return false;
					}
					pLPInsureAccTraceSchema.setMoneyType(trace_finType);
				}
				else
				{
					CError.buildErr(this, "出现红利和年金以外的帐户，可能是问题数据！");
					return false;
				}
				pLPInsureAccTraceSchema.setPayDate(this.mLPEdorItemSchema.getEdorValiDate());
				pLPInsureAccTraceSchema.setModifyDate(mCurrentDate);
				pLPInsureAccTraceSchema.setModifyTime(mCurrentTime);
				pLPInsureAccTraceSchema.setMakeDate(mCurrentDate);
				pLPInsureAccTraceSchema.setMakeTime("00:00:00");
				pLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
				map.put(pLPInsureAccTraceSchema, "DELETE&INSERT");
			}
			map.put(tLPInsureAccSet, "DELETE&INSERT");
		}

		if (rLCInsureAccClassSet != null && rLCInsureAccClassSet.size() > 0) {
			for (int n = 1; n <= rLCInsureAccClassSet.size(); n++) {
				tLPInsureAccClassSchema = new LPInsureAccClassSchema();
				tRef.transFields(tLPInsureAccClassSchema,
						rLCInsureAccClassSet.get(n));
				tLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema
						.getEdorNo());
				tLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				tLPInsureAccClassSchema.setInsuAccBala(0); // 退保后余额置为0
				tLPInsureAccClassSchema.setModifyDate(mCurrentDate);
				tLPInsureAccClassSchema.setModifyTime(mCurrentTime);
				tLPInsureAccClassSchema.setOperator(mGlobalInput.Operator);
				tLPInsureAccClassSet.add(tLPInsureAccClassSchema); 
			}
			map.put(tLPInsureAccClassSet, "DELETE&INSERT");
		}

		if (rLCInsureAccTraceSet != null && rLCInsureAccTraceSet.size() > 0) {
			for (int k = 1; k <= rLCInsureAccTraceSet.size(); k++) 
			{
				LPInsureAccTraceSchema pLPInsureAccTraceSchema = new LPInsureAccTraceSchema();

				tRef.transFields(pLPInsureAccTraceSchema, rLCInsureAccTraceSet
						.get(k));
				String tLimit = PubFun.getNoLimit(rLCInsureAccTraceSet.get(k)
						.getManageCom());
				String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
				pLPInsureAccTraceSchema
						.setEdorNo(mLPEdorItemSchema.getEdorNo());
				pLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				pLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema
						.getEdorValiDate());
				pLPInsureAccTraceSchema.setSerialNo(serNo);
				if (pLPInsureAccTraceSchema.getInsuAccNo().equals("000001")
						|| pLPInsureAccTraceSchema.getInsuAccNo().equals(
								"000007")
						|| pLPInsureAccTraceSchema.getInsuAccNo().equals(
								"000008")) {
					String trace_finType = BqCalBL.getFinType_HL_SC("HL", pLPInsureAccTraceSchema.getInsuAccNo(), "LX");
					if(trace_finType.equals(""))
					{
						CError.buildErr(this, "红利领取时的财务类型获取失败！");
						return false;
					}
					pLPInsureAccTraceSchema.setMoneyType(trace_finType);
				} else if (pLPInsureAccTraceSchema.getInsuAccNo().equals(
						"000005")) {
					String trace_finType = BqCalBL.getFinType_HL_SC("SC", pLPInsureAccTraceSchema.getInsuAccNo(), "LX");
					if(trace_finType.equals(""))
					{
						CError.buildErr(this, "生存金领取时的财务类型获取失败！");
						return false;
					}
					pLPInsureAccTraceSchema.setMoneyType(trace_finType);
				}
				else
				{
					CError.buildErr(this, "出现红利和年金以外的帐户，可能是问题数据！");
					return false;
				}
//				pLPInsureAccTraceSchema.setMoney(-rLCInsureAccTraceSet.get(k)
//						.getMoney());
				pLPInsureAccTraceSchema.setModifyDate(mCurrentDate);
				pLPInsureAccTraceSchema.setModifyTime(mCurrentTime);
				pLPInsureAccTraceSchema.setMakeDate(mCurrentDate);
//				pLPInsureAccTraceSchema.setMakeTime(mCurrentTime);
				pLPInsureAccTraceSchema.setMakeTime("00:00:00");
				pLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
				tLPInsureAccTraceSet.add(pLPInsureAccTraceSchema);
			}
			map.put(tLPInsureAccTraceSet, "DELETE&INSERT");
		}		
		//营改增 add zhangyingfeng 2016-07-07
		//税价分离 计算器
		TaxCalculator.calBySchemaSet(tLJSGetEndorseSet);
		//end zhangyingfeng 2016-07-07
		map.put(tLJSGetEndorseSet, "DELETE&INSERT");	
		mLJSGetEndorseSet.add(tLJSGetEndorseSet);
		return true;
}	
	/**
	 * 针对万能险之出去手续费记录
	 * 
	 * @return LJSGetEndorseSet
	 */
	private LJSGetEndorseSet distinctWorkNoteFee(
			LJSGetEndorseSet pLJSGetEndorseSet, String rFlag) {
		LJSGetEndorseSet rLJSGetEndorseSet = new LJSGetEndorseSet();
		if (pLJSGetEndorseSet == null || pLJSGetEndorseSet.size() < 1
				|| EdorCal) // 如果是查询则返回结果
		{
			return pLJSGetEndorseSet;
		}
		for (int i = 1; i <= pLJSGetEndorseSet.size(); i++) {
			if (pLJSGetEndorseSet.get(i).getSubFeeOperationType().equals(
					BqCode.Pay_TBFee)) {
				mZTMoneyFee += pLJSGetEndorseSet.get(i).getGetMoney();
			}
		}
		for (int i = 1; i <= pLJSGetEndorseSet.size(); i++) {
			if (pLJSGetEndorseSet.get(i).getSubFeeOperationType().equals(
					BqCode.Pay_TBFee)) {
				if ("Y".equals(rFlag)) // 如果是帐户险
				{
					continue;
				}
			} else if (pLJSGetEndorseSet.get(i).getSubFeeOperationType()
					.equals(BqCode.Get_BaseCashValue)) {
				if ("Y".equals(rFlag))// 如果是帐户险
				{
					double tGetMoney = pLJSGetEndorseSet.get(i).getGetMoney();
					pLJSGetEndorseSet.get(i).setGetMoney(
							tGetMoney - mZTMoneyFee);
					rLJSGetEndorseSet.add(pLJSGetEndorseSet.get(i));
				} else {
					rLJSGetEndorseSet.add(pLJSGetEndorseSet.get(i));
				}
			} else {
				rLJSGetEndorseSet.add(pLJSGetEndorseSet.get(i));
			}
		}

		return rLJSGetEndorseSet;
	}

	/**
	 * 判断是否包含主险
	 * 
	 * @param pLPPolSet
	 * @return boolean
	 */
	private boolean hasMainPol(LPPolSet pLPPolSet) {
		for (int i = 1; i <= pLPPolSet.size(); i++) {
			if (pLPPolSet.get(i).getPolNo().equals(
					pLPPolSet.get(i).getMainPolNo())) {
				return true; // 有主险
			}
		}
		return false;
	}

	// 取得手续费
	private double getWorkNoteFee(double tGetMoney, String tRiskCode,
			String tCValiDate) {
		// 已过保单年度
		ExeSQL tExeSQL = new ExeSQL();
		double mWorkNoteFee;
		int tInterval = PubFun.calInterval(tCValiDate, mLPEdorItemSchema
				.getEdorValiDate(), "Y");

		Calculator tCalculator = new Calculator();
		// 查询算法编码
		String tSQL = "select calcode from lmedorcal where caltype='GetFee' and edortype='CT'"
				+ " and dutycode='000000' and riskcode='?tRiskCode?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("tRiskCode", tRiskCode);
		String tCalCode = tExeSQL.getOneValue(sqlbv);
		if ("".equals(tCalCode) || tCalCode == null) {
			return 0;
		}
		BqCalBase mBqCalBase=new BqCalBase();
		mBqCalBase.setInterval(tInterval);
		mBqCalBase.setGetMoney(tGetMoney);
		if (!prepareBOMList(mBqCalBase)) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return -1;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		tCalculator.setCalCode(tCalCode);
		// 本次结算日期对应的保单年度
		tCalculator.addBasicFactor("Interval", String.valueOf(tInterval));
		tCalculator.addBasicFactor("GetMoney", String.valueOf(tGetMoney));
		String tMoney = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "领取费用计算失败!");
			return -1;
		} else {

		}
		mWorkNoteFee = Double.parseDouble(tMoney);

		return mWorkNoteFee;

	}
	
	/**
	 * 为全局变量mBomList赋值，如果已经赋值过，则不再赋值
	 * 
	 * @return
	 */
	private boolean prepareBOMList(BqCalBase mBqCalBase) {
		try {
			if (!this.mBomListFlag) {
				this.mPrepareBOMBQEdorBL.setBqCalBase(mBqCalBase);
				this.mBomList = this.mPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
				this.mBomListFlag = true;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.mBomListFlag = false;
			return false;
		}
	}

	/**
	 * 改变保单的状态（注：是保单级状态）
	 * 
	 * @param tContNo
	 * @param tStateType
	 * @param tState
	 * @param tNewStateDate
	 * @return boolean true--成功，false--失败。结果放在mLPContStateSet变量中（累计）
	 */
	private boolean changeContState(String tContNo, String tStateType,
			String tState, String tNewStateDate, String tPolNo) {
		try {
			// 当前日期时间
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			LCContStateSchema tLCContStateSchema = null;
			LPContStateSchema tLPContStateSchema = null;
			// 先查询当前状态是否是要改变的状态，如果是，则保持
			String tSql = "SELECT *" + " FROM LCContState" + " WHERE ContNo='?tContNo?'" + " and PolNo='?tPolNo?'"
					+ " and StateType='?tStateType?'" + " and State='?tState?'" + " and EndDate is null";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tContNo", tContNo);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tStateType", tStateType);
			sqlbv.put("tState", tState);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				// 现在的状态就是要改变后的状态，所以，保持
				return true;
			}

			// 按张容所说，如果新旧状态主键重复（指在C表中重复），则直接用新状态把旧状态替掉。2005-09-21日修改。
			LCContStateDB tLCContStateDB = new LCContStateDB();
			tLCContStateDB.setContNo(tContNo);
			tLCContStateDB.setPolNo(tPolNo);
			tLCContStateDB.setStateType(tStateType);
			tLCContStateDB.setStartDate(tNewStateDate);
			if (!tLCContStateDB.getInfo()) {
				// 查询现在状态，将此状态结束
				tSql = "SELECT *" + " FROM LCContState" + " WHERE ContNo='?tContNo?'" + " and StateType='?tStateType?'"
						+ " and PolNo='?tPolNo?'"
						+ " and EndDate is null";
				SQLwithBindVariables sbv=new SQLwithBindVariables();
				sbv.sql(tSql);
				sbv.put("tContNo", tContNo);
				sbv.put("tStateType", tStateType);
				sbv.put("tPolNo", tPolNo);
				tLCContStateDB = new LCContStateDB();
				LCContStateSet tLCContStateSet = new LCContStateSet();
				tLCContStateSet = tLCContStateDB.executeQuery(sbv);
				if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
					tLCContStateSchema = new LCContStateSchema();
					tLCContStateSchema = tLCContStateSet.get(1);
					tLCContStateSchema.setEndDate(PubFun.calDate(tNewStateDate,
							-1, "D", null)); // 状态在前一天结束
					tLCContStateSchema.setOperator(mGlobalInput.Operator);
					tLCContStateSchema.setModifyDate(tCurrentDate);
					tLCContStateSchema.setModifyTime(tCurrentTime);
					tLPContStateSchema = new LPContStateSchema();
					this.mReflections.transFields(tLPContStateSchema,
							tLCContStateSchema);
					tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPContStateSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					mLPContStateSet.add(tLPContStateSchema);
				}
			}

			// 新状态信息
			tLPContStateSchema = new LPContStateSchema();
			tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPContStateSchema.setContNo(tContNo);
			tLPContStateSchema.setInsuredNo("000000");
			tLPContStateSchema.setPolNo(tPolNo);
			tLPContStateSchema.setStateType(tStateType);
			tLPContStateSchema.setState(tState);
			if ("CT".equals(mLPEdorItemSchema.getEdorType()) && tStateType.equals("Terminate")) {
				tLPContStateSchema.setStateReason("02");
			}
			if ("WT".equals(mLPEdorItemSchema.getEdorType()) && tStateType.equals("Terminate")) {
				tLPContStateSchema.setStateReason("06");
			}
			if ("XT".equals(mLPEdorItemSchema.getEdorType()) && tStateType.equals("Terminate")) {
				tLPContStateSchema.setStateReason("05");
			}
			tLPContStateSchema.setStartDate(tNewStateDate);
			tLPContStateSchema.setOperator(mGlobalInput.Operator);
			tLPContStateSchema.setMakeDate(tCurrentDate);
			tLPContStateSchema.setMakeTime(tCurrentTime);
			tLPContStateSchema.setModifyDate(tCurrentDate);
			tLPContStateSchema.setModifyTime(tCurrentTime);
			mLPContStateSet.add(tLPContStateSchema);
			return true;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PEdorLNDetailBL";
			tError.functionName = "changeContState";
			tError.errorMessage = "修改保单状态时产生错误！保单号：" + tContNo;
			this.mErrors.addOneError(tError);
			return false;
		}
	}
/***/	
private boolean DealSumLJSgetEndorse(LJSGetEndorseSet tLJSGetEndorseSet,LPPolSchema tLPPolSchema)
{
	double tGetMoney=0,tPayMoney=0;
	for(int k=1;k<=tLJSGetEndorseSet.size();k++)
	{
		//0 ---表示需要补费
		//1 ---表示需要退费 
		if("1".equals(tLJSGetEndorseSet.get(k).getGetFlag()))
		{
			tGetMoney+=tLJSGetEndorseSet.get(k).getGetMoney();
		}
		if("0".equals(tLJSGetEndorseSet.get(k).getGetFlag()))
		{
			tPayMoney+=tLJSGetEndorseSet.get(k).getGetMoney();
		}
	}
	double tIntvMoney=tGetMoney-tPayMoney;
	//退保合计为零
	if(tIntvMoney<0)
	{
		BqCalBL tBqCalBL = new BqCalBL();
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema =tBqCalBL.initLJSGetEndorse(mLPEdorItemSchema, tLPPolSchema, "000000", 
				"000000", BqCode.Get_BDMoney, "BD", -tIntvMoney, mGlobalInput);
		tLJSGetEndorseSchema.setGetFlag("1");
		dZTMoney-=tIntvMoney;
		//营改增 add zhangyingfeng 2016-07-07
		//税价分离 计算器
		TaxCalculator.calBySchema(tLJSGetEndorseSchema);
		//end zhangyingfeng 2016-07-07
		map.put(tLJSGetEndorseSchema, "DELETE&INSERT");	
	}
	return true;
}
	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.add(map);
		mResult.add(mTransferData);
		mResult.add(mBqCalBase);
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

}
