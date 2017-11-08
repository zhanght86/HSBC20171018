package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LLBnfDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LLBnfSchema;
import com.sinosoft.lis.vschema.LLBnfSet;
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
 * Description: 清单打印:已结案件清单--LLPRBEndCase.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhaoy，2005-9-3 15:22
 * @version 1.0
 */
public class LLPRBEndCaseBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBEndCaseBL.class);

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
	private String strStatAround = "";// 统计口径
	private String strAuditConclu = "";// 审核结论
	private String mNCLType = "";// 申请类型

	public LLPRBEndCaseBL() {
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
		this.strStatAround = (String) mTransferData
				.getValueByName("strStatAround");
		this.strAuditConclu = (String) mTransferData
				.getValueByName("strAuditConclu");
		this.mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		TextTag texttag = new TextTag();
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("LLPRBEndCase.vts", ""); // appoint to a
															// special output
															// .vts file
		ListTable tlistTable = new ListTable();
		tlistTable.setName("ENDCASE");

		int sumcount = 0;
		String tTimeChance = strStatAround.equals("01") ? " b.ExamDate"
				: " a.EndCaseDate";// 判断统计口径
		String tClmState = strStatAround.equals("01") ? " and a.clmstate in ('50','60') "
				: " and a.clmstate = '60'";
		String tAuditConclu = "and b.AuditConclusion ";
		if (strAuditConclu.equals("03"))// 判断审核结论
		{
			tAuditConclu += " in ('0','1') ";
		} else {
			tAuditConclu += strAuditConclu.equals("01") ? " ='0' " : " ='1' ";
		}
		double sumMoney1 = 0;
		double sumMoney2 = 0;
		double sumMoney3 = 0;
		String strSql;
		String Dieflag = "";
		String CusNo = "";
		String CusName = "";
		String CusPhone = "";
		String AgentGroupName = "";
		String ComName = "";
		String PayeeName = ""; // 领款人
		String applType = mNCLType.trim().equals("1") ? " and a.rgtobj='1' "
				: " and a.rgtobj='2' ";// 申请类型
		strSql = " select a.RgtNo,a.AssigneeName,a.AssigneePhone,a.AssigneeType,a.AssigneeCode,"
				+ " a.AccidentDate,(case when c.RealPay is null then 0 else c.RealPay end),a.MngCom,b.ExamDate,a.EndCaseDate,(case when c.DeclinePay is null then 0 else c.DeclinePay end),(case when c.standpay is null then 0 else c.standpay end),b.AuditConclusion "
				+ " from llregister a,LLClaimUWMain b,LLClaim c where "
				+ tTimeChance
				+ " between '"
				+ "?startdate?"
				+ "' and '"
				+ "?enddate?"
				+ "' "
				+ " and a.RgtNo=c.ClmNo and a.RgtNo=b.ClmNo "
				+ tAuditConclu
				+ applType
				+ " and a.MngCom like concat('"
				+ "?mngcom?"
				+ "','%') "
				+ tClmState + " order by " + tTimeChance + ",a.RgtNo";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("startdate", strStartDate);
		sqlbv.put("enddate", strEndDate);
		sqlbv.put("mngcom", strMngCom);
		logger.debug("开始执行sql操作" + strSql);
		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs = exeSQL.execSQL(sqlbv);

		if (ssrs.getMaxRow() != 0) {
			sumcount = ssrs.getMaxRow();
			for (int index = 1; index <= ssrs.getMaxRow(); index++) {
				String ClmNo = ssrs.GetText(index, 1);
				logger.debug("ClmNo" + ClmNo);

				// 取得出险人、联系电话、案件标识（如出险人死亡需要标识）
				strSql = "select a.CustomerNo,a.DieFlag,b.Phone,c.Name from llcase a,LCAddress b,LDPerson c"
						+ " where a.CaseNo ='"
						+ "?CaseNo?"
						+ "' and  a.CustomerNo=b.CustomerNo and a.CustomerNo=c.CustomerNo "
						+ " order by a.AccDate,b.customerno desc";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSql);
				sqlbv1.put("CaseNo", ClmNo);
				SSRS ssrs1 = exeSQL.execSQL(sqlbv1);
				if (ssrs1.getMaxRow() != 0) {
					for (int i = 1; i <= ssrs1.getMaxRow(); i++) {
						if ((ssrs1.GetText(i, 2)).trim().equals("1"))// 存在死亡标志
						{
							Dieflag = "死亡案件";
						} else {
							Dieflag = "非死亡案件";
						}
					}
					CusNo = ssrs1.GetText(1, 1);// 取得出险人
					CusName = ssrs1.GetText(1, 4);// 取得出险人姓名
					CusPhone = ssrs1.GetText(1, 3);// 取得出险人电话
				}
				// 取得事故日期
				strSql = "select a.AccDate from LLAccident a,LLCaseRela b where a.AccNo=b.CaseRelaNo and b.CaseNo='"
						+ ClmNo + "'";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(strSql);
				sqlbv2.put("CaseNo", ClmNo);
				String AccDate = exeSQL.getOneValue(sqlbv2);
				AccDate = AccDate.substring(0, 10);
				logger.debug("事故日期" + AccDate);
				// 取得受托人区部组（业务员）
				String AgentType = (ssrs.GetText(index, 4)).trim();// 受托人类型
				String AgentCode = (ssrs.GetText(index, 5)).trim();// 业务员编码
				if (AgentType.equals("0")) {
					strSql = " select a.AgentGroup from LAAgent a "
							+ " where a.AgentCode='" + "?AgentCode?" + "'";
					SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
					sqlbv3.sql(strSql);
					sqlbv3.put("AgentCode", AgentCode);
					AgentGroupName = exeSQL.getOneValue(sqlbv3);
				} else {
					AgentGroupName = "";
				}
				// 取得领款人姓名,Added by niuzj 20050912
				LLBnfDB tLLBnfDB = new LLBnfDB();
				tLLBnfDB.setClmNo(ClmNo);
				LLBnfSet tLLBnfSet = new LLBnfSet();
				tLLBnfSet.set(tLLBnfDB.query());
				PayeeName = "";
				for (int k = 1; k <= tLLBnfSet.size(); k++) // 领款人不唯一
				{
					LLBnfSchema tLLBnfSchema = new LLBnfSchema();
					tLLBnfSchema = tLLBnfSet.get(k);
					String tPayeeName = tLLBnfSchema.getPayeeName();
					if (k == 1) {
						PayeeName = tPayeeName;
					} else // 如果领款人不唯一
					{
						PayeeName = PayeeName + "/" + tPayeeName;
					}
				}
				// 机构代码
				String tMngCom = ssrs.GetText(index, 8);
				// 机构名称
				LDComSchema tLDComSchema = new LDComSchema();
				LDComDB tLDComDB = new LDComDB();
				tLDComDB.setComCode(tMngCom);
				tLDComDB.getInfo();
				tLDComSchema = tLDComDB.getSchema();
				String tMngComName = tLDComSchema.getName();
				// 审批时间
				String tExamDate = ssrs.GetText(index, 9);
				// 结案时间
				String tEndCaseDate = ssrs.GetText(index, 10);
				// 拒付金额
				String tDeclinePay = ssrs.GetText(index, 11);
				// 给付保险金
				String tStandPay = ssrs.GetText(index, 12);
				// 应付金额
				String tRealPay = ssrs.GetText(index, 7);
				// 审核结论
				String tAuditConcluCode = ssrs.GetText(index, 13);
				String tAuditConcluName = tAuditConcluCode.equals("0") ? " 给付 "
						: " 拒付 ";
				// 退费
				String strTF = " select (case when sum(llbalance.pay) is null then 0 else sum(llbalance.pay) end) from llbalance, llbalancerela "
						+ " where llbalance.FeeOperationType = llbalancerela.baltype "
						+ " and llbalance.SubFeeOperationType = llbalancerela.subbaltype "
						+ " and llbalance.FeeFinaType = llbalancerela.finatype "
						+ " and llbalance.clmno = '"
						+ "?clmno?"
						+ "'"
						+ " and llbalancerela.finatype = 'TF' ";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(strTF);
				sqlbv4.put("clmno", ClmNo);
				String tTFMoney = exeSQL.getOneValue(sqlbv4);
				// 补费
				String strBF = " select (case when sum(llbalance.pay) is null then 0 else sum(llbalance.pay) end) from llbalance, llbalancerela "
						+ " where llbalance.FeeOperationType = llbalancerela.baltype "
						+ " and llbalance.SubFeeOperationType = llbalancerela.subbaltype "
						+ " and llbalance.FeeFinaType = llbalancerela.finatype "
						+ " and llbalance.clmno = '"
						+ "?clmno?"
						+ "'"
						+ " and llbalancerela.finatype = 'BF' ";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(strBF);
				sqlbv5.put("clmno", ClmNo);
				String tBFMoney = exeSQL.getOneValue(sqlbv5);
				// 数据存储
				String[] cols = new String[20];
				cols[0] = ClmNo; // 赔案号
				cols[1] = CusName; // 出险人
				cols[2] = AccDate; // 事故日期
				cols[3] = ssrs.GetText(index, 6); // 出险日期
				cols[4] = CusPhone; // 出险人联系电话
				cols[5] = ssrs.GetText(index, 2); // 受托人
				cols[6] = ssrs.GetText(index, 3); // 受托人联系电话
				cols[7] = AgentGroupName; // 受托人区部组代码
				cols[8] = Dieflag; // 案件标识
				cols[9] = PayeeName; // 领款人姓名
				cols[10] = tAuditConcluName; // 审核结论
				cols[11] = tStandPay; // 给付保险金
				cols[12] = tDeclinePay; // 拒付保险金
				cols[13] = tBFMoney; // 补费
				cols[14] = tTFMoney; // 退费
				cols[15] = tRealPay; // 应付金额
				cols[16] = tExamDate; // 审批通过日期
				cols[17] = tEndCaseDate; // 结案日期
				cols[18] = tMngCom; // 机构代码
				cols[19] = tMngComName; // 机构名称

				double realpay = Double.parseDouble(tRealPay);
				sumMoney1 = sumMoney1 + realpay;// 总应付金额
				double StandPay = Double.parseDouble(tStandPay);// 总给付保险金
				sumMoney2 = sumMoney2 + StandPay;
				double DeclinePay = Double.parseDouble(tDeclinePay);// 总拒付保险金
				sumMoney3 = sumMoney3 + DeclinePay;
				tlistTable.add(cols);
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLPRBEndCaseBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		String[] col = new String[20];
		col[0] = "";
		col[1] = ""; // 出险人
		col[2] = "";
		col[3] = "";
		col[4] = "";
		col[5] = "";
		col[6] = "";
		col[7] = "";
		col[8] = "";
		col[9] = "";
		col[10] = "";

		xmlexport.addListTable(tlistTable, col);
		// 统计条件,取得机构名称
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(strMngCom);
		if (tLDComDB.getInfo()) {
			ComName = tLDComDB.getName();
		}
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 "; // 申请类型
		String StatCondition = "统计机构：" + ComName + "/起始日期：" + strStartDate
				+ "至" + strEndDate + "/申请类型：" + tApplTypeName;
		texttag.add("StatCondition", StatCondition);
		// 统计日期
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		texttag.add("StatDate", SysDate);
		// 统计口径:$=/StatAround$
		String tTimeChanceName = strStatAround.equals("01") ? "审批通过时间" : "结案时间";// 判断统计口径
		texttag.add("StatAround", tTimeChanceName);
		// 审核结论:$=/ClaimAuditCon$
		String tClaimAuditCon = "";
		int a = 0;
		a = Integer.parseInt(strAuditConclu);
		switch (a) {
		case 1:
			tClaimAuditCon = "给付或部分给付";
			break;
		case 2:
			tClaimAuditCon = "整案拒付";
			break;
		case 3:
			tClaimAuditCon = "给付或部分给付和整案拒付";
			break;
		}
		texttag.add("ClaimAuditCon", tClaimAuditCon);
		// 统计数量
		texttag.add("StatAmount", sumcount);
		// 总应付金额：$=/StatMoney$
		texttag.add("StatMoney", new DecimalFormat("0.00").format(sumMoney1));
		// 总给付保险金$=/ECStandPay$
		texttag.add("ECStandPay", new DecimalFormat("0.00").format(sumMoney2));
		// 总拒付保险金$=/ECDeclinePay$
		texttag
				.add("ECDeclinePay", new DecimalFormat("0.00")
						.format(sumMoney3));

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
		tTransferData.setNameAndValue("strStartDate", "2005-11-01");
		tTransferData.setNameAndValue("strEndDate", "2005-11-04");
		tTransferData.setNameAndValue("strStatAround", "02");
		tTransferData.setNameAndValue("strAuditConclu", "03");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRBEndCaseBL tLLPRBEndCaseBL = new LLPRBEndCaseBL();
		if (tLLPRBEndCaseBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}
		int n = tLLPRBEndCaseBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRBEndCaseBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
