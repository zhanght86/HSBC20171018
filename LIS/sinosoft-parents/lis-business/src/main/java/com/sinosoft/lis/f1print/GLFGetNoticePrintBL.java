package com.sinosoft.lis.f1print;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 生存领取清单打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：sunsx
 * @version：1.0 2.0
 * @CreateDate： 2009-04-16
 */
public class GLFGetNoticePrintBL implements PrintService {
	private static Logger logger = Logger.getLogger(GLFGetNoticePrintBL.class);

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

	private XmlExport tXmlExport = new XmlExport();

	private String mGrpContNo = ""; // 团体保单号

	private String mSerialNo = "";// 流水号

	private String mAppName = "";// 申请人

	public static final String VTS_NAME = "GLFGetNotice0416.vts"; // 模板名称

	public GLFGetNoticePrintBL() {
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

		// 准备需要打印的数据
		if (!preparePrintData()) {
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
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_GRPPOL)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GBfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 取团体号
		mGrpContNo = mLOPRTManagerSchema.getOtherNo();

		if (mGrpContNo == null || mGrpContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GBfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取团体保单号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		mSerialNo = mLOPRTManagerSchema.getStandbyFlag1();

		if (mSerialNo == null || mSerialNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GBfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取获水号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		mAppName = mLOPRTManagerSchema.getStandbyFlag2();
		if (mAppName == null || mAppName.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GBfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取获水号失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 取打印通知书类型
		String tEdorItem = mLOPRTManagerSchema.getCode();
		if (tEdorItem == null || tEdorItem.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "GBfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tEdorItem.trim().equals(PrintManagerBL.CODE_GEdorGetNotice)) {
			CError tError = new CError();
			tError.moduleName = "GBfNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "单据类型错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean preparePrintData() {
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档
		tXmlExport.addDisplayControl("displayHead");
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		tLCGrpContDB.setGrpContNo(mGrpContNo);
		if (!tLCGrpContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCGrpContSchema = tLCGrpContDB.getSchema();
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(mGrpContNo);
		LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
		if (tLCGrpPolSet == null || tLCGrpPolSet.size() < 1) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		String tRiskCode = tLCGrpPolSet.get(1).getRiskCode();
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(tRiskCode);
		if (!tLMRiskDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		LMRiskSchema tLMRiskSchema = tLMRiskDB.getSchema();

		ListTable tLFListTable = new ListTable();
		tLFListTable.setName("LF");
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String tSql = " select a.contno,b.name,(case a.getdutykind when '1' then '趸领' when '2' then '年领' when '3' then '月领' when '4' then '年领' when '5' then '月领' when '6' then '年领' when '7' then '月领' when '8' then '年领' when '9' then '月领' else '其它' end),"
				+ " concat(concat('第' , (select count(distinct GetNoticeNo)+1 from ljagetdraw where getdate < a.getdate and contno = a.contno and insuredno = a.insuredno)) , '期'),a.getdate,"
				+ " concat(sum(a.getmoney),'元'),(case c.paymode when '1' then '现金' when '4' then '银行转账' else '其它' end),"
				+ " (case when (select bankname from ldbank where bankcode = c.bankcode) is not null then (select bankname from ldbank where bankcode = c.bankcode)  else '' end),(case when c.accname is not null then c.accname  else '' end),(case when c.bankaccno is not null then c.bankaccno  else '' end) "
				+ " from ljagetdraw a, lcinsured b, ljaget c"
				+ " where a.grpcontno = b.grpcontno "
				+ " and a.contno = b.contno "
				+ " and a.insuredno = b.insuredno "
				+ " and a.serialno = c.serialno"
				+ " and a.actugetno = c.actugetno"
				+ " and a.grpcontno = '"
				+ "?mGrpContNo?"
				+ "'"
				+ " and a.serialno = '"
				+ "?mSerialNo?"
				+ "'"
				+ " group by a.contno,b.name,a.getdutykind,a.insuredno,a.getdate,c.paymode,c.bankcode,c.bankaccno,c.accname order by a.ContNo";
		sqlbv1.sql(tSql);
		sqlbv1.put("mGrpContNo", mGrpContNo);
		sqlbv1.put("mSerialNo", mSerialNo);
		SSRS rSSRS = new SSRS();
		ExeSQL rExeSQL = new ExeSQL();
		rSSRS = rExeSQL.execSQL(sqlbv1);
		if (rSSRS == null || rSSRS.MaxRow < 1) {
			CError.buildErr(this, "查询本次领取记录失败");
			return false;
		}
		int tArrLen = rSSRS.MaxRow;
		for (int i = 1; i <= tArrLen; i++) {
			String[] strLF = new String[10];
			for (int k = 1; k <= strLF.length; k++) {
				strLF[k - 1] = rSSRS.GetText(i, k);
			}
			tLFListTable.add(strLF);
		}
		// 模版自上而下的元素
		texttag.add("GrpName", tLCGrpContSchema.getGrpName());// 投保人姓名

		texttag.add("GrpContNo", mGrpContNo); // 合同号

		texttag.add("AppName", mAppName); // 申请人姓名
		texttag.add("RiskName", tLMRiskSchema.getRiskName()); // 变更项目
		String[] b_strLF = new String[10];
		tXmlExport.addListTable(tLFListTable, b_strLF);
		tXmlExport.addTextTag(texttag);
		mResult.clear();
		mResult.addElement(tXmlExport);
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
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("86000020080819000077");
		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLOPRTManagerSchema);
		GLFGetNoticePrintBL tBfNoticePrintBL = new GLFGetNoticePrintBL();
		if (!tBfNoticePrintBL.submitData(tVData, "PRINT")) {
			logger.debug("\t@> BfNoticePrintBL 打印失败");
		}
	} // function main end
}
