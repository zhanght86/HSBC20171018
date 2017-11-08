package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LLAccidentDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LLInqApplyDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.vschema.LLRegisterSet;
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
 * Title:
 * </p>
 * <p>
 * Description: 单证打印：PCT018-----调查任务通知书------InquiryNoticeC000220.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author yuejw,2005-08-11
 * @version 1.0
 */

public class LLPRTInteInqTaskBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRTInteInqTaskBL.class);

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

	private String mPrtCode = "PCT017";// 打印编码
	private String mClmNo = ""; // 赔案号
	private String mCusNo = ""; // 客户号
	private String mInqNo = ""; // 调查序号

	public LLPRTInteInqTaskBL() {
	}

	public static void main(String[] args) {

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------单证打印：调查任务通知书-----LLPRTInteInqTaskBL测试-----开始----------");

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

		logger.debug("----------单证打印：调查任务通知书------LLPRTInteInqTaskBL测试-----结束----------");
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
		mInputData = cInputData;

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		mCusNo = (String) mTransferData.getValueByName("CustNo"); // 出险人客户号
		mInqNo = (String) mTransferData.getValueByName("InqNo"); // 出险人客户号
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		/** ----------------- 赔案号 、出险人客户号、调查序号 由外部传入------------- */
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew tXmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
//		tXmlExport.createDocument("InquiryNoticeC000220.vts", "");
		tXmlExport.createDocument("调查任务通知书", "", "", "0");
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		// 赔案号-----传入
		// 理赔类型----ClaimType
		String tClaimType = tLLPRTPubFunBL.getSLLReportReason(mClmNo, mCusNo);
		// 出险人姓名----tName
		String nameSql = "select a.name from ldperson a where "
				+ "a.customerno = '" + "?cusno?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(nameSql);
		sqlbv.put("cusno", mCusNo);
		ExeSQL tExeSQL = new ExeSQL();
		String tName = tExeSQL.getOneValue(sqlbv);
		// 出险时间

		// 系统时间-----SysDate
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		// #############################################################################
		// 查询调查申请表-----查询调查原因,调查项目,备注,是否异地调查,调查机构
		LLInqApplyDB tLLInqApplyDB = new LLInqApplyDB();
		tLLInqApplyDB.setClmNo(mClmNo);
		tLLInqApplyDB.setInqNo(mInqNo);
		tLLInqApplyDB.getInfo();

		// 查询调查原因
		String tllinqreasonSql = "select trim(CodeName)from ldcode where 1 = 1 and codetype = 'llinqreason' and code='"
				+ "?code?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tllinqreasonSql);
		sqlbv1.put("code", tLLInqApplyDB.getInqRCode());
		ExeSQL ttExeSQL = new ExeSQL();
		String tllinqreason = ttExeSQL.getOneValue(sqlbv1);
		// 调查项目
		String tInqItem = tLLInqApplyDB.getInqItem();
		// 备注
		String tRemark = tLLInqApplyDB.getRemark();
		if (tRemark == null) {
			tRemark = "";
		}
		// 查询机构名称
		String mngCom = tLLInqApplyDB.getInqDept();
		String tManageComNameSql = "select name from LDCom where ComCode='"
				+ "?mngcom?" + "'";
		// String tManageComNameSql="select codename from ldcode where
		// codetype='station' and code='"+mngCom+"'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tManageComNameSql);
		sqlbv2.put("mngcom", mngCom);
		ExeSQL comExeSQL = new ExeSQL();
		String tManageComName = comExeSQL.getOneValue(sqlbv2);

		// 是否异地调查-----0---本地（否）；1----异地（是）
		String LocFlag = tLLInqApplyDB.getLocFlag();
		if (LocFlag == null) {
			LocFlag = "";
		}
		String tLocFlag = "";
		if (LocFlag.equals("0")) {
			tLocFlag = "否";
		}
		if (LocFlag.equals("1")) {
			tLocFlag = "是";
		}
		// #############################################################################
		// 查询 立案分案---LLCase，查询 出险时间，申请人陈述出险情况（事故描述）
		// LLCaseDB tLLCaseDB= new LLCaseDB();
		// tLLCaseDB.setCaseNo(mClmNo);
		// tLLCaseDB.setCustomerNo(mCusNo);
		// tLLCaseDB.getInfo();
		// 查询事件号-----用“赔案号”查询 分案事件关联---LLCaseRela
		String tAccNoSql = "select CaseRelaNo from LLCaseRela where CaseNo='"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tAccNoSql);
		sqlbv3.put("clmno", mClmNo);
		ExeSQL tAccNoExeSQL = new ExeSQL();
		String tAccNo = tAccNoExeSQL.getOneValue(sqlbv3);
		// 查询 事件表（LLAccident）----查询 出险时间，申请人陈述出险情况（事故描述）
//		String tAccidentSql = "select *  from LLAccident where accno ='"
//				+ tAccNo + "'";
		LLAccidentDB tLLAccidentDB = new LLAccidentDB();
		tLLAccidentDB.setAccNo(tAccNo);
		tLLAccidentDB.getInfo();

		String tAccDate = tLLAccidentDB.getAccDate();// 出险时间
		String tAccDesc = tLLAccidentDB.getAccDesc();// 申请人陈述出险情况：
		String tApplyPer = tLLInqApplyDB.getApplyPer();// 提调查人：----申请人
		String tApplyDate = tLLInqApplyDB.getApplyDate(); // 提调查时间：----申请日期
		// #############################################################################
		logger.debug("--------以下为单个数据变量赋值--------");
		tTextTag.add("ManageComName", tManageComName); // 机构名称：----调查机构
		tTextTag.add("LLInqApply.LocFlag", tLocFlag); // 是否异地调查：
		tTextTag.add("LLClaim.ClmNo", mClmNo); // 赔案号
		tTextTag.add("LLCase.CustomerName", tName); // 出险人
		tTextTag.add("LLReportReason.ReasonCode", tClaimType);// 理赔类型
		tTextTag.add("LLCase.AccidentDate", tAccDate);// 出险时间
		tTextTag.add("LLCase.AccdentDesc", tAccDesc);// 申请人陈述出险情况：
		tTextTag.add("InqName", tApplyPer); // 提调查人：----申请人
		tTextTag.add("SysDate", tApplyDate); // 提调查时间：----申请日期

		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		// #############################################################################
		logger.debug("--------以下为“申请理赔时保单状况”--------");
		// 首先查出LLClaimDetail表中指定赔案号下的不同的保单号(合同号)的数量作为最外层循环
		String contSQL = "select * from lccont t where 1=1 and (t.appntno='"
				+ "?cusno?" + "' or t.insuredno='" + "?cusno?" + "')";
		// contSQL = "select * from lccont where contno in (select distinct contno from
		// llclaimdetail where clmno='"+mClmNo+"')";
		logger.debug("--------保单查询--------" + contSQL);
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(contSQL);
		sqlbv4.put("cusno", mCusNo);
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		tLCContSet.set(tLCContDB.executeQuery(sqlbv4));

		ListTable tListTable_Cont = new ListTable();
		tListTable_Cont.setName("CONT");
		String[] Title_Cont = new String[3];
		Title_Cont[0] = "";
		Title_Cont[1] = "";
		Title_Cont[2] = "";
		for (int i = 1; i <= tLCContSet.size(); i++) {
			// 取得保单号(合同号)
			String tcontNo = tLCContSet.get(i).getContNo();
			// ------------------查询合同险种名称<M--主险(Main) S--附险(Sub)
			// A--两者都可以>------------------------------
			// String triskSql="select riskcode,riskname,subriskflag from lmriskapp "
			String triskSql = "select riskname from lmriskapp where subriskflag='M' "
					// String triskSql="select riskcode from lmriskapp where subriskflag='M' "
					+ " and riskcode in "
					+ " (select riskcode from lcpol where contno='"
					+ "?contno?"
					+ "')";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(triskSql);
			sqlbv5.put("contno", tcontNo);
			ExeSQL tRiskExeSQL = new ExeSQL();
			String triskname = tRiskExeSQL.getOneValue(sqlbv5);
			logger.debug("---------保单号(合同号)---------------" + tcontNo);
			logger.debug("---------险种名称（主险）---------------" + triskname);
			// LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();//<险种承保定义>
			// LMRiskAppSet tLMRiskAppSet = new LMRiskAppSet();
			// tLMRiskAppSet = tLMRiskAppDB.executeQuery(triskSql);
			// if(tLMRiskAppSet.size()>0)
			// {
			// for (int m=1;m<=tLMRiskAppSet.size();m++)
			// {
			// if(tLMRiskAppSet.get(m).getSubRiskFlag().equals("M"))
			// {
			// String triskname=tLMRiskAppSet.get(m).getRiskName();
			// }
			// }
			// }

			// 保单生效日
			String tSql = "select to_char(cvalidate,'yyyy-mm-dd') from lccont where "
					+ " contno = '" + "?contno?" + "'";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tSql);
			sqlbv6.put("contno", tcontNo);
			ExeSQL tDExeSQL = new ExeSQL();
			String tDate = tDExeSQL.getOneValue(sqlbv6);

			// 取得保单号(合同号)
			String[] contStr = new String[3];
			contStr[0] = "合同号:" + tcontNo;
			logger.debug("contStr[0]" + contStr[0]);
			contStr[1] = "险种名称：" + triskname;
			contStr[2] = "生效日期：" + tDate;
			tListTable_Cont.add(contStr);

			// ----------------查询“出险保项”、“有效保额”、“生效日期”；--------------
			// 查出保项数目
			String bxSQL = "select * from LLClaimDetail where clmno='" + mClmNo
					+ "' and contno='" + "?contno?" + "' ";
			logger.debug("########查询--出险保项--有效保额--生效日期--########" + bxSQL);
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(bxSQL);
			sqlbv7.put("contno", tcontNo);
			LLClaimDetailDB bxLLClaimDetailDB = new LLClaimDetailDB();
			LLClaimDetailSet bxLLClaimDetailSet = bxLLClaimDetailDB
					.executeQuery(sqlbv7);
			// 进入内层循环,//内层循环保项的数目
			for (int j = 1; j <= bxLLClaimDetailSet.size(); j++) {
				// 根据llclaimdetail中的 给付责任类型和给付责任编码 从LMDutyGetClm中 查询给付责任名称 即
				// 保项名称
				String bxNameSQL = "select GetDutyName from lmdutygetclm where getdutycode='"
						+ "?getdutycode?"
						+ "'"
						+ " and getdutykind='"
						+ "?getdutykind?" + "'";
				SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
				sqlbv8.sql(bxNameSQL);
				sqlbv8.put("getdutycode", bxLLClaimDetailSet.get(j).getGetDutyCode());
				sqlbv8.put("getdutykind", bxLLClaimDetailSet.get(j).getGetDutyKind());
				ExeSQL bxnameExeSQL = new ExeSQL();
				String bxName = bxnameExeSQL.getOneValue(sqlbv8);
				String[] stra = new String[3];
				stra[0] = "出险保项: " + bxName;
				stra[1] = "有效保额: " + bxLLClaimDetailSet.get(j).getAmnt();
				stra[2] = "生效日期: " + bxLLClaimDetailSet.get(j).getCValiDate();
				tListTable_Cont.add(stra); // 将保项信息加入ListTable
			}
			String[] spaceStr = new String[1];
			spaceStr[0] = "";
			tListTable_Cont.add(spaceStr);// 加空行
		}
		tXmlExport.addListTable(tListTable_Cont, Title_Cont);

		// #############################################################################
		logger.debug("--------以下为“既往赔案情况”--------");

		// 根据 出险人客户号 查询“赔案号”
		String tClmSql = "select * from LLRegister a where 1=1 "
				+ "and a.RgtNo !='"
				+ "?clmno?"
				+ "'"
				+ " and a.RgtNo in (select t.CaseNo from LLCase t where t.CaseNo!='"
				+ "?clmno?" + "' and t.CustomerNo = '" + "?cusno?" + "')";
		logger.debug("--------“既往赔案情况”查询语句===----" + tClmSql);
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(tClmSql);
		sqlbv9.put("clmno", mClmNo);
		sqlbv9.put("cusno", mCusNo);
		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		LLRegisterSet tLLRegisterSet = tLLRegisterDB.executeQuery(sqlbv9);
		// 查询出“赔案”数目 作为外层循环
		ListTable tListTable_HISTORY = new ListTable();
		tListTable_HISTORY.setName("HISTORY");
		String[] Title_HISTORY = new String[1];
		Title_HISTORY[0] = "";
		if (tLLRegisterSet.size() > 0) {
			for (int k = 1; k <= tLLRegisterSet.size(); k++) {
				String ttRgtNo = tLLRegisterSet.get(k).getRgtNo();

				// //查询 立案分案---LLCase，查询 既往赔案的 出险时间，申请人陈述出险情况（事故描述）
				// LLCaseDB ttLLCaseDB = new LLCaseDB();
				// ttLLCaseDB.setCaseNo(tRgtNo);
				// ttLLCaseDB.setCustomerNo(mCusNo);
				// ttLLCaseDB.getInfo();
				// 查询事件号-----用“赔案号”查询 分案事件关联---LLCaseRela
				String ttAccNoSql = "select CaseRelaNo from LLCaseRela where CaseNo='"
						+ "?caseno?" + "'";
				SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
				sqlbv10.sql(ttAccNoSql);
				sqlbv10.put("caseno", ttRgtNo);
				ExeSQL ttAccNoExeSQL = new ExeSQL();
				String ttAccNo = ttAccNoExeSQL.getOneValue(sqlbv10);
				// 查询 事件表（LLAccident）----查询 出险时间，申请人陈述出险情况（事故描述）
				LLAccidentDB ttLLAccidentDB = new LLAccidentDB();
				ttLLAccidentDB.setAccNo(ttAccNo);
				ttLLAccidentDB.getInfo();
				String ttAccDate = ttLLAccidentDB.getAccDate();
				String ttAccdentDesc = ttLLAccidentDB.getAccDesc();
				if (ttAccdentDesc == null) {
					ttAccdentDesc = "";
				}

				// 理赔类型----reason
				LLPRTPubFunBL ttLLPRTPubFunBL = new LLPRTPubFunBL();
				String ttreason = ttLLPRTPubFunBL.getSLLReportReason(ttRgtNo,
						mCusNo);

				// 查询 赔案---LLClaim，查出“赔付结论”
				LLClaimDB tLLClaimDB = new LLClaimDB();
				tLLClaimDB.setClmNo(ttRgtNo);
				tLLClaimDB.getInfo();
				String ttGiveType = tLLClaimDB.getGiveType();
				if (ttGiveType == null) {
					ttGiveType = "";
				}

				// 既往赔案号
				String[] clmNoStr = new String[1];
				clmNoStr[0] = "既往赔案 " + k + "：" + ttRgtNo; // 页面上显示既往赔案号
				tListTable_HISTORY.add(clmNoStr);
				// 出险时间
				String[] accidentDateStr = new String[1];
				accidentDateStr[0] = "出险时间：" + ttAccDate;
				tListTable_HISTORY.add(accidentDateStr);
				// 理赔类型
				String[] reasonStr = new String[1];
				reasonStr[0] = "理赔类型：" + ttreason;
				tListTable_HISTORY.add(reasonStr);
				// 出险情况
				String[] accdentDescStr = new String[1];
				accdentDescStr[0] = "出险情况：" + ttAccdentDesc;
				tListTable_HISTORY.add(accdentDescStr);
				// 理赔结论 0--给付;1--拒付;2--客户撤案;3--公司撤案;
				String[] giveTypeStr = new String[1];
				if (ttGiveType.equals("0")) {
					giveTypeStr[0] = "理赔结论：" + "给付";
				} else if (ttGiveType.equals("1")) {
					giveTypeStr[0] = "理赔结论：" + "拒付";
				} else if (ttGiveType.equals("2")) {
					giveTypeStr[0] = "理赔结论：" + "客户撤案";
				} else if (ttGiveType.equals("3")) {
					giveTypeStr[0] = "理赔结论：" + "公司撤案";
				} else {
					giveTypeStr[0] = "理赔结论：";
				}
				tListTable_HISTORY.add(giveTypeStr);

			}
			String[] spaceStr = new String[1];
			spaceStr[0] = "";
			tListTable_HISTORY.add(spaceStr); // 加空行
		}
		tXmlExport.addListTable(tListTable_HISTORY, Title_HISTORY);

		// #############################################################################
		logger.debug("--------以下为“调查原因及要求”--------");
		ListTable tListTable_REASON = new ListTable();
		tListTable_REASON.setName("REASON");
		String[] Title_REASON = new String[1];
		Title_REASON[0] = "";
		// 调查原因
		String[] llinqreasonStr = new String[1];
		llinqreasonStr[0] = "调查原因：" + tllinqreason;
		tListTable_REASON.add(llinqreasonStr);

		// 加入空行
		String[] tNull = new String[1];
		tNull[0] = "";
		// tListTable_REASON.add(tNull);
		// tListTable_REASON.add(tNull);

		// 调查项目
		String[] InqItemStr = new String[1];
		InqItemStr[0] = "调查项目：" + tInqItem;
		tListTable_REASON.add(InqItemStr);
		// tListTable_REASON.add(tNull);
		// tListTable_REASON.add(tNull);

		// 备注
		String[] RemarkStr = new String[1];
		RemarkStr[0] = "备注：" + tRemark;
		tListTable_REASON.add(RemarkStr);

		tXmlExport.addListTable(tListTable_REASON, Title_REASON);

		/* ##################### 后台调试部分，生成临时文件############################ */
		// String strTemplatePath="D:/ui/f1print/NCLtemplate/";
		// String strVFPathName=strTemplatePath+"DDDD.vts";
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(tXmlExport.getInputStream(), strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);
		// tXmlExport.outputDocumentToFile("D:/ui/f1print/NCLtemplate/", "testHZM");
		// //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		logger.debug("xmlexport=" + tXmlExport);
		
	    String updateStateSql="update loprtmanager set stateflag='1' where otherno='"+"?clmno?"+"' and code='PCT018'";
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
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRTInteInqTaskBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			//
			mErrors.addOneError(tError);
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
