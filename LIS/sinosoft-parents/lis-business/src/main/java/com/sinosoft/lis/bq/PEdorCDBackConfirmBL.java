package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPAddressDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
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
 * Description: 客户地址信息变更保全回退处理
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
public class PEdorCDBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorCDBackConfirmBL.class);
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

	private Reflections mRef = new Reflections();

	private LPContSet mLPContSet = new LPContSet();
	private LCContSet mLCContSet = new LCContSet();
	private LPPolSet mLPPolSet = new LPPolSet();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LCDutySet mLCDutySet = new LCDutySet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LCPremSet mLCPremSet = new LCPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LCGetSet mLCGetSet = new LCGetSet();

	public PEdorCDBackConfirmBL() {
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
		logger.debug("=== 客户地址信息变更回退确认生效 ===");
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();

		String AddressNo = "";// 原地址号
		LPAppntDB tLPAppntDB = new LPAppntDB();
		tLPAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPAppntDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (tLPAppntDB.getInfo()) {
			mRef.transFields(tLCAppntSchema, tLPAppntDB.getSchema());
			tLCAppntSchema.setModifyDate(CurrDate);
			tLCAppntSchema.setModifyTime(CurrTime);
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());
			if (!tLCAppntDB.getInfo()) {
				CError.buildErr(this, "查询投保人信息失败!保单号："
						+ mLPEdorItemSchema.getContNo());
				return false;
			}
			mRef.transFields(tLPAppntSchema, tLCAppntDB.getSchema());
			tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPAppntSchema.setModifyDate(CurrDate);
			tLPAppntSchema.setModifyTime(CurrTime);
			AddressNo = tLCAppntSchema.getAddressNo();
			map.put(tLCAppntSchema, "DELETE&INSERT");
			map.put(tLPAppntSchema, "DELETE&INSERT");
		}

		LCInsuredSet aLCInsuredSet = new LCInsuredSet();
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();

		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		tLPInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPInsuredDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredSet = tLPInsuredDB.query();
		if (tLPInsuredSet.size() > 0) {
			LCInsuredSchema tLCInsuredSchema;
			LPInsuredSchema tLPInsuredSchema;
			for (int i = 1; i <= tLPInsuredSet.size(); i++) {
				tLCInsuredSchema = new LCInsuredSchema();
				mRef.transFields(tLCInsuredSchema, tLPInsuredSet.get(i));
				tLCInsuredSchema.setModifyDate(CurrDate);
				tLCInsuredSchema.setModifyTime(CurrTime);
				aLCInsuredSet.add(tLCInsuredSchema);
				LCInsuredDB tLCInsuredDB = new LCInsuredDB();
				tLCInsuredDB.setContNo(tLCInsuredSchema.getContNo());
				tLCInsuredDB.setInsuredNo(tLCInsuredSchema.getInsuredNo());
				if (!tLCInsuredDB.getInfo()) {
					CError.buildErr(this, "查询被保人信息失败!被保人号:"
							+ tLCInsuredSchema.getInsuredNo() + ",保单号:"
							+ tLCInsuredSchema.getContNo());
					return false;
				}
				tLPInsuredSchema = new LPInsuredSchema();
				mRef.transFields(tLPInsuredSchema, tLCInsuredDB.getSchema());
				tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPInsuredSchema.setModifyDate(CurrDate);
				tLPInsuredSchema.setModifyTime(CurrTime);
				aLPInsuredSet.add(tLPInsuredSchema);
			}
			map.put(aLCInsuredSet, "DELETE&INSERT");
			map.put(aLPInsuredSet, "DELETE&INSERT");
			if (AddressNo.equals("")) {
				AddressNo = aLCInsuredSet.get(1).getAddressNo();
			}
		}

		// 处理地址表信息，更新LPEdorItemSchema中的StandByFlag3判断是从C表取数，还是从P表取数
		// 即，上次保全受理是否有一个LPEdoritemSchema做过回退,
		// 因为C表中只有一条记录，不能在同一个保全受理中回退交换多次。
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setStandbyFlag3("Yes");
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet.size() < 1) {
			LPAddressDB tLPAddressDB = new LPAddressDB();
			LPAddressSet tLPAddressSet = new LPAddressSet();
			tLPAddressDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPAddressDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPAddressSet = tLPAddressDB.query();
			if (tLPAddressSet.size() < 1) {
				CError.buildErr(this, "查询原客户地址信息失败!");
				return false;
			}
			if (StrTool.compareString(AddressNo, String.valueOf(tLPAddressSet
					.get(1).getAddressNo()))) {
				LCAddressSchema tLCAddressSchema = new LCAddressSchema();
				LPAddressSchema tLPAddressSchema = new LPAddressSchema();
				mRef.transFields(tLCAddressSchema, tLPAddressSet.get(1));
				tLCAddressSchema.setModifyDate(CurrDate);
				tLCAddressSchema.setModifyTime(CurrTime);
				LCAddressDB tLCAddressDB = new LCAddressDB();
				tLCAddressDB
						.setCustomerNo(tLPAddressSet.get(1).getCustomerNo());
				tLCAddressDB.setAddressNo(tLPAddressSet.get(1).getAddressNo());
				if (!tLCAddressDB.getInfo()) {
					CError.buildErr(this, "查询客户地址信息失败!");
					return false;
				}
				mRef.transFields(tLPAddressSchema, tLCAddressDB.getSchema());
				tLPAddressSchema.setEdorNo(mLPEdorItemSchema.getEdorType());
				tLPAddressSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPAddressSchema.setModifyDate(CurrDate);
				tLPAddressSchema.setModifyTime(CurrTime);
				map.put(tLCAddressSchema, "DELETE&INSERT");
				map.put(tLPAddressSchema, "DELETE&INSERT");
			} else {
				String delAddress = "delete from lcaddress where customerno = '"
						+ "?customerno?"
						+ "' and addressno = '"
						+ "?addressno?" + "'";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(delAddress);
				sqlbv.put("customerno", tLPAddressSet.get(1).getCustomerNo());
				sqlbv.put("addressno", tLPAddressSet.get(1).getAddressNo());
				map.put(sqlbv, "DELETE");
			}
			mLPEdorItemSchema.setStandbyFlag3("Yes");
			mLPEdorItemSchema.setEdorState("b");
			map.put(mLPEdorItemSchema, "UPDATE");
		} else {
			LPAddressDB tLPAddressDB = new LPAddressDB();
			LPAddressSet tLPAddressSet = new LPAddressSet();
			tLPAddressDB.setEdorNo(tLPEdorItemSet.get(1).getEdorNo());
			tLPAddressDB.setEdorType(tLPEdorItemSet.get(1).getEdorType());
			tLPAddressSet = tLPAddressDB.query();
			if (tLPAddressSet.size() < 1) {
				CError.buildErr(this, "查询原客户地址信息失败!");
				return false;
			}
			tLPAddressSet.get(1).setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPAddressSet.get(1).setEdorType(mLPEdorItemSchema.getEdorType());
			tLPAddressSet.get(1).setModifyDate(CurrDate);
			tLPAddressSet.get(1).setModifyTime(CurrTime);
			map.put(tLPAddressSet.get(1), "DELETE&INSERT");
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

	/**
	 * 将P表的一组表与C表的一组表交换的函数结果存入一组private的变量里
	 * 
	 * @param vEdorNo
	 *            String
	 * @param vEdorType
	 *            String
	 * @return boolean
	 */
	private boolean changeCPDate(String vEdorNo, String vEdorType) {
		// Cont表
		LPContDB tLPContDB = new LPContDB();
		LCContDB tLCContDB = new LCContDB();
		tLPContDB.setEdorNo(vEdorNo);
		tLPContDB.setEdorType(vEdorType);
		LPContSet tLPContSet = new LPContSet();
		tLPContSet = tLPContDB.query();
		if (tLPContSet == null || tLPContSet.size() <= 0) {
			this.makeError("changeCPDate", "查询保全保单表时产生错误！EdorNo=" + vEdorNo);
			return false;
		}
		for (int i = 1; i <= tLPContSet.size(); i++) {
			LCContSchema tLCContSchema = new LCContSchema();
			mRef.transFields(tLCContSchema, tLPContSet.get(i));
			mLCContSet.add(tLCContSchema);
			// 查询C表匹配数据交换到P表
			tLCContDB.setContNo(tLCContSchema.getContNo());
			if (tLCContDB.getInfo()) {
				LPContSchema tLPContSchema = new LPContSchema();
				mRef.transFields(tLPContSchema, tLCContDB.getSchema());
				tLPContSchema.setEdorNo(vEdorNo);
				tLPContSchema.setEdorType(vEdorType);
				mLPContSet.add(tLPContSchema);
			}
		}

		// Pol表
		LPPolDB tLPPolDB = new LPPolDB();
		LCPolDB tLCPolDB = new LCPolDB();
		tLPPolDB.setEdorNo(vEdorNo);
		tLPPolDB.setEdorType(vEdorType);
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet == null || tLPPolSet.size() <= 0) {
			this.makeError("changeCPDate", "查询保全险种表时产生错误！EdorNo=" + vEdorNo);
			return false;
		}
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			mRef.transFields(tLCPolSchema, tLPPolSet.get(i));
			mLCPolSet.add(tLCPolSchema);
			// 查询C表匹配数据交换到P表
			tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
			if (tLCPolDB.getInfo()) {
				LPPolSchema tLPPolSchema = new LPPolSchema();
				mRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
				tLPPolSchema.setEdorNo(vEdorNo);
				tLPPolSchema.setEdorType(vEdorType);
				mLPPolSet.add(tLPPolSchema);
			}
		}

		// Duty表
		LPDutyDB tLPDutyDB = new LPDutyDB();
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLPDutyDB.setEdorNo(vEdorNo);
		tLPDutyDB.setEdorType(vEdorType);
		LPDutySet tLPDutySet = new LPDutySet();
		tLPDutySet = tLPDutyDB.query();
		if (tLPDutySet == null || tLPDutySet.size() <= 0) {
			this.makeError("changeCPDate", "查询保全责任表时产生错误！EdorNo=" + vEdorNo);
			return false;
		}
		for (int i = 1; i <= tLPDutySet.size(); i++) {
			LCDutySchema tLCDutySchema = new LCDutySchema();
			mRef.transFields(tLCDutySchema, tLPDutySet.get(i));
			mLCDutySet.add(tLCDutySchema);
			// 查询C表匹配数据交换到P表
			tLCDutyDB.setPolNo(tLCDutySchema.getPolNo());
			tLCDutyDB.setDutyCode(tLCDutySchema.getDutyCode());
			if (tLCDutyDB.getInfo()) {
				LPDutySchema tLPDutySchema = new LPDutySchema();
				mRef.transFields(tLPDutySchema, tLCDutyDB.getSchema());
				tLPDutySchema.setEdorNo(vEdorNo);
				tLPDutySchema.setEdorType(vEdorType);
				mLPDutySet.add(tLPDutySchema);
			}
		}

		// Prem表
		LPPremDB tLPPremDB = new LPPremDB();
		LCPremDB tLCPremDB = new LCPremDB();
		tLPPremDB.setEdorNo(vEdorNo);
		tLPPremDB.setEdorType(vEdorType);
		LPPremSet tLPPremSet = new LPPremSet();
		tLPPremSet = tLPPremDB.query();
		if (tLPPremSet == null || tLPPremSet.size() <= 0) {
			this.makeError("changeCPDate", "查询保全保费项表时产生错误！EdorNo=" + vEdorNo);
			return false;
		}
		for (int i = 1; i <= tLPPremSet.size(); i++) {
			LCPremSchema tLCPremSchema = new LCPremSchema();
			mRef.transFields(tLCPremSchema, tLPPremSet.get(i));
			mLCPremSet.add(tLCPremSchema);
			// 查询C表匹配数据交换到P表
			tLCPremDB.setPolNo(tLCPremSchema.getPolNo());
			tLCPremDB.setDutyCode(tLCPremSchema.getDutyCode());
			tLCPremDB.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			if (tLCPremDB.getInfo()) {
				LPPremSchema tLPPremSchema = new LPPremSchema();
				mRef.transFields(tLPPremSchema, tLCPremDB.getSchema());
				tLPPremSchema.setEdorNo(vEdorNo);
				tLPPremSchema.setEdorType(vEdorType);
				mLPPremSet.add(tLPPremSchema);
			}
		}

		// Get表
		LPGetDB tLPGetDB = new LPGetDB();
		LCGetDB tLCGetDB = new LCGetDB();
		tLPGetDB.setEdorNo(vEdorNo);
		tLPGetDB.setEdorType(vEdorType);
		LPGetSet tLPGetSet = new LPGetSet();
		tLPGetSet = tLPGetDB.query();
		if (tLPGetSet == null || tLPGetSet.size() <= 0) {
			this.makeError("changeCPDate", "查询保全领取项表时产生错误！EdorNo=" + vEdorNo);
			return false;
		}
		for (int i = 1; i <= tLPGetSet.size(); i++) {
			LCGetSchema tLCGetSchema = new LCGetSchema();
			mRef.transFields(tLCGetSchema, tLPGetSet.get(i));
			mLCGetSet.add(tLCGetSchema);
			// 查询C表匹配数据交换到P表
			tLCGetDB.setPolNo(tLCGetSchema.getPolNo());
			tLCGetDB.setDutyCode(tLCGetSchema.getDutyCode());
			tLCGetDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
			if (tLCGetDB.getInfo()) {
				LPGetSchema tLPGetSchema = new LPGetSchema();
				mRef.transFields(tLPGetSchema, tLCGetDB.getSchema());
				tLPGetSchema.setEdorNo(vEdorNo);
				tLPGetSchema.setEdorType(vEdorType);
				mLPGetSet.add(tLPGetSchema);
			}
		}

		return true;
	}

	/**
	 * 错误处理
	 * 
	 * @param vFuncName
	 *            String
	 * @param vErrMessage
	 *            String
	 */

	private void makeError(String vFuncName, String vErrMessage) {
		// @@错误处理
		CError tError = new CError();
		tError.moduleName = "PEdorCDDetailBL";
		tError.functionName = vFuncName;
		tError.errorMessage = vErrMessage;
		this.mErrors.addOneError(tError);
	}

	public static void main(String[] args) {
		PEdorCDBackConfirmBL pedorcdbackconfirmbl = new PEdorCDBackConfirmBL();
	}
}
