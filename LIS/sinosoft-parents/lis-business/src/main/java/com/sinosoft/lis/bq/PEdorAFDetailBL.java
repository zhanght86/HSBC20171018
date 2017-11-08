/*
 * @(#)PEdorAFDetailBL.java	2005-07-07
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLUWPremMasterDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LLUWPremMasterSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-二核处理（加费、特约）明细保存处理类
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
 * @CreateDate：2005-07-26
 */
public class PEdorAFDetailBL {
private static Logger logger = Logger.getLogger(PEdorAFDetailBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// 准备校验规则所需要的计算要素
	private BqCalBase mBqCalBase = new BqCalBase();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorAFDetailBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 *            传入的数据对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// *******************************************************************
		// mOperate 操作字符串说明：
		// Prepare - 二核处理前准备P表数据 （为了直接调用保全人工核保进行加费计算）
		// Detail - 二核明细处理
		// *******************************************************************

		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收前台数据失败!");
			return false;
		}

		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError.buildErr(this, "传入数据有误!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {

		// 查询批改项目信息
		if (!queryData()) {
			return false;
		}

		if (mOperate.equals("Prepare")) {
			// 二核处理前准备P表数据 （为了直接调用保全人工核保进行加费计算）
			return doPrepare();
		} else if (mOperate.equals("Detail")) {
			// 二核明细处理
			return doDetail();
		} else {
			CError.buildErr(this, "操作标志错误!");
			return false;
		}
	}

	/**
	 * 二核处理前准备P表数据 （为了直接调用保全人工核保进行加费计算）
	 * 
	 * @return boolean
	 */
	private boolean doPrepare() {
		if (mLPEdorItemSchema.getStandbyFlag1() != null
				&& mLPEdorItemSchema.getStandbyFlag1().equals("1")) {
			CError.buildErr(this, "二核数据已经准备!");
			return false;
		}

		// 准备保单、险种、责任、保费项、领取等信息
		String sInsertLPCont = " insert into LPCont ( select " + "'"
				+ "?EdorNo?" + "', " + "'"
				+ "?EdorType?" + "', " + " c.* "
				+ " from LCCont c where c.contno = '"
				+ "?ContNo?" + "')";
		String sInsertLPPol = " insert into LPPol ( select " + "'"
				+ "?EdorNo?" + "', " + "'"
				+ "?EdorType?" + "', " + " c.* "
				+ " from LCPol c where c.contno = '"
				+ "?ContNo?" + "')";
		String sInsertLPDuty = " insert into LPDuty ( select " + "'"
				+ "?EdorNo?" + "', " + "'"
				+ "?EdorType?" + "', " + " c.* "
				+ " from LCDuty c where c.contno = '"
				+ "?ContNo?" + "')";
		String sInsertLPPrem = " insert into LPPrem ( select " + "'"
				+ "?EdorNo?" + "', " + "'"
				+ "?EdorType?" + "', " + " c.* "
				+ " from LCPrem c where c.contno = '"
				+ "?ContNo?" + "')";
		String sInsertLPGet = " insert into LPGet ( select " + "'"
				+ "?EdorNo?" + "', " + "'"
				+ "?EdorType?" + "', " + " c.* "
				+ " from LCGet c where c.contno = '"
				+ "?ContNo?" + "')";
        SQLwithBindVariables sbv1=new SQLwithBindVariables();
        sbv1.sql(sInsertLPCont);
        sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
        sbv1.put("EdorType", mLPEdorItemSchema.getEdorType());
        sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sbv2=new SQLwithBindVariables();
        sbv2.sql(sInsertLPPol);
        sbv2.put("EdorNo", mLPEdorItemSchema.getEdorNo());
        sbv2.put("EdorType", mLPEdorItemSchema.getEdorType());
        sbv2.put("ContNo", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sbv3=new SQLwithBindVariables();
        sbv3.sql(sInsertLPDuty);
        sbv3.put("EdorNo", mLPEdorItemSchema.getEdorNo());
        sbv3.put("EdorType", mLPEdorItemSchema.getEdorType());
        sbv3.put("ContNo", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sbv4=new SQLwithBindVariables();
        sbv4.sql(sInsertLPPrem);
        sbv4.put("EdorNo", mLPEdorItemSchema.getEdorNo());
        sbv4.put("EdorType", mLPEdorItemSchema.getEdorType());
        sbv4.put("ContNo", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sbv5=new SQLwithBindVariables();
        sbv5.sql(sInsertLPGet);
        sbv5.put("EdorNo", mLPEdorItemSchema.getEdorNo());
        sbv5.put("EdorType", mLPEdorItemSchema.getEdorType());
        sbv5.put("ContNo", mLPEdorItemSchema.getContNo());
		map.put(sbv1, "INSERT");
		map.put(sbv2, "INSERT");
		map.put(sbv3, "INSERT");
		map.put(sbv4, "INSERT");
		map.put(sbv5, "INSERT");

		mLPEdorItemSchema.setStandbyFlag1("1");
		map.put(mLPEdorItemSchema, "UPDATE");

		return true;
	}

	/**
	 * 二核明细处理
	 * 
	 * @return boolean
	 */
	private boolean doDetail() {
		String sClmNo = getClmNo(mLPEdorItemSchema.getContNo());
		String sContNo = mLPEdorItemSchema.getContNo();
		// 查询理赔核保加费涉及的险种
		String selLPPol = " select * from lppol where polno in "
				+ " ( select distinct polno from LLUWPremMaster where contno = '"
				+ "?sContNo?" + "' and clmno = '" + "?sClmNo?" + "' ) and contno = '"
				+ "?sContNo?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(selLPPol);
		sqlbv.put("sContNo", sContNo);	
		sqlbv.put("sClmNo", sClmNo);
		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = tLPPolDB.executeQuery(sqlbv);
		if (tLPPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全险种信息查询失败!");
			return false;
		}
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			LPPremSet tLPPremSet = queryClmPrem(sClmNo, tLPPolSet.get(i)
					.getPolNo());
			if (tLPPremSet == null) {
				return false;
			}

			VData tInputData = new VData();
			tInputData.add(mGlobalInput);
			tInputData.add(tLPPolSet.get(i));
			tInputData.add(tLPPremSet);
			tInputData.add("二核加费");// 加费原因
			EdorUWManuAddUI tEdorUWManuAddUI = new EdorUWManuAddUI();
			if (!tEdorUWManuAddUI.submitData(tInputData, "")) {
				CError.buildErr(this, "加费处理失败!");
				return false;
			}
		}

		return true;
	}

	/**
	 * 查询批改项目信息
	 * 
	 * @return boolean
	 */
	private boolean queryData() {

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询批改项目信息失败!");
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));

		return true;
	}

	/**
	 * 查询理赔加费信息
	 * 
	 * @return boolean
	 */
	private String getClmNo(String sContNo) {
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setActivityID("0000000000");
		tLWMissionDB.setMissionProp3(sContNo);
		LWMissionSet tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionDB.mErrors.needDealError()) {
			CError.buildErr(this, "二核任务查询失败!");
			return null;
		}
		if (tLWMissionSet == null || tLWMissionSet.size() < 1) {
			CError.buildErr(this, "未查到二核任务!");
			return null;
		}

		return tLWMissionSet.get(1).getMissionProp2();
	}

	/**
	 * 查询理赔加费信息
	 * 
	 * @return boolean
	 */
	private LPPremSet queryClmPrem(String sClmNo, String sPolNo) {

		LLUWPremMasterDB tLLUWPremMasterDB = new LLUWPremMasterDB();
		tLLUWPremMasterDB.setClmNo(sClmNo);
		tLLUWPremMasterDB.setContNo(mLPEdorItemSchema.getContNo());
		tLLUWPremMasterDB.setPolNo(sPolNo);
		LLUWPremMasterSet tLLUWPremMasterSet = tLLUWPremMasterDB.query();
		if (tLLUWPremMasterDB.mErrors.needDealError()) {
			CError.buildErr(this, "二核理赔加费查询失败!");
			return null;
		}
		if (tLLUWPremMasterSet == null || tLLUWPremMasterSet.size() < 1) {
			CError.buildErr(this, "未查到理赔加费信息!");
			return null;
		}

		Reflections tRf = new Reflections();
		LPPremSet clmLPPremSet = new LPPremSet();
		LPPremSchema clmLPPremSchema;
		for (int i = 1; i <= tLLUWPremMasterSet.size(); i++) {
			clmLPPremSchema = new LPPremSchema();
			tRf.transFields(clmLPPremSchema, tLLUWPremMasterSet.get(i));
			clmLPPremSet.add(clmLPPremSchema);
		}

		return clmLPPremSet;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(map);
		mResult.add(mBqCalBase);
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

}
