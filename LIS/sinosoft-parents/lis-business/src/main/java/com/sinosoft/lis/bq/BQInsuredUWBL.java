package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPCSpecDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPIndUWMasterDB;
import com.sinosoft.lis.db.LPIndUWSubDB;
import com.sinosoft.lis.db.LPIndUWSubDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPIndUWMasterSchema;
import com.sinosoft.lis.schema.LPIndUWSubSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.vschema.LPIndUWSubSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPIndUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
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

public class BQInsuredUWBL {
private static Logger logger = Logger.getLogger(BQInsuredUWBL.class);

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
	private LPIndUWMasterSchema mLPIndUWMasterSchema = new LPIndUWMasterSchema();	
	
	private LPIndUWMasterSchema tLPIndUWMasterSchema = new LPIndUWMasterSchema();
	private LPIndUWSubSchema tLPIndUWSubSchema = new LPIndUWSubSchema();
	private LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();	

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;

	/** 业务数据 */
	private String mEdorNo;
	private String mEdorType;
	private String mContNo;
	private String mInsuredNo;

	public BQInsuredUWBL() {
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

		map.put(tLPIndUWMasterSchema, "UPDATE");
		map.put(tLPIndUWSubSchema, "INSERT");
		//map.put(tLPInsuredSchema, "UPDATE");

		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setEdorNo(mEdorNo);
		tLPInsuredDB.setEdorType(mEdorType);
		tLPInsuredDB.setContNo(mContNo);
		tLPInsuredDB.setInsuredNo(mInsuredNo);
		if (!tLPInsuredDB.getInfo()) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "查询被保人信息出错!") ;
			return false;
		}
		tLPInsuredSchema = tLPInsuredDB.getSchema(); 
		
		if(!prepareIndUWMaster())
			return false;

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
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据mOperator失败!") ;
			return false;
		}

		// 获得管理机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLPContDB.mErrors );
			CError.buildErr(this, "前台传输全局公共数据mOperator失败!");
			return false;
		}
		// 获得当前EdorNo
		mEdorNo = (String) mTransferData.getValueByName("EdorNo");
		if (mEdorNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中EdorNo失败!");
			return false;
		}
		// 获得当前EdorNo
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		if (mEdorType == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中EdorType失败!");
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
		mLPIndUWMasterSchema = (LPIndUWMasterSchema) mTransferData
				.getValueByName("LPIndUWMasterSchema");
		if (mLPIndUWMasterSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this, "前台传输业务数据中LPIndUWMasterSchema失败!");
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
		//准备核保主表数据
		
		tLPIndUWMasterSchema.setUWIdea(mLPIndUWMasterSchema.getUWIdea());	
		   
		tLPIndUWMasterSchema.setOperator(mOperator);
		tLPIndUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPIndUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		
		// 准备被保人核保子表数据
		Reflections mReflections = new Reflections();
		mReflections.transFields(tLPIndUWSubSchema, tLPIndUWMasterSchema);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String sql = "select max(uwno) from lpinduwsub where edorno='"+"?mEdorNo?"+"' and contno='"+"?mContNo?"+"' and insuredno='"+"?mInsuredNo?"+"'";
		sqlbv.sql(sql);
		sqlbv.put("mContNo", mContNo);
		sqlbv.put("mInsuredNo", mInsuredNo);
		sqlbv.put("mEdorNo", mEdorNo);
		ExeSQL txExeSQL = new ExeSQL();
        int uwno = Integer.parseInt(txExeSQL.getOneValue(sqlbv));
        	
        tLPIndUWSubSchema.setUWNo(uwno+1);
		tLPIndUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLPIndUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			
		return true;
	}
	
	/**
	 * prepareIndUWMaster
	 * 
	 * @return boolean
	 */
	private boolean prepareIndUWMaster() {
		LPIndUWMasterDB tLPIndUWMasterDB = new LPIndUWMasterDB();
		tLPIndUWMasterDB.setEdorNo(mEdorNo);
		tLPIndUWMasterDB.setEdorType(mEdorType);
		tLPIndUWMasterDB.setContNo(mContNo);
		tLPIndUWMasterDB.setInsuredNo(mInsuredNo);
		if (tLPIndUWMasterDB.getInfo() == false) {
			// @@错误处理
			CError.buildErr(this,"查询被保人核保主表出错!");
			return false;			
		}
		else
		{
			tLPIndUWMasterSchema.setSchema(tLPIndUWMasterDB);
			int uwno = tLPIndUWMasterDB.getUWNo();
			tLPIndUWMasterSchema.setUWNo(uwno + 1);			
			tLPIndUWMasterSchema.setOperator(mOperator); // 操作员		
			tLPIndUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLPIndUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			
			LPIndUWSubDB tLPIndUWSubDB = new LPIndUWSubDB();
			tLPIndUWSubDB.setEdorNo(mEdorNo);
			tLPIndUWSubDB.setContNo(mContNo);
			tLPIndUWSubDB.setInsuredNo(mInsuredNo);
			LPIndUWSubSet tLPIndUWSubSet = new LPIndUWSubSet();
			tLPIndUWSubSet = tLPIndUWSubDB.query();
			if (tLPIndUWSubSet==null || tLPIndUWSubSet.size()<1) {
				// @@错误处理
				CError.buildErr(this,"查询保全被保人核保子表出错!");
				return false;
			}
		}			
		
		return true;
	}

	/**
	 * prepareInsured
	 * 
	 * @return boolean
	 */
	private boolean prepareInsured() {
		tLPInsuredSchema.setUWCode(mOperator);
		tLPInsuredSchema.setUWDate(PubFun.getCurrentDate());
		tLPInsuredSchema.setUWTime(PubFun.getCurrentTime());

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
