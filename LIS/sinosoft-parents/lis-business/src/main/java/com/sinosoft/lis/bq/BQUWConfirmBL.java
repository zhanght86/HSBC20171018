package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPCUWMasterDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPCUWMasterSchema;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

public class BQUWConfirmBL {
private static Logger logger = Logger.getLogger(BQUWConfirmBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	private VData rResult = new VData(); // add by yaory

	private String mark = ""; // add by yaory

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private Reflections ref = new Reflections();

	private GlobalInput mGlobalInput = new GlobalInput();

	private MMap map = new MMap();

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();

	private String theCurrentTime = PubFun.getCurrentTime();

	/** 业务处理相关变量 */
	private LPCUWMasterSchema mLPCUWMasterSchema = new LPCUWMasterSchema();
	private LPCUWMasterSchema mOldLPCUWMasterSchema = new LPCUWMasterSchema();

	private String GrpNo = "";

	private String GrpName = "";

	private String mDealFlag = "1"; // 程序处理逻辑标志，1为走个险逻辑，2为团险

	private String mFamilyType = "";
	private String mInputNo = "";
	private String mEdorAcceptNo = "";
	private String mEdorNo="";
	private String mContNo="";
	// @Constructor
	public BQUWConfirmBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("now in ContBL submit");
		// 将外部传入的数据分解到本类的属性中，准备处理
		if (!getInputData()) {
			return false;
		}
		logger.debug("---getInputData---");
		// 校验传入的数据
		// if (!mOperate.equals("DELETE||CONT"))
		// {
		if (!checkData()) {
			return false;
		}
		logger.debug("---checkData---");
		// }

		// 根据业务逻辑对数据进行处理
		logger.debug("---dealData start---");
		if (!dealData()) {
			return false;
		}
		logger.debug("---dealData  ended---");
		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		// 数据提交、保存
		logger.debug("卡单不会执行以下程序！");
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
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
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
			// 合同表
			mLPCUWMasterSchema.setSchema((LPCUWMasterSchema) mInputData
					.getObjectByObjectName("LPCUWMasterSchema", 0));
			if (mLPCUWMasterSchema == null ) {
				CError.buildErr(this, "前台传入数据失败！") ;
				return false;
			}
			mEdorNo=mLPCUWMasterSchema.getEdorNo();
			mContNo=mLPCUWMasterSchema.getContNo();
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
		} catch (Exception ex) {
			CError.buildErr(this, "得到前台传入的数据时发生错误！");
			return false;

		}
		return true;

	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
		tLPCUWMasterDB.setEdorNo(mLPCUWMasterSchema.getEdorNo());
		tLPCUWMasterDB.setEdorType(mLPCUWMasterSchema.getEdorType());
		tLPCUWMasterDB.setContNo(mLPCUWMasterSchema.getContNo());
		if(!tLPCUWMasterDB.getInfo()){
			CError.buildErr(this, "查询LPCUWMaster表失败，前台传入数据有误！");
			return false;
		}
		mOldLPCUWMasterSchema = tLPCUWMasterDB.getSchema();
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
		tLPEdorMainDB.setEdorNo(mEdorNo);
		tLPEdorMainDB.setContNo(mContNo);
		tLPEdorMainSet=tLPEdorMainDB.query();
		if(tLPEdorMainSet.size()<1||tLPEdorMainSet.size()!=1){
			CError.buildErr(this, "查询保全申请主表失败！");
			return false;
		}
		mEdorAcceptNo=tLPEdorMainSet.get(1).getEdorAcceptNo();
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {

		mOldLPCUWMasterSchema.setCustomerIdea(mLPCUWMasterSchema.getCustomerIdea());
		mOldLPCUWMasterSchema.setModifyDate(theCurrentDate);
		mOldLPCUWMasterSchema.setModifyTime(theCurrentTime);
		mOldLPCUWMasterSchema.setOperator(mGlobalInput.Operator);
		mOldLPCUWMasterSchema.setEdorAcceptNo(mEdorAcceptNo);
		map.put(mOldLPCUWMasterSchema, "UPDATE");
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		mInputData.clear();
		mInputData.add(map);
		rResult.clear();
		rResult.add(map);
		mResult.clear();

	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */

	public VData getResult() {
		return mResult;
	}

	public VData getCardResult() {
		return rResult;
	}

}
