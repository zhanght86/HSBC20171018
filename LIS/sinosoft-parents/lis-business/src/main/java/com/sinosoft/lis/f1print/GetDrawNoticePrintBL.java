package com.sinosoft.lis.f1print;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 补费通知书打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：wangyan,pst
 * @version：1.0 2.0
 * @CreateDate：2005-07-30, 2009-02-03
 */
public class GetDrawNoticePrintBL implements PrintService {
	private static Logger logger = Logger.getLogger(GetDrawNoticePrintBL.class);

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

	private XmlExportNew mXmlExport = new XmlExportNew();

	private LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();

	private String mEdorAcceptNo = ""; // 保全受理号

	private String mContNo = ""; // 保单号

	private String tContNo = ""; // 保单号

	private String tEdorNo = ""; // 批单号

	private String tEdorType = ""; // 批改类型

	private String mCustomerNo = ""; // 客户号

	private String mAppName = ""; // 投保人姓名

	private String mSex = ""; // 投保人性别

	private String mInsuredName = ""; // 被保人姓名

	private String mNoticeNo = ""; // 通知书号

	private String mAppName1 = ""; // 客户姓名

	private String mAppDate = ""; // 申请日期

	private String mEndDate = ""; // 补费止期

	private String mChgItem = ""; // 变更项目

	private String mGetMoney = ""; // 补费小计

	private String mMoney = ""; // 补费合计大写

	private String mSumMoney = ""; // 补费合计小写

	private String mPayMode = ""; // 补费形式

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

	public GetDrawNoticePrintBL() {
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

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
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

		return true;
	}

	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) mInputData
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
			tError.moduleName = "BfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);
			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_INDPOL)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 取打印通知书类型
		String tEdorItem = mLOPRTManagerSchema.getCode();
		if (tEdorItem == null || tEdorItem.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "BfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tEdorItem.trim().equals(PrintManagerBL.CODE_PEdorAutoPayAG)) {
			CError tError = new CError();
			tError.moduleName = "BfNoticePrintBL";
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

		return true;
	}

	// 获取数据库数据
	private boolean getBaseData() {

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		if (!tLCContDB.getInfo()) {
			return false;
		}
		String tPrintName = "生存保险金/年金/满期金领取通知书";
		mXmlExport.createDocument(tPrintName);//初始化数据存储类
		PrintTool.setBarCode(mXmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLCContDB.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			mXmlExport.setUserLanguage(uLanguage);
		mXmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		TextTag tTextTag = new TextTag();
		tTextTag.add("ZipCode", this.mLOPRTManagerSchema.getStandbyFlag3());
		tTextTag.add("Address", this.mLOPRTManagerSchema.getStandbyFlag4());
		tTextTag.add("BnfName", this.mLOPRTManagerSchema.getStandbyFlag2());
		tTextTag.add("ContNo", mLOPRTManagerSchema.getOtherNo());
		tTextTag.add("RiskName", mLOPRTManagerSchema.getStandbyFlag5());
		tTextTag.add("GetDate", mLOPRTManagerSchema.getStandbyFlag6());
		tTextTag.add("GetNoiceNo", mLOPRTManagerSchema.getPrtSeq());
		tTextTag.add("CValiDate", tLCContDB.getCValiDate());
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mLOPRTManagerSchema.getManageCom().substring(0, 4));
		if (!tLDComDB.getInfo()) {
			return false;
		}
		tTextTag.add("ManageCom", tLDComDB.getName());
		tTextTag.add("PrintDate", PubFun.getCurrentDate());
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mLOPRTManagerSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			return false;
		}
		tTextTag.add("AgentName", tLAAgentDB.getName());
		tTextTag.add("AgentCode", tLAAgentDB.getAgentCode());
		tTextTag.add("Phone", "".equals(tLAAgentDB.getPhone()) ? tLAAgentDB
				.getPhone() : tLAAgentDB.getMobile());

		ListTable tCTListTable = new ListTable();
		tCTListTable.setName("Get");
		String listString = this.mLOPRTManagerSchema.getRemark();
		String[] a = listString.split("\\$");
		for (int i = 0; i < a.length; i++) {
			String b = a[i];
			logger.debug(b);
			String[] c = b.split("\\|");
			for (int j = 0; j < c.length; j++) {
				logger.debug(c[j]);
			}
			tCTListTable.add(c);
		}
		mXmlExport.addTextTag(tTextTag);
		String[] temp = new String[5];
		mXmlExport.addListTable(tCTListTable, temp);
		this.mResult.add(mXmlExport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 测试主程序业务方法
	 * 
	 * @param String[]
	 */
	public static void main(String[] args) {
		String string1 = "年金|王风莲|1.0|500.0|年领$";
		String[] a = string1.split("\\$");
		for (int i = 0; i < a.length; i++) {
			String b = a[i];
			logger.debug(b);
			String[] c = b.split("\\|");
			for (int j = 0; j < c.length; j++) {
				logger.debug(c[j]);
			}
		}
	} // function main end
}
