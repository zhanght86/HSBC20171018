package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全收据打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：liurx
 * @version：1.0
 * @CreateDate：2005-09-15 Modified By QianLy on 2006-10-23
 */
public class EdorCheckPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(EdorCheckPrintBL.class);
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
	private TransferData mTransferData = new TransferData();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private String mOtherSign = ""; // 需要特殊处理的分公司的个性标记
	private String mContNo = "";
	private String mEdorNo = "";
	private String mEdorType = "";
	private String mEdorAcceptNo = "";
	private String mPayDate = "";
	private String mName = "";
	private String mCMoney = "";
	private String mMoney = "0.00";
	private String mEdorItem = "";
	private String mInterest = "0.00";
	private String mFee = "0.00";
	private String mAgentCode = "";
	private String mAgentName = "";
	private String CMPrem = "0.00";
	private String CMInterest = "0.00";
	private double tInterest = 0;
	private double tFee = 0;
	private String VTS_NAME = "PEdorCheque.vts"; // 模板名称
	private double sumPrem = 0;// 保费总金额，包括利息，此统计仅用于会计科目名称的动态显示
	private String chgItem = "";
	private String mCurrentDate = PubFun.getCurrentDate();
	private String companyCode = "";
	private String mPhone = "";
	private String mAddress = "";
	private int mRow = 0;// 计行器，程序取出数来后，从模板行列阵区域上第一行第一列开始置值
	private int mCol = 0;// 计列器，程序取出数来后，从模板行列阵区域上第一行第一列开始置值
	private int mCount = 0;

	public EdorCheckPrintBL() {
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
			CError tError = new CError();
			tError.moduleName = "EdorCheckPrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}

		// 从数据库获得数据
		if (!getBaseData()) {
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
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null || mLOPRTManagerSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		String tPrtSeq = mLOPRTManagerSchema.getPrtSeq();
		if (tPrtSeq == null || tPrtSeq.trim().equals("")) {
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

		mEdorNo = mLOPRTManagerSchema.getOtherNo();

		if (mEdorNo == null || mEdorNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorCheckPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取批单号失败！";
			this.mErrors.addOneError(tError);

			return false;
		}

		mEdorAcceptNo = mLOPRTManagerSchema.getStandbyFlag1();
		if (mEdorAcceptNo == null || mEdorAcceptNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorCheckPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保全受理号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = mLOPRTManagerSchema.getStandbyFlag2();
		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorCheckPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保单号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		mEdorType = mLOPRTManagerSchema.getStandbyFlag3();
		if (mEdorType == null || mEdorType.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorCheckPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取批改类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mTransferData != null) {
			mOtherSign = (String) mTransferData.getValueByName("OtherSign");
			logger.debug("mOtherSign:" + mOtherSign);
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		return true;
	}

	private boolean getBaseData() {
		return true;
	}

	private boolean preparePrintData() {
		XmlExport tXmlExport = new XmlExport();

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";

		// XinYQ modified on 2007-03-22 : 发票抬头 保单层收费类业务打印发票时付款人一律取投保人姓名,
		// 客户层变更收费类业务一律取申请客户号对应的客户姓名
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		strsql = "select OtherNoType, EdorAppName from LPEdorApp where EdorAcceptNo = '"
				+ "?mEdorAcceptNo?" + "'";
		sqlbv1.sql(strsql);
		sqlbv1.put("mEdorAcceptNo", mEdorAcceptNo);
		tssrs = texesql.execSQL(sqlbv1);
		if (tssrs != null && tssrs.getMaxRow() > 0) {
			String sOtherNoType = new String("");
			sOtherNoType = tssrs.GetText(1, 1);
			if (sOtherNoType != null && sOtherNoType.trim().equals("1")) {
				mName = tssrs.GetText(1, 2);
			} else if (sOtherNoType.trim().equals("3")) {
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				strsql = "select AppntName from LCCont where ContNo = (select OtherNo from LPEdorApp where EdorAcceptNo = '"
						+ "?mEdorAcceptNo?" + "')";
				sqlbv2.sql(strsql);
				sqlbv2.put("mEdorAcceptNo", mEdorAcceptNo);
				mName = texesql.getOneValue(sqlbv2);
			}
		}
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strsql = "select paydate,(case when (select substr(comcode,1,6) from lduser where usercode = a.operator) is not null then (select substr(comcode,1,6) from lduser where usercode = a.operator)  else '' end),"
					+ "(select username from lduser where usercode = a.operator), "
					+ "(select codename from ldcode where codetype = 'edorgetpayquery' and code = (select paymode from ljtempfeeclass where tempfeeno = a.tempfeeno and rownum = 1)),"
					+ " (select name from ldcom where trim(comcode) = (select trim(substr(comcode,1,6)) from lduser where usercode = a.operator)) "
					+ "  from ljtempfee a where  othernotype = '10' and otherno ='"
					+ "?mEdorAcceptNo?" + "'";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strsql = "select paydate,(case when (select substr(comcode,1,6) from lduser where usercode = a.operator) is not null then (select substr(comcode,1,6) from lduser where usercode = a.operator)  else '' end),"
					+ "(select username from lduser where usercode = a.operator), "
					+ "(select codename from ldcode where codetype = 'edorgetpayquery' and code = (select paymode from ljtempfeeclass where tempfeeno = a.tempfeeno  limit 0,1)),"
					+ " (select name from ldcom where trim(comcode) = (select trim(substr(comcode,1,6)) from lduser where usercode = a.operator)) "
					+ "  from ljtempfee a where  othernotype = '10' and otherno ='"
					+ "?mEdorAcceptNo?" + "'";
		}
		sqlbv3.sql(strsql);
		sqlbv3.put("mEdorAcceptNo", mEdorAcceptNo);
		tssrs = texesql.execSQL(sqlbv3);
		if (tssrs != null && tssrs.getMaxRow() > 0) {
			mPayDate = tssrs.GetText(1, 1);
			companyCode = tssrs.GetText(1, 2);
			String[] addressArr = BqNameFun.getAddressArr(companyCode);
			mPhone = addressArr[BqNameFun.PHONE_AT];
			mAddress = addressArr[BqNameFun.ADDRESS_AT];
			texttag.add("Operator", tssrs.GetText(1, 3));
			texttag.add("Company", companyCode);
			texttag.add("Phone", mPhone);
			texttag.add("Address", mAddress);
			texttag.add("PayForm", tssrs.GetText(1, 4));
			texttag.add("ComName", tssrs.GetText(1, 5));
		}
		if (mEdorType.trim().equals("CM") || mEdorType.trim().equals("HI")) {
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			strsql = "select (case when sumactupaymoney is not null then sumactupaymoney  else 0 end) from ljapay where incometype = '10' and incomeno = '"
					+ "?mEdorAcceptNo?" + "'";
			sqlbv4.sql(strsql);
			sqlbv4.put("mEdorAcceptNo", mEdorAcceptNo);
			tssrs = texesql.execSQL(sqlbv4);
			if (tssrs == null || tssrs.getMaxRow() <= 0) {
				CError.buildErr(this, "无实收费信息！");
				return false;
			}
			mMoney = BqNameFun.getRound(tssrs.GetText(1, 1));
			mCMoney = BqNameFun.getCMoney(mMoney);
			mMoney = BqNameFun.getPartRound(mMoney) + "元";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			strsql = "select edorname from lmedoritem where " + " edorcode = '"
					+ "?mEdorType?" + "' and appobj = 'B'  ";
			sqlbv5.sql(strsql);
			sqlbv5.put("mEdorType", mEdorType);
			tssrs = texesql.execSQL(sqlbv5);
			if (tssrs != null && tssrs.getMaxRow() > 0) {
				mEdorItem = tssrs.GetText(1, 1);
			}
			chgItem = "保费";
			/** ************************注意这区域中间最好不要插其它sql********************************** */
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			strsql = "select distinct trim(contno) from ljagetendorse where endorsementno in (select edorno from lpedormain where edoracceptno = '"
					+ "?mEdorAcceptNo?" + "')";
			sqlbv6.sql(strsql);
			sqlbv6.put("mEdorAcceptNo", mEdorAcceptNo);
			tssrs = texesql.execSQL(sqlbv6);
			if (tssrs == null || tssrs.getMaxRow() <= 0) {
				CError.buildErr(this, "查询此次保全所涉及保单失败！");
				return false;
			}
			String cmPayToDate = getCMPayDate(tssrs);
			cmPayToDate = BqNameFun.getFDate(cmPayToDate);
			texttag.add("nextPayDate", cmPayToDate);

			for (int iCont = 1; iCont <= tssrs.getMaxRow(); iCont++) {
				if (iCont == 1) {
					mContNo = "保单" + tssrs.GetText(iCont, 1);
				} else {
					mContNo += "，保单" + tssrs.GetText(iCont, 1);
				}
			}
			String str = "系 " + mContNo + "项下之" + mEdorItem + chgItem;
			texttag.add("ContList", mContNo);// 宁波发票用
			texttag.add("mark", mContNo + "项下之" + mEdorItem + chgItem);// 上海发票用
			int len = str.length();
			if (len <= 54) {
				texttag.add("Content", str);
			} else {
				str = "系";
				texttag.add("Content", str);
				mCol = 0;
				for (int tt = 1; tt <= tssrs.getMaxRow(); tt++) {
					if (tt == 15) {
						break;// 如果一行打两个，最多允许打14张保单
					}
					if (mCol != 1) {
						mRow++;
					}
					mCol++;
					if (mCol == 3) {
						mCol = 1;
					}
					texttag.add("List" + String.valueOf(mRow)
							+ String.valueOf(mCol), "  保单"
							+ tssrs.GetText(tt, 1));
				}
				mRow++;
				texttag.add("List" + String.valueOf(mRow) + "1", "项下之"
						+ mEdorItem + chgItem);
			}
			/** *********************************************************************************** */
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			strsql = "select (case when (select sum(getmoney) from ljagetendorse where "
					+ " feeoperationtype = '"
					+ "?mEdorType?"
					+ "' and endorsementno in "
					+ " (select edorno from lpedoritem where edoracceptno = '"
					+ "?mEdorAcceptNo?"
					+ "' and edortype = '"
					+ "?mEdorType?"
					+ "') "
					+ " and feefinatype in ("
					+ getFeeFinaType(mEdorType)
					+ ")) is not null then (select sum(getmoney) from ljagetendorse where "
					+ " feeoperationtype = '"
					+ "?mEdorType?"
					+ "' and endorsementno in "
					+ " (select edorno from lpedoritem where edoracceptno = '"
					+ "?mEdorAcceptNo?"
					+ "' and edortype = '"
					+ "?mEdorType?"
					+ "') "
					+ " and feefinatype in ("
					+ getFeeFinaType(mEdorType)
					+ "))  else 0 end),"
					+ " (case when (select sum(getmoney) from ljagetendorse where "
					+ " feeoperationtype = '"
					+"?mEdorType?"
					+ "' and endorsementno in "
					+ " (select edorno from lpedoritem where edoracceptno = '"
					+ "?mEdorAcceptNo?"
					+ "' and edortype = '"
					+"?mEdorType?"
					+ "') "
					+ " and feefinatype ='LX') is not null then (select sum(getmoney) from ljagetendorse where "
					+ " feeoperationtype = '"
					+"?mEdorType?"
					+ "' and endorsementno in "
					+ " (select edorno from lpedoritem where edoracceptno = '"
					+ "?mEdorAcceptNo?"
					+ "' and edortype = '"
					+"?mEdorType?"
					+ "') "
					+ " and feefinatype ='LX')  else 0 end) " + " from dual ";
			sqlbv7.sql(strsql);
			sqlbv7.put("mEdorType", mEdorType);
			sqlbv7.put("mEdorAcceptNo", mEdorAcceptNo);
			tssrs = texesql.execSQL(sqlbv7);
			double tCMPrem = Double.parseDouble(tssrs.GetText(1, 1));
			double tCMInterest = Double.parseDouble(tssrs.GetText(1, 2));
			// tXmlExport.addDisplayControl("displayCM");
			mCol = 0;
			if (tCMPrem > 0) {
				mRow++;
				mCol++;
				CMPrem = BqNameFun.getPartRound(tssrs.GetText(1, 1));
				texttag.add("List" + String.valueOf(mRow)
						+ String.valueOf(mCol), "保费：" + CMPrem + "元");
				// 河南，重庆专用：
				texttag.add("PayItem1", mEdorItem + "保费");
				texttag.add("PayMoney1", CMPrem);
			}
			if (tCMInterest > 0) {
				if (mRow == 0) {
					mRow++;
				}
				mCol++;
				CMInterest = BqNameFun.getPartRound(tssrs.GetText(1, 2));
				texttag.add("List" + String.valueOf(mRow)
						+ String.valueOf(mCol), "利息：" + CMInterest + "元");
				// 河南，重庆专用：
				texttag.add("PayItem2", mEdorItem + "利息");
				texttag.add("PayMoney2", CMInterest);
			}
			// 客户层业务员显示为空
			texttag.add("AgentCode", "");
			texttag.add("AgentName", "");
			if (mOtherSign == null || !mOtherSign.trim().equals("1")) {
				// 标志为"1"的分公司会单独有地方显示业务员
				mAgentCode = "业务员编号：";
				mAgentName = "业务员姓名：";
				mRow++;
				texttag.add("List" + String.valueOf(mRow) + "1", mAgentCode);
				texttag.add("List" + String.valueOf(mRow) + "2", mAgentName);
			}
		} else {
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mContNo);
			if (!tLCContDB.getInfo()) {
				CError.buildErr(this, "无保单信息！");
				return false;
			}
			mLCContSchema = tLCContDB.getSchema();

			mAgentCode = mLCContSchema.getAgentCode();
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			strsql = "select edorname,(select name from laagent where (agentcode)= '"
					+ "?mAgentCode?"
					+ "') from lmedoritem where "
					+ " edorcode = '" + "?mEdorType?" + "' and appobj = 'I' ";
			sqlbv8.sql(strsql);
			sqlbv8.put("mAgentCode", mAgentCode);
			sqlbv8.put("mEdorType", mEdorType);
			tssrs = texesql.execSQL(sqlbv8);
			if (tssrs != null && tssrs.getMaxRow() > 0) {
				mEdorItem = tssrs.GetText(1, 1);
				mAgentName = tssrs.GetText(1, 2);
			}
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			strsql = "select (case when (select sum(getmoney) from ljagetendorse where "
					+ " endorsementno = '"
					+ "?mEdorNo?"
					+ "' and feeoperationtype = '"
					+ "?mEdorType?"
					+ "' "
					+ " and contno = '"
					+ "?mContNo?"
					+ "' ) is not null then (select sum(getmoney) from ljagetendorse where "
					+ " endorsementno = '"
					+ "?mEdorNo?"
					+ "' and feeoperationtype = '"
					+ "?mEdorType?"
					+ "' "
					+ " and contno = '"
					+ "?mContNo?"
					+ "' )  else 0 end)+(case when (select sum(SumActuPayMoney) "
					+ " from ljapayperson where payno = (select payno from ljapay where othernotype = '10' and otherno = '"
					+ "?mEdorAcceptNo?"
					+ "') "
					+ " and contno = '"
					+ "?mContNo?"
					+ "' and paytype = '" + "?mEdorType?" + "') is not null then (select sum(SumActuPayMoney) "
					+ " from ljapayperson where payno = (select payno from ljapay where othernotype = '10' and otherno = '"
					+ "?mEdorAcceptNo?"
					+ "') "
					+ " and contno = '"
					+ "?mContNo?"
					+ "' and paytype = '" + "?mEdorType?" + "')  else 0 end) from dual ";
			sqlbv9.sql(strsql);
			sqlbv9.put("mEdorNo", mEdorNo);
			sqlbv9.put("mEdorType", mEdorType);
			sqlbv9.put("mContNo", mContNo);
			sqlbv9.put("mEdorAcceptNo", mEdorAcceptNo);
			tssrs = texesql.execSQL(sqlbv9);
			double tMoney = Double.parseDouble(tssrs.GetText(1, 1));
			if (tMoney == 0) {
				CError.buildErr(this, "该保单未发生财务收费！");
				return false;
			}
			mMoney = BqNameFun.getRound(tssrs.GetText(1, 1));
			mCMoney = BqNameFun.getCMoney(mMoney);
			mMoney = BqNameFun.getPartRound(mMoney) + "元";
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			strsql = "select (case when (select abs(sum(getmoney)) from ljagetendorse "
					+ " where endorsementno = '"
					+ "?mEdorNo?"
					+ "' and feeoperationtype = '"
					+ "?mEdorType?"
					+ "' and contno = '"
					+ "?mContNo?"
					+ "' and feefinatype = 'LX') is not null then (select abs(sum(getmoney)) from ljagetendorse "
					+ " where endorsementno = '"
					+ "?mEdorNo?"
					+ "' and feeoperationtype = '"
					+ "?mEdorType?"
					+ "' and contno = '"
					+ "?mContNo?"
					+ "' and feefinatype = 'LX')  else 0 end), "
					+ " (case when (select abs(sum(getmoney)) from ljagetendorse "
					+ " where endorsementno = '"
					+ "?mEdorNo?"
					+ "' and feeoperationtype = '"
					+ "?mEdorType?"
					+ "' and contno = '"
					+ "?mContNo?"
					+ "' and feefinatype = 'GB') is not null then (select abs(sum(getmoney)) from ljagetendorse "
					+ " where endorsementno = '"
					+ "?mEdorNo?"
					+ "' and feeoperationtype = '"
					+ "?mEdorType?"
					+ "' and contno = '"
					+ "?mContNo?"
					+ "' and feefinatype = 'GB')  else 0 end) " + " from dual ";
			sqlbv10.sql(strsql);
			sqlbv10.put("mEdorNo", mEdorNo);
			sqlbv10.put("mEdorType", mEdorType);
			sqlbv10.put("mContNo", mContNo);
			tssrs = texesql.execSQL(sqlbv10);
			tInterest = Double.parseDouble(tssrs.GetText(1, 1));
			tFee = Double.parseDouble(tssrs.GetText(1, 2));
			mInterest = BqNameFun.getPartRound(tssrs.GetText(1, 1));
			mFee = BqNameFun.getPartRound(tssrs.GetText(1, 2));
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			strsql = " select ("
					+ "select riskcode from lcpol p where polno = t.polno), sum(money) "
					+ " from (select polno, getmoney money from ljagetendorse where endorsementno = '"
					+ "?mEdorNo?"
					+ "' and feeoperationtype = '"
					+ "?mEdorType?"
					+ "' and feefinatype in ("
					+ getFeeFinaType(mEdorType)
					+ ") "
					+ "union "
					+ "select polno, sum(SumActuPayMoney) money  from ljapayperson where contno = '"
					+ "?mContNo?"
					+ "' and  paytype = '"
					+ "?mEdorType?"
					+ "' and payno = (select payno from ljapay where othernotype = '10' and otherno = '"
					+ "?mEdorAcceptNo?" + "' )group by polno) t group by polno  ";
			logger.debug("=================================\n" + strsql);
			sqlbv11.sql(strsql);
			sqlbv11.put("mEdorNo", mEdorNo);
			sqlbv11.put("mEdorType", mEdorType);
			sqlbv11.put("mContNo", mContNo);
			sqlbv11.put("mEdorAcceptNo", mEdorAcceptNo);
			tssrs = texesql.execSQL(sqlbv11);
			String strLine1 = "";
			String strLine2 = "";
			if (tssrs != null && tssrs.getMaxRow() > 0) {
				for (int i = 1; i <= tssrs.getMaxRow(); i++) {
					mRow++;
					strLine1 = "险种：" + tssrs.GetText(i, 1);
					strLine2 = getFeeTitle(mEdorType) + "："
							+ BqNameFun.getPartRound(tssrs.GetText(i, 2)) + "元";
					texttag.add("List" + String.valueOf(mRow) + "1", strLine1);
					texttag.add("List" + String.valueOf(mRow) + "2", strLine2);
					sumPrem += Double.parseDouble(tssrs.GetText(i, 2));
				}
				// 河南，重庆专用
				mCount++;
				texttag.add("PayItem" + String.valueOf(mCount), mEdorItem
						+ getFeeTitle(mEdorType));
				texttag.add("PayMoney" + String.valueOf(mCount), BqNameFun
						.getRound(sumPrem));
			}
			// strsql = "select paytodate from lccont where contno =
			// '"+mContNo+"'";
			// tssrs = texesql.execSQL(strsql);
			// if (tssrs != null && tssrs.getMaxRow() > 0)
			// {
			// String tPayToDate = tssrs.GetText(1,1);
			// tPayToDate = BqNameFun.getFDate(tPayToDate);
			// texttag.add("nextPayDate", tPayToDate);
			// }
			String tPayToDate = getPayToDate(mContNo);
			String tRiskName = getMainPolName(mContNo);
			String tRiskCode = getMainPolCode(mContNo);// Add By QianLy 贵州专用
			texttag.add("RiskCode", tRiskCode);// Add By QianLy 贵州专用
			texttag.add("RiskName", tRiskName);

			tPayToDate = BqNameFun.getFDate(tPayToDate);
			texttag.add("nextPayDate", tPayToDate);

			texttag.add("LCCont.ContNo", mContNo);
			mContNo = "保单" + mContNo;
			if (tInterest > 0) {
				mRow++;
				texttag.add("List" + String.valueOf(mRow) + "1", "利息："
						+ mInterest + "元");
				mCount++;
				// 河南，重庆专用
				texttag.add("PayItem" + String.valueOf(mCount), mEdorItem
						+ "利息");
				texttag.add("PayMoney" + String.valueOf(mCount), mInterest);
			}
			if (tFee > 0) {
				mRow++;
				texttag.add("List" + String.valueOf(mRow) + "1", "工本费：" + mFee);
				mCount++;
				// 河南，重庆专用
				texttag.add("PayItem" + String.valueOf(mCount), mEdorItem
						+ "工本费");
				texttag.add("PayMoney" + String.valueOf(mCount), mFee + "元");
			}
			chgItem = getChgItem(mEdorType);
			if (mName == null || mName.trim().equals("")) {
				mName = mLCContSchema.getAppntName();
			}
			texttag.add("AgentCode", mAgentCode);
			texttag.add("AgentName", mAgentName);
			if (mOtherSign == null || !mOtherSign.trim().equals("1")) {
				// 标志为"1"的分公司会单独有地方显示业务员
				mAgentCode = "业务员编号：" + mAgentCode;
				mAgentName = "业务员姓名：" + mAgentName;
				mRow++;
				texttag.add("List" + String.valueOf(mRow) + "1", mAgentCode);
				texttag.add("List" + String.valueOf(mRow) + "2", mAgentName);
			}
			String str = "系 " + mContNo + "项下之" + mEdorItem + chgItem;
			texttag.add("Content", str);
			texttag.add("ContList", mContNo);// 宁波发票用
			texttag.add("mark", mContNo + "项下之" + mEdorItem + chgItem);// 上海,辽宁发票用
		}
		mCurrentDate = BqNameFun.getFDate(mCurrentDate);

		texttag.add("LCCont.AppntName", mName);
		texttag.add("SysDate", mCurrentDate);
		texttag.add("EdorItem", "保全项目:" + mEdorItem);// 贵州发票用
		// texttag.add("EdorItem", mEdorItem);//注掉3个，拼成1个
		// texttag.add("ChgItem", chgItem);
		texttag.add("CMoney", mCMoney);
		texttag.add("Money", mMoney);
		// texttag.add("ContNo", mContNo);
		texttag.add("PayDate", BqNameFun.getFDate(mPayDate));
		texttag.add("CustomerName", mName);
		texttag.add("Year", BqNameFun.getYear(mPayDate));
		texttag.add("Month", BqNameFun.getMonth(mPayDate));
		texttag.add("Day", BqNameFun.getDay(mPayDate));

		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		// tXmlExport.outputDocumentToFile("d:\\", "penotice"); //测试用
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;

	}

	/** ************************************************************************* */
	/**
	 * 由批改类型得到其财务类型标题
	 * 
	 * @param: String
	 * @return: String
	 */
	private String getFeeTitle(String tEdorType) {
		String str = "保费";
		if (tEdorType != null && !tEdorType.trim().equals("")) {
			tEdorType = tEdorType.trim();
			if (tEdorType.equals("RF")) {
				str = "本金";
			}
		}
		return str;
	}

	/**
	 * 由批改类型得到批改补退费表中其财务类型
	 * 
	 * @param: String
	 * @return: String
	 */
	private String getFeeFinaType(String tEdorType) {
		String str = "'BF','TF'";
		if (tEdorType != null && !tEdorType.trim().equals("")) {
			tEdorType = tEdorType.trim();
			// 贷款清偿
			if (tEdorType.equals("RF")) {
				str = "'HK'";
			}
		}
		return str;
	}

	private String getChgItem(String tEdorType) {
		String str = "";
		if (tEdorType != null && !tEdorType.trim().equals("")) {
			tEdorType = tEdorType.trim();
			// 贷款清偿
			if (tEdorType.equals("RF")) {
				str = "本息和";
				return str;
			}
		}
		sumPrem += tInterest;
		if (sumPrem > 0) {
			if (tFee > 0) {
				str = "保费/工本费";
			} else {
				str = "保费";
			}
		} else if (tFee > 0) {
			str = "工本费";
		}
		return str;
	}

	// 通过保单号得到其保费缴至日

	// 如果主险非趸交，则直接取其主险的paytodate字段
	// 如果主险趸交，则取其附加险的paytodate，如果没有附加险，或附加险是长期、趸交的附加险，则取开票日期
	private String getPayToDate(String tContNo) {
		String tPayToDate = "";
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		String strsql = "select (case when (select paytodate from lcpol where polno = mainpolno and payintv<>'0' and contno = '"
				+ "?tContNo?"
				+ "') is not null then (select paytodate from lcpol where polno = mainpolno and payintv<>'0' and contno = '"
				+ "?tContNo?"
				+ "')  else ((case when (select min(paytodate) from lcpol where polno <> mainpolno and appflag='1' and contno = '"
				+ "?tContNo?"
				+ "' and ((payintv = '0' and riskcode not in(select riskcode from lmriskapp where riskperiod = 'L' and subriskflag = 'S')) or (payintv <> '0'))) is not null then (select min(paytodate) from lcpol where polno <> mainpolno and appflag='1' and contno = '"
				+ "?tContNo?"
				+ "' and ((payintv = '0' and riskcode not in(select riskcode from lmriskapp where riskperiod = 'L' and subriskflag = 'S')) or (payintv <> '0')))  else " + "to_date('?mCurrentDate?','yyyy-mm-dd')" + " end)) end) from dual";
		sqlbv12.sql(strsql);
		sqlbv12.put("tContNo", tContNo);
		sqlbv12.put("mCurrentDate", mCurrentDate);
		tssrs = texesql.execSQL(sqlbv12);
		if (tssrs != null && tssrs.getMaxRow() >= 1) {
			tPayToDate = tssrs.GetText(1, 1);
		}
		return tPayToDate;
	}

	private String getCMPayDate(SSRS ssrs) {
		String cmPayToDate = "";
		if (ssrs != null && ssrs.getMaxRow() >= 1) {
			SSRS tssrs = new SSRS();
			ExeSQL texesql = new ExeSQL();
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			String strsql = "select (case when min(a.paytodate) is not null then min(a.paytodate)  else " + "to_date('?mCurrentDate?','yyyy-mm-dd')" + " end) from (";
			for (int i = 1; i <= ssrs.getMaxRow(); i++) {
				if (i != 1) {
					strsql += " union ";
				}
				strsql += " select (case when (select paytodate from lcpol where polno = mainpolno and payintv<>'0' and contno = '"+ "?contno"+i+"?"+ "') is not null then (select paytodate from lcpol where polno = mainpolno and payintv<>'0' and contno = '"+ "?contno"+i+"?"+ "')  else ((case when (select min(paytodate) from lcpol where polno <> mainpolno and appflag='1' and contno = '"
						+ "?contno"+i+"?"
						+ "' and ((payintv = '0' and riskcode not in(select riskcode from lmriskapp where riskperiod = 'L' and subriskflag = 'S')) or (payintv <> '0'))) is not null then (select min(paytodate) from lcpol where polno <> mainpolno and appflag='1' and contno = '"
						+ "?contno"+i+"?"
						+ "' and ((payintv = '0' and riskcode not in(select riskcode from lmriskapp where riskperiod = 'L' and subriskflag = 'S')) or (payintv <> '0')))  else null end)) end) paytodate from dual ";
				sqlbv13.put("contno"+i, ssrs.GetText(i, 1));
			}
			strsql += ") a";
			sqlbv13.sql(strsql);
			sqlbv13.put("mCurrentDate", mCurrentDate);
			tssrs = texesql.execSQL(sqlbv13);
			if (tssrs != null && tssrs.getMaxRow() >= 1) {
				cmPayToDate = tssrs.GetText(1, 1);
			}
		}
		return cmPayToDate;
	}

	private String getMainPolName(String tContNo) {
		String tName = "";
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		String strsql = "select (select riskstatname from lmrisk where riskcode = a.riskcode) from lcpol a where contno = '"
				+ "?tContNo?" + "' and polno = mainpolno";
		sqlbv15.sql(strsql);
		sqlbv15.put("tContNo", tContNo);
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		tssrs = texesql.execSQL(sqlbv15);
		if (tssrs != null && tssrs.getMaxRow() > 0) {
			tName = tssrs.GetText(1, 1);
		}
		return tName;
	}

	private String getMainPolCode(String tContNo) {
		String tName = "";
		SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
		String strsql = "select riskcode from lcpol a where contno = '"
				+ "?tContNo?" + "' and polno = mainpolno";
		sqlbv16.sql(strsql);
		sqlbv16.put("tContNo", tContNo);
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		tssrs = texesql.execSQL(sqlbv16);
		if (tssrs != null && tssrs.getMaxRow() > 0) {
			tName = tssrs.GetText(1, 1);
		}
		return tName;
	}

	/** ************************************************************************* */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
	// public static void main(String[] args)
	// {
	// logger.debug("test begin:");
	// EdorCheckPrintBL tEdorCheckPrintBL = new EdorCheckPrintBL();
	//
	// VData tVData = new VData();
	//
	// GlobalInput tGlobalInput = new GlobalInput();
	// tGlobalInput.ManageCom = "86210000";
	// tGlobalInput.Operator = "liurx";
	//
	// String tPrtSeq = "8103200098175";
	// LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	// tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
	//
	// tVData.add(tGlobalInput);
	// tVData.add(tLOPRTManagerSchema);
	// if(!tEdorCheckPrintBL.submitData(tVData, "PRINT"))
	// {
	// logger.debug("test failed");
	// }
	// logger.debug("test end");
	// }
}
