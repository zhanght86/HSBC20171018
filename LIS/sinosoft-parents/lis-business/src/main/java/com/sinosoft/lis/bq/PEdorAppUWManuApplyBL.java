/*
 * @(#)PersonUnionBL.java 2005-06-03 Copyright 2005 Sinosoft Co. Ltd. All rights reserved. All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCSpecDB;
import com.sinosoft.lis.db.LPSpecDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LDSysTraceSchema;
import com.sinosoft.lis.schema.LPSpecSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LPSpecSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-人工核保申请处理类
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
 * @CreateDate：2005-06-03
 */
public class PEdorAppUWManuApplyBL {
private static Logger logger = Logger.getLogger(PEdorAppUWManuApplyBL.class);
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

	private String mMissionID;
	private String mSubMissionID;
	private String mEdorAcceptNo;

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorAppUWManuApplyBL() {
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

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("after prepareOutputData...");

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionDB.setSubMissionID(mSubMissionID);
		tLWMissionDB.setActivityID("0000000005"); // 人工核保申请
		LWMissionSet tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "查询工作流保全人工核保申请任务节点失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
			CError tError = new CError();
			tError.errorMessage = "查询工作流保全人工核保的起始任务节点失败!";
			mErrors.addOneError(tError);
			return false;
		}

		LWMissionSchema tLWMissionSchema = tLWMissionSet.get(1);
		String sApplyFlag = tLWMissionSchema.getMissionProp10(); // 申请标志位
		if (sApplyFlag != null && sApplyFlag.trim().equals("1")) {
			CError tError = new CError();
			tError.errorMessage = "保全人工核保任务已经申请过!";
			mErrors.addOneError(tError);
			return false;
		}

		tLWMissionSchema.setMissionProp10("1");
		map.put(tLWMissionSchema, "UPDATE");

		LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
		tLDSysTraceSchema.setCreatePos("保全个单人工核保");
		tLDSysTraceSchema.setMakeDate(PubFun.getCurrentDate());
		tLDSysTraceSchema.setMakeTime(PubFun.getCurrentTime());
		tLDSysTraceSchema.setModifyDate(PubFun.getCurrentDate());
		tLDSysTraceSchema.setModifyTime(PubFun.getCurrentTime());
		tLDSysTraceSchema.setManageCom(mGlobalInput.ManageCom);
		tLDSysTraceSchema.setOperator(mGlobalInput.Operator);
		tLDSysTraceSchema.setOperator2(mGlobalInput.Operator);
		tLDSysTraceSchema.setPolNo(mEdorAcceptNo);
		tLDSysTraceSchema.setPolState(2001);
		tLDSysTraceSchema.setRemark("U");

		map.put(tLDSysTraceSchema, "INSERT");

		// 应业务需求, 要求在保全人工核保时能修改删除新契约的特约信息
		// 因此这里把新契约特约信息拷贝一份放入P表, 在保全确认生效时再CP互换
		// XinYQ added on 2007-03-13

		String QuerySQL = new String("");
		String DeleteSQL = new String("");

		// 先检查本次保全是否已经存在特约, 可能是被打回重进人工核保的
		QuerySQL = "select * " + "from LPSpec a " + "where 1 = 1 "
				+ "and EdorNo in " + "(select EdorNo " + "from LPEdorItem "
				+ "where 1 = 1 " + "and EdorAcceptNo = '" + "?mEdorAcceptNo?"
				+ "')";
		// logger.debug(QuerySQL);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(QuerySQL);
		sqlbv.put("mEdorAcceptNo", mEdorAcceptNo);
		LPSpecDB tLPSpecDB = new LPSpecDB();
		LPSpecSet tLPSpecSet = new LPSpecSet();
		try {
			tLPSpecSet = tLPSpecDB.executeQuery(sqlbv);
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全特约批改表出现异常！");
			return false;
		}
		if (tLPSpecSet == null || tLPSpecSet.size() <= 0) {
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();

			// 查询 LPEdorItem
			QuerySQL = "select distinct EdorNo, EdorType, ContNo "
					+ "from LPEdorItem " + "where 1 = 1 "
					+ "and EdorAcceptNo = '" + "?mEdorAcceptNo?" + "'";
			// logger.debug(QuerySQL);
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(QuerySQL);
			sqlbv.put("mEdorAcceptNo", mEdorAcceptNo);
			try {
				tSSRS = tExeSQL.execSQL(sqlbv);
			} catch (Exception ex) {
				CError.buildErr(this, "查询保全批改项目表出现异常！");
				return false;
			}
			if (tSSRS != null && tSSRS.getMaxRow() > 0) {
				LPSpecSet tLPSpecSetNew = new LPSpecSet();
				// LPUWMasterSet tLPUWMasterSetNew = new LPUWMasterSet();

				for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
					// 查询 LCSpec
					QuerySQL = "select * " + "from LCSpec " + "where 1 = 1 "
							+ "and PolNo in " + "(select PolNo "
							+ "from LCPol " + "where 1 = 1 " + "and ContNo = '?ContNo?')";
					// logger.debug(QuerySQL);
					SQLwithBindVariables sbv=new SQLwithBindVariables();
					sbv.sql(QuerySQL);
					sbv.put("ContNo", tSSRS.GetText(i, 3));
					LCSpecDB tLCSpecDB = new LCSpecDB();
					LCSpecSet tLCSpecSet = new LCSpecSet();
					try {
						tLCSpecSet = tLCSpecDB.executeQuery(sbv);
					} catch (Exception ex) {
						CError.buildErr(this, "查询新契约特约表出现异常！");
						return false;
					}
					// 拷贝 LCSpec 到 LPSpec
					if (tLCSpecSet != null && tLCSpecSet.size() > 0) {
						for (int k = 1; k <= tLCSpecSet.size(); k++) {
							LCSpecSchema tLCSpecSchema = new LCSpecSchema();
							tLCSpecSchema = tLCSpecSet.get(k);
							LPSpecSchema tLPSpecSchemaNew = new LPSpecSchema();
							PubFun.copySchema(tLPSpecSchemaNew, tLCSpecSchema);
							tLPSpecSchemaNew.setEdorNo(tSSRS.GetText(i, 1));
							tLPSpecSchemaNew.setEdorType(tSSRS.GetText(i, 2));
							tLPSpecSchemaNew.setOperator(mGlobalInput.Operator);
							tLPSpecSchemaNew.setModifyDate(mCurrentDate);
							tLPSpecSchemaNew.setModifyTime(mCurrentTime);
							tLPSpecSetNew.add(tLPSpecSchemaNew);
							tLPSpecSchemaNew = null;
							tLCSpecSchema = null;
						}
					}
					tLCSpecDB = null;
					tLCSpecSet = null;

					// ----------------------------------------------------------

					// //删除 LPUWMaster
					// DeleteSQL = "delete from LPUWMaster "
					// + "where 1 = 1 "
					// + "and EdorNo = '" + tSSRS.GetText(i, 1) + "' "
					// + "and EdorType = '" + tSSRS.GetText(i, 2) + "'";
					// //logger.debug(DeleteSQL);
					// map.put(DeleteSQL, "DELETE");
					//
					// //----------------------------------------------------------
					//
					// //查询 LCUWMaster
					// LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
					// tLCUWMasterDB.setContNo(tSSRS.GetText(i, 3));
					// LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
					// try
					// {
					// tLCUWMasterSet = tLCUWMasterDB.query();
					// }
					// catch (Exception ex)
					// {
					// CError.buildErr(this, "查询新契约特约原因表出现异常！");
					// return false;
					// }
					// //拷贝 LCUWMaster 到 LPUWMaster
					// if (tLCUWMasterSet != null && tLCUWMasterSet.size() > 0)
					// {
					// for (int k = 1; k <= tLCUWMasterSet.size(); k++)
					// {
					// LCUWMasterSchema tLCUWMasterSchema = new
					// LCUWMasterSchema();
					// tLCUWMasterSchema = tLCUWMasterSet.get(k);
					// LPUWMasterSchema tLPUWMasterSchemaNew = new
					// LPUWMasterSchema();
					// PubFun.copySchema(tLPUWMasterSchemaNew,
					// tLCUWMasterSchema);
					// tLPUWMasterSchemaNew.setEdorNo(tSSRS.GetText(i, 1));
					// tLPUWMasterSchemaNew.setEdorType(tSSRS.GetText(i, 2));
					// tLPUWMasterSchemaNew.setOperator(mGlobalInput.Operator);
					// tLPUWMasterSchemaNew.setModifyDate(mCurrentDate);
					// tLPUWMasterSchemaNew.setModifyTime(mCurrentTime);
					// tLPUWMasterSetNew.add(tLPUWMasterSchemaNew);
					// tLPUWMasterSchemaNew = null;
					// tLCUWMasterSchema = null;
					// }
					// }
					// tLCUWMasterDB = null;
					// tLCUWMasterSet = null;
				}
				map.put(tLPSpecSetNew, "INSERT");
				// map.put(tLPUWMasterSetNew, "INSERT");
				tLPSpecSetNew = null;
				// tLPUWMasterSetNew = null;
			}
			tExeSQL = null;
			tSSRS = null;
		}
		tLPSpecDB = null;
		tLPSpecSet = null;

		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			VData tVData = new VData();
			tVData.add(map);
			mResult.add(tVData);
		} catch (Exception ex) {
			CError tError = new CError();
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
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

}
