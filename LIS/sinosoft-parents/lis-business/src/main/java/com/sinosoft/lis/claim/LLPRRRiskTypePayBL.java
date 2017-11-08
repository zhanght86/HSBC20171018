package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔报表打印：险种类别给付统计--LLPRRRiskTypePayBL.vts
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司、用户）、机构统计范围选项（总公司、某分公司、某中支）、
 * 统计口径（审批通过日期和结案日期）、统计时间段、渠道选项（团、个、全部渠道）、险种选项（分险种统计、不分险种统计） 表头：报表名称、统计条件、统计日期
 * 数据项：机构（或用户）、险种、短期医疗险（含极短险）、人寿险、意外险和重疾险的给付数量和金额、
 * 拒付数据和金额、给付金额权重、拒付金额权重（权重计算仅限于不分险种计算的选项被选中时） 算法：按照选择的条件统计各项数据，按照保项层面统计
 * 排序：机构（或用户） 表尾：各项数据的合计
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhaoy，2005-9-13 9:50 modify by wanzh 2005/11/16
 * @version 1.0
 */
public class LLPRRRiskTypePayBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRRiskTypePayBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	// private String mPrtCode = "PCT005" ; //打印编码
	private String strStartDate = "";
	private String strEndDate = "";
	private String strSaleType = "";// 渠道选项
	private String strRiskCode = "";
	private String strLevel = "";
	private String strManageCom = "";
	private String strStatDem = "";// 统计口径
	private String[] cols;

	public LLPRRRiskTypePayBL() {
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
		if (!dealData()) {
			return false;
		}
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
		this.mInputData = cInputData;
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.strStartDate = (String) mTransferData.getValueByName("StartDate");
		this.strEndDate = (String) mTransferData.getValueByName("EndDate");
		this.strSaleType = (String) mTransferData.getValueByName("SaleType");
		this.strRiskCode = (String) mTransferData.getValueByName("RiskCode");
		this.strLevel = (String) mTransferData.getValueByName("Level");
		this.strManageCom = (String) mTransferData.getValueByName("ManageCom");
		this.strStatDem = (String) mTransferData.getValueByName("StatDem");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		TextTag texttag = new TextTag();
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("LLPRRRiskTypePayBL.vts", ""); // appoint to a
																// special
																// output .vts
																// file
		ListTable tlistTable = new ListTable();
		tlistTable.setName("RTP");

		String strSql1 = "";
		String strLevelSql = "";
		String riskname = "";
		String salename = "";
		String levelname = "";

		double sumcol2 = 0;
		double sumcol3 = 0;
		double sumcol6 = 0;
		double sumcol7 = 0;
		double sumcol10 = 0;
		double sumcol11 = 0;
		double sumcol14 = 0;
		double sumcol15 = 0;
		double sumcol18 = 0;
		double sumcol19 = 0;

		int concol2 = 0;
		int concol3 = 0;
		int concol6 = 0;
		int concol7 = 0;
		int concol10 = 0;
		int concol11 = 0;
		int concol14 = 0;
		int concol15 = 0;
		int concol18 = 0;
		int concol19 = 0;
		// 判断险种选项是否为分险种统计
		String riskflag = strRiskCode.trim().equals("1") ? "1" : "0";// 1-分险种统计
																		// 0-不分险种
		if (!strLevel.equals("05"))// 机构层面选项为非用户
		{
			strLevelSql = " select ComCode,Name from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') and trim(comgrade)='"
					+ "?level?"
					+ "' order by ComCode";
		} else {
			strLevelSql = " select usercode,UserName from llclaimuser where ComCode like concat('"
					+ "?mngcom?" + "','%') order by usercode";
		}
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strLevelSql);
		sqlbv.put("mngcom", strManageCom);
		sqlbv.put("level", strLevel);
		logger.debug("开始执行strLevelSql操作**********" + strLevelSql);
		ExeSQL execLevelSQL = new ExeSQL();
		SSRS ssrsLevel = execLevelSQL.execSQL(sqlbv);
		if (ssrsLevel.getMaxRow() != 0) {
			for (int index = 1; index <= ssrsLevel.getMaxRow(); index++)// for1
			{
				String magcode = ssrsLevel.GetText(index, 1);// 机构（或者用户）代码
				String magname = ssrsLevel.GetText(index, 2);// 机构（或者用户）名字
				// 给付、拒付金额（短期医疗险、人寿险、短期意外险、重疾险）
				if (!strLevel.equals("05"))// 机构层面选项为非用户
				{
					if (strStatDem.equals("1"))// 统计口径 1-审批通过日期
					{
						strSql1 = " select max(case when '"
								+ "?riskflag?"
								+ "'='1' then substr(a.riskcode,3,3) else '不分险种' end),"
								+ " a.GiveType,c.risksortvalue,(case when sum(case when a.givetype='1' then a.standpay"
								+ " when a.givetype='0' then a.realpay end ) is null then 0 else sum(case when a.givetype='1' then a.standpay"
								+ " when a.givetype='0' then a.realpay end ) end),"
								+ " count(a.riskcode) "
								+ " from llclaimpolicy a,llregister b,lmrisksort c ,llclaimuwmain d "
								+ " where b.MngCom like concat('"
								+ "?operator?"
								+ "','%') and b.RgtNo = a.ClmNo and b.RgtNo = d.clmno "
								+ " and d.examconclusion = '0' and d.examdate between '"
								+ "?startdate?"
								+ "' and '"
								+ "?enddate?"
								+ "' "
								+ " and a.riskcode=c.riskcode and c.risksorttype ='20' and (a.givetype='0' or a.givetype='1')";
					}
					if (strStatDem.equals("2"))// 统计口径 2-结案日期
					{
						strSql1 = " select max(case when '"
								+ "?riskflag?"
								+ "'='1' then substr(a.riskcode,3,3) else '不分险种' end),"
								+ " a.GiveType,c.risksortvalue,(case when sum(case when a.givetype='1' then a.standpay"
								+ " when a.givetype='0' then a.realpay end ) is null then 0 else sum(case when a.givetype='1' then a.standpay"
								+ " when a.givetype='0' then a.realpay end ) end),"
								+ " count(a.riskcode) "
								+ " from llclaimpolicy a,llregister b,lmrisksort c ,llclaimuwmain d "
								+ " where b.MngCom like concat('"
								+ "?operator?"
								+ "','%') and b.RgtNo = a.ClmNo and b.RgtNo = d.clmno "
								+ " and b.ClmState='60' and b.endcasedate between '"
								+ "?startdate?"
								+ "' and '"
								+ "?enddate?"
								+ "' "
								+ " and a.riskcode=c.riskcode and c.risksorttype ='20' and (a.givetype='0' or a.givetype='1')";
					}
				} else// 机构层面选项为用户
				{
					if (strStatDem.equals("1"))// 统计口径 1-审批通过日期
					{
						strSql1 = " select max(case when '"
								+ "?riskflag?"
								+ "'='1' then substr(a.riskcode,3,3) else '不分险种' end),"
								+ " a.GiveType,c.risksortvalue,(case when sum(case when a.givetype='1' then a.standpay"
								+ " when a.givetype='0' then a.realpay end ) is null then 0 else sum(case when a.givetype='1' then a.standpay"
								+ " when a.givetype='0' then a.realpay end ) end),"
								+ " count(a.riskcode) "
								+ " from llclaimpolicy a,llregister b,lmrisksort c,llclaimuwmain d "
								+ " where b.Operator = '"
								+ "?operator?"
								+ "' and b.RgtNo=a.ClmNo and b.RgtNo = d.clmno "
								+ " and d.examconclusion = '0' and d.examdate between '"
								+ "?startdate?"
								+ "' and '"
								+ "?enddate?"
								+ "' "
								+ " and a.riskcode=c.riskcode and c.risksorttype ='20' and (a.givetype='0' or a.givetype='1') ";
					}
					if (strStatDem.equals("2"))// 统计口径 2-结案日期
					{
						strSql1 = " select max(case when '"
								+ "?riskflag?"
								+ "'='1' then substr(a.riskcode,3,3) else '不分险种' end),"
								+ " a.GiveType,c.risksortvalue,(case when sum(case when a.givetype='1' then a.standpay"
								+ " when a.givetype='0' then a.realpay end ) is null then 0 else sum(case when a.givetype='1' then a.standpay"
								+ " when a.givetype='0' then a.realpay end ) end),"
								+ " count(a.riskcode) "
								+ " from llclaimpolicy a,llregister b,lmrisksort c,llclaimuwmain d "
								+ " where b.Operator = '"
								+ "?operator?"
								+ "' and b.RgtNo=a.ClmNo and b.RgtNo = d.clmno "
								+ " and b.ClmState='60' and b.endcasedate between '"
								+ "?startdate?"
								+ "' and '"
								+ "?enddate?"
								+ "' "
								+ " and a.riskcode=c.riskcode and c.risksorttype ='20' and (a.givetype='0' or a.givetype='1') ";
					}
				}
				if (!strSaleType.trim().equals("") && strSaleType != null)// 渠道选项为团、个（非全部渠道）
				{
					strSql1 = strSql1 + " and b.RgtClass='" + "?saletype?" + "'";
				}
				if (riskflag.equals("1"))// 分险种统计
				{

					// strSql1=strSql1 + " Group by
					// a.riskcode,a.GiveType,c.risksortvalue order by
					// a.riskcode,a.GiveType,c.risksortvalue";
					strSql1 = strSql1
							+ " Group by substr(a.riskcode,3,3),a.GiveType,c.risksortvalue order by substr(a.riskcode,3,3),a.GiveType,c.risksortvalue";
				} else {
					strSql1 = strSql1 + " Group by a.GiveType,c.risksortvalue ";// 不分险种统计
				}

				ExeSQL exeSQL1 = new ExeSQL();

				logger.debug("strSql1=*****************=" + strSql1);
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSql1);
				sqlbv1.put("riskflag", riskflag);
				sqlbv1.put("operator", ssrsLevel.GetText(index, 1));
				sqlbv1.put("startdate", strStartDate);
				sqlbv1.put("enddate", strEndDate);
				sqlbv1.put("saletype", strSaleType);
				SSRS ssrs1 = exeSQL1.execSQL(sqlbv1);

				// 不分险种统计算法
				if (riskflag.trim().equals("0")) {
					if (ssrs1.getMaxRow() != 0) {
						cols = new String[33];
						// 初始化金额
						for (int i_cols = 2; i_cols <= 31; i_cols++) {
							cols[i_cols] = "0";
						}
						for (int i_count1 = 1; i_count1 <= ssrs1.getMaxRow(); i_count1++) {
							int risktype = Integer.parseInt(ssrs1.GetText(
									i_count1, 3));
							// 给付类型
							if (ssrs1.GetText(i_count1, 2).equals("0"))// 给付
							{
								// 险种类型
								switch (risktype) {
								case 1:// 人寿险
									cols[8] = ssrs1.GetText(i_count1, 4);
									sumcol6 = sumcol6
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[9] = ssrs1.GetText(i_count1, 5);
									concol6 = concol6
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 2:// 重疾险
									cols[26] = ssrs1.GetText(i_count1, 4);
									sumcol18 = sumcol18
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[27] = ssrs1.GetText(i_count1, 5);
									concol18 = concol18
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 3:// 短期意外险
									cols[14] = ssrs1.GetText(i_count1, 4);
									sumcol10 = sumcol10
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[15] = ssrs1.GetText(i_count1, 5);
									concol10 = concol10
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 4:// 短期健康险
									cols[2] = ssrs1.GetText(i_count1, 4);
									sumcol2 = sumcol2
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[3] = ssrs1.GetText(i_count1, 5);
									concol2 = concol2
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 5:// 长期意外险
									cols[20] = ssrs1.GetText(i_count1, 4);
									sumcol14 = sumcol14
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[21] = ssrs1.GetText(i_count1, 5);
									concol14 = concol14
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								default:
									break;
								}// end 险种类型switch
							}
							if (ssrs1.GetText(i_count1, 2).equals("1"))// 拒付
							{
								// 险种类型
								switch (risktype) {
								case 1:// 人寿险
									cols[10] = ssrs1.GetText(i_count1, 4);
									sumcol7 = sumcol7
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[11] = ssrs1.GetText(i_count1, 5);
									concol7 = concol7
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 2:// 重疾险
									cols[28] = ssrs1.GetText(i_count1, 4);
									sumcol19 = sumcol19
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[29] = ssrs1.GetText(i_count1, 5);
									concol19 = concol19
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 3:// 短期意外险
									cols[16] = ssrs1.GetText(i_count1, 4);
									sumcol11 = sumcol11
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[17] = ssrs1.GetText(i_count1, 5);
									concol11 = concol11
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 4:// 短期健康险
									cols[4] = ssrs1.GetText(i_count1, 4);
									sumcol3 = sumcol3
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[5] = ssrs1.GetText(i_count1, 5);
									concol3 = concol3
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 5:// 长期意外险
									cols[22] = ssrs1.GetText(i_count1, 4);
									sumcol15 = sumcol15
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[23] = ssrs1.GetText(i_count1, 5);
									concol15 = concol15
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								default:
									break;
								}// end 险种类型switch
							}
						}// end for
						// 计算权重
						// 短期医疗险给付、拒付金额权重
						double gfqz1 = 0.00;
						double jfqz1 = 0.00;
						logger.debug("11" + cols[2]);
						if (Double.parseDouble(cols[2])
								+ Double.parseDouble(cols[4]) > 0) {
							gfqz1 = Double.parseDouble(cols[2])
									/ (Double.parseDouble(cols[2]) + Double
											.parseDouble(cols[4]));
							jfqz1 = Double.parseDouble(cols[4])
									/ (Double.parseDouble(cols[2]) + Double
											.parseDouble(cols[4]));
						}
						// 人寿险给付、拒付金额权重
						double gfqz2 = 0.00;
						double jfqz2 = 0.00;
						if (Double.parseDouble(cols[8])
								+ Double.parseDouble(cols[10]) > 0) {
							gfqz2 = Double.parseDouble(cols[8])
									/ (Double.parseDouble(cols[8]) + Double
											.parseDouble(cols[10]));
							jfqz2 = Double.parseDouble(cols[10])
									/ (Double.parseDouble(cols[8]) + Double
											.parseDouble(cols[10]));
						}
						// 短期意外险给付、拒付金额权重
						double gfqz3 = 0;
						double jfqz3 = 0;
						if (Double.parseDouble(cols[14])
								+ Double.parseDouble(cols[16]) > 0) {
							gfqz3 = Double.parseDouble(cols[14])
									/ (Double.parseDouble(cols[14]) + Double
											.parseDouble(cols[16]));
							jfqz3 = Double.parseDouble(cols[16])
									/ (Double.parseDouble(cols[14]) + Double
											.parseDouble(cols[16]));
						}
						// 长期意外险给付、拒付金额权重
						double gfqz4 = 0;
						double jfqz4 = 0;
						if (Double.parseDouble(cols[20])
								+ Double.parseDouble(cols[22]) > 0) {
							gfqz4 = Double.parseDouble(cols[20])
									/ (Double.parseDouble(cols[20]) + Double
											.parseDouble(cols[22]));
							jfqz4 = Double.parseDouble(cols[22])
									/ (Double.parseDouble(cols[20]) + Double
											.parseDouble(cols[22]));
						}
						// 重疾险给付、拒付金额权重
						double gfqz5 = 0;
						double jfqz5 = 0;
						if (Double.parseDouble(cols[26])
								+ Double.parseDouble(cols[28]) > 0) {
							gfqz5 = Double.parseDouble(cols[26])
									/ (Double.parseDouble(cols[26]) + Double
											.parseDouble(cols[28]));
							jfqz5 = Double.parseDouble(cols[28])
									/ (Double.parseDouble(cols[26]) + Double
											.parseDouble(cols[28]));
						}
						if (strLevel.equals("05"))// 统计层面为用户
						{
							cols[0] = ssrsLevel.GetText(index, 2);
							cols[32] = ssrsLevel.GetText(index, 1);
						} else {
							cols[0] = ssrsLevel.GetText(index, 1);
							cols[32] = ssrsLevel.GetText(index, 2);
						}
						cols[6] = new DecimalFormat("0.00").format(gfqz1);
						cols[7] = new DecimalFormat("0.00").format(jfqz1);
						cols[12] = new DecimalFormat("0.00").format(gfqz2);
						cols[13] = new DecimalFormat("0.00").format(jfqz2);
						cols[18] = new DecimalFormat("0.00").format(gfqz3);
						cols[19] = new DecimalFormat("0.00").format(jfqz3);
						cols[24] = new DecimalFormat("0.00").format(gfqz4);
						cols[25] = new DecimalFormat("0.00").format(jfqz4);
						cols[30] = new DecimalFormat("0.00").format(gfqz5);
						cols[31] = new DecimalFormat("0.00").format(jfqz5);
						tlistTable.add(cols);
					} else {
						// 没有查到任何险种给付、拒付信息
					}
				}// end不分险种统计算法

				// 分险种统计算法
				if (riskflag.trim().equals("1")) {
					if (ssrs1.getMaxRow() != 0) {
						String oriskcode = "";// 险种代码初值
						for (int i_count1 = 1; i_count1 <= ssrs1.getMaxRow(); i_count1++) {
							// 判断：如果本次循环的险种不等于前一条记录的险种
							if (!ssrs1.GetText(i_count1, 1).trim().equals(
									oriskcode)) {
								cols = new String[33];
								// 初始化金额
								for (int i_cols = 2; i_cols <= 31; i_cols++) {
									cols[i_cols] = "";
								}
							}
							// 机构名称只显示一次
							if (i_count1 == 1) {
								cols[0] = ssrsLevel.GetText(index, 1);
								cols[32] = ssrsLevel.GetText(index, 2);
							}
							int risktype = Integer.parseInt(ssrs1.GetText(
									i_count1, 3));
							// 给付类型
							if (ssrs1.GetText(i_count1, 2).equals("0"))// 给付
							{
								// 险种类型
								switch (risktype) {
								case 1:// 人寿险
									cols[8] = ssrs1.GetText(i_count1, 4);
									sumcol6 = sumcol6
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[9] = ssrs1.GetText(i_count1, 5);
									concol6 = concol6
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 2:// 重疾险
									cols[26] = ssrs1.GetText(i_count1, 4);
									sumcol18 = sumcol18
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									logger.debug("******************sumcol18=="
													+ sumcol18);
									cols[27] = ssrs1.GetText(i_count1, 5);

									concol18 = concol18
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									logger.debug("******************concol18=="
													+ concol18);
									break;
								case 3:// 短期意外险
									cols[14] = ssrs1.GetText(i_count1, 4);
									sumcol10 = sumcol10
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[15] = ssrs1.GetText(i_count1, 5);
									concol10 = concol10
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 4:// 短期健康险
									cols[2] = ssrs1.GetText(i_count1, 4);
									sumcol2 = sumcol2
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[3] = ssrs1.GetText(i_count1, 5);
									concol2 = concol2
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 5:// 长期意外险
									cols[20] = ssrs1.GetText(i_count1, 4);
									sumcol14 = sumcol14
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[21] = ssrs1.GetText(i_count1, 5);
									concol14 = concol14
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								default:
									break;
								}// end 险种类型switch
							}
							if (ssrs1.GetText(i_count1, 2).equals("1"))// 拒付
							{
								// 险种类型
								switch (risktype) {
								case 1:// 人寿险
									cols[10] = ssrs1.GetText(i_count1, 4);
									sumcol7 = sumcol7
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[11] = ssrs1.GetText(i_count1, 5);
									concol7 = concol7
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 2:// 重疾险
									cols[28] = ssrs1.GetText(i_count1, 4);
									sumcol19 = sumcol19
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[29] = ssrs1.GetText(i_count1, 5);
									concol19 = concol19
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 3:// 短期意外险
									cols[16] = ssrs1.GetText(i_count1, 4);
									sumcol11 = sumcol11
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[17] = ssrs1.GetText(i_count1, 5);
									concol11 = concol11
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 4:// 短期健康险
									cols[4] = ssrs1.GetText(i_count1, 4);
									sumcol3 = sumcol3
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[5] = ssrs1.GetText(i_count1, 5);
									concol3 = concol3
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								case 5:// 长期意外险
									cols[22] = ssrs1.GetText(i_count1, 4);
									sumcol15 = sumcol15
											+ Double.parseDouble(ssrs1.GetText(
													i_count1, 4));
									cols[23] = ssrs1.GetText(i_count1, 5);
									concol15 = concol15
											+ Integer.parseInt(ssrs1.GetText(
													i_count1, 5));
									break;
								default:
									break;
								}// end 险种类型switch
							}
							cols[0] = magcode;
							cols[32] = magname;
							cols[1] = ssrs1.GetText(i_count1, 1);// 险种代码
							// 判断：如果本次循环的险种不等于前一条记录的险种,将本次循环的数据添加到list项中保存
							if (!ssrs1.GetText(i_count1, 1).trim().equals(
									oriskcode)) {
								tlistTable.add(cols);
							}
							oriskcode = ssrs1.GetText(i_count1, 1).trim();
						}// end for
					}
				}// end 分险种算法
			}// end for1
		} else {
			CError tError = new CError();
			tError.moduleName = "LLPRRRiskTypePayBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		String[] col = new String[1];
		col[0] = "";

		xmlexport.addListTable(tlistTable, col);
		// 各项数据合计（给付、拒付显示）
		texttag.add("RTPAdd1", sumcol2);
		texttag.add("RTPAdd3", sumcol3);
		texttag.add("RTPAdd7", sumcol6);
		texttag.add("RTPAdd9", sumcol7);
		texttag.add("RTPAdd13", sumcol10);
		texttag.add("RTPAdd15", sumcol11);
		texttag.add("RTPAdd19", sumcol14);
		texttag.add("RTPAdd21", sumcol15);
		texttag.add("RTPAdd25", sumcol18);
		texttag.add("RTPAdd27", sumcol19);
		// 统计数量
		texttag.add("RTPAdd2", concol2);
		texttag.add("RTPAdd4", concol3);
		texttag.add("RTPAdd8", concol6);
		texttag.add("RTPAdd10", concol7);
		texttag.add("RTPAdd14", concol10);
		texttag.add("RTPAdd16", concol11);
		texttag.add("RTPAdd20", concol14);
		texttag.add("RTPAdd22", concol15);
		texttag.add("RTPAdd26", concol18);
		texttag.add("RTPAdd28", concol19);

		// 各项数据合计（权重计算、显示）
		// 短期医疗险给付、拒付金额权重
		double sumgfqz1 = 0.00;
		double sumjfqz1 = 0.00;
		// 人寿险给付、拒付金额权重
		double sumgfqz2 = 0.00;
		double sumjfqz2 = 0.00;
		// 短期意外险给付、拒付金额权重
		double sumgfqz3 = 0.00;
		double sumjfqz3 = 0.00;
		// 长期意外险给付、拒付金额权重
		double sumgfqz4 = 0.00;
		double sumjfqz4 = 0.00;
		// 重疾险给付、拒付金额权重
		double sumgfqz5 = 0.00;
		double sumjfqz5 = 0.00;
		if (riskflag.equals("0"))// 不分险种统计
		{
			if ((sumcol2 + sumcol3) > 0) {
				sumgfqz1 = sumcol2 / (sumcol2 + sumcol3);
				sumjfqz1 = sumcol3 / (sumcol2 + sumcol3);
			}
			if ((sumcol6 + sumcol7) > 0) {
				sumgfqz2 = sumcol6 / (sumcol6 + sumcol7);
				sumjfqz2 = sumcol7 / (sumcol6 + sumcol7);
			}
			if ((sumcol10 + sumcol11) > 0) {
				sumgfqz3 = sumcol10 / (sumcol10 + sumcol11);
				sumjfqz3 = sumcol11 / (sumcol10 + sumcol11);
			}
			if ((sumcol14 + sumcol15) > 0) {
				sumgfqz4 = sumcol14 / (sumcol14 + sumcol15);
				sumjfqz4 = sumcol15 / (sumcol14 + sumcol15);
			}
			if ((sumcol18 + sumcol19) > 0) {
				sumgfqz5 = sumcol18 / (sumcol18 + sumcol19);
				sumjfqz5 = sumcol19 / (sumcol18 + sumcol19);
			}

			texttag.add("RTPAdd5", BqNameFun.getRound(sumgfqz1));
			texttag.add("RTPAdd6", BqNameFun.getRound(sumjfqz1));
			texttag.add("RTPAdd11", BqNameFun.getRound(sumgfqz2));
			texttag.add("RTPAdd12", BqNameFun.getRound(sumjfqz2));
			texttag.add("RTPAdd17", BqNameFun.getRound(sumgfqz3));
			texttag.add("RTPAdd18", BqNameFun.getRound(sumjfqz3));
			texttag.add("RTPAdd23", BqNameFun.getRound(sumgfqz4));
			texttag.add("RTPAdd24", BqNameFun.getRound(sumjfqz4));
			texttag.add("RTPAdd29", BqNameFun.getRound(sumgfqz5));
			texttag.add("RTPAdd30", BqNameFun.getRound(sumjfqz5));
		}
		// 报表条件显示
		// 险种选项
		riskname = riskflag.equals("1") ? "分险种统计" : "不分险种统计";
		texttag.add("RTPRiskCode", riskname);
		// 渠道选项
		if (!strSaleType.trim().equals("") && strSaleType != null) {
			salename = strSaleType.trim().equals("1") ? "个人" : "团体";
		} else {
			salename = "全部渠道";
		}
		texttag.add("RTPChan", salename);
		// 机构层面
		switch (Integer.parseInt(strLevel)) {
		case 1:
			levelname = "总公司";
			break;
		case 2:
			levelname = "分公司";
			break;
		case 3:
			levelname = "中支公司";
			break;
		case 4:
			levelname = "支公司";
			break;
		case 5:
			levelname = "用户";
			break;
		default:
			levelname = "";
			break;
		}
		texttag.add("RTPManage", levelname);
		// 机构统计范围
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(strManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tManageCom = tLDComSchema.getName();
		texttag.add("RTPStatSer", tManageCom);
		// 统计时间段
		texttag.add("RTPStaTimes", strStartDate + "至" + strEndDate);
		String mStatDem = strStatDem.equals("1") ? "审批通过日期" : "结案日期";
		texttag.add("StatDem", mStatDem);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	// 得到结果集
	public VData getResult() {

		return mResult;
	}

	// 主函数
	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";

		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("StartDate", "2006-08-01");
		tTransferData.setNameAndValue("EndDate", "2006-08-01");
		tTransferData.setNameAndValue("Level", "02");
		tTransferData.setNameAndValue("ManageCom", "8621");
		tTransferData.setNameAndValue("RiskCode", "1");
		tTransferData.setNameAndValue("StatDem", "1");
		tTransferData.setNameAndValue("SaleType", "1");
		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRRiskTypePayBL tLLPRRRiskTypePayBL = new LLPRRRiskTypePayBL();
		if (tLLPRRRiskTypePayBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}
		int n = tLLPRRRiskTypePayBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRRiskTypePayBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
