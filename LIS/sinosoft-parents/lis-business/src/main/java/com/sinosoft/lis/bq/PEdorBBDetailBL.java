package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPAppntBL;
import com.sinosoft.lis.bl.LPContBL;
import com.sinosoft.lis.bl.LPInsuredBL;
import com.sinosoft.lis.bl.LPPersonBL;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vdb.LDPersonDBSet;
import com.sinosoft.lis.vdb.LPPersonDBSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 客户基本信息变更
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author pst
 * @version 1.0
 */
public class PEdorBBDetailBL {
private static Logger logger = Logger.getLogger(PEdorBBDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/**客户身份标志，投保人还是被保人，0，投保人，1，被保人，2，两种兼之,但是不处理其作为受益人的情况，有专门项目负责*/
	private String tAppntIsInsuredFlag = "";

	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();

	private LPAppntSchema mLPAppntSchema = new LPAppntSchema();

	private LPPersonSet mLPPersonSet = new LPPersonSet();
	
	private GlobalInput mGlobalInput = new GlobalInput();
	
	// 获得此时的日期和时间
	private String strCurrentDate = PubFun.getCurrentDate();
	private String strCurrentTime = PubFun.getCurrentTime();

	public PEdorBBDetailBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据校验
		if (!checkData()) {
			return false;
		}

		logger.debug("after checkData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

//		PubSubmit tSubmit = new PubSubmit();
//		if (!tSubmit.submitData(mResult, "")) {
//			// @@错误处理
//			this.mErrors.copyAllErrors(tSubmit.mErrors);
//		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPInsuredSchema = (LPInsuredSchema) mInputData
					.getObjectByObjectName("LPInsuredSchema", 0);
			mLPAppntSchema = (LPAppntSchema) mInputData
			.getObjectByObjectName("LPAppntSchema", 0);
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			if (mLPEdorItemSchema == null) {
				return false;
			}
			tAppntIsInsuredFlag = mLPEdorItemSchema.getStandbyFlag1();
			// 获得mLPEdorItemSchema的其它信息
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
			tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
			mLPEdorItemSchema.setSchema(tLPEdorItemDB.query().get(1));
			
			mLPInsuredSchema.setContNo(mLPEdorItemSchema.getContNo());
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败！");
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {


		// 准备要更新的LPCont表的最新信息（主要是银行帐户信息）
		LPContBL tLPContBL = new LPContBL();
		LPContSchema tLPContSchema = new LPContSchema();
		tLPContSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
		tLPContSchema.setContNo(this.mLPEdorItemSchema.getContNo());
		if (!tLPContBL.queryLastLPCont(this.mLPEdorItemSchema, tLPContSchema)) {
			return false;
		}
		tLPContSchema.setSchema(tLPContBL.getSchema());
		
		


//		LDPersonDB tLDPersonDB = new LDPersonDB();
//		LDPersonSet tLDPersonSet = new LDPersonDBSet();
//		tLDPersonDB.setCustomerNo(mLPEdorItemSchema.getInsuredNo());
//		tLDPersonSet = tLDPersonDB.query();
//		if (tLDPersonSet.size() < 1) {
//			CError.buildErr(this, "客户关键信息不符!");
//			return false;
//		}
//		
//		LPPersonBL tLPPersonBL = new LPPersonBL();
//		LPPersonSchema gLPPersonSchema = new LPPersonSchema();
//		gLPPersonSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
//		gLPPersonSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
//		gLPPersonSchema.setCustomerNo(this.mLPEdorItemSchema.getInsuredNo());
//		if (!tLPPersonBL.queryLastLPPerson(this.mLPEdorItemSchema,
//				gLPPersonSchema)) {
//			return false;
//		}
//		gLPPersonSchema.setSchema(tLPPersonBL.getSchema());
		
		
		// 准备要更新的LPInsured表的最新信息
		LPInsuredBL tLPInsuredBL = new LPInsuredBL();
		if ("1".equals(tAppntIsInsuredFlag)) {
			if (!tLPInsuredBL.queryLastLPInsured(this.mLPEdorItemSchema,
					this.mLPInsuredSchema)) {
				return false;
			}
			LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
			tLPInsuredSchema = tLPInsuredBL.getSchema();
			// 更新保全相关信息
			tLPInsuredSchema.setMarriage(mLPInsuredSchema.getMarriage());
			tLPInsuredSchema.setName(mLPInsuredSchema.getName());
			tLPInsuredSchema.setNativePlace(mLPInsuredSchema.getNativePlace());
			tLPInsuredSchema.setRgtAddress(mLPInsuredSchema.getRgtAddress());
			tLPInsuredSchema.setSocialInsuFlag(mLPInsuredSchema.getSocialInsuFlag());
			tLPInsuredSchema.setLanguage(mLPInsuredSchema.getLanguage());
			tLPInsuredSchema.setOperator(this.mGlobalInput.Operator);
			tLPInsuredSchema.setModifyDate(strCurrentDate);
			tLPInsuredSchema.setModifyTime(strCurrentTime);
			mMap.put(tLPInsuredSchema, "DELETE&INSERT");
			//tLPContSchema.setInsuredName(mLPInsuredSchema.getName());
			
//			gLPPersonSchema.setMarriage(mLPInsuredSchema.getMarriage());
//			gLPPersonSchema.setName(mLPInsuredSchema.getName());
//			gLPPersonSchema.setNativePlace(mLPInsuredSchema.getNativePlace());
//			gLPPersonSchema.setRgtAddress(mLPInsuredSchema.getRgtAddress());
//			gLPPersonSchema.setOperator(this.mGlobalInput.Operator);
//			gLPPersonSchema.setModifyDate(strCurrentDate);
//			gLPPersonSchema.setModifyTime(strCurrentTime);
			
		}

		LPAppntBL tLPAppntBL = new LPAppntBL();
		LPAppntSchema gLPAppntSchema = new LPAppntSchema();

		if ("0".equals(tAppntIsInsuredFlag)) {
			gLPAppntSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
			gLPAppntSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
			gLPAppntSchema.setContNo(this.mLPEdorItemSchema.getContNo());
			gLPAppntSchema.setAppntNo(this.mLPAppntSchema.getAppntNo());
			if (!tLPAppntBL.queryLastLPAppnt(this.mLPEdorItemSchema,
					gLPAppntSchema)) {
				return false;
			}
			LPAppntSchema tLPAppntSchema = new LPAppntSchema();
			tLPAppntSchema.setSchema(tLPAppntBL.getSchema());
			tLPAppntSchema.setMarriage(mLPAppntSchema.getMarriage());
			tLPAppntSchema.setAppntName(mLPAppntSchema.getAppntName());
			tLPAppntSchema.setNativePlace(mLPAppntSchema.getNativePlace());
			tLPAppntSchema.setRgtAddress(mLPAppntSchema.getRgtAddress());
			tLPAppntSchema.setLanguage(mLPAppntSchema.getLanguage());
			tLPAppntSchema.setOperator(this.mGlobalInput.Operator);
			tLPAppntSchema.setModifyDate(strCurrentDate);
			tLPAppntSchema.setModifyTime(strCurrentTime);
			mMap.put(tLPAppntSchema, "DELETE&INSERT");

			//tLPContSchema.setAppntName(mLPAppntSchema.getAppntName());
			
//			gLPPersonSchema.setMarriage(mLPAppntSchema.getMarriage());
//			gLPPersonSchema.setName(mLPAppntSchema.getAppntName());
//			gLPPersonSchema.setNativePlace(mLPAppntSchema.getNativePlace());
//			gLPPersonSchema.setRgtAddress(mLPAppntSchema.getRgtAddress());
//			gLPPersonSchema.setOperator(this.mGlobalInput.Operator);
//			gLPPersonSchema.setModifyDate(strCurrentDate);
//			gLPPersonSchema.setModifyTime(strCurrentTime);
		}

		//tLPContSchema.setInsuredName(mLPInsuredSchema.getName());
		//tLPContSchema.setModifyDate(strCurrentDate);
		//tLPContSchema.setModifyTime(strCurrentTime);
		if ("2".equals(tAppntIsInsuredFlag))

		{
			if (!tLPInsuredBL.queryLastLPInsured(this.mLPEdorItemSchema,
					this.mLPInsuredSchema)) {
				return false;
			}
			LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
			tLPInsuredSchema = tLPInsuredBL.getSchema();
			// 更新保全相关信息
			tLPInsuredSchema.setMarriage(mLPInsuredSchema.getMarriage());
			tLPInsuredSchema.setName(mLPInsuredSchema.getName());
			tLPInsuredSchema.setNativePlace(mLPInsuredSchema.getNativePlace());
			tLPInsuredSchema.setRgtAddress(mLPInsuredSchema.getRgtAddress());
			tLPInsuredSchema.setSocialInsuFlag(mLPInsuredSchema.getSocialInsuFlag());
			tLPInsuredSchema.setLanguage(mLPInsuredSchema.getLanguage());
			tLPInsuredSchema.setOperator(this.mGlobalInput.Operator);
			tLPInsuredSchema.setModifyDate(strCurrentDate);
			tLPInsuredSchema.setModifyTime(strCurrentTime);
			mMap.put(tLPInsuredSchema, "DELETE&INSERT");
			//tLPContSchema.setInsuredName(mLPInsuredSchema.getName());

			gLPAppntSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
			gLPAppntSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
			gLPAppntSchema.setContNo(this.mLPEdorItemSchema.getContNo());
			gLPAppntSchema.setAppntNo(this.mLPAppntSchema.getAppntNo());
			if (!tLPAppntBL.queryLastLPAppnt(this.mLPEdorItemSchema,
					gLPAppntSchema)) {
				return false;
			}
			LPAppntSchema tLPAppntSchema = new LPAppntSchema();
			tLPAppntSchema.setSchema(tLPAppntBL.getSchema());
			tLPAppntSchema.setMarriage(mLPAppntSchema.getMarriage());
			//tLPAppntSchema.setAppntName(mLPAppntSchema.getAppntName());
			tLPAppntSchema.setNativePlace(mLPAppntSchema.getNativePlace());
			tLPAppntSchema.setRgtAddress(mLPAppntSchema.getRgtAddress());
			tLPAppntSchema.setSocialInsuFlag(mLPInsuredSchema.getSocialInsuFlag());
			tLPAppntSchema.setLanguage(mLPAppntSchema.getLanguage());
			tLPAppntSchema.setOperator(this.mGlobalInput.Operator);
			tLPAppntSchema.setModifyDate(strCurrentDate);
			tLPAppntSchema.setModifyTime(strCurrentTime);
			mMap.put(tLPAppntSchema, "DELETE&INSERT");
			//tLPContSchema.setAppntName(mLPInsuredSchema.getName());
			//tLPContSchema.setInsuredName(mLPInsuredSchema.getName());
			
			
//			gLPPersonSchema.setMarriage(mLPAppntSchema.getMarriage());
//			gLPPersonSchema.setName(mLPAppntSchema.getAppntName());
//			gLPPersonSchema.setNativePlace(mLPAppntSchema.getNativePlace());
//			gLPPersonSchema.setRgtAddress(mLPAppntSchema.getRgtAddress());
//			gLPPersonSchema.setOperator(this.mGlobalInput.Operator);
//			gLPPersonSchema.setModifyDate(strCurrentDate);
//			gLPPersonSchema.setModifyTime(strCurrentTime);

		}

		
//		LPPolSet tLPPolSet = new LPPolSet();
//		LPPolDB tLPPolDB = new LPPolDB();
//		LCPolSet tLCPolSet = new LCPolSet();
//		LCPolDB tLCPolDB = new LCPolDB();
//		Reflections tRef = new Reflections();
//		tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
//		tLCPolSet = tLCPolDB.query();
//		if (tLCPolDB.mErrors.needDealError()) {
//			CError.buildErr(this, "保单险种信息失败!");
//			return false;
//		}
//		if (tLCPolSet.size() > 0 || tLCPolSet != null) {
//			for (int i = 1; i <= tLCPolSet.size(); i++) {
//				LPPolSchema tLPPolSchema = new LPPolSchema();
//				tRef.transFields(tLPPolSchema, tLCPolSet.get(i));
//				if ("1".equals(tAppntIsInsuredFlag)) {
//					tLPPolSchema.setInsuredName(mLPInsuredSchema.getName());
//				}
//				if ("0".equals(tAppntIsInsuredFlag)) {
//					tLPPolSchema.setAppntName(mLPAppntSchema.getAppntName());
//				}
//                // 如果投保人和背包人是同一个人
//				if ("2".equals(tAppntIsInsuredFlag))
//				{
//					tLPPolSchema.setAppntName(mLPAppntSchema.getAppntName());
//					tLPPolSchema.setInsuredName(mLPInsuredSchema.getName());
//				}
//				tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//				tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//				tLPPolSchema.setOperator(this.mGlobalInput.Operator);
//				tLPPolSchema.setModifyDate(strCurrentDate);
//				tLPPolSchema.setModifyTime(strCurrentTime);
//				tLPPolSet.add(tLPPolSchema);
//			}
//			mMap.put(tLPPolSet, "DELETE&INSERT");
//		}

		//mMap.put(tLPContSchema, "DELETE&INSERT");
//		mMap.put(gLPPersonSchema, "DELETE&INSERT");
		// 修改“个险保全项目表”相应信息
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
		mLPEdorItemSchema.setStandbyFlag1(tAppntIsInsuredFlag);
		mLPEdorItemSchema.setModifyDate(strCurrentDate);
		mLPEdorItemSchema.setModifyTime(strCurrentTime);
		mMap.put(mLPEdorItemSchema, "UPDATE");

		mResult.clear();
		mResult.add(mMap);
		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		mBqCalBase.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		mResult.add(mBqCalBase);

		return true;
	}

}
