/*
 * <p>ClassName: ContInsuredBL </p>
 * <p>Description: ContInsuredBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2004-11-18 10:09:44
 */
package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import com.sinosoft.lis.bl.LCAppntBL;
import com.sinosoft.lis.bl.LCGetBL;
import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCAscriptionRuleFactoryDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContPlanDB;
import com.sinosoft.lis.db.LCContPlanDutyParamDB;
import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGetToAccDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPENoticeDB;
import com.sinosoft.lis.db.LCPENoticeResultDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPolOriginalDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCPremToAccDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LMRiskDutyFactorDB;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.CheckFieldCom;
import com.sinosoft.lis.pubfun.FieldCarrier;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ES_DOC_RELATIONSchema;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContPlanRiskSchema;
import com.sinosoft.lis.schema.LCContPlanSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDelPolLogSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCGetToAccSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPayRuleFactorySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCPremToAccSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LLAccountSchema;
import com.sinosoft.lis.schema.LMCheckFieldSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vbl.LCBnfBLSet;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCAscriptionRuleFactorySet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCContPlanDutyParamSet;
import com.sinosoft.lis.vschema.LCContPlanRiskSet;
import com.sinosoft.lis.vschema.LCContPlanSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCCustomerImpartDetailSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDelPolLogSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGetToAccSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPENoticeResultSet;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.lis.vschema.LCPolOriginalSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LMCheckFieldSet;
import com.sinosoft.lis.vschema.LMRiskDutyFactorSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class ContInsuredBL {
private static Logger logger = Logger.getLogger(ContInsuredBL.class);
	/**
	 * 对比险种中关系到保额保费计算的被保人信息是否有更改
	 * 
	 * @param vLDPersonSchema
	 *            LDPersonSchema
	 * @param vLCInsuredSchema
	 *            LCInsuredSchema
	 * @return boolean 
	 */
	private static boolean balance(LDPersonSchema vLDPersonSchema,
			LCInsuredSchema vLCInsuredSchema) {
		// 性别
		if (!(vLDPersonSchema.getSex().trim()).equals(vLCInsuredSchema.getSex()
				.trim())) {
			logger.debug("性别:" + vLDPersonSchema.getSex() + ":"
					+ vLCInsuredSchema.getSex());
			return true;
		}
		// 生日
		if (!(vLDPersonSchema.getBirthday().trim()).equals(vLCInsuredSchema
				.getBirthday().trim())) {
			logger.debug("生日:" + vLDPersonSchema.getBirthday() + ":"
					+ vLCInsuredSchema.getBirthday());
			return true;
		}
		// 职业类别
		if (vLDPersonSchema.getOccupationType() != null) {
			if (!StrTool.compareString(vLDPersonSchema.getOccupationType(),
					vLCInsuredSchema.getOccupationType())) {
				logger.debug("职业:" + vLDPersonSchema.getOccupationType()
						+ ":" + vLCInsuredSchema.getOccupationType());
				return true;
			}
		}
		// 职业类别
		if (!StrTool.compareString(vLDPersonSchema.getIDType(),
				vLCInsuredSchema.getIDType())) {
			logger.debug("证件类型:" + vLDPersonSchema.getIDType() + ":"
					+ vLCInsuredSchema.getIDType());
			return true;
		}

		if (!StrTool.compareString(vLDPersonSchema.getIDNo(), vLCInsuredSchema
				.getIDNo())) {
			logger.debug("证件号码:" + vLDPersonSchema.getIDNo() + ":"
					+ vLCInsuredSchema.getIDNo());
			return true;
		}

		else {
			if (vLCInsuredSchema.getOccupationType() != null) {
				return true;
			}
		}

		return false;
	}

	public static void main(String[] args) {
		GlobalInput tGI = new GlobalInput();
		LCContSchema tLCContSchema = new LCContSchema();

		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		LCInsuredDB tOLDLCInsuredDB = new LCInsuredDB();
		LCAddressSchema tLCAddressSchema = new LCAddressSchema();
		LCInsuredSchema tmLCInsuredSchema = new LCInsuredSchema();
		LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
		LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
		TransferData tTransferData = new TransferData();
		tGI.ComCode = "86210000";
		tGI.ManageCom = "86210000";
		tGI.Operator = "001";

		tLCContSchema.setPrtNo("86000706210002");
		tLCContSchema.setGrpContNo("880152523365");
		tLCContSchema.setManageCom("86210000");

		tLDPersonSchema.setName("ab");
		tLDPersonSchema.setBirthday("1980-12-12");
		tLDPersonSchema.setSex("1");
		tLDPersonSchema.setPluralityType("130210000012697");
		tLDPersonSchema.setIDType("8");
		tLDPersonSchema.setIDNo("23333333333");
		tLDPersonSchema.setOccupationCode("Z008004");
		tLDPersonSchema.setOccupationType("1");

		tOLDLCInsuredDB.setName("ab");
		tOLDLCInsuredDB.setBirthday("1980-12-12");
		tOLDLCInsuredDB.setSex("1");
		tOLDLCInsuredDB.setPluralityType("130210000012697");
		tOLDLCInsuredDB.setIDType("8");
		tOLDLCInsuredDB.setIDNo("23333333333");
		tOLDLCInsuredDB.setOccupationCode("Z008004");
		tOLDLCInsuredDB.setOccupationType("1");

		tTransferData.setNameAndValue("SavePolType", "0"); // 保全保存标记，默认为0，标识非保全
		tTransferData.setNameAndValue("ContNo", "");
		tTransferData.setNameAndValue("FamilyType", "0"); // 家庭单标记
		tTransferData.setNameAndValue("ContType", "2"); // 团单，个人单标记
		tTransferData.setNameAndValue("PolTypeFlag", "0"); // 无名单标记
		tTransferData.setNameAndValue("InsuredPeoples", "1"); // 被保险人人数
		tTransferData.setNameAndValue("InsuredAppAge", "25"); // 被保险人年龄
		tTransferData.setNameAndValue("SequenceNo", null); // 内部客户号
		tTransferData.setNameAndValue("Mult", "1"); // 内部客户号
		tTransferData.setNameAndValue("EdorType", "NI"); // 内部客户号

		// tLCCustomerImpartSet.decode("");
		// tLCCustomerImpartDetailSet.decode("");
		VData tVData = new VData();
		tVData.add(tLCContSchema);
		tVData.add(tLDPersonSchema);
		tVData.add(tLCCustomerImpartSet);
		// tVData.add(tLCCustomerImpartDetailSet);
		tVData.add(tmLCInsuredSchema);
		tVData.add(tLCAddressSchema);
		tVData.add(tOLDLCInsuredDB);
		tVData.add(tTransferData);
		tVData.add(tGI);
		ContInsuredBL tContInsuredBL = new ContInsuredBL();
		if (tContInsuredBL.submitData(tVData, "INSERT||CONTINSURED")) {
			logger.debug("Sucess");
		} else {
			logger.debug(tContInsuredBL.mErrors.getFirstError());
		}
	}

	private String agentcode;
	private String appflag;
	private String appntno;

	private String ContNo;
	private String ContType;
	private String FamilyType;
	private boolean ISAppPersonSave = true; // 投保人客户信息是否保存标

	private boolean isFillList = false;
	private boolean ISPlanRisk = false; // 套餐险种

	/** 业务处理相关变量 */
	private String manageCom; // add by yaory
	private MMap map = new MMap();

	private LDPersonSchema mAppPerson = new LDPersonSchema();

	private String mark = "";
	/** 1为个单，2为团单 */
	private String mDealFlag = "1"; // 处理逻辑，1为个单，2为团单
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private LCAccountSchema mLCAccountSchema = new LCAccountSchema();
	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();
	private LCAppntBL mLCAppntBL = new LCAppntBL(); // add by yaory for card
	private LCAppntSchema mLCAppntSchema = new LCAppntSchema(); // add by tianwk
	// for broker
	// balance
	private LCPolSet mmLCPolSet =new LCPolSet();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCCustomerImpartDetailSet mLCCustomerImpartDetailSet;
	private LCCustomerImpartParamsSet mLCCustomerImpartParamsSet;
	private LCCustomerImpartSet mLCCustomerImpartSet;
	private LCGrpContDB mLCGrpContDB = new LCGrpContDB();
	private LLAccountSchema mLLAccountSchema = new LLAccountSchema();
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
	private LCInsuredSchema tOLCLCInsuredSchema = new LCInsuredSchema();
	/** 保单 */
	private LCPolBL mLCPolBL = new LCPolBL();
	private LDPersonSchema mLDPersonSchema = new LDPersonSchema(); // 从界面传入的客户数据
	private String mLoadFlag = "";// 操作环节。---yeshu,20071224
	private HashMap mMainPolMap = new HashMap();
	private LCInsuredDB mOLDLCInsuredDB = new LCInsuredDB(); // 记录需要删除或修改的客户
	/** 数据操作字符串 */
	private String mOperate;
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();
	private String nonameflag = "0";
	private String operateType = ""; // add by tianwk
	private String operator;
	private LCPolSet preLCPolSet = new LCPolSet();
	private Reflections ref = new Reflections();
	private VData rResult = new VData(); // add by yaory

	private String signcom;
	private ES_DOC_RELATIONSchema tES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema(); // 添加到关联表被保险人的数据
	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	
	//wangxw 20100920 子公司号码
	private String mGrpNo = "";

	private String tInsuredAppAge;

	private String tInsuredPeoples;
	private String locCurrency = "";//本币信息
	private LCAddressSchema tLCAddressSchema;
	private LDPersonSchema tLDPersonSchema; // tLDPersonSet包含需要新建的客户

	private String tPolTypeFlag;
	private String tSequenceNo; // 客户顺序号
	private String KDCheckFlag = "0";//自助卡单标记，1--自助卡单，0--正常保单
	private String CardRelationToInsured =""; //卡单判断是否为统一人 是被保人号取为投保人号

	private SSRS tSSRS = new SSRS(); // 备用

	// private LCInsuredSet mLCInsuredSet=new LCInsuredSet();
	public ContInsuredBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 缓存数据准备成功的险种保单信息
	 * 
	 * @param strID
	 *            String
	 * @param aLCPolSchema
	 *            LCPolSchema
	 */
	public void cachePolInfo(String tRiskCode, LCPolSchema tLCPolSchema) {
		mMainPolMap.put(tRiskCode, tLCPolSchema);
	}

	/**
	 * 校验传入的数据
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		if (!this.checkLDPerson()) {
			return false;
		}
		logger.debug("after checkLDPerson!!!!");
		if (!this.checkLCAddress()) {
			return false;
		}
		logger.debug("after checkLCAddress!!!!");
		if (mDealFlag.equals("2") && mOperate.equals("INSERT||CONTINSURED")) {
			if (mLDPersonSchema.getCustomerNo() != null
					&& !("").equals(mLDPersonSchema.getCustomerNo())) {
				LCInsuredDB tLCInsuredDB = new LCInsuredDB();
				tLCInsuredDB.setGrpContNo(mLCContSchema.getGrpContNo());
				tLCInsuredDB.setInsuredNo(mLDPersonSchema.getCustomerNo());
				int insuredCount = tLCInsuredDB.getCount();
				if (tLCInsuredDB.mErrors.needDealError()) {
					CError tError = new CError();
					tError.moduleName = "ContBL";
					tError.functionName = "checkData";
					tError.errorMessage = "查询团体合同下被保险人失败!";

					this.mErrors.addOneError(tError);
					return false;
				}
				if (insuredCount > 0) {

					if("NI".equals(mTransferData.getValueByName("EdorType"))){
						
						LCContDB tLCContDB = new LCContDB();
						tLCContDB.setGrpContNo(mLCContSchema.getGrpContNo());
						tLCContDB.setInsuredNo(mLDPersonSchema.getCustomerNo());
						tLCContDB.setAppFlag("1");
						LCContSet tLCContSet = tLCContDB.query();
						if(tLCContSet != null && tLCContSet.size()>0){
							CError tError = new CError();
							tError.moduleName = "ContBL";
							tError.functionName = "checkData";
							tError.errorMessage = "该团体合同下已经存在该被保险人(姓名："+mLDPersonSchema.getName()+")的有效保单，不能重复添加!";
							this.mErrors.addOneError(tError);
							return false;
						}

					}else{
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkData";
						tError.errorMessage = "该团体合同下已经存在该被保险人(姓名："+mLDPersonSchema.getName()+")，不能重复添加!";
						this.mErrors.addOneError(tError);
						return false;
					}

				}
			}
		}
		// if (mOperate.equals("UPDATE||CONTINSURED"))
		// {
		// LCPolDB tLCPolDB = new LCPolDB();
		// tLCPolDB.setContNo(mLCContSchema.getContNo());
		// tLCPolDB.setInsuredNo(mOLDLCInsuredDB.getInsuredNo());
		// int polCOunt = tLCPolDB.getCount();
		// if (tLCPolDB.mErrors.needDealError())
		// {
		// CError.buildErr(this, "查询被保险人险种保单时失败！");
		// return false;
		// }
		// if (polCOunt > 0)
		// {
		// if (balance(mLDPersonSchema, mOLDLCInsuredDB))
		// {
		// CError.buildErr(this,
		// "该被保险人已经录入险种不能更新性别，生日，职业类别等关键信息,请先删除险种！");
		// }
		// return false;
		// }
		// }

		// //删除时要判断如果时主被保险人，要确认没有其他被保险人??放到录入完成时统一校验有没有主被保险人
		// if (mOperate.equals("DELETE||CONTINSURED")){
		// if (("00").equals(mOLDLCInsuredDB.getRelationToMainInsured())){
		// LCInsuredDB tempLCInsuredDB = new LCInsuredDB();
		// LCInsuredSet tempLCInsuredSet = new LCInsuredSet();
		// tempLCInsuredDB.setContNo(mOLDLCInsuredDB.getContNo());
		// tempLCInsuredSet = tempLCInsuredDB.query();
		// if (tempLCInsuredDB.mErrors.needDealError()) {
		// this.mErrors.copyAllErrors(mOLDLCInsuredDB.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "ContInsuredBL";
		// tError.functionName = "deleteData";
		// tError.errorMessage = "获取合同下被保险人信息时失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// if (tempLCInsuredSet.size() > 1) {
		// this.mErrors.copyAllErrors(mOLDLCInsuredDB.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "ContInsuredBL";
		// tError.functionName = "deleteData";
		// tError.errorMessage = "请先删除其他被保险人再删除主被保险人!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// }

		// }
		return true;

	}

	/**
	 * 检查地址数据是否正确 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean checkLCAddress() {
		if (mDealFlag.equals("2")) {
			return true;
		}
		if (mLCAddressSchema != null) {
			if (mLCAddressSchema.getPostalAddress() != null) { // 去空格
				mLCAddressSchema.setPostalAddress(mLCAddressSchema
						.getPostalAddress().trim());
				if (mLCAddressSchema.getZipCode() != null) {
					mLCAddressSchema.setZipCode(mLCAddressSchema.getZipCode()
							.trim());
				}
				if (mLCAddressSchema.getPhone() != null) {
					mLCAddressSchema.setPhone(mLCAddressSchema.getPhone()
							.trim());
				}

			}
			logger.debug("mLCAddressSchema.getAddressNo()==="
					+ mLCAddressSchema.getAddressNo());
			logger.debug("mLCAddressSchema.encode()==="
					+ mLCAddressSchema.encode());
			if (mLCAddressSchema.getAddressNo() != 0
					&& mLDPersonSchema.getCustomerNo() != null
					&& !mLDPersonSchema.getCustomerNo().equals("")) {
				// 如果有地址号
				if (mLCAddressSchema.getAddressNo() != 0) {
					LCAddressDB tLCAddressDB = new LCAddressDB();

					tLCAddressDB.setAddressNo(mLCAddressSchema.getAddressNo());
					tLCAddressDB
							.setCustomerNo(mLCAddressSchema.getCustomerNo());
					if (!tLCAddressDB.getInfo()) {
						/*
						 * CError tError = new CError(); tError.moduleName =
						 * "ContBL"; tError.functionName = "checkAddress";
						 * tError.errorMessage = "数据库查询失败!";
						 * this.mErrors.addOneError(tError);
						 * 
						 * return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(
							mLCAddressSchema.getCustomerNo(), tLCAddressDB
									.getCustomerNo())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户号(" +
						 * tLCAddressDB.getCustomerNo() + ")与录入的客户号(" +
						 * mLCAddressSchema.getCustomerNo() + ")不匹配！"; cuErrInfo
						 * = cuErrInfo + "请去地址号码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(""
							+ mLCAddressSchema.getAddressNo(), ""
							+ tLCAddressDB.getAddressNo())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户地址代码(" +
						 * tLCAddressDB.getAddressNo() + ")与录入的客户地址代码(" +
						 * mLCAddressSchema.getAddressNo() + ")不匹配！"; cuErrInfo
						 * = cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(mLCAddressSchema
							.getPostalAddress(), tLCAddressDB
							.getPostalAddress())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户通讯地址(" +
						 * tLCAddressDB.getPostalAddress() + ")与录入的客户通讯地址(" +
						 * mLCAddressSchema.getPostalAddress() + ")不匹配！";
						 * cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！"; CError
						 * tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(mLCAddressSchema.getZipCode(),
							tLCAddressDB.getZipCode())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户邮政编码(" +
						 * tLCAddressDB.getZipCode() + ")与录入的客户邮政编码(" +
						 * mLCAddressSchema.getZipCode() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getPhone(),
							tLCAddressDB.getPhone())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户通讯电话(" +
						 * tLCAddressDB.getPhone() + ")与录入的客户通讯电话(" +
						 * mLCAddressSchema.getPhone() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(mLCAddressSchema.getFax(),
							tLCAddressDB.getFax())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户通讯传真(" + tLCAddressDB.getFax()
						 * + ")与录入的客户通讯传真(" + mLCAddressSchema.getFax() +
						 * ")不匹配！"; cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！";
						 * CError tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema
							.getHomeAddress(), tLCAddressDB.getHomeAddress())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户家庭地址(" +
						 * tLCAddressDB.getHomeAddress() + ")与录入的客户家庭地址(" +
						 * mLCAddressSchema.getHomeAddress() + ")不匹配！";
						 * cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！"; CError
						 * tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema
							.getHomeZipCode(), tLCAddressDB.getHomeZipCode())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户家庭邮编(" +
						 * tLCAddressDB.getHomeZipCode() + ")与录入的客户家庭邮编(" +
						 * mLCAddressSchema.getHomeZipCode() + ")不匹配！";
						 * cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！"; CError
						 * tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(mLCAddressSchema.getHomePhone(),
							tLCAddressDB.getHomePhone())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户家庭电话(" +
						 * tLCAddressDB.getHomePhone() + ")与录入的客户家庭电话(" +
						 * mLCAddressSchema.getHomePhone() + ")不匹配！"; cuErrInfo
						 * = cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(mLCAddressSchema.getHomeFax(),
							tLCAddressDB.getHomeFax())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户家庭传真(" +
						 * tLCAddressDB.getHomeFax() + ")与录入的客户家庭传真(" +
						 * mLCAddressSchema.getHomeFax() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(mLCAddressSchema
							.getCompanyAddress(), tLCAddressDB
							.getCompanyAddress())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户单位地址(" +
						 * tLCAddressDB.getCompanyAddress() + ")与录入的客户单位地址(" +
						 * mLCAddressSchema. getCompanyAddress() + ")不匹配！";
						 * cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！"; CError
						 * tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);

					}
					if (!StrTool.compareString(mLCAddressSchema
							.getCompanyZipCode(), tLCAddressDB
							.getCompanyZipCode())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户单位邮编(" +
						 * tLCAddressDB.getCompanyZipCode() + ")与录入的客户单位邮编(" +
						 * mLCAddressSchema.getCompanyZipCode() + ")不匹配！";
						 * cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！"; CError
						 * tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema
							.getPhone(), tLCAddressDB.getPhone())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户单位电话(" +
						 * tLCAddressDB.getCompanyPhone() + ")与录入的客户单位电话(" +
						 * mLCAddressSchema.getCompanyPhone() + ")不匹配！";
						 * cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！"; CError
						 * tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(
							mLCAddressSchema.getCompanyFax(), tLCAddressDB
									.getCompanyFax())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户单位传真(" +
						 * tLCAddressDB.getCompanyFax() + ")与录入的客户单位传真(" +
						 * mLCAddressSchema.getCompanyFax() + ")不匹配！"; cuErrInfo
						 * = cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getMobile(),
							tLCAddressDB.getMobile())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户手机(" +
						 * tLCAddressDB.getMobile() + ")与录入的客户手机(" +
						 * mLCAddressSchema.getMobile() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getMobileChs(),
							tLCAddressDB.getMobileChs())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户手机中文标示(" +
						 * tLCAddressDB.getMobileChs() + ")与录入的客户手机中文标示(" +
						 * mLCAddressSchema.getMobileChs() + ")不匹配！"; cuErrInfo
						 * = cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getEMail(),
							tLCAddressDB.getEMail())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户e_mail(" +
						 * tLCAddressDB.getEMail() + ")与录入的客户e_mail(" +
						 * mLCAddressSchema.getEMail() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getBP(),
							tLCAddressDB.getBP())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户传呼(" + tLCAddressDB.getBP() +
						 * ")与录入的客户传呼(" + mLCAddressSchema.getBP() + ")不匹配！";
						 * cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！"; CError
						 * tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getMobile2(),
							tLCAddressDB.getMobile2())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户手机2(" +
						 * tLCAddressDB.getMobile2() + ")与录入的客户手机2(" +
						 * mLCAddressSchema.getMobile2() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(
							mLCAddressSchema.getMobileChs2(), tLCAddressDB
									.getMobileChs2())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户手机中文标示2(" +
						 * tLCAddressDB.getMobileChs2() + ")与录入的客户手机中文标示2(" +
						 * mLCAddressSchema.getMobileChs2() + ")不匹配！"; cuErrInfo
						 * = cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getEMail2(),
							tLCAddressDB.getEMail2())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户e_mail2(" +
						 * tLCAddressDB.getEMail2() + ")与录入的客户e_mail2(" +
						 * mLCAddressSchema.getEMail2() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getBP2(),
							tLCAddressDB.getBP2())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户传呼2(" + tLCAddressDB.getBP2()
						 * + ")与录入的客户传呼2(" + mLCAddressSchema.getBP2() +
						 * ")不匹配！"; cuErrInfo = cuErrInfo + "请去掉地址代码，重新操作！";
						 * CError tError = new CError(); tError.moduleName =
						 * "ProposalBL"; tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}
					if (!StrTool.compareString(mLCAddressSchema.getGrpName(),
							tLCAddressDB.getGrpName())) {
						/*
						 * String cuErrInfo = "您输入的被保人客户号[" +
						 * mLCAddressSchema.getCustomerNo() + "]："; cuErrInfo =
						 * cuErrInfo + "对应在数据库中的客户单位名称(" +
						 * tLCAddressDB.getGrpName() + ")与录入的客户单位名称(" +
						 * mLCAddressSchema.getGrpName() + ")不匹配！"; cuErrInfo =
						 * cuErrInfo + "请去掉地址代码，重新操作！"; CError tError = new
						 * CError(); tError.moduleName = "ProposalBL";
						 * tError.functionName = "checkPerson";
						 * tError.errorMessage = cuErrInfo;
						 * this.mErrors.addOneError(tError); return false;
						 */
						mLCAddressSchema.setAddressNo(0);
					}

				}
			} else {
				mLCAddressSchema.setAddressNo(0);
			}

		}

		return true;
	}

	/**
	 * 检查被保人录入的数据是否正确(适用于个人投保单和集体下的个单) 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean checkLDPerson() {
		// 如果是无名单或者公共帐户的个人，不校验返回
		if (StrTool.compareString(tPolTypeFlag, "1")
				|| StrTool.compareString(tPolTypeFlag, "2")) {
			return true;
		}

		if (mLDPersonSchema != null) {
			if (mLDPersonSchema.getName() != null) { // 去空格
				mLDPersonSchema.setName(mLDPersonSchema.getName().trim());
			}
			if (mLDPersonSchema.getSex() != null) {
				mLDPersonSchema.setSex(mLDPersonSchema.getSex().trim());
			}
			if (mLDPersonSchema.getIDNo() != null) {
				mLDPersonSchema.setIDNo(mLDPersonSchema.getIDNo().trim());
			}
			if (mLDPersonSchema.getIDType() != null) {
				mLDPersonSchema.setIDType(mLDPersonSchema.getIDType().trim());
			}
			if (mLDPersonSchema.getBirthday() != null) {
				mLDPersonSchema.setBirthday(mLDPersonSchema.getBirthday()
						.trim());
			}

			// if ( (mLDPersonSchema.getOccupationCode() != null ||
			// !("").equals(mLDPersonSchema.getOccupationCode().trim())) &&
			// (mLDPersonSchema.getOccupationType() != null ||
			// !("").equals(mLDPersonSchema.getOccupationType().trim()))) {
			// ExeSQL tExeSQL = new ExeSQL();
			// String tOccupationType = tExeSQL.getOneValue(
			// "select occupationtype from LDOccupation where occupationcode='"
			// +
			// mLDPersonSchema.getOccupationCode() + "'");
			// if (!mLDPersonSchema.getOccupationType().trim().equals(
			// tOccupationType.trim())) {
			// CError tError = new CError();
			// tError.moduleName = "ContInsuredBL";
			// tError.functionName = "checkPerson";
			// tError.errorMessage = "客户职业代码和职业类别不对应！职业代码为" +
			// mLDPersonSchema.getOccupationCode() +
			// "的职业类别为：" + tOccupationType;
			// this.mErrors.addOneError(tError);
			// return false;
			//
			// }
			// }
			// 如果客户号存在，那么就利用客户号查询数据，将查询出的客户信息与录入客户信息比较
			// 如皋重要信息（姓名、证件类型、证件号码、出生日期、性别）有任意一项不同
			// 去掉客户号码以及地址号码，且客户号需要重新生成。
			if (mLDPersonSchema.getCustomerNo() != null) {
				// 如果有客户号
				// 如果有客户号
				if (!mLDPersonSchema.getCustomerNo().equals("")
						&& !"00".equals(tSequenceNo)
						&& (mark == null || !mark.equals("card"))) {
					LDPersonDB tLDPersonDB = new LDPersonDB();
					tLDPersonDB.setCustomerNo(mLDPersonSchema.getCustomerNo());
					if (!tLDPersonDB.getInfo()) {
						CError tError = new CError();
						tError.moduleName = "ContBL";
						tError.functionName = "checkPerson";
						tError.errorMessage = "查询客户号["
								+ mLDPersonSchema.getCustomerNo() + "]失败!";
						this.mErrors.addOneError(tError);
						return false;
					}
					if (mLDPersonSchema.getName() != null) {
						String Name = StrTool.GBKToUnicode(tLDPersonDB
								.getName().trim());
						String NewName = StrTool.GBKToUnicode(mLDPersonSchema
								.getName().trim());
						if (!Name.equals(NewName)) {
							// 保存比较客户时的错误信息
							String cuErrInfo = "您输入的被保险人客户号["
									+ mLDPersonSchema.getCustomerNo() + "]：";
							cuErrInfo = cuErrInfo + "对应在数据库中的客户姓名(" + Name
									+ ")" + "与您录入的客户姓名(" + NewName + ")不匹配！";
							cuErrInfo = cuErrInfo + "请去掉客户号码，重新操作！";
							CError tError = new CError();
							tError.moduleName = "ProposalBL";
							tError.functionName = "checkPerson";
							tError.errorMessage = cuErrInfo;
							this.mErrors.addOneError(tError);
							return false;
						}
					}
					if (mLDPersonSchema.getSex() != null) {
						if (!tLDPersonDB.getSex().equals(
								mLDPersonSchema.getSex())) {
							// 保存比较客户时的错误信息
							String cuErrInfo = "您输入的被保险人客户号["
									+ mLDPersonSchema.getCustomerNo() + "]：";
							cuErrInfo = cuErrInfo + "对应在数据库中的客户性别("
									+ tLDPersonDB.getSex() + ")" + "与您录入的客户性别("
									+ mLDPersonSchema.getSex() + ")不匹配！";
							cuErrInfo = cuErrInfo + "请去掉客户号码，重新操作！";
							CError tError = new CError();
							tError.moduleName = "ContBL";
							tError.functionName = "checkPerson";
							tError.errorMessage = cuErrInfo;
							this.mErrors.addOneError(tError);
							return false;
						}
					}
					if (mLDPersonSchema.getBirthday() != null) {
						if (!tLDPersonDB.getBirthday().equals(
								mLDPersonSchema.getBirthday())) {
							// 保存比较客户时的错误信息
							String cuErrInfo = "您输入的被保险人客户号["
									+ mLDPersonSchema.getCustomerNo() + "]：";
							cuErrInfo = cuErrInfo + "对应在数据库中的客户生日("
									+ tLDPersonDB.getBirthday() + ")"
									+ "与您录入的客户生日("
									+ mLDPersonSchema.getBirthday() + ")不匹配！";
							cuErrInfo = cuErrInfo + "请去掉客户号码，重新操作！";
							CError tError = new CError();
							tError.moduleName = "ContBL";
							tError.functionName = "checkPerson";
							tError.errorMessage = cuErrInfo;
							this.mErrors.addOneError(tError);
							return false;
						}
					}
					if (mLDPersonSchema.getIDNo() != null) {
						ExeSQL tExeSQL = new ExeSQL();
						SQLwithBindVariables sqlbv = new SQLwithBindVariables();
						sqlbv.sql("select ChgIDNo('"
								+ "?IDNo?" + "','"
								+ "?IDType?"
								+ "') from dual");
						sqlbv.put("IDNo", tLDPersonDB.getIDNo());
						sqlbv.put("IDType", tLDPersonDB.getIDType());
						
						String OriginIDNo = tExeSQL
								.getOneValue(sqlbv);
						
						SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
						sqlbv1.sql("select ChgIDNo('"
								+ "?IDNo?" + "','"
								+ "?IDType?"
								+ "') from dual");
						sqlbv1.put("IDNo", mLDPersonSchema.getIDNo());
						sqlbv1.put("IDType", mLDPersonSchema.getIDType());
						String InputIDNo = tExeSQL
								.getOneValue(sqlbv1);
						// if
						//(!tLDPersonDB.getIDNo().equals(mLDPersonSchema.getIDNo
						// ()))
						if (!OriginIDNo.equals(InputIDNo)) {
							// 保存比较客户时的错误信息
							String cuErrInfo = "您输入的被保险人客户号["
									+ mLDPersonSchema.getCustomerNo() + "]：";
							cuErrInfo = cuErrInfo + "对应在数据库中的证件号码("
									+ tLDPersonDB.getIDNo() + ")"
									+ "与您录入的证件号码(" + mLDPersonSchema.getIDNo()
									+ ")不匹配！";
							cuErrInfo = cuErrInfo + "请去掉客户号码，重新操作！";
							CError tError = new CError();
							tError.moduleName = "ContBL";
							tError.functionName = "checkPerson";
							tError.errorMessage = cuErrInfo;
							this.mErrors.addOneError(tError);
							return false;
						}
					}
					if (mLDPersonSchema.getIDType() != null) {
						if (!tLDPersonDB.getIDType().equals(
								mLDPersonSchema.getIDType())) {
							// 保存比较客户时的错误信息
							String cuErrInfo = "您输入的被保险人客户号["
									+ mLDPersonSchema.getCustomerNo() + "]：";
							cuErrInfo = cuErrInfo + "对应在数据库中的证件类型("
									+ tLDPersonDB.getIDType() + ")"
									+ "与您录入的证件类型("
									+ mLDPersonSchema.getIDType() + ")不匹配！";
							cuErrInfo = cuErrInfo + "请去掉客户号码，重新操作！";
							CError tError = new CError();
							tError.moduleName = "ContBL";
							tError.functionName = "checkPerson";
							tError.errorMessage = cuErrInfo;
							this.mErrors.addOneError(tError);
							return false;
						}
					}
				} else {
					// 如果没有客户号,查找客户信息表是否有相同名字，性别，出生年月，身份证号的纪录，若有，取客户号
					/*
					//if (mLDPersonSchema.getIDType().equals("0")) {
						if ("0".equals(mLDPersonSchema.getIDType())) {
						if ((mLDPersonSchema.getName() != null)
								&& (mLDPersonSchema.getSex() != null)
								&& (mLDPersonSchema.getIDNo() != null)) {
							LDPersonDB tLDPersonDB = new LDPersonDB();
							tLDPersonDB.setName(mLDPersonSchema.getName());
							tLDPersonDB.setSex(mLDPersonSchema.getSex());
							tLDPersonDB.setBirthday(mLDPersonSchema
									.getBirthday());
							tLDPersonDB.setIDType("0"); // 证件类型为身份证
							tLDPersonDB.setIDNo(mLDPersonSchema.getIDNo());
							LDPersonSet tLDPersonSet = tLDPersonDB.query();
							if (tLDPersonSet != null) {
								if (tLDPersonSet.size() > 0) {
									mLDPersonSchema.setCustomerNo(tLDPersonSet
											.get(1).getCustomerNo());
								}
							}
						}
					}*/
				}
			}

		}

		return true;
	}

	private boolean CheckTBField(String operType) {
		// 保单 mLCPolBL mLCGrpPolBL
		// 投保人 mLCAppntBL mLCAppntGrpBL
		// 被保人 mLCInsuredBLSet mLCInsuredBLSetNew
		// 受益人 mLCBnfBLSet mLCBnfBLSetNew
		// 告知信息 mLCCustomerImpartBLSet mLCCustomerImpartBLSetNew
		// 特别约定 mLCSpecBLSet mLCSpecBLSetNew
		// 保费项表 mLCPremBLSet 保存特殊的保费项数据(目前针对磁盘投保，不用计算保费保额类型)
		// 给付项表 mLCGetBLSet
		// 一般的责任信息 mLCDutyBL
		// 责任表 mLCDutyBLSet
		String strMsg = "";
		boolean MsgFlag = false;
		// LCInsuredSchema tLCInsuredSchema = mLCInsuredBLSetNew.get(1);
		// LCDutySchema tLCDutySchema = mLCDutyBLSet.get(1);

		String RiskCode = mLCPolBL.getRiskCode();
		logger.debug("测试 riskcode=" + RiskCode);
		// logger.debug("测试 by yaory"+mLCPolBL.getPrem());
		try {
			VData tVData = new VData();
			CheckFieldCom tCheckFieldCom = new CheckFieldCom();

			// 计算要素
			FieldCarrier tFieldCarrier = new FieldCarrier();
			tFieldCarrier.setPrem(mLCPolBL.getPrem()); // 保费
			tFieldCarrier.setInsuredNo(mLCPolBL.getInsuredNo()); // add by yaory
			logger.debug("测试 fieldcarrier====" + mLCPolBL.getInsuredNo());
			tFieldCarrier.setAppAge(mLCPolBL.getInsuredAppAge()); // 被保人年龄
			tFieldCarrier.setInsuredName(mLCPolBL.getInsuredName()); // 被保人姓名
			tFieldCarrier.setSex(mLCPolBL.getInsuredSex()); // 被保人性别
			tFieldCarrier.setMult(mLCPolBL.getMult()); // 投保份数
			tFieldCarrier.setPolNo(mLCPolBL.getPolNo()); // 投保单号码
			tFieldCarrier.setMainPolNo(mLCPolBL.getMainPolNo()); // 主险号码
			tFieldCarrier.setRiskCode(mLCPolBL.getRiskCode()); // 险种编码
			tFieldCarrier.setCValiDate(mLCPolBL.getCValiDate()); // 生效日期
			tFieldCarrier.setAmnt(mLCPolBL.getAmnt()); // 保额
			tFieldCarrier.setInsuredBirthday(mLCPolBL.getInsuredBirthday()); // 被保人出生日期
			tFieldCarrier.setInsuYear(mLCPolBL.getInsuYear()); // 保险期间
			tFieldCarrier.setInsuYearFlag(mLCPolBL.getInsuYearFlag()); // 保险期间单位
			tFieldCarrier.setPayEndYear(mLCPolBL.getPayEndYear()); // 交费期间
			tFieldCarrier.setPayEndYearFlag(mLCPolBL.getPayEndYearFlag()); // 交费期间单位
			tFieldCarrier.setPayIntv(mLCPolBL.getPayIntv()); // 交费方式
			tFieldCarrier.setPayYears(mLCPolBL.getPayYears()); // 交费年期
			tFieldCarrier.setOccupationType(mLCPolBL.getOccupationType()); // 被保人职业类别
			tFieldCarrier.setGetYear(mLCPolBL.getGetYear()); // 领取年龄
			tFieldCarrier.setGrpPolNo(mLCPolBL.getGrpPolNo());
			tFieldCarrier.setContNo(mLCPolBL.getContNo());
			tFieldCarrier.setEndDate(mLCPolBL.getEndDate());
			// logger.debug("保单类型为："+mLCPolBL.getPolTypeFlag());
			tFieldCarrier.setPolTypeFlag(mLCPolBL.getPolTypeFlag());

			// if (mark != null && mark.equals("card"))
			// {
			//tFieldCarrier.setOccupationCode(mLCInsuredBL.getOccupationCode());
			// }
			//
			// LCPremSchema tLCPremSchema = null;
			////logger.debug("测试 by  yaory mlcpremblset="+mLCPremBLSet.size
			// ());
			// for (int i = 1; i <= mLCPremBLSet.size(); i++)
			// {
			//
			// tLCPremSchema = mLCPremBLSet.get(i);
			//
			// }
			/*
			 * Lis5.3 upgrade get
			 * logger.debug("进入算法表的管理费比例为"+tLCPremSchema
			 * .getManageFeeRate());
			 * tFieldCarrier.setManageFeeRate(tLCPremSchema.getManageFeeRate());
			 */
			// logger.debug("进入算法表的责任编码为"+tLCPremSchema.getDutyCode());
			// tLCPremSchema可能为空，不能直接使用的，，edit by yaory,,hehe,,,,,
			// try
			// {
			// tFieldCarrier.setDutyCode(tLCPremSchema.getDutyCode());
			// }
			// catch (Exception ex)
			// {
			// logger.debug("lmcheckfield中没有责任代码 ");
			// }
			// edit by yaory logger.debug("没有执行以下");
			if (mLCPolBL.getStandbyFlag1() != null) {
				tFieldCarrier.setStandbyFlag1(mLCPolBL.getStandbyFlag1());
			}
			if (mLCPolBL.getStandbyFlag2() != null) {
				tFieldCarrier.setStandbyFlag2(mLCPolBL.getStandbyFlag2());
			}
			if (mLCPolBL.getStandbyFlag3() != null) {
				tFieldCarrier.setStandbyFlag3(mLCPolBL.getStandbyFlag3());
			}
			/*
			 * Lis5.3 upgrade get if (mLCPolBL.getStandbyFlag4() != null) {
			 * tFieldCarrier.setStandbyFlag4(mLCPolBL.getStandbyFlag4()); } if
			 * (tLCInsuredSchema != null) {
			 * tFieldCarrier.setIDNo(tLCInsuredSchema.getIDNo()); //被保人证件号码（身份证）
			 * tFieldCarrier.setWorkType(tLCInsuredSchema.getWorkType()); //职业工种
			 * tFieldCarrier.setGrpName(tLCInsuredSchema.getGrpName()); //单位名称 }
			 */
			// logger.debug("Prepare Data");
			tVData.add(tFieldCarrier);

			LMCheckFieldSchema tLMCheckFieldSchema = new LMCheckFieldSchema();
			tLMCheckFieldSchema.setRiskCode(RiskCode);

			tLMCheckFieldSchema.setFieldName("TB" + operType); // 投保
			tVData.add(tLMCheckFieldSchema);
			if (tCheckFieldCom.CheckField(tVData) == false) {
				logger.debug("hehehehehe");
				this.mErrors.copyAllErrors(tCheckFieldCom.mErrors);

				return false;
			} else {
				// logger.debug("Check Data");

				LMCheckFieldSet mLMCheckFieldSet = tCheckFieldCom
						.GetCheckFieldSet();
				for (int n = 1; n <= mLMCheckFieldSet.size(); n++) {
					LMCheckFieldSchema tField = mLMCheckFieldSet.get(n);
					if ((tField.getReturnValiFlag() != null)
							&& tField.getReturnValiFlag().equals("N")) {
						if ((tField.getMsgFlag() != null)
								&& tField.getMsgFlag().equals("Y")) {
							MsgFlag = true;
							strMsg = strMsg + tField.getMsg() + " ; ";

							break;
						}
					}
				}
				if (MsgFlag == true) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ProposalBL";
					tError.functionName = "CheckTBField";
					tError.errorMessage = "数据有误：" + strMsg;
					this.mErrors.addOneError(tError);

					return false;
				}
			}
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalBL";
			tError.functionName = "CheckTBField";
			tError.errorMessage = "发生错误，请检验CheckField模块:" + ex;
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/*
	 * 如果增人时有保险计划,那么要一块添加保险计划下的所有险种 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean jixf 20051119 add
	 */
	private boolean contplandeal() {

		MMap subMap = null; // 提交结果集缓存


		LCContPlanDB tLCContPlanDB = new LCContPlanDB();
		LCContPlanSchema tLCContPlanSchema = new LCContPlanSchema();
		LCContPlanSet tLCContPlanSet = new LCContPlanSet();
		tLCContPlanDB.setGrpContNo(mLCContSchema.getGrpContNo());
		tLCContPlanDB.setContPlanCode(mLCInsuredSchema.getContPlanCode());
		tLCContPlanSet = tLCContPlanDB.query();
		tLCContPlanSchema = tLCContPlanSet.get(1);

		LCContPlanRiskDB tLCContPlanRiskDB = new LCContPlanRiskDB();
		LCContPlanRiskSchema tLCContPlanRiskSchema = new LCContPlanRiskSchema();
		LCContPlanRiskSet tLCContPlanRiskSet = new LCContPlanRiskSet();
		tLCContPlanRiskDB.setGrpContNo(mLCContSchema.getGrpContNo());
		tLCContPlanRiskDB.setContPlanCode(mLCInsuredSchema.getContPlanCode());
		tLCContPlanRiskSet = tLCContPlanRiskDB.query();

		// 对保险险种计划排序，确保主险在前面
		LCContPlanRiskSet mainPlanRiskSet = new LCContPlanRiskSet();
		LCContPlanRiskSet subPlanRiskSet = new LCContPlanRiskSet();
		LCContPlanRiskSchema contPlanRiskSchema = null;
		for (int t = 1; t <= tLCContPlanRiskSet.size(); t++) {
			contPlanRiskSchema = tLCContPlanRiskSet.get(t);
			if (contPlanRiskSchema.getRiskCode().equals(
					contPlanRiskSchema.getMainRiskCode())) {
				mainPlanRiskSet.add(contPlanRiskSchema);
			} else {
				subPlanRiskSet.add(contPlanRiskSchema);
			}

		}
		mainPlanRiskSet.add(subPlanRiskSet);
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(mLCContSchema.getGrpContNo());
		LCGrpPolSchema tLCGrpPolSchema = tLCGrpPolDB.getSchema();
		LCInsuredSchema tLCInsuredScheam = new LCInsuredSchema();
		tLCInsuredScheam = mLCInsuredSchema;
		for (int i = 1; i <= mainPlanRiskSet.size(); i++) {
			LCContPlanRiskSchema ttLCContPlanRiskSchema = mainPlanRiskSet
					.get(i);
			VData tData = prepareContPlanData(tLCInsuredScheam, mLCContSchema,
					ttLCContPlanRiskSchema);
			if (tData == null) {
				CError.buildErr(this,"数据处理失败ContInsuredBL-->dealData!");
				return false;
			}
//			如果是通过保障计划添加被保险人需要得到GetDutyKind add at 2008-12-15
			LMRiskDutyFactorSet tLMRiskDutyFactorSet =new LMRiskDutyFactorSet();
	        LMRiskDutyFactorDB tLMRiskDutyFactorDB =new LMRiskDutyFactorDB();
	        LCContPlanDutyParamSet tLCContPlanDutyParamSet = new LCContPlanDutyParamSet();
	        LCContPlanDutyParamDB tLCContPlanDutyParamDB = new LCContPlanDutyParamDB();
	        tLMRiskDutyFactorDB.setRiskCode(mainPlanRiskSet.get(i).getRiskCode());
	        tLMRiskDutyFactorDB.setCalFactor("GetDutyKind");
	        tLMRiskDutyFactorSet = tLMRiskDutyFactorDB.query();
	        if(tLMRiskDutyFactorSet.size()>0){
	        	//需要录入GetDutyKind
	        	tLCContPlanDutyParamDB.setGrpContNo(mainPlanRiskSet.get(i).getGrpContNo());
	        	tLCContPlanDutyParamDB.setMainRiskCode(mainPlanRiskSet.get(i).getRiskCode());
	        	tLCContPlanDutyParamDB.setContPlanCode(mainPlanRiskSet.get(i).getContPlanCode());
	        	tLCContPlanDutyParamDB.setCalFactor("GetDutyKind");
	        	tLCContPlanDutyParamSet =tLCContPlanDutyParamDB.query();
	        	if(tLCContPlanDutyParamSet.size()>0){
	        		//查询到责任对应的信息
//	        		for(int j=1;j<=tLCContPlanDutyParamSet.size();j++){
//	        			if(tLCContPlanDutyParamSet.get(j).getCalFactorValue()==null||
//	        					tLCContPlanDutyParamSet.get(j).getCalFactorValue().equals("")){
//	        				//没有查询到应该录入的GetDutyKind
//	        				CError.buildErr(this, "险种"+mainPlanRiskSet.get(i).getRiskCode()+"的保障计划中没有查询到相应的‘领取方式’！");
//	        				return false;
//	        			}else{
	        				//传入数据在循环责任是取出GetDutyKind
	        				tData.add(tLCContPlanDutyParamSet);
//	        			}
//	        		}
	        	}
	        }
			// 提交生成数据
			MMap oneRisk = submiDatattoProposalBL(tData, mLCContSchema,
					tLCGrpPolSchema);
			if (oneRisk == null) {
				// 失败

				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据处理失败ContInsuredBL-->dealData!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				// 成功
				if (subMap == null) {
					subMap = oneRisk;
				} else {
					subMap.add(oneRisk);
				}
			}

		}
		map.add(subMap);
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql("update lccont set amnt=(select sum(amnt) from lcpol where contno='"
				+ "?contno?"
				+ "'),prem=(select sum(prem) from lcpol where contno='"
				+ "?contno?" + "') where contno='"
				+ "?contno?" + "'");
		sqlbv2.put("contno",mLCContSchema.getContNo());
		map.put(sqlbv2, "UPDATE");

        return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		String tNo;
		String tLimit;
		boolean tISNewPerson = false;
		// 当是个人时，由界面上取得合同号，当是团体下个人的时候，需要在保存个人合同下第一个被保险人的时候，生成LCCont
		if (mDealFlag.equals("2")) {
			if (mLCContSchema.getContNo() == null
					|| mLCContSchema.getContNo().equals("")) {
				mLCGrpContDB.setGrpContNo(mLCContSchema.getGrpContNo());
				if (!mLCGrpContDB.getInfo()) {
					this.mErrors.copyAllErrors(mLCGrpContDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "ContInsuredBL";
					tError.functionName = "deleteData";
					tError.errorMessage = "获取团体合同数据时失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				logger.debug("团单处理");
				mLCContSchema.setManageCom(mLCGrpContDB.getManageCom());
				mLCContSchema.setAgentCode(mLCGrpContDB.getAgentCode());
				mLCContSchema.setAgentGroup(mLCGrpContDB.getAgentGroup());
				mLCContSchema.setAgentCom(mLCGrpContDB.getAgentCom());
				mLCContSchema.setAgentType(mLCGrpContDB.getAgentType());
				mLCContSchema.setSaleChnl(mLCGrpContDB.getSaleChnl());
				mLCContSchema.setSignCom(mGlobalInput.ManageCom);
				mLCContSchema.setAppntNo(mLCGrpContDB.getAppntNo());
				mLCContSchema.setAppntName(mLCGrpContDB.getGrpName());
				mLCContSchema.setInputDate(PubFun.getCurrentDate());
				mLCContSchema.setInputTime(PubFun.getCurrentTime());
				mLCContSchema.setPolApplyDate(mLCGrpContDB.getCValiDate());

				tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
				tNo = PubFun1.CreateMaxNo("ProposalContNo", tLimit);
				mLCContSchema.setContNo(tNo);

				/**
				 * zhangzheng 2009-02-12
				 * 无名单的contno不用特殊生成
				 */
//				// 无名单补名单，则根据生成规则生成保单号
//				if (mLCInsuredSchema.getPluralityType() != null
//						&& !mLCInsuredSchema.getPluralityType().equals("null")
//						&& !mLCInsuredSchema.getPluralityType().equals("")) {
//					String tReturn = PubFun1.CreateMaxNo("CONTNO", "86"); //
//					String newContNo = PubFun1.creatVerifyDigit(tReturn);
//					mLCContSchema.setContNo(newContNo);
//				}
				// 合同上去掉处理机构
				// if (mLCInsuredSchema.getExecuteCom() == null ||
				// mLCInsuredSchema.getExecuteCom().equals("")) {
				// mLCContSchema.setExecuteCom(mGlobalInput.ManageCom);
				// }
				// else {
				//mLCContSchema.setExecuteCom(mLCInsuredSchema.getExecuteCom());
				// }
				mLCContSchema.setProposalContNo(tNo);
				mLCContSchema.setContType(ContType);
				mLCContSchema.setInsuredNo("0"); //暂设
				mLCContSchema.setPolType(tPolTypeFlag);
				if (tInsuredPeoples == null || tInsuredPeoples.equals("")) {
					//tongmeng 2009-03-19 modify
					//如果为空,设置为1
					tInsuredPeoples = "1";
					mLCContSchema.setPeoples(1);
				} else {
					mLCContSchema.setPeoples(tInsuredPeoples);
				}
				mLCContSchema.setCardFlag("0");
				String mSavePolType = (String) mTransferData
						.getValueByName("SavePolType");
				mLCContSchema.setPolType(tPolTypeFlag);
				if (mLCContSchema.getAppFlag() == null
						|| mLCContSchema.getAppFlag().equals("")) {
					if (mSavePolType != null && !mSavePolType.equals("")
							&& !mSavePolType.equals("null")) {
						if (mSavePolType.trim().equals("")) {
							mLCContSchema.setAppFlag("0");
							mLCContSchema.setState("0");
						} else {
							mLCContSchema.setAppFlag(mSavePolType);
							mLCContSchema.setState("0");
						}
					} else {
						mLCContSchema.setAppFlag("0");
						mLCContSchema.setState("0");
					}
				}

				mLCContSchema.setApproveFlag("0");
				mLCContSchema.setUWFlag("0");
				mLCContSchema.setOperator(mGlobalInput.Operator);
				mLCContSchema.setMakeDate(theCurrentDate);
				mLCContSchema.setMakeTime(theCurrentTime);
			}
		} else { // 针对个人合同单处理被保人数
			logger.debug("人数是----***：" + mLCContSchema.getPeoples());
			if (mLCContSchema.getPeoples() == 0) {
				mLCContSchema.setPeoples(1);
			} else {
				//tongmeng 2009-03-19 只有新增才修改peoples
				if (mOperate.equals("INSERT||CONTINSURED"))
				{
					mLCContSchema.setPeoples(mLCContSchema.getPeoples() + 1);
					
				}
			}
		}

		if (mLCContSchema.getPolType() == null
				|| mLCContSchema.getPolType().equals("")) {
			mLCContSchema.setPolType("0");
		}

		logger.debug("管理机构===" + manageCom);
		// tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
		tLimit = PubFun.getNoLimit(manageCom);
		logger.debug("测试=====" + tLimit + "-"
				+ mLDPersonSchema.getIDType() + "-"
				+ mLDPersonSchema.getBirthday());
		logger.debug("测试=====" + mLDPersonSchema.getName() + "-"
				+ mLDPersonSchema.getSex() + "-" + mLDPersonSchema.getIDNo());
		// 客户信息处理
		String ss = "";
		ExeSQL ttExeSQL = new ExeSQL();
		//moddify by duanyh 2009-02-04 
		//自助卡单虚拟被保人与投保人为同一人，此处无法进行客户号合并，直接取此保单的投保人客户号作为被保人客户号
		if(KDCheckFlag != null && KDCheckFlag.equals("1"))
		{
			ss = mLCContSchema.getAppntNo();
			logger.debug("==>自助卡单，投被保人客户号都为："+ss);
		}
		else
		{
			if (mLCContSchema.getPolType().equals("0")) {
//				String tsql = " select customerno from ldperson where 1=1 "
//						+ " and name = '" + mLDPersonSchema.getName() + "'"
//						+ " and sex = '" + mLDPersonSchema.getSex() + "'"
//						+ " and trim(ComPareID(trim(IDNo),trim(IDType),'"
//						+ mLDPersonSchema.getIDNo() + "','"
//						+ mLDPersonSchema.getIDType() + "')) = '1'"
//						+ " and IDType = '" + mLDPersonSchema.getIDType() + "'"
//						+ " and Birthday = '" + mLDPersonSchema.getBirthday() + "'";

//				ss = ttExeSQL.getOneValue(tsql);
				logger.debug("sfdjfldjfl:" + ss);
				if(mDealFlag.equals("2")){
					if(ss==null||"".equals(ss)){
						ss=getGrpCustomerNo(mLCInsuredSchema,mLCAddressSchema);
					}
					if("Error".equals(ss))
					{
						logger.debug("传入的被保人信息为空，请确认！");
						return false;
					}
				}
				if(mDealFlag.equals("1")){
					if("card".equals(mark)){
						//卡单时， 如果投保人与被保人关系为00-本人 被保人InsuredNo取投保人AppntNo
						if("00".equals(CardRelationToInsured)){
							ss =mLCContSchema.getAppntNo();
							mLDPersonSchema.setCustomerNo("");//将ldperson中的客户号删除 用ss
						}else{
							//不是同一人时 保留页面录入的被保人
							ss=getCustomerNo(mLCInsuredSchema,mLCAddressSchema);
						}
					}else{
						if(ss==null||"".equals(ss)){
							ss=getCustomerNo(mLCInsuredSchema,mLCAddressSchema);
						}
					}
				}
			}
		}
	
		// yaory
		// 判断是否需要新建客户
		if ((mLDPersonSchema.getCustomerNo() == null || ("")
				.equals(mLDPersonSchema.getCustomerNo()))
				&& (ss == null || ss.equals(""))) {
			tISNewPerson = true;
			tNo = PubFun1.CreateMaxNo("CUSTOMERNO", "SN");
			mLDPersonSchema.setCustomerNo(tNo);
			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			logger.debug("######mLDPersonSchema.getCustomerNo()=="
					+ mLDPersonSchema.getCustomerNo());
			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%mLCContSchema.getPolType()="
							+ mLCContSchema.getPolType());

			// if (mLCContSchema.getPolType().equals("0"))
			{ // 个人客户
				mLDPersonSchema.setMakeDate(theCurrentDate);
				mLDPersonSchema.setMakeTime(theCurrentTime);
				mLDPersonSchema.setModifyDate(theCurrentDate);
				mLDPersonSchema.setModifyTime(theCurrentTime);
				mLDPersonSchema.setOperator(mGlobalInput.Operator);
				tLDPersonSchema = mLDPersonSchema; // tLDPersonSet包含需要新建的客户
			}
			if (mLCContSchema.getPolType().equals("0")) { // 个人客户
			} else {
				if (mLCContSchema.getPolType().equals("2")) {
					mLCInsuredSchema.setName("公共账户");
				} else if (mLCContSchema.getPolType().equals("3")) {
					mLCInsuredSchema.setName("法人账户");
				} else {
					mLCInsuredSchema.setName("无名单");
				}
				mLDPersonSchema.setSex("2"); // 无名单和公共账户，法人账户保存为2
				mLDPersonSchema.setBirthday("1899-12-31");
				if (mLCInsuredSchema.getSex() == null
						|| mLCInsuredSchema.getSex().equals("")) {
					mLCInsuredSchema.setSex("2");
				}
				if (mLCInsuredSchema.getBirthday() == null
						|| mLCInsuredSchema.getBirthday().equals("")) { //如果是无名单，
					// 没有录入年龄
					// ，
					// 默认是30
					int tInsuredAge = 0;
					if (tInsuredAppAge == null || tInsuredAppAge.equals("")) { // 如果投保年龄没有录入
						tInsuredAge = 0;

					} else {
						tInsuredAge = Integer.parseInt(tInsuredAppAge); // 前台录入年龄
					}

					// 年龄所在年=系统年-年龄
					String year = Integer.toString(Integer.parseInt(StrTool
							.getYear())
							- tInsuredAge);
					String brithday = StrTool.getDate(year, StrTool.getMonth(),
							StrTool.getDay());
					brithday = StrTool.replace(brithday, "/", "-");
					if (tInsuredAge == 0) {
						mLCInsuredSchema.setBirthday("1899-12-31");
					} else {
						mLCInsuredSchema.setBirthday(brithday);
					}
				}
			}
		}
		if (mLDPersonSchema.getCustomerNo() != null
				&& !(mLDPersonSchema.getCustomerNo().equals(""))) {
			mLCInsuredSchema.setInsuredNo(mLDPersonSchema.getCustomerNo());
			mLCContSchema.setInsuredNo(mLDPersonSchema.getCustomerNo());
		} else {
			mLCInsuredSchema.setInsuredNo(ss);
			mLDPersonSchema.setCustomerNo(ss);
			mLCContSchema.setInsuredNo(ss);
			tISNewPerson = false; // 无需新建客户
		}
		if (!tISNewPerson) {
			if (mOperate.equals("INSERT||CONTINSURED")) {
				LCInsuredDB tLCInsuredDB = new LCInsuredDB();
				if (mDealFlag.equals("2")) {
					tLCInsuredDB.setGrpContNo(mLCContSchema.getGrpContNo());
					tLCInsuredDB.setInsuredNo(mLDPersonSchema.getCustomerNo());
					if (tLCInsuredDB.query() != null && tLCInsuredDB.query().size() > 0) {

						if("NI".equals(mTransferData.getValueByName("EdorType"))){

							LCContDB tLCContDB = new LCContDB();
							tLCContDB.setGrpContNo(mLCContSchema.getGrpContNo());
							tLCContDB.setInsuredNo(mLDPersonSchema.getCustomerNo());
							tLCContDB.setAppFlag("1");
							LCContSet tLCContSet = tLCContDB.query();
							if(tLCContSet != null && tLCContSet.size()>0){
								CError tError = new CError();
								tError.moduleName = "ContBL";
								tError.functionName = "checkData";
								tError.errorMessage = "该团体合同下已经存在该被保险人(姓名："+mLDPersonSchema.getName()+")的有效保单，不能重复添加!";
								this.mErrors.addOneError(tError);
								return false;
							}

						}else{
							CError tError = new CError();
							tError.moduleName = "ContBL";
							tError.functionName = "checkData";
							tError.errorMessage = "该团体合同下已经存在该被保险人(姓名："+mLDPersonSchema.getName()+")，不能重复添加!";
							this.mErrors.addOneError(tError);
							return false;
						}
					}
				} else {
					tLCInsuredDB.setContNo(mLCContSchema.getContNo());
					tLCInsuredDB.setInsuredNo(mLDPersonSchema.getCustomerNo());
					if (tLCInsuredDB.getInfo()) {
						CError tError = new CError();
						tError.moduleName = "ContIsuredBL";
						tError.functionName = "DealDate";
						tError.errorMessage = "不能重复添加同一客户!";
						this.mErrors.addOneError(tError);

					}
				}
			}
			// 将需要更新的字段赋值
			// 需要更新的字段有驾照、婚姻状况、国籍等
			if (mOperate.equals("UPDATE||CONTINSURED")
					|| mOperate.equals("INSERT||CONTINSURED")) {
				LDPersonDB aLDPersonDB = new LDPersonDB();
				aLDPersonDB.setCustomerNo(this.mLDPersonSchema.getCustomerNo());
				aLDPersonDB.getInfo();
				logger.debug("######################################");
				logger.debug("######################################");
				logger.debug("######################################");
				logger.debug("##################HERE!###############");
				tLDPersonSchema = (LDPersonSchema) aLDPersonDB.getSchema();
				if (mLDPersonSchema.getNativePlace() != null
						&& !mLDPersonSchema.getNativePlace().equals("")) {
					tLDPersonSchema.setNativePlace(mLDPersonSchema
							.getNativePlace());
				}
				if (mLDPersonSchema.getNationality() != null
						&& !mLDPersonSchema.getNationality().equals("")) {
					tLDPersonSchema.setNationality(mLDPersonSchema
							.getNationality());
				}
				if (mLDPersonSchema.getRgtAddress() != null
						&& !mLDPersonSchema.getRgtAddress().equals("")) {
					tLDPersonSchema.setRgtAddress(mLDPersonSchema
							.getRgtAddress());
				}
				if (mLDPersonSchema.getMarriage() != null
						&& !mLDPersonSchema.getMarriage().equals("")) {
					tLDPersonSchema.setMarriage(mLDPersonSchema.getMarriage());
				}
				if (mLDPersonSchema.getDegree() != null
						&& !mLDPersonSchema.getDegree().equals("")) {
					tLDPersonSchema.setDegree(mLDPersonSchema.getDegree());
				}
				if (mLDPersonSchema.getOccupationType() != null
						&& !mLDPersonSchema.getOccupationType().equals("")) {
					tLDPersonSchema.setOccupationType(mLDPersonSchema
							.getOccupationType());
				}
				if (mLDPersonSchema.getOccupationCode() != null
						&& !mLDPersonSchema.getOccupationCode().equals("")) {
					tLDPersonSchema.setOccupationCode(mLDPersonSchema
							.getOccupationCode());
				}
				if (mLDPersonSchema.getWorkType() != null
						&& !mLDPersonSchema.getWorkType().equals("")) {
					tLDPersonSchema.setWorkType(mLDPersonSchema.getWorkType());
				}
				if (mLDPersonSchema.getPluralityType() != null
						&& !mLDPersonSchema.getPluralityType().equals("")) {
					tLDPersonSchema.setPluralityType(mLDPersonSchema
							.getPluralityType());
				}
				if (mLDPersonSchema.getSmokeFlag() != null
						&& !mLDPersonSchema.getSmokeFlag().equals("")) {
					tLDPersonSchema
							.setSmokeFlag(mLDPersonSchema.getSmokeFlag());
				}
				if (mLDPersonSchema.getLicenseType() != null
						&& !mLDPersonSchema.getLicenseType().equals("")) {
					tLDPersonSchema.setLicenseType(mLDPersonSchema
							.getLicenseType());
				}
				if (mLDPersonSchema.getGrpName() != null
						&& !mLDPersonSchema.getGrpName().equals("")) {
					tLDPersonSchema.setGrpName(mLDPersonSchema.getGrpName());
				}
				if (mLDPersonSchema.getIDExpDate() != null
						&& !mLDPersonSchema.getIDExpDate().equals("")) {
					tLDPersonSchema.setIDExpDate(mLDPersonSchema.getIDExpDate());
				}
				if (mLDPersonSchema.getSocialInsuFlag() != null
						&& !mLDPersonSchema.getSocialInsuFlag().equals("")) {
					tLDPersonSchema.setSocialInsuFlag(mLDPersonSchema.getSocialInsuFlag());
				}
				if (mLDPersonSchema.getLanguage() != null
						&& !mLDPersonSchema.getLanguage().equals("")) {
					tLDPersonSchema.setLanguage(mLDPersonSchema.getLanguage());
				}
				tLDPersonSchema.setModifyDate(theCurrentDate);
				tLDPersonSchema.setModifyTime(theCurrentTime);
			}
		}

		if (!mLCContSchema.getPolType().equals("0")) {
			if (mLCContSchema.getPolType().equals("2")) {
				mLCInsuredSchema.setName("公共账户");
			} else {
				mLCInsuredSchema.setName("无名单");
			}

			if (mLCInsuredSchema.getSex() == null
					|| mLCInsuredSchema.getSex().equals("")) {
				mLCInsuredSchema.setSex("0");
			}
			if (mLCInsuredSchema.getBirthday() == null
					|| mLCInsuredSchema.getBirthday().equals("")) { // 如果是无名单，
				// 没有录入年龄
				// ，默认是30
				int tInsuredAge = 0;
				tInsuredAge = Integer.parseInt(tInsuredAppAge); // 前台录入年龄

				// 年龄所在年=系统年-年龄
				String year = Integer.toString(Integer.parseInt(StrTool
						.getYear())
						- tInsuredAge);
				String brithday = StrTool.getDate(year, StrTool.getMonth(),
						StrTool.getDay());
				brithday = StrTool.replace(brithday, "/", "-");
				mLCInsuredSchema.setBirthday(brithday);
			}
		}

		if (mDealFlag.equals("1")) {
			
			// 产生客户地址
			if (mLCAddressSchema != null) {
				logger.debug("产生客户地址 in continsuredbl=="
						+ mLCAddressSchema.getPostalAddress() + "-"
						+ mLCAddressSchema.getAddressNo());
				// if
				// (!StrTool.compareString(mLCAddressSchema.getPostalAddress(),
				// "") &&
				// (StrTool.compareString("" + mLCAddressSchema.getAddressNo(),
				// "0")))
				if (mLCAddressSchema.getAddressNo() == 0) {
					try {
						tSSRS = new SSRS();
						String sql = "Select Case When max(AddressNo) Is Null Then 0 Else max(AddressNo) End from LCAddress where CustomerNo='"
								+ "?CustomerNo?" + "'";
						SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
						sqlbv3.sql(sql);
						sqlbv3.put("CustomerNo", mLDPersonSchema.getCustomerNo());
						ExeSQL tExeSQL = new ExeSQL();
						tSSRS = tExeSQL.execSQL(sqlbv3);
						logger.debug("地址号====" + tSSRS.MaxRow + "-" + sql);
						Integer firstinteger = Integer.valueOf(tSSRS.GetText(1,
								1));
						int ttNo = firstinteger.intValue() + 1;
						Integer integer = new Integer(ttNo);
						tNo = integer.toString();
						logger.debug("得到的地址码是：" + tNo);
						mLCAddressSchema.setAddressNo(tNo);

					} catch (Exception e) {
						CError tError = new CError();
						tError.moduleName = "ContIsuredBL";
						tError.functionName = "createAddressNo";
						tError.errorMessage = "地址码超长,生成号码失败,请先删除原来的超长地址码!";
						this.mErrors.addOneError(tError);
						mLCAddressSchema.setAddressNo(0);
					}
					logger.debug("客户地址处理完毕");
					mLCAddressSchema.setCustomerNo(mLDPersonSchema
							.getCustomerNo());
					mLCAddressSchema.setMakeDate(theCurrentDate);
					mLCAddressSchema.setMakeTime(theCurrentTime);
					mLCAddressSchema.setModifyDate(theCurrentDate);
					mLCAddressSchema.setModifyTime(theCurrentTime);
					mLCAddressSchema.setOperator(mGlobalInput.Operator);
					tLCAddressSchema = new LCAddressSchema();
					tLCAddressSchema.setSchema(mLCAddressSchema); // tLCAddressSchema包含需要新建的客户地址
				}
			}
		}
		//tongmeng 2009-03-30 add
		//如果是团险,直接update地址表
		else if(mDealFlag.equals("2"))
		{
			// 产生客户地址
			if (mLCAddressSchema != null) {
				logger.debug("产生客户地址 in continsuredbl=="
						+ mLCAddressSchema.getPostalAddress() + "-"
						+ mLCAddressSchema.getAddressNo());
				// if
				// (!StrTool.compareString(mLCAddressSchema.getPostalAddress(),
				// "") &&
				// (StrTool.compareString("" + mLCAddressSchema.getAddressNo(),
				// "0")))
				//if (mLCAddressSchema.getAddressNo() == 0) 
				{

					logger.debug("客户地址处理完毕");
					mLCAddressSchema.setCustomerNo(mLDPersonSchema
							.getCustomerNo());
					mLCAddressSchema.setMakeDate(theCurrentDate);
					mLCAddressSchema.setMakeTime(theCurrentTime);
					mLCAddressSchema.setModifyDate(theCurrentDate);
					mLCAddressSchema.setModifyTime(theCurrentTime);
					mLCAddressSchema.setOperator(mGlobalInput.Operator);
					tLCAddressSchema = new LCAddressSchema();
					tLCAddressSchema.setSchema(mLCAddressSchema); // tLCAddressSchema包含需要新建的客户地址
				}
			}
		}
		// tongmeng 2008-08-18 add
		// 泰康程序产生
		if (mLLAccountSchema != null) {
			if (mLLAccountSchema.getBankCode() != null
					&& !mLLAccountSchema.getBankCode().equals("")
					&& mLLAccountSchema.getAccount() != null
					&& !mLLAccountSchema.getAccount().equals("")) {
				mLLAccountSchema.setInsuredNo(mLDPersonSchema.getCustomerNo());
				if (mLLAccountSchema.getName() == null
						&& ("").equals(mLLAccountSchema.getName())) {
					mLLAccountSchema.setName(mLDPersonSchema.getName());
				}
				mLLAccountSchema.setInsuredName(mLDPersonSchema.getName());
				mLLAccountSchema.setContNo(mLCContSchema.getContNo());
				mLLAccountSchema.setMakeDate(theCurrentDate);
				mLLAccountSchema.setMakeTime(theCurrentTime);
				mLLAccountSchema.setModifyDate(theCurrentDate);
				mLLAccountSchema.setModefyTime(theCurrentTime);
				mLLAccountSchema.setOperator(mGlobalInput.Operator);
			}
		}

		// 产生客户账户
		// if (mLCAccountSchema != null) {
		// if (mLCAccountSchema.getBankCode() != null &&
		// mLCAccountSchema.getBankAccNo() != null) {
		// mLCAccountSchema.setCustomerNo(mLDPersonSchema.getCustomerNo());
		// if (mLCAccountSchema.getAccName() == null &&
		// ("").equals(mLCAccountSchema.getAccName()))
		// mLCAccountSchema.setAccName(mLDPersonSchema.getName());
		// mLCAccountSchema.setMakeDate(theCurrentDate);
		// mLCAccountSchema.setMakeTime(theCurrentTime);
		// mLCAccountSchema.setModifyDate(theCurrentDate);
		// mLCAccountSchema.setModifyTime(theCurrentTime);
		// mLCAccountSchema.setOperator(mGlobalInput.Operator);
		//
		// map.put(mLCAccountSchema, "INSERT");
		// }
		// }
		// 如果是主被保险人，设置合同表相关信息
		logger.debug("主被保险人==="
				+ mLCInsuredSchema.getRelationToMainInsured());
		if (("00").equals(mLCInsuredSchema.getRelationToMainInsured())) {
			mLCContSchema.setInsuredBirthday(mLCInsuredSchema.getBirthday());
			mLCContSchema.setInsuredIDNo(mLCInsuredSchema.getIDNo());
			mLCContSchema.setInsuredIDType(mLCInsuredSchema.getIDType());
			mLCContSchema.setInsuredName(mLCInsuredSchema.getName());
			mLCContSchema.setInsuredNo(mLCInsuredSchema.getInsuredNo());
			mLCContSchema.setInsuredSex(mLCInsuredSchema.getSex());
			// 如果是家庭单，在录入主被保险人时产生家庭保障号（在前台控制必须首先录入主被保险人）
			if (("1").equals(mLCContSchema.getFamilyType())) { // 家庭单
				if (mLCContSchema.getFamilyID() == null
						|| mLCContSchema.getFamilyID().equals("")) {
					tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
					tNo = PubFun1.CreateMaxNo("FamilyID", 10);
					// mLCInsuredSchema.setFamilyID(tNo);
					mLCContSchema.setFamilyID(tNo);
				}
			}
		}

		mLCInsuredSchema.setAddressNo("" + mLCAddressSchema.getAddressNo());
		mLCInsuredSchema.setContNo(mLCContSchema.getContNo());
		mLCInsuredSchema.setPrtNo(mLCContSchema.getPrtNo());
		mLCInsuredSchema.setAppntNo(mLCContSchema.getAppntNo());
		mLCInsuredSchema.setGrpContNo(mLCContSchema.getGrpContNo());
		//wangxw 20100920 子公司号码
		//mLCInsuredSchema.setGrpNo(mGrpNo);

		mLCInsuredSchema.setManageCom(mLCContSchema.getManageCom()); // 管理理机构
		if (mLCInsuredSchema.getExecuteCom() == null
				|| mLCInsuredSchema.getExecuteCom().equals("")) {
			mLCInsuredSchema.setExecuteCom(mLCInsuredSchema.getManageCom()); // 处理机构
		}
		mLCInsuredSchema.setSequenceNo(tSequenceNo);
		mLCInsuredSchema.setFamilyID(mLCContSchema.getFamilyID());
		mLCInsuredSchema.setModifyDate(theCurrentDate);
		mLCInsuredSchema.setModifyTime(theCurrentTime);
		mLCInsuredSchema.setOperator(mGlobalInput.Operator);
//		mLCInsuredSchema.setSocialInsuFlag("0");//初始化社保标记
		//tongmeng 2009-03-19 add
		//设置被保人数
		mLCInsuredSchema.setInsuredPeoples(this.tInsuredPeoples);
		mLCInsuredSchema.setGrpNo(this.mOLDLCInsuredDB.getGrpNo());
		if (mDealFlag.equals("2")) {
			// 对于团险添加被保人，对被保人序号赋值
			// 取最大值加1
			if (mOperate.equals("INSERT||CONTINSURED")) {
				// 在以下SQL增加了hint词/*+RULE*/来提高效率
				String tSQL = "select /*+RULE*/ (case when max(customerseqno) is null then 0 else max(customerseqno) end) from lcinsured "
						+ "where grpcontno='"
						+ "?grpcontno?"
						+ "'";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tSQL);
				sqlbv4.put("grpcontno", mLCContSchema.getGrpContNo());
				ExeSQL tExeSQL = new ExeSQL();
				int tCustomerNo = Integer.parseInt(tExeSQL.getOneValue(sqlbv4));
				mLCInsuredSchema.setCustomerSeqNo(tCustomerNo + 1);
			}
		}
		logger.debug("处理告知信息");
		// 处理告知信息
		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			// 设置所有告知信息得客户号码
			for (int i = 1; i <= mLCCustomerImpartSet.size(); i++) {
				mLCCustomerImpartSet.get(i)
						.setContNo(mLCContSchema.getContNo());
				mLCCustomerImpartSet.get(i).setGrpContNo(
						mLCContSchema.getGrpContNo());
				mLCCustomerImpartSet.get(i).setPrtNo(mLCContSchema.getPrtNo());
				mLCCustomerImpartSet.get(i).setProposalContNo(
						mLCContSchema.getProposalContNo());
				mLCCustomerImpartSet.get(i).setCustomerNo(
						mLCInsuredSchema.getInsuredNo());
				
				//处理被保人社保标记
				if(mLCContSchema.getPrtNo()!=null && !mLCContSchema.getPrtNo().equals("") 
						&& mLCContSchema.getPrtNo().length()==14 && mLCContSchema.getPrtNo().substring(2,4).equals("51"))//家庭单
				{
					/*if (mLCCustomerImpartSet.get(i).getImpartVer()!=null && !mLCCustomerImpartSet.get(i).getImpartVer().equals("")
							&& mLCCustomerImpartSet.get(i).getImpartCode()!=null && !mLCCustomerImpartSet.get(i).getImpartCode().equals("")
							&& mLCCustomerImpartSet.get(i).getCustomerNoType()!=null && !mLCCustomerImpartSet.get(i).getCustomerNoType().equals("")
							&& (("D02".equals(mLCCustomerImpartSet.get(i).getImpartVer())
							&& "D0118".equals(mLCCustomerImpartSet.get(i)
									.getImpartCode())) || ("A02".equals(mLCCustomerImpartSet.get(i).getImpartVer())
											&& "A0119".equals(mLCCustomerImpartSet.get(i)
													.getImpartCode())))
							&& "I".equals(mLCCustomerImpartSet.get(i)
									.getCustomerNoType())) {
						mLCInsuredSchema.setSocialInsuFlag("1");
					}*/
					if(mLCInsuredSchema.getSocialInsuFlag()==null||"".equals(mLCInsuredSchema.getSocialInsuFlag())){
						mLCInsuredSchema.setSocialInsuFlag("0");//如果界面初入值为空则置默认值为0
						if (mLCCustomerImpartSet.get(i).getImpartVer()!=null && !mLCCustomerImpartSet.get(i).getImpartVer().equals("")
								&& mLCCustomerImpartSet.get(i).getImpartCode()!=null && !mLCCustomerImpartSet.get(i).getImpartCode().equals("")
								&& mLCCustomerImpartSet.get(i).getCustomerNoType()!=null && !mLCCustomerImpartSet.get(i).getCustomerNoType().equals("")
								&& ("D02".equals(mLCCustomerImpartSet.get(i).getImpartVer())
								&& "D0118".equals(mLCCustomerImpartSet.get(i)
										.getImpartCode()))
								&& "I".equals(mLCCustomerImpartSet.get(i)
										.getCustomerNoType())) {
							mLCInsuredSchema.setSocialInsuFlag("1");
						}else{
							mLCInsuredSchema.setSocialInsuFlag("0");
						}
					}
				}
				else 
				{
					if(mLCInsuredSchema.getSocialInsuFlag()==null||"".equals(mLCInsuredSchema.getSocialInsuFlag())){
						mLCInsuredSchema.setSocialInsuFlag("0");//如果界面初入值为空则置默认值为0
						if (mLCCustomerImpartSet.get(i).getImpartVer()!=null && !mLCCustomerImpartSet.get(i).getImpartVer().equals("")
								&& mLCCustomerImpartSet.get(i).getImpartCode()!=null && !mLCCustomerImpartSet.get(i).getImpartCode().equals("")
								&& mLCCustomerImpartSet.get(i).getCustomerNoType()!=null && !mLCCustomerImpartSet.get(i).getCustomerNoType().equals("")
								&& ("A02".equals(mLCCustomerImpartSet.get(i).getImpartVer())
												&& "A0119".equals(mLCCustomerImpartSet.get(i)
														.getImpartCode()))
								&& "I".equals(mLCCustomerImpartSet.get(i)
										.getCustomerNoType())) {
							mLCInsuredSchema.setSocialInsuFlag("1");
						}else{
							mLCInsuredSchema.setSocialInsuFlag("0");
						}
					}
				}	
				
			}
			CustomerImpartBL mCustomerImpartBL = new CustomerImpartBL();
			VData tempVData = new VData();
			tempVData.add(mLCCustomerImpartSet);
			tempVData.add(mGlobalInput);
			mCustomerImpartBL.submitData(tempVData, "IMPART||DEAL");
			if (mCustomerImpartBL.mErrors.needDealError()) {
				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "dealData";
				tError.errorMessage = mCustomerImpartBL.mErrors.getFirstError()
						.toString();
				this.mErrors.addOneError(tError);
				return false;
			}
			tempVData.clear();
			tempVData = mCustomerImpartBL.getResult();
			if (null != (LCCustomerImpartSet) tempVData.getObjectByObjectName(
					"LCCustomerImpartSet", 0)) {
				mLCCustomerImpartSet = (LCCustomerImpartSet) tempVData
						.getObjectByObjectName("LCCustomerImpartSet", 0);
				logger.debug("告知条数" + mLCCustomerImpartSet.size());
			} else {
				logger.debug("告知条数为空");
			}
			if (null != (LCCustomerImpartParamsSet) tempVData
					.getObjectByObjectName("LCCustomerImpartParamsSet", 0)) {
				mLCCustomerImpartParamsSet = (LCCustomerImpartParamsSet) tempVData
						.getObjectByObjectName("LCCustomerImpartParamsSet", 0);
			}
		}
		
		//处理客户表社保标记		
		tLDPersonSchema.setSocialInsuFlag(mLCInsuredSchema.getSocialInsuFlag());
		
		// 处理告知明细信息
		if (mLCCustomerImpartDetailSet != null
				&& mLCCustomerImpartDetailSet.size() > 0) {
			// 设置所有告知明细信息得客户号码
			for (int i = 1; i <= mLCCustomerImpartDetailSet.size(); i++) {
				String sql = "Select Case When max(SubSerialNo) Is Null Then '0' Else max(SubSerialNo) End from LCCustomerImpartDetail where GrpContNo='"
						+ "?GrpContNo?"
						+ "' and ProposalContNo='"
						+ "?ProposalContNo?"
						+ "' and ImpartVer='"
						+ "?ImpartVer?"
						+ "' and  ImpartCode='"
						+ "?ImpartCode?"
						+ "' and CustomerNo='"
						+ "?CustomerNo?"
						+ "' and CustomerNoType='1'";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(sql);
				sqlbv5.put("GrpContNo", mLCContSchema.getContNo());
				sqlbv5.put("ProposalContNo", mLCContSchema.getProposalContNo());
				sqlbv5.put("ImpartVer", mLCCustomerImpartDetailSet.get(i).getImpartVer());
				sqlbv5.put("ImpartCode", mLCCustomerImpartDetailSet.get(i).getImpartCode());
				sqlbv5.put("CustomerNo", mLCInsuredSchema.getInsuredNo());
				ExeSQL tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(sqlbv5);
				Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
				int ttNo = firstinteger.intValue() + 1;
				Integer integer = new Integer(ttNo);
				tNo = integer.toString();
				logger.debug("得到的告知码是：" + tNo);

				mLCCustomerImpartDetailSet.get(i).setSubSerialNo(tNo);
				mLCCustomerImpartDetailSet.get(i).setContNo(
						mLCContSchema.getContNo());
				mLCCustomerImpartDetailSet.get(i).setGrpContNo(
						mLCContSchema.getGrpContNo());
				mLCCustomerImpartDetailSet.get(i).setPrtNo(
						mLCContSchema.getPrtNo());
				mLCCustomerImpartDetailSet.get(i).setProposalContNo(
						mLCContSchema.getProposalContNo());
				mLCCustomerImpartDetailSet.get(i).setCustomerNo(
						mLCInsuredSchema.getInsuredNo());
				mLCCustomerImpartDetailSet.get(i).setOperator(
						mGlobalInput.Operator);
				mLCCustomerImpartDetailSet.get(i).setMakeDate(theCurrentDate);
				mLCCustomerImpartDetailSet.get(i).setMakeTime(theCurrentTime);
				mLCCustomerImpartDetailSet.get(i).setModifyDate(theCurrentDate);
				mLCCustomerImpartDetailSet.get(i).setModifyTime(theCurrentTime);
			}
		}
		if (mOperate.equals("INSERT||CONTINSURED")) {
			if (!insertData()) {
				return false;
			}
			if (mTransferData.getValueByName("EdorType") != null
					&& "NR".equals(mTransferData.getValueByName("EdorType"))) {
				LCContDB tLCContDB = new LCContDB();
				tLCContDB.setGrpContNo(mLCContSchema.getGrpContNo());
				tLCContDB.setInsuredNo(mLCContSchema.getInsuredNo());
				tLCContDB.setAppFlag("1");
				LCContSet nLCContSet = new LCContSet();
				nLCContSet = tLCContDB.query();
				if (nLCContSet.size() != 1) {
					CError tError = new CError();
					tError.moduleName = "ContInsuredBL";
					tError.functionName = "submitData";
					tError.errorMessage = "保单下不存在该用户或其已经退保!";
					this.mErrors.addOneError(tError);
					return false;
				}
				mLCContSchema = nLCContSet.get(1);
			}
		}
		if (mOperate.equals("UPDATE||CONTINSURED")) {
			if (!updateData()) {
				return false;
			}
		
			// Update by YaoYi in 2011-9-20 for group insured. TODO
			LCAppntDB tLCAppntDB = null;
			LCGrpAppntDB tLCGrpAppntDB = null;
			String contType = mLCContSchema.getContType();
			if ("1".equals(contType)) {
				tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(mLCContSchema.getContNo());
				if(!tLCAppntDB.getInfo()){
					CError.buildErr(this, "查询投保人信息失败！");
					return false;
				}
			} else if ("2".equals(contType)) {
				tLCGrpAppntDB = new LCGrpAppntDB();
				tLCGrpAppntDB.setGrpContNo(mLCContSchema.getGrpContNo());
				if(!tLCGrpAppntDB.getInfo()){
					CError.buildErr(this, "查询投保人信息失败！");
					return false;
				}
			}

			//增加对特约、体检、LCPOLORIGINAL表客户号的修改09-06-05
			
			String tInsuredNoSql = "select insuredno from lcinsured where contno='"+"?contno?"
								+"' and sequenceno='"+"?sequenceno?"+"'";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tInsuredNoSql);
			sqlbv6.put("contno",mLCContSchema.getContNo());
			sqlbv6.put("sequenceno",tSequenceNo);
			ExeSQL tExeSQL = new ExeSQL();
			String tInsuredNo = tExeSQL.getOneValue(sqlbv6);
			//处理特约
			LCCSpecDB tLCCSpecDB = new LCCSpecDB();
			LCCSpecSet tLCCSpecSet = new LCCSpecSet();
			tLCCSpecDB.setContNo(mLCContSchema.getContNo());
			tLCCSpecDB.setCustomerNo(tInsuredNo);
			tLCCSpecSet = tLCCSpecDB.query();
			if(tLCCSpecSet.size()>0){
				for(int i=1;i<=tLCCSpecSet.size();i++){
					tLCCSpecSet.get(i).setCustomerNo(mLCInsuredSchema.getInsuredNo());
					tLCCSpecSet.get(i).setModifyDate(theCurrentDate);
					tLCCSpecSet.get(i).setModifyTime(theCurrentTime);
				}
				map.put(tLCCSpecSet, "UPDATE");
			}
			//处理体检
			LCPENoticeDB tLCPENoticeDB = new LCPENoticeDB();
			LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
			tLCPENoticeDB.setContNo(mLCContSchema.getContNo());
			tLCPENoticeDB.setCustomerNo(tInsuredNo);
			tLCPENoticeDB.setCustomerType("I");
			tLCPENoticeSet = tLCPENoticeDB.query();
			if(tLCPENoticeSet.size()>0){
				for(int i=1;i<=tLCPENoticeSet.size();i++){
					tLCPENoticeSet.get(i).setCustomerNo(mLCInsuredSchema.getInsuredNo());
					// update by YaoYi in 2011-9-20 for group insured. TODO
					if ("1".equals(contType)) {
						tLCPENoticeSet.get(i).setAppName(tLCAppntDB.getAppntName());
					} else if ("2".equals(contType)) {
						tLCPENoticeSet.get(i).setAppName(tLCGrpAppntDB.getName());
					}
					tLCPENoticeSet.get(i).setName(mLCInsuredSchema.getName());
					tLCPENoticeSet.get(i).setModifyDate(theCurrentDate);
					tLCPENoticeSet.get(i).setModifyTime(theCurrentTime);
				}
				map.put(tLCPENoticeSet, "UPDATE");
			}
			//体检结果
			LCPENoticeResultDB tLCPENoticeResultDB = new LCPENoticeResultDB();
			LCPENoticeResultSet tLCPENoticeResultSet = new LCPENoticeResultSet();
			tLCPENoticeResultDB.setContNo(mLCContSchema.getContNo());
			tLCPENoticeResultDB.setCustomerNo(tInsuredNo);
			tLCPENoticeResultSet = tLCPENoticeResultDB.query();
			if(tLCPENoticeResultSet.size()>0){
				for(int i=1;i<=tLCPENoticeResultSet.size();i++){
					tLCPENoticeResultSet.get(i).setCustomerNo(mLCInsuredSchema.getInsuredNo());
					tLCPENoticeResultSet.get(i).setName(mLCInsuredSchema.getInsuredNo());
					tLCPENoticeResultSet.get(i).setModifyDate(theCurrentDate);
					tLCPENoticeResultSet.get(i).setModifyTime(theCurrentTime);
				}
				map.put(tLCPENoticeResultSet, "UPDATE");
			}
			//LCPolOriginal
			LCPolOriginalDB tLCPolOriginalDB = new LCPolOriginalDB();
			LCPolOriginalSet tLCPolOriginalSet = new LCPolOriginalSet();
			tLCPolOriginalDB.setContNo(mLCContSchema.getContNo());
			tLCPolOriginalDB.setInsuredNo(tInsuredNo);
			tLCPolOriginalSet = tLCPolOriginalDB.query();
			if(tLCPolOriginalSet.size()>0){
				for(int i=1;i<=tLCPolOriginalSet.size();i++){
					tLCPolOriginalSet.get(i).setInsuredNo(mLCInsuredSchema.getInsuredNo());
					// update by YaoYi in 2011-9-20 for group insured.	TODO
					if ("1".equals(contType)) {
						tLCPolOriginalSet.get(i).setAppntNo(tLCAppntDB.getAppntNo());
					} else if ("2".equals(contType)) {
						tLCPolOriginalSet.get(i).setAppntNo(tLCGrpAppntDB.getCustomerNo());
					}
					tLCPolOriginalSet.get(i).setModifyDate(theCurrentDate);
					tLCPolOriginalSet.get(i).setModifyTime(theCurrentTime);
				}
				map.put(tLCPolOriginalSet, "UPDATE");
			}
		}

		return true;
	}

	/**
	 * 删除处理，删除处理后的数据，根据操作类型的不同采取不同的处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean deleteData() {
		// LCGrpContDB mLCGrpContDB = new LCGrpContDB();

		String updateContFlag = "INSERT";
		boolean updateGrpContFlag = false;
		// 对于删除的情况，首先需要的是备份数据
		if (mOperate.equals("DELETE||CONTINSURED")) {
			updateContFlag = "UPDATE";
		}
		map.put(mOLDLCInsuredDB.getSchema(), "DELETE");
		// 删除告知参数表
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql("delete from LCCustomerImpartParams where ContNo='"
				+ "?ContNo?" + "' and CustomerNo = '"
				+ "?CustomerNo?" + "' and CustomerNoType"
				+ " = '1'");
		sqlbv7.put("ContNo",mLCContSchema.getContNo());
		sqlbv7.put("CustomerNo",mOLDLCInsuredDB.getInsuredNo());
		map.put(sqlbv7, "DELETE");
		// 删除告知表
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql("delete from LCCustomerImpart where ContNo='"
				+ "?ContNo?" + "' and CustomerNo = '"
				+ "?CustomerNo?" + "' and CustomerNoType"
				+ " = '1'");
		sqlbv8.put("ContNo",mLCContSchema.getContNo());
		sqlbv8.put("CustomerNo",mOLDLCInsuredDB.getInsuredNo());
		map.put(sqlbv8, "DELETE");
		// 删除告知明细表
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql("delete from LCCustomerImpartDetail where ContNo='"
				+ "?ContNo?" + "' and CustomerNo = '"
				+ "?CustomerNo?" + "' and CustomerNoType"
				+ " = '1'");
		sqlbv9.put("ContNo",mLCContSchema.getContNo());
		sqlbv9.put("CustomerNo",mOLDLCInsuredDB.getInsuredNo());
		map.put(sqlbv9, "DELETE");
		
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql("delete from LLAccount where ContNo='"
				+ "?ContNo?" + "'");
		sqlbv10.put("ContNo",mLCContSchema.getContNo());
		map.put(sqlbv10, "DELETE");
		LCPolSet tLCPolSet = new LCPolSet();
		if (mDealFlag.equals("2")) {
//			团险-如果该被保人下有连带被保人，则将他（她）连同其连带被保人全部删除
//			删除前要将数据放入缓存否则lccont和lcinsuredrela表间的互查删除会不完全
			LCPolDB tLCPolDB =new LCPolDB();
			String tSQL = " select * from lcpol where insuredno='"+"?insuredno?"+"' and contno = '"+"?contno?"+"'"
				         +" union "
				         +" select * from lcpol where insuredno in (select customerno from lcinsuredrelated where maincustomerno ='"+"?insuredno?"+"') "
			             +" and grpcontno =(select grpcontno from lccont where contno = '"+"?contno?"+"')";
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(tSQL);
			sqlbv11.put("contno",mLCContSchema.getContNo());
			sqlbv11.put("insuredno",mOLDLCInsuredDB.getInsuredNo());
			map.put(sqlbv11, "DELETE");
			tLCPolSet = tLCPolDB.executeQuery(sqlbv11);
			
			// Add by YaoYi in 2011-9-22 for data reference.
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql("delete from lcbnf where contno ='"+"?contno?"+"'");
			sqlbv12.put("contno",mLCContSchema.getContNo());
			map.put(sqlbv12, "DELETE");
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql("delete from lcinsured where contno ='"+"?contno?"+"'");
			sqlbv13.put("contno",mLCContSchema.getContNo());
			map.put(sqlbv13, "DELETE");
			//---------------------------------------------------------------------------------------------
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql("delete from lccont where contno ='"+"?contno?"+"'");
			sqlbv14.put("contno",mLCContSchema.getContNo());
			map.put(sqlbv14, "DELETE");
			//当前tLCPolSet中保存的是要删除的被保人信息 add by liuqh 2009-02-12
			for(int i=1;i<=tLCPolSet.size();i++){
//				循环删除被保人及其连带被保人
				//LCInsured
				String tsql ="delete from lcinsured where contno ='"+"?contno?"+"' and insuredno in (select customerno from lcinsuredrelated where maincustomerno ='"+"?insuredno?"+"')";
				logger.debug("tsql:"+tsql);
				SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
				sqlbv15.sql(tsql);
				sqlbv15.put("contno",tLCPolSet.get(i).getContNo());
				sqlbv15.put("insuredno",tLCPolSet.get(i).getInsuredNo());
				map.put(sqlbv15, "DELETE");
				//LCCustomerImpartParams
				if(!tLCPolSet.get(i).getContNo().equals(mLCContSchema.getContNo())&&
						   !tLCPolSet.get(i).getInsuredNo().equals(mOLDLCInsuredDB.getInsuredNo())){//上面已经删除的下面不能重复添加
					SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
					sqlbv16.sql("delete from LCCustomerImpartParams where ContNo='"
							+ "?contno?" + "' and CustomerNo = '"
							+ "?insuredno?" + "' and CustomerNoType"
							+ " = '1'");
					sqlbv16.put("contno",tLCPolSet.get(i).getContNo());
					sqlbv16.put("insuredno",tLCPolSet.get(i).getInsuredNo());
					map.put(sqlbv16, "DELETE");
				}
				//LCCustomerImpart
				if(!tLCPolSet.get(i).getContNo().equals(mLCContSchema.getContNo())&&
						   !tLCPolSet.get(i).getInsuredNo().equals(mOLDLCInsuredDB.getInsuredNo())){//上面已经删除的下面不能重复添加
					SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
					sqlbv17.sql("delete from LCCustomerImpart where ContNo='"
							+ "?contno?" + "' and CustomerNo = '"
							+ "?insuredno?" + "' and CustomerNoType"
							+ " = '1'");
					sqlbv17.put("contno",tLCPolSet.get(i).getContNo());
					sqlbv17.put("insuredno",tLCPolSet.get(i).getInsuredNo());
					map.put(sqlbv17, "DELETE");
				}
				//LCCustomerImpartDetail
				if(!tLCPolSet.get(i).getContNo().equals(mLCContSchema.getContNo())&&
						   !tLCPolSet.get(i).getInsuredNo().equals(mOLDLCInsuredDB.getInsuredNo())){//上面已经删除的下面不能重复添加
					SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
					sqlbv18.sql("delete from LCCustomerImpartDetail where ContNo='"
							+ "?contno?" + "' and CustomerNo = '"
							+ "?insuredno?" + "' and CustomerNoType"
							+ " = '1'");
					sqlbv18.put("contno",tLCPolSet.get(i).getContNo());
					sqlbv18.put("insuredno",tLCPolSet.get(i).getInsuredNo());
					map.put(sqlbv18, "DELETE");
				}
				//LLAccount
				if(!tLCPolSet.get(i).getContNo().equals(mLCContSchema.getContNo())){
					SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
					sqlbv19.sql("delete from LLAccount where ContNo='"
							+ "?contno?" + "'");
					sqlbv19.put("contno",tLCPolSet.get(i).getContNo());
					map.put(sqlbv19, "DELETE");
				}
				//删除连带
				SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
				sqlbv20.sql("delete from lcinsuredrelated where polno ='"+"?polno?"+"'"
						+" and customerno='"+"?insuredno?"+"'");
				sqlbv20.put("polno",tLCPolSet.get(i).getPolNo());
				sqlbv20.put("insuredno",tLCPolSet.get(i).getInsuredNo());
				map.put(sqlbv20, "DELETE");
				
				//LCCont 个险不会删除lccont
				SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
				sqlbv21.sql("delete from lccont where contno='"+"?contno?"+"' ");
				sqlbv21.put("contno",tLCPolSet.get(i).getContNo());
				map.put(sqlbv21, "DELETE");
				
			}
		}
		LCPolDB tLCPolDB = new LCPolDB();

		if (mDealFlag.equals("2")) {//团险时有能需要删除其连带被保人
			preLCPolSet.add(tLCPolSet);
		}else{
			tLCPolDB.setContNo(mLCContSchema.getContNo());
			tLCPolDB.setInsuredNo(mOLDLCInsuredDB.getInsuredNo());
			preLCPolSet = tLCPolDB.query();
		}
		//将缓存中的lcpol数据赋给preLCPolSet 所以下面要给为循环删除
		if (tLCPolDB.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "ContInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询该客户下险种保单时失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String updateLCGrpContStr = "";
		// 如果删除被保险人时，需要删除被保险人下所有险种，在更新时不需要
		if (mOperate.equals("DELETE||CONTINSURED")) {
			if (preLCPolSet.size() > 0) {
				LCDelPolLogSet mLCDelPolLogSet = new LCDelPolLogSet();
				for(int j=1;j<=preLCPolSet.size();j++){
				// /////////////////////////备份险种数据/////////////////////////////
				// map.put(
				// "insert into LOBDuty (select * from LCDuty a where a.PolNo in "
				// + "(select b.polno from lcpol b where b.ContNo='" +
				// mLCContSchema.getContNo() + "'"
				// + " and b.InsuredNo = '" + mOLDLCInsuredDB.getInsuredNo()
				// + "'))", "INSERT");
				LCDutyDB tLCDutyDB = new LCDutyDB();
				LCDutySet tLCDutySet = new LCDutySet();
				SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
				sqlbv22.sql("select * from LCDuty a where a.PolNo in "
						+ "(select b.polno from lcpol b where b.ContNo='"
						+ "?ContNo?"
						+ "'"
						+ " and b.InsuredNo = '"
						+ "?InsuredNo?" + "')");
				sqlbv22.put("contno",preLCPolSet.get(j).getContNo());
				sqlbv22.put("insuredno",preLCPolSet.get(j).getInsuredNo());
				tLCDutySet = tLCDutyDB.executeQuery(sqlbv22);
				if (tLCDutyDB.mErrors.needDealError()) {
					CError.buildErr(this, "查询被保险人:"+preLCPolSet.get(j).getInsuredNo()+"-"+preLCPolSet.get(j).getInsuredName()+"责任信息时失败；");
				}
				if (tLCDutySet.size() > 0) {
					map.put(tLCDutySet, "DELETE"); // 被保人下的责任删除
				}

				// map.put(
				// "insert into LOBPrem (select * from LCPrem a where a.PolNo in "
				// + "(select b.polno from lcpol b where b.ContNo='" +
				// mLCContSchema.getContNo() + "'"
				// + " and b.InsuredNo = '" + mOLDLCInsuredDB.getInsuredNo()
				// + "'))", "INSERT");
				LCPremDB tLCPremDB = new LCPremDB();
				LCPremSet tLCPremSet = new LCPremSet();
				SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
				sqlbv23.sql("select * from LCPrem a where a.PolNo in "
						+ "(select b.polno from lcpol b where b.ContNo='"
						+ "?ContNo?"
						+ "'"
						+ " and b.InsuredNo = '"
						+ "?InsuredNo?" + "')");
				sqlbv23.put("contno",preLCPolSet.get(j).getContNo());
				sqlbv23.put("insuredno",preLCPolSet.get(j).getInsuredNo());
				tLCPremSet = tLCPremDB
						.executeQuery(sqlbv23);
				if (tLCPremDB.mErrors.needDealError()) {
					CError.buildErr(this, "查询被保险人:"+preLCPolSet.get(j).getInsuredNo()+"-"+preLCPolSet.get(j).getInsuredName()+"保费项信息时失败；");
				}
				if (tLCPremSet.size() > 0) {
					map.put(tLCPremSet, "DELETE"); // 被保人下的险种删除
				}

				// map.put(
				// "insert into LOBGet (select * from LCGet a where a.PolNo in "
				// + "(select b.polno from lcpol b where b.ContNo='" +
				// mLCContSchema.getContNo() + "'"
				// + " and b.InsuredNo = '" + mOLDLCInsuredDB.getInsuredNo()
				// + "'))", "INSERT");
				LCGetDB tLCGetDB = new LCGetDB();
				LCGetSet tLCGetSet = new LCGetSet();
				SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
				sqlbv24.sql("select * from LCGet a where a.PolNo in "
						+ "(select b.polno from lcpol b where b.ContNo='"
						+ "?ContNo?"
						+ "'"
						+ " and b.InsuredNo = '"
						+ "?InsuredNo?" + "')");
				sqlbv24.put("contno",preLCPolSet.get(j).getContNo());
				sqlbv24.put("insuredno",preLCPolSet.get(j).getInsuredNo());
				tLCGetSet = tLCGetDB
						.executeQuery(sqlbv24);
				if (tLCGetDB.mErrors.needDealError()) {
					CError.buildErr(this, "查询被保险人:"+preLCPolSet.get(j).getInsuredNo()+"-"+preLCPolSet.get(j).getInsuredName()+"给付项信息时失败；");
				}
				if (tLCGetSet.size() > 0) {
					map.put(tLCGetSet, "DELETE"); // 被保人下的给付删除
				}

				// map.put(
				// "insert into LOBPol (select * from LCPol where ContNo='"
				// + mLCContSchema.getContNo() + "' and InsuredNo = '"
				// + mOLDLCInsuredDB.getInsuredNo() + "')", "INSERT");

				//如果是团险 则preLCPolSet.size() 不为1 应该放在循环外面 否则会报错
//				map.put(preLCPolSet, "DELETE"); // 被保人下的险种删除

			  }
				for (int polcount = 1; polcount <= preLCPolSet.size(); polcount++) {
					// 填写险种删除操作的日志////////////////////////////////////////////
					LCDelPolLogSchema mLCDelPolLog = new LCDelPolLogSchema();
					LCPolSchema tLCPolSchema = new LCPolSchema();
					tLCPolSchema.setSchema(preLCPolSet.get(polcount));
					mLCDelPolLog.setOtherNo(tLCPolSchema.getPolNo());
					if (ContType.equals("2")) {
						mLCDelPolLog.setOtherNoType("4");
					} else {
						mLCDelPolLog.setOtherNoType("14");
					}
					mLCDelPolLog.setPrtNo(tLCPolSchema.getPrtNo());
					if (tLCPolSchema.getAppFlag().equals("1")) {
						mLCDelPolLog.setIsPolFlag("1");
					} else {
						mLCDelPolLog.setIsPolFlag("0");
					}
					mLCDelPolLog.setOperator(mGlobalInput.Operator);
					mLCDelPolLog.setManageCom(mGlobalInput.ManageCom);
					mLCDelPolLog.setMakeDate(PubFun.getCurrentDate());
					mLCDelPolLog.setMakeTime(PubFun.getCurrentTime());
					mLCDelPolLog.setModifyDate(PubFun.getCurrentDate());
					mLCDelPolLog.setModifyTime(PubFun.getCurrentTime());
					mLCDelPolLogSet.add(mLCDelPolLog);
					if (polcount == preLCPolSet.size()) {
						map.put(mLCDelPolLogSet, "INSERT");
					}
					////////////////////////////////////////////////////////////
					// /////////////
					// 更新合同表
					mLCContSchema.setPrem(mLCContSchema.getPrem()
							- preLCPolSet.get(polcount).getPrem());
					mLCContSchema.setSumPrem(mLCContSchema.getSumPrem()
							- preLCPolSet.get(polcount).getSumPrem());
					mLCContSchema.setAmnt(mLCContSchema.getAmnt()
							- preLCPolSet.get(polcount).getAmnt());
					mLCContSchema.setMult(mLCContSchema.getMult()
							- preLCPolSet.get(polcount).getMult());
					mLCContSchema.setPeoples(mLCContSchema.getPeoples()
							- preLCPolSet.get(polcount).getInsuredPeoples());
					updateContFlag = "UPDATE";
					// 更新团体表
				}
				map.put(preLCPolSet, "DELETE"); // 被保人下的险种删除
				if (mDealFlag.equals("2")) {
					
					// 集体险种信息
					String fromPart = "from LCPol where GrpContNo='"
						+ "?GrpContNo?"
						+ "' and riskcode ='"
						+ "?riskcode?"//因为preLCPolSet>0
						+ "')";
					SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
					sqlbv25.sql("update LCGrpPol set "
							+ "Prem=(select (case when SUM(Prem) is null then 0 else SUM(Prem) end) "
							+ fromPart
							+ ",Amnt=(select (case when SUM(Amnt) is null then 0 else SUM(Amnt) end) "
							+ fromPart
							+ ",SumPrem=(select (case when SUM(SumPrem) is null then 0 else SUM(SumPrem) end) "
							+ fromPart
							+ ",Mult=(select (case when SUM(Mult) is null then 0 else SUM(Mult) end) "
							+ fromPart
							+ ",Peoples2=(select (case when SUM(InsuredPeoples) is null then 0 else SUM(InsuredPeoples) end) "
							+ fromPart
							+ " where grppolno='"
							+ "?grppolno?" + "'");
					sqlbv25.put("GrpContNo", mLCContSchema.getGrpContNo());
					sqlbv25.put("riskcode", preLCPolSet.get(1).getRiskCode());
					sqlbv25.put("grppolno", preLCPolSet.get(1).getGrpPolNo());
					
					map.put(sqlbv25,"UPDATE");
				}
			}
			if (("1").equals(mLCContSchema.getPolType())) { // 无名单
				mLCContSchema.setPeoples(0);

			} else {
				mLCContSchema.setPeoples(mLCContSchema.getPeoples() - 1);

			}

			if (mDealFlag.equals("2")) {

				// 更新此被保人后边被保人的序号,只针对删除被保人
				String tUpCusSeqSQL = "update lcinsured set "
						+ "customerseqno=customerseqno-"
						+ "?customerseqno?"        //有可能不止删除一个人
						+ " where grpcontno='" + "?grpcontno?"
						+ "' and customerseqno>'"
//						+ " (select CustomerSeqNo from lcinsured where contno='"+preLCPolSet.get(i).getContNo()+"'"
//						+ " and insuredno ='"+preLCPolSet.get(i)+"')"
						+ "?customerseqno?" + "'";
				SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
				sqlbv26.sql(tUpCusSeqSQL);
				sqlbv26.put("customerseqno", preLCPolSet.size());
				sqlbv26.put("grpcontno", mLCContSchema.getGrpContNo());
				sqlbv26.put("customerseqno1", mOLDLCInsuredDB.getCustomerSeqNo());
				map.put(sqlbv26, "UPDATE");
				// 集体险种信息
				String fromPart = "from LCPol where GrpContNo='"
						+ "?grpcontno?" + "')";
				updateLCGrpContStr = "update LCGrpCont set "
						+ "Prem=(select (case when SUM(Prem) is null then 0 else SUM(Prem) end) " + fromPart
						+ ",Amnt=(select (case when SUM(Amnt) is null then 0 else SUM(Amnt) end) " + fromPart
						+ ",SumPrem=(select (case when SUM(SumPrem) is null then 0 else SUM(SumPrem) end) " + fromPart
						+ ",Mult=(select (case when SUM(Mult) is null then 0 else SUM(Mult) end) " + fromPart
						+ ",Peoples2=(select (case when SUM(Peoples) is null then 0 else SUM(Peoples) end) "
						+ "from lccont where grpcontno='"
						+ "?grpcontno?" + "')"
						+ " where grpcontno='" + "?grpcontno?"
						+ "'";
				updateGrpContFlag = true;
			}

			// 主被保险人时删除合同上主被保险人信息,在点录入完成，复核修改完成时，需要检查是否有主被保险人
			if (("00").equals(mOLDLCInsuredDB.getRelationToMainInsured())) {
				////////////////////在此首先填写日志////////////////////////////////////
				// ////
				LCDelPolLogSchema InsuredLCDelPolLog = new LCDelPolLogSchema();
				InsuredLCDelPolLog.setOtherNo(mOLDLCInsuredDB.getSchema()
						.getInsuredNo());
				InsuredLCDelPolLog.setOtherNoType("13");
				InsuredLCDelPolLog.setPrtNo(mOLDLCInsuredDB.getSchema()
						.getPrtNo());
				if (mOLDLCInsuredDB.getSchema().equals("1")) { // no
					// appflag,only
					// give it
					InsuredLCDelPolLog.setIsPolFlag("1");
				} else {
					InsuredLCDelPolLog.setIsPolFlag("0");
				}
				InsuredLCDelPolLog.setOperator(mGlobalInput.Operator);
				InsuredLCDelPolLog.setManageCom(mGlobalInput.ManageCom);
				InsuredLCDelPolLog.setMakeDate(PubFun.getCurrentDate());
				InsuredLCDelPolLog.setMakeTime(PubFun.getCurrentTime());
				InsuredLCDelPolLog.setModifyDate(PubFun.getCurrentDate());
				InsuredLCDelPolLog.setModifyTime(PubFun.getCurrentTime());
				/////////////////////////////日志填写完毕/////////////////////////////
				// /////

				mLCContSchema.setInsuredBirthday("");
				mLCContSchema.setInsuredIDNo("");
				mLCContSchema.setInsuredIDType("");
				mLCContSchema.setInsuredName("");
				mLCContSchema.setInsuredNo("0");
				mLCContSchema.setInsuredSex("");
				mLCContSchema.setModifyDate(theCurrentDate);
				mLCContSchema.setModifyTime(theCurrentTime);
				updateContFlag = "UPDATE";
				// 团单时删除合同
				if (mDealFlag.equals("2")) {
					// 首先将被保人的删除操作的日志填写,团单与个单的不同仅仅在于Type
					InsuredLCDelPolLog.setOtherNoType("3");
					// /////////////填写日志完毕////////////////////////////////////
					LCInsuredDB tLCInsuredDB = new LCInsuredDB();
					tLCInsuredDB.setContNo(mOLDLCInsuredDB.getContNo());
					int insuredCount = tLCInsuredDB.getCount();
					if (tLCInsuredDB.mErrors.needDealError()) {
						CError tError = new CError();
						tError.moduleName = "ContInsuredBL";
						tError.functionName = "getInputData";
						tError.errorMessage = "查询客户时失败!";
						this.mErrors.addOneError(tError);
						return false;
					}
					// 最后一个客户时需要删除个人合同表
					if (insuredCount == 1) {
						updateContFlag = "DELETE";
						//////////////////////////////在此首先填写个单合同日志//////////////
						// //////////////////////////
						LCDelPolLogSchema LCContLCDelPolLog = new LCDelPolLogSchema();
						LCContLCDelPolLog.setOtherNo(mLCContSchema.getContNo());
						LCContLCDelPolLog.setOtherNoType("2");
						LCContLCDelPolLog.setPrtNo(mLCContSchema.getPrtNo());
						if (mLCContSchema.getAppFlag().equals("1")) {
							LCContLCDelPolLog.setIsPolFlag("1");
						} else {
							LCContLCDelPolLog.setIsPolFlag("0");
						}
						LCContLCDelPolLog.setOperator(mGlobalInput.Operator);
						LCContLCDelPolLog.setManageCom(mGlobalInput.ManageCom);
						LCContLCDelPolLog.setMakeDate(PubFun.getCurrentDate());
						LCContLCDelPolLog.setMakeTime(PubFun.getCurrentTime());
						LCContLCDelPolLog
								.setModifyDate(PubFun.getCurrentDate());
						LCContLCDelPolLog
								.setModifyTime(PubFun.getCurrentTime());
						map.put(LCContLCDelPolLog, "INSERT");
						// ///日志填写完毕//////////////////////////////////
						// //数据的备份
						// map.put(
						// "insert into LOBCont (select * from LCCont where ContNo='"
						// + mLCContSchema.getContNo() + "')", "INSERT");
					}
				}
				// map.put(mLCContSchema, "UPDATE");
				// 删除主被保险人时填写日志
				map.put(InsuredLCDelPolLog, "INSERT");
			}

		}
		////////////////////////////////////////////////////////////////////////
		// //
		// 添加对于UPDATE操作的处理，对于UPDATE操作为了更新LDPERSON表中的记录需要先删除
		// LDPERSON表中的记录
		////////////////////////////////////////////////////////////////////////
		// //
		logger.debug("###########################");
		logger.debug("##################mOperate==" + mOperate);
		logger.debug("###########################");
		if (mOperate.equals("UPDATE||CONTINSURED")) {
			logger.debug("###########################");
			logger.debug("##################tLDPersonSchema.getCustomerNo()=="
							+ tLDPersonSchema.getCustomerNo());
			logger.debug("###########################");
			SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
			sqlbv28.sql("delete from ldperson where customerno='"
					+ "?customerno?" + "'");
			sqlbv28.put("customerno", tLDPersonSchema.getCustomerNo());
			map.put(sqlbv28, "DELETE");
		}
		if (!updateContFlag.equals("INSERT")) {
			map.put(mLCContSchema, updateContFlag);
		}
		if (updateGrpContFlag) {
			SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
			sqlbv27.sql(updateLCGrpContStr);
			sqlbv27.put("grpcontno", mLCContSchema.getGrpContNo());
			map.put(sqlbv27, "UPDATE");
		}
		return true;
	}

	private boolean delrisk(VData cInputData) {
		logger.debug("asfdsfsfafasf0");
		String tContType = "1";
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		logger.debug("asfdsfsfafasf2");
		if (mTransferData == null) {
			CError tError = new CError();
			tError.moduleName = "ContInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "未得到传送数据!";
			this.mErrors.addOneError(tError);
			return false;

		}
		String temp = (String) mTransferData.getValueByName("PolNo");
		logger.debug("***************************");
		logger.debug("PolNo" + temp);
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(temp);
		logger.debug("mLCPolBL==" + mLCPolBL.getOccupationType());
		if (!tLCPolDB.getInfo()) {
			CError.buildErr(this, "查询险种保单失败！");
			return false;
		}
		mLCPolBL.setSchema(tLCPolDB.getSchema());
		tContType = mLCPolBL.getContType();
		logger.debug("*********mLCPolBL:" + mLCPolBL.getPolNo());

		// logger.debug("asfdsfsfafasf3");

		String InsuredNo = (String) mTransferData.getValueByName("InsuredNo");
		String PolNo = (String) mTransferData.getValueByName("PolNo");
		String ContNo = (String) mTransferData.getValueByName("ContNo");
		// 先判断它是否具有附加险
		ExeSQL xeSql = new ExeSQL();
		SSRS gSSRS = new SSRS();
		SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
		sqlbv29.sql("select * from lcpol where mainpolno='" + "?polno?"
				+ "' and polno!='" + "?polno?" + "'");
		sqlbv29.put("polno", PolNo);
		gSSRS = xeSql.execSQL(sqlbv29);
		if (gSSRS.MaxRow > 0) {
			CError tError = new CError();
			tError.moduleName = "ContInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "该险种具有附加险，请先删除附加险!";
			this.mErrors.addOneError(tError);
			return false;

		}

		// 判断国家及本币 cyj
		String CurrString = "select codename from ldcode1 where codetype = 'currencyprecision' " 
			+" and code1 = (select sysvarvalue from ldsysvar where sysvar='nativeplace')";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(CurrString);
		ExeSQL tExeSQL = new ExeSQL();
		locCurrency = tExeSQL.getOneValue(sqlbv);
		if(locCurrency==null||"".equals(locCurrency)){
			CError.buildErr(this, "查询本币信息失败！");
			return false;
		}
		// 判断结束 write by yaory
		SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
		sqlbv30.sql("delete from lcpol where PolNo='" + "?polno?" + "'");
		sqlbv30.put("polno", PolNo);
		map.put(sqlbv30, "DELETE");
		
		SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
		sqlbv31.sql("delete from LCInsuredRelated where PolNo='" + "?polno?" + "'");
		sqlbv31.put("polno", PolNo);
		map.put(sqlbv31,"DELETE");
		
		SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
		sqlbv32.sql("delete from LCDuty where PolNo='" + "?polno?" + "'");
		sqlbv32.put("polno", PolNo);
		map.put(sqlbv32, "DELETE");
		
		SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
		sqlbv33.sql("delete from LCBnf where PolNo='" + "?polno?" + "'");
		sqlbv33.put("polno", PolNo);
		map.put(sqlbv33, "DELETE");
		
		SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
		sqlbv34.sql("delete from LCPrem where PolNo='" + "?polno?" + "'");
		sqlbv34.put("polno", PolNo);
		map.put(sqlbv34, "DELETE");
		
		SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
		sqlbv35.sql("delete from LCGet where PolNo='" + "?polno?" + "'");
		sqlbv35.put("polno", PolNo);
		map.put(sqlbv35, "DELETE");
		
		SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
		sqlbv36.sql("delete from LCSpec where PolNo='" + "?polno?" + "'");
		sqlbv36.put("polno", PolNo);
		map.put(sqlbv36, "DELETE");
		
		SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
		sqlbv37.sql("delete from lcpolother where PolNo='" + "?polno?" + "'");
		sqlbv37.put("polno", PolNo);
		map.put(sqlbv37, "DELETE");
		
		//tongmeng 2010-11-30 modify
		//删除账户投资比例
		SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
		sqlbv38.sql("delete from lcperinvestplan where PolNo='" + "?polno?" + "'");
		sqlbv38.put("polno", PolNo);
		map.put(sqlbv38, "DELETE");
//		map.put("update lccont set amnt = (select sum(amnt) from lcpol where insuredno= '"+InsuredNo+"' and contno = lccont.contno) , prem = (select sum(prem) from lcpol where insuredno= '"+InsuredNo+"' and contno = lccont.contno) where contno = '" +ContNo+ "'", "UPDATE");
		// 删除险种时，多币种重新转换
		String curPolString ="select * from lcpol where contno='" +"?contno?"+ "' and insuredno='"+"?insuredno?"+"' and polno <>'" + "?polno?" + "' ";
		LCPolDB curLCPolDB = new LCPolDB();
		LCPolSet curLCPolSet = new LCPolSet();
		SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
		sqlbv39.sql(curPolString);
		sqlbv39.put("contno", ContNo);
		sqlbv39.put("insuredno", InsuredNo);
		sqlbv39.put("polno", PolNo);
		curLCPolSet = curLCPolDB.executeQuery(sqlbv39);
		double ExchPrem = 0.00;
		double ExchAmnt = 0.00;
		double ExchSumPrem = 0.00;
		LDExch tLDExch = new LDExch();
		for (int i=1;i<=curLCPolSet.size();i++){
			ExchPrem += tLDExch.toBaseCur(curLCPolSet.get(i).getCurrency(), locCurrency, theCurrentDate, curLCPolSet.get(i).getPrem());		
			ExchAmnt += tLDExch.toBaseCur(curLCPolSet.get(i).getCurrency(), locCurrency, theCurrentDate, curLCPolSet.get(i).getAmnt());
			ExchSumPrem += tLDExch.toBaseCur(curLCPolSet.get(i).getCurrency(), locCurrency, theCurrentDate, curLCPolSet.get(i).getSumPrem());
		}
		SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
		sqlbv40.sql("update lccont set amnt = "+"?amnt?"+", prem = "+"?prem?"+", sumprem = "+"?sumprem?"+" where contno = '" +"?contno?"+ "'");
		sqlbv40.put("amnt", ExchAmnt);
		sqlbv40.put("prem", ExchPrem);
		sqlbv40.put("sumprem", ExchSumPrem);
		sqlbv40.put("contno", ContNo);
		map.put(sqlbv40, "UPDATE");
		// 泰康此处有一部分删除帐户表的功能????这个在承保时不需要使用吧???? tongmeng
		//

		//if (mDealFlag.equals("2")) 
		{
			// 集体险种信息
			String fromPart = "from LCPol where GrpContNo='"
					+ "?GrpContNo?" + "' and riskcode ='"
					+ "??" + "')";
			SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
			sqlbv41.sql("update LCGrpPol set " + "Prem=(select SUM(Prem) "
					+ fromPart + ",Amnt=(select SUM(Amnt) " + fromPart
					+ ",SumPrem=(select SUM(SumPrem) " + fromPart
					+ ",Mult=(select SUM(Mult) " + fromPart
					+ ",Peoples2=(select SUM(InsuredPeoples) " + fromPart
					+ " where grppolno='" + "?grppolno?" + "'");
			sqlbv41.put("GrpContNo",mLCPolBL.getGrpContNo());
			sqlbv41.put("riskcode",mLCPolBL.getRiskCode());
			sqlbv41.put("grppolno",mLCPolBL.getGrpPolNo());
			map.put(sqlbv41,
					"UPDATE");
			fromPart = "from LCPol where GrpContNo='" + "?GrpContNo?"
					+ "')";
			String updateLCGrpContStr = "update LCGrpCont set "
					+ "Prem=(select SUM(Prem) " + fromPart
					+ ",Amnt=(select SUM(Amnt) " + fromPart
					+ ",SumPrem=(select SUM(SumPrem) " + fromPart
					+ ",Mult=(select SUM(Mult) " + fromPart
					+ ",Peoples2=(select SUM(Peoples) "
					+ "from lccont where grpcontno='" + "?GrpContNo?"
					+ "')" + " where grpcontno='" + "?GrpContNo?"
					+ "'";
			SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
			sqlbv42.sql(updateLCGrpContStr);
			sqlbv42.put("GrpContNo", mLCPolBL.getGrpContNo());
			map.put(sqlbv42, "UPDATE");
		}
		//折扣和应收
		SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
		sqlbv43.sql("delete from lcdiscount where PolNo='" + "?PolNo?" + "'");
		sqlbv43.put("PolNo", PolNo);
		map.put(sqlbv43, "DELETE");
		SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
		sqlbv44.sql("delete from ljspay where OtherNo='" + "?contno?" + "' and OtherNotype='2' "
				+" and not exists(select 1 from ljspayperson where contno='" + "?contno?" + "' and polno<>'" + "?PolNo?" + "' and currency=ljspay.currency)");
		sqlbv44.put("contno", mLCPolBL.getContNo());
		sqlbv44.put("PolNo", PolNo);
		map.put(sqlbv44, "DELETE");		
		String sql = "select currency,sum(sumduepaymoney) from ljspayperson where contno='" + "?contno?" + "' and polno<>'" + "?PolNo?" + "' group by currency";//修改应收总表
		SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
		sqlbv45.sql(sql);
		sqlbv45.put("contno", mLCPolBL.getContNo());
		sqlbv45.put("PolNo", PolNo);
		ExeSQL yExeSQL = new ExeSQL();
		SSRS ySSRS = yExeSQL.execSQL(sqlbv45);
		if(ySSRS!=null && ySSRS.getMaxRow()>0)
		{
			for(int i=1;i<=ySSRS.getMaxRow();i++)
			{
				SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
				sqlbv46.sql("update ljspay set sumduepaymoney='"+"?sumduepaymoney?"+"' where OtherNo='" + "?OtherNo?" + "' and OtherNotype='2' and currency='"+"?currency?"+"'");
				sqlbv46.put("sumduepaymoney",ySSRS.GetText(i, 2));
				sqlbv46.put("OtherNo",mLCPolBL.getContNo());
				sqlbv46.put("currency",ySSRS.GetText(i, 1));
				map.put(sqlbv46, "UPDATE");
			}			
		}
		SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
		sqlbv47.sql("delete from ljspayperson where PolNo='" + "?PolNo?" + "'");
		sqlbv47.put("PolNo",PolNo);
		map.put(sqlbv47, "DELETE");

		if (CheckTBField("DELETE") == false) {
			return false;
		}
		return true;
	}

	public VData formatLCPol(LCPolSchema tLCPolSchema,
			LCGrpPolSchema tLCGrpPolSchema) {
		VData tReturnData = new VData();
		if (tLCGrpPolSchema == null) {
			return null;
		}

		// 校验险种是否存在，能不能挂在其指定主险中
		tLCPolSchema.setPrtNo(tLCGrpPolSchema.getPrtNo());
		tLCPolSchema.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
		
		
		
		
		if ("1".equals(tLCPolSchema.getSpecifyValiDate())
				&& tLCPolSchema.getCValiDate() != null) {
			// 如果磁盘倒入指定生效日期，且生效日期字段有值,那么就用 生效日期字段的值
		} else {
			// 否则一律用集体单的生效日期
			if(tLCPolSchema.getSpecifyValiDate()!=null&&tLCPolSchema.getSpecifyValiDate().equals("Y"))
			{
				
			}
			else
			{
				tLCPolSchema.setCValiDate(tLCGrpPolSchema.getCValiDate());
			}
			logger.debug("----------validate--------"
					+ tLCGrpPolSchema.getCValiDate());
			logger.debug("====" + mTransferData.getValueByName("BQFlag"));
			if (mTransferData.getValueByName("BQFlag") != null
					&& !mTransferData.getValueByName("BQFlag").equals("")
					&& !mTransferData.getValueByName("BQFlag").equals("null"))

			{
				String EdorValiDate = (String) mTransferData
						.getValueByName("EdorValiDate");
				tLCPolSchema.setCValiDate(EdorValiDate);
			}

		}
		logger.debug("----------------====validate=====----"
				+ tLCPolSchema.getCValiDate());
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(tLCGrpPolSchema.getGrpContNo());
		LCGrpContSchema tLCGrpContSchema = tLCGrpContDB.getSchema();
		tLCPolSchema.setManageCom(tLCGrpPolSchema.getManageCom());
		tLCPolSchema.setSaleChnl(tLCGrpPolSchema.getSaleChnl());
		tLCPolSchema.setAgentCom(tLCGrpPolSchema.getAgentCom());
		tLCPolSchema.setAgentCode(tLCGrpPolSchema.getAgentCode());
		tLCPolSchema.setAgentType(tLCGrpPolSchema.getAgentType());
		tLCPolSchema.setAgentGroup(tLCGrpPolSchema.getAgentGroup());
		tLCPolSchema.setAgentCode1(tLCGrpPolSchema.getAgentCode1());
		tLCPolSchema.setAppntName(tLCGrpPolSchema.getGrpName());
		tLCPolSchema.setAppntNo(tLCGrpPolSchema.getCustomerNo());
		tLCPolSchema.setPolApplyDate(tLCGrpContSchema.getPolApplyDate());
		if (tLCGrpPolSchema.getAppFlag() != null
				&& tLCGrpPolSchema.getAppFlag().equals("0")) {
			tLCPolSchema.setInterestDifFlag("0");
		}
		if ("2".equals(mLCContSchema.getAppFlag())) {
			tLCPolSchema.setInterestDifFlag("2"); // 保全暂定标志为2
		}
		tReturnData.add(tLCPolSchema);
		return tReturnData;
	}

	private LCPolSchema formLCPolSchema(LCInsuredSchema insuredSchema,
			LCContPlanRiskSchema tLCContPlanRiskSchema,
			LCContSchema contSchema, LCGrpPolSchema tLCGrpPolSchema) {
		LCPolSchema tLCPolSchema = new LCPolSchema();
		String strRiskCode = tLCContPlanRiskSchema.getRiskCode();

		if (tLCGrpPolSchema == null) {
			CError.buildErr(this,"数据处理失败ContInsuredBL-->dealData!");

			return null;
		}

		tLCPolSchema.setPrtNo(tLCGrpPolSchema.getPrtNo());
		tLCPolSchema.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());

		// 否则一律用集体单的生效日期
		//tongmeng 2009-03-20 如果个单没有指定生效日,使用团单的,如果个单指定生效日,使用每个人的.
		//wangxw 20100919 lcpol 的生效日期，团单时候判断是否为空，都为空则选择当前时间。
		if(contSchema.getCValiDate()!=null)
		{
			tLCPolSchema.setCValiDate(contSchema.getCValiDate());
			tLCPolSchema.setSpecifyValiDate("Y");
		}
		else if( tLCGrpPolSchema.getCValiDate() != null )
		{
			tLCPolSchema.setCValiDate(tLCGrpPolSchema.getCValiDate());
		}else{
			CError tError = new CError();
			tError.moduleName = "ContInsuredBL";
			tError.functionName = "formLCPolSchema";
			tError.errorMessage = "生效日期为空!";
			this.mErrors.addOneError(tError);
			 //tLCPolSchema.setCValiDate( PubFun.getCurrentDate() );
		}
			
		
		// 如果是保全增人，磁盘倒入指定生效日期，且生效日期字段无值,那么就用 页面传入值
		// jixf 20051102
		if (mTransferData.getValueByName("BQFlag") != null
				&& !((String)mTransferData.getValueByName("BQFlag")).equals("")
				&&!((String)mTransferData.getValueByName("BQFlag")).equals("null") ){
			String EdorValiDate = (String) mTransferData
					.getValueByName("EdorValiDate");
			logger.debug("----------Edorvalidate-----------"
					+ EdorValiDate);
			tLCPolSchema.setCValiDate(EdorValiDate);
		}

		tLCPolSchema.setManageCom(tLCGrpPolSchema.getManageCom());
		tLCPolSchema.setSaleChnl(tLCGrpPolSchema.getSaleChnl());
		tLCPolSchema.setAgentCom(tLCGrpPolSchema.getAgentCom());
		tLCPolSchema.setAgentCode(tLCGrpPolSchema.getAgentCode());
		tLCPolSchema.setAgentGroup(tLCGrpPolSchema.getAgentGroup());
		tLCPolSchema.setAgentCode1(tLCGrpPolSchema.getAgentCode1());
		tLCPolSchema.setGrpContNo(tLCGrpPolSchema.getGrpContNo());
		// tLCPolSchema.set
		tLCPolSchema.setContType("2");
		// tLCPolSchema.setPolTypeFlag("2");
		tLCPolSchema.setPolTypeFlag(contSchema.getPolType());
		tLCPolSchema.setInsuredPeoples(contSchema.getPeoples());
		tLCPolSchema.setRiskCode(strRiskCode);

		tLCPolSchema.setContNo(insuredSchema.getContNo().trim());
		tLCPolSchema.setInsuredSex(insuredSchema.getSex().trim());
		tLCPolSchema.setInsuredBirthday(insuredSchema.getBirthday());
		tLCPolSchema.setInsuredName(insuredSchema.getName().trim());

		tLCPolSchema.setInsuredNo(insuredSchema.getInsuredNo());

		return tLCPolSchema;

	}

	public VData getCardResult() {
		return this.rResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {

		try {
			mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));

			this.mLCInsuredSchema.setSchema((LCInsuredSchema) cInputData
					.getObjectByObjectName("LCInsuredSchema", 0));
//			this.mLLAccountSchema.setSchema((LLAccountSchema) cInputData
//					.getObjectByObjectName("LLAccountSchema", 0));
			if (mLCInsuredSchema.getContPlanCode()!=null&&!"".equals(mLCInsuredSchema.getContPlanCode())) {
				String PlanAscripsql = null;
				PlanAscripsql = "select d.CalFactorValue  from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCContPlanDutyParam d where  a.calfactor='AscriptionRuleCode' and a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion and ContPlanCode = '"
						+ "?ContPlanCode?"
						+ "'and GrpContNO = '"
						+ "?GrpContNO?"
						+ "' order by a.RiskCode,d.MainRiskCode,a.DutyCode,a.factororder";
				SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
				sqlbv48.sql(PlanAscripsql);
				sqlbv48.put("ContPlanCode", mLCInsuredSchema.getContPlanCode());
				sqlbv48.put("GrpContNO", mLCInsuredSchema.getGrpContNo());
				ExeSQL PlanAscripExeSQL = new ExeSQL();
				SSRS PlanAscripssrs = new SSRS();
				PlanAscripssrs = PlanAscripExeSQL.execSQL(sqlbv48);
				if (PlanAscripssrs.getMaxRow() >= 1) {
					if (!PlanAscripssrs.GetText(1, 1).equals("")) {
						// 有归属

						String Ascripsql1 = null;
						Ascripsql1 = "select * from LCAscriptionRuleFactory where grpcontno='"
								+ "?grpcontno?"
								+ "' and ASCRIPTIONRULECODE='"
								+ "?ASCRIPTIONRULECODE?" + "'";
						SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
						sqlbv49.sql(Ascripsql1);
						sqlbv49.put("grpcontno", mLCInsuredSchema.getGrpContNo());
						sqlbv49.put("ASCRIPTIONRULECODE", PlanAscripssrs.GetText(1, 1));
						LCAscriptionRuleFactorySet tLCAscriptionRuleFactorySet = new LCAscriptionRuleFactorySet();
						LCAscriptionRuleFactoryDB tLCAscriptionRuleFactoryDB = new LCAscriptionRuleFactoryDB();

						tLCAscriptionRuleFactorySet = tLCAscriptionRuleFactoryDB
								.executeQuery(sqlbv49);

						for (int m = 1; m <= tLCAscriptionRuleFactorySet.size(); m++) {
							if (tLCAscriptionRuleFactorySet.get(m)
									.getFactoryType().equals("000006")
									&& (mLCInsuredSchema.getJoinCompanyDate() == null || mLCInsuredSchema
											.getJoinCompanyDate().equals(""))) {
								CError.buildErr(this,"未录入入司日期，请补全!");
								return false;
							}
						}
					}
				}
			}

			this.mTransferData = (TransferData) cInputData
					.getObjectByObjectName("TransferData", 0);

			if (mTransferData == null) {
				CError.buildErr(this, "未得到传送数据!");
				return false;

			}

			String tISPlanRisk = (String) mTransferData
					.getValueByName("ISPlanRisk");
			if (tISPlanRisk != null && tISPlanRisk.equals("Y")) {
				ISPlanRisk = true;
			}

			mark = (String) mTransferData.getValueByName("mark");
			KDCheckFlag = (String)mTransferData.getValueByName("KDCheckFlag");
			CardRelationToInsured = (String)mTransferData.getValueByName("CardRelationToInsured");
			if (mark != null && mark.equals("card")) {
				mLCAppntBL.setSchema((LCAppntSchema) cInputData
						.getObjectByObjectName("LCAppntSchema", 0));
			}
			logger.debug("in continsuredbl 卡单标志====" + mark);
			if (mark != null) {
				if (mark.equals("card")) {
					logger.debug("因为是卡单所以取第二个地址");
					this.mLCAddressSchema
							.setSchema((LCAddressSchema) cInputData
									.getObjectByObjectName("LCAddressSchema", 1));
					this.mLCContSchema.setSchema((LCContSchema) cInputData
							.getObjectByObjectName("LCContSchema", 1));
					if (mark != null && mark.equals("card")) {
						appntno = mLCContSchema.getAppntNo();
					}
				} else {
					logger.debug("come here !");
					this.mLCAddressSchema
							.setSchema((LCAddressSchema) cInputData
									.getObjectByObjectName("LCAddressSchema", 0));
					this.mLCContSchema.setSchema((LCContSchema) cInputData
							.getObjectByObjectName("LCContSchema", 0));

				}
			} else {
				logger.debug("卡单标志是NULL");
				this.mLCAddressSchema.setSchema((LCAddressSchema) cInputData
						.getObjectByObjectName("LCAddressSchema", 0));
				this.mLCContSchema.setSchema((LCContSchema) cInputData
						.getObjectByObjectName("LCContSchema", 0));

			}

			manageCom = mLCContSchema.getManageCom();
			agentcode = mLCContSchema.getAgentCode();
			signcom = mLCContSchema.getSignCom();
			appflag = mLCContSchema.getAppFlag();
			operator = mLCContSchema.getOperator();

			logger.debug("已经给值====" + manageCom);
//			this.mOLDLCInsuredDB = ((LCInsuredDB) cInputData
//					.getObjectByObjectName("LCInsuredDB", 0));
			tOLCLCInsuredSchema    = (LCInsuredSchema)cInputData.getObjectByObjectName("LCInsuredSchema", 5);
			if(tOLCLCInsuredSchema==null)
			{	
				tOLCLCInsuredSchema  = (LCInsuredSchema) cInputData.getObjectByObjectName("LCInsuredSchema", 0);
			}
			if(tOLCLCInsuredSchema!=null){
				this.mOLDLCInsuredDB.setSchema(tOLCLCInsuredSchema);
			}
			// logger.debug("asdfasdf" + mOLDLCInsuredDB.getInsuredNo());

			this.mLDPersonSchema.setSchema((LDPersonSchema) cInputData
					.getObjectByObjectName("LDPersonSchema", 0));
			this.mLCCustomerImpartSet = (LCCustomerImpartSet) cInputData
					.getObjectByObjectName("LCCustomerImpartSet", 0);
			this.mmLCPolSet = (LCPolSet) cInputData
					.getObjectByObjectName("LCPolSet", 0);
			this.mLCCustomerImpartDetailSet = (LCCustomerImpartDetailSet) cInputData
					.getObjectByObjectName("LCCustomerImpartDetailSet", 0);

			FamilyType = (String) mTransferData.getValueByName("FamilyType");
			tPolTypeFlag = (String) mTransferData.getValueByName("PolTypeFlag");
			tInsuredPeoples = (String) mTransferData
					.getValueByName("InsuredPeoples");
			mLoadFlag = (String) mTransferData.getValueByName("LoadFlag");// 20071224
			if (tPolTypeFlag == null || tPolTypeFlag.equals("")) {
				tPolTypeFlag = "0";
			}

			if (tInsuredPeoples == null
					|| tInsuredPeoples.equals("")
					|| (Integer.parseInt(tInsuredPeoples) != 1 && tPolTypeFlag
							.equals("0"))) {
				tInsuredPeoples = "1";
			}

			tInsuredAppAge = (String) mTransferData
					.getValueByName("InsuredAppAge");

			// ????tongmeng 2008-08-18 ??暂时注释掉此处.为什么会设置默认年龄???
			/*
			 * if (tInsuredAppAge == null || tInsuredAppAge.equals("")) {
			 * //如果投保年龄没有录入 tInsuredAppAge = "30"; }
			 */

			FamilyType = (String) mTransferData.getValueByName("FamilyType");
			tSequenceNo = (String) mTransferData.getValueByName("SequenceNo");
			logger.debug("sequenceno:"+mLCInsuredSchema.getSequenceNo());
			ContType = (String) mTransferData.getValueByName("ContType");
			if (ContType == null || "".equals(ContType)) {
				ContType = "1";
			}
			logger.debug("被保人录入,ContType=" + ContType);

			// 如果保单类型为集体总单且不是卡单则走团单处理逻辑
			// 否则为个单逻辑
			if ("2".equals(ContType) && !"card".equals(mark)) {
				this.mDealFlag = "2";
			} else {
				this.mDealFlag = "1";
			}

			String DiskImportFlag = (String) mTransferData
					.getValueByName("DiskImportFlag");

			ref.transFields(mLCInsuredSchema, mLDPersonSchema); // 客户表
			mLCInsuredSchema.setInsuredNo(mLDPersonSchema.getCustomerNo());
			mLCContSchema.setContType(ContType);
			ContNo = mLCContSchema.getContNo();
			// 当是个人时，由界面上取得合同号，当是团体下个人的时候，需要在保存个人合同下第一个的时候，生成LCCont

			if (ContNo == null || ("").equals(ContNo)) {
				// @@错误处理
				if (mDealFlag.equals("1")) {
					CError.buildErr(this,"未得到合同号!");
					return false;
				}
			} else {
				if (DiskImportFlag != null && DiskImportFlag.equals("1")) {
					// DoNothing
				} else {
					/** @todo 加入卡单处理逻辑 */
					if (mark != null) {
						if (mark.equals("card")) {
						} else {
							LCContDB tLCContDB = new LCContDB();
							tLCContDB.setContNo(ContNo);
							tLCContDB.getInfo();
							if (tLCContDB.mErrors.needDealError()) {
								// @@错误处理
								this.mErrors.copyAllErrors(tLCContDB.mErrors);
								CError.buildErr(this,"未查到相关合同信息!");
								return false;
							}

							mLCContSchema.setSchema(tLCContDB);
						}
					} else {
						LCContDB tLCContDB = new LCContDB();
						tLCContDB.setContNo(ContNo);
						tLCContDB.getInfo();
						if (tLCContDB.mErrors.needDealError()) {
							// @@错误处理
							this.mErrors.copyAllErrors(tLCContDB.mErrors);
							CError.buildErr(this,"未查到相关合同信息!");
							return false;
						}
						//tongmeng 2009-03-20 add
						//团险支持个单生效日指定
						String tCValiDate = mLCContSchema.getCValiDate();
						mLCContSchema.setSchema(tLCContDB);
						if (mDealFlag.equals("2")) {
							mLCContSchema.setCValiDate(tCValiDate);
						}
					}
				}
			}

			mLCContSchema.setFamilyType(FamilyType);
			if (!mOperate.equals("INSERT||CONTINSURED")) {
				logger.debug("InsuredNo:"
						+ mOLDLCInsuredDB.getInsuredNo());
				mOLDLCInsuredDB.setContNo(mLCContSchema.getContNo());
				if("card".equals(this.mark)){
					mOLDLCInsuredDB.setInsuredNo(mLCInsuredSchema.getInsuredNo());
				}else{
					mOLDLCInsuredDB.setInsuredNo(mOLDLCInsuredDB.getInsuredNo());
				}
				if (!mOLDLCInsuredDB.getInfo()) {
					// @@错误处理
					this.mErrors.copyAllErrors(mOLDLCInsuredDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "ContInsuredBL";
					tError.functionName = "deleteData";
					tError.errorMessage = "获取待删除被保险人信息时失败!";
					this.mErrors.addOneError(tError);
					return false;
				}

				if ("2".equals(mDealFlag)) {
					// 将序号赋值
					mLCInsuredSchema.setCustomerSeqNo(mOLDLCInsuredDB
							.getCustomerSeqNo());
				}

				if (!tPolTypeFlag.equals("0") && mDealFlag.equals("2")) {
					mLCContSchema.setPeoples(mLCContSchema.getPeoples()
							+ Integer.parseInt(tInsuredPeoples)
							- mOLDLCInsuredDB.getInsuredPeoples());
				}
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();

			CError tError = new CError();
			tError.moduleName = "ContInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;

		}
		logger.debug("this.mLCInsuredSchema.getLicenseType()=="
				+ this.mLCInsuredSchema.getLicenseType());

		logger.debug("this.mLDPersonSchema.getLicenseType()=="
				+ this.mLDPersonSchema.getLicenseType());

		// this.mGlobalInput.setSchema((GlobalInput)cInputData.
		// getObjectByObjectName("GlobalInput",0));
		return true;
	}

	public VData getResult() {
		mResult.remove(map);
		return this.mResult;
	}

	/**
	 * 处理保存逻辑，保存处理过的数据 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean insertData() {
		// 团单时需要更新团体合同表
		mLCContSchema.setModifyDate(theCurrentDate);
		mLCContSchema.setModifyTime(theCurrentTime);
		mLCInsuredSchema.setMakeDate(theCurrentDate);
		mLCInsuredSchema.setMakeTime(theCurrentTime);
		// //////////******************
		// String addperFlag = "0";
		// String perNum = "";

		String sql = "";

		if (mark != null && mark.equals("card")) { // add by yaory
			mLCContSchema.setGrpContNo("00000000000000000000");
			mLCContSchema.setProposalContNo(mLCContSchema.getContNo());
//			mLCContSchema.setPrtNo(mLCContSchema.getContNo());
			//不知道为什么将prtno覆盖为CONTNO 要与lcpol中数据统一 
			mLCContSchema.setPrtNo(mLCContSchema.getPrtNo());
			mLCContSchema.setAppntNo(appntno);
			mLCContSchema.setAppntName(mLCAppntBL.getAppntName());
			mLCContSchema.setAppntSex(mLCAppntBL.getAppntSex());
			mLCContSchema.setAppntBirthday(mLCAppntBL.getAppntBirthday());
			mLCContSchema.setAppntIDType(mLCAppntBL.getIDType());
			mLCContSchema.setAppntIDNo(mLCAppntBL.getIDNo());
			mLCContSchema.setInsuredNo(mLCInsuredSchema.getInsuredNo());
			mLCContSchema.setInsuredName(mLCInsuredSchema.getName());
			mLCContSchema.setInsuredBirthday(mLCInsuredSchema.getBirthday());
			mLCContSchema.setInsuredSex(mLCInsuredSchema.getSex());
			mLCContSchema.setInsuredIDType(mLCInsuredSchema.getIDType());
			mLCContSchema.setInsuredIDNo(mLCInsuredSchema.getIDNo());
			mLCContSchema.setManageCom(manageCom);
			mLCContSchema.setAgentCode(agentcode);
			mLCContSchema.setSignCom(signcom);
			mLCContSchema.setContType(ContType);
			mLCContSchema.setApproveFlag("9");
			mLCContSchema.setUWFlag("9");
			mLCContSchema.setAppFlag(appflag);
			mLCContSchema.setOperator(operator);
			mLCContSchema.setCardFlag("4");
			mLCContSchema.setModifyDate(theCurrentDate);
			mLCContSchema.setModifyTime(theCurrentTime);
			mLCContSchema.setMakeDate(theCurrentDate);
			mLCContSchema.setMakeTime(theCurrentTime);
			mLCInsuredSchema.setGrpContNo("00000000000000000000");
//			mLCInsuredSchema.setPrtNo(mLCContSchema.getContNo());
			mLCInsuredSchema.setPrtNo(mLCContSchema.getPrtNo());
			mLCInsuredSchema.setAppntNo(appntno);
			mLCInsuredSchema.setManageCom(manageCom);
			mLCInsuredSchema.setExecuteCom(manageCom);
		}

		//add by liuqh 2008-12-09 
		//团险录入被保险人
		if (mDealFlag.equals("2")) {
			mLCContSchema.setInsuredNo(mLCInsuredSchema.getInsuredNo());
			mLCContSchema.setInsuredName(mLCInsuredSchema.getName());
			mLCContSchema.setInsuredBirthday(mLCInsuredSchema.getBirthday());
			mLCContSchema.setInsuredSex(mLCInsuredSchema.getSex());
			mLCContSchema.setInsuredIDType(mLCInsuredSchema.getIDType());
			mLCContSchema.setInsuredIDNo(mLCInsuredSchema.getIDNo());
		}
		// ------------------------------------------Beg
		// 如果是无名补有名，为了避免数据转换的问题，应该这样处理
		// 如果客户已经转过有名，则生成的信息中用的CONTNO应该是原有的，如果没有则用新生成的。
		// tongmeng 2008-06-10 此处处理有问题!!!
		// 暂时处理方法,判断下是否团单

		// 先查询原有的CONTPLANCDE
		// modify on 2009-06-15 
		LCInsuredDB ttLCInsuredDB = new LCInsuredDB();
		LCInsuredSet ttLCInsuredSet = new LCInsuredSet();
		String ttsql = "select * from lcinsured where contno='"
			+ "?contno?" + "'";
		SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
		sqlbv50.sql(ttsql);
		sqlbv50.put("contno", mLCInsuredSchema.getPluralityType());
		ttLCInsuredSet = ttLCInsuredDB.executeQuery(sqlbv50);
		
		if (mDealFlag.equals("2")
				&& mLCInsuredSchema.getPluralityType() != null
				&& !mLCInsuredSchema.getPluralityType().equals("null")
				&& !mLCInsuredSchema.getPluralityType().equals("")
				&& ttLCInsuredSet.size()>0) {
			isFillList = true;
			// String tReturn = PubFun1.CreateMaxNo("CONTNO", "86"); //
			// String newContNo = PubFun1.creatVerifyDigit(tReturn);
			// mLCContSchema.setContNo(newContNo);
			mLCContSchema.setInsuredNo(mLCInsuredSchema.getInsuredNo());
			mLCContSchema.setInsuredName(mLCInsuredSchema.getName());
			mLCContSchema.setInsuredBirthday(mLCInsuredSchema.getBirthday());
			mLCContSchema.setInsuredSex(mLCInsuredSchema.getSex());
			mLCContSchema.setInsuredIDType(mLCInsuredSchema.getIDType());
			mLCContSchema.setInsuredIDNo(mLCInsuredSchema.getIDNo());
				String tem = ttLCInsuredSet.get(1).getContPlanCode();
				logger.debug("无名单的保障计划编码：" + tem);
				mLCInsuredSchema.setContPlanCode(tem);
				// *********************
				
				// //查询已经存在的个人合同号。
				// LCContDB rLCContDB = new LCContDB();
				// LCContSet rLCContSet = new LCContSet();
				// String rsql = "select * from lccont where grpcontno='" +
				// mLCContSchema.getGrpContNo() + "' and insuredno='" +
				// mLCContSchema.getInsuredNo() + "'";
				// logger.debug(rsql);
				// rLCContSet = rLCContDB.executeQuery(rsql);
				// if (rLCContSet != null && rLCContSet.size() > 0) {
				// //实际应把相应的金额也修改啦，不过时间太紧啦，暂不做。
				// mLCContSchema.setContNo(rLCContSet.get(1).getContNo());
				// mLCInsuredSchema.setContNo(rLCContSet.get(1).getContNo());
				// }
				// else {
				map.put(mLCContSchema, "DELETE&INSERT");
				map.put(mLCInsuredSchema, "INSERT");
				// }

		}
		// --------------------------------------------------End
		else {
			map.put(mLCContSchema, "DELETE&INSERT");
			map.put(mLCInsuredSchema, "INSERT");
		}
		logger.debug("LCContSchema.peoples=" + mLCContSchema.getPeoples());
		if (tLDPersonSchema != null && tPolTypeFlag != null
				&& tPolTypeFlag.equals("0")) {
			// map.put(tLDPersonSchema, "INSERT"); //yaory20050930公共帐户与无名单不用写入数据

			if (tLDPersonSchema.getCustomerNo() != null
					&& tLDPersonSchema.getName() != null) {
				map.put(tLDPersonSchema, "DELETE&INSERT"); // yaory20050930公共帐户与无名单不用写入数据
			}
		}
		/******************** 兼业结算投保人信息处理 *******************************/
		if (mLCAppntSchema != null && operateType != null
				&& operateType.equals("BrokerBalance")) {
			if (ISAppPersonSave) {
				map.put(mAppPerson, "DELETE&INSERT");
			}
			map.put(mLCAppntSchema, "DELETE&INSERT");
		}
		/********************** 兼业结算投保人信息处理 *******************************/
	//tongmeng 2009-03-30 modify
		//团险也更新地址表
		if (//mDealFlag.equals("1") && 
				tLCAddressSchema != null) {
			map.put(tLCAddressSchema, "DELETE&INSERT");
		}
		if (mLCCustomerImpartParamsSet != null) {
			map.put(mLCCustomerImpartParamsSet, "INSERT");
		}
		if (mLCCustomerImpartSet != null) {
			map.put(mLCCustomerImpartSet, "INSERT");
		}
		if (mLCCustomerImpartDetailSet != null) {
			map.put(mLCCustomerImpartDetailSet, "INSERT");
		}
		if (mDealFlag.equals("2")) {
			mLCGrpContDB.setGrpContNo(mLCContSchema.getGrpContNo());
			mLCGrpContDB.getInfo();
			if (mOperate.equals("INSERT||CONTINSURED") && !isFillList) {
				mLCGrpContDB.setPeoples2(mLCGrpContDB.getPeoples2()
						+ mLCContSchema.getPeoples());
			}
			if (mOperate.equals("UPDATE||CONTINSURED")
					&& !mLCContSchema.getPolType().equals("0")) {
				mLCGrpContDB.setPeoples2(mLCGrpContDB.getPeoples2()
						+ Integer.parseInt(tInsuredPeoples)
						- this.mOLDLCInsuredDB.getInsuredPeoples());
			}

			mLCGrpContDB.setModifyDate(theCurrentDate);
			mLCGrpContDB.setModifyTime(theCurrentTime);
			map.put(mLCGrpContDB.getSchema(), "UPDATE");
		}
		logger.debug(mLCInsuredSchema.getPluralityType());

		// ---------------------------------------Beg
		// 如果mLCInsuredSchema.getPluralityType()不等于NULL而是团单下的个人合同号，直接在这提交险种信息
		if (isFillList) {
			//无名单补名单程序修改 2009-07-17 不是平均分配 ，能够指定保费、保额
			double PolAmntRate =0.0;   //计算风险保额，保证补名单的 （保额/风险保额）=无名单的 （保额/风险保额）
			double PolPremRate =0.0;   //计算标准保费，保证补名单的 （保费/标准保费）=无名单的 （保费/标准保费）
			double DutyAmntRate =0.0;   //计算风险保额，保证责任项补名单的 （保额/风险保额）=无名单的 （保额/风险保额）
			double DutyPremRate =0.0;   //计算标准保费，保证责任项补名单的 （保费/标准保费）=无名单的 （保费/标准保费）
			double PremPremRate =0.0;   //计算标准保费，保证保费项补名单的 （保费/标准保费）=无名单的 （保费/标准保费）
			//-----------------------------------------------------
			// 补名单时是否需要拆分保费保额，及被保人数：0-不需要；1-需要
			String NeedDivAmnt = "1";// 默认为需要拆分保额
			ExeSQL sDivExeSQL = new ExeSQL();
			String sDivSQL = " select count(*) from lmrisksort where risksorttype='27' and risksortvalue='0' "
					+ " and riskcode in ( select riskcode  from lcpol where contno='"
					+ "?contno?" + "' )";
			SQLwithBindVariables sqlbv51 = new SQLwithBindVariables();
			sqlbv51.sql(sDivSQL);
			sqlbv51.put("contno", mLCInsuredSchema.getPluralityType());
			String tContDivAmnt = sDivExeSQL.getOneValue(sqlbv51);
			try {
				NeedDivAmnt = "1"; // 默认为需要拆分保额
				if (Integer.parseInt(tContDivAmnt) > 0) {
					NeedDivAmnt = "0"; // 不需要拆分保额
				}
			} catch (Exception e) {
				NeedDivAmnt = "1"; // 默认为需要拆分保额
			}

			logger.debug("无名单补录开始准备数据！");
			// 首先校验无名单人数是否都用完啦
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			LCInsuredSet tLCInsuredSet = new LCInsuredSet();
			sql = "select * from lcinsured where contno='"
					+ "?contno?" + "'";
			SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
			sqlbv52.sql(sql);
			sqlbv52.put("contno", mLCInsuredSchema.getPluralityType());
			tLCInsuredSet = tLCInsuredDB.executeQuery(sqlbv52);

			if (tLCInsuredSet == null || tLCInsuredSet.size() == 0
					|| tLCInsuredSet.get(1).getInsuredPeoples() == 0) {
				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "原无名单投保人数已经为0，不能进行补名单！";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 以下进行数据准备，首先求出总人数，然后各个数值直接除人数即可，，yaory
			LCPolSet tLCPolSet = new LCPolSet();
			LCPremSet tLCPremSet = new LCPremSet();
			LCGetSet tLCGetSet = new LCGetSet();
			LCDutySet tLCDutySet = new LCDutySet();
			LCInsuredSet mLCInsuredSet = new LCInsuredSet();

			int rate = tLCInsuredSet.get(1).getInsuredPeoples();
			int tFillPeoples = Integer.parseInt(tInsuredPeoples);
			tLCInsuredSet.get(1).setInsuredPeoples(rate - tFillPeoples);
			// 不需要拆分更新
			if (("0").equals(NeedDivAmnt)) {
				tLCInsuredSet.get(1).setInsuredPeoples(rate);
			}
			map.put(tLCInsuredSet, "UPDATE");
			// 修改合同人数
			LCContDB tLCContDB = new LCContDB();
			LCContSet tLCContSet = new LCContSet();
			tLCContDB.setContNo(mLCInsuredSchema.getPluralityType());
			tLCContSet = tLCContDB.query();
			if (tLCContSet != null && tLCContSet.size() > 0) {
				// 将所补名单签单日期与签单时间置为无名单相应值
				mLCContSchema.setSignDate(tLCContSet.get(1).getSignDate());
				mLCContSchema.setSignTime(tLCContSet.get(1).getSignTime());

				int contrate = tLCContSet.get(1).getPeoples();
				double contprem = 0;
				double contamnt = 0;
				double contsumprem = 0;
				contprem = Arith.round(tLCContSet.get(1).getPrem() / contrate,
						2);
				contsumprem = Arith.round(tLCContSet.get(1).getSumPrem()
						/ contrate, 2);
				contamnt = Arith.round(tLCContSet.get(1).getAmnt() / contrate,
						2);
//				double cont1 = tLCContSet.get(1).getPrem() - contprem
//						* tFillPeoples;
//				double cont2 = tLCContSet.get(1).getSumPrem() - contsumprem
//						* tFillPeoples;
//				double cont3 = tLCContSet.get(1).getAmnt() - contamnt
//						* tFillPeoples;
				double cont1 = tLCContSet.get(1).getPrem() - mLCContSchema.getPrem();
				//double cont2 = tLCContSet.get(1).getSumPrem() - mLCContSchema.getSumPrem();
				double cont3 = tLCContSet.get(1).getAmnt() - mLCContSchema.getAmnt();
				if(cont1<0||cont3<0){
					CError.buildErr(this, "合同信息错误：无名单剩余保费或保额小于此补名单保费或保额！");
					return false;
				}
				// 不需要拆分更新
				if (("0").equals(NeedDivAmnt)) {
					logger.debug("拆分");
					cont1 = tLCContSet.get(1).getPrem();
					//cont2 = tLCContSet.get(1).getSumPrem();
					cont3 = tLCContSet.get(1).getAmnt();
				}
				logger.debug("此时合同下无名单的保费、保额剩余分别为："+cont1+","+cont3);
				SQLwithBindVariables sqlbv53 = new SQLwithBindVariables();
				sqlbv53.sql("update lccont set Peoples=Peoples-" + "?Peoples?"
						+ ", prem='" + "?prem?" + "',/*sumprem='0"
						+ "',*/amnt='" + "?amnt?" + "' ,modifydate='"+"?modifydate?"
						+ "',modifytime ='"+"?modifytime?"+"' where contno='"
						+ "?contno?" + "'");
				sqlbv53.put("Peoples", tFillPeoples);
				sqlbv53.put("prem", cont1);
				sqlbv53.put("amnt", cont3);
				sqlbv53.put("modifydate", PubFun.getCurrentDate());
				sqlbv53.put("modifytime", PubFun.getCurrentTime());
				sqlbv53.put("contno", mLCInsuredSchema.getPluralityType());
				map.put(sqlbv53, "UPDATE");
			}
			// 第一个处理险种信息
			LCPolDB mLCPolDB = new LCPolDB();
			LCPolSet mLCPolSet = new LCPolSet();
			sql = "select * from lcpol where contno='"
					+ "?contno?" + "'";
			logger.debug(sql);
			SQLwithBindVariables sqlbv54 = new SQLwithBindVariables();
			sqlbv54.sql(sql);
			sqlbv54.put("contno",mLCInsuredSchema.getPluralityType());
			mLCPolSet = mLCPolDB.executeQuery(sqlbv54);
			LCPolSchema mLCPolSchema = new LCPolSchema();
			LCGetDB mLCGetDB = new LCGetDB();
			LCGetSet mLCGetSet = new LCGetSet();
			LCDutyDB mLCDutyDB = new LCDutyDB();
			LCDutySet mLCDutySet = new LCDutySet();
			LCPremDB mLCPremDB = new LCPremDB();
			LCPremSet mLCPremSet = new LCPremSet();
			String tNo = "";
			String tLimit = "";
			double riskamnt = 0;
			double prem = 0;
			double standprem = 0;
			double amnt = 0;
			double dutystandprem = 0;
			double dutyprem = 0;
			double dutysumprem = 0;
			double dutyamnt = 0;
			double dutyriskamnt = 0;
			double premstandprem = 0;
			double premprem = 0;
			double premsumprem = 0;
			double getactu = 0;
			double getstand = 0;
			double getsum = 0;
			double verifyAmnt = 0;
			double verifyPrem = 0;
			tLimit = PubFun.getNoLimit(mLCInsuredSchema.getManageCom());
			String polno = "";
			// 增加对数据转换得支持，如果已经转过得险种，则给出提示，不能进行另一此添加。
			// 首先根据合同号查询出这个人已经具有得险种
			LCPolDB rLCPolDB = new LCPolDB();
			LCPolSet rLCPolSet = new LCPolSet();
			String tsql = "select * from lcpol where contno='"
					+ "?contno?" + "'";
			logger.debug(tsql);
			SQLwithBindVariables sqlbv55 = new SQLwithBindVariables();
			sqlbv55.sql(tsql);
			sqlbv55.put("contno", mLCContSchema.getContNo());
			String tPolno = ""; // 以后以它为标志
			rLCPolSet = rLCPolDB.executeQuery(sqlbv55);
			double anonymouse_Prem =0;
			double anonymouse_Amnt =0;
			for (int i = 1; i <= mLCPolSet.size(); i++) {
				if(mLCPolSet.get(i).getPrem()!=0)
				PolPremRate = Arith.round(mLCPolSet.get(i).getStandPrem() / mLCPolSet.get(i).getPrem(), 2);//保费比例
				if(mLCPolSet.get(i).getAmnt()!=0)
				PolAmntRate = Arith.round(mLCPolSet.get(i).getRiskAmnt() / mLCPolSet.get(i).getAmnt(), 2);//保额比例
				
				
				mLCPolSchema = new LCPolSchema();
				mLCPolSchema = mLCPolSet.get(i);
				// 校验险种是否增加过
				if (rLCPolSet != null && rLCPolSet.size() > 0) {
					for (int m = 1; m <= rLCPolSet.size(); m++) {
						if (rLCPolSet.get(m).getRiskCode().equals(
								mLCPolSchema.getRiskCode())) {
							CError tError = new CError();
							tError.moduleName = "ContInsuredBL";
							tError.functionName = "getInputData";
							tError.errorMessage = "险种"
									+ mLCPolSchema.getRiskCode() + "您已经转换过！";
							this.mErrors.addOneError(tError);
							return false;

						}
					}
				}

				sDivSQL = " select count(*) from lmrisksort where risksorttype='27' and risksortvalue='0' "
						+ " and riskcode = '"
						+ "?riskcode?"
						+ "' ";
				SQLwithBindVariables sqlbv56 = new SQLwithBindVariables();
				sqlbv56.sql(sDivSQL);
				sqlbv56.put("riskcode", mLCPolSchema.getRiskCode());
				String tPolDivAmnt = sDivExeSQL.getOneValue(sqlbv56);
				try {
					NeedDivAmnt = "1"; // 默认为需要拆分保额
					if (Integer.parseInt(tPolDivAmnt) > 0) {
						NeedDivAmnt = "0"; // 不需要拆分保额
					}
				} catch (Exception e) {
					NeedDivAmnt = "1"; // 默认为需要拆分保额
				}

				polno = mLCPolSchema.getPolNo(); // 先准备一下
				tNo = PubFun1.CreateMaxNo("ProposalNo", tLimit);
				// 保单号需要新生成=proposalno=mainpolno;contno;
				// poltypeflag-0;insuredno;insuredname;
				// 性别，出生日期，年龄；职业类别，standprem,prem,amnt,riskamnt;modifydate,
				// modifytime;
				tPolno = mLCPolSchema.getPolNo();
				//取出对险种的信息
				double tAddOnePrem =0.0; //该险种保单的保费
				double tAddOneAmnt =0.0; //该险种保单的保额
				for(int m=1;m<=mmLCPolSet.size();m++){
					if(tPolno.equals(mmLCPolSet.get(m).getPolNo())){
						tAddOnePrem =mmLCPolSet.get(m).getPrem();
						tAddOneAmnt =mmLCPolSet.get(m).getAmnt();
					}
				}
				if(tAddOnePrem==0||tAddOneAmnt==0){
					CError.buildErr(this, "无名单保额、保费不能为‘0’，请重新录入！");
					return false;
				}
				mLCPolSchema.setPolNo(tNo);
				mLCPolSchema.setProposalNo(tNo);
				mLCPolSchema.setMainPolNo(tNo);
				mLCPolSchema.setContNo(mLCInsuredSchema.getContNo());
				mLCPolSchema.setPolTypeFlag(mLCContSchema.getPolType());
				mLCPolSchema.setInsuredNo(mLCInsuredSchema.getInsuredNo());
				mLCPolSchema.setContNo(mLCContSchema.getContNo());
				mLCPolSchema.setInsuredName(mLCContSchema.getInsuredName());
				mLCPolSchema.setInsuredPeoples(tFillPeoples);
				mLCPolSchema.setInsuredBirthday(mLCContSchema
						.getInsuredBirthday());
				mLCPolSchema.setInsuredAppAge(tInsuredAppAge);
				mLCPolSchema.setInsuredSex(mLCInsuredSchema.getSex());
				mLCPolSchema.setOccupationType(mLCInsuredSchema
						.getOccupationType());
				mLCPolSchema.setModifyDate(PubFun.getCurrentDate());
				mLCPolSchema.setModifyTime(PubFun.getCurrentTime());
				//无名单保费 用于计算lcget的actuget
				anonymouse_Prem =mLCPolSchema.getPrem();
				anonymouse_Amnt =mLCPolSchema.getAmnt();
				// 以下是涉及金额的
				prem = Arith.round(mLCPolSchema.getPrem() / rate, 2);
				amnt = Arith.round(mLCPolSchema.getAmnt() / rate, 2);
				

				riskamnt = Arith.round(mLCPolSchema.getRiskAmnt() / rate, 2);
				standprem = Arith.round(mLCPolSchema.getStandPrem() / rate, 2);
				verifyAmnt = verifyAmnt + amnt * tFillPeoples;
				verifyPrem = verifyPrem + standprem * tFillPeoples;
				// 修改原来的金额
//				double pol1 = mLCPolSchema.getPrem() - prem * tFillPeoples;
//				double pol2 = mLCPolSchema.getStandPrem() - standprem
//						* tFillPeoples;
//				double pol3 = mLCPolSchema.getAmnt() - amnt * tFillPeoples;
//				double pol4 = mLCPolSchema.getRiskAmnt() - riskamnt
				double pol1 = mLCPolSchema.getPrem() - tAddOnePrem;
				double pol2 = mLCPolSchema.getStandPrem() - tAddOnePrem;
				double pol3 = mLCPolSchema.getAmnt() - tAddOneAmnt;
				double pol4 = mLCPolSchema.getRiskAmnt() - tAddOneAmnt;
				if(pol1<0||pol2<0||pol3<0||pol4<0){
					CError.buildErr(this, "险种信息错误：无名单剩余保费或保额小于此补名单保费或保额！");
					return false;
				}
				// 然后给自己的保费

				mLCPolSchema.setInsuredName(mLCInsuredSchema.getName());
				mLCPolSchema.setInsuredNo(mLCInsuredSchema.getInsuredNo());
				mLCPolSchema.setInsuredBirthday(mLCInsuredSchema.getBirthday());
				mLCPolSchema.setInsuredSex(mLCInsuredSchema.getSex());

				// 不需要拆分更新
				if (("0").equals(NeedDivAmnt)) {
					pol1 = mLCPolSchema.getPrem();
					pol2 = mLCPolSchema.getStandPrem();
					pol3 = mLCPolSchema.getAmnt(); 
					pol4 = mLCPolSchema.getRiskAmnt();
					mLCPolSchema.setPrem(pol1);
					mLCPolSchema.setStandPrem(pol2);
					mLCPolSchema.setRiskAmnt(pol4);
					mLCPolSchema.setAmnt(pol3);
				} else {
//					mLCPolSchema.setPrem(prem * tFillPeoples);
//					mLCPolSchema.setStandPrem(standprem * tFillPeoples);
//					mLCPolSchema.setAmnt(amnt * tFillPeoples);
//					mLCPolSchema.setRiskAmnt(riskamnt * tFillPeoples);
					mLCPolSchema.setPrem(tAddOnePrem);
					mLCPolSchema.setStandPrem(tAddOnePrem*PolPremRate);
					mLCPolSchema.setAmnt(tAddOneAmnt);
					mLCPolSchema.setRiskAmnt(tAddOneAmnt*PolAmntRate);
				}
				SQLwithBindVariables sqlbv57 = new SQLwithBindVariables();
				sqlbv57.sql("update lcpol set prem = '" + "?prem?" + "',standprem='"
						+ "?standprem?" + "',amnt='" + "?amnt?" + "',riskamnt='" + "?riskamnt?"
						+ "',insuredpeoples=insuredpeoples-" + "?insuredpeoples?"
						+ " where polno='" + "?polno?" + "'");
				sqlbv57.put("prem", pol1);
				sqlbv57.put("standprem", pol2);
				sqlbv57.put("amnt", pol3);
				sqlbv57.put("riskamnt", pol4);
				sqlbv57.put("insuredpeoples", tFillPeoples);
				sqlbv57.put("polno", polno);
				map.put(sqlbv57, "UPDATE");
				map.put(mLCPolSchema, "DELETE&INSERT");

				tLCPolSet.add(mLCPolSchema);
				// 处理责任
				mLCDutyDB = new LCDutyDB();
				mLCDutySet = new LCDutySet();
				sql = "select * from lcduty where polno='" + "?polno?" + "'";
				SQLwithBindVariables sqlbv58 = new SQLwithBindVariables();
				sqlbv58.sql(sql);
				sqlbv58.put("polno", tPolno);
				logger.debug(sql);
				mLCDutySet = mLCDutyDB.executeQuery(sqlbv58);
				LCDutySchema mLCDutySchema = new LCDutySchema();
				logger.debug("LCDutySet.size()==" + mLCDutySet.size());
				
				//求出该险种所有责任的保费和
				double SumDutyPrem =0.0;
				double SumDutyAmnt =0.0;
				double SumDutySumPrem =0.0;
				for (int m=1;m<=mLCDutySet.size();m++){
					SumDutyPrem=SumDutyPrem+mLCDutySet.get(m).getPrem();
					SumDutyAmnt=SumDutyAmnt+mLCDutySet.get(m).getAmnt();
					SumDutySumPrem=SumDutySumPrem+mLCDutySet.get(m).getSumPrem();
				}
				double dutyRate_Prem=0.0;  //多责任，获取当前责任保费占总险种保费的比例
				double dutyRate_amnt=0.0;  //多责任，获取当前责任保额占总险种保额的比例
				double dutyRate_sumPrem =0.0;  //多责任，获取当前责任保额和占总险种保额和的比例
				for (int j = 1; j <= mLCDutySet.size(); j++) {
					//2009-07-17
					if(SumDutyPrem!=0)
					dutyRate_Prem =Arith.round(mLCDutySet.get(j).getPrem() / SumDutyPrem, 2);//得到当前责任占总保费的比例
					if(SumDutyAmnt!=0)
					dutyRate_amnt =Arith.round(mLCDutySet.get(j).getAmnt() / SumDutyAmnt, 2);//得到当前责任占总保额的比例
					if(SumDutySumPrem!=0)
					dutyRate_sumPrem =Arith.round(mLCDutySet.get(j).getSumPrem() / SumDutySumPrem, 2);//得到当前责任占总保费和的比例
					
					if(mLCDutySet.get(j).getPrem()!=0)
					DutyPremRate =Arith.round(mLCDutySet.get(j).getStandPrem() / mLCDutySet.get(j).getPrem(), 2);//用于求风险保额
					if(mLCDutySet.get(j).getAmnt()!=0)
					DutyAmntRate =Arith.round(mLCDutySet.get(j).getRiskAmnt() / mLCDutySet.get(j).getAmnt(), 2); //用于求风险保费
					//-----------------------------------------
					mLCDutySchema = new LCDutySchema();
					mLCDutySchema = mLCDutySet.get(j);
					// contno;polno;standprem,prem,sumprem;amnt;riskamnt;
					// ;modifydate,modifytime
					mLCDutySchema.setContNo(mLCInsuredSchema.getContNo());
					mLCDutySchema.setPolNo(tNo);
					mLCDutySchema.setModifyDate(PubFun.getCurrentDate());
					mLCDutySchema.setModifyTime(PubFun.getCurrentTime());
					dutystandprem = Arith.round(mLCDutySchema.getStandPrem()
							/ rate, 2);
					dutyprem = Arith.round(mLCDutySchema.getPrem() / rate, 2);
					dutysumprem = Arith.round(
							mLCDutySchema.getSumPrem() / rate, 2);
					dutyamnt = Arith.round(mLCDutySchema.getAmnt() / rate, 2);
					dutyriskamnt = Arith.round(mLCDutySchema.getRiskAmnt()
							/ rate, 2);

					double duty1 = mLCDutySet.get(j).getStandPrem()-tAddOnePrem * dutyRate_Prem*DutyPremRate;
					double duty2 = mLCDutySet.get(j).getPrem() - tAddOnePrem * dutyRate_Prem;
					//double duty3 = mLCDutySet.get(j).getSumPrem() - tAddOnePrem * dutyRate_Prem;//没有必要校验SUMPREM
					double duty4 = mLCDutySet.get(j).getAmnt() - tAddOneAmnt * dutyRate_amnt;
					double duty5 = mLCDutySet.get(j).getRiskAmnt()-tAddOneAmnt * dutyRate_amnt *DutyAmntRate;

					if(duty1<0||duty2<0||duty4<0||duty5<0){
						CError.buildErr(this, "责任信息错误：无名单剩余保费或保额小于此补名单保费或保额！");
						return false;
					}
					mLCDutySchema.setContNo(mLCContSchema.getContNo());
					// 不需要拆分更新
					if (("0").equals(NeedDivAmnt)) {
						duty1 = mLCDutySet.get(j).getStandPrem();
						duty2 = mLCDutySet.get(j).getPrem();
						//duty3 = mLCDutySet.get(j).getSumPrem();
						duty4 = mLCDutySet.get(j).getAmnt();
						duty5 = mLCDutySet.get(j).getRiskAmnt();
						mLCDutySchema.setStandPrem(duty1);
						mLCDutySchema.setPrem(duty2);
						mLCDutySchema.setSumPrem(duty2);
						mLCDutySchema.setAmnt(duty4);
						mLCDutySchema.setRiskAmnt(duty5);
					} else {
//						mLCDutySchema
//								.setStandPrem(dutystandprem * tFillPeoples);
//						mLCDutySchema.setPrem(dutyprem * tFillPeoples);
//						mLCDutySchema.setSumPrem(dutysumprem * tFillPeoples);
//						mLCDutySchema.setAmnt(dutyamnt * tFillPeoples);
//						mLCDutySchema.setRiskAmnt(dutyriskamnt * tFillPeoples);
						mLCDutySchema
						.setStandPrem(tAddOnePrem * dutyRate_Prem*DutyPremRate);
						mLCDutySchema.setPrem(tAddOnePrem * dutyRate_Prem);
						mLCDutySchema.setSumPrem(tAddOnePrem * dutyRate_Prem);
						mLCDutySchema.setAmnt(tAddOneAmnt * dutyRate_amnt);
						mLCDutySchema.setRiskAmnt(tAddOneAmnt * dutyRate_amnt *DutyAmntRate);
					}
					SQLwithBindVariables sqlbv59 = new SQLwithBindVariables();
					sqlbv59.sql("update lcduty set standprem='" + "?standprem?"
							+ "',prem='" + "?prem?" + "',amnt='" + "?amnt?" + "',riskamnt='" + "?riskamnt?"
							+ "' where polno='" + "?polno?" + "' and dutycode='"
							+ "?dutycode?" + "'");
					sqlbv59.put("standprem", duty1);
					sqlbv59.put("prem", duty2);
					sqlbv59.put("amnt", duty4);
					sqlbv59.put("riskamnt", duty5);
					sqlbv59.put("polno", polno);
					sqlbv59.put("dutycode", mLCDutySchema.getDutyCode());
					map.put(sqlbv59, "UPDATE");

					map.put(mLCDutySchema, "DELETE&INSERT");
					tLCDutySet.add(mLCDutySchema);
				}
				// 以上循环处理了责任
				// 处理保费
				mLCPremDB = new LCPremDB();
				mLCPremSet = new LCPremSet();
				sql = "select * From lcprem where polno='" + "?polno?" + "'";
				SQLwithBindVariables sqlbv60 = new SQLwithBindVariables();
				sqlbv60.sql(sql);
				sqlbv60.put("polno", tPolno);
				logger.debug(sql);
				mLCPremSet = mLCPremDB.executeQuery(sqlbv60);
				LCPremSchema mLCPremSchema = new LCPremSchema();
//				求出该险种所有责任的保费和
				double SumPremPrem =0.0;
				//double SumPremAmnt =0.0;
				double SumPremSumPrem =0.0;
				for (int n=1;n<=mLCPremSet.size();n++){
					SumPremPrem=SumPremPrem+mLCPremSet.get(n).getPrem();
					//SumPremAmnt=SumPremAmnt+mLCPremSet.get(n).get
					SumPremSumPrem=SumPremSumPrem+mLCPremSet.get(n).getSumPrem();
				}
				double premRate_Prem=0.0;  //多责任，获取当前责任保费占总险种保费的比例
				double premRate_amnt=0.0;  //多责任，获取当前责任保额占总险种保额的比例
				double prmeRate_sumPrem =0.0;  //多责任，获取当前责任保额和占总险种保额和的比例
				for (int t = 1; t <= mLCPremSet.size(); t++) {
//					2009-07-17
					if(SumPremPrem!=0)
					dutyRate_Prem =Arith.round(mLCPremSet.get(t).getPrem() / SumPremPrem, 2);//得到当前责任占总保费的比例
					//dutyRate_amnt =Arith.round(mLCDutySet.get(j).getAmnt() / SumDutyAmnt, 2);//得到当前责任占总保额的比例
					if(SumPremSumPrem!=0)
					dutyRate_sumPrem =Arith.round(mLCPremSet.get(t).getSumPrem() / SumPremSumPrem, 2);//得到当前责任占总保费和的比例
					
					if(mLCPremSet.get(t).getPrem()!=0)
					PremPremRate =Arith.round(mLCPremSet.get(t).getStandPrem() / mLCPremSet.get(t).getPrem(), 2);//用于求标准保费
					//DutyAmntRate =Arith.round(mLCDutySet.get(j).getRiskAmnt() / mLCDutySet.get(j).getAmnt(), 2); //用于求风险保费
					//-----------------------------------------
					
					
					mLCPremSchema = new LCPremSchema();
					mLCPremSchema = mLCPremSet.get(t);
					//contno;polno;standprem;prem;sumprem;modifydate,modifytime;
					mLCPremSchema.setContNo(mLCInsuredSchema.getContNo());
					mLCPremSchema.setPolNo(tNo);
					mLCPremSchema.setModifyDate(PubFun.getCurrentDate());
					mLCPremSchema.setModifyTime(PubFun.getCurrentTime());
					premstandprem = Arith.round(mLCPremSchema.getStandPrem()
							/ rate, 2);
					premprem = Arith.round(mLCPremSchema.getPrem() / rate, 2);
					premsumprem = Arith.round(
							mLCPremSchema.getSumPrem() / rate, 2);
//					double prem1 = mLCPremSet.get(t).getStandPrem()
//							- premstandprem * tFillPeoples;
//					double prem2 = mLCPremSet.get(t).getPrem() - premprem
//							* tFillPeoples;
//					double prem3 = mLCPremSet.get(t).getSumPrem() - premsumprem
//							* tFillPeoples;
					double prem1 = mLCPremSet.get(t).getStandPrem()-tAddOnePrem * dutyRate_Prem *PremPremRate;
					double prem2 = mLCPremSet.get(t).getPrem() - tAddOnePrem * dutyRate_Prem;
					//double prem3 = mLCPremSet.get(t).getSumPrem() - tAddOnePrem * dutyRate_Prem;//没有必要校验SUMPREM 无名单补名单只关注PREM和AMNT，都是一次交清。

					if(prem1<0||prem2<0){
						CError.buildErr(this, "费用信息错误：无名单剩余保费或保额小于此补名单保费或保额！");
						return false;
					}
					mLCPremSchema.setContNo(mLCContSchema.getContNo());
					// 不需要拆分更新
					if (("0").equals(NeedDivAmnt)) {
						prem1 = mLCPremSet.get(t).getStandPrem();
						prem2 = mLCPremSet.get(t).getPrem();
						//prem3 = mLCPremSet.get(t).getSumPrem();
						mLCPremSchema.setStandPrem(prem1);
						mLCPremSchema.setPrem(prem2);
						mLCPremSchema.setSumPrem(prem2);
					} else {
						mLCPremSchema
								.setStandPrem(tAddOnePrem * dutyRate_Prem *PremPremRate);
						mLCPremSchema.setPrem(tAddOnePrem * dutyRate_Prem );
						mLCPremSchema.setSumPrem(tAddOnePrem * dutyRate_sumPrem );
					}
					//
					SQLwithBindVariables sqlbv61 = new SQLwithBindVariables();
					sqlbv61.sql("update lcprem set StandPrem='" + "?StandPrem?"
							+ "',prem='" + "?prem?" + "' where polno='" + "?polno?" + "' and dutycode='"
							+ "?dutycode?"
							+ "' and payplancode='"
							+ "?payplancode?" + "'");
					sqlbv61.put("StandPrem", prem1);
					sqlbv61.put("prem", prem2);
					sqlbv61.put("polno", polno);
					sqlbv61.put("dutycode", mLCPremSchema.getDutyCode());
					sqlbv61.put("payplancode", mLCPremSchema.getPayPlanCode());
					map.put(sqlbv61, "UPDATE");

					map.put(mLCPremSchema, "DELETE&INSERT");
					tLCPremSet.add(mLCPremSchema);
				}
				// 以上处理了保费
				// 处理领取项
				mLCGetDB = new LCGetDB();
				mLCGetSet = new LCGetSet();
				sql = "select * From lcget where polno='" + "?polno?" + "'";
				SQLwithBindVariables sqlbv62 = new SQLwithBindVariables();
				sqlbv62.sql(sql);
				sqlbv62.put("polno", tPolno);
				logger.debug(sql);
				mLCGetSet = mLCGetDB.executeQuery(sqlbv62);
				LCGetSchema mLCGetSchema = new LCGetSchema();
				logger.debug("mLCGetSet.size():" + mLCGetSet.size());
				
//				double SumActuGet =0.0;
//				double SumStandMoney =0.0;
//				double SumMoney =0.0;
//				for (int o=1;o<=mLCGetSet.size();o++){
//					SumActuGet=SumActuGet+mLCGetSet.get(o).getActuGet();
//					SumStandMoney=SumStandMoney+mLCGetSet.get(o).getStandMoney();
//					SumMoney=SumMoney+mLCGetSet.get(o).getSumMoney();
//				}
//				double getActuRate=0.0;  //多责任，获取当前责任保费占总险种保费的比例
//				double getStandMoneyRate=0.0;  //多责任，获取当前责任保额占总险种保额的比例
//				double getSumMoneyRate =0.0;  //多责任，获取当前责任保额和占总险种保额和的比例
				double ActuGet_rate =0;
				double StandMoney_rate =0;
				double SumMoney_rate =0;
//				
				for (int m = 1; m <= mLCGetSet.size(); m++) {
					
					//2009-07-17
//					if(anonymouse_Prem!=0){
//					ActuGet_rate =Arith.round(mLCGetSet.get(m).getActuGet() / anonymouse_Prem, 2);
//					StandMoney_rate =Arith.round(mLCGetSet.get(m).getStandMoney() / anonymouse_Prem, 2);
//					SumMoney_rate =Arith.round(mLCGetSet.get(m).getSumMoney() / anonymouse_Prem, 2);
//					}
					//2009-08-07  通过和保额进行等比计算 无名单(StandMoney)/无名单(Amnt)=新单(StandMoney)/新单(Amnt)
					if(anonymouse_Amnt!=0){
						ActuGet_rate =Arith.round(mLCGetSet.get(m).getActuGet() / anonymouse_Amnt, 2);
						StandMoney_rate =Arith.round(mLCGetSet.get(m).getStandMoney() / anonymouse_Amnt, 2);
						SumMoney_rate =Arith.round(mLCGetSet.get(m).getSumMoney() / anonymouse_Amnt, 2);
					}
//					if(SumActuGet!=0)
//					   getActuRate =Arith.round(mLCGetSet.get(m).getActuGet() / SumActuGet, 2);//得到当前责任占总保费的比例
//					if(SumStandMoney!=0)
//					getStandMoneyRate =Arith.round(mLCGetSet.get(m).getStandMoney() / SumStandMoney, 2);//得到当前责任占总保额的比例
//					if(SumMoney!=0)
//					getSumMoneyRate =Arith.round(mLCGetSet.get(m).getSumMoney() / SumMoney, 2);//得到当前责任占总保费和的比例
					
					//PremPremRate =Arith.round(mLCDutySet.get(t).getStandPrem() / mLCDutySet.get(t).getPrem(), 2);//用于求标准保费
					//DutyAmntRate =Arith.round(mLCDutySet.get(j).getRiskAmnt() / mLCDutySet.get(j).getAmnt(), 2); //用于求风险保费
					//-----------------------------------------
					
					// contno;polno;insuredno;standmoney;actuget;summoney;
					// modifydate;modifytime;
					mLCGetSchema = new LCGetSchema();
					mLCGetSchema = mLCGetSet.get(m);
					logger.debug("mLCGetSchema.getGetDutyCode():"
							+ mLCGetSchema.getGetDutyCode());
					mLCGetSchema.setContNo(mLCInsuredSchema.getContNo());
					mLCGetSchema.setPolNo(tNo);
					mLCGetSchema.setInsuredNo(mLCInsuredSchema.getInsuredNo());
					mLCGetSchema.setModifyDate(PubFun.getCurrentDate());
					mLCGetSchema.setModifyTime(PubFun.getCurrentTime());
					// mLCContSchema.getContNo()
					mLCGetSchema.setContNo(mLCContSchema.getContNo());
					getactu = Arith.round(mLCGetSchema.getActuGet() / rate, 2);
					getstand = Arith.round(mLCGetSchema.getStandMoney() / rate,
							2);
					getsum = Arith.round(mLCGetSchema.getSumMoney() / rate, 2);
					//
//					double real1 = mLCGetSet.get(m).getActuGet() - getactu
//							* tFillPeoples;
//					double real2 = mLCGetSet.get(m).getSumMoney() - getsum
//							* tFillPeoples;
//					double real3 = mLCGetSet.get(m).getStandMoney() - getstand
//							* tFillPeoples;
//					double real1 = mLCGetSet.get(m).getActuGet() - tAddOnePrem*ActuGet_rate;
//					double real2 = mLCGetSet.get(m).getSumMoney() - tAddOnePrem*SumMoney_rate;
//					double real3 = mLCGetSet.get(m).getStandMoney() - tAddOnePrem*StandMoney_rate;
					double real1 = mLCGetSet.get(m).getActuGet() - tAddOneAmnt*ActuGet_rate;
					double real2 = mLCGetSet.get(m).getSumMoney() - tAddOneAmnt*SumMoney_rate;
					double real3 = mLCGetSet.get(m).getStandMoney() - tAddOneAmnt*StandMoney_rate;

					if(real1<0||real2<0||real3<0){
						CError.buildErr(this, "责任信息错误：无名单剩余领取金额或总金额小于此补名单剩余领取金额或总金额！");
						return false;
					}
					// 不需要拆分更新
					if (("0").equals(NeedDivAmnt)) {
						real1 = mLCGetSet.get(m).getActuGet();
						real2 = mLCGetSet.get(m).getSumMoney();
						real3 = mLCGetSet.get(m).getStandMoney();
						mLCGetSchema.setActuGet(real1);
						mLCGetSchema.setStandMoney(real3);
						mLCGetSchema.setSumMoney(real2);

					} else {
//						mLCGetSchema.setActuGet(tAddOnePrem*ActuGet_rate);
//						mLCGetSchema.setStandMoney(tAddOnePrem*StandMoney_rate);
//						mLCGetSchema.setSumMoney(tAddOnePrem*SumMoney_rate);
						mLCGetSchema.setActuGet(tAddOneAmnt*ActuGet_rate);
						mLCGetSchema.setStandMoney(tAddOneAmnt*StandMoney_rate);
						mLCGetSchema.setSumMoney(tAddOneAmnt*SumMoney_rate);
					}
					SQLwithBindVariables sqlbv63 = new SQLwithBindVariables();
					sqlbv63.sql("update lcget set ActuGet='" + "?ActuGet?"
							+ "',SumMoney='" + "?SumMoney?" + "',StandMoney='" + "?StandMoney?"
							+ "' where polno='" + "?polno?" + "' and dutycode='"
							+ "?polno?"
							+ "' and getdutycode='"
							+ "?getdutycode?" + "'");
					sqlbv63.put("ActuGet", real1);
					sqlbv63.put("SumMoney", real2);
					sqlbv63.put("StandMoney", real3);
					sqlbv63.put("polno", polno);
					sqlbv63.put("dutycode", mLCGetSchema.getDutyCode());
					sqlbv63.put("getdutycode", mLCGetSchema.getGetDutyCode());
					map.put(sqlbv63, "UPDATE");

					map.put(mLCGetSchema, "DELETE&INSERT");
					tLCGetSet.add(mLCGetSchema);
				}

				// 处理帐户
				LCInsureAccDB mLCInsureAccDB = new LCInsureAccDB();
				LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();
				sql = "select * from LCInsureAcc where  polno='" + "?polno?" + "'";
				SQLwithBindVariables sqlbv64 = new SQLwithBindVariables();
				sqlbv64.sql(sql);
				sqlbv64.put("polno", polno);
				mLCInsureAccSet = mLCInsureAccDB.executeQuery(sqlbv64);
				LCInsureAccSchema mLCInsureAccSchema = new LCInsureAccSchema();
				double insusumpay = 0;
				double insulastaccbal = 0;
				double insuaccbala = 0;
				double rinsusumpay = 0;
				double rinsulastaccbal = 0;
				double rinsuaccbala = 0;
				
				double insusumpay_rate = 0;
				double insulastaccbal_rate = 0;
				double insuaccbala_rate = 0;
				
				double sum_insusumpay =0.0;
				double sum_insulastaccbal = 0;
				double sum_insuaccbala = 0;
				for (int o=1;o<=mLCInsureAccSet.size();o++){
					sum_insusumpay=sum_insusumpay+mLCInsureAccSet.get(o).getSumPay();
					sum_insulastaccbal=sum_insulastaccbal+mLCInsureAccSet.get(o).getLastAccBala();
					sum_insuaccbala=sum_insuaccbala+mLCInsureAccSet.get(o).getInsuAccBala();
				}
				
				for (int k = 1; k < mLCInsureAccSet.size(); k++) {
					
					mLCInsureAccSchema = new LCInsureAccSchema();
					mLCInsureAccSchema = mLCInsureAccSet.get(k);
					//所占总值的比例
					insusumpay_rate=Arith.round(mLCInsureAccSchema.getSumPay()/ sum_insusumpay, 2);
					insulastaccbal_rate=Arith.round(mLCInsureAccSchema.getLastAccBala()/ sum_insulastaccbal, 2);
					sum_insuaccbala=Arith.round(mLCInsureAccSchema.getInsuAccBala()/ sum_insuaccbala, 2);
					// polno,contno,insuredno,sumpay,lastaccbala,insuaccbala;
//					insusumpay = Arith.round(mLCInsureAccSchema.getSumPay()
//							/ rate, 2);
//					insulastaccbal = Arith.round(mLCInsureAccSchema
//							.getLastAccBala()
//							/ rate, 2);
//					insuaccbala = Arith.round(mLCInsureAccSchema
//							.getInsuAccBala()
//							/ rate, 2);
					insusumpay = Arith.round(mLCInsureAccSchema.getSumPay()*insusumpay_rate, 2);
					insulastaccbal = Arith.round(mLCInsureAccSchema
							.getLastAccBala()*insulastaccbal_rate, 2);
					insuaccbala = Arith.round(mLCInsureAccSchema
							.getInsuAccBala()*sum_insuaccbala, 2);

//					rinsusumpay = mLCInsureAccSchema.getSumPay() - insusumpay
//							* tFillPeoples;
//					rinsulastaccbal = mLCInsureAccSchema.getLastAccBala()
//							- rinsulastaccbal * tFillPeoples;
//					rinsuaccbala = mLCInsureAccSchema.getInsuAccBala()
//							- rinsuaccbala * tFillPeoples;
					rinsusumpay = mLCInsureAccSchema.getSumPay() - insusumpay;
					rinsulastaccbal = mLCInsureAccSchema.getLastAccBala()-insulastaccbal;
					rinsuaccbala = mLCInsureAccSchema.getInsuAccBala()-insuaccbala;
					if(rinsusumpay<0||rinsulastaccbal<0||rinsuaccbala<0){
						CError.buildErr(this, "账户信息错误：无名单账户金额小于补名单的账户金额！");
						return false;
					
					}
					mLCInsureAccSchema.setSumPay(insusumpay);
					// mLCContSchema.getContNo()
					mLCInsureAccSchema.setContNo(mLCContSchema.getContNo());
					mLCInsureAccSchema.setLastAccBala(insulastaccbal);
					mLCInsureAccSchema.setInsuAccBala(insuaccbala);
					mLCInsureAccSchema.setModifyDate(PubFun.getCurrentDate());
					mLCInsureAccSchema.setModifyTime(PubFun.getCurrentTime());
					mLCInsureAccSchema.setPolNo(tNo);
					mLCInsureAccSchema.setContNo(mLCInsuredSchema.getContNo());
					mLCInsureAccSchema.setInsuredNo(mLCInsuredSchema
							.getInsuredNo());
					// tNo
					mLCInsureAccSchema.setPolNo(tNo);
					SQLwithBindVariables sqlbv65 = new SQLwithBindVariables();
					sqlbv65.sql("update LCInsureAcc set SumPay='"
							+ "?SumPay?" + "',LastAccBala='"
							+ "?LastAccBala?" + "',InsuAccBala='"
							+ "?InsuAccBala?" + "' where polno='" + "?polno?"
							+ "' and InsuAccNo='"
							+ "?InsuAccNo?" + "'");
					sqlbv65.put("SumPay", rinsusumpay);
					sqlbv65.put("LastAccBala", rinsulastaccbal);
					sqlbv65.put("InsuAccBala", rinsuaccbala);
					sqlbv65.put("polno", polno);
					sqlbv65.put("InsuAccNo", mLCInsureAccSchema.getInsuAccNo());
					map.put(sqlbv65,"UPDATE");
					map.put(mLCInsureAccSchema, "DELETE&INSERT");
				}
				// 修改帐户明细
				LCInsureAccClassDB mLCInsureAccClassDB = new LCInsureAccClassDB();
				LCInsureAccClassSet mLCInsureAccClassSet = new LCInsureAccClassSet();
				sql = "select * From LCInsureAccClass where polno='" + "?polno?"
						+ "'";
				SQLwithBindVariables sqlbv66 = new SQLwithBindVariables();
				sqlbv66.sql(sql);
				sqlbv66.put("polno",polno);
				LCInsureAccClassSchema mLCInsureAccClassSchema = new LCInsureAccClassSchema();
				mLCInsureAccClassSet = mLCInsureAccClassDB.executeQuery(sqlbv66);
				double classsumpay = 0;
				double classlast = 0;
				double classinsba = 0;
				double rclasssumpay = 0;
				double rclasslast = 0;
				double rclassinsba = 0;
				
				double sum_classsumpay = 0;
				double sum_classlast = 0;
				double sum_classinsba = 0;
				
				double classsumpay_rate = 0;
				double classlast_rate = 0;
				double classinsba_rate = 0;
				for (int r=1;r<=mLCInsureAccClassSet.size();r++){
					sum_classsumpay=sum_classsumpay+mLCInsureAccClassSet.get(r).getSumPay();
					sum_classlast=sum_classlast+mLCInsureAccClassSet.get(r).getLastAccBala();
					sum_classinsba=sum_classinsba+mLCInsureAccClassSet.get(r).getInsuAccBala();
				}
				
				for (int r = 1; r <= mLCInsureAccClassSet.size(); r++) {
					mLCInsureAccClassSchema = mLCInsureAccClassSet.get(r);
					
					if(sum_classsumpay!=0)
					classsumpay_rate=Arith.round(mLCInsureAccClassSchema.getSumPay()/ sum_classsumpay, 2);
					if(sum_classlast!=0)
					classlast_rate=Arith.round(mLCInsureAccClassSchema.getLastAccBala()/ sum_classlast, 2);
					if(sum_classinsba!=0)
					classinsba_rate=Arith.round(mLCInsureAccClassSchema.getInsuAccBala()/ sum_classinsba, 2);
					
					
//					classsumpay = Arith.round((mLCInsureAccClassSchema
//							.getSumPay() / rate)
//							* (rate - tFillPeoples), 2);
//					classlast = Arith.round((mLCInsureAccClassSchema
//							.getLastAccBala() / rate)
//							* (rate - tFillPeoples), 2);
//					classinsba = Arith.round((mLCInsureAccClassSchema
//							.getInsuAccBala() / rate)
//							* (rate - tFillPeoples), 2);
					classsumpay = Arith.round((mLCInsureAccClassSchema
							.getSumPay() *classsumpay_rate), 2);
					classlast = Arith.round((mLCInsureAccClassSchema
							.getLastAccBala() *classlast_rate), 2);
					classinsba = Arith.round((mLCInsureAccClassSchema
							.getInsuAccBala() *classinsba_rate), 2);
					
					rclasssumpay =mLCInsureAccClassSchema.getSumPay()-classsumpay;
					rclasslast =mLCInsureAccClassSchema.getLastAccBala()-classlast;
					rclassinsba =mLCInsureAccClassSchema.getInsuAccBala() -classinsba;
					if(rclasssumpay<0||rclasslast<0||rclassinsba<0){
						CError.buildErr(this, "账户分类表信息错误：无名单的账户金额小于补名单的账户金额！");
						return false;
					}
					SQLwithBindVariables sqlbv67 = new SQLwithBindVariables();
					sqlbv67.sql("update LCInsureAccClass set SumPay='"
							+ "?SumPay?" + "',LastAccBala='" + "?LastAccBala?"
							+ "',InsuAccBala='" + "?InsuAccBala?"
							+ "' where polno='"
							+ "?polno?"
							+ "' and InsuAccNo='"
							+ "?InsuAccNo?"
							+ "' and PayPlanCode='"
							+ "?PayPlanCode?"
							+ "' and OtherNo='"
							+ "?OtherNo?"
							+ "' and AccAscription='"
							+ "?AccAscription?" + "'");
					sqlbv67.put("SumPay", classsumpay);
					sqlbv67.put("LastAccBala", classlast);
					sqlbv67.put("InsuAccBala", classinsba);
					sqlbv67.put("polno", mLCInsureAccClassSchema.getPolNo());
					sqlbv67.put("InsuAccNo", mLCInsureAccClassSchema.getInsuAccNo());
					sqlbv67.put("PayPlanCode", mLCInsureAccClassSchema.getPayPlanCode());
					sqlbv67.put("OtherNo", mLCInsureAccClassSchema.getOtherNo());
					sqlbv67.put("AccAscription", mLCInsureAccClassSchema.getAccAscription());
					map.put(sqlbv67,
							"UPDATE");
					mLCInsureAccClassSchema.setPolNo(tNo);
					mLCInsureAccClassSchema.setSumPay(Arith.round(
							 classsumpay,2));
					mLCInsureAccClassSchema.setLastAccBala(Arith.round(
							 classlast, 2));
					mLCInsureAccClassSchema.setInsuAccBala(Arith.round(
							 classinsba, 2));
					mLCInsureAccClassSchema.setContNo(mLCInsuredSchema
							.getContNo());
					map.put(mLCInsureAccClassSchema, "DELETE&INSERT");
				}
				// LCPremToAcc
				LCPremToAccDB tLCPremToAccDB = new LCPremToAccDB();
				LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
				sql = "select * From LCPremToAcc where polno='" + "?polno?" + "'";
				SQLwithBindVariables sqlbv68 = new SQLwithBindVariables();
				sqlbv68.sql(sql);
				sqlbv68.put("polno", polno);
				tLCPremToAccSet = tLCPremToAccDB.executeQuery(sqlbv68);
				LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema();
				for (int r = 1; r <= tLCPremToAccSet.size(); r++) {
					tLCPremToAccSchema = new LCPremToAccSchema();
					tLCPremToAccSchema = tLCPremToAccSet.get(r);
					tLCPremToAccSchema.setPolNo(tNo);
					map.put(tLCPremToAccSchema, "DELETE&INSERT");
				}
				// LCGetToAcc
				LCGetToAccDB tLCGetToAccDB = new LCGetToAccDB();
				LCGetToAccSet tLCGetToAccSet = new LCGetToAccSet();
				sql = "select * From LCGetToAcc where polno='" + "?polno?" + "'";
				SQLwithBindVariables sqlbv69 = new SQLwithBindVariables();
				sqlbv69.sql(sql);
				sqlbv69.put("polno", polno);
				tLCGetToAccSet = tLCGetToAccDB.executeQuery(sqlbv69);
				LCGetToAccSchema tLCGetToAccSchema = new LCGetToAccSchema();
				for (int r = 1; r <= tLCGetToAccSet.size(); r++) {
					tLCGetToAccSchema = new LCGetToAccSchema();
					tLCGetToAccSchema = tLCGetToAccSet.get(r);
					tLCGetToAccSchema.setPolNo(tNo);
					map.put(tLCGetToAccSchema, "DELETE&INSERT");

				}
				// LCInsureAccTrace
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
				sql = "select * From LCInsureAccTrace where polno='" + "?polno?"
						+ "'";
				SQLwithBindVariables sqlbv70 = new SQLwithBindVariables();
				sqlbv70.sql(sql);
				sqlbv70.put("polno", polno);
				tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv70);
				LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
				double tAccTraceMoney = 0;
				double sum_tAccTraceMoney = 0;
				double tAccTraceMoney_rate = 0;
				for (int k=1;k<=tLCInsureAccTraceSet.size();k++){
					sum_tAccTraceMoney=sum_tAccTraceMoney+tLCInsureAccTraceSet.get(k).getMoney();
				}
				
				for (int r = 1; r <= tLCInsureAccTraceSet.size(); r++) {
					tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
					tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(r);
					if(sum_tAccTraceMoney!=0)
					tAccTraceMoney_rate=Arith.round(tLCInsureAccTraceSchema.getMoney()/ sum_tAccTraceMoney, 2);
					
					tLCInsureAccTraceSchema.setPolNo(tNo);
					tLCInsureAccTraceSchema.setContNo(mLCInsuredSchema
							.getContNo());
					tAccTraceMoney = Arith.round(tLCInsureAccTraceSchema
							.getMoney()
							* tAccTraceMoney_rate, 2);
					tLCInsureAccTraceSchema.setMoney(tAccTraceMoney);
					map.put(tLCInsureAccTraceSchema, "DELETE&INSERT");
				}
			}
			SQLwithBindVariables sqlbv71 = new SQLwithBindVariables();
			sqlbv71.sql("update lccont set appflag='1',uwflag='9',amnt="
					+ "?amnt?" + ",prem=" + "?prem?" + " where contno='"
					+ "?contno?" + "'");
			sqlbv71.put("amnt", mLCContSchema.getAmnt());
			sqlbv71.put("prem", mLCContSchema.getPrem());
			sqlbv71.put("contno", mLCContSchema.getContNo());
			map.put(sqlbv71, "UPDATE");

		}
		// -------------------------------------------------------End
		return true;
	}

	private void jbInit() throws Exception {
	}

	/*
	 * 保险计划分险种准备提交给ProposalBL的数据 add by jixf 20051119
	 */
	private VData prepareContPlanData(LCInsuredSchema insuredSchema,
			LCContSchema contSchema, LCContPlanRiskSchema tLCContPlanRiskSchema) {
		String tMult = (String) mTransferData.getValueByName("Mult");
		VData tNewVData = new VData();
		MMap tmpMap = new MMap();
		String tPlanType = tLCContPlanRiskSchema.getPlanType();
		String tMainRiskCode = tLCContPlanRiskSchema.getMainRiskCode();
		String strRiskCode = tLCContPlanRiskSchema.getRiskCode();
		String tGrpContNo = tLCContPlanRiskSchema.getGrpContNo();
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LCPolBL tMainPolBL = new LCPolBL();
		tLCGrpPolDB.setGrpContNo(tGrpContNo);
		logger.debug("-----------------grpcontno-----"
				+ tLCContPlanRiskSchema.getGrpContNo());
		tLCGrpPolDB.setRiskCode(strRiskCode);
		tLCGrpPolSet = tLCGrpPolDB.query();
		LCGrpPolSchema sLCGrpPolSchema = tLCGrpPolSet.get(1);

		if (sLCGrpPolSchema == null) {
			CError.buildErr(this,"险种对应的集体投保单没有找到!");

			return null;
		}

		// 拷贝一份，避免修改本地数据
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		tLCGrpPolSchema.setSchema(sLCGrpPolSchema);
		tNewVData.add(insuredSchema);
		tNewVData.add(contSchema);

		LCPolSchema tLCPolSchema = formLCPolSchema(insuredSchema,
				tLCContPlanRiskSchema, contSchema, tLCGrpPolSchema);
		// 主险保单信息
		tLCPolSchema.setInsuredNo(insuredSchema.getInsuredNo());
		// 填充所有保单信息
		VData polData = formatLCPol(tLCPolSchema, sLCGrpPolSchema);
		if (polData == null) {
			CError.buildErr(this,"mformatLCPol中有问题");
		}

		MMap polRelaMap = (MMap) polData.getObjectByObjectName("MMap", 0);
		tLCPolSchema = (LCPolSchema) polData.getObjectByObjectName(
				"LCPolSchema", 0);
		tLCPolSchema.setInsuredNo(insuredSchema.getInsuredNo());
		if (tMult != null && !"".equals(tMult)) {
			tLCPolSchema.setMult(tMult);
		} else {
			tLCPolSchema.setMult(1);
		}
		if (tMainRiskCode != null && !"".equals(tMainRiskCode)
				&& !tMainRiskCode.equals(strRiskCode)) {
			LCPolSchema mainPolSchema = (LCPolSchema) this.mMainPolMap
					.get(tMainRiskCode);
			if (mainPolSchema == null) {
				CError.buildErr(this, "险种" + strRiskCode + "查找主险保单失败");
				return null;
			}
			tMainPolBL.setSchema(mainPolSchema);
			tLCPolSchema.setMainPolNo(tMainPolBL.getPolNo());
		} else {
			tMainPolBL.setSchema(tLCPolSchema);
		}

		if (polRelaMap != null && polRelaMap.keySet().size() > 0) {
			tmpMap.add(polRelaMap);
		}

		// 责任信息查询和生成
		ContPlanQryBL contPlanQryBL = new ContPlanQryBL();
		VData tVData = new VData();
		logger.debug(tLCContPlanRiskSchema.getContPlanCode() + "-"
				+ insuredSchema.getContPlanCode());
		tLCContPlanRiskSchema.setContPlanCode(insuredSchema.getContPlanCode()); // yaory
		tVData.add(tLCContPlanRiskSchema);
		boolean b = contPlanQryBL.submitData(tVData, "");
		if (!b || contPlanQryBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(contPlanQryBL.mErrors);
			return null;
		}
		tVData = contPlanQryBL.getResult();
		TransferData tTransferData = new TransferData();
//		tTransferData = (TransferData) tVData.getObjectByObjectName(
//				"TransferData", 0);
		LCDutySet tDutySet = (LCDutySet) tVData.getObjectByObjectName(
				"LCDutySet", 0);
		if (tDutySet == null) {
			LCDutySchema tDutySchema = (LCDutySchema) tVData
					.getObjectByObjectName("LCDutySchema", 0);
			if (tDutySchema == null) {
				// CError.buildErr(this, "查询计划要约出错:责任信息不能为空");
				CError.buildErr(this,"mformatLCPol中有问题");

				return null;
			}
			setPolInfoByDuty(tLCPolSchema, tDutySchema);
			tDutySet = new LCDutySet();
			tDutySet.add(tDutySchema);
		}
		if (tDutySet == null || tDutySet.size() <= 0) {
			// CError.buildErr(this, strRiskCode + "要约信息生成错误出错");
			CError.buildErr(this,"mformatLCPol中有问题");

			return null;
		}
		tNewVData.add(tDutySet);
		// 保费
		LCPremSet tPremSet = new LCPremSet();

		String tPayRuleCode = (String) tTransferData
				.getValueByName("PayRuleCode");
		if (tPayRuleCode != null && !"".equals(tPayRuleCode)) {
			// 如果有缴费规则则取得缴费信息
			LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
			tLCPayRuleFactorySchema.setGrpContNo(tGrpContNo);
			tLCPayRuleFactorySchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLCPayRuleFactorySchema.setPayRuleCode(tPayRuleCode);

			VData tPayVData = new VData();
			tLCPolSchema.setPayRuleCode(tPayRuleCode);
			tPayVData.addElement(tLCPayRuleFactorySchema);
			tPayVData.addElement(tLCPolSchema);
			tPayVData.addElement(insuredSchema);

			QueryPayRuleUI tQueryPayRuleUI = new QueryPayRuleUI();
			if (!tQueryPayRuleUI.submitData(tPayVData)) {
				logger.debug("缴费规则查询失败！");
			} else {
				tPremSet = (LCPremSet) tQueryPayRuleUI.getResult()
						.getObjectByObjectName("LCPremSet", 0);
			}
		}
		tNewVData.add(tPremSet);

		String tAscriptionRuleCode = (String) tTransferData
				.getValueByName("AscriptionRuleCode");
		if (tAscriptionRuleCode != null && !"".equals(tAscriptionRuleCode)) {
			tLCPolSchema.setAscriptionRuleCode(tAscriptionRuleCode);
		}

		// 加入对应险种的集体投保单信息,险种承保描述信息
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		tLMRiskAppDB.setRiskCode(tLCContPlanRiskSchema.getRiskCode());
		LMRiskAppSchema tLMRiskAppSchema = tLMRiskAppDB.getSchema();
		if (tLMRiskAppSchema == null) {
			CError.buildErr(this,"mformatLCPol中有问题");
			return null;
		}
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(tLCContPlanRiskSchema.getRiskCode());
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		tLMRiskSchema = tLMRiskDB.getSchema();

		if (tLMRiskSchema == null) {
			CError.buildErr(this,"mformatLCPol中有问题");

			return null;
		}

		LDGrpDB tLDGrpDB = new LDGrpDB();
		tLDGrpDB.setCustomerNo(tLCGrpPolSchema.getCustomerNo());
		LDGrpSchema ttLDGrpSchema = tLDGrpDB.getSchema();

		LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
		tLCGrpAppntDB.setGrpContNo(sLCGrpPolSchema.getGrpContNo());
		LCGrpAppntSchema tLCGrpAppntSchema = tLCGrpAppntDB.getSchema();

		tTransferData.setNameAndValue("GrpImport", 1);
		// jixf 20051102
		logger.debug("-------------BQFlag"
				+ mTransferData.getValueByName("BQFlag"));
		if (mTransferData.getValueByName("BQFlag") != null
				&& mTransferData.getValueByName("BQFlag") != ""
				&& "2".equals(StrTool.cTrim(mTransferData.getValueByName(
						"BQFlag").toString()))
				&& ("NI".equals(StrTool.cTrim(mTransferData.getValueByName(
						"EdorType").toString())) || "NR".equals(StrTool
						.cTrim(mTransferData.getValueByName("EdorType")
								.toString())))) {
			// 保全保存标记
			logger.debug("----------------EdorTYpe-----"
					+ mTransferData.getValueByName("EdorTypeCal"));
			tTransferData.setNameAndValue("SavePolType", "2");
			tTransferData.setNameAndValue("EdorType", mTransferData
					.getValueByName("EdorType"));
			tTransferData.setNameAndValue("EdorTypeCal", mTransferData
					.getValueByName("EdorTypeCal"));

			logger.debug("");
			tTransferData.setNameAndValue("EdorValiDate", mTransferData
					.getValueByName("EdorValiDate"));

			logger.debug(tTransferData.getValueByName("SavePolType"));
		}

		if (tPlanType != null && "6".equals(tPlanType)) {
			tTransferData.setNameAndValue("ISPlanRisk", "Y");
			tTransferData.setNameAndValue("Mult", tMult);
			tTransferData.setNameAndValue("EdorType", mTransferData
					.getValueByName("EdorType"));
			logger.debug("-666EdorType"
					+ mTransferData.getValueByName("EdorType"));
		}
		// jixf end
		// LCPol 团险没有附加险的情况,主险附加险一样!
		// LCPolBL tLCPolBL = new LCPolBL();
		// tLCPolBL.setSchema(tLCPolSchema);
		tNewVData.add(mGlobalInput);
		tNewVData.add(tLCGrpPolSchema);
		tNewVData.add(ttLDGrpSchema);
		tNewVData.add(tLCGrpAppntSchema);
		tNewVData.add(tLCPolSchema);
		tNewVData.add(tTransferData);
		tNewVData.add(tMainPolBL);
		tNewVData.add(tmpMap);
		return tNewVData;
	}

	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			rResult.clear();
			rResult.add(map);
			mResult.clear();
			LCInsuredSchema tempLCInsuredSchema = new LCInsuredSchema();
			tempLCInsuredSchema.setSchema(mLCInsuredSchema);
			mResult.add(tempLCInsuredSchema);
			mResult.add(this.tLCAddressSchema);
			mResult.add(this.tLDPersonSchema);
			mResult.add(this.mLCContSchema);
			mResult.add(this.mLLAccountSchema);
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 处理数据，不提交后台执行
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean preparesubmitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!mOperate.equals("DELETE||CONTINSURED")) {
			if (!this.checkData()) {
				return false;
			}
			logger.debug("---checkData---");
			// 进行业务处理
			if (!dealData()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据处理失败ContInsuredBL-->dealData!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} else {
			if (!deleteData()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "deleteData";
				tError.errorMessage = "删除数据时失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		mInputData = null;
		return true;
	}

	private void setPolInfoByDuty(LCPolSchema tLCPolSchema,
			LCDutySchema dutySchema) {

		tLCPolSchema.setInsuYear(dutySchema.getInsuYear());
		tLCPolSchema.setInsuYearFlag(dutySchema.getInsuYearFlag());
		//tongmeng 2009-03-20 modify
		//不需要重新计算保险期间 
		if("M".equals(dutySchema.getInsuYearFlag()))
		{
			
		}
		
		
		logger.debug(dutySchema.getInsuYear() + "-"
				+ dutySchema.getInsuYearFlag());
		if (tLCPolSchema.getPrem() <= 0) {
			tLCPolSchema.setPrem(dutySchema.getPrem());
		}
		if (tLCPolSchema.getAmnt() <= 0) {
			tLCPolSchema.setAmnt(dutySchema.getAmnt());
		}

		tLCPolSchema.setPayEndYear(dutySchema.getPayEndYear());
		tLCPolSchema.setPayEndYearFlag(dutySchema.getPayEndYearFlag());
		tLCPolSchema.setGetYear(dutySchema.getGetYear());
		tLCPolSchema.setGetYearFlag(dutySchema.getGetYearFlag());
		tLCPolSchema.setAcciYear(dutySchema.getAcciYear());
		tLCPolSchema.setAcciYearFlag(dutySchema.getAcciYearFlag());
		if (tLCPolSchema.getMult() <= 0) {
			tLCPolSchema.setMult(dutySchema.getMult());
		}
		// 计算方向,在按分数卖的保单，切记算方向为O的时候
		if (dutySchema.getMult() > 0
				&& "O".equals(StrTool.cTrim(dutySchema.getPremToAmnt()))) {
			tLCPolSchema.setPremToAmnt(dutySchema.getPremToAmnt());
		}
		tLCPolSchema.setStandbyFlag1(dutySchema.getStandbyFlag1());
		tLCPolSchema.setStandbyFlag2(dutySchema.getStandbyFlag2());
		tLCPolSchema.setStandbyFlag3(dutySchema.getStandbyFlag3());
	}

	/*
	 * 保险计划中分险种提交到,计算后返回数据 add by jixf 20051119
	 */
	private MMap submiDatattoProposalBL(VData tNewVData,
			LCContSchema ContSchema, LCGrpPolSchema tLCGrpPolSchema) {
		// tNewVData 里面还有一些要创建的信息，因此需要重新获得
		MMap map = (MMap) tNewVData.getObjectByObjectName("MMap", 0);
		if (map == null) {
			map = new MMap();
		}
		//tongmeng 2009-03-30 modify
		//如果该合同被保人之前已经保存过该记录.提交参数为UPDATE||PROPOSAL
		ProposalBL tProposalBL = new ProposalBL();
		if (!tProposalBL.PrepareSubmitData(tNewVData, "INSERT||PROPOSAL")) {
			this.mErrors.copyAllErrors(tProposalBL.mErrors);
			return null;
		} else {
			VData pData = tProposalBL.getSubmitResult();
			if (pData == null) {
				// CError.buildErr(this, "保单提交计算失败!");
				CError.buildErr(this,"mformatLCPol中有问题");

				return null;
			}
			//
			// LCContSchema rcontSchema =
			// (LCContSchema)pData.getObjectByObjectName("LCContSchema",2);
			LCPolSchema rPolSchema = (LCPolSchema) pData.getObjectByObjectName(
					"LCPolSchema", 0);
			LCGrpPolSchema rGrpPol = (LCGrpPolSchema) pData
					.getObjectByObjectName("LCGrpPolSchema", 0);
			LCGrpContSchema rGrpCont = (LCGrpContSchema) pData
					.getObjectByObjectName("LCGrpContSchema", 0);
			LCPremBLSet rPremBLSet = (LCPremBLSet) pData.getObjectByObjectName(
					"LCPremBLSet", 0);
			if (rPremBLSet == null) {
				// CError.buildErr(this, "保单提交计算保费项数据准备有误!");
				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "submitData";
				tError.errorMessage = "mformatLCPol中有问题";
				this.mErrors.addOneError(tError);

				return null;
			}
			LCPremSet rPremSet = new LCPremSet();
			for (int i = 1; i <= rPremBLSet.size(); i++) {
				rPremSet.add(rPremBLSet.get(i));
			}

			LCDutyBLSet rDutyBLSet = (LCDutyBLSet) pData.getObjectByObjectName(
					"LCDutyBLSet", 0);
			if (rDutyBLSet == null) {
				// CError.buildErr(this, "保单提交计算责任项数据准备有误!");
				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "submitData";
				tError.errorMessage = "mformatLCPol中有问题";
				this.mErrors.addOneError(tError);

				return null;
			}
			LCDutySet rDutySet = new LCDutySet();
			for (int i = 1; i <= rDutyBLSet.size(); i++) {
				rDutySet.add(rDutyBLSet.get(i));
			}

			LCGetBLSet rGetBLSet = (LCGetBLSet) pData.getObjectByObjectName(
					"LCGetBLSet", 0);
			if (rGetBLSet == null) {
				// CError.buildErr(this, "保单提交计算给付项数据准备有误!");
				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "submitData";
				tError.errorMessage = "mformatLCPol中有问题";
				this.mErrors.addOneError(tError);

				return null;
			}
			LCGetSet rGetSet = new LCGetSet();
			for (int i = 1; i <= rGetBLSet.size(); i++) {
				rGetSet.add(rGetBLSet.get(i));
			}

			LCBnfBLSet rBnfBLSet = (LCBnfBLSet) pData.getObjectByObjectName(
					"LCBnfBLSet", 0);
			LCBnfSet rBnfSet = new LCBnfSet();
			if (rBnfBLSet != null) {
				for (int i = 1; i <= rBnfBLSet.size(); i++) {
					rBnfSet.add(rBnfBLSet.get(i));
				}
			}
			LCInsuredRelatedSet tLCInsuredRelatedSet = (LCInsuredRelatedSet) pData
					.getObjectByObjectName("LCInsuredRelatedSet", 0);
			// if ( tLCInsuredRelatedSet!=null && LCInsuredRelatedSet.size()>0)

			// 更新个单合同金额相关，更新的时候同时更新缓存中的数据
			// String contId = m_GrpPolImpInfo.getContKey(PolKey);
			// LCContSchema tLCContSchema = m_GrpPolImpInfo.findLCContfromCache(
			// co ntId);
			LCContSchema tLCContSchema = new LCContSchema();
			tLCContSchema.setSchema(ContSchema);
			if (tLCContSchema == null) {
				// CError.buildErr(this, "查找合同[" + contId + "]失败");
				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "submitData";
				tError.errorMessage = "mformatLCPol中有问题";
				this.mErrors.addOneError(tError);

				return null;
			}
			String tContNo = tLCContSchema.getContNo();
			tLCContSchema.setPrem(PubFun.setPrecision(tLCContSchema.getPrem()
					+ rPolSchema.getPrem(), "0.00"));
			tLCContSchema.setAmnt(tLCContSchema.getAmnt()
					+ rPolSchema.getAmnt());
			tLCContSchema.setSumPrem(tLCContSchema.getPrem()//update by liuqh
					+ rPolSchema.getSumPrem());
			tLCContSchema.setMult(tLCContSchema.getMult()
					+ rPolSchema.getMult());
			LCContSchema tContSchema = new LCContSchema();

			tContSchema.setSchema(tLCContSchema);
			LCContSet tContSet = new LCContSet();
			// 更新集体险种金额相关
			// String riskCode = rPolSchema.getRiskCode();
			// LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			// tLCGrpPolDB.setGrpPolNo(rPolSchema.getGrpPolNo());
			// tLCGrpPolSchema = tLCGrpPolDB.query().get(1);
			//tLCGrpPolSchema.setPrem(PubFun.setPrecision(tLCGrpPolSchema.getPrem
			// () +
			// rPolSchema.getPrem(), "0.00"));
			// tLCGrpPolSchema.setAmnt(tLCGrpPolSchema.getAmnt() +
			// rPolSchema.getAmnt());
			// logger.debug(
			// "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			//tLCGrpPolSchema.setPayEndDate(PubFun.getLaterDate(tLCGrpPolSchema.
			// getPayEndDate(), rPolSchema.getPayEndDate()));
			// tLCGrpPolSchema.setPaytoDate(PubFun.getLaterDate(tLCGrpPolSchema.
			// getPaytoDate(), rPolSchema.getPaytoDate()));
			// tLCGrpPolSchema.setFirstPayDate(PubFun.getBeforeDate(
			// tLCGrpPolSchema.
			// getFirstPayDate(), rPolSchema.getFirstPayDate()));
			//
			// rGrpPol.setPrem(tLCGrpPolSchema.getPrem());
			// rGrpPol.setAmnt(tLCGrpPolSchema.getAmnt());
			// rGrpPol.setPayEndDate(tLCGrpPolSchema.getPayEndDate());
			// rGrpPol.setPaytoDate(tLCGrpPolSchema.getPaytoDate());
			// rGrpPol.setFirstPayDate(tLCGrpPolSchema.getFirstPayDate());

			// //更新团单合同金额相关
			// LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			// tLCGrpContDB.setGrpContNo(tLCGrpPolSchema.getGrpContNo());
			// LCGrpContSchema sGrpContSchema = tLCGrpContDB.query().get(1);
			//sGrpContSchema.setPrem(PubFun.setPrecision(sGrpContSchema.getPrem(
			// ) +
			// rPolSchema.getPrem(), "0.00"));
			// sGrpContSchema.setAmnt(sGrpContSchema.getAmnt() +
			// rPolSchema.getAmnt());

			// rGrpCont.setPrem(sGrpContSchema.getPrem());
			// rGrpCont.setAmnt(sGrpContSchema.getAmnt());
			// rGrpCont.setPeoples2(mLCGrpContDB.getPeoples2());
			tContSet.add(tContSchema);
			map.put(tContSet, "UPDATE");
			map.put(rPolSchema, "INSERT");
			map.put(rGrpPol, "UPDATE");
			// map.put(rGrpCont, "UPDATE");
			map.put(rPremSet, "INSERT");
			map.put(rDutySet, "INSERT");
			map.put(rGetSet, "INSERT");
			map.put(rBnfSet, "INSERT");
			map.put(tLCInsuredRelatedSet, "INSERT");
			Date date = new Date();
			Random rd = new Random(date.getTime());
			long u = rd.nextLong();
			// 个人单需要重新统计个单合同人数
			if ("0".equals(tContSchema.getPolType())) {
				String s0 = " update lccont set peoples=(select count(distinct insuredno) from lcpol where '"
						+ "?u?"
						+ "'='"
						+ "?u?"
						+ "' and contno='"
						+ "?contno?"
						+ "')"
						+ " where contno='"
						+ "?contno?" + "'" + " and PolType = '0' ";
				SQLwithBindVariables sqlbv72 = new SQLwithBindVariables();
				sqlbv72.sql(s0);
				sqlbv72.put("u", u);
				sqlbv72.put("contno", tContSchema.getContNo());
				map.put(sqlbv72, "UPDATE");
			}

			String s2 = "update lcgrppol set peoples2=(select (case when sum(insuredpeoples) is null then 0 else sum(insuredpeoples) end) from lcpol where '"
					  + "?u?"
				      + "'='"
			 	      + "?u?"
				      +"' and grppolno='"
					+ "?grppolno?"
					+ "') where grppolno='"
					+ "?grppolno?" + "'";
			SQLwithBindVariables sqlbv73 = new SQLwithBindVariables();
			sqlbv73.sql(s2);
			sqlbv73.put("u", u);
			sqlbv73.put("grppolno", rGrpPol.getGrpPolNo());
			map.put(sqlbv73, "UPDATE");
			String fromPart = "from LCPol where GrpContNo='"
					+ "?GrpContNo?" + "')";
			String updateLCGrpContStr = "update LCGrpCont set "
					+ "Prem=(select SUM(Prem) " + fromPart
					+ ",Amnt=(select SUM(Amnt) " + fromPart
					+ ",SumPrem=(select SUM(SumPrem) " + fromPart
					+ ",Mult=(select SUM(Mult) " + fromPart
					+ ",Peoples2=(select SUM(Peoples) "
					+ "from lccont where grpcontno='"
					+ "?grpcontno?" + "')" + " where '"
					+ "?u?"
				    + "'='"
			 	    + "?u?"
				    +"' and grpcontno='"
					+ "?grpcontno?" + "'";
			SQLwithBindVariables sqlbv74 = new SQLwithBindVariables();
			sqlbv74.sql(updateLCGrpContStr);
			sqlbv74.put("u", u);
			sqlbv74.put("GrpContNo", rGrpCont.getGrpContNo());
			sqlbv74.put("grpcontno", tContSchema.getGrpContNo());
			map.put(sqlbv74, "UPDATE");

			cachePolInfo(rPolSchema.getRiskCode(), rPolSchema);
		}

		return map;
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
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 保单

		// 得到外部传入的数据,将数据备份到本类中
		if (mOperate.equals("DELETE||INSUREDRISK")) {
			logger.debug("asfdsfsfafasf");

			if (!delrisk(cInputData)) {
				// @@错误处理
				CError.buildErr(this,"数据处理失败ContInsuredBL-->delrisk!");
				return false;

			}
		} else {
			if (!getInputData(cInputData)) {
				return false;
			}
			logger.debug("after getInputData!!!!!");
			if (!mOperate.equals("DELETE||CONTINSURED")) {
				if (!this.checkData()) {
					return false;
				}

				logger.debug("---checkData---");
				// 进行业务处理
				if (!dealData()) {
					// @@错误处理
					CError.buildErr(this,"数据处理失败ContInsuredBL-->dealData!");
					return false;
				}
				//tongmeng 2008-11-11 注释
				// if (mOperate.equals("INSERT||CONTINSURED")) {
				if (mLCInsuredSchema.getContPlanCode() != null
						&& !mLCInsuredSchema.getContPlanCode().equals("")) {
					if (!isFillList) // 若为无名补有名则不计算
					{
						if (!ISPlanRisk) {
							if (!contplandeal()) {
								// @@错误处理
								CError.buildErr(this,"数据处理失败ContInsuredBL-->contplandeal!");
								return false;
							}
						}
					}
				}
			  //add by hu 2012-6-6 若是无名单补名单则不能删除无名单的lcpol等相关表信息，其他的没明白为什么要删除
			  if (!isFillList)
			  {
				if(!deleteRisk()){
					// @@错误处理
					CError.buildErr(this,"数据处理失败ContInsuredBL-->dealData!");
					return false;
				}
			  }
				} else {
				if (!deleteData()) {
					// @@错误处理
					CError.buildErr(this,"删除数据时失败!");
					return false;
				}
			}

			// 在进行被保险人修改的之后,如果有个人险种要进行重算
			// if (mOperate.equals("UPDATE||CONTINSURED") &&
			// preLCPolSet.size() > 0)
			// {
			// LCContSchema aftLCContSchema = new LCContSchema();
			// LCPolSet aftLCPolSet = new LCPolSet();
			// VData tInputData = new VData();
			// ReCalInsuredBL tReCalInsuredBl = new ReCalInsuredBL(
			// mLCInsuredSchema,
			// preLCPolSet, mLCContSchema, mGlobalInput);
			// if (!tReCalInsuredBl.reCalInsured())
			// {
			// this.mErrors.copyAllErrors(tReCalInsuredBl.mErrors);
			// return false;
			// }
			// tInputData = tReCalInsuredBl.getResult();
			// map.add((MMap) tInputData.getObjectByObjectName("MMap", 0));
			// //团单下，单更新了个人合同时，也需要更新团体合同和集体险种表
			// if (ContType.equals("2"))
			// {
			// if ((LCContSchema) tInputData.getObjectByObjectName(
			// "LCContSchema", 0) != null)
			// {
			// aftLCContSchema = (LCContSchema) tInputData.
			// getObjectByObjectName(
			// "LCContSchema", 0);
			// aftLCPolSet = (LCPolSet) tInputData.
			// getObjectByObjectName(
			// "LCPolSet", 0);
			// mLCGrpContDB.setGrpContNo(aftLCContSchema.getGrpContNo());
			// //集体险种信息
			// String wherePart = "from LCPol where GrpContNo='" +
			// mLCGrpContDB.getGrpContNo()
			// + "')";
			// String updateLCGrpContStr = "update LCGrpCont set "
			// + "Prem=(select SUM(Prem) " +
			// wherePart
			// + ",Amnt=(select SUM(Amnt) " +
			// wherePart
			// + ",SumPrem=(select SUM(SumPrem) " +
			// wherePart
			// + ",Mult=(select SUM(Mult) " +
			// wherePart
			// + ",Peoples2=(select SUM(Peoples) "
			// + "from lccont where grpcontno='"
			// + mLCGrpContDB.getGrpContNo() +
			// "')"
			// + " where grpcontno='"
			// + mLCGrpContDB.getGrpContNo() +
			// "'";
			//
			// map.put(updateLCGrpContStr, "UPDATE");
			// //更新集体险种表
			// for (int i = 1; i <= preLCPolSet.size(); i++)
			// {
			// String fromPart = "from LCPol where GrpContNo='" +
			// aftLCContSchema.getGrpContNo()
			// + "' and riskcode ='" +
			// preLCPolSet.get(i).getRiskCode() +
			// "')";
			// map.put("update LCGrpPol set "
			// + "Prem=(select SUM(Prem) " + fromPart
			// + ", Amnt=(select SUM(Amnt) " + fromPart
			// + ", SumPrem=(select SUM(SumPrem) " +
			// fromPart
			// + ", Mult=(select SUM(Mult) " + fromPart
			// + ", Peoples2=(select SUM(InsuredPeoples) " +
			// fromPart + " where grppolno='" +
			// preLCPolSet.get(i).getGrpPolNo()
			// + "'", "UPDATE");
			// }
			// }
			//
			// }
			// }
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		// 　数据提交、保存
		// add by yaory
		if (mark != null) {
			if (mark.equals("card")) {
				logger.debug("in continsuredbl 是卡单！");
				return true;
			}
		}
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		mInputData = null;
		return true;
	}

	/**
	 * 更新逻辑，更新处理过的数据 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean updateData() {
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mLCContSchema.getContNo());
		tLCPolDB.setInsuredNo(mOLDLCInsuredDB.getInsuredNo());
		tLCPolSet = tLCPolDB.query();
		if (tLCPolDB.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "ContInsuredBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询该客户的险种保单失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug(StrTool.compareString(mOLDLCInsuredDB
				.getContPlanCode(), mLCInsuredSchema.getContPlanCode()));
		if (tLCPolSet.size() > 0) {
			if (!StrTool.compareString(mOLDLCInsuredDB.getContPlanCode(),
					mLCInsuredSchema.getContPlanCode())) {
				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "dealData";
				tError.errorMessage = "该被保险人下还有险种保单，不能更新保险计划！";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (mDealFlag.equals("2")) {
				String tSQL = "";
				int tAgeDiff = 0;
				LCPolSet t1LCPolSet = new LCPolSet();
				LCPolSchema tLCPolSchema = new LCPolSchema();
				if (!mLCContSchema.getPolType().equals("0")) {
					tAgeDiff = mLCInsuredSchema.getInsuredPeoples()
							- mOLDLCInsuredDB.getInsuredPeoples();
					tSQL = "update LCGrpPol set peoples2=peoples2+"
							+ "?tAgeDiff?"
							+ " where GrpPolNo in (select distinct GrpPolNo from lcpol where ContNo='"
							+ "?ContNo?" + "' and InsuredNo='"
							+ "?InsuredNo?" + "')";
					SQLwithBindVariables sqlbv75 = new SQLwithBindVariables();
					sqlbv75.sql(tSQL);
					sqlbv75.put("tAgeDiff",mLCInsuredSchema.getInsuredPeoples()
							- mOLDLCInsuredDB.getInsuredPeoples());
					sqlbv75.put("ContNo", mLCContSchema.getContNo());
					sqlbv75.put("InsuredNo", mOLDLCInsuredDB.getInsuredNo());
					map.put(sqlbv75, "UPDATE");
				}
				for (int i = 0; i < tLCPolSet.size(); i++) {
					tLCPolSchema = tLCPolSet.get(i + 1);
					tLCPolSchema.setInsuredPeoples(tLCPolSchema
							.getInsuredPeoples()
							+ tAgeDiff);
					tLCPolSchema.setInsuredAppAge(tInsuredAppAge);
					tLCPolSchema.setInsuredName(mLCInsuredSchema.getName());
					tLCPolSchema.setInsuredNo(mLCInsuredSchema.getInsuredNo());
					tLCPolSchema.setInsuredBirthday(mLCInsuredSchema
							.getBirthday());
					tLCPolSchema.setInsuredSex(mLCInsuredSchema.getSex());
					t1LCPolSet.add(tLCPolSchema);
				}
				map.put(t1LCPolSet, "UPDATE");
			}
		}
		if (!deleteData()) {
			return false;
		}
		if (!insertData()) {
			return false;
		}
		logger.debug("ys---------the mOLDLCInsuredDB.getInsuredNo() is :"
				+ mOLDLCInsuredDB.getInsuredNo());
		logger.debug("ys---------the mOLDLCInsuredDB.getInsuredNo() is :"
				+ mLCInsuredSchema.getInsuredNo());
		// 当客户号码，姓名发生变更时，要更新险种表，领取项表
		//tongmeng 2009-03-23 add
		//对于团单如果生效日发生变化了,也需要修改
		boolean tCValidateFlag = false;
		String tOldCValidate = "";
		if(mDealFlag.equals("2"))
		{
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv76 = new SQLwithBindVariables();
			sqlbv76.sql("select (case when to_char(cvalidate,'yyyy-mm-dd') is null then '' else to_char(cvalidate,'yyyy-mm-dd') end) from lccont where contno='"+"?contno?"+"'");
			sqlbv76.put("contno", mLCInsuredSchema.getContNo());
			tOldCValidate = tExeSQL.getOneValue(sqlbv76);
			
			String tCurrentCValiDate = mLCContSchema.getCValiDate();
			if(tCurrentCValiDate==null)
			{
				tCurrentCValiDate = "";
			}
			if(!tCurrentCValiDate.equals(tOldCValidate)
				)
			{
				tCValidateFlag = true;
			}
		}
		//tongmeng 2009-06-22 modify
		//如果职业类别发生变化,也需要重算
		String tOldocc = mOLDLCInsuredDB.getOccupationType();
		if(tOldocc==null)
		{
			tOldocc = "";
		}
		String tNewocc = mLCInsuredSchema.getOccupationType();
		if(tNewocc==null)
		{
			tNewocc = "";
		}
		if ((!mOLDLCInsuredDB.getName().equals(mLCInsuredSchema.getName()) 
				|| !mOLDLCInsuredDB.getInsuredNo().equals(mLCInsuredSchema.getInsuredNo())
				||!tOldocc.equals(tNewocc))
		||(mDealFlag.equals("2")&&tCValidateFlag)		
		)// 若在问题件修改时修改被保人信息则更新险种相关表的数据
		// 。
		// --
		// -
		// yeshu
		// ,
		// 20071224
		{
			logger.debug("ys@@@@@@@@@@@@@@the mOLDLCInsuredDB.getInsuredNo() is :"
							+ mOLDLCInsuredDB.getInsuredNo());
			// logger.debug(
			// "ys---------the mOLDLCInsuredDB.getInsuredNo() is :"
			// +mOLDLCInsuredDB.getInsuredNo());
			// start.---yeshu,20071224
			// LCGetSet tLCGetSet = new LCGetSet();
			// LCGetDB tLCGetDB = new LCGetDB();
			//
			// tLCGetDB.setContNo(mLCContSchema.getContNo());
			// tLCGetDB.setInsuredNo(mOLDLCInsuredDB.getInsuredNo());
			// tLCGetSet = tLCGetDB.query();
			// if (tLCGetDB.mErrors.needDealError())
			// {
			// CError tError = new CError();
			// tError.moduleName = "ContInsuredBL";
			// tError.functionName = "dealData";
			// tError.errorMessage = "查询该客户的领取项失败！";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			// for (int i = 1; i <= tLCPolSet.size(); i++)
			// {
			// tLCPolSet.get(i).setInsuredNo(mLCInsuredSchema.getInsuredNo());
			// tLCPolSet.get(i).setInsuredName(mLCInsuredSchema.getName());
			// tLCPolSet.get(i).setInsuredBirthday(mLCInsuredSchema.
			// getBirthday());
			// tLCPolSet.get(i).setInsuredSex(mLCInsuredSchema.getSex());
			//
			// }
			// for (int i = 1; i <= tLCGetSet.size(); i++)
			// {
			// tLCGetSet.get(i).setInsuredNo(mLCInsuredSchema.getInsuredNo());
			// }
			// //更新查出来的保单集合，为重算作准备
			// for (int i = 1; i <= preLCPolSet.size(); i++)
			// {
			// preLCPolSet.get(i).setInsuredNo(mLCInsuredSchema.getInsuredNo());
			// preLCPolSet.get(i).setInsuredName(mLCInsuredSchema.getName());
			// preLCPolSet.get(i).setInsuredBirthday(mLCInsuredSchema.
			// getBirthday());
			// preLCPolSet.get(i).setInsuredSex(mLCInsuredSchema.getSex());
			// }
			// if (tLCPolSet.size() > 0)
			// {
			// // map.put(tLCPolSet, "UPDATE");
			// }
			// if (tLCGetSet.size() > 0)
			// {
			// // map.put(tLCGetSet, "UPDATE");
			// }
			// 注释以上部分。取得旧被保人的险种相关信息，赋给最新被保人值，进行险种重算处理。
			// 更新前的险种相关数据。
			LCPolDB oldLCPolDB = new LCPolDB();
			LCDutyDB oldLCDutyDB = new LCDutyDB();
			LCPolSet oldLCPolSet = new LCPolSet();
			LCDutySet oldLCDutySet = new LCDutySet();
			// 放入map更新的内容。
			LCPolSet newLCPolSet = new LCPolSet();
			LCDutySet newLCDutySet = new LCDutySet();
			LCGetSet newLCGetSet = new LCGetSet();
			LCPremSet newLCPremSet = new LCPremSet();
			// 查询旧的lcpol
			oldLCPolDB.setContNo(mOLDLCInsuredDB.getContNo());
			oldLCPolDB.setInsuredNo(mOLDLCInsuredDB.getInsuredNo());
			oldLCPolSet = oldLCPolDB.query();
			if (oldLCPolSet != null && oldLCPolSet.size() >= 1) {
				// 查询每个险种下的所有责任。
				for (int i = 1; i <= oldLCPolSet.size(); i++) {
					LCPolBL recalLCPol = new LCPolBL();
					LCDutyBLSet recalLCDuty = new LCDutyBLSet();
					oldLCDutyDB = new LCDutyDB();
					oldLCDutySet = new LCDutySet();
					oldLCDutyDB.setPolNo(oldLCPolSet.get(i).getPolNo());
					oldLCDutySet = oldLCDutyDB.query();

					//tongmeng 2009-03-23 add
					//如果团单生效日被修改,需要重新修改lcgrp下的payenddate
					//在点击录入完毕后再处理吧....
					if(mDealFlag.equals("2")&&tCValidateFlag)
					{
						
					}
					
					if (oldLCDutySet != null && oldLCDutySet.size() >= 1) {// 为lcpol赋上新被保人的相关值
						// ，
						// 供重算使用
						recalLCPol.setSchema(oldLCPolSet.get(i));
						recalLCPol
								.setInsuredNo(mLCInsuredSchema.getInsuredNo());
						recalLCPol.setInsuredName(mLCInsuredSchema.getName());
						recalLCPol.setInsuredSex(mLCInsuredSchema.getSex());
						recalLCPol.setInsuredBirthday(mLCInsuredSchema
								.getBirthday());
						recalLCPol.setInsuredAppAge(PubFun.calInterval(
								mLCInsuredSchema.getBirthday(), recalLCPol
										.getCValiDate(), "Y"));
						recalLCPol.setOccupationType(mLCInsuredSchema
								.getOccupationType());
						recalLCPol.setPayEndDate("");
						recalLCPol.setEndDate("");
						recalLCPol.setPayYears("");
						recalLCPol.setYears("");
						//tongmeng 2009-03-23 modify
						//
						if(mLCContSchema.getCValiDate()!=null&&!mLCContSchema.getCValiDate().equals(""))
						{
							recalLCPol
							.setCValiDate(mLCContSchema.getCValiDate());
						}
						else
						{
						recalLCPol
								.setCValiDate(mLCContSchema.getPolApplyDate());
						}
						logger.debug("mLCContSchema.getPolApplyDate():"
								+ mLCContSchema.getPolApplyDate());
						for (int j = 1; j <= oldLCDutySet.size(); j++) {// 循环为险种下的每个责任赋新被保人的值
							// ，
							// 供重算使用
							// 。
							oldLCDutySet.get(j).setPayYears("");
							oldLCDutySet.get(j).setYears("");
							oldLCDutySet.get(j).setPayEndDate("");
							oldLCDutySet.get(j).setEndDate("");
							recalLCDuty.add(oldLCDutySet.get(j));
						}
					}
					//tongmeng 2009-06-18 modify
					//重算需要传入GetDutyKind
					String tSQL_Get = "  select max(getdutykind) from lcget "
						            + " where contno='"+"?contno?"+"' "
						            + " and polno = '"+"?polno?"+"' "
                                    + " and getdutykind is not null and getdutykind<>'0' ";
					SQLwithBindVariables sqlbv77 = new SQLwithBindVariables();
					sqlbv77.sql(tSQL_Get);
					sqlbv77.put("contno", recalLCPol.getContNo());
					sqlbv77.put("polno", recalLCPol.getPolNo());
					ExeSQL tExeSQL = new ExeSQL();
					String tValue = tExeSQL.getOneValue(sqlbv77);
					LCGetBL tLCGetBL = new LCGetBL();
					CalBL tCalBL = null;
					if(tValue!=null&&!tValue.equals(""))
					{
						tLCGetBL.setGetDutyKind(tValue);
						LCGetBLSet tLCGetBLSet = new LCGetBLSet();
						tLCGetBLSet.add(tLCGetBL);
						tCalBL = new CalBL(recalLCPol, recalLCDuty, tLCGetBLSet,
								"");
					}
					else
					{
						tCalBL = new CalBL(recalLCPol, recalLCDuty, "");
					}
					// 重算该险种。
					//CalBL tCalBL = new CalBL(recalLCPol, recalLCDuty, "");
					tCalBL.setOperator(mGlobalInput.Operator);
					if (tCalBL.calPol()) {
						/*
						 * 此处应增加重算后调用投保规则的处理。 当前万能险投保规则与投被保人无关，时间关系暂不处理。
						 */

						// 将重算处理后的险种信息数据放入待更新集合中。
						newLCPolSet.add(tCalBL.getLCPol());
						newLCDutySet.add(tCalBL.getLCDuty());
						newLCGetSet.add(tCalBL.getLCGet());
						newLCPremSet.add(tCalBL.getLCPrem());
					} else {
						CError tError = new CError();
						tError.moduleName = "ContInsuredBL";
						tError.functionName = "dealData";
						tError.errorMessage = "险种重算失败！";
						this.mErrors.addOneError(tError);
						return false;
					}
					// 更新该险种下受益人对应的被保人信息。
					LCBnfDB oldLCBnfDB = new LCBnfDB();
					LCBnfSet newLCBnfSet = new LCBnfSet();
					oldLCBnfDB.setPolNo(oldLCPolSet.get(i).getPolNo());
					newLCBnfSet = oldLCBnfDB.query();
					if (newLCBnfSet != null && newLCBnfSet.size() >= 1) {
						SQLwithBindVariables sqlbv78 = new SQLwithBindVariables();
						sqlbv78.sql("update lcbnf set insuredno='"
								+ "?insuredno?"
								+ "' where polno='"
								+ "?polno?" + "'");
						sqlbv78.put("insuredno", mLCInsuredSchema.getInsuredNo());
						sqlbv78.put("polno", oldLCPolSet.get(i).getPolNo());
						map.put(sqlbv78,
										"UPDATE");
						SQLwithBindVariables sqlbv79 = new SQLwithBindVariables();
						sqlbv79.sql("update lcbnf set name='"
								+ "?name?" + "',sex='"
								+ "?sex?" + "',idtype='"
								+ "?idtype?" + "',idno='"
								+ "?idno?"
								+ "' where polno='"
								+ "?polno?"
								+ "' and relationtoinsured='00'");
						sqlbv79.put("name", mLCInsuredSchema.getName());
						sqlbv79.put("sex", mLCInsuredSchema.getSex());
						sqlbv79.put("idtype", mLCInsuredSchema.getIDType());
						sqlbv79.put("idno", mLCInsuredSchema.getIDNo());
						sqlbv79.put("polno", oldLCPolSet.get(i).getPolNo());
						map.put(sqlbv79, "UPDATE");
					}
				}
				map.put(newLCPolSet, "DELETE&INSERT");
				map.put(newLCDutySet, "DELETE&INSERT");
				map.put(newLCGetSet, "DELETE&INSERT");
				map.put(newLCPremSet, "DELETE&INSERT");
				//tongmeng 2009-03-23 add
				//如果团单生效日被修改,需要重新修改lcgrp下的payenddate
				//在点击录入完毕以后再处理....
			
			}
			// end.---yeshu,20071224
		}

		return true;
	}
	/**
	 * 团险客户合并
	 * 合并原则：
	   1．姓名、性别、出生日期、证件号码、证件类型五项与系统中原有客户资料完全一致时，则进行自动合并。
	 * @param tLCPolSchema LCPolSchema
	 * @param tLCAppntIndSchema LCAppntIndSchema
	 * @param tLCInsuredSchema LCInsuredSchema
	 * @param tFlag int 客户号码合并类型：0 投保人客户号合并，1 被保险人客户号合并
	 * @return String 返回已经存在的客户号，如果没有投保，返回空字符串
	 */
	public String getGrpCustomerNo(LCInsuredSchema tLCInsuredSchema,LCAddressSchema tLCAddressSchema){
		String tCustomerNo="";
		try {
			String tName = "";  //客户姓名
			String tSex = "";  //客户性别
			String tBirthday = "";  //客户出生日期
			String tIDNoType = "";  //证件类型
			String tIDNo = "";      //证件号码
//    String tManagecom = tLCPolSchema.getManageCom();  //管理机构
//    String tBankAccNo = tLCPolSchema.getBankAccNo();  //银行帐号
//    String tAgentCode = tLCPolSchema.getAgentCode();  //代理人编码
			String tPhone = "";     //联系电话1
			String tPhone2 = "";    //联系电话2
			tName = tLCInsuredSchema.getName();
			tSex = tLCInsuredSchema.getSex();
			tBirthday = tLCInsuredSchema.getBirthday();
			tIDNoType = tLCInsuredSchema.getIDType();
			tIDNo = tLCInsuredSchema.getIDNo();
			tPhone = tLCAddressSchema.getMobile();
			tPhone2 = tLCAddressSchema.getPhone();
			if(tPhone==null||tPhone.equals(""))
			{
				tPhone = " ";
			}
			if(tPhone2==null||tPhone2.equals(""))
			{
				tPhone2 = " ";
			}
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sqlbv80 = new SQLwithBindVariables();
			sqlbv80.sql(" select to_char(to_date('"+"?birthday?"+"','yyyy-mm-dd'),'yyyy-mm-dd') from dual ");
			sqlbv80.put("birthday", tBirthday);
			tBirthday = tExeSQL.getOneValue(sqlbv80);
			
			logger.debug("*********getCustomerNo***********");
			logger.debug("tName: "+StrTool.GBKToUnicode(tName));
			logger.debug("tSex: "+tSex);
			logger.debug("tBirthday: "+tBirthday);
			logger.debug("tIDNoType: "+tIDNoType);
			logger.debug("tIDNo: "+tIDNo);
			logger.debug("tPhone: "+tPhone);
			logger.debug("tPhone2: "+tPhone2);
			
			LDPersonSet tLDPersonSet = new LDPersonSet();
		    LDPersonDB tLDPersonDB = new LDPersonDB();
		    
		    if(StrTool.cTrim(tCustomerNo).equals(""))
		    {
		    	tLDPersonDB = new LDPersonDB();	
		    	if(!tIDNoType.equals("0")){
		    		if("".equals(tName)&&"".equals(tSex)&&"".equals(tBirthday)&&"".equals(tIDNoType)&&"".equals(tIDNo)){
		    			logger.debug("用于校验是否同一客户的五项基本信息全为空，传入的被保人基本信息不全，请检查！");
		    			CError.buildErr(this, "传入的被保人基本信息为空(姓名、性别、出生日期、证件类型、证件号码)！");
		    			return "Error";
		    		}
		    		tLDPersonDB.setName(tName);
		    		tLDPersonDB.setSex(tSex);
		    		tLDPersonDB.setBirthday(tBirthday);
		    		tLDPersonDB.setIDType(tIDNoType);
		    		tLDPersonDB.setIDNo(tIDNo);
		    		tLDPersonSet=tLDPersonDB.query();
		    		if(tLDPersonSet.size()>0){
		    			tCustomerNo =tLDPersonSet.get(1).getCustomerNo();
		    		}
		    	 }else{
		    		 if("".equals(tName)&&"".equals(tSex)&&"".equals(tBirthday)&&"".equals(tIDNoType)){
			    			logger.debug("用于校验是否同一客户的五项基本信息全为空，传入的被保人基本信息不全，请检查！");
			    			CError.buildErr(this, "传入的被保人基本信息为空(姓名、性别、出生日期、证件类型)！");
			    			return "Error";
			    		}
		    		tLDPersonDB.setName(tName);
		    		tLDPersonDB.setSex(tSex);
		    		tLDPersonDB.setBirthday(tBirthday);
		    		tLDPersonDB.setIDType(tIDNoType);
		    		//tLDPersonDB.setBirthday(tIDNo);
//		         String tpersonSql="select * from ldperson where name='"+StrTool.GBKToUnicode(tName)+"' "
//									+" and sex='"+tSex+"' "
//									+" and birthday=to_date('"+tBirthday+"','yyyy-mm-dd')";
		    		tLDPersonSet = tLDPersonDB.query();
		    		logger.debug("查询除身份证号外其他四项都一样的数据条数： "+tLDPersonSet.size());
		    		for(int i=1;i<=tLDPersonSet.size();i++){
		    			String tCompareCustomerNo =tLDPersonSet.get(i).getCustomerNo();
		    			String tCompareIDNo = tLDPersonSet.get(i).getIDNo();
		    			if(StrTool.cTrim(tCompareIDNo).length()==15){
		    				if(StrTool.cTrim(tCompareIDNo).length()==StrTool.cTrim(tIDNo).length()){
		    					//新增人的IDNo是15位
		    					if(tCompareIDNo.equals(tIDNo)){
		    						return tCompareCustomerNo;
		    					}
		    				}else{
		    					//新增人的IDNo是18位 需要转换成15位后再做比较
		    					if(tCompareIDNo.equals(get15IDNo(tIDNo))){
		    						return tCompareCustomerNo;
		    					}
		    					
		    				}
		    			}else {
		    				if(StrTool.cTrim(tCompareIDNo).length()==StrTool.cTrim(tIDNo).length()){
		    					//新增人的IDNo是18位
		    					if(tCompareIDNo.equals(tIDNo)){
		    						return tCompareCustomerNo;
		    					}
		    				}else{
		    					//新增人的IDNo是15位 需要转换成18位后再做比较
		    					if(tCompareIDNo.equals(get18IDNo(tIDNo,tBirthday))){
		    						return tCompareCustomerNo;
		    					}
		    				}
		    				
		    			}
		    		}
		    	}
		    }
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tCustomerNo;
	}
	/**
	 * 客户合并
	 * 合并原则：
	   1．姓名、性别、出生日期、证件号码四项与系统中原有客户资料完全一致时，则进行自动合并。
	   2．姓名、性别、出生日期，三项与系统中原有客户资料完全一致，且证件号码因15位或18位与原有客户资料不匹配时，系统自动将15位的号码转为18位后（但只作校验用，系统仍记录原有的15位号码），进行校验，如四项与系统中原有客户资料一致，则进行自动合并。
	   3．姓名、性别、出生日期，三项与系统中原有客户资料完全一致，且机构代码、银行帐号、业务员代码、电话中有任意一项与系统中原有客户资料一致时，进行自动合并。
	   4、姓名、性别、出生日期，三项与系统中原有客户资料完全一致，且有银行账号信息，当录入的“帐号”数字内容完全一致时进行自动合并。
	   5、姓名、性别、出生日期”三项与系统中原有客户资料完全一致，且客户年龄小于18周岁，当“投保人姓名”或“投保人证件号码的数字部分”与系统中原有客户资料完全一致时，对该客户进行自动合并。
	 * @param tLCPolSchema LCPolSchema
	 * @param tLCAppntIndSchema LCAppntIndSchema
	 * @param tLCInsuredSchema LCInsuredSchema
	 * @param tFlag int 客户号码合并类型：0 投保人客户号合并，1 被保险人客户号合并
	 * @return String 返回已经存在的客户号，如果没有投保，返回空字符串
	 */
	public String getCustomerNo(LCInsuredSchema tLCInsuredSchema,LCAddressSchema tLCAddressSchema)
	{
	  String tCustomerNo = "";
	  try
	  {
	    String tName = "";  //客户姓名
	    String tSex = "";  //客户性别
	    String tBirthday = "";  //客户出生日期
	    String tIDNoType = "";  //证件类型
	    String tIDNo = "";      //证件号码
	//    String tManagecom = tLCPolSchema.getManageCom();  //管理机构
	//    String tBankAccNo = tLCPolSchema.getBankAccNo();  //银行帐号
	//    String tAgentCode = tLCPolSchema.getAgentCode();  //代理人编码
	    String tPhone = "";     //联系电话1
	    String tPhone2 = "";    //联系电话2
	    String tBankAccNo = ""; //银行账号
	    String tAppntName = ""; //投保人姓名
	    String tAppntIDNo = "";   //投保人证件号码
	    String tAppntBirthday = "";//投保人出生日期
	    LCAppntDB tLCAppntDB = new LCAppntDB();
	    tLCAppntDB.setContNo(tLCInsuredSchema.getContNo());
	    if(!tLCAppntDB.getInfo()){
	    	if(!"card".equals(mark)){
	    		CError.buildErr(this, "查询印刷号为"+tLCInsuredSchema.getContNo()+"的投保人失败！");
//	    		return "";
	    	} else {
	    		logger.debug("卡单，无法查询到投保人信息 prtno: "+ ContNo);
	    	}
	    }else {
	    	tAppntName = tLCAppntDB.getAppntName();
	    	tAppntIDNo = tLCAppntDB.getIDNo();
	    	tAppntBirthday = tLCAppntDB.getAppntBirthday();
	    }
	    tName = tLCInsuredSchema.getName();
	    tSex = tLCInsuredSchema.getSex();
	    tBirthday = tLCInsuredSchema.getBirthday();
	    tIDNoType = tLCInsuredSchema.getIDType();
	    tIDNo = tLCInsuredSchema.getIDNo();
	    tPhone = tLCAddressSchema.getMobile();
	    tPhone2 = tLCAddressSchema.getPhone();
	    tBankAccNo = tLCInsuredSchema.getBankAccNo();
	    if(tPhone==null||tPhone.equals(""))
	    {
	    	tPhone = " ";
	    }
	    if(tPhone2==null||tPhone2.equals(""))
	    {
	    	tPhone2 = " ";
	    }
	    ExeSQL tExeSQL = new ExeSQL();
	    SQLwithBindVariables sqlbv81 = new SQLwithBindVariables();
		sqlbv81.sql(" select to_char(to_date('"+"?birthday?"+"','yyyy-mm-dd'),'yyyy-mm-dd') from dual ");
		sqlbv81.put("birthday", tBirthday);
	    tBirthday = tExeSQL.getOneValue(sqlbv81);
		    
	    
	    logger.debug("*********getCustomerNo***********");
	    logger.debug("tName: "+StrTool.GBKToUnicode(tName));
	    logger.debug("tSex: "+tSex);
	    logger.debug("tBirthday: "+tBirthday);
	    logger.debug("tIDNoType: "+tIDNoType);
	    logger.debug("tIDNo: "+tIDNo);
	    logger.debug("tPhone: "+tPhone);
	    logger.debug("tPhone2: "+tPhone2);
	
	//    //1．姓名、性别、出生日期、证件号码四项与系统中原有客户资料完全一致时，则进行自动合并。
	//    logger.debug("******Start Step 1***********");
	//    LDPersonDB tLDPersonDB = new LDPersonDB();
	//    tLDPersonDB.setName(tName);
	//    tLDPersonDB.setSex(tSex);
	//    tLDPersonDB.setBirthday(tBirthday);
	//    tLDPersonDB.setIDNo(tIDNo);
	
	//    LDPersonSet tLDPersonSet = tLDPersonDB.query();
	//    logger.debug("***tLDPersonSet.getName Step 1: " + tLDPersonSet.encode());
	//    if (tLDPersonSet != null && tLDPersonSet.size() > 0)
	//    {
	//      tCustomerNo = tLDPersonSet.get(1).getCustomerNo();
	//    }
	//    logger.debug("******End Step 1 tCustomerNo : "+tCustomerNo);
	    
	    LDPersonSet tLDPersonSet = new LDPersonSet();
	    LDPersonDB tLDPersonDB = new LDPersonDB();
	    if(StrTool.cTrim(tCustomerNo).equals(""))
	    {
	    	 tLDPersonDB = new LDPersonDB();	
//	    	 tLDPersonDB.setName(tName);
//	         tLDPersonDB.setSex(tSex);
//	         tLDPersonDB.setBirthday(tBirthday);
	         String tpersonSql="select * from ldperson a where name='"+"?name?"+"' "
								+" and sex='"+"?sex?"+"' "
								+" and birthday=to_date('"+"?birthday?"+"','yyyy-mm-dd')"
	         					+" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
	         		            +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) ";
	         SQLwithBindVariables sqlbv82 = new SQLwithBindVariables();
	         sqlbv82.sql(tpersonSql);
	         sqlbv82.put("name", StrTool.GBKToUnicode(tName));
	         sqlbv82.put("sex", tSex);
	         sqlbv82.put("birthday", tBirthday);
	         tLDPersonSet = tLDPersonDB.executeQuery(sqlbv82);
	         logger.debug("判断是否有姓名性别出生日期相同的客户sql: "+tpersonSql);
//	         tLDPersonSet = tLDPersonDB.query();
	         if (tLDPersonSet != null && tLDPersonSet.size() > 0)
	         {
	        	 //首先判断证件号码长度是否18位或者15位
	        	 if(StrTool.cTrim(tIDNo).length()==18||StrTool.cTrim(tIDNo).length()==15)
	        	 {
	        		  //如果是18位或者15位不校验证件类型,将18和15位的号码按照身份证转换后校验
	        		  logger.debug("***Step 1 start 证件号码为15或者18位: " + tIDNo);
	        		  LDPersonSet tempLDPersonSet = new LDPersonSet();
	            	  if(StrTool.cTrim(tIDNo).length()==18)
	       	          {
	       		          String customer_sql="select * from ldperson a where name='"+"?name?"+"' and sex='"+"?sex?"+"'"
	       		          +" and birthday='"+"?birthdy?"+"' and (lower(idno)=lower('"+"?idno1?"+"') or lower(idno)=lower('"+"?idno2?"+"') )"
	       		          +" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
	       		          +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) ";
	       		       SQLwithBindVariables sqlbv83 = new SQLwithBindVariables();
	       		       sqlbv83.sql(customer_sql);
	       		       sqlbv83.put("name", StrTool.GBKToUnicode(tName));
	       		       sqlbv83.put("sex", tSex);
	       		       sqlbv83.put("birthday", tBirthday);
	       		       sqlbv83.put("idno1", get15IDNo(tIDNo));
	       		       sqlbv83.put("idno2", tIDNo);
	       		          //+" and idtype='0'";
	       		          logger.debug("***customer_sql Step 2 : " + customer_sql);
	       		          logger.debug("customer_sql1111111111111:"+customer_sql);
	       		          tempLDPersonSet = tLDPersonDB.executeQuery(sqlbv83);
	       	          }
	       	          else if(StrTool.cTrim(tIDNo).length()==15)
	       	          {
	       		          String customer_sql="select * from ldperson a where name='"+"?name?"+"' and sex='"+"?sex?"+"'"
	       		          +" and birthday='"+"?birthday?"+"' and (lower(idno)=lower('"+"?idno1?"+"') or lower(idno)=lower('"+"idno2"+"') )"
	       		          +" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
	       		          +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) ";
	       		       SQLwithBindVariables sqlbv84 = new SQLwithBindVariables();
	       		       sqlbv84.sql(customer_sql);
	       		       sqlbv84.put("name", StrTool.GBKToUnicode(tName));
	       		       sqlbv84.put("sex", tSex);
	       		       sqlbv84.put("birthday", tBirthday);
	       		       sqlbv84.put("idno1", get18IDNo(tIDNo,tBirthday));
	       		       sqlbv84.put("idno2", tIDNo);
	       		          //+" and idtype='0'";
	       		          logger.debug("customer_sql2222222222222:"+customer_sql);
	       		          tempLDPersonSet = tLDPersonDB.executeQuery(sqlbv84);
	       	          }
	       	          
	       	          if (tempLDPersonSet != null && tempLDPersonSet.size() > 0)
	       	          {
	       	            tCustomerNo = tempLDPersonSet.get(1).getCustomerNo();
	       	          }
	       	          logger.debug("***Step 1 end 证件号码为15或者18位: " + tCustomerNo);
	        	 }
	        	 else
	        	 {
	        		 LDPersonSet tempLDPersonSet2 = new LDPersonSet();
	            		for(int i=1;i<=tLDPersonSet.size();i++)
	  		            {
	  			           	if("2".equals(tIDNoType)&&tIDNoType.equals(StrTool.cTrim(tLDPersonSet.get(i).getIDType())))
	  			           	{
	  			           	    logger.debug("***Step 2 statr 证件类型同为军官证或者护照: " + tIDNoType);
	  			           		if(tIDNo.replaceAll("[^0-9]", "").equals(tLDPersonSet.get(i).getIDNo().replaceAll("[^0-9]", "")))
	  			           		{
	  			           			tCustomerNo = tLDPersonSet.get(i).getCustomerNo();
	  			           		    logger.debug("***Step 2 end succ 证件类型同为军官证: " + tCustomerNo);
	  			           			break;
	  			           		}
	  			           	    logger.debug("***Step 2 end 证件类型同为军官证: " + tCustomerNo);
	  			           	}
	  			          else if((!("2".equals(tIDNoType)||"8".equals(tIDNoType)||"9".equals(tIDNoType)))&&tIDNoType!=null&&tIDNoType.equals(StrTool.cTrim(tLDPersonSet.get(i).getIDType())))
	  			           	{
	  			           		logger.debug("***Step 3 statr 证件类型与证件号码是否都相同: " + tCustomerNo);
	  			           		String customer_sql="select * from ldperson a where name='"+"?name?"+"' and sex='"+"?sex?"+"'"
	  			           		+" and birthday='"+"?birthday?"+"' and (lower(idno)=lower('"+"?idno?"+"') and lower(idtype)=lower('"+"?idtype?"+"') )"
	         					+" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
	         		            +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) ";
	  			           		SQLwithBindVariables sqlbv85 = new SQLwithBindVariables();
	  			           		sqlbv85.sql(customer_sql);
	  			           		sqlbv85.put("name", StrTool.GBKToUnicode(tName));
	  			           		sqlbv85.put("sex", tSex);
	  			           		sqlbv85.put("birthday", tBirthday);
	  			           		sqlbv85.put("idno", tIDNo);
	  			           		sqlbv85.put("idtype", tIDNoType);
	  			           		logger.debug("customer_sql2222222222222:"+customer_sql);
	  			           		tempLDPersonSet2 = tLDPersonDB.executeQuery(sqlbv85);
	  			           		
	  			           		if (tempLDPersonSet2 != null && tempLDPersonSet2.size() > 0)
	  			           		{
	  			           			tCustomerNo = tempLDPersonSet2.get(1).getCustomerNo();
	  			           			logger.debug("***Step 3 end succ 证件号码与证件类型都相同: " + tCustomerNo);
	  			           		}
	  			           		
//		  			           	if(tIDNo.toUpperCase().equals(tLDPersonSet.get(i).getIDNo().toUpperCase())){
//	  			           			tCustomerNo = tLDPersonSet.get(i).getCustomerNo();
//	  			           			break;
//	  			           		}
	  			           	}
//	  			           	else
//	  			           	{
	  			           	logger.debug("***Step 4 statr 是否有电话一致: " + tCustomerNo);
  			           		String customer_sql="select * from ldperson a where name='"+"?name?"
  			           			+"' and sex='"+"?sex?"
  			           			+"' and birthday='"+"?birthday?"
  			           			+"' and '"+"?tPhone2?"
  			           			+"' in (select replace(phone, '无', '') from lcaddress where customerno=a.customerno)"
  			           			+ " and '"+"?tPhone2?"+"' is not null and '"+"?tPhone2?"+"'<>' ' "
	         					+" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
	         		            +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) "
  			           			+" union "
  			           			+" select * from ldperson a where name='"+"?name?"
  			           			+"' and sex='"+"?sex?"
  			           			+"' and birthday='"+"?birthday?"
  			           			+"' and '"+"?tPhone2?"
  			           			+"' in (select replace(mobile, '无', '') from lcaddress where customerno=a.customerno)"
  			           			+ " and '"+"?tPhone2?"+"' is not null and '"+"?tPhone2?"+"'<>' ' "
	         					+" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
	         		            +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) "
  			           			+" union "
  			           			+" select * from ldperson a where name='"+"?name?"
			           			+"' and sex='"+"?sex?"
			           			+"' and birthday='"+"?birthday?"
			           			+"' and '"+"?tPhone?"
			           			+"' in (select replace(phone, '无', '') from lcaddress where customerno=a.customerno)"
			           			+ " and '"+"?tPhone?"+"' is not null and '"+"?tPhone?"+"'<>' ' "
			        			+" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
	         		            +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) "
			           			+" union "
			           			+" select * from ldperson a where name='"+"?name?"
			           			+"' and sex='"+"?sex?"
			           			+"' and birthday='"+"?birthday?"
			           			+"' and '"+"?tPhone?"
			           			+"' in (select replace(mobile, '无', '') from lcaddress where customerno=a.customerno)"
			           			+ " and '"+"?tPhone?"+"' is not null and '"+"?tPhone?"+"'<>' ' "
			        			+" and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
	         		            +" union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) "
			           			;
  			           		SQLwithBindVariables sqlbv86 = new SQLwithBindVariables();
			           		sqlbv86.sql(customer_sql);
			           		sqlbv86.put("name", StrTool.GBKToUnicode(tName));
			           		sqlbv86.put("sex", tSex);
			           		sqlbv86.put("birthday", tBirthday);
			           		sqlbv86.put("tPhone", tPhone);
			           		sqlbv86.put("tPhone2", tPhone2);
  			           		tempLDPersonSet2 = tLDPersonDB.executeQuery(sqlbv86);
		  			          logger.debug("判断是否有电话一致sql："+customer_sql);
	  			           		if (tempLDPersonSet2 != null && tempLDPersonSet2.size() > 0)
	  			           		{
	  			           			tCustomerNo = tempLDPersonSet2.get(1).getCustomerNo();
	  			           			logger.debug("***Step 4 end succ 是否有电话一致: " + tCustomerNo);
	  			           			break;
	  			           		}
	  			           		//2010-4-13 增加两条自动合并规则
	  			           	String tBankSql = "select * from ldperson a  where name = '"+"?name?"+"'"
  			           		+ " and sex = '"+"?sex?"+"' and birthday = '"+"?birthday?"+"'"
  			           		+ " and ('"+"?tBankAccNo?"+"' in (select bankaccno from lcappnt "
  			           		+ " where appntno = a.customerno )"
  			           		+ " or '"+"?tBankAccNo?"+"' in (select bankaccno from lcinsured "
  			           		+ " where insuredno = a.customerno))"
  			           		+ " and '"+"?tBankAccNo?"+"' is not null"
  			           		+ " and '"+"?tBankAccNo?"+"' <> ' '"
  			           		+ " and exists (select 1 from lcappnt"
  			           		+ " where grpcontno = '00000000000000000000'"
  			           		+ " and appntno = a.customerno union select 1"
  			           		+ " from lcinsured where grpcontno = '00000000000000000000'"
  			           		+ " and insuredno = a.customerno)";
	  			          SQLwithBindVariables sqlbv87 = new SQLwithBindVariables();
			           		sqlbv87.sql(tBankSql);
			           		sqlbv87.put("name", StrTool.GBKToUnicode(tName));
			           		sqlbv87.put("sex", tSex);
			           		sqlbv87.put("birthday", tBirthday);
			           		sqlbv87.put("tBankAccNo", tBankAccNo);
  			          tempLDPersonSet2 = tLDPersonDB.executeQuery(sqlbv87);
  			          logger.debug("判断是否有银行账号一致sql："+customer_sql);
  			          if(tempLDPersonSet2 != null && tempLDPersonSet2.size() > 0){
  			        	tCustomerNo = tempLDPersonSet2.get(1).getCustomerNo();
		           			logger.debug("***Step 5 end succ 是否有银行账号一致: " + tCustomerNo);
		           			break;
  			          }
  			          //
  			          if(PubFun.calInterval(tBirthday,theCurrentDate,"Y")<18){
  			        	  String tAppntNameSql = "select * from ldperson where customerno in ("
  			        		  + " select insuredno from lcinsured a where name = '"+"?name?"+"'"
  			        		  + " and sex = '"+"?sex?"+"' and birthday = '"+"?birthday?"+"'"
  			        		  + " and '"+"?tAppntName?"+"' in (select appntname from lcappnt where prtno = a.prtno))";
  			        	SQLwithBindVariables sqlbv88 = new SQLwithBindVariables();
		           		sqlbv88.sql(tAppntNameSql);
		           		sqlbv88.put("name", StrTool.GBKToUnicode(tName));
		           		sqlbv88.put("sex", tSex);
		           		sqlbv88.put("birthday", tBirthday);
		           		sqlbv88.put("tAppntName", StrTool.GBKToUnicode(tAppntName));
  			        	  tempLDPersonSet2 = tLDPersonDB.executeQuery(sqlbv88);
  	  			          logger.debug("被保人小于18岁,投保人姓名是否一致sql："+tAppntNameSql);
  	  			          if(tempLDPersonSet2 != null && tempLDPersonSet2.size() > 0){
  	  			        	tCustomerNo = tempLDPersonSet2.get(1).getCustomerNo();
  			           			logger.debug("***Step 6 end succ 投保人姓名是否一致: " + tCustomerNo);
  			           			break;
  	  			          }
  	  			          if(StrTool.cTrim(tAppntIDNo).length()==18||StrTool.cTrim(tAppntIDNo).length()==15){
  	  			        	  if(StrTool.cTrim(tAppntIDNo).length()==18){
	  	  			        	String tAppntIDSql = "select * from ldperson a where customerno in ("
	  			        		+ " select insuredno from lcinsured b where name = '"+"?name?"+"'"
	  			        		+ " and sex = '"+"?sex?"+"' and birthday = '"+"?birthday?"+"'"
	  			        		+ " and exists  (select 1 from lcappnt where prtno=b.prtno"
	  			        		+ " and (lower(idno)=lower('"+"?idno1?"+"') or lower(idno)=lower('"+"?idno2?"+"') )))" 
	  			        		+ " and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
	         		            + " union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) ";
	  	  			        SQLwithBindVariables sqlbv89 = new SQLwithBindVariables();
			           		sqlbv89.sql(tAppntIDSql);
			           		sqlbv89.put("name", StrTool.GBKToUnicode(tName));
			           		sqlbv89.put("sex", tSex);
			           		sqlbv89.put("birthday", tBirthday);
			           		sqlbv89.put("idno1", get15IDNo(tAppntIDNo));
			           		sqlbv89.put("idno2", tAppntIDNo);
	  	  			        	tempLDPersonSet2 = tLDPersonDB.executeQuery(sqlbv89);
	  	  			        	logger.debug("被保人小于18岁,投保人证件号码一致sql："+tAppntIDSql);
	  	  			        	if(tempLDPersonSet2 != null && tempLDPersonSet2.size() > 0){
	  	  			        		tCustomerNo = tempLDPersonSet2.get(1).getCustomerNo();
	  			           			logger.debug("***Step 7 end succ 投保人证件号码是否一致: " + tCustomerNo);
	  			           			break;
	  	  			        	}
  	  			        	  }else if(StrTool.cTrim(tIDNo).length()==15){
  	  			        		String tAppntIDSql = "select * from ldperson a where customerno in ("
  		  			        		+ " select insuredno from lcinsured b where name = '"+"?name?"+"'"
  		  			        		+ " and sex = '"+"?sex?"+"' and birthday = '"+"?birthday?"+"'"
  		  			        		+ " and exists  (select 1 from lcappnt where prtno=b.prtno  "
  		  			        		+ " and (lower(idno)=lower('"+"?idno1?"+"') or lower(idno)=lower('"+"?idno2?"+"') )))" 
  		  			        		+ " and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.customerno "
  		         		            + " union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.customerno) ";
	  	  			        	SQLwithBindVariables sqlbv90 = new SQLwithBindVariables();
				           		sqlbv90.sql(tAppntIDSql);
				           		sqlbv90.put("name", StrTool.GBKToUnicode(tName));
				           		sqlbv90.put("sex", tSex);
				           		sqlbv90.put("birthday", tBirthday);
				           		sqlbv90.put("idno1", get18IDNo(tAppntIDNo,tAppntBirthday));
				           		sqlbv90.put("idno2", tAppntIDNo);
  	  			        		tempLDPersonSet2 = tLDPersonDB.executeQuery(sqlbv90);
  		  	  			        	logger.debug("被保人小于18岁,投保人证件号码一致sql："+tAppntIDSql);
  		  	  			        	if(tempLDPersonSet2 != null && tempLDPersonSet2.size() > 0){
  		  	  			        		tCustomerNo = tempLDPersonSet2.get(1).getCustomerNo();
  		  			           			logger.debug("***Step 8 end succ 投保人证件号码是否一致: " + tCustomerNo);
  		  			           			break;
  		  	  			        	}
  	  			        	  }
  	  			          } else {
  	  			        	  LCAppntSet tLCAppntSet = new LCAppntSet();
  	  			        	  LCAppntDB tempLCAppntDB = new LCAppntDB();
  	  			        	  String tAppntIDSql = "select * from lcappnt where prtno in ("
		  			        		+ " select prtno from lcinsured a where name = '"+"?name?"+"'"
		  			        		+ " and sex = '"+"?sex?"+"' and birthday = '"+"?birthday?"+"'"
		  			        		+ " and  exists (select 1 from lcappnt where grpcontno='00000000000000000000' and appntno=a.insuredno "
		         		            + " union select 1 from lcinsured where grpcontno='00000000000000000000' and insuredno=a.insuredno)) ";
  	  			        	SQLwithBindVariables sqlbv91 = new SQLwithBindVariables();
			           		sqlbv91.sql(tAppntIDSql);
			           		sqlbv91.put("name", StrTool.GBKToUnicode(tName));
			           		sqlbv91.put("sex", tSex);
			           		sqlbv91.put("birthday", tBirthday);
  	  			        	  tLCAppntSet = tempLCAppntDB.executeQuery(sqlbv91);
  	  			        	  for(int k=1;k<=tLCAppntSet.size();k++){
  	  			        		if((tAppntIDNo.replaceAll("[^0-9]", "")).equals(tLCAppntSet.get(k).getIDNo().replaceAll("[^0-9]", ""))){
  	  			        			logger.debug("***Step 9 end succ 投保人证件号码数字部分一致: ");
  	  			        			String tInsuredNoSql = "select insuredno from lcinsured where name='"+"?name?"+"'"
  	  			        				+ " and sex='"+"?sex?"+"' and birthday='"+"?birthday?"+"'"
  	  			        				+ " and prtno='"+"?prtno?"+"'";
	  	  			        		SQLwithBindVariables sqlbv92 = new SQLwithBindVariables();
	  				           		sqlbv92.sql(tInsuredNoSql);
	  				           		sqlbv92.put("name", StrTool.GBKToUnicode(tName));
	  				           		sqlbv92.put("sex", tSex);
	  				           		sqlbv92.put("birthday", tBirthday);
	  				           		sqlbv92.put("prtno", tLCAppntSet.get(k).getPrtNo());
  	  			        			tCustomerNo = tExeSQL.getOneValue(sqlbv92);
  	  			        			break;
  	  			        		}
  	  			        	  }
  	  			          }
  			          }
	  		            }
	        	 }
	         }
	         
	    }
	
	  }
	  catch(Exception ex)
	  {
	    ex.printStackTrace();
	    logger.debug("客户合并时发生异常××××");
	  }
	  logger.debug("******End  tCustomerNo : "+tCustomerNo);
	  return tCustomerNo;
	
	}
	
	public String get15IDNo(String IDNo) {
		String str = "";
		str += IDNo.substring(0, 6);
		str += IDNo.substring(8, 17);
		return str;
	}
	
	public static String get18IDNo(String IDNo, String Birthday) {
		if (IDNo.length() == 18) {
			if (IDNo.endsWith("x"))
				IDNo = IDNo.substring(0, 17) + "X";
			return IDNo;
		}
		String str = "";
		str += IDNo.substring(0, 6);
		if (Birthday.length() == 10) {
			str += Birthday.substring(0, 2);
		} else
			str += "19";
		str += IDNo.substring(6, 15);
		int n = 0;
		int[] weight = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
				8, 4, 2 };
		for (int i = 0; i < 17; i++) {
			n += Integer.parseInt(str.substring(i, i + 1)) * weight[i];
		}
		n %= 11;
		if (n == 0)
			str += "1";
		else if (n == 1)
			str += "0";
		else if (n == 2)
			str += "X";
		else if (n == 3)
			str += "9";
		else if (n == 4)
			str += "8";
		else if (n == 5)
			str += "7";
		else if (n == 6)
			str += "6";
		else if (n == 7)
			str += "5";
		else if (n == 8)
			str += "4";
		else if (n == 9)
			str += "3";
		else if (n == 10)
			str += "2";
		return str;
	}
	
	public boolean deleteRisk(){
		//当进行银代无扫描录入时，传进来的保单险种信息为null
		if(this.mmLCPolSet == null){
			return true;
		}
		int count = this.mmLCPolSet.size();
		for (int i = 0; i < count; i++) {
			TransferData tTransferData = new TransferData();
			LCPolSchema tLCPolSchema = mmLCPolSet.get(i+1);
			tTransferData.setNameAndValue("InsuredNo", tLCPolSchema.getInsuredNo());
			tTransferData.setNameAndValue("PolNo", tLCPolSchema.getPolNo());
			VData tVData = new VData();
			tVData.add(tTransferData);
			if (!delrisk(tVData)) {
				// @@错误处理
				CError.buildErr(this,"数据处理失败ContInsuredBL-->deleteRisk!");
				return false;
			}
		}
		return true;
	}
}
