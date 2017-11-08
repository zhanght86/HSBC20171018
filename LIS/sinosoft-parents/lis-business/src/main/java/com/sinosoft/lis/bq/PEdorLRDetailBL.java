package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保单遗失补发项目明细</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Alex
 * @version 1.0
 */
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LPEdorItemDB;

public class PEdorLRDetailBL {
private static Logger logger = Logger.getLogger(PEdorLRDetailBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate = null;
	/** 校验 */
	private BqCalBase mBqCalBase = new BqCalBase();
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorItemSchema mLPEdorItemSchema_input = new LPEdorItemSchema();
	private LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private Reflections mReflections = new Reflections();
	private double mGBMoney = 10.0;

	public PEdorLRDetailBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
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
		// PubSubmit tSubmit = new PubSubmit();
		//
		// if (!tSubmit.submitData(mResult, "")) //数据提交
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tSubmit.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "PEdorLRDetailBL";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// logger.debug("PEdorLRDetailBL End PubSubmit");
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema_input = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGBMoney = mLPEdorItemSchema_input.getGetMoney();// TODO

		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "PEdorLRDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError tError = new CError();
			tError.moduleName = "PEdorLRDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "输入数据有误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		LCPolDB tLCPolDB = new LCPolDB();
		Reflections tReflections = new Reflections();

		// 随便给一个主险POLNO号吧
		String tSQL = "select * from lcpol where mainpolno = polno and contno = '?contno?'";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(tSQL);
		sbv.put("contno", mLPEdorItemSchema_input.getContNo());
		mLCPolSchema = tLCPolDB.executeQuery(sbv).get(1);

		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema_input.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema_input.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema_input.getContNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema_input.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
			// @@错误处理
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorLRDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "无保全申请数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPEdorItemSchema = tLPEdorItemSet.get(1);
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		mLPEdorItemSchema.setGetMoney(mGBMoney);
		mLPEdorItemSchema
				.setEdorReason(mLPEdorItemSchema_input.getEdorReason());
		mLPEdorItemSchema.setEdorReasonCode(mLPEdorItemSchema_input
				.getEdorReasonCode());

		// ------------add by ssx 生成相应的LPCont---------------------------
		// 删除LPCont
		tSQL = "delete from LPCont " + "where 1 = 1 " + "and EdorNo = '?EdorNo?' " + "and EdorType = '?EdorType?' " + "and ContNo = '?ContNo?' ";
		logger.debug(tSQL);
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(tSQL);
		sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sbv1.put("EdorType", mLPEdorItemSchema.getEdorType());
		sbv1.put("ContNo", mLPEdorItemSchema.getContNo());

		mMap.put(sbv1, "DELETE");
		// 生成本次新的LPCont
		LPContSchema tLPContSchema = new LPContSchema();
		// 将C表数据拷贝到P表
		tReflections.transFields(tLPContSchema, tLCContSchema);
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		logger.debug("新生成的LPCONT表的保单号:" + tLPContSchema.getContNo());
		// 补发次数+1
		tLPContSchema.setLostTimes(tLCContSchema.getLostTimes() + 1);
		if (mGBMoney >= 0) {
			tLPContSchema.setPrintCount(10);
		}
		tLPContSchema.setOperator(mGlobalInput.Operator);// 设置操作员
		tLPContSchema.setModifyDate(PubFun.getCurrentDate());
		tLPContSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(tLPContSchema, "INSERT");

		// 判断是保单是否处于挂失状态 生成的LPContState要为其解挂
		if (isOnLost(mLPEdorItemSchema.getContNo())) {
			LCContStateSet tLCContStateSet = new LCContStateSet();
			LPContStateSet tLPContStateSet = new LPContStateSet();
			LCContStateDB tLCContStateDB = new LCContStateDB();
			String strSql = "select * from lccontstate where statetype = 'Lost' "
					+ "and enddate is null and contno = '?contno?'";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(strSql);
			sbv2.put("contno", mLPEdorItemSchema.getContNo());
			tLCContStateSet = tLCContStateDB.executeQuery(sbv2);
			LCContStateSchema tLCContStateSchema = null;
			LPContStateSchema tLPContStateSchema = null;
			if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
				tLCContStateSchema = new LCContStateSchema();
				tLCContStateSchema = tLCContStateSet.get(1);
				tLPContStateSchema = new LPContStateSchema();
				this.mReflections.transFields(tLPContStateSchema,
						tLCContStateSchema);
				tLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
				tLPContStateSchema.setModifyTime(PubFun.getCurrentTime());
				//防止挂失的当天出现补发解挂保单
				String tEndDate = PubFun.calDate(mLPEdorItemSchema.getEdorValiDate(), -1, "D", null);
				if (tEndDate.compareTo(tLCContStateSchema.getStartDate()) < 0)
					tEndDate = tLCContStateSchema.getStartDate();
				tLPContStateSchema.setEndDate(tEndDate);// 设置结束日期在前一天
				tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPContStateSchema.setOperator(mGlobalInput.Operator);
				tLPContStateSet.add(tLPContStateSchema);
			}
			// 生成新的状态LPContStateSchema TODO
			tLPContStateSchema = new LPContStateSchema();
			tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPContStateSchema.setContNo(mLPEdorItemSchema.getContNo());
			tLPContStateSchema.setInsuredNo("000000");
			tLPContStateSchema.setPolNo("000000");
			tLPContStateSchema.setStateType("Lost");
			// 解挂标志
			tLPContStateSchema.setState("0");
			//防止挂失的当天出现补发解挂保单 新的状态日期往后延一天以防止主键冲突
			String tNewStartDate = mLPEdorItemSchema.getEdorValiDate();
			if (tNewStartDate.compareTo(tLCContStateSchema.getStartDate()) <= 0)
				tNewStartDate = PubFun.calDate(mLPEdorItemSchema.getEdorValiDate(), 1, "D", null);
			tLPContStateSchema.setStartDate(tNewStartDate);
			tLPContStateSchema.setOperator(mGlobalInput.Operator);
			tLPContStateSchema.setMakeDate(PubFun.getCurrentDate());
			tLPContStateSchema.setMakeTime(PubFun.getCurrentTime());
			tLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLPContStateSchema.setModifyTime(PubFun.getCurrentTime());
			tLPContStateSet.add(tLPContStateSchema);
			mMap.put(tLPContStateSet, "DELETE&INSERT");

		}
		
		//add by jiaqiangli 2009-02-05 多次保存ljsgetendorse前一次保存的未删除
		//以后这种判断getmoney满足某个条件才添加schema到map的，请都要有删除的动作，不管此次有没有insert
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql("delete from ljsgetendorse where GetNoticeNo='?GetNoticeNo?' and EndorsementNo='?EndorsementNo?' and FeeOperationType='?FeeOperationType?'");
		sbv3.put("GetNoticeNo", mLPEdorItemSchema.getEdorNo());
		sbv3.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sbv3.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		mMap.put(sbv3, "DELETE");
		//add by jiaqiangli 2009-02-05 多次保存ljsgetendorse前一次保存的未删除

		// -------------------add end-------------------------------------
		if (mGBMoney > 0) {
			LJSGetEndorseSchema tLJSGetEndorseSchema = null;
			tLJSGetEndorseSchema = this.initLJSGetEndorse("GB");
			if (tLJSGetEndorseSchema == null) {
				CError.buildErr(this, "生成交退费记录失败");
				return false;
			}
			tLJSGetEndorseSchema.setGetMoney(mGBMoney);
			tLJSGetEndorseSchema.setManageCom(tLCContSchema.getManageCom());
			tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_WorkNoteFee);
	          //营改增 add zhangyingfeng 2016-07-13
	          //价税分离 计算器
	          TaxCalculator.calBySchema(tLJSGetEndorseSchema);
	          //end zhangyingfeng 2016-07-13
			mMap.put(tLJSGetEndorseSchema, "DELETE&INSERT");
		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareData() {
		mMap.put(mLPEdorMainSchema, "UPDATE");
		mMap.put(mLPEdorItemSchema, "UPDATE");
		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		mResult.clear();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Money", mLPEdorItemSchema.getGetMoney());

		mResult.add(mBqCalBase);
		mResult.add(mMap);
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
	 * 判断保单是否处于挂失状态
	 * 
	 * @param tContNo
	 * @return
	 */
	private boolean isOnLost(String tContNo) {
		String strsql = "select contno from lccontstate where contno='?tContNo?' and statetype='Lost' and state='1' and enddate is null ";
		ExeSQL q_exesql = new ExeSQL();
		SSRS tssrs = new SSRS();
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(strsql);
		sbv.put("tContNo", tContNo);
		tssrs = q_exesql.execSQL(sbv);
		if (tssrs != null && tssrs.getMaxRow() >= 1) {
			return true;
		}
		return false;
	}

	/**
	 * 生成交退费记录
	 * 
	 * @return
	 */
	private LJSGetEndorseSchema initLJSGetEndorse(String strfinType) {
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();

		// mPayCount++;
		mLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
		mLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		mLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema
				.getEdorType());
		// 从描述表中获取财务接口类型，modify by Minim at 2003-12-23
		String finType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(),
				strfinType, mLPEdorItemSchema.getPolNo());
		if (finType.equals("")) {
			// @@错误处理
			CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码!");
			return null;
		}
		mLJSGetEndorseSchema.setFeeFinaType(finType);
		mLJSGetEndorseSchema.setDutyCode("0");
		mLJSGetEndorseSchema.setPayPlanCode("0");

		// 走保全交费财务流程
		mLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); // 保全给付用批单号
		mLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付

		mReflections.transFields(mLJSGetEndorseSchema, mLCPolSchema);

		mLJSGetEndorseSchema.setPolNo("000000");// 工本费不计到险种,计到保单

		mLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
		mLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
		mLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
		mLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
		mLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);

		mLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
		mLJSGetEndorseSchema.setGetFlag("0");

		tLJSGetEndorseSchema.setSchema(mLJSGetEndorseSchema);

		return tLJSGetEndorseSchema;
	}

}
