package com.sinosoft.lis.f1print;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
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
public class BQProgressPrint implements PrintService {
	private static Logger logger = Logger.getLogger(BQProgressPrint.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	@SuppressWarnings("unused")
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private XmlExportNew tXmlExport = new XmlExportNew();

	public BQProgressPrint() {
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
			tError.moduleName = "GBfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);
			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_CONT)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GBfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 个人保单号
		String tContNo = mLOPRTManagerSchema.getOtherNo();
		if (tContNo == null || tContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GBfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保全受理号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 取打印通知书类型
		String tCode = mLOPRTManagerSchema.getCode();
		if (tCode == null || tCode.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "GBfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tCode.trim().equals(PrintManagerBL.CODE_PEdorRemind)) {
			CError tError = new CError();
			tError.moduleName = "GBfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "单据类型错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 获取数据库数据
	@SuppressWarnings("unchecked")
	private boolean getBaseData() {
		String contNo = mLOPRTManagerSchema.getOtherNo();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(contNo);
		tLCContDB.getInfo();
		tXmlExport.createDocument("保全办理进度通知书"); // 初始化xml文档
		String uLanguage = PrintTool.getCustomerLanguage(tLCContDB.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) {
			tXmlExport.setUserLanguage(uLanguage);
		}
		tXmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));
		
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String tSQL = "select agentcode from lccont where contno = '"
				+ "?contno?" + "'";
		sqlbv.sql(tSQL);
		sqlbv.put("contno", mLOPRTManagerSchema.getOtherNo());
		String tAgentCode = tExeSQL.getOneValue(sqlbv);
		tSQL = "select name from laagent where agentcode = '" + "?tAgentCode?"
				+ "'";
		sqlbv.sql(tSQL);
		sqlbv.put("tAgentCode", tAgentCode);
		String tAgetnName = tExeSQL.getOneValue(sqlbv);

		// 模版自上而下的元素
		tTextTag.add("PrintNo", mLOPRTManagerSchema.getPrtSeq());// 通知书号
		tTextTag.add("AppntName", mLOPRTManagerSchema.getStandbyFlag4()); // 申请资格人姓名
		tTextTag.add("ContNo", contNo); // 保单号
		tTextTag.add("EdorAppDate", changeDate(mLOPRTManagerSchema
				.getStandbyFlag7())); // 保全受理日期
		tTextTag.add("EdorType", mLOPRTManagerSchema.getStandbyFlag5()); // 保全项目名称
		tTextTag.add("EdorState", mLOPRTManagerSchema.getStandbyFlag6()); // 保全状态名称
		tTextTag.add("ManageCom", BqNameFun.getComName(mLOPRTManagerSchema
				.getManageCom().substring(0, 4))); // 分公司名称
		tTextTag.add("Today", changeDate(PubFun.getCurrentDate()));
		tTextTag.add("Operator", tAgetnName); // 经办人姓名
		tTextTag.add("OperatorCode", tAgentCode); // 经办人CODE

		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}
		mResult.clear();
		mResult.addElement(tXmlExport);
		return true;
	}

	private String changeDate(String tDate) {
		String tRet = "";
		FDate fDate = new FDate();
		GregorianCalendar tCalendar = new GregorianCalendar();
		int tYear = 0;
		int tMonth = 0;
		int tDay = 0;
		if (!"".equals(tDate)) {
			tCalendar.setTime(fDate.getDate(tDate));
			tYear = tCalendar.get(Calendar.YEAR);
			tMonth = tCalendar.get(Calendar.MONTH) + 1;
			tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
			tRet = tYear + "年" + tMonth + "月" + tDay + "日";
		}
		return tRet;
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
		String t = "86110000";
		logger.debug(t.substring(0, 4));
	} // function main end
}
