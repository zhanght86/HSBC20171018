package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web 业务系统 </p>
 * <p>Description: 增补健康告知ConfirmBL</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Nicholas
 * @version 1.0
 */

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPCustomerImpartDB;
import com.sinosoft.lis.db.LPCustomerImpartParamsDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

public class PEdorHIConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorHIConfirmBL.class);
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
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet(); // 因为是客户层，所以根据保全受理号去做
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPContSet mLPContSet = new LPContSet();
	private LCContSet mLCContSet = new LCContSet();
	private LPPolSet mLPPolSet = new LPPolSet();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LCDutySet mLCDutySet = new LCDutySet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LCPremSet mLCPremSet = new LCPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LCGetSet mLCGetSet = new LCGetSet();
	
	private ValidateEdorData2 mValidateEdorData = null;
	
	private String mEdorNo = null;
	private String mEdorType = null;
	private String mContNo = null;

	private Reflections mReflections = new Reflections();

	public PEdorHIConfirmBL() {
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
		    mEdorNo = mLPEdorItemSchema.getEdorNo();
		    mEdorType = mLPEdorItemSchema.getEdorType();
		    mContNo = mLPEdorItemSchema.getContNo();
		    mValidateEdorData = new ValidateEdorData2(mGlobalInput, mEdorNo,mEdorType, mContNo, "ContNo");
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorHIConfirmBL";
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

			LPCustomerImpartDB tLPCustomerImpartDB = null;
			LPCustomerImpartSet tLPCustomerImpartSet = new LPCustomerImpartSet();
			LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
			LCCustomerImpartSchema tLCCustomerImpartSchema = null;
			LPCustomerImpartParamsDB tLPCustomerImpartParamsDB = null;
			LPCustomerImpartParamsSet tLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();
			LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
			LCCustomerImpartParamsSchema tLCCustomerImpartParamsSchema = null;

				// 交换客户告知表数据
				tLPCustomerImpartDB = new LPCustomerImpartDB();
				tLPCustomerImpartDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPCustomerImpartDB
						.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPCustomerImpartSet = tLPCustomerImpartDB.query();
				if (tLPCustomerImpartSet == null
						|| tLPCustomerImpartSet.size() <= 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorHIConfirmBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询客户告知信息错误！";
					this.mErrors.addOneError(tError);
					return false;
				}
				for (int i = 1; i <= tLPCustomerImpartSet.size(); i++) {
					tLCCustomerImpartSchema = new LCCustomerImpartSchema();
					mReflections.transFields(tLCCustomerImpartSchema,
							tLPCustomerImpartSet.get(i));
					tLCCustomerImpartSchema
							.setOperator(this.mGlobalInput.Operator);
					tLCCustomerImpartSchema.setModifyDate(strCurrentDate);
					tLCCustomerImpartSchema.setModifyTime(strCurrentTime);
					tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
				}

				// 交换客户告知参数表数据
				tLPCustomerImpartParamsDB = new LPCustomerImpartParamsDB();
				tLPCustomerImpartParamsDB.setEdorNo(mLPEdorItemSchema
						.getEdorNo());
				tLPCustomerImpartParamsDB.setEdorType(mLPEdorItemSchema
						.getEdorType());
				tLPCustomerImpartParamsSet = tLPCustomerImpartParamsDB.query();
				if (tLPCustomerImpartParamsSet.size() > 0) {
					for (int i = 1; i <= tLPCustomerImpartParamsSet.size(); i++) {
						tLCCustomerImpartParamsSchema = new LCCustomerImpartParamsSchema();
						mReflections.transFields(tLCCustomerImpartParamsSchema,
								tLPCustomerImpartParamsSet.get(i));
						tLCCustomerImpartParamsSchema
								.setOperator(this.mGlobalInput.Operator);
						tLCCustomerImpartParamsSchema.setModifyDate(strCurrentDate);
						tLCCustomerImpartParamsSchema.setModifyTime(strCurrentTime);
						tLCCustomerImpartParamsSet
								.add(tLCCustomerImpartParamsSchema);
					}
				}


				//采用新的方式进行 CP 互换
			    String[] chgTables =  {"LCPol","LCDuty","LCPrem","LCGet","LCCont","LCAppnt","LCInsured"};
			    mValidateEdorData.changeData(chgTables);
			    mMap.add(mValidateEdorData.getMap());
			    
			    //处理特约
			    mMap.add(BqNameFun.DealSpecData(mLPEdorItemSchema));


//			mMap.put(tLPCustomerImpartSet, "DELETE");
//			mMap.put(tLPCustomerImpartParamsSet, "DELETE");
			
			mMap.put(tLCCustomerImpartSet, "DELETE&INSERT");
			mMap.put(tLCCustomerImpartParamsSet, "DELETE&INSERT");
			



			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorHIConfirmBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	// 错误处理
	private void makeError(String vFuncName, String vErrMessage) {
		// @@错误处理
		CError tError = new CError();
		tError.moduleName = "PEdorHIConfirmBL";
		tError.functionName = vFuncName;
		tError.errorMessage = vErrMessage;
		this.mErrors.addOneError(tError);
	}

	// --将P表的一组表与C表的一组表交换的函数结果存入一组private的变量里
	private boolean changeCPData(String vEdorNo, String vEdorType,
			String sContNo) {
		
		
		// Cont表
		LPContDB tLPContDB = new LPContDB();
		LCContDB tLCContDB = new LCContDB();
		tLPContDB.setEdorNo(vEdorNo);
		tLPContDB.setEdorType(vEdorType);
		LPContSet tLPContSet = new LPContSet();
		tLPContSet = tLPContDB.query();
		if (tLPContSet == null || tLPContSet.size() <= 0) {
			this.makeError("changeCPData", "查询保全保单表时产生错误！EdorNo=" + vEdorNo);
			return false;
		}
		for (int i = 1; i <= tLPContSet.size(); i++) {
			LCContSchema tLCContSchema = new LCContSchema();
			mReflections.transFields(tLCContSchema, tLPContSet.get(i));
			mLCContSet.add(tLCContSchema);
			// 查询C表匹配数据交换到P表
			tLCContDB.setContNo(tLCContSchema.getContNo());
			if (tLCContDB.getInfo()) {
				LPContSchema tLPContSchema = new LPContSchema();
				mReflections.transFields(tLPContSchema, tLCContDB.getSchema());
				tLPContSchema.setEdorNo(vEdorNo);
				tLPContSchema.setEdorType(vEdorType);
				mLPContSet.add(tLPContSchema);
			}
		}

		// Pol表
		LPPolDB tLPPolDB = new LPPolDB();
		LCPolDB tLCPolDB = new LCPolDB();
		tLPPolDB.setEdorNo(vEdorNo);
		tLPPolDB.setEdorType(vEdorType);
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet != null && tLPPolSet.size() > 0) {
			// this.makeError("changeCPData", "查询保全险种表时产生错误！EdorNo=" + vEdorNo);
			// return false;

			for (int i = 1; i <= tLPPolSet.size(); i++) {
				LCPolSchema tLCPolSchema = new LCPolSchema();
				mReflections.transFields(tLCPolSchema, tLPPolSet.get(i));
				mLCPolSet.add(tLCPolSchema);
				// 查询C表匹配数据交换到P表
				tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
				if (tLCPolDB.getInfo()) {
					LPPolSchema tLPPolSchema = new LPPolSchema();
					mReflections
							.transFields(tLPPolSchema, tLCPolDB.getSchema());
					tLPPolSchema.setEdorNo(vEdorNo);
					tLPPolSchema.setEdorType(vEdorType);
					mLPPolSet.add(tLPPolSchema);
				}
			}
		}

		// Duty表
		LPDutyDB tLPDutyDB = new LPDutyDB();
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLPDutyDB.setEdorNo(vEdorNo);
		tLPDutyDB.setEdorType(vEdorType);
		LPDutySet tLPDutySet = new LPDutySet();
		tLPDutySet = tLPDutyDB.query();
		if (tLPDutySet == null || tLPDutySet.size() <= 0) {
			this.makeError("changeCPData", "查询保全责任表时产生错误！EdorNo=" + vEdorNo);
			return false;
		}
		for (int i = 1; i <= tLPDutySet.size(); i++) {
			LCDutySchema tLCDutySchema = new LCDutySchema();
			mReflections.transFields(tLCDutySchema, tLPDutySet.get(i));
			mLCDutySet.add(tLCDutySchema);
			// 查询C表匹配数据交换到P表
			tLCDutyDB.setPolNo(tLCDutySchema.getPolNo());
			tLCDutyDB.setDutyCode(tLCDutySchema.getDutyCode());
			if (tLCDutyDB.getInfo()) {
				LPDutySchema tLPDutySchema = new LPDutySchema();
				mReflections.transFields(tLPDutySchema, tLCDutyDB.getSchema());
				tLPDutySchema.setEdorNo(vEdorNo);
				tLPDutySchema.setEdorType(vEdorType);
				mLPDutySet.add(tLPDutySchema);
			}
		}

		// Prem表
		LPPremDB tLPPremDB = new LPPremDB();
		tLPPremDB.setEdorNo(vEdorNo);
		tLPPremDB.setEdorType(vEdorType);
		LPPremSet tLPPremSet = new LPPremSet();
		tLPPremSet = tLPPremDB.query();
		if (tLPPremSet == null || tLPPremSet.size() <= 0) {
			this.makeError("changeCPData", "查询保全保费项表时产生错误！EdorNo=" + vEdorNo);
			return false;
		}
		for (int i = 1; i <= tLPPremSet.size(); i++) {
			LCPremSchema tLCPremSchema = new LCPremSchema();
			mReflections.transFields(tLCPremSchema, tLPPremSet.get(i));
			mLCPremSet.add(tLCPremSchema);
			
			LPPremSchema aLPPremSchema = new LPPremSchema();
			aLPPremSchema = tLPPremSet.get(i);
			//modify by jiaqiangli 2009-05-07
			LCPremDB tLCPremDB = new LCPremDB();
			 tLCPremDB.setPolNo(aLPPremSchema.getPolNo());
			 tLCPremDB.setDutyCode(aLPPremSchema.getDutyCode());
			 tLCPremDB.setPayPlanCode(aLPPremSchema.getPayPlanCode());
			 
			 boolean tExistsFlag = tLCPremDB.getInfo();
			if (tLCPremDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLCPremDB.mErrors);
				mErrors.addOneError(new CError("查询保费项目表失败！"));
				return false;
			}
			if (tExistsFlag == true) {
				mReflections.transFields(aLPPremSchema, tLCPremDB.getSchema());
				aLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				aLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mLPPremSet.add(aLPPremSchema);
			}
		}

//		LCPremDB tLCPremDB = new LCPremDB();
//		tLCPremDB.setContNo(sContNo);
//		LCPremSet tLCPremSet = tLCPremDB.query();
//		if (tLPPremSet == null || tLPPremSet.size() <= 0) {
//			this.makeError("changeCPData", "查询保费项表时产生错误！EdorNo=" + vEdorNo);
//			return false;
//		}
//		for (int i = 1; i <= tLCPremSet.size(); i++) {
//			LPPremSchema tLPPremSchema = new LPPremSchema();
//			mReflections.transFields(tLPPremSchema, tLCPremSet.get(i));
//			tLPPremSchema.setEdorNo(vEdorNo);
//			tLPPremSchema.setEdorType(vEdorType);
//			mLPPremSet.add(tLPPremSchema);
//		}

		// Get表
		LPGetDB tLPGetDB = new LPGetDB();
		LCGetDB tLCGetDB = new LCGetDB();
		tLPGetDB.setEdorNo(vEdorNo);
		tLPGetDB.setEdorType(vEdorType);
		LPGetSet tLPGetSet = new LPGetSet();
		tLPGetSet = tLPGetDB.query();
		if (tLPGetSet == null || tLPGetSet.size() <= 0) {
			this.makeError("changeCPData", "查询保全领取项表时产生错误！EdorNo=" + vEdorNo);
			return false;
		}
		for (int i = 1; i <= tLPGetSet.size(); i++) {
			LCGetSchema tLCGetSchema = new LCGetSchema();
			mReflections.transFields(tLCGetSchema, tLPGetSet.get(i));
			mLCGetSet.add(tLCGetSchema);
			// 查询C表匹配数据交换到P表
			tLCGetDB.setPolNo(tLCGetSchema.getPolNo());
			tLCGetDB.setDutyCode(tLCGetSchema.getDutyCode());
			tLCGetDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
			if (tLCGetDB.getInfo()) {
				LPGetSchema tLPGetSchema = new LPGetSchema();
				mReflections.transFields(tLPGetSchema, tLCGetDB.getSchema());
				tLPGetSchema.setEdorNo(vEdorNo);
				tLPGetSchema.setEdorType(vEdorType);
				mLPGetSet.add(tLPGetSchema);
			}
		}
		return true;

	}

}
