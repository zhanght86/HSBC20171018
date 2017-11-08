/*
 * @(#)PEdorValidBL.java	2005-06-10
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPSpecDB;
import com.sinosoft.lis.db.LPUWMasterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPSpecSet;
import com.sinosoft.lis.vschema.LPUWMasterSet;
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
 * Description: 保全-保全生效处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：zhangtao
 * @version：1.0
 * @CreateDate：2005-06-10
 */
public class GEdorValidBL {
private static Logger logger = Logger.getLogger(GEdorValidBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 传输数据 */
	private TransferData mTransferData = new TransferData();
	// private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LJAPaySet mLJAPaySet = new LJAPaySet();
	private LPGrpEdorItemSet mLPGrpEdorItemSet = new LPGrpEdorItemSet();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();

	private String mEdorAcceptNo = "";
	private String mEdorConfNo = "";

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public GEdorValidBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}
		logger.debug("after getInputData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("after dealData...");

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("after prepareOutputData...");

		// 数据提交
//		PubSubmit tPubSubmit = new PubSubmit();
//		if (!tPubSubmit.submitData(mInputData, "")) {
//			// @@错误处理
//			mErrors.copyAllErrors(tPubSubmit.mErrors);
//			CError.buildErr(this, "数据提交失败");
//			return false;
//		}
//		mInputData = null;
		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		// 校验保全受理是否已经保全确认
		if (!checkLPEdorApp()) {
			return false;
		}

		// 查询保全受理下所有保全项目
		if (!getLPGrpEdorItem()) {
			return false;
		}

		// 执行各个保全项目确认处理
		if (!calEdorTypeConfirm()) {
			return false;
		}

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mLJAPaySet = (LJAPaySet) mInputData.getObjectByObjectName(
				"LJAPaySet", 0);
		mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
		if (mEdorAcceptNo == null || mEdorAcceptNo.trim().equals("")) {
			CError tError = new CError();
			tError.errorMessage = "前台传输数据EdorAcceptNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		this.mEdorConfNo = (String) mTransferData.getValueByName("EdorConfNo");
		
		if (mEdorConfNo == null || mEdorConfNo.trim().equals("")) {
			CError tError = new CError();
			tError.errorMessage = "批单号生成失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			CError tError = new CError();
			tError.errorMessage = "准备数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 校验保全受理是否已经保全确认
	 * 
	 * @return: boolean
	 */
	private boolean checkLPEdorApp() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
		if (!tLPEdorAppDB.getInfo()) {
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全受理信息查询失败!" + "保全受理号：" + mEdorAcceptNo;
			mErrors.addOneError(tError);
			return false;
		}
		if (tLPEdorAppDB.getEdorState().equals("0")) {
			CError tError = new CError();
			tError.errorMessage = "保全受理已经确认生效!" + "保全受理号：" + mEdorAcceptNo;
			mErrors.addOneError(tError);
			return false;
		}
		//comment by wk
//		if (!tLPEdorAppDB.getEdorState().equals("6")) {
//			CError tError = new CError();
//			tError.errorMessage = "保全受理尚未确认!" + "保全受理号：" + mEdorAcceptNo;
//			mErrors.addOneError(tError);
//			return false;
//		}

		return true;
	}

	/**
	 * 查询保全项目
	 */
	private boolean getLPGrpEdorItem() {
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
		mLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPGrpEdorItemDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "查询保全项目失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mLPGrpEdorItemSet == null || mLPGrpEdorItemSet.size() < 1) {
			CError.buildErr(this, "保全申请下没有保全项目变更!" + "保全申请号：" + mEdorAcceptNo);
			return false;
		}

		return true;
	}

	/**
	 * 执行各个保全项目确认生效处理
	 */
	private boolean calEdorTypeConfirm() {
		boolean appValid = true;

		String sEdorAcceptNo = mLPGrpEdorItemSet.get(1).getEdorAcceptNo();
		String sEdorState;
		String tEdorType;
		String sEdorValiDate;
		for (int i = 1; i <= mLPGrpEdorItemSet.size(); i++) {
			sEdorState = mLPGrpEdorItemSet.get(i).getEdorState();
			tEdorType = mLPGrpEdorItemSet.get(i).getEdorType();
			sEdorValiDate = mLPGrpEdorItemSet.get(i).getEdorValiDate();

			// 判断保全项目批改状态，如果已经确认生效，就不能再生效替换了
			if (sEdorState.equals("0")) {
				logger.debug("== 保全项目" + tEdorType + "已经确认生效== "
						+ "保全受理号：" + sEdorAcceptNo + "==");
				continue;
			}

			// 如果到生效日期，则生效替换
			//if (PubFun.calInterval(sEdorValiDate, mCurrentDate, "D") >= 0) {
			// 执行该保全项目确认处理
			logger.debug("== befor GrpEdor" + tEdorType
					+ "ConfirmBL ==");
			try {
				Class tClass = Class.forName("com.sinosoft.lis.bqgrp.GrpEdor"
						+ tEdorType + "ConfirmBL");
				EdorConfirm tEdorConfirm = (EdorConfirm) tClass
				.newInstance();
				VData tVData = new VData();

				tVData.add(mGlobalInput);
				tVData.add(mLPGrpEdorItemSet.get(i));
				tVData.add(mLJAPaySet);
				if (!tEdorConfirm.submitData(tVData, "CONFIRM||"
						+ tEdorType)) {
					CError.buildErr(this, "保全项目" + tEdorType + "确认生效失败!"
							+ "保全受理号：" + sEdorAcceptNo + "=="
							+ tEdorConfirm.mErrors.getFirstError());
					return false;
				} else {
					logger.debug("== after GrpEdor" + tEdorType
							+ "ConfirmBL ==");
					VData rVData = tEdorConfirm.getResult();
					MMap tMap = (MMap) rVData.getObjectByObjectName("MMap",
							0);
					if (tMap == null) {
						CError.buildErr(this, "获取保全项目" + tEdorType
								+ "的保全确认生效结果时失败!");
						return false;

					} else {
						map.add(tMap);
					}
				}

			} catch (ClassNotFoundException ex) {
				logger.debug("====ClassNotFoundException===");

			} catch (Exception ex) {
				CError.buildErr(this, "保全项目" + tEdorType + "确认生效失败!");
				return false;
			}

			// 将人工核保时录入的特约信息导入C表
			// Add By QianLy on 2006-10-25---------
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setEdorAcceptNo(sEdorAcceptNo);
			tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSet.get(i).getEdorNo());
			mLPEdorItemSet = tLPEdorItemDB.query();
			for (int j = 0; j < mLPEdorItemSet.size(); j++) {
				inserSpec(mLPEdorItemSet.get(i));
			}
			// -----------

			// 将该团体保全项目下所有个单项目批改状态置为 0 - 生效
			String wherePart = "where edortype = '"
				+ "?edortype?"
				+ "' and grpcontno = '"
				+ "?grpcontno?"
				+ "' and edorno = '"
				+ "?edorno?"
				+ "' and EdorAcceptNo = '?mEdorAcceptNo?'";
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE LPEdorItem set EdorState = '0' , ")
			.append(" Operator = '").append("?Operator?")
			.append("', ModifyDate = '").append("?mCurrentDate?")
			.append("', ModifyTime = '").append("?mCurrentTime?")
			.append("' ").append(wherePart);
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sbSQL.toString());
			sqlbv.put("Operator", mGlobalInput.Operator);
			sqlbv.put("mCurrentDate", mCurrentDate);
			sqlbv.put("mCurrentTime", mCurrentTime);
			sqlbv.put("edortype", mLPGrpEdorItemSet.get(i).getEdorType());
			sqlbv.put("grpcontno", mLPGrpEdorItemSet.get(i).getGrpContNo());
			sqlbv.put("edorno", mLPGrpEdorItemSet.get(i).getEdorNo());
			sqlbv.put("mEdorAcceptNo", mEdorAcceptNo);
			map.put(sqlbv, "UPDATE");

			// 更新该保全项目批改状态为确认生效
			mLPGrpEdorItemSet.get(i).setEdorState("0");
			mLPGrpEdorItemSet.get(i).setOperator(mGlobalInput.Operator);
			mLPGrpEdorItemSet.get(i).setModifyDate(mCurrentDate);
			mLPGrpEdorItemSet.get(i).setModifyTime(mCurrentTime);
			map.put(mLPGrpEdorItemSet.get(i).getSchema(), "UPDATE");
//			} else {
//			// 该保全项目尚未到生效日期
//			logger.debug("== 该保全项目尚未到生效日期==" + tEdorType + "==");
//			appValid = false;
//			}
			LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalDB.setCalType("StatUpdate");
			tLMEdorCalDB.setEdorType(mLPGrpEdorItemSet.get(i).getEdorType());
			LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query(); //查找保费累计变动金额计算信息
            if (tLMEdorCalDB.mErrors.needDealError()) {
                mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
                mErrors.addOneError(new CError("查询险种责任保全计算信息失败！"));
                return false;
            }
            if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
                logger.debug("没有计算信息，不作计算");

            }else {
            	//统计更新开始
            	
            	String upGrpContSql = "update LCGrpCont a set "
            						+ "	a.prem = (case when (select sum(Prem) from LCPol where GrpContNo = a.GrpContNo and AppFlag = '1') is not null then (select sum(Prem) from LCPol where GrpContNo = a.GrpContNo and AppFlag = '1') else 0 end) ,"
            						+ " a.peoples = (case when (select count(distinct InsuredNo) from LCPol where GrpContNo = a.GrpContNo and AppFlag = '1') is not null then (select count(distinct InsuredNo) from LCPol where GrpContNo = a.GrpContNo and AppFlag = '1') else 0 end) ,"
            						+ "	a.Amnt = (case when (select sum(Amnt) from LCPol where GrpContNo = a.GrpContNo and AppFlag = '1') is not null then (select sum(Amnt) from LCPol where GrpContNo = a.GrpContNo and AppFlag = '1') else 0 end) ,"
            						+ "	a.Mult = (case when (select sum(Mult) from LCPol where GrpContNo = a.GrpContNo and AppFlag = '1') is not null then (select sum(Mult) from LCPol where GrpContNo = a.GrpContNo and AppFlag = '1') else 0 end) ,"
            						+ "	a.SumPrem = (case when (select sum(SumPrem) from LCPol where GrpContNo = a.GrpContNo and AppFlag = '1') is not null then (select sum(SumPrem) from LCPol where GrpContNo = a.GrpContNo and AppFlag = '1') else 0 end) "
            						+ " where a.GrpContNo = '"+"?GrpContNo?"+"'";
            	SQLwithBindVariables sbv1=new SQLwithBindVariables();
            	sbv1.sql(upGrpContSql);
            	sbv1.put("GrpContNo", mLPGrpEdorItemSet.get(i).getGrpContNo());
            	map.put(sbv1, "UPDATE");
            	
            	String upGrpPolSql = "update LCGrpPol a set "
									+ "	a.Prem = (case when (select sum(Prem) from LCPol where GrpPolNo = a.GrpPolNo and AppFlag = '1') is not null then (select sum(Prem) from LCPol where GrpPolNo = a.GrpPolNo and AppFlag = '1') else 0 end) ,"
									+ " a.Peoples2 = (case when (select count(distinct InsuredNo) from LCPol where GrpPolNo = a.GrpPolNo and AppFlag = '1') is not null then (select count(distinct InsuredNo) from LCPol where GrpPolNo = a.GrpPolNo and AppFlag = '1') else 0 end),"
									+ "	a.Amnt = (case when (select sum(Amnt) from LCPol where GrpPolNo = a.GrpPolNo and AppFlag = '1') is not null then (select sum(Amnt) from LCPol where GrpPolNo = a.GrpPolNo and AppFlag = '1') else 0 end) ,"
									+ "	a.Mult = (case when (select sum(Mult) from LCPol where GrpPolNo = a.GrpPolNo and AppFlag = '1') is not null then (select sum(Mult) from LCPol where GrpPolNo = a.GrpPolNo and AppFlag = '1') else 0 end) ,"
									+ "	a.SumPrem = (case when (select sum(SumPrem) from LCPol where GrpPolNo = a.GrpPolNo and AppFlag = '1') is not null then (select sum(SumPrem) from LCPol where GrpPolNo = a.GrpPolNo and AppFlag = '1') else 0 end) "
									+ " where a.GrpContNo = '"+"?GrpContNo?"+"'";
            	SQLwithBindVariables sbv2=new SQLwithBindVariables();
            	sbv2.sql(upGrpPolSql);
            	sbv2.put("GrpContNo", mLPGrpEdorItemSet.get(i).getGrpContNo());
            	map.put(sbv2, "UPDATE");
            }
		}

		if (appValid) // 保全申请下所有保全项目都已生效
		{

			// 统一更新保全受理、申请批单的批改状态为确认生效[0]
			updAppState();

			// 保单解挂
			BqContHangUpBL tContHangUpBL = new BqContHangUpBL(mGlobalInput);
			if (!tContHangUpBL.hangUpEdorAccept(sEdorAcceptNo, "0")) {
				CError.buildErr(this, "保全保单解挂失败! ");
				return false;
			} else {
				MMap tMap = tContHangUpBL.getMMap();
				map.add(tMap); // del because DB hs not update zhangtao
				// 2005-08-02
			}

		}
		return true;
	}

	/**
	 * 特约信息C、P互换
	 * 
	 * @param pLPEdorItemSchema
	 */
	private void inserSpec(LPEdorItemSchema pLPEdorItemSchema) {
		LCSpecSet inserLCSpecSet = new LCSpecSet();
		String sqlSpec;
		String sqlUW;
		SQLwithBindVariables sqlbvs=new SQLwithBindVariables();
		SQLwithBindVariables sqlbvu=new SQLwithBindVariables();
		if (pLPEdorItemSchema.getPolNo().trim().equals("000000")) {
			sqlSpec = " select * from LPSpec where edorno = '"
				+ "?edorno?" + "' and edortype = '"
				+ "?edortype?" + "' ";
            sqlbvs.sql(sqlSpec);
            sqlbvs.put("edorno", pLPEdorItemSchema.getEdorNo());
            sqlbvs.put("edortype", pLPEdorItemSchema.getEdorType());
			sqlUW = " select * from LPUWMaster where edorno = '"
				+ "?edorno?" + "' and edortype = '"
				+ "?edortype?" + "' ";
			sqlbvu.sql(sqlUW);
			sqlbvu.put("edorno", pLPEdorItemSchema.getEdorNo());
			sqlbvu.put("edortype", pLPEdorItemSchema.getEdorType());
		} else {
			sqlSpec = " select * from LPSpec where edorno = '"
				+ "?edorno?" + "' and edortype = '"
				+ "?edortype?" + "' and polno = '"
				+ "?polno?" + "' ";
            sqlbvs.sql(sqlSpec);
            sqlbvs.put("edorno", pLPEdorItemSchema.getEdorNo());
            sqlbvs.put("edortype", pLPEdorItemSchema.getEdorType());
            sqlbvs.put("polno", pLPEdorItemSchema.getPolNo());
			sqlUW = " select * from LPUWMaster where edorno = '"
				+ "?edorno?" + "' and edortype = '"
				+ "?edortype?" + "' and polno = '"
				+ "?polno?" + "' ";
	
			sqlbvu.sql(sqlUW);
			sqlbvu.put("edorno", pLPEdorItemSchema.getEdorNo());
			sqlbvu.put("edortype", pLPEdorItemSchema.getEdorType());
			sqlbvu.put("polno", pLPEdorItemSchema.getPolNo());
		}
		// 特约信息
		LPSpecDB tLPSpecDB = new LPSpecDB();
		LPSpecSet tLPSpecSet = tLPSpecDB.executeQuery(sqlbvs);
		LCSpecSchema tLCSpecSchema = new LCSpecSchema();
		inserLCSpecSet.add(tLCSpecSchema);
		Reflections tRef = new Reflections();
		tRef.transFields(inserLCSpecSet, tLPSpecSet);

		// 核保特约原因
		LPUWMasterDB tLPUWMasterDB = new LPUWMasterDB();
		LPUWMasterSet tLPUWMasterSet = tLPUWMasterDB.executeQuery(sqlbvu);
		for (int i = 1; i <= tLPUWMasterSet.size(); i++) {
			String updSQL = " update LCUWMaster set specreason = '"
				+ "?specreason?"
				+ "' where contno = '" + "?contno?"
				+ "' and polno = '" + "?polno?"
				+ "'";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(updSQL);
			sbv.put("specreason", tLPUWMasterSet.get(i).getSpecReason());
			sbv.put("contno", tLPUWMasterSet.get(i).getContNo());
			sbv.put("polno", tLPUWMasterSet.get(i).getPolNo());
			map.put(sbv, "UPDATE");
		}
		map.put(inserLCSpecSet, "INSERT");
	}

	/**
	 * 统一更新保全受理、申请批单的批改状态为确认生效[0]
	 */
	private void updAppState() {
		String wherePart = "where EdorAcceptNo='" + "?mEdorAcceptNo?" + "'";
		StringBuffer sbSQL = new StringBuffer();

		// 暂时对批单的生效不做细分处理，与保全申请一起统一生效

		// 个人保全批单
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorMain set EdorState = '0' ,ModifyDate = '").append("?mCurrentDate?").append(
		"', ModifyTime = '").append("?mCurrentTime?").append("' ").append(
				wherePart);
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sbSQL.toString());
		sbv1.put("mCurrentDate", mCurrentDate);
		sbv1.put("mCurrentTime", mCurrentTime);
		sbv1.put("mEdorAcceptNo", mEdorAcceptNo);
		map.put(sbv1, "UPDATE");

		// 团体保全批单
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPGrpEdorMain set EdorState = '0', ModifyDate = '").append("?mCurrentDate?").append(
		"', ModifyTime = '").append("?mCurrentTime?").append("' ").append(
				wherePart);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sbSQL.toString());
		sbv2.put("mCurrentDate", mCurrentDate);
		sbv2.put("mCurrentTime", mCurrentTime);
		sbv2.put("mEdorAcceptNo", mEdorAcceptNo);
		map.put(sbv2, "UPDATE");

		// 保全申请
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorApp set EdorState = '0' , ").append(" EdorConfNo = '")
		.append("?EdorConfNo?")
		 .append("',ModifyDate = '").append("?mCurrentDate?").append(
		"', ModifyTime = '").append("?mCurrentTime?").append("' ").append(
				wherePart);
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sbSQL.toString());
		sbv3.put("EdorConfNo", this.mEdorConfNo);
		sbv3.put("mCurrentDate", mCurrentDate);
		sbv3.put("mCurrentTime", mCurrentTime);
		sbv3.put("mEdorAcceptNo", mEdorAcceptNo);
		map.put(sbv3, "UPDATE");
		
		String[] asscociateTable_OtherNo = {"LJAGet"};
		String updateSql="";
		for (int i = 0; i < asscociateTable_OtherNo.length; i++) {
			updateSql = "update " + asscociateTable_OtherNo[i]
			+ " set OtherNo = '" + "?mEdorConfNo?" + "' where OtherNo = '"
			+ "?mEdorAcceptNo?" + "' and OtherNoType='10'";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(updateSql);
			sbv.put("mEdorConfNo", mEdorConfNo);
			sbv.put("mEdorAcceptNo", mEdorAcceptNo);
			map.put(sbv, "UPDATE");
		}

	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorAcceptNo", "6120060608000003");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		GEdorValidBL tGEdorValidBL = new GEdorValidBL();

		if (!tGEdorValidBL.submitData(tVData, "")) {
			logger.debug(tGEdorValidBL.mErrors.getError(0).errorMessage);
		} else {
			logger.debug("OKOKOKOKOKOKOKOKOKOKO");
		}

	}

}
