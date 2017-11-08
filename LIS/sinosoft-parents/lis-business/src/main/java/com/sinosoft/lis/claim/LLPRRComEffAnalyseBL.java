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
 * Description: 统计报表打印：机构理赔时效情况分析--LLPRRComEffAnalyse.vts
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司）、机构统计范围选项（总公司、某分公司、某中支）、统计时间段 表头：报表名称、统计条件、统计日期
 * 数据项：机构、理赔时效 天， 天， 天， 天和 天的赔案件数（结案时间－立案起始时间，计算到分钟）和权重占比 算法：按照选择的条件统计各项数据 排序：机构
 * 表尾：各项数据的合计
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author niuzj,2005-10-04
 * @version 1.0 Modify by ZHaorx Modify by niuzj，2006-04-13，兼容团险
 */

public class LLPRRComEffAnalyseBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRComEffAnalyseBL.class);
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
	private String mCEAStAro = ""; // 统计口径
	private String mCEAStAroName = "";
	private String mNCLType = ""; // 申请类型

	public LLPRRComEffAnalyseBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------统计报表打印：机构理赔时效情况分析-----LLPRRComEffAnalyseBL测试-----开始----------");
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
		logger.debug("----------统计报表打印：机构理赔时效情况分析-----LLPRRComEffAnalyseBL测试-----结束----------");
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
		this.mCEAStAro = (String) mTransferData.getValueByName("CEAStAround"); // 统计口径
		this.mCEAStAroName = (String) mTransferData
				.getValueByName("CEAStAroundName");
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
		tXmlExport.createDocument("LLPRRComEffAnalyse.vts", ""); // 所用模板名称
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		ListTable tListTable = new ListTable(); // 新建一个ListTable的实例,给模板的每一列赋值
		tListTable.setName("CEA");

		// 定义列表标题，共11列
		String[] Title = new String[11];
		Title[0] = "";
		Title[1] = "";

		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/CEA/ROW/COL1内容，并为列表赋值--");

		// 各项数据的合计
		int tCEAAdd2 = 0;
		double tCEAAdd3 = 0.00;
		int tCEAAdd4 = 0;
		double tCEAAdd5 = 0.00;
		int tCEAAdd6 = 0;
		double tCEAAdd7 = 0.00;
		int tCEAAdd8 = 0;
		double tCEAAdd9 = 0.00;
		int tCEAAdd10 = 0;
		double tCEAAdd11 = 0.00;

		String tStAroTimes = mCEAStAro.equals("01") ? " and a.examconclusion ='0' and a.examdate "
				: " and b.endcaseflag = '1' and b.endcasedate ";// 统计口径条件
																// 01-审批通过日期
																// 02-结案日期
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

				int t1 = 0; // 理赔时效<=1天的赔案件数
				double tA = 0.00;
				int t2 = 0; // 理赔时效>1<=3天的赔案件数
				double tB = 0.00;
				int t3 = 0; // 理赔时效>3<=5天的赔案件数
				double tC = 0.00;
				int t4 = 0; // 理赔时效>5<=7天的赔案件数
				double tD = 0.00;
				int t5 = 0; // 理赔时效>7天的赔案件数
				double tE = 0.00;
				int tT = 0; // 总赔案件数

				/** **************************************************************************** */
				// round(sum((a.outdate-b.makedate) +
				// to_number(to_date(a.outtime,'HH24:MI:SS')-to_date(b.maketime,'HH24:MI:SS'))),2)
				// 该日期时间差计算函数参考"机构理赔时效统计"而来,不一样的是它计算出的日期时间差为天数,而不是分钟.
				// 保留两位小数
				/** **************************************************************************** */
				// String strSql = " select a.missionprop1, "
				// + " round(sum((a.outdate-b.makedate) "//b.makedate
				// 与b.rgtdate一致
				// + " +
				// to_number(to_date(a.outtime,'HH24:MI:SS')-to_date(b.maketime,'HH24:MI:SS'))),2)
				// "
				// + " from lbmission a,llregister b "
				// + " where 1=1 "
				// + " and a.processid='0000000005' " //理赔
				// // +" and a.activityid='0000005075' " //结案
				// + " and a.activityid='0000005055' " //审批通过时间
				// + " and trim(a.missionprop1)=trim(b.rgtno) "
				// + " and a.missionprop20 like '" + tMngCode + "%' "
				// + " and (a.outdate between '" + mStartDate + "' and '" +
				// mEndDate + "') "
				// + " and (b.makedate between '" + mStartDate + "' and '" +
				// mEndDate + "') "
				// + " group by a.missionprop1 ";
				String strSql = "";
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					strSql = " select a.clmno,"
							+ " round(nvl(sum((a.examdate -b.makedate) "
							+ " + to_number(to_date(a.modifytime,'HH24:MI:SS') - to_date(b.maketime,'HH24:MI:SS'))),0),2)"
							+ " from llclaimuwmain a,llregister b where a.clmno = b.rgtno "
							+ " and a.mngcom like concat('" + "?mngcode?" + "','%') " + tNCLType
							+ tStAroTimes + " between '" + "?startdate?" + "' and '"
							+ "?enddate?" + "' " + " group by a.clmno ";
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					strSql = " select a.clmno,"
							+ "ROUND(CASE WHEN SUM(DATEDIFF(a.examdate , b.makedate) + "
							+ "to_number ((TIME_TO_SEC(to_date(a.modifytime,'HH24:MI:SS'))- TIME_TO_SEC(to_date(b.maketime,'HH24:MI:SS')))/(24*60*60))) IS NOT NULL "
	                        + "THEN SUM(DATEDIFF(a.examdate , b.makedate) + "
							+ "to_number ((TIME_TO_SEC(to_date(a.modifytime,'HH24:MI:SS'))- TIME_TO_SEC(to_date(b.maketime,'HH24:MI:SS')))/(24*60*60))) ELSE 0 END,2) "
							+ " from llclaimuwmain a,llregister b where a.clmno = b.rgtno "
							+ " and a.mngcom like concat('" + "?mngcode?" + "','%') " + tNCLType
							+ tStAroTimes + " between '" + "?startdate?" + "' and '"
							+ "?enddate?" + "' " + " group by a.clmno ";
				}
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSql);
				sqlbv1.put("mngcode", tMngCode);
				sqlbv1.put("startdate", mStartDate);
				sqlbv1.put("enddate", mEndDate);
				ExeSQL exSql = new ExeSQL();
				SSRS ssSql = new SSRS();
				ssSql = exSql.execSQL(sqlbv1);
				for (int j = 1; j <= ssSql.getMaxRow(); j++) {
					String tDate = ssSql.GetText(j, 2); // 日期时间差

					// 分别统计不同理赔时效的赔案件数
					if (Double.parseDouble(tDate) <= 1) {
						t1 = t1 + 1; // 理赔时效 <=1天的赔案件数
					} else if ((Double.parseDouble(tDate) <= 3)
							&& (Double.parseDouble(tDate) > 1)) {
						t2 = t2 + 1; // 理赔时效 >1<=3天的赔案件数
					} else if ((Double.parseDouble(tDate) <= 5)
							&& (Double.parseDouble(tDate) > 3)) {
						t3 = t3 + 1; // 理赔时效 >3<=5天的赔案件数
					} else if ((Double.parseDouble(tDate) <= 7)
							&& (Double.parseDouble(tDate) > 5)) {
						t4 = t4 + 1; // 理赔时效 >5<=7天的赔案件数
					} else {
						t5 = t5 + 1; // 理赔时效 >7天的赔案件数
					}
				}
				tT = t1 + t2 + t3 + t4 + t5; // 总赔案件数
				if (tT != 0) {
					tA = (double) t1 / tT; // 权重
					tB = (double) t2 / tT;
					tC = (double) t3 / tT;
					tD = (double) t4 / tT;
					tE = (double) t5 / tT;
				}
				String[] Stra = new String[12]; // 给模板中的每一列赋值
				Stra[0] = tMngName; // 机构或用户名称
				Stra[1] = String.valueOf(t1); // 理赔时效 <=1天的赔案件数
				Stra[2] = new DecimalFormat("0.00").format(tA);
				Stra[3] = String.valueOf(t2); // 理赔时效 >1<=3天的赔案件数
				Stra[4] = new DecimalFormat("0.00").format(tB);
				Stra[5] = String.valueOf(t3); // 理赔时效 >3<=5天的赔案件数
				Stra[6] = new DecimalFormat("0.00").format(tC);
				Stra[7] = String.valueOf(t4); // 理赔时效 >5<=7天的赔案件数
				Stra[8] = new DecimalFormat("0.00").format(tD);
				Stra[9] = String.valueOf(t5); // 理赔时效 >7天的赔案件数
				Stra[10] = new DecimalFormat("0.00").format(tE);
				Stra[11] = tMngCode; // 机构代码
				tListTable.add(Stra);

				// 计算各项数据的合计
				tCEAAdd2 = tCEAAdd2 + t1;
				// tCEAAdd3 = tCEAAdd3 + tA;
				tCEAAdd4 = tCEAAdd4 + t2;
				// tCEAAdd5 = tCEAAdd5 + tB;
				tCEAAdd6 = tCEAAdd6 + t3;
				// tCEAAdd7 = tCEAAdd7 + tC;
				tCEAAdd8 = tCEAAdd8 + t4;
				// tCEAAdd9 = tCEAAdd9 + tD;
				tCEAAdd10 = tCEAAdd10 + t5;
				// tCEAAdd11 = tCEAAdd11 + tE;
			}
			tCEAAdd3 = (double) tCEAAdd2
					/ (tCEAAdd2 + tCEAAdd4 + tCEAAdd6 + tCEAAdd8 + tCEAAdd10);
			tCEAAdd5 = (double) tCEAAdd4
					/ (tCEAAdd2 + tCEAAdd4 + tCEAAdd6 + tCEAAdd8 + tCEAAdd10);
			tCEAAdd7 = (double) tCEAAdd6
					/ (tCEAAdd2 + tCEAAdd4 + tCEAAdd6 + tCEAAdd8 + tCEAAdd10);
			tCEAAdd9 = (double) tCEAAdd8
					/ (tCEAAdd2 + tCEAAdd4 + tCEAAdd6 + tCEAAdd8 + tCEAAdd10);
			tCEAAdd11 = (double) tCEAAdd10
					/ (tCEAAdd2 + tCEAAdd4 + tCEAAdd6 + tCEAAdd8 + tCEAAdd10);
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
		tTextTag.add("CEAStaDate", SysDate); // 统计日期: $=/CEAStaDate$

		// 统计口径： $=/CEAStAround$
		tTextTag.add("CEAStAround", mCEAStAroName);

		// 统计时间段：$=/CEAStaTimes$
		String tStatTimes = mStartDate + "至" + mEndDate;
		tTextTag.add("CEAStaTimes", tStatTimes);

		// 统计机构：$=/CEAStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("CEAStatSer", tMngCom);

		// 机构层面：$=/CEAStatManage$
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
		tTextTag.add("CEAManage", tLevelName);

		// 申请类型判断
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 ";
		tTextTag.add("CEAApplType", tApplTypeName); // 申请类型$=/CEAApplType$

		tTextTag.add("CEAAdd2", tCEAAdd2);
		tTextTag.add("CEAAdd3", new DecimalFormat("0.00").format(tCEAAdd3));
		tTextTag.add("CEAAdd4", tCEAAdd4);
		tTextTag.add("CEAAdd5", new DecimalFormat("0.00").format(tCEAAdd5));
		tTextTag.add("CEAAdd6", tCEAAdd6);
		tTextTag.add("CEAAdd7", new DecimalFormat("0.00").format(tCEAAdd7));
		tTextTag.add("CEAAdd8", tCEAAdd8);
		tTextTag.add("CEAAdd9", new DecimalFormat("0.00").format(tCEAAdd9));
		tTextTag.add("CEAAdd10", tCEAAdd10);
		tTextTag.add("CEAAdd11", new DecimalFormat("0.00").format(tCEAAdd11));

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

		tTransferData.setNameAndValue("StartDate", "2005-1-1"); // 统计起期
		tTransferData.setNameAndValue("EndDate", "2005-10-09"); // 统计止期
		tTransferData.setNameAndValue("Level", "01"); // 统计层面
		tTransferData.setNameAndValue("ManageCom", "86"); // 统计机构
		tTransferData.setNameAndValue("CEAStAround", "01"); // 统计口径 01 02

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRComEffAnalyseBL tLLPRRComEffAnalyseBL = new LLPRRComEffAnalyseBL();
		if (tLLPRRComEffAnalyseBL.submitData(tVData, "") == false) {
			logger.debug("-------统计报表打印：机构理赔时效情况分析---失败----");
		}
		int n = tLLPRRComEffAnalyseBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRComEffAnalyseBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
