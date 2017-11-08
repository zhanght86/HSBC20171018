package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全回退处理
 * </p>
 * 
 * <p>
 * Description: 新增特约保全回退确认类
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
 * @CreateDate 2005-10-18
 * @version 1.0
 */
public class PEdorDABackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorDABackConfirmBL.class);
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

	public PEdorDABackConfirmBL() {
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
		logger.debug("=== 新增特约回退确认生效 ===");
		Reflections tRef = new Reflections();
		LPContSchema tLPContSchema = new LPContSchema();
		LCContSchema tLCContSchema = new LCContSchema();

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLPContDB.getInfo()) {
			CError.buildErr(this, "查询原保单信息失败!");
			return false;
		}
		tRef.transFields(tLCContSchema, tLPContDB.getSchema());
		tLCContSchema.setModifyDate(CurrDate);
		tLCContSchema.setModifyTime(CurrTime);

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "查询保单信息失败!");
			return false;
		}
		tRef.transFields(tLPContSchema, tLCContDB.getSchema());
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setModifyDate(CurrDate);
		tLPContSchema.setModifyTime(CurrTime);

		map.put(tLCContSchema, "DELETE&INSERT");
		map.put(tLPContSchema, "DELETE&INSERT");

		LCPolSchema tLCPolSchema = new LCPolSchema();
		LPPolSchema tLPPolSchema = new LPPolSchema();

		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLPPolDB.getInfo()) {
			CError.buildErr(this, "查询原险种信息失败!");
			return false;
		}
		tRef.transFields(tLCPolSchema, tLPPolDB.getSchema());
		tLCPolSchema.setModifyDate(CurrDate);
		tLCPolSchema.setModifyTime(CurrTime);

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLCPolDB.getInfo()) {
			CError.buildErr(this, "查询险种信息失败!");
			return false;
		}
		tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
		tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSchema.setModifyDate(CurrDate);
		tLPPolSchema.setModifyTime(CurrTime);

		map.put(tLCPolSchema, "DELETE&INSERT");
		map.put(tLPPolSchema, "DELETE&INSERT");

		String delDuty = "delete from lcduty where dutycode = '607001' and polno = '?polno?'";
		String delPrem = "delete from lcprem where dutycode = '607001' and polno = '?polno?'";
		String delGet = "delete from lcget where dutycode = '607001' and polno = '?polno?'";
        SQLwithBindVariables sbv1=new SQLwithBindVariables();
        sbv1.sql(delDuty);
        sbv1.put("polno", mLPEdorItemSchema.getPolNo());
        SQLwithBindVariables sbv2=new SQLwithBindVariables();
        sbv2.sql(delPrem);
        sbv2.put("polno", mLPEdorItemSchema.getPolNo());
        SQLwithBindVariables sbv3=new SQLwithBindVariables();
        sbv3.sql(delGet);
        sbv3.put("polno", mLPEdorItemSchema.getPolNo());
		map.put(sbv1, "DELETE");
		map.put(sbv2, "DELETE");
		map.put(sbv3, "DELETE");

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
		PEdorDABackConfirmBL pedordabackconfirmbl = new PEdorDABackConfirmBL();
	}
}
