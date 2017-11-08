/*
 * @(#)GrpEdorNSCancelBL.java      Apr 26, 2006
 *
 * Copyright 2006 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */
package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团体保全操作 －－ 新增附加险
 * </p>
 * <p>
 * Description: 团体保全新增附加险（整单新增）保全撤销
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：lizhuo
 * @CreateDate: Apr 26, 2006
 * @version：1.0
 */
public class GrpEdorWSCancelBL implements EdorCancel {
private static Logger logger = Logger.getLogger(GrpEdorWSCancelBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
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
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "GrpEdorNSCancelBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mGlobalInput == null || mLPGrpEdorItemSchema == null) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorNSCancelBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "输入数据有误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
		LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
		tLPGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpPolDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpPolDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpPolDB.setAppFlag("2");
		tLPGrpPolSet = tLPGrpPolDB.query();
		if (tLPGrpPolSet == null) {
			return true;
		}
		for (int i = 1; i <= tLPGrpPolSet.size(); i++) {
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolDB.setGrpPolNo(tLPGrpPolSet.get(i).getGrpPolNo());
			tLCPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
			tLCPolSet = tLCPolDB.query();
			if (tLCPolSet == null) {
				CError.buildErr(this, "查询新增险种失败!");
				return false;
			}
			for (int j = 1; j <= tLCPolSet.size(); j++) {
				String PolNo = tLCPolSet.get(j).getPolNo();
				String[] tab = { "lcduty", "lcprem", "lcget", "lppol",
						"lpduty", "lpprem", "lpget" };
				for (int k = 0; k < tab.length; k++) {
					mMap.put("delete from " + tab[k] + " where polno = '"
							+ PolNo + "'", "DELETE");
				}
			}
			mMap.put(tLCPolSet, "DELETE");
			mMap.put("delete from lcgrppol where grppolno = '"
					+ tLPGrpPolSet.get(i).getGrpPolNo() + "'", "DELETE");
			mMap.put("delete from lpgrppol where grppolno = '"
					+ tLPGrpPolSet.get(i).getGrpPolNo() + "'", "DELETE");
			mMap.put("delete from ljsgetendorse where grppolno = '"
					+ tLPGrpPolSet.get(i).getGrpPolNo() + "'", "DELETE");
			mMap.put("delete from ljspayperson where grppolno = '"
					+ tLPGrpPolSet.get(i).getGrpPolNo() + "'", "DELETE");
		}
		return true;
	}

	private boolean prepareData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
