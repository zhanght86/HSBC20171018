package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.util.HashMap;

import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredRelatedSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LLAccountSchema;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class InsuredRelaAddBL {
private static Logger logger = Logger.getLogger(InsuredRelaAddBL.class);

	private String ContNo;
	private String ContType;

	/** 业务处理相关变量 */
	private String manageCom; // add by yaory
	private MMap map = new MMap();

	private String mark = "";

	private String mPolNo = "";
	private String mMainInsuredNo = "";
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();
	// balance
	private LCContSchema mLCContSchema = new LCContSchema();
	private LLAccountSchema mLLAccountSchema = new LLAccountSchema();
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
	private LCInsuredRelatedSchema mLCInsuredRelatedSchema = new LCInsuredRelatedSchema();
	/** 保单 */
	private LDPersonSchema mLDPersonSchema = new LDPersonSchema(); // 从界面传入的客户数据
	private HashMap mMainPolMap = new HashMap();
	/** 数据操作字符串 */
	private String mOperate;
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();
	private Reflections ref = new Reflections();
	private VData rResult = new VData(); // add by yaory

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	private String tInsuredAppAge;

	private String tInsuredPeoples;

	private LCAddressSchema tLCAddressSchema;
	private LDPersonSchema tLDPersonSchema; // tLDPersonSet包含需要新建的客户

	private String tPolTypeFlag;
	private String tSequenceNo; // 客户顺序号
	private String mRiskSortValue = "";// 是否校验连带被保险人人数标志

	// private LCInsuredSet mLCInsuredSet=new LCInsuredSet();
	public InsuredRelaAddBL() {
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
			this.mLCInsuredRelatedSchema
					.setSchema((LCInsuredRelatedSchema) cInputData
							.getObjectByObjectName("LCInsuredRelatedSchema", 0));
			// this.mLLAccountSchema.setSchema((LLAccountSchema) cInputData
			// .getObjectByObjectName("LLAccountSchema", 0));

			this.mTransferData = (TransferData) cInputData
					.getObjectByObjectName("TransferData", 0);

			if (mTransferData == null) {
				CError.buildErr(this, "未得到传送数据!");
				return false;

			}

			manageCom = mGlobalInput.ManageCom;

			logger.debug("已经给值====" + manageCom);

			this.mLDPersonSchema.setSchema((LDPersonSchema) cInputData
					.getObjectByObjectName("LDPersonSchema", 0));

			tInsuredAppAge = (String) mTransferData
					.getValueByName("InsuredAppAge");
			mMainInsuredNo = (String) mTransferData
					.getValueByName("MainInsuredNo");
			ContNo = (String) mTransferData.getValueByName("ContNo");
			mPolNo = (String) mTransferData.getValueByName("PolNo");
			mRiskSortValue = (String) mTransferData.getValueByName("RiskSortValue");
			logger.debug("mRiskSortValue:"+mRiskSortValue);

			ref.transFields(mLCInsuredSchema, mLDPersonSchema); // 客户表
			mLCInsuredSchema.setInsuredNo(mLDPersonSchema.getCustomerNo());
			mLCContSchema.setContType(ContType);
			this.mLCContSchema.setSchema((LCContSchema) cInputData
					.getObjectByObjectName("LCContSchema", 0));
			ContNo = mLCContSchema.getContNo();
			// 当是个人时，由界面上取得合同号，当是团体下个人的时候，需要在保存个人合同下第一个的时候，生成LCCont

		} catch (Exception ex) {
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
	
	/**
	 * 校验传入的数据
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		
		// 校验连带被保险人数量
		if(mRiskSortValue==null||mRiskSortValue.equals(""))
		{
			CError.buildErr(this, "传入校验连带被保险人人数标志失败!!");
			return false;
		}
		else
		{
			/**
			 *  RiskSortValue(险种分类值):
          		0 连带被保险人补人不需要交验人数  险种241803;
          		1 连带被保险人补人需要交验人数 （险种241801，241805）使用lcduty.StandbyFlag2校验连带被保人人数;
          		2 需要判断承保时录入的是否有连带被保险人 （险种241802，241806）使用lcduty.StandbyFlag1判断是否有连带被保人 0-无连带被保人,1-有连带被保人
          		  等于2的承保时选择没有连带被保险人的记录在界面初始化时就不会查询出来,所以传入到后台的都不是有连带被保险人但不需要校验人数的
			 */
			/**
			if(mRiskSortValue.trim().equals("1"))
			{
				String tSql = "select count(*) from lcinsuredrelated a  where "
					+ " a.polno = '" + mPolNo + "' and maincustomerno = '"
					+ mMainInsuredNo + "'" + " and RelationToInsured <> '00'";
				ExeSQL tExeSQL = new ExeSQL();
				String tResult = tExeSQL.getOneValue(tSql);
				int tInsuredRelaNum = Integer.parseInt(tResult);
				String tSql2 = "select standbyflag2 from lcduty where polno='" + mPolNo
					+ "'";
				String tResult2 = tExeSQL.getOneValue(tSql2);
				
				if(tResult2==null||tResult2.equals(""))
				{
					CError.buildErr(this, "查询不到承保录入的主被保险下的连带被保险人数!");
					return false;
				}
				else
				{
					int tStandByFlag2 = Integer.parseInt(tResult2);
					if (tInsuredRelaNum >= tStandByFlag2) {
						CError.buildErr(this, "超过该主被保险下的连带被保人数，无法新增连带被保险人!");
						return false;
					}
				}
			}
			*/
		}

		
		if (!this.checkLDPerson()) {
			return false;
		}
		return true;

	}

	/**
	 * 检查地址数据是否正确 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	 * 
	 * @return boolean
	 */

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

		}

		return true;
	}

	/*
	 * 如果增人时有保险计划,那么要一块添加保险计划下的所有险种 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean jixf 20051119 add
	 */

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
		logger.debug("人数是----***：" + mLCContSchema.getPeoples());
		if (mLCContSchema.getPeoples() == 0) {
			mLCContSchema.setPeoples(1);
		} else {
			// tongmeng 2009-03-19 只有新增才修改peoples
			if (mOperate.equals("INSERT||CONTINSURED")) {
				mLCContSchema.setPeoples(mLCContSchema.getPeoples() + 1);
			}
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
		logger.debug("sfdjfldjfl:" + ss);
		if (ss == null || "".equals(ss)) {
			ss = getGrpCustomerNo(mLCInsuredSchema, mLCAddressSchema);
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
		}
		if (mLDPersonSchema.getCustomerNo() != null
				&& !(mLDPersonSchema.getCustomerNo().equals(""))) {
			mLCInsuredSchema.setInsuredNo(mLDPersonSchema.getCustomerNo());
			mLCInsuredRelatedSchema.setCustomerNo(mLDPersonSchema
					.getCustomerNo());
			mLCContSchema.setInsuredNo(mLDPersonSchema.getCustomerNo());
		} else {
			mLCInsuredSchema.setInsuredNo(ss);
			mLDPersonSchema.setCustomerNo(ss);
			mLCInsuredRelatedSchema.setCustomerNo(ss);
			mLCContSchema.setInsuredNo(ss);
			tISNewPerson = false; // 无需新建客户
		}
		if (mOperate.equals("INSERT||CONTINSURED")) {
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setGrpContNo(mLCContSchema.getGrpContNo());
			tLCInsuredDB.setContNo(ContNo);
			tLCInsuredDB.setInsuredNo(mLDPersonSchema.getCustomerNo());
			if (tLCInsuredDB.query() != null && tLCInsuredDB.query().size() > 0) {
				CError tError = new CError();
				tError.moduleName = "ContIsuredBL";
				tError.functionName = "DealDate";
				tError.errorMessage = "不能重复添加同一客户!";
				this.mErrors.addOneError(tError);
				return false;
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
				tLDPersonSchema
						.setNativePlace(mLDPersonSchema.getNativePlace());
			}
			if (mLDPersonSchema.getNationality() != null
					&& !mLDPersonSchema.getNationality().equals("")) {
				tLDPersonSchema
						.setNationality(mLDPersonSchema.getNationality());
			}
			if (mLDPersonSchema.getRgtAddress() != null
					&& !mLDPersonSchema.getRgtAddress().equals("")) {
				tLDPersonSchema.setRgtAddress(mLDPersonSchema.getRgtAddress());
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
				tLDPersonSchema.setSmokeFlag(mLDPersonSchema.getSmokeFlag());
			}
			if (mLDPersonSchema.getLicenseType() != null
					&& !mLDPersonSchema.getLicenseType().equals("")) {
				tLDPersonSchema
						.setLicenseType(mLDPersonSchema.getLicenseType());
			}
			if (mLDPersonSchema.getGrpName() != null
					&& !mLDPersonSchema.getGrpName().equals("")) {
				tLDPersonSchema.setGrpName(mLDPersonSchema.getGrpName());
			}
			tLDPersonSchema.setModifyDate(theCurrentDate);
			tLDPersonSchema.setModifyTime(theCurrentTime);
		}

		// tongmeng 2008-08-18 add
		// 泰康程序产生

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
		String tAppntNoSql = "select appntno from lccont where contno='"
				+ ContNo + "'";
		String tAppntNo = "";
		ExeSQL tExeSQL = new ExeSQL();
		tAppntNo = tExeSQL.getOneValue(tAppntNoSql);
		mLCInsuredSchema.setAppntNo(tAppntNo);
		mLCInsuredSchema.setGrpContNo(mLCContSchema.getGrpContNo());

		mLCInsuredSchema.setManageCom(mLCContSchema.getManageCom()); // 管理理机构
		if (mLCInsuredSchema.getExecuteCom() == null
				|| mLCInsuredSchema.getExecuteCom().equals("")) {
			mLCInsuredSchema.setExecuteCom(mLCInsuredSchema.getManageCom()); // 处理机构
		}
		mLCInsuredSchema.setSequenceNo(tSequenceNo);
		mLCInsuredSchema.setFamilyID(mLCContSchema.getFamilyID());
		mLCInsuredSchema.setMakeDate(theCurrentDate);
		mLCInsuredSchema.setMakeTime(theCurrentTime);
		mLCInsuredSchema.setModifyDate(theCurrentDate);
		mLCInsuredSchema.setModifyTime(theCurrentTime);
		mLCInsuredSchema.setOperator(mGlobalInput.Operator);
		mLCInsuredSchema.setSocialInsuFlag("0");// 初始化社保标记

		mLCInsuredRelatedSchema.setMakeDate(theCurrentDate);
		mLCInsuredRelatedSchema.setMakeTime(theCurrentTime);
		mLCInsuredRelatedSchema.setModifyDate(theCurrentDate);
		mLCInsuredRelatedSchema.setModifyTime(theCurrentTime);
		mLCInsuredRelatedSchema.setOperator(mGlobalInput.Operator);
		// tongmeng 2009-03-19 add
		// 设置被保人数
		mLCInsuredSchema.setInsuredPeoples(this.tInsuredPeoples);
		// 对于团险添加被保人，对被保人序号赋值
		// 取最大值加1
		if (mOperate.equals("INSERT||CONTINSURED")) {
			// 在以下SQL增加了hint词/*+RULE*/来提高效率
			String tSQL = "select /*+RULE*/ nvl(max(customerseqno),0) from lcinsured "
					+ "where grpcontno='" + mLCContSchema.getGrpContNo() + "'";
			// ExeSQL tExeSQL = new ExeSQL();
			int tCustomerNo = Integer.parseInt(tExeSQL.getOneValue(tSQL));
			mLCInsuredSchema.setCustomerSeqNo(tCustomerNo + 1);
		}
		logger.debug("处理告知信息");

		// 处理客户表社保标记
		tLDPersonSchema.setSocialInsuFlag(mLCInsuredSchema.getSocialInsuFlag());
		ref.transFields(tLDPersonSchema,mLCInsuredSchema);
		map.put(mLCInsuredSchema, "DELETE&INSERT");
		map.put(mLCInsuredRelatedSchema, "DELETE&INSERT");
		map.put(tLDPersonSchema, "DELETE&INSERT");
		// 处理告知明细信息
		if (mOperate.equals("INSERT||CONTINSURED")) {
			if (!insertData()) {
				return false;
			}
		}

		return true;
	}

	public VData getCardResult() {
		return this.rResult;
	}

	

	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 处理保存逻辑，保存处理过的数据 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean insertData() {
		return true;
	}

	private void jbInit() throws Exception {
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

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("after getInputData!!!!!");
		if (!this.checkData()) {
			return false;
		}

		logger.debug("---checkData---");
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError.buildErr(this, "数据处理失败ContInsuredBL-->dealData!");
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		// 　数据提交、保存
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

	/**
	 * 团险客户合并 合并原则： 1．姓名、性别、出生日期、证件号码、证件类型五项与系统中原有客户资料完全一致时，则进行自动合并。
	 * 
	 * @param tLCPolSchema
	 *            LCPolSchema
	 * @param tLCAppntIndSchema
	 *            LCAppntIndSchema
	 * @param tLCInsuredSchema
	 *            LCInsuredSchema
	 * @param tFlag
	 *            int 客户号码合并类型：0 投保人客户号合并，1 被保险人客户号合并
	 * @return String 返回已经存在的客户号，如果没有投保，返回空字符串
	 */
	public String getGrpCustomerNo(LCInsuredSchema tLCInsuredSchema,
			LCAddressSchema tLCAddressSchema) {
		String tCustomerNo = "";
		try {
			String tName = ""; // 客户姓名
			String tSex = ""; // 客户性别
			String tBirthday = ""; // 客户出生日期
			String tIDNoType = ""; // 证件类型
			String tIDNo = ""; // 证件号码
			// String tManagecom = tLCPolSchema.getManageCom(); //管理机构
			// String tBankAccNo = tLCPolSchema.getBankAccNo(); //银行帐号
			// String tAgentCode = tLCPolSchema.getAgentCode(); //代理人编码
			String tPhone = ""; // 联系电话1
			String tPhone2 = ""; // 联系电话2
			tName = tLCInsuredSchema.getName();
			tSex = tLCInsuredSchema.getSex();
			tBirthday = tLCInsuredSchema.getBirthday();
			tIDNoType = tLCInsuredSchema.getIDType();
			tIDNo = tLCInsuredSchema.getIDNo();
			tPhone = tLCAddressSchema.getMobile();
			tPhone2 = tLCAddressSchema.getPhone();
			if (tPhone == null || tPhone.equals("")) {
				tPhone = " ";
			}
			if (tPhone2 == null || tPhone2.equals("")) {
				tPhone2 = " ";
			}
			ExeSQL tExeSQL = new ExeSQL();
			tBirthday = tExeSQL.getOneValue(" select to_char(to_date('"
					+ tBirthday + "'),'yyyy-mm-dd') from dual ");

			logger.debug("*********getCustomerNo***********");
			logger.debug("tName: " + StrTool.GBKToUnicode(tName));
			logger.debug("tSex: " + tSex);
			logger.debug("tBirthday: " + tBirthday);
			logger.debug("tIDNoType: " + tIDNoType);
			logger.debug("tIDNo: " + tIDNo);
			logger.debug("tPhone: " + tPhone);
			logger.debug("tPhone2: " + tPhone2);

			LDPersonSet tLDPersonSet = new LDPersonSet();
			LDPersonDB tLDPersonDB = new LDPersonDB();

			if (StrTool.cTrim(tCustomerNo).equals("")) {
				tLDPersonDB = new LDPersonDB();
				if (!tIDNoType.equals("0")) {
					tLDPersonDB.setName(tName);
					tLDPersonDB.setSex(tSex);
					tLDPersonDB.setBirthday(tBirthday);
					tLDPersonDB.setIDType(tIDNoType);
					tLDPersonDB.setIDNo(tIDNo);
					tLDPersonSet = tLDPersonDB.query();
					if (tLDPersonSet.size() > 0) {
						tCustomerNo = tLDPersonSet.get(1).getCustomerNo();
					}
				} else {
					tLDPersonDB.setName(tName);
					tLDPersonDB.setSex(tSex);
					tLDPersonDB.setBirthday(tBirthday);
					tLDPersonDB.setIDType(tIDNoType);
					// tLDPersonDB.setBirthday(tIDNo);
					// String
					// tpersonSql="select * from ldperson where name='"+StrTool
					// .GBKToUnicode(tName)+"' "
					// +" and sex='"+tSex+"' "
					// +" and birthday=to_date('"+tBirthday+"','yyyy-mm-dd')";
					tLDPersonSet = tLDPersonDB.query();
					logger.debug("查询除身份证号外其他四项都一样的数据条数： "
							+ tLDPersonSet.size());
					for (int i = 1; i <= tLDPersonSet.size(); i++) {
						String tCompareCustomerNo = tLDPersonSet.get(i)
								.getCustomerNo();
						String tCompareIDNo = tLDPersonSet.get(i).getIDNo();
						if (StrTool.cTrim(tCompareIDNo).length() == 15) {
							if (StrTool.cTrim(tCompareIDNo).length() == StrTool
									.cTrim(tIDNo).length()) {
								// 新增人的IDNo是15位
								if (tCompareIDNo.equals(tIDNo)) {
									return tCompareCustomerNo;
								}
							} else {
								// 新增人的IDNo是18位 需要转换成15位后再做比较
								if (tCompareIDNo.equals(get15IDNo(tIDNo))) {
									return tCompareCustomerNo;
								}

							}
						} else {
							if (StrTool.cTrim(tCompareIDNo).length() == StrTool
									.cTrim(tIDNo).length()) {
								// 新增人的IDNo是18位
								if (tCompareIDNo.equals(tIDNo)) {
									return tCompareCustomerNo;
								}
							} else {
								// 新增人的IDNo是15位 需要转换成18位后再做比较
								if (tCompareIDNo.equals(get18IDNo(tIDNo,
										tBirthday))) {
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
	
	
	public static void main(String[] args) {
		GlobalInput tGI = new GlobalInput();
		LCContSchema tLCContSchema = new LCContSchema();

		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		LCInsuredDB tOLDLCInsuredDB = new LCInsuredDB();
		LCAddressSchema tLCAddressSchema = new LCAddressSchema();
		LCInsuredSchema tmLCInsuredSchema = new LCInsuredSchema();
		LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
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
		InsuredRelaAddBL tInsuredRelaAddBL = new InsuredRelaAddBL();
		if (tInsuredRelaAddBL.submitData(tVData, "INSERT||CONTINSURED")) {
			logger.debug("Sucess");
		} else {
			logger.debug(tInsuredRelaAddBL.mErrors.getFirstError());
		}
	}
}
