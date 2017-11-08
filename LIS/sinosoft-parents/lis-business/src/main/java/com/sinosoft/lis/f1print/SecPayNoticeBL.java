package com.sinosoft.lis.f1print;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCAddressSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class SecPayNoticeBL {
private static Logger logger = Logger.getLogger(SecPayNoticeBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String mCustomerid = ""; // 客户代码
	private String mCustomername = ""; // 帐户类型
	private String mdate; // 统计的开始时间
	private String mAddressNo;
	// String mSuccFlag[] = new String[10];

	private int mCount1 = 0;
	private int mColnum = 0;
	private String mMessage = "";
	private String sec_sqla;
	private String custom_sql;
	private String address_sql;
	private double mSumMoney = 0.00; // 收入合计
	private PremBankPubFun mPremBankPubFun = new PremBankPubFun();
	private LCAddressDB tLCAddressDB = new LCAddressDB();
	private LCAddressSet tLCAddressSet = new LCAddressSet();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private VData mLOPRTManagerSchemas = new VData();

	public SecPayNoticeBL() {
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

	private boolean getInputData(VData cInputData) {
		// mCustomerid = (String) cInputData.get(0);
		// mCustomername = (String) cInputData.get(1);
		// mdate = (String) cInputData.get(2);
		mLOPRTManagerSet = (LOPRTManagerSet) cInputData.getObjectByObjectName(
				"LOPRTManagerSet", 0);
		if (mLOPRTManagerSet == null) {
			buildError("getInputData", "没有获得打印管理表数据的信息！");
			return false;
		}
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSet.get(1).getPrtSeq());
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerDB.getInfo();
		tLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		mCustomerid = tLOPRTManagerSchema.getStandbyFlag1();
		mdate = tLOPRTManagerSchema.getMakeDate();
		return true;

	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "SecPayNoticeBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	// 查询出首期或者是续期的银行代收正确清单
	private boolean getPrintData() {
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("SecondHastenM.vts", "PRINT");
		ListTable alistTable = new ListTable();
		alistTable.setName("CONT");
		//
		// //转账成功的清单，要查询应收和实收的并集因为在没有进行核销时，仍为应收信息
		// //打印成功的清单
		//
		// 收入的统计
		String PrtSeq_sql = "";
		int tCount = mLOPRTManagerSet.size();
		if (tCount == 1) {
			PrtSeq_sql = "('"
					+ String.valueOf(mLOPRTManagerSet.get(1).getPrtSeq())
					+ "')";
		} else {
			for (int m = 1; m <= tCount; m++) {
				if (m == 1) {
					PrtSeq_sql = "('"
							+ String.valueOf(mLOPRTManagerSet.get(m)
									.getPrtSeq()) + "'";
				} else {
					if (m == tCount) {
						PrtSeq_sql = PrtSeq_sql
								+ ",'"
								+ String.valueOf(mLOPRTManagerSet.get(m)
										.getPrtSeq()) + "')";
					} else {
						PrtSeq_sql = PrtSeq_sql
								+ ",'"
								+ String.valueOf(mLOPRTManagerSet.get(m)
										.getPrtSeq()) + "' ";

					}
				}
			}
		}

		sec_sqla = "select a.otherno, b.STARTPAYDATE, b.SumDuePayMoney, c.name,c.(select GetAppntPhone(b.otherno,'') from dual) "
				+ " from loprtmanager a, LJSPay b,laagent c "
				+ "where trim(a.code) = 'A9' "
				// and trim(a.StandbyFlag1) = ' + mCustomerid
				// + " and a.makedate ='" + mdate + "'"
				+ " and trim(a.StandbyFlag2) = trim(b.GetNoticeNo) and trim(a.agentcode) = trim(c.agentcode)"
				+ " and a.PrtSeq in " + PrtSeq_sql;
		// //////对收入的赋值
		logger.debug("查询的语句是" + sec_sqla);
		ExeSQL sec_exesqla = new ExeSQL();
		SSRS sec_ssrsa = sec_exesqla.execSQL(sec_sqla);
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
					if (j == 3) {
						logger.debug("行" + String.valueOf(i) + "====="
								+ String.valueOf(mSumMoney));
						mSumMoney = mSumMoney
								+ Double.parseDouble(sec_ssrsa.GetText(i, j));
					}
					cols[j] = sec_ssrsa.GetText(i, j + 1).trim();
				}
				alistTable.add(cols);
				mCount1 = mCount1 + 1;
			}
		}
		// 向容器中转入相应的数据
		if (mCount1 > 0) {
			logger.debug("开始执行最外部分的循环");
			xmlexport.addDisplayControl("displayinfo");
			// //////////////////////客户名称
			xmlexport.addListTable(alistTable, cols);
			// texttag.add("SysDate", PubFun.getCurrentDate());
		}

		custom_sql = "select a.CustomerNo,max(a.name),max(b.AddressNo) from ldperson a, LCAddress b"
				+ " where b.CustomerNo = a.customerno and a.customerno = '"
				+ "?mCustomerid?" + "'  group by a.CustomerNo";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(custom_sql);
		sqlbv2.put("mCustomerid", mCustomerid);
		logger.debug("客户名称:" + custom_sql);
		ExeSQL custom_exesql = new ExeSQL();
		SSRS custom_ssrs = custom_exesql.execSQL(sqlbv2);
		if (custom_ssrs.getMaxRow() == 1) {
			logger.debug("客户名称是" + custom_ssrs.getMaxRow());
			mCustomername = custom_ssrs.GetText(1, 2).trim();
			mAddressNo = custom_ssrs.GetText(1, 3).trim();
		} else {

			return false;

		}

		texttag.add("LCCont.AppntName", mCustomername); // 客户名称
		address_sql = " select * from lcaddress where customerno = '"
				+ "?mCustomerid?" + "' and AddressNo='" + "?mAddressNo?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(address_sql);
		sqlbv3.put("mCustomerid", mCustomerid);
		sqlbv3.put("mAddressNo", mAddressNo);
		logger.debug("查询的语句是" + address_sql);
		tLCAddressSet = tLCAddressDB.executeQuery(sqlbv3);
		logger.debug("===========" + tLCAddressSet.size()
				+ "===============");
		if (tLCAddressSet.size() == 1) {

			LCAddressSchema tLCAddressSchema = new LCAddressSchema();
			tLCAddressSchema = tLCAddressSet.get(1).getSchema();

			ExeSQL t1ExeSQL = new ExeSQL();
			SSRS t1SSRS = new SSRS();
			String t1SQL = "select placename from ldaddress where "
					+ "placetype = '03' and trim(placecode) = '"
					+ "?placecode?" + "'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(t1SQL);
			sqlbv4.put("placecode", tLCAddressSchema.getCounty());
			String tAddressEX = "";
			t1SSRS = t1ExeSQL.execSQL(sqlbv4);
			if (t1SSRS.MaxRow > 0) {
				tAddressEX = t1SSRS.GetText(1, 1);
			}

			String tZipcode = tLCAddressSchema.getZipCode();
			String tPostalAddress = tAddressEX
					+ tLCAddressSchema.getPostalAddress();
			String tphone = tLCAddressSchema.getPhone();

			int p = 0;
			String tYear = "";
			String tMonth = "";
			java.util.StringTokenizer strTok = new StringTokenizer(mdate, "-");
			while (strTok.hasMoreTokens()) {
				if (p == 0) {
					tYear = strTok.nextToken();
				}
				if (p == 1) {
					tMonth = strTok.nextToken();
				}
				p++;
				if (p == 2)
					break;
			}

			texttag.add("ZipCode", tZipcode);
			texttag.add("Address", tPostalAddress);
			texttag.add("Address", tPostalAddress);
			texttag.add("PhoneNumber", tphone);
			texttag.add("SumMoney", mSumMoney);
			texttag.add("Year", tYear);
			texttag.add("Month", tMonth);

		}
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		// xmlexport.outputDocumentToFile("d:\\",
		// "SecPayNoticeBL"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;

		// else {
		// this.mErrors.copyAllErrors(this.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "SecPayNoticeBL";
		// tError.functionName = "getPrintData";
		// tError.errorMessage = mMessage;
		// this.mErrors.addOneError(tError);
		// return false;
		//
		// }
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
		String aCustomerid = "0000551840";
		String aCustomername = "朋友";
		String adate = "2005-8-13";
		VData tVData = new VData();
		tVData.addElement(aCustomerid);
		tVData.addElement(aCustomername);
		tVData.addElement(adate);

		SecPayNoticeBL tSecPayNoticeBL = new SecPayNoticeBL();
		tSecPayNoticeBL.submitData(tVData, "PRINT");
	}

	private void jbInit() throws Exception {
	}

}
