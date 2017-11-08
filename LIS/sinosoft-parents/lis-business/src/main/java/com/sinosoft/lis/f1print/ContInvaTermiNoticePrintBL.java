package com.sinosoft.lis.f1print;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

public class ContInvaTermiNoticePrintBL implements PrintService {
	private static Logger logger = Logger
			.getLogger(ContInvaTermiNoticePrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private XmlExportNew mXmlExport = new XmlExportNew();

	private String mZipCode; // 邮编号

	private String mPostalAddress; // 地址

	private String mAppntName; // 投保人姓名

	public ContInvaTermiNoticePrintBL() {
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
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_CONT)) {
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
		if (!tEdorItem.trim().equals(PrintManagerBL.CODE_PEdorPreInvali)) {
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
	@SuppressWarnings("unchecked")
	private boolean getBaseData() {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "LoanPayNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询保单信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mAppntName = tLCContDB.getAppntName(); // 投保人姓名
		if (mAppntName == null || mAppntName.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "LoanPayNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "投保人名字为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(tLCContDB.getContNo());
		if (!tLCAppntDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "LoanPayNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询投保人信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 查询投保人地址信息
		LCAddressDB tLCAddressDB = new LCAddressDB();
		tLCAddressDB.setCustomerNo(tLCAppntDB.getAppntNo());
		tLCAddressDB.setAddressNo(tLCAppntDB.getAddressNo());
		if (!tLCAddressDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "LoanPayNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询客户地址失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 客户邮编
		mZipCode = tLCAddressDB.getZipCode();
		mPostalAddress = tLCAddressDB.getPostalAddress();
		if (mPostalAddress == null || mPostalAddress.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "LoanPayNoticePrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "投保人地址为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("mPostalAddress:" + mPostalAddress);

		String tPrintName = "保险合同效力终止通知书";
		mXmlExport.createDocument(tPrintName);//初始化数据存储类
		PrintTool.setBarCode(mXmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLCContDB.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			mXmlExport.setUserLanguage(uLanguage);
		mXmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		TextTag tTextTag = new TextTag();
		
		tTextTag.add("ContNo", mLOPRTManagerSchema.getOtherNo());
		tTextTag.add("AppntName", mAppntName);
		tTextTag.add("ZipCode", mZipCode);
		tTextTag.add("PostAddress", mPostalAddress);
		// 将要终止日期
		// mLOPRTManagerSchema.getStandbyFlag1()保存的是终止时间提前一个月的时间，实际终止时间应该推后一个月
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("select to_char(add_months(to_date('"
				+ "?Flag1?"
				+ "','YYYY-MM-DD'),1),'YYYY-MM-DD') from dual");
		sqlbv.put("Flag1", mLOPRTManagerSchema.getStandbyFlag1());
		String InValiTermiDate = tExeSQL.getOneValue(sqlbv);
		tTextTag.add("InValiTermiDate", BqNameFun.getChDate(InValiTermiDate));
		// tTextTag.add("InValiTermiDate",
		// BqNameFun.getChDate(mLOPRTManagerSchema.getStandbyFlag1()));

		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mLOPRTManagerSchema.getManageCom().substring(0, 4));
		if (!tLDComDB.getInfo()) {
			return false;
		}
		tTextTag.add("ManageComName", tLDComDB.getName());
		tTextTag.add("PrintDate", BqNameFun.getChDate(PubFun.getCurrentDate()));

		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mLOPRTManagerSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			return false;
		}
		tTextTag.add("AgentName", tLAAgentDB.getName());
		tTextTag.add("AgentCode", tLAAgentDB.getAgentCode());
		tTextTag.add("AgentPhone",
				!"".equals(tLAAgentDB.getPhone()) ? tLAAgentDB.getPhone()
						: tLAAgentDB.getMobile());
		tTextTag.add("AccBala", mLOPRTManagerSchema.getStandbyFlag2());

		ListTable tCTListTable = new ListTable();
		tCTListTable.setName("ContTermi");
		String listString = this.mLOPRTManagerSchema.getRemark();
		String[] a = listString.split("\\$");
		for (int i = 0; i < a.length; i++) {
			String b = a[i];
			logger.debug(b);
			String[] c = b.split("\\|");
			String[] tArray = new String[c.length];
			for (int j = 0; j < c.length; j++) {
				tArray[j] = c[j];
			}
			tCTListTable.add(tArray);
		}

		mXmlExport.addTextTag(tTextTag);
		String[] temp = new String[3];
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
}
