package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LATreeSchema;
import com.sinosoft.lis.db.LATreeDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.vschema.LATreeSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

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
	private LATreeSchema mLATreeSchema = new LATreeSchema();

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
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
			if(this.mOperate.equals("AGENT&&UPDATE")){
			   //业务员
				mLATreeSchema.setSchema((LATreeSchema) mInputData
					.getObjectByObjectName("LATreeSchema", 0));
			}else{
				mLDPersonSchema.setSchema((LDPersonSchema) mInputData
						.getObjectByObjectName("LDPersonSchema", 0));
			}
			// 客户表
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
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {
		String tNowDate = PubFun.getCurrentDate();
		String tNowTime = PubFun.getCurrentTime();
		if(this.mOperate.equals("AGENT&&UPDATE")){
			//此为业务员黑名单表latree
			LATreeSchema tLATreeSchema =new LATreeSchema();
			LATreeDB tLATreeDB =new LATreeDB();
			LATreeSet tLATreeSet =new LATreeSet();
			tLATreeDB.setAgentCode(mLATreeSchema.getAgentCode());
			tLATreeSet = tLATreeDB.query();
			if(tLATreeSet.size()<=0){
                  CError.buildErr(this, "代码为："+mLATreeSchema.getAgentCode()+"的业务员不存在！");
                  return false;
			}else{
				tLATreeSchema = tLATreeSet.get(1);
				tLATreeSchema.setBlackLisFlag(mLATreeSchema.getBlackLisFlag());
				tLATreeSchema.setReasonType(mLATreeSchema.getReasonType());
				tLATreeSchema.setReason(mLATreeSchema.getReason());
				tLATreeSchema.setOperator(this.mGlobalInput.Operator);
				//ModifyDate&ModifyTime待定
				tLATreeSet =new LATreeSet();
				tLATreeSet.add(tLATreeSchema);
				map.put(tLATreeSchema, "UPDATE");
			}
			
		}else{
			//CUSTOMER&&UPDATE
			LDPersonSchema tLDPersonSchema =new LDPersonSchema();
			LDPersonDB tLDPersonDB =new LDPersonDB();
			LDPersonSet tLDPersonSet =new LDPersonSet();
			tLDPersonDB.setCustomerNo(mLDPersonSchema.getCustomerNo());
			tLDPersonSet = tLDPersonDB.query();
			if(tLDPersonSet.size()<0){
				CError.buildErr(this, "客户号为："+mLDPersonSchema.getCustomerNo()+"的客户不存在！");
				return false;
			}else{
				tLDPersonSchema =tLDPersonSet.get(1);
				tLDPersonSchema.setBlacklistFlag(mLDPersonSchema.getBlacklistFlag());
				tLDPersonSchema.setRemark(mLDPersonSchema.getRemark());
				tLDPersonSchema.setModifyDate(tNowDate);
				tLDPersonSchema.setModifyTime(tNowTime);
				tLDPersonSchema.setOperator(this.mGlobalInput.Operator);
				tLDPersonSet = new LDPersonSet();
				tLDPersonSet.add(tLDPersonSchema);
				map.put(tLDPersonSet, "UPDATE");
			}
		}
		if (mOperate.equals("UPDATE")) {
			String updateSQL = "update LDPerson set BlacklistFlag='"
					+ "?BlacklistFlag?" + "',Remark='"
					+ "?Remark?" + "',ModifyDate='"
					+ "?theCurrentDate?" + "',ModifyTime='" + "?theCurrentTime?"
					+ "',Operator='" + "?Operator?"
					+ "' where CustomerNo='"
					+ "?CustomerNo?" + "'";
			logger.debug("--update SQL==" + updateSQL);
			SQLwithBindVariables sqlbv= new SQLwithBindVariables();
			sqlbv.sql(updateSQL);
			sqlbv.put("BlacklistFlag", this.mLDPersonSchema.getBlacklistFlag());
			sqlbv.put("Remark", this.mLDPersonSchema.getRemark());
			sqlbv.put("theCurrentDate", this.theCurrentDate);
			sqlbv.put("theCurrentTime", this.theCurrentTime);
			sqlbv.put("Operator", this.mGlobalInput.Operator);
			sqlbv.put("CustomerNo", this.mLDPersonSchema.getCustomerNo());
			this.map.put(sqlbv, "UPDATE");
		}

		if (mOperate.equals("DELETE")) {
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
		mInputData.clear();
		mInputData.add(map);
		mResult.clear();
		if(this.mOperate.equals("AGENT||INSERT")){
			mResult.add(mLATreeSchema);
		}else{
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
