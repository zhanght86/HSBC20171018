package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LPAddressDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vbl.LCPolBLSet;
import com.sinosoft.lis.vbl.LPPolBLSet;
import com.sinosoft.lis.vschema.LCAddressSet;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.lis.vschema.LPPolSet;
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
 * @ReWrite ZhangRong
 * @version 1.0
 */
public class PEdorACConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorACConfirmBL.class);
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

	public PEdorACConfirmBL() {
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

		this.setOperate("CONFIRM||AC");

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
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();
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
		 * tLCAppntIndSchema.setModifyDate(tCurrentDate);
		 * tLCAppntIndSchema.setModifyTime(tCurrentTime);
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
		 * tLCBnfSchema.setModifyDate(tCurrentDate);
		 * tLCBnfSchema.setModifyTime(tCurrentTime);
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

		LCContSet aLCContSet = new LCContSet();
		LCPolSet aLCPolSet = new LCPolSet();
		LCAppntSet aLCAppntSet = new LCAppntSet();
		LCAddressSet aLCAddressSet = new LCAddressSet();
		LDPersonSet aLDPersonSet = new LDPersonSet();

		LPContSet aLPContSet = new LPContSet();
		LPPolSet aLPPolSet = new LPPolSet();
		LPAppntSet aLPAppntSet = new LPAppntSet();
		LPAddressSet aLPAddressSet = new LPAddressSet();
		LPPersonSet aLPPersonSet = new LPPersonSet();

		Reflections tRef = new Reflections();

		// 得到投保人资料信息表
		LPPolSet tLPPolSet = new LPPolSet();
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());

		String strSql = "select * from LPPol where EdorNo = '"
				+ "?EdorNo?" + "' and EdorType = '"
				+ "?EdorType?" + "'";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(strSql);
        sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
        sqlbv.put("EdorType", mLPEdorItemSchema.getEdorType());
		tLPPolSet = tLPPolDB.executeQuery(sqlbv);

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

		LPContSchema tLPContSchema = new LPContSchema();
		LCContSchema tLCContSchema = new LCContSchema();
		LPContSchema aLPContSchema = new LPContSchema();
		LCContSchema aLCContSchema = new LCContSchema();
		LCContSet tLCContSet = new LCContSet();
		LPContSet tLPContSet = new LPContSet();

		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setSchema(tLPContSchema);
		tLPContSet = tLPContDB.query();
		for (int j = 1; j <= tLPContSet.size(); j++) {
			aLPContSchema = tLPContSet.get(j);
			tLCContSchema = new LCContSchema();
			tLPContSchema = new LPContSchema();
			// ******************** 更新应收记录 *********** add by lizhuo at
			// 2005-11-05 ***** Begin
			this.updateLJSPay(aLPContSchema);
			// ******************** 更新应收记录 *********** add by lizhuo at
			// 2005-11-05 ***** End
			LCContDB aLCContDB = new LCContDB();
			aLCContDB.setContNo(aLPContSchema.getContNo());
			aLCContDB.setInsuredNo(aLPContSchema.getInsuredNo());
			if (!aLCContDB.getInfo()) {
				mErrors.copyAllErrors(aLCContDB.mErrors);
				mErrors.addOneError(new CError("查询被保人信息失败！"));
				return false;
			}
			aLCContSchema = aLCContDB.getSchema();
			tRef.transFields(tLPContSchema, aLCContSchema);
			tLPContSchema.setEdorNo(aLPContSchema.getEdorNo());
			tLPContSchema.setEdorType(aLPContSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCContSchema, aLPContSchema);
			tLCContSchema.setModifyDate(tCurrentDate);
			tLCContSchema.setModifyTime(tCurrentTime);

			aLPContSet.add(tLPContSchema);
			tLCContSet.add(tLCContSchema);
			aLCContSet.add(tLCContSchema);
		}

		// 得到当前保单的投保人资料
		LCAppntSchema aLCAppntSchema = new LCAppntSchema();
		LPAppntSchema aLPAppntSchema = new LPAppntSchema();
		LCAppntSet tLCAppntSet = new LCAppntSet();
		LPAppntSet tLPAppntSet = new LPAppntSet();

		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntSchema.setContNo(mLPEdorItemSchema.getContNo());

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
			tLCAppntSchema.setModifyDate(tCurrentDate);
			tLCAppntSchema.setModifyTime(tCurrentTime);

			aLPAppntSet.add(tLPAppntSchema);
			tLCAppntSet.add(tLCAppntSchema);
			aLCAppntSet.add(tLCAppntSchema);
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
		String selMaxLPAddress = " select * from lpaddress where edorno = '"
				+ "?edorno?" + "' and edortype = '"
				+ "?edortype?" + "' and customerno = '"
				+ "?customerno?" + "' order by addressno desc ";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(selMaxLPAddress);
		sbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv.put("customerno", tLPAppntSchema.getAppntNo());
		LPAddressDB tLPAddressDB = new LPAddressDB();

		tLPAddressSet = tLPAddressDB.executeQuery(sbv);
		// for (int j = 1; j <= tLPAddressSet.size(); j++) {
		if (tLPAddressSet != null && tLPAddressSet.size() > 0) {
			aLPAddressSchema = tLPAddressSet.get(1);
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
				tLCAddressSchema.setModifyDate(tCurrentDate);
				tLCAddressSchema.setModifyTime(tCurrentTime);
				tLCAddressSet.add(tLCAddressSchema);
				aLCAddressSet.add(tLCAddressSchema);
			} else {
				tNewAdd = false;
				aLCAddressSchema = aLCAddressDB.getSchema();
				tRef.transFields(tLPAddressSchema, aLCAddressSchema);
				tLPAddressSchema.setEdorNo(aLPAddressSchema.getEdorNo());
				tLPAddressSchema.setEdorType(aLPAddressSchema.getEdorType());

				// 转换成保单个人信息。
				tRef.transFields(tLCAddressSchema, aLPAddressSchema);
				tLCAddressSchema.setModifyDate(tCurrentDate);
				tLCAddressSchema.setModifyTime(tCurrentTime);

				aLPAddressSet.add(tLPAddressSchema);
				tLCAddressSet.add(tLCAddressSchema);
				aLCAddressSet.add(tLCAddressSchema);
			}
		}
		// }

		// 得到当前保单的投保人资料
		LDPersonSchema aLCPersonSchema = new LDPersonSchema();
		LPPersonSchema aLPPersonSchema = new LPPersonSchema();
		LDPersonSet tLDPersonSet = new LDPersonSet();
		LPPersonSet tLPPersonSet = new LPPersonSet();

		LPPersonSchema tLPPersonSchema = new LPPersonSchema();
		tLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPersonSchema.setEdorType(mLPEdorItemSchema.getEdorType());

		LPPersonDB tLPPersonDB = new LPPersonDB();
		tLPPersonDB.setSchema(tLPPersonSchema);
		tLPPersonSet = tLPPersonDB.query();
		for (int j = 1; j <= tLPPersonSet.size(); j++) {
			aLPPersonSchema = tLPPersonSet.get(j);
			LDPersonSchema tLDPersonSchema = new LDPersonSchema();
			tLPPersonSchema = new LPPersonSchema();

			LDPersonDB aLDPersonDB = new LDPersonDB();
			aLDPersonDB.setCustomerNo(aLPPersonSchema.getCustomerNo());
			if (!aLDPersonDB.getInfo()) {
				mErrors.copyAllErrors(aLDPersonDB.mErrors);
				mErrors.addOneError(new CError("查询投保人信息失败！"));
				return false;
			}
			aLCPersonSchema = aLDPersonDB.getSchema();
			tRef.transFields(tLPPersonSchema, aLCPersonSchema);
			tLPPersonSchema.setEdorNo(aLPPersonSchema.getEdorNo());
			tLPPersonSchema.setEdorType(aLPPersonSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLDPersonSchema, aLPPersonSchema);
			tLDPersonSchema.setModifyDate(tCurrentDate);
			tLDPersonSchema.setModifyTime(tCurrentTime);

			aLPPersonSet.add(tLPPersonSchema);
			tLDPersonSet.add(tLDPersonSchema);
			aLDPersonSet.add(tLDPersonSchema);
		}

		// 被保人与投保人关系变更
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LPInsuredSchema tLPInsuredSchema = null;
		LCInsuredSchema tLCInsuredSchema = null;
		LPInsuredSet tempLPInsuredSet = new LPInsuredSet();
		LPInsuredDB tLPInsuredDB = new LPInsuredDB();

		tLPInsuredDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
		tempLPInsuredSet = tLPInsuredDB.query();
		if (tempLPInsuredSet != null && tempLPInsuredSet.size() > 0) {
			for (int j = 1; j <= tempLPInsuredSet.size(); j++) {
				tLCInsuredSchema = new LCInsuredSchema();
				tRef.transFields(tLCInsuredSchema, tempLPInsuredSet.get(j));
				tLCInsuredSchema.setOperator(mGlobalInput.Operator);
				tLCInsuredSchema.setModifyDate(tCurrentDate);
				tLCInsuredSchema.setModifyTime(tCurrentTime);
				tLCInsuredSet.add(tLCInsuredSchema);

				LCInsuredDB tLCInsuredDB = new LCInsuredDB();
				tLCInsuredDB.setContNo(tLCInsuredSchema.getContNo());
				tLCInsuredDB.setInsuredNo(tLCInsuredSchema.getInsuredNo());
				if (tLCInsuredDB.getInfo()) {
					tLPInsuredSchema = new LPInsuredSchema();
					tRef
							.transFields(tLPInsuredSchema, tLCInsuredDB
									.getSchema());
					tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPInsuredSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					tLPInsuredSchema.setOperator(mGlobalInput.Operator);
					tLPInsuredSchema.setModifyDate(tCurrentDate);
					tLPInsuredSchema.setModifyTime(tCurrentTime);
					tLPInsuredSet.add(tLPInsuredSchema);
				}
			}
		}

		// LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		// LCInsuredSchema tLCInsuredSchema = null;
		// LPInsuredSchema aLPInsuredSchema = new LPInsuredSchema();
		// LCInsuredSchema aLCInsuredSchema = new LCInsuredSchema();
		// LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		// LPInsuredSet aLPInsuredSet = new LPInsuredSet();
		// LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		// LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		// LCInsuredDB aLCInsuredDB = null;
		//
		// tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		// tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		// tLPInsuredSchema.setContNo(mLPEdorItemSchema.getContNo());
		//
		// tLPInsuredDB.setSchema(tLPInsuredSchema);
		// tLPInsuredSet = tLPInsuredDB.query();
		// if (tLPInsuredSet != null && tLPInsuredSet.size() > 0)
		// {
		// for (int j = 1; j <= tLPInsuredSet.size(); j++)
		// {
		// aLPInsuredSchema = tLPInsuredSet.get(j);
		// tLCInsuredSchema = new LCInsuredSchema();
		// aLCInsuredSchema = new LCInsuredSchema();
		//
		// aLCInsuredDB = new LCInsuredDB();
		// aLCInsuredDB.setContNo(aLPInsuredSchema.getContNo());
		// aLCInsuredDB.setInsuredNo(aLPInsuredSchema.getInsuredNo());
		// if (!aLCInsuredDB.getInfo())
		// {
		// mErrors.copyAllErrors(aLCInsuredDB.mErrors);
		// mErrors.addOneError(new CError("查询被保人信息失败！"));
		// return false;
		// }
		// tLCInsuredSchema = aLCInsuredDB.getSchema();
		// aLCInsuredSchema = aLCInsuredDB.getSchema();
		// tRef.transFields(aLPInsuredSchema, tLCInsuredSchema);
		// aLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		// aLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		// aLPInsuredSchema.setModifyDate(tCurrentDate);
		// aLPInsuredSchema.setModifyTime(tCurrentTime);
		// aLPInsuredSchema.setOperator(mGlobalInput.Operator);
		//
		//
		// aLCInsuredSchema.setModifyDate(tCurrentDate);
		// aLCInsuredSchema.setModifyTime(tCurrentTime);
		// aLCInsuredSchema.setOperator(mGlobalInput.Operator);
		//
		// aLPInsuredSet.add(aLPInsuredSchema);
		// tLCInsuredSet.add(aLCInsuredSchema);
		// }
		// }

		// //得到当前LPEdorItem保单信息主表的状态，并更新状态为申请确认。
		// mLPEdorItemSchema.setEdorState("0");
		// mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
		// mLPEdorItemSchema.setModifyDate(tCurrentDate);
		// mLPEdorItemSchema.setModifyTime(tCurrentDate);
		//
		// mMap.put(mLPEdorItemSchema, "UPDATE");
		mMap.put(aLCContSet, "UPDATE");
		mMap.put(aLCPolSet, "UPDATE");
		mMap.put(aLCAppntSet, "UPDATE");
		mMap.put(aLDPersonSet, "UPDATE");
		mMap.put(aLPContSet, "UPDATE");
		mMap.put(aLPPolSet, "UPDATE");
		mMap.put(aLPAppntSet, "UPDATE");
		mMap.put(aLPPersonSet, "UPDATE");
		mMap.put(tLPInsuredSet, "DELETE&INSERT");
		mMap.put(tLCInsuredSet, "DELETE&INSERT");
		if (tNewAdd) {
			mMap.put(aLCAddressSet, "INSERT");
		} else {
			mMap.put(aLCAddressSet, "UPDATE");
			mMap.put(aLPAddressSet, "UPDATE");
		}
		mResult.add(mMap);
		return true;
	}

	/**
	 * updateLJSPay 更新应收总表 add by lizhuo at 2005-11-05
	 * 
	 * @param tLPContSchema
	 *            LPContSchema
	 */
	private void updateLJSPay(LPContSchema tLPContSchema) {
		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		// LJSPaySchema tLJSPaySchema = new LJSPaySchema();

		// tLJSPaySchema.setOtherNoType("2");
		// tLJSPaySchema.setOtherNo(tLPContSchema.getContNo());
		//
		// tLJSPayDB.setSchema(tLJSPaySchema);
		// tLJSPaySet = tLJSPayDB.query();
		String StrSQL = "select * from ljspay where otherno = '"
				+ "?otherno?"
				+ "' and othernotype in ('2','3')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(StrSQL);
		sqlbv.put("otherno", mLPEdorItemSchema.getContNo());
		tLJSPaySet = tLJSPayDB.executeQuery(sqlbv);

		if (tLJSPaySet.size() > 0) {
			LJSPaySet aLJSPaySet = new LJSPaySet();
			for (int j = 1; j <= tLJSPaySet.size(); j++) {
				LJSPaySchema aLJSPaySchema = tLJSPaySet.get(j);
				aLJSPaySchema.setBankCode(tLPContSchema.getBankCode());
				aLJSPaySchema.setBankAccNo(tLPContSchema.getBankAccNo());
				aLJSPaySchema.setAccName(tLPContSchema.getAccName());
				aLJSPaySchema.setModifyDate(PubFun.getCurrentDate());
				aLJSPaySchema.setModifyTime(PubFun.getCurrentTime());
				aLJSPaySchema.setOperator(mGlobalInput.Operator);
				aLJSPaySet.add(aLJSPaySchema);
			}
			mMap.put(aLJSPaySet, "UPDATE");
			LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
			LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
			tLJSPayPersonDB.setContNo(tLPContSchema.getContNo());
			tLJSPayPersonSet = tLJSPayPersonDB.query();
			if (tLJSPayPersonSet.size() > 0) {
				for (int j = 1; j <= tLJSPayPersonSet.size(); j++) {
					tLJSPayPersonSet.get(j).setBankCode(
							tLPContSchema.getBankCode());
					tLJSPayPersonSet.get(j).setBankAccNo(
							tLPContSchema.getBankAccNo());
					tLJSPayPersonSet.get(j).setModifyDate(
							PubFun.getCurrentDate());
					tLJSPayPersonSet.get(j).setModifyTime(
							PubFun.getCurrentTime());
					tLJSPayPersonSet.get(j).setOperator(mGlobalInput.Operator);
				}
				mMap.put(tLJSPayPersonSet, "UPDATE");
			}
		}

	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		tGlobalInput.ComCode = "86";
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

		tLPEdorItemSchema.setEdorAcceptNo("86000000002246");
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setEdorNo("420000000000322");
		tLPEdorItemSchema.setEdorType("AC");
		tLPEdorItemSchema.setContNo("230110000002218");
		tLPEdorItemSchema.setInsuredNo("000000");
		tLPEdorItemSchema.setPolNo("000000");

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLPEdorItemSchema);

		PEdorACConfirmBL tPEdorACConfirmBL = new PEdorACConfirmBL();
		if (tPEdorACConfirmBL.submitData(tVData, "")) {
			logger.debug("OK!");
		}

	}

}
