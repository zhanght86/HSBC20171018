package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCRReportDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCRReportSchema;
import com.sinosoft.lis.vschema.LCRReportSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
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
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HL
 * @version 6.0
 */
public class InputRReportResultBL implements BusinessService {
private static Logger logger = Logger.getLogger(InputRReportResultBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mOperate;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;

	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private MMap mMap;
	private LCRReportSchema mLCRReportSchema = new LCRReportSchema();
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();

	public InputRReportResultBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            输入的数据
	 * @param cOperate
	 *            数据操作
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mResult, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---commitData---");
		logger.debug("Start  Submit...");

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.put(mLCRReportSchema, "UPDATE");
		if (mLCCUWMasterSchema != null) {
			map.put(mLCCUWMasterSchema, "UPDATE");
		}
		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "UWRReportAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            输入数据
	 * @param cOperate
	 *            数据操作
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "InputRReportResultAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLCRReportSchema.setSchema((LCRReportSchema) mTransferData
				.getValueByName("LCRReportSchema"));
		if (mLCRReportSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "InputRReportResultAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据LCRReport失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "InputRReportResultAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mOperate = cOperate;
		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "InputRReportResultAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的mCont
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "InputRReportResultAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		if (prepareReport() == false) {
			return false;
		}

		return true;
	}

	private boolean prepareReport() {
		// 取回复内容
		String tReplyContent = mLCRReportSchema.getReplyContente();
		// 取生调费用
		double tRreportFee = mLCRReportSchema.getRReportFee();
		logger.debug(tReplyContent);
		// 取生调记录
		String tsql = "select * from LCRReport where ContNo = '"
				+ "?ContNo?" + "' and prtseq =  '"
				+ "?prtseq?" + "' and replyflag = '0'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tsql);
		sqlbv.put("ContNo", mLCRReportSchema.getProposalContNo());
		sqlbv.put("prtseq", mLCRReportSchema.getPrtSeq());
		logger.debug("sql:" + tsql);
		LCRReportDB tLCRReportDB = new LCRReportDB();
		LCRReportSet tLCRReportSet = new LCRReportSet();

		tLCRReportSet = tLCRReportDB.executeQuery(sqlbv);

		if (tLCRReportSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportReplyBL";
			tError.functionName = "prepareReport";
			tError.errorMessage = "此单已经回复!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			mLCRReportSchema = tLCRReportSet.get(1);
		}

		mLCRReportSchema.setReplyContente(tReplyContent);
		mLCRReportSchema.setRReportFee(tRreportFee);
		mLCRReportSchema.setReplyFlag("1");
		mLCRReportSchema.setReplyOperator(mOperater);
		mLCRReportSchema.setReplyDate(PubFun.getCurrentDate());
		mLCRReportSchema.setReplyTime(PubFun.getCurrentTime());
		mLCRReportSchema.setModifyDate(PubFun.getCurrentDate());
		mLCRReportSchema.setModifyTime(PubFun.getCurrentTime());

		// 核保主表信息
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mLCRReportSchema.getContNo());

		if (tLCCUWMasterDB.getInfo() == false) {
			// // @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "UWRReportBL";
			// tError.functionName = "prepareReport";
			// tError.errorMessage = "无核保主表信息!";
			// this.mErrors.addOneError(tError);
			// return false;
			mLCCUWMasterSchema = null; // 为使保全核保能复用新契约核保下发生调通知书，更改查询新契约核保表的内容。
										// modified by zhangrong
		} else {
			mLCCUWMasterSchema = tLCCUWMasterDB.getSchema();
			mLCCUWMasterSchema.setReportFlag("2");
		}
		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return boolean
	 */
	private boolean prepareTransferData() {

		return true;
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回工作流中的Lwfieldmap所描述的值
	 * 
	 * @return TransferData
	 */
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}
}
