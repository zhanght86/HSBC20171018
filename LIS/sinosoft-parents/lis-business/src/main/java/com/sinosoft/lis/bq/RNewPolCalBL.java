package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCDutyBL;
import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.tb.CalBL;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

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
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 */
public class RNewPolCalBL {
private static Logger logger = Logger.getLogger(RNewPolCalBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private String mOperate;

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 保单 */
	private LCPolBL mLCPolBL = new LCPolBL();

	/** 责任 */
	private LCDutyBL mLCDutyBL = new LCDutyBL();
	private LCDutyBLSet mLCDutyBLSet = new LCDutyBLSet();

	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCGetSet mLCGetSet = new LCGetSet();
	private LCDutySet mLCDutySet = new LCDutySet();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private Reflections mRef = new Reflections();

	private double RNewPrem = 0;

	public RNewPolCalBL() {
	}

	public boolean RNPolCalBL(LPPolSchema aLPPolSchema) {
		LCPolSchema aLCPolSchema = new LCPolSchema();
		mRef.transFields(aLCPolSchema, aLPPolSchema);
		mLCPolBL.setSchema(aLCPolSchema);

		// 保全项目减保与现保额对比。取最小值
		SSRS nSSRS = new SSRS();
		String tsql = "select Amnt from LPRNPolAmnt where polno = '?polno?' and edorno = '?edorno?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tsql);
		sqlbv.put("polno", aLCPolSchema.getPolNo());
		sqlbv.put("edorno", aLPPolSchema.getEdorNo());
		ExeSQL zExeSQL = new ExeSQL();
		nSSRS = zExeSQL.execSQL(sqlbv);
		if (nSSRS.getMaxRow() > 0) {
			String Amnt = nSSRS.GetText(1, 1);
			double tAmnt = Double.parseDouble(Amnt);
			if (tAmnt > mLCPolBL.getAmnt())
				mLCPolBL.setAmnt(tAmnt);
		}
		// 自动续保生效日对应主险续期时的缴费对应日
		mLCPolBL.setCValiDate(mLCPolBL.getPaytoDate());

		// 自动续保保费保额计算要素变化
		// 被保人年龄
		int tInsuredAge = PubFun.calInterval(mLCPolBL.getInsuredBirthday(),
				mLCPolBL.getCValiDate(), "Y");

		mLCPolBL.setInsuredAppAge(tInsuredAge);
		mLCPolBL.setMakeDate(CurrentDate);
		mLCPolBL.setMakeTime(CurrentTime);
		mLCPolBL.setModifyDate(CurrentDate);
		mLCPolBL.setModifyTime(CurrentTime);
		mLCPolBL.setRenewCount(mLCPolBL.getRenewCount() + 1);
		mLCPolBL.setRnewFlag(-1); // 自动续保标志
		logger.debug("CValiDate=========" + mLCPolBL.getCValiDate());

		LCDutyDB tLCDutyDB = new LCDutyDB();
		LCDutySet tLCDutySet = new LCDutySet();
		tsql = "select * from lcduty where polno ='?polno?'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tsql);
		sqlbv1.put("polno", aLCPolSchema.getPolNo());
		tLCDutySet = tLCDutyDB.executeQuery(sqlbv1);
		tLCDutySet.get(1).setPrem(mLCPolBL.getPrem());
		tLCDutySet.get(1).setStandPrem(mLCPolBL.getPrem());
		mLCDutyBLSet.add(tLCDutySet);

		// 通过保额计算保费
		CalBL tCalBL;
		tCalBL = new CalBL(mLCPolBL, mLCDutyBLSet, "");
		if (tCalBL.calPol() == false) {
			CError.buildErr(this, "险种保单重新计算时失败:"
					+ tCalBL.mErrors.getFirstError());
			return false;
		}
		mLCPolSchema = tCalBL.getLCPol().getSchema();
		RNewPrem += mLCPolSchema.getPrem();
		return true;
	}

	public boolean RNPolCalBL(LCPolSchema aLCPolSchema) {

		mLCPolBL.setSchema(aLCPolSchema);

		// 保全项目减保与现保额对比。取最小值
		SSRS nSSRS = new SSRS();
		String tsql = "select Amnt from LPRNPolAmnt where polno = '?polno?' and State ='1'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tsql);
		sqlbv2.put("polno", aLCPolSchema.getPolNo());
		ExeSQL zExeSQL = new ExeSQL();
		nSSRS = zExeSQL.execSQL(sqlbv2);
		if (nSSRS.getMaxRow() > 0) {
			String Amnt = nSSRS.GetText(1, 1);
			double tAmnt = Double.parseDouble(Amnt);
			if (tAmnt > mLCPolBL.getAmnt())
				mLCPolBL.setAmnt(tAmnt);
		}
		// 自动续保生效日对应主险续期时的缴费对应日
		mLCPolBL.setCValiDate(mLCPolBL.getPaytoDate());

		// 自动续保保费保额计算要素变化
		// 被保人年龄
		int tInsuredAge = PubFun.calInterval(mLCPolBL.getInsuredBirthday(),
				mLCPolBL.getCValiDate(), "Y");

		mLCPolBL.setInsuredAppAge(tInsuredAge);
		mLCPolBL.setMakeDate(CurrentDate);
		mLCPolBL.setMakeTime(CurrentTime);
		mLCPolBL.setModifyDate(CurrentDate);
		mLCPolBL.setModifyTime(CurrentTime);
		mLCPolBL.setRenewCount(mLCPolBL.getRenewCount() + 1);
		mLCPolBL.setRnewFlag(-1); // 自动续保标志
		logger.debug("CValiDate=========" + mLCPolBL.getCValiDate());

		LCDutyDB tLCDutyDB = new LCDutyDB();
		LCDutySet tLCDutySet = new LCDutySet();
		tsql = "select * from lcduty where polno ='?polno?'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tsql);
		sqlbv3.put("polno", aLCPolSchema.getPolNo());
		tLCDutySet = tLCDutyDB.executeQuery(sqlbv3);
		tLCDutySet.get(1).setStandPrem(mLCPolBL.getStandPrem());
		tLCDutySet.get(1).setPrem(mLCPolBL.getStandPrem());
		mLCDutyBLSet.add(tLCDutySet);

		// 通过保额计算保费
		CalBL tCalBL;
		tCalBL = new CalBL(mLCPolBL, mLCDutyBLSet, "");
		if (tCalBL.calPol() == false) {
			CError.buildErr(this, "险种保单重新计算时失败:"
					+ tCalBL.mErrors.getFirstError());
			return false;
		}
		mLCPolSchema = tCalBL.getLCPol().getSchema();
		double AddJK = 0;
		double AddZY = 0;
		AddJK = this.getJKSuperRisk(aLCPolSchema);
		AddZY = this.getZYSuperRisk(aLCPolSchema);
		RNewPrem += (1 + AddJK / 100 + AddZY / 100) * mLCPolSchema.getPrem();
		return true;
	}

	public boolean RNewContCalBL(LCContSchema aLCContSchema) {
		String str = "select * from lcpol where contno = '?contno?' and rnewflag <> -2";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(str);
		sqlbv4.put("contno", aLCContSchema.getContNo());
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = tLCPolDB.executeQuery(sqlbv4);
		if (tLCPolSet == null || tLCPolSet.size() < 1) {
			mErrors.addOneError("查询续保险种失败!");
			return false;
		}
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(1);
			RNPolCalBL(tLCPolSchema);
		}
		return true;
	}

	public boolean RNewContCalBL(LPContSchema aLPContSchema) {
		String str = "select * from lcpol where contno = '?contno?' and rnewflag <> -2";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(str);
		sqlbv5.put("contno", aLPContSchema.getContNo());
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = tLCPolDB.executeQuery(sqlbv5);
		if (tLCPolSet == null || tLCPolSet.size() < 1) {
			mErrors.addOneError("查询续保险种失败!");
			return false;
		}
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			LPPolSchema tLPPolSchema = new LPPolSchema();
			tLCPolSchema = tLCPolSet.get(1);
			mRef.transFields(tLPPolSchema, tLCPolSchema);
			RNPolCalBL(tLCPolSchema);
		}
		return true;
	}

	public double getRNewPrem() {
		return RNewPrem;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (getInputData(cInputData) == false) {
			return false;
		}
		// 数据操作校验
		if (checkData() == false) {
			return false;
		}

		// 进行业务处理
		if (dealData() == false) {
			return false;
		}
		if (prepareOutputData() == false) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {

		mLCPolBL.setSchema((LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0));
		// mLCDutyBLSet = (LCDutyBLSet) cInputData.getObjectByObjectName(
		// "LCDutyBLSet", 0);
		return true;
	}

	private boolean checkData() {

		return true;
	}

	private boolean dealData() {

		String tLimit = "";
		// 原来的保单号
		String SPolNo = mLCPolBL.getPolNo();

		String tsql;

		// 保全项目减保与现保额对比。取最小值
		// SSRS nSSRS = new SSRS();
		// String tsql = "select Amnt from LPRNPolAmnt where polno = '" + SPolNo
		// +
		// "' and State ='1'";
		// ExeSQL zExeSQL = new ExeSQL();
		// nSSRS = zExeSQL.execSQL(tsql);
		// if (nSSRS.getMaxRow() > 0) {
		// String Amnt = nSSRS.GetText(1, 1);
		// double tAmnt = Double.parseDouble(Amnt);
		// if (tAmnt > mLCPolBL.getAmnt())
		// mLCPolBL.setAmnt(tAmnt);
		// }
		// 自动续保生效日对应主险续期时的缴费对应日
		mLCPolBL.setCValiDate(mLCPolBL.getPaytoDate());

		// 自动续保保费保额计算要素变化
		// 被保人年龄
		int tInsuredAge = PubFun.calInterval(mLCPolBL.getInsuredBirthday(),
				mLCPolBL.getCValiDate(), "Y");

		mLCPolBL.setInsuredAppAge(tInsuredAge);
		mLCPolBL.setMakeDate(CurrentDate);
		mLCPolBL.setMakeTime(CurrentTime);
		mLCPolBL.setModifyDate(CurrentDate);
		mLCPolBL.setModifyTime(CurrentTime);
		mLCPolBL.setRenewCount(mLCPolBL.getRenewCount() + 1);
		mLCPolBL.setRnewFlag(-1); // 自动续保标志
		logger.debug("ssssssssssss=========" + mLCPolBL.getCValiDate());
		// 通过险种号查询责任
		if (mLCDutyBLSet == null || mLCDutyBLSet.size() == 0) {
			LCDutyDB tLCDutyDB = new LCDutyDB();
			LCDutySet tLCDutySet = new LCDutySet();
			tsql = "select * from lcduty where polno ='?SPolNo?'";
			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			sqlbv6.sql(tsql);
			sqlbv6.put("SPolNo", SPolNo);
			tLCDutySet = tLCDutyDB.executeQuery(sqlbv6);
			mLCDutyBLSet.add(tLCDutySet);
		}
		mLCDutyBLSet.get(1).setAmnt(mLCPolBL.getAmnt());
		mLCDutyBLSet.get(1).setMult(mLCPolBL.getMult());
		mLCDutyBLSet.get(1).setStandPrem(mLCPolBL.getStandPrem());
		for (int a = 1; a <= mLCDutyBLSet.size(); a++) {
			LCDutySchema tLCDutySchema = new LCDutySchema();
			tLCDutySchema = mLCDutyBLSet.get(a).getSchema();
			mLCDutyBLSet.set(a, tLCDutySchema);
		}

		// 通过保额计算保费
		CalBL tCalBL;
		tCalBL = new CalBL(mLCPolBL, mLCDutyBLSet, "");
		if (tCalBL.calPol() == false) {
			CError.buildErr(this, "险种保单重新计算时失败:"
					+ tCalBL.mErrors.getFirstError());
			return false;
		}

		// 得到续保保单相关信息

		// lcpol
		mLCPolSchema = tCalBL.getLCPol().getSchema();
		logger.debug("new date============="
				+ mLCPolSchema.getCValiDate());
		// 处理附加险续保的paytodate
		String newpaytodate = mLCPolSchema.getPayEndDate();
		mLCPolSchema.setPaytoDate(newpaytodate);
		mLCPolSchema.setAppFlag("9");// 续保处理的中间过程，业务操作完成后应制回“1”
		logger.debug("结束时间===========" + mLCPolSchema.getPayEndDate());
		logger.debug("应缴时间===========" + mLCPolSchema.getPaytoDate());
		// 保费表
		mLCPremSet = tCalBL.getLCPrem();
		for (int b = 1; b <= mLCPremSet.size(); b++) {
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema = mLCPremSet.get(b).getSchema();
			tLCPremSchema.setPayTimes(tLCPremSchema.getPayTimes() + 1);
			tLCPremSchema.setOperator(mLCPolSchema.getOperator());
			tLCPremSchema.setMakeDate(CurrentDate);
			tLCPremSchema.setMakeTime(CurrentTime);
			tLCPremSchema.setModifyDate(CurrentDate);
			tLCPremSchema.setModifyTime(CurrentTime);
			tLCPremSchema.setPaytoDate(mLCPolSchema.getPaytoDate());
			mLCPremSet.set(b, tLCPremSchema);
		}

		// 领取表
		mLCGetSet = tCalBL.getLCGet();
		for (int b = 1; b <= mLCGetSet.size(); b++) {
			LCGetSchema tLCGetSchema = new LCGetSchema();
			tLCGetSchema = mLCGetSet.get(b).getSchema();
			tLCGetSchema.setOperator(mLCPolSchema.getOperator());
			tLCGetSchema.setMakeDate(CurrentDate);
			tLCGetSchema.setMakeTime(CurrentTime);
			tLCGetSchema.setModifyDate(CurrentDate);
			tLCGetSchema.setModifyTime(CurrentTime);
			tLCGetSchema.setGetEndState("0");
			mLCGetSet.set(b, tLCGetSchema);
		}
		mLCDutySet = tCalBL.getLCDuty();
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	private boolean prepareOutputData() {
		mResult.clear();
		logger.debug("payenddate=====" + mLCPolSchema.getPayEndDate());
		logger.debug("paytodate======" + mLCPolSchema.getPaytoDate());
		mResult.add(mLCPolSchema);
		mResult.add(mLCPremSet);
		mResult.add(mLCGetSet);
		mResult.add(mLCDutySet);
		return true;
	}

	public double getJKSuperRisk(LPPolSchema aLPPolSchema) {
		double aValue = 0;
		LPPremDB tLPPremDB = new LPPremDB();
		LPPremSet tLPPremSet = new LPPremSet();
		String Str = "select * from lpprem where polno = '?polno?' and edorno = '?edorno?' and edortype = '?edortype?' and payplantype in ('01','03') order by makedate desc,maketime desc";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(Str);
		sqlbv7.put("polno", aLPPolSchema.getPolNo());
		sqlbv7.put("edorno", aLPPolSchema.getEdorNo());
		sqlbv7.put("edortype", aLPPolSchema.getEdorType());
		tLPPremSet = tLPPremDB.executeQuery(sqlbv7);
		if (tLPPremSet != null && tLPPremSet.size() > 0) {
			aValue = tLPPremSet.get(1).getSuppRiskScore();
		}
		return aValue;
	}

	public double getJKSuperRisk(LCPolSchema aLCPolSchema) {
		double aValue = 0;
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = new LCPremSet();
		String Str = "select * from lcprem where polno = '?polno?' and payplantype in ('01','03') order by makedate desc,maketime desc";
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(Str);
		sqlbv8.put("polno", aLCPolSchema.getPolNo());
		tLCPremSet = tLCPremDB.executeQuery(sqlbv8);
		if (tLCPremSet != null && tLCPremSet.size() > 0) {
			aValue = tLCPremSet.get(1).getSuppRiskScore();
		}
		return aValue;
	}

	public double getZYSuperRisk(LCPolSchema aLCPolSchema) {
		double aValue = 0;
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = new LCPremSet();
		String Str = "select * from lcprem where polno = '?polno?' and payplantype in ('02','04') order by makedate desc,maketime desc";
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(Str);
		sqlbv9.put("polno", aLCPolSchema.getPolNo());
		tLCPremSet = tLCPremDB.executeQuery(sqlbv9);
		if (tLCPremSet != null && tLCPremSet.size() > 0) {
			aValue = tLCPremSet.get(1).getSuppRiskScore();
		}
		return aValue;
	}

	public double getZYSuperRisk(LPPolSchema aLPPolSchema) {
		double aValue = 0;
		LPPremDB tLPPremDB = new LPPremDB();
		LPPremSet tLPPremSet = new LPPremSet();
		String Str = "select * from lpprem where polno = '?polno?' and edorno = '?edorno?' and edortype = '?edortype?' and payplantype in ('02','04') order by makedate desc,maketime desc";
		SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
		sqlbv10.sql(Str);
		sqlbv10.put("polno", aLPPolSchema.getPolNo());
		sqlbv10.put("edorno", aLPPolSchema.getEdorNo());
		sqlbv10.put("edortype", aLPPolSchema.getEdorType());
		
		tLPPremSet = tLPPremDB.executeQuery(sqlbv10);
		if (tLPPremSet != null && tLPPremSet.size() > 0) {
			aValue = tLPPremSet.get(1).getSuppRiskScore();
		}
		return aValue;
	}
}
