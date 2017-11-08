package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.schema.LLClaimUserSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
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
 * Description: 清单打印：未处理案件清单--LLPRBCaseNoDeal.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhaoy，2005-9-1 10:48
 * @version 1.0
 */
public class LLPRBCaseNoDealBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBCaseNoDealBL.class);

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
	private String strMngCom = "";// 统计机构
	private String strStartDate = "";// 统计起期
	private String strEndDate = "";// 统计止期
	private String strOverTime = "";// 超过时长
	private String strSharePool = "";// 共享池选项
	private String mNCLType = "";// 业务类型

	public LLPRBCaseNoDealBL() {
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
		this.strMngCom = (String) mTransferData.getValueByName("strMngCom");
		this.strStartDate = (String) mTransferData
				.getValueByName("strStartDate");
		this.strEndDate = (String) mTransferData.getValueByName("strEndDate");
		this.strOverTime = (String) mTransferData.getValueByName("strOverTime");
		this.strSharePool = (String) mTransferData
				.getValueByName("strSharePool");
		this.mNCLType = (String) mTransferData.getValueByName("NCLType");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		TextTag texttag = new TextTag();
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("LLPRBCaseNoDeal.vts", ""); // appoint to a
																// special
																// output .vts
																// file
		ListTable tlistTable = new ListTable();
		tlistTable.setName("NODEAL");

		double sumMoney = 0;
		int sumcount = 0;
		String strSql;
		String ClmState = "";
		String ComName = "";
		String strSQL2;
		String strSQL21;
		String strSQL22;
		String strSQL23;
		String strSQL24;
		String strSQL25;
		// =========2005-9-25 10:53=======by
		// zl==========使用activityid作为判断条件===============BEG==============================================================================
		// strSql = "select
		// a.MissionProp1,a.MissionProp4,a.MissionProp6,a.MissionProp2,a.MakeDate,a.LastOperator,a.MissionProp3,nvl(b.StandPay,0),nvl(b.RealPay,0)"
		// + " from lwmission a,LLClaim b where a.activityid like '0000005%' and
		// (a.MakeDate between '"
		// + strStartDate + "' and '" + strEndDate + "') and a.MissionProp2='" +
		// strSharePool + "' and MissionProp20 like '" + strMngCom
		// +"%' and a.DefaultOperator is null and
		// trim(a.MissionProp1)=trim(b.ClmNo(+))";
		// 业务类型判断llregister.rgtobj：1-个险 2-团险（共享池选项为待支付SQL中判断条件）
		String nclType00 = mNCLType.trim().equals("1") ? " and llregister.rgtobj = '1' "
				: " and llregister.rgtobj = '2' ";
		// 业务类型判断llregister.rgtobj：1-个险 2-团险（共享池选项不是待支付SQL中判断条件）
		String nclType50 = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where a.missionprop1 = trim(llregister.rgtno) and llregister.rgtobj = '1' ) "
				: " and exists (select 'X' from llregister where a.missionprop1 = trim(llregister.rgtno) and llregister.rgtobj = '2' ) ";
		if (mNCLType.equals("2") & strSharePool.equals("0000005015")) {
			nclType50 = " and exists (select 'X' from llreport where a.missionprop1 = trim(llreport.rgtobjno) and llreport.rgtobj = '2' )";
		}
		// 共享池选项为立案时,个险和团险activityid不同
		String mSharePool = "";
		if (mNCLType.equals("2") && strSharePool.equals("0000005015"))// 团体待立案
		{
			mSharePool = "0000005215";
		} else {
			mSharePool = strSharePool;
		}
		// 对待审核特殊处理
		String tDSH = strSharePool.equals("0000005035") ? " and (a.MissionProp19='0' or a.MissionProp19 is null) "
				: " ";
		if (mNCLType.equals("2") & strSharePool.equals("0000005015")) {
			strSql = " select c.rptno,a.MissionProp4,a.MakeDate,a.MissionProp6," // a.MissionProp2,a.MakeDate,a.LastOperator,
					+ " a.MissionProp3,(case when b.StandPay is null then 0 else b.StandPay end),(case when b.RealPay is null then 0 else b.RealPay end),"
					+ " c.Rgtobjno" // 团体赔案号
					+ " from lwmission a,LLClaim b,llreport c "
					+ " where a.activityid = '"
					+ "?sharepool?"
					+ "'"
					+ " and a.DefaultOperator is null "
					+ tDSH
					+ " And trim(a.Missionprop1) = trim(c.Rgtobjno)"
					+ " And c.rptno = Trim(b.Clmno(+))"
					+ " and a.MakeDate between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "'"
					+ " and a.MissionProp20 like concat('"
					+ "?mngcom?" + "','%')";

		} else {
			strSql = " select a.MissionProp1,a.MissionProp4,a.MakeDate,a.MissionProp6," // a.MissionProp2,a.MakeDate,a.LastOperator,
					+ " a.MissionProp3,(case when b.StandPay is null then 0 else b.StandPay end),(case when b.RealPay is null then 0 else b.RealPay end),"
					+ " (select LLReport.rgtobjno from LLReport where LLReport.rptno = b.ClmNo)" // 团体赔案号
					+ " from lwmission a,LLClaim b "
					+ " where a.activityid = '"
					+ "?sharepool?"
					+ "'"
					+ " and a.DefaultOperator is null "
					+ tDSH
					+ " and a.MissionProp1 = trim(b.ClmNo(+))"
					+ " and a.MakeDate between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "'"
					+ " and a.MissionProp20 like concat('"
					+ "?mngcom?" + "','%')" + nclType50;
		}
		strSQL21 = " select llsubreport.subrptno, " // 1 Clmno
				+ " (select ldperson.name from ldperson where ldperson.customerno = llsubreport.customerno),"// 2出险人
				+ " (select llaccident.accdate from llaccident,llcaserela where llaccident.accno = llcaserela.caserelano and llcaserela.caseno = llsubreport.subrptno),"// 3出险日期
				// + " null,"//4?
				+ " (select llclaimuwmain.examdate from llclaimuwmain where llclaimuwmain.clmno = llsubreport.subrptno),"// 5进入赔案个阶段的时间
				// + " null," //6操作人姓名
				+ " llsubreport.customerno,"// 7客户号
				+ " 0,"// 8StandPay
				+ " (select (case when sum(llclaim.realpay) is null then 0 else sum(llclaim.realpay) end) from llclaim where llclaim.clmno = llsubreport.subrptno),"// 9金额
				+ " (select LLReport.rgtobjno from LLReport where LLReport.rptno = llsubreport.subrptno)" // 团体赔案号
				+ " from llsubreport where llsubreport.subrptno in "
				+ " (select llregister.rgtno from llregister, ljaget, llclaimuwmain "
				+ " where llregister.rgtno = ljaget.otherno "
				+ " and llclaimuwmain.clmno = llregister.rgtno "
				+ nclType00
				+ " and llclaimuwmain.auditdate between '"
				+ "?startdate?"
				+ "' and '"
				+ "?enddate?"
				+ "' "
				+ " and ljaget.enteraccdate is null "
				+ " and llregister.mngcom like concat('" + "?mngcom?" + "','%')";
		strSQL22 = " and datediff(to_date('" + "?enddate?"
				+ "','YYYY-MM-DD') , llclaimuwmain.auditdate)>= to_number('"
				+ "?overtime?" + "')";
		strSQL23 = " ) "
				+ " union "// 针对申诉案件的处理,申诉案件在llsubreport表中是没有记录的,通过申诉号找到原赔案相关原始信息
				+ " select llappeal.appealno,"// 1申诉号
				+ " (select ldperson.name from ldperson where ldperson.customerno = llsubreport.customerno),"// 2
				+ " (select llaccident.accdate from llaccident,llcaserela where llaccident.AccNo = llcaserela.caserelano and llcaserela.caseno = llappeal.appealno),"// 3
				// + " null,"//4
				+ " (select llclaimuwmain.examdate from llclaimuwmain where llclaimuwmain.clmno = llappeal.appealno),"// 5
				// + " null,"//6
				+ " llsubreport.customerno,"// 7
				+ " 0,"// 8
				+ " (select (case when sum(llclaim.realpay) is null then 0 else sum(llclaim.realpay) end) from llclaim where llclaim.clmno = llappeal.appealno),"// 9
				+ " (select LLReport.rgtobjno from LLReport where LLReport.rptno = llsubreport.subrptno)" // 团体赔案号
				+ " from llsubreport, llappeal "
				+ " where llappeal.caseno = llsubreport.subrptno"
				+ " and llsubreport.subrptno in"
				+ " (select llappeal.caseno from llappeal"
				+ " where llappeal.caseno in "
				+ " ((select llregister.rgtno from llregister, ljaget, llclaimuwmain"
				+ " where llregister.rgtno = ljaget.otherno "
				+ " and llclaimuwmain.clmno = llregister.rgtno " + nclType00
				+ " and llclaimuwmain.auditdate between '" + "?startdate?"
				+ "' and '" + "?enddate?" + "' "
				+ " and ljaget.enteraccdate is null "
				+ " and llregister.mngcom like concat('" + "?mngcom?" + "','%')";
		strSQL24 = " and datediff(to_date('" + "?enddate?"
				+ "','YYYY-MM-DD') , llclaimuwmain.auditdate)>= to_number('"
				+ "?overtime?" + "')";
		strSQL25 = " )))" + " order by 4,7,1 ";// 按时长、金额、赔案号排序

		if (strOverTime.trim().equals("") || strOverTime.trim().equals(null)) // 无超过时长条件
		{
			strSql = strSql
					+ " order by a.MakeDate,b.StandPay,b.RealPay,a.MissionProp1";
			strSQL2 = strSQL21 + strSQL23 + strSQL25;
		} else // 有超过时长条件
		{
			strSql = strSql
					+ " and datediff(to_date('"
					+ "?enddate?"
					+ "','YYYY-MM-DD') , a.MakeDate)>= to_number('"
					+ "?overtime?"
					+ "')"
					+ " order by a.MakeDate,b.StandPay,b.RealPay,a.MissionProp1";
			strSQL2 = strSQL21 + strSQL22 + strSQL23 + strSQL24 + strSQL25;
		}
		// =========2005-9-25 10:53=======by
		// zl==========使用activityid作为判断条件===============END==============================================================================

		logger.debug("---开始执行sql操作:");
		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs;
		if (strSharePool.trim().equals("0000000000")) {
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(strSQL2);
			sqlbv.put("sharepool", mSharePool);
			sqlbv.put("startdate", strStartDate);
			sqlbv.put("enddate", strEndDate);
			sqlbv.put("overtime", strOverTime);
			sqlbv.put("mngcom", strMngCom);
			ssrs = exeSQL.execSQL(sqlbv);
		} else {
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(strSql);
			sqlbv1.put("sharepool", mSharePool);
			sqlbv1.put("startdate", strStartDate);
			sqlbv1.put("enddate", strEndDate);
			sqlbv1.put("overtime", strOverTime);
			sqlbv1.put("mngcom", strMngCom);
			ssrs = exeSQL.execSQL(sqlbv1);
		}

		// logger.debug(new JdbcUrl().getIP());
		// logger.debug("xxxx:" + ssrs.getMaxRow());

		if (ssrs.getMaxRow() != 0) {
			sumcount = ssrs.getMaxRow();// 统计数量
			for (int index = 1; index <= ssrs.getMaxRow(); index++) {
				String ClmNo = ssrs.GetText(index, 1);
				String GrpClmNo = ssrs.GetText(index, 8);
				String CusNo = ssrs.GetText(index, 5);
				logger.debug("ClmNo" + ClmNo);
				logger.debug("CusNo" + CusNo);
				if (mNCLType.equals("1")) {
					GrpClmNo = "";
				}
				// 取得事故日期
				strSql = " select a.AccDate from LLAccident a,LLCaseRela b "
						+ " where a.AccNo=b.CaseRelaNo and b.CaseNo='" + "?clmno?"
						+ "'";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(strSql);
				sqlbv2.put("clmno", ClmNo);
				String AccDate = exeSQL.getOneValue(sqlbv2);
				AccDate = AccDate.substring(0, 10);
				// logger.debug("事故日期" + AccDate);
				// 取得报案人和联系方式
				String tSQLRptor = " select rptorname,rptorphone from llreport where RptNo = '"
						+ "?clmno?" + "' and RgtFlag != '30' ";
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(tSQLRptor);
				sqlbv3.put("clmno", ClmNo);
				SSRS tRptorSSRS = new SSRS();
				tRptorSSRS = exeSQL.execSQL(sqlbv3);
				String RptorName = "";
				String RptorPhone = "";
				if (tRptorSSRS.getMaxRow() > 0) {
					RptorName = tRptorSSRS.GetText(1, 1);
					RptorPhone = tRptorSSRS.GetText(1, 2);
				}
				// 案件状态
				int tCND = 0;
				tCND = Integer.parseInt(strSharePool);
				switch (tCND) {
				case 5015:
					ClmState = "待立案";
					break;
				case 5035:
					ClmState = "待审核";
					break;
				case 5055:
					ClmState = "待审批";
					break;
				case 5075:
					ClmState = "待结案";
					break;
				case 0:
					ClmState = "待支付";
					break;
				default:
					ClmState = "";
					break;
				}
				// 操作人姓名及时间
				LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
				LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
				tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(ClmNo);

				LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
				tLLClaimUWMainSchema = tLLPRTPubFunBL.getLLClaimUWMain(ClmNo);

				String tRgtDate = tLLRegisterSchema.getRgtDate();// 立案时间
				String tOperator = tLLRegisterSchema.getOperator();// 立案人代码
				String tAuditDate = tLLClaimUWMainSchema.getAuditDate();// 审核时间
				String tAuditPer = tLLClaimUWMainSchema.getAuditPer();// 审核人代码
				String tExamDate = tLLClaimUWMainSchema.getExamDate();// 审批时间
				String tExamPer = tLLClaimUWMainSchema.getExamPer();// 审批人代码

				LLClaimUserSchema tLLClaimUserOp = new LLClaimUserSchema();// 将立案人，审核人和审批人代码汉化
				tLLClaimUserOp = tLLPRTPubFunBL.getLLClaimUser(tOperator);
				String UserNameOp = tLLClaimUserOp.getUserName();// 立案人名称
				LLClaimUserSchema tLLClaimUserAu = new LLClaimUserSchema();
				tLLClaimUserAu = tLLPRTPubFunBL.getLLClaimUser(tAuditPer);
				String UserNameAu = tLLClaimUserAu.getUserName();// 审核人名字
				LLClaimUserSchema tLLClaimUserEx = new LLClaimUserSchema();
				tLLClaimUserEx = tLLPRTPubFunBL.getLLClaimUser(tExamPer);
				String UserNameEx = tLLClaimUserEx.getUserName();// 审批人名字

				String tJA = " select z.username ,r.EndCaseDate from llclaim r,llclaimuser z where z.usercode=r.clmuwer and r.ClmState ='60' and r.clmno = '"
						+ "?clmno?" + "'  ";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tJA);
				sqlbv4.put("clmno", ClmNo);
				SSRS tJASSRS = new SSRS();
				tJASSRS = exeSQL.execSQL(sqlbv4);
				String tJAName = "";
				String tJATime = "";
				if (tJASSRS.getMaxRow() > 0) {
					tJAName = tJASSRS.GetText(1, 1);
					tJATime = tJASSRS.GetText(1, 2);
				}
				// 取得VIP标识
				LDPersonDB tLDPersonDB = new LDPersonDB();
				tLDPersonDB.setCustomerNo(CusNo);
				String VIPValue = "";
				if (tLDPersonDB.getInfo()) {
					VIPValue = tLDPersonDB.getVIPValue();
				}
				String vipflag = (VIPValue == "1") ? "VIP" : "非VIP";
				double StandPay = 0;
				double RealPay = 0;
				double pay = 0;
				StandPay = Double.parseDouble(ssrs.GetText(index, 6));
				RealPay = Double.parseDouble(ssrs.GetText(index, 7));
				switch (Integer.parseInt(strSharePool)) {
				case 5035:
					pay = StandPay;
					break;// 预估金额-待审核
				case 5055:
					pay = RealPay;
					break;// 核赔金额-待审批
				case 5075:
					pay = RealPay;
					break;// 核赔金额-待结案
				case 0:
					pay = RealPay;
					break;// -待支付
				default:
					break;
				}
				sumMoney = sumMoney + pay;
				// 统计机构名称
				LDComSchema tLDComSchema = new LDComSchema();
				LDComDB tLDComDB = new LDComDB();
				tLDComDB.setComCode(strMngCom);
				tLDComDB.getInfo();
				tLDComSchema = tLDComDB.getSchema();
				String tMngComName = tLDComSchema.getName();
				// 数据存储
				String[] cols = new String[20];
				cols[0] = strMngCom;// 机构代码
				cols[1] = tMngComName;// 机构名称
				cols[2] = ClmNo;// 赔案号
				cols[3] = ssrs.GetText(index, 2);// 出险人
				cols[4] = RptorName;// 报案人
				cols[5] = RptorPhone;// 报案人联系方式
				cols[6] = AccDate;// 事故日期
				cols[7] = ssrs.GetText(index, 3);// 出险日期
				cols[8] = ClmState;// 案件状态
				cols[9] = tRgtDate;// 立案时间
				cols[10] = UserNameOp;// 立案人
				cols[11] = tAuditDate;// 审核时间
				cols[12] = UserNameAu;// 审核人
				cols[13] = tExamDate;// 审批时间
				cols[14] = UserNameEx;// 审批人
				cols[15] = tJATime;// 结案时间
				cols[16] = tJAName;// 结案人
				cols[17] = Double.toString(pay);// 金额
				cols[18] = vipflag;// VIP标识
				cols[19] = GrpClmNo;
				tlistTable.add(cols);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLPRBCaseNoDealBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		String[] col = new String[1];
		col[0] = "";

		xmlexport.addListTable(tlistTable, col);
		// 统计条件
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(strMngCom);
		if (tLDComDB.getInfo()) {
			ComName = tLDComDB.getName();
		}
		String mNCLTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";
		String StatCondition = "统计机构：" + ComName + " 统计时间段：" + strStartDate
				+ "至" + strEndDate + " 共享池选项：" + ClmState + " 申请类型："
				+ mNCLTypeName;
		if (!(strOverTime.trim().equals(""))
				&& !(strOverTime.trim().equals(null))) {
			StatCondition = StatCondition + " 超过时长：" + strOverTime + "天";
		}
		texttag.add("StatCondition", StatCondition);
		// 统计日期
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		texttag.add("StatDate", SysDate);
		texttag.add("StatAmount", sumcount); // 统计数量
		texttag.add("StatMoney", Double.toString(sumMoney)); // 统计金额

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		// xmlexport.outputDocumentToFile("D:\\", "test11"); //输出xml文档到文件
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
		tG.ManageCom = "862100";
		tG.ComCode = "862100";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("strMngCom", "862100");
		tTransferData.setNameAndValue("strStartDate", "2005-12-01");
		tTransferData.setNameAndValue("strEndDate", "2005-12-18");
		tTransferData.setNameAndValue("strOverTime", "");
		tTransferData.setNameAndValue("strSharePool", "0000005075");
		tTransferData.setNameAndValue("NCLType", "1");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRBCaseNoDealBL tLLPRBCaseNoDealBL = new LLPRBCaseNoDealBL();
		if (tLLPRBCaseNoDealBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}
		int n = tLLPRBCaseNoDealBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRBCaseNoDealBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
