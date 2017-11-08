package com.sinosoft.lis.f1print;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: --- 核保限额 ---
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: sinosoft<S/p>
 * 
 * @author wangyiyan
 * @version 1.0
 */
public class PEdorQuotaPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(PEdorQuotaPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private String mAgentCode = "";
	private String mBank; // 银行及储蓄所
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	private TextTag mTextTag = new TextTag();

	public PEdorQuotaPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
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
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLOPRTManagerSchema == null || mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			buildError("getInputData", "没有得到足够的信息:印刷号不能为空！");
			return false;
		}
		return true;
	}

	/**
	 * 返回处理信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
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
		cError.moduleName = "PEdorQuotaPrintBL";
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
		// 根据印刷号查询打印队列中的纪录
		XmlExportNew xmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
		String PrtNo = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号
		logger.debug("PrtNo=" + PrtNo);
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(PrtNo);
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());

		if (!tLCContDB.getInfo()) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			buildError("outputXML", "在取得LCCont的数据时发生错误");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();
		mTextTag.add("LCCont.ContNo", mLCContSchema.getContNo());
		mTextTag.add("LCCont.AppntName", mLCContSchema.getAppntName());
		mTextTag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
		if (mLCContSchema.getAppntSex() == "0") {
			mTextTag.add("LCCont.AppntSex", "男");
		}
		if (mLCContSchema.getAppntSex() == "1") {
			mTextTag.add("LCCont.AppntSex", "女");
		}
		// String tSql = null ;
		// tSql = "select sum(t.Amnt)*100,sum(t.Prem)*100 from LPPol t where
		// t.edorno='"+mLOPRTManagerSchema.getStandbyFlag1()+"' and t.PolNo!=t.MainPolNo
		// and t.ContNo = '" +
		// mLCContSchema.getContNo () + "'" ;
		// SSRS temptSSRS = new SSRS () ;
		// temptSSRS = tempExeSQL.execSQL (tSql) ;
		// mTextTag.add ("LimitMoney",temptSSRS.GetText
		// (1,1).substring(0,temptSSRS.GetText (1,1).length()-2)+"."+temptSSRS.GetText
		// (1,1).substring(temptSSRS.GetText (1,1).length()-2,temptSSRS.GetText
		// (1,1).length()));
		// temptSSRS.GetText (1,1)) ;
		// temptSSRS.GetText (1,1).substring(0,temptSSRS.GetText
		// (1,1).length()-2)+"."+temptSSRS.GetText (1,1).substring(temptSSRS.GetText
		// (1,1).length()-2,temptSSRS.GetText (1,1).length())
		mTextTag.add("Remark", mLOPRTManagerSchema.getRemark());

		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		mTextTag.add("SysDate", SysDate);

		String[] allArr = BqNameFun.getAllNames(mLCContSchema.getAgentCode());
		mTextTag.add("ManageComName", allArr[BqNameFun.CENTER_BRANCH]);

		mAgentCode = mLCContSchema.getAgentCode();
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgent的数据时发生错误");
			return false;
		}
		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息

		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
		String AgentGroup = mLAAgentSchema.getAgentGroup();
		logger.debug("AgentGroup=" + AgentGroup);
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgentBranch的数据时发生错误");
			return false;
		}

		xmlExport.createDocument("保全核保限额通知书");
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLCContDB.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			xmlExport.setUserLanguage(uLanguage);
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		String sysDate = new SimpleDateFormat("yyyy年mm月dd日").format(new Date());

		if (mLCContSchema.getSaleChnl().equals("3")) {
			LAComSchema mLAComSchema = new LAComSchema();
			LAComDB mLAComDB = new LAComDB();
			mLAComDB.setAgentCom(mLCContSchema.getAgentCom());
			// Modified By QianLy on 2006-09-13 --------
			if (mLAComDB.getInfo()) {
				mLAComSchema = mLAComDB.getSchema(); // 保存银行代码信息
				mBank = mLAComSchema.getName(); // 代理机构
				// CError tError = new CError();
				// tError.moduleName = "EdorAddChargePrintBL";
				// tError.functionName = "getBaseData";
				// tError.errorMessage = "在取得LACom的数据时发生错误!";
				// this.mErrors.addOneError(tError);
				// return false;
			} else {
				mBank = "";
			}
			// ------------

			mTextTag.add("BankNo", mBank); // 代理机构
			mTextTag.add("AgentGroup", tLABranchGroupDB.getName()); // 业务分布及业务组.
		}

		// mTextTag.add ("LABranchGroup.Name",tLABranchGroupDB.getName ()) ;
		mTextTag.add("LABranchGroup.Name", allArr[BqNameFun.SALE_SERVICE]
				+ allArr[BqNameFun.DEPART]);
		mTextTag.add("LAAgent.Name", mLAAgentSchema.getName());
		mTextTag.add("LAAgent.AgentCode", mLCContSchema.getAgentCode());
		mTextTag.add("LCCont.UWOperator", mLOPRTManagerSchema.getReqOperator());
		// texttag.add("LCSpec.SpecContent", contnt)
		String Operator = mLCContSchema.getOperator();
		logger.debug("Operator=" + Operator);
		mTextTag.add("SysDate", sysDate);
		if (mTextTag.size() > 0) {
			xmlExport.addTextTag(mTextTag);
		}

		mResult.clear();
		mResult.addElement(xmlExport);
		logger.debug("xmlexport=" + xmlExport);
		return true;
	}
}
