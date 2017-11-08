/*
 * @(#)PEdorAFConfirmBL.java	2005-07-26
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.db.LPSpecDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.lis.vschema.LPSpecSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-二核处理（加费、特约）确认类
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
 * @CreateDate：2005-07-26
 */
public class PEdorAFConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorAFConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
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
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorAFConfirmBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("===PEdorFMConfirmBL===");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据校验
		if (!checkData()) {
			return false;
		}

		logger.debug("after checkData...");

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

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		// C、P表数据互换
		if (!exchangCPData()) {
			return false;
		}

		return true;
	}

	/**
	 * C、P表数据互换
	 * 
	 * @return: boolean
	 */
	private boolean exchangCPData() {
		Reflections tRef = new Reflections();

		// 特约信息 P表数据直接导入C表
		LCSpecSet aLCSpecSet = new LCSpecSet();

		// 查询P表数据[特约信息]
		LPSpecSet tLPSpecSet = new LPSpecSet();
		LPSpecDB tLPSpecDB = new LPSpecDB();
		tLPSpecDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPSpecDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPSpecSet.set(tLPSpecDB.query());
		if (tLPSpecDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全特约信息失败!");
		}
		if (tLPSpecSet != null && tLPSpecSet.size() > 0) {
			for (int j = 1; j <= tLPSpecSet.size(); j++) {
				// 将P表中数据放到C表中[特约信息]
				LCSpecSchema tLCSpecSchema = new LCSpecSchema();
				tRef.transFields(tLCSpecSchema, tLPSpecSet.get(j).getSchema());
				aLCSpecSet.add(tLCSpecSchema);
			}
		}

		// 加费信息
		LCPremSet aLCPremSet = new LCPremSet();
		LPPremSet aLPPremSet = new LPPremSet();
		LPPremSchema tLPPremSchema = new LPPremSchema();
		LCPremSchema tLCPremSchema = new LCPremSchema();
		// 查询P表数据
		LPPremSet tLPPremSet = new LPPremSet();
		LPPremDB tLPPremDB = new LPPremDB();
		tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPremSet.set(tLPPremDB.query());
		if (tLPPremDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全特约信息失败!");
		}
		if (tLPPremSet != null && tLPPremSet.size() > 0) {
			for (int j = 1; j <= tLPPremSet.size(); j++) {
				// 如果C表中有该记录，则C、P互换
				LCPremSet tLCPremSet = new LCPremSet();
				LCPremDB tLCPremDB = new LCPremDB();
				tLCPremDB.setPolNo(tLPPremSet.get(j).getPolNo());
				tLCPremDB.setDutyCode(tLPPremSet.get(j).getDutyCode());
				tLCPremDB.setPayPlanCode(tLPPremSet.get(j).getPayPlanCode());
				tLCPremSet = tLCPremDB.query();
				if (tLCPremDB.mErrors.needDealError()) {
					CError.buildErr(this, "查询保费项失败!");
				}
				if (tLCPremSet != null && tLCPremSet.size() == 1) {
					// C、P互换
					tLPPremSchema = new LPPremSchema();
					tRef.transFields(tLPPremSchema, tLCPremSet.get(1));
					tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					aLPPremSet.add(tLPPremSchema);
				}
				tLCPremSchema = new LCPremSchema();
				tRef.transFields(tLCPremSchema, tLPPremSet.get(j));
				aLCPremSet.add(tLCPremSchema);
			}
		}

		// 遗留问题：如果有追溯加费，需要将C表中前时间段的加费记录删除 zhangtao 2005-07-27

		map.put(aLCSpecSet, "DELETE&INSERT");
		map.put(aLCPremSet, "DELETE&INSERT");
		map.put(aLPPremSet, "DELETE&INSERT");

		// 更新保全项目状态为确认生效
		mLPEdorItemSchema.setEdorState("0");
		mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		mLPEdorItemSchema.setModifyDate(mCurrentDate);
		mLPEdorItemSchema.setModifyTime(mCurrentTime);

		map.put(mLPEdorItemSchema, "UPDATE");

		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return: boolean
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLPEdorItemDB.getInfo()) {
			mErrors.addOneError(new CError("查询批改项目信息失败!"));
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

		return true;
	}

	/**
	 * 判断是否有追溯加费
	 * 
	 * @param pLPPremSet
	 *            P表中保存的理赔传来的加费记录
	 * @return boolean
	 */
	private boolean calBJAddPrem(LPPremSet pLPPremSet) {
		// 查询保单生效日期
		String sql = " select cvalidate from lccont " + " where contno = '"
				+ "?contno?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		ExeSQL tExeSQL = new ExeSQL();
		String sCValiDate = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保单生效日期查询失败!");
			return false;
		}
		if (sCValiDate == null || sCValiDate.equals("")) {
			CError.buildErr(this, "保单生效日期为空!");
			return false;
		}
		if (sCValiDate.length() > 10) {
			sCValiDate = sCValiDate.substring(0, 10);
		}

		String sLastPayendYear = "1900-01-01"; // 找出最晚的交费终止日期
		for (int m = 1; m <= pLPPremSet.size(); m++) {
			int intval = PubFun.calInterval(sLastPayendYear, pLPPremSet.get(m)
					.getPayEndDate(), "D");

			if (intval > 0) {
				sLastPayendYear = pLPPremSet.get(m).getPayEndDate();
			}

		}

		LPPremSchema iLPPremSchema = new LPPremSchema();
		for (int i = 1; i <= pLPPremSet.size(); i++) {
			iLPPremSchema = pLPPremSet.get(i);
			String sPayPlanType = iLPPremSchema.getPayPlanType();
			// 如果是追溯加费，肯定是追溯到起效日期，不会追溯到中途时间
			// 加费起期为保单生效日期不一定代表是追溯加费
			if (iLPPremSchema.getPayStartDate().equals("sCValiDate")
					&& iLPPremSchema.getPayEndDate().equals(sLastPayendYear)) // 追溯加费
			{
				if (sPayPlanType.equals("01") || sPayPlanType.equals("03")) // 健康加费
				{

				} else if (sPayPlanType.equals("02")
						|| sPayPlanType.equals("04")) // 职业加费
				{

				}
			} else {

			}
		}

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全!"));
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
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorFMDetailBLF";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
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

	public static void main(String[] args) {

	}

	private void jbInit() throws Exception {
	}
}
