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
 * Title:
 * </p>
 * <p>
 * Description: 慰问清单打印，模板为LLPRBCondole.vts
 * </p>
 * 由于取消了慰问信的打印需要增加慰问清单的输出 统计界面：报案起止日期、统计机构（总公司、某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）、
 * 案件标识（慰问案件、全部）、案件状态（未立案、全部） 表头：报表名称、统计条件、统计日期
 * 数据项：赔案号、出险人、事故日期、出险日期、报案时间、出险原因、治疗情况、理赔类型、报案人、报案人联系电话、报案人邮编、
 * 报案人联系地址、出险人联系电话、服务人员组织代码、服务人员代码和姓名、服务人员联系电话、报案受理人代码和姓名、 报案受理机构代码、出险情况、备注（第1条）
 * 算法：在选择的机构内、指定的起始日期内报案的相关案件 排序：报案时间、赔案号 表尾：统计数量 希望也能输出到Excel 备注用于记录出险人的治疗医院和病床
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author niuzj 20050831 modify by wanzh 2005/11/18
 * @version 1.0 modify by ZHaorx 2005-10-22 modift by niuzj 2005-11-25
 */

public class LLPRBCondoleBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBCondoleBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	// 向后台提交,后面传输数据的容器
	private VData mInputData = new VData();
	// 返回前台的数据包,往界面传输数据的容器
	private VData mResult = new VData();
	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();

	private String mComCode = ""; // 统计机构
	private String mStartDate = ""; // 报案开始日期
	private String mEndDate = ""; // 报案结束日期
	private String mCaseID = ""; // 案件标识
	private String mCaseState = ""; // 案件状态
	private String mNCLType = ""; // 申请类型
	private String mCurrentDate = PubFun.getCurrentDate(); // 系统日期
	private String mCurrentTime = PubFun.getCurrentTime(); // 系统时间

	public LLPRBCondoleBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------慰问清单LLPRBCondoleBL打印测试开始----------");
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
		// 准备输出数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("-----------慰问清单LLPRBCondoleBL打印测试结束----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData; // 得到外部传入的数据,将数据备份到本类中

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 报案开始日期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 报案结束日期
		mComCode = (String) mTransferData.getValueByName("StatiCode"); // 统计机构
		mCaseID = (String) mTransferData.getValueByName("CaseID"); // 案件标识
		mCaseState = (String) mTransferData.getValueByName("CaseState"); // 案件状态
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型
		return true;
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
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("LLPRBCondole.vts", ""); // 所用模板名称
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

		// 定义列表标题，共9列
		String[] Title = new String[24];
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
		Title[21] = "";
		Title[22] = "";
		Title[23] = "";

		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/CONDOLE/ROW/COL内容，并为列表赋值--");
		// 查询符合条件的赔案号
		String applType = mNCLType.trim().equals("1") ? " and a.rgtobj='1' "
				: " and a.rgtobj='2' ";
		String tStrSQL = " select a.rptno,b.customerno,a.accidentdate,b.accdate,a.rptdate,a.accidentreason, "
				+ " b.curedesc,a.rptorname,a.rptorphone,a.rptoraddress,a.operator,a.mngcom,b.accdesc,a.postcode "
				+ " from llreport a,llsubreport b "
				+ " where 1=1 "
				+ " and a.rptno=b.subrptno "
				+ applType // 申请类型
				+ " and (a.rptdate between '"
				+ "?startdate?"
				+ "' and '"
				+ "?enddate?" + "') " // 报案日期
				+ " and a.mngcom like concat('" + "?comcode?" + "','%') "; // 统计机构
				
		// 拼入案件标识
		if (mCaseID.equals("1")) {
			tStrSQL = tStrSQL + " and b.condoleflag='1' "; // 慰问案件
		} else {
			tStrSQL = tStrSQL;
		}
		// 拼入案件状态
		if (mCaseState.equals("1")) {
			tStrSQL = tStrSQL + " and a.rgtflag='10' "; // 未立案
		} else {
			tStrSQL = tStrSQL;
		}
		// 拼入排序条件
		tStrSQL = tStrSQL + " order by a.rptdate,a.rptno ";
		logger.debug("----------查询赔案号语句为-----------" + tStrSQL);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tStrSQL);
		sqlbv.put("startdate", mStartDate);
		sqlbv.put("enddate", mEndDate);
		sqlbv.put("comcode", mComCode);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		// 新建一个ListTable的实例,给模板的每一列赋值
		ListTable tListTable = new ListTable();
		tListTable.setName("CONDOLE");

		int tStatCount = 0; // 用来统计符合条件的记录的数量
		if (tSSRS.getMaxRow() != 0) {
			// 循环处理每一条打印记录
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				logger.debug("-------------第 " + i
						+ " 次循环开始----------------");
				String tClmNo = tSSRS.GetText(i, 1); // 赔案号
				String tCustNo = tSSRS.GetText(i, 2); // 出险人客户号
				String tAccidentDate = tSSRS.GetText(i, 3); // 事故日期
				String tAccDate = tSSRS.GetText(i, 4); // 出险日期
				String tRptDate = tSSRS.GetText(i, 5); // 报案日期
				String tAccReason = tSSRS.GetText(i, 6); // 出险原因（事故类型）代码
				String tCureDesc = tSSRS.GetText(i, 7); // 治疗情况代码
				String tRptorName = tSSRS.GetText(i, 8); // 报案人姓名
				String tRptorPhone = tSSRS.GetText(i, 9); // 报案人联系电话
				String tRptorAdd = tSSRS.GetText(i, 10); // 报案人联系地址
				String tOperator = tSSRS.GetText(i, 11); // 报案受理人代码
				String tMngCode = tSSRS.GetText(i, 12); // 报案受理机构代码
				String tAccDesc = tSSRS.GetText(i, 13); // 出险情况（事故描述）
				String tPostCode = tSSRS.GetText(i, 14); // 报案人邮编

				// 出险人姓名
				String tSql1 = " select a.name from ldperson a where "
						+ " a.customerno = '" + "?customerno?" + "'";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tSql1);
				sqlbv1.put("customerno", tCustNo);
				ExeSQL tExeSQL1 = new ExeSQL();
				String tCustName = tExeSQL1.getOneValue(sqlbv1);

				// 出险人联系电话
				String tSql2 = " select a.phone from lcaddress a where "
						+ " a.customerno='" + "?customerno?" + "'";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tSql2);
				sqlbv2.put("customerno", tCustNo);
				ExeSQL tExeSQL2 = new ExeSQL();
				String tCustPhone = tExeSQL2.getOneValue(sqlbv2);

				// 出险原因名称
				String tSql3 = " select d.codename from ldcode d  "
						+ " where d.codetype='lloccurreason' "
						+ " and trim(d.code)='" + "?accreason?" + "' ";
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(tSql2);
				sqlbv3.put("accreason", tAccReason);
				ExeSQL tExeSQL3 = new ExeSQL();
				String tAccReaName = tExeSQL3.getOneValue(sqlbv3);

				/**
				 * 从ldcode表中查询的另外一种方法 LDCodeSchema tLDCodeSchema = new
				 * LDCodeSchema(); LDCodeDB tLDCodeDB = new LDCodeDB();
				 * tLDCodeDB.setCodeType("lloccurreason");
				 * tLDCodeDB.setCode(tAccReason); tLDCodeDB.getInfo();
				 * tLDCodeSchema = tLDCodeDB.getSchema(); ttAccReaName =
				 * tLDCodeSchema.getCodeName();
				 */

				// 治疗情况名称
				String tSql4 = " select d.codename from ldcode d "
						+ " where d.codetype='llcuredesc' "
						+ " and trim(d.code)='" + "?curedesc?" + "' ";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tSql4);
				sqlbv4.put("curedesc", tCureDesc);
				ExeSQL tExeSQL4 = new ExeSQL();
				String tCureDescName = tExeSQL4.getOneValue(sqlbv4);

				// 理赔类型
				String tSql5 = " select b.codename from llreportreason a,ldcode b "
						+ " where trim(a.reasoncode)=trim(b.code) "
						+ " and b.codetype='llclaimtype' "
						+ " and a.rpno='"
						+ "?rpno?"
						+ "' "
						+ " and a.customerno='"
						+ "?customerno?"
						+ "' ";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(tSql5);
				sqlbv5.put("rpno", tClmNo);
				sqlbv5.put("customerno", tCustNo);
				SSRS tSSRS5 = new SSRS();
				tSSRS5 = cusExeSQL.execSQL(sqlbv5);
				String tReasonName = "";
				if (tSSRS5.getMaxRow() != 0) {
					for (int j = 1; j <= tSSRS5.MaxRow; j++) {
						String tReason = tSSRS5.GetText(j, 1);
						// 有多个理赔类型时要合并
						tReasonName = tReasonName + "/" + tReason;
					}
				} else {
					tReasonName = "";
				}

				// 服务人员代码
				String tSQLoperator = " select lcpol.agentcode from lcpol "
						+ " where lcpol.insuredno = '" + "?custno?" + "' "
						+ " or lcpol.appntno = '" + "?custno?" + "' "
						// + " and lcpol.AppFlag <> '0' " //1--保单 0--投保单
						+ " order by lcpol.StandPrem desc "; // 对应的保单可能有多个，从而服务人员可能有多个，这里取总标准保费最多的一个
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql(tSQLoperator);
				sqlbv6.put("custno", tCustNo);
				ExeSQL tExeSQL9 = new ExeSQL();
				String tWaitorCode = tExeSQL9.getOneValue(sqlbv6);

				// 服务人员姓名和联系电话的SQL，服务人员组织代码
				String tSQLNameandPhone = " select laagent.name,laagent.phone,laagent.agentgroup "
						+ " from laagent where laagent.agentcode = '"
						+ "?agentcode?" + "' ";
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(tSQLNameandPhone);
				sqlbv7.put("agentcode", tWaitorCode);
				SSRS tSSRS2 = new SSRS();
				tSSRS2 = cusExeSQL.execSQL(sqlbv7);
				String tWaitorName = "";
				String tRelphone = "";
				String tAggentGroup = "";
				if (tSSRS2.getMaxRow() > 0) {
					tWaitorName = tSSRS2.GetText(1, 1); // 服务人员姓名
					tRelphone = tSSRS2.GetText(1, 2); // 服务人员联系电话
					tAggentGroup = tSSRS2.GetText(1, 3); // 服务人员组织代码
				}

				// 报案受理人姓名
				String tSql6 = " select a.username from llclaimuser a "
						+ " where a.usercode='" + "?usercode?" + "' ";
				SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
				sqlbv8.sql(tSql6);
				sqlbv8.put("usercode", tOperator);
				ExeSQL tExeSQL6 = new ExeSQL();
				String tOperatorName = tExeSQL6.getOneValue(sqlbv8);

				// 备注
				String tSql7 = " select llclaimdesc.describe from llclaimdesc "
						+ " where llclaimdesc.descno = "
						+ " (select min(descno) from llclaimdesc where clmno = '"
						+ "?clmno?" + "') " // 报案备注信息的第一条,描述序号descno最小的
						+ " and llclaimdesc.clmno = '" + "?clmno?" + "' ";
				SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
				sqlbv9.sql(tSql7);
				sqlbv9.put("clmno", tClmNo);
				ExeSQL tExeSQL7 = new ExeSQL();
				String tReportInfor = tExeSQL7.getOneValue(sqlbv9);

				// 报案受理机构名称
				String tSql8 = " select name from ldcom where comcode='"
						+ "?comcode?" + "' ";
				SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
				sqlbv10.sql(tSql8);
				sqlbv10.put("comcode", tMngCode);
				ExeSQL tExeSQL8 = new ExeSQL();
				String tMngName = tExeSQL8.getOneValue(sqlbv10);

				// 为模板的每一列赋值
				String[] stra = new String[24];
				stra[0] = tClmNo; // 赔案号
				stra[1] = tCustName; // 出险人姓名
				stra[2] = tAccidentDate; // 事故日期
				stra[3] = tAccDate; // 出险日期
				stra[4] = tRptDate; // 报案日期
				stra[5] = tAccReaName; // 出险原因
				stra[6] = tCureDescName; // 治疗情况
				stra[7] = tReasonName; // 理赔类型
				stra[8] = tRptorName; // 报案人
				stra[9] = tRptorPhone; // 报案人联系电话
				stra[10] = tRptorAdd; // 报案人联系地址
				stra[11] = tCustPhone; // 出险人联系电话
				stra[12] = tAggentGroup; // 服务人员组织代码
				stra[13] = tWaitorCode; // 服务人员代码
				stra[14] = tWaitorName; // 服务人员姓名
				stra[15] = tRelphone; // 服务人员联系电话
				stra[16] = tOperator; // 报案受理人代码
				stra[17] = tOperatorName; // 报案受理人姓名
				stra[18] = tMngCode; // 报案受理机构
				stra[19] = tAccDesc; // 出险情况
				stra[20] = tReportInfor; // 备注
				stra[21] = tMngCode; // 机构代码(先用报案受理机构)
				stra[22] = tMngName; // 机构名称
				stra[23] = tPostCode; // 报案人邮编

				tListTable.add(stra);
				tStatCount = tStatCount + 1; // 记录数量加1
				logger.debug("-------------第 " + i
						+ " 次循环结束----------------");
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLPRBCondoleBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}
		logger.debug("----------以下查询是为单个变量赋值-----------");
		// 统计时间,默认为系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		// 管理机构名称
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mComCode);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		// String tStatCondition = "统计机构:" + tMngCom + "/报案起至时间:" + mStartDate +
		// "至" + mEndDate; //统计条件

		// 报案起期
		String tStartDate = mStartDate;
		// 报案止期
		String tEndDate = mEndDate;
		// 案件标识
		String tCaseID = "";
		if (mCaseID.equals("1")) {
			tCaseID = "慰问案件";
		} else {
			tCaseID = "全部";
		}
		// 案件状态Modify by zhaorx 2006-10-11
		String tCaseState = "";
		if (mCaseState.equals("1")) {
			tCaseState = "未立案";
		} else {
			tCaseState = "全部";
		}
		String applTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 ";
		tTextTag.add("StatCom", tMngCom); // 统计机构：$=/StatCom$
		tTextTag.add("StatDate", SysDate); // 统计日期：$=/StatDate$
		tTextTag.add("StartDate", tStartDate); // 报案起期：$=/StartDate$
		tTextTag.add("EndDate", tEndDate); // 报案止期：$=/EndDate$
		tTextTag.add("CaseID", tCaseID); // 案件标识：$=/CaseID$
		tTextTag.add("CaseState", tCaseState); // 案件状态：$=/CaseState$
		tTextTag.add("StatCount", tStatCount); // 统计数量：$=/StatCount$
		tTextTag.add("ApplType", applTypeName); // 申请类型

		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		logger.debug("********************************************");
		logger.debug("---以下 将tXmlExport打包，返回前台--");

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
		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/**
	 * 返回处理信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	// test
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StatiCode", "86"); // 统计机构
		tTransferData.setNameAndValue("StartDate", "2007-10-10"); // 报案开始日期
		tTransferData.setNameAndValue("EndDate", "2007-10-10"); // 报案结束日期
		tTransferData.setNameAndValue("CaseID", "1");
		tTransferData.setNameAndValue("CaseState", "1");
		tTransferData.setNameAndValue("NCLType", "2");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLPRBCondoleBL tLLPRBCondoleBL = new LLPRBCondoleBL();
		if (tLLPRBCondoleBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔清单打印：慰问清单出错---------------");
		}
	}
}
