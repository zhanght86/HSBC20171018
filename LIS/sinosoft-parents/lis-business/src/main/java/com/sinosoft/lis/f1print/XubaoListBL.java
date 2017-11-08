package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.operfee.RNHangUp;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author guanwei
 * @version 1.0
 */

public class XubaoListBL {
private static Logger logger = Logger.getLogger(XubaoListBL.class);
	private String mStartDate = "";
	private String mEndDate = "";
	private String mManageCom = "";
	double main = 0;
	double add = 0;
	double all = 0;
	int TotalCount = 0;

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	private String mSelString = "";

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	private boolean tFlag = false;
	private Object text;

	public XubaoListBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		mResult.clear();

		// 准备所有要打印的数据

		if (!getPrintData()) {

			return false;
		}

		return true;

	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 起始日期
		logger.debug("|||||||||StartDate||||||||" + mStartDate);
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 终止日期
		logger.debug("|||||||||mEndDate||||||||" + mEndDate);
		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 管理机构
		logger.debug("|||||||||mManageCom||||||||" + mManageCom);

		return true;
	}

	/**
	 * 获取返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "XubaoListBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	private boolean getPrintData() {
		ListTable tlistTable = new ListTable();
		String[] ContractTitle = new String[15]; // 随意定义的 与显示无关

		tlistTable.setName("list");
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String[] strArr = null;

		String tSql = "select * from LCCont "
				+ " where ContNo in (select distinct ContNo "
				+ "  from LCPol a, LMRiskApp b "
				+ " where a.RiskCode = b.RiskCode " + "   and PaytoDate >= '"
				+ "?mStartDate?" + "'" + "   and PaytoDate <= '" + "?mEndDate?" + "'"
				+ "   and ManageCom like concat('" + "?mManageCom?" + "','%') "
				+ "   and b.riskperiod = 'M'" + "   and MainPolNo = PolNo "
				+ "   and RnewFlag <> '-1')"
				+ " order by ManageCom, AgentCode, CValiDate,AppntNo";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		sqlbv1.put("mManageCom", mManageCom);
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		tLCContSet = tLCContDB.executeQuery(sqlbv1);
		logger.debug("续保清单查询主SQL：：：：：：：：：：：：：：：：：：：：：：：：" + tSql);
		if (tLCContSet.size() <= 0) {
			logger.debug("该管理机构" + mManageCom + "在所选日期区间" + mStartDate
					+ "至" + mEndDate + "没有数据。");
			buildError("getPrintData", "该管理机构在所选日期区间没有数据。");
			return false;
		} else
			for (int i = 1; i <= tLCContSet.size(); i++) {
				TotalCount = TotalCount + 1;
				logger.debug("开始处理第" + TotalCount + "条记录。");

				String tEndDate = ""; // 定义保单终止日
				String tContNo = ""; // 定义保单合同号
				String tAppntNo = ""; // 定义投保人编号
				String tAppntName = ""; // 定义投保人姓名
				String tPostAddress = ""; // 定义通讯地址
				String tZipCode = ""; // 定义邮政编码
				String tRiskShortName = ""; // 定义险种名称
				String tMainPrem = ""; // 定义主险保费
				String tAdditionalPrem = ""; // 定义附加险保费
				String tSPrem = ""; // 定义应收保费
				String tPayMode = ""; // 定义交费方式
				String tBankAccNo = ""; // 定义银行帐号
				String tPhontNo = ""; // 定义电话号码
				String tAgentCode = ""; // 定义服务人员代码
				String tAgentName = ""; // 定义服务人员姓名
				String tAgentGroupName = ""; // 定义服务人员所在区部组名称
				strArr = new String[14];

				LCContSchema tLCContSchema = new LCContSchema();
				// tLCContSchema = tSSRS.get(i);
				tLCContSchema = tLCContSet.get(i);

				// 保单合同号
				tContNo = tLCContSchema.getContNo();
				// 判断保单是否被挂起
				logger.debug("判断保单是否被挂起...");
				RNHangUp tRNHangUp = new RNHangUp(mGlobalInput);
				if (!tRNHangUp.checkHangUP(tContNo)) {
					this.mErrors.copyAllErrors(tRNHangUp.mErrors);
					continue;
				}
				strArr[1] = tContNo;

				// 交费对应日
				SSRS edSSRS = new SSRS();
				tSql = "select EndDate" + "  from LCPol " + " where ContNo = '"
						+ "?tContNo?" + "'" + "   and polno = mainpolno";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tSql);
				sqlbv2.put("tContNo", tContNo);
				logger.debug("查询主险交费对应日...");
				edSSRS = exeSql.execSQL(sqlbv2);
				tEndDate = edSSRS.GetText(1, 1);
				if (tEndDate != null) {
					strArr[0] = tEndDate;
				}

				// 投保人姓名
				tAppntName = tLCContSchema.getAppntName();
				strArr[2] = tAppntName;
				logger.debug("得到投保人姓名:" + tAppntName);

				// //银行帐号
				// tBankAccNo = tLCContSchema.getBankAccNo();
				// strArr[10] = tBankAccNo;
				// logger.debug("得到银行帐号：" + tBankAccNo);

				// 通讯地址&& 邮政编码&& 联系电话
				tSql = "select AddressNo from LCAppnt where ContNo='" + "?tContNo?"
						+ "'";
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(tSql);
				sqlbv3.put("tContNo", tContNo);
				exeSql = new ExeSQL();
				tSSRS = exeSql.execSQL(sqlbv3);
				String tAddressNo = tSSRS.GetText(1, 1);
				tAppntNo = tLCContSchema.getAppntNo();
				if (tSSRS.getMaxRow() > 0) {
					tAddressNo = tSSRS.GetText(1, 1);
					tSql = "select PostalAddress " + "  from LCAddress "
							+ " where CustomerNo='" + "?tAppntNo?" + "' "
							+ "   and AddressNo = '" + "?tAddressNo?" + "'";
					SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
					sqlbv4.sql(tSql);
					sqlbv4.put("tAppntNo", tAppntNo);
					sqlbv4.put("tAddressNo", tAddressNo);
					exeSql = new ExeSQL();
					SSRS paSSRS = new SSRS();
					paSSRS = exeSql.execSQL(sqlbv4);
					logger.debug("查询通讯地址" + tSql);
					tPostAddress = paSSRS.GetText(1, 1);
					strArr[3] = tPostAddress;
					logger.debug("通讯地址：" + tPostAddress);

					tSql = "select ZipCode " + "  from LCAddress "
							+ " where CustomerNo='" + "?tAppntNo?" + "' "
							+ "   and AddressNo = '" + "?tAddressNo?" + "'";
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					sqlbv5.sql(tSql);
					sqlbv5.put("tAppntNo", tAppntNo);
					sqlbv5.put("tAddressNo", tAddressNo);
					exeSql = new ExeSQL();
					SSRS zpSSRS = new SSRS();
					zpSSRS = exeSql.execSQL(sqlbv5);
					logger.debug("查询邮政编码" + tSql);
					tZipCode = zpSSRS.GetText(1, 1);
					strArr[4] = tZipCode;
					logger.debug("邮政编码：" + tZipCode);

					tSql = "select Phone " + "  from LCAddress "
							+ " where CustomerNo='" + "?tAppntNo?" + "' "
							+ "   and AddressNo = '" + "?tAddressNo?" + "'";
					SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
					sqlbv6.sql(tSql);
					sqlbv6.put("tAppntNo", tAppntNo);
					sqlbv6.put("tAddressNo", tAddressNo);
					exeSql = new ExeSQL();
					SSRS pnSSRS = new SSRS();
					pnSSRS = exeSql.execSQL(sqlbv6);
					logger.debug("查询联系电话" + tSql);
					tPhontNo = pnSSRS.GetText(1, 1);
					strArr[10] = tPhontNo;
					logger.debug("联系电话" + tPhontNo);
				}

				// 不续保险种
				SSRS tempSSRS = new SSRS();
				tSql = "select RiskCode from LCPol where ContNo = '" + "?tContNo?"
						+ "' " + "   and MainPolNo = PolNo";
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(tSql);
				sqlbv7.put("tContNo", tContNo);
				tempSSRS = exeSql.execSQL(sqlbv7);
				logger.debug("查询不续保险种：" + tSql);
				if (tempSSRS.getMaxRow() > 0) {
					for (int j = 1; j <= tempSSRS.getMaxRow(); j++) {
						tSql = "select RiskShortName from LMRisk "
								+ " where RiskCode = '"
								+ "?RiskCode?" + "'";
						SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
						sqlbv8.sql(tSql);
						sqlbv8.put("RiskCode", tempSSRS.GetText(1, 1));
						SSRS rmSSRS = new SSRS();
						rmSSRS = exeSql.execSQL(sqlbv8);
						tRiskShortName = rmSSRS.GetText(1, 1);
						strArr[5] = tRiskShortName;
						logger.debug("不续保险种名称：" + tRiskShortName);
					}
				}

				// 主险保费
				SSRS mpSSRS = new SSRS();
				tSql = "select Prem " + "  from LCPol " + " where ContNo ='"
						+ "?tContNo?" + "'" + "   and MainPolNo = PolNo";
				SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
				sqlbv9.sql(tSql);
				sqlbv9.put("tContNo", tContNo);
				mpSSRS = exeSql.execSQL(sqlbv9);
				logger.debug("查询主险保费" + tSql);
				tMainPrem = mpSSRS.GetText(1, 1);
				strArr[6] = tMainPrem;
				logger.debug("主险保费:" + tMainPrem);
				// 主险保费合计
				SSRS msSSRS = new SSRS();
				tSql = "select sum(Prem) " + "  from LCPol "
						+ " where ContNo ='" + "?tContNo?" + "'"
						+ "   and MainPolNo = PolNo";
				SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
				sqlbv10.sql(tSql);
				sqlbv10.put("tContNo", tContNo);
				msSSRS = exeSql.execSQL(sqlbv10);
				logger.debug("主险保费合计:" + tSql);
				String tSumMain = msSSRS.GetText(1, 1);
				if (tSumMain != null && !tSumMain.equals("")
						&& !tSumMain.equals("null")) {
					main = main + Double.parseDouble(tSumMain);
				}
				if (tSumMain == null || tSumMain.equals("null")) {
					tSumMain = "0";
				}

				// 附险保费
				SSRS apSSRS = new SSRS();
				tSql = "select Prem " + "  from LCPol " + " where ContNo ='"
						+ "?tContNo?" + "'" + "   and MainPolNo <> PolNo";
				SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
				sqlbv11.sql(tSql);
				sqlbv11.put("tContNo", tContNo);
				apSSRS = exeSql.execSQL(sqlbv11);
				logger.debug("查询附险保费:" + tSql);
				if (apSSRS.getMaxRow() > 0) {
					tAdditionalPrem = apSSRS.GetText(1, 1);
					strArr[7] = tAdditionalPrem;
					logger.debug("附险保费:" + tAdditionalPrem);
				}
				// 附险保费合计
				SSRS asSSRS = new SSRS();
				tSql = "select sum(Prem) " + "  from LCPol "
						+ " where ContNo ='" + "?tContNo?" + "'"
						+ "   and MainPolNo <> PolNo";
				SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
				sqlbv12.sql(tSql);
				sqlbv12.put("tContNo", tContNo);
				asSSRS = exeSql.execSQL(sqlbv12);
				logger.debug("附险保费合计:" + tSql);
				String tSumAdd = asSSRS.GetText(1, 1);
				if (tSumAdd != null && !tSumAdd.equals("")
						&& !tSumAdd.equals("null")) {
					add = add + Double.parseDouble(tSumAdd);
				}
				if (tSumAdd == null || tSumAdd.equals("null")) {
					tSumAdd = "0";
				}

				// 应收保费
				SSRS spSSRS = new SSRS();
				tSql = "select sum(Prem) " + "  from LCPol "
						+ " where ContNo ='" + "?tContNo?" + "'";
				SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
				sqlbv13.sql(tSql);
				sqlbv13.put("tContNo", tContNo);
				spSSRS = exeSql.execSQL(sqlbv13);
				logger.debug("查询应收保费:" + tSql);
				tSPrem = spSSRS.GetText(1, 1);
				strArr[8] = tSPrem;
				logger.debug("应收保费:" + tSPrem);
				// 应收保费合计
				SSRS saSSRS = new SSRS();
				tSql = "select sum(Prem) " + "  from LCPol "
						+ " where ContNo ='" + "?tContNo?" + "'";
				SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
				sqlbv14.sql(tSql);
				sqlbv14.put("tContNo", tContNo);
				saSSRS = exeSql.execSQL(sqlbv14);
				logger.debug("应收保费合计" + tSql);
				String tSumAll = saSSRS.GetText(1, 1);
				if (tSumAll != null && !tSumAll.equals("")
						&& !tSumAll.equals("null")) {
					all = all + Double.parseDouble(tSumAll);
				}
				if (tSumAll == null || tSumAll.equals("null")) {
					tSumAll = "0";
				}

				// 付费方式
				SSRS pmSSRS = new SSRS();
				tSql = "select CodeName "
						+ "  from LDCode"
						+ " where CodeType = 'paymode' "
						+ "   and Code in (select PayMode from LCCont  where ContNo ='"
						+ "?tContNo?" + "')";
				SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
				sqlbv15.sql(tSql);
				sqlbv15.put("tContNo", tContNo);
				pmSSRS = exeSql.execSQL(sqlbv15);
				logger.debug("查询付费方式" + tSql);
				if (pmSSRS.getMaxRow() > 0) {
					tPayMode = pmSSRS.GetText(1, 1);
					strArr[9] = tPayMode;
				}

				// 服务人员代码
				logger.debug("查询服务人员代码");
				tAgentCode = tLCContSchema.getAgentCode();
				strArr[11] = tAgentCode;
				logger.debug("服务人员代码:" + tAgentCode);

				// 服务人员姓名

				SSRS anSSRS = new SSRS();
				tSql = "select Name " + "  from LAAgent"
						+ " where AgentCode = '" + "?tAgentCode?" + "' ";
				SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
				sqlbv16.sql(tSql);
				sqlbv16.put("tAgentCode", tAgentCode);
				anSSRS = exeSql.execSQL(sqlbv16);
				logger.debug("查询服务人员姓名:" + tSql);
				tAgentName = anSSRS.GetText(1, 1);
				strArr[12] = tAgentName;
				logger.debug("服务人员姓名:" + tAgentName);

				// 服务人员所在区部组名称
				logger.debug("查询服务人员所在区部组名称");
				SSRS agSSRS = new SSRS();
				SSRS gnSSRS = new SSRS();
				String tAgentGroup = ""; // 服务人员展业机构
				tSql = "select AgentGroup from LAAgent where AgentCode ='"
						+ "?tAgentCode?" + "'";
				SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
				sqlbv17.sql(tSql);
				sqlbv17.put("tAgentCode", tAgentCode);
				agSSRS = exeSql.execSQL(sqlbv17);
				tAgentGroup = agSSRS.GetText(1, 1);
				tSql = "select getBranchGlobalName('" + tAgentGroup
						+ "') from dual";
				gnSSRS = exeSql.execSQL(tSql);
				tAgentGroupName = gnSSRS.GetText(1, 1);
				strArr[13] = tAgentGroupName;
				logger.debug("服务人员所在区部组名称:" + tAgentGroupName);

				tlistTable.add(strArr);
			}

		// 统计机构
		SSRS nSSRS = new SSRS();
		tSql = "select Name from LDCom where ComCode='" + "?mManageCom?" + "'";
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		sqlbv19.sql(tSql);
		sqlbv19.put("mManageCom", mManageCom);
		nSSRS = exeSql.execSQL(sqlbv19);
		String tManageName = nSSRS.GetText(1, 1);
		logger.debug("getPrintData success");

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("XubaoList.vts", "PRINT"); // 最好紧接着就初始化xml文档

		texttag.add("BQInit", "");
		texttag.add("StartDate", mStartDate);
		texttag.add("EndDate", mEndDate);
		texttag.add("ManageName", tManageName);
		texttag.add("TotalCount", String.valueOf(TotalCount));
		texttag.add("SumMainPrem", String.valueOf(main));
		texttag.add("SumAddPrem", String.valueOf(add));
		texttag.add("SumAllPrem", String.valueOf(all));
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		xmlexport.addListTable(tlistTable, ContractTitle);
		mResult.addElement(xmlexport);
		return true;
	}

	private void jbInit() throws Exception {
	}
}
