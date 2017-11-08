package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LOEngBonusPolSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPEngBonusPolSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LOEngBonusPolSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPEngBonusPolSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团体保全减人操作
 * </p>
 * 
 * <p>
 * Description: 团体保全减人操作 保全明细BL层接口
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 */
public class GrpEdorZTDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorZTDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局业务数据变量 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private LPContSet mLPContSet = new LPContSet();

	/**
	 * 团体保全减人操作外部接口函数 aOperator: ZT||INSERT 减人业务操作 ZT||DELETE 撤销减人操作
	 * 
	 * @param aInputData
	 * @param aOperator
	 * @return
	 */
	public boolean submitData(VData aInputData, String aOperator) {
		mInputData = (VData) aInputData.clone();
		mOperate = aOperator;

		if (!getInputData()) {
			return false;
		}

		if (mOperate == null || mOperate.equals("")) {
			CError.buildErr(this, "输入的数据操作字符为空!");
			return false;
		} else if (mOperate.equals("ZT||DELETE")) {
			// 撤销减人操作
			if (!deleteData()) {
				return false;
			}
		} else if (mOperate.equals("ZT||INSERT")) {
			// 减人业务操作
			if (!dealData()) {
				return false;
			}
		} else {
			CError.buildErr(this, "输入的数据操作字符没有定义!");
			return false;
		}

		if (!prepareData()) {
			return false;
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		boolean Errorflag = false;
		try {
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
			mLPContSet = (LPContSet) mInputData.getObjectByObjectName(
					"LPContSet", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			if (mLPContSet == null || mLPContSet.size() == 0) {
				LPContSchema tLPContSchema = (LPContSchema) mInputData
						.getObjectByObjectName("LPContSchema", 0);
				if (tLPContSchema == null)
					Errorflag = true;
				else {
					mLPContSet = new LPContSet();
					mLPContSet.add(tLPContSchema);
				}
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorZTDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (Errorflag || mLPGrpEdorItemSchema == null || mGlobalInput == null) {
			CError.buildErr(this, "接收数据不完整!");
			return false;
		}

		String EdorReasonCode = mLPGrpEdorItemSchema.getEdorReasonCode();
		String EdorReason = mLPGrpEdorItemSchema.getEdorReason();
		EdorReasonCode = "";

		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		if (!tLPGrpEdorItemDB.getInfo()) {
			CError.buildErr(this, "查询团体保全项目信息失败!");
			return false;
		}
		mLPGrpEdorItemSchema = tLPGrpEdorItemDB.getSchema();

		if ("G&Detail".equals(mOperate))
			mOperate = "ZT||INSERT";

		if ("ZT||INSERT".equals(mOperate)) {
			mLPGrpEdorItemSchema.setEdorReasonCode(EdorReasonCode);
			mLPGrpEdorItemSchema.setEdorReason(EdorReason);
			// 更新所有个人批改项目表中的减人原因
			mMap.put("update lpedoritem set EdorReasonCode = '"
					+ EdorReasonCode + "',EdorReason = '" + EdorReason
					+ "' where edorno = '" + mLPGrpEdorItemSchema.getEdorNo()
					+ "' and grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo() + "'", "UPDATE");
		}

		return true;
	}

	/**
	 * 撤销减人操作，即删除产生的P表数据
	 * 
	 * @return boolean
	 */
	private boolean deleteData() {
		String EdorInfo = " and EdorType = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "'" + " and EdorNo = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "'";
		for (int i = 1; i <= mLPContSet.size(); i++) {
			String delLPCont = EdorInfo + " and ContNo = '"
					+ mLPContSet.get(i).getContNo() + "'";
			mMap.put("delete from lpcont where 1=1" + delLPCont, "DELETE");
			mMap.put("delete from lppol where 1=1" + delLPCont, "DELETE");
			mMap.put("delete from lpedoritem where 1=1" + delLPCont, "DELETE");
			mMap.put("delete from lpedormain where contno = '"
					+ mLPContSet.get(i).getContNo() + "' and edorno = '"
					+ mLPGrpEdorItemSchema.getEdorNo() + "'", "DELETE");
			mMap.put(
					"delete from ljsgetendorse where 1=1 and endorsementno = '"
							+ mLPGrpEdorItemSchema.getEdorNo() + "'"
							+ " and contno = '" + mLPContSet.get(i).getContNo()
							+ "'", "DELETE");

			mMap
					.put("delete from LPInsureAcc where 1=1 " + delLPCont,
							"DELETE");
			mMap.put("delete from LPInsureAccClass where 1=1 " + delLPCont,
					"DELETE");
			mMap.put("delete from LPInsureAccTrace where 1=1 " + delLPCont,
					"DELETE");
			mMap
					.put(
							"delete from LPInsureAccTrace where 1=1 and contno <> '"
									+ mLPContSet.get(i).getContNo()
									+ "' and accalterno in (select polno from lcpol where polno = mainpolno and contno = '"
									+ mLPContSet.get(i).getContNo() + "')"
									+ EdorInfo, "DELETE");
			mMap.put("delete from LPEngBonusPol where 1=1 " + delLPCont,
					"DELETE");
		}

		String UpGrpEdorItem = " update lpgrpedoritem a set a.edorstate = '3'"
				+ " where exists(select 1 from dual where (select count(*) from lpedoritem where edoracceptno = a.edoracceptno and edorno = a.edorno) = 0)"
				+ " and a.edoracceptno = '"
				+ mLPGrpEdorItemSchema.getEdorAcceptNo() + "'";
		mMap.put(UpGrpEdorItem, "UPDATE");

		String UpGrpEdorItemGetMoney = " update lpgrpedoritem a set a.getmoney = (select nvl(sum(getmoney),0) from lpedoritem where edoracceptno = a.edoracceptno and edorno = a.edorno) "
				+ " where a.edoracceptno = '"
				+ mLPGrpEdorItemSchema.getEdorAcceptNo() + "'";
		mMap.put(UpGrpEdorItemGetMoney, "UPDATE");
		mMap
				.put(
						"update lpgrpedormain a set getmoney = (select nvl(sum(getmoney),0) from lpgrpedoritem where edoracceptno = a.edoracceptno and edorno = a.edorno) where a.edoracceptno = '"
								+ mLPGrpEdorItemSchema.getEdorAcceptNo() + "'",
						"UPDATE");
		mMap
				.put(
						"update lpedorapp a set getmoney = (select nvl(sum(getmoney),0) from lpgrpedormain where edoracceptno = a.edoracceptno) where a.edoracceptno = '"
								+ mLPGrpEdorItemSchema.getEdorAcceptNo() + "'",
						"UPDATE");

		// mLPGrpEdorItemSchema.setEdorState("3");
		// mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		// mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		// mMap.put(mLPGrpEdorItemSchema,"UPDATE");
		return true;
	}

	private boolean dealData() {
		String CurrDate = PubFun.getCurrentDate();
		String CurrTime = PubFun.getCurrentTime();

		Reflections tRef = new Reflections();
		LPEdorMainSet cLPEdorMainSet = new LPEdorMainSet();
		LPEdorMainSchema cLPEdorMainSchema;
		LPEdorItemSet cLPEdorItemSet = new LPEdorItemSet();
		LPEdorItemSchema cLPEdorItemSchema;

		EdorCalZT tEdorCalZT;
		LJSGetEndorseSet cLJSGetEndorseSet = new LJSGetEndorseSet();
		LJSGetEndorseSchema cLJSGetEndorseSchema;

		LPPolSet aLPPolSet = new LPPolSet();
		LPContSchema tLPContSchema;
		LPContSet aLPContSet = new LPContSet();

		LOEngBonusPolSet tLOEngBonusPolSet = new LOEngBonusPolSet();
		LPEngBonusPolSet tLPEngBonusPolSet = new LPEngBonusPolSet();

		VData rVData = new VData();

		LCInsureAccSet rLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccClassSet rLCInsureAccClassSet = new LCInsureAccClassSet();
		LCInsureAccTraceSet rLCInsureAccTraceSet = new LCInsureAccTraceSet();

		LPInsureAccSet tLPInsureAccSet = new LPInsureAccSet();
		LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
		LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();
		LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
		LPInsureAccTraceSet tLPInsureAccTraceSet = new LPInsureAccTraceSet();
		LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();

		String tContNo;
		String tInsuredNo;
		double AllGetMoney = 0;
		for (int i = 1; i <= mLPContSet.size(); i++) {
			tContNo = mLPContSet.get(i).getContNo();
			tInsuredNo = mLPContSet.get(i).getInsuredNo();

			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(tContNo);
			if (!tLCContDB.getInfo()) {
				CError.buildErr(this, "查询个险分单信息失败!");
				return false;
			}
			tLPContSchema = new LPContSchema();
			tRef.transFields(tLPContSchema, tLCContDB.getSchema());
			tLPContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			// tLPContSchema.setOperator(mGlobalInput.Operator);
			tLPContSchema.setModifyDate(CurrDate);
			tLPContSchema.setModifyTime(CurrTime);
			aLPContSet.add(tLPContSchema);

			// TransferData tTransferData = new TransferData();
			// tTransferData.setNameAndValue("ContNo",tContNo);
			// tTransferData.setNameAndValue("InsuredNo",tInsuredNo);
			//
			// if(!CheckInsured(tTransferData,"","ZT","000000")){
			// logger.debug("此被保人不满足减人操作，被保人号：" + tInsuredNo);
			// continue;
			// }

			cLPEdorItemSchema = new LPEdorItemSchema();
			tRef.transFields(cLPEdorItemSchema, mLPGrpEdorItemSchema);
			cLPEdorItemSchema.setInsuredNo(tInsuredNo);
			cLPEdorItemSchema.setContNo(tContNo);

			AddGEdorItemCheckBL tAddGEdorItemCheckBL = new AddGEdorItemCheckBL();
			LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
			tLPEdorItemSet.add(cLPEdorItemSchema);
			if (!tAddGEdorItemCheckBL.canAddPEdorItem(tLPEdorItemSet)) {
				CError.buildErr(this, tInsuredNo + " "
						+ tAddGEdorItemCheckBL.getErrors().getFirstError());
				return false;
			}

			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setContNo(tContNo);
			tLCPolDB.setInsuredNo(tInsuredNo);
			tLCPolDB.setAppFlag("1");
			LCPolSet tLCPolSet = tLCPolDB.query();
			if (tLCPolSet == null || tLCPolSet.size() < 1) {
				CError.buildErr(this, "查询此被保人有效险种失败!被保人客户号：" + tInsuredNo);
				return false;
			}

			double ZTMoney = 0;
			LCPolSchema tLCPolSchema;
			LPPolSchema tLPPolSchema;

			for (int j = 1; j <= tLCPolSet.size(); j++) {
				tLCPolSchema = tLCPolSet.get(j);
				cLPEdorItemSchema.setPolNo(tLCPolSchema.getPolNo());
				double tGetMoney = 0;
				tEdorCalZT = new EdorCalZT(mGlobalInput);
				tGetMoney = tEdorCalZT.calZTData(cLPEdorItemSchema);
				if (tGetMoney == -1) {
					mErrors.copyAllErrors(tEdorCalZT.mErrors);
					CError.buildErr(this, "计算减人退费失败!");
					return false;
				}

				if (tGetMoney > 0)
					tGetMoney = -tGetMoney;
				cLJSGetEndorseSet.add(tEdorCalZT.getLJSGetEndorseSet());

				double PoundAge = this.CalPoundAge(-tGetMoney, tLCPolSchema
						.getRiskCode());
				if (PoundAge == -1) {
					CError.buildErr(this, "计算手续费失败!");
					return false;
				}
				if (PoundAge > 0) {
					cLJSGetEndorseSchema = new LJSGetEndorseSchema();
					cLJSGetEndorseSchema = this.initLJSGetEndorse(tLCPolSchema,
							BqCode.Pay_TBFee, "TB", PoundAge);
					if (cLJSGetEndorseSchema == null) {
						return false;
					}
					cLJSGetEndorseSet.add(cLJSGetEndorseSchema);
					tGetMoney += PoundAge;
				}

				ZTMoney += tGetMoney;

				tLPPolSchema = new LPPolSchema();
				tRef.transFields(tLPPolSchema, tLCPolSchema);
				tLPPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tLPPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				tLPPolSchema.setOperator(mGlobalInput.Operator);
				tLPPolSchema.setModifyDate(CurrDate);
				tLPPolSchema.setModifyTime(CurrTime);
				aLPPolSet.add(tLPPolSchema);

				rVData.clear();
				rVData = tEdorCalZT.getResult();

				// 分红结果
				tLOEngBonusPolSet = (LOEngBonusPolSet) rVData
						.getObjectByObjectName("LOEngBonusPolSet", 0);
				if (tLOEngBonusPolSet != null && tLOEngBonusPolSet.size() > 0) {
					LOEngBonusPolSchema tLOEngBonusPolSchema;
					LPEngBonusPolSchema tLPEngBonusPolSchema;
					for (int m = 1; m <= tLOEngBonusPolSet.size(); m++) {
						tLOEngBonusPolSchema = tLOEngBonusPolSet.get(m);
						tLPEngBonusPolSchema = new LPEngBonusPolSchema();
						tRef.transFields(tLPEngBonusPolSchema,
								tLOEngBonusPolSchema);
						tLPEngBonusPolSchema.setEdorNo(mLPGrpEdorItemSchema
								.getEdorNo());
						tLPEngBonusPolSchema.setEdorType(mLPGrpEdorItemSchema
								.getEdorType());
						tLPEngBonusPolSet.add(tLPEngBonusPolSchema);
					}
				}

				// 万能险帐户结算结果
				rLCInsureAccSet = (LCInsureAccSet) rVData
						.getObjectByObjectName("LCInsureAccSet", 0);
				rLCInsureAccClassSet = (LCInsureAccClassSet) rVData
						.getObjectByObjectName("LCInsureAccClassSet", 0);
				rLCInsureAccTraceSet = (LCInsureAccTraceSet) rVData
						.getObjectByObjectName("LCInsureAccTraceSet", 0);

				if (rLCInsureAccSet != null && rLCInsureAccSet.size() > 0) {
					for (int m = 1; m <= rLCInsureAccSet.size(); m++) {
						tLPInsureAccSchema = new LPInsureAccSchema();
						tRef.transFields(tLPInsureAccSchema, rLCInsureAccSet
								.get(m));
						tLPInsureAccSchema.setEdorNo(mLPGrpEdorItemSchema
								.getEdorNo());
						tLPInsureAccSchema.setEdorType(mLPGrpEdorItemSchema
								.getEdorType());
						tLPInsureAccSchema.setInsuAccBala(0); // 退保后余额置为0
						tLPInsureAccSet.add(tLPInsureAccSchema);
					}
				}

				if (rLCInsureAccClassSet != null
						&& rLCInsureAccClassSet.size() > 0) {
					for (int n = 1; n <= rLCInsureAccClassSet.size(); n++) {
						tLPInsureAccClassSchema = new LPInsureAccClassSchema();
						tRef.transFields(tLPInsureAccClassSchema,
								rLCInsureAccClassSet.get(n));
						tLPInsureAccClassSchema.setEdorNo(mLPGrpEdorItemSchema
								.getEdorNo());
						tLPInsureAccClassSchema
								.setEdorType(mLPGrpEdorItemSchema.getEdorType());
						tLPInsureAccClassSchema.setInsuAccBala(0); // 退保后余额置为0
						tLPInsureAccClassSet.add(tLPInsureAccClassSchema);
					}
				}

				if (rLCInsureAccTraceSet != null
						&& rLCInsureAccTraceSet.size() > 0) {
					for (int k = 1; k <= rLCInsureAccTraceSet.size(); k++) {
						tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
						tRef.transFields(tLPInsureAccTraceSchema,
								rLCInsureAccTraceSet.get(k));
						tLPInsureAccTraceSchema.setEdorNo(mLPGrpEdorItemSchema
								.getEdorNo());
						tLPInsureAccTraceSchema
								.setEdorType(mLPGrpEdorItemSchema.getEdorType());
						tLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);
					}

				}
			}

			AllGetMoney += ZTMoney;

			cLPEdorItemSchema.setPolNo("000000");
			cLPEdorItemSchema.setGetMoney(ZTMoney);
			cLPEdorItemSchema.setEdorState("1");
			cLPEdorItemSchema.setMakeDate(CurrDate);
			cLPEdorItemSchema.setMakeTime(CurrTime);
			cLPEdorItemSchema.setModifyDate(CurrDate);
			cLPEdorItemSchema.setModifyTime(CurrTime);
			cLPEdorItemSet.add(cLPEdorItemSchema);

			cLPEdorMainSchema = new LPEdorMainSchema();
			tRef.transFields(cLPEdorMainSchema, mLPGrpEdorItemSchema);
			tRef.transFields(cLPEdorMainSchema, cLPEdorItemSchema);
			cLPEdorMainSet.add(cLPEdorMainSchema);
		}

		mLPGrpEdorItemSchema.setGetMoney(AllGetMoney);
		mLPGrpEdorItemSchema.setEdorState("3");
		mLPGrpEdorItemSchema.setModifyDate(CurrDate);
		mLPGrpEdorItemSchema.setModifyTime(CurrTime);
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");

		mMap.put(aLPContSet, "DELETE&INSERT");
		mMap.put(aLPPolSet, "DELETE&INSERT");
		mMap.put(tLPInsureAccSet, "DELETE&INSERT");
		mMap.put(tLPInsureAccClassSet, "DELETE&INSERT");
		mMap.put(tLPInsureAccTraceSet, "DELETE&INSERT");
		mMap.put(tLPEngBonusPolSet, "DELETE&INSERT");
		mMap.put(cLJSGetEndorseSet, "DELETE&INSERT");
		mMap.put(cLPEdorItemSet, "DELETE&INSERT");
		mMap.put(cLPEdorMainSet, "DELETE&INSERT");
		// ---add---zhangtao---2006-11-1---为了满足人工核保需要，将LCGrpPol中数据复制到LPGrpPol--------BGN-------
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLCGrpPolDB.setAppFlag("1");
		LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();

		LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
		tRef = new Reflections();
		for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
			LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
			tRef.transFields(tLPGrpPolSchema, tLCGrpPolSet.get(i));
			tLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPGrpPolSchema.setModifyDate(CurrDate);
			tLPGrpPolSchema.setModifyTime(CurrTime);
			tLPGrpPolSet.add(tLPGrpPolSchema);
		}

		String sql = "delete from lpgrppol where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' and grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "'";
		mMap.put(sql, "DELETE");

		mMap.put(tLPGrpPolSet, "INSERT");

		// 更新团体险种表表更后的保费、保额、份数
		sql = " update lpgrppol g set "
				+ " prem = prem - (select sum(prem) from lppol p where p.grppolno = g.grppolno and p.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' and p.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and p.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "'), "
				+ " amnt = amnt - (select sum(amnt) from lppol p where p.grppolno = g.grppolno and p.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' and p.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and p.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "'), "
				+ " mult = mult - (select sum(p.mult) from lppol p where p.grppolno = g.grppolno and p.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' and p.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and p.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "'), "
				+ " peoples2 = peoples2 - (select sum(1) from lppol p where p.grppolno = g.grppolno and p.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' and p.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and p.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "') "
				+ " where g.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' "
				+ " and g.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and g.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' ";
		mMap.put(sql, "UPDATE");
		// ---add---zhangtao---2006-11-1---为了满足人工核保需要，将LCGrpPol中数据复制到LPGrpPol--------END-------

		// 更新 LPGrpEdorItem : XinYQ added on 2006-11-24
		String UpdateSQL = new String("");
		UpdateSQL = "update LPGrpEdorItem " + "set GetMoney = "
				+ "(select nvl(sum(GetMoney), 0) " + "from LPEdorItem "
				+ "where 1 = 1 " + "and EdorNo = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' "
				+ "and EdorType = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' "
				+ "and GrpContNo = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "'), "
				+ "ModifyDate = '"
				+ CurrDate
				+ "', "
				+ "ModifyTime = '"
				+ CurrTime
				+ "' "
				+ "where 1 = 1 "
				+ "and EdorNo = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' "
				+ "and EdorType = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' "
				+ "and GrpContNo = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "'";
		// logger.debug(UpdateSQL);
		mMap.put(UpdateSQL, "UPDATE");

		return true;
	}

	/**
	 * 校验此被保人是否能进行减人操作
	 * 
	 * @param aTransferData
	 *            检验所需的计算要素
	 * @param aCalType
	 * @param aEdorType
	 * @param aRiskCode
	 * @return
	 */
	// private boolean CheckInsured(TransferData aTransferData, String aCalType,
	// String aEdorType,String aRiskCode)
	// {
	// Vector tVector = new Vector();
	// tVector = aTransferData.getValueNames();
	// if(tVector == null || tVector.size() == 0){
	// return true;
	// }
	// Calculator tCalculator = new Calculator();
	// for(int i = 0; i < tVector.size(); i++){
	// String tFactorName = new String();
	// String tFactorValue = new String();
	// tFactorName = (String) tVector.get(i);
	// tFactorValue = (String) aTransferData.getValueByName(
	// tFactorName);
	// tCalculator.addBasicFactor(tFactorName, tFactorValue);
	// }
	//
	// LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
	// LMEdorCalSet tLMEdorCalSet = new LMEdorCalSet();
	// tLMEdorCalDB.setCalType(aCalType);
	// tLMEdorCalDB.setEdorType(aEdorType);
	// tLMEdorCalDB.setRiskCode(aRiskCode);
	// tLMEdorCalSet = tLMEdorCalDB.query();
	// if(tLMEdorCalSet == null || tLMEdorCalSet.size() < 1){
	// return true;
	// }
	//
	// for(int i = 1; i <= tLMEdorCalSet.size(); i++){
	// tCalculator.setCalCode(tLMEdorCalSet.get(i).getCalCode());
	// String tValue = tCalculator.calculate();
	// if(!"1".equals(tValue)){
	// return false;
	// }
	// }
	//
	// return true;
	// }
	/**
	 * 生成批改补退费信息
	 * 
	 * @param aLCPolSchema
	 * @param aOperationType
	 * @param financeType
	 * @param GetMoney
	 * @return
	 */
	private LJSGetEndorseSchema initLJSGetEndorse(LCPolSchema aLCPolSchema,
			String aOperationType, String financeType, double GetMoney) {
		String aFeeType = BqCalBL.getFinType(
				mLPGrpEdorItemSchema.getEdorType(), financeType, aLCPolSchema
						.getPolNo());
		if (aFeeType == null || aFeeType.equals("")) {
			CError.buildErr(this, "查询财务类型失败!");
			return null;
		}
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		Reflections tRef = new Reflections();
		tRef.transFields(tLJSGetEndorseSchema, aLCPolSchema);
		tLJSGetEndorseSchema.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo()); // 给付通知书号码
		tLJSGetEndorseSchema.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setGetDate(mLPGrpEdorItemSchema.getEdorValiDate());
		tLJSGetEndorseSchema.setGetMoney(GetMoney);
		tLJSGetEndorseSchema.setFeeOperationType(mLPGrpEdorItemSchema
				.getEdorType());
		tLJSGetEndorseSchema.setSubFeeOperationType(aOperationType); // 补退费子业务类型
		tLJSGetEndorseSchema.setFeeFinaType(aFeeType); // 补退费财务类型
		tLJSGetEndorseSchema.setPayPlanCode("0"); // 无作用
		tLJSGetEndorseSchema.setDutyCode("0"); // 无作用，但一定要，转ljagetendorse时非空
		tLJSGetEndorseSchema.setOtherNo(mLPGrpEdorItemSchema.getEdorNo()); // 其他号码置为保全批单号
		tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
		tLJSGetEndorseSchema.setGetFlag("0");
		tLJSGetEndorseSchema.setOperator(mLPGrpEdorItemSchema.getOperator());
		tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
		tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
		tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
		tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());

		return tLJSGetEndorseSchema;
	}

	/**
	 * 计算险种退保金的手续费
	 * 
	 * @param Refundment
	 * @param RiskCode
	 * @return
	 */
	private double CalPoundAge(double Refundment, String RiskCode) {
		double PoundAge = 0;
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(RiskCode);
		tLMEdorCalDB.setDutyCode("000000");
		tLMEdorCalDB.setCalType("PoundAge");
		tLMEdorCalDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		if (!tLMEdorCalDB.getInfo()) {
			logger.debug("No PoundAge!");
			return PoundAge;
		}

		Calculator tCal = new Calculator();
		tCal.setCalCode(tLMEdorCalDB.getCalCode());
		tCal.addBasicFactor("GetMoney", String.valueOf(Refundment));
		try {
			String aResult = tCal.calculate();
			PoundAge = Double.parseDouble(aResult);
		} catch (Exception e) {
			CError.buildErr(this, "计算异常!");
			return -1;
		}
		return PoundAge;
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

	// public static void main(String[] args) {
	// GlobalInput tGI = new GlobalInput();
	// tGI.Operator = "lee";
	// tGI.ManageCom = "86";
	// LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	// tLPGrpEdorItemSchema.decode("6120060221000002|6020060223000001||ZT||240110000000243|||||||||0|0|0|0||||||||||||");
	// LPContSet tLPContSet = new LPContSet();
	// tLPContSet.decode("|||000000026327||||||||||||||||||||||||0000607730||||||0|||||||||||||0|0||||0|0|0|0|0|0|||||||||||||||||||||||||||||||||||||||||");
	// VData tVD = new VData();
	// tVD.add(tGI);
	// tVD.add(tLPGrpEdorItemSchema);
	// tVD.add(tLPContSet);
	// GrpEdorZTDetailBL tGrpEdorZTDetailBL = new GrpEdorZTDetailBL();
	// tGrpEdorZTDetailBL.submitData(tVD,"ZT||INSERT");
	// }
}
