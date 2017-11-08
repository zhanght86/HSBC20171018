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
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔报表打印：保项状态汇总表--LLPRRItemState.vts
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司、用户）、 机构统计范围选项（总公司、某分公司、某中支）、统计时间段
 * 表头：报表名称、统计条件、统计日期 数据项：机构（或用户）、保项、立案、审核、审批、结案、给付数、
 * 关闭数（包括不予立案、客户撤案、公司撤案等）、审批拒付数、处理保项总数 算法：按照选择的条件下分别统计对应的报案日期、立案日期等在统计时间段的数据
 * 注：同一赔案中有多个同一保项计算为多个。 排序：机构（或用户） 表尾：各项数据的合计
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ZHaoRx，2005-9-26 10:08
 * @version 1.0
 */
public class LLPRRItemStateBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRItemStateBL.class);
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

	private String mStartDate = ""; // 开始日期
	private String mEndDate = ""; // 结束日期
	private String mLevel = ""; // 机构层面选项
	private String mManageCom = ""; // 机构统计范围选项
	private String mNCLType = ""; // 申请类型

	public LLPRRItemStateBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-----统计报表打印：保项状态汇总表-----LLPRRItemStateBL测试-----开始-----");
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
		logger.debug("-----统计报表打印：保项状态汇总表-----LLPRRItemStateBL测试-----结束------");

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
		this.mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
		this.mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		this.mLevel = (String) mTransferData.getValueByName("Level"); // 统计层面
		this.mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 统计机构
		this.mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 新建一个TextTag的实例
		TextTag tTextTag = new TextTag();
		// 新建一个XmlExport的实例
		XmlExport tXmlExport = new XmlExport();
		// 所用模板名称
		tXmlExport.createDocument("LLPRRItemState.vts", "");
		// 定义列表标题，共10列
		String[] Title = new String[10];
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
		// 新建一个ListTable的实例,给模板的每一列赋值
		ListTable tListTable = new ListTable();
		tListTable.setName("IS");

		int tISAdd1 = 0; // 立案总件数
		int tISAdd2 = 0; // 审核总件数
		int tISAdd3 = 0; // 审批总件数
		int tISAdd4 = 0; // 结案案总件数量
		int tISAdd5 = 0; // 给付总件数
		int tISAdd6 = 0; // 关闭总件数
		int tISAdd7 = 0; // 审批拒付总数
		int tISAdd8 = 0; // 处理保项总数

		// 机构层面选项 $=/ISManage$
		String tLevelName = "";
		int tLei = 0;
		switch (Integer.parseInt(mLevel)) {
		case 1:
			tLevelName = "总公司";
			tLei = 2;
			break;
		case 2:
			tLevelName = "分公司";
			tLei = 4;
			break;
		case 3:
			tLevelName = "中支公司";
			tLei = 6;
			break;
		case 4:
			tLevelName = "支公司";
			tLei = 8;
			break;
		case 5:
			tLevelName = "用户";
			break;
		default:
			tLevelName = "";
			break;
		}
		tTextTag.add("ISManage", tLevelName);
		String strLevelSql = "";

		// 判断机构层面选项是否为非用户
		if (!mLevel.equals("05")) {
			strLevelSql = " select ComCode,Name from ldcom where ComCode like concat('"
					+ "?mngcom?"
					+ "','%') and trim(comgrade)= '"
					+ "?level?"
					+ "' order by ComCode ";
		} else {
			strLevelSql = " select usercode,UserName from llclaimuser where ComCode like concat('"
					+ "?mngcom?" + "','%') order by usercode ";
		}
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strLevelSql);
		sqlbv.put("mngcom", mManageCom);
		sqlbv.put("level", mLevel);
		
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		// 判断是否查询到管理机构,没查到时提示"没有要统计的数据",例:ManageCom=8632,而Level=01
		if (tSSRS.getMaxRow() <= 0) {
			CError tError = new CError();
			tError.moduleName = "LLPRRItemStateBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			logger.debug("-------------第 " + i + " 次外层循环开始--------------");
			String magcode = tSSRS.GetText(i, 1);
			String magname = tSSRS.GetText(i, 2);
			// 申请类型判断llregister.rgtobj：1-个险 2-团险
			String tApplType = mNCLType.trim().equals("1") ? " and a.rgtobj = '1' "
					: " and a.rgtobj = '2'  ";
			// 以下拼写公共SQL语句
			String tGDKGDC = " and c.getdutykind = b.getdutykind and c.getdutycode = b.getdutycode and ";// 内层与外层关联条件
			String tGDKGD2 = " and c.getdutykind = b.getdutykind and c.getdutycode = b.getdutycode  ";
			String tSQLcd = " select count(1) from llclaimdetail c, llregister d ";// 内层子查询语句
			String tSQLc = " select count(1) from llclaimdetail c ";// 内层子查询语句
			String tSQLce = " select count(1) from llclaimdetail c, llclaim e ";// 内层子查询语句
			String tSQLbagjh = " from llclaimdetail b,"// 外层SQL连表
					+ " llregister a left join llclaimuwmain g on g.clmno = a.rgtno, "// 左连接,llclaimuwmain表中数据可能不全,如立案阶段的数据就没有
					+ " llclaim h  "// 左连接,ljagetclaim表中数据可能不全--left join
									// ljagetclaim j on j.otherno = h.clmno
					+ " where a.rgtno = b.clmno and a.rgtno = h.clmno ";
			String tTIMEs = " between '" + "?startdate?" + "' and '" + "?enddate?"
					+ "' ";// 统计时间段
			// Modify by zhaorx 2006-10-10
			// 若8621立案，86审核，审核时间在统计时段内，会有漏洞（见QC7232）。经与冯博沟通，只统计8621的。
			String tTIMEsor = " and ((a.rgtdate " + tTIMEs
					+ " or (g.auditdate " + tTIMEs + " and g.mngcom like concat('"
					+ "?magcode?"
					+ "','%') ) "// 求保项总数条件，不论案件处于那个状态，在统计时间内都统计
					+ " or (g.examdate " + tTIMEs + " and g.mngcom like concat('"
					+ "?magcode?" + "','%')) or h.endcasedate " + tTIMEs + " )) ";// 这几字段是立案，审核，审批等时间--or
																			// j.enteraccdate
																			// "+tTIMEs+"
			String tBAOXINGName = " (select getdutyname from lmdutygetclm s where s.getdutycode=b.getdutycode and s.getdutykind=b.getdutykind), ";// 查保项名
			String tGroupOrder = " group by substr(a.mngcom,1," + tLei
					+ "), b.getdutycode, b.getdutykind order by b.getdutycode ";// 分组并排序
			String tPublicSQL = "";// 主SQL语句
			if (!mLevel.equals("05"))// 机构层面选项为非用户
			{
				tPublicSQL = " select substr(a.mngcom,1,"
						+ tLei
						+ "),b.getdutykind,b.getdutycode,"// 按a.mngcom,b.getdutykind,b.getdutycode分组
						+ " ("
						+ tSQLcd
						+ " where d.rgtno = c.clmno  "
						+ tGDKGDC // 立案件数
						+ " d.mngcom like concat('"
						+ "?magcode?"
						+ "','%') and d.rgtdate "
						+ tTIMEs
						+ "),"
						+ " ("
						+ tSQLc
						+ " ,llclaimuwmain f where c.clmno = f.clmno  "
						+ tGDKGDC// 审核件数
						+ " f.mngcom like concat('"
						+ "?magcode?"
						+ "','%') and f.auditdate "
						+ tTIMEs
						+ "), "
						+ " ("
						+ tSQLc
						+ " ,llclaimuwmain f where c.clmno=f.clmno  "
						+ tGDKGDC// 审批件数
						+ " f.examcom like concat('"
						+ "?magcode?"
						+ "','%') and f.examdate "
						+ tTIMEs
						+ ") + "// 可能有一个案件多次审批的情况,用exists判断llclaimuwmain中的examdate是否为空
						+ " ("
						+ tSQLc
						+ " where c.clmno in (select distinct f.clmno from llclaimuwmdetail f "
						+ "  where f.examcom like concat('"
						+ "?magcode?"
						+ "','%')  and f.examdate "
						+ tTIMEs// 经多次审核的案件,审核确认后,将上一次审批地时间和审批人字段置空
						+ " and exists (select 'X' from llclaimuwmain where clmno=f.clmno and examdate is null))"
						+ tGDKGD2
						+ "),"
						+ " ("
						+ tSQLc
						+ ",llclaim f where f.clmno = c.clmno  "
						+ tGDKGDC// 结案的案件数量
						+ " f.mngcom like concat('"
						+ "?magcode?"
						+ "','%') and f.endcasedate "
						+ tTIMEs
						+ "),"
						+ " ("
						+ tSQLc
						+ " ,llclaimuwmain f where c.clmno = f.clmno  "
						+ tGDKGDC// 给付件数select count(1) from llregister f,
									// ljagetclaim c where f.rgtno = c.otherno "
									// +tGDKGDC+ " "
						+ " c.givetype = '0' and f.ExamConclusion='0' "
						+ " and f.examcom like concat('"
						+ "?magcode?"
						+ "','%') and f.examdate "
						+ tTIMEs
						+ "),"// f.mngcom like '" + magcode + "%' and
								// c.enteraccdate " +tTIMEs+ " and
								// c.feeoperationtype = 'A'),
						+ " ("
						+ tSQLce
						+ " where e.clmno = c.clmno  "
						+ tGDKGDC
						+ "  e.clmstate = '70' "// 关闭件数
						+ " and e.mngcom like concat('"
						+ "?magcode?"
						+ "','%') and e.EndCaseDate "
						+ tTIMEs
						+ "), "
						+ " ("
						+ tSQLce
						+ " ,llclaimuwmain f where e.clmno = c.clmno and c.clmno=f.clmno "
						+ tGDKGDC// 审批拒付数
						+ " f.examcom like concat('" + magcode + "','%') and f.examdate "
						+ tTIMEs + " and e.givetype = '1' )," + tBAOXINGName // 保项名字（汉字部分）
						+ " count(b.clmno) " // 处理保项总数
						+ tSQLbagjh// 外层连表及求保项数条件
						+ tApplType// 申诉类型判断
						+ " and b.mngcom like concat('" + magcode + "','%')" + tTIMEsor// 求保项总数条件，不论案件处于那个状态，在统计时间内都统计
						+ tGroupOrder;// 分组并排序
			} else// 机构层面选项为用户
			{
				tPublicSQL = " select substr(a.mngcom,1,"
						+ tLei
						+ "),b.getdutykind,b.getdutycode,"// 按a.mngcom,b.getdutykind,b.getdutycode分组
						+ " ("
						+ tSQLcd
						+ " where d.rgtno = c.clmno  "
						+ tGDKGDC// 立案件数
						+ " d.operator = '"
						+ "?magcode?"
						+ "' and d.rgtdate "
						+ tTIMEs
						+ "),"
						+ " ("
						+ tSQLc
						+ " ,llclaimuwmain f where c.clmno=f.clmno  "
						+ tGDKGDC// 审核件数
						+ " f.auditper = '"
						+ "?magcode?"
						+ "' and  f.auditdate "
						+ tTIMEs
						+ "), "
						+ " ("
						+ tSQLc
						+ " ,llclaimuwmain f where c.clmno=f.clmno  "
						+ tGDKGDC// 审批件数
						+ " f.examper = '"
						+ "?magcode?"
						+ "' and  f.examdate "
						+ tTIMEs
						+ ") + "// 可能有一个案件多次审批的情况,连llclaimuwmdetail求审批次数和
						+ " ("
						+ tSQLc
						+ " where c.clmno in (select distinct f.clmno from llclaimuwmdetail f where "
						+ " f.examper = '"
						+ "?magcode?"
						+ "' and  f.examdate "
						+ tTIMEs
						+ " and exists (select 'X' from llclaimuwmain where clmno=f.clmno and examdate is null))"
						+ tGDKGD2
						+ "), "
						+ " ("
						+ tSQLc
						+ " ,llclaim f where f.clmno = c.clmno  "
						+ tGDKGDC// 结案的案件数量
						+ " f.operator = '"
						+ "?magcode?"
						+ "' and  f.endcasedate "
						+ tTIMEs
						+ "),"
						+ " ("
						+ tSQLc
						+ " ,llclaimuwmain f where c.clmno=f.clmno  "
						+ tGDKGDC// 给付件数
						+ " c.givetype = '0' and f.ExamConclusion='0' "
						+ " and f.examper = '"
						+ "?magcode?"
						+ "' and  f.auditdate "
						+ tTIMEs
						+ "), "
						+ " ("
						+ tSQLce
						+ " where e.clmno = c.clmno  "
						+ tGDKGDC
						+ " e.clmstate = '70' "// 关闭件数
						+ " and e.operator = '"
						+ "?magcode?"
						+ "' and e.EndCaseDate "
						+ tTIMEs
						+ "),"
						+ " ("
						+ tSQLce
						+ " ,llclaimuwmain f where e.clmno = c.clmno and c.clmno=f.clmno  "
						+ tGDKGDC // 审批拒付数
						+ " f.examper = '"
						+ "?magcode?"
						+ "' and  f.examdate "
						+ tTIMEs
						+ " and e.givetype = '1' ),"
						+ tBAOXINGName // 保项名字（汉字部分）
						+ " count(b.clmno) " // 处理保项总数
						+ tSQLbagjh// 外层连表及求保项数条件
						+ tApplType// 申诉类型判断
						+ " and ((a.rgtdate " + tTIMEs + " and a.operator = '"
						+ "?magcode?" + "') " + " or (g.auditdate " + tTIMEs
						+ " and g.auditper = '" + "?magcode?" + "') "
						+ " or (g.examdate " + tTIMEs + " and g.examper = '"
						+ "?magcode?" + "') " + " or (h.endcasedate " + tTIMEs
						+ " and h.operator = '" + "?magcode?" + "')) "
						// + tTIMEsor//求保项总数条件，不论案件处于那个状态，在统计时间内都统计
						+ tGroupOrder;// 分组并排序;
			}
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tPublicSQL);
			sqlbv1.put("magcode",magcode);
			sqlbv1.put("startdate",mStartDate);
			sqlbv1.put("enddate",mEndDate);
			ExeSQL ExetPublicSQL = new ExeSQL();
			SSRS tPublic = new SSRS();
			tPublic = ExetPublicSQL.execSQL(sqlbv1);
			for (int j = 1; j <= tPublic.getMaxRow(); j++) {
				logger.debug("-------------第 " + j
						+ " 次内层循环开始--------------");
				// String tMNG = tPublic.GetText(j, 1);
				// String tGDK = tPublic.GetText(j, 2);
				String tGDC = tPublic.GetText(j, 3); // getdutycode保项代码部分
				String tLA = tPublic.GetText(j, 4); // 立案件数
				String tSH = tPublic.GetText(j, 5); // 审核件数
				String tSP = tPublic.GetText(j, 6); // 审批件数
				String tJA = tPublic.GetText(j, 7); // 结案的案件数量
				String tJF = tPublic.GetText(j, 8); // 给付件数
				String tGB = tPublic.GetText(j, 9); // 关闭件数
				String tSPJF = tPublic.GetText(j, 10); // 审批拒付数
				String tBXName = tPublic.GetText(j, 11); // 保项字段部分
				String tCLBXS = tPublic.GetText(j, 12); // 处理保项总数

				String tBX = tGDC + tBXName; // 拼出保项字符串
				// 定义列表标题
				String[] stra = new String[11];
				stra[0] = magname; // 机构(或用户)名称
				stra[1] = tBX; // 保项
				stra[2] = tLA; // 立案件数
				stra[3] = tSH; // 审核件数
				stra[4] = tSP; // 审批件数
				stra[5] = tJA; // 结案件数
				stra[6] = tJF; // 给付件数
				stra[7] = tGB; // 关闭件数
				stra[8] = tSPJF; // 审批拒付件数
				stra[9] = tCLBXS; // 处理保项总数
				stra[10] = magcode; // 机构（或用户）代码
				tListTable.add(stra);

				// 立案总件数
				tISAdd1 = tISAdd1 + Integer.parseInt(tLA);
				// 审核总件数
				tISAdd2 = tISAdd2 + Integer.parseInt(tSH);
				// 审批总件数
				tISAdd3 = tISAdd3 + Integer.parseInt(tSP);
				// 结案案总件数量
				tISAdd4 = tISAdd4 + Integer.parseInt(tJA);
				// 给付总件数
				tISAdd5 = tISAdd5 + Integer.parseInt(tJF);
				// 关闭总件数
				tISAdd6 = tISAdd6 + Integer.parseInt(tGB);
				// 审批拒付数
				tISAdd7 = tISAdd7 + Integer.parseInt(tSPJF);
				// 处理保项总数
				tISAdd8 = tISAdd8 + Integer.parseInt(tCLBXS);
				logger.debug("内层SQL为:" + tPublicSQL + "");
				logger.debug("-------------第 " + j
						+ " 次内层循环结束--------------");
				logger.debug("****************************************************");
			}
			logger.debug("-------------第 " + i + " 次外层循环结束--------------");
			logger.debug("****************************************************");
		}
		tTextTag.add("ISAdd1", tISAdd1);
		tTextTag.add("ISAdd2", tISAdd2);
		tTextTag.add("ISAdd3", tISAdd3);
		tTextTag.add("ISAdd4", tISAdd4);
		tTextTag.add("ISAdd5", tISAdd5);
		tTextTag.add("ISAdd6", tISAdd6);
		tTextTag.add("ISAdd7", tISAdd7);
		tTextTag.add("ISAdd8", tISAdd8);

		// 统计时间段 $=/ISStatTimes$
		String tTimes = mStartDate + "至" + mEndDate;
		tTextTag.add("ISStatTimes", tTimes);
		// 机构统计范围 $=/ISStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("ISStatSer", tMngCom);
		// 统计时间,默认为系统时间$=/ISDay$
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		tTextTag.add("ISDay", SysDate);
		// 申请类型: $=/ISApplType$
		String mNCLTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";
		tTextTag.add("ISApplType", mNCLTypeName);

		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		// /*##################### 后台调试部分，生成临时文件############################*/
		// String strTemplatePath = "E:/lis/ui/f1print/NCLtemplate/";
		// String strVFPathName = strTemplatePath + "ZHRX-IS-Test1008.vts";
		// //生成临时文件名
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(tXmlExport.getInputStream(),
		// strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);

		logger.debug("********************************************");
		logger.debug("---以下 将XmlExport打包，返回前台--");

		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("tXmlExport=" + tXmlExport);

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
		tTransferData.setNameAndValue("StartDate", "2005-9-25");
		tTransferData.setNameAndValue("EndDate", "2005-10-20");
		tTransferData.setNameAndValue("Level", "05");// 01 02 03 04 05
		tTransferData.setNameAndValue("ManageCom", "86");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRItemStateBL tLLPRRItemStateBL = new LLPRRItemStateBL();
		if (tLLPRRItemStateBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}
		int n = tLLPRRItemStateBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRItemStateBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}

	private void jbInit() throws Exception {
	}
}
