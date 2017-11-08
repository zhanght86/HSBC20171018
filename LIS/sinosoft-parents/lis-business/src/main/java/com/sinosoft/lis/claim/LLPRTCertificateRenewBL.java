package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LLAffixDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLClaimUserSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LLSubReportSchema;
import com.sinosoft.lis.vschema.LLAffixSet;
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
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 单证打印：理赔单证补充通知书--PCT003,LpdzbctzsC000120.vts
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
public class LLPRTCertificateRenewBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRTCertificateRenewBL.class);

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
	private String mPrtSeq = ""; // 流水号

	public LLPRTCertificateRenewBL() {
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.ManageCom = "86";
		tG.Operator = "001";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("PrtSeq", "0000002835");

		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRTCertificateRenewBL tLLPRTCertificateRenewBL = new LLPRTCertificateRenewBL();
		if (tLLPRTCertificateRenewBL.submitData(tVData, "") == false) {
			logger.debug("#######----单证补充通知书打印出错----##############");
		}

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------补充单证入口-----LLPRTCertificateRenew测试-----开始----------");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

//		if (!dealData()) {
//			return false;
//		}
		if(!getPrintData())
		{
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}
//
//		if (!pubSubmit()) {
//			return false;
//		}

		logger.debug("----------补充单证入口-----LLPRTCertificateRenew测试-----结束----------");
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
		// this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); //赔案号
		// this.mCusNo = (String) mTransferData.getValueByName("CustNo");
		// //出险人客户号
		this.mPrtSeq = (String) mTransferData.getValueByName("PrtSeq");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private boolean dealData() {
		String mClmNoSQL = "select OtherNo from LOPRTManager where PrtSeq='"
				+ "?PrtSeq?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(mClmNoSQL);
		sqlbv.put("PrtSeq", mPrtSeq);
		ExeSQL clmExeSQL = new ExeSQL();
		mClmNo = clmExeSQL.getOneValue(sqlbv);
		String mCusNoSQL = "select CustomerNo from LLcase where CaseNo='"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(mCusNoSQL);
		sqlbv1.put("clmno", mClmNo);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS cusNoSSRS = new SSRS();
		cusNoSSRS = cusExeSQL.execSQL(sqlbv1);
		// for(int i=1;i<=cusNoSSRS.getMaxRow();i++){}
		mCusNo = cusNoSSRS.GetText(1, 1);
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义打印
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("LpdzbctzsC000120.vts", "");
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		// 理赔类型---------------------------------------------------------------
		String ClaimType = tLLPRTPubFunBL.getSLLReportReason(mClmNo, mCusNo);
		// 出险人姓名--------------------------------------------------------------
		String nameSql = "select a.name from ldperson a where "
				+ "a.customerno = '" + "?cusno?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(nameSql);
		sqlbv2.put("cusno", mCusNo);
		ExeSQL tExeSQL = new ExeSQL();
		String tName = tExeSQL.getOneValue(sqlbv2);
		// 出险原因---------------------------------------------------------------
		String reason = tLLPRTPubFunBL.getLLReport(mClmNo).getAccidentReason();
		String tSQL = "select codename from ldcode where code='" + "?reason?"
				+ "' and codetype='lloccurreason'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSQL);
		sqlbv3.put("reason", reason);
		ExeSQL tExeSQL1 = new ExeSQL();
		String claimRsn = tExeSQL1.getOneValue(sqlbv3);

		LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
		tLLAccidentSchema = tLLPRTPubFunBL.getLLAccident(mClmNo);
		// 出险时间---------------------------------------------------------------
		String claimTime = "";
		claimTime = tLLAccidentSchema.getAccDate();

		// 收信人(申请人)姓名Name、性别、管理机构 根据赔案号（mClmNo）从立案申请登记(LLRegister)中查
		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
		tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(mClmNo);
		String rgtname = "";
		String mngCom = ""; // 管理机构编码
		rgtname = tLLRegisterSchema.getRgtantName();
		// String sexFlag = "" ;
		String sex = "";
		String sexFlag = tLLRegisterSchema.getRgtantSex();
		if (sexFlag == null) {
			sexFlag = "";
		}
		// 用性别判断称谓
		if (sexFlag.equals("0")) {
			sex = "先生";
		} else if (sexFlag.equals("1")) {
			sex = "女士";
		} else {
			sex = "";
		}
		mngCom = tLLRegisterSchema.getMngCom();
		String comSQL = "select a.name,a.phone from LDCom a where ComCode='"
				+ "?mngcom?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(comSQL);
		sqlbv4.put("mngcom", mngCom);
		ExeSQL comExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = comExeSQL.execSQL(sqlbv4);
		String company = tSSRS.GetText(1, 1);// 根据管理机构代码查出名称
		// String tel2 = tSSRS.GetText(1,2) ;

		// 保单服务人员姓名、业务号和电话---------------------------------------------
		// tLAAgentSchema = tLLPRTPubFunBL.getLAAgent("01",mCusNo);

		String tAgentCodeSql = "select AgentCode from lccont where 1=1 "
				+ " and insuredno='" + "?cusno?" + "' order by CValiDate desc";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tAgentCodeSql);
		sqlbv5.put("cusno", mCusNo);
		ExeSQL tAgentCodeExeSQL = new ExeSQL();
		String tAgentCode = tAgentCodeExeSQL.getOneValue(sqlbv5);
		String SeveiceName = "";
		String transNo = "";
		String tel = "";
		if (tAgentCode.equals("") || tAgentCode == null) {
			SeveiceName = "";
			transNo = "";
			tel = "";
		} else {
			LAAgentSchema tLAAgentSchema = new LAAgentSchema();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tAgentCode);
			tLAAgentDB.getInfo();
			tLAAgentSchema.setSchema(tLAAgentDB.getSchema());
			SeveiceName = tLAAgentSchema.getName();
			transNo = tLAAgentSchema.getAgentCode();
			tel = tLAAgentSchema.getPhone();
		}
		// 经办人-----------------------------------------------------------------
		String operator = "";
		LLSubReportSchema ttLLSubReportSchema = new LLSubReportSchema();
		ttLLSubReportSchema = tLLPRTPubFunBL.getLLSubReport(mClmNo, mCusNo);
		operator = ttLLSubReportSchema.getOperator();
		String checkerSQL = "select UserName from llclaimuser "
				+ "where usercode='" + "?operator?" + "'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(checkerSQL);
		sqlbv6.put("operator", operator);
		ExeSQL cExeSQL = new ExeSQL();
		String jingbanren = cExeSQL.getOneValue(sqlbv6);
		LLClaimUserSchema tLLClaimUserSchema = new LLClaimUserSchema();
		tLLClaimUserSchema = tLLPRTPubFunBL.getLLClaimUser(operator);
		String tel2 = tLLClaimUserSchema.getRelPhone(); // 经办人电话

		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		tTextTag.add("ClaimNo", mClmNo);// 陪案号
		tTextTag.add("ClaimType", ClaimType);// 理赔类型
		tTextTag.add("ClaimRsn", claimRsn);// 出险原因
		tTextTag.add("AccName", tName);// 出险人
		tTextTag.add("ClaimTime", claimTime);// 出险时间
		tTextTag.add("ClaimName", rgtname);// 申请人姓名
		tTextTag.add("Sex", sex);// 申请人称谓
		tTextTag.add("ServiceName", SeveiceName);// 保单服务人员
		tTextTag.add("TransNo", transNo);// 业务号
		tTextTag.add("Tel", tel);// 联系电话
		tTextTag.add("LPAppnt.Operator", jingbanren);// 经办人operator
		tTextTag.add("SysDate", SysDate);// 通知日期
		// tTextTag.add("ManageComName",company);//
		tTextTag.add("Tel2", tel2);// 联系电话
		ListTable tListTable = new ListTable();
		tListTable.setName("NT");
		String[] Title = new String[1];
		Title[0] = "";

		// 理赔补充单证 "select * from Llaffix where AffixConclusion is null"
		LLAffixDB tLLAffixDB = new LLAffixDB();
		String affixSQL = "select * from Llaffix where AffixConclusion is null and rgtno='"
				+ "?clmno?" + "' and customerno='" + "?cusno?" + "'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(affixSQL);
		sqlbv7.put("clmno", mClmNo);
		sqlbv7.put("cusno", mCusNo);
		LLAffixSet tLLAffixSet = tLLAffixDB.executeQuery(sqlbv7);

		for (int i = 1; i <= tLLAffixSet.size(); i++) {
			String[] stra = new String[1];
			stra[0] = i + "." + tLLAffixSet.get(i).getAffixName();
			tListTable.add(stra);
			logger.debug("i" + i);
		}

		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		tXmlExport.addListTable(tListTable, Title);
		tXmlExport.outputDocumentToFile("c:\\", "testHZM"); // 输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);
		
//	    String updateStateSql="update loprtmanager set stateflag='1' where otherno='"+mClmNo+"' and code='PCT003'";
//	    logger.debug("更新打印管理表的sql:"+updateStateSql);
//	    mMMap.put(updateStateSql, "UPDATE");

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unused")
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
	@SuppressWarnings("unchecked")
	private boolean getPrintData()
	{
		String mClmNoSQL = "select OtherNo from LOPRTManager where PrtSeq='"+ "?PrtSeq?" + "'";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(mClmNoSQL);
		sqlbv8.put("PrtSeq", mPrtSeq);
		ExeSQL clmExeSQL = new ExeSQL();
		mClmNo = clmExeSQL.getOneValue(sqlbv8);
		String mCusNoSQL = "select CustomerNo from LLcase where CaseNo='"+ "?clmno?" + "'";//个险一个案件只有一个出险人
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(mCusNoSQL);
		sqlbv9.put("clmno", mClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		mCusNo = tExeSQL.getOneValue(sqlbv9);
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义打印
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew tXmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
//		tXmlExport.createDocument("LpdzbctzsC000120.vts", "");
		tXmlExport.createDocument("单证补充通知单[问题件]", "", "", "0");
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		// 理赔类型---------------------------------------------------------------
//		String ClaimType = tLLPRTPubFunBL.getSLLReportReason(mClmNo, mCusNo);
		String ClaimType = tLLPRTPubFunBL.getClaimTypeReason(mClmNo, mCusNo);
		// 出险人姓名--------------------------------------------------------------
		String nameSql = "select a.name from ldperson a where a.customerno = '" + "?cusno?" + "'";
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(nameSql);
		sqlbv10.put("cusno", mCusNo);
		String tName = tExeSQL.getOneValue(sqlbv10);

		LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
		tLLAccidentSchema = tLLPRTPubFunBL.getLLAccident(mClmNo);
		// 出险时间---------------------------------------------------------------
		String claimTime =  tLLAccidentSchema.getAccDate();
	

		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
		tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(mClmNo);
		// 出险原因---------------------------------------------------------------

		String tSQL = "select codename from ldcode where code='"+"?reason?"+"' and codetype='lloccurreason'";
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(tSQL);
		sqlbv11.put("reason", tLLRegisterSchema.getAccidentReason());
		String claimRsn = tExeSQL.getOneValue(sqlbv11);
		// 收信人(申请人)姓名Name、性别、管理机构 根据赔案号（mClmNo）从立案申请登记(LLRegister)中查rgtantname,postcode,rgtantaddress,accidentdate
		String tRgtantName=tLLRegisterSchema.getRgtantName();   //申请人姓名
		String tRgtantAddress=tLLRegisterSchema.getRgtantAddress();// 申请人地址
		String tPostCode=tLLRegisterSchema.getPostCode();    // 申请人邮编
		

	
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		tTextTag.add("ClaimNo", mClmNo);// 赔案号
		tTextTag.add("ClaimType", ClaimType);// 理赔类型
		tTextTag.add("ClaimRsn", claimRsn);// 出险原因
		tTextTag.add("AccName", tName);// 出险人
		tTextTag.add("ClaimTime", claimTime);// 出险时间
		tTextTag.add("RptorName", tRgtantName);// 收件人
		tTextTag.add("RptorZip", tPostCode);// 邮编
		tTextTag.add("RptorAddress", tRgtantAddress);// 邮寄地址
		tTextTag.add("ClaimName", tName);// 申请人姓名



		ListTable tListTable = new ListTable();
		tListTable.setName("NT");
		String[] Title = new String[1];
		Title[0] = "";
	
		// 理赔补充单证 "select * from Llaffix where AffixConclusion is null"
		LLAffixDB tLLAffixDB = new LLAffixDB();
		String affixSQL = "select * from Llaffix where AffixConclusion is null and rgtno='"
			            + "?clmno?" + "' and (subflag is null or subflag='1') and AffixState='01'";
		if((!"".equals(mCusNo)) && (mCusNo!= null))
		{
			affixSQL = affixSQL + " and customerno='"+"?cusno?" +"'";
		}
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(affixSQL);
		sqlbv12.put("clmno", mClmNo);
		sqlbv12.put("cusno", mCusNo);
		LLAffixSet tLLAffixSet = tLLAffixDB.executeQuery(sqlbv12);
		 String mOperator = "";
		if(tLLAffixSet.size()>0)
		{
			// 经办人-----------------------------------------------------------------
			String checkerSQL = "select UserName from llclaimuser where usercode='" + "?usercode?" + "'";
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(checkerSQL);
			sqlbv13.put("usercode", tLLAffixSet.get(1).getOperator());
			mOperator = tExeSQL.getOneValue(sqlbv13);
			for (int i = 1; i <= tLLAffixSet.size(); i++) {
				String[] stra = new String[1];
				stra[0] = i + "." + tLLAffixSet.get(i).getAffixName();
				tListTable.add(stra);
				logger.debug("i" + i);
			}
		}
		tTextTag.add("Operator", mOperator);// 经办人operator
		tTextTag.add("SysDate", SysDate);// 通知日期
	
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}
	
		tXmlExport.addListTable(tListTable, Title);

//		tXmlExport.outputDocumentToFile("c:\\", "testHZM"); // 输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);
	
		String updateStateSql = "update loprtmanager set stateflag='1' where otherno='"
				+ "?clmno?" + "' and code='PCT003' And stateflag= '3'";
		logger.debug("更新打印管理表的sql:" + updateStateSql);
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(updateStateSql);
		sqlbv14.put("clmno", mClmNo);
		mMMap.put(sqlbv14, "UPDATE");
		
		String updateAffixState ="Update llaffix Set AffixState='02' Where rgtno='"
				+ "?clmno?" + "' and AffixState='01' and exists (select 1 from LLRegisterIssue where rgtno=llaffix.rgtno and IssueConclusion='02' and IssueReplyDate is null)";
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.sql(updateAffixState);
		sqlbv15.put("clmno", mClmNo);
		mMMap.put(sqlbv15, "UPDATE");
		mResult.add(mMMap);

		return true;
	
	}
	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

}
