package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LBMissionDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LWFieldMapDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.LWProcessInstanceDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.finfee.TempFeeWithdrawBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LCApplyRecallPolSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LJAGetTempFeeSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LWFieldMapSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LCApplyRecallPolSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LWFieldMapSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.LWProcessInstanceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 张星
 * @version 1.0
 */

public class GrpApplyRecallPolBL {
private static Logger logger = Logger.getLogger(GrpApplyRecallPolBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mReturnFeeData;
	private String mOperate;
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();
	private MMap mmap = new MMap();
	private LCApplyRecallPolSchema mLCApplyRecallPolSchema = new LCApplyRecallPolSchema();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String mPrtNo = "";
	private String mGrpContNo = "";
	private String mMissionID = "";
	private String mGetNoticeNo = ""; // 给付通知书号码
	private boolean PrintFlag = false;

	// 业务处理相关变量
	LCApplyRecallPolSet mLCApplyRecallPolSet = new LCApplyRecallPolSet();
	LCApplyRecallPolSet outDelLCApplyRecallPolSet = new LCApplyRecallPolSet();
	LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
	LBMissionSet mLBMissionSet = new LBMissionSet();
	LWMissionSet mLWMissionSet = new LWMissionSet();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public GrpApplyRecallPolBL() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData(mOperate)) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "ApplyRecallPolBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	private boolean dealData(String Operate) {
		// 查询合同信息
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setPrtNo(mPrtNo);
		tLCGrpContSet.set(tLCGrpContDB.query());
		if (tLCGrpContDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ApplyRecallPolBL";
			tError.functionName = "prepareContUW";
			tError.errorMessage = "合同信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGrpContSchema = tLCGrpContSet.get(1).getSchema();
		if (tLCGrpContSet == null || tLCGrpContSet.size() != 1) {
			CError tError = new CError();
			tError.moduleName = "ApplyRecallPolBL";
			tError.functionName = "prepareContUW";
			tError.errorMessage = "没有找到该印刷号对应的合同信息，请确认!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCGrpContSchema = tLCGrpContSet.get(1);
		tLCGrpContSchema.setUWFlag("a");

		// 查询该保单下所有险种保单
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setPrtNo(mPrtNo);
		tLCGrpPolSet.set(tLCGrpPolDB.query());
		if (tLCGrpPolSet == null || tLCGrpPolSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "ApplyRecallPolBL";
			tError.functionName = "dealData";
			tError.errorMessage = "没有找到该印刷号对应的险种保单，请确认!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 生成保单撤单申请记录
		LCGrpPolSchema aLCGrpPolSchema = new LCGrpPolSchema();
		LCApplyRecallPolSchema tLCApplyRecallPolSchema;
		for (int j = 1; j <= tLCGrpPolSet.size(); j++) {
			aLCGrpPolSchema.setSchema(tLCGrpPolSet.get(j));

			tLCApplyRecallPolSchema = new LCApplyRecallPolSchema();

			tLCApplyRecallPolSchema.setPrtNo(aLCGrpPolSchema.getPrtNo());
			tLCApplyRecallPolSchema.setProposalNo(aLCGrpPolSchema
					.getGrpProposalNo());
			tLCApplyRecallPolSchema.setMainPolNo(aLCGrpPolSchema.getGrpPolNo());
			tLCApplyRecallPolSchema.setRemark(mLCApplyRecallPolSchema
					.getRemark());
			tLCApplyRecallPolSchema.setApplyType(mLCApplyRecallPolSchema
					.getApplyType()); // Add by
			// Minim
			tLCApplyRecallPolSchema.setInputState("0");
			tLCApplyRecallPolSchema.setOperator(mGlobalInput.Operator);
			tLCApplyRecallPolSchema.setManageCom(mGlobalInput.ManageCom);
			tLCApplyRecallPolSchema.setMakeDate(CurrentDate);
			tLCApplyRecallPolSchema.setMakeTime(CurrentTime);
			tLCApplyRecallPolSchema.setModifyDate(CurrentDate);
			tLCApplyRecallPolSchema.setModifyTime(CurrentTime);
			mLCApplyRecallPolSet.add(tLCApplyRecallPolSchema);

			LCApplyRecallPolSchema outDelLCApplyRecallPolSchema = new LCApplyRecallPolSchema();
			outDelLCApplyRecallPolSchema.setProposalNo(aLCGrpPolSchema
					.getGrpProposalNo());
			outDelLCApplyRecallPolSet.add(outDelLCApplyRecallPolSchema.getDB()
					.query());

		}
		// 处理暂缴费退费
		if (!returnFee()) {
			return false;
		}

		// ====ADD====zhangtao======2005-04-13=================BGN======================
		// 获取工作流任务ID
		if (!getMissionID()) {
			return false;
		}

		// 删除保单工作流节点 [备份]
		LWMissionSet tLWMissionSet = new LWMissionSet();
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionSet.set(tLWMissionDB.query());
		mLWMissionSet.add(tLWMissionSet);
		if (tLWMissionDB.mErrors.needDealError() == true) {
			// @@错误处理
			mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpGiveEnsureBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "保单工作流任务查询失败!" + "印刷号：" + mPrtNo + "任务号："
					+ mMissionID;
			mErrors.addOneError(tError);
			return false;
		}
		Reflections tReflections = new Reflections();

		LBMissionSchema tLBMissionSchema;
		String tSerielNo = "";
		for (int i = 1; i <= tLWMissionSet.size(); i++) {
			tSerielNo = PubFun1.CreateMaxNo("MissionSerielNo", 10);

			tLBMissionSchema = new LBMissionSchema();
			tReflections.transFields(tLBMissionSchema, tLWMissionSet.get(i));

			tLBMissionSchema.setSerialNo(tSerielNo);
			tLBMissionSchema.setActivityStatus("3"); // 节点任务执行完毕
			tLBMissionSchema.setLastOperator(mGlobalInput.Operator);
			tLBMissionSchema.setMakeDate(CurrentDate);
			tLBMissionSchema.setMakeTime(CurrentTime);
			tLBMissionSchema.setModifyDate(CurrentDate);
			tLBMissionSchema.setModifyTime(CurrentTime);
			mLBMissionSet.add(tLBMissionSchema);
		}

		// String delSQL = "DELETE FROM LWMISSION WHERE MISSIONID = '" + mMissionID +
		// "'";
		// map.put(delSQL, "DELETE");

		// ====ADD====zhangtao======2005-04-13=================END======================
		return true;
	}

	/**
	 * 如果该客户已经加费，进行暂缴费退费操作
	 */
	private boolean returnFee() {
		String payMode = ""; // 交费方式
		String BankCode = ""; // 银行编码
		String BankAccNo = ""; // 银行账号
		String AccName = ""; // 户名

		String strSql = "";

		// 测试该投保单是否有暂交费待退
		strSql = "select distinct tempfeeno from ljtempfee where otherno = '"
				+ mPrtNo
				+ "'"
				+ " and (enteraccdate is not null  and enteraccdate<>'3000-01-01')"
				+ " and confdate is null";

		LJTempFeeDB sLJTempFeeDB = new LJTempFeeDB();
		LJTempFeeSet sLJTempFeeSet = new LJTempFeeSet();
		sLJTempFeeSet = sLJTempFeeDB.executeQuery(strSql);
		logger.debug("暂交费数量:  " + sLJTempFeeSet.size());
		if (sLJTempFeeSet.size() == 0) {
			logger.debug("Out ReturnFee");
			return true;
		}

		// 如果通知书号不为空，找出退费方式（优先级依次为支票，银行，现金）

		String sql = "select * from ljtempfeeclass where tempfeeno in ("
				+ strSql + ")";

		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB
				.executeQuery(sql);
		if (tLJTempFeeClassSet == null || tLJTempFeeClassSet.size() == 0) {
			mErrors.addOneError("没有找到对应的暂交费分类纪录");
			return false;
		}
		PrintFlag = true;
		if (!preparePrint()) {
			return false;
		}

		for (int i = 1; i <= tLJTempFeeClassSet.size(); i++) {
			if (tLJTempFeeClassSet.get(i).getPayMode().equals("2")
					|| tLJTempFeeClassSet.get(i).getPayMode().equals("3")) {
				payMode = tLJTempFeeClassSet.get(i).getPayMode();
				BankCode = tLJTempFeeClassSet.get(i).getBankCode();
				BankAccNo = tLJTempFeeClassSet.get(i).getChequeNo();
				break;
			}
			if (tLJTempFeeClassSet.get(i).getPayMode().equals("4")) {
				payMode = tLJTempFeeClassSet.get(i).getPayMode();
				BankCode = tLJTempFeeClassSet.get(i).getBankCode();
				BankAccNo = tLJTempFeeClassSet.get(i).getBankAccNo();
				AccName = tLJTempFeeClassSet.get(i).getAccName();
			} else {
				payMode = "1";
			}
		}
		// GetPayType tGetPayType = new GetPayType();
		// if (!tGetPayType.getPayTypeForLCPol(mPrtNo))
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tGetPayType.mErrors);
		// logger.debug("查询出错信息 :" + tGetPayType.mErrors);
		// return false;
		// }
		// else
		// {
		// PrintFlag = true;
		// if (!preparePrint())
		// {
		// return false;
		// }
		//
		// payMode = tGetPayType.getPayMode(); //交费方式
		// BankCode = tGetPayType.getBankCode(); //银行编码
		// BankAccNo = tGetPayType.getBankAccNo(); //银行账号
		// AccName = tGetPayType.getAccName(); //户名
		//
		// logger.debug("===payMode===" + payMode);
		// logger.debug("===BankCode==" + BankCode);
		// logger.debug("===AccNo=====" + BankAccNo);
		// logger.debug("===AccName===" + AccName);
		//
		// }

		TransferData sTansferData = new TransferData();
		sTansferData.setNameAndValue("PayMode", payMode);
		sTansferData.setNameAndValue("NotBLS", "1");
		sTansferData.setNameAndValue("SubmitFlag", "NoSubmit");
		if (payMode.equals("1")) {
			sTansferData.setNameAndValue("BankFlag", "0");
		} else {
			sTansferData.setNameAndValue("BankCode", BankCode);
			sTansferData.setNameAndValue("AccNo", BankAccNo);
			sTansferData.setNameAndValue("AccName", AccName);
			sTansferData.setNameAndValue("BankFlag", "1");
		}
		sTansferData.setNameAndValue("OtherNoFlag", "1");
		// 生成给付通知书号
		String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		mGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit); // 产生给付通知书号

		sTansferData.setNameAndValue("GetNoticeNo", mGetNoticeNo);

		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		LJAGetTempFeeSet tLJAGetTempFeeSet = new LJAGetTempFeeSet();

		for (int index = 1; index <= sLJTempFeeSet.size(); index++) {
			logger.debug("----------处理第  " + index + " 笔暂收费开始----- ");

			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			tLJTempFeeSchema.setTempFeeNo(sLJTempFeeSet.get(index)
					.getTempFeeNo());
			tLJTempFeeSet.add(tLJTempFeeSchema);

			LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
			tLJAGetTempFeeSchema.setGetReasonCode("99");
			tLJAGetTempFeeSet.add(tLJAGetTempFeeSchema);

			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.add(tLJTempFeeSet);
			tVData.add(tLJAGetTempFeeSet);
			tVData.add(sTansferData);
			tVData.add(mGlobalInput);
			// 数据传输-----准备调用退费程序
			logger.debug("--------开始传输数据---------");
			TempFeeWithdrawBL tTempFeeWithdrawBL = new TempFeeWithdrawBL();
			if (tTempFeeWithdrawBL.submitData(tVData, "INSERT")) {
				logger.debug("---ok---");
			} else {
				logger.debug("---NO---");
			}
			if (tTempFeeWithdrawBL.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tTempFeeWithdrawBL.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWManuNormChkBL";
				tError.functionName = "submitData";
				tError.errorMessage = "核保成功,但退费失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			VData ReturnFeeData = new VData();
			ReturnFeeData = tTempFeeWithdrawBL.getResult();
			MMap ReturnFeeMap = new MMap();
			ReturnFeeMap = (MMap) ReturnFeeData
					.getObjectByObjectName("MMap", 0);
			mmap.add(ReturnFeeMap);
			logger.debug("-----------处理第  " + index + " 笔暂收费 成功------");
		}

		logger.debug("Out ReturnFee");
		return true;
	}

	/**
	 * 打印信息表
	 * 
	 * @return
	 */
	private boolean preparePrint() {
		// 处于未打印状态的通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_PE); // 体检
		tLOPRTManagerDB.setOtherNo(mLCGrpContSchema.getGrpContNo());
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_GRPPOL); // 保单号
		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("URGEInterval");

		if (tLDSysVarDB.getInfo() == false) {
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "prepareURGE";
			tError.errorMessage = "没有描述催发间隔!";
			this.mErrors.addOneError(tError);
			return false;
		}
		FDate tFDate = new FDate();
		int tInterval = Integer.parseInt(tLDSysVarDB.getSysVarValue());
		logger.debug(tInterval);

		Date tDate = PubFun.calDate(tFDate.getDate(PubFun.getCurrentDate()),
				tInterval, "D", null);
		logger.debug(tDate); // 取预计催办日期
		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);

		String mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
		// 准备打印管理表数据
		mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setOtherNo(mLCGrpContSchema.getGrpContNo());
		mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GRPPOL); // 保单号
		mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GRP_WITHDRAW); // 团单撤单通知书
		mLOPRTManagerSchema.setManageCom(mLCGrpContSchema.getManageCom());
		mLOPRTManagerSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
		mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

		mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		mLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSchema.setPatchFlag("0");
		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

		mLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setForMakeDate(tDate);

		return true;
	}

	// 从输入数据中得到所有对象
	// 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	private boolean getInputData() {
		mLCApplyRecallPolSchema = (LCApplyRecallPolSchema) mInputData
				.getObjectByObjectName("LCApplyRecallPolSchema", 0);

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		if (mLCApplyRecallPolSchema == null
				|| mLCApplyRecallPolSchema.getPrtNo() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ApplyRecallPolBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		mPrtNo = mLCApplyRecallPolSchema.getPrtNo();

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	// 准备往后层输出所需要的数据
	private boolean prepareOutputData() {
		try {
			map.put(mLBMissionSet, "INSERT");
			map.put(tLCGrpContSchema, "UPDATE");
			map.put(mLWMissionSet, "DELETE");
			map.put(outDelLCApplyRecallPolSet, "DELETE");
			map.put(mLCApplyRecallPolSet, "INSERT");
			map.add(mmap);
			if (PrintFlag == true) {
				map.put(mLOPRTManagerSchema, "INSERT");
			}

			map.put("update lcgrppol set uwflag='a' where grpcontno='" + mPrtNo
					+ "'", "UPDATE");
			map.put("update lcpol set uwflag='a' where grpcontno='" + mPrtNo
					+ "'", "UPDATE");
			map.put("update lccont set uwflag='a' where grpcontno='" + mPrtNo
					+ "'", "UPDATE");

			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ApplyRecallPolBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	private boolean getMissionID() {

		LBMissionSet tLBMissionSet = new LBMissionSet();
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LBMissionDB tLBMissionDB = new LBMissionDB();

		LWProcessInstanceSet tLWProcessInstanceSet = new LWProcessInstanceSet();
		LWProcessInstanceDB tLWProcessInstanceDB = new LWProcessInstanceDB();
		tLWProcessInstanceDB.setProcessID("0000000004");
		tLWProcessInstanceDB.setStartType("0"); // 根节点
		tLWProcessInstanceSet.set(tLWProcessInstanceDB.query());
		if (tLWProcessInstanceDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLWProcessInstanceDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getContNo";
			tError.errorMessage = "工作流过程实例查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLWProcessInstanceSet == null || tLWProcessInstanceSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getContNo";
			tError.errorMessage = "工作流过程实例查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 循环查找该过程所有根节点寻找MissionID
		String tActivityID;
		for (int i = 1; i <= tLWProcessInstanceSet.size(); i++) {
			tActivityID = tLWProcessInstanceSet.get(i).getTransitionStart();

			LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
			LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
			tLWFieldMapDB.setActivityID(tActivityID);
			tLWFieldMapSet.set(tLWFieldMapDB.query());
			if (tLWFieldMapDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLWFieldMapDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LDContTimeBL";
				tError.functionName = "getContNo";
				tError.errorMessage = "工作流任务字段映射查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			LWFieldMapSchema tLWFieldMapSchema = new LWFieldMapSchema();
			for (int j = 1; j <= tLWFieldMapSet.size(); j++) {
				tLWFieldMapSchema.setSchema(tLWFieldMapSet.get(j));
				// 找到印刷号影射字段
				if (tLWFieldMapSchema.getSourFieldName().equals("PrtNo")) {
					// 匹配该印刷号在此节点是否有任务
					mLWMissionSet.clear();
					tLWMissionDB = new LWMissionDB();
					tLWMissionDB.setActivityID(tActivityID);
					tLWMissionDB.setV(tLWFieldMapSchema.getDestFieldName(),
							mPrtNo);
					mLWMissionSet.set(tLWMissionDB.query());
					if (tLWMissionDB.mErrors.needDealError() == true) {
						// @@错误处理
						mErrors.copyAllErrors(tLWMissionDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "ApplyRecallPolBL";
						tError.functionName = "getBaseData";
						tError.errorMessage = "保单工作流任务ID查询失败!" + "印刷号："
								+ mPrtNo;
						mErrors.addOneError(tError);
						return false;
					}
					if (mLWMissionSet != null && mLWMissionSet.size() == 1) {
						mMissionID = mLWMissionSet.get(1).getMissionID();
						logger.debug("==MissionID==" + mMissionID);
						return true;
					}
					// 查找备份表
					if (mLWMissionSet == null || mLWMissionSet.size() == 0) {
						tLBMissionSet.clear();
						tLBMissionDB = new LBMissionDB();
						tLBMissionDB.setActivityID(tActivityID);
						tLBMissionDB.setV(tLWFieldMapSchema.getDestFieldName(),
								mPrtNo);
						tLBMissionSet.set(tLBMissionDB.query());
						if (tLBMissionDB.mErrors.needDealError() == true) {
							// @@错误处理
							mErrors.copyAllErrors(tLBMissionDB.mErrors);
							CError tError = new CError();
							tError.moduleName = "ApplyRecallPolBL";
							tError.functionName = "getBaseData";
							tError.errorMessage = "保单工作流任务ID查询失败!" + "印刷号："
									+ mPrtNo;
							mErrors.addOneError(tError);
							return false;
						}
						if (tLBMissionSet != null && tLBMissionSet.size() == 1) {
							mMissionID = tLBMissionSet.get(1).getMissionID();
							logger.debug("==MissionID==" + mMissionID);
							return true;
						}
					}
				}
			}
		}
		if (mMissionID == null || "".equals(mMissionID)) {
			CError tError = new CError();
			tError.moduleName = "ApplyRecallPolBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "保单工作流任务ID查询失败!" + "印刷号：" + mPrtNo;
			mErrors.addOneError(tError);
			return false;
		}
		// 如果有任务正处于申请处理中，不能撤单
		mLWMissionSet.clear();
		tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setMissionID(mMissionID);
		mLWMissionSet.set(tLWMissionDB.query());
		if (tLWMissionDB.mErrors.needDealError() == true) {
			// @@错误处理
			mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ApplyRecallPolBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "保单工作流任务查询失败!" + "印刷号：" + mPrtNo;
			mErrors.addOneError(tError);
			return false;
		}
		// 当前处理申请人
		String tDefaultOperator;
		for (int i = 1; i <= mLWMissionSet.size(); i++) {
			tDefaultOperator = mLWMissionSet.get(i).getDefaultOperator();
			if (tDefaultOperator != null && !"".equals(tDefaultOperator)) {
				CError tError = new CError();
				tError.moduleName = "GrpApplyRecallPolBL";
				tError.functionName = "getBaseData";
				tError.errorMessage = "保单工作流任务正处于申请处理中，不能撤单!" + "印刷号：" + mPrtNo
						+ "申请人：" + tDefaultOperator;
				mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}
}
