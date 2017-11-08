/*
 * @(#)PEdorValidBL.java	2005-06-10
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-保全生效处理类
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
public class PEdorAppUpdMIsssionBL {
private static Logger logger = Logger.getLogger(PEdorAppUpdMIsssionBL.class);
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

	public PEdorAppUpdMIsssionBL() {
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
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		String sActivityID = (String) mTransferData
				.getValueByName("ActivityID");
		String sMissionID = (String) mTransferData.getValueByName("MissionID");
		String sSubMissionID = (String) mTransferData
				.getValueByName("SubMissionID");
		String sAppntName = (String) mTransferData.getValueByName("AppntName");
		String sPaytoDate = (String) mTransferData.getValueByName("PaytoDate");
		String sOtherNo = (String) mTransferData.getValueByName("OtherNo");
		String sOtherNoType = (String) mTransferData
				.getValueByName("OtherNoType");
		String sEdorAppName = (String) mTransferData
				.getValueByName("EdorAppName");
		String sApptype = (String) mTransferData.getValueByName("Apptype");
		String sEdorAppDate = (String) mTransferData
				.getValueByName("EdorAppDate");

		// 查出保全申请节点，更新节点数据
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setMissionID(sMissionID);
		tLWMissionDB.setSubMissionID(sSubMissionID);
		tLWMissionDB.setActivityID(sActivityID);
		LWMissionSet tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全无扫描申请任务查询失败!");
			return false;
		}
		if (tLWMissionSet == null || tLWMissionSet.size() < 1) {
			CError.buildErr(this, "没有保全无扫描申请任务!");
			return false;
		}
		tLWMissionSet.get(1).setMissionProp7(sOtherNo);
		tLWMissionSet.get(1).setMissionProp3(sOtherNoType);
		tLWMissionSet.get(1).setMissionProp4(sEdorAppName);
		tLWMissionSet.get(1).setMissionProp5(sApptype);
		tLWMissionSet.get(1).setMissionProp6(sEdorAppDate);
		tLWMissionSet.get(1).setMissionProp11(sAppntName);
		tLWMissionSet.get(1).setMissionProp12(sPaytoDate);
		map.put(tLWMissionSet.get(1), "UPDATE");
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

}
