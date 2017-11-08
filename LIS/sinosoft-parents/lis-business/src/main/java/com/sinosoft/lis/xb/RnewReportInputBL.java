/*
 * @(#)UWReportInputBL.java	2008-10-16
 *
 * Copyright 2008 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.xb;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;

import com.sinosoft.lis.tb.UWReportInputBLS;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
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
 * Description: 新契约-核保分析报告处理类
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
 * @CreateDate：2008-10-16
 */
public class RnewReportInputBL {
private static Logger logger = Logger.getLogger(RnewReportInputBL.class);
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
	private RnewUWReportSchema mRnewUWReportSchema = new RnewUWReportSchema();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public RnewReportInputBL() {
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
			CError.buildErr(this, "LCUWReportSchema准备失败!");
			return false;
		}
		
		logger.debug("after dealData...");
	    if (this.mOperate.equals("REPORT|INSERT"))                   //先查询再插入
	    {
	      //准备给后台的数据
	      if (!prepareOutputData())
	        return false;
	      logger.debug("--- prepareOutputData---");

	      //数据提交
	      logger.debug("Start UWReporInputBL Submit...");
	      RnewReportInputBLS tRnewReportInputBLS = new RnewReportInputBLS();	      
	      //如果有需要处理的错误，则返回
	      if (!tRnewReportInputBLS.submitData(mInputData,mOperate))
	      {
	        // @@错误处理
	        this.mErrors.copyAllErrors(tRnewReportInputBLS.mErrors);
	        CError.buildErr(this, "数据提交失败!");
	        return false;
	      }
	      logger.debug("End UWReportInputBL Submit...");
	    }

	    logger.debug("--- commitData---");

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData()  
	{	
		mRnewUWReportSchema.setUWOperator(mGlobalInput.Operator);
		RnewUWReportDB tRnewUWReportDB = new RnewUWReportDB();
		RnewUWReportSet tRnewUWReportSet = new RnewUWReportSet();
//		tRnewUWReportDB.setOtherNo(mRnewUWReportSchema.getOtherNo());
//		tRnewUWReportDB.setUWOperator(mRnewUWReportSchema.getUWOperator());
//		tRnewUWReportSet = tRnewUWReportDB.query();
//		if(tRnewUWReportSet == null || tRnewUWReportSet.size()==0)
//		{
//			mRnewUWReportSchema.setMakeDate(mCurrentDate);
//			mRnewUWReportSchema.setMakeTime(mCurrentTime);
//		}
//		else
//		{
//			mRnewUWReportSchema.setMakeDate(tRnewUWReportSet.get(1).getMakeDate());
//			mRnewUWReportSchema.setMakeTime(tRnewUWReportSet.get(1).getMakeTime());
//		}		
		String tSerialNo = PubFun1.CreateMaxNo("RnewUWReport", 20);
		mRnewUWReportSchema.setSerialNo(tSerialNo);
		mRnewUWReportSchema.setMakeDate(mCurrentDate);
		mRnewUWReportSchema.setMakeDate(mCurrentDate);
		mRnewUWReportSchema.setMakeTime(mCurrentTime);
		mRnewUWReportSchema.setManageCom(mGlobalInput.ManageCom);		
		mRnewUWReportSchema.setModifyDate(mCurrentDate);
		mRnewUWReportSchema.setModifyTime(mCurrentTime);
			
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
		mRnewUWReportSchema.setSchema((RnewUWReportSchema) mInputData
				.getObjectByObjectName("RnewUWReportSchema", 0));		
		String sReportCont = mRnewUWReportSchema.getReportCont();

		logger.debug("lengh="
				+ sReportCont.length());
		if (sReportCont.length() > 800) {
			// @@错误处理
			CError.buildErr(this, "输入核保分析报告内容过长!");
			return false;
		}		
		
		String sOtherNo = mRnewUWReportSchema.getOtherNo();
		if (sOtherNo == null || sOtherNo.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据OtherNo失败!");
			return false;
		}
		String sOtherNoType = mRnewUWReportSchema.getOtherNoType();
		if (sOtherNoType == null || sOtherNoType.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据OtherNoType失败!");
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
			mInputData.add(mRnewUWReportSchema);
			
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
