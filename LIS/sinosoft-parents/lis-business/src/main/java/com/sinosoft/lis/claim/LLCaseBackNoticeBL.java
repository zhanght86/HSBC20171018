package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description: 理赔回退收费通知书打印:-----理赔回退收费通知书打印------LLCaseBackNotice.vts
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author ccc
 * @version 1.0
 */
public class LLCaseBackNoticeBL {
private static Logger logger = Logger.getLogger(LLCaseBackNoticeBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();
	private PubFun mPubFun = new PubFun();
	LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private String mClmNo = ""; // 赔案号
	private String mBackReason = ""; // 回退原因
	private String mPrtNoticeNo = "";

	public LLCaseBackNoticeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------单证打印：理赔查勘费用调查单-----LLCaseBackNoticeBL测试-----开始----------");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		if (!pubSubmit()) {
			return false;
		}

		logger.debug("----------单证打印：理赔查勘费用调查单------LLCaseBackNoticeBL测试-----结束----------");
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData;

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNoQ"); // 赔案号
		mBackReason = (String) mTransferData.getValueByName("BackReasonQ"); // 回退原因
		mPrtNoticeNo = (String) mTransferData.getValueByName("PrtNoticeNo"); // 通知书号码
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("LLCaseBackNotice.vts", "");

		// #############################################################################
		// 查询调查申请表-----调查机构
		LLClaimDB tLLClaimDB = new LLClaimDB();
		tLLClaimDB.setClmNo(mClmNo);
		tLLClaimDB.getInfo();

		logger.debug("--------以下为单个数据变量赋值--------");

		// 出险人
		String ChuXianRen = "";
		// ExeSQL tExeSQL = new ExeSQL();
		// String tLLCaseSql = "select customerno from llcase where caseno = '"
		// +
		// mClmNo + "'";
		// String tCustomerNo = tExeSQL.getOneValue(tLLCaseSql);
		ExeSQL tExeSQL7 = new ExeSQL();
		String tLLCaseSql2 = "select name from ldperson where customerno in (select customerno from llcase where caseno = '"
				+ "?caseno?" + "')";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tLLCaseSql2);
		sqlbv.put("caseno", mClmNo);
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL7.execSQL(sqlbv);
		if (tSSRS.getMaxRow() != 0) {
			// 循环处理每一条打印记录
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				ChuXianRen = ChuXianRen + " " + tSSRS.GetText(1, i);
			}
		}
		// ChuXianRen = tExeSQL7.getOneValue(tLLCaseSql2);
		String tPolNo = "";
		ExeSQL ttExeSQL = new ExeSQL();
		String tLLClaimDetailSql = "select polno from llclaimdetail where clmno = '"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tLLClaimDetailSql);
		sqlbv1.put("clmno", mClmNo);
		tPolNo = ttExeSQL.getOneValue(sqlbv1);
		String tContNo = "";
		ExeSQL tExeSQL2 = new ExeSQL();
		String tLCPolSql = "select contno from lcpol where polno = '" + "?polno?"
				+ "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tLCPolSql);
		sqlbv2.put("polno", tPolNo);
		tContNo = tExeSQL2.getOneValue(sqlbv2);
		ExeSQL tExeSQL3 = new ExeSQL();
		SSRS tSsrs = new SSRS();
		String tLLClaiUWMainSql = "select AuditPer,ExamPer from llclaimuwmain where clmno = '"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tLLClaiUWMainSql);
		sqlbv3.put("clmno", mClmNo);
		tSsrs = tExeSQL3.execSQL(sqlbv3);
		// 审批人
		String tLLCaseBackSql = "select operator from llcaseback where clmno = '"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tLLCaseBackSql);
		sqlbv4.put("clmno", mClmNo);
		ExeSQL tExeSQL5 = new ExeSQL();
		String tOperator = tExeSQL5.getOneValue(sqlbv4);
		double SumMoney = 0.00;
		ExeSQL tttExeSQL = new ExeSQL();
		String tLJSPay = " select (case when SumDuePayMoney is null then 0 else SumDuePayMoney end) from ljspay where otherno = '"
				+ "?clmno?" + "' and GetNoticeNo = '" + "?GetNoticeNo?" + "'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tLJSPay);
		sqlbv5.put("clmno", mClmNo);
		sqlbv5.put("GetNoticeNo", mPrtNoticeNo);
		SSRS ttSsrs = new SSRS();
		ttSsrs = tttExeSQL.execSQL(sqlbv5);
		SumMoney = Double.parseDouble(ttSsrs.GetText(1, 1));
		String moneyxx = new DecimalFormat("0.00").format(SumMoney);
		String SumMoneyD = mPubFun.getChnMoney(SumMoney);
		String tLDCodeSql = "select trim(codename) from ldcode where trim(code)='"
				+ "?backreason?" + "' and codetype = 'llbackreason'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tLDCodeSql);
		sqlbv6.put("backreason", mBackReason);
		ExeSQL tExeSQL4 = new ExeSQL();
		String mBakcReasonWord = tExeSQL4.getOneValue(sqlbv6);
		String ttLLCaseBacjSql = "select backdesc from llcaseback where clmno = '"
				+ "?clmno?" + "' and BackReason = '" + "?BackReason?" + "'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(ttLLCaseBacjSql);
		sqlbv7.put("clmno", mClmNo);
		sqlbv7.put("BackReason", mBackReason);
		ExeSQL tExeSQl6 = new ExeSQL();
		String tBackdesc = tExeSQl6.getOneValue(sqlbv7);
		
		String sql="select agentcode,managecom from ljspay where otherno = '"
			+ "?clmno?" + "' and GetNoticeNo = '" + "?GetNoticeNo?" + "'";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(sql);
		sqlbv8.put("clmno", mClmNo);
		sqlbv8.put("GetNoticeNo", mPrtNoticeNo);
		tSSRS=tExeSQl6.execSQL(sqlbv8);

		tTextTag.add("peianhao", mClmNo); // 赔案号
		tTextTag.add("noticeno", mPrtNoticeNo); // 通知书号码
		tTextTag.add("contno", tContNo); // 保单号
		tTextTag.add("chuxianren", ChuXianRen); // 出险人
		tTextTag.add("yuanyin", mBakcReasonWord); // 回退原因
		tTextTag.add("yuanyinxx", tBackdesc); // 回退原因详细说明
		tTextTag.add("moneys", moneyxx); // 总计金额小写
		tTextTag.add("MONEY", SumMoneyD); // 总计金额大写
		tTextTag.add("agentCode", tSSRS.GetText(1, 1)); // 代理人编码
		tTextTag.add("manageCom", tSSRS.GetText(1, 2)); // 管理机构
		tTextTag.add("shenhe", mLLClaimPubFunBL
				.getUserName(tSsrs.GetText(1, 1))); // 审核人
		tTextTag.add("shenpi", tOperator); // 审批人
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		tTextTag.add("shijian", SysDate);

		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		mResult.clear();
		mResult.addElement(tXmlExport);
		logger.debug("xmlexport=" + tXmlExport);
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRTInteInqPayBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}

}
