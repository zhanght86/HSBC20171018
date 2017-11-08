package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
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
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 统计报表打印：医疗费用花费归类统计--LLPRRMedical.vts
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司）、机构统计范围选项（总公司、某分公司、某中支）、
 * 医院类别选项（单个类别、全部类别）、统计口径（出险日期、审批通过日期）、医疗费用类别（单个费用类别、全部费用类别） 表头：报表名称、统计条件、统计日期
 * 数据项：机构、医院类别、医疗费用类别、医疗费用花费金额、赔案数量、平均花费（医疗费用花费总金额/赔案数量）、
 * 医疗费用责任内金额、平均责任内金额（责任内总金额/赔案数量） 算法：按照选择的条件统计各项数据 排序：机构、医院类别、医疗费用类别 表尾：各项数据的合计
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author niuzj,2005-10-04
 * @version 1.0 modify by ZHaoRx 2005-10-15
 */

public class LLPRRMedicalBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRMedicalBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate(); // 系统日期
	private String CurrentTime = PubFun.getCurrentTime(); // 系统时间

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private String strManageCom = ""; // 统计机构
	private String strLevel = ""; // 统计层面
	private String strStartDate = ""; // 统计起期
	private String strEndDate = ""; // 统计止期
	private String strHosSort = ""; // 医院类别
	private String strMediFee = ""; // 医疗费用
	private String strMStAroun = ""; // 统计口径
	private String strMStArounName = "";
	private String mNCLType = "";// 申请类型

	public LLPRRMedicalBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------统计报表打印：医疗费用花费归类统计-----LLPRRMedicalBL测试-----开始----------");

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

		logger.debug("----------统计报表打印：医疗费用花费归类统计-----LLPRRMedicalBL测试-----结束----------");
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

		this.strManageCom = (String) mTransferData.getValueByName("ManageCom"); // 统计机构
		this.strLevel = (String) mTransferData.getValueByName("Level"); // 统计层面
		this.strStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
		this.strEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		this.strHosSort = (String) mTransferData.getValueByName("HosSort"); // 医院类别
		this.strMediFee = (String) mTransferData.getValueByName("MediFee"); // 医疗费用
		this.strMStAroun = (String) mTransferData.getValueByName("MStAroun"); // 统计口径
		this.strMStArounName = (String) mTransferData
				.getValueByName("MStArounName");
		this.mNCLType = (String) mTransferData.getValueByName("NCLType");

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
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("LLPRRMedical.vts", ""); // 所用模板名称
		ListTable tListTable = new ListTable(); // 新建一个ListTable的实例,给模板的每一列赋值
		tListTable.setName("RICD");

		// 定义列表标题，共8列
		String[] Title = new String[8];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";

		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/RICD/ROW/COL1内容，并为列表赋值--");

		String tHosSortName = "";
		String tHosSortCode = "";
		String tMediFeeName = "";
		String tMediFeeCode = "";
		String strHos = "";
		String strFee = "";
		String tHFJE = "0";
		String tCLM = "0";
		String tZRNJE = "0";
		double tACC1 = 0;
		double tACC2 = 0;
		double tRICDAdd4 = 0;
		int tRICDAdd5 = 0;
		double tRICDAdd6 = 0;
		double tRICDAdd7 = 0;
		double tRICDAdd8 = 0;
		// String strA = "";
		// String strB = "";
		// String strC = "";
		String strSql = "";
		/** ******************************查询统计机构层面,作为第一层循环************************************* */
		String strLevelSql = " select ComCode,Name from ldcom where ComCode like concat('"
				+ "?mngcom?"
				+ "','%') "
				+ " and trim(comgrade)='"
				+ "?level?"
				+ "' " + " order by ComCode ";
		logger.debug("查询统计层面的SQL语句为:" + strLevelSql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strLevelSql);
		sqlbv.put("mngcom", strManageCom);
		sqlbv.put("level", strLevel);
		ExeSQL execLevelSQL = new ExeSQL();
		SSRS ssrsLevel = execLevelSQL.execSQL(sqlbv); // 执行查询语句strLevelSql
		// 统计口径条件
		String tMStArounTa = strMStAroun.equals("01") ? " ,llcase d where d.rgtno=c.clmno "
				: ",llclaimuwmain e where e.clmno = c.clmno ";
		String tMStArounTi = strMStAroun.equals("01") ? " and d.accdate  between '"
				+ "?startdate?" + "' and '" + "?enddate?" + "' "
				: " and e.examconclusion = '0' and e.examdate  between '"
						+ "?startdate?" + "' and '" + "?enddate?" + "'";
		String tMStArounEx = strMStAroun.equals("01") ? " select 'Z' from llcase where aa.clmno = llcase.rgtno and llcase.accdate  "
				: " select 'Z' from llclaimuwmain where aa.clmno = llclaimuwmain.clmno and llclaimuwmain.examconclusion = '0' and llclaimuwmain.examdate  ";
		// String tClmState = " and c.clmstate in ('50','60')
		// ";//strMStAroun.equals("01") ? " and c.clmstate='60' " :
		// 申请类型判断
		String tApplTypeaa = mNCLType.equals("1") ? " and exists (select 'X' from llregister where aa.clmno = llregister.rgtno and llregister.rgtobj = '1') "
				: " and exists (select 'X' from llregister where aa.clmno = llregister.rgtno and llregister.rgtobj = '2') ";
		String tApplTypeb = mNCLType.equals("1") ? " and exists (select 'X' from llregister where b.clmno = llregister.rgtno and llregister.rgtobj = '1') "
				: " and exists (select 'X' from llregister where b.clmno = llregister.rgtno and llregister.rgtobj = '2') ";
		String tApplType = mNCLType.equals("1") ? " and exists (select 'X' from llregister where c.clmno = llregister.rgtno and llregister.rgtobj = '1') "
				: " and exists (select 'X' from llregister where c.clmno = llregister.rgtno and llregister.rgtobj = '2') ";
		String tExistsaa ="";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tExistsaa = tApplTypeaa
					+ " and exists (select 'Y' from llclaim where aa.clmno = llclaim.clmno and llclaim.clmstate in ('50', '60')) "
					+ " and exists (" + tMStArounEx + " between '" + "?startdate?"
					+ "' and '" + "?enddate?" + "' ) " + " and rownum = '1' ";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tExistsaa = tApplTypeaa
					+ " and exists (select 'Y' from llclaim where aa.clmno = llclaim.clmno and llclaim.clmstate in ('50', '60')) "
					+ " and exists (" + tMStArounEx + " between '" + "?startdate?"
					+ "' and '" + "?enddate?" + "' ) " + " limit 1 ";
		}
		String tExistsb = tApplTypeb
				+ " and exists (select 'Y' from llclaim where b.clmno = llclaim.clmno and llclaim.clmstate in ('50', '60')) "
				+ " and exists (" + tMStArounEx + " between '" + "?startdate?"
				+ "' and '" + "?enddate?" + "' ) ";

		// strSql = " select a.dutyfeecode,a.hosgrade,"
		// + " (select (select nvl(sum(b.fee),0) from llcasereceipt b "
		// + " where b.clmno = aa.clmno and b.feeitemcode = aa.dutyfeecode "
		// + tExistsb + ") "
		// + " from llclaimdutyfee aa where aa.HosGrade = a.hosgrade "
		// + " and aa.DutyFeeCode = a.dutyfeecode "
		// + tExistsaa + "), "
		// + " count(distinct(c.clmno)),"
		// + " nvl(sum(a.adjsum), 0) "
		// + " from llclaimdutyfee a, llclaim c, " + tMStArounTa
		// + " and a.clmno = c.clmno and c.clmstate in ('50', '60') "
		// + tApplType
		// + " and a.HosGrade = '21' and a.DutyFeeCode = 'CC001' "
		// + tMStArounTi
		// + " group by a.dutyfeecode, a.hosgrade ";

		if (ssrsLevel.getMaxRow() != 0) { // 查询有结果
			for (int i = 1; i <= ssrsLevel.getMaxRow(); i++) {
				String mngCode = ssrsLevel.GetText(i, 1); // 机构代码
				String tMngName = ssrsLevel.GetText(i, 2); // 机构名称

				/** ***********************************几个公用SQL语句************************************************ */
				// 医疗费用花费金额
				// String strAA = " select nvl(sum(b.fee),0) from llclaimdutyfee
				// a,llcasereceipt b,llclaim c,llcase d " + tMStArounTa
				// + " and a.clmno=b.clmno "
				// + " and a.clmno=c.clmno "
				// + " and c.clmno=d.rgtno "
				// + tClmState
				// + tApplType
				// + tMStArounTi
				// + " between '" + strStartDate + "' and '" + strEndDate + "' "
				// + " and b.mngcom like '" + mngCode + "%' ";
				// 赔案数量
				// String strBB = " select count(distinct(c.clmno)) from
				// llclaimdutyfee a,llcasereceipt b,llclaim c,llcase d " +
				// tMStArounTa
				// + " and a.clmno=b.clmno "
				// + " and a.clmno=c.clmno "
				// + " and c.clmno=d.rgtno "
				// + tClmState
				// + tApplType
				// + tMStArounTi
				// + " between '" + strStartDate + "' and '" + strEndDate + "' "
				// + " and c.mngcom like '" + mngCode + "%' ";
				// 医疗费用责任内金额
				// String strCC = " select nvl(sum(a.adjsum),0) from
				// llclaimdutyfee a,llcasereceipt b,llclaim c,llcase d " +
				// tMStArounTa
				// + " and a.clmno=b.clmno "
				// + " and a.clmno=c.clmno "
				// + " and c.clmno=d.rgtno "
				// + tClmState
				// + tApplType
				// + tMStArounTi
				// + " between '" + strStartDate + "' and '" + strEndDate + "' "
				// + " and a.mngcom like '" + mngCode + "%' ";
				/** *******************************根据医院等级,医疗费用类别拼SQL语句********************************************* */
				if (strHosSort.equals("") || strHosSort.equals("null")
						|| strHosSort == null) {// 医院类别为空
					strHos = " select a.codename,a.code from ldcode a "
							+ " where 1=1 " + " and a.codetype='llhosgrade' ";
					SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					sqlbv1.sql(strHos);
					ExeSQL strHosSQL = new ExeSQL();
					SSRS strSHos = new SSRS();
					strSHos = strHosSQL.execSQL(sqlbv1);

					if (strMediFee.equals("") || strMediFee.equals("null")
							|| strMediFee == null) {// 医疗费用为空（医院类别为空）
						strFee = " select a.codename,a.code from ldcode a "
								+ " where 1=1 "
								+ " and a.codetype='llmedfeetype' ";
						SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
						sqlbv2.sql(strFee);
						ExeSQL strFeeSQL = new ExeSQL();
						SSRS strSFee = new SSRS();
						strSFee = strFeeSQL.execSQL(sqlbv2);

						for (int k = 1; k <= strSHos.getMaxRow(); k++) {
							for (int h = 1; h <= strSFee.getMaxRow(); h++) {
								String[] Stra1 = new String[9];
								Stra1[0] = tMngName; // 机构
								tHosSortName = strSHos.GetText(k, 1); // 医院等级名称
								tHosSortCode = strSHos.GetText(k, 2);
								Stra1[1] = tHosSortName; // 医院类别

								tMediFeeName = strSFee.GetText(h, 1); // 医疗费用类别名称
								tMediFeeCode = strSFee.GetText(h, 2);
								Stra1[2] = tMediFeeName; // 医疗费用类别

								// strA = strAA + " and a.HosGrade='" +
								// tHosSortCode + "' "
								// + " and a.DutyFeeCode='" + tMediFeeCode + "'
								// "
								// + " and b.feeitemcode='" + tMediFeeCode + "'
								// ";
								// strB = strBB + " and a.HosGrade='" +
								// tHosSortCode + "' "
								// + " and a.DutyFeeCode='" + tMediFeeCode + "'
								// "
								// + " and b.feeitemcode='" + tMediFeeCode + "'
								// ";
								// strC = strCC + " and a.HosGrade='" +
								// tHosSortCode + "' "
								// + " and a.DutyFeeCode='" + tMediFeeCode + "'
								// "
								// + " and b.feeitemcode='" + tMediFeeCode + "'
								// ";
								// strSql = " select a.dutyfeecode,a.hosgrade, "
								// + " (" + strA + "), "
								// + " (" + strB + "), "
								// + " (" + strC + ") "
								// + " from llclaimdutyfee a,llcasereceipt
								// b,llclaim c,llcase d "
								// + " where 1=1 "
								// + " and a.clmno=b.clmno "
								// + " and a.clmno = c.clmno "
								// + " and c.clmno = d.rgtno "
								// + tClmState + tApplType
								// + " and a.HosGrade='" + tHosSortCode + "' "
								// + " and a.DutyFeeCode='" + tMediFeeCode + "'
								// "
								// + " and b.feeitemcode='" + tMediFeeCode + "'
								// "
								// + " group by a.dutyfeecode,a.hosgrade ";
								// logger.debug("合并后的查询SQL语句为:" + strSql);

								strSql = " select a.dutyfeecode,a.hosgrade,"
										+ " (select (select (case when sum(b.fee) is null then 0 else sum(b.fee) end) from llcasereceipt b "
										+ " where b.clmno = aa.clmno and b.feeitemcode = aa.dutyfeecode "
										+ tExistsb
										+ ") "
										+ " from llclaimdutyfee aa where aa.HosGrade = a.hosgrade "
										+ " and aa.DutyFeeCode = a.dutyfeecode "
										+ tExistsaa
										+ "), "
										+ " count(distinct(c.clmno)),"
										+ " (case when sum(a.adjsum) is null then 0 else sum(a.adjsum) end) "
										+ " from llclaimdutyfee a, llclaim c "
										+ tMStArounTa
										+ " and a.clmno = c.clmno and c.clmstate in ('50', '60') "
										+ tApplType
										+ " and a.HosGrade = '"
										+ "?hscode?"
										+ "' "
										+ " and a.DutyFeeCode = '"
										+ "?mfcode?"
										+ "' "
										+ tMStArounTi
										+ " group by a.dutyfeecode, a.hosgrade ";
								SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
								sqlbv3.sql(strSql);
								sqlbv3.put("startdate", strStartDate);
								sqlbv3.put("enddate", strEndDate);
								sqlbv3.put("hscode", tHosSortCode);
								sqlbv3.put("mfcode", tMediFeeCode);
										
								ExeSQL strExeSQL = new ExeSQL();
								SSRS strSSRS = new SSRS();
								strSSRS = strExeSQL.execSQL(sqlbv3);
								tHFJE = strSSRS.GetText(1, 3); // 医疗费用花费金额
								tCLM = strSSRS.GetText(1, 4); // 赔案数量
								tZRNJE = strSSRS.GetText(1, 5); // 医疗费用责任内金额
								if (strSSRS.getMaxRow() == 0) {
									tHFJE = "0";
									tCLM = "0";
									tZRNJE = "0";
								}
								if (Integer.parseInt(tCLM) != 0) {
									// 平均花费（医疗费用花费总金额/赔案数量）
									tACC1 = Double.parseDouble(tHFJE)
											/ Integer.parseInt(tCLM);
									// 平均责任内金额（责任内总金额/赔案数量）
									tACC2 = Double.parseDouble(tZRNJE)
											/ Integer.parseInt(tCLM);
								} else {
									tACC1 = 0;
									tACC2 = 0;
								}
								Stra1[3] = tHFJE; // 医疗费用花费金额
								Stra1[4] = tCLM; // 赔案数量
								Stra1[5] = String.valueOf(tACC1); // 平均花费
								Stra1[6] = tZRNJE; // 医疗费用责任内金额
								Stra1[7] = String.valueOf(tACC2); // 平均责任内金额
								Stra1[8] = mngCode;
								tListTable.add(Stra1);
								// 计算各项数据的和
								tRICDAdd4 = tRICDAdd4
										+ Double.parseDouble(tHFJE);
								// tRICDAdd5 = tRICDAdd5 +
								// Integer.parseInt(tCLM);
								// tRICDAdd6 = tRICDAdd6 + tACC1;
								tRICDAdd7 = tRICDAdd7
										+ Double.parseDouble(tZRNJE);
								// tRICDAdd8 = tRICDAdd8 + tACC2;
							}
						}
						String tSQLPAC = "";
						tSQLPAC = " select count(distinct(c.clmno)) "
								+ " from llclaimdutyfee a, llclaim c "
								+ tMStArounTa
								+ " and a.clmno = c.clmno and c.clmstate in ('50', '60') "
								+ tMStArounTi + tApplType;
						SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
						sqlbv4.sql(tSQLPAC);
						sqlbv4.put("startdate", strStartDate);
						sqlbv4.put("enddate", strEndDate);
						ExeSQL tExepac = new ExeSQL();
						String pac = tExepac.getOneValue(sqlbv4);
						tRICDAdd5 = Integer.parseInt(pac);
					} else {// 医疗费用不为空（医院类别为空）
						strFee = " select a.codename from ldcode a "
								+ " where 1=1 "
								+ " and a.codetype='llmedfeetype' "
								+ " and a.code='" + strMediFee + "' ";
						SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
						sqlbv5.sql(strFee);
						sqlbv5.put("code", strMediFee);
						ExeSQL strFeeSQL = new ExeSQL();
						SSRS strSFee = new SSRS();
						strSFee = strFeeSQL.execSQL(sqlbv5);
						tMediFeeName = strSFee.GetText(1, 1); // 医疗费用类别名称

						for (int k = 1; k <= strSHos.getMaxRow(); k++) {
							String[] Stra2 = new String[9];
							Stra2[0] = tMngName; // 机构
							tHosSortName = strSHos.GetText(k, 1); // 医院等级名称
							tHosSortCode = strSHos.GetText(k, 2);
							Stra2[1] = tHosSortName; // 医院类别
							Stra2[2] = tMediFeeName; // 医疗费用类别

							strSql = " select a.dutyfeecode,a.hosgrade,"
									+ " (select (select (case when sum(b.fee) is null then 0 else sum(b.fee) end) from llcasereceipt b "
									+ " where b.clmno = aa.clmno and b.feeitemcode = aa.dutyfeecode "
									+ tExistsb
									+ ") "
									+ " from llclaimdutyfee aa where aa.HosGrade = a.hosgrade "
									+ " and aa.DutyFeeCode = a.dutyfeecode "
									+ tExistsaa
									+ "), "
									+ " count(distinct(c.clmno)),"
									+ " (case when sum(a.adjsum) is null then 0 else sum(a.adjsum) end) "
									+ " from llclaimdutyfee a, llclaim c "
									+ tMStArounTa
									+ " and a.clmno = c.clmno and c.clmstate in ('50', '60') "
									+ tApplType + " and a.HosGrade = '"
									+ "?hscode?" + "' "
									+ " and a.DutyFeeCode = '" + "?medifee?"
									+ "' " + tMStArounTi
									+ " group by a.dutyfeecode, a.hosgrade ";

							// strA = strAA + " and a.HosGrade='" + tHosSortCode
							// + "' "
							// + " and a.DutyFeeCode='" + strMediFee + "' "
							// + " and b.feeitemcode='" + strMediFee + "' ";
							// strB = strBB + " and a.HosGrade='" + tHosSortCode
							// + "' "
							// + " and a.DutyFeeCode='" + strMediFee + "' "
							// + " and b.feeitemcode='" + strMediFee + "' ";
							// strC = strCC + " and a.HosGrade='" + tHosSortCode
							// + "' "
							// + " and a.DutyFeeCode='" + strMediFee + "' "
							// + " and b.feeitemcode='" + strMediFee + "' ";
							// strSql = " select a.dutyfeecode,a.hosgrade, "
							// + " (" + strA + "), "
							// + " (" + strB + "), "
							// + " (" + strC + ") "
							// + " from llclaimdutyfee a,llcasereceipt b,llclaim
							// c,llcase d "
							// + " where 1=1 "
							// + " and a.clmno=b.clmno "
							// + " and a.clmno = c.clmno "
							// + " and c.clmno = d.rgtno "
							// + tClmState + tApplType
							// + " and a.HosGrade='" + tHosSortCode + "' "
							// + " and a.DutyFeeCode='" + strMediFee + "' "
							// + " and b.feeitemcode='" + strMediFee + "' "
							// + " group by a.dutyfeecode,a.hosgrade ";
							// logger.debug("合并后的查询SQL语句为:" + strSql);
							SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
							sqlbv6.sql(strSql);
							sqlbv6.put("medifee", strMediFee);
							sqlbv6.put("hscode", tHosSortCode);
							sqlbv6.put("startdate", strStartDate);
							sqlbv6.put("enddate", strEndDate);
							ExeSQL strExeSQL = new ExeSQL();
							SSRS strSSRS = new SSRS();
							strSSRS = strExeSQL.execSQL(sqlbv6);

							tHFJE = strSSRS.GetText(1, 3); // 医疗费用花费金额
							tCLM = strSSRS.GetText(1, 4); // 赔案数量
							tZRNJE = strSSRS.GetText(1, 5); // 医疗费用责任内金额
							if (strSSRS.getMaxRow() == 0) {
								tHFJE = "0";
								tCLM = "0";
								tZRNJE = "0";
							}
							if (Integer.parseInt(tCLM) != 0) {
								// 平均花费（医疗费用花费总金额/赔案数量）
								tACC1 = Double.parseDouble(tHFJE)
										/ Integer.parseInt(tCLM);
								// 平均责任内金额（责任内总金额/赔案数量）
								tACC2 = Double.parseDouble(tZRNJE)
										/ Integer.parseInt(tCLM);
							} else {
								tACC1 = 0;
								tACC2 = 0;
							}
							Stra2[3] = tHFJE; // 医疗费用花费金额
							Stra2[4] = tCLM; // 赔案数量
							Stra2[5] = String.valueOf(tACC1); // 平均花费
							Stra2[6] = tZRNJE; // 医疗费用责任内金额
							Stra2[7] = String.valueOf(tACC2); // 平均责任内金额
							Stra2[8] = mngCode;
							tListTable.add(Stra2);

							// 计算各项数据的和
							tRICDAdd4 = tRICDAdd4 + Double.parseDouble(tHFJE);
							// tRICDAdd5 = tRICDAdd5 + Integer.parseInt(tCLM);
							// tRICDAdd6 = tRICDAdd6 + tACC1;
							tRICDAdd7 = tRICDAdd7 + Double.parseDouble(tZRNJE);
							// tRICDAdd8 = tRICDAdd8 + tACC2;
						}
						String tSQLPAC = "";
						tSQLPAC = " select count(distinct(c.clmno)) "
								+ " from llclaimdutyfee a, llclaim c "
								+ tMStArounTa
								+ " and a.clmno = c.clmno and c.clmstate in ('50', '60') "
								+ " and a.DutyFeeCode = '" + "?medifee?" + "' "
								+ tMStArounTi + tApplType;
						SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
						sqlbv7.sql(tSQLPAC);
						sqlbv7.put("medifee", strMediFee);
						sqlbv7.put("startdate", strStartDate);
						sqlbv7.put("enddate", strEndDate);
						ExeSQL tExepac = new ExeSQL();
						String pac = tExepac.getOneValue(sqlbv7);
						tRICDAdd5 = Integer.parseInt(pac);
					}
				} else { // 医院等级不为空
					strHos = " select a.codename from ldcode a "
							+ " where 1=1 " + " and a.codetype='llhosgrade' "
							+ " and a.code='" + "?hossort?" + "' ";
					SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
					sqlbv7.sql(strHos);
					sqlbv7.put("hossort", strHosSort);
					ExeSQL strHosSQL = new ExeSQL();
					SSRS strSHos = new SSRS();
					strSHos = strHosSQL.execSQL(sqlbv7);
					tHosSortName = strSHos.GetText(1, 1); // 医院等级名称

					if (strMediFee.equals("") || strMediFee.equals("null")
							|| strMediFee == null) {// 医疗费用为空（医院类别不为空）
						strFee = " select a.codename,a.code from ldcode a "
								+ " where 1=1 "
								+ " and a.codetype='llmedfeetype' ";
						SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
						sqlbv8.sql(strFee);
						ExeSQL strFeeSQL = new ExeSQL();
						SSRS strSFee = new SSRS();
						strSFee = strFeeSQL.execSQL(sqlbv8);
						for (int h = 1; h <= strSFee.getMaxRow(); h++) {
							tMediFeeName = strSFee.GetText(h, 1); // 医疗费用类别名称
							tMediFeeCode = strSFee.GetText(h, 2);

							String[] Stra3 = new String[9];
							Stra3[0] = tMngName; // 机构
							Stra3[1] = tHosSortName; // 医院类别
							Stra3[2] = tMediFeeName; // 医疗费用类别

							strSql = " select a.dutyfeecode,a.hosgrade,"
									+ " (select (select (case when sum(b.fee) is null then 0 else sum(b.fee) end) from llcasereceipt b "
									+ " where b.clmno = aa.clmno and b.feeitemcode = aa.dutyfeecode "
									+ tExistsb
									+ ") "
									+ " from llclaimdutyfee aa where aa.HosGrade = a.hosgrade "
									+ " and aa.DutyFeeCode = a.dutyfeecode "
									+ tExistsaa
									+ "), "
									+ " count(distinct(c.clmno)),"
									+ " (case when sum(a.adjsum) is null then 0 else sum(a.adjsum) end) "
									+ " from llclaimdutyfee a, llclaim c "
									+ tMStArounTa
									+ " and a.clmno = c.clmno and c.clmstate in ('50', '60') "
									+ tApplType + " and a.HosGrade = '"
									+ strHosSort + "' "
									+ " and a.DutyFeeCode = '" + "?mfcode?"
									+ "' " + tMStArounTi
									+ " group by a.dutyfeecode, a.hosgrade ";

							// strA = strAA + " and a.HosGrade='" + strHosSort +
							// "' "
							// + " and a.DutyFeeCode='" + tMediFeeCode + "' "
							// + " and b.feeitemcode='" + tMediFeeCode + "' ";
							// strB = strBB + " and a.HosGrade='" + strHosSort
							// +"' "
							// + " and a.DutyFeeCode='" + tMediFeeCode + "' "
							// + " and b.feeitemcode='" + tMediFeeCode + "' ";
							// strC = strCC + " and a.HosGrade='" + strHosSort +
							// "' "
							// + " and a.DutyFeeCode='" + tMediFeeCode + "' "
							// + " and b.feeitemcode='" + tMediFeeCode + "' ";
							// strSql = " select a.dutyfeecode,a.hosgrade, "
							// + " (" + strA + "), "
							// + " (" + strB + "), "
							// + " (" + strC + ") "
							// + " from llclaimdutyfee a,llcasereceipt b,llclaim
							// c,llcase d "
							// + " where 1=1 "
							// + " and a.clmno=b.clmno "
							// + " and a.clmno = c.clmno "
							// + " and c.clmno = d.rgtno "
							// + tClmState + tApplType
							// + " and a.HosGrade='" + strHosSort + "' "
							// + " and a.DutyFeeCode='" + tMediFeeCode + "' "
							// + " and b.feeitemcode='" + tMediFeeCode + "' "
							// + " group by a.dutyfeecode,a.hosgrade ";
							// logger.debug("合并后的查询SQL语句为:" + strSql);
							SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
							sqlbv9.sql(strSql);
							sqlbv9.put("mfcode", tMediFeeCode);
							sqlbv9.put("startdate", strStartDate);
							sqlbv9.put("enddate", strEndDate);
							ExeSQL strExeSQL = new ExeSQL();
							SSRS strSSRS = new SSRS();
							strSSRS = strExeSQL.execSQL(sqlbv9);

							tHFJE = strSSRS.GetText(1, 3); // 医疗费用花费金额
							tCLM = strSSRS.GetText(1, 4); // 赔案数量
							tZRNJE = strSSRS.GetText(1, 5); // 医疗费用责任内金额
							if (strSSRS.getMaxRow() == 0) {
								tHFJE = "0";
								tCLM = "0";
								tZRNJE = "0";
							}
							if (Integer.parseInt(tCLM) != 0) {
								// 平均花费（医疗费用花费总金额/赔案数量）
								tACC1 = Double.parseDouble(tHFJE)
										/ Integer.parseInt(tCLM);
								// 平均责任内金额（责任内总金额/赔案数量）
								tACC2 = Double.parseDouble(tZRNJE)
										/ Integer.parseInt(tCLM);
							} else {
								tACC1 = 0;
								tACC2 = 0;
							}
							Stra3[3] = tHFJE; // 医疗费用花费金额
							Stra3[4] = tCLM; // 赔案数量
							Stra3[5] = String.valueOf(tACC1); // 平均花费
							Stra3[6] = tZRNJE; // 医疗费用责任内金额
							Stra3[7] = String.valueOf(tACC2); // 平均责任内金额
							Stra3[8] = mngCode;
							tListTable.add(Stra3);

							// 计算各项数据的和
							tRICDAdd4 = tRICDAdd4 + Double.parseDouble(tHFJE);
							// tRICDAdd5 = tRICDAdd5 + Integer.parseInt(tCLM);
							// tRICDAdd6 = tRICDAdd6 + tACC1;
							tRICDAdd7 = tRICDAdd7 + Double.parseDouble(tZRNJE);
							// tRICDAdd8 = tRICDAdd8 + tACC2;
						}
						String tSQLPAC = "";
						tSQLPAC = " select count(distinct(c.clmno)) "
								+ " from llclaimdutyfee a, llclaim c "
								+ tMStArounTa
								+ " and a.clmno = c.clmno and c.clmstate in ('50', '60') "
								+ " and a.HosGrade = '" + strHosSort + "' "
								+ tMStArounTi + tApplType;
						SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
						sqlbv10.sql(tSQLPAC);
						sqlbv10.put("hossort", strHosSort);
						sqlbv10.put("startdate", strStartDate);
						sqlbv10.put("enddate", strEndDate);
						ExeSQL tExepac = new ExeSQL();
						String pac = tExepac.getOneValue(sqlbv10);
						tRICDAdd5 = Integer.parseInt(pac);
					} else {// 医疗费用不为空（医院类别不为空）
						strFee = " select a.codename from ldcode a "
								+ " where 1=1 "
								+ " and a.codetype='llmedfeetype' "
								+ " and a.code='" + "?medifee?" + "' ";
						SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
						sqlbv11.sql(strFee);
						sqlbv11.put("medifee", strMediFee);
						ExeSQL strFeeSQL = new ExeSQL();
						SSRS strSFee = new SSRS();
						strSFee = strFeeSQL.execSQL(sqlbv11);
						tMediFeeName = strSFee.GetText(1, 1); // 医疗费用类别名称

						String[] Stra4 = new String[9];
						Stra4[0] = tMngName; // 机构
						Stra4[1] = tHosSortName; // 医院类别
						Stra4[2] = tMediFeeName; // 医疗费用类别

						strSql = " select a.dutyfeecode,a.hosgrade,"
								+ " (select (select (case when sum(b.fee) is null then 0 else sum(b.fee) end) from llcasereceipt b "
								+ " where b.clmno = aa.clmno and b.feeitemcode = aa.dutyfeecode "
								+ tExistsb
								+ ") "
								+ " from llclaimdutyfee aa where aa.HosGrade = a.hosgrade "
								+ " and aa.DutyFeeCode = a.dutyfeecode "
								+ tExistsaa
								+ "), "
								+ " count(distinct(c.clmno)),"
								+ " (case when sum(a.adjsum) is null then 0 else sum(a.adjsum) end) "
								+ " from llclaimdutyfee a, llclaim c "
								+ tMStArounTa
								+ " and a.clmno = c.clmno and c.clmstate in ('50', '60') "
								+ tApplType + " and a.HosGrade = '"
								+ "?hossort?" + "' " + " and a.DutyFeeCode = '"
								+ "?medifee?" + "' " + tMStArounTi
								+ " group by a.dutyfeecode, a.hosgrade ";

						// strA = strAA + " and a.HosGrade='" + strHosSort + "'
						// "
						// + " and a.DutyFeeCode='" + strMediFee + "' "
						// + " and b.feeitemcode='" + strMediFee + "' ";
						// strB = strBB + " and a.HosGrade='" + strHosSort + "'
						// "
						// + " and a.DutyFeeCode='" + strMediFee + "' "
						// + " and b.feeitemcode='" + strMediFee + "' ";
						// strC = strCC + " and a.HosGrade='" + strHosSort + "'
						// "
						// + " and a.DutyFeeCode='" + strMediFee + "' "
						// + " and b.feeitemcode='" + strMediFee + "' ";
						// strSql = " select a.dutyfeecode,a.hosgrade, "
						// + " (" + strA + "), "
						// + " (" + strB + "), "
						// + " (" + strC + ") "
						// + " from llclaimdutyfee a,llcasereceipt b,llclaim
						// c,llcase d "
						// + " where 1=1 "
						// + " and a.clmno=b.clmno "
						// + " and a.clmno = c.clmno "
						// + " and c.clmno = d.rgtno "
						// + tClmState + tApplType
						// + " and a.HosGrade='" + strHosSort + "' "
						// + " and a.DutyFeeCode='" + strMediFee + "' "
						// + " and b.feeitemcode='" + strMediFee + "' "
						// + " group by a.dutyfeecode,a.hosgrade ";
						// logger.debug("合并后的查询SQL语句为:" + strSql);
						SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
						sqlbv12.sql(strSql);
						sqlbv12.put("hossort", strHosSort);
						sqlbv12.put("medifee", strMediFee);
						sqlbv12.put("startdate", strStartDate);
						sqlbv12.put("enddate", strEndDate);
						ExeSQL strExeSQL = new ExeSQL();
						SSRS strSSRS = new SSRS();
						strSSRS = strExeSQL.execSQL(sqlbv12);

						tHFJE = strSSRS.GetText(1, 3); // 医疗费用花费金额
						tCLM = strSSRS.GetText(1, 4); // 赔案数量
						tZRNJE = strSSRS.GetText(1, 5); // 医疗费用责任内金额
						if (strSSRS.getMaxRow() == 0) {
							tHFJE = "0";
							tCLM = "0";
							tZRNJE = "0";
						}
						if (Integer.parseInt(tCLM) != 0) {
							// 平均花费（医疗费用花费总金额/赔案数量）
							tACC1 = Double.parseDouble(tHFJE)
									/ Integer.parseInt(tCLM);
							// 平均责任内金额（责任内总金额/赔案数量）
							tACC2 = Double.parseDouble(tZRNJE)
									/ Integer.parseInt(tCLM);
						} else {
							tACC1 = 0;
							tACC2 = 0;
						}
						Stra4[3] = tHFJE; // 医疗费用花费金额
						Stra4[4] = tCLM; // 赔案数量
						Stra4[5] = String.valueOf(tACC1); // 平均花费
						Stra4[6] = tZRNJE; // 医疗费用责任内金额
						Stra4[7] = String.valueOf(tACC2); // 平均责任内金额
						Stra4[8] = mngCode;
						tListTable.add(Stra4);

						// 计算各项数据的和
						tRICDAdd4 = tRICDAdd4 + Double.parseDouble(tHFJE);
						// tRICDAdd5 = tRICDAdd5 + Integer.parseInt(tCLM);
						// tRICDAdd6 = tRICDAdd6 + tACC1;
						tRICDAdd7 = tRICDAdd7 + Double.parseDouble(tZRNJE);
						// tRICDAdd8 = tRICDAdd8 + tACC2;
						String tSQLPAC = "";
						tSQLPAC = " select count(distinct(c.clmno)) "
								+ " from llclaimdutyfee a, llclaim c "
								+ tMStArounTa
								+ " and a.clmno = c.clmno and c.clmstate in ('50', '60') "
								+ " and a.HosGrade = '" + "?hossort?" + "' "
								+ " and a.DutyFeeCode = '" + "?medifee?" + "' "
								+ tMStArounTi + tApplType;
						SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
						sqlbv13.sql(tSQLPAC);
						sqlbv13.put("hossort", strHosSort);
						sqlbv13.put("medifee", strMediFee);
						sqlbv13.put("startdate", strStartDate);
						sqlbv13.put("enddate", strEndDate);
						ExeSQL tExepac = new ExeSQL();
						String pac = tExepac.getOneValue(sqlbv13);
						tRICDAdd5 = Integer.parseInt(pac);
					}
				}
			}
		} else // 查询无结果
		{
			CError tError = new CError();
			tError.moduleName = "LLPRRClaimConclusionBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}
		logger.debug("----------以下查询是为单个变量赋值-----------");
		// 统计日期,默认为系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		tTextTag.add("RICDStaDate", SysDate); // 统计日期: $=/RICDStaDate$

		// 统计时间段:$=/RICDStaTimes$
		String tStatTimes = strStartDate + "至" + strEndDate;
		tTextTag.add("RICDStaTimes", tStatTimes);

		// 机构统计范围：$=/RICDStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(strManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("RICDStatSer", tMngCom);

		// 机构层面：$=/RICDManage$
		String tLevelName = "";
		switch (Integer.parseInt(strLevel)) {
		case 1:
			tLevelName = "总公司";
			break;
		case 2:
			tLevelName = "分公司";
			break;
		case 3:
			tLevelName = "中支公司";
			break;
		case 4:
			tLevelName = "支公司";
			break;
		default:
			tLevelName = "";
			break;
		}
		tTextTag.add("RICDManage", tLevelName);
		// 统计口径:$=/MStAround$
		tTextTag.add("MStAround", strMStArounName);
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		// 医院类别 ： $=/RICDHosType$
		String tHosType = strHosSort.equals("") ? "全部医院类别" : " "
				+ tLLPRTPubFunBL.getLDCode("llhosgrade", strHosSort) + " ";
		tTextTag.add("RICDHosType", tHosType);

		// 医疗费用类别：$=/RICDHosPayType$
		String tHosPayType = strMediFee.equals("") ? "全部医疗费用类别" : " "
				+ tLLPRTPubFunBL.getLDCode("llmedfeetype", strMediFee) + " ";
		tTextTag.add("RICDHosPayType", tHosPayType);

		// 申请类型: $=/CSApplType$
		String mNCLTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";
		tTextTag.add("RICDApplType", mNCLTypeName);

		// 各项数据的合计
		tTextTag.add("RICDAdd4", tRICDAdd4);
		tTextTag.add("RICDAdd5", tRICDAdd5);
		if (tRICDAdd5 != 0) {
			tRICDAdd6 = tRICDAdd4 / tRICDAdd5;
			tRICDAdd8 = tRICDAdd7 / tRICDAdd5;
		}

		tTextTag.add("RICDAdd6", tRICDAdd6);
		tTextTag.add("RICDAdd7", tRICDAdd7);
		tTextTag.add("RICDAdd8", tRICDAdd8);

		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		/* ##################### 后台调试部分，生成临时文件############################ */
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

		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		return true;
	}

	// 错误处理
	public CErrors getErrors() {
		return mErrors;
	}

	// 得到结果集
	public VData getResult() {
		return mResult;
	}

	// 主函数,test
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";
		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("StartDate", "2007-02-09"); // 统计起期
		tTransferData.setNameAndValue("EndDate", "2007-02-09"); // 统计止期
		tTransferData.setNameAndValue("Level", "01"); // 统计层面
		tTransferData.setNameAndValue("ManageCom", "86"); // 统计机构
		tTransferData.setNameAndValue("HosSort", "21"); // 医院类别
		tTransferData.setNameAndValue("MediFee", "CC001"); // 医疗费用
		tTransferData.setNameAndValue("MStAroun", "01"); // 统计口径 01 02
		tTransferData.setNameAndValue("NCLType", "1"); //

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRMedicalBL tLLPRRMedicalBL = new LLPRRMedicalBL();
		if (tLLPRRMedicalBL.submitData(tVData, "") == false) {
			logger.debug("-------统计报表打印：医疗费用花费归类统计---失败----");
		}
		int n = tLLPRRMedicalBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRMedicalBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
