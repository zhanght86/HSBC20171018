package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
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
public class PEdorDTDetailBL implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorDTDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	GlobalInput mGlobalInput = new GlobalInput();
	LCContSchema mLCContSchema = new LCContSchema();

	public PEdorDTDetailBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据
	 * @param: cOperate 数据操作字符串
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			mErrors.addOneError("接收数据失败!");
			return false;
		}
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError("接受数据无效!");
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemSet = tLPEdorItemDB.query();
		mLPEdorItemSchema = tLPEdorItemSet.get(1);

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}

		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LCDutyDB tLCDutyDB = new LCDutyDB();
		LCDutySet tLCDutySet = new LCDutySet();
		tLCDutyDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLCDutyDB.setDutyCode("607001");
		tLCDutySet = tLCDutyDB.query();
		if (tLCDutySet == null) {
			mErrors.addOneError("没有豁免责任，不能进行特约责任终止!");
			return false;
		}
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
		}
		mLCContSchema.setSchema(tLCContDB.getSchema());
		return true;
	}

	/**
	 * 准备需要保存的数据 调用个人保全投保人信息变更业务逻辑处理类PEdorACBL进行处理
	 */
	private boolean dealData() {
		double bPrem = 0;

		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolDB.setContNo(mLPEdorItemSchema.getEdorNo());
		tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLCPolDB.getInfo()) {
			mErrors.addOneError("查询险种信息失败!");
			return false;
		}
		tLCPolSchema = tLCPolDB.getSchema();
		LCDutyDB tLCDutyDB = new LCDutyDB();
		LCDutySet tLCDutySet = new LCDutySet();
		tLCDutyDB.setPolNo(tLCPolSchema.getPolNo());
		tLCDutySet = tLCDutyDB.query();
		if (tLCDutySet == null) {
			mErrors.addOneError("查询险种责任信息失败!");
			return false;
		}
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = new LCPremSet();
		String StrSQL = "select * from lcprem where polno = '?polno?' and payenddate > '?payenddate?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(StrSQL);
		sqlbv.put("polno", tLCPolSchema.getPolNo());
		sqlbv.put("payenddate", mLPEdorItemSchema.getEdorValiDate());
		tLCPremSet = tLCPremDB.executeQuery(sqlbv);
		if (tLCPremSet == null) {
			mErrors.addOneError("查询险种缴费项表失败!");
			return false;
		}

		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = new LCGetSet();
		tLCGetDB.setPolNo(tLCPolSchema.getPolNo());
		tLCGetSet = tLCGetDB.query();
		if (tLCGetSet == null) {
			mErrors.addOneError("查询险领取责任表失败!");
			return false;
		}

		Reflections tRef = new Reflections();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LPDutySet tLPDutySet = new LPDutySet();
		LPPremSet tLPPremSet = new LPPremSet();
		LPGetSet tLPGetSet = new LPGetSet();

		tRef.transFields(tLPPolSchema, tLCPolSchema);
		tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
		tLPPolSchema.setModifyTime(PubFun.getCurrentTime());

		for (int i = 1; i <= tLCDutySet.size(); i++) {
			LPDutySchema tLPDutySchema = new LPDutySchema();
			tRef.transFields(tLPDutySchema, tLCDutySet.get(i));
			tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPDutySchema.setModifyDate(PubFun.getCurrentDate());
			tLPDutySchema.setModifyTime(PubFun.getCurrentTime());
			tLPDutySet.add(tLPDutySchema);
			if (tLPDutySchema.getDutyCode().trim().equals("607001")) {
				bPrem = tLPDutySchema.getPrem();
				tLPPolSchema.setPrem(tLPPolSchema.getPrem() - bPrem);
				tLPPolSchema.setStandPrem(tLPPolSchema.getStandPrem() - bPrem);
				tLPPolSchema.setSumPrem(tLPPolSchema.getSumPrem() - bPrem);
			}
		}

		for (int i = 1; i <= tLCPremSet.size(); i++) {
			LPPremSchema tLPPremSchema = new LPPremSchema();
			tRef.transFields(tLPPremSchema, tLCPremSet.get(i));
			tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPremSchema.setModifyDate(PubFun.getCurrentDate());
			tLPPremSchema.setModifyTime(PubFun.getCurrentTime());
			tLPPremSet.add(tLPPremSchema);
		}

		for (int i = 1; i <= tLCGetSet.size(); i++) {
			LPGetSchema tLPGetSchema = new LPGetSchema();
			tRef.transFields(tLPGetSchema, tLCGetSet.get(i));
			tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPGetSchema.setModifyDate(PubFun.getCurrentDate());
			tLPGetSchema.setModifyTime(PubFun.getCurrentTime());
			tLPGetSet.add(tLPGetSchema);
		}

		LPContSchema tLPContSchema = new LPContSchema();
		tRef.transFields(tLPContSchema, mLCContSchema);

		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setModifyDate(PubFun.getCurrentDate());
		tLPContSchema.setModifyTime(PubFun.getCurrentTime());
		tLPContSchema.setPrem(tLPContSchema.getPrem() - bPrem);
		tLPContSchema.setSumPrem(tLPContSchema.getSumPrem() - bPrem);

		mMap.put(tLPContSchema, "DELETE&INSERT");
		mMap.put(tLPPolSchema, "DELETE&INSERT");
		mMap.put(tLPDutySet, "DELETE&INSERT");
		mMap.put(tLPPremSet, "DELETE&INSERT");
		mMap.put(tLPGetSet, "DELETE&INSERT");
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

}
