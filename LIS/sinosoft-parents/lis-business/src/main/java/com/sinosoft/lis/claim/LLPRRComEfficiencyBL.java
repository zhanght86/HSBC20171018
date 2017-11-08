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
 * Description: 统计报表打印：机构理赔时效统计--LLPRRComEfficiency.vts
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司）、机构统计范围选项（总公司、某分公司、某中支）、
 * 统计时间段、案件类型选项（普通、投诉、诉讼、疑难案件、简易案件、全部案件类型）、 理赔结论选项（拒付、给付、客户撤案、公司撤案、全部结论）
 * 表头：报表名称、统计条件、统计日期 数据项：机构、件均理赔时效（审批通过时间－立案起始时间，计算到分钟） 算法：按照选择的条件统计各项数据 排序：机构
 * 表尾：无
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author niuzj,2005-09-26
 * @version 1.0 Modify by niuzj，2006-04-13，兼容团险
 */

public class LLPRRComEfficiencyBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRComEfficiencyBL.class);
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

	private String mManageCom = ""; // 统计机构
	private String mLevel = ""; // 统计层面
	private String mStartDate = ""; // 统计起期
	private String mEndDate = ""; // 统计止期
	private String mCaseType = ""; // 案件类型
	private String mPayCon = ""; // 理赔结论
	private String mNCLType = ""; // 申请类型

	public LLPRRComEfficiencyBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------统计报表打印：机构理赔时效统计-----LLPRRComEfficiencyBL测试-----开始----------");

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

		logger.debug("----------统计报表打印：机构理赔时效统计-----LLPRRComEfficiencyBL测试-----结束----------");
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
		this.mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 统计机构
		this.mLevel = (String) mTransferData.getValueByName("Level"); // 统计层面
		this.mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
		this.mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		this.mCaseType = (String) mTransferData.getValueByName("CaseType"); // 案件类型
		this.mPayCon = (String) mTransferData.getValueByName("PayCon"); // 理赔结论
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
		tXmlExport.createDocument("LLPRRComEfficiency.vts", ""); // 所用模板名称
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		ListTable tListTable = new ListTable(); // 新建一个ListTable的实例,给模板的每一列赋值
		tListTable.setName("CE");

		// 定义列表标题，共7列
		String[] Title = new String[2];
		Title[0] = "";
		Title[1] = "";

		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/CE/ROW/COL内容，并为列表赋值--");

		// 先查询出管理机构
		String strMngSql = " select ComCode,Name from ldcom where ComCode like concat('"
				+ "?mngcom?"
				+ "','%') "
				+ " and comgrade='"
				+ "?level?"
				+ "' "
				+ " order by ComCode ";
		logger.debug("查询管理机构的SQL语句为:" + strMngSql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strMngSql);
		sqlbv.put("mngcom",mManageCom);
		sqlbv.put("level",mLevel);
		ExeSQL exeMngSQL = new ExeSQL();
		SSRS ssrsMng = exeMngSQL.execSQL(sqlbv); // 执行查询语句strMngSql

		String tPayConTa = mPayCon.equals("") ? " where 1=1 "
				: " ,llclaim c where c.clmno = a.clmno "; // 理赔结论选项

		// 业务类型判断llregister.rgtobj：1-个险 2-团险
		// String tNCLType = mNCLType.trim().equals("1") ? " and exists (select
		// 'X' from llregister where a.otherno = llregister.rgtno and
		// llregister.rgtobj = '1' ) "
		// : " and exists (select 'X' from llregister where a.otherno =
		// llregister.rgtno and llregister.rgtobj = '2' ) ";
		String tNCLType = mNCLType.trim().equals("1") ? " and b.rgtobj='1' "
				: " and b.rgtobj='2' ";

		// 再分别查询,给每个管理机构赋值
		if (ssrsMng.getMaxRow() != 0) {
			for (int i = 1; i <= ssrsMng.getMaxRow(); i++) {
				String tMngCode = ssrsMng.GetText(i, 1); // 管理机构代码
				String tMngName = ssrsMng.GetText(i, 2); // 管理机构名称

				/** **************************以下是几个公用的查询SQL语句************************************** */
				/**
				 * *用来计算日期时间之间的差,精确到分钟,保留两位小数 round(sum((
				 * a.outdate-b.makedate)*24*60 +
				 * to_number(to_date(a.outtime,'HH24:MI:SS')-to_date(b.maketime,'HH24:MI:SS'))*60*24)/count(*),2)
				 */

				// //理赔结论为空
				// //案件类型和理赔结论都为空 //案件类型不为空,理赔结论为空
				// String strSqlA = " select
				// count(*),round(sum((a.outdate-b.makedate)*24*60 "
				// + " +
				// to_number(to_date(a.outtime,'HH24:MI:SS')-to_date(b.maketime,'HH24:MI:SS'))*60*24)/count(*),2)
				// "
				// + " from lbmission a,llregister b "
				// + " where 1=1 "
				// + " and a.processid='0000000005' " //理赔
				// //+" and a.activityid='0000005075' " //结案
				// + " and a.activityid='0000005055' " //审批通过时间
				// + " and a.missionprop1=trim(b.rgtno) "
				// + " and a.missionprop20 like '" + tMngCode + "%' "
				// + " and (a.outdate between '" + mStartDate + "' and '" +
				// mEndDate + "') "
				// + " and (b.makedate between '" + mStartDate + "' and '" +
				// mEndDate + "') ";
				//
				// //理赔结论不为空
				// //案件类型为空,理赔结论不为空 //案件类型和理赔结论都不为空
				// String strSqlB = " select count(*),round(sum((
				// a.outdate-b.makedate)*24*60 "
				// + " +
				// to_number(to_date(a.outtime,'HH24:MI:SS')-to_date(b.maketime,'HH24:MI:SS'))*60*24)/count(*),2)
				// "
				// + " from lbmission a,llregister b,llclaim c "
				// + " where 1=1 "
				// + " and a.missionprop1=trim(b.rgtno) "
				// + " and a.missionprop1=trim(c.clmno) "
				// + " and a.processid='0000000005' " //理赔
				// // +" and a.activityid='0000005075' " //结案
				// + " and a.activityid='0000005055' " //审批通过时间
				// + " and a.missionprop20 like '" + tMngCode + "%' "
				// + " and (a.outdate between '" + mStartDate + "' and '" +
				// mEndDate + "') "
				// + " and (b.makedate between '" + mStartDate + "' and '" +
				// mEndDate + "') ";
				String strSqlA ="";
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					strSqlA = " select count(*),round(sum(( a.examdate - b.makedate)*24*60 "// b.rgtdate与b.makedate意义相同
							+ " + to_number(to_date(a.modifytime,'HH24:MI:SS')-to_date(b.maketime,'HH24:MI:SS'))*60*24)/count(*),2) "
							+ " from llclaimuwmain a,llregister b "
							+ tPayConTa
							+ " and a.clmno = b.rgtno "
							+ " and b.mngcom like concat('"
							+ "?mngcode?"
							+ "','%') "
							+ tNCLType
							+ " and a.examconclusion = '0' "// 审批通过
							+ " and a.examdate between '"
							+ "?startdate?"
							+ "' and '"
							+ "?enddate?" + "' ";
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					strSqlA = " SELECT COUNT(*), ROUND(SUM(DATEDIFF('2016-01-02' , '2016-01-01') * 24 * 60 + to_number ("
							+ "TIME_TO_SEC(to_date ('12:00:00', 'HH24:MI:SS')) - TIME_TO_SEC(to_date ('6:00:00', 'HH24:MI:SS'))) /60"
							+ ") / COUNT(*),2)  "
							+ " from llclaimuwmain a,llregister b "
							+ tPayConTa
							+ " and a.clmno = b.rgtno "
							+ " and b.mngcom like concat('"
							+ "?mngcode?"
							+ "','%') "
							+ tNCLType
							+ " and a.examconclusion = '0' "// 审批通过
							+ " and a.examdate between '"
							+ "?startdate?"
							+ "' and '"
							+ "?enddate?" + "' ";
				}
				
				
				/** ************************用案件类型去拼SQL语句**************************************************** */
				if (mCaseType.equals(""))// 全部案件类型
				{
					strSqlA = strSqlA;
				} else {
					switch (Integer.parseInt(mCaseType)) {
					case 1:// 简易案件
						strSqlA = strSqlA + " and b.rgtstate='01' ";
						break;
					case 11:// 普通案件
						strSqlA = strSqlA + " and b.rgtstate='11' ";
						break;
					case 12:// 申诉案件
						strSqlA = strSqlA + " and b.rgtstate='12' ";
						break;
					case 13:// 诉讼案件
						strSqlA = strSqlA + " and b.rgtstate='13' ";
						break;
					case 14:// 疑难案件
						strSqlA = strSqlA + " and b.rgtstate='14' ";
						break;
					default:
						break;
					}
				}
				/** ************************用理赔结论去拼SQL语句**************************************************** */
				if (mPayCon.equals(""))// 全部理赔结论
				{
					strSqlA = strSqlA;
				} else {
					switch (Integer.parseInt(mPayCon)) {
					case 0:
						strSqlA = strSqlA + " and c.givetype='0' ";
						break;// 给付
					case 1:
						strSqlA = strSqlA + " and c.givetype='1' ";
						break;// 拒付
					case 2:
						strSqlA = strSqlA + " and c.givetype='2' ";
						break;// 客户撤案
					case 3:
						strSqlA = strSqlA + " and c.givetype='3' ";
						break;// 公司撤案
					default:
						break;
					}
				}
				/** ********************************************************************************************** */
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSqlA);
				sqlbv1.put("mngcode", tMngCode);
				sqlbv1.put("startdate", mStartDate);
				sqlbv1.put("enddate", mEndDate);
				ExeSQL exSql = new ExeSQL();
				SSRS ssSql = new SSRS();
				ssSql = exSql.execSQL(sqlbv1);
				String tLPSX = ssSql.GetText(1, 2);
				if (tLPSX == null || tLPSX.equals("") || tLPSX.equals("null")) {
					tLPSX = "0.00";
				}

				/** ********************************给模板中的列赋值**************************************************** */
				String[] Stra = new String[3]; // 给模板中的每一列赋值
				Stra[0] = tMngCode; // 机构或用户名称
				Stra[1] = tMngName; // 机构或用户名称
				Stra[2] = tLPSX; // 件均理赔时效
				tListTable.add(Stra);
			}
		} else // 没有查询到管理机构
		{
			CError tError = new CError();
			tError.moduleName = "LLPRRComEfficiencyBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}
		/** ********************************给模板中的单个变量赋值************************************************ */
		logger.debug("----------以下查询是为单个变量赋值-----------");
		// 统计日期,默认为系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		tTextTag.add("CEStaDate", SysDate); // 统计日期: $=/CEStaDate$

		// 统计时间段：$=/CEStaTimes$
		String tStatTimes = mStartDate + "至" + mEndDate;
		tTextTag.add("CEStaTimes", tStatTimes);

		// 统计机构：$=/CEStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("CEStatSer", tMngCom);

		// 机构层面：$=/CEStatManage$
		String tLevelName = "";
		switch (Integer.parseInt(mLevel)) {
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
		tTextTag.add("CEStatManage", tLevelName);

		// 案件类型：$=/CECaseType$
		String tCaseTypeName = "";
		if (mCaseType.equals("")) {
			tCaseTypeName = "全部案件类型";
		} else {
			switch (Integer.parseInt(mCaseType)) {
			case 1:
				tCaseTypeName = "简易案件";
				break;
			case 11:
				tCaseTypeName = "普通案件";
				break;
			case 12:
				tCaseTypeName = "诉讼案件";
				break;
			case 13:
				tCaseTypeName = "申诉案件";
				break;
			case 14:
				tCaseTypeName = "疑难案件";
				break;
			default:
				tCaseTypeName = "";
				break;
			}
		}
		tTextTag.add("CECaseType", tCaseTypeName);

		// 理赔结论：$=/CEConclu$
		String tPayConName = "";
		if (mPayCon.equals("")) {
			tPayConName = "全部理赔结论";
		} else {
			switch (Integer.parseInt(mPayCon)) {
			case 0:
				tPayConName = "给付";
				break;
			case 1:
				tPayConName = "拒付";
				break;
			case 2:
				tPayConName = "客户撤案";
				break;
			case 3:
				tPayConName = "公司撤案";
				break;
			default:
				tPayConName = "";
				break;
			}
		}
		tTextTag.add("CEConclu", tPayConName);

		// 申请类型判断
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 ";
		tTextTag.add("CEApplType", tApplTypeName); // 申请类型$=/CEApplType$

		/** ****************************************************************************************** */
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

		tTransferData.setNameAndValue("ManageCom", "8632"); // 统计机构
		tTransferData.setNameAndValue("Level", "03"); // 机构层面
		tTransferData.setNameAndValue("StartDate", "2005-1-1"); // 统计起期
		tTransferData.setNameAndValue("EndDate", "2005-9-23"); // 统计止期
		tTransferData.setNameAndValue("CaseType", "01"); // 案件类型
		tTransferData.setNameAndValue("PayCon", ""); // 理赔结论

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRComEfficiencyBL tLLPRRComEfficiencyBL = new LLPRRComEfficiencyBL();
		if (tLLPRRComEfficiencyBL.submitData(tVData, "") == false) {
			logger.debug("-------统计报表打印：机构理赔时效统计---失败----");
		}
		int n = tLLPRRComEfficiencyBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRComEfficiencyBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
