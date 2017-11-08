package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 人工核保险种结论
 * </p>
 * <p>
 * Description: 人工核保险种结论保存
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0 modify by zhangxing
 */

public class ManuUWRiskSaveBL {
private static Logger logger = Logger.getLogger(ManuUWRiskSaveBL.class);
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
	private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCUWSubSchema mLCUWSubSchema = new LCUWSubSchema();

	/** 业务操作字符串 */
	private String mUWFlag = "";
	private String mUWIdea = "";
	private String mSugPassFlag;
	private String mSugUWIdea;
	private String mPreamnt;

	public ManuUWRiskSaveBL() {
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

		map.put(mLCPolSchema, "UPDATE");
		map.put(mLCUWMasterSchema, "UPDATE");
		map.put(mLCUWSubSchema, "INSERT");

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
		int uwno = mLCUWMasterSchema.getUWNo() + 1;

		//上次核保结论与本次核保结论在不通过和通过之间变化时需要发核保通知书 ln add 2009-06-30
		if(((mLCUWMasterSchema.getPassFlag()!=null && !mLCUWMasterSchema.getPassFlag().equals("")) 
				&& (mLCUWMasterSchema.getPassFlag().equals("1") 
				|| mLCUWMasterSchema.getPassFlag().equals("2")
				|| mLCUWMasterSchema.getPassFlag().equals("a")))
				&& (mUWFlag.equals("4") || mUWFlag.equals("9")))
		{
			mLCUWMasterSchema.setChangePolFlag("1");//修改核保决定标志
		}
		else if(mUWFlag.equals("1") || mUWFlag.equals("2")|| mUWFlag.equals("a"))
		{
			mLCUWMasterSchema.setChangePolFlag("1");//修改核保决定标志
		}		
		mLCUWMasterSchema.setPassFlag(mUWFlag);
		mLCUWMasterSchema.setUWIdea(mUWIdea);
		mLCUWMasterSchema.setSugPassFlag(mSugPassFlag);
		mLCUWMasterSchema.setSugUWIdea(mSugUWIdea);
		mLCUWMasterSchema.setAutoUWFlag("2");
		mLCUWMasterSchema.setUWNo(uwno); // 表示核保次数的序列号
		mLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		mLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		mLCUWMasterSchema.setOperator(mOperator); // 操作员		
		mLCUWMasterSchema.setChangePolReason(mPreamnt);

		LCUWSubDB tLCUWSubDB = new LCUWSubDB();
		//tLCUWSubDB.setProposalNo(mLCUWMasterSchema.getProposalNo());
		String tSql = "select * from lcuwsub where ProposalNo='"+"?ProposalNo?"+"' order by uwno desc";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("ProposalNo", mLCUWMasterSchema.getProposalNo());
		mLCUWSubSet = tLCUWSubDB.executeQuery(sqlbv);
		if (mLCUWSubSet == null || mLCUWSubSet.size() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this, "查询核保轨迹表失败!") ;
			return false;
		}

		int m = mLCUWSubSet.size();
		if (m >= 0) {
			//m++; // 核保次数

			mLCUWSubSchema.setUWNo(mLCUWSubSet.get(1).getUWNo()+1); // 第几次核保
			mLCUWSubSchema.setContNo(mLCUWMasterSchema.getContNo());
			mLCUWSubSchema.setPolNo(mLCUWMasterSchema.getPolNo());
			mLCUWSubSchema.setGrpContNo(mLCUWMasterSchema.getGrpContNo());
			mLCUWSubSchema.setProposalContNo(mLCUWMasterSchema
					.getProposalContNo());
			mLCUWSubSchema.setProposalNo(mLCUWMasterSchema.getProposalNo());
			mLCUWSubSchema.setInsuredNo(mLCUWMasterSchema.getInsuredNo());
			mLCUWSubSchema.setInsuredName(mLCUWMasterSchema.getInsuredName());
			mLCUWSubSchema.setAppntNo(mLCUWMasterSchema.getAppntNo());
			mLCUWSubSchema.setAppntName(mLCUWMasterSchema.getAppntName());
			mLCUWSubSchema.setAgentCode(mLCUWMasterSchema.getAgentCode());
			mLCUWSubSchema.setAgentGroup(mLCUWMasterSchema.getAgentGroup());
			mLCUWSubSchema.setUWGrade(mLCUWMasterSchema.getUWGrade()); // 核保级别
			mLCUWSubSchema.setAppGrade(mLCUWMasterSchema.getAppGrade()); // 申请级别
			mLCUWSubSchema.setAutoUWFlag(mLCUWMasterSchema.getAutoUWFlag());
			mLCUWSubSchema.setState(mLCUWMasterSchema.getState());
			mLCUWSubSchema.setPassFlag(mUWFlag);
			mLCUWSubSchema.setPostponeDay(mLCUWMasterSchema.getPostponeDay());
			mLCUWSubSchema.setPostponeDate(mLCUWMasterSchema.getPostponeDate());
			mLCUWSubSchema.setUpReportContent(mLCUWMasterSchema
					.getUpReportContent());
			mLCUWSubSchema.setHealthFlag(mLCUWMasterSchema.getHealthFlag());
			mLCUWSubSchema.setSpecFlag(mLCUWMasterSchema.getSpecFlag());
			mLCUWSubSchema.setSpecReason(mLCUWMasterSchema.getSpecReason());
			mLCUWSubSchema.setQuesFlag(mLCUWMasterSchema.getQuesFlag());
			mLCUWSubSchema.setReportFlag(mLCUWMasterSchema.getReportFlag());
			mLCUWSubSchema.setChangePolFlag(mLCUWMasterSchema
					.getChangePolFlag());
			mLCUWSubSchema.setChangePolReason(mLCUWMasterSchema
					.getChangePolReason());
			mLCUWSubSchema.setAddPremReason(mLCUWMasterSchema
					.getAddPremReason());
			mLCUWSubSchema.setPrintFlag(mLCUWMasterSchema.getPrintFlag());
			mLCUWSubSchema.setPrintFlag2(mLCUWMasterSchema.getPrintFlag2());
			mLCUWSubSchema.setUWIdea(mLCUWMasterSchema.getUWIdea());
			mLCUWSubSchema.setOperator(mLCUWMasterSchema.getOperator()); // 操作员
			mLCUWSubSchema.setManageCom(mLCUWMasterSchema.getManageCom());
			mLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			mLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			mLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			mLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
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
		tLCPolDB.setPolNo(mLCUWMasterSchema.getPolNo());
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setProposalNo(mLCUWMasterSchema.getProposalNo());

		if (!tLCPolDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "查询险种保单表失败!") ;
			return false;
		}
		mLCPolSchema = tLCPolDB.getSchema();

		if (!tLCUWMasterDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "查询险种核保险种表失败!") ;
			return false;
		}
		mLCUWMasterSchema = tLCUWMasterDB.getSchema();
		
		String strSql = "select count(1) from lcprem where polno='"
			+ "?polno?" + "' and payplancode like '000000%%' ";		
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSql);
		sqlbv1.put("polno", mLCUWMasterSchema.getPolNo());
		ExeSQL tExeSQL = new ExeSQL();
		int rs_addfee = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));	
		if(((rs_addfee > 0)
				|| (tLCUWMasterDB.getChangePolFlag()!=null && tLCUWMasterDB.getChangePolFlag().equals("1")))
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
		if (mLCUWMasterSchema.getPassFlag().equals("1")
				|| mLCUWMasterSchema.getPassFlag().equals("4")
				|| mLCUWMasterSchema.getPassFlag().equals("9"))

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
		mLCUWMasterSchema = (LCUWMasterSchema) cInputData
				.getObjectByObjectName("LCUWMasterSchema", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mOperator = tGI.Operator;
		if (mOperator == null || mOperator.length() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据Operator失败!") ;
			return false;
		}

		if (mLCUWMasterSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this, "前台传输数据LCUWMasterSchema失败!") ;
			return false;
		}

		mUWFlag = mLCUWMasterSchema.getPassFlag();
		if (mUWFlag == null || mUWFlag.length() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this, "前台传输数据UWFlag失败!") ;
			return false;
		}

		mUWIdea = mLCUWMasterSchema.getUWIdea();
		mSugPassFlag = mLCUWMasterSchema.getSugPassFlag();
		mSugUWIdea = mLCUWMasterSchema.getSugUWIdea();
		mPreamnt = mLCUWMasterSchema.getChangePolReason();

		return true;
	}
}
