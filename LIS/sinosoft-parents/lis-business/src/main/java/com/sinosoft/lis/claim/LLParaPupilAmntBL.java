/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLParaPupilAmntDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLParaPupilAmntSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

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

public class LLParaPupilAmntBL {
private static Logger logger = Logger.getLogger(LLParaPupilAmntBL.class);
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
	private LLParaPupilAmntSchema mLLParaPupilAmntSchema = new LLParaPupilAmntSchema();

	public LLParaPupilAmntBL() {
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
		logger.debug("----------LLParaPupilAmntBL Begin----------");
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
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLParaPupilAmntBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		logger.debug("----------LLParaPupilAmntBL End----------");
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
		mLLParaPupilAmntSchema = (LLParaPupilAmntSchema) mInputData
				.getObjectByObjectName("LLParaPupilAmntSchema", 0);
		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		logger.debug("---------Go To checkData----------");
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaPupilAmntBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaPupilAmntBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输操作符失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLLParaPupilAmntSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaPupilAmntBL";
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
		logger.debug("---------Go To dealData----------");

		boolean tReturn = true;

		// 进行理赔用户的录入
		if (mOperate.equals("BaseValue||INSERT")) {
			if (!BaseValueInsert()) {
				return false;
			}
		}

		// 进行理赔用户的修改
		if (mOperate.equals("BaseValue||UPDATE")) {
			if (!BaseValueUpdate()) {
				return false;
			}
		}

		// 进行理赔用户的删除
		if (mOperate.equals("BaseValue||DELETE")) {
			if (!BaseValueDelete()) {
				return false;
			}
		}
		return tReturn;
	}

	/**
	 * 进行未成年人保额标准管理的录入
	 * 
	 * @return
	 */
	private boolean BaseValueInsert() {
		logger.debug("---------Go To BaseValueInsert()----------");

		// 进行未成年人保额标准表的数据查询
		LLParaPupilAmntDB tLLParaPupilAmntDB = new LLParaPupilAmntDB();
		tLLParaPupilAmntDB.setComCode(mLLParaPupilAmntSchema.getComCode()); // 管理机构代码
		tLLParaPupilAmntDB.setStartDate(mLLParaPupilAmntSchema.getStartDate());
		logger.debug("---------Go To BaseValueInsert()----------"
				+ tLLParaPupilAmntDB.getCount());
		// 如果未成年人保额标准表无数据，则添加数据
		if (tLLParaPupilAmntDB.getCount() == 0) {
			LLParaPupilAmntSchema tLLParaPupilAmntSchema = new LLParaPupilAmntSchema();
			tLLParaPupilAmntSchema.setSchema(mLLParaPupilAmntSchema);
			// tLLParaPupilAmntSchema.setComCode(mGlobalInput.ManageCom);
			tLLParaPupilAmntSchema.setOperator(mGlobalInput.Operator);
			tLLParaPupilAmntSchema.setMakeDate(CurrentDate);
			tLLParaPupilAmntSchema.setMakeTime(CurrentTime);
			tLLParaPupilAmntSchema.setModifyDate(CurrentDate);
			tLLParaPupilAmntSchema.setModifyTime(CurrentTime);
			map.put(tLLParaPupilAmntSchema, "INSERT");
			return true;
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaPupilAmntBL";
			tError.functionName = "BaseValueInsert";
			tError.errorMessage = "该未成年人标额标准已经存在于表中!";
			this.mErrors.addOneError(tError);
			return false;
		}

	}

	/**
	 * 进行未成年人保额标准的删除
	 * 
	 * @return
	 */
	private boolean BaseValueDelete() {
		LLParaPupilAmntSchema tLLParaPupilAmntSchema = new LLParaPupilAmntSchema();
		tLLParaPupilAmntSchema.setSchema(mLLParaPupilAmntSchema);
		map.put(tLLParaPupilAmntSchema, "DELETE");
		return true;
	}

	/**
	 * 进行未成年人保额标准的修改
	 * 
	 * @return
	 */
	private boolean BaseValueUpdate() {

		LLParaPupilAmntSchema tLLParaPupilAmntSchema = new LLParaPupilAmntSchema();
		tLLParaPupilAmntSchema.setSchema(mLLParaPupilAmntSchema);
		// tLLParaPupilAmntSchema.setComCode(mGlobalInput.ManageCom);
		tLLParaPupilAmntSchema.setOperator(mGlobalInput.Operator);
		tLLParaPupilAmntSchema.setMakeDate(this.CurrentDate);
		tLLParaPupilAmntSchema.setMakeTime(this.CurrentTime);
		tLLParaPupilAmntSchema.setModifyDate(CurrentDate);
		tLLParaPupilAmntSchema.setModifyTime(CurrentTime);
		map.put(tLLParaPupilAmntSchema, "UPDATE");
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
			// mResult.add(map);//供前台界面使用
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaPupilAmntBL";
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
