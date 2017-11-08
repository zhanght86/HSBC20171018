package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LCContSchema;
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
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
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
public class PEdorNSBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorNSBackConfirmBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	//public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// 需要回退的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPContSchema mLPContSchema = new LPContSchema();
	private LCContSchema mLCContSchema = new LCContSchema();

	public PEdorNSBackConfirmBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 *            传入的数据对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema = // 需要回退的保全项目
			(LPEdorItemSchema) mInputData.getObjectByObjectName(
					"LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收前台数据失败!");
			return false;
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError.buildErr(this, "传入数据有误!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		logger.debug("=== 新增附加险回退确认生效 ===");
		Reflections tRef = new Reflections();

		LPPolSchema aLPPolSchema = new LPPolSchema();
		LCPolSchema aLCPolSchema = new LCPolSchema();

		LPDutySchema aLPDutySchema = new LPDutySchema();
		LCDutySchema aLCDutySchema = new LCDutySchema();

		LPGetSchema aLPGetSchema = new LPGetSchema();
		LCGetSchema aLCGetSchema = new LCGetSchema();

		LPPremSchema aLPPremSchema = new LPPremSchema();
		LCPremSchema aLCPremSchema = new LCPremSchema();

		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSet = tLPPolDB.query();
		double bAmnt = 0;
		double bMult = 0;
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			String ProPosalNo = tLPPolSet.get(i).getProposalNo();
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolDB.setProposalNo(ProPosalNo);
			tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
			tLCPolSet = tLCPolDB.query();
			if (tLCPolSet == null || tLCPolSet.size() < 0) {
				CError.buildErr(this, "查询新增附加险失败!");
				return false;
			}
			String PolNo = tLCPolSet.get(1).getPolNo();

			String delPol = "delete from lcpol where  polno = '?PolNo?'";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(delPol);
			sbv1.put("PolNo", PolNo);
			String delDuty = "delete from lcduty where  polno = '?PolNo?'";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(delDuty);
			sbv2.put("PolNo", PolNo);
			String delPrem = "delete from lcprem where polno = '?PolNo?'";
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(delPrem);
			sbv3.put("PolNo", PolNo);
			String delGet = "delete from lcget where polno = '?PolNo?'";
			SQLwithBindVariables sbv4=new SQLwithBindVariables();
			sbv4.sql(delGet);
			sbv4.put("PolNo", PolNo);

			bAmnt += tLCPolSet.get(1).getAmnt();
			bMult += tLCPolSet.get(1).getMult();

			// 只需删除C表即可，P表中已有备份
			map.put(sbv1, "DELETE");
			map.put(sbv2, "DELETE");
			map.put(sbv3, "DELETE");
			map.put(sbv4, "DELETE");

		}

		LPContDB tLPContDB = new LPContDB();
		LCContDB tLCContDB = new LCContDB();
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			CError.buildErr(this, "查询保单信息失败!");
			return false;
		}
		tRef.transFields(mLCContSchema, tLPContDB.getSchema());
		mLCContSchema.setAmnt(mLCContSchema.getAmnt() - bAmnt);
		mLCContSchema.setMult(mLCContSchema.getMult() - bMult);

		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "查询保单信息失败!");
			return false;
		}
		tRef.transFields(mLPContSchema, tLCContDB.getSchema());
		mLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		mLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());

		map.put(mLCContSchema, "DELETE&INSERT");
		map.put(mLPContSchema, "DELETE&INSERT");

		// 新增附加险回退，撤销续期
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNo(mLPEdorItemSchema.getContNo());
		tLJSPayDB.setOtherNoType("2");
		LJSPaySet tLJSPaySet = tLJSPayDB.query();
		if (tLJSPayDB.mErrors.needDealError()) {
			CError.buildErr(this, "续期应收信息查询失败!");
			return false;
		}
		if (tLJSPaySet != null && tLJSPaySet.size() > 0) {
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("SubmitFlag", "noSubmit");
			VData tVData = new VData();
			tVData.add(mLCContSchema);
			tVData.add(tTransferData);
			tVData.add(mGlobalInput);

			IndiDueFeeCancelBL tIndiDueFeeCancelBL = new IndiDueFeeCancelBL();
			if (!tIndiDueFeeCancelBL.submitData(tVData, "BQApp")) {
				mErrors.copyAllErrors(tIndiDueFeeCancelBL.mErrors);
				return false;
			}
			tVData.clear();
			tVData = tIndiDueFeeCancelBL.getResult();
			MMap tMMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
			if (tMMap != null) {
				map.add(tMMap);
			}
		} else {
			logger.debug("== 没有续期应收数据 保单号：　"
					+ mLPEdorItemSchema.getContNo() + "==");
		}

		// add by lizhuo at 2005-12-14
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql("update lccont a set amnt = (select sum(amnt) from lcpol where contno = a.contno and appflag = '1')"
				+ ",mult = (select sum(mult) from lcpol where contno = a.contno and appflag = '1')"
				+ ",prem = (select sum(prem) from lcpol where contno = a.contno and appflag = '1')"
				+ " where a.contno = '?contno?'");
		sbv5.put("contno", mLPEdorItemSchema.getContNo());
		map
				.put(sbv5, "UPDATE");

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		PEdorNSBackConfirmBL pedornsbackconfirmbl = new PEdorNSBackConfirmBL();
	}
}
