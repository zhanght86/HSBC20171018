package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LDGrpBlackListTraceSchema;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.vschema.LDGrpSet;
import com.sinosoft.lis.vschema.LDGrpBlackListTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.Reflections;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ccvip
 * @version 6.0
 */
public class UWQualityManageBL {
private static Logger logger = Logger.getLogger(UWQualityManageBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();

	/** 需要传到关系的数据* */

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	/** 业务处理相关变量 */
	private LDPersonSchema mLDPersonSchema = new LDPersonSchema();
	private LDGrpSchema mLDGrpSchema = new LDGrpSchema();
	private Reflections ref = new Reflections();

	public UWQualityManageBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("now in UWQualityManageBL submit");
		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false)
			return false;
		logger.debug("---getInputData---");
		if (this.checkData() == false)
			return false;
		logger.debug("---checkData---");

		// 根据业务逻辑对数据进行处理
		if (this.dealData() == false)
			return false;

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		// 数据提交、保存
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start UWQualityManageBL Submit...");
		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWQualityManageBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---commitData---");

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
			//不知道是不是以前操作LDPerson 所以保留
			if("CUSTOMER||QUALITY".equals(mOperate)){
				// 团体客户表
				mLDGrpSchema.setSchema((LDGrpSchema) mInputData
						.getObjectByObjectName("LDGrpSchema", 0));
			}else{
				// 客户表
				mLDPersonSchema.setSchema((LDPersonSchema) mInputData
						.getObjectByObjectName("LDPersonSchema", 0));
			}
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
			return true;
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "UWQualityManageBL";
			tError.functionName = "checkData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;

		}

	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		//将以前的逻辑都放在else中 因为不知道是否有用 不会影响当前程序
		if("CUSTOMER||QUALITY".equals(mOperate)){
				LDGrpDB tLDGrpDB =new LDGrpDB();
				tLDGrpDB.setCustomerNo(mLDGrpSchema.getCustomerNo());
				if(!tLDGrpDB.getInfo()){
					CError.buildErr(this, "客户号："+mLDGrpSchema.getCustomerNo()+" 错误或无此客户！请确认");
					return false;
			}
		}else{
			if (mOperate.equals("UPDATE")) {
				if (this.mLDPersonSchema.getCustomerNo() == null) {
					CError tError = new CError();
					tError.moduleName = "UWQualityManageBL";
					tError.functionName = "checkData";
					tError.errorMessage = "进入客户品质管理，传如数据错误!";
					this.mErrors.addOneError(tError);
					return false;
				}
				
			}
			if (mOperate.equals("DELETE")) {
				
			}
		}
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {
		String tNowDate =PubFun.getCurrentDate();
		String tNowTime =PubFun.getCurrentTime();
		if("CUSTOMER||QUALITY".equals(mOperate)){
//			ref.transFields(mLCInsuredSchema, mLDPersonSchema); // 客户表
			//想查询要修改的客户的当前信息 保存到黑名单轨迹表中
			String tSerialNo =PubFun1.CreateMaxNo("BLACKLISTTRACE", 20);
			LDGrpDB tLDGrpDB =new LDGrpDB();
			LDGrpSchema tLDGrpSchema=new LDGrpSchema();
			LDGrpSet tLDGrpSet =new LDGrpSet();
			LDGrpBlackListTraceSchema tLDGrpBlackListTraceSchema=new LDGrpBlackListTraceSchema();
			LDGrpBlackListTraceSet tLDGrpBlackListTraceSet=new LDGrpBlackListTraceSet();
			tLDGrpDB.setCustomerNo(mLDGrpSchema.getCustomerNo());
			if(!tLDGrpDB.getInfo()){
				CError.buildErr(this, "客户号："+mLDGrpSchema.getCustomerNo()+" 错误或无此客户！请确认");
				return false;
			}
			tLDGrpSchema = tLDGrpDB.getSchema();
			tLDGrpBlackListTraceSchema.setSerialNo(tSerialNo);
			ref.transFields(tLDGrpBlackListTraceSchema, tLDGrpSchema);
			//有可能之前BlackListFlag为空 而轨迹表中要求该字段非空 
			if(tLDGrpSchema.getBlacklistFlag()==null||"".equals(tLDGrpSchema.getBlacklistFlag())){
				tLDGrpBlackListTraceSchema.setBlacklistFlag("0");
			}
			if(tLDGrpSchema.getBlackListReason()==null||"".equals(tLDGrpSchema.getBlackListReason())){
				tLDGrpBlackListTraceSchema.setBlackListReason("0");
			}
			tLDGrpBlackListTraceSchema.setMakeDate(tNowDate);
			tLDGrpBlackListTraceSchema.setMakeTime(tNowTime);
			tLDGrpBlackListTraceSchema.setModifyDate(tNowDate);
			tLDGrpBlackListTraceSchema.setModifyTime(tNowTime);
			tLDGrpBlackListTraceSet.add(tLDGrpBlackListTraceSchema);
			map.put(tLDGrpBlackListTraceSet, "INSERT");
			//将LDGrp表中的原有信息删除
			map.put("Delete from LDGrp where customerno ='"+mLDGrpSchema.getCustomerNo()+"'", "DELETE");
			//修改完将当前信息保存到LDGrp
			tLDGrpSchema.setRemark(mLDGrpSchema.getRemark());
			tLDGrpSchema.setBlacklistFlag(mLDGrpSchema.getBlacklistFlag());
			tLDGrpSchema.setBlackListReason(mLDGrpSchema.getBlackListReason());
			tLDGrpSchema.setModifyDate(tNowDate);
			tLDGrpSchema.setModifyTime(tNowTime);
			tLDGrpSet.add(tLDGrpSchema);
			map.put(tLDGrpSet, "INSERT");			
		}else{
			if (mOperate.equals("UPDATE")) {
				String updateSQL = "update LDPerson set BlacklistFlag='"
					+ this.mLDPersonSchema.getBlacklistFlag() + "',Remark='"
					+ this.mLDPersonSchema.getRemark() + "',ModifyDate='"
					+ theCurrentDate + "',ModifyTime='" + this.theCurrentTime
					+ "',Operator='" + this.mGlobalInput.Operator
					+ "' where CustomerNo='"
					+ this.mLDPersonSchema.getCustomerNo() + "'";
				logger.debug("--update SQL==" + updateSQL);
				this.map.put(updateSQL, "UPDATE");
			}
			if (mOperate.equals("DELETE")) {
			}
		}


		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		if("CUSTOMER||QUALITY".equals(mOperate)){
			mInputData.clear();
			mInputData.add(map);
		}else{
			mInputData.clear();
			mInputData.add(map);
			mResult.clear();
			mResult.add(mLDPersonSchema);
		}
	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */

	public VData getResult() {
		return mResult;
	}

}
