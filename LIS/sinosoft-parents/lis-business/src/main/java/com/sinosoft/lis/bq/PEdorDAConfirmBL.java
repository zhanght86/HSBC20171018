package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPCustomerImpartDB;
import com.sinosoft.lis.db.LPCustomerImpartParamsDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
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
public class PEdorDAConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorDAConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorDAConfirmBL() {
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
		logger.debug("-------DA Confirm----");

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

		this.setOperate("CONFIRM||DA");

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
		return true;
	}

	private boolean prepareData() {
		// C表
		LCPolSet aLCPolSet = new LCPolSet();
		LCDutySet aLCDutySet = new LCDutySet();
		LCPremSet aLCPremSet = new LCPremSet();
		LCGetSet aLCGetSet = new LCGetSet();
		LCCustomerImpartSet aLCCustomerImpartSet = new LCCustomerImpartSet();
		LCCustomerImpartParamsSet aLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();

		// P表
		LPPolSet aLPPolSet = new LPPolSet();
		LPDutySet aLPDutySet = new LPDutySet();
		LPPremSet aLPPremSet = new LPPremSet();
		LPGetSet aLPGetSet = new LPGetSet();
		LPCustomerImpartSet aLPCustomerImpartSet = new LPCustomerImpartSet();
		LPCustomerImpartParamsSet aLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();

		Reflections tRef = new Reflections();

		// 险种表处理
		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet.size() < 1) {
			CError tError = new CError();
			tError.buildErr(this, "查询险种表出错!");
			return false;
		}
		LCPolSchema aLCPolSchema = new LCPolSchema();
		LPPolSchema aLPPolSchema;
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			aLCPolSchema = new LCPolSchema();
			aLPPolSchema = tLPPolSet.get(i);
			tRef.transFields(aLCPolSchema, aLPPolSchema);
			aLCPolSchema.setModifyDate(PubFun.getCurrentDate());
			aLCPolSchema.setModifyTime(PubFun.getCurrentTime());
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(aLPPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.copyAllErrors(tLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询险种保单表失败！"));
				return false;
			}
			tRef.transFields(aLPPolSchema, tLCPolDB.getSchema());
			aLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			aLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			aLPPolSet.add(aLPPolSchema);
			aLCPolSet.add(aLCPolSchema);
		}
		mMap.put(aLPPolSet, "DELETE&INSERT");
		mMap.put(aLCPolSet, "DELETE&INSERT");

		// 险种责任表
		LPDutyDB tLPDutyDB = new LPDutyDB();
		LPDutySchema tLPDutySchema = new LPDutySchema();
		LPDutySet tLPDutySet = new LPDutySet();
		tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPDutyDB.setDutyCode("607001");
		tLPDutySet = tLPDutyDB.query();
		if (tLPDutySet.size() < 1) {
			CError tError = new CError();
			tError.buildErr(this, "查询险种责任表失败!");
			return false;
		}
		for (int i = 1; i <= tLPDutySet.size(); i++) {
			LCDutySchema aLCDutySchema = new LCDutySchema();
			LPDutySchema aLPDutySchema = new LPDutySchema();
			aLPDutySchema = tLPDutySet.get(i);
			tRef.transFields(aLCDutySchema, aLPDutySchema);
			aLCDutySchema.setModifyDate(PubFun.getCurrentDate());
			aLCDutySchema.setModifyTime(PubFun.getCurrentTime());
			aLCDutySet.add(aLCDutySchema); // 只往C表中插数据
		}
		mMap.put(aLCDutySet, "DELETE&INSERT");

		// 保费项目表
		LPPremDB tLPPremDB = new LPPremDB();
		LPPremSchema tLPPremSchema = new LPPremSchema();
		LPPremSet tLPPremSet = new LPPremSet();
		tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPremDB.setDutyCode("607001");
		tLPPremSet = tLPPremDB.query();
		if (tLPPremSet.size() < 1) {
			CError tError = new CError();
			tError.buildErr(this, "查询保费项目表失败!");
			return false;
		}

		for (int i = 1; i <= tLPPremSet.size(); i++) {
			LCPremSchema aLCPremSchema = new LCPremSchema();
			LPPremSchema aLPPremSchema = new LPPremSchema();
			aLPPremSchema = tLPPremSet.get(i);
			tRef.transFields(aLCPremSchema, aLPPremSchema);
			aLCPremSchema.setModifyDate(PubFun.getCurrentDate());
			aLCPremSchema.setModifyTime(PubFun.getCurrentTime());
			aLCPremSet.add(aLCPremSchema); // 只往C表中插数据

			// 续期处理
			if (aLCPremSchema.getPayPlanType().trim().equals("0")) {
				LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
				LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
				tLJSPayPersonDB.setPolNo(aLCPremSchema.getPolNo());
				tLJSPayPersonDB.setPayPlanCode(aLCPremSchema.getPayPlanCode());
				tLJSPayPersonSet = tLJSPayPersonDB.query();
				if (tLJSPayPersonSet != null && tLJSPayPersonSet.size() > 0) {
					double aPrem = tLJSPayPersonSet.get(1).getSumActuPayMoney();
			          //营改增 add zhangyingfeng 2016-07-14
			          //借鉴上面取值  取 净额 税额  税率
					double aNetAm = tLJSPayPersonSet.get(1).getNetAmount();
					double aTaxAm = tLJSPayPersonSet.get(1).getTaxAmount();
					double aTax = tLJSPayPersonSet.get(1).getTax();
			          //end zhangyingfeng 2016-07-14
					tLJSPayPersonSet.get(1).setSumActuPayMoney(
							aLCPremSchema.getPrem());
					tLJSPayPersonSet.get(1).setSumDuePayMoney(
							aLCPremSchema.getPrem());
					tLJSPayPersonSet.get(1).setModifyDate(
							PubFun.getCurrentDate());
					tLJSPayPersonSet.get(1).setModifyTime(
							PubFun.getCurrentTime());
			          //营改增 add zhangyingfeng 2016-07-14
			          //价税分离 计算器
			          TaxCalculator.calBySchema(tLJSPayPersonSet.get(1));
			          //end zhangyingfeng 2016-07-14
					mMap.put(tLJSPayPersonSet, "DELETE&INSERT");
					LJSPayDB tLJSPayDB = new LJSPayDB();
					LJSPaySet tLJSPaySet = new LJSPaySet();
					tLJSPayDB.setOtherNo(mLPEdorItemSchema.getContNo());
					tLJSPaySet = tLJSPayDB.query();
					if (tLJSPaySet != null && tLJSPaySet.size() > 0) {
						tLJSPaySet.get(1).setSumDuePayMoney(
								tLJSPaySet.get(1).getSumDuePayMoney() - aPrem
										+ aLCPremSchema.getPrem());
						tLJSPaySet.get(1)
								.setModifyDate(PubFun.getCurrentDate());
						tLJSPaySet.get(1)
								.setModifyTime(PubFun.getCurrentTime());
				          //营改增 add zhangyingfeng 2016-07-14
				          //借鉴上面取值  从tLJSPayPersonSet取净额 税额  税率不变    tLJSPayPersonSet.get(1).setSumActuPayMoney(aLCPremSchema.getPrem());
						tLJSPaySet.get(1).setNetAmount(tLJSPaySet.get(1).getNetAmount()-aNetAm+tLJSPayPersonSet.get(1).getNetAmount());
						tLJSPaySet.get(1).setTaxAmount(tLJSPaySet.get(1).getTaxAmount()-aTaxAm+tLJSPayPersonSet.get(1).getTaxAmount());
				          //end zhangyingfeng 2016-07-14
						mMap.put(tLJSPaySet, "DELETE&INSERT");
					}
				}
			}
		}
		mMap.put(aLCPremSet, "DELETE&INSERT");

		// 领取项目表
		LPGetDB tLPGetDB = new LPGetDB();
		LPGetSchema tLPGetSchema = new LPGetSchema();
		LPGetSet tLPGetSet = new LPGetSet();
		tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPGetDB.setDutyCode("607001");
		tLPGetSet = tLPGetDB.query();
		if (tLPGetSet.size() < 1) {
			CError tError = new CError();
			tError.buildErr(this, "查询领取项目表失败!");
			return false;
		}
		for (int i = 1; i <= tLPGetSet.size(); i++) {
			LCGetSchema aLCGetSchema = new LCGetSchema();
			LPGetSchema aLPGetSchema = new LPGetSchema();
			aLPGetSchema = tLPGetSet.get(i);
			tRef.transFields(aLCGetSchema, aLPGetSchema);
			aLCGetSchema.setModifyDate(PubFun.getCurrentDate());
			aLCGetSchema.setModifyTime(PubFun.getCurrentTime());
			aLCGetSet.add(aLCGetSchema); // 只往C表中插数据
		}
		mMap.put(aLCGetSet, "DELETE&INSERT");

		// 健康告知
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
				tLCCustomerImpartSchema.setModifyDate(PubFun.getCurrentDate());
				tLCCustomerImpartSchema.setModifyTime(PubFun.getCurrentTime());
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

		LPContSchema aLPContSchema = new LPContSchema();
		LCContSchema aLCContSchema = new LCContSchema();
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			mErrors.addOneError("查询批改保单信息失败!");
			return false;
		}
		tRef.transFields(aLCContSchema, tLPContDB.getSchema());
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		tRef.transFields(aLPContSchema, tLCContDB.getSchema());
		aLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		mMap.put(aLCContSchema, "UPDATE");
		mMap.put(aLPContSchema, "UPDATE");

		mLPEdorItemSchema.setEdorState("0");
		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(mLPEdorItemSchema, "UPDATE");

		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	public static void main(String[] args) {
		PEdorDAConfirmBL pedordaconfirmbl = new PEdorDAConfirmBL();
	}
}
