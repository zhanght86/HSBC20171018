package com.sinosoft.lis.claimnb;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.f1print.PrintTool;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: LLUWPartCancelPrintBL.java
 * </p>
 * 
 * <p>
 * Description: 二核通知书打印类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft<S/p>
 * 
 * @author wanzh 2005/12/12
 * @version 1.0
 */
public class LLUWPartCancelPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(LLUWPartCancelPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 输入的查询sql语句
	private String mAgentCode = "";

	// 保费保额计算出来后的精确位数
	private String FORMATMODOL = "0.00";
	// 数字转换对象
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);

	// 业务处理相关变量
	/** 全局数据 */

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	public LLUWPartCancelPrintBL() {
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
		cError.moduleName = "LCContF1PBL";
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
		String prtSeq = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号
		logger.debug("prtSeq");
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(prtSeq);
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}

		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		int m, i;
		if (!tLCContDB.getInfo()) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			buildError("outputXML", "在取得LCCont的数据时发生错误");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
		if (mLCContSchema.getContType().equals("1")) {
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);
				buildError("outputXML", "在取得LCAppnt中数据时发生错误");
				return false;
			}
		} else // Modify by zhaorx 2006-08-15
		{
			tLCGrpAppntDB.setGrpContNo(mLCContSchema.getGrpContNo());
			if (tLCGrpAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);
				buildError("outputXML", "在取得LCGrpAppnt中数据时发生错误");
				return false;
			}
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
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgentBranch的数据时发生错误");
			return false;
		}

		String[] RiskTitle = new String[5];

		RiskTitle[0] = "Type";
		RiskTitle[1] = "RiskCode";
		RiskTitle[2] = "RiskName";
		RiskTitle[3] = "Prem";
		RiskTitle[4] = "Amnt";

		ListTable tRiskListTable = new ListTable();
		String strRisk[] = null;
		tRiskListTable.setName("RISK");
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolDB.setContNo(mLCContSchema.getContNo());
		tLCPolSet = tLCPolDB.query();
		double SumPrem = 0;
		String mSumPrem = "";
		for (int j = 1; j <= tLCPolSet.size(); j++) {
			strRisk = new String[5];
			mLCPolSchema = tLCPolSet.get(j).getSchema();
			if (mLCPolSchema.getUWFlag().equals("a")) {
				continue;
			}
			if (mLCPolSchema.getPolNo().equals(mLCPolSchema.getMainPolNo())) {
				strRisk[0] = "主  险:";
			} else {
				strRisk[0] = "附加险:";
			}
			strRisk[1] = mLCPolSchema.getRiskCode();
			strRisk[2] = getRiskName(mLCPolSchema.getRiskCode());
			strRisk[3] = "￥" + mDecimalFormat.format(mLCPolSchema.getAmnt());
			strRisk[4] = "应缴保费￥"
					+ mDecimalFormat.format(mLCPolSchema.getStandPrem());
			SumPrem = SumPrem + mLCPolSchema.getStandPrem();
			// 格式化的新数据
			mSumPrem = "￥ " + mDecimalFormat.format(SumPrem);
			tRiskListTable.add(strRisk);

		}

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例

		// 二核拒保通知书 add by wanzh 2005/12/12/16:00
		if (mLOPRTManagerSchema.getCode().equals("LP00")) {
//			xmlexport.createDocument("LLNUWPJB.vts", "");
			xmlexport.createDocument("二核拒保通知书");
		}

		// 二核延期通知书 add by wanzh 2005/12/12/16:00
		if (mLOPRTManagerSchema.getCode().equals("LP06")) {
//			xmlexport.createDocument("LLUWPHBYQ.vts", "");
			xmlexport.createDocument("二核延期通知书");
		}
		// 二核退保通知书 add by wanzh 2005/12/12/16:00
		if (mLOPRTManagerSchema.getCode().equals("LP84")) {
//			xmlexport.createDocument("LLUWPTB.vts", "");
			xmlexport.createDocument("二核退保通知书");
		}

		// 二核拒保延期通知书 add by wanzh 2005/12/12/16:00
		if (mLOPRTManagerSchema.getCode().equals("LP86")) {
//			xmlexport.createDocument("LLUWPJBYQ.vts", "");
			xmlexport.createDocument("二核拒保延期通知书");
		}
		// 二核修改事项索要材料通知书
		if (mLOPRTManagerSchema.getCode().equals("LP81")) {
//			xmlexport.createDocument("LLUWPXGSXSYCL.vts", "");
			xmlexport.createDocument("二核修改事项索要材料通知书");
		}

		ExeSQL tempExeSQL = new ExeSQL();
		// 查询出险种名称
		String tRiskSql = "select riskname from lmrisk where riskcode in"
				+ " (select riskcode from lcpol where contno='"
				+ "?contno?" + "')";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tRiskSql);
		sqlbv.put("contno", mLCContSchema.getContNo());
		SSRS tRiskSSRS = new SSRS();
		tRiskSSRS = tempExeSQL.execSQL(sqlbv);
		String tRiskName = "";
		for (int h = 0; h <= tRiskSSRS.MaxRow; h++) {
			tRiskName += tRiskSSRS.GetText(h + 1, 1) + "  ";
		}

		xmlexport.addListTable(tRiskListTable, RiskTitle); // 保存加费信息
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
		String tSql = "select riskcode from lcpol where contno = '"
				+ "?contno?" + "' and polno = mainpolno";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("contno", mLCContSchema.getContNo());
		SSRS PolSSRS = new SSRS();
		PolSSRS = tempExeSQL.execSQL(sqlbv1);
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
			mLAComDB.setAgentCom(mLCContSchema.getAgentCom());
			if (!mLAComDB.getInfo()) {
				mErrors.copyAllErrors(mLAComDB.mErrors);
				buildError("outputXML", "在取得LACom的数据时发生错误");
				return false;
			}
			mLAComSchema = mLAComDB.getSchema(); // 保存银行代码信息

			texttag.add("LCCont.BankCode", mLAComSchema.getName()); // 代理机构
			texttag.add("BankNo", mLAComSchema.getName()); // 代理机构

			texttag.add("LCCont.AgentType", tLABranchGroupDB.getName()); // 业务分布及业务组
																			// .
			texttag.add("Depart.Name", tLABranchGroupDB.getName()); // 业务分布及业务组.
			texttag.add("AgentGroup", tLABranchGroupDB.getName()); // 业务分布及业务组.

		}
		/**
		 * ====================================================================
		 * ====== 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
		 * LABranchGroup.Name：取8位编码的营业销售部
		 * ========================================
		 * ==================================
		 */
		// 中支机构名称
		String tManageComCode = mLCContSchema.getManageCom();
		if (mLCContSchema.getManageCom().length() > 6)
			tManageComCode = mLCContSchema.getManageCom().substring(0, 6);
		String tManageComName = getComName(tManageComCode);
		// 营业销售部名称
		String tBranchGroupCode = mLCContSchema.getManageCom();
		String tBranchGroupName = getComName(tBranchGroupCode);

		PrintTool.setBarCode(xmlexport, mLOPRTManagerSchema.getPrtSeq());
//		texttag.add("BarCode1", "UN027"); // 2006-09-06 周磊 修改通知书类别
		texttag.add("RiskName", tRiskName); // 险种名称
		texttag.add("UWError", mLOPRTManagerSchema.getRemark()); // 拒保原因
//		texttag
//				.add(
//						"BarCodeParam1",
//						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
//		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
//		texttag
//				.add(
//						"BarCodeParam1",
//						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		texttag.add("LCCont.ContNo", mLCContSchema.getContNo());
		texttag.add("PrtNo", mLCContSchema.getContNo());
		texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
		texttag.add("AppntName", mLCContSchema.getAppntName());
		texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
		texttag.add("LCCont.Managecom", tManageComName);
		texttag.add("ManageComName", tManageComName); // 6位的中支机构
		texttag.add("LABranchGroup.Name", getComName(mLCContSchema
				.getManageCom())
				+ " " + getUpComName(mLAAgentSchema.getBranchCode()));
		texttag.add("LAAgent.Name", mLAAgentSchema.getName());
		texttag.add("LCCont.AgentCode", mLAAgentSchema.getAgentCode());
		texttag.add("LAAgent.AgentCode", mLAAgentSchema.getAgentCode());
		texttag.add("AgentName", mLAAgentSchema.getName());
		texttag.add("AgentCode", mLAAgentSchema.getAgentCode());
		texttag.add("ManCom", mLAAgentSchema.getManageCom());
		String tManageComSql = "select name from ldcom where comcode='"
				+ "?comcode?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tManageComSql);
		sqlbv2.put("comcode", mLAAgentSchema.getManageCom());
		String tManageCom = tempExeSQL.getOneValue(sqlbv2);
		texttag.add("ManageCom", tManageCom);
		texttag.add("Phone", mLAAgentSchema.getMobile());
		texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq());
		texttag.add("UWCode", mLOPRTManagerSchema.getReqOperator());
		texttag.add("LCCont.UWOperator", mLOPRTManagerSchema.getReqOperator());
		texttag.add("LCContl.UWOperator", mLOPRTManagerSchema.getReqOperator());
		LJTempFeeDB mLJTempFeeDB = new LJTempFeeDB();
		LJTempFeeSchema mLJTempFeeSchema = new LJTempFeeSchema();
		mLJTempFeeDB.setOtherNo(mLCContSchema.getContNo());
		mLJTempFeeSchema = mLJTempFeeDB.query().get(1);
		if (mLJTempFeeSchema != null) {
			texttag.add("TempFeeNo", mLJTempFeeSchema.getTempFeeNo());
		}
		texttag.add("DealContent", mLOPRTManagerSchema.getRemark());
		texttag.add("SumPrem", mSumPrem);
		texttag.add("Res", mLOPRTManagerSchema.getRemark());

		texttag.add("SysDate", SysDate);
		texttag.add("LCCUWMaster.ChangePolReason", mLOPRTManagerSchema
				.getRemark());
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		mResult.clear();
		mResult.addElement(xmlexport);
		logger.debug("xmlexport=" + xmlexport);
		return true;
	}

	// 下面是一些辅助函数

	private String getRiskName(String strRiskCode) {
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(strRiskCode);
		if (!tLMRiskDB.getInfo()) {
			mErrors.copyAllErrors(tLMRiskDB.mErrors);
			buildError("outputXML", "在取得RiskName的数据时发生错误");
		}

		return tLMRiskDB.getRiskName();
	}

	private String getComName(String strComCode) {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			buildError("outputXML", "在取得Ldcom的数据时发生错误");
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

	public static void main(String[] args) {
		LLUWPartCancelPrintBL tLLUWPartCancelPrintBL = new LLUWPartCancelPrintBL();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("8100000000171");
		VData tVData = new VData();
		tVData.addElement(tLOPRTManagerSchema);

		tLLUWPartCancelPrintBL.submitData(tVData, "PRINT");
	}

}
