package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPENoticeDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LMCertifySubDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPENoticeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:打印通知书，并下发单证
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */

public class PrintSendOutSysCertBL {
private static Logger logger = Logger.getLogger(PrintSendOutSysCertBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mPrtSeq;
	private String mCode;
	private boolean mPatchFlag;
	private boolean mAutoSysCertSendOutFlag = true;

	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	/** 单证发放表 */
	private LZSysCertifySchema mLZSysCertifySchema = new LZSysCertifySchema();

	/** 体检主表 */
	private LCPENoticeSchema mLCPENoticeSchema = new LCPENoticeSchema();

	public PrintSendOutSysCertBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

		// 进行业务处理
		if (!dealData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start  Submit...");
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "AskUWConfirmBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// 添加核保通知书打印管理表数据
		map.put(mLOPRTManagerSchema, "UPDATE");

		// 添加续保批单核保主表数据
		if (mPatchFlag == false) {
			map.put(mLCPENoticeSchema, "UPDATE");
		}

		// 添加续保体检通知书自动发放表数据
		if (mAutoSysCertSendOutFlag == true) {
			map.put(mLZSysCertifySchema, "INSERT");
		}

		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 通知书流水号码
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);
		if (!tLOPRTManagerDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PrintSendOutSysCertBL";
			tError.functionName = "checkData";
			tError.errorMessage = "查询打印管理表失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mCode = tLOPRTManagerDB.getCode();
		if (mCode.equals("") || mCode == null) {
			CError tError = new CError();
			tError.moduleName = "PrintSendOutSysCertBL";
			tError.functionName = "checkData";
			tError.errorMessage = "取得通知书类型编码失败 !";
			this.mErrors.addOneError(tError);
			return false;

		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		// 通知书对应的投保单号码
		mContNo = mLOPRTManagerSchema.getOtherNo();
		if (mContNo == null) {
			CError tError = new CError();
			tError.moduleName = "PrintSendOutSysCertBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PrintSendOutSysCertBL";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		if (mLOPRTManagerSchema.getPatchFlag() == null)
			mPatchFlag = false;
		else if (mLOPRTManagerSchema.getPatchFlag().equals("0"))
			mPatchFlag = false;
		else if (mLOPRTManagerSchema.getPatchFlag().equals("1"))
			mPatchFlag = true;

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWPrintAutoHealthAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWPrintAutoHealthAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWPrintAutoHealthAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 核保生调信息
		logger.debug("Code=" + mCode);
		if (!mCode.equals("G04")) {
			if (preparePrintAutoHealth() == false)
				return false;
		}
		// 打印队列
		if (preparePrint() == false)
			return false;

		// 发放系统单证打印队列
		if (prepareAutoSysCertSendOut() == false)
			return false;

		return true;

	}

	/**
	 * 准备核保资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePrintAutoHealth() {

		// //准备核保主表信息

		if (mLOPRTManagerSchema.getPatchFlag() == null)
			mPatchFlag = false;

		else if (mLOPRTManagerSchema.getPatchFlag().equals("0"))
			mPatchFlag = false;
		else if (mLOPRTManagerSchema.getPatchFlag().equals("1"))
			mPatchFlag = true;

		if (mPatchFlag == false) // 不是补打，则更新数据状态
		{
			// 更新体检通知书
			LCPENoticeDB tLCPENoticeDB = new LCPENoticeDB();
			tLCPENoticeDB.setContNo(mContNo);
			tLCPENoticeDB.setPrtSeq(mPrtSeq);
			tLCPENoticeDB.setPrintFlag("0");
			tLCPENoticeDB.setCustomerNo(mLOPRTManagerSchema.getStandbyFlag1()); // 备用字段1存放客户号
			LCPENoticeSet tLCPENoticeSet = tLCPENoticeDB.query();
			if (tLCPENoticeSet == null || tLCPENoticeSet.size() <= 0) {
				mErrors.copyAllErrors(tLCPENoticeDB.mErrors);
				return false;
			}
			mLCPENoticeSchema = tLCPENoticeSet.get(1);
			mLCPENoticeSchema.setPrintFlag("1");
			mLCPENoticeSchema.setModifyDate(PubFun.getCurrentDate());
			mLCPENoticeSchema.setModifyTime(PubFun.getCurrentTime());
		}
		return true;
	}

	/**
	 * 准备打印信息表
	 * 
	 * @return
	 */
	private boolean preparePrint() {
		// 准备打印管理表数据
		mLOPRTManagerSchema.setStateFlag("1");
		mLOPRTManagerSchema.setDoneDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setDoneTime(PubFun.getCurrentTime());
		mLOPRTManagerSchema.setExeCom(mManageCom);
		mLOPRTManagerSchema.setExeOperator(mOperater);
		return true;
	}

	/**
	 * 准备核保资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareAutoSysCertSendOut() {
		// 判断打印过的单据是否需要自动发放
		mCode = mLOPRTManagerSchema.getCode();
		if (mCode == null || mCode.equals("")) {
			CError tError = new CError();
			tError.moduleName = "PrintSendOutSysCertBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输业务数据中mCode失败!";
			this.mErrors.addOneError(tError);
			return false;

		}
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType("syscertifycode");
		tLDCodeDB.setCode(mCode);
		if (!tLDCodeDB.getInfo()) {
			mAutoSysCertSendOutFlag = false;
			return true;
		}

		LMCertifySubDB tLMCertifySubDB = new LMCertifySubDB();
		tLMCertifySubDB.setCertifyCode(tLDCodeDB.getCodeName());
		if (!tLMCertifySubDB.getInfo()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMCertifySubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWPrintAutoHealthAfterInitService";
			tError.functionName = "prepareAutoSysCertSendOut";
			tError.errorMessage = "查询单证回收描述信息表失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String tflag = tLMCertifySubDB.getAutoSend();
		if (StrTool.cTrim(tflag).equals("") || StrTool.cTrim(tflag).equals("N")) {
			mAutoSysCertSendOutFlag = false;
			return true;
		}
		// //准备自动发放表信息
		if (mAutoSysCertSendOutFlag == true) // 需要自动发放，则更新数据状态
		{
			LZSysCertifySchema tLZSysCertifyschema = new LZSysCertifySchema();
			tLZSysCertifyschema.setCertifyCode(tLDCodeDB.getCodeName());
			tLZSysCertifyschema.setCertifyNo(mPrtSeq);
			tLZSysCertifyschema.setSendOutCom("A" + mGlobalInput.ManageCom);
			tLZSysCertifyschema.setReceiveCom("D"
					+ mLOPRTManagerSchema.getAgentCode());
			logger.debug("D" + mLOPRTManagerSchema.getAgentCode());
			tLZSysCertifyschema.setHandler("SYS");
			tLZSysCertifyschema.setStateFlag("0");// 团单体检状态为1，个单为0
			tLZSysCertifyschema.setHandleDate(PubFun.getCurrentDate());
			tLZSysCertifyschema.setTakeBackNo(PubFun1.CreateMaxNo("TAKEBACKNO",
					PubFun.getNoLimit(mManageCom.substring(1))));
			tLZSysCertifyschema.setSendNo(PubFun1.CreateMaxNo("TAKEBACKNO",
					PubFun.getNoLimit(mManageCom.substring(1))));
			tLZSysCertifyschema.setOperator(mOperater);
			tLZSysCertifyschema.setMakeDate(PubFun.getCurrentDate());
			tLZSysCertifyschema.setMakeTime(PubFun.getCurrentTime());
			tLZSysCertifyschema.setModifyDate(PubFun.getCurrentDate());
			tLZSysCertifyschema.setModifyTime(PubFun.getCurrentTime());
			mLZSysCertifySchema = tLZSysCertifyschema;
		}
		return true;
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}
}
