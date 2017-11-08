package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBMissionDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCRReportDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LWLockDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCDelPolLogSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCRReportSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LWLockSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:团单整单删除UI层
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

public class GroupContDeleteBL {
private static Logger logger = Logger.getLogger(GroupContDeleteBL.class);
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
	private TransferData mTransferData = new TransferData();

	private String mDelReason = "";
	private String mRemark = "";
	private String mMissionId = "";
	private String tPrtNo = "";

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCDelPolLogSchema mLCDelPolLog = new LCDelPolLogSchema();

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public GroupContDeleteBL() {
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
		// 全局变量实例
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		this.mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mDelReason = (String) mTransferData.getValueByName("GrpDelReason");
		if (mDelReason == null || mDelReason.equals("")) {
			mErrors.addOneError(new CError("没有得到保单删除原因数据！"));
			return false;
		}
		mRemark = (String) mTransferData.getValueByName("Remark");
		if (mRemark == null) {
			mRemark = "";
		}

		if (mGlobalInput == null) {
			mErrors.addOneError(new CError("没有得到全局量信息"));

			return false;
		}

		mOperator = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;

		// 团体保单实例
		mLCGrpContSchema.setSchema((LCGrpContSchema) mInputData
				.getObjectByObjectName("LCGrpContSchema", 0));

		if (mLCGrpContSchema == null) {
			this.mErrors.addOneError(new CError("传入的团单信息为空！"));

			return false;
		}

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());

		LCGrpContSet tLCGrpContSet = tLCGrpContDB.query();

		if ((tLCGrpContSet == null) || (tLCGrpContSet.size() <= 0)) {
			this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			this.mErrors.addOneError(new CError("未查到集体合同单！"));

			return false;
		}

		mLCGrpContSchema.setSchema(tLCGrpContSet.get(1));
		if (mLCGrpContSchema.getAppFlag().equals("0")) { // 若为新单则校验是否还有未回收的通知书
			if (!checkUWReply()) {
				return false;
			}
		}
		tPrtNo = tLCGrpContSet.get(1).getPrtNo();
		return true;
	}

	/**
	 * 对业务数据进行加工 对于新增的操作，这里需要有生成新合同号和新客户号的操作。
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		String tGrpContNo = mLCGrpContSchema.getGrpContNo();
		mLCDelPolLog.setOtherNo(tGrpContNo);
		mLCDelPolLog.setOtherNoType("01"); // 新单删除
		mLCDelPolLog.setPrtNo(mLCGrpContSchema.getPrtNo());

		if ("1".equals(mLCGrpContSchema.getAppFlag())) {
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
		mLCDelPolLog.setDelReason(mDelReason);
		mLCDelPolLog.setRemark(mRemark);
		// mMap.put("delete from LOBInsuredRelated where PolNo in (select PolNo from
		// LOBPol where GrpContNo = '" +
		// tGrpContNo + "')", "DELETE");
		// mMap.put("insert into LOBInsuredRelated (select * from LCInsuredRelated where
		// PolNo in (select PolNo from LCPol where GrpContNo = '" +
		// tGrpContNo + "'))", "INSERT");
		mMap
				.put(
						"delete from LCInsuredRelated where PolNo in (select PolNo from LCPol where GrpContNo = '"
								+ tGrpContNo + "')", "DELETE");

		mMap
				.put(
						"delete from LOBDuty  where PolNo in (select PolNo from LOBPol where GrpContNo = '"
								+ tGrpContNo + "')", "DELETE");
		mMap
				.put(
						"insert into LOBDuty (select * from LCDuty where PolNo in (select PolNo from LCPol where GrpContNo = '"
								+ tGrpContNo + "'))", "INSERT");
		mMap
				.put(
						"delete from LCDuty where PolNo in (select PolNo from LCPol where GrpContNo = '"
								+ tGrpContNo + "')", "DELETE");

		// mMap.put("delete from LOBPrem_1 where PolNo in (select PolNo from LOBPol
		// where GrpContNo = '" +
		// tGrpContNo + "')", "DELETE");
		// mMap.put("insert into LOBPrem_1 (select * from LCPrem_1 where PolNo in
		// (select PolNo from LCPol where GrpContNo = '" +
		// tGrpContNo + "'))", "INSERT");
		mMap
				.put(
						"delete from LCPrem_1 where PolNo in (select PolNo from LCPol where GrpContNo = '"
								+ tGrpContNo + "')", "DELETE");

		// mMap.put("delete from LOBPremToAcc where PolNo in (select PolNo from LOBPol
		// where GrpContNo = '" +
		// tGrpContNo + "')", "DELETE");
		// mMap.put("insert into LOBPremToAcc (select * from LCPremToAcc where PolNo in
		// (select PolNo from LCPol where GrpContNo = '" +
		// tGrpContNo + "'))", "INSERT");
		mMap
				.put(
						"delete from LCPremToAcc where PolNo in (select PolNo from LCPol where GrpContNo = '"
								+ tGrpContNo + "')", "DELETE");

		// mMap.put("delete from LOBGetToAcc where PolNo in (select PolNo from LOBPol
		// where GrpContNo = '" +
		// tGrpContNo + "')", "DELETE");
		// mMap.put("insert into LOBGetToAcc (select * from LCGetToAcc where PolNo in
		// (select PolNo from LCPol where GrpContNo = '" +
		// tGrpContNo + "'))", "INSERT");
		mMap
				.put(
						"delete from LCGetToAcc where PolNo in (select PolNo from LCPol where GrpContNo = '"
								+ tGrpContNo + "')", "DELETE");

		mMap.put("delete from  LOBPol  where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");
		mMap.put("insert into LOBPol (select * from LCPol where GrpContNo = '"
				+ tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCPol where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		mMap
				.put(
						"delete from LOBBnf  where ContNo in (select ContNo from LOBCont where GrpContNo = '"
								+ tGrpContNo + "')", "DELETE");
		mMap
				.put(
						"insert into LOBBnf (select * from LCBnf where ContNo in (select ContNo from LCCont where GrpContNo = '"
								+ tGrpContNo + "'))", "INSERT");
		mMap
				.put(
						"delete from LCBnf where ContNo in (select ContNo from LCCont where GrpContNo = '"
								+ tGrpContNo + "')", "DELETE");

		mMap.put("delete from LOBCont  where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");
		mMap.put(
				"insert into LOBCont (select * from LCCont where GrpContNo = '"
						+ tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCCont where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		mMap.put(
				"delete from LOBGrpPol where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");
		mMap.put(
				"insert into LOBGrpPol (select * from LCGrpPol where GrpContNo = '"
						+ tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGrpPol where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		mMap.put("delete from LOBPrem  where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");
		mMap.put(
				"insert into LOBPrem (select * from LCPrem where GrpContNo = '"
						+ tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCPrem where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		mMap.put("delete from LOBGet  where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");
		mMap.put("delete from LCGet where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");
		mMap.put("insert into LOBGet (select * from LCGet where GrpContNo = '"
				+ tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGet where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		mMap.put("delete from LOBInsureAccFee  where GrpContNo = '"
				+ tGrpContNo + "'", "DELETE");
		mMap.put(
				"insert into LOBInsureAccFee (select * from LCInsureAccFee where GrpContNo = '"
						+ tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCInsureAccFee where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LOBInsureAccClassFee where GrpContNo = '"
				+ tGrpContNo + "'", "DELETE");
		mMap
				.put(
						"insert into LOBInsureAccClassFee (select * from LCInsureAccClassFee where GrpContNo = '"
								+ tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCInsureAccClassFee where GrpContNo = '"
				+ tGrpContNo + "'", "DELETE");

		mMap.put("delete from LOBInsureAccClass  where GrpContNo = '"
				+ tGrpContNo + "'", "DELETE");
		mMap
				.put(
						"insert into LOBInsureAccClass (select * from LCInsureAccClass where GrpContNo = '"
								+ tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCInsureAccClass where GrpContNo = '"
				+ tGrpContNo + "'", "DELETE");

		mMap.put("delete from LOBInsureAcc  where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");
		mMap.put(
				"insert into LOBInsureAcc (select * from LCInsureAcc where GrpContNo = '"
						+ tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCInsureAcc where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LOBInsureAccTrace  where GrpContNo = '"
				+ tGrpContNo + "'", "DELETE");
		mMap
				.put(
						"insert into LOBInsureAccTrace (select * from LCInsureAccTrace where GrpContNo = '"
								+ tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCInsureAccTrace where GrpContNo = '"
				+ tGrpContNo + "'", "DELETE");

		mMap.put("delete from LOBInsured  where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");
		mMap.put(
				"insert into LOBInsured (select * from LCInsured where GrpContNo = '"
						+ tGrpContNo + "')", "INSERT");
		mMap.put(
				"delete from LCInsured where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		mMap.put("delete from LOBCustomerImpart  where GrpContNo = '"
				+ tGrpContNo + "'", "DELETE");
		mMap
				.put(
						"insert into LOBCustomerImpart (select * from LCCustomerImpart where GrpContNo = '"
								+ tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCCustomerImpart where GrpContNo = '"
				+ tGrpContNo + "'", "DELETE");

		// mMap.put("insert into LOBCustomerImpartParams (select * from
		// LCCustomerImpartParams where GrpContNo = '" + tGrpContNo + "')",
		// "INSERT");
		// mMap.put("delete from LCCustomerImpartParams where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");

		// mMap.put(
		// "delete from LOBAppnt where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBAppnt (select * from LCAppnt where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCAppnt where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		mMap.put("delete from LOBGrpAppnt  where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");
		mMap.put(
				"insert into LOBGrpAppnt (select * from LCGrpAppnt where GrpContNo = '"
						+ tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGrpAppnt where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBGrpFee where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBGrpFee (select * from LCGrpFee where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGrpFee where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		// mMap.put(
		// "delete from LOBGrpFeeParam where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBGrpFeeParam (select * from LCGrpFeeParam where GrpContNo = '"
		// +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGrpFeeParam where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBContPlan where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBContPlan (select * from LCContPlan where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCContPlan where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBGeneral where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBGeneral (select * from LCGeneral where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put(
				"delete from LCGeneral where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		// mMap.put("delete from LOBGeneralToRisk where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put("insert into LOBGeneralToRisk (select * from LCGeneralToRisk where
		// GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGeneralToRisk where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBContPlanRisk where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBContPlanRisk (select * from LCContPlanRisk where GrpContNo =
		// '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCContPlanRisk where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put("delete from LOBContPlanDutyParam where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put("insert into LOBContPlanDutyParam (select * from LCContPlanDutyParam
		// where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCContPlanDutyParam where GrpContNo = '"
				+ tGrpContNo + "'", "DELETE");

		// mMap.put("delete from LOBContPlanFactory where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put("insert into LOBContPlanFactory (select * from LCContPlanFactory
		// where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCContPlanFactory where GrpContNo = '"
				+ tGrpContNo + "'", "DELETE");

		// mMap.put("delete from LOBContPlanParam where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put("insert into LOBContPlanParam (select * from LCContPlanParam where
		// GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCContPlanParam where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBUWError where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBUWError (select * from LCUWError where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put(
				"delete from LCUWError where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		// mMap.put(
		// "delete from LOBUWSub where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBUWSub (select * from LCUWSub where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCUWSub where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		// mMap.put(
		// "delete from LOBUWMaster where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBUWMaster (select * from LCUWMaster where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCUWMaster where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBCUWError where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBCUWError (select * from LCCUWError where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCCUWError where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBCUWSub where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBCUWSub (select * from LCCUWSub where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCCUWSub where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		// mMap.put(
		// "delete from LOBCUWMaster where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBCUWMaster (select * from LCCUWMaster where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCCUWMaster where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBGUWError where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBGUWError (select * from LCGUWError where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGUWError where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBGUWSub where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBGUWSub (select * from LCGUWSub where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGUWSub where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		// mMap.put(
		// "delete from LOBGUWMaster where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBGUWMaster (select * from LCGUWMaster where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGUWMaster where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBGCUWError where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBGCUWError (select * from LCGCUWError where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGCUWError where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBGCUWSub where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBGCUWSub (select * from LCGCUWSub where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put(
				"delete from LCGCUWSub where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		// mMap.put(
		// "delete from LOBGCUWMaster where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBGCUWMaster (select * from LCGCUWMaster where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGCUWMaster where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBUWReport where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBUWReport where GrpContNo = '" +
		// tGrpContNo + "'", "INSERT");
		mMap.put("delete from LCUWReport where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBNotePad where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBNotePad (select * from LCNotePad where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put(
				"delete from LCNotePad where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		// mMap.put(
		// "delete from LOBRReport where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		//
		// mMap.put(
		// "insert into LOBRReport (select * from LCRReport where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put(
				"delete from LCRReport where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		// mMap.put(
		// "delete from LOBIssuePol where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBIssuePol (select * from LCIssuePol where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCIssuePol where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBGrpIssuePol where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBGrpIssuePol (select * from LCGrpIssuePol where GrpContNo = '"
		// +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGrpIssuePol where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBPENoticeItem where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBPENoticeItem (select * from LCPENoticeItem where GrpContNo =
		// '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCPENoticeItem where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");

		// mMap.put(
		// "delete from LOBPENotice where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBPENotice (select * from LCPENotice where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap
				.put(
						"delete from LCPENotice where contno in (select contno from lccont where GrpContNo = '"
								+ tGrpContNo + "')", "DELETE");

		// mMap.put(
		// "delete from LOBCGrpSpec where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBCGrpSpec (select * from LCCGrpSpec where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		// mMap.put("delete from LCCGrpSpec where GrpContNo = '" + tGrpContNo +
		// "'", "DELETE");

		// mMap.put(
		// "delete from LOBCSpec where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBCSpec (select * from LCCSpec where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCCSpec where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		// mMap.put(
		// "delete from LOBSpec where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");
		// mMap.put(
		// "insert into LOBSpec (select * from LCSpec where GrpContNo = '" +
		// tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCSpec where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		mMap.put("delete from LOBGrpCont where GrpContNo = '" + tGrpContNo
				+ "'", "DELETE");
		mMap.put(
				"insert into LOBGrpCont (select * from LCGrpCont where GrpContNo = '"
						+ tGrpContNo + "')", "INSERT");
		mMap.put(
				"delete from LCGrpCont where GrpContNo = '" + tGrpContNo + "'",
				"DELETE");

		mMap.put("delete from LACommisionDetail where grpcontno='" + tGrpContNo
				+ "'", "DELETE");
		// 团单下的通知书
		String sql = "delete from loprtmanager where otherno in (select contno from lccont where grpcontno='"
				+ tGrpContNo + "') ";
		String sql2 = "delete from loprtmanager where otherno='" + tGrpContNo
				+ "'";
		mMap.put(sql, "DELETE");
		mMap.put(sql2, "DELETE");

		mMap.put(mLCDelPolLog, "INSERT");
		// 团单工作流节点得删除
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setMissionProp1(tGrpContNo);
		tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet.size() != 0) {
			tLWMissionDB = new LWMissionDB();
			tLWMissionDB.setMissionID(tLWMissionSet.get(1).getMissionID());
			mMissionId = tLWMissionSet.get(1).getMissionID();
			tLWMissionSet = new LWMissionSet();
			tLWMissionSet = tLWMissionDB.query();
			if (tLWMissionSet.size() != 0) {
				mMap.put(tLWMissionSet, "DELETE");
			}
		} else {
			/* 在录入时lwmission表中的missionprop1的数据段存储的是prtno号，而不是grpcontno */
			String SQL;
			String tPrtNo;
			ExeSQL tExeSQL = new ExeSQL();
			SQL = "select prtno from lcgrpcont where grpcontno = '"
					+ tGrpContNo + "'";
			tPrtNo = tExeSQL.getOneValue(SQL);
			tLWMissionDB = new LWMissionDB();
			tLWMissionSet = new LWMissionSet();
			tLWMissionDB.setMissionProp1(tPrtNo);
			tLWMissionDB.setProcessID("0000000003");
			/* 由于prtno所对应的保单号不是唯一的，所以要加processID的限制 */
			tLWMissionSet = tLWMissionDB.query();
			if (tLWMissionSet.size() != 0) {
				tLWMissionDB = new LWMissionDB();
				tLWMissionDB.setMissionID(tLWMissionSet.get(1).getMissionID());
				mMissionId = tLWMissionSet.get(1).getMissionID();
				tLWMissionSet = new LWMissionSet();
				tLWMissionSet = tLWMissionDB.query();
				if (tLWMissionSet.size() != 0) {
					mMap.put(tLWMissionSet, "DELETE");
				}
			}
		}
		// 工作流b表数据
		if (mMissionId != null && !mMissionId.equals("")
				&& !mMissionId.equals("null")) {
			LBMissionDB tLBMissionDB = new LBMissionDB();
			LBMissionSet tLBMissionSet = new LBMissionSet();
			// tLBMissionDB.setMissionProp1(tGrpContNo);
			tLBMissionDB.setMissionID(mMissionId);
			tLBMissionSet = tLBMissionDB.query();
			mMap.put(tLBMissionSet, "DELETE");
			// LWLock表
			LWLockDB tLWLockDB = new LWLockDB();
			tLWLockDB.setMissionID(mMissionId);
			LWLockSet tLWLockSet = new LWLockSet();
			tLWLockSet = tLWLockDB.query();
			mMap.put(tLWLockSet, "DELETE");
		}
		// 扫描表
		mMap.put("delete from ES_DOC_PAGES "
				+ "where DocId in (select DocId from ES_DOC_RELATION "
				+ "where BussNo = '" + tPrtNo + "')", "DELETE");

		mMap.put("delete from ES_DOC_MAIN "
				+ "where DocId in (select DocId from ES_DOC_RELATION "
				+ "where BussNo = '" + tPrtNo + "')", "DELETE");
		mMap.put("delete from ES_DOC_RELATION where BussNo = '" + tPrtNo + "'",
				"DELETE");

		return true;
	}

	private boolean checkUWReply() {
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setCode("G03");
		tLOPRTManagerDB.setStandbyFlag3(mLCGrpContSchema.getGrpContNo());
		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		tLOPRTManagerSet = tLOPRTManagerDB.query();
		for (int i = 1; i <= tLOPRTManagerSet.size(); i++) {
			if (!tLOPRTManagerSet.get(i).getStateFlag().equals("2")) {
				CError tError = new CError();
				tError.moduleName = "GrpUWManuNormChkBL";
				tError.functionName = "checkUWReply";
				tError.errorMessage = "该团单下有未回销的体检通知书";
				this.mErrors.addOneError(tError);
				return false;

			}
		}
		LCRReportDB tLCRReportDB = new LCRReportDB();
		tLCRReportDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		LCRReportSet tLCRReportSet = new LCRReportSet();
		tLCRReportSet = tLCRReportDB.query();
		for (int i = 1; i <= tLCRReportSet.size(); i++) {
			if (!tLCRReportSet.get(i).getReplyFlag().equals("1")) {
				CError tError = new CError();
				tError.moduleName = "GrpUWManuNormChkBL";
				tError.functionName = "checkUWReply";
				tError.errorMessage = "该团单下有未回复的生调卷";
				this.mErrors.addOneError(tError);
				return false;

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
