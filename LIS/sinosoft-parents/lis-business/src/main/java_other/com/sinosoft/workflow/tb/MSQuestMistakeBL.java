package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCIssueMistakeDB;
import com.sinosoft.lis.db.LWNotePadDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCIssueMistakeSchema;
import com.sinosoft.lis.schema.LWNotePadSchema;
import com.sinosoft.lis.vschema.LWNotePadSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class MSQuestMistakeBL {
private static Logger logger = Logger.getLogger(MSQuestMistakeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 传输数据 */
	private LCIssueMistakeSchema mLCIssueMistakeSchema = new LCIssueMistakeSchema();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public MSQuestMistakeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("after prepareOutputData...");

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "WorkFlowNotePadBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {

		if (mOperate.equals("INSERT")) {
			String tSerialNo = PubFun1.CreateMaxNo("LCIssueMistake", 20);
			mLCIssueMistakeSchema.setSerialNo(tSerialNo);
			mLCIssueMistakeSchema.setOperator(mGlobalInput.Operator);
			mLCIssueMistakeSchema.setManageCom(mGlobalInput.ManageCom);
			mLCIssueMistakeSchema.setMakeDate(mCurrentDate);
			mLCIssueMistakeSchema.setMakeTime(mCurrentTime);
			mLCIssueMistakeSchema.setModifyDate(mCurrentDate);
			mLCIssueMistakeSchema.setModifyTime(mCurrentTime);

			map.put(mLCIssueMistakeSchema, "INSERT");
		}
		if (mOperate.equals("UPDATE")) {
			LCIssueMistakeDB tLCIssueMistakeDB = new LCIssueMistakeDB();
			tLCIssueMistakeDB.setProposalContNo(mLCIssueMistakeSchema.getProposalContNo());
			tLCIssueMistakeDB.setSerialNo(mLCIssueMistakeSchema.getSerialNo());
			if(!tLCIssueMistakeDB.getInfo()){
				CError.buildErr(this, "查询问题件差错记录表失败！");
				return false;
			}
			tLCIssueMistakeDB.setErrContent(mLCIssueMistakeSchema.getErrContent());
			tLCIssueMistakeDB.setErrManageCom(mLCIssueMistakeSchema.getErrManageCom());
			tLCIssueMistakeDB.setErrorType(mLCIssueMistakeSchema.getErrorType());
			tLCIssueMistakeDB.setOperator(mGlobalInput.Operator);
			tLCIssueMistakeDB.setManageCom(mGlobalInput.ManageCom);
			tLCIssueMistakeDB.setModifyDate(mCurrentDate);
			tLCIssueMistakeDB.setModifyTime(mCurrentTime);

			map.put(tLCIssueMistakeDB.getSchema(), "UPDATE");
		}
		if (mOperate.equals("DELETE")) {

			map.put(mLCIssueMistakeSchema, "DELETE");
		}
		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mLCIssueMistakeSchema.setSchema((LCIssueMistakeSchema) mInputData
				.getObjectByObjectName("LCIssueMistakeSchema", 0));

		//String sMissionID = mLCIssueMistakeSchema.getMissionID();
//		logger.debug("lengh="
//				+ mLCIssueMistakeSchema.getNotePadCont().length());
		if (mLCIssueMistakeSchema.getErrContent().length() > 800) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "MSQuestMistakeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "输入记事本内容过长!";
			this.mErrors.addOneError(tError);
			return false;
		}

		String tProposalContNo = mLCIssueMistakeSchema.getProposalContNo();
		if (tProposalContNo == null || tProposalContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "MSQuestMistakeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据ProposalContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		String tErrorType = mLCIssueMistakeSchema.getErrorType();
		if (tErrorType == null || tErrorType.trim().equals("")) {
			CError.buildErr(this, "前台传输数据ErrorType失败！");
			return false;
		}
		
		String tErrManageCom = mLCIssueMistakeSchema.getErrManageCom();
		if (tErrManageCom == null || tErrManageCom.trim().equals("")) {
			CError.buildErr(this, "前台传输数据ErrManageCom失败！");
			return false;
		}
		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WorkFlowNotePadBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */

	public VData getResult() {
		return mResult;
	}

}
