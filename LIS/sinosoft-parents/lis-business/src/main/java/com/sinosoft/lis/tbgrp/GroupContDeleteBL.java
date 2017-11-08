package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCDelPolLogSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
	private String mGrpContNo;

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
	 * 申请控制并发
	 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	private boolean lockNo(String tPrtNo) {
		if (!mPubLock.lock(tPrtNo, "LC1002")) {
			return false;
		}
		return true;
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

		//删除保单控制并发
		try {
			if (!lockNo(mGrpContNo)) {
				logger.debug("印刷号：["+mGrpContNo+"]锁定号码失败!");
				this.mErrors.addOneError(this.mPubLock.mErrors.getFirstError());
				//tLockFlag = false;
				//mPubLock.unLock();
				//continue;
				return false;
			}
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			mPubLock.unLock();
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
		mGrpContNo = mLCGrpContSchema.getGrpContNo();

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());

		LCGrpContSet tLCGrpContSet = tLCGrpContDB.query();

		if ((tLCGrpContSet == null) || (tLCGrpContSet.size() <= 0)) {
			this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			this.mErrors.addOneError(new CError("未查到集体合同单！"));

			return false;
		}

		mLCGrpContSchema.setSchema(tLCGrpContSet.get(1));

		return true;
	}

	/**
	 * 对业务数据进行加工 对于新增的操作，这里需要有生成新合同号和新客户号的操作。
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		mLCDelPolLog.setOtherNo(mGrpContNo);
		mLCDelPolLog.setOtherNoType("0");
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

		// mMap.put("insert into LOBInsuredRelated (select * from
		// LCInsuredRelated where PolNo in (select PolNo from LCPol where
		// GrpContNo = '" + tGrpContNo + "'))", "INSERT");
		// mMap.put("delete from LCInsuredRelated where PolNo in (select PolNo
		// from LCPol where GrpContNo = '" + tGrpContNo + "')", "DELETE");

		// mMap.put("insert into LOBDuty (select * from LCDuty where PolNo in
		// (select PolNo from LCPol where GrpContNo = '" + tGrpContNo + "'))",
		// "INSERT");
		mMap
				.put(
						"delete from LCDuty where PolNo in (select PolNo from LCPol where GrpContNo = '"
								+ mGrpContNo + "')", "DELETE");

		// mMap.put("insert into LOBPrem_1 (select * from LCPrem_1 where PolNo
		// in (select PolNo from LCPol where GrpContNo = '" + tGrpContNo +
		// "'))", "INSERT");
		// mMap.put("delete from LCPrem_1 where PolNo in (select PolNo from
		// LCPol where GrpContNo = '" + tGrpContNo + "')", "DELETE");

		// mMap.put("insert into LOBPremToAcc (select * from LCPremToAcc where
		// PolNo in (select PolNo from LCPol where GrpContNo = '" + tGrpContNo +
		// "'))", "INSERT");
		mMap
				.put(
						"delete from LCPremToAcc where PolNo in (select PolNo from LCPol where GrpContNo = '"
								+ mGrpContNo + "')", "DELETE");

		// mMap.put("insert into LOBGetToAcc (select * from LCGetToAcc where
		// PolNo in (select PolNo from LCPol where GrpContNo = '" + tGrpContNo +
		// "'))", "INSERT");
		mMap
				.put(
						"delete from LCGetToAcc where PolNo in (select PolNo from LCPol where GrpContNo = '"
								+ mGrpContNo + "')", "DELETE");

		mMap.put("delete from LJAPayPerson where  GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");
		mMap.put(
				"delete from LCBnf where polno in (select PolNo from LCPol where GrpContNo = '"
						+ mGrpContNo + "')", "DELETE");

		// mMap.put("insert into LOBCont (select * from LCCont where GrpContNo =
		// '" + tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCCont where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");

		// mMap.put("insert into LOBGrpPol (select * from LCGrpPol where
		// GrpContNo = '" + tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGrpPol where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");

		// mMap.put("insert into LOBPrem (select * from LCPrem where GrpContNo =
		// '" + tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCPrem where  GrpContNo = '" + mGrpContNo + "'",
				"DELETE");

		// mMap.put("insert into LOBGet (select * from LCGet where GrpContNo =
		// '" + tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGet where  GrpContNo = '" + mGrpContNo + "'",
				"DELETE");

		// mMap.put("insert into LOBInsureAccFee (select * from LCInsureAccFee
		// where GrpContNo = '" + tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCInsureAccFee where  GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		// mMap.put("insert into LOBInsureAccClassFee (select * from
		// LCInsureAccClassFee where GrpContNo = '" + tGrpContNo + "')",
		// "INSERT");
		mMap.put("delete from LCInsureAccClassFee where  GrpContNo = '"
				+ mGrpContNo + "'", "DELETE");

		// mMap.put("insert into LOBInsureAccClass (select * from
		// LCInsureAccClass where GrpContNo = '" + tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCInsureAccClass where  GrpContNo = '"
				+ mGrpContNo + "'", "DELETE");

		// mMap.put("insert into LOBInsureAcc (select * from LCInsureAcc where
		// GrpContNo = '" + tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCInsureAcc where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		// mMap.put("insert into LOBInsureAccTrace (select * from
		// LCInsureAccTrace where GrpContNo = '" + tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCInsureAccTrace where  GrpContNo = '"
				+ mGrpContNo + "'", "DELETE");

		// mMap.put("insert into LOBInsured (select * from LCInsured where
		// GrpContNo = '" + tGrpContNo + "')", "INSERT");
		mMap.put(
				"delete from LCInsured where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");

		// mMap.put("insert into LOBCustomerImpart (select * from
		// LCCustomerImpart where GrpContNo = '" + tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCCustomerImpart where GrpContNo = '"
				+ mGrpContNo + "'", "DELETE");

		// mMap.put("insert into LOBCustomerImpartParams (select * from
		// LCCustomerImpartParams where GrpContNo = '" + tGrpContNo + "')",
		// "INSERT");
		// mMap.put("delete from LCCustomerImpartParams where GrpContNo = '" +
		// tGrpContNo + "'", "DELETE");

		// mMap.put("insert into LOBAppnt (select * from LCAppnt where GrpContNo
		// = '" + tGrpContNo + "')", "INSERT");
		// mMap.put("delete from LCAppnt where GrpContNo = '" + tGrpContNo +
		// "'", "DELETE");

		// mMap.put("insert into LOBGrpAppnt (select * from LCGrpAppnt where
		// GrpContNo = '" + tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGrpAppnt where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");
		mMap.put(
				"delete from llaccount where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");
		// mMap.put("insert into LOBGrpFee (select * from LCGrpFee where
		// GrpContNo = '" + tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGrpFee where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");

		// mMap.put("insert into LOBGrpFeeParam (select * from LCGrpFeeParam
		// where GrpContNo = '" + tGrpContNo + "')", "INSERT");
		mMap.put("delete from LCGrpFeeParam where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LCContPlan where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put(
				"delete from LCGeneral where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");

		// 增加影响的处理
		mMap.put("delete From es_doc_main where doccode='"
				+ mLCGrpContSchema.getPrtNo() + "'", "DELETE");
		mMap
				.put(
						"delete from es_doc_pages where docid in (select docid from es_doc_relation where bussno='"
								+ mLCGrpContSchema.getPrtNo() + "')", "DELETE");
		mMap.put("delete From es_doc_relation where bussno='"
				+ mLCGrpContSchema.getPrtNo() + "'", "DELETE");

		mMap.put("delete from LCContPlanRisk where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LCContPlanDutyParam where GrpContNo = '"
				+ mGrpContNo + "'", "DELETE");

		mMap.put("delete from LCContPlanFactory where GrpContNo = '"
				+ mGrpContNo + "'", "DELETE");

		mMap.put("delete from LCContPlanParam where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put(
				"delete from LCUWError where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");

		mMap.put("delete from LCUWSub where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");

		mMap.put("delete from LCUWMaster where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LCCUWError where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LCCUWSub where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");

		mMap.put("delete from LCCUWMaster where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LCGUWError where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LCGUWSub where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");

		mMap.put("delete from LCGUWMaster where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LCGCUWError where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put(
				"delete from LCGCUWSub where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");

		mMap.put("delete from LCGCUWMaster where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LCIssuePol where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LCGrpIssuePol where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LCPENoticeItem where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LCPENotice where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LCCGrpSpec where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");

		mMap.put("delete from LCSpec where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");

		mMap.put(
				"delete from LCGrpCont where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");
		mMap.put("delete from lacommisiondetail where GrpContNo = '"
				+ mGrpContNo + "'", "DELETE");
		mMap.put("delete from lwmission where missionprop1='" + mGrpContNo
				+ "'", "DELETE");
		mMap.put("delete from LCPol where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");
		mMap.put("delete from lcgrpfee where GrpContNo = '" + mGrpContNo + "'",
				"DELETE");
		mMap.put("delete from lcgrpfeeparam where GrpContNo = '" + mGrpContNo
				+ "'", "DELETE");
		mMap.put("delete from lcascriptionrulefactory where GrpContNo = '"
				+ mGrpContNo + "'", "DELETE");
		mMap.put("delete from lcascriptionruleparams where GrpContNo = '"
				+ mGrpContNo + "'", "DELETE");
		mMap.put(mLCDelPolLog, "INSERT");

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
