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
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * @author Administrator add by jiaqiangli 2009-03-07 保险合同效力中止通知书
 */
public class ContInvaNoticePrintBL implements PrintService {
	private static Logger logger = Logger
			.getLogger(ContInvaNoticePrintBL.class);

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

	public ContInvaNoticePrintBL() {
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
		if (!tEdorItem.trim().equals(PrintManagerBL.CODE_PEdorContInvalid)) {
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

		String printName = "保险合同效力中止通知书";
		mXmlExport.createDocument(printName);//初始化数据存储类
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
		tTextTag.add("InValiTermiDate", mLOPRTManagerSchema.getStandbyFlag1());

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

		// 长期险失效短期险终止日期
		tTextTag.add("InValiDate", mLOPRTManagerSchema.getStandbyFlag1());

		// bqcode 失效原因
		String tInvaReason = "";
		if (mLOPRTManagerSchema.getStandbyFlag4().equals("01"))
			tInvaReason = "保险费逾期未交付";
		else if (mLOPRTManagerSchema.getStandbyFlag4().equals("02"))
			tInvaReason = "现金价值净额不足以垫交保费(宽限期内)";
		else if (mLOPRTManagerSchema.getStandbyFlag4().equals("03"))
			tInvaReason = "现金价值净额不足以垫交保费(宽限期外)";
		else if (mLOPRTManagerSchema.getStandbyFlag4().equals("04"))
			tInvaReason = "保单贷款本息等于现金价值净额";
		tTextTag.add("InvaReason", tInvaReason);

		// 特殊处理短期险终止
		String tTermiRisk = "";
		String tOtherNotice = "";
		// ExeSQL tExeSQL = new ExeSQL();
		// String tInvaTermiSQL = "select (select riskname from lcpol
		// b,lmriskapp c where b.contno=a.contno and b.polno=a.polno and
		// b.riskcode=c.riskcode) from lccontstate a where
		// contno='"+mLOPRTManagerSchema.getOtherNo()+"' "
		// + "and startdate = '"+mLOPRTManagerSchema.getStandbyFlag1()+"' and
		// statetype='Terminate' and statereason='07' and enddate is null ";
		// SSRS tSSRS = tExeSQL.execSQL(tInvaTermiSQL);
		// if (tSSRS.getMaxRow() > 0) {
		if (mLOPRTManagerSchema.getRemark() != null
				&& !mLOPRTManagerSchema.getRemark().equals("")) {
			tTermiRisk = mLOPRTManagerSchema.getRemark();
			// for (int i=1;i<=tSSRS.getMaxRow();i++) {
			// if (i==1)
			// tTermiRisk += tSSRS.GetText(i, 1);
			// else
			// tTermiRisk += "、"+tSSRS.GetText(i, 1);
			// }
			// 主险失效附加险终止的 两个startdate现在置的不一样 主险是paytodate+67+1 附加险是paytodate+1
			tOtherNotice = "我们需提醒您注意的是，该合同项下的" + tTermiRisk + "依据合同约定，于"
					+ mLOPRTManagerSchema.getStandbyFlag1() + "起效力终止，不可申请恢复。";
			tTextTag.add("OtherNotice", tOtherNotice);
		}

		mXmlExport.addTextTag(tTextTag);
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
