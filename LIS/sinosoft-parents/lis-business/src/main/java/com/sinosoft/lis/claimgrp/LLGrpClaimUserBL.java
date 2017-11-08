/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:提交理赔用户管理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author: quyang
 * @version 1.0
 */

public class LLGrpClaimUserBL {
private static Logger logger = Logger.getLogger(LLGrpClaimUserBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private MMap map = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private LLGrpClaimUserSchema mLLGrpClaimUserSchema = new LLGrpClaimUserSchema();

	public LLGrpClaimUserBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------LLGrpClaimUserBL Begin----------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 检查数据合法性
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,原因是:"+tPubSubmit.mErrors.getLastError());
			return false;
		}
		
		mInputData = null;
		logger.debug("----------LLGrpClaimUserBL End----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		logger.debug("---------Go To getInputData----------");

		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLLGrpClaimUserSchema = (LLGrpClaimUserSchema) mInputData
				.getObjectByObjectName("LLGrpClaimUserSchema", 0);
		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		logger.debug("---------Go To checkData----------");
		
		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}
		
		if (mOperate == null) {
			
			// @@错误处理
			CError.buildErr(this, "前台传输操作符失败!");
			return false;
		}
		
		if (mLLGrpClaimUserSchema == null) {
			
			// @@错误处理
			CError.buildErr(this, "接受的Schema信息全部为空!");
			return false;
		}
		
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mLLGrpClaimUserSchema.getUpUserCode());
		
		if(!tLDUserDB.getInfo())
		{
			// @@错误处理
			CError.buildErr(this, "录入的上级用户不存在,请重新录入!");
			return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("---------Go To dealData----------");

		boolean tReturn = true;

		// 进行理赔用户的录入
		if (mOperate.equals("User||INSERT")) {
			if (!UserInsert()) {
				return false;
			}
		}

		// 进行理赔用户的修改
		if (mOperate.equals("User||UPDATE")) {
			if (!UserUpdate()) {
				return false;
			}
		}

		// 进行理赔用户的删除
		if (mOperate.equals("User||DELETE")) {
			if (!UserDelete()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 进行理赔用户管理的录入
	 * 
	 * @return
	 */
	private boolean UserInsert() {
		logger.debug("---------Go To UserInsert()----------");

		// 进行理赔用户管理主表的数据查询
		LLGrpClaimUserDB tLLGrpClaimUserDB = new LLGrpClaimUserDB();
		tLLGrpClaimUserDB.setUserCode(mLLGrpClaimUserSchema.getUserCode()); // 用户编码

		logger.debug("---------Go To UserInsert()----------"
				+ tLLGrpClaimUserDB.getCount());

		// 如果理赔师用户表无数据，则添加数据
		if (tLLGrpClaimUserDB.getCount() == 0)
		{
			LLGrpClaimUserSchema tLLGrpClaimUserSchema = new LLGrpClaimUserSchema();
			
			tLLGrpClaimUserSchema.setSchema(mLLGrpClaimUserSchema);

			//分公司签批人为必须经手人
			if(tLLGrpClaimUserSchema.getJobCategory().trim().toUpperCase().equals("TC"))
			{
				tLLGrpClaimUserSchema.setHandleFlag("1");
			}
			
			if(tLLGrpClaimUserSchema.getRgtFlag()==null||tLLGrpClaimUserSchema.getRgtFlag().trim().equals(""))
			{
				tLLGrpClaimUserSchema.setRgtFlag("0");//默认在岗
			}
			
			tLLGrpClaimUserSchema.setOperator(mGlobalInput.Operator);
			tLLGrpClaimUserSchema.setManageCom(mGlobalInput.ManageCom);
			tLLGrpClaimUserSchema.setMakeDate(CurrentDate);
			tLLGrpClaimUserSchema.setMakeTime(CurrentTime);
			tLLGrpClaimUserSchema.setModifyDate(CurrentDate);
			tLLGrpClaimUserSchema.setModiftTime(CurrentTime);
			map.put(tLLGrpClaimUserSchema, "INSERT");
			return true;
		} 
		else 
		{
			// @@错误处理
			CError.buildErr(this, "该用户已经存在于表中!");
			return false;
		}

	}

	/**
	 * 进行理赔用户的删除
	 * 
	 * @return
	 */
	private boolean UserDelete() {
		LLGrpClaimUserSchema tLLGrpClaimUserSchema = new LLGrpClaimUserSchema();
		tLLGrpClaimUserSchema.setSchema(mLLGrpClaimUserSchema);
		map.put(tLLGrpClaimUserSchema, "DELETE");
		return true;
	}

	/**
	 * 进行理赔用户的修改
	 * 
	 * @return
	 */
	private boolean UserUpdate() {

		LLGrpClaimUserSchema tLLGrpClaimUserSchema = new LLGrpClaimUserSchema();
		tLLGrpClaimUserSchema.setSchema(mLLGrpClaimUserSchema);
		tLLGrpClaimUserSchema.setOperator(this.mGlobalInput.Operator);
		tLLGrpClaimUserSchema.setManageCom(this.mGlobalInput.ManageCom);
		tLLGrpClaimUserSchema.setMakeDate(this.CurrentDate);
		tLLGrpClaimUserSchema.setMakeTime(this.CurrentTime);
		tLLGrpClaimUserSchema.setModifyDate(this.CurrentDate);
		tLLGrpClaimUserSchema.setModiftTime(this.CurrentTime);
		map.put(tLLGrpClaimUserSchema, "UPDATE");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			// mResult.add(mLLAccidentSchema);//供前台界面使用
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLReportBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 提供返回前台界面的数据
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 用于测试
	 * 
	 * @return
	 */
	public static void main(String[] args) {
	}

}
