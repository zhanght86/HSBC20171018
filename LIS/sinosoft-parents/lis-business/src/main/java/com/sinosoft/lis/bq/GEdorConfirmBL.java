package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
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
public class GEdorConfirmBL {
private static Logger logger = Logger.getLogger(GEdorConfirmBL.class);
	// 传输数据类
	private VData mInputData;

	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	// 操作符
	public String mOperate;
	public String mStrTemplatePath = "";
	private VData mResult = new VData();

	private MMap map = new MMap();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
	private LPGrpEdorMainSet tLPGrpEdorMainSet = new LPGrpEdorMainSet();

	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public GEdorConfirmBL() {
	}

	protected boolean LRPolPrint(String GrpPolNo) {
		logger.debug("start LRPolPrint...");
		// LCGrpPolF1PUI tLCGrpPolF1PUI = new LCGrpPolF1PUI();
		// LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();

		// tLCGrpPolSchema.setGrpPolNo(GrpPolNo); //必须使用主险保单号
		// tLCGrpPolSet.add(tLCGrpPolSchema);
		// 更新LCGrpPol表中的printcount字段为0
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpPolNo(GrpPolNo);
		tLCGrpPolSchema = tLCGrpPolDB.query().get(1);
		String strSql ="";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			 strSql = "update LCGrpPol set printcount = 0 where grppolno in (select grppolno from lcgrppol where prtno = '?prtno?')";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strSql = "update LCGrpPol a,(select grppolno from lcgrppol where prtno = '?prtno?')  b set a.Prtno = 0 where a.grppolno= b.grppolno";
		}
		sqlbv.sql(strSql);
		sqlbv.put("prtno", tLCGrpPolSchema.getPrtNo());
		
		logger.debug(strSql);
		tLCGrpPolDB.executeQuery(sqlbv);

		// 不再生成Xml数据，因为修改了printCount字段后，已经可以在保单打印界面中进行重打
		// 如果生成Xml数据，打印机会自动扫描到，并打印，不符合需求，modify by Minim at 2004-5-9
		// VData vData = new VData();
		// vData.addElement(new GlobalInput());
		// vData.addElement(tLCGrpPolSet);
		// vData.addElement("");
		//
		// if( !tLCGrpPolF1PUI.submitData(vData, "REPRINT") ) {
		// logger.debug("fail to get print data");
		// return false;
		// } else {
		// vData = tLCGrpPolF1PUI.getResult();
		// try {
		// InputStream ins = (InputStream)vData.get(0);
		// String strTemplatePath = mStrTemplatePath;
		//
		// // It is important to use buffered streams to enhance performance
		// BufferedInputStream bufIs = new BufferedInputStream(ins);
		//
		// String strTime = PubFun.getCurrentDate() + "_" +
		// PubFun.getCurrentTime();
		// strTime = strTime.replace(':', '_').replace('-', '_');
		//
		// // 个人保单用LCPolData；团体保单用LCGrpPolData
		//
		// FileOutputStream fos =
		// new FileOutputStream(strTemplatePath + "LCGrpPolData" + strTime +
		// ".xml");
		// BufferedOutputStream bufOs = new BufferedOutputStream(fos);
		// int n = 0;
		//
		// while( ( n = bufIs.read() ) != -1 ) {
		// bufOs.write(n);
		// }
		//
		// bufOs.flush();
		// bufOs.close();
		//
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		// }
		logger.debug("LRPolPrint Scucess");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {

		LPGrpEdorMainSchema aLPGrpEdorMainSchema = new LPGrpEdorMainSchema();

		logger.debug("Start GrpEdorConfirm BL Submit...");
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		aLPGrpEdorMainSchema = (LPGrpEdorMainSchema) mInputData
				.getObjectByObjectName("LPGrpEdorMainSchema", 0);
		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		tLPGrpEdorMainDB.setSchema(aLPGrpEdorMainSchema);
		tLPGrpEdorMainDB.getInfo();
		if (tLPGrpEdorMainDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询团体批单主表时失败");
			return false;
		}
		mLPGrpEdorMainSchema.setSchema(tLPGrpEdorMainDB);

		// aLPGrpEdorMainSchema = (LPGrpEdorMainSchema)
		// mInputData.getObjectByObjectName("LPGrpEdorMainSchema",
		// 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		// mLPGrpEdorMainSchema.setSchema(aLPGrpEdorMainSchema);
		mStrTemplatePath = (String) mInputData.get(2);

		// 校验团体保全主表的核保标记，校验补费、退费核销
		if (!checkData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("End 校验团体保全主表的核保标记，校验补费、退费核销");

		return true;
	}

	private boolean dealData() {
		String execSql = "";
		ExeSQL aExeSQL = new ExeSQL();
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();

		LPGrpEdorItemDB aLPGrpEdorItemDB = new LPGrpEdorItemDB();
		String strsqlp = "select * from LPGrpEdorItem where GrpContNo ='"
				+ "?GrpContNo?" + "' and Edorno = '"
				+ "?Edorno?"
				+ "' order by MakeDate,MakeTime";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strsqlp);
		sqlbv.put("GrpContNo", mLPGrpEdorMainSchema.getGrpContNo());
		sqlbv.put("Edorno", mLPGrpEdorMainSchema.getEdorNo());
		tLPGrpEdorItemSet = aLPGrpEdorItemDB.executeQuery(sqlbv);

		// 按照保全项目来进行确认（tjj chg 1024）
		logger.debug("按照保全项目来进行确认 tLPGrpEdorItemSet.size()"
				+ tLPGrpEdorItemSet.size());
		String tGrpContNo = mLPGrpEdorMainSchema.getGrpContNo();
		String tEdorNo = mLPGrpEdorMainSchema.getEdorNo();
		for (int i = 1; i <= tLPGrpEdorItemSet.size(); i++) {

			LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
			tLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(i));

			String tEdorType = tLPGrpEdorItemSchema.getEdorType();
			// 检查个单是否全部通过确认
			LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
			tLPGrpEdorItemDB.setGrpContNo(tGrpContNo);
			tLPGrpEdorItemDB.setEdorNo(tEdorNo);
			tLPGrpEdorItemDB.setEdorType(tEdorType);
			tLPGrpEdorItemDB.setEdorState("1");
			int aCount = tLPGrpEdorItemDB.getCount();
			if (tLPGrpEdorItemDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询批单号为" + tEdorNo + ",项目为" + tEdorType
						+ "下个单是否全部通过确认时失败");
				return false;
			}

			logger.debug("acount: " + aCount);

			if (aCount == 0) {

				tLPGrpEdorItemSchema.setEdorState("0");
				tLPGrpEdorItemSchema.setModifyDate(theCurrentDate);
				tLPGrpEdorItemSchema.setModifyTime(theCurrentTime);
				tLPGrpEdorItemSchema.setOperator(mGlobalInput.Operator);

				// 按照保全项目对集体单进行特殊处理
				logger.debug("按照保全项目对集体单进行特殊处理 edortype:"
						+ tLPGrpEdorItemSchema.getEdorType());

				try {
					Class tClass = Class.forName("com.sinosoft.lis.bq.GrpEdor"
							+ tEdorType + "ConfirmBL");
					EdorConfirm tGrpEdorConfirm = (EdorConfirm) tClass
							.newInstance();
					VData tVData = new VData();

					tVData.add(tLPGrpEdorItemSchema);
					// tVData.add(mLJSGetEndorseSchema);
					tVData.add(mGlobalInput);
					tVData.add(tLPGrpEdorMainSet);

					if (!tGrpEdorConfirm.submitData(tVData, "CONFIRM||"
							+ tEdorType)) {
						CError.buildErr(this, "保全项目" + tEdorType
								+ "申请时失败！失败原因："
								+ tGrpEdorConfirm.mErrors.getFirstError());

						return false;
					} else {
						VData rVData = tGrpEdorConfirm.getResult();
						MMap tMap = new MMap();
						tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
						if (tMap == null) {
							CError.buildErr(this, "得到保全项目为:" + tEdorType
									+ "的保全确认结果时失败！");
							return false;

						} else {
							map.add(tMap);
						}
					}

				} catch (ClassNotFoundException ex) {

					map.put(tLPGrpEdorItemSchema, "UPDATE");
				} catch (Exception ex) {
					CError.buildErr(this, "保全项目" + tEdorType + "申请确认时失败！");
					return false;
				}

				// 以下注掉，在做具体项目需要看具体的逻辑 -BY PQ 2004-12-31
				// if (tLPGrpEdorItemSchema.getEdorType().equals("ZT") ||
				// tLPGrpEdorItemSchema.getEdorType().equals("LT")) {
				// VData tVData = new VData();
				// PGrpEdorConfirmBLS tPGrpEdorConfirmBLS = new PGrpEdorConfirmBLS();
				//
				// //准备团体退保数据，校验集体下个人数目，决定是否删除集体单
				// tVData = this.preGrpconfirmZT(tLPGrpEdorItemSchema);
				//
				// if (!tPGrpEdorConfirmBLS.submitData(tVData, "CONFIRM||ZT")) {
				// return false;
				// }
				// } else if (tLPGrpEdorItemSchema.getEdorType().equals("WT")) {
				// VData tVData = new VData();
				// PGrpEdorConfirmBLS tPGrpEdorConfirmBLS = new PGrpEdorConfirmBLS();
				// tVData = this.preGrpconfirmZT(tLPGrpEdorItemSchema);
				//
				// if (!tPGrpEdorConfirmBLS.submitData(tVData, "CONFIRM||WT")) {
				// return false;
				// }
				// } else if (tLPGrpEdorItemSchema.getEdorType().equals("GT")) {
				// VData tVData = new VData();
				// PGrpEdorConfirmBLS tPGrpEdorConfirmBLS = new PGrpEdorConfirmBLS();
				// tVData = this.preGrpconfirmZT(tLPGrpEdorItemSchema);
				//
				// if (!tPGrpEdorConfirmBLS.submitData(tVData, "CONFIRM||GT")) {
				// return false;
				// }
				// } else if (tLPGrpEdorItemSchema.getEdorType().equals("LR")) {
				// VData tVData = new VData();
				// PGrpEdorConfirmBLS tPGrpEdorConfirmBLS = new PGrpEdorConfirmBLS();
				// tVData.addElement(tLPGrpEdorItemSchema);
				//
				// if (!tPGrpEdorConfirmBLS.submitData(tVData, "CONFIRM||LR")) {
				// return false;
				// }
				//
				// //打印团体的保单
				// String GrpPolNo = tLPGrpEdorItemSchema.getGrpContNo();
				//
				// if (!LRPolPrint(GrpPolNo)) {
				// CError tError = new CError();
				// tError.moduleName = "GEdorConfirmBL";
				// tError.functionName = "submitData";
				// tError.errorMessage = "保单打印失败!";
				// this.mErrors.addOneError(tError);
				//
				// return false;
				// }
				// } else if (tLPGrpEdorItemSchema.getEdorType().equals("AC")) {
				// VData tVData = new VData();
				// PGrpEdorConfirmBLS tPGrpEdorConfirmBLS = new PGrpEdorConfirmBLS();
				// tVData = preGrpConfirmAC(tLPGrpEdorItemSchema);
				//
				// if (!tPGrpEdorConfirmBLS.submitData(tVData, "CONFIRM||AC")) {
				// return false;
				// }
				// }
				// //*****SC*****与AC类似，一个保单主附同变，所以独立设计**yangzhao
				// else if (tLPGrpEdorItemSchema.getEdorType().equals("SC")) {
				// VData tVData = new VData();
				// PGrpEdorConfirmBLS tPGrpEdorConfirmBLS = new PGrpEdorConfirmBLS();
				// tVData = preGrpConfirmSC(tLPGrpEdorItemSchema);
				//
				// if (!tPGrpEdorConfirmBLS.submitData(tVData, "CONFIRM||SC")) {
				// return false;
				// }
				// }
				// //**********************************************
				// else if (tLPGrpEdorItemSchema.getEdorType().equals("CA")) {
				// VData tVData = new VData();
				// PGrpEdorConfirmBLS tPGrpEdorConfirmBLS = new PGrpEdorConfirmBLS();
				// tVData = preGrpConfirmCA(tLPGrpEdorItemSchema);
				//
				// if (!tPGrpEdorConfirmBLS.submitData(tVData, "CONFIRM||CA")) {
				// return false;
				// }
				// } else if (tLPGrpEdorItemSchema.getEdorType().equals("LF")) {
				// VData tVData = new VData();
				// PGrpEdorConfirmBLS tPGrpEdorConfirmBLS = new PGrpEdorConfirmBLS();
				// tVData = preGrpConfirmLF(tLPGrpEdorItemSchema);
				//
				// if (!tPGrpEdorConfirmBLS.submitData(tVData, "CONFIRM||LF")) {
				// return false;
				// }
				// } else if (tLPGrpEdorItemSchema.getEdorType().equals("RF")) {
				// VData tVData = new VData();
				// PGrpEdorRFConfirmBL tPGrpEdorRFConfirmBL = new PGrpEdorRFConfirmBL();
				// tVData.addElement(mLPGrpEdorMainSchema);
				// tVData.addElement(mGlobalInput);
				//
				// //tVData = preGrpConfirmRF(tLPGrpEdorItemSchema);
				// if (!tPGrpEdorRFConfirmBL.submitData(tVData, "CONFIRM||RF")) {
				// return false;
				// }
				// } else if (tLPGrpEdorItemSchema.getEdorType().equals("NS")) {
				// VData tVData = new VData();
				// PGrpEdorNSConfirmBL tPGrpEdorNSConfirmBL = new PGrpEdorNSConfirmBL();
				// tVData.addElement(mLPGrpEdorMainSchema);
				// tVData.addElement(mGlobalInput);
				//
				// //tVData = preGrpConfirmRF(tLPGrpEdorItemSchema);
				// if (!tPGrpEdorNSConfirmBL.submitData(tVData, "CONFIRM||NS")) {
				// return false;
				// }
				// } else if (tLPGrpEdorItemSchema.getEdorType().equals("MO")) {
				// VData tVData = new VData();
				// PGrpEdorMOConfirmBL tPGrpEdorMOConfirmBL = new PGrpEdorMOConfirmBL();
				// tVData.addElement(mLPGrpEdorMainSchema);
				// tVData.addElement(mGlobalInput);
				//
				// if (!tPGrpEdorMOConfirmBL.submitData(tVData, "CONFIRM||MO")) {
				// return false;
				// }
				// } else if (tLPGrpEdorItemSchema.getEdorType().equals("IA")) {
				// VData tVData = new VData();
				// PGrpEdorConfirmBLS tPGrpEdorConfirmBLS = new PGrpEdorConfirmBLS();
				// tVData = preGrpConfirmIA(tLPGrpEdorItemSchema);
				//
				// if (!tPGrpEdorConfirmBLS.submitData(tVData, "CONFIRM||IA")) {
				// return false;
				// }
				// } else {
				// logger.debug(
				// "-------------NO SPECIAL EDOR TYPE!------------");
				//
				// VData tVData = new VData();
				// PGrpEdorConfirmBLS tPGrpEdorConfirmBLS = new PGrpEdorConfirmBLS();
				// tVData.addElement(tLPGrpEdorItemSchema);
				//
				// if (!tPGrpEdorConfirmBLS.submitData(tVData, "CONFIRM||NO")) {
				// return false;
				// }
				// }
			}
		}
		mLPGrpEdorMainSchema.setEdorState("0");
		mLPGrpEdorMainSchema.setConfDate(theCurrentDate);
		mLPGrpEdorMainSchema.setConfTime(theCurrentTime);
		mLPGrpEdorMainSchema.setConfOperator(mGlobalInput.Operator);

		tLPGrpEdorMainSet.add(mLPGrpEdorMainSchema);
		map.put(mLPGrpEdorMainSchema, "UPDATE");
		String tSql = "update LCGrpCont set LastEdorDate='"
				+ "?LastEdorDate?"
				+ "' where GrpContNo='" + "?GrpContNo?"
				+ "'";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(tSql);
		sbv.put("LastEdorDate", mLPGrpEdorMainSchema.getEdorValiDate());
		sbv.put("GrpContNo", mLPGrpEdorMainSchema.getGrpContNo());
		map.put(sbv, "UPDATE");

		return true;

	}

	// 以下以下具体项目的处理，放到实现保全申请确认接口的类里 by PQ-2004-12-30
	// 准备团体退保数据，校验集体下个人数目，决定是否删除集体单
	// private VData preGrpconfirmZT(LPGrpEdorMainSchema aLPGrpEdorMainSchema) {
	// String aGrpPolNo;
	// String aEdorNo;
	// VData aInputData = new VData();
	// aInputData.clear();
	//
	// LBGrpPolSchema tLBGrpPolSchema = new LBGrpPolSchema();
	// LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
	// LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
	// LBGrpPolSet tLBGrpPolSet = new LBGrpPolSet();
	// Reflections tReflections = new Reflections();
	//
	// aEdorNo = aLPGrpEdorMainSchema.getEdorNo();
	// aGrpPolNo = aLPGrpEdorMainSchema.getGrpPolNo();
	//
	// String tSql = "select count(*) from LPEdorMain where EdorNo='" +
	// aLPGrpEdorMainSchema.getEdorNo() + "' and EdorType='" +
	// aLPGrpEdorMainSchema.getEdorType() + "' and GrpPolNo='" +
	// aLPGrpEdorMainSchema.getGrpPolNo() + "'";
	// logger.debug("---sql:" + tSql);
	//
	// ExeSQL tExeSQL = new ExeSQL();
	// String tReturn = tExeSQL.getOneValue(tSql);
	//
	// if ((tReturn == null) || tReturn.trim().equals("")) {
	// tReturn = "0";
	// }
	//
	// String tSql2 = "select count(*) from LPEdorMain where EdorNo='" +
	// aLPGrpEdorMainSchema.getEdorNo() + "' and EdorType='" +
	// aLPGrpEdorMainSchema.getEdorType() + "' and GrpPolNo='" +
	// aLPGrpEdorMainSchema.getGrpPolNo() + "'";
	// logger.debug("---sql:" + tSql);
	//
	// String tPrem = tExeSQL.getOneValue(tSql);
	// double tChgPrem = 0;
	//
	// if ((tPrem == null) || tPrem.trim().equals("")) {
	// tChgPrem = 0;
	// } else {
	// tChgPrem = Double.parseDouble(tPrem);
	// }
	//
	// String tSql1 = "select count(*) from lcPol where GrpPolNo='" +
	// aLPGrpEdorMainSchema.getGrpPolNo() + "'";
	// String tResult = tExeSQL.getOneValue(tSql1);
	//
	// if ((tResult == null) || tResult.trim().equals("")) {
	// tResult = "0";
	// }
	//
	// logger.debug("集体下个人数目，决定是否删除集体单:" + tResult);
	//
	// if (tResult.trim().equals("0")) {
	// //准备数据
	// //保单信息备份
	// tLCGrpPolSet.clear();
	// tLBGrpPolSet.clear();
	//
	// LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
	// tLCGrpPolDB.setGrpPolNo(aGrpPolNo);
	// tLCGrpPolSet = tLCGrpPolDB.query();
	//
	// for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
	// tLBGrpPolSchema = new LBGrpPolSchema();
	// tLCGrpPolSchema = tLCGrpPolSet.get(i);
	// tReflections.transFields(tLBGrpPolSchema, tLCGrpPolSchema);
	// tLBGrpPolSchema.setEdorNo(aEdorNo);
	// tLBGrpPolSchema.setSumPrem(tLBGrpPolSchema.getSumPrem() +
	// tChgPrem);
	// tLBGrpPolSchema.setPrem(tLBGrpPolSchema.getSumPrem());
	// tLBGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
	// tLBGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
	// tLBGrpPolSet.add(tLBGrpPolSchema);
	// }
	// }
	//
	// aInputData.clear();
	// aInputData.addElement(aLPGrpEdorMainSchema);
	// aInputData.addElement(tLBGrpPolSet);
	//
	// return aInputData;
	// }
	//
	// /**
	// * 投保人变更保全确认数据准备
	// * @param aLPGrpEdorMainSchema
	// * @return
	// */
	// private VData preGrpConfirmAC(LPGrpEdorMainSchema aLPGrpEdorMainSchema) {
	// VData tVData = new VData();
	// LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
	// LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
	// Reflections tRef = new Reflections();
	//
	// LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
	// tLPGrpPolDB.setGrpPolNo(aLPGrpEdorMainSchema.getGrpPolNo());
	// tLPGrpPolDB.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
	// tLPGrpPolDB.setEdorType(aLPGrpEdorMainSchema.getEdorType());
	//
	// if (!tLPGrpPolDB.getInfo()) {
	// this.mErrors.copyAllErrors(tLPGrpPolDB.mErrors);
	//
	// return tVData;
	// }
	//
	// //*****yangzhao****12-01*****附加险的LP->LC
	// logger.debug("查出LC and LP 附加险的数据");
	//
	// String strSql;
	// strSql = "select * from lcgrppol where grppolno <> '" +
	// aLPGrpEdorMainSchema.getGrpPolNo() + "' and grpno = '" +
	// tLPGrpPolDB.getGrpNo() + "' and prtno = '" +
	// tLPGrpPolDB.getPrtNo() + "'";
	// logger.debug(strSql);
	//
	// LCGrpPolSet ttLCGrpPolSet = new LCGrpPolSet();
	// LCGrpPolDB ttLCGrpPolDB = new LCGrpPolDB();
	// ttLCGrpPolSet = ttLCGrpPolDB.executeQuery(strSql); //ttLCGrpPolSet为变更之前的附加险数据
	// strSql = "select * from lpgrppol where edorno = '" +
	// aLPGrpEdorMainSchema.getEdorNo() + "' and grppolno <> '" +
	// aLPGrpEdorMainSchema.getGrpPolNo() + "' and grpno = '" +
	// tLPGrpPolDB.getGrpNo() + "' and prtno = '" +
	// tLPGrpPolDB.getPrtNo() + "' and edortype = '" +
	// aLPGrpEdorMainSchema.getEdorType() + "'";
	// logger.debug(strSql);
	//
	// LPGrpPolSet ttLPGrpPolSet = new LPGrpPolSet();
	// LPGrpPolDB ttLPGrpPolDB = new LPGrpPolDB();
	// ttLPGrpPolSet = ttLPGrpPolDB.executeQuery(strSql); //ttLPGrpPolSet为变更后的附加险数据
	//
	// LCGrpPolSet tempLCGrpPolSet = new LCGrpPolSet(); //建立一个临时的SET以便辅助LC LP之间的数据交换
	// LCGrpPolSchema qLCGrpPolSchema = new LCGrpPolSchema();
	// tempLCGrpPolSet.add(qLCGrpPolSchema);
	// tRef.transFields(tempLCGrpPolSet, ttLCGrpPolSet);
	//
	// //****************************************
	// if (!tLPGrpPolDB.getInfo()) {
	// this.mErrors.copyAllErrors(tLPGrpPolDB.mErrors);
	//
	// return tVData;
	// } else {
	// tRef.transFields(tLCGrpPolSchema, tLPGrpPolDB.getSchema());
	//
	// //*******yangzhao**12-01********附加险LP->LC
	// tRef.transFields(ttLCGrpPolSet, ttLPGrpPolSet);
	//
	// //*****************************************
	// }
	//
	// LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
	// tLCGrpPolDB.setGrpPolNo(aLPGrpEdorMainSchema.getGrpPolNo());
	//
	// if (!tLCGrpPolDB.getInfo()) {
	// this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
	//
	// return tVData;
	// } else {
	// tRef.transFields(tLPGrpPolSchema, tLCGrpPolDB.getSchema());
	// tLPGrpPolSchema.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
	// tLPGrpPolSchema.setEdorType(aLPGrpEdorMainSchema.getEdorType());
	//
	// //*********yangzhao*******12-01****附加险LC->LP
	// tRef.transFields(ttLPGrpPolSet, tempLCGrpPolSet);
	//
	// for (int i = 1; i <= ttLPGrpPolSet.size(); i++) {
	// ttLPGrpPolSet.get(i).setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
	// ttLPGrpPolSet.get(i).setEdorType(aLPGrpEdorMainSchema.getEdorType());
	// }
	//
	// //********************************************
	// }
	//
	// //***由于投保单位的变更导致了团体下个人单所谓的投保人的变更，所以给于实现，
	// //注：：：这个部分就是在团体保全确认的时候才对个人单进行直接对数据库的操作
	// //这里主要对2个操作 lcpol lcappentgrp
	// String strSql2;
	// strSql2 = "select * from lcpol where grppolno in (select grppolno from
	// lcgrppol where prtno='" +
	// tLCGrpPolSchema.getPrtNo() + "' and grpno='" +
	// tLCGrpPolSchema.getGrpNo() + "')";
	// logger.debug(strSql2);
	//
	// LCPolSet ttLCPolSet = new LCPolSet();
	// LCPolDB tLCPolDB = new LCPolDB();
	// ttLCPolSet = tLCPolDB.executeQuery(strSql2);
	//
	// for (int i = 1; i <= ttLCPolSet.size(); i++) //改变个人单的投保人姓名
	// {
	// ttLCPolSet.get(i).setAppntName(tLCGrpPolSchema.getGrpName());
	// }
	//
	// String strSql3;
	//
	// //strSql3 = "select * from LCAppntGrp where grpno = '" +
	// tLCGrpPolSchema.getGrpNo() +"' and";
	// strSql3 = "select * from lcappntgrp where polno in (select polno from lcpol
	// where grppolno in (select grppolno from lcgrppol where prtno='" +
	// tLCGrpPolSchema.getPrtNo() + "' and grpno='" +
	// tLCGrpPolSchema.getGrpNo() + "' )) and grpno='" +
	// tLCGrpPolSchema.getGrpNo() + "'";
	// logger.debug(strSql3);
	//
	// LCAppntGrpSet ttLCAppentGrpSet = new LCAppntGrpSet();
	// LCAppntGrpDB tLCAppntGrpDB = new LCAppntGrpDB();
	// ttLCAppentGrpSet = tLCAppntGrpDB.executeQuery(strSql3);
	//
	// for (int i = 1; i <= ttLCAppentGrpSet.size(); i++) //改变个人单的投保人姓名
	// {
	// ttLCAppentGrpSet.get(i).setGrpName(tLCGrpPolSchema.getGrpName());
	// ttLCAppentGrpSet.get(i).setGrpAddressCode(tLCGrpPolSchema.getGrpAddressCode());
	// ttLCAppentGrpSet.get(i).setGrpAddress(tLCGrpPolSchema.getGrpAddress());
	// ttLCAppentGrpSet.get(i).setGrpZipCode(tLCGrpPolSchema.getGrpZipCode());
	// ttLCAppentGrpSet.get(i).setBusinessType(tLCGrpPolSchema.getBusinessType());
	// ttLCAppentGrpSet.get(i).setGrpNature(tLCGrpPolSchema.getGrpNature());
	//
	// ttLCAppentGrpSet.get(i).setGetFlag(tLCGrpPolSchema.getGetFlag());
	// ttLCAppentGrpSet.get(i).setBankAccNo(tLCGrpPolSchema.getBankAccNo());
	// ttLCAppentGrpSet.get(i).setBankCode(tLCGrpPolSchema.getBankCode());
	// ttLCAppentGrpSet.get(i).setGrpGroupNo(tLCGrpPolSchema.getGrpGroupNo());
	//
	// ttLCAppentGrpSet.get(i).setLinkMan1(tLCGrpPolSchema.getLinkMan1());
	// ttLCAppentGrpSet.get(i).setDepartment1(tLCGrpPolSchema.getDepartment1());
	// ttLCAppentGrpSet.get(i).setHeadShip1(tLCGrpPolSchema.getHeadShip1());
	// ttLCAppentGrpSet.get(i).setPhone1(tLCGrpPolSchema.getPhone1());
	// ttLCAppentGrpSet.get(i).setE_Mail1(tLCGrpPolSchema.getE_Mail1());
	// ttLCAppentGrpSet.get(i).setFax1(tLCGrpPolSchema.getFax1());
	// ttLCAppentGrpSet.get(i).setLinkMan2(tLCGrpPolSchema.getLinkMan2());
	// ttLCAppentGrpSet.get(i).setDepartment2(tLCGrpPolSchema.getDepartment2());
	// ttLCAppentGrpSet.get(i).setHeadShip2(tLCGrpPolSchema.getHeadShip2());
	// ttLCAppentGrpSet.get(i).setPhone2(tLCGrpPolSchema.getPhone2());
	// ttLCAppentGrpSet.get(i).setE_Mail2(tLCGrpPolSchema.getE_Mail2());
	// ttLCAppentGrpSet.get(i).setFax2(tLCGrpPolSchema.getFax2());
	// }
	//
	// //*****************************************************
	// tVData.addElement(aLPGrpEdorMainSchema);
	// tVData.addElement(tLCGrpPolSchema);
	// tVData.addElement(tLPGrpPolSchema);
	// tVData.addElement(ttLCGrpPolSet);
	// tVData.addElement(ttLPGrpPolSet);
	// tVData.addElement(ttLCPolSet);
	// tVData.addElement(ttLCAppentGrpSet);
	//
	// return tVData;
	// }
	//
	// //************SC******similar with AC
	//
	// /**
	// * 投保人变更保全确认数据准备
	// * @param aLPGrpEdorMainSchema
	// * @return
	// */
	// private VData preGrpConfirmSC(LPGrpEdorMainSchema aLPGrpEdorMainSchema) {
	// VData tVData = new VData();
	// LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
	// LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
	// Reflections tRef = new Reflections();
	//
	// LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
	// tLPGrpPolDB.setGrpPolNo(aLPGrpEdorMainSchema.getGrpPolNo());
	// tLPGrpPolDB.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
	// tLPGrpPolDB.setEdorType(aLPGrpEdorMainSchema.getEdorType());
	//
	// if (!tLPGrpPolDB.getInfo()) {
	// this.mErrors.copyAllErrors(tLPGrpPolDB.mErrors);
	//
	// return tVData;
	// }
	//
	// //*****yangzhao****12-01*****附加险的LP->LC
	// logger.debug("查出LC and LP 附加险的数据");
	//
	// String strSql;
	// strSql = "select * from lcgrppol where grppolno <> '" +
	// aLPGrpEdorMainSchema.getGrpPolNo() + "' and grpno = '" +
	// tLPGrpPolDB.getGrpNo() + "' and prtno = '" +
	// tLPGrpPolDB.getPrtNo() + "'";
	// logger.debug(strSql);
	//
	// LCGrpPolSet ttLCGrpPolSet = new LCGrpPolSet();
	// LCGrpPolDB ttLCGrpPolDB = new LCGrpPolDB();
	// ttLCGrpPolSet = ttLCGrpPolDB.executeQuery(strSql); //ttLCGrpPolSet为变更之前的附加险数据
	// strSql = "select * from lpgrppol where edorno = '" +
	// aLPGrpEdorMainSchema.getEdorNo() + "' and grppolno <> '" +
	// aLPGrpEdorMainSchema.getGrpPolNo() + "' and grpno = '" +
	// tLPGrpPolDB.getGrpNo() + "' and prtno = '" +
	// tLPGrpPolDB.getPrtNo() + "' and edortype = '" +
	// aLPGrpEdorMainSchema.getEdorType() + "'";
	// logger.debug(strSql);
	//
	// LPGrpPolSet ttLPGrpPolSet = new LPGrpPolSet();
	// LPGrpPolDB ttLPGrpPolDB = new LPGrpPolDB();
	// ttLPGrpPolSet = ttLPGrpPolDB.executeQuery(strSql); //ttLPGrpPolSet为变更后的附加险数据
	//
	// LCGrpPolSet tempLCGrpPolSet = new LCGrpPolSet(); //建立一个临时的SET以便辅助LC LP之间的数据交换
	// LCGrpPolSchema qLCGrpPolSchema = new LCGrpPolSchema();
	// tempLCGrpPolSet.add(qLCGrpPolSchema);
	// tRef.transFields(tempLCGrpPolSet, ttLCGrpPolSet);
	//
	// //****************************************
	// if (!tLPGrpPolDB.getInfo()) {
	// this.mErrors.copyAllErrors(tLPGrpPolDB.mErrors);
	//
	// return tVData;
	// } else {
	// tRef.transFields(tLCGrpPolSchema, tLPGrpPolDB.getSchema());
	//
	// //*******yangzhao**12-01********附加险LP->LC
	// tRef.transFields(ttLCGrpPolSet, ttLPGrpPolSet);
	//
	// //*****************************************
	// }
	//
	// LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
	// tLCGrpPolDB.setGrpPolNo(aLPGrpEdorMainSchema.getGrpPolNo());
	//
	// if (!tLCGrpPolDB.getInfo()) {
	// this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
	//
	// return tVData;
	// } else {
	// tRef.transFields(tLPGrpPolSchema, tLCGrpPolDB.getSchema());
	// tLPGrpPolSchema.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
	// tLPGrpPolSchema.setEdorType(aLPGrpEdorMainSchema.getEdorType());
	//
	// //*********yangzhao*******12-01****附加险LC->LP
	// tRef.transFields(ttLPGrpPolSet, tempLCGrpPolSet);
	//
	// for (int i = 1; i <= ttLPGrpPolSet.size(); i++) {
	// ttLPGrpPolSet.get(i).setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
	// ttLPGrpPolSet.get(i).setEdorType(aLPGrpEdorMainSchema.getEdorType());
	// }
	//
	// //********************************************
	// }
	//
	// tVData.addElement(aLPGrpEdorMainSchema);
	// tVData.addElement(tLCGrpPolSchema);
	// tVData.addElement(tLPGrpPolSchema);
	// tVData.addElement(ttLCGrpPolSet);
	// tVData.addElement(ttLPGrpPolSet);
	//
	// return tVData;
	// }
	//
	// //*********************************** SC over
	//
	// /**
	// * 借款
	// * @param aLPGrpEdorMainSchema
	// * @return
	// */
	// private VData preGrpConfirmLF(LPGrpEdorMainSchema aLPGrpEdorMainSchema) {
	// logger.debug("--------preGrpConfirmIF-----*************-----");
	//
	// VData tVData = new VData();
	// Reflections tReflections = new Reflections();
	// LPEdorMainBL tLPEdorMainBL = new LPEdorMainBL();
	//
	// LPLoanSchema aLPLoanSchema = new LPLoanSchema();
	// LOLoanSchema aLOLoanSchema = new LOLoanSchema();
	//
	// //得到保全借款业务表数据
	// LPLoanDB tLPLoanDB = new LPLoanDB();
	// tLPLoanDB.setEdorNo(mLPGrpEdorMainSchema.getEdorNo());
	// tLPLoanDB.setEdorType(mLPGrpEdorMainSchema.getEdorType());
	// tLPLoanDB.setPolNo(mLPGrpEdorMainSchema.getGrpPolNo());
	//
	// LPLoanSet tLPLoanSet = tLPLoanDB.query();
	//
	// if (tLPLoanSet.size() != 1) {
	// logger.debug("&&&&&&&&&&&");
	// }
	//
	// aLPLoanSchema = tLPLoanSet.get(1);
	//
	// //转换成保单个人信息。
	// tReflections.transFields(aLOLoanSchema, aLPLoanSchema);
	// aLOLoanSchema.setModifyDate(PubFun.getCurrentDate());
	// aLOLoanSchema.setModifyTime(PubFun.getCurrentTime());
	//
	// tVData.addElement(aLPGrpEdorMainSchema);
	// tVData.addElement(aLOLoanSchema);
	//
	// return tVData;
	// }
	//
	// /**
	// * 还款
	// * @param aLPGrpEdorMainSchema
	// * @return
	// */
	// /**
	// * 帐户资金转移
	// * 功能是对LPInsureAcc进行数据的转换LP<->LC;LPInsureAccTrace的数据转移到LC表中
	// * @param aLPGrpEdorMainSchema
	// * @return
	// */
	// private VData preGrpConfirmCA(LPGrpEdorMainSchema aLPGrpEdorMainSchema) {
	// logger.debug(
	// "*****************preGrpConfirmCA*******************");
	//
	// VData tVData = new VData();
	// Reflections tRef = new Reflections();
	//
	// ////////////////////////////////////////
	// tVData.addElement(aLPGrpEdorMainSchema);
	//
	// //通过主批改表查询LPInsureAccTrace的数据
	// LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
	// tLPInsureAccTraceDB.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
	// tLPInsureAccTraceDB.setEdorType(aLPGrpEdorMainSchema.getEdorType());
	//
	// LPInsureAccTraceSet outLPInsureAccTraceSet = tLPInsureAccTraceDB.query();
	//
	// if (outLPInsureAccTraceSet.size() == 0) {
	// logger.debug("没有查出LPInsureAccTrace的数据");
	// this.mErrors.copyAllErrors(tLPInsureAccTraceDB.mErrors);
	//
	// return tVData;
	// }
	//
	// //转移LPInsureAccTrace->LCInsureAccTrace
	// LCInsureAccTraceSet outLCInsureAccTraceSet = new LCInsureAccTraceSet();
	// LCInsureAccTraceSchema qLCInsureAccTraceSchema = new
	// LCInsureAccTraceSchema();
	// outLCInsureAccTraceSet.add(qLCInsureAccTraceSchema);
	// tRef.transFields(outLCInsureAccTraceSet, outLPInsureAccTraceSet);
	//
	// //通过主批改表查询LPInsureAcc的数据 tLPInsureAccSet
	// LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB();
	// tLPInsureAccDB.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
	// tLPInsureAccDB.setEdorType(aLPGrpEdorMainSchema.getEdorType());
	//
	// LPInsureAccSet tLPInsureAccSet = tLPInsureAccDB.query();
	//
	// if (tLPInsureAccSet.size() == 0) {
	// logger.debug("没有查出LPInsureAcc的数据");
	// this.mErrors.copyAllErrors(tLPInsureAccDB.mErrors);
	//
	// return tVData;
	// }
	//
	// //交换LPInsureAcc和LCInsureAcc的数据
	// //1.通过LP表查询LC表的数据 bLCInsureAccSet
	// LCInsureAccSet bLCInsureAccSet = new LCInsureAccSet();
	//
	// for (int i = 1; i <= tLPInsureAccSet.size(); i++) {
	// LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
	// tLCInsureAccDB.setPolNo(tLPInsureAccSet.get(i).getPolNo());
	// tLCInsureAccDB.setInsuAccNo(tLPInsureAccSet.get(i).getInsuAccNo());
	// tLCInsureAccDB.setOtherNo(tLPInsureAccSet.get(i).getOtherNo());
	//
	// if (!tLCInsureAccDB.getInfo()) {
	// this.mErrors.copyAllErrors(tLCInsureAccDB.mErrors);
	//
	// return tVData;
	// }
	//
	// bLCInsureAccSet.add(tLCInsureAccDB.getSchema());
	// }
	//
	// //2.备份LC表的数据到 outLCInsureAccSet
	// LCInsureAccSet outLCInsureAccSet = new LCInsureAccSet();
	// LCInsureAccSchema qLCInsureAccSchema = new LCInsureAccSchema();
	// outLCInsureAccSet.add(qLCInsureAccSchema);
	// tRef.transFields(outLCInsureAccSet, bLCInsureAccSet);
	//
	// //3.LP->LC 新的数据到outLCInsureAccSet
	// tRef.transFields(outLCInsureAccSet, tLPInsureAccSet);
	//
	// //4.LC->LP 新的数据到tLPInsureAccSet
	// tRef.transFields(tLPInsureAccSet, bLCInsureAccSet);
	//
	// for (int i = 1; i <= tLPInsureAccSet.size(); i++) {
	// tLPInsureAccSet.get(i).setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
	// tLPInsureAccSet.get(i).setEdorType(aLPGrpEdorMainSchema.getEdorType());
	// }
	//
	// //传入准备数据
	// //tVData.addElement(aLPGrpEdorMainSchema);
	// tVData.addElement(outLPInsureAccTraceSet);
	// tVData.addElement(outLCInsureAccTraceSet);
	// tVData.addElement(tLPInsureAccSet);
	// tVData.addElement(outLCInsureAccSet);
	//
	// return tVData;
	// }
	//
	// /**
	// * 投保人变更保全确认数据准备
	// * @param aLPGrpEdorMainSchema
	// * @return
	// */
	// private VData preGrpConfirmIA(LPGrpEdorMainSchema aLPGrpEdorMainSchema) {
	// logger.debug("--------preGrpConfirmIA----------");
	//
	// VData tVData = new VData();
	// LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
	// LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
	// Reflections tRef = new Reflections();
	//
	// LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
	// tLPGrpPolDB.setGrpPolNo(aLPGrpEdorMainSchema.getGrpPolNo());
	// tLPGrpPolDB.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
	// tLPGrpPolDB.setEdorType(aLPGrpEdorMainSchema.getEdorType());
	//
	// logger.debug("------------------------------");
	// logger.debug("aLPGrpEdorMainSchema EdorNo:" +
	// aLPGrpEdorMainSchema.getEdorNo());
	// logger.debug("aLPGrpEdorMainSchema EdorType:" +
	// aLPGrpEdorMainSchema.getEdorType());
	// logger.debug("aLPGrpEdorMainSchema GrpPolNo:" +
	// aLPGrpEdorMainSchema.getGrpPolNo());
	// logger.debug("------------------------------");
	//
	// if (!tLPGrpPolDB.getInfo()) {
	// logger.debug("not getInof");
	// this.mErrors.copyAllErrors(tLPGrpPolDB.mErrors);
	//
	// return tVData;
	// } else {
	// tRef.transFields(tLCGrpPolSchema, tLPGrpPolDB.getSchema());
	// }
	//
	// LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
	// tLCGrpPolDB.setGrpPolNo(aLPGrpEdorMainSchema.getGrpPolNo());
	//
	// if (!tLCGrpPolDB.getInfo()) {
	// this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
	//
	// return tVData;
	// } else {
	// tRef.transFields(tLPGrpPolSchema, tLCGrpPolDB.getSchema());
	// tLPGrpPolSchema.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
	// tLPGrpPolSchema.setEdorType(aLPGrpEdorMainSchema.getEdorType());
	// }
	//
	// tVData.addElement(aLPGrpEdorMainSchema);
	// tVData.addElement(tLCGrpPolSchema);
	// tVData.addElement(tLPGrpPolSchema);
	//
	// return tVData;
	// }
	private boolean prepareOutputData() {

		mInputData.clear();
		mInputData.add(map);
		mResult.clear();
		mResult.add(map);

		return true;
	}

	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		boolean flag = true;
		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		LPGrpEdorMainSet tLPGrpEdorItemSet = new LPGrpEdorMainSet();
		String sql = "select * from LPGrpEdorMain where edorno='"
				+ "?edorno?"
				+ "' and uwstate in ('0','5')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("edorno", mLPGrpEdorMainSchema.getEdorNo());
		tLPGrpEdorItemSet.clear();
		tLPGrpEdorItemSet = tLPGrpEdorMainDB.executeQuery(sqlbv);

		if ((tLPGrpEdorItemSet != null) && (tLPGrpEdorItemSet.size() > 0)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorConfirmBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该申请核保没有通过!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mGlobalInput.ManageCom = mLPGrpEdorMainSchema.getManageCom();
		// 下段批改补退费相关代码还未检查 --remark by PQ 2004-12-31
		LJSGetDB tLJSGetDB = new LJSGetDB();
		LJSGetSet tLJSGetSet = new LJSGetSet();

		tLJSGetDB.setOtherNo(mLPGrpEdorMainSchema.getEdorNo());
		tLJSGetDB.setOtherNoType("3");
		tLJSGetSet = tLJSGetDB.query();

		if (tLJSGetSet.size() > 0) {
			String aGetNoticeNo = tLJSGetSet.get(1).getGetNoticeNo();
			LJFinaConfirm tLJFinaConfirm = new LJFinaConfirm(aGetNoticeNo, "O");
			tLJFinaConfirm.setOperator(mGlobalInput.Operator);
			tLJFinaConfirm.setLimit(PubFun.getNoLimit(mGlobalInput.ManageCom));

			if (!tLJFinaConfirm.submitData()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJFinaConfirm.mErrors);

				CError tError = new CError();
				tError.moduleName = "EdorConfirmBL";
				tError.functionName = "checkData";
				tError.errorMessage = "保全退费核销失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

		}

		LJSPayDB tLJSPayDB = new LJSPayDB();
		LJSPaySet tLJSPaySet = new LJSPaySet();

		tLJSPayDB.setOtherNo(mLPGrpEdorMainSchema.getEdorNo());
		tLJSPayDB.setOtherNoType("3");
		tLJSPaySet = tLJSPayDB.query();

		if (tLJSPaySet.size() > 0) {
			String aGetNoticeNo = tLJSPaySet.get(1).getGetNoticeNo();
			LJFinaConfirm tLJFinaConfirm = new LJFinaConfirm(aGetNoticeNo, "I");
			tLJFinaConfirm.setOperator(mGlobalInput.Operator);
			tLJFinaConfirm.setLimit(PubFun.getNoLimit(mGlobalInput.ManageCom));

			if (!tLJFinaConfirm.submitData()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJFinaConfirm.mErrors);

				CError tError = new CError();
				tError.moduleName = "EdorConfirmBL";
				tError.functionName = "checkData";
				tError.errorMessage = "保全交费核销失败!";
				this.mErrors.addOneError(tError);

				return false;
			}
		}

		return flag;
	}

	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		GEdorConfirmBL aGEdorConfirmBL = new GEdorConfirmBL();
		LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "Admin";

		// tLPGrpEdorMainSchema.setGrpPolNo("86330020040220000008");
		tLPGrpEdorMainSchema.setEdorNo("86330020040430010072");

		String strTemplatePath = "xerox/printdata/";
		tInputData.addElement(strTemplatePath);
		tInputData.addElement(tLPGrpEdorMainSchema);
		tInputData.addElement(tGlobalInput);

		aGEdorConfirmBL.submitData(tInputData, "INSERT||GRPEDORCONFIRM");
	}
}
