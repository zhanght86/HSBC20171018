package com.sinosoft.lis.claimnb;
import org.apache.log4j.Logger;

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author ccvip
 * @version 1.0
 */
public class LLUWPNoticeBL {
private static Logger logger = Logger.getLogger(LLUWPNoticeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private String mPrtSeq;

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public LLUWPNoticeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("INSERT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData(mResult)) {
			return false;
		}

		MMap map = new MMap();
		map.put(mLOPRTManagerSchema, "INSERT");
		mResult.add(map);

		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPubSubmit Submit ...");
		if (!tPubSubmit.submitData(mResult, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				mErrors.copyAllErrors(tPubSubmit.mErrors);
				return false;
			} else {
				buildError("submitData", "LLUWPNoticeBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return true;
		}
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @param vData
	 *            VData
	 * @return boolean
	 */
	private boolean prepareOutputData(VData vData) {

		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);

		mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);

		String ManageCom = "";
		String AgentCode = "";
		String MakeDate = "";
		String MakeTime = "";

		String tSQL = "select managecom,agentcode from lccont where contno = '"
				+ "?contno?" + "'";
		SQLwithBindVariables sqlbv =new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("contno", mLOPRTManagerSchema.getOtherNo());
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);

		mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setManageCom(tSSRS.GetText(1, 1));
		mLOPRTManagerSchema.setAgentCode(tSSRS.GetText(1, 2));
		mLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
		mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		mLOPRTManagerSchema.setExeCom(mGlobalInput.ManageCom);
		mLOPRTManagerSchema.setExeOperator(mGlobalInput.Operator);
		mLOPRTManagerSchema.setPrtType("0");
		mLOPRTManagerSchema.setStateFlag("0");

		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		mLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);

		try {
			vData.clear();
			vData.add(mGlobalInput);
			vData.add(mLOPRTManagerSchema);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	/**
	 * 获取返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LLUWPNoticeUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

}
