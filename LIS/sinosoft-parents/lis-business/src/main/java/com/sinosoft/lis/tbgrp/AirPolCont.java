package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAirPolForCalDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LACommisionDetailSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAirPolForCalSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCCGrpSpecSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LLAccountSchema;
import com.sinosoft.lis.tb.ContInsuredBL;
import com.sinosoft.lis.tb.ProposalBL;
import com.sinosoft.lis.vschema.LACommisionDetailSet;
import com.sinosoft.lis.vschema.LCAirPolForCalSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDiseaseImpartSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCHistoryImpartSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class AirPolCont {
private static Logger logger = Logger.getLogger(AirPolCont.class);

	/**
	 * @param args
	 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public CErrors mErrors = new CErrors();

	ExeSQL mExeSQL = new ExeSQL();

	String mOperator = "";

	String mRiskCode = "";
	private String tRiskCode;

	MMap mapDel = new MMap();

	LCAirPolForCalSet updateLCAirPolForCalSet = new LCAirPolForCalSet();

	public AirPolCont() {
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.debug(PubFun1.CreateMaxNo("CUSTOMERNO", "SN"));
		logger.debug("@@"+PubFun.getNoLimit("86330000"));
		String tNo = PubFun1.CreateMaxNo("ProposalNo", PubFun.getNoLimit("86330000"));
		logger.debug("tNo="+tNo);
		VData tVData = new VData();
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.ManageCom = "86";
		mGlobalInput.Operator = "001";
		tVData.add(mGlobalInput);
		AirPolCont tAirPolCont = new AirPolCont();
		if (!tAirPolCont.submitData(tVData, "")) {
			logger.debug("错误："
					+ tAirPolCont.mErrors.getError(0).errorMessage);
		}

	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (getInputData(cInputData) == false) {
			return false;
		}
		if (dealData() == false) {
			VData sVData = new VData();
			sVData.add(mapDel);
			PubSubmit ps = new PubSubmit();
			ps.submitData(sVData);
			return false;
		}
		return true;
	}

	/**
	 * 逻辑处理：包括生成投保单信息，处理成功后将待计算保单信息转移到已结算信息表中，最后修改复核状态
	 * 
	 * @return
	 */
	private boolean dealData() {
		//String sql_cal = " select polno from LCAirPolForCal where State='B' and Password='"+ mGlobalInput.Operator+"' order by polno";
		String sql_cal = " select polno from (select polno,RANK () OVER (PARTITION BY insuredname order by polno) rank "
			+ " from LCAirPolForCal where State='B' and Password='"
			+ mGlobalInput.Operator + "' and riskcode='"+tRiskCode+"') a where a.rank = 1 order by polno";
		SSRS tAirPolSSRS = new SSRS();
		tAirPolSSRS = mExeSQL.execSQL(sql_cal);

		LCAirPolForCalDB tLCAirPolForCalDB = new LCAirPolForCalDB();
		LCAirPolForCalSchema tLCAirPolForCalSchema = new LCAirPolForCalSchema();
		tLCAirPolForCalDB.setPolNo(tAirPolSSRS.GetText(1, 1));
		if (!tLCAirPolForCalDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "AirpolCont";
			tError.functionName = "dealData";
			tError.errorMessage = tAirPolSSRS.GetText(1, 1) + "保单查询失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		tLCAirPolForCalSchema = tLCAirPolForCalDB.getSchema();
		mRiskCode = tLCAirPolForCalSchema.getRiskCode();
		logger.debug("RiskCode: " + mRiskCode);
		if (mRiskCode == null || "".equals(mRiskCode)) {
			CError tError = new CError();
			tError.moduleName = "AirpolCont";
			tError.functionName = "dealData";
			tError.errorMessage = tAirPolSSRS.GetText(1, 1) + "险种编码为空！";
			this.mErrors.addOneError(tError);
			return false;
		}

		String tLimit = "";
		tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);

		// 产生印刷号
		String tPrtNo = PubFun1.CreateMaxNo("GRPRPTNO", 14);
		logger.debug("PrtNo: " + tPrtNo);
		try {

			// 集体保单表
			LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema(); // 集体合同信息
			tLCGrpContSchema.setProposalGrpContNo(tPrtNo); // 集体投保单号码
			tLCGrpContSchema.setPrtNo(tPrtNo); // 印刷号码
			tLCGrpContSchema.setManageCom(tLCAirPolForCalSchema.getManageCom()); // 管理机构
			tLCGrpContSchema.setSaleChnl("01"); // 销售渠道
			tLCGrpContSchema.setAgentCom(tLCAirPolForCalSchema.getAgentCom()); // 代理机构
			tLCGrpContSchema.setAgentCode(tLCAirPolForCalSchema.getAgentCode()); // 代理人编码
			String tAgentGroup = tLCAirPolForCalSchema.getAgentGroup();
			if(tAgentGroup == null || tAgentGroup.equals(""))
			{
				String sql="select agentgroup from laagent where agentcode='"+tLCAirPolForCalSchema.getAgentCode()+"'";
				ExeSQL tExeSQL = new ExeSQL();
				tAgentGroup = tExeSQL.getOneValue(sql);
			}
			tLCGrpContSchema.setAgentGroup(tAgentGroup); // 代理人组别
			// 查询代理公司
			String tSql = "select nvl(name,'航意险团单') from lacom where agentcom='"
					+ tLCAirPolForCalSchema.getAgentCom() + "'";
			String tGrpName = mExeSQL.getOneValue(tSql);
			tLCGrpContSchema.setGrpName(tGrpName);
			tLCGrpContSchema.setCValiDate(tLCAirPolForCalSchema.getCValiDate()); // 保单生效日期
			tLCGrpContSchema.setPolApplyDate(tLCAirPolForCalSchema
					.getCValiDate()); // 保单投保日期
			tLCGrpContSchema.setAgentType("01");
			tLCGrpContSchema.setStandbyFlag2("AirPol");

			// 团单投保人信息
			LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema(); // 团单投保人
			// tLCGrpAppntSchema.setGrpContNo(request.getParameter("ProposalGrpContNo"));
			// //集体投保单号码
			// tLCGrpAppntSchema.setCustomerNo(request.getParameter("GrpNo"));
			// //客户号码
			tLCGrpAppntSchema.setPrtNo(tPrtNo); // 印刷号码
			tLCGrpAppntSchema.setName(tGrpName);

			// 团体客户信息 LDGrp
			LDGrpSchema tLDGrpSchema = new LDGrpSchema(); // 团体客户
			tLDGrpSchema.setGrpName(tGrpName); // 单位名称

			LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema(); // 团体客户地址
			LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet(); // 客户告知
			LCHistoryImpartSet tLCHistoryImpartSet = new LCHistoryImpartSet(); // 既往告知
			// //既往告知
			LCDiseaseImpartSet tLCDiseaseImpartSet = new LCDiseaseImpartSet(); // 严重疾病告知
			LCCGrpSpecSchema tLCCGrpSpecSchema = new LCCGrpSpecSchema();
			// 保单发佣分配表
			LACommisionDetailSet tLACommisionDetailSet = new LACommisionDetailSet();
			LACommisionDetailSchema tLACommisionDetailSchema = new LACommisionDetailSchema();
			tLACommisionDetailSchema.setAgentCode(tLCAirPolForCalSchema
					.getAgentCode());
			tLACommisionDetailSchema.setAgentGroup(tAgentGroup);
			tLACommisionDetailSchema.setPolType("0");
			tLACommisionDetailSchema.setBusiRate(1);
			tLACommisionDetailSet.add(tLACommisionDetailSchema);

			VData tVData = new VData();
			tVData.add(tLCGrpContSchema);
			tVData.add(tLCGrpAppntSchema);
			tVData.add(tLDGrpSchema);
			tVData.add(tLCGrpAddressSchema);
			tVData.add(tLCCustomerImpartSet); // 团体告知信息
			tVData.add(tLCHistoryImpartSet); // 既往告知
			tVData.add(tLCDiseaseImpartSet); // 严重疾病告知
			tVData.add(tLACommisionDetailSet);
			tVData.add(tLCCGrpSpecSchema);
			tVData.add(mGlobalInput);
			// 传递非SCHEMA信息
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("SavePolType", "0"); // 保全保存标记，默认为0，标识非保全
			tVData.addElement(tTransferData);

			GroupContUI tGroupContUI = new GroupContUI();
			if (tGroupContUI.submitData(tVData, "INSERT||GROUPPOL") == false) {
				CError tError = new CError();
				tError.moduleName = "AirpolCont";
				tError.functionName = "dealData";
				tError.errorMessage = " 保存失败，原因是: "
						+ tGroupContUI.mErrors.getError(0).errorMessage;
				this.mErrors.addOneError(tError);
				prepareDelMap(tPrtNo);
				return false;
			} else {
				tVData.clear();
				tVData = tGroupContUI.getResult();
				LCGrpContSchema ttLCGrpContSchema = new LCGrpContSchema();
				LCGrpAppntSchema ttLCGrpAppntSchema = new LCGrpAppntSchema();
				ttLCGrpContSchema.setSchema((LCGrpContSchema) tVData
						.getObjectByObjectName("LCGrpContSchema", 0));
				ttLCGrpAppntSchema.setSchema((LCGrpAppntSchema) tVData
						.getObjectByObjectName("LCGrpAppntSchema", 0));

				LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
				LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
				tLCGrpPolSchema.setGrpContNo(ttLCGrpContSchema.getGrpContNo());
				tLCGrpPolSchema.setPrtNo(ttLCGrpContSchema.getPrtNo());
				tLCGrpPolSchema.setManageCom(tLCAirPolForCalSchema
						.getManageCom());
				tLCGrpPolSchema.setSaleChnl("01");
				tLCGrpPolSchema
						.setAgentCom(tLCAirPolForCalSchema.getAgentCom());
				tLCGrpPolSchema.setAgentCode(tLCAirPolForCalSchema
						.getAgentCode());
				tLCGrpPolSchema.setAgentGroup(tAgentGroup);
				tLCGrpPolSchema.setRiskCode(mRiskCode);
				tLCGrpPolSchema.setCValiDate(tLCAirPolForCalSchema
						.getCValiDate());
				tLCGrpPolSchema.setPayIntv("0");
				tLCGrpPolSchema.setStandbyFlag2("AirPol");
				tLCGrpPolSchema.setAgentType("01");
				mLCGrpPolSet.add(tLCGrpPolSchema);

				tVData.clear();
				tVData.add(mLCGrpPolSet);
				tVData.add(ttLCGrpContSchema);
				tVData.add(mGlobalInput);
				GroupRiskUI tGroupRiskUI = new GroupRiskUI();
				if (tGroupRiskUI.submitData(tVData, "INSERT||GROUPRISK") == false) {
					this.mErrors.copyAllErrors(tGroupRiskUI.mErrors);
					prepareDelMap(tPrtNo);
					return false;
				} else {
					// 开始添加被保人信息
					for (int i = 1; i <= tAirPolSSRS.getMaxRow(); i++) {
						tLCAirPolForCalDB = new LCAirPolForCalDB();
						tLCAirPolForCalSchema = new LCAirPolForCalSchema();
						tLCAirPolForCalDB.setPolNo(tAirPolSSRS.GetText(i, 1));
						if (!tLCAirPolForCalDB.getInfo()) {
							CError tError = new CError();
							tError.moduleName = "AirpolCont";
							tError.functionName = "dealData";
							tError.errorMessage = tAirPolSSRS.GetText(i, 1)
									+ "保单查询失败！";
							this.mErrors.addOneError(tError);
							return false;
						}

						tLCAirPolForCalSchema = tLCAirPolForCalDB.getSchema();
						String tRiskCode = tLCAirPolForCalSchema.getRiskCode();
						logger.debug("RiskCode: " + tRiskCode);
						if (tRiskCode == null || "".equals(tRiskCode)) {
							CError tError = new CError();
							tError.moduleName = "AirpolCont";
							tError.functionName = "dealData";
							tError.errorMessage = tAirPolSSRS.GetText(i, 1)
									+ "险种编码为空！";
							this.mErrors.addOneError(tError);
							return false;
						}

						LCContSchema tLCContSchema = new LCContSchema();
						LDPersonSchema tLDPersonSchema = new LDPersonSchema();
						LCAddressSchema tLCAddressSchema = new LCAddressSchema();
						LCInsuredSchema tmLCInsuredSchema = new LCInsuredSchema();
						LCCustomerImpartSet ttLCCustomerImpartSet = new LCCustomerImpartSet();
						LLAccountSchema tLLAccountSchema = new LLAccountSchema();
						tTransferData = new TransferData();
						ContInsuredBL tContInsuredBL = new ContInsuredBL();

						logger.debug("ttLCGrpContSchema.getGrpContNo()="
								+ ttLCGrpContSchema.getGrpContNo());
						tLCContSchema.setGrpContNo(ttLCGrpContSchema
								.getGrpContNo());
						tLCContSchema.setPrtNo(tPrtNo);
						tLCContSchema.setManageCom(ttLCGrpContSchema
								.getManageCom());
						tLCContSchema.setContNo(tLCAirPolForCalSchema
								.getPolNo());
						tLCContSchema.setProposalContNo(tLCAirPolForCalSchema
								.getPolNo());
						tLCContSchema.setAgentCode(ttLCGrpContSchema
								.getAgentCode());
						tLCContSchema.setAgentGroup(ttLCGrpContSchema
								.getAgentGroup());
						tLCContSchema.setAgentCom(ttLCGrpContSchema
								.getAgentCom());
						tLCContSchema.setAgentType(ttLCGrpContSchema
								.getAgentType());
						tLCContSchema.setSaleChnl(ttLCGrpContSchema
								.getSaleChnl());
						tLCContSchema.setSignCom(ttLCGrpContSchema
								.getManageCom());
						tLCContSchema
								.setAppntNo(ttLCGrpContSchema.getAppntNo());
						tLCContSchema.setAppntName(ttLCGrpContSchema
								.getGrpName());
						tLCContSchema.setInputDate(PubFun.getCurrentDate());
						tLCContSchema.setInputTime(PubFun.getCurrentTime());
						tLCContSchema.setPolApplyDate(ttLCGrpContSchema
								.getCValiDate());
						tLCContSchema.setCValiDate(tLCAirPolForCalSchema.getCValiDate()); // 保单生效日期
						tLCContSchema.setContType("2");// 团单
						tLCContSchema.setInsuredNo("0"); // 暂设
						tLCContSchema.setPolType("0");
						tLCContSchema.setPeoples(1);
						tLCContSchema.setCardFlag("0");
						tLCContSchema.setAppFlag("0");
						tLCContSchema.setState("0");
						tLCContSchema.setApproveFlag("0");
						tLCContSchema.setUWFlag("0");
						tLCContSchema.setOperator(mGlobalInput.Operator);
						tLCContSchema.setMakeDate(PubFun.getCurrentDate());
						tLCContSchema.setMakeTime(PubFun.getCurrentTime());
						tLCContSchema.setAgentType("01");

						String tBirthday = "";
						if (tLCAirPolForCalSchema.getInsuredBirthday() != null
								&& !tLCAirPolForCalSchema.getInsuredBirthday()
										.equals("")) {
							tBirthday = tLCAirPolForCalSchema
									.getInsuredBirthday();
						} else
							tBirthday = "1900-01-01";

						String tSex = "";
						if (tLCAirPolForCalSchema.getInsuredSex() != null
								&& !tLCAirPolForCalSchema.getInsuredSex()
										.equals("")) {
							tSex = tLCAirPolForCalSchema.getInsuredSex();
						} else
							tSex = "2";

						tmLCInsuredSchema.setRelationToMainInsured("00");
						tmLCInsuredSchema.setPrtNo(tPrtNo);
						tmLCInsuredSchema.setGrpContNo(ttLCGrpContSchema
								.getGrpContNo());
						tmLCInsuredSchema.setManageCom(mGlobalInput.ManageCom);
						tmLCInsuredSchema.setExecuteCom(mGlobalInput.ManageCom);
						tmLCInsuredSchema.setName(tLCAirPolForCalSchema
								.getInsuredName());
						tmLCInsuredSchema.setSex(tSex);

						tmLCInsuredSchema.setBirthday(tBirthday);
						tmLCInsuredSchema.setIDType(tLCAirPolForCalSchema
								.getInsuredIDType());
						tmLCInsuredSchema.setIDNo(tLCAirPolForCalSchema
								.getInsuredIDNo());
						tmLCInsuredSchema
								.setOccupationType(tLCAirPolForCalSchema
										.getOccupationType());
						tmLCInsuredSchema.setContPlanCode("");
						tmLCInsuredSchema.setContNo(tLCAirPolForCalSchema
								.getPolNo());

						tLDPersonSchema.setName(tLCAirPolForCalSchema
								.getInsuredName());
						tLDPersonSchema.setSex(tSex);
						tLDPersonSchema.setBirthday(tBirthday);
						tLDPersonSchema.setIDType(tLCAirPolForCalSchema
								.getInsuredIDType());
						tLDPersonSchema.setIDNo(tLCAirPolForCalSchema
								.getInsuredIDNo());
						tLDPersonSchema.setOccupationType(tLCAirPolForCalSchema
								.getOccupationType());

						tTransferData = new TransferData();
						tTransferData.setNameAndValue("PolTypeFlag", "0");// 个人单
						tTransferData.setNameAndValue("ContType", "2");// 团单
						tTransferData.setNameAndValue("SavePolType", "0");// 保全保存标记，默认为0，标识非保全
						tTransferData.setNameAndValue("DiskImportFlag", "1");

						tVData.clear();
						tVData.add(tLCContSchema);
						tVData.add(tLDPersonSchema);
						tVData.add(ttLCCustomerImpartSet);
						tVData.add(tmLCInsuredSchema);
						tVData.add(tLCAddressSchema);
						tVData.add(tLLAccountSchema);
						tVData.add(tTransferData);
						tVData.add(mGlobalInput);

						if (tContInsuredBL.submitData(tVData,
								"INSERT||CONTINSURED") == false) {
							this.mErrors.copyAllErrors(tContInsuredBL.mErrors);
							prepareDelMap(tPrtNo);
							return false;
						} else {
							tVData.clear();
							tVData = tContInsuredBL.getResult();
							LCContSchema mLCContSchema = new LCContSchema();
							LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
							mLCContSchema.setSchema((LCContSchema) tVData
									.getObjectByObjectName("LCContSchema", 0));
							mLCInsuredSchema
									.setSchema((LCInsuredSchema) tVData
											.getObjectByObjectName(
													"LCInsuredSchema", 0));

							LCPolSchema tLCPolSchema = new LCPolSchema();
							tLCPolSchema.setGrpContNo(mLCContSchema
									.getGrpContNo());
							tLCPolSchema.setPrtNo(tPrtNo);
							tLCPolSchema.setManageCom(tLCAirPolForCalSchema
									.getManageCom());
							tLCPolSchema.setSaleChnl("01");
							tLCPolSchema.setAgentCom(tLCAirPolForCalSchema
									.getAgentCom());
							tLCPolSchema.setAgentCode(tLCAirPolForCalSchema
									.getAgentCode());
							tLCPolSchema.setAgentGroup(ttLCGrpContSchema
									.getAgentGroup());
							tLCPolSchema.setPolApplyDate(tLCAirPolForCalSchema
									.getCValiDate());
							tLCPolSchema.setRiskCode(tRiskCode);
							tLCPolSchema.setCValiDate(tLCAirPolForCalSchema
									.getCValiDate());
							tLCPolSchema.setSpecifyValiDate("Y"); // 是否指定生效日期
							tLCPolSchema.setPayMode("1"); // 首期交费方式
							tLCPolSchema.setPayLocation("1"); // 收费方式
							tLCPolSchema.setMult(tLCAirPolForCalSchema
									.getMult()); // 份数
							tLCPolSchema.setPrem(tLCAirPolForCalSchema
									.getPrem()); // 保费
							tLCPolSchema.setAmnt(tLCAirPolForCalSchema
									.getAmnt()); // 保额
							tLCPolSchema.setRnewFlag("-2"); // 非续保
							tLCPolSchema.setFloatRate(1); //
							tLCPolSchema.setContNo(tLCAirPolForCalSchema
									.getPolNo()); // 存储短期险系统中的保单号
							tLCPolSchema.setStandbyFlag1(tLCAirPolForCalSchema
									.getRiskType());// 险种款式
							tLCPolSchema.setStandbyFlag2(tLCAirPolForCalSchema
									.getReason());// 出国事由
							tLCPolSchema.setInsuYear(tLCAirPolForCalSchema
									.getInsuYear());// 保险年期
							tLCPolSchema.setInsuYearFlag(tLCAirPolForCalSchema
									.getInsuYearFlag());// 保险年期单位
							tLCPolSchema.setInsuredBirthday(tBirthday);// 被保险人生日
							tLCPolSchema.setInsuredSex(tSex);// 被保险人性别
							tLCPolSchema.setInsuredName(tLCAirPolForCalSchema
									.getInsuredName());
							tLCPolSchema.setAppntName(tGrpName);
							tLCPolSchema.setInsuredPeoples("1");
							tLCPolSchema.setAgentType("01");

							logger.debug("end LCPolSchema "+tLCPolSchema.getStandbyFlag1());

							LCDutySchema tLCDutySchema = new LCDutySchema();

							tLCDutySchema.setInsuYear(tLCAirPolForCalSchema
									.getInsuYear()); // 保险期间
							tLCDutySchema.setInsuYearFlag(tLCAirPolForCalSchema
									.getInsuYearFlag());
							tLCDutySchema.setContNo(tLCAirPolForCalSchema
									.getPolNo());
							tLCDutySchema.setStandbyFlag1(tLCAirPolForCalSchema
									.getRiskType());// 险种款式
							if("211609".equals(tLCAirPolForCalSchema.getRiskCode()))
							{
								tLCDutySchema.setCalRule("3");//信贷无忧保额保费一定
							}

							logger.debug("end LCDutySchema ");

							LCBnfSet tLCBnfSet = new LCBnfSet();
							LCBnfSchema tLCBnfSchema = new LCBnfSchema();

							if (tLCAirPolForCalSchema.getBnfName() != null
									&& !"".equals(tLCAirPolForCalSchema
											.getBnfName())) {
								tLCBnfSchema.setBnfType("1");
								tLCBnfSchema.setName(tLCAirPolForCalSchema
										.getBnfName());
								tLCBnfSchema
										.setRelationToInsured(tLCAirPolForCalSchema
												.getRelationToInsured());
								tLCBnfSchema.setZipCode(tLCAirPolForCalSchema
										.getBnfZipCode());
								tLCBnfSchema.setBnfGrade("1");
								tLCBnfSet.add(tLCBnfSchema);
							}
							tTransferData = new TransferData();
							tTransferData
									.setNameAndValue("ChangePlanFlag", "1");

							tVData = new VData();
							tVData.addElement(mLCContSchema);
							tVData.addElement(mLCInsuredSchema);
							tVData.addElement(ttLCGrpAppntSchema);
							tVData.addElement(tLCPolSchema);
							tVData.addElement(tLCBnfSet);
							tVData.addElement(tLCDutySchema);
							tVData.addElement(mGlobalInput);
							tVData.addElement(tTransferData);

							ProposalBL mProposalBL = new ProposalBL();
							if (!mProposalBL.submitData(tVData,
									"INSERT||PROPOSAL")) {
								this.mErrors.copyAllErrors(mProposalBL.mErrors);
								prepareDelMap(tPrtNo);
								return false;
							}
						}
						tLCAirPolForCalSchema.setState("C");
						tLCAirPolForCalSchema.setModifyDate(PubFun.getCurrentDate());
						tLCAirPolForCalSchema.setModifyTime(PubFun.getCurrentTime());
						updateLCAirPolForCalSet.add(tLCAirPolForCalSchema);
					}
					mapDel = new MMap();
					mapDel.put(updateLCAirPolForCalSet, "UPDATE");
					VData sVData = new VData();
					sVData.add(mapDel);
					PubSubmit ps = new PubSubmit();
					ps.submitData(sVData);
				}
			}

			// 处理完毕，将LCAirPolForCal中的信息转移到LBAirPol中；
			MMap tMMap = new MMap();
			String sql_insert1 = " insert into LBAirPol select * from LCAirPolForCal where State = 'C' and Password = '"
					+ mGlobalInput.Operator + "' ";
			// 将处理不成功的state改为A，仍然保存在LCAirPol中
//			String sql_update = " update LCAirPolForCal set state='A' where state='B' and Password = '"
//					+ mGlobalInput.Operator + "' ";
			String sql_insert2 = " insert into LCAirPol select * from LCAirPolForCal where State = 'B' and Password = '"
					+ mGlobalInput.Operator + "' ";
			String sql_delete = " delete from LCAirPolForCal where State in('B','C') and Password = '"
					+ mGlobalInput.Operator + "' ";
			tMMap.put(sql_insert1, "INSERT");
//			tMMap.put(sql_update, "UPDATE");
			tMMap.put(sql_insert2, "INSERT");
			tMMap.put(sql_delete, "DELETE");
			VData tempVData = new VData();
			tempVData.add(tMMap);
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tempVData)) {
				CError tError = new CError();
				tError.moduleName = "AirpolCont";
				tError.functionName = "dealData";
				tError.errorMessage = "保单信息转移到LBAirPol时出错！";
				this.mErrors.addOneError(tError);
			}

			// 将复核状态、自核状态都置为核保通过的标志
			tMMap = new MMap();
			tMMap.put("update lcpol set Approveflag='9',Approvecode='"
					+ mGlobalInput.Operator + "',ApproveDate='"
					+ PubFun.getCurrentDate() + "',ApproveTime='"
					+ PubFun.getCurrentTime() + "',UWFlag='9',UWCode='"
					+ mGlobalInput.Operator + "',UWDate='"
					+ PubFun.getCurrentDate() + "',UWTime='"
					+ PubFun.getCurrentTime() + "' where prtno='" + tPrtNo
					+ "'", "UPDATE");
			tMMap.put("update lccont set Approveflag='9',Approvecode='"
					+ mGlobalInput.Operator + "',ApproveDate='"
					+ PubFun.getCurrentDate() + "',ApproveTime='"
					+ PubFun.getCurrentTime() + "',UWFlag='9',UWOperator='"
					+ mGlobalInput.Operator + "',UWDate='"
					+ PubFun.getCurrentDate() + "',UWTime='"
					+ PubFun.getCurrentTime() + "' where prtno='" + tPrtNo
					+ "'", "UPDATE");
			tMMap.put("update lcgrppol set Approveflag='9',Approvecode='"
					+ mGlobalInput.Operator + "',ApproveDate='"
					+ PubFun.getCurrentDate() + "',ApproveTime='"
					+ PubFun.getCurrentTime() + "',UWFlag='9',UWOperator='"
					+ mGlobalInput.Operator + "',UWDate='"
					+ PubFun.getCurrentDate() + "',UWTime='"
					+ PubFun.getCurrentTime() + "' where prtno='" + tPrtNo
					+ "'", "UPDATE");
			tMMap.put("update LCGrpCont set Approveflag='9',Approvecode='"
					+ mGlobalInput.Operator + "',ApproveDate='"
					+ PubFun.getCurrentDate() + "',ApproveTime='"
					+ PubFun.getCurrentTime() + "',UWFlag='9',UWOperator='"
					+ mGlobalInput.Operator + "',UWDate='"
					+ PubFun.getCurrentDate() + "',UWTime='"
					+ PubFun.getCurrentTime() + "' where prtno='" + tPrtNo
					+ "'", "UPDATE");
			tempVData = new VData();
			tempVData.add(tMMap);
			tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tempVData)) {
				CError tError = new CError();
				tError.moduleName = "AirpolCont";
				tError.functionName = "dealData";
				tError.errorMessage = "修改符合信息时报错！";
				this.mErrors.addOneError(tError);
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			prepareDelMap(tPrtNo);
			CError tError = new CError();
			tError.moduleName = "AirpolCont";
			tError.functionName = "dealData";
			tError.errorMessage = ex.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("~~~~~~~~~~~deal successful~~~~~~~~~~");
		return true;
	}

	/**
	 * 处理过程出错，需要回滚数据
	 * 
	 * @param tPrtNo
	 */
	private void prepareDelMap(String tPrtNo) {

		mapDel = new MMap();
		mapDel
				.put(
						"delete from LACommisionDetail where grpcontno =(select grpcontno from lcgrpcont where prtno='"
								+ tPrtNo + "')", "DELETE");
		mapDel
				.put(
						"delete from lcinsured where grpcontno =(select grpcontno from lcgrpcont where prtno='"
								+ tPrtNo + "')", "DELETE");
		mapDel.put("delete from LCGrpAppnt where prtno='" + tPrtNo + "'",
				"DELETE");
		mapDel.put("delete from lcgrppol where prtno='" + tPrtNo + "'",
				"DELETE");

		mapDel.put("delete from lcpol where prtno='" + tPrtNo + "'", "DELETE");
		mapDel
				.put(
						"delete from lcprem where grpcontno =(select grpcontno from lcgrpcont where prtno='"
								+ tPrtNo + "')", "DELETE");
		mapDel
				.put(
						"delete from lcget where grpcontno =(select grpcontno from lcgrpcont where prtno='"
								+ tPrtNo + "')", "DELETE");
		mapDel.put(
				"delete from lcduty where contno in (select contno from lccont where prtno='"
						+ tPrtNo + "')", "DELETE");
		mapDel.put("delete from lccont where prtno='" + tPrtNo + "'", "DELETE");
		mapDel.put("delete from LCGrpCont where prtno='" + tPrtNo + "'",
				"DELETE");
		mapDel
				.put(
						"insert into LCAirPol select * from LCAirPolForCal where State = 'B' and Password = '"
								+ mGlobalInput.Operator + "'", "INSERT");
		mapDel.put(
				"delete from LCAirPolForCal where State = 'B' and Password = '"
						+ mGlobalInput.Operator + "'", "DELETE");

	}

	/**
	 * 获取外部传入参数
	 * 
	 * @return
	 */
	private boolean getInputData(VData cInputData) {
		// TODO Auto-generated method stub
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		TransferData mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
		tRiskCode=(String)mTransferData.getValueByName("RiskCode");
		return true;
	}

}
