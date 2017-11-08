/**
 * 
 */
package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpContDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团体保全处理 - 整单加保
 * </p>
 * 
 * <p>
 * Description: 整单加保保全确认处理BL类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author lizhuo
 * @version 1.0
 * @CreateDate 2006-3-8
 */
public class GrpEdorWAConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorWAConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 签单调用传入的集体保单号 */
	/** 传入的业务数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();

	public GrpEdorWAConfirmBL() {
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.sinosoft.lis.bq.EdorConfirm#submitData(com.sinosoft.utility.VData,
	 *      java.lang.String)
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getinputData()) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareData()) {
			return false;
		}
		return true;
	}

	private boolean prepareData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	private boolean dealData() {
		Reflections tRef = new Reflections();

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1) {
			CError.buildErr(this, "查询加保有效个人保全信息失败!");
			return false;
		}

		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			VData cVData = new VData();
			cVData.add(tLPEdorItemSet.get(i));
			cVData.add(mGlobalInput);
			PEdorAAConfirmBL tPEdorAAConfirmBL = new PEdorAAConfirmBL();
			if (!tPEdorAAConfirmBL.submitData(cVData, "")) {
				mErrors.copyAllErrors(tPEdorAAConfirmBL.mErrors);
				CError.buildErr(this, "有效个人加保保全生效失败!");
				return false;
			}
			MMap map = (MMap) tPEdorAAConfirmBL.getResult()
					.getObjectByObjectName("MMap", 0);
			if (map != null) {
				mMap.add(map);
			}
		}

		LPGrpContSchema tLPGrpContSchema = new LPGrpContSchema();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		LPGrpContDB tLPGrpContDB = new LPGrpContDB();
		tLPGrpContDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLPGrpContDB.getInfo()) {
			CError.buildErr(this, "查询");
			return false;
		}
		tRef.transFields(tLCGrpContSchema, tLPGrpContDB.getSchema());
		tLCGrpContSchema.setOperator(mGlobalInput.Operator);
		tLCGrpContSchema.setModifyDate(CurrDate);
		tLCGrpContSchema.setModifyTime(CurrTime);
		mMap.put(tLCGrpContSchema, "UPDATE");

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "查询团体保单信息失败!");
			return false;
		}
		tRef.transFields(tLPGrpContSchema, tLCGrpContDB.getSchema());
		tLPGrpContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpContSchema.setModifyDate(CurrDate);
		tLPGrpContSchema.setModifyTime(CurrTime);
		mMap.put(tLPGrpContSchema, "DELETE&INSERT");

		LPGrpPolSchema tLPGrpPolSchema;
		LCGrpPolSchema tLCGrpPolSchema;
		LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();

		LPGrpPolDB cLPGrpPolDB = new LPGrpPolDB();
		cLPGrpPolDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		cLPGrpPolDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		LPGrpPolSet cLPGrpPolSet = cLPGrpPolDB.query();
		if (cLPGrpPolSet != null && cLPGrpPolSet.size() > 0) {
			for (int i = 1; i <= cLPGrpPolSet.size(); i++) {
				tLCGrpPolSchema = new LCGrpPolSchema();
				tRef.transFields(tLCGrpPolSchema, cLPGrpPolSet.get(i));
				tLCGrpPolSchema.setOperator(mGlobalInput.Operator);
				tLCGrpPolSchema.setModifyDate(CurrDate);
				tLCGrpPolSchema.setModifyTime(CurrTime);
				tLCGrpPolSet.add(tLCGrpPolSchema);

				LCGrpPolDB cLCGrpPolDB = new LCGrpPolDB();
				cLCGrpPolDB.setGrpPolNo(cLPGrpPolSet.get(i).getGrpPolNo());
				if (!cLCGrpPolDB.getInfo()) {
					CError.buildErr(this, "查询团体险种信息失败!");
					return false;
				}
				tLPGrpPolSchema = new LPGrpPolSchema();
				tRef.transFields(tLPGrpPolSchema, cLCGrpPolDB.getSchema());
				tLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				tLPGrpPolSchema.setOperator(mGlobalInput.Operator);
				tLPGrpPolSchema.setModifyDate(CurrDate);
				tLPGrpPolSchema.setModifyTime(CurrTime);
				tLPGrpPolSet.add(tLPGrpPolSchema);
			}
			mMap.put(tLCGrpPolSet, "DELETE&INSERT");
			mMap.put(tLPGrpPolSet, "DELETE&INSERT");
		}

		mLPGrpEdorItemSchema.setEdorState("0");
		mLPGrpEdorItemSchema.setModifyDate(CurrDate);
		mLPGrpEdorItemSchema.setModifyTime(CurrTime);
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");

		return true;
	}

	private boolean checkData() {
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		if (!tLPGrpEdorItemDB.getInfo()) {
			CError.buildErr(this, "查询团体保全项目信息失败!");
			return false;
		}
		mLPGrpEdorItemSchema = tLPGrpEdorItemDB.getSchema();
		return true;
	}

	private boolean getinputData() {
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mGlobalInput == null || mLPGrpEdorItemSchema == null) {
			CError.buildErr(this, "接受数据不完整!");
		}
		return true;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.sinosoft.lis.bq.EdorConfirm#getResult()
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
