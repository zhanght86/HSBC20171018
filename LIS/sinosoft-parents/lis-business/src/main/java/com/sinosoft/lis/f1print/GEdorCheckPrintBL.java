package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContSchema;
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
 * Description: 保全团体收据打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：QianLy
 * @version：1.0
 * @CreateDate：2006-11-20
 */
public class GEdorCheckPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(GEdorCheckPrintBL.class);
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
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private String mOtherSign = ""; // 需要特殊处理的分公司的个性标记
	private String mGrpContNo = "";
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
	private String VTS_NAME = "GEdorCheque.vts"; // 模板名称
	private double sumPrem = 0;// 保费总金额，包括利息，此统计仅用于会计科目名称的动态显示
	private String chgItem = "";
	private String mCurrentDate = PubFun.getCurrentDate();
	private String companyCode = "";
	private String mPhone = "";
	private String mAddress = "";
	private int mRow = 0;// 计行器，程序取出数来后，从模板行列阵区域上第一行第一列开始置值
	private int mCol = 0;// 计列器，程序取出数来后，从模板行列阵区域上第一行第一列开始置值
	private int mCount = 0;

	public GEdorCheckPrintBL() {
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

		mGrpContNo = mLOPRTManagerSchema.getStandbyFlag2();
		if (mGrpContNo == null || mGrpContNo.trim().equals("")) {
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
		// ===del===zhangtao===团体发票直接打印投保单位名称，不需要打印申请人姓名====BGN==========
		// strsql = "select edorappname from lpedorapp where edoracceptno =
		// '"+mEdorAcceptNo+"'";
		// tssrs = texesql.execSQL(strsql);
		// if (tssrs != null && tssrs.getMaxRow() > 0){
		// if(tssrs.GetText(1,1)!=null && !tssrs.GetText(1,1).equals("")){
		// mName = tssrs.GetText(1,1);
		// }
		// }
		// ===del===zhangtao===团体发票直接打印投保单位名称，不需要打印申请人姓名====END==========
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
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
					+ "(select codename from ldcode where codetype = 'edorgetpayquery' and code = (select paymode from ljtempfeeclass where tempfeeno = a.tempfeeno limit 0,1)),"
					+ " (select name from ldcom where trim(comcode) = (select trim(substr(comcode,1,6)) from lduser where usercode = a.operator)) "
					+ "  from ljtempfee a where  othernotype = '10' and otherno ='"
					+ "?mEdorAcceptNo?" + "'";
		}
		sqlbv1.sql(strsql);
		sqlbv1.put("mEdorAcceptNo", mEdorAcceptNo);
		tssrs = texesql.execSQL(sqlbv1);
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
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mGrpContNo);
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "无保单信息！");
			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema();

		mAgentCode = mLCGrpContSchema.getAgentCode();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		strsql = "select edorname,(select name from laagent where (agentcode)= '"
				+ "?mAgentCode?"
				+ "') from lmedoritem where "
				+ " edorcode = '"
				+ "?mEdorType?" + "' and appobj = 'G' ";
		sqlbv2.sql(strsql);
		sqlbv2.put("mAgentCode", mAgentCode);
		sqlbv2.put("mEdorType", mEdorType);
		tssrs = texesql.execSQL(sqlbv2);
		if (tssrs != null && tssrs.getMaxRow() > 0) {
			mEdorItem = tssrs.GetText(1, 1);
			mAgentName = tssrs.GetText(1, 2);
		}
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		strsql = "select (case when (select sum(getmoney) from ljagetendorse where "
				+ " endorsementno = '"
				+ "?mEdorNo?"
				+ "'"
				+ " and feeoperationtype = '"
				+ "?mEdorType?"
				+ "'"
				+ " and grpcontno = '"
				+ "?mGrpContNo?"
				+ "'"
				+ " ) is not null then (select sum(getmoney) from ljagetendorse where "
				+ " endorsementno = '"
				+ "?mEdorNo?"
				+ "'"
				+ " and feeoperationtype = '"
				+ "?mEdorType?"
				+ "'"
				+ " and grpcontno = '"
				+ "?mGrpContNo?"
				+ "'"
				+ " )  else 0 end)+(case when (select sum(SumActuPayMoney) "
				+ " from ljapayperson where payno = (select payno from ljapay where othernotype = '10' and otherno = '"
				+ "?mEdorAcceptNo?" + "') " + " and grpcontno = '" + "?mGrpContNo?"
				+ "'" + " and paytype = '" + "?mEdorType?" + "'"
				+ " ) is not null then (select sum(SumActuPayMoney) "
				+ " from ljapayperson where payno = (select payno from ljapay where othernotype = '10' and otherno = '"
				+ "?mEdorAcceptNo?" + "') " + " and grpcontno = '" + "?mGrpContNo?"
				+ "'" + " and paytype = '" + "?mEdorType?" + "'"
				+ " )  else 0 end) from dual ";
		sqlbv3.sql(strsql);
		sqlbv3.put("mEdorNo", mEdorNo);
		sqlbv3.put("mEdorType", mEdorType);
		sqlbv3.put("mGrpContNo", mGrpContNo);
		sqlbv3.put("mEdorAcceptNo", mEdorAcceptNo);
		tssrs = texesql.execSQL(sqlbv3);
		double tMoney = Double.parseDouble(tssrs.GetText(1, 1));
		if (tMoney == 0) {
			CError.buildErr(this, "该集体保单未发生财务收费！");
			return false;
		}
		mMoney = BqNameFun.getRoundMoney(tssrs.GetText(1, 1));
		mCMoney = BqNameFun.getCMoney(mMoney);
		mMoney = BqNameFun.chgMoney(mMoney) + "元";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		strsql = "select (case when (select abs(sum(getmoney)) from ljagetendorse "
				+ " where endorsementno = '" + "?mEdorNo?" + "'"
				+ " and feeoperationtype = '" + "?mEdorType?" + "'"
				+ " and grpcontno = '" + "?mGrpContNo?" + "'"
				+ " and feefinatype = 'LX') is not null then (select abs(sum(getmoney)) from ljagetendorse "
				+ " where endorsementno = '" + "?mEdorNo?" + "'"
				+ " and feeoperationtype = '" + "?mEdorType?" + "'"
				+ " and grpcontno = '" + "?mGrpContNo?" + "'"
				+ " and feefinatype = 'LX')  else 0 end),"
				+ " (case when (select abs(sum(getmoney)) from ljagetendorse "
				+ " where endorsementno = '" + "?mEdorNo?" + "'"
				+ " and feeoperationtype = '" + "?mEdorType?" + "'"
				+ " and grpcontno = '" + "?mGrpContNo?" + "'"
				+ " and feefinatype = 'GB') is not null then (select abs(sum(getmoney)) from ljagetendorse "
				+ " where endorsementno = '" + "?mEdorNo?" + "'"
				+ " and feeoperationtype = '" + "?mEdorType?" + "'"
				+ " and grpcontno = '" + "?mGrpContNo?" + "'"
				+ " and feefinatype = 'GB')  else 0 end) " + " from dual ";
		sqlbv4.sql(strsql);
		sqlbv4.put("mEdorNo", mEdorNo);
		sqlbv4.put("mEdorType", mEdorType);
		sqlbv4.put("mGrpContNo", mGrpContNo);
		tssrs = texesql.execSQL(sqlbv4);
		tInterest = Double.parseDouble(tssrs.GetText(1, 1));
		tFee = Double.parseDouble(tssrs.GetText(1, 2));
		mInterest = BqNameFun.chgMoney(tssrs.GetText(1, 1));
		mFee = BqNameFun.chgMoney(tssrs.GetText(1, 2));
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		strsql = "select (select riskcode from lcgrppol p where grppolno = t.grppolno), sum(money) "
				+ " from (select grppolno, getmoney money from ljagetendorse where endorsementno = '"
				+ "?mEdorNo?"
				+ "'"
				+ " and feeoperationtype = '"
				+ "?mEdorType?"
				+ "'"
				+ " and feefinatype in ('BF','TF','HK')"
				+ " union"
				+ " select grppolno, sum(SumActuPayMoney) money  from ljapayperson where grpcontno = '"
				+ "?mGrpContNo?"
				+ "'"
				+ " and  paytype = '"
				+ "?mEdorType?"
				+ "'"
				+ " and payno = (select payno from ljapay where othernotype = '10' and otherno = '"
				+ "?mEdorAcceptNo?"
				+ "'"
				+ " )group by grppolno) t group by grppolno" + "";
		sqlbv5.sql(strsql);
		sqlbv5.put("mEdorNo", mEdorNo);
		sqlbv5.put("mEdorType", mEdorType);
		sqlbv5.put("mGrpContNo", mGrpContNo);
		sqlbv5.put("mEdorAcceptNo", mEdorAcceptNo);
		tssrs = texesql.execSQL(sqlbv5);
		String strLine1 = "";
		String strLine2 = "";
		if (tssrs != null && tssrs.getMaxRow() > 0) {
			for (int i = 1; i <= tssrs.getMaxRow(); i++) {
				mRow++;
				strLine1 = "险种：" + tssrs.GetText(i, 1);
				strLine2 = getFeeTitle(mEdorType) + "："
						+ BqNameFun.chgMoney(tssrs.GetText(i, 2)) + "元";
				texttag.add("List" + String.valueOf(mRow) + "1", strLine1);
				texttag.add("List" + String.valueOf(mRow) + "2", strLine2);
				sumPrem += Double.parseDouble(tssrs.GetText(i, 2));
			}
			// 河南，重庆专用
			mCount++;
			// texttag.add("PayItem"+String.valueOf(mCount),
			// mEdorItem+getFeeTitle(mEdorType));
			// texttag.add("PayMoney"+String.valueOf(mCount),
			// BqNameFun.getRound(sumPrem));
		}
		// String tPayToDate = getPayToDate(mGrpContNo);
		// String tRiskName = getMainPolName(mGrpContNo);
		// String tRiskCode = getMainPolCode(mGrpContNo);//Add By QianLy 贵州专用
		// texttag.add("RiskCode",tRiskCode);//Add By QianLy 贵州专用
		// texttag.add("RiskName",tRiskName);
		//
		// tPayToDate = BqNameFun.getFDate(tPayToDate);
		// texttag.add("nextPayDate", tPayToDate);

		texttag.add("GrpContNo", mGrpContNo);
		mGrpContNo = "保单" + mGrpContNo;
		if (tInterest > 0) {
			// mRow++;
			texttag.add("List" + String.valueOf(mRow) + "3", "利息：" + mInterest
					+ "元");
			mCount++;
		}
		// if (tFee > 0){
		// mRow++;
		// texttag.add("List"+String.valueOf(mRow)+"1", "工本费："+mFee);
		// mCount++;
		// }
		chgItem = getChgItem(mEdorType);

		mName = mLCGrpContSchema.getGrpName();

		texttag.add("AgentCode", mAgentCode);
		texttag.add("AgentName", mAgentName);
		if (mOtherSign == null || !mOtherSign.trim().equals("1")) {
			// 标志为"1"的分公司会单独有地方显示业务员
			// 团体分公司暂无需求，先不考虑，待有具体格式后再做
			// mAgentCode = "业务员编号：" + mAgentCode;
			// mAgentName = "业务员姓名：" + mAgentName;
			// mRow++;
			// texttag.add("List" + String.valueOf(mRow) + "1", mAgentCode);
			// texttag.add("List" + String.valueOf(mRow) + "2", mAgentName);
		}
		String str = "系 " + mGrpContNo + "项下之" + mEdorItem + chgItem;
		texttag.add("Content", str);
		// texttag.add("ContList", mGrpContNo);//宁波发票用
		// texttag.add("mark", mGrpContNo+"项下之"+mEdorItem+chgItem);//上海,辽宁发票用

		mCurrentDate = BqNameFun.getFDate(mCurrentDate);

		texttag.add("GrpName", mName);
		texttag.add("SysDate", mCurrentDate);
		texttag.add("EdorItem", "保全项目:" + mEdorItem);// 贵州发票用
		texttag.add("CMoney", mCMoney);
		texttag.add("Money", mMoney);
		texttag.add("PayDate", BqNameFun.getFDate(mPayDate));
		texttag.add("Year", BqNameFun.getYear(mPayDate));
		texttag.add("Month", BqNameFun.getMonth(mPayDate));
		texttag.add("Day", BqNameFun.getDay(mPayDate));

		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		// tXmlExport.outputDocumentToFile("D:\\qly\\XMLDoc\\", "GrpCheque");
		// //测试用
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
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		String strsql = "select (case when (select paytodate from lcpol where polno = mainpolno and payintv<>'0' and contno = '"
				+ "?tContNo?"
				+ "') is not null then (select paytodate from lcpol where polno = mainpolno and payintv<>'0' and contno = '"
				+ "?tContNo?"
				+ "')  else ((case when (select min(paytodate) from lcpol where polno <> mainpolno and appflag='1' and contno = '"
				+ "?tContNo?"
				+ "' and ((payintv = '0' and riskcode not in(select riskcode from lmriskapp where riskperiod = 'L' and subriskflag = 'S')) or (payintv <> '0'))) is not null then (select min(paytodate) from lcpol where polno <> mainpolno and appflag='1' and contno = '"
				+ "?tContNo?"
				+ "' and ((payintv = '0' and riskcode not in(select riskcode from lmriskapp where riskperiod = 'L' and subriskflag = 'S')) or (payintv <> '0')))  else " + "to_date('?mCurrentDate?','yyyy-mm-dd')" + " end)) end) from dual";
		sqlbv6.sql(strsql);
		sqlbv6.put("tContNo", tContNo);
		sqlbv6.put("mCurrentDate", mCurrentDate);
		tssrs = texesql.execSQL(sqlbv6);
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
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			String strsql = "select (case when min(a.paytodate) is not null then min(a.paytodate)  else " + "to_date('?mCurrentDate?','yyyy-mm-dd')" + " end) from (";
			for (int i = 1; i <= ssrs.getMaxRow(); i++) {
				if (i != 1) {
					strsql += " union ";
				}
				strsql += " select (case when (select paytodate from lcpol where polno = mainpolno and payintv<>'0' and contno = '"
						+ "?contno?"
						+ "') is not null then (select paytodate from lcpol where polno = mainpolno and payintv<>'0' and contno = '"
						+ "?contno?"
						+ "')  else ((case when (select min(paytodate) from lcpol where polno <> mainpolno and appflag='1' and contno = '"
						+ "?contno?"
						+ "' and ((payintv = '0' and riskcode not in(select riskcode from lmriskapp where riskperiod = 'L' and subriskflag = 'S')) or (payintv <> '0'))) is not null then (select min(paytodate) from lcpol where polno <> mainpolno and appflag='1' and contno = '"
						+ "?contno?"
						+ "' and ((payintv = '0' and riskcode not in(select riskcode from lmriskapp where riskperiod = 'L' and subriskflag = 'S')) or (payintv <> '0')))  else null end)) end) paytodate from dual ";
				sqlbv7.put("contno", ssrs.GetText(i, 1));
			}
			strsql += ") a";
			sqlbv7.sql(strsql);
			sqlbv7.put("mCurrentDate", mCurrentDate);
			tssrs = texesql.execSQL(sqlbv7);
			if (tssrs != null && tssrs.getMaxRow() >= 1) {
				cmPayToDate = tssrs.GetText(1, 1);
			}
		}
		return cmPayToDate;
	}

	private String getMainPolName(String tContNo) {
		String tName = "";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		String strsql = "select (select riskstatname from lmrisk where riskcode = a.riskcode) from lcpol a where contno = '"
				+ "?tContNo?" + "' and polno = mainpolno";
		sqlbv8.sql(strsql);
		sqlbv8.put("tContNo", tContNo);
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		tssrs = texesql.execSQL(sqlbv8);
		if (tssrs != null && tssrs.getMaxRow() > 0) {
			tName = tssrs.GetText(1, 1);
		}
		return tName;
	}

	private String getMainPolCode(String tContNo) {
		String tName = "";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		String strsql = "select riskcode from lcpol a where contno = '"
				+ "?tContNo?" + "' and polno = mainpolno";
		sqlbv9.sql(strsql);
		sqlbv9.put("tContNo", tContNo);
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		tssrs = texesql.execSQL(sqlbv9);
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

	public static void main(String[] args) {
		logger.debug("test begin:");
		GEdorCheckPrintBL tGEdorCheckPrintBL = new GEdorCheckPrintBL();

		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86210000";
		tGlobalInput.Operator = "001";

		String tPrtSeq = "8102103180584";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);

		tVData.add(tGlobalInput);
		tVData.add(tLOPRTManagerSchema);
		if (!tGEdorCheckPrintBL.submitData(tVData, "PRINT")) {
			logger.debug("test failed");
		}
		logger.debug("test end");
	}
}
