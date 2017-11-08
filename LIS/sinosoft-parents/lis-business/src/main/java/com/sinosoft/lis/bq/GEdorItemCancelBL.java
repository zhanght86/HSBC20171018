package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LOBGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:团单项目删除
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
public class GEdorItemCancelBL {
private static Logger logger = Logger.getLogger(GEdorItemCancelBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 传输到后台处理的map */
	private MMap mMap = new MMap();
	/** 撤销申请原因 */
	private String delReason;
	private String reasonCode;
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private TransferData mTransferData = new TransferData();

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public GEdorItemCancelBL() {
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

		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);

		mLPEdorItemSet = (LPEdorItemSet) mInputData.getObjectByObjectName(
				"LPEdorItemSet", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		delReason = (String) mTransferData.getValueByName("DelReason");
		reasonCode = (String) mTransferData.getValueByName("ReasonCode");

		// 查询该团体保全项目信息
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "团体保全项目查询失败!");
			return false;
		}
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1) {
			CError.buildErr(this, "没有该团体保全项目!");
			return false;
		}
		mLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);

		return true;
	}

	/**
	 * 更新该保全项目之后添加的保全项目状态为未录入
	 * 
	 * @return boolean
	 */
	private boolean updateData() {
		// 查出在该保全项目之后添加的保全项目
		String strSql = " select * from LPGrpEdorItem where EdorAcceptNo = '"
				+ "?EdorAcceptNo?" + "' and EdorNo = '"
				+ "?EdorNo?" + "' and (MakeDate > '"
				+ "?MakeDate?" + "' or (MakeDate = '"
				+ "?MakeDate?" + "' and MakeTime > '"
				+ "?MakeTime?"
				+ "' )) order by MakeDate, MakeTime desc ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("EdorAcceptNo", mLPGrpEdorItemSchema.getEdorAcceptNo());
		sqlbv.put("EdorNo", mLPGrpEdorItemSchema.getEdorNo());
		sqlbv.put("MakeDate", mLPGrpEdorItemSchema.getMakeDate());
		sqlbv.put("MakeTime", mLPGrpEdorItemSchema.getMakeTime());
		logger.debug(strSql);
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB
				.executeQuery(sqlbv);
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "团体保全项目查询失败!");
			return false;
		}
		if (tLPGrpEdorItemSet != null && tLPGrpEdorItemSet.size() > 0) {
			for (int i = 1; i <= tLPGrpEdorItemSet.size(); i++) {
				// Q: 是否需要这步处理？ zhangtao 2005-10-18
				// A: 不需要 zhangtao 2005-10-18
				// if (!dealData(tLPGrpEdorItemSet.get(i)))
				// {
				// return false;
				// }

				tLPGrpEdorItemSet.get(i).setEdorState("3"); // 重新置为未录入

				// 将团单保全项目下的个单项目状态置为未录入
				LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
				tLPEdorItemDB.setEdorNo(tLPGrpEdorItemSet.get(i).getEdorNo());
				tLPEdorItemDB.setEdorType(tLPGrpEdorItemSet.get(i)
						.getEdorType());
				tLPEdorItemDB.setGrpContNo(tLPGrpEdorItemSet.get(i)
						.getGrpContNo());
				LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
				if (tLPGrpEdorItemDB.mErrors.needDealError()) {
					CError.buildErr(this, "团体保全项目下个单保全项目查询失败!");
					return false;
				}
				if (tLPEdorItemSet != null && tLPEdorItemSet.size() > 0) {
					for (int j = 1; j <= tLPEdorItemSet.size(); j++) {
						tLPEdorItemSet.get(j).setEdorState("3");
					}

					mMap.put(tLPEdorItemSet, "UPDATE");
				}
			}

			mMap.put(tLPGrpEdorItemSet, "UPDATE");
		}

		return true;
	}

	/**
	 * 执行团体保全项目撤销处理
	 * 
	 * @param mLPGrpEdorItemSchema
	 * @return boolean
	 */
	private boolean dealData() {
		if (mOperate.equalsIgnoreCase("G&I&EDORITEM")) // 只撤销团体保全项目下的个人保全项目
		{
			// 调用个单撤销
			mInputData.add(mLPEdorItemSet.get(1));
			PEdorItemCancelBL tPEdorItemCancelBL = new PEdorItemCancelBL();
			if (!tPEdorItemCancelBL.submitData(mInputData, "EDORITEM")) {
				this.mErrors.copyAllErrors(tPEdorItemCancelBL.mErrors);
				return false;
			} else {
				mMap.add(tPEdorItemCancelBL.getMap());
				// XinYQ added on 2007-04-20 : 更新 LPGrpEdorItem
				String UpdateSQL = new String("");
				UpdateSQL = "update LPGrpEdorItem " + "set GetMoney = "
						+ "(select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end) " + "from LPEdorItem "
						+ "where 1 = 1 " + "and EdorNo = '"
						+ "?EdorNo?"
						+ "' "
						+ "and EdorType = '"
						+ "?EdorType?"
						+ "' "
						+ "and GrpContNo = '"
						+ "?GrpContNo?"
						+ "'), "
						+ "ModifyDate = '"
						+ "?theCurrentDate?"
						+ "', "
						+ "ModifyTime = '"
						+ "?theCurrentTime?"
						+ "' "
						+ "where 1 = 1 "
						+ "and EdorNo = '"
						+ "?EdorNo?"
						+ "' "
						+ "and EdorType = '"
						+ "?EdorType?"
						+ "' "
						+ "and GrpContNo = '"
						+ "?GrpContNo?" + "'";
				// logger.debug(UpdateSQL);
				SQLwithBindVariables usqlbv=new SQLwithBindVariables();
				usqlbv.sql(UpdateSQL);
				usqlbv.put("EdorNo", mLPGrpEdorItemSchema.getEdorNo());
				usqlbv.put("EdorType", mLPGrpEdorItemSchema.getEdorType());
				usqlbv.put("GrpContNo", mLPGrpEdorItemSchema.getGrpContNo());
				usqlbv.put("theCurrentDate", theCurrentDate);
				usqlbv.put("theCurrentTime", theCurrentTime);
				mMap.put(usqlbv, "UPDATE");
			}
		} else if (mOperate.equalsIgnoreCase("G&EDORITEM")) // 撤销整个团体保全项目
		{
			// 更新该保全项目之后添加的保全项目状态为未录入
			if (!updateData()) {
				return false;
			}

			// 调用保全项目对应的撤销处理类
			if (!calGrpEdorXXCancelBL()) {
				return false;
			}

			// 循环调用个单撤销
			if (!cancelAllEdorItem()) {
				return false;
			}

			// 删除数据
			if (!delTable()) {
				return false;
			}

			// 撤销对理赔和续期的挂起状态
			BqContHangUpBL tContHangUpBL = new BqContHangUpBL(mGlobalInput);
			if (!tContHangUpBL.HangUpGrp_RN_Claim(mLPGrpEdorItemSchema
					.getGrpContNo(), mLPGrpEdorItemSchema.getEdorType(), "0")) {
				CError.buildErr(this, "团体保单挂起失败! ");
				return false;
			} else {
				MMap tMap = tContHangUpBL.getMMap();
				mMap.add(tMap);
			}
		}
		// <!-- XinYQ added on 2006-04-19 : 保全项目公用险种操作 : BGN -->
		else if (mOperate.equalsIgnoreCase("G&EdorRisk")) // 删除团体保全险种
		{
			return delGrpEdorRisk();
		}
		// <!-- XinYQ added on 2006-04-19 : 保全项目公用险种操作 : END -->

		return true;
	}

	/**
	 * 删除数据
	 * 
	 * @return: boolean
	 */
	private boolean delTable() {
		String delSql;
		String tEdorNo = mLPGrpEdorItemSchema.getEdorNo();
		String tEdorType = mLPGrpEdorItemSchema.getEdorType();
		String[] tableForDel = { "LPGeneral", "LPGrpCont", "LPCont",
				"LPGeneralToRisk", "LPPol", "LPGrpAppnt", "LPGrpPol",
				"LPGetToAcc", "LPInsureAcc", "LPPremToAcc", "LPPrem", "LPGet",
				"LPDuty", "LPPrem_1", "LPInsureAccClass", "LPInsureAccTrace",
				"LPContPlanDutyParam", "LPContPlanRisk", "LPContPlan",
				"LPAppnt", "LPCustomerImpart", "LPInsuredRelated", "LPBnf",
				"LPInsured", "LPCustomerImpartParams", "LPInsureAccFee",
				"LPInsureAccClassFee", "LPContPlanFactory", "LPContPlanParam",
				"LPGrpFee", "LPGrpFeeParam", "LPMove", "LPAppntTrace",
				"LPLoan", "LPReturnLoan","LPGUWError",
				"LPGUWMaster", "LPCUWError", "LPCUWMaster", "LPCUWSub",
				"LPUWError", "LPUWMaster", "LPUWSub", "LPGCUWError",
				"LPGCUWMaster", "LPGCUWSub", "LPGUWSub", "LPSpec",
				"LPIssuePol", "LPGrpIssuePol", "LPCGrpSpec", "LPCSpec",
				"LPAccount", "LPPerson", "LPAddress", "LPGrpAddress", "LPGrp",
				"LPPayRuleFactory", "LPPayRuleParams","LPGrpContState",
				"LPCustomerImpartDetail", "LPAccMove" };
		for (int i = 0; i < tableForDel.length; i++) {
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			delSql = "delete from  " + tableForDel[i] + " where EdorNo = '"
					+ "?tEdorNo?" + "' and EdorType='" + "?tEdorType?" + "'";
			sbv.sql(delSql);
			sbv.put("tEdorNo", tEdorNo);
			sbv.put("tEdorType", tEdorType);
			mMap.put(sbv, "DELETE");
		}

		// 更新主表、批单表相关费用字段
		// Q: 考虑是否有必要再去更新这几个字段 zhangtao 2005-10-18
		// A: 没有必要 zhangtao 2005-10-18

		// 删除批改补退费表的相关记录
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql("delete from LJSGetEndorse where EndorsementNo = '"
				+ "?EndorsementNo?"
				+ "' and FeeOperationType = '"
				+ "?FeeOperationType?" + "' and GrpContNo = '"
				+ "?GrpContNo?" + "' ");
		sbv1.put("EndorsementNo", mLPGrpEdorItemSchema.getEdorNo());
		sbv1.put("FeeOperationType", mLPGrpEdorItemSchema.getEdorType());
		sbv1.put("GrpContNo", mLPGrpEdorItemSchema.getGrpContNo());
		mMap.put(sbv1, "DELETE");

		// 删除团体项目下所有个单保全项目
		String sql = " delete from lpedoritem where edorno = '"
				+ "?EndorsementNo?" + "' and edortype = '"
				+ "?FeeOperationType?" + "' and grpcontno= '"
				+ "?GrpContNo?" + "'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.put("EndorsementNo", mLPGrpEdorItemSchema.getEdorNo());
		sbv2.put("FeeOperationType", mLPGrpEdorItemSchema.getEdorType());
		sbv2.put("GrpContNo", mLPGrpEdorItemSchema.getGrpContNo());
		mMap.put(sbv2, "DELETE");

		// 将将数据备份，并存储备份信息
		LOBGrpEdorItemSchema cLOBGrpEdorItemSchema = new LOBGrpEdorItemSchema();
		Reflections crf = new Reflections();
		crf.transFields(cLOBGrpEdorItemSchema, mLPGrpEdorItemSchema);
		cLOBGrpEdorItemSchema.setReason(delReason); // 添加撤销原因
		cLOBGrpEdorItemSchema.setReasonCode(reasonCode); // 添加撤销原因
		mMap.put(cLOBGrpEdorItemSchema, "DELETE&INSERT");
		mMap.put(mLPGrpEdorItemSchema, "DELETE");

		// 若是最后一条项目，则删除对应批单
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql("delete from LPGrpEdorMain where EdorNo= '"
				+ "?EdorNo?"
				+ "' and 0 = (select count(*) from LPGrpEdorItem where LPGrpEdorItem.EdorNo = '"
				+ "?EdorNo?" + "')");
		sbv3.put("EdorNo", tEdorNo);
		mMap
				.put(sbv3, "DELETE");
		return true;
	}

	/**
	 * 调用保全项目对应的撤销处理类
	 * 
	 * @return: boolean
	 */
	private boolean calGrpEdorXXCancelBL() {
		String sEdorType = mLPGrpEdorItemSchema.getEdorType();
		try {
			Class tClass = Class.forName("com.sinosoft.lis.bq.GrpEdor"
					+ sEdorType + "CancelBL");
			EdorCancel tGrpEdorCancel = (EdorCancel) tClass.newInstance();
			VData tVData = new VData();

			tVData.add(mLPGrpEdorItemSchema);
			tVData.add(mGlobalInput);

			if (!tGrpEdorCancel.submitData(tVData, "EDORCANCEL||" + sEdorType)) {
				mErrors.copyAllErrors(tGrpEdorCancel.getErrors());
				return false;
			} else {
				VData rVData = tGrpEdorCancel.getResult();
				MMap tMap = new MMap();
				tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
				if (tMap == null) {
					mErrors.addOneError(new CError("得到保单号为："
							+ mLPGrpEdorItemSchema.getGrpContNo() + "保全项目为:"
							+ sEdorType + "的保全撤销结果时失败!"));
					return false;
				} else {
					mMap.add(tMap);
				}
			}

		} catch (ClassNotFoundException ex) {
			// 没有不用报错
		} catch (Exception ex) {
			CError.buildErr(this, "保全项目" + sEdorType + "撤销失败!");
			return false;
		}

		return true;
	}

	/**
	 * 循环调用个单撤销
	 * 
	 * @return boolean
	 */
	private boolean cancelAllEdorItem() {
		// 查出在该保全项目之后添加的保全项目
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		mLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "个人保全项目查询失败!");
			return false;
		}
		if (mLPEdorItemSet != null && mLPEdorItemSet.size() > 0) {
			for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
				// 调用个单撤销
				LPEdorItemSchema tLPEdorItemSchema = (LPEdorItemSchema) mInputData
						.getObjectByObjectName("LPEdorItemSchema", 0);
				if (tLPEdorItemSchema != null) {
					mInputData.remove(tLPEdorItemSchema);
				}

				mInputData.add(mLPEdorItemSet.get(i));
				PEdorItemCancelBL tPEdorItemCancelBL = new PEdorItemCancelBL();
				if (!tPEdorItemCancelBL.submitData(mInputData, "EDORITEM")) {
					this.mErrors.copyAllErrors(tPEdorItemCancelBL.mErrors);
					return false;
				} else {
					mMap.add(tPEdorItemCancelBL.getMap());
				}
			}
		}

		return true;
	}

	// <!-- XinYQ added on 2006-04-19 : 保全项目公用险种操作 : BGN -->
	// ==========================================================================

	/**
	 * 添加团单保全险种
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean delGrpEdorRisk() {
		String sEdorAcceptNo = (String) mTransferData
				.getValueByName("EdorAcceptNo");
		String sEdorNo = (String) mTransferData.getValueByName("EdorNo");
		String sEdorType = (String) mTransferData.getValueByName("EdorType");
		String sGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		String sGrpPolNo = (String) mTransferData.getValueByName("GrpPolNo");

		// ----------------------------------------------------------------------

		if (sEdorAcceptNo == null || sEdorAcceptNo.trim().equals("")
				|| sEdorNo == null || sEdorNo.trim().equals("")
				|| sEdorType == null || sEdorType.trim().equals("")
				|| sGrpContNo == null || sGrpContNo.trim().equals("")
				|| sGrpPolNo == null || sGrpPolNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全受理号、批单号、批改类型、集体合同号和集体险种号！");
			return false;
		}

		// ----------------------------------------------------------------------

		String QuerySQL = new String("");
		String DeleteSQL = new String("");
		String UpdateSQL = new String("");

		// ----------------------------------------------------------------------

		// 查询 LPEdorItem
		QuerySQL = "select * " + "from LPEdorItem " + "where 1 = 1 "
				+ "and EdorAcceptNo = '" + "?sEdorAcceptNo?" + "' "
				+ "and EdorNo = '" + "?sEdorNo?" + "' " + "and EdorType = '"
				+ "?sEdorType?" + "' " + "and PolNo in " + "(select PolNo "
				+ "from LCPol " + "where 1 = 1 " + "and GrpContNo = '"
				+ "?sGrpContNo?" + "' " + "and GrpPolNo = '" + "?sGrpPolNo?" + "')";
		// logger.debug(QuerySQL);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(QuerySQL);
		sqlbv.put("sEdorAcceptNo", sEdorAcceptNo);
		sqlbv.put("sEdorNo", sEdorNo);
		sqlbv.put("sEdorType", sEdorType);
		sqlbv.put("sGrpContNo", sGrpContNo);
		sqlbv.put("sGrpPolNo", sGrpPolNo);
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		mLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "个人保全项目查询失败!");
			return false;
		}
		if (mLPEdorItemSet != null && mLPEdorItemSet.size() > 0) {
			for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
				// 循环调用个单撤销
				LPEdorItemSchema tLPEdorItemSchema = (LPEdorItemSchema) mInputData
						.getObjectByObjectName("LPEdorItemSchema", 0);
				if (tLPEdorItemSchema != null) {
					mInputData.remove(tLPEdorItemSchema);
				}

				mInputData.add(mLPEdorItemSet.get(i));
				PEdorItemCancelBL tPEdorItemCancelBL = new PEdorItemCancelBL();
				if (!tPEdorItemCancelBL.submitData(mInputData, "EDORITEM")) {
					this.mErrors.copyAllErrors(tPEdorItemCancelBL.mErrors);
					return false;
				} else {
					mMap.add(tPEdorItemCancelBL.getMap());
				}
			}
		}

		// ----------------------------------------------------------------------

		// 删除 LPEdorItem
		DeleteSQL = "delete from LPEdorItem " + "where 1 = 1 "
				+ "and EdorAcceptNo = '" + "?sEdorAcceptNo?" + "' "
				+ "and EdorNo = '" + "?sEdorNo?" + "' " + "and EdorType = '"
				+ "?sEdorType?" + "' " + "and PolNo in " + "(select PolNo "
				+ "from LCPol " + "where 1 = 1 " + "and GrpContNo = '"
				+ "?sGrpContNo?" + "' " + "and GrpPolNo = '" + "?sGrpPolNo?" + "')";
		// logger.debug(DeleteSQL);
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(DeleteSQL);
		sqlbv1.put("sEdorAcceptNo", sEdorAcceptNo);
		sqlbv1.put("sEdorNo", sEdorNo);
		sqlbv1.put("sEdorType", sEdorType);
		sqlbv1.put("sGrpContNo", sGrpContNo);
		sqlbv1.put("sGrpPolNo", sGrpPolNo);
		mMap.put(sqlbv1, "DELETE");

		// ----------------------------------------------------------------------

		// 删除 LPGrpPol
		DeleteSQL = "delete from LPGrpPol " + "where 1 = 1 " + "and EdorNo = '"
				+ "?sEdorNo?" + "' " + "and EdorType = '" + "?sEdorType?" + "' "
				+ "and GrpContNo = '" + "?sGrpContNo?" + "' " + "and GrpPolNo = '"
				+ "?sGrpPolNo?" + "'";
		// logger.debug(DeleteSQL);
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(DeleteSQL);
		sqlbv2.put("sEdorNo", sEdorNo);
		sqlbv2.put("sEdorType", sEdorType);
		sqlbv2.put("sGrpContNo", sGrpContNo);
		sqlbv2.put("sGrpPolNo", sGrpPolNo);
		mMap.put(sqlbv2, "DELETE");

		// ----------------------------------------------------------------------

		// 更新 LPGrpEdorItem
		UpdateSQL = "update LPGrpEdorItem " + "set GetMoney = "
				+ "(select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end) " + "from LPEdorItem "
				+ "where 1 = 1 " + "and EdorNo = '"
				+ "?sEdorNo?"
				+ "' "
				+ "and EdorType = '"
				+ "?sEdorType?"
				+ "' "
				+ "and GrpContNo = '"
				+ "?sGrpContNo?"
				+ "'), "
				+ "ModifyDate = '?theCurrentDate?', "
				+ "ModifyTime = '?theCurrentTime?' "
				+ "where 1 = 1 "
				+ "and EdorNo = '"
				+ "?sEdorNo?"
				+ "' "
				+ "and EdorType = '"
				+ "?sEdorType?"
				+ "' "
				+ "and GrpContNo = '"
				+ "?sGrpContNo?" + "'";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(UpdateSQL);
		sbv3.put("sEdorNo", sEdorNo);
		sbv3.put("sEdorType", sEdorType);
		sbv3.put("sGrpContNo", sGrpContNo);
		sbv3.put("theCurrentDate", theCurrentDate);
		sbv3.put("theCurrentTime", theCurrentTime);
		// logger.debug(UpdateSQL);
		mMap.put(sbv3, "UPDATE");

		return true;
	}

	// ==========================================================================
	// <!-- XinYQ added on 2006-04-19 : 保全项目公用险种操作 : END -->

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
