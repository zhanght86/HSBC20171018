package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LOPRTManager2DB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.schema.LOPRTManager2Schema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.LOPRTManager2Set;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

public class PrintManager2BL {
private static Logger logger = Logger.getLogger(PrintManager2BL.class);

	// 常量定义
	// OtherNoType 其它号码类型
	public static final String ONT_INDPOL = "00"; // 个人保单号
	public static final String ONT_GRPPOL = "01"; // 集体保单号
	public static final String ONT_CONT = "02"; // 合同号
	public static final String ONT_EDOR = "03"; // 批单号
	public static final String ONT_GET = "04"; // 实付收据号
	public static final String ONT_PRT = "05"; // 保单印刷号

	// Code 单据类型
	public static final String CODE_DECLINE = "00"; // 拒保通知书
	public static final String CODE_OUTPAY = "01"; // 首期溢交保费通知书
	public static final String CODE_BALANCE = "02"; // 退余额的打印格式
	public static final String CODE_PE = "03"; // 体检通知书
	public static final String CODE_MEET = "04"; // 面见通知书
	public static final String CODE_UW = "05"; // 核保通知书
	public static final String CODE_DEFER = "06"; // 延期承保通知书
	public static final String CODE_FIRSTPAY = "07"; // 首期交费通知书
	public static final String CODE_REFUND = "08"; // 新契约退费通知书
	public static final String CODE_WITHDRAW = "09"; // 撤单通知书

	public static final String CODE_URGE_FP = "10"; // 首期交费通知书催办通知书
	public static final String CODE_URGE_PE = "11"; // 体检通知书催办通知书
	public static final String CODE_URGE_UW = "12"; // 核保通知书催办通知书
	public static final String CODE_URGE_GC = "13"; // 收款收据
	public static final String CODE_AGEN_QUEST = "14"; // 业务员问题件通知书
	public static final String CODE_URGE_PB = "15"; // 缴费催办通知书
	public static final String CODE_OVERDUE = "18"; // 逾期通知书
	public static final String CODE_OVERDUE_DEPOSE = "19"; // 逾期作废通知书

	public static final String CODE_PEdorDECLINE = "20"; // 保全拒保通知书
	public static final String CODE_PEdorPE = "23"; // 保全体检通知书
	public static final String CODE_PEdorMEET = "24"; // 保全面见通知书
	public static final String CODE_PEdorUW = "25"; // 保全核保通知书
	public static final String CODE_PEdorDEFER = "26"; // 保全延期承保通知书

	public static final String CODE_PRnewDECLINE = "40"; // 续保拒保通知书
	public static final String CODE_PRnewPE = "43"; // 续保体检通知书
	public static final String CODE_PRnewMEET = "44"; // 续保面见通知书
	public static final String CODE_PRnewUW = "45"; // 续保核保通知书
	public static final String CODE_PRnewDEFER = "46"; // 续保延期承保通知书
	public static final String CODE_PRnewNotice = "47"; // 续保催收通知书
	public static final String CODE_PRnewSure = "49"; // 续保催收通知书

	public static final String CODE_BONUSPAY = "30"; // 个人红利派发通知书
	public static final String CODE_REPAY = "31"; // 续期缴费通知书
	public static final String CODE_PINVOICE = "32"; // 个人发票
	public static final String CODE_GINVOICE = "33"; // 团体发票
	public static final String CODE_GRPBONUSPAY = "34"; // 团体红利派发通知书

	public static final String CODE_GRP_UW = "50"; // 团体核保通知书
	public static final String CODE_GRP_DECLINE = "51"; // 团体拒保通知书
	public static final String CODE_GRP_WITHDRAW = "52"; // 团体撤单通知书
	public static final String CODE_GRP_DEFER = "53"; // 团体延期承保通知书

	public static final String ASK_GRP_DECLINE = "60"; // 团体询价拒保通知书
	public static final String ASk_GRP_SUCESS = "61"; // 团体询价成功通知书
	public static final String Ask_GRP_WITHDRAW = "62"; // 团体询价撤单通知书
	public static final String ASK_GRP_DEFER = "63"; // 团体询价延期通知书
	public static final String ASK_GRP_INFO = "64"; // 团体询价补充材料通知书
	public static final String ASK_GRP_TRACK = "65"; // 团单询价跟踪通知书

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
	private LOPRTManager2Schema mLOPRTManager2Schema = new LOPRTManager2Schema();

	public PrintManager2BL() {
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
				&& !cOperate.equals("REPRINT") && !cOperate.equals("REQ")
				&& !cOperate.equals("POSTREPRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		mOperate = cOperate;

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
		tVData.add(mLOPRTManager2Schema);

		if (mOperate.equals("REQUEST")) {
			tOperate = "INSERT";
		} else if (mOperate.equals("CONFIRM")) {
			tOperate = "UPDATE";
		} else if (mOperate.equals("POSTREPRINT")) {
			tOperate = "UPDATE";
		} else if (mOperate.equals("REQ")) {
			// 调用者通过getResult得到处理过的数据，在外部对得到的数据再进行处理
			mResult = tVData;
			return true;
		} else if (mOperate.equals("REPRINT")) {
			// 直接返回
			return true;
		}

		PrintManager2BLS tPrintManager2BLS = new PrintManager2BLS();

		if (!tPrintManager2BLS.submitData(tVData, tOperate)) {
			mErrors.copyAllErrors(tPrintManager2BLS.mErrors);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (mOperate.equals("REQUEST") || mOperate.equals("REQ")) { // 打印申请
			// 处于未打印状态的通知书在打印队列中只能有一个
			// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
			LOPRTManager2DB tLOPRTManager2DB = new LOPRTManager2DB();

			tLOPRTManager2DB.setCode(mLOPRTManager2Schema.getCode());
			tLOPRTManager2DB.setOtherNo(mLOPRTManager2Schema.getOtherNo());
			tLOPRTManager2DB.setOtherNoType(mLOPRTManager2Schema
					.getOtherNoType());
			tLOPRTManager2DB.setManageCom(mLOPRTManager2Schema.getManageCom());
			tLOPRTManager2DB.setAgentCode(mLOPRTManager2Schema.getAgentCode());
			tLOPRTManager2DB.setStandbyFlag1(mLOPRTManager2Schema
					.getStandbyFlag1());
			tLOPRTManager2DB.setStandbyFlag2(mLOPRTManager2Schema
					.getStandbyFlag2());
			tLOPRTManager2DB.setStateFlag("0");

			LOPRTManager2Set tLOPRTManager2Set = tLOPRTManager2DB.query();

			if (tLOPRTManager2Set == null) {
				buildError("dealData", "查询LOPRTManager2表时出现错误");
				return false;
			}

			if (tLOPRTManager2Set.size() != 0) {
				buildError("dealData", "处于未打印状态的通知书在打印队列中只能有一个");
				return false;
			}

			String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);

			mLOPRTManager2Schema.setPrtSeq(PubFun1.CreateMaxNo("PRTSEQ2NO",
					strNoLimit));
			mLOPRTManager2Schema.setReqCom(mGlobalInput.ComCode);
			mLOPRTManager2Schema.setReqOperator(mGlobalInput.Operator);
			mLOPRTManager2Schema.setExeCom(mGlobalInput.ComCode);
			mLOPRTManager2Schema.setExeOperator(mGlobalInput.Operator);
			mLOPRTManager2Schema.setStateFlag("1");
			mLOPRTManager2Schema.setPrtType("0");
			mLOPRTManager2Schema.setMakeDate(PubFun.getCurrentDate());
			mLOPRTManager2Schema.setMakeTime(PubFun.getCurrentTime());
			mLOPRTManager2Schema.setDoneDate(PubFun.getCurrentDate());
			mLOPRTManager2Schema.setDoneTime(PubFun.getCurrentTime());

		} else if (mOperate.equals("CONFIRM")) { // 打印执行

			LOPRTManager2DB tLOPRTManager2DB = new LOPRTManager2DB();
			tLOPRTManager2DB.setPrtSeq(mLOPRTManager2Schema.getPrtSeq());
			if (!tLOPRTManager2DB.getInfo()) {
				mErrors.copyAllErrors(tLOPRTManager2DB.mErrors);
				return false;
			}

			// 查询打印队列的信息
			mLOPRTManager2Schema = tLOPRTManager2DB.getSchema();

			if (mLOPRTManager2Schema.getStateFlag() == null) {
				buildError("dealData", "无效的打印状态");
				return false;
			} else if (!mLOPRTManager2Schema.getStateFlag().equals("0")) {
				buildError("dealData", "该打印请求不是在请求状态");
				return false;
			}

			// 调用打印服务
			if (!callPrintService(mLOPRTManager2Schema)) {
				return false;
			}

			// 打印后的处理
			mLOPRTManager2Schema.setStateFlag("1");
			mLOPRTManager2Schema.setDoneDate(PubFun.getCurrentDate());
			mLOPRTManager2Schema.setDoneTime(PubFun.getCurrentTime());

		} else if (mOperate.equals("REPRINT")) { // 补打所执行的操作
			LOPRTManager2DB tLOPRTManager2DB = new LOPRTManager2DB();
			tLOPRTManager2DB.setPrtSeq(mLOPRTManager2Schema.getPrtSeq());
			if (!tLOPRTManager2DB.getInfo()) {
				mErrors.copyAllErrors(tLOPRTManager2DB.mErrors);
				return false;
			}

			// 查询打印队列的信息
			mLOPRTManager2Schema = tLOPRTManager2DB.getSchema();

			if (!callPrintService(mLOPRTManager2Schema)) {
				return false;
			}

		} else if (mOperate.equals("POSTREPRINT")) {
			// LOPRTManager2Schema tLOPRTManager2Schema = new
			// LOPRTManager2Schema();
			// tLOPRTManager2Schema.setSchema(mLOPRTManager2Schema);
			mLOPRTManager2Schema.setExeCom(mGlobalInput.ComCode);
			mLOPRTManager2Schema.setExeOperator(mGlobalInput.Operator);
			mLOPRTManager2Schema.setDoneDate(PubFun.getCurrentDate());
			mLOPRTManager2Schema.setDoneTime(PubFun.getCurrentTime());

		} else {
			buildError("dealData", "不支持的操作字符串");
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
		mLOPRTManager2Schema.setSchema((LOPRTManager2Schema) cInputData
				.getObjectByObjectName("LOPRTManager2Schema", 0));

		if (mGlobalInput == null || mLOPRTManager2Schema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "PrintManager2BL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 调用打印服务
	 * 
	 * @param aLOPRTManager2Schema
	 * @return
	 */
	private boolean callPrintService(LOPRTManager2Schema aLOPRTManager2Schema) {

		// 查找打印服务
		String strSQL = "SELECT * FROM LDCode WHERE CodeType = 'print_service'";
		strSQL += " AND Code = '" + "?Code?" + "'";
		strSQL += " AND OtherSign = '0'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("Code", aLOPRTManager2Schema.getCode());
		LDCodeSet tLDCodeSet = new LDCodeDB().executeQuery(sqlbv);

		if (tLDCodeSet.size() == 0) {
			buildError("dealData", "找不到对应的打印服务类(Code = '"
					+ aLOPRTManager2Schema.getCode() + "')");
			return false;
		}

		// 调用打印服务
		LDCodeSchema tLDCodeSchema = tLDCodeSet.get(1);

		try {
			Class cls = Class.forName(tLDCodeSchema.getCodeAlias());
			PrintService ps = (PrintService) cls.newInstance();

			// 准备数据
			String strOperate = tLDCodeSchema.getCodeName();

			VData vData = new VData();

			vData.add(mGlobalInput);
			vData.add(aLOPRTManager2Schema);

			if (!ps.submitData(vData, strOperate)) {
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

	public static void main(String[] args) {

		GlobalInput tGlobalInput = new GlobalInput();

		tGlobalInput.ComCode = "8611";
		tGlobalInput.ManageCom = "8611";
		tGlobalInput.Operator = "001";

		// LOPRTManager2Schema tLOPRTManager2Schema = new LOPRTManager2Schema();
		// tLOPRTManager2Schema.setOtherNo("86110020030370000005");
		// tLOPRTManager2Schema.setOtherNoType("04");
		// tLOPRTManager2Schema.setCode("13");
		// VData vData = new VData();
		// vData.add(tGlobalInput);
		// vData.add(tLOPRTManager2Schema);
		// PrintManager2BL tPrintManager2BL = new PrintManager2BL();
		// tPrintManager2BL.submitData(vData, "REQUEST");

		LOPRTManager2Schema tLOPRTManager2Schema = new LOPRTManager2Schema();
		tLOPRTManager2Schema.setPrtSeq("86000020040820000001");
		VData vData = new VData();
		vData.add(tGlobalInput);
		vData.add(tLOPRTManager2Schema);
		PrintManager2BL tPrintManager2BL = new PrintManager2BL();
		tPrintManager2BL.submitData(vData, "REPRINT");

	}
}
