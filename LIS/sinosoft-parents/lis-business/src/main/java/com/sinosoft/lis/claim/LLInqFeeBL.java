/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLInqFeeSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 调查费用录入
 * </p>
 * <p>
 * Description: 调查费用录入逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zl
 * @version 1.0
 */
public class LLInqFeeBL {
private static Logger logger = Logger.getLogger(LLInqFeeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private MMap map = new MMap();
	private String mIsReportExist;
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private LLInqFeeSchema mLLInqFeeSchema = new LLInqFeeSchema();

	private GlobalInput mG = new GlobalInput();

	public LLInqFeeBL() {
	}

	public static void main(String[] args) {
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
		logger.debug("----------LLInqFeeBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("----------after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------after checkInputData----------");
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}
		logger.debug("----------after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------after prepareOutputData----------");
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLInqFeeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("--start getInputData()");
		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值
		mIsReportExist = (String) mInputData.getObjectByObjectName("string", 0);
		logger.debug("---getInputData===>mIsReportExist="
				+ mIsReportExist);
		mLLInqFeeSchema = (LLInqFeeSchema) mInputData.getObjectByObjectName(
				"LLInqFeeSchema", 0);

		if (mLLInqFeeSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqFeeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受的信息全部为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		logger.debug("----------begin checkInputData----------");
		try {
			// 非空字段检验
			if (mLLInqFeeSchema.getClmNo() == null)// 赔案号
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLInqFeeBL";
				tError.functionName = "checkInputData";
				tError.errorMessage = "接受的赔案号为空!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (mLLInqFeeSchema.getInqNo() == null)// 调查序号
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLInqFeeBL";
				tError.functionName = "checkInputData";
				tError.errorMessage = "接受的调查序号为空!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (mLLInqFeeSchema.getInqDept() == null)// 调查机构
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLInqFeeBL";
				tError.functionName = "checkInputData";
				tError.errorMessage = "接受的调查机构为空!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (mLLInqFeeSchema.getFeeType() == null)// 费用类型
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLInqFeeBL";
				tError.functionName = "checkInputData";
				tError.errorMessage = "接受的费用类型为空!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimAuditBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "在校验输入的数据时出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		logger.debug("------start dealData-----" + cOperate);

		boolean tReturn = false;

		// 如果还没判断事件是否存在,在这里判断,返回mIsReportExist: true or false
		if (mIsReportExist.equals("")) {
			// ***************************************
			// 判断事件是否存在,同LLInqFee.js
			// ***************************************

			String strSql = "select * from LLInqFee where " + "ClmNo = '"
					+ "?ClmNo?" + "'" + " and InqNo = '"
					+ "?InqNo?" + "'" + " and InqDept = '"
					+ "?InqDept?" + "'" + " and FeeType = '"
					+ "?FeeType?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(strSql);
			sqlbv.put("ClmNo", mLLInqFeeSchema.getClmNo());
			sqlbv.put("InqNo", mLLInqFeeSchema.getInqNo());
			sqlbv.put("InqDept", mLLInqFeeSchema.getInqDept());
			sqlbv.put("FeeType", mLLInqFeeSchema.getFeeType());
			ExeSQL exesql = new ExeSQL();
			String tResult = exesql.getOneValue(sqlbv);
			logger.debug("-----判断事件是否存在tResult.length()= "
					+ tResult.length());
			if (tResult.length() > 0) {
				mIsReportExist = "true";
			} else {
				mIsReportExist = "false";
			}
			logger.debug("-----after dealData==>mIsReportExist= "
					+ mIsReportExist);
		}
		// 添加纪录
		if (cOperate.equals("INSERT")) {
			logger.debug("---start deal insert and mIsReportExist="
					+ mIsReportExist);

			if (mIsReportExist.equals("true"))// 事件存在
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLInqFeeBL";
				tError.functionName = "dealData";
				tError.errorMessage = "记录已存在!";
				this.mErrors.addOneError(tError);

				tReturn = false;
			} else {
				logger.debug("----go to false deal;");
				// 调查费用表
				mLLInqFeeSchema.setOperator(mG.Operator);
				mLLInqFeeSchema.setMakeDate(CurrentDate);
				mLLInqFeeSchema.setMakeTime(CurrentTime);
				mLLInqFeeSchema.setModifyDate(CurrentDate);
				mLLInqFeeSchema.setModifyTime(CurrentTime);

				map.put(mLLInqFeeSchema, "INSERT");

				tReturn = true;
			}
		}
		return tReturn;
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
			mResult.add(mLLInqFeeSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqFeeBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

}
