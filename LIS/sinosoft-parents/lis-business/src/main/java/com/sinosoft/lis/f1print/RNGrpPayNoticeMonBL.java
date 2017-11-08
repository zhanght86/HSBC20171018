package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: 团险续期缴费通知书</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft</p>
 * @author guanwei
 * @version 1.0
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.Vector;

import com.f1j.ss.BookModelImpl;
import com.f1j.util.F1Exception;
import com.sinosoft.lis.bq.BqPolBalBL;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDAddressDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LOPRTManagerSubDB;
import com.sinosoft.lis.pubfun.ChangeCodetoName;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LDAddressSchema;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LOPRTManagerSubSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LOPRTManagerSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class RNGrpPayNoticeMonBL {
private static Logger logger = Logger.getLogger(RNGrpPayNoticeMonBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private VData mLOPRTManagerSchemas = new VData();
	private int mSchemaNum = 0;
	private int mmonth = 0;
	private int emonth = 0;
	private int myear = 1900;
	private int eyear = 1900;

	private TransferData mTransferData = new TransferData();
	String mAppntNo = "";
	String mAppntName = "";

	public RNGrpPayNoticeMonBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			logger.debug("cOperate:" + cOperate);
			if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")
					&& !cOperate.equals("BATCHCONFIRM")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			if (cOperate.equals("BATCHCONFIRM")) {
				if (!getBatchInputData(cInputData)) {
					return false;
				}
				mResult.clear();
				// 准备所有要打印的数据
				getBatchPrintData();
			} else {
				// 得到外部传入的数据，将数据备份到本类中（不管有没有operate,都要执行这一部）
				if (!getInputData(cInputData)) {
					return false;
				}

				if (cOperate.equals("CONFIRM")) {
					mResult.clear();
					// 准备所有要打印的数据
					getPrintData();
				} else if (cOperate.equals("PRINT")) {
					if (!saveData(cInputData)) {
						return false;
					}
				}
			}
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", ex.toString());
			return false;
		}

	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		mLOPRTManagerSet = (LOPRTManagerSet) cInputData.getObjectByObjectName(
				"LOPRTManagerSet", 0);
		if (mLOPRTManagerSet == null) {
			buildError("getInputData", "没有获得打印管理表数据的信息！");
			return false;
		}

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			buildError("getInputData", "没有获得TransferData数据的信息！");
			return false;
		}

		mAppntNo = (String) mTransferData.getValueByName("AppntNo");
		mAppntName = (String) mTransferData.getValueByName("AppntName");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getBatchInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mAppntNo = (String) mTransferData.getValueByName("AppntNo");
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RNGrpPayNoticeMonBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);

	}

	private boolean getPrintData() {

		String tmMouth = "";
		int lxcount = 0;
		ExeSQL exeSql = new ExeSQL();
		SSRS testSSRS = new SSRS();
		SSRS tzuSSRS = new SSRS();
		SSRS tySSRS = new SSRS();
		SSRS t1SSRS = new SSRS();
		testSSRS = exeSql
				.execSQL("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
		String strTemplatePath = testSSRS.GetText(1, 1);
		// 数据库中的路径-模板路径D:/lis/ui/f1print/NCLtemplate/
		testSSRS = exeSql
				.execSQL("select SysVarValue from ldsysvar where Sysvar='VTSFilePath'");
		String strVTSFilePath = testSSRS.GetText(1, 1);
		testSSRS = exeSql
				.execSQL("select SysVarValue from ldsysvar where Sysvar='VTSRealPath'");
		strVTSFilePath = testSSRS.GetText(1, 1) + strVTSFilePath;
		Vector tVector = new Vector();
		int fff = 0;
		int tCount = mLOPRTManagerSet.size();

		logger.debug("tCount==" + tCount);
		int page = 0;
		if (tCount % 3 == 0) {
			page = tCount / 3;
			lxcount = 3;
		} else {
			page = tCount / 3 + 1;
			lxcount = tCount % 3;
		}
		logger.debug("page==" + page);
		String[] strVFPathName = new String[page];
		int pageFlag;
		int x = 0;
		double tSumPrem = 0;
		String[] count = null;
		XmlExport xmlExportAll = new XmlExport();
		xmlExportAll.createDocuments("printer", mGlobalInput);
		for (int j = 0; j < page; j++) {
			if (j + 1 == page) {
				count = new String[lxcount];
			} else {
				count = new String[3];
			}
			int p = 0;
			pageFlag = 1;
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			xmlExport.createDocument("RNGrpPayNoticeMon.vts", "printer"); // 最好紧接着就初始化xml文
			SSRS tSrs = new SSRS();
			LOPRTManagerDB tsLOPRTManagerDB = new LOPRTManagerDB();
			tsLOPRTManagerDB.setPrtSeq(mLOPRTManagerSet.get(1).getPrtSeq());
			tsLOPRTManagerDB.getInfo();
			int f = 0;
			tSrs = exeSql.execSQL("select appntno from lccont where contno = '"
					+ tsLOPRTManagerDB.getOtherNo() + "'");

			logger.debug("=========================tSrs======================="
							+ tSrs.GetText(1, 1));
			String tappntinfo = " select t.Province,t.City,t.County,"
					+ "        t.PostalAddress,t.ZipCode,"
					+ "        n.appntname,t.Phone,n.ManageCom ,"
					+ "        n.ContNo ,n.appntno ,n.appntsex,"
					+ "        n.IDType,n.IDNo"
					+ "   from lcaddress t,lcappnt n "
					+ "  where 1=1 and n.addressno = t.addressno"
					+ " and t.customerno = n.appntno ";
			if (tSrs.getMaxRow() != 0) {
				tappntinfo += " and n.appntno = '" + tSrs.GetText(1, 1) + "'";
			}

			testSSRS = exeSql.execSQL(tappntinfo);
			if (testSSRS.getMaxRow() == 0) {
				buildError("getPrintData", "在取得LAAgent的数据时发生错误");
				return false;
			}
			String tAppntSex = testSSRS.GetText(1, 11);
			String tIDType = testSSRS.GetText(1, 12);
			String tIDNo = testSSRS.GetText(1, 13);
			String tIDSex = "";
			if (!tIDNo.equals("") && !tIDNo.equals("null")
					&& !tIDNo.equals(null)) {
				tIDSex = tIDNo.substring(tIDNo.length() - 1, tIDNo.length());
			}
			String txsAppntName = "";
			if (tAppntSex.equals("0") || tAppntSex.equals("1")) {
				if (tAppntSex.equals("0")) {
					txsAppntName = testSSRS.GetText(1, 6) + "先生";
				} else
					txsAppntName = testSSRS.GetText(1, 6) + "女士";
			} else {
				if (tIDType.equals("0")
						&& (tIDSex.equals("1") || tIDSex.equals("3")
								|| tIDSex.equals("5") || tIDSex.equals("7") || tIDSex
								.equals("9"))) {
					txsAppntName = testSSRS.GetText(1, 6) + "先生";
				}
				if (tIDType.equals("0")
						&& (tIDSex.equals("2") || tIDSex.equals("4")
								|| tIDSex.equals("6") || tIDSex.equals("8") || tIDSex
								.equals("0"))) {
					txsAppntName = testSSRS.GetText(1, 6) + "女士";
				} else {
					txsAppntName = testSSRS.GetText(1, 6) + "客户";
				}
			}
			FDate tfDate = new FDate();
			texttag.add("Address", getPlaceName(testSSRS.GetText(1, 1), "01")
					+ getPlaceName(testSSRS.GetText(1, 2), "02")
					+ getPlaceName(testSSRS.GetText(1, 3), "03")
					+ testSSRS.GetText(1, 4));
			texttag.add("ZipCode", testSSRS.GetText(1, 5));
			texttag.add("LCCont.AppntName", txsAppntName);
			texttag.add("PhoneNumber", testSSRS.GetText(1, 7));

			texttag.add("No", testSSRS.GetText(1, 10));
			texttag.add("ManageComName", getManageComName(testSSRS
					.GetText(1, 8)));

			for (int i = j * 3 + 1; i <= j * 3 + 3; i++) {
				boolean tXQExt = false;
				String tStart = "";
				String InterestDate = "";
				double mCountResult = 0;
				logger.debug("i==" + i);
				/*----------BL---------------------*/
				if (pageFlag == 1)
					x = 1;
				if (i > tCount) { // 已经没有需要打印的通知书
					continue;
				}
				// 取得loprtmanager表数据
				LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
				tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSet.get(i).getPrtSeq());
				if (tLOPRTManagerDB.getInfo() == false) {
					mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
					buildError("getPrintData", "在取得LOPRTManager的数据时发生错误");
					return false;
				}
				LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
				tLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

				// 取得lccont表数据
				LCContSchema tLCContSchema = new LCContSchema();
				LCContDB tLCContDB = new LCContDB();
				tLCContSchema = new LCContSchema();
				tLCContDB.setContNo(tLOPRTManagerSchema.getOtherNo());
				if (!tLCContDB.getInfo()) {
					this.buildError("getPrintData", "查询合同表出错！");
				}
				int tComYear = 0;
				int tComDate = 0;
				tLCContSchema = tLCContDB.getSchema();
				FDate fa = new FDate();

				// 取得loprtmanagerSub表数据
				LOPRTManagerSubDB tLOPRTManagerSubDB = new LOPRTManagerSubDB();
				tLOPRTManagerSubDB.setPrtSeq(tLOPRTManagerSchema.getPrtSeq());
				LOPRTManagerSubSet tLOPRTManagerSubSet = new LOPRTManagerSubSet();
				tLOPRTManagerSubSet = tLOPRTManagerSubDB.query();

				if (tLOPRTManagerSubSet == null) {
					mErrors.copyAllErrors(tLOPRTManagerSubDB.mErrors);
					buildError("getPrintData", "在取得LOPRTManager的数据时发生错误");
					return false;
				}
				String tPayToDate = null;
				double tPrem = 0;
				String tNoticeNo = "";
				SSRS tljsSSRS = new SSRS();
				SSRS tljaSSRS = new SSRS();
				// 判断本期应缴保费和上期实缴保费相同的保单号是否发生了金额的变化，如果变化则提示用户
				tljsSSRS = exeSql
						.execSQL("select a.riskcode , a.sumactupaymoney from ljapayperson a , lcpol b where lastpaytodate in ( "
								+ "  select max(lastpaytodate) from ljapayperson a, "
								+ " lcpol b where a.contno = b.contno and "
								+ " b.polno = b.mainpolno and a.contno = '"
								+ tLCContSchema.getContNo()
								+ " ') "
								+ " and a.contno = b.contno and a.contno = '"
								+ tLCContSchema.getContNo()
								+ " ' and a.polno < > b.mainpolno "
								+ " group by a.riskcode, a.sumactupaymoney ");

				tljaSSRS = exeSql.execSQL("select a.riskcode, "
						+ " a.sumactupaymoney from ljspayperson a, "
						+ " lcpol b where a.contno = b.contno "
						+ " and a.contno = '" + tLCContSchema.getContNo()
						+ "' and a.polno <> " + " b.mainpolno "
						+ " group by a.riskcode, a.sumactupaymoney ");
				if (tljaSSRS.getMaxRow() != 0 && tljsSSRS.getMaxRow() != 0
						&& tljaSSRS.GetText(1, 1) != null
						&& tljsSSRS.GetText(1, 1) != null)
					for (int a = 0; a < tljsSSRS.getMaxRow(); a++) {
						for (int s = 0; s < tljaSSRS.getMaxRow(); s++) {
							if (tljsSSRS.GetText(a + 1, 1).equals(
									tljaSSRS.GetText(s + 1, 1))) {
								if (!tljsSSRS.GetText(a + 1, 2).equals(
										tljaSSRS.GetText(s + 1, 2)))
									tXQExt = true;
							}

						}
					}
				if (tXQExt) {
					tStart = "*";
				}

				for (int m = 1; m <= tLOPRTManagerSubSet.size(); m++) {
					LOPRTManagerSubSchema tLOPRTManagerSubSchema = new LOPRTManagerSubSchema();
					tLOPRTManagerSubSchema = tLOPRTManagerSubSet.get(m);
					tNoticeNo = tLOPRTManagerSubSchema.getGetNoticeNo();
					LCPolDB tLCPolDB = new LCPolDB();
					LCPolSet tLCPolSet = new LCPolSet();
					tLCPolDB.setContNo(tLCContSchema.getContNo());
					tLCPolDB.setRiskCode(tLOPRTManagerSubSchema.getRiskCode());
					tLCPolSet = tLCPolDB.query();
					if (tLCPolSet == null) {
						mErrors.copyAllErrors(tLCPolDB.mErrors);
						buildError("getPrintData", "在取得lcpol的数据时发生错误");
						return false;
					}
					SSRS tSSRS = new SSRS();
					tSSRS = exeSql
							.execSQL("select riskshortname from lmrisk where 1=1 "
									+ " and riskcode = '"
									+ tLOPRTManagerSubSchema.getRiskCode()
									+ "'");
					if (tSSRS.getMaxRow() == 0) {
						buildError("getPrintData", "在取得险种名称时发生错误");
						return false;
					}
					logger.debug("riskshortname==" + tSSRS.GetText(1, 1));
					logger.debug("RiskNamettt==" + "RiskName" + x
							+ (m - 1));
					SSRS tsdSSRS = new SSRS();
					tsdSSRS = exeSql
							.execSQL("    select sum(b.SumduePayMoney) from ljspayperson b  where b.contno = '"
									+ tLOPRTManagerSchema.getOtherNo()
									+ "' "
									+ " and b.riskcode = '"
									+ tLOPRTManagerSubSchema.getRiskCode()
									+ "'");

					texttag.add("RiskName" + x + (m - 1), tSSRS.GetText(1, 1));
					texttag.add("PremStandard" + x + (m - 1),
							new DecimalFormat("0.00").format(Double
									.parseDouble(tsdSSRS.GetText(1, 1))));
					tPrem = tPrem + Double.parseDouble(tsdSSRS.GetText(1, 1)); // new
					// DecimalFormat("0.00").format(Double.parseDouble(tsdSSRS.GetText(1,1))
					if (tPayToDate == null) {
						tPayToDate = tLCPolSet.get(1).getPaytoDate();
						if (fa.getDate(tPayToDate) != null
								&& !fa.getDate(tPayToDate).equals("null")
								&& !fa.getDate(tPayToDate).equals("")) {
							tComDate = fa.getDate(tPayToDate).getMonth() + 1;
							tComYear = Integer.parseInt(tPayToDate.substring(0,
									4));
							if (mmonth == 0) {
								mmonth = emonth;
							}

							if (tComDate > mmonth) {
								if (tComDate > emonth) {
									emonth = tComDate;
									mmonth = tComDate;
									if (tCount == 1) {
										mmonth = tComDate;
									}

								} else {
									mmonth = tComDate;
								}
							} else {
								mmonth = tComDate;
							}

							if (tComYear > myear) {
								if (tComYear > eyear) {
									eyear = tComYear;
									logger.debug("=============================tComYear > myear================================="
													+ tComYear);
								}
							} else {
								myear = tComYear;
								logger.debug("============else============="
												+ "" + "tComYear");
							}

						}

					}

					BqPolBalBL tBqPolBalBL = new BqPolBalBL();
					for (int q = 1; q <= tLCPolSet.size(); q++) {
						if (tBqPolBalBL.calAutoPayInterest(tLCPolSet.get(q)
								.getPolNo(), tPayToDate)) {
							mCountResult += tBqPolBalBL.getCalResult();

						}

					}

				}
				LAAgentSchema tLAAgentSchema = new LAAgentSchema();
				LAAgentDB tLAAgentDB = new LAAgentDB();
				tLAAgentDB.setAgentCode(tLCContSchema.getAgentCode());
				if (!tLAAgentDB.getInfo()) {
					mErrors.copyAllErrors(tLAAgentDB.mErrors);
					buildError("getPrintData", "在取得LAAgent的数据时发生错误");
					return false;
				}
				tLAAgentSchema = tLAAgentDB.getSchema();
				/*----------BL-----------end----------*/
				// 计算组别的
				// 找出一张单子中代理机构和AgentCode的值
				tySSRS = exeSql
						.execSQL("select managecom , agentcode  from lccont where contno = '"
								+ tLCContSchema.getContNo() + "'");
				t1SSRS = exeSql
						.execSQL("select (select name from labranchgroup where managecom ='"
								+ tySSRS.GetText(1, 1)
								+ "' and branchattr = (select Substr(branchattr,1,4) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
								+ tySSRS.GetText(1, 2)
								+ "' ))), "
								+ " (select name from labranchgroup where managecom ='"
								+ tySSRS.GetText(1, 1)
								+ "' and branchattr = (select Substr(branchattr,1,7) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
								+ tySSRS.GetText(1, 2)
								+ "' ))), "
								+ "  ((select name from labranchgroup where managecom ='"
								+ tySSRS.GetText(1, 1)
								+ "' and agentgroup = (select branchcode from laagent where agentcode ='"
								+ tySSRS.GetText(1, 2) + "' ))) from dual ");
				String tAgentManage = t1SSRS.GetText(1, 1)
						+ t1SSRS.GetText(1, 2) + t1SSRS.GetText(1, 3);
				tzuSSRS = exeSql
						.execSQL("select name ,phone from laagent where agentcode = '"
								+ tySSRS.GetText(1, 2) + "'");
				count[p] = tAgentManage + "  " + tzuSSRS.GetText(1, 1) + " "
						+ tySSRS.GetText(1, 2) + "  " + tzuSSRS.GetText(1, 2);
				if (count[p].equals("") || count[p] == null
						|| count[p].equals("null")) {
					count[p] = " ";
				}
				p++;
				// 计算每个保单的利息的
				String tSelfMoney = "";
				String tInterest = "";
				String tEndDate = "";
				SSRS tfinSSRS = new SSRS();
				tfinSSRS = exeSql
						.execSQL("select sum(summoney) from loloan where loantype ='1' and polno in (select polno from lcpol where contno ='"
								+ tLCContSchema.getContNo() + "') ");
				logger.debug(exeSql);
				tSelfMoney = tfinSSRS.GetText(1, 1);
				if (tSelfMoney.equals("null") || tSelfMoney.equals("")
						|| tSelfMoney == null) {
					tSelfMoney = "0";
				}
				logger.debug("======================================================"
								+ tSelfMoney);
				texttag.add("SelfMoney" + x, new DecimalFormat("0.00")
						.format(Double.parseDouble(tSelfMoney)));
				texttag.add("Interest" + x, new DecimalFormat("0.00")
						.format(mCountResult));
				logger.debug(mCountResult);
				texttag.add("EndDate" + x, tPayToDate);
				texttag.add("Year", eyear);
				texttag.add("Month", mmonth);

				xmlExport.addDisplayControl("displayNotice" + x);
				texttag.add("NoticeNo" + x, tNoticeNo);
				texttag.add("ContNo" + x, tLCContSchema.getContNo() + tStart);
				texttag.add("PayDate" + x, tPayToDate);
				texttag
						.add("Prem" + x, new DecimalFormat("0.00")
								.format(tPrem));
				texttag.add("Account" + x, tLCContSchema.getBankAccNo());
				texttag.add("ServicePerson" + x, tLAAgentSchema.getName());
				texttag.add("ServicePhone" + x, tLAAgentSchema.getPhone());
				tSumPrem = tSumPrem + tPrem;
				x++;
				pageFlag = 0;
				modifLOPRT(tLOPRTManagerSchema);

			}

			if (mmonth == emonth) {
				tmMouth = String.valueOf(mmonth);
			} else {
				tmMouth = mmonth + "至" + emonth;

			}

			texttag.add("tMonth", tmMouth);

			if (j == page - 1) { // 仅是在最后一页打印本月合计
				texttag.add("SumPrem", new DecimalFormat("0.00")
						.format(tSumPrem));
			}

			ListTable tlistTable = new ListTable();
			String strArr[] = null;
			tlistTable.setName("AppendWork");

			RNGrpPayNoticeMonBL pay = new RNGrpPayNoticeMonBL();

			String Strbuffer = pay.getString(count);
			int tLength = 0;
			StringTokenizer tTokize = new StringTokenizer(Strbuffer, ",");
			if (tTokize.hasMoreTokens()) {
				tLength++;
			}
			String[] tsqString = new String[tLength];
			int zu = 0;
			if (tTokize.hasMoreTokens()) {
				tsqString[zu] = tTokize.nextToken();
				zu++;
			}
			strArr = new String[1];
			for (int s = 0; s < tsqString.length; s++) {

				strArr[0] = tsqString[s];
				tlistTable.add(strArr);
			}

			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
				xmlExport.addListTable(tlistTable, strArr);
			}
			xmlExportAll.addDataSet(
					xmlExportAll.getDocument().getRootElement(), xmlExport
							.getDocument().getRootElement());
			CombineVts tcombineVts = null;
			tcombineVts = new CombineVts(xmlExport.getInputStream(),
					strTemplatePath);

			ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			tcombineVts.output(dataStream);
			strVFPathName[j] = strVTSFilePath + j + "RNGrpPayNoticeMon.vts";
			// 把dataStream存储到磁盘文件
			AccessVtsFile.saveToFile(dataStream, strVFPathName[j]);
		}
		VtsFileCombine vtsfilecombine = new VtsFileCombine();
		BookModelImpl tb = vtsfilecombine.dataCombine(strVFPathName);
		String allname = strVTSFilePath + "HastenPersonal"
				+ FileQueue.getFileName() + ".vts";
		try {
			vtsfilecombine.write(tb, allname);
		} catch (IOException ex) {
		} catch (F1Exception ex) {
		}
		xmlExportAll.setTemplateName("RNGrpPayNoticeMon.vts");
		mResult.clear();
		mResult.addElement(allname);
		mResult.addElement(xmlExportAll);

		return true;
	}

	/**
	 * getManageComName 获得管理机构的汉化
	 * 
	 * @param tManageCom
	 *            String
	 * @return String
	 */
	private String getManageComName(String tManageCom) {
		String tManageComName = "";
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(tManageCom);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			buildError("getManageComName", "在取得ldcom的数据时发生错误");
			return "";
		}
		LDComSchema tLDComSchema = new LDComSchema();
		tLDComSchema = tLDComDB.getSchema();
		tManageComName = tLDComSchema.getName();
		return tManageComName;
	}

	/**
	 * getPlaceName 获得地名的汉化
	 * 
	 * @param string
	 *            String
	 * @param string1
	 *            String
	 * @return String
	 */
	private String getPlaceName(String tPlaceCode, String tPlaceType) {
		if (tPlaceCode == null || tPlaceCode.equals("null")
				|| tPlaceCode.equals(""))
			return "";
		String tPlaceName = "";
		LDAddressDB tLDAddressDB = new LDAddressDB();
		tLDAddressDB.setPlaceCode(tPlaceCode);
		tLDAddressDB.setPlaceType(tPlaceType);
		LDAddressSchema tLDAddressSchema = new LDAddressSchema();
		tLDAddressSchema = tLDAddressDB.getSchema();
		tPlaceName = tLDAddressSchema.getPlaceName();
		return tPlaceName;
	}

	private boolean getBatchPrintData() {
		String flag = null;
		XmlExport xmlexport = null;
		ListTable tListTable = null;
		String[] strArr = null;
		LOPRTManagerDB tLOPRTManagerDB;
		for (int i = 0; i < mSchemaNum; i++) {
			tLOPRTManagerDB = new LOPRTManagerDB();
			tLOPRTManagerDB
					.setPrtSeq(((LOPRTManagerSchema) mLOPRTManagerSchemas
							.getObjectByObjectName("LOPRTManagerSchema", i))
							.getPrtSeq());
			if (tLOPRTManagerDB.getInfo() == false) {
				mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
				buildError("outputXML", "在取得LOPRTManager的数据时发生错误");
				return false;
			}

			LJSPaySet tLJSPaySet = new LJSPaySet();
			LJSPayDB tLJSPayDB = new LJSPayDB();
			tLJSPayDB.setOtherNo(tLOPRTManagerDB.getOtherNo());
			tLJSPaySet = tLJSPayDB.query();
			if (tLJSPaySet == null) {
				mErrors.copyAllErrors(tLJSPayDB.mErrors);
				buildError("outputXML", "在取得LJSPay的数据时发生错误");
				return false;
			}
			LJSPaySchema tLJSPaySchema = tLJSPaySet.get(1);
			logger.debug("-------Prtseq-------"
					+ tLOPRTManagerDB.getPrtSeq());
			logger.debug("------type-----"
					+ tLJSPaySchema.getOtherNoType());
			if (tLJSPaySchema.getOtherNoType().equals("1")) {
				flag = "GrpNo";
			} else if (tLJSPaySchema.getOtherNoType().equals("2")) {
				flag = "PerNo";
			}

			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("PRTSEQ", tLOPRTManagerDB.getPrtSeq());
			texttag.add("GetNoticeNo", tLJSPaySchema.getGetNoticeNo());
			texttag.add("OtherNo", tLJSPaySchema.getOtherNo());
			texttag.add("SumDuePayMoney", tLJSPaySchema.getSumDuePayMoney());

			texttag.add("AgentCode", tLJSPaySchema.getAgentCode());
			texttag.add("AgentName", ChangeCodetoName
					.getAgentName(tLJSPaySchema.getAgentCode()));

			if (flag.equals("GrpNo")) {
				LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
				tLCGrpPolDB.setGrpPolNo(tLJSPaySchema.getOtherNo());
				if (!tLCGrpPolDB.getInfo()) {
					mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
					buildError("outputXML", "在取得LCGrpPol的数据时发生错误");
					return false;
				}
				if (xmlexport == null) {
					xmlexport = new XmlExport(); // 新建一个XmlExport的实例
					xmlexport
							.createDocument("RNGrpPayNoticeMon.vts", "printer"); // 最好紧接着就初始化xml文档
				}
				texttag.add("GrpName", tLCGrpPolDB.getGrpName());
				texttag.add("PayDate", tLCGrpPolDB.getPaytoDate());
				LDComDB tLDComDB = new LDComDB();
				tLDComDB.setComCode(tLCGrpPolDB.getManageCom());
				if (!tLDComDB.getInfo()) {
					mErrors.copyAllErrors(tLDComDB.mErrors);
					buildError("outputXML", "在取得LDCom的数据时发生错误");
					return false;
				}
				texttag.add("ManageCom", tLDComDB.getName());
			}

			if (flag.equals("PerNo")) {
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(tLJSPaySchema.getOtherNo());
				if (!tLCPolDB.getInfo()) {
					mErrors.copyAllErrors(tLCPolDB.mErrors);
					buildError("outputXML", "在取得LCPol的数据时发生错误");
					return false;
				}
				if (xmlexport == null) {
					xmlexport = new XmlExport(); // 新建一个XmlExport的实例
				}
				texttag.add("AppntName", tLCPolDB.getAppntName());
				texttag.add("PayDate", tLCPolDB.getPaytoDate());
				LDComDB tLDComDB = new LDComDB();
				tLDComDB.setComCode(tLCPolDB.getManageCom());
				if (!tLDComDB.getInfo()) {
					mErrors.copyAllErrors(tLDComDB.mErrors);
					buildError("outputXML", "在取得LDCom的数据时发生错误");
					return false;
				}
				texttag.add("ManageCom", tLDComDB.getName());
			}
			xmlexport.addListTables(tListTable, strArr, texttag);
		}
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;

	}

	private boolean modifLOPRT(LOPRTManagerSchema tLOPRTManagerSchema) {
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(tLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		VData tVData = new VData();
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		// mLOPRTManagerSchema.setStateFlag("1");
		mLOPRTManagerSchema.setDoneDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setExeOperator(mGlobalInput.Operator);

		MMap tMap = new MMap();
		tMap.put(mLOPRTManagerSchema, "UPDATE");
		tVData.add(tMap);
		PubSubmit tpub = new PubSubmit();
		tpub.submitData(tVData, "");
		logger.debug("打印表更新一次了");
		return true;

	}

	private boolean saveData(VData mInputData) {

		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		mLOPRTManagerSchema.setStateFlag("1");
		mLOPRTManagerSchema.setDoneDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setExeOperator(mGlobalInput.Operator);
		tLOPRTManagerDB.setSchema((LOPRTManagerSchema) mInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		mResult.add(mLOPRTManagerSchema);
		mResult.add(tLOPRTManagerDB);
		FirstPayF1PBLS tFirstPayF1PBLS = new FirstPayF1PBLS();
		tFirstPayF1PBLS.submitData(mResult, "PRINT");
		if (tFirstPayF1PBLS.mErrors.needDealError()) {
			mErrors.copyAllErrors(tFirstPayF1PBLS.mErrors);
			buildError("saveData", "提交数据库出错！");
			return false;
		}
		return true;

	}

	public String getString(String[] str) {
		String com = "";
		String strBuffer = "";
		String[] compareStr = new String[str.length];
		compareStr = str;
		for (int as = 0; as < str.length; as++) {
			com = compareStr[as];

			if (!com.equalsIgnoreCase("null") && !com.equals("") && com != null) {
				for (int bs = 0; bs < str.length; bs++) {
					if (str[bs].equalsIgnoreCase("null") || str[bs].equals("")
							|| str[bs] == null) {
						break;
					}
					if (!com.equals(str[bs])) {
						strBuffer += com + ",";

					} else {
						strBuffer = com;
					}
				}
			}
		}

		logger.debug(strBuffer);
		return strBuffer;
	}

	public static void main(String[] args) {
		RNGrpPayNoticeMonBL RNGrpPayNoticeMonBL = new RNGrpPayNoticeMonBL();
	}
}
