/*
 * @(#)UWReportInputBL.java	2008-10-16
 *
 * Copyright 2008 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCUWReportDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCUWReportSchema;
import com.sinosoft.lis.vschema.LCUWReportSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
public class UWReportInputBL {
private static Logger logger = Logger.getLogger(UWReportInputBL.class);
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
	private LCUWReportSchema mLCUWReportSchema = new LCUWReportSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCContSchema tLCContSchema = new LCContSchema();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private boolean mContModifyFlag = false;

	public UWReportInputBL() {
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
	      UWReportInputBLS tUWReportInputBLS = new UWReportInputBLS();	      
	      //如果有需要处理的错误，则返回
	      if (!tUWReportInputBLS.submitData(mInputData,mOperate))
	      {
	        // @@错误处理
	        this.mErrors.copyAllErrors(tUWReportInputBLS.mErrors);
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
		mLCUWReportSchema.setUWOperator(mGlobalInput.Operator);
		LCUWReportDB tLCUWReportDB = new LCUWReportDB();
		LCUWReportSet tLCUWReportSet = new LCUWReportSet();
//		tLCUWReportDB.setOtherNo(mLCUWReportSchema.getOtherNo());
//		tLCUWReportDB.setUWOperator(mLCUWReportSchema.getUWOperator());
//		tLCUWReportSet = tLCUWReportDB.query();
//		if(tLCUWReportSet == null || tLCUWReportSet.size()==0)
//		{
//			mLCUWReportSchema.setMakeDate(mCurrentDate);
//			mLCUWReportSchema.setMakeTime(mCurrentTime);
//		}
//		else
//		{
//			mLCUWReportSchema.setMakeDate(tLCUWReportSet.get(1).getMakeDate());
//			mLCUWReportSchema.setMakeTime(tLCUWReportSet.get(1).getMakeTime());
//		}		
		String tSerialNo = PubFun1.CreateMaxNo("LCUWReport", 20);
		mLCUWReportSchema.setSerialNo(tSerialNo);
		mLCUWReportSchema.setMakeDate(mCurrentDate);
		mLCUWReportSchema.setMakeDate(mCurrentDate);
		mLCUWReportSchema.setMakeTime(mCurrentTime);
		mLCUWReportSchema.setManageCom(mGlobalInput.ManageCom);		
		mLCUWReportSchema.setModifyDate(mCurrentDate); 
		mLCUWReportSchema.setModifyTime(mCurrentTime);
		
		//增加修改合同表中的商业因素承保标记
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLCContSchema.getContNo());
		if(!tLCContDB.getInfo())
		{
			CError.buildErr(this, "合同表查询失败！");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		if((mLCContSchema.getBussFlag()!=null && mLCContSchema.getBussFlag().equals("Y")
		  &&(tLCContSchema.getBussFlag()==null || tLCContSchema.getBussFlag().equals("")
				  ||(tLCContSchema.getBussFlag()!=null && !tLCContSchema.getBussFlag().equals("") && tLCContSchema.getBussFlag().equals("N"))))
			||(mLCContSchema.getBussFlag()!=null && mLCContSchema.getBussFlag().equals("N")
					&& tLCContSchema.getBussFlag()!=null && !tLCContSchema.getBussFlag().equals("") && tLCContSchema.getBussFlag().equals("Y")))
			mContModifyFlag = true;
		if(mContModifyFlag == true)
		{
			tLCContSchema.setBussFlag(mLCContSchema.getBussFlag());	
			tLCContSchema.setOperator(mGlobalInput.Operator);
			tLCContSchema.setModifyDate(mCurrentDate); 
			tLCContSchema.setModifyTime(mCurrentTime);
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
		mLCUWReportSchema.setSchema((LCUWReportSchema) mInputData
				.getObjectByObjectName("LCUWReportSchema", 0));	
		mLCContSchema.setSchema((LCContSchema) mInputData
				.getObjectByObjectName("LCContSchema", 0));
		String sReportCont = mLCUWReportSchema.getReportCont();

		logger.debug("lengh="
				+ sReportCont.length());
		if (sReportCont.length() > 800) {
			// @@错误处理
			CError.buildErr(this, "输入核保分析报告内容过长!");
			return false;
		}		
		
		String sOtherNo = mLCUWReportSchema.getOtherNo();
		if (sOtherNo == null || sOtherNo.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输数据OtherNo失败!");
			return false;
		}
		String sOtherNoType = mLCUWReportSchema.getOtherNoType();
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
			mInputData.add(mLCUWReportSchema);
			if(mContModifyFlag == true)
			{
				mInputData.add(tLCContSchema);
			}
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
