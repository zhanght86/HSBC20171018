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
 * Description: 清单打印：处理中案件清单--LLPRBCaseDealProcess.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhaoy，2005-9-1 18:07
 * @version 1.0
 */
public class LLPRBCaseDealProcessBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBCaseDealProcessBL.class);

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
	private String strMngCom = "";
	private String strStartDate = "";
	private String strEndDate = "";
	private String strOverTime = "";
	private String strWorkPool = "";
	private String mNCLType = ""; // 申请类型

	public LLPRBCaseDealProcessBL() {
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
		this.strWorkPool = (String) mTransferData.getValueByName("strWorkPool");
		this.mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		TextTag texttag = new TextTag();
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("LLPRBCaseDealProcess.vts", ""); // appoint
																	// to a
																	// special
																	// output
																	// .vts file
		ListTable tlistTable = new ListTable();
		tlistTable.setName("DICASE");

		double sumMoney = 0;
		int sumcount = 0;
		String strSql = "";
		String ClmState = "";
		String ComName = "";
		String applType = ""; // 申请类型
		if (mNCLType.trim().equals("1")) // 申请类型
		{
			applType = " and Exists (Select 'X' From Llregister Where trim(Llregister.Rgtno) = a.MissionProp1 And Llregister.Rgtobj = '1')";
		} else {
			applType = " and Exists (Select 'X' From Llregister Where trim(Llregister.Rgtno) = a.MissionProp1 And Llregister.Rgtobj = '2')";
		}

		// =========2005-9-25 10:53=======by
		// zl==========使用activityid作为判断条件===============BEG==============================================================================
		if (strWorkPool.trim().equals("00")) // 调查队列
		{
			strSql = "select a.MissionProp1,a.MakeDate,a.MissionProp7,a.activityid,(case when b.StandPay is null then 0 else b.StandPay end),(case when b.RealPay is null then 0 else b.RealPay end)"
					+ " from lwmission a left join LLClaim b on trim(a.MissionProp1) = trim(b.ClmNo) "
					+ " where a.activityid in('0000005125','0000005145','0000005165','0000005175')"
					+ " and a.MakeDate between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "'"
					+ " and MissionProp20 like concat('"
					+ "?mngcom?"
					+ "','%')"
					+ applType;
		} else if (strWorkPool.trim().equals("0000005035")) // 审核队列,因为有审批不通过的特殊情况
		{
			strSql = "select a.MissionProp1,a.MakeDate,case when a.DefaultOperator is not null then a.DefaultOperator else a.MissionProp19 end,a.activityid,(case when b.StandPay is null then 0 else b.StandPay end),(case when b.RealPay is null then 0 else b.RealPay end)"
					+ " from lwmission a left join LLClaim b on trim(a.MissionProp1)=trim(b.ClmNo) "
					+ " where a.activityid = '"
					+ "?workpool?"
					+ "'"
					+ " and (a.DefaultOperator is not null or (a.MissionProp19 is not null and a.MissionProp19 != '0')) "
					+ " and a.MakeDate between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "'"
					+ " and MissionProp20 like concat('"
					+ "?mngcom?" + "','%')" + applType;
		} else // 非调查队列（立案、审批、结案）
		{
			if (mNCLType.trim().equals("2") && strWorkPool.equals("0000005015"))// 团体立案activityid
																				// =
																				// '0000005215'
			{// Modify by zhaorx 2006-06-19
				// 立案确认前lwmission中只存一条整案信息，missionprop1为团体赔案号，连llregister表取相关信息。
				strSql = "select c.rgtno,a.MakeDate,a.DefaultOperator,a.activityid,(case when b.StandPay is null then 0 else b.StandPay end),(case when b.RealPay is null then 0 else b.RealPay end)"
						+ " from lwmission a,LLClaim b right join llregister c on trim(c.rgtobjno)=b.ClmNo "
						+ " where a.activityid = '0000005215' "
						+ " and a.missionprop1 = trim(c.rgtobjno) and c.rgtobj='2' "
						+ " and a.DefaultOperator is not null "
						+ " and a.MakeDate between '"
						+ "?startdate?"
						+ "' and '"
						+ "?enddate?"
						+ "'"
						+ " and MissionProp20 like concat('" + "?mngcom?" + "','%')";
			} else {
				strSql = "select a.MissionProp1,a.MakeDate,a.DefaultOperator,a.activityid,(case when b.StandPay is null then 0 else b.StandPay end),(case when b.RealPay is null then 0 else b.RealPay end)"
						+ " from lwmission a left join LLClaim b on trim(a.MissionProp1)=trim(b.ClmNo) "
						+ " where a.activityid = '"
						+ "?workpool?"
						+ "'"
						+ " and a.DefaultOperator is not null "
						+ " and a.MakeDate between '"
						+ "?startdate?"
						+ "' and '"
						+ "?enddate?"
						+ "'"
						+ " and MissionProp20 like concat('"
						+ "?mngcom?"
						+ "','%')"
						+ applType;
			}
		}

		if (strOverTime.trim().equals("") || strOverTime.trim().equals(null)) // 无超过时长条件
		{
			if (mNCLType.trim().equals("2") && strWorkPool.equals("0000005015")) {
				strSql = strSql
						+ " order by a.MakeDate,b.StandPay,b.RealPay,c.rgtno ";
			} else {
				strSql = strSql
						+ " order by a.MakeDate,b.StandPay,b.RealPay,a.MissionProp1";
			}

		} else // 有超过时长条件
		{
			if (mNCLType.trim().equals("2") && strWorkPool.equals("0000005015")) {
				strSql = strSql + " and datediff(to_date('" + "?enddate?"
						+ "','YYYY-MM-DD'),a.MakeDate)>= to_number('"
						+ "?overtime?" + "')"
						+ " order by a.MakeDate,b.StandPay,b.RealPay,c.rgtno ";
			} else {
				strSql = strSql
						+ " and datediff(to_date('"
						+ "?enddate?"
						+ "','YYYY-MM-DD'),a.MakeDate)>= to_number('"
						+ "?overtime?"
						+ "')"
						+ " order by a.MakeDate,b.StandPay,b.RealPay,a.MissionProp1 ";
			}

		}
		// =========2005-9-25 10:53=======by
		// zl==========使用activityid作为判断条件===============END==============================================================================
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("startdate", strStartDate);
		sqlbv.put("enddate", strEndDate);
		sqlbv.put("mngcom", strMngCom);
		sqlbv.put("workpool", strWorkPool);
		sqlbv.put("overtime", strOverTime);
		logger.debug("---开始执行sql操作：");
		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs = exeSQL.execSQL(sqlbv);

		if (ssrs.getMaxRow() != 0) {
			sumcount = ssrs.getMaxRow();
			for (int index = 1; index <= ssrs.getMaxRow(); index++) {
				String ClmNo = ssrs.GetText(index, 1); // 取得赔案号

				// ======2005-9-25 12:00=========by
				// zl==========对于报案到立案尚未保存信息联合报案分案查询==========BEG
				// 取得出险日期，出险人
				strSql = "select a.CustomerNo,a.AccDate from LLCase a "
						+ " where CaseNo = '" + "?clmno?" + "'" + " union "
						+ "select b.CustomerNo,b.AccDate from llsubreport b "
						+ " where subrptno = '" + "?clmno?" + "'" + " order by 2 ";
				// ======2005-9-25 12:00=========by
				// zl==========对于报案到立案尚未保存信息联合报案分案查询==========END
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSql);
				sqlbv1.put("clmno", ClmNo);
				SSRS ssrs1 = exeSQL.execSQL(sqlbv1);
				String CusNo = "";
				String CaseDate = "";
				String CusName = "";
				if (ssrs.getMaxRow() != 0) {
					CusNo = ssrs1.GetText(1, 1); // 取得出险人
					LDPersonDB tLDPersonDB = new LDPersonDB();
					tLDPersonDB.setCustomerNo(CusNo);
					if (tLDPersonDB.getInfo()) {
						CusName = tLDPersonDB.getName();
					}
					CaseDate = ssrs1.GetText(1, 2); // 出险日期
					logger.debug("出险日期" + CaseDate);
				}
				// 取得事故日期
				strSql = " select a.AccDate from LLAccident a,LLCaseRela b where a.AccNo=b.CaseRelaNo and b.CaseNo='"
						+ "?clmno?" + "'";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(strSql);
				sqlbv2.put("clmno", ClmNo);
				String AccDate = exeSQL.getOneValue(sqlbv2);
				AccDate = AccDate.substring(0, 10);
				logger.debug("事故日期" + AccDate);
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
				switch (Integer.parseInt(strWorkPool)) {
				case 5015:
					ClmState = "立案";
					break;
				case 5035:
					ClmState = "审核";
					break;
				case 5055:
					ClmState = "审批";
					break;
				case 5075:
					ClmState = "结案";
					break;
				case 0:
					ClmState = "调查";
					break;
				default:
					ClmState = "";
					break;
				}
				// 取得VIP标识
				LDPersonDB tLDPersonDB = new LDPersonDB();
				tLDPersonDB.setCustomerNo(CusNo);
				String VIPValue = "";
				String vipflag = "";
				if (tLDPersonDB.getInfo()) {
					VIPValue = tLDPersonDB.getVIPValue();
				}
				vipflag = (VIPValue == "1") ? "VIP" : "非VIP";
				// 取得金额
				double StandPay = 0;
				double RealPay = 0;
				double pay = 0;
				StandPay = Double.parseDouble(ssrs.GetText(index, 5));
				RealPay = Double.parseDouble(ssrs.GetText(index, 6));
				// 机构名称
				LDComSchema tLDComSchema = new LDComSchema();
				LDComDB tLDComDB = new LDComDB();
				tLDComDB.setComCode(strMngCom);
				tLDComDB.getInfo();
				tLDComSchema = tLDComDB.getSchema();
				String tMngComName = tLDComSchema.getName();
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
				String tJA = " select z.username ,r.EndCaseDate from llclaim r,"// 结案人，结案日期
						+ " llclaimuser z where z.usercode=r.clmuwer and r.ClmState ='60' and r.clmno = '"
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
				String tDC = " select InqPer,InqStartDate from llinqapply where clmno='"
						+ "?clmno?" + "' ";// 调查人，调查时间
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(tDC);
				sqlbv5.put("clmno", ClmNo);
				SSRS tDCSSRS = new SSRS();
				tDCSSRS = exeSQL.execSQL(sqlbv5);
				String tDCName = "";
				String tDCTime = "";
				if (tDCSSRS.getMaxRow() > 0) {
					for (int r = 1; r <= tDCSSRS.getMaxRow(); r++)// 一个案件可能发起多个调查
					{
						String tDCName_r = tDCSSRS.GetText(r, 1);
						String tDCTime_r = tDCSSRS.GetText(r, 2);
						tDCName = tDCName + tDCName_r + " ";
						tDCTime = tDCTime + tDCTime_r + " ";
					}
				}
				// 数据存储
				String[] cols = new String[21];
				cols[0] = strMngCom; // 机构代码
				cols[1] = tMngComName; // 机构名称
				cols[2] = ClmNo; // 案赔号
				cols[3] = CusName; // 出险人
				cols[4] = RptorName; // 报案人
				cols[5] = RptorPhone; // 报案人联系方式
				cols[6] = AccDate; // 事故日期
				cols[7] = CaseDate; // 出险日期
				cols[8] = ClmState; // 案件状态
				cols[9] = tRgtDate; // 立案时间
				cols[10] = UserNameOp; // 立案人
				cols[11] = tAuditDate; // 审核时间
				cols[12] = UserNameAu; // 审核人
				cols[13] = tExamDate; // 审批时间
				cols[14] = UserNameEx; // 审批人
				cols[15] = tDCTime; // 调查时间
				cols[16] = tDCName; // 调查人
				cols[17] = tJATime; // 结案时间
				cols[18] = tJAName; // 结案人
				if (strWorkPool.trim().equals("0000005055")
						|| strWorkPool.trim().equals("0000005075")) {
					pay = RealPay; // 核赔金额
				} else {
					pay = StandPay; // 预估金额
				}
				sumMoney = sumMoney + pay;
				cols[19] = Double.toString(pay); // 金额
				cols[20] = vipflag; // VIP标识
				tlistTable.add(cols);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLPRBCaseDealProcessBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据！";
			mErrors.addOneError(tError);
			return false;
		}

		String[] col = new String[1];
		col[0] = "";
		xmlexport.addListTable(tlistTable, col);
		// 取得机构名称
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(strMngCom);
		if (tLDComDB.getInfo()) {
			ComName = tLDComDB.getName();
		}
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 "; // 申请类型
		String StatCondition = "统计机构：" + ComName + "/统计时间段：" + strStartDate
				+ "至" + strEndDate + "/工作队列选项：" + ClmState + "/申请类型："
				+ tApplTypeName;

		if (!(strOverTime.trim().equals(""))
				&& !(strOverTime.trim().equals(null))) {
			StatCondition = StatCondition + "/超过时长：" + strOverTime + "天";
		}

		texttag.add("StatCondition", StatCondition);
		// 统计日期
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		texttag.add("StatDate", SysDate);
		texttag.add("StatAmount", sumcount); // 统计数量
		texttag.add("StatMoney", sumMoney); // 统计金额

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
		tG.ManageCom = "86";
		tG.ComCode = "86";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("strMngCom", "86");
		tTransferData.setNameAndValue("strStartDate", "2005-10-01");
		tTransferData.setNameAndValue("strEndDate", "2005-12-07");
		tTransferData.setNameAndValue("strOverTime", "1");
		tTransferData.setNameAndValue("strWorkPool", "0000005075");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRBCaseDealProcessBL tLLPRBCaseDealProcessBL = new LLPRBCaseDealProcessBL();
		if (tLLPRBCaseDealProcessBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}
		int n = tLLPRBCaseDealProcessBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRBCaseDealProcessBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
