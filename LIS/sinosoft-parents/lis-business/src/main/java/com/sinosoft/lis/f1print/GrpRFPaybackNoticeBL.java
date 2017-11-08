package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

public class GrpRFPaybackNoticeBL {
	private static Logger logger = Logger.getLogger(GrpRFPaybackNoticeBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private String FORMATMODOL = "0.00";
	// 数字转换对象
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();

	private String mOperate = "";

	public GrpRFPaybackNoticeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		try {

			if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			if (mOperate.equals("PRINT")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintData();

			} else if (mOperate.equals("CONFIRM")) {
				if (!saveData(cInputData)) {
					return false;
				}
			}

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", ex.toString());
			return false;
		}
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RefuseAppF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private void getPrintData() throws Exception {


		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema); // 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}

		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all message！

		tLCGrpContDB.setPrtNo(mLOPRTManagerSchema.getOtherNo());
		LCGrpContSet tempLCGrpContSet = tLCGrpContDB.query();

		if (tempLCGrpContSet.size() != 1) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			throw new Exception("在查询保单信息时出错！");
		}

		tLCGrpContDB.setSchema(tempLCGrpContSet.get(1));

		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(tLCGrpContDB.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			throw new Exception("在查询代理人信息时出错");
		}
		mLAAgentSchema = tLAAgentDB.getSchema();

		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
			throw new Exception("在查询代理机构时出错");
		}

		// 查询需要退的费用  ljaget中存放的是grpcontno
		String tStrSQL = "select nvl(sum(sumgetmoney),0) from ljaget where otherno = '"
				+ tLCGrpContDB.getGrpContNo() + "' and othernotype = '6' and confdate is null ";
		ExeSQL tExeSQL = new ExeSQL();
		String tResult = tExeSQL.getOneValue(tStrSQL);
		if (tResult == "" || tResult == null || tResult.equals("")) {
			tResult = "0";
		}
		String RiskFeeRest = mDecimalFormat.format(Double.parseDouble(tResult));
		
		XmlExportNew xmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
		TextTag texttag = new TextTag();
		String tPrintName = "团单溢交退费通知书";
		xmlExport.createDocument(tPrintName);// 初始化数据存储类
		PrintTool.setBarCode(xmlExport, "UN081");
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());// 条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLCGrpContDB
				.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage))
			xmlExport.setUserLanguage(uLanguage);// 用户语言
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));// 系统语言
		texttag.add("RiskFeeRest", RiskFeeRest);

		String[] RiskTitle = new String[3];

		RiskTitle[0] = "RiskCode";
		RiskTitle[1] = "RiskName";
		RiskTitle[2] = "Prem";

		ListTable tRiskListTable = new ListTable();

		tRiskListTable.setName("RISK");

		String sSql_1 = "select * from lccontplan where plantype='6' "
				+ " and proposalgrpcontno ='" + tLCGrpContDB.getPrtNo() + "'"
				+ " and grpcontno='" + tLCGrpContDB.getGrpContNo() + "'";
		SSRS sSSRS_1 = new SSRS();
		ExeSQL rExeSQL = new ExeSQL();
		sSSRS_1 = rExeSQL.execSQL(sSql_1);

		String sSql = "";
		if (sSSRS_1.getMaxRow() != 0) {
			sSql = "select lccontplan.contplancode,lccontplan.contplanname,"
					+ "( select nvl(sum(prem),0) from lcpol where lcpol.grpcontno=lccontplan.grpcontno "
					+ " and lcpol.prtno=lccontplan.proposalgrpcontno)"
					+ " from lccontplan where plantype='6'"
					+ " and proposalgrpcontno ='" + tLCGrpContDB.getPrtNo()
					+ "'";
		}

		else {
			sSql = "select distinct a.riskcode,"
					+ "(select riskname from lmrisk where riskcode =a.riskcode),sum(a.prem)"
					+ "from lcpol a " + "where a.prtno='"
					+ tLCGrpContDB.getPrtNo() + "' and grpcontno='"
					+ tLCGrpContDB.getGrpContNo() + "'"
					+ " group by a.riskcode";
		}

		SSRS sSSRS = new SSRS();
		ExeSQL sExeSQL = new ExeSQL();
		sSSRS = sExeSQL.execSQL(sSql);

		double tTotalRiskFee = 0;// 保险费合计
		for (int k = 1; k <= sSSRS.getMaxRow(); k++) {
			String[] strRisk = new String[3];
			strRisk[0] = sSSRS.GetText(k, 1);
			strRisk[1] = sSSRS.GetText(k, 2);
			strRisk[2] = "￥"
					+ mDecimalFormat.format(Double.parseDouble(sSSRS.GetText(k,
							3)));
			tTotalRiskFee = tTotalRiskFee
					+ Double.parseDouble(sSSRS.GetText(k, 3));
			tRiskListTable.add(strRisk);
		}

		String tSql = "select nvl((select codename from ldcode"
				+ " where codetype = 'paymode' and code = "
				+ "(select paymode from ljtempfeeclass b "
				+ " where b.tempfeeno = a.tempfeeno "
				+ " and rownum = '1')),'未缴费')" + " from ljtempfee a "
				+ " where a.otherno = '" + tLCGrpContDB.getGrpContNo() + "'";
		SSRS paymode = new SSRS();
		paymode = tExeSQL.execSQL(tSql);

		texttag.add("LCCont.AppntName", tLCGrpContDB.getGrpName());
		texttag.add("AppntName", tLCGrpContDB.getGrpName());
		texttag.add("PrtNo", tLCGrpContDB.getPrtNo());
		texttag.add("LCGrpContl.Operator", tLCGrpContDB.getOperator());
		texttag.add("LCCont.ProposalContNo", tLCGrpContDB
				.getProposalGrpContNo());
		texttag.add("LCGrpCont.ContNo", tLCGrpContDB.getGrpContNo());
		texttag.add("LAAgent.Name", getAgentName(tLCGrpContDB.getAgentCode()));
		;
		texttag.add("LCCont.AgentCode", tLCGrpContDB.getAgentCode());
		texttag.add("ManageComName", getComName(tLCGrpContDB.getManageCom()));
		texttag.add("Depart.Name", tLABranchGroupDB.getName());
		texttag.add("LABranchGroup.Name", mLAAgentSchema.getManageCom());
		texttag.add("LCGrpCont.BankCode", tLCGrpContDB.getBankCode());
		texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq());
		texttag.add("LCGrpContl.Operator", tLCGrpContDB.getOperator());
		texttag.add("TotalRiskFee", mDecimalFormat.format(tTotalRiskFee));
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		texttag.add("SysDate", df.format(new Date()));
		if (paymode != null && paymode.getMaxRow() != 0) {
			texttag.add("Paymode", paymode.GetText(1, 1));
		} else {
			texttag.add("Paymode", "");
		}

		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}
		xmlExport.addListTable(tRiskListTable, RiskTitle); // 保存加费信息
		mResult.clear();
		mResult.addElement(xmlExport);

	}

	// 下面是一些辅助函数


	private String getAgentName(String strAgentCode) throws Exception {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(strAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			throw new Exception("在取得LAAgent的数据时发生错误");
		}
		return tLAAgentDB.getName();
	}

	private String getComName(String strComCode) throws Exception {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strComCode);
		tLDCodeDB.setCodeType("station");

		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			throw new Exception("在取得LDCode的数据时发生错误");
		}
		return tLDCodeDB.getCodeName();
	}
	
	private boolean saveData(VData mInputData) {
		// 根据印刷号查询打印队列中的纪录
		// mLOPRTManagerSchema
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		mLOPRTManagerSchema.setStateFlag("1");
		mLOPRTManagerSchema.setDoneDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setExeOperator(mGlobalInput.Operator);
		MMap map = new MMap();
		map.put(mLOPRTManagerSchema, "UPDATE");
		PubSubmit tPubSubmit =new PubSubmit();
		VData ttVData =new VData();
		ttVData.add(map);
		if(!tPubSubmit.submitData(ttVData)){
			CError.buildErr(this, "数据提交失败！");
			return false;
		}
		return true;
	}

	public static void main(String args[]) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("20061215000004");
		VData tVData = new VData();

		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86210001";
		tG.ManageCom = "86210001";
		tG.Operator = "001";
		tVData.addElement(tLOPRTManagerSchema);
		tVData.addElement(tG);
		GrpRFPaybackNoticeBL tGrpRFPaybackNoticeUI = new GrpRFPaybackNoticeBL();
		tGrpRFPaybackNoticeUI.submitData(tVData, "PRINT");
	}

}
