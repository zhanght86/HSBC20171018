package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title: Web 业务系统
 * </p>
 * <p>
 * Description: 客户地址信息变更ConfirmBL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author Nicholas
 * @version 1.0
 */

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
import com.sinosoft.lis.vschema.LCAddressSet;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPAppntSet;
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
import com.sinosoft.utility.VData;

public class PEdorCDConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorCDConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet(); // 因为是客户层，所以根据保全受理号去做
	private GlobalInput mGlobalInput = new GlobalInput();

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

	private Reflections mReflections = new Reflections();

	public PEdorCDConfirmBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		// logger.debug("---End getInputData---");

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// logger.debug("after checkData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		// logger.debug("---End dealData---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorCDDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return: boolean
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		mLPEdorItemSet = tLPEdorItemDB.query();
		if (mLPEdorItemSet == null || mLPEdorItemSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorCDDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "查询批改项目信息失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLPEdorItemDB = null;

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			// 获得此时的日期和时间
			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();

			LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
			LPEdorItemSchema tLPEdorItemSchema = null;
			LCAppntDB tLCAppntDB = new LCAppntDB();
			LPAppntDB tLPAppntDB = new LPAppntDB();
			LCAppntSet tLCAppntSet = new LCAppntSet();
			LPAppntSet tLPAppntSet = new LPAppntSet();
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			LPInsuredDB tLPInsuredDB = new LPInsuredDB();
			LCInsuredSet tLCInsuredSet = new LCInsuredSet();
			LPInsuredSet tLPInsuredSet = new LPInsuredSet();

			// 交换地址信息
			LCAddressSet tLCAddressSet = new LCAddressSet();
			LPAddressSet tLPAddressSet = new LPAddressSet();
			LPAddressSet tempLPAddressSet = new LPAddressSet();

			// ====================== LPAddress 中已经按EdorNo保存记录，直接由EdorNo查询 ====
			// Begin
			/**
			 * Modify by lizhuo at 2005-10-19
			 */
			//
			// String tSql = "SELECT *"
			// + " FROM LPAddress a"
			// + " WHERE EdorType='CD'"
			// + " and exists(select 'X' from LPEdorItem where EdorAcceptNo='" +
			// mLPEdorItemSchema.getEdorAcceptNo() +
			// "' and EdorNo=a.EdorNo)";
			LCAddressDB tLCAddressDB = new LCAddressDB();
			LPAddressDB tLPAddressDB = new LPAddressDB();
			// tempLPAddressSet = tLPAddressDB.executeQuery(tSql);
			tLPAddressDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPAddressDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tempLPAddressSet = tLPAddressDB.query();
			// ====================== LPAddress 中已经按EdorNo保存记录，直接由EdorNo查询 ====
			// End
			if (tempLPAddressSet == null || tempLPAddressSet.size() <= 0) {
				this.makeError("dealData", "查询保全地址信息失败！");
				return false;
			}
			for (int i = 1; i <= tempLPAddressSet.size(); i++) {
				LCAddressSchema tLCAddressSchema = new LCAddressSchema();
				mReflections.transFields(tLCAddressSchema, tempLPAddressSet
						.get(i));
				tLCAddressSchema.setOperator(this.mGlobalInput.Operator);
				tLCAddressSchema.setModifyDate(strCurrentDate);
				tLCAddressSchema.setModifyTime(strCurrentTime);
				tLCAddressSet.add(tLCAddressSchema);
				// 查询C表匹配数据交换到P表
				tLCAddressDB.setCustomerNo(tLCAddressSchema.getCustomerNo());
				tLCAddressDB.setAddressNo(tLCAddressSchema.getAddressNo());
				if (tLCAddressDB.getInfo()) {
					LPAddressSchema tLPAddressSchema = new LPAddressSchema();
					mReflections.transFields(tLPAddressSchema, tLCAddressDB
							.getSchema());
					tLPAddressSchema.setEdorNo(tempLPAddressSet.get(i)
							.getEdorNo());
					tLPAddressSchema.setEdorType(tempLPAddressSet.get(i)
							.getEdorType());
					tLPAddressSchema.setOperator(this.mGlobalInput.Operator);
					tLPAddressSchema.setModifyDate(strCurrentDate);
					tLPAddressSchema.setModifyTime(strCurrentTime);
					tLPAddressSet.add(tLPAddressSchema);
				}
			}

			for (int k = 1; k <= mLPEdorItemSet.size(); k++) {
				tLPEdorItemSchema = new LPEdorItemSchema();
				tLPEdorItemSchema = mLPEdorItemSet.get(k);

				// 交换投保人表数据
				tLPAppntDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
				tLPAppntDB.setEdorType(tLPEdorItemSchema.getEdorType());
				LPAppntSet tempLPAppntSet = tLPAppntDB.query();
				if (tempLPAppntSet != null || tempLPAppntSet.size() > 0) {
					for (int i = 1; i <= tempLPAppntSet.size(); i++) {
						LCAppntSchema tLCAppntSchema = new LCAppntSchema();
						mReflections.transFields(tLCAppntSchema, tempLPAppntSet
								.get(i));
						tLCAppntSchema.setOperator(this.mGlobalInput.Operator);
						tLCAppntSchema.setModifyDate(strCurrentDate);
						tLCAppntSchema.setModifyTime(strCurrentTime);
						tLCAppntSet.add(tLCAppntSchema);
						// 查询C表匹配数据交换到P表
						tLCAppntDB.setContNo(tLCAppntSchema.getContNo());
						if (tLCAppntDB.getInfo()) {
							LPAppntSchema tLPAppntSchema = new LPAppntSchema();
							mReflections.transFields(tLPAppntSchema, tLCAppntDB
									.getSchema());
							tLPAppntSchema.setEdorNo(tempLPAppntSet.get(i)
									.getEdorNo());
							tLPAppntSchema.setEdorType(tempLPAppntSet.get(i)
									.getEdorType());
							tLPAppntSchema
									.setOperator(this.mGlobalInput.Operator);
							tLPAppntSchema.setModifyDate(strCurrentDate);
							tLPAppntSchema.setModifyTime(strCurrentTime);
							tLPAppntSet.add(tLPAppntSchema);
						}
					}
				}

				// 交换被保人表数据
				tLPInsuredDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
				tLPInsuredDB.setEdorType(tLPEdorItemSchema.getEdorType());
				LPInsuredSet tempLPInsuredSet = tLPInsuredDB.query();
				if (tempLPInsuredSet != null || tempLPInsuredSet.size() > 0) {
					for (int i = 1; i <= tempLPInsuredSet.size(); i++) {
						LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
						mReflections.transFields(tLCInsuredSchema,
								tempLPInsuredSet.get(i));
						tLCInsuredSchema
								.setOperator(this.mGlobalInput.Operator);
						tLCInsuredSchema.setModifyDate(strCurrentDate);
						tLCInsuredSchema.setModifyTime(strCurrentTime);
						tLCInsuredSet.add(tLCInsuredSchema);
						// 查询C表匹配数据交换到P表
						tLCInsuredDB.setContNo(tLCInsuredSchema.getContNo());
						tLCInsuredDB.setInsuredNo(tLCInsuredSchema
								.getInsuredNo());
						if (tLCInsuredDB.getInfo()) {
							LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
							mReflections.transFields(tLPInsuredSchema,
									tLCInsuredDB.getSchema());
							tLPInsuredSchema.setEdorNo(tempLPInsuredSet.get(i)
									.getEdorNo());
							tLPInsuredSchema.setEdorType(tempLPInsuredSet
									.get(i).getEdorType());
							tLPInsuredSchema
									.setOperator(this.mGlobalInput.Operator);
							tLPInsuredSchema.setModifyDate(strCurrentDate);
							tLPInsuredSchema.setModifyTime(strCurrentTime);
							tLPInsuredSet.add(tLPInsuredSchema);
						}
					}
				}

				// //设置保全主表数据到保全确认状态
				// tLPEdorItemSchema.setEdorState("0");
				// tLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
				// tLPEdorItemSchema.setModifyDate(strCurrentDate);
				// tLPEdorItemSchema.setModifyTime(strCurrentTime);
				// tLPEdorItemSet.add(tLPEdorItemSchema);

				// ***2005-08-30日新增***由于人工核保时可能会加费，所以这里将P表相应数据换回C表
				if (!changeCPDate(tLPEdorItemSchema.getEdorNo(),
						tLPEdorItemSchema.getEdorType())) {
					return false;
				}
			}

			mMap.put(tLCAddressSet, "DELETE&INSERT");
			mMap.put(tLPAddressSet, "DELETE&INSERT");
			mMap.put(tLCAppntSet, "DELETE&INSERT");
			mMap.put(tLCInsuredSet, "DELETE&INSERT");
			// ==== add by lizhuo at 2005-10-19 ==== Begin
			mMap.put(tLPAppntSet, "DELETE&INSERT");
			mMap.put(tLPInsuredSet, "DELETE&INSERT");
			// ===================================== End
			// mMap.put(tLPEdorItemSet, "UPDATE");
			mMap.put(mLPContSet, "DELETE&INSERT");
			mMap.put(mLCContSet, "DELETE&INSERT");
			mMap.put(mLPPolSet, "DELETE&INSERT");
			mMap.put(mLCPolSet, "DELETE&INSERT");
			mMap.put(mLPDutySet, "DELETE&INSERT");
			mMap.put(mLCDutySet, "DELETE&INSERT");
			mMap.put(mLPPremSet, "DELETE&INSERT");
			mMap.put(mLCPremSet, "DELETE&INSERT");
			mMap.put(mLPGetSet, "DELETE&INSERT");
			mMap.put(mLCGetSet, "DELETE&INSERT");

			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorCDDetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	// 错误处理
	private void makeError(String vFuncName, String vErrMessage) {
		// @@错误处理
		CError tError = new CError();
		tError.moduleName = "PEdorCDDetailBL";
		tError.functionName = vFuncName;
		tError.errorMessage = vErrMessage;
		this.mErrors.addOneError(tError);
	}

	// --将P表的一组表与C表的一组表交换的函数结果存入一组private的变量里
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
			mReflections.transFields(tLCContSchema, tLPContSet.get(i));
			mLCContSet.add(tLCContSchema);
			// 查询C表匹配数据交换到P表
			tLCContDB.setContNo(tLCContSchema.getContNo());
			if (tLCContDB.getInfo()) {
				LPContSchema tLPContSchema = new LPContSchema();
				mReflections.transFields(tLPContSchema, tLCContDB.getSchema());
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
			mReflections.transFields(tLCPolSchema, tLPPolSet.get(i));
			mLCPolSet.add(tLCPolSchema);
			// 查询C表匹配数据交换到P表
			tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
			if (tLCPolDB.getInfo()) {
				LPPolSchema tLPPolSchema = new LPPolSchema();
				mReflections.transFields(tLPPolSchema, tLCPolDB.getSchema());
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
			mReflections.transFields(tLCDutySchema, tLPDutySet.get(i));
			mLCDutySet.add(tLCDutySchema);
			// 查询C表匹配数据交换到P表
			tLCDutyDB.setPolNo(tLCDutySchema.getPolNo());
			tLCDutyDB.setDutyCode(tLCDutySchema.getDutyCode());
			if (tLCDutyDB.getInfo()) {
				LPDutySchema tLPDutySchema = new LPDutySchema();
				mReflections.transFields(tLPDutySchema, tLCDutyDB.getSchema());
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
			mReflections.transFields(tLCPremSchema, tLPPremSet.get(i));
			mLCPremSet.add(tLCPremSchema);
			// 查询C表匹配数据交换到P表
			tLCPremDB.setPolNo(tLCPremSchema.getPolNo());
			tLCPremDB.setDutyCode(tLCPremSchema.getDutyCode());
			tLCPremDB.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			if (tLCPremDB.getInfo()) {
				LPPremSchema tLPPremSchema = new LPPremSchema();
				mReflections.transFields(tLPPremSchema, tLCPremDB.getSchema());
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
			mReflections.transFields(tLCGetSchema, tLPGetSet.get(i));
			mLCGetSet.add(tLCGetSchema);
			// 查询C表匹配数据交换到P表
			tLCGetDB.setPolNo(tLCGetSchema.getPolNo());
			tLCGetDB.setDutyCode(tLCGetSchema.getDutyCode());
			tLCGetDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
			if (tLCGetDB.getInfo()) {
				LPGetSchema tLPGetSchema = new LPGetSchema();
				mReflections.transFields(tLPGetSchema, tLCGetDB.getSchema());
				tLPGetSchema.setEdorNo(vEdorNo);
				tLPGetSchema.setEdorType(vEdorType);
				mLPGetSet.add(tLPGetSchema);
			}
		}

		return true;
	}

	public static void main(String[] args) {
		PEdorCDConfirmBL tPEdorCDConfirmBL = new PEdorCDConfirmBL();
	}
}
