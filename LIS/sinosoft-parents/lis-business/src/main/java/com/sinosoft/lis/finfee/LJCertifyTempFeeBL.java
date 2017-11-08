/**
 * Copyright (c) 2006 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJCertifyTempFeeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJCertifyTempFeeSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证保证金管理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author: wl
 * @version 1.0
 */

public class LJCertifyTempFeeBL {
private static Logger logger = Logger.getLogger(LJCertifyTempFeeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private String tLimit = "";
	// **生成的暂交费号
	private String tNo = "";
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private String mLimit = "";
	/** 数据操作字符串 */
	private String mOperate;
	private MMap map = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private String mManageCom = "";
	private String mPolicyCom = "";
	private LJCertifyTempFeeSchema mLJCertifyTempFeeSchema = new LJCertifyTempFeeSchema();

	public LJCertifyTempFeeBL() {
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
		logger.debug("----------LJCertifyTempFeeBL Begin----------");
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
			tError.moduleName = "LJCertifyTempFeeBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		logger.debug("----------LJCertifyTempFeeBL End----------");
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
		mLJCertifyTempFeeSchema = (LJCertifyTempFeeSchema) mInputData
				.getObjectByObjectName("LJCertifyTempFeeSchema", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mManageCom = (String) mTransferData.getValueByName("ManageComQ");// 收付机构
		mPolicyCom = (String) mTransferData.getValueByName("PolicyComQ");// 管理机构
		if (mLJCertifyTempFeeSchema == null) {
			logger.debug("没有得到输入的数据");
			return false;
		}
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
			tError.moduleName = "LJCertifyTempFeeBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJCertifyTempFeeBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输操作符失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLJCertifyTempFeeSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJCertifyTempFeeBL";
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

		// 进行暂交费信息的录入
		if (mOperate.equals("CertifyTempFee||INSERT")) {
			if (!CertifyTempFeeInsert()) {
				return false;
			}
		}

		return tReturn;
	}

	/**
	 * 进行单证保证金暂收费表的录入
	 * 
	 * @return
	 */
	private boolean CertifyTempFeeInsert() {
		logger.debug("---------Go To CertifyTempFeeInsert()----------");

		// 进行单证暂交费表的数据查询
		LJCertifyTempFeeDB tLJCertifyTempFeeDB = new LJCertifyTempFeeDB();
		tLJCertifyTempFeeDB.setIDNo(mLJCertifyTempFeeSchema.getIDNo()); // 证件号码
		tLJCertifyTempFeeDB.setIDType(mLJCertifyTempFeeSchema.getIDType()); // 证件类型
		tLJCertifyTempFeeDB.setConfFlag("0");// 业务员做了暂收退费后，仍可以进行缴纳保证金并入司--haopan

		logger.debug("---------Go To CertifyTempFeeInsert()----------"
				+ tLJCertifyTempFeeDB.getCount());
		// 如果单证暂交费表无数据，则添加数据
		if (tLJCertifyTempFeeDB.getCount() == 0) {
			LJCertifyTempFeeSchema tLJCertifyTempFeeSchema = new LJCertifyTempFeeSchema();
			tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom); // 产生通知书号即暂交费号
			tNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit);
			tLJCertifyTempFeeSchema.setSchema(mLJCertifyTempFeeSchema);
			tLJCertifyTempFeeSchema.setManageCom(mManageCom);
			// tLJCertifyTempFeeSchema.setPolicyCom(mGlobalInput.ManageCom);
			// tLJCertifyTempFeeSchema.setPolicyCom(mPolicyCom);
			tLJCertifyTempFeeSchema.setGetNoticeNo(tNo);
			tLJCertifyTempFeeSchema.setOperator(mGlobalInput.Operator);
			tLJCertifyTempFeeSchema.setMakeDate(CurrentDate);
			tLJCertifyTempFeeSchema.setMakeTime(CurrentTime);
			tLJCertifyTempFeeSchema.setModifyDate(CurrentDate);
			tLJCertifyTempFeeSchema.setModifyTime(CurrentTime);
			tLJCertifyTempFeeSchema.setConfFlag("0");
			map.put(tLJCertifyTempFeeSchema, "INSERT");
			mLimit = tNo;
			return true;
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJCertifyTempFeeBL";
			tError.functionName = "CertifyTempFeeInsert";
			tError.errorMessage = "该业务员已经存在!";
			this.mErrors.addOneError(tError);
			return false;
		}

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
			tError.moduleName = "LJCertifyTempFeeBL";
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
	public String getLimit() {
		return mLimit;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 用于测试
	 * 
	 * @return
	 */
	public static void main(String[] args) {
		// LJCertifyTempFeeSchema tLJCertifyTempFeeSchema = new
		// LJCertifyTempFeeSchema();
		// //准备后台数据
		// tLJCertifyTempFeeSchema.setPayMoney("100.00"); //交费金额
		// tLJCertifyTempFeeSchema.setIDType("1"); //证件类型
		// tLJCertifyTempFeeSchema.setIDNo("037"); //证件号码
		// tLJCertifyTempFeeSchema.setPolicyCom("86"); //管理机构
		// tLJCertifyTempFeeSchema.setManageCom("86");
		// tLJCertifyTempFeeSchema.setPayDate("2006-8-31"); //交费日期
		//
		// VData tVData = new VData();
		//
		// GlobalInput tGI = new GlobalInput();
		// tGI.ComCode = "8621";
		// tGI.ManageCom = "8621";
		// tGI.Operator = "001";
		// tVData.add(tGI);
		// tVData.add("CertifyTempFee||INSERT");
		// tVData.add(tLJCertifyTempFeeSchema);
		// LJCertifyTempFeeUI tLJCertifyTempFeeUI = new LJCertifyTempFeeUI();
		// if (!tLJCertifyTempFeeUI.submitData(tVData,
		// "CertifyTempFee||INSERT"))
		// {
		// logger.debug("Error!!!!!!!!!!");
		// }
	}

}
