package com.sinosoft.lis.claim;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LLClaimUWMainDB;
import com.sinosoft.lis.db.LLInqCertificateDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LLAffixSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.vschema.LLInqCertificateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 单证打印：理赔案件审核报告 vtAudiReportC000200.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLPRTClaimAuditiReportBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRTClaimAuditiReportBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */

	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();

	private String mClmNo = ""; // 赔案号
	private String mCusNo = ""; // 客户号

	public LLPRTClaimAuditiReportBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------批单-理赔给付批注-----LLPRTClaimAuditiReportBL测试-----开始----------");

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

		if (!pubSubmit()) {
			return false;
		}

		logger.debug("----------批单-理赔给付批注-----LLPRTClaimAuditiReportBL测试-----结束----------");
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

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		this.mCusNo = (String) mTransferData.getValueByName("CustNo"); // 出险人客户号
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义打印
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew tXmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
//		tXmlExport.createDocument("AudiReportC000200.vts", "");
		tXmlExport.createDocument("赔案审核报告");
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

		// 理赔类型---------------------------------------------------------------
		String ClaimType = tLLPRTPubFunBL.getSLLReportReason(mClmNo, mCusNo);

		// 出险人姓名。身份暂时不做--------------------------------------------------
		String insuredSql = "select * from ldperson a where "
				+ "a.customerno = '" + "?cusno?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(insuredSql);
		sqlbv.put("cusno", mCusNo);
		LDPersonDB tLDPersonDB = new LDPersonDB();
		LDPersonSet tLDPersonSet = tLDPersonDB.executeQuery(sqlbv);
		String tName = tLDPersonSet.get(1).getName();
		// 年龄 llcase中的customerage --------------------------------------------
		String ageSQL = "select customerage from llcase where caseno='"
				+ "?clmno?" + "'" + "and customerno='" + "?cusno?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(ageSQL);
		sqlbv1.put("clmno", mClmNo);
		sqlbv1.put("cusno", mCusNo);
		ExeSQL ageExeSQL = new ExeSQL();
		String age = ageExeSQL.getOneValue(sqlbv1);

		// 系统时间---------------------------------------------------------------
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		// ----------------------------------------------------------------------
		tTextTag.add("LLClaim.ClmNo", mClmNo); // 赔案号
		tTextTag.add("SysDate", SysDate); // 日期
		tTextTag.add("LLCase.CustomerName", tName); // 出险人
		tTextTag.add("ClmType", ClaimType); // 理赔类型
		tTextTag.add("LLCase.CustomerAge", age); // 出险人年龄
		// 预估金额
		String standSQL = "select (case when StandPay is null then 0 else StandPay end) from llclaim where clmno='"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(standSQL);
		sqlbv2.put("clmno", mClmNo);
		ExeSQL standExeSQL = new ExeSQL();
		String standPay = standExeSQL.getOneValue(sqlbv2);// Modify by
		// zhaorx
		// 2006-06-22
		tTextTag.add("LLClaim.StandPay", new DecimalFormat("0.00")
				.format(Double.parseDouble(standPay)));
		// 审核结论---------------------------------------------------------------
		String audiSQL = "select codename from ldcode where codetype='llclaimconclusion'"
				+ " and code in (select auditConclusion from llclaimuwmain where "
				+ " clmno = '" + "?clmno?" + "')";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(audiSQL);
		sqlbv3.put("clmno", mClmNo);
		ExeSQL audiExeSQL = new ExeSQL();
		String auditConclusion = audiExeSQL.getOneValue(sqlbv3);
		tTextTag.add("LLClaimUWMain.AuditConclusion", auditConclusion);
		// 出险情况---------------------------------------------------------------
		String caseSQL = "select AccdentDesc from llcase where caseno='"
				+ "?clmno?" + "'" + " and CustomerNo='" + "?cusmo?" + "'";
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(caseSQL);
		sqlbv12.put("clmno", mClmNo);
		sqlbv12.put("cusmo", mCusNo);
		ExeSQL caseExeSQL = new ExeSQL();
		String accdentDesc = caseExeSQL.getOneValue(sqlbv12);
		tTextTag.add("RiskCase", accdentDesc);
		// 责任认定---------------------------------------------------------------
		String auditIdeaSQL = "select AuditIdea from LLClaimUWMain where clmno='"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(auditIdeaSQL);
		sqlbv4.put("clmno", mClmNo);
		ExeSQL auditIdeaExeSQL = new ExeSQL();
		String auditIdea = auditIdeaExeSQL.getOneValue(sqlbv4);
		tTextTag.add("Duty", auditIdea);
		// 审核结论---------------------------------------------------------------
		LLClaimUWMainDB tLLClaimUWMainDB = new LLClaimUWMainDB();
		tLLClaimUWMainDB.setClmNo(mClmNo);
		if (tLLClaimUWMainDB.getInfo()) {
			LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
			tLLClaimUWMainSchema.setSchema(tLLClaimUWMainDB.getSchema());

			String tAuditPer = tLLClaimUWMainSchema.getAuditPer();
			String tSql = "select username from LLClaimUser where "
					+ "usercode = '" + "?usercode?" + "'";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSql);
			sqlbv5.put("usercode", tAuditPer);
			ExeSQL tExeSQL = new ExeSQL();
			tAuditPer = tExeSQL.getOneValue(sqlbv5);

			String tAuditDate = tLLClaimUWMainSchema.getAuditDate();
			FDate mFDate = new FDate();
			GregorianCalendar mCalendar = new GregorianCalendar();
			mCalendar.setTime(mFDate.getDate(tAuditDate));
			String year = String.valueOf(mCalendar.get(Calendar.YEAR));
			String month = String.valueOf(mCalendar.get(Calendar.MONTH) + 1); // 月份加1,否则不一致
			String day = String.valueOf(mCalendar.get(Calendar.DATE));
			tAuditDate = year + "年" + month + "月" + day + "日";

			tTextTag.add("LLClaimUWMain.AuditIdea", tLLClaimUWMainSchema
					.getAuditIdea());
			tTextTag.add("LLClaimUWMain.AuditPer", tAuditPer);
			tTextTag.add("AuditDate", tAuditDate);
		}

		// 首先查出LLClaimDetail表中指定赔案号下的不同的保单号(合同号)的数量作为最外层循环---
		String contSQL = "";
		contSQL = "select * from lccont where contno in (select distinct contno from llclaimdetail where clmno='"
				+ "?clmno?" + "' and givetype='0')";
		// logger.debug(contSQL);
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(contSQL);
		sqlbv6.put("clmno", mClmNo);
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = tLCContDB.executeQuery(sqlbv6);

		ListTable tListTable = new ListTable();
		tListTable.setName("CONT");

		String[] Title = new String[2];
		Title[0] = "";
		Title[1] = "";

		for (int i = 1; i <= tLCContSet.size(); i++) {
			// 取得保单号(合同号)---------------------------------------------------
			String contNo = tLCContSet.get(i).getContNo();

			String[] contStr = new String[2];
			contStr[0] = "合同号: " + contNo;// 页面上显示为保单号
			contStr[1] = "投保日期: " + tLCContSet.get(i).getSignDate();
			tListTable.add(contStr);

			// 查出保项数目--------------------------------------------------------
			String bxSQL = "";
			bxSQL = "select * from LLClaimDetail where clmno='" + "?clmno?"
					+ "' and contno='" + "?contno?" + "'";
			logger.debug(bxSQL);
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(bxSQL);
			sqlbv7.put("clmno", mClmNo);
			sqlbv7.put("contno", contNo);
			LLClaimDetailDB bxLLClaimDetailDB = new LLClaimDetailDB();
			LLClaimDetailSet bxLLClaimDetailSet = bxLLClaimDetailDB
					.executeQuery(sqlbv7);
			for (int j = 1; j <= bxLLClaimDetailSet.size(); j++)// 内层循环保项的数目
			{
				// 有效保额-------------------------------------------------------
				String feeSQL = "";
				feeSQL = "select * from lcget where polno='"
						+ "?polno?" + "'"
						+ " and getdutycode='"
						+ "?getdutycode?" + "'"
						+ " and dutycode='"
						+ "?dutycode?" + "'";
				// logger.debug(feeSQL);
				SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
				sqlbv8.sql(feeSQL);
				sqlbv8.put("polno", bxLLClaimDetailSet.get(j).getPolNo());
				sqlbv8.put("getdutycode", bxLLClaimDetailSet.get(j).getGetDutyCode());
				sqlbv8.put("dutycode", bxLLClaimDetailSet.get(j).getDutyCode());
				LCGetDB tLCGetDB = new LCGetDB();
				LCGetSet tLCGetSet = tLCGetDB.executeQuery(sqlbv8);
				double fee = 0;
				fee = tLCGetSet.get(1).getStandMoney()
						- tLCGetSet.get(1).getSumMoney();

				// 根据llclaimdetail中的 给付责任类型和给付责任编码 从LMDutyGetClm中 查询给付责任名称 即
				// 保项名称
				String bxNameSQL = "select GetDutyName from lmdutygetclm where getdutycode='"
						+ "?getdutycode?"
						+ "'"
						+ " and getdutykind='"
						+ "?getdutykind?" + "'";
				SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
				sqlbv9.sql(bxNameSQL);
				sqlbv9.put("getdutykind", bxLLClaimDetailSet.get(j).getGetDutyKind());
				sqlbv9.put("getdutycode",bxLLClaimDetailSet.get(j).getGetDutyCode());
				ExeSQL bxnameExeSQL = new ExeSQL();
				String bxName = bxnameExeSQL.getOneValue(sqlbv9);

				String[] stra = new String[2];
				stra[0] = "出险保项: " + bxName;// 保项 Modify by zhaorx 2006-06-22
				stra[1] = "有效保额: " + new DecimalFormat("0.00").format(fee);// lcget表中standmoney-summoney
				tListTable.add(stra);// 将保项信息加入ListTable
			}

		}
		// 理赔调查取证材料---------------------------------------------------------
		String inqSQL = "select * from LLInqCertificate where clmno='" + "?clmno?"
				+ "'";
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(inqSQL);
		sqlbv10.put("clmno", mClmNo);
		LLInqCertificateDB tLLInqCertificateDB = new LLInqCertificateDB();
		LLInqCertificateSet tLLInqCertificateSet = tLLInqCertificateDB
				.executeQuery(sqlbv10);
		// 客户提交材料------------------------------------------------------------
		LLAffixSet tLLAffixSet = tLLPRTPubFunBL.getLLAffix(mClmNo, mCusNo);
		ListTable inqListTable = new ListTable();
		inqListTable.setName("RESEARCH");
		// for(int i=1;i<=tLLInqCertificateSet.size();i++)
		// {
		// String[] inqStr = new String[2] ;
		// inqStr[0] = i+". "+tLLInqCertificateSet.get(i).getCerName();
		// inqStr[1] = String.valueOf(tLLInqCertificateSet.get(i).getCerCount())+"页" ;
		// inqListTable.add(inqStr);
		// }
		//
		// //客户提交材料------------------------------------------------------------
		// LLAffixSet tLLAffixSet = tLLPRTPubFunBL.getLLAffix(mClmNo,mCusNo);
		// ListTable affListTable = new ListTable();
		// affListTable.setName("PROVE");
		// for(int i=1;i<=tLLAffixSet.size();i++)
		// {
		// String[] affix = new String[2];
		// affix[0] = i+". "+tLLAffixSet.get(i).getAffixName();
		// affix[1] = String.valueOf(tLLAffixSet.get(i).getReadyCount())+ "页";
		// affListTable.add(affix);
		// }

		// Modify by zhaorx 2006-04-20无人争晓渡，残月下寒沙。
		int lli = tLLInqCertificateSet.size();
		int lla = tLLAffixSet.size();
		if (lli >= lla) {
			for (int i = 1; i <= lli; i++) {
				String[] inqStr = new String[4];
				inqStr[0] = i + ". " + tLLInqCertificateSet.get(i).getCerName();
				inqStr[1] = String.valueOf(tLLInqCertificateSet.get(i)
						.getCerCount())
						+ "页";
				if (i <= lla) {
					inqStr[2] = i + ". " + tLLAffixSet.get(i).getAffixName();
					inqStr[3] = String.valueOf(tLLAffixSet.get(i)
							.getReadyCount())
							+ "页";
				} else {
					inqStr[2] = " ";
					inqStr[3] = " ";
				}
				inqListTable.add(inqStr);
			}
		} else {
			for (int i = 1; i <= lla; i++) {
				String[] inqStr = new String[4];
				if (i <= lli) {
					inqStr[0] = i + ". "
							+ tLLInqCertificateSet.get(i).getCerName();
					inqStr[1] = String.valueOf(tLLInqCertificateSet.get(i)
							.getCerCount())
							+ "页";
				} else {
					inqStr[0] = " ";
					inqStr[1] = " ";
				}
				inqStr[2] = i + ". " + tLLAffixSet.get(i).getAffixName();
				inqStr[3] = String.valueOf(tLLAffixSet.get(i).getReadyCount())
						+ "页";
				inqListTable.add(inqStr);
			}
		}
		// ---------------------------------------------------------------------
		tXmlExport.addListTable(tListTable, Title);
		tXmlExport.addListTable(inqListTable, Title);
		// tXmlExport.addListTable(affListTable,Title);
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 加入单个字段的打印变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		logger.debug("xmlexport=" + tXmlExport);
		
	    String updateStateSql="update loprtmanager set stateflag='1' where otherno='"+"?clmno?"+"' and code='PCT019'";
	    logger.debug("更新打印管理表的sql:"+updateStateSql);
	    SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(updateStateSql);
		sqlbv11.put("clmno", mClmNo);
	    mMMap.put(sqlbv11, "UPDATE");

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
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalCheckBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			//
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

}
