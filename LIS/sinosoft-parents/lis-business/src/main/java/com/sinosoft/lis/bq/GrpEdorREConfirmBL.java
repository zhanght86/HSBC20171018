package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContStateDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJSPayGrpDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpContStateDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LJAPayGrpSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LJAPayGrpSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSPayGrpSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: sinosfot
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */
public class GrpEdorREConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorREConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	/** 复效险种 */
	private LCGrpContStateSet mLCGrpContStateSet = new LCGrpContStateSet();
	private LJAPaySchema mLJAPaySchema = new LJAPaySchema();

	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMap = new MMap();

	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();

	public boolean submitData(VData cInputData, String cOperate) {

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 数据校验
		if (!checkData()) {
			return false;
		}
		// 数据准备操作（dealData())
		if (!dealData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		if (mLPGrpEdorItemSchema == null || mGlobalInput == null) {
			CError.buildErr(this, "输入数据不完整!");
			return false;
		}
		return true;
	}

	/**
	 * 对业务数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpEdorItemDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询团险保全项目信息失败!");
			return false;
		}
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1) {
			CError.buildErr(this, "团险保全项目信息不存在!");
			return false;
		}
		mLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);

		return true;
	}

	private boolean dealData() {
		if (!dealGrpContState())
			return false;

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getGrpContNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() == 0) {
			CError.buildErr(this, "查询个人保全信息失败!");
			tLPEdorItemDB = null;
			tLPEdorItemSet = null;
			return false;
		}
		tLPEdorItemDB = null;

		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			MMap map = dealContRE(tLPEdorItemSet.get(i));
			if (map == null) {
				CError.buildErr(this, "处理个人保全复效失败!");
				return false;
			}
			mMap.add(map);
			map = null;
		}

		LJAPayDB tLJAPayDB = new LJAPayDB();
		tLJAPayDB.setOtherNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLJAPayDB.setOtherNoType("10");
		LJAPaySet tLJAPaySet = tLJAPayDB.query();
		if (tLJAPaySet == null || tLJAPaySet.size() == 0) {
			CError.buildErr(this, "查询复效保全实收记录失败!");
			return false;
		}
		mLJAPaySchema.setSchema(tLJAPaySet.get(1));

		mMap.put("delete from ljspay where otherno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "'", "DELETE");
		mMap.put("delete from ljspaygrp where grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "'", "DELETE");

		String SQL = "select distinct grppolno from ljapayperson where payno = '"
				+ mLJAPaySchema.getPayNo() + "'";
		SSRS tSS = new SSRS();
		ExeSQL tES = new ExeSQL();
		tSS = tES.execSQL(SQL);
		if (tSS.MaxRow < 1) {
			CError.buildErr(this, "查询团体险种号失败!");
			return false;
		}
		for (int i = 1; i <= tSS.MaxRow; i++) {
			MMap map = dealLJSPayGrp(tSS.GetText(i, 1));
			if (map == null)
				return false;
			else
				mMap.add(map);
		}
		// for(int i = 1; i <= mLCGrpContStateSet.size(); i++){
		// dealLJSPayGrp(mLCGrpContStateSet.get(i).getGrpPolNo());
		// }

		return true;
	}

	private MMap dealContRE(LPEdorItemSchema cLPEdorItemSchema) {
		VData tVD = new VData();
		tVD.add(mGlobalInput);
		tVD.add(cLPEdorItemSchema);
		PEdorREConfirmBL tPEdorREConfirmBL = new PEdorREConfirmBL();
		if (!tPEdorREConfirmBL.submitData(tVD, "")) {
			mErrors.copyAllErrors(tPEdorREConfirmBL.mErrors);
			tPEdorREConfirmBL = null;
			tVD = null;
			return null;
		}
		MMap map = (MMap) tPEdorREConfirmBL.getResult().getObjectByObjectName(
				"MMap", 0);
		if (map == null) {
			CError.buildErr(this, "接收复效数据失败!");
			tPEdorREConfirmBL = null;
			tVD = null;
			return null;
		}
		tPEdorREConfirmBL = null;
		tVD = null;
		return map;
	}

	/**
	 * 根据复效险种，处理LJAPayGrp数据
	 * 
	 * @param GrpPolNo
	 * @return MMap
	 */
	private MMap dealLJSPayGrp(String GrpPolNo) {
		MMap map = new MMap();
		LJSPayGrpDB tLJSPayGrpDB = new LJSPayGrpDB();
		tLJSPayGrpDB.setGrpPolNo(GrpPolNo);
		LJSPayGrpSet tLJSPayGrpSet = tLJSPayGrpDB.query();
		if (tLJSPayGrpSet == null || tLJSPayGrpSet.size() == 0) {
			CError.buildErr(this, "查询团体险种应收记录失败!");
			return null;
		}
		if (tLJSPayGrpSet.size() > 1) {
			CError.buildErr(this, "团体险种存在多期应收记录!");
			return null;
		}

		LJAPayGrpSet tLJAPayGrpSet = new LJAPayGrpSet();

		Reflections tRef = new Reflections();
		LJAPayGrpSchema tLJAPayGrpSchema = new LJAPayGrpSchema();
		tRef.transFields(tLJAPayGrpSchema, tLJSPayGrpSet.get(1));
		tLJAPayGrpSchema.setPayNo(mLJAPaySchema.getPayNo());
		tLJAPayGrpSchema.setGetNoticeNo(mLJAPaySchema.getGetNoticeNo());
		tLJAPayGrpSchema.setPayType(mLPGrpEdorItemSchema.getEdorType());
		tLJAPayGrpSchema.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
		tLJAPayGrpSchema.setEnterAccDate(mLJAPaySchema.getEnterAccDate());
		tLJAPayGrpSchema.setConfDate(CurrDate);
		tLJAPayGrpSchema.setMakeDate(CurrDate);
		tLJAPayGrpSchema.setMakeTime(CurrTime);
		tLJAPayGrpSchema.setModifyDate(CurrDate);
		tLJAPayGrpSchema.setModifyTime(CurrTime);
		tLJAPayGrpSchema.setOperator(mGlobalInput.Operator);

		if (tLJAPayGrpSchema.getPayIntv() == -1) {
			tLJAPayGrpSchema.setSumActuPayMoney(mLJAPaySchema
					.getSumActuPayMoney());
			tLJAPayGrpSchema.setSumDuePayMoney(mLJAPaySchema
					.getSumActuPayMoney());
			tLJAPayGrpSet.add(tLJAPayGrpSchema);
		} else {
			tLJAPayGrpSet.add(tLJAPayGrpSchema);
			LJAPayGrpSet otherLJAPayGrpSet = CreateOtherLJAPayGrp(tLJAPayGrpSchema);
			if (otherLJAPayGrpSet == null) {
				mErrors.addOneError("生成实收集体交费失败!");
				return null;
			}
			if (otherLJAPayGrpSet.size() > 0) {
				tLJAPayGrpSet.add(otherLJAPayGrpSet);
			}
		}

		map.put(tLJAPayGrpSet, "INSERT");

		return map;
	}

	/**
	 * 复效多期应收时，根据复效险种首期LJAPayGrp，生成其余各期数据
	 * 
	 * @param cLJAPayGrpSchema
	 * @return LJAPayGrpSet
	 */
	private LJAPayGrpSet CreateOtherLJAPayGrp(LJAPayGrpSchema cLJAPayGrpSchema) {
		LJAPayGrpSet otherLJAPayGrpSet = new LJAPayGrpSet();
		int iCount = 1;
		int CountUnit = 1;
		FDate tFD = new FDate();
		String DateUnit;
		switch (cLJAPayGrpSchema.getPayIntv()) {
		case 1:
			DateUnit = "M";
			break;
		case 3:
			DateUnit = "M";
			CountUnit = 3;
			break;
		case 6:
			DateUnit = "M";
			CountUnit = 6;
			break;
		case 12:
			DateUnit = "Y";
			break;
		case 0:
			CError.buildErr(this, "趸交险种不能复效!");
			return null;
		default:
			CError.buildErr(this, "不支持交费间隔!");
			return null;
		}
		ExeSQL tES = new ExeSQL();
		String CurPayToDate = PubFun.calDate(
				cLJAPayGrpSchema.getCurPayToDate(), iCount * CountUnit,
				DateUnit, null);
		while (tFD.getDate(CurPayToDate).before(
				tFD.getDate(mLPGrpEdorItemSchema.getEdorValiDate()))) {
			String sGetMoney = tES
					.getOneValue("select nvl(sum(sumactupaymoney),0) from ljapayperson where paytype = '"
							+ mLPGrpEdorItemSchema.getEdorType()
							+ "' and grppolno = '"
							+ cLJAPayGrpSchema.getGrpPolNo()
							+ "' and getnoticeno = '"
							+ cLJAPayGrpSchema.getGetNoticeNo() + "'");
			double GetMoney = Double.parseDouble(sGetMoney);
			if (GetMoney != 0) {
				LJAPayGrpSchema tLJAPayGrpSchema = new LJAPayGrpSchema();
				tLJAPayGrpSchema.setSchema(cLJAPayGrpSchema);
				tLJAPayGrpSchema.setSumActuPayMoney(GetMoney);
				tLJAPayGrpSchema.setSumDuePayMoney(GetMoney);
				tLJAPayGrpSchema
						.setPayCount(tLJAPayGrpSchema.getPayCount() + 1);
				tLJAPayGrpSchema.setLastPayToDate(PubFun.calDate(
						cLJAPayGrpSchema.getLastPayToDate(),
						iCount * CountUnit, DateUnit, null));
				tLJAPayGrpSchema.setCurPayToDate(CurPayToDate);
				otherLJAPayGrpSet.add(tLJAPayGrpSchema);
				tLJAPayGrpSchema = null;
			}
			++iCount;
			CurPayToDate = PubFun.calDate(cLJAPayGrpSchema.getCurPayToDate(),
					iCount * CountUnit, DateUnit, null);
		}
		return otherLJAPayGrpSet;
	}

	private boolean dealGrpContState() {
		// 取出现有C、P表数据
		LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
		tLCGrpContStateDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLCGrpContStateDB.setStateType("Available");
		LCGrpContStateSet tLCGrpContStateSet = tLCGrpContStateDB.query();
		if (tLCGrpContStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询团体保单状态信息失败!");
			return false;
		}
		if (tLCGrpContStateSet == null || tLCGrpContStateSet.size() < 1) {
			CError.buildErr(this, "团体保单状态信息不存在!");
		}

		for (int i = 1; i <= tLCGrpContStateSet.size(); i++) {
			if ("1".equals(tLCGrpContStateSet.get(i).getState())
					&& (tLCGrpContStateSet.get(i).getEndDate() == null || tLCGrpContStateSet
							.get(i).getEndDate().trim().equals(""))) {
				mLCGrpContStateSet.add(tLCGrpContStateSet.get(i));
			}
		}

		LPGrpContStateDB tLPGrpContStateDB = new LPGrpContStateDB();
		tLPGrpContStateDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContStateDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpContStateDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpContStateDB.setStateType("Available");
		LPGrpContStateSet tLPGrpContStateSet = tLPGrpContStateDB.query();
		if (tLPGrpContStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全团体保单状态信息失败!");
			return false;
		}
		if (tLPGrpContStateSet == null || tLPGrpContStateSet.size() < 1) {
			CError.buildErr(this, "保全团体保单状态信息不存在!");
			return false;
		}

		String delSql;
		// 清除旧P表
		delSql = " delete from lpgrpcontstate where " + " edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "'" + " and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "'"
				+ " and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "' " + " and statetype = 'Available'";
		mMap.put(delSql, "DELETE");

		// 清除旧C表
		delSql = " delete from lcgrpcontstate where " + " grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "' "
				+ " and statetype = 'Available'";
		mMap.put(delSql, "DELETE");

		Reflections ref = new Reflections();
		String CurrDate = PubFun.getCurrentDate();
		String CurrTime = PubFun.getCurrentTime();
		int i;
		// 插入P表
		LPGrpContStateSchema tLPGrpContStateSchema;
		for (i = 1; i <= tLCGrpContStateSet.size(); i++) {
			tLPGrpContStateSchema = new LPGrpContStateSchema();
			ref.transFields(tLPGrpContStateSchema, tLCGrpContStateSet.get(i));
			tLPGrpContStateSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpContStateSchema.setEdorType(mLPGrpEdorItemSchema
					.getEdorType());
			tLPGrpContStateSchema.setModifyDate(CurrDate);
			tLPGrpContStateSchema.setModifyTime(CurrTime);
			mMap.put(tLPGrpContStateSchema, "INSERT");
		}

		// 插入C表
		LCGrpContStateSchema tLCGrpContStateSchema;
		for (i = 1; i <= tLPGrpContStateSet.size(); i++) {
			tLCGrpContStateSchema = new LCGrpContStateSchema();
			ref.transFields(tLCGrpContStateSchema, tLPGrpContStateSet.get(i));
			tLCGrpContStateSchema.setModifyDate(CurrDate);
			tLCGrpContStateSchema.setModifyTime(CurrTime);
			mMap.put(tLCGrpContStateSchema, "INSERT");
		}
		mResult.add(mMap);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
