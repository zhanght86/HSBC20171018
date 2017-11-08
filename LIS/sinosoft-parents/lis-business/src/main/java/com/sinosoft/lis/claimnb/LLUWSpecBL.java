package com.sinosoft.lis.claimnb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LLUWSpecMasterSchema;
import com.sinosoft.lis.schema.LLUWSpecSubSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author wanzh
 * @version 1.0
 */

public class LLUWSpecBL {
private static Logger logger = Logger.getLogger(LLUWSpecBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private MMap mMMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	/** 核保主表 */
	private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
	/** 特约表 */
	private LLUWSpecMasterSchema mLLUWSpecMasterSchema = new LLUWSpecMasterSchema();
	/** 理赔特约承保轨迹表 */
	private LLUWSpecSubSchema mLLUWSpecSubSchema = new LLUWSpecSubSchema();
	/** 数据操作字符串 */
	private String mOperater = "";
	private String mManageCom = "";
	private String mOperate = "";
	/** 业务数据操作字符串 */
	private String mClmNo = "";
	private String mPolNo = "";
	private String mProposalNo = "";
	private String mSerialNo = "";
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public LLUWSpecBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("------------------理赔核保-----特约承保操作LLUWSpecBL-----开始------------------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
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
		if (!pubSubmit()) {
			return false;
		}
		logger.debug("------------------理赔核保-----特约承保操作LLUWSpecBL-----结束------------------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mInputData = cInputData;
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLCUWMasterSchema = (LCUWMasterSchema) cInputData
				.getObjectByObjectName("LCUWMasterSchema", 0);
		mLLUWSpecMasterSchema = (LLUWSpecMasterSchema) cInputData
				.getObjectByObjectName("LLUWSpecMasterSchema", 0);
		mLLUWSpecSubSchema = (LLUWSpecSubSchema) cInputData
				.getObjectByObjectName("LLUWSpecSubSchema", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		this.mOperate = StrTool.cTrim(cOperate);
		this.mClmNo = StrTool.cTrim((String) mTransferData
				.getValueByName("ClmNo")); // 赔案号
		this.mPolNo = StrTool.cTrim((String) mTransferData
				.getValueByName("PolNo"));
		this.mProposalNo = StrTool.cTrim((String) mTransferData
				.getValueByName("ProposalNo"));
		this.mSerialNo = (String) mTransferData.getValueByName("SerialNo");
		if (this.mSerialNo == "" || this.mSerialNo == null
				|| mSerialNo.trim().equals("")) {
			mSerialNo = "";
		}
		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "mOperater";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operater失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "mManageCom";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 删除一条指定的特约信息
		if (mOperate.equals("DELETE")) {
			getDelete();
		}
		// 准备数据
		if (mOperate.equals("SAVE")) {
			if (prepareLLUWSpecMaster() == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 删除指定的数据
	 * 
	 * @return
	 */
	private boolean getDelete() {
//		String tProposalNo = StrTool.cTrim(mLLUWSpecMasterSchema
//				.getProposalNo());
//		String tDelSql = "delete from LLUWSpecMaster where 1=1 "
//				+ " and ProposalNo ='" + tProposalNo + "'"
//				+ " and SerialNo = '" + mSerialNo + "'" + " and clmno = '"
//				+ mClmNo + "'";
//		mMMap.put(tDelSql, "DELETE");
		return true;
	}

	/**
	 * 准备特约资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareLLUWSpecMaster() {

		// 准备二核核保主表信息
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setProposalNo(mProposalNo);
		if (tLCUWMasterDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCUWMasterDB";
			tError.functionName = "prepareSpec";
			tError.errorMessage = "无二核批单核保主表信息!";
			this.mErrors.addOneError(tError);
			return false;
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.1 :准备LCUWMaster表数据
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSpecReason = mLCUWMasterSchema.getSpecReason();
		mLCUWMasterSchema.setSchema(tLCUWMasterDB.getSchema());
		if (tSpecReason != null && !tSpecReason.trim().equals("")) {
			mLCUWMasterSchema.setSpecReason(tSpecReason);
		} else {
			mLCUWMasterSchema.setSpecReason("");
		}
		if (mLCUWMasterSchema != null) {
			mLCUWMasterSchema.setSpecFlag("1"); // 特约标识
		} else {
			mLCUWMasterSchema.setSpecFlag("0"); // 特约标识
		}

		mLCUWMasterSchema.setSpecReason(mLCUWMasterSchema.getSpecReason());
		mLCUWMasterSchema.setOperator(mOperater);
		mLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		mLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		// 准备LCUWMaster表数据
		mMMap.put(mLCUWMasterSchema, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.2:准备LLUWSpecMaster表数据
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// 生成特约流水号
		if (mSerialNo == "" || mSerialNo == null) {
			String tSerialNo = "CLM" + PubFun1.CreateMaxNo("SpecCode", 10);
			if (tSerialNo == "" || tSerialNo == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLUWSpecBL";
				tError.functionName = "prepareSpec";
				tError.errorMessage = "在后台没有生成特约流水号!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				mSerialNo = tSerialNo;
			}
		}
		if (mLLUWSpecMasterSchema != null) {
			mLLUWSpecMasterSchema.setClmNo(mClmNo);
			mLLUWSpecMasterSchema.setSerialNo(mSerialNo);
			mLLUWSpecMasterSchema
					.setGrpContNo(mLCUWMasterSchema.getGrpContNo());
//			mLLUWSpecMasterSchema.setPolNo(mPolNo);
//			mLLUWSpecMasterSchema.setProposalNo(mProposalNo);
			mLLUWSpecMasterSchema.setPrtFlag("1");
			mLLUWSpecMasterSchema.setBackupType("");
			mLLUWSpecMasterSchema.setSpecContent(mLLUWSpecMasterSchema
					.getSpecContent());
			mLLUWSpecMasterSchema.setOperator(mOperater);
			mLLUWSpecMasterSchema.setMakeDate(mCurrentDate);
			mLLUWSpecMasterSchema.setMakeTime(mCurrentTime);
			mLLUWSpecMasterSchema.setModifyDate(mCurrentDate);
			mLLUWSpecMasterSchema.setModifyTime(mCurrentTime);
			mMMap.put(mLLUWSpecMasterSchema, "DELETE&INSERT");
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－ NO.3:准备LLUWSpecSub表数据
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mLLUWSpecSubSchema != null) {
			mLLUWSpecSubSchema.setClmNo(mClmNo);
			mLLUWSpecSubSchema.setSerialNo(mSerialNo);
			mLLUWSpecSubSchema.setGrpContNo(mLCUWMasterSchema.getGrpContNo());
//			mLLUWSpecSubSchema.setPolNo(mPolNo);
			mLLUWSpecSubSchema.setBatNo(mLLUWSpecSubSchema.getBatNo());
//			mLLUWSpecSubSchema.setProposalNo(mProposalNo);
			mLLUWSpecSubSchema.setPrtFlag("1");
			mLLUWSpecSubSchema.setBackupType("");
			mLLUWSpecSubSchema.setSpecContent(mLLUWSpecSubSchema
					.getSpecContent());
			mLLUWSpecSubSchema.setOperator(mOperater);
			mLLUWSpecSubSchema.setMakeDate(mCurrentDate);
			mLLUWSpecSubSchema.setMakeTime(mCurrentTime);
			mLLUWSpecSubSchema.setModifyDate(mCurrentDate);
			mLLUWSpecSubSchema.setModifyTime(mCurrentTime);
			mMMap.put(mLLUWSpecSubSchema, "DELETE&INSERT");

		}
		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);
		mResult.clear();
		mResult.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			this.mErrors.addOneError("数据提交失败,原因"
					+ tPubSubmit.mErrors.getError(0).errorMessage);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "96";

		String tOperate = "SAVE";// DELETE
		String tClmNo = "90000014346";
		String tContNo = "NJD10326111000066";
		String tPolNo = "NJD10326111000066000";
		String tProposalNo = "0NJD105261610003490";
		String tSerialNo = "";
		String tSpecReason = "风了";
		String tSpecContent = " 真的吗  ";
		String tBatNo = "6100000000012";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", tClmNo);
		tTransferData.setNameAndValue("ContNo", tContNo);
		tTransferData.setNameAndValue("PolNo", tPolNo);
		tTransferData.setNameAndValue("ProposalNo", tProposalNo);
		tTransferData.setNameAndValue("SerialNo", tSerialNo);
		tTransferData.setNameAndValue("SpecContent", tSpecContent);

		LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
		mLCUWMasterSchema.setProposalNo(tProposalNo);
		mLCUWMasterSchema.setSpecReason(tSpecReason);

		LLUWSpecMasterSchema tLLUWSpecMasterSchema = new LLUWSpecMasterSchema();
//		tLLUWSpecMasterSchema.setPolNo(tPolNo);
//		tLLUWSpecMasterSchema.setPolNo(tPolNo);
		tLLUWSpecMasterSchema.setClmNo(tClmNo);
		tLLUWSpecMasterSchema.setContNo(tContNo);
		tLLUWSpecMasterSchema.setSpecContent(tSpecContent);

		LLUWSpecSubSchema tLLUWSpecSubSchema = new LLUWSpecSubSchema();
//		tLLUWSpecSubSchema.setPolNo(tPolNo);
		tLLUWSpecSubSchema.setClmNo(tClmNo);
		tLLUWSpecSubSchema.setContNo(tContNo);
		tLLUWSpecSubSchema.setSpecContent(tSpecContent);
		tLLUWSpecSubSchema.setBatNo(tBatNo);

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		tVData.add(mLCUWMasterSchema);
		tVData.add(tLLUWSpecMasterSchema);
		tVData.add(tLLUWSpecSubSchema);

		LLUWSpecBL tLLUWSpecBL = new LLUWSpecBL();
		tLLUWSpecBL.submitData(tVData, tOperate);
		// tLLUWSpecBL.pubSubmit();
		int n = tLLUWSpecBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "提示信息: "
					+ tLLUWSpecBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}

	}

}
