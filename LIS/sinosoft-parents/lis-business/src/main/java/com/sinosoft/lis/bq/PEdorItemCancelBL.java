package com.sinosoft.lis.bq;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDelPolLogSchema;
import com.sinosoft.lis.schema.LOBEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:团单整单删除BL层
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
 * @author zhangrong modify by Alex
 * @version 1.0
 */
public class PEdorItemCancelBL {
private static Logger logger = Logger.getLogger(PEdorItemCancelBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 传输到后台处理的map */
	private MMap mMap = new MMap();

	/** 数据操作字符串 */
	private String mOperate;

	// 状态 1-保全录入完成 2-保单申请确认 3-未录入 4-理算
	private String mEdorState;

	// 显示的层级 1-仅保单 2-保单,被保人 3-保单,被保人,险种
	private String mDisplayType;
	
	//客户层保全标记
	private String customerBQFlag="0";  //0-非客户层 1-客户层
	private String xCustomerNo="";  //客户层保全下客户号

	// 是否删除并复制

	/** 撤销申请原因 */
	private	String CancelReasonContent = "";
	private	String 	SCanclReason = "";

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LCDelPolLogSchema mLCDelPolLog = new LCDelPolLogSchema();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private TransferData mTransferData = new TransferData();

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public PEdorItemCancelBL() {
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
		mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		prepareOutputData();

		return true;
	}

	/**
	 * 将UI层传输来得数据根据业务还原成具体的类
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		// 删除原因和原因编码
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		this.CancelReasonContent = (String) mTransferData.getValueByName("CancelReasonContent");
		this.SCanclReason = (String) mTransferData
		.getValueByName("SCanclReason");

		// 批改项目
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);

		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError.buildErr(this, "传入信息有误!");
			return false;
		}

		return true;
	}

	/**
	 * 更新在当前保全项目之后又有变更的保全项目批改状态为未录入
	 * 
	 * @param mLPEdorItemSchema_befor
	 * @return boolean
	 */
	private boolean updEdorState(LPEdorItemSchema mLPEdorItemSchema_befor) {
		StringBuffer sbSQL = new StringBuffer();

		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorItem set EdorState = '3' ").append(
				" WHERE EdorNo = '")
				.append("?EdorNo?").append(
						"' AND ContNo = '").append(
						"?ContNo?").append(
						"' AND (ModifyDate > '").append(
						"?ModifyDate?").append(
						"' or (ModifyDate = '").append(
						"?ModifyDate?").append(
						"' and ModifyTime > '").append(
						"?ModifyTime?").append("')) ")
				.append(" AND (MakeDate > '").append(
						"?MakeDate?").append(
						"' or (MakeDate = '").append(
						"?MakeDate?").append(
						"' and MakeTime > '").append(
						"?MakeTime?").append("')) ")
				.append("  AND not (EdorAcceptNo = '").append(
						"?EdorAcceptNo?").append(
						"' and EdorNo = '").append(
						"?EdorNo?").append(
						"' and EdorType = '").append(
						"?EdorType?").append(
						"' and ContNo = '").append(
						"?ContNo?").append(
						"' and InsuredNo = '").append(
						"?InsuredNo?").append(
						"' and PolNo = '").append(
						"?PolNo?").append("')");

		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sbSQL.toString());
		sqlbv.put("EdorNo", mLPEdorItemSchema_befor.getEdorNo());
		sqlbv.put("ContNo", mLPEdorItemSchema_befor.getContNo());
		sqlbv.put("ModifyDate", mLPEdorItemSchema_befor.getModifyDate());
		sqlbv.put("ModifyTime", mLPEdorItemSchema_befor.getModifyTime());
		sqlbv.put("MakeDate", mLPEdorItemSchema_befor.getMakeDate());
		sqlbv.put("MakeTime", mLPEdorItemSchema_befor.getMakeTime());
		sqlbv.put("EdorAcceptNo", mLPEdorItemSchema_befor.getEdorAcceptNo());
		sqlbv.put("EdorType", mLPEdorItemSchema_befor.getEdorType());
		sqlbv.put("InsuredNo", mLPEdorItemSchema_befor.getInsuredNo());
		sqlbv.put("PolNo", mLPEdorItemSchema_befor.getPolNo());
		logger.debug(sbSQL.toString());

		mMap.put(sqlbv, "UPDATE");

		return true;
	}

	/**
	 * 业务处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		if (!getEdorApp()) {
			return false;
		}

		for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
			// 调用各个保全项目撤销处理类（如果有的话）
			//MS不需要再调用单独的撤销类
//			if (!calPEdorXXCancelBL(mLPEdorItemSet.get(i))) {
//				return false;
//			}

			// 公用删除数据处理
			if (!delTable(mLPEdorItemSet.get(i))) {
				return false;
			}

			// 更新在当前保全项目之后又有变更的保全项目批改状态为未录入
			if (!updEdorState(mLPEdorItemSet.get(i))) {
				return false;
			}
		}

		// 重新统计变动保费、变动保额、补/退费金额、补/退费利息
		sumChang(mLPEdorItemSchema.getEdorAcceptNo());
		// 删除通知书数据
		logger.debug("start to delete print data:");
		delLOPrtManager(mLPEdorItemSchema);
		return true;
	}

	/**
	 * 判断保全申请是以客户申请还是保单申请
	 * 
	 * @return boolean
	 */
	private boolean getEdorApp() {
		// 判断保全申请是以客户申请还是保单申请
		String sql = " select othernotype from lpedorapp "
				+ " where edoracceptno = '?edoracceptno?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("edoracceptno", mLPEdorItemSchema.getEdorAcceptNo());
		ExeSQL tExeSQL = new ExeSQL();
		String sOtherNoType = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全受理号码类型失败!");
			return false;
		}
		if (sOtherNoType == null || sOtherNoType.equals("")) {
			CError.buildErr(this, "查询保全受理号码类型失败!");
			return false;
		}

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();

		if ("3".equals(sOtherNoType) || "4".equals(sOtherNoType)) // 个人保单号
																	// 团体保单号
		{
			sql = "select * from LPEdorItem where EdorAcceptNo ='?EdorAcceptNo?'"
					+ " and EdorNo='?EdorNo?'"
					+ " and ContNo='?ContNo?'"
					+ " and EdorType='?EdorType?'"
					+ " and PolNo = '?PolNo?'"
					+ " and InsuredNo = '?InsuredNo?'";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
			sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
			sqlbv.put("EdorType", mLPEdorItemSchema.getEdorType());
			sqlbv.put("PolNo", mLPEdorItemSchema.getPolNo());
			sqlbv.put("InsuredNo", mLPEdorItemSchema.getInsuredNo());
		} 
		else if ("1".equals(sOtherNoType)) // 个人客户号
		{
			sql = "select * from LPEdorItem where EdorAcceptNo ='?EdorAcceptNo?' and EdorType='?EdorType?'";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
			sqlbv.put("EdorType", mLPEdorItemSchema.getEdorType());
			customerBQFlag = "1";
			//然后取出客户层保全客户号
			String sql1 = " select otherno from lpedorapp "
				+ " where edoracceptno = '?edoracceptno?'";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(sql1);
			sbv.put("edoracceptno", mLPEdorItemSchema.getEdorAcceptNo());
			xCustomerNo=tExeSQL.getOneValue(sbv);
			if (xCustomerNo == null || xCustomerNo.equals("")) {
				CError.buildErr(this, "查询客户层保全客户号失败!");
				return false;
			}
		}
		mLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv);
		if (mLPEdorItemSet.size() == 0 || mLPEdorItemSet == null) {
			CError.buildErr(this, "没有相应的保全项目!");
			return false;
		}
		logger.debug("=需要撤销的保全项目=" + mLPEdorItemSet.size() + "个");
		return true;
	}

	private boolean delTable(LPEdorItemSchema aLPEdorItemSchema) {

		String delSql;
		String tEdorNo = aLPEdorItemSchema.getEdorNo();
		String tEdorType = aLPEdorItemSchema.getEdorType();
		String tContNo = aLPEdorItemSchema.getContNo();
		String tInsuredNo = aLPEdorItemSchema.getInsuredNo();
		String tPolNo = aLPEdorItemSchema.getPolNo();

		String[] EdorLevelTable = { "LPEdorPrint", "LPEdorPrint2" };

		String[] ContLevelTable = { "LPCont","LPContState", "LPCustomerImpart",
				"LPCustomerImpartDetail", "LPCustomerImpartParams", "LPCUWSub",
				"LPCUWMaster", "LPCUWError", "LPInsureAcc", "LPInsureAccClass",
				"LPInsureAccTrace", "LPEngBonusPol", "LPInsureAccFee",
				"LPInsureAccClassFee", "LPInsureAccFeeTrace", "LPUWMaster",
				"LPUWSub", "LPUWError", "LPSpec", "LPLoan", "LPReturnLoan",
				"LPEdorUWError","LPCSpec","LPBnf","LPContTempInfo"};//客户层保全还需要删除LPContTempInfo表记录
		String[] CustomerLevelTable = { "LPAppnt", "LPInsured", "LPPerson",
				"LPAddress", "LPAccount" };
		String[] PolLevelTable = { "LPPol", "LPInsuredRelated", 
				"LPDuty", "LPGet", "LPGetToAcc", "LPPrem",      //"LPPrem_1",    LPPrem_1 表不存在，保全新增险种 项目撤销失败
				"LPPremToAcc", "LPInsureAccFee", "LPInsureAccClassFee",
				"LPInsureAccFeeTrace", "LPInsureAcc", "LPInsureAccClass",
				"LPInsureAccTrace", "LPAccMove", "LPAppntTrace",
				"LPAccMoveGet", "LPUWMaster", "LPUWSub", "LPUWError", "LPSpec",
				"LPLoan", "LPReturnLoan", "LPEdorUWError" };
		String[] ContPolLevelTable = { "LPPol", "LPDuty", "LPGet", "LPPrem", "LPDiscount" };
		// =========删除相关表名备份===========================BGN=========================
		// String[] tableForDel =
		// {
		// "LPGeneral", "LPCont", "LPGeneralToRisk",
		// "LPPol",
		// "LPGetToAcc", "LPInsureAcc",
		// "LPPremToAcc", "LPPrem", "LPGet", "LPDuty",
		// "LPPrem_1",
		// "LPInsureAccClass", "LPInsureAccTrace",
		// "LPContPlanDutyParam",
		// "LPContPlanRisk", "LPContPlan", "LPAppnt",
		// "LPCustomerImpart",
		// "LPInsuredRelated", "LPBnf", "LPInsured",
		// "LPCustomerImpartParams", "LPInsureAccFee",
		// "LPInsureAccClassFee",
		// "LPContPlanFactory", "LPContPlanParam",
		// "LPMove", "LPAppntTrace", "LPLoan",
		// "LPReturnLoan", "LPEdorPrint3", "LPGUWError",
		// "LPGUWMaster",
		// "LPCUWError",
		// "LPCUWMaster", "LPCUWSub", "LPUWError",
		// "LPUWMaster",
		// "LPUWSub",
		// "LPGCUWError", "LPGCUWMaster", "LPGCUWSub",
		// "LPGUWSub",
		// "LPSpec", "LPIssuePol",
		// "LPCSpec", "LPAccount", "LPPerson",
		// "LPAddress",
		// "LPPayRuleFactory", "LPPayRuleParams",
		// "LPCustomerImpartDetail", "LPAccMove",
		// "LPEdorItem"
		// };
		// =========删除相关表名备份===========================END=========================
		for (int i = 0; i < EdorLevelTable.length; i++) {
			delSql = "delete from  "+EdorLevelTable[i]+" where  EdorNo = '?tEdorNo?'";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(delSql);
			sbv.put("tEdorNo", tEdorNo);
			mMap.put(sbv, "DELETE");
		}

		delSql = "delete from LPIssuePol where  EdorNo = '?tEdorNo?' and edortype='?tEdorType?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(delSql);
		sbv1.put("tEdorNo", tEdorNo);
		sbv1.put("tEdorType", tEdorType);
		mMap.put(sbv1, "DELETE");

		/** ****保单级**************************************************** */
		for (int i = 0; i < ContLevelTable.length; i++) {
			logger.debug("****" + ContLevelTable[i]);
			delSql = "delete from  "+ContLevelTable[i]+" where  EdorNo = '?tEdorNo?' and EdorType='?tEdorType?'"
					+ " and ContNo = '?tContNo?'";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(delSql);
			sbv2.put("tEdorNo", tEdorNo);
			sbv2.put("tEdorType", tEdorType);
			sbv2.put("tContNo", tContNo);
			mMap.put(sbv2, "DELETE");
		}
		
		
		//add by jiaqiangli 2009-02-09 XXXXX为啥没有？加之
		for (int i = 0; i < CustomerLevelTable.length; i++) {
			logger.debug("****" + CustomerLevelTable[i]);
			delSql = "delete from "+CustomerLevelTable[i]+" where  EdorNo = '?tEdorNo?' and EdorType='?tEdorNo?' ";
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(delSql);
			sbv3.put("tEdorNo", tEdorNo);
			sbv3.put("tEdorNo", tEdorNo);
			mMap.put(sbv3, "DELETE");
		}
		//add by jiaqiangli 2009-02-09 XXXXXXx为啥没有？加之
		
		
		
		// 删除保单迁移 为何？ zhangtao 2005-07-16
		delSql = "delete from LPMove where  EdorNo = '?tEdorNo?' and EdorType='?tEdorType?'" + " and ContNoOld = '?tContNo?'";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(delSql);
		sbv4.put("tEdorNo", tEdorNo);
		sbv4.put("tEdorType", tEdorType);
		sbv4.put("tContNo", tContNo);
		mMap.put(sbv4, "DELETE");

		/** ****被保人级*************************************************** */
		// 被保人号不为空,删除以下表
		if (!aLPEdorItemSchema.getInsuredNo().equals("000000")) 
		{
			if("1".equals(customerBQFlag))  //客户层保全
			{
				delSql = "delete from  LPInsured where EdorNo = '?tEdorNo?' and EdorType='?tEdorType?' and insuredno='?xCustomerNo?' and contNo='?tContNo?'";
				SQLwithBindVariables sbv5=new SQLwithBindVariables();
				sbv5.sql(delSql);
				sbv5.put("tEdorNo", tEdorNo);
				sbv5.put("tEdorType", tEdorType);
				sbv5.put("xCustomerNo", xCustomerNo);
				sbv5.put("tContNo", tContNo);
				mMap.put(sbv5, "DELETE");
				delSql = "delete from  LPPerson where  EdorNo = '?tEdorNo?' and EdorType='?tEdorType?' and customerno='?xCustomerNo?'";
				SQLwithBindVariables sbv6=new SQLwithBindVariables();
				sbv6.sql(delSql);
				sbv6.put("tEdorNo", tEdorNo);
				sbv6.put("tEdorType", tEdorType);
				sbv6.put("xCustomerNo", xCustomerNo);
				mMap.put(sbv6, "DELETE");
				delSql = "delete from  LPAppnt where EdorNo = '?tEdorNo?' and EdorType='?tEdorType?' and appntno='?xCustomerNo?'";
				SQLwithBindVariables sbv7=new SQLwithBindVariables();
				sbv7.sql(delSql);
				sbv7.put("tEdorNo", tEdorNo);
				sbv7.put("tEdorType", tEdorType);
				sbv7.put("xCustomerNo", xCustomerNo);
				mMap.put(sbv7, "DELETE");
		
				delSql = "delete from  LPAddress where EdorNo = '?tEdorNo?' and EdorType='?tEdorType?' and customerNo='?xCustomerNo?'";
				SQLwithBindVariables sbv8=new SQLwithBindVariables();
				sbv8.sql(delSql);
				sbv8.put("tEdorNo", tEdorNo);
				sbv8.put("tEdorType", tEdorType);
				sbv8.put("xCustomerNo", xCustomerNo);
				mMap.put(sbv8, "DELETE");
		
				delSql = "delete from  LPAccount where EdorNo = '?tEdorNo?' and EdorType='?tEdorType?' and customerNo='?xCustomerNo?'";
				SQLwithBindVariables sbv9=new SQLwithBindVariables();
				sbv9.sql(delSql);
				sbv9.put("tEdorNo", tEdorNo);
				sbv9.put("tEdorType", tEdorType);
				sbv9.put("xCustomerNo", xCustomerNo);
				mMap.put(sbv9, "DELETE");
			}
			else
			{
				delSql = "delete from  LPInsured where EdorNo = '?tEdorNo?' and EdorType='?tEdorType?' and insuredno='?tInsuredNo?' and contNo='?tContNo?'";
				SQLwithBindVariables sbv10=new SQLwithBindVariables();
				sbv10.sql(delSql);
				sbv10.put("tEdorNo", tEdorNo);
				sbv10.put("tEdorType", tEdorType);
				sbv10.put("tInsuredNo", tInsuredNo);
				sbv10.put("tContNo", tContNo);
				mMap.put(sbv10, "DELETE");
				delSql = "delete from  LPPerson where  EdorNo = '?tEdorNo?' and EdorType='?tEdorType?' and customerno='?tInsuredNo?'";
				SQLwithBindVariables sbv11=new SQLwithBindVariables();
				sbv11.sql(delSql);
				sbv11.put("tEdorNo", tEdorNo);
				sbv11.put("tEdorType", tEdorType);
				sbv11.put("tInsuredNo", tInsuredNo);
				mMap.put(sbv11, "DELETE");
				delSql = "delete from  LPAppnt where EdorNo = '?tEdorNo?' and EdorType='?tEdorType?' and appntno='?tInsuredNo?'";
				SQLwithBindVariables sbv12=new SQLwithBindVariables();
				sbv12.sql(delSql);
				sbv12.put("tEdorNo", tEdorNo);
				sbv12.put("tEdorType", tEdorType);
				sbv12.put("tInsuredNo", tInsuredNo);
				mMap.put(sbv12, "DELETE");
		
				delSql = "delete from  LPAddress where EdorNo = '?tEdorNo?' and EdorType='?tEdorType?' and customerNo='?tInsuredNo?'";
				SQLwithBindVariables sbv13=new SQLwithBindVariables();
				sbv13.sql(delSql);
				sbv13.put("tEdorNo", tEdorNo);
				sbv13.put("tEdorType", tEdorType);
				sbv13.put("tInsuredNo", tInsuredNo);
				mMap.put(sbv13, "DELETE");
		
				delSql = "delete from  LPAccount where EdorNo = '?tEdorNo?' and EdorType='?tEdorType?' and customerNo='?tInsuredNo?'";
				SQLwithBindVariables sbv14=new SQLwithBindVariables();
				sbv14.sql(delSql);
				sbv14.put("tEdorNo", tEdorNo);
				sbv14.put("tEdorType", tEdorType);
				sbv14.put("tInsuredNo", tInsuredNo);
				mMap.put(sbv14, "DELETE");
			}
		}

		/** ****险种级*************************************************** */
		// 如果险种号不为空则删除以下表
		if (!aLPEdorItemSchema.getPolNo().equals("000000")) {
			for (int i = 0; i < PolLevelTable.length; i++) {
				delSql = " delete from  "+PolLevelTable[i]+" where  EdorNo = '?tEdorNo?' and EdorType='?tEdorType?' and polno = '?tPolNo?'"; // 险种级的保全项目根据polno删除
				SQLwithBindVariables sbv15=new SQLwithBindVariables();
				sbv15.sql(delSql);
				sbv15.put("tEdorNo", tEdorNo);
				sbv15.put("tEdorType",tEdorType);
				sbv15.put("tPolNo", tPolNo);
				mMap.put(sbv15, "DELETE");
			}
		} else {
			for (int i = 0; i < ContPolLevelTable.length; i++) {
				delSql = " delete from  "+ContPolLevelTable[i]+" where  EdorNo = '?tEdorNo?' and EdorType='?tEdorType?'" + " and ContNo = '?tContNo?'";
				SQLwithBindVariables sbv16=new SQLwithBindVariables();
				sbv16.sql(delSql);
				sbv16.put("tEdorNo", tEdorNo);
				sbv16.put("tEdorType",tEdorType);
				sbv16.put("tContNo", tContNo);
				mMap.put(sbv16, "DELETE");// 报单级的保全项目根据contno删除
			}
		}

		if (!mOperate.equals("EDORMAIN")) {
			SQLwithBindVariables sbv17=new SQLwithBindVariables();
			sbv17.sql("delete from lpedorMain where EdorNo= '?tEdorNo?' and contno = '?tContNo?' and 1 = (select count(*) from LpEdorItem where lpedorItem.EdorNo='?tEdorNo?' and contno = '?tContNo?' )");
			sbv17.put("tEdorNo", tEdorNo);
			sbv17.put("tContNo", tContNo);
			mMap
					.put(sbv17, "DELETE"); // 若是最后一条项目，则删除对应批单
			// 如果是团体保全,撤销个人单的同时解挂该个人单
			delSql = " delete from lcconthangupstate "
					+ " where exists (select 'X' from lcgrpcont where grpcontno = (select grpcontno from lccont c where c.contno = lcconthangupstate.contno)) "
					+ " and hanguptype = '2' and contno = '?tContNo?'";
			SQLwithBindVariables sbv18=new SQLwithBindVariables();
			sbv18.sql(delSql);
			sbv18.put("tContNo", tContNo);
			mMap.put(sbv18, "DELETE");
		}

		// 删除批改补退费表的相关记录 是否会多删？ zhangtao 2005-07-16
		String delLJSSql = " delete from LJSGetEndorse where EndorsementNo = '?EndorsementNo?' and FeeOperationType = '?FeeOperationType?' and contno = '?contno?' ";
		SQLwithBindVariables sbv19=new SQLwithBindVariables();
		

		if (!tPolNo.trim().equals("000000")) {
			delLJSSql += " and polno = '?tPolNo?'";

			sbv19.put("tPolNo", tPolNo);
		}
		sbv19.sql(delLJSSql);
		sbv19.put("EndorsementNo", aLPEdorItemSchema.getEdorNo());
		sbv19.put("FeeOperationType", aLPEdorItemSchema.getEdorType());
		sbv19.put("contno", aLPEdorItemSchema.getContNo());
		mMap.put(sbv19, "DELETE");
		
		//add by jiaqiangli 2008-12-25 2 fix 8g 保全撤销delete ljspayperson
		logger.debug("Systemout by jiaqiangli");
		String delLJSPayPersonSql = " delete from ljspayperson where getnoticeno = '?getnoticeno?' and (paytype = '?paytype?' or paytype in (select code from ldcode where codetype='discounttype')) and contno = '?contno?' ";
		SQLwithBindVariables sbv20=new SQLwithBindVariables();
		
	if (!tPolNo.trim().equals("000000")) {
		delLJSPayPersonSql += " and polno = '?tPolNo?'";

		sbv20.put("tPolNo", tPolNo);
	}
	logger.debug("Systemout by jiaqiangli"+delLJSPayPersonSql);
	sbv20.sql(delLJSPayPersonSql);
	sbv20.put("getnoticeno", aLPEdorItemSchema.getEdorNo());
	sbv20.put("paytype", aLPEdorItemSchema.getEdorType());
	sbv20.put("contno", aLPEdorItemSchema.getContNo());
	mMap.put(sbv20, "DELETE");
	//add by jiaqiangli 2008-12-25 2 fix 8g 保全撤销delete ljspayperson

		// 如果是新增附加险，需删除C表数据
		if (tEdorType.trim().equals("NS")) {
			String strSQL = "select * from lccont where contno = '?tContNo?' ";
			SQLwithBindVariables sbv21=new SQLwithBindVariables();
			sbv21.sql(strSQL);
			sbv21.put("tContNo", tContNo);
			LCContDB tLCContDB = new LCContDB();
			LCContSet tLCContSet = tLCContDB.executeQuery(sbv21);
			if (tLCContDB.mErrors.needDealError()) {
				CError.buildErr(this, "保单信息查询失败!");
				return false;
			}
			if (tLCContSet == null || tLCContSet.size() < 1) {
				CError.buildErr(this, "未查到保单信息!");
				return false;
			}

			// 查出新增加的附加险,可能会有多主险多次申请，只删除指定的险种的附加险
			strSQL = " select * from lcpol where appflag = '2' and polno <> mainpolno "
					+ " and contno = '?tContNo?' and  mainpolno='?tPolNo?'";
			sbv21=new SQLwithBindVariables();
			sbv21.sql(strSQL);
			sbv21.put("tContNo", tContNo);
			sbv21.put("tPolNo", tPolNo);
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = tLCPolDB.executeQuery(sbv21);
			if (tLCPolDB.mErrors.needDealError()) {
				CError.buildErr(this, "险种信息查询失败!");
				return false;
			}
			if (tLCPolSet != null && tLCPolSet.size() > 0) {
				double chgPrem = 0.0;
				double chgAmnt = 0.0;
				for (int i = 1; i <= tLCPolSet.size(); i++) {
					chgPrem += tLCPolSet.get(i).getPrem();
					chgAmnt += tLCPolSet.get(i).getAmnt();
					//此时的PolNo 不同于LPEdorItem中的PolNo
					String sPolNo = tLCPolSet.get(i).getPolNo();
					String delLCPol = " delete from lcpol where polno = '?sPolNo?' ";
					SQLwithBindVariables sbv22=new SQLwithBindVariables();
					sbv22.sql(delLCPol);
					sbv22.put("sPolNo", sPolNo);
					String delLPPol = " delete from lppol where polno = '?sPolNo?' ";
					SQLwithBindVariables sbv23=new SQLwithBindVariables();
					sbv23.sql(delLPPol);
					sbv23.put("sPolNo", sPolNo);
					String delLCDuty = " delete from lcduty where polno = '?sPolNo?' ";
					SQLwithBindVariables sbv24=new SQLwithBindVariables();
					sbv24.sql(delLCDuty);
					sbv24.put("sPolNo", sPolNo);
					String delLPDuty = " delete from lpduty where polno = '?sPolNo?' ";
					SQLwithBindVariables sbv25=new SQLwithBindVariables();
					sbv25.sql(delLPDuty);
					sbv25.put("sPolNo", sPolNo);
					String delLCPrem = " delete from lcprem where polno = '?sPolNo?' ";
					SQLwithBindVariables sbv26=new SQLwithBindVariables();
					sbv26.sql(delLCPrem);
					sbv26.put("sPolNo", sPolNo);
					String delLPPrem = " delete from lpprem where polno = '?sPolNo?' ";
					SQLwithBindVariables sbv27=new SQLwithBindVariables();
					sbv27.sql(delLPPrem);
					sbv27.put("sPolNo", sPolNo);
					String delLCGet = " delete from lcget where polno = '?sPolNo?' ";
					SQLwithBindVariables sbv28=new SQLwithBindVariables();
					sbv28.sql(delLCGet);
					sbv28.put("sPolNo", sPolNo);
					String delLPGet = " delete from lpget where polno = '?sPolNo?' ";
					SQLwithBindVariables sbv29=new SQLwithBindVariables();
					sbv29.sql(delLPGet);
					sbv29.put("sPolNo", sPolNo);
					String delLCSpec = " delete from LCSpec where polno = '?sPolNo?' ";
					SQLwithBindVariables sbv30=new SQLwithBindVariables();
					sbv30.sql(delLCSpec);
					sbv30.put("sPolNo", sPolNo);
				    String delLPSpec = " delete from LPSpec where polno = '?sPolNo?' ";
					SQLwithBindVariables sbv31=new SQLwithBindVariables();
					sbv31.sql(delLPSpec);
					sbv31.put("sPolNo", sPolNo);
					String delLCBnf = " delete from LCBnf where polno = '?sPolNo?' ";
					SQLwithBindVariables sbv32=new SQLwithBindVariables();
					sbv32.sql(delLCBnf);
					sbv32.put("sPolNo", sPolNo);
				    String delLPBnf = " delete from LPBnf where polno = '?sPolNo?' ";
					SQLwithBindVariables sbv33=new SQLwithBindVariables();
					sbv33.sql(delLPBnf);
					sbv33.put("sPolNo", sPolNo);
					
					String delLjsPayPerson = " delete from ljspayperson where polno = '?sPolNo?' and trim(paytype) = 'NS'";
					SQLwithBindVariables sbv34=new SQLwithBindVariables();
					sbv34.sql(delLjsPayPerson);
					sbv34.put("sPolNo", sPolNo);
					String delLjsLJSGetEnddorse = " delete from ljsgetendorse where polno = '?sPolNo?' and feeoperationtype = 'NS'";
					SQLwithBindVariables sbv35=new SQLwithBindVariables();
					sbv35.sql(delLjsLJSGetEnddorse);
					sbv35.put("sPolNo", sPolNo);
					mMap.put(sbv22, "DELETE");
					mMap.put(sbv23, "DELETE");
					mMap.put(sbv24, "DELETE");
					mMap.put(sbv25, "DELETE");
					mMap.put(sbv26, "DELETE");
					mMap.put(sbv27, "DELETE");
					mMap.put(sbv28, "DELETE");
					mMap.put(sbv29, "DELETE");
					mMap.put(sbv34, "DELETE");
					mMap.put(sbv35, "DELETE");
					
					mMap.put(sbv30, "DELETE");
					mMap.put(sbv31, "DELETE");
					
					mMap.put(sbv32, "DELETE");
					mMap.put(sbv33, "DELETE");
				}

				tLCContSet.get(1)
						.setPrem(tLCContSet.get(1).getPrem() - chgPrem);
				tLCContSet.get(1)
						.setAmnt(tLCContSet.get(1).getAmnt() - chgAmnt);
				mMap.put(tLCContSet.get(1), "UPDATE");
			}
		}
		if (tEdorType.trim().equals("CS")) {
			String delCSSql = "delete from  ES_DOC_PAGES where DocId = (select docid from ES_DOC_MAIN where DocCode = '?DocCode?') and PageType='7'";
			SQLwithBindVariables sbv36=new SQLwithBindVariables();
			sbv36.sql(delCSSql);
			sbv36.put("DocCode", aLPEdorItemSchema.getEdorAcceptNo());
			mMap.put(sbv36, "DELETE");
		}
		mMap.put(aLPEdorItemSchema, "DELETE");
		
		BqContHangUpBL tBqContHangUpBL = new BqContHangUpBL(mGlobalInput);
		if (!tBqContHangUpBL.hangUpCont(mLPEdorItemSchema.getContNo(), "0",
				mLPEdorItemSchema.getEdorAcceptNo())) {
			CError.buildErr(this, "保单解挂失败! 保单号："
					+ mLPEdorItemSchema.getContNo());
			return false;
		} else {
			MMap tMap = tBqContHangUpBL.getMMap();
			mMap.add(tMap);
		}
		
		return true;
	}

	/**
	 * 删除添加保全项目时生成的通知书数据
	 * 
	 * @return boolean
	 */
	private void delLOPrtManager(LPEdorItemSchema tLPEdorItemSchema) {
		String sEdorType = tLPEdorItemSchema.getEdorType();
		logger.debug("EdorType:" + sEdorType);
		if ("CM".equals(sEdorType) || "HI".equals(sEdorType)) {
			String delCusSql = "delete from loprtmanager where code in(?code?) and otherno = '?otherno?'";
			logger.debug("delCusSql:" + delCusSql);
			ArrayList<String> strArr=new ArrayList<String>();
			strArr.add(PrintManagerBL.CODE_PEdorCMInfo);
			strArr.add(PrintManagerBL.CODE_PEdorHIInfo);
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(delCusSql);
			sqlbv.put("code", strArr);
			sqlbv.put("otherno", tLPEdorItemSchema.getEdorAcceptNo());
			mMap.put(sqlbv, "DELETE");
		}
		if ("EA".equals(sEdorType)) {
			String delEASql = "delete from loprtmanager where code = '?code?' and otherno = '?otherno?'";
			logger.debug("delEASql:" + delEASql);
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(delEASql);
			sbv.put("code", PrintManagerBL.CODE_PEdorEndCont);
			sbv.put("otherno", tLPEdorItemSchema.getContNo());
			mMap.put(sbv, "DELETE");
		}
	}

	/**
	 * 调用各保全项目对应的撤销处理类
	 * 
	 * @return boolean
	 */
	private boolean calPEdorXXCancelBL(LPEdorItemSchema pLPEdorItemSchema) {
		// 调用各保全项目对应的明细处理类
		String sEdorType = pLPEdorItemSchema.getEdorType();
		try {
			Class tClass = Class.forName("com.sinosoft.lis.bq.PEdor"
					+ sEdorType + "CancelBL");
			EdorCancel tEdorCancel = (EdorCancel) tClass.newInstance();

			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(pLPEdorItemSchema);

			if (!tEdorCancel.submitData(tVData, mOperate)) {
				mErrors.copyAllErrors(tEdorCancel.getErrors());
				return false;
			} else {
				VData rVData = tEdorCancel.getResult();
				MMap tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
				if (tMap == null) {
					CError.buildErr(this, "获取保全项目" + sEdorType + "的撤销处理结果时失败!");
					return false;
				} else {
					mMap.add(tMap);
				}
			}
		} catch (ClassNotFoundException BLEX) {
			// 只有需要特殊处理的保全项目才有撤销处理类，其他都不用
			logger.debug("找不到保全项目" + sEdorType + "对应的撤销处理类!");
		} catch (Exception ex) {
			CError.buildErr(this, "保全项目" + sEdorType + "撤销处理失败!");
			return false;
		}

		return true;
	}

	/**
	 * 统计变动保费、变动保额、补/退费金额、补/退费利息
	 * 
	 * @return boolean
	 */
	private void sumChang(String pEdorAcceptNo) {
		String wherePartApp = "where EdorAcceptNo='?pEdorAcceptNo?'";
		String wherePartMain = "where EdorAcceptNo='?pEdorAcceptNo?'"
				+ " and edorno = edorno ";
		StringBuffer sbSQL = new StringBuffer();

		// 批单层
		sbSQL.append(" UPDATE LPEdorMain set ").append(
				" ChgPrem = (select sum(ChgPrem) from LPEdorItem ").append(
				wherePartMain).append("), ").append(
				" ChgAmnt = (select sum(ChgAmnt) from LPEdorItem ").append(
				wherePartMain).append("), ").append(
				" GetMoney = (select sum(GetMoney) from LPEdorItem ").append(
				wherePartMain).append("), ").append(
				" GetInterest = (select sum(GetInterest) from LPEdorItem ")
				.append(wherePartMain).append(") ").append(wherePartApp);
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sbSQL.toString());
		sbv1.put("pEdorAcceptNo", pEdorAcceptNo);
		mMap.put(sbv1, "UPDATE");

		// 保全申请层
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorApp set ").append(
				" ChgPrem = (select sum(ChgPrem) from LPEdorItem ").append(
				wherePartApp).append("), ").append(
				" ChgAmnt = (select sum(ChgAmnt) from LPEdorItem ").append(
				wherePartApp).append("), ").append(
				" GetMoney = (select sum(GetMoney) from LPEdorItem ").append(
				wherePartApp).append("), ").append(
				" GetInterest = (select sum(GetInterest) from LPEdorItem ")
				.append(wherePartApp).append(") ").append(wherePartApp);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sbSQL.toString());
		sbv2.put("pEdorAcceptNo", pEdorAcceptNo);
		mMap.put(sbv2, "UPDATE");

	}

	/**
	 * 准备数据，重新填充数据容器中的内容
	 */
	private void prepareOutputData() {
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

	public MMap getMap() {
		return mMap;
	}

}
