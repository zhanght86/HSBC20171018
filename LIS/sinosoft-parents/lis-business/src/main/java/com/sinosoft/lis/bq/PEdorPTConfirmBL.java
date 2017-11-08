package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
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
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
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
 * Description: 保全申请确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @ReWrite by lizhuo
 * @version 1.0
 */
public class PEdorPTConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorPTConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private MMap mMap = new MMap();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorPTConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("PEdorPTConfirm------------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		getInputData(cInputData);

		if (!dealData()) {
			return false;
		}
		logger.debug("after dealData");

		if (!prepareOutData()) {
			return false;
		}
		logger.debug("after prepareOutData");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private void getInputData(VData cInputData) {
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		Reflections tRef = new Reflections();

		// 得到当前LPEdorItem保单信息主表的状态，并更新状态为申请确认。
		mLPEdorItemSchema.setEdorState("0");

		mMap.put(mLPEdorItemSchema, "UPDATE");

		/* LCPol <---> LPPol（得到投保人资料信息表p，c 表 ） */
		LPPolSet tLPPolSet = new LPPolSet();
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());

		String strSql = "select * from LPPol where EdorNo = '?EdorNo?' and EdorType = '?EdorType?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("EdorType", mLPEdorItemSchema.getEdorType());

		tLPPolSet = tLPPolDB.executeQuery(sqlbv);
		LPPolSet aLPPolSet = new LPPolSet();
		LCPolSet aLCPolSet = new LCPolSet();
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			LPPolSchema tLPPolSchema = new LPPolSchema();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tRef.transFields(tLCPolSchema, tLPPolSet.get(i));
			tLCPolSchema.setModifyTime(PubFun.getCurrentTime());
			tLCPolSchema.setModifyDate(PubFun.getCurrentDate());

			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.copyAllErrors(tLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询险种保单表失败！"));
				return false;
			}

			tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolSchema.setModifyTime(PubFun.getCurrentTime());
			tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
			aLPPolSet.add(tLPPolSchema);
			aLCPolSet.add(tLCPolSchema);
		}
		mMap.put(aLPPolSet, "UPDATE");
		mMap.put(aLCPolSet, "UPDATE");
		
		// 险种责任表 lcduty lpduty
		LPDutyDB tLPDutyDB = new LPDutyDB();
		LPDutySchema tLPDutySchema = new LPDutySchema();
		LPDutySet tLPDutySet = new LPDutySet();
		tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPDutyDB.setSchema(tLPDutySchema);
		tLPDutySet = tLPDutyDB.query();
		if (tLPDutySet.size() < 1) {
			CError.buildErr(this, "查询险种责任表失败!");
			return false;
		}
        
		LCDutySet aLCDutySet = new LCDutySet();
		LPDutySet aLPDutySet = new LPDutySet();
		
		for (int i = 1; i <= tLPDutySet.size(); i++) {
			LCDutySchema aLCDutySchema = new LCDutySchema();
			LPDutySchema aLPDutySchema = new LPDutySchema();
			aLPDutySchema = tLPDutySet.get(i);
			tRef.transFields(aLCDutySchema, aLPDutySchema);
			aLCDutySchema.setModifyDate(PubFun.getCurrentDate());
			aLCDutySchema.setModifyTime(PubFun.getCurrentTime());
			 LCDutyDB tLCDutyDB = new LCDutyDB();
			 tLCDutyDB.setPolNo(aLPDutySchema.getPolNo());
			 tLCDutyDB.setDutyCode(aLPDutySchema.getDutyCode());
			 if (!tLCDutyDB.getInfo()) {
			 mErrors.copyAllErrors(tLCDutyDB.mErrors);
			 mErrors.addOneError(new CError("查询险种保单表失败！"));
			 return false;
			 }
			 tRef.transFields(aLPDutySchema, tLCDutyDB.getSchema());
			 aLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			 aLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
			 aLPDutySet.add(aLPDutySchema);
			 aLCDutySet.add(aLCDutySchema);// 只往C表中插数据
		}
		mMap.put(aLPDutySet,"UPDATE");
		mMap.put(aLCDutySet, "DELETE&INSERT");

		// 保费项目表 lcprem - lpprem
		LPPremDB tLPPremDB = new LPPremDB();
		LPPremSchema tLPPremSchema = new LPPremSchema();
		LPPremSet tLPPremSet = new LPPremSet();
		tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPremDB.setSchema(tLPPremSchema);
		tLPPremSet = tLPPremDB.query();
		if (tLPPremSet.size() < 1) {
			CError.buildErr(this, "查询保费项目表失败!");
			return false;
		}
		
		LCPremSet aLCPremSet = new LCPremSet();
		LPPremSet aLPPremSet = new LPPremSet();
		for (int i = 1; i <= tLPPremSet.size(); i++) {
			LCPremSchema aLCPremSchema = new LCPremSchema();
			LPPremSchema aLPPremSchema = new LPPremSchema();
			aLPPremSchema = tLPPremSet.get(i);
			tRef.transFields(aLCPremSchema, aLPPremSchema);
			aLCPremSchema.setModifyDate(PubFun.getCurrentDate());
			aLCPremSchema.setModifyTime(PubFun.getCurrentTime());
			 LCPremDB tLCPremDB = new LCPremDB();
			 tLCPremDB.setPolNo(aLPPremSchema.getPolNo());
			 tLCPremDB.setDutyCode(aLPPremSchema.getDutyCode());
			 tLCPremDB.setPayPlanCode(aLPPremSchema.getPayPlanCode());
			 if (!tLCPremDB.getInfo()) {
			 mErrors.copyAllErrors(tLCPremDB.mErrors);
			 mErrors.addOneError(new CError("查询保费项目表失败！"));
			 return false;
			 }
			 tRef.transFields(aLPPremSchema, tLCPremDB.getSchema());
			 aLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			 aLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			 aLPPremSet.add(aLPPremSchema);
			 aLCPremSet.add(aLCPremSchema);// 只往C表中插数据
		}
		mMap.put(aLPPremSet,"UPDATE");
		mMap.put(aLCPremSet, "DELETE&INSERT");
		
		// 领取项表 lcget - lpget
		LPGetDB tLPGetDB = new LPGetDB();
		LPGetSchema tLPGetSchema = new LPGetSchema();
		LPGetSet tLPGetSet = new LPGetSet();
		tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPGetDB.setSchema(tLPGetSchema);
		tLPGetSet = tLPGetDB.query();
		if (tLPGetSet.size() < 1) {
			CError.buildErr(this, "查询保费项目表失败!");
			return false;
		}
		
		LCGetSet aLCGetSet = new LCGetSet();
		LPGetSet aLPGetSet = new LPGetSet();
		for (int i = 1; i <= tLPGetSet.size(); i++) {
			LCGetSchema aLCGetSchema = new LCGetSchema();
			LPGetSchema aLPGetSchema = new LPGetSchema();
			aLPGetSchema = tLPGetSet.get(i);
			tRef.transFields(aLCGetSchema, aLPGetSchema);
			aLCGetSchema.setModifyDate(PubFun.getCurrentDate());
			aLCGetSchema.setModifyTime(PubFun.getCurrentTime());
			 LCGetDB tLCGetDB = new LCGetDB();
			 tLCGetDB.setPolNo(aLPGetSchema.getPolNo());
			 tLCGetDB.setDutyCode(aLPGetSchema.getDutyCode());
			 tLCGetDB.setGetDutyCode(aLPGetSchema.getGetDutyCode());
			 if (!tLCGetDB.getInfo()) {
			 mErrors.copyAllErrors(tLCGetDB.mErrors);
			 mErrors.addOneError(new CError("查询保费项目表失败！"));
			 return false;
			 }
			 tRef.transFields(aLPGetSchema, tLCGetDB.getSchema());
			 aLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			 aLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			 aLPGetSet.add(aLPGetSchema);
			 aLCGetSet.add(aLCGetSchema);// 只往C表中插数据
		}
		mMap.put(aLPGetSet,"UPDATE");
		mMap.put(aLCGetSet, "DELETE&INSERT");
		
		//lccont
		LPContSchema mLPContSchema = new LPContSchema();
		LCContSchema mLCContSchema = new LCContSchema();
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			mErrors.addOneError("查询更改险种信息失败!");
			return false;
		}
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询原险种信息失败!");
			return false;
		}
		tRef.transFields(mLPContSchema, tLCContDB.getSchema());
		tRef.transFields(mLCContSchema, tLPContDB.getSchema());
		mLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		mLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		mMap.put(mLPContSchema, "UPDATE");
		mMap.put(mLCContSchema, "UPDATE");
		
		return true;
	}

	private boolean prepareOutData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "lee";
		tG.ManageCom = "86110000";

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemDB.setEdorAcceptNo("6120050927000088");
		tLPEdorItemSchema = tLPEdorItemDB.query().get(1);

		PEdorPTConfirmBL tPEdorPTConfirmBL = new PEdorPTConfirmBL();
		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tLPEdorItemSchema);

		if (!tPEdorPTConfirmBL.submitData(tVData, "")) {
			logger.debug(tPEdorPTConfirmBL.mErrors.getError(0).errorMessage);
		}
		VData tdd = new VData();
		tdd = tPEdorPTConfirmBL.getResult();
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(tdd, "")) {
			// @@错误处理

		}

	}

}
