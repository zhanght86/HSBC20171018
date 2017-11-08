package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author unascribed
 * @version 1.0
 */

public class RePrintBL {
private static Logger logger = Logger.getLogger(RePrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private String mOperate = "";

	private MMap mMap = new MMap();

	// private VData mInputData = new VData();

	public RePrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		// mInputData = cInputData;
		try {

			if (!cOperate.equals("CONFIRM")
			// &&!cOperate.equals("PRINT")
			) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			// if( cOperate.equals("CONFIRM") )
			// {
			mResult.clear();
			// 准备所有要打印的数据
			// 保存旧保单号，生成新保单号，设置补打标志，将所有信息保存到DB中，同时调用12种打印模板,插入一条新数据，保存到数据库
			getPrintData(cInputData);
			// }
			// else if( cOperate.equals("PRINT") )
			// {
			// if( !saveData(cInputData) ) {
			// return false;
			// }
			// }
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", ex.toString());
			return false;
		}
	}

	public static void main(String[] args) {
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema = (LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0);

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RefuseAppF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private void getPrintData(VData cInputData) throws Exception {

		// LCPolDB tLCPolDB = new LCPolDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSchema.getPrtSeq()); // 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
		logger.debug("流水号" + tLOPRTManagerDB.getPrtSeq());
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}

		// 保存旧印刷流水号，生成新流水号，设置补打标志，将所有信息保存到DB中，同时调用12种打印模板,插入一条新数据，保存到数据库
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// tLOPRTManagerSchema.setSchema(tLOPRTManagerDB);
		tLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		tLOPRTManagerSchema.setOldPrtSeq(tLOPRTManagerDB.getOldPrtSeq());
		tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		tLOPRTManagerSchema.setDoneDate("");
		tLOPRTManagerSchema.setDoneTime("");
		tLOPRTManagerSchema.setPatchFlag("1");
		tLOPRTManagerSchema.setStateFlag("0");

		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		tLOPRTManagerSchema.setPrtSeq(PubFun1.CreateMaxNo("PRTSEQNO",
				strNoLimit));
		// mResult.add(mLOPRTManagerSchema);
		mResult.add(tLOPRTManagerSchema);

		/**
		 * 将原来的BLS方式改为用Map提交的方式
		 */
		logger.debug("NewPrtSeq=" + tLOPRTManagerSchema.getPrtSeq());
		mMap.put(tLOPRTManagerSchema, "INSERT");
		// 将旧流水号改为已回复
		String tSQL = "Update LOPrtManager set stateflag='2' where oldprtseq='"
				+ "?oldprtseq?"
				+ "' and stateflag='1' and code='"
				+ "?code?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("oldprtseq", tLOPRTManagerSchema.getOldPrtSeq());
		sqlbv.put("code", tLOPRTManagerSchema.getCode());
		logger.debug("tSQL:" + tSQL);
		mMap.put(sqlbv, "UPDATE");
		mResult.add(mMap);

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			mErrors.copyAllErrors(tPubSubmit.mErrors);
		}

		//
		// RePrintBLS tRePrintBLS = new RePrintBLS();
		// tRePrintBLS.submitData(mResult, mOperate);
		// if (tRePrintBLS.mErrors.needDealError()) {
		// mErrors.copyAllErrors(tRePrintBLS.mErrors);
		// buildError("saveData", "提交数据库出错！");
		// }

		// end of else.

	} // end of getPrintData.
}
