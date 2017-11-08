package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.lis.vschema.LPReturnLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统保全申请确认变动功能部分
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft< /p>
 * 
 * @author Tjj
 * @version 1.0
 */
public class GEdorAppConfirmBL {
private static Logger logger = Logger.getLogger(GEdorAppConfirmBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	// 操作符
	public String mOperate;

	/** 全局数据 */
	private MMap map = new MMap();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
	private LPGrpEdorItemSet mLPGrpEdorItemSet = new LPGrpEdorItemSet();
	private LPGrpEdorMainSet tLPGrpEdorMainSet = new LPGrpEdorMainSet();
	private LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();
	private LJSGetEndorseSet outLJSGetEndorseSet = new LJSGetEndorseSet();
	private LPReturnLoanSet outLPReturnLoanSet = new LPReturnLoanSet();
	private Reflections mReflections = new Reflections();

	String mPayPrintParams = "";

	public GEdorAppConfirmBL() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start GrpEdorAppConfirm BLS Submit...");

		if (!prepareData()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		// if (!getPrintData())
		// {
		// return false;
		// }

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		// 数据提交、保存

		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		mInputData = null;
		return true;
	}

	/**
	 * 普通保全项目对集体单操作
	 * 
	 * @return
	 */
	private boolean prepareData() {

		LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();

		LPGrpEdorItemDB aLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorMainSchema = (LPGrpEdorMainSchema) mInputData
				.getObjectByObjectName("LPGrpEdorMainSchema", 0);
		mLPGrpEdorMainSchema.setSchema(tLPGrpEdorMainSchema);
		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		tLPGrpEdorMainDB.setSchema(tLPGrpEdorMainSchema);
		tLPGrpEdorMainDB.getInfo();
		if (tLPGrpEdorMainDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询团体批单主表时失败");
			return false;
		}
		tLPGrpEdorMainSchema.setSchema(tLPGrpEdorMainDB);
		mLPGrpEdorMainSchema.setSchema(tLPGrpEdorMainDB);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mLPGrpEdorItemSet.clear();
		aLPGrpEdorItemDB.setGrpContNo(tLPGrpEdorMainSchema.getGrpContNo());
		aLPGrpEdorItemDB.setEdorNo(tLPGrpEdorMainSchema.getEdorNo());
		aLPGrpEdorItemDB.setEdorState("1");

		mLPGrpEdorItemSet = aLPGrpEdorItemDB.query();
		if (aLPGrpEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询团体批单项目表时失败");
			return false;
		}

		logger.debug("mLPGrpEdorItemSet size :"
				+ mLPGrpEdorItemSet.size());
		return true;
	}

	/**
	 * 生成打印数据
	 * 
	 * @return
	 */

	private boolean getPrintData() {
		// remark by PQ
		try {
			logger.debug("start 生成打印数据...");
			VData tVData = new VData();
			// LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
			// tLPGrpEdorMainSchema.setGrpContNo(mLPGrpEdorMainSchema.getGrpContNo());
			tVData.addElement(mLPGrpEdorMainSchema);
			tVData.addElement(mGlobalInput);

			PrtGrpEndorsementBL tGrpPrtEndorsementBL = new PrtGrpEndorsementBL(
					mLPGrpEdorMainSchema.getEdorNo());
			if (!tGrpPrtEndorsementBL.submitData(tVData, "")) {
				mErrors.copyAllErrors(tGrpPrtEndorsementBL.mErrors);
				return false;
			}
			MMap tmap = new MMap();
			tVData = tGrpPrtEndorsementBL.getResult();
			tmap = (MMap) tVData.getObjectByObjectName("MMap", 0);
			if (tmap != null) {
				map.add(tmap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "生成打印数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 普通保全项目对集体单操作
	 * 
	 * @return
	 */
	private boolean dealData() {
		ExeSQL aExeSQL = new ExeSQL();
		String tEdorNo = mLPGrpEdorMainSchema.getEdorNo();
		String tGrpContNo = mLPGrpEdorMainSchema.getGrpContNo();
		// 按照保全项目来进行确认（tjj chg 1024）
		for (int i = 1; i <= mLPGrpEdorItemSet.size(); i++) {

			LPGrpEdorItemSchema iLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
			iLPGrpEdorItemSchema.setSchema(mLPGrpEdorItemSet.get(i));

			String tEdorType = iLPGrpEdorItemSchema.getEdorType();
			// 检查个单是否全部通过计算
			String sql = "select count(*) from LPEdorItem where GrpContNo='"
					+ "?tGrpContNo?" + "' and EdorNo='" + "?tEdorNo?"
					+ "' and EdorType='" + "?tEdorType?" + "' and EdorState ='1'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("tGrpContNo", tGrpContNo);
			sqlbv.put("tEdorNo", tEdorNo);
			sqlbv.put("tEdorType", tEdorType);
			String aReturn = aExeSQL.getOneValue(sqlbv);
			if (aExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "查询批单号为" + tEdorNo + ",项目为" + tEdorType
						+ "下个单保全项目时失败");
				return false;
			}

			int aCount;
			aCount = 100;

			if ((aReturn == null) || aReturn.trim().equals("0")) {
				aCount = 0;
			} else {
				aCount = Integer.parseInt(aReturn);
			}

			logger.debug("aCount :" + aCount);

			if (aCount == 0) {

				iLPGrpEdorItemSchema.setEdorState("2");
				iLPGrpEdorItemSchema.setUWFlag("0");
				// iLPGrpEdorItemSchema.setApproveDate(PubFun.getCurrentDate());
				// iLPGrpEdorItemSchema.setApproveCode(mGlobalInput.Operator);

				// mLPGrpEdorMainSchema.setSchema(iLPGrpEdorItemSchema);

				// 按照保全项目对集体单进行特殊处理
				logger.debug("edortype:" + tEdorType);
				try {
					Class tClass = Class.forName("com.sinosoft.lis.bq.GrpEdor"
							+ tEdorType + "AppConfirmBL");
					EdorAppConfirm tGrpEdorAppConfirm = (EdorAppConfirm) tClass
							.newInstance();
					VData tVData = new VData();

					tVData.add(iLPGrpEdorItemSchema);
					tVData.add(mLJSGetEndorseSchema);
					tVData.add(mGlobalInput);
					tVData.add(tLPGrpEdorMainSet);

					if (!tGrpEdorAppConfirm.submitData(tVData, "APPCONFIRM||"
							+ tEdorType)) {
						/*
						 * CError.buildErr(this, "保全项目" + tEdorType +
						 * "申请确认时失败！失败原因：" + tGrpEdorAppConfirm.mErrors.
						 * getFirstError());
						 */

						return false;
					} else {
						VData rVData = tGrpEdorAppConfirm.getResult();
						MMap tMap = new MMap();
						tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
						if (tMap == null) {
							CError.buildErr(this, "得到保全项目为:" + tEdorType
									+ "的保全申请确认结果时失败！");
							return false;

						} else {
							map.add(tMap);
						}
					}

				} catch (ClassNotFoundException ex) {
					map.put(iLPGrpEdorItemSchema, "UPDATE");
					String wherePart = "where EdorNo='" + "?tEdorNo?"
							+ "' and EdorType='" + "?tEdorType?" + "'";
					SQLwithBindVariables sbv1=new SQLwithBindVariables();
					sbv1.sql("update LPGrpEdorItem set ChgPrem= (select sum(ChgPrem) from LPEdorItem "
							+ wherePart
							+ "), "
							+ "ChgAmnt= (select sum(ChgAmnt) from LPEdorItem "
							+ wherePart
							+ "), "
							+ "GetMoney= (select sum(GetMoney) from LPEdorItem "
							+ wherePart
							+ "), "
							+ "GetInterest= (select sum(GetInterest) from LPEdorItem "
							+ wherePart + ") " + wherePart);
					sbv1.put("tEdorNo", tEdorNo);
					sbv1.put("tEdorType", tEdorType);
					map
							.put(sbv1,"UPDATE");

				} catch (Exception ex) {
					CError.buildErr(this, "保全项目" + tEdorType + "申请确认时失败！");
					return false;
				}
				// 以下注掉，在做具体项目需要看具体的逻辑 -BY PQ 2004-12-30
				// if (iLPGrpEdorItemSchema.getEdorType().equals("LR"))
				// {
				// VData tVData = new VData();
				// PGrpEdorAppConfirmBLS tPGrpEdorAppConfirmBLS = new
				// PGrpEdorAppConfirmBLS();
				// preGrpAppConfirmLR();
				// tVData.add(iLPGrpEdorItemSchema);
				// tVData.add(mLJSGetEndorseSchema);
				//
				// if (!tPGrpEdorAppConfirmBLS.submitData(tVData,
				// "APPCONFIRM||LR"))
				// {
				// return false;
				// }
				// }
				// else if (iLPGrpEdorItemSchema.getEdorType().equals("NS"))
				// {
				// VData tVData = new VData();
				// PGrpEdorNSAppConfirmBL tPGrpEdorNSAppConfirmBL = new
				// PGrpEdorNSAppConfirmBL();
				// tVData.add(iLPGrpEdorItemSchema);
				// tVData.add(mGlobalInput);
				//
				// if (!tPGrpEdorNSAppConfirmBL.submitData(tVData,
				// "APPCONFIRM||NS"))
				// {
				// return false;
				// }
				// }
				// else if (iLPGrpEdorItemSchema.getEdorType().equals("RF"))
				// {
				// VData tVData = new VData();
				//
				// //preGrpAppConfirmRF();
				// PGrpEdorRFAppConfirmBL tPGrpEdorRFAppConfirmBL = new
				// PGrpEdorRFAppConfirmBL();
				// tVData.add(iLPGrpEdorItemSchema);
				// tVData.add(mGlobalInput);
				//
				// if (!tPGrpEdorRFAppConfirmBL.submitData(tVData,
				// "APPCONFIRM||RF"))
				// {
				// return false;
				// }
				// }
				// else if (iLPGrpEdorItemSchema.getEdorType().equals("LF"))
				// {
				// VData tVData = new VData();
				// GEdorLFAppConfirmBL tGEdorLFAppConfirmBL = new
				// GEdorLFAppConfirmBL();
				//
				// //preGrpAppConfirmLF();
				// tVData.add(iLPGrpEdorItemSchema);
				// tVData.add(mGlobalInput);
				//
				// if (!tGEdorLFAppConfirmBL.submitData(tVData,
				// "APPCONFIRM||LR"))
				// {
				// //因为LF要操作的表和LR一样，所以这里复用一下LR的代码
				// return false;
				// }
				// }
				// else if (iLPGrpEdorItemSchema.getEdorType().equals("CA"))
				// {
				// logger.debug(
				// "-------------CACACACA EDOR TYPE!------------");
				//
				// if (iLPGrpEdorItemSchema.getChgPrem() > 0)
				// {
				// throw new NullPointerException("金额没有完全转移！");
				// }
				//
				// VData tVData = new VData();
				// PGrpEdorAppConfirmBLS tPGrpEdorAppConfirmBLS = new
				// PGrpEdorAppConfirmBLS();
				// tVData.addElement(iLPGrpEdorItemSchema);
				//
				// if (!tPGrpEdorAppConfirmBLS.submitData(tVData,
				// "APPCONFIRM||NO"))
				// {
				// return false;
				// }
				// }
				// else if (iLPGrpEdorItemSchema.getEdorType().equals("LT"))
				// {
				// VData tVData = new VData();
				// PGrpEdorLTAppConfirmBL tPGrpEdorLTAppConfirmBL = new
				// PGrpEdorLTAppConfirmBL();
				// tVData.add(iLPGrpEdorItemSchema);
				// tVData.add(mGlobalInput);
				//
				// if (!tPGrpEdorLTAppConfirmBL.submitData(tVData,
				// "APPCONFIRM||LT"))
				// {
				// return false;
				// }
				// }
				// else
				// {
				// logger.debug(
				// "-------------NO SPECIAL EDOR TYPE!------------");
				//
				// VData tVData = new VData();
				// PGrpEdorAppConfirmBLS tPGrpEdorAppConfirmBLS = new
				// PGrpEdorAppConfirmBLS();
				// tVData.addElement(iLPGrpEdorItemSchema);
				//
				// if (!tPGrpEdorAppConfirmBLS.submitData(tVData,
				// "APPCONFIRM||NO"))
				// {
				// return false;
				// }
				// }
				// end
			}
		}
		// String execSql =
		// "select sum(ChgPrem) from LPEdorMain where EdorNo='" +
		// mLPGrpEdorMainSchema.getEdorNo() + "'";
		// // + "' and EdorType='" +
		// // iLPGrpEdorItemSchema.getEdorType() + "'";
		// String tReturn = aExeSQL.getOneValue(execSql);
		// if (aExeSQL.mErrors.needDealError())
		// {
		// CError.buildErr(this,
		// "查询批单号为" + tEdorNo + "下个单保全变更保费时失败");
		// return false;
		// }
		//
		// double tChgPrem;
		// double tGetMoney;
		// double tGetInterest;
		//
		// if ((tReturn == null) || tReturn.trim().equals(""))
		// {
		// tChgPrem = 0;
		// }
		// else
		// {
		// tChgPrem = Double.parseDouble(tReturn);
		// }
		//
		// execSql =
		// "select sum(GetMoney) from LPEdorMain where EdorNo='" +
		// mLPGrpEdorMainSchema.getEdorNo() + "'";
		// tReturn = aExeSQL.getOneValue(execSql);
		// if (aExeSQL.mErrors.needDealError())
		// {
		// CError.buildErr(this,
		// "查询批单号为" + tEdorNo + "下个单保全补/退费金额时失败");
		// return false;
		// }
		//
		// if ((tReturn == null) || tReturn.trim().equals(""))
		// {
		// tGetMoney = 0;
		// }
		// else
		// {
		// tGetMoney = Double.parseDouble(tReturn);
		// }
		// execSql =
		// "select sum(GetInterest) from LPEdorMain where EdorNo='" +
		// tEdorNo + "'";
		// tReturn = aExeSQL.getOneValue(execSql);
		// if (aExeSQL.mErrors.needDealError())
		// {
		// CError.buildErr(this,
		// "查询批单号为" + tEdorNo + "下个单保全利息金额时失败");
		// return false;
		// }
		//
		// if ((tReturn == null) || tReturn.trim().equals(""))
		// {
		// tGetInterest = 0;
		// }
		// else
		// {
		// tGetInterest = Double.parseDouble(tReturn);
		// }
		// mLPGrpEdorMainSchema.setChgPrem(tChgPrem);
		// mLPGrpEdorMainSchema.setGetMoney(tGetMoney);
		// mLPGrpEdorMainSchema.setGetInterest(tGetInterest);
		mLPGrpEdorMainSchema.setEdorState("2");
		mLPGrpEdorMainSchema.setUWState("0");
		mLPGrpEdorMainSchema.setOperator(mGlobalInput.Operator);
		mLPGrpEdorMainSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpEdorMainSchema.setModifyTime(PubFun.getCurrentTime());
		tLPGrpEdorMainSet.add(mLPGrpEdorMainSchema);
		map.put(mLPGrpEdorMainSchema, "UPDATE");
		String wherePart = "where EdorNo='" + "?tEdorNo?" + "' and GrpContNo='"
				+ "?tGrpContNo?" + "'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql("update LPGrpEdorMain set ChgPrem= (select sum(ChgPrem) from LPGrpEdorItem "
				+ wherePart
				+ "), "
				+ "ChgAmnt= (select sum(ChgAmnt) from LPGrpEdorItem "
				+ wherePart
				+ "), "
				+ "GetMoney= (select sum(GetMoney) from LPGrpEdorItem "
				+ wherePart
				+ "), "
				+ "GetInterest= (select sum(GetInterest) from LPGrpEdorItem "
				+ wherePart + ") " + wherePart);
		sbv2.put("tEdorNo", tEdorNo);
		sbv2.put("tGrpContNo", tGrpContNo);
		map
				.put(sbv2, "UPDATE");

		return true;
	}

	private boolean prepareOutputData() {

		mInputData.clear();
		mInputData.add(map);
		mResult.clear();
		mResult.add(map);

		return true;
	}

	// 以下以下具体项目的处理，放到实现保全申请确认接口的类里 by PQ-2004-12-30
	/**
	 * 团体还款，
	 * 
	 * @return
	 */
	// private boolean preGrpAppConfirmRF()
	// {
	// //设置保全主表数据到申请确认状态
	// mLPGrpEdorMainSchema.setEdorState("2");
	// mLPGrpEdorMainSchema.setUWState("0");
	//
	// //获取所有还款数据
	// LPReturnLoanDB tLPReturnLoanDB = new LPReturnLoanDB();
	//
	// tLPReturnLoanDB.setPolNo(mLPGrpEdorMainSchema.getGrpContNo());
	// tLPReturnLoanDB.setEdorType(mLPGrpEdorMainSchema.getEdorType());
	// tLPReturnLoanDB.setEdorNo(mLPGrpEdorMainSchema.getEdorNo());
	// outLPReturnLoanSet = tLPReturnLoanDB.query();
	//
	// if (outLPReturnLoanSet.size() == 0)
	// {
	// throw new NullPointerException("没有还款数据，不能作此操作！");
	// }
	//
	// logger.debug("End 获取所有还款数据");
	//
	// //获取每笔还款对应的利率数据
	// if (!getLoanRate(outLPReturnLoanSet))
	// {
	// throw new NullPointerException("没有借款利率，无法计算利息！");
	// }
	//
	// logger.debug("End 获取每笔还款对应的利率数据");
	//
	// //计算每笔还款本金对应的利息
	// calculateInterest(outLPReturnLoanSet);
	// logger.debug("End 计算每笔还款本金对应的利息");
	//
	// //往批改补退费表（应收/应付）新增数据
	// if (!setLJSGetEndorse(outLPReturnLoanSet))
	// {
	// throw new NullPointerException("往批改补退费表（应收/应付）新增数据失败!");
	// }
	//
	// logger.debug("End 往批改补退费表（应收/应付）新增数据");
	//
	// return true;
	// }
	//
	// /**
	// * 获取每笔还款对应的利率数据
	// * @param tLPReturnLoanSet
	// * @return
	// */
	// private boolean getLoanRate(LPReturnLoanSet tLPReturnLoanSet)
	// {
	// for (int i = 0; i < tLPReturnLoanSet.size(); i++)
	// {
	// LPReturnLoanSchema tLPReturnLoanSchema = tLPReturnLoanSet.get(i +
	// 1);
	// LOLoanDB tLOLoanDB = new LOLoanDB();
	//
	// //从借款业务表中获取利率数据
	// tLOLoanDB.setPolNo(tLPReturnLoanSchema.getPolNo());
	// tLOLoanDB.setEdorNo(tLPReturnLoanSchema.getLoanNo()); //原批单号
	//
	// if (!tLOLoanDB.getInfo())
	// {
	// return false;
	// }
	//
	// //将利率数据保存进tLPReturnLoanSchema，因为是引用，所以不用返回
	// String tEdorNo = tLPReturnLoanSchema.getEdorNo();
	// mReflections.transFields(tLPReturnLoanSchema, tLOLoanDB.getSchema());
	// tLPReturnLoanSchema.setEdorNo(tEdorNo);
	// }
	//
	// return true;
	// }
	//
	// /**
	// * 计算还款利息
	// * @param autoPayLOLoanSet
	// */
	// private void calculateInterest(LPReturnLoanSet tLPReturnLoanSet)
	// {
	// int i;
	// double interestMoney = 0;
	// AccountManage tAccountManage = new AccountManage();
	// VData tVData = new VData();
	// String edorValiDate = getEdorValiDate();
	//
	// for (i = 0; i < tLPReturnLoanSet.size(); i++)
	// {
	// LPReturnLoanSchema tLPReturnLoanSchema = tLPReturnLoanSet.get(i +
	// 1);
	//
	// //计算利息函数需要LOLoanSchema，所以要转换一下
	// LOLoanSchema tLOLoanSchema = new LOLoanSchema();
	// mReflections.transFields(tLOLoanSchema, tLPReturnLoanSchema);
	// tVData.clear();
	// tVData.add(tLOLoanSchema);
	//
	// interestMoney = tAccountManage.getMultiInterest("1", tVData,
	// tLPReturnLoanSchema.getReturnMoney(),
	// tLPReturnLoanSchema.getLoanDate(), edorValiDate);
	//
	// tLPReturnLoanSchema.setReturnInterest(interestMoney + "");
	// }
	// }
	//
	// /**
	// * 获取保全生效日
	// * @return
	// */
	// private String getEdorValiDate()
	// {
	// LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
	// tLPGrpEdorMainDB.setSchema(mLPGrpEdorMainSchema);
	// tLPGrpEdorMainDB.getInfo();
	//
	// return tLPGrpEdorMainDB.getEdorValiDate();
	// }
	//
	// /**
	// * 往批改补退费表（应收/应付）新增数据
	// * @return
	// */
	// private boolean setLJSGetEndorse(LPReturnLoanSet tLPReturnLoanSet)
	// {
	// int i;
	//
	// //查询保单的信息
	// LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
	// tLCGrpPolDB.setGrpContNo(mLPGrpEdorMainSchema.getGrpContNo());
	//
	// if (!tLCGrpPolDB.getInfo())
	// {
	// return false;
	// }
	//
	// double totalMoney = 0;
	//
	// //处理所有的还款数据
	// for (i = 0; i < tLPReturnLoanSet.size(); i++)
	// {
	// LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
	//
	// tLJSGetEndorseSchema.setGetNoticeNo(mLPGrpEdorMainSchema.getEdorNo()); //无作用
	// tLJSGetEndorseSchema.setEndorsementNo(mLPGrpEdorMainSchema.
	// getEdorNo());
	// tLJSGetEndorseSchema.setFeeOperationType(mLPGrpEdorMainSchema.
	// getEdorType());
	// tLJSGetEndorseSchema.setPayPlanCode("0"); //无作用
	// tLJSGetEndorseSchema.setDutyCode("0"); //无作用，但一定要，转ljagetendorse时非空
	//
	// //把保单信息传递进来
	// mReflections.transFields(tLJSGetEndorseSchema,
	// tLCGrpPolDB.getSchema());
	//
	// tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
	// tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
	// tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
	// tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
	// tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
	//
	// LPReturnLoanSchema tLPReturnLoanSchema = tLPReturnLoanSet.get(i +
	// 1);
	//
	// tLJSGetEndorseSchema.setOtherNo(tLPReturnLoanSchema.getLoanNo()); //原批单号
	// tLJSGetEndorseSchema.setOtherNoType("3"); //保全给付
	//
	// //记录还款本金
	// tLJSGetEndorseSchema.setFeeFinaType("BF"); //还款本金属于补费
	// tLJSGetEndorseSchema.setGetFlag("0");
	// tLJSGetEndorseSchema.setGetDate(mLPGrpEdorMainSchema.
	// getEdorValiDate());
	// tLJSGetEndorseSchema.setGetMoney(tLPReturnLoanSchema.getReturnMoney());
	// outLJSGetEndorseSet.add(tLJSGetEndorseSchema);
	// totalMoney = PubFun.setPrecision(totalMoney +
	// tLPReturnLoanSchema.getReturnMoney(),
	// "0.00");
	//
	// //记录还款利息
	// LJSGetEndorseSchema rateLJSGetEndorseSchema = new
	// LJSGetEndorseSchema();
	// rateLJSGetEndorseSchema.setSchema(tLJSGetEndorseSchema);
	// rateLJSGetEndorseSchema.setFeeFinaType("LX"); //还款利息
	// rateLJSGetEndorseSchema.setGetMoney(tLPReturnLoanSchema.
	// getReturnInterest());
	// outLJSGetEndorseSet.add(rateLJSGetEndorseSchema);
	// totalMoney = PubFun.setPrecision(totalMoney +
	// tLPReturnLoanSchema.
	// getReturnInterest(), "0.00");
	// }
	//
	// mLPGrpEdorMainSchema.setGetMoney(totalMoney);
	//
	// return true;
	// }
	//
	// /**
	// * 保单补发数据准备
	// * @return
	// */
	// private boolean preGrpAppConfirmLR()
	// {
	// String flag;
	// String cOperate;
	// String insuredName;
	// int m;
	// m = 0;
	// flag = "N";
	// insuredName = null;
	//
	// double aTotalReturn = 0;
	// double aRate = 0;
	// String aCalCode;
	//
	// LPGrpEdorMainBL tLPGrpEdorMainBL = new LPGrpEdorMainBL();
	// LPGrpPolBL tLPGrpPolBL = new LPGrpPolBL();
	// LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
	// LJSGetEndorseBL tLJSGetEndorseBL = new LJSGetEndorseBL();
	//
	// tLPGrpPolBL.queryLPGrpPol(mLPGrpEdorMainSchema);
	// tLPGrpEdorMainBL.setSchema(mLPGrpEdorMainSchema);
	//
	// //生成批改交退费表
	// LMEdorLRDB tLMEdorLRDB = new LMEdorLRDB();
	// tLMEdorLRDB.setPolType("G");
	//
	// if (!tLMEdorLRDB.getInfo())
	// {
	// logger.debug("------no descriptions------");
	// }
	// else
	// {
	// BqCalBase tBqCalBase = new BqCalBase();
	// tBqCalBase.setPrem(tLPGrpPolBL.getPrem());
	// aCalCode = tLMEdorLRDB.getCalCode();
	//
	// BqCalBL tBqCalBL = new BqCalBL(tBqCalBase, aCalCode, "");
	// double aGetMoney = tBqCalBL.calGetEndorse("");
	//
	// if (aGetMoney == 0)
	// {
	// CError tError = new CError();
	// tError.moduleName = "PEdorAppConfirmBL";
	// tError.functionName = "submitData";
	// tError.errorMessage = "制单费计算失败!";
	// this.mErrors.addOneError(tError);
	//
	// return false;
	// }
	//
	// Reflections tRef = new Reflections();
	// LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
	// tRef.transFields(tLCGrpPolSchema, tLPGrpPolBL.getSchema());
	// tLJSGetEndorseSchema = tBqCalBL.initLJSGetEndorse(tLCGrpPolSchema,
	// mLPGrpEdorMainSchema, "BF", aGetMoney);
	// }
	//
	// tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
	// tLJSGetEndorseSchema.setManageCom(mLPGrpEdorMainSchema.getManageCom());
	//
	// //从描述表中获取财务接口类型，add by JL at 2004-8-30
	// String finType = BqCalBL.getFinType(mLPGrpEdorMainSchema.getEdorType(),
	// "GB", tLJSGetEndorseSchema.getPolNo());
	//
	// if (finType.equals(""))
	// {
	// CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码");
	//
	// return false;
	// }
	//
	// tLJSGetEndorseSchema.setFeeFinaType(finType);
	// tLJSGetEndorseSchema.setDutyCode("0");
	// tLJSGetEndorseSchema.setPayPlanCode("0");
	// tLJSGetEndorseSchema.setOtherNo("0");
	// tLJSGetEndorseSchema.setOtherNoType("NO");
	// tLJSGetEndorseSchema.setPolType("1");
	// tLJSGetEndorseSchema.setSerialNo("");
	// tLJSGetEndorseSchema.setGetFlag("0"); //yangzhao 12-05***1改成0，因为LR涉及的是交费
	//
	// tLJSGetEndorseBL.setSchema(tLJSGetEndorseSchema);
	// tLJSGetEndorseBL.setDefaultFields();
	// aTotalReturn = aTotalReturn + tLJSGetEndorseBL.getGetMoney();
	//
	// tLPGrpEdorMainBL.setGetMoney(aTotalReturn);
	// logger.debug("----totalreturn:" + aTotalReturn);
	//
	// //更新保单信息
	// //得到当前LPGrpEdorMain保单信息主表的状态，并更新状态为申请确认。
	// //tLPGrpEdorMainBL.setUpdateFields();
	// mLPGrpEdorMainSchema = tLPGrpEdorMainBL.getSchema();
	// mLPGrpEdorMainSchema.setEdorState("2");
	// tLJSGetEndorseSchema.setSchema(tLJSGetEndorseBL.getSchema());
	// mLPGrpEdorMainSchema.setUWState("0");
	// mLJSGetEndorseSchema.setSchema(tLJSGetEndorseBL.getSchema());
	//
	// return true;
	// }
	private boolean checkData() {
		return true;
	}
}
