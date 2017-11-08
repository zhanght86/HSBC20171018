package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全申请确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @ReWrite ZhangRong
 * @version 1.0
 */

public class PEdorNIAppConfirmBL implements EdorAppConfirm {
private static Logger logger = Logger.getLogger(PEdorNIAppConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
	private Reflections mReflections = new Reflections();

	public PEdorNIAppConfirmBL() {
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

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 数据准备操作
		if (!prepareOutputData()) {
			return false;
		}

		// PubSubmit tSubmit = new PubSubmit();
		//
		// if (!tSubmit.submitData(mResult, "")) //数据提交
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tSubmit.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "PEdorNIAppConfirmBL";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// logger.debug("End AppConfirm NI BL Submit...");

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
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorNIAppConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			CError tError = new CError();
			tError.moduleName = "PEdorNIAppConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			mErrors.addOneError(new CError("查询保全项目信息失败！"));
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));

		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorMainDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorMainDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLPEdorMainDB.getInfo()) {
			mErrors.copyAllErrors(tLPEdorMainDB.mErrors);
			mErrors.addOneError(new CError("查询保全信息失败！"));
			return false;
		}
		mLPEdorMainSchema.setSchema(tLPEdorMainDB.getSchema());

		return true;
	}

	/**
	 * 设置保全主表数据到申请确认状态
	 * 
	 * @return
	 */
	private boolean setAppConfirmState() {
		mLPEdorItemSchema.setEdorState("2"); // 申请确认状态
		mLPEdorItemSchema.setUWFlag("0");
		return true;
	}

	/**
	 * 往批改补退费表（应收/应付）新增数据
	 * 
	 * @return
	 */
	private boolean setLJSGetEndorse() {
		try {

			// 设置批改补退费表
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
			LCPolSet tLCPolSet = tLCPolDB.query();
			if (tLCPolSet == null || tLCPolSet.size() < 0) {
				mErrors.copyAllErrors(tLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询险种保单数据失败！"));
			}
			LCPolSchema tLCPolSchema = null;
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				tLCPolSchema = tLCPolSet.get(i);
				LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();

				tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema
						.getEdorNo()); // 给付通知书号码
				tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema
						.getEdorNo());
				tLJSGetEndorseSchema.setGrpContNo(mLPEdorItemSchema
						.getGrpContNo());
				tLJSGetEndorseSchema.setContNo(mLPEdorItemSchema.getContNo());
				tLJSGetEndorseSchema.setPolNo(tLCPolSchema.getPolNo());
				tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema
						.getEdorType());
				tLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema
						.getEdorValiDate());
				tLJSGetEndorseSchema.setGetMoney(tLCPolSchema.getPrem());
				tLJSGetEndorseSchema.setFeeOperationType("NI");
				tLJSGetEndorseSchema.setFeeFinaType("BF"); // 补费
				tLJSGetEndorseSchema.setPayPlanCode("00000000"); // 无作用
				tLJSGetEndorseSchema.setDutyCode("0"); // 无作用，但一定要，转ljagetendorse时非空
				tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 无作用
				tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
				tLJSGetEndorseSchema.setGetFlag("0");
				tLJSGetEndorseSchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLJSGetEndorseSchema.setAgentCom(tLCPolSchema.getAgentCom());
				tLJSGetEndorseSchema
						.setAgentGroup(tLCPolSchema.getAgentGroup());
				tLJSGetEndorseSchema.setAgentType(tLCPolSchema.getAgentType());
				tLJSGetEndorseSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
				tLJSGetEndorseSchema.setKindCode(tLCPolSchema.getKindCode());
				tLJSGetEndorseSchema.setAppntNo(tLCPolSchema.getAppntNo());
				tLJSGetEndorseSchema.setRiskCode(tLCPolSchema.getRiskCode());
				tLJSGetEndorseSchema.setRiskVersion(tLCPolSchema
						.getRiskVersion());
				tLJSGetEndorseSchema.setHandler(tLCPolSchema.getHandler());
				tLJSGetEndorseSchema.setApproveCode(tLCPolSchema
						.getApproveCode());
				tLJSGetEndorseSchema.setApproveDate(tLCPolSchema
						.getApproveDate());
				tLJSGetEndorseSchema.setApproveTime(tLCPolSchema
						.getApproveTime());

				tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
				tLJSGetEndorseSchema.setManageCom(mGlobalInput.ManageCom);
				tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
				tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
				tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
				tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
		          //营改增 add zhangyingfeng 2016-07-14
		          //价税分离 计算器
		          TaxCalculator.calBySchema(tLJSGetEndorseSchema);
		          //end zhangyingfeng 2016-07-14
				mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			// 设置保全主表数据到申请确认状态
			if (!setAppConfirmState()) {
				CError tError = new CError();
				tError.moduleName = "PEdorNIAppConfirmBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "设置保全主表数据到申请确认状态失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 往批改补退费表（应收/应付）新增数据
			if (!setLJSGetEndorse()) {
				CError tError = new CError();
				tError.moduleName = "PEdorNIAppConfirmBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "往批改补退费表（应收/应付）新增数据失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorNIAppConfirmBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareOutputData() {
		try {
			mMap.put(mLJSGetEndorseSet, "DELETE&INSERT");
			mMap.put(mLPEdorItemSchema, "UPDATE");
			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorNIAppConfirmBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86110000";

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorNo("86110020030430000401");
		tLPEdorItemSchema.setGrpContNo("86110020030220000127");
		tLPEdorItemSchema.setEdorType("NI");
		tLPEdorItemSchema = (tLPEdorItemSchema.getDB().query()).get(1);

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLPEdorItemSchema);

		PEdorNIAppConfirmBL tPEdorNIAppConfirmBL = new PEdorNIAppConfirmBL();
		if (!tPEdorNIAppConfirmBL.submitData(tVData, "INSERT")) {
			VData rVData = tPEdorNIAppConfirmBL.getResult();
			logger.debug("Submit Failed! " + (String) rVData.get(0));
		} else {
			logger.debug("Submit Succed!");
		}
	}

}
