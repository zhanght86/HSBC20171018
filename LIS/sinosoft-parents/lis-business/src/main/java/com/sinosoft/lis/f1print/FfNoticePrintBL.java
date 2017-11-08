package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 付费通知书打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：wangyan
 * @version：1.0
 * @CreateDate：2005-08-06
 */
public class FfNoticePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(FfNoticePrintBL.class);
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

	private XmlExport tXmlExport = new XmlExport();

	private LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
	private LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();

	private ListTable bfListTable = new ListTable(); // 补费清单列表
	private ListTable tfListTable = new ListTable(); // 付费清单列表
	private ListTable tjListTable = new ListTable(); // 付费小计列表
	private ListTable xjListTable = new ListTable(); // 补费小计列表

	private String mEdorAcceptNo = ""; // 保全受理号
	private String mContNo = ""; // 保单号
	private String tContNo = ""; // 保单号
	private String tEdorNo = ""; // 批单号
	private String tEdorType = ""; // 批改类型
	private String mCustomerNo = ""; // 客户号
	private String mAppName = ""; // 投保人姓名
	private String mSex = ""; // 投保人性别
	private String mInsuredName = ""; // 被保人姓名
	private String mInsuredSex = ""; // 被保人性别
	private String mNoticeNo = ""; // 通知书号
	private String mAppName1 = ""; // 客户姓名
	private String mAppDate = ""; // 申请日期
	private String mChgItem = ""; // 变更项目
	private String mGetMoney = ""; // 付费小计
	private String mMoney = ""; // 付费合计大写
	private String mSumMoney = ""; // 付费合计小写
	private String mPayMode = ""; // 付费形式
	private String mBankName = ""; // 开户行
	private String mAccount = ""; // 帐号
	private String mAccountName = ""; // 户名
	private String mOperator = ""; // 经办人
	private String mAppr = ""; // 复核人
	private String mManageComName = ""; // 机构名称
	private String mSysDate = ""; // 系统时间
	private String mManageCom = ""; // 营销服务部
	private String mBranchGroup = ""; // 营业部
	private String mAgentName = ""; // 代理人姓名
	private String mAgentCode = ""; // 代理人代码
	private String mCompanyAddress = ""; // 公司地址
	private String mCompanyPost = ""; // 公司邮编
	private String mSubCode = "";
	private String mAmnt = "";
	private double mTotal = 0;
	private String sEdorType = "";

	public static final String VTS_NAME = "FftzsP001010.vts"; // 模板名称

	public FfNoticePrintBL() {
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
			tError.moduleName = "BfNoticePrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据校验操作
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

		if (mGlobalInput == null || mLOPRTManagerSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			mErrors.addOneError(new CError("没有得到足够的信息:打印流水号不能为空！"));
			return false;
		}
		// 取打印通知书记录
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (!tLOPRTManagerDB.getInfo()) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

		String mNoType = mLOPRTManagerSchema.getOtherNoType();
		if (mNoType == null || mNoType.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);
			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_EDORACCEPT)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 取保全申请号
		mEdorAcceptNo = mLOPRTManagerSchema.getOtherNo();
		if (mEdorAcceptNo == null || mEdorAcceptNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保全受理号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 取打印通知书类型
		String tEdorItem = mLOPRTManagerSchema.getCode();
		if (tEdorItem == null || tEdorItem.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "FfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tEdorItem.trim().equals(PrintManagerBL.CODE_PEdorGetInfo)) {
			CError tError = new CError();
			tError.moduleName = "FfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "单据类型错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
		;
		if (!tLPEdorAppDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "FfNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "数据验证失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLPEdorAppSchema = new LPEdorAppSchema();
		tLPEdorAppSchema = tLPEdorAppDB.getSchema();
		return true;
	}

	// 获取数据库数据
	private boolean getBaseData() {
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String mManageCom1 = ""; // 保单管理机构
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档
		bfListTable.setName("CL"); // 补费清单
		tfListTable.setName("RE"); // 付费清单
		tjListTable.setName("ST"); // 付费小计
		xjListTable.setName("TT"); // 补费小计

		mOperator = tLPEdorAppSchema.getOperator(); // 经办人
		mAppr = tLPEdorAppSchema.getApproveOperator(); // 复核人
		// 判断付费方式
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("select codename from ldcode where codetype = 'edorgetpayform' and code = '"
				+ "?code?" + "'");
		sqlbv1.put("code", tLPEdorAppSchema.getPayForm());
		mPayMode = tExeSQL.getOneValue(sqlbv1);
		if ("4".equals(tLPEdorAppSchema.getPayForm())
				|| "7".equals(tLPEdorAppSchema.getPayForm())) {
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("select bankname from ldbank where bankcode = '"
					+ "?bankcode?" + "'");
			sqlbv2.put("bankcode", tLPEdorAppSchema.getBankCode());
			mBankName = tExeSQL.getOneValue(sqlbv2);
			mAccount = tLPEdorAppSchema.getBankAccNo();
			mAccountName = tLPEdorAppSchema.getAccName();
		}
		//
		// if ("".equals(mBankName) || mBankName == null)
		// {
		// mPayMode = "现金";
		// }
		// else
		// {
		// mPayMode = "银行划款";
		//
		// }
		// mSumMoney = String.valueOf(tLPEdorAppSchema.getGetMoney());
		// mSumMoney = tExeSQL.getOneValue("select sum(GetMoney) from lpedormain
		// where edoracceptno = '" + mEdorAcceptNo + "'");
		// mSumMoney = tExeSQL.getOneValue("select Sum(GetMoney) from ljagetdraw
		// where actugetno In ( Select actugetno From ljaget where othernotype =
		// '10' and otherno = '" + mEdorAcceptNo + "')");
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql("select Sum(SumGetMoney) from ljaget where othernotype = '10' and otherno = '"
				+ "?mEdorAcceptNo?" + "'");
		sqlbv3.put("mEdorAcceptNo", mEdorAcceptNo);
		mSumMoney = tExeSQL.getOneValue(sqlbv3);
		if ("".equals(mSumMoney)) {
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql("select (case when sum(getmoney) is not null then sum(getmoney)  else 0 end) from LJAGetEndorse where EndorsementNo In (Select EdorNo From LPEdorMain where edoracceptno = '"
					+ "?mEdorAcceptNo?" + "')");
			sqlbv4.put("mEdorAcceptNo", mEdorAcceptNo);
			String t1 = tExeSQL.getOneValue(sqlbv4);
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql("select (case when sum(sumactupaymoney) is not null then sum(sumactupaymoney)  else 0 end) from ljapayperson where payno In ( Select actugetno From ljaget where othernotype = '10' and otherno = '"
					+ "?mEdorAcceptNo?" + "')");
			sqlbv5.put("mEdorAcceptNo", mEdorAcceptNo);
			String t2 = tExeSQL.getOneValue(sqlbv5);

			mSumMoney = String.valueOf(Double.parseDouble(t1)
					+ Double.parseDouble(t2));
		}
		if (!"".equals(mSumMoney)) {
			if (Double.parseDouble(mSumMoney) < 0) {
				mSumMoney = BqNameFun.getRound(mSumMoney.substring(1, mSumMoney
						.length()));
			}
			mMoney = PubFun.getChnMoney(Double.parseDouble(mSumMoney)); // 付费合计大写
			mSumMoney = BqNameFun.getRound(mSumMoney) + "元"; // 付费合计小写
		}

		// 保单级别保全
		if (tLPEdorAppSchema.getOtherNoType().equals("3")) {
			mContNo = tLPEdorAppSchema.getOtherNo();
			LCContDB tLCContDB = new LCContDB();
			LCContSchema tLCContSchema = new LCContSchema();
			tLCContDB.setContNo(mContNo);
			if (!tLCContDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "FfNoticePrintBL";
				tError.functionName = "getBaseData";
				tError.errorMessage = "查询保单信息失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLCContSchema = tLCContDB.getSchema();
			mAppName = tLCContSchema.getAppntName(); // 投保人姓名
			mSex = tLCContSchema.getAppntSex(); // 投保人性别
			mInsuredName = tLCContSchema.getInsuredName(); // 被保人姓名
			mAgentCode = tLCContSchema.getAgentCode(); // 代理人编码
			mInsuredSex = tLCContSchema.getInsuredSex(); // 被保人姓名
			String[] allArr = BqNameFun.getAllNames(mAgentCode);
			mManageCom = allArr[BqNameFun.SALE_SERVICE]; // 营销服务部
			mAgentName = allArr[BqNameFun.AGENT_NAME]; // 营业部
			mCompanyAddress = allArr[BqNameFun.ADDRESS]; // 公司地址
			mCompanyPost = allArr[BqNameFun.ZIPCODE]; // 公司邮编
			mBranchGroup = allArr[BqNameFun.DEPART]; // 办理地址
			mManageComName = allArr[BqNameFun.COM]; // 机构名称
			tXmlExport.addDisplayControl("displayManage");
			tXmlExport.addDisplayControl("displayCustomer");

			if (mAppName == null || mAppName.trim().equals("")) {
				CError tError = new CError();
				tError.moduleName = "FfNoticePrintBL";
				tError.functionName = "getBaseData";
				tError.errorMessage = "投保人姓名为空!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (mInsuredName == null || mInsuredName.trim().equals("")) {
				CError tError = new CError();
				tError.moduleName = "FfNoticePrintBL";
				tError.functionName = "getBaseData";
				tError.errorMessage = "被保人姓名为空!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if ("".equals(mSex)) {
				mAppName1 = mAppName.trim() + "先生/女士:";
			} else if ("0".equals(mSex)) {
				mAppName1 = mAppName.trim() + "先生:";
			} else if ("1".equals(mSex)) {
				mAppName1 = mAppName.trim() + "女士:";
			}
			tXmlExport.addDisplayControl("displayCustomer");
		}
		// 客户层保全
		else if (tLPEdorAppSchema.getOtherNoType().equals("1")) {
			mCustomerNo = tLPEdorAppSchema.getOtherNo();
			LDPersonDB tLDPersonDB = new LDPersonDB();
			LDPersonSchema tLDPersonSchema = new LDPersonSchema();
			tLDPersonDB.setCustomerNo(mCustomerNo);
			if (!tLDPersonDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "FfNoticePrintBL";
				tError.functionName = "getBaseData";
				tError.errorMessage = "查询客户信息失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLDPersonSchema = tLDPersonDB.getSchema();
			mSex = tLDPersonSchema.getSex();
			mAppName = tLDPersonSchema.getName();

			if ("".equals(mSex)) {
				mAppName1 = mAppName.trim() + "先生/女士:";
			} else if ("0".equals(mSex)) {
				mAppName1 = mAppName.trim() + "先生:";
			} else if ("1".equals(mSex)) {
				mAppName1 = mAppName.trim() + "女士:";
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "BfNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "错误的OtherType类型!";
			this.mErrors.addOneError(tError);
			return false;
		}
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql("SELECT ACTUGETNO From LJAGet where OtherNoType = '10' and OtherNo = '"
				+ "?mEdorAcceptNo?" + "'");
		sqlbv6.put("mEdorAcceptNo", mEdorAcceptNo);
		mNoticeNo = tExeSQL.getOneValue(sqlbv6); // 通知书号
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql("SELECT MIN(edorAppDate) From LPEdorItem where EdorAcceptNo = '"
				+ "?mEdorAcceptNo?" + "'");
		sqlbv7.put("mEdorAcceptNo", mEdorAcceptNo);
		String mAppDate1 = tExeSQL.getOneValue(sqlbv7); // 申请日期

		/**
		 * Modified By QianLy on 2006-09-05-------------- FDate fDate = new
		 * FDate(); GregorianCalendar tCalendar = new GregorianCalendar(); int
		 * tYear = 0; int tMonth = 0; int tDay = 0; if (!"".equals(mAppDate1)) {
		 * tCalendar.setTime(fDate.getDate(mAppDate1)); tYear =
		 * tCalendar.get(Calendar.YEAR); tMonth = tCalendar.get(Calendar.MONTH) +
		 * 1; tDay = tCalendar.get(Calendar.DAY_OF_MONTH); mAppDate = tYear +
		 * "年" + tMonth + "月" + tDay + "日"; }
		 */
		mAppDate = BqNameFun.getFDate(mAppDate1);
		// ----------------

		// 获取保全信息
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(mEdorAcceptNo);
		LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.query();
		if (tLPEdorMainSet == null || tLPEdorMainSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BfNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "无保全申请数据!";
			this.mErrors.addOneError(tError);
			return false;
		} else if (tLPEdorMainSet.size() > 1) {
			mManageCom1 = tLPEdorAppSchema.getManageCom();
			if ("".equals(mManageComName)) {
				mManageComName = BqNameFun.getComM(mManageCom1);
			}
		}
		// else
		// {
		// tXmlExport.addDisplayControl("displayManage");
		// tXmlExport.addDisplayControl("displayCustomer");
		// }

		for (int i = 0; i < tLPEdorMainSet.size(); i++) {
			if (!dealOne(i)) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "BfNoticePrintBL";
				tError.functionName = "getBaseData";
				tError.errorMessage = "保单处理失败";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		if (!dealCount()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BfNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "付费小计处理失败";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 生成-年-月-日格式的日期
		StrTool tSrtTool = new StrTool();
		mSysDate = tSrtTool.getYear() + "年" + tSrtTool.getMonth() + "月"
				+ tSrtTool.getDay() + "日";
		return true;
	}

	private boolean preparePrintData() {
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		// 模版自上而下的元素
		texttag.add("AppntName", mAppName); // 投保人
		texttag.add("InsuredName", mInsuredName); // 被保人
		texttag.add("NoticeNo", mNoticeNo); // 通知书号
		texttag.add("EdorAcceptNo", mEdorAcceptNo); // 保全受理号
		texttag.add("AppntName1", mAppName1); // 客户
		texttag.add("AppDate", mAppDate); // 申请日期
		texttag.add("ChgItem", mChgItem); // 变更项目
		texttag.add("CapitalReTotalMoney", mMoney); // 付费合计大写
		texttag.add("ReTotalMoney", mSumMoney); // 付费合计小写
		texttag.add("ReType", mPayMode); // 付费形式
		if ("4".equals(tLPEdorAppSchema.getPayForm())
				|| "7".equals(tLPEdorAppSchema.getPayForm())) {
			texttag.add("BankName", mBankName); // 开户行
			texttag.add("BankAccNo", mAccount); // 帐号
			texttag.add("LDBank.AccName", mAccountName); // 户名
			tXmlExport.addDisplayControl("displayBankType");
		}
		texttag.add("LPAppnt.Operator", CodeNameQuery.getOperator(mOperator)); // 经办人
		texttag.add("LPAppnt.Appr", CodeNameQuery.getOperator(mAppr)); // 复核人
		texttag.add("ManageComName", mManageComName); // 部门
		texttag.add("SysDate", mSysDate); // 打印日期
		texttag.add("LABranchGroup.Name", mManageCom); // 营销服务部
		texttag.add("Dep", mBranchGroup); // 营业部
		texttag.add("LAAgent.Name", mAgentName); // 业务员姓名
		texttag.add("LAAgent.AgentCode", mAgentCode); // 业务员编码
		texttag.add("CompanyAddress", mCompanyAddress); // 公司地址
		texttag.add("CompanyPost", mCompanyPost); // 邮编

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
			if (tfListTable.size() != 0) {
				tXmlExport.addDisplayControl("displayTf");
				tXmlExport.addDisplayControl("displayTui");
				String[] Strbf = new String[1];
				Strbf[0] = "";
				tfListTable.add(Strbf);
				String[] tt_strEN = new String[1];
				tXmlExport.addListTable(xjListTable, tt_strEN);
				// Strbf = new String[1];
				// Strbf[0] = "补费小计：" + BqNameFun.getRound(mTotal);
				// tfListTable.add(Strbf);
				String[] tf_strEN = new String[1];
				tXmlExport.addListTable(tfListTable, tf_strEN);
			}
			if (xjListTable.size() == 0) {
				String[] Strbf = new String[1];
				Strbf[0] = "";
				bfListTable.add(Strbf);
			}
			String[] bf_strEN = new String[1];
			tXmlExport.addListTable(bfListTable, bf_strEN);
			if (tjListTable.size() != 0) {
				tXmlExport.addDisplayControl("displaySum");
				String[] tj_strEN = new String[2];
				tXmlExport.addListTable(tjListTable, tj_strEN);
			}
		}

		// tXmlExport.outputDocumentToFile("d:\\", "fftzs");
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}

	// 处理单张保单
	private boolean dealOne(int i) {
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(mEdorAcceptNo);
		LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.query();
		LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		tLPEdorMainSchema.setSchema(tLPEdorMainSet.get(i + 1));
		tEdorNo = tLPEdorMainSchema.getEdorNo();
		tContNo = tLPEdorMainSchema.getContNo();

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
		tLPEdorItemDB.setEdorNo(tEdorNo);

		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BfNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "无保全申请数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		tEdorType = tLPEdorItemSchema.getEdorType();
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql("Select edorname from lmedoritem where edorcode = '"
				+ "?tEdorType?" + "'");
		sqlbv8.put("tEdorType", tEdorType);
		mChgItem = tExeSQL.getOneValue(sqlbv8); // 变更项目
		if (tEdorType.equals("CM") || tEdorType.equals("HI")) {
			LPContDB tLPContDB = new LPContDB();
			LPContSchema tLPContSchema = new LPContSchema();
			tLPContDB.setContNo(tContNo);
			tLPContDB.setEdorNo(tEdorNo);
			tLPContDB.setEdorType(tEdorType);
			if (!tLPContDB.getInfo()) {
				LCContDB tLCContDB = new LCContDB();
				LCContSchema tLCContSchema = new LCContSchema();
				tLCContDB.setContNo(mContNo);
				if (!tLCContDB.getInfo()) {
					CError tError = new CError();
					tError.moduleName = "BfNoticePrintBL";
					tError.functionName = "getBaseData";
					tError.errorMessage = "查询保单信息失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLCContSchema = tLCContDB.getSchema();
				mAppName = tLCContSchema.getAppntName(); // 投保人姓名
				mInsuredName = tLCContSchema.getInsuredName(); // 被保人姓名
				mInsuredSex = tLCContSchema.getInsuredSex(); // 被保人性别
			} else {
				tLPContSchema = tLPContDB.getSchema();
				mAppName = tLPContSchema.getAppntName(); // 投保人姓名
				mInsuredName = tLPContSchema.getInsuredName(); // 被保人姓名
				mInsuredSex = tLPContSchema.getInsuredSex(); // 被保人性别
			}
		}

		String d = "";
		// mGetMoney = tExeSQL.getOneValue(
		// "select sum(getMoney) from lpedoritem where EdorAcceptNo = '" +
		// mEdorAcceptNo + "'" + " and ContNo = '" + tContNo + "'");
		String tGetMoney1;
		String tGetMoney2;
		if ("AG".equals(tEdorType)) {
			// Modified By QianLy on 2006-09-05------------
			// tGetMoney1 = tExeSQL.getOneValue( "Select nvl(sum(getmoney),0)
			// From ljagetdraw Where actugetno In ("
			// + "Select actugetno From ljaget where othernotype = '10' and
			// otherno = ("
			// + "Select edoracceptno from lpedoritem where edorno = '"
			// + tEdorNo + "')"
			// + " and contno = '"
			// + mContNo + "')");
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql("Select (case when sum(sumgetmoney) is not null then sum(sumgetmoney)  else 0 end) From ljaget where othernotype = '10' and otherno = ("
					+ "Select edoracceptno from lpedoritem where edorno = '"
					+ "?tEdorNo?" + "')");
			sqlbv9.put("tEdorNo", tEdorNo);
			tGetMoney1 = tExeSQL.getOneValue(sqlbv9);
			// ----------------
		} else if ("XT".equals(tEdorType)) // 协议退保单独处理
		{
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql("select (case when sum(getmoney) is not null then sum(getmoney)  else 0 end) from LJAGetEndorse where EndorsementNo = '"
					+ "?tEdorNo?"
					+ "' and FeeOperationType = '"
					+ "?tEdorType?"
					+ "' and otherno='000000' and othernotype='000'");
			sqlbv10.put("tEdorNo", tEdorNo);
			sqlbv10.put("tEdorType", tEdorType);
			tGetMoney1 = tExeSQL.getOneValue(sqlbv10);
		} else {
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql("select (case when sum(getmoney) is not null then sum(getmoney)  else 0 end) from LJAGetEndorse where EndorsementNo = '"
					+ "?tEdorNo?"
					+ "' and FeeOperationType = '"
					+ "?tEdorType?" + "'");
			sqlbv11.put("tEdorNo", tEdorNo);
			sqlbv11.put("tEdorType", tEdorType);
			tGetMoney1 = tExeSQL.getOneValue(sqlbv11);
		}
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql("select (case when sum(sumactupaymoney) is not null then sum(sumactupaymoney)  else 0 end) from ljapayperson where payno In ( Select actugetno From ljaget where othernotype = '10' and otherno = '"
				+ "?mEdorAcceptNo?" + "')");
		sqlbv12.put("mEdorAcceptNo", mEdorAcceptNo);
		tGetMoney2 = tExeSQL.getOneValue(sqlbv12);
		mGetMoney = String.valueOf(Double.parseDouble(tGetMoney1)
				+ Double.parseDouble(tGetMoney2));

		String Strbf[] = new String[1];
		// if (!("0".equals(mGetMoney)))
		if (Double.parseDouble(mGetMoney) != 0) {
			if (Double.parseDouble(mGetMoney) < 0 || "AG".equals(tEdorType)) {
				// 按顺序提取数据
				if (i != 0) {
					Strbf = new String[1];
					Strbf[0] = "";
					bfListTable.add(Strbf);
				}
				Strbf = new String[1];
				Strbf[0] = "保单号：" + tContNo;
				bfListTable.add(Strbf);
				Strbf = new String[1];
				Strbf[0] = "投保人姓名：" + mAppName + "          被保险人姓名："
						+ mInsuredName;
				bfListTable.add(Strbf);
			} else {
				// 按顺序提取数据
				Strbf = new String[1];
				Strbf[0] = "";
				tfListTable.add(Strbf);
				Strbf = new String[1];
				Strbf[0] = "保单号：" + tContNo;
				tfListTable.add(Strbf);
				Strbf = new String[1];
				Strbf[0] = "投保人姓名：" + mAppName + "          被保险人姓名："
						+ mInsuredName;
				tfListTable.add(Strbf);
			}
			// 职业类别变更
			if (tEdorType.equals("IO")) {
				d = getAmnt(BqCode.Get_Prem);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "保费付费金额：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_AddPremHealth);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "健康加费付费金额：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_AddPremOccupation);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "职业加费付费金额：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_GetDraw);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "生存保险金付费金额：" + d;
					bfListTable.add(Strbf);
				}
			}
			// 保单质押贷款
			else if (tEdorType.equals("LN")) {
				d = getAmnt(BqCode.Get_LoanCorpus);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "贷款本金：" + d;
					bfListTable.add(Strbf);
				}
				SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
				// added by liurx 2006-07-01 代扣印花税
				String stampSql = "select sum(getmoney) from LJAGetEndorse where EndorsementNo = '"
						+ "?tEdorNo?"
						+ "' and FeeOperationType = '"
						+ "?tEdorType?"
						+ "' and SubFeeOperationType = '"
						+ "?SubFeeOperationType?"
						+ "'";
				sqlbv13.sql(stampSql);
				sqlbv13.put("tEdorNo", tEdorNo);
				sqlbv13.put("tEdorType", tEdorType);
				sqlbv13.put("SubFeeOperationType", BqCode.Pay_Revenue);
				SSRS stampSSRS = new SSRS();
				stampSSRS = tExeSQL.execSQL(sqlbv13);
				if (stampSSRS != null && stampSSRS.getMaxRow() > 0) {
					Strbf = new String[1];
					Strbf[0] = "代扣印花税金额："
							+ BqNameFun.getRound(stampSSRS.GetText(1, 1));
					bfListTable.add(Strbf);
				}
			}
			// 年金、满期金给付&利差返还
			else if (tEdorType.equals("AG") || tEdorType.equals("利差返还")) {
				if (tEdorType.equals("AG")) {
					mChgItem = "年金、满期金给付";
					mSex = mInsuredSex; // 被保人性别
					if ("".equals(mSex)) {
						mAppName1 = mInsuredName.trim() + "先生/女士：";
					} else if ("0".equals(mSex)) {
						mAppName1 = mInsuredName.trim() + "先生：";
					} else if ("1".equals(mSex)) {
						mAppName1 = mInsuredName.trim() + "女士：";
					}
				}
				SSRS mSSRS = new SSRS();
				/**
				 * Modified By QianLy on 2006-09-05------- String tSql = "Select
				 * getdutycode,getmoney From ljagetdraw Where actugetno In (" +
				 * "Select actugetno From ljaget where othernotype = '10' and
				 * otherno = (" + "Select edoracceptno from lpedoritem where
				 * edorno = '" + tEdorNo + "')" + " and contno = '" + mContNo +
				 * "')";
				 */
				SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
				String tSql = " select getdutycode,getmoney from ljagetdraw where getnoticeno in("
						+ " select getnoticeno from ljaget where othernotype = '10' and otherno = ("
						+ " select edoracceptno from lpedoritem where edorno = '"
						+ "?tEdorNo?"
						+ "'))"
						+ " union"
						+ " select 'bonus',getmoney from ljabonusget where getnoticeno in("
						+ " select getnoticeno from ljaget where othernotype = '10' and otherno = ("
						+ " select edoracceptno from lpedoritem where edorno = '"
						+ "?tEdorNo?" + "'))";
				sqlbv14.sql(tSql);
				sqlbv14.put("tEdorNo", tEdorNo);
				mSSRS = tExeSQL.execSQL(sqlbv14);
				if (mSSRS == null || mSSRS.MaxRow < 1) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "BfNoticePrintBL";
					tError.functionName = "getBaseData";
					tError.errorMessage = "没有险种信息";
					this.mErrors.addOneError(tError);
					return false;
				}
				for (int j = 1; j <= mSSRS.MaxRow; j++) {
					String mGetDutyCode = mSSRS.GetText(j, 1);
					String mGetDutyName = "";
					if (mGetDutyCode.equals("bonus")) {
						mGetDutyName = "红利领取";
					} else {
						SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
						sqlbv15.sql("Select Getdutyname from lmdutyget where getdutycode = '"
								+ "?mGetDutyCode?" + "'");
						sqlbv15.put("mGetDutyCode", mGetDutyCode);
						mGetDutyName = tExeSQL.getOneValue(sqlbv15);
					}
					// ----------------------
					String mGetMoney1 = mSSRS.GetText(j, 2);
					if (!"".equals(mGetMoney1)) {
						Strbf = new String[1];
						Strbf[0] = "领取项目：" + mGetDutyName;
						bfListTable.add(Strbf);
						Strbf = new String[1];
						Strbf[0] = "领取金额：" + BqNameFun.getRound(mGetMoney1);
						bfListTable.add(Strbf);
					}
				}
			}
			// 减保、退保
			else if (tEdorType.equals("CT") || tEdorType.equals("PT")
					|| tEdorType.equals("EA") || tEdorType.equals("XT")) {
				d = getAmnt(BqCode.Get_Prem);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "保费退保金额：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_BaseCashValue);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "基本保额应退金额：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_AddPremHealth);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "健康加费应退金额：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_AddPremOccupation);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "职业加费应退金额：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_BonusCashValue);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "累计红利保额应退金额：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_FinaBonus);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "终了红利：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_MorePrem);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "多收保费退还：" + d;
					bfListTable.add(Strbf);
				}
				if (!("".equals(getAmnt(BqCode.Get_LoanCorpus))
						&& "".equals(getAmnt(BqCode.Get_LoanCorpusInterest))
						&& "".equals(getAmnt(BqCode.Get_AutoPayPrem))
						&& "".equals(getAmnt(BqCode.Get_AutoPayPremInterest))
						&& "".equals(getAmnt(BqCode.Pay_WorkNoteFee)) && ""
						.equals(getAmnt(BqCode.Pay_TBFee)))) {
					Strbf = new String[1];
					Strbf[0] = "相关扣费：";
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_LoanCorpus);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "  保单质押贷款本金：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_LoanCorpusInterest);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "  保单质押贷款利息：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_AutoPayPrem);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "  自垫本金：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_AutoPayPremInterest);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "  自垫利息：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Pay_TBFee);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "  退保手续费：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Pay_WorkNoteFee);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "  工本费：" + d;
					bfListTable.add(Strbf);
				}
			}
			// 投连部分领取
			else if (tEdorType.equals("投连部分领取")) {
				d = getAmnt(BqCode.Get_InvestAccValue);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "领取投资帐户价值：" + d;
					bfListTable.add(Strbf);
				}
			} else if (tEdorType.equals("RB")) {
				d = getAmnt(BqCode.Pay_Prem);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "补缴保费(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Pay_InsurAddPremHealth);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "补缴被保险人健康加费(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Pay_InsurAddPremOccupation);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "补缴被保险人职业加费(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Pay_AppntAddPremHealth);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "补缴投保人健康加费(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Pay_AppntAddPremOccupation);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "补缴投保人职业加费(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Pay_GetDraw);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "生存金补费(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Pay_PremInterest);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "补交保费利息(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Pay_LoanCorpus);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "贷款本金清偿(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Pay_LoanCorpusInterest);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "贷款利息清偿(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Pay_AutoPayPrem);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "自垫本金清偿(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Pay_AutoPayPremInterest);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "自垫利息清偿(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Pay_WorkNoteFee);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "工本费(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Pay_TBFee);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "退保手续费(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_Prem);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "保费付费金额(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_AddPremHealth);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "健康加费付费金额(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_AddPremOccupation);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "职业加费付费金额(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_GetDraw);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "领取金额(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_BonusCashValue);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "累计红利保额应退金额(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_BaseCashValue);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "基本保额应退金额(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_FinaBonus);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "终了红利(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_MorePrem);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "多收保费退还(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_LoanCorpus);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "保单质押贷款本金(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_LoanCorpusInterest);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "保单质押贷款利息(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_AutoPayPrem);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "自垫本金(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_AutoPayPremInterest);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "自垫利息(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_InvestAccValue);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "领取投资账户价值(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_AccBala);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "利差帐户余额(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_HasGet);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "已领取应扣除金额(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				d = getAmnt(BqCode.Get_FillBonus);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "红利补派(冲帐)：" + d;
					bfListTable.add(Strbf);
				}
				SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
				sqlbv16.sql("select abs(Sum(sumactupaymoney)) from ljapayperson where payno In ( Select actugetno From ljaget where othernotype = '10' and otherno = '"
						+ "?mEdorAcceptNo?" + "')");
				sqlbv16.put("mEdorAcceptNo", mEdorAcceptNo);
				d = tExeSQL.getOneValue(sqlbv16);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "保费(冲帐)：" + BqNameFun.getRound(d);
					bfListTable.add(Strbf);
				}
				SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
				sqlbv17.sql("select abs(Sum(GetMoney)) from ljagetdraw where actugetno In ( Select actugetno From ljaget where othernotype = '10' and otherno = '"
						+ "?mEdorAcceptNo?" + "')");
				sqlbv17.put("mEdorAcceptNo", mEdorAcceptNo);
				d = tExeSQL.getOneValue(sqlbv17);
				if (!"".equals(d)) {
					Strbf = new String[1];
					Strbf[0] = "年金、满期金(冲帐)：" + BqNameFun.getRound(d);
					bfListTable.add(Strbf);
				}
			}
			// 客户信息变更、增补健康告知(可能出现多张保单情况)
			else if (tEdorType.equals("CM") || tEdorType.equals("HI")) {
				if (Double.parseDouble(mGetMoney) > 0) {
					d = getAmnt(BqCode.Pay_Prem);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "补缴保费：" + d;
						tfListTable.add(Strbf);
						mTotal = mTotal + Double.parseDouble(d);
					}
					d = getAmnt(BqCode.Pay_InsurAddPremHealth);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "补缴被保险人健康加费：" + d;
						tfListTable.add(Strbf);
						mTotal = mTotal + Double.parseDouble(d);
					}
					d = getAmnt(BqCode.Pay_InsurAddPremOccupation);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "补缴被保险人职业加费：" + d;
						tfListTable.add(Strbf);
						mTotal = mTotal + Double.parseDouble(d);
					}
					d = getAmnt(BqCode.Pay_AppntAddPremHealth);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "补缴投保人健康加费：" + d;
						tfListTable.add(Strbf);
						mTotal = mTotal + Double.parseDouble(d);
					}
					d = getAmnt(BqCode.Pay_AppntAddPremOccupation);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "补缴投保人职业加费：" + d;
						tfListTable.add(Strbf);
						mTotal = mTotal + Double.parseDouble(d);
					}
					d = getAmnt(BqCode.Pay_GetDraw);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "生存金补费：" + d;
						tfListTable.add(Strbf);
						mTotal = mTotal + Double.parseDouble(d);
					}
					d = getAmnt(BqCode.Pay_PremInterest);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "补交利息：" + d;
						tfListTable.add(Strbf);
						mTotal = mTotal + Double.parseDouble(d);
					}
					d = getAmnt(BqCode.Get_Prem);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "保费付费金额：" + d;
						tfListTable.add(Strbf);
						mTotal = mTotal + Double.parseDouble(d);
					}
					d = getAmnt(BqCode.Get_AddPremHealth);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "健康加费付费金额：" + d;
						tfListTable.add(Strbf);
						mTotal = mTotal + Double.parseDouble(d);
					}
					d = getAmnt(BqCode.Get_AddPremOccupation);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "职业加费付费金额：" + d;
						tfListTable.add(Strbf);
						mTotal = mTotal + Double.parseDouble(d);
					}
					d = getAmnt(BqCode.Get_GetDraw);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "生存保险金付费金额：" + d;
						tfListTable.add(Strbf);
						mTotal = mTotal + Double.parseDouble(d);
					}
				} else if (Double.parseDouble(mGetMoney) < 0) {
					d = getAmnt(BqCode.Get_Prem);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "保费付费金额：" + d;
						bfListTable.add(Strbf);
					}
					d = getAmnt(BqCode.Get_AddPremHealth);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "健康加费付费金额：" + d;
						bfListTable.add(Strbf);
					}
					d = getAmnt(BqCode.Get_AddPremOccupation);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "职业加费付费金额：" + d;
						bfListTable.add(Strbf);
					}
					d = getAmnt(BqCode.Get_GetDraw);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "生存保险金付费金额：" + d;
						bfListTable.add(Strbf);
					}
					d = getAmnt(BqCode.Pay_Prem);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "补缴保费：" + d;
						bfListTable.add(Strbf);
					}
					d = getAmnt(BqCode.Pay_InsurAddPremHealth);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "补缴被保险人健康加费：" + d;
						bfListTable.add(Strbf);
					}
					d = getAmnt(BqCode.Pay_InsurAddPremOccupation);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "补缴被保险人职业加费：" + d;
						bfListTable.add(Strbf);
					}
					d = getAmnt(BqCode.Pay_AppntAddPremHealth);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "补缴投保人健康加费：" + d;
						bfListTable.add(Strbf);
					}
					d = getAmnt(BqCode.Pay_AppntAddPremOccupation);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "补缴投保人职业加费：" + d;
						bfListTable.add(Strbf);
					}
					d = getAmnt(BqCode.Pay_GetDraw);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "生存金补费：" + d;
						bfListTable.add(Strbf);
					}
					d = getAmnt(BqCode.Pay_PremInterest);
					if (!"".equals(d)) {
						Strbf = new String[1];
						Strbf[0] = "补交利息：" + d;
						bfListTable.add(Strbf);
					}
				} else {
					CError tError = new CError();
					tError.moduleName = "BfNoticePrintBL";
					tError.functionName = "getBaseData";
					tError.errorMessage = "错误的补费金额!";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
			if ("".equals(mGetMoney)) {
				mGetMoney = "0.00";
			} else {
				double s = Double.parseDouble(mGetMoney);
				if (s < 0) {
					mGetMoney = BqNameFun.getRound(mGetMoney.substring(1,
							mGetMoney.length()));
					Strbf = new String[1];
					Strbf[0] = "保单付费金额小计：" + BqNameFun.getRound(mGetMoney);
					bfListTable.add(Strbf);
				} else {
					if ("AG".equals(tEdorType)) {
						Strbf = new String[1];
						Strbf[0] = "保单付费金额小计：" + BqNameFun.getRound(mGetMoney);
						bfListTable.add(Strbf);
					}
					// else
					// {
					// Strbf = new String[1];
					// Strbf[0] = "保单补费金额小计：" + BqNameFun.getRound(mGetMoney);
					// tfListTable.add(Strbf);
					// }
				}
			}
		}
		return true;
	}

	private String getAmnt(String aSubCode) {
		mSubCode = aSubCode;
		ExeSQL tExeSQL = new ExeSQL();
		String i_sql = "";
		SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
		if ("XT".equals(tEdorType)) {
			i_sql = "select sum(getmoney) from LJAGetEndorse where EndorsementNo = '"
					+ "?tEdorNo?"
					+ "' and FeeOperationType = '"
					+ "?tEdorType?"
					+ "' and SubFeeOperationType Like concat(concat('%','"	+ "?mSubCode?"+ "'),'%') and otherno='000000' and othernotype='000'";
		} else {

			i_sql = "select sum(getmoney) from LJAGetEndorse where EndorsementNo = '"
					+ "?tEdorNo?"
					+ "' and FeeOperationType = '"
					+ "?tEdorType?"
					+ "' and SubFeeOperationType Like concat(concat('%','"
					+ "?mSubCode?"
					+ "'),'%')";
		}
		sqlbv18.sql(i_sql);
		sqlbv18.put("tEdorNo", tEdorNo);
		sqlbv18.put("tEdorType", tEdorType);
		sqlbv18.put("mSubCode", mSubCode);
		mAmnt = tExeSQL.getOneValue(sqlbv18);
		if (!"".equals(mAmnt)) {
			double s = Double.parseDouble(mAmnt);
			if (s < 0) {
				mAmnt = mAmnt.substring(1, mAmnt.length());
			}
			mAmnt = BqNameFun.getRound(mAmnt);
		}
		return mAmnt;
	}

	private boolean dealCount() {
		String[] Strtj = new String[2];
		String tSql = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		if (tEdorType.equals("RB")) {
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			tSql = "select edortype from LPEdorItem where edoracceptno= (select standbyflag1 from lpedoritem where edoracceptno = '"
					+ "?mEdorAcceptNo?" + "')";
			sqlbv20.sql(tSql);
			sqlbv20.put("mEdorAcceptNo", mEdorAcceptNo);
			sEdorType = tExeSQL.getOneValue(sqlbv20);
			SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
			tSql = "select distinct(riskcode) from LPPol where edorno in (select edorno from lpedormain Where EdorAcceptNo = (select standbyflag1 from lpedoritem where edoracceptno = '"
					+ "?mEdorAcceptNo?"
					+ "')) and contno In (Select contno From lpedoritem where edorno in (select edorno from lpedormain Where EdorAcceptNo = '"
					+ "?mEdorAcceptNo?" + "'))";
			sqlbv21.sql(tSql);
			sqlbv21.put("mEdorAcceptNo", mEdorAcceptNo);
			// XinYQ added on 2007-03-16 增加没有 LPPol 的特殊处理
			tSSRS = tExeSQL.execSQL(sqlbv21);
			
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				tSql = "select distinct RiskCode " + "from LCPol "
						+ "where 1 = 1 " + "and ContNo in " + "(select ContNo "
						+ "from LPEdorItem " + "where 1 = 1 "
						+ "and EdorAcceptNo = " + "(select StandByFlag1 "
						+ "from LPEdorItem " + "where 1 = 1 "
						+ "and EdorAcceptNo = '" + "?mEdorAcceptNo?" + "'))";
				// logger.debug(tSql);
			}
		} else {
			tSql = "select distinct(riskcode) from LCPol where contno In (Select contno From lpedoritem where edorno in (select edorno from lpedormain Where EdorAcceptNo = '"
					+ "?mEdorAcceptNo?" + "'))";
		}
		sqlbv22.sql(tSql);
		sqlbv22.put("mEdorAcceptNo", mEdorAcceptNo);
		tSSRS = tExeSQL.execSQL(sqlbv22);
		if (tSSRS == null || tSSRS.MaxRow <= 0) {
			if (!"LR".equals(sEdorType)) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "BfNoticePrintBL";
				tError.functionName = "dealCount";
				tError.errorMessage = "险种统计时出错";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		for (int j = 1; j <= tSSRS.MaxRow; j++) {
			Strtj = new String[2];
			String mRiskCode = tSSRS.GetText(j, 1);
			String mSql1 = "";
			String mSql2 = "";
			SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
			SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			if (!"AG".equals(tEdorType)) {
				tSql = "select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) from LJAGetEndorse where EndorsementNo In "
						+ "(Select EdorNo from LPedorItem where EdorAcceptNo = '"
						+ "?mEdorAcceptNo?"
						+ "') and FeeOperationType = '"
						+ "?tEdorType?"
						+ "'"
						+ " and PolNo in (Select PolNo From LCPol where ContNo in (Select contno From lpedormain Where EdorAcceptNo = '"
						+ "?mEdorAcceptNo?"
						+ "') and riskcode = '"
						+ "?mRiskCode?"
						+ "')";
				
				mSql1 = "select (case when sum(sumactupaymoney) is not null then sum(sumactupaymoney)  else 0 end) from ljapayperson where payno In ( Select actugetno From ljaget where othernotype = '10' and otherno = '"
						+ "?mEdorAcceptNo?"
						+ "') and riskcode = '"
						+ "?mRiskCode?"
						+ "'";
				sqlbv25.sql(mSql1);
				sqlbv25.put("mEdorAcceptNo", mEdorAcceptNo);
				sqlbv25.put("mRiskCode", mRiskCode);
				mSql2 = "select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) from ljagetdraw where actugetno In ( Select actugetno From ljaget where othernotype = '10' and otherno = '"
						+ "?mEdorAcceptNo?"
						+ "') and riskcode = '"
						+ "?mRiskCode?"
						+ "'";
				sqlbv26.sql(mSql2);
				sqlbv26.put("mEdorAcceptNo", mEdorAcceptNo);
				sqlbv26.put("mRiskCode", mRiskCode);
			} else {
				/**
				 * Modified By QianLy on 2006-09-05------- tSql = "select
				 * nvl(Sum(GetMoney),0) from ljagetdraw where actugetno In (
				 * Select actugetno From ljaget where othernotype = '10' and
				 * otherno = '" + mEdorAcceptNo + "') and polno in (select polno
				 * from lcpol where riskcode = '" + mRiskCode + "')";
				 */
				tSql = "select (case when sum(a.money) is not null then sum(a.money)  else 0 end)"
						+ " from (select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) money"
						+ "       from ljagetdraw"
						+ "       where actugetno In"
						+ "              (Select actugetno"
						+ "                 From ljaget"
						+ "                 where othernotype = '10'"
						+ "                  and otherno = '"
						+ "?mEdorAcceptNo?"
						+ "')"
						+ "        and polno in"
						+ "              (select polno from lcpol where riskcode = '"
						+ "?mRiskCode?"
						+ "')"
						+ "       union"
						+ "       select (case when sum(GetMoney) is not null then sum(GetMoney)  else 0 end) money"
						+ "       from ljabonusget"
						+ "       where getnoticeno in"
						+ "              (Select getnoticeno"
						+ "                 From ljaget"
						+ "                 where othernotype = '10'"
						+ "                  and otherno = '"
						+ "?mEdorAcceptNo?"
						+ "')"
						+ "        and otherno in"
						+ "               (select polno from lcpol where riskcode = '"
						+ "?mRiskCode?" + "')) a" + "";
			}
			sqlbv24.sql(tSql);
			sqlbv24.put("mEdorAcceptNo", mEdorAcceptNo);
			sqlbv24.put("tEdorType", tEdorType);
			sqlbv24.put("mRiskCode", mRiskCode);
			String mFee = tExeSQL.getOneValue(sqlbv24);
			String tFee1;
			String tFee2;
			if (tEdorType.equals("RB")) {
				tFee1 = tExeSQL.getOneValue(sqlbv25);
				tFee2 = tExeSQL.getOneValue(sqlbv26);
				mFee = String.valueOf(Double.parseDouble(tFee1)
						+ Double.parseDouble(tFee2) + Double.parseDouble(mFee));
			}
			if (Double.parseDouble(mFee) != 0) {
				if (Double.parseDouble(mFee) < 0 || "AG".equals(tEdorType)) {
					if (Double.parseDouble(mFee) < 0) {
						mFee = mFee.substring(1, mFee.length());
					}
					SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
					sqlbv28.sql("select riskshortname from lmrisk where riskcode = '"
							+ "?mRiskCode?" + "'");
					sqlbv28.put("mRiskCode", mRiskCode);
					String mRiskName = tExeSQL.getOneValue(sqlbv28);
					Strtj[0] = "险种：" + mRiskName;
					Strtj[1] = "付费金额:" + BqNameFun.getRound(mFee);
					tjListTable.add(Strtj);
				} else {
					SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
					sqlbv29.sql("select riskshortname from lmrisk where riskcode = '"
							+ "?mRiskCode?" + "'");
					sqlbv29.put("mRiskCode", mRiskCode);
					String mRiskName = tExeSQL.getOneValue(sqlbv29);
					Strtj[0] = "险种：" + mRiskName;
					Strtj[1] = "补费金额:" + BqNameFun.getRound(mFee);
					xjListTable.add(Strtj);
				}
			}
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
