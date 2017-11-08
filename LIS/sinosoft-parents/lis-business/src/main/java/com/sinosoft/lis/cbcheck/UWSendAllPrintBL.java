package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCSpecDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCRReportPrtSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCRReportPrtSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: 发通知书
 * </p>
 * <p>
 * Description: 发通知书的通用类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */

public class UWSendAllPrintBL implements BusinessService{
private static Logger logger = Logger.getLogger(UWSendAllPrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private LOPRTManagerSchema mmLOPRTManagerSchema = new LOPRTManagerSchema();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	private LCContSchema mLCContSchema = new LCContSchema();

	private LCRReportPrtSet mLCRReportPrtSet = new LCRReportPrtSet();

	private LCSpecSet mLCSpecSet = new LCSpecSet();

	private LCSpecSet mmLCSpecSet = new LCSpecSet();

	private LCRReportPrtSet mmLCRReportPrtSet = new LCRReportPrtSet();

	/** 核保操作轨迹表 */
	private LCCUWSubSchema mLCCUWSubSchema = new LCCUWSubSchema();

	/** 数据操作字符串 */
	private String mOperator;

	private String mManageCom;

	private String mOperate;

	private String mPrtSeq;

	private String ttSql;

	private String mContNo;

	public UWSendAllPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");

		// 提交数据
		if (cOperate.equals("submit")) {
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCInsuredUWBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} else {
			// 准备工作流map表数据
			if (!prepareTransferData()) {
				return false;
			}

		}
		return true;
	}

	/**
	 * 准备工作流map表数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("ContNo", mLCContSchema.getContNo());
		mTransferData.setNameAndValue("PrtNo", mLCContSchema.getPrtNo());
		mTransferData
				.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
		mTransferData.setNameAndValue("PrtSeq", mPrtSeq);
		mTransferData
				.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
		mTransferData.setNameAndValue("Code", mLOPRTManagerSchema.getCode());
		mTransferData.setNameAndValue("OldPrtSeq", mPrtSeq);
		mTransferData.setNameAndValue("SaleChnl", mLCContSchema.getSaleChnl());
		mResult.add(mTransferData);

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		LOPRTManagerDB mLOPRTManagerDB = new LOPRTManagerDB();

		mLOPRTManagerDB.setCode(mLOPRTManagerSchema.getCode()); // 打印类型
		mLOPRTManagerDB.setOtherNo(mLOPRTManagerSchema.getOtherNo());
		mLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		mLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet ttLOPRTManagerSet = mLOPRTManagerDB.query();
		if (ttLOPRTManagerSet.size() != 0
				&& mLOPRTManagerSchema.getCode().equals("82")) {
			map.put(ttLOPRTManagerSet, "DELETE");
		}
		if (mLOPRTManagerSchema.getCode().equals("82")) {
			logger.debug("共有" + mLCSpecSet.size() + "条特约");
			for (int k = 1; k <= mLCSpecSet.size(); k++) {
				LCSpecSchema mmLCSpecSchema = new LCSpecSchema();
				LCSpecDB mLCSpecDB = new LCSpecDB();
				logger.debug("^^^^^^^^^^^^^^^^^^^^"
						+ mLCSpecSet.get(k).getProposalNo());
				logger.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				logger.debug("^^^^^^^^^^^^^^^^^^^^"
						+ mLCSpecSet.get(k).getSerialNo());
				if (mLCSpecSet.get(k).getProposalNo() == null
						|| mLCSpecSet.get(k).getProposalNo().equals("")) {
					CError tError = new CError();
					tError.moduleName = "UWSendAllPrintBL";
					tError.functionName = "prepareOutputData";
					tError.errorMessage = "获取第" + k + "条特约信息的PropossalNo值失败!";
					this.mErrors.addOneError(tError);
					return false;

				}
				if (mLCSpecSet.get(k).getSerialNo() == null
						|| mLCSpecSet.get(k).getSerialNo().equals("")) {
					CError tError = new CError();
					tError.moduleName = "UWSendAllPrintBL";
					tError.functionName = "prepareOutputData";
					tError.errorMessage = "获取第" + k + "条特约信息的SerialNo值失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				mLCSpecDB.setProposalNo(mLCSpecSet.get(k).getProposalNo());
				mLCSpecDB.setSerialNo(mLCSpecSet.get(k).getSerialNo());
				LCSpecSet tempLCSpecSet = new LCSpecSet();
				tempLCSpecSet = mLCSpecDB.query();
				if (tempLCSpecSet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "UWSendALLPrintBL";
					tError.functionName = "prepareOutputData";
					tError.errorMessage = "查询相关特约信息失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				mmLCSpecSchema = tempLCSpecSet.get(1);
				mmLCSpecSchema.setPrtSeq(mPrtSeq);
				mmLCSpecSet.add(mmLCSpecSchema);
			}
			// String sql = "update lcspec set prtseq='" + mPrtSeq
			// + "' where contno = '" +
			// mLOPRTManagerSchema.getOtherNo()
			// + "' and ";
			// //+ "' and prtseq is null ";
			map.put(mmLCSpecSet, "UPDATE");
		}
		map.put(mmLOPRTManagerSchema, "INSERT");

		if (mLOPRTManagerSchema.getCode().equals("89")
				|| mLOPRTManagerSchema.getCode().equals("BQ88")
				|| mLOPRTManagerSchema.getCode().equals("LP89")) {
			map.put(mmLCRReportPrtSet, "INSERT");
		}
		if (mLOPRTManagerSchema.getCode().length() > 2
				&& mLOPRTManagerSchema.getCode().substring(0, 2).equals("BQ")) {
			// 保全的核保通知书暂不添加核保轨迹记录
		} else if (mLOPRTManagerSchema.getCode().length() > 2
				&& mLOPRTManagerSchema.getCode().substring(0, 2).equals("LP")) {
			// 保全的核保通知书暂不添加核保轨迹记录
		}

		else {
			// 添加核保操作轨迹的记录
			// tongmeng 2007-10-30 modify
			// 如果人工核保直接下核保结论。不处理核保操作轨迹表
			logger.debug("!!!:"
							+ this.mLOPRTManagerSchema.getStandbyFlag7()
									.equals("TBJB"));
			if (this.mLOPRTManagerSchema.getStandbyFlag7() != null
					&& !this.mLOPRTManagerSchema.getStandbyFlag7().equals(
							"TBJB")) {
				map.put(mLCCUWSubSchema, "INSERT");
			}
		}

		mResult.add(map);
		return true;

	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "UWSendALLPrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验是否有保单数据
		if (mLOPRTManagerSchema.getOtherNoType().equals("02")) {
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
			if (!tLCContDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "UWSendPrintBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "查询合同信息失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLCContSchema = tLCContDB.getSchema();
		}
		if (mLOPRTManagerSchema.getCode().equals("82")) {
			ExeSQL tempExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();

			ttSql = "select contno from LCSpec where ContNo='"
					+ "?ContNo?" + "'and prtseq is null";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(ttSql);
			sqlbv.put("ContNo", mLOPRTManagerSchema.getOtherNo());
			tSSRS = tempExeSQL.execSQL(sqlbv);
			logger.debug("##############################");
			logger.debug("##############################");
			logger.debug("##############################"
					+ tSSRS.getMaxRow());
			logger.debug("##############################");
			logger.debug("##############################");
			// if (tSSRS.getMaxRow() == 0) {
			// CError tError = new CError();
			// tError.moduleName = "UWSendALLPrintBL";
			// tError.functionName = "getInputData";
			// tError.errorMessage = "无未发送的特别约定通知书!";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
		}
		// 特约通知书未回收 也能再次发送。
		if (!mLOPRTManagerSchema.getCode().equals("00")
				&& !mLOPRTManagerSchema.getCode().equals("06")
				&& !mLOPRTManagerSchema.getCode().equals("09")) {
			if (CheckPrint() == false) {
				return false;
			}
		} else {
			// 需要把原来的打印完.

		}

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

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mInputData = cInputData;

		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		if (mLOPRTManagerSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有传入数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLOPRTManagerSchema.getCode().equals("89")
				|| mLOPRTManagerSchema.getCode().equals("BQ88")
				|| mLOPRTManagerSchema.getCode().equals("LP89")) {
			mLCRReportPrtSet = (LCRReportPrtSet) mTransferData
					.getValueByName("LCRReportPrtSet");
		}
		if (mLOPRTManagerSchema.getCode().equals("82")) {
			mLCSpecSet = (LCSpecSet) mTransferData.getValueByName("LCSpecSet");
		}
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得管理机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = mLOPRTManagerSchema.getOtherNo();

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (PreparePrint() == false) {
			return false;
		}
		if (mLOPRTManagerSchema.getCode().length() > 2
				&& mLOPRTManagerSchema.getCode().substring(0, 2).equals("BQ")) {
			// 保全的核保通知书暂不添加核保轨迹记录
		} else if (mLOPRTManagerSchema.getCode().substring(0, 2).equals("LP")) {
			// 理赔不用保存核保核保记录。
		} else {
			// 准备操作轨迹
			if (saveUWOperateTrack() == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	private boolean CheckPrint() {
		String tsql = "select distinct 1 from LOPRTManager where otherno = '"
				+ "?otherno?" + "' and othernotype ='"
				+ "?othernotype?" + "' and code = '"
				+ "?code?"
				+ "' and stateflag in ('0','1')";
		// logger.debug(tsql);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		if (mLOPRTManagerSchema.getCode().trim().substring(0, 2).equals("BQ")) {
			tsql += " and StandbyFlag1 ='"
					+ "?StandbyFlag1?" + "'";
			sqlbv1.put("StandbyFlag1", mLOPRTManagerSchema.getStandbyFlag1());
		}
		sqlbv1.sql(tsql);
		sqlbv1.put("otherno", mLOPRTManagerSchema.getOtherNo());
		sqlbv1.put("othernotype", mLOPRTManagerSchema.getOtherNoType());
		sqlbv1.put("code", mLOPRTManagerSchema.getCode().trim());
		ExeSQL tExeSQL = new ExeSQL();
		String tflag = tExeSQL.getOneValue(sqlbv1);

		if (tflag.trim().equals("1")) {
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "checkPrint";
			tError.errorMessage = "通知书已发放，且未回收，不允许发放新的通知书!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	private boolean PreparePrint() {
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode(mLOPRTManagerSchema.getCode()); // 打印类型
		tLOPRTManagerDB.setOtherNo(mLOPRTManagerSchema.getOtherNo());
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号

		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();

		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);

		}

		if (!mLOPRTManagerSchema.getCode().equals("82")) {
			if (tLOPRTManagerSet.size() != 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorUWAutoHealthAfterInitService";
				tError.functionName = "preparePrint";
				tError.errorMessage = "处于未打印状态的通知书在打印队列中只能有一个!";
				this.mErrors.addOneError(tError);
			}
		}
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("URGEInterval");

		if (tLDSysVarDB.getInfo() == false) {
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "prepareURGE";
			tError.errorMessage = "没有描述催发间隔!";
			this.mErrors.addOneError(tError);

		}
		FDate tFDate = new FDate();
		int tInterval = Integer.parseInt(tLDSysVarDB.getSysVarValue());
		logger.debug(tInterval);

		Date tDate = PubFun.calDate(tFDate.getDate(PubFun.getCurrentDate()),
				tInterval, "D", null);
		logger.debug(tDate); // 取预计催办日期

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "LCContDB";
			tError.functionName = "LCContDB";
			tError.errorMessage = "查询合同信息错误!";
			this.mErrors.addOneError(tError);

		}

		// 准备打印管理表数据

		logger.debug(mGlobalInput.ComCode);
		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		String cLimit = mLOPRTManagerSchema.getCode().replaceAll("[^0-9]", "");
		//mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
		mPrtSeq = PubFun1.CreateMaxNo("UWPRTSEQ", cLimit);
		
		mmLOPRTManagerSchema.setPrtSeq(mPrtSeq);
		mmLOPRTManagerSchema.setOtherNo(mLOPRTManagerSchema.getOtherNo());

		mmLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		mmLOPRTManagerSchema.setCode(mLOPRTManagerSchema.getCode()); // 打印类型
		mmLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom()); // 管理机构
		mmLOPRTManagerSchema.setAgentCode(tLCContDB.getAgentCode()); // 代理人编码
		mmLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		mmLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

		mmLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		mmLOPRTManagerSchema.setStateFlag("0");
		mmLOPRTManagerSchema.setPatchFlag("0");
		mmLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mmLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

		mmLOPRTManagerSchema.setForMakeDate(tDate);
		mmLOPRTManagerSchema.setRemark(mLOPRTManagerSchema.getRemark());
		mmLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);
		// tongmeng 2007-10-30 add
		// 人工核保直接下结论使用StandbyFlag7
		mmLOPRTManagerSchema.setStandbyFlag5(mLOPRTManagerSchema
				.getStandbyFlag5());
		mmLOPRTManagerSchema.setStandbyFlag6(mLOPRTManagerSchema
				.getStandbyFlag6());
		mmLOPRTManagerSchema.setStandbyFlag7(mLOPRTManagerSchema
				.getStandbyFlag7());
		if (mLOPRTManagerSchema.getCode().equals("89")
				|| mLOPRTManagerSchema.getCode().equals("BQ88")
				|| mLOPRTManagerSchema.getCode().equals("LP89")) {
			for (int k = 1; k <= mLCRReportPrtSet.size(); k++) {
				LCRReportPrtSchema tLCRReportPrtSchema = new LCRReportPrtSchema();
				tLCRReportPrtSchema.setGrpContNo("00000000000000000000");
				tLCRReportPrtSchema.setContNo(mLOPRTManagerSchema.getOtherNo());
				tLCRReportPrtSchema.setPrtSeq(mPrtSeq);
				tLCRReportPrtSchema.setAskCode(mLCRReportPrtSet.get(k)
						.getAskCode());
				tLCRReportPrtSchema.setOperator(mGlobalInput.Operator);
				tLCRReportPrtSchema.setMakeDate(PubFun.getCurrentDate());
				tLCRReportPrtSchema.setMakeTime(PubFun.getCurrentTime());
				tLCRReportPrtSchema.setModifyDate(PubFun.getCurrentDate());
				tLCRReportPrtSchema.setModifyTime(PubFun.getCurrentTime());
				mmLCRReportPrtSet.add(tLCRReportPrtSchema);
			}
		}
		if (mLOPRTManagerSchema.getCode().trim().substring(0, 2).equals("BQ")) {
			mmLOPRTManagerSchema.setStandbyFlag1(mLOPRTManagerSchema
					.getStandbyFlag1());
			mmLOPRTManagerSchema.setStandbyFlag2(mLOPRTManagerSchema
					.getStandbyFlag2());
		}
		return true;

	}

	/**
	 * 保存核保操作轨迹 @
	 */
	private boolean saveUWOperateTrack() {
		// 判断发送的核保通知书的类型
		String tSQL = "select codename from ldcode where codetype='uwnoticetype' and code='"
				+ "?code?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSQL);
		sqlbv2.put("code", mLOPRTManagerSchema.getCode());
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv2);

		// 核保轨迹表
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(mLCContSchema.getContNo());
		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();
		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWManuNormChkBL";
			tError.functionName = "prepareAllUW";
			tError.errorMessage = "LCCUWSub表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int m = tLCCUWSubSet.size();
		logger.debug("subcount=" + m);
		if (m > 0) {
			m++; // 核保次数
			tLCCUWSubSchema = new LCCUWSubSchema();
			tLCCUWSubSchema.setUWNo(m); // 第几次核保
			tLCCUWSubSchema.setContNo(mContNo);
			tLCCUWSubSchema.setGrpContNo("00000000000000000000");
			tLCCUWSubSchema.setProposalContNo(mContNo);
			tLCCUWSubSchema.setOperator(mGlobalInput.Operator);
			tLCCUWSubSchema.setUWIdea("核保员发送核保通知书。（通知书类型：\""
					+ tSSRS.GetText(1, 1) + "\";通知书号：" + mPrtSeq + "）");
			tLCCUWSubSchema.setAutoUWFlag("2");
			tLCCUWSubSchema.setOperator(mGlobalInput.Operator); // 操作员
			tLCCUWSubSchema.setManageCom(mLCContSchema.getManageCom());
			tLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());

		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWManuNormChkBL";
			tError.functionName = "prepareAllUW";
			tError.errorMessage = "LCCUWSub表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLCCUWSubSchema = tLCCUWSubSchema;

		return true;
	}

}
