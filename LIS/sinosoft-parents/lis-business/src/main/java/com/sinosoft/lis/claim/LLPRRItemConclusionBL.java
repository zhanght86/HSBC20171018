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
 * Description: 统计报表打印：赔案结论（保项层面）统计--LLPRRItemConclusion.vts
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司、用户）、机构统计范围选项（总公司、某分公司、某中支）、
 * 统计时间段、统计口径选项（审核日期、审批日期、结案日期、支付日期）、险种选项（分险种统计、不分险种统计） 表头：报表名称、统计条件、统计日期
 * 数据项：机构（或用户）、险种、给付保项数和金额、拒付保项数和金额、保项总数和总金额 算法：按照选择的条件统计各项数据 排序：机构（或用户）
 * 表尾：各项数据的合计
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author niuzj,2005-09-22
 * @version 1.0
 */

public class LLPRRItemConclusionBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRItemConclusionBL.class);

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
	private String mICRiskCho = ""; // 险种选项
	private String mICRiskChoName = "";
	private String mNCLType = ""; // 申请类型

	public LLPRRItemConclusionBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------统计报表打印：赔案结论（保项层面）统计-----LLPRRItemConclusionBL测试-----开始----------");

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

		logger.debug("----------统计报表打印：赔案结论（保项层面）统计-----LLPRRItemConclusionBL测试-----结束----------");
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
		this.mICRiskCho = (String) mTransferData.getValueByName("ICRiskCho"); // 险种选项
		this.mICRiskChoName = (String) mTransferData
				.getValueByName("ICRiskChoName");
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
		tXmlExport.createDocument("LLPRRItemConclusion.vts", ""); // 所用模板名称
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		ListTable tListTable = new ListTable(); // 新建一个ListTable的实例,给模板的每一列赋值
		tListTable.setName("IC");

		// 定义列表标题，共7列
		String[] Title = new String[7];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";

		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/IC/ROW/COL1内容，并为列表赋值--");

		// 用于统计各项数据的合计
		int tICAdd1 = 0; // 给付保项总数:$=/ICAdd1$
		double tICAdd2 = 0; // 给付保项总金额:$=/ICAdd2$
		int tICAdd3 = 0; // 拒付保项总数:$=/ICAdd3$
		double tICAdd4 = 0; // 拒付保项总金额:$=/ICAdd4$
		int tICAdd5 = 0; // 总保项数:$=/ICAdd5$
		double tICAdd6 = 0; // 总保项金额:$=/ICAdd6$

		String tTimes = " between '" + strStartDate + "' and '" + strEndDate
				+ "' ";// 时间条件
		String tICRiskChoHe = mICRiskCho.equals("1") ? " select substr(a.riskcode,3,3), "
				: " select '不分险种',";// 险种选项_SQL（Head）条件 1-分险种统计 2-不分险种统计
		String tICRiskChoLa = mICRiskCho.equals("1") ? " group by substr(a.riskcode, 3, 3),a.givetype order by substr(a.riskcode, 3, 3),a.givetype "
				: " group by a.givetype order by a.givetype ";// 险种选项_SQL（Last）条件
		String strLevelSql = ""; // 查询统计层面
		if (!strLevel.equals("05"))// 机构层面选项为非用户
		{
			strLevelSql = " select ComCode,Name from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') "
					+ " and comgrade='"
					+ "?level?"
					+ "' " + " order by ComCode ";
		} else // 用户层面
		{
			strLevelSql = " select usercode,UserName from llclaimuser where ComCode like concat('"
					+ "?mngcom?" + "','%') " + " order by usercode";
		}
		logger.debug("查询统计层面的SQL语句为:" + strLevelSql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strLevelSql);
		sqlbv.put("mngcom", strManageCom);
		sqlbv.put("level", strLevel);
		ExeSQL execLevelSQL = new ExeSQL();
		SSRS ssrsLevel = execLevelSQL.execSQL(sqlbv); // 执行查询语句strLevelSql
		if (ssrsLevel.getMaxRow() != 0) // 查询有结果
		{
			// 申请类型判断llregister.rgtobj：1-个险 2-团险
			String tApplType = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where a.clmno = llregister.rgtno and llregister.rgtobj = '1' ) "
					: " and exists (select 'X' from llregister where a.clmno = llregister.rgtno and llregister.rgtobj = '2' ) ";
			for (int i = 1; i <= ssrsLevel.getMaxRow(); i++) // 循环处理每条记录
			{
				String mngCode = ssrsLevel.GetText(i, 1);// 机构或用户代码
				String tMngName = ssrsLevel.GetText(i, 2);// 机构或用户名称
				String tMng_Operator = "";// 机构或用户条件
				String tPublicSQL = "";// 公用SQL
				// 保项层面,以llclaimdetail为主表
				// 用统计口径,再拼SQL语句
				// 根据前面查出的机构或用户统计,此时就不需要再用机构或用户代码分组,故注释掉分组的语句
				switch (Integer.parseInt(strStatKJ))// 统计口径条件判断tPublicSQL取值
				{
				case 1:// 统计口径为:审核日期
				// strSql1=" select count(*),sum(a.realpay) from llclaimdetail
				// a,llclaimuwmain b "
				// +" where 1=1 "
				// +" and a.clmno=b.clmno "
				// +" and a.givetype='0' " //给付
				// +" and b.AuditDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
				// strSql2=" select count(*),sum(a.realpay) from llclaimdetail
				// a,llclaimuwmain b "
				// +" where 1=1 "
				// +" and a.clmno=b.clmno "
				// +" and a.givetype='1' " //拒付
				// +" and b.AuditDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
					tMng_Operator = strLevel.equals("05") ? " and b.auditper = '"
							+ "?mngcode?" + "' "
							: " and a.MngCom like concat('" + "?mngcode?" + "','%') ";// 05-用户层面,其余为非用户层面
					tPublicSQL = tICRiskChoHe
							+ " a.givetype,count(*),(case when sum(a.realpay) is null then 0 else sum(a.realpay) end) from llclaimdetail a,llclaimuwmain b "
							+ " where 1=1 and a.clmno = b.clmno and a.givetype in ('0','1') "
							+ " and b.AuditDate " + tTimes + tMng_Operator
							+ tApplType + tICRiskChoLa;
					break;
				case 2: // 统计口径为:审批日期
				// strSql1=" select count(*),sum(a.realpay) from llclaimdetail
				// a,llclaimuwmain b "
				// +" where 1=1 "
				// +" and a.clmno=b.clmno "
				// +" and a.givetype='0' " //给付
				// +" and b.ExamDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
				// strSql2=" select count(*),sum(a.realpay) from llclaimdetail
				// a,llclaimuwmain b "
				// +" where 1=1 "
				// +" and a.clmno=b.clmno "
				// +" and a.givetype='1' " //拒付
				// +" and b.ExamDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
					tMng_Operator = strLevel.equals("05") ? " and b.examper = '"
							+ "?mngcode?" + "' "
							: " and a.MngCom like concat('" + "?mngcode?" + "','%') ";// 05-用户层面,其余为非用户层面
					tPublicSQL = tICRiskChoHe
							+ " a.givetype,count(*),(case when sum(a.realpay) is null then 0 else sum(a.realpay) end) from llclaimdetail a,llclaimuwmain b "
							+ " where 1=1 and a.clmno=b.clmno and a.givetype in ('0','1') "
							+ " and  b.ExamDate " + tTimes + tMng_Operator
							+ tApplType + tICRiskChoLa;
					break;
				case 3:// 统计口径为:结案日期
				// strSql1=" select count(*),sum(a.realpay) from llclaimdetail
				// a,llclaim b "
				// +" where 1=1 "
				// +" and a.clmno=b.clmno "
				// +" and a.givetype='0' " //给付
				// +" and b.EndCaseDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
				// strSql2=" select count(*),sum(a.realpay) from llclaimdetail
				// a,llclaim b "
				// +" where 1=1 "
				// +" and a.clmno=b.clmno "
				// +" and a.givetype='1' " //拒付
				// +" and b.EndCaseDate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
					tMng_Operator = strLevel.equals("05") ? " and b.operator = '"
							+ "?mngcode?" + "' "
							: " and a.MngCom like concat('" + "?mngcode?" + "','%') ";// 05-用户层面,其余为非用户层面
					tPublicSQL = tICRiskChoHe
							+ " a.givetype,count(*),(case when sum(a.realpay) is null then 0 else sum(a.realpay) end) from llclaimdetail a,llclaim b "
							+ " where 1=1 and a.clmno=b.clmno and a.givetype in ('0','1') "
							+ " and b.EndCaseDate " + tTimes + tMng_Operator
							+ tApplType + tICRiskChoLa;
					break;
				case 4: // 统计口径为:支付日期
				// strSql1=" select count(*),sum(a.realpay) from llclaimdetail
				// a,ljaget b "
				// +" where 1=1 "
				// +" and a.clmno=b.otherno "
				// +" and b.othernotype='5' "
				// +" and a.givetype='0' " //给付
				// +" and b.enteraccdate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
				// strSql2=" select count(*),sum(a.realpay) from llclaimdetail
				// a,ljaget b "
				// +" where 1=1 "
				// +" and a.clmno=b.otherno "
				// +" and b.othernotype='5' "
				// +" and a.givetype='1' " //拒付
				// +" and b.enteraccdate between '"+strStartDate+"' and
				// '"+strEndDate+"' ";
					tMng_Operator = strLevel.equals("05") ? " and a.operator = '"
							+ "?mngcode?" + "' "
							: " and a.MngCom like concat('" + "?mngcode?" + "','%') ";// 05-用户层面,其余为非用户层面
					tPublicSQL = tICRiskChoHe
							+ " a.givetype,count(*),(case when sum(a.realpay) is null then 0 else sum(a.realpay) end) from llclaimdetail a,ljaget b "
							+ " where 1=1 and a.clmno=b.otherno and b.othernotype='5' and a.givetype='1' "
							+ " and b.enteraccdate " + tTimes + tMng_Operator
							+ tApplType + tICRiskChoLa;
					break;
				default:
					break;
				}
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tPublicSQL);
				sqlbv1.put("mngcode", mngCode);
				sqlbv1.put("startdate", strStartDate);
				sqlbv1.put("enddate", strEndDate);
				ExeSQL strExeSQL = new ExeSQL();
				SSRS strSSRS = new SSRS();
				strSSRS = strExeSQL.execSQL(sqlbv1); // 执行最后得到的查询语句tPublicSQL
				String tRiskCode = "";
				String tGFBXS = "0";
				String tGFBXJE = "0.00";
				String tJFBXS = "0";
				String tJFBXJE = "0.00";
				int tZBXS = 0;
				double tZBXJE = 0;
				if (strSSRS.getMaxRow() > 0) {
					for (int r = 1; r <= strSSRS.getMaxRow(); r++) { // 分险种统计的处理：查询结果分一行，多行，最后一行分别处理
						if (mICRiskCho.equals("1")) {
							// 清除上一次数据，否则加和会有误
							tGFBXS = "0";
							tGFBXJE = "0.00";
							tJFBXS = "0";
							tJFBXJE = "0.00";
							// 针对一行的处理
							if (strSSRS.getMaxRow() == 1) {
								if (strSSRS.GetText(r, 2).equals("0")) { // 给付
									logger.debug("-----分险种-查询结果为单行数据！（给付）-----");
									tRiskCode = strSSRS.GetText(r, 1); // 险种代码
									tGFBXS = strSSRS.GetText(r, 3); // 给付保项数
									tGFBXJE = strSSRS.GetText(r, 4); // 给付保项金额
								} else {
									logger.debug("-----分险种-查询结果为单行数据！（拒付）-----");
									tRiskCode = strSSRS.GetText(r, 1); // 险种代码
									tJFBXS = strSSRS.GetText(r, 3); // 拒付保项数
									tJFBXJE = strSSRS.GetText(r, 4); // 拒付保项金额
								}
							}
							// 针对最后一行的处理，若放到多行处理的后面，在最后两行同险种时会丢失数据
							if ((r == strSSRS.getMaxRow())
									&& (strSSRS.getMaxRow() != 1)) {
								if (!strSSRS.GetText(r, 1).equals(
										strSSRS.GetText(r - 1, 1))) { // 若险种与前一行不相同，做如下处理
									if (strSSRS.GetText(r, 2).equals("0")) { // 给付
										logger.debug("-----分险种-查询结果为多行数据的最后一行数据，与上一行险种不同！（给付）-----");
										tRiskCode = strSSRS.GetText(r, 1); // 险种代码
										tGFBXS = strSSRS.GetText(r, 3); // 给付保项数
										tGFBXJE = strSSRS.GetText(r, 4); // 给付保项金额
									} else {// 拒付
										logger.debug("-----分险种-查询结果为多行数据的最后一行数据，与上一行险种不同！（拒付）-----");
										tRiskCode = strSSRS.GetText(r, 1); // 险种代码
										tJFBXS = strSSRS.GetText(r, 3); // 拒付保项数
										tJFBXJE = strSSRS.GetText(r, 4); // 拒付保项金额
									}
								} else { // 若险种与前一行相同
									logger.debug("-----分险种-查询结果为多行数据的最后一行数据，与上一行险种相同！（continue跳出）-----");
									continue; // 跳出去，否则产生一条都为0的数据
								}
							}
							// 针对多行的处理
							if ((strSSRS.getMaxRow() > 1)
									&& (strSSRS.getMaxRow() >= r + 1)) { // 相邻两行不同险种
								if (!strSSRS.GetText(r, 1).equals(
										strSSRS.GetText(r + 1, 1))) {
									if (strSSRS.GetText(r, 2).equals("0")) { // 给付
										logger.debug("-----分险种-查询结果为多行数据，第"
														+ r + "行险种"
														+ strSSRS.GetText(r, 1)
														+ "与下一行险种不同！（给付）-----");
										tRiskCode = strSSRS.GetText(r, 1); // 险种代码
										tGFBXS = strSSRS.GetText(r, 3); // 给付保项数
										tGFBXJE = strSSRS.GetText(r, 4); // 给付保项金额
									} else { // 拒付
										logger.debug("-----分险种-查询结果为多行数据，第"
														+ r + "行险种"
														+ strSSRS.GetText(r, 1)
														+ "与下一行险种不同！（拒付）-----");
										tRiskCode = strSSRS.GetText(r, 1); // 险种代码
										tJFBXS = strSSRS.GetText(r, 3); // 拒付保项数
										tJFBXJE = strSSRS.GetText(r, 4); // 拒付保项金额
									}
								}
								// 若相邻两行险种相同,一定是第一行givetype=0（给付）,第二行givetype=1（拒付）
								else {
									logger.debug("-----分险种-查询结果为多行数据，第"
											+ r + "行险种" + strSSRS.GetText(r, 1)
											+ "与下一行险种相同！（同时取给付与拒付数据）-----");
									tRiskCode = strSSRS.GetText(r, 1); // 险种代码
									tGFBXS = strSSRS.GetText(r, 3); // 给付保项数
									tGFBXJE = strSSRS.GetText(r, 4); // 给付保项金额
									tJFBXS = strSSRS.GetText(r + 1, 3); // 拒付保项数
									tJFBXJE = strSSRS.GetText(r + 1, 4); // 拒付保项金额
									r = r + 1; // r+1后与strSSRS.getMaxRow()相等没关系，有！=条件限制
								}
							}
						}
						// 不分险种统计的处理
						else { // 只有一行数据
							if (strSSRS.getMaxRow() == 1) {
								if (strSSRS.GetText(r, 2).equals("0")) { // 给付
									logger.debug("-----不分险种-查询结果为单行数据！（给付）-----");
									tRiskCode = strSSRS.GetText(r, 1); // 险种代码
									tGFBXS = strSSRS.GetText(r, 3); // 给付保项数
									tGFBXJE = strSSRS.GetText(r, 4); // 给付保项金额
								} else {// 拒付
									logger.debug("-----不分险种-查询结果为单行数据！（拒付）-----");
									tRiskCode = strSSRS.GetText(r, 1); // 险种代码
									tJFBXS = strSSRS.GetText(r, 3); // 拒付保项数
									tJFBXJE = strSSRS.GetText(r, 4); // 拒付保项金额
								}
							}
							// 两行数据
							else {
								logger.debug("-----不分险种-查询结果为两行数据！（同时取给付与拒付数据）-----");
								tRiskCode = strSSRS.GetText(r, 1); // 险种代码
								tGFBXS = strSSRS.GetText(r, 3); // 给付保项数
								tGFBXJE = strSSRS.GetText(r, 4); // 给付保项金额
								tJFBXS = strSSRS.GetText(r + 1, 3); // 拒付保项数
								tJFBXJE = strSSRS.GetText(r + 1, 4); // 拒付保项金额
								r = r + 1;
							}
						}
						// 计算保项总数和保项总金额
						tZBXS = Integer.parseInt(tGFBXS)
								+ Integer.parseInt(tJFBXS);
						tZBXJE = Double.parseDouble(tGFBXJE)
								+ Double.parseDouble(tJFBXJE);

						// 给模板中的每一列赋值
						String[] Stra = new String[9];
						Stra[0] = tRiskCode; // 险种代码
						Stra[1] = tGFBXS; // 给付保项数
						Stra[2] = tGFBXJE; // 给付保项金额
						Stra[3] = tJFBXS; // 拒付保项数
						Stra[4] = tJFBXJE; // 拒付保项金额
						Stra[5] = String.valueOf(tZBXS); // 总保项数
						Stra[6] = String.valueOf(tZBXJE); // 总保项金额
						Stra[7] = mngCode; // 机构或用户代码
						Stra[8] = tMngName; // 机构或用户名称
						tListTable.add(Stra);

						// 计算各项数据的合计
						tICAdd1 = tICAdd1 + Integer.parseInt(tGFBXS); // 给付保项总数
						tICAdd2 = tICAdd2 + Double.parseDouble(tGFBXJE); // 给付保项总金额
						tICAdd3 = tICAdd3 + Integer.parseInt(tJFBXS); // 拒付保项总数
						tICAdd4 = tICAdd4 + Double.parseDouble(tJFBXJE); // 拒付保项总金额
						tICAdd5 = tICAdd5 + tZBXS; // 总保项数
						tICAdd6 = tICAdd6 + tZBXJE; // 总保项金额
					}
				}
			}
		} else // 查询无结果
		{
			CError tError = new CError();
			tError.moduleName = "LLPRRItemConclusionBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询是为单个变量赋值-----------");
		// 统计日期,默认为系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		// 统计日期: $=/ICStaDate$
		tTextTag.add("ICStaDate", SysDate);

		// 统计时间段:$=/ICStaTimes$
		String tStatTimes = strStartDate + "至" + strEndDate;
		tTextTag.add("ICStaTimes", tStatTimes);

		// 统计机构名称:$=/ICStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(strManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("ICStatSer", tMngCom);

		// 统计层面:$=/ICManage$
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
		tTextTag.add("ICManage", tLevelName);

		// 统计口径:$=/ICStatAround$
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

		// 申请类型: $=/ICApplType$
		String mNCLTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";
		tTextTag.add("ICApplType", mNCLTypeName);

		tTextTag.add("ICStatAround", tStatKJName);
		// 险种选项： $=/ICRiskChoice$
		tTextTag.add("ICRiskChoice", mICRiskChoName);

		// 给付保项总数:$=/ICAdd1$
		tTextTag.add("ICAdd1", tICAdd1);
		// 给付保项总金额:$=/ICAdd2$,格式化取两位小数
		tTextTag.add("ICAdd2", new DecimalFormat("0.00").format(tICAdd2));
		// 拒付保项总数:$=/ICAdd3$
		tTextTag.add("ICAdd3", tICAdd3);
		// 拒付保项总金额:$=/ICAdd4$,格式化取两位小数
		tTextTag.add("ICAdd4", new DecimalFormat("0.00").format(tICAdd4));
		// 总保项数:$=/ICAdd5$
		tTextTag.add("ICAdd5", tICAdd5);
		// 总保项金额:$=/ICAdd6$,格式化取两位小数
		tTextTag.add("ICAdd6", new DecimalFormat("0.00").format(tICAdd6));

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
		tTransferData.setNameAndValue("EndDate", "2006-01-01"); // 统计止期
		tTransferData.setNameAndValue("Level", "02"); // 统计层面
		tTransferData.setNameAndValue("ManageCom", "86"); // 统计机构
		tTransferData.setNameAndValue("StatKJ", "1"); // 统计口径 1 2 3 4
		tTransferData.setNameAndValue("ICRiskCho", "1"); // 险种选项 1-分险种统计
															// 2-不分险种统计

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRItemConclusionBL tLLPRRItemConclusionBL = new LLPRRItemConclusionBL();
		if (tLLPRRItemConclusionBL.submitData(tVData, "") == false) {
			logger.debug("-------统计报表打印：赔案结论（保项层面）统计---失败----");
		}
		int n = tLLPRRItemConclusionBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRItemConclusionBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
