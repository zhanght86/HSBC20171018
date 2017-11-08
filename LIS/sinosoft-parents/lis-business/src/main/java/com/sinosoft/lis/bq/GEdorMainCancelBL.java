package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOBGrpEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
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
public class GEdorMainCancelBL {
private static Logger logger = Logger.getLogger(GEdorMainCancelBL.class);
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

	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
	private TransferData tTransferData = new TransferData();
	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public GEdorMainCancelBL() {
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
		// logger.debug("PEdorItemCancelBL报告："+delReason);
		logger.debug("reasonCode in GEdorMainCancel is:" + reasonCode);

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

		if (!"G&EDORAPP".equals(mOperate)) {
			PubSubmit tPubSubmit = new PubSubmit();

			if (!tPubSubmit.submitData(mResult, cOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);

				return false;
			}
		}
		return true;
	}

	/**
	 * 将UI层传输来得数据根据业务还原成具体的类
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		ExeSQL tExeSQL = new ExeSQL();
		// 全局变量实例
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			mErrors.addOneError(new CError("没有得到全局量信息"));

			return false;
		}

		// 团体保单实例
		mLPGrpEdorMainSchema.setSchema((LPGrpEdorMainSchema) mInputData
				.getObjectByObjectName("LPGrpEdorMainSchema", 0));

		if (mLPGrpEdorMainSchema == null) {
			this.mErrors.addOneError(new CError("传入的团单信息为空！"));

			return false;
		}
		tTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		delReason = (String) tTransferData.getValueByName("DelReason");
		reasonCode = (String) tTransferData.getValueByName("ReasonCode");

		String strSql = "select Max(a.edorno) from lpgrpedormain a where a.EdorState<>'0' and a.GrpContNo='"
				+ "?GrpContNo?" + "' ";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(strSql);
        sqlbv.put("GrpContNo", mLPGrpEdorMainSchema.getGrpContNo());
		String MaxEdorNo = tExeSQL.getOneValue(sqlbv);

		logger.debug("MaxEdorNo=" + MaxEdorNo);

		if ((MaxEdorNo == null) || (MaxEdorNo.trim() == "")) {
			this.mErrors.addOneError(new CError("该批单不是最近一条，无法删除!最大的批单号为："
					+ MaxEdorNo));
			return false;
		}

		if (!MaxEdorNo.equals(mLPGrpEdorMainSchema.getEdorNo())) {
			this.mErrors.addOneError(new CError("该批单不是最近一条，无法删除!!最大的批单号为："
					+ MaxEdorNo));
			return false;
		}

		return true;
	}

	/**
	 * 对业务数据进行加工 对于新增的操作，这里需要有生成新合同号和新客户号的操作。
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		String delSql;
		String tEdorNo = mLPGrpEdorMainSchema.getEdorNo();

		// 将将数据备份，并存储备份信息
		LPGrpEdorMainSchema cLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		LOBGrpEdorMainSchema cLOBGrpEdorMainSchema = new LOBGrpEdorMainSchema();
		LPGrpEdorMainDB cLPGrpEdorMainDB = new LPGrpEdorMainDB();
		LPGrpEdorMainSet cLPGrpEdorMainSet = new LPGrpEdorMainSet();

		// 选出当前记录
		logger.debug("%%%%%%%%" + delReason);
		String sql = "select * from LpGrpEdorMain where EdorNo='" + "?tEdorNo?"
				+ "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("tEdorNo", tEdorNo);
		cLPGrpEdorMainSet = cLPGrpEdorMainDB.executeQuery(sqlbv);
		if (cLPGrpEdorMainSet.size() > 0 && cLPGrpEdorMainSet != null) {
			for (int k = 1; k <= cLPGrpEdorMainSet.size(); k++) {
				cLPGrpEdorMainSchema = cLPGrpEdorMainSet.get(k);
				logger.debug("cLPGrpEdorMainSchema:"
						+ cLPGrpEdorMainSchema.getFieldCount()); // 测试用
				Reflections crf = new Reflections();
				crf.transFields(cLOBGrpEdorMainSchema, cLPGrpEdorMainSchema); // 将一条记录整体复制
				cLOBGrpEdorMainSchema.setReason(delReason); // 添加撤销原因
				cLOBGrpEdorMainSchema.setReasonCode(reasonCode); // 添加撤销原因编码
				cLOBGrpEdorMainSchema.setMakeDate(theCurrentDate); // 设置修改时间为当前时间
				cLOBGrpEdorMainSchema.setMakeTime(theCurrentTime);

				// cLOBGrpEdorItemSet.add(cLOBGrpEdorItemSchema);
			}
			mMap.put(cLOBGrpEdorMainSchema, "DELETE&INSERT");
		}

		String[] tableForDel = { "LPGeneral", "LPGrpCont", "LPCont",
				"LPGeneralToRisk", "LPPol", "LPGrpAppnt", "LPGrpPol",
				"LPGetToAcc", "LPInsureAcc", "LPPremToAcc", "LPPrem", "LPGet",
				"LPDuty", "LPPrem_1", "LPInsureAccClass", "LPInsureAccTrace",
				"LPContPlanDutyParam", "LPContPlanRisk", "LPContPlan",
				"LPAppnt", "LPCustomerImpart", "LPInsuredRelated", "LPBnf",
				"LPInsured", "LPCustomerImpartParams", "LPInsureAccFee",
				"LPInsureAccClassFee", "LPContPlanFactory", "LPContPlanParam",
				"LPGrpFee", "LPGrpFeeParam", "LPMove", "LPEdorPrint",
				"LPAppntTrace", "LPLoan", "LPReturnLoan", "LPEdorPrint2",
				"LPGUWError", "LPGUWMaster", "LPCUWError",
				"LPCUWMaster", "LPCUWSub", "LPUWError", "LPUWMaster",
				"LPUWSub", "LPPENoticeItem", "LPPENotice", "LPGCUWError",
				"LPGCUWMaster", "LPGCUWSub", "LPGUWSub", "LPSpec",
				"LPIssuePol", "LPGrpIssuePol", "LPCGrpSpec", "LPCSpec",
				"LPAccount", "LPPerson", "LPAddress", "LPGrpAddress", "LPGrp",
				"LPPayRuleFactory", "LPPayRuleParams", "LPRReportResult",
				"LPRReport", "LPRReportItem", "LPCustomerImpartDetail",
				"LPAccMove", "LPEdorItem", "LPEdorMain", "LPGrpEdorItem",
				"LPGrpEdorMain" };

		for (int i = 0; i < tableForDel.length; i++) {
			delSql = "delete from  " + tableForDel[i] + " where EdorNo = '"
					+ "?tEdorNo?" + "'";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(delSql);
			sbv.put("tEdorNo", tEdorNo);
			mMap.put(sbv, "DELETE");
		}

		// 对暂交费表操作
		String delFeeClass = "delete from LJTempFeeClass where"
				+ " TempFeeNo in (select TempFeeNo from LJTempFee where otherno='"
				+ "?tEdorNo?" + "' and otherNoType='3')";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(delFeeClass);
		sbv1.put("tEdorNo", tEdorNo);
		mMap.put(sbv1, "DELETE");

		String delFee = "delete from LJTempFee where otherno='" + "?tEdorNo?"
				+ "' and otherNoType='3'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(delFee);
		sbv2.put("tEdorNo", tEdorNo);
		mMap.put(sbv2, "DELETE");

		// 删除批改补退费表中相关记录
		delSql = "delete from  LJSGetEndorse where EndorsementNo = '" + "?tEdorNo?"
				+ "'";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(delSql);
		sbv3.put("tEdorNo", tEdorNo);
		mMap.put(sbv3, "DELETE");

		// 对于一些特殊的保全项目调用特殊的保全项目取消类
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorNo(tEdorNo);
		LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		for (int i = 0; i < tLPGrpEdorItemSet.size(); i++) {
			tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(i + 1);
			try {
				Class tClass = Class.forName("com.sinosoft.lis.bq.GrpEdor"
						+ tLPGrpEdorItemSchema.getEdorType() + "CancelBL");
				EdorCancel tGrpEdorCancel = (EdorCancel) tClass.newInstance();
				VData tVData = new VData();

				tVData.add(tLPGrpEdorItemSchema);
				tVData.add(mGlobalInput);

				if (!tGrpEdorCancel.submitData(tVData, "EDORCANCEL||"
						+ tLPGrpEdorItemSchema.getEdorType())) {
					return false;
				} else {
					VData rVData = tGrpEdorCancel.getResult();
					MMap tMap = new MMap();
					tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
					if (tMap == null) {
						mErrors.addOneError(new CError("得到保单号为："
								+ tLPGrpEdorItemSchema.getGrpContNo()
								+ "保全项目为:" + tLPGrpEdorItemSchema.getEdorType()
								+ "的保全申请确认结果时失败！"));
						return false;

					} else {
						mMap.add(tMap);
					}
				}

			} catch (ClassNotFoundException ex) {
				logger.debug("Not Found the "
						+ tLPGrpEdorItemSchema.getEdorType() + " Cancel Class");
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}

		}

		// mMap.put("delete from LPGrpEdorMain a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE"); //删除项目
		// mMap.put("delete from LPGrpEdorItem a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// /*******************************************************************************************/
		// mMap.put("delete from LPAccount a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPPerson a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPAddress a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPGrp a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPGrpAddress a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPAppnt a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPGrpAppnt a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPCustomerImpartParams a where a.EdorNo='" +
		// tEdorNo + "' ", "DELETE");
		//
		// mMap.put("delete from LPCustomerImpart a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPCont a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPGrpCont a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPContPlan a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPContPlanRisk a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPInsured a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPGeneral a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPInsuredRelated a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPBnf a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPPol a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPGrpPol a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPGeneralToRisk a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPDuty a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPContPlanDutyParam a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPContPlanFactory a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPContPlanParam a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPPrem_1 a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPPrem a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPGet a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPGrpFee a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPInsureAccFee a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPPremToAcc a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPGetToAcc a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPGrpFeeParam a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPInsureAccClassFee a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPInsureAcc a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPInsureAccClass a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPInsureAccTrace a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPLoan a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPAccMove a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPAppntTrace a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPReturnLoan a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPEdorPrint a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// mMap.put("delete from LPEdorPrint2 a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPEdorPrint3 a where a.EdorNo='" + tEdorNo +
		// "' ", "DELETE");
		//
		// mMap.put("delete from LPMove a where a.EdorNo='" + tEdorNo + "' ",
		// "DELETE");
		//
		// /*********************************************************************************************/
		// mMap.put("delete from lpedoritem where lpedoritem.EdorNo= '" + tEdorNo +
		// "' ", "DELETE"); //删除个单对应的项目
		//
		// mMap.put("delete from lpedorMain where EdorNo= '" + tEdorNo +
		// "' and 0=(select count(*) from LpEdorItem where lpedorItem.EdorNo='" +
		// tEdorNo + "')", "DELETE");

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

	public MMap getMap() {
		return mMap;
	}

}
