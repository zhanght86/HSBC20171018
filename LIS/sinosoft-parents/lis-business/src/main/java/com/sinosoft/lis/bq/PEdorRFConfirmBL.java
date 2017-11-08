package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保单质押贷款清偿提交类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Nicholas
 * @version 1.0
 */

import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LPContStateDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPReturnLoanDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LOReturnLoanSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPReturnLoanSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LOReturnLoanSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPReturnLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

public class PEdorRFConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorRFConfirmBL.class);
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

	public PEdorRFConfirmBL() {
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
			tError.moduleName = "PEdorRFConfirmBL";
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
			mErrors.addOneError(new CError("查询批改项目信息失败!"));
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
			
			Reflections tReflections = new Reflections();
			LOLoanSet tLOLoanSet = new LOLoanSet();


			// 生成“还款业务表”记录
			LPReturnLoanDB tLPReturnLoanDB = new LPReturnLoanDB();

			LPReturnLoanSchema tLPReturnLoanSchema = null;
			LPReturnLoanSet tLPReturnLoanSet = new LPReturnLoanSet();
			LOReturnLoanSchema tLOReturnLoanSchema = null;
			LOReturnLoanSet tLOReturnLoanSet = new LOReturnLoanSet();
			tLPReturnLoanDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPReturnLoanDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPReturnLoanSet = tLPReturnLoanDB.query();
			if (tLPReturnLoanSet != null && tLPReturnLoanSet.size() > 0) {
				for (int i = 1; i <= tLPReturnLoanSet.size(); i++) {
					tLPReturnLoanSchema = new LPReturnLoanSchema();
					tLPReturnLoanSchema = tLPReturnLoanSet.get(i);
					tLOReturnLoanSchema = new LOReturnLoanSchema();
					LOLoanSchema tLOLoanSchema = new LOLoanSchema();
					// 转换Schema
					tReflections.transFields(tLOReturnLoanSchema,
							tLPReturnLoanSchema);
					tLOReturnLoanSchema.setOperator(this.mGlobalInput.Operator);
					tLOReturnLoanSchema.setModifyDate(strCurrentDate);
					tLOReturnLoanSchema.setModifyTime(strCurrentTime);
					tLOReturnLoanSet.add(tLOReturnLoanSchema);

					LOLoanDB rLOLoanDB = new LOLoanDB();
					LOLoanSet rLOLoanSet = new LOLoanSet();
					rLOLoanDB.setEdorNo(tLPReturnLoanSchema.getLoanNo());
					rLOLoanDB.setContNo(tLPReturnLoanSchema.getContNo());
					rLOLoanDB.setLoanType("0");
					rLOLoanDB.setPayOffFlag("0");
					rLOLoanDB.setCurrency(tLPReturnLoanSchema.getCurrency());
					rLOLoanSet = rLOLoanDB.query();
					if (rLOLoanSet.size() < 1 || rLOLoanSet == null) {
						mErrors.addOneError(new CError("查询以往借款数据信息失败!"));
						return false;
					}
					tLOLoanSchema = rLOLoanSet.get(1);
					tLOLoanSchema.setSumMoney(tLPReturnLoanSchema.getSumMoney());
					tLOLoanSchema.setLeaveMoney(tLPReturnLoanSchema
							.getLeaveMoney());
					tLOLoanSchema.setPayOffDate(tLPReturnLoanSchema
							.getPayOffDate());
					tLOLoanSchema.setPayOffFlag(tLPReturnLoanSchema
							.getPayOffFlag());
					//用于保存本次还款日期,即计息日期
					tLOLoanSchema.setNewLoanDate(mLPEdorItemSchema.getEdorAppDate());
					tLOLoanSet.add(tLOLoanSchema);
				}
			}
			mMap.put(tLOReturnLoanSet, "DELETE&INSERT");
			mMap.put(tLPReturnLoanSet, "DELETE");
			mMap.put(tLOLoanSet, "UPDATE");
			// 改变保单状态为贷款（保单层）
			LPContStateDB tLPContStateDB = new LPContStateDB();
			tLPContStateDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContStateDB.setEdorType(mLPEdorItemSchema.getEdorType());
			LPContStateSet tLPContStateSet = tLPContStateDB.query();
			if (tLPContStateSet != null || tLPContStateSet.size() > 0) {
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
			tError.moduleName = "PEdorRFConfirmBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}
}
