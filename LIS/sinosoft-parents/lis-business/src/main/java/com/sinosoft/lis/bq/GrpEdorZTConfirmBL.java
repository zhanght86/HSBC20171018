package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEngBonusPolDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.db.LPInsureAccTraceDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LOEngBonusPolSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LOEngBonusPolSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEngBonusPolSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团体保全减人操作
 * </p>
 * 
 * <p>
 * Description: 团体保全减人操作 保全确认类BL层
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
public class GrpEdorZTConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorZTConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mCurrDate = PubFun.getCurrentDate();
	private String mCurrTime = PubFun.getCurrentTime();
	/** 签单调用传入的集体保单号 */
	/** 传入的业务数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	/** 个人分单状态表信息 */
	private LCContStateSet mInstLCContStateSet = new LCContStateSet();
	private LCContStateSet mUpdLCContStateSet = new LCContStateSet();
	/** 续期总减少保费 */
	private double mZTPrem = 0;

	public GrpEdorZTConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		// 数据准备操作（dealData())
		if (!dealData()) {
			return false;
		}

		// 数据准备操作（dealData())
		if (!prepareData()) {
			return false;
		}
		this.setOperate("CONFIRM||ZT");

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
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
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		if (!tLPGrpEdorItemDB.getInfo()) {
			CError.buildErr(this, "查询团险保全项目信息失败!");
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());
		return true;
	}

	private boolean dealData() {
		Reflections tRef = new Reflections();

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1) {
			CError.buildErr(this, "查询减人个人保全信息失败!");
			return false;
		}
		String ContNo = "";

		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			ContNo = tLPEdorItemSet.get(i).getContNo();
			if (!updLCContState(ContNo, "000000", "000000")) {
				return false;
			}

			LCInsureAccSet updLCInsureAccSet = new LCInsureAccSet();
			LPInsureAccSet updLPInsureAccSet = new LPInsureAccSet();
			LCInsureAccClassSet updLCInsureAccClassSet = new LCInsureAccClassSet();
			LPInsureAccClassSet updLPInsureAccClassSet = new LPInsureAccClassSet();
			LCInsureAccTraceSet insLCInsureAccTraceSet = new LCInsureAccTraceSet();

			LPPolDB tLPPolDB = new LPPolDB();
			LPPolSet tLPPolSet = new LPPolSet();
			tLPPolDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPPolDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPPolDB.setContNo(ContNo);
			tLPPolSet = tLPPolDB.query();
			if (tLPPolSet == null || tLPPolSet.size() == 0) {
				CError.buildErr(this, "查询减人的险种信息失败!");
				return false;
			}
			for (int j = 1; j <= tLPPolSet.size(); j++) {
				if (!updLCContState(ContNo, "000000", tLPPolSet.get(j)
						.getPolNo())) {
					return false;
				}

				LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB();
				tLPInsureAccDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tLPInsureAccDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				tLPInsureAccDB.setPolNo(tLPPolSet.get(j).getPolNo());
				LPInsureAccSet tLPInsureAccSet = tLPInsureAccDB.query();
				if (tLPInsureAccDB.mErrors.needDealError()) {
					CError.buildErr(this, "查询保全保险帐户信息失败!");
					return false;
				}
				if (tLPInsureAccSet != null && tLPInsureAccSet.size() > 0) {
					LCInsureAccSchema tLCInsureAccSchema;
					LPInsureAccSchema tLPInsureAccSchema;
					for (int m = 1; m <= tLPInsureAccSet.size(); m++) {
						tLCInsureAccSchema = new LCInsureAccSchema();
						tRef.transFields(tLCInsureAccSchema, tLPInsureAccSet
								.get(m));
						updLCInsureAccSet.add(tLCInsureAccSchema);

						LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
						tLCInsureAccDB.setPolNo(tLCInsureAccSchema.getPolNo());
						tLCInsureAccDB.setInsuAccNo(tLCInsureAccSchema
								.getInsuAccNo());
						LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
						if (tLCInsureAccDB.mErrors.needDealError()) {
							CError.buildErr(this, "查询保全保险帐户信息失败!");
							return false;
						}
						if (tLCInsureAccSet == null
								|| tLCInsureAccSet.size() < 1) {
							CError.buildErr(this, "没有查到保险帐户信息!");
							return false;
						}
						tLPInsureAccSchema = new LPInsureAccSchema();
						tRef.transFields(tLPInsureAccSchema, tLCInsureAccSet
								.get(1));
						tLPInsureAccSchema.setEdorNo(mLPGrpEdorItemSchema
								.getEdorNo());
						tLPInsureAccSchema.setEdorType(mLPGrpEdorItemSchema
								.getEdorType());
						updLPInsureAccSet.add(tLPInsureAccSchema);

						LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();
						tLPInsureAccClassDB.setEdorNo(mLPGrpEdorItemSchema
								.getEdorNo());
						tLPInsureAccClassDB.setEdorType(mLPGrpEdorItemSchema
								.getEdorType());
						tLPInsureAccClassDB.setPolNo(tLPPolSet.get(j)
								.getPolNo());
						tLPInsureAccClassDB.setInsuAccNo(tLCInsureAccSchema
								.getInsuAccNo());
						LPInsureAccClassSet tLPInsureAccClassSet = tLPInsureAccClassDB
								.query();
						if (tLPInsureAccClassDB.mErrors.needDealError()) {
							CError.buildErr(this, "查询保全保险帐户分类信息失败!");
							return false;
						}
						if (tLPInsureAccClassSet == null
								|| tLPInsureAccClassSet.size() < 1) {
							CError.buildErr(this, "没有查到保全保险帐户分类信息!");
							return false;
						} else {
							LCInsureAccClassSchema tLCInsureAccClassSchema;
							LPInsureAccClassSchema tLPInsureAccClassSchema;
							for (int n = 1; n <= tLPInsureAccClassSet.size(); n++) {
								tLCInsureAccClassSchema = new LCInsureAccClassSchema();
								tRef.transFields(tLCInsureAccClassSchema,
										tLPInsureAccClassSet.get(n));
								updLCInsureAccClassSet
										.add(tLCInsureAccClassSchema);

								LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
								tLCInsureAccClassDB
										.setPolNo(tLCInsureAccClassSchema
												.getPolNo());
								tLCInsureAccClassDB
										.setInsuAccNo(tLCInsureAccClassSchema
												.getInsuAccNo());
								tLCInsureAccClassDB
										.setPayPlanCode(tLCInsureAccClassSchema
												.getPayPlanCode());
								tLCInsureAccClassDB
										.setOtherNo(tLCInsureAccClassSchema
												.getOtherNo());
								tLCInsureAccClassDB
										.setAccAscription(tLCInsureAccClassSchema
												.getAccAscription());
								LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB
										.query();
								if (tLCInsureAccClassDB.mErrors.needDealError()) {
									CError.buildErr(this, "查询保全保险帐户分类信息失败!");
									return false;
								}
								if (tLCInsureAccClassSet == null
										|| tLCInsureAccClassSet.size() < 1) {
									CError.buildErr(this, "没有查到保险帐户分类信息!");
									return false;
								}
								tLPInsureAccClassSchema = new LPInsureAccClassSchema();
								tRef.transFields(tLPInsureAccClassSchema,
										tLCInsureAccClassSet.get(1));
								tLPInsureAccClassSchema
										.setEdorNo(mLPGrpEdorItemSchema
												.getEdorNo());
								tLPInsureAccClassSchema
										.setEdorType(mLPGrpEdorItemSchema
												.getEdorType());
								updLPInsureAccClassSet
										.add(tLPInsureAccClassSchema);
							}
						}
						LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
						tLPInsureAccTraceDB.setEdorNo(mLPGrpEdorItemSchema
								.getEdorNo());
						tLPInsureAccTraceDB.setEdorType(mLPGrpEdorItemSchema
								.getEdorType());
						tLPInsureAccTraceDB.setPolNo(tLPPolSet.get(j)
								.getPolNo());
						tLPInsureAccTraceDB.setInsuAccNo(tLCInsureAccSchema
								.getInsuAccNo());
						LPInsureAccTraceSet tLPInsureAccTraceSet = tLPInsureAccTraceDB
								.query();
						if (tLPInsureAccTraceDB.mErrors.needDealError()) {
							CError.buildErr(this, "查询保全保险帐户计价履历信息失败!");
							return false;
						}
						if (tLPInsureAccTraceSet == null
								|| tLPInsureAccTraceSet.size() < 1) {
							CError.buildErr(this, "没有查到保全保险帐户计价履历信息!");
							return false;
						} else {
							LCInsureAccTraceSchema tLCInsureAccTraceSchema;
							for (int k = 1; k <= tLPInsureAccTraceSet.size(); k++) {
								tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
								tRef.transFields(tLCInsureAccTraceSchema,
										tLPInsureAccTraceSet.get(k));
								insLCInsureAccTraceSet
										.add(tLCInsureAccTraceSchema);
							}
						}
					}
				}
			}

			LPEngBonusPolDB tLPEngBonusPolDB = new LPEngBonusPolDB();
			tLPEngBonusPolDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPEngBonusPolDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPEngBonusPolDB.setContNo(ContNo);
			LPEngBonusPolSet tLPEngBonusPolSet = tLPEngBonusPolDB.query();
			if (tLPEngBonusPolDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询保全分红信息失败!");
				return false;
			}
			if (tLPEngBonusPolSet != null && tLPEngBonusPolSet.size() > 0) {
				LOEngBonusPolSet insLOEngBonusPolSet = new LOEngBonusPolSet();
				LOEngBonusPolSchema tLOEngBonusPolSchema;
				for (int k = 1; k <= tLPEngBonusPolSet.size(); k++) {
					tLOEngBonusPolSchema = new LOEngBonusPolSchema();
					tRef.transFields(tLOEngBonusPolSchema, tLPEngBonusPolSet
							.get(k));
					insLOEngBonusPolSet.add(tLOEngBonusPolSchema);
				}
				mMap.put(insLOEngBonusPolSet, "DELETE&INSERT");
			}

			mMap.put(updLCInsureAccSet, "UPDATE");
			mMap.put(updLPInsureAccSet, "UPDATE");
			mMap.put(updLCInsureAccClassSet, "UPDATE");
			mMap.put(updLPInsureAccClassSet, "UPDATE");
			mMap.put(insLCInsureAccTraceSet, "DELETE&INSERT");

			// 处理续期
			if (!dealLJSPay(ContNo)) {
				return false;
			}
		}
		// ===add===zhangtao===2006-08-04===个人未归属部分转移到公共账户里========BGN========
		// 公共账户额外处理，暂时只对轨迹表进行处理
		LCInsureAccTraceSet insPubLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCPolDB aLCPolDB = new LCPolDB();
		aLCPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		aLCPolDB.setPolTypeFlag("2");
		LCPolSet aLCPolSet = aLCPolDB.query();
		if (aLCPolSet != null && aLCPolSet.size() > 0) {
			LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
			tLPInsureAccTraceDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPInsureAccTraceDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPInsureAccTraceDB.setPolNo(aLCPolSet.get(1).getPolNo());
			LPInsureAccTraceSet tLPInsureAccTraceSet = tLPInsureAccTraceDB
					.query();
			if (tLPInsureAccTraceDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询保全保险帐户计价履历信息失败!");
				return false;
			}
			if (tLPInsureAccTraceSet != null && tLPInsureAccTraceSet.size() > 0) {
				LCInsureAccTraceSchema tLCInsureAccTraceSchema;
				for (int k = 1; k <= tLPInsureAccTraceSet.size(); k++) {
					tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
					tRef.transFields(tLCInsureAccTraceSchema,
							tLPInsureAccTraceSet.get(k));
					insPubLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
				}
			}
			mMap.put(insPubLCInsureAccTraceSet, "INSERT");
		}
		// ===add===zhangtao===2006-08-04===个人未归属部分转移到公共账户里========END========
		if (mInstLCContStateSet != null && mInstLCContStateSet.size() > 0) {
			mMap.put(mInstLCContStateSet, "INSERT");
		}

		if (mUpdLCContStateSet != null && mUpdLCContStateSet.size() > 0) {
			mMap.put(mUpdLCContStateSet, "UPDATE");
		}

		if (mZTPrem > 0) {
			String UpDateLJSPay = "update ljspay set sumduepaymoney = sumduepaymoney - "
					+ String.valueOf(mZTPrem)
					+ " where otherno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo() + "'";
			mMap.put(UpDateLJSPay, "UPDATE");
		}

		String ContInfo = "(select contno from lpedoritem where edoracceptno = '"
				+ mLPGrpEdorItemSchema.getEdorAcceptNo()
				+ "'"
				+ " and grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "')";
		String UpLCCont = "update lccont set appflag = '4'," + " operator = '"
				+ mGlobalInput.Operator + "'," + " modifydate = '" + mCurrDate
				+ "'," + " modifytime = '" + mCurrTime + "'"
				+ " where contno in " + ContInfo;
		String UpLCPol = "update lcpol set appflag = '4'," + " operator = '"
				+ mGlobalInput.Operator + "'," + " modifydate = '" + mCurrDate
				+ "'," + " modifytime = '" + mCurrTime + "'"
				+ " where appflag = '1' and contno in " + ContInfo;
		mMap.put(UpLCCont, "UPDATE");
		mMap.put(UpLCPol, "UPDATE");

		// 保存LCGrpCont，LCGrpPol数据
		String EdorInfo = "'" + mLPGrpEdorItemSchema.getEdorNo() + "','"
				+ mLPGrpEdorItemSchema.getEdorType() + "'";
		// String[] tab = {"LCGrpCont","LCGrpPol"};
		String[] tab = { "LCGrpCont" };
		for (int t = 0; t < tab.length; t++) {
			mMap.put("insert into "
					+ tab[t].toUpperCase().replaceFirst("C", "P") + " (select "
					+ EdorInfo + ",a.* from " + tab[t]
					+ " a where grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo() + "')", "INSERT");
		}
		// 修改LCGrpCont投保人数Peoples2
		mMap
				.put(
						"update lcgrpcont a set (peoples2,amnt,prem,mult) = (select nvl(sum(1),0),nvl(sum(amnt),0),nvl(sum(prem),0),nvl(sum(mult),0) from lccont where grpcontno = a.grpcontno and appflag = '1') where a.grpcontno = '"
								+ mLPGrpEdorItemSchema.getGrpContNo() + "'",
						"UPDATE");
		// 修改LCGrpPol投保人数Peoples2
		// mMap.put("update lcgrppol a set (peoples2,amnt,prem,mult) = (select
		// nvl(sum(1),0),nvl(sum(amnt),0),nvl(sum(prem),0),nvl(sum(mult),0) from
		// lcpol where grppolno = a.grppolno and appflag = '1') where
		// a.grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo() +
		// "'","UPDATE");
		// 在明细增加LPGrpPol,确认时候互换
		LCGrpPolSet aLCGrpPolSet = new LCGrpPolSet();
		LPGrpPolSet aLPGrpPolSet = new LPGrpPolSet();

		// 查询P表数据
		LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
		LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
		tLPGrpPolDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpPolDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpPolSet = tLPGrpPolDB.query();
		for (int j = 1; j <= tLPGrpPolSet.size(); j++) {
			// 将P表中数据放到C表中
			LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
			tRef.transFields(tLCGrpPolSchema, tLPGrpPolSet.get(j).getSchema());
			aLCGrpPolSet.add(tLCGrpPolSchema);

			// 查询C表数据
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());

			if (tLCGrpPolDB.getInfo()) {
				// 将C表中数据放到P表中
				LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
				tRef.transFields(tLPGrpPolSchema, tLCGrpPolSchema.getSchema());
				tLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				aLPGrpPolSet.add(tLPGrpPolSchema);
			}
		}

		mMap.put(aLCGrpPolSet, "UPDATE");
		mMap.put(aLPGrpPolSet, "UPDATE");

		mLPGrpEdorItemSchema.setEdorState("0");
		mLPGrpEdorItemSchema.setModifyDate(mCurrDate);
		mLPGrpEdorItemSchema.setModifyTime(mCurrTime);

		return true;
	}

	private boolean dealLJSPay(String aContNo) {
		ExeSQL tExeSQL = new ExeSQL();
		double ContPrem = 0;
		String tSum = tExeSQL
				.getOneValue("select nvl(sum(sumduepaymoney),0) from ljspayperson where contno = '"
						+ aContNo + "'");
		if (tSum != null) {
			ContPrem = Double.parseDouble(tSum);
		}
		if (ContPrem > 0) {
			String delLJSPayPerson = "delete from ljspayperson where contno = '"
					+ aContNo + "'";
			mMap.put(delLJSPayPerson, "DELETE");
			mZTPrem += ContPrem;
		}
		return true;
	}

	private boolean prepareData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	/**
	 * 更新保单状态表
	 */
	private boolean updLCContState(String sContNo, String sInsuredNo,
			String sPolNo) {
		String sStateReason = "02"; // 02-退保终止
		if (mLPGrpEdorItemSchema.getEdorType().equals("EA")) {
			sStateReason = "03"; // 03-解约终止
		}

		String sql = " select * from lccontstate where trim(statetype) = 'Terminate' "
				+ " and enddate is null  and contno = '" + sContNo +
				// "' and insuredno = '" + sInsuredNo +
				"' and polno = '" + sPolNo + "'";

		LCContStateSchema tLCContStateSchema = new LCContStateSchema();

		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = tLCContStateDB.executeQuery(sql);
		if (tLCContStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单状态查询失败!");
			return false;
		}
		if (tLCContStateSet == null || tLCContStateSet.size() < 1) {
			// 没有记录
			tLCContStateSchema.setContNo(sContNo);
			tLCContStateSchema.setInsuredNo("000000");
			tLCContStateSchema.setPolNo(sPolNo);
			tLCContStateSchema.setStateType("Terminate");
			tLCContStateSchema.setStateReason("02");
			tLCContStateSchema.setState("1");
			tLCContStateSchema.setStartDate(mLPGrpEdorItemSchema
					.getEdorValiDate());
			// tLCContStateSchema.setEndDate();
			tLCContStateSchema.setOperator(mGlobalInput.Operator);
			tLCContStateSchema.setMakeDate(mCurrDate);
			tLCContStateSchema.setMakeTime(mCurrTime);
			tLCContStateSchema.setModifyDate(mCurrDate);
			tLCContStateSchema.setModifyTime(mCurrTime);
			mInstLCContStateSet.add(tLCContStateSchema);
		} else {
			String sEndDate = PubFun.calDate(mLPGrpEdorItemSchema
					.getEdorValiDate(), -1, "D", null);
			tLCContStateSchema = tLCContStateSet.get(1);
			tLCContStateSchema.setEndDate(sEndDate);
			tLCContStateSchema.setModifyDate(mCurrDate);
			tLCContStateSchema.setModifyTime(mCurrTime);
			mUpdLCContStateSet.add(tLCContStateSchema);

			tLCContStateSchema = new LCContStateSchema();
			tLCContStateSchema.setContNo(sContNo);
			tLCContStateSchema.setInsuredNo("000000");
			tLCContStateSchema.setPolNo(sPolNo);
			tLCContStateSchema.setStateType("Terminate");
			tLCContStateSchema.setStateReason("02");
			tLCContStateSchema.setState("1");
			tLCContStateSchema.setStartDate(mLPGrpEdorItemSchema
					.getEdorValiDate());
			// tLCContStateSchema.setEndDate();
			tLCContStateSchema.setOperator(mGlobalInput.Operator);
			tLCContStateSchema.setMakeDate(mCurrDate);
			tLCContStateSchema.setMakeTime(mCurrTime);
			tLCContStateSchema.setModifyDate(mCurrDate);
			tLCContStateSchema.setModifyTime(mCurrTime);
			mInstLCContStateSet.add(tLCContStateSchema);
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public static void main(String[] args) {
		GrpEdorZTConfirmBL grpedorztconfirmbl = new GrpEdorZTConfirmBL();
	}
}
