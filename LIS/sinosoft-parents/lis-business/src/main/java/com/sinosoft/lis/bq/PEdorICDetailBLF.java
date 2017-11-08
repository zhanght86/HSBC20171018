/*
 * @(#)PEdorICDetailBLF.java	2005-07-07
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPAddressBL;
import com.sinosoft.lis.bl.LPAppntBL;
import com.sinosoft.lis.bl.LPInsuredBL;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-
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
 * @CreateDate：2005-07-07
 */
public class PEdorICDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorICDetailBLF.class);
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
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	 private LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();

	public PEdorICDetailBLF() {
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
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError.buildErr(this, "数据提交失败!");
			return false;
		}
		mInputData = null;

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {

		VData tVData = new VData();
		tVData.addElement(mLPEdorItemSchema);
		tVData.addElement(mLPInsuredSchema);
		tVData.addElement(mGlobalInput);
		PEdorICDetailBL tPEdorICDetailBL = new PEdorICDetailBL();
		if (!tPEdorICDetailBL.getSubmitData(tVData, "INSERT||CM")) {
			this.mErrors.copyAllErrors(tPEdorICDetailBL.mErrors);
			CError.buildErr(this, "处理保单号为" + mLPEdorItemSchema.getContNo()
					+ "的被保险人信息变更时失败!");
			return false;

		}
		MMap tMap = (MMap) tPEdorICDetailBL.getResult()
				.getObjectByObjectName("MMap", 0);
		if (tMap != null) {
			map.add(tMap);
		}

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPInsuredSchema = (LPInsuredSchema) mInputData.getObjectByObjectName(
				"LPInsuredSchema", 0);
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
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

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		logger.debug("-------test...");
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		tLPEdorItemSchema.setEdorAcceptNo("6120060426000060");
		tLPEdorItemSchema.setEdorNo("6020060426000083");
		tLPEdorItemSchema.setEdorType("IC");
		tLPEdorItemSchema.setContNo("86000020080469000228");
		tLPEdorItemSchema.setInsuredNo("9000001156");
		tLPEdorItemSchema.setPolNo("000000");

		tLPInsuredSchema.setInsuredNo("9000001156");
		tLPInsuredSchema.setEdorType("IC");
		tLPInsuredSchema.setName("13212");
		tLPInsuredSchema.setSex("0");
		tLPInsuredSchema.setBirthday("1956-05-31");
		tLPInsuredSchema.setIDType("0");
		tLPInsuredSchema.setIDNo("231002560531051");

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";

		VData tVData = new VData();
		tVData.addElement(tLPEdorItemSchema);
		tVData.addElement(tLPInsuredSchema);
		tVData.addElement(tGlobalInput);
		PEdorICDetailBLF tPEdorCMDetailUI = new PEdorICDetailBLF();
		if (!tPEdorCMDetailUI.submitData(tVData, "INSERT||MAIN")) {
			logger.debug(tPEdorCMDetailUI.mErrors.getErrContent());
		} else {
			logger.debug("IC Submit OK!");
		}
	}
}
