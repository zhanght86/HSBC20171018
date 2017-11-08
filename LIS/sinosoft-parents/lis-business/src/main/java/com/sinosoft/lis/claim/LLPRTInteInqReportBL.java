package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLInqApplyDB;
import com.sinosoft.lis.db.LLInqCertificateDB;
import com.sinosoft.lis.db.LLInqCourseDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.LLInqCertificateSet;
import com.sinosoft.lis.vschema.LLInqCourseSet;
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
 * Description: 单证打印：调查报告通知书-----PCT017 -----InquiryRptC000210.vts
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
public class LLPRTInteInqReportBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRTInteInqReportBL.class);

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

	public LLPRTInteInqReportBL() {
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

		logger.debug("----------单证打印：调查报告通知书-----LLPRTInteInqReportBL测试-----开始----------");

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

		logger.debug("----------单证打印：调查报告通知书------LLPRTInteInqReportBL测试-----结束----------");
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
//		tXmlExport.createDocument("InquiryRptC000210.vts", "");
		tXmlExport.createDocument("调查报告通知书");
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
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
		// 系统时间-----SysDate
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		// 查询调查申请表-----查询调查原因，调查结论，计算调查天数
		LLInqApplyDB tLLInqApplyDB = new LLInqApplyDB();
		tLLInqApplyDB.setClmNo(mClmNo);
		tLLInqApplyDB.setInqNo(mInqNo);
		tLLInqApplyDB.getInfo();
		// 计算调查天数
		int tDays = PubFun.calInterval(tLLInqApplyDB.getInqStartDate(),
				tLLInqApplyDB.getInqEndDate(), "D");
		// 查询调查原因
		String tllinqreasonSql = "select trim(CodeName)from ldcode where 1 = 1 and codetype = 'llinqreason' and code='"
				+ "?code?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tllinqreasonSql);
		sqlbv1.put("code", tLLInqApplyDB.getInqRCode());
		ExeSQL ttExeSQL = new ExeSQL();
		String tllinqreason = ttExeSQL.getOneValue(sqlbv1);

		// 调查过程表-----查询调查途径及证明材料，//查询该赔案的所有调查人（）
		LLInqCourseDB tLLInqCourseDB = new LLInqCourseDB();
		LLInqCourseSet tLLInqCourseSet = new LLInqCourseSet();
		tLLInqCourseDB.setClmNo(mClmNo);
		tLLInqCourseDB.setInqNo(mInqNo);
		tLLInqCourseSet.set(tLLInqCourseDB.query());
		String tInquiryPerson = "";
		if (tLLInqCourseSet.size() > 0) {
			for (int n = 1; n <= tLLInqCourseSet.size(); n++) {
				if (!(tLLInqCourseSet.get(n).getInqPer1() == null || tLLInqCourseSet
						.get(n).getInqPer1().equals(""))) {
					tInquiryPerson = tInquiryPerson
							+ tLLInqCourseSet.get(n).getInqPer1();
				}

				if (!(tLLInqCourseSet.get(n).getInqPer2() == null || tLLInqCourseSet
						.get(n).getInqPer2().equals(""))) {
					tInquiryPerson = tInquiryPerson + ","
							+ tLLInqCourseSet.get(n).getInqPer2();
				}

				if (n != tLLInqCourseSet.size()) {
					tInquiryPerson = tInquiryPerson + ";";
				}

			}
			// tInquiryPerson=tInquiryPerson+"；";
		}

		// #############################################################################
		logger.debug("--------以下为单个数据变量赋值--------");
		tTextTag.add("LLClaim.ClmNo", mClmNo); // 赔案号
		tTextTag.add("LLCase.CustomerName", tName); // 出险人
		tTextTag.add("ReasonCode", tClaimType);// 理赔类型
		tTextTag.add("Date", CurrentDate);// 提交调查日期-----
		tTextTag.add("LLSurvey.StartMan", tLLInqApplyDB.getApplyPer());// 提交调查人
		tTextTag.add("Days", tDays);// 实际调查天数----通过计算得出
		tTextTag.add("InquiryPerson", tInquiryPerson);// 调查人
		tTextTag.add("SysDate", CurrentDate);// 提交报告日期----系统日期
		tTextTag.add("LLInqConclusion.InqConclusion", tLLInqApplyDB
				.getInqConclusion());// 调查结论

		// #############################################################################
		logger.debug("--------调查原因--------");
		ListTable tListTable_InqReason = new ListTable();
		tListTable_InqReason.setName("INQUIRY");
		String[] Title_InqReason = new String[1];
		Title_InqReason[0] = "";
		String[] tllinqreasonStr = new String[1];
		tllinqreasonStr[0] = tllinqreason;// "";
		tListTable_InqReason.add(tllinqreasonStr);
		tXmlExport.addListTable(tListTable_InqReason, Title_InqReason);

		// #############################################################################
		logger.debug("--------调查途径及证明材料--------");

		ListTable tListTable_InqCourse = new ListTable();
		tListTable_InqCourse.setName("CONT2");
		String[] Title_InqCourse = new String[3];
		Title_InqCourse[0] = "";
		Title_InqCourse[1] = "";
		Title_InqCourse[2] = "";
		String idetaiInqCourse = "";// 详细调查过程
		if (tLLInqCourseSet.size() > 0) {
			for (int K = 1; K <= tLLInqCourseSet.size(); K++) {
				String tCouNo = tLLInqCourseSet.get(K).getCouNo();

				// 详细调查过程
				if (K == 1) {
					idetaiInqCourse = "  " + idetaiInqCourse
							+ tLLInqCourseSet.get(K).getInqCourse() + "\r\n";
				} else if (K != tLLInqCourseSet.size()) {
					idetaiInqCourse = idetaiInqCourse + "  "
							+ tLLInqCourseSet.get(K).getInqCourse() + "\r\n";
				} else {
					idetaiInqCourse = idetaiInqCourse + "  "
							+ tLLInqCourseSet.get(K).getInqCourse();
				}

				// 调查过程单证信息
				LLInqCertificateDB tLLInqCertificateDB = new LLInqCertificateDB();
				LLInqCertificateSet tLLInqCertificateSet = new LLInqCertificateSet();
				tLLInqCertificateDB.setClmNo(mClmNo);
				tLLInqCertificateDB.setInqNo(mInqNo);
				tLLInqCertificateDB.setCouNo(tLLInqCourseSet.get(K).getCouNo());
				tLLInqCertificateSet.set(tLLInqCertificateDB.query());
				String CerName = "";// 证明材料------调查过程单证名称合并
				for (int j = 1; j <= tLLInqCertificateSet.size(); j++) {
					if (j == 1) {
						CerName = tLLInqCertificateSet.get(j).getCerName();
					} else {
						CerName = CerName + "；"
								+ tLLInqCertificateSet.get(j).getCerName();
					}

				}

				String tInqByPer = tLLInqCourseSet.get(K).getInqByPer();

				String tInqModeSql = "select codename from ldcode where codetype ='llinqmode' and code='"
						+ "?code?" + "'";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tInqModeSql);
				sqlbv2.put("code", tLLInqCourseSet.get(K).getInqMode());
				ExeSQL tInqModeExeSQL = new ExeSQL();
				String tInqMode = tInqModeExeSQL.getOneValue(sqlbv2);

				String[] contStr = new String[3];
				if (!(tInqByPer == null || tInqByPer.equals(""))) {
					contStr[0] = K + "." + tInqByPer;// "被调查对象: "
				} else {
					contStr[0] = "";// "被调查对象: "
				}

				contStr[1] = tInqMode;// "调查途径";
				contStr[2] = CerName;// "证明材料";
				tListTable_InqCourse.add(contStr);
			}
			logger.debug("详细调查过程=" + idetaiInqCourse);
			tTextTag.add("LLInqConclusion.InqCourse", idetaiInqCourse);// 调查结论
		}

		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		tXmlExport.addListTable(tListTable_InqCourse, Title_InqCourse);

		// String strTemplatePath="D:/ui/f1print/NCLtemplate/";
		// String strVFPathName=strTemplatePath+"aaaa.vts";
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(tXmlExport.getInputStream(),
		// strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);

		/* ##################### 加入多行数据的打印变量############################ */
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		logger.debug("xmlexport=" + tXmlExport);
		
	    String updateStateSql="update loprtmanager set stateflag='1' where otherno='"+"?clmno?"+"' and code='PCT017'";
	    logger.debug("更新打印管理表的sql:"+updateStateSql);
	    SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(updateStateSql);
		sqlbv3.put("clmno", mClmNo);
	    mMMap.put(sqlbv3, "UPDATE");

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
			tError.moduleName = "LLPRTInteInqReportBL";
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
