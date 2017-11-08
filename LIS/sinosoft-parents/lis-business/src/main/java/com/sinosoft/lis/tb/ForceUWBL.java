package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
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
 * @author HL
 * @version 6.0
 */
public class ForceUWBL {
private static Logger logger = Logger.getLogger(ForceUWBL.class);
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
	private LCContSchema mLCContSchema = new LCContSchema();

	public ForceUWBL() {
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
		logger.debug("now in ForceUWBL submit");
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
		logger.debug("Start ForceUWBL Submit...");
		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
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
			// 合同表
			mLCContSchema.setSchema((LCContSchema) mInputData
					.getObjectByObjectName("LCContSchema", 0));
			return true;
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "ForceUWBL";
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
			if (this.mLCContSchema.getForceUWFlag() == null
					|| (!this.mLCContSchema.getForceUWFlag().equals("1") && !this.mLCContSchema
							.getForceUWFlag().equals("0"))) {
				CError tError = new CError();
				tError.moduleName = "ForceUWBL";
				tError.functionName = "checkData";
				tError.errorMessage = "强制进入人工核保，代码录入错误!";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 如果选择强制进入人工核保需要录入原因
			// 暂时注释掉
			if (this.mLCContSchema.getForceUWFlag().equals("1")) {
				if (this.mLCContSchema.getForceUWReason() == null
						&& this.mLCContSchema.getForceUWReason().equals("")) {

					CError tError = new CError();
					tError.moduleName = "ForceUWBL";
					tError.functionName = "checkData";
					tError.errorMessage = "如选择强制进入人工核保，需要录入强制原因!";
					this.mErrors.addOneError(tError);
					return false;
				}
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
		if (mOperate.equals("UPDATE")) {
			String updateSQL = "update LCCont set ForceUWFlag='"
					+ "?ForceUWFlag?" + "',ForceUWReason='"
					+ "?ForceUWReason?" + "',ModifyDate='"
					+ "?ModifyDate?" + "',ModifyTime='" + "?ModifyTime?"
					+ "',Operator='" + "?Operator?"
					+ "' where ContNo='" + "?ContNo?" + "'";
			logger.debug("--update SQL==" + updateSQL);
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(updateSQL);
			sqlbv.put("ForceUWFlag", this.mLCContSchema.getForceUWFlag());
			sqlbv.put("ForceUWReason", this.mLCContSchema.getForceUWReason());
			sqlbv.put("ModifyDate", theCurrentDate);
			sqlbv.put("ModifyTime", this.theCurrentTime);
			sqlbv.put("Operator", this.mGlobalInput.Operator);
			sqlbv.put("ContNo", this.mLCContSchema.getContNo());
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
		mResult.add(mLCContSchema);
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
