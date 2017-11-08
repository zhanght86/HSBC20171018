package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保单遗失补发项目明细</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * ReWrite ZhangRong
 * @version 1.0
 */

import java.util.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class GrpEdorWTDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorWTDetailBL.class);
  /** 传入数据的容器 */
  private VData mInputData = new VData();


  /** 传出数据的容器 */
  private MMap mMap = new MMap();
  private VData mResult = new VData();


  /** 数据操作字符串 */
  private String mOperate;


  /** 错误处理类 */
  public CErrors mErrors = new CErrors();


  /** 全局基础数据 */
  private GlobalInput mGlobalInput = new GlobalInput();
  private LPGrpEdorItemSchema mLPGrpEdorItemSchema_in = new
      LPGrpEdorItemSchema();
  private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
  private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
  private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
  private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
  private LPGrpContSchema mLPGrpContSchema = new LPGrpContSchema();
  private LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();
  private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
  private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
  private LPContSet mLPContSet = new LPContSet();
  private LPPolSet mLPPolSet = new LPPolSet();
  private LPContStateSet saveLPContStateSet = new LPContStateSet();
  private LPGrpContStateSet saveLPGrpContStateSet = new LPGrpContStateSet();
  private GrpEdorCalZT mGrpEdorCalZT = new GrpEdorCalZT();
  private Reflections tRef = new Reflections();
  private String currDate = PubFun.getCurrentDate();
  private String currTime = PubFun.getCurrentTime();

  public GrpEdorWTDetailBL() {
  }


  /**
   * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
   * @param cInputData 传入的数据,VData对象
   * @param cOperate 数据操作字符串，主要包括"INSERT"
   * @return 布尔值（true--提交成功, false--提交失败）
   */
  public boolean submitData(VData cInputData, String cOperate) {

    //将操作数据拷贝到本类中
    this.mInputData = (VData) cInputData.clone();
    this.mOperate = cOperate;

    //得到外部传入的数据,将数据备份到本类中
    if (!getInputData()) {
      return false;
    }
    logger.debug("---------------getInputData end---------------------");

    //进行业务处理
    if (!dealData()) {
      return false;
    }
    logger.debug("---------------dealData end---------------------");

    //准备往后台的数据
    if (!prepareData()) {
      return false;
    }
    logger.debug("---------------prepareData end---------------------");

    PubSubmit tSubmit = new PubSubmit();

    if (!tSubmit.submitData(mResult, "")) { //数据提交
      // @@错误处理
      this.mErrors.copyAllErrors(tSubmit.mErrors);
      CError tError = new CError();
      tError.moduleName = "GrpEdorWTDetailBL";
      tError.functionName = "submitData";
      tError.errorMessage = "数据提交失败!";
      this.mErrors.addOneError(tError);
      return false;
    }
    logger.debug("GrpEdorWTDetailBL End PubSubmit");
    return true;
  }


  /**
   * 将外部传入的数据分解到本类的属性中
   * @param: 无
   * @return: boolean
   */
  private boolean getInputData() {
    try {
      mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
          "GlobalInput", 0);
      mLPGrpEdorItemSchema_in = (LPGrpEdorItemSchema) mInputData.
                                getObjectByObjectName(
          "LPGrpEdorItemSchema",
          0);

    } catch (Exception e) {
      // @@错误处理
      e.printStackTrace();
      CError tError = new CError();
      tError.moduleName = "GrpEdorWTDetailBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "接收数据失败!!";
      this.mErrors.addOneError(tError);
      return false;
    }

    if (mGlobalInput == null || mLPGrpEdorItemSchema_in == null) {
      CError tError = new CError();
      tError.moduleName = "GrpEdorWTDetailBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "输入数据有误!";
      this.mErrors.addOneError(tError);
      return false;
    }
    LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
    tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema_in.getEdorNo());
    tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema_in.getEdorType());
    tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema_in.getGrpContNo());
    LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();

    if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1) {
      CError tError = new CError();
      tError.moduleName = "GrpEdorWTDetailBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "输入数据有误,LPGrpEdorItem中没有相关数据!";
      this.mErrors.addOneError(tError);
      return false;
    }
    mLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);

    LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
    tLPGrpEdorMainDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    tLPGrpEdorMainDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
    if (!tLPGrpEdorMainDB.getInfo()) {
      CError tError = new CError();
      tError.moduleName = "GrpEdorWTDetailBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "输入数据有误,LPGrpEdorMain中没有相关数据!";
      this.mErrors.addOneError(tError);
      return false;
    }
    mLPGrpEdorMainSchema = tLPGrpEdorMainDB.getSchema();

    return true;
  }


  /**
   * 根据前面的输入数据，进行逻辑处理
   * @return 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData() {
    LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();
    LCGrpContDB tLCGrpContDB = new LCGrpContDB();
    tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
    LCGrpContSchema mLCGrpContSchema = tLCGrpContDB.query().get(1);
    Reflections ref = new Reflections();
    ref.transFields(mLPGrpContSchema, mLCGrpContSchema);
    mLPGrpContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    mLPGrpContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());

    LCPolDB tLCPolDB = new LCPolDB();
    tLCPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
    tLCPolDB.setAppFlag("1");
    LCPolSet mLCPolSet = tLCPolDB.query();
    double dSumTF = 0.0;
    for (int j = 1; j <= mLCPolSet.size(); j++) {
    	LCPolSchema mLCPolSchema = new LCPolSchema();
    	LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema(); 
    	
    	mLCPolSchema.setSchema(mLCPolSet.get(j));
    	Reflections tRef = new Reflections();
    	tRef.transFields(tLPEdorItemSchema, mLPGrpEdorItemSchema);
    	tRef.transFields(tLPEdorItemSchema, mLCPolSchema);
    	tLPEdorItemSchema.setGrpContNo(mLPGrpEdorItemSchema.
    			getGrpContNo());
    	tLPEdorItemSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    	tLPEdorItemSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
    	tLPEdorItemSchema.setPolNo(mLCPolSchema.getPolNo()); //算退费的入参1
    	tLPEdorItemSchema.setChgAmnt( -mLCPolSchema.getAmnt());
    	tLPEdorItemSchema.setChgPrem( -mLCPolSchema.getPrem());
    	tLPEdorItemSchema.setGetMoney(-mLCPolSchema.getPrem());
    	tLPEdorItemSchema.setInsuredNo(mLCPolSchema.getInsuredNo());
    	tLPEdorItemSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
    	tLPEdorItemSchema.setOperator(mGlobalInput.Operator);
    	tLPEdorItemSchema.setUWFlag("0");
    	tLPEdorItemSchema.setMakeDate(currDate);
    	tLPEdorItemSchema.setMakeTime(currTime);
    	tLPEdorItemSchema.setModifyDate(currDate);
    	tLPEdorItemSchema.setModifyTime(currTime);
    	tLPEdorItemSchema.setEdorState("1");
    	mLPEdorItemSet.add(tLPEdorItemSchema);
    	   	
    	
    	saveLPContStateSet.add(mGrpEdorCalZT.creatContState(tLPEdorItemSchema));
    	//设置批改补退费表
    	if(mLCPolSchema.getPrem()>0){
    		dSumTF += mLCPolSchema.getPrem();
    		String sFeeType = BqCalBL.getFinType(tLPEdorItemSchema.getEdorType(), "TF",mLCPolSchema.getPolNo());
    		mLJSGetEndorseSchema = new LJSGetEndorseSchema();
    		ref.transFields(mLJSGetEndorseSchema, mLCPolSet.get(j));
    		mLJSGetEndorseSchema.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo()); //给付通知书号码
    		mLJSGetEndorseSchema.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
    		mLJSGetEndorseSchema.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
    		mLJSGetEndorseSchema.setPolNo(mLCPolSchema.getPolNo());
    		mLJSGetEndorseSchema.setFeeOperationType(mLPGrpEdorItemSchema.getEdorType());
    		mLJSGetEndorseSchema.setGetDate(mLPGrpEdorItemSchema.getEdorValiDate());
    		mLJSGetEndorseSchema.setGetMoney(mLCPolSchema.getPrem()); //退保
    		mLJSGetEndorseSchema.setFeeFinaType(sFeeType);
    		mLJSGetEndorseSchema.setPayPlanCode("000000"); //无作用
    		mLJSGetEndorseSchema.setDutyCode("000000"); //无作用，但一定要，转ljagetendorse时非空
    		mLJSGetEndorseSchema.setOtherNo(mLPGrpEdorItemSchema.getEdorNo()); //无作用
    		mLJSGetEndorseSchema.setOtherNoType("3");
    		mLJSGetEndorseSchema.setGetFlag("1");
    		mLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Get_Prem);
    		mLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
    		mLJSGetEndorseSchema.setManageCom(mGlobalInput.ManageCom);
    		mLJSGetEndorseSchema.setAgentCode(tLCGrpContDB.getAgentCode());
    		mLJSGetEndorseSchema.setCurrency("01");
    		mLJSGetEndorseSchema.setAgentCom(tLCGrpContDB.getAgentCom());
    		mLJSGetEndorseSchema.setAgentGroup(tLCGrpContDB.getAgentGroup());
    		mLJSGetEndorseSchema.setAgentType(tLCGrpContDB.getAgentType());
    		mLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
    		mLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
    		mLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
    		mLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
    		mLJSGetEndorseSet.add(mLJSGetEndorseSchema);
    	}
    }
    logger.debug("总保费" + dSumTF);
	//mLPEdorMainSet  2012/6/7modify lpedormain表和lccont表数据记录相对应，以解决我险种时存储失败
    LCContDB tLCContDB = new LCContDB();
    tLCContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
    tLCContDB.setAppFlag("1");
    LCContSet mLCContSet = tLCContDB.query();
    for(int m = 1; m <= mLCContSet.size(); m++){
    	   LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
    	   LCContSchema mLCContSchema = new LCContSchema();
    	   mLCContSchema.setSchema(mLCContSet.get(m));
	       tRef.transFields(tLPEdorMainSchema, mLPGrpEdorItemSchema);
	       tRef.transFields(tLPEdorMainSchema, mLCContSchema);
	
	       tLPEdorMainSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
	       tLPEdorMainSchema.setChgAmnt( -mLCContSchema.getAmnt());
	       tLPEdorMainSchema.setChgPrem( -mLCContSchema.getPrem());
	       tLPEdorMainSchema.setGetMoney(-mLCContSchema.getPrem());
	       tLPEdorMainSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
	       tLPEdorMainSchema.setOperator(mGlobalInput.Operator);
	       tLPEdorMainSchema.setMakeDate(currDate);
	       tLPEdorMainSchema.setMakeTime(currTime);
	       tLPEdorMainSchema.setModifyDate(currDate);
	       tLPEdorMainSchema.setModifyTime(currTime);
	       tLPEdorMainSchema.setEdorState("1");
	       mLPEdorMainSet.add(tLPEdorMainSchema);
    }
    
    //扣除工本费10元
    String sFeeFinaType = BqCalBL.getFinType(mLPGrpEdorItemSchema
			.getEdorType(), "GB", "000000");
	LJSGetEndorseSchema tLJSGetEndorseSchemaNew = new LJSGetEndorseSchema();
	tLJSGetEndorseSchemaNew.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo());
	tLJSGetEndorseSchemaNew.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
	tLJSGetEndorseSchemaNew.setFeeOperationType(mLPGrpEdorItemSchema.getEdorType());
	tLJSGetEndorseSchemaNew.setFeeFinaType(sFeeFinaType);
	tLJSGetEndorseSchemaNew.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
	tLJSGetEndorseSchemaNew.setPolNo("000000");
	tLJSGetEndorseSchemaNew.setOtherNo(mLPGrpEdorItemSchema.getEdorNo());
	tLJSGetEndorseSchemaNew.setOtherNoType("3");
	tLJSGetEndorseSchemaNew.setDutyCode("000000");
	tLJSGetEndorseSchemaNew.setPayPlanCode("000000");
	tLJSGetEndorseSchemaNew.setGetDate(mLPGrpEdorItemSchema.getEdorValiDate());
	tLJSGetEndorseSchemaNew.setGetMoney(10); // 负数表示领取
	tLJSGetEndorseSchemaNew.setManageCom(mGlobalInput.ManageCom);
	tLJSGetEndorseSchemaNew.setPolType("1");
	tLJSGetEndorseSchemaNew.setGetFlag("0");//交费
	tLJSGetEndorseSchemaNew.setCurrency("01");
	tLJSGetEndorseSchemaNew.setOperator(mGlobalInput.Operator);
	tLJSGetEndorseSchemaNew.setMakeDate(PubFun.getCurrentDate());
	tLJSGetEndorseSchemaNew.setMakeTime(PubFun.getCurrentTime());
	tLJSGetEndorseSchemaNew.setModifyDate(PubFun.getCurrentDate());
	tLJSGetEndorseSchemaNew.setModifyTime(PubFun.getCurrentTime());
	tLJSGetEndorseSchemaNew.setSubFeeOperationType(BqCode.Pay_WorkNoteFee);
	mLJSGetEndorseSet.add(tLJSGetEndorseSchemaNew);

    mLPGrpEdorItemSchema.setGetMoney(dSumTF-10);
    mLPGrpEdorItemSchema.setEdorState("1");
    mLPGrpEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
    mLPGrpEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
    mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
    mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
    saveLPGrpContStateSet.add(mGrpEdorCalZT.creatGrpContState(mLPGrpEdorItemSchema));
    return true;
  }


  /**
   * 准备往后层输出所需要的数据
   * @return 如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean prepareData() {
    mMap.put(mLJSGetEndorseSet, "DELETE&INSERT");
    mMap.put(mLPGrpEdorItemSchema, "UPDATE");
    mMap.put(mLPGrpContSchema, "DELETE&INSERT");
    mMap.put(mLPEdorItemSet, "DELETE&INSERT");
    mMap.put(mLPEdorMainSet, "DELETE&INSERT");
    mMap.put(saveLPContStateSet, "DELETE&INSERT");
    mMap.put(saveLPGrpContStateSet, "DELETE&INSERT");
    mResult.clear();
    mResult.add(mMap);

    return true;
  }


  /**
   * 数据输出方法，供外界获取数据处理结果
   * @return 包含有数据查询结果字符串的VData对象
   */
  public VData getResult() {
    return mResult;
  }

}
