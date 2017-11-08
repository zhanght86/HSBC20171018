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

public class MonthXQFeeStateprintBL {
private static Logger logger = Logger.getLogger(MonthXQFeeStateprintBL.class);
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

	public MonthXQFeeStateprintBL() {

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
		// if(!tFlag)
		// {
		if (!getPrintData()) {

			return false;
		}
		// }else
		// {
		// if (!getPrintManageData()) {
		//
		// return false;
		// }

		// }
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
		cError.moduleName = "MonthXQFeeStateprintBL";
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
			tMonnth = // mStartDate.substring(5, 7) + "," +
			mEndDate.substring(5, 7);

		}
		ExeSQL exeSql = new ExeSQL();
		SSRS tzuSSRS = new SSRS();
		String sql1 = " select name from ldcom where comcode = '" + "?mManageCom?"
				+ "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(sql1);
		sqlbv3.put("mManageCom", mManageCom);
		tzuSSRS = exeSql.execSQL(sqlbv3);
		String comname = tzuSSRS.GetText(1, 1);

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
			// 如果没有输入代理人信息，就要做下面的验证
			// if(mAgentCode !=null && !mAgentCode.equals("null") &&
			// !mAgentCode.equals(""))
			// {
			// }
			// else
			// {
			// }
			ExeSQL tExeSQL = new ExeSQL();
			// SSRS tSSRS = new SSRS();
			// tSSRS = tExeSQL.execSQL(strSql);

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
						+ " where othernotype = '3' "
						+ " and a.payintv = '1' "
						+ "   and a.contno = b.otherno "
						+ "   and a.polno = a.mainpolno "
						+ "   and b.startpaydate >= '"
						+ mStartDate
						+ "' "
						+ "   and b.startpaydate <= '"
						+ mEndDate
						+ "' "
						+ "   and b.agentcode = '"
						+ mAgentCode
						+ "' "
						+ " order by a.agentcode, a.agentgroup, a.contno, a.paytodate ";

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
						+ " where othernotype = '3' "
						+ " and a.payintv = '1' "
						+ "   and a.contno = b.otherno "
						+ "   and a.polno = a.mainpolno "
						+ "   and b.startpaydate >= '"
						+ mStartDate
						+ "' "
						+ "   and b.startpaydate <= '"
						+ mEndDate
						+ "' "
						+ "   and b.managecom like '"
						+ mManageCom
						+ "%' "
						+ " order by a.agentcode, a.agentgroup, a.contno, a.paytodate ";

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
				tlistTable.setName("MonthXQFeeState");
				// for (int i = 1; i <= tSSRS.MaxRow; i++) {
				rs = st.executeQuery(strSql);
				while (rs.next()) {
					totalcount = totalcount + 1;
					flag1 = "0";
					logger.debug("打印的行数为   *************************      ");
					logger.debug("打印的保单" + rs.getString("ContNo"));
					logger.debug(i++);

					// if (mAgentCode != null && !mAgentCode.equals("null") &&
					// !mAgentCode.equals(""))
					// {
					// strArr = new String[29];
					//
					// }
					// else
					// {
					//
					// strArr = new String[32];
					// }

					strArr = new String[15];

					strArr[0] = rs.getString("contno").trim(); // 保单号
					strArr[1] = "未收"; // 保单状态
					strArr[2] = rs.getString("agentcode").trim(); // 维护人员
					// strArr[3] = tAgentGroup;//人员机构
					strArr[4] = rs.getString("paytodate").trim(); // 交费对应日
					strArr[5] = rs.getString("AppntName").trim(); // 投保人

					SSRS tempSSRS = new SSRS(); // 交费方式
					String sql = "";
					sql = "select paylocation from lccont where contno='"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
					sqlbv4.sql(sql);
					sqlbv4.put("contno", rs.getString("ContNo"));
					tempSSRS = tExeSQL.execSQL(sqlbv4);

					if (tempSSRS.GetText(1, 1).equals("0")) {
						strArr[6] = "银行";
					} else if (tempSSRS.GetText(1, 1).equals("1")) {
						strArr[6] = "柜台";
					} else {
						strArr[6] = "上门";
					}

					sql = "select sumduepaymoney,bankaccno,PayDate from ljspay where otherno='"
							+ "?otherno?" + "' and othernotype='3'";
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					sqlbv5.sql(sql);
					sqlbv5.put("otherno", rs.getString("ContNo"));
					SSRS tempSSRS1 = new SSRS();
					tempSSRS1 = tExeSQL.execSQL(sqlbv5);
					strArr[7] = tempSSRS1.GetText(1, 2); // 帐号
					strArr[11] = tempSSRS1.GetText(1, 1); // 应缴保费
					SumMoney = SumMoney + parseFloat(tempSSRS1.GetText(1, 1));

					sql = "select max(paycount) from ljapayperson where contno = '"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
					sqlbv6.sql(sql);
					sqlbv6.put("contno", rs.getString("ContNo"));
					SSRS tempSSRS2 = new SSRS();
					tempSSRS2 = tExeSQL.execSQL(sqlbv6);
					String paycount = tempSSRS2.GetText(1, 1);
					strArr[10] = String.valueOf(Integer.parseInt(paycount) + 1); // 应交费次数
					strArr[9] = paycount; // 已交费次数
					strArr[8] = String.valueOf(Integer.parseInt(paycount) / 12);
					; // 交费年度

					sql = "select  concat((select placename from ldaddress where placetype = '02' and placecode = b.city),b.postaladdress),b.zipcode,b.phone from  lcaddress b ,lcappnt c "
							+ " where c.appntno = b.customerno and c.addressno =  b.addressno "
							+ " and  c.contno='" + "?contno?" + "'";
					SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
					sqlbv7.sql(sql);
					sqlbv7.put("contno", rs.getString("ContNo"));
					SSRS tempSSRS3 = new SSRS();
					tempSSRS3 = tExeSQL.execSQL(sqlbv7);
					strArr[12] = tempSSRS3.GetText(1, 1); // 地址
					strArr[13] = tempSSRS3.GetText(1, 2); // 邮编
					strArr[14] = tempSSRS3.GetText(1, 3); // 电话

					// 人员机构
					sql = "select c.agentcode, a.managecom from laagent a,lccont c where trim(c.agentcode) = a.agentcode and c.contno = '"
							+ "?contno?" + "' ";
					SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
					sqlbv8.sql(sql);
					sqlbv8.put("contno", rs.getString("ContNo"));
					SSRS tempSSRS4 = new SSRS();
					tempSSRS4 = tExeSQL.execSQL(sqlbv8);

					String agentcode = tempSSRS4.GetText(1, 1);
					String managecom = tempSSRS4.GetText(1, 2);
					SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
					sqlbv9.sql("select (select name from labranchgroup where managecom ='"
							+ "?managecom?"
							+ "' and branchattr = (select Substr(branchattr,1,4) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
							+ "?agentcode?"
							+ "' ))), "
							+ " (select name from labranchgroup where managecom ='"
							+ "?managecom?"
							+ "' and branchattr = (select Substr(branchattr,1,7) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
							+ "?agentcode?"
							+ "' ))), "
							+ "  ((select name from labranchgroup where managecom ='"
							+ "?managecom?"
							+ "' and agentgroup = (select branchcode from laagent where agentcode ='"
							+ "?agentcode?" + "' ))) from dual ");
					sqlbv9.put("managecom", managecom);
					sqlbv9.put("agentcode", agentcode);
					tempSSRS4 = tExeSQL.execSQL(sqlbv9);
					String tAgentManage = tempSSRS4.GetText(1, 1)
							+ tempSSRS4.GetText(1, 2) + tempSSRS4.GetText(1, 3);

					strArr[3] = tAgentManage;

					tlistTable.add(strArr);

				}

				// 已收**************************************************************
				// *****************************************************************
				// ****************************************************************
				if (mAgentCode != null && !mAgentCode.equals("null")
						&& !mAgentCode.equals("")) {
					strSql = "select *  from lcpol a where exists  "
							+ "(select contno  from LJaPayPerson b "
							+ " where b.LastPayToDate >= '"
							+ mStartDate
							+ "'  and b.LastPayToDate <= '"
							+ mEndDate
							+ "' "
							+ "and b.paycount > 1 and b.managecom like '"
							+ mManageCom
							+ "%' "
							+ "and exists (select 'X' from ljapay c  where c.othernotype = '3' "
							+ "and b.getnoticeno = c.getnoticeno)  and b.contno = a.contno ) and a.payintv = '1'  and polno = mainpolno  and agentcode = '"
							+ mAgentCode
							+ "' and  managecom like '"
							+ mManageCom
							+ "%'"
							+ " order by agentcode, agentgroup,contno,paytodate ";

				} else {
					strSql = "select *  from lcpol a where exists  "
							+ "(select contno  from LJaPayPerson b "
							+ " where b.LastPayToDate >= '"
							+ mStartDate
							+ "'  and b.LastPayToDate <= '"
							+ mEndDate
							+ "' "
							+ "and b.paycount > 1 and b.managecom like '"
							+ mManageCom
							+ "%' "
							+ "and exists (select 'X' from ljapay c  where c.othernotype = '3' "
							+ "and b.getnoticeno = c.getnoticeno)  and b.contno = a.contno )  and a.payintv = '1' and polno = mainpolno "
							+ " and a.managecom like '"
							+ mManageCom
							+ "%'  order by agentcode, agentgroup,contno,paytodate ";

				}

				rs = st.executeQuery(strSql);

				logger.debug("打印的行数＝＝＝＝＝＝");

				tlistTable.setName("MonthXQFeeState");
				while (rs.next()) {
					flag2 = "0";
					totalcount = totalcount + 1;
					logger.debug("打印的行数为   *************************      ");
					logger.debug("实收打印的保单" + rs.getString("ContNo"));
					logger.debug(i++);

					// SSRS tswd = new SSRS();
					// tswd = tExeSQL.execSQL("select lastpaytodate from
					// ljapayperson where paycount in (select max(paycount) from
					// ljapayperson where contno = '"+rs.getString("ContNo")+"')
					// and contno = '"+rs.getString("ContNo")+"'");
					// if (mAgentCode != null && !mAgentCode.equals("null") &&
					// !mAgentCode.equals("")) {
					// strArr = new String[29];
					//
					// } else {
					//
					// strArr = new String[32];
					//
					// }
					String sql = "";
					strArr = new String[15];

					strArr[0] = rs.getString("contno").trim(); // 保单号
					strArr[1] = "已收"; // 保单状态
					strArr[2] = rs.getString("agentcode").trim(); // 维护人员
					// strArr[3] = tAgentGroup;//人员机构

					SSRS tempSSRS5 = new SSRS();
					sql = "select lastpaytodate  from ljapayperson where paycount in (select max(paycount) from ljapayperson  where contno = '"
							+ "?contno?"
							+ "') and contno = '"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
					sqlbv10.sql(sql);
					sqlbv10.put("contno", rs.getString("ContNo"));
					tempSSRS5 = tExeSQL.execSQL(sqlbv10);
					strArr[4] = tempSSRS5.GetText(1, 1); // 交费对应日
					strArr[5] = rs.getString("AppntName").trim(); // 投保人

					SSRS tempSSRS = new SSRS(); // 交费方式
					sql = "select paylocation from lccont where contno='"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
					sqlbv11.sql(sql);
					sqlbv11.put("contno", rs.getString("ContNo"));
					tempSSRS = tExeSQL.execSQL(sqlbv11);

					if (tempSSRS.GetText(1, 1).equals("0")) {
						strArr[6] = "银行";
					} else if (tempSSRS.GetText(1, 1).equals("1")) {
						strArr[6] = "柜台";
					} else {
						strArr[6] = "上门";
					}

					sql = "select SumActuPayMoney,BankAccNo from ljapay where otherno='"
							+ "?contno?"
							+ "' and othernotype='3' and  confdate in (select max(confdate) from ljapayperson where contno = '"
							+ "?contno?" + "' and paytype = 'ZC')";
					SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
					sqlbv12.sql(sql);
					sqlbv12.put("contno", rs.getString("ContNo"));
					SSRS tempSSRS1 = new SSRS();
					tempSSRS1 = tExeSQL.execSQL(sqlbv12);
					strArr[7] = tempSSRS1.GetText(1, 2); // 帐号
					strArr[11] = tempSSRS1.GetText(1, 1); // 应缴保费
					SumMoney = SumMoney + parseFloat(tempSSRS1.GetText(1, 1));

					sql = "select max(paycount) from ljapayperson where contno = '"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
					sqlbv13.sql(sql);
					sqlbv13.put("contno", rs.getString("ContNo"));
					SSRS tempSSRS2 = new SSRS();
					tempSSRS2 = tExeSQL.execSQL(sqlbv13);
					String paycount = tempSSRS2.GetText(1, 1);
					strArr[10] = paycount; // 应交费次数
					strArr[9] = paycount; // 已交费次数
					strArr[8] = String.valueOf(Integer.parseInt(paycount) / 12);
					; // 交费年度

					sql = "select  concat((select placename from ldaddress where placetype = '02' and placecode = b.city),b.postaladdress),b.zipcode,b.phone from  lcaddress b ,lcappnt c "
							+ " where c.appntno = b.customerno and c.addressno =  b.addressno "
							+ " and  c.contno='" + "?contno?" + "'";
					SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
					sqlbv14.sql(sql);
					sqlbv14.put("contno", rs.getString("ContNo"));
					SSRS tempSSRS3 = new SSRS();
					tempSSRS3 = tExeSQL.execSQL(sqlbv14);
					strArr[12] = tempSSRS3.GetText(1, 1); // 地址
					strArr[13] = tempSSRS3.GetText(1, 2); // 邮编
					strArr[14] = tempSSRS3.GetText(1, 3); // 电话

					// 人员机构
					sql = "select c.agentcode, a.managecom from laagent a,lccont c where trim(c.agentcode) = a.agentcode and c.contno = '"
							+ "?contno?" + "' ";
					SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
					sqlbv15.sql(sql);
					sqlbv15.put("contno", rs.getString("ContNo"));
					SSRS tempSSRS4 = new SSRS();
					tempSSRS4 = tExeSQL.execSQL(sqlbv15);

					String agentcode = tempSSRS4.GetText(1, 1);
					String managecom = tempSSRS4.GetText(1, 2);
					SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
					sqlbv16.sql("select (select name from labranchgroup where managecom ='"
							+ "?managecom?"
							+ "' and branchattr = (select Substr(branchattr,1,4) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
							+ "?agentcode?"
							+ "' ))), "
							+ " (select name from labranchgroup where managecom ='"
							+ "?managecom?"
							+ "' and branchattr = (select Substr(branchattr,1,7) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
							+ "?agentcode?"
							+ "' ))), "
							+ "  ((select name from labranchgroup where managecom ='"
							+ "?managecom?"
							+ "' and agentgroup = (select branchcode from laagent where agentcode ='"
							+ "?agentcode?" + "' ))) from dual ");
					sqlbv16.put("managecom", managecom);
					sqlbv16.put("agentcode", agentcode);
					tempSSRS4 = tExeSQL.execSQL(sqlbv16);
					String tAgentManage = tempSSRS4.GetText(1, 1)
							+ tempSSRS4.GetText(1, 2) + tempSSRS4.GetText(1, 3);

					strArr[3] = tAgentManage;

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
			xmlexport.createDocument("MonthXQFeeState.vts", "PRINT");

			// 最好紧接着就初始化xml文档
			texttag.add("sum", tSumMoney);
			texttag.add("startdate", mStartDate);
			texttag.add("enddate", mEndDate);
			texttag.add("managecom", comname);
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
				strSql = "select a.contno,a.agentcode,a.paytodate,a.appntname "
						+ "  from lcpol a, ljspay b "
						+ " where othernotype = '3' "
						+ " and a.payintv = '1' "
						+ "   and a.contno = b.otherno "
						+ "   and a.polno = a.mainpolno "
						+ "   and b.startpaydate >= '"
						+ "?mStartDate?"
						+ "' "
						+ "   and b.startpaydate <= '"
						+ "?mEndDate?"
						+ "' "
						+ "   and b.agentcode = '"
						+ "?mAgentCode?"
						+ "' "
						+ " order by a.agentcode, a.agentgroup, a.contno, a.paytodate ";

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
				strSql = "select a.contno,a.agentcode,a.paytodate,a.appntname "
						+ "  from lcpol a, ljspay b "
						+ " where othernotype = '3' "
						+ " and a.payintv = '1' "
						+ "   and a.contno = b.otherno "
						+ "   and a.polno = a.mainpolno "
						+ "   and b.startpaydate >= '"
						+ "?mStartDate?"
						+ "' "
						+ "   and b.startpaydate <= '"
						+ "?mEndDate?"
						+ "' "
						+ "   and b.managecom like concat('"
						+ "?mManageCom?"
						+ "','%') "
						+ " order by a.agentcode, a.agentgroup, a.contno, a.paytodate ";

			}
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.sql(strSql);
			sqlbv17.put("mStartDate", mStartDate);
			sqlbv17.put("mEndDate", mEndDate);
			sqlbv17.put("mAgentCode", mAgentCode);
			sqlbv17.put("mManageCom", mManageCom);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv17);

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
			tlistTable.setName("MonthXQFeeState");
			for (int i = 1; i <= tSSRS.MaxRow; i++) {

				strArr = new String[15];

				strArr[0] = tSSRS.GetText(i, 1); // 保单号
				strArr[1] = "未收"; // 保单状态
				strArr[2] = tSSRS.GetText(i, 2); // 维护人员
				// strArr[3] = tAgentGroup;//人员机构
				strArr[4] = tSSRS.GetText(i, 3); // 交费对应日
				strArr[5] = tSSRS.GetText(i, 4); // 投保人

				SSRS tempSSRS = new SSRS(); // 交费方式
				String sql = "";
				sql = "select paylocation from lccont where contno='"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
				sqlbv18.sql(sql);
				sqlbv18.put("contno", tSSRS.GetText(i, 1));
				tempSSRS = tExeSQL.execSQL(sqlbv18);

				if (tempSSRS.GetText(1, 1).equals("0")) {
					strArr[6] = "银行";
				} else if (tempSSRS.GetText(1, 1).equals("1")) {
					strArr[6] = "柜台";
				} else {
					strArr[6] = "上门";
				}

				sql = "select sumduepaymoney,bankaccno,PayDate from ljspay where otherno='"
						+ "?otherno?" + "' and othernotype='3'";
				SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				sqlbv19.sql(sql);
				sqlbv19.put("otherno", tSSRS.GetText(i, 1));
				SSRS tempSSRS1 = new SSRS();
				tempSSRS1 = tExeSQL.execSQL(sqlbv19);
				strArr[7] = tempSSRS1.GetText(1, 2); // 帐号
				strArr[11] = tempSSRS1.GetText(1, 1); // 应缴保费
				SumMoney = SumMoney + parseFloat(tempSSRS1.GetText(1, 1));

				sql = "select max(paycount) from ljapayperson where contno = '"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
				sqlbv20.sql(sql);
				sqlbv20.put("contno", tSSRS.GetText(i, 1));
				SSRS tempSSRS2 = new SSRS();
				tempSSRS2 = tExeSQL.execSQL(sqlbv20);
				String paycount = tempSSRS2.GetText(1, 1);
				strArr[10] = String.valueOf(Integer.parseInt(paycount) + 1); // 应交费次数
				strArr[9] = paycount; // 已交费次数
				strArr[8] = String.valueOf(Integer.parseInt(paycount) / 12);
				; // 交费年度

				sql = "select  concat((select placename from ldaddress where placetype = '02' and placecode = b.city),b.postaladdress),b.zipcode,b.phone from  lcaddress b ,lcappnt c "
						+ " where c.appntno = b.customerno and c.addressno =  b.addressno "
						+ " and  c.contno='" + "?contno?" + "'";
				SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
				sqlbv21.sql(sql);
				sqlbv21.put("contno", tSSRS.GetText(i, 1));
				SSRS tempSSRS3 = new SSRS();
				tempSSRS3 = tExeSQL.execSQL(sqlbv21);
				strArr[12] = tempSSRS3.GetText(1, 1); // 地址
				strArr[13] = tempSSRS3.GetText(1, 2); // 邮编
				strArr[14] = tempSSRS3.GetText(1, 3); // 电话

				// 人员机构
				sql = "select c.agentcode, a.managecom from laagent a,lccont c where trim(c.agentcode) = a.agentcode and c.contno = '"
						+ "?contno?" + "' ";
				SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
				sqlbv22.sql(sql);
				sqlbv22.put("contno", tSSRS.GetText(i, 1));
				SSRS tempSSRS4 = new SSRS();
				tempSSRS4 = tExeSQL.execSQL(sqlbv22);

				String agentcode = tempSSRS4.GetText(1, 1);
				String managecom = tempSSRS4.GetText(1, 2);
				SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
				sqlbv23.sql("select (select name from labranchgroup where managecom ='"
						+ "?managecom?"
						+ "' and branchattr = (select Substr(branchattr,1,4) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
						+ "?agentcode?"
						+ "' ))), "
						+ " (select name from labranchgroup where managecom ='"
						+ "?managecom?"
						+ "' and branchattr = (select Substr(branchattr,1,7) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
						+ "?agentcode?"
						+ "' ))), "
						+ "  ((select name from labranchgroup where managecom ='"
						+ "?managecom?"
						+ "' and agentgroup = (select branchcode from laagent where agentcode ='"
						+ "?agentcode?" + "' ))) from dual ");
				sqlbv23.put("managecom", managecom);
				sqlbv23.put("agentcode", agentcode);
				tempSSRS4 = tExeSQL.execSQL(sqlbv23);
				String tAgentManage = tempSSRS4.GetText(1, 1)
						+ tempSSRS4.GetText(1, 2) + tempSSRS4.GetText(1, 3);

				strArr[3] = tAgentManage;

				tlistTable.add(strArr);

			}
			String tSumMoney = new DecimalFormat("0.00").format(SumMoney);
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			xmlexport.createDocument("MonthXQFeeState.vts", "PRINT");

			// 最好紧接着就初始化xml文档
			texttag.add("sum", tSumMoney);
			texttag.add("startdate", mStartDate);
			texttag.add("enddate", mEndDate);
			texttag.add("managecom", comname);
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
				strSql = "select a.contno,a.agentcode,a.appntname  from lcpol a where exists  "
						+ "(select contno  from LJaPayPerson b "
						+ " where b.LastPayToDate >= '"
						+ "?mStartDate?"
						+ "'  and b.LastPayToDate <= '"
						+ "?mEndDate?"
						+ "' "
						+ "and b.paycount > 1 and b.managecom like '"
						+ "?mManageCom?"
						+ "%' "
						+ "and exists (select 'X' from ljapay c  where c.othernotype = '3' "
						+ "and b.getnoticeno = c.getnoticeno)  and b.contno = a.contno )  and a.payintv = '1' and polno = mainpolno  and agentcode = '"
						+ "?mAgentCode?"
						+ "' and managecom like '"
						+ "?mManageCom?"
						+ "%'"
						+ " order by agentcode, agentgroup,contno,paytodate ";

			} else {
				strSql = "select a.contno,a.agentcode,a.appntname  from lcpol a where exists  "
						+ "(select contno  from LJaPayPerson b "
						+ " where b.LastPayToDate >= '"
						+ "?mStartDate?"
						+ "'  and b.LastPayToDate <= '"
						+ "?mEndDate?"
						+ "' "
						+ "and b.paycount > 1 and b.managecom like '"
						+ "?mManageCom?"
						+ "%' "
						+ "and exists (select 'X' from ljapay c  where c.othernotype = '3' "
						+ "and b.getnoticeno = c.getnoticeno)  and b.contno = a.contno )  and a.payintv = '1' and polno = mainpolno "
						+ " and a.managecom like '"
						+ "?mManageCom?"
						+ "%'"
						+ " order by agentcode, agentgroup,contno,paytodate ";

			}
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			sqlbv24.sql(strSql);
			sqlbv24.put("mStartDate", mStartDate);
			sqlbv24.put("mEndDate", mEndDate);
			sqlbv24.put("mManageCom", mManageCom);
			sqlbv24.put("mAgentCode", mAgentCode);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv24);
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
			tlistTable.setName("MonthXQFeeState");
			for (int i = 1; i <= tSSRS.MaxRow; i++) {

				strArr = new String[15];

				strArr[0] = tSSRS.GetText(i, 1); // 保单号
				strArr[1] = "已收"; // 保单状态
				strArr[2] = tSSRS.GetText(i, 2); // 维护人员
				// strArr[3] = tAgentGroup;//人员机构

				String sql = "";
				SSRS tempSSRS5 = new SSRS();
				sql = "select lastpaytodate  from ljapayperson where paycount in (select max(paycount) from ljapayperson  where contno = '"
						+ "?contno?"
						+ "') and contno = '"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
				sqlbv25.sql(sql);
				sqlbv25.put("contno", tSSRS.GetText(i, 1));
				tempSSRS5 = tExeSQL.execSQL(sqlbv25);
				strArr[4] = tempSSRS5.GetText(1, 1); // 交费对应日
				strArr[5] = tSSRS.GetText(i, 3); // 投保人

				SSRS tempSSRS = new SSRS(); // 交费方式
				sql = "select paylocation from lccont where contno='"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
				sqlbv26.sql(sql);
				sqlbv26.put("contno", tSSRS.GetText(i, 1));
				tempSSRS = tExeSQL.execSQL(sqlbv26);

				if (tempSSRS.GetText(1, 1).equals("0")) {
					strArr[6] = "银行";
				} else if (tempSSRS.GetText(1, 1).equals("1")) {
					strArr[6] = "柜台";
				} else {
					strArr[6] = "上门";
				}

				sql = "select SumActuPayMoney,BankAccNo from ljapay where otherno='"
						+ "?otherno?"
						+ "' and othernotype='3' and  confdate in (select max(confdate) from ljapayperson where contno = '"
						+ "?otherno?" + "' and paytype = 'ZC')";
				SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
				sqlbv27.sql(sql);
				sqlbv27.put("otherno", tSSRS.GetText(i, 1));
				SSRS tempSSRS1 = new SSRS();
				tempSSRS1 = tExeSQL.execSQL(sqlbv27);
				strArr[7] = tempSSRS1.GetText(1, 2); // 帐号
				strArr[11] = tempSSRS1.GetText(1, 1); // 应缴保费
				SumMoney = SumMoney + parseFloat(tempSSRS1.GetText(1, 1));

				sql = "select max(paycount) from ljapayperson where contno = '"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
				sqlbv28.sql(sql);
				sqlbv28.put("contno", tSSRS.GetText(i, 1));
				SSRS tempSSRS2 = new SSRS();
				tempSSRS2 = tExeSQL.execSQL(sqlbv28);
				String paycount = tempSSRS2.GetText(1, 1);
				strArr[10] = paycount; // 应交费次数
				strArr[9] = paycount; // 已交费次数
				strArr[8] = String.valueOf(Integer.parseInt(paycount) / 12);
				; // 交费年度

				sql = "select  concat((select placename from ldaddress where placetype = '02' and placecode = b.city),b.postaladdress),b.zipcode,b.phone from  lcaddress b ,lcappnt c "
						+ " where c.appntno = b.customerno and c.addressno =  b.addressno "
						+ " and  c.contno='" + "?contno?" + "'";
				SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
				sqlbv29.sql(sql);
				sqlbv29.put("contno", tSSRS.GetText(i, 1));
				SSRS tempSSRS3 = new SSRS();
				tempSSRS3 = tExeSQL.execSQL(sqlbv29);
				strArr[12] = tempSSRS3.GetText(1, 1); // 地址
				strArr[13] = tempSSRS3.GetText(1, 2); // 邮编
				strArr[14] = tempSSRS3.GetText(1, 3); // 电话

				// 人员机构
				sql = "select c.agentcode, a.managecom from laagent a,lccont c where trim(c.agentcode) = a.agentcode and c.contno = '"
						+ "?contno?" + "' ";
				SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
				sqlbv30.sql(sql);
				sqlbv30.put("contno", tSSRS.GetText(i, 1));
				SSRS tempSSRS4 = new SSRS();
				tempSSRS4 = tExeSQL.execSQL(sqlbv30);

				String agentcode = tempSSRS4.GetText(1, 1);
				String managecom = tempSSRS4.GetText(1, 2);
				SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
				sqlbv31.sql("select (select name from labranchgroup where managecom ='"
						+ "?managecom?"
						+ "' and branchattr = (select Substr(branchattr,1,4) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
						+ "?agentcode?"
						+ "' ))), "
						+ " (select name from labranchgroup where managecom ='"
						+ "?managecom?"
						+ "' and branchattr = (select Substr(branchattr,1,7) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
						+ "?agentcode?"
						+ "' ))), "
						+ "  ((select name from labranchgroup where managecom ='"
						+ "?managecom?"
						+ "' and agentgroup = (select branchcode from laagent where agentcode ='"
						+ "?agentcode?" + "' ))) from dual ");
				sqlbv31.put("managecom", managecom);
				sqlbv31.put("agentcode", agentcode);
				tempSSRS4 = tExeSQL.execSQL(sqlbv31);
				String tAgentManage = tempSSRS4.GetText(1, 1)
						+ tempSSRS4.GetText(1, 2) + tempSSRS4.GetText(1, 3);

				strArr[3] = tAgentManage;

				tlistTable.add(strArr);

			}

			String tSumMoney = new DecimalFormat("0.00").format(SumMoney);

			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			xmlexport.createDocument("MonthXQFeeState.vts", "PRINT");

			// 最好紧接着就初始化xml文档
			texttag.add("sum", tSumMoney);
			texttag.add("startdate", mStartDate);
			texttag.add("enddate", mEndDate);
			texttag.add("managecom", comname);
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

	/**
	 * 当没有代理人 这个时候需要打印一个机构所有业务员的打印处理
	 * 
	 * @return boolean
	 */
	private boolean getPrintManageData() {
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
		//
		// if (mStartDate.substring(5, 7).equals(mEndDate.substring(5, 7))) {
		// tMonnth = mStartDate.substring(5, 7);
		// }
		// else {
		// tMonnth = //mStartDate.substring(5, 7) + "," +
		// mEndDate.substring(5, 7);
		//
		// }
		//
		ExeSQL exeSql = new ExeSQL();
		SSRS tzuSSRS = new SSRS();
		// tzuSSRS = exeSql.execSQL("select laagent.name from labranchgroup,
		// laagent where trim(laagent.agentgroup) =
		// trim(labranchgroup.agentgroup) and trim(laagent.agentcode) ='" +
		// mAgentCode + "'");
		// if(tzuSSRS.MaxRow==0)
		// {
		//
		// tAgentName = "未查到数据";
		// }else{
		//
		// tAgentName = tzuSSRS.GetText(1,1);
		// }

		// tzuSSRS = exeSql.execSQL("select (select name from labranchgroup
		// where managecom ='"+mManageCom+"' and branchattr = (select
		// Substr(branchattr,0,4) from labranchgroup where agentgroup = (select
		// branchcode from laagent where agentcode ='"+mAgentCode+"' ))), "
		// +" (select name from labranchgroup where managecom ='"+mManageCom+"'
		// and branchattr = (select Substr(branchattr,0,7) from labranchgroup
		// where agentgroup = (select branchcode from laagent where agentcode
		// ='"+mAgentCode+"' ))), "
		// +" ((select name from labranchgroup where managecom ='"+mManageCom+"'
		// and agentgroup = (select branchcode from laagent where agentcode
		// ='"+mAgentCode+"' ))) from dual ");
		// tAgentGroup = tzuSSRS.GetText(1,1) + tzuSSRS.GetText(1,2) +
		// tzuSSRS.GetText(1,3);
		//

		String[] ContractTitle = new String[14]; // 随意定义的与显示无关
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
		logger.debug("程序执行标志＝＝＝＝＝＝" + mOperation);
		int k = 0; // 银行件数
		int j = 0; // 上门件数
		int m = 0; //
		if (mOperation.equals("01")) {
			String strArr[] = null;
			ListTable tlistTable = new ListTable();

			for (int s = 0; s < mAgent.length; s++) {
				mAgentCode = mAgent[s];
				String strSql = "";
				// and agentcode = '" + mAgentCode + "'
				strSql = "select * from lcpol a where "
						+ " agentcode = '"
						+ "?mAgentCode?"
						+ "' and polno=mainpolno and  paytodate>'"
						+ "?mStartDate?"
						+ "' and paytodate<'"
						+ "?mEndDate?"
						+ "' and contno in (select otherno from ljspay where othernotype='2') order by paytodate";
				SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
				sqlbv32.sql(strSql);
				sqlbv32.put("mAgentCode", mAgentCode);
				sqlbv32.put("mStartDate", mStartDate);
				sqlbv32.put("mEndDate", mEndDate);
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv32);
				totalcount = totalcount + tSSRS.getMaxRow();
				if (tSSRS.getMaxRow() == 0) {
					break;

				}
				logger.debug("打印的行数＝＝＝＝＝＝" + tSSRS.MaxRow);

				tlistTable.setName("RISK");
				for (int i = 1; i <= tSSRS.MaxRow; i++) {

					strArr = new String[14];
					strArr[0] = tSSRS.GetText(i, 37);
					strArr[1] = tSSRS.GetText(i, 3);
					// 查询投保人
					// logger.debug("进入实际查询中。。。。。");
					SSRS tempSSRS = new SSRS();
					String sql = "";

					strArr[2] = tSSRS.GetText(i, 30);
					// 通讯地址 电话 邮编

					sql = "select postaladdress,phone,zipcode,customerno From lcaddress where customerno = (select appntno from lccont where contno='"
							+ "?contno?" + "')";
					SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
					sqlbv33.sql(sql);
					sqlbv33.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv33);
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
					strArr[6] = tSSRS.GetText(i, 61);
					sql = " select sum(a.SumActuPayMoney) from ljspayperson a, ljspay b, lcpol d where b.otherno = a.contno  "
							+ " and a.GetNoticeNo = b.GetNoticeNo  and b.OtherNoType = '2'  and a.contno = '"
							+ "?contno?"
							+ "' "
							+ " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno ";
					SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
					sqlbv34.sql(sql);
					sqlbv34.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv34);
					if (tempSSRS.MaxRow != 0 && tempSSRS.GetText(1, 1) != null
							&& !tempSSRS.GetText(1, 1).equals("null")) {
						strArr[7] = tempSSRS.GetText(1, 1);

					} else {
						strArr[7] = "0";

					}

					// 健康加费
					sql = "select sum(prem) from lcprem where (payplantype ='01' or payplantype='11') and contno='"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
					sqlbv35.sql(sql);
					sqlbv35.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv35);
					if (tempSSRS.MaxRow > 0) {
						strArr[8] = tempSSRS.GetText(1, 1);

					} else {
						strArr[8] = "0";

					}
					// 职业加费
					sql = "select sum(prem) from lcprem where (payplantype='02' or payplantype='12') and contno='"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
					sqlbv36.sql(sql);
					sqlbv36.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv36);
					if (tempSSRS.MaxRow > 0) {
						strArr[9] = tempSSRS.GetText(1, 1);

					} else {
						strArr[9] = "0";

					}
					// 应收保费
					sql = "select sumduepaymoney,bankaccno,PayDate from ljspay where otherno='"
							+ "?otherno?" + "' and othernotype='2'";
					SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
					sqlbv37.sql(sql);
					sqlbv37.put("otherno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv37);
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
					String tempgetpayendyear = tSSRS.GetText(i, 44);
					String templocation = "";

					String payintv = "";
					String payendyear = "";
					if (tempgetpayendyear.equals("1000")) {
						payendyear = "终身";
					} else {
						payendyear = tempgetpayendyear;
					}

					sql = "select paylocation from lccont where contno='"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
					sqlbv38.sql(sql);
					sqlbv38.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv38);
					if (tempSSRS.MaxRow == 0) {

					} else {
						templocation = tempSSRS.GetText(1, 1);
					}

					if (templocation.equals("0")) {
						payintv = "银行自动转帐";
						k = k + 1;
					} else if (templocation.equals("1")) {
						payintv = "柜台交费";
						m = m + 1;
					}

					else {
						payintv = "人工收取";
						j = j + 1;
					}
					strArr[11] = payendyear;
					strArr[12] = payintv;

					// 账号
					strArr[13] = bankno;
					tlistTable.add(strArr);

				}
				// 已收**************************************************************
				// *****************************************************************
				// ****************************************************************
				strSql = "select * from lcpol a where"
						+ " agentcode = '"
						+ "?mAgentCode?"
						+ "'  and polno=mainpolno  and contno in (select contno from LJAPayPerson where LastPayToDate>='"
						+ "?mStartDate?" + "' and LastPayToDate<='" + "?mEndDate?"
						+ "') order by paytodate";
				SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
				sqlbv39.sql(strSql);
				sqlbv39.put("mAgentCode", mAgentCode);
				sqlbv39.put("mStartDate", mStartDate);
				sqlbv39.put("mEndDate", mEndDate);
				tSSRS = tExeSQL.execSQL(sqlbv39);
				totalcount = totalcount + tSSRS.getMaxRow();
				if (tSSRS.getMaxRow() == 0) {
					break;

				}
				logger.debug("打印的行数＝＝＝＝＝＝" + tSSRS.MaxRow);

				if (flag1.equals("1") && flag2.equals("1")) {
					buildError("getPrintData", "没有要打印的数据！！");
					return false;
				}

				tlistTable.setName("RISK");
				for (int i = 1; i <= tSSRS.MaxRow; i++) {
					SSRS tswd = new SSRS();
					SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
					sqlbv40.sql("select lastpaytodate  from ljapayperson where paycount in (select max(paycount) from ljapayperson  where contno = '"
							+ "?contno?"
							+ "') and contno = '"
							+ "?contno?" + "'");
					sqlbv40.put("contno", tSSRS.GetText(i, 3));
					tswd = tExeSQL.execSQL(sqlbv40);
					strArr = new String[14];
					strArr[0] = tswd.GetText(1, 1);
					strArr[1] = tSSRS.GetText(i, 3);
					// 查询投保人
					// logger.debug("进入实际查询中。。。。。");
					SSRS tempSSRS = new SSRS();
					String sql = "";

					strArr[2] = tSSRS.GetText(i, 30);
					// 通讯地址 电话 邮编
					sql = "select postaladdress,phone,zipcode,customerno From lcaddress where customerno = (select appntno from lccont where contno='"
							+ "?contno?" + "')";
					SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
					sqlbv41.sql(sql);
					sqlbv41.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv41);
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
					strArr[6] = tSSRS.GetText(i, 61);
					SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
					sqlbv42.sql(" select sum(a.SumActuPayMoney) from ljspayperson a, ljspay b, lcpol d where b.otherno = a.contno  "
							+ " and a.GetNoticeNo = b.GetNoticeNo  and b.OtherNoType = '2'  and a.contno = '"
							+ "?contno?"
							+ "' "
							+ " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno ");
					sqlbv42.put("contno", tSSRS.GetText(i, 3));
					tzuSSRS = exeSql.execSQL(sqlbv42); // lihuan加
					if (tzuSSRS.MaxRow != 0 && tzuSSRS.GetText(1, 1) != null
							&& !tzuSSRS.GetText(1, 1).equals("null")) {
						strArr[7] = tzuSSRS.GetText(1, 1);
					}

					else {
						SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
						sqlbv43.sql(" select sum(a.SumActuPayMoney) from ljapayperson a, ljapay b, lcpol d where b.otherno = a.contno  "
								+ " and a.GetNoticeNo = b.GetNoticeNo  and b.OtherNoType = '2'  and a.contno = '"
								+ "?contno?"
								+ "' "
								+ " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno and a.confdate in (select max(confdate) from ljapayperson where contno = '"
								+ "?contno?"
								+ "'  and paytype = 'ZC')");
						sqlbv43.put("contno", tSSRS.GetText(i, 3));
						tzuSSRS = exeSql.execSQL(sqlbv43);

						if (tzuSSRS.MaxRow != 0
								&& tzuSSRS.GetText(1, 1) != null
								&& !tzuSSRS.GetText(1, 1).equals("null")) {

							strArr[7] = tzuSSRS.GetText(1, 1);

						}
					}

					// 健康加费
					sql = "select sum(prem) from lcprem where (payplantype ='01' or payplantype='11') and contno='"
							+ "?contno?" + "' ";
					SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
					sqlbv44.sql(sql);
					sqlbv44.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv44);
					if (tempSSRS.MaxRow > 0) {
						strArr[8] = tempSSRS.GetText(1, 1);

					} else {
						strArr[8] = "0";

					}
					// 职业加费
					sql = "select sum(prem) from lcprem where (payplantype='02' or payplantype='12') and contno='"
							+ "?contno?" + "' ";
					SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
					sqlbv45.sql(sql);
					sqlbv45.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv45);
					if (tempSSRS.MaxRow > 0) {
						strArr[9] = tempSSRS.GetText(1, 1);

					} else {
						strArr[9] = "0";

					}
					// 实收保费
					sql = "select SumActuPayMoney,BankAccNo,PayDate from ljapay where otherno='"
							+ "?otherno?" + "' and othernotype='2'";
					SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
					sqlbv46.sql(sql);
					sqlbv46.put("otherno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv46);
					String bankno = "";
					if (tempSSRS.MaxRow > 0) {
						strArr[10] = tempSSRS.GetText(1, 1);
						// strArr[0] = tempSSRS.GetText(1, 3);
						SumMoney = SumMoney
								+ parseFloat(tempSSRS.GetText(1, 1));
						bankno = tempSSRS.GetText(1, 2);

					} else {
						// strArr[0] = "未录入";
						strArr[10] = "0";

					}
					// 缴费年期 缴费形式
					String tempgetpayendyear = tSSRS.GetText(i, 44);
					String templocation = "";

					String payintv = "";
					String payendyear = "";
					if (tempgetpayendyear.equals("1000")) {
						payendyear = "终身";
					} else {
						payendyear = tempgetpayendyear;
					}

					sql = "select paylocation from lccont where contno='"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
					sqlbv47.sql(sql);
					sqlbv47.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv47);
					if (tempSSRS.MaxRow == 0) {

					} else {
						templocation = tempSSRS.GetText(1, 1);
					}

					if (templocation.equals("0")) {
						payintv = "银行自动转帐";
						k = k + 1;
					} else if (templocation.equals("1")) {
						payintv = "柜台交费";
						m = m + 1;
					}

					else {
						payintv = "人工收取";
						j = j + 1;
					}
					strArr[11] = payendyear;
					strArr[12] = payintv;

					// 账号
					strArr[13] = bankno;

					tlistTable.add(strArr);

				}
			}

			String tSumMoney = new DecimalFormat("0.00").format(SumMoney);
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			xmlexport.createDocument("Yslist.vts", "PRINT"); // 最好紧接着就初始化xml文档
			texttag.add("RiskFee", tSumMoney);
			texttag.add("Count", totalcount);
			texttag.add("cCount", m);
			texttag.add("EFTCount", k);
			texttag.add("ServeCount", j);
			texttag.add("LCCont.AgentCode", " ");
			texttag.add("LAAgent.Name", " ");
			texttag.add("Year", tYear);
			texttag.add("Month", tMonnth);
			// texttag.add("type", "应收清单");
			texttag.add("Name", " ");
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			xmlexport.addListTable(tlistTable, ContractTitle);
			// xmlexport.outputDocumentToFile("e:\\", "atest"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);

		}
		if (mOperation.equals("02")) {

			String strArr[] = null;
			ListTable tlistTable = new ListTable();
			tlistTable.setName("RISK");

			for (int s = 0; s < mAgent.length; s++) {
				mAgentCode = mAgent[s];

				String strSql = "";
				// and agentcode = '" + mAgentCode + "'
				strSql = "select * from lcpol a where "
						+ "   agentcode = '"
						+ "?mAgentCode?"
						+ "' and  paytodate>'"
						+ "?mStartDate?"
						+ "' and paytodate<'"
						+ "?mEndDate?"
						+ "' and polno=mainpolno  and contno in (select otherno from ljspay where othernotype='2') order by paytodate";
				SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
				sqlbv48.sql(strSql);
				sqlbv48.put("mAgentCode", mAgentCode);
				sqlbv48.put("mStartDate", mStartDate);
				sqlbv48.put("mEndDate", mEndDate);
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv48);
				totalcount = totalcount + tSSRS.getMaxRow();
				if (tSSRS.getMaxRow() == 0) {
					break;
				}
				logger.debug("打印的行数＝＝＝＝＝＝" + tSSRS.MaxRow);

				tlistTable.setName("RISK");
				for (int i = 1; i <= tSSRS.MaxRow; i++) {

					strArr = new String[14];
					strArr[0] = tSSRS.GetText(i, 37);
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
					sql = "select postaladdress,phone,zipcode,customerno From lcaddress where customerno = (select appntno from lccont where contno='"
							+ "?contno?" + "')";
					SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
					sqlbv49.sql(sql);
					sqlbv49.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv49);
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
					strArr[6] = tSSRS.GetText(i, 61);
					// sql="select sum(prem) from lcpol where polno=mainpolno
					// and contno='"+tSSRS.GetText(i,3)+"' group by polno";
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
							+ " and a.GetNoticeNo = b.GetNoticeNo  and b.OtherNoType = '2'  and a.contno = '"
							+ "?contno?"
							+ "' "
							+ " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno ";
					SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
					sqlbv50.sql(sql);
					sqlbv50.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv50);
					if (tempSSRS.MaxRow != 0 && tempSSRS.GetText(1, 1) != null
							&& !tempSSRS.GetText(1, 1).equals("null")) {
						strArr[7] = tempSSRS.GetText(1, 1);

					} else {
						strArr[7] = "0";

					}

					// 健康加费
					sql = "select sum(prem) from lcprem where (payplantype ='01' or payplantype='11') and contno='"
							+ "?contno?" + "' ";
					SQLwithBindVariables sqlbv51 = new SQLwithBindVariables();
					sqlbv51.sql(sql);
					sqlbv51.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv51);
					if (tempSSRS.MaxRow > 0) {
						strArr[8] = tempSSRS.GetText(1, 1);

					} else {
						strArr[8] = "0";

					}
					// 职业加费
					sql = "select sum(prem) from lcprem where (payplantype='02' or payplantype='12') and contno='"
							+ "?contno?" + "' ";
					SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
					sqlbv52.sql(sql);
					sqlbv52.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv52);
					if (tempSSRS.MaxRow > 0) {
						strArr[9] = tempSSRS.GetText(1, 1);

					} else {
						strArr[9] = "0";

					}
					// 应收保费
					sql = "select sumduepaymoney,bankaccno,PayDate from ljspay where otherno='"
							+ "?otherno?" + "' and othernotype='2'";
					SQLwithBindVariables sqlbv53 = new SQLwithBindVariables();
					sqlbv53.sql(sql);
					sqlbv53.put("otherno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv53);
					String bankno = "";
					if (tempSSRS.MaxRow > 0) {
						strArr[10] = tempSSRS.GetText(1, 1);
						// strArr[0] = tempSSRS.GetText(1, 3);
						SumMoney = SumMoney
								+ parseFloat(tempSSRS.GetText(1, 1));
						bankno = tempSSRS.GetText(1, 2);

					} else {
						// strArr[0] ="未录入";
						strArr[10] = "0";

					}
					// 缴费年期 缴费形式
					String tempgetpayendyear = tSSRS.GetText(i, 44);
					String templocation = "";

					String payintv = "";
					String payendyear = "";
					if (tempgetpayendyear.equals("1000")) {
						payendyear = "终身";
					} else {
						payendyear = tempgetpayendyear;
					}

					sql = "select paylocation from lccont where contno='"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv54 = new SQLwithBindVariables();
					sqlbv54.sql(sql);
					sqlbv54.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv54);
					if (tempSSRS.MaxRow == 0) {

					} else {
						templocation = tempSSRS.GetText(1, 1);
					}

					if (templocation.equals("0")) {
						payintv = "银行自动转帐";
						k = k + 1;
					} else if (templocation.equals("1")) {
						payintv = "柜台交费";
						m = m + 1;
					}

					else {
						payintv = "人工收取";
						j = j + 1;
					}
					strArr[11] = payendyear;
					strArr[12] = payintv;

					// 账号
					strArr[13] = bankno;
					tlistTable.add(strArr);

				}
			}
			String tSumMoney = new DecimalFormat("0.00").format(SumMoney);
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			xmlexport.createDocument("ungottenlist.vts", "PRINT"); // 最好紧接着就初始化xml文档
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
			String strArr[] = null;
			ListTable tlistTable = new ListTable();
			tlistTable.setName("RISK");

			for (int s = 0; s < mAgent.length; s++) {
				mAgentCode = mAgent[s];

				String strSql = "";
				// and agentcode = '" + mAgentCode + "'
				strSql = "select * from lcpol a where"
						+ "  agentcode = '"
						+ "?mAgentCode?"
						+ "'  and polno=mainpolno  and contno in (select contno from LJAPayPerson where LastPayToDate>='"
						+ "?mStartDate?" + "' and LastPayToDate<='" + "?mEndDate?"
						+ "') order by paytodate";
				SQLwithBindVariables sqlbv55 = new SQLwithBindVariables();
				sqlbv55.sql(strSql);
				sqlbv55.put("mAgentCode", mAgentCode);
				sqlbv55.put("mStartDate", mStartDate);
				sqlbv55.put("mEndDate", mEndDate);
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv55);
				totalcount = totalcount + tSSRS.getMaxRow();
				if (tSSRS.getMaxRow() == 0) {
					break;
				}
				logger.debug("打印的行数＝＝＝＝＝＝" + tSSRS.MaxRow);

				tlistTable.setName("RISK");
				for (int i = 1; i <= tSSRS.MaxRow; i++) {

					strArr = new String[14];
					strArr[0] = tSSRS.GetText(i, 37);
					strArr[1] = tSSRS.GetText(i, 3);
					// 查询投保人
					// logger.debug("进入实际查询中。。。。。");
					SSRS tempSSRS = new SSRS();
					String sql = "";

					strArr[2] = tSSRS.GetText(i, 30);
					// 通讯地址 电话 邮编
					sql = "select postaladdress,phone,zipcode,customerno From lcaddress where customerno = (select appntno from lccont where contno='"
							+ "?contno?" + "')";
					SQLwithBindVariables sqlbv56 = new SQLwithBindVariables();
					sqlbv56.sql(sql);
					sqlbv56.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv56);
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
					strArr[6] = tSSRS.GetText(i, 61);
					SQLwithBindVariables sqlbv57 = new SQLwithBindVariables();
					sqlbv57.sql(" select sum(a.SumActuPayMoney) from ljspayperson a, ljspay b, lcpol d where b.otherno = a.contno  "
							+ " and a.GetNoticeNo = b.GetNoticeNo  and b.OtherNoType = '2'  and a.contno = '"
							+ "?contno?"
							+ "' "
							+ " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno ");
					sqlbv57.put("contno", tSSRS.GetText(i, 3));
					tzuSSRS = exeSql.execSQL(sqlbv57); // lihuan加
					if (tzuSSRS.MaxRow != 0 && tzuSSRS.GetText(1, 1) != null
							&& !tzuSSRS.GetText(1, 1).equals("null")) {
						strArr[7] = tzuSSRS.GetText(1, 1);
					}

					else {
						SQLwithBindVariables sqlbv58 = new SQLwithBindVariables();
						sqlbv58.sql(" select sum(a.SumActuPayMoney) from ljapayperson a, ljapay b, lcpol d where b.otherno = a.contno  "
								+ " and a.GetNoticeNo = b.GetNoticeNo  and b.OtherNoType = '2'  and a.contno = '"
								+ "?contno?"
								+ "' "
								+ " and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno and a.confdate in (select max(confdate) from ljapayperson where contno = '"
								+ "?contno?"
								+ "'  and paytype = 'ZC')");
						sqlbv58.put("contno", tSSRS.GetText(i, 3));
						tzuSSRS = exeSql.execSQL(sqlbv58);

						if (tzuSSRS.MaxRow != 0
								&& tzuSSRS.GetText(1, 1) != null
								&& !tzuSSRS.GetText(1, 1).equals("null")) {

							strArr[7] = tzuSSRS.GetText(1, 1);

						}
					}

					// 健康加费
					sql = "select sum(prem) from lcprem where (payplantype ='01' or payplantype='11') and contno='"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv59 = new SQLwithBindVariables();
					sqlbv59.sql(sql);
					sqlbv59.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv59);
					if (tempSSRS.MaxRow > 0) {
						strArr[8] = tempSSRS.GetText(1, 1);

					} else {
						strArr[8] = "0";

					}
					// 职业加费
					sql = "select sum(prem) from lcprem where (payplantype='02' or payplantype='12') and contno='"
							+ "?contno?" + "' ";
					SQLwithBindVariables sqlbv60 = new SQLwithBindVariables();
					sqlbv60.sql(sql);
					sqlbv60.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv60);
					if (tempSSRS.MaxRow > 0) {
						strArr[9] = tempSSRS.GetText(1, 1);

					} else {
						strArr[9] = "0";

					}
					// 实收保费
					sql = "select SumActuPayMoney,BankAccNo,PayDate from ljapay where otherno='"
							+ "?otherno?" + "' and othernotype='2'";
					SQLwithBindVariables sqlbv61 = new SQLwithBindVariables();
					sqlbv61.sql(sql);
					sqlbv61.put("otherno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv61);
					String bankno = "";
					if (tempSSRS.MaxRow > 0) {
						strArr[10] = tempSSRS.GetText(1, 1);
						// strArr[0] = tempSSRS.GetText(1, 3);
						SumMoney = SumMoney
								+ parseFloat(tempSSRS.GetText(1, 1));
						bankno = tempSSRS.GetText(1, 2);

					} else {
						// strArr[0] = "未录入";
						strArr[10] = "0";

					}
					// 缴费年期 缴费形式
					String tempgetpayendyear = tSSRS.GetText(i, 44);
					String templocation = "";

					String payintv = "";
					String payendyear = "";
					if (tempgetpayendyear.equals("1000")) {
						payendyear = "终身";
					} else {
						payendyear = tempgetpayendyear;
					}

					sql = "select paylocation from lccont where contno='"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv62 = new SQLwithBindVariables();
					sqlbv62.sql(sql);
					sqlbv62.put("contno", tSSRS.GetText(i, 3));
					tempSSRS = tExeSQL.execSQL(sqlbv62);
					if (tempSSRS.MaxRow == 0) {

					} else {
						templocation = tempSSRS.GetText(1, 1);
					}

					if (templocation.equals("0")) {
						payintv = "银行自动转帐";
						k = k + 1;
					} else if (templocation.equals("1")) {
						payintv = "柜台交费";
						m = m + 1;
					}

					else {
						payintv = "人工收取";
						j = j + 1;
					}
					strArr[11] = payendyear;
					strArr[12] = payintv;
					// 账号
					strArr[13] = bankno;
					tlistTable.add(strArr);

				}

			}
			String tSumMoney = new DecimalFormat("0.00").format(SumMoney);

			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			xmlexport.createDocument("gottenlist.vts", "PRINT"); // 最好紧接着就初始化xml文档
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

}
