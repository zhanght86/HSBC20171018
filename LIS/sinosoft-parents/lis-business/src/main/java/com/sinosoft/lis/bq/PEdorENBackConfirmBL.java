/*
 * @(#)PEdorENBackConfirmBL.java 2005-09-27 Copyright 2005 Sinosoft Co. Ltd. All rights reserved. All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全- 满期不续保回退确认生效处理类
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
 * @CreateDate：2005-09-27
 */
public class PEdorENBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorENBackConfirmBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	//public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// 需要回退的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorENBackConfirmBL() {
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

		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareData()) {
			return false;
		}

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
					.getObjectByObjectName("LPEdorItemSchema", 0); // 需要回退的保全项目
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收前台数据失败!");
			return false;
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
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
		logger.debug("=== 续保方式回退确认生效 ===");
		Reflections tRef = new Reflections();

		// 恢复保单信息
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLPContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PEdorENConfirmBL";
			tError.functionName = "exchangCPData";
			tError.errorMessage = "查询保全保单数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		LCContSchema tLCContSchema = new LCContSchema();
		tRef.transFields(tLCContSchema, tLPContDB.getSchema());
		tLCContSchema.setOperator(this.mGlobalInput.Operator);
		tLCContSchema.setModifyDate(mCurrentDate);
		tLCContSchema.setModifyTime(mCurrentTime);
		map.put(tLCContSchema, "DELETE&INSERT");

		// 恢复险种信息
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
		LPPolSet tLPolSet = tLPPolDB.query();
		LCPremSet tLCPremSet = new LCPremSet();
		if (tLPPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全个人保单险种信息失败!");
			return false;
		}
		if (tLPolSet != null && tLPolSet.size() > 0) {
			LCPolSet instLCPolSet = new LCPolSet();
			LCPolSchema tLCPolSchema;
			for (int i = 1; i <= tLPolSet.size(); i++) {
				tLCPolSchema = new LCPolSchema();
				tRef.transFields(tLCPolSchema, tLPolSet.get(i).getSchema());
				instLCPolSet.add(tLCPolSchema.getSchema());
				// Add By QianLy on 2006-09-18-----------
				LPPremDB tLPPremDB = new LPPremDB();
				LPPremSet tempLPPremSet = new LPPremSet();
				tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPPremDB.setPolNo(tLCPolSchema.getPolNo());
				tempLPPremSet = tLPPremDB.query();
				if (tempLPPremSet != null && tempLPPremSet.size() > 0) {
					for (int j = 1; j <= tempLPPremSet.size(); j++) {
						// 先交换CP表数据
						LCPremSchema tLCPremSchema = new LCPremSchema();
						tRef.transFields(tLCPremSchema, tempLPPremSet.get(j));
						tLCPremSchema.setOperator(this.mGlobalInput.Operator);
						tLCPremSchema.setModifyDate(mCurrentDate);
						tLCPremSchema.setModifyTime(mCurrentTime);
						tLCPremSet.add(tLCPremSchema);
					}
				}
				//map.put(tLCPremSet, "DELETE&INSERT");
				// -------------
				//不支持状态回退，保单状态发生变化不允许进行回退，所以不需要进行状态表的更新
//				if (!updLCContState(tLCPolSchema.getContNo(), "000000",
//						tLCPolSchema.getPolNo())) {
//					return false;
//				}
			}
			//modify by jiaqiangli 2009-03-28 传入相同对象，改成放在循环外
			map.put(tLCPremSet, "DELETE&INSERT");
			map.put(instLCPolSet, "DELETE&INSERT");
		}

		// 撤销续期重新抽档
//		LJSPayDB tLJSPayDB = new LJSPayDB();
//		tLJSPayDB.setOtherNo(mLPEdorItemSchema.getContNo());
//		tLJSPayDB.setOtherNoType("2");
//		LJSPaySet tLJSPaySet = tLJSPayDB.query();
//		if (tLJSPayDB.mErrors.needDealError()) {
//			CError.buildErr(this, "续期应收信息查询失败!");
//			return false;
//		}
//		if (tLJSPaySet != null && tLJSPaySet.size() > 0) {
//			TransferData tTransferData = new TransferData();
//			tTransferData.setNameAndValue("SubmitFlag", "noSubmit");
//			VData tVData = new VData();
//			tVData.add(tLCContSchema);
//			tVData.add(tTransferData);
//			tVData.add(mGlobalInput);
//
//			IndiDueFeeCancelBL tIndiDueFeeCancelBL = new IndiDueFeeCancelBL();
//			if (!tIndiDueFeeCancelBL.submitData(tVData, "BQApp")) {
//				mErrors.copyAllErrors(tIndiDueFeeCancelBL.mErrors);
//				return false;
//			}
//			tVData.clear();
//			tVData = tIndiDueFeeCancelBL.getResult();
//			MMap tMMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
//			if (tMMap != null) {
//				map.add(tMMap);
//			}
//		} else {
//			logger.debug("== 没有续期应收数据 保单号：　"
//					+ mLPEdorItemSchema.getContNo() + "==");
//		}
		return true;
	}

	/**
	 * 更新保单状态表
	 */
	private boolean updLCContState(String sContNo, String sInsuredNo,
			String sPolNo) {

		String delSql = " delete from lccontstate where trim(statetype) = 'Terminate' and trim(state) = '1' "
				+ " and enddate is null and startdate = '?startdate?' and contno = '?sContNo?' and insuredno = '?sInsuredNo?' and polno = '?sPolNo?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(delSql);
		sbv1.put("startdate", mLPEdorItemSchema.getEdorValiDate());
		sbv1.put("sContNo", sContNo);
		sbv1.put("sInsuredNo", sInsuredNo);
		sbv1.put("sPolNo", sPolNo);
		map.put(sbv1, "DELETE");

		String sEndDate = PubFun.calDate(mLPEdorItemSchema.getEdorValiDate(),
				-1, "D", null);
		String sql = " select * from lccontstate where trim(statetype) = 'Terminate' "
				+ " and enddate = '?sEndDate?'  and contno = '?sContNo?' and insuredno = '?sInsuredNo?' and polno = '?sPolNo?'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sql);
		sbv2.put("sEndDate", sEndDate);
		sbv2.put("sContNo", sContNo);
		sbv2.put("sInsuredNo", sInsuredNo);
		sbv2.put("sPolNo", sPolNo);
		logger.debug(sql);
		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = tLCContStateDB.executeQuery(sbv2);
		if (tLCContStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单状态查询失败!");
			return false;
		}
		if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
			tLCContStateSet.get(1).setEndDate("");
			map.put(tLCContStateSet.get(1).getSchema(), "UPDATE");
		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(map);
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

	public CErrors getErrors() {
		return null;
	}

}
