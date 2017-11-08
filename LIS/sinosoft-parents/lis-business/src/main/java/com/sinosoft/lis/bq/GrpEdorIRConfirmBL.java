package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPAddressDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPCustomerImpartDB;
import com.sinosoft.lis.db.LPCustomerImpartParamsDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
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
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团体保全业务处理
 * </p>
 * 
 * <p>
 * Description: 换人保全确认业务处理类
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
 * @CreatDate 2005-11-06
 * @version 1.0
 */
public class GrpEdorIRConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorIRConfirmBL.class);
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
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LPEdorItemSchema mLPEdorItemSchema;
	private GlobalInput mGlobalInput = new GlobalInput();

	public GrpEdorIRConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-------Grp IR Confirm----");

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
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
		this.setOperate("CONFIRM||IR");
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mLPGrpEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		mLPEdorItemSet = tLPEdorItemDB.query();
		if (mLPEdorItemSet == null || mLPEdorItemSet.size() < 1) {
			CError.buildErr(this, "查询批改项目信息失败!");
			return false;
		}
		return true;
	}

	private boolean dealData() {
		for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
			mLPEdorItemSchema = new LPEdorItemSchema();
			mLPEdorItemSchema.setSchema(mLPEdorItemSet.get(i));
			this.dealLPEdorItemInfo();
		}
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
		tLPEdorMainDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPEdorMainDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorMainSet = tLPEdorMainDB.query();
		for (int i = 1; i <= tLPEdorMainSet.size(); i++) {
			tLPEdorMainSet.get(i).setEdorState("0");
			tLPEdorMainSet.get(i).setOperator(mGlobalInput.Operator);
			tLPEdorMainSet.get(i).setModifyDate(CurrDate);
			tLPEdorMainSet.get(i).setModifyTime(CurrTime);
		}
		mMap.put(tLPEdorMainSet, "UPDATE");

		mLPGrpEdorItemSchema.setEdorState("0");
		mLPGrpEdorItemSchema.setOperator(mGlobalInput.Operator);
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

	private boolean dealLPEdorItemInfo() {
		Reflections tRef = new Reflections();

		LCContSchema tLCContSchema = new LCContSchema();
		LPContSchema tLPContSchema = new LPContSchema();

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			mErrors.addOneError("查询批改保单信息失败!");
			return false;
		}
		tRef.transFields(tLCContSchema, tLPContDB.getSchema());
		tLCContSchema.setModifyDate(CurrDate);
		tLCContSchema.setModifyTime(CurrTime);
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		tRef.transFields(tLPContSchema, tLCContDB.getSchema());
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setModifyDate(CurrDate);
		tLPContSchema.setModifyTime(CurrTime);
		mMap.put(tLCContSchema, "UPDATE");
		mMap.put(tLPContSchema, "UPDATE");

		LCPolSet aLCPolSet = new LCPolSet();
		LPPolSet aLPPolSet = new LPPolSet();
		LCDutySet aLCDutySet = new LCDutySet();
		LPDutySet aLPDutySet = new LPDutySet();
		LCPremSet aLCPremSet = new LCPremSet();
		LPPremSet aLPPremSet = new LPPremSet();
		LCGetSet aLCGetSet = new LCGetSet();
		LPGetSet aLPGetSet = new LPGetSet();

		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet.size() < 1) {
			CError.buildErr(this, "查询险种表出错!");
			return false;
		}
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tRef.transFields(tLCPolSchema, tLPPolSet.get(i));
			tLCPolSchema.setModifyDate(CurrDate);
			tLCPolSchema.setModifyTime(CurrTime);
			aLCPolSet.add(tLCPolSchema);

			LCPolDB tLCPolDB = new LCPolDB();
			LPPolSchema tLPPolSchema = new LPPolSchema();
			tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.copyAllErrors(tLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询险种保单表失败！"));
				return false;
			}
			tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolSchema.setModifyDate(CurrDate);
			tLPPolSchema.setModifyTime(CurrTime);
			aLPPolSet.add(tLPPolSchema);

			LPDutyDB tLPDutyDB = new LPDutyDB();
			LPDutySet tLPDutySet = new LPDutySet();
			tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPDutyDB.setPolNo(tLPPolSchema.getPolNo());
			tLPDutySet = tLPDutyDB.query();
			for (int j = 1; j <= tLPDutySet.size(); j++) {
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tRef.transFields(tLCDutySchema, tLPDutySet.get(j));
				tLCDutySchema.setModifyDate(CurrDate);
				tLCDutySchema.setModifyTime(CurrTime);
				aLCDutySet.add(tLCDutySchema);
				tLCDutySchema = null;
			}
			if (tLPDutySet != null && tLPDutySet.size() > 0) {
				LCDutyDB tLCDutyDB = new LCDutyDB();
				LCDutySet tLCDutySet = new LCDutySet();
				tLCDutyDB.setPolNo(tLPPolSchema.getPolNo());
				tLCDutySet = tLCDutyDB.query();
				if (tLCDutySet == null) {
					CError.buildErr(this, "查询责任信息失败!");
					return false;
				}
				for (int j = 1; j <= tLCDutySet.size(); j++) {
					LPDutySchema tLPDutySchema = new LPDutySchema();
					tRef.transFields(tLPDutySchema, tLCDutySet.get(j));
					tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPDutySchema.setModifyDate(CurrDate);
					tLPDutySchema.setModifyTime(CurrTime);
					aLPDutySet.add(tLPDutySchema);
					tLPDutySchema = null;
				}
			}

			// Prem
			LPPremDB tLPPremDB = new LPPremDB();
			LPPremSet tLPPremSet = new LPPremSet();
			tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPremDB.setPolNo(tLPPolSchema.getPolNo());
			tLPPremSet = tLPPremDB.query();
			for (int j = 1; j <= tLPPremSet.size(); j++) {
				LCPremSchema tLCPremSchema = new LCPremSchema();
				tRef.transFields(tLCPremSchema, tLPPremSet.get(j));
				tLCPremSchema.setModifyDate(CurrDate);
				tLCPremSchema.setModifyTime(CurrTime);
				aLCPremSet.add(tLCPremSchema);
				tLCPremSchema = null;
			}
			if (tLPPremSet != null && tLPPremSet.size() > 0) {
				LCPremDB tLCPremDB = new LCPremDB();
				tLCPremDB.setPolNo(tLPPolSchema.getPolNo());
				LCPremSet tLCPremSet = tLCPremDB.query();
				if (tLCPremSet == null) {
					CError.buildErr(this, "查询保费信息失败!");
					return false;
				}
				for (int j = 1; j <= tLCPremSet.size(); j++) {
					LPPremSchema tLPPremSchema = new LPPremSchema();
					tRef.transFields(tLPPremSchema, tLCPremSet.get(j));
					tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPPremSchema.setModifyDate(CurrDate);
					tLPPremSchema.setModifyTime(CurrTime);
					aLPPremSet.add(tLPPremSchema);
					tLPPremSchema = null;
				}
				mMap.put(tLPPremSet, "DELETE");
				mMap.put(tLCPremSet, "DELETE");
			}

			// 得到给付项信息
			LPGetDB tLPGetDB = new LPGetDB();
			LPGetSet tLPGetSet = new LPGetSet();
			tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPGetDB.setPolNo(tLPPolSchema.getPolNo());
			tLPGetSet = tLPGetDB.query();
			for (int j = 1; j <= tLPGetSet.size(); j++) {
				LCGetSchema tLCGetSchema = new LCGetSchema();
				tRef.transFields(tLCGetSchema, tLPGetSet.get(j).getSchema());
				tLCGetSchema.setModifyDate(CurrDate);
				tLCGetSchema.setModifyTime(CurrTime);
				aLCGetSet.add(tLCGetSchema);
				tLCGetSchema = null;
			}

			// 原给付项信息
			if (tLPGetSet != null && tLPGetSet.size() > 0) {
				LCGetDB tLCGetDB = new LCGetDB();
				LCGetSet tLCGetSet = new LCGetSet();
				tLCGetDB.setPolNo(tLPPolSchema.getPolNo());
				tLCGetSet = tLCGetDB.query();
				if (tLCGetSet == null) {
					CError.buildErr(this, "查询给付项信息失败!");
					return false;
				}
				for (int j = 1; j <= tLCGetSet.size(); j++) {
					LPGetSchema tLPGetSchema = new LPGetSchema();
					tRef
							.transFields(tLPGetSchema, tLCGetSet.get(j)
									.getSchema());
					tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPGetSchema.setModifyDate(CurrDate);
					tLPGetSchema.setModifyTime(CurrTime);
					aLPGetSet.add(tLPGetSchema);
				}
			}
		}
		mMap.put(aLCPolSet, "UPDATE");
		mMap.put(aLPPolSet, "UPDATE");
		mMap.put(aLCDutySet, "DELETE&INSERT");
		mMap.put(aLPDutySet, "DELETE&INSERT");
		mMap.put(aLCPremSet, "INSERT");
		mMap.put(aLPPremSet, "INSERT");
		mMap.put(aLCGetSet, "DELETE&INSERT");
		mMap.put(aLPGetSet, "DELETE&INSERT");

		LCInsuredSet aLCInsuredSet = new LCInsuredSet();
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();

		String CustomerNo = ""; // 替换被保人的客户号
		String AddressNo = "";
		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		tLPInsuredDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPInsuredSet = tLPInsuredDB.query();
		if (tLPInsuredSet.size() < 1) {
			CError.buildErr(this, "查询替换被保人信息失败!保单号："
					+ mLPEdorItemSchema.getContNo());
			return false;
		}

		CustomerNo = tLPInsuredSet.get(1).getInsuredNo();
		AddressNo = tLPInsuredSet.get(1).getAddressNo();

		LCInsuredSchema tLCInsuredSchema;
		for (int i = 1; i <= tLPInsuredSet.size(); i++) {
			tLCInsuredSchema = new LCInsuredSchema();
			tRef.transFields(tLCInsuredSchema, tLPInsuredSet.get(i));
			tLCInsuredSchema.setOperator(mGlobalInput.Operator);
			tLCInsuredSchema.setModifyDate(CurrDate);
			tLCInsuredSchema.setModifyTime(CurrTime);
			aLCInsuredSet.add(tLCInsuredSchema);
		}
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCInsuredDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		if (!tLCInsuredDB.getInfo()) {
			mErrors.addOneError("查询被保人信息失败!被保人号："
					+ mLPEdorItemSchema.getInsuredNo());
			return false;
		}
		tRef.transFields(tLPInsuredSchema, tLCInsuredDB.getSchema());
		tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredSchema.setOperator(mGlobalInput.Operator);
		tLPInsuredSchema.setModifyDate(CurrDate);
		tLPInsuredSchema.setModifyTime(CurrTime);
		aLPInsuredSet.add(tLPInsuredSchema);

		mMap.put(tLPInsuredSet, "DELETE");
		mMap.put(tLCInsuredDB.getSchema(), "DELETE");
		mMap.put(aLCInsuredSet, "DELETE&INSERT");
		mMap.put(aLPInsuredSet, "DELETE&INSERT");

		// 处理客户资料信息
		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		LPPersonSchema tLPPersonSchema = new LPPersonSchema();

		LPPersonDB tLPPersonDB = new LPPersonDB();
		tLPPersonDB.setCustomerNo(CustomerNo);
		tLPPersonDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPersonDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPPersonDB.getInfo()) {
			CError.buildErr(this, "查询新被保人信息失败!被保人号：" + CustomerNo);
			return false;
		}
		tRef.transFields(tLDPersonSchema, tLPPersonDB.getSchema());
		tLDPersonSchema.setOperator(mGlobalInput.Operator);
		tLDPersonSchema.setModifyDate(CurrDate);
		tLDPersonSchema.setModifyTime(CurrTime);
		mMap.put(tLDPersonSchema, "DELETE&INSERT");
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(CustomerNo);
		if (tLDPersonDB.getInfo()) {
			tRef.transFields(tLPPersonSchema, tLDPersonDB.getSchema());
			tLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPersonSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPersonSchema.setOperator(mGlobalInput.Operator);
			tLPPersonSchema.setModifyDate(CurrDate);
			tLPPersonSchema.setModifyTime(CurrTime);
			mMap.put(tLPPersonSchema, "DELETE&INSERT");
		}

		// 处理地址信息
		LCAddressSchema tLCAddressSchema = new LCAddressSchema();
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();

		LPAddressDB tLPAddressDB = new LPAddressDB();
		tLPAddressDB.setCustomerNo(CustomerNo);
		tLPAddressDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAddressDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAddressDB.setAddressNo(AddressNo);
		if (!tLPAddressDB.getInfo()) {
			CError.buildErr(this, "查询新被保人地址信息失败!被保人号：" + CustomerNo);
			return false;
		}
		tRef.transFields(tLCAddressSchema, tLPAddressDB.getSchema());
		tLCAddressSchema.setOperator(mGlobalInput.Operator);
		tLCAddressSchema.setModifyDate(CurrDate);
		tLCAddressSchema.setModifyTime(CurrTime);
		mMap.put(tLCAddressSchema, "DELETE&INSERT");

		LCAddressDB tLCAddressDB = new LCAddressDB();
		tLCAddressDB.setCustomerNo(CustomerNo);
		tLCAddressDB.setAddressNo(AddressNo);
		if (tLCAddressDB.getInfo()) {
			tRef.transFields(tLPAddressSchema, tLCAddressDB.getSchema());
			tLPAddressSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPAddressSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPAddressSchema.setOperator(mGlobalInput.Operator);
			tLPAddressSchema.setModifyDate(CurrDate);
			tLPAddressSchema.setModifyTime(CurrTime);
			mMap.put(tLPAddressSchema, "DELETE&INSERT");
		}

		// 处理健康告知等信息
		LCCustomerImpartSet aLCCustomerImpartSet = new LCCustomerImpartSet();
		LCCustomerImpartParamsSet aLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();

		LPCustomerImpartDB tLPCustomerImpartDB = new LPCustomerImpartDB();
		LPCustomerImpartSet tLPCustomerImpartSet = new LPCustomerImpartSet();
		tLPCustomerImpartDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPCustomerImpartDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPCustomerImpartDB.setCustomerNo(CustomerNo);
		tLPCustomerImpartSet = tLPCustomerImpartDB.query();
		if (tLPCustomerImpartSet.size() > 0) {
			for (int i = 1; i <= tLPCustomerImpartSet.size(); i++) {
				LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
				tRef.transFields(tLCCustomerImpartSchema, tLPCustomerImpartSet
						.get(i));
				tLCCustomerImpartSchema.setOperator(mGlobalInput.Operator);
				tLCCustomerImpartSchema.setModifyDate(PubFun.getCurrentDate());
				tLCCustomerImpartSchema.setModifyTime(PubFun.getCurrentTime());
				aLCCustomerImpartSet.add(tLCCustomerImpartSchema);
			}
			mMap.put(aLCCustomerImpartSet, "DELETE&INSERT");
		}

		LPCustomerImpartParamsDB tLPCustomerImpartParamsDB = new LPCustomerImpartParamsDB();
		LPCustomerImpartParamsSet tLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();
		tLPCustomerImpartParamsDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPCustomerImpartParamsDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPCustomerImpartParamsDB.setCustomerNo(CustomerNo);
		tLPCustomerImpartParamsSet = tLPCustomerImpartParamsDB.query();
		if (tLPCustomerImpartParamsSet.size() > 0) {
			for (int i = 1; i <= tLPCustomerImpartParamsSet.size(); i++) {
				LCCustomerImpartParamsSchema tLCCustomerImpartParamsSchema = new LCCustomerImpartParamsSchema();
				tRef.transFields(tLCCustomerImpartParamsSchema,
						tLPCustomerImpartParamsSet.get(i));
				tLCCustomerImpartParamsSchema
						.setOperator(mGlobalInput.Operator);
				tLCCustomerImpartParamsSchema.setModifyDate(PubFun
						.getCurrentDate());
				tLCCustomerImpartParamsSchema.setModifyTime(PubFun
						.getCurrentTime());
				aLCCustomerImpartParamsSet.add(tLCCustomerImpartParamsSchema);
			}
			mMap.put(aLCCustomerImpartParamsSet, "DELETE&INSERT");
		}

		mLPEdorItemSchema.setEdorState("0");
		mLPEdorItemSchema.setModifyDate(CurrDate);
		mLPEdorItemSchema.setModifyTime(CurrTime);
		mMap.put(mLPEdorItemSchema, "UPDATE");

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

	public static void main(String[] args) {
		GrpEdorIRConfirmBL grpedorirconfirmbl = new GrpEdorIRConfirmBL();
	}
}
