package com.sinosoft.lis.claimnb;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import com.sinosoft.lis.claim.LLClaimPubFunBL;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
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
 * Description: 打印补费通知书 --LLUWPCLMAddFeeBL.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author niuzj,2006-01-24
 * @version 1.0
 */

public class LLUWPCLMAddFeeBL implements PrintService {
private static Logger logger = Logger.getLogger(LLUWPCLMAddFeeBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();
	private XmlExport xmlExportAll = new XmlExport();
	private GlobalInput tGI;
	private String mClmNo = ""; // 赔案号
	private String mWebPath = ""; // 模板路径
	private String mNoticeNo = ""; // 通知书号
	private String mContNo = ""; // 合同号
	private String mDoutyCode = ""; // 责任编码
	private String mPayPlanCode = ""; // 交费计划编码
	private String mPolNO = ""; // 保单号

	public LLUWPCLMAddFeeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------补费通知书-----LLUWPCLMAddFeeBL测试-----开始----------");

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

		logger.debug("----------补费通知书-----LLUWPCLMAddFeeBL测试-----结束----------");
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
		tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		// 合同号
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo.equals("") || mContNo.equals(null)) {
			CError tError = new CError();
			tError.moduleName = "LLUWPCLMAddFeeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "传入的合同号为空!";
			mErrors.addOneError(tError);
			return false;
		}
		// 赔案号
		mClmNo = (String) mTransferData.getValueByName("ClmNo");
		if (mClmNo.equals("") || mClmNo.equals(null)) {
			CError tError = new CError();
			tError.moduleName = "LLUWPCLMAddFeeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "传入的赔案号为空!";
			mErrors.addOneError(tError);
			return false;
		}
		// 通知书号
		mNoticeNo = (String) mTransferData.getValueByName("NoticeNo");
		if (mNoticeNo.equals("") || mNoticeNo.equals(null)) {
			CError tError = new CError();
			tError.moduleName = "LLUWPCLMAddFeeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "传入的通知书号码为空!";
			mErrors.addOneError(tError);
			return false;
		}
		mWebPath = (String) mTransferData.getValueByName("Path");
		// 责任编码
		mDoutyCode = (String) mTransferData.getValueByName("DoutyCode");
		if (mDoutyCode.equals("") || mDoutyCode.equals(null)) {
			CError tError = new CError();
			tError.moduleName = "LLUWPCLMAddFeeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "传入的责任编码为空!";
			mErrors.addOneError(tError);
			return false;
		}

		// 交费计划编码
		mPayPlanCode = (String) mTransferData.getValueByName("PayPlayCode");
		if (mPayPlanCode.equals("") || mPayPlanCode.equals(null)) {
			CError tError = new CError();
			tError.moduleName = "LLUWPCLMAddFeeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "传入的交费计划编码为空!";
			mErrors.addOneError(tError);
			return false;
		}
		// 保单号
		mPolNO = (String) mTransferData.getValueByName("PolNo");
		if (mPolNO.equals("") || mPolNO.equals(null)) {
			CError tError = new CError();
			tError.moduleName = "LLUWPCLMAddFeeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "传入的保单号为空!";
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("LLUWPCLMAddFeeBL.vts", ""); // 所用模板名称
		LLClaimPubFunBL tLLClaimPubFunBL = new LLClaimPubFunBL();

		FDate fDate = new FDate();
		Date tDate = null;
		GregorianCalendar tCalendar = new GregorianCalendar();
		int tYear = 0;
		int tMonth = 0;
		int tDay = 0;

		logger.debug("----------以下查询是为单个变量赋值-----------");

		// 应收号，即通知书号
		String tNoticeNo = mNoticeNo;

		// 赔案号
		String tClmNo = mClmNo;

		// 立案日期
		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
		tLLRegisterSchema = tLLClaimPubFunBL.getLLRegister(tClmNo);
		String tRgtDate = tLLRegisterSchema.getRgtDate();

		// 补费止期=立案日期+30天
		// 可以用SQL：select to_date('2005-02-01','yyyy-mm-dd')+30 from dual
		/**
		 * formatDate()方法在生产环境中不能执行报错，所以修改为另外一种写法 tDate =
		 * PubFun.calDate(fDate.getDate(tRgtDate),30,"D",null); String tReEndDate =
		 * StrTool.formatDate(tDate); logger.debug(StrTool.formatDate(tDate) );
		 */
		String tReEndDate = PubFun.calDate(tRgtDate, 30, "D", null);
		logger.debug(tReEndDate);

		// 保单号
		// String tSql1 =" select distinct a.polno,a.appntno from LLUWPremMaster a where
		// a.ClmNo = '"+mClmNo+"' ";
		ExeSQL tExeSQL = new ExeSQL();
		// String tPolNo = tExeSQL.getOneValue(tSql1);
		String tPolNo = mPolNO;

		// 险种号，根据保单号去查询
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		tLCPolSchema = tLLClaimPubFunBL.getLCPol(tPolNo);
		String tRiskCode = tLCPolSchema.getRiskCode();
		tLMRiskSchema = tLLClaimPubFunBL.getLMRisk(tRiskCode);
		String tRiskName = tLMRiskSchema.getRiskShortName();

		// 投保人姓名,被保人姓名
		String tSql2 = " select b.appntname,b.insuredname,b.agentcode,b.managecom from lccont b where b.contno='"
				+ "?contno?" + "' ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql2);
		sqlbv.put("contno", mContNo);
		SSRS tSSRS2 = new SSRS();
		tSSRS2 = tExeSQL.execSQL(sqlbv);
		String tAppntName = "";
		String tInsuredName = "";
		String tAgentCode = "";
		String tManageCom = "";
		if (tSSRS2.getMaxRow() > 0) {
			tAppntName = tSSRS2.GetText(1, 1);
			tInsuredName = tSSRS2.GetText(1, 2);
			tAgentCode = tSSRS2.GetText(1, 3);
			tManageCom = tSSRS2.GetText(1, 4);
		}

		// 健康加费,职业加费
		/**
		 * 用ljspayperson表中的payplancode去判断是健康加费还是职业加费，这个有问题 String tSql3 =" select
		 * distinct polno,dutycode,payplancode,sum(sumduepaymoney) " +" from
		 * ljspayperson where 1=1 " +" and contno='"+mContNo+"' " +" and
		 * polno='"+tPolNo+"' " +" and getnoticeno = '"+mNoticeNo+"' " //+" and
		 * payplancode in ('00000001','00000011') " //01-首期健康加费,11-复效健康加费 //+" and
		 * payplancode in ('00000002','00000012') " //02-首期职业加费,12-复效职业加费 +" group by
		 * polno,dutycode,payplancode ";
		 */
		// 重新写查询健康加费和职业加费的SQL，by niuzj,2006-02-07
		String tSql3 = " select b.polno,b.dutycode,b.payplantype,sum(a.sumduepaymoney) "
				+ " from ljspayperson a,lcprem b "
				+ " where 1=1 "
				+ " and a.polno=b.polno "
				+ " and a.dutycode=b.dutycode "
				+ " and a.payplancode=b.payplancode "
				+ " and a.paytype='ZC' "
				+ " and a.getnoticeno='"
				+ "?noticeno?"
				+ "' "
				+ " and b.polno='"
				+ "?polno?"
				+ "' "
				+ " and b.dutycode='"
				+ "?dutycode?"
				+ "' "
				+ " and b.payplancode='"
				+ "?payplancode?"
				+ "'  and b.payplancode like '000000%'"
				+ " group by b.polno,b.dutycode,b.payplantype,b.payplancode ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql3);
		sqlbv1.put("noticeno", mNoticeNo);
		sqlbv1.put("polno", tPolNo);
		sqlbv1.put("dutycode", mDoutyCode);
		sqlbv1.put("payplancode", mPayPlanCode);
		SSRS tSSRS3 = new SSRS();
		tSSRS3 = tExeSQL.execSQL(sqlbv1);
		// String tPayplanCode = "";
		String tPayplanType = "";
		String tSum1 = "";
		String tSum2 = "";
		if (tSSRS3.getMaxRow() > 0) {
			for (int i = 1; i <= tSSRS3.MaxRow; i++) {
				tPayplanType = tSSRS3.GetText(i, 3);
				if (tPayplanType.equals("01")) {
					tSum1 = tSSRS3.GetText(i, 4);// 健康加费
				} else if (tPayplanType.equals("02")) {
					tSum2 = tSSRS3.GetText(i, 4);// 职业加费
				} else {
					tSum1 = "";
					tSum2 = "";
				}
			}

			if (tSum1.equals("") || tSum1.equals(null)) {
				tSum1 = "0";
			}
			if (tSum2.equals("") || tSum2.equals(null)) {
				tSum2 = "0";
			}

		}

		// 利息
		String tSql4 = " select pay from ljspayclaim  where 1=1 "
				+ " and OtherNoType='5' and OtherNo='" + "?clmno?" + "' "
				+ " and getnoticeno='" + "?noticeno?" + "' " + " and contno='"
				+ "?contno?" + "' " + " and polno ='" + "?polno?" + "' "
				+ " and subfeeoperationtype ='C3002' "
				+ " and feefinatype ='LX' "; // 财务科目为利息
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql4);
		sqlbv2.put("noticeno", mNoticeNo);
		sqlbv2.put("polno", tPolNo);
		sqlbv2.put("clmno", mClmNo);
		sqlbv2.put("contno", mContNo);
		String tLX = tExeSQL.getOneValue(sqlbv2);
		if (tLX.equals("") || tLX.equals(null)) {
			tLX = "0";
		}

		// 补费金额合计
		String tSql5 = " select sumduepaymoney from ljspay where 1=1 "
				+ " and getnoticeno ='" + "?noticeno?" + "' " + " and otherno = '"
				+ "?clmno?" + "' and othernotype = '5' ";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql5);
		sqlbv3.put("noticeno", mNoticeNo);
		sqlbv3.put("clmno", mClmNo);
		String tCount = tExeSQL.getOneValue(sqlbv3);
		if (tCount.equals("") || tCount.equals(null)) {
			tCount = "0";
		}

		// 审核人姓名，取操作人
		String tSql6 = " select username from llclaimuser where usercode='"
				+ "?usercode?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql6);
		sqlbv4.put("usercode", tGI.Operator);
		String tOperator = tExeSQL.getOneValue(sqlbv4);

		// 统计时间,默认为系统时间
		String tSysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		logger.debug("*********把查询出的值添加到模版中去*********");
		tTextTag.add("NoticeNo", tNoticeNo);
		tTextTag.add("ClaimNo", tClmNo);
		tTextTag.add("AppntName1", tAppntName);
		tTextTag.add("RgtDate", StrTool.getChnDate(tRgtDate));
		tTextTag.add("ReEndDate", StrTool.getChnDate(tReEndDate));
		tTextTag.add("RiskShortName", tRiskName);
		tTextTag.add("PolNo", tPolNo);
		tTextTag.add("AppntName2", tAppntName);
		tTextTag.add("InsureName", tInsuredName);
		tTextTag.add("WorkAddFee", new DecimalFormat("0.00").format(Double
				.parseDouble(tSum2)));
		tTextTag.add("HealthAddFee", new DecimalFormat("0.00").format(Double
				.parseDouble(tSum1)));
		tTextTag.add("Accrual", new DecimalFormat("0.00").format(Double
				.parseDouble(tLX)));
		tTextTag.add("TotalFee", new DecimalFormat("0.00").format(Double
				.parseDouble(tCount)));
		tTextTag.add("AuditorName", tOperator);
		tTextTag.add("SysDate", tSysDate);
		tTextTag.add("AgentCode", tAgentCode);
		tTextTag.add("ManageCom", tManageCom);

		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		// tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		/* ##################### 后台调试部分，生成临时文件############################ */
		// String strTemplatePath="E:/lis/ui/f1print/NCLtemplate/";
		// String strVFPathName=strTemplatePath+"test.vts"; //生成临时文件名
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(tXmlExport.getInputStream(), strTemplatePath);
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
			tError.moduleName = "LLUWPCLMAddFeeBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
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

	// 主函数
	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo", "BJ010422651010378");
		tTransferData.setNameAndValue("ClmNo", "90000018853");
		tTransferData.setNameAndValue("NoticeNo", "CLMUW0000000023");
		tTransferData.setNameAndValue("DoutyCode", "265000");
		tTransferData.setNameAndValue("PayPlayCode", "00000001");
		tTransferData.setNameAndValue("PolNO", "BJ010422651010378000");
		// tTransferData.setNameAndValue("Path", "E:/ui/f1print/NCLtemplate");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLUWPCLMAddFeeBL tLLUWPCLMAddFeeBL = new LLUWPCLMAddFeeBL();
		if (tLLUWPCLMAddFeeBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}

	}

}
