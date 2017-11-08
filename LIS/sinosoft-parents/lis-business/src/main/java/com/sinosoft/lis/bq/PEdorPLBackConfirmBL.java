package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LPContStateDB;
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
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全回退处理
 * </p>
 * 
 * <p>
 * Description: 保单挂失、解挂回退处理确认类
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
 * @CreateDate 2005-10-10
 * @version 1.0
 */
public class PEdorPLBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorPLBackConfirmBL.class);
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

	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();
	// 需要回退的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorPLBackConfirmBL() {
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
		logger.debug("=== 保单挂失、解挂回退确认生效 ===");
		Reflections tRef = new Reflections();

		LCContStateSet aLCContStateSet = new LCContStateSet();
		LPContStateSet aLPContStateSet = new LPContStateSet();

		LPContStateDB tLPContStateDB = new LPContStateDB();
		LPContStateSet tLPContStateSet = new LPContStateSet();
		tLPContStateDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContStateDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContStateDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContStateDB.setStateType("Lost");
		tLPContStateSet = tLPContStateDB.query();
		if (tLPContStateSet == null || tLPContStateSet.size() < 1) {
			mErrors.copyAllErrors(tLPContStateSet.mErrors);
			mErrors.addOneError(new CError("查询原挂失、解挂信息失败！"));
			return false;
		}

		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = new LCContStateSet();
		String strSql = "select * from lccontstate where statetype = 'Lost' "
				+ "and enddate is null and contno = '?contno?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		tLCContStateSet = tLCContStateDB.executeQuery(sqlbv);
		if (tLCContStateSet.size() < 1) {
			CError.buildErr(this, "查询挂失、解挂失败!");
			return false;
		}
		if (StrTool.compareString(tLCContStateSet.get(1).getStartDate(),
				tLPContStateSet.get(1).getStartDate())
				&& StrTool.compareString(tLCContStateSet.get(1).getState(),
						tLPContStateSet.get(1).getState())) {
			map.put(tLCContStateSet, "DELETE");

		} else {
			for (int i = 1; i <= tLPContStateSet.size(); i++) {
				LCContStateSchema tLCContStateSchema = new LCContStateSchema();
				tRef.transFields(tLCContStateSchema, tLPContStateSet.get(i));
				tLCContStateSchema.setModifyDate(CurrDate);
				tLCContStateSchema.setModifyTime(CurrTime);
				aLCContStateSet.add(tLCContStateSchema);
			}
			for (int i = 1; i <= tLCContStateSet.size(); i++) {
				LPContStateSchema tLPContStateSchema = new LPContStateSchema();
				tRef.transFields(tLPContStateSchema, tLCContStateSet.get(i));
				tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPContStateSchema.setModifyDate(CurrDate);
				tLPContStateSchema.setModifyTime(CurrTime);
				aLPContStateSet.add(tLPContStateSchema);
			}
			map.put(tLPContStateSet, "DELETE");
			map.put(tLCContStateSet, "DELETE");
			map.put(aLPContStateSet, "DELETE&INSERT");
			map.put(aLCContStateSet, "DELETE&INSERT");
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
		return mErrors;
	}

	public static void main(String[] args) {
		PEdorPLBackConfirmBL pedorplbackconfirmbl = new PEdorPLBackConfirmBL();
	}
}
