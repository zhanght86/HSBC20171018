/*
 * @(#)UWScoreRReportBL.java	2008-10-25
 *
 * Copyright 2008 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.xb;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;

import com.sinosoft.lis.tb.UWScoreRReportBLS;
import com.sinosoft.lis.db.RnewScoreRReportDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.RnewScoreRReportSchema;
import com.sinosoft.lis.schema.RnewScoreRReportSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.RnewScoreRReportSubSet;
import com.sinosoft.lis.vschema.RnewScoreRReportSet;
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
public class RnewUWScoreRReportBL {
private static Logger logger = Logger.getLogger(RnewUWScoreRReportBL.class);
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
	private RnewScoreRReportSchema mRnewScoreRReportSchema = new RnewScoreRReportSchema();
	private RnewScoreRReportSubSchema mRnewScoreRReportSubSchema;
	private RnewScoreRReportSubSet mRnewScoreRReportSubSet = new RnewScoreRReportSubSet();
	private RnewScoreRReportSubSet mRnewScoreRReportSubSet1 = new RnewScoreRReportSubSet();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public RnewUWScoreRReportBL() {
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
		RnewUWScoreRReportBLS tUWScoreRReportBLS = new RnewUWScoreRReportBLS();
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
		tLCContDB.setContNo(mRnewScoreRReportSchema.getContNo());
		tLCContSet = tLCContDB.query();
		if (tLCContSet == null && tLCContSet.size() == 0) {
			CError.buildErr(this, "查询合同表失败!");
			return false;
		}
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema = tLCContSet.get(1);
		mRnewScoreRReportSchema.setGrpContNo(tLCContSchema.getGrpContNo());
		mRnewScoreRReportSchema.setProposalContNo(tLCContSchema
				.getProposalContNo());

		RnewScoreRReportDB tRnewScoreRReportDB = new RnewScoreRReportDB();
		RnewScoreRReportSet tRnewScoreRReportSet = new RnewScoreRReportSet();
		tRnewScoreRReportDB.setContNo(mRnewScoreRReportSchema.getContNo());
		tRnewScoreRReportDB.setCustomerNo(mRnewScoreRReportSchema.getCustomerNo());
		tRnewScoreRReportSet = tRnewScoreRReportDB.query();
		if (tRnewScoreRReportSet == null || tRnewScoreRReportSet.size() == 0) {
			logger.debug("第一次录入****************");
			mRnewScoreRReportSchema.setOperator(mGlobalInput.Operator);
			mRnewScoreRReportSchema.setMakeDate(mCurrentDate);
			mRnewScoreRReportSchema.setMakeTime(mCurrentTime);
			for (int i = 1; i <= mRnewScoreRReportSubSet.size(); i++) {
				mRnewScoreRReportSubSchema = new RnewScoreRReportSubSchema();
				mRnewScoreRReportSubSchema
						.setSchema(mRnewScoreRReportSubSet.get(i));
				mRnewScoreRReportSubSchema.setOperator(mGlobalInput.Operator);
				mRnewScoreRReportSubSchema.setMakeDate(mCurrentDate);
				mRnewScoreRReportSubSchema.setMakeTime(mCurrentTime);
				mRnewScoreRReportSubSchema.setGrpContNo(tLCContSchema
						.getGrpContNo());
				mRnewScoreRReportSubSchema.setProposalContNo(tLCContSchema
						.getProposalContNo());

				mRnewScoreRReportSubSchema.setManageCom(mGlobalInput.ManageCom);
				mRnewScoreRReportSubSchema.setModifyDate(mCurrentDate);
				mRnewScoreRReportSubSchema.setModifyTime(mCurrentTime);
				mRnewScoreRReportSubSet1.add(mRnewScoreRReportSubSchema);
			}

		} else {
			mRnewScoreRReportSchema.setOperator(tRnewScoreRReportSet.get(1)
					.getOperator());
			mRnewScoreRReportSchema.setMakeDate(tRnewScoreRReportSet.get(1)
					.getMakeDate());
			mRnewScoreRReportSchema.setMakeTime(tRnewScoreRReportSet.get(1)
					.getMakeTime());
			for (int i = 1; i <= mRnewScoreRReportSubSet.size(); i++) {
				mRnewScoreRReportSubSchema = new RnewScoreRReportSubSchema();
				mRnewScoreRReportSubSchema
						.setSchema(mRnewScoreRReportSubSet.get(i));
				mRnewScoreRReportSubSchema.setOperator(tRnewScoreRReportSet.get(1)
						.getOperator());
				mRnewScoreRReportSubSchema.setMakeDate(tRnewScoreRReportSet.get(1)
						.getMakeDate());
				mRnewScoreRReportSubSchema.setMakeTime(tRnewScoreRReportSet.get(1)
						.getMakeTime());
				mRnewScoreRReportSubSchema.setGrpContNo(tLCContSchema
						.getGrpContNo());
				mRnewScoreRReportSubSchema.setProposalContNo(tLCContSchema
						.getProposalContNo());

				mRnewScoreRReportSubSchema.setManageCom(mGlobalInput.ManageCom);
				mRnewScoreRReportSubSchema.setModifyDate(mCurrentDate);
				mRnewScoreRReportSubSchema.setModifyTime(mCurrentTime);
				mRnewScoreRReportSubSet1.add(mRnewScoreRReportSubSchema);
			}
		}

		mRnewScoreRReportSchema.setManageCom(mGlobalInput.ManageCom);
		mRnewScoreRReportSchema.setModifyDate(mCurrentDate);
		mRnewScoreRReportSchema.setModifyTime(mCurrentTime);

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
		mRnewScoreRReportSchema.setSchema((RnewScoreRReportSchema) mInputData
				.getObjectByObjectName("RnewScoreRReportSchema", 0));
		mRnewScoreRReportSubSet = (RnewScoreRReportSubSet) mInputData
				.getObjectByObjectName("RnewScoreRReportSubSet", 0);

		String sContNo = mRnewScoreRReportSchema.getContNo();
		String sCustomerNo = mRnewScoreRReportSchema.getCustomerNo();
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
			mInputData.add(mRnewScoreRReportSchema);
			mInputData.add(mRnewScoreRReportSubSet1);

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
