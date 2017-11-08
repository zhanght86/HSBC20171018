package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.db.LCAccountDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccClassFeeDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccFeeDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LPAccountDB;
import com.sinosoft.lis.db.LPAddressDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPCustomerImpartDB;
import com.sinosoft.lis.db.LPCustomerImpartParamsDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.db.LPInsureAccClassFeeDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.db.LPInsureAccFeeDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPAccountSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vbl.LCPolBLSet;
import com.sinosoft.lis.vbl.LPPolBLSet;
import com.sinosoft.lis.vschema.LCAccountSet;
import com.sinosoft.lis.vschema.LCAddressSet;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPAccountSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全投保人更改确认
 * </p>
 * 
 * <p>
 * Description: 保全确认类
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
 * @author lizhuo
 * @version 1.0
 */

public class PEdorAEConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorAEConfirmBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;

	private LCPolBL mLCPolBL = new LCPolBL();
	private LCPolBLSet mLCPolBLSet = new LCPolBLSet();
	private LPPolBL mLPPolBL = new LPPolBL();
	private LPPolBLSet mLPPolBLSet = new LPPolBLSet();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private String tCurrentDate = PubFun.getCurrentDate();
	private String tCurrentTime = PubFun.getCurrentTime();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	
	private ValidateEdorData2 mValidateEdorData = null;
	
	private String mEdorNo = null;
	private String mEdorType = null;
	private String mContNo = null;

	public PEdorAEConfirmBL() {
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

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("--------AE Confirm----");

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			CError.buildErr(this, "查询数据失败!");
			return false;
		}
		// 数据准备操作（preparedata())
		if (!prepareData()) {
			return false;
		}

		this.setOperate("CONFIRM||AE");

		logger.debug("---" + mOperate);

		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
			mErrors.addOneError(new CError("查询批改项目信息失败！"));
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		
	    mEdorNo = mLPEdorItemSchema.getEdorNo();
	    mEdorType = mLPEdorItemSchema.getEdorType();
	    mContNo = mLPEdorItemSchema.getContNo();
	    mValidateEdorData = new ValidateEdorData2(mGlobalInput, mEdorNo,mEdorType, mContNo, "ContNo");
		return true;
	}

	private boolean prepareData() {
		
		//采用新的方式进行 CP 互换
	    String[] chgTables = {"LCGet","LCDuty"};
	    mValidateEdorData.changeData(chgTables);
	    mMap.add(mValidateEdorData.getMap());
	    //处理特约
	    mMap.add(BqNameFun.DealSpecData(mLPEdorItemSchema));

		LCPolSet aLCPolSet = new LCPolSet();
		LCAppntSet aLCAppntSet = new LCAppntSet();
		LCAddressSet aLCAddressSet = new LCAddressSet();
		LDPersonSet aLDPersonSet = new LDPersonSet();
		LCInsuredSet aLCInsuredSet = new LCInsuredSet();
		LCPremSet aLCPremSet = new LCPremSet();
		LCAccountSchema aLCAccountSchema = new LCAccountSchema();
		LCInsureAccSet aLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccClassSet aLCInsureAccClassSet = new LCInsureAccClassSet();
		LCInsureAccFeeSet aLCInsureAccFeeSet = new LCInsureAccFeeSet();
		LCInsureAccClassFeeSet aLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LCCustomerImpartSet aLCCustomerImpartSet = new LCCustomerImpartSet();
		LCCustomerImpartParamsSet aLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();

		LPPolSet aLPPolSet = new LPPolSet();
		LPAppntSet aLPAppntSet = new LPAppntSet();
		LPAddressSet aLPAddressSet = new LPAddressSet();
		LPPersonSet aLPPersonSet = new LPPersonSet();
		LPAccountSchema aLPAccountSchema = new LPAccountSchema();
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();
		LPPremSet aLPPremSet = new LPPremSet();
		LPInsureAccSet aLPInsureAccSet = new LPInsureAccSet();
		LPInsureAccClassSet aLPInsureAccClassSet = new LPInsureAccClassSet();
		LPInsureAccFeeSet aLPInsureAccFeeSet = new LPInsureAccFeeSet();
		LPInsureAccClassFeeSet aLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
		LPCustomerImpartSet aLPCustomerImpartSet = new LPCustomerImpartSet();
		LPCustomerImpartParamsSet aLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();

		Reflections tRef = new Reflections();

		// 得到投保人资料信息表
		LPPolSet tLPPolSet = new LPPolSet();
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSet = tLPPolDB.query();

		for (int i = 1; i <= tLPPolSet.size(); i++) {
			LPPolSchema tLPPolSchema = new LPPolSchema();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tRef.transFields(tLCPolSchema, tLPPolSet.get(i));

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
			aLPPolSet.add(tLPPolSchema);
			aLCPolSet.add(tLCPolSchema);
		}

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLPContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		LPContSchema tLPContSchema = new LPContSchema();
		LCContSchema tLCContSchema = new LCContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		tRef.transFields(tLCContSchema, tLPContDB.getSchema()); // 新
		tRef.transFields(tLPContSchema, tLCContDB.getSchema()); // 原
		tLCContSchema.setModifyDate(tCurrentDate);
		tLCContSchema.setModifyTime(tCurrentTime);
		tLPContSchema.setModifyDate(tCurrentDate);
		tLPContSchema.setModifyTime(tCurrentTime);
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		mMap.put(tLCContSchema, "UPDATE");
		mMap.put(tLPContSchema, "UPDATE");

		LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
		LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
		tLJSPayPersonDB.setContNo(tLCContSchema.getContNo());
		tLJSPayPersonSet = tLJSPayPersonDB.query();
		if (tLJSPayPersonSet != null && tLJSPayPersonSet.size() > 0) {
			for (int k = 1; k <= tLJSPayPersonSet.size(); k++) {
				tLJSPayPersonSet.get(k).setAppntNo(tLCContSchema.getAppntNo());
				tLJSPayPersonSet.get(k).setBankAccNo(
						tLCContSchema.getBankAccNo());
				tLJSPayPersonSet.get(k)
						.setBankCode(tLCContSchema.getBankCode());
			}
			mMap.put(tLJSPayPersonSet, "DELETE&INSERT");

			LJSPayDB tLJSPayDB = new LJSPayDB();
			LJSPaySet tLJSPaySet = new LJSPaySet();
			tLJSPayDB.setOtherNo(tLCContSchema.getContNo());
			tLJSPaySet = tLJSPayDB.query();
			if (tLJSPaySet != null && tLJSPaySet.size() > 0) {
				tLJSPaySet.get(1).setAppntNo(tLCContSchema.getAppntNo());
				tLJSPaySet.get(1).setAccName(tLCContSchema.getAccName());
				tLJSPaySet.get(1).setBankAccNo(tLCContSchema.getBankAccNo());
				tLJSPaySet.get(1).setBankCode(tLCContSchema.getBankCode());
			}
			mMap.put(tLJSPaySet.get(1), "DELETE&INSERT");
		}

		// 查询个单被保人表
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LPInsuredSchema aLPInsuredSchema = new LPInsuredSchema();
		LCInsuredSchema aLCInsuredSchema = new LCInsuredSchema();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();

		tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setSchema(tLPInsuredSchema);
		tLPInsuredSet = tLPInsuredDB.query();
		int m;
		m = tLPInsuredSet.size();
		for (int j = 1; j <= m; j++) {
			aLPInsuredSchema = tLPInsuredSet.get(j);
			tLCInsuredSchema = new LCInsuredSchema();
			tLPInsuredSchema = new LPInsuredSchema();

			LCInsuredDB aLCInsuredDB = new LCInsuredDB();
			aLCInsuredDB.setContNo(aLPInsuredSchema.getContNo());
			aLCInsuredDB.setInsuredNo(aLPInsuredSchema.getInsuredNo());
			if (!aLCInsuredDB.getInfo()) {
				mErrors.copyAllErrors(aLCInsuredDB.mErrors);
				mErrors.addOneError(new CError("查询个单被保人表失败！"));
				return false;
			}
			aLCInsuredSchema = aLCInsuredDB.getSchema();
			tRef.transFields(tLPInsuredSchema, aLCInsuredSchema);
			tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCInsuredSchema, aLPInsuredSchema);
			tLCInsuredSchema.setModifyDate(tCurrentDate);
			tLCInsuredSchema.setModifyTime(tCurrentTime);

			aLPInsuredSet.add(tLPInsuredSchema);
			tLPInsuredSet.add(tLCInsuredSchema);
			aLCInsuredSet.add(tLCInsuredSchema);
		}

		// 得到当前保单的投保人资料
		LPAppntDB tLPAppntDB = new LPAppntDB();
		tLPAppntDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLPAppntDB.getInfo()) {
			mErrors.addOneError("查询投保人信息失败!");
			return false;
		}
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCAppntDB.getInfo()) {
			mErrors.addOneError("查询投保人信息失败!");
			return false;
		}
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tRef.transFields(tLCAppntSchema, tLPAppntDB.getSchema()); // 新
		tRef.transFields(tLPAppntSchema, tLCAppntDB.getSchema()); // 原
		tLCAppntSchema.setModifyDate(tCurrentDate);
		tLCAppntSchema.setModifyTime(tCurrentTime);
		tLPAppntSchema.setModifyDate(tCurrentDate);
		tLPAppntSchema.setModifyTime(tCurrentTime);
		tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		mMap.put(tLCAppntSchema, "UPDATE");
		mMap.put(tLPAppntSchema, "UPDATE");

		// 得到当前保单的投保人地址资料
		LPAddressSet tLPAddressSet = new LPAddressSet();
		LPAddressSchema aLPAddressSchema = new LPAddressSchema();
		LPAddressDB tLPAddressDB = new LPAddressDB();
		tLPAddressDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAddressDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAddressDB.setCustomerNo(tLCAppntSchema.getAppntNo());
		tLPAddressSet = tLPAddressDB.query();
		if (tLPAddressSet.size() < 1) {
			mErrors.addOneError("查询地址信息失败!");
			return false;
		}
		for (int j = 1; j <= tLPAddressSet.size(); j++) {
			LCAddressSchema tLCAddressSchema = new LCAddressSchema();
			aLPAddressSchema = tLPAddressSet.get(j); // 新
			LCAddressDB aLCAddressDB = new LCAddressDB();
			aLCAddressDB.setCustomerNo(aLPAddressSchema.getCustomerNo());
			aLCAddressDB.setAddressNo(aLPAddressSchema.getAddressNo());
			if (!aLCAddressDB.getInfo()) {
				tRef.transFields(tLCAddressSchema, aLPAddressSchema);
				tLCAddressSchema.setModifyDate(tCurrentDate);
				tLCAddressSchema.setModifyTime(tCurrentTime);
				mMap.put(tLCAddressSchema, "DELETE&INSERT");
			}
			//mMap.put(aLPAddressSchema, "DELETE"); 保存为保全回退，此时第一条新增数据与P表内容是一致，所有保全新增类操作均要采取这样的方式处理
//			} else {
//				LPAddressSchema tLPAddressSchema = new LPAddressSchema();
//				//tRef.transFields(tLPAddressSchema, aLCAddressDB.getSchema());
//				tLPAddressSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//				tLPAddressSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//				// 转换成保单个人信息。
//				tRef.transFields(tLCAddressSchema, aLPAddressSchema);
//				tLCAddressSchema.setModifyDate(tCurrentDate);
//				tLCAddressSchema.setModifyTime(tCurrentTime);
//				mMap.put(tLCAddressSchema, "UPDATE");
//				mMap.put(aLPAddressSchema, "DELETE");
//			}
		}

		// 得到当前保单的投保人资料
		LPPersonDB tLPPersonDB = new LPPersonDB();
		tLPPersonDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPersonDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPPersonSet tLPPersonSet = new LPPersonSet();
		tLPPersonSet = tLPPersonDB.query();
		logger.debug("The LPPerson SIZE :" + tLPPersonSet.size());
		for (int j = 1; j <= tLPPersonSet.size(); j++) {
			LDPersonDB tLDPersonDB = new LDPersonDB();
			tLDPersonDB.setCustomerNo(tLPPersonSet.get(j).getCustomerNo());
			if (tLDPersonDB.getInfo()) {
				LDPersonSchema tLDPersonSchema = new LDPersonSchema();
				LPPersonSchema tLPPersonSchema = new LPPersonSchema();
				tRef.transFields(tLDPersonSchema, tLPPersonSet.get(j));
				tRef.transFields(tLPPersonSchema, tLDPersonDB.getSchema());
				tLDPersonSchema.setModifyDate(tCurrentDate);
				tLDPersonSchema.setModifyTime(tCurrentTime);
				tLPPersonSchema.setModifyDate(tCurrentDate);
				tLPPersonSchema.setModifyTime(tCurrentTime);
				tLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPersonSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mMap.put(tLDPersonSchema, "UPDATE");
				mMap.put(tLPPersonSchema, "UPDATE");
			} else {
				LDPersonSchema tLDPersonSchema = new LDPersonSchema();
				tRef.transFields(tLDPersonSchema, tLPPersonSet.get(j));
				tLDPersonSchema.setModifyDate(tCurrentDate);
				tLDPersonSchema.setModifyTime(tCurrentTime);
				mMap.put(tLDPersonSchema, "INSERT");
			}
		}

		// 帐户表
		LCAccountSet tLCAccountSet = new LCAccountSet();
		LPAccountSet tLPAccountSet = new LPAccountSet();
		LPAccountSchema tLPAccountSchema = new LPAccountSchema();
		tLPAccountSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAccountSchema.setEdorType(mLPEdorItemSchema.getEdorType());

		LPAccountDB tLPAccountDB = new LPAccountDB();
		tLPAccountDB.setSchema(tLPAccountSchema);
		tLPAccountSet = tLPAccountDB.query();
		if (tLPAccountSet.size() > 0) {
			for (int j = 1; j <= tLPAccountSet.size(); j++) {
				aLPAccountSchema = tLPAccountSet.get(j);
				LCAccountSchema tLCAccountSchema = new LCAccountSchema();
				tLPAccountSchema = new LPAccountSchema();

				LCAccountDB aLCAccountDB = new LCAccountDB();
				aLCAccountDB.setCustomerNo(aLPAccountSchema.getCustomerNo());
				aLCAccountDB.setBankAccNo(aLPAccountSchema.getBankAccNo());
				aLCAccountDB.setBankCode(aLPAccountSchema.getBankCode());
				aLCAccountDB.setAccName(aLPAccountSchema.getAccName());
				if (aLCAccountDB.getInfo()) {
					aLCAccountSchema = aLCAccountDB.getSchema();
					tRef.transFields(tLPAccountSchema, aLCAccountSchema);
					tLPAccountSchema.setEdorNo(aLPAccountSchema.getEdorNo());
					tLPAccountSchema
							.setEdorType(aLPAccountSchema.getEdorType());

					// 转换成保单个人信息。
					tRef.transFields(tLCAccountSchema, aLPAccountSchema);
					tLCAccountSchema.setModifyDate(tCurrentDate);
					tLCAccountSchema.setModifyTime(tCurrentTime);

					mMap.put(tLPAccountSchema, "UPDATE");
					mMap.put(tLCAccountSchema, "UPDATE");
				} else {
					tRef.transFields(tLCAccountSchema, aLPAccountSchema);
					logger.debug(tLCAccountSchema.encode());
					tLCAccountSchema.setMakeDate(tCurrentDate);
					tLCAccountSchema.setModifyTime(tCurrentTime);
					tLCAccountSchema.setModifyDate(tCurrentDate);
					tLCAccountSchema.setModifyTime(tCurrentTime);

					mMap.put(tLCAccountSchema, "INSERT");
					// mMap.put(aLPAccountSchema, "DELETE");
				}
			}
		}

		// LPPremSet tLPPremSet = new LPPremSet();
		// LPPremSchema aLPPremSchema = new LPPremSchema();
		// aLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		// aLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		// LPPremDB tLPPremDB = new LPPremDB();
		// tLPPremDB.setSchema(aLPPremSchema);
		// tLPPremSet = tLPPremDB.query();
		// if (tLPPremSet.size() > 0) {
		// for (int i = 1; i <= tLPPremSet.size(); i++) {
		// LCPremSchema tLCPremSchema = new LCPremSchema();
		// LPPremSchema tLPPremSchema = new LPPremSchema();
		// tRef.transFields(tLCPremSchema, tLPPremSet.get(i));
		// tLCPremSchema.setModifyDate(tCurrentDate);
		// tLCPremSchema.setModifyTime(tCurrentTime);
		// LCPremDB tLCPremDB = new LCPremDB();
		// tLCPremDB.setPolNo(tLCPremSchema.getPolNo());
		// tLCPremDB.setDutyCode(tLCPremSchema.getDutyCode());
		// tLCPremDB.setPayPlanCode(tLCPremSchema.getPayPlanCode());
		// if (!tLCPremDB.getInfo()) {
		// mErrors.copyAllErrors(tLCPremDB.mErrors);
		// mErrors.addOneError(new CError("查询保费项表失败！"));
		// return false;
		// }
		// tRef.transFields(tLPPremSchema, tLCPremDB.getSchema());
		// tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		// tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		// tLPPremSchema.setModifyDate(tCurrentDate);
		// tLPPremSchema.setModifyTime(tCurrentTime);
		// aLPPremSet.add(tLPPremSchema);
		// aLCPremSet.add(tLCPremSchema);
		// }
		// mMap.put(aLCPremSet, "UPDATE");
		// mMap.put(aLCPremSet, "UPDATE");
		// }

		//modify by jiaqiangli 2009-05-07
		Reflections tReflections = new Reflections();
		// 保费项表
		LPPremDB tLPPremDB = new LPPremDB();
		LPPremSet tLPPremSet = new LPPremSet();
		tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPremDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPPremSet = tLPPremDB.query();
		for (int j = 1; j <= tLPPremSet.size(); j++) {
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tRef.transFields(tLCPremSchema, tLPPremSet.get(j).getSchema());
			aLCPremSet.add(tLCPremSchema);
			
			LPPremSchema aLPPremSchema = new LPPremSchema();
			aLPPremSchema = tLPPremSet.get(j);
			
			LCPremDB tLCPremDB = new LCPremDB();
			 tLCPremDB.setPolNo(aLPPremSchema.getPolNo());
			 tLCPremDB.setDutyCode(aLPPremSchema.getDutyCode());
			 tLCPremDB.setPayPlanCode(aLPPremSchema.getPayPlanCode());
			 
			 boolean tExistsFlag = tLCPremDB.getInfo();
			if (tLCPremDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLCPremDB.mErrors);
				mErrors.addOneError(new CError("查询保费项目表失败！"));
				return false;
			}
			if (tExistsFlag == true) {
				tReflections.transFields(aLPPremSchema, tLCPremDB.getSchema());
				aLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				aLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				aLPPremSet.add(aLPPremSchema);
			}
		}
//		LCPremDB tLCPremDB = new LCPremDB();
//		tLCPremDB.setContNo(mLPEdorItemSchema.getContNo());
//		tLCPremSet = tLCPremDB.query();
//		for (int j = 1; j <= tLCPremSet.size(); j++) {
//			LPPremSchema tLPPremSchema = new LPPremSchema();
//			tRef.transFields(tLPPremSchema, tLCPremSet.get(j).getSchema());
//			tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//			tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//			aLPPremSet.add(tLPPremSchema);
//		}
//		mMap.put("delete from LPPrem where EdorNo = '"
//				+ mLPEdorItemSchema.getEdorNo() + "' and EdorType = '"
//				+ mLPEdorItemSchema.getEdorType() + "'", "DELETE");
//		mMap.put("delete from LCPrem where ContNo = '"
//				+ mLPEdorItemSchema.getContNo() + "'", "DELETE");
		mMap.put(aLCPremSet, "DELETE&INSERT");
		mMap.put(aLPPremSet, "DELETE&INSERT");

		LPCustomerImpartSet tLPCustomerImpartSet = new LPCustomerImpartSet();
		LPCustomerImpartSchema aLPCustomerImpartSchema = new LPCustomerImpartSchema();
		aLPCustomerImpartSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPCustomerImpartSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		LPCustomerImpartDB tLPCustomerImpartDB = new LPCustomerImpartDB();
		tLPCustomerImpartDB.setSchema(aLPCustomerImpartSchema);
		tLPCustomerImpartSet = tLPCustomerImpartDB.query();
		if (tLPCustomerImpartSet.size() > 0) {
			for (int i = 1; i <= tLPCustomerImpartSet.size(); i++) {
				LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
				LPCustomerImpartSchema tLPCustomerImpartSchema = new LPCustomerImpartSchema();
				tRef.transFields(tLCCustomerImpartSchema, tLPCustomerImpartSet
						.get(i));
				tLCCustomerImpartSchema.setModifyDate(tCurrentDate);
				tLCCustomerImpartSchema.setModifyTime(tCurrentTime);
				aLCCustomerImpartSet.add(tLCCustomerImpartSchema);
			}
			mMap.put(aLCCustomerImpartSet, "DELETE&INSERT");
		}

		LPCustomerImpartParamsSet tLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();
		LPCustomerImpartParamsSchema aLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
		aLPCustomerImpartParamsSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPCustomerImpartParamsSchema.setEdorType(mLPEdorItemSchema
				.getEdorType());
		LPCustomerImpartParamsDB tLPCustomerImpartParamsDB = new LPCustomerImpartParamsDB();
		tLPCustomerImpartParamsDB.setSchema(aLPCustomerImpartParamsSchema);
		tLPCustomerImpartParamsSet = tLPCustomerImpartParamsDB.query();
		if (tLPCustomerImpartParamsSet.size() > 0) {
			for (int i = 1; i <= tLPCustomerImpartParamsSet.size(); i++) {
				LCCustomerImpartParamsSchema tLCCustomerImpartParamsSchema = new LCCustomerImpartParamsSchema();
				LPCustomerImpartParamsSchema tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
				tRef.transFields(tLCCustomerImpartParamsSchema,
						tLPCustomerImpartParamsSet.get(i));
				tLCCustomerImpartParamsSchema.setModifyDate(PubFun
						.getCurrentDate());
				tLCCustomerImpartParamsSchema.setModifyTime(PubFun
						.getCurrentTime());
				aLCCustomerImpartParamsSet.add(tLCCustomerImpartParamsSchema);
			}
			mMap.put(aLCCustomerImpartParamsSet, "DELETE&INSERT");
		}

		// 查询保险帐户表
		LPInsureAccSet tLPInsureAccSet = new LPInsureAccSet();
		LPInsureAccSchema aLPInsureAccSchema = new LPInsureAccSchema();
		aLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPInsureAccSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB();
		tLPInsureAccDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsureAccDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsureAccSet = tLPInsureAccDB.query();
		if (tLPInsureAccSet.size() >= 1) {
			for (int i = 1; i <= tLPInsureAccSet.size(); i++) {
				LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
				LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
				tRef.transFields(tLCInsureAccSchema, tLPInsureAccSet.get(i));
				tLCInsureAccSchema.setModifyDate(tCurrentDate);
				tLCInsureAccSchema.setModifyTime(tCurrentTime);
				LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
				tLCInsureAccDB.setSchema(tLCInsureAccSchema);
				if (!tLCInsureAccDB.getInfo()) {
					mErrors.copyAllErrors(tLCInsureAccDB.mErrors);
					mErrors.addOneError(new CError("查询保险帐户表失败！"));
					return false;
				}
				tRef
						.transFields(tLPInsureAccSchema, tLCInsureAccDB
								.getSchema());
				tLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsureAccSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPInsureAccSchema.setModifyDate(tCurrentDate);
				tLPInsureAccSchema.setModifyTime(tCurrentTime);
				aLPInsureAccSet.add(tLPInsureAccSchema);
				aLCInsureAccSet.add(tLCInsureAccSchema);
			}
			mMap.put(aLCInsureAccSet, "UPDATE");
			mMap.put(aLPInsureAccSet, "UPDATE");
		}

		// 查询保险账户分类表
		LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();
		LPInsureAccClassSchema aLPInsureAccClassSchema = new LPInsureAccClassSchema();
		aLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();
		tLPInsureAccClassDB.setSchema(aLPInsureAccClassSchema);
		tLPInsureAccClassSet = tLPInsureAccClassDB.query();
		if (tLPInsureAccClassSet.size() >= 1) {
			for (int i = 1; i <= tLPInsureAccClassSet.size(); i++) {
				LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
				LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
				tRef.transFields(tLCInsureAccClassSchema, tLPInsureAccClassSet
						.get(i));
				tLCInsureAccClassSchema.setModifyDate(tCurrentDate);
				tLCInsureAccClassSchema.setModifyTime(tCurrentTime);
				LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
				tLCInsureAccClassDB.setSchema(tLCInsureAccClassSchema);
				if (!tLCInsureAccClassDB.getInfo()) {
					mErrors.copyAllErrors(tLCInsureAccClassDB.mErrors);
					mErrors.addOneError(new CError("查询保险账户分类表失败！"));
					return false;
				}
				tRef.transFields(tLPInsureAccClassSchema, tLCInsureAccClassDB
						.getSchema());
				tLPInsureAccClassSchema
						.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				tLPInsureAccClassSchema.setModifyDate(tCurrentDate);
				tLPInsureAccClassSchema.setModifyTime(tCurrentTime);
				aLPInsureAccClassSet.add(tLPInsureAccClassSchema);
				aLCInsureAccClassSet.add(tLCInsureAccClassSchema);
			}
			mMap.put(aLCInsureAccClassSet, "UPDATE");
			mMap.put(aLPInsureAccClassSet, "UPDATE");
		}

		// 查询保险账户管理费分类表
		LPInsureAccClassFeeSet tLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
		LPInsureAccClassFeeSchema aLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
		aLPInsureAccClassFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPInsureAccClassFeeSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		LPInsureAccClassFeeDB tLPInsureAccClassFeeDB = new LPInsureAccClassFeeDB();
		tLPInsureAccClassFeeDB.setSchema(aLPInsureAccClassFeeSchema);
		tLPInsureAccClassFeeSet = tLPInsureAccClassFeeDB.query();
		if (tLPInsureAccClassFeeSet.size() >= 1) {
			for (int i = 1; i <= tLPInsureAccClassFeeSet.size(); i++) {
				LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
				LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
				tRef.transFields(tLCInsureAccClassFeeSchema,
						tLPInsureAccClassFeeSet.get(i));
				tLCInsureAccClassFeeSchema.setModifyDate(PubFun
						.getCurrentDate());
				tLCInsureAccClassFeeSchema.setModifyTime(PubFun
						.getCurrentTime());
				LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
				tLCInsureAccClassFeeDB.setSchema(tLCInsureAccClassFeeSchema);
				if (!tLCInsureAccClassFeeDB.getInfo()) {
					mErrors.copyAllErrors(tLCInsureAccClassFeeDB.mErrors);
					mErrors.addOneError(new CError("查询保险账户管理费分类表失败！"));
					return false;
				}
				tRef.transFields(tLPInsureAccClassFeeSchema,
						tLCInsureAccClassFeeDB.getSchema());
				tLPInsureAccClassFeeSchema.setEdorNo(mLPEdorItemSchema
						.getEdorNo());
				tLPInsureAccClassFeeSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				tLPInsureAccClassFeeSchema.setModifyDate(PubFun
						.getCurrentDate());
				tLPInsureAccClassFeeSchema.setModifyTime(PubFun
						.getCurrentTime());
				aLPInsureAccClassFeeSet.add(tLPInsureAccClassFeeSchema);
				aLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
			}
			mMap.put(aLCInsureAccClassFeeSet, "UPDATE");
			mMap.put(aLPInsureAccClassFeeSet, "UPDATE");
		}

		// 查询保险帐户管理费表
		LPInsureAccFeeSet tLPInsureAccFeeSet = new LPInsureAccFeeSet();
		LPInsureAccFeeSchema aLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
		aLPInsureAccFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPInsureAccFeeSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		LPInsureAccFeeDB tLPInsureAccFeeDB = new LPInsureAccFeeDB();
		tLPInsureAccFeeDB.setSchema(aLPInsureAccFeeSchema);
		tLPInsureAccFeeSet = tLPInsureAccFeeDB.query();
		if (tLPInsureAccFeeSet.size() >= 1) {
			for (int i = 1; i <= tLPInsureAccFeeSet.size(); i++) {
				LCInsureAccFeeSchema tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
				LPInsureAccFeeSchema tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
				tRef.transFields(tLCInsureAccFeeSchema, tLPInsureAccFeeSet
						.get(i));
				tLCInsureAccFeeSchema.setModifyDate(tCurrentDate);
				tLCInsureAccFeeSchema.setModifyTime(tCurrentTime);
				LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
				tLCInsureAccFeeDB.setSchema(tLCInsureAccFeeSchema);
				if (!tLCInsureAccFeeDB.getInfo()) {
					mErrors.copyAllErrors(tLCInsureAccFeeDB.mErrors);
					mErrors.addOneError(new CError("查询保险帐户管理费表失败！"));
					return false;
				}
				tRef.transFields(tLPInsureAccFeeSchema, tLCInsureAccFeeDB
						.getSchema());
				tLPInsureAccFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsureAccFeeSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				tLPInsureAccFeeSchema.setModifyDate(tCurrentDate);
				tLPInsureAccFeeSchema.setModifyTime(tCurrentTime);
				aLPInsureAccFeeSet.add(tLPInsureAccFeeSchema);
				aLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
			}
			mMap.put(aLCInsureAccFeeSet, "UPDATE");
			mMap.put(aLPInsureAccFeeSet, "UPDATE");
		}

		// 得到当前LPEdorItem保单信息主表的状态，并更新状态为申请确认。
		mLPEdorItemSchema.setEdorState("0");
		mMap.put(mLPEdorItemSchema, "UPDATE");
		mMap.put(aLCPolSet, "UPDATE");
		mMap.put(aLPPolSet, "UPDATE");
		mMap.put(aLCInsuredSet, "UPDATE");
		mMap.put(aLPInsuredSet, "UPDATE");
		mResult.add(mMap);
		return true;
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		tGlobalInput.ComCode = "86";
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

		tLPEdorItemSchema.setEdorAcceptNo("86000000000781");
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setEdorNo("410000000000616");
		tLPEdorItemSchema.setEdorType("AE");
		tLPEdorItemSchema.setContNo("230110000000466");
		tLPEdorItemSchema.setInsuredNo("000000");
		tLPEdorItemSchema.setPolNo("000000");

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLPEdorItemSchema);

		PEdorAEConfirmBL tPEdorAEConfirmBL = new PEdorAEConfirmBL();
		if (tPEdorAEConfirmBL.submitData(tVData, "")) {
			logger.debug("OK!");
		}

	}
}
