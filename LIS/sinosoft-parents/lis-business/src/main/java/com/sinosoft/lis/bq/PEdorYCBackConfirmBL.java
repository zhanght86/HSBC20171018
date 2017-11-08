/*
 * @(#)PEdorYCBackConfirmBL.java	2005-10-26
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全- 保险期限变更回退确认生效处理类
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
 * @CreateDate：2007-06-18
 */
public class PEdorYCBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorYCBackConfirmBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	//public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// 需要回退的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorYCBackConfirmBL() {
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
			mLPEdorItemSchema = // 需要回退的保全项目
			(LPEdorItemSchema) mInputData.getObjectByObjectName(
					"LPEdorItemSchema", 0);
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
		if (!exchangCPData()) {
			return false;
		}

		return true;
	}

	/**
	 * C、P表数据互换
	 * 
	 * @return: boolean
	 */
	private boolean exchangCPData() {
		Reflections tRef = new Reflections();
		// C表
		LCContSet aLCContSet = new LCContSet();
		LCPolSet aLCPolSet = new LCPolSet();
		LCDutySet aLCDutySet = new LCDutySet();
		LCPremSet aLCPremSet = new LCPremSet();
		LCGetSet aLCGetSet = new LCGetSet();

		// P表
		LPContSet aLPContSet = new LPContSet();
		LPPolSet aLPPolSet = new LPPolSet();
		LPDutySet aLPDutySet = new LPDutySet();
		LPPremSet aLPPremSet = new LPPremSet();
		LPGetSet aLPGetSet = new LPGetSet();

		// 查询P表数据[保单信息]
		LPContSet tLPContSet = new LPContSet();
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContSet = tLPContDB.query();
		for (int j = 1; j <= tLPContSet.size(); j++) {
			// 将P表中数据放到C表中[保单信息]
			LCContSchema tLCContSchema = new LCContSchema();
			tRef.transFields(tLCContSchema, tLPContSet.get(j).getSchema());
			aLCContSet.add(tLCContSchema);
		}
		// 查询C表数据[保单信息]
		LCContSet tLCContSet = new LCContSet();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCContSet = tLCContDB.query();
		for (int j = 1; j <= tLCContSet.size(); j++) {
			// 将C表中数据放到P表中[保单信息]
			LPContSchema tLPContSchema = new LPContSchema();
			tRef.transFields(tLPContSchema, tLCContSet.get(j).getSchema());
			tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			aLPContSet.add(tLPContSchema);
		}

		// 查询P表数据[险种保单]
		LPPolSet tLPPolSet = new LPPolSet();
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPPolSet.set(tLPPolDB.query());
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			// 将P表中数据放到C表中[险种保单]
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tRef.transFields(tLCPolSchema, tLPPolSet.get(i));
			aLCPolSet.add(tLCPolSchema);

			// 查询C表数据[险种保单]
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.copyAllErrors(tLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询险种保单失败!"));
				return false;
			}

			// 将C表中数据放到P表中[险种保单]
			LPPolSchema tLPPolSchema = new LPPolSchema();
			tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			aLPPolSet.add(tLPPolSchema);
		}

		// 查询P表数据[保费项]
		LPPremSet tLPPremSet = new LPPremSet();
		LPPremDB tLPPremDB = new LPPremDB();
		tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPremDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPPremSet.set(tLPPremDB.query());
		for (int j = 1; j <= tLPPremSet.size(); j++) {
			// 将P表中数据放到C表中[保费项]
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tRef.transFields(tLCPremSchema, tLPPremSet.get(j).getSchema());
			aLCPremSet.add(tLCPremSchema);
		}

		// 查询C表数据[保费项]
		LCPremSet tLCPremSet = new LCPremSet();
		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLCPremSet = tLCPremDB.query();
		for (int j = 1; j <= tLCPremSet.size(); j++) {
			// 将C表中数据放到P表中[保费项]
			LPPremSchema tLPPremSchema = new LPPremSchema();
			tRef.transFields(tLPPremSchema, tLCPremSet.get(j).getSchema());
			tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			aLPPremSet.add(tLPPremSchema);
		}

		// 查询P表数据[责任信息]
		LPDutySet tLPDutySet = new LPDutySet();
		LPDutyDB tLPDutyDB = new LPDutyDB();
		tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPDutyDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPDutySet = tLPDutyDB.query();
		for (int j = 1; j <= tLPDutySet.size(); j++) {
			// 将P表中数据放到C表中[责任信息]
			LCDutySchema tLCDutySchema = new LCDutySchema();
			tRef.transFields(tLCDutySchema, tLPDutySet.get(j).getSchema());
			aLCDutySet.add(tLCDutySchema);
		}
		// 查询C表数据[责任信息]
		LCDutySet tLCDutySet = new LCDutySet();
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLCDutySet = tLCDutyDB.query();
		for (int j = 1; j <= tLCDutySet.size(); j++) {
			// 将C表中数据放到P表中[责任信息]
			LPDutySchema tLPDutySchema = new LPDutySchema();
			tRef.transFields(tLPDutySchema, tLCDutySet.get(j).getSchema());
			tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
			aLPDutySet.add(tLPDutySchema);
		}

		// 查询P表数据[给付项信息]
		LPGetDB tLPGetDB = new LPGetDB();
		LPGetSet tLPGetSet = new LPGetSet();
		tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPGetDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPGetSet = tLPGetDB.query();
		for (int j = 1; j <= tLPGetSet.size(); j++) {
			// 将P表中数据放到C表中[给付项信息]
			LCGetSchema tLCGetSchema = new LCGetSchema();
			tRef.transFields(tLCGetSchema, tLPGetSet.get(j).getSchema());
			aLCGetSet.add(tLCGetSchema);
		}
		// 查询C表数据[给付项]
		LCGetSet tLCGetSet = new LCGetSet();
		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLCGetSet = tLCGetDB.query();
		for (int j = 1; j <= tLCGetSet.size(); j++) {
			// 将C表中数据放到P表中[给付项]
			LPGetSchema tLPGetSchema = new LPGetSchema();
			tRef.transFields(tLPGetSchema, tLCGetSet.get(j).getSchema());
			tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			aLPGetSet.add(tLPGetSchema);
		}

		map.put(aLCContSet, "UPDATE");
		map.put(aLCPolSet, "UPDATE");
		map.put(aLCDutySet, "UPDATE");
		map.put(aLCPremSet, "UPDATE");
		map.put(aLCGetSet, "UPDATE");

		map.put(aLPContSet, "UPDATE");
		map.put(aLPPolSet, "UPDATE");
		map.put(aLPDutySet, "UPDATE");
		map.put(aLPPremSet, "UPDATE");
		map.put(aLPGetSet, "UPDATE");

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(map);
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

	public CErrors getErrors() {
		return null;
	}

}
