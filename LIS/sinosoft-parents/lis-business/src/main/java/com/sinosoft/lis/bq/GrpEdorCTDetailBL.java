/*
 * @(#)GrpEdorCTDetailBL.java      Apr 5, 2006
 *
 * Copyright 2006 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */
package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团体保全操作 －－ 投保人解除合同（整单退保）
 * </p>
 * <p>
 * Description: 投保人解除合同（整单退保）保全明细处理BL类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：lizhuo
 * @CreateDate: Apr 5, 2006
 * @version：1.0
 */
public class GrpEdorCTDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorCTDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();
	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();

	public boolean submitData(VData aInputData, String aOperator) {
		mInputData = (VData) aInputData.clone();
		mOperate = aOperator;

		if (!getInputData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData() {
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPGrpPolSet = (LPGrpPolSet) mInputData.getObjectByObjectName(
				"LPGrpPolSet", 0);
		if (mLPGrpPolSet == null || mLPGrpEdorItemSchema == null
				|| mGlobalInput == null) {
			CError.buildErr(this, "接收数据不完整!");
			return false;
		}

		String sEdorReasonCode = mLPGrpEdorItemSchema.getEdorReasonCode();
		String sEdorReason = mLPGrpEdorItemSchema.getEdorReason();
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		if (!tLPGrpEdorItemDB.getInfo()) {
			CError.buildErr(this, "查询保全项目信息失败!");
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());
		mLPGrpEdorItemSchema.setEdorReasonCode(sEdorReasonCode);
		mLPGrpEdorItemSchema.setEdorReason(sEdorReason);
		return true;
	}

	private boolean dealData() {
		MMap tMap = deletePreData();
		if (tMap == null) {
			CError.buildErr(this, "删除数据失败!");
			return false;
		}
		mMap.add(tMap);

		Reflections tRef = new Reflections();
		LPInsureAccTraceSet allLPInsureAccTraceSet = new LPInsureAccTraceSet();
		LPInsureAccTraceSet pubLPInsureAccTraceSet = new LPInsureAccTraceSet();

		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		tLCContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLCContDB.setAppFlag("1");
		tLCContSet = tLCContDB.query();
		if (tLCContSet == null || tLCContSet.size() == 0) {
			CError.buildErr(this, "查询有效分单信息失败!");
			return false;
		}

		String GrpPolNo = "";
		LPGrpPolSet aLPGrpPolSet = new LPGrpPolSet();
		for (int i = 1; i <= mLPGrpPolSet.size(); i++) {
			if (i == 1) {
				GrpPolNo = "'" + mLPGrpPolSet.get(i).getGrpPolNo() + "'";
			} else {
				GrpPolNo += "," + "'" + mLPGrpPolSet.get(i).getGrpPolNo() + "'";
			}
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpPolNo(mLPGrpPolSet.get(i).getGrpPolNo());
			if (!tLCGrpPolDB.getInfo()) {
				CError.buildErr(this, "查询团体险种信息失败!");
				return false;
			}
			LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
			tRef.transFields(tLPGrpPolSchema, tLCGrpPolDB.getSchema());
			tLPGrpPolSchema.setAppFlag("4");
			tLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPGrpPolSchema.setOperator(mGlobalInput.Operator);
			tLPGrpPolSchema.setModifyDate(CurrDate);
			tLPGrpPolSchema.setModifyTime(CurrTime);
			aLPGrpPolSet.add(tLPGrpPolSchema);
		}

		mMap.put(aLPGrpPolSet, "DELETE&INSERT");

		for (int i = 1; i <= tLCContSet.size(); i++) {
			String PolSql = "select * from lcpol where contno = '"
					+ tLCContSet.get(i).getContNo() + "'"
					+ " and appflag = '1'" + " and grppolno in (" + GrpPolNo
					+ ")";
			logger.debug("SQL:" + PolSql);
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolSet = tLCPolDB.executeQuery(PolSql);
			if (tLCPolSet == null || tLCPolSet.size() == 0) {
				CError.buildErr(this, "查询被保人险种信息失败!被保人号:"
						+ tLCContSet.get(i).getInsuredNo());
				return false;
			}

			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tRef.transFields(tLPEdorItemSchema, mLPGrpEdorItemSchema);
			tLPEdorItemSchema.setInsuredNo(tLCContSet.get(i).getInsuredNo());
			tLPEdorItemSchema.setContNo(tLCContSet.get(i).getContNo());
			tLPEdorItemSchema.setPolNo("000000");

			// ===add===zhangtao===2006-11-3=====添加个人校验=================BGN==============
			AddGEdorItemCheckBL tAddGEdorItemCheckBL = new AddGEdorItemCheckBL();
			if (!tAddGEdorItemCheckBL.canAddPEdorItem(tLPEdorItemSchema)) {
				continue;// 该被保人不能参与退保计算
			}
			// ===add===zhangtao===2006-11-3=====添加个人校验=================END==============
			LPPolSet tLPPolSet = new LPPolSet();
			for (int j = 1; j <= tLCPolSet.size(); j++) {
				LPPolSchema tLPPolSchema = new LPPolSchema();
				tRef.transFields(tLPPolSchema, tLCPolSet.get(j));
				tLPPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tLPPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				tLPPolSchema.setOperator(mGlobalInput.Operator);
				tLPPolSchema.setModifyDate(CurrDate);
				tLPPolSchema.setModifyTime(CurrTime);
				tLPPolSet.add(tLPPolSchema);
			}

			mInputData.clear();
			mInputData.add(tLPPolSet);
			mInputData.add(tLPEdorItemSchema);
			mInputData.add(mGlobalInput);
			PEdorCTDetailBL tPEdorCTDetailBL = new PEdorCTDetailBL();
			if (!tPEdorCTDetailBL.submitData(mInputData, "NOEdorQuery")) {
				logger.debug("个人退保计算失败!被保人号："
						+ tLCContSet.get(i).getInsuredNo());
				mErrors.copyAllErrors(tPEdorCTDetailBL.mErrors);
				CError.buildErr(this, "个人退保计算失败!被保人号："
						+ tLCContSet.get(i).getInsuredNo());
				return false;
			}
			MMap map = (MMap) tPEdorCTDetailBL.getResult()
					.getObjectByObjectName("MMap", 0);
			if (map == null) {
				logger.debug("接收个人退保计算数据失败!被保人号："
						+ tLCContSet.get(i).getInsuredNo());
				CError.buildErr(this, "接收个人退保计算数据失败!");
				return false;
			}
			mMap.add(map);
			// ===add====zhangtao======增加对公共账户的处理================BGN=================
			// LPInsureAccClassSet rLPInsureAccClassSet =
			// (LPInsureAccClassSet)
			// tPEdorCTDetailBL.getResult().getObjectByObjectName("LPInsureAccClassSet", 0);
			LPInsureAccTraceSet rLPInsureAccTraceSet = (LPInsureAccTraceSet) tPEdorCTDetailBL
					.getResult()
					.getObjectByObjectName("LPInsureAccTraceSet", 0);
			allLPInsureAccTraceSet.add(rLPInsureAccTraceSet);
			// ===add====zhangtao======增加对公共账户的处理================END=================
			tLPEdorItemSchema.setEdorState("1");
			mMap.put(tLPEdorItemSchema, "DELETE&INSERT");
			LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
			tRef.transFields(tLPEdorMainSchema, mLPGrpEdorItemSchema);
			tRef.transFields(tLPEdorMainSchema, tLPEdorItemSchema);
			mMap.put(tLPEdorMainSchema, "DELETE&INSERT");

			map = null;
			tPEdorCTDetailBL = null;
			tLCPolDB = null;
			tLCPolSet = null;
			tLPPolSet = null;
		}
		// ===add====zhangtao======增加对公共账户的处理================BGN=================
		double dAddGetMoney = 0.0;
		LCPolDB aLCPolDB = new LCPolDB();
		aLCPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		aLCPolDB.setPolTypeFlag("2");
		LCPolSet aLCPolSet = aLCPolDB.query();
		if (aLCPolSet.size() > 0)// 有公共账户
		{
			String sPubContNo = aLCPolSet.get(1).getContNo();
			String sPubPolNo = aLCPolSet.get(1).getPolNo();
			LPInsureAccTraceSchema tLPInsureAccTraceSchema;
			LPInsureAccTraceSchema pubLPInsureAccTraceSchema;
			for (int i = 1; i <= allLPInsureAccTraceSet.size(); i++) {
				tLPInsureAccTraceSchema = allLPInsureAccTraceSet.get(i);
				if (tLPInsureAccTraceSchema.getPolNo().equals(sPubPolNo)
						&& tLPInsureAccTraceSchema.getMoneyType().equals("PG")) {
					dAddGetMoney += tLPInsureAccTraceSchema.getMoney();

					pubLPInsureAccTraceSchema = tLPInsureAccTraceSchema
							.getSchema();
					String sLimit = PubFun.getNoLimit(tLPInsureAccTraceSchema
							.getManageCom());
					String serNo = PubFun1.CreateMaxNo("SERIALNO", sLimit);
					pubLPInsureAccTraceSchema.setSerialNo(serNo);
					pubLPInsureAccTraceSchema.setMoney(-tLPInsureAccTraceSchema
							.getMoney());
					pubLPInsureAccTraceSchema.setMoneyType("TB");
					pubLPInsureAccTraceSet.add(pubLPInsureAccTraceSchema);
				}
			}

			// 退保扣除手续费后的金额
			int iInterval = PubFun.calInterval(aLCPolSet.get(1).getCValiDate(),
					mLPGrpEdorItemSchema.getEdorAppDate(), "Y");

			EdorCalZT tEdorCalZT = new EdorCalZT();
			double dZTMoneyByAccK = tEdorCalZT.getActuMoney(aLCPolSet.get(1)
					.getRiskCode(), dAddGetMoney, iInterval, "CalFee");
			if (dZTMoneyByAccK == -1) {
				CError.buildErr(this, "手续费计算失败!");
				return false;
			}
			double dSXFee = dAddGetMoney - dZTMoneyByAccK; // 扣除的手续费
			String updSql = " update ljsgetendorse j set j.getmoney = j.getmoney - "
					+ dAddGetMoney
					+ " where j.feeoperationtype = '"
					+ mLPGrpEdorItemSchema.getEdorType()
					+ "' and j.feefinatype = 'TB' and j.subfeeoperationtype = '"
					+ BqCode.Get_BaseCashValue
					+ "' and j.polno = '"
					+ sPubPolNo
					+ "' and j.endorsementno = '"
					+ mLPGrpEdorItemSchema.getEdorNo() + "'";
			mMap.put(updSql, "UPDATE");

			updSql = " update ljsgetendorse j set j.getmoney = j.getmoney + "
					+ dSXFee
					+ " where j.feeoperationtype = '"
					+ mLPGrpEdorItemSchema.getEdorType()
					+ "' and j.feefinatype = 'TB' and j.subfeeoperationtype = '"
					+ BqCode.Pay_TBFee + "' and j.polno = '" + sPubPolNo
					+ "' and j.endorsementno = '"
					+ mLPGrpEdorItemSchema.getEdorNo() + "'";
			mMap.put(updSql, "UPDATE");

			updSql = "update lpedoritem  set getmoney = getmoney - "
					+ dZTMoneyByAccK + " where edorno = '"
					+ mLPGrpEdorItemSchema.getEdorNo() + "' and contno = '"
					+ sPubContNo + "'";
			mMap.put(updSql, "UPDATE");
			mMap.put(pubLPInsureAccTraceSet, "INSERT");
		}
		// ===add====zhangtao======增加对公共账户的处理================END=================
		mLPGrpEdorItemSchema.setEdorState("3");
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");

		// 更新LPGrpEdorItem表GetMoney
		mMap
				.put(
						"update lpgrpedoritem a set getmoney = (select nvl(sum(getmoney),0) from lpedoritem where grpcontno = a.grpcontno and edorno = a.edorno and edortype = a.edortype)"
								+ " where a.edorno = '"
								+ mLPGrpEdorItemSchema.getEdorNo()
								+ "'"
								+ " and grpcontno = '"
								+ mLPGrpEdorItemSchema.getGrpContNo()
								+ "'"
								+ " and a.edortype = '"
								+ mLPGrpEdorItemSchema.getEdorType() + "'",
						"UPDATE");
		return true;
	}

	private MMap deletePreData() {
		MMap map = new MMap();
		String sqlWhere = " grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "' and edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "'";
		map.put("delete from lppol where" + sqlWhere, "DELETE");
		map.put("delete from lpcont where" + sqlWhere, "DELETE");
		map.put("delete from lpinsured where" + sqlWhere, "DELETE");

		map.put("delete from LPInsureAcc where" + sqlWhere, "DELETE");
		map.put("delete from LPInsureAccClass where" + sqlWhere, "DELETE");
		map.put("delete from LPInsureAccTrace where" + sqlWhere, "DELETE");
		map.put("delete from LPEngBonusPol where" + sqlWhere, "DELETE");

		map.put(" delete from LJSGetEndorse " + " where EndorsementNo='"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' and FeeOperationType='"
				+ mLPGrpEdorItemSchema.getEdorType() + "'", "DELETE");

		map.put("delete from lpedormain where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "'", "DELETE");
		map.put("delete from lpedoritem where" + sqlWhere, "DELETE");
		map.put("delete from lpgrppol where" + sqlWhere, "DELETE");

		return map;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
