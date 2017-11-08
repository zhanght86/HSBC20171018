package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
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
 * @author quyang
 * @version 1.0
 */
public class QuotaPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(QuotaPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

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
	private LCSpecSchema mLCSpecSchema = new LCSpecSchema();

	public QuotaPrintBL() {
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
		cError.moduleName = "QuotaPrintUI";
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

		ExeSQL tempExeSQL = new ExeSQL();

		String ttSql = null;

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		String ContNo = mLOPRTManagerSchema.getOtherNo();
		logger.debug("ContNo=" + ContNo);
		int m, i;
		if (!tLCContDB.getInfo()) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			buildError("outputXML", "在取得LCCont的数据时发生错误");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();

		logger.debug("##############################");
		logger.debug("##############################");
		logger.debug("##############################");

		SSRS ttSSRS = new SSRS();

		ttSql = "select codename from ldcode where Codetype='station'"
				+ " and code ='" + "?code?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(ttSql);
		sqlbv1.put("code", mLCContSchema.getManageCom());
		ttSSRS = tempExeSQL.execSQL(sqlbv1);
		if (ttSSRS.getMaxRow() == 0) {
			CError tError = new CError();
			tError.moduleName = "UWSendALLPrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "在取得管理机构信息时出错!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String Station = ttSSRS.GetText(1, 1);

		logger.debug("##############################");
		logger.debug("##############################");
		logger.debug("##############################");

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
		String AgentGroup = mLAAgentSchema.getAgentGroup();
		logger.debug("AgentGroup=" + AgentGroup);
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgentBranch的数据时发生错误");
			return false;
		}

		logger.debug("##############################");
		logger.debug("###---     核保限额       ---###");
		logger.debug("##############################");

		// 主险
		ttSql = "select (select tt.riskshortname from lmrisk tt where tt.riskcode = t.RiskCode),t.RiskCode,t.Amnt,t.Prem,t.ContNo from LCPol t  where t.ContNo = '"
				+ "?ContNo?" + "'" + "order by polno";
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(ttSql);
		sqlbv2.put("ContNo", ContNo);
		tSSRS = tempExeSQL.execSQL(sqlbv2);
		String[][] strIW = new String[20][5];

		double SumPrem = 0; // 合计应缴费金额
		String[] Title = new String[5];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		ListTable tPremListTable = new ListTable();
		tPremListTable.setName("RISK");
		boolean flag = false;
		int x = 0;
		for (int ii = 1; ii < tSSRS.getMaxRow() + 1; ii++) {

			if (tSSRS != null && tSSRS.getMaxRow() > 0 && tSSRS.getMaxCol() > 0) {
				if (!(tSSRS.GetText(ii, 4).equals("0")
						|| tSSRS.GetText(ii, 4).trim().equals("") || tSSRS
						.GetText(ii, 4).equals("null"))) {

					flag = true;

					strIW[x][0] = "险种" + ii + ":"; // 主险还是附加险
					strIW[x][1] = tSSRS.GetText(ii, 1); // 险种名称
					strIW[x][2] = tSSRS.GetText(ii, 2); // 险种代码
					strIW[x][3] = mDecimalFormat.format(new Double(tSSRS
							.GetText(ii, 3))); // 现有保额
					strIW[x][4] = "￥ "
							+ mDecimalFormat.format(new Double(tSSRS.GetText(
									ii, 4))); // 应缴保费

					logger.debug(tSSRS.GetText(ii, 1));
					SumPrem = SumPrem
							+ Double.parseDouble(tSSRS.GetText(ii, 4));

					tPremListTable.add(strIW[x]);
					x++;
				}

			}
		}

		logger.debug("##############################");
		logger.debug("####---     核保限额       ---###");
		logger.debug("##############################");

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("NewQuota.vts", "");
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		// if(mLCContSchema.getSaleChnl().equals("3"))
		// {
		// LAComSchema mLAComSchema = new LAComSchema();
		// LAComDB mLAComDB = new LAComDB();
		// mLAComDB.setAgentCom(mLCContSchema.getAgentCom());
		// if (!mLAComDB.getInfo())
		// {
		// mErrors.copyAllErrors(mLAComDB.mErrors);
		// buildError("outputXML", "在取得LACom的数据时发生错误");
		// return false;
		// }
		// mLAComSchema = mLAComDB.getSchema(); //保存银行代码信息
		//
		// texttag.add("BankNo", mLAComSchema.getName()); //代理机构
		// texttag.add("AgentGroup", tLABranchGroupDB.getName()); //业务分布及业务组.
		// }
		LCPolSchema mLCPolSchema = new LCPolSchema();
		LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
		String tSql = "select riskcode from lcpol where contno = '"
				+ "?contno?" + "' and polno = mainpolno";
		SSRS PolSSRS = new SSRS();
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("contno", mLCContSchema.getContNo());
		PolSSRS = tempExeSQL.execSQL(sqlbv3);
		if (PolSSRS != null) {
			mLMRiskAppDB.setRiskCode(PolSSRS.GetText(1, 1));
			if (!mLMRiskAppDB.getInfo()) {
				buildError("outputXML", "查询LMRiskApp表出错！");
				return false;
			}
		} else {
			buildError("outputXML", "查询合同险种信息出错！");
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

			texttag.add("BankNo", mLAComSchema.getName()); // 代理机构
			texttag.add("AgentGroup", tLABranchGroupDB.getName()); // 业务分布及业务组.
		}

		/**
		 * ==========================================================================
		 * 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
		 * LABranchGroup.Name：取8位编码的营业销售部 Sumprem : 取用标准的格式：0.00
		 * ==========================================================================
		 */
		// 中支机构名称
		String tManageComCode = mLCContSchema.getManageCom();
		if (mLCContSchema.getManageCom().length() > 6) {
			tManageComCode = mLCContSchema.getManageCom().substring(0, 6);
		}
		String tManageComName = getComName(tManageComCode);

		String mSumprem = "￥ " + mDecimalFormat.format(SumPrem);

		texttag.add("BarCode1", "UN018");
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam2",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		texttag.add("LCCont.ProposalContNo", mLCContSchema.getContNo());
		texttag.add("LCCont.Managecom", tManageComName);
		texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
		texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
		texttag.add("LABranchGroup.Name", getComName(mLCContSchema
				.getManageCom())
				+ " " + getUpComName(mLAAgentSchema.getBranchCode()));
		texttag.add("LAAgent.Name", mLAAgentSchema.getName());
		texttag.add("LAAgent.AgentCode", mLCContSchema.getAgentCode());
		texttag.add("LCContl.UWOperator", mLOPRTManagerSchema.getReqOperator());
		texttag.add("SumPrem", mSumprem);
		String Operator = mLCContSchema.getOperator();
		logger.debug("Operator=" + Operator);
		texttag.add("SysDate", SysDate);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		if (flag) {
			xmlexport.addDisplayControl("Risk"); // 模版上的主险附加险部分的控制标记
			xmlexport.addListTable(tPremListTable, Title); // 保存主险附加险信息
		}

		// xmlexport.outputDocumentToFile("d:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		logger.debug("xmlexport=" + xmlexport);
		return true;
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

	private String getComName(String strComCode) {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			buildError("outputXML", "在取得Ldcom的数据时发生错误");
		}
		return tLDComDB.getName();
	}

	public static void main(String[] args) {
		QuotaPrintBL tQuotaPrintUI = new QuotaPrintBL();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("8100007420272");
		VData tVData = new VData();
		tVData.addElement(tLOPRTManagerSchema);
		tQuotaPrintUI.submitData(tVData, "PRINT");
	}

}
