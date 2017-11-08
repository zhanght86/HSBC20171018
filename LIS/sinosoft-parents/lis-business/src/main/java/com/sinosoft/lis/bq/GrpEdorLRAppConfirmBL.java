package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保单遗失补发申请确认
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 2.0
 */
public class GrpEdorLRAppConfirmBL implements EdorAppConfirm {
private static Logger logger = Logger.getLogger(GrpEdorLRAppConfirmBL.class);
	// /** 错误处理类，每个需要错误处理的类中都放置该类 */
	// public CErrors mErrors = new CErrors();
	//
	// /** 往后面传输数据的容器 */
	// private VData mInputData;
	//
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	//
	// /** 数据操作字符串 */
	// private String mOperate;
	//
	// /** 全局数据 */
	// //private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	// private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new
	// LPGrpEdorItemSchema();
	// private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new
	// LPGrpEdorMainSchema();
	// private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
	// private GlobalInput mGlobalInput = new GlobalInput();

	public GrpEdorLRAppConfirmBL() {
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
		// //将操作数据拷贝到本类中
		// mInputData = (VData) cInputData.clone();
		// this.mOperate = cOperate;
		//
		// //得到外部传入的数据,将数据备份到本类中
		// if (!getInputData())
		// {
		// return false;
		// }
		//
		// //进行业务处理
		// if (!dealData())
		// {
		// return false;
		// }
		//
		// //数据准备操作
		// if (!prepareData())
		// {
		// return false;
		// }

		return true;
	}

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
		// try
		// {
		// mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData.
		// getObjectByObjectName("LPGrpEdorItemSchema",
		// 0);
		// mGlobalInput.setSchema((GlobalInput) mInputData.
		// getObjectByObjectName("GlobalInput", 0));
		// }
		// catch (Exception e)
		// {
		// CError.buildErr(this, "接收数据失败");
		// return false;
		// }
		//
		// if (mLPGrpEdorItemSchema == null || mGlobalInput == null)
		// {
		// CError tError = new CError();
		// tError.moduleName = "GrpEdorLRAppConfirmBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "接收数据失败!!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		// tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		// tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		// tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		// LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		// if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() != 1)
		// {
		// mErrors.copyAllErrors(tLPGrpEdorItemDB.mErrors);
		// mErrors.addOneError(new CError("查询保全项目信息失败！"));
		// return false;
		// }
		// mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1));
		//
		// LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		// tLPGrpEdorMainDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		// tLPGrpEdorMainDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		// if (!tLPGrpEdorMainDB.getInfo())
		// {
		// mErrors.copyAllErrors(tLPGrpEdorMainDB.mErrors);
		// mErrors.addOneError(new CError("查询保全信息失败！"));
		// return false;
		// }
		// mLPGrpEdorMainSchema = tLPGrpEdorMainDB.getSchema();

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// //设置批改补退费表
		// LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		// tLJSGetEndorseSchema.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo());
		// //给付通知书号码
		// tLJSGetEndorseSchema.setEndorsementNo(mLPGrpEdorItemSchema.
		// getEdorNo());
		// tLJSGetEndorseSchema.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		// tLJSGetEndorseSchema.setPolNo("000000");
		// tLJSGetEndorseSchema.setFeeOperationType(mLPGrpEdorItemSchema.
		// getEdorType());
		// tLJSGetEndorseSchema.setGetDate(mLPGrpEdorItemSchema.
		// getEdorValiDate());
		// tLJSGetEndorseSchema.setGetMoney(mLPGrpEdorItemSchema.getGetMoney());
		// tLJSGetEndorseSchema.setFeeOperationType("LR");
		// tLJSGetEndorseSchema.setRiskCode("000000");
		// BqCalBL tBqCalBl = new BqCalBL();
		// String feeFinaType = tBqCalBl.getFinType("LR", "GB",
		// mLPGrpEdorItemSchema.
		// getGrpContNo());
		// if (feeFinaType.equals(""))
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.errorMessage = "在LDCode1中缺少保全财务接口转换类型编码!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// tLJSGetEndorseSchema.setFeeFinaType(feeFinaType);
		// tLJSGetEndorseSchema.setPayPlanCode("00000000"); //无作用
		// tLJSGetEndorseSchema.setDutyCode("0"); //无作用，但一定要，转ljagetendorse时非空
		// tLJSGetEndorseSchema.setOtherNo(mLPGrpEdorItemSchema.getEdorNo());
		// tLJSGetEndorseSchema.setOtherNoType("3");
		// tLJSGetEndorseSchema.setGetFlag("0");
		// tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
		// tLJSGetEndorseSchema.setManageCom(mGlobalInput.ManageCom);
		// LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		// tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		// if (tLCGrpContDB.getInfo())
		// {
		// tLJSGetEndorseSchema.setAgentCode(tLCGrpContDB.getAgentCode());
		// tLJSGetEndorseSchema.setAgentCom(tLCGrpContDB.getAgentCom());
		// tLJSGetEndorseSchema.setAgentGroup(tLCGrpContDB.getAgentGroup());
		// tLJSGetEndorseSchema.setAgentType(tLCGrpContDB.getAgentType());
		// }
		// tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
		// tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
		// tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
		// tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
		//
		// mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		// mLPGrpEdorItemSchema.setEdorState("2");
		// mLPGrpEdorItemSchema.setUWFlag("0");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		// mResult.clear();
		// MMap map = new MMap();
		// map.put(mLJSGetEndorseSet, "INSERT");
		// map.put(mLPGrpEdorItemSchema, "UPDATE");
		// mResult.add(map);
		return true;
	}
}
