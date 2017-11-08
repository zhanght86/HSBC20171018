package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.db.LPGrpAddressDB;
import com.sinosoft.lis.db.LPGrpAppntDB;
import com.sinosoft.lis.db.LPGrpContDB;
import com.sinosoft.lis.db.LPGrpDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LPGrpAddressSchema;
import com.sinosoft.lis.schema.LPGrpAppntSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpSchema;
import com.sinosoft.lis.vschema.LPGrpAddressSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全申请确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @version 1.0
 */
public class GrpEdorACConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorACConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/**  */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();
	boolean newaddrFlag = false;

	public GrpEdorACConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据准备操作
		if (!prepareData()) {
			return false;
		}
		logger.debug("---End prepareData---");
		if (!prepareOutputData()) {
			return false;
		}

		// // 数据操作业务处理
		// PEdorConfirmBLS tPEdorConfirmBLS = new PEdorConfirmBLS();
		// logger.debug("Start Confirm BB BL Submit...");
		// if (!tPEdorConfirmBLS.submitData(mInputData, mOperate))
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tPEdorConfirmBLS.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "PEdorConfirmBL";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean prepareOutputData() {

		mResult.clear();
		mResult.add(map);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		VData tVData = new VData();
		LPGrpContSchema tLPGrpContSchema = new LPGrpContSchema();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		Reflections tRef = new Reflections();

		String tGrpContNo = mLPGrpEdorItemSchema.getGrpContNo();
		String tEdorNo = mLPGrpEdorItemSchema.getEdorNo();
		String tEdorType = mLPGrpEdorItemSchema.getEdorType();

		LPGrpAppntDB tLPGrpAppntDB = new LPGrpAppntDB();
		tLPGrpAppntDB.setGrpContNo(tGrpContNo);
		tLPGrpAppntDB.setEdorNo(tEdorNo);
		tLPGrpAppntDB.setEdorType(tEdorType);
		if (!tLPGrpAppntDB.getInfo()) {
			this.mErrors.copyAllErrors(tLPGrpAppntDB.mErrors);

			return false;
		}

		LPGrpContDB tLPGrpContDB = new LPGrpContDB();
		tLPGrpContDB.setGrpContNo(tGrpContNo);
		tLPGrpContDB.setEdorNo(tEdorNo);
		tLPGrpContDB.setEdorType(tEdorType);
		if (!tLPGrpContDB.getInfo()) {
			this.mErrors.copyAllErrors(tLPGrpContDB.mErrors);

			return false;
		}

		LPGrpDB tLPGrpDB = new LPGrpDB();
		tLPGrpDB.setEdorNo(tEdorNo);
		tLPGrpDB.setEdorType(tEdorType);
		tLPGrpDB.setCustomerNo(tLPGrpContDB.getAppntNo());
		if (!tLPGrpDB.getInfo()) {
			this.mErrors.copyAllErrors(tLPGrpDB.mErrors);

			return false;
		}

		LPGrpAddressDB tLPGrpAddressDB = new LPGrpAddressDB();
		LPGrpAddressSet tLPGrpAddressSet = new LPGrpAddressSet();
		tLPGrpAddressDB.setEdorNo(tEdorNo);
		tLPGrpAddressDB.setEdorType(tEdorType);
		tLPGrpAddressDB.setCustomerNo(tLPGrpContDB.getAppntNo());
		tLPGrpAddressDB.setAddressNo(tLPGrpContDB.getAddressNo());
		tLPGrpAddressSet = tLPGrpAddressDB.query();
		if (tLPGrpAddressDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLPGrpAddressDB.mErrors);
			return false;
		}
		if (tLPGrpAddressSet.size() > 0) {
			newaddrFlag = true;
			tLPGrpAddressDB.setSchema(tLPGrpAddressSet.get(1));
		}

		LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
		tLCGrpAppntDB.setGrpContNo(tGrpContNo);
		if (!tLCGrpAppntDB.getInfo()) {
			this.mErrors.copyAllErrors(tLCGrpAppntDB.mErrors);

			return false;
		}

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(tGrpContNo);
		if (!tLCGrpContDB.getInfo()) {
			this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);

			return false;
		}

		LDGrpDB tLDGrpDB = new LDGrpDB();
		tLDGrpDB.setCustomerNo(tLCGrpContDB.getAppntNo());
		if (!tLDGrpDB.getInfo()) {
			this.mErrors.copyAllErrors(tLDGrpDB.mErrors);

			return false;
		}

		LCGrpAppntSchema afterLCGrpAppntSchema = new LCGrpAppntSchema();
		LCGrpContSchema afterLCGrpContSchema = new LCGrpContSchema();
		LDGrpSchema afterLDGrpSchema = new LDGrpSchema();
		LCGrpAddressSchema afterLCGrpAddressSchema = new LCGrpAddressSchema();

		LPGrpAppntSchema afterLPGrpAppntSchema = new LPGrpAppntSchema();
		LPGrpContSchema afterLPGrpContSchema = new LPGrpContSchema();
		LPGrpSchema afterLPGrpSchema = new LPGrpSchema();
		LPGrpAddressSchema afterLPGrpAddressSchema = new LPGrpAddressSchema();

		tRef.transFields(afterLCGrpAppntSchema, tLPGrpAppntDB.getSchema());
		map.put(afterLCGrpAppntSchema, "UPDATE");

		tRef.transFields(afterLCGrpContSchema, tLPGrpContDB.getSchema());
		map.put(afterLCGrpContSchema, "UPDATE");

		tRef.transFields(afterLDGrpSchema, tLPGrpDB.getSchema());
		map.put(afterLDGrpSchema, "UPDATE");
		if (newaddrFlag) {
			tRef.transFields(afterLCGrpAddressSchema, tLPGrpAddressDB
					.getSchema());
			map.put(afterLCGrpAddressSchema, "INSERT");
		}
		afterLPGrpAppntSchema.setSchema(tLPGrpAppntDB.getSchema());
		tRef.transFields(afterLPGrpAppntSchema, tLCGrpAppntDB.getSchema());
		map.put(afterLPGrpAppntSchema, "UPDATE");

		afterLPGrpContSchema.setSchema(tLPGrpContDB.getSchema());
		tRef.transFields(afterLPGrpContSchema, tLCGrpContDB.getSchema());
		map.put(afterLPGrpContSchema, "UPDATE");

		afterLPGrpSchema.setSchema(tLPGrpDB.getSchema());
		tRef.transFields(afterLPGrpSchema, tLDGrpDB.getSchema());
		map.put(afterLPGrpSchema, "UPDATE");

		// 需要更新团单下所有个人保单的投保人号和投保人姓名
		String grpName = afterLDGrpSchema.getGrpName();
		String wherePart = " where GrpContNo='"
				+ afterLCGrpContSchema.getGrpContNo() + "'";
		String updateLCContSQL = "update LCCont set AppntName='" + grpName
				+ "'" + wherePart;
		String updateLCGrpPol = "update LCGrpPol set GrpName='" + grpName + "'"
				+ wherePart;
		String updateLCPol = "update LCPol set AppntName='" + grpName + "'"
				+ wherePart;
		map.put(StrTool.GBKToUnicode(updateLCContSQL), "UPDATE");
		map.put(StrTool.GBKToUnicode(updateLCGrpPol), "UPDATE");
		map.put(StrTool.GBKToUnicode(updateLCPol), "UPDATE");
		// tRef.transFields(afterLPGrpAddressSchema,tLPGrpAddressDB.getSchema());

		return true;
		// LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
		// tLCGrpAddressDB.setCustomerNo(tLCGrpContDB.getAppntNo());
		// tLCGrpAddressDB.setAddressNo(tLCGrpContDB.getAddressNo());
		// if (!tLCGrpAddressDB.getInfo())
		// {
		// this.mErrors.copyAllErrors(tLPGrpAddressDB.mErrors);
		//
		// return false;
		// }

		//
		// //*****yangzhao****12-01*****附加险的LP->LC
		// logger.debug("查出LC and LP 附加险的数据");
		//
		// String strSql;
		// strSql = "select * from lcgrppol where grppolno <> '" +
		// mLPGrpEdorItemSchema.getGrpContNo() + "' and grpno = '" +
		// tLPGrpContDB.getGrpNo() + "' and prtno = '" +
		// tLPGrpContDB.getPrtNo() + "'";
		// logger.debug(strSql);
		//
		// LCGrpPolSet ttLCGrpPolSet = new LCGrpPolSet();
		// LCGrpPolDB ttLCGrpPolDB = new LCGrpPolDB();
		// ttLCGrpPolSet = ttLCGrpPolDB.executeQuery(strSql);
		// //ttLCGrpPolSet为变更之前的附加险数据
		// strSql = "select * from lpgrppol where edorno = '" +
		// mLPGrpEdorItemSchema.getEdorNo() + "' and grppolno <> '" +
		// mLPGrpEdorItemSchema.getGrpContNo() + "' and grpno = '" +
		// tLPGrpContDB.getGrpNo() + "' and prtno = '" +
		// tLPGrpContDB.getPrtNo() + "' and edortype = '" +
		// mLPGrpEdorItemSchema.getEdorType() + "'";
		// logger.debug(strSql);
		//
		// LPGrpPolSet ttLPGrpPolSet = new LPGrpPolSet();
		// LPGrpContDB ttLPGrpPolDB = new LPGrpContDB();
		// ttLPGrpPolSet = ttLPGrpPolDB.executeQuery(strSql);
		// //ttLPGrpPolSet为变更后的附加险数据
		//
		// LCGrpPolSet tempLCGrpPolSet = new LCGrpPolSet(); //建立一个临时的SET以便辅助LC
		// LP之间的数据交换
		// LCGrpContSchema qLCGrpPolSchema = new LCGrpContSchema();
		// tempLCGrpPolSet.add(qLCGrpPolSchema);
		// tRef.transFields(tempLCGrpPolSet, ttLCGrpPolSet);
		//
		// //****************************************
		// if (!tLPGrpContDB.getInfo())
		// {
		// this.mErrors.copyAllErrors(tLPGrpContDB.mErrors);
		//
		// return false;
		// }
		// else
		// {
		// tRef.transFields(tLCGrpContSchema, tLPGrpContDB.getSchema());
		//
		// //*******yangzhao**12-01********附加险LP->LC
		// tRef.transFields(ttLCGrpPolSet, ttLPGrpPolSet);
		//
		// //*****************************************
		// }
		//
		// LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		// tLCGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		//
		// if (!tLCGrpPolDB.getInfo())
		// {
		// this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
		//
		// return false;
		// }
		// else
		// {
		// tRef.transFields(tLPGrpContSchema, tLCGrpPolDB.getSchema());
		// tLPGrpContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		// tLPGrpContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		//
		// //*********yangzhao*******12-01****附加险LC->LP
		// tRef.transFields(ttLPGrpPolSet, tempLCGrpPolSet);
		//
		// for (int i = 1; i <= ttLPGrpPolSet.size(); i++)
		// {
		// ttLPGrpPolSet.get(i).setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		// ttLPGrpPolSet.get(i).setEdorType(mLPGrpEdorItemSchema.
		// getEdorType());
		// }
		//
		// //********************************************
		// }
		//
		// //***由于投保单位的变更导致了团体下个人单所谓的投保人的变更，所以给于实现，
		// //注：：：这个部分就是在团体保全确认的时候才对个人单进行直接对数据库的操作
		// //这里主要对2个操作 lcpol lcappentgrp
		// String strSql2;
		// strSql2 = "select * from lcpol where grppolno in (select grppolno
		// from lcgrppol where prtno='" +
		// tLCGrpContSchema.getPrtNo() + "')";
		// /*Lis5.3 upgrade get
		// and grpno='" +
		// tLCGrpContSchema.getGrpNo() + "')";
		// */
		// logger.debug(strSql2);
		//
		// LCPolSet ttLCPolSet = new LCPolSet();
		// LCPolDB tLCPolDB = new LCPolDB();
		// ttLCPolSet = tLCPolDB.executeQuery(strSql2);
		//
		// for (int i = 1; i <= ttLCPolSet.size(); i++)
		// { //改变个人单的投保人姓名
		// ttLCPolSet.get(i).setAppntName(tLCGrpContSchema.getGrpName());
		// }
		//
		// String strSql3;
		//
		// //strSql3 = "select * from LCAppntGrp where grpno = '" +
		// tLCGrpContSchema.getGrpNo() +"' and";
		// strSql3 = "select * from lcappntgrp where polno in (select polno from
		// lcpol where grppolno in (select grppolno from lcgrppol where prtno='"
		// +
		// tLCGrpContSchema.getPrtNo() + "'))";
		// /*Lis5.3 upgrade get
		// and grpno='" +
		// tLCGrpContSchema.getGrpNo() + "' )) and grpno='" +
		// tLCGrpContSchema.getGrpNo() + "'";
		// */
		// logger.debug(strSql3);
		//
		// LCAppntGrpSet ttLCAppentGrpSet = new LCAppntGrpSet();
		// LCAppntGrpDB tLCAppntGrpDB = new LCAppntGrpDB();
		// ttLCAppentGrpSet = tLCAppntGrpDB.executeQuery(strSql3);
		//
		// for (int i = 1; i <= ttLCAppentGrpSet.size(); i++)
		// { //改变个人单的投保人姓名
		// ttLCAppentGrpSet.get(i).setGrpName(tLCGrpContSchema.getGrpName());
		// /*Lis5.3 upgrade get
		// ttLCAppentGrpSet.get(i).setGetFlag(tLCGrpContSchema.getGetFlag());
		// ttLCAppentGrpSet.get(i).setBankAccNo(tLCGrpContSchema.getBankAccNo());
		// ttLCAppentGrpSet.get(i).setBankCode(tLCGrpContSchema.getBankCode());
		// ttLCAppentGrpSet.get(i).setGrpAddressCode(tLCGrpContSchema.
		// getGrpAddressCode());
		// ttLCAppentGrpSet.get(i).setGrpAddress(tLCGrpContSchema.getGrpAddress());
		// ttLCAppentGrpSet.get(i).setGrpZipCode(tLCGrpContSchema.getGrpZipCode());
		// ttLCAppentGrpSet.get(i).setBusinessType(tLCGrpContSchema.getBusinessType());
		// ttLCAppentGrpSet.get(i).setGrpNature(tLCGrpContSchema.getGrpNature());
		// ttLCAppentGrpSet.get(i).setGrpGroupNo(tLCGrpContSchema.getGrpGroupNo());
		// ttLCAppentGrpSet.get(i).setLinkMan1(tLCGrpContSchema.getLinkMan1());
		// ttLCAppentGrpSet.get(i).setDepartment1(tLCGrpContSchema.getDepartment1());
		// ttLCAppentGrpSet.get(i).setHeadShip1(tLCGrpContSchema.getHeadShip1());
		// ttLCAppentGrpSet.get(i).setPhone1(tLCGrpContSchema.getPhone1());
		// ttLCAppentGrpSet.get(i).setE_Mail1(tLCGrpContSchema.getE_Mail1());
		// ttLCAppentGrpSet.get(i).setFax1(tLCGrpContSchema.getFax1());
		// ttLCAppentGrpSet.get(i).setLinkMan2(tLCGrpContSchema.getLinkMan2());
		// ttLCAppentGrpSet.get(i).setDepartment2(tLCGrpContSchema.getDepartment2());
		// ttLCAppentGrpSet.get(i).setHeadShip2(tLCGrpContSchema.getHeadShip2());
		// ttLCAppentGrpSet.get(i).setPhone2(tLCGrpContSchema.getPhone2());
		// ttLCAppentGrpSet.get(i).setE_Mail2(tLCGrpContSchema.getE_Mail2());
		// ttLCAppentGrpSet.get(i).setFax2(tLCGrpContSchema.getFax2());
		// */
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

	}

	public TransferData getReturnTransferData() {
		return null;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
