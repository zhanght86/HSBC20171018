package com.sinosoft.lis.f1print;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 领取通知书 (代生存调查通知书)打印类
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
 * @CreateDate：2005-08-05
 */
public class AutoPayAGNoticePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(AutoPayAGNoticePrintBL.class);
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

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private String mPolNo; // 保单险种号
	private String mContNo; // 合同号
	private String mInsuredNo; // 被保人号
	private String mName; // 被保人姓名
	private String mBankNameList = "";
	private String mGetMoney;
	private String mAddress;
	private String mZipCode;
	private String mAppntName;
	private String mMakeDate;
	private String mAgentCode; // 业务员编码
	private String mCode = ""; // 通知书类型

	public static final String VTS_NAME = "LqtzsDscdcP001600.vts"; // 模板名称
	public static final String VTS_NAME_1 = "LqtzsDscdcP009040.vts"; // 609险种模板名称

	public AutoPayAGNoticePrintBL() {
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
			tError.moduleName = "AutoPayAGNoticePrintBL";
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
		// liuzhao 2008-08-21
		TransferData tTransferData = (TransferData) mInputData
				.getObjectByObjectName("transferData", 0);
		mCode = (String) tTransferData.getValueByName("NoticeType");
		if (mCode == null || mCode.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoPayAGNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取通知书类型编码失败！";
			this.mErrors.addOneError(tError);

			return false;
		}
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
			tError.moduleName = "AutoPayAGNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);

			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_INDPOL)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoPayAGNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tNoticeType = mLOPRTManagerSchema.getCode();
		if (tNoticeType == null || tNoticeType.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "AutoPayAGNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tNoticeType.trim().equals(PrintManagerBL.CODE_PEdorAutoPayAG)) {
			CError tError = new CError();
			tError.moduleName = "AutoPayAGNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "单据类型错误：只能是领取通知书！";
			this.mErrors.addOneError(tError);
			return false;
		}

		mPolNo = mLOPRTManagerSchema.getOtherNo();

		if (mPolNo == null || mPolNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoPayAGNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保单险种号失败！";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mPolNo);
		if (!tLCPolDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "AutoPayAGNoticePrintBL";
			tError.functionName = "checkdata";
			tError.errorMessage = "无保单数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCPolSchema = tLCPolDB.getSchema();
		mContNo = mLCPolSchema.getContNo();
		if (mContNo == null || mContNo.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "AutoPayAGNoticePrintBL";
			tError.functionName = "checkdata";
			tError.errorMessage = "合同号为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInsuredNo = mLCPolSchema.getInsuredNo();
		if (mInsuredNo == null || mInsuredNo.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "AutoPayAGNoticePrintBL";
			tError.functionName = "checkdata";
			tError.errorMessage = "被保人客户号为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	private boolean getBaseData() {
		// 查询被保人信息
		// 查询投保人信息
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		tLCInsuredDB.setContNo(mContNo);
		tLCInsuredDB.setInsuredNo(mInsuredNo);
		if (!tLCInsuredDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "AutoPayPreEndNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询被保人信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCInsuredSchema = tLCInsuredDB.getSchema();
		mName = tLCInsuredSchema.getName(); // 被保人姓名
		if (mName == null || mName.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "AutoPayAGNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "被保人名字为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String tSex = "";
		tSex = tLCInsuredSchema.getSex();
		if (tSex.trim().equals("0")) {
			mName = mName.trim() + "先生";
		} else if (tSex.trim().equals("1")) {
			mName = mName.trim() + "女士";
		} else {
			mName = mName.trim() + "女士/先生";
		}

		// 查询投保人信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntDB.setContNo(mContNo);
		if (!tLCAppntDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "BfzdNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "查询保单信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCAppntSchema = tLCAppntDB.getSchema();
		String tAddressNo = tLCAppntSchema.getAddressNo(); // 投保人地址号
		String tCustomerNo = tLCAppntSchema.getAppntNo(); // 投保人客户号
		mAppntName = tLCAppntSchema.getAppntName();
		// 查询投保人地址信息
		LCAddressDB tLCAddressDB = new LCAddressDB();
		LCAddressSchema tLCAddressSchema = new LCAddressSchema();
		tLCAddressDB.setCustomerNo(tCustomerNo);
		tLCAddressDB.setAddressNo(tAddressNo);
		if (!tLCAddressDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "AutoPayAGNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "查询客户地址失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCAddressSchema = tLCAddressDB.getSchema();
		mZipCode = tLCAddressSchema.getZipCode(); // 客户邮编
		CodeNameQuery tCodeNameQuery = new CodeNameQuery();
		mAddress = tCodeNameQuery.getDistrict(tLCAddressSchema.getCounty())
				+ tLCAddressSchema.getPostalAddress(); // 客户地址

		mAgentCode = mLCPolSchema.getAgentCode();
		mMakeDate = mLOPRTManagerSchema.getMakeDate();
		mMakeDate = BqNameFun.getFDate(mMakeDate);

		return true;
	}

	private boolean preparePrintData() {
		XmlExport tXmlExport = new XmlExport();
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		// liuzhao Modify 2007-08-21 609需求2201
		if (mCode.trim().equals("BQ18")) {
			tXmlExport.createDocument(VTS_NAME_1, "printer"); // 初始化xml文档
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			// 以下判断显示项目
			String tSql = "select rnewflag,prem from lcpol where polno = '"
					+ "?polno?" + "'";
			sqlbv.sql(tSql);
			sqlbv.put("polno", mLOPRTManagerSchema.getOtherNo());
			SSRS tRs = tExeSQL.execSQL(sqlbv);
			String tRnewFlag = tRs.GetText(1, 1);
			String tPrem = tRs.GetText(1, 2);
			if (tRnewFlag != null && !tRnewFlag.equals("")) {
				if (tRnewFlag.equals("-1")) {
					tXmlExport.addDisplayControl("displayType1");
				}
			}

			tSql = "select Bonusflag from lmriskapp where riskcode in(select riskcode from lcpol where polno = '"
					+ "?polno?" + "')";
			sqlbv.sql(tSql);
			sqlbv.put("polno", mLOPRTManagerSchema.getOtherNo());
			String tBonusflag = tExeSQL.getOneValue(sqlbv);
			if (tBonusflag != null && !tBonusflag.equals("")) {
				if (tBonusflag.equals("1") || tBonusflag.equals("2")) {
					TextTag mexttag = new TextTag();
					mexttag.add("Prem", tPrem);
					tXmlExport.addTextTag(mexttag);
					tXmlExport.addDisplayControl("displayType2");
				}
			}

		} else {
			tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档
		}

		String tNoticeNo = mLOPRTManagerSchema.getStandbyFlag2();
		if (tNoticeNo == null || tNoticeNo.trim().equals("")) {
			CError.buildErr(this, "领取通知号为空！");
			return false;
		}
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String strsql = "select contno,(select riskshortname from lmrisk b where b.riskcode = a.riskcode),"
				+ "(select getdutyname from lmdutyget c where c.getdutycode = a.getdutycode),"
				+ "getdate,getmoney from ljsgetdraw a where "
				+ "a.getnoticeno = '" + "?tNoticeNo?" + "'";
		sqlbv1.sql(strsql);
		sqlbv1.put("tNoticeNo", tNoticeNo);
		tssrs = texesql.execSQL(sqlbv1);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "给付表中无应付数据！");
			return false;
		}
		String[] tContListTitle = new String[5];
		tContListTitle[0] = "PolNo";
		tContListTitle[1] = "RiskName";
		tContListTitle[2] = "GetDutyName";
		tContListTitle[3] = "GetDate";
		tContListTitle[4] = "GetStd";
		ListTable tContListTable = new ListTable();
		tContListTable.setName("RISK");
		String strLine[] = null;
		double tGetMoney = 0.0;
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[5];
			strLine[0] = tssrs.GetText(i, 1);
			strLine[1] = tssrs.GetText(i, 2);
			strLine[2] = tssrs.GetText(i, 3);
			strLine[3] = BqNameFun.getFDate(tssrs.GetText(i, 4));
			strLine[4] = BqNameFun.getRound(tssrs.GetText(i, 5)) + "元";
			tGetMoney += Double.parseDouble(tssrs.GetText(i, 5));
			tContListTable.add(strLine);
		}
		mGetMoney = BqNameFun.getRound(tGetMoney) + "元";

		// 模版自上而下的元素
		String[] allArr = BqNameFun.getAllNames(mAgentCode);

		// //查询银行列表
		// strsql = "select trim(bankname) from ldbank "
		// +" where comcode = '"+BqNameFun.getFilialCode(mAgentCode)+"'";
		// tssrs = texesql.execSQL(strsql);
		// if(tssrs!=null && tssrs.getMaxRow()>0)
		// {
		// for(int iBank = 1;iBank<=tssrs.getMaxRow();iBank++)
		// {
		// if(iBank == tssrs.getMaxRow())
		// {
		// mBankNameList += tssrs.GetText(iBank,1)+"的";
		// }
		// else
		// {
		// mBankNameList += tssrs.GetText(iBank,1)+"或";
		// }
		// }
		// }
		texttag.add("AppntName", mAppntName);
		texttag.add("AgentCode", mAgentCode);
		texttag.add("District", allArr[BqNameFun.DISTRICT]);
		texttag.add("Depart", allArr[BqNameFun.DEPART]);
		texttag.add("Team", allArr[BqNameFun.TEAM]);
		texttag.add("AgentName", allArr[BqNameFun.AGENT_NAME]);
		texttag.add("ManageComName", allArr[BqNameFun.COM]);
		texttag.add("SaleService", allArr[BqNameFun.SALE_SERVICE]);
		texttag.add("CompanyAddress", allArr[BqNameFun.ADDRESS]);
		texttag.add("CompanyPost", allArr[BqNameFun.ZIPCODE]);
		texttag.add("MakeDate", mMakeDate);

		texttag.add("Name", mName);
		// texttag.add("BankNameList", mBankNameList);
		texttag.add("GetMoney", mGetMoney);
		texttag.add("Address", mAddress);
		texttag.add("Post", mZipCode);
		texttag.add("Filial", allArr[BqNameFun.FILIAL]);
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, tContListTitle);

		// tXmlExport.outputDocumentToFile("d:\\", "P001600"); //测试用
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;

	}

	/**
	 * 得到通过机构代码得到机构名称
	 * 
	 * @param strComCode
	 * @return
	 * @throws Exception
	 */
	private String getComName(String strComCode) {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strComCode);
		tLDCodeDB.setCodeType("station");

		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			return "";
		}
		return tLDCodeDB.getCodeName();
	}

	// 得到年、月、日格式的日期
	private String getFDate(String mDate) {
		String mFDate = "";
		if (mDate != null && !mDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(mDate));
			int tYear = tCalendar.get(Calendar.YEAR);
			int tMonth = tCalendar.get(Calendar.MONTH) + 1;
			int tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
			mFDate = String.valueOf(tYear) + "年" + String.valueOf(tMonth) + "月"
					+ String.valueOf(tDay) + "日";
		}
		return mFDate;
	}

	// 返回tDate n年后的对应日，n为负值时为n年前的对应日
	private String calDate(String tDate, int n) {
		String coDate = "";
		if (tDate != null && !tDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(tDate));
			int tYear = tCalendar.get(Calendar.YEAR);
			int tMonth = tCalendar.get(Calendar.MONTH) + 1;
			int tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
			tYear = tYear + n;
			// 如果tDate是2月29日，而coDate不是闰年
			if (tMonth == 2 && tDay == 29 && !isLeap(tYear)) {
				tMonth = 3;
				tDay = 1;
			}
			coDate = String.valueOf(tYear) + "-" + String.valueOf(tMonth) + "-"
					+ String.valueOf(tDay);
		}
		return coDate;
	}

	private boolean isLeap(int tYear) {
		boolean returnFlag = (tYear % 4) == 0 ? ((tYear % 100) == 0 ? ((tYear % 400) == 0 ? true
				: false)
				: true)
				: false;

		return returnFlag;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
