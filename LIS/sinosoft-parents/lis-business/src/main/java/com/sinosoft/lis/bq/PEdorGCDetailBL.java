package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPInsuredBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.db.LPAccountDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPAccountSchema;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 领取信息变更
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
 * @author lizhuo
 * @version 1.0
 */
public class PEdorGCDetailBL {
private static Logger logger = Logger.getLogger(PEdorGCDetailBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = null;
	/** 封装要操作的数据，以便一次提交 */
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 校验 */
	private BqCalBase mBqCalBase = new BqCalBase();

	/** 全局数据 */
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LPContSchema mLPContSchema = new LPContSchema();
	private LPPolSchema mLPPolSchema = new LPPolSchema();
	private LPInsuredSet mLPInsuredSet = new LPInsuredSet();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPAccountSchema mLPAccountSchema = new LPAccountSchema();
	private LPBnfSet mLPBnfSet = new LPBnfSet();
	private Reflections ref = new Reflections();

	public PEdorGCDetailBL() {
	}

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
		mResult.clear();
		mResult.add(mBqCalBase);
		mResult.add(mMap);
		return true;
	}

	/** 获取前台数据 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPPolSchema = (LPPolSchema) mInputData.getObjectByObjectName(
					"LPPolSchema", 0);
			mLPAccountSchema = (LPAccountSchema) mInputData
					.getObjectByObjectName("LPAccountSchema", 0);
			//add lpbnf
			//生存金保存受益人分配信息
			mLPBnfSet = (LPBnfSet) mInputData.getObjectByObjectName("LPBnfSet", 0);

			if (mGlobalInput == null || mLPEdorItemSchema == null
					|| mLPPolSchema == null) {
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		boolean flag = true;
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "PEdorGCDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "无保全申请数据!";
			this.mErrors.addOneError(tError);
			return false;

		}
		// 获取保全主表数据，节省查询次数
		mLPEdorItemSchema = tLPEdorItemSet.get(1);
		if (!mLPEdorItemSchema.getEdorState().trim().equals("1")
				&& !mLPEdorItemSchema.getEdorState().trim().equals("3")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorIODetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "该保全已经申请确认不能修改!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return flag;

	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		boolean flag = true;
		// //处理个人保单表信息
		// LPContBL tLPContBL = new LPContBL();
		// if (!tLPContBL.queryLPCont(mLPEdorItemSchema))
		// {
		// CError.buildErr(this, "查询个人保单出错！");
		// return false;
		// }
		// tLPContBL.setEdorNo(mLPContSchema.getEdorNo());
		// tLPContBL.setEdorType(mLPContSchema.getEdorType());
		// tLPContBL.setPayLocation(mLPContSchema.getPayLocation());
		// tLPContBL.setBankCode(mLPContSchema.getBankCode());
		// tLPContBL.setBankAccNo(mLPContSchema.getBankAccNo());
		// tLPContBL.setAccName(mLPContSchema.getAccName());
		//
		// tLPContBL.setModifyDate(PubFun.getCurrentDate());
		// tLPContBL.setModifyTime(PubFun.getCurrentTime());
		// mMap.put(tLPContBL.getSchema(), "DELETE&INSERT"); //加入Map
		//comment by jiaqiangli 2008-10-07 MS没有此选择项
//		if (mLPPolSchema.getGetForm().equals("2")) {
//			// 处理被保人表
//			LPInsuredBL tLPInsuredBL = new LPInsuredBL();
//			tLPInsuredBL.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
//			tLPInsuredBL.setEdorNo(mLPEdorItemSchema.getEdorNo());
//			tLPInsuredBL.setEdorType(mLPEdorItemSchema.getEdorType());
//			LPInsuredSet tLPInsuredSet = tLPInsuredBL
//					.queryLPInsured(mLPEdorItemSchema);
//			LPInsuredSchema tLPInsuredSchema = tLPInsuredSet.get(1);
//			tLPInsuredSchema.setBankCode(mLPPolSchema.getGetBankCode());
//			tLPInsuredSchema.setBankAccNo(mLPPolSchema.getGetBankAccNo());
//			tLPInsuredSchema.setAccName(mLPPolSchema.getGetAccName());
//			mLPInsuredSet.add(tLPInsuredSchema);
//			mMap.put(mLPInsuredSet, "DELETE&INSERT");
//		}
		// 处理险种表
		LPPolBL tLPPolBL = new LPPolBL();
		tLPPolBL.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPPolBL.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolBL.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPPolBL.queryLPPol(mLPEdorItemSchema)) {
			CError.buildErr(this, "查询保单险种信息失败！");
			return false;
		}
		LPPolSchema tLPPolSchema = tLPPolBL.getSchema();
		tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolSchema.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSchema.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPPolSchema.setGetForm(mLPPolSchema.getGetForm());

		tLPPolSchema.setGetBankCode(mLPPolSchema.getGetBankCode());
		tLPPolSchema.setGetBankAccNo(mLPPolSchema.getGetBankAccNo());
		tLPPolSchema.setGetAccName(mLPPolSchema.getGetAccName());

		mMap.put(tLPPolSchema, "DELETE&INSERT");

		//comment by jiaqiangli 2008-08-25
		// 处理保全客户账户表
//		if (mLPPolSchema.getGetForm() == "0"
//				|| mLPPolSchema.getGetForm().equals("0")) {
//			LPAccountDB tLPAccountDB = new LPAccountDB();
//			tLPAccountDB.setSchema(mLPAccountSchema);
//			if (!tLPAccountDB.getInfo()) {
//				mMap.put(tLPAccountDB.getSchema(), "INSERT");
//			}
//		}
		//add by jiaqiangli 2008-08-25 生存金受益人分配信息
		logger.debug("this.mGlobalInput.Operator"+this.mGlobalInput.Operator);
		int lpbnfsize = mLPBnfSet.size();
		for (int i = 1; i <= lpbnfsize; i++) {
			this.mLPBnfSet.get(i).setMakeDate(PubFun.getCurrentDate());
			this.mLPBnfSet.get(i).setMakeTime(PubFun.getCurrentTime());
			this.mLPBnfSet.get(i).setModifyDate(PubFun.getCurrentDate());
			this.mLPBnfSet.get(i).setModifyTime(PubFun.getCurrentTime());
			this.mLPBnfSet.get(i).setOperator(this.mGlobalInput.Operator);
		}
		//可以返回修改生存金受益人分配信息
		mMap.put(mLPBnfSet, "DELETE&INSERT");
		//add by jiaqiangli 2008-08-25 生存金受益人分配信息

		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(mLPEdorItemSchema, "UPDATE");

		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());

		return flag;

	}

	/**
	 * getOperate
	 * 
	 * @return Object
	 */
	private Object getOperate() {
		return mOperate;
	}

	/**
	 * getResult
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
