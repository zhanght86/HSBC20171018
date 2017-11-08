package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
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
public class PEdorDADetailBL {
private static Logger logger = Logger.getLogger(PEdorDADetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	private BqCalBase mBqCalBase = new BqCalBase();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局变量 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private Reflections mRef = new Reflections();
	private LPPolSet mLPPolSet = new LPPolSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private String StartDate = "";
	private LPCustomerImpartSet mLPCustomerImpartSet = new LPCustomerImpartSet();
	private LPCustomerImpartParamsSet mLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();

	public PEdorDADetailBL() {
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
			mLCCustomerImpartSet = (LCCustomerImpartSet) mInputData
					.getObjectByObjectName("LCCustomerImpartSet", 0);
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
		mLCContSchema = tLCContDB.getSchema();

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLCPolDB.getInfo()) {
			mErrors.addOneError("查询保单险种失败!");
			return false;
		}
		mLCPolSchema.setSchema(tLCPolDB.getSchema());

		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		if (!mLCPolSchema.getRiskCode().trim().equals("00607000")) {
			mErrors.addOneError("非607险种不能进行增加附加特约责任!");
			return false;
		}
		if (mLCPolSchema.getPayIntv() != 12) {
			mErrors.addOneError("非607年交险种不能进行增加附加特约责任!");
			return false;
		}

		LCDutyDB tLCDutyDB = new LCDutyDB();
		LCDutySet tLCDutySet = new LCDutySet();
		tLCDutyDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLCDutyDB.setDutyCode("607001");
		tLCDutySet = tLCDutyDB.query();
		if (tLCDutySet != null && tLCDutySet.size() > 0) {
			mErrors.addOneError("此607年交险种已含附加特约责任，不能进行增加附加特约责任!");
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据 调用个人保全投保人信息变更业务逻辑处理类PEdorACBL进行处理
	 */
	private boolean dealData() {
		LPPolSchema aLPPolSchema = new LPPolSchema();

		LCPolBL aLCPolBL = new LCPolBL();
		aLCPolBL.setSchema(mLCPolSchema);
		mRef.transFields(aLPPolSchema, mLCPolSchema);
		aLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());

		LCDutyBLSet aLCDutyBLSet = new LCDutyBLSet();
		// 对保费项进行计算，包括已终止效力的保费项
		ReCalBL tReCalBL = new ReCalBL(aLPPolSchema, mLPEdorItemSchema);
		aLCDutyBLSet = tReCalBL.getRecalDuty(mLPEdorItemSchema);
		LCDutySchema aLCDutySchema = new LCDutySchema();
		aLCDutySchema.setSchema(aLCDutyBLSet.get(1));
		aLCDutySchema.setDutyCode("607001");
		// StartDate = PubFun.calDate(aLCDutySchema.getPaytoDate(), 1, "Y",
		// null);
		StartDate = aLCDutySchema.getPaytoDate();
		aLCDutySchema.setCValiDate(StartDate);
		aLCDutySchema.setGetStartDate(StartDate);
		aLCDutySchema.setMakeDate(PubFun.getCurrentDate());
		aLCDutySchema.setMakeTime(PubFun.getCurrentTime());
		aLCDutySchema.setModifyDate(PubFun.getCurrentDate());
		aLCDutySchema.setModifyTime(PubFun.getCurrentTime());
		aLCDutyBLSet.add(aLCDutySchema);

		// 准备重算需要的保费项表数据
		LCPremSet aLCPermSet = tReCalBL.getRecalPrem(mLPEdorItemSchema);

		// 准备重算需要的领取项表数据
		LCGetBLSet aLCGetBLSet = tReCalBL.getRecalGet(mLPEdorItemSchema);

		// 重算
		if (!tReCalBL.recalWithData(aLCPolBL, aLCDutyBLSet, aLCPermSet,
				aLCGetBLSet, mLPEdorItemSchema)) {
			mErrors.addOneError("重算失败!");
			return false;
		}
		mLPPolSet = tReCalBL.aftLPPolSet;
		mLPPolSet.get(1).setAppFlag("1");
		mLPDutySet = tReCalBL.aftLPDutySet;
		mLPPremSet = tReCalBL.aftLPPremSet;
		mLPGetSet = tReCalBL.aftLPGetSet;

		mLPPolSet.get(1).setSignCom(mLCPolSchema.getSignCom());
		mLPPolSet.get(1).setSignDate(mLCPolSchema.getSignDate());
		mLPPolSet.get(1).setSignTime(mLCPolSchema.getSignTime());
		mLPPolSet.get(1).setYears(mLCPolSchema.getYears());
		mLPPolSet.get(1).setEndDate(mLCPolSchema.getEndDate());
		mLPPolSet.get(1).setPaytoDate(mLCPolSchema.getPaytoDate());

		for (int a = 1; a <= mLPDutySet.size(); a++) {
			if (mLPDutySet.get(a).getDutyCode().trim().equals("607001")) {
				mLPDutySet.get(a).setCValiDate(StartDate);
				mLPDutySet.get(a).setGetStartDate(StartDate);
				mLPDutySet.get(a).setPaytoDate(StartDate);
				mLPDutySet.get(a).setYears(mLCPolSchema.getYears());
				mLPDutySet.get(a).setEndDate(mLCPolSchema.getEndDate());
				break;
			}
		}
		for (int a = 1; a <= mLPPremSet.size(); a++) {
			if (mLPPremSet.get(a).getDutyCode().trim().equals("607001")) {
				mLPPremSet.get(a).setPayStartDate(StartDate);
				mLPPremSet.get(a).setPaytoDate(StartDate);
				break;
			}
		}
		for (int a = 1; a <= mLPGetSet.size(); a++) {
			if (mLPGetSet.get(a).getDutyCode().trim().equals("607001")) {
				mLPGetSet.get(a).setGetStartDate(StartDate);
				mLPGetSet.get(a).setGettoDate(StartDate);
				mLPGetSet.get(a).setGetEndDate(mLCPolSchema.getEndDate());
				break;
			}
		}

		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			LCCustomerImpartSchema aLCCustomerImpartSchema = new LCCustomerImpartSchema();
			for (int k = 1; k <= mLCCustomerImpartSet.size(); k++) {
				aLCCustomerImpartSchema = mLCCustomerImpartSet.get(k);
				aLCCustomerImpartSchema.setGrpContNo(mLCContSchema
						.getGrpContNo());
				if (mLCContSchema.getGrpContNo() == null) {
					aLCCustomerImpartSchema
							.setGrpContNo("00000000000000000000");
				}
				aLCCustomerImpartSchema.setPrtNo(mLCContSchema.getPrtNo());
				if (mLCContSchema.getPrtNo() == null) {
					mErrors.addOneError("个人保单印刷号码查询失败!");
					return false;
				}
				logger.debug("===================== PrtNo ==========="
						+ mLCContSchema.getPrtNo());
				aLCCustomerImpartSchema.setCustomerNo(mLCContSchema
						.getAppntNo());
				aLCCustomerImpartSchema.setCustomerNoType("A");
				mLCCustomerImpartSet.set(k, aLCCustomerImpartSchema);
				logger.debug(mLCCustomerImpartSet.get(k).getCustomerNo());
			}
		}

		if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
			logger.debug("654654987356");
			VData cVData = new VData();
			cVData.add(mLCCustomerImpartSet);
			cVData.add(mGlobalInput);
			CustomerImpartBL tCustomerImpartBL = new CustomerImpartBL();
			tCustomerImpartBL.submitData(cVData, "IMPART||DEAL");
			mErrors.copyAllErrors(tCustomerImpartBL.mErrors);
			if (tCustomerImpartBL.mErrors.needDealError()) {

				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "dealData";
				tError.errorMessage = tCustomerImpartBL.mErrors.getFirstError()
						.toString();
				this.mErrors.addOneError(tError);
				return false;
			}
			VData tempVData = new VData();
			tempVData = tCustomerImpartBL.getResult();
			LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
			LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
			try {
				tLCCustomerImpartSet = (LCCustomerImpartSet) tempVData
						.getObjectByObjectName("LCCustomerImpartSet", 0);
				tLCCustomerImpartParamsSet = (LCCustomerImpartParamsSet) tempVData
						.getObjectByObjectName("LCCustomerImpartParamsSet", 0);
			} catch (Exception e) {
				CError.buildErr(this, "接受数据失败!");
				return false;
			}
			Reflections tRef = new Reflections();
			LPCustomerImpartParamsSchema tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
			LPCustomerImpartSchema tLPCustomerImpartSchema = new LPCustomerImpartSchema();
			for (int i = 1; i <= tLCCustomerImpartSet.size(); i++) {
				tLPCustomerImpartSchema = new LPCustomerImpartSchema();
				tRef.transFields(tLPCustomerImpartSchema, tLCCustomerImpartSet
						.get(i));
				tLPCustomerImpartSchema
						.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPCustomerImpartSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				tLPCustomerImpartSchema.setGrpContNo("00000000000000000000");
				tLPCustomerImpartSchema.setPrtNo(mLCContSchema.getPrtNo());
				if (mLCContSchema.getPrtNo() == null) {
					mErrors.addOneError("个人保单印刷号码查询失败!");
					return false;
				}
				mLPCustomerImpartSet.add(tLPCustomerImpartSchema);
			}

			for (int i = 1; i <= tLCCustomerImpartParamsSet.size(); i++) {
				tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
				tRef.transFields(tLPCustomerImpartParamsSchema,
						tLCCustomerImpartParamsSet.get(i));

				tLPCustomerImpartParamsSchema.setEdorNo(mLPEdorItemSchema
						.getEdorNo());
				tLPCustomerImpartParamsSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				tLPCustomerImpartParamsSchema
						.setGrpContNo("00000000000000000000");
				tLPCustomerImpartParamsSchema
						.setPrtNo(mLCContSchema.getPrtNo());
				if (mLCContSchema.getPrtNo() == null) {
					mErrors.addOneError("个人保单印刷号码查询失败!");
					return false;
				}
				mLPCustomerImpartParamsSet.add(tLPCustomerImpartParamsSchema);
			}

			mMap.put(mLPCustomerImpartSet, "DELETE&INSERT");
			mMap.put(mLPCustomerImpartParamsSet, "DELETE&INSERT");

		}

		LPContSchema tLPContSchema = new LPContSchema();
		mRef.transFields(tLPContSchema, mLCContSchema);
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setPrem(tLPContSchema.getPrem() - mLCPolSchema.getPrem()
				+ mLPPolSet.get(1).getPrem());

		mMap.put(tLPContSchema, "DELETE&INSERT");
		mMap.put(mLPPolSet, "DELETE&INSERT");
		mMap.put(mLPDutySet, "DELETE&INSERT");
		mMap.put(mLPPremSet, "DELETE&INSERT");
		mMap.put(mLPGetSet, "DELETE&INSERT");
		mResult.clear();
		mBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());
		mBqCalBase.setPolNo(mLPEdorItemSchema.getPolNo());
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
