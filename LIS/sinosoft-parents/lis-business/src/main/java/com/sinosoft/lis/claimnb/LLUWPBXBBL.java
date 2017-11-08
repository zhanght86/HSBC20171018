package com.sinosoft.lis.claimnb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.f1print.PrintTool;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LABranchGroupSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: LLUWPBXBBL.java
 * </p>
 * 
 * <p>
 * Description: 二核不自动续保通知书打印类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wanzh 2005/12/12
 * @version 1.0
 */
public class LLUWPBXBBL implements PrintService {
private static Logger logger = Logger.getLogger(LLUWPBXBBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private String mAgentCode = "";

	/** 全局数据 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();

	public LLUWPBXBBL() {
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
		if (!cOperate.equals("PRINT") && !cOperate.equals("CONFIRM")) {
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
		if (mLOPRTManagerSchema == null) {
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
		cError.moduleName = "SpecPrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	@SuppressWarnings("unchecked")
	private boolean getPrintData() {
		// 根据印刷号查询打印队列中的纪录
		String PrtNo = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(PrtNo);
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

		ExeSQL tExeSQL1 = new ExeSQL();
		SSRS tSsrs1 = new SSRS();
		String tSql1 = "select otherno,standbyflag1 from loprtmanager where prtseq = '"
				+ "?PrtNo?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql1);
		sqlbv.put("PrtNo", PrtNo);
		tSsrs1 = tExeSQL1.execSQL(sqlbv);
		if (tSsrs1.getMaxRow() == 0) {
			buildError("outputXML", "在loprtmanager表中没有获取流水号为：['" + PrtNo
					+ "']的数据！");
			return false;
		}
		String tContNo = tSsrs1.GetText(1, 1); // 保单号
		String tClmNo = tSsrs1.GetText(1, 2); // 赔案号

		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		String ttSql = "";
		ttSql = "select contno from lccont where contno = '" + "?contno?"
				+ "' and appflag = '1'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(ttSql);
		sqlbv1.put("contno", tContNo);
		tSSRS = tExeSQL.execSQL(sqlbv1);
		if (tSSRS.getMaxRow() == 0) {
			buildError("outputXML", "在lccont表中没有获取保单号为：['" + tContNo + "']的数据！");
			return false;
		}
		String mContNo = tSSRS.GetText(1, 1);
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			buildError("outputXML", "在取得LCCont的数据时发生错误");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mLCContSchema.getContNo());
		if (tLCAppntDB.getInfo() == false) {
			mErrors.copyAllErrors(tLCAppntDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
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
		@SuppressWarnings("unused")
		String AgentGroup = mLAAgentSchema.getAgentGroup();
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgentBranch的数据时发生错误");
			return false;
		}
		/**
		 * 得到险种号码
		 */
		String tRiskName = ""; // 险种名
		String tPayToDate = ""; // 届满日期
		String tRiskName_PayToDate = ""; // 拼两个字符串
		SSRS tSsrs = new SSRS();
		ExeSQL tExeSql = new ExeSQL();
		ttSql = "select polno from lluwmaster where contno = '" + "?contno?"
				+ "' and " + " caseno = '" + "?caseno?" + "' and passflag = 'b' ";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(ttSql);
		sqlbv2.put("contno", mContNo);
		sqlbv2.put("caseno", tClmNo);
		tSsrs = tExeSql.execSQL(sqlbv2);
		if (tSsrs.getMaxRow() == 0) {
			buildError("outputXML", "在表中lluwmaster没有取得赔案号为：['" + tClmNo
					+ "']和保单号为：['" + mContNo + "']的数据");
			return false;
		} else { // 在lluwmaster中polno和caseno是主键，ttSql所查完全可能是多条记录
			for (int z = 1; z <= tSsrs.getMaxRow(); z++) {
				String tPolNo = tSsrs.GetText(z, 1);
				logger.debug(z + tPolNo);
				String tYear = "";
				String tMonth = "";
				String tDay = "";
				// 查险种名
				String tRiskNameSQL = " select riskstatname from lmrisk where "
						+ " exists (select 'X' from lcpol where lmrisk.riskcode = riskcode"
						+ " and polno = '" + "?polno?" + "') ";
				// lcpol中polno主键，lmriskapp中riskcode主键，所查字段唯一
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(tRiskNameSQL);
				sqlbv3.put("polno", tPolNo);
				SSRS tSSRSRN = new SSRS();
				tSSRSRN = tExeSql.execSQL(sqlbv3);
				if (tSSRSRN.getMaxRow() == 0) {
					buildError("outputXML", "在lmriskapp,lcpol表中没有取得险种号为：['"
							+ tPolNo + "']的险种名数据");
					return false;
				}
				String tRiskNameMid = tSSRSRN.GetText(1, 1); // 险种名称中间变量
				// 查届满日期
				SSRS tSsrs3 = new SSRS();
				String tPayTodayDateSQL = " select paytodate from lcpol where polno = '"
						+ "?polno?" + "'";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tPayTodayDateSQL);
				sqlbv4.put("polno", tPolNo);
				tSsrs3 = tExeSql.execSQL(sqlbv4);
				if (tSsrs3.getMaxRow() == 0) {
					buildError("outputXML", "在表中lcpol没有取得险种号为：['" + tPolNo
							+ "']的数据");
					return false;
				}
				tYear = tSsrs3.GetText(1, 1).substring(0, 4);
				tMonth = tSsrs3.GetText(1, 1).substring(5, 7);
				tDay = tSsrs3.GetText(1, 1).substring(8, 10);
				tPayToDate = tYear + "年" + tMonth + "月" + tDay + "日";
				tRiskName_PayToDate = tRiskNameMid + "，保险期间将于" + tPayToDate
						+ "届满；" + tRiskName_PayToDate; // 险种名称和届满日期
				tRiskName = tRiskNameMid + "；" + tRiskName; // 险种名称
			}
		}

		// String[] Title = new String[4];
		// Title[0] = "";
		// Title[1] = "";
		// Title[2] = "";
		// Title[3] = "";

		// ListTable ListTable1 = new ListTable();
		// ListTable1.setName("AppendWork");

		// String[] stra = new String[5];
		// stra[0] = "险种号："; //险种号
		// stra[1] = tPolNo; //险种
		// stra[2] = " 终止日期："; //险种终止日期
		// stra[3] = tPayToDate; //险种终止日期
		// stra[4] = "";

		// ListTable1.add(stra);
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew tXmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
//		tXmlExport.createDocument("LLUWPBXB.vts", "");
		tXmlExport.createDocument("二核不自动续保通知书", "", "", "0");
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
		ExeSQL tempExeSQL = new ExeSQL();
		String tSql = "select riskcode from lcpol where contno = '"
				+ "?contno?" + "' and polno = mainpolno";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSql);
		sqlbv5.put("contno", mLCContSchema.getContNo());
		SSRS PolSSRS = new SSRS();
		PolSSRS = tempExeSQL.execSQL(sqlbv5);
		if (PolSSRS != null) {
			mLMRiskAppDB.setRiskCode(PolSSRS.GetText(1, 1));
			if (!mLMRiskAppDB.getInfo()) {
				buildError("outputXML", "查询LMRiskApp表出错！");
				return false;
			}
		} else {
			buildError("outputXML", "查询合同险种信息出错!");
			return false;
		}
		if (mLMRiskAppDB.getRiskProp().equals("Y")) {
			LAComSchema mLAComSchema = new LAComSchema();
			LAComDB mLAComDB = new LAComDB();
			// mLAComDB.setAgentCom(mLCContSchema.getAgentCom());
			LABranchGroupDB tLABranchGroupDBW = new LABranchGroupDB();
			LABranchGroupSchema tLABranchGroupSchema = new LABranchGroupSchema();
			ExeSQL tempExeSQLW = new ExeSQL();
			String tSqlwl = "select agentcom,agentgroup from lccont where contno = '"
					+ "?contno?" + "'";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tSqlwl);
			sqlbv6.put("contno", tContNo);
			SSRS PolSSRSW = new SSRS();
			PolSSRSW = tempExeSQLW.execSQL(sqlbv6);
			mLAComDB.setAgentCom(PolSSRSW.GetText(1, 1));
			// String AA=PolSSRSw.GetText(1, 2);
			tLABranchGroupDBW.setAgentGroup(PolSSRSW.GetText(1, 2));
			if (!tLABranchGroupDBW.getInfo()) {
				mErrors.copyAllErrors(tLABranchGroupDBW.mErrors);
				buildError("outputXML", "在取得LAAgentBranch的数据时发生错误");
				return false;
			}

			if (!mLAComDB.getInfo()) {
				mErrors.copyAllErrors(mLAComDB.mErrors);
				buildError("outputXML", "在取得LACom的数据时发生错误");
				return false;
			}
			mLAComSchema = mLAComDB.getSchema(); // 保存银行代码信息
			tLABranchGroupSchema = tLABranchGroupDBW.getSchema(); // 保存业务分组代码信息

			texttag.add("LCCont.BankCode", mLAComSchema.getName()); // 代理机构
			texttag.add("LCCont.AgentType", tLABranchGroupSchema.getName()); // 业务分布及业务组.
		}
		String tManageComCode = mLCContSchema.getManageCom();
		if (mLCContSchema.getManageCom().length() > 6) {
			tManageComCode = mLCContSchema.getManageCom().substring(0, 6);
		}
		String tManageComName = "";
		try {
			tManageComName = getComName(tManageComCode);
		} catch (Exception ex) {
		}
		;
//		texttag.add("BarCode1", "UN027"); // 2006-09-06 周磊 修改通知书类别
//		texttag.add("BarCodeParam1","BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
//		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
//		texttag.add("BarCodeParam2","BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		PrintTool.setBarCode(tXmlExport, mLOPRTManagerSchema.getPrtSeq());
		texttag.add("LCCont.ContNo", mContNo);
		texttag.add("LCCont.Managecom", tManageComName);
		texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
		texttag.add("AppntName", mLCContSchema.getAppntName());
		texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
		try {
			texttag.add("LABranchGroup.Name", getComName(mLCContSchema
					.getManageCom())
					+ " " + getUpComName(mLAAgentSchema.getBranchCode()));
		} catch (Exception ex1) {
		}
		texttag.add("LAAgent.Name", mLAAgentSchema.getName());
		texttag.add("LCCont.AgentCode", mLCContSchema.getAgentCode());
		texttag.add("LCCont.UWOperator", mLOPRTManagerSchema.getReqOperator());
		texttag.add("SysDate", SysDate);
		texttag.add("RiskNamePayToDate", tRiskName_PayToDate); // 险种名和届满日期
		texttag.add("RiskName", tRiskName); // 险种名称

		// xmlexport.addListTable(ListTable1, Title);
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}

	private String getComName(String strComCode) throws Exception {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			throw new Exception("在取得LDCom的数据时发生错误");
		}
		return tLDComDB.getName();
	}

	public String getUpComName(String comcode) {
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupDB ttLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(comcode);
		if (!tLABranchGroupDB.getInfo()) {
			this.buildError("getUpComName", comcode + "机构不存在！");
			return null;
		} else {
			ttLABranchGroupDB.setAgentGroup(tLABranchGroupDB.getUpBranch());
			if (!ttLABranchGroupDB.getInfo()) {
				this.buildError("getUpComName", "在查找comcode所属的营业部时出错！");
				return null;
			} else {
				return ttLABranchGroupDB.getName();
			}
		}
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		LLUWPBXBBL tLLUWPBXBBL = new LLUWPBXBBL();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("8102100351138");
		VData tVData = new VData();
		tVData.addElement(tLOPRTManagerSchema);
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "001";
		tVData.addElement(tG);
		tLLUWPBXBBL.submitData(tVData, "PRINT");
	}
}
