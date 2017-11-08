package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LYReturnFromBankBDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LYReturnFromBankBSchema;
import com.sinosoft.lis.vschema.LYReturnFromBankBSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

public class XQWoodcutterNoticeBL {
private static Logger logger = Logger.getLogger(XQWoodcutterNoticeBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	private String mSelString = "";

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();
	private String mOthernoType = "";

	private TransferData mTransferData = new TransferData();
	private LYReturnFromBankBSchema mLYReturnFromBankBSchema = new LYReturnFromBankBSchema();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public XQWoodcutterNoticeBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOthernoType = cOperate;

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!getLYReturnFromBankBInfo()) {
			return false;
		}
		mResult.clear();

		// 准备所有要打印的数据
		if (mOthernoType.equals("1")) {
			// 团单交费通知书
			// 打印接口，放入需要打印的方法完成打印
		} else {
			if (!getPrintData()) {
				return false;
			}
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
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("8102101505769");
		tLOPRTManagerSchema.setStandbyFlag4("3100000905060");
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "8621";
		VData tVData = new VData();
		VData mResult = new VData();

		tVData.addElement(tG);
		tVData.addElement(tLOPRTManagerSchema);
		// tVData.addElement(tTransferData);
		XQWoodcutterNoticeBL tXQWoodcutterNoticeBL = new XQWoodcutterNoticeBL();

		if (!tXQWoodcutterNoticeBL.submitData(tVData, "2")) {
			logger.debug("失败");
		} else {
			logger.debug("成功");
		}
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
		mLOPRTManagerSchema = (LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0);
		if (mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	private boolean getLYReturnFromBankBInfo() {

		/** @todo 优化，冲正，回退，暂收费退费的银行划款除外 */
		String tSql = "select * from LYReturnFromBankB a where a.DealType='S'"
				+ " and a.PayCode='"
				+ "?PayCode?"
				+
				// "' and a.NoType = '" + mOthernoType +
				"' and a.BankSuccFlag='0000'"
				+ " and not exists  (select 1 from ljapayperson d where d.contno =a.polno and d.getnoticeno =a.paycode and d.sumactupaymoney < 0)"
				+ " and not exists (select tempfeeno from ljagettempfee where tempfeeno=a.paycode)";
		logger.debug(tSql);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("PayCode", mLOPRTManagerSchema.getStandbyFlag4());
		LYReturnFromBankBDB tLYReturnFromBankBDB = new LYReturnFromBankBDB();

		LYReturnFromBankBSet tLYReturnFromBankBSet = tLYReturnFromBankBDB
				.executeQuery(sqlbv1);

		if (tLYReturnFromBankBSet.size() <= 0) {
			buildError("getLYReturnFromBankBInfo", "LYReturnFromBankB获得数据有误！");
			return false;
		} else {
			mLYReturnFromBankBSchema = tLYReturnFromBankBSet.get(1);
		}

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
		double tMainRisk = 0;
		double tExtendRisk = 0;
		double tCountRisk = 0;
		String tFee = "";
		boolean tFlag = false;
		ExeSQL exeSql = new ExeSQL();
		SSRS tzuSSRS = new SSRS();
		SSRS nSSRS = new SSRS();
		SSRS ydSSRS = new SSRS();
		String remark = "";
		// 对打印表的值进行控制，为的是在打印的时候再下面能够有效地显示备注信息

		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSchema.getPrtSeq());
		if (!tLOPRTManagerDB.getInfo()) {
			buildError("dealData", "打印管理表");
			return false;
		}

		LYReturnFromBankBDB tLYReturnFromBankBDB = new LYReturnFromBankBDB();
		LYReturnFromBankBSet tLYReturnFromBankBSet = new LYReturnFromBankBSet();
		tLYReturnFromBankBDB.setSchema(mLYReturnFromBankBSchema);
		tLYReturnFromBankBSet = tLYReturnFromBankBDB.query();
		// 有了客户的全部信息
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(" select concat((select placename from ldaddress where placetype = '03' and placecode = lc.county),lc.postaladdress) ,lc.zipcode,"
				+ "   la.appntname , la.appntsex, "
				+ "   la.IDType,la.IDNo"
				+ "   from lcaddress lc ,lcappnt la "
				+ "  where lc.addressno = la.addressno "
				+ "    and lc.customerno = la.appntno "
				+ "    and la.contno ='"
				+ "?contno?" + "'");
		sqlbv2.put("contno", tLYReturnFromBankBSet.get(1).getPolNo());
		tzuSSRS = exeSql.execSQL(sqlbv2);
		String tPostalAddress = tzuSSRS.GetText(1, 1);
		String tZipCode = tzuSSRS.GetText(1, 2);
		String tAppntName = tzuSSRS.GetText(1, 3);
		String tAppntSex = tzuSSRS.GetText(1, 4);
		String tIDType = tzuSSRS.GetText(1, 5);
		String tIDNo = tzuSSRS.GetText(1, 6);
		String tIDSex = "";
		if (!tIDNo.equals("") && !tIDNo.equals("null") && !tIDNo.equals(null)) {
			tIDSex = tIDNo.substring(tIDNo.length() - 1, tIDNo.length());
		}
		String txsAppntName = "";
		if (tAppntSex.equals("0") || tAppntSex.equals("1")) {
			if (tAppntSex.equals("0")) {
				txsAppntName = tAppntName + "先生";
			}
			if (tAppntSex.equals("1")) {
				txsAppntName = tAppntName + "女士";
			}
		} else {
			if (tIDType.equals("0")
					&& (tIDSex.equals("1") || tIDSex.equals("3")
							|| tIDSex.equals("5") || tIDSex.equals("7") || tIDSex
							.equals("9"))) {
				txsAppntName = tAppntName + "先生";
			}
			if (tIDType.equals("0")
					&& (tIDSex.equals("2") || tIDSex.equals("4")
							|| tIDSex.equals("6") || tIDSex.equals("8") || tIDSex
							.equals("0"))) {
				txsAppntName = tAppntName + "女士";
			} else {
				txsAppntName = tAppntName + "客户";
			}
		}
		logger.debug("--------客户称呼--------" + txsAppntName);
		// 有了代理员的信息 String tAgentManage
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql("select agentcode  from lccont where contno ='"
				+ "?contno?" + "'");
		sqlbv3.put("contno", tLYReturnFromBankBSet.get(1).getPolNo());
		nSSRS = exeSql.execSQL(sqlbv3);

		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(nSSRS.GetText(1, 1));
		if (!tLAAgentDB.getInfo()) {
			buildError("dealData", "查询代理人信息失败!");
			return false;
		} else {
			String ManageName = tLAAgentDB.getManageCom();
			String tAgentCode = tLAAgentDB.getAgentCode();
			String tAgentName = tLAAgentDB.getName();
			String tAgentPhone = tLAAgentDB.getPhone();
			// 得到险种名称

			// tzuSSRS = exeSql.execSQL(
			// "select (select name from labranchgroup where managecom ='" +
			// ManageName + "' and branchattr = (select Substr(branchattr,0,4) from
			// labranchgroup where agentgroup = (select branchcode from laagent where
			// agentcode ='" +
			// tAgentCode + "' ))), "
			// + " (select name from labranchgroup where managecom ='" +
			// ManageName + "' and branchattr = (select Substr(branchattr,0,7) from
			// labranchgroup where agentgroup = (select branchcode from laagent where
			// agentcode ='" +
			// tAgentCode + "' ))), "
			// + " ((select name from labranchgroup where managecom ='" +
			// ManageName +
			// "' and agentgroup = (select branchcode from laagent where agentcode ='" +
			// tAgentCode + "' ))) from dual ");
			// String tAgentManage = tzuSSRS.GetText(1, 1) + tzuSSRS.GetText(1, 2) +
			// tzuSSRS.GetText(1, 3);
			tzuSSRS = exeSql.execSQL("select getglobalbranch('" + tAgentCode
					+ "','" + tLYReturnFromBankBSet.get(1).getPolNo()
					+ "') from dual");

			String tAgentManage = tzuSSRS.GetText(1, 1);
			/** @todo 查询险种名称 */
			String triskname_sql = " select "
					+ "(select riskshortname from lmrisk where riskcode=c.riskcode) riskname "
					+ "   from lcpol c " + "  where c.contno = '"
					+ "?contno?"
					+ "' and c.polno = c.mainpolno";
			logger.debug("获得该合同下的主险的sql语句" + triskname_sql);
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(triskname_sql);
			sqlbv5.put("contno", tLYReturnFromBankBSet.get(1).getPolNo());
			tzuSSRS = exeSql.execSQL(sqlbv5);
			String tRiskName = tzuSSRS.GetText(1, 1);

			/*
			 * 用来判断现在保单所在财务状态。
			 * 
			 */
			ExeSQL tCWExeSQL = new ExeSQL();
			SSRS tCWSSRS = new SSRS();
			String tCWSql = "select * from ljspay where getnoticeno = '"
					+ "?getnoticeno?" + "'";
			// 得到lcpol中的主险的费用 在做的时候设置一个数据为的是算加法
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tCWSql);
			sqlbv6.put("getnoticeno", tLYReturnFromBankBSet.get(1).getPayCode());
			tCWSSRS = tCWExeSQL.execSQL(sqlbv6);
			SSRS tCSSSRS = new SSRS();
			String tCSSql = "select * from ljapay where getnoticeno = '"
					+ "?getnoticeno?" + "'";
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tCSSql);
			sqlbv7.put("getnoticeno", tLYReturnFromBankBSet.get(1).getPayCode());
			tCSSSRS = tCWExeSQL.execSQL(sqlbv7);
			// 判断是否未核销，相应数据在应收表中
			if (tCWSSRS != null && !tCWSSRS.equals("null")
					&& tCWSSRS.MaxRow != 0) {
				logger.debug("尚未核销，查询应收相应表中的数据");
				SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
				sqlbv8.sql(" select sum(a.SumActuPayMoney) "
						+ "   from ljspayperson a, ljspay b, lcpol d "
						+ "  where b.otherno = a.contno "
						+ "    and a.GetNoticeNo = b.GetNoticeNo "
						+
						// " and b.OtherNoType in ('2','3') " +
						"    and a.contno = '"
						+ "?contno?"
						+ "' "
						+ "    and a.polno = d.polno and a.contno = d.contno and d.polno = d.mainpolno ");
				sqlbv8.put("contno", tLYReturnFromBankBSet.get(1).getPolNo());
				tzuSSRS = exeSql.execSQL(sqlbv8); // lihuan加

				if (tzuSSRS.GetText(1, 1) != null
						&& !tzuSSRS.GetText(1, 1).equals("")
						&& !tzuSSRS.GetText(1, 1).equals("null")) {
					tMainRisk = Double.parseDouble(tzuSSRS.GetText(1, 1));

					// 得到下回缴费对应日
					SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
					sqlbv9.sql("select max(curpaytodate) from ljspayperson where getnoticeno = '"
							+ "?getnoticeno?"
							+ "'");
					sqlbv9.put("getnoticeno", tLYReturnFromBankBSet.get(1).getPayCode());
					ydSSRS = exeSql.execSQL(sqlbv9);
					tFee = ydSSRS.GetText(1, 1);
				}
				tFlag = true;
			}
			// 判断是否已核销，相应数据在实收表中
			else if (tCSSSRS != null && !tCSSSRS.equals("null")
					&& tCSSSRS.MaxRow != 0) {
				logger.debug("已核销，查询实收表中相应数据。");
				SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
				sqlbv10.sql(" select sum(a.SumActuPayMoney) "
						+ "   from ljapayperson a, ljapay b, lcpol d "
						+ "  where b.otherno = a.contno "
						+ "    and a.GetNoticeNo = b.GetNoticeNo "
						+
						// " and b.OtherNoType in ('2','3') " +
						"    and a.contno = '"
						+ "?contno?" + "' "
						+ "    and a.polno = d.polno "
						+ "    and a.contno = d.contno "
						+ "    and d.polno = d.mainpolno "
						+ "    and paytype = 'ZC' and b.getnoticeno = '"
						+ "?getnoticeno?" + "'");
				sqlbv10.put("contno", tLYReturnFromBankBSet.get(1).getPolNo());
				sqlbv10.put("getnoticeno", tLYReturnFromBankBSet.get(1).getPayCode());
				tzuSSRS = exeSql.execSQL(sqlbv10); // lihuan加
				if (tzuSSRS.MaxRow != 0) {
					if (tzuSSRS.GetText(1, 1) != null
							&& !tzuSSRS.GetText(1, 1).equals("")
							&& !tzuSSRS.GetText(1, 1).equals("null")) {
						tMainRisk = Double.parseDouble(tzuSSRS.GetText(1, 1));
						SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
						sqlbv11.sql("select max(curpaytodate) from ljapayperson where getnoticeno = '"
								+ "?getnoticeno?" + "'");
						sqlbv11.put("getnoticeno", tLYReturnFromBankBSet.get(1).getPayCode());
						ydSSRS = exeSql.execSQL(sqlbv11);
						tFee = ydSSRS.GetText(1, 1);
					}
				}
			} else {
				// 抛出异常
				logger.debug("未查询到相关数据，请确认是否进行了回退等相关操作。");
				buildError("getPrintData", "未查询到相关数据，请确认是否进行了回退等相关操作。");
				return false;
			}
			// 得到lcpol中附加险的费用
			if (tFlag) {
				SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
				sqlbv12.sql(" select a.riskcode ,a.SumActuPayMoney "
						+ "   from ljspayperson a, ljspay b, lcpol d "
						+ "  where b.otherno = a.contno  "
						+ "    and a.GetNoticeNo = b.GetNoticeNo "
						+ "    and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno "
						+
						// " and b.OtherNoType in ('2','3') " +
						"    and a.ContNo = '"
						+ "?ContNo?"
						+ "' " + "and a.GetNoticeNo = '"
						+ "?GetNoticeNo?"
						+ "'");
				sqlbv12.put("ContNo", tLYReturnFromBankBSet.get(1).getPolNo());
				sqlbv12.put("GetNoticeNo", tLYReturnFromBankBSet.get(1).getPayCode());
				tzuSSRS = exeSql.execSQL(sqlbv12); // lihuan加
				if (tzuSSRS.MaxRow != 0) {
					if (tzuSSRS.GetText(1, 1) != null
							&& !tzuSSRS.GetText(1, 1).equals("null")) {
						for (int i = 1; i <= tzuSSRS.getMaxRow(); i++) {
							SSRS tExName = new SSRS();
							SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
							sqlbv13.sql("select riskshortname from lmrisk where  riskcode ='"
									+ "?riskcode?" + "'");
							sqlbv13.put("riskcode", tzuSSRS.GetText(i, 1));
							tExName = exeSql.execSQL(sqlbv13);
							tExtendRisk = tExtendRisk
									+ Double.parseDouble(tzuSSRS.GetText(i, 2));
							remark = remark
									+ tExName.GetText(1, 1)
									+ "￥"
									+ new DecimalFormat("0.00")
											.format(Double.parseDouble(tzuSSRS
													.GetText(i, 2))) + "   ";
						}
					}
				}
			} else {
				SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
				sqlbv14.sql(" select a.riskcode ,a.SumActuPayMoney "
						+ "   from ljapayperson a, ljapay b, lcpol d "
						+ "  where b.otherno = a.contno  "
						+ "    and a.GetNoticeNo = b.GetNoticeNo "
						+ "    and a.polno = d.polno and a.contno = d.contno and d.polno <> d.mainpolno "
						+
						// " and b.OtherNoType in ('2','3') " +
						"    and paytype = 'ZC'"
						+ "    and a.contno = '"
						+ "?contno?"
						+ "' " + "and a.getnoticeno = '"
						+ "?getnoticeno?"
						+ "'");
				sqlbv14.put("contno", tLYReturnFromBankBSet.get(1).getPolNo());
				sqlbv14.put("getnoticeno", tLYReturnFromBankBSet.get(1).getPayCode());
				tzuSSRS = exeSql.execSQL(sqlbv14); // lihuan加
				if (tzuSSRS.MaxRow != 0) {
					if (tzuSSRS.GetText(1, 1) != null
							&& !tzuSSRS.GetText(1, 1).equals("null")) {
						for (int i = 1; i <= tzuSSRS.getMaxRow(); i++) {
							SSRS tExName = new SSRS();
							SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
							sqlbv15.sql("select riskshortname from lmrisk where  riskcode ='"
									+ "?riskcode?" + "'");
							sqlbv15.put("riskcode", tzuSSRS.GetText(i, 1));
							tExName = exeSql.execSQL(sqlbv15);
							tExtendRisk = tExtendRisk
									+ Double.parseDouble(tzuSSRS.GetText(i, 2));
							remark = remark
									+ tExName.GetText(1, 1)
									+ "￥"
									+ new DecimalFormat("0.00")
											.format(Double.parseDouble(tzuSSRS
													.GetText(i, 2))) + "   ";
						}
					}
				}
			}
			// 得到打印的缴费对应日
			tCountRisk = tExtendRisk + tMainRisk;

			String countriskmoney = PubFun.getChnMoney(tCountRisk);
			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			xmlexport.createDocument("ExtendTransfer.vts", "PRINT"); // 最好紧接着就初始化xml文档

			if (tLOPRTManagerDB.getSchema().getStateFlag().equals("1")) {
				String exInfor = "本保单本次交费以此凭证为准，原凭证同时作废，特此说明。";
				String exInforDate = "补发时间: " + PubFun.getCurrentDate();
				texttag.add("exInfor", exInfor);
				texttag.add("exInforDate", exInforDate);
			}

			texttag.add("AppntName", txsAppntName);
			texttag.add("Address", tPostalAddress);
			texttag.add("ZipCode", tZipCode);
			texttag.add("LCCont.AppntName", tAppntName);
			texttag.add("LAAgent.AgentCode", tAgentCode);
			texttag.add("LAAgent.Name", tAgentName);
			texttag.add("ManageComName", tAgentManage);
			texttag.add("ReceiveDate", String.valueOf(tLYReturnFromBankBSet
					.get(1).getSendDate()));
			texttag.add("RiskName", tRiskName);
			texttag.add("LCCont.ContNo", tLYReturnFromBankBSet.get(1)
					.getPolNo());
			texttag.add("RenewCount", tFee);
			texttag
					.add("MainPrem", new DecimalFormat("0.00")
							.format(tMainRisk));
			texttag.add("AppendPrem", new DecimalFormat("0.00")
					.format(tExtendRisk));
			texttag.add("SumPrem", countriskmoney);
			texttag.add("ReMark", remark);
			texttag.add("BankAccount", tLYReturnFromBankBSet.get(1).getAccNo());
			texttag.add("ESumPrem", new DecimalFormat("0.00")
					.format(tCountRisk));
			texttag.add("LAAgent.Phone", tAgentPhone);
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			// xmlexport.outputDocumentToFile("D:\\", "atest"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlexport);

			tLOPRTManagerDB.setStateFlag("1");
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			MMap map = new MMap();
			map.put(tLOPRTManagerSchema, "UPDATE");
			VData tVData = new VData();
			tVData.add(map);

			PubSubmit p = new PubSubmit();
			if (!p.submitData(tVData, "")) {
				buildError("dealData", "打印表处理失败!");
				return false;
			}

		}
		return true;
	}
}
