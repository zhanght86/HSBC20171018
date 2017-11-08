package com.sinosoft.lis.claimnb;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.f1print.PrintTool;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LDComSchema;
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
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 理赔核保加退费通知书打印-----------SpecPrint.vts
 * </p>
 * <p>
 * Description ：打印理赔二核的加退费通知书通知书
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author wanzh 2005/12/01
 * @version 1.0
 */

public class LLUWPAddRefundmentBL {
	private static Logger logger = Logger.getLogger(LLUWPAddRefundmentBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 全局数据 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public LLUWPAddRefundmentBL() {
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
		cError.moduleName = "LLUWPAddFeeBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	private boolean getPrintData() {
		double SumPrem = 0; // 合计保费
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.1 得到打印队列中合同号和节点号信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// 根据印刷号查询打印队列中的纪录
		String PrtNo = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号

		ExeSQL tExeSQL1 = new ExeSQL();
		SSRS tSsrs1 = new SSRS();
		String tSql1 = "select otherno,standbyflag2 from loprtmanager where prtseq = '"
				+ "?PrtNo?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql1);
		sqlbv.put("PrtNo", PrtNo);
		tSsrs1 = tExeSQL1.execSQL(sqlbv);
		if (tSsrs1.getMaxRow() == 0) {
			return false;
		}
		String tContNo = tSsrs1.GetText(1, 1); // 保单号
		String tClmNo = tSsrs1.GetText(1, 2); // 节点号

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.2 得到合同号下的相关信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		ExeSQL tExeSQL3 = new ExeSQL();
		SSRS tSsrs3 = new SSRS();
		String tSql3 = "select proposalcontno,managecom,appntname,insuredname,uwoperator,agentgroup,agentcode,bankaccno,newbankaccno "
				+ " from lccont where contno = '" + "?contno?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql3);
		sqlbv1.put("contno", tContNo);
		tSsrs3 = tExeSQL3.execSQL(sqlbv1);
		if (tSsrs3.getMaxRow() == 0) {
			return false;
		}
		String tProposalContNo = tSsrs3.GetText(1, 1); // 总投保单号
		String tManageComCode = tSsrs3.GetText(1, 2); // 管理结构代码
		String tAppntName = tSsrs3.GetText(1, 3); // 投保人姓名
		String tInsuredName = tSsrs3.GetText(1, 4); // 出险人姓名
		String tUWOperator = tSsrs3.GetText(1, 5); // 核保人姓名
		String tAgentGroup = tSsrs3.GetText(1, 6); // 业务分部记忆分组
		String tAgentCode = tSsrs3.GetText(1, 7); // 业务员代码
		String tBankAccNo = tSsrs3.GetText(1, 8); // 银行帐号
		String tNewBankAccNo = tSsrs3.GetText(1, 9); // 新银行帐号
		// 管理机构名称
		if (tManageComCode.length() > 6)
			tManageComCode = tManageComCode.substring(0, 6);
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(tManageComCode);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tManageCom = tLDComSchema.getName();
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.2.1 开始获取特约保单的销售服务部以及营业部名称
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql21 = "";
		ExeSQL tExeSQL21 = new ExeSQL();
		SSRS tSSRS21 = new SSRS();
		tSql21 = "select name from labranchgroup  where agentgroup = '"
				+ "?agentgroup?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql21);
		sqlbv2.put("agentgroup", tAgentGroup);
		tSSRS21 = tExeSQL21.execSQL(sqlbv2);
		if (tSSRS21.getMaxRow() == 0) {
			return false;
		}
		String tBranchName = tSSRS21.GetText(1, 1); // 营销服务部及营业部
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.2.2 开始获取特约保单的业务员姓名
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql22 = "";
		ExeSQL tExeSQL22 = new ExeSQL();
		SSRS tSSRS22 = new SSRS();
		tSql22 = "select name from laagent  where agentcode = '" + "?agentcode?"
				+ "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql22);
		sqlbv3.put("agentcode", tAgentCode);
		tSSRS22 = tExeSQL22.execSQL(sqlbv3);
		if (tSSRS22.getMaxRow() == 0) {
			return false;
		}
		String tAgentName = tSSRS22.GetText(1, 1); // 营业员姓名
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.3 得到投保人职业加费信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		boolean flagapp1 = false;
		boolean flagapp2 = false;
		String tSql4 = " select a.riskcode,sum(b.prem) from lcpol a ,lluwpremmaster b "
				+ " where b.contno= '"
				+ "?contno?"
				+ "' and b.addfeedirect="
				+ "'01' and b.payplancode like '000000%' and b.payplantype= '02' "
				+ " and a.polno = b.polno and b.clmno = '"
				+ "?clmno?"
				+ "'"
				+ " and b.polno in (select c.polno from lcpol c where c.polno = c.mainpolno"
				+ " and c.contno ='" + "?contno?" + "') group by riskcode ";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql4);
		sqlbv4.put("contno", tContNo);
		sqlbv4.put("clmno", tClmNo);
		ExeSQL tExeSQL4 = new ExeSQL();
		SSRS tSsrs4 = new SSRS(); // 投保人职业加费
		tSsrs4 = tExeSQL4.execSQL(sqlbv4);
		String[] Title = new String[4];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";

		ListTable ListTable1 = new ListTable();
		ListTable1.setName("AppendWork");
		if (tSsrs4 != null && tSsrs4.getMaxRow() > 0 && tSsrs4.getMaxCol() > 0) {
			if (!(tSsrs4.GetText(1, 2).equals("0")
					|| tSsrs4.GetText(1, 2).trim().equals("") || tSsrs4
					.GetText(1, 2).equals("null"))) {
				flagapp1 = true;
				LMRiskDB t1LMRiskDB = new LMRiskDB();
				LMRiskSchema t1LMRiskSchema = new LMRiskSchema();
				t1LMRiskDB.setRiskCode(tSsrs4.GetText(1, 1)); // 职业加费金额
				t1LMRiskSchema = t1LMRiskDB.query().get(1);

				String[] stra = new String[5];
				stra[0] = "主险"; // 主险还是附加险
				stra[1] = t1LMRiskSchema.getRiskName(); // 险种名称
				stra[2] = tSsrs4.GetText(1, 1); // 险种代码
				stra[3] = tSsrs4.GetText(1, 2) + "元"; // 职业加费金额
				stra[4] = "";
				SumPrem = SumPrem + Double.parseDouble(tSsrs4.GetText(1, 2));
				ListTable1.add(stra);
			}
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.4 得到投保人健康加费信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql5 = "select a.riskcode,sum(b.prem) from lcpol a ,lluwpremmaster b "
				+ " where b.contno = '"
				+ "?contno?"
				+ "' and b.addfeedirect = "
				+ "'01' and b.payplancode like '000000%' and b.payplantype = '01' "
				+ " and a.polno = b.polno and b.clmno = '"
				+ "?clmno?"
				+ "'"
				+ " and b.polno in (select c.polno from lcpol c where c.polno = c.mainpolno"
				+ " and c.contno ='" + "?contno?" + "') group by riskcode ";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSql5);
		sqlbv5.put("contno", tContNo);
		sqlbv5.put("clmno", tClmNo);
		ExeSQL tExeSQL5 = new ExeSQL();
		SSRS tSrss5 = new SSRS(); // 投保人健康加费
		tSrss5 = tExeSQL5.execSQL(sqlbv5);

		ListTable ListTable2 = new ListTable();
		ListTable2.setName("AppendHealth");
		if (tSrss5 != null && tSrss5.getMaxRow() > 0 && tSrss5.getMaxCol() > 0) {
			if (!(tSrss5.GetText(1, 2).equals("0")
					|| tSrss5.GetText(1, 2).trim().equals("") || tSrss5
					.GetText(1, 2).equals("null"))) {
				flagapp2 = true;
				LMRiskDB t2LMRiskDB = new LMRiskDB();
				LMRiskSchema t2LMRiskSchema = new LMRiskSchema();
				t2LMRiskDB.setRiskCode(tSrss5.GetText(1, 1));// 健康加费金额
				t2LMRiskSchema = t2LMRiskDB.query().get(1);
				String[] strb = new String[5];
				strb[0] = "主险"; // 主险
				strb[1] = t2LMRiskSchema.getRiskName(); // 险种名称
				strb[2] = tSrss5.GetText(1, 1); // 险种代码
				strb[3] = tSrss5.GetText(1, 2) + "元"; // 健康加费金额
				strb[4] = "";
				SumPrem = SumPrem + Double.parseDouble(tSrss5.GetText(1, 2));
				ListTable2.add(strb);
			}
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.5 判断是否有联生险
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql6 = "select a.polno from lcpol a where a.contno='"
				+ "?contno?"
				+ "' "
				+ " and a.riskcode in (select b.riskcode from lmriskapp b where b.insuredflag ='M'"
				+ " and riskprop = 'I')";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tSql6);
		sqlbv6.put("contno", tContNo);
		ExeSQL tExeSQL6 = new ExeSQL();
		SSRS tSrss6 = new SSRS();
		tSrss6 = tExeSQL6.execSQL(sqlbv6);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.６ 查询该合同下有几个被保险人
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(tContNo);
		LCInsuredSet tempLCInsuredSet = tLCInsuredDB.query();

		int i = 2 * tempLCInsuredSet.size();// 每个被保险人有职业和健康加费两种。
		ListTable[] tPremListTable = new ListTable[i];

		boolean[] Flag = new boolean[i];

		SSRS temp1SSRS = new SSRS(); // 所有主险

		SSRS temp2SSRS = new SSRS(); // 某一主险下的职业加费
		SSRS temp5SSRS = new SSRS(); // 某一主险下的健康加费

		SSRS temp3SSRS = new SSRS(); // 该主险下的所有附加检

		SSRS temp4SSRS = new SSRS(); // 某一附加险下的职业加费
		SSRS temp6SSRS = new SSRS(); // 某一附加险下的健康加费

		SSRS tempASSRS = new SSRS(); // 备用
		SSRS tempBSSRS = new SSRS(); // 备用

		int number, j;
		int x = 0;
		String[][] strIW = new String[20][5];
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.６ 被保人加费部分（多个被保人情况）
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// 该合同号下--没有--联生险
		if (tSrss6.getMaxRow() == 0) {
			for (number = 0, j = 0; number < tempLCInsuredSet.size(); number++, j = j + 2) {
				// 第一被保人
				LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
				mLCInsuredSchema = tempLCInsuredSet.get(number + 1).getSchema();
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.６.1 查询所有主险
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				ExeSQL tExeSQL7 = new ExeSQL();
				String tSql7 = "select polno,riskcode from lcpol where contno ='"
						+ "?contno?"
						+ "'and insuredno ='"
						+ "?insuredno?"
						+ "'and polno = mainpolno";
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(tSql7);
				sqlbv7.put("contno", tContNo);
				sqlbv7.put("insuredno", mLCInsuredSchema.getInsuredNo());
				temp1SSRS = tExeSQL7.execSQL(sqlbv7);
				tPremListTable[j] = new ListTable();
				tPremListTable[j].setName("InsuredWork" + number); // 对应模版被保险人职业加费部分的行对象名

				tPremListTable[j + 1] = new ListTable();
				tPremListTable[j + 1].setName("InsuredHealth" + number); // 对应模版被保险人健康加费部分的行对象名

				for (int MRisk = 0; MRisk < temp1SSRS.getMaxRow(); MRisk++) {
					// 取第一主险
					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.６.1.1
					 * 2-职业加费 PayPlanType = 02
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					ExeSQL tExeSQL8 = new ExeSQL();
					String tPolNo = temp1SSRS.GetText(MRisk + 1, 1); // 险种号
					String tSql8 = "select sum(prem),payplantype from lluwpremmaster "
							+ " where polno='"
							+ "?polno?"
							+ "'and payplancode like '000000%' "
							+ " and payplantype in('02') and clmno = '"
							+ "?clmno?" + "' group by payplantype";
					SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
					sqlbv8.sql(tSql8);
					sqlbv8.put("polno", tPolNo);
					sqlbv8.put("clmno", tClmNo);
					temp2SSRS = tExeSQL8.execSQL(sqlbv8);
					if (temp2SSRS != null && temp2SSRS.getMaxRow() > 0
							&& temp2SSRS.getMaxCol() > 0) {
						if (!(temp2SSRS.GetText(1, 1).equals("0")
								|| temp2SSRS.GetText(1, 1).trim().equals("") || temp2SSRS
								.GetText(1, 1).equals("null"))) {
							Flag[j] = true;

							LMRiskDB tLMRiskDB = new LMRiskDB();
							String tRiskCode = temp1SSRS.GetText(MRisk + 1, 2); // 险种代码
							tLMRiskDB.setRiskCode(tRiskCode);
							if (!tLMRiskDB.getInfo()) {
								mErrors.copyAllErrors(tLMRiskDB.mErrors);
								buildError("outputXML", "在取得LMRisk的数据时发生错误");
								return false;
							}

							LMRiskSchema tLMRiskSchema = tLMRiskDB.getSchema();

							strIW[x][0] = "主险"; // 主险还是附加险
							strIW[x][1] = tLMRiskSchema.getRiskName(); // 险种名称
							strIW[x][2] = temp1SSRS.GetText(MRisk + 1, 2); // 险种代码
							strIW[x][3] = temp2SSRS.GetText(1, 1) + "元"; // 职业加费金额
							strIW[x][4] = "";
							SumPrem = SumPrem
									+ Double.parseDouble(temp2SSRS
											.GetText(1, 1));
							tPremListTable[j].add(strIW[x]);
							x++;
						}
					}

					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.６.1.2 健康加费
					 * PayPlanType = 01
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					ExeSQL tExeSQL9 = new ExeSQL();
					String tSql9 = "select sum(prem),payplantype from lluwpremmaster "
							+ " where polno ='"
							+ "?polno?"
							+ "' and payplancode like '000000%'"
							+ " and payplantype in('01') and clmno = '"
							+ "?clmno?" + "' group by payplantype";
					SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
					sqlbv9.sql(tSql9);
					sqlbv9.put("polno", tPolNo);
					sqlbv9.put("clmno", tClmNo);
					temp5SSRS = tExeSQL9.execSQL(sqlbv9);
					if (temp5SSRS != null && temp5SSRS.getMaxRow() > 0
							&& temp5SSRS.getMaxCol() > 0) {
						if (!(temp5SSRS.GetText(1, 1).equals("0")
								|| temp5SSRS.GetText(1, 1).trim().equals("") || temp5SSRS
								.GetText(1, 1).equals("null"))) {
							Flag[j + 1] = true;
							LMRiskDB tLMRiskDB = new LMRiskDB();
							String tRiskCode = temp1SSRS.GetText(MRisk + 1, 2);
							tLMRiskDB.setRiskCode(tRiskCode);

							if (!tLMRiskDB.getInfo()) {
								mErrors.copyAllErrors(tLMRiskDB.mErrors);
								buildError("outputXML", "在取得LMRisk的数据时发生错误");
								return false;
							}
							LMRiskSchema tLMRiskSchema = tLMRiskDB.getSchema();

							strIW[x][0] = "主险"; // 主险还是附加险
							strIW[x][1] = tLMRiskSchema.getRiskName(); // 险种名称
							strIW[x][2] = tRiskCode; // 险种代码
							strIW[x][3] = temp5SSRS.GetText(1, 1) + "元"; // 健康加费金额
							strIW[x][4] = "";
							SumPrem = SumPrem
									+ Double.parseDouble(temp5SSRS
											.GetText(1, 1));
							tPremListTable[j + 1].add(strIW[x]);
							x++;
						}

					}
					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.６.2
					 * 查询此主险下的附加险 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */

					String tSql10 = " select polno,riskcode from lcpol where mainpolno  = '"
							+ "?polno?" + "' and polno!= mainpolno ";
					SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
					sqlbv10.sql(tSql10);
					sqlbv10.put("polno", tPolNo);
					ExeSQL tExeSQL10 = new ExeSQL();
					temp3SSRS = tExeSQL10.execSQL(sqlbv10);

					for (int ARisk = 0; ARisk < temp3SSRS.getMaxRow(); ARisk++) {
						// 取第一附加险
						/**
						 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.６.2.1
						 * 2-职业加费 PayPlanType = 02
						 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
						 */
						ExeSQL tExeSQL11 = new ExeSQL();
						tPolNo = temp3SSRS.GetText(ARisk + 1, 1); // 附加险险种号
						String tSql11 = "select sum(prem),payplanType from lluwpremmaster where "
								+ " polno ='"
								+ "?polno?"
								+ "' and clmno = '"
								+ "?clmno?"
								+ "' and payplancode  like '000000%'"
								+ " and PayPlanType in('02') group by payplanType";
						SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
						sqlbv11.sql(tSql11);
						sqlbv11.put("polno", tPolNo);
						sqlbv11.put("clmno", tClmNo);
						temp4SSRS = tExeSQL11.execSQL(sqlbv11);
						if (temp4SSRS != null && temp4SSRS.getMaxRow() > 0
								&& temp2SSRS.getMaxCol() > 0) {
							if (!(temp4SSRS.GetText(1, 1).equals("0")
									|| temp4SSRS.GetText(1, 1).trim()
											.equals("") || temp4SSRS.GetText(1,
									1).equals("null"))) {
								Flag[j] = true;
								LMRiskDB tLMRiskDB = new LMRiskDB();
								tLMRiskDB.setRiskCode(temp3SSRS.GetText(
										ARisk + 1, 2));
								if (!tLMRiskDB.getInfo()) {
									mErrors.copyAllErrors(tLMRiskDB.mErrors);
									buildError("outputXML", "在取得LMRisk的数据时发生错误");
									return false;
								}

								LMRiskSchema tLMRiskSchema = tLMRiskDB
										.getSchema();

								strIW[x][0] = "附加险"; // 主险还是附加险
								strIW[x][1] = tLMRiskSchema.getRiskName(); // 险种名称
								strIW[x][2] = temp3SSRS.GetText(ARisk + 1, 2); // 险种代码
								strIW[x][3] = temp4SSRS.GetText(1, 1) + "元"; // 职业加费金额
								strIW[x][4] = "";

								SumPrem = SumPrem
										+ Double.parseDouble(temp4SSRS.GetText(
												1, 1));
								tPremListTable[j].add(strIW[x]);
								x++;
							}
						}
						/**
						 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.６.2.2
						 * 健康加费 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
						 */
						ExeSQL tExeSQL12 = new ExeSQL();
						String tSql12 = "select sum(prem),payplanType from lluwpremmaster where "
								+ " PolNo='"
								+ "?polno?"
								+ "' and clmno = '"
								+ "?clmno?"
								+ "' and PayPlanCode like '000000%'"
								+ " and PayPlanType in('01') group by payplanType";
						SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
						sqlbv12.sql(tSql12);
						sqlbv12.put("polno", tPolNo);
						sqlbv12.put("clmno", tClmNo);
						temp6SSRS = tExeSQL12.execSQL(sqlbv12);

						if (temp6SSRS != null && temp6SSRS.getMaxRow() > 0
								&& temp6SSRS.getMaxCol() > 0) {
							if (!(temp6SSRS.GetText(1, 1).equals("0")
									|| temp6SSRS.GetText(1, 1).trim()
											.equals("") || temp6SSRS.GetText(1,
									1).equals("null"))) {
								Flag[j + 1] = true;
								LMRiskDB tLMRiskDB = new LMRiskDB();
								tLMRiskDB.setRiskCode(temp3SSRS.GetText(
										ARisk + 1, 2));
								if (!tLMRiskDB.getInfo()) {
									mErrors.copyAllErrors(tLMRiskDB.mErrors);
									buildError("outputXML", "在取得LMRisk的数据时发生错误");
									return false;
								}

								LMRiskSchema tLMRiskSchema = tLMRiskDB
										.getSchema();

								strIW[x][0] = "附加险"; // 主险还是加险
								strIW[x][1] = tLMRiskSchema.getRiskName(); // 险种名称
								strIW[x][2] = temp3SSRS.GetText(ARisk + 1, 2); // 险种代码
								strIW[x][3] = temp6SSRS.GetText(1, 1) + "元"; // 健康加费金额
								strIW[x][4] = "";

								SumPrem = SumPrem
										+ Double.parseDouble(temp6SSRS.GetText(
												1, 1));
								tPremListTable[j + 1].add(strIW[x]);
								x++;
							}
						}
					}
				}
			}
		} // end of “非联生险”。

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.7 被保险人部分(联生险情况)
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		else {
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.7.1.1
			 * 第一被保险人职业加费－－主险 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String mPolNo = tSrss6.GetText(1, 1); // 得到险种号
			ExeSQL tExeSQL13 = new ExeSQL();
			String tSql13 = "select a.riskcode ,sum(b.prem),b.AddFeeDirect from lcpol a,lluwpremmaster b "
					+ " where a.polno = '"
					+ "?polno?"
					+ "' and a.polno = a.mainpolno and b.polno ='"
					+ "?polno?"
					+ "'"
					+ " and b.PayPlanCode like '000000%' and AddFeeDirect in ('02','03')"
					+ " and b.PayPlanType ='02' and b.clmno = '"
					+ "?clmno?"
					+ "' group by a.riskcode,b.AddFeeDirect ";
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(tSql13);
			sqlbv13.put("polno", mPolNo);
			sqlbv13.put("clmno", tClmNo);
			temp1SSRS = tExeSQL13.execSQL(sqlbv13);// 联生主险

			tPremListTable[0] = new ListTable();
			tPremListTable[0].setName("InsuredWork0");
			if (temp1SSRS != null && temp1SSRS.getMaxRow() > 0
					&& temp1SSRS.getMaxCol() > 0) {
				if (!(temp1SSRS.GetText(1, 2).equals("0")
						|| temp1SSRS.GetText(1, 2).trim().equals("") || temp1SSRS
						.GetText(1, 2).equals("null"))) {
					Flag[0] = true;

					LMRiskDB t3LMRiskDB = new LMRiskDB();
					LMRiskSchema t3LMRiskSchema = new LMRiskSchema();

					t3LMRiskDB.setRiskCode(temp1SSRS.GetText(1, 1));
					t3LMRiskSchema = t3LMRiskDB.getSchema();

					String[] strc = new String[5];
					strc[0] = "主险"; // 主险还是附加险
					strc[1] = t3LMRiskSchema.getRiskName(); // 险种名称
					strc[2] = temp1SSRS.GetText(1, 1); // 险种代码
					strc[3] = temp1SSRS.GetText(1, 2) + "元"; // 健康加费金额
					strc[4] = "";
					if (temp1SSRS.GetText(1, 3).equals("02")) {
						SumPrem = SumPrem
								+ Double.parseDouble(temp1SSRS.GetText(1, 2));
					}
					tPremListTable[0].add(strc);
				}
			}
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.7.1.2
			 * 第一被保险人职业加费－－附加险 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			ExeSQL tExeSQL14 = new ExeSQL();
			String tSql14 = " select a.riskcode ,sum(b.prem),b.AddFeeDirect from lcpol a,lluwpremmaster b "
					+ " where a.mainpolno='"
					+ "?polno?"
					+ "' and a.polno!=a.mainpolno and a.contno='"
					+ "?contno?"
					+ "'"
					+ " and b.polno =a.polno and b.clmno = '"
					+ "?clmno?"
					+ "' and b.PayPlanCode like '000000%' "
					+ " and b.AddFeeDirect in ('02','03') and b.PayPlanType ='02' group by a.riskcode,b.AddFeeDirect";
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(tSql14);
			sqlbv14.put("polno", mPolNo);
			sqlbv14.put("clmno", tClmNo);
			sqlbv14.put("contno", tContNo);
			temp2SSRS = tExeSQL14.execSQL(sqlbv14); // 该主险下的所有附加险
			if (temp2SSRS != null && temp2SSRS.getMaxRow() > 0
					&& temp2SSRS.getMaxCol() > 0) {
				String[][] strd = new String[20][5];
				for (int f = 0; f < temp2SSRS.getMaxRow(); f++) {
					if (!(temp2SSRS.GetText(f + 1, 2).equals("0")
							|| temp2SSRS.GetText(f + 1, 2).trim().equals("") || temp2SSRS
							.GetText(f + 1, 2).equals("null"))) {

						LMRiskDB t4LMRiskDB = new LMRiskDB();
						LMRiskSchema t4LMRiskSchema = new LMRiskSchema();

						t4LMRiskDB.setRiskCode(temp2SSRS.GetText(f + 1, 1));
						t4LMRiskSchema = t4LMRiskDB.getSchema();

						strd[f][0] = "附加险"; // 主险还是附加险
						strd[f][1] = t4LMRiskSchema.getRiskName(); // 险种名称
						strd[f][2] = temp2SSRS.GetText(f + 1, 1); // 险种代码
						strd[f][3] = temp2SSRS.GetText(f + 1, 2) + "元"; // 健康加费金额
						strd[f][4] = "";
						if (temp2SSRS.GetText(1, 3).equals("02")) {
							SumPrem = SumPrem
									+ Double.parseDouble(temp2SSRS.GetText(
											f + 1, 2));
						}
						tPremListTable[0].add(strd[f]);
					}
				}
			}
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.7.2。1
			 * 第一被保险人健康加费－－主险 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			ExeSQL tExeSQL15 = new ExeSQL();
			String tSql15 = "select a.riskcode ,sum(b.prem),b.AddFeeDirect from lcpol a,lluwpremmaster b "
					+ " where a.polno ='"
					+ "?polno?"
					+ "' and a.polno=a.mainpolno and b.polno ='"
					+ "?polno?"
					+ "' "
					+ " and b.PayPlanCode like '000000%' and AddFeeDirect in ('02','03') and "
					+ " b.PayPlanType ='01' and b.clmno = '"
					+ "?clmno?"
					+ "' group by a.riskcode,b.AddFeeDirect ";
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(tSql15);
			sqlbv15.put("polno", mPolNo);
			sqlbv15.put("clmno", tClmNo);
			temp3SSRS = tExeSQL15.execSQL(sqlbv15);// 联生主险

			tPremListTable[1] = new ListTable();
			tPremListTable[1].setName("InsuredHealth0");
			if (temp3SSRS != null && temp3SSRS.getMaxRow() > 0
					&& temp3SSRS.getMaxCol() > 0) {
				if (!(temp3SSRS.GetText(1, 2).equals("0")
						|| temp3SSRS.GetText(1, 2).trim().equals("") || temp3SSRS
						.GetText(1, 2).equals("null"))) {
					Flag[1] = true;

					LMRiskDB t4LMRiskDB = new LMRiskDB();
					LMRiskSchema t4LMRiskSchema = new LMRiskSchema();

					t4LMRiskDB.setRiskCode(temp3SSRS.GetText(1, 1));
					t4LMRiskSchema = t4LMRiskDB.getSchema();

					String[] stre = new String[5];
					stre[0] = "主险"; // 主险还是附加险
					stre[1] = t4LMRiskSchema.getRiskName(); // 险种名称
					stre[2] = temp3SSRS.GetText(1, 1); // 险种代码
					stre[3] = temp3SSRS.GetText(1, 2) + "元"; // 健康加费金额
					stre[4] = "";
					if (temp3SSRS.GetText(1, 3).equals("02")) {
						SumPrem = SumPrem
								+ Double.parseDouble(temp3SSRS.GetText(1, 2));
					}
					tPremListTable[1].add(stre);
				}
			}
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.7.2。1
			 * 第一被保险人健康加费－－附加险 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			ExeSQL tExeSQL16 = new ExeSQL();
			String tSql16 = " select a.riskcode ,sum(b.prem),b.AddFeeDirect from lcpol a,lluwpremmaster b "
					+ " where a.mainpolno ='"
					+ "?polno?"
					+ "' and a.polno!=a.mainpolno and a.contno='"
					+ "?contno?"
					+ "' "
					+ " and b.polno =a.polno and b.PayPlanCode like '000000%' and b.AddFeeDirect in ('02','03')"
					+ " and b.PayPlanType='01' and b.clmno = '"
					+ "?clmno?"
					+ "' group by a.riskcode,b.AddFeeDirect";
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(tSql16);
			sqlbv16.put("polno", mPolNo);
			sqlbv16.put("contno", tContNo);
			sqlbv16.put("clmno", tClmNo);
			temp4SSRS = tExeSQL16.execSQL(sqlbv16);// 该主险下的所有附加险
			if (temp4SSRS != null && temp4SSRS.getMaxRow() > 0
					&& temp4SSRS.getMaxCol() > 0) {
				String[][] strf = new String[20][5];
				for (int g = 0; g < temp4SSRS.getMaxRow(); g++) {
					if (!(temp4SSRS.GetText(g + 1, 2).equals("0")
							|| temp4SSRS.GetText(g + 1, 2).trim().equals("") || temp4SSRS
							.GetText(g + 1, 2).equals("null"))) {

						LMRiskDB t4LMRiskDB = new LMRiskDB();
						LMRiskSchema t4LMRiskSchema = new LMRiskSchema();

						t4LMRiskDB.setRiskCode(temp4SSRS.GetText(g + 1, 1));
						t4LMRiskSchema = t4LMRiskDB.getSchema();

						strf[g][0] = "附加险"; // 主险还是附加险
						strf[g][1] = t4LMRiskSchema.getRiskName(); // 险种名称
						strf[g][2] = temp4SSRS.GetText(g + 1, 1); // 险种代码
						strf[g][3] = temp4SSRS.GetText(g + 1, 2) + "元"; // 健康加费金额
						strf[g][4] = "";
						if (temp4SSRS.GetText(1, 3).equals("02")) {
							SumPrem = SumPrem
									+ Double.parseDouble(temp4SSRS.GetText(
											g + 1, 2));
						}
						tPremListTable[1].add(strf[g]);
					}
				}
			}
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.7.3.1
			 * 第二被保险人职业加费－－主险 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			ExeSQL tExeSQL17 = new ExeSQL();
			String tSql17 = " select a.riskcode ,sum(b.prem),b.AddFeeDirect from lcpol a,lluwpremmaster b "
					+ " where a.polno='"
					+ "?polno?"
					+ "' and a.polno=a.mainpolno and b.polno ='"
					+ "?polno?"
					+ "'"
					+ " and b.PayPlanCode like '000000%' and AddFeeDirect in ('04','03') and "
					+ " b.PayPlanType='02' and b.clmno = '"
					+ "?clmno?"
					+ "' group by a.riskcode,b.AddFeeDirect ";
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.sql(tSql17);
			sqlbv17.put("polno", mPolNo);
			sqlbv17.put("clmno", tClmNo);
			temp5SSRS = tExeSQL17.execSQL(sqlbv17);// 联生主险
			tPremListTable[2] = new ListTable();
			tPremListTable[2].setName("InsuredWork1");
			if (temp5SSRS != null && temp5SSRS.getMaxRow() > 0
					&& temp5SSRS.getMaxCol() > 0) {
				if (!(temp5SSRS.GetText(1, 2).equals("0")
						|| temp5SSRS.GetText(1, 2).trim().equals("") || temp5SSRS
						.GetText(1, 2).equals("null"))) {
					Flag[2] = true;

					LMRiskDB t3LMRiskDB = new LMRiskDB();
					LMRiskSchema t3LMRiskSchema = new LMRiskSchema();

					t3LMRiskDB.setRiskCode(temp5SSRS.GetText(1, 1));
					t3LMRiskSchema = t3LMRiskDB.getSchema();

					String[] strg = new String[5];
					strg[0] = "主险"; // 主险还是附加险
					strg[1] = t3LMRiskSchema.getRiskName(); // 险种名称
					strg[2] = temp5SSRS.GetText(1, 1); // 险种代码
					strg[3] = temp5SSRS.GetText(1, 2) + "元"; // 健康加费金额
					strg[4] = "";

					SumPrem = SumPrem
							+ Double.parseDouble(temp5SSRS.GetText(1, 2));

					tPremListTable[2].add(strg);
				}
			}
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.7.3.2
			 * 第二被保险人职业加费－－附加险 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			ExeSQL tExeSQL18 = new ExeSQL();
			String tSql18 = " select a.riskcode ,sum(b.prem),b.AddFeeDirect from lcpol a,lluwpremmaster b "
					+ " where a.mainpolno='"
					+ "?polno?"
					+ "' and a.polno!=a.mainpolno and a.contno='"
					+ "?contno?"
					+ "' "
					+ " and b.polno =a.polno and b.PayPlanCode like '000000%' and b.AddFeeDirect in ('04','03')"
					+ " and b.PayPlanType='02' and b.clmno = '"
					+ "?clmno?"
					+ "' group by a.riskcode,b.AddFeeDirect";
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql(tSql18);
			sqlbv18.put("polno", mPolNo);
			sqlbv18.put("contno", tContNo);
			sqlbv18.put("clmno", tClmNo);
			temp6SSRS = tExeSQL18.execSQL(sqlbv18);// 该主险下的所有附加险
			if (temp6SSRS != null && temp6SSRS.getMaxRow() > 0
					&& temp6SSRS.getMaxCol() > 0) {
				String[][] strh = new String[20][5];
				for (int p = 0; p < temp6SSRS.getMaxRow(); p++) {
					if (!(temp6SSRS.GetText(p + 1, 2).equals("0")
							|| temp6SSRS.GetText(p + 1, 2).trim().equals("") || temp6SSRS
							.GetText(p + 1, 2).equals("null"))) {

						LMRiskDB t4LMRiskDB = new LMRiskDB();
						LMRiskSchema t4LMRiskSchema = new LMRiskSchema();

						t4LMRiskDB.setRiskCode(temp6SSRS.GetText(p + 1, 1));
						t4LMRiskSchema = t4LMRiskDB.getSchema();

						strh[p][0] = "附加险"; // 主险还是附加险
						strh[p][1] = t4LMRiskSchema.getRiskName(); // 险种名称
						strh[p][2] = temp6SSRS.GetText(p + 1, 1); // 险种代码
						strh[p][3] = temp6SSRS.GetText(p + 1, 2) + "元"; // 健康加费金额
						strh[p][4] = "";

						SumPrem = SumPrem
								+ Double.parseDouble(temp6SSRS
										.GetText(p + 1, 2));
						tPremListTable[2].add(strh[p]);
					}
				}
			}
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.7.4.1
			 * 第二被保险人健康加费－－主险 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			ExeSQL tExeSQL19 = new ExeSQL();
			String tSql19 = " select a.riskcode ,sum(b.prem),b.AddFeeDirect from lcpol a,lluwpremmaster b "
					+ " where a.polno='"
					+ "?polno?"
					+ "' and a.polno=a.mainpolno and b.polno ='"
					+ "?polno?"
					+ "'"
					+ " and b.PayPlanCode like '000000%' and AddFeeDirect in ('04','03')"
					+ " and b.PayPlanType='01' and b.clmno = '"
					+ "?clmno?"
					+ "' group by a.riskcode,b.AddFeeDirect ";
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			sqlbv19.sql(tSql19);
			sqlbv19.put("polno", mPolNo);
			sqlbv19.put("clmno", tClmNo);
			tempASSRS = tExeSQL19.execSQL(sqlbv19);// 联生主险
			tPremListTable[3] = new ListTable();
			tPremListTable[3].setName("InsuredHealth1");
			if (tempASSRS != null && tempASSRS.getMaxRow() > 0
					&& tempASSRS.getMaxCol() > 0) {
				if (!(tempASSRS.GetText(1, 2).equals("0")
						|| tempASSRS.GetText(1, 2).trim().equals("") || tempASSRS
						.GetText(1, 2).equals("null"))) {
					Flag[3] = true;

					LMRiskDB t4LMRiskDB = new LMRiskDB();
					LMRiskSchema t4LMRiskSchema = new LMRiskSchema();

					t4LMRiskDB.setRiskCode(tempASSRS.GetText(1, 1));
					t4LMRiskSchema = t4LMRiskDB.getSchema();

					String[] stri = new String[5];
					stri[0] = "主险"; // 主险还是附加险
					stri[1] = t4LMRiskSchema.getRiskName(); // 险种名称
					stri[2] = tempASSRS.GetText(1, 1); // 险种代码
					stri[3] = tempASSRS.GetText(1, 2) + "元"; // 健康加费金额
					stri[4] = "";

					SumPrem = SumPrem
							+ Double.parseDouble(tempASSRS.GetText(1, 2));

					tPremListTable[3].add(stri);
				}
			}
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.7.4.1
			 * 第二被保险人健康加费－－附加险 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			ExeSQL tExeSQL20 = new ExeSQL();
			String tSql20 = " select a.riskcode ,sum(b.prem),b.AddFeeDirect from lcpol a,lluwpremmaster b "
					+ " where a.mainpolno='"
					+ "?polno?"
					+ "' and a.polno!=a.mainpolno and a.contno='"
					+ "?contno?"
					+ "'"
					+ " and b.polno =a.polno and b.PayPlanCode like '000000%' and b.AddFeeDirect in ('04','03')"
					+ " and b.PayPlanType='01' and b.clmno = '"
					+ "?clmno?"
					+ "' group by a.riskcode,b.AddFeeDirect";
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.sql(tSql20);
			sqlbv20.put("clmno", tClmNo);
			sqlbv20.put("contno", tContNo);
			sqlbv20.put("polno", mPolNo);
			tempBSSRS = tExeSQL20.execSQL(sqlbv20);// 该主险下的所有附加险
			if (tempBSSRS != null && tempBSSRS.getMaxRow() > 0
					&& tempBSSRS.getMaxCol() > 0) {
				String[][] strj = new String[20][5];
				for (int h = 0; h < tempBSSRS.getMaxRow(); h++) {
					if (!(tempBSSRS.GetText(h + 1, 2).equals("0")
							|| tempBSSRS.GetText(h + 1, 2).trim().equals("") || tempBSSRS
							.GetText(h + 1, 2).equals("null"))) {

						LMRiskDB t4LMRiskDB = new LMRiskDB();
						LMRiskSchema t4LMRiskSchema = new LMRiskSchema();

						t4LMRiskDB.setRiskCode(tempBSSRS.GetText(h + 1, 1));
						t4LMRiskSchema = t4LMRiskDB.getSchema();
						strj[h][0] = "附加险"; // 主险还是附加险
						strj[h][1] = t4LMRiskSchema.getRiskName(); // 险种名称
						strj[h][2] = tempBSSRS.GetText(h + 1, 1); // 险种代码
						strj[h][3] = tempBSSRS.GetText(h + 1, 2) + "元"; // 健康加费金额
						strj[h][4] = "";

						SumPrem = SumPrem
								+ Double.parseDouble(tempBSSRS
										.GetText(h + 1, 2));

						tPremListTable[3].add(strj[h]);
					}
				}
			}
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.8.1 现有保费保额部分--－－主险
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		ExeSQL tExeSQL23 = new ExeSQL();
		String tSql23 = "select (select tt.riskname from lmrisk tt where tt.riskcode = t.RiskCode),t.RiskCode,t.Amnt,t.StandPrem,t.ContNo,t.uwflag from LCPol t where t.PolNo=t.MainPolNo and t.ContNo = '"
				+ "?contno?" + "'";
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		sqlbv21.sql(tSql23);
		sqlbv21.put("contno", tContNo);
		SSRS tSrss23 = new SSRS();

		tSrss23 = tExeSQL23.execSQL(sqlbv21);
		String[][] str = new String[20][5];
		int XY = 0;
		@SuppressWarnings("unused")
		boolean FlagXY = false;
		ListTable ListTable = new ListTable();
		ListTable.setName("RISK");

		for (int ii = 1; ii < tSrss23.getMaxRow() + 1; ii++) {

			if (tSrss23 != null && tSrss23.getMaxRow() > 0
					&& tSrss23.getMaxCol() > 0) {
				if (tSrss23.GetText(ii, 6).equals("1")
						|| tSrss23.GetText(ii, 6).equals("2")
						|| tSrss23.GetText(ii, 6).equals("a")) {
					continue;
				}
				if (!(tSrss23.GetText(ii, 4).equals("0")
						|| tSrss23.GetText(ii, 4).trim().equals("") || tSrss23
						.GetText(ii, 4).equals("null"))) {

					FlagXY = true;
					str[XY][0] = "主险 "; // 主险还是附加险
					str[XY][1] = tSrss23.GetText(ii, 1); // 险种名称
					str[XY][2] = tSrss23.GetText(ii, 2); // 险种代码
					str[XY][3] = tSrss23.GetText(ii, 3) + "元"; // 现有保额
					str[XY][4] = tSrss23.GetText(ii, 4) + "元"; // 应缴保费

					SumPrem = SumPrem
							+ Double.parseDouble(tSrss23.GetText(ii, 4));

					ListTable.add(str[XY]);
				}
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.8.1
				 * 现有保费保额部分--－附加险 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				ExeSQL tExeSQL24 = new ExeSQL();

				String tSql24 = "select (select tt.riskname from lmrisk tt where tt.riskcode = t.RiskCode),"
						+ "t.RiskCode,t.Amnt,t.StandPrem,t.ContNo,t.uwflag from LCPol t where "
						+ "t.PolNo!=t.MainPolNo and t.ContNo = '"
						+ "?ContNo?" + "'";
				SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
				sqlbv22.sql(tSql24);
				sqlbv22.put("ContNo", tSrss23.GetText(ii, 5));
				SSRS tSrss24 = new SSRS();
				tSrss24 = tExeSQL24.execSQL(sqlbv22);

				for (int jj = 1; jj < tSrss24.getMaxRow() + 1; jj++) {
					// 取第一附加险

					if (tSrss24 != null && tSrss24.getMaxRow() > 0
							&& tSrss24.getMaxCol() > 0) {
						if (tSrss24.GetText(jj, 6).equals("1")
								|| tSrss24.GetText(jj, 6).equals("2")
								|| tSrss24.GetText(jj, 6).equals("a")) {
							continue;
						}

						if (!(tSrss24.GetText(jj, 4).equals("0")
								|| tSrss24.GetText(jj, 4).trim().equals("") || tSrss24
								.GetText(jj, 4).equals("null"))) {

							XY++;

							str[XY][0] = "附加险"; // 主险还是附加险
							str[XY][1] = tSrss24.GetText(jj, 1); // 险种名称
							str[XY][2] = tSrss24.GetText(jj, 2); // 险种代码
							str[XY][3] = tSrss24.GetText(jj, 3) + "元"; // 现有保额
							str[XY][4] = tSrss24.GetText(jj, 4) + "元"; // 应缴保费

							SumPrem = SumPrem
									+ Double
											.parseDouble(tSrss24.GetText(jj, 4));
							ListTable.add(str[XY]);
						}
					}
				}

				XY++;
			}
		}

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew tXmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
		// xmlexport.createDocument("LLUWPJTF.vts", "printer"); //
		// 最好紧接着就初始化xml文档
		tXmlExport.createDocument("理赔核保加退费通知书", "", "", "0");
		String Code = mLOPRTManagerSchema.getCode();
		logger.debug("Code=" + Code);

		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.9 实际合计应缴费金额 （应缴费金额－退费金额 ）
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		String tStrSQL = "select sum(PayMoney) from LJTempFee where "
				+ " (TempFeeType in ('0','6','1')and otherno in (select ContNo from lccont where proposalcontno='"
				+ "?ContNo?" + "'))";
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		sqlbv23.sql(tStrSQL);
		sqlbv23.put("ContNo", tProposalContNo);
		ExeSQL tExeSQL = new ExeSQL();
		String mRealPayMoney = tExeSQL.getOneValue(sqlbv23);
		double RiskFeeRest = 0;
		if (mRealPayMoney != null && mRealPayMoney != "") {
			RiskFeeRest = SumPrem - Double.parseDouble(mRealPayMoney);
		} else {
			RiskFeeRest = SumPrem;
		}
		if (RiskFeeRest > 0) {
			tXmlExport.addDisplayControl("display");
		}
		// texttag.add("BarCode1", "UN027"); // 2006-09-06 周磊 修改通知书类别
		// texttag.add("BarCodeParam1","BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		// texttag.add("BarCode2", PrtNo);
		// texttag.add("BarCodeParam2","BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		PrintTool.setBarCode(tXmlExport, PrtNo);

		texttag.add("LCCont.ProposalContNo", tContNo);
		texttag.add("BankAccount", tBankAccNo);
		texttag.add("LCCont.Managecom", tManageCom);
		texttag.add("LCCont.AppntName", tAppntName);
		texttag.add("AppntName", tAppntName);
		texttag.add("LCCont.InsuredName", tInsuredName);
		texttag.add("LCCont.NewBankAccNo", tNewBankAccNo);
		texttag.add("LABranchGroup.Name", tBranchName);
		texttag.add("LAAgent.Name", tAgentName);
		texttag.add("LAAgent.AgentCode", tAgentCode);
		texttag.add("LCContl.UWOperator", tUWOperator);
		texttag.add("DealContent", mLOPRTManagerSchema.getRemark());
		texttag.add("SumPrem", RiskFeeRest);

		texttag.add("SysDate", SysDate);
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}

		for (int k = 0, t = 0; k < tempLCInsuredSet.size(); k++, t = t + 2) {
			if (Flag[t] == true) {
				tXmlExport.addDisplayControl("displayInsWork" + k); // 模版上的职业加费部分的控制标记
				tXmlExport.addListTable(tPremListTable[t], Title); // 保存职业加费信息
			}
			if (Flag[t + 1] == true) {
				tXmlExport.addDisplayControl("displayInsHealth" + k); // 模版上的健康加费部分的控制标记
				tXmlExport.addListTable(tPremListTable[t + 1], Title); // 保存健康加费信息
			}
		}

		// 添加投保人信息到模板
		if (flagapp1 == true) {
			tXmlExport.addDisplayControl("displayAppWork"); // 模版上投保人职业加费部分的控制标记
			tXmlExport.addListTable(ListTable1, Title); // 保存投保人职业加费信息
		}

		if (flagapp2 == true) {
			tXmlExport.addDisplayControl("displayAppHealth"); // 模版上投保人健康加费部分的控制标记
			tXmlExport.addListTable(ListTable2, Title); // 保存投保人健康加费信息
		}

		mResult.clear();
		mResult.addElement(tXmlExport);
		logger.debug("xmlexport=" + tXmlExport);
		return true;

	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("8100000258844");

		TransferData tTransferData = new TransferData();

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		tVData.add(tLOPRTManagerSchema);

		LLUWPAddRefundmentBL tLLUWPAddRefundmentBL = new LLUWPAddRefundmentBL();
		String tOperate = "PRINT";
		if (tLLUWPAddRefundmentBL.submitData(tVData, tOperate) == false) {
			logger.debug("----------理赔核保加费通知书打印出错---------------");
		}

	}

}
