package com.sinosoft.lis.f1print;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
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

public class BankPayNoticeBL {
private static Logger logger = Logger.getLogger(BankPayNoticeBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String mCustomerid = ""; // 客户代码
	private String mCustomername = ""; // 帐户类型
	private String mchantypecode; // 统计的开始时间
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
	private int mMaxMonth = 0;
	private int mMinMonth = 0;
	private int mMaxYear = 0;
	private int mMinYear = 0;
	private String magentcode;
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private VData mLOPRTManagerSchemas = new VData();

	public BankPayNoticeBL() {
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
		// mchantypecode = (String) cInputData.get(2);
		// return true;
		mLOPRTManagerSet = (LOPRTManagerSet) cInputData.getObjectByObjectName(
				"LOPRTManagerSet", 0);
		logger.debug("LOPRTManagerSet.size="
				+ String.valueOf(mLOPRTManagerSet.size()));
		if (mLOPRTManagerSet == null) {
			buildError("getInputData", "没有获得打印管理表数据的信息！");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "BankPayNoticeBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	// 查询出首期或者是续期的银行代收正确清单
	private boolean getPrintData() {
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("HastenBank.vts", "PRINT");
		ListTable alistTable = new ListTable();
		alistTable.setName("CONT");

		//
		// //转账成功的清单，要查询应收和实收的并集因为在没有进行核销时，仍为应收信息
		// //打印成功的清单
		//
		// 收入的统计
		logger.debug("BankPayNoticeBL");
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
		logger.debug("最外层循环m=" + String.valueOf(tCount) + "注意");
		// LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		// tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSet.get(i).getPrtSeq());
		// LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// tLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sec_sqla = "select a.otherno,a.startpaydate,( select (case when sum(sumduepaymoney) is not null then sum(sumduepaymoney)  else 0 end) from ljspayperson b, lcpol c  where c.mainpolno = c.polno and c.polno = b.polno and b.contno = c.contno and b.getnoticeno = a.getnoticeno) as mainprem,"
				+ " ( select (case when sum(sumduepaymoney) is not null then sum(sumduepaymoney)  else 0 end) from ljspayperson b, lcpol c  where c.mainpolno = c.polno and c.polno <> b.polno and b.contno = c.contno and b.getnoticeno = a.getnoticeno) as subprem,"
				+ " (select BankAccNo from lccont where ContNo=a.otherno ) as BankAccNo , a.SumDuePayMoney ,a.agentcode "

				+ " from loprtmanager t, ljspay a  where t.code = '35' and t.standbyflag2 = a.getnoticeno "
				+ " and a.othernotype = '3'  and t.PrtSeq in " + "?PrtSeq_sql?";
		// mLOPRTManagerSet.get(m).getPrtSeq() + "'";
		sqlbv.sql(sec_sqla);
		sqlbv.put("PrtSeq_sql", PrtSeq_sql);
		logger.debug("查询的语句是" + sec_sqla);
		ExeSQL sec_exesqla = new ExeSQL();
		SSRS sec_ssrsa = sec_exesqla.execSQL(sqlbv);
		String[] cols = null;
		if (sec_ssrsa.getMaxRow() > 0) {
			logger.debug("查询的结果是" + sec_ssrsa.getMaxRow());

			// 对其明细信息进行附值
			mColnum = sec_ssrsa.getMaxCol();
			for (int i = 1; i <= sec_ssrsa.getMaxRow(); i++) {
				logger.debug("第二层循环i=" + String.valueOf(i) + "注意");
				cols = new String[mColnum];
				for (int j = 0; j <= mColnum - 1; j++) {

					logger.debug("第三层循环j=" + String.valueOf(j) + "注意");
					if (sec_ssrsa.GetText(i, j + 1) == null
							|| sec_ssrsa.GetText(i, j + 1).equals("null")) {
						cols[j] = " ";
						continue;
					}
					if (j == 5) {
						mSumMoney = mSumMoney
								+ Double.parseDouble(sec_ssrsa
										.GetText(i, j + 1));
					}
					if (j == 6) {
						magentcode = sec_ssrsa.GetText(i, j + 1);
					}
					if (j == 1) {
						Date mdate = new FDate().getDate(sec_ssrsa.GetText(i,
								j + 1));
						if (i == 1) {
							mMinYear = mMaxYear = mdate.getYear() + 1900;
							mMinMonth = mMaxMonth = mdate.getMonth() + 1;
						}
						if (mMinYear > mdate.getYear() + 1900) {
							mMinYear = mdate.getYear() + 1900;
						}
						if (mMaxYear < mdate.getYear() + 1900) {
							mMaxYear = mdate.getYear() + 1900;
						}
						if (mMinMonth > mdate.getMonth() + 1) {
							mMinMonth = mdate.getMonth() + 1;
						}
						if (mMaxMonth < mdate.getMonth() + 1) {
							mMaxMonth = mdate.getMonth() + 1;
						}

					}
					if (j == 3) {
						if (sec_ssrsa.GetText(i, j + 1) == null
								|| sec_ssrsa.GetText(i, j + 1).equals("null")) {
							cols[j] = "0";
						}
					}

					if (j != 5 || j != 6) {
						cols[j] = sec_ssrsa.GetText(i, j + 1).trim();
					}
				}
				alistTable.add(cols);
				mCount1 = mCount1 + 1;
			}
		}
		// 向容器中转入相应的数据
		if (mCount1 > 0) {
			logger.debug("================开始加数据了===================");
			xmlexport.addDisplayControl("displayinfo");
			// //////////////////////客户名称
			xmlexport.addListTable(alistTable, cols);

			// texttag.add("SysDate", PubFun.getCurrentDate());
			// custom_sql =
			// "select a.CustomerNo,max(a.name),max(b.AddressNo) from ldperson a, LCAddress
			// b"
			// + " where b.CustomerNo = a.customerno and a.customerno = '" +
			// mCustomerid + "' group by a.CustomerNo";
			// logger.debug("客户名称:" + custom_sql);
			// ExeSQL custom_exesql = new ExeSQL();
			// SSRS custom_ssrs = custom_exesql.execSQL(custom_sql);
			// if (custom_ssrs.getMaxRow() == 1) {
			// logger.debug("客户名称是" + custom_ssrs.getMaxRow());
			// mCustomername = custom_ssrs.GetText(1, 2).trim();
			// mAddressNo = custom_ssrs.GetText(1, 3).trim();
			// } else {
			// return false;
			//
			// }

			// ///////////投保人的基本信息
			//
			//
			// else {
			// this.mErrors.copyAllErrors(this.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "BankPayNoticeBL";
			// tError.functionName = "getPrintData";
			// tError.errorMessage = mMessage;
			// this.mErrors.addOneError(tError);
			// return false;
			//
			// }
		}
		ExeSQL exeSql = new ExeSQL();
		SSRS testSSRS = new SSRS();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSet.get(1).getPrtSeq());
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerDB.getInfo();
		tLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("select  concat((select placename from ldaddress where placetype = '03' "
				+ "and trim(placecode) = b.county),b.postaladdress),b.zipcode,c.appntname , (select GetAppntPhone(c.contno,'') from dual), "
				+ "b.customerno ,c.managecom, c.contno ,c.appntno "
				+ "from  lcaddress b ,lcappnt c "
				+ "where c.appntno = b.customerno and c.addressno =  b.addressno "
				+ "and  c.contno='"
				+ "?contno?"
				+ "'");
		sqlbv1.put("contno", tLOPRTManagerSchema.getOtherNo());
		testSSRS = exeSql.execSQL(sqlbv1);
		if (testSSRS.getMaxRow() == 0) {
			buildError("getPrintData", "在取得LAAgent的数据时发生错误");
			return false;
		}
		texttag.add("Address", testSSRS.GetText(1, 1));
		texttag.add("ZipCode", testSSRS.GetText(1, 2));
		texttag.add("LCCont.AppntName", testSSRS.GetText(1, 3));
		texttag.add("PhoneNumber", testSSRS.GetText(1, 4));

		// ////////////业务员的基本信息
		ExeSQL exeagentSql = new ExeSQL();
		SSRS agentSSRS = new SSRS();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		String agent_sql = " select getGlobalBranch('" + "?var1?"
				+ "' , '" + "?var2?"
				+ "')from dual";
		sqlbv2.sql(agent_sql);
		sqlbv2.put("var1", magentcode.trim());
		sqlbv2.put("var2", tLOPRTManagerSchema.getOtherNo().trim());
		logger.debug(agent_sql);
		agentSSRS = exeagentSql.execSQL(sqlbv2);

		texttag.add("AgentGroup", agentSSRS.GetText(1, 1));
		// texttag.add("Department", agentSSRS.GetText(1, 2));
		// texttag.add("Group", agentSSRS.GetText(1, 3));
		agent_sql = " select name from laagent where agentcode = '"
				+ "?agentcode?" + "'";
		sqlbv2.sql(agent_sql);
		sqlbv2.put("agentcode", magentcode.trim());
		agentSSRS = exeagentSql.execSQL(sqlbv2);
		texttag.add("Agent", agentSSRS.GetText(1, 1));
		texttag.add("Agentcode", magentcode);
		// /////////////
		if (mMinYear == mMaxYear) {
			texttag.add("Year", String.valueOf(mMinYear));
		} else {
			texttag.add("Year", String.valueOf(mMinYear) + "至"
					+ String.valueOf(mMaxYear));
		}
		if (mMinMonth == mMaxMonth) {
			texttag.add("Month", String.valueOf(mMinMonth));
		} else {
			texttag.add("Month", String.valueOf(mMinMonth) + "至"
					+ String.valueOf(mMaxMonth));
		}
		texttag.add("SumMoney", mSumMoney);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		// xmlexport.outputDocumentToFile("d:\\",
		// "BankPayNoticeBL"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	// public String[] getSuccFlag(String aBankCode) {
	// String[] aSuccFlag = new String[10];
	// // LDBankDB tLDBankDB = new LDBankDB();
	// // tLDBankDB.setBankCode(aBankCode);
	// // tLDBankDB.getInfo();
	// //
	// // String[] arrSucc = PubFun.split(tLDBankDB.getSchema().
	// // getAgentPaySuccFlag().trim(), ";");
	// // logger.debug("数组的长度是" + arrSucc.length);
	// // for (int i = 0; i < arrSucc.length; i++) {
	// // if (!(arrSucc[i] == null || arrSucc[i].equals(""))) {
	// // aSuccFlag[i] = arrSucc[i];
	// // logger.debug("该银行的返回标志是" + arrSucc[i]);
	// // }
	// // }
	// return aSuccFlag;
	// }

	public static void main(String[] args) {
		String aCustomerid = "0000556040";
		String aCustomername = "DAN";
		String achantypecode = "3";
		VData tVData = new VData();
		tVData.addElement(aCustomerid);
		tVData.addElement(aCustomername);
		tVData.addElement(achantypecode);

		BankPayNoticeBL tBankPayNoticeBL = new BankPayNoticeBL();
		tBankPayNoticeBL.submitData(tVData, "PRINT");
	}

	private void jbInit() throws Exception {
	}
}
