/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLCommendHospitalDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLCommendHospitalSchema;
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

public class LLCommendHospitalBL {
private static Logger logger = Logger.getLogger(LLCommendHospitalBL.class);
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
	private LLCommendHospitalSchema mLLCommendHospitalSchema = new LLCommendHospitalSchema();

	public LLCommendHospitalBL() {
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
		logger.debug("----------LLCommendHospitalBL Begin----------");
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
			tError.moduleName = "LLCommendHospitalBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		logger.debug("----------LLCommendHospitalBL End----------");
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
		mLLCommendHospitalSchema = (LLCommendHospitalSchema) mInputData
				.getObjectByObjectName("LLCommendHospitalSchema", 0);
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
			tError.moduleName = "LLCommendHospitalBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCommendHospitalBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输操作符失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLLCommendHospitalSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCommendHospitalBL";
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
		if (mOperate.equals("Hospital||INSERT")) {
			if (!HospitalInsert()) {
				return false;
			}
		}

		// 进行理赔用户的修改
		if (mOperate.equals("Hospital||UPDATE")) {
			if (!HospitalUpdate()) {
				return false;
			}
		}

		// 进行理赔用户的删除
		if (mOperate.equals("Hospital||DELETE")) {
			if (!HospitalDelete()) {
				return false;
			}
		}
		return tReturn;
	}

	/**
	 * 进行理赔用户管理的录入
	 * 
	 * @return
	 */
	private boolean HospitalInsert() {
		logger.debug("---------Go To HospitalInsert()----------");

		// 进行推荐医院管理主表的数据查询
		LLCommendHospitalDB tLLCommendHospitalDB = new LLCommendHospitalDB();
		tLLCommendHospitalDB.setHospitalCode(mLLCommendHospitalSchema
				.getHospitalCode()); // 用户编码

		logger.debug("---------Go To HospitalInsert()----------"
				+ tLLCommendHospitalDB.getCount());
		// 如果推荐医院列表无数据，则添加数据
		if (tLLCommendHospitalDB.getCount() == 0) {
			LLCommendHospitalSchema tLLCommendHospitalSchema = new LLCommendHospitalSchema();
			tLLCommendHospitalSchema.setSchema(mLLCommendHospitalSchema);
			tLLCommendHospitalSchema.setMngCom(mGlobalInput.ManageCom);
			tLLCommendHospitalSchema.setOperator(mGlobalInput.Operator);
			tLLCommendHospitalSchema.setMakeDate(CurrentDate);
			tLLCommendHospitalSchema.setMakeTime(CurrentTime);
			tLLCommendHospitalSchema.setModifyDate(CurrentDate);
			tLLCommendHospitalSchema.setModifyTime(CurrentTime);
			map.put(tLLCommendHospitalSchema, "INSERT");
			return true;
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCommendHospitalBL";
			tError.functionName = "HospitalInsert";
			tError.errorMessage = "该医院已经存在于表中!";
			this.mErrors.addOneError(tError);
			return false;
		}

	}

	/**
	 * 进行推荐医院的删除
	 * 
	 * @return
	 */
	private boolean HospitalDelete() {
		LLCommendHospitalSchema tLLCommendHospitalSchema = new LLCommendHospitalSchema();
		tLLCommendHospitalSchema.setSchema(mLLCommendHospitalSchema);
		map.put(tLLCommendHospitalSchema, "DELETE");
		return true;
	}

	/**
	 * 进行推荐医院的修改
	 * 
	 * @return
	 */
	private boolean HospitalUpdate() {

		LLCommendHospitalSchema tLLCommendHospitalSchema = new LLCommendHospitalSchema();
		tLLCommendHospitalSchema.setSchema(mLLCommendHospitalSchema);
		tLLCommendHospitalSchema.setMngCom(mGlobalInput.ManageCom);
		tLLCommendHospitalSchema.setOperator(mGlobalInput.Operator);
		tLLCommendHospitalSchema.setMngCom(mGlobalInput.ManageCom);
		tLLCommendHospitalSchema.setMakeDate(this.CurrentDate);
		tLLCommendHospitalSchema.setMakeTime(this.CurrentTime);
		tLLCommendHospitalSchema.setModifyDate(CurrentDate);
		tLLCommendHospitalSchema.setModifyTime(CurrentTime);
		map.put(tLLCommendHospitalSchema, "UPDATE");
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
			tError.moduleName = "LLCommendHospitalBL";
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
