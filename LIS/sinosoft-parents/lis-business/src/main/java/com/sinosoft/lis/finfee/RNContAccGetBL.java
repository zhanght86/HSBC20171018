package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAGetTempFeeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保单账户回退
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author guanwei
 * @version 1.0
 */

public class RNContAccGetBL {
private static Logger logger = Logger.getLogger(RNContAccGetBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private TransferData mTransferData = new TransferData();
	private VData tVData = new VData();

	/** 数据操作字符串 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	// 业务处理相关变量
	private String Operator = "";
	private String ManageCom = "";
	private String ComCode = "";
	private LCContDB mLCContDB = new LCContDB();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private LJAGetTempFeeSet mLJAGetTempFeeSet = new LJAGetTempFeeSet();
	private String mContNo = ""; // 保单号
	private String mGetMoney = ""; // 领取金额
	private String mGetMode = ""; // 领取方式
	private String mBankCode = ""; // 银行代码
	private String mBankAccNo = ""; // 银行帐号
	private String mAccName = ""; // 账户姓名
	private String tLimit = "";
	private String mSerialNo = ""; // 实付流水号
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	public static void main(String[] args) {
		GlobalInput tGI = new GlobalInput();
		tGI.ComCode = "86";
		tGI.Operator = "001";
		tGI.ManageCom = "86";
		TransferData tTransferData = new TransferData();
		String mContNo = "000006618549";
		String mGetMode = "4";
		String mGetMoney = "100.00";
		String mBankCode = "2100010";
		String mBankAccNo = "1234567890123456789";
		String mAccName = "缴宝存";
		tTransferData.setNameAndValue("ContNo", mContNo);
		tTransferData.setNameAndValue("GetMode", mGetMode);
		tTransferData.setNameAndValue("GetMoney", mGetMoney);
		tTransferData.setNameAndValue("BankCode", mBankCode);
		tTransferData.setNameAndValue("BankAccNo", mBankAccNo);
		tTransferData.setNameAndValue("AccName", mAccName);

		VData tVData = new VData();
		tVData.addElement(tTransferData);
		tVData.addElement(tGI);

		RNContAccGetBL tRNContAccGetBL = new RNContAccGetBL();

		if (!tRNContAccGetBL.submitData(tVData, "INSERT")) {
			logger.debug(tRNContAccGetBL.mErrors.getErrContent());
		}
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		logger.debug("Operate==" + cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		logger.debug("End getInputData");

		// 校验数据
		if (!checkData())
			return false;
		logger.debug("End checkData");

		// 进行业务处理
		if (!dealData())
			return false;
		logger.debug("End dealData！");

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		logger.debug("End prepareOutputData");

		// 提交数据
		logger.debug("Start PubSubmit");
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "RNContAccGetBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("\n\nDone");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData) {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		logger.debug("get GlobalInput");
		if (mInputData.getObjectByObjectName("TransferData", 0) != null) {
			logger.debug("begin to get TransferData");
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			mContNo = (String) mTransferData.getValueByName("ContNo");
			logger.debug("==========保单号ContNo==========" + mContNo);
			mGetMoney = (String) mTransferData.getValueByName("GetMoney");
			logger.debug("==========领取金额GetMoney==========" + mGetMoney);
			mGetMode = (String) mTransferData.getValueByName("GetMode");
			logger.debug("==========领取方式GetMode==========" + mGetMode);
			mAccName = (String) mTransferData.getValueByName("AccName");
			logger.debug("==========账户姓名AccName==========" + mAccName);
			mBankCode = (String) mTransferData.getValueByName("BankCode");
			logger.debug("==========银行代码BankCode==========" + mBankCode);
			mBankAccNo = (String) mTransferData.getValueByName("BankAccNo");
			logger.debug("==========银行账号BankAccNo==========" + mBankAccNo);
			logger.debug("end get TransferData");
		}
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RNContAccGetBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 检验
	 * 
	 * @return
	 */
	public boolean checkData() {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "RNContAccGetBL";
			tError.functionName = "checkData";
			tError.errorMessage = "未找到保单相关信息!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		/** @todo ----处理保单表表LCCont----UPDATE */
		mLCContDB.setContNo(mContNo);
		if (!mLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "DifSetBL";
			tError.functionName = "checkData";
			tError.errorMessage = "未找到保单相关信息!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema = mLCContDB.getSchema();
		logger.debug("保单号为:" + mContNo + "；相关信息："
				+ mLCContSchema.toString());
		double tDif = mLCContSchema.getDif() - Double.parseDouble(mGetMoney);
		if (tDif < 0) {
			CError tError = new CError();
			tError.moduleName = "DifSetBL";
			tError.functionName = "checkData";
			tError.errorMessage = "帐户余额不足";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setDif(tDif);
		mMMap.put(mLCContSchema, "UPDATE");
		logger.debug("处理LCCont完毕");

		/** @todo ----处理实付总表LJAGet----INSERT */
		ManageCom = mGlobalInput.ManageCom;
		Operator = mGlobalInput.Operator;
		tLimit = PubFun.getNoLimit(ManageCom);
		mSerialNo = PubFun1.CreateMaxNo("GETNO", tLimit); // 实付流水号

		LJAGetSchema tLJAGetSchema = new LJAGetSchema();

		tLJAGetSchema.setActuGetNo(mSerialNo);
		logger.debug("本次处理LJAGet插入的实付号：" + mSerialNo);
		tLJAGetSchema.setActualDrawer(mLCContSchema.getAppntName());
		tLJAGetSchema.setActualDrawerID(mLCContSchema.getAppntIDNo());
		tLJAGetSchema.setAgentCode(mLCContSchema.getAgentCode());
		tLJAGetSchema.setAgentCom(mLCContSchema.getAgentCom());
		tLJAGetSchema.setAgentGroup(mLCContSchema.getAgentGroup());
		tLJAGetSchema.setAppntNo(mLCContSchema.getAppntNo());
		tLJAGetSchema.setDrawer(mLCContSchema.getAppntName());
		tLJAGetSchema.setDrawerID(mLCContSchema.getAppntIDNo());
		tLJAGetSchema.setGetNoticeNo(mSerialNo);
		tLJAGetSchema.setMakeDate(CurrentDate);
		tLJAGetSchema.setMakeTime(CurrentTime);
		tLJAGetSchema.setManageCom(mLCContSchema.getManageCom());
		tLJAGetSchema.setModifyDate(CurrentDate);
		tLJAGetSchema.setModifyTime(CurrentTime);
		tLJAGetSchema.setOperator(Operator);
		tLJAGetSchema.setOtherNo(mContNo);
		tLJAGetSchema.setOtherNoType("3");
		tLJAGetSchema.setPayMode(mGetMode);
		tLJAGetSchema.setShouldDate(CurrentDate);
		tLJAGetSchema.setStartGetDate(CurrentDate);
		tLJAGetSchema.setSumGetMoney(mGetMoney);

		if (mGetMode.equals("4")) {
			if (mAccName == null || mAccName.equals("")) {
				CError tError = new CError();
				tError.moduleName = "RNContAccGetBL";
				tError.functionName = "dealData";
				tError.errorMessage = "未得到账户姓名!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (mBankCode == null || mBankCode.equals("")) {
				CError tError = new CError();
				tError.moduleName = "RNContAccGetBL";
				tError.functionName = "dealData";
				tError.errorMessage = "未得到银行代码!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (mBankAccNo == null || mBankAccNo.equals("")) {
				CError tError = new CError();
				tError.moduleName = "RNContAccGetBL";
				tError.functionName = "dealData";
				tError.errorMessage = "未得到银行账号!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLJAGetSchema.setAccName(mAccName);
			tLJAGetSchema.setBankCode(mBankCode);
			tLJAGetSchema.setBankAccNo(mBankAccNo);
		}
		mMMap.put(tLJAGetSchema, "INSERT");
		logger.debug("处理LJAGet完毕");

		/** @todo ----处理暂交费退费实付表LJAGetTempFee----INSERT */
		LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
		tLJAGetTempFeeSchema.setActuGetNo(mSerialNo);
		tLJAGetTempFeeSchema.setAgentCode(mLCContSchema.getAgentCode());
		tLJAGetTempFeeSchema.setAgentCom(mLCContSchema.getAgentCom());
		tLJAGetTempFeeSchema.setAgentGroup(mLCContSchema.getAgentGroup());
		tLJAGetTempFeeSchema.setAPPntName(mLCContSchema.getAppntName());
		tLJAGetTempFeeSchema.setGetDate(CurrentDate);
		tLJAGetTempFeeSchema.setGetMoney(mGetMoney);
		tLJAGetTempFeeSchema.setManageCom(mLCContSchema.getManageCom());
		tLJAGetTempFeeSchema.setMakeDate(CurrentDate);
		tLJAGetTempFeeSchema.setMakeTime(CurrentTime);
		tLJAGetTempFeeSchema.setModifyDate(CurrentDate);
		tLJAGetTempFeeSchema.setModifyTime(CurrentTime);
		tLJAGetTempFeeSchema.setOperator(Operator);
		tLJAGetTempFeeSchema.setPayMode(mGetMode);
		tLJAGetTempFeeSchema.setRiskCode("000000");
		tLJAGetTempFeeSchema.setSerialNo(mSerialNo);
		tLJAGetTempFeeSchema.setTempFeeNo(mSerialNo);
		tLJAGetTempFeeSchema.setTempFeeType("2");
		mMMap.put(tLJAGetTempFeeSchema, "INSERT");
		logger.debug("处理LJAGetTempFee完毕");

		/** @todo ----处理暂打印管理表LOPrtManager----INSERT */
		logger.debug("开始处理打印管理表");
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(mSerialNo);
		tLOPRTManagerSchema.setOtherNo(mLCContSchema.getContNo());
		tLOPRTManagerSchema.setOtherNoType("00");
		tLOPRTManagerSchema.setCode("90");
		tLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
		tLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		tLOPRTManagerSchema.setPrtType("0");
		tLOPRTManagerSchema.setStateFlag("0");
		tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		tLOPRTManagerSchema.setOldPrtSeq(mSerialNo);
		tLOPRTManagerSchema.setStandbyFlag6(mSerialNo);
		tLOPRTManagerSchema.setStandbyFlag7(mSerialNo);
		tLOPRTManagerSchema.setPatchFlag("0");
		mLOPRTManagerSet.add(tLOPRTManagerSchema);
		mMMap.put(mLOPRTManagerSet, "INSERT");
		logger.debug("打印管理表处理完毕");
		return true;
	}

	// private boolean setLOPrtManager(LJTempFeeSet tLJTempFeeSet) {
	// LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
	// tLJTempFeeSchema = tLJTempFeeSet.get(1);
	// if (tLJTempFeeSchema.getTempFeeNo() != null) {
	// /** @todo 准备打印表数据 **/
	// LOPRTManagerSchema tLOPRTManagerSchema = new
	// LOPRTManagerSchema();
	// String prtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
	// try {
	// tLOPRTManagerSchema.setPrtSeq(prtSeqNo);
	// tLOPRTManagerSchema.setOtherNo(tLJTempFeeSchema.
	// getOtherNo());
	// tLOPRTManagerSchema.setOtherNoType("00");
	// tLOPRTManagerSchema.setCode("90");
	// tLOPRTManagerSchema.setManageCom(tLJTempFeeSchema.
	// getPolicyCom());
	// tLOPRTManagerSchema.setAgentCode(tLJTempFeeSchema.
	// getAgentCode());
	// tLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
	// tLOPRTManagerSchema.setReqOperator(mGlobalInput.
	// Operator);
	// tLOPRTManagerSchema.setPrtType("0");
	// tLOPRTManagerSchema.setStateFlag("0");
	// tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
	// tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
	// tLOPRTManagerSchema.setOldPrtSeq(prtSeqNo);
	// tLOPRTManagerSchema.setStandbyFlag6(tLJTempFeeSchema.
	// getTempFeeNo());
	// tLOPRTManagerSchema.setStandbyFlag7(tNo);
	// tLOPRTManagerSchema.setPatchFlag("0");
	// mLOPRTManagerSet.add(tLOPRTManagerSchema);
	// } catch (Exception ex) {
	// return false;
	// }
	// }
	// }

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		logger.debug("preparing OutputData");
		mResult.clear();
		mResult.add(mTransferData);
		mResult.add(mMMap);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
