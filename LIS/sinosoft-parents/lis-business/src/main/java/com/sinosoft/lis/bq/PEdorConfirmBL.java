/*
 * @(#)PEdorConfirmBL.java	2005-06-10
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.f1print.PrintTool;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorPrintSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;
import com.sinosoft.workflowengine.ActivityOperator;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-保全确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：zhangtao
 * @version：1.0
 * @CreateDate：2005-06-10
 */
public class PEdorConfirmBL {
private static Logger logger = Logger.getLogger(PEdorConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	@SuppressWarnings("unused")
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 传输数据 */
	private TransferData mTransferData = new TransferData();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private LJAGetSet mLJAGetSet = new LJAGetSet();
	private LJAPaySet mLJAPaySet = new LJAPaySet();
	private String mEdorAcceptNo = "";
	private String mEdorConfNo = "";
	private String mTemplatePath = "";

	private String mMissionID = "";

	// 逾期标志 add by zhangtao at 2005-06-01
	private String mOverDueFlag = "";
	// 个人、团体保全标志（false为个人保全，true为团体保全） add by lizhuo at 2006-3-3
	private boolean PGrpFlag = false;


	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorConfirmBL() {
	}
	
	public LJAPaySet getLJAPaySet()
	{
		return this.mLJAPaySet;
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

		logger.debug("after getInputData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

		if (mOverDueFlag.equals("OverDue")) { // 逾期终止
			return true;
		}

		if (!delCancelMission()) {
			return false;
		}
		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("after prepareOutputData...");

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		if (!getLPEdorApp()) {
			return false;
		}
		// //查询保全受理下所有申请批单
		// if (!getLPEdorMain())
		// {
		// return false;
		// }
		// for (int i = 1; i <= mLPEdorMainSet.size(); i++)
		// {
		// LPEdorMainSchema tLPEdorMainSchema = mLPEdorMainSet.get(i);
		// 财务核销处理
		if (!doLJFina()) {
			if (!mOverDueFlag.equals("OverDue")) {
				return false;
			} else {

				mTransferData.setNameAndValue("OverDueFlag", "Y");

				// 统一更新批改状态为逾期终止
				updEdorState(mEdorAcceptNo, "4");

				// 保单解挂
				BqContHangUpBL tContHangUpBL = new BqContHangUpBL(mGlobalInput);
				if (!tContHangUpBL.hangUpEdorAccept(mEdorAcceptNo, "0")) {
					CError.buildErr(this, "保全保单解挂失败! ");
					return false;
				} else {
					MMap tMap = tContHangUpBL.getMMap();
					map.add(tMap); // del zhangtao 2005-08-02
				}

				// 逾期终止，不再往后执行，但须继续往外层提交
				if (!prepareOutputData()) {
					return false;
				}

				logger.debug("==逾期终止 @ PEdorConfirmBL==");
				return true;
			}
		}

		// 更新批单批改号
		// logger.debug("==> 开始更新批单批改号!");
		// EdorNoUpdate tEdorNoUpdate =
		// new EdorNoUpdate(tLPEdorMainSchema.getEdorNo(),
		// tLPEdorMainSchema.getManageCom(),
		// false);
		// if (!tEdorNoUpdate.updateEdorNo())
		// {
		// return false;
		// }
		// else
		// {
		// VData rVData = tEdorNoUpdate.getResult();
		// MMap tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
		// if (tMap == null)
		// {
		// CError.buildErr(this, "获取更新批单批改号结果时失败!");
		// return false;
		// }
		// else
		// {
		// map.add(tMap);
		// }
		// }
		// logger.debug("==> 更新批单批改号成功! 更新后批单号为：" +
		

		// 批单打印
		try {
			if (!PGrpFlag) {
				if (!printData())// 生成个险批单数据
				{
					mErrors.addOneError("生成批单数据失败!");
					return false;
				}
			} else {
				if (!printGrpData())// 生成团险批单数据
				{
					mErrors.addOneError("生成批单数据失败!");
					return false;
				}
			}
		} catch (Exception e) {
			logger.debug("生成批单数据失败!");
		}

		// 统一更新批改状态为确认未生效，并设置保全确认信息
		updEdorState(mEdorAcceptNo, "6");

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));

			mEdorAcceptNo = (String) mTransferData
					.getValueByName("EdorAcceptNo");
			if (mEdorAcceptNo == null || mEdorAcceptNo.trim().equals("")) {
				CError tError = new CError();
				tError.errorMessage = "前台传输数据EdorAcceptNo失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			
			this.mEdorConfNo = (String) mTransferData
			.getValueByName("EdorConfNo");
			
			if (mEdorConfNo == null || mEdorConfNo.trim().equals("")) {
				CError tError = new CError();
				tError.errorMessage = "批单号生成失败！";
				this.mErrors.addOneError(tError);
				return false;
			}

			mTemplatePath = (String) mTransferData
					.getValueByName("TemplatePath");
			if (mTemplatePath == null || mTemplatePath.trim().equals("")) {
				CError tError = new CError();
				tError.errorMessage = "前台传输数据TemplatePath失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 获得任务号
			mMissionID = (String) mTransferData.getValueByName("MissionID");
			if (mMissionID == null || mMissionID.equals("")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorApproveBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "前台传输业务数据中MissionID失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");

			return false;
		}

		return true;

	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	@SuppressWarnings("unchecked")
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
			mResult.add(mTransferData);
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
	private boolean getLPEdorApp() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
		if (!tLPEdorAppDB.getInfo()) {
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全受理信息查询失败!" + "保全受理号：" + mEdorAcceptNo;
			mErrors.addOneError(tError);
			return false;
		}
		mLPEdorAppSchema.setSchema(tLPEdorAppDB.getSchema());
		
		//add by jiaqiangli 2009-06-10 为防止并发增加校验判断
		if ("0".equals(mLPEdorAppSchema.getEdorState())) {
			CError.buildErr(this, "该保全受理已经保全确认!");
			return false;
		}
		//add by jiaqiangli 2009-06-10 为防止并发增加校验判断
		
		if (mLPEdorAppSchema.getEdorState().equals("4")) {
			CError.buildErr(this, "保全受理已经逾期终止!");
			return false;
		}
		if ("2".equals(mLPEdorAppSchema.getOtherNoType())
				|| "4".equals(mLPEdorAppSchema.getOtherNoType())) {
			PGrpFlag = true;
		}
		return true;
	}

	/**
	 * 财务核销处理
	 * 
	 * @return boolean
	 */
	private boolean doLJFina() {
		boolean flag = true;

		// mGlobalInput.ManageCom = tLPEdorMainSchema.getManageCom();

		LJSGetSet tLJSGetSet = new LJSGetSet();
		LJSGetDB tLJSGetDB = new LJSGetDB();
		tLJSGetDB.setOtherNo(mEdorAcceptNo);
		tLJSGetDB.setOtherNoType("10");
		tLJSGetSet = tLJSGetDB.query();
		if (tLJSGetSet.size() > 0) {
			String aGetNoticeNo = tLJSGetSet.get(1).getGetNoticeNo();
			//tSumGetMoney=-tLJSGetSet.get(1).getSumGetMoney(); //汇总金额
			LJFinaConfirm tLJFinaConfirm = new LJFinaConfirm(aGetNoticeNo, "O"); // 财务付费
			tLJFinaConfirm.setOperator(mGlobalInput.Operator);
			tLJFinaConfirm.setLimit(PubFun.getNoLimit(mGlobalInput.ManageCom));
			tLJFinaConfirm.setDrawer(mLPEdorAppSchema.getPayGetName());
			tLJFinaConfirm.setDrawerID(mLPEdorAppSchema.getPersonID());
			tLJFinaConfirm.setShouldDate(mCurrentDate); // 应付日期
			tLJFinaConfirm.setEdorAcceptNo(mEdorAcceptNo);
			if (!tLJFinaConfirm.submitData()) {
				mErrors.copyAllErrors(tLJFinaConfirm.mErrors);
				CError tError = new CError();
				tError.errorMessage = "保全退费核销失败!";
				mErrors.addOneError(tError);
				return false;
			} else {
				VData rVData = tLJFinaConfirm.getResult();
				MMap tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
				if (tMap == null) {
					CError.buildErr(this, "获取保全退费核销结果时失败!");
					return false;
				} else {
					map.add(tMap);
				}
			    this.mLJAGetSet.add(tLJFinaConfirm.getLJAGetSet());
			}
			
			//add by xiongzh 2010服务规范要求，非亲办件不包含内部转办件、退费金额超过1000元的保全项目，需要生成保全办理结果通知书
			if((mLPEdorAppSchema.getAppType().equals("2")||mLPEdorAppSchema.getAppType().equals("3"))
					&&tLJSGetSet.get(1).getSumGetMoney()>1000 )
			{
				if(!getBQResultNotice(mLPEdorAppSchema,mLJAGetSet.get(1)))
				{	
					CError.buildErr(this, "生成保全办理结果通知书失败！");
					return false;
				}
			}
			
			// 如果付费总金额为0，不用打印付费通知书 add by zhangtao 2005-10-25
			//如果是LG生存给付，不在此处生成打印记录   MS不需要打印付费通知书
//			String sql="select  edortype from lpedoritem where edoracceptno='"+mEdorAcceptNo+"' and rownum=1";
//			ExeSQL tExeSQL = new ExeSQL();
//			String tEdorType = tExeSQL.getOneValue(sql);
//			if(!tEdorType.equals("LG"))
//			{
//				if (tLJSGetSet.get(1).getSumGetMoney() != 0) {
//					// 插入付费通知书
//					if (!InsertPRT()) {
//						return false;
//					}
//					mTransferData.setNameAndValue("NeedGetFlag", "Y"); // 前台提示打印付费通知书
//				} else {
//					mTransferData.setNameAndValue("NeedGetFlag", "N"); // 不需要打印付费通知书
//				}
//			}
//			else
//			{
//				mTransferData.setNameAndValue("NeedGetFlag", "N");// 不需要打印付费通知书
//				LJAGetSet tLJAGetSet = tLJFinaConfirm.getLJAGetSet();
//				for (int i=1;i<=tLJAGetSet.size();i++ )
//				{
//					
//				}
//			}
		}

		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNo(mEdorAcceptNo);
		tLJSPayDB.setOtherNoType("10");
		tLJSPaySet = tLJSPayDB.query();
		if (tLJSPaySet.size() > 0) {
			String aGetNoticeNo = tLJSPaySet.get(1).getGetNoticeNo();
			//tSumGetMoney=tLJSPaySet.get(1).getSumDuePayMoney(); //汇总金额
			LJFinaConfirm tLJFinaConfirm = new LJFinaConfirm(aGetNoticeNo, "I"); // 财务收费
			tLJFinaConfirm.setOperator(mGlobalInput.Operator);
			tLJFinaConfirm.setLimit(PubFun.getNoLimit(mGlobalInput.ManageCom));
			tLJFinaConfirm.setDrawer(mLPEdorAppSchema.getPayGetName());
			tLJFinaConfirm.setDrawerID(mLPEdorAppSchema.getPersonID());
			logger.debug("start LJFinaConfirm...");
			if (!tLJFinaConfirm.submitData()) {
				// ====DEL=====zhangtao=====2006-01-06======确认不需要做交费逾期校验========BGN=====
				// for (int i = 1; i <= tLJSPaySet.size(); i++)
				// {
				// LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
				// tLJTempFeeDB.setTempFeeNo(tLJSPaySet.get(i).getGetNoticeNo());
				// tLJTempFeeDB.setTempFeeType("4"); //保全交费
				// LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.query();
				// if (tLJTempFeeSet != null && tLJTempFeeSet.size() > 0)
				// {
				// //已经交费
				// continue;
				// }
				// else
				// {
				// //没有交费
				// String sPayDate = tLJSPaySet.get(i).getPayDate();//最晚交费日期
				// int itvs = PubFun.calInterval(sPayDate, mCurrentDate, "D");
				// if (itvs > 0)
				// {
				// mOverDueFlag = "OverDue";
				// logger.debug("== 逾期终止 ==");
				// return false;
				// }
				// }
				// }
				// ====DEL=====zhangtao=====2006-01-06======确认不需要做交费逾期校验========END=====

				// 核销错误处理
				mErrors.copyAllErrors(tLJFinaConfirm.mErrors);
				CError tError = new CError();
				tError.errorMessage = "保全交费核销失败！请确认是否已交费!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				VData rVData = tLJFinaConfirm.getResult();
				MMap tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
				if (tMap == null) {
					CError.buildErr(this, "获取保全交费核销结果时失败!");
					return false;
				} else {
					map.add(tMap);
				}
				this.mLJAPaySet.add(tLJFinaConfirm.getLJAPaySet());
			}
		}

		return flag;
	}
	
	
	


	// /**
	// * 统一更新保全受理、保全申请批单、保全项目的批改状态为
	// * @param sEdorAcceptNo
	// * @param sEdorState
	// * @return boolean
	// */
	// private void setEdorStop(String sEdorAcceptNo, String sEdorState)
	// {
	// String wherePart = "where EdorAcceptNo='" + sEdorAcceptNo + "'";
	//
	// StringBuffer sbSQL = new StringBuffer();
	//
	// //保全项目
	// sbSQL.setLength(0);
	// sbSQL
	// .append(" UPDATE LPEdorItem set EdorState = '" )
	// .append(sEdorState)
	// .append("', Operator = '")
	// .append(mGlobalInput.Operator)
	// .append("', ModifyDate = '")
	// .append(mCurrentDate)
	// .append("', ModifyTime = '")
	// .append(mCurrentTime)
	// .append("' ")
	// .append(wherePart);
	// map.put(sbSQL.toString(), "UPDATE");
	//
	// //保全批单
	// sbSQL.setLength(0);
	// sbSQL
	// .append(" UPDATE LPEdorMain set EdorState = '" )
	// .append(sEdorState)
	// .append("', Operator = '")
	// .append(mGlobalInput.Operator)
	// .append("', ModifyDate = '")
	// .append(mCurrentDate)
	// .append("', ModifyTime = '")
	// .append(mCurrentTime)
	// .append("' ")
	// .append(wherePart);
	// map.put(sbSQL.toString(), "UPDATE");
	//
	// //保全申请
	// sbSQL.setLength(0);
	// sbSQL
	// .append(" UPDATE LPEdorApp set EdorState = '" )
	// .append(sEdorState)
	// .append("', Operator = '")
	// .append(mGlobalInput.Operator)
	// .append("', ModifyDate = '")
	// .append(mCurrentDate)
	// .append("', ModifyTime = '")
	// .append(mCurrentTime)
	// .append("' ")
	// .append(wherePart);
	// map.put(sbSQL.toString(), "UPDATE");
	//
	// }

	/**
	 * 统一更新保全受理、保全申请批单、保全项目的批改状态为确认未生效 更新保全确认信息
	 * 
	 * @param sEdorAcceptNo
	 * @param sEdorState
	 */
	private void updEdorState(String sEdorAcceptNo, String sEdorState) {
		String wherePart = "where EdorAcceptNo='?sEdorAcceptNo?'";

		StringBuffer sbSQL = new StringBuffer();

		// 保全项目
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorItem set EdorState = '")
				.append("?sEdorState?")
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sbSQL.toString());
		sbv1.put("sEdorAcceptNo", sEdorAcceptNo);
		sbv1.put("sEdorState", sEdorState);
		sbv1.put("mCurrentDate", mCurrentDate);
		sbv1.put("mCurrentTime", mCurrentTime);
		map.put(sbv1, "UPDATE");

		// 保全批单
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorMain set EdorState = '").append("?sEdorState?")
				.append("', ConfOperator = '").append("?ConfOperator?")
				.append("', ConfDate = '").append("?mCurrentDate?").append(
						"', ConfTime = '")
				.append("?mCurrentTime?")
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sbSQL.toString());
		sbv2.put("sEdorAcceptNo", sEdorAcceptNo);
		sbv2.put("sEdorState", sEdorState);
		sbv2.put("mCurrentDate", mCurrentDate);
		sbv2.put("mCurrentTime", mCurrentTime);
		sbv2.put("ConfOperator", mGlobalInput.Operator);
		map.put(sbv2, "UPDATE");

		// 保全申请
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorApp set EdorState = '").append("?sEdorState?")
				.append("', ConfOperator = '").append("?ConfOperator?")
				.append("', ConfDate = '").append("?mCurrentDate?").append(
						"', ConfTime = '")
				.append("?mCurrentTime?")
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sbSQL.toString());
		sbv3.put("sEdorAcceptNo", sEdorAcceptNo);
		sbv3.put("sEdorState", sEdorState);
		sbv3.put("mCurrentDate", mCurrentDate);
		sbv3.put("mCurrentTime", mCurrentTime);
		sbv3.put("ConfOperator", mGlobalInput.Operator);
		map.put(sbv3, "UPDATE");

		// 团体保全批单
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPGrpEdorMain set EdorState = '").append(
				"?sEdorState?").append("', ConfOperator = '").append(
				"?ConfOperator?").append("', ConfDate = '").append(
				"?mCurrentDate?").append("', ConfTime = '")
				.append("?mCurrentTime?")
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(sbSQL.toString());
		sbv4.put("sEdorAcceptNo", sEdorAcceptNo);
		sbv4.put("sEdorState", sEdorState);
		sbv4.put("mCurrentDate", mCurrentDate);
		sbv4.put("mCurrentTime", mCurrentTime);
		sbv4.put("ConfOperator", mGlobalInput.Operator);
		map.put(sbv4, "UPDATE");

		// 团体保全项目申请
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPGrpEdorItem set EdorState = '")
				.append("?sEdorState?")
				// .append("', Operator = '")
				// .append(mGlobalInput.Operator)
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(sbSQL.toString());
		sbv5.put("sEdorAcceptNo", sEdorAcceptNo);
		sbv5.put("sEdorState", sEdorState);
		sbv5.put("mCurrentDate", mCurrentDate);
		sbv5.put("mCurrentTime", mCurrentTime);
		map.put(sbv5, "UPDATE");

	}

	/**
	 * 往打印管理表插入保全办理结果通知书记录
	 * 
	 * @param: 无
	 * @return: void
	 */
	private boolean getBQResultNotice(LPEdorAppSchema tLPEdorAppSchema ,LJAGetSchema tLJAGetSchema) 
	{
		logger.debug("== 插入保全办理结果通知书 ==");
		LOPRTManagerSchema mmLOPRTManagerSchema = new LOPRTManagerSchema();
		try {
			String Code = PrintManagerBL.CODE_PEdorResult;// 个险保全办理结果通知书
			
			String strNoLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
			mmLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			mmLOPRTManagerSchema.setOtherNo(mEdorAcceptNo);
			mmLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_EDORACCEPT); // 保全受理号
			mmLOPRTManagerSchema.setCode(Code); // 个险保全办理结果通知书
			mmLOPRTManagerSchema.setManageCom(mLPEdorAppSchema.getManageCom()); // 管理机构
			mmLOPRTManagerSchema.setAgentCode(""); // 代理人编码
			mmLOPRTManagerSchema.setReqCom(mLPEdorAppSchema.getManageCom());
			mmLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

			mmLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			mmLOPRTManagerSchema.setStateFlag("0"); // 打印状态
			mmLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
			mmLOPRTManagerSchema.setStandbyFlag1(tLPEdorAppSchema.getOtherNo()); //保单号
			if("1".equals(tLJAGetSchema.getPayMode()))
			{
				mmLOPRTManagerSchema.setStandbyFlag2("现金");
			}
			else
			{
				mmLOPRTManagerSchema.setStandbyFlag2("转帐");
				mmLOPRTManagerSchema.setStandbyFlag3(tLJAGetSchema.getBankAccNo());
			}
			mmLOPRTManagerSchema.setStandbyFlag4(String.valueOf(tLJAGetSchema.getSumGetMoney())); //退费金额
			
			mmLOPRTManagerSchema.setMakeDate(mCurrentDate);
			mmLOPRTManagerSchema.setMakeTime(mCurrentTime);

			mmLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);

			map.put(mmLOPRTManagerSchema, "INSERT");
		} catch (Exception e) {
			CError.buildErr(this, "插入保全办理结果通知书失败!");
			return false;
		}

		return true;
	}
	

    /**批单打印 支持保单级别客户信息，险种信息的变更，
	   changed by pst on 2008-11-26*/
	@SuppressWarnings({ "unchecked", "static-access" })
	private boolean printData() {
		String mEdorAcceptNo = mLPEdorAppSchema.getEdorAcceptNo();
		mLPEdorAppSchema.setEdorConfNo(this.mEdorConfNo);
		XmlExportNew xmlExport = new XmlExportNew();
		logger.debug("\n\nStart Write Print Data\n\n");
		logger.debug("EdorAcceptNo ================= > " + mEdorAcceptNo);
		// 一个变更出一张批单
		ExeSQL aExeSQL = new ExeSQL();
		SSRS aSSRS = new SSRS();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select  edorno,edortype,contno from lpedoritem where edoracceptno = '?mEdorAcceptNo?' group by edorno,edortype,contno");
		sqlbv.put("mEdorAcceptNo", mEdorAcceptNo);
		aSSRS = aExeSQL
				.execSQL(sqlbv);
		for (int j = 1; j <= aSSRS.getMaxRow(); j++) {
			// 存放批改保单级别信息，支持保单级别客户信息，险种信息的变更，但支持客户级别保单信息的变更会有问题，即变更同一客户下多张保单
			//但可以通过项目分支来区分
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema.setEdorAcceptNo(mEdorAcceptNo);
			tLPEdorItemSchema.setEdorNo(aSSRS.GetText(j, 1));
			tLPEdorItemSchema.setEdorType(aSSRS.GetText(j, 2));
			tLPEdorItemSchema.setContNo(aSSRS.GetText(j, 3));
			xmlExport.createDocument("个人保全批单");
			String customerNo = "";
			if("3".equals(mLPEdorAppSchema.getOtherNoType())){
				LCContDB tLCContDB = new LCContDB();
				tLCContDB.setContNo(mLPEdorAppSchema.getOtherNo());
				tLCContDB.getInfo();
				customerNo = tLCContDB.getAppntNo();
			}else{
				customerNo = mLPEdorAppSchema.getOtherNo();
			}
			String uLanguage = PrintTool.getCustomerLanguage(customerNo);
			if (uLanguage != null && !"".equals(uLanguage)) 
				xmlExport.setUserLanguage(uLanguage);//用户语言
			xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言

			boolean mClassflag = true;
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql("select edorname from lmedoritem where edorcode = '?edorcode?'");
			sbv.put("edorcode", aSSRS.GetText(j, 2));
			String EdorName = tExeSQL
					.getOneValue(sbv);
			try {
				Class tClass = Class.forName("com.sinosoft.lis.bq.PEdor"
						+ tLPEdorItemSchema.getEdorType() + "PrintBL");
				EdorPrint tEdorPrint = (EdorPrint) tClass.newInstance();
				VData aVData = new VData();
				aVData.add(tLPEdorItemSchema);
				aVData.add(mLPEdorAppSchema); // 有时需要从主表中判断本次保全的收付费方式，故添加保全主申请表信息
				aVData.add(xmlExport);
				aVData.add(mGlobalInput);
				aVData.add(this.mLJAGetSet);//对于多领取人的保全项目，需要传入领取信息，否则无法获取支付号
				if (!tEdorPrint.submitData(aVData, "PRINT"
						+ tLPEdorItemSchema.getEdorType())) {
					mErrors.copyAllErrors(tEdorPrint.mErrors);
					mErrors.addOneError("保全项目" + EdorName + "打印处理失败!");
					return false;
				}
				VData cVData = new VData();
				cVData = tEdorPrint.getResult();
				xmlExport = (XmlExportNew) cVData.getObjectByObjectName(
						"XmlExportNew", 0);
			} catch (ClassNotFoundException ex) {
				mClassflag = false;
				logger.debug("未找到" + EdorName + "保全项目打印处理!");
			} catch (Exception ex) {
				mClassflag = false;
				CError.buildErr(this, "保全项目" + EdorName + "打印处理失败!");
				return false;
			}
			logger.debug("成功完成" + EdorName + "保全项目打印处理!");
			if (mClassflag) {

				// 生成主打印批单schema
				LPEdorPrintSchema tLPEdorPrintSchemaMain = new LPEdorPrintSchema();
				tLPEdorPrintSchemaMain.setEdorNo(tLPEdorItemSchema.getEdorNo());
				tLPEdorPrintSchemaMain.setManageCom(mGlobalInput.ManageCom);
				tLPEdorPrintSchemaMain.setPrtFlag("N");
				tLPEdorPrintSchemaMain.setPrtTimes(0);
				tLPEdorPrintSchemaMain.setMakeDate(PubFun.getCurrentDate());
				tLPEdorPrintSchemaMain.setMakeTime(PubFun.getCurrentTime());
				tLPEdorPrintSchemaMain.setOperator(mGlobalInput.Operator);
				tLPEdorPrintSchemaMain.setModifyDate(PubFun.getCurrentDate());
				tLPEdorPrintSchemaMain.setModifyTime(PubFun.getCurrentTime());
				InputStream ins = xmlExport.getInputStream();

				tLPEdorPrintSchemaMain.setEdorInfo(ins);
				SQLwithBindVariables sbv1=new SQLwithBindVariables();
				sbv1.sql("delete from LPEdorPrint where EdorNo='?EdorNo?'");
				sbv1.put("EdorNo", tLPEdorItemSchema.getEdorNo());
				map.put(sbv1, "DELETE");
				map.put(tLPEdorPrintSchemaMain, "BLOBINSERT");
			}
		}
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "static-access" })
	private boolean printGrpData() {
		XmlExportNew xmlExport = new XmlExportNew();
		logger.debug("\n\nStart Write Print Data\n\n");
		logger.debug("EdorAcceptNo ================= > " + mEdorAcceptNo);
		mLPEdorAppSchema.setEdorConfNo(this.mEdorConfNo);
		// 按不同项目内容进行打印（一个批单号打印）
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		LPGrpEdorItemSchema tLPGrpEdorItemSchema;
		ExeSQL aExeSQL = new ExeSQL();
		SSRS aSSRS = new SSRS();
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql("select distinct edorno from lpgrpedoritem where edoracceptno = '?mEdorAcceptNo?' group by edorno");
		sbv1.put("mEdorAcceptNo", mEdorAcceptNo);
		aSSRS = aExeSQL
				.execSQL(sbv1);
		if (aSSRS == null || aSSRS.MaxRow == 0) {
			CError.buildErr(this, "查询批改项目表失败!");
			return false;
		}

		String EdorNo = "";
		for (int j = 1; j <= aSSRS.getMaxRow(); j++) {
			EdorNo = aSSRS.GetText(j, 1);
			// 一个保单出一张批单
			tLPGrpEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
			tLPGrpEdorItemDB.setEdorNo(aSSRS.GetText(j, 1));
			tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
			if (tLPGrpEdorItemSet.size() < 1) {
				CError.buildErr(this, "查询保全项目表失败，未找到保全项目!");
				return false;
			}
			xmlExport.createDocument("团体保全批单");
			String customerNo = "";
			if("3".equals(mLPEdorAppSchema.getOtherNoType())){
				LCContDB tLCContDB = new LCContDB();
				tLCContDB.setContNo(mLPEdorAppSchema.getOtherNo());
				tLCContDB.getInfo();
				customerNo = tLCContDB.getAppntNo();
			}else{
				customerNo = mLPEdorAppSchema.getOtherNo();
			}
			String uLanguage = PrintTool.getCustomerLanguage(customerNo);
			if (uLanguage != null && !"".equals(uLanguage)) 
				xmlExport.setUserLanguage(uLanguage);//用户语言
			xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言

			boolean mClassflag = true;
			for (int i = 1; i <= tLPGrpEdorItemSet.size(); i++) {
				// 批单打印有各个项目的单独生成打印数据
				tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
				tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(i);
				ExeSQL tExeSQL = new ExeSQL();
				SQLwithBindVariables sbv2=new SQLwithBindVariables();
				sbv2.sql("select edorname from lmedoritem where edorcode = '?edorcode?' and appobj = 'G'");
				sbv2.put("edorcode", tLPGrpEdorItemSchema.getEdorType());
				String EdorName = tExeSQL
						.getOneValue(sbv2);
				try {
					Class tClass = Class.forName("com.sinosoft.lis.bqgrp.GrpEdor"
							+ tLPGrpEdorItemSchema.getEdorType() + "PrintBL");
					EdorPrint tEdorPrint = (EdorPrint) tClass.newInstance();
					VData aVData = new VData();
					aVData.add(tLPGrpEdorItemSchema);
					aVData.add(xmlExport);
					aVData.add(mLPEdorAppSchema); 
					aVData.add(mGlobalInput);
					if (!tEdorPrint.submitData(aVData, "PRINT"
							+ tLPGrpEdorItemSchema.getEdorType())) {
						mErrors.copyAllErrors(tEdorPrint.mErrors);
						mErrors.addOneError("保全项目" + EdorName + "打印处理失败!");
						return false;
					}
					VData cVData = new VData();
					cVData = tEdorPrint.getResult();
					xmlExport = (XmlExportNew) cVData.getObjectByObjectName(
							"XmlExportNew", 0);
					// //可能存在批单的附属清单
					MMap tMap = (MMap) cVData.getObjectByObjectName("MMap", 0);
					if (tMap != null) {
						map.add(tMap);
					}
				} catch (ClassNotFoundException ex) {
					mClassflag = false;
					logger.debug("未找到" + EdorName + "保全项目打印处理!");
				} catch (Exception ex) {
					mClassflag = false;
					CError.buildErr(this, "保全项目" + EdorName + "打印处理失败!");
					return false;
				}
				logger.debug("成功完成" + EdorName + "保全项目打印处理!");
			}
			if (mClassflag) {
				// 生成主打印批单schema
				LPEdorPrintSchema tLPEdorPrintSchemaMain = new LPEdorPrintSchema();
				tLPEdorPrintSchemaMain.setEdorNo(EdorNo);
				tLPEdorPrintSchemaMain.setManageCom(mLPEdorAppSchema
						.getManageCom());
				tLPEdorPrintSchemaMain.setPrtFlag("N");
				tLPEdorPrintSchemaMain.setPrtTimes(0);
				tLPEdorPrintSchemaMain.setMakeDate(PubFun.getCurrentDate());
				tLPEdorPrintSchemaMain.setMakeTime(PubFun.getCurrentTime());
				tLPEdorPrintSchemaMain.setOperator(mGlobalInput.Operator);
				tLPEdorPrintSchemaMain.setModifyDate(PubFun.getCurrentDate());
				tLPEdorPrintSchemaMain.setModifyTime(PubFun.getCurrentTime());
				InputStream ins = xmlExport.getInputStream();

				tLPEdorPrintSchemaMain.setEdorInfo(ins);
				SQLwithBindVariables sbv3=new SQLwithBindVariables();
				sbv3.sql("delete from LPEdorPrint where EdorNo='?EdorNo?'");
				sbv3.put("EdorNo", EdorNo);
				map
						.put(sbv3, "DELETE");
				map.put(tLPEdorPrintSchemaMain, "BLOBINSERT");
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean delCancelMission() {
		String sYBTFlag = (String) mTransferData.getValueByName("YBT");
		if (sYBTFlag != null && sYBTFlag.equals("YBT")) {
			return true;
		}

		String sql = " select * from lwmission where ActivityID in ('0000000008', '0000008008') "
				+ " and missionid = '" + "?mMissionID?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mMissionID", mMissionID);
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sqlbv);
		if (tLWMissionDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "查询工作流保全人工核保申请任务节点失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLWMissionSet == null || tLWMissionSet.size() < 1) {
			CError tError = new CError();
			tError.errorMessage = "查询工作流保全人工核保申请任务节点失败!";
			mErrors.addOneError(tError);
			return false;
		}

		for (int i = 1; i <= tLWMissionSet.size(); i++) {
			LWMissionSchema tLWMissionSchema = tLWMissionSet.get(i);
			ActivityOperator tActivityOperator = new ActivityOperator();
			
			VData tempInputData = new VData();
		    TransferData tempTransferData = new TransferData();
		    tempInputData.add(tempTransferData);
			
			if (!tActivityOperator.DeleteMission(tLWMissionSchema
					.getMissionID(), tLWMissionSchema.getSubMissionID(),
					tLWMissionSchema.getActivityID(), tempInputData)) {
				CError tError = new CError();
				tError.errorMessage = "工作流保全撤销节点删除失败!";
				mErrors.addOneError(tError);
				return false;
			}

			VData tempVData = tActivityOperator.getResult();
			if ((tempVData != null) && (tempVData.size() > 0)) {
				MMap tmap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
				map.add(tmap);
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorAcceptNo", "6120030202000002");
		tTransferData.setNameAndValue("TemplatePath", "test");

		PEdorConfirmBL tPEdorConfirmBL = new PEdorConfirmBL();
		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		if (!tPEdorConfirmBL.submitData(tVData, "")) {
			logger.debug(tPEdorConfirmBL.mErrors.getError(0).errorMessage);
		}
	}
}
