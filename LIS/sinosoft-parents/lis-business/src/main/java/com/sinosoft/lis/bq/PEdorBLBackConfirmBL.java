package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全- 保单质押银行贷款回退确认生效处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：liurx
 * @version：1.0
 * @CreateDate：2005-10-13
 */
public class PEdorBLBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorBLBackConfirmBL.class);
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

	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private String mContNo = "";

	public PEdorBLBackConfirmBL() {
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
		logger.debug("=== 保单质押银行贷款回退确认生效 ===");
		String tEndDate = PubFun.calDate(mLPEdorItemSchema.getEdorValiDate(),
				-1, "D", null);
		mContNo = mLPEdorItemSchema.getContNo();
		if (mContNo == null || mContNo.trim().equals("")) {
			CError.buildErr(this, "保单号为空!");
			return false;
		}
		Reflections tRef = null;
		// 查询贷款状态记录
		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = new LCContStateSet();
		String strsql = "select * from lccontstate where contno = '"
				+ "?mContNo?"
				+ "' "
				+ " and statetype = 'BankLoan' and state = '1' and enddate is null "
				+ " and startdate = '" + "?startdate?"
				+ "'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(strsql);
		sbv1.put("mContNo", mContNo);
		sbv1.put("startdate", mLPEdorItemSchema.getEdorValiDate());
		tLCContStateSet = tLCContStateDB.executeQuery(sbv1);
		// 如果存在，则删除此记录，并备份到p表中
		if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
			tRef = new Reflections();
			LPContStateSet tLPContStateSet = new LPContStateSet();
			for (int i = 1; i <= tLCContStateSet.size(); i++) {
				LPContStateSchema tLPContStateSchema = new LPContStateSchema();
				tRef.transFields(tLPContStateSchema, tLCContStateSet.get(i));
				tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPContStateSet.add(tLPContStateSchema);
			}
			map.put(tLPContStateSet, "DELETE&INSERT");
			map.put(tLCContStateSet, "DELETE");
		}
		// 查找旧状态数据，如果存在，则置其enddate为空，并将其备份到p表中
		LCContStateDB oldLCContStateDB = new LCContStateDB();
		LCContStateSet oldLCContStateSet = new LCContStateSet();
		strsql = "select * from lccontstate where contno = '" + "?mContNo?" + "' "
				+ " and statetype = 'BankLoan' and state = '0' "
				+ " and enddate = '" + "?tEndDate?" + "'";
		sbv1=new SQLwithBindVariables();
		sbv1.sql(strsql);
		sbv1.put("mContNo", mContNo);
		sbv1.put("tEndDate", tEndDate);
		oldLCContStateSet = oldLCContStateDB.executeQuery(sbv1);
		if (oldLCContStateSet != null && oldLCContStateSet.size() > 0) {
			tRef = new Reflections();
			LPContStateSet oldLPContStateSet = new LPContStateSet();
			LCContStateSet uptLCContStateSet = new LCContStateSet();
			for (int i = 1; i <= oldLCContStateSet.size(); i++) {
				LPContStateSchema tLPContStateSchema = new LPContStateSchema();
				LCContStateSchema tLCContStateSchema = new LCContStateSchema();
				tLCContStateSchema.setSchema(oldLCContStateSet.get(i));
				tRef.transFields(tLPContStateSchema, oldLCContStateSet.get(i));
				tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				oldLPContStateSet.add(tLPContStateSchema);
				tLCContStateSchema.setEndDate("");
				uptLCContStateSet.add(tLCContStateSchema);
			}
			map.put(oldLPContStateSet, "DELETE&INSERT");
			map.put(uptLCContStateSet, "UPDATE");
		}
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
		return null;
	}

}
