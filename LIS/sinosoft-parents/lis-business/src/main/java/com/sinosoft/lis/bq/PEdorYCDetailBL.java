package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LMDutyPayAddFeeSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zhangtao
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2006-09-28, 2006-10-10, 2006-10-18, 2007-05-15
 * @direction: 保全保险期限变更明细
 ******************************************************************************/
import com.sinosoft.lis.bl.LCPolBL;

// ******************************************************************************

public class PEdorYCDetailBL {
private static Logger logger = Logger.getLogger(PEdorYCDetailBL.class);
	// public PEdorBCDetailBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData mInputData;
	private String mOperation;
	private GlobalInput mGlobalInput;
	private LPPolSchema mLPPolSchema;
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();// 批改补退费记录

	private Reflections mRef = new Reflections();
	private LPEdorItemSchema mLPEdorItemSchema;

	// 输出数据
	private MMap mMap;
	private VData mResult;

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> PEdorBCDetailBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> PEdorBCDetailBL.submitData() : 无法获取 InputData 数据！");
			return false;
		} else {
			mInputData = new VData();
			mInputData = (VData) cInputData.clone();
		}
		mOperation = (cOperate != null) ? cOperate.trim() : "";

		// ----------------------------------------------------------------------

		// 业务处理
		if (!getInputData())
			return false;
		if (!checkData())
			return false;
		if (!dealData())
			return false;
		if (!outputData())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> PEdorBCDetailBL.submitData() 结束");
		return true;
	} // function submitData end

	// ==========================================================================

	/**
	 * 获取外部传入数据和校验必录字段的合法性
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean getInputData() {
		// logger.debug("\t@> PEdorBCDetailBL.getInputData() 开始");

		// GlobalInput
		mGlobalInput = new GlobalInput();
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PEdorBCDetailBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// LPEdorItemSchema
		mLPEdorItemSchema = new LPEdorItemSchema();
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		if (mLPEdorItemSchema == null) {
			CError.buildErr(this, "无法获取待操作保全项目信息！");
			logger.debug("\t@> PEdorBCDetailBL.getInputData() : 无法获取 LPEdorItemSchema 数据！");
			return false;
		}

		// LPPolSchema
		mLPPolSchema = (LPPolSchema) mInputData.getObjectByObjectName(
				"LPPolSchema", 0);
		if (mLPPolSchema == null) {
			CError.buildErr(this, "无法获取变更之后的保险期限信息！");
			logger.debug("\t@> PEdorBCDetailBL.getInputData() : 无法获取 LPPolSchema 数据！");
			return false;
		}

		// logger.debug("\t@> PEdorBCDetailBL.getInputData() 结束");
		return true;
	} // function getInputData end

	// ==========================================================================

	/**
	 * 根据传入的数据进行业务逻辑层的合法性校验
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean checkData() {
		// logger.debug("\t@> PEdorBCDetailBL.checkData() 开始");

		// 查询 LPEdorItem
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		try {
			tLPEdorItemSet = tLPEdorItemDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全项目明细表发生异常！");
			return false;
		}
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			CError.buildErr(this, "在保全项目明细表中找不到待操作批单的纪录！");
			return false;
		} else {
			mLPEdorItemSchema = tLPEdorItemSet.get(1);
		}
		tLPEdorItemDB = null;
		tLPEdorItemSet = null;

		// logger.debug("\t@> PEdorBCDetailBL.checkData() 结束");
		return true;
	} // function checkData end

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean dealData() {
		// logger.debug("\t@> PEdorBCDetailBL.dealData() 开始");
		mMap = new MMap();
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// 每次保存现删除P表数据
		delPData();

		// ----------------------------------------------------------------------
		// 查询 LCPol
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = tLCPolDB.query();
		if (tLCPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "险种信息查询失败！");
			return false;
		}
		if (tLCPolSet == null || tLCPolSet.size() <= 0) {
			CError.buildErr(this, "险种信息查询失败！");
			return false;
		}

		LPPolSchema tLPPolSchemaOld = new LPPolSchema();
		PubFun.copySchema(tLPPolSchemaOld, tLCPolSet.get(1));

		LPPolSchema tLPPolSchemaNew = new LPPolSchema();
		tLPPolSchemaNew = tLPPolSchemaOld.getSchema();
		tLPPolSchemaNew.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolSchemaNew.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSchemaNew.setCValiDate(mLPPolSchema.getCValiDate());
		tLPPolSchemaNew.setEndDate(mLPPolSchema.getEndDate());
		tLPPolSchemaNew.setOperator(mGlobalInput.Operator);
		tLPPolSchemaNew.setModifyDate(sCurrentDate);
		tLPPolSchemaNew.setModifyTime(sCurrentTime);
		// 计算保险期限
		if (mLPEdorItemSchema.getEdorType().equals("YB"))// 保险起期提前
		{
			// 保险期限不变，calbl会根据保险起期和保险期限自动计算出保险止期
		} else {
			// 根据保险起止期计算保险期限
			int iInsuyear = PubFun.calInterval(mLPPolSchema.getCValiDate(),
					mLPPolSchema.getEndDate(), "D");
			tLPPolSchemaNew.setInsuYear(iInsuyear);
			tLPPolSchemaNew.setInsuYearFlag("D");
		}

		// 重新计算保费保额
		if (!ReCalculate(tLPPolSchemaNew, mLPEdorItemSchema)) {
			return false;
		}
		mLPPolSchema.setSchema(tLPPolSchemaNew);

		// 查询 LCCont
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		LCContSet tLCContSet = new LCContSet();
		tLCContSet = tLCContDB.query();
		if (tLCContDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单信息查询失败！");
			return false;
		}
		if (tLCContSet == null || tLCContSet.size() <= 0) {
			CError.buildErr(this, "保单信息查询失败！");
			return false;
		}
		LPContSchema tLPContSchemaNew = new LPContSchema();
		PubFun.copySchema(tLPContSchemaNew, tLCContSet.get(1));
		tLPContSchemaNew.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchemaNew.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchemaNew.setCValiDate(mLPPolSchema.getCValiDate());
		tLPContSchemaNew.setPaytoDate(mLPPolSchema.getPaytoDate());
		// tLPContSchemaNew.setOperator(mGlobalInput.Operator);
		tLPContSchemaNew.setModifyDate(sCurrentDate);
		tLPContSchemaNew.setModifyTime(sCurrentTime);

		double dChgPrem = mLPPolSchema.getPrem() - tLPPolSchemaOld.getPrem();
		double dGetMoney = dChgPrem;
		// 创建批改补退费
		if (dGetMoney > 0) {
			if (!createLJSGetEndorse(dGetMoney, "BF", BqCode.Pay_Prem,
					mLPEdorItemSchema)) {
				return false;
			}
		} else if (dGetMoney < 0) {
			if (!createLJSGetEndorse(dGetMoney, "TF", BqCode.Get_Prem,
					mLPEdorItemSchema)) {
				return false;
			}
		}
		// 更新 LPEdorItem
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setModifyDate(sCurrentDate);
		mLPEdorItemSchema.setModifyTime(sCurrentTime);
		mLPEdorItemSchema.setChgPrem(dChgPrem);
		mLPEdorItemSchema.setChgAmnt(0);
		mLPEdorItemSchema.setGetMoney(dGetMoney);
		mMap.put(mLPEdorItemSchema, "UPDATE");

		mMap.put(tLPContSchemaNew, "INSERT");
		mMap.put(mLPPolSchema, "INSERT");
		mMap.put(mLPDutySet, "INSERT");
		mMap.put(mLPPremSet, "INSERT");
		mMap.put(mLPGetSet, "INSERT");
		mMap.put(mLPEdorItemSchema, "UPDATE");
		mMap.put(mLJSGetEndorseSet, "INSERT");
		return true;
	} // function dealData end

	/**
	 * 重新计算保费
	 * 
	 * @param aLPPolSchema
	 * @return boolean
	 */
	private boolean ReCalculate(LPPolSchema aLPPolSchema,
			LPEdorItemSchema pLPEdorItemSchema) {
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		tLMRiskAppDB.setRiskCode(aLPPolSchema.getRiskCode());
		LMRiskAppSet tLMRiskAppSet = tLMRiskAppDB.query();
		if (tLMRiskAppDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
			CError.buildErr(this, "查询险种描述信息失败!");
			return false;
		}
		if (tLMRiskAppSet == null || tLMRiskAppSet.size() != 1) {
			CError.buildErr(this, "查询险种描述信息失败!");
			return false;
		}
		ReCalBL tReCalBL = new ReCalBL(aLPPolSchema, pLPEdorItemSchema);
		// 准备重算需要的保单表数据
		LCPolBL tLCPolBL = tReCalBL.getRecalPol(aLPPolSchema);
		// 准备重算需要的责任表数据
		LCDutyBLSet tLCDutyBLSet = tReCalBL.getRecalDuty(pLPEdorItemSchema);
		// 准备重算需要的保费项表数据
		LCPremSet tLCPermSet = tReCalBL.getRecalPrem(pLPEdorItemSchema);
		// 准备重算需要的领取项表数据
		LCGetBLSet tLCGetBLSet = tReCalBL.getRecalGet(pLPEdorItemSchema);
		for (int i = 1; i <= tLCDutyBLSet.size(); i++) {
			tLCDutyBLSet.get(i).setCValiDate(aLPPolSchema.getCValiDate());
			tLCDutyBLSet.get(i).setEndDate(aLPPolSchema.getEndDate());
			tLCDutyBLSet.get(i).setInsuYear(aLPPolSchema.getInsuYear());
			tLCDutyBLSet.get(i).setInsuYearFlag(aLPPolSchema.getInsuYearFlag());
		}
		for (int i = 1; i <= tLCPermSet.size(); i++) {
			tLCPermSet.get(i).setPayStartDate(aLPPolSchema.getCValiDate());
			tLCPermSet.get(i).setPayEndDate(aLPPolSchema.getEndDate());
		}

		// 重新计算加费金额
		LCPremSet afterLCPermSet = recalAddPrem(aLPPolSchema, tLCPermSet,
				tLCDutyBLSet);
		if (afterLCPermSet == null) {
			return false;
		}
		tReCalBL.preLCPremSet = afterLCPermSet;

		// 重算
		if (!tReCalBL.recalWithData(tLCPolBL, tLCDutyBLSet, afterLCPermSet,
				tLCGetBLSet, pLPEdorItemSchema)) {
			this.mErrors.copyAllErrors(tReCalBL.mErrors);
			CError.buildErr(this, "保费重算失败" + tReCalBL.mErrors.getFirstError());
			return false;
		}

		aLPPolSchema.setSchema(tReCalBL.aftLPPolSet.get(1));

		aLPPolSchema.setPaytoDate(aLPPolSchema.getPayEndDate());
		for (int i = 1; i <= tReCalBL.aftLPDutySet.size(); i++) {
			tReCalBL.aftLPDutySet.get(i).setPaytoDate(
					aLPPolSchema.getPayEndDate());
		}
		for (int i = 1; i <= tReCalBL.aftLPPremSet.size(); i++) {
			tReCalBL.aftLPPremSet.get(i).setPaytoDate(
					aLPPolSchema.getPayEndDate());
		}

		mLPDutySet.add(tReCalBL.aftLPDutySet);
		mLPPremSet.add(tReCalBL.aftLPPremSet);
		mLPGetSet.add(tReCalBL.aftLPGetSet);

		return true;
	}

	/**
	 * 重新计算加费金额
	 * 
	 * @param tLCPolSchema
	 * @return boolean
	 */
	private LCPremSet recalAddPrem(LPPolSchema tLPPolSchema,
			LCPremSet tLCPermSet, LCDutyBLSet tLCDutyBLSet) {
		LCPremSet rLCPermSet = new LCPremSet();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		mRef.transFields(tLCPolSchema, tLPPolSchema);
		LCDutySchema tLCDutySchema;
		LCPremSchema tLCPremSchema;
		LMDutyPayAddFeeSchema tLMDutyPayAddFeeSchema;
		for (int i = 1; i <= tLCPermSet.size(); i++) {
			if (tLCPermSet.get(i).getPayPlanType() != null
					&& !tLCPermSet.get(i).getPayPlanType().equals("0")) {
				tLMDutyPayAddFeeSchema = new LMDutyPayAddFeeSchema();
				tLMDutyPayAddFeeSchema.setAddFeeObject(tLCPermSet.get(i)
						.getAddFeeDirect());
				tLMDutyPayAddFeeSchema.setAddFeeType(tLCPermSet.get(i)
						.getPayPlanType());

				tLCDutySchema = getLCDuty(tLCPermSet.get(i).getDutyCode(),
						tLCDutyBLSet);
				if (tLCDutySchema == null) {
					CError.buildErr(this, "加费项责任编码有误!");
					return null;
				}

				tLCPremSchema = new LCPremSchema();
				mRef.transFields(tLCPremSchema, tLCPermSet.get(i).getSchema());

				VData tInputData = new VData();
				tInputData.add(tLCPolSchema);
				tInputData.add(tLCPremSchema);
				tInputData.add(tLCDutySchema);
				tInputData.add(tLMDutyPayAddFeeSchema);
				tInputData.add(mGlobalInput);

				AddPremCalBL tAddPremCalBL = new AddPremCalBL();
				if (!tAddPremCalBL.submitData(tInputData, "")) {
					CError.buildErr(this, "加费计算失败!");
					return null;
				}
				TransferData tTransferData = (TransferData) ((VData) tAddPremCalBL
						.getResult()).getObjectByObjectName("TransferData", 0);
				String sNewAddFee = (String) tTransferData
						.getValueByName("mValue");
				if (!sNewAddFee.equals("-1")) {
					tLCPermSet.get(i).setPrem(
							(String) tTransferData.getValueByName("mValue"));
				} else
				// 没有加费费率
				{
					CError.buildErr(this, "没有对应的加费费率，请上报申请并补充表外费率!");
					return null;
				}
			}
			rLCPermSet.add(tLCPermSet.get(i));
		}
		return rLCPermSet;
	}

	/**
	 * 获取加费项对应的责任项
	 * 
	 * @param sDutyCode
	 * @return LCDutySchema
	 */
	private LCDutySchema getLCDuty(String sDutyCode, LCDutyBLSet tLCDutyBLSet) {
		LCDutySchema tLCDutySchema = new LCDutySchema();
		for (int i = 1; i <= tLCDutyBLSet.size(); i++) {
			if (sDutyCode != null
					&& sDutyCode.equals(tLCDutyBLSet.get(i).getDutyCode())) {
				mRef
						.transFields(tLCDutySchema, tLCDutyBLSet.get(i)
								.getSchema());
				return tLCDutySchema;
			}
		}

		return null;
	}

	/**
	 * 删除上次保存数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean delPData() {
		String DeleteSQL;
		// 删除 LPCont
		DeleteSQL = "delete from LPCont " + "where 1 = 1 " + "and EdorNo = '?EdorNo?' " + "and EdorType = '?EdorType?' " + "and ContNo = '?ContNo?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(DeleteSQL);
		sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("EdorType", mLPEdorItemSchema.getEdorType());
		sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
		// logger.debug(DeleteSQL);
		mMap.put(sqlbv, "DELETE");

		// 删除 LPPol
		DeleteSQL = "delete from LPPol " + "where 1 = 1 " + "and EdorNo = '?EdorNo?' " + "and EdorType = '?EdorType?' " + "and ContNo = '?ContNo?' " + "and PolNo = '?PolNo?'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(DeleteSQL);
		sqlbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sqlbv1.put("EdorType", mLPEdorItemSchema.getEdorType());
		sqlbv1.put("ContNo", mLPEdorItemSchema.getContNo());
		sqlbv1.put("PolNo", mLPEdorItemSchema.getPolNo());
		// logger.debug(DeleteSQL);
		mMap.put(sqlbv1, "DELETE");

		// 删除 LPDuty
		DeleteSQL = "delete from LPDuty " + "where 1 = 1 " + "and EdorNo = '?EdorNo?' " + "and EdorType = '?EdorType?' " + "and ContNo = '?ContNo?' " + "and PolNo = '?PolNo?'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(DeleteSQL);
		sqlbv2.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sqlbv2.put("EdorType", mLPEdorItemSchema.getEdorType());
		sqlbv2.put("ContNo", mLPEdorItemSchema.getContNo());
		sqlbv2.put("PolNo", mLPEdorItemSchema.getPolNo());
		 
		// logger.debug(DeleteSQL);
		mMap.put(sqlbv2, "DELETE");

		// 删除 LPPrem
		DeleteSQL = "delete from LPPrem " + "where 1 = 1 " + "and EdorNo = '?EdorNo?' " + "and EdorType = '?EdorType?' " + "and ContNo = '?ContNo?' " + "and PolNo = '?PolNo?'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(DeleteSQL);
		sqlbv3.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sqlbv3.put("EdorType", mLPEdorItemSchema.getEdorType());
		sqlbv3.put("ContNo", mLPEdorItemSchema.getContNo());
		sqlbv3.put("PolNo", mLPEdorItemSchema.getPolNo());
		// logger.debug(DeleteSQL);
		mMap.put(sqlbv3, "DELETE");

		// 删除 LPGet
		DeleteSQL = "delete from LPGet " + "where 1 = 1 " + "and EdorNo = '?EdorNo?' " + "and EdorType = '?EdorType?' " + "and ContNo = '?ContNo?' " + "and PolNo = '?PolNo?'";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(DeleteSQL);
		sqlbv4.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sqlbv4.put("EdorType", mLPEdorItemSchema.getEdorType());
		sqlbv4.put("ContNo", mLPEdorItemSchema.getContNo());
		sqlbv4.put("PolNo", mLPEdorItemSchema.getPolNo());
		// logger.debug(DeleteSQL);
		mMap.put(sqlbv4, "DELETE");

		// 删除LJSGetEndorse
		DeleteSQL = " delete from LJSGetEndorse " + "where 1 = 1 " + "and EndorsementNo = '?EndorsementNo?' " + "and FeeOperationType = '?FeeOperationType?' " + "and contno = '?contno?' " + "and PolNo = '?PolNo?'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(DeleteSQL);
		sqlbv5.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sqlbv5.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sqlbv5.put("contno", mLPEdorItemSchema.getContNo());
		sqlbv5.put("polno", mLPEdorItemSchema.getPolNo());

		mMap.put(sqlbv5, "DELETE");

		return true;
	}

	// ==========================================================================

	/**
	 * 创建批改补退费记录
	 * 
	 * @param dBJJE
	 * @param sBQFeeType
	 * @param sSubFeeOperationType
	 * @return boolean
	 */
	private boolean createLJSGetEndorse(double dMoneyValue, String sBQFeeType,
			String sSubFeeOperationType, LPEdorItemSchema pLPEdorItemSchema) {
		if (Math.abs(dMoneyValue - 0) < 0.01) {
			return true;
		}

		String sFeeType = BqCalBL.getFinType(pLPEdorItemSchema.getEdorType(),
				sBQFeeType, mLPPolSchema.getPolNo());

		BqCalBL tBqCalBL = new BqCalBL();
		LJSGetEndorseSchema tLJSGetEndorseSchema;

		tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema = tBqCalBL.initLJSGetEndorse(pLPEdorItemSchema,
				mLPPolSchema, null, sSubFeeOperationType, sFeeType, Arith
						.round(dMoneyValue, 2), mGlobalInput);
		// 用子业务类型区分同一保全项目下不同的费用
		if (tLJSGetEndorseSchema == null) {
			mErrors.copyAllErrors(tBqCalBL.mErrors);
			return false;
		}
        //营改增 add zhangyingfeng 2016-07-14
        //价税分离 计算器
        TaxCalculator.calBySchema(tLJSGetEndorseSchema);
        //end zhangyingfeng 2016-07-14
		mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		return true;
	}

	/**
	 * 准备经过本类处理的输出数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean outputData() {
		// logger.debug("\t@> PEdorBCDetailBL.outputData() 开始");

		mResult = new VData();
		mResult.add(mMap);

		// logger.debug("\t@> PEdorBCDetailBL.outputData() 结束");
		return true;
	} // function outputData end

	// ==========================================================================

	/**
	 * 返回经过本类处理的数据结果
	 * 
	 * @param null
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	} // function getResult end

	// ==========================================================================

	/**
	 * 返回传入本类的操作类型
	 * 
	 * @param null
	 * @return String
	 */
	public String getOperation() {
		return mOperation;
	} // function getOperation end

	// ==========================================================================

	/**
	 * 返回本类运行时产生的错误信息
	 * 
	 * @param null
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	} // function getErrors end

	// ==========================================================================

	/**
	 * 处理本类运行时产生的垃圾
	 * 
	 * @param null
	 */
	private void collectGarbage() {
		if (mInputData != null)
			mInputData = null;
		if (mGlobalInput != null)
			mGlobalInput = null;
		if (mLPPolSchema != null)
			mLPPolSchema = null;
		if (mLPEdorItemSchema != null)
			mLPEdorItemSchema = null;
		if (mMap != null)
			mMap = null;
	} // function collectGarbage end

	// ==========================================================================

} // class PEdorBCDetailBL end
