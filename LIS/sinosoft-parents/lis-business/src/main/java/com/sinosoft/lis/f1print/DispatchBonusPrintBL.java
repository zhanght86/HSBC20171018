package com.sinosoft.lis.f1print;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 分红业绩报告书打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：liurx, XinYQ,pst
 * @version：1.0, 1.1
 * @CreateDate：2005-08-05, 2006-08-23,2009-02-14
 */
public class DispatchBonusPrintBL implements PrintService {
	private static Logger logger = Logger.getLogger(DispatchBonusPrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private LCContSchema mLCContSchema = new LCContSchema();

	// 基本的标签信息

	/** 合同号 */
	private String mContNo;

	/** 邮政编码 */
	private String mPost;

	/** 地址 */
	private String mAddress;

	/** 投保人姓名 */
	private String mName;

	/** 被保人姓名 */
	private String mInsureName;

	/** 生效日 */
	private String mCValiDate;

	/** 本期末保险金额 */
	private String mAmnt;

	/** 当天 */
	private String mDay;

	/** 本次结算利率类型 */
	private String mRateIntv;

	/** 月结算日 */
	private String mBalaDate;

	// 本期红利领取方式 本期红利金额 红利延迟发放利息 本期红利本息合计 累积红利帐户余额（含本期） 本期红利抵交保险费金额
	// 本期红利购买增额交清的保额 红利分配后累计保额（含本期）

	// 为了红利通知书准备数据，而字段又不够，采取拼串的方式记录即 PolNo+"-"+Data,将多张保单的数据存放在保单打印管理表的附属字段
	/** 本期红利金额 */
	private String tPolBonusMoney = "";

	/** 红利延迟发放利息 */
	private String tPolBonusYCLX = "";

	/** 本期红利本息合计 */
	private String tPolSumBonus = "";

	/** 累积红利帐户余额（含本期） */
	private String tPolSumAccBala = "";

	/** 本期红利抵交保险费金额 */
	private String tPolPayPrem = "";

	/** 本期红利购买增额交清的保额 */
	private String tPolGetAmnt = "";

	/** 红利分配后累计保额（含本期 */
	private String tPolSumGetAmnt = "";

	/** 声明TextTag的实例 */
	TextTag texttag = new TextTag();

	/** 声明ListTable的实例 */
	private ListTable tBonusListTable = new ListTable();

	private ExeSQL tExeSQL = new ExeSQL();

	public DispatchBonusPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!mOperate.equals("PRINT")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DispatchBonusPrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		/** 获得标签数据 */
		if (!getBaseData()) {
			return false;
		}
		/** 获取表格数据 */
		if (!getLisTableData()) {
			return false;
		}

		// 准备需要打印的数据
		if (!preparePrintData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null || mLOPRTManagerSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			mErrors.addOneError(new CError("没有得到足够的信息:流水号不能为空！"));
			return false;
		}
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		String mNoType = mLOPRTManagerSchema.getOtherNoType();

		if (mNoType == null || mNoType.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DispatchBonusPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);

			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_CONT)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DispatchBonusPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tNoticeType = mLOPRTManagerSchema.getCode();
		if (tNoticeType == null || tNoticeType.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "DispatchBonusPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tNoticeType.trim().equals(PrintManagerBL.CODE_BONUSPAY)) {
			CError tError = new CError();
			tError.moduleName = "DispatchBonusPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "单据类型错误：分红报告书！";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = mLOPRTManagerSchema.getOtherNo();

		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DispatchBonusPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保单险种号失败！";
			this.mErrors.addOneError(tError);

			return false;
		}
		/** 本期红利金额 */
		tPolBonusMoney = mLOPRTManagerSchema.getStandbyFlag1();

		/** 红利延迟发放利息 */
		tPolBonusYCLX = mLOPRTManagerSchema.getStandbyFlag2();

		/** 本期红利本息合计 */
		tPolSumBonus = mLOPRTManagerSchema.getStandbyFlag3();

		/** 累积红利帐户余额（含本期） */
		tPolSumAccBala = mLOPRTManagerSchema.getStandbyFlag4();

		/** 本期红利抵交保险费金额 */
		tPolPayPrem = mLOPRTManagerSchema.getStandbyFlag5();

		/** 本期红利购买增额交清的保额 */
		tPolGetAmnt = mLOPRTManagerSchema.getStandbyFlag6();
		if (tPolGetAmnt == null) {
			tPolGetAmnt = "";
		}

		/** 红利分配后累计保额（含本期） */
		tPolSumGetAmnt = mLOPRTManagerSchema.getStandbyFlag7();
		if (tPolSumGetAmnt == null) {
			tPolSumGetAmnt = "";
		}

		return true;
	}

	/** 获得标签数据 */
	private boolean getBaseData() {

		LCContDB tLCContDB = new LCContDB();

		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "DispatchBonusPrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "查询保单信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();

		// 查询投保人信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntDB.setContNo(mContNo);
		if (!tLCAppntDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "DispatchBonusPrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "查询投保人信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCAppntSchema = tLCAppntDB.getSchema();
		mName = tLCAppntSchema.getAppntName();
		// logger.debug("投保人姓名："+mName);
		if (mName == null || mName.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "DispatchBonusPrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "投保人名字为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 查询投保人地址及保单信息
		LCAddressDB tLCAddressDB = new LCAddressDB();
		LCAddressSchema tLCAddressSchema = new LCAddressSchema();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String tSQLAD = "select * from lcaddress l "
				+ " where l.customerno =(select c.appntno from lcappnt c where c.contno ='"
				+ "?mContNo?" + "') " + " and l.addressno ='"
				+ "?addressno?" + "'";
		sqlbv1.sql(tSQLAD);
		sqlbv1.put("mContNo", mContNo);
		sqlbv1.put("addressno", tLCAppntSchema.getAddressNo());
		tLCAddressSchema = tLCAddressDB.executeQuery(sqlbv1).get(1);
		if (tLCAddressSchema == null) {
			CError tError = new CError();
			tError.moduleName = "DispatchBonusPrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "投保人地址信息为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mPost = tLCAddressSchema.getZipCode();

		mAddress = tLCAddressSchema.getPostalAddress();
		if (mAddress == null || mAddress.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "DispatchBonusPrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "投保人地址为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String tLastBosusSQL = "0.00";
		if (mLOPRTManagerSchema.getRemark() != null
				&& (mLOPRTManagerSchema.getRemark().length() == 4)) {
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			tLastBosusSQL = "select bonusmoney from lobonuspol where contno='"
					+ "?contno?" + "' and fiscalyear='"
					+ "?fiscalyear?"
					+ "'";
			sqlbv2.sql(tLastBosusSQL);
			sqlbv2.put("contno", mLCContSchema.getContNo());
			sqlbv2.put("fiscalyear", (Integer.parseInt(mLOPRTManagerSchema.getRemark()) - 1));
			if (!"".equals(tExeSQL.getOneValue(sqlbv2))) {
				tLastBosusSQL = tExeSQL.getOneValue(sqlbv2);
			} else {
				tLastBosusSQL = "0.00";
			}
		}
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		String tLastSumPremSQL = "select sum(prem) from lcpol where contno='"
				+ "?contno?" + "' and appflag=1";
		sqlbv3.sql(tLastSumPremSQL);
		sqlbv3.put("contno", mLCContSchema.getContNo());
		if (!"".equals(tExeSQL.getOneValue(sqlbv3))) {
			tLastSumPremSQL = tExeSQL.getOneValue(sqlbv3);
		} else {
			tLastSumPremSQL = "0.00";
		}

		texttag.add("Post", mPost);
		texttag.add("Address", mAddress);
		texttag.add("AppntName", mName);
		texttag.add("ContNo", mContNo);
		texttag.add("Branch", BqNameFun.getComM(mContNo.substring(0, 4)));
		texttag.add("CvaliDate", mLCContSchema.getCValiDate());
		texttag.add("FinaYear", mLOPRTManagerSchema.getRemark());
		texttag.add("PrintDate", PubFun.getCurrentDate());
		texttag.add("LastSumPrem", tLastSumPremSQL);
		texttag.add("LastBonusMoney", tLastBosusSQL);
		// 添加代理人信息
		BqNameFun.AddBqNoticeAgentInfo(mLCContSchema, texttag);

		return true;
	}

	/** 获取表格数据 */

	private boolean getLisTableData() {

		tBonusListTable.setName("HL");

		String tSGetDate = "", tAGetDate = "", tBonusGetMode = "";

		SSRS BQSSRS = new SSRS();

		// add by jiaqiangli 2009-10-19 122202 122201
		// 附加险lobonuspol.bonusgetmode='null'
		// 附加险lobonuspol.bonusgetmode='null' 参见红利计算 为了更安全起见 is null的也加入判断
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		String tSQL = "select a.polno, (case when a.bonusgetmode='null' or a.bonusgetmode is null then '1' else a.bonusgetmode end) ,a.bonusmoney, a.bonusinterest, a.sgetdate,a.fiscalyear,a.agetdate  "
				+ " from lobonuspol a where a.fiscalyear="
				+ "?fiscalyear?"
				+ " and a.contno='"
				+ "?mContNo?"
				+ "'" + " and a.bonusflag='1' ";
		sqlbv4.sql(tSQL);
		sqlbv4.put("fiscalyear", mLOPRTManagerSchema.getRemark());
		sqlbv4.put("mContNo", mContNo);
		logger.debug("执行SQL" + tSQL);
		BQSSRS = tExeSQL.execSQL(sqlbv4);
		if (BQSSRS != null && BQSSRS.MaxRow > 0) {
			tBonusGetMode = BQSSRS.GetText(1, 2);
			tSGetDate = BQSSRS.GetText(1, 5);
			tAGetDate = BQSSRS.GetText(1, 7);

			// 将数据放入ListTable
			String strIO[] = null;
			// 险种 本期红利领取方式 本期红利金额 红利延迟发放利息 本期红利本息合计 累积红利帐户余额（含本期） 本期红利抵交保险费金额
			// 本期红利购买增额交清的保额 红利分配后累计保额（含本期）

			Pattern p = Pattern.compile("[|]"); // 选出|
												// //用正则切开，也可以直接用字符的split,参数是一样
			String PolBonusMoney[] = null;
			if (!"".equals(tPolBonusMoney) && tPolBonusMoney != null) {
				PolBonusMoney = p.split(tPolBonusMoney);
			}
			String PolBonusYCLX[] = null;
			if (!"".equals(tPolBonusYCLX) && tPolBonusYCLX != null) {
				PolBonusYCLX = p.split(tPolBonusYCLX);
			}
			String PolSumBonus[] = null;
			if (!"".equals(tPolSumBonus) && tPolSumBonus != null) {
				PolSumBonus = p.split(tPolSumBonus);
			}
			String PolSumAccBala[] = null;
			if (!"".equals(tPolSumAccBala) && tPolSumAccBala != null) {
				PolSumAccBala = p.split(tPolSumAccBala);
			}

			String PolPayPrem[] = null;
			if (!"".equals(tPolPayPrem) && tPolPayPrem != null) {
				PolPayPrem = p.split(tPolPayPrem);
			}

			if (PolBonusMoney != null) {
				for (int i = 0; i < PolBonusMoney.length; i++) {
					strIO = new String[9];
					strIO[0] = BqNameFun.getRiskNameByPolNo(PolBonusMoney[i]
							.substring(0, PolBonusMoney[i].indexOf("-")));
					strIO[1] = BqNameFun.getCodeName(tBonusGetMode,
							"bonusgetmode");
					;
					strIO[2] = PolBonusMoney[i].substring(PolBonusMoney[i]
							.indexOf("-") + 1, PolBonusMoney[i].length());
					strIO[3] = PolBonusYCLX[i].substring(PolBonusYCLX[i]
							.indexOf("-") + 1, PolBonusYCLX[i].length());
					strIO[4] = PolSumBonus[i].substring(PolSumBonus[i]
							.indexOf("-") + 1, PolSumBonus[i].length());
					if ("1".equals(tBonusGetMode))// 累计生息
					{
						strIO[5] = PolSumAccBala[i].substring(PolSumAccBala[i]
								.indexOf("-") + 1, PolSumAccBala[i].length());
					} else {
						strIO[5] = "---";
					}

					if ("3".equals(tBonusGetMode))// 抵交保费
					{
						strIO[6] = PolPayPrem[i].substring(PolPayPrem[i]
								.indexOf("-") + 1, PolPayPrem[i].length());
					} else {
						strIO[6] = "---";
					}

					if ("5".equals(tBonusGetMode))// 增额交清
					{
						// strIO[7]=PolGetAmnt[i].substring(PolGetAmnt[i].indexOf("-")+1,
						// PolGetAmnt[i].length());
						// strIO[8]=PolSumGetAmnt[i].substring(PolSumGetAmnt[i].indexOf("-")+1,
						// PolSumGetAmnt[i].length());
						// 原红利增额激情的存放字段方式不对，采取新的方式 从LCDuty表中进行查询
						SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
						String tZRAmntSQL = " select sum(amnt)"
								+ "	from lcduty r "
								+ " where "
								+ "	  char_length(dutycode) = 10 "
								+ "	 and substr(firstpaydate, 1, 4) = '"
								+ "?firstpaydate?"
								+ "' and polno='"
								+ "?polno?" + "'";
						sqlbv5.sql(tZRAmntSQL);
						sqlbv5.put("firstpaydate", (Integer.parseInt(mLOPRTManagerSchema.getRemark()) + 1));
						sqlbv5.put("polno", PolBonusMoney[i].substring(0,PolBonusMoney[i].indexOf("-")));
						strIO[7] = tExeSQL.getOneValue(sqlbv5);
						SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
						String tZRSumAmntSQL = " select sum(amnt)"
								+ "	from lcduty r "
								+ " where  polno='"
								+ "?polno?" + "' ";
						sqlbv6.sql(tZRSumAmntSQL);
						sqlbv6.put("polno", PolBonusMoney[i].substring(0,PolBonusMoney[i].indexOf("-")));
						strIO[8] = tExeSQL.getOneValue(sqlbv6);

					} else {
						strIO[7] = "---";
						SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
						String tZRSumAmntSQL = " select sum(amnt)"
								+ "	from lcduty r "
								+ " where  polno='"+ "?polno?" + "' ";
						sqlbv7.sql(tZRSumAmntSQL);
						sqlbv7.put("polno",  PolBonusMoney[i].substring(0,PolBonusMoney[i].indexOf("-")));
						strIO[8] = tExeSQL.getOneValue(sqlbv7);
					}
					tBonusListTable.add(strIO);
				}
			}
			texttag.add("BonusAssDate", tSGetDate);
			texttag.add("BonusAsADate", tAGetDate);
		}
		return true;
	}
	private boolean preparePrintData() {
		String tPrintName = "红利派发通知书";
		XmlExportNew xmlExport = new XmlExportNew();// 新建一个XmlExport的实例
		xmlExport.createDocument(tPrintName);//初始化数据存储类
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(mLCContSchema.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			xmlExport.setUserLanguage(uLanguage);
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}
		if (tBonusListTable.size() > 0) {
			String b_strIO[] = new String[9];
			xmlExport.addListTable(tBonusListTable, b_strIO);
		}
		mResult.clear();
		mResult.addElement(xmlExport);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.Operator = "bq";
		mGlobalInput.ManageCom = "86";
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerDB.setOtherNo("86130020080219000222");
		tLOPRTManagerDB.setCode("BQ01");
		tLOPRTManagerSchema = tLOPRTManagerDB.query().get(1);
		VData tVData = new VData();
		tVData.add(tLOPRTManagerSchema);
		tVData.add(mGlobalInput);
		DispatchBonusPrintBL tDispatchBonusPrintBL = new DispatchBonusPrintBL();
		tDispatchBonusPrintBL.submitData(tVData, "PRINT");
		tDispatchBonusPrintBL = null;

	}
}
