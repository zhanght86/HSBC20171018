/*
 * @(#)UWScoreRReportBL.java	2008-10-25
 *
 * Copyright 2008 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;

import com.sinosoft.lis.tb.UWScoreRReportBLS;
import com.sinosoft.lis.db.LCScoreRReportDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCScoreRReportSchema;
import com.sinosoft.lis.schema.LCScoreRReportSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCScoreRReportSubSet;
import com.sinosoft.lis.vschema.LCScoreRReportSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.COracleBlob;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 新契约-生调评审处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：ln
 * @version：1.0
 * @CreateDate：2008-10-25
 */
public class UWScoreRReportBL {
private static Logger logger = Logger.getLogger(UWScoreRReportBL.class);
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
	private LCScoreRReportSchema mLCScoreRReportSchema = new LCScoreRReportSchema();
	private LCScoreRReportSubSchema mLCScoreRReportSubSchema;
	private LCScoreRReportSubSet mLCScoreRReportSubSet = new LCScoreRReportSubSet();
	private LCScoreRReportSubSet mLCScoreRReportSubSet1 = new LCScoreRReportSubSet();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public UWScoreRReportBL() {
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
			CError.buildErr(this, "数据准备失败!");
			return false;
		}

		logger.debug("after dealData...");
		// 准备给后台的数据
		if (!prepareOutputData())
			return false;
		logger.debug("--- after prepareOutputData---");

		// 数据提交
		logger.debug("Start UWScoreRReportBL Submit...");
		UWScoreRReportBLS tUWScoreRReportBLS = new UWScoreRReportBLS();
		// 如果有需要处理的错误，则返回
		if (!tUWScoreRReportBLS.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWScoreRReportBLS.mErrors);
			CError.buildErr(this, "数据提交失败!");
			return false;
		}
		logger.debug("End UWScoreRReportBL Submit...");

		logger.debug("--- commitData---");

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		tLCContDB.setContNo(mLCScoreRReportSchema.getContNo());
		tLCContSet = tLCContDB.query();
		if (tLCContSet == null && tLCContSet.size() == 0) {
			CError.buildErr(this, "查询合同表失败!");
			return false;
		}
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema = tLCContSet.get(1);
		mLCScoreRReportSchema.setGrpContNo(tLCContSchema.getGrpContNo());
		mLCScoreRReportSchema.setProposalContNo(tLCContSchema
				.getProposalContNo());

		LCScoreRReportDB tLCScoreRReportDB = new LCScoreRReportDB();
		LCScoreRReportSet tLCScoreRReportSet = new LCScoreRReportSet();
		tLCScoreRReportDB.setContNo(mLCScoreRReportSchema.getContNo());
		tLCScoreRReportDB.setCustomerNo(mLCScoreRReportSchema.getCustomerNo());
		tLCScoreRReportSet = tLCScoreRReportDB.query();
		if (tLCScoreRReportSet == null || tLCScoreRReportSet.size() == 0) {
			logger.debug("第一次录入****************");
			mLCScoreRReportSchema.setOperator(mGlobalInput.Operator);
			mLCScoreRReportSchema.setMakeDate(mCurrentDate);
			mLCScoreRReportSchema.setMakeTime(mCurrentTime);
			for (int i = 1; i <= mLCScoreRReportSubSet.size(); i++) {
				mLCScoreRReportSubSchema = new LCScoreRReportSubSchema();
				mLCScoreRReportSubSchema
						.setSchema(mLCScoreRReportSubSet.get(i));
				mLCScoreRReportSubSchema.setOperator(mGlobalInput.Operator);
				mLCScoreRReportSubSchema.setMakeDate(mCurrentDate);
				mLCScoreRReportSubSchema.setMakeTime(mCurrentTime);
				mLCScoreRReportSubSchema.setGrpContNo(tLCContSchema
						.getGrpContNo());
				mLCScoreRReportSubSchema.setProposalContNo(tLCContSchema
						.getProposalContNo());

				mLCScoreRReportSubSchema.setManageCom(mGlobalInput.ManageCom);
				mLCScoreRReportSubSchema.setModifyDate(mCurrentDate);
				mLCScoreRReportSubSchema.setModifyTime(mCurrentTime);
				mLCScoreRReportSubSet1.add(mLCScoreRReportSubSchema);
			}

		} else {
			mLCScoreRReportSchema.setOperator(tLCScoreRReportSet.get(1)
					.getOperator());
			mLCScoreRReportSchema.setMakeDate(tLCScoreRReportSet.get(1)
					.getMakeDate());
			mLCScoreRReportSchema.setMakeTime(tLCScoreRReportSet.get(1)
					.getMakeTime());
			for (int i = 1; i <= mLCScoreRReportSubSet.size(); i++) {
				mLCScoreRReportSubSchema = new LCScoreRReportSubSchema();
				mLCScoreRReportSubSchema
						.setSchema(mLCScoreRReportSubSet.get(i));
				mLCScoreRReportSubSchema.setOperator(tLCScoreRReportSet.get(1)
						.getOperator());
				mLCScoreRReportSubSchema.setMakeDate(tLCScoreRReportSet.get(1)
						.getMakeDate());
				mLCScoreRReportSubSchema.setMakeTime(tLCScoreRReportSet.get(1)
						.getMakeTime());
				mLCScoreRReportSubSchema.setGrpContNo(tLCContSchema
						.getGrpContNo());
				mLCScoreRReportSubSchema.setProposalContNo(tLCContSchema
						.getProposalContNo());

				mLCScoreRReportSubSchema.setManageCom(mGlobalInput.ManageCom);
				mLCScoreRReportSubSchema.setModifyDate(mCurrentDate);
				mLCScoreRReportSubSchema.setModifyTime(mCurrentTime);
				mLCScoreRReportSubSet1.add(mLCScoreRReportSubSchema);
			}
		}

		mLCScoreRReportSchema.setManageCom(mGlobalInput.ManageCom);
		mLCScoreRReportSchema.setModifyDate(mCurrentDate);
		mLCScoreRReportSchema.setModifyTime(mCurrentTime);

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
		mLCScoreRReportSchema.setSchema((LCScoreRReportSchema) mInputData
				.getObjectByObjectName("LCScoreRReportSchema", 0));
		mLCScoreRReportSubSet = (LCScoreRReportSubSet) mInputData
				.getObjectByObjectName("LCScoreRReportSubSet", 0);

		String sContNo = mLCScoreRReportSchema.getContNo();
		String sCustomerNo = mLCScoreRReportSchema.getCustomerNo();
		if (sContNo == null || sContNo.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据OtherNo失败!");
			return false;
		}
		if (sCustomerNo == null || sCustomerNo.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据CustomerNo失败!");
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
			mInputData.add(mLCScoreRReportSchema);
			mInputData.add(mLCScoreRReportSubSet1);

			mResult.clear();
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错:" + ex.toString());
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

}
