package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保单质押贷款提交类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Nicholas
 * @version 1.0
 */

import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LPContStateDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPLoanDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPLoanSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

public class PEdorLNConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorLNConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	private Reflections mReflections = new Reflections();
	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();

	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorLNConfirmBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据校验
		if (!checkData()) {
			return false;
		}

		logger.debug("after checkData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorLNConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return: boolean
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLPEdorItemDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorLNConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询批改项目信息失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			// 获得此时的日期和时间
			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();
            
			// 交换借款表数据
			LOLoanSet tLOLoanSet = new LOLoanSet();
			LOLoanSchema tLOLoanSchema = null;
			LPLoanDB tLPLoanDB = new LPLoanDB();
			LPLoanSet tLPLoanSet = new LPLoanSet();
			tLPLoanDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPLoanDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPLoanDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPLoanSet = tLPLoanDB.query();
			if (tLPLoanSet == null || tLPLoanSet.size() <= 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorLNConfirmBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询保全贷款记录信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			LPLoanSet aLPLoanSet = new LPLoanSet();
			for (int i = 1; i <= tLPLoanSet.size(); i++) {
				LPLoanSchema tLPLoanSchema = new LPLoanSchema();
				tLPLoanSchema = tLPLoanSet.get(i);
				tLOLoanSchema = new LOLoanSchema();
				

				//存放本次借款新增的记录，数据由P表转至O表后将P表删除
				aLPLoanSet.add(tLPLoanSchema);
			    mReflections.transFields(tLOLoanSchema, tLPLoanSchema);		
				tLOLoanSet.add(tLOLoanSchema);
				    	
			}
			mMap.put(tLOLoanSet, "DELETE&INSERT");
			mMap.put(aLPLoanSet, "DELETE");			

			LPContStateDB tLPContStateDB = new LPContStateDB();
			tLPContStateDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContStateDB.setEdorType(mLPEdorItemSchema.getEdorType());
			LPContStateSet tLPContStateSet = tLPContStateDB.query();
			if (tLPContStateSet != null && tLPContStateSet.size() > 0) {
				mMap.put(tLPContStateSet, "DELETE");
				LCContStateSet tLCContStateSet = new LCContStateSet();
				for (int i = 1; i <= tLPContStateSet.size(); i++) {
					LCContStateSchema tLCContStateSchema = new LCContStateSchema();
					this.mReflections.transFields(tLCContStateSchema,
							tLPContStateSet.get(i));
					tLCContStateSchema.setOperator(this.mGlobalInput.Operator);
					tLCContStateSchema.setModifyDate(strCurrentDate);
					tLCContStateSchema.setModifyTime(strCurrentTime);
					tLCContStateSet.add(tLCContStateSchema);
				}
				mMap.put(tLCContStateSet, "DELETE&INSERT");
			}


			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorLNConfirmBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}
}
