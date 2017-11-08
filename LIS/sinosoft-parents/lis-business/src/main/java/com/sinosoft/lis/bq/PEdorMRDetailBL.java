package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJAGetDrawDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.tb.CalBL;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 主险续保处理
 * </p>
 * 
 * <p>
 * Description: 主险续保明细处理类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 */
public class PEdorMRDetailBL {
private static Logger logger = Logger.getLogger(PEdorMRDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private MMap mMap = new MMap();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();

	private BqCalBase mBqCalBase = new BqCalBase();

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

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
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			if (mLPEdorItemSchema == null || mGlobalInput == null) {
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		if (!tLPEdorItemDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PInsuredBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "无保全申请数据!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);
			return false;
		}
		// 将查询出来的保全主表数据保存至模块变量中，省去其它的重复查询
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单表信息失败!");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();

		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolDB.setContNo(mLCContSchema.getContNo());
		tLCPolDB.setRiskCode("00609000");
		tLCPolSet = tLCPolDB.query();
		if (tLCPolSet == null || tLCPolSet.size() < 1) {
			mErrors.addOneError("查询险种表失败!");
			return false;
		}
		mLCPolSchema = tLCPolSet.get(1);
		return true;
	}

	private boolean dealData() {
		Reflections tRef = new Reflections();
		String tPolNo = "";

		LPPolDB bLPPolDB = new LPPolDB();
		LPPolSet bLPPolSet = new LPPolSet();
		LPPolSchema bLPPolSchema = new LPPolSchema();
		bLPPolDB.setRiskCode("00609000");
		bLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		bLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		bLPPolSet = bLPPolDB.query();
		if (bLPPolSet != null && bLPPolSet.size() > 0) {
			tPolNo = bLPPolSet.get(1).getPolNo();
		} else {
			// 产生新险种号
			String tLimit = PubFun.getNoLimit(mLPEdorItemSchema.getManageCom());
			tPolNo = PubFun1.CreateMaxNo("POLNO", tLimit);
		}
		// 计算投保年龄
		String Birthday = mLCPolSchema.getInsuredBirthday();
		int tAppAge = PubFun.calInterval(Birthday, mLPEdorItemSchema
				.getEdorValiDate(), "Y");

		String OldEnd = mLCPolSchema.getEndDate();
		String CValiDate = mLPEdorItemSchema.getEdorValiDate();
		CValiDate = PubFun.calDate(CValiDate, 1, "D", null);
		String EndDate = PubFun.calDate(CValiDate, 3, "Y", null);

		LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();
		LJAGetDrawSet tLJAGetDrawSet = new LJAGetDrawSet();
		String str = "select * from LJAGetDraw where polno = '?polno?' and confdate is null order by getdate desc";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(str);
		sqlbv.put("polno", mLCPolSchema.getPolNo());
		tLJAGetDrawSet = tLJAGetDrawDB.executeQuery(sqlbv);
		if (tLJAGetDrawSet == null || tLJAGetDrawSet.size() < 1) {
			mErrors.addOneError("查询生存领取给付表失败!");
			return false;
		}

		double EPrem = tLJAGetDrawSet.get(1).getGetMoney();

		LPPolSchema aLPPolSchema = new LPPolSchema();
		LCPolSchema aLCPolSchema = new LCPolSchema();
		tRef.transFields(aLPPolSchema, mLCPolSchema);
		aLPPolSchema.setInsuredAppAge(tAppAge);
		aLPPolSchema.setPolNo(tPolNo);
		aLPPolSchema.setMainPolNo(tPolNo);
		aLPPolSchema.setPrem(tLJAGetDrawSet.get(1).getGetMoney());
		aLPPolSchema.setStandPrem(aLPPolSchema.getPrem());
		aLPPolSchema.setYears(3);
		aLPPolSchema.setCValiDate(CValiDate);
		aLPPolSchema.setEndDate(EndDate);
		aLPPolSchema.setAmnt(0);
		aLPPolSchema.setMult(0);
		aLPPolSchema.setInsuYear(3);
		aLPPolSchema.setPayYears(3);
		aLPPolSchema.setPremToAmnt("Y");
		aLPPolSchema.setGetYear(3);
		tRef.transFields(aLCPolSchema, aLPPolSchema);

		LCPolBL aLCPolBL = new LCPolBL();
		aLCPolBL.setSchema(aLCPolSchema);

		LCDutyBLSet aLCDutyBLSet = new LCDutyBLSet();
		LCDutyDB aLCDutyDB = new LCDutyDB();
		aLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
		aLCDutyBLSet.add(aLCDutyDB.query());
		aLCDutyBLSet.get(1).setPolNo(tPolNo);
		aLCDutyBLSet.get(1).setAmnt(0);
		aLCDutyBLSet.get(1).setMult(0);
		aLCDutyBLSet.get(1).setPrem(aLCPolSchema.getPrem());
		aLCDutyBLSet.get(1).setStandPrem(aLCPolSchema.getPrem());
		aLCDutyBLSet.get(1).setYears(3);
		aLCDutyBLSet.get(1).setCValiDate(CValiDate);
		aLCDutyBLSet.get(1).setEndDate(EndDate);
		aLCDutyBLSet.get(1).setInsuYear(3);
		aLCDutyBLSet.get(1).setPayYears(3);
		aLCDutyBLSet.get(1).setPremToAmnt("Y");
		aLCDutyBLSet.get(1).setGetYear(3);

		LCGetBLSet aLCGetBLSet = new LCGetBLSet();
		LCGetDB aLCGetDB = new LCGetDB();
		aLCGetDB.setPolNo(mLCPolSchema.getPolNo());
		aLCGetDB.setGetDutyCode("609040");
		aLCGetBLSet.add(aLCGetDB.query());
		aLCGetBLSet.get(1).setPolNo(tPolNo);

		CalBL aCalBL = new CalBL(aLCPolBL, aLCDutyBLSet, aLCGetBLSet, "MR");
		aCalBL.calPol();

		LPPolSchema tLPPolSchema = new LPPolSchema();
		aLCPolBL = aCalBL.getLCPol();
		tRef.transFields(tLPPolSchema, aLCPolBL.getSchema());
		tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSchema.setCValiDate(CValiDate);
		tLPPolSchema.setEndDate(EndDate);
		tLPPolSchema.setPayEndYear(3);
		tLPPolSchema.setInsuYear(3);
		tLPPolSchema.setYears(3);
		tLPPolSchema.setGetYear(3);
		tLPPolSchema.setPayYears(3);
		tLPPolSchema.setMult(EPrem / 1000);
		tLPPolSchema.setPayEndDate(EndDate);
		tLPPolSchema.setPaytoDate(EndDate);
		tLPPolSchema.setGetStartDate(CValiDate);
		tLPPolSchema.setPrem(EPrem);
		tLPPolSchema.setStandPrem(EPrem);
		tLPPolSchema.setSumPrem(EPrem);
		tLPPolSchema.setRenewCount(1);
		tLPPolSchema.setMainPolNo(tLPPolSchema.getPolNo());

		aLCDutyBLSet = aCalBL.getLCDuty();
		aLCGetBLSet = aCalBL.getLCGet();
		LCPremBLSet aLCPremBLSet = new LCPremBLSet();
		aLCPremBLSet = aCalBL.getLCPrem();

		for (int i = 1; i <= aLCDutyBLSet.size(); i++) {
			LPDutySchema iLPDutySchema = new LPDutySchema();
			tRef.transFields(iLPDutySchema, aLCDutyBLSet.get(i));
			iLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			iLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
			iLPDutySchema.setModifyDate(PubFun.getCurrentDate());
			iLPDutySchema.setModifyTime(PubFun.getCurrentTime());
			iLPDutySchema.setMult(EPrem / 1000);
			iLPDutySchema.setCValiDate(CValiDate);
			iLPDutySchema.setEndDate(EndDate);
			iLPDutySchema.setPayEndYear(3);
			iLPDutySchema.setInsuYear(3);
			iLPDutySchema.setYears(3);
			iLPDutySchema.setGetYear(3);
			iLPDutySchema.setPayYears(3);
			iLPDutySchema.setPayEndDate(EndDate);
			iLPDutySchema.setPaytoDate(EndDate);
			iLPDutySchema.setGetStartDate(CValiDate);
			iLPDutySchema.setPrem(EPrem);
			iLPDutySchema.setStandPrem(EPrem);
			iLPDutySchema.setSumPrem(EPrem);
			mLPDutySet.add(iLPDutySchema);
		}

		for (int i = 1; i <= aLCPremBLSet.size(); i++) {
			LPPremSchema iLPPremSchema = new LPPremSchema();
			LCPremSchema iLCPremSchema = new LCPremSchema();
			iLCPremSchema.setSchema(aLCPremBLSet.get(i));
			tRef.transFields(iLPPremSchema, iLCPremSchema);
			iLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			iLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			iLPPremSchema.setPayStartDate(CValiDate);
			iLPPremSchema.setPaytoDate(EndDate);
			iLPPremSchema.setPayEndDate(EndDate);
			iLPPremSchema.setMakeDate(PubFun.getCurrentDate());
			iLPPremSchema.setMakeTime(PubFun.getCurrentTime());
			iLPPremSchema.setModifyDate(PubFun.getCurrentDate());
			iLPPremSchema.setModifyTime(PubFun.getCurrentTime());
			iLPPremSchema.setOperator(mGlobalInput.Operator);
			iLPPremSchema.setPrem(EPrem);
			iLPPremSchema.setStandPrem(EPrem);
			iLPPremSchema.setSumPrem(EPrem);
			mLPPremSet.add(iLPPremSchema);
		}

		for (int i = 1; i <= aLCGetBLSet.size(); i++) {
			LPGetSchema iLPGetSchema = new LPGetSchema();
			LCGetSchema iLCGetSchema = new LCGetSchema();
			iLCGetSchema.setSchema(aLCGetBLSet.get(i));
			tRef.transFields(iLPGetSchema, iLCGetSchema);
			iLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			iLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			iLPGetSchema.setGettoDate(EndDate);
			iLPGetSchema.setGetStartDate(EndDate);
			iLPGetSchema.setGetEndDate(EndDate);
			iLPGetSchema.setMakeDate(PubFun.getCurrentDate());
			iLPGetSchema.setMakeTime(PubFun.getCurrentTime());
			iLPGetSchema.setModifyDate(PubFun.getCurrentDate());
			iLPGetSchema.setModifyTime(PubFun.getCurrentTime());
			iLPGetSchema.setOperator(mGlobalInput.Operator);
			mLPGetSet.add(iLPGetSchema);
		}

		LPContSchema tLPContSchema = new LPContSchema();
		tRef.transFields(tLPContSchema, mLCContSchema);
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setPaytoDate(EndDate);
		tLPContSchema.setPrem(EPrem);
		tLPContSchema.setSumPrem(EPrem);
		tLPContSchema.setCValiDate(CValiDate);
		tLPContSchema.setAmnt(tLPContSchema.getAmnt() - mLCPolSchema.getAmnt()
				+ tLPPolSchema.getAmnt());
		tLPContSchema.setMult(tLPContSchema.getMult() - mLCPolSchema.getMult()
				+ EPrem / 1000);
		tLPContSchema.setPaytoDate(EndDate);

		mMap.put(tLPContSchema, "DELETE&INSERT");
		mMap.put(tLPPolSchema, "DELETE&INSERT");
		mMap.put(mLPDutySet, "DELETE&INSERT");
		mMap.put(mLPPremSet, "DELETE&INSERT");
		mMap.put(mLPGetSet, "DELETE&INSERT");

		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		mResult.clear();
		mResult.add(mMap);

		return true;
	}

	public PEdorMRDetailBL() {
	}
}
