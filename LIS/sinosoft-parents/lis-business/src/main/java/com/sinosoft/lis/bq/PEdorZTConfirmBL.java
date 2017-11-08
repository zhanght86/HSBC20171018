package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.pubfun.ContCancel;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vbl.LCPolBLSet;
import com.sinosoft.lis.vbl.LPInsuredBLSet;
import com.sinosoft.lis.vbl.LPPolBLSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
 * @author Tjj
 * @version 1.0
 */
public class PEdorZTConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorZTConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务对象 */
	private LCPolBL mLCPolBL = new LCPolBL();
	private LCPolBLSet mLCPolBLSet = new LCPolBLSet();
	private LPPolBL mLPPolBL = new LPPolBL();
	private LPPolBLSet mLPPolBLSet = new LPPolBLSet();
	private LPInsuredBLSet mLPInsuredBLSet = new LPInsuredBLSet();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();

	public PEdorZTConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		getInputData(cInputData);
		logger.debug("after get input data....");

		// 数据准备操作（preparedata()),撤单数据准备
		if (!prepareData())
			return false;
		logger.debug("after prepareData data....");

		// this.setOperate("CONFIRM||ZT");
		// logger.debug("---"+mOperate);
		//
		// // 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		// PEdorConfirmBLS tPEdorConfirmBLS = new PEdorConfirmBLS();
		// logger.debug("Start Confirm ZT BL Submit...");
		// if (!tPEdorConfirmBLS.submitData(mInputData,mOperate)) {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tPEdorConfirmBLS.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "PEdorConfirmBL";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @param cInputData
	 *            如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private void getInputData(VData cInputData) {
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		String flag, cOperate, insuredName;
		String aEdorNo, aPolNo;
		int m = 0;
		flag = "N";
		insuredName = null;
		aEdorNo = null;
		aPolNo = null;

		// LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();

		aPolNo = mLPEdorItemSchema.getPolNo();
		aEdorNo = mLPEdorItemSchema.getEdorNo();
		ContCancel tContCancel = new ContCancel();
		if (mLPEdorItemSchema.getPolNo().equals("000000")
				&& mLPEdorItemSchema.getInsuredNo().equals("000000")) // 合同退保
		{
			MMap tMMap = new MMap();
			tMMap = tContCancel.prepareContData(mLPEdorItemSchema.getContNo(),
					aEdorNo);
			if (tContCancel.mErrors.needDealError()) {
				CError.buildErr(this, "准备个人保单数据失败！");
				return false;
			}
			map.add(tMMap);
		} else if (mLPEdorItemSchema.getPolNo().equals("000000")) // 被保险人退保
		{
			MMap tMMap = new MMap();
			tMMap = tContCancel.prepareInsuredData(mLPEdorItemSchema
					.getContNo(), mLPEdorItemSchema.getInsuredNo(), aEdorNo);
			if (tContCancel.mErrors.needDealError()) {
				CError.buildErr(this, "准备被保险人数据失败！");
				return false;
			}
			map.add(tMMap);

		} else {
			MMap tMMap = new MMap();
			tMMap = tContCancel.preparePolData(aPolNo, aEdorNo);
			if (tContCancel.mErrors.needDealError()) {
				CError.buildErr(this, "准备个人险种数据失败！");
				return false;
			}
			map.add(tMMap);
		}

		mInputData.clear();

		// 备份保单信息
		logger.debug("\n\nStart 撤单数据准备");
		// if (aMainPolNo.compareTo(aPolNo) == 0) //主险单，同时处理主附险
		// //已增加帐户处理，by Minim at 2004-2-25
		// mInputData = tContCancel.prepareMainPolData(aPolNo,aEdorNo);
		// else //附单，应该是只处理附加险

		logger.debug("End 撤单数据准备\n\n");

		// 得到当前LPEdorMain保单信息主表的状态，并更新状态为申请确认。
		// tLPEdorMainSchema.setSchema(mLPEdorItemSchema);
		//
		// tLPEdorMainSchema.setUpdateFields();
		// mLPEdorItemSchema = tLPEdorMainSchema.getSchema();

		mLPEdorItemSchema.setEdorState("0");
		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		// mLPEdorItemSchema.setConfDate(PubFun.getCurrentDate());
		// mLPEdorItemSchema.setConfOperator(mGlobalInput.Operator);

		map.put(mLPEdorItemSchema, "UPDATE");

		mResult.clear();
		mResult.add(map);

		return true;
	}

}
