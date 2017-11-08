/*
 * @(#)GrpEdorWSDetailBL.java      Apr 19, 2006
 *
 * Copyright 2006 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */
package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCDutyBL;
import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.pubfun.CheckFieldCom;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.FieldCarrier;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LMCheckFieldSchema;
import com.sinosoft.lis.schema.LPCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.tb.CalBL;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LMCheckFieldSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团体保全操作 －－ 新增附加险
 * </p>
 * <p>
 * Description: 团体保全新增附加险（整单新增）保全明细
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：lizhuo
 * @CreateDate: Apr 19, 2006
 * @version：1.0
 */
public class GrpEdorWSDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorWSDetailBL.class);
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
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();
	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();
	private String CValidate = "";
	private String Paytodate = "";
	private String Enddate = "";
	private String StrMsg = "";

	private LCCustomerImpartSet mLCCustomerImpartSet;
	private LPCustomerImpartSet mLPCustomerImpartSet;
	private LPCustomerImpartParamsSet mLPCustomerImpartParamsSet;

	public boolean submitData(VData aInputData, String aOperator) {
		mInputData = (VData) aInputData.clone();
		mOperate = aOperator;

		if (!getInputData(mInputData)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		try {
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPGrpPolSet = (LPGrpPolSet) cInputData.getObjectByObjectName(
					"LPGrpPolSet", 0);
			mLCCustomerImpartSet = (LCCustomerImpartSet) mInputData
					.getObjectByObjectName("LCCustomerImpartSet", 0);

		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorWSDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLPGrpEdorItemSchema == null || mGlobalInput == null
				|| mLPGrpPolSet == null) {
			CError.buildErr(this, "接收数据不完整!");
			return false;
		}
		return true;
	}

	private boolean checkData() {
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		if (!tLPGrpEdorItemDB.getInfo()) {
			CError.buildErr(this, "查询团体保全项目信息失败!");
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());
		return true;
	}

	private boolean dealData() {
		if ("INSERT||POL".equals(mOperate)) {
			if (!insertOnePol())
				return false;
		} else if ("DELETE||POL".equals(mOperate)) {
			if (!deletePol())
				return false;
		} else if ("INSERT||Impart".equals(mOperate)) {
			if (!InsertImpart())
				return false;
		}

		return true;
	}

	private boolean deletePol() {
		String GrpPolNo = "";
		for (int i = 1; i <= mLPGrpPolSet.size(); i++) {
			if (i == 1) {
				GrpPolNo = "'" + mLPGrpPolSet.get(i).getGrpPolNo() + "'";
			} else {
				GrpPolNo = GrpPolNo + ",'" + mLPGrpPolSet.get(i).getGrpPolNo()
						+ "'";
			}

			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolDB.setGrpPolNo(mLPGrpPolSet.get(i).getGrpPolNo());
			tLCPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
			tLCPolSet = tLCPolDB.query();
			if (tLCPolSet == null) {
				CError.buildErr(this, "查询新增险种失败!");
				return false;
			}
			for (int j = 1; j <= tLCPolSet.size(); j++) {
				String PolNo = tLCPolSet.get(j).getPolNo();
				String[] tab = { "lcduty", "lcprem", "lcget", "lppol",
						"lpduty", "lpprem", "lpget" };
				for (int k = 0; k < tab.length; k++) {
					mMap.put("delete from " + tab[k] + " where polno = '"
							+ PolNo + "'", "DELETE");
				}
			}
			mMap.put(tLCPolSet, "DELETE");
			mMap.put("delete from lcgrppol where grppolno = '"
					+ mLPGrpPolSet.get(i).getGrpPolNo() + "'", "DELETE");
			mMap.put("delete from lpgrppol where grppolno = '"
					+ mLPGrpPolSet.get(i).getGrpPolNo() + "'", "DELETE");
			mMap.put("delete from ljsgetendorse where grppolno = '"
					+ mLPGrpPolSet.get(i).getGrpPolNo() + "'", "DELETE");
			mMap.put("delete from ljspayperson where grppolno = '"
					+ mLPGrpPolSet.get(i).getGrpPolNo() + "'", "DELETE");
		}
		LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
		LPGrpPolSet tLPGrpPolSet = tLPGrpPolDB
				.executeQuery("select * from lpgrppol where edorno = '"
						+ mLPGrpEdorItemSchema.getEdorNo()
						+ "' and edortype = '"
						+ mLPGrpEdorItemSchema.getEdorType()
						+ "' and grppolno not in (" + GrpPolNo + ")");
		if (tLPGrpPolSet == null || tLPGrpPolSet.size() == 0) {
			mMap.put("delete from lpedoritem where edorno = '"
					+ mLPGrpEdorItemSchema.getEdorNo() + "'", "DELETE");
			mMap.put("delete from lpedormain where edorno = '"
					+ mLPGrpEdorItemSchema.getEdorNo() + "'", "DELETE");
			mMap.put(
					"update lpgrpedoritem set edorstate = '3' where edorno = '"
							+ mLPGrpEdorItemSchema.getEdorNo() + "'", "UPDATE");
		} else {
			mMap
					.put(
							"update lpedoritem a set getmoney = (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and contno = a.contno) where edorno = '"
									+ mLPGrpEdorItemSchema.getEdorNo() + "'",
							"UPDATE");
			mMap
					.put(
							"update lpedormain a set getmoney = (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and contno = a.contno) where edorno = '"
									+ mLPGrpEdorItemSchema.getEdorNo() + "'",
							"UPDATE");
			mMap
					.put(
							"update lpgrpedoritem a set getmoney = (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and grpcontno = a.grpcontno) where edorno = '"
									+ mLPGrpEdorItemSchema.getEdorNo() + "'",
							"UPDATE");
		}
		return true;
	}

	private boolean insertOnePol() {
		if (mLPGrpPolSet.size() != 1) {
			CError.buildErr(this, "一次只能新增一条附加险!");
			return false;
		}

		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();

		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		tLMRiskAppDB.setRiskCode(mLPGrpPolSet.get(1).getRiskCode());
		if (!tLMRiskAppDB.getInfo()) {
			CError.buildErr(this, "查询险种失败!");
			return false;
		}

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "查询团体合同信息失败!");
			return false;
		}
		tLCGrpContSchema = tLCGrpContDB.getSchema();

		TransferData tTransferData = new TransferData();
		tTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (tTransferData == null) {
			CError.buildErr(this, "传入数据不完整，缺保险期间信息!");
			return false;
		}

		String InsuYear = (String) tTransferData.getValueByName("InsuYear");
		String InsuYearFlag = (String) tTransferData
				.getValueByName("InsuYearFlag");
		if (InsuYear == null || InsuYearFlag == null) {
			CError.buildErr(this, "传入保险期间信息不完整!");
			return false;
		}

		int InsuredYear = 0;
		String InsuredYearFlag = "";
		// ===upd===zhangtao===保险止期保险止期、缴费止期、交至日期处理==========BGN=============
		int iInputInsuYear = 0;
		try {
			iInputInsuYear = Integer.parseInt(InsuYear);
		} catch (Exception e) {
			CError.buildErr(this, "保险期间格式错误!");
			return false;
		}
		if ((iInputInsuYear <= 1 && StrTool.compareString(InsuYearFlag, "Y"))
				|| (iInputInsuYear <= 12 && StrTool.compareString(InsuYearFlag,
						"M"))) {// 短期附加险
			String CDate = tLCGrpContSchema.getCValiDate();
			CValidate = mLPGrpEdorItemSchema.getEdorValiDate();
			FDate tFD = new FDate();
			while (tFD.getDate(CDate).before(tFD.getDate(CValidate))) {
				CDate = PubFun.calDate(CDate, 1, "Y", null);
			}
			if (tFD.getDate(PubFun.calDate(CValidate, 30, "D", null)).after(
					tFD.getDate(CDate))) {
				CValidate = CDate;
				Enddate = PubFun.calDate(CValidate, 1, "Y", null);
			} else {
				Enddate = CDate;
			}
			Paytodate = Enddate;

			InsuredYear = PubFun.calInterval2(CValidate, Enddate, "M");
			if (InsuredYear == 12) {
				InsuredYearFlag = "Y";
				InsuredYear = 1;
			} else {
				InsuredYearFlag = "M";
			}
		} else { // 长期附加险
			InsuredYear = Integer.parseInt(InsuYear);
			InsuredYearFlag = InsuYearFlag;
			CValidate = mLPGrpEdorItemSchema.getEdorValiDate();
			Enddate = PubFun.calDate(CValidate, InsuredYear, InsuredYearFlag,
					null);
			if (StrTool.compareString(InsuredYearFlag, "Y") && InsuredYear > 1)
				Paytodate = PubFun.calDate(CValidate, 1, "Y", null);
			else
				Paytodate = Enddate;
		}
		// ===upd===zhangtao===保险止期保险止期、缴费止期、交至日期处理==========END=============
		String tLimit = PubFun.getNoLimit(tLCGrpContSchema.getManageCom());
		String tNo = PubFun1.CreateMaxNo("GrpProposalNo", tLimit);

		tLCGrpPolSchema.setGrpPolNo(tNo);
		tLCGrpPolSchema.setGrpContNo(tLCGrpContSchema.getGrpContNo());
		tLCGrpPolSchema.setGrpProposalNo(tNo);
		tLCGrpPolSchema.setPrtNo(tLCGrpContSchema.getPrtNo());
		tLCGrpPolSchema.setKindCode(tLMRiskAppDB.getKindCode());
		tLCGrpPolSchema.setRiskCode(tLMRiskAppDB.getRiskCode());
		tLCGrpPolSchema.setRiskVersion(tLMRiskAppDB.getRiskVer());
		tLCGrpPolSchema.setManageCom(tLCGrpContSchema.getManageCom());
		tLCGrpPolSchema.setSaleChnl(tLCGrpContSchema.getSaleChnl());
		tLCGrpPolSchema.setAgentCode(tLCGrpContSchema.getAgentCode());
		tLCGrpPolSchema.setAgentCode1(tLCGrpContSchema.getAgentCode1());
		tLCGrpPolSchema.setAgentCom(tLCGrpContSchema.getAgentCom());
		tLCGrpPolSchema.setAgentGroup(tLCGrpContSchema.getAgentGroup());
		tLCGrpPolSchema.setAgentType(tLCGrpContSchema.getAgentType());
		tLCGrpPolSchema.setCustomerNo(tLCGrpContSchema.getAppntNo());
		tLCGrpPolSchema.setAddressNo(tLCGrpContSchema.getAddressNo());
		tLCGrpPolSchema.setGrpName(tLCGrpContSchema.getGrpName());
		tLCGrpPolSchema.setFirstPayDate(CValidate);
		tLCGrpPolSchema.setPayEndDate(Enddate);
		tLCGrpPolSchema.setPaytoDate(Paytodate);
		tLCGrpPolSchema.setPayIntv(0);
		tLCGrpPolSchema.setAppFlag("2");
		tLCGrpPolSchema.setAmnt(mLPGrpPolSet.get(1).getAmnt());
		tLCGrpPolSchema.setMult(mLPGrpPolSet.get(1).getMult());
		tLCGrpPolSchema.setGetLimit(mLPGrpPolSet.get(1).getGetLimit());
		tLCGrpPolSchema.setGetRate(mLPGrpPolSet.get(1).getGetRate());
		tLCGrpPolSchema.setCValiDate(CValidate);
		tLCGrpPolSchema.setOperator(mGlobalInput.Operator);
		tLCGrpPolSchema.setMakeDate(CurrDate);
		tLCGrpPolSchema.setMakeTime(CurrTime);
		tLCGrpPolSchema.setModifyDate(CurrDate);
		tLCGrpPolSchema.setModifyTime(CurrTime);
		// 保存本次新增附加险的额度，查询时使用
		tLCGrpPolSchema.setStandbyFlag3(mLPGrpPolSet.get(1).getStandbyFlag3());
		// 暂时默认核保通过
		tLCGrpPolSchema.setUWFlag("9");
		tLCGrpPolSchema.setUWOperator(mGlobalInput.Operator);
		tLCGrpPolSchema.setUWDate(CurrDate);
		tLCGrpPolSchema.setUWTime(CurrTime);

		String StrSQL = "";
		Reflections tRef = new Reflections();

		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet mainLCPolSet = new LCPolSet();
		StrSQL = "select * from lcpol where 1=1 " + " and grpcontno = '"
				+ tLCGrpPolSchema.getGrpContNo() + "'" + " and grppolno = '"
				+ mLPGrpPolSet.get(1).getStandbyFlag1() + "'"
				+ " and riskcode = '" + mLPGrpPolSet.get(1).getStandbyFlag2()
				+ "'" + " and appflag = '1'" + " and mainpolno = polno";
		mainLCPolSet = tLCPolDB.executeQuery(StrSQL);
		if (mainLCPolSet == null || mainLCPolSet.size() < 1) {
			CError.buildErr(this, "查询个人主险信息失败!");
			return false;
		}

		// LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		// tLMEdorCalDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		// tLMEdorCalDB.setCalType("NSRATE");
		// tLMEdorCalDB.setRiskCode(tLCGrpPolSchema.getRiskCode());
		// tLMEdorCalDB.setDutyCode("000000");
		// if(!tLMEdorCalDB.getInfo()){
		// CError.buildErr(this,"查询短期费率计算编码表失败，此险种不能新增附加险!");
		// return false;
		// }
		//
		// Calculator tCal = new Calculator();
		// tCal.setCalCode(tLMEdorCalDB.getCalCode());
		// tCal.addBasicFactor("NSMonths",String.valueOf(PubFun.calInterval2(CValidate,Enddate,"M")));
		// String strRate = tCal.calculate();
		// tCal = null;
		// tLMEdorCalDB = null;
		//
		// tLMEdorCalDB = new LMEdorCalDB();
		// tLMEdorCalDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		// tLMEdorCalDB.setCalType("MinPep");
		// tLMEdorCalDB.setRiskCode(tLCGrpPolSchema.getRiskCode());
		// tLMEdorCalDB.setDutyCode("000000");
		// if(!tLMEdorCalDB.getInfo()){
		// CError.buildErr(this,"查询最低投保人数计算编码表失败，此险种不能新增附加险!");
		// return false;
		// }
		// tCal = new Calculator();
		// tCal.setCalCode(tLMEdorCalDB.getCalCode());
		// tCal.addBasicFactor("RiskCode",tLCGrpPolSchema.getRiskCode());
		// tCal.addBasicFactor("GrpContNo",tLCGrpPolSchema.getGrpContNo());
		// String strCount = tCal.calculate();
		// tCal = null;
		// tLMEdorCalDB = null;
		//
		// if(strRate == null || strRate.equals("") || strCount == null ||
		// strCount.equals("")){
		// CError.buildErr(this,"查询险种短期费率或最低投保人数失败!");
		// return false;
		// }
		double tRate, tCount;
		tRate = 1;
		tCount = 0;
		// try{
		// tRate = Double.parseDouble(strRate);
		// tCount = Double.parseDouble(strCount);
		// }
		// catch(Exception e){
		// CError.buildErr(this,"数据类型转换异常!");
		// return false;
		// }

		LCPolSet aLCPolSet = new LCPolSet();
		LPPolSet aLPPolSet = new LPPolSet();
		LCDutySet aLCDutySet = new LCDutySet();
		LPDutySet aLPDutySet = new LPDutySet();
		LCPremSet aLCPremSet = new LCPremSet();
		LPPremSet aLPPremSet = new LPPremSet();
		LCGetSet aLCGetSet = new LCGetSet();
		LPGetSet aLPGetSet = new LPGetSet();

		LPEdorMainSet aLPEdorMainSet = new LPEdorMainSet();
		LPEdorItemSet aLPEdorItemSet = new LPEdorItemSet();

		LJSGetEndorseSet aLJSGetEndorseSet = new LJSGetEndorseSet();
		LJSPayPersonSet aLJSPayPersonSet = new LJSPayPersonSet();

		LCPolSchema tLCPolSchema;
		LCDutySchema tLCDutySchema;
		LCPremSchema tLCPremSchema;
		LCGetSchema tLCGetSchema;
		LPPolSchema tLPPolSchema;
		LPDutySchema tLPDutySchema;
		LPPremSchema tLPPremSchema;
		LPGetSchema tLPGetSchema;

		LJSGetEndorseSchema tLJSGetEndorseSchema;
		LJSPayPersonSchema tLJSPayPersonSchema;

		LCPolBL tLCPolBL;
		LCDutyBL tLCDutyBL;
		CalBL tCalBL;
		BqCalBL tBqCalBL;

		String FeeType = BqCalBL.getFinType(mLPGrpEdorItemSchema.getEdorType(),
				"BF", "000000");
		if (FeeType == null || "".equals(FeeType)) {
			CError.buildErr(this, "查询财务类型编码失败!");
			return false;
		}

		int nCount = 0;
		double allPrem = 0;

		for (int i = 1; i <= mainLCPolSet.size(); i++) {
			int tInsuredAppAge = PubFun.calInterval(mainLCPolSet.get(i)
					.getInsuredBirthday(), CValidate, "Y");
			if (tInsuredAppAge > tLMRiskAppDB.getMaxInsuredAge()
					|| tInsuredAppAge < tLMRiskAppDB.getMinInsuredAge()) {
				// 此被保人不满足投保年龄
				logger.debug("此被保人不满足投保年龄!InsuredNo:"
						+ mainLCPolSet.get(i).getInsuredNo());
				continue;
			}

			LPEdorItemSchema tLPEdorItemSchema = createLPEdorItem(mainLCPolSet
					.get(i));
			if (tLPEdorItemSchema == null) {
				return false;
			}

			String PolNo = PubFun1.CreateMaxNo("PROPOSALNO", PubFun
					.getNoLimit(tLCGrpContSchema.getManageCom()));
			tLCPolSchema = new LCPolSchema();
			tLCPolSchema.setGrpContNo(tLCGrpPolSchema.getGrpContNo());
			tLCPolSchema.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
			tLCPolSchema.setPolNo(PolNo);
			tLCPolSchema.setProposalNo(PolNo);
			tLCPolSchema.setContNo(mainLCPolSet.get(i).getContNo());
			tLCPolSchema.setPrtNo(mainLCPolSet.get(i).getPrtNo());
			tLCPolSchema.setMainPolNo(mainLCPolSet.get(i).getPolNo());
			tLCPolSchema.setContType(mainLCPolSet.get(i).getContType());
			tLCPolSchema.setPolTypeFlag(mainLCPolSet.get(i).getPolTypeFlag());
			tLCPolSchema.setMainPolNo(mainLCPolSet.get(i).getMainPolNo());
			tLCPolSchema.setManageCom(tLCGrpPolSchema.getManageCom());
			tLCPolSchema.setKindCode(tLMRiskAppDB.getKindCode());
			tLCPolSchema.setRiskCode(tLMRiskAppDB.getRiskCode());
			tLCPolSchema.setRiskVersion(tLMRiskAppDB.getRiskVer());
			tLCPolSchema.setSaleChnl(tLCGrpContSchema.getSaleChnl());
			tLCPolSchema.setAgentCode(tLCGrpContSchema.getAgentCode());
			tLCPolSchema.setAgentCode1(tLCGrpContSchema.getAgentCode1());
			tLCPolSchema.setAgentCom(tLCGrpContSchema.getAgentCom());
			tLCPolSchema.setAgentGroup(tLCGrpContSchema.getAgentGroup());
			tLCPolSchema.setAgentType(tLCGrpContSchema.getAgentType());
			tLCPolSchema.setInsuredNo(mainLCPolSet.get(i).getInsuredNo());
			tLCPolSchema.setInsuredName(mainLCPolSet.get(i).getInsuredName());
			tLCPolSchema.setInsuredSex(mainLCPolSet.get(i).getInsuredSex());
			tLCPolSchema.setInsuredBirthday(mainLCPolSet.get(i)
					.getInsuredBirthday());
			tLCPolSchema.setInsuredAppAge(tInsuredAppAge);
			tLCPolSchema.setInsuredPeoples(1);
			tLCPolSchema.setOccupationType(mainLCPolSet.get(i)
					.getOccupationType());
			tLCPolSchema.setAppntNo(mainLCPolSet.get(i).getAppntNo());
			tLCPolSchema.setAppntName(mainLCPolSet.get(i).getAppntName());
			tLCPolSchema.setCValiDate(CValidate);
			tLCPolSchema.setAmnt(tLCGrpPolSchema.getAmnt());
			tLCPolSchema.setMult(tLCGrpPolSchema.getMult());
			tLCPolSchema.setFirstPayDate(CValidate);
			tLCPolSchema.setGetStartDate(CValidate);
			tLCPolSchema.setAppFlag("2");
			tLCPolSchema.setSignCom(mainLCPolSet.get(i).getManageCom());
			tLCPolSchema.setSignDate(CValidate);
			tLCPolSchema.setSignTime(CurrTime);
			tLCPolSchema.setSpecifyValiDate("Y");
			// if("M".equals(tLMRiskAppDB.getRiskPeriod()))
			tLCPolSchema.setPayEndYear(InsuredYear);
			tLCPolSchema.setPayEndYearFlag(InsuredYearFlag);
			tLCPolSchema.setPayIntv(0);
			tLCPolSchema.setInsuYear(InsuredYear);
			tLCPolSchema.setInsuYearFlag(InsuredYearFlag);

			// 暂时默认核保通过
			tLCPolSchema.setUWFlag("9");
			tLCPolSchema.setUWCode(mGlobalInput.Operator);
			tLCPolSchema.setUWDate(CurrDate);
			tLCPolSchema.setUWTime(CurrTime);

			tLCPolBL = new LCPolBL();
			tLCPolBL.setSchema(tLCPolSchema);
			tLCDutyBL = new LCDutyBL();
			LCDutySchema cLCDutySchema = new LCDutySchema();
			tRef.transFields(cLCDutySchema, tLCPolSchema);
			cLCDutySchema.setGetLimit(tLCGrpPolSchema.getGetLimit());
			cLCDutySchema.setGetRate(tLCGrpPolSchema.getGetRate());
			tLCDutyBL.setSchema(cLCDutySchema);
			LCDutyBLSet tLCDutyBLSet = new LCDutyBLSet();
			tLCDutyBLSet.add(tLCDutyBL);
			tCalBL = new CalBL(tLCPolBL, tLCDutyBLSet, mLPGrpEdorItemSchema
					.getEdorType());
			try {
				if (!tCalBL.calPol()) {
					logger.debug("此被保人不满足投保条件!InsuredNo:"
							+ mainLCPolSet.get(i).getInsuredNo());
					logger.debug("原因：" + tCalBL.mErrors.getFirstError());
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("此被保人不满足投保条件!InsuredNo:"
						+ mainLCPolSet.get(i).getInsuredNo());
				continue;
			}

			tLCPolSchema.setSchema(tCalBL.getLCPol());
			tLCDutyBLSet = tCalBL.getLCDuty();
			LCPremBLSet tLCPremBLSet = tCalBL.getLCPrem();
			LCGetBLSet tLCGetBLSet = tCalBL.getLCGet();

			tLCPolSchema.setPayEndDate(Enddate);
			tLCPolSchema.setEndDate(Enddate);
			tLCPolSchema.setPaytoDate(Paytodate);
			tLCPolSchema.setOperator(mGlobalInput.Operator);
			tLCPolSchema.setMakeDate(CurrDate);
			tLCPolSchema.setMakeTime(CurrTime);
			tLCPolSchema.setModifyDate(CurrDate);
			tLCPolSchema.setModifyTime(CurrTime);

			if (!checkField(tLCPolSchema)) {
				logger.debug("此被保人不满足投保条件!InsuredNo:"
						+ mainLCPolSet.get(i).getInsuredNo());
				logger.debug("原因：" + StrMsg);
				continue;
			}

			aLCPolSet.add(tLCPolSchema);

			tLPPolSchema = new LPPolSchema();
			tRef.transFields(tLPPolSchema, tLCPolSchema);
			tLPPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			aLPPolSet.add(tLPPolSchema);

			for (int j = 1; j <= tLCDutyBLSet.size(); j++) {
				tLCDutySchema = new LCDutySchema();
				tLCDutySchema.setSchema(tLCDutyBLSet.get(j));
				tLCDutySchema.setPayEndDate(Enddate);
				tLCDutySchema.setEndDate(Enddate);
				tLCDutySchema.setPaytoDate(Paytodate);
				tLCDutySchema.setOperator(mGlobalInput.Operator);
				tLCDutySchema.setMakeDate(CurrDate);
				tLCDutySchema.setMakeTime(CurrTime);
				tLCDutySchema.setModifyDate(CurrDate);
				tLCDutySchema.setModifyTime(CurrTime);
				aLCDutySet.add(tLCDutySchema);

				tLPDutySchema = new LPDutySchema();
				tRef.transFields(tLPDutySchema, tLCDutySchema);
				tLPDutySchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tLPDutySchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				aLPDutySet.add(tLPDutySchema);
			}

			for (int j = 1; j <= tLCPremBLSet.size(); j++) {
				tLCPremSchema = new LCPremSchema();
				tLCPremSchema.setSchema(tLCPremBLSet.get(j));
				tLCPremSchema.setPrem(tLCPremSchema.getPrem() * tRate);
				tLCPremSchema
						.setStandPrem(tLCPremSchema.getStandPrem() * tRate);
				tLCPremSchema.setPayEndDate(Enddate);
				tLCPremSchema.setPaytoDate(Paytodate);
				tLCPremSchema.setOperator(mGlobalInput.Operator);
				tLCPremSchema.setMakeDate(CurrDate);
				tLCPremSchema.setMakeTime(CurrTime);
				tLCPremSchema.setModifyDate(CurrDate);
				tLCPremSchema.setModifyTime(CurrTime);
				aLCPremSet.add(tLCPremSchema);

				tLPPremSchema = new LPPremSchema();
				tRef.transFields(tLPPremSchema, tLCPremSchema);
				tLPPremSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tLPPremSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				aLPPremSet.add(tLPPremSchema);

				tBqCalBL = new BqCalBL();
				tLJSGetEndorseSchema = new LJSGetEndorseSchema();
				tLJSGetEndorseSchema = tBqCalBL.initLJSGetEndorse(
						tLPEdorItemSchema, tLPPolSchema, tLPPremSchema
								.getDutyCode(), tLPPremSchema.getPayPlanCode(),
						BqCode.Pay_Prem, FeeType, tLPPremSchema.getPrem(),
						mGlobalInput);
				aLJSGetEndorseSet.add(tLJSGetEndorseSchema);

				tLJSPayPersonSchema = new LJSPayPersonSchema();
				tRef.transFields(tLJSPayPersonSchema, tLJSGetEndorseSchema);
				tLJSPayPersonSchema.setPayAimClass("2");
				tLJSPayPersonSchema.setPayCount(1);
				tLJSPayPersonSchema.setSumActuPayMoney(tLJSGetEndorseSchema
						.getGetMoney());
				tLJSPayPersonSchema.setSumDuePayMoney(tLJSGetEndorseSchema
						.getGetMoney());
				tLJSPayPersonSchema.setPayDate(mLPGrpEdorItemSchema
						.getEdorValiDate());
				tLJSPayPersonSchema.setPayType(mLPGrpEdorItemSchema
						.getEdorType());
				tLJSPayPersonSchema.setLastPayToDate(tLCPremSchema
						.getPayStartDate());
				tLJSPayPersonSchema.setCurPayToDate(tLCPremSchema
						.getPaytoDate());
				aLJSPayPersonSet.add(tLJSPayPersonSchema);

				tLPEdorItemSchema.setGetMoney(tLPEdorItemSchema.getGetMoney()
						+ tLJSPayPersonSchema.getSumDuePayMoney());
				allPrem += tLJSPayPersonSchema.getSumDuePayMoney();
			}

			for (int j = 1; j <= tLCGetBLSet.size(); j++) {
				tLCGetSchema = new LCGetSchema();
				tLCGetSchema.setSchema(tLCGetBLSet.get(j));
				tLCGetSchema.setGetEndDate(Enddate);
				tLCGetSchema.setOperator(mGlobalInput.Operator);
				tLCGetSchema.setMakeDate(CurrDate);
				tLCGetSchema.setMakeTime(CurrTime);
				tLCGetSchema.setModifyDate(CurrDate);
				tLCGetSchema.setModifyTime(CurrTime);
				aLCGetSet.add(tLCGetSchema);

				tLPGetSchema = new LPGetSchema();
				tRef.transFields(tLPGetSchema, tLCGetSchema);
				tLPGetSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tLPGetSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				aLPGetSet.add(tLPGetSchema);
			}

			tLPEdorItemSchema.setEdorState("1");
			LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
			tRef.transFields(tLPEdorMainSchema, mLPGrpEdorItemSchema);
			tRef.transFields(tLPEdorMainSchema, tLPEdorItemSchema);

			aLPEdorMainSet.add(tLPEdorMainSchema);
			aLPEdorItemSet.add(tLPEdorItemSchema);

			nCount++;
		}

		if (nCount == 0 || nCount < tCount) {
			CError.buildErr(this, "不符合最低投保人数!");
			return false;
		}

		tLCGrpPolSchema.setPeoples2(nCount);
		tLCGrpPolSchema.setPrem(allPrem);
		LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
		tRef.transFields(tLPGrpPolSchema, tLCGrpPolSchema);
		tLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());

		mMap.put(tLCGrpPolSchema, "INSERT");
		mMap.put(aLCPolSet, "INSERT");
		mMap.put(aLCDutySet, "INSERT");
		mMap.put(aLCPremSet, "INSERT");
		mMap.put(aLCGetSet, "INSERT");

		mMap.put(tLPGrpPolSchema, "INSERT");
		mMap.put(aLPPolSet, "INSERT");
		mMap.put(aLPDutySet, "INSERT");
		mMap.put(aLPPremSet, "INSERT");
		mMap.put(aLPGetSet, "INSERT");

		mMap.put(aLJSGetEndorseSet, "INSERT");
		mMap.put(aLJSPayPersonSet, "INSERT");

		mMap.put(aLPEdorMainSet, "DELETE&INSERT");
		mMap.put(aLPEdorItemSet, "DELETE&INSERT");

		mMap
				.put(
						"update lpgrpedoritem a set getmoney = (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno) where edorno = '"
								+ mLPGrpEdorItemSchema.getEdorNo() + "'",
						"UPDATE");

		mMap.put("delete from lpcont where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "'", "DELETE");
		mMap
				.put(
						"insert into lpcont (select '"
								+ mLPGrpEdorItemSchema.getEdorNo()
								+ "','"
								+ mLPGrpEdorItemSchema.getEdorType()
								+ "',a.* from lccont a where contno in (select contno from lpedoritem where edorno = '"
								+ mLPGrpEdorItemSchema.getEdorNo() + "'))",
						"INSERT");

		return true;
	}

	private LPEdorItemSchema createLPEdorItem(LCPolSchema mainLCPolSchema) {
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		Reflections tRef = new Reflections();

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPEdorItemDB.setContNo(mainLCPolSchema.getContNo());
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null) {
			CError.buildErr(this, "查询保全信息错误!");
			return null;
		} else if (tLPEdorItemSet.size() == 0) {
			tRef.transFields(tLPEdorItemSchema, mLPGrpEdorItemSchema);
			tLPEdorItemSchema.setInsuredNo(mainLCPolSchema.getInsuredNo());
			tLPEdorItemSchema.setContNo(mainLCPolSchema.getContNo());
			tLPEdorItemSchema.setPolNo(mainLCPolSchema.getPolNo());
			tLPEdorItemSchema.setGetMoney(0);
			return tLPEdorItemSchema;
		} else
			return tLPEdorItemSet.get(1);
	}

	private boolean checkField(LCPolSchema cLCPolSchema) {
		CheckFieldCom tCheckFieldCom = new CheckFieldCom();

		// 计算要素
		FieldCarrier tFieldCarrier = new FieldCarrier();
		tFieldCarrier.setPrem(cLCPolSchema.getPrem()); // 保费
		tFieldCarrier.setInsuredNo(cLCPolSchema.getInsuredNo());
		tFieldCarrier.setAppAge(cLCPolSchema.getInsuredAppAge()); // 被保人年龄
		tFieldCarrier.setInsuredName(cLCPolSchema.getInsuredName()); // 被保人姓名
		tFieldCarrier.setSex(cLCPolSchema.getInsuredSex()); // 被保人性别
		tFieldCarrier.setMult(cLCPolSchema.getMult()); // 投保份数
		tFieldCarrier.setPolNo(cLCPolSchema.getPolNo()); // 投保单号码
		tFieldCarrier.setMainPolNo(cLCPolSchema.getMainPolNo()); // 主险号码
		tFieldCarrier.setRiskCode(cLCPolSchema.getRiskCode()); // 险种编码
		tFieldCarrier.setCValiDate(cLCPolSchema.getCValiDate()); // 生效日期
		tFieldCarrier.setAmnt(cLCPolSchema.getAmnt()); // 保额
		tFieldCarrier.setInsuredBirthday(cLCPolSchema.getInsuredBirthday()); // 被保人出生日期
		tFieldCarrier.setInsuYear(cLCPolSchema.getInsuYear()); // 保险期间
		tFieldCarrier.setInsuYearFlag(cLCPolSchema.getInsuYearFlag()); // 保险期间单位
		tFieldCarrier.setPayEndYear(cLCPolSchema.getPayEndYear()); // 交费期间
		tFieldCarrier.setPayEndYearFlag(cLCPolSchema.getPayEndYearFlag()); // 交费期间单位
		tFieldCarrier.setPayIntv(cLCPolSchema.getPayIntv()); // 交费方式
		tFieldCarrier.setPayYears(cLCPolSchema.getPayYears()); // 交费年期
		tFieldCarrier.setOccupationType(cLCPolSchema.getOccupationType()); // 被保人职业类别
		tFieldCarrier.setGetYear(cLCPolSchema.getGetYear()); // 领取年龄
		tFieldCarrier.setGrpPolNo(cLCPolSchema.getGrpPolNo());
		tFieldCarrier.setContNo(cLCPolSchema.getContNo());
		tFieldCarrier.setEndDate(cLCPolSchema.getEndDate());
		tFieldCarrier.setPolTypeFlag(cLCPolSchema.getPolTypeFlag());
		// tFieldCarrier.setGetLimit("");
		// tFieldCarrier.setGetDutyKind("");
		tFieldCarrier.setOccupationCode("");
		tFieldCarrier.setDutyCode("");
		tFieldCarrier.setStandbyFlag1(cLCPolSchema.getStandbyFlag1());
		tFieldCarrier.setStandbyFlag2(cLCPolSchema.getStandbyFlag2());
		tFieldCarrier.setStandbyFlag3(cLCPolSchema.getStandbyFlag3());
		tFieldCarrier.setManageCom(cLCPolSchema.getManageCom());

		mInputData.clear();
		mInputData.add(tFieldCarrier);

		LMCheckFieldSchema tLMCheckFieldSchema = new LMCheckFieldSchema();
		tLMCheckFieldSchema.setRiskCode(cLCPolSchema.getRiskCode());
		tLMCheckFieldSchema.setFieldName("TBINSERT"); // 投保
		mInputData.add(tLMCheckFieldSchema);
		if (!tCheckFieldCom.CheckField(mInputData)) {
			return false;
		} else {
			boolean MsgFlag = false;
			StrMsg = "";
			LMCheckFieldSet mLMCheckFieldSet = tCheckFieldCom
					.GetCheckFieldSet();
			for (int n = 1; n <= mLMCheckFieldSet.size(); n++) {
				LMCheckFieldSchema tField = mLMCheckFieldSet.get(n);
				if ((tField.getReturnValiFlag() != null)
						&& tField.getReturnValiFlag().equals("N")) {
					if ((tField.getMsgFlag() != null)
							&& tField.getMsgFlag().equals("Y")) {
						MsgFlag = true;
						StrMsg = StrMsg + tField.getMsg() + " ; ";
						break;
					}
				}
			}
			if (MsgFlag == true) {
				return false;
			}
		}

		return true;
	}

	private boolean InsertImpart() {
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "查询团体合同信息失败!");
			return false;
		}
		LCGrpContSchema tLCGrpContSchema = tLCGrpContDB.getSchema();

		// 健康告知处理
		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			LCCustomerImpartSchema aLCCustomerImpartSchema = new LCCustomerImpartSchema();
			for (int k = 1; k <= mLCCustomerImpartSet.size(); k++) {
				aLCCustomerImpartSchema = mLCCustomerImpartSet.get(k);
				aLCCustomerImpartSchema.setGrpContNo(mLPGrpEdorItemSchema
						.getGrpContNo());
				aLCCustomerImpartSchema.setPrtNo(tLCGrpContSchema.getPrtNo());
				aLCCustomerImpartSchema.setCustomerNoType("I");
				aLCCustomerImpartSchema.setProposalContNo(tLCGrpContSchema
						.getProposalGrpContNo());
				mLCCustomerImpartSet.set(k, aLCCustomerImpartSchema);
				logger.debug(mLCCustomerImpartSet.get(k).getCustomerNo());
			}
		}

		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			VData cVData = new VData();
			cVData.add(mLCCustomerImpartSet);
			cVData.add(mGlobalInput);
			CustomerImpartBL tCustomerImpartBL = new CustomerImpartBL();
			if (!tCustomerImpartBL.submitData(cVData, "IMPART||DEAL")) {
				CError
						.buildErr(this, tCustomerImpartBL.mErrors
								.getFirstError());
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
			LPCustomerImpartParamsSchema tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
			LPCustomerImpartSchema tLPCustomerImpartSchema = new LPCustomerImpartSchema();
			Reflections tRef = new Reflections();
			mLPCustomerImpartSet = new LPCustomerImpartSet();
			for (int i = 1; i <= tLCCustomerImpartSet.size(); i++) {
				tLPCustomerImpartSchema = new LPCustomerImpartSchema();
				tRef.transFields(tLPCustomerImpartSchema, tLCCustomerImpartSet
						.get(i));
				tLPCustomerImpartSchema.setEdorNo(mLPGrpEdorItemSchema
						.getEdorNo());
				tLPCustomerImpartSchema.setEdorType(mLPGrpEdorItemSchema
						.getEdorType());
				tLPCustomerImpartSchema.setGrpContNo(mLPGrpEdorItemSchema
						.getGrpContNo());
				tLPCustomerImpartSchema.setPrtNo(tLCGrpContSchema.getPrtNo());
				tLPCustomerImpartSchema.setProposalContNo(tLCGrpContSchema
						.getProposalGrpContNo());
				mLPCustomerImpartSet.add(tLPCustomerImpartSchema);
			}
			mLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();
			for (int i = 1; i <= tLCCustomerImpartParamsSet.size(); i++) {
				tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
				tRef.transFields(tLPCustomerImpartParamsSchema,
						tLCCustomerImpartParamsSet.get(i));

				tLPCustomerImpartParamsSchema.setEdorNo(mLPGrpEdorItemSchema
						.getEdorNo());
				tLPCustomerImpartParamsSchema.setEdorType(mLPGrpEdorItemSchema
						.getEdorType());
				tLPCustomerImpartParamsSchema.setGrpContNo(mLPGrpEdorItemSchema
						.getGrpContNo());
				tLPCustomerImpartParamsSchema.setPrtNo(tLCGrpContSchema
						.getPrtNo());
				tLPCustomerImpartParamsSchema
						.setProposalContNo(tLCGrpContSchema
								.getProposalGrpContNo());
				mLPCustomerImpartParamsSet.add(tLPCustomerImpartParamsSchema);
			}

			mMap.put(mLPCustomerImpartSet, "DELETE&INSERT");
			mMap.put(mLPCustomerImpartParamsSet, "DELETE&INSERT");

		}
		return true;
	}

	private boolean prepareData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
