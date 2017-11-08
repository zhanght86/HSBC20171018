package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPUWMasterDB;
import com.sinosoft.lis.db.LPUWSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPUWMasterSchema;
import com.sinosoft.lis.schema.LPUWSubSchema;
import com.sinosoft.lis.vschema.LPUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
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

public class BQUWRiskSaveBL {
private static Logger logger = Logger.getLogger(BQUWRiskSaveBL.class);
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
	private LPUWMasterSchema mLPUWMasterSchema = new LPUWMasterSchema();
	private LPPolSchema tLPPolSchema = new LPPolSchema();
	
	/** 被保人核保主表 */
	private LPUWMasterSchema tLPUWMasterSchema = new LPUWMasterSchema();
	/** 被保人核保子表 */
	private LPUWSubSchema tLPUWSubSchema = new LPUWSubSchema();
	private LPContSchema tLPContSchema = new LPContSchema();

	/** 业务操作字符串 */
	private String mUWFlag = "";
	private String mUWIdea = "";
	private String mSugPassFlag;
	private String mSugUWIdea;
	private String mPreamnt;
	private String mEdorNo;
	private String mEdorType;

	public BQUWRiskSaveBL() {
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

		map.put(tLPPolSchema, "UPDATE");
		map.put(tLPUWMasterSchema, "UPDATE");
		map.put(tLPUWSubSchema, "INSERT");

		mResult.add(map);

		return true;
	}

	/**
	 * dealData
	 * 
	 * @return boolean
	 */
	private boolean dealData() {

		tLPPolSchema.setUWFlag(mUWFlag);
		tLPPolSchema.setUWCode(mOperator);
		tLPPolSchema.setUWDate(PubFun.getCurrentDate());
		tLPPolSchema.setUWTime(PubFun.getCurrentTime());		

		tLPUWMasterSchema.setPassFlag(mUWFlag);
		tLPUWMasterSchema.setAutoUWFlag("2");//人工核保
		tLPUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		tLPUWMasterSchema.setOperator(mOperator); // 操作员

		// 准备被保人核保子表数据
		Reflections mReflections = new Reflections();
		mReflections.transFields(tLPUWSubSchema, tLPUWMasterSchema);
		LPUWSubDB tLPUWSubDB = new LPUWSubDB();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String sql = "select max(uwno) from LPUWSub where edorno='"+"?mEdorNo?"
		         +"' and edortype='"+"?mEdorType?"+"'"
		         + " and polno='"+"?polno?"+"'";
		sqlbv.sql(sql);
		sqlbv.put("polno", mLPUWMasterSchema.getPolNo());
		sqlbv.put("mEdorNo", mEdorNo);
		sqlbv.put("mEdorType", mEdorType);
		ExeSQL txExeSQL = new ExeSQL();
        int uwno = Integer.parseInt(txExeSQL.getOneValue(sqlbv));
        tLPUWSubSchema.setUWNo(uwno+1);
		tLPUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLPUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		
		return true;
	}

	/**
	 * checkData
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mEdorNo);
		tLPPolDB.setEdorType(mEdorType);
		tLPPolDB.setPolNo(mLPUWMasterSchema.getPolNo());		

		if (!tLPPolDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "查询险种保单表失败!") ;
			return false;
		}
		tLPPolSchema = tLPPolDB.getSchema();
		/*if(mUWFlag.equals("c"))//维持原条件承保 --取原险种承保结论
		{
			mUWFlag = tLPPolSchema.getUWFlag();
		}*/
		
		if(!prepareIndUWMaster())
			return false;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String sql = "select (case when count(*) is not null then count(*) else 0 end) from lpprem where edorno='"+"?mEdorNo?"
		        +"' and edortype='"+"?mEdorType?"+"'"
		        + " and polno='"+"?polno?"+"' and payplancode like '000000%' and (addform is null or addform <>'3')";
		sqlbv.sql(sql);
		sqlbv.put("polno", mLPUWMasterSchema.getPolNo());
		sqlbv.put("mEdorNo", mEdorNo);
		sqlbv.put("mEdorType", mEdorType);
		ExeSQL txExeSQL = new ExeSQL();
		int uwno = Integer.parseInt(txExeSQL.getOneValue(sqlbv));
		if(uwno>0 && mUWFlag.equals("9"))
		{
			CError.buildErr(this, "该险种有加费，不能下标准体核保结论!") ;
			return false;
		}

		// if(!checkUW())
		// return false;

		return true;
	}
	
	/**
	 * prepareIndUWMaster
	 * 
	 * @return boolean
	 */
	private boolean prepareIndUWMaster() {
		LPUWMasterDB tLPUWMasterDB = new LPUWMasterDB();
		tLPUWMasterDB.setEdorNo(mEdorNo);
		tLPUWMasterDB.setEdorType(mEdorType);
		tLPUWMasterDB.setPolNo(mLPUWMasterSchema.getPolNo());
		if (tLPUWMasterDB.getInfo() == false) {
			// @@错误处理
			CError.buildErr(this,"查询险种核保主表出错!");
			return false;									
		}
		else
		{
			tLPUWMasterSchema.setSchema(tLPUWMasterDB);
			int uwno = tLPUWMasterDB.getUWNo();
			tLPUWMasterSchema.setUWNo(uwno + 1);// 每次核保后uwno加1			
			tLPUWMasterSchema.setOperator(mOperator); // 操作员		
			tLPUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLPUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			
			LPUWSubDB tLPUWSubDB = new LPUWSubDB();
			tLPUWSubDB.setEdorNo(mEdorNo);
			tLPUWSubDB.setEdorType(mEdorType);
			tLPUWSubDB.setPolNo(mLPUWMasterSchema.getPolNo());
			LPUWSubSet tLPUWSubSet = new LPUWSubSet();
			tLPUWSubSet = tLPUWSubDB.query();
			if (tLPUWSubSet==null || tLPUWSubSet.size()<1) {
				// @@错误处理
				CError.buildErr(this,"查询保全险种核保子表出错!");
				return false;
			}
		}			
		
		return true;
	}

	/**
	 * checkUW 校验时否经过核保
	 * 
	 * @return boolean
	 */
	private boolean checkUW() {
		if (tLPUWMasterSchema.getPassFlag().equals("1")
				|| tLPUWMasterSchema.getPassFlag().equals("4")
				|| tLPUWMasterSchema.getPassFlag().equals("9"))

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
		mLPUWMasterSchema = (LPUWMasterSchema) cInputData
				.getObjectByObjectName("LPUWMasterSchema", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mOperator = tGI.Operator;
		if (mOperator == null || mOperator.length() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据Operator失败!") ;
			return false;
		}

		if (mLPUWMasterSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this, "前台传输数据LPUWMasterSchema失败!") ;
			return false;
		}

		mUWFlag = mLPUWMasterSchema.getPassFlag();
		if (mUWFlag == null || mUWFlag.length() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError.buildErr(this, "前台传输数据UWFlag失败!") ;
			return false;
		}

		mUWIdea = mLPUWMasterSchema.getUWIdea();
		mSugPassFlag = mLPUWMasterSchema.getSugPassFlag();
		mSugUWIdea = mLPUWMasterSchema.getSugUWIdea();
		mPreamnt = mLPUWMasterSchema.getChangePolReason();
		mEdorNo = mLPUWMasterSchema.getEdorNo();
		mEdorType = mLPUWMasterSchema.getEdorType();

		return true;
	}
}
