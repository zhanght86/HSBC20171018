/*
 * @(#)PEdorValidBL.java    2005-06-10
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved. All right reserved.
 *
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全-保全生效处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author：zhangtao
 * @version：1.0
 * @CreateDate：2005-06-10
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCSpecDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPSpecDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPSpecSchema;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPSpecSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PEdorValidBL {
private static Logger logger = Logger.getLogger(PEdorValidBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 传输数据 */
	private TransferData mTransferData = new TransferData();
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();

	private String mEdorAcceptNo = "";
	private String mEdorValiDate = "";
	private String mEdorConfNo = "";
	
	private static String mVAILIDATE_CALTYPE = "ValiDate";

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();

	public PEdorValidBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}
		// logger.debug("after getInputData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
		// logger.debug("after dealData...");

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		// logger.debug("after prepareOutputData...");

		// 数据提交
//		PubSubmit tPubSubmit = new PubSubmit();
//		if (!tPubSubmit.submitData(mInputData, "")) {
//			// @@错误处理
//			mErrors.copyAllErrors(tPubSubmit.mErrors);
//			CError.buildErr(this, "数据提交失败");
//			return false;
//		}
//		mInputData = null;
		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		// 校验保全受理是否已经保全确认
		if (!checkLPEdorApp()) {
			return false;
		}

		// 查询保全受理下所有保全项目
		if (!getLPEdorItem()) {
			return false;
		}

		// 执行各个保全项目确认处理
		if (!calEdorTypeConfirm()) {
			return false;
		}

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
		if (mEdorAcceptNo == null || mEdorAcceptNo.trim().equals("")) {
			CError tError = new CError();
			tError.errorMessage = "前台传输数据EdorAcceptNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLJAPayPersonSet = (LJAPayPersonSet) mInputData.getObjectByObjectName("LJAPayPersonSet", 0);
		this.mEdorConfNo = (String) mTransferData.getValueByName("EdorConfNo");
		
		if (mEdorConfNo == null || mEdorConfNo.trim().equals("")) {
			CError tError = new CError();
			tError.errorMessage = "批单号生成失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			CError tError = new CError();
			tError.errorMessage = "准备数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 校验保全受理是否已经保全确认
	 * 
	 * @return: boolean
	 */
	private boolean checkLPEdorApp() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
		if (!tLPEdorAppDB.getInfo()) {
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全受理信息查询失败!" + "保全受理号：" + mEdorAcceptNo;
			mErrors.addOneError(tError);
			return false;
		}
		if (tLPEdorAppDB.getEdorState().equals("0")) {
			CError tError = new CError();
			tError.errorMessage = "保全受理已经确认生效!" + "保全受理号：" + mEdorAcceptNo;
			mErrors.addOneError(tError);
			return false;
		}
		// comment by wk
//		if (!tLPEdorAppDB.getEdorState().equals("6")) {
//			CError tError = new CError();
//			tError.errorMessage = "保全受理尚未确认!" + "保全受理号：" + mEdorAcceptNo;
//			mErrors.addOneError(tError);
//			return false;
//		}
		return true;
	}

	/**
	 * 查询保全项目
	 */
	private boolean getLPEdorItem() {
		// ===add===zhangtao====2006-08-24====生产库发现无条件查询导致内存溢出，故在此先校验查询条件=====BGN=======
		if (mEdorAcceptNo == null || mEdorAcceptNo.equals("")) {
			CError.buildErr(this, "保全项目查询条件为空!");
			return false;
		}
		// ===add===zhangtao====2006-08-24====生产库发现无条件查询导致内存溢出，故在此先校验查询条件=====END=======
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
		mLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "查询保全项目失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mLPEdorItemSet == null || mLPEdorItemSet.size() < 1) {
			CError.buildErr(this, "保全申请下没有保全项目变更!" + "保全申请号：" + mEdorAcceptNo);
			return false;
		}
		return true;
	}
	
	/**
	 * 保全生效日规则  无补退费 当天生效  退费 当天生效  收费 次日生效
	 * @param: 无
	 * @return: void
	 */
	private String calValiDate(LPEdorItemSchema    tLPEdorItemSchema) {
		// ********************************************
		// 生效日期的计算代码将描述在[LMEdorCal]表中
		// ********************************************
		String sEdorValiDate = ""; // 生效日期
		String sCalCode = ""; // 计算代码
		String sRiskCode = "000000";
		String sPolNo = tLPEdorItemSchema.getPolNo();
		if (sPolNo.equals("000000")) {
			sRiskCode = "000000";
		} else {
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLPEdorItemSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.copyAllErrors(tLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询险种保单信息失败!"));
				return null;
			}
			sRiskCode = tLCPolDB.getRiskCode();
		}
		LMEdorCalSet tLMEdorCalSet;
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode("000000");
		tLMEdorCalDB.setDutyCode("000000");
		tLMEdorCalDB.setEdorType(tLPEdorItemSchema.getEdorType());
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
		logger.debug("===sCalCode1===" + sCalCode);
		if (sCalCode == null || sCalCode.trim().equals("")) {
			// 如果该险种没有特殊描述保全生效日期计算代码，则取通用计算代码
			tLMEdorCalDB = new LMEdorCalDB();
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalDB.setDutyCode("000000");
			tLMEdorCalDB.setEdorType(tLPEdorItemSchema.getEdorType());
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
		}
		  
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(sCalCode);
		// 增加基本要素
		tCalculator.addBasicFactor("EdorAppDate", tLPEdorItemSchema
				.getEdorAppDate());// 申请日期
		tCalculator.addBasicFactor("PolNo", tLPEdorItemSchema.getPolNo()); // 险种号码
		tCalculator.addBasicFactor("ContNo", tLPEdorItemSchema.getContNo()); // 保单号码 
		tCalculator.addBasicFactor("EdorAcceptNo", tLPEdorItemSchema.getEdorAcceptNo());//保全受理号
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
	 * 执行各个保全项目确认生效处理
	 */
	private boolean calEdorTypeConfirm() {
		boolean appValid = true;
		String sEdorAcceptNo = mLPEdorItemSet.get(1).getEdorAcceptNo();
		String sEdorState;
		String tEdorType;
//		String sEdorValiDate;
		mEdorValiDate="";
		for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
			sEdorState = mLPEdorItemSet.get(i).getEdorState();
			tEdorType = mLPEdorItemSet.get(i).getEdorType();
//			mEdorValiDate = mLPEdorItemSet.get(i).getEdorValiDate();
			//计算生效日期
			if(mEdorValiDate.equals(""))
			{
				mEdorValiDate = calValiDate(mLPEdorItemSet.get(i));
			}
			if(mEdorValiDate==null||mEdorValiDate.equals(""))
			{
				CError.buildErr(this, "保全项目" + tEdorType + "生效日期计算失败!");
				return false;
			}
			mLPEdorItemSet.get(i).setEdorValiDate(mEdorValiDate);
			// 判断保全项目批改状态，如果已经确认生效，就不能再生效替换了
			if (sEdorState.equals("0")) {
				logger.debug("== 保全项目" + tEdorType + "已经确认生效== "
						+ "保全受理号：" + sEdorAcceptNo + "==");
				continue;
			}

			/*
			 * //如果到生效日期，则生效替换 if (PubFun.calInterval(sEdorValiDate,
			 * mCurrentDate, "D") >= 0) {
			 */
			// 执行该保全项目确认处理
			logger.debug("== befor PEdor" + tEdorType + "ConfirmBL ==");
			try {
				Class tClass = Class.forName("com.sinosoft.lis.bq.PEdor"
						+ tEdorType + "ConfirmBL");
				EdorConfirm tEdorConfirm = (EdorConfirm) tClass.newInstance();
				VData tVData = new VData();

				tVData.add(mGlobalInput);
				tVData.add(mLPEdorItemSet.get(i));
				tVData.add(tLJAPayPersonSet);

				if (!tEdorConfirm.submitData(tVData, "CONFIRM||" + tEdorType)) {
					CError.buildErr(this, tEdorConfirm.mErrors.getFirstError());
					return false;
				} else {
					logger.debug("== after PEdor" + tEdorType
							+ "ConfirmBL ==");
					VData rVData = tEdorConfirm.getResult();
					MMap tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
					if (tMap == null) {
						CError.buildErr(this, "获取保全项目" + tEdorType
								+ "的保全确认生效结果时失败!");
						return false;

					} else {
						map.add(tMap);
					}
				}

			} catch (ClassNotFoundException ex) {
				logger.debug("====ClassNotFoundException===");
			} catch (Exception ex) {
				CError.buildErr(this, "保全项目" + tEdorType + "确认生效失败!");
				return false;
			}

			// 将人工核保时录入的特约信息导入C表
			
			// project 由于MS有单独保全项目处理特约

			// 更新该保全项目批改状态为确认生效
			mLPEdorItemSet.get(i).setEdorState("0");
			//为了避免生效日在批单程序中被修改，这里重新设置一次
			mLPEdorItemSet.get(i).setEdorValiDate(mEdorValiDate);
			// mLPEdorItemSet.get(i).setOperator(mGlobalInput.Operator);
			mLPEdorItemSet.get(i).setModifyDate(mCurrentDate);
			mLPEdorItemSet.get(i).setModifyTime(mCurrentTime);
			map.put(mLPEdorItemSet.get(i).getSchema(), "UPDATE");
			/* } */
			/*
			 * else //未到生效日也要进行确认处理，解除保单挂起。 { //该保全项目尚未到生效日期
			 * logger.debug("== 该保全项目尚未到生效日期==" + tEdorType + "==");
			 * appValid = false; }
			 */
		}

		if (appValid) // 保全申请下所有保全项目都已生效
		{
			// 统一更新保全受理、申请批单的批改状态为确认生效[0]
			updAppState();

			// 保单解挂
			BqContHangUpBL tContHangUpBL = new BqContHangUpBL(mGlobalInput);
			if (!tContHangUpBL.hangUpEdorAccept(sEdorAcceptNo, "0")) {
				CError.buildErr(this, "保全保单解挂失败! ");
				return false;
			} else {
				MMap tMap = tContHangUpBL.getMMap();
				map.add(tMap); // del because DB hs not update zhangtao
				// 2005-08-02
			}

			// <!-- XinYQ added on 2006-01-10 : 准备抽检工作流必要数据 : BGN -->
			// 查询 LPEdorItem 以获取 EdorType EdorValiDate GetMoney
			String QueryLPEdorItemSQL = "select b.EdorType, " + "a.GetMoney, "
					+ "b.EdorValiDate, " + "a.Operator, "
					+ "a.ApproveOperator, " + "a.ApproveDate, "
					+ "a.EdorAppDate, " + "a.MakeDate " + "from LPEdorApp a, "
					+ "(select distinct EdorType as EdorType, "
					+ "min(EdorValiDate) as EdorValiDate " + "from LPEdorItem "
					+ "where EdorAcceptNo = '?mEdorAcceptNo?' "
					+ "group by EdorType, EdorValiDate) b "
					+ "where a.EdorAcceptNo = '?mEdorAcceptNo?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(QueryLPEdorItemSQL);
			sqlbv.put("mEdorAcceptNo", mEdorAcceptNo);
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			try {
				tSSRS = tExeSQL.execSQL(sqlbv);
			} catch (Exception ex) {
				logger.debug("\t@> PEdorValidBL.calEdorTypeConfirm() : 执行 SQL 查询异常");
				logger.debug("\t                                     : 错误原因 : "
								+ tExeSQL.mErrors.getFirstError());
				CError.buildErr(this, "查询保全项目信息失败！");
				return false;
			}
			// 字段映射
			// select * from LWFieldMap where ActivityID = '0000000080'
			// 准备更新 LWMission 的 SQL
			if (tSSRS.getMaxRow() > 0) {
				String UpdateLWMissionSQL = "update LWMission "
						+ "set ActivityStatus = '2', " + "MissionProp13  = '?MissionProp13?', "
						+ "MissionProp14  = '?MissionProp14?', "
						+ "MissionProp15  = '?MissionProp15?', "
						+ "MissionProp16  = '?MissionProp16?', "
						+ "MissionProp17  = '?MissionProp17?', "
						+ "MissionProp18  = '?MissionProp18?', "
						+ "MissionProp19  = '?MissionProp19?', "
						+ "MissionProp20  = '?MissionProp20?' "
						+ "where ActivityID = '0000000080' and MissionProp1 = '?mEdorAcceptNo?'";
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(UpdateLWMissionSQL);
				sqlbv2.put("MissionProp13", tSSRS.GetText(1, 1));
				sqlbv2.put("MissionProp14", tSSRS.GetText(1, 2));
				sqlbv2.put("MissionProp15", tSSRS.GetText(1, 3));
				sqlbv2.put("MissionProp16", tSSRS.GetText(1, 4));
				sqlbv2.put("MissionProp17", tSSRS.GetText(1, 5));
				sqlbv2.put("MissionProp18", tSSRS.GetText(1, 6));
				sqlbv2.put("MissionProp19", tSSRS.GetText(1, 7));
				sqlbv2.put("MissionProp20", tSSRS.GetText(1, 8));
				sqlbv2.put("mEdorAcceptNo", mEdorAcceptNo);
				map.put(sqlbv2, "UPDATE");
			}
			// 垃圾处理
			tSSRS = null;
			tExeSQL = null;
			// <!-- XinYQ added on 2006-01-10 : 准备抽检工作流必要数据 : END -->

		}
		return true;
	}

	/**
	 * 特约信息C、P互换 XinYQ rewrote on 2007-03-15
	 * 
	 * @param LPEdorItemSchema
	 */
	private void inserSpec(LPEdorItemSchema tLPEdorItemSchema) {
		if (tLPEdorItemSchema == null || tLPEdorItemSchema.getEdorNo() == null) {
			return;
		}

		String QuerySQL = new String("");
		String DeleteSQL = new String("");

		// ----------------------------------------------------------------------

		// 删除 LCSpec
		DeleteSQL = "delete from LCSpec " + "where 1 = 1 " + "and PolNo in "
				+ "(select PolNo " + "from LCPol " + "where 1 = 1 "
				+ "and ContNo = '?ContNo?')";
		// logger.debug(DeleteSQL);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(DeleteSQL);
		sqlbv.put("ContNo", tLPEdorItemSchema.getContNo());
		map.put(sqlbv, "DELETE");

		// ----------------------------------------------------------------------

		// 查询 LPSpec
		LPSpecDB tLPSpecDB = new LPSpecDB();
		tLPSpecDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
		tLPSpecDB.setEdorType(tLPEdorItemSchema.getEdorType());
		LPSpecSet tLPSpecSet = new LPSpecSet();
		try {
			tLPSpecSet = tLPSpecDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全特约批改表出现异常！");
			return;
		}

		// 拷贝 LPSpec 到 LCSpec
		if (tLPSpecSet != null && tLPSpecSet.size() > 0) {
			LCSpecSet tLCSpecSetNew = new LCSpecSet();
			for (int i = 1; i <= tLPSpecSet.size(); i++) {
				LPSpecSchema tLPSpecSchema = new LPSpecSchema();
				tLPSpecSchema = tLPSpecSet.get(i);
				LCSpecSchema tLCSpecSchemaNew = new LCSpecSchema();
				PubFun.copySchema(tLCSpecSchemaNew, tLPSpecSchema);
				tLCSpecSchemaNew.setOperator(mGlobalInput.Operator);
				tLCSpecSchemaNew.setModifyDate(mCurrentDate);
				tLCSpecSchemaNew.setModifyTime(mCurrentTime);
				tLCSpecSetNew.add(tLCSpecSchemaNew);
				tLCSpecSchemaNew = null;
				tLPSpecSchema = null;
			}
			map.put(tLCSpecSetNew, "INSERT");
			tLCSpecSetNew = null;
		}
		tLPSpecDB = null;
		tLPSpecSet = null;

		// ----------------------------------------------------------------------

		// //删除 LCUWMaster
		// DeleteSQL = "delete from LCUWMaster "
		// + "where 1 = 1 "
		// + "and ContNo = '" + tLPEdorItemSchema.getContNo() + "'";
		// //logger.debug(DeleteSQL);
		// map.put(DeleteSQL, "DELETE");
		//
		// //----------------------------------------------------------------------
		//
		// //查询 LPUWMaster
		// LPUWMasterDB tLPUWMasterDB = new LPUWMasterDB();
		// tLPUWMasterDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
		// tLPUWMasterDB.setEdorType(tLPEdorItemSchema.getEdorType());
		// //if (tLPEdorItemSchema.getPolNo() != null &&
		// !tLPEdorItemSchema.getPolNo().trim().equals("000000"))
		// //{
		// // tLPUWMasterDB.setPolNo(tLPEdorItemSchema.getPolNo());
		// //}
		// LPUWMasterSet tLPUWMasterSet = new LPUWMasterSet();
		// try
		// {
		// tLPUWMasterSet = tLPUWMasterDB.query();
		// }
		// catch (Exception ex)
		// {
		// CError.buildErr(this, "查询保全特约原因表出现异常！");
		// return;
		// }
		// //拷贝 LPUWMaster 到 LCUWMaster
		// if (tLPUWMasterSet != null && tLPUWMasterSet.size() > 0)
		// {
		// LCUWMasterSet tLCUWMasterSetNew = new LCUWMasterSet();
		// for (int i = 1; i <= tLPUWMasterSet.size(); i++)
		// {
		// LPUWMasterSchema tLPUWMasterSchema = new LPUWMasterSchema();
		// tLPUWMasterSchema = tLPUWMasterSet.get(i);
		// LCUWMasterSchema tLCUWMasterSchemaNew = new LCUWMasterSchema();
		// PubFun.copySchema(tLCUWMasterSchemaNew, tLPUWMasterSchema);
		// tLCUWMasterSchemaNew.setOperator(mGlobalInput.Operator);
		// tLCUWMasterSchemaNew.setModifyDate(mCurrentDate);
		// tLCUWMasterSchemaNew.setModifyTime(mCurrentTime);
		// tLCUWMasterSetNew.add(tLCUWMasterSchemaNew);
		// tLCUWMasterSchemaNew = null;
		// tLPUWMasterSchema = null;
		// }
		// map.put(tLCUWMasterSetNew, "INSERT");
		// tLCUWMasterSetNew = null;
		// }
		// tLPUWMasterDB = null;
		// tLPUWMasterSet = null;

		// ----------------------------------------------------------------------

		// 删除 LPSpec
		DeleteSQL = "delete from LPSpec " + "where 1 = 1 " + "and EdorNo = '?EdorNo?' " + "and EdorType = '?EdorType?'";
		// logger.debug(DeleteSQL);
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(DeleteSQL);
		sqlbv1.put("EdorNo", tLPEdorItemSchema.getEdorNo());
		sqlbv1.put("EdorType", tLPEdorItemSchema.getEdorType());
		map.put(sqlbv1, "DELETE");

		// ----------------------------------------------------------------------

		// 查询 LCSpec
		LCSpecDB tLCSpecDB = new LCSpecDB();
		tLCSpecDB.setContNo(tLPEdorItemSchema.getContNo());
		LCSpecSet tLCSpecSet = new LCSpecSet();
		try {
			tLCSpecSet = tLCSpecDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询新契约特约表出现异常！");
			return;
		}

		// 拷贝 LCSpec 到 LPSpec
		if (tLCSpecSet != null && tLCSpecSet.size() > 0) {
			LPSpecSet tLPSpecSetNew = new LPSpecSet();
			for (int i = 1; i <= tLCSpecSet.size(); i++) {
				LCSpecSchema tLCSpecSchema = new LCSpecSchema();
				tLCSpecSchema = tLCSpecSet.get(i);
				LPSpecSchema tLPSpecSchemaNew = new LPSpecSchema();
				PubFun.copySchema(tLPSpecSchemaNew, tLCSpecSchema);
				tLPSpecSchemaNew.setEdorNo(tLPEdorItemSchema.getEdorNo());
				tLPSpecSchemaNew.setEdorType(tLPEdorItemSchema.getEdorType());
				tLPSpecSchemaNew.setOperator(mGlobalInput.Operator);
				tLPSpecSchemaNew.setModifyDate(mCurrentDate);
				tLPSpecSchemaNew.setModifyTime(mCurrentTime);
				tLPSpecSetNew.add(tLPSpecSchemaNew);
				tLPSpecSchemaNew = null;
				tLCSpecSchema = null;
			}
			map.put(tLPSpecSetNew, "INSERT");
			tLPSpecSetNew = null;
		}
		tLCSpecDB = null;
		tLCSpecSet = null;

		// ----------------------------------------------------------------------

		// //删除 LPUWMaster
		// DeleteSQL = "delete from LPUWMaster "
		// + "where 1 = 1 "
		// + "and EdorNo = '" + tLPEdorItemSchema.getEdorNo() + "' "
		// + "and EdorType = '" + tLPEdorItemSchema.getEdorType() + "'";
		// //logger.debug(DeleteSQL);
		// map.put(DeleteSQL, "DELETE");
		//
		// //----------------------------------------------------------------------
		//
		// //查询 LCUWMaster
		// LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		// tLCUWMasterDB.setContNo(tLPEdorItemSchema.getContNo());
		// LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		// try
		// {
		// tLCUWMasterSet = tLCUWMasterDB.query();
		// }
		// catch (Exception ex)
		// {
		// CError.buildErr(this, "查询新契约特约原因表出现异常！");
		// return;
		// }
		// //拷贝 LCUWMaster 到 LPUWMaster
		// if (tLCUWMasterSet != null && tLCUWMasterSet.size() > 0)
		// {
		// LPUWMasterSet tLPUWMasterSetNew = new LPUWMasterSet();
		// for (int k = 1; k <= tLCUWMasterSet.size(); k++)
		// {
		// LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		// tLCUWMasterSchema = tLCUWMasterSet.get(k);
		// LPUWMasterSchema tLPUWMasterSchemaNew = new LPUWMasterSchema();
		// PubFun.copySchema(tLPUWMasterSchemaNew, tLCUWMasterSchema);
		// tLPUWMasterSchemaNew.setEdorNo(tLPEdorItemSchema.getEdorNo());
		// tLPUWMasterSchemaNew.setEdorType(tLPEdorItemSchema.getEdorType());
		// tLPUWMasterSchemaNew.setOperator(mGlobalInput.Operator);
		// tLPUWMasterSchemaNew.setModifyDate(mCurrentDate);
		// tLPUWMasterSchemaNew.setModifyTime(mCurrentTime);
		// tLPUWMasterSetNew.add(tLPUWMasterSchemaNew);
		// tLPUWMasterSchemaNew = null;
		// tLCUWMasterSchema = null;
		// }
		// map.put(tLPUWMasterSetNew, "INSERT");
		// tLPUWMasterSetNew = null;
		// }
		// tLCUWMasterDB = null;
		// tLCUWMasterSet = null;
	}

	/**
	 * 统一更新保全受理、申请批单的批改状态为确认生效[0]
	 */
	private void updAppState() {
		String wherePart = "where EdorAcceptNo='?mEdorAcceptNo?'";
		StringBuffer sbSQL = new StringBuffer();

		// 暂时对批单的生效不做细分处理，与保全申请一起统一生效

		// 保全批单
		sbSQL.setLength(0);
		sbSQL
				.append(" UPDATE LPEdorMain set EdorState = '0' , ")
				.append(" EdorValiDate='").append("?EdorValiDate?").append("',")
				 .append(" confoperator = '")
				 .append("?confoperator?")
				 .append("',")
				.append(" ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("', ")
				.append(" confdate = '").append("?mCurrentDate?").append(
						"', conftime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sbSQL.toString());
		sqlbv.put("EdorValiDate", this.mEdorValiDate);
		sqlbv.put("confoperator", mGlobalInput.Operator);
		sqlbv.put("mCurrentDate", mCurrentDate);
		sqlbv.put("mCurrentTime", mCurrentTime);
		sqlbv.put("mEdorAcceptNo", mEdorAcceptNo);
		map.put(sqlbv, "UPDATE");

		//保全确认时进行换号
//		String strLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);		
//		String strEdorConfNo = PubFun1.CreateMaxNo("EdorConfNo",
//				strLimit);
		// 保全申请
		sbSQL.setLength(0);
		sbSQL
				.append(" UPDATE LPEdorApp set EdorState = '0' ,")
				.append(" EdorConfNo = '")
				.append("?EdorConfNo?")
				 .append("', ")
				 .append(" confoperator = '")
				 .append("?confoperator?")
				 .append("',")
				.append(" ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("', ")
				.append(" confdate = '").append("?mCurrentDate?").append(
						"', conftime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sbSQL.toString());
		sqlbv2.put("EdorConfNo", this.mEdorConfNo);
		sqlbv2.put("confoperator", mGlobalInput.Operator);
		sqlbv2.put("mCurrentDate", mCurrentDate);
		sqlbv2.put("mCurrentTime", mCurrentTime);
		sqlbv2.put("mEdorAcceptNo", mEdorAcceptNo);
		map.put(sqlbv2, "UPDATE");
		
        //为实现财务归档，在对财务数据的OtherNo进行更新
		String[] asscociateTable_OtherNo = {"LJAGet"
				};
		String updateSql="";
		//for (int i = 0; i < asscociateTable_OtherNo.length; i++) {
			//updateSql = "update ?asscociateTable_OtherNo? set OtherNo = '?mEdorConfNo?' where OtherNo = '?mEdorAcceptNo?' and OtherNoType='10'";
			updateSql = "update LJAGet set OtherNo = '?mEdorConfNo?' where OtherNo = '?mEdorAcceptNo?' and OtherNoType='10'";
			
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(updateSql);
			//sqlbv3.put("asscociateTable_OtherNo", asscociateTable_OtherNo[i]);
			sqlbv3.put("mEdorAcceptNo", mEdorAcceptNo);
			sqlbv3.put("mEdorConfNo", mEdorConfNo);
			map.put(sqlbv3, "UPDATE");
		//}

	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";
		tG.ComCode = "86110000";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorAcceptNo", "6120070227003563");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		PEdorValidBL tPEdorValidBL = new PEdorValidBL();

		if (!tPEdorValidBL.submitData(tVData, "")) {
			logger.debug(tPEdorValidBL.mErrors.getFirstError());
		}
	}

}
