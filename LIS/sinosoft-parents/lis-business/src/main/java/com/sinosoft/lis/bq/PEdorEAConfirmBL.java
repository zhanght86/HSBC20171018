package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 */
public class PEdorEAConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorEAConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	// /** 往后面传输数据的容器 */
	// private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	// /** 数据操作字符串 */
	// private String mOperate;
	// /** 业务对象 */
	// private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	// /** 全局数据 */
	// private GlobalInput mGlobalInput = new GlobalInput();
	// private MMap map = new MMap();
	// //系统当前时间
	// private String mCurrentDate = PubFun.getCurrentDate();
	// private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorEAConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            输入的数据
	 * @param cOperate
	 *            数据操作
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		PEdorCTConfirmBL tPEdorCTConfirmBL = new PEdorCTConfirmBL();
		// //将操作数据拷贝到本类中
		// mInputData = (VData) cInputData.clone();
		// mOperate = cOperate;
		//
		// //得到外部传入的数据,将数据备份到本类中
		// if (!getInputData()) {
		// return false;
		// }
		// logger.debug("after getInputData....");
		//
		// //数据操作业务处理
		// if (!dealData()) {
		// return false;
		// }
		// logger.debug("after dealData...");
		//
		// //准备提交后台的数据
		// if (!prepareOutputData()) {
		// return false;
		// }
		// logger.debug("after prepareOutputData....");

		if (!tPEdorCTConfirmBL.submitData(cInputData, cOperate)) {
			mErrors.copyAllErrors(tPEdorCTConfirmBL.mErrors);
			return false;
		}
		// mResult.clear();
		mResult = tPEdorCTConfirmBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 */
	// private boolean getInputData()
	// {
	// try {
	//
	// mLPEdorItemSchema =
	// (LPEdorItemSchema)
	// mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
	// //全局变量
	// mGlobalInput =
	// (GlobalInput)
	// mInputData.getObjectByObjectName("GlobalInput", 0);
	// } catch (Exception e) {
	// e.printStackTrace();
	// CError.buildErr(this, "接收前台数据失败!");
	// return false;
	// }
	// if (mGlobalInput == null || mLPEdorItemSchema == null) {
	// CError.buildErr(this, "传入数据有误!");
	// return false;
	// }
	//
	// return true;
	// }
	/**
	 * 根据前面准备的数据进行逻辑处理
	 */
	// private boolean dealData()
	// {
	//
	// String sEdorNo = mLPEdorItemSchema.getEdorNo();
	//
	// ContCancel tContCancel = new ContCancel();
	//
	// LPContDB tLPContDB = new LPContDB();
	// tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
	// tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
	// tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
	// LPContSet tLPContSet = tLPContDB.query();
	// if (tLPContDB.mErrors.needDealError()) {
	// CError.buildErr(this, "查询保全个人保单数据失败!");
	// return false;
	// }
	//
	// LPInsuredDB tLPInsuredDB = new LPInsuredDB();
	// tLPInsuredDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
	// tLPInsuredDB.setEdorType(mLPEdorItemSchema.getEdorType());
	// tLPInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
	// LPInsuredSet tLPInsuredSet = tLPInsuredDB.query();
	// if (tLPInsuredDB.mErrors.needDealError()) {
	// CError.buildErr(this, "查询保全个人保单被保险人数据失败!");
	// return false;
	// }
	//
	// LPPolDB tLPPolDB = new LPPolDB();
	// tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
	// tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
	// tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
	// LPPolSet tLPolSet = tLPPolDB.query();
	// if (tLPPolDB.mErrors.needDealError()) {
	// CError.buildErr(this, "查询保全个人保单险种数据失败!");
	// return false;
	// }
	//
	// MMap tMMap;
	// if (tLPContSet.size() > 0) { //合同退保
	// tMMap = new MMap();
	// tMMap = tContCancel.
	// prepareContData(mLPEdorItemSchema.getContNo(), sEdorNo);
	// if (tContCancel.mErrors.needDealError()) {
	// CError.buildErr(this, "准备个人保单退保数据失败!");
	// return false;
	// }
	// map.add(tMMap);
	// } else {
	// if (tLPInsuredSet.size() > 0) { //被保险人退保
	// tMMap = new MMap();
	// tMMap = tContCancel.prepareInsuredData(mLPEdorItemSchema.
	// getContNo(),
	// mLPEdorItemSchema.getInsuredNo(),
	// sEdorNo);
	// if (tContCancel.mErrors.needDealError()) {
	// CError.buildErr(this, "准备被保险人退保数据失败!");
	// return false;
	// }
	// map.add(tMMap);
	// }
	// if (tLPolSet.size() > 0) {
	// for (int i = 1; i <= tLPolSet.size(); i++) {
	// tMMap = new MMap();
	// tMMap = tContCancel.preparePolData(tLPolSet.get(i).getPolNo(),
	// sEdorNo);
	// if (tContCancel.mErrors.needDealError()) {
	// CError.buildErr(this, "准备个人险种退保数据失败!");
	// return false;
	// }
	// map.add(tMMap);
	// }
	// } else {
	// CError.buildErr(this, "没有查到任何准备退保的数据!");
	// return false;
	// }
	//
	// }
	//
	// mInputData.clear();
	//
	// //备份保单信息
	// logger.debug("\n\nStart 撤单数据准备");
	//
	//
	// logger.debug("End 撤单数据准备\n\n");
	//
	// //得到当前LPEdorMain保单信息主表的状态，并更新状态为申请确认。
	// // tLPEdorMainSchema.setSchema(mLPEdorItemSchema);
	// //
	// // tLPEdorMainSchema.setUpdateFields();
	// // mLPEdorItemSchema = tLPEdorMainSchema.getSchema();
	//
	// //更新保全项目状态为确认生效
	// mLPEdorItemSchema.setEdorState("0");
	// mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
	// mLPEdorItemSchema.setModifyDate(mCurrentDate);
	// mLPEdorItemSchema.setModifyTime(mCurrentTime);
	//
	// map.put(mLPEdorItemSchema, "UPDATE");
	//
	// return true;
	// }
	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	// private boolean prepareOutputData()
	// {
	// try {
	// mResult.clear();
	// mResult.add(map);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
	// return false;
	// }
	//
	// return true;
	// }

	public static void main(String[] args) {
		PEdorEAConfirmBL tPEdorEAConfirmBL = new PEdorEAConfirmBL();
	}
}
