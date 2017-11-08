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
 * Title: 团体保全 - 被保人职业类别变更
 * </p>
 * 
 * <p>
 * Description: 团体保全 - 被保人职业类别变更保全确认类
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
public class GrpEdorIOConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorIOConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();

	public GrpEdorIOConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-------Grp IO Confirm----");

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
		this.setOperate("CONFIRM||IO");

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
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1) {
			CError.buildErr(this, "查询个人保全变更信息失败!");
			return false;
		}
		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			VData pVData = new VData();
			pVData.add(mGlobalInput);
			pVData.add(tLPEdorItemSet.get(i));

			PEdorIOConfirmBL tPEdorIOConfirmBL = new PEdorIOConfirmBL();
			if (!tPEdorIOConfirmBL.submitData(pVData, "")) {
				mErrors.copyAllErrors(tPEdorIOConfirmBL.mErrors);
				CError.buildErr(this, "执行个人职业类别确认失败!");
				return false;
			}
			mMap.add((MMap) tPEdorIOConfirmBL.getResult()
					.getObjectByObjectName("MMap", 0));
		}

		// 保存LCGrpCont，LCGrpPol数据
		String EdorInfo = "'" + mLPGrpEdorItemSchema.getEdorNo() + "','"
				+ mLPGrpEdorItemSchema.getEdorType() + "'";
		// String[] tab = {"LCGrpCont","LCGrpPol"};
		String[] tab = { "LCGrpCont" };
		for (int t = 0; t < tab.length; t++) {
			mMap.put("insert into "
					+ tab[t].toUpperCase().replaceFirst("C", "P") + " (select "
					+ EdorInfo + ",a.* from " + tab[t]
					+ " a where grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo() + "')", "INSERT");
		}
		// 修改LCGrpCont的Amnt,Prem,Mult
		mMap
				.put(
						"update lcgrpcont a set (amnt,prem,mult) = (select nvl(sum(amnt),0),nvl(sum(prem),0),nvl(sum(mult),0) from lcpol where grpcontno = a.grpcontno and appflag = '1') where a.grpcontno = '"
								+ mLPGrpEdorItemSchema.getGrpContNo() + "'",
						"UPDATE");
		// 修改LCGrpPol的Amnt,Prem,Mult
		mMap
				.put(
						"update lcgrppol a set (amnt,prem,mult) = (select nvl(sum(amnt),0),nvl(sum(prem),0),nvl(sum(mult),0) from lcpol where grppolno = a.grppolno and appflag = '1') where a.grpcontno = '"
								+ mLPGrpEdorItemSchema.getGrpContNo() + "'",
						"UPDATE");

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
