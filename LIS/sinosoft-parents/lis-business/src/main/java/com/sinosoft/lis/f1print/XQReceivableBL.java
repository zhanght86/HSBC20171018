package com.sinosoft.lis.f1print;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author yaory
 * @version 6.0
 */

public class XQReceivableBL {
private static Logger logger = Logger.getLogger(XQReceivableBL.class);
	String mManageCom = "";
	String mStartDate = "";
	String mAgentCode = "";
	String mOperation = "";
	String mEndDate = "";
	String mAgent[] = null;

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private String mPayCount = "";
	private VData mResult = new VData();
	private String mSelString = "";
	/** 打印应收清单中直接与数据库进行连接的变量 */
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();

	// private LCPolSchema mLCPolSchema = new LCPolSchema();
	// private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
	// private LOPRTManagerSchema mLOPRTManagerSchema = new
	// LOPRTManagerSchema();
	// private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	// private VData mLOPRTManagerSchemas = new VData();
	// private int mSchemaNum = 0;
	private String flag1 = "1"; // all verify
	private String flag2 = "1"; // all verify
	private TransferData mTransferData = new TransferData();
	private boolean tFlag = false;
	private String mchantypecode = "";

	public XQReceivableBL() {

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

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2005-12-26");
		tTransferData.setNameAndValue("EndDate", "2006-01-25");
		tTransferData.setNameAndValue("ManageCom", "86310001");
		tTransferData.setNameAndValue("Operation", "01");
		tTransferData.setNameAndValue("chantypecode", "2");
		// tTransferData.setNameAndValue("AgentCode","01247202");

		GlobalInput g = new GlobalInput();
		VData tVData = new VData();
		// LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";

		tVData.addElement(tTransferData);
		tVData.add(tG);
		XQReceivableBL u = new XQReceivableBL();
		u.submitData(tVData, "PRINT");
		VData result = new VData();
		result = u.getResult();

	}

	// 得到代理人的管理机构，解决如果管理机构和管理人不对应的时候查不到信息；
	private void getManagecom() {
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("select managecom from laagent where agentcode = '"
				+ "?mAgentCode?" + "'");
		sqlbv1.put("mAgentCode", mAgentCode);
		tSSRS = tExeSQL.execSQL(sqlbv1);
		mManageCom = tSSRS.GetText(1, 1);
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
		// mDay = (String[]) cInputData.get(0);

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 管理机构
		mOperation = (String) mTransferData.getValueByName("Operation");
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		mAgentCode = (String) mTransferData.getValueByName("AgentCode");
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		mchantypecode = (String) mTransferData.getValueByName("chantypecode");
		if (mAgentCode != null && !mAgentCode.equals("null")
				&& !mAgentCode.equals("")) {
			getManagecom(); // 得到代理人的管理机构
		}

		return true;
	}

	private void AgentQuery() {

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql("select agentcode from laagent where managecom like concat('"
				+ "?mManageCom?" + "','%')");
		sqlbv2.put("mManageCom", mManageCom);
		tSSRS = tExeSQL.execSQL(sqlbv2);
		mAgent = new String[tSSRS.getMaxRow()];
		for (int i = 0; i < tSSRS.getMaxRow(); i++) {
			mAgent[i] = tSSRS.GetText(i + 1, 1);
		}
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
		cError.moduleName = "LCPolF1PBL";
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
		double SumMoney = 0;
		int tEFTCount = 0;
		int tServeCount = 0;
		int totalcount = 0;
		String tAgentGroup = "";
		String tAgentName = "";
		String tYear = "";
		String tMonnth = "";
		Date tDate = new Date();
		tYear = mStartDate.substring(0, 4);
		logger.debug("================================================="
				+ mStartDate.substring(5, 7));

		if (mStartDate.substring(5, 7).equals(mEndDate.substring(5, 7))) {
			tMonnth = mStartDate.substring(5, 7);
		} else {
			tMonnth = mEndDate.substring(5, 7);

		}
		ExeSQL exeSql = new ExeSQL();
		SSRS tzuSSRS = new SSRS();
		if (mAgentCode != null && !mAgentCode.equals("null")
				&& !mAgentCode.equals("")) {
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql("select laagent.name from labranchgroup, laagent where (laagent.agentgroup) = (labranchgroup.agentgroup) and (laagent.agentcode)  ='"
					+ "?mAgentCode?" + "'");
			sqlbv3.put("mAgentCode", mAgentCode);
			tzuSSRS = exeSql.execSQL(sqlbv3);
			if (tzuSSRS.MaxRow == 0) {

				tAgentName = "未查到数据";
			} else {

				tAgentName = tzuSSRS.GetText(1, 1);
			}
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			String tSql = "";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSql = "select (select name from labranchgroup where managecom ='"
						+ "?mManageCom?"
						+ "' and branchattr = (select Substr(branchattr,1,4) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
						+ "?mAgentCode?"
						+ "' ))), "
						+ " (select name from labranchgroup where managecom ='"
						+ "?mManageCom?"
						+ "' and branchattr = (select Substr(branchattr,1,7) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
						+ "?mAgentCode?"
						+ "' )) and rownum =1 ), "
						+ "  ((select name from labranchgroup where managecom ='"
						+ "?mManageCom?"
						+ "' and agentgroup = (select branchcode from laagent where agentcode ='"
						+ "?mAgentCode?" + "' ))) from dual ";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSql = "select (select name from labranchgroup where managecom ='"
						+ "?mManageCom?"
						+ "' and branchattr = (select Substr(branchattr,1,4) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
						+ "?mAgentCode?"
						+ "' ))), "
						+ " (select name from labranchgroup where managecom ='"
						+ "?mManageCom?"
						+ "' and branchattr = (select Substr(branchattr,1,7) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
						+ "?mAgentCode?"
						+ "' )) limit 0,1 ), "
						+ "  ((select name from labranchgroup where managecom ='"
						+ "?mManageCom?"
						+ "' and agentgroup = (select branchcode from laagent where agentcode ='"
						+ "?mAgentCode?" + "' ))) from dual ";
			}
			sqlbv4.sql(tSql);
			sqlbv4.put("mManageCom", mManageCom);
			sqlbv4.put("mAgentCode", mAgentCode);
			tzuSSRS = exeSql.execSQL(sqlbv4);
			tAgentGroup = tzuSSRS.GetText(1, 1) + tzuSSRS.GetText(1, 2)
					+ tzuSSRS.GetText(1, 3);

		}

		String[] ContractTitle = new String[32]; // 随意定义的与显示无关
		ContractTitle[0] = "受益人";
		ContractTitle[1] = "证件号";
		ContractTitle[2] = "受益顺序";
		ContractTitle[3] = "受益份额";
		ContractTitle[4] = "受益人";
		ContractTitle[5] = "证件号";
		ContractTitle[6] = "受益顺序";
		ContractTitle[7] = "受益份额";

		ContractTitle[8] = "受益人";
		ContractTitle[9] = "证件号";
		ContractTitle[10] = "受益顺序";
		ContractTitle[11] = "受益份额";
		ContractTitle[12] = "受益顺序";
		ContractTitle[13] = "受益份额";
		ContractTitle[14] = "受益";
		ContractTitle[15] = "受益";
		ContractTitle[16] = "受益";
		ContractTitle[17] = "受益";
		ContractTitle[18] = "受益";
		ContractTitle[19] = "受益";
		ContractTitle[20] = "受益";
		ContractTitle[21] = "受益";
		ContractTitle[22] = "受益";
		ContractTitle[23] = "受益";
		ContractTitle[24] = "受益";
		ContractTitle[25] = "受益";
		ContractTitle[26] = "受益";
		ContractTitle[27] = "受益";
		ContractTitle[28] = "受益";
		ContractTitle[29] = "受益";
		ContractTitle[30] = "受益";
		ContractTitle[31] = "受益";

		logger.debug("程序执行标志＝＝＝＝＝＝" + mOperation);
		int k = 0; // 银行件数
		int j = 0; // 上门件数
		int m = 0; //
		if (mOperation.equals("01")) {
			String strSql = "";
			ExeSQL tExeSQL = new ExeSQL();
			if (mAgentCode != null && !mAgentCode.equals("null")
					&& !mAgentCode.equals("")) {
				strSql = "select a.* " + "  from lcpol a, ljspay b "
						+ " where 1=1 " + "   and a.contno = b.otherno "
						+ "   and a.polno = a.mainpolno "
						+ "   and b.startpaydate >= '" + mStartDate + "' "
						+ "   and b.startpaydate <= '" + mEndDate + "' "
						+ "   and b.agentcode = '" + mAgentCode
						+ "'  and b.sumduepaymoney <> '0' "
						+ " and b.othernotype in ('3','2','8')"
						+ " and exists (select 1 from lmriskapp where mngcom='"
						+ mchantypecode + "' and riskcode=a.riskcode)";
				if (mchantypecode.equals("B")) {
					strSql += " and a.payintv = '12' ";
				}

				strSql += " order by a.managecom, a.agentcode, a.paytodate, a.appntno ";

			} else {
				strSql = "select a.* " + "  from lcpol a, ljspay b "
						+ " where 1=1 " + "   and a.contno = b.otherno "
						+ "   and a.polno = a.mainpolno "
						+ "   and b.startpaydate >= '" + mStartDate + "' "
						+ "   and b.startpaydate <= '" + mEndDate
						+ "'  and b.sumduepaymoney <> '0' "
						+ " and b.othernotype in ('3','2','8')"
						+ " and exists (select 1 from lmriskapp where mngcom='"
						+ mchantypecode + "' and riskcode=a.riskcode)";
				if (mchantypecode.equals("B")) {
					strSql += " and a.payintv = '12' ";
				}

				strSql += "   and b.managecom like '"
						+ mManageCom
						+ "%' "
						+ " order by a.managecom, a.agentcode, a.paytodate, a.appntno ";

			}

			ListTable tlistTable = new ListTable();

			logger.debug(strSql);
			try {
				conn = DBConnPool.getConnection();
				st = conn.createStatement();
				rs = st.executeQuery(strSql);
				int i = 0;
				int tTempCount = 1;
				while (rs.next()) {
					tTempCount++;
					logger.debug("打印数目" + tTempCount);
					if (tTempCount > 4000) {
						rs.close();
						buildError("getPrintData", "由于数据量过大，请提请批量打印");
						return false;
					}
				}

				logger.debug("打印的行数＝＝＝＝＝＝");

				String strArr[] = null;
				tlistTable.setName("RISK");
				// for (int i = 1; i <= tSSRS.MaxRow; i++) {
				rs = st.executeQuery(strSql);
				while (rs.next()) {
					totalcount = totalcount + 1;
					flag1 = "0";
					logger.debug("打印的行数为   *************************      ");
					logger.debug("打印的保单" + rs.getString("ContNo"));
					logger.debug(i++);

					if (mAgentCode != null && !mAgentCode.equals("null")
							&& !mAgentCode.equals("")) {
						strArr = new String[29];

					} else {

						strArr = new String[32];
					}
					String tPaytoDate = "select startpaydate from ljspay where otherno = '"
							+ "?otherno?" + "' ";
					SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
					sqlbv6.sql(tPaytoDate);
					sqlbv6.put("otherno", rs.getString("ContNo").trim());
					SSRS DateSSRS = new SSRS();
					ExeSQL DateExe = new ExeSQL();
					DateSSRS = DateExe.execSQL(sqlbv6);
					strArr[0] = DateSSRS.GetText(1, 1);
					strArr[1] = rs.getString("ContNo").trim();
					// 查询投保人
					// logger.debug("进入实际查询中。。。。。");
					SSRS tempSSRS = new SSRS();
					String sql = "";
					// SSRS tSSRS = new SSRS();
					// strArr[2] = tSSRS.GetText(i, 30);
					strArr[2] = rs.getString("AppntName");
					// 通讯地址 电话 邮编

					sql = "select  (select placename from ldaddress where placetype = '03' and trim(placecode) = b.county)||b.postaladdress, (select GetAppntPhone('"
							+ rs.getString("ContNo").trim()
							+ "','') from dual) ,b.zipcode,b.customerno from  lcaddress b ,lcappnt c "
							+ " where c.appntno = b.customerno and c.addressno =  b.addressno "
							+ " and  c.contno='" + rs.getString("ContNo") + "'";

					tempSSRS = tExeSQL.execSQL(sql);
					if (tempSSRS.MaxRow > 0) {
						strArr[3] = tempSSRS.GetText(1, 1);
						strArr[4] = tempSSRS.GetText(1, 2);
						strArr[5] = tempSSRS.GetText(1, 3);
					} else {
						strArr[3] = "不详";
						strArr[4] = "不详";
						strArr[5] = "不详";
					}
					// 主险保费 附险保费

					sql = "select sum(a.SumActuPayMoney) from ljspayperson a, lmriskapp b, lcpol d where 1=1 "
							+ "and a.riskcode=b.riskcode and d.riskcode=b.riskcode  and b.mngcom = '"
							+ "?mchantypecode?"
							+ "' and a.contno = '"
							+ "?contno?"
							+ "' "
							+ " and a.polno = d.polno and a.contno = d.contno and d.polno = d.mainpolno "; // lihuan加
					SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
					sqlbv8.sql(sql);
					sqlbv8.put("mchantypecode", mchantypecode);
					sqlbv8.put("contno", rs.getString("ContNo"));
					tempSSRS = tExeSQL.execSQL(sqlbv8);

					strArr[6] = tempSSRS.GetText(1, 1);

					if (strArr[6] != null && !strArr[6].equals("null")) {

					} else {
						strArr[6] = "0";
					}
					sql = " select sum(a.SumActuPayMoney) from ljspayperson a, lmriskapp b, lcpol d where 1=1  "
							+ " and a.riskcode=b.riskcode and d.riskcode=b.riskcode  and b.mngcom = '"
							+ "?mchantypecode?"
							+ "'  and a.contno = '"
							+ "?contno?"
							+ "' "
							+ " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno ";
					SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
					sqlbv9.sql(sql);
					sqlbv9.put("mchantypecode", mchantypecode);
					sqlbv9.put("contno", rs.getString("ContNo"));
					tempSSRS = tExeSQL.execSQL(sqlbv9);
					if (tempSSRS.MaxRow != 0 && tempSSRS.GetText(1, 1) != null
							&& !tempSSRS.GetText(1, 1).equals("null")) {
						strArr[7] = tempSSRS.GetText(1, 1);

					} else {
						strArr[7] = "0";

					}

					// 健康加费
					sql = "select sum(prem) from lcprem where (payplantype ='01' or payplantype='11') and contno='"
							+ "?contno?" + "' group by contno";
					SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
					sqlbv10.sql(sql);
					sqlbv10.put("contno", rs.getString("ContNo"));
					tempSSRS = tExeSQL.execSQL(sqlbv10);
					if (tempSSRS.MaxRow > 0 && tempSSRS != null
							&& !tempSSRS.GetText(1, 1).equals("null")) {
						strArr[8] = tempSSRS.GetText(1, 1);

					} else {
						strArr[8] = "0";

					}
					// 职业加费
					sql = "select sum(prem) from lcprem where (payplantype='02' or payplantype='12') and contno='"
							+ "?contno?" + "' group by contno";
					SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
					sqlbv11.sql(sql);
					sqlbv11.put("contno", rs.getString("ContNo"));
					tempSSRS = tExeSQL.execSQL(sqlbv11);
					if (tempSSRS.MaxRow > 0 && tempSSRS != null
							&& !tempSSRS.GetText(1, 1).equals("null")) {
						strArr[9] = tempSSRS.GetText(1, 1);

					} else {
						strArr[9] = "0";

					}
					// 应收保费
					sql = "select sumduepaymoney,bankaccno,PayDate from ljspay where otherno='"
							+ "?otherno?" + "'";
					SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
					sqlbv12.sql(sql);
					sqlbv12.put("otherno", rs.getString("ContNo"));
					tempSSRS = tExeSQL.execSQL(sqlbv12);
					String bankno = "";
					if (tempSSRS.MaxRow > 0) {
						// strArr[0] = tempSSRS.GetText(1, 3);
						strArr[10] = tempSSRS.GetText(1, 1);
						SumMoney = SumMoney
								+ parseFloat(tempSSRS.GetText(1, 1));
						bankno = tempSSRS.GetText(1, 2);

					} else {
						// strArr[0] = "未录入";
						strArr[10] = "0";

					}
					// 缴费年期 缴费形式
					// String tempgetpayendyear = tSSRS.GetText(i, 44);
					String tempgetpayendyear = rs.getString("PayYears");
					String templocation = "";

					String payintv = "";
					String payendyear = "";
					if (tempgetpayendyear.equals("1000")) {
						payendyear = "终身";
					} else {
						payendyear = tempgetpayendyear;
					}

					sql = "select paylocation,InsuredName from lccont where contno='"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
					sqlbv13.sql(sql);
					sqlbv13.put("contno", rs.getString("ContNo"));
					tempSSRS = tExeSQL.execSQL(sqlbv13);
					if (tempSSRS.MaxRow == 0) {

					} else {
						templocation = tempSSRS.GetText(1, 1);
					}

					if (templocation.equals("0")) {
						payintv = "银行";
						k = k + 1;
					} else if (templocation.equals("1")) {
						payintv = "柜台";
						m = m + 1;
					}

					else {
						payintv = "上门";
						j = j + 1;
					}
					strArr[11] = payendyear;
					strArr[12] = payintv;

					// 账号
					strArr[13] = bankno;
					// 被保险人
					strArr[14] = tempSSRS.GetText(1, 2);
					SSRS tMainPol = new SSRS();
					SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
					sqlbv14.sql("select riskcode from lcpol where  polno = mainpolno and contno = '"
							+ "?contno?" + "'");
					sqlbv14.put("contno", rs.getString("ContNo"));
					tMainPol = tExeSQL.execSQL(sqlbv14);
					// 主险险种代码
					strArr[15] = tMainPol.GetText(1, 1);
					// 交费次数
					SQLwithBindVariables sqlbv = new SQLwithBindVariables();
					sqlbv.sql("select max(paycount) from ljapayperson where contno = '"
							+ "?contno?" + "'");
					sqlbv.put("contno", rs.getString("ContNo"));
					tMainPol = tExeSQL
							.execSQL(sqlbv);
					strArr[16] = String.valueOf(Integer.parseInt(tMainPol
							.GetText(1, 1)) + 1);
					// 上年度服务人员'HB210422361000260'
					SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
					sqlbv15.sql("select s.name from ljapayperson a, laagent s where trim(a.agentcode) = s.agentcode and  a.paycount =   ("
							+ " select max(paycount) from ljapayperson where contno = '"
							+ "?contno?"
							+ "')    "
							+ " and a.contno = '"
							+ "?contno?" + "'");
					sqlbv15.put("contno", rs.getString("ContNo"));
					tMainPol = tExeSQL.execSQL(sqlbv15);
					strArr[17] = tMainPol.GetText(1, 1);

					strArr[18] = "未收";
					ExeSQL tBExeSQL = new ExeSQL();
					SSRS tBSSRS = new SSRS();
					// /*投保人性别*/strArr[19] 应收清单的，应收未收
					String tSql = "select codename  from ldcode a,lcappnt b "
							+ " where a.codetype = 'sex' and a.code = b.appntsex"
							+ " and contno = '" + "?contno?" + "'";
					SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
					sqlbv16.sql(tSql);
					sqlbv16.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv16);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[19] = tBSSRS.GetText(1, 1);
					} else {
						strArr[19] = "暂无";
					}
					// /*服务人员起期*/strArr[20]
					tSql = "select StartSerDate  from LACommisionDetail a,lccont b "
							+ "where a.grpcontno = b.contno   and a.agentcode = trim(b.agentcode) "
							+ " and contno = '" + "?contno?" + "'";
					SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
					sqlbv17.sql(tSql);
					sqlbv17.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv17);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[20] = tBSSRS.GetText(1, 1);
					} else {
						strArr[20] = "暂无";
					}

					// /*投保人与被保人关系*/strArr[21]
					tSql = "select codename  from ldcode "
							+ "where codetype = 'relation'   and code in "
							+ "(select RelationToAppnt from lcinsured where contno = '"
							+ "?contno?" + "')";
					SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
					sqlbv18.sql(tSql);
					sqlbv18.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv18);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[21] = tBSSRS.GetText(1, 1);
					} else {
						strArr[21] = "暂无";
					}

					// /*投保人与业务员关系-- 当前为空*/strArr[22]

					tSql = "select codename  from ldcode "
							+ "where codetype = 'agentrelatoappnt' and code in "
							+ " (select relationship "
							+ "   from lacommisiondetail "
							+ "  where relationship is not null "
							+ "    and grpcontno = '" + "?grpcontno?"
							+ "' " + " union " + " select relationship "
							+ "   from lacommisiondetailb "
							+ "  where relationship is not null "
							+ "    and grpcontno = '" + "?grpcontno?"
							+ "')";
					SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
					sqlbv19.sql(tSql);
					sqlbv19.put("grpcontno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv19);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[22] = tBSSRS.GetText(1, 1);
					} else {
						strArr[22] = "暂无";

					}

					// /*被保人生日*/strArr[23]
					tSql = "select birthday from  lcinsured where contno = '"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
					sqlbv20.sql(tSql);
					sqlbv20.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv20);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[23] = tBSSRS.GetText(1, 1);
					} else {
						strArr[23] = "暂无";
					}

					// /*被保人电话*/strArr[24]
					tSql = "select phone  from lcaddress "
							+ "where customerno in "
							+ "( select insuredno from lcinsured where contno='"
							+ "?contno?"
							+ "' and sequenceno = 1 and addressno = lcaddress.addressno)";
					SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
					sqlbv21.sql(tSql);
					sqlbv21.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv21);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[24] = tBSSRS.GetText(1, 1);
					} else {
						strArr[24] = "暂无";
					}

					// /*投保人身份证号*/strArr[25]
					tSql = "select idno from lcappnt where contno = '"
							+ "?contno?" + "' and idtype = '0'";
					SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
					sqlbv22.sql(tSql);
					sqlbv22.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv22);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[25] = tBSSRS.GetText(1, 1);
					} else {
						strArr[25] = "暂无";
					}

					// /*投保人工作单位*/strArr[26]
					tSql = "select a.companyaddress from lcaddress a , lcappnt b "
							+ "where b.appntno = a.customerno and a.addressno = b.addressno "
							+ "and b.contno = '" + "?contno?" + "'";
					SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
					sqlbv23.sql(tSql);
					sqlbv23.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv23);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[26] = tBSSRS.GetText(1, 1).replaceAll("<", "(")
								.replaceAll(">", ")");
					} else {
						strArr[26] = "暂无";
					}

					// /*主险保额*/strArr[27]
					tSql = "select amnt  from lcpol where   polno = mainpolno and contno = '"
							+ "?contno?" + "'  ";
					SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
					sqlbv24.sql(tSql);
					sqlbv24.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv24);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[27] = tBSSRS.GetText(1, 1);
					} else {
						strArr[27] = "暂无";
					}

					// /*附加险保额*/strArr[28]
					tSql = "select sum(amnt)  from lcpol where   polno <> mainpolno and contno = '"
							+ "?contno?" + "' and appflag = '1' ";
					SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
					sqlbv25.sql(tSql);
					sqlbv25.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv25);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[28] = tBSSRS.GetText(1, 1);
					} else {
						strArr[28] = "暂无";
					}

					if (mAgentCode != null && !mAgentCode.equals("null")
							&& !mAgentCode.equals("")) {
					} else {
						sql = "select a.name , c.agentcode , a.managecom,c.contno from laagent a,lccont c where trim(c.agentcode) = (a.agentcode) and (c.contno) = '"
								+ "?contno?" + "' ";
						SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
						sqlbv26.sql(tSql);
						sqlbv26.put("contno", rs.getString("ContNo"));
						tempSSRS = tExeSQL.execSQL(sqlbv26);

						strArr[29] = tempSSRS.GetText(1, 1);
						strArr[30] = tempSSRS.GetText(1, 2);
						tempSSRS = tExeSQL.execSQL("select getGlobalBranch('"
								+ tempSSRS.GetText(1, 2) + "','"
								+ tempSSRS.GetText(1, 4) + "') from dual ");
						String tAgentManage = tempSSRS.GetText(1, 1);

						strArr[31] = tAgentManage;

					}

					tlistTable.add(strArr);

				}

				// 已收**************************************************************
				// *****************************************************************
				// ****************************************************************
				if (mAgentCode != null && !mAgentCode.equals("null")
						&& !mAgentCode.equals("")) {
					strSql = "select * "
							+ "from lcpol a "
							+ "where  contno in "
							+ "(select b.otherno "
							+ "from ljapay b, ljapayperson c "
							+ "where b.getnoticeno = c.getnoticeno "
							+ "and c.LastPayToDate >= '"
							+ mStartDate
							+ "' "
							+ "and c.LastPayToDate <=  '"
							+ mEndDate
							+ "' "
							+ "and b.managecom like '"
							+ mManageCom
							+ "%' and c.managecom like '"
							+ mManageCom
							+ "%' "
							+ "and exists (select 1 from lmriskapp where mngcom='"
							+ mchantypecode
							+ "' and riskcode=c.riskcode)"
							+ " and   b.sumactupaymoney <> '0' ) "
							+ "and a.polno = a.mainpolno "
							+ "and exists (select 1 from lmriskapp where mngcom='"
							+ mchantypecode + "' and riskcode=a.riskcode)"
							+ "and a.agentcode = '" + mAgentCode + "'";
					if (mchantypecode.equals("B")) {
						strSql += " and a.payintv = '12' ";
					}
					strSql += "and a.managecom like '"
							+ mManageCom
							+ "%' "
							+ "order by managecom, agentcode, paytodate, appntno ";

				} else {
					strSql = "select * "
							+ "from lcpol a "
							+ "where  contno in "
							+ "(select b.otherno "
							+ "from ljapay b, ljapayperson c "
							+ "where b.getnoticeno = c.getnoticeno "
							+ "and c.LastPayToDate >= '"
							+ mStartDate
							+ "' "
							+ "and c.LastPayToDate <= '"
							+ mEndDate
							+ "'"
							+ "and b.managecom like '"
							+ mManageCom
							+ "%' and c.managecom like '"
							+ mManageCom
							+ "%' "
							+ "and exists (select 1 from lmriskapp where mngcom='"
							+ mchantypecode
							+ "' and riskcode=c.riskcode)"
							+ " and   b.sumactupaymoney <> '0' ) "
							+ "and exists (select 1 from lmriskapp where mngcom='"
							+ mchantypecode + "' and riskcode=a.riskcode)"
							+ "and a.polno = a.mainpolno  ";
					if (mchantypecode.equals("B")) {
						strSql += " and a.payintv = '12' ";
					}

					strSql += "and a.managecom like '"
							+ mManageCom
							+ "%' "
							+ "order by managecom, agentcode, paytodate, appntno ";

				}

				rs = st.executeQuery(strSql);

				logger.debug("打印的行数＝＝＝＝＝＝");

				tlistTable.setName("RISK");
				while (rs.next()) {
					flag2 = "0";
					totalcount = totalcount + 1;
					logger.debug("打印的行数为   *************************      ");
					logger.debug("实收打印的保单" + rs.getString("ContNo"));
					logger.debug(i++);

					SSRS tswd = new SSRS();
					SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
					sqlbv28.sql("select lastpaytodate  from ljapayperson where paycount in (select max(paycount) from ljapayperson  where contno = '"
							+ "?contno?"
							+ "') and contno = '"
							+ "?contno?" + "'");
					sqlbv28.put("contno", rs.getString("ContNo"));
					tswd = tExeSQL.execSQL(sqlbv28);
					if (mAgentCode != null && !mAgentCode.equals("null")
							&& !mAgentCode.equals("")) {
						strArr = new String[29];

					} else {

						strArr = new String[32];

					}

					strArr[0] = tswd.GetText(1, 1);
					strArr[1] = rs.getString("ContNo").trim();
					// 查询投保人
					// logger.debug("进入实际查询中。。。。。");
					SSRS tempSSRS = new SSRS();
					String sql = "";

					strArr[2] = rs.getString("AppntName");
					// 通讯地址 电话 邮编
					sql = "select  (select placename from ldaddress where placetype = '03' and trim(placecode) = b.county)||b.postaladdress, (select GetAppntPhone('"
							+ rs.getString("ContNo").trim()
							+ "','') from dual) ,b.zipcode,b.customerno from  lcaddress b ,lcappnt c "
							+ " where c.appntno = b.customerno and c.addressno =  b.addressno "
							+ " and  c.contno='" + rs.getString("ContNo") + "'";

					tempSSRS = tExeSQL.execSQL(sql);
					if (tempSSRS.MaxRow > 0) {
						strArr[3] = tempSSRS.GetText(1, 1);
						strArr[4] = tempSSRS.GetText(1, 2);
						strArr[5] = tempSSRS.GetText(1, 3);
					} else {
						strArr[3] = "不详";
						strArr[4] = "不详";
						strArr[5] = "不详";

					}
					// 主险保费 附险保费
					SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
					sqlbv30.sql("select sum(a.SumActuPayMoney) from ljapayperson a, ljapay b, lcpol d where b.otherno = a.contno "
							+ "and a.GetNoticeNo = b.GetNoticeNo and a.contno = '"
							+ "?contno?"
							+ "' "
							+ " and a.polno = d.polno and a.contno = d.contno and d.polno = d.mainpolno  and a.confdate in (select max(confdate) from ljapayperson where contno = '"
							+ "?contno?"
							+ "'  and paytype = 'ZC')");
					sqlbv30.put("contno", rs.getString("ContNo"));
					tzuSSRS = exeSql.execSQL(sqlbv30);

					strArr[6] = tzuSSRS.GetText(1, 1);
					if (strArr[6] != null && !strArr[6].equals("null")) {

					} else {
						strArr[6] = "0";
					}
					SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
					sqlbv31.sql(" select sum(a.SumActuPayMoney) from ljspayperson a, ljspay b, lcpol d where b.otherno = a.contno  "
							+ " and a.GetNoticeNo = b.GetNoticeNo  and a.contno = '"
							+ "?contno?"
							+ "' "
							+ " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno ");
					sqlbv31.put("contno", rs.getString("ContNo"));
					tzuSSRS = exeSql.execSQL(sqlbv31); // lihuan加
					if (tzuSSRS.MaxRow != 0 && tzuSSRS.GetText(1, 1) != null
							&& !tzuSSRS.GetText(1, 1).equals("null")) {
						strArr[7] = tzuSSRS.GetText(1, 1);
					}

					else {
						SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
						sqlbv32.sql(" select sum(a.SumActuPayMoney) from ljapayperson a, ljapay b, lcpol d where b.otherno = a.contno  "
								+ " and a.GetNoticeNo = b.GetNoticeNo  and a.contno = '"
								+ "?contno?"
								+ "' "
								+ " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno and a.confdate in (select max(confdate) from ljapayperson where contno = '"
								+ "?contno?"
								+ "'  and paytype = 'ZC')");
						sqlbv32.put("", rs.getString("ContNo"));
						tzuSSRS = exeSql.execSQL(sqlbv32);

						if (tzuSSRS.MaxRow != 0
								&& tzuSSRS.GetText(1, 1) != null
								&& !tzuSSRS.GetText(1, 1).equals("null")) {

							strArr[7] = tzuSSRS.GetText(1, 1);

						} else {
							strArr[7] = "0";

						}

					}

					// 健康加费
					sql = "select sum(prem) from lcprem where (payplantype ='01' or payplantype='11') and contno='"
							+ "?contno?" + "' ";
					SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
					sqlbv33.sql(sql);
					sqlbv33.put("contno", rs.getString("ContNo"));
					tempSSRS = tExeSQL.execSQL(sqlbv33);
					if (tempSSRS.MaxRow > 0 && tempSSRS != null
							&& !tempSSRS.GetText(1, 1).equals("null")) {
						strArr[8] = tempSSRS.GetText(1, 1);

					} else {
						strArr[8] = "0";

					}
					// 职业加费
					sql = "select sum(prem) from lcprem where (payplantype='02' or payplantype='12') and contno='"
							+ "?contno?" + "' ";
					SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
					sqlbv34.sql(sql);
					sqlbv34.put("contno", rs.getString("ContNo"));
					tempSSRS = tExeSQL.execSQL(sqlbv34);
					if (tempSSRS.MaxRow > 0 && tempSSRS != null
							&& !tempSSRS.GetText(1, 1).equals("null")) {
						strArr[9] = tempSSRS.GetText(1, 1);

					} else {
						strArr[9] = "0";

					}
					// 实收保费
					sql = "select SumActuPayMoney,BankAccNo,PayDate from ljapay where otherno='"
							+ "?contno?"
							+ "'  and  confdate in (select max(confdate) from ljapayperson where contno = '"
							+ "?contno?" + "' and paytype = 'ZC')";
					SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
					sqlbv35.sql(sql);
					sqlbv35.put("contno", rs.getString("ContNo"));
					tempSSRS = tExeSQL.execSQL(sqlbv35);
					String bankno = "";
					if (tempSSRS.MaxRow > 0) {
						strArr[10] = tempSSRS.GetText(1, 1);
						// strArr[0] = tempSSRS.GetText(1, 3);
						SumMoney = SumMoney + parseFloat(strArr[6])
								+ parseFloat(strArr[7]);
						bankno = tempSSRS.GetText(1, 2);

					} else {
						// strArr[0] = "未录入";
						strArr[10] = "0";

					}
					// 缴费年期 缴费形式
					String tempgetpayendyear = rs.getString("PayYears");
					String templocation = "";

					String payintv = "";
					String payendyear = "";
					if (tempgetpayendyear.equals("1000")) {
						payendyear = "终身";
					} else {
						payendyear = tempgetpayendyear;
					}

					sql = "select paylocation , InsuredName from lccont where contno='"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
					sqlbv36.sql(sql);
					sqlbv36.put("contno", rs.getString("ContNo"));
					tempSSRS = tExeSQL.execSQL(sqlbv36);
					if (tempSSRS.MaxRow == 0) {

					} else {
						templocation = tempSSRS.GetText(1, 1);
					}

					if (templocation.equals("0")) {
						payintv = "银行";
						k = k + 1;
					} else if (templocation.equals("1")) {
						payintv = "柜台";
						m = m + 1;
					}

					else {
						payintv = "上门";
						j = j + 1;
					}
					strArr[11] = payendyear;
					strArr[12] = payintv;

					// 账号
					strArr[13] = bankno;
					// 被保险人
					strArr[14] = tempSSRS.GetText(1, 2);
					SSRS tMainPol = new SSRS();
					SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
					sqlbv37.sql("select riskcode from lcpol where  polno = mainpolno and contno = '"
							+ "?contno?" + "'");
					sqlbv37.put("contno", rs.getString("ContNo"));
					tMainPol = tExeSQL.execSQL(sqlbv37);
					// 主险险种代码
					strArr[15] = tMainPol.GetText(1, 1);
					// 交费次数
					SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
					sqlbv38.sql("select max(paycount) from ljapayperson where contno = '"
							+ "?contno?" + "'");
					sqlbv38.put("contno", rs.getString("ContNo"));
					tMainPol = tExeSQL.execSQL(sqlbv38);
					strArr[16] = String.valueOf(Integer.parseInt(tMainPol
							.GetText(1, 1)));
					// 上年度服务人员'HB210422361000260'
					SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
					sqlbv39.sql("select s.name from ljapayperson a, laagent s where trim(a.agentcode) = s.agentcode and  a.paycount =   ("
							+ " select max(paycount)-1 from ljapayperson where contno = '"
							+ "?contno?"
							+ "')    "
							+ " and a.contno = '"
							+ "?contno?" + "'");
					sqlbv39.put("contno", rs.getString("ContNo"));
					tMainPol = tExeSQL.execSQL(sqlbv39);
					strArr[17] = tMainPol.GetText(1, 1);

					strArr[18] = "已收";
					ExeSQL tBExeSQL = new ExeSQL();
					SSRS tBSSRS = new SSRS();
					// /*投保人性别*/strArr[19] 应收清单的，应收未收
					String tSql = "select codename  from ldcode a,lcappnt b "
							+ " where a.codetype = 'sex' and a.code = b.appntsex"
							+ " and contno = '" + "?contno?" + "'";
					SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
					sqlbv40.sql(tSql);
					sqlbv40.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv40);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[19] = tBSSRS.GetText(1, 1);
					} else {
						strArr[19] = "暂无";
					}
					// /*服务人员起期*/strArr[20]
					tSql = "select StartSerDate  from LACommisionDetail a,lccont b "
							+ "where a.grpcontno = b.contno   and a.agentcode = trim (b.agentcode) "
							+ " and contno = '" + "?contno?" + "'";
					SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
					sqlbv41.sql(tSql);
					sqlbv41.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv41);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[20] = tBSSRS.GetText(1, 1);
					} else {
						strArr[20] = "暂无";
					}

					// /*投保人与被保人关系*/strArr[21]
					tSql = "select codename  from ldcode "
							+ "where codetype = 'relation'   and code in "
							+ "(select RelationToAppnt from lcinsured where contno = '"
							+ "?contno?" + "')";
					SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
					sqlbv42.sql(tSql);
					sqlbv42.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv42);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[21] = tBSSRS.GetText(1, 1);
					} else {
						strArr[21] = "暂无";
					}

					// /*投保人与业务员关系-- 当前为空*/strArr[22]
					tSql = "select codename  from ldcode "
							+ "where codetype = 'agentrelatoappnt' and code in "
							+ " (select relationship "
							+ "   from lacommisiondetail "
							+ "  where relationship is not null "
							+ "    and grpcontno = '" + "?grpcontno?"
							+ "' " + " union " + " select relationship "
							+ "   from lacommisiondetailb "
							+ "  where relationship is not null "
							+ "    and grpcontno = '" + "?grpcontno?"
							+ "')";
					SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
					sqlbv43.sql(tSql);
					sqlbv43.put("grpcontno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv43);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[22] = tBSSRS.GetText(1, 1);
					} else {
						strArr[22] = "暂无";

					}

					// /*被保人生日*/strArr[23]
					tSql = "select birthday from  lcinsured where contno = '"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
					sqlbv44.sql(tSql);
					sqlbv44.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv44);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[23] = tBSSRS.GetText(1, 1);
					} else {
						strArr[23] = "暂无";
					}

					// /*被保人电话*/strArr[24]
					tSql = "select phone  from lcaddress "
							+ "where customerno in "
							+ "( select insuredno from lcinsured where contno='"
							+ "?contno?"
							+ "' and sequenceno = 1 and addressno = lcaddress.addressno)";
					SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
					sqlbv45.sql(tSql);
					sqlbv45.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv45);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[24] = tBSSRS.GetText(1, 1);
					} else {
						strArr[24] = "暂无";
					}

					// /*投保人身份证号*/strArr[25]
					tSql = "select idno from lcappnt where contno = '"
							+ "?contno?" + "' and idtype = '0'";
					SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
					sqlbv46.sql(tSql);
					sqlbv46.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv46);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[25] = tBSSRS.GetText(1, 1);
					} else {
						strArr[25] = "暂无";
					}

					// /*投保人工作单位*/strArr[26]
					tSql = "select a.companyaddress from lcaddress a , lis.lcappnt b "
							+ "where b.appntno = a.customerno and a.addressno = b.addressno "
							+ "and b.contno = '" + "?contno?" + "'";
					SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
					sqlbv47.sql(tSql);
					sqlbv47.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv47);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[26] = tBSSRS.GetText(1, 1).replaceAll("<", "(")
								.replaceAll(">", ")");
					} else {
						strArr[26] = "暂无";
					}

					// /*主险保额*/strArr[27]
					tSql = "select amnt  from lcpol where   polno = mainpolno and contno = '"
							+ "?contno?" + "'  ";
					SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
					sqlbv48.sql(tSql);
					sqlbv48.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv48);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[27] = tBSSRS.GetText(1, 1);
					} else {
						strArr[27] = "暂无";
					}

					// /*附加险保额*/strArr[28]
					tSql = "select sum(amnt)  from lcpol where   polno <> mainpolno and contno = '"
							+ "?contno?" + "' and appflag = '1' ";
					SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
					sqlbv49.sql(tSql);
					sqlbv49.put("contno", rs.getString("ContNo"));
					tBSSRS = tBExeSQL.execSQL(sqlbv49);
					if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
							&& !tBSSRS.GetText(1, 1).equals("null")
							&& !tBSSRS.GetText(1, 1).equals("")) {
						strArr[28] = tBSSRS.GetText(1, 1);
					} else {
						strArr[28] = "暂无";
					}

					if (mAgentCode != null && !mAgentCode.equals("null")
							&& !mAgentCode.equals("")) {
					} else {
						// sql = "select a.name , c.agentcode , a.managecom from
						// laagent a,lccont c where trim(c.agentcode) =
						// (a.agentcode) and (c.contno) = '" +
						// rs.getString("ContNo") + "' ";
						// tempSSRS = tExeSQL.execSQL(sql);
						//
						// strArr[29] = tempSSRS.GetText(1, 1);
						// strArr[30] = tempSSRS.GetText(1, 2);
						// tempSSRS = tExeSQL.execSQL(
						// "select (select name from labranchgroup where
						// managecom ='" +
						// tempSSRS.GetText(1, 3) + "' and branchattr = (select
						// Substr(branchattr,0,4) from labranchgroup where
						// agentgroup = (select branchcode from laagent where
						// agentcode ='" +
						// tempSSRS.GetText(1, 2) + "' ))), "
						// +
						// " (select name from labranchgroup where managecom ='"
						// +
						// tempSSRS.GetText(1, 3) + "' and branchattr = (select
						// Substr(branchattr,0,7) from labranchgroup where
						// agentgroup = (select branchcode from laagent where
						// agentcode ='" +
						// tempSSRS.GetText(1, 2) + "' ))), "
						// +
						// " ((select name from labranchgroup where managecom
						// ='" +
						// tempSSRS.GetText(1, 3) +
						// "' and agentgroup = (select branchcode from laagent
						// where agentcode ='" +
						// tempSSRS.GetText(1, 2) + "' ))) from dual ");
						// String tAgentManage = tempSSRS.GetText(1, 1) +
						// tempSSRS.GetText(1, 2) +
						// tempSSRS.GetText(1, 3);
						sql = "select a.name , c.agentcode , a.managecom,c.contno from laagent a,lccont c where trim(c.agentcode) = (a.agentcode) and (c.contno) = '"
								+ "?contno?" + "' ";
						SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
						sqlbv50.sql(sql);
						sqlbv50.put("contno", rs.getString("ContNo"));
						tempSSRS = tExeSQL.execSQL(sqlbv50);

						strArr[29] = tempSSRS.GetText(1, 1);
						strArr[30] = tempSSRS.GetText(1, 2);
						tempSSRS = tExeSQL.execSQL("select getGlobalBranch('"
								+ tempSSRS.GetText(1, 2) + "','"
								+ tempSSRS.GetText(1, 4) + "') from dual ");
						String tAgentManage = tempSSRS.GetText(1, 1);

						strArr[31] = tAgentManage;
					}

					tlistTable.add(strArr);
				}
				if (flag1.equals("1") && flag2.equals("1")) {
					rs.close();
					buildError("getPrintData", "没有要打印的数据！！");
					return false;
				}

				rs.close();
				st.close();
				conn.close();

			} catch (Exception e) {
				e.printStackTrace();
				try {
					rs.close();
					st.close();
					conn.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				e.getMessage();
			}

			// new DecimalFormat("0.00").format(Double.
			// parseDouble(SumMoney));
			String tSumMoney = new DecimalFormat("0.00").format(SumMoney);
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			if (mAgentCode != null && !mAgentCode.equals("null")
					&& !mAgentCode.equals("")) {
				xmlexport.createDocument("Yslist.vts", "PRINT");

			} else {

				xmlexport.createDocument("MYslist.vts", "PRINT");

			}

			// 最好紧接着就初始化xml文档
			texttag.add("RiskFee", tSumMoney);
			texttag.add("Count", totalcount);
			texttag.add("cCount", m);
			texttag.add("EFTCount", k);
			texttag.add("ServeCount", j);
			texttag.add("LCCont.AgentCode", mAgentCode);
			texttag.add("LAAgent.Name", tAgentName);
			texttag.add("Year", tYear);
			texttag.add("Month", tMonnth);
			// texttag.add("type", "应收清单");
			texttag.add("Name", tAgentGroup);
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			xmlexport.addListTable(tlistTable, ContractTitle);
			// xmlexport.outputDocumentToFile("e:\\", "atest"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);

		}

		if (mOperation.equals("02")) {
			String strSql = "";
			// and agentcode = '" + mAgentCode + "'
			if (mAgentCode != null && !mAgentCode.equals("null")
					&& !mAgentCode.equals("")) {
				// strSql = "select * from lcpol a, ljspayperson b " +
				// //"(select contno from ljspayperson b " +
				// " where b.contno = a.contno " +
				// " and b.LastPayToDate >= '" + mStartDate +
				// "' and b.LastPayToDate <= '" + mEndDate + "' " +
				// "and b.paycount > 1 and b.managecom like '" +
				// mManageCom + "%' " +
				// "and exists (select 'X' from ljspay c where c.othernotype =
				// '2' " +
				// "and b.getnoticeno = c.getnoticeno) and a.polno = a.mainpolno
				// and a.agentcode = '" +
				// mAgentCode + "'" +
				// " order by a.managecom, a.agentcode, a.paytodate, a.appntno
				// ";
				strSql = "select a.* "
						+ "  from lcpol a, ljspay b "
						+ " where 1=1 "
						+ "   and exists (select 1 from lmriskapp where mngcom='"
						+ "?mchantypecode?" + "' and riskcode=a.riskcode)"
						+ "   and a.contno = b.otherno "
						+ "   and a.polno = a.mainpolno "
						+ "   and b.startpaydate >= '" + "?mStartDate?" + "' "
						+ "   and b.startpaydate <= '" + "?mEndDate?" + "' "
						+ "   and b.agentcode = '" + "?mAgentCode?"
						+ "'  and b.sumduepaymoney <> '0' "
						+ " and b.othernotype in ('3','2','8')";
				if (mchantypecode.equals("B")) {
					strSql += " and a.payintv = '12' ";
				}

				strSql += " order by a.managecom, a.agentcode, a.paytodate, a.appntno ";

			} else {

				// strSql = "select a.* " +
				// "from lcpol a,ljspayperson b " +
				// "where b.contno = a.contno " +
				// " and b.LastPayToDate >= '" + mStartDate +"' " +
				// " and b.LastPayToDate <= '" + mEndDate + "' " +
				// " and b.paycount > 1 " +
				// " and b.managecom like '" +mManageCom + "%'" +
				// " and exists (select 'X' " +
				// " from ljspay c " +
				// " where c.othernotype = '2' " +
				// " and b.getnoticeno = c.getnoticeno) " +
				// "and a.polno = a.mainpolno " +
				// " and a.managecom like '" +mManageCom + "%' "
				// + "order by a.managecom, a.agentcode, a.paytodate, a.appntno
				// ";
				strSql = "select a.* "
						+ "  from lcpol a, ljspay b "
						+ " where 1=1 "
						+ "   and exists (select 1 from lmriskapp where mngcom='"
						+ "?mchantypecode?" + "' and riskcode=a.riskcode)"
						+ "   and a.contno = b.otherno "
						+ "   and a.polno = a.mainpolno "
						+ " and othernotype in ('3','2','8')"
						+ "   and b.startpaydate >= '" + "?mStartDate?" + "' "
						+ "   and b.startpaydate <= '" + "?mEndDate?"
						+ "'  and b.sumduepaymoney <> '0' ";
				if (mchantypecode.equals("B")) {
					strSql += " and a.payintv = '12' ";
				}

				strSql += "   and b.managecom like concat('"
						+ "?mManageCom?"
						+ "','%') "
						+ " order by a.managecom, a.agentcode, a.paytodate, a.appntno ";

			}
			SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
			sqlbv52.sql(strSql);
			sqlbv52.put("mchantypecode", mchantypecode);
			sqlbv52.put("mStartDate", mStartDate);
			sqlbv52.put("mEndDate", mEndDate);
			sqlbv52.put("mAgentCode", mAgentCode);
			sqlbv52.put("mManageCom", mManageCom);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv52);

			totalcount = totalcount + tSSRS.getMaxRow();
			if (tSSRS.getMaxRow() == 0) {
				mErrors.copyAllErrors(tSSRS.mErrors);
				buildError("getPrintData", "没有要打印的数据！！");
				return false;
			}
			if (tSSRS.getMaxRow() > 4000) {
				mErrors.copyAllErrors(tSSRS.mErrors);
				buildError("getPrintData", "打印数据过大，请提请批量打印！");
				return false;
			}

			logger.debug("打印的行数＝＝＝＝＝＝" + tSSRS.MaxRow);
			ListTable tlistTable = new ListTable();
			String strArr[] = null;
			tlistTable.setName("RISK");
			for (int i = 1; i <= tSSRS.MaxRow; i++) {

				if (mAgentCode != null && !mAgentCode.equals("null")
						&& !mAgentCode.equals("")) {

					strArr = new String[28];

				} else {

					strArr = new String[31];
				}

				String tPaytoDate = "select (case when startpaydate is not null then startpaydate else to_date('1900-01-01','yyyy-mm-dd') end) from ljspay where otherno = '"
						+ "?otherno?" + "' ";
				SQLwithBindVariables sqlbv53 = new SQLwithBindVariables();
				sqlbv53.sql(tPaytoDate);
				sqlbv53.put("otherno", tSSRS.GetText(i, 3).trim());
				SSRS DateSSRS = new SSRS();
				ExeSQL DateExe = new ExeSQL();
				DateSSRS = DateExe.execSQL(sqlbv53);

				strArr[0] = DateSSRS.GetText(1, 1);
				strArr[1] = tSSRS.GetText(i, 3);
				// 查询投保人
				// logger.debug("进入实际查询中。。。。。");
				SSRS tempSSRS = new SSRS();
				String sql = "";
				// sql="select appntname from lccont where
				// contno='"+tSSRS.GetText(i,3)+"'";
				//
				// tempSSRS = tExeSQL.execSQL(sql);
				// if(tempSSRS.MaxRow>0)
				// {
				// strArr[2]=tempSSRS.GetText(1,1);
				// }else{
				// strArr[2]="不详";
				// }
				strArr[2] = tSSRS.GetText(i, 30);
				// 通讯地址 电话 邮编
				sql = "select  (select placename from ldaddress where placetype = '03' and trim(placecode) = b.county)||b.postaladdress, (select GetAppntPhone('"
						+ tSSRS.GetText(i, 3)
						+ "','') from dual)  ,b.zipcode,b.customerno from  lcaddress b ,lcappnt c "
						+ " where c.appntno = b.customerno and c.addressno =  b.addressno "
						+ " and  c.contno='" + tSSRS.GetText(i, 3) + "'";

				tempSSRS = tExeSQL.execSQL(sql);
				if (tempSSRS.MaxRow > 0) {
					strArr[3] = tempSSRS.GetText(1, 1);
					strArr[4] = tempSSRS.GetText(1, 2);
					strArr[5] = tempSSRS.GetText(1, 3);
				} else {
					strArr[3] = "不详";
					strArr[4] = "不详";
					strArr[5] = "不详";

				}
				// 主险保费 附险保费
				sql = "select sum(a.SumActuPayMoney) from ljspayperson a, ljspay b, lcpol d where b.otherno = a.contno "
						+ "and a.GetNoticeNo = b.GetNoticeNo   and a.contno = '"
						+ "?contno?"
						+ "' "
						+ " and a.polno = d.polno and a.contno = d.contno and d.polno = d.mainpolno "; // lihuan加
				SQLwithBindVariables sqlbv55 = new SQLwithBindVariables();
				sqlbv55.sql(sql);
				sqlbv55.put("contno", tSSRS.GetText(i, 3));
				tempSSRS = tExeSQL.execSQL(sqlbv55);

				strArr[6] = tempSSRS.GetText(1, 1);
				if (strArr[6] != null && !strArr[6].equals("null")) {

				} else {
					strArr[6] = "0";
				}

				// sql="select sum(prem) from lcpol where polno=mainpolno and
				// contno='"+tSSRS.GetText(i,3)+"' group by polno";
				// tempSSRS = tExeSQL.execSQL(sql);
				// if(tempSSRS.MaxRow>0)
				// {
				// strArr[6]=tempSSRS.GetText(1,1);
				//
				// }else{
				// strArr[6]="0";
				//
				// }

				sql = " select sum(a.SumActuPayMoney) from ljspayperson a, ljspay b, lcpol d where b.otherno = a.contno  "
						+ " and a.GetNoticeNo = b.GetNoticeNo    and a.contno = '"
						+ "?contno?"
						+ "' "
						+ " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno ";
				SQLwithBindVariables sqlbv56 = new SQLwithBindVariables();
				sqlbv56.sql(sql);
				sqlbv56.put("contno", tSSRS.GetText(i, 3));
				tempSSRS = tExeSQL.execSQL(sqlbv56);
				if (tempSSRS.MaxRow != 0 && tempSSRS.GetText(1, 1) != null
						&& !tempSSRS.GetText(1, 1).equals("null")) {
					strArr[7] = tempSSRS.GetText(1, 1);

				} else {
					strArr[7] = "0";

				}

				// 健康加费
				sql = "select sum(prem) from lcprem where (payplantype ='01' or payplantype='11') and contno='"
						+ "?contno?" + "' ";
				SQLwithBindVariables sqlbv57 = new SQLwithBindVariables();
				sqlbv57.sql(sql);
				sqlbv57.put("contno", tSSRS.GetText(i, 3));
				tempSSRS = tExeSQL.execSQL(sqlbv57);
				if (tempSSRS.MaxRow > 0 && tempSSRS != null
						&& !tempSSRS.GetText(1, 1).equals("null")) {
					strArr[8] = tempSSRS.GetText(1, 1);

				} else {
					strArr[8] = "0";

				}
				// 职业加费
				sql = "select sum(prem) from lcprem where (payplantype='02' or payplantype='12') and contno='"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv58 = new SQLwithBindVariables();
				sqlbv58.sql(sql);
				sqlbv58.put("contno", tSSRS.GetText(i, 3));
				tempSSRS = tExeSQL.execSQL(sqlbv58);
				if (tempSSRS.MaxRow > 0 && tempSSRS != null
						&& !tempSSRS.GetText(1, 1).equals("null")) {
					strArr[9] = tempSSRS.GetText(1, 1);

				} else {
					strArr[9] = "0";

				}
				// 应收保费
				sql = "select sumduepaymoney,bankaccno,PayDate from ljspay where otherno='"
						+ "?otherno?" + "'";
				SQLwithBindVariables sqlbv59 = new SQLwithBindVariables();
				sqlbv59.sql(sql);
				sqlbv59.put("otherno", tSSRS.GetText(i, 3));
				tempSSRS = tExeSQL.execSQL(sqlbv59);
				String bankno = "";
				if (tempSSRS.MaxRow > 0) {
					strArr[10] = tempSSRS.GetText(1, 1);
					// strArr[0] = tempSSRS.GetText(1, 3);
					SumMoney = SumMoney + parseFloat(tempSSRS.GetText(1, 1));
					bankno = tempSSRS.GetText(1, 2);

				} else {
					// strArr[0] ="未录入";
					strArr[10] = "0";

				}
				// 缴费年期 缴费形式
				String tempgetpayendyear = tSSRS.GetText(i, 54);
				String templocation = "";

				String payintv = "";
				String payendyear = "";
				if (tempgetpayendyear.equals("1000")) {
					payendyear = "终身";
				} else {
					payendyear = tempgetpayendyear;
				}

				sql = "select paylocation ,InsuredName from lccont where contno='"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv60 = new SQLwithBindVariables();
				sqlbv60.sql(sql);
				sqlbv60.put("contno", tSSRS.GetText(i, 3));
				tempSSRS = tExeSQL.execSQL(sqlbv60);
				if (tempSSRS.MaxRow == 0) {

				} else {
					templocation = tempSSRS.GetText(1, 1);
				}

				if (templocation.equals("0")) {
					payintv = "银行";
					k = k + 1;
				} else if (templocation.equals("1")) {
					payintv = "柜台";
					m = m + 1;
				}

				else {
					payintv = "上门";
					j = j + 1;
				}
				strArr[11] = payendyear;
				strArr[12] = payintv;

				// 账号
				strArr[13] = bankno;

				// 被保险人
				strArr[14] = tempSSRS.GetText(1, 2);
				SSRS tMainPol = new SSRS();
				SQLwithBindVariables sqlbv61 = new SQLwithBindVariables();
				sqlbv61.sql("select riskcode from lcpol where  polno = mainpolno and contno = '"
						+ "?contno?" + "'");
				sqlbv61.put("contno", tSSRS.GetText(i, 3));
				tMainPol = tExeSQL.execSQL(sqlbv61);
				// 主险险种代码
				strArr[15] = tMainPol.GetText(1, 1);
				// 交费次数
				SQLwithBindVariables sqlbv62 = new SQLwithBindVariables();
				sqlbv62.sql("select max(paycount) from ljapayperson where contno = '"
						+ "?contno?" + "'");
				sqlbv62.put("contno", tSSRS.GetText(i, 3));
				tMainPol = tExeSQL.execSQL(sqlbv62);
				strArr[16] = String.valueOf(Integer.parseInt(tMainPol.GetText(
						1, 1)) + 1);
				// 上年度服务人员'HB210422361000260'
				SQLwithBindVariables sqlbv63 = new SQLwithBindVariables();
				sqlbv63.sql("select s.name from ljapayperson a, laagent s where trim(a.agentcode) = s.agentcode and  a.paycount =   ("
						+ " select max(paycount) from ljapayperson where contno = '"
						+ "?contno?"
						+ "')    "
						+ " and a.contno = '"
						+ "?contno?"
						+ "'");
				sqlbv63.put("contno", tSSRS.GetText(i, 3));
				tMainPol = tExeSQL.execSQL(sqlbv63);
				strArr[17] = tMainPol.GetText(1, 1);
				// /*投保人性别*/strArr[18]
				ExeSQL tBExeSQL = new ExeSQL();
				SSRS tBSSRS = new SSRS();
				// /*投保人性别*/strArr[18] 应收未收
				String tSql = "select codename  from ldcode a,lcappnt b "
						+ " where a.codetype = 'sex' and a.code = b.appntsex"
						+ " and contno = '" + "?contno?" + "'";
				SQLwithBindVariables sqlbv64 = new SQLwithBindVariables();
				sqlbv64.sql(tSql);
				sqlbv64.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv64);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[18] = tBSSRS.GetText(1, 1);
				} else {
					strArr[18] = "暂无";
				}
				// /*服务人员起期*/strArr[19]
				tSql = "select StartSerDate  from LACommisionDetail a,lccont b "
						+ "where a.grpcontno = b.contno   and a.agentcode = trim (b.agentcode) "
						+ " and contno = '" + "?contno?" + "'";
				SQLwithBindVariables sqlbv65 = new SQLwithBindVariables();
				sqlbv65.sql(tSql);
				sqlbv65.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv65);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[19] = tBSSRS.GetText(1, 1);
				} else {
					strArr[19] = "暂无";
				}

				// /*投保人与被保人关系*/strArr[20]
				tSql = "select codename  from ldcode "
						+ "where codetype = 'relation'   and code in "
						+ "(select RelationToAppnt from lcinsured where contno = '"
						+ "?contno?" + "')";
				SQLwithBindVariables sqlbv66 = new SQLwithBindVariables();
				sqlbv66.sql(tSql);
				sqlbv66.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv66);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[20] = tBSSRS.GetText(1, 1);
				} else {
					strArr[20] = "暂无";
				}

				// /*投保人与业务员关系-- 当前为空*/strArr[21]
				tSql = "select codename  from ldcode "
						+ "where codetype = 'agentrelatoappnt' and code in "
						+ " (select relationship "
						+ "   from lacommisiondetail "
						+ "  where relationship is not null "
						+ "    and grpcontno = '" + "?grpcontno?" + "' "
						+ " union " + " select relationship "
						+ "   from lacommisiondetailb "
						+ "  where relationship is not null "
						+ "    and grpcontno = '" + "?grpcontno?" + "')";
				SQLwithBindVariables sqlbv67 = new SQLwithBindVariables();
				sqlbv67.sql(tSql);
				sqlbv67.put("grpcontno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv67);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[21] = tBSSRS.GetText(1, 1);
				} else {
					strArr[21] = "暂无";

				}

				// /*被保人生日*/strArr[22]
				tSql = "select birthday from  lcinsured where contno = '"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv68 = new SQLwithBindVariables();
				sqlbv68.sql(tSql);
				sqlbv68.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv68);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[22] = tBSSRS.GetText(1, 1);
				} else {
					strArr[22] = "暂无";
				}

				// /*被保人电话*/strArr[23]
				tSql = "select phone  from lcaddress "
						+ "where customerno in "
						+ "( select insuredno from lcinsured where contno='"
						+ "?contno?"
						+ "' and sequenceno = 1 and addressno = lcaddress.addressno)";
				SQLwithBindVariables sqlbv69 = new SQLwithBindVariables();
				sqlbv69.sql(tSql);
				sqlbv69.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv69);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[23] = tBSSRS.GetText(1, 1);
				} else {
					strArr[23] = "暂无";
				}

				// /*投保人身份证号*/strArr[24]
				tSql = "select idno from lcappnt where contno = '"
						+ "?contno?" + "' and idtype = '0'";
				SQLwithBindVariables sqlbv70 = new SQLwithBindVariables();
				sqlbv70.sql(tSql);
				sqlbv70.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv70);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[24] = tBSSRS.GetText(1, 1);
				} else {
					strArr[24] = "暂无";
				}

				// /*投保人工作单位*/strArr[25]
				tSql = "select a.companyaddress from lcaddress a , lis.lcappnt b "
						+ "where b.appntno = a.customerno and a.addressno = b.addressno "
						+ "and b.contno = '" + "?contno?" + "'";
				SQLwithBindVariables sqlbv71 = new SQLwithBindVariables();
				sqlbv71.sql(tSql);
				sqlbv71.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv71);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[25] = tBSSRS.GetText(1, 1).replaceAll("<", "(")
							.replaceAll(">", ")");
				} else {
					strArr[25] = "暂无";
				}

				// /*主险保额*/strArr[26]
				tSql = "select amnt  from lcpol where   polno = mainpolno and contno = '"
						+ "?contno?" + "'  ";
				SQLwithBindVariables sqlbv72 = new SQLwithBindVariables();
				sqlbv72.sql(tSql);
				sqlbv72.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv72);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[26] = tBSSRS.GetText(1, 1);
				} else {
					strArr[26] = "暂无";
				}

				// /*附加险保额*/strArr[27]
				tSql = "select sum(amnt)  from lcpol where   polno <> mainpolno and contno = '"
						+ "?contno?" + "' and appflag = '1' ";
				SQLwithBindVariables sqlbv73 = new SQLwithBindVariables();
				sqlbv73.sql(tSql);
				sqlbv73.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv73);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[27] = tBSSRS.GetText(1, 1);
				} else {
					strArr[27] = "暂无";
				}

				if (mAgentCode != null && !mAgentCode.equals("null")
						&& !mAgentCode.equals("")) {
				} else {
					// sql = "select a.name , c.agentcode , a.managecom from
					// laagent a,lccont c where trim(c.agentcode) = a.agentcode
					// and (c.contno) = '" +
					// tSSRS.GetText(i, 3) + "' ";
					// tempSSRS = tExeSQL.execSQL(sql);
					//
					// strArr[28] = tempSSRS.GetText(1, 1);
					// strArr[29] = tempSSRS.GetText(1, 2);
					// tempSSRS = tExeSQL.execSQL(
					// "select (select name from labranchgroup where managecom
					// ='" +
					// tempSSRS.GetText(1, 3) + "' and branchattr = (select
					// Substr(branchattr,0,4) from labranchgroup where
					// agentgroup = (select branchcode from laagent where
					// agentcode ='" +
					// tempSSRS.GetText(1, 2) + "' ))), "
					// +
					// " (select name from labranchgroup where managecom ='" +
					// tempSSRS.GetText(1, 3) + "' and branchattr = (select
					// Substr(branchattr,0,7) from labranchgroup where
					// agentgroup = (select branchcode from laagent where
					// agentcode ='" +
					// tempSSRS.GetText(1, 2) + "' ))), "
					// +
					// " ((select name from labranchgroup where managecom ='" +
					// tempSSRS.GetText(1, 3) +
					// "' and agentgroup = (select branchcode from laagent where
					// agentcode ='" +
					// tempSSRS.GetText(1, 2) + "' ))) from dual ");
					// String tAgentManage = tempSSRS.GetText(1, 1) +
					// tempSSRS.GetText(1, 2) +
					// tempSSRS.GetText(1, 3);
					//
					// strArr[30] = tAgentManage;
					sql = "select a.name , c.agentcode , a.managecom,c.contno from laagent a,lccont c where trim(c.agentcode) = (a.agentcode) and (c.contno) = '"
							+ "?contno?" + "' ";
					SQLwithBindVariables sqlbv74 = new SQLwithBindVariables();
					sqlbv74.sql(sql);
					sqlbv74.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv74);

					strArr[28] = tempSSRS.GetText(1, 1);
					strArr[29] = tempSSRS.GetText(1, 2);
					tempSSRS = tExeSQL.execSQL("select getGlobalBranch('"
							+ tempSSRS.GetText(1, 2) + "','"
							+ tempSSRS.GetText(1, 4) + "') from dual ");
					String tAgentManage = tempSSRS.GetText(1, 1);

					strArr[30] = tAgentManage;
				}

				tlistTable.add(strArr);

			}
			String tSumMoney = new DecimalFormat("0.00").format(SumMoney);
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			if (mAgentCode != null && !mAgentCode.equals("null")
					&& !mAgentCode.equals("")) {
				xmlexport.createDocument("ungottenlist.vts", "PRINT");
			} else {

				xmlexport.createDocument("Mungottenlist.vts", "PRINT");
			}

			// 最好紧接着就初始化xml文档
			texttag.add("RiskFee", tSumMoney);
			texttag.add("Count", totalcount);
			texttag.add("cCount", m);
			texttag.add("EFTCount", k);
			texttag.add("ServeCount", j);
			texttag.add("LCCont.AgentCode", mAgentCode);
			texttag.add("LAAgent.Name", tAgentName);
			// texttag.add("type", "应收未收清单");
			texttag.add("Year", tYear);
			texttag.add("Month", tMonnth);
			texttag.add("Name", tAgentGroup);
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			xmlexport.addListTable(tlistTable, ContractTitle);
			// xmlexport.outputDocumentToFile("e:\\", "atest"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);

		}
		if (mOperation.equals("03")) {
			String strSql = "";
			// and agentcode = '" + mAgentCode + "'
			if (mAgentCode != null && !mAgentCode.equals("null")
					&& !mAgentCode.equals("")) {
				strSql = "select * "
						+ "from lcpol a "
						+ "where  contno in "
						+ "(select b.otherno "
						+ "from ljapay b, ljapayperson c "
						+ "where b.getnoticeno = c.getnoticeno "
						+ "and c.LastPayToDate >= '"
						+ "?mStartDate?"
						+ "' "
						+ "and c.LastPayToDate <=  '"
						+ "?mEndDate?"
						+ "' "
						+ "and b.managecom like concat('"
						+ "?mManageCom?"
						+ "','%') and c.managecom like concat('"
						+ "?mManageCom?"
						+ "','%')"
						+ " and exists (select 1 from lmriskapp where riskcode=c.riskcode and mngcom='"
						+ "?mchantypecode?"
						+ "')"
						+ " and   b.sumactupaymoney <> '0')"
						+ "and a.polno = a.mainpolno "
						+ " and exists (select 1 from lmriskapp where riskcode=a.riskcode and mngcom='"
						+ "?mchantypecode?" + "')" + "and a.agentcode = '"
						+ mAgentCode + "'";
				if (mchantypecode.equals("B")) {
					strSql += " and a.payintv = '12' ";
				}
				strSql += "and a.managecom like concat('"
						+ "?mManageCom?"
						+ "','%') "
						+ "order by managecom, agentcode, paytodate, appntno ";

			} else {
				strSql = "select * "
						+ "from lcpol a "
						+ "where  contno in "
						+ "(select b.otherno "
						+ "from ljapay b, ljapayperson c "
						+ "where b.getnoticeno = c.getnoticeno "
						+ "and c.LastPayToDate >= '"
						+ "?mStartDate?"
						+ "' "
						+ "and c.LastPayToDate <=  '"
						+ "?mEndDate?"
						+ "' "
						+ "and b.managecom like concat('"
						+ "?mManageCom?"
						+ "','%') and c.managecom like concat('"
						+ "?mManageCom?"
						+ "','%')"
						+ " and exists (select 1 from lmriskapp where riskcode=c.riskcode and mngcom='"
						+ "?mchantypecode?"
						+ "')"
						+ " and   b.sumactupaymoney <> '0')"
						+ "and a.polno = a.mainpolno "
						+ " and exists (select 1 from lmriskapp where riskcode=a.riskcode and mngcom='"
						+ "?mchantypecode?" + "')";
				if (mchantypecode.equals("B")) {
					strSql += " and a.payintv = '12' ";
				}

				strSql += "and a.managecom like concat('"
						+ "?mManageCom?"
						+ "','%') "
						+ "order by managecom, agentcode, paytodate, appntno ";

			}
			SQLwithBindVariables sqlbv76 = new SQLwithBindVariables();
			sqlbv76.sql(strSql);
			sqlbv76.put("mStartDate", mStartDate);
			sqlbv76.put("mEndDate", mEndDate);
			sqlbv76.put("mManageCom", mManageCom);
			sqlbv76.put("mchantypecode", mchantypecode);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv76);
			totalcount = totalcount + tSSRS.getMaxRow();
			if (tSSRS.getMaxRow() == 0) {
				mErrors.copyAllErrors(tSSRS.mErrors);
				buildError("getPrintData", "没有要打印的数据！！");
				return false;
			}
			if (tSSRS.getMaxRow() > 4000) {
				mErrors.copyAllErrors(tSSRS.mErrors);
				buildError("getPrintData", "打印数据量过大，请提交批量打印！！");
				return false;
			}

			logger.debug("打印的行数＝＝＝＝＝＝" + tSSRS.MaxRow);
			ListTable tlistTable = new ListTable();
			String strArr[] = null;
			tlistTable.setName("RISK");
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				if (mAgentCode != null && !mAgentCode.equals("null")
						&& !mAgentCode.equals("")) {
					strArr = new String[28];

				} else {

					strArr = new String[31];

				}

				strArr[0] = tSSRS.GetText(i, 37);
				strArr[1] = tSSRS.GetText(i, 3);
				// 查询投保人
				// logger.debug("进入实际查询中。。。。。");
				SSRS tempSSRS = new SSRS();
				String sql = "";

				strArr[2] = tSSRS.GetText(i, 30);
				// 通讯地址 电话 邮编
				sql = "select  (select placename from ldaddress where placetype = '03' and trim(placecode) = b.county)||b.postaladdress, (select GetAppntPhone('"
						+ tSSRS.GetText(i, 3)
						+ "','') from dual) ,b.zipcode,b.customerno from  lcaddress b ,lcappnt c "
						+ " where c.appntno = b.customerno and c.addressno =  b.addressno "
						+ " and  c.contno='" + tSSRS.GetText(i, 3) + "'";

				tempSSRS = tExeSQL.execSQL(sql);
				if (tempSSRS.MaxRow > 0) {
					strArr[3] = tempSSRS.GetText(1, 1);
					strArr[4] = tempSSRS.GetText(1, 2);
					strArr[5] = tempSSRS.GetText(1, 3);
				} else {
					strArr[3] = "不详";
					strArr[4] = "不详";
					strArr[5] = "不详";

				}
				// 主险保费 附险保费
				SQLwithBindVariables sqlbv78 = new SQLwithBindVariables();
				sqlbv78.sql("select sum(a.SumActuPayMoney) from ljapayperson a, ljapay b, lcpol d where b.otherno = a.contno "
						+ "and a.GetNoticeNo = b.GetNoticeNo and a.contno = '"
						+ "?contno?"
						+ "' "
						+ " and a.polno = d.polno and a.contno = d.contno and d.polno = d.mainpolno  and a.confdate in (select max(confdate) from ljapayperson where contno = '"
						+ "?contno?"
						+ "'  and paytype = 'ZC')");
				sqlbv78.put("contno", tSSRS.GetText(i, 3));
				tzuSSRS = exeSql.execSQL(sqlbv78);

				strArr[6] = tzuSSRS.GetText(1, 1);
				if (strArr[6] != null && !strArr[6].equals("null")) {

				} else {
					strArr[6] = "0";
				}
				SQLwithBindVariables sqlbv79 = new SQLwithBindVariables();
				sqlbv79.sql(" select sum(a.SumActuPayMoney) from ljspayperson a, ljspay b, lcpol d where b.otherno = a.contno  "
						+ " and a.GetNoticeNo = b.GetNoticeNo  and a.contno = '"
						+ "?contno?"
						+ "' "
						+ " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno ");
				sqlbv79.put("contno", tSSRS.GetText(i, 3));
				tzuSSRS = exeSql.execSQL(sqlbv79); // lihuan加
				if (tzuSSRS.MaxRow != 0 && tzuSSRS.GetText(1, 1) != null
						&& !tzuSSRS.GetText(1, 1).equals("null")) {
					strArr[7] = tzuSSRS.GetText(1, 1);
				}

				else {
					SQLwithBindVariables sqlbv80 = new SQLwithBindVariables();
					sqlbv80.sql(" select sum(a.SumActuPayMoney) from ljapayperson a, ljapay b, lcpol d where b.otherno = a.contno  "
							+ " and a.GetNoticeNo = b.GetNoticeNo and a.contno = '"
							+ "?contno?"
							+ "' "
							+ " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno and a.confdate in (select max(confdate) from ljapayperson where contno = '"
							+ "?contno?"
							+ "'  and paytype = 'ZC')");
					sqlbv80.put("contno", tSSRS.GetText(i, 3));
					tzuSSRS = exeSql.execSQL(sqlbv80);

					if (tzuSSRS.MaxRow != 0 && tzuSSRS.GetText(1, 1) != null
							&& !tzuSSRS.GetText(1, 1).equals("null")) {

						strArr[7] = tzuSSRS.GetText(1, 1);

					} else {
						strArr[7] = "0";

					}

				}

				// 健康加费
				sql = "select sum(prem) from lcprem where (payplantype ='01' or payplantype='11') and contno='"
						+ "?contno?" + "' ";
				SQLwithBindVariables sqlbv81 = new SQLwithBindVariables();
				sqlbv81.sql(sql);
				sqlbv81.put("contno", tSSRS.GetText(i, 3));
				tempSSRS = tExeSQL.execSQL(sqlbv81);
				if (tempSSRS.MaxRow > 0 && tempSSRS != null
						&& !tempSSRS.GetText(1, 1).equals("null")) {
					strArr[8] = tempSSRS.GetText(1, 1);

				} else {
					strArr[8] = "0";

				}
				// 职业加费
				sql = "select sum(prem) from lcprem where (payplantype='02' or payplantype='12') and contno='"
						+ "?contno?" + "' ";
				SQLwithBindVariables sqlbv82 = new SQLwithBindVariables();
				sqlbv82.sql(sql);
				sqlbv82.put("contno", tSSRS.GetText(i, 3));
				tempSSRS = tExeSQL.execSQL(sqlbv82);
				if (tempSSRS.MaxRow > 0 && tempSSRS != null
						&& !tempSSRS.GetText(1, 1).equals("null")) {
					strArr[9] = tempSSRS.GetText(1, 1);

				} else {
					strArr[9] = "0";

				}
				// 实收保费
				sql = "select SumActuPayMoney,BankAccNo,PayDate from ljapay where otherno='"
						+ "?otherno?"
						+ "' and  confdate in (select max(confdate) from ljapayperson where contno = '"
						+ "?otherno?" + "' and paytype = 'ZC')";
				SQLwithBindVariables sqlbv83 = new SQLwithBindVariables();
				sqlbv83.sql(sql);
				sqlbv83.put("otherno", tSSRS.GetText(i, 3));
				tempSSRS = tExeSQL.execSQL(sqlbv83);
				String bankno = "";
				if (tempSSRS.MaxRow > 0) {
					strArr[10] = tempSSRS.GetText(1, 1);
					// strArr[0] = tempSSRS.GetText(1, 3);
					SumMoney = SumMoney + parseFloat(strArr[6])
							+ parseFloat(strArr[7]);
					bankno = tempSSRS.GetText(1, 2);

				} else {
					// strArr[0] = "未录入";
					strArr[10] = "0";

				}
				// 缴费年期 缴费形式
				String tempgetpayendyear = tSSRS.GetText(i, 54);
				String templocation = "";

				String payintv = "";
				String payendyear = "";
				if (tempgetpayendyear.equals("1000")) {
					payendyear = "终身";
				} else {
					payendyear = tempgetpayendyear;
				}

				sql = "select paylocation ,InsuredName from lccont where contno='"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv84 = new SQLwithBindVariables();
				sqlbv84.sql(sql);
				sqlbv84.put("contno", tSSRS.GetText(i, 3));
				tempSSRS = tExeSQL.execSQL(sqlbv84);
				if (tempSSRS.MaxRow == 0) {

				} else {
					templocation = tempSSRS.GetText(1, 1);
				}

				if (templocation.equals("0")) {
					payintv = "银行";
					k = k + 1;
				} else if (templocation.equals("1")) {
					payintv = "柜台";
					m = m + 1;
				}

				else {
					payintv = "上门";
					j = j + 1;
				}
				strArr[11] = payendyear;
				strArr[12] = payintv;
				// 账号
				strArr[13] = bankno;

				// 被保险人
				strArr[14] = tempSSRS.GetText(1, 2);
				SSRS tMainPol = new SSRS();
				SQLwithBindVariables sqlbv85 = new SQLwithBindVariables();
				sqlbv85.sql("select riskcode from lcpol where  polno = mainpolno and contno = '"
						+ "?contno?" + "'");
				sqlbv85.put("contno", tSSRS.GetText(i, 3));
				tMainPol = tExeSQL.execSQL(sqlbv85);
				// 主险险种代码
				strArr[15] = tMainPol.GetText(1, 1);
				// 交费次数
				SQLwithBindVariables sqlbv86 = new SQLwithBindVariables();
				sqlbv86.sql("select max(paycount) from ljapayperson where contno = '"
						+ "?contno?" + "'");
				sqlbv86.put("contno", tSSRS.GetText(i, 3));
				tMainPol = tExeSQL.execSQL(sqlbv86);
				strArr[16] = String.valueOf(Integer.parseInt(tMainPol.GetText(
						1, 1)));
				// 上年度服务人员'HB210422361000260'
				SQLwithBindVariables sqlbv87 = new SQLwithBindVariables();
				sqlbv87.sql("select s.name from ljapayperson a, laagent s where trim(a.agentcode)  = s.agentcode and  a.paycount =   ("
						+ " select max(paycount)-1 from ljapayperson where contno = '"
						+ "?contno?"
						+ "')    "
						+ " and a.contno = '"
						+ "?contno?"
						+ "'");
				sqlbv87.put("contno", tSSRS.GetText(i, 3));
				tMainPol = tExeSQL.execSQL(sqlbv87);
				strArr[17] = tMainPol.GetText(1, 1);
				// /*投保人性别*/strArr[18] 应收未收
				ExeSQL tBExeSQL = new ExeSQL();
				SSRS tBSSRS = new SSRS();
				String tSql = "select codename  from ldcode a,lcappnt b "
						+ " where a.codetype = 'sex' and a.code = b.appntsex"
						+ " and contno = '" + "?contno?" + "'";
				SQLwithBindVariables sqlbv88 = new SQLwithBindVariables();
				sqlbv88.sql(tSql);
				sqlbv88.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv88);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[18] = tBSSRS.GetText(1, 1);
				} else {
					strArr[18] = "暂无";
				}
				// /*服务人员起期*/strArr[19]
				tSql = "select StartSerDate  from LACommisionDetail a,lccont b "
						+ "where a.grpcontno = b.contno   and a.agentcode = trim (b.agentcode) "
						+ " and contno = '" + "?contno?" + "'";
				SQLwithBindVariables sqlbv89 = new SQLwithBindVariables();
				sqlbv89.sql(tSql);
				sqlbv89.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv89);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[19] = tBSSRS.GetText(1, 1);
				} else {
					strArr[19] = "暂无";
				}

				// /*投保人与被保人关系*/strArr[20]
				tSql = "select codename  from ldcode "
						+ "where codetype = 'relation'   and code in "
						+ "(select RelationToAppnt from lcinsured where contno = '"
						+ "?contno?" + "')";
				SQLwithBindVariables sqlbv90 = new SQLwithBindVariables();
				sqlbv90.sql(tSql);
				sqlbv90.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv90);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[20] = tBSSRS.GetText(1, 1);
				} else {
					strArr[20] = "暂无";
				}

				// /*投保人与业务员关系-- 当前为空*/strArr[21]
				tSql = "select codename  from ldcode "
						+ "where codetype = 'agentrelatoappnt' and code in "
						+ " (select relationship "
						+ "   from lacommisiondetail "
						+ "  where relationship is not null "
						+ "    and grpcontno = '" + "?grpcontno?" + "' "
						+ " union " + " select relationship "
						+ "   from lacommisiondetailb "
						+ "  where relationship is not null "
						+ "    and grpcontno = '" + "?grpcontno?" + "')";
				SQLwithBindVariables sqlbv91 = new SQLwithBindVariables();
				sqlbv91.sql(tSql);
				sqlbv91.put("grpcontno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv91);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[21] = tBSSRS.GetText(1, 1);
				} else {
					strArr[21] = "暂无";

				}

				// /*被保人生日*/strArr[22]
				tSql = "select birthday from  lcinsured where contno = '"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv92 = new SQLwithBindVariables();
				sqlbv92.sql(tSql);
				sqlbv92.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv92);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[22] = tBSSRS.GetText(1, 1);
				} else {
					strArr[22] = "暂无";
				}

				// /*被保人电话*/strArr[23]
				tSql = "select phone  from lis.lcaddress "
						+ "where customerno in "
						+ "( select insuredno from lis.lcinsured where contno='"
						+ "?contno?"
						+ "' and sequenceno = 1 and addressno = lcaddress.addressno)";
				SQLwithBindVariables sqlbv93 = new SQLwithBindVariables();
				sqlbv93.sql(tSql);
				sqlbv93.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv93);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[23] = tBSSRS.GetText(1, 1);
				} else {
					strArr[23] = "暂无";
				}

				// /*投保人身份证号*/strArr[24]
				tSql = "select idno from lcappnt where contno = '"
						+ "?contno?" + "' and idtype = '0'";
				SQLwithBindVariables sqlbv94 = new SQLwithBindVariables();
				sqlbv94.sql(tSql);
				sqlbv94.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv94);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[24] = tBSSRS.GetText(1, 1);
				} else {
					strArr[24] = "暂无";
				}

				// /*投保人工作单位*/strArr[25]
				tSql = "select a.companyaddress from lcaddress a , lis.lcappnt b "
						+ "where b.appntno = a.customerno and a.addressno = b.addressno "
						+ "and b.contno = '" + "?contno?" + "'";
				SQLwithBindVariables sqlbv95 = new SQLwithBindVariables();
				sqlbv95.sql(tSql);
				sqlbv95.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv95);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[25] = tBSSRS.GetText(1, 1).replaceAll("<", "(")
							.replaceAll(">", ")");
				} else {
					strArr[25] = "暂无";
				}

				// /*主险保额*/strArr[26]
				tSql = "select amnt  from lcpol where   polno = mainpolno and contno = '"
						+ "?contno?" + "'  ";
				SQLwithBindVariables sqlbv96 = new SQLwithBindVariables();
				sqlbv96.sql(tSql);
				sqlbv96.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv96);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[26] = tBSSRS.GetText(1, 1);
				} else {
					strArr[26] = "暂无";
				}

				// /*附加险保额*/strArr[27]
				tSql = "select sum(amnt)  from lcpol where   polno <> mainpolno and contno = '"
						+ "?contno?" + "' and appflag = '1' ";
				SQLwithBindVariables sqlbv97 = new SQLwithBindVariables();
				sqlbv97.sql(tSql);
				sqlbv97.put("contno", tSSRS.GetText(i, 3));
				tBSSRS = tBExeSQL.execSQL(sqlbv97);
				if (tBSSRS.getMaxRow() != 0 && tBSSRS.GetText(1, 1) != null
						&& !tBSSRS.GetText(1, 1).equals("null")
						&& !tBSSRS.GetText(1, 1).equals("")) {
					strArr[27] = tBSSRS.GetText(1, 1);
				} else {
					strArr[27] = "暂无";
				}

				if (mAgentCode != null && !mAgentCode.equals("null")
						&& !mAgentCode.equals("")) {
				} else {
					sql = "select a.name , c.agentcode , a.managecom,c.contno from laagent a,lccont c where trim(c.agentcode) = (a.agentcode) and (c.contno) = '"
							+ "?contno?" + "' ";
					SQLwithBindVariables sqlbv98 = new SQLwithBindVariables();
					sqlbv98.sql(sql);
					sqlbv98.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv98);

					strArr[28] = tempSSRS.GetText(1, 1);
					strArr[29] = tempSSRS.GetText(1, 2);
					tempSSRS = tExeSQL.execSQL("select getGlobalBranch('"
							+ tempSSRS.GetText(1, 2) + "','"
							+ tempSSRS.GetText(1, 4) + "') from dual ");
					String tAgentManage = tempSSRS.GetText(1, 1);

					strArr[30] = tAgentManage;

				}

				tlistTable.add(strArr);

			}

			String tSumMoney = new DecimalFormat("0.00").format(SumMoney);

			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例

			if (mAgentCode != null && !mAgentCode.equals("null")
					&& !mAgentCode.equals("")) {
				xmlexport.createDocument("gottenlist.vts", "PRINT");
			} else {

				xmlexport.createDocument("Mgottenlist.vts", "PRINT");
			}

			// 最好紧接着就初始化xml文档
			texttag.add("RiskFee", tSumMoney);
			texttag.add("Count", totalcount);
			texttag.add("cCount", m);
			texttag.add("EFTCount", k);
			texttag.add("ServeCount", j);
			// texttag.add("type", "应收已收清单");
			texttag.add("LCCont.AgentCode", mAgentCode);
			texttag.add("LAAgent.Name", tAgentName);
			texttag.add("Year", tYear);
			texttag.add("Month", tMonnth);
			texttag.add("Name", tAgentGroup);
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			xmlexport.addListTable(tlistTable, ContractTitle);
			// xmlexport.outputDocumentToFile("e:\\", "atest"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);

		}
		return true;
	}

	public float parseFloat(String s) {
		float f1 = 0;
		String tmp = "";
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			s1 = s.substring(i, i + 1);
			if (s1.equals("0") || s1.equals("1") || s1.equals("2")
					|| s1.equals("3") || s1.equals("4") || s1.equals("5")
					|| s1.equals("6") || s1.equals("7") || s1.equals("8")
					|| s1.equals("9") || s1.equals(".")) {
				tmp = tmp + s1;
			} else if (tmp.length() > 0) {
				break;
			}
		}
		f1 = Float.parseFloat(tmp);
		// logger.debug("tmp:"+tmp+" f1:"+f1);
		return f1;
	}
}
