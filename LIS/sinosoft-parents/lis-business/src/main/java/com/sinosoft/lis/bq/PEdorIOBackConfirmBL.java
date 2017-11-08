package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPCustomerImpartDB;
import com.sinosoft.lis.db.LPCustomerImpartParamsDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PEdorIOBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorIOBackConfirmBL.class);
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

	public PEdorIOBackConfirmBL() {
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
		logger.debug("=== 职业类别变更保全回退确认生效 ===");
	    Reflections mReflections = new Reflections();
	    
		String strCurrentDate = PubFun.getCurrentDate();
		String strCurrentTime = PubFun.getCurrentTime();
		
//		LPPolSchema aLPPolSchema = new LPPolSchema();
//		LCPolSchema aLCPolSchema = new LCPolSchema();
//
//		LPDutySchema aLPDutySchema = new LPDutySchema();
//		LCDutySchema aLCDutySchema = new LCDutySchema();
//		
//		LPPremSchema aLPPremSchema = new LPPremSchema();
//		LCPremSchema aLCPremSchema = new LCPremSchema();
//
//		LPGetSchema aLPGetSchema = new LPGetSchema();
//		LCGetSchema aLCGetSchema = new LCGetSchema();
//
//		LCPolSet aLCPolSet = new LCPolSet();
//		LPPolSet aLPPolSet = new LPPolSet();
//
//		LCDutySet aLCDutySet = new LCDutySet();
//		LPDutySet aLPDutySet = new LPDutySet();
//
//		LCPremSet aLCPremSet = new LCPremSet();
//		LPPremSet aLPPremSet = new LPPremSet();
//
//		LCGetSet aLCGetSet = new LCGetSet();
//		LPGetSet aLPGetSet = new LPGetSet();
//
//		LPPolSet tLPPolSet = new LPPolSet();
//		LPPolDB tLPPolDB = new LPPolDB();
//		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
//		tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
//		tLPPolSet = tLPPolDB.query();
//
//		for (int i = 1; i <= tLPPolSet.size(); i++) {
//			// Pol
//			String PolNo = tLPPolSet.get(i).getPolNo();
//			aLPPolSchema = new LPPolSchema();
//			aLCPolSchema = new LCPolSchema();
//			tRef.transFields(aLCPolSchema, tLPPolSet.get(i));
//			aLCPolSchema.setModifyDate(CurrDate);
//			aLCPolSchema.setModifyTime(CurrTime);
//
//			LCPolDB tLCPolDB = new LCPolDB();
//			tLCPolDB.setPolNo(PolNo);
//			if (!tLCPolDB.getInfo()) {
//				CError.buildErr(this, "查询险种表失败!险种号" + tLCPolDB.getPolNo());
//				return false;
//			}
//			tRef.transFields(aLPPolSchema, tLCPolDB.getSchema());
//			aLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//			aLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//			aLPPolSchema.setModifyDate(CurrDate);
//			aLPPolSchema.setModifyTime(CurrTime);
//
//			aLCPolSet.add(aLCPolSchema);
//			aLPPolSet.add(aLPPolSchema);
//
//			// Duty
//			LPDutyDB tLPDutyDB = new LPDutyDB();
//			tLPDutyDB.setPolNo(PolNo);
//			tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//			tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
//			LPDutySet tLPDutySet = new LPDutySet();
//			tLPDutySet = tLPDutyDB.query();
//			if (tLPDutySet == null || tLPDutySet.size() < 1) {
//				CError.buildErr(this, "查询险种责任信息失败!");
//				return false;
//			}
//			for (int j = 1; j <= tLPDutySet.size(); j++) {
//				aLCDutySchema = new LCDutySchema();
//				tRef.transFields(aLCDutySchema, tLPDutySet.get(j));
//				aLCDutySchema.setModifyDate(CurrDate);
//				aLCDutySchema.setModifyTime(CurrTime);
//				aLCDutySet.add(aLCDutySchema);
//			}
//
//			LCDutyDB tLCDutyDB = new LCDutyDB();
//			LCDutySet tLCDutySet = new LCDutySet();
//			tLCDutyDB.setPolNo(PolNo);
//			tLCDutySet = tLCDutyDB.query();
//			if (tLCDutySet == null || tLCDutySet.size() < 1) {
//				CError.buildErr(this, "查询险种责任信息失败!");
//				return false;
//			}
//			for (int j = 1; j <= tLCDutySet.size(); j++) {
//				aLPDutySchema = new LPDutySchema();
//				tRef.transFields(aLPDutySchema, tLCDutySet.get(j));
//				aLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//				aLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
//				aLPDutySchema.setModifyDate(CurrDate);
//				aLPDutySchema.setModifyTime(CurrTime);
//				aLPDutySet.add(aLPDutySchema);
//			}
//
//			// Prem
//			LPPremDB tLPPremDB = new LPPremDB();
//			tLPPremDB.setPolNo(PolNo);
//			tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//			tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
//			LPPremSet tLPPremSet = new LPPremSet();
//			tLPPremSet = tLPPremDB.query();
//			if (tLPPremSet == null || tLPPremSet.size() < 1) {
//				CError.buildErr(this, "查询险种保费信息失败!");
//				return false;
//			}
//			for (int j = 1; j <= tLPPremSet.size(); j++) {
//				aLCPremSchema = new LCPremSchema();
//				tRef.transFields(aLCPremSchema, tLPPremSet.get(j));
//				aLCPremSchema.setModifyDate(CurrDate);
//				aLCPremSchema.setModifyTime(CurrTime);
//				aLCPremSet.add(aLCPremSchema);
//			}
//			LCPremDB tLCPremDB = new LCPremDB();
//			LCPremSet tLCPremSet = new LCPremSet();
//			tLCPremDB.setPolNo(PolNo);
//			tLCPremSet = tLCPremDB.query();
//			if (tLCPremSet == null || tLCPremSet.size() < 1) {
//				CError.buildErr(this, "查询险种保费信息失败!");
//				return false;
//			}
//			for (int j = 1; j <= tLCPremSet.size(); j++) {
//				aLPPremSchema = new LPPremSchema();
//				tRef.transFields(aLPPremSchema, tLCPremSet.get(j));
//				aLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//				aLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//				aLPPremSchema.setModifyDate(CurrDate);
//				aLPPremSchema.setModifyTime(CurrTime);
//				aLPPremSet.add(aLPPremSchema);
//			}
//
//			// Get
//			LPGetDB tLPGetDB = new LPGetDB();
//			tLPGetDB.setPolNo(PolNo);
//			tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//			tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
//			LPGetSet tLPGetSet = new LPGetSet();
//			tLPGetSet = tLPGetDB.query();
//			if (tLPGetSet == null || tLPGetSet.size() < 1) {
//				CError.buildErr(this, "查询险种责任信息失败!");
//				return false;
//			}
//			for (int j = 1; j <= tLPGetSet.size(); j++) {
//				aLCGetSchema = new LCGetSchema();
//				tRef.transFields(aLCGetSchema, tLPGetSet.get(j));
//				aLCGetSchema.setModifyDate(CurrDate);
//				aLCGetSchema.setModifyTime(CurrTime);
//				aLCGetSet.add(aLCGetSchema);
//			}
//			LCGetDB tLCGetDB = new LCGetDB();
//			LCGetSet tLCGetSet = new LCGetSet();
//			tLCGetDB.setPolNo(PolNo);
//			tLCGetSet = tLCGetDB.query();
//			if (tLCGetSet == null || tLCGetSet.size() < 1) {
//				CError.buildErr(this, "查询险种责任信息失败!");
//				return false;
//			}
//			for (int j = 1; j <= tLCGetSet.size(); j++) {
//				aLPGetSchema = new LPGetSchema();
//				tRef.transFields(aLPGetSchema, tLCGetSet.get(j));
//				aLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//				aLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//				aLPGetSchema.setModifyDate(CurrDate);
//				aLPGetSchema.setModifyTime(CurrTime);
//				aLPGetSet.add(aLPGetSchema);
//			}
//		}
//
//		LPContSchema tLPContSchema = new LPContSchema();
//		LCContSchema tLCContSchema = new LCContSchema();
//
//		LPContDB tLPContDB = new LPContDB();
//		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
//		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
//		if (!tLPContDB.getInfo()) {
//			CError.buildErr(this, "查询原保单信息失败！");
//			return false;
//		}
//		tRef.transFields(tLCContSchema, tLPContDB.getSchema());
//		tLCContSchema.setModifyDate(CurrDate);
//		tLCContSchema.setModifyTime(CurrTime);
//		LCContDB tLCContDB = new LCContDB();
//		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
//		if (!tLCContDB.getInfo()) {
//			CError.buildErr(this, "查询保单信息失败！");
//			return false;
//		}
//		tRef.transFields(tLPContSchema, tLCContDB.getSchema());
//		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//		tLPContSchema.setModifyDate(CurrDate);
//		tLPContSchema.setModifyTime(CurrTime);
//
//		// Insured
//		LCInsuredSet aLCInsuredSet = new LCInsuredSet();
//		LPInsuredSet aLPInsuredSet = new LPInsuredSet();
//
//		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
//		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
//		tLPInsuredDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		tLPInsuredDB.setEdorType(mLPEdorItemSchema.getEdorType());
//		tLPInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
//		tLPInsuredSet = tLPInsuredDB.query();
//		for (int j = 1; j <= tLPInsuredSet.size(); j++) {
//			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
//			LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
//
//			tRef.transFields(tLCInsuredSchema, tLPInsuredSet.get(j));
//			tLCInsuredSchema.setModifyDate(CurrDate);
//			tLCInsuredSchema.setModifyTime(CurrTime);
//			aLCInsuredSet.add(tLCInsuredSchema);
//
//			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
//			tLCInsuredDB.setContNo(tLCInsuredSchema.getContNo());
//			tLCInsuredDB.setInsuredNo(tLCInsuredSchema.getInsuredNo());
//			if (!tLCInsuredDB.getInfo()) {
//				mErrors.copyAllErrors(tLCInsuredDB.mErrors);
//				mErrors.addOneError(new CError("查询被保人信息失败！"));
//				return false;
//			}
//			tRef.transFields(tLPInsuredSchema, tLCInsuredSchema.getSchema());
//			tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//			tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//			tLPInsuredSchema.setModifyDate(CurrDate);
//			tLPInsuredSchema.setModifyTime(CurrTime);
//			aLPInsuredSet.add(tLPInsuredSchema);
//		}
//
//		// Appnt
//		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
//		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
//
//		LPAppntDB tLPAppntDB = new LPAppntDB();
//		LPAppntSet tLPAppntSet = new LPAppntSet();
//		tLPAppntDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		tLPAppntDB.setEdorType(mLPEdorItemSchema.getEdorType());
//		tLPAppntDB.setContNo(mLPEdorItemSchema.getContNo());
//		tLPAppntSet = tLPAppntDB.query();
//		//当投保人与被保人为同一人时才有可能处理lcappnt lpappnt
//		if (tLPAppntSet.size() > 0) {
//			tRef.transFields(tLCAppntSchema, tLPAppntSet.get(1));
//			tLCAppntSchema.setModifyDate(CurrDate);
//			tLCAppntSchema.setModifyTime(CurrTime);
//			LCAppntDB tLCAppntDB = new LCAppntDB();
//			tLCAppntDB.setContNo(tLCAppntSchema.getContNo());
//			tLCAppntDB.setAppntNo(tLCAppntSchema.getAppntNo());
//			if (!tLCAppntDB.getInfo()) {
//				mErrors.copyAllErrors(tLCAppntDB.mErrors);
//				mErrors.addOneError(new CError("查询投保人信息失败！"));
//				return false;
//			}
//			tRef.transFields(tLPAppntSchema, tLCAppntDB.getSchema());
//			tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//			tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//			tLPAppntSchema.setModifyDate(CurrDate);
//			tLPAppntSchema.setModifyTime(CurrTime);
//			map.put(tLPAppntSchema, "DELETE&INSERT");
//			map.put(tLCAppntSchema, "DELETE&INSERT");
//		}

		// LDPerson
//		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
//		LPPersonSchema tLPPersonSchema = new LPPersonSchema();
//
//		LPPersonDB tLPPersonDB = new LPPersonDB();
//		LPPersonSet tLPPersonSet = new LPPersonSet();
//		tLPPersonDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		tLPPersonDB.setEdorType(mLPEdorItemSchema.getEdorType());
//		tLPPersonSet = tLPPersonDB.query();
//		if (tLPPersonSet.size() < 1) {
//			CError.buildErr(this, "查询客户信息失败!");
//			return false;
//		}
//		tRef.transFields(tLDPersonSchema, tLPPersonSet.get(1));
//		tLDPersonSchema.setModifyDate(CurrDate);
//		tLDPersonSchema.setModifyTime(CurrTime);
//
//		LDPersonDB tLDPersonDB = new LDPersonDB();
//		tLDPersonDB.setCustomerNo(tLDPersonSchema.getCustomerNo());
//		if (!tLDPersonDB.getInfo()) {
//			mErrors.copyAllErrors(tLDPersonDB.mErrors);
//			mErrors.addOneError(new CError("查询投保人信息失败！"));
//			return false;
//		}
//		tRef.transFields(tLPPersonSchema, tLDPersonDB.getSchema());
//		tLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//		tLPPersonSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//		tLPPersonSchema.setModifyDate(CurrDate);
//		tLPPersonSchema.setModifyTime(CurrTime);

//		map.put(aLCPolSet, "DELETE&INSERT");
//		map.put(aLPPolSet, "DELETE&INSERT");
//		map.put(aLPPremSet, "DELETE&INSERT");
//		map.put(aLCPremSet, "DELETE&INSERT");
//		map.put(aLCDutySet, "DELETE&INSERT");
//		map.put(aLPDutySet, "DELETE&INSERT");
//		map.put(aLCGetSet, "DELETE&INSERT");
//		map.put(aLPGetSet, "DELETE&INSERT");
//		
//		map.put(tLCContSchema, "DELETE&INSERT");
//		map.put(tLPContSchema, "DELETE&INSERT");
		
//		map.put(tLDPersonSchema, "DELETE&INSERT");
//		map.put(tLPPersonSchema, "DELETE&INSERT");
		
//		map.put(aLPInsuredSet, "DELETE&INSERT");
//		map.put(aLCInsuredSet, "DELETE&INSERT");
		
		LPCustomerImpartDB tLPCustomerImpartDB = null;
		LPCustomerImpartSet tLPCustomerImpartSet = new LPCustomerImpartSet();
		LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
		LCCustomerImpartSchema tLCCustomerImpartSchema = null;
		LPCustomerImpartParamsDB tLPCustomerImpartParamsDB = null;
		LPCustomerImpartParamsSet tLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();
		LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
		LCCustomerImpartParamsSchema tLCCustomerImpartParamsSchema = null;

		// 交换客户告知表数据
		tLPCustomerImpartDB = new LPCustomerImpartDB();
		tLPCustomerImpartDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPCustomerImpartDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPCustomerImpartSet = tLPCustomerImpartDB.query();
		if (tLPCustomerImpartSet != null || tLPCustomerImpartSet.size() > 0) {
			for (int i = 1; i <= tLPCustomerImpartSet.size(); i++) {
				tLCCustomerImpartSchema = new LCCustomerImpartSchema();
				mReflections.transFields(tLCCustomerImpartSchema,
						tLPCustomerImpartSet.get(i));
				tLCCustomerImpartSchema.setOperator(this.mGlobalInput.Operator);
				tLCCustomerImpartSchema.setModifyDate(strCurrentDate);
				tLCCustomerImpartSchema.setModifyTime(strCurrentTime);
				tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
			}
		}

		// 交换客户告知参数表数据
		tLPCustomerImpartParamsDB = new LPCustomerImpartParamsDB();
		tLPCustomerImpartParamsDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPCustomerImpartParamsDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPCustomerImpartParamsSet = tLPCustomerImpartParamsDB.query();
		if (tLPCustomerImpartParamsSet != null
				&& tLPCustomerImpartParamsSet.size() > 0) {
			for (int i = 1; i <= tLPCustomerImpartParamsSet.size(); i++) {
				tLCCustomerImpartParamsSchema = new LCCustomerImpartParamsSchema();
				mReflections.transFields(tLCCustomerImpartParamsSchema,
						tLPCustomerImpartParamsSet.get(i));
				tLCCustomerImpartParamsSchema
						.setOperator(this.mGlobalInput.Operator);
				tLCCustomerImpartParamsSchema.setModifyDate(strCurrentDate);
				tLCCustomerImpartParamsSchema.setModifyTime(strCurrentTime);
				tLCCustomerImpartParamsSet.add(tLCCustomerImpartParamsSchema);
			}

		}
		map.put(tLCCustomerImpartSet, "DELETE");
		map.put(tLCCustomerImpartParamsSet, "DELETE");
		ValidateEdorData2 mValidateEdorData = new ValidateEdorData2(mGlobalInput, mLPEdorItemSchema.getEdorNo(),mLPEdorItemSchema.getEdorType(), mLPEdorItemSchema.getContNo(), "ContNo");
		//采用新的方式进行保全数据回退
	    String[] chgTables = {"LCCont","LCPol","LCDuty","LCGet","LCAppnt","LCInsured","LCCSpec"};
	    mValidateEdorData.backConfirmData(chgTables);
	    map.add(mValidateEdorData.getMap());
	    //处理保费表
	    map.add(BqNameFun.DealPrem4BackConfirm(mLPEdorItemSchema));
		
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

//	public static void main(String[] args) {
//		PEdorIOBackConfirmBL tPEdorIOBackConfirmBL = new PEdorIOBackConfirmBL();
//	}
}
