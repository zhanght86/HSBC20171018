package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPAddressBL;
import com.sinosoft.lis.bl.LPAppntBL;
import com.sinosoft.lis.bl.LPContBL;
import com.sinosoft.lis.bl.LPDutyBL;
import com.sinosoft.lis.bl.LPGetBL;
import com.sinosoft.lis.bl.LPInsureAccBL;
import com.sinosoft.lis.bl.LPInsureAccClassBL;
import com.sinosoft.lis.bl.LPInsureAccClassFeeBL;
import com.sinosoft.lis.bl.LPInsureAccFeeBL;
import com.sinosoft.lis.bl.LPInsuredBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.bl.LPPremBL;
import com.sinosoft.lis.db.LCAccountDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPAccountDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPAccountSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vbl.LCPolBLSet;
import com.sinosoft.lis.vdb.LDPersonDBSet;
import com.sinosoft.lis.vdb.LPPersonDBSet;
import com.sinosoft.lis.vschema.LCAccountSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LPAccountSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.tb.CustomerImpartBL;

/**
 * <p>
 * Title: 投保人变更明细
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author lizhuo
 * @version 1.0
 */
public class PEdorAEDetailBL {
private static Logger logger = Logger.getLogger(PEdorAEDetailBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPAppntSchema mLPAppntSchema = new LPAppntSchema();
	private LPAppntSet mLPAppntSet = new LPAppntSet();
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	private LPCustomerImpartSet mLPCustomerImpartSet = new LPCustomerImpartSet();
	private LPCustomerImpartParamsSet mLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();
	private LPAddressSchema mLPAddressSchema = new LPAddressSchema();
	private LPAddressSet mLPAddressSet = new LPAddressSet();
	private LPPersonSet mLPPersonSet = new LPPersonSet();
	private LPContSet mLPContSet = new LPContSet();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LPPolSet mLPPolSet = new LPPolSet();
	private LPInsureAccSet mLPInsureAccSet = new LPInsureAccSet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LPInsureAccClassSet mLPInsureAccClassSet = new LPInsureAccClassSet();
	private LPInsuredSet mLPInsuredSet = new LPInsuredSet();
	private LPInsuredSet aLPInsuredSet = new LPInsuredSet();
	private LPInsureAccFeeSet mLPInsureAccFeeSet = new LPInsureAccFeeSet();
	private LPInsureAccClassFeeSet mLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LPAccountSchema mLPAccountSchema = new LPAccountSchema();
	private LPPersonSchema mLPPersonSchema = new LPPersonSchema();
	// 新客户
	private String mAppntNo = "";
	// private LPPolSet mLPPolSet = new LPPolSet();
	private GlobalInput mGlobalInput = new GlobalInput();
	private Reflections tRef = new Reflections();
	private BqCalBase mBqCalBase = new BqCalBase();
	private String mReasonCode = "";

	public PEdorAEDetailBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		// 数据校验操作
		if (!checkData()) {
			return false;
		}
		// 数据准备操作
		if (!dealData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPAppntSchema = (LPAppntSchema) mInputData.getObjectByObjectName(
					"LPAppntSchema", 0);
			mLPAddressSchema = (LPAddressSchema) mInputData
					.getObjectByObjectName("LPAddressSchema", 0);
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			aLPInsuredSet = (LPInsuredSet) mInputData.getObjectByObjectName(
					"LPInsuredSet", 0);
			mLCCustomerImpartSet = (LCCustomerImpartSet) mInputData
					.getObjectByObjectName("LCCustomerImpartSet", 0);
			mReasonCode = mLPEdorItemSchema.getEdorReasonCode();
			if (mLPEdorItemSchema.getEdorReasonCode().equals("01")) {
				mLPPersonSchema = (LPPersonSchema) mInputData
						.getObjectByObjectName("LPPersonSchema", 0);
				if (mLPPersonSchema == null) {
					return false;
				}
			}
			if (mLPAppntSchema == null || mLPEdorItemSchema == null) {
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		boolean flag = true;
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		// tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemSet = tLPEdorItemDB.query();

		mLPEdorItemSchema = tLPEdorItemSet.get(1);
		mLPEdorItemSchema.setEdorReasonCode(mReasonCode);

		return flag;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		// XinYQ added on 2007-05-23
		String DeleteSQL = new String("");
	
		String[] arrNeedDelete = new String[] { "LPCSpec","LPPerson", "LPAppnt","LPDuty","LPPrem","LPGet",
				"LPAddress", "LPAccount" };
		for (int i = 0; i < arrNeedDelete.length; i++) {
			DeleteSQL = "delete from " + arrNeedDelete[i] + " "
					+ "where 1 = 1 " + "and EdorNo = '"
					+ "?EdorNo?" + "' " + "and EdorType = '"
					+ "?EdorType?" + "'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(DeleteSQL);
			sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sqlbv.put("EdorType", mLPEdorItemSchema.getEdorType());
			mMap.put(sqlbv, "DELETE");
		}

		//删除多次保存的告知
		String tSqla = "DELETE FROM LPCustomerImpart WHERE LPCustomerImpart.EdorType='AE' and exists (select 'X' from LPEdorItem where EdorAcceptNo='"
			+ "?EdorAcceptNo?"
			+ "' and EdorNo=LPCustomerImpart.EdorNo)";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(tSqla);
		sbv1.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
		mMap.put(sbv1, "DELETE");
		String tSqlb = "DELETE FROM LPCustomerImpartParams WHERE LPCustomerImpartParams.EdorType='AE' and exists (select 'X' from LPEdorItem where EdorAcceptNo='"
			+ "?EdorAcceptNo?"
			+ "' and EdorNo=LPCustomerImpartParams.EdorNo)";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(tSqlb);
		sbv2.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
		mMap.put(sbv2, "DELETE");
		
		
			
		LPCSpecSet mLPCSpecSet = new LPCSpecSet();
		LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		LCCSpecSet tLCCSpecSet = new LCCSpecSet();
		tLCCSpecDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCCSpecSet = tLCCSpecDB.query();
		if(tLCCSpecSet.size()>0)
		{
			for(int k=1;k<=tLCCSpecSet.size();k++)
			{
				LPCSpecSchema mLPCSpecSchema = new LPCSpecSchema();
				tRef.transFields(mLPCSpecSchema, tLCCSpecSet.get(k));	
				mLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				mLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mLPCSpecSet.add(mLPCSpecSchema);				
			}
			mMap.put(mLPCSpecSet, "DELETE&INSERT");
		}
		
		// 本次保单的处理
		LCContDB cLCContDB = new LCContDB();
		cLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!cLCContDB.getInfo()) {
			CError.buildErr(this, "查询保单信息失败!");
			return false;
		}
		mLCContSchema = cLCContDB.getSchema();

		// 准备投保人表的信息
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		int mFlag;
		// 查询使用
		ExeSQL i_exesql = new ExeSQL();

		// 准备个人保单（保全）的信息
		LPPersonSchema tLPPersonSchema = new LPPersonSchema();
		mAppntNo = mLPAppntSchema.getAppntNo();
		if (mAppntNo.equals("") || mAppntNo == null) {
			//属于新增客户
			LDPersonDB tLDPersonDB = new LDPersonDB();
			LDPersonSet tLDPersonSet = new LDPersonDBSet();
			tLDPersonDB.setName(mLPAppntSchema.getAppntName());
			tLDPersonDB.setSex(mLPAppntSchema.getAppntSex());
			tLDPersonDB.setBirthday(mLPAppntSchema.getAppntBirthday());
			//无证件时无需进行比较
			if(!"9".equals(mLPAppntSchema.getIDType()))
			{
			  tLDPersonDB.setIDType(mLPAppntSchema.getIDType());
			  tLDPersonDB.setIDNo(mLPAppntSchema.getIDNo());
			}
			tLDPersonSet = tLDPersonDB.query();
			if (tLDPersonSet.size() < 1) {
				// 产生新的客户号
				mAppntNo = PubFun1.CreateMaxNo("CUSTOMERNO", "SN");
				tLPAppntSchema.setAppntNo(mAppntNo);
				tLPPersonSchema.setOperator(mGlobalInput.Operator);
				tLPPersonSchema.setMakeDate(PubFun.getCurrentDate());
				tLPPersonSchema.setMakeTime(PubFun.getCurrentTime());
			} else {
				mAppntNo = tLDPersonSet.get(1).getCustomerNo();
				tRef.transFields(tLPPersonSchema, tLDPersonSet.get(1));
			}
		} else {
			// 已存在客户信息，进行校验
			LPPersonDB tLPPersonDB = new LPPersonDB();
			LPPersonSet tLPPersonSet = new LPPersonDBSet();
			tLPPersonDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPersonDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPersonDB.setName(mLPAppntSchema.getAppntName());
			tLPPersonDB.setSex(mLPAppntSchema.getAppntSex());
			tLPPersonDB.setBirthday(mLPAppntSchema.getAppntBirthday());
			tLPPersonDB.setIDType(mLPAppntSchema.getIDType());
			tLPPersonDB.setIDNo(mLPAppntSchema.getIDNo());
			tLPPersonSet = tLPPersonDB.query(); // 先查LPPerson表
			if (tLPPersonSet.size() > 0) {
				tLPPersonSchema = tLPPersonSet.get(1);
			} else {
				// 如果不符合查询LDPerson表
				LDPersonDB tLDPersonDB = new LDPersonDB();
				LDPersonSet tLDPersonSet = new LDPersonDBSet();
				tLDPersonDB.setName(mLPAppntSchema.getAppntName());
				tLDPersonDB.setSex(mLPAppntSchema.getAppntSex());
				tLDPersonDB.setBirthday(mLPAppntSchema.getAppntBirthday());
				//无证件时无需进行比较
				if(!"9".equals(mLPAppntSchema.getIDType()))
				{
				  tLDPersonDB.setIDType(mLPAppntSchema.getIDType());
				  tLDPersonDB.setIDNo(mLPAppntSchema.getIDNo());
				}

				tLDPersonSet = tLDPersonDB.query();
				// logger.debug(tLDPersonSet.size());
				if (tLDPersonSet.size() < 1) {
					CError.buildErr(this, "客户关键信息不符!");
					mErrors.addOneError("客户关键信息不符!");
					return false;
				}
				tRef.transFields(tLPPersonSchema, tLDPersonSet.get(1));
			}
		}
		tLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPersonSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPersonSchema.setCustomerNo(mAppntNo);
		tLPPersonSchema.setName(mLPAppntSchema.getAppntName());
		tLPPersonSchema.setSex(mLPAppntSchema.getAppntSex());
		tLPPersonSchema.setBirthday(mLPAppntSchema.getAppntBirthday());
		tLPPersonSchema.setIDType(mLPAppntSchema.getIDType());
		tLPPersonSchema.setIDNo(mLPAppntSchema.getIDNo());
		tLPPersonSchema.setOccupationType(mLPAppntSchema.getOccupationType());
		tLPPersonSchema.setOccupationCode(mLPAppntSchema.getOccupationCode());
		tLPPersonSchema.setMarriage(mLPAppntSchema.getMarriage());
		tLPPersonSchema.setRgtAddress(mLPAppntSchema.getRgtAddress());
		// tLPPersonSchema.setHealth(mLPAppntSchema.getHealth());
		tLPPersonSchema.setNativePlace(mLPAppntSchema.getNativePlace());
		// tLPPersonSchema.setNationality(mLPAppntSchema.getNationality());
		tLPPersonSchema.setWorkType(mLPAppntSchema.getWorkType());
		tLPPersonSchema.setPluralityType(mLPAppntSchema.getPluralityType());
		tLPPersonSchema.setGrpName(mLPAddressSchema.getGrpName());
		tLPPersonSchema.setModifyDate(PubFun.getCurrentDate());
		tLPPersonSchema.setModifyTime(PubFun.getCurrentTime());
		tLPPersonSchema.setLastName(mLPAppntSchema.getLastName());
		tLPPersonSchema.setFirstName(mLPAppntSchema.getFirstName());
		tLPPersonSchema.setLastNameEn(mLPAppntSchema.getLastNameEn());
		tLPPersonSchema.setFirstNameEn(mLPAppntSchema.getFirstNameEn());
		tLPPersonSchema.setNameEn(mLPAppntSchema.getNameEn());
		tLPPersonSchema.setLastNamePY(mLPAppntSchema.getLastNamePY());
		tLPPersonSchema.setFirstNamePY(mLPAppntSchema.getFirstNamePY());
		tLPPersonSchema.setLanguage(mLPAppntSchema.getLanguage());
		mLPPersonSet.add(tLPPersonSchema);
		mMap.put(mLPPersonSet, "DELETE&INSERT"); // 加入Map

		// 检测地址号
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		String AddressNo = "";
		mFlag = 0; // 地址号查找标志
		if (!mLPAppntSchema.getAppntNo().equals("")) {
			//老客户新地址
			if (mLPAddressSchema.getAddressNo() == 0) {
					AddressNo = "1";
					tLPAddressSchema.setOperator(mGlobalInput.Operator);
					tLPAddressSchema.setMakeDate(PubFun.getCurrentDate());
					tLPAddressSchema.setMakeTime(PubFun.getCurrentTime());
			} else {
				String i_sql = "";
				i_sql = "select (case when Max(AddressNo) is not null then Max(AddressNo) else 0 end) + 1 from LCAddress where CustomerNo = '"
						+ "?CustomerNo?" + "'";
				SQLwithBindVariables sbv=new SQLwithBindVariables();
				sbv.sql(i_sql);
				sbv.put("CustomerNo", mLPAppntSchema.getAppntNo());
				AddressNo = i_exesql.getOneValue(sbv).toString();
				tLPAddressSchema.setOperator(mGlobalInput.Operator);
				tLPAddressSchema.setMakeDate(PubFun.getCurrentDate());
				tLPAddressSchema.setMakeTime(PubFun.getCurrentTime());
			}
		} else {
			//新客户新地址
			AddressNo = "1";
			tLPAddressSchema.setOperator(mGlobalInput.Operator);
			tLPAddressSchema.setMakeDate(PubFun.getCurrentDate());
			tLPAddressSchema.setMakeTime(PubFun.getCurrentTime());
		}

		// 设置地址表信息
		logger.debug(AddressNo);
		tLPAddressSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAddressSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAddressSchema.setCustomerNo(mAppntNo);
		tLPAddressSchema.setAddressNo(AddressNo);
		tLPAddressSchema.setPostalAddress(mLPAddressSchema.getPostalAddress());
		tLPAddressSchema.setZipCode(mLPAddressSchema.getZipCode());
		tLPAddressSchema.setHomeAddress(mLPAddressSchema.getHomeAddress());
		tLPAddressSchema.setHomeZipCode(mLPAddressSchema.getHomeZipCode());
		// tLPAddressSchema.setHomePhone(mLPAddressSchema.getHomePhone());
		// tLPAddressSchema.setHomeFax(mLPAddressSchema.getHomeFax());
		// tLPAddressSchema.setPhone(mLPAddressSchema.getPhone());
		tLPAddressSchema.setMobile(mLPAddressSchema.getMobile());
		// tLPAddressSchema.setFax(mLPAddressSchema.getFax());
		// tLPAddressSchema.setProvince(mLPAddressSchema.getProvince());
		// tLPAddressSchema.setCity(mLPAddressSchema.getCity());
		// tLPAddressSchema.setCounty(mLPAddressSchema.getCounty());
		tLPAddressSchema.setEMail(mLPAddressSchema.getEMail());
		tLPAddressSchema.setGrpName(mLPAddressSchema.getGrpName());
		tLPAddressSchema.setPhone(mLPAddressSchema.getPhone());
		// tLPAddressSchema.setCompanyAddress(mLPAddressSchema.getCompanyAddress());
		// tLPAddressSchema.setCompanyZipCode(mLPAddressSchema.getCompanyZipCode());
		// tLPAddressSchema.setCompanyFax(mLPAddressSchema.getCompanyFax());
		mLPAddressSchema.setSchema(tLPAddressSchema);
		mLPAddressSchema.setModifyDate(PubFun.getCurrentDate());
		mLPAddressSchema.setModifyTime(PubFun.getCurrentTime());
		mLPAddressSet.add(mLPAddressSchema);
		mMap.put(mLPAddressSet, "DELETE&INSERT"); // 加入Map

		// 帐户信息处理
		LPAccountSchema tLPAccountSchema = new LPAccountSchema();
		
    	mFlag = 0; // 帐户标记
		if (!mLPAppntSchema.getAppntNo().equals("")
				|| mLPAppntSchema.getAppntNo() != null) {
			if (!mLPAppntSchema.getAccName().equals("")
					&& !mLPAppntSchema.getBankAccNo().equals("")
					&& !mLPAppntSchema.getBankCode().equals("")) {
				LPAccountDB tLPAccountDB = new LPAccountDB();
				LPAccountSet tLPAccountSet = new LPAccountSet();
				tLPAccountDB.setCustomerNo(mLPAppntSchema.getAppntNo());
				tLPAccountDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPAccountDB.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPAccountDB.setBankAccNo(mLPAppntSchema.getBankAccNo());
				tLPAccountDB.setBankCode(mLPAppntSchema.getBankCode());
				tLPAccountDB.setAccName(mLPAppntSchema.getAccName());
				tLPAccountSet = tLPAccountDB.query();
				logger.debug(tLPAccountSet.size());
				if (tLPAccountSet.size() > 0) {
					mFlag = 1;
				}
				if (mFlag == 0) { // P表未查到再查C表
					LCAccountDB tLCAccountDB = new LCAccountDB();
					LCAccountSet tLCAccountSet = new LCAccountSet();
					tLCAccountDB.setCustomerNo(mLPAppntSchema.getAppntNo());
					tLCAccountDB.setBankAccNo(mLPAppntSchema.getBankAccNo());
					tLCAccountDB.setBankCode(mLPAppntSchema.getBankCode());
					tLCAccountDB.setAccName(mLPAppntSchema.getAccName());
					tLCAccountSet = tLCAccountDB.query();
					logger.debug(tLCAccountSet.size());
					if (tLCAccountSet.size() > 0) {
						mFlag = 1;
					}
				}
			} else {
				mFlag = 1;
			}
		}
		boolean bPayLocation = true;
		if (mLPAppntSchema.getAccName() == null
				|| mLPAppntSchema.getBankAccNo() == null
				|| mLPAppntSchema.getBankCode() == null
				|| mLPAppntSchema.getAccName().equals("")
				|| mLPAppntSchema.getBankAccNo().equals("")
				|| mLPAppntSchema.getBankCode().equals("")) {
			bPayLocation = false;
			mFlag = 1;
		}
		if (mFlag == 0) {
			// 在帐户表中新建信息
			tLPAccountSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPAccountSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPAccountSchema.setCustomerNo(mAppntNo);
			tLPAccountSchema.setAccName(mLPAppntSchema.getAccName());
			tLPAccountSchema.setBankCode(mLPAppntSchema.getBankCode());
			tLPAccountSchema.setBankAccNo(mLPAppntSchema.getBankAccNo());
			tLPAccountSchema.setAccKind("Y");
			tLPAccountSchema.setOperator(mGlobalInput.Operator);
			tLPAccountSchema.setMakeDate(PubFun.getCurrentDate());
			tLPAccountSchema.setMakeTime(PubFun.getCurrentTime());
			tLPAccountSchema.setModifyDate(PubFun.getCurrentDate());
			tLPAccountSchema.setModifyTime(PubFun.getCurrentTime());
			mLPAccountSchema.setSchema(tLPAccountSchema);
			mMap.put(mLPAccountSchema, "DELETE&INSERT");
		}

		// 查询投保人LPAppnt表
		LPAppntBL tLPAppntBL = new LPAppntBL();
		LPAppntSet tLPAppntSet = new LPAppntSet();
		tLPAppntSet = tLPAppntBL.queryLPAppnt(mLPEdorItemSchema);
		logger.debug(tLPAppntSet.size());
		if (tLPAppntSet.size() <= 0) {
			// @@错误处理
			CError.buildErr(this, "查询投保人信息失败！");
			return false;
		} else {
			tLPAppntSchema.setSchema(tLPAppntSet.get(1));
		}
		// 投保人信息
		tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntSchema.setAppntName(mLPAppntSchema.getAppntName());
		tLPAppntSchema.setContNo(mLPAppntSchema.getContNo());
		tLPAppntSchema.setAppntNo(mAppntNo);
		tLPAppntSchema.setAppntSex(mLPAppntSchema.getAppntSex());
		tLPAppntSchema.setAppntBirthday(mLPAppntSchema.getAppntBirthday());
		tLPAppntSchema.setIDType(mLPAppntSchema.getIDType());
		tLPAppntSchema.setIDNo(mLPAppntSchema.getIDNo());
		tLPAppntSchema.setOccupationType(mLPAppntSchema.getOccupationType());
		tLPAppntSchema.setOccupationCode(mLPAppntSchema.getOccupationCode());
		tLPAppntSchema.setWorkType(mLPAppntSchema.getWorkType());
		tLPAppntSchema.setPluralityType(mLPAppntSchema.getPluralityType());
		tLPAppntSchema.setMarriage(mLPAppntSchema.getMarriage());
		// tLPAppntSchema.setHealth(mLPAppntSchema.getHealth());
		// tLPAppntSchema.setNationality(mLPAppntSchema.getNationality());
		tLPAppntSchema.setNativePlace(mLPAppntSchema.getNativePlace());
		tLPAppntSchema.setModifyDate(PubFun.getCurrentDate());
		tLPAppntSchema.setModifyTime(PubFun.getCurrentTime());
		tLPAppntSchema.setAddressNo(AddressNo);
		tLPAppntSchema.setBankAccNo(mLPAppntSchema.getBankAccNo());
		tLPAppntSchema.setBankCode(mLPAppntSchema.getBankCode());
		tLPAppntSchema.setAccName(mLPAppntSchema.getAccName());
		tLPAppntSchema.setRgtAddress(mLPAppntSchema.getRgtAddress());
		tLPAppntSchema.setLastName(mLPAppntSchema.getLastName());
		tLPAppntSchema.setFirstName(mLPAppntSchema.getFirstName());
		tLPAppntSchema.setLastNameEn(mLPAppntSchema.getLastNameEn());
		tLPAppntSchema.setFirstNameEn(mLPAppntSchema.getFirstNameEn());
		tLPAppntSchema.setNameEn(mLPAppntSchema.getNameEn());
		tLPAppntSchema.setLastNamePY(mLPAppntSchema.getLastNamePY());
		tLPAppntSchema.setFirstNamePY(mLPAppntSchema.getFirstNamePY());
		tLPAppntSchema.setLanguage(mLPAppntSchema.getLanguage());

		// tLPAppntSchema.setSmokeFlag(mLPAppntSchema.getSmokeFlag());
		// 设定与主被报人的关系
		tLPAppntSchema.setRelationToInsured(mLPAppntSchema
				.getRelationToInsured());
		// LCAppntSchema tLcAppntSchema = new LCAppntSchema();
		mLPAppntSet.add(tLPAppntSchema);
		mMap.put(mLPAppntSet, "DELETE&INSERT"); // 加入Map

		// 健康告知
		if ((mLPAppntSchema.getAppntNo().equals("") || mLPAppntSchema
				.getAppntNo() == null)
				&& (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0)) {
			LCCustomerImpartSchema aLCCustomerImpartSchema = new LCCustomerImpartSchema();
			for (int k = 1; k <= mLCCustomerImpartSet.size(); k++) {
				aLCCustomerImpartSchema = mLCCustomerImpartSet.get(k);
				aLCCustomerImpartSchema.setGrpContNo(mLCContSchema
						.getGrpContNo());
				if (mLCContSchema.getGrpContNo() == null) {
					aLCCustomerImpartSchema
							.setGrpContNo("00000000000000000000");
				}
				aLCCustomerImpartSchema.setPrtNo(mLCContSchema.getPrtNo());
				if (mLCContSchema.getPrtNo() == null) {
					mErrors.addOneError("个人保单印刷号码查询失败!");
					return false;
				}
				logger.debug("===================== PrtNo ==========="
						+ mLCContSchema.getPrtNo());
				aLCCustomerImpartSchema.setCustomerNo(mAppntNo);
				aLCCustomerImpartSchema.setCustomerNoType("0");
				mLCCustomerImpartSet.set(k, aLCCustomerImpartSchema);
				logger.debug(mLCCustomerImpartSet.get(k).getCustomerNo());
			}
		}

		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			logger.debug("654654987356");
			VData cVData = new VData();
			cVData.add(mLCCustomerImpartSet);
			cVData.add(mGlobalInput);
			CustomerImpartBL tCustomerImpartBL = new CustomerImpartBL();
			tCustomerImpartBL.submitData(cVData, "IMPART||DEAL");
			mErrors.copyAllErrors(tCustomerImpartBL.mErrors);
			if (tCustomerImpartBL.mErrors.needDealError()) {

				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "dealData";
				tError.errorMessage = tCustomerImpartBL.mErrors.getFirstError()
						.toString();
				this.mErrors.addOneError(tError);
				return false;
			}
			VData tempVData = new VData();
			tempVData = tCustomerImpartBL.getResult();
			LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
			LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
			try {
				tLCCustomerImpartSet = (LCCustomerImpartSet) tempVData
						.getObjectByObjectName("LCCustomerImpartSet", 0);
				tLCCustomerImpartParamsSet = (LCCustomerImpartParamsSet) tempVData
						.getObjectByObjectName("LCCustomerImpartParamsSet", 0);
			} catch (Exception e) {
				CError.buildErr(this, "接受数据失败!");
				return false;
			}
			Reflections tRef = new Reflections();
			LPCustomerImpartParamsSchema tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
			LPCustomerImpartSchema tLPCustomerImpartSchema = new LPCustomerImpartSchema();
			if (tLCCustomerImpartSet != null && tLCCustomerImpartSet.size() > 0) {
				for (int i = 1; i <= tLCCustomerImpartSet.size(); i++) {
					tLPCustomerImpartSchema = new LPCustomerImpartSchema();
					tRef.transFields(tLPCustomerImpartSchema,
							tLCCustomerImpartSet.get(i));
					tLPCustomerImpartSchema.setEdorNo(mLPEdorItemSchema
							.getEdorNo());
					tLPCustomerImpartSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					tLPCustomerImpartSchema
							.setGrpContNo("00000000000000000000");
					tLPCustomerImpartSchema.setPrtNo(mLCContSchema.getPrtNo());
					if (mLCContSchema.getPrtNo() == null) {
						mErrors.addOneError("个人保单印刷号码查询失败!");
						return false;
					}
					mLPCustomerImpartSet.add(tLPCustomerImpartSchema);
				}
			}
			if (tLCCustomerImpartParamsSet != null
					&& tLCCustomerImpartParamsSet.size() > 0) {
				for (int i = 1; i <= tLCCustomerImpartParamsSet.size(); i++) {
					tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
					tRef.transFields(tLPCustomerImpartParamsSchema,
							tLCCustomerImpartParamsSet.get(i));

					tLPCustomerImpartParamsSchema.setEdorNo(mLPEdorItemSchema
							.getEdorNo());
					tLPCustomerImpartParamsSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					tLPCustomerImpartParamsSchema
							.setGrpContNo("00000000000000000000");
					tLPCustomerImpartParamsSchema.setPrtNo(mLCContSchema
							.getPrtNo());
					if (mLCContSchema.getPrtNo() == null) {
						mErrors.addOneError("个人保单印刷号码查询失败!");
						return false;
					}
					mLPCustomerImpartParamsSet
							.add(tLPCustomerImpartParamsSchema);
				}
			}
		}

		
		if (mLPCustomerImpartSet.size() > 0) {
			mMap.put(mLPCustomerImpartSet, "DELETE&INSERT");
		}
		if (mLPCustomerImpartParamsSet.size() > 0) {
			mMap.put(mLPCustomerImpartParamsSet, "DELETE&INSERT");
		}
		// 设置个人保单表
		LPContBL tLPContBL = new LPContBL();
		if (!tLPContBL.queryLPCont(mLPEdorItemSchema)) {
			CError.buildErr(this, "查询合同保单信息失败!");
			return false;
		}
		tLPContBL.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContBL.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContBL.setAppntNo(mAppntNo);
		tLPContBL.setAppntName(mLPAppntSchema.getAppntName());
		tLPContBL.setAppntSex(mLPAppntSchema.getAppntSex());
		tLPContBL.setAppntBirthday(mLPAppntSchema.getAppntBirthday());
		tLPContBL.setAppntBirthday(mLPAppntSchema.getAppntBirthday());
		tLPContBL.setAppntIDType(mLPAppntSchema.getIDType());
		tLPContBL.setAppntIDNo(mLPAppntSchema.getIDNo());
		tLPContBL.setBankAccNo(mLPAppntSchema.getBankAccNo());
		tLPContBL.setBankCode(mLPAppntSchema.getBankCode());
		tLPContBL.setAccName(mLPAppntSchema.getAccName());
		tLPContBL.setModifyDate(PubFun.getCurrentDate());
		tLPContBL.setModifyTime(PubFun.getCurrentTime());
		mLPContSet.clear();
		mLPContSet.add(tLPContBL.getSchema());
		logger.debug("PayLocation:" + bPayLocation);
//		if (bPayLocation == true) {
//			mLPContSet.get(1).setPayLocation("0");
//		} else {
//			mLPContSet.get(1).setPayLocation("1");
//		}

		mMap.put(mLPContSet, "DELETE&INSERT"); // 加入Map

		// 被保人表
		LPInsuredBL tLPInsuredBL = new LPInsuredBL();
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		tLPInsuredSet = tLPInsuredBL.queryAllLPInsured(mLPEdorItemSchema);
		if (tLPInsuredSet.size() < 1) {
			logger.debug("-----------Error!-----------");
			CError.buildErr(this, "查询个人险种表信息失败!");
			return false;
		}
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		for (int j = 1; j <= tLPInsuredSet.size(); j++) {
			tLPInsuredSchema = new LPInsuredSchema();
			tLPInsuredSchema = tLPInsuredSet.get(j);
			tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPInsuredSchema.setAppntNo(mAppntNo);
			tLPInsuredSchema.setModifyDate(PubFun.getCurrentDate());
			tLPInsuredSchema.setModifyTime(PubFun.getCurrentTime());
			logger.debug("InsuredNo :" + tLPInsuredSchema.getInsuredNo());
			for (int a = 1; a <= aLPInsuredSet.size(); a++) {
				logger.debug("Compare InsuredNo:"
						+ aLPInsuredSet.get(a).getInsuredNo());
				if (StrTool.compareString(tLPInsuredSchema.getInsuredNo(),
						aLPInsuredSet.get(a).getInsuredNo())) {
					tLPInsuredSchema.setRelationToAppnt(aLPInsuredSet.get(a)
							.getRelationToAppnt());
					tLPInsuredSchema.setRelationToMainInsured(aLPInsuredSet
							.get(a).getRelationToMainInsured());
				}
			}
			mLPInsuredSet.add(tLPInsuredSchema);
		}
		mMap.put(mLPInsuredSet, "DELETE&INSERT"); // 加入Map

		// 设置个人险种表
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCPolDB.setAppFlag("1");

		LCPolSet tLCPolSet = new LCPolBLSet();
		tLCPolSet = tLCPolDB.query();
		if (tLCPolSet.size() < 1) {
			CError.buildErr(this, "查询个人险种表信息失败!");
			return false;
		}
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = mLPEdorItemSchema.getSchema();
			tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(i);
			tLPEdorItemSchema.setPolNo(tLCPolSchema.getPolNo());
			mLPEdorItemSet.add(tLPEdorItemSchema);

			LPPolBL tLPPolBL = new LPPolBL();
			if (!tLPPolBL.queryLPPol(tLPEdorItemSchema)) {
				CError.buildErr(this, "查询个人险种表信息失败!");
				return false;
			}
			tLPPolBL.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPPolBL.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPPolBL.setAppntNo(mAppntNo);
			tLPPolBL.setAppntName(mLPAppntSchema.getAppntName());
			tLPPolBL.setModifyDate(PubFun.getCurrentDate());
			tLPPolBL.setModifyTime(PubFun.getCurrentTime());
			mLPPolSet.add(tLPPolBL.getSchema());

			// 设置保险帐户表
			LPInsureAccBL tLPInsureAccBL = new LPInsureAccBL();
			LPInsureAccSet tLPInsureAccSet = new LPInsureAccSet();
			tLPInsureAccSet = tLPInsureAccBL
					.queryAllLPInsureAcc(tLPEdorItemSchema);
			if (tLPInsureAccSet!=null&&tLPInsureAccSet.size() >= 1) {
				LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
				for (int j = 1; j <= tLPInsureAccSet.size(); j++) {
					tLPInsureAccSchema = tLPInsureAccSet.get(j);
					tLPInsureAccSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
					tLPInsureAccSchema.setEdorType(tLPEdorItemSchema
							.getEdorType());
					tLPInsureAccSchema.setAppntNo(mAppntNo);
					tLPInsureAccSchema.setModifyDate(PubFun.getCurrentDate());
					tLPInsureAccSchema.setModifyTime(PubFun.getCurrentTime());
					mLPInsureAccSet.add(tLPInsureAccSchema);
				}
			} 
			// 设置保费项表
			LPPremBL tLPPremBL = new LPPremBL();
			LPPremSet tLPPremSet = new LPPremSet();
			tLPPremSet = tLPPremBL.queryAllLPPrem(tLPEdorItemSchema);
			if (tLPPremSet.size() >= 1) {
				LPPremSchema tLPPremSchema = new LPPremSchema();
				for (int j = 1; j <= tLPPremSet.size(); j++) {
					tLPPremSchema = tLPPremSet.get(j);
					tLPPremSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
					tLPPremSchema.setEdorType(tLPEdorItemSchema.getEdorType());
					tLPPremSchema.setOperator(mGlobalInput.Operator);
					tLPPremSchema.setAppntNo(mAppntNo);
					tLPPremSchema.setModifyDate(PubFun.getCurrentDate());
					tLPPremSchema.setModifyTime(PubFun.getCurrentTime());
					mLPPremSet.add(tLPPremSchema);
				}
			}
			//为核保准备数据
			LPDutyBL tLPDutyBL = new LPDutyBL();
			LPDutySet tLPDutySet = new LPDutySet();
			tLPDutySet = tLPDutyBL.queryAllLPDuty(tLPEdorItemSchema);
			if (tLPDutySet.size() >=1) {
				for (int j = 1; j <= tLPDutySet.size(); j++) {
					LPDutySchema tLPDutySchema = new LPDutySchema();
					tLPDutySchema = tLPDutySet.get(j);
					tLPDutySchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
					tLPDutySchema.setEdorType(tLPEdorItemSchema.getEdorType());
					tLPDutySchema.setOperator(mGlobalInput.Operator);
					tLPDutySchema.setModifyDate(PubFun.getCurrentDate());
					tLPDutySchema.setModifyTime(PubFun.getCurrentTime());
					mLPDutySet.add(tLPDutySchema);
				}
			}
			LPGetBL tLPGetBL = new LPGetBL();
			LPGetSet tLPGetSet = new LPGetSet();
			tLPGetSet = tLPGetBL.queryAllLPGet(tLPEdorItemSchema);
			if (tLPGetSet.size() >=1) {
				for (int j = 1; j <= tLPGetSet.size(); j++) {
					LPGetSchema tLPGetSchema = new LPGetSchema();
					tLPGetSchema = tLPGetSet.get(j);
					tLPGetSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
					tLPGetSchema.setEdorType(tLPEdorItemSchema.getEdorType());
					tLPGetSchema.setOperator(mGlobalInput.Operator);
					tLPGetSchema.setModifyDate(PubFun.getCurrentDate());
					tLPGetSchema.setModifyTime(PubFun.getCurrentTime());
					mLPGetSet.add(tLPGetSchema);
				}
			}
			// 设置保险账户分类表
			LPInsureAccClassBL tLPInsureAccClassBL = new LPInsureAccClassBL();
			LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();
			tLPInsureAccClassSet = tLPInsureAccClassBL
					.queryAllLPInsureAccClass(tLPEdorItemSchema);
			if (tLPInsureAccClassSet!=null&&tLPInsureAccClassSet.size() >= 1) {
				LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
				for (int j = 1; j <= tLPInsureAccClassSet.size(); j++) {
					tLPInsureAccClassSchema = tLPInsureAccClassSet.get(j);
					tLPInsureAccClassSchema.setEdorNo(tLPEdorItemSchema
							.getEdorNo());
					tLPInsureAccClassSchema.setEdorType(tLPEdorItemSchema
							.getEdorType());
					tLPInsureAccClassSchema.setAppntNo(mAppntNo);
					tLPInsureAccClassSchema.setModifyDate(PubFun
							.getCurrentDate());
					tLPInsureAccClassSchema.setModifyTime(PubFun
							.getCurrentTime());
					mLPInsureAccClassSet.add(tLPInsureAccClassSchema);
				}
			}

			// 设置保险帐户管理费表
			LPInsureAccFeeBL tLPInsureAccFeeBL = new LPInsureAccFeeBL();
			LPInsureAccFeeSet tLPInsureAccFeeSet = new LPInsureAccFeeSet();
			tLPInsureAccFeeSet = tLPInsureAccFeeBL
					.queryAllLPInsureAccFee(tLPEdorItemSchema);
			if (tLPInsureAccFeeSet!=null&&tLPInsureAccFeeSet.size() >= 1 ) {
				LPInsureAccFeeSchema tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
				for (int j = 1; j <= tLPInsureAccFeeSet.size(); j++) {
					tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
					tLPInsureAccFeeSchema = tLPInsureAccFeeSet.get(j);
					tLPInsureAccFeeSchema.setEdorNo(tLPEdorItemSchema
							.getEdorNo());
					tLPInsureAccFeeSchema.setEdorType("AE");
					tLPInsureAccFeeSchema
							.setPolNo(tLPEdorItemSchema.getPolNo());
					tLPInsureAccFeeSchema.setAppntNo(mAppntNo);
					tLPInsureAccFeeSchema.setMakeDate(PubFun.getCurrentDate());
					tLPInsureAccFeeSchema.setMakeTime(PubFun.getCurrentTime());
					mLPInsureAccFeeSet.add(tLPInsureAccFeeSchema);
			}
			// 设置保险账户管理费分类表
			LPInsureAccClassFeeBL tLPInsureAccClassFeeBL = new LPInsureAccClassFeeBL();
			LPInsureAccClassFeeSet tLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
			tLPInsureAccClassFeeSet = tLPInsureAccClassFeeBL
					.queryAllLPInsureAccClassFee(tLPEdorItemSchema);
			if (tLPInsureAccClassFeeSet!=null&&tLPInsureAccClassFeeSet.size() >= 1) {
				LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
				for (int j = 1; j <= tLPInsureAccClassFeeSet.size(); j++) {
					tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
					tLPInsureAccClassFeeSchema = tLPInsureAccClassFeeSet.get(j);
					tLPInsureAccClassFeeSchema.setEdorNo(tLPEdorItemSchema
							.getEdorNo());
					tLPInsureAccClassFeeSchema.setEdorType(tLPEdorItemSchema
							.getEdorType());
					tLPInsureAccClassFeeSchema.setPolNo(tLPEdorItemSchema
							.getPolNo());
					tLPInsureAccClassFeeSchema.setAppntNo(mAppntNo);
					tLPInsureAccClassFeeSchema.setMakeDate(PubFun
							.getCurrentDate());
					tLPInsureAccClassFeeSchema.setMakeTime(PubFun
							.getCurrentTime());
					mLPInsureAccClassFeeSet.add(tLPInsureAccClassFeeSchema);
			 }
			}
			}
		}
		mMap.put(mLPPolSet, "DELETE&INSERT"); // 加入Map
		mMap.put(mLPInsureAccClassFeeSet, "DELETE&INSERT");
		mMap.put(mLPInsureAccFeeSet, "DELETE&INSERT"); // 加入Map
		mMap.put(mLPInsureAccClassSet, "DELETE&INSERT"); // 加入Map
		mMap.put(mLPPremSet, "DELETE&INSERT"); // 加入Map
		//为核保准备数据
		mMap.put(mLPDutySet, "DELETE&INSERT"); // 加入Map
		mMap.put(mLPGetSet, "DELETE&INSERT"); // 加入Map
		//end
		mMap.put(mLPInsureAccSet, "DELETE&INSERT"); // 加入Map
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setGetMoney(0);
		mLPEdorItemSchema.setEdorReasonCode(mReasonCode);
		mLPEdorItemSchema.setDisplayType("1");
		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(mLPEdorItemSchema, "UPDATE");



		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		mBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());
		mBqCalBase.setInsuredNo(mLPContSet.get(1).getInsuredNo());

		mResult.clear();

		mResult.add(mMap);
		mResult.add(mBqCalBase);

		return true;

	}

	public VData getResult() {
		return mResult;
	}
}
