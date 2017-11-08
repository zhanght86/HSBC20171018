package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;


import com.sinosoft.lis.db.LCAirPolDB;
import com.sinosoft.lis.db.LCGrpAirContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LACommisionDetailSchema;
import com.sinosoft.lis.schema.LBAirPolSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAirPolSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCCGrpSpecSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpAirContSchema;
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
import com.sinosoft.lis.vschema.LCAirPolSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDiseaseImpartSet;
import com.sinosoft.lis.vschema.LCGrpAirContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCHistoryImpartSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class AirGrpPolContBL {
private static Logger logger = Logger.getLogger(AirGrpPolContBL.class);

	/**
	 * @param args
	 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public CErrors mErrors = new CErrors();
	private LCGrpAirContSet mLCGrpAirContSet= new LCGrpAirContSet();
	private LCGrpAirContSchema mLCGrpAirContSchema;
	private LCAirPolSet mLCAirPolSet = new LCAirPolSet();
	private String mGrpContNo; //团险合同号
	private MMap map = new MMap();
	public AirGrpPolContBL() {
	}



	public boolean submitData(VData cInputData, String cOperate) {
		try
		{
			if (!getInputData(cInputData)) 
			{
				CError.buildErr(this, "获取数据失败");
				return false;
			}
			
			if(!checkData())
			{
				CError.buildErr(this, "数据校验失败");
				return false;
			}
			if (!dealData()) {
	
				CError.buildErr(this, "业务处理失败");
				return false;
			}
			
			VData sVData = new VData();
			sVData.add(map);
			PubSubmit ps = new PubSubmit();
			ps.submitData(sVData);
		}
		catch(Exception ex)
		{
			CError.buildErr(this, ex.toString());
			return false;
		}
		return true;
	}

	/**
	 * 获取外部传入参数
	 * 
	 * @return
	 */
	private boolean getInputData(VData cInputData) {

		logger.debug("--getInputData--");
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		mLCGrpAirContSet = (LCGrpAirContSet) cInputData.getObjectByObjectName("LCGrpAirContSet", 0);
		if (mGlobalInput == null) {
			mErrors.addOneError(new CError("获取全局量信息失败"));
			return false;
		}
		if (mLCGrpAirContSet == null) {
			mErrors.addOneError(new CError("获取航意险团单数据失败"));
			return false;
		}
		return true;
	}
	
	//进行数据校验
	private boolean checkData() {
		logger.debug("---checkData--");
		//进行团单信息的校验
		mGrpContNo = "";
		mGrpContNo = mLCGrpAirContSet.get(1).getGrpContNo(); //由于前台选择保单为单选,所以该处直接获取第一条记录
		LCGrpAirContDB mLCGrpAirContDB = new LCGrpAirContDB();
		mLCGrpAirContDB.setGrpContNo(mGrpContNo);
		if(!mLCGrpAirContDB.getInfo())
		{
			CError.buildErr(this, "短期险平台不存在团体合同"+mGrpContNo+"相关信息,请核实");
			return false;
		}
		else
		{
			mLCGrpAirContSchema= new LCGrpAirContSchema();
			mLCGrpAirContSchema=mLCGrpAirContDB.getSchema();
			if("C".equals(mLCGrpAirContSchema.getState()))
			{
				CError.buildErr(this, "团单"+mLCGrpAirContSchema.getGrpContNo()+"已经结算,请核实");
				return false;
			}
		}
		
		//进行个险信息的校验
		String mSql = "select * from lcairpol where StandByFlag2='"+mGrpContNo+"'";
		logger.debug("---查询团险下的个单信息-----"+mSql);
		LCAirPolDB mLCAirPolDB = new LCAirPolDB();
		mLCAirPolSet = new LCAirPolSet();
		mLCAirPolSet = mLCAirPolDB.executeQuery(mSql);
		if(mLCAirPolSet.size()<=0 || mLCAirPolSet==null)
		{
			CError.buildErr(this, "短期险平台团单"+mGrpContNo+"不存在相应的个单信息");
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
		logger.debug("----dealData---");
		// 集体保单表
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema(); // 集体合同信息
		tLCGrpContSchema.setGrpContNo(mLCGrpAirContSchema.getGrpContNo()); //集体合同号
		tLCGrpContSchema.setProposalGrpContNo(mLCGrpAirContSchema.getGrpContNo()); // 集体投保单号码
		tLCGrpContSchema.setPrtNo(mLCGrpAirContSchema.getPrtNo()); // 印刷号码
		tLCGrpContSchema.setManageCom(mLCGrpAirContSchema.getManageCom()); // 管理机构
		tLCGrpContSchema.setSaleChnl("01"); // 销售渠道
		tLCGrpContSchema.setAgentCom(mLCGrpAirContSchema.getAgentCom()); // 代理机构
		tLCGrpContSchema.setAgentCode(mLCGrpAirContSchema.getAgentCode()); // 代理人编码
		String tAgentGroup = mLCGrpAirContSchema.getAgentGroup();
		if(tAgentGroup == null || tAgentGroup.equals(""))
		{
			String sql="select agentgroup from laagent where agentcode='"+mLCGrpAirContSchema.getAgentCode()+"'";
			ExeSQL tExeSQL = new ExeSQL();
			tAgentGroup = tExeSQL.getOneValue(sql);
		}
		tLCGrpContSchema.setAgentGroup(tAgentGroup); // 代理人组别
		tLCGrpContSchema.setGrpName(mLCGrpAirContSchema.getGrpName());
		tLCGrpContSchema.setCValiDate(mLCGrpAirContSchema.getCValiDate()); // 保单生效日期
		tLCGrpContSchema.setPolApplyDate(mLCGrpAirContSchema.getCValiDate()); // 保单投保日期
		tLCGrpContSchema.setAgentType("01");
		tLCGrpContSchema.setStandbyFlag2("AirPol");
		logger.debug("---end prepare LCGrpCont---");

		// 团单投保人信息
		LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema(); // 团单投保人
		tLCGrpAppntSchema.setPrtNo(mLCGrpAirContSchema.getPrtNo()); // 印刷号码
		tLCGrpAppntSchema.setName(mLCGrpAirContSchema.getGrpName());
		tLCGrpAppntSchema.setGrpContNo(mLCGrpAirContSchema.getGrpContNo());
		logger.debug("---end prepare LCGrpAppnt---");

		// 团体客户信息 LDGrp
		LDGrpSchema tLDGrpSchema = new LDGrpSchema(); // 团体客户
		tLDGrpSchema.setGrpName(mLCGrpAirContSchema.getGrpName()); // 单位名称

		LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema(); // 团体客户地址
		tLCGrpAddressSchema.setGrpAddress(mLCGrpAirContSchema.getGrpAddress());
		tLCGrpAddressSchema.setPhone1(mLCGrpAirContSchema.getGrpPhone());
		LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet(); // 客户告知
		LCHistoryImpartSet tLCHistoryImpartSet = new LCHistoryImpartSet(); // 既往告知
		// //既往告知
		LCDiseaseImpartSet tLCDiseaseImpartSet = new LCDiseaseImpartSet(); // 严重疾病告知
		LCCGrpSpecSchema tLCCGrpSpecSchema = new LCCGrpSpecSchema();
		// 保单发佣分配表
		LACommisionDetailSet tLACommisionDetailSet = new LACommisionDetailSet();
		LACommisionDetailSchema tLACommisionDetailSchema = new LACommisionDetailSchema();
		tLACommisionDetailSchema.setAgentCode(mLCGrpAirContSchema.getAgentCode());
		tLACommisionDetailSchema.setAgentGroup(tAgentGroup);
		tLACommisionDetailSchema.setPolType("0");
		tLACommisionDetailSchema.setBusiRate(1);
		tLACommisionDetailSet.add(tLACommisionDetailSchema);
		logger.debug("---end prepare 团险其他信息---");
		
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
			this.mErrors.copyAllErrors(tGroupContUI.mErrors);
			prepareDelMap(mLCGrpAirContSchema.getPrtNo());
			return false;
		} 
		else {
			logger.debug("--prepare LCGrpPol --");
			tVData.clear();
			tVData = tGroupContUI.getResult();
			LCGrpContSchema ttLCGrpContSchema = new LCGrpContSchema();
			LCGrpAppntSchema ttLCGrpAppntSchema = new LCGrpAppntSchema();
			ttLCGrpContSchema.setSchema((LCGrpContSchema) tVData.getObjectByObjectName("LCGrpContSchema", 0));
			ttLCGrpAppntSchema.setSchema((LCGrpAppntSchema) tVData.getObjectByObjectName("LCGrpAppntSchema", 0));

			LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
			LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
			tLCGrpPolSchema.setGrpContNo(ttLCGrpContSchema.getGrpContNo());
			tLCGrpPolSchema.setPrtNo(ttLCGrpContSchema.getPrtNo());
			tLCGrpPolSchema.setManageCom(mLCGrpAirContSchema.getManageCom());
			tLCGrpPolSchema.setSaleChnl("01");
			tLCGrpPolSchema.setAgentCom(mLCGrpAirContSchema.getAgentCom());
			tLCGrpPolSchema.setAgentCode(mLCGrpAirContSchema.getAgentCode());
			tLCGrpPolSchema.setAgentGroup(tAgentGroup);
			tLCGrpPolSchema.setRiskCode(mLCGrpAirContSchema.getRiskCode());
			tLCGrpPolSchema.setCValiDate(mLCGrpAirContSchema.getCValiDate());
			tLCGrpPolSchema.setPayIntv("0");
			tLCGrpPolSchema.setStandbyFlag2("AirPol");
			tLCGrpPolSchema.setAgentType("01");
//			tLCGrpPolSchema.setPrem(mLCGrpAirContSchema.getSumPrem());//由于承保核心系统对lcgrppol表的保费做累加操作，所以此处不赋值，否则金额翻倍
//			tLCGrpPolSchema.setAmnt(mLCGrpAirContSchema.getSumAmnt());
			mLCGrpPolSet.add(tLCGrpPolSchema);

			tVData.clear();
			tVData.add(mLCGrpPolSet);
			tVData.add(ttLCGrpContSchema);
			tVData.add(mGlobalInput);
			GroupRiskUI tGroupRiskUI = new GroupRiskUI();
			if (tGroupRiskUI.submitData(tVData, "INSERT||GROUPRISK") == false) {
				this.mErrors.copyAllErrors(tGroupRiskUI.mErrors);
				prepareDelMap(mLCGrpAirContSchema.getPrtNo());
				return false;
			} 
			else {
				logger.debug("---准备个单信息---");
				LCGrpAirContSet tLCGrpAirContSet = new LCGrpAirContSet();
				// 开始添加被保人信息
				for (int i = 1; i <= mLCAirPolSet.size(); i++) {
					LCAirPolSchema mLCAirPolSchema = new LCAirPolSchema();
					mLCAirPolSchema = mLCAirPolSet.get(i);

					LCContSchema tLCContSchema = new LCContSchema();
					LDPersonSchema tLDPersonSchema = new LDPersonSchema();
					LCAddressSchema tLCAddressSchema = new LCAddressSchema();
					LCInsuredSchema tmLCInsuredSchema = new LCInsuredSchema();
					LCCustomerImpartSet ttLCCustomerImpartSet = new LCCustomerImpartSet();
					LLAccountSchema tLLAccountSchema = new LLAccountSchema();
					tTransferData = new TransferData();
					ContInsuredBL tContInsuredBL = new ContInsuredBL();

					logger.debug("ttLCGrpContSchema.getGrpContNo()="+ ttLCGrpContSchema.getGrpContNo());
					tLCContSchema.setGrpContNo(ttLCGrpContSchema.getGrpContNo());
					tLCContSchema.setPrtNo(mLCGrpAirContSchema.getPrtNo());
					tLCContSchema.setManageCom(ttLCGrpContSchema.getManageCom());
					tLCContSchema.setContNo(mLCAirPolSchema.getPolNo());
					tLCContSchema.setProposalContNo(mLCAirPolSchema.getPolNo());
					tLCContSchema.setAgentCode(ttLCGrpContSchema.getAgentCode());
					tLCContSchema.setAgentGroup(ttLCGrpContSchema.getAgentGroup());
					tLCContSchema.setAgentCom(ttLCGrpContSchema.getAgentCom());
					tLCContSchema.setAgentType(ttLCGrpContSchema.getAgentType());
					tLCContSchema.setSaleChnl(ttLCGrpContSchema.getSaleChnl());
					tLCContSchema.setSignCom(ttLCGrpContSchema.getManageCom());
					tLCContSchema.setAppntNo(ttLCGrpContSchema.getAppntNo());
					tLCContSchema.setAppntName(ttLCGrpContSchema.getGrpName());
					tLCContSchema.setInputDate(PubFun.getCurrentDate());
					tLCContSchema.setInputTime(PubFun.getCurrentTime());
					tLCContSchema.setPolApplyDate(ttLCGrpContSchema.getCValiDate());
					tLCContSchema.setCValiDate(mLCAirPolSchema.getCValiDate()); // 保单生效日期
					tLCContSchema.setOrganizeDate(mLCGrpAirContSchema.getOrganizeDate());
					tLCContSchema.setOrganizeTime(mLCGrpAirContSchema.getOrganizeTime());
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
					if (mLCAirPolSchema.getInsuredBirthday() != null&&
							!mLCAirPolSchema.getInsuredBirthday().equals("")) {
						tBirthday = mLCAirPolSchema.getInsuredBirthday();
					} else
						tBirthday = "1900-01-01";

					String tSex = "";
					if (mLCAirPolSchema.getInsuredSex() != null
							&& !mLCAirPolSchema.getInsuredSex()
									.equals("")) {
						tSex = mLCAirPolSchema.getInsuredSex();
					} else
						tSex = "2";

					tmLCInsuredSchema.setRelationToMainInsured("00");
					tmLCInsuredSchema.setPrtNo(mLCGrpAirContSchema.getPrtNo());
					tmLCInsuredSchema.setGrpContNo(ttLCGrpContSchema.getGrpContNo());
					tmLCInsuredSchema.setManageCom(mGlobalInput.ManageCom);
					tmLCInsuredSchema.setExecuteCom(mGlobalInput.ManageCom);
					tmLCInsuredSchema.setName(mLCAirPolSchema.getInsuredName());
					tmLCInsuredSchema.setSex(tSex);

					tmLCInsuredSchema.setBirthday(tBirthday);
					tmLCInsuredSchema.setIDType(mLCAirPolSchema.getInsuredIDType());
					tmLCInsuredSchema.setIDNo(mLCAirPolSchema.getInsuredIDNo());
					tmLCInsuredSchema.setOccupationType(mLCAirPolSchema.getOccupationType());
					tmLCInsuredSchema.setContPlanCode("");
					tmLCInsuredSchema.setContNo(mLCAirPolSchema.getPolNo());

					tLDPersonSchema.setName(mLCAirPolSchema.getInsuredName());
					tLDPersonSchema.setSex(tSex);
					tLDPersonSchema.setBirthday(tBirthday);
					tLDPersonSchema.setIDType(mLCAirPolSchema.getInsuredIDType());
					tLDPersonSchema.setIDNo(mLCAirPolSchema.getInsuredIDNo());
					tLDPersonSchema.setOccupationType(mLCAirPolSchema.getOccupationType());

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

					logger.debug("---beging ContInsuredBL----");
					if (tContInsuredBL.submitData(tVData,"INSERT||CONTINSURED") == false) {
						this.mErrors.copyAllErrors(tContInsuredBL.mErrors);
						prepareDelMap(mLCGrpAirContSchema.getPrtNo());
						return false;
					} 
					else {
						tVData.clear();
						tVData = tContInsuredBL.getResult();
						LCContSchema mLCContSchema = new LCContSchema();
						LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
						mLCContSchema.setSchema((LCContSchema) tVData.getObjectByObjectName("LCContSchema", 0));
						mLCInsuredSchema.setSchema((LCInsuredSchema) tVData.getObjectByObjectName("LCInsuredSchema", 0));

						LCPolSchema tLCPolSchema = new LCPolSchema();
						tLCPolSchema.setGrpContNo(mLCContSchema.getGrpContNo());
						tLCPolSchema.setPrtNo(mLCGrpAirContSchema.getPrtNo());
						tLCPolSchema.setManageCom(mLCGrpAirContSchema.getManageCom());
						tLCPolSchema.setSaleChnl("01");
						tLCPolSchema.setAgentCom(mLCGrpAirContSchema.getAgentCom());
						tLCPolSchema.setAgentCode(mLCGrpAirContSchema.getAgentCode());
						tLCPolSchema.setAgentGroup(ttLCGrpContSchema.getAgentGroup());
						tLCPolSchema.setPolApplyDate(mLCGrpAirContSchema.getCValiDate());
						tLCPolSchema.setRiskCode(mLCAirPolSchema.getRiskCode());
						tLCPolSchema.setCValiDate(mLCGrpAirContSchema.getCValiDate());
						tLCPolSchema.setSpecifyValiDate("Y"); // 是否指定生效日期
						tLCPolSchema.setPayMode("1"); // 首期交费方式
						tLCPolSchema.setPayLocation("1"); // 收费方式
						tLCPolSchema.setMult(mLCAirPolSchema.getMult()); // 份数
						tLCPolSchema.setPrem(mLCAirPolSchema.getPrem()); // 保费
						tLCPolSchema.setAmnt(mLCAirPolSchema.getAmnt()); // 保额
						tLCPolSchema.setRnewFlag("-2"); // 非续保
//						tLCPolSchema.setFloatRate(1); //
						tLCPolSchema.setContNo(mLCAirPolSchema.getPolNo()); // 存储短期险系统中的保单号
						tLCPolSchema.setStandbyFlag1(mLCAirPolSchema.getRiskType());// 险种款式
						tLCPolSchema.setStandbyFlag2(mLCAirPolSchema.getReason());// 出国事由
						tLCPolSchema.setInsuYear(mLCAirPolSchema.getInsuYear());// 保险年期
						tLCPolSchema.setInsuYearFlag(mLCAirPolSchema.getInsuYearFlag());// 保险年期单位
						tLCPolSchema.setInsuredBirthday(tBirthday);// 被保险人生日
						tLCPolSchema.setInsuredSex(tSex);// 被保险人性别
						tLCPolSchema.setInsuredName(mLCAirPolSchema.getInsuredName());
						tLCPolSchema.setAppntName(mLCGrpAirContSchema.getGrpName());
						tLCPolSchema.setInsuredPeoples("1");
						tLCPolSchema.setAgentType("01");

						logger.debug("end LCPolSchema "+tLCPolSchema.getStandbyFlag1());

						LCDutySchema tLCDutySchema = new LCDutySchema();

						tLCDutySchema.setInsuYear(mLCAirPolSchema.getInsuYear()); // 保险期间
						tLCDutySchema.setInsuYearFlag(mLCAirPolSchema.getInsuYearFlag());
						tLCDutySchema.setContNo(mLCAirPolSchema.getPolNo());
						tLCDutySchema.setStandbyFlag1(mLCAirPolSchema.getRiskType());// 险种款式
						tLCDutySchema.setCalRule("3");//团险意外旅游约定保额保费

						logger.debug("end LCDutySchema ");

						LCBnfSet tLCBnfSet = new LCBnfSet();
						LCBnfSchema tLCBnfSchema = new LCBnfSchema();

						if (mLCAirPolSchema.getBnfName() != null
								&& !"".equals(mLCAirPolSchema.getBnfName())) {
							tLCBnfSchema.setBnfType("1");
							tLCBnfSchema.setName(mLCAirPolSchema.getBnfName());
							tLCBnfSchema.setRelationToInsured(mLCAirPolSchema.getRelationToInsured());
							tLCBnfSchema.setZipCode(mLCAirPolSchema.getBnfZipCode());
							tLCBnfSchema.setBnfGrade("1");
							tLCBnfSet.add(tLCBnfSchema);
						}
						
						tTransferData = new TransferData();
						tTransferData.setNameAndValue("ChangePlanFlag", "1");

						tVData = new VData();
						tVData.addElement(mLCContSchema);
						tVData.addElement(mLCInsuredSchema);
						tVData.addElement(ttLCGrpAppntSchema);
						tVData.addElement(tLCPolSchema);
						tVData.addElement(tLCBnfSet);
						tVData.addElement(tLCDutySchema);
						tVData.addElement(mGlobalInput);
						tVData.addElement(tTransferData);

						logger.debug("-----begin ProposalBL----");
						ProposalBL mProposalBL = new ProposalBL();
						if (!mProposalBL.submitData(tVData,
								"INSERT||PROPOSAL")) {
							this.mErrors.copyAllErrors(mProposalBL.mErrors);
							prepareDelMap(mLCGrpAirContSchema.getPrtNo());
							return false;
						}
					}
					logger.debug("-----congratulations,核心处理结束-");
					mLCGrpAirContSchema.setBalanceDate(PubFun.getCurrentDate());//结算日期
					mLCGrpAirContSchema.setState("C");//结算完毕
					mLCGrpAirContSchema.setModifyDate(PubFun.getCurrentDate());
					mLCGrpAirContSchema.setModifyTime(PubFun.getCurrentTime());
					tLCGrpAirContSet.add(mLCGrpAirContSchema);
				}
				map = new MMap();
				map.put(tLCGrpAirContSet, "UPDATE");
				VData sVData = new VData();
				sVData.add(map);
				PubSubmit ps = new PubSubmit();
				ps.submitData(sVData);
				logger.debug("------LCGrpAirCont更新状态成功----");
			}
		}

		for (int j = 1; j <= mLCAirPolSet.size(); j++)
		{
			// 处理完毕，将LCAirPol中的信息转移到LBAirPol中；
			LCAirPolSchema uLCAirPolSchema = new LCAirPolSchema();
			uLCAirPolSchema = mLCAirPolSet.get(j);
			LBAirPolSchema uLBAirPolSchema = new LBAirPolSchema();
			Reflections ref = new Reflections();
			ref.transFields(uLBAirPolSchema, uLCAirPolSchema);
			uLBAirPolSchema.setState("C");
//			uLBAirPolSchema.setMakeDate(PubFun.getCurrentDate());
//			uLBAirPolSchema.setMakeTime(PubFun.getCurrentTime());
//			uLBAirPolSchema.setModifyDate(PubFun.getCurrentDate());
//			uLBAirPolSchema.setModifyTime(PubFun.getCurrentTime());
			
			logger.debug("----prepare LBAirPol-----");

			MMap tMMap = new MMap();
			tMMap.put(uLBAirPolSchema, "INSERT");
			tMMap.put(uLCAirPolSchema, "DELETE");
			VData tempVData = new VData();
			tempVData.add(tMMap);
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tempVData)) {
				CError.buildErr(this, "保单信息转移到LBAirPol时出错！");
			}
	
			logger.debug("-----LBAirPol处理完毕---");
			// 将复核状态、自核状态都置为核保通过的标志
			tMMap = new MMap();
			tMMap.put("update lcpol set Approveflag='9',Approvecode='"
					+ mGlobalInput.Operator + "',ApproveDate='"
					+ PubFun.getCurrentDate() + "',ApproveTime='"
					+ PubFun.getCurrentTime() + "',UWFlag='9',UWCode='"
					+ mGlobalInput.Operator + "',UWDate='"
					+ PubFun.getCurrentDate() + "',UWTime='"
					+ PubFun.getCurrentTime() + "' where grpcontno='" + mGrpContNo
					+ "'", "UPDATE");
			tMMap.put("update lccont set Approveflag='9',Approvecode='"
					+ mGlobalInput.Operator + "',ApproveDate='"
					+ PubFun.getCurrentDate() + "',ApproveTime='"
					+ PubFun.getCurrentTime() + "',UWFlag='9',UWOperator='"
					+ mGlobalInput.Operator + "',UWDate='"
					+ PubFun.getCurrentDate() + "',UWTime='"
					+ PubFun.getCurrentTime() + "' where grpcontno='" + mGrpContNo
					+ "'", "UPDATE");
			tMMap.put("update lcgrppol set Approveflag='9',Approvecode='"
					+ mGlobalInput.Operator + "',ApproveDate='"
					+ PubFun.getCurrentDate() + "',ApproveTime='"
					+ PubFun.getCurrentTime() + "',UWFlag='9',UWOperator='"
					+ mGlobalInput.Operator + "',UWDate='"
					+ PubFun.getCurrentDate() + "',UWTime='"
					+ PubFun.getCurrentTime() + "' where grpcontno='" + mGrpContNo
					+ "'", "UPDATE");
			tMMap.put("update LCGrpCont set Approveflag='9',Approvecode='"
					+ mGlobalInput.Operator + "',ApproveDate='"
					+ PubFun.getCurrentDate() + "',ApproveTime='"
					+ PubFun.getCurrentTime() + "',UWFlag='9',UWOperator='"
					+ mGlobalInput.Operator + "',UWDate='"
					+ PubFun.getCurrentDate() + "',UWTime='"
					+ PubFun.getCurrentTime() + "' where grpcontno='" + mGrpContNo
					+ "'", "UPDATE");
			tempVData = new VData();
			tempVData.add(tMMap);
			tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tempVData)) {
				CError.buildErr(this, "修改符合信息时报错！");
				return false;
			}
			logger.debug("-----核保状态处理结束---");
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

		logger.debug("BadNews 结算失败,进行数据回滚");
		map = new MMap();
		map.put("delete from LACommisionDetail where grpcontno =(select grpcontno from lcgrpcont where prtno='"+ tPrtNo + "')", "DELETE");
		map.put("delete from lcinsured where grpcontno =(select grpcontno from lcgrpcont where prtno='"+ tPrtNo + "')", "DELETE");
		map.put("delete from LCGrpAppnt where prtno='" + tPrtNo + "'","DELETE");
		map.put("delete from lcgrppol where prtno='" + tPrtNo + "'","DELETE");

		map.put("delete from lcpol where prtno='" + tPrtNo + "'", "DELETE");
		map.put("delete from lcprem where grpcontno =(select grpcontno from lcgrpcont where prtno='"+ tPrtNo + "')", "DELETE");
		map.put("delete from lcget where grpcontno =(select grpcontno from lcgrpcont where prtno='"+ tPrtNo + "')", "DELETE");
		map.put("delete from lcduty where contno in (select contno from lccont where prtno='"+ tPrtNo + "')", "DELETE");
		map.put("delete from lccont where prtno='" + tPrtNo + "'", "DELETE");
		map.put("delete from LCGrpCont where prtno='" + tPrtNo + "'","DELETE");
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

}
