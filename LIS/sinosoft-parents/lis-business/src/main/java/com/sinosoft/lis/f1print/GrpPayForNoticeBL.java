package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author wl
 * @version 1.0
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import com.f1j.ss.BookModelImpl;
import com.f1j.util.F1Exception;
import com.sinosoft.lis.bq.BqPolBalBL;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LDAddressDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LDAddressSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GrpPayForNoticeBL {
private static Logger logger = Logger.getLogger(GrpPayForNoticeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private SSRS tsdSSRS = new SSRS();
	private SSRS ttSSRS = new SSRS();
	private SSRS tttSSRS = new SSRS();
	private SSRS tljspaySSRS = new SSRS();
	private SSRS lcpolSSRS = new SSRS();
	private SSRS hangupSSRS = new SSRS();
	private String mFlage = "";
	private String tRiskCode = "";
	private int mmonth = 0;
	private int emonth = 0;
	private int myear = 1900;
	private int eyear = 1900;

	private TransferData mTransferData = new TransferData();
	String mAppntNo = "";
	String mAppntName = "";

	public GrpPayForNoticeBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			logger.debug("cOperate:" + cOperate);
			if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}
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

			logger.debug("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
							+ mGlobalInput.Operator);
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", ex.toString());
			return false;
		}

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

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "PayForNoticeBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);

	}

	private boolean getPrintData() {

		String tmMouth = "";
		ExeSQL exeSql = new ExeSQL();
		SSRS testSSRS = new SSRS();
		int tCount = mLOPRTManagerSet.size();
		logger.debug("tCount==" + tCount);
		int page = 0;
		if (tCount % 3 == 0) {
			page = tCount / 3;
		} else {
			page = tCount / 3 + 1;
		}
		logger.debug("page==" + page);
		int pageFlag;
		int x = 0;
		double tSumPrem = 0;
		XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		// 新建一个ListTable实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DBUWAF");
		// 定义列表标题，共4列,采用循环的方式
		String[] Title = new String[7];
		for (int i = 1; i <= 7; i++) {
			Title[i - 1] = "";
		}

		for (int j = 0; j < page; j++) {
			xmlExport.createDocument("GrpPayForNotice.vts", "");
			pageFlag = 1;
			SSRS tSrs = new SSRS();
			SSRS tSrs2 = new SSRS();
			SSRS tSrs3 = new SSRS();
			LOPRTManagerDB tsLOPRTManagerDB = new LOPRTManagerDB();
			tsLOPRTManagerDB.setPrtSeq(mLOPRTManagerSet.get(1).getPrtSeq());
			tsLOPRTManagerDB.getInfo();
			tSrs2 = exeSql
					.execSQL("select agentcode from lcgrpcont where grpcontno = '"
							+ tsLOPRTManagerDB.getOtherNo() + "'");
			tSrs3 = exeSql
					.execSQL("select managecom from laagent where agentcode = '"
							+ tSrs2.GetText(1, 1) + "'");
			tSrs = exeSql
					.execSQL("select a.appntno,(select name from laagent where agentcode = '"
							+ tSrs2.GetText(1, 1)
							+ "'), "
							+ "(select mobile from laagent where agentcode = '"
							+ tSrs2.GetText(1, 1)
							+ "'), "
							+ "(select phone from laagent where agentcode = '"
							+ tSrs2.GetText(1, 1)
							+ "'),"
							+ "(select name from ldcom where comcode = '"
							+ tSrs3.GetText(1, 1)
							+ "'), "
							+ "(select name from ldcom where comcode = '"
							+ tSrs3.GetText(1, 1).substring(0, 4)
							+ "') "
							+ " from lcgrpcont a where a.grpcontno = '"
							+ tsLOPRTManagerDB.getOtherNo() + "'");

			SSRS tLCContSrs = new SSRS();
			SSRS tLCContHangUpSrs = new SSRS();
			SSRS tLCPolSrs = new SSRS();
			SSRS tLCPolSrs2 = new SSRS();
			int HangUpCount = 0;
			int HangUpCount2 = 0;
			String tHangUpSum = "0.00";
			double HangUpSum = 0;
			double ttHangUpSum = 0;
			tLCContSrs = exeSql
					.execSQL("select contno from ljspayperson where grpcontno = '"
							+ tsLOPRTManagerDB.getOtherNo() + "'");
			tLCPolSrs2 = exeSql
					.execSQL("select contno from lcpol where grpcontno = '"
							+ tsLOPRTManagerDB.getOtherNo()
							+ "' and appflag != '4'");
			for (int index1 = 1; index1 <= tLCContSrs.getMaxRow(); index1++) {
				tLCContHangUpSrs = exeSql
						.execSQL("select hanguptype,polno from lcconthangupstate where contno = '"
								+ tLCContSrs.GetText(index1, 1) + "'");
				for (int index2 = 1; index2 <= tLCContHangUpSrs.getMaxRow(); index2++) {
					if (tLCContHangUpSrs.GetText(index2, 1) != null) {
						HangUpCount2++;
						// tLCPolSrs = exeSql.execSQL("select prem from lcpol
						// where polno = '"+tLCContHangUpSrs.GetText(index2,
						// 2)+"'");
						// tHangUpSum = tLCPolSrs.GetText(1,1);
						// HangUpSum = Double.parseDouble(tHangUpSum);
						// HangUpSum = HangUpSum + HangUpSum;
					}
				}
				// tLCPolSrs = exeSql.execSQL(
				// "select SumDuePayMoney from ljspayperson where contno = '" +
				// tLCContSrs.GetText(index1, 1) + "'");
				// tHangUpSum = tLCPolSrs.GetText(1, 1);
				// ttHangUpSum = Double.parseDouble(tHangUpSum);
				// HangUpSum += ttHangUpSum;

				if (HangUpCount2 >= 1) {
					HangUpCount++;
					tLCPolSrs = exeSql
							.execSQL("select SumDuePayMoney from ljspayperson where contno = '"
									+ tLCContSrs.GetText(index1, 1) + "'");
					tHangUpSum = tLCPolSrs.GetText(1, 1);
					ttHangUpSum = Double.parseDouble(tHangUpSum);
					HangUpSum += ttHangUpSum;

				}
			}
			texttag.add("PersonNum2", tLCPolSrs2.getMaxRow()); // 有效被保险人数
			texttag.add("PersonNum", tLCPolSrs2.getMaxRow()); // 参保人数
			texttag.add("FreeNum", HangUpCount); // +
			logger.debug("=========================tSrs======================="
							+ tSrs.GetText(1, 1));
			String tappntinfo = " select t.GrpAddress,t.GrpZipCode, n.name, t.Phone1,t.Phone2 from lcgrpaddress t,lcgrpappnt n"
					+ " where 1 = 1and n.addressno = t.addressno "
					+ " and t.customerno = n.customerno ";
			if (tSrs.getMaxRow() != 0) {
				tappntinfo += " and n.customerno = '" + tSrs.GetText(1, 1)
						+ "' " + " and n.grpcontno = '"
						+ tsLOPRTManagerDB.getOtherNo() + "'";
			}

			testSSRS = exeSql.execSQL(tappntinfo);
			if (testSSRS.getMaxRow() == 0) {
				buildError("getPrintData", "在取得LAAgent的数据时发生错误");
				return false;
			}
			// 法人相关信息
			texttag.add("Address", testSSRS.GetText(1, 1)); // +
			texttag.add("ZipCode", testSSRS.GetText(1, 2));
			texttag.add("AppntName", testSSRS.GetText(1, 3));
			texttag.add("PhoneNumber", testSSRS.GetText(1, 4) + ";"
					+ testSSRS.GetText(1, 5));
			texttag.add("Address2", testSSRS.GetText(1, 1)); // +
			// texttag.add("PersonNum", testSSRS.GetText(1, 2));
			texttag.add("AppntName2", testSSRS.GetText(1, 3));
			texttag.add("PhoneNumber2", testSSRS.GetText(1, 4));

			logger.debug("fuwu=" + tSrs.GetText(1, 2));
			texttag.add("ServerName", tSrs.GetText(1, 2)); // 服务人员姓名
			texttag.add("PhoneNum", tSrs.GetText(1, 3) + " "
					+ tSrs.GetText(1, 4) + " " + testSSRS.GetText(1, 4)); // 电话
			texttag.add("ManageCom", tSrs.GetText(1, 6) + "+"
					+ tSrs.GetText(1, 5)); // 所在机构

			for (int i = j * 3 + 1; i <= j * 3 + 3; i++) {
				boolean tXQExt = false;
				double mCountResult = 0;
				logger.debug("i==" + i);
				/*----------BL---------------------*/
				if (pageFlag == 1) {
					x = 1;
				}
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

				// 取得lcgrpcont表数据
				LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
				LCGrpContDB tLCGrpContDB = new LCGrpContDB();
				tLCGrpContSchema = new LCGrpContSchema();
				tLCGrpContDB.setGrpContNo(tLOPRTManagerSchema.getOtherNo());
				if (!tLCGrpContDB.getInfo()) {
					this.buildError("getPrintData", "查询合同表出错！");
				}
				int tComYear = 0;
				int tComDate = 0;
				tLCGrpContSchema = tLCGrpContDB.getSchema();
				FDate fa = new FDate();

				// 取得loprtmanagerSub表数据
				// 正确提取本期交费的险种个数
				logger.debug("=============提取正确的该保单下的险种信息=================");
				String tRISKSql = "select grppolno,riskcode "
						+ "from lcgrppol " + "where grpcontno = '"
						+ tsLOPRTManagerDB.getOtherNo() + "' "
						+ "group by grppolno,riskcode";
				ExeSQL tRiskExeSQL = new ExeSQL();
				SSRS tRiskSSRS = new SSRS();
				tRiskSSRS = tRiskExeSQL.execSQL(tRISKSql);
				logger.debug("=============" + tRISKSql
						+ "=================");

				if (tRiskSSRS == null) {
					mErrors.copyAllErrors(tRiskSSRS.mErrors);
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
						.execSQL("select a.riskcode , a.sumactupaymoney from ljapayperson a , lcgrppol b where lastpaytodate in ( "
								+ "  select max(lastpaytodate) from ljapayperson a, "
								+ " lcgrppol b where a.grpcontno = b.grpcontno "
								+ " and a.grpcontno = '"
								+ tLCGrpContSchema.getGrpContNo()
								+ " ') "
								+ " and a.grpcontno = b.grpcontno and a.grpcontno = '"
								+ tLCGrpContSchema.getGrpContNo()
								+ "'"
								+ " group by a.riskcode, a.sumactupaymoney ");

				tljaSSRS = exeSql.execSQL("select a.riskcode, "
						+ " a.sumactupaymoney from ljspayperson a, "
						+ " lcgrppol b where a.grpcontno = b.grpcontno "
						+ " and a.grpcontno = '"
						+ tLCGrpContSchema.getGrpContNo() + "'"
						+ " group by a.riskcode, a.sumactupaymoney ");
				if (tljaSSRS.getMaxRow() != 0 && tljsSSRS.getMaxRow() != 0
						&& tljaSSRS.GetText(1, 1) != null
						&& tljsSSRS.GetText(1, 1) != null) {
					for (int a = 0; a < tljsSSRS.getMaxRow(); a++) {
						for (int s = 0; s < tljaSSRS.getMaxRow(); s++) {
							if (tljsSSRS.GetText(a + 1, 1).equals(
									tljaSSRS.GetText(s + 1, 1))) {
								if (!tljsSSRS.GetText(a + 1, 2).equals(
										tljaSSRS.GetText(s + 1, 2))) {
									tXQExt = true;
								}
							}

						}
					}
				}
				if (tRiskSSRS.getMaxRow() == 0) {
					buildError("getPrintData", "在取得loprtmanagersub中的数据时发生错误");
					return false;
				}
				for (int m = 1; m <= tRiskSSRS.getMaxRow(); m++) {
					// tNoticeNo = tRiskSSRS.GetText(m, 1);
					LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
					LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
					tLCGrpPolDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());
					tLCGrpPolDB.setRiskCode(tRiskSSRS.GetText(m, 2));
					tLCGrpPolSet = tLCGrpPolDB.query();
					if (tLCGrpPolSet == null) {
						mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
						buildError("getPrintData", "在取得lcgrppol的数据时发生错误");
						return false;
					}
					// SSRS tsdSSRS = new SSRS();
					if (tRiskSSRS.GetText(m, 2) == "") {
						return false;
					}
					tRiskCode = tRiskSSRS.GetText(m, 2);
					tsdSSRS = exeSql
							.execSQL("select polno from ljspayperson where grpcontno = '"
									+ tLOPRTManagerSchema.getOtherNo()
									+ "' "
									+ "and riskcode = '" + tRiskCode + "'");

					for (int index = 1; index <= tsdSSRS.getMaxRow(); index++) {
						tPrem = Double.parseDouble(tsdSSRS.GetText(index, 1));
					}

					if (tPayToDate == null) {
						SSRS tdateSSRS = new SSRS();
						ExeSQL tExeSQL = new ExeSQL();
						String tSql = "select startpaydate from ljspay where otherno = '"
								+ tLOPRTManagerSchema.getOtherNo()
								+ "' and getnoticeno = '"
								+ tLOPRTManagerSchema.getStandbyFlag2() + "'";
						tdateSSRS = tExeSQL.execSQL(tSql);
						logger.debug(tSql);
						if (tdateSSRS.MaxRow > 0
								&& !tdateSSRS.GetText(1, 1).equals("null")
								&& !tdateSSRS.GetText(1, 1).equals("")) {
							tPayToDate = tdateSSRS.GetText(1, 1);
						} else {
							tPayToDate = tLCGrpPolSet.get(1).getPaytoDate();
						}
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
								}
							} else {
								myear = tComYear;
							}

						}

					}

					BqPolBalBL tBqPolBalBL = new BqPolBalBL();
					for (int q = 1; q <= tLCGrpPolSet.size(); q++) {
						if (tBqPolBalBL.calAutoPayInterest(tLCGrpPolSet.get(q)
								.getGrpPolNo(), tPayToDate)) {
							mCountResult += tBqPolBalBL.getCalResult();

						}

					}

				}
				LAAgentSchema tLAAgentSchema = new LAAgentSchema();
				LAAgentDB tLAAgentDB = new LAAgentDB();
				tLAAgentDB.setAgentCode(tLCGrpContSchema.getAgentCode());
				if (!tLAAgentDB.getInfo()) {
					mErrors.copyAllErrors(tLAAgentDB.mErrors);
					buildError("getPrintData", "在取得LAAgent的数据时发生错误");
					return false;
				}
				tLAAgentSchema = tLAAgentDB.getSchema();

				texttag.add("Year", eyear);
				texttag.add("Month", mmonth);
				texttag.add("Year2", eyear);
				texttag.add("Month2", mmonth);
				texttag.add("GrpContNo", tLOPRTManagerSchema.getOtherNo());
				ExeSQL tlsjpaySql = new ExeSQL();
				tljspaySSRS = tlsjpaySql
						.execSQL("select paydate from ljspay where getnoticeno = '"
								+ tLOPRTManagerSchema.getStandbyFlag2() + "'");
				texttag.add("ChargeData", tljspaySSRS.GetText(1, 1));

				String Result = "";

				if (tRiskSSRS.MaxRow != 0) {
					for (int index = 1; index <= tRiskSSRS.MaxRow; index++) {
						String[] Stra = new String[7];

						String sprem = "select prem from lcgrppol where grppolno = '"
								+ tRiskSSRS.GetText(index, 1) + "'";
						ExeSQL sExeSQL = new ExeSQL();
						SSRS spSSRS = sExeSQL.execSQL(sprem);

						String mystr = "select riskname,riskcode from lmriskapp where subRiskFlag = 'M' and riskcode = '"
								+ tRiskSSRS.GetText(index, 2) + "'";
						ExeSQL texeSql = new ExeSQL();
						ttSSRS = texeSql.execSQL(mystr);
						if (ttSSRS.MaxRow != 0) {
							ExeSQL mExeSql = new ExeSQL();
							SSRS xSSRS = new SSRS();
							xSSRS = mExeSql
									.execSQL("select a.prem,(select paycount from ljspaygrp where grppolno = a.grppolno),"
											+ "(select paydate from ljspaygrp where grppolno = a.grppolno),(select SumDuePayMoney from ljspaygrp where grppolno = a.grppolno) from lcgrppol a where a.grppolno = '"
											+ tRiskSSRS.GetText(index, 1) + "'");

							Stra[5] = spSSRS.GetText(1, 1); // 应交保费
							Stra[0] = tLOPRTManagerSchema.getOtherNo(); // 保单号
							Stra[1] = ttSSRS.GetText(1, 1); // 主险险种
							Stra[2] = xSSRS.GetText(1, 3); // 交费对应日
							Stra[6] = xSSRS.GetText(1, 2); // 交费期数
							Stra[3] = xSSRS.GetText(1, 1); // 主险保费

							double ssprem = Double.parseDouble(xSSRS.GetText(1,
									4))
									- Double.parseDouble(xSSRS.GetText(1, 1));
							Stra[4] = String.valueOf(ssprem);// 附险保费

						} else {
							continue;
						}

						tListTable.add(Stra);

					}

					String tLJSPaySql = "select SumDuePayMoney from ljspay where otherno = '"
							+ tLOPRTManagerSchema.getOtherNo() + "'";
					ExeSQL tExeSQL = new ExeSQL();
					SSRS tSSRSF = new SSRS();
					tSSRSF = tExeSQL.execSQL(tLJSPaySql);
					double ttSumPrem = Double.parseDouble(tSSRSF.GetText(1, 1));
					tSumPrem = 0 + ttSumPrem;

				}

				/*
				 * if (tsdSSRS.getMaxRow() != 0) { for (int index = 1; index <=
				 * tsdSSRS.getMaxRow(); index++) { ExeSQL texeSql = new
				 * ExeSQL(); ttSSRS = texeSql.execSQL( "select
				 * sum(SumDuePayMoney) from ljspayperson where polno = '" +
				 * tsdSSRS.GetText(index, 1) + "' "); // " + // "and riskcode = '" + //
				 * tRiskCode + "'");
				 * 
				 * String tContNo = tsdSSRS.GetText(index, 1); //保单号 String
				 * tMRisk = ""; String tSRisk = "";
				 * 
				 * ExeSQL lcpolSql = new ExeSQL(); lcpolSSRS =
				 * lcpolSql.execSQL("select a.prem, " + " (select riskname from
				 * lmriskapp where riskcode = (select riskcode from " + " lcpol
				 * where polno = (select mainpolno from lcpol where polno = " + " '" +
				 * tContNo + "'))),(select riskname from lmriskapp where
				 * riskcode = (select riskcode from lcpol where polno = '" +
				 * tContNo + "') " + "),(select paycount from ljspayperson where
				 * polno = a.polno) " + ",(select paydate from ljspayperson
				 * where polno = a.polno), " + "(select prem from lcpol where
				 * polno = (select mainpolno from lcpol where polno = '" +
				 * tContNo + "')), " + " a.mainpolno from lcpol a where
				 * a.mainpolno = '" + tContNo + "'"); String tMainPolNo =
				 * lcpolSSRS.GetText(1, 7); // String tMainPolNoName = ""; // if
				 * (tMainPolNo == tContNo) // { // tMainPolNoName =
				 * lcpolSSRS.GetText(1, 4); // } // ExeSQL ljspaypersonSql = new
				 * ExeSQL(); // tttSSRS = ljspaypersonSql.execSQL( // "select
				 * a.prem,(select riskname from lmriskapp where " // + //
				 * "riskcode = (select riskcode from lcpol where polno = '" + //
				 * tContNo + "')), " // + // "(select paycount from ljspayperson
				 * where polno = a.polno) " // + // ",(select paydate from
				 * ljspayperson where polno = a.polno) " // + "from lcpol a
				 * where a.polno = '" + // tContNo + "'"); String[] Stra = new
				 * String[7]; Stra[5] = lcpolSSRS.GetText(1, 1); //应交保费 Stra[0] =
				 * tLOPRTManagerSchema.getOtherNo(); //保单号 Stra[1] =
				 * lcpolSSRS.GetText(1, 2); //主险险种 Stra[2] =
				 * lcpolSSRS.GetText(1, 5); //交费对应日 Stra[6] =
				 * lcpolSSRS.GetText(1, 4); //交费期数 Stra[3] =
				 * lcpolSSRS.GetText(1, 6); //主险保费
				 * logger.debug("mainpolno=" + tMainPolNo);
				 * logger.debug("polno=" + tContNo); if
				 * (!tMainPolNo.equals(null)) { Stra[4] = "0.00"; //附险保费 } else {
				 * Stra[4] = lcpolSSRS.GetText(1, 1); //附险保费 } // ExeSQL
				 * tHangupexeSql = new ExeSQL(); // hangupSSRS =
				 * tHangupexeSql.execSQL( // "select hanguptype,polno,(select
				 * prem from lcpol where " // + // "polno =
				 * lcconthangupstate.polno) from lcconthangupstate where " // + "
				 * polno = '" + tContNo + // "' and insuredno = (select
				 * insuredno from lcpol where polno = '" + // tContNo + "') " // + // "
				 * and contno = (select contno from lcpol where polno = '" + //
				 * tContNo + "')"); // Result = hangupSSRS.GetText(1, 1); //
				 * tHangUpSum = hangupSSRS.GetText(1, 2); // HangUpSum =
				 * Double.parseDouble(tHangUpSum); // if (!Result.equals("0")) // { // //
				 * HangUpSum = HangUpSum + HangUpSum; // } tListTable.add(Stra);
				 *  }
				 * 
				 * String tLJSPaySql = "select SumDuePayMoney from ljspay where
				 * otherno = '" + tLOPRTManagerSchema.getOtherNo() + "'"; ExeSQL
				 * tExeSQL = new ExeSQL(); SSRS tSSRSF = new SSRS(); tSSRSF =
				 * tExeSQL.execSQL(tLJSPaySql); double ttSumPrem =
				 * Double.parseDouble(tSSRSF.GetText(1, 1)); tSumPrem = 0 +
				 * ttSumPrem; }
				 */

				x++;
				pageFlag = 0;
				modifLOPRT(tLOPRTManagerSchema);

				texttag.add("FreeMoney", new DecimalFormat("0.00")
						.format(HangUpSum)); // 挂起保费
				texttag.add("SumMoney1", new DecimalFormat("0.00")
						.format(tSumPrem)); // 本期保费
				double ShouldChargeSum = tSumPrem + (-1) * HangUpSum;
				texttag.add("SumMoney2", new DecimalFormat("0.00")
						.format(ShouldChargeSum)); // 应交保费合计
			}

			if (mmonth == emonth) {
				tmMouth = String.valueOf(mmonth);
			} else {
				tmMouth = mmonth + "至" + emonth;

			}

			texttag.add("tMonth", tmMouth);

			// if (j == page - 1)
			// { //仅是在最后一页打印本月合计
			// texttag.add("SumMoeny1",
			// new DecimalFormat("0.00").format(tSumPrem));
			// }
			// 添加动态列表数组，参数为一个TextTag
			xmlExport.addListTable(tListTable, Title);
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}
		}

		mResult.clear();
		// mResult.addElement(allname);
		mResult.addElement(xmlExport);
		// getCustomerList();

		return true;
	}

	private void getCustomerList() {
		XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例

		ExeSQL exeSql = new ExeSQL();
		SSRS listSSRS = new SSRS();

		ListTable tListTable = new ListTable();
		tListTable.setName("RISK");

		String strSql = "select "
				+ " d.customerseqno,"
				+ "(select '* ' from ljspayperson a,lcprem b where 1=1"
				+ " and a.contno=b.contno"
				+ " and a.dutycode=b.dutycode"
				+ " and a.payplancode=b.payplancode"
				+ " and a.sumduepaymoney<>b.prem"
				+ " and a.grpcontno=d.grpcontno"
				+ " and a.grpcontno=d.grpcontno"
				+ " and a.contno=d.contno"
				+ " and b.contno=d.contno"
				+ " )||"
				+ " name name,"
				+ " case d.sex"
				+ " when '1' then '男'"
				+ " when '0' then '女'"
				+ " else '不详'"
				+ " end case,"
				+ " d.birthday,"
				+ "( "
				+ " select sum(t.sumduepaymoney) from ljspayperson t where polno in"
				+ " (select polno from lcpol where grpcontno=d.grpcontno and contno=d.contno and appflag='1' and mainpolno =polno)"
				+ " ) mainmoney,"
				+ "	nvl( "
				+ "(select sum(t.sumduepaymoney) from ljspayperson t where polno in"
				+ " (select polno from lcpol where grpcontno=d.grpcontno and contno=d.contno and appflag='1' and mainpolno <>polno)),'0'"
				+ " ) fumoney,"
				+ " (select sum(sumduepaymoney) from ljspayperson where contno=d.contno) summoney,"
				+ "("
				+ " select paycount from ljspayperson where polno in"
				+ " (select polno from lcpol where grpcontno=d.grpcontno and contno=d.contno and appflag='1' and mainpolno =polno)"
				+ " and rownum=1"
				+ ") paycount,"
				+ " case (select count(1) from lcconthangupstate where contno=d.contno and rnflag='1')"
				+ " when 0 then '否'"
				+ " when 1 then '是'"
				+ " else '不详'"
				+ " end case,"
				+ "("
				+ " select codealias from ldcode where codetype='conthanguptype' and code=(select lcconthangupstate.hanguptype from lcconthangupstate where contno=d.contno and rnflag='1' and rownum=1)"
				+ ") hangupreason"
				+ " from lcinsured d"
				+ " where grpcontno in (select otherno from ljspay where othernotype='1' and appntno='"
				+ mAppntNo + "' )" + " and managecom like '"
				+ mGlobalInput.ComCode + "%'" + " order by d.customerseqno";
		// 查询获取信息
		listSSRS = exeSql.execSQL(strSql);
		// 设置模板title
		String[] title = new String[listSSRS.MaxCol];
		// 设置分页，每页150条条纪录；
		int count = 150;
		int page = 0;

		if (listSSRS.MaxRow % count == 0) {
			page = listSSRS.MaxRow / count;
		} else {
			page = listSSRS.MaxRow / count + 1;
		}

		for (int i = 0; i < page; i++) {
			xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			xmlExport.createDocument("GrpPayForNoticeList.vts", "");
			tListTable = new ListTable();
			tListTable.setName("RISK");
			for (int line = 0; line < count; line++) {
				if (line < listSSRS.MaxRow) {
					String[] inf = new String[listSSRS.MaxCol];
					for (int j = 0; j < listSSRS.MaxCol; j++) {
						inf[j] = listSSRS.GetText(line + 1, j + 1);
					}
					tListTable.add(inf);
					// xmlExport.addListTable(tListTable, title);
				} else {
					break;
				}
			}
			xmlExport.addListTable(tListTable, title);
			texttag.add("curPage", i + 1);
			texttag.add("Page", page);
			xmlExport.addTextTag(texttag);
			mResult.add(xmlExport);
		}

		return;
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
				|| tPlaceCode.equals("")) {
			return "";
		}
		String tPlaceName = "";
		LDAddressDB tLDAddressDB = new LDAddressDB();
		tLDAddressDB.setPlaceCode(tPlaceCode);
		tLDAddressDB.setPlaceType(tPlaceType);
		LDAddressSchema tLDAddressSchema = new LDAddressSchema();
		tLDAddressSchema = tLDAddressDB.getSchema();
		tPlaceName = tLDAddressSchema.getPlaceName();
		return tPlaceName;
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
		// GrpPayForNoticeBL tGrpPayForNoticeBL = new GrpPayForNoticeBL();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";

		TransferData tTransferData = new TransferData();

		String tAppntNo = "0000002630";
		String tAppntName = "旅社";
		tTransferData.setNameAndValue("AppntNo", tAppntNo);
		tTransferData.setNameAndValue("AppntName", tAppntName);
		LOPRTManagerSchema tLOPRTManagerSchema;
		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		String[] tPrtSeq = new String[2];
		tPrtSeq[0] = "8100000000288";
		tPrtSeq[1] = "8102100001594";

		for (int i = 0; i < tPrtSeq.length; i++) {
			tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema.setPrtSeq(tPrtSeq[i]);
			logger.debug(tPrtSeq[i]);
			tLOPRTManagerSet.add(tLOPRTManagerSchema);
		}

		VData tVData = new VData();
		VData mResult = new VData();
		// tVData.addElement("strOperation");
		tVData.addElement(tG);
		tVData.addElement(tLOPRTManagerSet);

		if (tTransferData == null) {
			logger.debug("=====================================");
		}

		tVData.addElement(tTransferData);

		GrpPayForNoticeUI tGrpPayForNoticeUI = new GrpPayForNoticeUI();

		if (!tGrpPayForNoticeUI.submitData(tVData, "CONFIRM")) {
			VData RResult = new VData();
			RResult = tGrpPayForNoticeUI.getResult();
			String[] strVFPathName = new String[RResult.size()]; // 临时文件个数。
			for (int k = 0; k < RResult.size(); k++) {
				XmlExport txmlExport = new XmlExport();
				txmlExport = (XmlExport) RResult.getObjectByObjectName(
						"XmlExport", k);
				CombineVts tcombineVts = null;
				String strTemplatePath = "D:/lis/ui/f1print/NCLtemplate/";
				tcombineVts = new CombineVts(txmlExport.getInputStream(),
						strTemplatePath);
				ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
				tcombineVts.output(dataStream);
				strVFPathName[k] = "D:/lis/ui/vtsfile/001_" + k + "_"
						+ "GrpPayForNotice.vts";
				AccessVtsFile.saveToFile(dataStream, strVFPathName[k]);
			}
			VtsFileCombine vtsfilecombine = new VtsFileCombine();
			try {
				BookModelImpl tb = vtsfilecombine.dataCombine(strVFPathName);
				vtsfilecombine.write(tb,
						"D:/lis/ui/vtsfile/001_newGrpPayForNotice.vts");
			} catch (IOException ex) {
			} catch (F1Exception ex) {
			} catch (Exception ex) {
			}

		}
	}
}
