/**
 * <p>Title:需要输入统计项的的承保报表</p>
 * <p>Description: 7张报表</p>
 * <p>uw1：保险体检件统计报表  uw2: 撤单件统计报表 uw3: 拒保延期件统计报表</p>
 * <p>uw4: 职业分布统计报表  uw5: 高保额件分布状况统计报表uw6: 高保额件明细清单</p>
 * <p>uw7: 承保工作效率统计表 uw8: 保险新单状况统计表uw9: 问题件统计表</p>
 * <p>uw10: 核保师工作量统计</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @author guoxiang
 * @version 1.0
 *
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.Vector;

import com.sinosoft.lis.db.LDOccupationDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.vschema.LDOccupationSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class UWCheckBL {
private static Logger logger = Logger.getLogger(UWCheckBL.class);
	/** 业务处理相关变量 */
	private static String timeFactor = String
			.valueOf(1.00 - ((double) 110 / 365)); // 时间因子

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String mManageCom = "";
	private String UserCode = "";
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mRiskCode = ""; // 险种代码
	private String mRiskFlag = ""; // 主附险标志
	private String mDefineCode = ""; // 统计输入的报表代码
	private String[] mDay = null; // 统计输入的时间项
	private String[][] mFlag = { { "0", "17" }, { "18", "40" }, { "41", "50" },
			{ "51", "55" }, { "56", "60" }, { "60", "以上" } };
	private String mPolType ="";
	private String mSaleChnl ="";

	/** 构造函数 */
	public UWCheckBL() {
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) { // 打印

		// 全局变量
		mRiskCode = (String) cInputData.get(0);
		mRiskFlag = (String) cInputData.get(1);
		mDay = (String[]) cInputData.get(2);
		mManageCom = (String) cInputData.get(3);
		mDefineCode = (String) cInputData.get(4);
		mManageCom = (String) cInputData.get(5);
		mPolType = (String) cInputData.get(6);
		mSaleChnl = (String) cInputData.get(7);
		mPolType="86"+mPolType;
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		// test:
		logger.debug("大小：" + cInputData.size());
		logger.debug("险种:" + mRiskCode);
		logger.debug("主附险标志:" + mRiskFlag);
		logger.debug("starttime：" + mDay[0]);
		logger.debug("endtime：" + mDay[1]);
		logger.debug("操作员：" + mGlobalInput.Operator);
		logger.debug("操作机构：" + mGlobalInput.ManageCom);
		logger.debug("被统计的机构为：" + mManageCom);
		logger.debug("销售渠道：" + mSaleChnl);
		logger.debug("保单性质：" + mPolType);

		if (mGlobalInput == null) {
			CError.buildErr(this, "没有得到足够的信息！");

			return false;
		}
		// 最高核保师能查询其它任意核保师的核保统计
		if (mDefineCode.equals("uw10") || mDefineCode.equals("uw12")) {
			UserCode = (String) cInputData.get(8); // 传入的核保师工号
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RiskClaimBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/** 传输数据的公共方法 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINTGET") && !cOperate.equals("PRINTPAY")) {
			CError.buildErr(this,"不支持的操作字符串");

			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}

		mResult.clear();

		if (!getPrintDataPay()) {
			return false;
		}

		return true;
	}

	private boolean getPrintDataPay() {
		logger.debug("报表代码类型：" + mDefineCode);

		String xmlname = "";
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		FinDayTool tFinDayTool = new FinDayTool();
		ListTable tlistTable = new ListTable();
		String[] strArr = null;

		// ************************保险体检件统计报表***************************************************************************************************
		if (mDefineCode.equals("uw1")) {
			xmlexport.createDocument("UWHeath.vts", "printer");

			// 在遍历前先查询得到set或SSRS
			ExeSQL xdExeSQL = new ExeSQL();
			String New_sql = "select lcpol.InsuredAppAge from lcpol where  lcpol.grppolno='00000000000000000000'"
					+ "  and lcpol.salechnl in ('02','03') and lcpol.renewcount=0"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",ReportPubFun.getParameterStr(mManageCom, "?mngcom?")
							)
					+ ReportPubFun.getWherePart("lcpol.makedate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);
			New_sql = New_sql + " UNION ALL "
					+ StrTool.replace(New_sql, "lcpol", "lbpol");
			logger.debug("－－－－－－－新单 （预收）－－－－－－" + New_sql);
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(New_sql);
			sqlbv.put("mngcom", mManageCom);
			sqlbv.put("mday0", mDay[0]);
			sqlbv.put("mday1", mDay[1]);
			SSRS XDSSRS = xdExeSQL.execSQL(sqlbv);

			ExeSQL htffExeSQL = new ExeSQL();
			String HtFF_sql = "select max(lcpol.InsuredAppAge) from LOPRTManager,lcpol"
					+ " WHERE LOPRTManager.otherno=lcpol.ProposalNo "
					+ " and lcpol.grppolno='00000000000000000000' "
					+ " and lcpol.salechnl in ('02','03') and LOPRTManager.code='03' " // 体检通知书
					+ " and LOPRTManager.stateflag<>'0'  " // 打印的肯定回收
					+ " and LOPRTManager.prtseq in(select v_first_MESend.v_prtseq from v_first_MESend)　" // 过滤同一个人跨月发放体检件的情况
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("LOPRTManager.MakeDate",
							ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1) // 发放入机日期
					+ " group by lcpol.prtno ";
			HtFF_sql = HtFF_sql + " UNION ALL "
					+ StrTool.replace(HtFF_sql, "lcpol", "lbpol");
			logger.debug("--------(发放时间的)体检件---－－－－" + HtFF_sql);
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(HtFF_sql);
			sqlbv1.put("mngcom", mManageCom);
			sqlbv1.put("mday0", mDay[0]);
			sqlbv1.put("mday1", mDay[1]);
			SSRS HTFFSSRS = htffExeSQL.execSQL(sqlbv1);

			ExeSQL htExeSQL = new ExeSQL();
			String Ht_sql = "select max(lcpol.InsuredAppAge) from LOPRTManager,LZSysCertify,lcpol"
					+ " WHERE LOPRTManager.otherno=lcpol.ProposalNo "
					+ " and LZSysCertify.certifyno=LOPRTManager.PrtSeq "
					+ " and lcpol.grppolno='00000000000000000000' "
					+ " and lcpol.salechnl in ('02','03') and LOPRTManager.code='03' " // 体检通知书
					+ " and LOPRTManager.stateflag='2'  and LZSysCertify.StateFlag='1' " // 打印的单据已经回复,正常回收
					+ " and LZSysCertify.CertifyCode='8888' " // 体检通知书
					+ " and LOPRTManager.prtseq in(select v_first_MEBack.v_prtseq from v_first_MEBack)　" // 过滤同一个人跨月发放体检件的情况
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart(
							"LZSysCertify.TakeBackMakeDate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"),
							1) // 回收入机日期
					+ " group by lcpol.prtno ";
			Ht_sql = Ht_sql + " UNION ALL "
					+ StrTool.replace(Ht_sql, "lcpol", "lbpol");
			logger.debug("--------(回收时间的)体检件---－－－－" + Ht_sql);
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(Ht_sql);
			sqlbv2.put("mngcom", mManageCom);
			sqlbv2.put("mday0", mDay[0]);
			sqlbv2.put("mday1", mDay[1]);
			SSRS HTSSRS = htExeSQL.execSQL(sqlbv2);

			ExeSQL specExeSQL = new ExeSQL();
			String Spec_sql = "select max(lcpol.InsuredAppAge) from LOPRTManager,LZSysCertify,lcpol"
					+ " WHERE LOPRTManager.otherno=lcpol.ProposalNo "
					+ " and LZSysCertify.certifyno=LOPRTManager.PrtSeq "
					+ " and lcpol.grppolno='00000000000000000000' "
					+ " and lcpol.salechnl in ('02','03') and LOPRTManager.code='03' " // 体检通知书
					+ " and LOPRTManager.stateflag='2'  and LZSysCertify.StateFlag='1' " // 打印的单据已经回复,正常回收
					+ " and LZSysCertify.CertifyCode='8888' " // 体检通知书
					+ " and LOPRTManager.prtseq in(select v_first_MEBack.v_prtseq from v_first_MEBack)　" // 过滤同一个人跨月发放体检件的情况
					+ " and lcpol.uwflag='9'" // 标准体
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart(
							"LZSysCertify.TakeBackMakeDate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"),
							1) // 回收入机日期
					+ " group by lcpol.prtno ";

			Spec_sql = Spec_sql + " UNION ALL "
					+ StrTool.replace(Spec_sql, "lcpol", "lbpol");
			logger.debug("－－－－－(回收时间的)_标准件数－－－－－" + Spec_sql);
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(Spec_sql);
			sqlbv3.put("mngcom", mManageCom);
			sqlbv3.put("mday0", mDay[0]);
			sqlbv3.put("mday1", mDay[1]);
			SSRS specSSRS = specExeSQL.execSQL(sqlbv3);
			String SecondSpec_sql = "select max(lcpol.InsuredAppAge) from LOPRTManager,LZSysCertify,lcpol"
					+ " WHERE LOPRTManager.otherno=lcpol.ProposalNo "
					+ " and LZSysCertify.certifyno=LOPRTManager.PrtSeq "
					+ " and lcpol.grppolno='00000000000000000000' "
					+ " and lcpol.salechnl in ('02','03') and LOPRTManager.code='03' " // 体检通知书
					+ " and LOPRTManager.stateflag='2'  and LZSysCertify.StateFlag='1' " // 打印的单据已经回复,正常回收
					+ " and LZSysCertify.CertifyCode='8888' " // 体检通知书
					+ " and LOPRTManager.prtseq in(select v_first_MEBack.v_prtseq from v_first_MEBack)　" // 过滤同一个人跨月发放体检件的情况
					+ " and lcpol.uwflag='4'" // 次标准体
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart(
							"LZSysCertify.TakeBackMakeDate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"),
							1) // 回收入机日期
					+ " group by lcpol.prtno ";
			SecondSpec_sql = SecondSpec_sql + " UNION ALL "
					+ StrTool.replace(SecondSpec_sql, "lcpol", "lbpol");
			logger.debug("－－－－－((回收时间的)体检_次标准件数sql:" + SecondSpec_sql);
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(SecondSpec_sql);
			sqlbv4.put("mngcom", mManageCom);
			sqlbv4.put("mday0", mDay[0]);
			sqlbv4.put("mday1", mDay[1]);
			ExeSQL SecondSpecExeSQL = new ExeSQL();
			SSRS SecondSpecSSRS = SecondSpecExeSQL.execSQL(sqlbv4);
			ExeSQL NoPassExeSQL = new ExeSQL();
			String NoPass_sql = "select max(lcpol.InsuredAppAge) from LOPRTManager,LZSysCertify,lcpol"
					+ " WHERE LOPRTManager.otherno=lcpol.ProposalNo "
					+ " and LZSysCertify.certifyno=LOPRTManager.PrtSeq "
					+ " and lcpol.grppolno='00000000000000000000' "
					+ " and lcpol.salechnl in ('02','03') and LOPRTManager.code='03' " // 体检通知书
					+ " and LOPRTManager.stateflag='2'  and LZSysCertify.StateFlag='1' " // 打印的单据已经回复,正常回收
					+ " and LZSysCertify.CertifyCode='8888' "
					+ " and LOPRTManager.prtseq in(select v_first_MEBack.v_prtseq from v_first_MEBack)　" // 过滤同一个人跨月发放体检件的情况
					+ " and lcpol.uwflag in('1','2')" // 拒保延期
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart(
							"LZSysCertify.TakeBackMakeDate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"),
							1) // 回收入机日期
					+ " group by lcpol.prtno ";
			NoPass_sql = NoPass_sql + " UNION ALL "
					+ StrTool.replace(NoPass_sql, "lcpol", "lbpol");
			logger.debug("－－－－－((回收时间的)体检_拒/延件数sql:" + NoPass_sql);
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(NoPass_sql);
			sqlbv5.put("mngcom", mManageCom);
			sqlbv5.put("mday0", mDay[0]);
			sqlbv5.put("mday1", mDay[1]);
			SSRS NoPassSSRS = NoPassExeSQL.execSQL(sqlbv5);

			ExeSQL BackExeSQL = new ExeSQL();
			String Back_sql = "select max(lcpol.InsuredAppAge) from LOPRTManager,LZSysCertify,lcpol"
					+ " WHERE LOPRTManager.otherno=lcpol.ProposalNo "
					+ " and LZSysCertify.certifyno=LOPRTManager.PrtSeq "
					+ " and lcpol.grppolno='00000000000000000000' "
					+ " and lcpol.salechnl in ('02','03') and LOPRTManager.code='03' " // 体检通知书
					+ " and LOPRTManager.stateflag='2'  and LZSysCertify.StateFlag='1' " // 打印的单据已经回复,正常回收
					+ " and LZSysCertify.CertifyCode='8888' "
					+ " and LOPRTManager.prtseq in(select v_first_MEBack.v_prtseq from v_first_MEBack)　" // 过滤同一个人跨月发放体检件的情况
					+ "  and lcpol.uwflag in('a')" // 撤单
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart(
							"LZSysCertify.TakeBackMakeDate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"),
							1) // 回收入机日期
					+ " group by lcpol.prtno ";
			Back_sql = Back_sql + " UNION ALL "
					+ StrTool.replace(Back_sql, "lcpol", "lbpol");
			logger.debug("－－－－－((回收时间的)体检_撤单件数sql:" + Back_sql);
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(Back_sql);
			sqlbv6.put("mngcom", mManageCom);
			sqlbv6.put("mday0", mDay[0]);
			sqlbv6.put("mday1", mDay[1]);
			SSRS BackSSRS = BackExeSQL.execSQL(sqlbv6);

			ExeSQL YXExeSQL = new ExeSQL();
			String YX_sql = "select max(lcpol.InsuredAppAge) from LOPRTManager,LZSysCertify,lcpol"
					+ " WHERE LOPRTManager.otherno=lcpol.ProposalNo "
					+ " and LZSysCertify.certifyno=LOPRTManager.PrtSeq "
					+ " and lcpol.grppolno='00000000000000000000' "
					+ " and lcpol.salechnl in ('02','03') and LOPRTManager.code='03' " // 体检通知书
					+ " and LOPRTManager.stateflag='2'  and LZSysCertify.StateFlag='1' " // 打印的单据已经回复,正常回收
					+ " and LZSysCertify.CertifyCode='8888' " // 体检通知书
					+ " and LOPRTManager.prtseq in(select v_first_MEBack.v_prtseq from v_first_MEBack)　" // 过滤同一个人跨月发放体检件的情况
					+ "  and lcpol.ProposalNo in (select ProposalNo from  LCPENotice where  PEResult is not null )" // 体检阳性
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart(
							"LZSysCertify.TakeBackMakeDate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"),
							1) // 回收入机日期
					+ " group by lcpol.prtno ";
			YX_sql = YX_sql + " UNION ALL "
					+ StrTool.replace(YX_sql, "lcpol", "lbpol");
			logger.debug("－－－－－((回收时间的)体检_阳性sql:" + YX_sql);
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(YX_sql);
			sqlbv7.put("mngcom", mManageCom);
			sqlbv7.put("mday0", mDay[0]);
			sqlbv7.put("mday1", mDay[1]);
			SSRS YXSSRS = YXExeSQL.execSQL(sqlbv7);

			ExeSQL QYExeSQL = new ExeSQL();
			String QY_sql = "select max(lcpol.InsuredAppAge)"
					+ " from lcpol,LCRReport"
					+ " where lcpol.salechnl in ('02','03')"
					+ " and lcpol.grppolno='00000000000000000000' and lcpol.renewcount=0"
					+ " and lcpol.proposalno=LCRReport.polno and trim(LCRReport.serialno)='0'"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("LCRReport.MakeDate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1) // 契约入机日期
					+ " group by lcpol.prtno ";
			QY_sql = QY_sql + " UNION ALL "
					+ StrTool.replace(QY_sql, "lcpol", "lbpol");
			logger.debug("－－－－－((回收时间的)契约sql:" + QY_sql);
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(QY_sql);
			sqlbv8.put("mngcom", mManageCom);
			sqlbv8.put("mday0", mDay[0]);
			sqlbv8.put("mday1", mDay[1]);
			SSRS QYSSRS = QYExeSQL.execSQL(sqlbv8);

			ExeSQL QYHFExeSQL = new ExeSQL();
			String QYHF_sql = "select max(lcpol.InsuredAppAge)"
					+ " from lcpol,LCRReport"
					+ " where lcpol.proposalno=LCRReport.polno"
					+ " and lcpol.grppolno='00000000000000000000' and lcpol.renewcount=0"
					+ " and lcpol.salechnl in ('02','03') and lcrreport.ReplyFlag='1' and trim(LCRReport.serialno)='0'"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("LCRReport.ReplyDate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1) // 契约回收日期
					+ " group by lcpol.prtno ";
			QYHF_sql = QYHF_sql + " UNION ALL "
					+ StrTool.replace(QYHF_sql, "lcpol", "lbpol");
			logger.debug("－－－－－((回收时间的)回复契约sql:" + QYHF_sql);
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(QYHF_sql);
			sqlbv9.put("mngcom", mManageCom);
			sqlbv9.put("mday0", mDay[0]);
			sqlbv9.put("mday1", mDay[1]);
			SSRS QYHFSSRS = QYHFExeSQL.execSQL(sqlbv9);

			tlistTable.setName("uw1");

			int snd = 0;
			int stjff = 0;
			int stj = 0;
			int sbj = 0;
			int scbj = 0;
			int sjyj = 0;
			int scdj = 0;
			int sysj = 0;
			int sqyj = 0;
			int sqyhf = 0;

			for (int i = 0; i < mFlag.length; i++) {
				String Flag = "";

				if (mFlag[i][1].equals("以上")) {
					Flag = mFlag[i][0] + mFlag[i][1];
				} else {
					Flag = mFlag[i][0] + "-" + mFlag[i][1];
				}

				int nd = 0;
				int tjff = 0;
				int tj = 0;
				int bj = 0;
				int cbj = 0;
				int jyj = 0;
				int cdj = 0;
				int ysj = 0;
				int qyj = 0;
				int qyhf = 0;

				for (int j = 1; j <= XDSSRS.MaxRow; j++) {
					nd += ReportPubFun.getCount(XDSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--新单件数
				}

				for (int j = 1; j <= HTFFSSRS.MaxRow; j++) {
					tjff += ReportPubFun.getCount(HTFFSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--体检件数(发放)
				}

				for (int j = 1; j <= HTSSRS.MaxRow; j++) {
					tj += ReportPubFun.getCount(HTSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄-体检件数（回收）
				}

				for (int j = 1; j <= specSSRS.MaxRow; j++) {
					bj += ReportPubFun.getCount(specSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--标准件数
				}

				for (int j = 1; j <= SecondSpecSSRS.MaxRow; j++) {
					cbj += ReportPubFun.getCount(SecondSpecSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--次标准件数
				}

				for (int j = 1; j <= NoPassSSRS.MaxRow; j++) {
					jyj += ReportPubFun.getCount(NoPassSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--拒/延件数
				}

				for (int j = 1; j <= BackSSRS.MaxRow; j++) {
					cdj += ReportPubFun.getCount(BackSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--撤单件数
				}

				for (int j = 1; j <= YXSSRS.MaxRow; j++) {
					ysj += ReportPubFun.getCount(YXSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--体检阳性
				}

				for (int j = 1; j <= QYSSRS.MaxRow; j++) {
					qyj += ReportPubFun.getCount(QYSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--契约发放件数
				}

				for (int j = 1; j <= QYHFSSRS.MaxRow; j++) {
					qyhf += ReportPubFun.getCount(QYHFSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--契约回收撤单件数
				}

				strArr = new String[11];
				strArr[0] = Flag;
				strArr[1] = String.valueOf(nd); // 预收件数
				strArr[2] = String.valueOf(tjff); // 体检发放件数
				strArr[3] = String.valueOf(tj); // 体检回收件数
				strArr[4] = String.valueOf(bj); // 标准件数
				strArr[5] = String.valueOf(cbj); // 次标准件数
				strArr[6] = String.valueOf(jyj); // 拒/延件数
				strArr[7] = String.valueOf(cdj); // 撤单件数
				strArr[8] = String.valueOf(ysj); // 体检阳性
				strArr[9] = String.valueOf(qyj); // 契约发放
				strArr[10] = String.valueOf(qyhf); // 契约回收
				tlistTable.add(strArr);
				snd = snd + nd;
				stjff = stjff + tjff;
				stj = stj + tj;
				sbj = sbj + bj;
				scbj = scbj + cbj;
				sjyj = sjyj + jyj;
				scdj = scdj + cdj;
				sysj = sysj + ysj;
				sqyj = sqyj + qyj;
				sqyhf = sqyhf + qyhf;
			}

			strArr = new String[11];
			strArr[0] = "合计";
			strArr[1] = new DecimalFormat("0").format(snd);
			strArr[2] = new DecimalFormat("0").format(stjff);
			strArr[3] = new DecimalFormat("0").format(stj);
			strArr[4] = new DecimalFormat("0").format(sbj);
			strArr[5] = new DecimalFormat("0").format(scbj);
			strArr[6] = new DecimalFormat("0").format(sjyj);
			strArr[7] = new DecimalFormat("0").format(scdj);
			strArr[8] = new DecimalFormat("0").format(sysj);
			strArr[9] = new DecimalFormat("0").format(sqyj);
			strArr[10] = new DecimalFormat("0").format(sqyhf);
			tlistTable.add(strArr);
			strArr = new String[11];
			strArr[0] = "ageGrp";
			strArr[1] = "nd"; // 预收件数
			strArr[2] = "tjff"; // 体检发放件数
			strArr[3] = "tj"; // 体检回收件数
			strArr[4] = "bj"; // 标准件数
			strArr[5] = "cbj"; // 次标准件数
			strArr[6] = "jyj"; // 拒/延件数
			strArr[7] = "cdj"; // 撤单件数
			strArr[8] = "ysj"; // 体检阳性
			strArr[9] = "qyj"; // 契约发放
			strArr[10] = "qyhf"; // 契约回收

			xmlexport.addListTable(tlistTable, strArr);
		}

		// ************************撤单件统计报表***************************************************************************************************
		if (mDefineCode.equals("uw2")) {
			xmlexport.createDocument("UWBack.vts", "printer");

			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			LMRiskAppSet tLMRiskAppSet = tLMRiskAppDB.query();
			String Back = "select lcpol.prtno,lcpol.proposalno,lcpol.riskcode,lcpol.prem,"
					+ "lcuwmaster.AgentCode,lcuwmaster.UWIdea,lcpol.amnt,lcpol.managecom,"
					+ " (select d.riskcode from lcpol d where d.polno=lcpol.mainpolno) mainriskcode,"
					+ " (select c.SubRiskFlag from  lmriskapp c where c.riskcode=lcpol.riskcode ) SubRiskFlag"
					+ " from lcpol,lcuwmaster"
					+ " where lcpol.proposalno=lcuwmaster.proposalno "
					+ " and lcpol.grppolno='00000000000000000000'  and lcpol.salechnl in ('02','03')"
					+ " and lcuwmaster.passflag='a'  and lcpol.renewcount=0 and lcpol.appflag='0'"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePart("lcuwmaster.modifydate",
							ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ " order by lcpol.managecom,mainriskcode,lcpol.prtno,SubRiskFlag";

			// 仅仅是承保时核保处理的，不用考虑b表
			// Back_sql=Back_sql+" UNION ALL
			// "+StrTool.replace(Back_sql,"lcpol","lbpol");
			ExeSQL Back_exesql = new ExeSQL();
			logger.debug("Back_sql" + Back);
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(Back);
			sqlbv10.put("mngcom", mManageCom);
			sqlbv10.put("mday0", mDay[0]);
			sqlbv10.put("mday1", mDay[1]);
			SSRS Back_ssrs = Back_exesql.execSQL(sqlbv10);

			// logger.debug("相关信息的条数是" + Back_ssrs.getMaxRow());
			if (Back_ssrs.getMaxRow() == 0) {
				CError.buildErr(this, "查询的结果是0");
			}

			String CompairPrtNO = "";
			int CompairNO = 1;

			for (int j = 1; j <= Back_ssrs.getMaxRow(); j++) {
				strArr = new String[8];
				strArr[0] = String.valueOf(CompairNO);
				strArr[1] = Back_ssrs.GetText(j, 1); // 印刷号

				if (CompairPrtNO.equals(Back_ssrs.GetText(j, 1))) {
					CompairNO--;
					strArr[0] = String.valueOf(CompairNO);
				}

				strArr[2] = ReportPubFun.getRiskName(Back_ssrs.GetText(j, 3),
						tLMRiskAppSet); // 险种;
				strArr[3] = String.valueOf(Double.parseDouble(Back_ssrs
						.GetText(j, 7))); // 保额
				strArr[4] = String.valueOf(Double.parseDouble(Back_ssrs
						.GetText(j, 4))); // 保费
				strArr[5] = Back_ssrs.GetText(j, 8); // 保单管理机构
				strArr[6] = Back_ssrs.GetText(j, 6); // 撤单原因
				strArr[7] = Back_ssrs.GetText(j, 5); // 代理人号

				CompairPrtNO = strArr[1];
				CompairNO++;
				tlistTable.add(strArr);
			}

			tlistTable.setName("uw2");
			strArr = new String[8];
			strArr[0] = "No";
			strArr[1] = "PrtNO";
			strArr[2] = "RiskCode";
			strArr[3] = "Amnt";
			strArr[4] = "Prem";
			strArr[5] = "Mng";
			strArr[6] = "BakcCause";
			strArr[7] = "AgentCode";
			xmlexport.addListTable(tlistTable, strArr);
		}

		// ************************拒保，延期件统计报表***************************************************************************************************
		if (mDefineCode.equals("uw3")) { // 拒保，延期件统计报表
			xmlexport.createDocument("UWNoPass.vts", "printer");

			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			LMRiskAppSet tLMRiskAppSet = tLMRiskAppDB.query();
			String sql = "select lcpol.prtno,lcpol.proposalno,lcpol.riskcode,lcpol.prem,lcpol.amnt,"
					+ " lcuwmaster.AgentCode,lcuwmaster.UWIdea,lcuwmaster.passflag,"
					+ " lcpol.insuredsex,lcpol.insuredappage,lcpol.InsuredNo,lcpol.ManageCom,"
					+ " (select d.riskcode from lcpol d where d.polno=lcpol.mainpolno) mainriskcode,"
					+ " (select c.SubRiskFlag from  lmriskapp c where c.riskcode=lcpol.riskcode ) SubRiskFlag"
					+ "  from lcpol,lcuwmaster"
					+ " where lcpol.proposalno=lcuwmaster.proposalno "
					+ " and lcpol.grppolno='00000000000000000000' and lcpol.renewcount=0  and lcpol.salechnl in ('02','03')"
					+ " and (lcuwmaster.passflag='1' or lcuwmaster.passflag='2') "
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePart("lcuwmaster.modifydate",
							ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ " order by lcuwmaster.passflag,lcpol.managecom,mainriskcode,lcpol.prtno,SubRiskFlag";

			// Back_sql=Back_sql+" UNION ALL
			// "+StrTool.replace(Back_sql,"lcpol","lbpol");
			ExeSQL Back_exesql = new ExeSQL();
			logger.debug("sql---" + sql);
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(sql);
			sqlbv11.put("mngcom", mManageCom);
			sqlbv11.put("mday0", mDay[0]);
			sqlbv11.put("mday1", mDay[1]);
			SSRS Back_ssrs = Back_exesql.execSQL(sqlbv11);

			// logger.debug("按照印刷号查询的相关信息的条数是" + Back_ssrs.getMaxRow());
			if (Back_ssrs.getMaxRow() == 0) {
				CError.buildErr(this, "查询的结果是0");
			}

			String CompairPrtNO = "";
			int CompairNO = 1;

			for (int j = 1; j <= Back_ssrs.getMaxRow(); j++) {
				strArr = new String[13];
				strArr[0] = String.valueOf(CompairNO);
				strArr[1] = Back_ssrs.GetText(j, 1); // 印刷号

				if (CompairPrtNO.equals(Back_ssrs.GetText(j, 1))) {
					CompairNO--;
					strArr[0] = String.valueOf(CompairNO);
				}

				strArr[2] = ReportPubFun.getRiskName(Back_ssrs.GetText(j, 3),
						tLMRiskAppSet); // 险种
				strArr[3] = Back_ssrs.GetText(j, 5); // 保额
				strArr[4] = Back_ssrs.GetText(j, 4); // 保费
				strArr[5] = ReportPubFun.getSexName(Back_ssrs.GetText(j, 9)); // 性别
				strArr[6] = Back_ssrs.GetText(j, 10); // 年龄
				strArr[7] = ReportPubFun.getLdPersonInfo(
						Back_ssrs.GetText(j, 11), "0").getWorkType(); // 职业
				strArr[8] = ReportPubFun.getLdPersonInfo(
						Back_ssrs.GetText(j, 11), "0").getOccupationCode(); // 职业代码
				strArr[9] = Back_ssrs.GetText(j, 12); // 分公司
				strArr[10] = ReportPubFun.getTypeName(Back_ssrs.GetText(j, 8)); // 核保结论
																				// 延/拒类型
				strArr[11] = Back_ssrs.GetText(j, 7); // 原因
				strArr[12] = Back_ssrs.GetText(j, 6); // 代理人号
				CompairPrtNO = strArr[1];
				CompairNO++;
				tlistTable.add(strArr);
			}

			tlistTable.setName("uw3");
			strArr = new String[13];
			strArr[0] = "no";
			strArr[2] = "riskcode";
			strArr[3] = "amnt";
			strArr[4] = "prem";
			strArr[5] = "sex";
			strArr[6] = "age";
			strArr[7] = "work";
			strArr[8] = "occupetype";
			strArr[9] = "mng";
			strArr[10] = "passflag";
			strArr[11] = "cause";
			strArr[12] = "agentcode";
			xmlexport.addListTable(tlistTable, strArr);
		}

		// *************************************职业分布统计报表***********************************************************
		if (mDefineCode.equals("uw4")) {
			xmlexport.createDocument("UWStaff.vts", "printer");

			// 在遍历前先查询得到set或SSRS
			ExeSQL xdExeSQL = new ExeSQL();
			String New_sql = "select lcpol.riskcode,lcpol.OccupationType from lcpol where lcpol.appflag='1' "
					+ " and lcpol.grppolno='00000000000000000000' and lcpol.renewcount=0 and lcpol.salechnl in ('02','03')"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("lcpol.SignDate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);

			// New_sql = New_sql + " GROUP BY lcpol.riskcode";
			New_sql = New_sql + " UNION ALL "
					+ StrTool.replace(New_sql, "lcpol", "lbpol");

			logger.debug("新单sql:" + New_sql);
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(New_sql);
			sqlbv12.put("mngcom", mManageCom);
			sqlbv12.put("mday0", mDay[0]);
			sqlbv12.put("mday1", mDay[1]);
			SSRS XDSSRS = xdExeSQL.execSQL(sqlbv12);
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();

			if (mRiskCode != "") {
				tLMRiskAppDB.setRiskCode(mRiskCode);
			}

			if (mRiskFlag != "") {
				tLMRiskAppDB.setSubRiskFlag("M");
			}

			LMRiskAppSet tLMRiskAppSet = tLMRiskAppDB.query();

			for (int i = 1; i <= tLMRiskAppSet.size(); i++) {
				LMRiskAppSchema tLMRiskAppSchema = tLMRiskAppSet.get(i);
				String[] tRow = new String[1];
				String[] tColumn = new String[1];
				tRow[0] = tLMRiskAppSchema.getRiskCode(); // 险种

				String[] mRow = new String[8];
				String[] mColumn = new String[1];
				mRow[0] = ReportPubFun.getCountSumUW(XDSSRS, tRow[0]); // 总承保件数
				mRow[1] = ReportPubFun.getClassCount(XDSSRS, tRow[0], "1"); // 职业分类一类
				mRow[2] = ReportPubFun.getClassCount(XDSSRS, tRow[0], "2"); // 职业分类二类
				mRow[3] = ReportPubFun.getClassCount(XDSSRS, tRow[0], "3"); // 职业分类三类
				mRow[4] = ReportPubFun.getClassCount(XDSSRS, tRow[0], "4"); // 职业分类四类
				mRow[5] = ReportPubFun.getClassCount(XDSSRS, tRow[0], "5"); // 职业分类五类
				mRow[6] = ReportPubFun.getClassCount(XDSSRS, tRow[0], "6"); // 职业分类六类
				mRow[7] = ReportPubFun.getClassCount(XDSSRS, tRow[0], "0"); // 职业分类0类

				FinCheckKey tF = new FinCheckKey(tRow, tColumn);
				FinCheckKey mF = new FinCheckKey(mRow, mColumn);
				tFinDayTool.enterBasicInfo(tF, mF);
			}

			double total = 0;
			double t1 = 0;
			double t2 = 0;
			double t3 = 0;
			double t4 = 0;
			double t5 = 0;
			double t6 = 0;
			double t0 = 0;
			tlistTable.setName("uw4");

			Vector tv = tFinDayTool.getAllRiskCode();
			logger.debug("key.size:" + tv.size());

			Enumeration e = tv.elements();

			while (e.hasMoreElements()) {
				String RiskCode = (String) e.nextElement();
				String[] ttRow = new String[1];
				String[] ttColumn = new String[1];
				ttRow[0] = RiskCode;

				FinCheckKey ttFinCheckKey = new FinCheckKey(ttRow, ttColumn);
				strArr = new String[9];
				strArr[0] = ReportPubFun.getRiskName(RiskCode, tLMRiskAppSet);
				strArr[1] = tFinDayTool.getUniqueValue(ttFinCheckKey, 0); // 总
				strArr[2] = tFinDayTool.getUniqueValue(ttFinCheckKey, 1); // 1类
				strArr[3] = tFinDayTool.getUniqueValue(ttFinCheckKey, 2); // 2类
				strArr[4] = tFinDayTool.getUniqueValue(ttFinCheckKey, 3); // 3类
				strArr[5] = tFinDayTool.getUniqueValue(ttFinCheckKey, 4); // 4类
				strArr[6] = tFinDayTool.getUniqueValue(ttFinCheckKey, 5); // 5类
				strArr[7] = tFinDayTool.getUniqueValue(ttFinCheckKey, 6); // 6类
				strArr[8] = tFinDayTool.getUniqueValue(ttFinCheckKey, 7); // 0类
				tlistTable.add(strArr);
				total += ReportPubFun.functionDouble(strArr[1]);
				t1 += ReportPubFun.functionDouble(strArr[2]);
				t2 += ReportPubFun.functionDouble(strArr[3]);
				t3 += ReportPubFun.functionDouble(strArr[4]);
				t4 += ReportPubFun.functionDouble(strArr[5]);
				t5 += ReportPubFun.functionDouble(strArr[6]);
				t6 += ReportPubFun.functionDouble(strArr[7]);
				t0 += ReportPubFun.functionDouble(strArr[8]);
			}

			strArr = new String[9];
			strArr[0] = "合计";
			strArr[1] = String.valueOf(total); // 总
			strArr[2] = String.valueOf(t1); // 1类
			strArr[3] = String.valueOf(t2); // 2类
			strArr[4] = String.valueOf(t3); // 3类
			strArr[5] = String.valueOf(t4); // 4类
			strArr[6] = String.valueOf(t5); // 5类
			strArr[7] = String.valueOf(t6); // 6类
			strArr[8] = String.valueOf(t0); // 0类
			tlistTable.add(strArr);
			strArr = new String[9];
			strArr[0] = "RiskCode";
			strArr[1] = "mSumCount";
			strArr[2] = "mFirstClass";
			strArr[3] = "mSencondClass";
			strArr[4] = "mThirdClass";
			strArr[5] = "mFourClass";
			strArr[6] = "mFiveClass";
			strArr[7] = "mSixClass";
			strArr[8] = "mNoClass";
			xmlexport.addListTable(tlistTable, strArr);
		}

		// *************************************高保额件分布状况统计表***********************************************************
		if (mDefineCode.equals("uw5")) {
			xmlexport.createDocument("UWHighAmnt.vts", "printer");

			ExeSQL xdExeSQL = new ExeSQL();
			String New_sql = "select max(lcpol.InsuredAppAge),LIFEAMNT(lcpol.insuredno)+accidentamnt(lcpol.insuredno) "
					+ " from lcpol where lcpol.appflag='1' "
					+ " and lcpol.grppolno='00000000000000000000' and lcpol.renewcount=0 and lcpol.salechnl in ('02','03')"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("lcpol.SignDate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1) + " GROUP BY lcpol.insuredno";
			New_sql = New_sql + " UNION ALL "
					+ StrTool.replace(New_sql, "lcpol", "lbpol");
			logger.debug("新单sql:" + New_sql);
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(New_sql);
			sqlbv13.put("mngcom", mManageCom);
			sqlbv13.put("mday0", mDay[0]);
			sqlbv13.put("mday1", mDay[1]);
			SSRS XDSSRS = xdExeSQL.execSQL(sqlbv13);

			for (int i = 1; i <= XDSSRS.MaxRow; i++) {
				String[] tRow = new String[1];
				String[] tColumn = new String[1];
				String[] mRow = new String[5];
				String[] mColumn = new String[1];
				tRow[0] = ReportPubFun.getAgeGroup(XDSSRS.GetText(i, 1), mFlag); // 年龄
				mRow[0] = "1"; // 承保件数
				mRow[1] = ReportPubFun.getHighAmnt(XDSSRS.GetText(i, 2),
						500000, 1000000);
				mRow[2] = ReportPubFun.getHighAmnt(XDSSRS.GetText(i, 2),
						1000000, 2000000);
				mRow[3] = ReportPubFun.getHighAmnt(XDSSRS.GetText(i, 2),
						2000000, 3000000);
				mRow[4] = ReportPubFun.getHighAmnt(XDSSRS.GetText(i, 2),
						3000000, 1000000000);

				// 取一个较大的数表示300万以上，有不能超过double 类型的最大的取值
				FinCheckKey tF = new FinCheckKey(tRow, tColumn);
				FinCheckKey mF = new FinCheckKey(mRow, mColumn);
				tFinDayTool.enterBasicInfo(tF, mF);
			}

			tlistTable.setName("uw5");

			double zcb = 0.0;
			double first = 0.0;
			double Second = 0.0;
			double third = 0.0;
			double forth = 0.0;
			double sum = 0.0;
			double avg = 0.0;

			for (int i = 0; i < mFlag.length; i++) {
				String Flag = "";

				if (mFlag[i][1].equals("以上")) {
					Flag = mFlag[i][0] + mFlag[i][1];
				} else {
					Flag = mFlag[i][0] + "-" + mFlag[i][1];
				}

				strArr = new String[8];

				Vector tv = tFinDayTool.getAllRiskCode();
				logger.debug("key.size:" + tv.size());

				Enumeration e = tv.elements();

				while (e.hasMoreElements()) {
					String AgeGroup = (String) e.nextElement();
					logger.debug("AgeGroup:" + AgeGroup);

					if (AgeGroup.equals(Flag)) {
						String[] ttRow = new String[1];
						String[] ttColumn = new String[1];
						ttRow[0] = Flag;

						FinCheckKey ttFinCheckKey = new FinCheckKey(ttRow,
								ttColumn);
						strArr[0] = Flag;
						strArr[1] = new DecimalFormat("0").format(tFinDayTool
								.getTotalValue(ttFinCheckKey, 0)); // 总承保件数
						strArr[2] = new DecimalFormat("0").format(tFinDayTool
								.getTotalValue(ttFinCheckKey, 1)); // 50-100万
						strArr[3] = new DecimalFormat("0").format(tFinDayTool
								.getTotalValue(ttFinCheckKey, 2)); // 100-200万
						strArr[4] = new DecimalFormat("0").format(tFinDayTool
								.getTotalValue(ttFinCheckKey, 3)); // 200-300万
						strArr[5] = new DecimalFormat("0").format(tFinDayTool
								.getTotalValue(ttFinCheckKey, 4)); // 300万以上
					} else {
						continue;
					}
				}

				if ((strArr[1] == null) && (strArr[2] == null)
						&& (strArr[3] == null) && (strArr[4] == null)
						&& (strArr[5] == null)) {
					strArr[0] = Flag;
					strArr[1] = "0"; // 总承保件数
					strArr[2] = "0"; // 50-100万
					strArr[3] = "0"; // 100-200万
					strArr[4] = "0"; // 200-300万
					strArr[5] = "0"; // 300万以上
				}

				strArr[6] = new DecimalFormat("0").format(ReportPubFun
						.functionDouble(strArr[2])
						+ ReportPubFun.functionDouble(strArr[3])
						+ ReportPubFun.functionDouble(strArr[4])
						+ ReportPubFun.functionDouble(strArr[5]));
				strArr[7] = ReportPubFun.functionDivision(strArr[6], strArr[1],
						"0.0000");
				tlistTable.add(strArr);
				zcb = zcb + ReportPubFun.functionDouble(strArr[1]);
				first = first + ReportPubFun.functionDouble(strArr[2]);
				Second = Second + ReportPubFun.functionDouble(strArr[3]);
				third = third + ReportPubFun.functionDouble(strArr[4]);
				forth = forth + ReportPubFun.functionDouble(strArr[5]);
				sum = sum + ReportPubFun.functionDouble(strArr[6]);
			}

			strArr = new String[8];
			strArr[0] = "合计";
			strArr[1] = new DecimalFormat("0").format(zcb);
			strArr[2] = new DecimalFormat("0").format(first);
			strArr[3] = new DecimalFormat("0").format(Second);
			strArr[4] = new DecimalFormat("0").format(third);
			strArr[5] = new DecimalFormat("0").format(forth);
			strArr[6] = new DecimalFormat("0").format(sum);
			strArr[7] = ReportPubFun.functionDivision(sum, zcb, "0.0000");
			tlistTable.add(strArr);
			strArr = new String[8];
			strArr[0] = "Flag";
			strArr[1] = "zcb_js";
			strArr[2] = "first_js";
			strArr[3] = "Second_js";
			strArr[4] = "third_js";
			strArr[5] = "fort";
			strArr[6] = "sum_js";
			strArr[7] = "avg_js";
			xmlexport.addListTable(tlistTable, strArr);
		}

		// ************************************高保额件明细清单******************************************************************
		if (mDefineCode.equals("uw6")) {
			xmlexport.createDocument("UWHighAmntCheck.vts", "printer");

			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			LMRiskAppSet tLMRiskAppSet = tLMRiskAppDB.query();
			LDOccupationDB tLDOccupationDB = new LDOccupationDB();
			LDOccupationSet tLDOccupationSet = tLDOccupationDB.query();
			String Back_sql = "select lcpol.InsuredNo,lcpol.InsuredName,lcpol.InsuredAppAge,lcpol.InsuredSex,lcpol.riskcode"
					+ ",lcpol.Amnt,lcpol.CValiDate,lcpol.PayYears,lcpol.Remark,lcpol.Polno from lcpol"
					+ " where lcpol.appflag='1' and lcpol.amnt>='500000' "
					+ " and lcpol.grppolno='00000000000000000000' and lcpol.renewcount=0 and lcpol.salechnl in ('02','03')"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePart("lcpol.SignDate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"));
			Back_sql = Back_sql + " UNION ALL "
					+ StrTool.replace(Back_sql, "lcpol", "lbpol");
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(Back_sql);
			sqlbv14.put("mngcom", mManageCom);
			sqlbv14.put("mday0", mDay[0]);
			sqlbv14.put("mday1", mDay[1]);
			ExeSQL Back_exesql = new ExeSQL();
			SSRS Back_ssrs = Back_exesql.execSQL(sqlbv14);
			logger.debug("按照被保险人号的相关信息的条数是" + Back_ssrs.getMaxRow());

			if (Back_ssrs.getMaxRow() == 0) {
				CError.buildErr(this, "查询的结果是0");
			}

			tlistTable.setName("uw6");

			for (int j = 1; j <= Back_ssrs.getMaxRow(); j++) {
				strArr = new String[10];
				strArr[0] = String.valueOf(j);
				strArr[1] = Back_ssrs.GetText(j, 2); // 投保人
				strArr[2] = Back_ssrs.GetText(j, 3); // 投保人年龄
				strArr[3] = ReportPubFun.getSexName(Back_ssrs.GetText(j, 4)); // 被保险人性别
				strArr[4] = ReportPubFun.getOccupationName(ReportPubFun
						.getLdPersonInfo(Back_ssrs.GetText(j, 1), "0")
						.getOccupationCode(), tLDOccupationSet); // 职业
				strArr[5] = ReportPubFun.getRiskName(Back_ssrs.GetText(j, 5),
						tLMRiskAppSet); // 险种
				strArr[6] = String.valueOf(Double.parseDouble(Back_ssrs
						.GetText(j, 6)) / 10000); // 保费
				strArr[7] = Back_ssrs.GetText(j, 7); // 生效日期
				strArr[8] = Back_ssrs.GetText(j, 8); // 交费年期
				strArr[9] = Back_ssrs.GetText(j, 9); // 备注
				tlistTable.add(strArr);
			}

			strArr = new String[10];
			strArr[0] = "No";
			strArr[1] = "InsuredName";
			strArr[2] = "InsuredAPPage";
			strArr[3] = "InsuredSex";
			strArr[4] = "Occupation";
			strArr[5] = "RiskName";
			strArr[6] = "Amnt";
			strArr[7] = "CValiDate";
			strArr[8] = "PayYears";
			strArr[9] = "Remark";
			xmlexport.addListTable(tlistTable, strArr);
		}

		// ************************************承保工作效率统计表******************************************************************
		if (mDefineCode.equals("uw7")) {
			xmlexport.createDocument("UWEfficiency.vts", "printer");

			// 在遍历前先查询得到set或SSRS
			ExeSQL ES_DOCeSQL = new ExeSQL();
			String ScanSQL = "select managecom from ES_DOC_MAIN where 1=1 "
					+ ReportPubFun.getWherePart("Input_Date", ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"),
							1);
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(ScanSQL);
			sqlbv15.put("mday0", mDay[0]);
			sqlbv15.put("mday1", mDay[1]);
			SSRS ES_DOCRS = ES_DOCeSQL.execSQL(sqlbv15);
			ExeSQL xdExeSQL = new ExeSQL();
			String New_sql = "select lcpol.managecom from lcpol where lcpol.appflag='1'"
					+ " and lcpol.grppolno='00000000000000000000' and lcpol.renewcount=0  and lcpol.salechnl in ('02','03')"
					+ ReportPubFun.getWherePart("lcpol.signdate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);
			New_sql = New_sql + " UNION ALL "
					+ StrTool.replace(New_sql, "lcpol", "lbpol");
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(New_sql);
			sqlbv16.put("mday0", mDay[0]);
			sqlbv16.put("mday1", mDay[1]);
			SSRS XDSSRS = xdExeSQL.execSQL(sqlbv16);
			ExeSQL BJExeSQL = new ExeSQL();
			String BackJ_sql = "select lcpol.managecom from lcpol,lcuwmaster"
					+ " where lcpol.proposalno=lcuwmaster.proposalno "
					+ " and lcpol.grppolno='00000000000000000000'  and lcpol.salechnl in ('02','03')"
					+ " and lcuwmaster.passflag='a' and lcpol.appflag='0' and lcpol.renewcount=0 "
					+ ReportPubFun.getWherePart("lcuwmaster.modifydate",
							ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);
			BackJ_sql = BackJ_sql + " UNION ALL "
					+ StrTool.replace(BackJ_sql, "lcpol", "lbpol");
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.sql(BackJ_sql);
			sqlbv17.put("mday0", mDay[0]);
			sqlbv17.put("mday1", mDay[1]);
			SSRS BJSSRS = BJExeSQL.execSQL(sqlbv17);
			ExeSQL ztExeSQL = new ExeSQL();
			String ZCCDCountSQL = "select lcpol.managecom from lcuwmaster,lcpol,lcprem"
					+ " where lcuwmaster.proposalno=lcpol.proposalno and lcprem.polno=lcpol.polno"
					+ " and lcuwmaster.healthflag='0'" // 不是体检件，特约，加费，没发核保通知书
					+ " and lcprem.payplantype not in('1' ,'2')"
					+ " and lcprem.payplantype is not null"
					+ " and lcuwmaster.printflag='0' and lcpol.renewcount=0"
					+ " and lcpol.PolNo not in( select PolNo from LcSpec) "
					+ " and lcpol.grppolno='00000000000000000000'  and lcpol.salechnl in ('02','03')";
			ZCCDCountSQL = ZCCDCountSQL + " UNION ALL "
					+ StrTool.replace(ZCCDCountSQL, "lcpol", "lbpol");
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql(ZCCDCountSQL);
			SSRS ztSSRS = ztExeSQL.execSQL(sqlbv18);
			ExeSQL wtExeSQL = new ExeSQL();
			String WTCountSQL = "select lcpol.managecom from lcuwmaster,lcpol,lcprem"
					+ " where lcuwmaster.proposalno=lcpol.proposalno"
					+ " and lcprem.polno=lcpol.polno "
					+ " and lcpol.grppolno='00000000000000000000'  and lcpol.renewcount=0 and lcpol.salechnl in ('02','03')"
					+ " and (lcuwmaster.healthflag!='0'"
					+ " or lcprem.payplantype in('1' ,'2')"
					+ " or lcuwmaster.printflag!='0'"
					+ " or lcpol.PolNo in( select PolNo from LcSpec))";
			WTCountSQL = WTCountSQL + " UNION ALL "
					+ StrTool.replace(WTCountSQL, "lcpol", "lbpol");
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			sqlbv19.sql(WTCountSQL);
			SSRS wtSSRS = wtExeSQL.execSQL(sqlbv19);
			ExeSQL CDExeSQL = new ExeSQL();
			String CDTimeSQL = "select sum(lcpol.signdate-ES_DOC_MAIN.INPUT_DATE)*"
					+ timeFactor
					+ " ,ES_DOC_MAIN.managecom  from  ES_DOC_MAIN,lcpol"
					+ " where trim(lcpol.PrtNo)=ES_DOC_MAIN.doc_code"
					+ " and lcpol.grppolno='00000000000000000000'  and lcpol.renewcount=0 and lcpol.salechnl in ('02','03')"
					+ ReportPubFun.getWherePart("ES_DOC_MAIN.Input_Date",
							ReportPubFun.getParameterStr(mDay[0],"?mday0?"), "", 1)
					+ ReportPubFun.getWherePart("lcpol.SignDate", "", ReportPubFun.getParameterStr(mDay[1],"?mday1?"),
							1) + " GROUP BY ES_DOC_MAIN.managecom";
			CDTimeSQL = CDTimeSQL + " UNION ALL "
					+ StrTool.replace(CDTimeSQL, "lcpol", "lbpol");
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.sql(CDTimeSQL);
			sqlbv20.put("mday0", mDay[0]);
			sqlbv20.put("mday1", mDay[1]);
			SSRS CDSSRS = CDExeSQL.execSQL(sqlbv20);
			ExeSQL SDExeSQL = new ExeSQL();
			String SDTimeSQL = "select sum(lcpol.CustomGetPolDate-lcpol.signdate)*"
					+ timeFactor
					+ " ,managecom from lcpol"
					+ " where lcpol.AppFlag='1' and lcpol.CustomGetPolDate is not null"
					+ " and lcpol.grppolno='00000000000000000000' and lcpol.renewcount=0 and lcpol.salechnl in ('02','03')"
					+ ReportPubFun.getWherePart("lcpol.signdate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"), "",
							1)
					+ ReportPubFun.getWherePart("lcpol.CustomGetPolDate", "",
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1) + " GROUP BY lcpol.managecom";
			SDTimeSQL = SDTimeSQL + " UNION ALL "
					+ StrTool.replace(SDTimeSQL, "lcpol", "lbpol");
			logger.debug(SDTimeSQL);
			SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
			sqlbv21.sql(SDTimeSQL);
			sqlbv21.put("mday0", mDay[0]);
			sqlbv21.put("mday1", mDay[1]);
			SSRS SDSSRS = SDExeSQL.execSQL(sqlbv21);

			// sql:开始遍历
			String UWJ_sql = "select comCode from ldcom where char_length(trim(comCode))=4";

			if (mManageCom.length() > 4) {
				mManageCom = mManageCom.substring(0, 4);
			}

			UWJ_sql = UWJ_sql
					+ ReportPubFun.getWherePartLike("comCode", ReportPubFun.getParameterStr(mManageCom, "?mngcom?"));
			logger.debug("获得唯一的4位管理机构的sql语句是:" + UWJ_sql);
			SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
			sqlbv22.sql(UWJ_sql);
			sqlbv22.put("mngcom", mManageCom);
			ExeSQL BackExeSQL = new ExeSQL();
			SSRS BackSSRS = new SSRS();
			BackSSRS = BackExeSQL.execSQL(sqlbv22);

			if (BackSSRS.getMaxRow() == 0) {
				CError.buildErr(this, "查询的结果是0");
			}

			for (int i = 1; i <= BackSSRS.MaxRow; i++) {
				String[] tRow = new String[1];
				String[] tColumn = new String[1];
				tRow[0] = BackSSRS.GetText(i, 1); // 机构为查询行线索

				String[] mRow = new String[11];
				String[] mColumn = new String[1];
				mRow[0] = ReportPubFun.getCount(ES_DOCRS, BackSSRS
						.GetText(i, 1)); // 要约（扫描）件数
				mRow[1] = ReportPubFun.getCount(XDSSRS, BackSSRS.GetText(i, 1)); // 出单（签单）件数
				mRow[2] = ReportPubFun.getCount(BJSSRS, BackSSRS.GetText(i, 1)); // 撤单时间件数
				mRow[7] = ReportPubFun.getCount(ztSSRS, BackSSRS.GetText(i, 1)); // 正常件
																					// 公用
				mRow[8] = ReportPubFun.getCount(wtSSRS, BackSSRS.GetText(i, 1)); // 问题件
																					// 函数
				mRow[9] = ReportPubFun.getTime(CDSSRS, BackSSRS.GetText(i, 1)); // 出单时间
																				// 公用
				mRow[10] = ReportPubFun.getTime(SDSSRS, BackSSRS.GetText(i, 1)); // 送达时间
																					// 函数
				mRow[3] = ReportPubFun
						.functionDivision(mRow[9], mRow[7], "0.0"); // 平均正常出单时间
				mRow[4] = ReportPubFun
						.functionDivision(mRow[9], mRow[8], "0.0"); // 平均问题出单时间
				mRow[5] = ReportPubFun.functionDivision(mRow[10], mRow[7],
						"0.0"); // 平均正常送达时间
				mRow[6] = ReportPubFun.functionDivision(mRow[10], mRow[8],
						"0.0"); // 平均问题送达时间

				FinCheckKey tF = new FinCheckKey(tRow, tColumn);
				FinCheckKey mF = new FinCheckKey(mRow, mColumn);
				tFinDayTool.enterBasicInfo(tF, mF);
			}

			tlistTable.setName("uw7");

			Vector tv = tFinDayTool.getAllRowKey(0);
			Enumeration e = tv.elements();
			double Scan_js = 0;
			double Sign_js = 0;
			double Back_js = 0;
			double ZC_js = 0;
			double WT_js = 0;
			double CD_time = 0;
			double SD_time = 0;

			while (e.hasMoreElements()) {
				String tMngment = (String) e.nextElement();
				String[] tttRow = new String[1];
				String[] tttColumn = new String[1];
				tttRow[0] = tMngment; // 管理机构

				FinCheckKey tttFinCheckKey = new FinCheckKey(tttRow, tttColumn);
				strArr = new String[12];
				strArr[0] = tMngment; // 管理机构
				strArr[1] = tFinDayTool.getUniqueValue(tttFinCheckKey, 0); // 扫描件
				strArr[2] = tFinDayTool.getUniqueValue(tttFinCheckKey, 1); // 签单件
				strArr[3] = tFinDayTool.getUniqueValue(tttFinCheckKey, 2); // 撤单件
				strArr[4] = tFinDayTool.getUniqueValue(tttFinCheckKey, 3); // 平均正常出单时间
				strArr[5] = tFinDayTool.getUniqueValue(tttFinCheckKey, 4); // 平均问题出单时间
				strArr[6] = tFinDayTool.getUniqueValue(tttFinCheckKey, 5); // 平均正常送达时间
				strArr[7] = tFinDayTool.getUniqueValue(tttFinCheckKey, 6); // 平均问题送达时间
				strArr[8] = tFinDayTool.getUniqueValue(tttFinCheckKey, 7);
				strArr[9] = tFinDayTool.getUniqueValue(tttFinCheckKey, 8);
				strArr[10] = tFinDayTool.getUniqueValue(tttFinCheckKey, 9);
				strArr[11] = tFinDayTool.getUniqueValue(tttFinCheckKey, 10);

				tlistTable.add(strArr);

				if (strArr[1].equals("") || strArr[2].equals("")
						|| strArr[3].equals("") || strArr[8].equals("")
						|| strArr[9].equals("") || strArr[10].equals("")
						|| strArr[11].equals("")) {
					continue;
				}

				Scan_js = Scan_js + Double.parseDouble(strArr[1]);
				Sign_js = Sign_js + Double.parseDouble(strArr[2]);
				Back_js = Back_js + Double.parseDouble(strArr[3]);
				ZC_js = ZC_js + Double.parseDouble(strArr[8]);
				WT_js = WT_js + Double.parseDouble(strArr[9]);
				CD_time = CD_time + Double.parseDouble(strArr[10]);
				SD_time = SD_time + Double.parseDouble(strArr[11]);
			}

			strArr = new String[8];
			strArr[0] = "合计/平均";
			strArr[1] = new DecimalFormat("0.0").format(Scan_js);
			strArr[2] = new DecimalFormat("0.0").format(Sign_js);
			strArr[3] = new DecimalFormat("0.0").format(Back_js);
			strArr[4] = ReportPubFun.functionDivision(CD_time, ZC_js, "0.0");
			strArr[5] = ReportPubFun.functionDivision(CD_time, WT_js, "0.0");
			strArr[6] = ReportPubFun.functionDivision(SD_time, ZC_js, "0.0");
			strArr[7] = ReportPubFun.functionDivision(SD_time, WT_js, "0.0");
			tlistTable.add(strArr);
			strArr = new String[8];
			strArr[0] = "Mngment";
			strArr[1] = "Scan";
			strArr[2] = "Sign";
			strArr[3] = "Back";
			strArr[4] = "CDZC";
			strArr[5] = "CDWT";
			strArr[6] = "SDZC";
			strArr[7] = "SDWT";
			xmlexport.addListTable(tlistTable, strArr);
		}

		if (mDefineCode.equals("uw8")) {
			xmlexport.createDocument("UWNew.vts", "printer");

			ExeSQL ES_SQL = new ExeSQL();
			String Scan_SQL = "select lcpol.InsuredAppAge from"
					+ " ES_DOC_MAIN,lcpol where trim(lcpol.PrtNo)=ES_DOC_MAIN.doc_code "
					+ " and lcpol.grppolno='00000000000000000000'  and lcpol.renewcount=0 and lcpol.salechnl in ('02','03')"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("ES_DOC_MAIN.Input_Date",
							ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);
			Scan_SQL = Scan_SQL + " UNION ALL "
					+ StrTool.replace(Scan_SQL, "lcpol", "lbpol");
			SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
			sqlbv23.sql(Scan_SQL);
			sqlbv23.put("mngcom", mManageCom);
			sqlbv23.put("mday0", mDay[0]);
			sqlbv23.put("mday1", mDay[1]);
			SSRS ES_DOCSSRS = ES_SQL.execSQL(sqlbv23);

			logger.debug("-------------要约件数-----------------------------"
					+ Scan_SQL);

			// 趸交，期交，合计 start
			String selJS = "select InsuredAppAge";
			String selBF = "select insuredappage,prem";
			String conDJ = " and PayIntv<=0";
			String conQJ = " and PayIntv>0";
			String otherSql = " from lcpol  WHERE lcpol.salechnl in ('02','03')  and lcpol.renewcount=0 and lcpol.grppolno='00000000000000000000'"
					+ " and lcpol.appflag='1'  "
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("lcpol.signdate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);
			ExeSQL DJJSSQL = new ExeSQL();
			String DJJSSql = selJS + otherSql + conDJ;
			DJJSSql = DJJSSql + " UNION ALL "
					+ StrTool.replace(DJJSSql, "lcpol", "lbpol");
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			sqlbv24.sql(DJJSSql);
			sqlbv24.put("mngcom", mManageCom);
			sqlbv24.put("mday0", mDay[0]);
			sqlbv24.put("mday1", mDay[1]);
			SSRS DJJSSSRS = DJJSSQL.execSQL(sqlbv24);
			logger.debug("-------------趸交件数-----------------------------"
					+ DJJSSql);

			ExeSQL DJBFSQL = new ExeSQL();
			String DJBFSql = selBF + otherSql + conDJ;
			DJBFSql = DJBFSql + " UNION ALL "
					+ StrTool.replace(DJBFSql, "lcpol", "lbpol");
			SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
			sqlbv25.sql(DJBFSql);
			sqlbv25.put("mngcom", mManageCom);
			sqlbv25.put("mday0", mDay[0]);
			sqlbv25.put("mday1", mDay[1]);
			SSRS DJBFSSRS = DJBFSQL.execSQL(sqlbv25);
			logger.debug("-------------趸交保费-----------------------------"
					+ DJBFSql);

			ExeSQL QJJSSQL = new ExeSQL();
			String QJJSSql = selJS + otherSql + conQJ;
			QJJSSql = QJJSSql + " UNION ALL "
					+ StrTool.replace(QJJSSql, "lcpol", "lbpol");
			SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
			sqlbv26.sql(QJJSSql);
			sqlbv26.put("mngcom", mManageCom);
			sqlbv26.put("mday0", mDay[0]);
			sqlbv26.put("mday1", mDay[1]);
			SSRS QJJSSSRS = QJJSSQL.execSQL(sqlbv26);
			logger.debug("-------------期交件数-----------------------------"
					+ QJJSSql);

			ExeSQL QJBFSQL = new ExeSQL();
			String QJBFSql = selBF + otherSql + conQJ;
			QJBFSql = QJBFSql + " UNION ALL "
					+ StrTool.replace(QJBFSql, "lcpol", "lbpol");
			SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
			sqlbv27.sql(QJBFSql);
			sqlbv27.put("mngcom", mManageCom);
			sqlbv27.put("mday0", mDay[0]);
			sqlbv27.put("mday1", mDay[1]);
			SSRS QJBFSSRS = QJBFSQL.execSQL(sqlbv27);
			logger.debug("-------------期交保费-----------------------------"
					+ QJBFSql);

			ExeSQL HJJSSQL = new ExeSQL();
			String HJJSSql = selJS + otherSql;
			HJJSSql = HJJSSql + " UNION ALL "
					+ StrTool.replace(HJJSSql, "lcpol", "lbpol");
			SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
			sqlbv28.sql(HJJSSql);
			sqlbv28.put("mngcom", mManageCom);
			sqlbv28.put("mday0", mDay[0]);
			sqlbv28.put("mday1", mDay[1]);
			SSRS HJJSSSRS = HJJSSQL.execSQL(sqlbv28);
			logger.debug("-------------合计件数-----------------------------"
					+ HJJSSql);

			ExeSQL HJBFSQL = new ExeSQL();
			String HJBFSql = selBF + otherSql;
			HJBFSql = HJBFSql + " UNION ALL "
					+ StrTool.replace(HJBFSql, "lcpol", "lbpol");
			SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
			sqlbv29.sql(HJBFSql);
			sqlbv29.put("mngcom", mManageCom);
			sqlbv29.put("mday0", mDay[0]);
			sqlbv29.put("mday1", mDay[1]);
			SSRS HJBFSSRS = HJBFSQL.execSQL(sqlbv29);
			logger.debug("-------------合计保费-----------------------------"
					+ HJBFSql);

			// 趸交，期交，合计 END
			ExeSQL specExeSQL = new ExeSQL();
			String Spec_sql = "select lcpol.InsuredAppAge from lcpol"
					+ " WHERE lcpol.salechnl in ('02','03')  and lcpol.renewcount=0 and lcpol.grppolno='00000000000000000000'"
					+ " and lcpol.appflag='1'  and lcpol.uwflag in ('9')"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("lcpol.signdate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);
			Spec_sql = Spec_sql + " UNION ALL "
					+ StrTool.replace(Spec_sql, "lcpol", "lbpol");
			logger.debug("-------------标准体-----------------------------"
					+ Spec_sql);
			SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
			sqlbv30.sql(Spec_sql);
			sqlbv30.put("mngcom", mManageCom);
			sqlbv30.put("mday0", mDay[0]);
			sqlbv30.put("mday1", mDay[1]);
			SSRS specSSRS = specExeSQL.execSQL(sqlbv30);

			ExeSQL specsecondExeSQL = new ExeSQL();
			String specsecond_sql = "select lcpol.InsuredAppAge from lcpol"
					+ " WHERE lcpol.salechnl in ('02','03')  and lcpol.renewcount=0 and lcpol.grppolno='00000000000000000000'"
					+ " and lcpol.appflag='1'  and lcpol.uwflag in ('4')"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("lcpol.signdate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);
			specsecond_sql = specsecond_sql + " UNION ALL "
					+ StrTool.replace(specsecond_sql, "lcpol", "lbpol");
			logger.debug("-------------次标准体-----------------------------"
					+ specsecond_sql);
			SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
			sqlbv31.sql(specsecond_sql);
			sqlbv31.put("mngcom", mManageCom);
			sqlbv31.put("mday0", mDay[0]);
			sqlbv31.put("mday1", mDay[1]);
			SSRS specsecondSSRS = specExeSQL.execSQL(sqlbv31);

			ExeSQL tyExeSQL = new ExeSQL();
			String ty_sql = "select lcpol.InsuredAppAge from lcpol"
					+ " WHERE lcpol.salechnl in ('02','03')  and lcpol.renewcount=0 and lcpol.grppolno='00000000000000000000'"
					+ " and lcpol.appflag='1'  "
					+ " and polno in( select distinct polno from lcspec where speccontent is null or trim(speccontent)='' )"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("lcpol.signdate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);
			ty_sql = ty_sql + " UNION ALL "
					+ StrTool.replace(ty_sql, "lcpol", "lbpol");
			logger.debug("----------------特约承保----------------------------"
							+ ty_sql);
			SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
			sqlbv32.sql(ty_sql);
			sqlbv32.put("mngcom", mManageCom);
			sqlbv32.put("mday0", mDay[0]);
			sqlbv32.put("mday1", mDay[1]);
			SSRS tySSRS = tyExeSQL.execSQL(sqlbv32);
			ExeSQL conExeSQL = new ExeSQL();
			String con_sql = "select lcpol.InsuredAppAge from lcpol,lcprem where lcprem.polno=lcpol.polno"
					+ " and lcpol.salechnl in ('02','03')  and lcpol.renewcount=0 and lcpol.grppolno='00000000000000000000' "
					+ " and lcpol.appflag='1' and substr(trim(lcprem.payplancode),1,6)='000000' "
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("lcpol.signdate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);
			con_sql = con_sql + " UNION ALL "
					+ StrTool.replace(con_sql, "lcpol", "lbpol");
			logger.debug("--------------------加费承保---------------------"
					+ con_sql);
			SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
			sqlbv33.sql(con_sql);
			sqlbv33.put("mngcom", mManageCom);
			sqlbv33.put("mday0", mDay[0]);
			sqlbv33.put("mday1", mDay[1]);
			SSRS conSSRS = conExeSQL.execSQL(sqlbv33);
			ExeSQL chgExeSQL = new ExeSQL();
			String chg_sql = "select lcpol.InsuredAppAge from lcpol,lcuwmaster where  lcuwmaster.polno=lcpol.polno"
					+ " and lcpol.salechnl in ('02','03') and lcpol.renewcount=0 and lcpol.grppolno='00000000000000000000' "
					+ " and lcpol.appflag='1' and lcuwmaster.changepolflag<>'0'"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("lcpol.signdate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);
			chg_sql = chg_sql + " UNION ALL "
					+ StrTool.replace(chg_sql, "lcpol", "lbpol");
			logger.debug("----------------------变更承保---------------"
					+ chg_sql);
			SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
			sqlbv34.sql(chg_sql);
			sqlbv34.put("mngcom", mManageCom);
			sqlbv34.put("mday0", mDay[0]);
			sqlbv34.put("mday1", mDay[1]);
			SSRS chgSSRS = chgExeSQL.execSQL(sqlbv34);
			ExeSQL APassExeSQL = new ExeSQL();
			String ANoPass_sql = "select lcpol.InsuredAppAge from lcuwmaster,lcpol"
					+ " WHERE lcuwmaster.ProposalNo=lcpol.ProposalNo and lcpol.grppolno='00000000000000000000'"
					+ " and passflag='2'  "
					+ " and lcpol.appflag='0' and lcpol.renewcount=0 and lcpol.salechnl in ('02','03')"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("lcuwmaster.modifydate",
							ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);
			ANoPass_sql = ANoPass_sql + " UNION ALL "
					+ StrTool.replace(ANoPass_sql, "lcpol", "lbpol");
			logger.debug("－－－－－－－－延期件数sql－－－－－－－－:" + ANoPass_sql);
			SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
			sqlbv35.sql(ANoPass_sql);
			sqlbv35.put("mngcom", mManageCom);
			sqlbv35.put("mday0", mDay[0]);
			sqlbv35.put("mday1", mDay[1]);
			SSRS ANoPassSSRS = APassExeSQL.execSQL(sqlbv35);
			ExeSQL NoPassExeSQL = new ExeSQL();
			String NoPass_sql = "select lcpol.InsuredAppAge from lcuwmaster,lcpol"
					+ " WHERE lcuwmaster.ProposalNo=lcpol.ProposalNo and lcpol.grppolno='00000000000000000000'"
					+ " and passflag='1'  "
					+ " and lcpol.appflag='0'  and lcpol.renewcount=0 and lcpol.salechnl in ('02','03')"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("lcuwmaster.modifydate",
							ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);
			NoPass_sql = NoPass_sql + " UNION ALL "
					+ StrTool.replace(NoPass_sql, "lcpol", "lbpol");
			logger.debug("－－－－－－－－－－拒保件数sql－－－－－－－－－－－" + NoPass_sql);
			SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
			sqlbv36.sql(NoPass_sql);
			sqlbv36.put("mngcom", mManageCom);
			sqlbv36.put("mday0", mDay[0]);
			sqlbv36.put("mday1", mDay[1]);
			SSRS NoPassSSRS = NoPassExeSQL.execSQL(sqlbv36);
			ExeSQL BackExeSQL = new ExeSQL();
			String Back_sql = "select lcpol.InsuredAppAge from lcuwmaster,lcpol"
					+ " WHERE lcuwmaster.ProposalNo=lcpol.ProposalNo and lcpol.grppolno='00000000000000000000'"
					+ " and passflag='a' "
					+ " and lcpol.appflag='0'  and lcpol.renewcount=0 and lcpol.salechnl in ('02','03')"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("lcuwmaster.modifydate",
							ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);
			Back_sql = Back_sql + " UNION ALL "
					+ StrTool.replace(Back_sql, "lcpol", "lbpol");
			logger.debug("－－－－－－－－－－撤单件数sql－－－－－－－－－－－－－" + Back_sql);
			SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
			sqlbv37.sql(Back_sql);
			sqlbv37.put("mngcom", mManageCom);
			sqlbv37.put("mday0", mDay[0]);
			sqlbv37.put("mday1", mDay[1]);
			SSRS BackSSRS = BackExeSQL.execSQL(sqlbv37);
			int snd = 0;
			int sdjjs = 0;
			double sdjbf = 0;
			int sqjjs = 0;
			double sqjbf = 0;
			int shjjs = 0;
			double shjbf = 0;
			int sbzj = 0;
			int scbzj = 0;
			int stjj = 0;
			int sjfj = 0;
			int sbgj = 0;
			int syqj = 0;
			int sjbj = 0;
			int scdj = 0;
			int shj = 0;
			tlistTable.setName("uw8");

			for (int i = 0; i < mFlag.length; i++) {
				String Flag = "";

				if (mFlag[i][1].equals("以上")) {
					Flag = mFlag[i][0] + mFlag[i][1];
				} else {
					Flag = mFlag[i][0] + "-" + mFlag[i][1];
				}

				int nd = 0;
				int djjs = 0;
				double djbf = 0;
				int qjjs = 0;
				double qjbf = 0;
				int hjjs = 0;
				double hjbf = 0;
				int bzj = 0;
				int cbzj = 0;
				int tjj = 0;
				int jfj = 0;
				int bgj = 0;
				int yqj = 0;
				int jbj = 0;
				int cdj = 0;

				for (int j = 1; j <= ES_DOCSSRS.MaxRow; j++) {
					nd += ReportPubFun.getCount(ES_DOCSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--要约件数
				}

				for (int j = 1; j <= DJJSSSRS.MaxRow; j++) {
					djjs += ReportPubFun.getCount(DJJSSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--趸交件数
				}

				for (int j = 1; j <= DJBFSSRS.MaxRow; j++) {
					djbf += ReportPubFun.getPrem(DJBFSSRS.GetText(j, 1),
							DJBFSSRS.GetText(j, 2), mFlag[i][0], mFlag[i][1]); // 年龄--趸交保费
				}

				for (int j = 1; j <= QJJSSSRS.MaxRow; j++) {
					qjjs += ReportPubFun.getCount(QJJSSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--期交件数
				}

				for (int j = 1; j <= QJBFSSRS.MaxRow; j++) {
					qjbf += ReportPubFun.getPrem(QJBFSSRS.GetText(j, 1),
							QJBFSSRS.GetText(j, 2), mFlag[i][0], mFlag[i][1]); // 年龄--期交保费
				}

				for (int j = 1; j <= HJJSSSRS.MaxRow; j++) {
					hjjs += ReportPubFun.getCount(HJJSSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--合计件数
				}

				for (int j = 1; j <= HJBFSSRS.MaxRow; j++) {
					hjbf += ReportPubFun.getPrem(HJBFSSRS.GetText(j, 1),
							HJBFSSRS.GetText(j, 2), mFlag[i][0], mFlag[i][1]); // 年龄--合计保费
				}

				for (int j = 1; j <= specSSRS.MaxRow; j++) {
					bzj += ReportPubFun.getCount(specSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--标准件数
				}

				for (int j = 1; j <= specsecondSSRS.MaxRow; j++) {
					cbzj += ReportPubFun.getCount(specsecondSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--次标准件数
				}

				for (int j = 1; j <= tySSRS.MaxRow; j++) {
					tjj += ReportPubFun.getCount(tySSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--特约件数
				}

				for (int j = 1; j <= conSSRS.MaxRow; j++) {
					jfj += ReportPubFun.getCount(conSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄-加费件数
				}

				for (int j = 1; j <= chgSSRS.MaxRow; j++) {
					bgj += ReportPubFun.getCount(chgSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--变更件数
				}

				for (int j = 1; j <= ANoPassSSRS.MaxRow; j++) {
					yqj += ReportPubFun.getCount(ANoPassSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--延期件数
				}

				for (int j = 1; j <= NoPassSSRS.MaxRow; j++) {
					jbj += ReportPubFun.getCount(NoPassSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--拒保件数
				}

				for (int j = 1; j <= BackSSRS.MaxRow; j++) {
					cdj += ReportPubFun.getCount(BackSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 年龄--撤单件数
				}

				strArr = new String[17];
				strArr[0] = Flag;
				strArr[1] = String.valueOf(nd); // 要约件数
				strArr[2] = String.valueOf(djjs); // 趸交件数
				strArr[3] = String.valueOf(djbf); // 趸交保费
				strArr[4] = String.valueOf(qjjs); // 期交件数
				strArr[5] = String.valueOf(qjbf); // 期交保费
				strArr[6] = String.valueOf(hjjs); // 合计件数
				strArr[7] = String.valueOf(hjbf); // 合计保费
				strArr[8] = String.valueOf(bzj); // 标准件数
				strArr[9] = String.valueOf(cbzj); // 次标准件数
				strArr[10] = String.valueOf(tjj); // 特约件数
				strArr[11] = String.valueOf(jfj); // 加费件数
				strArr[12] = String.valueOf(bgj); // 变更件数
				strArr[13] = String.valueOf(jbj); // 拒保件数
				strArr[14] = String.valueOf(yqj); // 延期件数
				strArr[15] = String.valueOf(cdj); // 撤单件数
				strArr[16] = String.valueOf(bzj + cbzj); // 合计
				tlistTable.add(strArr);

				snd += nd;
				sdjjs += djjs;
				sdjbf += djbf;
				sqjjs += qjjs;
				sqjbf += qjbf;
				shjjs += hjjs;
				shjbf += hjbf;
				sbzj += bzj;
				scbzj += cbzj;
				stjj += tjj;
				sjfj += jfj;
				sbgj += bgj;
				syqj += yqj;
				sjbj += jbj;
				scdj += cdj;
				shj += (bzj + cbzj);
			}

			strArr = new String[17];
			strArr[0] = "合计";
			strArr[1] = new DecimalFormat("0").format(snd); // 要约件数
			strArr[2] = new DecimalFormat("0").format(sdjjs); // 趸交件数
			strArr[3] = new DecimalFormat("0.0").format(sdjbf); // 趸交保费

			strArr[4] = new DecimalFormat("0").format(sqjjs); // 期交件数
			strArr[5] = new DecimalFormat("0.0").format(sqjbf); // 期交保费

			strArr[6] = new DecimalFormat("0").format(shjjs); // 合计件数
			strArr[7] = new DecimalFormat("0.0").format(shjbf); // 合计保费

			strArr[8] = new DecimalFormat("0").format(sbzj); // 标准件数
			strArr[9] = new DecimalFormat("0").format(scbzj); // 次标准件数
			strArr[10] = new DecimalFormat("0").format(stjj); // 特约件数
			strArr[11] = new DecimalFormat("0").format(sjfj); // 加费件数
			strArr[12] = new DecimalFormat("0").format(sbgj); // 变更件数
			strArr[13] = new DecimalFormat("0").format(sjbj); // 拒保件数
			strArr[14] = new DecimalFormat("0").format(syqj); // 延期件数
			strArr[15] = new DecimalFormat("0").format(scdj); // 撤单件数
			strArr[16] = new DecimalFormat("0").format(shj); // 合计
			tlistTable.add(strArr);
			strArr = new String[17];
			strArr[0] = "ageGrp";
			strArr[1] = "yy"; // 要约件数
			strArr[2] = "djjs"; // 趸交件数
			strArr[3] = "djbf"; // 趸交保费
			strArr[4] = "qjjs"; // 期交件数
			strArr[5] = "qjbf"; // 期交保费
			strArr[6] = "hjjs"; // 合计件数
			strArr[7] = "hjbf"; // 合计保费
			strArr[8] = "bzjs"; // 标准件数
			strArr[9] = "cbzjs"; // 次标准件数
			strArr[10] = "tyjs"; // 特约件数
			strArr[11] = "jfjs"; // 加费件数
			strArr[12] = "bgjs"; // 变更件数
			strArr[13] = "jbjs"; // 拒保件数
			strArr[14] = "yqjs"; // 延期件数
			strArr[15] = "cdjs"; // 撤单件数
			strArr[16] = "hjjs"; // 合计
			xmlexport.addListTable(tlistTable, strArr);
		}

		if (mDefineCode.equals("uw9")) {
		      String tPolTypeSQL ="";
		      if(mPolType.length()>2){
		        tPolTypeSQL =" and substr(a.prtno,1,4)='"+"?poltype?"+"'";
		      }
		      xmlexport.createDocument("UWQuestion.vts", "printer");

		      String WT_sql = "select a.managecom a,count(prtno), "
		            + " sum(case when (select count(*) from es_doc_main where doccode=a.prtno and subtype='UA001')>0 and selltype<>'08' then 1 else 0 end),"
		            + " sum((case when selltype='08' and appflag in ('1','4') then 1 else 0 end)),"
		            + " sum(case when (select count(*) from es_doc_main where doccode=a.prtno and subtype='UA001')=0 and selltype<>'08' then 1 else 0 end),"
		          + " sum((select count(distinct contno) from lcissuepol  where backobjtype in ('2', '3') and operatepos in ('0', '5') "
		          + " and NeedPrint in ('Y', 'P') and  contno= a.contno "
		          + " and exists (select 1 from ldcodemod where code =issuetype and recordquest='Y'))),"
		          + " sum((select count(1) from lcissuepol  where backobjtype in ('2', '3') and operatepos in ('0', '5')"
		          + " and NeedPrint in ('Y', 'P') and contno = a.contno "
		          + " and exists (select 1 from ldcodemod where code =issuetype and recordquest='Y'))) "
		          + " from lccont a where 1=1 "
		          + ReportPubFun.getWherePart("a.salechnl", ReportPubFun.getParameterStr(mSaleChnl,"?salechnl?"))
		          //+ " and a.renewcount=0"
		          + tPolTypeSQL
		          + " and a.conttype ='1' /*and a.appflag ='1'*/"
		          + " and exists (select 1 from lcpol where contno =a.contno "
		          + ReportPubFun.getWherePartInRiskCode(
		              "riskcode", mRiskCode, mRiskFlag)
		          + " )"
		          + ReportPubFun.getWherePart("a.makedate",
		              ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1)
		          + ReportPubFun.getWherePartLike("a.managecom",
		              ReportPubFun.getParameterStr(mManageCom, "?mngcom?")) 
		          + ReportPubFun.getWherePartLike("a.managecom",
		        		  ReportPubFun.getParameterStr(mGlobalInput.ComCode,"?comcode?"))
		          + " group by a.managecom";

		      // use view replace union all
		      // WT_sql=WT_sql+" UNION ALL
		      // "+StrTool.replace(WT_sql,"lcpol","lbpol");
		      ExeSQL WT_exesql = new ExeSQL();
		      logger.debug("-----问题件sql---" + WT_sql);
		      SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
				sqlbv38.sql(WT_sql);
				sqlbv38.put("poltype", mPolType);
				sqlbv38.put("salechnl", mSaleChnl);
				sqlbv38.put("comcode", mGlobalInput.ComCode);
				sqlbv38.put("mngcom", mManageCom);
				sqlbv38.put("mday0", mDay[0]);
				sqlbv38.put("mday1", mDay[1]);
		      SSRS WT_ssrs = WT_exesql.execSQL(sqlbv38);

		      // logger.debug("按照印刷号查询的相关信息的条数是" + Back_ssrs.getMaxRow());
		      if (WT_ssrs.getMaxRow() == 0) {
		        CError.buildErr(this, "查询的结果是0");
		      }

		      tlistTable.setName("uw9");

		      double ysjs = 0.0;
		      double yyjs = 0.0;
		      double wtjs = 0.0;
		      double wtts = 0.0;
		      double scans = 0.0;
		      double noscans =0.0;

		      for (int j = 1; j <= WT_ssrs.getMaxRow(); j++) {
		        strArr = new String[9];
		        strArr[0] = WT_ssrs.GetText(j, 1); // 管理机构
		        strArr[1] = String.valueOf(Integer.parseInt(WT_ssrs.GetText(j, 3))+Integer.parseInt(WT_ssrs.GetText(j, 4))+Integer.parseInt(WT_ssrs.GetText(j, 5))); // 预收件数
		        strArr[2] = WT_ssrs.GetText(j, 3); //扫描件预收件数
		        strArr[3] = WT_ssrs.GetText(j, 4); // 银、邮保件数
		        strArr[4] = WT_ssrs.GetText(j, 5); //无扫描件预收件数
		        strArr[5] = WT_ssrs.GetText(j, 6); // 问题件数
		        strArr[6] = WT_ssrs.GetText(j, 7); // 问题条数
		        strArr[7] = ReportPubFun.functionDivision(strArr[6], strArr[5],
		            "0.0"); // 件均问题数
		        strArr[8] = String.valueOf(Double.parseDouble(ReportPubFun.functionDivision(strArr[5], strArr[1],
		            "0.0"))*100)+"%"; // 问题件占比
		         tlistTable.add(strArr);
		        ysjs = ysjs + ReportPubFun.functionDouble(strArr[1]);
		        scans = scans + ReportPubFun.functionDouble(strArr[2]);
		        yyjs = yyjs + ReportPubFun.functionDouble(strArr[3]);
		        noscans = noscans + ReportPubFun.functionDouble(strArr[4]);
		        wtjs = wtjs + ReportPubFun.functionDouble(strArr[5]);
		        wtts = wtts + ReportPubFun.functionDouble(strArr[6]);
		      }

		      strArr = new String[9];
		      strArr[0] = "合计";
		      strArr[1] = new DecimalFormat("0").format(ysjs);
		      strArr[2] = new DecimalFormat("0").format(scans);
		      strArr[3] = new DecimalFormat("0").format(yyjs);
		      strArr[4] = new DecimalFormat("0").format(noscans);
		      strArr[5] = new DecimalFormat("0").format(wtjs);
		      strArr[6] = new DecimalFormat("0").format(wtts);
		      strArr[7] = ReportPubFun.functionDivision(strArr[6], strArr[5],
		          "0.0");
		      strArr[8] = String.valueOf(Double.parseDouble(ReportPubFun.functionDivision(strArr[5], strArr[1],
		          "0.0"))*100)+"%";
		      tlistTable.add(strArr);
		      strArr = new String[9];
		      strArr[0] = "mng";
		      strArr[1] = "ys_js";
		      strArr[2] = "scans";
		      strArr[3] = "yy_js";
		      strArr[4] = "noscans";
		      strArr[5] = "wt_js";
		      strArr[6] = "wt_ts";
		      strArr[7] = "jj_ts";
		      strArr[8] = "wt_zb";
		      xmlexport.addListTable(tlistTable, strArr);
		    }

		// ***********************
		// 核保师工作量统计***************************************************************************************************
		if (mDefineCode.equals("uw10") || mDefineCode.equals("uw12")) {
			xmlexport.createDocument("MSUWWorker.vts", "printer");

			ExeSQL hbExeSQL = new ExeSQL();
			/*// 核保通知书打印,非打印,业务员通知书,返回问题件修改岗
			// 生调通知书
			String HB_sql = " select LOPRTManager.ReqOperator from LOPRTManager,lccont"
					+ " WHERE LOPRTManager.otherno=lccont.contNo  "
					+ " and lccont.conttype='1' "
					+ " and LOPRTManager.code in('TB89','TB90','14','04','03') " // 核保通知书
					+ " and (LOPRTManager.PatchFlag!=1 or LOPRTManager.PatchFlag is  null)" // 过滤补打件数
			;

			// tongmeng 2007-12-24 modify
			if (mRiskCode != null & !mRiskCode.equals("")) {
				HB_sql = HB_sql
						+ " and exists(select '1' from lcpol where riskcode='"
						+ mRiskCode
						+ "' and proposalno=LCUWMaster.proposalno) ";
			} else if (this.mRiskFlag.equals("on")) {
				HB_sql = HB_sql
						+ " and exists(select '1' from lcpol where riskcode in (select riskcode from lmriskapp where subRiskFlag='M') and contno=lccont.contno) ";
			}

			HB_sql = HB_sql
					+ ReportPubFun.getWherePartLike("lccont.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("LOPRTManager.MakeDate",
							mDay[0], mDay[1], 1); // 发放入机日期
			HB_sql = HB_sql
					+ " union all"
					+ " select operator from lcissuepol,lccont where backobjtype in ('1','4') and state is not null and lccont.conttype='1'";
			// tongmeng 2007-12-24 modify
			if (mRiskCode != null & !mRiskCode.equals("")) {
				HB_sql = HB_sql
						+ " and exists(select '1' from lcpol b where b.contno=lccont.contno and b.riskcode='"
						+ mRiskCode
						+ "' ) ";
			} else if (this.mRiskFlag.equals("on")) {
				HB_sql = HB_sql
						+ " and exists(select '1' from lcpol b where b.contno=lccont.contno and b.riskcode in (select riskcode from lmriskapp where subRiskFlag='M') ) ";
			}
			HB_sql = HB_sql
					+ ReportPubFun.getWherePartLike("lccont.managecom",
							mManageCom)
					+ ReportPubFun.getWherePart("lcissuepol.ModifyDate",
							mDay[0], mDay[1], 1); // 发放入机日期
*/
			//发放通知书次数
			String HB_sql = " select a.Operator from LCCUWSub a,lccont b "
				+ " WHERE a.contno=b.contno  "
				+ " and b.conttype='1' "
				+ " and a.passflag='8' and a.printflag='1' " // 核保通知书
			;
			if (mRiskCode != null && !mRiskCode.equals("")) {
				HB_sql = HB_sql
						+ " and exists(select '1' from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and contno=b.contno) ";
			} else if (this.mRiskFlag.equals("on")) {
				HB_sql = HB_sql
						+ " and exists(select '1' from lcpol where riskcode in (select riskcode from lmriskapp where subRiskFlag='M') and contno=b.contno) ";
			}
			if (mSaleChnl != null && !mSaleChnl.equals("")) {
				HB_sql = HB_sql
						+ " and b.salechnl='" + "?salechnl?" + "' ";
			}
			if (mPolType != null && !mPolType.equals("") && !"86".equals(mPolType)) {
				HB_sql = HB_sql
						+ " and b.prtno like concat('" + "?poltype?" + "','%%') ";
			}
	
			HB_sql = HB_sql
					+ ReportPubFun.getWherePartLike("b.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("a.MakeDate",
							ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1); // 发放入机日期
			logger.debug("--------(发放的)核保通知书件次---－－－－" + HB_sql);
			SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
			sqlbv39.sql(HB_sql);
			sqlbv39.put("riskcode", mRiskCode);
			sqlbv39.put("poltype", mPolType);
			sqlbv39.put("salechnl", mSaleChnl);
			sqlbv39.put("mngcom", mManageCom);
			sqlbv39.put("mday0", mDay[0]);
			sqlbv39.put("mday1", mDay[1]);
			SSRS HBSSRS = hbExeSQL.execSQL(sqlbv39);

			ExeSQL zbExeSQL = new ExeSQL();
			String ZB_sql = "select a.createoperator from lbmission a,lccont b "
//					+ " WHERE b.conttype='1' and activityid='0000001134'"
					+ " WHERE b.conttype='1' and activityid in (select activityid from lwactivity  where functionid ='10010039')"
					+ " and a.missionprop2=b.contno "
					+ " and a.outdate is not null";
					
			if (mRiskCode != null && !mRiskCode.equals("")) {
				ZB_sql = ZB_sql
						+ " and exists(select '1' from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and contno=b.contno) ";
			} else if (this.mRiskFlag.equals("on")) {
				ZB_sql = ZB_sql
						+ " and exists(select '1' from lcpol where riskcode in (select riskcode from lmriskapp where subRiskFlag='M') and contno=b.contno) ";
			}
			if (mSaleChnl != null && !mSaleChnl.equals("")) {
				ZB_sql = ZB_sql
						+ " and b.salechnl='" + "?salechnl?" + "' ";
			}
			if (mPolType != null && !mPolType.equals("") && !"86".equals(mPolType)) {
				ZB_sql = ZB_sql
						+ " and b.prtno like concat('" + "?poltype?" + "','%%') ";
			}
			
			ZB_sql = ZB_sql
					+ ReportPubFun.getWherePartLike("b.managecom",
									ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("a.indate",
									ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1); 
			// QD_sql = QD_sql + " UNION ALL " +
			// StrTool.replace(QD_sql, "lcpol", "lbpol");
			logger.debug("-------------再保呈报-----------------------------"
							+ ZB_sql);
			SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
			sqlbv40.sql(ZB_sql);
			sqlbv40.put("riskcode", mRiskCode);
			sqlbv40.put("poltype", mPolType);
			sqlbv40.put("salechnl", mSaleChnl);
			sqlbv40.put("mngcom", mManageCom);
			sqlbv40.put("mday0", mDay[0]);
			sqlbv40.put("mday1", mDay[1]);
			SSRS zbSSRS = zbExeSQL.execSQL(sqlbv40);

			ExeSQL shbExeSQL = new ExeSQL();
			String ShB_sql = "select a.Operator from LCUWSendTrace a ,lccont b "
					+ "WHERE a.OtherNo=b.proposalcontno and OtherNoType='1' ";
			
			if (mRiskCode != null && !mRiskCode.equals("")) {
				ShB_sql = ShB_sql
					+ " and exists(select '1' from lcpol where riskcode='"
					+ "?riskcode?"
					+ "' and contno=b.contno) ";
			} else if (this.mRiskFlag.equals("on")) {
				ShB_sql = ShB_sql
						+ " and exists(select '1' from lcpol where riskcode in (select riskcode from lmriskapp where subRiskFlag='M') and contno=b.contno) ";
			}
			if (mSaleChnl != null && !mSaleChnl.equals("")) {
				ShB_sql = ShB_sql
						+ " and b.salechnl='" + "?salechnl?" + "' ";
			}
			if (mPolType != null && !mPolType.equals("") && !"86".equals(mPolType)) {
				ShB_sql = ShB_sql
						+ " and b.prtno like concat('" + "?poltype?" + "','%%') ";
			}
			
			ShB_sql = ShB_sql
					+ ReportPubFun.getWherePartLike("b.managecom",
									ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("a.makedate",
									ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1); // 发放入机日期
			logger.debug("-------------上报-----------------------------"
							+ ShB_sql);
			SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
			sqlbv41.sql(ShB_sql);
			sqlbv41.put("riskcode", mRiskCode);
			sqlbv41.put("poltype", mPolType);
			sqlbv41.put("salechnl", mSaleChnl);
			sqlbv41.put("mngcom", mManageCom);
			sqlbv41.put("mday0", mDay[0]);
			sqlbv41.put("mday1", mDay[1]);
			SSRS shbSSRS = shbExeSQL.execSQL(sqlbv41);
			
			// uwflag=9的包括 仅仅自动核保不需人工核保的保单
			ExeSQL specExeSQL = new ExeSQL();
			String Spec_sql = " select operator,passflag from ("
			    + "select distinct(a.contno),min(a.makedate),a.Operator,a.passflag from lccuwsub a,lccont b"
				+ " WHERE a.contno=b.contno and b.conttype='1' "
				+ " and a.passflag in ('1','2','4','9','a') and a.upreport='0' ";
				if (mRiskCode != null && !mRiskCode.equals("")) {
					Spec_sql = Spec_sql
						+ " and exists(select '1' from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and contno=b.contno) ";
				} else if (this.mRiskFlag.equals("on")) {
					Spec_sql = Spec_sql
							+ " and exists(select '1' from lcpol where riskcode in (select riskcode from lmriskapp where subRiskFlag='M') and contno=b.contno) ";
				}
				if (mSaleChnl != null && !mSaleChnl.equals("")) {
					Spec_sql = Spec_sql
							+ " and b.salechnl='" + "?salechnl?" + "' ";
				}
				if (mPolType != null && !mPolType.equals("") && !"86".equals(mPolType)) {
					Spec_sql = Spec_sql
							+ " and b.prtno like concat('" + "?poltype?" + "','%%') ";
				}
				
				Spec_sql = Spec_sql
						+ ReportPubFun.getWherePartLike("b.managecom",
										ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
						+ ReportPubFun.getWherePart("a.makedate",
										ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1); // 发放入机日期
				Spec_sql = Spec_sql
						+ " group by a.contno,a.makedate,a.operator,a.passflag )D where D.passflag='9' ";
			logger.debug("-------------标准体(正常承保) 人工核保  核保结论 为时间点-----------------------------"
							+ Spec_sql);
			SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
			sqlbv42.sql(Spec_sql);
			sqlbv42.put("riskcode", mRiskCode);
			sqlbv42.put("poltype", mPolType);
			sqlbv42.put("salechnl", mSaleChnl);
			sqlbv42.put("mngcom", mManageCom);
			sqlbv42.put("mday0", mDay[0]);
			sqlbv42.put("mday1", mDay[1]);
			SSRS specSSRS = specExeSQL.execSQL(sqlbv42);

			ExeSQL specsecondExeSQL = new ExeSQL();
			String specsecond_sql = " select operator,passflag from ("
			    + "select distinct(a.contno),min(a.makedate),a.Operator,a.passflag from lccuwsub a,lccont b"
				+ " WHERE a.contno=b.contno and b.conttype='1' "
				+ " and a.passflag in ('1','2','4','9','a') and a.upreport='0' ";
				if (mRiskCode != null && !mRiskCode.equals("")) {
					specsecond_sql = specsecond_sql
						+ " and exists(select '1' from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and contno=b.contno) ";
				} else if (this.mRiskFlag.equals("on")) {
					specsecond_sql = specsecond_sql
							+ " and exists(select '1' from lcpol where riskcode in (select riskcode from lmriskapp where subRiskFlag='M') and contno=b.contno) ";
				}
				if (mSaleChnl != null && !mSaleChnl.equals("")) {
					specsecond_sql = specsecond_sql
							+ " and b.salechnl='" + "?salechnl?" + "' ";
				}
				if (mPolType != null && !mPolType.equals("") && !"86".equals(mPolType)) {
					specsecond_sql = specsecond_sql
							+ " and b.prtno like concat('" + "?poltype?" + "','%%') ";
				}
				
				specsecond_sql = specsecond_sql
						+ ReportPubFun.getWherePartLike("b.managecom",
										ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
						+ ReportPubFun.getWherePart("a.makedate",
										ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1); // 发放入机日期
				specsecond_sql = specsecond_sql
						+ " group by a.contno,a.makedate,a.operator,a.passflag )D where D.passflag='4' ";
			logger.debug("-------------次标准体(条件/通融承保)  核保结论 为时间点-----------------------------"
							+ specsecond_sql);
			SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
			sqlbv43.sql(specsecond_sql);
			sqlbv43.put("riskcode", mRiskCode);
			sqlbv43.put("poltype", mPolType);
			sqlbv43.put("salechnl", mSaleChnl);
			sqlbv43.put("mngcom", mManageCom);
			sqlbv43.put("mday0", mDay[0]);
			sqlbv43.put("mday1", mDay[1]);
			SSRS specsecondSSRS = specsecondExeSQL.execSQL(sqlbv43);	
			
			ExeSQL APassExeSQL = new ExeSQL();
			String ANoPass_sql = " select operator,passflag from ("
			    + "select distinct(a.contno),min(a.makedate),a.Operator,a.passflag from lccuwsub a,lccont b"
				+ " WHERE a.contno=b.contno and b.conttype='1' "
				+ " and a.passflag in ('1','2','4','9','a') and a.upreport='0' ";
				if (mRiskCode != null && !mRiskCode.equals("")) {
					ANoPass_sql = ANoPass_sql
						+ " and exists(select '1' from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and contno=b.contno) ";
				} else if (this.mRiskFlag.equals("on")) {
					ANoPass_sql = ANoPass_sql
							+ " and exists(select '1' from lcpol where riskcode in (select riskcode from lmriskapp where subRiskFlag='M') and contno=b.contno) ";
				}
				if (mSaleChnl != null && !mSaleChnl.equals("")) {
					ANoPass_sql = ANoPass_sql
							+ " and b.salechnl='" + "?salechnl?" + "' ";
				}
				if (mPolType != null && !mPolType.equals("") && !"86".equals(mPolType)) {
					ANoPass_sql = ANoPass_sql
							+ " and b.prtno like concat('" + "?poltype?" + "','%%') ";
				}
				
				ANoPass_sql = ANoPass_sql
						+ ReportPubFun.getWherePartLike("b.managecom",
										ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
						+ ReportPubFun.getWherePart("a.makedate",
										ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1); // 发放入机日期
				ANoPass_sql = ANoPass_sql
						+ " group by a.contno,a.makedate,a.operator,a.passflag )D where D.passflag='2' ";
			logger.debug("－－－－－－－－延期件数sql－－－－－－－－:" + ANoPass_sql);	
			SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
			sqlbv44.sql(ANoPass_sql);
			sqlbv44.put("riskcode", mRiskCode);
			sqlbv44.put("poltype", mPolType);
			sqlbv44.put("salechnl", mSaleChnl);
			sqlbv44.put("mngcom", mManageCom);
			sqlbv44.put("mday0", mDay[0]);
			sqlbv44.put("mday1", mDay[1]);
			SSRS ANoPassSSRS = APassExeSQL.execSQL(sqlbv44);
			
			ExeSQL NoPassExeSQL = new ExeSQL();
			String NoPass_sql = " select operator,passflag from ("
				    + "select distinct(a.contno),min(a.makedate),a.Operator,a.passflag from lccuwsub a,lccont b"
					+ " WHERE a.contno=b.contno and b.conttype='1' "
					+ " and a.passflag in ('1','2','4','9','a') and a.upreport='0' ";
					if (mRiskCode != null && !mRiskCode.equals("")) {
						NoPass_sql = NoPass_sql
							+ " and exists(select '1' from lcpol where riskcode='"
							+ "?riskcode?"
							+ "' and contno=b.contno) ";
					} else if (this.mRiskFlag.equals("on")) {
						NoPass_sql = NoPass_sql
								+ " and exists(select '1' from lcpol where riskcode in (select riskcode from lmriskapp where subRiskFlag='M') and contno=b.contno) ";
					}
					if (mSaleChnl != null && !mSaleChnl.equals("")) {
						NoPass_sql = NoPass_sql
								+ " and b.salechnl='" + "?salechnl?" + "' ";
					}
					if (mPolType != null && !mPolType.equals("") && !"86".equals(mPolType)) {
						NoPass_sql = NoPass_sql
								+ " and b.prtno like concat('" + "?poltype?" + "','%%') ";
					}
					
					NoPass_sql = NoPass_sql
							+ ReportPubFun.getWherePartLike("b.managecom",
											ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
							+ ReportPubFun.getWherePart("a.makedate",
											ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1); // 发放入机日期
					NoPass_sql = NoPass_sql
							+ " group by a.contno,a.makedate,a.operator,a.passflag )D where D.passflag='1' ";
			logger.debug("－－－－－－－－－－拒保件数sql－－－－－－－－－－－" + NoPass_sql);
			SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
			sqlbv45.sql(NoPass_sql);
			sqlbv45.put("riskcode", mRiskCode);
			sqlbv45.put("poltype", mPolType);
			sqlbv45.put("salechnl", mSaleChnl);
			sqlbv45.put("mngcom", mManageCom);
			sqlbv45.put("mday0", mDay[0]);
			sqlbv45.put("mday1", mDay[1]);
			SSRS NoPassSSRS = NoPassExeSQL.execSQL(sqlbv45);				

			ExeSQL BackExeSQL = new ExeSQL();
			String Back_sql = " select operator,passflag from ("
			    + "select distinct(a.contno),min(a.makedate),a.Operator,a.passflag from lccuwsub a,lccont b"
				+ " WHERE a.contno=b.contno and b.conttype='1' "
				+ " and a.passflag in ('1','2','4','9','a') and a.upreport='0' ";
				if (mRiskCode != null && !mRiskCode.equals("")) {
					Back_sql = Back_sql
						+ " and exists(select '1' from lcpol where riskcode='"
						+ "?riskcode?"
						+ "' and contno=b.contno) ";
				} else if (this.mRiskFlag.equals("on")) {
					Back_sql = Back_sql
							+ " and exists(select '1' from lcpol where riskcode in (select riskcode from lmriskapp where subRiskFlag='M') and contno=b.contno) ";
				}
				if (mSaleChnl != null && !mSaleChnl.equals("")) {
					Back_sql = Back_sql
							+ " and b.salechnl='" + "?salechnl?" + "' ";
				}
				if (mPolType != null && !mPolType.equals("") && !"86".equals(mPolType)) {
					Back_sql = Back_sql
							+ " and b.prtno like concat('" + "?poltype?" + "','%%') ";
				}
				
				Back_sql = Back_sql
						+ ReportPubFun.getWherePartLike("b.managecom",
										ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
						+ ReportPubFun.getWherePart("a.makedate",
										ReportPubFun.getParameterStr(mDay[0],"?mday0?"), ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1); // 发放入机日期
				Back_sql = Back_sql
						+ " group by a.contno,a.makedate,a.operator,a.passflag )D where D.passflag='a' ";
			logger.debug("－－－－－－－－－－撤单件数sql－－－－－－－－－－－－－" + Back_sql);
			SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
			sqlbv46.sql(Back_sql);
			sqlbv46.put("riskcode", mRiskCode);
			sqlbv46.put("poltype", mPolType);
			sqlbv46.put("salechnl", mSaleChnl);
			sqlbv46.put("mngcom", mManageCom);
			sqlbv46.put("mday0", mDay[0]);
			sqlbv46.put("mday1", mDay[1]);
			SSRS BackSSRS = BackExeSQL.execSQL(sqlbv46);			

			tlistTable.setName("uw10");
			String strsql = "";
			double shbffjc = 0; // 总核保通知书发放件次
			double szbj = 0; // 总再保件次
			double sshbj = 0; // 总上报件次
			double sbzj = 0; // 总标准体件数
			double scbzj = 0; // 总次标准体件数
			double sjbj = 0; // 总拒保件数
			double syqj = 0; // 总延期件数
			double scdj = 0; // 总撤单件数
			String HBS_sql = " select distinct usercode from lduwuser where "
					+ " managecom in('86') and uwtype='1' ";
					//+ " and  uwpopedom not in ('K','C') ";
			if (UserCode == null) {
				UserCode = "";
				strsql = " ";
			} else if (!UserCode.equals("")) {
				strsql = " and usercode='" + "?usercode?" + "' ";
			}

			HBS_sql = HBS_sql + strsql + " order by usercode ";

			logger.debug("获得唯一的核保师的sql语句是:" + HBS_sql);
			SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
			sqlbv47.sql(HBS_sql);
			sqlbv47.put("usercode", "?usercode?");
			ExeSQL HBSExeSQL = new ExeSQL();
			SSRS HBSSSRS = new SSRS();
			HBSSSRS = HBSExeSQL.execSQL(sqlbv47);

			if (HBSSSRS.getMaxRow() == 0) {
				CError.buildErr(this, "查询的结果是0");
			}

			for (int i = 1; i <= HBSSSRS.MaxRow; i++) {
				double hbffjc = 0; // 核保通知书发放件次
				double zbj = 0; // 再保件次
				double shbj = 0; // 上报件次
				double bzj = 0; // 标准体件数
				double cbzj = 0; // 次标准体件数
				double jbj = 0; // 拒保件数
				double yqj = 0; // 延期件数
				double cdj = 0; // 撤单件数				

				hbffjc += ReportPubFun.getCounts(HBSSSRS.GetText(i, 1), HBSSRS,	1); // 核保通知书发放件次	
				
				zbj += ReportPubFun.getCounts(HBSSSRS.GetText(i, 1), zbSSRS, 1); // 再保件

				shbj += ReportPubFun.getCounts(HBSSSRS.GetText(i, 1), shbSSRS, 1); // 上报件
				
				bzj += ReportPubFun.getCounts(HBSSSRS.GetText(i, 1), specSSRS, 1); // 标准体件数

				cbzj += ReportPubFun.getCounts(HBSSSRS.GetText(i, 1), specsecondSSRS, 1); // 次标准体件数				
				
				jbj += ReportPubFun.getCounts(HBSSSRS.GetText(i, 1), NoPassSSRS, 1); // 拒保件数

				yqj += ReportPubFun.getCounts(HBSSSRS.GetText(i, 1), ANoPassSSRS, 1); // 延期件数

				cdj += ReportPubFun.getCounts(HBSSSRS.GetText(i, 1), BackSSRS, 1); // 撤单件数				

				strArr = new String[10];
				strArr[0] = ReportPubFun.getLdUserInfo(HBSSSRS.GetText(i, 1))
						.getUserName();
				strArr[1] = new DecimalFormat("0").format(hbffjc); // 核保通知书发放件次
				strArr[2] = new DecimalFormat("0").format(zbj); // 再保件
				strArr[3] = new DecimalFormat("0").format(shbj); //上报件 
				strArr[4] = new DecimalFormat("0").format(bzj); // 标准体件数
				strArr[5] = new DecimalFormat("0").format(cbzj); // 次标准体件数
				strArr[6] = new DecimalFormat("0").format(jbj); // 拒保件数
				strArr[7] = new DecimalFormat("0").format(yqj); // 延期件数
				strArr[8] = new DecimalFormat("0").format(cdj); // 撤单件数				
				strArr[9] = new DecimalFormat("0").format(hbffjc + zbj + shbj + bzj + cbzj + jbj
						+ yqj + cdj); // 合计
				tlistTable.add(strArr);

				shbffjc = shbffjc + hbffjc;
				szbj = szbj + zbj;
				sshbj = sshbj + shbj;
				sbzj = sbzj + bzj;
				scbzj = scbzj + cbzj;
				sjbj = sjbj + jbj;
				syqj = syqj + yqj;
				scdj = scdj + cdj;

			}

			strArr = new String[10];
			strArr[0] = "合计";
			strArr[1] = new DecimalFormat("0").format(shbffjc);
			strArr[2] = new DecimalFormat("0").format(szbj);
			strArr[3] = new DecimalFormat("0").format(sshbj);
			strArr[4] = new DecimalFormat("0").format(sbzj);
			strArr[5] = new DecimalFormat("0").format(scbzj);
			strArr[6] = new DecimalFormat("0").format(sjbj);
			strArr[7] = new DecimalFormat("0").format(syqj);
			strArr[8] = new DecimalFormat("0").format(scdj);			
			strArr[9] = new DecimalFormat("0").format(shbffjc + szbj + sshbj + sbzj + scbzj + sjbj
					+ syqj + scdj);
			tlistTable.add(strArr);
			strArr = new String[10];
			strArr[0] = "HBGrp";
			strArr[1] = "shbffjc"; // 核保通知书发放件次
			strArr[2] = "szbj";  //再保
			strArr[3] = "sshbj"; //上报
			strArr[4] = "sbzj"; // 标准体件数
			strArr[5] = "scbzj"; // 次标准体件数
			strArr[6] = "sjbj"; // 拒保件数
			strArr[7] = "syqj"; // 延期件数
			strArr[8] = "scdj"; // 撤单件数
			strArr[9] = "HJ"; // 合计

			xmlexport.addListTable(tlistTable, strArr);
		}

		// ************************保险加费评点分析报表**********************************************
		if (mDefineCode.equals("uw11")) {
			xmlexport.createDocument("HeathAddPay.vts", "printer");

			// 在遍历前先查询得到set或SSRS
			ExeSQL ApExeSQL = new ExeSQL();
			String Ap_sql = "select lcpol.insuredappage from lcpol,lcprem"
					+ " where lcpol.polno=lcprem.polno"
					+ " and lcpol.salechnl in ('02','03')"
					+ " and lcpol.grppolno='00000000000000000000'"
					+ " and suppriskscore is not null"
					+ ReportPubFun.getWherePartInRiskCode("lcpol.riskcode",
							mRiskCode, mRiskFlag)
					+ ReportPubFun.getWherePartLike("lcpol.managecom",
							ReportPubFun.getParameterStr(mManageCom, "?mngcom?"))
					+ ReportPubFun.getWherePart("lcpol.makedate", ReportPubFun.getParameterStr(mDay[0],"?mday0?"),
							ReportPubFun.getParameterStr(mDay[1],"?mday1?"), 1);

			String Ap_b_sql = StrTool.replace(Ap_sql, "lcpol", "lbpol");
			String HeathAdd = " and PayPlanType='1'";
			String JobAdd = " and PayPlanType='2'";

			logger.debug("加费点为25" + Ap_sql + HeathAdd
					+ " and suppriskscore='25'" + " UNION ALL " + Ap_b_sql
					+ HeathAdd + " and suppriskscore='25'");
			SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
			sqlbv48.sql(Ap_sql + HeathAdd
					+ " and suppriskscore='25'" + " UNION ALL " + Ap_b_sql
					+ HeathAdd + " and suppriskscore='25'");
			sqlbv48.put("mngcom", mManageCom);
			sqlbv48.put("mday0", mDay[0]);
			sqlbv48.put("mday1", mDay[1]);
			SSRS AP25SSRS = ApExeSQL.execSQL(sqlbv48);
			SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
			sqlbv49.sql(Ap_sql + HeathAdd
					+ " and suppriskscore='50'" + " UNION ALL " + Ap_b_sql
					+ HeathAdd + " and suppriskscore='50'");
			sqlbv49.put("mngcom", mManageCom);
			sqlbv49.put("mday0", mDay[0]);
			sqlbv49.put("mday1", mDay[1]);
			SSRS AP50SSRS = ApExeSQL.execSQL(sqlbv49);
			SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
			sqlbv50.sql(Ap_sql + HeathAdd
					+ " and suppriskscore='75'" + " UNION ALL " + Ap_b_sql
					+ HeathAdd + " and suppriskscore='75'");
			sqlbv50.put("mngcom", mManageCom);
			sqlbv50.put("mday0", mDay[0]);
			sqlbv50.put("mday1", mDay[1]);
			SSRS AP75SSRS = ApExeSQL.execSQL(sqlbv50);
			SQLwithBindVariables sqlbv51 = new SQLwithBindVariables();
			sqlbv51.sql(Ap_sql + HeathAdd
					+ " and suppriskscore='100'" + " UNION ALL " + Ap_b_sql
					+ HeathAdd + " and suppriskscore='100'");
			sqlbv51.put("mngcom", mManageCom);
			sqlbv51.put("mday0", mDay[0]);
			sqlbv51.put("mday1", mDay[1]);
			SSRS AP100SSRS = ApExeSQL.execSQL(sqlbv51);
			SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
			sqlbv52.sql(Ap_sql + HeathAdd
					+ " and suppriskscore='125'" + " UNION ALL " + Ap_b_sql
					+ HeathAdd + " and suppriskscore='125'");
			sqlbv52.put("mngcom", mManageCom);
			sqlbv52.put("mday0", mDay[0]);
			sqlbv52.put("mday1", mDay[1]);
			SSRS AP125SSRS = ApExeSQL.execSQL(sqlbv52);
			SQLwithBindVariables sqlbv53 = new SQLwithBindVariables();
			sqlbv53.sql(Ap_sql + HeathAdd
					+ " and suppriskscore='150'" + " UNION ALL " + Ap_b_sql
					+ HeathAdd + " and suppriskscore='150'");
			sqlbv53.put("mngcom", mManageCom);
			sqlbv53.put("mday0", mDay[0]);
			sqlbv53.put("mday1", mDay[1]);
			SSRS AP150SSRS = ApExeSQL.execSQL(sqlbv53);
			SQLwithBindVariables sqlbv54 = new SQLwithBindVariables();
			sqlbv54.sql(Ap_sql + HeathAdd
					+ " and suppriskscore='175'" + " UNION ALL " + Ap_b_sql
					+ HeathAdd + " and suppriskscore='175'");
			sqlbv54.put("mngcom", mManageCom);
			sqlbv54.put("mday0", mDay[0]);
			sqlbv54.put("mday1", mDay[1]);
			SSRS AP175SSRS = ApExeSQL.execSQL(sqlbv54);
			SQLwithBindVariables sqlbv55 = new SQLwithBindVariables();
			sqlbv55.sql(Ap_sql + HeathAdd
					+ " and suppriskscore='200'" + " UNION ALL " + Ap_b_sql
					+ HeathAdd + " and suppriskscore='200'");
			sqlbv55.put("mngcom", mManageCom);
			sqlbv55.put("mday0", mDay[0]);
			sqlbv55.put("mday1", mDay[1]);
			SSRS AP200SSRS = ApExeSQL.execSQL(sqlbv55);
			SQLwithBindVariables sqlbv56 = new SQLwithBindVariables();
			sqlbv56.sql(Ap_sql + HeathAdd
					+ " and suppriskscore>'200'" + " UNION ALL " + Ap_b_sql
					+ HeathAdd + " and suppriskscore>'200'");
			sqlbv56.put("mngcom", mManageCom);
			sqlbv56.put("mday0", mDay[0]);
			sqlbv56.put("mday1", mDay[1]);
			SSRS APD200SSRS = ApExeSQL.execSQL(sqlbv56);
			SQLwithBindVariables sqlbv57 = new SQLwithBindVariables();
			sqlbv57.sql(Ap_sql + HeathAdd + " UNION ALL "
					+ Ap_b_sql + HeathAdd);
			sqlbv57.put("mngcom", mManageCom);
			sqlbv57.put("mday0", mDay[0]);
			sqlbv57.put("mday1", mDay[1]);
			SSRS APSumSSRS = ApExeSQL.execSQL(sqlbv57);
			SQLwithBindVariables sqlbv58 = new SQLwithBindVariables();
			sqlbv58.sql(Ap_sql + JobAdd
					+ " and suppriskscore='25'" + " UNION ALL " + Ap_b_sql
					+ JobAdd + " and suppriskscore='25'");
			sqlbv58.put("mngcom", mManageCom);
			sqlbv58.put("mday0", mDay[0]);
			sqlbv58.put("mday1", mDay[1]);
			SSRS APJobSSRS = ApExeSQL.execSQL(sqlbv58);
			tlistTable.setName("uw11");

			int stf = 0;
			int sft = 0;
			int ssf = 0;
			int soh = 0;
			int sotf = 0;
			int soft = 0;
			int sosf = 0;
			int sth = 0;
			int sdth = 0;
			int ssum = 0;
			int sapjob = 0;

			for (int i = 0; i < mFlag.length; i++) {
				String Flag = "";

				if (mFlag[i][1].equals("以上")) {
					Flag = mFlag[i][0] + mFlag[i][1];
				} else {
					Flag = mFlag[i][0] + "-" + mFlag[i][1];
				}

				int tf = 0;
				int ft = 0;
				int sf = 0;
				int oh = 0;
				int otf = 0;
				int oft = 0;
				int osf = 0;
				int th = 0;
				int dth = 0;
				int sum = 0;
				int apjob = 0;

				for (int j = 1; j <= AP25SSRS.MaxRow; j++) {
					tf += ReportPubFun.getCount(AP25SSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 健康加费评点等于25时统计件数
				}

				for (int j = 1; j <= AP50SSRS.MaxRow; j++) {
					ft += ReportPubFun.getCount(AP50SSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 健康加费评点等于50时统计件数
				}

				for (int j = 1; j <= AP75SSRS.MaxRow; j++) {
					sf += ReportPubFun.getCount(AP75SSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 健康加费评点等于75时统计件数
				}

				for (int j = 1; j <= AP100SSRS.MaxRow; j++) {
					oh += ReportPubFun.getCount(AP100SSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 健康加费评点等于100时统计件数
				}

				for (int j = 1; j <= AP125SSRS.MaxRow; j++) {
					otf += ReportPubFun.getCount(AP125SSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 健康加费评点等于125时统计件数
				}

				for (int j = 1; j <= AP150SSRS.MaxRow; j++) {
					oft += ReportPubFun.getCount(AP150SSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 健康加费评点等于150时统计件数
				}

				for (int j = 1; j <= AP175SSRS.MaxRow; j++) {
					osf += ReportPubFun.getCount(AP175SSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 健康加费评点等于175时统计件数
				}

				for (int j = 1; j <= AP200SSRS.MaxRow; j++) {
					th += ReportPubFun.getCount(AP200SSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 健康加费评点等于200时统计件数
				}

				for (int j = 1; j <= APD200SSRS.MaxRow; j++) {
					dth += ReportPubFun.getCount(APD200SSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 健康加费评点大于200时统计件数
				}

				for (int j = 1; j <= APSumSSRS.MaxRow; j++) {
					sum += ReportPubFun.getCount(APSumSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 健康加费承保件数总数
				}

				for (int j = 1; j <= APJobSSRS.MaxRow; j++) {
					apjob += ReportPubFun.getCount(APJobSSRS.GetText(j, 1),
							mFlag[i][0], mFlag[i][1]); // 职业加费承保件数总数
				}

				strArr = new String[12];
				strArr[0] = Flag;
				strArr[1] = String.valueOf(tf); // 健康加费评点等于25时统计件数
				strArr[2] = String.valueOf(ft); // 健康加费评点等于50时统计件数
				strArr[3] = String.valueOf(sf); // 健康加费评点等于75时统计件数
				strArr[4] = String.valueOf(oh); // 健康加费评点等于100时统计件数
				strArr[5] = String.valueOf(otf); // 健康加费评点等于125时统计件数
				strArr[6] = String.valueOf(oft); // 健康加费评点等于150时统计件数
				strArr[7] = String.valueOf(osf); // 健康加费评点等于175时统计件数
				strArr[8] = String.valueOf(th); // 健康加费评点等于200时统计件数
				strArr[9] = String.valueOf(dth); // 健康加费评点大于200时统计件数
				strArr[10] = String.valueOf(sum); // 健康加费承保件数总数
				strArr[11] = String.valueOf(apjob); // 职业加费承保件数总数

				tlistTable.add(strArr);

				stf = stf + tf;
				sft = sft + ft;
				ssf = ssf + sf;
				soh = soh + oh;
				sotf = sotf + otf;
				soft = soft + oft;
				sosf = sosf + osf;
				sth = sth + th;
				sdth = sdth + dth;
				ssum = ssum + sum;
				sapjob = sapjob + apjob;
			}

			strArr = new String[12];
			strArr[0] = "合计";
			strArr[1] = new DecimalFormat("0").format(stf);
			strArr[2] = new DecimalFormat("0").format(sft);
			strArr[3] = new DecimalFormat("0").format(ssf);
			strArr[4] = new DecimalFormat("0").format(soh);
			strArr[5] = new DecimalFormat("0").format(sotf);
			strArr[6] = new DecimalFormat("0").format(soft);
			strArr[7] = new DecimalFormat("0").format(sosf);
			strArr[8] = new DecimalFormat("0").format(sth);
			strArr[9] = new DecimalFormat("0").format(sdth);
			strArr[10] = new DecimalFormat("0").format(ssum);
			strArr[11] = new DecimalFormat("0").format(sapjob);
			tlistTable.add(strArr);

			strArr = new String[12];
			strArr[0] = "ageGrp";
			strArr[1] = "tf"; // 健康加费评点等于25时统计件数
			strArr[2] = "ft"; // 健康加费评点等于50时统计件数
			strArr[3] = "sf"; // 健康加费评点等于75时统计件数
			strArr[4] = "oh"; // 健康加费评点等于100时统计件数
			strArr[5] = "otf"; // 健康加费评点等于125时统计件数
			strArr[6] = "oft"; // 健康加费评点等于150时统计件数
			strArr[7] = "osf"; // 健康加费评点等于175时统计件数
			strArr[8] = "th"; // 健康加费评点等于200时统计件数
			strArr[9] = "dth"; // 健康加费评点大于200时统计件数
			strArr[10] = "sum"; // 健康加费承保件数总数
			strArr[11] = "apjob"; // 职业加费承保件数总数

			xmlexport.addListTable(tlistTable, strArr);
		}

		String CurrentDate = PubFun.getCurrentDate();
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		String sdate = mDay[0] + "-";
		String StartDate = StrTool.decodeStr(sdate, "-", 1) + "年"
		+ StrTool.decodeStr(sdate, "-", 2) + "月"
		+ StrTool.decodeStr(sdate, "-", 3) + "日";
		String edate = mDay[1] + "-";
		String EndDate = StrTool.decodeStr(edate, "-", 1) + "年"
		+ StrTool.decodeStr(edate, "-", 2) + "月"
		+ StrTool.decodeStr(edate, "-", 3) + "日";
		String cdate = CurrentDate + "-";
		String CurrentDate1 = StrTool.decodeStr(cdate, "-", 1) + "年"
		+ StrTool.decodeStr(cdate, "-", 2) + "月"
		+ StrTool.decodeStr(cdate, "-", 3) + "日";
		texttag.add("StartDate", StartDate);
		texttag.add("EndDate", EndDate);
		texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
		texttag.add("Operator", mGlobalInput.Operator);
		texttag.add("time", CurrentDate1);
		logger.debug("大小" + texttag.size());

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		// xmlexport.outputDocumentToFile("e:\\",xmlname);//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}
}
