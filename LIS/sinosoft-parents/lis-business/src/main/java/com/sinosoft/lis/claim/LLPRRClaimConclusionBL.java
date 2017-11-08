package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

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
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 统计报表打印：赔案结论（赔案层面）统计--LLPRRClaimConclusion.vts
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司、用户）、机构统计范围选项（总公司、某分公司、某中支）、
 * 统计时间段、统计口径选项（审核日期、审批日期、结案日期、实付日期）、险种选项（分险种统计、不分险种统计） 表头：报表名称、统计条件、统计日期
 * 数据项：机构（或用户）、险种、拒付案件数和金额、给付案件数和金额、关闭案件数和金额、案件总数和总金额 算法：按照选择的条件 排序：机构（或用户）
 * 表尾：各项数据的合计 注：所有保项均拒付的案件为拒付案件，含有给付保项的案件为给付案件
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author niuzj,2005-09-25
 * @version 1.0
 */

public class LLPRRClaimConclusionBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRClaimConclusionBL.class);
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
	private String strStatKJ = ""; // 统计口径
	private String mCCRiskCho = ""; // 险种选项
	private String mCCRiskChoName = "";
	private String mNCLType = ""; // 申请类型

	private String[] cols;

	public LLPRRClaimConclusionBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------统计报表打印：赔案结论（赔案层面）统计-----LLPRRClaimConclusionBL测试-----开始----------");

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

		logger.debug("----------统计报表打印：赔案结论（赔案层面）统计-----LLPRRClaimConclusionBL测试-----结束----------");
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
		this.strStatKJ = (String) mTransferData.getValueByName("StatKJ"); // 统计口径
		this.mCCRiskCho = (String) mTransferData.getValueByName("CCRiskCho"); // 险种选项
		this.mCCRiskChoName = (String) mTransferData
				.getValueByName("CCRiskChoName");
		this.mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型

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
		tXmlExport.createDocument("LLPRRClaimConclusion.vts", ""); // 所用模板名称
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		ListTable tListTable = new ListTable(); // 新建一个ListTable的实例,给模板的每一列赋值
		tListTable.setName("CC");

		// 定义列表标题，共7列
		String[] Title = new String[9];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";
		Title[8] = "";

		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/CC/ROW/COL1内容，并为列表赋值--");

		// 用于统计各项数据的合计
		int tCCAdd1 = 0; // 给付案件总数
		double tCCAdd2 = 0; // 给付案件总金额
		int tCCAdd3 = 0; // 拒付案件总数
		double tCCAdd4 = 0; // 拒付案件总金额
		int tCCAdd5 = 0; // 关闭案件总数
		double tCCAdd6 = 0; // 关闭案件总金额
		int tCCAdd7 = 0; // 总案件数
		double tCCAdd8 = 0; // 总案件金额

		String tTimes = " between '" + "?startdate?" + "' and '" + "?enddate?"
				+ "' ";// 时间条件
		String tRiskChoHe = mCCRiskCho.equals("1") ? " select substr(z.riskcode,3,3),"
				: " select '不分险种',";// 险种选项_SQL（head）字段
		String tRiskChoTa = mCCRiskCho.equals("1") ? " where exists (select 'X' from llclaimpolicy x where x.riskcode = z.riskcode and a.clmno = x.clmno) "
				: " where 1=1 ";// 险种选项_表（table）字段
		String tRiskChoLa = mCCRiskCho.equals("1") ? " from lmrisk z group by z.riskcode order by z.riskcode "
				: " from dual ";// 险种选项_SQL（last）字段
		String strLevelSql = ""; // 查询统计层面
		if (!strLevel.equals("05")) { // 机构层面选项为非用户
			strLevelSql = " select ComCode,Name from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') "
					+ " and comgrade = '"
					+ "?level?"
					+ "' " + " order by ComCode ";
		} else { // 用户层面
			strLevelSql = " select usercode,UserName from llclaimuser where ComCode like concat('"
					+ "?mngcom?" + "','%') " + " order by usercode";
		}
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strLevelSql);
		sqlbv.put("mngcom", strManageCom);
		sqlbv.put("level", strLevel);
		logger.debug("查询统计层面的SQL语句为:" + strLevelSql);
		ExeSQL execLevelSQL = new ExeSQL();
		SSRS ssrsLevel = execLevelSQL.execSQL(sqlbv); // 执行查询语句strLevelSql
		if (ssrsLevel.getMaxRow() != 0) { // 查询有结果
			// 申请类型判断llregister.rgtobj：1-个险 2-团险
			String tApplType = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where a.clmno = llregister.rgtno and llregister.rgtobj = '1' ) "
					: " and exists (select 'X' from llregister where a.clmno = llregister.rgtno and llregister.rgtobj = '2' ) ";
			for (int i = 1; i <= ssrsLevel.getMaxRow(); i++) {
				String mngCode = ssrsLevel.GetText(i, 1);// 机构或用户代码
				String tMngName = ssrsLevel.GetText(i, 2);// 机构或用户名称
				// 用统计层面,拼SQL语句
				String tMng_Operator = strLevel.equals("05") ? " and a.Operator = '"
						+ "?mngcode?" + "' "
						: " and a.MngCom like concat('" + "?mngcode?" + "','%') ";
				String tSQLPubilc = "";// 最后拼出的SQL
				String tSQLBFXZ = "";// 不分险种的SQL

				// 赔案层面,以llclaim为主表
				// 用统计口径,再拼SQL语句
				switch (Integer.parseInt(strStatKJ)) {
				case 1:// 统计口径为:审核日期
				// strSql1=" select count(*),sum(a.standpay) from llclaim
				// a,llclaimuwmain b "
				// +" where 1=1 "
				// +" and a.clmno=b.clmno "
				// +" and a.givetype='0' " //给付
				// +" and b.AuditDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
				// strSql2=" select count(*),sum(a.DeclinePay) from llclaim
				// a,llclaimuwmain b "
				// +" where 1=1 "
				// +" and a.clmno=b.clmno "
				// +" and a.givetype='1' " //拒付
				// +" and b.AuditDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
				// strSql3=" select count(*),sum(a.standpay) from llclaim
				// a,llclaimuwmain b "
				// +" where 1=1 "
				// +" and a.clmno=b.clmno "
				// +" and a.clmstate='70' " //关闭案件
				// +" and b.AuditDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";

					tSQLPubilc = tRiskChoHe
							+ " (select count(*) from llclaim a,llclaimuwmain b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='0' and b.AuditDate "
							+ tTimes
							+ tMng_Operator
							+ "), "
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a,llclaimuwmain b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='0' and b.AuditDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select count(*) from llclaim a,llclaimuwmain b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='1' and b.AuditDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select CASE WHEN SUM(a.DeclinePay) IS NOT NULL THEN SUM(a.DeclinePay) ELSE 0 end from llclaim a,llclaimuwmain b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='1' and b.AuditDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select count(*) from llclaim a,llclaimuwmain b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.clmno and a.clmstate='70' and b.AuditDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a,llclaimuwmain b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.clmno and a.clmstate='70' and b.AuditDate "
							+ tTimes + tMng_Operator + ") " + tRiskChoLa;
					tSQLBFXZ = " select '不分险种',"// 分险种统计时，求各项数据和，下同
							+ " (select count(*) from llclaim a,llclaimuwmain b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='0' and b.AuditDate "
							+ tTimes
							+ tMng_Operator
							+ "), "
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a,llclaimuwmain b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='0' and b.AuditDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select count(*) from llclaim a,llclaimuwmain b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='1' and b.AuditDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select CASE WHEN SUM(a.DeclinePay) IS NOT NULL THEN SUM(a.DeclinePay) ELSE 0 end from llclaim a,llclaimuwmain b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='1' and b.AuditDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select count(*) from llclaim a,llclaimuwmain b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.clmno and a.clmstate='70' and b.AuditDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a,llclaimuwmain b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.clmno and a.clmstate='70' and b.AuditDate "
							+ tTimes + tMng_Operator + ") " + " from dual ";
					break;
				case 2:// 统计口径为:审批日期
				// strSql1=" select count(*),sum(a.standpay) from llclaim
				// a,llclaimuwmain b "
				// +" where 1=1 "
				// +" and a.clmno=b.clmno "
				// +" and a.givetype='0' " //给付
				// +" and b.ExamDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
				// strSql2=" select count(*),sum(a.DeclinePay) from llclaim
				// a,llclaimuwmain b "
				// +" where 1=1 "
				// +" and a.clmno=b.clmno "
				// +" and a.givetype='1' " //拒付
				// +" and b.ExamDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
				// strSql3=" select count(*),sum(a.standpay) from llclaim
				// a,llclaimuwmain b "
				// +" where 1=1 "
				// +" and a.clmno=b.clmno "
				// +" and a.clmstate='70' " //关闭案件
				// +" and b.ExamDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
					tSQLPubilc = tRiskChoHe
							+ " (select count(*) from llclaim a,llclaimuwmain b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='0' and b.ExamDate "
							+ tTimes
							+ tMng_Operator
							+ "), "
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a,llclaimuwmain b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='0' and b.ExamDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select count(*) from llclaim a,llclaimuwmain b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='1' and b.ExamDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select CASE WHEN SUM(a.DeclinePay) IS NOT NULL THEN SUM(a.DeclinePay) ELSE 0 end from llclaim a,llclaimuwmain b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='1' and b.ExamDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select count(*) from llclaim a,llclaimuwmain b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.clmno and a.clmstate='70' and b.ExamDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a,llclaimuwmain b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.clmno and a.clmstate='70' and b.ExamDate "
							+ tTimes + tMng_Operator + ") " + tRiskChoLa;
					tSQLBFXZ = " select '不分险种',"
							+ " (select count(*) from llclaim a,llclaimuwmain b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='0' and b.ExamDate "
							+ tTimes
							+ tMng_Operator
							+ "), "
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a,llclaimuwmain b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='0' and b.ExamDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select count(*) from llclaim a,llclaimuwmain b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='1' and b.ExamDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select CASE WHEN SUM(a.DeclinePay) IS NOT NULL THEN SUM(a.DeclinePay) ELSE 0 end from llclaim a,llclaimuwmain b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.clmno and a.givetype='1' and b.ExamDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select count(*) from llclaim a,llclaimuwmain b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.clmno and a.clmstate='70' and b.ExamDate "
							+ tTimes
							+ tMng_Operator
							+ "),"
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a,llclaimuwmain b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.clmno and a.clmstate='70' and b.ExamDate "
							+ tTimes + tMng_Operator + ") " + " from dual ";
					break;
				case 3:// 统计口径为:结案日期
				// strSql1=" select count(*),sum(a.standpay) from llclaim a "
				// +" where 1=1 "
				// +" and a.givetype='0' " //给付
				// +" and a.EndCaseDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
				// strSql2=" select count(*),sum(a.DeclinePay) from llclaim a "
				// +" where 1=1 "
				// +" and a.givetype='1' " //拒付
				// +" and a.EndCaseDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
				// strSql3=" select count(*),sum(a.standpay) from llclaim a "
				// +" where 1=1 "
				// +" and a.clmstate='70' " //关闭案件
				// +" and a.EndCaseDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
					tSQLPubilc = tRiskChoHe
							+ " (select count(*) from llclaim a "
							+ tRiskChoTa
							+ tApplType
							+ " and a.givetype='0' and a.EndCaseDate "
							+ tTimes
							+ tMng_Operator
							+ "), "
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a "
							+ tRiskChoTa
							+ tApplType
							+ " and a.givetype='0' and a.EndCaseDate "
							+ tTimes
							+ tMng_Operator
							+ "), "
							+ " (select count(*) from llclaim a "
							+ tRiskChoTa
							+ tApplType
							+ " and a.givetype='1' and a.EndCaseDate "
							+ tTimes
							+ tMng_Operator
							+ "), "
							+ " (select CASE WHEN SUM(a.DeclinePay) IS NOT NULL THEN SUM(a.DeclinePay) ELSE 0 end from llclaim a "
							+ tRiskChoTa + tApplType
							+ " and a.givetype='1' and a.EndCaseDate " + tTimes
							+ tMng_Operator + "), "
							+ " (select count(*) from llclaim a " + tRiskChoTa
							+ tApplType
							+ " and a.clmstate='70' and a.EndCaseDate "
							+ tTimes + tMng_Operator + "), "
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a "
							+ tRiskChoTa + tApplType
							+ " and a.clmstate='70' and a.EndCaseDate "
							+ tTimes + tMng_Operator + ") " + tRiskChoLa;
					tSQLBFXZ = " select '不分险种',"
							+ " (select count(*) from llclaim a "
							+ " where 1=1 "
							+ tApplType
							+ " and a.givetype='0' and a.EndCaseDate "
							+ tTimes
							+ tMng_Operator
							+ "), "
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a "
							+ " where 1=1 "
							+ tApplType
							+ " and a.givetype='0' and a.EndCaseDate "
							+ tTimes
							+ tMng_Operator
							+ "), "
							+ " (select count(*) from llclaim a "
							+ " where 1=1 "
							+ tApplType
							+ " and a.givetype='1' and a.EndCaseDate "
							+ tTimes
							+ tMng_Operator
							+ "), "
							+ " (select CASE WHEN SUM(a.DeclinePay) IS NOT NULL THEN SUM(a.DeclinePay) ELSE 0 end from llclaim a "
							+ " where 1=1 "
							+ tApplType
							+ " and a.givetype='1' and a.EndCaseDate "
							+ tTimes
							+ tMng_Operator
							+ "), "
							+ " (select count(*) from llclaim a "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmstate='70' and a.EndCaseDate "
							+ tTimes
							+ tMng_Operator
							+ "), "
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmstate='70' and a.EndCaseDate "
							+ tTimes + tMng_Operator + ") " + " from dual ";
					break;
				case 4:// 统计口径为:支付日期
				// strSql1=" select count(*),sum(a.standpay) from llclaim
				// a,ljaget b "
				// +" where 1=1 "
				// +" and a.clmno=b.otherno "
				// +" and b.othernotype='5' "
				// +" and a.givetype='0' " //给付
				// +" and b.enteraccdate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
				// strSql2=" select count(*),sum(a.DeclinePay) from llclaim
				// a,ljaget b "
				// +" where 1=1 "
				// +" and a.clmno=b.otherno "
				// +" and b.othernotype='5' "
				// +" and a.givetype='1' " //拒付
				// +" and b.enteraccdate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
				// strSql3=" select count(*),sum(a.standpay) from llclaim
				// a,ljaget b "
				// +" where 1=1 "
				// +" and a.clmno=b.otherno "
				// +" and b.othernotype='5' "
				// +" and a.clmstate='70' " //关闭案件
				// +" and b.enteraccdate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
					tSQLPubilc = tRiskChoHe
							+ " (select count(*) from llclaim a,ljaget b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.otherno and b.othernotype='5' and a.givetype='0' and b.enteraccdate "
							+ tTimes
							+ tMng_Operator
							+ " ),"
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a,ljaget b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.otherno and b.othernotype='5' and a.givetype='0' and b.enteraccdate "
							+ tTimes
							+ tMng_Operator
							+ " ),"
							+ " (select count(*) from llclaim a,ljaget b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.otherno and b.othernotype='5' and a.givetype='1' and b.enteraccdate "
							+ tTimes
							+ tMng_Operator
							+ " ),"
							+ " (select CASE WHEN SUM(a.DeclinePay) IS NOT NULL THEN SUM(a.DeclinePay) ELSE 0 end from llclaim a,ljaget b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.otherno and b.othernotype='5' and a.givetype='1' and b.enteraccdate "
							+ tTimes
							+ tMng_Operator
							+ " ),"
							+ " (select count(*) from llclaim a,ljaget b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.otherno and b.othernotype='5' and a.clmstate='70' and b.enteraccdate "
							+ tTimes
							+ tMng_Operator
							+ " ),"
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a,ljaget b "
							+ tRiskChoTa
							+ tApplType
							+ " and a.clmno=b.otherno and b.othernotype='5' and a.clmstate='70' and b.enteraccdate "
							+ tTimes + tMng_Operator + " )" + tRiskChoLa;
					tSQLBFXZ = " select '不分险种',"
							+ " (select count(*) from llclaim a,ljaget b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.otherno and b.othernotype='5' and a.givetype='0' and b.enteraccdate "
							+ tTimes
							+ tMng_Operator
							+ " ),"
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a,ljaget b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.otherno and b.othernotype='5' and a.givetype='0' and b.enteraccdate "
							+ tTimes
							+ tMng_Operator
							+ " ),"
							+ " (select count(*) from llclaim a,ljaget b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.otherno and b.othernotype='5' and a.givetype='1' and b.enteraccdate "
							+ tTimes
							+ tMng_Operator
							+ " ),"
							+ " (select CASE WHEN SUM(a.DeclinePay) IS NOT NULL THEN SUM(a.DeclinePay) ELSE 0 end from llclaim a,ljaget b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.otherno and b.othernotype='5' and a.givetype='1' and b.enteraccdate "
							+ tTimes
							+ tMng_Operator
							+ " ),"
							+ " (select count(*) from llclaim a,ljaget b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.otherno and b.othernotype='5' and a.clmstate='70' and b.enteraccdate "
							+ tTimes
							+ tMng_Operator
							+ " ),"
							+ " (select case when sum(a.standpay) is not null then sum(a.standpay) else 0 end from llclaim a,ljaget b "
							+ " where 1=1 "
							+ tApplType
							+ " and a.clmno=b.otherno and b.othernotype='5' and a.clmstate='70' and b.enteraccdate "
							+ tTimes + tMng_Operator + " )" + " from dual ";
					break;
				}
				logger.debug(tSQLPubilc);
				ExeSQL strExeSQL = new ExeSQL();
				SSRS strSSRS = new SSRS();
				SSRS tSSRSHE = new SSRS();
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tSQLPubilc);
				sqlbv1.put("startdate",strStartDate);
				sqlbv1.put("enddate",strEndDate);
				sqlbv1.put("mngcode",mngCode);
				strSSRS = strExeSQL.execSQL(sqlbv1); // 执行最后得到的查询语句
				if (mCCRiskCho.equals("1"))// 执行分险种统计时求和的SQL
				{
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(tSQLBFXZ);
					sqlbv2.put("startdate",strStartDate);
					sqlbv2.put("enddate",strEndDate);
					sqlbv2.put("mngcode",mngCode);
					tSSRSHE = strExeSQL.execSQL(sqlbv2);
				}
				String tRiskCode = "";
				String tGFBXS = "0";
				String tGFBXJE = "0";
				String tJFBXS = "0";
				String tJFBXJE = "0";
				String tGBAJS = "0";
				String tGBAJJE = "0";
				int tZBXS = 0;
				double tZBXJE = 0;
				if (strSSRS.getMaxRow() > 0) {
					for (int t = 1; t <= strSSRS.getMaxRow(); t++) {
						tRiskCode = strSSRS.GetText(t, 1);// 险种代码
						tGFBXS = strSSRS.GetText(t, 2);// 给付案件数
						tGFBXJE = strSSRS.GetText(t, 3);// 给付案件金额
						tJFBXS = strSSRS.GetText(t, 4);// 拒付案件数
						tJFBXJE = strSSRS.GetText(t, 5);// 拒付案件金额
						tGBAJS = strSSRS.GetText(t, 6);// 关闭案件数
						tGBAJJE = strSSRS.GetText(t, 7);// 关闭案件金额
						// 计算案件总数和案件总金额
						tZBXS = Integer.parseInt(tGFBXS)
								+ Integer.parseInt(tJFBXS)
								+ Integer.parseInt(tGBAJS);
						tZBXJE = Double.parseDouble(tGFBXJE)
								+ Double.parseDouble(tJFBXJE)
								+ Double.parseDouble(tGBAJJE);

						// 给模板中的每一列赋值
						String[] Stra = new String[11];
						Stra[0] = tRiskCode;
						Stra[1] = tGFBXS; // 给付案件数
						Stra[2] = tGFBXJE; // 给付案件金额
						Stra[3] = tJFBXS; // 拒付案件数
						Stra[4] = tJFBXJE; // 拒付案件金额
						Stra[5] = tGBAJS; // 关闭案件数
						Stra[6] = tGBAJJE; // 关闭案件金额
						Stra[7] = String.valueOf(tZBXS); // 总保项数
						Stra[8] = String.valueOf(tZBXJE); // 总保项金额
						Stra[9] = mngCode; // 机构或用户代码
						Stra[10] = tMngName;// 机构或用户名称
						tListTable.add(Stra);
					}
				}
				// 计算各项数据的合计
				if (mCCRiskCho.equals("1"))// 分险种统计
				{
					tCCAdd1 = tCCAdd1 + Integer.parseInt(tSSRSHE.GetText(1, 2)); // 给付案件总数
					tCCAdd2 = tCCAdd2
							+ Double.parseDouble(tSSRSHE.GetText(1, 3)); // 给付案件总金额
					tCCAdd3 = tCCAdd3 + Integer.parseInt(tSSRSHE.GetText(1, 4)); // 拒付案件总数
					tCCAdd4 = tCCAdd4
							+ Double.parseDouble(tSSRSHE.GetText(1, 5)); // 拒付案件总金额
					tCCAdd5 = tCCAdd5 + Integer.parseInt(tSSRSHE.GetText(1, 6)); // 关闭案件总数
					tCCAdd6 = tCCAdd6
							+ Double.parseDouble(tSSRSHE.GetText(1, 7)); // 关闭案件总金额
					tCCAdd7 = tCCAdd7 + Integer.parseInt(tSSRSHE.GetText(1, 2))
							+ Integer.parseInt(tSSRSHE.GetText(1, 4))
							+ Integer.parseInt(tSSRSHE.GetText(1, 6)); // 总案件数
					tCCAdd8 = tCCAdd8
							+ Double.parseDouble(tSSRSHE.GetText(1, 3))
							+ Double.parseDouble(tSSRSHE.GetText(1, 5))
							+ Double.parseDouble(tSSRSHE.GetText(1, 7)); // 总案件金额
				} else// 不分险种统计
				{
					tCCAdd1 = tCCAdd1 + Integer.parseInt(tGFBXS); // 给付案件总数
					tCCAdd2 = tCCAdd2 + Double.parseDouble(tGFBXJE); // 给付案件总金额
					tCCAdd3 = tCCAdd3 + Integer.parseInt(tJFBXS); // 拒付案件总数
					tCCAdd4 = tCCAdd4 + Double.parseDouble(tJFBXJE); // 拒付案件总金额
					tCCAdd5 = tCCAdd5 + Integer.parseInt(tGBAJS); // 关闭案件总数
					tCCAdd6 = tCCAdd6 + Double.parseDouble(tGBAJJE); // 关闭案件总金额
					tCCAdd7 = tCCAdd7 + tZBXS; // 总案件数
					tCCAdd8 = tCCAdd8 + tZBXJE; // 总案件金额
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
		tTextTag.add("CCStaDate", SysDate); // 统计日期: $=/CCStaDate$

		// 统计时间段:$=/CCStaTimes$
		String tStatTimes = strStartDate + "至" + strEndDate;
		tTextTag.add("CCStaTimes", tStatTimes);

		// 统计机构名称:$=/CCStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(strManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("CCStaSer", tMngCom);

		// 统计层面:$=/CCManage$
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
		case 5:
			tLevelName = "用户";
			break;
		default:
			tLevelName = "";
			break;
		}
		tTextTag.add("CCManage", tLevelName);

		// 统计口径:$=/CCStatAround$
		String tStatKJName = "";
		switch (Integer.parseInt(strStatKJ)) {
		case 1:
			tStatKJName = "审核日期";
			break;
		case 2:
			tStatKJName = "审批日期";
			break;
		case 3:
			tStatKJName = "结案日期";
			break;
		case 4:
			tStatKJName = "实付日期";
			break;
		default:
			tStatKJName = "";
			break;
		}

		// 申请类型: $=/CCApplType$
		String mNCLTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";
		tTextTag.add("CCApplType", mNCLTypeName);
		tTextTag.add("CCStatAround", tStatKJName);
		// 险种选项：$=/CCRiskCode$
		tTextTag.add("CCRiskCode", mCCRiskChoName);

		// 给付案件总数:$=/CCAdd1$
		tTextTag.add("CCAdd1", tCCAdd1);
		// 给付案件总金额:$=/CCAdd2$,格式化取两位小数
		tTextTag.add("CCAdd2", new DecimalFormat("0.00").format(tCCAdd2));
		// 拒付案件总数:$=/CCAdd3$
		tTextTag.add("CCAdd3", tCCAdd3);
		// 拒付案件总金额:$=/CCAdd4$,格式化取两位小数
		tTextTag.add("CCAdd4", new DecimalFormat("0.00").format(tCCAdd4));
		// 关闭案件总数:$=/CCAdd5$
		tTextTag.add("CCAdd5", tCCAdd5);
		// 关闭案件总金额:$=/CCAdd6$,格式化取两位小数
		tTextTag.add("CCAdd6", new DecimalFormat("0.00").format(tCCAdd6));
		// 总案件数:$=/CCAdd5$
		tTextTag.add("CCAdd7", tCCAdd7);
		// 总案件金额:$=/CCAdd6$,格式化取两位小数
		tTextTag.add("CCAdd8", new DecimalFormat("0.00").format(tCCAdd8));

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

		tTransferData.setNameAndValue("StartDate", "2005-11-01"); // 统计起期
		tTransferData.setNameAndValue("EndDate", "2005-11-23"); // 统计止期
		tTransferData.setNameAndValue("Level", "01"); // 统计层面
		tTransferData.setNameAndValue("ManageCom", "86"); // 统计机构
		tTransferData.setNameAndValue("StatKJ", "1"); // 统计口径
		tTransferData.setNameAndValue("CCRiskCho", "1"); // 险种选项

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRClaimConclusionBL tLLPRRClaimConclusionBL = new LLPRRClaimConclusionBL();
		if (tLLPRRClaimConclusionBL.submitData(tVData, "") == false) {
			logger.debug("-------统计报表打印：赔案结论（赔案层面）统计---失败----");
		}
		int n = tLLPRRClaimConclusionBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRClaimConclusionBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
