package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LDCodeSchema;
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
 * Title: 综合打印-->续收打印-->续期保费实收日结清单
 * </p>
 * <p>
 * Description: 表头信息：选择的选项内容
 * ------------------------------------------------------------------------- 保单号
 * 投保人 交费对应日 应收保费 实收保费 保费实收日 业务员/服务人员 区部组 （银行网点） 交费形式 交费方式 保费形式 用户
 * -------------------------------------------------------------------------
 * 表尾信息：收银合计： 12件 181800.00元 其中：现金： 1件 600.00元 支票： 11件 181200.00元 转帐： 0件 0.00元
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author
 * @version 1.0
 */

public class XQInComeFeeDailyCheckBL implements PrintService {
private static Logger logger = Logger.getLogger(XQInComeFeeDailyCheckBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */

	private TransferData mTransferData = new TransferData();

	private String mStatiCode = ""; // 统计机构
	private String mStartDate = ""; // 开始时间
	private String mEndDate = ""; // 结束时间
	private String mSaleChnl = ""; // 渠道
	// private String mTransferBank="";//划款银行
	// private String mBankCode="";//银行网点
	private String mPayMode = ""; // 保费形式
	private String mSecPayMode = ""; // 交费形式
	private String mPayIntv = ""; // 交费方式
	private String mOperator = ""; // 操作员
	private String XBSRCondtion = ""; // 外部传入的条件字符串，用作表头
	private String XBSRTimes = ""; // 时间段

	public XQInComeFeeDailyCheckBL() {

	}

	/**
	 * 主函数------主要用于 设置数据，调试程序入口
	 * 
	 * @param:
	 * @return: 数据处理后信息
	 */

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "8632";
		tG.ManageCom = "8632";

		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("StatiCode", "8632"); // 统计机构
		tTransferData.setNameAndValue("StartDate", "2006-05-12"); // 开始时间
		tTransferData.setNameAndValue("EndDate", "2006-05-12"); // 结束时间
		tTransferData.setNameAndValue("SaleChnl", "1"); // 渠道
		tTransferData.setNameAndValue("TransferBank", ""); // 划款银行
		tTransferData.setNameAndValue("BankCode", ""); // 银行网点
		tTransferData.setNameAndValue("PayMode", "1"); // 保费形式
		tTransferData.setNameAndValue("SecPayMode", "0"); // 交费形式
		tTransferData.setNameAndValue("PayIntv", ""); // 交费方式
		tTransferData.setNameAndValue("Operator", "001"); // 操作员

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		XQInComeFeeDailyCheckBL tXQInComeFeeDailyCheckBL = new XQInComeFeeDailyCheckBL();
		if (tXQInComeFeeDailyCheckBL.submitData(tVData, "") == false) {
			logger.debug("----------续收打印-->续期保费实收日结清单出错---------------");
		}
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData--- 输入的数据 cOperate--- 数据操作符
	 * @return: 布尔值，成功返回“true”，失败返回“false”
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("-----续收打印-->续期保费实收日结清单开始---------XQInComeFeeDailyCheckBL---");
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
		logger.debug("-----续收打印-->续期保费实收日结清单结束----XQInComeFeeDailyCheckBL---");

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
		mInputData = cInputData; // 得到外部传入的数据,将数据备份到本类中
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStatiCode = (String) mTransferData.getValueByName("StatiCode"); // 统计机构
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 开始时间
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 结束时间
		mSaleChnl = (String) mTransferData.getValueByName("SaleChnl"); // 渠道
		mPayMode = (String) mTransferData.getValueByName("PayMode"); // 保费形式
		mSecPayMode = (String) mTransferData.getValueByName("SecPayMode"); // 交费形式
		mPayIntv = (String) mTransferData.getValueByName("PayIntv"); // 交费方式
		mOperator = (String) mTransferData.getValueByName("Operator"); // 操作员

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		if (mStatiCode == null || mStatiCode.equals("")) {
			CError tError = new CError();
			tError.moduleName = "XQInComeFeeDailyCheckBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "前台传入 机构代码 失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (mStartDate == null || mStartDate.equals("")) {
			CError tError = new CError();
			tError.moduleName = "XQInComeFeeDailyCheckBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "前台传入 起始日期 失败";
			mErrors.addOneError(tError);
			return false;

		}
		if (mEndDate == null || mEndDate.equals("")) {
			CError tError = new CError();
			tError.moduleName = "XQInComeFeeDailyCheckBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "前台传入 结束日期 失败";
			mErrors.addOneError(tError);
			return false;

		}
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mStatiCode);
		if (tLDComDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "XQInComeFeeDailyCheckBL";
			tError.functionName = "getLDCode";
			tError.errorMessage = "查询机构信息失败!,机构代码为" + mStatiCode;
			this.mErrors.addOneError(tError);
			return false;
		}
		tLDComSchema.setSchema(tLDComDB.getSchema());
		String StatiCodeName = tLDComSchema.getName();
		XBSRCondtion = "统计机构为：" + mStatiCode + "--" + StatiCodeName;
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 系统日期
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		// 将以"-"分隔日期值，转换为 “XXXX年XX月XX日”形式
		String strDate = StrTool.getVisaYear(mStartDate) + "年"
				+ StrTool.getVisaMonth(mStartDate) + "月"
				+ StrTool.getVisaDay(mStartDate) + "日";
		String strStartDate = StrTool.getChnDate(mStartDate);
		String strEndDate = StrTool.getChnDate(mEndDate);

		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("XQInComeFeeDailyCheck.vts", ""); // 最好紧接着就初始化xml文档

		TextTag tTextTag = new TextTag(); // 增加一个动态标签，参数为标签名，值
		ListTable tListTable = new ListTable(); // 新建一个ListTable，参数为一个字符串数组，包含所需的值

		logger.debug("*********************************************************");
		logger.debug("*******给列表赋值********************************");
		tListTable.setName("XBSR");
		// 定义列表标题,12列
		String[] Title = new String[12];
		for (int i = 0; i < 11; i++) {
			Title[i] = "";
		}
		// logger.debug("-----首先查询，“保单号---保费实收日----交费形式----用户”用于外层循环------");
		// 判断查询非必须条件是否使用
		String InComeSQL = " "; // 用于正常收费
		String PayOutSQL = " "; // 用于回退处理
		if (!(mSaleChnl.equals(""))) {
			InComeSQL = InComeSQL
					+ " and exists (select 1 from lccont where contno = a.contno and salechnl ='"
					+ "?mSaleChnl?" + "')"; // 销售渠道
			String tSaleChnlName = "   ||销售渠道："
					+ getLDCode("salechnl", mSaleChnl);
			XBSRCondtion = XBSRCondtion + tSaleChnlName;
		}
		if (!(mSecPayMode.equals(""))) {
			InComeSQL = InComeSQL
					+ " and exists (select 1 from lccont where paylocation='"
					+ "?mSecPayMode?" + "' and contno=a.contno)"; // 交费形式
			String tSecPayModeName = "   ||交费形式："
					+ getLDCode("paylocation", mSecPayMode);
			XBSRCondtion = XBSRCondtion + tSecPayModeName;
		}
		if (!(mPayIntv.equals(""))) {
			InComeSQL = InComeSQL
					+ " and  exists ( select 1 from lcpol where polno=mainpolno and  payintv='"
					+ "?mPayIntv?" + "' and contno=a.contno)"; // 交费方式
			String tPayIntvName = "   ||交费方式：" + getLDCode("payintv", mPayIntv);
			XBSRCondtion = XBSRCondtion + tPayIntvName;
		}
		if (!(mPayMode.equals(""))) {
			InComeSQL = InComeSQL
					+ " and exists ( select 1 from ljtempfeeclass where paymode ='"
					+ "?mPayMode?" + "' and tempfeeno=a.getnoticeno)"; // 保费形式<c代表ljaget表>
			String tPayModeName = "   ||保费形式：" + getLDCode("paymode", mPayMode);
			XBSRCondtion = XBSRCondtion + tPayModeName;
		}
		if (!(mOperator.equals(""))) {
			InComeSQL = InComeSQL + " and a.operator='" + "?mOperator?" + "' "; // 用户<c代表ljaget表>
			String tOperator = "   ||用户：" + mOperator;
			XBSRCondtion = XBSRCondtion + tOperator;
		}
		String strSQL ="";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strSQL = " select a.contno contno,a.riskcode,"
					+ " (select appntname from lcappnt where contno=a.contno) appntname,"
					+ " a.lastpaytodate paytodate,"
					+ " a.sumduepaymoney dueprem,"
					+ " a.sumactupaymoney prem,"
					+ " a.makedate makedate,"
					+ " concat(a.agentcode,(select concat('|',name) from laagent where agentcode=a.agentcode)) agentcode,"
					+ " getGlobalBranch(a.agentcode,a.contno) agent,"
					+ " (select codename from ldcode where codetype='paylocation' and  code in (select c.paylocation from lccont c where c.contno=a.contno)) pay,"
					+ " (select codename from ldcode where codetype='payintv' and code in (select payintv from lcpol where mainpolno=polno and contno=a.contno and rownum=1)) payintv,"
					+ " (select codename from ldcode where codetype = 'paymode' and code in (select paymode from ljtempfeeclass  where tempfeeno = a.getnoticeno and rownum=1)),"
					+ " a.operator operator" + " from ljapayperson a"
					+ " where a.mainpolyear > 1" + " and a.paytype in ('ZC','HM')"
					+ " and a.makedate between '" + "?mStartDate?" + "' and '"
					+ "?mEndDate?" + "' " + " and a.managecom like concat('" + "?mStatiCode?"
					+ "','%')" + InComeSQL;
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strSQL = " select a.contno contno,a.riskcode,"
					+ " (select appntname from lcappnt where contno=a.contno) appntname,"
					+ " a.lastpaytodate paytodate,"
					+ " a.sumduepaymoney dueprem,"
					+ " a.sumactupaymoney prem,"
					+ " a.makedate makedate,"
					+ " concat(a.agentcode,(select concat('|',name) from laagent where agentcode=a.agentcode)) agentcode,"
					+ " getGlobalBranch(a.agentcode,a.contno) agent,"
					+ " (select codename from ldcode where codetype='paylocation' and  code in (select c.paylocation from lccont c where c.contno=a.contno)) pay,"
					+ " (select codename from ldcode where codetype='payintv' and code in (select payintv from lcpol where mainpolno=polno and contno=a.contno )limit 0,1) payintv,"
					+ " (select codename from ldcode where codetype = 'paymode' and code in (select paymode from ljtempfeeclass  where tempfeeno = a.getnoticeno )limit 0,1),"
					+ " a.operator operator" + " from ljapayperson a"
					+ " where a.mainpolyear > 1" + " and a.paytype in ('ZC','HM')"
					+ " and a.makedate between '" + "?mStartDate?" + "' and '"
					+ "?mEndDate?" + "' " + " and a.managecom like concat('" + "?mStatiCode?"
					+ "','%')" + InComeSQL;
		}
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mStatiCode", mStatiCode);
		sqlbv.put("mSaleChnl", mSaleChnl);
		sqlbv.put("mSecPayMode", mSecPayMode);
		sqlbv.put("mPayIntv", mPayIntv);
		sqlbv.put("mPayMode", mPayMode);
		sqlbv.put("mOperator", mOperator);
		logger.debug("外层查询语句strSQL====" + strSQL);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS.getMaxRow() <= 0) {
			CError tError = new CError();
			tError.moduleName = "XQInComeFeeDailyCheckBL";
			tError.functionName = "dealData";
			tError.errorMessage = "根据该输入条件查询无结果，请重新输入。";
			logger.debug("根据该输入条件查询无结果，请重新输入。");
			this.mErrors.addOneError(tError);
			return false;

		}
		int tMaxRow = tSSRS.getMaxRow();
		logger.debug("-----------查询到收费  [" + tMaxRow + "] 笔。");

		int tInComeNum = 0; // 收银合计： 件 元
		double tInComeSum = 0;
		int tCashNum = 0; // 现金： 件 元
		double tCashSum = 0;
		int tCheckNum = 0; // 支票： 件 元
		double tCheckSum = 0;
		int tTranNum = 0; // 转帐： 件 元
		double tTranSum = 0;
		for (int i = 1; i <= tMaxRow; i++) {
			String tContNo = tSSRS.GetText(i, 1); // 保单号
			String tRiskCode = tSSRS.GetText(i, 2);
			String tApptName = tSSRS.GetText(i, 3); // 投保人
			String tLastPayToDate = tSSRS.GetText(i, 4); // 交费对应日
			String tSumDuePay = tSSRS.GetText(i, 5); // 应收保费
			String tSumActuPay = tSSRS.GetText(i, 6); // 实收保费
			String tEnteraccDate = tSSRS.GetText(i, 7); // 保费实收日期
			String tAgentName = tSSRS.GetText(i, 8); // 业务员姓名
			String tAgentManage = tSSRS.GetText(i, 9); // 业务员姓名
			String tPaylocation = tSSRS.GetText(i, 10); // 缴费形式
			String tPayintv = tSSRS.GetText(i, 11); // 缴费方式
			String tPaymode = tSSRS.GetText(i, 12); // 保费形式
			String tOperator = tSSRS.GetText(i, 13); // yonghu

			String[] Stra = new String[13];
			Stra[0] = tContNo; // 保单号
			Stra[1] = tRiskCode;
			Stra[2] = tApptName; // 投保人
			Stra[3] = tLastPayToDate; // 交费对应日
			Stra[4] = tSumDuePay; // 应收保费
			Stra[5] = tSumActuPay; // 实收保费
			Stra[6] = tEnteraccDate; // 保费实收日
			Stra[7] = tAgentName;
			Stra[8] = tAgentManage; // 区部组 （银行网点）
			Stra[9] = tPaylocation; // 交费形式
			Stra[10] = tPayintv; // 缴费方式;
			Stra[11] = tPaymode; // 保费形式
			Stra[12] = tOperator; // 用户
			tListTable.add(Stra);
			// 计算表尾信息
			tInComeNum = tInComeNum + 1; // 收银合计： 件 元
			tInComeSum = tInComeSum + Double.parseDouble(tSumActuPay);

			// paymode

			if ((tPaymode.equals("现金") || tPaymode.equals("现金送款簿"))
					&& tPaymode != null) {
				tCashNum = tCashNum + 1; // 现金： 件 元
				tCashSum = tCashSum + Double.parseDouble(tSumActuPay);
			}

			if (tPaymode.equals("支票") && tPaymode != null) {
				tCheckNum = tCheckNum + 1; // 支票： 件 元
				tCheckSum = tCheckSum + Double.parseDouble(tSumActuPay);
			}

			tTranNum = tInComeNum - tCashNum - tCheckNum; // 转帐： 件 元
			tTranSum = tTranSum + tInComeSum - tCashSum - tCheckSum;

		}

		logger.debug("----------------所有合同交费信息循环处理完毕---------------");
		// 格式化金额数据 Double.parseDouble(new
		// DecimalFormat("0.00").format(t_Sum_Return))
		tInComeSum = Double.parseDouble(new DecimalFormat("0.00")
				.format(tInComeSum));
		tCashSum = Double.parseDouble(new DecimalFormat("0.00")
				.format(tCashSum));
		tCheckSum = Double.parseDouble(new DecimalFormat("0.00")
				.format(tCheckSum));
		tTranSum = Double.parseDouble(new DecimalFormat("0.00")
				.format(tTranSum));

		logger.debug("********给标签赋值***************************************");
		XBSRTimes = strStartDate + "至" + strEndDate;
		tTextTag.add("XBSRTimes", XBSRTimes); // $=/XBSRTimes$ ------日期（外部传入的
												// “确认日期”）
		tTextTag.add("XBSRCondtion", XBSRCondtion); // $=/XBSRCondtion$-------表头信息：选择的选项内容
		tTextTag.add("XBSRj", String.valueOf(tInComeNum) + "件"); // 收银合计$=/XBSRj$
																	// <件数>
		tTextTag.add("XBSRm", String.valueOf(tInComeSum) + "元"); // $=/XBSRm$
																	// <金额>
		tTextTag.add("XBSRj1", String.valueOf(tCashNum) + "件"); // 现金：$=/XBSRj1$
																// <件数>
		tTextTag.add("XBSRm1", String.valueOf(tCashSum) + "元"); // $=/XBSRm1$
																// <金额>
		tTextTag.add("XBSRj2", String.valueOf(tCheckNum) + "件"); // 支票：$=/XBSRj2$
																	// <件数>
		tTextTag.add("XBSRm2", String.valueOf(tCheckSum) + "元"); // $=/XBSRm2$
																	// <金额>
		tTextTag.add("XBSRj3", String.valueOf(tTranNum) + "件"); // 转帐：$=/XBSRj3$
																// <件数>
		tTextTag.add("XBSRm3", String.valueOf(tTranSum) + "元"); // $=/XBSRm3$
																// <金额>

		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}
		logger.debug("************打包数据，准备返回前台**************");
		mResult.clear();
		mResult.add(tXmlExport);
		logger.debug("xmlexport=" + tXmlExport);
		return true;
	}

	/**
	 * 得到代码表的汉字的信息
	 * 
	 * @param pCodeType:代码类型
	 * @param pCode:代码编号
	 * @return
	 */
	public String getLDCode(String pCodeType, String pCode) {
		String tReturn = "";
		LDCodeSchema tLDCodeSchema = new LDCodeSchema();
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType(pCodeType);
		tLDCodeDB.setCode(pCode);
		if (tLDCodeDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDCodeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "XQInComeFeeDailyCheckBL";
			tError.functionName = "getLDCode";
			tError.errorMessage = "查询代码表[LDCode]信息失败!,代码类型为:" + pCodeType
					+ "代码为：" + pCode;
			this.mErrors.addOneError(tError);
			return tReturn;
		}
		tLDCodeSchema.setSchema(tLDCodeDB.getSchema());
		if (tLDCodeSchema != null) {
			tReturn = StrTool.cTrim(tLDCodeSchema.getCodeName());
		}
		return tReturn;
	}

	// 错误处理函数
	public CErrors getErrors() {
		return mErrors;
	}

	// 打包数据---用于向前台返回
	public VData getResult() {
		return mResult;
	}

}
