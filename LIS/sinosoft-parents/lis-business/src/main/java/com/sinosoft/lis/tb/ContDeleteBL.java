package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDelPolLogSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:个单整单删除BL层
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author zhangrong
 * @version 1.0
 */
public class ContDeleteBL {
private static Logger logger = Logger.getLogger(ContDeleteBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 传输到后台处理的map */
	private MMap mMap = new MMap();

	/** 数据操作字符串 */
	private String mOperate;
	private String mOperator;
	private String mManageCom;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCDelPolLogSchema mLCDelPolLog = new LCDelPolLogSchema();

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public ContDeleteBL() {
	}

	/**
	 * 处理实际的业务逻辑。
	 * 
	 * @param cInputData
	 *            VData 从前台接收的表单数据
	 * @param cOperate
	 *            String 操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将数据取到本类变量中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 将VData数据还原成业务需要的类
		if (this.getInputData() == false) {
			return false;
		}

		logger.debug("---getInputData successful---");

		if (this.dealData() == false) {
			return false;
		}

		logger.debug("---dealdata successful---");

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mResult, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			return false;
		}

		return true;
	}

	/**
	 * 将UI层传输来得数据根据业务还原成具体的类
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		logger.debug("contdeletebl---getInputData()");
		// 全局变量实例
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			mErrors.addOneError(new CError("没有得到全局量信息"));

			return false;
		}

		mOperator = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;

		// 团体保单实例
		mLCContSchema.setSchema((LCContSchema) mInputData
				.getObjectByObjectName("LCContSchema", 0));

		if (mLCContSchema == null) {
			this.mErrors.addOneError(new CError("传入的团单信息为空！"));

			return false;
		}

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLCContSchema.getContNo());

		LCContSet tLCContSet = tLCContDB.query();

		if ((tLCContSet == null) || (tLCContSet.size() <= 0)) {
			this.mErrors.copyAllErrors(tLCContDB.mErrors);
			this.mErrors.addOneError(new CError("未查到集体合同单！"));
			return false;
		}
		mLCContSchema.setSchema(tLCContSet.get(1));
		String sscontno = mLCContSchema.getContNo();
		return true;
	}

	/**
	 * 对业务数据进行加工 对于新增的操作，这里需要有生成新合同号和新客户号的操作。
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		String tContType = mLCContSchema.getContType();
		String tContNo = mLCContSchema.getContNo();
		String tGrpContNo = mLCContSchema.getGrpContNo();
		int tPeoples = mLCContSchema.getPeoples();
		double tPrem = mLCContSchema.getPrem();
		double tAmnt = mLCContSchema.getAmnt();

		mLCDelPolLog.setOtherNo(tContNo);
		mLCDelPolLog.setOtherNoType("12");
		mLCDelPolLog.setPrtNo(mLCContSchema.getPrtNo());

		if ("1".equals(mLCContSchema.getAppFlag())) {
			mLCDelPolLog.setIsPolFlag("1");
		} else {
			mLCDelPolLog.setIsPolFlag("0");
		}

		mLCDelPolLog.setOperator(mOperator);
		mLCDelPolLog.setManageCom(mManageCom);
		mLCDelPolLog.setMakeDate(theCurrentDate);
		mLCDelPolLog.setMakeTime(theCurrentTime);
		mLCDelPolLog.setModifyDate(theCurrentDate);
		mLCDelPolLog.setModifyTime(theCurrentTime);

		if ("2".equals(tContType)) {
			logger.debug("进入团单个单删除");

			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();

			tLCPolDB.setContNo(tContNo);
			tLCPolSet = tLCPolDB.query();

			LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
			LCGrpContSet tLCGrpContSet = new LCGrpContSet();
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(tGrpContNo);
			tLCGrpContSet = tLCGrpContDB.query();
			tLCGrpContSchema.setSchema(tLCGrpContSet.get(1));
			double premnew = tLCGrpContSchema.getPrem();
			double Amntnew = tLCGrpContSchema.getAmnt();
			int Peoplesnew = tLCGrpContSchema.getPeoples2();
			premnew = premnew - tPrem;
			Amntnew = Amntnew - tAmnt;
			Peoplesnew = Peoplesnew - tPeoples;

			// mMap.put("insert into LOBGrpCont (select * from LCGrpCont
			// where GrpContNo = '" + tGrpContNo + "')", "INSERT");
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql("update LCGrpCont set  prem = (select case when sum(prem) is null  then 0 else sum(prem) end from LCCont where GrpContNo='"
					+ "?tGrpContNo?"
					+ "' and ContNo!='"
					+ "?tContNo?"
					+ "'), "
					+ "Amnt = (select case when sum(Amnt) is null  then 0 else sum(Amnt) end from LCCont where GrpContNo='"
					+ "?tGrpContNo?"
					+ "' and ContNo!='"
					+ "?tContNo?"
					+ "'), "
					+ "Peoples2 = "
					+ "?Peoplesnew?"
					+ " where GrpContNo  =  '"
					+ "?tGrpContNo?"
					+ "'");
			sqlbv.put("tGrpContNo", tGrpContNo);
			sqlbv.put("tContNo", tContNo);
			sqlbv.put("Peoplesnew", Peoplesnew);
			mMap.put(sqlbv, "UPDATE");
			int count = tLCPolSet.size();
			logger.debug(count);
			for (int i = 1; i <= count; i++) {
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema.setSchema(tLCPolSet.get(i));
				String tGrpPolNo = tLCPolSchema.getGrpPolNo();
				String tPolNo = tLCPolSchema.getPolNo();
				tPrem = tLCPolSchema.getPrem();
				tAmnt = tLCPolSchema.getAmnt();

				LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
				LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
				LCGrpPolDB tLCGrpPoltDB = new LCGrpPolDB();
				tLCGrpPoltDB.setGrpPolNo(tGrpPolNo);
				tLCGrpPolSet = tLCGrpPoltDB.query();
				tLCGrpPolSchema.setSchema(tLCGrpPolSet.get(1));
				premnew = tLCGrpPolSchema.getPrem();
				Amntnew = tLCGrpPolSchema.getAmnt();
				Peoplesnew = tLCGrpPolSchema.getPeoples2();
				premnew = premnew - tPrem;
				Amntnew = Amntnew - tAmnt;
				Peoplesnew = Peoplesnew - tPeoples;

				// mMap.put("insert into LOBGrpPol (select * from LCGrpPol
				// where GrpPolNo = '" + tGrpPolNo + "')", "INSERT");
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql("update LCGrpPol set  prem =(select case when sum(prem) is null  then 0 else sum(prem) end from LCPol where GrpPolNo='"
						+ "?tGrpPolNo?"
						+ "' and PolNo!='"
						+ "?tPolNo?"
						+ "'), "
						+ "Amnt = (select case when sum(Amnt) is null  then 0 else sum(Amnt) end from LCPol where GrpPolNo='"
						+ "?tGrpPolNo?"
						+ "' and PolNo!='"
						+ "?tPolNo?"
						+ "'), "
						+ "Peoples2 = "
						+ "?Peoplesnew?"
						+ " where GrpPolNo  =  '"
						+ "?tGrpPolNo?"
						+ "'");
				sqlbv1.put("tGrpPolNo", tGrpPolNo);
				sqlbv1.put("tPolNo", tPolNo);
				sqlbv1.put("Peoplesnew", Peoplesnew);
				mMap.put(sqlbv1, "UPDATE");
			}
		}

		// mMap.put("insert into LOBInsuredRelated (select * from
		// LCInsuredRelated where PolNo in (select PolNo from LCPol
		// where ContNo = '" + tContNo + "'))", "INSERT");
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql("delete from LCInsuredRelated where PolNo in (select PolNo from LCPol where ContNo = '"
				+ 
				"?tContNo?" + "')");
		sqlbv2.put("tContNo", tContNo);
		mMap.put(sqlbv2, "DELETE");

		// mMap.put("insert into LOBDuty (select * from LCDuty where PolNo in
		// (select PolNo from LCPol where ContNo = '" + tContNo + "'))",
		// "INSERT");
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql("delete from LCDuty where PolNo in (select PolNo from LCPol where ContNo = '"
				+ 
				"?tContNo?" + "')");
		sqlbv3.put("tContNo", tContNo);
		mMap.put(sqlbv3, "DELETE");

		// mMap.put("insert into LOBPrem_1 (select * from LCPrem_1 where PolNo
		// in (select PolNo from LCPol where ContNo = '" + tContNo + "'))",
		// "INSERT");
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql("delete from LCPrem_1 where PolNo in (select PolNo from LCPol where ContNo = '"
				+ 
				"?tContNo?" + "')");
		sqlbv4.put("tContNo", tContNo);
		mMap.put(sqlbv4, "DELETE");

		// mMap.put("insert into LOBPremToAcc (select * from LCPremToAcc where
		// PolNo in (select PolNo from LCPol where ContNo = '" + tContNo +
		// "'))", "INSERT");
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql("delete from LCPremToAcc where PolNo in (select PolNo from LCPol where ContNo = '"
				+ 
				"?tContNo?" + "')");
		sqlbv5.put("tContNo", tContNo);
		mMap.put(sqlbv5, "DELETE");

		// mMap.put("insert into LOBGetToAcc (select * from LCGetToAcc where
		// PolNo in (select PolNo from LCPol where ContNo = '" + tContNo +
		// "'))", "INSERT");
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql("delete from LCGetToAcc where PolNo in (select PolNo from LCPol where ContNo = '"
				+ 
				"?tContNo?" + "')");
		sqlbv6.put("tContNo", tContNo);
		mMap.put(sqlbv6, "DELETE");

		// mMap.put("insert into LOBPol (select * from LCPol where ContNo = '" +
		// tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql("delete from LCPol where ContNo = '" + "?tContNo?" + "'");
		sqlbv7.put("tContNo", tContNo);
		mMap.put(sqlbv7, "DELETE");

		// mMap.put("insert into LOBBnf (select * from LCBnf where ContNo in
		// (select ContNo from LCCont where ContNo = '" + tContNo + "'))",
		// "INSERT");
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql("delete from LCBnf where ContNo in (select ContNo from LCCont where ContNo = '"
				+ "?tContNo?" + "')");
		sqlbv8.put("tContNo", tContNo);
		mMap.put(sqlbv8, "DELETE");

		// mMap.put("insert into LOBCont (select * from LCCont where ContNo = '"
		// + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql("delete from LCCont where ContNo = '" + "?tContNo?" + "'");
		sqlbv9.put("tContNo", tContNo);
		mMap.put(sqlbv9, "DELETE");

		// mMap.put("insert into LOBPrem (select * from LCPrem where ContNo = '"
		// + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql("delete from LCPrem where ContNo = '" + "?tContNo?" + "'");
		sqlbv10.put("tContNo", tContNo);
		mMap.put(sqlbv10, "DELETE");

		// mMap.put("insert into LOBGet (select * from LCGet where ContNo = '" +
		// tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql("delete from LCGet where ContNo = '" + "?tContNo?" + "'");
		sqlbv11.put("tContNo", tContNo);
		mMap.put(sqlbv11, "DELETE");

		// mMap.put("insert into LOBInsureAccFee (select * from LCInsureAccFee
		// where ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql("delete from LCInsureAccFee where ContNo = '" + "?tContNo?" + "'");
		sqlbv12.put("tContNo", tContNo);
		mMap.put(sqlbv12, "DELETE");

		// mMap.put("insert into LOBInsureAccClassFee (select * from
		// LCInsureAccClassFee where ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql("delete from LCInsureAccClassFee where ContNo = '" + "?tContNo?" + "'");
		sqlbv13.put("tContNo", tContNo);
		mMap.put(sqlbv13, "DELETE");

		// mMap.put("insert into LOBInsureAccClass (select * from
		// LCInsureAccClass where ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql("delete from LCInsureAccClass where ContNo = '" + "?tContNo?" + "'");
		sqlbv14.put("tContNo", tContNo);
		mMap.put(sqlbv14, "DELETE");

		// mMap.put("insert into LOBInsureAcc (select * from LCInsureAcc where
		// ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.sql("delete from LCInsureAcc where ContNo = '" + "?tContNo?" + "'");
		sqlbv15.put("tContNo", tContNo);
		mMap.put(sqlbv15, "DELETE");

		// mMap.put("insert into LOBInsureAccTrace (select * from
		// LCInsureAccTrace where ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
		sqlbv16.sql("delete from LCInsureAccTrace where ContNo = '" + "?tContNo?" + "'");
		sqlbv16.put("tContNo", tContNo);
		mMap.put(sqlbv16, "DELETE");

		// mMap.put("insert into LOBInsured (select * from LCInsured where
		// ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
		sqlbv17.sql("delete from LCInsured where ContNo = '" + "?tContNo?" + "'");
		sqlbv17.put("tContNo", tContNo);
		mMap.put(sqlbv17, "DELETE");

		// mMap.put("insert into LOBCustomerImpart (select * from
		// LCCustomerImpart where ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
		sqlbv18.sql("delete from LCCustomerImpart where ContNo = '" + "?tContNo?" + "'");
		sqlbv18.put("tContNo", tContNo);
		mMap.put(sqlbv18, "DELETE");

		// mMap.put("insert into LOBCustomerImpartParams (select * from
		// LCCustomerImpartParams where ContNo = '" + tContNo + "')", "INSERT");
		// mMap.put("delete from LCCustomerImpartParams where ContNo = '" +
		// tContNo + "'", "DELETE");

		// mMap.put("insert into LOBAppnt (select * from LCAppnt where ContNo =
		// '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		sqlbv19.sql("delete from LCAppnt where ContNo = '" + "?tContNo?" + "'");
		sqlbv19.put("tContNo", tContNo);
		mMap.put(sqlbv19, "DELETE");

		// mMap.put("insert into LOBUWError (select * from LCUWError where
		// ContNo = '" + tContNo + "')", "INSERT");SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		sqlbv20.sql("delete from LCUWError where ContNo = '" + "?tContNo?" + "'");
		sqlbv20.put("tContNo", tContNo);
		mMap.put(sqlbv20, "DELETE");

		// mMap.put("insert into LOBUWSub (select * from LCUWSub where ContNo =
		// '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		sqlbv21.sql("delete from LCUWSub where ContNo = '" + "?tContNo?" + "'");
		sqlbv21.put("tContNo", tContNo);
		mMap.put(sqlbv21, "DELETE");

		// mMap.put("insert into LOBUWMaster (select * from LCUWMaster where
		// ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		sqlbv22.sql("delete from LCUWMaster where ContNo = '" + "?tContNo?" + "'");
		sqlbv22.put("tContNo", tContNo);
		mMap.put(sqlbv22, "DELETE");

		// mMap.put("insert into LOBCUWError (select * from LCCUWError where
		// ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		sqlbv23.sql("delete from LCCUWError where ContNo = '" + "?tContNo?" + "'");
		sqlbv23.put("tContNo", tContNo);
		mMap.put(sqlbv23, "DELETE");

		// mMap.put("insert into LOBCUWSub (select * from LCCUWSub where ContNo
		// = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
		sqlbv24.sql("delete from LCCUWSub where ContNo = '" + "?tContNo?" + "'");
		sqlbv24.put("tContNo", tContNo);
		mMap.put(sqlbv24, "DELETE");

		// mMap.put("insert into LOBCUWMaster (select * from LCCUWMaster where
		// ContNo = '" + tContNo + "')", "INSERT");
		mMap.put("delete from LCCUWMaster where ContNo = '" + "?tContNo?" + "'",
				"DELETE");

		// mMap.put("insert into LOBUWReport (select * from LCUWReport where
		// ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
		sqlbv25.sql("delete from LCUWReport where otherno = '" + "?tContNo?" + "'");
		sqlbv25.put("tContNo", tContNo);
		mMap.put(sqlbv25, "DELETE");

		// mMap.put("insert into LOBNotePad (select * from LCNotePad where
		// ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
		sqlbv26.sql("delete from LCNotePad where ContNo = '" + "?tContNo?" + "'");
		sqlbv26.put("tContNo", tContNo);
		mMap.put(sqlbv26, "DELETE");

		// mMap.put("insert into LOBRReport (select * from LCRReport where
		// ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
		sqlbv27.sql("delete from LCRReport where ContNo = '" + "?tContNo?" + "'");
		sqlbv27.put("tContNo", tContNo);
		mMap.put(sqlbv27, "DELETE");

		// mMap.put("insert into LOBIssuePol (select * from LCIssuePol where
		// ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
		sqlbv28.sql("delete from LCIssuePol where ContNo = '" + "?tContNo?" + "'");
		sqlbv28.put("tContNo", tContNo);
		mMap.put(sqlbv28, "DELETE");

		// mMap.put("insert into LOBPENoticeItem (select * from LCPENoticeItem
		// where ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
		sqlbv29.sql("delete from LCPENoticeItem where ContNo = '" + "?tContNo?" + "'");
		sqlbv29.put("tContNo", tContNo);
		mMap.put(sqlbv29, "DELETE");

		// mMap.put("insert into LOBPENotice (select * from LCPENotice where
		// ContNo = '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
		sqlbv30.sql("delete from LCPENotice where ContNo = '" + "?tContNo?" + "'");
		sqlbv30.put("tContNo", tContNo);
		mMap.put(sqlbv30, "DELETE");

		// mMap.put("insert into LOBCSpec (select * from LCCSpec where ContNo =
		// '" + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
		sqlbv31.sql("delete from LCCSpec where ContNo = '" + "?tContNo?" + "'");
		sqlbv31.put("tContNo", tContNo);
		mMap.put(sqlbv31, "DELETE");

		// mMap.put("insert into LOBSpec (select * from LCSpec where ContNo = '"
		// + tContNo + "')", "INSERT");
		SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
		sqlbv32.sql("delete from LCSpec where ContNo = '" + "?tContNo?" + "'");
		sqlbv32.put("tContNo", tContNo);
		mMap.put(sqlbv32, "DELETE");
		
		SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
		sqlbv33.sql("delete from LCGrpImportLog where ContNo = '" + "?tContNo?" + "'");
		sqlbv33.put("tContNo", tContNo);
		mMap.put(sqlbv33, "DELETE");

		mMap.put(mLCDelPolLog, "INSERT");
		// 对工作流节点的删除

		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setMissionProp2(tContNo);
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet.size() != 0) {
			tLWMissionDB = new LWMissionDB();
			tLWMissionDB.setMissionID(tLWMissionSet.get(1).getMissionID());
			tLWMissionSet = new LWMissionSet();
			tLWMissionSet = tLWMissionDB.query();
			if (tLWMissionSet.size() != 0) {
				mMap.put(tLWMissionSet, "DELETE");
			}
		}
		return true;
	}

	/**
	 * 准备数据，重新填充数据容器中的内容
	 */
	private void prepareOutputData() {
		// 记录当前操作员
		mResult.clear();
		mResult.add(mMap);
	}

	/**
	 * 操作结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}
}
