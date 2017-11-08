package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.pubfun.ZHashReport;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
import com.sinosoft.utility.XmlExportNew;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Hezy Lys
 * @version 1.0
 * @date:2003-05-28
 */

public class FinDayCheckBL {
private static Logger logger = Logger.getLogger(FinDayCheckBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String mDay[] = null; // 取得的时间
	private String mOpt = ""; // 接收收费的类型：暂收OR预收
	private String mChargeType = ""; // 将收费类型转换成汉字的显示
	private GlobalInput mGlobalInput = new GlobalInput();// 全局数据
	private double mAllsum;
	private int mAllcount;
	public FinDayCheckBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINTGET") && !cOperate.equals("PRINTPAY")&& !cOperate.equals("YingFu")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		if (!getInputData(cInputData))
			return false;
		mResult.clear();
		/***********************************************************************
		 * date:2003-05-28 author:lys function:判断收费的标志，若是Pay则是“暂收”、若是YSPay则是“预收”
		 */
		if (mOpt.equals("Pay")) {
			mChargeType = "暂收";
			logger.debug("收费类型是" + mChargeType);
		}
		if (mOpt.equals("YSPay")) {
			mChargeType = "预收";
			logger.debug("收费类型是" + mChargeType);
		}
		// 准备所有要打印的数据
		if (cOperate.equals("PRINTGET")) // 打印付费
		{
			if (mOpt.equals("GetMode")) {
				if (!getPrintDataGetMode()) {
					return false;
				}
			}else if(mOpt.equals("YWSF"))//实付
			{
				if(!getPrintDataPayYWSF()){
					return false;
				}
			}
			
//			else{			if (!getPrintDataGet()) {	return false;	}}
		}
		if (cOperate.equals("YingFu")) {//打印应付
			if (mOpt.equals("PKZC")) {
				if (!PrintPKZC()) {
					return false;
				}
			} else if (mOpt.equals("BQYF")) {//保全应付
				if (!PrintBQYF()) {
					return false;
				}
			} else if (mOpt.equals("LQJF")) {//领取给付
				if (!PrintLQJF()) {
					return false;
				}
			} else if (mOpt.equals("QTYF")) {//其它应付
				if (!PrintQTYF()) {
					return false;
				}
			} else {
				CError.buildErr(this, "不支持的操作字符串");
				return false;
			}
		}
		
		if (cOperate.equals("PRINTPAY")) // 打印收费
		{
			if (mOpt.equals("Pay")) {//暂收
				if (!getPrintDataPay()) {
					return false;
				}
			} else if(mOpt.equals("YSPay")){//预收
				if (!getPrintDataPay1()) {
					return false;
				}
			}else if(mOpt.equals("HeBao"))//保费收入
			{
				if(!getPrintDataPayHebao()){
					return false;
				}
			}
//			else if(mOpt.equals("BQSF"))//保费收入
//			{
//				if(!getPrintDataPayBQSF()){
//					return false;
//				}
//			}
			else if(mOpt.equals("PayMode"))
			{
				if(!getPrintDataPayMode()){
					return false;
				}
			}else if(mOpt.equals("AirPay"))
			{
				if(!getPrintDataPayAir()){
					return false;
				}
			}
			else if(mOpt.equals("GLFY"))
			{
				if(!getPrintDataGLFY()){
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		FinDayCheckBL tbl = new FinDayCheckBL();
		VData tVData = new VData();
		String[] days = { "2006-1-1", "2001-11-2" };
		tVData.add(days);
		GlobalInput tG = new GlobalInput();
		// tG.TempFeeType = '1';
		tG.ManageCom = "86";
		tVData.add(tG);
		tbl.submitData(tVData, "PRINTPAY");

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
	private boolean getInputData(VData cInputData) // 打印付费
	{
		mDay = (String[]) cInputData.get(0);
		mOpt = (String) cInputData.get(1);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		logger.debug("2003-05-28  BL ");
		logger.debug("接收的收费标志是" + mOpt);
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "FinDayCheckBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean getPrintDataPay() {
		SSRS tSSRS = new SSRS();
		SSRS nSSRS = new SSRS();
		double SumMoney = 0;
		long SumNumber = 0;
		String strArr[] = null;
		//XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
		//xmlexport.createDocument("FinDayCheckPayNew.vts", "printer");// 最好紧接着就初始化xml文档
		XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例
		xmlexport.createDocument("暂收日结打印", "", "", "0");
		ListTable tlistTable = new ListTable();
		ExeSQL tExeSQL = new ExeSQL();
		/***********************************************************************
		 * 对msql的说明：LJTempFee：（暂交费表） 对TempFeeType的说明：首期和续期两种（1,5,6首期、0,2,3,4续期）
		 * 0 ---直接交费,新生成 1 ---新单交费，录入暂交费收据号 2 ---续期催收交费，录入催收号 3 ---续期非催收交费，新生成 4
		 * ---保全交费，新生成 5 ---银行代扣（只是针对个险非银行险首期银行代扣） 6 ---定额单缴费
		 * 统计出在特定的时间段、管理机构的同一险种的交费金额的和。和每一险种的个数。
		 */
		// //////////////////////////修改group by 条件，以险种分组 hb
		// 2007-11-8////////////////////////////////
		String msql = "";
		logger.debug("执行的是暂收费操作！！！");
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		msql = "select RiskCode,(case when sum(PayMoney) is not null then sum(PayMoney)  else 0 end),sum(1),(select riskname from LMRisk where riskcode = LJTempFee.riskcode) "
				+ "from LJTempFee  where Confmakedate>='" + "?date1?"	+ "' and Confmakedate<='" + "?date2?" + "' " 
				+ "and ManageCom like concat('" + "?ManageCom?" + "','%') and TempFeeType = '1' ";
		String tGrpSql = "  Group By RiskCode";
		// //////////////////////////修改group by 条件，以险种分组 hb
		// 2007-11-8////////////////////////////////
		msql += tGrpSql;
		tlistTable.setName("RISK");
		sqlbv1.sql(msql);
		sqlbv1.put("date1", mDay[0]);
		sqlbv1.put("date2", mDay[1]);
		sqlbv1.put("ManageCom", mGlobalInput.ManageCom);
		tSSRS = tExeSQL.execSQL(sqlbv1);
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			strArr = new String[5];
			// 险种
			strArr[0] = tSSRS.GetText(i, 1);
			// if (tSSRS.GetText(i, 4).equals(""))
			// strArr[0] = tSSRS.GetText(i, 1);
			// else
			// strArr[0] = tSSRS.GetText(i, 4);

			// 渠道 险种
			strArr[1] = tSSRS.GetText(i, 4);

			strArr[2] = "";

			// 金额
			strArr[3] = tSSRS.GetText(i, 2);
			SumMoney = SumMoney + Double.parseDouble(strArr[3]);
			String strSum = new DecimalFormat("0.00").format(Double
					.valueOf(strArr[3]));
			strArr[3] = strSum;

			// 件数
			strArr[4] = tSSRS.GetText(i, 3);
			SumNumber = SumNumber + Long.valueOf(strArr[4]).longValue();

			tlistTable.add(strArr);
		}
		strArr = new String[5];
		strArr[0] = "";
		strArr[1] = "Risk";
		strArr[2] = "0.00";
		strArr[3] = "Money";
		strArr[4] = "Mult";
		xmlexport.addListTable(tlistTable, strArr);

		String tSumMoney = String.valueOf(SumMoney);
		tSumMoney = new DecimalFormat("0.00").format(Double.valueOf(tSumMoney));
		strArr = new String[5];
		strArr[0] = "";
		strArr[1] = "Risk";
		strArr[2] = "0.00";
		strArr[3] = "Money";
		strArr[4] = "Mult";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		String nsql = "select Name from LDCom where ComCode='"
				+ "?ManageCom?" + "'";
		sqlbv2.sql(nsql);
		sqlbv2.put("ManageCom", mGlobalInput.ManageCom);
		ExeSQL nExeSQL = new ExeSQL();
		nSSRS = nExeSQL.execSQL(sqlbv2);
		String manageCom = nSSRS.GetText(1, 1);

		TextTag texttag = new TextTag();// 新建一个TextTag的实例
		texttag.add("StartDate", mDay[0]);
		texttag.add("EndDate", mDay[1]);
		texttag.add("ManageCom", manageCom);
		texttag.add("SumMoney", tSumMoney);
		texttag.add("SumNumber", SumNumber);
		if (texttag.size() > 0)
			xmlexport.addTextTag(texttag);
		// xmlexport.outputDocumentToFile("e:\\","FinDayCheckBL.xml");//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	private boolean getPrintDataPay1() {
		SSRS tSSRS = new SSRS();
		SSRS nSSRS = new SSRS();
		double SumMoney = 0;
		long SumNumber = 0;
		String strArr[] = null;
		XmlExportNew xmlexport = new XmlExportNew();// 新建一个XmlExport的实例
		//xmlexport.createDocument("FinDayCheckPayYSNew.vts", "printer");// 最好紧接着就初始化xml文档
		xmlexport.createDocument("预收日结打印", "", "", "0");

		ListTable tlistTable = new ListTable();
		ExeSQL tExeSQL = new ExeSQL();
		/***********************************************************************
		 * 对msql的说明：LJTempFee：（暂交费表） 对TempFeeType的说明：首期和续期两种（1,5,6首期、0,2,3,4续期）
		 * 0 ---直接交费,新生成 1 ---新单交费，录入暂交费收据号 2 ---续期催收交费，录入催收号 3 ---续期非催收交费，新生成 4
		 * ---保全交费，新生成 5 ---银行代扣（只是针对个险非银行险首期银行代扣） 6 ---定额单缴费
		 * 统计出在特定的时间段、管理机构的同一险种的交费金额的和。和每一险种的个数。
		 */
		String msql = "";
		logger.debug("执行的是预收操作！！！");
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		msql =  "select type, riskcode,riskname,sum(money),sum(num) from ( select RiskCode riskcode ,(case when sum(PayMoney) is not null then sum(PayMoney)  else 0 end) money,sum(1) num, "
				+ "(select riskname from LMRisk where riskcode = a.riskcode) riskname, "
				+ "(case when (select codename from ldcode where codetype='tempfeetype' and code=trim(a.tempfeetype)) is not null then (select codename from ldcode where codetype='tempfeetype' and code=trim(a.tempfeetype))  else '其他' end) type	 "
				+ "from LJTempFee a  "
				+ "where Confmakedate >= '"+"?date1?"+"'	 and Confmakedate <= '"+"?date2?"+"'    "
				+ "and tempfeetype<>'1'	 and ManageCom like concat('"+"?ManageCom?"+"','%')    "
				+ "Group By RiskCode,tempfeetype "
				+ "union all	 "
				+ "select riskcode riskcode,(case when sum(sumactupaymoney) is not null then sum(sumactupaymoney)  else 0 end) money,sum(1) num, "
				+ "(select riskname from LMRisk where riskcode = a.riskcode) riskname,'预收'	 type "
				+ "from LJAPayPerson a  "
				+ "where ConfDate >= '"+"?date1?"+"' and ConfDate <= '"+"?date2?"+"'  "
				+ "and ManageCom like concat('"+"?ManageCom?"+"','%') and paytype = 'YET' and PayCount = 1  "
				+ "Group By RiskCode "
				+ "union all	 "
				+ "select riskcode riskcode,(case when sum(sumactupaymoney) is not null then sum(sumactupaymoney)  else 0 end) money,sum(1) num, "
				+ "(select riskname from LMRisk where riskcode = a.riskcode) riskname,'预收' type	 "
				+ "from LJAPayGrp a  "
				+ "where ConfDate >= '"+"?date1?"+"'		 and ConfDate <= '"+"?date2?"+"'  "
				+ "and ManageCom like concat('"+"?ManageCom?"+"','%') and paytype = 'YET' and PayCount = 1  "
				+ "Group By RiskCode ) g "
				+ "group by  type, riskcode,riskname order by  type, riskcode,riskname";
		tlistTable.setName("RISK");
		sqlbv3.sql(msql);
		sqlbv3.put("date1", mDay[0]);
		sqlbv3.put("date2", mDay[1]);
		sqlbv3.put("ManageCom", mGlobalInput.ManageCom);
		tSSRS = tExeSQL.execSQL(sqlbv3);
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			strArr = new String[5];
			// 收费类型
			strArr[0] = tSSRS.GetText(i, 1);
			// 险种编码
			strArr[1] = tSSRS.GetText(i, 2);
				// 险种名称
			strArr[2] = tSSRS.GetText(i, 3);
			// 金额
			strArr[3] = tSSRS.GetText(i, 4);
			SumMoney = SumMoney + Double.parseDouble(strArr[3]);
			String strSum = new DecimalFormat("0.00").format(Double
					.valueOf(strArr[3]));
			strArr[3] = strSum;

			// 件数
			strArr[4] = tSSRS.GetText(i, 5);
			SumNumber = SumNumber + Long.valueOf(strArr[4]).longValue();

			tlistTable.add(strArr);
		}
		strArr = new String[5];
		strArr[0] = "";
		strArr[1] = "Risk";
		strArr[2] = "";
		strArr[3] = "Money";
		strArr[4] = "Mult";
		xmlexport.addListTable(tlistTable, strArr);

		String tSumMoney = String.valueOf(SumMoney);
		tSumMoney = new DecimalFormat("0.00").format(Double.valueOf(tSumMoney));
//		strArr = new String[5];
//		strArr[0] = "";
//		strArr[1] = "Risk";
//		strArr[2] = "";
//		strArr[3] = "Money";
//		strArr[4] = "Mult";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		String nsql = "select Name from LDCom where ComCode='"
				+ "?ManageCom?" + "'";
		sqlbv4.sql(nsql);
		sqlbv4.put("ManageCom", mGlobalInput.ManageCom);
		ExeSQL nExeSQL = new ExeSQL();
		nSSRS = nExeSQL.execSQL(sqlbv4);
		String manageCom = nSSRS.GetText(1, 1);

		TextTag texttag = new TextTag();// 新建一个TextTag的实例
		texttag.add("StartDate", mDay[0]);
		texttag.add("EndDate", mDay[1]);
		texttag.add("ManageCom", manageCom);
		texttag.add("SumMoney", tSumMoney);
		texttag.add("SumNumber", SumNumber);
		if (texttag.size() > 0)
			xmlexport.addTextTag(texttag);
		// xmlexport.outputDocumentToFile("e:\\","FinDayCheckBL.xml");//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	/***************************************************************************
	 * 准备付费的打印数据 date：2003-05-28 author：lys table：实付总表（LJAGet）
	 */
	private boolean getPrintDataGet() {
		/***********************************************************************
		 * 变量说明 CS：次数 F : 分红 T: 投连 C：传统 JE:金额
		 **********************************************************************/
		// 总合计
		int CS = 0;
		double JE = 0;

		// 年金
		int NCS = 0;
		int NFCS = 0;
		int NTCS = 0;
		int NCCS = 0;

		double NJE = 0;
		double NFJE = 0;
		double NTJE = 0;
		double NCJE = 0;

		// 满期
		int MCS = 0;
		int MFCS = 0;
		int MTCS = 0;
		int MCCS = 0;

		double MJE = 0;
		double MFJE = 0;
		double MTJE = 0;
		double MCJE = 0;

		// 理赔
		int LCS = 0;
		int LFCS = 0;
		int LTCS = 0;
		int LCCS = 0;

		double LJE = 0;
		double LFJE = 0;
		double LTJE = 0;
		double LCJE = 0;

		// 退保金---犹豫期撤单
		int TCS = 0;
		int TFCS = 0;
		int TTCS = 0;
		int TCCS = 0;

		double TJE = 0;
		double TFJE = 0;
		double TTJE = 0;
		double TCJE = 0;

		// 退保金---退保金
		double T2JE = 0;
		double T2FJE = 0;// 分红金额
		double T2TJE = 0;// 投连金额
		double T2CJE = 0;// 传统金额

		// 批改补退费的其他项目
		// double PQJE = 0;
		// double PQFJE = 0;//分红金额
		// double PQTJE = 0;//投连金额
		// double PQCJE = 0;//传统金额

		// 借款的相关金额
		double JKJE = 0;// 借款的总金额
		double JKFJE = 0;// 借款的分红金额
		double JKTJE = 0;// 借款的投连金额
		double JKCJE = 0;// 借款的传统金额

		// 退费的相关金额
		double TuiFJE = 0;// 退费的总金额
		double TuiFFJE = 0;// 退费的分红金额
		double TuiFTJE = 0;// 退费的投连金额
		double TuiFCJE = 0;// 退费的传统金额

		// 余额的相关金额
		double EYJE = 0;// 余额的总金额
		double EYFJE = 0;// 余额的分红金额
		double EYTJE = 0;// 余额的投连金额
		double EYCJE = 0; // 余额的传统金额

		// 还款的相关金额
		double HKJE = 0; // 还款的总金额
		double HKFJE = 0;// 还款的分红金额
		double HKTJE = 0;// 还款的投连金额
		double HKCJE = 0;// 还款的传统金额

		// 利息的相关金额
		double LXJE = 0;// 利息的总金额
		double LXFJE = 0;// 利息的分红金额
		double LXTJE = 0;// 利息的投连金额
		double LXCJE = 0;// 利息的传统金额

		// 红利
		int HCS = 0;
		int HFCS = 0;
		int HTCS = 0;
		int HCCS = 0;

		double HJE = 0;
		double HFJE = 0;
		double HTJE = 0;
		double HCJE = 0;

		// 暂收保费
		int ZCS = 0;
		int ZFCS = 0;
		int ZTCS = 0;
		int ZCCS = 0;

		double ZJE = 0;
		double ZFJE = 0;
		double ZTJE = 0;
		double ZCJE = 0;

		// 其他
		int QCS = 0;
		int QFCS = 0;
		int QTCS = 0;
		int QCCS = 0;

		double QJE = 0;
		double QFJE = 0;
		double QTJE = 0;
		double QCJE = 0;

		// 垫交
		double DJE = 0;
		double DFJE = 0;
		double DTJE = 0;
		double DCJE = 0;

		// 还垫
		double HDJE = 0;
		double HDFJE = 0;
		double HDTJE = 0;
		double HDCJE = 0;

		// 工本费
		double GBJE = 0;
		double GBFJE = 0;
		double GBTJE = 0;
		double GBCJE = 0;

		XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
		xmlexport.createDocument("FinDayCheckGet.vts", "printer");// 最好紧接着就初始化xml文档
		/***********************************************************************
		 * 在实付总表中查询出该时间段内的实付号码和其他号码类型. 根据号码类型来判断所要查的表 0 ---表示生存领取对应的合同号 1
		 * ---表示生存领取对应的集体保单号 2 ---表示生存领取对应的个人保单号 3 ---表示批改号 4
		 * ---暂交费退费,对应暂交费退费表的给付通知书号 5 ---表示赔付应收表中的给付通知书号（就是赔案号） 6
		 * ---表示其他退费的给付通知书号码(对应号码添个人保单号） 7 ---表示红利对应的个人保单号。 8
		 * ---表示其他退费的给付通知书号码(对应号码填团体保单号）
		 */

		// 分红查询的sql语句
		String FenHong_sql = " and RiskCode in ( Select RiskCode From LMRiskApp"
				+ " Where RiskType3 = '2')";
		// 投连查询的sql语句
		String TouLian_sql = " and RiskCode in ( Select RiskCode From LMRiskApp "
				+ " Where RiskType3 = '3')";
		// 传统查询的sql语句
		String ChuanTong_sql = " and RiskCode in ( Select RiskCode From LMRiskApp"
				+ " Where RiskType3 in ('1','4','5'))";
		logger.debug("￥￥分红" + FenHong_sql);
		logger.debug("投连" + TouLian_sql);
		logger.debug("传统" + ChuanTong_sql);
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		String ActuGetNo_sql = "Select ActuGetNo,OtherNoType From LJAGet Where ConfDate >= '"
				+ "?date1?"
				+ "' and ConfDate <= '"
				+ "?date2?"
				+ "' and ManageCom like concat('" + "?ManageCom?" + "','%')";
		logger.debug("查询出实付总表中的实付号码和类型的sql语句是");
		logger.debug(ActuGetNo_sql);
		sqlbv5.sql(ActuGetNo_sql);
		sqlbv5.put("date1", mDay[0]);
		sqlbv5.put("date2", mDay[1]);
		sqlbv5.put("ManageCom", mGlobalInput.ManageCom);
		ExeSQL t_exesql = new ExeSQL();
		SSRS t_ssrs = t_exesql.execSQL(sqlbv5);
		logger.debug("查询的总个数是" + t_ssrs.getMaxRow());
		if (t_ssrs.getMaxRow() == 0) {
			logger.debug("查询的结果是0");
			CError tError = new CError();
			tError.moduleName = "ShowBillBL";
			tError.functionName = "getDutyGetClmInfo";
			tError.errorMessage = "没有要打印的数据！！！";
			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("实付表中的实付号码条数是" + t_ssrs.getMaxRow());
		for (int i = 1; i <= t_ssrs.getMaxRow(); i++) {
			/*******************************************************************
			 * 对于三级科目中的信息（分红（2）、投连（3）、传统（1,4,5）） 年金和满期的情况是当OtherNoType in（0，1,2）
			 * FenHong_sql :分红类型的查询 TouLian_sql:投连类型的查询 ChuanTong_sql:传统类型的查询
			 * 年金的情况当OtherNoType in(0,1,2,9)时是生存领取表的情况
			 * 根据LJAGetDraw（生存领取）表中的GetDutyCode（给付责任编码）到LMDutyGet
			 * （给付责任表）中查询出GetType1（给付分类）--（0：满期、其余是年金）
			 */
			logger.debug("查询出的号码是" + t_ssrs.GetText(i, 1));
			logger.debug("查询出的类型是" + t_ssrs.GetText(i, 2));

			if ((t_ssrs.GetText(i, 2).equals("0"))
					|| (t_ssrs.GetText(i, 2).equals("1"))
					|| (t_ssrs.GetText(i, 2).equals("2"))
					|| (t_ssrs.GetText(i, 2).equals("9"))) {
				// 先判断是年金还是满期GetType1=0是满期；GetType1<>0是年金
				logger.debug("判断是年金还是满期");
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				String tType = " Select GetType1 From LMDutyGet Where GetDutyCode = "
						+ "( Select max(GetDutyCode) From LJAGetDraw Where ActuGetNo = '"
						+ "?ActuGetNo?" + "' )";
				sqlbv6.sql(tType);
				sqlbv6.put("ActuGetNo", t_ssrs.GetText(i, 1));
				ExeSQL tType_exesql = new ExeSQL();
				SSRS tType_ssrs = tType_exesql.execSQL(sqlbv6);
				if (tType_ssrs.getMaxRow() != 0) {
					logger.debug("年金、满期的判断标志是" + tType_ssrs.GetText(1, 1));
					if (tType_ssrs.GetText(1, 1).equals("0")) {
						SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
						// 满期分红的查询
						String ManQiFH_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetDraw Where ActuGetNo = '"
								+ "?ActuGetNo?"
								+ "' and  GetDutyCode in "
								+ "(Select GetDutyCode From LMDutyGet Where GetType1 = '0') "
								+ FenHong_sql;
						logger.debug("￥￥满期分红的查询" + ManQiFH_sql);
						sqlbv7.sql(ManQiFH_sql);
						sqlbv7.put("ActuGetNo", t_ssrs.GetText(i, 1));
						SSRS ManQiFH_ssrs = t_exesql.execSQL(sqlbv7);
						MFJE = MFJE
								+ Double
										.parseDouble(ManQiFH_ssrs.GetText(1, 1));
						SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
						// 满期投连的查询
						String ManQiTL_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetDraw Where ActuGetNo = '"
								+ "?ActuGetNo?"
								+ "' and  GetDutyCode in "
								+ "(Select GetDutyCode From LMDutyGet Where GetType1 = '0') "
								+ TouLian_sql;
						sqlbv8.sql(ManQiTL_sql);
						sqlbv8.put("ActuGetNo", t_ssrs.GetText(i, 1));
						logger.debug("￥￥满期投连的查询" + ManQiTL_sql);
						SSRS ManQiTL_ssrs = t_exesql.execSQL(sqlbv8);
						MTJE = MTJE
								+ Double
										.parseDouble(ManQiTL_ssrs.GetText(1, 1));

						// 满期传统的查询
						SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
						String ManQiCT_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end)  From LJAGetDraw Where ActuGetNo = '"
								+ "?ActuGetNo?"
								+ "' and  GetDutyCode in "
								+ "(Select GetDutyCode From LMDutyGet Where GetType1 = '0') "
								+ ChuanTong_sql;
						sqlbv9.sql(ManQiCT_sql);
						sqlbv9.put("ActuGetNo", t_ssrs.GetText(i, 1));
						logger.debug("￥￥满期传统的查询" + ManQiCT_sql);
						SSRS ManQiCT_ssrs = t_exesql.execSQL(sqlbv9);
						MCJE = MCJE
								+ Double
										.parseDouble(ManQiCT_ssrs.GetText(1, 1));
					} else {
						SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
						// 年金分红信息的查询
						String NianJinFH_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetDraw Where ActuGetNo = '"
								+ "?ActuGetNo?"
								+ "' and  GetDutyCode in "
								+ "(Select GetDutyCode From LMDutyGet Where GetType1 <> '0')"
								+ FenHong_sql;
						sqlbv10.sql(NianJinFH_sql);
						sqlbv10.put("ActuGetNo", t_ssrs.GetText(i, 1));
						logger.debug("对年金分红的查询");
						SSRS NianJinFH_ssrs = t_exesql.execSQL(sqlbv10);
						NFJE = NFJE
								+ Double.parseDouble(NianJinFH_ssrs.GetText(1,
										1));
						SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
						// 年金投连信息的查询
						String NianJinTL_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetDraw Where ActuGetNo = '"
								+ "?ActuGetNo?"
								+ "' and  GetDutyCode in "
								+ "(Select GetDutyCode From LMDutyGet Where GetType1 <> '0')"
								+ TouLian_sql;
						sqlbv11.sql(NianJinTL_sql);
						sqlbv11.put("ActuGetNo", t_ssrs.GetText(i, 1));
						logger.debug("年金投连的查询" + NianJinTL_sql);
						SSRS NianJinTL_ssrs = t_exesql.execSQL(sqlbv11);
						NTJE = NTJE
								+ Double.parseDouble(NianJinTL_ssrs.GetText(1,
										1));
						SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
						// 年金传统信息的查询
						String NianJinCT_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetDraw Where ActuGetNo = '"
								+ "?ActuGetNo?"
								+ "' and  GetDutyCode in "
								+ "(Select GetDutyCode From LMDutyGet Where GetType1 <> '0')"
								+ ChuanTong_sql;
						sqlbv12.sql(NianJinCT_sql);
						sqlbv12.put("ActuGetNo", t_ssrs.GetText(i, 1));
						logger.debug("对年金传统的查询" + NianJinCT_sql);
						SSRS NianJinCT_ssrs = t_exesql.execSQL(sqlbv12);
						NCJE = NCJE
								+ Double.parseDouble(NianJinCT_ssrs.GetText(1,
										1));
					}
				}
			}
			/*******************************************************************
			 * date:2003-05-29 author:lys 对于理赔的情况：当OtherNotype＝＝5时，表示理赔
			 * 在LJAGetClaim（赔付实付表）中进行数据的查询
			 * 
			 */

			if (t_ssrs.GetText(i, 2).equals("5")) {
				SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
				// 理赔分红
				String LiPeiFH_sql = "Select  (case when sum(Pay) is not null then sum(Pay)  else 0 end) From LJAGetClaim "
						+ "Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "'"
						+ FenHong_sql;
				logger.debug("理赔分红" + LiPeiFH_sql);
				sqlbv13.sql(LiPeiFH_sql);
				sqlbv13.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS LiPeiFH_ssrs = t_exesql.execSQL(sqlbv13);
				LFJE = LFJE + Double.parseDouble(LiPeiFH_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
				// 理赔投连
				String LiPeiTL_sql = "Select (case when sum(Pay) is not null then sum(Pay)  else 0 end) From LJAGetClaim "
						+ "Where ActuGetNo = '" + "?ActuGetNo?" + "'"
						+ TouLian_sql;
				logger.debug("理赔投连" + LiPeiTL_sql);
				sqlbv14.sql(LiPeiTL_sql);
				sqlbv14.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS LiPeiTL_ssrs = t_exesql.execSQL(sqlbv14);
				LTJE = LTJE + Double.parseDouble(LiPeiTL_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
				// 理赔传统
				String LiPeiCT_sql = "Select (case when sum(Pay) is not null then sum(Pay)  else 0 end)  From LJAGetClaim "
						+ "Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "'"
						+ ChuanTong_sql;
				logger.debug("理赔传统" + LiPeiCT_sql);
				sqlbv15.sql(LiPeiCT_sql);
				sqlbv15.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS LiPeiCT_ssrs = t_exesql.execSQL(sqlbv15);
				LCJE = LCJE + Double.parseDouble(LiPeiCT_ssrs.GetText(1, 1));
			}

			/*******************************************************************
			 * date:2003-05-29 author:lys function :查询退保金情况---犹豫期撤单的操作
			 * 对LJAGetEndorse（批改补退费表）进行查询信息。 OtherTypeNo ==3时是退保金情况
			 * FeeOperationType='WT'
			 ******************************************************************/
			if (t_ssrs.GetText(i, 2).equals("3")) {
				// 犹豫期撤单分红
				SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
				String TuiBaoJinFH_sql = " Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeOperationType='WT' and feefinatype='TF' "
						+ FenHong_sql;
				logger.debug("退保金分红情况" + TuiBaoJinFH_sql);
				sqlbv16.sql(TuiBaoJinFH_sql);
				sqlbv16.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS TuiBaoJinFH_ssrs = t_exesql.execSQL(sqlbv16);
				TFJE = TFJE
						+ Double.parseDouble(TuiBaoJinFH_ssrs.GetText(1, 1));
				logger.debug("当i= " + i + "时，退保金－分红-次数的合计是" + TFCS);
				SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
				// 犹豫期撤单投连
				String TuiBaoJinTL_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeOperationType='WT' and feefinatype='TF' "
						+ TouLian_sql;
				logger.debug("退保金投连" + TuiBaoJinTL_sql);
				sqlbv17.sql(TuiBaoJinTL_sql);
				sqlbv17.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS TuiBaoJinTL_ssrs = t_exesql.execSQL(sqlbv17);
				TTJE = TTJE
						+ Double.parseDouble(TuiBaoJinTL_ssrs.GetText(1, 1));
				logger.debug("当i= " + i + "时，退保金－投连-次数的合计是" + TTCS);

				// 犹豫期撤单传统
				SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
				String TuiBaoJinCT_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeOperationType='WT' and feefinatype='TF' "
						+ ChuanTong_sql;
				logger.debug("退保金传统" + TuiBaoJinCT_sql);
				sqlbv18.sql(TuiBaoJinCT_sql);
				sqlbv18.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS TuiBaoJinCT_ssrs = t_exesql.execSQL(sqlbv18);
				TCJE = TCJE
						+ Double.parseDouble(TuiBaoJinCT_ssrs.GetText(1, 1));
			}

			/*******************************************************************
			 * date:2003-05-29 author:lys function :查询退保金情况---退保金的操作
			 * 对LJAGetEndorse（批改补退费表）进行查询信息。 OtherTypeNo ==3时是退保金情况
			 * FeeOperationType!='WT' FeeFinaType='TB'
			 ******************************************************************/
			if (t_ssrs.GetText(i, 2).equals("3")) {
				SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				// 退保金分红
				String TuiBaoJin2FH_sql = " Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType='TB' and FeeOperationType <>'WT' "
						+ FenHong_sql;
				logger.debug("退保金分红情况" + TuiBaoJin2FH_sql);
				sqlbv19.sql(TuiBaoJin2FH_sql);
				sqlbv19.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS TuiBaoJin2FH_ssrs = t_exesql.execSQL(sqlbv19);
				T2FJE = T2FJE
						+ Double.parseDouble(TuiBaoJin2FH_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
				// 退保金投连
				String TuiBaoJin2TL_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType='TB' and FeeOperationType <>'WT' "
						+ TouLian_sql;
				sqlbv20.sql(TuiBaoJin2TL_sql);
				sqlbv20.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS TuiBaoJin2TL_ssrs = t_exesql.execSQL(sqlbv20);
				T2TJE = T2TJE
						+ Double.parseDouble(TuiBaoJin2TL_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
				// 退保金传统
				String TuiBaoJin2CT_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType='TB' and FeeOperationType <>'WT' "
						+ ChuanTong_sql;
				sqlbv21.sql(TuiBaoJin2CT_sql);
				sqlbv21.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS TuiBaoJin2CT_ssrs = t_exesql.execSQL(sqlbv21);
				T2CJE = T2CJE
						+ Double.parseDouble(TuiBaoJin2CT_ssrs.GetText(1, 1));
			}

			/*******************************************************************
			 * Date :2003-12-15 Author :LiuYansong Function :查询是余额的情况
			 * OtherTypeNo ==3时是退保金情况 FeeOperationType!='WT' FeeFinaType='YE'
			 */
			if (t_ssrs.GetText(i, 2).equals("3")) {
				SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
				// 余额分红
				String YuEFH_sql = " Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'YE' and FeeOperationType <>'WT' "
						+ FenHong_sql;
				logger.debug("余额分红金额是" + YuEFH_sql);
				sqlbv22.sql(YuEFH_sql);
				sqlbv22.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS YuEFH_ssrs = t_exesql.execSQL(sqlbv22);
				EYFJE = EYFJE + Double.parseDouble(YuEFH_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
				// 余额投连
				String YuETL_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'YE' and FeeOperationType <>'WT' "
						+ TouLian_sql;
				sqlbv23.sql(YuETL_sql);
				sqlbv23.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS YuETL_ssrs = t_exesql.execSQL(sqlbv23);
				EYTJE = EYTJE + Double.parseDouble(YuETL_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
				// 余额传统
				String YuECT_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'YE' and FeeOperationType <>'WT' "
						+ ChuanTong_sql;
				sqlbv24.sql(YuECT_sql);
				sqlbv24.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS YuECT_ssrs = t_exesql.execSQL(sqlbv24);
				EYCJE = EYCJE + Double.parseDouble(YuECT_ssrs.GetText(1, 1));
			}

			/*******************************************************************
			 * date:2003-12-12 项目：批改补退费的其他项目。与后面的其他项目进行合计 author:lys function
			 * :查询退保金情况---退保金的操作 对LJAGetEndorse（批改补退费表）进行查询信息。 OtherTypeNo
			 * ==3时是退保金情况 FeeOperationType!='WT' FeeFinaType='TF'
			 */
			if (t_ssrs.GetText(i, 2).equals("3")) {
				SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
				// 退费分红
				String TuiFeiFH_sql = " Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'TF' and FeeOperationType <>'WT' "
						+ FenHong_sql;
				logger.debug("退费分红金额是" + TuiFeiFH_sql);
				sqlbv25.sql(TuiFeiFH_sql);
				sqlbv25.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS TuiFeiFH_ssrs = t_exesql.execSQL(sqlbv25);
				TuiFFJE = TuiFFJE
						+ Double.parseDouble(TuiFeiFH_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
				// 退费投连
				String TuiFeiTL_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'TF' and FeeOperationType <>'WT' "
						+ TouLian_sql;
				sqlbv26.sql(TuiFeiTL_sql);
				sqlbv26.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS TuiFeiTL_ssrs = t_exesql.execSQL(sqlbv26);
				TuiFTJE = TuiFTJE
						+ Double.parseDouble(TuiFeiTL_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
				// 退费传统
				String TuiFeiCT_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'TF' and FeeOperationType <>'WT' "
						+ ChuanTong_sql;
				sqlbv27.sql(TuiFeiCT_sql);
				sqlbv27.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS TuiFeiCT_ssrs = t_exesql.execSQL(sqlbv27);
				TuiFCJE = TuiFCJE
						+ Double.parseDouble(TuiFeiCT_ssrs.GetText(1, 1));
			}

			/*******************************************************************
			 * date:2005-8-29 项目：垫交。与后面的其他项目进行合计 function :查询垫交情况---垫交操作
			 * 对LJAGetEndorse（批改补退费表）进行查询信息。 OtherTypeNo ==3时是垫交情况
			 * FeeOperationType='AP' FeeFinaType='DJ'
			 */
			if (t_ssrs.GetText(i, 2).equals("3")) {
				SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
				// 垫交分红
				String DJFH_sql = " Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'DJ' and FeeOperationType ='AP' "
						+ FenHong_sql;
				logger.debug("退费分红金额是" + DJFH_sql);
				sqlbv28.sql(DJFH_sql);
				sqlbv28.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS DJFH_ssrs = t_exesql.execSQL(sqlbv28);
				DFJE = DFJE + Double.parseDouble(DJFH_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
				// 垫交投连
				String DJTL_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'DJ' and FeeOperationType ='AP' "
						+ TouLian_sql;
				sqlbv29.sql(DJTL_sql);
				sqlbv29.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS DJTL_ssrs = t_exesql.execSQL(sqlbv29);
				DTJE = DTJE + Double.parseDouble(DJTL_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
				// 垫交传统
				String DJCT_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'DJ' and FeeOperationType ='AP' "
						+ ChuanTong_sql;
				sqlbv30.sql(DJCT_sql);
				sqlbv30.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS DJCT_ssrs = t_exesql.execSQL(sqlbv30);
				DCJE = DCJE + Double.parseDouble(DJCT_ssrs.GetText(1, 1));
			}

			/*******************************************************************
			 * Date :2003-12-15 Author :LiuYansong Function :应付业务支持的"付款"类型的操作
			 * OtherTypeNo ==3时是退保金情况 FeeOperationType!='WT' FeeFinaType='JK'
			 * 
			 */
			if (t_ssrs.GetText(i, 2).equals("3")) {
				SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
				// 借款分红
				String JieKuanFH_sql = " select ( Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'JK' and FeeOperationType <>'WT' "
						+ FenHong_sql
						+ " ) - ( "
						+ "  Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'YHSL' and FeeOperationType <>'WT' "
						+ FenHong_sql + " ) from dual";
				logger.debug("借款分红金额是" + JieKuanFH_sql);
				sqlbv31.sql(JieKuanFH_sql);
				sqlbv31.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS JieKuanFH_ssrs = t_exesql.execSQL(sqlbv31);
				JKFJE = JKFJE
						+ Double.parseDouble(JieKuanFH_ssrs.GetText(1, 1));

				// 借款投连
				//
				// String JieKuanTL_sql = "Select nvl(Sum(GetMoney),0) From
				// LJAGetEndorse "
				// +" Where ActuGetNo = '"+t_ssrs.GetText(i,1)+"' and
				// FeeFinaType = 'JK' and FeeOperationType <>'WT' "
				// +TouLian_sql;
				SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
				String JieKuanTL_sql = " select ( Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'JK' and FeeOperationType <>'WT' "
						+ TouLian_sql
						+ " ) - ( "
						+ " Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'YHSL' and FeeOperationType <>'WT' "
						+ TouLian_sql + " ) from dual";
				sqlbv32.sql(JieKuanTL_sql);
				sqlbv32.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS JieKuanTL_ssrs = t_exesql.execSQL(sqlbv32);
				JKTJE = JKTJE
						+ Double.parseDouble(JieKuanTL_ssrs.GetText(1, 1));
				logger.debug("2003-12-15借款投连金额是" + JKTJE);

				// 借款传统
				// String JieKuanCT_sql = "Select nvl(Sum(GetMoney),0) From
				// LJAGetEndorse "
				// +" Where ActuGetNo = '"+t_ssrs.GetText(i,1)+"' and
				// FeeFinaType = 'JK' and FeeOperationType <>'WT' "
				// +ChuanTong_sql;
				SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
				String JieKuanCT_sql = " select ( Select(case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'JK' and FeeOperationType <>'WT' "
						+ ChuanTong_sql
						+ " ) - ( "
						+ "  Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'YHSL' and FeeOperationType <>'WT' "
						+ ChuanTong_sql + " ) from dual";
				sqlbv33.sql(JieKuanCT_sql);
				sqlbv33.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS JieKuanCT_ssrs = t_exesql.execSQL(sqlbv33);
				JKCJE = JKCJE
						+ Double.parseDouble(JieKuanCT_ssrs.GetText(1, 1));
				logger.debug("2003-12-15借款传统是" + JKCJE);
			}
			/*******************************************************************
			 * CreateDate : 2003-12-19 Author : LiuYansong Function : 处理还款的相关金额
			 * OtherTypeNo ==3时是退保金情况 FeeOperationType!='WT' FeeFinaType='HK'
			 */
			if (t_ssrs.GetText(i, 2).equals("3")) {
				SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
				// 还款分红
				String HuanKuanFH_sql = " Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'HK' and FeeOperationType <>'WT' "
						+ FenHong_sql;
				logger.debug("还款分红金额是" + HuanKuanFH_sql);
				sqlbv34.sql(HuanKuanFH_sql);
				sqlbv34.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS HuanKuanFH_ssrs = t_exesql.execSQL(sqlbv34);
				HKFJE = HKFJE
						+ Double.parseDouble(HuanKuanFH_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
				// 还款投连
				String HuanKuanTL_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'HK' and FeeOperationType <>'WT' "
						+ TouLian_sql;
				sqlbv35.sql(HuanKuanTL_sql);
				sqlbv35.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS HuanKuanTL_ssrs = t_exesql.execSQL(sqlbv35);
				HKTJE = HKTJE
						+ Double.parseDouble(HuanKuanTL_ssrs.GetText(1, 1));
				logger.debug("2003-12-19还款投连金额是" + HKTJE);
				SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
				// 还款传统
				String HuanKuanCT_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'HK' and FeeOperationType <>'WT' "
						+ ChuanTong_sql;
				sqlbv36.sql(HuanKuanCT_sql);
				sqlbv36.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS HuanKuanCT_ssrs = t_exesql.execSQL(sqlbv36);
				HKCJE = HKCJE
						+ Double.parseDouble(HuanKuanCT_ssrs.GetText(1, 1));
				logger.debug("2003-12-19还款传统是" + HKCJE);
			}

			/*******************************************************************
			 * CreateDate : 2003-12-19 Author : LiuYansong Function : 处理利息的相关金额
			 * OtherTypeNo ==3时是退保金情况 FeeOperationType!='WT' FeeFinaType='LX'
			 */

			if (t_ssrs.GetText(i, 2).equals("3")) {
				SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
				// 利息分红
				String LiXiFH_sql = " Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'LX' and FeeOperationType <>'WT' "
						+ FenHong_sql;
				logger.debug("利息分红金额是" + LiXiFH_sql);
				sqlbv37.sql(LiXiFH_sql);
				sqlbv37.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS LiXiFH_ssrs = t_exesql.execSQL(sqlbv37);
				LXFJE = LXFJE + Double.parseDouble(LiXiFH_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
				// 利息投连
				String LiXiTL_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'LX' and FeeOperationType <>'WT' "
						+ TouLian_sql;
				sqlbv38.sql(LiXiTL_sql);
				sqlbv38.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS LiXiTL_ssrs = t_exesql.execSQL(sqlbv38);
				LXTJE = LXTJE + Double.parseDouble(LiXiTL_ssrs.GetText(1, 1));
				logger.debug("2003-12-19利息投连金额是" + LXTJE);
				SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
				// 利息传统
				String LiXiCT_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and FeeFinaType = 'LX' and FeeOperationType <>'WT' "
						+ ChuanTong_sql;
				sqlbv39.sql(LiXiCT_sql);
				sqlbv39.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS LiXiCT_ssrs = t_exesql.execSQL(sqlbv39);
				LXCJE = LXCJE + Double.parseDouble(LiXiCT_ssrs.GetText(1, 1));
				logger.debug("2003-12-19利息传统是" + LXCJE);
			}

			/*******************************************************************
			 * date:2003-05-29 author:lys function
			 * :处理红利情况。查询LJABonusGet（红利给付实付表） OtherNoType = 7
			 * LJABonusGet表中的OtherNO 对应于LCPol表中的PolNo
			 * 
			 */
			if (t_ssrs.GetText(i, 2).equals("7")) {
				SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
				// 红利/分红/总金额
				String HongLiFHJE_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJABonusGet Where actuGetNo = '"
						+ "?actuGetNo?"
						+ "' and ( OtherNo in "
						+ " (( Select PolNo From LCPol Where RiskCode in "
						+ " (Select RiskCode From LMRiskApp Where RiskType3 ='2'))) "
						+ " or OtherNo in (Select PolNo From LBPol Where RiskCode in "
						+ "(Select RiskCode From LMRiskApp Where RiskType3 = '2')))";
				logger.debug("红利/分红/总金额" + HongLiFHJE_sql);
				sqlbv40.sql(HongLiFHJE_sql);
				sqlbv40.put("actuGetNo", t_ssrs.GetText(i, 1));
				SSRS HongLIFHJE_ssrs = t_exesql.execSQL(sqlbv40);
				HFJE = HFJE + Double.parseDouble(HongLIFHJE_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
				// 红利/投连/总金额
				String HongLiTLJE_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJABonusGet Where actuGetNo = '"
						+ "?actuGetNo?"
						+ "' and ( OtherNo in "
						+ " (( Select PolNo From LCPol Where RiskCode in "
						+ " (Select RiskCode From LMRiskApp Where RiskType3 ='3'))) "
						+ " or OtherNo in (Select PolNo From LBPol Where RiskCode in "
						+ "(Select RiskCode From LMRiskApp Where RiskType3 = '3')))";
				logger.debug("红利/投连/总金额" + HongLiTLJE_sql);
				sqlbv41.sql(HongLiTLJE_sql);
				sqlbv41.put("actuGetNo", t_ssrs.GetText(i, 1));
				SSRS HongLiTLJE_ssrs = t_exesql.execSQL(sqlbv41);
				HTJE = HTJE + Double.parseDouble(HongLiTLJE_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
				// 红利/传统/总金额
				String HongLiCTJE_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJABonusGet Where actuGetNo = '"
						+ "?actuGetNo?"
						+ "' and ( OtherNo in "
						+ " (( Select PolNo From LCPol Where RiskCode in "
						+ " (Select RiskCode From LMRiskApp Where RiskType3 in ('1','4','5')))) "
						+ " or OtherNo in (Select PolNo From LBPol Where RiskCode in "
						+ "(Select RiskCode From LMRiskApp Where RiskType3 in ('1','4','5'))))";
				logger.debug("红利/传统/总金额" + HongLiCTJE_sql);
				sqlbv42.sql(HongLiCTJE_sql);
				sqlbv42.put("actuGetNo", t_ssrs.GetText(i, 1));
				SSRS HongLiCTJE_ssrs = t_exesql.execSQL(sqlbv42);
				HCJE = HCJE + Double.parseDouble(HongLiCTJE_ssrs.GetText(1, 1));
			}
			/*******************************************************************
			 * date:2003-05-29 author:lys function 处理暂收退费情况. OtherNoType = 4
			 * ,LJAGetTempFee(赞交费退费实付表)
			 */
			if (t_ssrs.GetText(i, 2).equals("4")) {
				SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
				// 暂收退费分红
				String ZanShouFH_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetTempFee "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "'"
						+ FenHong_sql;
				logger.debug("暂收退费/分红 " + ZanShouFH_sql);
				sqlbv43.sql(ZanShouFH_sql);
				sqlbv43.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS ZanShouFH_ssrs = t_exesql.execSQL(sqlbv43);
				ZFJE = ZFJE + Double.parseDouble(ZanShouFH_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
				// 暂收退费投连
				String ZanShouTL_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetTempFee "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "'"
						+ TouLian_sql;
				logger.debug("暂收退费/投连" + ZanShouTL_sql);
				sqlbv44.sql(ZanShouTL_sql);
				sqlbv44.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS ZanShouTL_ssrs = t_exesql.execSQL(sqlbv44);
				ZTJE = ZTJE + Double.parseDouble(ZanShouTL_ssrs.GetText(1, 1));
				logger.debug("当i = " + i + "时，暂收退费－投连－次数合计是" + ZTCS);
				SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
				// 暂收退费传统
				String ZanShouCT_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end)  From LJAGetTempFee "
						+ " Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "'"
						+ ChuanTong_sql;
				logger.debug("暂收退费/传统" + ZanShouCT_sql);
				sqlbv45.sql(ZanShouCT_sql);
				sqlbv45.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS ZanShouCT_ssrs = t_exesql.execSQL(sqlbv45);
				ZCJE = ZCJE + Double.parseDouble(ZanShouCT_ssrs.GetText(1, 1));
				logger.debug("当i = " + i + "时，暂收退费－传统－次数合计是" + ZCCS);
			}
			/*******************************************************************
			 * date:2003-05-29 author:lys function :处理其他信息。LJAGetOther（其他退费实付表）
			 * OtherNoType = 6 OR 8 查询RiskCode 时 OtherNO = LCPol表中的PolNo
			 * 
			 */
			if ((t_ssrs.GetText(i, 2).equals("6"))
					|| (t_ssrs.GetText(i, 2).equals("8"))) {
				// 其他/分红/总金额
				SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
				String QiTaFHJE_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetOther Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and ( OtherNo in "
						+ " (( Select PolNo From LCPol Where RiskCode in "
						+ " (Select RiskCode From LMRiskApp Where RiskType3 ='2'))) "
						+ " or OtherNo in (Select PolNo From LBPol Where RiskCode in "
						+ "(Select RiskCode From LMRiskApp Where RiskType3 = '2'))"
						+ " or OtherNo in (Select GrpPolNo From LCGrpPol Where RiskCode in (Select RiskCode From LMRiskApp Where RiskType3 = '2'))"
						+ " or OtherNo in (Select GrpPolNo From LBGrpPol Where RiskCode in (Select RiskCode From LMRiskApp Where RiskType3 = '2')))";
				logger.debug("其他/分红/总金额" + QiTaFHJE_sql);
				sqlbv46.sql(QiTaFHJE_sql);
				sqlbv46.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS QiTaFHJE_ssrs = t_exesql.execSQL(sqlbv46);
				QFJE = QFJE + Double.parseDouble(QiTaFHJE_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
				// 其他/投连/总金额
				String QiTaTLJE_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetOther Where ActuGetNo = '"
						+ "?ActuGetNo?"
						+ "' and ( OtherNo in "
						+ " (( Select PolNo From LCPol Where RiskCode in "
						+ " (Select RiskCode From LMRiskApp Where RiskType3 ='3'))) "
						+ " or OtherNo in (Select PolNo From LBPol Where RiskCode in "
						+ "(Select RiskCode From LMRiskApp Where RiskType3 = '3'))"
						+ " or OtherNo in (Select GrpPolNo From LCGrpPol Where RiskCode in (Select RiskCode From LMRiskApp Where RiskType3 = '3'))"
						+ " or OtherNo in (Select GrpPolNo From LBGrpPol Where RiskCode in (Select RiskCode From LMRiskApp Where RiskType3 = '3') ))";
				logger.debug("其他/投连/总金额" + QiTaTLJE_sql);
				sqlbv47.sql(QiTaTLJE_sql);
				sqlbv47.put("ActuGetNo", t_ssrs.GetText(i, 1));
				SSRS QiTaTLJE_ssrs = t_exesql.execSQL(sqlbv47);
				QTJE = QTJE + Double.parseDouble(QiTaTLJE_ssrs.GetText(1, 1));
				SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
				// 其他/传统/总金额
				String QiTaCTJE_sql = "Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetOther Where actuGetNo = '"
						+ "?actuGetNo?"
						+ "' and ( OtherNo in "
						+ " (( Select PolNo From LCPol Where RiskCode in "
						+ " (Select RiskCode From LMRiskApp Where RiskType3 in ('1','4','5')))) "
						+ " or OtherNo in (Select PolNo From LBPol Where RiskCode in "
						+ "(Select RiskCode From LMRiskApp Where RiskType3 in ('1','4','5')))"
						+ " or OtherNo in (Select GrpPolNo From LCGrpPol Where RiskCode in (Select RiskCode From LMRiskApp Where RiskType3 in ('1','4','5')))"
						+ " or OtherNo in (Select GrpPolNo From LBGrpPol Where RiskCode in (Select RiskCode From LMRiskApp Where RiskType3 in ('1','4','5'))))";
				logger.debug("其他/传统/总金额" + QiTaCTJE_sql);
				sqlbv48.sql(QiTaCTJE_sql);
				sqlbv48.put("actuGetNo", t_ssrs.GetText(i, 1));
				SSRS QiTaCTJE_ssrs = t_exesql.execSQL(sqlbv48);
				QCJE = QCJE + Double.parseDouble(QiTaCTJE_ssrs.GetText(1, 1));
			}
		}
		
		// 对还垫进行处理
		String Date_sql = " and exists (select * from ljapay where incomeno=ljagetendorse.endorsementno and ConfDate >= '"
				+ "?date1?"
				+ "' and ConfDate <= '"
				+ "?date2?"
				+ "' and incometype='3') ";
		SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
		String HDFH_sql = " Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
				+ " Where FeeFinaType = 'HD' and FeeOperationType ='TR' "
				+ Date_sql + FenHong_sql + " and ManageCom like concat('"
				+ "?ManageCom?" + "','%')";
		logger.debug("还垫分红金额SQL" + HDFH_sql);
		sqlbv49.sql(HDFH_sql);
		sqlbv49.put("date1", mDay[0]);
		sqlbv49.put("date2", mDay[1]);
		sqlbv49.put("ManageCom", mGlobalInput.ManageCom);
		SSRS HDFH_ssrs = t_exesql.execSQL(sqlbv49);
		HDFJE = Double.parseDouble(HDFH_ssrs.GetText(1, 1));
		SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
		String HDTL_sql = " Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
				+ " Where FeeFinaType = 'HD' and FeeOperationType ='TR' "
				+ Date_sql + TouLian_sql + " and ManageCom like concat('"
				+ "?ManageCom?" + "','%')";
		logger.debug("还垫投联金额SQL" + HDTL_sql);
		sqlbv50.sql(HDTL_sql);
		sqlbv50.put("date1", mDay[0]);
		sqlbv50.put("date2", mDay[1]);
		sqlbv50.put("ManageCom", mGlobalInput.ManageCom);
		SSRS HDTL_ssrs = t_exesql.execSQL(sqlbv50);
		HDTJE = Double.parseDouble(HDTL_ssrs.GetText(1, 1));
		SQLwithBindVariables sqlbv51 = new SQLwithBindVariables();
		String HDCT_sql = " Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
				+ " Where FeeFinaType = 'HD' and FeeOperationType ='TR' "
				+ Date_sql + ChuanTong_sql + " and ManageCom like concat('"
				+ "?ManageCom?" + "','%')";
		logger.debug("还垫传统金额SQL" + HDCT_sql);
		sqlbv51.sql(HDCT_sql);
		sqlbv51.put("date1", mDay[0]);
		sqlbv51.put("date2", mDay[1]);
		sqlbv51.put("ManageCom", mGlobalInput.ManageCom);
		SSRS HDCT_ssrs = t_exesql.execSQL(sqlbv51);
		HDCJE = Double.parseDouble(HDCT_ssrs.GetText(1, 1));

		// 对工本费进行处理
		String GBDate_sql = " and (exists (select 1 from ljapay where incomeno=ljagetendorse.endorsementno and ConfDate >= '"
				+ "?date1?"
				+ "' and ConfDate <= '"
				+ "?date2?"
				+ "' and incometype='3') "
				+ " or exists (select 1 from ljaget where otherno=ljagetendorse.endorsementno and ConfDate >= '"
				+ "?date1?"
				+ "' and ConfDate <= '"
				+ "?date2?"
				+ "' and othernotype='3'))";
		SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
		String GBFH_sql = " Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
				+ " Where FeeFinaType = 'GB' " + GBDate_sql + FenHong_sql
				+ " and ManageCom like concat('" + "?ManageCom?" + "','%')";
		logger.debug("工本费分红金额SQL" + GBFH_sql);
		sqlbv52.sql(GBFH_sql);
		sqlbv52.put("date1", mDay[0]);
		sqlbv52.put("date2", mDay[1]);
		sqlbv52.put("ManageCom", mGlobalInput.ManageCom);
		SSRS GBFH_ssrs = t_exesql.execSQL(sqlbv52);
		GBFJE = Double.parseDouble(GBFH_ssrs.GetText(1, 1));
		SQLwithBindVariables sqlbv53 = new SQLwithBindVariables();
		String GBTL_sql = " Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
				+ " Where FeeFinaType = 'GB' " + GBDate_sql + TouLian_sql
				+ " and ManageCom like concat('" + "?ManageCom?" + "','%')";
		logger.debug("工本费投联金额SQL" + GBTL_sql);
		sqlbv53.sql(GBTL_sql);
		sqlbv53.put("date1", mDay[0]);
		sqlbv53.put("date2", mDay[1]);
		sqlbv53.put("ManageCom", mGlobalInput.ManageCom);
		SSRS GBTL_ssrs = t_exesql.execSQL(sqlbv53);
		GBTJE = Double.parseDouble(GBTL_ssrs.GetText(1, 1));
		SQLwithBindVariables sqlbv54 = new SQLwithBindVariables();
		String GBCT_sql = " Select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) From LJAGetEndorse "
				+ " Where FeeFinaType = 'GB' " + GBDate_sql + ChuanTong_sql
				+ " and ManageCom like concat('" + "?ManageCom?" + "','%')";
		logger.debug("工本费传统金额SQL" + GBCT_sql);
		sqlbv54.sql(GBCT_sql);
		sqlbv54.put("date1", mDay[0]);
		sqlbv54.put("date2", mDay[1]);
		sqlbv54.put("ManageCom", mGlobalInput.ManageCom);
		SSRS GBCT_ssrs = t_exesql.execSQL(sqlbv54);
		GBCJE = Double.parseDouble(GBCT_ssrs.GetText(1, 1));

		/***********************************************************************
		 * 对vts上的变量进行附值 CS :次数 JE :金额
		 */

		NCS = NFCS + NTCS + NCCS; // 年金次数
		NJE = NFJE + NTJE + NCJE;

		MCS = MFCS + MTCS + MCCS; // 满期次数
		MJE = MFJE + MTJE + MCJE;

		LCS = LFCS + LTCS + LCCS; // 理赔次数
		LJE = LFJE + LTJE + LCJE;

		TCS = TFCS + TTCS + TCCS; // 退保金---犹豫期撤单次数
		TJE = TFJE + TTJE + TCJE;

		T2JE = T2FJE + T2TJE + T2CJE; // 退保金---退保金

		HCS = HFCS + HTCS + HCCS; // 红利次数
		HJE = HFJE + HTJE + HCJE;

		ZCS = ZFCS + ZTCS + ZCCS; // 暂收退费次数
		ZJE = ZFJE + ZTJE + ZCJE;

		QCS = QFCS + QTCS + QCCS; // 其他次数

		QJE = QFJE + QTJE + QCJE;
		// 退费金额
		TuiFJE = TuiFFJE + TuiFTJE + TuiFCJE;
		// 借款金额
		JKJE = JKFJE + JKTJE + JKCJE;
		// 余额金额
		EYJE = EYFJE + EYTJE + EYCJE;
		// 还款金额
		HKJE = HKFJE + HKTJE + HKCJE;
		// 利息金额
		LXJE = LXFJE + LXTJE + LXCJE;
		// 垫交金额
		DJE = DFJE + DTJE + DCJE;
		// 还垫金额
		HDJE = HDFJE + HDTJE + HDCJE;
		// 工本费金额
		GBJE = GBFJE + GBTJE + GBCJE;
		// 总金额
		JE = NJE + MJE + LJE + TJE + HJE + ZJE + QJE + T2JE + TuiFJE + EYJE
				+ JKJE + HKJE + LXJE + DJE + HDJE + GBJE;

		// 对vts进行附值
		TextTag mtexttag = new TextTag();
		mtexttag.add("StartDate", mDay[0]); // 开始日期
		mtexttag.add("EndDate", mDay[1]); // 结束日期

		mtexttag.add("JE", new DecimalFormat("0.00").format(JE));

		mtexttag.add("NJE", new DecimalFormat("0.00").format(NJE));
		mtexttag.add("NFJE", new DecimalFormat("0.00").format(NFJE));
		mtexttag.add("NTJE", new DecimalFormat("0.00").format(NTJE));
		mtexttag.add("NCJE", new DecimalFormat("0.00").format(NCJE));

		mtexttag.add("MJE", new DecimalFormat("0.00").format(MJE));
		mtexttag.add("MFJE", new DecimalFormat("0.00").format(MFJE));
		mtexttag.add("MTJE", new DecimalFormat("0.00").format(MTJE));
		mtexttag.add("MCJE", new DecimalFormat("0.00").format(MCJE));

		mtexttag.add("LJE", new DecimalFormat("0.00").format(LJE));
		mtexttag.add("LFJE", new DecimalFormat("0.00").format(LFJE));
		mtexttag.add("LTJE", new DecimalFormat("0.00").format(LTJE));
		mtexttag.add("LCJE", new DecimalFormat("0.00").format(LCJE));

		mtexttag.add("TJE", new DecimalFormat("0.00").format(TJE));// 犹豫期撤单
		mtexttag.add("TFJE", new DecimalFormat("0.00").format(TFJE));
		mtexttag.add("TTJE", new DecimalFormat("0.00").format(TTJE));
		mtexttag.add("TCJE", new DecimalFormat("0.00").format(TCJE));

		mtexttag.add("T2JE", new DecimalFormat("0.00").format(T2JE));// 退保金
		mtexttag.add("T2FJE", new DecimalFormat("0.00").format(T2FJE));
		mtexttag.add("T2TJE", new DecimalFormat("0.00").format(T2TJE));
		mtexttag.add("T2CJE", new DecimalFormat("0.00").format(T2CJE));

		mtexttag.add("HJE", new DecimalFormat("0.00").format(HJE));
		mtexttag.add("HFJE", new DecimalFormat("0.00").format(HFJE));
		mtexttag.add("HTJE", new DecimalFormat("0.00").format(HTJE));
		mtexttag.add("HCJE", new DecimalFormat("0.00").format(HCJE));

		mtexttag.add("ZJE", new DecimalFormat("0.00").format(ZJE));
		mtexttag.add("ZFJE", new DecimalFormat("0.00").format(ZFJE));
		mtexttag.add("ZTJE", new DecimalFormat("0.00").format(ZTJE));
		mtexttag.add("ZCJE", new DecimalFormat("0.00").format(ZCJE));

		mtexttag.add("QJE", new DecimalFormat("0.00").format(QJE));
		mtexttag.add("QFJE", new DecimalFormat("0.00").format(QFJE));
		mtexttag.add("QTJE", new DecimalFormat("0.00").format(QTJE));
		mtexttag.add("QCJE", new DecimalFormat("0.00").format(QCJE));

		// 增加退费的金额
		mtexttag.add("TuiFJE", new DecimalFormat("0.00").format(TuiFJE));
		mtexttag.add("TuiFFJE", new DecimalFormat("0.00").format(TuiFFJE));
		mtexttag.add("TuiFTJE", new DecimalFormat("0.00").format(TuiFTJE));
		mtexttag.add("TuiFCJE", new DecimalFormat("0.00").format(TuiFCJE));

		// 增加余额的金额
		mtexttag.add("EYJE", new DecimalFormat("0.00").format(EYJE));
		mtexttag.add("EYFJE", new DecimalFormat("0.00").format(EYFJE));
		mtexttag.add("EYTJE", new DecimalFormat("0.00").format(EYTJE));
		mtexttag.add("EYCJE", new DecimalFormat("0.00").format(EYCJE));

		// 增加借款的金额
		mtexttag.add("JKJE", new DecimalFormat("0.00").format(JKJE));
		mtexttag.add("JKFJE", new DecimalFormat("0.00").format(JKFJE));
		mtexttag.add("JKTJE", new DecimalFormat("0.00").format(JKTJE));
		mtexttag.add("JKCJE", new DecimalFormat("0.00").format(JKCJE));

		// 增加还款的金额
		mtexttag.add("HKJE", new DecimalFormat("0.00").format(HKJE));
		mtexttag.add("HKFJE", new DecimalFormat("0.00").format(HKFJE));
		mtexttag.add("HKTJE", new DecimalFormat("0.00").format(HKTJE));
		mtexttag.add("HKCJE", new DecimalFormat("0.00").format(HKCJE));

		// 增加利息的金额
		mtexttag.add("LXJE", new DecimalFormat("0.00").format(LXJE));
		mtexttag.add("LXFJE", new DecimalFormat("0.00").format(LXFJE));
		mtexttag.add("LXTJE", new DecimalFormat("0.00").format(LXTJE));
		mtexttag.add("LXCJE", new DecimalFormat("0.00").format(LXCJE));

		// 垫交金额
		mtexttag.add("DJE", new DecimalFormat("0.00").format(DJE));
		mtexttag.add("DFJE", new DecimalFormat("0.00").format(DFJE));
		mtexttag.add("DTJE", new DecimalFormat("0.00").format(DTJE));
		mtexttag.add("DCJE", new DecimalFormat("0.00").format(DCJE));

		// 还垫金额
		mtexttag.add("HDJE", new DecimalFormat("0.00").format(HDJE));
		mtexttag.add("HDFJE", new DecimalFormat("0.00").format(HDFJE));
		mtexttag.add("HDTJE", new DecimalFormat("0.00").format(HDTJE));
		mtexttag.add("HDCJE", new DecimalFormat("0.00").format(HDCJE));

		// 工本费金额
		mtexttag.add("GBJE", new DecimalFormat("0.00").format(GBJE));
		mtexttag.add("GBFJE", new DecimalFormat("0.00").format(GBFJE));
		mtexttag.add("GBTJE", new DecimalFormat("0.00").format(GBTJE));
		mtexttag.add("GBCJE", new DecimalFormat("0.00").format(GBCJE));
		if (mtexttag.size() > 0)
			xmlexport.addTextTag(mtexttag);
		// xmlexport.outputDocumentToFile("e:\\","财务日结");//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}
	private boolean getPrintDataPayHebao() {
		XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例
		//xmlexport.createDocument("FinDayCheckPremiumPay.vts", "printer"); // 最好紧接着就初始化xml文档
		xmlexport.createDocument("保费收入日结打印", "", "", "0");
		String[] strArr = null;
		ListTable tlistTable = new ListTable();
		tlistTable.setName("RISK1");
		SQLwithBindVariables sqlbv55 = new SQLwithBindVariables();
		String strSql = "select A,B,(select riskname from lmrisk where riskcode = B),(select codename from ldcode where codetype = 'salechnl' and code = h),sum(d),count(f) "
				+ "from (select (case when a = 0 or a = -1 then '趸交' when c = 'S' and a != 0 then '首年首期' when c = 'X' and g >= e then '首年续期' when c = 'X' and g < e then '续年续期' end) A,"
				+ "b B,d,f,h"
				+ " from ("
				+ " select payintv a,riskcode b,(case paycount when 1 then 'S' else 'X' end) c,(case when sumactupaymoney is not null then sumactupaymoney  else 0 end) d,curpaytodate e,polno f,(select add_months(cvalidate, 12) from lcpol where polno = a.polno) g,(select salechnl from lcpol where polno = a.polno) h "
				+ " from ljapayperson a where confdate>='" + "?date1?" + "' and confdate<='" + "?date2?"+ "' "
				+ " and paytype='ZC' and managecom like concat('"+ "?ManageCom?" + "','%') and grppolno = '00000000000000000000'"
				+ " and not exists (select distinct 1 from ljapay where payno = a.payno and incometype = '10') "
				+ " and not exists (select distinct 1 from ljaget where actugetno = a.payno and othernotype = '10') "
				+ " union all "
				+ " select payintv a,riskcode b,(case paycount when 1 then 'S' else 'X' end) c,(case when sumactupaymoney is not null then sumactupaymoney  else 0 end) d,curpaytodate e,grppolno f,"
				+ " (select add_months(cvalidate, 12) from lcgrppol where grppolno = a.grppolno) g, "
				+ " (select salechnl from lcgrppol where grppolno = a.grppolno) h "
				+ " from ljapaygrp a where confdate>='"	+ "?date1?"+ "' and confdate<='"	+ "?date2?"+ "' "
				+ " and paytype='ZC' and managecom like concat('"	+ "?ManageCom?"	+ "','%') "
				+ "  and not exists (select distinct 1 from ljapay where payno = a.payno and incometype = '10') "
				+ " and not exists (select distinct 1 from ljaget where actugetno = a.payno and othernotype = '10') ) t ) l "
				+ " group by A, B, h order by A desc, h ";
		sqlbv55.sql(strSql);
		sqlbv55.put("date1", mDay[0]);
		sqlbv55.put("date2", mDay[1]);
		sqlbv55.put("ManageCom", mGlobalInput.ManageCom);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv55);
		logger.debug("strSql:" + strSql);

		double SumMoney = 0;
		int SumCount = 0;
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				double ThisMoney = Double.parseDouble(tSSRS.GetText(i, 5));
				int ThisCount = Integer.parseInt(tSSRS.GetText(i, 6));
				strArr = new String[7];
				strArr[0] = "";
				strArr[1] = tSSRS.GetText(i, 1);
				strArr[2] = tSSRS.GetText(i, 2);
				strArr[3] = tSSRS.GetText(i, 3);
				strArr[4] = tSSRS.GetText(i, 4);
				strArr[5] = tSSRS.GetText(i, 5);
				strArr[6] = tSSRS.GetText(i, 6);
				tlistTable.add(strArr);
				SumMoney += ThisMoney;
				SumCount += ThisCount;
		}
			String tSumMoney = String.valueOf(SumMoney);
			tSumMoney = new DecimalFormat("0.00").format(Double.valueOf(tSumMoney));
		strArr = new String[7];
		xmlexport.addListTable(tlistTable, strArr);
		SQLwithBindVariables sqlbv56 = new SQLwithBindVariables();
		String nsql = "select Name from LDCom where ComCode='"
			+ "?ManageCom?" + "'";
		ExeSQL nExeSQL = new ExeSQL();
		sqlbv56.sql(nsql);
		sqlbv56.put("ManageCom", mGlobalInput.ManageCom);
		SSRS nSSRS = nExeSQL.execSQL(sqlbv56);
		String manageCom = nSSRS.GetText(1, 1);
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		texttag.add("StartDate", mDay[0]);
		texttag.add("EndDate", mDay[1]);
		texttag.add("ManageCom", manageCom);
		texttag.add("SumCount", SumCount);
		texttag.add("SumMoney", tSumMoney);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}
		
	
	//业务实付
	 public boolean getPrintDataPayYWSF()
	    {
//	        XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
	//        xmlexport.createDocument("FinDayCheckSF.vts", "printer"); //最好紧接着就初始化xml文档
		 	XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例
			xmlexport.createDocument("业务实付日结打印", "", "", "0");
	        ListTable tlisttable = new ListTable();
	        tlisttable.setName("RISK");
	        SQLwithBindVariables sqlbv57 = new SQLwithBindVariables();
	        String[] strArr = null;
	        String tSql =" select ftype,pmode,rcode,(select riskname from lmriskapp where riskcode=rcode),(case when (select codename  from ldcode where codetype = 'salechnl' and code = schnl) is not null then (select codename  from ldcode where codetype = 'salechnl' and code = schnl)  else '' end),sum(money),sum(num) from ("
				        +" select '实付总计' ftype,paymode pmode,'' rcode,'' schnl,'0' pFlag,(case when sum(getmoney) is not null then sum(getmoney)  else 0 end) money,count(1) num from ("
				        +" select  (select codename from ldcode where codetype='paymode' and code=trim(paymode)) paymode, (case when sumgetmoney is not null then sumgetmoney  else 0 end) getmoney "
				        +" from ljaget where confdate >= '"+"?date1?"+"'   and confdate <= '"+"?date2?"+"'   and ManageCom Like concat('"+"?ManageCom?"+"','%')  "
				        +" ) t group by paymode"
				        +" union all"
				        +" select '保全收费总计' ftype,'' pmode,'' rcode,'' schnl,'1' pFlag,(case when sum(sumactupaymoney) is not null then sum(sumactupaymoney)  else 0 end) money,count(1) num  "
				        +" from ljapay where incometype='10' and  confdate >= '"+"?date1?"+"'   and confdate <= '"+"?date2?"+"'   and ManageCom Like concat('"+"?ManageCom?"+"','%')"
				        +" union all  "
				        +" select feefinatype ftype,'' pmode,riskcode rcode,salechnl schnl,'2' pFlag,(case when sum(getmoney) is not null then sum(getmoney)  else 0 end) money,count(1) num from ("
				        +" select (select codename from ldcode where codetype='feefinatype' and code=trim(a.feefinatype)) feefinatype, riskcode riskcode, (select salechnl from lcpol where polno=a.polno) salechnl,(case when getmoney is not null then getmoney  else 0 end) getmoney  "
				        +" from LJAGetEndorse a  where getconfirmdate >= '"+"?date1?"+"' and getconfirmdate <= '"+"?date2?"+"'   and managecom like concat('"+"?ManageCom?"+"','%')  and feefinatype in ('BF', 'LX', 'HD', 'GB','HK')    and exists (select 1 from ljaget where actugetno = a.actugetno and othernotype = '10' union select 1 from ljapay where payno=a.actugetno and incometype='10') "//将印花税调整到保全应付日结单中
				        +" union all  "
				        +" select '保费收入' feefinatype, riskcode riskcode, (select salechnl from lcpol where polno=a.polno) salechnl,(case when sumactupaymoney is not null then sumactupaymoney  else 0 end) getmoney   "
				        +" from ljapayperson a  where confdate >= '"+"?date1?"+"' and confdate <= '"+"?date2?"+"' and managecom like concat('"+"?ManageCom?"+"','%')  and exists (select 1 from ljaget where actugetno = a.payno and othernotype = '10' union select 1 from ljapay where payno=a.payno and incometype='10') "
				        +" union all  "
				        +" select (select codename from ldcode where codetype='feefinatype' and code=trim(a.feefinatype)) feefinatype, riskcode riskcode,salechnl salechnl,(case when pay is not null then pay  else 0 end) getmoney "
				        +" from LJAGetClaim a  where confdate >= '"+"?date1?"+"' and confdate <= '"+"?date2?"+"'  and managecom like concat('"+"?ManageCom?"+"','%') and feefinatype in ('BF', 'HDLX', 'HKLX', 'HK','HD','HLRB','YFRB')  and exists (select 1 from ljaget where actugetno = a.actugetno and othernotype = '5' union select 1 from ljapay where payno=a.actugetno and incometype='5') "
				        +" ) f "
				        +" group by feefinatype,riskcode,salechnl"
				        +" having sum(getmoney)<>0"
				        +" ) g group by pFlag,ftype,pmode,rcode,schnl order by pFlag,ftype,pmode,rcode,schnl";	        	  
	        logger.debug("SQL = " + tSql);
	        sqlbv57.sql(tSql);
	        sqlbv57.put("date1", mDay[0]);
	        sqlbv57.put("date2", mDay[1]);
	        sqlbv57.put("ManageCom", mGlobalInput.ManageCom);
	        ExeSQL tExeSQL = new ExeSQL();
	        SSRS tSSRS = tExeSQL.execSQL(sqlbv57);
	        int nRows = tSSRS.getMaxRow();
			double SumMoney = 0;
    		int SumCount = 0;
	        for (int i = 1; i <= nRows; i++)
	        {
	    
//				double ThisMoney = Double.parseDouble(tSSRS.GetText(i, 6));
//				int ThisCount = Integer.parseInt(tSSRS.GetText(i, 7));
	            strArr = new String[7];
	            for (int j = 0; j < 7; j++)
	            {
	                strArr[j] = tSSRS.GetText(i, j + 1);
	            }
//				SumMoney += ThisMoney;
//				SumCount += ThisCount;
	            tlisttable.add(strArr);
	        }
	        SQLwithBindVariables sqlbv58 = new SQLwithBindVariables();
	        String sumSql ="select sum(money),sum(num) from (select (case when sum(sumgetmoney) is not null then sum(sumgetmoney)  else 0 end) money ,count(1) num from ljaget where confdate >= '"+"?date1?"+"'   and confdate <= '"+"?date2?"+"'   and ManageCom Like concat('"+"?ManageCom?"+"','%')  "
	        			 +" union all select -(case when sum(sumactupaymoney) is not null then sum(sumactupaymoney)  else 0 end) money ,count(1) num from ljapay  where confdate >= '"+"?date1?"+"'   and confdate <= '"+"?date2?"+"'   and ManageCom Like concat('"+"?ManageCom?"+"','%') and incometype='10') g";
	        sqlbv58.sql(sumSql);
	        sqlbv58.put("date1", mDay[0]);
	        sqlbv58.put("date2", mDay[1]);
	        sqlbv58.put("ManageCom", mGlobalInput.ManageCom);
	        SSRS sumSSRS = tExeSQL.execSQL(sqlbv58);
	        for(int i = 1; i <= sumSSRS.getMaxRow(); i++)
	        {
	        	SumMoney =Double.parseDouble(sumSSRS.GetText(1, 1));
	        	SumCount=Integer.parseInt(sumSSRS.GetText(1, 2));
	        }
	        strArr = new String[5];
	        strArr[0] = "PayMode";
	        strArr[1] = "RiskName";
	        strArr[2] = "Chnl";
	        strArr[3] = "Money";
	        strArr[4] = "Count";

	        xmlexport.addListTable(tlisttable, strArr);
	        SQLwithBindVariables sqlbv59 = new SQLwithBindVariables();
			String nsql = "select Name from LDCom where ComCode='"
				+ "?ManageCom?" + "'";
			sqlbv59.sql(nsql);
			sqlbv59.put("ManageCom", mGlobalInput.ManageCom);
			ExeSQL nExeSQL = new ExeSQL();
			SSRS nSSRS = nExeSQL.execSQL(sqlbv59);
			String manageCom = nSSRS.GetText(1, 1);
			String tSumMoney = String.valueOf(SumMoney);
			tSumMoney = new DecimalFormat("0.00").format(Double.valueOf(tSumMoney));
	        TextTag texttag = new TextTag();
	        texttag.add("StartDate", mDay[0]);
	        texttag.add("EndDate", mDay[1]);
	        texttag.add("ManageCom", manageCom);
			texttag.add("SumCount", SumCount);
			texttag.add("SumMoney", tSumMoney);
	        if (texttag.size() > 0)
	        {
	            xmlexport.addTextTag(texttag);
	        }
	        mResult.clear();
	        mResult.addElement(xmlexport);

	        return true;
	    }
	 //保全收费日结
	 public boolean getPrintDataPayBQSF() {
			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			xmlexport.createDocument("FinDayCheckBQSF.vts", "printer"); // 最好紧接着就初始化xml文档

			ListTable tlisttable = new ListTable();
			tlisttable.setName("RISK");

			String[] tSql = new String[14];

			String[] strArr = null;

			// 保全收费总计
			tSql[1] = " Select '保全收费',' ',' ',(case when sum(money) is not null then sum(money)  else 0 end),count(1) From (select getmoney money from LJAGetEndorse Where MakeDate >='"
					+ "?date1?"
					+ "' and MakeDate <= '"
					+ "?date2?"
					+ "' and ManageCom Like concat('"
					+ "?ManageCom?"
					+ "','%')"
					+ "and (FeeFinaType in ('BF','GB','HK','LX','RV') ) union all "
					+ "select sumactupaymoney money from ljapayperson Where PayType='ZC' and MakeDate >='"
					+ "?date1?"
					+ "' and MakeDate <= '"
					+ "?date2?"
					+ "' and ManageCom Like concat('"
					+ "?ManageCom?"
					+ "','%')"
					+ "and (payno in (select actugetno from ljaget where othernotype='10') "
					+ "or payno in(select payno from ljapay where othernotype='10')))";

			// 保全保费合计
			tSql[2] = " Select '保费',' ',' ',(case when sum(money) is not null then sum(money)  else 0 end),count(1) From (select getmoney money from LJAGetEndorse Where MakeDate >='"
					+ "?date1?"
					+ "' and MakeDate <= '"
					+ "?date2?"
					+ "' and ManageCom Like concat('"
					+ "?ManageCom?"
					+ "','%') "
					+ "and FeeFinaType = 'BF' union all "
					+ "select sumactupaymoney money from ljapayperson Where PayType='ZC' and MakeDate >='"
					+ "?date1?"
					+ "' and MakeDate <= '"
					+ "?date2?"
					+ "' and ManageCom Like concat('"
					+ "?ManageCom?"
					+ "','%')"
					+ "and (payno in (select actugetno from ljaget where othernotype='10') "
					+ "or payno in(select payno from ljapay where othernotype='10')))";

			// 保全保费统计
			tSql[3] = "Select '',(Select riskname From LMRiskApp Where riskcode=a),"
					+ "(case trim(b) when '1' then '团体险' when '2' then '个人营销' when '3' then '银行代理' when '4' then '电子商务' else '其它' end),d,e "
					+ "from (select a,b,(case when sum(c) is not null then sum(c)  else 0 end) d,count(*) e from (Select riskcode a,"
					+ "(select salechnl from lcpol where polno=ljagetendorse.polno) b,getmoney c "
					+ "From LJAGetEndorse Where MakeDate >='"
					+ "?date1?"
					+ "' and MakeDate <= '"
					+ "?date2?"
					+ "' and ManageCom Like concat('"
					+ "?ManageCom?"
					+ "','%')"
					+ "and FeeFinaType = 'BF' and getmoney<>0 "
					+ "union all select riskcode a,(select salechnl from lcpol "
					+ "where polno=ljapayperson.polno) b,sumactupaymoney c "
					+ "From LJAPayPerson Where PayType='ZC' and MakeDate >='"
					+ "?date1?"
					+ "' and MakeDate <= '"
					+ "?date2?"
					+ "' and ManageCom Like concat('"
					+ "?ManageCom?"
					+ "','%')"
					+ "and (payno in (select actugetno from ljaget where othernotype='10') "
					+ "or payno in(select payno from ljapay where othernotype='10'))"
					+ ") group by a,b order by a,b)";

			// 保全工本费合计
			tSql[4] = " Select '工本费','','',(case when sum(getmoney) is not null then sum(getmoney)  else 0 end),count(1) From LJAGetEndorse Where MakeDate >='"
					+ "?date1?"
					+ "' and MakeDate <= '"
					+ "?date2?"
					+ "' and ManageCom Like concat('"
					+ "?ManageCom?"
					+ "','%')"
					+ " and FeeFinaType = 'GB'";

			// 保全工本费统计
			tSql[5] = "Select '',(Select riskname From LMRiskApp Where riskcode=a),"
					+ "(case trim(b) when '1' then '团体险' when '2' then '个人营销' when '3' then '银行代理' when '4' then '电子商务' else '其它' end),d,e "
					+ "from (select a,b,(case when sum(c) is not null then sum(c)  else 0 end) d,count(*) e from (Select riskcode a,"
					+ "(select salechnl from lcpol where polno=ljagetendorse.polno) b,getmoney c "
					+ "From LJAGetEndorse Where MakeDate >='"
					+ "?date1?"
					+ "' and MakeDate <= '"
					+ "?date2?"
					+ "' and ManageCom Like concat('"
					+ "?ManageCom?"
					+ "','%')"
					+ " and FeeFinaType = 'GB' and getmoney<>0) group by a,b order by a,b)";

			// 保全印花税合计
			tSql[6] = " Select '印花税','','',(case when sum(getmoney) is not null then sum(getmoney)  else 0 end),count(1) From LJAGetEndorse Where MakeDate >='"
					+ "?date1?"
					+ "' and MakeDate <= '"
					+ "?date2?"
					+ "' and ManageCom Like concat('"
					+ "?ManageCom?"
					+ "','%')"
					+ " and FeeFinaType = 'RV'";

			// 保全印花税统计
			tSql[7] = "Select '',(Select riskname From LMRiskApp Where riskcode=a),"
					+ "(case trim(b) when '1' then '团体险' when '2' then '个人营销' when '3' then '银行代理' when '4' then '电子商务' else '其它' end),d,e "
					+ "from (select a,b,(case when sum(c) is not null then sum(c)  else 0 end) d,count(*) e from (Select riskcode a,"
					+ "(select salechnl from lcpol where polno=ljagetendorse.polno) b,getmoney c "
					+ "From LJAGetEndorse Where MakeDate >='"
					+ "?date1?"
					+ "' and MakeDate <= '"
					+ "?date2?"
					+ "' and ManageCom Like concat('"
					+ "?ManageCom?"
					+ "','%')"
					+ " and FeeFinaType = 'RV' and getmoney<>0) group by a,b order by a,b)";
			// 一期暂时不提供
			// 保全还垫合计
			// tSql[8] = " Select '还垫','','',sum(getmoney),count(1) From
			// LJAGetEndorse Where MakeDate >='"
			// + mDay[0] + "' and MakeDate <= '" + mDay[1]
			// + "' and ManageCom Like '" + mGlobalInput.ManageCom + "%' "
			// + " and SubFeeOperationType = 'P010'";
			//
			// //保全还垫统计
			// tSql[9] = "Select '',(Select riskname From LMRiskApp Where
			// riskcode=a),"
			// + "decode(b,'1','团体险','2','个人营销','3','银行代理','4','电子商务','其它'),d,e "
			// + "from (select a,b,sum(c) d,count(*) e from (Select riskcode a,"
			// + "(select salechnl from lcpol where polno=ljagetendorse.polno)
			// b,getmoney c "
			// + "From LJAGetEndorse Where MakeDate >='" + mDay[0]
			// + "' and MakeDate <= '" + mDay[1] + "' and ManageCom Like '"
			// + mGlobalInput.ManageCom + "%' "
			// + " and SubFeeOperationType = 'P010' and getmoney<>0) group by a,b
			// order by a,b)";

			// 保全还款合计
			tSql[10] = " Select '还款','','',(case when sum(getmoney) is not null then sum(getmoney)  else 0 end),count(1) From LJAGetEndorse Where MakeDate >='"
					+ "?date1?"
					+ "' and MakeDate <= '"
					+ "?date2?"
					+ "' and ManageCom Like concat('"
					+ "?ManageCom?"
					+ "','%') "
					+ " and FeeFinaType = 'HK'";

			// 保全还款统计
			tSql[11] = "Select '',(Select riskname From LMRiskApp Where riskcode=a),"
					+ "(case trim(b) when '1' then '团体险' when '2' then '个人营销' when '3' then '银行代理' when '4' then '电子商务' else '其它' end),d,e "
					+ "from (select a,b,(case when sum(c) is not null then sum(c)  else 0 end),0) d,count(*) e from (Select riskcode a,"
					+ "(select salechnl from lcpol where polno=ljagetendorse.polno) b,getmoney c "
					+ "From LJAGetEndorse Where MakeDate >='"
					+ "?date1?"
					+ "' and MakeDate <= '"
					+ "?date2?"
					+ "' and ManageCom Like concat('"
					+ "?ManageCom?"
					+ "','%')"
					+ " and FeeFinaType = 'HK' and getmoney<>0) group by a,b order by a,b)";

			// 保全利息合计
			tSql[12] = " Select '利息','','',(case when sum(getmoney) is not null then sum(getmoney)  else 0 end),count(1) From LJAGetEndorse Where MakeDate >='"
					+ "?date1?"
					+ "' and MakeDate <= '"
					+ "?date2?"
					+ "' and ManageCom Like concat('"
					+ "?ManageCom?"
					+ "','%')"
					+ " and FeeFinaType = 'LX'";

			// 保全利息统计
			tSql[13] = "Select '',(Select riskname From LMRiskApp Where riskcode=a),"
					+ "(case trim(b) when '1' then '团体险' when '2' then '个人营销' when '3' then '银行代理' when '4' then '电子商务' else '其它' end),d,e "
					+ "from (select a,b,(case when sum(c) is not null then sum(c)  else 0 end) d,count(*) e from (Select riskcode a,"
					+ "(select salechnl from lcpol where polno=ljagetendorse.polno) b,getmoney c "
					+ "From LJAGetEndorse Where MakeDate >='"
					+ "?date1?"
					+ "' and MakeDate <= '"
					+ "?date2?"
					+ "' and ManageCom Like concat('"
					+ "?ManageCom?"
					+ "','%') "
					+ " and FeeFinaType = 'LX' and getmoney<>0) group by a,b order by a,b)";

			for (int k = 1; k <= 13; k++) {
				if (tSql[k] == null) {
					continue;
				}
				logger.debug("SQL[" + k + "]=" + tSql[k]);
				ExeSQL tExeSQL = new ExeSQL();
				SQLwithBindVariables sqlbv60 = new SQLwithBindVariables();
				sqlbv60.sql(tSql[k]);
				sqlbv60.put("date1", mDay[0]);
				sqlbv60.put("date2", mDay[1]);
				sqlbv60.put("ManageCom", mGlobalInput.ManageCom);
				SSRS tSSRS = tExeSQL.execSQL(sqlbv60);
				int nRows = tSSRS.getMaxRow();

				for (int i = 1; i <= nRows; i++) {
					strArr = new String[5];
					for (int j = 0; j < 5; j++) {
						strArr[j] = tSSRS.GetText(i, j + 1);
					}
					tlisttable.add(strArr);
				}
			}

			strArr = new String[5];
			strArr[0] = "BQ";
			strArr[1] = "RiskName";
			strArr[2] = "Chnl";
			strArr[3] = "Money";
			strArr[4] = "Count";

			xmlexport.addListTable(tlisttable, strArr);
			SQLwithBindVariables sqlbv61 = new SQLwithBindVariables();
			String nsql = "select Name from LDCom where ComCode='"
				+ "?ManageCom?" + "'";
			sqlbv61.sql(nsql);
			sqlbv61.put("ManageCom", mGlobalInput.ManageCom);
			ExeSQL nExeSQL = new ExeSQL();
			SSRS nSSRS = nExeSQL.execSQL(sqlbv61);
			String manageCom = nSSRS.GetText(1, 1);
			TextTag texttag = new TextTag();
			texttag.add("StartDate", mDay[0]);
			texttag.add("EndDate", mDay[1]);
			texttag.add("ManageCom", manageCom);

			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			mResult.clear();
			mResult.addElement(xmlexport);

			return true;
		}
	 public boolean getPrintDataPayMode() {
			    String strArr[] = null;
//			    XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
//				xmlexport.createDocument("FinDayCheckModePay.vts", "printer");// 最好紧接着就初始化xml文档
			    XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例
				xmlexport.createDocument("暂收费方式日结打印", "", "", "0");
			       ListTable tlistTable = new ListTable();
			    	      String msql="";
			    	      SQLwithBindVariables sqlbv62 = new SQLwithBindVariables();
			      msql = "select (case m.paymode when '集中批收' then '集中批收' else (select codename from ldcode where codetype = 'paymode' and trim(code) = trim(m.paymode)) end), "
			    	  + "m.bankcode,(case paymode when '2' then m.chequeno when '3' then m.chequeno when '4' then (select codename from ldcode where codetype = 'bank' and trim(code) = trim(m.bankcode)) when '6' then (select codename from ldcode where codetype = 'bank' and trim(code) = trim(m.bankcode)) when '集中批收' then (select codename from ldcode where codetype = 'bank' and trim(code) = trim(m.bankcode)) else '' end),'', "
			    	  + "sum(m.money) c,count(1) "
			    	  + "from (select (case b.paymode when 'A' then '4' when 'B' then '4' else b.paymode end) paymode, "
			    	  + "(case paymode when '4' then b.bankcode when 'B' then b.bankcode when '2' then b.bankcode when '3' then b.bankcode when '6' then b.bankcode else '' end) bankcode, "
			    	  + "(case b.paymode when '2' then b.chequeno when '3' then b.chequeno else '' end) chequeno, "
			    	  + "(case when b.paymoney is not null then b.paymoney  else 0 end) money "
			    	  + "from LJTempFeeClass b "
			    	  + "where ConfMakeDate >= '"+"?date1?"+"' and ConfMakeDate <= '"+"?date2?"+"' "
			    	  + "and ManageCom like concat('"+"?ManageCom?"+"','%') "
			    	  + "and ((paymode not in ('4', 'A', 'B')) or "
			    	  + "((paymode in ('4', 'A', 'B')) and exists (select 1 from ldcode where codetype = 'bank' and trim(code) = trim(b.bankcode) and rpad(trim(comcode), 6, '00') = substr(b.managecom, 1, 6)))) "
			    	  + "union all "
			    	  + "select '集中批收' paymode,b.bankcode bankcode,'' chequeno,(case when b.paymoney is not null then b.paymoney else 0 end) money "
			    	  + "from LJTempFeeClass b "
			    	  + "where ConfMakeDate >= '"+"?date1?"+"' and ConfMakeDate <= '"+"?date2?"+"' "
			    	  + "and ManageCom like concat('"+"?ManageCom?"+"','%') "
			    	  + "and ((paymode in ('4', 'A', 'B') and exists (select 1 from ldcode where codetype = 'bank' and trim(code) = trim(b.bankcode) and rpad(trim(comcode), 6, '00') <> substr(b.managecom, 1, 6))))) m "
			    	  + "group by m.paymode, m.bankcode, m.chequeno "
			    	  + "order by m.paymode, m.bankcode, m.chequeno ";

					tlistTable.setName("RISK1");
					sqlbv62.sql(msql);
					sqlbv62.put("date1", mDay[0]);
					sqlbv62.put("date2", mDay[1]);
					sqlbv62.put("ManageCom", mGlobalInput.ManageCom);
					logger.debug("msql="+msql);
					SSRS tSSRS = new SSRS();
					ExeSQL tExeSQL = new ExeSQL();
					tSSRS = tExeSQL.execSQL(sqlbv62);
					for (int i = 1; i <= tSSRS.MaxRow; i++) {
						strArr = new String[6];
						strArr[0] = tSSRS.GetText(i, 1);
						strArr[1] = tSSRS.GetText(i, 2);
						strArr[2] = tSSRS.GetText(i, 3);
						strArr[3] = tSSRS.GetText(i, 4);
						strArr[4] = tSSRS.GetText(i, 5);
						strArr[5] = tSSRS.GetText(i, 6);
						tlistTable.add(strArr);
						
					}
					strArr = new String[6];
					strArr[0] = "Risk"; 
					strArr[1] ="BankCodeOrCheckNo";
					strArr[2] = "";
					strArr[3] = "";
					strArr[4] = "Money";
					strArr[5] = "Count";
					xmlexport.addListTable(tlistTable, strArr);
					SQLwithBindVariables sqlbv63 = new SQLwithBindVariables();
					String nsql = "select Name from LDCom where ComCode='"
							+ "?ManageCom?" + "'";
					sqlbv63.sql(nsql);
					sqlbv63.put("ManageCom", mGlobalInput.ManageCom);
					ExeSQL nExeSQL = new ExeSQL();
					SSRS nSSRS = new SSRS();
					nSSRS = nExeSQL.execSQL(sqlbv63);
					String manageCom = nSSRS.GetText(1, 1);

					TextTag texttag = new TextTag();// 新建一个TextTag的实例
					texttag.add("StartDate", mDay[0]);
					texttag.add("EndDate", mDay[1]);
					texttag.add("ManageCom", manageCom);
					if (texttag.size() > 0)
						xmlexport.addTextTag(texttag);
					mResult.clear();
					mResult.addElement(xmlexport);
					return true;
		
		}
	 public boolean getPrintDataGetMode() {
		  
		    String strArr[] = null;
//		    XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
//			xmlexport.createDocument("FinDayCheckModeGet.vts", "printer");// 最好紧接着就初始化xml文档
		    XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例
			xmlexport.createDocument("业务支出方式日结打印", "", "", "0");
		       ListTable tlistTable = new ListTable();
		    	      String msql="";
		    	      SQLwithBindVariables sqlbv64 = new SQLwithBindVariables();
		      msql = "select (case m.paymode when '集中批付' then '集中批付' else (select codename from ldcode where codetype = 'paymode' and trim(code) = trim(m.paymode)) end), "
					+ "bankcode,(case paymode when '2' then m.chequeno when '3' then m.chequeno when '4' then (select codename from ldcode where codetype = 'bank' and trim(code) = trim(m.bankcode)) when '集中批付' then (select codename from ldcode where codetype = 'bank' and trim(code) = trim(m.bankcode)) else '' end),'', "
					+ "sum(m.money),count(1) "
					+ "from (select b.paymode paymode,(case paymode when '4' then b.bankcode when '2' then b.bankcode when '3' then b.bankcode else '' end) bankcode, "
					+ "(case b.paymode when '2' then b.chequeno when '3' then b.chequeno else '' end) chequeno,(case when b.getmoney is not null then b.getmoney  else 0 end) money "
					+ "from LJFIGet b "
					+ "where ConfDate >= '"+"?date1?"+"' and ConfDate <= '"+"?date2?"+"' "
					+ "and ManageCom like concat('"+"?ManageCom?"+"','%') "
					+ "and (paymode <> '4' or "
					+ "(paymode = '4' and exists (select 1 from ldbank where bankcode = b.bankcode and rpad(trim(comcode), 6, '00') = substr(b.managecom, 1, 6)))) "
					+ "union all "
					+ "select '集中批付' paymode,b.bankcode,'' chequeno,(case when b.getmoney is not null then b.getmoney  else 0 end) money "
					+ "from LJFIGet b "
					+ "where ConfDate >= '"+"?date1?"+"' and ConfDate <= '"+"?date2?"+"' "
					+ "and ManageCom like concat('"+"?ManageCom?"+"','%') "
					+ "and paymode = '4' "
					+ "and exists (select 1 from ldbank where bankcode = b.bankcode and rpad(trim(comcode), 6, '00') <> substr(b.managecom, 1, 6))) m "
					+ "group by m.paymode, m.bankcode, m.chequeno "
					+ "order by m.paymode, m.bankcode, m.chequeno";

				tlistTable.setName("RISK1");
				sqlbv64.sql(msql);
				sqlbv64.put("date1", mDay[0]);
				sqlbv64.put("date2", mDay[1]);
				sqlbv64.put("ManageCom", mGlobalInput.ManageCom);
				logger.debug("msql="+msql);
				SSRS tSSRS = new SSRS();
				ExeSQL tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(sqlbv64);
				for (int i = 1; i <= tSSRS.MaxRow; i++) {
					strArr = new String[6];
					strArr[0] = tSSRS.GetText(i, 1);
					strArr[1] = tSSRS.GetText(i, 2);
					strArr[2] = tSSRS.GetText(i, 3);
					strArr[3] = tSSRS.GetText(i, 4);
					strArr[4] = tSSRS.GetText(i, 5);
					strArr[5] = tSSRS.GetText(i, 6);
					tlistTable.add(strArr);
					
				}
				strArr = new String[6];
				strArr[0] = "Risk"; 
				strArr[1] ="BankCodeOrCheckNo";
				strArr[2] = ""; 
				strArr[3] ="";
				strArr[4] = "Money";
				strArr[5] = "Count";
				xmlexport.addListTable(tlistTable, strArr);
				SQLwithBindVariables sqlbv65 = new SQLwithBindVariables();
				String nsql = "select Name from LDCom where ComCode='"
						+ "?ManageCom?" + "'";
				sqlbv65.sql(nsql);
				sqlbv65.put("ManageCom", mGlobalInput.ManageCom);
				ExeSQL nExeSQL = new ExeSQL();
				SSRS nSSRS = new SSRS();
				nSSRS = nExeSQL.execSQL(sqlbv65);
				String manageCom = nSSRS.GetText(1, 1);

				TextTag texttag = new TextTag();// 新建一个TextTag的实例
				texttag.add("StartDate", mDay[0]);
				texttag.add("EndDate", mDay[1]);
				texttag.add("ManageCom", manageCom);
				if (texttag.size() > 0)
					xmlexport.addTextTag(texttag);
				mResult.clear();
				mResult.addElement(xmlexport);
				return true;
	
	}
	 //管理费收入
	 private boolean getPrintDataGLFY(){

		    SSRS tSSRS = new SSRS();
		    SSRS nSSRS = new SSRS();
		    double SumMoney = 0;
		    long SumNumber=0;
		    String strArr[] = null;
//		    XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
//			xmlexport.createDocument("FinDayCheckPayGLFY.vts", "printer");// 最好紧接着就初始化xml文档
		    XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例
			xmlexport.createDocument("管理费收入日结打印", "", "", "0");
		       ListTable tlistTable = new ListTable();
		      ExeSQL tExeSQL = new ExeSQL();
		      String msql="";
		      SQLwithBindVariables sqlbv66 = new SQLwithBindVariables();
		      msql = "select riskcode,(select riskname from lmriskapp where riskcode=A.riskcode), "
		      + "(select codename from ldcode where codetype='salechnl' and code=A.salechnl),sum(A.fee),count(1)   "
		      + "from  (select p.riskcode riskcode,p.salechnl salechnl,(select (case when sum(fee) is not null then sum(fee)  else 0 end) from lcinsureaccfeetrace where grppolno=m.grppolno and makedate=m.makedate and feecode like '633%') fee "
		      + " from ljapaygrp m ,lcgrppol p  "
		      + "where confdate>='"+"?date1?"+"' and confdate<='"+"?date2?"+"' and paytype='TM'  "
		      + "and m.grppolno=p.grppolno and m.managecom like concat('"+"?ManageCom?"+"','%')  "
		      + "and not exists (select 1 from ljapay where payno=m.payno and incometype='3') and  "
		      + "not exists (select 1 from dual where p.appflag='1' and p.StandbyFlag4='1' and m.paycount='1')  "
		      + "union all  "
		      + "select p.riskcode riskcode ,p.salechnl salechnl,(select (case when sum(fee) is not null then sum(fee)  else 0 end) from lcinsureaccfeetrace where grppolno=m.grppolno and makedate=m.makedate and feecode like '633%') fee  "
		      + "from ljapaygrp m ,lcgrppol p  "
		      + "where confdate>='"+"?date1?"+"' and confdate<='"+"?date2?"+"' and paytype='TM'  "
		      + "and m.grppolno=p.grppolno and m.managecom like concat('"+"?ManageCom?"+"','%')  "
		      + "and  exists (select 1 from ljapay where payno=m.payno and incometype='3') )A  "
		      + "group by A.riskcode,A.salechnl   ";
				tlistTable.setName("RISK");
				sqlbv66.sql(msql);
				sqlbv66.put("date1", mDay[0]);
				sqlbv66.put("date2", mDay[1]);
				sqlbv66.put("ManageCom", mGlobalInput.ManageCom);
				logger.debug("msql"+msql);
				tSSRS = tExeSQL.execSQL(sqlbv66);
				for (int i = 1; i <= tSSRS.MaxRow; i++) {
					strArr = new String[5];
	
					strArr[0] = tSSRS.GetText(i, 1);
					strArr[1] = tSSRS.GetText(i, 2);
					strArr[2] = tSSRS.GetText(i, 3);
					strArr[3] = tSSRS.GetText(i, 4);
					SumMoney = SumMoney + Double.parseDouble(strArr[3]);
					String strSum = new DecimalFormat("0.00").format(Double
							.valueOf(strArr[3]));
					strArr[3] = strSum;
					strArr[4]= tSSRS.GetText(i, 5);
					tlistTable.add(strArr);
					
				}
				strArr = new String[5];
				strArr[0] = "Risk";
				strArr[1] = "";
				strArr[2] = "";
				strArr[3] = "Money";
				strArr[4] = "Mult";
				xmlexport.addListTable(tlistTable, strArr);

				String tSumMoney = String.valueOf(SumMoney);
				tSumMoney = new DecimalFormat("0.00").format(Double.valueOf(tSumMoney));
				strArr = new String[5];
				strArr[0] = "Risk";
				strArr[1] = "";
				strArr[2] = "";
				strArr[3] = "Money";
				strArr[4] = "Mult";
				SQLwithBindVariables sqlbv67 = new SQLwithBindVariables();
				String nsql = "select Name from LDCom where ComCode='"
						+ "?ManageCom?" + "'";
				sqlbv67.sql(nsql);
				sqlbv67.put("ManageCom", mGlobalInput.ManageCom);
				ExeSQL nExeSQL = new ExeSQL();
				nSSRS = nExeSQL.execSQL(sqlbv67);
				String manageCom = nSSRS.GetText(1, 1);

				TextTag texttag = new TextTag();// 新建一个TextTag的实例
				texttag.add("StartDate", mDay[0]);
				texttag.add("EndDate", mDay[1]);
				texttag.add("ManageCom", manageCom);
				texttag.add("SumMoney", tSumMoney);
				texttag.add("SumNumber", SumNumber);
				if (texttag.size() > 0)
					xmlexport.addTextTag(texttag);
				mResult.clear();
				mResult.addElement(xmlexport);
				return true;
		  
	  }
	 //航意险
	 private boolean getPrintDataPayAir(){

		    SSRS tSSRS = new SSRS();
		    SSRS nSSRS = new SSRS();
		    double SumMoney = 0;
		    long SumNumber=0;
		    String strArr[] = null;
//		    XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
//			xmlexport.createDocument("FinDayCheckPayAir.vts", "printer");// 最好紧接着就初始化xml文档
		    XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例
			xmlexport.createDocument("航意险应收日结打印", "", "", "0");
			
			ListTable tlistTable = new ListTable();
		    ExeSQL tExeSQL = new ExeSQL();
		    String msql="";
		    SQLwithBindVariables sqlbv68 = new SQLwithBindVariables();
		    msql = "select riskcode,  (select riskname from LMRisk where riskcode=a.riskcode),sum(prem),agentcom  "
		    	  + "from(  "
		    	  + "select riskcode, polno, (case when prem is not null then prem  else 0 end) prem, agentcom    "
		    	  + "from lcairpol where makedate >= '"+"?date1?"+"'  and makedate <= '"+"?date2?"+"'   "
		    	  + "and managecom like concat('"+"?ManageCom?"+"','%')  and state in ('A', 'B', 'C')   "
		    	  + "union all  "
		    	  + "select  riskcode,polno, (case when (prem * -1) is not null then (prem * -1)  else 0 end) prem, agentcom   "
		    	  + "from lcairpol where ModifyDate >= '"+"?date1?"+"' and ModifyDate <= '"+"?date2?"+"'   "
		    	  + "and managecom like concat('"+"?ManageCom?"+"','%')  and state in ('A', 'B', 'C') and polstate = '2' ) a  "
		    	  + "group by RiskCode,agentcom "
		    	  + "union all "
		    	  + "select riskcode,  (select riskname from LMRisk where riskcode=a.riskcode),sum(prem),agentcom  "
		    	  + "from(  "
		    	  + "select riskcode, polno, (case when prem is not null then prem  else 0 end) prem, agentcom    "
		    	  + "from lbairpol where makedate >= '"+"?date1?"+"'  and makedate <= '"+"?date2?"+"'   "
		    	  + "and managecom like concat('"+"?ManageCom?"+"','%')  and state in ('A', 'B', 'C')   "
		    	  + "union all  "
		    	  + "select  riskcode,polno, (case when (prem * -1) is not null then (prem * -1)  else 0 end) prem, agentcom   "
		    	  + "from lbairpol where ModifyDate >= '"+"?date1?"+"' and ModifyDate <= '"+"?date2?"+"'   "
		    	  + "and managecom like concat('"+"?ManageCom?"+"','%')  and state in ('A', 'B', 'C') and polstate = '2' ) a  "
		    	  + "group by RiskCode,agentcom ";

				tlistTable.setName("RISK");
				sqlbv68.sql(msql);
				sqlbv68.put("date1", mDay[0]);
				sqlbv68.put("date2", mDay[1]);
				sqlbv68.put("ManageCom", mGlobalInput.ManageCom);
				logger.debug("msql"+msql);
				tSSRS = tExeSQL.execSQL(sqlbv68);
				for (int i = 1; i <= tSSRS.MaxRow; i++) {
					strArr = new String[4];
					// 险种编码
					strArr[0] = tSSRS.GetText(i, 1);
					// 险种名称
					if (tSSRS.GetText(i, 2).equals(""))
						strArr[1] = tSSRS.GetText(i, 1);
					else
						strArr[1] = tSSRS.GetText(i, 2);

					// 金额
					strArr[2] = tSSRS.GetText(i, 3);
					SumMoney = SumMoney + Double.parseDouble(strArr[2]);
					String strSum = new DecimalFormat("0.00").format(Double
							.valueOf(strArr[2]));
					strArr[2] = strSum;

					//代理网点
					strArr[3]= tSSRS.GetText(i, 4);
					
					tlistTable.add(strArr);
					
				}
				strArr = new String[5];
				strArr[0] = "Risk";
				strArr[1] = "";
				strArr[2] = "Money";
				strArr[3] = "Mult";
				xmlexport.addListTable(tlistTable, strArr);

				String tSumMoney = String.valueOf(SumMoney);
				tSumMoney = new DecimalFormat("0.00").format(Double.valueOf(tSumMoney));
				strArr = new String[4];
				strArr[0] = "Risk";
				strArr[1] = "";
				strArr[2] = "Money";
				strArr[3] = "Mult";
				SQLwithBindVariables sqlbv69 = new SQLwithBindVariables();
				String nsql = "select Name from LDCom where ComCode='"
						+ "?ManageCom?" + "'";
				sqlbv69.sql(nsql);
				sqlbv69.put("ManageCom", mGlobalInput.ManageCom);
				ExeSQL nExeSQL = new ExeSQL();
				nSSRS = nExeSQL.execSQL(sqlbv69);
				String manageCom = nSSRS.GetText(1, 1);

				TextTag texttag = new TextTag();// 新建一个TextTag的实例
				texttag.add("StartDate", mDay[0]);
				texttag.add("EndDate", mDay[1]);
				texttag.add("ManageCom", manageCom);
				texttag.add("SumMoney", tSumMoney);
				texttag.add("SumNumber", SumNumber);
				if (texttag.size() > 0)
					xmlexport.addTextTag(texttag);
				// xmlexport.outputDocumentToFile("e:\\","FinDayCheckBL.xml");//输出xml文档到文件
				mResult.clear();
				mResult.addElement(xmlexport);
				return true;
		  
	  }
	// 其他应付
	private boolean PrintQTYF() {
//		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
//		xmlexport.createDocument("FinDayCheckQTYF.vts", "printer"); // 最好紧接着就初始化xml文档
		XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例
		xmlexport.createDocument("其它应付日结打印", "", "", "0");
		String[] strArr = null;
		ListTable tlistTable = new ListTable();
		tlistTable.setName("RISK1");
		String strSql = "";
		SQLwithBindVariables sqlbv70 = new SQLwithBindVariables();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strSql ="select feefinatype,riskcode,(select riskname from lmrisk where riskcode=m.riskcode),(case when (select codename  from ldcode where codetype = 'salechnl' and code = m.salechnl) is not null then (select codename  from ldcode where codetype = 'salechnl' and code = m.salechnl)  else '' end),sum(money),count(1) from ( "
					+ "select '暂收退费' feefinatype , riskcode riskcode,'' salechnl , (case when getmoney is not null then getmoney  else 0 end) money from LJAGetTempFee   "
					+ "where MakeDate >='"+ "?date1?"+"' and MakeDate <= '"+ "?date2?"+"' and ManageCom Like concat('"+ "?ManageCom?" +"','%') "
					+ "union all  "
					+ "select '预收退费' feefinatype, '' riskcode,'' salechnl,(case when a.getmoney is not null then a.getmoney  else 0 end) money from LJAGetOther a "
					+ "where makeDate >='"+ "?date1?"+"' and makeDate <= '"+ "?date2?"+"' and manageCom Like concat('"+ "?ManageCom?" +"','%')   "
					//zy 2009-06-30 溢交退费不带险种
//					+ "select '预收退费' feefinatype, (select riskcode from lcpol where contno=a.otherno and mainpolno=polno  and rownum=1) riskcode,'' salechnl,nvl(a.getmoney,0) money from LJAGetOther a "
//					+ "where makeDate >='"+ mDay[0]+"' and makeDate <= '"+ mDay[1]+"' and manageCom Like '"+ mGlobalInput.ManageCom +"%'  and othernotype='0' "
//					+ "union all  "
//					+ "select '预收退费' feefinatype, (select riskcode from lcgrppol where grpcontno=a.otherno and rownum=1 ) riskcode,'' salechnl,nvl(a.getmoney,0) money from LJAGetOther a "
//					+ "where  makeDate >='"+ mDay[0]+"' and makeDate <= '"+ mDay[1]+"' and manageCom Like '"+ mGlobalInput.ManageCom +"%'   and othernotype='1'  "
//					+ "union all  "
//					+ "select '预收退费' feefinatype, (select riskcode from lcpol where contno = (select contno from lccont where familycontno=a.otherno and rownum=1 ) and mainpolno=polno and rownum=1 ) riskcode,'' salechnl,nvl(a.GetMoney,0) money from LJAGetOther a "
//					+ "where  makeDate >='"+ mDay[0]+"' and makeDate <= '"+ mDay[1]+"' and manageCom Like '"+ mGlobalInput.ManageCom +"%'   and othernotype='2'  "
					+ "union all  "
					+ "select (case a.insuaccno when '000001' then '红利累计领取' else (case when (select codename from ldcode where codetype='feefinatype' and code=trim(a.moneytype)) is not null then (select codename from ldcode where codetype='feefinatype' and code=trim(a.moneytype))  else '其他' end) end) feefinatype,riskcode riskcode ,(select salechnl from lcpol where polno=a.polno and rownum=1) salechnl,(case when money is not null then money  else 0 end) money from LCInsureAccTrace a  "
					+ "where makeDate>='"+"?date1?"+"' and makeDate <= '"+ "?date2?"+"' and manageCom like concat('"+ "?ManageCom?" +"','%') and  grpcontno = '00000000000000000000' and moneytype in ('HL','HLLX','DJLX','YCLX') "
					+ "union all  "
					+ "select '红利累计领取' feefinatype,riskcode riskcode ,(select salechnl from lcgrppol where grppolno=a.grppolno and rownum=1) salechnl,(case when money is not null then money  else 0 end) money from LCInsureAccTrace a  "
					+ "where makeDate>='"+ "?date1?"+"' and makeDate <= '"+ "?date2?"+"' and manageCom like concat('"+ "?ManageCom?" +"','%')  and  grpcontno <> '00000000000000000000' and  moneytype in ('HL','HLLX') "
					+ "union all  "
					+ "select (case when (select codename from ldcode where codetype='feefinatype' and code=trim(a.feefinatype)) is not null then (select codename from ldcode where codetype='feefinatype' and code=trim(a.feefinatype))  else '其他' end) feefinatype,riskcode riskcode,(select salechnl from lcpol where polno=a.polno and rownum=1) salechnl,(case when getmoney is not null then getmoney  else 0 end) money From LJABonusGet a "
					+ "where makeDate >='"+"?date1?"+"' and makeDate <= '"+ "?date2?"+"' and manageCom Like concat('"+ "?ManageCom?" +"','%') and feefinatype in ('LJTF','HLTF'))m "
					+ "group by m.feefinatype,m.riskcode,m.salechnl "
					+ "order by m.feefinatype,m.riskcode,m.salechnl ";;
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strSql ="select feefinatype,riskcode,(select riskname from lmrisk where riskcode=m.riskcode),(case when (select codename  from ldcode where codetype = 'salechnl' and code = m.salechnl) is not null then (select codename  from ldcode where codetype = 'salechnl' and code = m.salechnl)  else '' end),sum(money),count(1) from ( "
					+ "select '暂收退费' feefinatype , riskcode riskcode,'' salechnl , (case when getmoney is not null then getmoney  else 0 end) money from LJAGetTempFee   "
					+ "where MakeDate >='"+ "?date1?"+"' and MakeDate <= '"+ "?date2?"+"' and ManageCom Like concat('"+ "?ManageCom?" +"','%') "
					+ "union all  "
					+ "select '预收退费' feefinatype, '' riskcode,'' salechnl,(case when a.getmoney is not null then a.getmoney  else 0 end) money from LJAGetOther a "
					+ "where makeDate >='"+ "?date1?"+"' and makeDate <= '"+ "?date2?"+"' and manageCom Like concat('"+ "?ManageCom?" +"','%')   "
					//zy 2009-06-30 溢交退费不带险种
//					+ "select '预收退费' feefinatype, (select riskcode from lcpol where contno=a.otherno and mainpolno=polno  and rownum=1) riskcode,'' salechnl,nvl(a.getmoney,0) money from LJAGetOther a "
//					+ "where makeDate >='"+ mDay[0]+"' and makeDate <= '"+ mDay[1]+"' and manageCom Like '"+ mGlobalInput.ManageCom +"%'  and othernotype='0' "
//					+ "union all  "
//					+ "select '预收退费' feefinatype, (select riskcode from lcgrppol where grpcontno=a.otherno and rownum=1 ) riskcode,'' salechnl,nvl(a.getmoney,0) money from LJAGetOther a "
//					+ "where  makeDate >='"+ mDay[0]+"' and makeDate <= '"+ mDay[1]+"' and manageCom Like '"+ mGlobalInput.ManageCom +"%'   and othernotype='1'  "
//					+ "union all  "
//					+ "select '预收退费' feefinatype, (select riskcode from lcpol where contno = (select contno from lccont where familycontno=a.otherno and rownum=1 ) and mainpolno=polno and rownum=1 ) riskcode,'' salechnl,nvl(a.GetMoney,0) money from LJAGetOther a "
//					+ "where  makeDate >='"+ mDay[0]+"' and makeDate <= '"+ mDay[1]+"' and manageCom Like '"+ mGlobalInput.ManageCom +"%'   and othernotype='2'  "
					+ "union all  "
					+ "select (case a.insuaccno when '000001' then '红利累计领取' else (case when (select codename from ldcode where codetype='feefinatype' and code=trim(a.moneytype)) is not null then (select codename from ldcode where codetype='feefinatype' and code=trim(a.moneytype))  else '其他' end) end) feefinatype,riskcode riskcode ,(select salechnl from lcpol where polno=a.polno limit 0,1) salechnl,(case when money is not null then money  else 0 end) money from LCInsureAccTrace a  "
					+ "where makeDate>='"+"?date1?"+"' and makeDate <= '"+ "?date2?"+"' and manageCom like concat('"+ "?ManageCom?" +"','%') and  grpcontno = '00000000000000000000' and moneytype in ('HL','HLLX','DJLX','YCLX') "
					+ "union all  "
					+ "select '红利累计领取' feefinatype,riskcode riskcode ,(select salechnl from lcgrppol where grppolno=a.grppolno limit 0,1) salechnl,(case when money is not null then money  else 0 end) money from LCInsureAccTrace a  "
					+ "where makeDate>='"+ "?date1?"+"' and makeDate <= '"+ "?date2?"+"' and manageCom like concat('"+ "?ManageCom?" +"','%')  and  grpcontno <> '00000000000000000000' and  moneytype in ('HL','HLLX') "
					+ "union all  "
					+ "select (case when (select codename from ldcode where codetype='feefinatype' and code=trim(a.feefinatype)) is not null then (select codename from ldcode where codetype='feefinatype' and code=trim(a.feefinatype))  else '其他' end) feefinatype,riskcode riskcode,(select salechnl from lcpol where polno=a.polno limit 0,1) salechnl,(case when getmoney is not null then getmoney  else 0 end) money From LJABonusGet a "
					+ "where makeDate >='"+"?date1?"+"' and makeDate <= '"+ "?date2?"+"' and manageCom Like concat('"+ "?ManageCom?" +"','%') and feefinatype in ('LJTF','HLTF'))m "
					+ "group by m.feefinatype,m.riskcode,m.salechnl "
					+ "order by m.feefinatype,m.riskcode,m.salechnl ";;
		}
						sqlbv70.sql(strSql);
						sqlbv70.put("date1", mDay[0]);
						sqlbv70.put("date2", mDay[1]);
						sqlbv70.put("ManageCom", mGlobalInput.ManageCom);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv70);
		logger.debug("strSql:" + strSql);

		double SumMoney = 0;
		int SumCount = 0;
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				double ThisMoney = Double.parseDouble(tSSRS.GetText(i, 5));
				int ThisCount = Integer.parseInt(tSSRS.GetText(i, 6));
				strArr = new String[6];
				strArr[0] = tSSRS.GetText(i, 1);
				strArr[1] = tSSRS.GetText(i, 2);
				strArr[2] = tSSRS.GetText(i, 3);
				strArr[3] = tSSRS.GetText(i, 4);
				strArr[4] = tSSRS.GetText(i, 5);
				strArr[5] = tSSRS.GetText(i, 6);
				tlistTable.add(strArr);
				SumMoney += ThisMoney;
				SumCount += ThisCount;
		}
			String tSumMoney = String.valueOf(SumMoney);
			tSumMoney = new DecimalFormat("0.00").format(Double.valueOf(tSumMoney));
		strArr = new String[6];
		SQLwithBindVariables sqlbv71 = new SQLwithBindVariables();
		String nsql = "select Name from LDCom where ComCode='"
			+ "?ManageCom?" + "'";
		sqlbv71.sql(nsql);
		sqlbv71.put("ManageCom", mGlobalInput.ManageCom);
		ExeSQL nExeSQL = new ExeSQL();
		SSRS nSSRS = nExeSQL.execSQL(sqlbv71);
		String manageCom = nSSRS.GetText(1, 1);
		xmlexport.addListTable(tlistTable, strArr);
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		texttag.add("StartDate", mDay[0]);
		texttag.add("EndDate", mDay[1]);
		texttag.add("ManageCom", manageCom);
		texttag.add("SumCount", SumCount);
		texttag.add("SumMoney", tSumMoney);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

	// 领取给付
	private boolean PrintLQJF() {
	//	XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		//xmlexport.createDocument("FinDayCheckLQJF.vts", "printer"); // 最好紧接着就初始化xml文档
		XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例
		xmlexport.createDocument("领取给付日结打印", "", "", "0");
		String[] strArr = null;
		ListTable tlistTable = new ListTable();
		tlistTable.setName("RISK1");
		SQLwithBindVariables sqlbv72 = new SQLwithBindVariables();
		String strSql =	"select (case when (select codename from ldcode where codetype = 'feefinatype'	 and code = trim(m.feefinatype)) is not null then (select codename from ldcode where codetype = 'feefinatype'	 and code = trim(m.feefinatype))  else '其他' end) type, m.riskcode, "
							+ "(select riskname from lmrisk where riskcode = m.riskcode), "
							+ "(select codename from ldcode where codetype = 'salechnl' and code = m.salechnl),sum(m.money),count(1) "
							+ "from (select moneytype feefinatype,riskcode riskcode,(select salechnl from lcpol where polno = a.polno) salechnl,(case when money is not null then money  else 0 end) money "
							+ "	From lcinsureacctrace a "
							+ " where makeDate >= '"+ "?date1?"+"' and makeDate <= '"+ "?date2?"+"' "
							+ "	 and managecom Like concat('"+ "?ManageCom?" +"','%') "
							+ "	 and grpcontno = '00000000000000000000' "
							+ "	 and moneytype in ('YF', 'EF', 'YFLX', 'EFLX'))  m "
							+ "group by m.feefinatype, m.riskcode, m.salechnl "
							+ "order by type, m.riskcode, m.salechnl ";
		sqlbv72.sql(strSql);
		sqlbv72.put("date1", mDay[0]);
		sqlbv72.put("date2", mDay[1]);
		sqlbv72.put("ManageCom", mGlobalInput.ManageCom);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv72);
		logger.debug("strSql:" + strSql);

		double SumMoney = 0;
		int SumCount = 0;
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				double ThisMoney = Double.parseDouble(tSSRS.GetText(i, 5));
				int ThisCount = Integer.parseInt(tSSRS.GetText(i, 6));
				strArr = new String[6];
				strArr[0] = tSSRS.GetText(i, 1);
				strArr[1] = tSSRS.GetText(i, 2);
				strArr[2] = tSSRS.GetText(i, 3);
				strArr[3] = tSSRS.GetText(i, 4);
				strArr[4] = tSSRS.GetText(i, 5);
				strArr[5] = tSSRS.GetText(i, 6);
				tlistTable.add(strArr);
				SumMoney += ThisMoney;
				SumCount += ThisCount;
		}
			String tSumMoney = String.valueOf(SumMoney);
			tSumMoney = new DecimalFormat("0.00").format(Double.valueOf(tSumMoney));
		strArr = new String[6];
		SQLwithBindVariables sqlbv73 = new SQLwithBindVariables();
		String nsql = "select Name from LDCom where ComCode='"
			+ "?ManageCom?" + "'";
		sqlbv73.sql(nsql);
		sqlbv73.put("ManageCom", mGlobalInput.ManageCom);
		ExeSQL nExeSQL = new ExeSQL();
		SSRS nSSRS = nExeSQL.execSQL(sqlbv73);
		String manageCom = nSSRS.GetText(1, 1);
	
		xmlexport.addListTable(tlistTable, strArr);
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		texttag.add("StartDate", mDay[0]);
		texttag.add("EndDate", mDay[1]);
		texttag.add("ManageCom", manageCom);
		texttag.add("SumCount", SumCount);
		texttag.add("SumMoney", tSumMoney);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	
	}

	// 保全应付 
	private boolean PrintBQYF() {

	//	XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		//xmlexport.createDocument("FinDayCheckBQYF.vts", "printer"); // 最好紧接着就初始化xml文档
		XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例
		xmlexport.createDocument("保全应付日结打印", "", "", "0");
		String[] strArr = null;
		ListTable tlistTable = new ListTable();
		tlistTable.setName("RISK1");
		SQLwithBindVariables sqlbv74 = new SQLwithBindVariables();
		String strSql =	"select (case when (select codename from ldcode where codetype = 'feefinatype' and code = trim(m.feefinatype)) is not null then (select codename from ldcode where codetype = 'feefinatype' and code = trim(m.feefinatype))  else '其他' end),riskcode, "
							+ "(select riskname from lmrisk where riskcode = m.riskcode), "
							+ "(select codename from ldcode where codetype = 'salechnl' and code = salechnl),sum(m.money),count(1),m.feefinatype "
							+ "  from (select feefinatype feefinatype,riskcode riskcode,(select salechnl from lcpol where polno = a.polno) salechnl,(case when GetMoney is not null then GetMoney  else 0 end) money "
							+ "from LJAGetEndorse a "
							+ "where MakeDate >= '"+ "?date1?"+"' and MakeDate <= '"+ "?date2?"+"' "
							+ "and ManageCom Like concat('"+ "?ManageCom?" +"','%') "
//							+ "and feefinatype not in ('LX', 'BF', 'GB', 'HK', 'HD', 'BD')) m "
							//zy 2009-06-19 保持与财务接口一致
							+ "and feefinatype in ('TF','TB','YE','DK','DJ','RV','LJTF')) m "
							+ "group by m.feefinatype, m.riskcode, m.salechnl "
							+ "order by m.feefinatype, m.riskcode, m.salechnl";
		sqlbv74.sql(strSql);
		sqlbv74.put("date1", mDay[0]);
		sqlbv74.put("date2", mDay[1]);
		sqlbv74.put("ManageCom", mGlobalInput.ManageCom);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv74);
		logger.debug("strSql:" + strSql);

		double SumMoney = 0;
		int SumCount = 0;
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				double ThisMoney = Double.parseDouble(tSSRS.GetText(i, 5));
				int ThisCount = Integer.parseInt(tSSRS.GetText(i, 6));
				strArr = new String[6];
				strArr[0] = tSSRS.GetText(i, 1);
				strArr[1] = tSSRS.GetText(i, 2);
				strArr[2] = tSSRS.GetText(i, 3);
				strArr[3] = tSSRS.GetText(i, 4);
				strArr[4] = tSSRS.GetText(i, 5);
				strArr[5] = tSSRS.GetText(i, 6);
				tlistTable.add(strArr);
				if("RV".equals(tSSRS.GetText(i, 7)))
					continue;
				SumMoney += ThisMoney;
				SumCount += ThisCount;
		}
			String tSumMoney = String.valueOf(SumMoney);
			tSumMoney = new DecimalFormat("0.00").format(Double.valueOf(tSumMoney));
		strArr = new String[6];
		xmlexport.addListTable(tlistTable, strArr);
		SQLwithBindVariables sqlbv75 = new SQLwithBindVariables();
		String nsql = "select Name from LDCom where ComCode='"
			+ "?ManageCom?" + "'";
		sqlbv75.sql(nsql);
		sqlbv75.put("ManageCom", mGlobalInput.ManageCom);
		ExeSQL nExeSQL = new ExeSQL();
		SSRS nSSRS = nExeSQL.execSQL(sqlbv75);
		String manageCom = nSSRS.GetText(1, 1);
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		texttag.add("StartDate", mDay[0]);
		texttag.add("EndDate", mDay[1]);
		texttag.add("ManageCom", manageCom);
		texttag.add("SumCount", SumCount);
		texttag.add("SumMoney", tSumMoney);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}
	
	// 赔款支出
	private boolean PrintPKZC() {
//		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
//		xmlexport.createDocument("FinDayCheckPKZC.vts", "printer"); // 最好紧接着就初始化xml文档
		XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例
		xmlexport.createDocument("赔款支出日结打印", "", "", "0");
		String[] strArr = null;
		ListTable tlistTable = new ListTable();
		tlistTable.setName("RISK1");
		SQLwithBindVariables sqlbv76 = new SQLwithBindVariables();
		String strSql = "select (case when (select codename from ldcode where codetype = 'feefinatype'	and code = trim(m.feefinatype)) is not null then (select codename from ldcode where codetype = 'feefinatype'	and code = trim(m.feefinatype))  else '其他' end),riskcode, "
			 			+ "(select riskname from lmrisk where riskcode = m.riskcode), "
			 			+ "(select codename from ldcode where codetype = 'salechnl' and code = m.salechnl),(case when sum(Pay) is not null then sum(Pay)  else 0 end),count(1) "
			 			+ "from LJAGetClaim m "
			 			+ "Where MakeDate >= '"+ "?date1?"+"' and MakeDate <= '"+"?date2?"+"' "
			 			+ " and ManageCom Like concat('"+ "?ManageCom?" +"','%') "
			 			+ "and feefinatype not in ('HDLX', 'HKLX', 'BF', 'HK', 'HD', 'HLRB', 'YFRB','HLTF', 'DJTF', 'XJTF', 'LJTF') "
			 			+ "group by feefinatype, riskcode, salechnl  order by feefinatype, riskcode, salechnl ";
		sqlbv76.sql(strSql);
		sqlbv76.put("date1", mDay[0]);
		sqlbv76.put("date2", mDay[1]);
		sqlbv76.put("ManageCom", mGlobalInput.ManageCom);
		
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv76);
		logger.debug("strSql:" + strSql);

		double SumMoney = 0;
		int SumCount = 0;
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				double ThisMoney = Double.parseDouble(tSSRS.GetText(i, 5));
				int ThisCount = Integer.parseInt(tSSRS.GetText(i, 6));
				strArr = new String[6];
				strArr[0] = tSSRS.GetText(i, 1);
				strArr[1] = tSSRS.GetText(i, 2);
				strArr[2] = tSSRS.GetText(i, 3);
				strArr[3] = tSSRS.GetText(i, 4);
				strArr[4] = tSSRS.GetText(i, 5);
				strArr[5] = tSSRS.GetText(i, 6);
				tlistTable.add(strArr);
				SumMoney += ThisMoney;
				SumCount += ThisCount;
		}
			
			String tSumMoney = String.valueOf(SumMoney);
			tSumMoney = new DecimalFormat("0.00").format(Double.valueOf(tSumMoney));
		strArr = new String[6];
		xmlexport.addListTable(tlistTable, strArr);
		SQLwithBindVariables sqlbv77 = new SQLwithBindVariables();
		String nsql = "select Name from LDCom where ComCode='"
			+ "?ManageCom?" + "'";
		sqlbv77.sql(nsql);
		sqlbv77.put("ManageCom", mGlobalInput.ManageCom);
		ExeSQL nExeSQL = new ExeSQL();
		SSRS nSSRS = nExeSQL.execSQL(sqlbv77);
		String manageCom = nSSRS.GetText(1, 1);
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		texttag.add("StartDate", mDay[0]);
		texttag.add("EndDate", mDay[1]);
		texttag.add("ManageCom", manageCom);
		texttag.add("SumCount", SumCount);
		texttag.add("SumMoney", tSumMoney);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}


	private void AddTag(XmlExport xmlExport) {
		TextTag tag = new TextTag();
		tag.add("StartDate", this.mDay[0]);
		tag.add("EndDate", this.mDay[1]);
		tag.add("ManageCom", mGlobalInput.ManageCom);
		tag.add("ALLSUM", ReportPubFun.functionJD(mAllsum, "0.00"));
		tag.add("ALLCOUNT", mAllcount);
		xmlExport.addTextTag(tag);
	}

	private void XianZhong(SQLwithBindVariables sql, String code, String name,
			XmlExport xmlExport) {
		logger.debug("sql:" + sql);
		ZHashReport rep = new ZHashReport(sql);
		rep.setColumnType(1, rep.StringType);
		rep.setColumnType(3, rep.IntType);
		rep.setSumColumn(true);
		rep.addToXML(xmlExport, code);
		double sum = Double.parseDouble(rep.getSumCols()[2]);
		int count = Integer.parseInt(rep.getSumCols()[3]);
		mAllsum += sum;
		mAllcount += count;
		TextTag tag = new TextTag();
		tag.add(code + "_DESC", name);
		tag.add(code + "_SUM", ReportPubFun.functionJD(sum, "0.00"));
		tag.add(code + "_COUNT", String.valueOf(count));
		xmlExport.addTextTag(tag);

	}
	
}
