package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LACommisionDetailDB;
import com.sinosoft.lis.db.LBMissionDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCCustomerImpartDB;
import com.sinosoft.lis.db.LCCustomerImpartDetailDB;
import com.sinosoft.lis.db.LCCustomerImpartParamsDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGetToAccDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccClassFeeDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccFeeDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCInsuredRelatedDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCPremToAccDB;
import com.sinosoft.lis.db.LCSpecDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LJAGetOtherDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LWFieldMapDB;
import com.sinosoft.lis.db.LWProcessInstanceDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LACommisionDetailBSchema;
import com.sinosoft.lis.schema.LBAppntSchema;
import com.sinosoft.lis.schema.LBBnfSchema;
import com.sinosoft.lis.schema.LBContSchema;
import com.sinosoft.lis.schema.LBCustomerImpartDetailSchema;
import com.sinosoft.lis.schema.LBCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LBCustomerImpartSchema;
import com.sinosoft.lis.schema.LBDutySchema;
import com.sinosoft.lis.schema.LBGetSchema;
import com.sinosoft.lis.schema.LBGetToAccSchema;
import com.sinosoft.lis.schema.LBInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LBInsureAccClassSchema;
import com.sinosoft.lis.schema.LBInsureAccFeeSchema;
import com.sinosoft.lis.schema.LBInsureAccSchema;
import com.sinosoft.lis.schema.LBInsureAccTraceSchema;
import com.sinosoft.lis.schema.LBInsuredRelatedSchema;
import com.sinosoft.lis.schema.LBInsuredSchema;
import com.sinosoft.lis.schema.LBPolSchema;
import com.sinosoft.lis.schema.LBPremSchema;
import com.sinosoft.lis.schema.LBPremToAccSchema;
import com.sinosoft.lis.schema.LBSpecSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDelPolLogSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJAGetOtherSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LOPRTManager2Schema;
import com.sinosoft.lis.schema.LWFieldMapSchema;
import com.sinosoft.lis.vschema.LACommisionDetailSet;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LBPolSet;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCCustomerImpartDetailSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGetToAccSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LWFieldMapSet;
import com.sinosoft.lis.vschema.LWProcessInstanceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:承保单整单删除BL层
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author chenrong
 * @version 1.0
 */
public class ProposalDeleteBL {
private static Logger logger = Logger.getLogger(ProposalDeleteBL.class);
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
	private LCDelPolLogSchema mLCDelPolLogSchema = new LCDelPolLogSchema();
	private LJAPaySet mLJAPaySet = new LJAPaySet();
	private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();
	private LJAPaySchema mLJAPaySchema = new LJAPaySchema();
	private LJAPayPersonSchema mLJAPayPersonSchema = new LJAPayPersonSchema();
	private TransferData mTransferData = new TransferData();

	// 保单数据打包字符串
	private String mLCContEncode = "";
	private String mDelReason = "";
	private String mRemark = "";
	// private String mDelReasonName = "";

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public ProposalDeleteBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ProposalDeleteBL proposaldeletebl = new ProposalDeleteBL();
	}

	public boolean submitData(VData cInputData, String cOperate) {

		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		if (getInputData() == false) {
			return false;
		}

		if (dealData() == false) {
			return false;
		}

		this.prepareOutputData();

		logger.debug("ProposalDeleteBL->ContNo="
				+ mLCContSchema.getContNo());

		try {
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mResult, mOperate)) {
				// 错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				return false;
			}

			return true;
		} catch (Exception ex) {
			this.mErrors.addOneError("删除失败，原因是：" + ex.toString());
			return false;
		}

	}

	/**
	 * 将UI层传输来得数据根据业务还原成具体的类
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		String tContNo = "";
		logger.debug("ProposalDeleteBL->getInputData()");
		// 全局变量实例
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			mErrors.addOneError(new CError("没有得到全局量信息"));
			return false;
		}

		this.mOperator = mGlobalInput.Operator;
		this.mManageCom = mGlobalInput.ManageCom;

		this.mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mDelReason = (String) mTransferData.getValueByName("DelReason");
		// mDelReasonName = (String)
		// mTransferData.getValueByName("DelReasonName");
		if (mDelReason == null || mDelReason.equals("")) {
			mErrors.addOneError(new CError("没有得到保单删除原因数据！"));
			return false;
		}
		mRemark = (String) mTransferData.getValueByName("Remark");
		if (mRemark == null) {
			mRemark = "";
		}

		// 承保单实例
		mLCContSchema.setSchema((LCContSchema) mInputData
				.getObjectByObjectName("LCContSchema", 0));
		tContNo = mLCContSchema.getContNo();

		if (mLCContSchema == null) {
			this.mErrors.addOneError(new CError("传入的承保单单信息为空！"));
			return false;
		}

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tContNo);
		tLCContDB.setAppFlag("1");

		LCContSet tLCContSet = tLCContDB.query();

		if ((tLCContSet == null) || (tLCContSet.size() <= 0)) {
			this.mErrors.copyAllErrors(tLCContDB.mErrors);
			this.mErrors.addOneError(new CError("未查到承保单！"));
			return false;
		}
		mLCContSchema.setSchema(tLCContSet.get(1));
		mLCContEncode = mLCContSchema.encode();

		return true;
	}

	/**
	 * 对业务数据进行加工
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		String tContNo = mLCContSchema.getContNo();
		String tPrtNo = mLCContSchema.getPrtNo();
		String tProposalContNo = mLCContSchema.getProposalContNo();
		// String tGetPolDate = mLCContSchema.getGetPolDate();
		// String tCustomGetPoldate = mLCContSchema.getCustomGetPolDate();
		String tSignDate = mLCContSchema.getSignDate();

		String tMissionID = getMissionID(tPrtNo);
		if (tMissionID != null && !"".equals(tMissionID)) {
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql("delete from lbmission where missionid = '" + "?tMissionID?"
					+ "'");
			sqlbv1.put("tMissionID", tMissionID);
			mMap.put(sqlbv1, "DELETE");
		}

		// 对于已做溢交退费的保单，建议退保处理，重新投保
		// 对于当天承保保单整单删除（当日未做日结的情况）,删除所有实收信息
		// 对于过天承保保单整单删除（未回单和已回单）,实收信息插入一条负记录进行冲正处理
		// 过天承保保单整单删除（已做薪资计算）,建议退保处理，重新投保

		if (isOverFee(tPrtNo) || isCD(tContNo)) {
			return false;
		} else {
			// mMap.put("delete from LJaget where OtherNo = '" + tPrtNo +
			// "' and OtherNoType='8' and enteraccdate is null", "DELETE");
			// mMap.put("delete from LJagetOther where OtherNo = '" + tPrtNo +
			// "' and OtherNoType='8' and FeeOperationType='YJ'" +
			// " and FeeFinaType='TF'", "DELETE");
			getAddMinus(tPrtNo);
		}

		// 判断是否为当天承保单
		if (tSignDate.equals(theCurrentDate)) {
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("delete from LJapay  where IncomeNo = '" + "?tContNo?" + "'");
			sqlbv2.put("tContNo", tContNo);
			// 删除所有实收信息
			mMap.put(sqlbv2,
					"DELETE");
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql("delete from LJapayperson where ContNo = '" + "?tContNo?"
					+ "'");
			sqlbv3.put("tContNo", tContNo);
			mMap.put(sqlbv3, "DELETE");
		} else if ((mLCContSchema.getAppFlag().equals("1"))
				&& (tSignDate != null) && !tSignDate.equals(theCurrentDate)) {
			if (isPedoritem(tContNo) || isClaim(tContNo)
					|| isCompensation(tContNo)) {
				return false;
			}
			// 实收信息插入一条负记录进行冲正处理
			payAddMinus(tContNo);
		} else {
			this.mErrors.addOneError(new CError("承保单不符合删除条件！"));
			return false;
		}

		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql("update LJtempfee set ConfFlag = '0',ConfDate = '',OtherNo='"
				+ "?tPrtNo?" + "',modifydate='" + "?theCurrentDate?" + "',modifytime='"
				+ "?theCurrentTime?" + "' where OtherNo = '" + "?tContNo?" + "'");
		sqlbv4.put("tPrtNo", tPrtNo);
		sqlbv4.put("theCurrentDate", theCurrentDate);
		sqlbv4.put("theCurrentTime", theCurrentTime);
		sqlbv4.put("tContNo", tContNo);
		// 修改暂交费信息
		mMap.put(sqlbv4,
				"UPDATE");
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql("update LJtempfeeClass set ConfFlag = '0',ConfDate = ''"
				+ ",modifydate='" + "?theCurrentDate?" + "',modifytime='"
				+ "?theCurrentTime?" + "' where OtherNo = '" + "?tPrtNo?" + "'");
		sqlbv5.put("theCurrentDate", theCurrentDate);
		sqlbv5.put("theCurrentTime", theCurrentTime);
		sqlbv5.put("tPrtNo", tPrtNo);
		mMap.put(sqlbv5,
				"UPDATE");

		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql("delete from ES_DOC_PAGES "
				+ "where DocId in (select DocId from ES_DOC_RELATION "
				+ "where BussNo = '" + "?tPrtNo?" + "')");
		sqlbv6.put("tPrtNo", tPrtNo);
		// 影像信息删除
		mMap.put(sqlbv6, "DELETE");

		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql("delete from ES_DOC_MAIN "
				+ "where DocId in (select DocId from ES_DOC_RELATION "
				+ "where BussNo = '" + "?tPrtNo?" + "')");
		sqlbv7.put("tPrtNo", tPrtNo);
		mMap.put(sqlbv7, "DELETE");
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql("delete from ES_DOC_RELATION where BussNo = '" + "?tPrtNo?" + "'");
		sqlbv8.put("tPrtNo", tPrtNo);
		mMap.put(sqlbv8,
				"DELETE");
		/**
		 * 业务数据备份------------------Beg
		 */
		// mMap.put("insert into LBPol (select '1009',l.* from lcpol l where
		// ContNo = '" +
		// tContNo + "')", "INSERT");
		backUpTable(tContNo, tPrtNo);
		// 备份个人保单
		LBContSchema tLBContSchema = new LBContSchema();
		String tLBContEncode = SysConst.PACKAGESPILTER + mLCContEncode;
		tLBContSchema.decode(tLBContEncode);
		tLBContSchema.setState("1009"); // 和银保通区别，state设为“1009”
		tLBContSchema.setModifyDate(theCurrentDate);
		tLBContSchema.setModifyTime(theCurrentTime);
		mMap.put(tLBContSchema, "DELETE&INSERT");

		/*
		 * mMap.put( "insert into LBAppnt (select '1009',l.* from LCAppnt l
		 * where ContNo = '" + tContNo + "')", "INSERT"); mMap.put("insert into
		 * LBInsureacc " + "(select '1009',l.* from LCInsureAcc l where ContNo = '" +
		 * tContNo + "')", "INSERT"); mMap.put("insert into LBInsureAccClass " +
		 * "(select '1009',l.* from LCInsureAccClass l where ContNo = '" +
		 * tContNo + "')", "INSERT"); mMap.put("insert into LBInsureAccTrace " +
		 * "(select '1009',l.* from LCInsureAccTrace l where ContNo = '" +
		 * tContNo + "')", "INSERT"); mMap.put("insert into LBInsureAccFee " +
		 * "(select '1009',l.* from LCInsureAccFee l where ContNo = '" + tContNo +
		 * "')", "INSERT"); mMap.put("insert into LBInsureAccClassFee " +
		 * "(select '1009',l.* from LCInsureAccClassFee l where ContNo = '" +
		 * tContNo + "')", "INSERT"); mMap.put("insert into LBInsuredrelated " +
		 * "(select '1009',l.* from LCInsuredrelated l where PolNo in " +
		 * "(select PolNo from LCPol where ContNo = '" + tContNo + "'))",
		 * "INSERT"); mMap.put("insert into LBInsured " + "(select '1009',l.*
		 * from LCInsured l where ContNo = '" + tContNo + "')", "INSERT");
		 * mMap.put( "insert into LBBnf (select '1009',l.* from LCBnf l where
		 * ContNo = '" + tContNo + "')", "INSERT"); mMap.put( "insert into
		 * LBDuty (select '1009',l.* from LCDuty l where ContNo = '" + tContNo +
		 * "')", "INSERT"); mMap.put("insert into LBPrem " + "(select '1009',l.*
		 * from LCPrem l " + "where PolNo in (select PolNo from LCPol where
		 * ContNo = '" + tContNo + "'))", "INSERT"); mMap.put("insert into
		 * LBPremToAcc " + "(select '1009',l.* from LCPremToAcc l " + "where
		 * PolNo in (select PolNo from LCPol where ContNo = '" + tContNo +
		 * "'))", "INSERT"); mMap.put( "insert into LBGet (select '1009',l.*
		 * from LCGet l where ContNo = '" + tContNo + "')", "INSERT");
		 * mMap.put("insert into LBGetToAcc " + "(select '1009',l.* from
		 * LCGetToAcc l " + "where PolNo in (select PolNo from LCPol where
		 * ContNo = '" + tContNo + "'))", "INSERT"); mMap.put("insert into
		 * LBCustomerimpart (select '" + tContNo + "',l.* from LCCustomerimpart
		 * l where ContNo = '" + tContNo + "')", "INSERT"); mMap.put("insert
		 * into LBCustomerImpartParams " + "(select '1009',l.* from
		 * LCCustomerImpartParams l where ContNo = '" + tContNo + "')",
		 * "INSERT"); mMap.put("insert into LBCustomerimpartdetail " + "(select
		 * '1009',l.* from LCCustomerimpartdetail l where ContNo = '" + tContNo +
		 * "')", "INSERT"); mMap.put( "insert into LBSpec (select l.* from
		 * LCSpec l where ContNo = '" + tContNo + "')", "INSERT");
		 */
		/**
		 * 业务数据备份------------------End
		 */

		/**
		 * 删除承保单信息------------------Beg
		 */
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql("delete from LCAppnt where ContNo = '" + "?tContNo?" + "'");
		sqlbv9.put("tContNo", tContNo);
		// 业务数据记录表
		mMap.put(sqlbv9,
				"DELETE"); // 个单投保人信息
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql("delete from LCInsured where ContNo = '" + "?tContNo?" + "'");
		sqlbv10.put("tContNo", tContNo);
		mMap.put(sqlbv10,
				"DELETE"); // 个单被保人信息
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql("delete from LCBnf where ContNo = '" + "?tContNo?" + "'");
		sqlbv11.put("tContNo", tContNo);
		mMap.put(sqlbv11,
						"DELETE"); // 受益人信息
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql("delete from LCDuty where ContNo = '" + "?tContNo?" + "'");
		sqlbv12.put("tContNo", tContNo);
		mMap.put(sqlbv12,
				"DELETE"); // 保单险种责任信息
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql("delete from LCPrem where ContNo = '" + "?tContNo?" + "'");
		sqlbv13.put("tContNo", tContNo);
		mMap.put(sqlbv13,
				"DELETE"); // 保费项信息
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql("delete from LCPremToAcc "
				+ "where PolNo in (select PolNo from LCPol where ContNo = '"
				+ "?tContNo?" + "')");
		sqlbv14.put("tContNo", tContNo);
		mMap.put(sqlbv14, "DELETE");
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.sql("delete from LCGet where ContNo = '" + "?tContNo?" + "'");
		sqlbv15.put("tContNo", tContNo);
		mMap.put(sqlbv15,
						"DELETE"); // 领取项信息
		SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
		sqlbv16.sql("delete from LCGetToAcc "
				+ "where PolNo in (select PolNo from LCPol where ContNo = '"
				+ "?tContNo?" + "')");
		sqlbv16.put("tContNo", tContNo);
		mMap.put(sqlbv16, "DELETE");
		SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
		sqlbv17.sql("delete from LCContstate where ContNo = '" + "?tContNo?" + "'");
		sqlbv17.put("tContNo", tContNo);
		mMap.put(sqlbv17,
				"DELETE"); // 保单状态信息
		SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
		sqlbv18.sql("delete from LCContHangupstate where ContNo = '" + "?tContNo?"
				+ "'");
		sqlbv18.put("tContNo", tContNo);
		mMap.put(sqlbv18, "DELETE");
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		sqlbv19.sql("delete from LAcommisiondetail where GrpContNo = '" + "?tContNo?"
				+ "'");
		sqlbv19.put("tContNo", tContNo);
		mMap.put(sqlbv19, "DELETE"); // 保单发佣分配
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		sqlbv20.sql("delete from LCCustomerImpart where ContNo = '" + "?tContNo?"
				+ "'");
		sqlbv20.put("tContNo", tContNo);
		mMap.put(sqlbv20, "DELETE"); // 客户告知信息
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		sqlbv21.sql("delete from LCCustomerImpartParams where ContNo = '"
				+ "?tContNo?" + "'");
		sqlbv21.put("tContNo", tContNo);
		mMap.put(sqlbv21, "DELETE"); // 客户告知参数信息
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		sqlbv22.sql("delete from LCCustomerimpartdetail where ContNo = '"
				+ "?tContNo?" + "'");
		sqlbv22.put("tContNo", tContNo);
		mMap.put(sqlbv22, "DELETE"); // 客户告知明细表
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		sqlbv23.sql("delete from LCInsureAccFee where ContNo = '" + "?tContNo?" + "'");
		sqlbv23.put("tContNo", tContNo);
		mMap.put(sqlbv23,
				"DELETE"); // 保险帐户管理费信息
		SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
		sqlbv24.sql("delete from LCInsureAccClassFee where ContNo = '" + "?tContNo?"
				+ "'");
		sqlbv24.put("tContNo", tContNo);
		mMap.put(sqlbv24, "DELETE"); // 保险账户管理费分类信息
		SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
		sqlbv25.sql("delete from LCInsureAccClass where ContNo = '" + "?tContNo?"
				+ "'");
		sqlbv25.put("tContNo", tContNo);
		mMap.put(sqlbv25, "DELETE"); // 保险账户分类信息
		SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
		sqlbv26.sql("delete from LCInsureAcc where ContNo = '" + "?tContNo?" + "'");
		sqlbv26.put("tContNo", tContNo);
		mMap.put(sqlbv26,
				"DELETE"); // 保险帐户信息
		SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
		sqlbv27.sql("delete from LCInsureAccTrace where ContNo = '" + "?tContNo?"
				+ "'");
		sqlbv27.put("tContNo", tContNo);
		mMap.put(sqlbv27, "DELETE"); // 保险帐户表记价履历信息
		SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
		sqlbv28.sql("delete from LCInsuredrelated "
				+ "where PolNo in (select PolNo from LCPol where ContNo = '"
				+ "?tContNo?" + "')");
		sqlbv28.put("tContNo", tContNo);
		mMap.put(sqlbv28, "DELETE"); // 个单连带被保人表
		SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
		sqlbv29.sql("delete from LCSpec where PolNo in (select PolNo from LCPol where ContNo = '"
				+ "?tContNo?" + "')");
		sqlbv29.put("tContNo", tContNo);
		mMap.put(sqlbv29, "DELETE"); // 特约信息
		SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
		sqlbv30.sql("delete from LCCont where ContNo = '" + "?tContNo?" + "'");
		sqlbv30.put("tContNo", tContNo);
		mMap.put(sqlbv30,
				"DELETE"); // 个人保单信息
		SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
		sqlbv31.sql("delete from LCPol where ContNo = '" + "?tContNo?" + "'");
		sqlbv31.put("tContNo", tContNo);
		mMap.put(sqlbv31,
						"DELETE"); // 个人险种信息
		SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
		sqlbv32.sql("delete from LCUWError where ContNo='" + "?tContNo?" + "'");
		sqlbv32.put("tContNo", tContNo);
		// 保单核保信息表
		mMap.put(sqlbv32,
				"DELETE"); // 个人险种核保错误信息
		SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
		sqlbv33.sql("delete from LCUWSub where ContNo='" + "?tContNo?" + "'");
		sqlbv33.put("tContNo", tContNo);
		mMap.put(sqlbv33,
						"DELETE"); // 个人险种核保核保轨迹信息
		SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
		sqlbv34.sql("delete from LCUWMaster where ContNo='" + "?tContNo?" + "'");
		sqlbv34.put("tContNo", tContNo);
		mMap.put(sqlbv34,
				"DELETE"); // 个人险种核保最近结果信息
		SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
		sqlbv35.sql("delete from LCCUWError where ContNo = '" + "?tPrtNo?"
				+ "' or ContNo='" + "?tContNo?" + "'");
		sqlbv35.put("tContNo", tContNo);
		sqlbv35.put("tPrtNo", tPrtNo);
		mMap.put(sqlbv35, "DELETE"); // 个人合同核保错误信息表
		SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
		sqlbv36.sql("delete from LCCUWSub where ContNo = '" + "?tPrtNo?"
				+ "' or ContNo='" + "?tContNo?" + "'");
		sqlbv36.put("tPrtNo", tPrtNo);
		sqlbv36.put("tContNo", tContNo);
		mMap.put(sqlbv36, "DELETE"); // 个人合同核保核保轨迹表
		
		SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
		sqlbv37.sql("delete from LCCUWMaster where ProposalContNo = '"
				+ "?tProposalContNo?" + "'");
		sqlbv37.put("tProposalContNo", tProposalContNo);
		mMap.put(sqlbv37, "DELETE"); // 个人核保最近结果表
		SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
		sqlbv38.sql("delete from LOPRTManager  where OtherNo = '" + "?tPrtNo?"
				+ "' or OtherNo='" + "?tContNo?" + "'");
		sqlbv38.put("tPrtNo", tPrtNo);
		sqlbv38.put("tContNo", tContNo);
		mMap.put(sqlbv38, "DELETE");
		SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
		sqlbv39.sql("delete from LCIssuepol where ProposalContNo = '"
				+ "?tProposalContNo?" + "'");
		sqlbv39.put("tProposalContNo", tProposalContNo);
		mMap.put(sqlbv39, "DELETE"); // 问题件表
		SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
		sqlbv40.sql("delete from LCPenoticeitem where ProposalContNo = '"
				+ "?tProposalContNo?" + "'");
		sqlbv40.put("tProposalContNo", tProposalContNo);
		mMap.put(sqlbv40, "DELETE"); // 体检通知对应的体检项目
		SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
		sqlbv41.sql("delete from LCPenotice where ProposalContNo = '"
				+ "?tProposalContNo?" + "'");
		sqlbv41.put("tProposalContNo", tProposalContNo);
		mMap.put(sqlbv41, "DELETE"); // 体检通知
		SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
		sqlbv42.sql("delete from LCPenoticeResult where ProposalContNo = '"
				+ "?tProposalContNo?" + "'");
		sqlbv42.put("tProposalContNo", tProposalContNo);
		mMap.put(sqlbv42, "DELETE"); // 体检结果
		SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
		sqlbv43.sql("delete from LCRreportitem where ProposalContNo = '"
				+ "?tProposalContNo?" + "'");
		sqlbv43.put("tProposalContNo", tProposalContNo);
		mMap.put(sqlbv43, "DELETE"); // 生调通知对应的生调项目
		SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
		sqlbv44.sql("delete from LCRreport where ProposalContNo = '"
				+ "?tProposalContNo?" + "'");
		sqlbv44.put("tProposalContNo", tProposalContNo);
		mMap.put(sqlbv44, "DELETE"); // 生存调查报告表
		SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
		sqlbv45.sql("delete from LCRreportprt where ContNo = '" + "?tPrtNo?"
				+ "' or ContNo='" + "?tContNo?" + "'");
		sqlbv45.put("tPrtNo", tPrtNo);
		sqlbv45.put("tContNo", tContNo);
		mMap.put(sqlbv45, "DELETE"); // 生调打印关联表
		SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
		sqlbv46.sql("delete from LCRreportresult where ProposalContNo = '"
				+ "?tProposalContNo?" + "'");
		sqlbv46.put("tProposalContNo", tProposalContNo);
		mMap.put(sqlbv46, "DELETE"); // 生调结果
		SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
		sqlbv47.sql("delete from LCUwsendtrace where OtherNo = '" + "?tContNo?"
				+ "' or OtherNo='" + "?tPrtNo?" + "'");
		sqlbv47.put("tContNo", tContNo);
		sqlbv47.put("tPrtNo", tPrtNo);
		mMap.put(sqlbv47, "DELETE"); // 核保上报轨迹表
		SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
		sqlbv48.sql("delete from LCReinsurereport where ContNo = '" + "?tPrtNo?"
				+ "' or ContNo='" + "?tContNo?" + "'");
		sqlbv48.put("tPrtNo", tPrtNo);
		sqlbv48.put("tContNo", tContNo);
		mMap.put(sqlbv48, "DELETE"); // 再保呈报表
		SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
		sqlbv49.sql("delete from LCReinsureinfo where ContNo = '" + "?tPrtNo?"
				+ "' or ContNo='" + "?tContNo?" + "'");
		sqlbv49.put("tPrtNo", tPrtNo);
		sqlbv49.put("tContNo", tContNo);
		mMap.put(sqlbv49, "DELETE"); // 再保信息表
		SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
		sqlbv50.sql("delete from LCReinsurereporttrace where ContNo = '" + "?tPrtNo?"
				+ "' or ContNo='" + "?tContNo?" + "'");
		sqlbv50.put("tPrtNo", tPrtNo);
		sqlbv50.put("tContNo", tContNo);
		mMap.put(sqlbv50, "DELETE"); // 再保呈报轨迹表
		SQLwithBindVariables sqlbv51 = new SQLwithBindVariables();
		sqlbv51.sql("delete from LCNotepad where ContNo = '" + "?tPrtNo?"
				+ "' or ContNo='" + "?tContNo?" + "'");
		sqlbv51.put("tPrtNo", tPrtNo);
		sqlbv51.put("tContNo", tContNo);
		mMap.put(sqlbv51, "DELETE"); // 记事本表

		SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
		sqlbv52.sql("delete from LAorphanpolicy where ContNo = '" + "?tContNo?" + "'");
		sqlbv52.put("tContNo", tContNo);
		// 其它
		mMap.put(sqlbv52,
				"DELETE"); // 孤儿单信息
		SQLwithBindVariables sqlbv53 = new SQLwithBindVariables();
		sqlbv53.sql("delete from LDContinvoicemap where ContNo = '" + "?tContNo?"
				+ "'");
		sqlbv53.put("tContNo", tContNo);
		mMap.put(sqlbv53, "DELETE"); // 保单发票对应表
		SQLwithBindVariables sqlbv54 = new SQLwithBindVariables();
		sqlbv54.sql("delete from LCServinfo where ContNo = '" + "?tPrtNo?"
				+ "' or ContNo='" + "?tContNo?" + "'");
		sqlbv54.put("tPrtNo", tPrtNo);
		sqlbv54.put("tContNo", tContNo);
		mMap.put(sqlbv54, "DELETE"); // 个单服务信息表
		SQLwithBindVariables sqlbv55 = new SQLwithBindVariables();
		sqlbv55.sql("delete from LCErrorreport where OtherNo = '" + "?tContNo?"
				+ "' or OtherNo='" + "?tPrtNo?" + "'");
		sqlbv55.put("tContNo", tContNo);
		sqlbv55.put("tPrtNo", tPrtNo);
		mMap.put(sqlbv55, "DELETE"); // 差错率统计表
		// mMap.put("delete from LAcommision where ContNo = '" + tContNo + "'",
		// "DELETE"); //直接佣金信息
		/**
		 * 删除承保单信息------------------End
		 */

		// 添加删除日志信息
		mLCDelPolLogSchema.setOtherNo(tContNo);
		mLCDelPolLogSchema.setOtherNoType("12");
		mLCDelPolLogSchema.setPrtNo(mLCContSchema.getPrtNo());
		mLCDelPolLogSchema.setIsPolFlag("1");
		mLCDelPolLogSchema.setOperator(mOperator);
		mLCDelPolLogSchema.setManageCom(mManageCom);
		mLCDelPolLogSchema.setMakeDate(theCurrentDate);
		mLCDelPolLogSchema.setMakeTime(theCurrentTime);
		mLCDelPolLogSchema.setModifyDate(theCurrentDate);
		mLCDelPolLogSchema.setModifyTime(theCurrentTime);
		mLCDelPolLogSchema.setDelReason(mDelReason);
		mLCDelPolLogSchema.setRemark(mRemark);
		mMap.put(mLCDelPolLogSchema, "INSERT");

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
	 * 备份保单险种表
	 * 
	 * @param mContNo
	 *            String
	 */
	private void backUpPol(String mContNo) {
		Reflections ref = new Reflections();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		LBPolSet tLBPolSet = new LBPolSet();

		// String tLCPolEncode = "";
		// String tLBPolEncode = "";

		tLCPolDB.setContNo(mContNo);

		tLCPolSet = tLCPolDB.query();

		if (tLCPolSet != null && tLCPolSet.size() > 0) {
			for (int i = 0; i < tLCPolSet.size(); i++) {
				LBPolSchema tLBPolSchema = new LBPolSchema();
				ref.transFields(tLBPolSchema, tLCPolSet.get(i + 1));
				tLCPolSchema = tLCPolSet.get(i + 1);
				// tLCPolEncode = tLCPolSchema.encode();
				// tLBPolEncode = SysConst.PACKAGESPILTER + tLCPolEncode;
				// tLBPolSchema.decode(tLBPolEncode);
				tLBPolSchema.setSignDate(theCurrentDate);
				tLBPolSchema.setModifyDate(theCurrentDate);
				tLBPolSchema.setModifyTime(theCurrentTime);
				tLBPolSchema.setEdorNo(mContNo);
				tLBPolSet.add(tLBPolSchema);

				LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB();
				LCInsuredRelatedSet tLCInsuredRelatedSet = new LCInsuredRelatedSet();
				tLCInsuredRelatedDB.setPolNo(tLCPolSchema.getPolNo());
				tLCInsuredRelatedSet = tLCInsuredRelatedDB.query();
				if (tLCInsuredRelatedSet != null
						&& tLCInsuredRelatedSet.size() > 0) {
					for (int j = 1; j <= tLCInsuredRelatedSet.size(); j++) {
						LBInsuredRelatedSchema tLBInsuredRelatedSchema = new LBInsuredRelatedSchema();
						ref.transFields(tLBInsuredRelatedSchema,
								tLCInsuredRelatedSet.get(j));
						tLBInsuredRelatedSchema.setModifyDate(theCurrentDate);
						tLBInsuredRelatedSchema.setModifyTime(theCurrentTime);
						mMap.put(tLBInsuredRelatedSchema, "DELETE&INSERT");
					}
				}

				LCSpecDB tLCSpecDB = new LCSpecDB();
				LCSpecSet tLCSpecSet = new LCSpecSet();
				tLCSpecDB.setPolNo(tLCPolSchema.getPolNo());
				tLCSpecSet = tLCSpecDB.query();
				if (tLCSpecSet != null && tLCSpecSet.size() > 0) {
					for (int j = 1; j <= tLCSpecSet.size(); j++) {
						LBSpecSchema tLBSpecSchema = new LBSpecSchema();
						ref.transFields(tLBSpecSchema, tLCSpecSet.get(j));
						tLBSpecSchema.setModifyDate(theCurrentDate);
						tLBSpecSchema.setModifyTime(theCurrentTime);
						mMap.put(tLBSpecSchema, "DELETE&INSERT");
					}
				}

				LCPremToAccDB tLCPremToAccDB = new LCPremToAccDB();
				LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
				tLCPremToAccDB.setPolNo(tLCPolSchema.getPolNo());
				tLCPremToAccSet = tLCPremToAccDB.query();
				if (tLCPremToAccSet != null && tLCPremToAccSet.size() > 0) {
					for (int j = 1; j <= tLCPremToAccSet.size(); j++) {
						LBPremToAccSchema tLBPremToAccSchema = new LBPremToAccSchema();
						ref.transFields(tLBPremToAccSchema, tLCPremToAccSet
								.get(j));
						tLBPremToAccSchema.setModifyDate(theCurrentDate);
						tLBPremToAccSchema.setModifyTime(theCurrentTime);
						mMap.put(tLBPremToAccSchema, "DELETE&INSERT");
					}
				}

				LCGetToAccDB tLCGetToAccDB = new LCGetToAccDB();
				LCGetToAccSet tLCGetToAccSet = new LCGetToAccSet();
				tLCGetToAccDB.setPolNo(tLCPolSchema.getPolNo());
				tLCGetToAccSet = tLCGetToAccDB.query();
				if (tLCGetToAccSet != null && tLCGetToAccSet.size() > 0) {
					for (int j = 1; j <= tLCGetToAccSet.size(); j++) {
						LBGetToAccSchema tLBGetToAccSchema = new LBGetToAccSchema();
						ref.transFields(tLBGetToAccSchema, tLCGetToAccSet
								.get(j));
						tLBGetToAccSchema.setModifyDate(theCurrentDate);
						tLBGetToAccSchema.setModifyTime(theCurrentTime);
						mMap.put(tLBGetToAccSchema, "DELETE&INSERT");
					}
				}
			}
			mMap.put(tLBPolSet, "INSERT");
		}

	}

	/**
	 * 判断是否已做保全
	 * 
	 * @param mContNo
	 *            String
	 * @return boolean
	 */
	private boolean isPedoritem(String mContNo) {
		// LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		// LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		// tLPEdorItemDB.setContNo(mContNo);
		// tLPEdorItemSet = tLPEdorItemDB.query();
		// if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
		// return false;
		// }
		SQLwithBindVariables sqlbv56 = new SQLwithBindVariables();
		String tSQL = "";
		tSQL = "select count(*) from LPEdorItem where ContNo='" + "?mContNo?" + "'";
		sqlbv56.sql(tSQL);
		sqlbv56.put("mContNo", mContNo);
		ExeSQL tExeSQL = new ExeSQL();
		String tCount = tExeSQL.getOneValue(sqlbv56);
		if (tCount == null || tCount.equals("0")) {
			return false;
		}
		this.mErrors.addOneError(new CError("承保单已做保全，建议退保或重新投保！"));
		return true;
	}

	/**
	 * 判断是否已做理赔
	 * 
	 * @param mContNo
	 *            String
	 * @return boolean
	 */
	private boolean isClaim(String mContNo) {
		// LLClaimPolicySet tLLClaimPolicySet = new LLClaimPolicySet();
		// LLClaimPolicyDB tLLClaimPolicyDB = new LLClaimPolicyDB();
		// tLLClaimPolicyDB.setContNo(mContNo);
		// tLLClaimPolicySet = tLLClaimPolicyDB.query();
		// if (tLLClaimPolicySet == null || tLLClaimPolicySet.size() <= 0) {
		// return false;
		// }
		String tSQL = "";
		SQLwithBindVariables sqlbv57 = new SQLwithBindVariables();
		tSQL = "select count(*) from LLClaimPolicy where ContNo='" + "?mContNo?"
				+ "'";
		sqlbv57.sql(tSQL);
		sqlbv57.put("mContNo", mContNo);
		ExeSQL tExeSQL = new ExeSQL();
		String tCount = tExeSQL.getOneValue(sqlbv57);
		if (tCount == null || tCount.equals("0")) {
			return false;
		}
		this.mErrors.addOneError(new CError("承保单已做理赔，建议退保或重新投保！"));
		return true;
	}

	/**
	 * 判断是否做溢交退费
	 * 
	 * @param mPrtNO
	 *            String
	 * @return boolean
	 */
	private boolean isOverFee(String mPrtNO) {
		String tSQL = "";
		ExeSQL tExeSQL = new ExeSQL();
		String tEnterAccDate = "";
		SQLwithBindVariables sqlbv58 = new SQLwithBindVariables();
		tSQL = "select enteraccdate from Ljaget where otherno='" + "?mPrtNO?"
				+ "' and  othernotype='8'";
		sqlbv58.sql(tSQL);
		sqlbv58.put("mPrtNO", mPrtNO);
		tEnterAccDate = tExeSQL.getOneValue(sqlbv58);
		if (tEnterAccDate != null && !tEnterAccDate.trim().equals("")) {
			this.mErrors.addOneError(new CError("承保单已做溢交退费，不能删除，建议退保或重新投保！"));
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 判断是否抽档
	 * 
	 * @param mPrtNO
	 *            String
	 * @return boolean
	 */
	private boolean isCD(String mContNO) {
		String tSQL = "";
		ExeSQL tExeSQL = new ExeSQL();
		String tCount = "";
		SQLwithBindVariables sqlbv59 = new SQLwithBindVariables();
		tSQL = "select count(1) from ljspay where otherno='" + "?mContNO?" + "'";
		sqlbv59.sql(tSQL);
		sqlbv59.put("mContNO", mContNO);
		tCount = tExeSQL.getOneValue(sqlbv59);
		if (tCount != null && !tCount.trim().equals("0")) {
			this.mErrors.addOneError(new CError("此保单已经抽档，不能做整单删除！"));
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 判断是否已做薪资计算
	 * 
	 * @param mContNo
	 *            String
	 * @return boolean
	 */
	private boolean isCompensation(String mContNo) {
		// String WageNo = "";
		// String ManageCom = "";
		// String BranchType = "";
		// String BranchType2 = "";
		String tSQL = "";
		ExeSQL tExeSQL = new ExeSQL();
		String tState = "";
		SQLwithBindVariables sqlbv60 = new SQLwithBindVariables();
		
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tSQL = "Select state from LAWageHistory "
					+ "where ((case when wageno is not null then wageno  else 0 end), trim(managecom), trim(branchtype), trim(branchtype2)) = "
					+ "(select (case when wageno is not null then wageno  else 0 end),trim(managecom),trim(branchtype),trim(branchtype2) from "
					+ "LACommision where contno='" + "?mContNo?"
					+ "' and MainPolNo=PolNo and PayYear='0' and rownum=1)";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tSQL = "Select state from LAWageHistory "
					+ "where ((case when wageno is not null then wageno  else 0 end), trim(managecom), trim(branchtype), trim(branchtype2)) = "
					+ "(select (case when wageno is not null then wageno  else 0 end),trim(managecom),trim(branchtype),trim(branchtype2) from "
					+ "LACommision where contno='" + "?mContNo?"
					+ "' and MainPolNo=PolNo and PayYear='0'  limit 0,1)";
		}
		sqlbv60.sql(tSQL);
		sqlbv60.put("mContNo", mContNo);
		tState = tExeSQL.getOneValue(sqlbv60);
		// State值为“21”或者为“14”证明已经做薪资计算
		if (tState != null
				&& (tState.trim().equals("21") || tState.trim().equals("14"))) {
			this.mErrors.addOneError(new CError("承保单已做薪资计算，建议退保或重新投保！"));
			return true;
		}

		// LACommisionSchema tLACommisionSchema = new LACommisionSchema();
		// LACommisionDB tLACommisionDB = new LACommisionDB();
		// tSQL = "select * from LACommision "
		// + "where contno = '" + mContNo +
		// "' and payyear='0' and polno=mainpolno";
		// LACommisionSet tLACommisionSet = tLACommisionDB.executeQuery(tSQL);
		// if (tLACommisionSet.size() > 0) {
		// tLACommisionSchema.setSchema(tLACommisionSet.get(1));
		// }
		// else {
		// return false;
		// }
		// WageNo = tLACommisionSchema.getWageNo();
		// ManageCom = tLACommisionSchema.getManageCom();
		// BranchType = tLACommisionSchema.getBranchType();
		// BranchType2 = tLACommisionSchema.getBranchType2();
		//
		// LAWageHistorySchema tLAWageHistorySchema = new LAWageHistorySchema();
		// LAWageHistoryDB tLAWageHistoryDB = new LAWageHistoryDB();
		// tLAWageHistoryDB.setWageNo(WageNo);
		// tLAWageHistoryDB.setManageCom(ManageCom);
		// tLAWageHistoryDB.setBranchType(BranchType);
		// tLAWageHistoryDB.setBranchType2(BranchType2);
		//
		// LAWageHistorySet tLAWageHistorySet = new LAWageHistorySet();
		// tLAWageHistorySet = tLAWageHistoryDB.query();
		// if (tLAWageHistorySet.size() > 0) {
		// tLAWageHistorySchema.setSchema(tLAWageHistorySet.get(1));
		//
		// //State值为“21”或者为“14”证明已经做薪资计算
		// if (tLAWageHistorySchema.getState().equals("21")
		// || tLAWageHistorySchema.getState().equals("14")) {
		// this.mErrors.addOneError(new CError("承保单已做薪资计算，建议退保或重新投保！"));
		// return true;
		// }
		// }
		return false;
	}

	/**
	 * 实收信息加入负记录进行冲正
	 */
	private void payAddMinus(String mContNo) {
		LJAPaySchema tLJAPaySchema = null;
		LJAPaySet tLJAPaySet = new LJAPaySet();
		LJAPayPersonSchema tLJAPayPersonSchema = null;
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		LJAPayDB tLJAPayDB = new LJAPayDB();
		LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
		String tPayNo = ""; // 收据号
		String tLimit = "";

		// 实收总表实例
		tLJAPayDB.setIncomeNo(mContNo);
		mLJAPaySet = tLJAPayDB.query();

		// 实收个人交费实例
		tLJAPayPersonDB.setContNo(mContNo);
		mLJAPayPersonSet = tLJAPayPersonDB.query();

		if (mLJAPaySet.size() > 0) {
			for (int i = 1; i <= mLJAPaySet.size(); i++) {
				mLJAPaySchema = mLJAPaySet.get(i);
				tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
				tPayNo = PubFun1.CreateMaxNo("PAYNO", tLimit); // 创建新收据号
				tLJAPaySchema = new LJAPaySchema();
				tLJAPaySchema = mLJAPaySchema.getSchema();
				tLJAPaySchema.setSumActuPayMoney(-mLJAPaySchema
						.getSumActuPayMoney()); // 冲正处理,设为原来的负值
				tLJAPaySchema.setPayNo(tPayNo); // 设置数据号
				tLJAPaySchema.setConfDate(theCurrentDate);
				tLJAPaySchema.setMakeDate(theCurrentDate);
				tLJAPaySchema.setMakeTime(theCurrentTime);
				tLJAPaySchema.setModifyDate(theCurrentDate);
				tLJAPaySchema.setModifyTime(theCurrentTime);
				tLJAPaySchema.setPayTypeFlag("9");
				tLJAPaySet.add(tLJAPaySchema);

				if (mLJAPayPersonSet.size() > 0) {
					for (int j = 1; j <= mLJAPayPersonSet.size(); j++) {
						tLJAPayPersonSchema = new LJAPayPersonSchema();
						mLJAPayPersonSchema = mLJAPayPersonSet.get(j);

						// 根据实收总表的收据号，获取实收个人交费记录，并进行冲正处理
						if (mLJAPayPersonSchema.getPayNo().equals(
								mLJAPaySchema.getPayNo())) {
							tLJAPayPersonSchema = mLJAPayPersonSchema
									.getSchema();
							tLJAPayPersonSchema
									.setSumActuPayMoney(-mLJAPayPersonSchema
											.getSumActuPayMoney());
							tLJAPayPersonSchema
									.setSumDuePayMoney(-mLJAPayPersonSchema
											.getSumDuePayMoney());
							tLJAPayPersonSchema.setPayNo(tPayNo); // 对应实收总表冲正处理收据号
							tLJAPayPersonSchema.setConfDate(theCurrentDate);
							tLJAPayPersonSchema.setMakeDate(theCurrentDate);
							tLJAPayPersonSchema.setMakeTime(theCurrentTime);
							tLJAPayPersonSchema.setModifyDate(theCurrentDate);
							tLJAPayPersonSchema.setModifyTime(theCurrentTime);
							tLJAPayPersonSchema.setPayTypeFlag("9");
							tLJAPayPersonSet.add(tLJAPayPersonSchema);
							// mLJAPayPersonSet.remove(mLJAPayPersonSchema);
							// break;
						}

					}
				}
			}
		}
		mMap.put(tLJAPaySet, "INSERT"); // 添加实收总表冲正记录
		mMap.put(tLJAPayPersonSet, "INSERT"); // 添加个人交费冲正记录
	}

	/**
	 * 实付信息加入负记录进行冲正
	 */
	private void getAddMinus(String mPrtNo) {
		LJAGetSchema tLJAGetSchema = null;
		LJAGetSet tLJAGetSet = new LJAGetSet();
		LJAGetSet ttLJAGetSet = new LJAGetSet();
		LJAGetOtherSchema tLJAGetOtherSchema = null;
		LJAGetOtherSet tLJAGetOtherSet = new LJAGetOtherSet();
		LJAGetOtherSet ttLJAGetOtherSet = new LJAGetOtherSet();
		LJAGetDB tLJAGetDB = new LJAGetDB();
		LJAGetOtherDB tLJAGetOtherDB = new LJAGetOtherDB();
		String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		String tSQL = "";
		SQLwithBindVariables sqlbv61 = new SQLwithBindVariables();
		tSQL = "select * from LJaget  where OtherNo = '" + "?mPrtNo?"
				+ "' and OtherNoType='8' and enteraccdate is null";
		sqlbv61.sql(tSQL);
		sqlbv61.put("mPrtNo", mPrtNo);
		tLJAGetSet = tLJAGetDB.executeQuery(sqlbv61);

		tSQL = "select * from LJagetOther  where OtherNo = '" + "?mPrtNo?"
				+ "' and OtherNoType='8' and FeeOperationType='YJ'"
				+ " and FeeFinaType='TF'";
		sqlbv61.sql(tSQL);
		sqlbv61.put("mPrtNo", mPrtNo);
		tLJAGetOtherSet = tLJAGetOtherDB.executeQuery(sqlbv61);

		if (tLJAGetSet != null && tLJAGetSet.size() > 0) {
			String getNo = "";
			for (int i = 1; i <= tLJAGetSet.size(); i++) {
				tLJAGetSchema = tLJAGetSet.get(i).getSchema();
				String tOldActuGeNo = tLJAGetSchema.getActuGetNo();
				double tSumMoney = tLJAGetSchema.getSumGetMoney();
				getNo = "";
				getNo = PubFun1.CreateMaxNo("GETNO", tLimit);
				tLJAGetSchema.setActuGetNo(getNo);
				tLJAGetSchema.setSumGetMoney(-tSumMoney);
				tLJAGetSchema.setEnterAccDate(theCurrentDate);
				tLJAGetSchema.setConfDate(theCurrentDate);
				tLJAGetSchema.setMakeDate(theCurrentDate);
				tLJAGetSchema.setMakeTime(theCurrentTime);
				tLJAGetSchema.setModifyDate(theCurrentDate);
				tLJAGetSchema.setModifyTime(theCurrentTime);
				ttLJAGetSet.add(tLJAGetSchema);
				if (tLJAGetOtherSet != null && tLJAGetOtherSet.size() > 0) {
					for (int j = 1; j <= tLJAGetOtherSet.size(); j++) {
						tLJAGetOtherSchema = tLJAGetOtherSet.get(j).getSchema();
						double tGetMoney = tLJAGetOtherSchema.getGetMoney();
						if (tLJAGetOtherSchema.getActuGetNo().trim().equals(
								tOldActuGeNo.trim())) {
							tLJAGetOtherSchema.setActuGetNo(getNo);
							tLJAGetOtherSchema.setGetMoney(-tGetMoney);
							tLJAGetOtherSchema.setConfDate(theCurrentDate);
							tLJAGetOtherSchema.setEnterAccDate(theCurrentDate);
							tLJAGetOtherSchema.setMakeDate(theCurrentDate);
							tLJAGetOtherSchema.setMakeTime(theCurrentTime);
							tLJAGetOtherSchema.setModifyDate(theCurrentDate);
							tLJAGetOtherSchema.setModifyTime(theCurrentTime);
							ttLJAGetOtherSet.add(tLJAGetOtherSchema);
						}
					}
				}

			}
		}
		SQLwithBindVariables sqlbv63 = new SQLwithBindVariables();
		sqlbv63.sql("update LJAGet set enteraccdate='" + "?theCurrentDate?"
				+ "',confdate='" + "?theCurrentDate?" + "',modifyDate='"
				+ "?theCurrentDate?" + "' where OtherNo = '" + "?mPrtNo?"
				+ "' and OtherNoType='8' and enteraccdate is null");
		sqlbv63.put("theCurrentDate", theCurrentDate);
		sqlbv63.put("mPrtNo", mPrtNo);
		mMap.put(sqlbv63, "UPDATE");
		SQLwithBindVariables sqlbv64 = new SQLwithBindVariables();
		sqlbv64.sql("update LJAGetOther set enteraccdate='" + "?theCurrentDate?"
				+ "',confdate='" + "?theCurrentDate?" + "',modifyDate='"
				+ "?theCurrentDate?" + "' where OtherNo = '" + "?mPrtNo?"
				+ "' and OtherNoType='8' and FeeOperationType='YJ'"
				+ " and FeeFinaType='TF'");
		sqlbv64.put("theCurrentDate", theCurrentDate);
		sqlbv64.put("mPrtNo", mPrtNo);
		mMap.put(sqlbv64, "UPDATE");
		mMap.put(ttLJAGetSet, "INSERT"); // 添加付总表冲正记录
		mMap.put(ttLJAGetOtherSet, "INSERT"); // 添加个人交费冲正记录
	}

	private void backUpTable(String tContNo, String tPrtNo) {
		Reflections ref = new Reflections();
		String tSQL = "";

		// 备份投保人表
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSet tLCAppntSet = new LCAppntSet();

		tLCAppntDB.setContNo(tContNo);
		tLCAppntSet = tLCAppntDB.query();
		if (tLCAppntSet != null && tLCAppntSet.size() > 0) {
			for (int i = 1; i <= tLCAppntSet.size(); i++) {
				LBAppntSchema tLBAppntSchema = new LBAppntSchema();
				ref.transFields(tLBAppntSchema, tLCAppntSet.get(i));
				tLBAppntSchema.setModifyDate(theCurrentDate);
				tLBAppntSchema.setModifyTime(theCurrentTime);
				tLBAppntSchema.setEdorNo(tContNo);
				mMap.put(tLBAppntSchema, "DELETE&INSERT");
			}
		}

		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		tLCInsureAccDB.setContNo(tContNo);
		tLCInsureAccSet = tLCInsureAccDB.query();
		if (tLCInsureAccSet != null && tLCInsureAccSet.size() > 0) {
			for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
				LBInsureAccSchema tLBInsureAccSchema = new LBInsureAccSchema();
				ref.transFields(tLBInsureAccSchema, tLCInsureAccSet.get(i));
				tLBInsureAccSchema.setModifyDate(theCurrentDate);
				tLBInsureAccSchema.setModifyTime(theCurrentTime);
				tLBInsureAccSchema.setEdorNo(tContNo);
				mMap.put(tLBInsureAccSchema, "DELETE&INSERT");
			}
		}

		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();

		tLCInsureAccClassDB.setContNo(tContNo);
		tLCInsureAccClassSet = tLCInsureAccClassDB.query();
		if (tLCInsureAccClassSet != null && tLCInsureAccClassSet.size() > 0) {
			for (int i = 1; i <= tLCInsureAccClassSet.size(); i++) {
				LBInsureAccClassSchema tLBInsureAccClassSchema = new LBInsureAccClassSchema();
				ref.transFields(tLBInsureAccClassSchema, tLCInsureAccClassSet
						.get(i));
				tLBInsureAccClassSchema.setModifyDate(theCurrentDate);
				tLBInsureAccClassSchema.setModifyTime(theCurrentTime);
				tLBInsureAccClassSchema.setEdorNo(tContNo);
				mMap.put(tLBInsureAccClassSchema, "DELETE&INSERT");
			}
		}

		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		tLCInsureAccTraceDB.setContNo(tContNo);
		tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
		if (tLCInsureAccTraceSet != null && tLCInsureAccTraceSet.size() > 0) {
			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
				LBInsureAccTraceSchema tLBInsureAccTraceSchema = new LBInsureAccTraceSchema();
				ref.transFields(tLBInsureAccTraceSchema, tLCInsureAccTraceSet
						.get(i));
				tLBInsureAccTraceSchema.setModifyDate(theCurrentDate);
				tLBInsureAccTraceSchema.setModifyTime(theCurrentTime);
				tLBInsureAccTraceSchema.setEdorNo(tContNo);
				mMap.put(tLBInsureAccTraceSchema, "DELETE&INSERT");
			}
		}

		LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
		LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
		tLCInsureAccFeeDB.setContNo(tContNo);
		tLCInsureAccFeeSet = tLCInsureAccFeeDB.query();
		if (tLCInsureAccFeeSet != null && tLCInsureAccFeeSet.size() > 0) {
			for (int i = 1; i <= tLCInsureAccFeeSet.size(); i++) {
				LBInsureAccFeeSchema tLBInsureAccFeeSchema = new LBInsureAccFeeSchema();
				ref.transFields(tLBInsureAccFeeSchema, tLCInsureAccFeeSet
						.get(i));
				tLBInsureAccFeeSchema.setModifyDate(theCurrentDate);
				tLBInsureAccFeeSchema.setModifyTime(theCurrentTime);
				tLBInsureAccFeeSchema.setEdorNo(tContNo);
				mMap.put(tLBInsureAccFeeSchema, "DELETE&INSERT");
			}
		}

		LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		tLCInsureAccClassFeeDB.setContNo(tContNo);
		tLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB.query();
		if (tLCInsureAccClassFeeSet != null
				&& tLCInsureAccClassFeeSet.size() > 0) {
			for (int i = 1; i <= tLCInsureAccClassFeeSet.size(); i++) {
				LBInsureAccClassFeeSchema tLBInsureAccClassFeeSchema = new LBInsureAccClassFeeSchema();
				ref.transFields(tLBInsureAccClassFeeSchema,
						tLCInsureAccClassFeeSet.get(i));
				tLBInsureAccClassFeeSchema.setModifyDate(theCurrentDate);
				tLBInsureAccClassFeeSchema.setModifyTime(theCurrentTime);
				tLBInsureAccClassFeeSchema.setEdorNo(tContNo);
				mMap.put(tLBInsureAccClassFeeSchema, "DELETE&INSERT");
			}
		}

		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		tLCInsuredDB.setContNo(tContNo);
		tLCInsuredSet = tLCInsuredDB.query();
		if (tLCInsuredSet != null && tLCInsuredSet.size() > 0) {
			for (int i = 1; i <= tLCInsuredSet.size(); i++) {
				LBInsuredSchema tLBInsuredSchema = new LBInsuredSchema();
				ref.transFields(tLBInsuredSchema, tLCInsuredSet.get(i));
				tLBInsuredSchema.setModifyDate(theCurrentDate);
				tLBInsuredSchema.setModifyTime(theCurrentTime);
				tLBInsuredSchema.setEdorNo(tContNo);
				mMap.put(tLBInsuredSchema, "DELETE&INSERT");
			}
		}

		LCBnfDB tLCBnfDB = new LCBnfDB();
		LCBnfSet tLCBnfSet = new LCBnfSet();
		tLCBnfDB.setContNo(tContNo);
		tLCBnfSet = tLCBnfDB.query();
		if (tLCBnfSet != null && tLCBnfSet.size() > 0) {
			for (int i = 1; i <= tLCBnfSet.size(); i++) {
				LBBnfSchema tLBBnfSchema = new LBBnfSchema();
				ref.transFields(tLBBnfSchema, tLCBnfSet.get(i));
				tLBBnfSchema.setModifyDate(theCurrentDate);
				tLBBnfSchema.setModifyTime(theCurrentTime);
				tLBBnfSchema.setEdorNo(tContNo);
				mMap.put(tLBBnfSchema, "DELETE&INSERT");
			}
		}

		LCDutyDB tLCDutyDB = new LCDutyDB();
		LCDutySet tLCDutySet = new LCDutySet();
		tLCDutyDB.setContNo(tContNo);
		tLCDutySet = tLCDutyDB.query();
		if (tLCDutySet != null && tLCDutySet.size() > 0) {
			for (int i = 1; i <= tLCDutySet.size(); i++) {
				LBDutySchema tLBDutySchema = new LBDutySchema();
				ref.transFields(tLBDutySchema, tLCDutySet.get(i));
				tLBDutySchema.setModifyDate(theCurrentDate);
				tLBDutySchema.setModifyTime(theCurrentTime);
				tLBDutySchema.setEdorNo(tContNo);
				mMap.put(tLBDutySchema, "DELETE&INSERT");
			}
		}

		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = new LCPremSet();
		tLCPremDB.setContNo(tContNo);
		tLCPremSet = tLCPremDB.query();
		if (tLCPremSet != null && tLCPremSet.size() > 0) {
			for (int i = 1; i <= tLCPremSet.size(); i++) {
				LBPremSchema tLBPremSchema = new LBPremSchema();
				ref.transFields(tLBPremSchema, tLCPremSet.get(i));
				tLBPremSchema.setModifyDate(theCurrentDate);
				tLBPremSchema.setModifyTime(theCurrentTime);
				tLBPremSchema.setEdorNo(tContNo);
				mMap.put(tLBPremSchema, "DELETE&INSERT");
			}
		}

		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = new LCGetSet();
		tLCGetDB.setContNo(tContNo);
		tLCGetSet = tLCGetDB.query();
		if (tLCGetSet != null && tLCGetSet.size() > 0) {
			for (int i = 1; i <= tLCGetSet.size(); i++) {
				LBGetSchema tLBGetSchema = new LBGetSchema();
				ref.transFields(tLBGetSchema, tLCGetSet.get(i));
				tLBGetSchema.setModifyDate(theCurrentDate);
				tLBGetSchema.setModifyTime(theCurrentTime);
				tLBGetSchema.setEdorNo(tContNo);
				mMap.put(tLBGetSchema, "DELETE&INSERT");
			}
		}

		LCCustomerImpartDB tLCCustomerimpartDB = new LCCustomerImpartDB();
		LCCustomerImpartSet tLCCustomerimpartSet = new LCCustomerImpartSet();
		tLCCustomerimpartDB.setContNo(tContNo);
		tLCCustomerimpartSet = tLCCustomerimpartDB.query();
		if (tLCCustomerimpartSet != null && tLCCustomerimpartSet.size() > 0) {
			for (int i = 1; i <= tLCCustomerimpartSet.size(); i++) {
				LBCustomerImpartSchema tLBCustomerimpartSchema = new LBCustomerImpartSchema();
				ref.transFields(tLBCustomerimpartSchema, tLCCustomerimpartSet
						.get(i));
				tLBCustomerimpartSchema.setModifyDate(theCurrentDate);
				tLBCustomerimpartSchema.setModifyTime(theCurrentTime);
				tLBCustomerimpartSchema.setEdorNo(tContNo);
				mMap.put(tLBCustomerimpartSchema, "DELETE&INSERT");
			}
		}

		LCCustomerImpartParamsDB tLCCustomerImpartParamsDB = new LCCustomerImpartParamsDB();
		LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
		tLCCustomerImpartParamsDB.setContNo(tContNo);
		tLCCustomerImpartParamsSet = tLCCustomerImpartParamsDB.query();
		if (tLCCustomerImpartParamsSet != null
				&& tLCCustomerImpartParamsSet.size() > 0) {
			for (int i = 1; i <= tLCCustomerImpartParamsSet.size(); i++) {
				LBCustomerImpartParamsSchema tLBCustomerImpartParamsSchema = new LBCustomerImpartParamsSchema();
				ref.transFields(tLBCustomerImpartParamsSchema,
						tLCCustomerImpartParamsSet.get(i));
				tLBCustomerImpartParamsSchema.setModifyDate(theCurrentDate);
				tLBCustomerImpartParamsSchema.setModifyTime(theCurrentTime);
				tLBCustomerImpartParamsSchema.setEdorNo(tContNo);
				mMap.put(tLBCustomerImpartParamsSchema, "DELETE&INSERT");
			}
		}

		LCCustomerImpartDetailDB tLCCustomerImpartDetailDB = new LCCustomerImpartDetailDB();
		LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
		tLCCustomerImpartDetailDB.setContNo(tContNo);
		tLCCustomerImpartDetailSet = tLCCustomerImpartDetailDB.query();
		if (tLCCustomerImpartDetailSet != null
				&& tLCCustomerImpartDetailSet.size() > 0) {
			for (int i = 1; i <= tLCCustomerImpartDetailSet.size(); i++) {
				LBCustomerImpartDetailSchema tLBCustomerImpartDetailSchema = new LBCustomerImpartDetailSchema();
				ref.transFields(tLBCustomerImpartDetailSchema,
						tLCCustomerImpartDetailSet.get(i));
				tLBCustomerImpartDetailSchema.setModifyDate(theCurrentDate);
				tLBCustomerImpartDetailSchema.setModifyTime(theCurrentTime);
				tLBCustomerImpartDetailSchema.setEdorNo(tContNo);
				mMap.put(tLBCustomerImpartDetailSchema, "DELETE&INSERT");
			}
		}

		// LCSpecDB tLCSpecDB = new LCSpecDB();
		// LCSpecSet tLCSpecSet = new LCSpecSet();
		// tSQL = "select * from lcspec where ContNo = '" + tContNo +
		// "' or ContNo='" + tPrtNo + "'";
		// tLCSpecSet = tLCSpecDB.executeQuery(tSQL);
		// if (tLCSpecSet != null && tLCSpecSet.size() > 0) {
		// for (int i = 1; i <= tLCSpecSet.size(); i++) {
		// LBSpecSchema tLBSpecSchema = new LBSpecSchema();
		// ref.transFields(tLBSpecSchema, tLCSpecSet.get(i));
		// tLBSpecSchema.setModifyDate(theCurrentDate);
		// tLBSpecSchema.setModifyTime(theCurrentTime);
		// mMap.put(tLBSpecSchema, "INSERT");
		// }
		// }

		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		SQLwithBindVariables sqlbv65 = new SQLwithBindVariables();
		tSQL = "select * from LOPRTManager where OtherNo = '" + "?tContNo?"
				+ "'";
		sqlbv65.sql(tSQL);
		sqlbv65.put("tContNo", tContNo);
		sqlbv65.put("tPrtNo", tPrtNo);
		tLOPRTManagerSet = tLOPRTManagerDB.executeQuery(sqlbv65);
		if (tLOPRTManagerSet != null && tLOPRTManagerSet.size() > 0) {
			for (int i = 1; i <= tLOPRTManagerSet.size(); i++) {
				LOPRTManager2Schema tLOPRTManager2Schema = new LOPRTManager2Schema();
				ref.transFields(tLOPRTManager2Schema, tLOPRTManagerSet.get(i));
				tLOPRTManager2Schema.setMakeDate(theCurrentDate);
				tLOPRTManager2Schema.setMakeTime(theCurrentTime);
				mMap.put(tLOPRTManager2Schema, "DELETE&INSERT");
			}
		}

		LACommisionDetailDB tLACommisionDetailDB = new LACommisionDetailDB();
		LACommisionDetailSet tLACommisionDetailSet = new LACommisionDetailSet();
		tLACommisionDetailDB.setGrpContNo(tContNo);
		tLACommisionDetailSet = tLACommisionDetailDB.query();
		if (tLACommisionDetailSet != null && tLACommisionDetailSet.size() > 0) {
			for (int i = 1; i <= tLACommisionDetailSet.size(); i++) {
				LACommisionDetailBSchema tLACommisionDetailBSchema = new LACommisionDetailBSchema();
				ref.transFields(tLACommisionDetailBSchema,
						tLACommisionDetailSet.get(i));
				tLACommisionDetailBSchema.setEdorNo(tContNo);
				tLACommisionDetailBSchema.setEdorType("99");
				tLACommisionDetailBSchema.setEndSerDate(theCurrentDate);
				tLACommisionDetailBSchema.setModifyDate(theCurrentDate);
				tLACommisionDetailBSchema.setModifyTime(theCurrentTime);
				mMap.put(tLACommisionDetailBSchema, "DELETE&INSERT");
			}
		}

		backUpPol(tContNo);
	}

	private String getMissionID(String mPrtNo) {
		LBMissionSet tLBMissionSet = new LBMissionSet();
		LBMissionDB tLBMissionDB = new LBMissionDB();
		String tMissionID = "";

		LWProcessInstanceSet tLWProcessInstanceSet = new LWProcessInstanceSet();
		LWProcessInstanceDB tLWProcessInstanceDB = new LWProcessInstanceDB();
		//processid 应在lwcorresponding查询最新定义的processid进行动态处理 2013-04-23 lzf
		//tLWProcessInstanceDB.setProcessID("0000000003");
		SQLwithBindVariables sqlbv66 = new SQLwithBindVariables();
		String sql1 = "select processid from lwcorresponding where busitype='1001'";
		sqlbv66.sql(sql1);
		ExeSQL tExeSQL = new ExeSQL();
		String tProcessID = tExeSQL.getOneValue(sqlbv66);
		tLWProcessInstanceDB.setProcessID(tProcessID);

		tLWProcessInstanceDB.setStartType("0"); // 根节点
		tLWProcessInstanceSet.set(tLWProcessInstanceDB.query());
		if (tLWProcessInstanceDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLWProcessInstanceDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getContNo";
			tError.errorMessage = "工作流过程实例查询失败!";
			this.mErrors.addOneError(tError);
			return null;
		}
		if (tLWProcessInstanceSet == null || tLWProcessInstanceSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getContNo";
			tError.errorMessage = "工作流过程实例查询失败!";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 循环查找该过程所有根节点寻找MissionID
		String tActivityID;
		for (int i = 1; i <= tLWProcessInstanceSet.size(); i++) {
			tActivityID = tLWProcessInstanceSet.get(i).getTransitionStart();

			LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
			LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
			tLWFieldMapDB.setActivityID(tActivityID);
			tLWFieldMapSet.set(tLWFieldMapDB.query());
			if (tLWFieldMapDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLWFieldMapDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LDContTimeBL";
				tError.functionName = "getContNo";
				tError.errorMessage = "工作流任务字段映射查询失败!";
				this.mErrors.addOneError(tError);
				return null;
			}

			LWFieldMapSchema tLWFieldMapSchema = new LWFieldMapSchema();
			for (int j = 1; j <= tLWFieldMapSet.size(); j++) {
				tLWFieldMapSchema.setSchema(tLWFieldMapSet.get(j));
				// 找到印刷号影射字段
				if (tLWFieldMapSchema.getSourFieldName().equals("PrtNo")) {
					// 匹配该印刷号在此节点是否有任务
					tLBMissionSet.clear();
					tLBMissionDB = new LBMissionDB();
					tLBMissionDB.setActivityID(tActivityID);
					tLBMissionDB.setV(tLWFieldMapSchema.getDestFieldName(),
							mPrtNo);
					tLBMissionSet.set(tLBMissionDB.query());
					if (tLBMissionDB.mErrors.needDealError() == true) {
						// @@错误处理
						mErrors.copyAllErrors(tLBMissionDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "ApplyRecallPolBL";
						tError.functionName = "getBaseData";
						tError.errorMessage = "保单工作流任务ID查询失败!" + "印刷号："
								+ mPrtNo;
						mErrors.addOneError(tError);
						return null;
					}
					if (tLBMissionSet != null && tLBMissionSet.size() == 1) {
						tMissionID = tLBMissionSet.get(1).getMissionID();
						logger.debug("==MissionID==" + tMissionID);
						return tMissionID;
					}
				}
			}
		}
		if (tMissionID == null || "".equals(tMissionID)) {
			CError tError = new CError();
			tError.moduleName = "ApplyRecallPolBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "保单工作流任务ID查询失败!" + "印刷号：" + mPrtNo;
			mErrors.addOneError(tError);
			return null;
		}

		return tMissionID;
	}

	/**
	 * 操作结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	private void jbInit() throws Exception {
	}

}
