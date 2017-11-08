/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLGrpClaimPopedomDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLGrpClaimPopedomSchema;
import com.sinosoft.lis.vschema.LLGrpClaimPopedomSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:提交理赔权限信息类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author:
 * @version 1.0
 */

public class LLGrpClaimPopedomBL {
private static Logger logger = Logger.getLogger(LLGrpClaimPopedomBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */

	private MMap map = new MMap();
	/** 往后面传输的数据库操作 */
	private MMap mapDel = new MMap();
	/** 执行删除的数据库操作，放在最后 */

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	// private LLGrpClaimPopedomDB mLLGrpClaimPopedomDB = new LLGrpClaimPopedomDB();

	private LLGrpClaimPopedomSchema mLLGrpClaimPopedomSchema = new LLGrpClaimPopedomSchema();

	public LLGrpClaimPopedomBL() {
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
		logger.debug("----------LLGrpClaimPopedomBL Begin----------");
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
		if (!tPubSubmit.submitData(mInputData, "")) {
			
			// @@错误处理
			CError.buildErr(this, "数据提交失败!");
			return false;
		}
		mInputData = null;

		logger.debug("----------LLGrpClaimPopedomBL End----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {

		mInputData = cInputData;
		this.mOperate = cOperate;

		logger.debug("LLGrpClaimPopedomBL测试--" + this.mOperate);

		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mTransferData =
		 (TransferData)mInputData.getObjectByObjectName("TransferData", 0);

		mLLGrpClaimPopedomSchema = (LLGrpClaimPopedomSchema) mInputData
				.getObjectByObjectName("LLGrpClaimPopedomSchema", 0);
		// 或者使用this.mLLGrpClaimPopedomSchema.setSchema((LLGrpClaimPopedomSchema)mInputData.getObjectByObjectName("LLGrpClaimPopedomSchema",0));
		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpClaimPopedomBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		String tOperator = mGlobalInput.Operator;
		if (tOperator == null || tOperator.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpClaimPopedomBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据[操作员编码]失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLLGrpClaimPopedomSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpClaimPopedomBL";
			tError.functionName = "checkData";
			tError.errorMessage = "接受的Schema信息全部为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		boolean tReturn = false;

		// 进行理赔权限的录入
		if (this.mOperate.equals("Popedom||INSERT")) {
			if (!PopedomInsert()) {
				return false;
			}
		}

		// 进行理赔权限的删除
		if (this.mOperate.equals("Popedom||DELETE")) {
			if (!PopedomDelete()) {
				return false;
			}
		}

		// 进行理赔权限的修改
		if (this.mOperate.equals("Popedom||UPDATE")) {
			if (!PopedomUpdate()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 进行理赔类型的权限数据查询
	 * 
	 * @return
	 */
	private boolean PopedomInsert() {
		// 进行理赔类型的权限数据查询
		LLGrpClaimPopedomDB tLLGrpClaimPopedomDB = new LLGrpClaimPopedomDB();

		tLLGrpClaimPopedomDB.setJobCategory(mLLGrpClaimPopedomSchema.getJobCategory()); // 权限级别
		tLLGrpClaimPopedomDB.setCaseCategory(mLLGrpClaimPopedomSchema.getCaseCategory());//案件类型
		tLLGrpClaimPopedomDB.setStartDate(mLLGrpClaimPopedomSchema.getStartDate()); // 启用日期
		tLLGrpClaimPopedomDB.setMngCom(mLLGrpClaimPopedomSchema.getMngCom()); // 管理机构

		LLGrpClaimPopedomSet tLLGrpClaimPopedomSet = new LLGrpClaimPopedomSet();
		tLLGrpClaimPopedomSet = tLLGrpClaimPopedomDB.query();
		// 或者是 tLLGrpClaimPopedomSet.set(tLLGrpClaimPopedomDB.query());

		if (tLLGrpClaimPopedomDB.mErrors.needDealError() == true) {
			
			// @@错误处理
			CError.buildErr(this, "理赔权限表查询失败,原因是:"+tLLGrpClaimPopedomDB.mErrors.getLastError());
			return false;
		}

		// 如果理赔师权限表无数据，则添加数据
		if (tLLGrpClaimPopedomSet == null || tLLGrpClaimPopedomSet.size() == 0) {
			LLGrpClaimPopedomSchema tLLGrpClaimPopedomSchema = new LLGrpClaimPopedomSchema();

			//tLLGrpClaimPopedomSchema.setPopedomLevel(mLLGrpClaimPopedomSchema.getPopedomLevel());
			tLLGrpClaimPopedomSchema.setSchema(mLLGrpClaimPopedomSchema);
			tLLGrpClaimPopedomSchema.setOperator(this.mGlobalInput.Operator);
			tLLGrpClaimPopedomSchema.setMngCom(this.mGlobalInput.ManageCom);
			tLLGrpClaimPopedomSchema.setMakeDate(this.CurrentDate);
			tLLGrpClaimPopedomSchema.setMakeTime(this.CurrentTime);
			tLLGrpClaimPopedomSchema.setModifyDate(this.CurrentDate);
			tLLGrpClaimPopedomSchema.setModifyTime(this.CurrentTime);

			map.put(tLLGrpClaimPopedomSchema, "INSERT");

		} 
		else 
		{
			// @@错误处理
			CError.buildErr(this, "该条信息已存在!");
			return false;
		}

		return true;
	}

	/**
	 * 进行理赔权限的删除
	 * 
	 * @return
	 */
	private boolean PopedomDelete() {
		
		map.put("delete from LLGrpClaimPopedom where JobCategory='"+mTransferData.getValueByName("OriJobCategory")+"' and CaseCategory='"+mTransferData.getValueByName("OriCaseCategory")+"'", "DELETE");
		return true;
	}

	/**
	 * 进行理赔权限的修改
	 * 
	 * @return
	 */
	private boolean PopedomUpdate() {

		LLGrpClaimPopedomSchema tLLGrpClaimPopedomSchema = new LLGrpClaimPopedomSchema();
		tLLGrpClaimPopedomSchema.setSchema(mLLGrpClaimPopedomSchema);

		tLLGrpClaimPopedomSchema.setOperator(this.mGlobalInput.Operator);
		tLLGrpClaimPopedomSchema.setMngCom(this.mGlobalInput.ManageCom);
		tLLGrpClaimPopedomSchema.setMakeDate(this.CurrentDate);
		tLLGrpClaimPopedomSchema.setMakeTime(this.CurrentTime);
		tLLGrpClaimPopedomSchema.setModifyDate(this.CurrentDate);
		tLLGrpClaimPopedomSchema.setModifyTime(this.CurrentTime);

		// logger.debug("LLMedicalFeeInpBL测试--tDay"+tDay);

		map.put("delete from LLGrpClaimPopedom where JobCategory='"+mTransferData.getValueByName("OriJobCategory")+"' and CaseCategory='"+mTransferData.getValueByName("OriCaseCategory")+"'", "DELETE");
		map.put(tLLGrpClaimPopedomSchema, "INSERT");
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
