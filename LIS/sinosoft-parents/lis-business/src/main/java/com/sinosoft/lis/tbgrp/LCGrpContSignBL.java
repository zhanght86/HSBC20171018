/*
 * @(#)LCGrpContSignBL.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import com.sinosoft.lis.cbcheckgrp.LCManageFeeBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpFeeDB;
import com.sinosoft.lis.db.LCGrpIssuePolDB;
import com.sinosoft.lis.db.LCGrpPENoticeDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.MultThreadErrorDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCOutManagePayDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LMRiskFeeDB;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.ManageFeeCalBL;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LCGrpFeeSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCOutManagePaySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LDSysTraceSchema;
import com.sinosoft.lis.schema.LJAGetOtherSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAPayGrpSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LCGrpFeeSet;
import com.sinosoft.lis.vschema.LCGrpIssuePolSet;
import com.sinosoft.lis.vschema.LCGrpPENoticeSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCOutManagePaySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.MultThreadErrorSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAPayGrpSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.vschema.LMRiskFeeSet;
import com.sinosoft.lis.vschema.LMRiskSet;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 承保签单业务逻辑处理类-团体签单
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @version 6.0
 */
public class LCGrpContSignBL {
private static Logger logger = Logger.getLogger(LCGrpContSignBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private MMap mFinFeeMap = new MMap(); // 处理团但财务的提交容器
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	private LCGrpContSet tLCGrpContSet = new LCGrpContSet();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
	private HashMap mLJTempFeeMap = new HashMap();

	// 全局需要用到的计算日期
	private Date mFirstPayDate = null;
	private Date maxPayDate;
	private Date maxEnterAccDate;
	private Date maxPayEndDate;
	private String mCurrentDate;
	private String mCurrentTime;
	private String outpayflag = "0";
	private HashMap paytoDateMap = new HashMap();
	// 保费溢额
	private double mGrpContDiff = 0.00;
	private final int DB_ERROR = 100;
	double sumGrpContPrem = 0.000;
	double sumGrpContStandPrem = 0.000;
	HashMap PolNoMap = new HashMap();
	// 团单财务处理准备数据
	private String tDate = PubFun.getCurrentDate();
	private String appName = "";
	private String tEnterAccDate = "";
	private String tAgentCode = "";
	private String tAgentGroup = "";
	private String tOperator = "";
	private double tSumMoney = 0; // 暂收保费和
	private double tempSumMoney = 0; // 暂收保费和
	private String tPayNo = "";
	private String tAgentType = "";
	private String tAppNo = "";
	private String tApprovecode = "";
	private String tApprDate = "";
	private double tDiff = 0;
	private FDate fDate = new FDate();
	private double ttMoney = 0;
	private String mPayType = "";
	private String mPayTypeleft = "";
	private String mFinFeeType = "";
	private LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet(); // 减少访问数据库的次数
	private Vector mTaskWaitList = new Vector();

	public LCGrpContSignBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!this.getInputData(cInputData)) {
			return false;
		}

		// 填充要签单的数据集
		if (!fillGrpCont()) {
			return false;
		}

		// 签单

		if (!signGrpCont()) {

			return false;
		} else {
			// 如果签单成功，写一个方法，将手续费进行处理
			LCManageFeeBL tLCManageFeeBL = new LCManageFeeBL();

			if (!tLCManageFeeBL.submitData(mInputData, "UPDATE")) {
				logger.debug("处理手续费失败！");
			}
			// mLCGrpContSet 使用的参数，只有一个集体合同号，一次只签一个保单
		}

		return true;
	}

	/**
	 * 团体保单签单
	 * 
	 * @return boolean
	 */
	private boolean signGrpCont() {
		LCGrpContSchema signSchema = null;
		LCContSet tSignLCContSet = null;
		String newGrpContNo = null;

		signSchema = mLCGrpContSet.get(1);
		if (signSchema.getState() != null && signSchema.getState().equals("6")) {
			CError.buildErr(this, "团单合同(" + signSchema.getGrpContNo()
					+ ")正在签单!");
			return false;

		}
		// 将单子置为签单状态
		MMap tempMMap = new MMap();
		tempMMap.put("update lcgrpcont set state='6' where grpcontno='"
				+ signSchema.getGrpContNo() + "'", "UPDATE");
		PubSubmit tempSubmit = new PubSubmit();
		VData temVData = new VData();
		temVData.add(tempMMap);
		if (!tempSubmit.submitData(temVData, null)) {
			CError.buildErr(this, "保单挂起失败！");
			return false;
		}

		// 增加对管理费设置的校验，如果没有设置，不能进行签单
		if (!checkGrpFee(signSchema)) {
			CError.buildErr(this, "团单合同(" + signSchema.getGrpContNo()
					+ ")管理费没有定义!");
			return false;

		}

		// 财务校验
		int cpFee = CheckTempFee(signSchema);
		if (cpFee == this.DB_ERROR) {
			CError.buildErr(this, "团单合同(" + signSchema.getGrpContNo()
					+ ")交费信息查找错误!");
			return false;
		}
		if (cpFee == -2) {
			CError.buildErr(this, "团单合同(" + signSchema.getGrpContNo()
					+ ")下存在产品定义错误!");
			return false;

		}
		if (cpFee == -3) {
			CError.buildErr(this, "团单合同(" + signSchema.getGrpContNo()
					+ ")公共账户保费不足抵缴管理费！");
			return false;

		}
		if (cpFee == -4) {
			CError.buildErr(this, "团单合同(" + signSchema.getGrpContNo()
					+ ")法人账户保费不足抵缴管理费！");
			return false;

		}

		if (cpFee < 0) {
			CError.buildErr(this, "团单合同(" + signSchema.getGrpContNo()
					+ ")交费不足或还有未到帐的交费!");
			return false;
		}

		
		if(!checkCurrency(signSchema)){
			CError.buildErr(this, "团单合同(" + signSchema.getGrpContNo()
					+ ")交费币种不一致或者金额不足!");
			return false;
		}
		if (signSchema.getGrpContNo().equals(signSchema.getPrtNo())) {
			// 未换号，则先换集体保单号
			String sql = "select grppolno,GrpProposalNo from lcgrppol where grpcontno='"
					+ signSchema.getGrpContNo() + "'";
			ExeSQL exesql = new ExeSQL();
			SSRS ssrs = exesql.execSQL(sql);
			if (ssrs == null) {
				CError.buildErr(this, "团单下没有相应的集体保单");
				return false;
			}
			String limitNo = PubFun.getNoLimit(signSchema.getManageCom());
			newGrpContNo = PubFun1.CreateMaxNo("GRPCONTNO", limitNo);
			MMap tmpMap = new MMap();
			tmpMap.put(
					"update lcgrppol set peoples3=peoples2 where grpcontno='"
							+ signSchema.getPrtNo() + "'", "UPDATE"); // add
																		// by
																		// wanglei,为了188计价，用peoples3记录承保时的人数，永不修改,要赶在lcgrppol中的grpcontno被修改为保单号之前
			// 目前只有保单号需要修改。
			tmpMap.add(prepareOtherUpdateGrpCont(newGrpContNo, signSchema
					.getPrtNo()));

			// 增加拥金描述修改

			// tmpMap.put("update lcgrpbonus set GrpContNo='" + newGrpContNo +
			// "' where grpcontno='" + signSchema.getPrtNo() + "'",
			// "UPDATE");

			tmpMap.put("update LACommisionDetail set GrpContNo='"
					+ newGrpContNo + "',ModifyDate='" + PubFun.getCurrentDate()
					+ "',ModifyTime='" + PubFun.getCurrentTime()
					+ "' where grpcontno='" + signSchema.getPrtNo() + "'",
					"UPDATE");
			// tmpMap.put("update LLDutyCtrl set GrpContNo='" + newGrpContNo +
			// "',ContNo='" + newGrpContNo + "' where grpcontno='" +
			// signSchema.getPrtNo() + "' and conttype='2'",
			// "UPDATE");
			// tmpMap.put("update LLBDutyCtrl set GrpContNo='" + newGrpContNo +
			// "',ContNo='" + newGrpContNo + "' where grpcontno='" +
			// signSchema.getPrtNo() + "'",
			// "UPDATE");
			// tmpMap.put("update LLDutyCtrllevel set ContNo='" + newGrpContNo +
			// "' where ContNo='" + signSchema.getPrtNo() + "'",
			// "UPDATE");
			// tmpMap.put("update LLBDutyCtrlLevel set ContNo='" + newGrpContNo
			// +
			// "' where ContNo='" + signSchema.getPrtNo() + "'",
			// "UPDATE");

			PubSubmit ps = new PubSubmit();
			VData sd = new VData();
			sd.add(tmpMap);
			if (!ps.submitData(sd, null)) {
				CError.buildErr(this, "合同号换号保存错误");
				return false;
			}
			// 更新成功
			signSchema.setGrpContNo(newGrpContNo);

		} else {
			newGrpContNo = signSchema.getGrpContNo();
		}

		// 查询获取所有合同单( 个单 )
		VData tVData =new VData();
		//tSignLCContSet = queryLCContSet(signSchema);
		tVData = queryLCContSet(signSchema);
//		if (tSignLCContSet == null || tSignLCContSet.size() == 0) {
//			logger.debug("团单下没有需要签单的个人合同！");
//		} else 
		{
			// 集体单签单
			boolean tOneResult = SignOneGrpCont(signSchema, tVData,
					newGrpContNo);
			if (!tOneResult) {
				if (!mErrors.needDealError()) {
					CError.buildErr(this, "团体合同(" + signSchema.getGrpContNo()
							+ "签单发生错误!");
				}
				return false;
			}
			// 增加收益人
		}
		// 更新整个团单相关信息
		if (!dealOneGrpCont(signSchema, newGrpContNo)) {
			if (!mErrors.needDealError()) {
				CError.buildErr(this, "团体合同(" + signSchema.getGrpContNo()
						+ "签单发生错误!");
			}
			return false;
		}
		return true;
	}
	
	/**
	 * 判断保单的险种币种和暂缴费的币种是否一致
	 * @param sPrtNo
	 * @return add by HY
	 */
	private boolean checkCurrency(LCGrpContSchema tLCGrpContSchema){
		String sPrtNo = tLCGrpContSchema.getPrtNo();
		try{
			String tSQL = " select riskcode,currency,nvl(sum(prem),0) from lcgrppol where 1 = 1  "
					    + " and prtno = '"+sPrtNo+"' "
					    + " and uwflag in ('3','4','9','d') "
					    + " group by grpcontno,riskcode,currency ";
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(tSQL);
			for(int i = 1; i <= tSSRS.getMaxRow(); i++){
				String tRiskCode = tSSRS.GetText(i, 1);
				String tCurrency = tSSRS.GetText(i, 2);
				String tempMoney = tSSRS.GetText(i, 3);
				double nMoney = Double.parseDouble(tempMoney);
				
				tSQL = " select 'X' from ljtempfee where 1 = 1 "
					 + " and otherno = '"+sPrtNo+"' "
					 + " and tempfeetype = '1' and (enteraccdate is not null and enteraccdate<>'3000-01-01') "
					 + " and confflag = '0' and riskcode = '"+tRiskCode+"' and currency = '"+tCurrency+"' ";
				String isFlag = tExeSQL.getOneValue(tSQL);
				if("X".equals(isFlag)){
					//判断金额
					tSQL = " select nvl(sum(paymoney),0) from ljtempfee where 1 = 1 "
						 + " and otherno = '"+sPrtNo+"' "
						 + " and tempfeetype = '1' and (enteraccdate is not null and enteraccdate<>'3000-01-01') "
						 + " and confflag = '0' and riskcode = '"+tRiskCode+"' and currency = '"+tCurrency+"' ";
					String tmoney = tExeSQL.getOneValue(tSQL);
					double nJMoney = Double.parseDouble(tmoney);
					if(nJMoney < nMoney){
						return false;
					}
				}else{
					return false;
				}
				
			}
					    
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean checkGrpFee(LCGrpContSchema signSchema) {
		ExeSQL tExeSQL = new ExeSQL();
		LCGrpPolDB ttLCGrpPolDB = new LCGrpPolDB();
		LCGrpPolSet ttLCGrpPolSet = new LCGrpPolSet();
		ttLCGrpPolDB.setGrpContNo(signSchema.getGrpContNo());
		ttLCGrpPolSet = ttLCGrpPolDB.query();
		LCGrpPolSchema tLCGrpPolSchema;
		LMRiskDB tLMRiskDB = new LMRiskDB();
		for (int i = 1; i <= ttLCGrpPolSet.size(); i++) {
			tLCGrpPolSchema = new LCGrpPolSchema();
			tLCGrpPolSchema = ttLCGrpPolSet.get(i);
			LMRiskSet tLMRiskSet = new LMRiskSet();
			tLMRiskDB.setRiskCode(tLCGrpPolSchema.getRiskCode());
			tLMRiskSet = tLMRiskDB.query();
			if (tLMRiskSet.get(1).getInsuAccFlag().trim().equals("Y")) {
				LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
				LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
				tLCGrpFeeDB.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
				tLCGrpFeeSet = tLCGrpFeeDB.query();
				if (tLCGrpFeeSet == null || tLCGrpFeeSet.size() == 0) {
					return false;
				}
				// 增加管理费行数的校验
				String accSql = "select count(*) from lmriskapp where riskcode ='"
						+ tLCGrpPolSchema.getRiskCode()
						+ "' and risktype6 in ('3') "; // risktype6 in ('3')
														// 代表162险种
				String accCount = tExeSQL.getOneValue(accSql);
				if (!accCount.equals("0")) {
					// 暂不增加通用处理，个人认为目前账户处理的方式有待。。。。
					if (tLCGrpFeeSet.size() != 2) {
						return false;
					}
				}
				String accSql2 = "select count(*) from lmriskapp where riskcode ='"
						+ tLCGrpPolSchema.getRiskCode()
						+ "' and risktype6 in ('4') "; // risktype6 in ('4')
														// 代表188险种
				String accCount2 = tExeSQL.getOneValue(accSql2);
				if (!accCount2.equals("0")) {
					// 暂不增加通用处理，个人认为目前账户处理的方式有待。。。。
					if (tLCGrpFeeSet.size() != 4) {
						return false;
					}
				}

			}
		}
		return true;
	}

	/**
	 * 集体保单签单
	 * 
	 * @param signSchema
	 *            LCGrpContSchema
	 * @param tSignLCContSet
	 *            LCContSet
	 * @param newGrpContNo
	 *            String
	 * @return boolean
	 */
	private boolean SignOneGrpCont(LCGrpContSchema signSchema,
			VData tVData, String newGrpContNo) {

		boolean signResult = true;
		boolean result = true;
		String tBatch_No="";
		SSRS tSSRS =new SSRS();
		tSSRS =(SSRS)tVData.getObjectByObjectName("SSRS", 0);
		logger.debug(tSSRS.getMaxRow());
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			tBatch_No =PubFun1.CreateMaxNo("GrpSignBatchNo", 20);
			for (int i = 1; i <=tSSRS.getMaxRow(); i++) {
				// 调用合同签单
				LCContSignBL tLCContSignBL = new LCContSignBL();

				LCContDB tLCContDB =new LCContDB();
				//减小内存支出
				LCContSet signSet = new LCContSet();
				
				tLCContDB.setContNo(tSSRS.GetText(i, 1));
//				if(!tLCContDB.getInfo()){
//					CError.buildErr(this, "查询合同号："+tSSRS.GetText(i, 1)+"错误！");
//					continue;
//				}
				signSet.add(tLCContDB.getSchema());
				VData tInput = new VData();
				tInput.add(mGlobalInput);
				tInput.add(0,tBatch_No);
				tInput.add(signSet);
				mTaskWaitList.add(tInput);
			}
			int mainContNum = tSSRS.MaxRow;//主被保人队列
			
			int tThreadNum=5;
			if(mainContNum>100&&mainContNum<=500){
				tThreadNum = 15;//common
			}else if(mainContNum>500){
				tThreadNum = 100;//fast
			}
	    ServiceA tService = new ServiceA("com.sinosoft.lis.tbgrp.LCContSignBL", mTaskWaitList, tThreadNum, 10);
		tService.start();
		//查询错误信息放到Error中
		MultThreadErrorDB tMultThreadErrorDB =new MultThreadErrorDB();
		MultThreadErrorSet tMultThreadErrorSet =new MultThreadErrorSet();
		tMultThreadErrorDB.setBatchNo(tBatch_No);
		tMultThreadErrorSet=tMultThreadErrorDB.query();
		for(int i=1;i<=tMultThreadErrorSet.size();i++){
			CError.buildErr(this, tMultThreadErrorSet.get(i).getReason());
		 }
		
		if (tMultThreadErrorSet.size() > 0) {
				return false;
			}
		}
		
		
		
		return result;
	}

	/**
	 * 处理与团体合同有关的相关表
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @param inNewGrpContNo
	 *            String
	 * @return boolean
	 */
	private boolean dealOneGrpCont(LCGrpContSchema tLCGrpContSchema,
			String inNewGrpContNo) {
		VData contData = new VData();
		// 处理业务，暂收核销，转实收，挂帐处理
		/* 第一步校验是否所有个单都已经处理完毕 */
		if (!checkSignCont(inNewGrpContNo)) {
			return false;
		}
		// 中间一步准备数据，不属于业务处理
		tAgentCode = tLCGrpContSchema.getAgentCode();
		tAgentGroup = tLCGrpContSchema.getAgentGroup();
		tOperator = mGlobalInput.Operator;
		tAgentType = tLCGrpContSchema.getAgentType();
		tAppNo = tLCGrpContSchema.getAppntNo();
		tOperator = mGlobalInput.Operator;
		tApprovecode = tLCGrpContSchema.getApproveCode();
		tApprDate = tLCGrpContSchema.getApproveDate();
		appName = tLCGrpContSchema.getGrpName();
		/* 第二步暂收核销 mFinFeeMap */
		if (!dealTemfee(tLCGrpContSchema, inNewGrpContNo)) {
			return false;
		}
		/* 第三步处理分单财务 mFinFeeMap */
		if (!dealSepGrppol(tLCGrpContSchema, inNewGrpContNo)) {
			return false;
		}
		/* 第四步处理总单财务 mFinFeeMap */
		if (!dealFinGrpCont(tLCGrpContSchema, inNewGrpContNo)) {
			return false;
		}
		/* 第五步处理个人下财务 mFinFeeMap */
		if (!dealLCPrem(tLCGrpContSchema, inNewGrpContNo)) {
			return false;
		}
		/* 第六步处理退费 mFinFeeMap */
		if (!dealBackMoney(tLCGrpContSchema, inNewGrpContNo)) {
			return false;
		}
		/* 第七步处理LCGRPPOL mFinFeeMap */
		if (!dealLCGrppol(tLCGrpContSchema, inNewGrpContNo)) {
			return false;
		}
		/* 第八步处理LCGRPCONT mFinFeeMap */
		if (!dealLCGrpCont(tLCGrpContSchema, inNewGrpContNo)) {
			return false;
		}
		PubSubmit pubSubmit = new PubSubmit();
		contData.add(mFinFeeMap);
		if (!pubSubmit.submitData(contData, "")) {

			CError.buildErr(this, "团单财务处理失败，请与信息部联系！");
			return false;
		}

		return true;
	}

	/*
	 * 第八步处理LCGRPCONT
	 */
	private boolean dealLCGrpCont(LCGrpContSchema tLCGrpContSchema,
			String inNewGrpContNo) {
		// 查询退保金额
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String tSql = "select nvl(sum(prem),0) from lcpol where grpcontno='"
				+ inNewGrpContNo + "' and uwflag='1'";
		tSSRS = tExeSQL.execSQL(tSql);
		double backPrem = Double.parseDouble(tSSRS.GetText(1, 1));

		tSql = "select nvl(sum(amnt),0) from lcpol where grpcontno='"
				+ inNewGrpContNo + "' and (uwflag='9' or uwflag='z')";
		tSSRS = tExeSQL.execSQL(tSql);
		double tAmnt = Double.parseDouble(tSSRS.GetText(1, 1));

		tLCGrpContSchema.setAppFlag("1");
		tLCGrpContSchema.setPrem(Arith.round(tSumMoney - backPrem, 2));
		tLCGrpContSchema.setSumPrem(Arith.round(tSumMoney - backPrem, 2));
		tLCGrpContSchema.setDif(Arith.round(tDiff, 2));
		tLCGrpContSchema.setAmnt(tAmnt);
		// LCContDB tLCContDB=new LCContDB();
		// LCContSet tLCContSet=new LCContSet();
		// tLCContDB.setGrpContNo(inNewGrpContNo);
		tSql = "select nvl(sum(peoples),0) from lccont where grpcontno='"
				+ inNewGrpContNo + "'";

		tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(tSql);
		int tPeople = Integer.parseInt(tSSRS.GetText(1, 1));
		// tLCContSet=tLCContDB.query();
		// tLCContSet=tLCContDB.executeQuery(tSql);
		tLCGrpContSchema.setPeoples(tPeople);
		tLCGrpContSchema.setPeoples2(tPeople);
		tLCGrpContSchema.setMarketType("0");
		tLCGrpContSchema.setSignDate(PubFun.getCurrentDate());
		tLCGrpContSchema.setSignTime(PubFun.getCurrentTime());
		tLCGrpContSchema.setSignCom(mGlobalInput.ManageCom);
		tLCGrpContSchema.setState("1");
		tLCGrpContSchema.setContType("2");
		mFinFeeMap.put(tLCGrpContSchema, "UPDATE");
		mFinFeeMap.put("update lccont  set CValiDate='"
				+ tLCGrpContSchema.getCValiDate() + "' where grpcontno='"
				+ inNewGrpContNo + "' and CValiDate is null ", "UPDATE");
		
		//tongmeng 2009-03-19 add
		//处理LCGrpIssuePol,lcgrppenotice 的换号
		LCGrpIssuePolSet tLCGrpIssuePolSet = new LCGrpIssuePolSet();
		LCGrpIssuePolDB tLCGrpIssuePolDB = new LCGrpIssuePolDB();
		String tSQL_Issue = "select * from LCGrpIssuePol where grpcontno='"+tLCGrpContSchema.getPrtNo()+"'";
		tLCGrpIssuePolSet = tLCGrpIssuePolDB.executeQuery(tSQL_Issue);
		if(tLCGrpIssuePolSet.size()>0)
		{
			for(int i=1;i<=tLCGrpIssuePolSet.size();i++)
			{
				tLCGrpIssuePolSet.get(i).setGrpContNo(inNewGrpContNo);
			}
			mFinFeeMap.put(tLCGrpIssuePolSet, "UPDATE");
		}
		LCGrpPENoticeSet tLCGrpPENoticeSet = new LCGrpPENoticeSet();
		LCGrpPENoticeDB tLCGrpPENoticeDB = new LCGrpPENoticeDB();
		String tSQL_PE = "select * from  LCGrpIssuePol where grpproposalno='"+tLCGrpContSchema.getPrtNo()+"'";
		tLCGrpPENoticeSet = tLCGrpPENoticeDB.executeQuery(tSQL_PE);
		if(tLCGrpPENoticeSet.size()>0)
		{
			for(int i=1;i<=tLCGrpPENoticeSet.size();i++)
			{
				tLCGrpPENoticeSet.get(i).setGrpContNo(inNewGrpContNo);
			}
			mFinFeeMap.put(tLCGrpPENoticeSet, "UPDATE");
		}
		
		return true;
	}

	/*
	 * 第七步处理LCGRPPOL
	 */
	private boolean dealLCGrppol(LCGrpContSchema tLCGrpContSchema,
			String inNewGrpContNo) {
		// tLCGrpPolSet 前面已经将数据查询出来
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		// 以上用来查询金额
		logger.debug("处理分单的个数：" + tLCGrpPolSet.size());
		LCGrpPolSet tLCGrpPol = new LCGrpPolSet();
		LCGrpPolSchema tLCGrpPolSchema;
		String unit = "M";
		int interval = 0;
		Date baseDate;
		/*
		 * 处理管理费
		 */
		LCOutManagePayDB tLCOutManagePayDB = new LCOutManagePayDB();
		LCOutManagePaySet tLCOutManagePaySet = new LCOutManagePaySet();
		tLCOutManagePayDB.setGrpContNo(tLCGrpContSchema.getPrtNo());
		tLCOutManagePayDB.setGrpPolNo("000000");
		tLCOutManagePaySet = tLCOutManagePayDB.query();
		double tOutPay = 0;
		if (tLCOutManagePaySet != null && tLCOutManagePaySet.size() > 0) {
			tOutPay = tLCOutManagePaySet.get(1).getPrem();
		}

		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			Date maxPaytodate = fDate.getDate("1900-1-1");
			tLCGrpPolSchema = new LCGrpPolSchema();
			tLCGrpPolSchema = tLCGrpPolSet.get(i);
			tLCGrpPolSchema.setAppFlag("1");
			//tLCGrpPolSchema.setSaleChnl("2"); //保持原来的销售渠道 modify on 2009-01-09

			String ttSql = "select nvl(risktype6,0) from lmriskapp where riskcode='"
				+ tLCGrpPolSchema.getRiskCode() + "' ";
			ExeSQL ttExeSQL = new ExeSQL();
			String tRiskType6 = ttExeSQL.getOneValue(ttSql); // 判断险种的类型

			if (tRiskType6.equals("1")) {
				tLCGrpPolSchema.setPrem(tLCGrpPolSchema.getPrem() + tOutPay);
				tLCGrpPolSchema.setSumPrem(tLCGrpPolSchema.getPrem());
			} else {
				tLCGrpPolSchema.setSumPrem(tLCGrpPolSchema.getPrem());
			}
			// 增加分红标志处理
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			LMRiskAppSet tLMRiskAppSet = new LMRiskAppSet();
			tLMRiskAppDB.setRiskCode(tLCGrpPolSchema.getRiskCode());
			tLMRiskAppSet = tLMRiskAppDB.query();
			LMRiskAppSchema tLMRiskAppSchema = tLMRiskAppSet.get(1);
			if (tLMRiskAppSchema.getBonusFlag() != null) {
				tLCGrpPolSchema.setBonusFlag(tLMRiskAppSet.get(1)
						.getBonusFlag());
			}
			// 设置PAYTODATE

			SSRS ttSSRS = new SSRS();
			String ttsql = "select max(paytodate) from lcpol where grppolno='"
				+ tLCGrpPolSchema.getGrpPolNo() + "'";
			ttSSRS = tExeSQL.execSQL(ttsql);
			if (ttSSRS.MaxRow > 0) {
				tLCGrpPolSchema.setPaytoDate(ttSSRS.GetText(1, 1));
				tLCGrpPolSchema.setRegetDate(ttSSRS.GetText(1, 1));
			} else {
				tLCGrpPolSchema.setPaytoDate(PubFun.getCurrentDate());
				tLCGrpPolSchema.setRegetDate(PubFun.getCurrentDate());
			}
			if(!"L".equals(tLMRiskAppSchema.getRiskPeriod())){
				//非长期险要置上payenddate，否则保全短期费率会出错。 add by sunsx 2009-09-02
				ttsql = "select max(payenddate) from lcpol where grppolno='"
					+ tLCGrpPolSchema.getGrpPolNo() + "'";
				ttSSRS = tExeSQL.execSQL(ttsql);
				if (ttSSRS.MaxRow > 0) {
					tLCGrpPolSchema.setPayEndDate(ttSSRS.GetText(1, 1));
				} else {
					tLCGrpPolSchema.setPayEndDate(PubFun.getCurrentDate());
				}
			}
			// 分单的钱数应该是已经承保的

			String tSql = "select nvl(sum(prem),0),nvl(sum(amnt),0) from lcpol where grppolno='"
				+ tLCGrpPolSchema.getGrpPolNo()
				+ "' and (uwflag='9' or uwflag='z')";
			tSSRS = tExeSQL.execSQL(tSql);
			double sumPrem = Double.parseDouble(tSSRS.GetText(1, 1));
			double sumAmnt = Double.parseDouble(tSSRS.GetText(1, 2));
			tLCGrpPolSchema.setPrem(sumPrem);
			tLCGrpPolSchema.setSumPrem(sumPrem);
			tLCGrpPolSchema.setAmnt(sumAmnt);
			tLCGrpPolSchema.setAgentGroup(tLCGrpContSchema.getAgentGroup());
			if (tLCGrpPolSchema.getUWFlag() != null
					&& !tLCGrpPolSchema.getUWFlag().equals("1")) {
				tLCGrpPolSchema.setState("1");
			} else {
				tLCGrpPolSchema.setState("0");
			}

			// 增加对账户型险种的支持 ttMoney(外缴管理费) tLCGrpContSchema

			// LMRiskAppSet ttLMRiskAppSet=new LMRiskAppSet();
			// tLMRiskAppDB.setRiskCode(tLCGrpPolSchema.getRiskCode());
			// ttLMRiskAppSet=tLMRiskAppDB.query();
			// if(ttLMRiskAppSet.get(1).getBonusFlag().equals("1") ||
			// ttLMRiskAppSet.get(1).getBonusFlag().equals("2"))
			// {
			// tLCGrpPolSchema.setPrem(tLCGrpPolSchema.getPrem()+ttMoney);
			// tLCGrpPolSchema.setSumPrem(tLCGrpPolSchema.getPrem());
			// }

			tLCGrpPol.add(tLCGrpPolSchema);
		}
		//tongmeng 2009-03-19 add
		//生成团险保单状态表
		LCGrpContStateSet tLCGrpContStateSet = new LCGrpContStateSet();
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			LCGrpPolSchema tempLCGrpPolSchema = new LCGrpPolSchema();
			tempLCGrpPolSchema = tLCGrpPolSet.get(i);
			LCGrpContStateSchema tLCGrpContStateSchema = new LCGrpContStateSchema();
			tLCGrpContStateSchema.setGrpContNo(inNewGrpContNo);
			tLCGrpContStateSchema.setGrpPolNo(tempLCGrpPolSchema.getGrpPolNo());
			tLCGrpContStateSchema.setStateType("Available");
			tLCGrpContStateSchema.setState("0");
			tLCGrpContStateSchema.setStartDate(tLCGrpContSchema.getCValiDate());
			tLCGrpContStateSchema.setOperator(mGlobalInput.Operator);
			tLCGrpContStateSchema.setMakeDate(PubFun.getCurrentDate());
			tLCGrpContStateSchema.setMakeTime(PubFun.getCurrentTime());
			tLCGrpContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGrpContStateSchema.setModifyTime(PubFun.getCurrentTime());
			tLCGrpContStateSet.add(tLCGrpContStateSchema);
		} 
		mFinFeeMap.put(tLCGrpPol, "UPDATE");
		if(tLCGrpContStateSet.size()>0)
		{
			mFinFeeMap.put(tLCGrpContStateSet, "INSERT");
		}
		return true;
	}

	/*
	 * 第六步处理退费
	 */
	private boolean dealBackMoney(LCGrpContSchema tLCGrpContSchema,
			String inNewGrpContNo) {
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String tSql = "select nvl(sum(prem),0) from lcpol where grpcontno='"
				+ inNewGrpContNo + "' and (uwflag='9' or uwflag='z')";
		tSSRS = tExeSQL.execSQL(tSql);
		double sumPrem = Double.parseDouble(tSSRS.GetText(1, 1));
		tSql = "select nvl(sum(paymoney),0) from ljtempfee where otherno='"
				+ tLCGrpContSchema.getPrtNo()
				+ "' and confflag='0'  and EnterAccDate is not null and EnterAccDate!='3000-1-1' and operstate='0'"; // 以后应该使用投保单号
		tSSRS = tExeSQL.execSQL(tSql);
		double sumPay = Double.parseDouble(tSSRS.GetText(1, 1));
		//查询险种编码 并判断是否为健康险
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		tLCGrpPolDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());
		tLCGrpPolSet = tLCGrpPolDB.query();
		if(tLCGrpPolSet.size()<=0){
			CError.buildErr(this, "合同号为"+tLCGrpContSchema.getGrpContNo()+"的合同,查询险种错误!");
		}else{
			String rRiskCode = tLCGrpPolSet.get(1).getRiskCode();
			LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
			LMRiskAppSet mLMRiskAppSet = new LMRiskAppSet();
			mLMRiskAppDB.setRiskType("H");
			mLMRiskAppDB.setHealthType("1");
			mLMRiskAppDB.setRiskCode(rRiskCode);
			mLMRiskAppSet = mLMRiskAppDB.query();
			if(mLMRiskAppSet.size()>0){
				mPayType = "TM";
				mPayTypeleft = "YETTM";
				mFinFeeType = "CM";
			}
			else{
				mPayType = "ZC";
				mPayTypeleft = "YET";
				mFinFeeType = "YJTF";
			}
		}

		
		if (sumPay > sumPrem) {
			tDiff = Arith.round(sumPay - sumPrem, 2);
			// 不用生成打印管理表数据
			
			//不生成溢交退费记录 modify by sunsx 2009-09-29
			/*
			String tLimit = PubFun.getNoLimit(tLCGrpContSchema.getManageCom());
			String getNo = PubFun1.CreateMaxNo("GETNO", tLimit);
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
			tLJAGetSchema.setActuGetNo(getNo);
			tLJAGetSchema.setOperState("0");
			tLJAGetSchema.setOtherNo(inNewGrpContNo);
			tLJAGetSchema.setOtherNoType("8");
			tLJAGetSchema.setSendBankCount(0);
			//tLJAGetSchema.setManageCom(mGlobalInput.ManageCom);
			//机构赋值为保单机构
			tLJAGetSchema.setManageCom(tLCGrpContSchema.getManageCom());
			tLJAGetSchema.setAgentType(tAgentType);
			tLJAGetSchema.setAgentCode(tAgentCode);
			tLJAGetSchema.setAgentGroup(tAgentGroup);
			tLJAGetSchema.setAppntNo(tAppNo);
			tLJAGetSchema.setSumGetMoney(Arith.round(sumPay - sumPrem, 2));
			tLJAGetSchema.setSaleChnl("2");
			tLJAGetSchema.setShouldDate(tDate);
			// tLJAGetSchema.setEnterAccDate(tEnterAccDate);
			tLJAGetSchema.setStartGetDate(tDate);
			tLJAGetSchema.setStartGetDate(tDate);
			tLJAGetSchema.setSerialNo(serNo);
			tLJAGetSchema.setOperator(tOperator);
			tLJAGetSchema.setMakeDate(tDate);
			tLJAGetSchema.setModifyDate(tDate);
			tLJAGetSchema.setMakeTime(PubFun.getCurrentTime());
			tLJAGetSchema.setModifyTime(PubFun.getCurrentTime());
			mFinFeeMap.put(tLJAGetSchema, "INSERT");

			LJAGetOtherSchema tLJAGetOtherSchema = new LJAGetOtherSchema();
			tLJAGetOtherSchema.setActuGetNo(getNo);
			tLJAGetOtherSchema.setOperState("0");
			tLJAGetOtherSchema.setOtherNo(inNewGrpContNo);
			tLJAGetOtherSchema.setOtherNoType("8");
			tLJAGetOtherSchema.setPayMode("1");
			tLJAGetOtherSchema.setGetMoney(Arith.round(sumPay - sumPrem, 2));
			tLJAGetOtherSchema.setGetDate(tDate);
			//tLJAGetOtherSchema.setManageCom(mGlobalInput.ManageCom);
			tLJAGetOtherSchema.setManageCom(tLCGrpContSchema.getManageCom());
			tLJAGetOtherSchema.setAgentType(tAgentType);
			tLJAGetOtherSchema.setAPPntName(appName);
			tLJAGetOtherSchema.setAgentCode(tAgentCode);
			tLJAGetOtherSchema.setAgentGroup(tAgentGroup);
			tLJAGetOtherSchema.setFeeOperationType("YJTF");
			tLJAGetOtherSchema.setFeeFinaType(mFinFeeType);
			tLJAGetOtherSchema.setSerialNo(serNo);
			tLJAGetOtherSchema.setOperator(tOperator);
			tLJAGetOtherSchema.setMakeDate(tDate);
			tLJAGetOtherSchema.setModifyDate(tDate);
			tLJAGetOtherSchema.setMakeTime(PubFun.getCurrentTime());
			tLJAGetOtherSchema.setModifyTime(PubFun.getCurrentTime());
			mFinFeeMap.put(tLJAGetOtherSchema, "INSERT");
			*/

			LJAPayGrpSchema nLJAPayGrpSchema = new LJAPayGrpSchema();
			nLJAPayGrpSchema.setGrpPolNo(tLCGrpPolSet.get(1).getGrpPolNo());
			nLJAPayGrpSchema.setOperState("0");
			nLJAPayGrpSchema.setPayCount("1");
			nLJAPayGrpSchema.setGrpContNo(inNewGrpContNo);
			//nLJAPayGrpSchema.setManageCom(mGlobalInput.ManageCom);
			nLJAPayGrpSchema.setManageCom(tLCGrpContSchema.getManageCom());
			nLJAPayGrpSchema.setRiskCode("000000");
			nLJAPayGrpSchema.setAgentType(tAgentType);
			nLJAPayGrpSchema.setAgentCode(tAgentCode);
			nLJAPayGrpSchema.setAgentGroup(tAgentGroup);
			nLJAPayGrpSchema.setPayNo(tPayNo);
			nLJAPayGrpSchema.setAppntNo(tAppNo);
			nLJAPayGrpSchema.setPayIntv(tLCGrpPolSet.get(1).getPayIntv());
			nLJAPayGrpSchema.setPayDate(tDate);
			nLJAPayGrpSchema.setPayType(mPayTypeleft); // 挂帐
			nLJAPayGrpSchema.setSumActuPayMoney(tDiff); // 挂帐费
			nLJAPayGrpSchema.setSumDuePayMoney(tDiff); // 挂帐费
			nLJAPayGrpSchema.setEnterAccDate(tEnterAccDate);
			nLJAPayGrpSchema.setConfDate(tDate);
			nLJAPayGrpSchema.setLastPayToDate(tDate);
			nLJAPayGrpSchema.setCurPayToDate(tDate);
			nLJAPayGrpSchema.setApproveCode(tOperator);
			nLJAPayGrpSchema.setOperator(tOperator);
			nLJAPayGrpSchema.setMakeDate(tDate);
			nLJAPayGrpSchema.setModifyDate(tDate);
			nLJAPayGrpSchema.setMakeTime(PubFun.getCurrentTime());
			nLJAPayGrpSchema.setModifyTime(PubFun.getCurrentTime());
			nLJAPayGrpSchema.setFeeMoney(0);
			//add by tansz on 20110921 for 币种临时解决方案 TODO
			nLJAPayGrpSchema.setCurrency(tLCGrpPolSet.get(1).getCurrency());
			mFinFeeMap.put(nLJAPayGrpSchema, "INSERT");

		}
		if(mLCGrpContSet.size()>0){
			String tSQL ="update ljtempfeeclass set otherno ='"+tPayNo+"' where tempfeeno ='"+mLCGrpContSet.get(1).getPrtNo()+"'";
			//mFinFeeMap.put(tSQL, "UPDATE");
		}else{
			CError.buildErr(this, "团体合同信息为空！");
			return false;
		}
		return true;
	}

	/*
	 * 第五步处理个人下财务
	 */
	private boolean dealLCPrem(LCGrpContSchema tLCGrpContSchema,
			String inNewGrpContNo) {
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
		tLJAPayPersonDB.setGrpContNo(inNewGrpContNo);
		ExeSQL tExeSQL = new ExeSQL();
		tLJAPayPersonSet = tLJAPayPersonDB.query();
		for(int i = 1; i <= tLJAPayPersonSet.size(); i++){
			String tGrpPolNo =  tLJAPayPersonSet.get(i).getGrpPolNo();
		    String strsql = " select currency from lcgrppol where 1 = 1 and grppolno = '"+tGrpPolNo+"' ";
		    String tcurrency = tExeSQL.getOneValue(strsql);
		    tLJAPayPersonSet.get(i).setCurrency(tcurrency);
		}
		mFinFeeMap.put(tLJAPayPersonSet, "UPDATE");
		
		mFinFeeMap.put("update LJAPayPerson set payno='" + tPayNo
				+ "' where grpcontno='" + inNewGrpContNo + "'", "UPDATE");
		
		
		
		
		// LJAPayPersonSet tLJAPayPersonSet=new LJAPayPersonSet();
		// LJAPayPersonSchema tLJAPayPersonSchema;
		// LCPremDB tLCPremDB=new LCPremDB();
		// LCPremSet tLCPremSet=new LCPremSet();
		// LCPremSchema tLCPremSchema;
		// tLCPremDB.setGrpContNo(inNewGrpContNo);
		// tLCPremSet=tLCPremDB.query();
		// String tRiskcode;
		// String tSql;
		// LCGrpPolSet tLCGrpPolSet;
		// LCGrpPolDB tLCGrpPolDB=new LCGrpPolDB();
		// if(tLCPremSet!=null && tLCPremSet.size()>0)
		// {
		// for(int i=1;i<=tLCPremSet.size();i++)
		// {
		// tLCPremSchema=new LCPremSchema();
		// tLCPremSchema=tLCPremSet.get(i);
		// tLJAPayPersonSchema=new LJAPayPersonSchema();
		// tLJAPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
		// tLJAPayPersonSchema.setPayCount(1);
		// tLJAPayPersonSchema.setGrpContNo(tLCPremSchema.getGrpContNo());
		// tRiskcode=tLCPremSchema.getDutyCode().substring(0,3);
		// tSql="select * From lcgrppol where grpcontno='"+inNewGrpContNo+"' and
		// riskcode='"+tRiskcode+"'";
		// tLCGrpPolSet=new LCGrpPolSet();
		// tLCGrpPolSet=tLCGrpPolDB.executeQuery(tSql);
		// tLJAPayPersonSchema.setGrpPolNo(tLCGrpPolSet.get(1).getGrpPolNo());
		// tLJAPayPersonSchema.setContNo(tLCPremSchema.getContNo());
		// tLJAPayPersonSchema.setManageCom(mGlobalInput.ManageCom);
		// tLJAPayPersonSchema.setAgentType(tAgentType);
		// tLJAPayPersonSchema.setRiskCode(tRiskcode);
		// tLJAPayPersonSchema.setAgentCode(tAgentCode);
		// tLJAPayPersonSchema.setAgentGroup(tAgentGroup);
		// tLJAPayPersonSchema.setAppntNo(tAppNo);
		// tLJAPayPersonSchema.setPayNo(tPayNo);
		// tLJAPayPersonSchema.setPayAimClass("1");
		// tLJAPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
		// tLJAPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
		// tLJAPayPersonSchema.setSumActuPayMoney(tLCPremSchema.getPrem());
		// tLJAPayPersonSchema.setSumDuePayMoney(tLCPremSchema.getPrem());
		// tLJAPayPersonSchema.setPayIntv(tLCPremSchema.getPayIntv());
		// tLJAPayPersonSchema.setPayDate(tDate);
		// tLJAPayPersonSchema.setPayType("ZC");
		// tLJAPayPersonSchema.setEnterAccDate(tEnterAccDate);
		// tLJAPayPersonSchema.setConfDate(tDate);
		// tLJAPayPersonSchema.setCurPayToDate(tDate);
		// tLJAPayPersonSchema.setLastPayToDate(tDate);
		// tLJAPayPersonSchema.setApproveCode(tApprovecode);
		// tLJAPayPersonSchema.setApproveDate(tApprDate);
		// tLJAPayPersonSchema.setMakeDate(tDate);
		// tLJAPayPersonSchema.setModifyDate(tDate);
		// tLJAPayPersonSchema.setMakeTime(PubFun.getCurrentTime());
		// tLJAPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
		// tLJAPayPersonSchema.setOperator(tOperator);
		// tLJAPayPersonSet.add(tLJAPayPersonSchema);
		// }
		// mFinFeeMap.put(tLJAPayPersonSet,"INSERT");
		// }else{
		// CError.buildErr(this, "团该团单下个人保费明细丢失数据，请与信息部联系！");
		// return false;
		// }

		return true;
	}

	/*
	 * 第四步处理总单财务
	 */
	private boolean dealFinGrpCont(LCGrpContSchema tLCGrpContSchema,
			String inNewGrpContNo) {
		LJAPaySchema tLJAPaySchema = new LJAPaySchema();
		tLJAPaySchema.setPayNo(tPayNo);
		tLJAPaySchema.setIncomeNo(inNewGrpContNo);
		//团险要将IncomeType置为‘1’
		tLJAPaySchema.setIncomeType("1");
		tLJAPaySchema.setAppntNo(tAppNo);
		tLJAPaySchema.setSumActuPayMoney(tempSumMoney);
		tLJAPaySchema.setPayDate(tDate);
		tLJAPaySchema.setEnterAccDate(tEnterAccDate);
		tLJAPaySchema.setSerialNo(tPayNo);
		tLJAPaySchema.setConfDate(tDate);
		tLJAPaySchema.setApproveCode(tApprovecode);
		tLJAPaySchema.setApproveDate(tApprDate);
		tLJAPaySchema.setMakeDate(tDate);
		tLJAPaySchema.setModifyDate(tDate);
		tLJAPaySchema.setMakeTime(PubFun.getCurrentTime());
		tLJAPaySchema.setModifyTime(PubFun.getCurrentTime());
		//tLJAPaySchema.setManageCom(mGlobalInput.ManageCom);
		tLJAPaySchema.setManageCom(tLCGrpContSchema.getManageCom());
		tLJAPaySchema.setOperator(mGlobalInput.Operator);
		tLJAPaySchema.setAgentType(tAgentType);
		tLJAPaySchema.setAgentCode(tAgentCode);
		tLJAPaySchema.setAgentGroup(tAgentGroup);
		tLJAPaySchema.setStartPayDate(tDate);
		tLJAPaySchema.setOtherNoType("7");
		tLJAPaySchema.setOtherNo(inNewGrpContNo);
		//add by tansz on 20110921 for 币种临时解决方案 TODO
		//不考虑同张保单下存在多币种的情况，可以取主险币种
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL = " select Currency from LCGrpPol where 1 = 1 and GrpContNo = '"+inNewGrpContNo+"' order by GRPPolNo  ";
		String tCurrency = tExeSQL.getOneValue(tSQL);
		if("".equals(tCurrency) || tCurrency == null ){
			tLJAPaySchema.setCurrency("01");
		}else{
			tLJAPaySchema.setCurrency(tCurrency);
		}
		
		mFinFeeMap.put(tLJAPaySchema, "INSERT");
		mFinFeeMap.put(
				"update LCInsureAccTrace set maketime='00:00:00',othertype='2',otherno='"
						+ tPayNo + "',payno='" + tPayNo + "' where grpcontno='"
						+ inNewGrpContNo + "'", "UPDATE");
		mFinFeeMap.put("update Lcinsureaccclass set otherno='" + tPayNo
				+ "' where grpcontno='" + inNewGrpContNo
				+ "' and othertype='2'", "UPDATE");
		mFinFeeMap.put("update LCInsureAccClassFee set othertype='2',otherno='"
				+ tPayNo + "' where grpcontno='" + inNewGrpContNo + "'",
				"UPDATE");
		mFinFeeMap.put("update LCInsureAccFeeTrace set othertype='2',otherno='"
				+ tPayNo + "' where grpcontno='" + inNewGrpContNo + "'",
				"UPDATE");
		mFinFeeMap.put("update lcpol set polstate='8' where grpcontno='"
				+ inNewGrpContNo + "' and (polstate is null or polstate='0')",
				"UPDATE");
		mFinFeeMap.put("update lccont set state='0' where grpcontno='"
				+ inNewGrpContNo + "' and state is null", "UPDATE");
		mFinFeeMap
				.put(
						"update ljapaygrp set feemoney=(select nvl(sum(fee),0) from lcinsureaccfeetrace"
								+ " where payno=ljapaygrp.payno and grppolno=ljapaygrp.grppolno and moneytype='GL')"
								+ "  where grpcontno='"
								+ inNewGrpContNo
								+ "' and operstate='0'", "UPDATE");
		return true;
	}

	/*
	 * 第三步处理分单财务
	 */
	private boolean dealSepGrppol(LCGrpContSchema tLCGrpContSchema,
			String inNewGrpContNo) {
		// 生成号码
		tPayNo = PubFun1.CreateMaxNo("PAYNO", PubFun
				.getNoLimit(tLCGrpContSchema.getManageCom()));
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		
		
//		//查询险种编码 并判断是否为健康险
//		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
//		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
//		tLCGrpPolDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());
//		tLCGrpPolSet = tLCGrpPolDB.query();
//		if(tLCGrpPolSet.size()<=0){
//			CError.buildErr(this, "合同号为"+tLCGrpContSchema.getGrpContNo()+"的合同,查询险种错误!");
//		}else{
//			String rRiskCode = tLCGrpPolSet.get(1).getRiskCode();
//			LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
//			LMRiskAppSet mLMRiskAppSet = new LMRiskAppSet();
//			mLMRiskAppDB.setRiskType("H");
//			//mLMRiskAppDB.setHealthType("1");
//			mLMRiskAppDB.setRiskCode(rRiskCode);
//			mLMRiskAppSet = mLMRiskAppDB.query();
//			if(mLMRiskAppSet.size()>0){
//				mPayType = "TM";
//				mPayTypeleft = "YETTM";
//			}
//			else{
//				mPayType = "ZC";
//				mPayTypeleft = "YET";
//			}
//		}

		LJAPayGrpSet tLJAPayGrpSet = new LJAPayGrpSet();
		LJAPayGrpSchema tLJAPayGrpSchema;
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();

		LCGrpPolSchema tLCGrpPolSchema;
		tLCGrpPolDB.setGrpContNo(inNewGrpContNo);
		tLCGrpPolSet = tLCGrpPolDB.query();
		double tOutPay = 0;
		if (tLCGrpPolSet != null && tLCGrpPolSet.size() > 0) {
			for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
				tLCGrpPolSchema = new LCGrpPolSchema();
				tLCGrpPolSchema = tLCGrpPolSet.get(i);
				LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
				LMRiskAppSet mLMRiskAppSet = new LMRiskAppSet();
				mLMRiskAppDB.setRiskType("H");
				mLMRiskAppDB.setHealthType("1");
				mLMRiskAppDB.setRiskCode(tLCGrpPolSchema.getRiskCode());
				mLMRiskAppSet = mLMRiskAppDB.query();
				if(mLMRiskAppSet.size()>0){
					mPayType = "TM";
				}
				else{
					mPayType = "ZC";
				}
			

				tLJAPayGrpSchema = new LJAPayGrpSchema();
				tLJAPayGrpSchema.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
				tLJAPayGrpSchema.setOperState("0");
				tLJAPayGrpSchema.setPayCount("1");
				tLJAPayGrpSchema.setGrpContNo(inNewGrpContNo);
				//tLJAPayGrpSchema.setManageCom(mGlobalInput.ManageCom);
				tLJAPayGrpSchema.setManageCom(tLCGrpPolSet.get(i).getManageCom());
				tLJAPayGrpSchema.setRiskCode(tLCGrpPolSchema.getRiskCode());
				tLJAPayGrpSchema.setAgentType(tAgentType);
				tLJAPayGrpSchema.setAgentCode(tAgentCode);
				tLJAPayGrpSchema.setAgentGroup(tAgentGroup);
				tLJAPayGrpSchema.setPayNo(tPayNo);
				tLJAPayGrpSchema.setAppntNo(tAppNo);
				// 如果是139险种还要处理外缴管理费
				LCOutManagePayDB tLCOutManagePayDB = new LCOutManagePayDB();
				LCOutManagePaySet tLCOutManagePaySet = new LCOutManagePaySet();
				tLCOutManagePayDB.setGrpContNo(tLCGrpContSchema.getPrtNo());
				tLCOutManagePayDB.setGrpPolNo("000000");
				tLCOutManagePaySet = tLCOutManagePayDB.query();
				if (tLCOutManagePaySet != null && tLCOutManagePaySet.size() > 0) {
					tOutPay = tLCOutManagePaySet.get(1).getPrem();
				}
				// else
				// {
				// tLCOutManagePayDB.setGrpContNo(inNewGrpContNo);
				// tLCOutManagePayDB.setGrpPolNo("000000");
				// tLCOutManagePaySet = new LCOutManagePaySet();
				// tLCOutManagePaySet = tLCOutManagePayDB.query();
				// if (tLCOutManagePaySet != null &&
				// tLCOutManagePaySet.size() > 0)
				// {
				// tOutPay = tLCOutManagePaySet.get(1).getPrem();
				// }
				//
				// }
				//
				String ttSql = "select nvl(risktype6,0) from lmriskapp where riskcode='"
						+ tLCGrpPolSchema.getRiskCode() + "' ";
				ExeSQL ttExeSQL = new ExeSQL();
				String tRiskType6 = ttExeSQL.getOneValue(ttSql); // 判断险种的类型
				if (tRiskType6.equals("1")) { // 139 151
					// 包括拒保的金额
					String tSql = "select nvl(sum(prem),0) from lcpol where grppolno='"
							+ tLCGrpPolSchema.getGrpPolNo()
							+ "' and uwflag='1'";
					tSSRS = tExeSQL.execSQL(tSql);
					double tBackMoney = Double.parseDouble(tSSRS.GetText(1, 1));
					tLJAPayGrpSchema.setSumActuPayMoney(Arith
							.round(tLCGrpPolSchema.getPrem() + tOutPay
									- tBackMoney, 2));
					tLJAPayGrpSchema.setSumDuePayMoney(Arith
							.round(tLCGrpPolSchema.getPrem() + tOutPay
									- tBackMoney, 2));
					tSumMoney = tSumMoney
							+ Arith.round(tLCGrpPolSchema.getPrem() + tOutPay,
									2);
				} else {
					String tSql = "select nvl(sum(prem),0) from lcpol where grppolno='"
							+ tLCGrpPolSchema.getGrpPolNo()
							+ "' and uwflag='1'";
					tSSRS = tExeSQL.execSQL(tSql);
					double tBackMoney = Double.parseDouble(tSSRS.GetText(1, 1));
					tSql = "select nvl(sum(prem),0) from lcpol where grppolno='"
							+ tLCGrpPolSchema.getGrpPolNo() + "'";
					tSSRS = tExeSQL.execSQL(tSql);
					double tSumPayMoney = Double.parseDouble(tSSRS
							.GetText(1, 1));
					tSumMoney = tSumMoney + tSumPayMoney;
					tLJAPayGrpSchema.setSumActuPayMoney(Arith.round(
							tSumPayMoney - tBackMoney, 2));
					tLJAPayGrpSchema.setSumDuePayMoney(Arith.round(tSumPayMoney
							- tBackMoney, 2));

				}
				// //增加对账户型险种的支持 ttMoney(外缴管理费)
				// LMRiskAppDB tLMRiskAppDB=new LMRiskAppDB();
				// LMRiskAppSet tLMRiskAppSet=new LMRiskAppSet();
				// tLMRiskAppDB.setRiskCode(tLCGrpPolSchema.getRiskCode());
				// tLMRiskAppSet=tLMRiskAppDB.query();
				// if(tLMRiskAppSet.get(1).getBonusFlag().equals("1") ||
				// tLMRiskAppSet.get(1).getBonusFlag().equals("2"))
				// {
				// tLJAPayGrpSchema.setSumActuPayMoney(tLJAPayGrpSchema.getSumActuPayMoney()+ttMoney);
				// tLJAPayGrpSchema.setSumDuePayMoney(tLJAPayGrpSchema.getSumDuePayMoney()+ttMoney);
				// }

				tOutPay = 0;
				tLJAPayGrpSchema.setPayIntv(tLCGrpPolSchema.getPayIntv());
				tLJAPayGrpSchema.setPayDate(tDate);
				tLJAPayGrpSchema.setPayType(mPayType);
				tLJAPayGrpSchema.setEnterAccDate(tEnterAccDate);
				tLJAPayGrpSchema.setConfDate(tDate);
				tLJAPayGrpSchema.setLastPayToDate(tDate);
				tLJAPayGrpSchema.setCurPayToDate(tDate);
				tLJAPayGrpSchema.setApproveCode(tOperator);
				tLJAPayGrpSchema.setOperator(tOperator);
				tLJAPayGrpSchema.setMakeDate(tDate);
				tLJAPayGrpSchema.setModifyDate(tDate);
				tLJAPayGrpSchema.setMakeTime(PubFun.getCurrentTime());
				tLJAPayGrpSchema.setModifyTime(PubFun.getCurrentTime());
				tLJAPayGrpSchema.setFeeMoney(0);
				//add by tansz on 20110921 for 币种临时解决方案 TODO
				tLJAPayGrpSchema.setCurrency(tLCGrpPolSchema.getCurrency());
				tLJAPayGrpSet.add(tLJAPayGrpSchema);
			}
			mFinFeeMap.put(tLJAPayGrpSet, "INSERT");
		} else {
			CError.buildErr(this, "该团但分单数据丢失，请与信息部联系！");
			return false;
		}

		return true;
	}

	/*
	 * 第二步暂收核销
	 */
	private boolean dealTemfee(LCGrpContSchema tLCGrpContSchema,
			String inNewGrpContNo) {
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet(); // submit
		String tPrtno = tLCGrpContSchema.getPrtNo(); // 取得投保单号
//		tLJTempFeeDB.setOtherNo(tPrtno);
//		tLJTempFeeDB.setOperState("0");
		//tLJTempFeeSet = tLJTempFeeDB.query();
		LJTempFeeSchema tLJTempFeeSchema;
		String tempfeesql=" select * from ljtempfee where otherno='"+tPrtno+"' "
		                 +" and othernotype='4' and tempfeetype='1' and confflag='0' "
		                 +" and enteraccdate is not null and operstate='0'";
		tLJTempFeeSet=tLJTempFeeDB.executeQuery(tempfeesql);
		String tTempNo = "";
		String tCachTempNo = "";
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		LJTempFeeClassSet tLJTempFeeClassSet;
		if (tLJTempFeeSet != null && tLJTempFeeSet.size() > 0) {
			for (int i = 1; i <= tLJTempFeeSet.size(); i++) {

				tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema = tLJTempFeeSet.get(i);
				if (i == 1) {

					tEnterAccDate = tLJTempFeeSchema.getEnterAccDate();
					tCachTempNo = tLJTempFeeSchema.getTempFeeNo();
				}
				tTempNo = tLJTempFeeSchema.getTempFeeNo();
				// 交费分类处理
				if (i == 1 || !tTempNo.equals(tCachTempNo)) {
					tCachTempNo = tTempNo;
					tTempNo = tLJTempFeeSchema.getTempFeeNo();
					tLJTempFeeClassDB.setTempFeeNo(tTempNo);
					//tLJTempFeeClassDB.setOperState("0");
					tLJTempFeeClassSet = new LJTempFeeClassSet();
					tLJTempFeeClassSet = tLJTempFeeClassDB.query();
					for (int j = 1; j <= tLJTempFeeClassSet.size(); j++) {
						tLJTempFeeClassSet.get(j).setConfDate(
								PubFun.getCurrentDate());
						tLJTempFeeClassSet.get(j).setConfFlag("1");
						tLJTempFeeClassSet.get(j).setOtherNo(inNewGrpContNo);
						tLJTempFeeClassSet.get(j).setOtherNoType("1");
					}
					mFinFeeMap.put(tLJTempFeeClassSet, "UPDATE");
				}
				tLJTempFeeSchema.setConfDate(tDate);
				tLJTempFeeSchema.setConfFlag("1");
				tLJTempFeeSchema.setOtherNoType("1");
				tLJTempFeeSchema.setOtherNo(inNewGrpContNo);
				tempSumMoney = tempSumMoney + tLJTempFeeSchema.getPayMoney();
				mLJTempFeeSet.add(tLJTempFeeSchema);
			}
			mFinFeeMap.put(mLJTempFeeSet, "UPDATE");
		} else {
			CError.buildErr(this, "没有找到该团单的暂收数据！");
			return false;
		}

		return true;
	}

	/*
	 * 第一部校验是否所有个单都已经处理完毕
	 */

	private boolean checkSignCont(String tGrpContNo) {
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		String tSql = "select * From lccont where grpcontno='" + tGrpContNo
				+ "' and (uwflag='4' or uwflag='9') and appflag = '0'";
		logger.debug("check lccont:" + tSql);
		tLCContSet = tLCContDB.executeQuery(tSql);
		if (tLCContSet != null && tLCContSet.size() > 0) {
			CError.buildErr(this, "还有个单合同未签单完毕,请检查个单合同!");
			return false;
		}
		return true;
	}

	/**
	 * 更新暂交费信息
	 * 
	 * @param tmpFeeSet
	 *            LJTempFeeSet
	 * @param newGrpContNo
	 *            String
	 * @param grppolSchema
	 *            LCGrpPolSchema
	 * @return MMap
	 */
	private MMap prepareUpdateTempFeeSet(LJTempFeeSet tmpFeeSet,
			String newGrpContNo, LCGrpPolSchema grppolSchema) {
		MMap tmpMap = new MMap();
		String confDate = mCurrentDate;
		// String confTime = mCurrentTime;
		// String sql = "";
		FDate fDate = new FDate();
		Date tFirstPayDate = null;
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		for (int i = 1; i <= tmpFeeSet.size(); i++) {
			LJTempFeeSchema tLJTempFeeSchema = tmpFeeSet.get(i);
			tFirstPayDate = fDate.getDate(tLJTempFeeSchema.getPayDate());
			if (maxEnterAccDate == null
					|| maxEnterAccDate.before(tFirstPayDate)) {
				maxEnterAccDate = tFirstPayDate;
			}
			if (mFirstPayDate == null || tFirstPayDate.before(mFirstPayDate)) {
				mFirstPayDate = tFirstPayDate;
			}
			if (maxPayDate == null || maxPayDate.before(tFirstPayDate)) {
				maxPayDate = tFirstPayDate;
			}
			tLJTempFeeSchema.setAgentCode(grppolSchema.getAgentCode());
			tLJTempFeeSchema.setAgentCom(grppolSchema.getAgentCom());
			tLJTempFeeSchema.setAgentGroup(grppolSchema.getAgentGroup());
			tLJTempFeeSchema.setAgentType(grppolSchema.getAgentType());
			tLJTempFeeSchema.setConfDate(confDate);
			// tLJTempFeeSchema.setConfMakeDate(confDate);
			tLJTempFeeSchema.setConfFlag("1");
			// tLJTempFeeSchema.setConfMakeTime(confTime);
			tLJTempFeeSchema.setSaleChnl(grppolSchema.getSaleChnl());
			tLJTempFeeSchema.setPolicyCom(grppolSchema.getManageCom());
			tLJTempFeeSchema.setAPPntName(grppolSchema.getGrpName());
			tLJTempFeeSchema.setOtherNo(newGrpContNo);
			tLJTempFeeSchema.setOtherNoType("7");
			tLJTempFeeSchema.setModifyDate(PubFun.getCurrentDate());
			tLJTempFeeSchema.setModifyTime(PubFun.getCurrentTime());

			tLJTempFeeClassDB.setTempFeeNo(tLJTempFeeSchema.getTempFeeNo());
			tLJTempFeeSchema.setOperState("0");
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.query();
			if (tLJTempFeeClassSet != null && tLJTempFeeClassSet.size() > 0) {
				for (int j = 1; j <= tLJTempFeeClassSet.size(); j++) {
					LJTempFeeClassSchema tSchema = tLJTempFeeClassSet.get(j);
					// tSchema.setAccName(grppolSchema.getAcc);
					tSchema.setApproveDate(confDate);
					tSchema.setConfDate(confDate);
					tSchema.setOtherNo(newGrpContNo);
					tSchema.setOtherNoType("7");
					tSchema.setAppntName(grppolSchema.getGrpName());
					// tSchema.setConfMakeDate(confDate);
					// tSchema.setConfMakeTime(confTime);
					tSchema.setConfFlag("1");
					tSchema.setModifyDate(PubFun.getCurrentDate());
					tSchema.setModifyTime(PubFun.getCurrentTime());

				}
				tmpMap.put(tLJTempFeeClassSet, "UPDATE");
			}
		}
		tmpMap.put(PubFun.copySchemaSet(tmpFeeSet), "UPDATE");
		return tmpMap;
	}

	/**
	 * 更新集体合同号
	 * 
	 * @param grpPolSet
	 *            LCGrpPolSet
	 * @param newGrpContNo
	 *            String
	 * @param tPayNo
	 *            String
	 * @return MMap
	 */
	private MMap prepareNewGrpPolSet(LCGrpPolSet grpPolSet,
			String newGrpContNo, String tPayNo) {
		MMap tmpmap = new MMap();
		for (int i = 1; i <= grpPolSet.size(); i++) {
			// String newGrpPolNo =
			// this.getNewPolNo(StrTool.cTrim(grpPolSet.get(i).
			// getGrpPolNo()));
			// tmpmap.put("delete from lcgrppol where grppolno='" +
			// grpPolSet.get(i).getGrpPolNo() + "'", "DELETE");

			// 更新暂交费信息
			logger.debug("&&&&&:" + grpPolSet.get(i).getGrpPolNo());
			MMap APayMap = dealGrpAPay(grpPolSet.get(i), tPayNo, grpPolSet.get(
					i).getGrpPolNo(), newGrpContNo);

			//
			// grpPolSet.get(i).setPayEndDate(maxPayEndDate); //yaory

			// 从缓存中找改险种下一次交费日期
			grpPolSet.get(i).setPaytoDate(
					(String) paytoDateMap.get(grpPolSet.get(i).getRiskCode()));

			grpPolSet.get(i).setFirstPayDate(mFirstPayDate);

			// grpPolSet.get(i).setGrpPolNo(newGrpPolNo);
			// grpPolSet.get(i).setGrpContNo(newGrpContNo);
			grpPolSet.get(i).setAppFlag("1");
			grpPolSet.get(i).setState("00019999");
			tmpmap.add(APayMap);
		}
		tmpmap.put(grpPolSet, "UPDATE");
		return tmpmap;
	}

	/**
	 * 均匀集体单保费,因为前面已经有不足保费条件的过滤，因此这里的暂交费总和肯定不小于应交保费
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @param moreGrpPolSet
	 *            LCGrpPolSet
	 * @param lackGrpPolSet
	 *            LCGrpPolSet
	 * @param inNewGrpContNo
	 *            String
	 * @return MMap
	 */
	private MMap balanceFee(LCGrpContSchema tLCGrpContSchema,
			LCGrpPolSet moreGrpPolSet, LCGrpPolSet lackGrpPolSet,
			String inNewGrpContNo) {
		MMap tmpMap = new MMap();
		if (lackGrpPolSet.size() <= 0) {
			logger.debug("没有少交保费的保单");
		}
		if (moreGrpPolSet.size() <= 0) {
			logger.debug("没有保费多余的保单");
		}
		int redundantPos = 1;
		int lackPos = 1;
		double left = 0.000;
		LCGrpPolSchema lackGrpPol = null;
		LCGrpPolSchema redundantGrpPolSchema = null;
		if (lackGrpPolSet.size() > 0 && moreGrpPolSet.size() > 0) {
			// String tPaySerialNo =
			// PubFun1.CreateMaxNo("PAYNO",PubFun.getNoLimit(mGlobalInput.ComCode));

			double adjustMoney = 0.000; // 用以调配的money

			// 以富余补不足
			while (lackPos <= lackGrpPolSet.size()
					&& redundantPos <= moreGrpPolSet.size()) {
				if (left >= 0.0) {
					lackGrpPol = lackGrpPolSet.get(lackPos);
				}
				if (left <= 0.0) {
					redundantGrpPolSchema = moreGrpPolSet.get(redundantPos);
				}

				double lackmoney = lackGrpPol.getDif();
				double redundantmoney = redundantGrpPolSchema.getDif();

				left = redundantmoney + lackmoney;
				// logger.debug("adjustMoney:" + adjustMoney);

				if (left >= 0.0) { // 富余足以加给不足
					adjustMoney = lackmoney;
					tmpMap.add(createTempFee(redundantGrpPolSchema, lackGrpPol,
							adjustMoney, inNewGrpContNo));
					lackGrpPol.setDif(0);
					redundantGrpPolSchema.setDif(redundantGrpPolSchema.getDif()
							+ adjustMoney);
					lackPos++;
				} else { // 富余不够加给不足
					// 把富足的全部分给不足
					adjustMoney = redundantmoney;
					tmpMap.add(createTempFee(redundantGrpPolSchema, lackGrpPol,
							adjustMoney, inNewGrpContNo));
					redundantGrpPolSchema.setDif(0);
					lackGrpPol.setDif(lackGrpPol.getDif() + adjustMoney);
					redundantPos++;
				}

			}
		}
		// 还有少交保费的保单,要生成催收?
		if (lackPos < lackGrpPolSet.size()) {
			logger.debug("还有少交保费的保单,要生成催收");
			return null;
		}

		// 还有有保费多余的保单,要生成退费
		if (redundantPos > 0 && redundantPos <= moreGrpPolSet.size()) {
			// LCGrpPolSet withdrawLCGrpPolSet = new LCGrpPolSet();
			//
			logger.debug("还有有保费多余的保单,要生成退费");
			double SumDiff = 0.000;
			for (int t = redundantPos; t <= moreGrpPolSet.size(); t++) {
				redundantGrpPolSchema = moreGrpPolSet.get(t);
				SumDiff += redundantGrpPolSchema.getDif();
				redundantGrpPolSchema.setDif(0);
				// withdrawLCGrpPolSet.add(redundantGrpPolSchema);
				// NewGrpPolFeeWithdrawBL tNewGrpPolFeeWithdrawBL = new
				// NewGrpPolFeeWithdrawBL();
				// VData inputData = new VData();
				// inputData.add(withdrawLCGrpPolSet);
				// inputData.add(this.mGlobalInput);
				// VData withdraw = tNewGrpPolFeeWithdrawBL.submitDataAllNew(
				// inputData);
				// if (withdraw == null)
				// {
				// CError.buildErr(this, "暂交费退费发生错误!");
				// return null;
				// }
				//
				// LJAGetSet getSet = (LJAGetSet)
				// withdraw.getObjectByObjectName(
				// "LJAGetSet", 0);
				// this.replaceOtherNo(getSet, inNewGrpContNo);
				// tmpMap.put(getSet, "INSERT");
				// LJAGetOtherSet getOtherSet = (LJAGetOtherSet) withdraw.
				// getObjectByObjectName(
				// "LJAGetOtherSet", 0);
				// this.replaceOtherNo(getOtherSet, inNewGrpContNo);
				// tmpMap.put(getOtherSet, "INSERT");
				// LOPRTManagerSet tLOPRTManagerSet = (LOPRTManagerSet)
				// withdraw.
				// getObjectByObjectName("LOPRTManagerSet", 0);
				// tmpMap.put(tLOPRTManagerSet, "INSERT");

			}
			tLCGrpContSchema.setDif(SumDiff);
		}

		return tmpMap;
	}

	private static void replaceOtherNo(LJAGetOtherSet otherSet,
			String newOtherNo) {
		for (int i = 1; i <= otherSet.size(); i++) {
			otherSet.get(i).setOtherNo(newOtherNo);
			// otherSet.get(i).setConfDate( null );
		}
	}

	private void replaceOtherNo(LJAGetSet agetSet, String newOtherNo) {
		for (int i = 1; i <= agetSet.size(); i++) {
			agetSet.get(i).setOtherNo(newOtherNo);
			// agetSet.get(i).setConfDate( mCurrentDate );
			agetSet.get(i).setShouldDate(mCurrentDate);
		}
	}

	/**
	 * 根据保单生成一张新的暂交费表
	 * 
	 * @param grpmorePolSchema
	 *            LCGrpPolSchema
	 * @param grplackPolSchema
	 *            LCGrpPolSchema
	 * @param money
	 *            double
	 * @param inNewGrpContNo
	 *            String
	 * @return MMap
	 */
	private MMap createTempFee(LCGrpPolSchema grpmorePolSchema,
			LCGrpPolSchema grplackPolSchema, double money, String inNewGrpContNo) {
		MMap tmpMap = new MMap();
		LJTempFeeSchema srcTempFeeSchema = (LJTempFeeSchema) this
				.getTempFee(grpmorePolSchema.getRiskCode());
		if (srcTempFeeSchema == null) {
			CError.buildErr(this, "没有查找到可用于借的暂交费");
			return null;
		}
		String confDate = mCurrentDate;
		String confTime = mCurrentTime;
		PubFun.setPrecision(money, "0.00");
		if (money < 0) {
			money = -money;
		}

		// 创建借的科目
		LJTempFeeSchema tjieSchema = new LJTempFeeSchema();
		tjieSchema.setSchema(srcTempFeeSchema);
		tjieSchema.setTempFeeType("9");
		tjieSchema.setRiskCode(grplackPolSchema.getRiskCode());
		tjieSchema.setPayMoney(money);
		tjieSchema.setMakeDate(confDate);
		tjieSchema.setMakeTime(confTime);
		tjieSchema.setModifyDate(PubFun.getCurrentDate());
		tjieSchema.setModifyTime(PubFun.getCurrentTime());

		// 创建贷的科目
		LJTempFeeSchema tdaiSchema = new LJTempFeeSchema();
		tdaiSchema.setSchema(srcTempFeeSchema);
		tdaiSchema.setTempFeeType("8");
		tdaiSchema.setRiskCode(grplackPolSchema.getRiskCode());
		tdaiSchema.setPayMoney(money);
		tdaiSchema.setMakeDate(confDate);
		tdaiSchema.setMakeTime(confTime);
		tdaiSchema.setModifyDate(PubFun.getCurrentDate());
		tdaiSchema.setModifyTime(PubFun.getCurrentTime());
		// 缓存
		if (this.getTempFee(grplackPolSchema.getRiskCode()) == null) {
			this.cacheTempFee(grplackPolSchema.getRiskCode(), tdaiSchema);
		}
		tmpMap.put(tjieSchema, "INSERT");
		tmpMap.put(tdaiSchema, "INSERT");
		return tmpMap;
	}

	private static LCGrpPolSchema queryGrpPol(String grpPolNo) {
		LCGrpPolDB grpPolDB = new LCGrpPolDB();
		grpPolDB.setGrpPolNo(grpPolNo);
		if (!grpPolDB.getInfo()) {
			return null;
		}
		return grpPolDB.getSchema();
	}

	/**
	 * 查询暂交费
	 * 
	 * @param prtNo
	 *            String
	 * @param riskcode
	 *            String
	 * @return LJTempFeeSet
	 */
	private static LJTempFeeSet queryTempFeeSet(String prtNo, String riskcode) {
		LJTempFeeSet tLJTempFeeSet;
		LJTempFeeSchema tLJTempFeeSchema = null;
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		// tLJTempFeeSchema = new LJTempFeeSchema();
		// tLJTempFeeSchema.setOtherNo(prtNo);
		// tLJTempFeeSchema.setOtherNoType("4");
		// tLJTempFeeSchema.setConfFlag("0");
		// tLJTempFeeSchema.setRiskCode(riskcode);
		// tLJTempFeeDB.setSchema(tLJTempFeeSchema);
		String sql = "select * from ljtempfee where otherno='"
				+ prtNo
				+ "' and OtherNoType='4' and riskcode='"
				+ riskcode
				+ "' and confflag='0' and enteraccdate is not null and enteraccdate!='3000-1-1' and operstate='0'";
		tLJTempFeeSet = tLJTempFeeDB.executeQuery(sql);
		// tLJTempFeeSet = tLJTempFeeDB.query();
		if (tLJTempFeeDB.mErrors.needDealError()) {
			CError.buildErr("LCGrpContSignBL", "queryTempFee",
					tLJTempFeeDB.mErrors);
			logger.debug("errrrrrrrrrr:"
					+ tLJTempFeeDB.mErrors.getErrContent());
			return null;
		}
		return tLJTempFeeSet;
	}

	/**
	 * 暂交费交费求和
	 * 
	 * @param tmpFeeSet
	 *            LJTempFeeSet
	 * @return double
	 */
	private static double sumPayMoney(LJTempFeeSet tmpFeeSet) {
		double sumPrem = 0.000;
		for (int i = 1; i <= tmpFeeSet.size(); i++) {
			sumPrem += tmpFeeSet.get(i).getPayMoney();
		}
		return sumPrem;

	}

	/**
	 * 更新团单合同换号
	 * 
	 * @param inNewGrpContNo
	 *            String
	 * @param oldGrpContNo
	 *            String
	 * @return MMap
	 */
	private static MMap prepareOtherUpdateGrpCont(String inNewGrpContNo,
			String oldGrpContNo) {
		String condition = " GrpContNo='" + inNewGrpContNo + "'";
		String wherepart = " grpcontno='" + oldGrpContNo + "'";
		// 涉及到团单合同号的表
		//2010-3-5  增加理赔责任控制的两张表
		String[] grpCont = { "LCGrpCont", "LCGrpAppnt", "LCGrpPol", "LCCont",
				"LCContPlan", "LCContPlanRisk", "LCContPlanDutyParam",
				"LCGeneral", "LCInsured", "LCPol", "LCCGrpSpec",
				"LCPENoticeItem", "LCPENotice", "LLAccount", "LCGrpFee",
				"LCGrpFeeParam", "LCAscriptionRuleParams",
				"LCAscriptionRuleFactory", "lcprem", "lcget", "lldutyctrl",
				"lldutyctrlindex","lcgcuwmaster","lcgcuwsub","lcuwmaster","lcuwsub","lcgrpissuepol"};
		Vector tmpVec = PubFun.formUpdateSql(grpCont, condition, wherepart);
		Vector sqlVec = new Vector();
		sqlVec.addAll(tmpVec);

		// //加个单签单时间
		// String[] signDateTime = {"LCCont"};
		// condition = condition +" ,signdate = '" + mCurrentDate +"'"
		// + ",signTime = '" + mCurrentTime +"'";
		// Vector contVec = PubFun.formUpdateSql( signDateTime,
		// condition,wherepart);
		// sqlVec.addAll( contVec );
		MMap tmpMap = new MMap();
		tmpMap.add(LCGrpContSignBL.getUpdateSql(sqlVec));
		return tmpMap;
	}

	/**
	 * 准备只需要更新团单合同号,集体单合同号的sql
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @param inNewGrpContNo
	 *            String
	 * @param lpGrpPolNo
	 *            String
	 * @param newGrpPolNo
	 *            String
	 * @return MMap
	 */
	private static MMap prepareOtherGrpPolSql(LCGrpContSchema tLCGrpContSchema,
			String inNewGrpContNo, String lpGrpPolNo, String newGrpPolNo) {

		// 更新团体合同,集体险种保单表相关
		String[] GrpPolContTables = {};
		// 集体险种保单表相关
		// String[] GrpPolTables =
		// {"LCInsureAccTrace"};
		Vector sqlVec = new Vector();

		// 其余表的更新sql
		StringBuffer condition = new StringBuffer(64);
		condition.append(" GrpContNo='");
		condition.append(inNewGrpContNo);
		condition.append("',GrpPolNo='");
		condition.append(newGrpPolNo);
		condition.append("'");
		// String condition = " GrpContNo='" + inNewGrpContNo + "',GrpPolNo='" +
		// newGrpPolNo + "'";
		StringBuffer wherepart = new StringBuffer();
		wherepart.append(" grppolno='");
		wherepart.append(lpGrpPolNo);
		wherepart.append("'");
		// String wherepart = " grppolno='" + lpGrpPolNo + "'";
		Vector VecContPol = PubFun.formUpdateSql(GrpPolContTables, condition
				.toString(), wherepart.toString());
		sqlVec.addAll(VecContPol);

		// //加个单签单时间
		// String[] signDateTime =
		// {"LCPol"};
		// String conditionsign = condition + " ,signdate = '" + mCurrentDate +
		// "'"
		// + ",signTime = '" + mCurrentTime + "'";
		// Vector contVec = PubFun.formUpdateSql(signDateTime, conditionsign,
		// wherepart);
		// sqlVec.addAll(contVec);
		//
		// //加账户相关
		// String[] AccTable
		// ={"LCInsureAcc","LCInsureAccClass","LCInsureAccFee","LCInsureAccClassFee"};
		// String conditionAcc =condition +",AccFoundDate='" + mCurrentDate +"'"
		// +",AccFoundTime='" + mCurrentTime+"'"
		// +",BalaDate='"+ mCurrentDate +"'"
		// +",BalaTime ='" + mCurrentTime +"'"
		// ;
		// Vector AccVec = PubFun.formUpdateSql(AccTable, conditionAcc,
		// wherepart);
		// sqlVec.addAll( AccVec );
		// String[] AccTraceTable={"LCInsureAccTrace"};
		// String conditionTrace = condition +",paydate='"+ mCurrentDate +"'";
		// Vector AccVecTrace = PubFun.formUpdateSql(AccTraceTable,
		// conditionTrace,
		// wherepart);
		// sqlVec.addAll( AccVecTrace );

		// sqlVec.add(sql1 );

		return getUpdateSql(sqlVec);
	}

	/**
	 * sql转换
	 * 
	 * @param sql
	 *            Vector
	 * @return MMap
	 */
	private static MMap getUpdateSql(Vector sql) {
		MMap tmpMap = new MMap();
		for (int i = 0; i < sql.size(); i++) {
			tmpMap.put((String) sql.get(i), "INSERT");
		}
		return tmpMap;

	}

	// /**
	// * 获取暂交费的更新信息
	// * 输出：如果发生错误则返回false,否则返回true
	// */
	// private MMap dealTempFee(LCGrpPolSchema tLCGrpPolSchema,
	// String tNewGrpContNo)
	// {
	//
	// MMap tmpMap = new MMap();
	// VData tReturn = new VData();
	// String tOldGrpPolNo = tLCGrpPolSchema.getGrpContNo();
	//
	// String confDate = PubFun.getCurrentDate();
	// String confTime = PubFun.getCurrentTime();
	//
	// LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
	// LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
	//
	// // if (mPolType.equals("3"))
	// // { // 合同下的集体的投保单
	// // tReturn.add(tLJTempFeeSet);
	// // tReturn.add(tLJTempFeeClassSet);
	// // }
	// // if (mPolType.equals("2"))
	// // {
	// // 暂交费信息
	// LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
	// tLJTempFeeDB.setOtherNo(tOldGrpPolNo);
	// tLJTempFeeDB.setOtherNoType("1");
	// tLJTempFeeDB.setTempFeeType("1");
	// tLJTempFeeDB.setConfFlag("0");
	//
	// tLJTempFeeSet = tLJTempFeeDB.query();
	// if (tLJTempFeeDB.mErrors.needDealError())
	// {
	// CError.buildErr(this, "LJTempFee表取数失败!");
	// return null;
	// }
	// //houzm增加的部分：将暂加费纪录类型为银行扣款的也查询出来
	// LJTempFeeDB tempLJTempFeeDB = new LJTempFeeDB();
	// tempLJTempFeeDB.setOtherNo(tLCGrpPolSchema.getPrtNo()); //印刷号
	// tempLJTempFeeDB.setOtherNoType("4"); //其它号码类型为印刷号
	// //tempLJTempFeeDB.setTempFeeType( "5" ); //暂交费类型为银行扣款
	// tempLJTempFeeDB.setRiskCode(tLCGrpPolSchema.getRiskCode());
	// tempLJTempFeeDB.setConfFlag("0"); //核销标记为假
	// LJTempFeeSet tempLJTempFeeSet = tempLJTempFeeDB.query();
	// if (tempLJTempFeeDB.mErrors.needDealError())
	// {
	// CError.buildErr(this, "财务信息取数失败表取数失败!");
	// return null;
	//
	// }
	// tLJTempFeeSet.add(tempLJTempFeeSet); //将后续查询到的纪录存放到set
	// tmpMap.put(PubFun.copySchemaSet(tLJTempFeeSet), "DELETE");
	// //以上为houzm添加
	// int n = tLJTempFeeSet.size();
	// for (int i = 1; i <= n; i++)
	// {
	// LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(i);
	//
	// LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
	// tLJTempFeeClassDB.setTempFeeNo(tLJTempFeeSchema.getTempFeeNo());
	// tLJTempFeeClassSet = tLJTempFeeClassDB.query();
	// if (tLJTempFeeClassDB.mErrors.needDealError())
	// {
	// CError.buildErr(this, "LJTempFeeClass表取数失败!");
	// continue;
	// } // end of if
	//
	// int m = tLJTempFeeClassSet.size();
	// if (m > 0)
	// {
	// tmpMap.put(PubFun.copySchemaSet(tLJTempFeeClassSet), "DELETE");
	// for (int j = 1; j <= m; j++)
	// {
	// LJTempFeeClassSchema tLJTempFeeClassSchema =
	// tLJTempFeeClassSet.get(j);
	// tLJTempFeeClassSchema.setConfDate(confDate);
	// tLJTempFeeClassSchema.setConfFlag("1");
	// tLJTempFeeClassSchema.setModifyDate(confDate);
	// tLJTempFeeClassSchema.setModifyTime(confTime);
	//
	// tLJTempFeeClassSet.set(j, tLJTempFeeClassSchema);
	// } // end of for
	//
	// tLJTempFeeSchema.setOtherNo(tNewGrpContNo);
	// tLJTempFeeSchema.setOtherNoType("1");
	// tLJTempFeeSchema.setConfDate(confDate);
	// tLJTempFeeSchema.setConfFlag("1");
	// tLJTempFeeSchema.setModifyDate(confDate);
	// tLJTempFeeSchema.setModifyTime(confTime);
	//
	// tLJTempFeeSet.set(i, tLJTempFeeSchema);
	//
	// }
	//
	// } // end of for
	// // } // end of if
	// tmpMap.put(tLJTempFeeSet, "INSERT");
	//
	// return tmpMap;
	// }

	/**
	 * 处理集体保单财务实交信息
	 * 
	 * @param tLCGrpPolSchema
	 *            LCGrpPolSchema
	 * @param tPaySerialNo
	 *            String
	 * @param newGrpPolNo
	 *            String
	 * @param inNewGrpContNo
	 *            String
	 * @return MMap
	 */
	private MMap dealGrpAPay(LCGrpPolSchema tLCGrpPolSchema,
			String tPaySerialNo, String newGrpPolNo, String inNewGrpContNo) {
		// LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet ();
		// LCGrpPolSchema tGrpPolSchema = null;
		LJAPayGrpSet PayGrpSet = new LJAPayGrpSet();
		// FDate fDate = new FDate();
		MMap tmpMap = new MMap();
		String confDate = mCurrentDate;
		String confTime = mCurrentTime;
		// String tPaySerialNo =
		// PubFun1.CreateMaxNo("PAYNO",PubFun.getNoLimit(mGlobalInput.ManageCom));
		// 集体保单查询
		// LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		// tLCGrpPolDB.setGrpContNo( tLCGrpContSchema.getGrpContNo());
		// tLCGrpPolSet = tLCGrpPolDB.query();
		// if ( tLCGrpPolSet == null )
		// return null;
		LCPremSet tPremSet = new LCPremSet();
		// LCPremSchema tPremSchema = null;
		LCPolSet tPolSet = new LCPolSet();
		LCPolSchema tPolSchema = null;
		LCPolDB tPolDB = new LCPolDB();
		LCPremDB tPremDB = new LCPremDB();
		LJAPayGrpSchema PayGrpSchema = null;

		FDate fdate = new FDate();
		Date tdate;

		tPolDB.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
		logger.debug("产生集体交费时的分单号：" + tLCGrpPolSchema.getGrpPolNo());
		tPolDB.setAppFlag("1"); // 已签单
		tPolSet = new LCPolSet();
		tPolSet = tPolDB.query();
		if (tPolSet == null) {
			return null;
		}
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		LJTempFeeSchema tempFeeSchema = (LJTempFeeSchema) this
				.getTempFee(tLCGrpPolSchema.getRiskCode());
		if (tempFeeSchema == null) {
			CError.buildErr(this, "没有暂交费信息!");
			return null;
		}
		for (int j = 1; j <= tPolSet.size(); j++) {
			tPolSchema = tPolSet.get(j);
			if (j == 1) {
				LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
				LMRiskAppSet mLMRiskAppSet = new LMRiskAppSet();
				mLMRiskAppDB.setRiskType("H");
				mLMRiskAppDB.setHealthType("1");
				mLMRiskAppDB.setRiskCode(tPolSchema.getRiskCode());
				mLMRiskAppSet = mLMRiskAppDB.query();
				if(mLMRiskAppSet.size()>0){
					mPayType = "TM";
				}
				else{
					mPayType = "ZC";
				}
				// 缓存下一次交费日期
				paytoDateMap.put(tPolSchema.getRiskCode(), tPolSchema
						.getPaytoDate());
				// 在第一个节点处初始化
				PayGrpSchema = new LJAPayGrpSchema();
				PayGrpSchema.setGrpPolNo(newGrpPolNo);
				PayGrpSchema.setRiskCode(tPolSchema.getRiskCode());
				PayGrpSchema.setSumActuPayMoney(0);
				PayGrpSchema.setSumDuePayMoney(0);
				PayGrpSchema.setPayType(mPayType);
				PayGrpSchema.setAgentCom(tLCGrpPolSchema.getAgentCom());
				PayGrpSchema.setAgentType(tLCGrpPolSchema.getAgentType());
				PayGrpSchema.setAgentGroup(tLCGrpPolSchema.getAgentGroup());
				PayGrpSchema.setAppntNo(tLCGrpPolSchema.getCustomerNo());
				PayGrpSchema.setApproveCode(mGlobalInput.Operator);
				PayGrpSchema.setApproveDate(confDate);
				PayGrpSchema.setPayCount(1);
				PayGrpSchema.setPayNo(tPaySerialNo);
				PayGrpSchema.setPayDate(tempFeeSchema.getPayDate());
				PayGrpSchema.setEnterAccDate(tempFeeSchema.getEnterAccDate());
				PayGrpSchema.setLastPayToDate(tempFeeSchema.getPayDate());
				// 下一次交费日期
				PayGrpSchema.setCurPayToDate(tPolSchema.getPaytoDate());
				PayGrpSchema.setApproveTime(confTime);
				PayGrpSchema.setConfDate(confDate);
				PayGrpSchema.setGrpContNo(inNewGrpContNo);
				PayGrpSchema.setMakeDate(confDate);
				PayGrpSchema.setMakeTime(confTime);
				PayGrpSchema.setModifyDate(PubFun.getCurrentDate());
				PayGrpSchema.setModifyTime(PubFun.getCurrentTime());
				PayGrpSchema.setOperator(mGlobalInput.Operator);
				PayGrpSchema.setManageCom(tLCGrpPolSchema.getManageCom());
				PayGrpSchema.setAgentCode(tPolSchema.getAgentCode());

			}
			// 实收信息,初始化基本信息
			tPolSchema = tPolSet.get(j);
			// 个人保单保费项查询
			tPremDB.setPolNo(tPolSchema.getPolNo());
			tPremSet = tPremDB.query();
			if (tPremSet == null) {
				continue;
			}
			tdate = fdate.getDate(tPolSchema.getPayEndDate());
			if (maxPayEndDate == null || maxPayEndDate.before(tdate)) {
				maxPayEndDate = tdate;
			}
			int m = tPremSet.size();
			if (m > 0) {
				for (int k = 1; k <= m; k++) {

					LCPremSchema tLCPremSchema = tPremSet.get(k);
					LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
					LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
					LMRiskAppSet mLMRiskAppSet = new LMRiskAppSet();
					mLMRiskAppDB.setRiskType("H");
					mLMRiskAppDB.setHealthType("1");
					mLMRiskAppDB.setRiskCode(tPolSchema.getRiskCode());
					mLMRiskAppSet = mLMRiskAppDB.query();
					if(mLMRiskAppSet.size()>0){
						mPayType = "TM";
					}
					else{
						mPayType = "ZC";
					}
					tLJAPayPersonSchema.setPolNo(tPolSchema.getPolNo());
					tLJAPayPersonSchema.setPayCount(1);
					tLJAPayPersonSchema.setGrpPolNo(newGrpPolNo);
					tLJAPayPersonSchema.setContNo(tPolSchema.getContNo());
					tLJAPayPersonSchema.setGrpContNo(inNewGrpContNo);
					tLJAPayPersonSchema.setAppntNo(tPolSchema.getAppntNo());
					tLJAPayPersonSchema.setPayNo(tPaySerialNo);
					tLJAPayPersonSchema.setPayAimClass("1");
					tLJAPayPersonSchema
							.setDutyCode(tLCPremSchema.getDutyCode());
					tLJAPayPersonSchema.setPayPlanCode(tLCPremSchema
							.getPayPlanCode());
					tLJAPayPersonSchema.setSumDuePayMoney(tLCPremSchema
							.getPrem());
					tLJAPayPersonSchema.setSumActuPayMoney(tLCPremSchema
							.getPrem());
					tLJAPayPersonSchema.setPayIntv(tLCPremSchema.getPayIntv());
					tLJAPayPersonSchema.setPayDate(tempFeeSchema.getPayDate());
					tLJAPayPersonSchema.setPayType(mPayType);
					tLJAPayPersonSchema.setEnterAccDate(tempFeeSchema
							.getEnterAccDate());
					tLJAPayPersonSchema.setConfDate(confDate);
					tLJAPayPersonSchema.setLastPayToDate("1899-12-31");
					tLJAPayPersonSchema.setCurPayToDate(tLCPremSchema
							.getPaytoDate());
					tLJAPayPersonSchema.setOperator(mGlobalInput.Operator);
					tLJAPayPersonSchema.setApproveCode(mGlobalInput.Operator);
					tLJAPayPersonSchema.setApproveDate(confDate);
					tLJAPayPersonSchema.setApproveTime(confTime);

					tLJAPayPersonSchema.setMakeDate(confDate);
					tLJAPayPersonSchema.setMakeTime(confTime);
					tLJAPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
					tLJAPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
					tLJAPayPersonSchema.setManageCom(tPolSchema.getManageCom());
					tLJAPayPersonSchema.setAgentCom(tPolSchema.getAgentCom());
					tLJAPayPersonSchema.setAgentType(tPolSchema.getAgentType());
					tLJAPayPersonSchema.setRiskCode(tPolSchema.getRiskCode());
					tLJAPayPersonSchema.setAgentCode(tPolSchema.getAgentCode());
					tLJAPayPersonSchema.setAgentGroup(tPolSchema
							.getAgentGroup());
					tLJAPayPersonSchema.setCurrency(tPolSchema.getCurrency());

					tLJAPayPersonSet.add(tLJAPayPersonSchema);
				}

			}
			tmpMap.put(tLJAPayPersonSet, "INSERT");
			// 集体保单实交信息
			fillLJAPayGrp(PayGrpSchema, tPremSet);
		}

		// 团单总单保费和
		sumGrpContPrem += PayGrpSchema.getSumActuPayMoney();
		sumGrpContStandPrem += PayGrpSchema.getSumDuePayMoney();
		PayGrpSet.add(PayGrpSchema);

		// }
		tmpMap.put(PayGrpSet, "INSERT");
		// 更新japaygrp
		// String sql ="update ljapaygrp set SumDuePayMoney=(select
		// sum(t.SumDuePayMoney) "
		// +" from ljapayperson t where t.payno= ljapaygrp.payno and
		// t.GrpPolNo=ljapaygrp.GrpPolNo)"
		// +" where payno='"+ tPaySerialNo +"'";
		// String sql1 ="update ljapaygrp set SumActuPayMoney=(select
		// sum(t.SumActuPayMoney) "
		// +" from ljapayperson t where t.payno= ljapaygrp.payno and
		// t.GrpPolNo=ljapaygrp.GrpPolNo)"
		// +" where payno='"+ tPaySerialNo +"'";
		// tmpMap.put(sql,"UPDATE");
		// tmpMap.put(sql1,"UPDATE");
		return tmpMap;
	}

	/**
	 * 总实收
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @param newGrpContNo
	 *            String
	 * @param tPayNo
	 *            String
	 * @return MMap
	 */
	private MMap prepareLJAPAY(LCGrpContSchema tLCGrpContSchema,
			String newGrpContNo, String tPayNo) {
		String confDate = mCurrentDate;
		String confTime = mCurrentTime;
		MMap tmpMap = new MMap();
		// 生成流水号
		LJAPaySchema tLJAPaySchema = new LJAPaySchema();
		tLJAPaySchema.setPayNo(tPayNo);
		tLJAPaySchema.setIncomeNo(newGrpContNo);
		tLJAPaySchema.setIncomeType("1");
		tLJAPaySchema.setAppntNo(tLCGrpContSchema.getAppntNo());
		tLJAPaySchema.setSumActuPayMoney(sumGrpContPrem);
		tLJAPaySchema.setPayDate(maxPayDate);
		tLJAPaySchema.setEnterAccDate(PubFun.getCurrentDate());
		tLJAPaySchema.setConfDate(PubFun.getCurrentDate());
		tLJAPaySchema.setApproveCode(mGlobalInput.Operator);
		tLJAPaySchema.setApproveDate(confDate);
		tLJAPaySchema.setSerialNo(tPayNo);
		tLJAPaySchema.setOperator(mGlobalInput.Operator);
		tLJAPaySchema.setMakeDate(confDate);
		tLJAPaySchema.setMakeTime(confTime);
		tLJAPaySchema.setModifyDate(PubFun.getCurrentDate());
		tLJAPaySchema.setModifyTime(PubFun.getCurrentTime());
		tLJAPaySchema.setStartPayDate(mFirstPayDate);
		tLJAPaySchema.setManageCom(tLCGrpContSchema.getManageCom());
		tLJAPaySchema.setAgentCom(tLCGrpContSchema.getAgentCom());
		tLJAPaySchema.setAgentType(tLCGrpContSchema.getAgentType());
		tLJAPaySchema.setAgentCode(tLCGrpContSchema.getAgentCode());
		tLJAPaySchema.setAgentGroup(tLCGrpContSchema.getAgentGroup());
		tLJAPaySchema.setAgentType(tLCGrpContSchema.getAgentType());
		tLJAPaySchema.setBankAccNo(tLCGrpContSchema.getBankAccNo());
		tLJAPaySchema.setBankCode(tLCGrpContSchema.getBankCode());
		tLJAPaySchema.setAccName(tLCGrpContSchema.getAccName());
		tLJAPaySchema.setOtherNoType("4");
		tLJAPaySchema.setOtherNo(newGrpContNo);
		tLJAPaySchema.setPayTypeFlag(tLCGrpContSchema.getPayMode()); // ?
		tmpMap.put(tLJAPaySchema, "INSERT");
		// 求收费总额
		// String updatesql ="update ljapay set SumActuPayMoney=(select
		// sum(t.SumActuPayMoney) from ljapayperson t where
		// t.payno=ljapay.payno)"
		// +" where payno='"+ tPayNo+ "'";
		// tmpMap.put( updatesql, "UPDATE");
		return tmpMap;
	}

	/**
	 * 根据传入保费项，求集体保单总应交和总实交
	 * 
	 * @param tPayGrpSchema
	 *            LJAPayGrpSchema
	 * @param tPremSet
	 *            LCPremSet
	 */
	private static void fillLJAPayGrp(LJAPayGrpSchema tPayGrpSchema,
			LCPremSet tPremSet) {
		// 求保费
		double sumPrem = 0.00;
		double sumStandPrem = 0.00;
		LCPremSchema tPremSchema = null;
		for (int t = 1; t <= tPremSet.size(); t++) {
			tPremSchema = tPremSet.get(t);
			sumPrem += tPremSchema.getPrem();
			sumStandPrem += tPremSchema.getStandPrem();
		}
		tPayGrpSchema.setSumActuPayMoney(PubFun.setPrecision(tPayGrpSchema
				.getSumActuPayMoney()
				+ sumPrem, "0.00"));
		tPayGrpSchema.setSumDuePayMoney(PubFun.setPrecision(tPayGrpSchema
				.getSumDuePayMoney()
				+ sumStandPrem, "0.00"));

	}

	/**
	 * 执行
	 * 
	 * @param conn
	 *            Connection
	 * @param sql
	 *            String
	 * @return double
	 */
	private static double execSumQuery(Connection conn, String sql) {
		PreparedStatement st = null;
		// Connection conn = null;
		ResultSet rs = null;
		try {
			// conn = DBConnPool.getConnection();
			if (conn == null) {
				return 0;
			}
			st = conn.prepareStatement(sql);
			if (st == null) {
				return 0;
			}
			rs = st.executeQuery();
			if (rs.next()) {
				return rs.getDouble(1);
			}
			return 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		} finally {
			try {
				rs.close();
				st.close();
				
			} catch (Exception e) {
			}

		}
	}

	/**
	 * 签单校验
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return boolean
	 */
	private static boolean checkGrpCont(LCGrpContSchema tLCGrpContSchema) {

		// 复核是否通过
		if (!"4".equals(tLCGrpContSchema.getApproveFlag())
				&& !"9".equals(tLCGrpContSchema.getApproveFlag())) {
			CError.buildErr("LCGrpContSignBL", "团单合同("
					+ tLCGrpContSchema.getGrpContNo() + ")未复核通过;");
			return false;
		}
		// 核保是否通过
		if (!"4".equals(tLCGrpContSchema.getUWFlag())
				&& !"9".equals(tLCGrpContSchema.getUWFlag())) {
			CError.buildErr("LCGrpContSignBL", "团单合同("
					+ tLCGrpContSchema.getGrpContNo() + ")未核保通过;");
			return false;
		}

		return true;

	}

	/**
	 * 团单暂交费查询比较
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return int
	 */
	private int CheckTempFee(LCGrpContSchema tLCGrpContSchema) {
		// 暂交费是否交够
		double sumPrem = 0.00;
		double sumTmpFee = 0.00;
		Connection conn = null;
		try {
			conn = DBConnPool.getConnection();
			if (conn == null) {
				CError.buildErr(this, "用尽的数据库连接资源,稍后再试");
				return this.DB_ERROR;
			}
			String sql0 = "select count(*)  from ljtempfee where TempFeeType in ('0','1') and othernotype='4' and otherno='"
					+ tLCGrpContSchema.getGrpContNo()
					+ "' and ConfFlag='0' and EnterAccDate is  null  and OperState='0'";
			double sumcount = LCGrpContSignBL.execSumQuery(conn, sql0);
			if (sumcount > 0) {
				return -1; // 存在未到帐的数据
			} else {
				//2008-09-16 团单既可以以表定费率折扣的形式或约定保额保费的形式录入保额保费，所以所交保费的总额应该为sum(Prem)，既实际保费，而不是StandPrem应交保费，这里将sql中的sum(standPrem)改为sum(prem)
				String sql = "select sum(prem) as sumprem from lcpol where grpcontno='"
						+ tLCGrpContSchema.getGrpContNo()
						+ "' and uwflag in ('4','9','z') group by grpcontno"; // in
																				// ('4','9')
																				// 只要不是拒保
				sumPrem = LCGrpContSignBL.execSumQuery(conn, sql);
				logger.debug(sql);
				// 判断是否有外交的管理费 只队139险种进行处理(139,151)
				LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
				LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
				String tsql = "select * From lcgrppol where riskcode in (select riskcode from lmriskapp where risktype6 in ('1')) and grpcontno='"
						+ tLCGrpContSchema.getGrpContNo() + "'";
				tLCGrpPolSet = tLCGrpPolDB.executeQuery(tsql);
				String FeeCalMode = "";
				String tRiskCode = "";
				String tDutyCode = "";
				if (tLCGrpPolSet != null && tLCGrpPolSet.size() > 0) {
					tRiskCode = tLCGrpPolSet.get(1).getRiskCode(); // 不是139，就是151，但只能是其中一个
					tDutyCode = tRiskCode.substring(1, 3) + "0002";
					LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
					LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet(); // 390002,510002
					tsql = "select * From lcgrpfee where riskcode in (select riskcode from lmriskapp where risktype6 in ('1')) and grpcontno='"
							+ tLCGrpContSchema.getGrpContNo() + "'";
					logger.debug(tsql);
					tLCGrpFeeSet = tLCGrpFeeDB.executeQuery(tsql);
					if (tLCGrpFeeSet != null && tLCGrpFeeSet.size() > 0) {
						FeeCalMode = tLCGrpFeeSet.get(1).getFeeCalMode();
						logger.debug(FeeCalMode);
						if (FeeCalMode.equals("03") || FeeCalMode.equals("04")
								|| FeeCalMode.equals("07")) {
							outpayflag = "1"; // 外缴标记
						}
					} else {
						outpayflag = "1"; // 外缴标记
						FeeCalMode = "07"; // 默认值
					}
				}
				// *****************校验管理费结束
				// ********如果是外缴
				if (outpayflag.equals("1")) {
					ManageFeeCalBL tManageFeeCalBL = new ManageFeeCalBL();
					if (!tManageFeeCalBL.submitData(tLCGrpContSchema
							.getGrpContNo(), tRiskCode, FeeCalMode, "1",
							"000000", tDutyCode)) {
						CError.buildErr(this, "管理费计算失败！");
						return -1;
					} else {
						sumPrem = sumPrem + tManageFeeCalBL.managefee;
						logger.debug("外缴的管理费："
								+ tManageFeeCalBL.managefee);
						// 将管理费保存;只有外缴的才保存
						LCOutManagePaySchema tOutManagePaySchema = new LCOutManagePaySchema();
						tOutManagePaySchema.setGrpContNo(tLCGrpContSchema
								.getPrtNo());
						tOutManagePaySchema.setGrpPolNo("000000"); // 暂定的，以后如果出现新险种这个字段一定会用的上的
						tOutManagePaySchema.setPrem(tManageFeeCalBL.managefee);
						tOutManagePaySchema.setOperator(mGlobalInput.Operator);
						tOutManagePaySchema
								.setMakeDate(PubFun.getCurrentDate());
						tOutManagePaySchema
								.setMakeTime(PubFun.getCurrentTime());
						PubSubmit tPubSubmit = new PubSubmit();
						VData mVResult = new VData();
						MMap tMMap = new MMap();
						tMMap.put(tOutManagePaySchema, "DELETE&INSERT");
						mVResult.add(tMMap);
						if (!tPubSubmit.submitData(mVResult, "Insert")) {
							logger.debug("保存外缴管理费失败");
						}

					}
				}
				// 增加校验，短险公共账户管理费校验 tLCGrpContSchema
				LCGrpPolDB ttLCGrpPolDB = new LCGrpPolDB();
				LCGrpPolSet ttLCGrpPolSet = new LCGrpPolSet();
				String tSql = "select * from lcgrpcont where grpcontno='"
						+ tLCGrpContSchema.getGrpContNo() + "' and riskcode='"
						+ tRiskCode + "'";
				ttLCGrpPolSet.set(ttLCGrpPolDB.executeQuery(tSql));
				// ttLCGrpPolDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());
				// ttLCGrpPolDB.setRiskCode(tRiskCode); //139,151
				// ttLCGrpPolSet = ttLCGrpPolDB.query();
				if (ttLCGrpPolSet != null && ttLCGrpPolSet.size() > 0) {
					LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
					tLCGrpPolSchema = ttLCGrpPolSet.get(1);
					if (checkInerFee(tLCGrpPolSchema) == -1) {
						// CError.buildErr(this, "公共账户保费不足抵缴管理费！");
						return -3;
					}
				}
				// 增加校验，长险法人账户管理费校验 tLCGrpContSchema 188,198
				tsql = "select * From lcgrppol where riskcode in (select riskcode from lmriskapp where risktype6 in ('4')) and grpcontno='"
						+ tLCGrpContSchema.getGrpContNo() + "'";
				tLCGrpPolSet = tLCGrpPolDB.executeQuery(tsql);
				if (tLCGrpPolSet != null && tLCGrpPolSet.size() > 0) {
					tRiskCode = tLCGrpPolSet.get(1).getRiskCode(); // 不是188，就是198，但只能是其中一个
					LCGrpPolDB tttLCGrpPolDB = new LCGrpPolDB();
					LCGrpPolSet tttLCGrpPolSet = new LCGrpPolSet();
					String ttSql = "select * from lcgrpcont where grpcontno='"
							+ tLCGrpContSchema.getGrpContNo()
							+ "' and riskcode='" + tRiskCode + "'";
					// tttLCGrpPolDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());
					// tttLCGrpPolDB.setRiskCode(tRiskCode);
					tttLCGrpPolSet.set(tttLCGrpPolDB.executeQuery(ttSql));
					if (tttLCGrpPolSet != null && tttLCGrpPolSet.size() > 0) {
						LCGrpPolSchema ttLCGrpPolSchema = new LCGrpPolSchema();
						ttLCGrpPolSchema = tttLCGrpPolSet.get(1);
						if (checkInFaFee(ttLCGrpPolSchema) == -1) {
							return -4;
						}
					}

				}

				// 增加外缴管理费校验，标准帐户型险种不会更新业务表，只有财务校验是必须的。
				ttMoney = VerifyOutPayMoney(tLCGrpContSchema);
				if (ttMoney == -1) {
					// CError.buildErr(this, "产品定义错误！");
					return -2;
				}
				if (ttMoney > 0) {
					LCOutManagePaySchema tOutManagePaySchema = new LCOutManagePaySchema();
					tOutManagePaySchema.setGrpContNo(tLCGrpContSchema
							.getPrtNo());
					tOutManagePaySchema.setGrpPolNo("000001"); // 避免与139共同承保，普通账户型险种不更新业务表。
					tOutManagePaySchema.setPrem(ttMoney);
					tOutManagePaySchema.setOperator(mGlobalInput.Operator);
					tOutManagePaySchema.setMakeDate(PubFun.getCurrentDate());
					tOutManagePaySchema.setMakeTime(PubFun.getCurrentTime());
					PubSubmit tPubSubmit = new PubSubmit();
					VData mVResult = new VData();
					MMap tMMap = new MMap();
					tMMap.put(tOutManagePaySchema, "DELETE&INSERT");
					mVResult.add(tMMap);
					if (!tPubSubmit.submitData(mVResult, "Insert")) {
						logger.debug("保存外缴管理费失败");
					}

				}
				// 保存外缴管理费
				sumPrem = sumPrem + ttMoney;
				logger.debug("所有的缴费：" + sumPrem);
				// mGlobalInput
				sumPrem = Arith.round(sumPrem, 2);
				// Modify by Zhoujunhui
				/*
				 * sql = " select sum(PayMoney) as sumpay from ljtempfee where
				 * TempFeeType in ('0','1') and othernotype='4' and otherno='" +
				 * tLCGrpContSchema.getGrpContNo() + "' and managecom='" +
				 * mGlobalInput.ManageCom + "' and riskcode in ( select riskcode
				 * from lcgrppol where grpcontno='" +
				 * tLCGrpContSchema.getGrpContNo() + "') and ConfFlag='0' and
				 * EnterAccDate is not null and EnterAccDate!='3000-1-1' group
				 * by otherno"; //原来使用印刷号 yaory logger.debug("**" + sql);
				 * sumTmpFee = LCGrpContSignBL.execSumQuery(conn, sql); if
				 * (sumTmpFee <= 0) { sql = " select sum(PayMoney) as sumpay
				 * from ljtempfee where TempFeeType in ('0','1') and
				 * othernotype='4' and otherno='" + tLCGrpContSchema.getPrtNo() + "'
				 * and managecom='" + mGlobalInput.ManageCom + "' and riskcode
				 * in ( select riskcode from lcgrppol where prtno='" +
				 * tLCGrpContSchema.getPrtNo() + "') and ConfFlag='0' and
				 * EnterAccDate is not null and EnterAccDate!='3000-1-1' group
				 * by otherno"; logger.debug("**" + sql); sumTmpFee =
				 * LCGrpContSignBL.execSumQuery(conn, sql); }
				 */
				sql = " select sum(PayMoney) as sumpay from ljtempfee where TempFeeType in ('0','1') and othernotype='4' and otherno='"
						+ tLCGrpContSchema.getGrpContNo()
						+ "' and policycom='"
						//+ mGlobalInput.ManageCom
						+ tLCGrpContSchema.getManageCom()
						+ "' and (riskcode in ( select riskcode from lcgrppol where grpcontno='"
						+ tLCGrpContSchema.getGrpContNo()
						+ "') or riskcode='000') and ConfFlag='0' and EnterAccDate is not null and EnterAccDate!='3000-1-1' and OperState='0' group by otherno";
				// 原来使用印刷号 yaory
				logger.debug("**" + sql);
				sumTmpFee = LCGrpContSignBL.execSumQuery(conn, sql);
				if (sumTmpFee <= 0) {
					sql = " select sum(PayMoney) as sumpay from ljtempfee where TempFeeType in ('0','1') and othernotype='4' and otherno='"
							+ tLCGrpContSchema.getPrtNo()
							+ "' and policycom='"
							//+ mGlobalInput.ManageCom
							+ tLCGrpContSchema.getManageCom()
							+ "' and (riskcode in ( select riskcode from lcgrppol where prtno='"
							+ tLCGrpContSchema.getPrtNo()
							+ "') or riskcode='000') and ConfFlag='0' and EnterAccDate is not null and EnterAccDate!='3000-1-1' and OperState='0' group by otherno";
					logger.debug("**" + sql);
					sumTmpFee = LCGrpContSignBL.execSumQuery(conn, sql);
				}
				// Modify End
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}
		if (sumTmpFee <= 0.0) {
			// CError.buildErr(this,"没有暂交费信息,不能签单");
			return -1;
		}
		if (sumTmpFee < sumPrem) {
			// CError.buildErr(this,"暂交费没有交够,不能签单");
			return -1;
		}
		if (sumTmpFee > sumPrem) {

			this.mGrpContDiff = PubFun
					.setPrecision(sumTmpFee - sumPrem, "0.00");
			return 1;
		}
		return 0;

	}

	/**
	 * 计算团单万能内扣管理费
	 * 
	 * @param tLCGrpPolSchema
	 *            LCGrpPolSchema
	 * @return double
	 */

	private double checkInFaFee(LCGrpPolSchema tLCGrpPolSchema) {
		LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
		LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
		String ttsql = "select * From lcgrpfee where grppolno='"
				+ tLCGrpPolSchema.getGrpPolNo()
				+ "' and insuaccno in (select insuaccno from lmriskfee where feetakeplace='01' )";
		tLCGrpFeeSet = tLCGrpFeeDB.executeQuery(ttsql);
		SSRS tSSRS = new SSRS();
		String tsql = "select prem from lcpol where grppolno='"
				+ tLCGrpPolSchema.getGrpPolNo() + "' and poltypeflag='3'"; // 法人账户金额
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(tsql);
		String temp = tSSRS.GetText(1, 1);
		double tprem = Double.parseDouble(temp); // 法人账户金额
		// 循环管理费定义,计算所有内扣管理费。
		String tFlag = "0";
		LCGrpFeeSchema tLCGrpFeeSchema;
		double innerfee = 0;
		for (int i = 1; i <= tLCGrpFeeSet.size(); i++) {
			tLCGrpFeeSchema = new LCGrpFeeSchema();
			tLCGrpFeeSchema = tLCGrpFeeSet.get(i);
			if (tLCGrpFeeSchema.getFeeCalCode().equals("01")) {
				if (tLCGrpFeeSchema.getInsuAccNo().equals("880006")) {
					if (tLCGrpFeeSchema.getFeeCalMode().equals("01")) {
						innerfee = innerfee + tLCGrpFeeSchema.getFeeValue();
					}
					if (tLCGrpFeeSchema.getFeeCalMode().equals("02")) {
						innerfee = innerfee + tLCGrpFeeSchema.getFeeValue()
								* tprem;
					}
				} else {
					if (tLCGrpFeeSchema.getFeeCalMode().equals("01")) {

						innerfee = innerfee + tLCGrpFeeSchema.getFeeValue()
								* tLCGrpPolSchema.getPeoples2();
					}
					if (tLCGrpFeeSchema.getFeeCalMode().equals("02")) {
						ttsql = "select nvl(sum(prem),0) from lcpol where grppolno='"
								+ tLCGrpPolSchema.getGrpPolNo()
								+ "' and (poltypeflag='0' or poltypeflag is null)";
						SSRS ttSSRS = new SSRS();
						ttSSRS = tExeSQL.execSQL(ttsql);
						double ttempMoney = Double.parseDouble(ttSSRS.GetText(
								1, 1));
						innerfee = innerfee + tLCGrpFeeSchema.getFeeValue()
								* ttempMoney;
					}

				}
			}
		}

		if (innerfee > tprem) {
			tFlag = "1";
		}

		if (tFlag.equals("1")) {
			return -1;
		} else {
			return 1;
		}

	}

	/**
	 * 计算团单特需医疗内扣管理费
	 * 
	 * @param tLCGrpPolSchema
	 *            LCGrpPolSchema
	 * @return double
	 */

	private double checkInerFee(LCGrpPolSchema tLCGrpPolSchema) {
		LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
		LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
		tLCGrpFeeDB.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
		tLCGrpFeeSet = tLCGrpFeeDB.query();
		LCGrpFeeSchema tLCGrpFeeSchema = tLCGrpFeeSet.get(1);
		String tFlag = "0";
		ExeSQL tExeSQL = new ExeSQL();
		if (tLCGrpFeeSchema.getFeeCalMode().equals("01")) {
			// 计算管理费
			double tMoney = tLCGrpFeeSchema.getFeeValue();
			SSRS tSSRS = new SSRS();
			String tsql = "select prem from lcpol where grppolno='"
					+ tLCGrpPolSchema.getGrpPolNo() + "' and poltypeflag='2'";
			tSSRS = tExeSQL.execSQL(tsql);
			String temp = tSSRS.GetText(1, 1);
			double tprem = Double.parseDouble(temp);
			if (Arith.round(tprem, 2) < Arith.round(tMoney, 2)) {
				tFlag = "1";
			}
		} else if (tLCGrpFeeSchema.getFeeCalMode().equals("02")) {
			// 计算管理费
			double tRate = tLCGrpFeeSchema.getFeeValue();
			SSRS tSSRS = new SSRS();
			String tsql = "select prem from lcpol where grppolno='"
					+ tLCGrpPolSchema.getGrpPolNo() + "' and poltypeflag='2'";
			tSSRS = tExeSQL.execSQL(tsql);
			String temp = tSSRS.GetText(1, 1);
			double tprem = Double.parseDouble(temp); // 公共账户保费
			tsql = "select nvl(sum(prem),0)*" + tRate
					+ " from lcpol where grppolno='"
					+ tLCGrpPolSchema.getGrpPolNo() + "'";
			tSSRS = tExeSQL.execSQL(tsql);
			temp = tSSRS.GetText(1, 1);
			double tfee = Double.parseDouble(temp);
			if (Arith.round(tprem, 2) < Arith.round(tfee, 2)) {
				tFlag = "1";
			}

		} else {
			// 不需要处理
		}
		if (tFlag.equals("1")) {
			return -1;
		} else {
			return 1;
		}
	}

	/**
	 * 计算团单下外缴管理费
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return double
	 */
	private double VerifyOutPayMoney(LCGrpContSchema tLCGrpContSchema) {
		double temvale = 0;
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		tLCGrpPolDB.setPrtNo(tLCGrpContSchema.getPrtNo());
		tLCGrpPolSet = tLCGrpPolDB.query();
		LCGrpPolSchema tLCGrpPolSchema;
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			tLCGrpPolSchema = new LCGrpPolSchema();
			tLCGrpPolSchema = tLCGrpPolSet.get(i);
			String tSql = "select nvl(risktype6,0) from lmriskapp where riskcode='"
					+ tLCGrpPolSchema.getRiskCode() + "' ";
			ExeSQL ttExeSQL = new ExeSQL();
			String tRiskType6 = ttExeSQL.getOneValue(tSql); // 判断险种的类型

			if (tRiskType6.equals("1")) { // 139 151
				continue;
			}
			LMRiskDB tLMRiskDB = new LMRiskDB();
			LMRiskSet tLMRiskSet = new LMRiskSet();
			tLMRiskDB.setRiskCode(tLCGrpPolSchema.getRiskCode());
			tLMRiskSet = tLMRiskDB.query();
			LMRiskSchema tLMRiskSchema = tLMRiskSet.get(1);
			if (tLMRiskSchema.getInsuAccFlag().equals("N")) {
				continue;
			}
			// 查询该分单是否有外缴管理费。
			LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
			LCGrpFeeSet tLCGrpFeeSet = new LCGrpFeeSet();
			tLCGrpFeeDB.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
			tLCGrpFeeSet = tLCGrpFeeDB.query();
			LCGrpFeeSchema tLCGrpFeeSchema;
			for (int j = 1; j <= tLCGrpFeeSet.size(); j++) {
				tLCGrpFeeSchema = new LCGrpFeeSchema();
				tLCGrpFeeSchema = tLCGrpFeeSet.get(j);
				String ttInsurAccNO;
				String ttTakePlace;
				if (tLCGrpFeeSchema.getFeeCalMode().equals("03")
						|| tLCGrpFeeSchema.getFeeCalMode().equals("04")) {
					// 判断该管理费收取点是不是收费时。
					ttInsurAccNO = tLCGrpFeeSchema.getInsuAccNo();
					LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
					LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
					tLMRiskFeeDB.setInsuAccNo(ttInsurAccNO);
					tLMRiskFeeSet = tLMRiskFeeDB.query();
					ttTakePlace = tLMRiskFeeSet.get(1).getFeeTakePlace();
					ExeSQL tExeSQL = new ExeSQL();
					if (ttTakePlace.equals("01")) {
						SSRS ttSSRS = new SSRS();
						String ttsql;
						ttsql = "select owner from lmriskinsuacc where insuaccno=(select feecode from lmriskfee where insuaccno='"
								+ ttInsurAccNO + "')";
						ttSSRS = tExeSQL.execSQL(ttsql);
						String ttOwner = ttSSRS.GetText(1, 1);
						// 计算外缴管理费进行累加
						// LMRiskInsuAccDB tLMRiskInsuAccDB=new
						// LMRiskInsuAccDB();
						// LMRiskInsuAccSet tLMRiskInsuAccSet=new
						// LMRiskInsuAccSet();
						// tLMRiskInsuAccDB.setInsuAccNo(ttInsurAccNO);
						// tLMRiskInsuAccSet=tLMRiskInsuAccDB.query();
						if (ttOwner.equals("0")) { // 个人账户

							SSRS tSSRS = new SSRS();

							if (tLCGrpFeeSchema.getFeeCalMode().equals("03")) {
								ttsql = "select nvl(count(*),0) from lcpol where grppolno='"
										+ tLCGrpPolSchema.getGrpPolNo()
										+ "' and (poltypeflag is null or poltypeflag='0')";

							} else {
								ttsql = "select nvl(sum(prem),0) from lcpol where grppolno='"
										+ tLCGrpPolSchema.getGrpPolNo()
										+ "' and (poltypeflag is null or poltypeflag='0')";

							}
							tSSRS = tExeSQL.execSQL(ttsql);
							temvale = temvale
									+ Double.parseDouble(tSSRS.GetText(1, 1))
									* tLCGrpFeeSchema.getFeeValue();
						} else if (ttOwner.equals("1")) { // 法人账户
							if (tLCGrpFeeSchema.getFeeCalMode().equals("03")) {
								temvale = temvale
										+ tLCGrpFeeSchema.getFeeValue();
							} else {
								SSRS tSSRS = new SSRS();
								ttsql = "select nvl(sum(prem),0) from lcpol where grppolno='"
										+ tLCGrpPolSchema.getGrpPolNo()
										+ "' and (poltypeflag='3')";
								tSSRS = tExeSQL.execSQL(ttsql);
								temvale = temvale
										+ Double.parseDouble(tSSRS
												.GetText(1, 1))
										* tLCGrpFeeSchema.getFeeValue();
							}

						} else if (ttOwner.equals("2")) { // 公共账户
							// 面前只有139险种考虑。
						} else {
							logger.debug("产品定义错误");
							temvale = -1;
						}
					} else {
						continue;
					}
				} else {
					continue;
				}
			}
		}
		return temvale;
	}

	/**
	 * 查询团体下所有合同
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return LCContSet
	 */
	private VData queryLCContSet(LCGrpContSchema tLCGrpContSchema) {
		// tLCContDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());
		VData tVData =new VData();

		String sql = "select contno from lccont where prtno='"
				+ tLCGrpContSchema.getPrtNo()
				+ "' and appflag='0' and uwflag in ('4','9') order by poltype desc ";
		logger.debug(sql);
		//较少放入内存中的数据
		SSRS tSSRS =new SSRS();
		ExeSQL tExeSQL =new ExeSQL();
		tSSRS=tExeSQL.execSQL(sql);
		tVData.add(tSSRS);
//		LCContSet tLCContSet = null;
//		LCContDB tLCContDB = new LCContDB();
//		tLCContSet = tLCContDB.executeQuery(sql);
//		if (tLCContDB.mErrors.needDealError()) {
//			this.mErrors.copyAllErrors(tLCContDB.mErrors);
//			return null;
//		}
//		if (tLCContSet == null || tLCContSet.size() <= 0) {
//			return null;
//		}
		return tVData;

	}

	/**
	 * 查询并填充LCGrpContSet的数据
	 * 
	 * @return boolean
	 */
	private boolean fillGrpCont() {
		LCGrpContDB db = new LCGrpContDB();
		db.setPrtNo(mLCGrpContSet.get(1).getPrtNo());
		LCGrpContSet grpcontSet = db.query();
		logger.debug(grpcontSet.size());
		if (grpcontSet == null || db.mErrors.needDealError()
				|| grpcontSet.size() == 0) {

			CError.buildErr(this, "查找团单合同信息失败!");

			return false;
		}

		mLCGrpContSet.set(1, grpcontSet.get(1));

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mLCGrpContSchema = (LCGrpContSchema) cInputData.getObjectByObjectName(
				"LCGrpContSchema", 0);
		if (mLCGrpContSchema == null) {
			mLCGrpContSet = (LCGrpContSet) cInputData.getObjectByObjectName(
					"LCGrpContSet", 0);
		} else {
			if (StrTool.cTrim(mLCGrpContSchema.getGrpContNo()).equals("")) {

				CError.buildErr(this, "集体投保单号传入失败!");
				return false;
			}

			mLCGrpContSet.add(mLCGrpContSchema);
		}

		if (mLCGrpContSet.size() <= 0) {
			CError.buildErr(this, "没有得到要签单的数据集");
			return false;
		}
		return true;
	}

	// private String getNewPolNo(String oldPolNo)
	// {
	// String polNo = (String) PolNoMap.get(oldPolNo);
	// if (polNo == null)
	// {
	// polNo = oldPolNo;
	// }
	// return polNo;
	// }

	private void cacheTempFee(Object key, LJTempFeeSchema tempFeeSchema) {
		this.mLJTempFeeMap.put(key, tempFeeSchema);
	}

	private Object getTempFee(String key) {
		return mLJTempFeeMap.get(key);
	}

	/**
	 * 签单校验(判断表单是否被锁定)
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return boolean : true 表示被锁定 false 表示未被锁定 LiuHao 2005-04-16 add
	 */
	private static boolean checkLockGrpCont(LCGrpContSchema tLCGrpContSchema) {
		String tSql = "";
		int notSignCount = 0;
		Connection conn = DBConnPool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		// 检索表单锁定状态
		tSql = "select count(*) from LDSysTrace where PolNo = '"
				+ tLCGrpContSchema.getGrpContNo() + "' and polstate = 1999";

		try {
			ps = conn.prepareStatement(tSql);
			rs = ps.executeQuery();
			if (rs.next()) {
				notSignCount = rs.getInt(1);
				rs.close();
				ps.close();
			}
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		finally
		{
			try {
				if(rs!=null)
				{
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(ps!=null)
				{
					ps.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if(conn!=null)
				{
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (notSignCount > 0) {
			return true;
		}

		return false;
	}

	/**
	 * 签单校验(指定表单锁定)
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 * @return 无 LiuHao 2005-04-16 add
	 */
	private boolean lockGrpCont(LCGrpContSchema tLCGrpContSchema) {
		MMap map = new MMap();

		tLDSysTraceSchema.setPolNo(tLCGrpContSchema.getGrpContNo()); //
		tLDSysTraceSchema.setOperator(tLCGrpContSchema.getOperator()); //
		tLDSysTraceSchema.setMakeDate(PubFun.getCurrentDate());
		tLDSysTraceSchema.setMakeTime(PubFun.getCurrentTime());
		tLDSysTraceSchema.setPolState(1999);
		tLDSysTraceSchema.setCreatePos("签单阶段");
		tLDSysTraceSchema.setManageCom(mGlobalInput.ManageCom);
		tLDSysTraceSchema.setOperator2(tLCGrpContSchema.getOperator());
		tLDSysTraceSchema.setModifyDate(PubFun.getCurrentDate());
		tLDSysTraceSchema.setModifyTime(PubFun.getCurrentTime());

		map.put(tLDSysTraceSchema, "INSERT");

		// 提交保存
		VData contData = new VData();
		contData.add(map);
		PubSubmit pubSubmit = new PubSubmit();
		if (!pubSubmit.submitData(contData, "")) {
			return false;
		}
		return true;
	}

	/**
	 * 签单校验(解除当前表的锁定状态) LiuHao 2005-04-16 add
	 * 
	 * @param tLCGrpContSchema
	 *            LCGrpContSchema
	 */
	private void ravelGrpCont(LCGrpContSchema tLCGrpContSchema) {
		MMap map = new MMap();

		map.put(tLDSysTraceSchema, "DELETE");

		// 提交保存
		VData contData = new VData();
		contData.add(map);
		PubSubmit pubSubmit = new PubSubmit();
		if (!pubSubmit.submitData(contData, "")) {
			CError.buildErr(this, "团单合同(" + tLCGrpContSchema.getGrpContNo()
					+ ")解锁失败！请与管理员联系！;");
			// tLDSysTraceSchema = null;
			// return false;
		}
		// return true;
		// tLDSysTraceSchema = null;
	}

	public static void main(String[] args) {
		VData cInputData = new VData();
		String cOperate = "";
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "0101";
		tGlobalInput.ComCode = "001";
		cInputData.add(tGlobalInput);
		String[] testContNo = { "20051214000002" };
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		for (int i = 0; i < testContNo.length; i++) {
			LCGrpContSchema tLCContSchema = new LCGrpContSchema();
			tLCContSchema.setPrtNo(testContNo[i]);
			tLCGrpContSet.add(tLCContSchema);
		}
		cInputData.add(tLCGrpContSet);
		LCGrpContSignBL lCGrpContSignBL = new LCGrpContSignBL();
		boolean actualReturn = lCGrpContSignBL.submitData(cInputData, cOperate);
	}
}
