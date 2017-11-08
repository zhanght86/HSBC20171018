package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class PrintCustomerBillBL {
private static Logger logger = Logger.getLogger(PrintCustomerBillBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String mCustomerNo = ""; // 客户代码
	private String mInsuAccNo = ""; // 客户帐号号码
	private String mAccType = ""; // 帐户类型
	private String mStartdate; // 统计的开始时间
	private String mEnddate; // 统计的结束时间
	// String mSuccFlag[] = new String[10];

	private int mCount2 = 0;
	private int mCount1 = 0;
	private int mColnum = 0;
	private String mMessage = "";
	private String sec_sqla;
	private String sec_sqlb;
	private String custom_sql;
	private String mCustomername;
	private double SumMoneyp = 0.00;// 支出合计
	private double SumMoneyi = 0.00;// 收入合计
	private PremBankPubFun mPremBankPubFun = new PremBankPubFun();

	public PrintCustomerBillBL() {
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
		if (mAccType.equals("002")) {
			if (!getPrintPersonData()) {
				return false;
			}
		}
		if (mAccType.equals("001")) {
			if (!getPrintGrpData()) {
				return false;
			}
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mAccType = (String) cInputData.get(0);
		mInsuAccNo = (String) cInputData.get(1);
		mCustomerNo = (String) cInputData.get(2); // 查询的标准“YF”or“YS”
		mStartdate = (String) cInputData.get(3);
		mEnddate = (String) cInputData.get(4);

		mAccType = mAccType.trim();
		mInsuAccNo = mInsuAccNo.trim();
		mCustomerNo = mCustomerNo.trim();
		mStartdate = mStartdate.trim();
		mEnddate = mEnddate.trim();
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "PrintCustomerBillBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	// 查询出首期或者是续期的银行代收正确清单
	private boolean getPrintPersonData() {
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("AccountContrastR000610.vts", "PRINT");
		ListTable alistTable = new ListTable();
		ListTable blistTable = new ListTable();
		alistTable.setName("INCOME");
		blistTable.setName("PAYMENT");
		//
		// //转账成功的清单，要查询应收和实收的并集因为在没有进行核销时，仍为应收信息
		// //打印成功的清单
		//
		// 收入的统计
		sec_sqla = "select rownum,c.OperationType,c.OtherNo,Abs(c.money),c.makedate"
				+ " from ldperson a,lccustomeracc b,lccustomeracctrace c"
				+ " where a.customerno=b.customerno and a.customerno=c.customerno and a.customerno='"
				+ "?mCustomerNo?"
				+ "' and OperFlag='1' and c.insuaccno='"
				+ "?mInsuAccNo?"
				+ "' and (c.makedate>='"
				+ "?mStartdate?"
				+ "' and c.makedate<='" + "?mEnddate?" + "')";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sec_sqla);
		sqlbv1.put("mCustomerNo", mCustomerNo);
		sqlbv1.put("mInsuAccNo", mInsuAccNo);
		sqlbv1.put("mStartdate", mStartdate);
		sqlbv1.put("mEnddate", mEnddate);
		// 对支出的统计
		sec_sqlb = "select c.OperationType,c.OtherNo,Abs(c.money),c.makedate"
				+ " from ldperson a,lccustomeracc b,lccustomeracctrace c"
				+ " where a.customerno=b.customerno and a.customerno=c.customerno and a.customerno='"
				+ mCustomerNo + "' and OperFlag='2' and c.insuaccno='"
				+ mInsuAccNo + "'  and (c.makedate>='" + mStartdate
				+ "' and c.makedate<='" + mEnddate + "')";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(sec_sqlb);
		sqlbv2.put("mCustomerNo", mCustomerNo);
		sqlbv2.put("mInsuAccNo", mInsuAccNo);
		sqlbv2.put("mStartdate", mStartdate);
		sqlbv2.put("mEnddate", mEnddate);
		// //////对收入的赋值
		logger.debug("收入的查询的语句是" + sec_sqla);
		ExeSQL sec_exesqla = new ExeSQL();
		SSRS sec_ssrsa = sec_exesqla.execSQL(sqlbv1);
		String[] cols = null;
		if (sec_ssrsa.getMaxRow() > 0) {
			logger.debug("查询的结果是" + sec_ssrsa.getMaxRow());

			// 对其明细信息进行附值
			mColnum = sec_ssrsa.getMaxCol();
			for (int i = 1; i <= sec_ssrsa.getMaxRow(); i++) {
				cols = new String[mColnum];
				for (int j = 0; j <= mColnum - 1; j++) {
					if (sec_ssrsa.GetText(i, j + 1) == null
							|| sec_ssrsa.GetText(i, j + 1).equals("null")) {
						cols[j] = " ";
						continue;
					}
					if (j == 4) {
						SumMoneyi = SumMoneyi
								+ Double.parseDouble(sec_ssrsa.GetText(i, j));
					}
					cols[j] = sec_ssrsa.GetText(i, j + 1).trim();
				}
				alistTable.add(cols);
				mCount1 = mCount1 + 1;
			}
		}
		// //////对支出的赋值
		logger.debug("支出的查询的语句是" + sec_sqlb);
		ExeSQL sec_exesqlb = new ExeSQL();
		SSRS sec_ssrsb = sec_exesqlb.execSQL(sqlbv2);
		String[] cols2 = null;
		if (sec_ssrsb.getMaxRow() > 0) {
			logger.debug("查询的结果是" + sec_ssrsb.getMaxRow());

			// 对其明细信息进行附值
			mColnum = sec_ssrsb.getMaxCol();
			for (int i = 1; i <= sec_ssrsb.getMaxRow(); i++) {
				cols2 = new String[mColnum];
				for (int j = 0; j <= mColnum - 1; j++) {
					if (sec_ssrsb.GetText(i, j + 1) == null
							|| sec_ssrsb.GetText(i, j + 1).equals("null")) {
						cols2[j] = " ";
						continue;
					}
					if (j == 3) {
						SumMoneyp = SumMoneyp
								+ Double.parseDouble(sec_ssrsb.GetText(i, j));
					}
					cols2[j] = sec_ssrsb.GetText(i, j + 1).trim();
				}
				blistTable.add(cols2);
				mCount2 = mCount2 + 1;
			}
		}
		// 向容器中转入相应的数据
		if (mCount2 > 0 || mCount1 > 0) {
			logger.debug("开始执行最外部分的循环");
			xmlexport.addDisplayControl("displayinfo");
			// //////////////////////客户名称
			xmlexport.addListTable(alistTable, cols);
			xmlexport.addListTable(blistTable, cols2);
			texttag.add("SysDate", PubFun.getCurrentDate());
			custom_sql = "select name from ldperson where CustomerNo='"
					+ "?mCustomerNo?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(custom_sql);
			sqlbv3.put("mCustomerNo", mCustomerNo);
			logger.debug("客户名称:" + custom_sql);
			ExeSQL custom_exesql = new ExeSQL();
			SSRS custom_ssrs = custom_exesql.execSQL(sqlbv3);
			if (custom_ssrs.getMaxRow() > 0) {
				logger.debug("客户名称是" + custom_ssrs.getMaxRow());
				mCustomername = custom_ssrs.GetText(1, 1).trim();
			}
			texttag.add("AppntName", mCustomername); // 客户名称
			texttag.add("SumIncome", SumMoneyi);
			texttag.add("SumPayment", SumMoneyp);
			texttag.add("StartDate", mStartdate);
			texttag.add("EndDate", mEnddate);
			// ////////////////保费合计:
			// //计算保费合计,一个批次号下所有"缴费退费金额"的合计
			// prem_sql =
			// "select sum(paymoney) from lyreturnfrombank where serialno='"
			// + mSerialNo + "' and bankcode='" + mBankCode +
			// "' and ComCode='"
			// + mStation + "' and (NoType='2' or NoType='6')";
			// logger.debug("保费合计:" + prem_sql);
			// ExeSQL prem_exesql = new ExeSQL();
			// SSRS prem_ssrs = prem_exesql.execSQL(prem_sql); //计算保费合计,一个批次号下所有"缴费退费金额"的合计
			// if (prem_ssrs.getMaxRow() > 0) {
			// logger.debug("保费合计结果是" +
			// prem_ssrs.getMaxRow());
			// mSumMoney = prem_ssrs.GetText(1, 1).trim();
			// }
			// texttag.add("SumFee", mSumMoney); //合计保费
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			// xmlexport.outputDocumentToFile("d:\\",
			// "PrintCustomerBillBL"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);
			return true;
		} else {
			// this.mErrors.copyAllErrors(this.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "PrintCustomerBillBL";
			// tError.functionName = "getPrintPersonData";
			// tError.errorMessage = mMessage;
			// this.mErrors.addOneError(tError);
			return false;

		}
	}

	private boolean getPrintGrpData() {
		return true;
	}

	public String[] getSuccFlag(String aBankCode) {
		String[] aSuccFlag = new String[10];
		// LDBankDB tLDBankDB = new LDBankDB();
		// tLDBankDB.setBankCode(aBankCode);
		// tLDBankDB.getInfo();
		//
		// String[] arrSucc = PubFun.split(tLDBankDB.getSchema().
		// getAgentPaySuccFlag().trim(), ";");
		// logger.debug("数组的长度是" + arrSucc.length);
		// for (int i = 0; i < arrSucc.length; i++) {
		// if (!(arrSucc[i] == null || arrSucc[i].equals(""))) {
		// aSuccFlag[i] = arrSucc[i];
		// logger.debug("该银行的返回标志是" + arrSucc[i]);
		// }
		// }
		return aSuccFlag;
	}

	public static void main(String[] args) {
		String aAccType = "002";
		String aInsuAccNo = "86110000000020";
		String aCustcode = "0000536520";
		String aStartdate = "2003-07-29";
		String aEnddate = "2005-08-11";
		VData tVData = new VData();
		tVData.addElement(aAccType);
		tVData.addElement(aInsuAccNo);
		tVData.addElement(aCustcode);
		tVData.addElement(aStartdate);
		tVData.addElement(aEnddate);

		PrintCustomerBillBL tPrintBillRightBL = new PrintCustomerBillBL();
		tPrintBillRightBL.submitData(tVData, "PRINT");
	}

	private void jbInit() throws Exception {
	}

}
