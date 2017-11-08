package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LMEdorItemDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LMEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团体保全申请-添加保全项目处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 * @date 2005-08-16
 */
public class GEdorAppItemBL implements BusinessService{
private static Logger logger = Logger.getLogger(GEdorAppItemBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	private MMap map = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private LPGrpEdorMainSet mLPGrpEdorMainSet = new LPGrpEdorMainSet();
	private LPGrpEdorItemSet mLPGrpEdorItemSet = new LPGrpEdorItemSet();
	private LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LMEdorItemSchema mLMEdorItemShema = new LMEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	// private String mDisplayType;
	private String mOtherNo;
	private String mOtherNoType;
	private String mManageCom;

	private static String mVAILIDATE_CALTYPE = "GValiDate";
	// 统一更新日期，时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public GEdorAppItemBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 *            传入的数据
	 * @param cOperate
	 *            数据操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		// *******************************************************************
		// mOperate 操作字符串说明：
		// GRPEDORITEM - 添加团体保全项目
		// PEDORITEM - 团体保全项目下添加个人保全项目
		// *******************************************************************

		// 将外部传入的数据分解到本类的属性中，准备处理
		if (!getInputData()) {
			return false;
		}

		logger.debug("---getInputData---");

		// 根据业务逻辑对数据进行处理
		if (!dealData()) {
			return false;
		}

		logger.debug("---dealData---");

		if (!prepareOutputData()) {
			return false;
		}

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorAppItemBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		mResult.clear();
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		// 全局变量
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		// 团体批改项目表
		mLPGrpEdorItemSet = (LPGrpEdorItemSet) mInputData
				.getObjectByObjectName("LPGrpEdorItemSet", 0);

		// 团体保全项目下添加个人保全项目
		mLPEdorItemSet = (LPEdorItemSet) mInputData.getObjectByObjectName(
				"LPEdorItemSet", 0);

		// 团体保全项目下添加险种
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mLPGrpPolSet = (LPGrpPolSet) mInputData.getObjectByObjectName(
				"LPGrpPolSet", 0);

		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {
		if (mOperate.equalsIgnoreCase("GrpEdorItem")) // 添加团单保全项目
		{
			if (mLPGrpEdorItemSet == null || mLPGrpEdorItemSet.size() <= 0) {
				CError.buildErr(this, "无法获取团单保全项目信息。添加团单保全项目失败！");
				return false;
			} else {
				// 根据不同项目挂起团体理赔或续期
				BqContHangUpBL tContHangUpBL = new BqContHangUpBL(mGlobalInput);
				if (!tContHangUpBL.HangUpGrp_RN_Claim(mLPGrpEdorItemSet.get(1)
						.getGrpContNo(),
						mLPGrpEdorItemSet.get(1).getEdorType(), "1")) {
					CError.buildErr(this, "团体保单挂起失败! ");
					return false;
				} else {
					MMap tMap = tContHangUpBL.getMMap();
					map.add(tMap);
				}

				// 校验添加权限
				for (int i = 1; i <= mLPGrpEdorItemSet.size(); i++) {
					LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
					tLPGrpEdorItemSchema = mLPGrpEdorItemSet.get(i);
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("EdorType",
							tLPGrpEdorItemSchema.getEdorType());
					tTransferData.setNameAndValue("EdorAppDate",
							tLPGrpEdorItemSchema.getEdorAppDate());
					tTransferData.setNameAndValue("EdorValiDate",
							tLPGrpEdorItemSchema.getEdorValiDate());
					tTransferData.setNameAndValue("EdorAcceptNo",
							tLPGrpEdorItemSchema.getEdorAcceptNo());
					VData tVData = new VData();
					tVData.add(mGlobalInput);
					tVData.add(tTransferData);
					tTransferData = null;
					PEdorPopedomCheckBL tPEdorPopedomCheckBL = new PEdorPopedomCheckBL();
					if (!tPEdorPopedomCheckBL.submitData(tVData, "GApply")) {
						mErrors.copyAllErrors(tPEdorPopedomCheckBL.mErrors);
						return false;
					}
					tPEdorPopedomCheckBL = null;
					tVData = null;
				}

				// 执行校验规则
				AddGEdorItemCheckBL tAddGEdorItemCheckBL = new AddGEdorItemCheckBL();
				if (!tAddGEdorItemCheckBL.canAddGEdorItem(mLPGrpEdorItemSet)) {
					mErrors.copyAllErrors(tAddGEdorItemCheckBL.getErrors());
					return false;
				}
				tAddGEdorItemCheckBL = null;
				// 添加
				if (!addGrpEdorItem())
					return false;

			}
		} else if (mOperate.equalsIgnoreCase("PEdorItem")) // 团体保全项目下添加个人保全项目
		{
			if (mLPEdorItemSet == null || mLPEdorItemSet.size() <= 0) {
				CError.buildErr(this, "无法获取分单保全项目信息。添加个人保全项目失败！");
				return false;
			} else {
				// 执行校验规则
				AddGEdorItemCheckBL tAddGEdorItemCheckBL = new AddGEdorItemCheckBL();
				if (!tAddGEdorItemCheckBL.canAddPEdorItem(mLPEdorItemSet)) {
					mErrors.copyAllErrors(tAddGEdorItemCheckBL.getErrors());
					return false;
				}
				tAddGEdorItemCheckBL = null;
				// 添加
				if (!addPEdorItem())
					return false;
			}
		} else if (mOperate.equalsIgnoreCase("G&EdorRisk")) // 添加团体保全险种
		{
			if (mLPGrpPolSet == null || mLPGrpPolSet.size() <= 0) {
				CError.buildErr(this, "无法获取团体保全险种信息。添加险种失败！");
				return false;
			} else {
				// 校验
				AddGEdorItemCheckBL tAddGEdorItemCheckBL = new AddGEdorItemCheckBL();
				if (!tAddGEdorItemCheckBL.canAddGEdorRisk(mLPGrpPolSet)) {
					mErrors.copyAllErrors(tAddGEdorItemCheckBL.getErrors());
					return false;
				}
				tAddGEdorItemCheckBL = null;
				// 添加
				for (int i = 1; i <= mLPGrpPolSet.size(); i++) {
					LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
					tLPGrpPolSchema = mLPGrpPolSet.get(i);
					if (!addGrpEdorRisk(tLPGrpPolSchema))
						return false;
					tLPGrpPolSchema = null;
				}
			} // mLPGrpPolSet != null
		}

		return true;
	}

	/**
	 * 添加团体保全项目
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean addGrpEdorItem() {
		if (!getOtherNo()) {
			return false;
		}

		LPGrpEdorMainSet tLPGrpEdorMainSet = new LPGrpEdorMainSet();
		for (int i = 1; i <= mLPGrpEdorItemSet.size(); i++) {
			// mDisplayType = mLPGrpEdorItemSet.get(i).getDisplayType(); //显示级别
			if (mOtherNoType.equals("4")) {
				// 个人保单号
				mLPGrpEdorItemSet.get(i).setGrpContNo(mOtherNo);
			}

			// 计算保全生效日期
			String sEdorValiDate = calValiDate(mLPGrpEdorItemSet.get(i));
			if (sEdorValiDate == null) {
				return false;
			}
			mLPGrpEdorItemSet.get(i).setEdorValiDate(sEdorValiDate);

			// 处理保全批改表
			tLPGrpEdorMainSet.clear();
			LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
			tLPGrpEdorMainDB.setEdorAcceptNo(mLPGrpEdorItemSet.get(i)
					.getEdorAcceptNo());
			tLPGrpEdorMainDB.setGrpContNo(mLPGrpEdorItemSet.get(i)
					.getGrpContNo());
			tLPGrpEdorMainSet.set(tLPGrpEdorMainDB.query());
			if (tLPGrpEdorMainSet.mErrors.needDealError()) {
				CError.buildErr(this, "查询批改主表失败！");
				return false;
			}

			if (tLPGrpEdorMainSet != null && tLPGrpEdorMainSet.size() > 0) {
				mLPGrpEdorItemSet.get(i).setEdorNo(
						tLPGrpEdorMainSet.get(1).getEdorNo());
				mLPGrpEdorItemSet.get(i).setEdorAppNo(
						tLPGrpEdorMainSet.get(1).getEdorAppNo());
			} else // 如果没有保全申请批单，生成一条
			{
				LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
				tLPGrpEdorMainSchema.setEdorAcceptNo(mLPGrpEdorItemSet.get(i)
						.getEdorAcceptNo());

				String strLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
				String strEdorAppNo = PubFun1
						.CreateMaxNo("EdorAppNo", strLimit);
				if (StrTool.compareString(strEdorAppNo, "")) {
					CError.buildErr(this, "生成保全申请批单号错误！");
					return false;
				} else {
					tLPGrpEdorMainSchema.setEdorAppNo(strEdorAppNo);
					tLPGrpEdorMainSchema.setEdorNo(strEdorAppNo);
					mLPGrpEdorItemSet.get(i).setEdorNo(strEdorAppNo);
					mLPGrpEdorItemSet.get(i).setEdorAppNo(strEdorAppNo);
				}

				tLPGrpEdorMainSchema.setGrpContNo(mLPGrpEdorItemSet.get(i)
						.getGrpContNo());
				tLPGrpEdorMainSchema.setEdorAppDate(mLPGrpEdorItemSet.get(i)
						.getEdorAppDate());
				tLPGrpEdorMainSchema.setEdorValiDate(mLPGrpEdorItemSet.get(i)
						.getEdorValiDate());
				tLPGrpEdorMainSchema.setEdorState("3");
				tLPGrpEdorMainSchema.setUWState("0");
				tLPGrpEdorMainSchema.setOperator(mGlobalInput.Operator);
//				tLPGrpEdorMainSchema.setManageCom(mGlobalInput.ManageCom);
				tLPGrpEdorMainSchema.setManageCom(this.mManageCom);
				tLPGrpEdorMainSchema.setMakeDate(mCurrentDate);
				tLPGrpEdorMainSchema.setMakeTime(mCurrentTime);
				tLPGrpEdorMainSchema.setModifyDate(mCurrentDate);
				tLPGrpEdorMainSchema.setModifyTime(mCurrentTime);

				mLPGrpEdorMainSet.add(tLPGrpEdorMainSchema);
			}
			LMEdorItemDB tLMEdorItemDB = new LMEdorItemDB();
			tLMEdorItemDB.setEdorCode(mLPGrpEdorItemSet.get(i).getEdorType());
			tLMEdorItemDB.setAppObj("G");
			LMEdorItemSet tLMEdorItemSet = tLMEdorItemDB.query();
			if (tLMEdorItemSet == null || tLMEdorItemSet.size() <= 0) {
				CError.buildErr(this, "查询保全定义表失败！");
				return false;
			}
			LMEdorItemSchema mLMEdorItemShema = tLMEdorItemSet.get(1);

			if ("0".equals(mLMEdorItemShema.getNeedDetail())) {
				mLPGrpEdorItemSet.get(i).setEdorState("1"); // 不需要录入明细
			} else {
				mLPGrpEdorItemSet.get(i).setEdorState("3"); // 申请待录入状态
			}

//			mLPGrpEdorItemSet.get(i).setManageCom(mGlobalInput.ManageCom);
			mLPGrpEdorItemSet.get(i).setManageCom(this.mManageCom);
			mLPGrpEdorItemSet.get(i).setOperator(mGlobalInput.Operator);
			mLPGrpEdorItemSet.get(i).setMakeDate(mCurrentDate);
			mLPGrpEdorItemSet.get(i).setMakeTime(mCurrentTime);
			mLPGrpEdorItemSet.get(i).setModifyDate(mCurrentDate);
			mLPGrpEdorItemSet.get(i).setModifyTime(mCurrentTime);
		}

		if (mLPGrpEdorMainSet != null && mLPGrpEdorMainSet.size() > 0) {
			map.put(mLPGrpEdorMainSet, "INSERT");
		}

		map.put(mLPGrpEdorItemSet, "INSERT");

		return true;
	}

	/**
	 * 团体保全项目下添加个人保全项目
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean addPEdorItem() {
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorAcceptNo(mLPEdorItemSet.get(1)
				.getEdorAcceptNo());
		tLPGrpEdorItemDB.setEdorNo(mLPEdorItemSet.get(1).getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPEdorItemSet.get(1).getEdorType());
		tLPGrpEdorItemDB.setGrpContNo(mLPEdorItemSet.get(1).getGrpContNo());
		LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "团体保全项目查询失败!");
			return false;
		}
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1) {
			CError.buildErr(this, "未查到团体保全项目!");
			return false;
		}
		if ("2".equals(tLPGrpEdorItemSet.get(1).getEdorState())) {
			CError.buildErr(this, "此次保全已申请确认，不能再添加个人保全操作!");
			return false;
		}

		LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
		for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
			// 校验个人单保全状态被挂起，则不允许添加该个人,添加校验规则控制,程序不用实现

			// 挂起该个人单
			BqContHangUpBL tBqContHangUpBL = new BqContHangUpBL(mGlobalInput);
			if (!tBqContHangUpBL.hangUpCont(mLPEdorItemSet.get(i).getContNo(),
					"1", mLPEdorItemSet.get(i).getEdorAcceptNo())) {
				CError.buildErr(this, "被保人挂起失败! 分单号："
						+ mLPEdorItemSet.get(i).getContNo());
				return false;
			} else {
				MMap tMap = tBqContHangUpBL.getMMap();
				map.add(tMap);
			}

			mLPEdorItemSet.get(i).setDisplayType(
					tLPGrpEdorItemSet.get(1).getDisplayType());
			mLPEdorItemSet.get(i).setEdorAppDate(
					tLPGrpEdorItemSet.get(1).getEdorAppDate());
			mLPEdorItemSet.get(i).setEdorValiDate(
					tLPGrpEdorItemSet.get(1).getEdorValiDate());
			mLPEdorItemSet.get(i).setEdorState("3");
			mLPEdorItemSet.get(i).setUWFlag("0");
			mLPEdorItemSet.get(i).setAppReason(
					tLPGrpEdorItemSet.get(1).getAppReason());
			mLPEdorItemSet.get(i).setEdorReasonCode(
					tLPGrpEdorItemSet.get(1).getEdorReasonCode());
			mLPEdorItemSet.get(i).setEdorReason(
					tLPGrpEdorItemSet.get(1).getEdorReason());
			mLPEdorItemSet.get(i).setManageCom(mGlobalInput.ManageCom);
			mLPEdorItemSet.get(i).setOperator(mGlobalInput.Operator);
			mLPEdorItemSet.get(i).setMakeDate(mCurrentDate);
			mLPEdorItemSet.get(i).setMakeTime(mCurrentTime);
			mLPEdorItemSet.get(i).setModifyDate(mCurrentDate);
			mLPEdorItemSet.get(i).setModifyTime(mCurrentTime);

			// 判断是否需要添加新的个人批单
			tLPEdorMainSet.clear();
			LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
			tLPEdorMainDB.setEdorAcceptNo(mLPEdorItemSet.get(i)
					.getEdorAcceptNo());
			tLPEdorMainDB.setEdorNo(mLPEdorItemSet.get(i).getEdorNo());
			tLPEdorMainDB.setContNo(mLPEdorItemSet.get(i).getContNo());
			tLPEdorMainSet = tLPEdorMainDB.query();
			if (tLPEdorMainDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询批改主表失败！");
				return false;
			}
			if (tLPEdorMainSet != null && tLPEdorMainSet.size() > 0) {
				// DoNothing
			} else // 如果没有申请批单，生成一条
			{
				LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
				Reflections tRef = new Reflections();
				tRef.transFields(tLPEdorMainSchema, mLPEdorItemSet.get(i));
				mLPEdorMainSet.add(tLPEdorMainSchema);
			}
		}

		map.put(mLPEdorItemSet, "INSERT");

		// 更新团体保全项目表的保全处理状态
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql("update lpgrpedoritem set edorstate = '3' where edoracceptno = '"
				+ "?edoracceptno?"
				+ "' and grpcontno = '"
				+ "?grpcontno?" + "'");
		sbv1.put("edoracceptno", tLPGrpEdorItemSet.get(1).getEdorAcceptNo());
		sbv1.put("grpcontno", tLPGrpEdorItemSet.get(1).getGrpContNo());
		map.put(sbv1,"UPDATE");
		
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql("update lpgrpedormain set edorstate = '3' where edoracceptno = '"
				+ "?edoracceptno?"
				+ "' and grpcontno = '"
				+ "?grpcontno?" + "'");
		sbv2.put("edoracceptno", tLPGrpEdorItemSet.get(1).getEdorAcceptNo());
		sbv2.put("grpcontno", tLPGrpEdorItemSet.get(1).getGrpContNo());
		map.put(sbv2,"UPDATE");

		if (mLPEdorMainSet != null && mLPEdorMainSet.size() > 0) {
			map.put(mLPEdorMainSet, "INSERT");
		}

		return true;
	}

	/**
	 * 根据保全申请日期计算保全生效日期
	 * 
	 * @param: 无
	 * @return: void
	 */
	private String calValiDate(LPGrpEdorItemSchema tLPGrpEdorItemSchema) {
		// ********************************************
		// 生效日期的计算代码将描述在[LMEdorCal]表中
		// ********************************************
		String sEdorValiDate = ""; // 生效日期
		String sCalCode = ""; // 计算代码

		LMEdorCalSet tLMEdorCalSet;
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();

		tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode("000000");
		tLMEdorCalDB.setDutyCode("000000");
		tLMEdorCalDB.setEdorType(tLPGrpEdorItemSchema.getEdorType());
		tLMEdorCalDB.setCalType(mVAILIDATE_CALTYPE);
		tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
			mErrors.addOneError(new CError("查询保全生效日期计算代码失败!"));
			return null;
		}
		if (tLMEdorCalSet != null && tLMEdorCalSet.size() == 1) {
			sCalCode = tLMEdorCalSet.get(1).getCalCode();
		}
		logger.debug("===sCalCode2===" + sCalCode);
		if (sCalCode == null || sCalCode.trim().equals("")) {
			// 如果该保全项目没有特殊描述保全生效日期计算代码，则取通用计算代码
			tLMEdorCalDB = new LMEdorCalDB();
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalDB.setDutyCode("000000");
			tLMEdorCalDB.setEdorType("00");
			tLMEdorCalDB.setCalType(mVAILIDATE_CALTYPE);
			if (!tLMEdorCalDB.getInfo()) {
				mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
				mErrors.addOneError(new CError("查询保全生效日期计算代码失败!"));
				return null;
			}
			sCalCode = tLMEdorCalDB.getCalCode();
			logger.debug("===sCalCode3===" + sCalCode);
		}
		int iInterval = getInterval(tLPGrpEdorItemSchema.getGrpContNo(),
				tLPGrpEdorItemSchema.getEdorAppDate());
		if (iInterval == -1) {
			return null;
		}
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(sCalCode);
		// 增加基本要素
		tCalculator.addBasicFactor("EdorAppDate", tLPGrpEdorItemSchema
				.getEdorAppDate());// 申请日期
		tCalculator.addBasicFactor("GrpContNo", tLPGrpEdorItemSchema
				.getGrpContNo());// 团体保单号
		tCalculator.addBasicFactor("Interval", String.valueOf(iInterval)); // 保单年度
		// 进行计算
		sEdorValiDate = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "保全生效日期计算失败!");
			return null;
		}
		if (sEdorValiDate == null || sEdorValiDate.equals("")) {
			CError.buildErr(this, "保全生效日期计算为空!");
			return null;
		}
		if (sEdorValiDate.length() > 10) {
			sEdorValiDate = sEdorValiDate.substring(0, 10);
		}
		return sEdorValiDate;
	}

	/**
	 * 查询保全受理信息
	 * 
	 * @return: boolean
	 */
	private boolean getOtherNo() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB
				.setEdorAcceptNo(mLPGrpEdorItemSet.get(1).getEdorAcceptNo());
		if (!tLPEdorAppDB.getInfo()) {
			// @@错误处理
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全受理查询失败!";
			mErrors.addOneError(tError);
			return false;
		}
		mOtherNo = tLPEdorAppDB.getOtherNo();
		mOtherNoType = tLPEdorAppDB.getOtherNoType();
		mManageCom = tLPEdorAppDB.getManageCom();

		return true;
	}

	// <!-- XinYQ added on 2006-04-19 : 保全项目公用险种操作 : BGN -->
	// ==========================================================================

	/**
	 * 添加团单保全险种
	 * 
	 * @param LPGrpPolSchema
	 * @return boolean
	 */
	private boolean addGrpEdorRisk(LPGrpPolSchema tLPGrpPolSchema) {
		// logger.debug("\t@> GEdorAppItemBL.addGrpEdorRisk() 开始");

		String sEdorAcceptNo = (String) mTransferData
				.getValueByName("EdorAcceptNo");
		String sEdorNo = tLPGrpPolSchema.getEdorNo();
		String sEdorType = tLPGrpPolSchema.getEdorType();
		String sGrpContNo = tLPGrpPolSchema.getGrpContNo();
		String sGrpPolNo = tLPGrpPolSchema.getGrpPolNo();

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

		// 查询获取团单保全信息
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorAcceptNo(sEdorAcceptNo);
		tLPGrpEdorItemDB.setEdorNo(sEdorNo);
		tLPGrpEdorItemDB.setEdorType(sEdorType);
		tLPGrpEdorItemDB.setGrpContNo(sGrpContNo);
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		try {
			tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询团单保全项目明细表出现异常！");
			return false;
		}
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() <= 0) {
			CError.buildErr(this, "在团单保全项目明细表中找不到待操作保单的纪录！");
			return false;
		} else {
			tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);
		}
		// 垃圾处理
		tLPGrpEdorItemSet = null;
		tLPGrpEdorItemDB = null;

		// ----------------------------------------------------------------------

		// 查询对应个单的 DisplayFlag
		// ExeSQL tExeSQL = new ExeSQL();
		// String QuerySQL = "select DisplayFlag "
		// + "from LMEdorItem "
		// + "where 1 = 1 "
		// + "and EdorCode = '" + sEdorType + "' "
		// + "and AppObj = 'I'";
		// //logger.debug(QuerySQL);
		// String sDisplayFlag = tExeSQL.getOneValue(QuerySQL);
		// tExeSQL = null;

		// ----------------------------------------------------------------------

		// 循环查询 LCPol 以检索可以添加的被保人
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setGrpContNo(sGrpContNo);
		tLCPolDB.setGrpPolNo(sGrpPolNo);
		tLCPolDB.setAppFlag("1");
		LCPolSet tLCPolSet = new LCPolSet();
		try {
			tLCPolSet = tLCPolDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询新契约个单险种表出现异常！");
			return false;
		}
		if (tLCPolSet == null || tLCPolSet.size() <= 0) {
			CError.buildErr(this, "在新契约个单险种表中找不到该保单！");
			return false;
		} else {
			// 准备数据容器
			if (mLPEdorItemSet == null) {
				mLPEdorItemSet = new LPEdorItemSet();
			} else {
				mLPEdorItemSet.clear();
			}
			// 统计变更的人数
			// int nGrpContCount = tLCPolSet.size();
			// int nCanNotChange = 0;
			// 循环添加数据
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema = tLCPolSet.get(i);
				LPEdorItemSchema tLPEdorItemSchemaNew = new LPEdorItemSchema();
				tLPEdorItemSchemaNew.setEdorAcceptNo(sEdorAcceptNo);
				tLPEdorItemSchemaNew.setEdorNo(sEdorNo);
				tLPEdorItemSchemaNew.setEdorType(sEdorType);
				tLPEdorItemSchemaNew.setEdorAppNo(sEdorNo); // 目前是一样的
				tLPEdorItemSchemaNew.setGrpContNo(tLCPolSchema.getGrpContNo());
				tLPEdorItemSchemaNew.setContNo(tLCPolSchema.getContNo());
				tLPEdorItemSchemaNew.setPolNo(tLCPolSchema.getPolNo());
				tLPEdorItemSchemaNew.setInsuredNo(tLCPolSchema.getInsuredNo());
				tLPEdorItemSchemaNew.setDisplayType(tLPGrpEdorItemSchema
						.getDisplayType());
				tLPEdorItemSchemaNew.setEdorAppDate(tLPGrpEdorItemSchema
						.getEdorAppDate());
				tLPEdorItemSchemaNew.setEdorValiDate(tLPGrpEdorItemSchema
						.getEdorValiDate());
				tLPEdorItemSchemaNew.setEdorState("3");
				tLPEdorItemSchemaNew.setUWFlag("0");
				tLPEdorItemSchemaNew.setAppReason(tLPGrpEdorItemSchema
						.getAppReason());
				tLPEdorItemSchemaNew.setEdorReasonCode(tLPGrpEdorItemSchema
						.getEdorReasonCode());
				tLPEdorItemSchemaNew.setEdorReason(tLPGrpEdorItemSchema
						.getEdorReason());
				tLPEdorItemSchemaNew.setManageCom(mGlobalInput.ManageCom);
				tLPEdorItemSchemaNew.setOperator(mGlobalInput.Operator);
				tLPEdorItemSchemaNew.setMakeDate(mCurrentDate);
				tLPEdorItemSchemaNew.setMakeTime(mCurrentTime);
				tLPEdorItemSchemaNew.setModifyDate(mCurrentDate);
				tLPEdorItemSchemaNew.setModifyTime(mCurrentTime);
				tLPEdorItemSchemaNew
						.setStandbyFlag3(tLCPolSchema.getGrpPolNo()); // 保存集体险种号
				// 根据 DisplayFlag 置不同的值
				// if (sDisplayFlag != null && !sDisplayFlag.trim().equals(""))
				// {
				// sDisplayFlag = sDisplayFlag.trim();
				// if (sDisplayFlag.equals("1"))
				// {
				// tLPEdorItemSchemaNew.setPolNo("000000");
				// tLPEdorItemSchemaNew.setInsuredNo("000000");
				// }
				// else if (sDisplayFlag.equals("2"))
				// {
				// tLPEdorItemSchemaNew.setPolNo("000000");
				// }
				// }
				mLPEdorItemSet.add(tLPEdorItemSchemaNew);
				tLPEdorItemSchemaNew = null;
				tLCPolSchema = null;
			}
			// 如果全部都不能参与变更, 返回
			// if (nGrpContCount == nCanNotChange)
			// {
			// CError.buildErr(this, "该团单险种下没有可以参与变更的个单！");
			// return false;
			// }
		}
		tLCPolDB = null;
		tLCPolSet = null;
		tLPGrpEdorItemSchema = null;

		// ----------------------------------------------------------------------

		// 生成保全个人批单并插入一条记录到 LPGrpPol
		if (!addPEdorItem()) {
			return false;
		} else {
			String DeleteSQL = new String("");
			DeleteSQL = "delete from LPGrpPol " + "where 1 = 1 "
					+ "and EdorNo = '" + "?sEdorNo?" + "' " + "and EdorType = '"
					+ "?sEdorType?" + "' " + "and GrpContNo = '" + "?sGrpContNo?"
					+ "' " + "and GrpPolNo = '" + "?sGrpPolNo?" + "'";
			// logger.debug(DeleteSQL);
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(DeleteSQL);
			sqlbv.put("sEdorNo", sEdorNo);
			sqlbv.put("sEdorType", sEdorType);
			sqlbv.put("sGrpContNo", sGrpContNo);
			sqlbv.put("sGrpPolNo", sGrpPolNo);
			map.put(sqlbv, "DELETE");

			// 插入一条记录到 LPGrpPol 用于区分可选险种和已选险种
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpContNo(sGrpContNo);
			tLCGrpPolDB.setGrpPolNo(sGrpPolNo);
			LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
			try {
				tLCGrpPolSet = tLCGrpPolDB.query();
			} catch (Exception ex) {
				CError.buildErr(this, "查询新契约团单险种表出现异常！");
				return false;
			}
			if (tLCGrpPolSet == null || tLCGrpPolSet.size() <= 0) {
				CError.buildErr(this, "在新契约团单险种表中找不到该保单！");
				return false;
			} else {
				Reflections tReflections = new Reflections();
				LPGrpPolSet tLPGrpPolSetNew = new LPGrpPolSet();
				for (int k = 1; k <= tLCGrpPolSet.size(); k++) {
					LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
					tLCGrpPolSchema = tLCGrpPolSet.get(k);
					LPGrpPolSchema tLPGrpPolSchemaNew = new LPGrpPolSchema();
					tReflections.transFields(tLPGrpPolSchemaNew,
							tLCGrpPolSchema);
					tLPGrpPolSchemaNew.setEdorNo(sEdorNo);
					tLPGrpPolSchemaNew.setEdorType(sEdorType);
					tLPGrpPolSchemaNew.setOperator(mGlobalInput.Operator);
					tLPGrpPolSchemaNew.setModifyDate(mCurrentDate);
					tLPGrpPolSchemaNew.setModifyTime(mCurrentTime);
					tLPGrpPolSetNew.add(tLPGrpPolSchemaNew);
					tLPGrpPolSchemaNew = null;
				}
				map.put(tLPGrpPolSetNew, "INSERT");
				tLPGrpPolSetNew = null;
				tReflections = null;
			}
		}

		// logger.debug("\t@> GEdorAppItemBL.addGrpEdorRisk() 结束");
		return true;
	} // function addGrpEdorRisk end

	// ==========================================================================
	// <!-- XinYQ added on 2006-04-19 : 保全项目公用险种操作 : END -->

	private int getInterval(String sGrpContNo, String sEdorAppDate) {
		int iInterval = 0;
		// 根据PolNo查询出保单生效日期
		String sql = " select cvalidate from lcgrpcont "
				+ " where grpcontno = '" + "?sGrpContNo?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sGrpContNo", sGrpContNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sCValiDate = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保单生效日期查询失败!");
			return -1;
		}
		if (sCValiDate == null || sCValiDate.equals("")) {
			CError.buildErr(this, "保单生效日期为空!");
			return -1;
		}
		if (sCValiDate.length() > 10) {
			sCValiDate = sCValiDate.substring(0, 10);
		}

		// 根据保单生效日期计算保单年度
		String calYearsType = "0";
		if (calYearsType.equals("0")) // 0-舍弃法(向下撇)
		{
			iInterval = PubFun.calInterval(sCValiDate, sEdorAppDate, "Y");
		} else if (calYearsType.equals("1")) // 1-约进法(向上撇)
		{
			iInterval = PubFun.calInterval2(sCValiDate, sEdorAppDate, "Y");
		}
		if (iInterval < 0) {
			CError.buildErr(this, "保单年度不符合实际!");
			return -1;
		}

		return iInterval;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorAppItemBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */

	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回本类运行时产生的错误信息
	 * 
	 * @param null
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "XinYQ";
		tG.ManageCom = "86";

		LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		// tLPEdorAppSchema.setEdorAcceptNo("6120050816000015");

		// LPGrpEdorItemSchema tLPEdorItemSchema = new LPGrpEdorItemSchema();
		// tLPEdorItemSchema.setEdorAcceptNo("6120050816000015");
		// tLPEdorItemSchema.setEdorType("BB");
		// tLPEdorItemSchema.setGrpContNo("240110000000260");
		// tLPEdorItemSchema.setDisplayType("1");
		// tLPEdorItemSchema.setEdorAppDate("2005-08-10");
		// LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		// tLPGrpEdorItemSet.add(tLPEdorItemSchema);

		// TransferData
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorAcceptNo", "6120060419000001");
		tTransferData.setNameAndValue("EdorNo", "6020060419000006");
		tTransferData.setNameAndValue("EdorType", "FM");
		tTransferData.setNameAndValue("GrpContNo", "240210000000008");
		tTransferData.setNameAndValue("GrpPolNo", "220210000000014");

		VData tVData = new VData();
		tVData.add(tG);
		// tVData.add(tLPGrpEdorItemSet);
		tVData.addElement(tTransferData);

		GEdorAppItemBL tGEdorAppItemBL = new GEdorAppItemBL();
		// if (!tGEdorAppItemBL.submitData(tVData, "INSERT||EDORITEM"))
		if (!tGEdorAppItemBL.submitData(tVData, "addGEdorRisk")) {
			logger.debug(tGEdorAppItemBL.mErrors.getError(0).errorMessage);
		}

	}
}
