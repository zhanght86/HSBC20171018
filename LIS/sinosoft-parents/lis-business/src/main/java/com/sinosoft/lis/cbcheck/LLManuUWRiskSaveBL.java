package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LLUWMasterDB;
import com.sinosoft.lis.db.LLUWSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LLUWMasterSchema;
import com.sinosoft.lis.schema.LLUWSubSchema;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LLUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LLManuUWRiskSaveBL {
private static Logger logger = Logger.getLogger(LLManuUWRiskSaveBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private VData mInputData;
	private GlobalInput tGI = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;
	private String mOperator;
	private String mManageCom;

	private TransferData mTransferData = new TransferData();

	/** 业务操作类 */
	private LLUWMasterSchema mLLUWMasterSchema = new LLUWMasterSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LLUWSubSet mLLUWSubSet = new LLUWSubSet();
	private LLUWSubSchema mLLUWSubSchema = new LLUWSubSchema();

	/** 业务操作字符串 */
	private String mUWFlag = "";
	private String mUWIdea = "";
	private String mSugPassFlag;
	private String mSugUWIdea;
	private String mPreamnt;
	private String mBatNo;
	private String mClmNo;

	public LLManuUWRiskSaveBL() {
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
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("Operate==" + cOperate);
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("After getinputdata");

		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("After dealData！");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("After prepareOutputData");

		logger.debug("Start DisDesbBL Submit...");

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError.buildErr(this, "数据提交失败!") ;

			return false;
		}

		logger.debug("DisDesbBL end");

		return true;
	}

	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

//		map.put(mLCPolSchema, "UPDATE");
		map.put(mLLUWMasterSchema, "UPDATE");
		map.put(mLLUWSubSchema, "INSERT");

		mResult.add(map);

		return true;
	}

	/**
	 * dealData
	 * 
	 * @return boolean
	 */
	private boolean dealData() {

		mLCPolSchema.setUWFlag(mUWFlag);
		mLCPolSchema.setUWCode(mOperator);
		mLCPolSchema.setUWDate(PubFun.getCurrentDate());
		mLCPolSchema.setUWTime(PubFun.getCurrentTime());

		// 每次核保后uwno加1
		int uwno = mLLUWMasterSchema.getUWNo() + 1;

		mLLUWMasterSchema.setPassFlag(mUWFlag);
		mLLUWMasterSchema.setUWIdea(mUWIdea);
		mLLUWMasterSchema.setSugPassFlag(mSugPassFlag);
		mLLUWMasterSchema.setSugUWIdea(mSugUWIdea);
		mLLUWMasterSchema.setAutoUWFlag("2");
		mLLUWMasterSchema.setUWNo(uwno); // 表示核保次数的序列号
		mLLUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		mLLUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		mLLUWMasterSchema.setOperator(mOperator); // 操作员
		mLLUWMasterSchema.setChangePolReason(mPreamnt);
		mLLUWMasterSchema.setCaseNo(mClmNo);

		LLUWSubDB tLLUWSubDB = new LLUWSubDB();
		//tLCUWSubDB.setProposalNo(mLCUWMasterSchema.getProposalNo());
		String tSql = "select * from lluwsub where PolNo='"+"?PolNo?"+"'"
						+ " and caseno='"+"?caseno?"+"' and batno='"+"?batno?"+"'"
						+ " order by uwno desc";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("PolNo", mLLUWMasterSchema.getPolNo());
		sqlbv.put("caseno", mClmNo);
		sqlbv.put("batno", mBatNo);
		mLLUWSubSet = tLLUWSubDB.executeQuery(sqlbv);
		if (mLLUWSubSet == null || mLLUWSubSet.size() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			//CError.buildErr(this, "查询核保轨迹表失败!") ;
			//return false;
		}

		int m = mLLUWSubSet.size();
		if (m >= 0) {
			//m++; // 核保次数

			mLLUWSubSchema.setUWNo(mLLUWSubSet.get(1).getUWNo()+1); // 第几次核保
			mLLUWSubSchema.setContNo(mLLUWMasterSchema.getContNo());
			mLLUWSubSchema.setPolNo(mLLUWMasterSchema.getPolNo());
			mLLUWSubSchema.setGrpContNo(mLLUWMasterSchema.getGrpContNo());
			mLLUWSubSchema.setProposalContNo(mLLUWMasterSchema
					.getProposalContNo());
			mLLUWSubSchema.setBatNo(mBatNo);
			mLLUWSubSchema.setCaseNo(mClmNo);
			mLLUWSubSchema.setProposalNo(mLLUWMasterSchema.getProposalNo());
			mLLUWSubSchema.setInsuredNo(mLLUWMasterSchema.getInsuredNo());
			mLLUWSubSchema.setInsuredName(mLLUWMasterSchema.getInsuredName());
			mLLUWSubSchema.setAppntNo(mLLUWMasterSchema.getAppntNo());
			mLLUWSubSchema.setAppntName(mLLUWMasterSchema.getAppntName());
			mLLUWSubSchema.setAgentCode(mLLUWMasterSchema.getAgentCode());
			mLLUWSubSchema.setAgentGroup(mLLUWMasterSchema.getAgentGroup());
			mLLUWSubSchema.setUWGrade(mLLUWMasterSchema.getUWGrade()); // 核保级别
			mLLUWSubSchema.setAppGrade(mLLUWMasterSchema.getAppGrade()); // 申请级别
			mLLUWSubSchema.setAutoUWFlag(mLLUWMasterSchema.getAutoUWFlag());
			mLLUWSubSchema.setState(mLLUWMasterSchema.getState());
			mLLUWSubSchema.setPassFlag(mUWFlag);
			mLLUWSubSchema.setPostponeDay(mLLUWMasterSchema.getPostponeDay());
			mLLUWSubSchema.setPostponeDate(mLLUWMasterSchema.getPostponeDate());
			mLLUWSubSchema.setUpReportContent(mLLUWMasterSchema
					.getUpReportContent());
			mLLUWSubSchema.setHealthFlag(mLLUWMasterSchema.getHealthFlag());
			mLLUWSubSchema.setSpecFlag(mLLUWMasterSchema.getSpecFlag());
			mLLUWSubSchema.setSpecReason(mLLUWMasterSchema.getSpecReason());
			mLLUWSubSchema.setQuesFlag(mLLUWMasterSchema.getQuesFlag());
			mLLUWSubSchema.setReportFlag(mLLUWMasterSchema.getReportFlag());
			mLLUWSubSchema.setChangePolFlag(mLLUWMasterSchema
					.getChangePolFlag());
			mLLUWSubSchema.setChangePolReason(mLLUWMasterSchema
					.getChangePolReason());
			mLLUWSubSchema.setAddPremReason(mLLUWMasterSchema
					.getAddPremReason());
			mLLUWSubSchema.setPrintFlag(mLLUWMasterSchema.getPrintFlag());
			mLLUWSubSchema.setPrintFlag2(mLLUWMasterSchema.getPrintFlag2());
			mLLUWSubSchema.setUWIdea(mLLUWMasterSchema.getUWIdea());
			mLLUWSubSchema.setOperator(mLLUWMasterSchema.getOperator()); // 操作员
			mLLUWSubSchema.setManageCom(mLLUWMasterSchema.getManageCom());
			mLLUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			mLLUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			mLLUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			mLLUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLUWSubDB.mErrors);
			CError.buildErr(this, "核保轨迹表查询失败!") ;
			return false;
		}

		return true;
	}

	/**
	 * checkData
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mLLUWMasterSchema.getPolNo());
		LLUWMasterDB tLLUWMasterDB = new LLUWMasterDB();
		tLLUWMasterDB.setPolNo(mLLUWMasterSchema.getPolNo());
		tLLUWMasterDB.setCaseNo(mClmNo);

		if (!tLCPolDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "查询险种保单表失败!") ;
			return false;
		}
		mLCPolSchema = tLCPolDB.getSchema();

		if (!tLLUWMasterDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "查询险种核保险种表失败!") ;
			return false;
		}
		mLLUWMasterSchema = tLLUWMasterDB.getSchema();
		
		if(((tLLUWMasterDB.getAddPremFlag()!=null && tLLUWMasterDB.getAddPremFlag().equals("1"))
				|| (tLLUWMasterDB.getChangePolFlag()!=null && tLLUWMasterDB.getChangePolFlag().equals("1")))
				&&mUWFlag.equals("9"))
		{
			CError.buildErr(this, "该险种有加费或保险计划变更，不能下标准体核保结论!") ;
			return false;
		}

		// if(!checkUW())
		// return false;

		return true;
	}

	/**
	 * checkUW 校验时否经过核保
	 * 
	 * @return boolean
	 */
	private boolean checkUW() {
		if (mLLUWMasterSchema.getPassFlag().equals("1")
				|| mLLUWMasterSchema.getPassFlag().equals("4")
				|| mLLUWMasterSchema.getPassFlag().equals("9"))

		{
			// @@错误处理
			CError.buildErr(this, "此险种核保结论已下!") ;
			return false;
		}
		return true;
	}

	/**
	 * getInputData
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 公用变量
		tGI = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		mLLUWMasterSchema = (LLUWMasterSchema) cInputData
				.getObjectByObjectName("LLUWMasterSchema", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mOperator = tGI.Operator;
		if (mOperator == null || mOperator.length() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据Operator失败!") ;
			return false;
		}

		if (mLLUWMasterSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this, "前台传输数据LCUWMasterSchema失败!") ;
			return false;
		}

		mUWFlag = mLLUWMasterSchema.getPassFlag();
		if (mUWFlag == null || mUWFlag.length() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this, "前台传输数据UWFlag失败!") ;
			return false;
		}
		
		mBatNo = (String) mTransferData.getValueByName("BatNo");
		mClmNo = (String) mTransferData.getValueByName("ClmNo");
		if(mBatNo==null||"".equals(mBatNo)){
			CError.buildErr(this, "前台传输数据mBatNo失败！");
			return false;
		}
		if(mClmNo==null||"".equals(mClmNo)){
			CError.buildErr(this, "前台传输数据mClmNo失败！");
			return false;
		}
		
		mUWIdea = mLLUWMasterSchema.getUWIdea();
		mSugPassFlag = mLLUWMasterSchema.getSugPassFlag();
		mSugUWIdea = mLLUWMasterSchema.getSugUWIdea();
		mPreamnt = mLLUWMasterSchema.getChangePolReason();

		return true;
	}
}
