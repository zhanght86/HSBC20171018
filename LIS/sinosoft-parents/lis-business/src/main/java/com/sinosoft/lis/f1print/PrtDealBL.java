package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.lis.vschema.LDCodeSet;
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
 * Copyright: sinosoft (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author haopan function :print and send out syscert
 * @version 1.0
 * @date 2006-10-16
 */
public class PrtDealBL {
private static Logger logger = Logger.getLogger(PrtDealBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	// 业务处理相关变量
	/** 全局数据 */
	private String mOperate = "";
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private VData mInputData = new VData();

	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mPrtSeq;
	private String mCode;
	private boolean mPatchFlag;
	private boolean mAutoSysCertSendOutFlag = true;
	private LZSysCertifySchema mLZSysCertifySchema = new LZSysCertifySchema();

	public PrtDealBL() {
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
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}
		// 保存数据
		String tOperate = "";
		VData tVData = new VData();
		tVData.add(mLOPRTManagerSchema);
		tVData.add(mLZSysCertifySchema);
		tOperate = "UPDATE&&INSERT";

		PrtDealBLS tPrtDealBLS = new PrtDealBLS();
		if (!tPrtDealBLS.submitData(tVData, tOperate)) {
			mErrors.copyAllErrors(tPrtDealBLS.mErrors);
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null || mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */

	private boolean dealData() {
		if (mOperate.equals("CONFIRM")) {
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			tLOPRTManagerDB.setPrtSeq(mLOPRTManagerSchema.getPrtSeq());
			if (!tLOPRTManagerDB.getInfo()) {
				mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
				return false;
			}
			// 查询打印队列的信息
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

			if (mLOPRTManagerSchema.getStateFlag() == null) {
				buildError("dealData", "无效的打印状态");
				return false;
			} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
				buildError("dealData", "该打印请求不是在请求状态");
				return false;
			}
			// 调用打印服务
			if (!callPrintService(mLOPRTManagerSchema)) {
				return false;
			}
			// 打印后的处理
			mLOPRTManagerSchema.setStateFlag("1");
			mLOPRTManagerSchema.setDoneDate(PubFun.getCurrentDate());
			mLOPRTManagerSchema.setDoneTime(PubFun.getCurrentTime());
			// 自动发送单证
			mCode = mLOPRTManagerSchema.getCode();
			mManageCom = mGlobalInput.ManageCom;
			mOperater = mGlobalInput.Operator;
			LDCodeDB tLDCodeDB = new LDCodeDB();
			tLDCodeDB.setCodeType("syscertifycode");
			tLDCodeDB.setCode(mCode);
			if (!tLDCodeDB.getInfo()) {
				mAutoSysCertSendOutFlag = false;
				return true;
			}

			LZSysCertifySchema tLZSysCertifyschema = new LZSysCertifySchema();
			logger.debug(tLDCodeDB.getCodeName());
			tLZSysCertifyschema.setCertifyCode(tLDCodeDB.getCodeName());
			tLZSysCertifyschema.setCertifyNo(mLOPRTManagerSchema.getPrtSeq());
			tLZSysCertifyschema.setSendOutCom("A" + mGlobalInput.ManageCom);
			tLZSysCertifyschema.setReceiveCom("D"
					+ mLOPRTManagerSchema.getAgentCode());
			logger.debug("D" + mLOPRTManagerSchema.getAgentCode());
			tLZSysCertifyschema.setHandler("SYS");
			tLZSysCertifyschema.setStateFlag("0");
			tLZSysCertifyschema.setHandleDate(PubFun.getCurrentDate());
			tLZSysCertifyschema.setTakeBackNo(PubFun1.CreateMaxNo("TAKEBACKNO",
					PubFun.getNoLimit(mManageCom.substring(1))));
			tLZSysCertifyschema.setSendNo(PubFun1.CreateMaxNo("TAKEBACKNO",
					PubFun.getNoLimit(mManageCom.substring(1))));
			tLZSysCertifyschema.setOperator(mOperater);
			tLZSysCertifyschema.setMakeDate(PubFun.getCurrentDate());
			tLZSysCertifyschema.setMakeTime(PubFun.getCurrentTime());
			tLZSysCertifyschema.setModifyDate(PubFun.getCurrentDate());
			tLZSysCertifyschema.setModifyTime(PubFun.getCurrentTime());
			mLZSysCertifySchema = tLZSysCertifyschema;

		} else {
			buildError("dealData", "不支持的操作字符串");
			return false;
		}

		return true;
	}

	/**
	 * 调用打印服务
	 * 
	 * @param aLOPRTManagerSchema
	 * @return
	 */
	private boolean callPrintService(LOPRTManagerSchema aLOPRTManagerSchema) {
		// 查找打印服务
		String strSQL = "";
		strSQL = "SELECT * FROM LDCode WHERE CodeType = 'print_service'";
		strSQL += " AND Code = '" + "?Code?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("Code", aLOPRTManagerSchema.getCode());
		LDCodeSet tLDCodeSet = new LDCodeDB().executeQuery(sqlbv);

		if (tLDCodeSet.size() == 0) {
			buildError("dealData", "找不到对应的打印服务类(Code = '"
					+ aLOPRTManagerSchema.getCode() + "')");
			return false;
		}
		// 调用打印服务
		LDCodeSchema tLDCodeSchema = tLDCodeSet.get(1);
		try {
			Class cls = Class.forName(tLDCodeSchema.getCodeAlias());
			PrintService ps = (PrintService) cls.newInstance();

			if (!ps.submitData(mInputData, mOperate)) {
				mErrors.copyAllErrors(ps.getErrors());
				return false;
			}

			mResult = ps.getResult();

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("callPrintService", ex.toString());
			return false;
		}

		return true;

	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "PrintManagerBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

}
