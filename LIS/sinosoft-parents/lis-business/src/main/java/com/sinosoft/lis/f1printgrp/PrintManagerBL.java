package com.sinosoft.lis.f1printgrp;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Kevin
 * @version 1.0
 */

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class PrintManagerBL {
private static Logger logger = Logger.getLogger(PrintManagerBL.class);
	// 常量定义
	// OtherNoType 其它号码类型
	public static final String ONT_INDPOL = "00"; // 个人保单号
	public static final String ONT_GRPPOL = "01"; // 集体保单号
	public static final String ONT_CONT = "02"; // 合同号
	public static final String ONT_EDOR = "03"; // 批单号
	public static final String ONT_GET = "04"; // 实付收据号
	public static final String ONT_PRT = "05"; // 保单印刷号
	public static final String ONT_EDORACCEPT = "06"; // 保全接受号

	// Code 单据类型
	public static final String CODE_DECLINE = "00"; // 拒保通知书
	public static final String CODE_OUTPAY = "01"; // 首期溢交保费通知书
	public static final String CODE_BALANCE = "02"; // 退余额的打印格式
	public static final String CODE_PE = "03"; // 体检通知书
	public static final String CODE_MEET = "04"; // 面见通知书
	public static final String CODE_UW = "0fffff5"; // 核保通知书
	public static final String CODE_ISSUE = "85";// 个单问题件通知书
	public static final String CODE_DEFER = "06"; // 延期承保通知书
	public static final String CODE_FIRSTPAY = "07"; // 首期交费通知书
	public static final String CODE_REFUND = "08"; // 新契约退费通知书
	public static final String CODE_WITHDRAW = "09"; // 撤单通知书

	public static final String CODE_URGE_FP = "10"; // 首期交费通知书催办通知书
	public static final String CODE_URGE_PE = "11"; // 体检通知书催办通知书
	public static final String CODE_URGE_UW = "12"; // 核保通知书催办通知书
	public static final String CODE_URGE_MEET = "14"; // 面见通知书催办通知书
	public static final String CODE_URGE_GC = "13"; // 收款收据

	public static final String CODE_AGEN_QUEST = "14"; // 业务员问题件通知书
	public static final String CODE_URGE_PB = "15"; // 缴费催办通知书
	public static final String CODE_TOVERDUE = "16";// 逾期划款通知书
	public static final String CODE_UINT = "17";// 客户合并通知书
	public static final String CODE_OVERDUE = "18"; // 逾期通知书
	public static final String CODE_OVERDUE_DEPOSE = "19"; // 逾期作废通知书

	public static final String CODE_UWCHANG = "81";// 个单承保计划变更通知书
	public static final String CODE_UWSPEC = "82";// 个单特约通知书
	public static final String CODE_UWADD = "83";// 个单加费通知书
	public static final String CODE_UWPARTCANCEL = "84";// 退保附加险或主险部分退保
	public static final String CODE_UWDEEFEODD = "86";// 拒保延期附加险

	public static final String CODE_PEdorLoanPay = "BQ10"; // 保单质押贷款还款通知书
	public static final String CODE_PEdorDECLINE = "20"; // 保全拒保通知书
	public static final String CODE_PEdorAPPREEND = "BQ21"; // 保全自垫预终止通知书
	public static final String CODE_PEdorAPEND = "BQ22"; // 自垫终止通知书
	public static final String CODE_PEdorPE = "23"; // 保全体检通知书
	public static final String CODE_PEdorMEET = "24"; // 保全面见通知书
	public static final String CODE_PEdorUW = "25"; // 保全核保通知书
	public static final String CODE_PEdorDEFER = "26"; // 保全延期承保通知书
	public static final String CODE_PEdorHIInfo = "BQ27"; // 保全增补健康告知信息通知书
	public static final String CODE_PEdorCMInfo = "BQ28"; // 保全要件变更信息通知书
	public static final String CODE_PEdorPreInvali = "BQ29"; // 保全失效预终止通知书
	public static final String CODE_PEdorEndCont = "BQ30"; // 保全公司解约通知书
	public static final String CODE_PEdorPayInfo = "BQ31"; // 保全收费通知书
	public static final String CODE_PEdorGetInfo = "BQ32"; // 保全付费通知书
	public static final String CODE_PEdorExpireTerminate = "BQ34"; // 保全满期终止通知书

	public static final String CODE_PEdorAutoPayAG = "BQ17"; // 保全催付上柜领取通知书
	public static final String CODE_PEdorAutoPayNoAG = "BQ18"; // 保全催付领取通知书

	public static final String CODE_EdorUWSPEC = "BQ80"; // 保全核保特别约定通知书
	public static final String CODE_EdorUWAdd = "BQ81"; // 保全核保加费通知书
	public static final String CODE_EdorUWXE = "BQ82"; // 保全核保限额通知书
	public static final String CODE_EdorUWAddGet = "BQ83"; // 保全核保加退费通知书
	public static final String CODE_EdorUWRANT = "BQ84"; // 保全核保拒保通知书
	public static final String CODE_EdorUWYQ = "BQ85"; // 保全核保延期通知书
	public static final String CODE_EdorUWSY = "BQ86"; // 保全核保修改事项所要材料通知书
	public static final String CODE_EdorUWJY = "BQ87"; // 保全核保拒保延期附加险通知书
	public static final String CODE_EdorUWWJ = "BQ88"; // 保全核保问卷通知书
	public static final String CODE_EdorUWBT = "BQ89"; // 保全核保不同意通知书
	public static final String CODE_EdorUWTJ = "BQ90"; // 保全体检通知书

	public static final String CODE_PEdorLOANPREEND = "BQ38"; // 贷款自垫预终止通知书
	public static final String CODE_PEdorLOANEND = "BQ39"; // 贷款终止通知书
	public static final String CODE_PRnewDECLINE = "40"; // 续保拒保通知书
	public static final String CODE_PRnewPE = "43"; // 续保体检通知书
	public static final String CODE_PRnewMEET = "44"; // 续保面见通知书
	public static final String CODE_PRnewUW = "45"; // 续保核保通知书
	public static final String CODE_PRnewDEFER = "46"; // 续保延期承保通知书
	public static final String CODE_PRnewNotice = "47"; // 续保催收通知书
	public static final String CODE_PRnewSure = "49"; // 续保催收通知书

	// 续期
	public static final String CODE_PERSONREPAY = "A0";// 个人续期缴费通知书
	public static final String CODE_AGENTREPAY = "A1";// 银代续期缴费通知书
	public static final String CODE_GRPREPAY = "31";// 团体续期缴费通知书
	public static final String CODE_GRPACCOUNTREPAY = "A3";// 团体帐户型续期缴费通知书
	public static final String CODE_REFUNDREPAY = "A4";// 续期保费退费通知单
	public static final String CODE_DECLINEEXTEND = "A5";// 不续保通知书
	public static final String CODE_EXTEND = "A6";// 续保通知书
	public static final String CODE_CHANGINGWAITER = "A7";// 服务人员变更通知书
	public static final String CODE_TLREPAY = "A8";// 投连缴费通知书
	public static final String CODE_REHASTENPAY = "A9";// 二次催缴通知书
	public static final String CODE_TLTERMINATE = "B0";// 投连即将终止通知书
	public static final String CODE_EXTENDTRANSFERY = "B1";// 续期划款成功通知书(年缴)
	public static final String CODE_EXTENDTRANSFERM = "B2";// 续期划款成功通知书(月缴)
	public static final String CODE_ACCOUNTDRAW = "B3";// 账户领取通知单

	// 理赔
	public static final String CODE_PPASSPAY = "C0";// 给付批单(个人)
	public static final String CODE_GRPPASSPAY = "C1";// 给付批单(团体)
	public static final String CODE_PASSRELEASE = "C2";// 豁免批单
	public static final String CODE_PERSONFEE = "C3";// 医疗费给付清单(个人)
	public static final String CODE_GRPFEE = "C4";// 医疗费给付清单（团体）
	public static final String CODE_CONTRACT = "C5";// 合同处理批单
	public static final String CODE_CLAIMNOTICE1 = "C6";// 理赔决定通知书（赔案层面)
	public static final String CODE_CLAIMNOTICE2 = "C7";// 理赔决定通知书（保项层面）
	public static final String CODE_SECONDCHECK = "C8";// 二次核保通知书
	public static final String CODE_CASENOTICE = "C9";// 非结案单证 单证通知书
	public static final String CODE_CLIENTCONDOLE = "D0";// 客户慰问书
	public static final String CODE_CASESIGNIN = "D1";// 单证签收单
	public static final String CODE_CASEAdd = "D2";// 单证补充通知单（问题件）
	public static final String CODE_CHECKUPNOTICE = "D3";// 鉴定提示通知
	public static final String CODE_PAYWARRANT = "D4";// 付费凭证
	public static final String CODE_SECONDCHECK1 = "D5";// 二次核保通知书

	public static final String CODE_BONUSPAY = "30"; // 个人红利派发通知书
	public static final String CODE_REPAY = "31"; // 续期缴费通知书
	public static final String CODE_PINVOICE = "32"; // 个人发票
	public static final String CODE_GINVOICE = "33"; // 团体发票
	public static final String CODE_GRPBONUSPAY = "34"; // 团体红利派发通知书

	public static final String CODE_GRP_UW = "50"; // 团体核保通知书
	public static final String CODE_GRP_DECLINE = "51"; // 团体拒保通知书
	public static final String CODE_GRP_WITHDRAW = "52"; // 团体撤单通知书
	public static final String CODE_GRP_DEFER = "53"; // 团体延期承保通知书
	public static final String CODE_GRP_GRPFIRSTPAY = "57"; // 团体首期交费通知书
	public static final String CODE_GRP_QUEST = "54";// 团单新契约问题件通知书
	public static final String CODE_GRP_UINT = "56";// 团单新契约客户合并通知书

	public static final String ASK_GRP_DECLINE = "60"; // 团体询价拒保通知书
	public static final String ASk_GRP_SUCESS = "61"; // 团体询价成功通知书
	public static final String Ask_GRP_WITHDRAW = "62"; // 团体询价撤单通知书
	public static final String ASK_GRP_DEFER = "63"; // 团体询价延期通知书
	public static final String ASK_GRP_INFO = "64"; // 团体询价补充材料通知书
	public static final String ASK_GRP_TRACK = "65"; // 团单询价跟踪通知书

	public static final String CODE_GRP_PE = "73"; // 团体体检通知书
	public static final String CODE_GRP_MEET = "74"; // 团体契调通知书
	public static final String CODE_GRP_UWREQUIRE = "75";// 团体核保要求通知书
	public static final String CODE_GRP_UWRESULT = "76";// 团单核保结论通知书
	public static final String CODE_GRP_REFUND = "78";// 团单溢交退费通知书
	// PrtType 打印方式
	public static final String PT_FRONT = "0"; // 前台打印
	public static final String PT_BACK = "1"; // 后台打印

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	// 业务处理相关变量
	/** 全局数据 */
	private String mOperate = "";
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private VData mInputData = new VData();

	public PrintManagerBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("REQUEST")
				&& !cOperate.equals("PRINT") && !cOperate.equals("PRINT2")
				&& !cOperate.equals("REQ") && !cOperate.equals("PRINTQD")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		logger.debug("Operate is :" + cOperate);
		mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!dealData()) {
			return false;
		}

		// 保存数据
		String tOperate = "";
		VData tVData = new VData();
		tVData.add(mLOPRTManagerSchema);

		if (mOperate.equals("REQUEST")) {
			tOperate = "INSERT";
		} else if (mOperate.equals("CONFIRM")) {
			tOperate = "UPDATE";
		} else if (mOperate.equals("REQ")) {
			// 调用者通过getResult得到处理过的数据，在外部对得到的数据再进行处理
			mResult = tVData;
			return true;
		} else if (mOperate.equals("PRINT") || mOperate.equals("PRINT2")
				|| mOperate.equals("PRINTQD")) {
			// 直接返回
			return true;
		}

		PrintManagerBLS tPrintManagerBLS = new PrintManagerBLS();

		if (!tPrintManagerBLS.submitData(tVData, tOperate)) {
			mErrors.copyAllErrors(tPrintManagerBLS.mErrors);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();

		tGlobalInput.ComCode = "8611";
		tGlobalInput.ManageCom = "8611";
		tGlobalInput.Operator = "001";

		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		tLOPRTManagerSchema.setPrtSeq("810000000002888");

		VData vData = new VData();

		vData.add(tGlobalInput);
		vData.add(tLOPRTManagerSchema);

		PrintManagerBL tPrintManagerBL = new PrintManagerBL();

		tPrintManagerBL.submitData(vData, "CONFIRM");

		logger.debug(tPrintManagerBL.mErrors.getFirstError());

	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (mOperate.equals("REQUEST") || mOperate.equals("REQ")) { // 打印申请
			// 处于未打印状态的通知书在打印队列中只能有一个
			// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

			tLOPRTManagerDB.setCode(mLOPRTManagerSchema.getCode());
			tLOPRTManagerDB.setOtherNo(mLOPRTManagerSchema.getOtherNo());
			tLOPRTManagerDB
					.setOtherNoType(mLOPRTManagerSchema.getOtherNoType());
			tLOPRTManagerDB.setStandbyFlag1(mLOPRTManagerSchema
					.getStandbyFlag1());
			tLOPRTManagerDB.setStandbyFlag2(mLOPRTManagerSchema
					.getStandbyFlag2());
			tLOPRTManagerDB.setStateFlag("0");

			LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();

			if (tLOPRTManagerSet == null) {
				buildError("dealData", "查询LOPRTManager表时出现错误");
				return false;
			}

			if (tLOPRTManagerSet.size() != 0) {
				buildError("dealData", "处于未打印状态的通知书在打印队列中只能有一个");
				return false;
			}

			String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);

			mLOPRTManagerSchema.setPrtSeq(PubFun1.CreateMaxNo("PRTSEQNO",
					strNoLimit));
			mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			mLOPRTManagerSchema.setStateFlag("0");
			mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

		} else if (mOperate.equals("CONFIRM")) { // 打印执行

			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSchema.getPrtSeq());
			if (!tLOPRTManagerDB.getInfo()) {
				mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
				return false;
			}

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("dealData", "无效的打印状态");
				return false;
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("dealData", "该打印请求不是在请求状态");
				return false;
			}

			// 调用打印服务
			if (!callPrintService(mLOPRTManagerSchema)) {
				return false;
			}

			// 打印后的处理
			mLOPRTManagerSchema.setStateFlag("1");
			mLOPRTManagerSchema.setDoneDate(PubFun.getCurrentDate());
			mLOPRTManagerSchema.setDoneTime(PubFun.getCurrentTime());

		} else if (mOperate.equals("PRINT") || mOperate.equals("PRINT2")) { // 打印之后所执行的操作
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSchema.getPrtSeq());
			if (!tLOPRTManagerDB.getInfo()) {
				mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
				return false;
			}

			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

			if (!callPrintService(mLOPRTManagerSchema)) {
				return false;
			}

		} else if (mOperate.equals("PRINTQD")) { // 打印清单
			if (!callPrintQDService(mLOPRTManagerSchema)) {
				return false;
			}
		} else {
			buildError("PrintManagerBL", "不支持的操作字符串");
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null || mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "PrintManagerBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 调用打印服务
	 * 
	 * @param aLOPRTManagerSchema
	 * @return
	 */
	private boolean callPrintService(LOPRTManagerSchema aLOPRTManagerSchema) {

		// 查找打印服务
		String strSQL = "SELECT * FROM LDCode WHERE CodeType = 'print_service'";
		strSQL += " AND Code = '" + aLOPRTManagerSchema.getCode() + "'";

		LDCodeSet tLDCodeSet = new LDCodeDB().executeQuery(strSQL);

		if (tLDCodeSet.size() == 0) {
			buildError("dealData", "找不到对应的打印服务类(Code = '"
					+ aLOPRTManagerSchema.getCode() + "')");
			return false;
		}

		// 调用打印服务
		LDCodeSchema tLDCodeSchema = tLDCodeSet.get(1);

		try {
			Class cls = Class.forName(tLDCodeSchema.getCodeAlias());
			PrintService ps = (PrintService) cls.newInstance();

			// 准备数据
			// String strOperate = tLDCodeSchema.getCodeName();

			// VData vData = new VData();

			// vData.add(mGlobalInput);
			// vData.add(aLOPRTManagerSchema);

			if (!ps.submitData(mInputData, mOperate)) {
				mErrors.copyAllErrors(ps.getErrors());
				return false;
			}

			mResult = ps.getResult();

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("callPrintService", ex.toString());
			return false;
		}

		return true;
	}

	/**
	 * 调用打印服务
	 * 
	 * @param aLOPRTManagerSchema
	 * @return
	 */
	private boolean callPrintQDService(LOPRTManagerSchema aLOPRTManagerSchema) {
		logger.debug("in callprintqdserver func");
		// 查找打印服务
		String strSQL = "SELECT * FROM LDCode WHERE CodeType = 'print_service'";
		strSQL += " AND Code = '" + aLOPRTManagerSchema.getCode() + "'";

		LDCodeSet tLDCodeSet = new LDCodeDB().executeQuery(strSQL);

		if (tLDCodeSet.size() == 0) {
			buildError("dealData", "找不到对应的打印服务类(Code = '"
					+ aLOPRTManagerSchema.getCode() + "')");
			return false;
		}

		// 调用打印服务
		LDCodeSchema tLDCodeSchema = tLDCodeSet.get(1);

		try {
			Class cls = Class.forName(tLDCodeSchema.getCodeAlias());
			PrintService ps = (PrintService) cls.newInstance();

			// 准备数据
			// String strOperate = tLDCodeSchema.getCodeName();

			VData vData = new VData();
			vData = (VData) mInputData.getObjectByObjectName("VData", 0);

			if (!ps.submitData(vData, "PRINT")) {
				mErrors.copyAllErrors(ps.getErrors());
				return false;
			}
			mResult = ps.getResult();
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("callPrintService", ex.toString());
			return false;
		}
		return true;
	}

}
