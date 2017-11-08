package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LPAddressDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vbl.LCPolBLSet;
import com.sinosoft.lis.vbl.LPPolBLSet;
import com.sinosoft.lis.vschema.LCAddressSet;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
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
 * @ReWrite ZhangRong
 * @version 1.0
 */
public class PEdorADConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorADConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;

	/**  */
	private LCPolBL mLCPolBL = new LCPolBL();
	private LCPolBLSet mLCPolBLSet = new LCPolBLSet();
	private LPPolBL mLPPolBL = new LPPolBL();
	private LPPolBLSet mLPPolBLSet = new LPPolBLSet();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorADConfirmBL() {
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
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		getInputData(cInputData);

		// 数据准备操作（preparedata())
		if (!prepareData()) {
			return false;
		}

		this.setOperate("CONFIRM||AD");

		logger.debug("---" + mOperate);

		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
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
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		/*
		 * String cOperate, AppntName; int m; m = 0; AppntName = ""; boolean
		 * tFlag = false;
		 * 
		 * Reflections tReflections = new Reflections();
		 * 
		 * LPAppntIndSet aLPAppntIndSet = new LPAppntIndSet(); LCAppntIndSet
		 * aLCAppntIndSet = new LCAppntIndSet(); LPPolSet tLPPolSet = new
		 * LPPolSet(); LCPolSet aLCPolSet = new LCPolSet();
		 * 
		 * //当投保人和受益人为同一人时，自动变更受益人 LCBnfSet aLCBnfSet = new LCBnfSet();
		 * //当投保人和被保险人是同一人时，自动进行被保险人变更 LCInsuredSet aLCInsuredSet = new
		 * LCInsuredSet();
		 * 
		 * LPPolDB tLPPolDB = new LPPolDB();
		 * tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		 * tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType()); tLPPolSet =
		 * tLPPolDB.query(); for (int i = 1; i <= tLPPolSet.size(); i++) {
		 * //得到当前保全需要更新的保全数据。 LPAppntIndDB tLPAppntIndDB = new LPAppntIndDB();
		 * tLPAppntIndDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		 * tLPAppntIndDB.setEdorType(mLPEdorItemSchema.getEdorType());
		 * tLPAppntIndDB.setPolNo(tLPPolSet.get(i).getPolNo());
		 * 
		 * LPAppntIndSet tLPAppntIndSet = new LPAppntIndSet(); tLPAppntIndSet =
		 * tLPAppntIndDB.query(); m = tLPAppntIndSet.size();
		 * logger.debug("---m" + m);
		 * 
		 * for (int j = 1; j <= m; j++) { LPAppntIndSchema tLPAppntIndSchema =
		 * new LPAppntIndSchema();
		 * 
		 * LCAppntIndDB tLCAppntIndDB = new LCAppntIndDB();
		 * tLCAppntIndDB.setPolNo(tLPAppntIndSet.get(j).getPolNo());
		 * tLCAppntIndDB.setCustomerNo(tLPAppntIndSet.get(j).getCustomerNo());
		 * if (!tLCAppntIndDB.getInfo()) { return false; }
		 * tReflections.transFields(tLPAppntIndSchema,
		 * tLCAppntIndDB.getSchema());
		 * tLPAppntIndSchema.setEdorNo(tLPAppntIndSet.get(j).getEdorNo());
		 * tLPAppntIndSchema.setEdorType(tLPAppntIndSet.get(j).getEdorType());
		 * 
		 * //转换成保单个人信息。 LCAppntIndSchema tLCAppntIndSchema = new
		 * LCAppntIndSchema(); tReflections.transFields(tLCAppntIndSchema,
		 * tLPAppntIndSet.get(j));
		 * tLCAppntIndSchema.setModifyDate(PubFun.getCurrentDate());
		 * tLCAppntIndSchema.setModifyTime(PubFun.getCurrentTime());
		 * 
		 * aLPAppntIndSet.add(tLPAppntIndSchema);
		 * aLCAppntIndSet.add(tLCAppntIndSchema); }
		 *  // 生成LCPol内容，为了确认时简化操作（name) LCPolSchema tLCPolSchema = new
		 * LCPolSchema(); tReflections.transFields(tLCPolSchema,
		 * tLPPolSet.get(i)); aLCPolSet.add(tLCPolSchema); }
		 * 
		 * //add by dingzhong 2003-07-01 //关联受益人表进行变更
		 * logger.debug("关联受益人表进行变更.......");
		 * logger.debug("LCAppntSize : " + aLPAppntIndSet.size()); nSize =
		 * aLPAppntIndSet.size(); LCBnfSet tLCBnfSet = new LCBnfSet(); LCBnfDB
		 * tLCBnfDB = new LCBnfDB(); for (int i = 1; i <= nSize; i++) {
		 * LPAppntIndSchema tAppntIndSchema = aLPAppntIndSet.get(i);
		 * logger.debug(tAppntIndSchema.getPolNo());
		 * logger.debug(tAppntIndSchema.getName());
		 * tLCBnfDB.setPolNo(tAppntIndSchema.getPolNo());
		 * tLCBnfDB.setName(tAppntIndSchema.getName()); tLCBnfSet =
		 * tLCBnfDB.query(); logger.debug("find size :" +
		 * tLCBnfSet.size()); if (tLCBnfSet.size() == 0) { continue; }
		 * 
		 * //找到在aLCAppntIndSet中对应的schema,用来更新LCBnfSet LCAppntIndSchema
		 * tLCAppntIndSchema = new LCAppntIndSchema(); int k = 1; for (; k <=
		 * aLCAppntIndSet.size(); k++) { tLCAppntIndSchema =
		 * aLCAppntIndSet.get(k); if
		 * (tLCAppntIndSchema.getPolNo().equals(tAppntIndSchema.getPolNo())) {
		 * break; } } if (k > aLCAppntIndSet.size()) { continue; }
		 * 
		 * for (int j = 1; j <= tLCBnfSet.size(); j++) { LCBnfSchema
		 * tLCBnfSchema = tLCBnfSet.get(j);
		 * tReflections.transFields(tLCBnfSchema, tLCAppntIndSchema);
		 * tLCBnfSchema.setModifyDate(PubFun.getCurrentDate());
		 * tLCBnfSchema.setModifyTime(PubFun.getCurrentTime());
		 * aLCBnfSet.add(tLCBnfSchema.getSchema()); }
		 *  }
		 * 
		 * //变更投保单中的主被保人信息 LCPolSet aResultLCPolSet = new LCPolSet(); for (int i =
		 * 1; i <= aLCPolSet.size(); i++) { LCPolSchema tLCPolSchema =
		 * aLCPolSet.get(i); String strAppntNo = tLCPolSchema.getAppntNo(); if
		 * (!tLCPolSchema.getInsuredNo().equals(tLCPolSchema.getAppntNo())) {
		 * continue; }
		 * 
		 * //找到对应的投保人的信息 int j = 1; for (; j <= aLCAppntIndSet.size(); j++) {
		 * LCAppntIndSchema tLCAppntIndSchema = aLCAppntIndSet.get(j); if
		 * (!tLCAppntIndSchema.getCustomerNo().equals(strAppntNo)) { continue; }
		 * tLCPolSchema.setInsuredName(tLCAppntIndSchema.getName());
		 * tLCPolSchema.setInsuredSex(tLCAppntIndSchema.getSex());
		 * tLCPolSchema.setInsuredBirthday(tLCAppntIndSchema.getBirthday()); }
		 * aResultLCPolSet.add(tLCPolSchema.getSchema());
		 *  }
		 * 
		 * //得到当前LPEdorItem保单信息主表的状态，并更新状态为申请确认。
		 * 
		 * mLPEdorItemSchema.setEdorState("0");
		 * mLPEdorItemSchema.setConfDate(PubFun.getCurrentDate());
		 * mLPEdorItemSchema.setConfOperator(mGlobalInput.Operator);
		 * //生效日期为保全生效日期
		 * mLPEdorItemSchema.setEdorValiDate(PubFun.getCurrentDate());
		 * 
		 * mInputData.clear(); mInputData.add(mLPEdorItemSchema);
		 * mInputData.add(aLCAppntIndSet); mInputData.add(aLPAppntIndSet);
		 * 
		 * mInputData.add(aLCPolSet); mInputData.add(tLPPolSet);
		 * mInputData.add(aLCBnfSet); mInputData.add(aLCInsuredSet);
		 * mInputData.add(aResultLCPolSet);
		 * 
		 * mResult.clear(); mResult.add(mLPEdorItemSchema);
		 * mResult.add(aLCAppntIndSet); mResult.add(aLCPolSet);
		 * mResult.add(tLPPolSet); mResult.add(aLPAppntIndSet);
		 * mResult.add(aResultLCPolSet); return true;
		 */

		LCAppntSet saveLCAppntSet = new LCAppntSet();
		LCAddressSet saveLCAddressSet = new LCAddressSet();

		LPAppntSet saveLPAppntSet = new LPAppntSet();
		LPAddressSet saveLPAddressSet = new LPAddressSet();

		Reflections tRef = new Reflections();

		// 得到当前保单的投保人资料
		LCAppntSchema aLCAppntSchema = new LCAppntSchema();
		LPAppntSchema aLPAppntSchema = new LPAppntSchema();
		LCAppntSet tLCAppntSet = new LCAppntSet();
		LPAppntSet tLPAppntSet = new LPAppntSet();

		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());

		LPAppntDB tLPAppntDB = new LPAppntDB();
		tLPAppntDB.setSchema(tLPAppntSchema);
		tLPAppntSet = tLPAppntDB.query();
		for (int j = 1; j <= tLPAppntSet.size(); j++) {
			aLPAppntSchema = tLPAppntSet.get(j);
			LCAppntSchema tLCAppntSchema = new LCAppntSchema();
			tLPAppntSchema = new LPAppntSchema();

			LCAppntDB aLCAppntDB = new LCAppntDB();
			aLCAppntDB.setContNo(aLPAppntSchema.getContNo());
			aLCAppntDB.setAppntNo(aLPAppntSchema.getAppntNo());
			if (!aLCAppntDB.getInfo()) {
				mErrors.copyAllErrors(aLCAppntDB.mErrors);
				mErrors.addOneError(new CError("查询投保人信息失败！"));
				return false;
			}
			aLCAppntSchema = aLCAppntDB.getSchema();
			tRef.transFields(tLPAppntSchema, aLCAppntSchema);
			tLPAppntSchema.setEdorNo(aLPAppntSchema.getEdorNo());
			tLPAppntSchema.setEdorType(aLPAppntSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCAppntSchema, aLPAppntSchema);
			tLCAppntSchema.setModifyDate(PubFun.getCurrentDate());
			tLCAppntSchema.setModifyTime(PubFun.getCurrentTime());

			saveLPAppntSet.add(tLPAppntSchema);
			tLCAppntSet.add(tLCAppntSchema);
			saveLCAppntSet.add(tLCAppntSchema);
		}

		// 得到当前保单的投保人资料
		boolean tNewAdd = false;
		LCAddressSchema aLCAddressSchema = new LCAddressSchema();
		LPAddressSchema aLPAddressSchema = new LPAddressSchema();
		LCAddressSet tLCAddressSet = new LCAddressSet();
		LPAddressSet tLPAddressSet = new LPAddressSet();

		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		tLPAddressSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAddressSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAddressSchema.setCustomerNo(tLPAppntSchema.getAppntNo());

		LPAddressDB tLPAddressDB = new LPAddressDB();
		tLPAddressDB.setSchema(tLPAddressSchema);
		tLPAddressSet = tLPAddressDB.query();
		for (int j = 1; j <= tLPAddressSet.size(); j++) {
			aLPAddressSchema = tLPAddressSet.get(j);
			LCAddressSchema tLCAddressSchema = new LCAddressSchema();
			tLPAddressSchema = new LPAddressSchema();

			LCAddressDB aLCAddressDB = new LCAddressDB();
			aLCAddressDB.setCustomerNo(aLPAddressSchema.getCustomerNo());
			aLCAddressDB.setAddressNo(aLPAddressSchema.getAddressNo());
			if (!aLCAddressDB.getInfo()) {
				if (aLCAddressDB.mErrors.needDealError()) {
					mErrors.copyAllErrors(aLCAddressDB.mErrors);
					mErrors.addOneError(new CError("查询投保人地址信息失败！"));
					return false;
				}
				tNewAdd = true;
				tRef.transFields(tLCAddressSchema, aLPAddressSchema);
				tLCAddressSchema.setModifyDate(PubFun.getCurrentDate());
				tLCAddressSchema.setModifyTime(PubFun.getCurrentTime());
				tLCAddressSet.add(tLCAddressSchema);
				saveLCAddressSet.add(tLCAddressSchema);
			} else {
				tNewAdd = false;
				aLCAddressSchema = aLCAddressDB.getSchema();
				tRef.transFields(tLPAddressSchema, aLCAddressSchema);
				tLPAddressSchema.setEdorNo(aLPAddressSchema.getEdorNo());
				tLPAddressSchema.setEdorType(aLPAddressSchema.getEdorType());

				// 转换成保单个人信息。
				tRef.transFields(tLCAddressSchema, aLPAddressSchema);
				tLCAddressSchema.setModifyDate(PubFun.getCurrentDate());
				tLCAddressSchema.setModifyTime(PubFun.getCurrentTime());

				saveLPAddressSet.add(tLPAddressSchema);
				tLCAddressSet.add(tLCAddressSchema);
				saveLCAddressSet.add(tLCAddressSchema);
			}
		}

		// 得到当前LPEdorItem保单信息主表的状态，并更新状态为申请确认。
		mLPEdorItemSchema.setEdorState("0");

		mMap.put(mLPEdorItemSchema, "UPDATE");

		mMap.put(saveLCAppntSet, "UPDATE");

		mMap.put(saveLPAppntSet, "UPDATE");
		if (tNewAdd) {
			mMap.put(saveLCAddressSet, "INSERT");
		} else {
			mMap.put(saveLCAddressSet, "UPDATE");
			mMap.put(saveLPAddressSet, "UPDATE");
		}
		mResult.add(mMap);
		return true;

	}

}
