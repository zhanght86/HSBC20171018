package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: 理赔清单打印：理赔日结清单-----LLPRBDailyBalance.vts
 * </p>
 * <p>
 * Description:财务科目就是理赔日结类型
 * 统计界面：统计机构（总公司、某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）、审批通过起止日期、
 * 财务科目选项（单个科目、全部科目）、排序选项（审批通过日期＋赔案号、险种号＋赔案号、科目＋险种＋赔案号） 表头：报表名称、统计条件、统计日期
 * 数据项：赔案号、保单号、出险人、给付险种简称和代码、审核结论、意外事故发生日期和出险日期、审批通过日期、财务科目、
 * 项目名称、给付金额、领款人和身份证、案件标识（如出险人死亡需要标识）、支付方式（现金、银行代付、支票、网银等）. 审核人代码,审核人姓名,实付日期
 * 算法：在选择的机构内、指定的起止日期审批确认的案件。（包括所有审核结论的赔案） 排序：选定的排序 表尾：统计数量和汇总金额 能输出到Excel
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author niuzj,2005-11-09
 * @version 1.0
 */

public class LLPRBDailyBalanceBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBDailyBalanceBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();
	private GlobalInput mG = new GlobalInput();
	private String mStatiCode = ""; // 统计机构
	private String mStartDate = ""; // 审批通过起期
	private String mEndDate = ""; // 审批通过止期
	private String mFinDayType = ""; // 财务科目
	private String mNCLType = ""; // 申请类型
	private String mStatiOpti = ""; // 排序选项

	public LLPRBDailyBalanceBL() {

	}

	/**
	 * 主函数------主要用于设置数据，调试程序入口
	 * 
	 * @param:
	 * @return: 数据处理后信息
	 */

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StatiCode", "862100");// 统计机构
		tTransferData.setNameAndValue("StartDate", "2006-01-01");// 开始日期
		tTransferData.setNameAndValue("EndDate", "2006-01-25"); // 结束日期
		tTransferData.setNameAndValue("FinDayType", "10");// 财务科目
		tTransferData.setNameAndValue("StatiOpti", "3");// 排序选项

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLPRBDailyBalanceBL tLLPRBDailyBalanceBL = new LLPRBDailyBalanceBL();
		if (tLLPRBDailyBalanceBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔清单打印：理赔日结清单出错---------------");
		}
	}

	/**
	 * 提交数据的公共方法
	 * 
	 * @param: cInputData--- 输入的数据 cOperate--- 数据操作符
	 * @return: 布尔值，成功返回“true”，失败返回“false”
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("-----理赔清单打印：理赔日结清单开始---------LLPRBDailyBalanceBL---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 校验检查外部传入的数据
		if (!checkInputData()) {
			return false;
		}

		// 处理业务数据
		if (!dealData()) {
			return false;
		}
		logger.debug("-----理赔清单打印：理赔日结清单结束----LLPRBDailyBalanceBL---");

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
		mInputData = cInputData;// 得到外部传入的数据,将数据备份到本类中
		mG = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStatiCode = (String) mTransferData.getValueByName("StatiCode"); // 统计机构
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 审批通过起期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 审批通过止期
		mFinDayType = (String) mTransferData.getValueByName("FinDayType"); // 财务科目
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型
		mStatiOpti = (String) mTransferData.getValueByName("StatiOpti"); // 排序选项

		return true;
	}

	// 根据外部传入的 "财务科目"拼写出不同的条件,去查询财务类型和业务类型
	// 需要参数: 财务科目类型代码mFinDayType
	// 返回参数: 条件语句strFinDayType
	/**
	 * by niuzj,2006-02-09,对财务科目04/10/11/12的特殊说明
	 * 1、财务科目10，统计的是由于死亡引起的理赔日结，统计的是长期险 2、财务科目11，统计的是由于除死亡原因外引起的理赔日结，统计的是长期险
	 * 3、财务科目12，统计的是由任何原因引起的理赔日结，统计的是短期险
	 * 4、由于红利没有区分是什么原因引起的（医疗/死亡/等），故需要对每笔红利的记录都去判断它对应的赔案的理赔原因
	 * 5、对于拒付案件的红利，无论是由什么原因引起的，都要统计到财务科目04中 按照这些条件修改财务科目04、10、11、12的统计SQL
	 */
	private String SQLFinDayType(String FinDayType) {
		String strFinDayType = "";
		String tFinDayType = FinDayType;

		if (tFinDayType.equals("01"))// 01----拒赔退费日结
		{
			strFinDayType = "and t.feefinatype = 'TF' and t.feeoperationtype  in('D01','D06') ";
		} else if (tFinDayType.equals("02"))// 02----退还多交保费日结
		{// Modify by zhaorx 2006-03-27
			strFinDayType = "and t.feefinatype = 'TF' and t.feeoperationtype in ('C01','C08') ";
		} else if (tFinDayType.equals("03"))// 03----欠交保费日结
		{
			strFinDayType = "and t.feefinatype in('BF','LX') and t.feeoperationtype = 'C02' ";
		} else if (tFinDayType.equals("04"))// 04----拒赔退保金日结
		{
			strFinDayType = " and (( t.feefinatype = 'TB' and t.feeoperationtype = 'D02') "
					// 对于拒赔案件，终了红利计算到04中，而在10、11、12中只统计给付的案件
					+ " or (t.feefinatype = 'EF' and t.feeoperationtype = 'C05' "
					+ " and (exists (select p.polno from llclaimpolicy p where p.clmno=t.otherno and p.polno=t.polno and p.givetype='1')))) ";
		} else if (tFinDayType.equals("05"))// 05----扣回自垫保费日结
		{
			strFinDayType = "and t.feefinatype in('BF','LX') and t.feeoperationtype = 'C07' ";
		} else if (tFinDayType.equals("06"))// 06----扣回质押贷款日结
		{
			strFinDayType = "and t.feefinatype in('HK','LX') and t.feeoperationtype ='C03' ";
		} else if (tFinDayType.equals("07"))// 07----年金日结
		{
			strFinDayType = "and t.feefinatype = 'EF' and t.feeoperationtype ='C04' ";
		}
		// else if(tFinDayType.equals("08"))//08----终了红利日结
		// {
		// strFinDayType="and t.feefinatype = 'EF' and t.feeoperationtype =
		// 'C05' and t.subfeeoperationtype='C0502' ";
		// }
		else if (tFinDayType.equals("09"))// 09----退还未满保费日结
		{
			strFinDayType = "and t.feefinatype = 'TF' and t.feeoperationtype  in('D05','C09') ";
		} else if (tFinDayType.equals("10"))// 10----死伤给付日结
		{
			strFinDayType = " and ((t.feefinatype = 'PK' and t.feeoperationtype = 'A' and trim(t.subfeeoperationtype) like '%02') "
					+ " or (t.feefinatype = 'EF' and t.feeoperationtype = 'C05' "
					+ " and (exists (select b.clmno from llclaimdutykind b where b.clmno = t.otherno and b.getdutykind in ('102','202'))) "
					+ " and (exists (select p.polno from llclaimpolicy p where p.clmno=t.otherno and p.polno=t.polno and p.givetype='0')))) ";
		} else if (tFinDayType.equals("11"))// 11----医疗给付日结
		{
			strFinDayType = " and ((t.feefinatype = 'PK' and t.feeoperationtype = 'A' and t.subfeeoperationtype not in('102','202')) "
					+ " or (t.feefinatype = 'EF' and t.feeoperationtype = 'C05' "
					+ " and (exists (select b.clmno from llclaimdutykind b where b.clmno = t.otherno and b.getdutykind not in ('102','202'))) "
					+ " and (exists (select p.polno from llclaimpolicy p where p.clmno=t.otherno and p.polno=t.polno and p.givetype='0')))) ";
		} else if (tFinDayType.equals("12"))// 12----赔款支出日结
		{
			strFinDayType = " and ((t.feefinatype = 'PK' and t.feeoperationtype = 'A') "
					+ " or (t.feefinatype = 'EF' and t.feeoperationtype = 'C05'  "
					+ " and (exists (select p.polno from llclaimpolicy p where p.clmno=t.otherno and p.polno=t.polno and p.givetype='0')))) ";
		} else if (tFinDayType.equals("13"))// 13----预付赔款日结
		{
			strFinDayType = "and t.feefinatype = 'B' and t.feeoperationtype ='B' ";
		} else // 全部选择
		{
			strFinDayType = "";
		}
		return strFinDayType;
	}

	/**
	 * 根据财务类型和业务类型去判断财务科目 输入:财务类型,业务类型/子业务类型 输出:财务科目
	 */
	private String StrFinDayType(String tFinType, String tFeeType,
			String tSubFeeType) {
		String ttFinType = tFinType;
		String ttFeeType = tFeeType;
		String ttSubFeeType = tSubFeeType;
		String ttFinDayType = "";

		if (ttFinType.equals("TF")
				&& (ttFeeType.equals("D01") || ttFeeType.equals("D06"))) {
			ttFinDayType = "01"; // 01----拒赔退费日结
		} else if (ttFinType.equals("TF")
				&& (ttFeeType.equals("C01") || ttFeeType.equals("C08"))) {// Modify
																			// by
																			// zhaorx
																			// 2006-03-27
			ttFinDayType = "02"; // 02----退还多交保费日结
		} else if ((ttFinType.equals("BF") || ttFinType.equals("LX"))
				&& ttFeeType.equals("C02")) {
			ttFinDayType = "03"; // 03----欠交保费日结
		} else if (ttFinType.equals("TB") && ttFeeType.equals("D02")) {
			ttFinDayType = "04"; // 04----拒赔退保金日结
		} else if ((ttFinType.equals("BF") || ttFinType.equals("LX"))
				&& ttFeeType.equals("C07")) {
			ttFinDayType = "05"; // 05----扣回自垫保费日结
		} else if ((ttFinType.equals("HK") || ttFinType.equals("LX"))
				&& ttFeeType.equals("C03")) {
			ttFinDayType = "06"; // 06----扣回质押贷款日结
		} else if (ttFinType.equals("EF") && ttFeeType.equals("C04")) {
			ttFinDayType = "07"; // 07----年金日结
		}
		// else if(ttFinType.equals("EF") && ttFeeType.equals("C05") &&
		// ttSubFeeType.equals("C0502"))
		// {
		// ttFinDayType = "08"; //08----终了红利日结
		// }
		else if (ttFinType.equals("TF")
				&& (ttFeeType.equals("D05") || ttFeeType.equals("C09"))) {
			ttFinDayType = "09"; // 09----退还未满保费日结
		} else if (ttFinType.equals("PK") && ttFeeType.equals("A")
				&& (ttSubFeeType.equals("102") || ttSubFeeType.equals("202"))) {
			ttFinDayType = "10"; // 10----死伤给付日结
		} else if (ttFinType.equals("PK") && ttFeeType.equals("A")
				&& (ttSubFeeType != "102" || ttSubFeeType != "202")) {
			ttFinDayType = "11"; // 11----医疗给付日结
		} else if (ttFinType.equals("PK") && ttFeeType.equals("A")) {
			ttFinDayType = "12"; // 12----赔款支出日结
		} else if (ttFinType.equals("B") && ttFeeType.equals("B")) {
			ttFinDayType = "13"; // 13----预付赔款日结
		} else {
			ttFinDayType = ""; // 其它财务科目
		}

		return ttFinDayType;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tXmlExport.createDocument("LLPRBDailyBalance.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("CFCD");
		// 定义列表标题
		String[] Title = new String[21];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";
		Title[8] = "";
		Title[9] = "";
		Title[10] = "";
		Title[11] = "";
		Title[12] = "";
		Title[13] = "";
		Title[14] = "";
		Title[15] = "";
		Title[16] = "";
		Title[17] = "";
		Title[18] = "";
		Title[19] = "";
		Title[20] = "";

		logger.debug("---以下 查询列表$*/CFCD/ROW/COL内容，并为列表赋值--");
		double tStatMoney = 0;// 总金额
		int tStatCount = 0; // 用来统计符合条件的记录的数量

		// 业务类型判断llregister.rgtobj：1-个险 2-团险
		String tNCLType = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where t.otherno = llregister.rgtno and llregister.rgtobj = '1' ) "
				: " and exists (select 'X' from llregister where t.otherno = llregister.rgtno and llregister.rgtobj = '2' ) ";

		// 财务科目名称
		String tFinDayType = "";
		// if(mFinDayType==null || mFinDayType.equals(""))
		// {
		// tFinDayType = "全部财务科目";
		// }
		// else
		// {
		// String strSql1=" select codename from ldcode where
		// codetype='acceptatonement' and code='"+mFinDayType+"' ";
		// logger.debug("strSql1=="+strSql1);
		// ExeSQL cusExeSQL = new ExeSQL() ;
		// SSRS tSSRS = new SSRS();
		// tSSRS = cusExeSQL.execSQL(strSql1);
		// tFinDayType = tSSRS.GetText(1,1);
		// }

		String strSQL = "";
		/**
		 * 2006-01-11,niuzj,注释掉原来查询语句，
		 * 因为原查询语句要去左连接llbnf表，这样使得统计出来的数据与实际数据不符,一般都是数据数据的倍数（这与受益人个数有关） strSQL="
		 * select distinct
		 * t.otherno,t.polno,t.riskcode,t.subfeeoperationtype,sum(nvl(t.pay,0)), " +"
		 * t.feeoperationtype,t.feefinatype,m.payeename, m.payeeidno, " +"
		 * (select k.codename from ldcode k where
		 * trim(k.code)=trim(m.casepaymode) and k.codetype = 'llpaymode'), " +"
		 * (select riskshortname from lmrisk where riskcode = t.riskcode), " +"
		 * (select max(b.name) from llcase a,ldperson b where a.caseno=t.otherno
		 * and a.customerno=b.customerno), " +" (select k.codename from
		 * llclaimuwmain j,ldcode k where j.clmno=t.otherno and
		 * j.auditconclusion=k.code and k.codetype='llclaimconclusion'), " +"
		 * (select max(accidentdate) from llregister where rgtno=t.otherno), " +"
		 * (select max(accdate) from llcase where caseno=t.otherno), " +"
		 * (select max(examdate) from llclaimuwmain where clmno=t.otherno), " +"
		 * (select (case count(1) when 0 then '未亡' else '死亡' end) from
		 * llappclaimreason l where l.caseno = t.otherno and l.reasoncode in
		 * ('102','202')), " +" (select auditper from llclaimuwmain where
		 * clmno=t.otherno), " +" (select d.username from llclaimuser
		 * d,llclaimuwmain e where e.clmno=t.otherno and e.auditper=d.usercode), " +"
		 * (select max(confdate) from ljagetclaim where otherno=t.otherno and
		 * polno=t.polno), " +" (select distinct subbaltypedesc from
		 * llbalancerela where subbaltype=t.subfeeoperationtype and
		 * baltype=t.feeoperationtype), " +" from llregister f,ljagetclaim t " +"
		 * left join llbnf m on m.clmno=t.otherno and m.polno=t.polno " +" where
		 * f.rgtno = t.otherno " +" and f.mngcom like '" + mStatiCode +"%' " +"
		 * and t.othernotype='5' "//赔案号 +" and t.opconfirmdate between '" +
		 * mStartDate + "' and '" + mEndDate + "' ";
		 */
		/**
		 * niuzj,2006-01-11,重写查询SQL语句
		 */
		// 实付号码，赔案号，保单号，险种号，子业务类型
		strSQL = " select distinct t.actugetno,t.otherno,t.polno,t.riskcode,t.subfeeoperationtype, "
				// 金额，业务类型，财务类型
				+ " sum(case when t.pay is not null then t.pay else 0 end),t.feeoperationtype,t.feefinatype, "
				// 险种简称
				+ " (select riskshortname from lmrisk where riskcode = t.riskcode), "
				// 出险人姓名
				+ " (select max(b.name) from llcase a,ldperson b where a.caseno=t.otherno and a.customerno=b.customerno), "
				// 审核结论
				+ " (select k.codename from llclaimuwmain j,ldcode k  where j.clmno=t.otherno and j.auditconclusion=k.code and k.codetype='llclaimconclusion'),  "
				// 意外事故发生日期
				+ " (select max(accidentdate) from llregister where rgtno=t.otherno), "
				// 出险日期
				+ " (select max(accdate) from llcase where caseno=t.otherno), "
				// 审批通过日期
				+ " (select max(examdate) from llclaimuwmain  where clmno=t.otherno), "
				// 案件标识(如:死亡标识)
				+ " (select (case count(1) when 0 then '未亡' else '死亡' end) from llappclaimreason l where l.caseno = t.otherno and l.reasoncode in ('102','202')), "
				// 审核人代码
				+ " (select auditper from llclaimuwmain  where clmno=t.otherno), "
				// 审核人姓名
				+ " (select d.username from llclaimuser d,llclaimuwmain e where e.clmno=t.otherno and e.auditper=d.usercode), "
				// 实付日期
				+ " (select max(confdate) from ljagetclaim where otherno=t.otherno and polno=t.polno), "
				// 项目名称（业务类型名称）
				+ " (select distinct subbaltypedesc from llbalancerela where subbaltype=t.subfeeoperationtype and baltype=t.feeoperationtype), "
				// 划款银行名称 Modify by zhaorx 2006-09-05(关联的银行可能有多个，取一个即可。)
				+ " (select max(s.bankname) from ljaget r ,ldbank s where r.actugetno=t.actugetno and r.bankcode=s.bankcode), "
				// 划款账号
				+ " (select r.bankaccno from ljaget r where r.actugetno=t.actugetno) ";

		/**
		 * 修改：niuzj,2006-01-16
		 * 原因：原来对财务科目的理解有误，致使当财务科目取10/11/12时统计出来的数据和“理赔日结”统计出来的数据不符
		 * 当财务科目选10/11时，应该统计的是长期险；当财务科目选12时，应该统计的是短期险
		 */
		// Modify by zhaorx 2006-08-24 将lirisktype全部换成lmriskapp表。
		if (mFinDayType.equals("10") || mFinDayType.equals("11")) {
			strSQL = strSQL + " from ljagetclaim t,lmriskapp u "
					+ " where 1 = 1 " + " and t.riskcode = u.riskcode "
					+ " and u.riskperiod='L' ";
		} else if (mFinDayType.equals("12")) {
			strSQL = strSQL + " from ljagetclaim t,lmriskapp u "
					+ " where 1 = 1 " + " and t.riskcode = u.riskcode "
					+ " and u.riskperiod !='L'";

		} else {
			strSQL = strSQL + " from ljagetclaim t " + " where 1=1 ";
		}

		strSQL = strSQL + " and t.managecom like concat('" + "?staticode?"
				+ "','%') "
				+ " and t.othernotype='5' "// 赔案号
				+ " and t.opconfirmdate between '" + "?startdate?" + "' and '"
				+ "?enddate?" + "' ";

		// 拼入财务科目条件
		if (mFinDayType == null || mFinDayType.equals("")) {
			strSQL = strSQL;
		} else {
			String tFinType = SQLFinDayType(mFinDayType);
			strSQL = strSQL + tFinType;
			/**
			 * 去掉这个限制条件，把它加到函数SQLFinDayType（）对红利的限制中去，by niuzj,2006-02-09
			 * //对于拒赔案件，其终了红利统计到财务科目04中，by niuzj,2005-12-12
			 * if(mFinDayType.equals("04")) { //strSQL = strSQL +" and
			 * llclaimpolicy.givetype='1' "; //针对90000014694赔案做的修改,by
			 * niuzj,2005-12-17 strSQL = strSQL +" and t.polno not in(select
			 * p.polno from llclaimpolicy p " +" where p.clmno=t.otherno and
			 * p.polno=t.polno and p.givetype='0') "; } else
			 * if(mFinDayType.equals("10") || mFinDayType.equals("11") ||
			 * mFinDayType.equals("12")) { //strSQL = strSQL +" and
			 * llclaimpolicy.givetype='0' "; strSQL = strSQL +" and t.polno not
			 * in(select p.polno from llclaimpolicy p " +" where
			 * p.clmno=t.otherno and p.polno=t.polno and p.givetype='1') "; }
			 * else { strSQL = strSQL ; }
			 */
		}

		// 拼入申请类型
		strSQL = strSQL + tNCLType;
		// 加上分组条件
		// strSQL = strSQL + "group by
		// t.otherno,t.polno,t.riskcode,t.feeoperationtype,t.subfeeoperationtype,t.feefinatype,m.payeename,m.payeeidno,m.casepaymode,t.actugetno
		// ";
		strSQL = strSQL
				+ "group by t.actugetno,t.otherno,t.polno,t.riskcode,t.feeoperationtype,t.subfeeoperationtype,t.feefinatype ";
		// 拼入排序条件
		if (mStatiOpti.equals("1")) {
			strSQL = strSQL + " order by 14,t.otherno "; // 用列数排序
		} else if (mStatiOpti.equals("2")) {
			strSQL = strSQL + " order by t.riskcode,t.otherno ";
		} else if (mStatiOpti.equals("3")) {
			// strSQL = strSQL + " order by
			// '"+tFinDayType+"',t.riskcode,t.otherno ";
			strSQL = strSQL + " order by t.feefinatype,t.riskcode,t.otherno ";
		}

		logger.debug("最后的查询语句:strSQL==" + strSQL);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("staticode", mStatiCode);
		sqlbv.put("startdate", mStartDate);
		sqlbv.put("enddate", mEndDate);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		if (tSSRS.getMaxRow() != 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				String tAcGetNo = tSSRS.GetText(i, 1); // 实付号
				String tClmNo = tSSRS.GetText(i, 2); // 赔案号
				String tPolNo = tSSRS.GetText(i, 3); // 保单号

				String tFinType = tSSRS.GetText(i, 8); // 财务类型
				String tFeeType = tSSRS.GetText(i, 7); // 业务类型
				String tSubFeeType = tSSRS.GetText(i, 5); // 子业务类型

				if (mFinDayType == null || mFinDayType.equals("")) {
					// 前台传入财务科目为空时,调用StrFinDayType()方法去查询
					String ttFinDayType = StrFinDayType(tFinType, tFeeType,
							tSubFeeType); // 财务科目代码
					if (ttFinDayType == null || ttFinDayType.equals("")) {
						tFinDayType = "其它财务科目";
					} else {
						String strSql1 = " select codename from ldcode where codetype='acceptatonement' and code='"
								+ "?code?" + "' ";
						logger.debug("strSql1==" + strSql1);
						SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
						sqlbv1.sql(strSql1);
						sqlbv1.put("code", ttFinDayType);
						ExeSQL ttcusExeSQL1 = new ExeSQL();
						tFinDayType = ttcusExeSQL1.getOneValue(sqlbv1); // 财务科目名称
					}
				} else {
					String strSql2 = " select codename from ldcode where codetype='acceptatonement' and code='"
							+ "?code?" + "' ";
					logger.debug("strSql2==" + strSql2);
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(strSql2);
					sqlbv2.put("code", mFinDayType);
					ExeSQL ttcusExeSQL2 = new ExeSQL();
					tFinDayType = ttcusExeSQL2.getOneValue(sqlbv2);
				}

				// 领款人，领款人身份证号,支付方式代码
				String strSql3 = " select distinct m.payeename, m.payeeidno,m.casepaymode from llbnf m where 1=1 "
						+ " and m.clmno ='"
						+ "?clmno?"
						+ "' "
						+ " and m.polno ='"
						+ "?polno?" + "' " + " and m.otherno ='" + "?otherno?" + "' ";
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(strSql3);
				sqlbv3.put("clmno", tClmNo);
				sqlbv3.put("polno", tPolNo);
				sqlbv3.put("otherno", tAcGetNo);
				ExeSQL cusExeSQL3 = new ExeSQL();
				SSRS tSSRS3 = new SSRS();
				tSSRS3 = cusExeSQL3.execSQL(sqlbv3);
				String tPayeeName = "";
				String tPayeeIdno = "";
				String tPayMode = "";
				if (tSSRS3.getMaxRow() > 0) {
					tPayeeName = tSSRS3.GetText(1, 1);
					tPayeeIdno = tSSRS3.GetText(1, 2);
					tPayMode = tSSRS3.GetText(1, 3);
				}

				// 支付方式名称
				String strSql4 = " select k.codename from ldcode k where 1=1 "
						+ " and k.code ='" + "?code?" + "' "
						+ " and k.codetype = 'llpaymode' ";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(strSql4);
				sqlbv4.put("code", tPayMode);
				ExeSQL cusExeSQL4 = new ExeSQL();
				String tPayModeName = cusExeSQL4.getOneValue(sqlbv4);

				String[] stra = new String[21];
				stra[0] = tSSRS.GetText(i, 2); // 赔案号
				stra[1] = tSSRS.GetText(i, 3); // 保单号
				stra[2] = tSSRS.GetText(i, 10); // 出险人姓名
				stra[3] = tSSRS.GetText(i, 4); // 给付险种代码
				stra[4] = tSSRS.GetText(i, 9); // 险种简称
				stra[5] = tSSRS.GetText(i, 11); // 审核结论
				stra[6] = tSSRS.GetText(i, 12); // 意外事故发生日期
				stra[7] = tSSRS.GetText(i, 13); // 出险日期
				stra[8] = tSSRS.GetText(i, 14); // 审批通过日期
				stra[9] = tFinDayType; // 财务科目
				stra[10] = tSSRS.GetText(i, 19); // 项目名称,业务类型名称
				stra[11] = tSSRS.GetText(i, 6); // 给付金额
				stra[12] = tPayeeName; // 领款人
				stra[13] = tPayeeIdno; // 领款人身份证号
				stra[14] = tSSRS.GetText(i, 15); // 案件标识(如:死亡标识)
				stra[15] = tPayModeName; // 支付方式
				stra[16] = tSSRS.GetText(i, 16); // 审核人代码
				stra[17] = tSSRS.GetText(i, 17); // 审核人姓名
				stra[18] = tSSRS.GetText(i, 18); // 实付日期
				stra[19] = tSSRS.GetText(i, 20); // 划款银行名称
				stra[20] = tSSRS.GetText(i, 21); // 划款账号

				tListTable.add(stra);
				String tMoney = tSSRS.GetText(i, 6);
				tStatCount = tStatCount + 1; // 记录数量加1
				tStatMoney = tStatMoney + Double.parseDouble(tMoney); // 统计总金额
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLPRBDailyBalanceBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询，是为单个变量赋值-----------");
		// 统计日期:$=/CFCDDate$
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		// 审批通过起期:$=/CFCDStaTime$
		String tStartDate = mStartDate;
		// 审批通过止期:$=/CFCDEndTime$
		String tEndDate = mEndDate;
		// 财务科目:$=/CFCDStatFinSeri$
		String tStatFinSeri = "";
		if (mFinDayType == null || mFinDayType.equals("")) {
			tStatFinSeri = "全部财务科目";
		} else {
			LDCodeSchema tLDCodeSchema = new LDCodeSchema();
			LDCodeDB tLDCodeDB = new LDCodeDB();
			tLDCodeDB.setCodeType("acceptatonement");
			tLDCodeDB.setCode(mFinDayType);
			tLDCodeDB.getInfo();
			tLDCodeSchema = tLDCodeDB.getSchema();
			tStatFinSeri = tLDCodeSchema.getCodeName();
		}

		// 排序选项:$=/CFCDOrder$
		String tStatiOpti = "";
		if (mStatiOpti.equals("1")) {
			tStatiOpti = "审批通过日期＋赔案号";
		} else if (mStatiOpti.equals("2")) {
			tStatiOpti = "险种号＋赔案号";
		} else if (mStatiOpti.equals("3")) {
			tStatiOpti = "科目＋险种＋赔案号";
		}

		// 统计机构:$=/CFCDStatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mStatiCode);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		// 申请类型判断
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 ";
		tTextTag.add("CFCDStatMngcom", tMngCom); // 统计机构
		tTextTag.add("CFCDDate", SysDate); // 统计日期
		tTextTag.add("CFCDStaTime", tStartDate); // 审批通过起期
		tTextTag.add("CFCDEndTime", tEndDate); // 审批通过止期
		tTextTag.add("CFCDStatFinSeri", tStatFinSeri); // 财务科目
		tTextTag.add("PYNApplType", tApplTypeName); // 申请类型$=/PYNApplType$
		tTextTag.add("CFCDOrder", tStatiOpti); // 排序选项
		tTextTag.add("CFCDStatSum", tStatCount); // 统计总数量
		tTextTag.add("CFCDStatMoneySum", new DecimalFormat("0.00")
				.format(tStatMoney)); // 统计总金额

		logger.debug("********************************************");
		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		// /*##################### 后台调试部分，生成临时文件############################*/
		// String strTemplatePath="E:/lis/ui/f1print/NCLtemplate/";
		// String strVFPathName=strTemplatePath+"test.vts"; //生成临时文件名
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(tXmlExport.getInputStream(),
		// strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);

		logger.debug("********************************************");
		logger.debug("---以下 将tXmlExport打包，返回前台--");
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
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
			return false;
		}
		return true;

	}

	// 错误处理函数
	public CErrors getErrors() {
		return mErrors;
	}

	// 打包数据---用于向前台返回
	public VData getResult() {
		return mResult;
	}

}
