package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.RnewIndUWMasterDB;
import com.sinosoft.lis.db.RnewIndUWSubDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.RnewIndUWMasterSchema;
import com.sinosoft.lis.schema.RnewIndUWSubSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.vschema.RnewIndUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 被保人核保
 * </p>
 * <p>
 * Description: 在人工核保时为被保人下核保结论
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class RnewInsuredUWBL {
private static Logger logger = Logger.getLogger(RnewInsuredUWBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	/** 业务处理类 */
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
	private RnewIndUWMasterSchema mRnewIndUWMasterSchema = new RnewIndUWMasterSchema();
	private RnewIndUWMasterSchema mRnewIndUWMasterSchemaNew = new RnewIndUWMasterSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private RnewIndUWSubSchema mRnewIndUWSubSchema = new RnewIndUWSubSchema();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;

	/** 业务数据 */
	private String mContNo;
	private String mInsuredNo;

	public RnewInsuredUWBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");

		// 提交数据
		if (cOperate.equals("submit")) {
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError.buildErr(this, "数据提交失败!") ;
				return false;
			}
		}

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.put(mRnewIndUWMasterSchemaNew, "DELETE&INSERT");
		map.put(mRnewIndUWSubSchema, "INSERT");
		map.put(mLCInsuredSchema, "UPDATE");

		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "查询合同信息出错!") ;
			return false;
		}
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mContNo);
		tLCInsuredDB.setInsuredNo(mInsuredNo);
		if (!tLCInsuredDB.getInfo()) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "查询被保人信息出错!") ;
			return false;
		}
		mLCInsuredSchema = tLCInsuredDB.getSchema();

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
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
			CError.buildErr(this,"前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据mOperator失败!") ;
			return false;
		}

		// 获得管理机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据mOperator失败!");
			return false;
		}

		// 获得当前ContNo
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中ContNo失败!");
			return false;
		}

		// 获得当前InsuredNo
		mInsuredNo = (String) mTransferData.getValueByName("InsuredNo");
		if (mInsuredNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中InsuredNo失败!");
			return false;
		}

		// 获得核保结论
		mRnewIndUWMasterSchema = (RnewIndUWMasterSchema) mTransferData
				.getValueByName("RnewIndUWMasterSchema");
		if (mRnewIndUWMasterSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中RnewIndUWMasterSchema失败!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
//		if (!prepareInsured()) {
//			return false;
//		}

		if (!prepareIndUW()) {
			return false;
		}

		return true;
	}

	/**
	 * prepareIndUW
	 * 
	 * @return boolean
	 */
	private boolean prepareIndUW() {
		RnewIndUWMasterDB tRnewIndUWMasterDB = new RnewIndUWMasterDB();
		tRnewIndUWMasterDB.setContNo(mContNo);
		tRnewIndUWMasterDB.setInsuredNo(mInsuredNo);

		if (tRnewIndUWMasterDB.getInfo()) {
			// 准备被保人核保主表数据
			mRnewIndUWMasterSchemaNew = tRnewIndUWMasterDB.getSchema();
			int uwno = mRnewIndUWMasterSchemaNew.getUWNo();
		//	mRnewIndUWMasterSchemaNew.setPassFlag(mRnewIndUWMasterSchema
		//			.getPassFlag());
			mRnewIndUWMasterSchemaNew.setUWIdea(mRnewIndUWMasterSchema.getUWIdea());
			mRnewIndUWMasterSchemaNew.setUWNo(uwno + 1);
		//	mRnewIndUWMasterSchemaNew.setSugPassFlag(mRnewIndUWMasterSchema
		//			.getSugPassFlag());
		//	mRnewIndUWMasterSchemaNew.setSugUWIdea(mRnewIndUWMasterSchema
		//			.getSugUWIdea());
			mRnewIndUWMasterSchemaNew.setOperator(mOperator); // 操作员		
			mRnewIndUWMasterSchemaNew.setModifyDate(PubFun.getCurrentDate());
			mRnewIndUWMasterSchemaNew.setModifyTime(PubFun.getCurrentTime());

			// 准备被保人核保子表数据
			RnewIndUWSubDB tRnewIndUWSubDB = new RnewIndUWSubDB();
			String tSql = "select * from RnewIndUWSub where contno='?mContNo?' and insuredno='?mInsuredNo?' order by uwno desc";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("mContNo", mContNo);
			sqlbv.put("mInsuredNo", mInsuredNo);
			RnewIndUWSubSet tRnewIndUWSubSet = new RnewIndUWSubSet();
			tRnewIndUWSubSet = tRnewIndUWSubDB.executeQuery(sqlbv);
			if(tRnewIndUWSubSet==null)
			{
				CError.buildErr(this,"查询被保人核保子表出错!");
				return false;
			}
			mRnewIndUWSubSchema = tRnewIndUWSubSet.get(1);
			mRnewIndUWSubSchema.setContNo(mRnewIndUWMasterSchema.getContNo());
			mRnewIndUWSubSchema.setInsuredNo(mRnewIndUWMasterSchema.getInsuredNo());
		//	mRnewIndUWSubSchema.setPassFlag(mRnewIndUWMasterSchema.getPassFlag());
			mRnewIndUWSubSchema.setUWIdea(mRnewIndUWMasterSchema.getUWIdea());
			mRnewIndUWSubSchema.setUWNo(tRnewIndUWSubSet.get(1).getUWNo() + 1);
		//	mRnewIndUWSubSchema.setSugPassFlag(mRnewIndUWMasterSchema
		//			.getSugPassFlag());
		//	mRnewIndUWSubSchema.setSugUWIdea(mRnewIndUWMasterSchema.getSugUWIdea());
			mRnewIndUWSubSchema.setOperator(mOperator); // 操作员	
			mRnewIndUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			mRnewIndUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			mRnewIndUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			mRnewIndUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// 准备被保人核保主表数据
			mRnewIndUWMasterSchemaNew.setContNo(mContNo);
			mRnewIndUWMasterSchemaNew.setGrpContNo(mLCInsuredSchema
					.getGrpContNo());
			mRnewIndUWMasterSchemaNew.setProposalContNo(mLCInsuredSchema
					.getContNo());
			mRnewIndUWMasterSchemaNew.setUWNo(1);
			mRnewIndUWMasterSchemaNew.setInsuredNo(mLCInsuredSchema
					.getInsuredNo());
			mRnewIndUWMasterSchemaNew.setInsuredName(mLCInsuredSchema.getName());
			mRnewIndUWMasterSchemaNew.setAppntNo(mLCInsuredSchema.getAppntNo());
			mRnewIndUWMasterSchemaNew.setAppntName(mLCContSchema.getAppntName());
			mRnewIndUWMasterSchemaNew.setAgentCode(mLCContSchema.getAgentCode());
			mRnewIndUWMasterSchemaNew
					.setAgentGroup(mLCContSchema.getAgentGroup());
			mRnewIndUWMasterSchemaNew.setUWGrade(""); // 核保级别
			mRnewIndUWMasterSchemaNew.setAppGrade(""); // 申报级别
			mRnewIndUWMasterSchemaNew.setPostponeDay("");
			mRnewIndUWMasterSchemaNew.setPostponeDate("");
			mRnewIndUWMasterSchemaNew.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			mRnewIndUWMasterSchemaNew
					.setState(mRnewIndUWMasterSchema.getPassFlag());
			mRnewIndUWMasterSchemaNew.setPassFlag(mRnewIndUWMasterSchema
					.getPassFlag());
			mRnewIndUWMasterSchemaNew.setHealthFlag("0");
			mRnewIndUWMasterSchemaNew.setSpecFlag("0");
			mRnewIndUWMasterSchemaNew.setQuesFlag("0");
			mRnewIndUWMasterSchemaNew.setReportFlag("0");
			mRnewIndUWMasterSchemaNew.setChangePolFlag("0");
			mRnewIndUWMasterSchemaNew.setPrintFlag("0");
			mRnewIndUWMasterSchemaNew.setPrintFlag2("0");
			mRnewIndUWMasterSchemaNew.setManageCom(mLCInsuredSchema
					.getManageCom());
			mRnewIndUWMasterSchemaNew.setUWIdea(mRnewIndUWMasterSchema.getUWIdea());
			mRnewIndUWMasterSchemaNew.setUpReportContent("");
			mRnewIndUWMasterSchemaNew.setOperator(mOperator); // 操作员
			mRnewIndUWMasterSchemaNew.setMakeDate(PubFun.getCurrentDate());
			mRnewIndUWMasterSchemaNew.setMakeTime(PubFun.getCurrentTime());
			mRnewIndUWMasterSchemaNew.setModifyDate(PubFun.getCurrentDate());
			mRnewIndUWMasterSchemaNew.setModifyTime(PubFun.getCurrentTime());
			mRnewIndUWMasterSchemaNew.setSugPassFlag(mRnewIndUWMasterSchema
					.getSugPassFlag());
			mRnewIndUWMasterSchemaNew.setSugUWIdea(mRnewIndUWMasterSchema
					.getSugUWIdea());

			// 准备被保人核保子表数据
			mRnewIndUWSubSchema.setContNo(mContNo);
			mRnewIndUWSubSchema.setGrpContNo(mLCInsuredSchema.getGrpContNo());
			mRnewIndUWSubSchema.setProposalContNo(mLCInsuredSchema.getContNo());
			mRnewIndUWSubSchema.setUWNo(1);
			mRnewIndUWSubSchema.setInsuredNo(mLCInsuredSchema.getInsuredNo());
			mRnewIndUWSubSchema.setInsuredName(mLCInsuredSchema.getName());
			mRnewIndUWSubSchema.setAppntNo(mLCInsuredSchema.getAppntNo());
			mRnewIndUWSubSchema.setAppntName(mLCContSchema.getAppntName());
			mRnewIndUWSubSchema.setAgentCode(mLCContSchema.getAgentCode());
			mRnewIndUWSubSchema.setAgentGroup(mLCContSchema.getAgentGroup());
			mRnewIndUWSubSchema.setUWGrade(""); // 核保级别
			mRnewIndUWSubSchema.setAppGrade(""); // 申报级别
			mRnewIndUWSubSchema.setPostponeDay("");
			mRnewIndUWSubSchema.setPostponeDate("");
			mRnewIndUWSubSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			mRnewIndUWSubSchema.setState(mRnewIndUWMasterSchema.getPassFlag());
			mRnewIndUWSubSchema.setPassFlag(mRnewIndUWMasterSchema.getPassFlag());
			mRnewIndUWSubSchema.setHealthFlag("0");
			mRnewIndUWSubSchema.setSpecFlag("0");
			mRnewIndUWSubSchema.setQuesFlag("0");
			mRnewIndUWSubSchema.setReportFlag("0");
			mRnewIndUWSubSchema.setChangePolFlag("0");
			mRnewIndUWSubSchema.setPrintFlag("0");
			mRnewIndUWSubSchema.setPrintFlag2("0");
			mRnewIndUWSubSchema.setManageCom(mLCInsuredSchema.getManageCom());
			mRnewIndUWSubSchema.setUWIdea(mRnewIndUWMasterSchema.getUWIdea());
			mRnewIndUWSubSchema.setUpReportContent("");
			mRnewIndUWSubSchema.setOperator(mOperator); // 操作员
			mRnewIndUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			mRnewIndUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			mRnewIndUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			mRnewIndUWSubSchema.setModifyTime(PubFun.getCurrentTime());
			mRnewIndUWSubSchema.setSugPassFlag(mRnewIndUWMasterSchema
					.getSugPassFlag());
			mRnewIndUWSubSchema.setSugUWIdea(mRnewIndUWMasterSchema.getSugUWIdea());
		}
		return true;
	}

	/**
	 * prepareInsured
	 * 
	 * @return boolean
	 */
	private boolean prepareInsured() {
		mLCInsuredSchema.setUWFlag(mRnewIndUWMasterSchema.getPassFlag());
		mLCInsuredSchema.setUWCode(mOperator);
		mLCInsuredSchema.setUWDate(PubFun.getCurrentDate());
		mLCInsuredSchema.setUWTime(PubFun.getCurrentTime());

		return true;
	}

	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
