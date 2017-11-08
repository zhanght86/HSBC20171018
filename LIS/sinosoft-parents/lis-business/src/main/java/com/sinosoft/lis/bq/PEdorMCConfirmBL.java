package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAccountDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPAccountDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LPAccountSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPAccountSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 交费形式变更
 * </p>*
 * <p>
 * Description: 保全确认类
 * </p>*
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>*
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lihs
 * @version 1.0
 */

public class PEdorMCConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorMCConfirmBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorMCConfirmBL() {
	}

	public VData getResult() {
		return mResult;
	}

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
		getInputData(mInputData);
		// 数据准备操作（preparedata())
		if (!prepareData()) {
			return false;
		}
		this.setOperate("CONFIRM||MC");
		logger.debug("---" + mOperate);
		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
			mErrors.addOneError(new CError("查询批改项目信息失败！"));
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		return true;
	}

	private boolean prepareData() {
		Reflections tRef = new Reflections();

		LPContDB tLPContDB = new LPContDB();

		LPContSet aLPContSet = new LPContSet();
		LPContSet tLPContSet = new LPContSet();
		LCContSet aLCContSet = new LCContSet();
		LCContSet tLCContSet = new LCContSet();

		LPContSchema tLPContSchema = new LPContSchema();
		LCContSchema tLCContSchema = new LCContSchema();
		LPContSchema aLPContSchema = new LPContSchema();
		LCContSchema aLCContSchema = new LCContSchema();

		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setContNo(mLPEdorItemSchema.getContNo());

		tLPContDB.setSchema(tLPContSchema);
		tLPContSet = tLPContDB.query();
		for (int j = 1; j <= tLPContSet.size(); j++) {
			aLPContSchema = tLPContSet.get(j);
			// 更新应收总表
			updateLJSPay(aLPContSchema);
			tLCContSchema = new LCContSchema();
			tLPContSchema = new LPContSchema();

			LCContDB aLCContDB = new LCContDB();
			aLCContDB.setContNo(aLPContSchema.getContNo());
			aLCContDB.setInsuredNo(aLPContSchema.getInsuredNo());
			if (!aLCContDB.getInfo()) {
				mErrors.copyAllErrors(aLCContDB.mErrors);
				mErrors.addOneError(new CError("查询被保人信息失败！"));
				return false;
			}
			aLCContSchema = aLCContDB.getSchema();
			tRef.transFields(tLPContSchema, aLCContSchema);
			tLPContSchema.setEdorNo(aLPContSchema.getEdorNo());
			tLPContSchema.setEdorType(aLPContSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCContSchema, aLPContSchema);
			tLCContSchema.setModifyDate(PubFun.getCurrentDate());
			tLCContSchema.setModifyTime(PubFun.getCurrentTime());

			aLPContSet.add(tLPContSchema);
			tLCContSet.add(tLCContSchema);
			aLCContSet.add(tLCContSchema);

		}

		// 得到当前LPEdorItem保单信息主表的状态，并更新状态为申请确认。
		mLPEdorItemSchema.setEdorState("0");
		{
			mMap.put(mLPEdorItemSchema, "UPDATE");
		}
		mMap.put(aLCContSet, "UPDATE");
		mMap.put(aLPContSet, "UPDATE");
		// 帐户表
		LPAccountSet tLPAccountSet = new LPAccountSet();
		LPAccountSchema aLPAccountSchema = new LPAccountSchema();
		LPAccountSchema tLPAccountSchema = new LPAccountSchema();
		LCAccountSchema aLCAccountSchema = new LCAccountSchema();
		tLPAccountSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAccountSchema.setEdorType(mLPEdorItemSchema.getEdorType());

		LPAccountDB tLPAccountDB = new LPAccountDB();
		tLPAccountDB.setSchema(tLPAccountSchema);
		tLPAccountSet = tLPAccountDB.query();
		if (tLPAccountSet.size() > 0) {
			for (int j = 1; j <= tLPAccountSet.size(); j++) {
				aLPAccountSchema = tLPAccountSet.get(j);
				LCAccountSchema tLCAccountSchema = new LCAccountSchema();
				tLPAccountSchema = new LPAccountSchema();

				LCAccountDB aLCAccountDB = new LCAccountDB();
				aLCAccountDB.setCustomerNo(aLPAccountSchema.getCustomerNo());
				aLCAccountDB.setBankAccNo(aLPAccountSchema.getBankAccNo());
				aLCAccountDB.setBankCode(aLPAccountSchema.getBankCode());
				aLCAccountDB.setAccName(aLPAccountSchema.getAccName());
				if (aLCAccountDB.getInfo()) {
					aLCAccountSchema = aLCAccountDB.getSchema();
					tRef.transFields(tLPAccountSchema, aLCAccountSchema);
					tLPAccountSchema.setEdorNo(aLPAccountSchema.getEdorNo());
					tLPAccountSchema
							.setEdorType(aLPAccountSchema.getEdorType());

					// 转换成保单个人信息。
					tRef.transFields(tLCAccountSchema, aLPAccountSchema);
					tLCAccountSchema.setModifyDate(PubFun.getCurrentDate());
					tLCAccountSchema.setModifyTime(PubFun.getCurrentTime());

					mMap.put(tLPAccountSchema, "UPDATE");
					mMap.put(tLCAccountSchema, "UPDATE");
				} else {
					tRef.transFields(tLCAccountSchema, aLPAccountSchema);
					logger.debug(tLCAccountSchema.encode());
					tLCAccountSchema.setMakeDate(PubFun.getCurrentDate());
					tLCAccountSchema.setModifyTime(PubFun.getCurrentTime());
					tLCAccountSchema.setModifyDate(PubFun.getCurrentDate());
					tLCAccountSchema.setModifyTime(PubFun.getCurrentTime());

					mMap.put(tLCAccountSchema, "INSERT");
					mMap.put(aLPAccountSchema, "DELETE");
				}
			}
		}

		mResult.add(mMap);
		return true;
	}

	/**
	 * updateLJSPay 更新应收总表
	 * 
	 * @param tLPContSchema
	 *            LPContSchema
	 */
	private void updateLJSPay(LPContSchema tLPContSchema) {
		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();

		tLJSPaySchema.setOtherNoType("2");
		tLJSPaySchema.setOtherNo(tLPContSchema.getContNo());
		tLJSPaySchema.setOperator(mGlobalInput.Operator);

		tLJSPayDB.setSchema(tLJSPaySchema);
		tLJSPaySet = tLJSPayDB.query();
		if (tLJSPaySet.size() > 0) {
			LJSPaySet aLJSPaySet = new LJSPaySet();
			for (int j = 1; j <= tLJSPaySet.size(); j++) {
				LJSPaySchema aLJSPaySchema = tLJSPaySet.get(j);
				aLJSPaySchema.setBankCode(tLPContSchema.getBankCode());
				aLJSPaySchema.setBankAccNo(tLPContSchema.getBankAccNo());
				aLJSPaySchema.setAccName(tLPContSchema.getAccName());
				aLJSPaySet.add(aLJSPaySchema);
			}
			mMap.put(aLJSPaySet, "UPDATE");
		}

	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		tGlobalInput.ComCode = "86";
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

		tLPEdorItemSchema.setEdorAcceptNo("86000000001019");
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setEdorNo("410000000000807");
		tLPEdorItemSchema.setEdorType("PC");
		tLPEdorItemSchema.setContNo("230110000000501");
		tLPEdorItemSchema.setInsuredNo("000000");

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLPEdorItemSchema);

		PEdorMCConfirmBL tPEdorAEConfirmBL = new PEdorMCConfirmBL();
		if (tPEdorAEConfirmBL.submitData(tVData, "")) {
			logger.debug("OK!");
		}

	}
}
