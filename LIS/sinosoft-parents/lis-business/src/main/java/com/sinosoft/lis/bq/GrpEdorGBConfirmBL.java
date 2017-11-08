package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团体保全领取年龄变更处理
 * </p>
 * 
 * <p>
 * Description: 团体保全领取年龄变更保全确认处理类
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
 * @author Lizhuo
 * @version 1.0
 */
public class GrpEdorGBConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorGBConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;

	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();

	/** 全局数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	public GrpEdorGBConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		// 数据准备操作（dealData())
		if (!dealData()) {
			return false;
		}

		// 数据准备操作（dealData())
		if (!prepareData()) {
			return false;
		}
		this.setOperate("CONFIRM||GB");

		logger.debug("after GrpEdorGBConfirmBL...");

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLPGrpEdorItemSchema == null || mGlobalInput == null) {
			CError.buildErr(this, "输入数据不完整!");
			return false;
		}
		return true;
	}

	/**
	 * 对业务数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		if (!tLPGrpEdorItemDB.getInfo()) {
			CError.buildErr(this, "查询团险保全项目信息失败!");
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());
		return true;
	}

	private boolean dealData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1) {
			CError.buildErr(this, "查询变更个人保全信息失败!");
			return false;
		}
		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			VData cVData = new VData();
			cVData.add(mGlobalInput);
			cVData.add(tLPEdorItemSet.get(i));
			PEdorGBConfirmBL tPEdorGBConfirmBL = new PEdorGBConfirmBL();
			if (!tPEdorGBConfirmBL.submitData(cVData, "")) {
				mErrors.copyAllErrors(tPEdorGBConfirmBL.mErrors);
				mErrors.addOneError("个人被保人信息变更保全生效失败!被保人号:"
						+ tLPEdorItemSet.get(i).getInsuredNo());
				return false;
			}
			MMap map = (MMap) tPEdorGBConfirmBL.getResult()
					.getObjectByObjectName("MMap", 0);
			if (map == null) {
				CError.buildErr(this, "接收被保人信息变更保全生效结构失败!被保人号:"
						+ tLPEdorItemSet.get(i).getInsuredNo());
				return false;
			}
			mMap.add(map);
		}
		mLPGrpEdorItemSchema.setEdorState("0");
		mLPGrpEdorItemSchema.setModifyDate(CurrDate);
		mLPGrpEdorItemSchema.setModifyTime(CurrTime);
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");
		return true;
	}

	private boolean prepareData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
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
}
