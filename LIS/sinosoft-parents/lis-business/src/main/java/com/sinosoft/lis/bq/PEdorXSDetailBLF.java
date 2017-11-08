package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
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
 * @author not attributable
 * @version 1.0
 */
public class PEdorXSDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorXSDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	GlobalInput mGlobalInput = new GlobalInput();
	LPPolSchema mLPPolSchema = new LPPolSchema();
	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
	
	public PEdorXSDetailBLF() {
	}

	public VData getResult() {
		mResult.clear();
		return mResult;
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

		if (mOperate != null && mOperate.equals("OnlyCheck")) {
			BqCalBase tBqCalBase = new BqCalBase();
			mResult.add(tBqCalBase);
			return true;
		}
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据校验
		// if(!checkData()){
		// return false;
		// }

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPTDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
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
			mLPPolSchema = (LPPolSchema) mInputData.getObjectByObjectName(
					"LPPolSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			if (mOperate.equals("EdorSave")) {
				// 调整后的金额
				mLJSGetEndorseSet = (LJSGetEndorseSet) mInputData
						.getObjectByObjectName("LJSGetEndorseSet", 0);
			}
		} catch (Exception e) {
			mErrors.addOneError("接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * 准备需要保存的数据 调用个人保全投保人信息变更业务逻辑处理类PEdorACBL进行处理
	 */
	private boolean dealData() {
		if (!mOperate.equals("EdorSave")) {// 减保计算
			// 调用减保程序PT
			PEdorPTDetailBL tPEdorPTDetailBL = new PEdorPTDetailBL();
			if (!tPEdorPTDetailBL.submitData(mInputData, mOperate)) {
				this.mErrors.copyAllErrors(tPEdorPTDetailBL.mErrors);
				CError tError = new CError();
				tError.moduleName = "PEdorPTDetailBLF";
				tError.functionName = "dealData";
				tError.errorMessage = "数据处理失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mResult.clear();
			mResult = tPEdorPTDetailBL.getResult();
		}
		else if (mOperate.equals("EdorSave")) { //协议减保 保存调整后的金额
			if (!queryLPEdorItem()) {
				return false;
			}

			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			LJSGetEndorseSet tLJSGetEndorseSetNew = new LJSGetEndorseSet();
			double tSumActGetMoney = 0.00;
			//差值 = 实退-应退 借用LPEdorItem.setStandbyFlag3保存
			double tMinusGetMoney = 0.0;
			//add by jiaqiangli 2008-09-16 此处是为了处理多次修改调整值后仍需要保证初始计算值的正确性
			//所以此处要先查询出上次(如果有的话)的差值
			if (mLPEdorItemSchema.getStandbyFlag3() != null && !mLPEdorItemSchema.getStandbyFlag3().equals(""))
				tMinusGetMoney = Double.parseDouble(mLPEdorItemSchema.getStandbyFlag3());
			//add by jiaqiangli 2008-09-16 此处是为了处理多次修改调整值后仍需要保证初始计算值的正确性
			for (int i = 1; i <= mLJSGetEndorseSet.size(); i++) // 险种层退费
			{

				String QuerySQL = new String("");
				QuerySQL = "select * from LJSGetEndorse " + "where 1 = 1 "
						+ " and EndorsementNo = '?EndorsementNo?' "
						+ " and FeeOperationType = '?FeeOperationType?' "
						+ " and FeeFinaType = '?FeeFinaType?' "
						+ " and PolNo = '?PolNo?' ";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(QuerySQL);
				sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
				sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
				sqlbv.put("FeeFinaType", mLJSGetEndorseSet.get(i).getFeeFinaType());
				sqlbv.put("PolNo", mLJSGetEndorseSet.get(i).getPolNo());

				LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
				tLJSGetEndorseSet = tLJSGetEndorseDB.executeQuery(sqlbv);
				if (tLJSGetEndorseDB.mErrors.needDealError()) {
					CError.buildErr(this, "退保费用查询出现异常！");
					return false;
				}
				if (tLJSGetEndorseSet == null || tLJSGetEndorseSet.size() < 1) {
					CError.buildErr(this, "退保费用查询失败！");
					return false;
				}

//				if (mLJSGetEndorseSet.get(i).getGetMoney() < 0) {
//					mErrors.copyAllErrors(this.mErrors);
//					CError.buildErr(this, "输入调整金额不能小于零！");
//					return false;
//				}
				
				//comment by jiaqiangli 2008-09-16
				//保存差值 实退-应退 所以 应退 = 实退 - 差值
				//财务要求不再保存明细的差值，只保存汇总的差值，但是这样明细的差值就丢失了
//				tMinusGetMoney += mLJSGetEndorseSet.get(i).getGetMoney() - tLJSGetEndorseSet.get(1).getGetMoney();
//				//保存实退值
//				tSumActGetMoney += mLJSGetEndorseSet.get(i).getGetMoney();
				
				//-------------end----输入金额可以大于应退金额-------------
				LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
				tLJSGetEndorseSchema = tLJSGetEndorseSet.get(1);
				LJSGetEndorseSchema tLJSGetEndorseSchemaNew = new LJSGetEndorseSchema();
				PubFun.copySchema(tLJSGetEndorseSchemaNew,tLJSGetEndorseSchema);
				//做调整金额替换计算金额
				//将调整金额（即实退金额）做替换，而应退金额不变
				tLJSGetEndorseSchemaNew.setGetMoney(mLJSGetEndorseSet.get(i).getGetMoney());
				tLJSGetEndorseSchemaNew.setOperator(mGlobalInput.Operator);
				tLJSGetEndorseSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLJSGetEndorseSchemaNew.setModifyTime(PubFun.getCurrentTime());
		         //营改增 add zhangyingfeng 2016-07-14
		          //价税分离 计算器
		          TaxCalculator.calBySchema(tLJSGetEndorseSchemaNew);
		          //end zhangyingfeng 2016-07-14
				tLJSGetEndorseSetNew.add(tLJSGetEndorseSchemaNew);
				tLJSGetEndorseSchemaNew = null;
				tLJSGetEndorseSchema = null;
			}
//			//GetMoney实退合计值
//			mLPEdorItemSchema.setGetMoney(tSumActGetMoney);
//			mLPEdorItemSchema.setEdorState("3");
//			mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
//			mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
//			//StandbyFlag1这个字段己经被借用为犹豫期标志
//			mLPEdorItemSchema.setStandbyFlag3(String.valueOf(tMinusGetMoney));// 借用这个字段保存差值
//
//			mMap.put(mLPEdorItemSchema, "UPDATE");
			
			//用调整值进行替换计算值
			mMap.put(tLJSGetEndorseSetNew, "DELETE&INSERT");
			
			//同一个事务内的update语句操作顺序很重要
			// add by jiaqiangli 2008-12-30
			// 最后的汇总ljsgetendorse到lpedoritem.getmoney
			// lpedoritem.getmoney standbyflag3汇总值 ljsgetendorse.getmoney serialno 明细值
			// 此处只需汇总调整金额即实退金额即可
			String tGetMoneySQL = "update lpedoritem set getmoney = (select sum(getmoney) from ljsgetendorse where endorsementno = '?endorsementno?' and contno = '?contno?' "
					+ " and feeoperationtype='?feeoperationtype?') where edoracceptno='?edoracceptno?' and contno='?contno?'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tGetMoneySQL);
			sqlbv1.put("endorsementno", mLPEdorItemSchema.getEdorNo());
			sqlbv1.put("contno", mLPEdorItemSchema.getContNo());
			sqlbv1.put("feeoperationtype", mLPEdorItemSchema.getEdorType());
			sqlbv1.put("edoracceptno", mLPEdorItemSchema.getEdorAcceptNo());
			mMap.put(sqlbv1, "UPDATE");
			// add by jiaqiangli 2008-12-30

			//最后添加处理
			mResult.clear();
			mResult.add(mMap);
		}
		return true;
	}
	
	private boolean queryLPEdorItem() {
		String sEdorReasonCode = mLPEdorItemSchema.getEdorReasonCode();
		String sEdorReason = mLPEdorItemSchema.getEdorReason();
		String sRelationToAppnt = mLPEdorItemSchema.getStandbyFlag2();

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			CError.buildErr(this, "查询批改项目信息失败！");
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		mLPEdorItemSchema.setEdorReasonCode(sEdorReasonCode);
		mLPEdorItemSchema.setEdorReason(sEdorReason);
		mLPEdorItemSchema.setStandbyFlag2(sRelationToAppnt);

		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
