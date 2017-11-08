package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPContStateDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
 * @version 2.0
 */
public class PEdorRCConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorRCConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorRCConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");
		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 数据准备操作
		if (!prepareData()) {
			return false;
		}
		logger.debug("---End prepareData---");

		// 数据操作业务处理

		return true;
	}

	/**
	 * 数据处理的方法,cont表与contstate表的PC互换 add by ssx at 2008-08-06
	 * 
	 * @return
	 */
	private boolean dealData() {

		try {

			Reflections tReflections = new Reflections();

			// -------------------cont表的互换-------------------------------
			// 查询LPPCont P,C互换
			LPContDB tLPContDB = new LPContDB();
			tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
			if (!tLPContDB.getInfo()) {
				CError.buildErr(this, "查询保全合同表发生异常！");
				return false;
			}
			LPContSchema tLPContSchema = tLPContDB.getSchema();
			LCContSchema tLCContSchemaNew = new LCContSchema();
			// P表拷贝到C表
			tReflections.transFields(tLCContSchemaNew, tLPContSchema);
			tLCContSchemaNew.setOperator(mGlobalInput.Operator);
			tLCContSchemaNew.setModifyDate(mCurrentDate);
			tLCContSchemaNew.setModifyTime(mCurrentTime);
			mMap.put(tLCContSchemaNew, "DELETE&INSERT");

			// 查询LCCont C,P互换
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
			if (!tLCContDB.getInfo()) {
				CError.buildErr(this, "查询个人合同表发生异常！");
				return false;
			}
			LCContSchema tLCContSchema = tLCContDB.getSchema();
			LPContSchema tLPContSchemaNew = new LPContSchema();
			// C表拷贝到P表
			tReflections.transFields(tLPContSchemaNew, tLCContSchema);
			tLPContSchemaNew.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContSchemaNew.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPContSchemaNew.setOperator(mGlobalInput.Operator);
			tLPContSchemaNew.setModifyDate(mCurrentDate);
			tLPContSchemaNew.setModifyTime(mCurrentTime);
			mMap.put(tLPContSchemaNew, "DELETE&INSERT");

			// ----------------cont表的互换结束----------------------------

		} catch (Exception ex) {
			CError.buildErr(this, "数据业务处理异常" + ex.getMessage());
			return false;
		}
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
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorLRConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
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
		mLPEdorMainSchema = tLPEdorMainDB.getSchema();

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		mResult.clear();
		mLPEdorMainSchema.setEdorState("0");
		mLPEdorMainSchema.setConfDate(PubFun.getCurrentDate());
		mLPEdorMainSchema.setConfOperator(mGlobalInput.Operator);
		// 当前日期为保全生效日期
		mLPEdorMainSchema.setEdorValiDate(PubFun.getCurrentDate());
		mMap.put(mLPEdorMainSchema, "UPDATE");
		mResult.add(mMap);

		return true;
	}

	public static void main(String[] args) {
		logger.debug("test start:");
		GlobalInput tG = new GlobalInput();
		tG.Operator = "bq";
		tG.ManageCom = "86";

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setEdorAcceptNo("6120051220000098");
		tLPEdorItemSchema.setContNo("NJG10227011000293");
		tLPEdorItemSchema.setEdorNo("6020051220000057");
		tLPEdorItemSchema.setEdorType("LR");
		tLPEdorItemSchema.setInsuredNo("000000");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tLPEdorItemSchema);
		try {
			Class tClass = Class
					.forName("com.sinosoft.lis.bq.PEdorLRConfirmBL");
			EdorConfirm tEdorConfirm = (EdorConfirm) tClass.newInstance();
			if (!tEdorConfirm.submitData(tVData, "CONFIRM||LR")) {
				logger.debug("== fail to PEdorLRConfirmBL ==");

			} else {
				logger.debug("== after PEdorLRConfirmBL ==");
			}
			VData rVData = tEdorConfirm.getResult();
			MMap tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
			MMap map = new MMap();
			map.add(tMap);
			VData mInputData = new VData();
			mInputData.add(map);
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, "")) {
				logger.debug("pusumit errors:"
						+ tPubSubmit.mErrors.getError(0).errorMessage);
			}

		} catch (Exception ex) {
			logger.debug("error:" + ex.toString());
		}
		logger.debug("test end");
	}
}
