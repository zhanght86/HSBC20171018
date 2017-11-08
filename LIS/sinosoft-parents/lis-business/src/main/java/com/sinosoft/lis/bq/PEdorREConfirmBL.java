package com.sinosoft.lis.bq;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPContStateDB;
import com.sinosoft.lis.db.LPCustomerImpartDB;
import com.sinosoft.lis.db.LPCustomerImpartParamsDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.db.LPReturnLoanDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LOReturnLoanSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.schema.LPReturnLoanSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LOReturnLoanSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.lis.vschema.LPReturnLoanSet;
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
 * @ReWrite Lizhuo
 * @version 1.0
 */
public class PEdorREConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorREConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LCContSchema mLCContSchema = new LCContSchema();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	
	private ValidateEdorData2 mValidateEdorData = null;
	
	private String mEdorNo = null;
	private String mEdorType = null;
	private String mContNo = null;

	private MMap mMap = new MMap();

	public PEdorREConfirmBL() {
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
		if (!getInputData()) {
			return false;
		}

		// 数据业务处理(queryData())
		if (!dealData()) {
			return false;
		}

		// 数据准备操作（preparedata())
		// if (!prepareData()) {
		// return false;
		// }

		this.setOperate("CONFIRM||RE");
		logger.debug("---" + mOperate);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
			CError.buildErr(this, "查询批改项目信息失败！");
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		
	    mEdorNo = mLPEdorItemSchema.getEdorNo();
	    mEdorType = mLPEdorItemSchema.getEdorType();
	    mContNo = mLPEdorItemSchema.getContNo();
	    mValidateEdorData = new ValidateEdorData2(mGlobalInput, mEdorNo,mEdorType, mContNo, "ContNo");
		// mLimit = PubFun.getNoLimit(mLPEdorItemSchema.getManageCom());
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		logger.debug("PEdorREConfirmBL in submitData");	
		
	    String[] chgTables = {"LCAppnt","LCInsured"};
	    mValidateEdorData.changeData(chgTables);
	    mMap.add(mValidateEdorData.getMap());
		
	    //处理特约
	    mMap.add(BqNameFun.DealSpecData(mLPEdorItemSchema));
		// 处理应付表信息
		String i_sql = "select * from LCPol where ContNo = '?ContNo?' and (payintv <> 0 or payintv = 0 and not exists (select 1  from lmrisk "
				+ " where riskcode = lcpol.riskcode and rnewflag = 'Y')) and payintv <> -1";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(i_sql);
		sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
		LCPolDB aLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = aLCPolDB.executeQuery(sqlbv);
		LCPolSchema tLCPolSchema = new LCPolSchema();
		if (tLCPolSet.size() < 1) {
			CError.buildErr(this, "查询险种表信息失败!");
			return false;
		}
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			tLCPolSchema = tLCPolSet.get(i);
			//comment by jiaqiangli 2008-10-22 下面的for循环处理mMap.put会抛出异常的。
//			// 删除顺序 先删分表再删总表
//			String delLJSPayPerson = " delete from ljspayperson where getnoticeno in (select getnoticeno from ljspay where othernotype in ('2','3') and otherno = '"
//					+ tLCPolSchema.getContNo() + "' )";
//			String delLJSPay = " delete from ljspay where othernotype in ('2','3') and otherno = '"
//					+ tLCPolSchema.getContNo() + "'";
//			mMap.put(delLJSPayPerson, "DELETE");
//			mMap.put(delLJSPay, "DELETE");
		}

		// P表与C表互换
		Reflections tRef = new Reflections();
		
		/* LCPol <---> LPPol（得到投保人资料信息表p，c 表 ） */
		LPPolSet tLPPolSet = new LPPolSet();
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());

		String strSql = "select * from LPPol where EdorNo = '?EdorNo?' and EdorType = '?EdorType?'";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(strSql);
		sbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sbv.put("EdorType", mLPEdorItemSchema.getEdorType());

		tLPPolSet = tLPPolDB.executeQuery(sbv);
		LPPolSet aLPPolSet = new LPPolSet();
		LCPolSet aLCPolSet = new LCPolSet();
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			LPPolSchema tLPPolSchema = new LPPolSchema();
			LCPolSchema tpLCPolSchema = new LCPolSchema();
			tRef.transFields(tpLCPolSchema, tLPPolSet.get(i));
			tpLCPolSchema.setModifyTime(PubFun.getCurrentTime());
			tpLCPolSchema.setModifyDate(PubFun.getCurrentDate());

			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tpLCPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.copyAllErrors(tLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询险种保单表失败！"));
				return false;
			}

			tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolSchema.setModifyTime(PubFun.getCurrentTime());
			tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
			aLPPolSet.add(tLPPolSchema);
			aLCPolSet.add(tpLCPolSchema);

		}
		mMap.put(aLPPolSet, "UPDATE");
		mMap.put(aLCPolSet, "UPDATE");
		
		// 险种责任表 lcduty lpduty
		LPDutyDB tLPDutyDB = new LPDutyDB();
		LPDutySchema tLPDutySchema = new LPDutySchema();
		LPDutySet tLPDutySet = new LPDutySet();
		tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPDutyDB.setSchema(tLPDutySchema);
		tLPDutySet = tLPDutyDB.query();
//		if (tLPDutySet.size() < 1) {
//			CError.buildErr(this, "查询险种责任表失败!");
//			return false;
//		}
        
		LCDutySet aLCDutySet = new LCDutySet();
		LPDutySet aLPDutySet = new LPDutySet();
		
		for (int i = 1; i <= tLPDutySet.size(); i++) {
			LCDutySchema aLCDutySchema = new LCDutySchema();
			LPDutySchema aLPDutySchema = new LPDutySchema();
			aLPDutySchema = tLPDutySet.get(i);
			tRef.transFields(aLCDutySchema, aLPDutySchema);
			aLCDutySchema.setModifyDate(PubFun.getCurrentDate());
			aLCDutySchema.setModifyTime(PubFun.getCurrentTime());
			 LCDutyDB tLCDutyDB = new LCDutyDB();
			 tLCDutyDB.setPolNo(aLPDutySchema.getPolNo());
			 tLCDutyDB.setDutyCode(aLPDutySchema.getDutyCode());
			 if (!tLCDutyDB.getInfo()) {
			 mErrors.copyAllErrors(tLCDutyDB.mErrors);
			 mErrors.addOneError(new CError("查询险种保单表失败！"));
			 return false;
			 }
			 tRef.transFields(aLPDutySchema, tLCDutyDB.getSchema());
			 aLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			 aLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
			 aLPDutySet.add(aLPDutySchema);
			 aLCDutySet.add(aLCDutySchema);// 只往C表中插数据
		}
		mMap.put(aLPDutySet,"UPDATE");
		mMap.put(aLCDutySet, "DELETE&INSERT");

		// 保费项目表 lcprem - lpprem
		LPPremDB tLPPremDB = new LPPremDB();
		LPPremSchema tLPPremSchema = new LPPremSchema();
		LPPremSet tLPPremSet = new LPPremSet();
		tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPremDB.setSchema(tLPPremSchema);
		tLPPremSet = tLPPremDB.query();
//		if (tLPPremSet.size() < 1) {
//			CError.buildErr(this, "查询保费项目表失败!");
//			return false;
//		}
		
		LCPremSet aLCPremSet = new LCPremSet();
		LPPremSet aLPPremSet = new LPPremSet();
		for (int i = 1; i <= tLPPremSet.size(); i++) {
			LCPremSchema aLCPremSchema = new LCPremSchema();
			LPPremSchema aLPPremSchema = new LPPremSchema();
			aLPPremSchema = tLPPremSet.get(i);
			tRef.transFields(aLCPremSchema, aLPPremSchema);
			aLCPremSchema.setModifyDate(PubFun.getCurrentDate());
			aLCPremSchema.setModifyTime(PubFun.getCurrentTime());
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
				tRef.transFields(aLPPremSchema, tLCPremDB.getSchema());
				aLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				aLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				aLPPremSet.add(aLPPremSchema);
			}
			 
			 aLCPremSet.add(aLCPremSchema);// 只往C表中插数据
		}
		mMap.put(aLPPremSet,"UPDATE");
		mMap.put(aLCPremSet, "DELETE&INSERT");
		
		
		// 领取项表 lcget - lpget
		LPGetDB tLPGetDB = new LPGetDB();
		LPGetSchema tLPGetSchema = new LPGetSchema();
		LPGetSet tLPGetSet = new LPGetSet();
		tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPGetDB.setSchema(tLPGetSchema);
		tLPGetSet = tLPGetDB.query();
//		if (tLPGetSet.size() < 1) {
//			CError.buildErr(this, "查询保费项目表失败!");
//			return false;
//		}
		LCGetSet aLCGetSet = new LCGetSet();
		LPGetSet aLPGetSet = new LPGetSet();
		for (int i = 1; i <= tLPGetSet.size(); i++) {
			LCGetSchema aLCGetSchema = new LCGetSchema();
			LPGetSchema aLPGetSchema = new LPGetSchema();
			aLPGetSchema = tLPGetSet.get(i);
			tRef.transFields(aLCGetSchema, aLPGetSchema);
			aLCGetSchema.setModifyDate(PubFun.getCurrentDate());
			aLCGetSchema.setModifyTime(PubFun.getCurrentTime());
			 LCGetDB tLCGetDB = new LCGetDB();
			 tLCGetDB.setPolNo(aLPGetSchema.getPolNo());
			 tLCGetDB.setDutyCode(aLPGetSchema.getDutyCode());
			 tLCGetDB.setGetDutyCode(aLPGetSchema.getGetDutyCode());
			 if (!tLCGetDB.getInfo()) {
			 mErrors.copyAllErrors(tLCGetDB.mErrors);
			 mErrors.addOneError(new CError("查询保费项目表失败！"));
			 return false;
			 }
			 tRef.transFields(aLPGetSchema, tLCGetDB.getSchema());
			 aLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			 aLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			 aLPGetSet.add(aLPGetSchema);
			 aLCGetSet.add(aLCGetSchema);// 只往C表中插数据
		}
		mMap.put(aLPGetSet,"UPDATE");
		mMap.put(aLCGetSet, "DELETE&INSERT");

		// LPCustomerImpartSet aLPCustomerImpartSet = new LPCustomerImpartSet();
		LCCustomerImpartSet aLCCustomerImpartSet = new LCCustomerImpartSet();
		LCContStateSet aLCContStateSet = new LCContStateSet();
		LPContStateSet aLPContStateSet = new LPContStateSet();
		// LPCustomerImpartParamsSet aLPCustomerImpartParamsSet = new
		// LPCustomerImpartParamsSet();
		LCCustomerImpartParamsSet aLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
		LPContSchema aLPContSchema = new LPContSchema();
		LCContSchema aLCContSchema = new LCContSchema();

		// 保单状态处理
		LPContStateDB tLPContStateDB = new LPContStateDB();
		LPContStateSet tLPContStateSet = new LPContStateSet();
		tLPContStateDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContStateDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContStateSet = tLPContStateDB.query();
		if (tLPContStateSet == null || tLPContStateSet.size() < 1) {
			mErrors.addOneError("查询更新保单状态表失败!");
			return false;
		}
		for (int j = 1; j <= tLPContStateSet.size(); j++) {
			LPContStateSchema tLPContStateSchema = new LPContStateSchema();
			LPContStateSchema cLPContStateSchema = new LPContStateSchema();
			LCContStateSchema tLCContStateSchema = new LCContStateSchema();
			tLPContStateSchema = tLPContStateSet.get(j);
			if ((tLPContStateSchema.getStateType().equals("Available")
					|| tLPContStateSchema.getStateType().equals("Loan")
					|| tLPContStateSchema.getStateType().equals("PayPrem"))
					&& tLPContStateSchema.getState().equals("1")) {
				LCContStateDB tLCContStateDB = new LCContStateDB();
				tLCContStateDB.setContNo(tLPContStateSchema.getContNo());
				tLCContStateDB.setInsuredNo(tLPContStateSchema.getInsuredNo());
				tLCContStateDB.setPolNo(tLPContStateSchema.getPolNo());
				tLCContStateDB.setStateType(tLPContStateSchema.getStateType());
				tLCContStateDB.setStartDate(tLPContStateSchema.getStartDate());
				if (!tLCContStateDB.getInfo()) {
					mErrors.addOneError("查询保单状态表失败!");
					return false;
				}
				tRef.transFields(cLPContStateSchema, tLCContStateDB.getSchema());
				cLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				cLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				cLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
				cLPContStateSchema.setModifyTime(PubFun.getCurrentTime());

				tRef.transFields(tLCContStateSchema, tLPContStateSchema);
				tLCContStateSchema.setModifyDate(PubFun.getCurrentDate());
				tLCContStateSchema.setModifyTime(PubFun.getCurrentTime());

				aLCContStateSet.add(tLCContStateSchema);
				aLPContStateSet.add(cLPContStateSchema);
			} 
			else if ((tLPContStateSchema.getStateType().equals("Available")
					|| tLPContStateSchema.getStateType().equals("Loan")
					|| tLPContStateSchema.getStateType().equals("PayPrem"))
					&& tLPContStateSchema.getState().equals("0")) {
				tRef.transFields(tLCContStateSchema, tLPContStateSchema);
				tLCContStateSchema.setModifyDate(PubFun.getCurrentDate());
				tLCContStateSchema.setModifyTime(PubFun.getCurrentTime());
				aLCContStateSet.add(tLCContStateSchema);
				//tLPContStateSchema State().equals("0")这个不删除么
			}
			else {
				mErrors.addOneError("更新保单状态信息不符合规范!");
				return false;
			}
		}
		mMap.put(aLCContStateSet, "DELETE&INSERT");
		mMap.put(aLPContStateSet, "DELETE&INSERT");
		
		//add by jiaqiangli 2008-10-24
		//处理loloan业务表 注意此处的处理方式并非是lploan与loloan的互换 see also in return fund(rf)
		LPReturnLoanDB tLPReturnLoanDB = new LPReturnLoanDB();
		LOLoanSet tLOLoanSet = new LOLoanSet();
		LPReturnLoanSchema tLPReturnLoanSchema = null;
		LPReturnLoanSet tLPReturnLoanSet = new LPReturnLoanSet();
		LOReturnLoanSchema tLOReturnLoanSchema = null;
		LOReturnLoanSet tLOReturnLoanSet = new LOReturnLoanSet();
		tLPReturnLoanDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPReturnLoanDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPReturnLoanSet = tLPReturnLoanDB.query();
		if (tLPReturnLoanSet != null && tLPReturnLoanSet.size() > 0) {
			for (int i = 1; i <= tLPReturnLoanSet.size(); i++) {
				tLPReturnLoanSchema = new LPReturnLoanSchema();
				tLPReturnLoanSchema = tLPReturnLoanSet.get(i);
				tLOReturnLoanSchema = new LOReturnLoanSchema();
				LOLoanSchema tLOLoanSchema = new LOLoanSchema();
				// 转换Schema
				tRef.transFields(tLOReturnLoanSchema,tLPReturnLoanSchema);
				tLOReturnLoanSchema.setOperator(this.mGlobalInput.Operator);
				tLOReturnLoanSchema.setModifyDate(PubFun.getCurrentDate());
				tLOReturnLoanSchema.setModifyTime(PubFun.getCurrentTime());
				tLOReturnLoanSet.add(tLOReturnLoanSchema);

				LOLoanDB rLOLoanDB = new LOLoanDB();
				LOLoanSet rLOLoanSet = new LOLoanSet();
				rLOLoanDB.setEdorNo(tLPReturnLoanSchema.getLoanNo());
				rLOLoanDB.setContNo(tLPReturnLoanSchema.getContNo());
				rLOLoanDB.setPolNo(tLPReturnLoanSchema.getPolNo());
				rLOLoanDB.setOrderNo(tLPReturnLoanSchema.getOrderNo());
				rLOLoanSet = rLOLoanDB.query();
				if (rLOLoanSet.size() < 1 || rLOLoanSet == null) {
					mErrors.addOneError(new CError("查询以往借款数据信息失败!"));
					return false;
				}
				tLOLoanSchema = rLOLoanSet.get(1);
				//还清
				tLOLoanSchema.setLeaveMoney(0);
				tLOLoanSchema.setPayOffDate(tLPReturnLoanSchema.getPayOffDate());
				tLOLoanSchema.setPayOffFlag(tLPReturnLoanSchema.getPayOffFlag());
				tLOLoanSet.add(tLOLoanSchema);
			}
			mMap.put(tLOReturnLoanSet, "DELETE&INSERT");
			mMap.put(tLPReturnLoanSet, "DELETE");
			mMap.put(tLOLoanSet, "UPDATE");
		}
		//add by jiaqiangli 2008-10-24

		// 删除失效通知书打印管理表记录
		String delLOPrtManager = " delete from loprtmanager where code = '42' and otherno = '?otherno?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(delLOPrtManager);
		sbv1.put("otherno", tLCPolSchema.getContNo());
		mMap.put(sbv1, "DELETE");
		
		// 还垫与还款通知书？？？？这个需要统一编码 2008-10-24
		String delPrtSql = "delete from loprtmanager where code in (?code?) and otherno = '?otherno?'";
		ArrayList<String> strArr=new ArrayList<String>();
		strArr.add(PrintManagerBL.CODE_PEdorLoanPay);
		strArr.add(PrintManagerBL.CODE_PEdorLOANPREEND);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(delPrtSql);
		sbv2.put("code", strArr);
		sbv2.put("otherno", mLPEdorItemSchema.getContNo());
	    mMap.put(sbv2, "DELETE");

		//comment by jiaqiangli 2008-10-20 没有信息字段需要修改 仍旧保留支持以后扩充字段
		// ======保费项表C、P 互换=========zhangtao========2005-08-25========END=========
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			mErrors.addOneError("查询批改保单信息失败!");
			return false;
		}
		tRef.transFields(aLCContSchema, tLPContDB.getSchema());
		// aLCContSchema.setPaytoDate(mPaytoDate);
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

		mLCContSchema.setSchema(aLCContSchema);

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
				// LPCustomerImpartSchema tLPCustomerImpartSchema = new
				// LPCustomerImpartSchema();
				tRef.transFields(tLCCustomerImpartSchema, tLPCustomerImpartSet
						.get(i));
				tLCCustomerImpartSchema.setModifyDate(PubFun.getCurrentDate());
				tLCCustomerImpartSchema.setModifyTime(PubFun.getCurrentTime());
				aLCCustomerImpartSet.add(tLCCustomerImpartSchema);
			}
			// mMap.put(aLPCustomerImpartSet, "UPDATE");
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
				// LPCustomerImpartParamsSchema tLPCustomerImpartParamsSchema =
				// new LPCustomerImpartParamsSchema();
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
		mLPEdorItemSchema.setEdorState("6");
		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(mLPEdorItemSchema, "UPDATE");
		mResult.clear();
		mResult.add(mMap);
		return true;
	}
}
