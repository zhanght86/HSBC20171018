package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCInsuredSet;
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
 * Title:
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
 * Company: Sinosoft<S/p>
 * 
 * @author ccvip
 * @version 1.0
 */
public class AddRefundmentBL implements PrintService {
private static Logger logger = Logger.getLogger(AddRefundmentBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private String mAgentCode = "";
	private String tSql = "";
	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
	private String mRealPayMoney = "";
	// 保费保额计算出来后的精确位数
	private String FORMATMODOL = "0.00";
	// 数字转换对象
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);

	// 业务处理相关变量
	/** 全局数据 */

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();

	public AddRefundmentBL() {
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
		if (!cOperate.equals("PRINT")) {
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
		double SumPrem = 0; // 合计保费

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

		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		String ContNO = mLOPRTManagerSchema.getOtherNo();
		logger.debug("ContNO=" + ContNO);

		if (!tLCCUWMasterDB.getInfo()) {
			mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			buildError("outputXML", "在取得LCCUWMasterDB的数据时发生错误");
			return false;
		}
		mLCCUWMasterSchema = tLCCUWMasterDB.getSchema();

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		String ContNo = mLOPRTManagerSchema.getOtherNo();
		logger.debug("ContNo=" + ContNo);

		if (!tLCContDB.getInfo()) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			buildError("outputXML", "在取得LCCont的数据时发生错误");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();

		logger.debug("##############################");
		logger.debug("##############################");
		logger.debug("##############################");

		ExeSQL tempExeSQL = new ExeSQL();
		// SSRS ttSSRS = new SSRS();
		// String ttSql = "select codename from ldcode where Codetype='station'"
		// +
		// " and code ='" + mLCContSchema.getManageCom() + "'";
		//
		// ttSSRS = tempExeSQL.execSQL(ttSql);
		// if (ttSSRS.getMaxRow() == 0) {
		// CError tError = new CError();
		// tError.moduleName = "AddPremPrintBL";
		// tError.functionName = "getPrintData";
		// tError.errorMessage = "在取得管理机构信息时出错!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// String Station = ttSSRS.GetText(1, 1);

		logger.debug("##############################");
		logger.debug("##############################");
		logger.debug("##############################");

		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mLCContSchema.getContNo());
		if (tLCAppntDB.getInfo() == false) {
			mErrors.copyAllErrors(tLCAppntDB.mErrors);
			buildError("outputXML", "在取得投保人数据时发生错误");
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

		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVV------------现有保费保额部分-------START------VVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV

		tSql = "select (select tt.riskshortname from lmrisk tt where tt.riskcode = t.RiskCode),t.RiskCode,t.Amnt,t.StandPrem,t.ContNo,t.uwflag "
				+ " from LCPol t where  t.ContNo = '"
				+ "?ContNo?"
				+ "' and uwflag not in ('1','2','a') order by polno";
		sqlbv.sql(tSql);
		sqlbv.put("ContNo", ContNo);
		SSRS XYSSRS = new SSRS();

		XYSSRS = tempExeSQL.execSQL(sqlbv);
		String[][] str = new String[20][5];
		int XY = 0;
		boolean FlagXY = false;
		ListTable ListTable = new ListTable();
		ListTable.setName("RISK");

		for (int ii = 1; ii < XYSSRS.getMaxRow() + 1; ii++) {

			if (XYSSRS != null && XYSSRS.getMaxRow() > 0
					&& XYSSRS.getMaxCol() > 0) {
				if (XYSSRS.GetText(ii, 6).equals("1")
						|| XYSSRS.GetText(ii, 6).equals("2")
						|| XYSSRS.GetText(ii, 6).equals("a")) {
					continue;
				}
				if (!(XYSSRS.GetText(ii, 4).equals("0")
						|| XYSSRS.GetText(ii, 4).trim().equals("") || XYSSRS
						.GetText(ii, 4).equals("null"))) {

					FlagXY = true;

					str[XY][0] = "险种" + ii + ":"; //
					str[XY][1] = XYSSRS.GetText(ii, 1); // 险种名称
					str[XY][2] = XYSSRS.GetText(ii, 2); // 险种代码
					str[XY][3] = mDecimalFormat.format(new Double(XYSSRS
							.GetText(ii, 3))); // 现有保额
					str[XY][4] = "￥ "
							+ mDecimalFormat.format(new Double(XYSSRS.GetText(
									ii, 4))); // 应缴保费

					logger.debug(XYSSRS.GetText(ii, 1));
					SumPrem = SumPrem
							+ Double.parseDouble(XYSSRS.GetText(ii, 4));

					ListTable.add(str[XY]);
					XY++;
				}

			}
		}
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVV------------现有保费保额部分--------END-------VVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV

		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVV--------投保人加费部分-------START--------VVVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV

		// 投保人职业加费
		boolean flagapp1 = false;
		boolean flagapp2 = false;
		tSql = "select a.riskcode,(case when sum(b.prem) is not null then sum(b.prem)  else 0 end) from lcpol a ,lcprem b "
				+ " where b.contno= '"
				+ "?contno?"
				+ "' and b.addfeedirect="
				+ "'01' and b.payplancode like '000000%' and b.payplantype= '02' "
				+ " and a.polno=b.polno"
				+ " and b.polno in (select c.polno from lcpol c where "
				+ "  c.contno='" + "?contno?"
				+ "' ) group by riskcode ";
		sqlbv.sql(tSql);
		sqlbv.put("contno", mLCContSchema.getContNo());
		SSRS temp7SSRS = new SSRS(); // 投保人职业加费
		temp7SSRS = tempExeSQL.execSQL(sqlbv);
		String[] Title = new String[4];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";

		ListTable ListTable1 = new ListTable();
		ListTable1.setName("AppendWork");
		for (int i = 1; i < temp7SSRS.getMaxRow(); i++) {
			if (temp7SSRS != null && temp7SSRS.getMaxRow() > 0
					&& temp7SSRS.getMaxCol() > 0) {
				if (!(temp7SSRS.GetText(i, 2).equals("0")
						|| temp7SSRS.GetText(i, 2).trim().equals("") || temp7SSRS
						.GetText(i, 2).equals("null"))) {
					flagapp1 = true;

					LMRiskDB t1LMRiskDB = new LMRiskDB();
					LMRiskSchema t1LMRiskSchema = new LMRiskSchema();

					t1LMRiskDB.setRiskCode(temp7SSRS.GetText(i, 1));
					t1LMRiskSchema = t1LMRiskDB.query().get(1);

					String[] stra = new String[5];
					stra[0] = "险种" + i + "："; // 主险还是附加险
					stra[1] = t1LMRiskSchema.getRiskShortName(); // 险种名称
					stra[2] = temp7SSRS.GetText(i, 1); // 险种代码
					stra[3] = "￥ "
							+ mDecimalFormat.format(new Double(temp7SSRS
									.GetText(i, 2))); // 职业加费金额
					stra[4] = "";

					SumPrem = SumPrem
							+ Double.parseDouble(temp7SSRS.GetText(i, 2));

					logger.debug("SumPrem=" + SumPrem);

					ListTable1.add(stra);
				}
			}
		}

		// 投保人健康加费
		tSql = "select a.riskcode,(case when sum(b.prem) is not null then sum(b.prem)  else 0 end) from lcpol a ,lcprem b "
				+ " where b.contno= '"
				+ "?contno?"
				+ "' and b.addfeedirect="
				+ "'01' and b.payplancode like '000000%' and b.payplantype= '01' "
				+ " and a.polno=b.polno"
				+ " and b.polno in (select c.polno from lcpol c where"
				+ "  c.contno='" + "?contno?"
				+ "') group by riskcode ";
		sqlbv.sql(tSql);
		sqlbv.put("contno", mLCContSchema.getContNo());
		SSRS temp8SSRS = new SSRS(); // 投保人健康加费
		temp8SSRS = tempExeSQL.execSQL(sqlbv);

		ListTable ListTable2 = new ListTable();
		ListTable2.setName("AppendHealth");
		for (int j = 1; j < temp8SSRS.getMaxRow(); j++) {
			if (temp8SSRS != null && temp8SSRS.getMaxRow() > 0
					&& temp8SSRS.getMaxCol() > 0) {
				if (!(temp8SSRS.GetText(j, 2).equals("0")
						|| temp8SSRS.GetText(j, 2).trim().equals("") || temp8SSRS
						.GetText(j, 2).equals("null"))) {
					flagapp2 = true;

					LMRiskDB t2LMRiskDB = new LMRiskDB();
					LMRiskSchema t2LMRiskSchema = new LMRiskSchema();

					t2LMRiskDB.setRiskCode(temp8SSRS.GetText(j, 1));
					logger.debug(temp8SSRS.GetText(j, 1));
					t2LMRiskSchema = t2LMRiskDB.query().get(1);
					logger.debug(t2LMRiskSchema.getRiskShortName());

					String[] strb = new String[5];
					strb[0] = "险种" + j + ":"; // 主险还是附加险
					strb[1] = t2LMRiskSchema.getRiskShortName(); // 险种名称
					strb[2] = temp8SSRS.GetText(j, 1); // 险种代码
					strb[3] = "￥ "
							+ mDecimalFormat.format(new Double(temp8SSRS
									.GetText(j, 2))); // 健康加费金额
					strb[4] = "";

					SumPrem = SumPrem
							+ Double.parseDouble(temp8SSRS.GetText(j, 2));
					logger.debug("SumPrem=" + SumPrem);
					ListTable2.add(strb);
				}
			}
		}
		logger.debug("flagapp1=" + flagapp1);
		logger.debug("flagapp2=" + flagapp2);
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVVVV---------投保人加费部分-----END----VVVVVVVVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV

		// 判断是否有联生险
		tSql = "select a.polno from lcpol a where a.contno='"
				+ "?contno?"
				+ "' and a.RiskCode in (select b.riskcode from lmriskapp b where b.insuredflag='M'"
				+ " and riskprop='I')  order by a.polno";
		sqlbv.sql(tSql);
		sqlbv.put("contno", mLCContSchema.getContNo());

		SSRS temp9SSRS = new SSRS();
		temp9SSRS = tempExeSQL.execSQL(sqlbv);

		// 查询该合同下有几个被保险人。
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mLCContSchema.getContNo());

		logger.debug(mLCContSchema.getContNo());
		LCInsuredSet tempLCInsuredSet = tLCInsuredDB.query();

		int i = 2 * tempLCInsuredSet.size(); // 每个被保险人有职业和健康加费两种。
		logger.debug(tempLCInsuredSet.size());

		ListTable[] tPremListTable = new ListTable[i];

		logger.debug("i=" + i);
		boolean[] Flag = new boolean[i];
		SSRS temp1SSRS = new SSRS(); // 所有主险

		SSRS temp2SSRS = new SSRS(); // 某一主险下的职业加费
		SSRS temp5SSRS = new SSRS(); // 某一主险下的健康加费

		SSRS temp3SSRS = new SSRS(); // 该主险下的所有附加检

		SSRS tempASSRS = new SSRS(); // 备用
		SSRS tempBSSRS = new SSRS(); // 备用

		int number, j;
		int x = 0;
		int y = 0;
		String[][] strIW = new String[20][5];

		if (temp9SSRS.getMaxRow() == 0) { // 该合同号下--没有--联生险。
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVV--------被保险人部分(多被保人情况)-------START------VVVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV

			for (number = 0, j = 0; number < tempLCInsuredSet.size(); number++, j = j + 2) {

				// 第一被保人
				LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
				mLCInsuredSchema = tempLCInsuredSet.get(number + 1).getSchema();
				logger.debug("InsuredNo="
						+ mLCInsuredSchema.getInsuredNo());

				// 查询所有险种

				LCPolDB tLCPolDB = new LCPolDB();
				tSql = "select PolNo , riskcode from LCPol where ContNo='"
						+ "?ContNo?" + "' and InsuredNo='"
						+ "?InsuredNo?" + "' order by PolNo";
				// and PolNo=MainPolNo";
				sqlbv.sql(tSql);
				sqlbv.put("ContNo", tLCContDB.getContNo());
				sqlbv.put("InsuredNo", mLCInsuredSchema.getInsuredNo());
				temp1SSRS = tempExeSQL.execSQL(sqlbv);

				logger.debug("j=" + j);
				tPremListTable[j] = new ListTable();
				tPremListTable[j].setName("InsuredWork" + number); // 对应模版被保险人职业加费部分的行对象名
				logger.debug("number=" + number);
				logger.debug(tPremListTable[j].getName());

				tPremListTable[j + 1] = new ListTable();
				tPremListTable[j + 1].setName("InsuredHealth" + number); // 对应模版被保险人健康加费部分的行对象名
				logger.debug("j+1=" + j + 1);
				logger.debug(tPremListTable[j + 1].getName());

				for (int MRisk_1 = 1; MRisk_1 <= temp1SSRS.getMaxRow(); MRisk_1++) {
					tSql = "select (case when sum(prem) is not null then sum(prem)  else 0 end),payplanType from LCPrem where PolNo='"
							+ "?PolNo?"
							+ "' and PayPlanCode like '000000%'"
							+ " and PayPlanType in('02') group by payplanType";
					sqlbv.sql(tSql);
					sqlbv.put("PolNo", temp1SSRS.GetText(MRisk_1, 1));
					logger.debug("###########PolNo="
							+ temp1SSRS.GetText(MRisk_1, 1));
					temp2SSRS = tempExeSQL.execSQL(sqlbv);
					// 2-职业加费 PayPlanType = 2
					for (int MRisk = 1; MRisk <= temp2SSRS.getMaxRow(); MRisk++) {
						if (temp2SSRS != null && temp2SSRS.getMaxRow() > 0
								&& temp2SSRS.getMaxCol() > 0) {
							if (!(temp2SSRS.GetText(MRisk, 1).equals("0")
									|| temp2SSRS.GetText(MRisk, 1).trim()
											.equals("") || temp2SSRS.GetText(
									MRisk, 1).equals("null"))) {
								Flag[j] = true;

								LMRiskDB tLMRiskDB = new LMRiskDB();
								tLMRiskDB.setRiskCode(temp1SSRS.GetText(
										MRisk_1, 2));
								logger.debug("RiskCode="
										+ temp1SSRS.GetText(MRisk_1, 2));
								if (!tLMRiskDB.getInfo()) {
									mErrors.copyAllErrors(tLMRiskDB.mErrors);
									buildError("outputXML", "在取得LMRisk的数据时发生错误");
									return false;
								}

								LMRiskSchema tLMRiskSchema = tLMRiskDB.query()
										.get(1);

								logger.debug("x=" + x);
								String[] strc = new String[5];
								strc[0] = "险种" + MRisk * MRisk_1 + ":"; // 主险还是附加险
								strc[1] = tLMRiskSchema.getRiskShortName(); // 险种名称
								logger.debug(tLMRiskSchema
										.getRiskShortName());
								strc[2] = temp1SSRS.GetText(MRisk_1, 2); // 险种代码
								strc[3] = "￥ "
										+ mDecimalFormat.format(new Double(
												temp2SSRS.GetText(MRisk, 1))); // 职业加费金额
								strc[4] = "";

								logger.debug(temp2SSRS.GetText(MRisk, 1));
								SumPrem = SumPrem
										+ Double.parseDouble(temp2SSRS.GetText(
												MRisk, 1));
								logger.debug("SumPrem=" + SumPrem);
								tPremListTable[j].add(strc);
								x++;
							}
						}
					}
				}

				for (int MRisk1_1 = 1; MRisk1_1 <= temp1SSRS.getMaxRow(); MRisk1_1++) {
					// 健康加费 PayPlanType = 1
					tSql = "select (case when sum(prem) is not null then sum(prem)  else 0 end),payplanType from LCPrem where PolNo='"
							+ "?PolNo?"
							+ "'and PayPlanCode like '000000%'"
							+ "and PayPlanType in('01') group by payplanType";
					sqlbv.sql(tSql);
					sqlbv.put("", temp1SSRS.GetText(MRisk1_1, 1));
					logger.debug("PolNo="
							+ temp1SSRS.GetText(MRisk1_1, 1));
					temp5SSRS = tempExeSQL.execSQL(sqlbv);
					for (int MRisk1 = 1; MRisk1 <= temp5SSRS.getMaxRow(); MRisk1++) {
						if (temp5SSRS != null && temp5SSRS.getMaxRow() > 0
								&& temp5SSRS.getMaxCol() > 0) {
							if (!(temp5SSRS.GetText(MRisk1, 1).equals("0")
									|| temp5SSRS.GetText(MRisk1, 1).trim()
											.equals("") || temp5SSRS.GetText(
									MRisk1, 1).equals("null"))) {
								Flag[j + 1] = true;

								LMRiskDB tLMRiskDB = new LMRiskDB();
								tLMRiskDB.setRiskCode(temp1SSRS.GetText(
										MRisk1_1, 2));
								logger.debug(temp1SSRS.GetText(MRisk1_1,
										2));
								if (!tLMRiskDB.getInfo()) {
									mErrors.copyAllErrors(tLMRiskDB.mErrors);
									buildError("outputXML", "在取得LMRisk的数据时发生错误");
									return false;
								}
								LMRiskSchema tLMRiskSchema = tLMRiskDB.query()
										.get(1);
								logger.debug(tLMRiskSchema
										.getRiskShortName());
								String[] strc = new String[5];
								strc[0] = "险种" + MRisk1 * MRisk1_1 + ":"; // 主险还是附加险
								strc[1] = tLMRiskSchema.getRiskShortName(); // 险种名称
								strc[2] = temp1SSRS.GetText(MRisk1_1, 2); // 险种代码
								strc[3] = "￥ "
										+ mDecimalFormat.format(new Double(
												temp5SSRS.GetText(MRisk1, 1))); // 健康加费金额
								strc[4] = "";

								logger.debug(temp5SSRS.GetText(MRisk1, 1));
								SumPrem = SumPrem
										+ Double.parseDouble(temp5SSRS.GetText(
												MRisk1, 1));
								logger.debug("SumPrem=" + SumPrem);
								tPremListTable[j + 1].add(strc);
								y++;
							}
						}
					}
				}
			}
		}

		// end of “非联生险”。

		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVV--------被保险人部分(联生险情况)-------START------VVVVVVVVVVVVVVV
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV

		// 第一被保险人职业加费

		else {

			tPremListTable[0] = new ListTable();
			tPremListTable[0].setName("InsuredWork0");
			for (int MRisk2_1 = 1; MRisk2_1 <= temp9SSRS.getMaxRow(); MRisk2_1++) {

				tSql = "select a.riskcode ,(case when sum(b.prem) is not null then sum(b.prem)  else 0 end),b.AddFeeDirect from lcpol a,lcprem b"
						+ " where a.polno='"
						+ "?polno?"
						+ "' and b.polno =a.polno "
						+ " and b.PayPlanCode like '000000%' and AddFeeDirect in ('02','03')"
						+ " and b.PayPlanType='02' group by a.riskcode,b.AddFeeDirect ";
				sqlbv.sql(tSql);
				sqlbv.put("polno", temp9SSRS.GetText(MRisk2_1, 1));
				temp1SSRS = tempExeSQL.execSQL(sqlbv); // 联生主险
				for (int MRisk2 = 1; MRisk2 <= temp1SSRS.getMaxRow(); MRisk2++) {
					if (temp1SSRS != null && temp1SSRS.getMaxRow() > 0
							&& temp1SSRS.getMaxCol() > 0) {
						if (!(temp1SSRS.GetText(MRisk2, 2).equals("0")
								|| temp1SSRS.GetText(MRisk2, 2).trim().equals(
										"") || temp1SSRS.GetText(MRisk2, 2)
								.equals("null"))) {
							Flag[0] = true;

							LMRiskDB t3LMRiskDB = new LMRiskDB();
							LMRiskSchema t3LMRiskSchema = new LMRiskSchema();

							t3LMRiskDB.setRiskCode(temp1SSRS.GetText(MRisk2_1,
									1));
							t3LMRiskSchema = t3LMRiskDB.query().get(1);

							String[] strc = new String[5];
							strc[0] = "险种" + MRisk2 * MRisk2_1 + ":"; // 主险还是附加险
							strc[1] = t3LMRiskSchema.getRiskShortName(); // 险种名称
							strc[2] = temp1SSRS.GetText(MRisk2_1, 1); // 险种代码
							strc[3] = "￥ "
									+ mDecimalFormat.format(new Double(
											temp1SSRS.GetText(MRisk2, 2))); // 健康加费金额
							strc[4] = "";
							if (temp1SSRS.GetText(MRisk2, 3).equals("02")) {
								SumPrem = SumPrem
										+ Double.parseDouble(temp1SSRS.GetText(
												MRisk2, 2));
								logger.debug("SumPrem=" + SumPrem);
							}
							tPremListTable[0].add(strc);
						}
					}
				}
			}

			// 第一被保险人健康加费

			tPremListTable[1] = new ListTable();
			tPremListTable[1].setName("InsuredHealth0");
			for (int MRisk3_1 = 1; MRisk3_1 <= temp9SSRS.getMaxRow(); MRisk3_1++) {

				tSql = "select a.riskcode ,(case when sum(b.prem) is not null then sum(b.prem)  else 0 end),b.AddFeeDirect from lcpol a,lcprem b "
						+ " where a.polno='"
						+ "?polno?"
						+ "'"
						+ " and b.polno =a.polno and b.PayPlanCode like '000000%' and AddFeeDirect in ('02','03')"
						+ " and b.PayPlanType='01'  group by a.riskcode,b.AddFeeDirect ";
				sqlbv.sql(tSql);
				sqlbv.put("polno", temp9SSRS.GetText(MRisk3_1, 1));

				temp3SSRS = tempExeSQL.execSQL(sqlbv);
				for (int MRisk3 = 1; MRisk3 <= temp3SSRS.getMaxRow(); MRisk3++) {
					if (temp3SSRS != null && temp3SSRS.getMaxRow() > 0
							&& temp3SSRS.getMaxCol() > 0) {
						if (!(temp3SSRS.GetText(MRisk3, 2).equals("0")
								|| temp3SSRS.GetText(MRisk3, 2).trim().equals(
										"") || temp3SSRS.GetText(MRisk3, 2)
								.equals("null"))) {
							Flag[1] = true;

							LMRiskDB t4LMRiskDB = new LMRiskDB();
							LMRiskSchema t4LMRiskSchema = new LMRiskSchema();

							t4LMRiskDB
									.setRiskCode(temp3SSRS.GetText(MRisk3, 1));
							t4LMRiskSchema = t4LMRiskDB.query().get(1);

							String[] stre = new String[5];
							stre[0] = "险种" + MRisk3 + ":"; // 主险还是附加险
							stre[1] = t4LMRiskSchema.getRiskShortName(); // 险种名称
							stre[2] = temp3SSRS.GetText(MRisk3, 1); // 险种代码
							stre[3] = "￥ "
									+ mDecimalFormat.format(new Double(
											temp3SSRS.GetText(MRisk3, 2))); // 健康加费金额
							stre[4] = "";
							if (temp3SSRS.GetText(MRisk3, 3).equals("02")) {
								SumPrem = SumPrem
										+ Double.parseDouble(temp3SSRS.GetText(
												MRisk3, 2));
								logger.debug("SumPrem=" + SumPrem);
							}
							tPremListTable[1].add(stre);
						}
					}
				}
			}

			// 第二被保险人职业加费

			tPremListTable[2] = new ListTable();
			tPremListTable[2].setName("InsuredWork1");
			for (int MRisk4_1 = 1; MRisk4_1 <= temp9SSRS.getMaxRow(); MRisk4_1++) {
				tSql = "select a.riskcode ,(case when sum(b.prem) is not null then sum(b.prem)  else 0 end),b.AddFeeDirect from lcpol a,lcprem b"
						+ " where a.polno='"
						+ "?polno?"
						+ "' and b.polno =a.polno and b.PayPlanCode like '000000%' and AddFeeDirect in ('04','03')"
						+ " and b.PayPlanType='02'  group by a.riskcode,b.AddFeeDirect  ";
				sqlbv.sql(tSql);
				sqlbv.put("polno", temp9SSRS.GetText(MRisk4_1, 1));

				temp5SSRS = tempExeSQL.execSQL(sqlbv);
				for (int MRisk4 = 1; MRisk4 <= temp5SSRS.getMaxRow(); MRisk4++) {
					if (temp5SSRS != null && temp5SSRS.getMaxRow() > 0
							&& temp5SSRS.getMaxCol() > 0) {
						if (!(temp5SSRS.GetText(MRisk4, 2).equals("0")
								|| temp5SSRS.GetText(MRisk4, 2).trim().equals(
										"") || temp5SSRS.GetText(MRisk4, 2)
								.equals("null"))) {
							Flag[2] = true;

							LMRiskDB t3LMRiskDB = new LMRiskDB();
							LMRiskSchema t3LMRiskSchema = new LMRiskSchema();

							t3LMRiskDB
									.setRiskCode(temp5SSRS.GetText(MRisk4, 1));
							t3LMRiskSchema = t3LMRiskDB.query().get(1);

							String[] strg = new String[5];
							strg[0] = "险种" + MRisk4 + ":"; // 主险还是附加险
							strg[1] = t3LMRiskSchema.getRiskShortName(); // 险种名称
							strg[2] = temp5SSRS.GetText(MRisk4, 1); // 险种代码
							strg[3] = "￥ "
									+ mDecimalFormat.format(new Double(
											temp5SSRS.GetText(MRisk4, 2))); // 健康加费金额
							strg[4] = "";

							SumPrem = SumPrem
									+ Double.parseDouble(temp5SSRS.GetText(
											MRisk4, 2));
							logger.debug("SumPrem=" + SumPrem);

							tPremListTable[2].add(strg);
						}
					}
				}
			}
			// 第二被保险人健康加费

			tPremListTable[3] = new ListTable();
			tPremListTable[3].setName("InsuredHealth1");
			for (int MRisk5_1 = 1; MRisk5_1 <= temp9SSRS.getMaxRow(); MRisk5_1++) {
				tSql = "select a.riskcode ,(case when sum(b.prem) is not null then sum(b.prem)  else 0 end),b.AddFeeDirect from lcpol a,lcprem b where a.polno='"
						+ "?polno?"
						+ "' and b.polno ='"
						+ "?polno?"
						+ "' and b.PayPlanCode like '000000%' and AddFeeDirect in ('04','03')"
						+ " and b.PayPlanType='01' group by a.riskcode,b.AddFeeDirect  ";
				sqlbv.sql(tSql);
				sqlbv.put("polno", temp9SSRS.GetText(MRisk5_1, 1));
				tempASSRS = tempExeSQL.execSQL(sqlbv);
				for (int MRisk5 = 1; MRisk5 <= tempASSRS.getMaxRow(); MRisk5++) {
					if (tempASSRS != null && tempASSRS.getMaxRow() > 0
							&& tempASSRS.getMaxCol() > 0) {
						if (!(tempASSRS.GetText(MRisk5, 2).equals("0")
								|| tempASSRS.GetText(MRisk5, 2).trim().equals(
										"") || tempASSRS.GetText(MRisk5, 2)
								.equals("null"))) {
							Flag[3] = true;

							LMRiskDB t4LMRiskDB = new LMRiskDB();
							LMRiskSchema t4LMRiskSchema = new LMRiskSchema();

							t4LMRiskDB
									.setRiskCode(tempASSRS.GetText(MRisk5, 1));
							t4LMRiskSchema = t4LMRiskDB.query().get(1);

							String[] stri = new String[5];
							stri[0] = "险种" + MRisk5 + ":"; // 主险还是附加险
							stri[1] = t4LMRiskSchema.getRiskShortName(); // 险种名称
							stri[2] = tempASSRS.GetText(MRisk5, 1); // 险种代码
							stri[3] = "￥ "
									+ mDecimalFormat.format(new Double(
											tempASSRS.GetText(MRisk5, 2))); // 健康加费金额
							stri[4] = "";

							SumPrem = SumPrem
									+ Double.parseDouble(tempASSRS.GetText(
											MRisk5, 2));
							logger.debug("SumPrem=" + SumPrem);

							tPremListTable[3].add(stri);
						}
					}
				}
			}
		}

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("AddReFund.vts", "printer"); // 最好紧接着就初始化xml文档
		String Code = mLOPRTManagerSchema.getCode();
		logger.debug("Code=" + Code);

		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		LCPolSchema mLCPolSchema = new LCPolSchema();
		LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String tSql = "select riskcode from lcpol where contno = '"
				+ "?contno?" + "' and polno = mainpolno";
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
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		String tStrSQL = "select sum(PayMoney) from LJTempFee where "
				+ " (TempFeeType in ('0','6','1')and otherno in (select ContNo from lccont where proposalcontno='"
				+ "?proposalcontno?" + "'))";
		sqlbv2.sql(tStrSQL);
		sqlbv2.put("proposalcontno", mLCContSchema.getProposalContNo());
		ExeSQL tExeSQL = new ExeSQL();
		mRealPayMoney = tExeSQL.getOneValue(sqlbv2);
		double RiskFeeRest = 0;
		String mSumprem = "";
		if (mRealPayMoney != null && mRealPayMoney != "") {
			RiskFeeRest = SumPrem - Double.parseDouble(mRealPayMoney);
			if (RiskFeeRest >= 0) {
				mSumprem = "需缴保费: " + "￥" + mDecimalFormat.format(RiskFeeRest);
			} else {
				mSumprem = "需退保费: " + "￥" + mDecimalFormat.format(-RiskFeeRest);
			}
		} else {
			RiskFeeRest = SumPrem;
			mSumprem = "需缴保费: " + "￥" + mDecimalFormat.format(RiskFeeRest);
		}
		if (RiskFeeRest > 0) {
			xmlexport.addDisplayControl("display");
		}

		tStrSQL = "select BankAccNo from LJTempFeeClass where "
				+ " OtherNo  = '" + "?OtherNo?" + "' ";
		sqlbv2.sql(tStrSQL);
		sqlbv2.put("OtherNo", mLCContSchema.getProposalContNo());
		SSRS MMSSRS = new SSRS();
		MMSSRS = tExeSQL.execSQL(sqlbv2);
		String BankNo = "";
		if (MMSSRS != null && MMSSRS.getMaxRow() > 0) {
			BankNo = MMSSRS.GetText(1, 1);
		}

		String Period = "";
		tSql = "select payintv,payyears from lcpol where contno = '"
				+ "contno" + "' and polno = mainpolno";
		sqlbv1.sql(tSql);
		sqlbv1.put("contno", mLCContSchema.getContNo());
		SSRS PeriodSSRS = new SSRS();
		PeriodSSRS = tempExeSQL.execSQL(sqlbv1);

		if (temp7SSRS.getMaxRow() > 0 || temp8SSRS.getMaxRow() > 0
				|| temp2SSRS.getMaxRow() > 0 || temp5SSRS.getMaxRow() > 0
				|| tempASSRS.getMaxRow() > 0) {
			if (RiskFeeRest > 0) {
				xmlexport.addDisplayControl("displayfee");
				if (PeriodSSRS.GetText(1, 1).equals("0")) {
					Period = "趸交";
				} else {
					Period = PeriodSSRS.GetText(1, 2) + "年";
				}
			}
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

		texttag.add("BarCode1", "UN020");
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
		texttag.add("BankAccount", mLCContSchema.getBankAccNo());
		texttag.add("LCCont.Managecom", tManageComName);
		texttag.add("LCCont.AppntName", mLCContSchema.getAppntName());
		texttag.add("AppntName", mLCContSchema.getAppntName());
		texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());
		texttag.add("LCCont.NewBankAccNo", mLCContSchema.getNewBankAccNo());
		texttag.add("LABranchGroup.Name", getComName(mLCContSchema
				.getManageCom())
				+ " " + getUpComName(mLAAgentSchema.getBranchCode()));
		texttag.add("LAAgent.Name", mLAAgentSchema.getName());
		texttag.add("LAAgent.AgentCode", mLCContSchema.getAgentCode());
		texttag.add("LCContl.UWOperator", mLOPRTManagerSchema.getReqOperator());
		texttag.add("DealContent", mLOPRTManagerSchema.getRemark());
		texttag.add("SumPrem", mSumprem);
		texttag.add("Period", Period);
		String Operator = mLCContSchema.getOperator();
		logger.debug("Operator=" + Operator);
		texttag.add("SysDate", SysDate);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		// 添加被保险人信息到模板
		for (int k = 0, t = 0; k < tempLCInsuredSet.size(); k++, t = t + 2) {
			if (Flag[t] == true) {
				xmlexport.addDisplayControl("display1");
				xmlexport.addDisplayControl("displayInsWork" + k); // 模版上的职业加费部分的控制标记
				xmlexport.addListTable(tPremListTable[t], Title); // 保存职业加费信息
			}
			if (Flag[t + 1] == true) {
				xmlexport.addDisplayControl("display1");
				xmlexport.addDisplayControl("displayInsHealth" + k); // 模版上的健康加费部分的控制标记
				xmlexport.addListTable(tPremListTable[t + 1], Title); // 保存健康加费信息
			}
		}

		// 添加投保人信息到模板
		logger.debug("flagapp1=" + flagapp1);
		logger.debug("flagapp2=" + flagapp2);
		if (flagapp1 == true) {
			xmlexport.addDisplayControl("display1");
			xmlexport.addDisplayControl("displayAppWork"); // 模版上投保人职业加费部分的控制标记
			xmlexport.addListTable(ListTable1, Title); // 保存投保人职业加费信息
		}

		if (flagapp2 == true) {
			xmlexport.addDisplayControl("display1");
			xmlexport.addDisplayControl("displayAppHealth"); // 模版上投保人健康加费部分的控制标记
			xmlexport.addListTable(ListTable2, Title); // 保存投保人健康加费信息
		}

		// 添加现有保额信息到模板
		if (FlagXY) {
			xmlexport.addDisplayControl("Risk"); // 模版上的主险附加险部分的控制标记
			xmlexport.addListTable(ListTable, Title); // 保存主险附加险信息
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
		AddRefundmentBL tAddRefundmentUI = new AddRefundmentBL();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("8100007534433");
		VData tVData = new VData();
		tVData.addElement(tLOPRTManagerSchema);
		tAddRefundmentUI.submitData(tVData, "PRINT");
	}

}
