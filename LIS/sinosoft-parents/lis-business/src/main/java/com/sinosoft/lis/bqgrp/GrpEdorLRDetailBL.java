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

public class GrpEdorLRDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorLRDetailBL.class);
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
  private String mNeedGetMoney = "Yes";
  private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
  private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
  private LPGrpEdorItemSchema mLPGrpEdorItemSchema_input = new
      LPGrpEdorItemSchema();
  private LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();
  private Reflections mReflections = new Reflections();
  private LCPolSchema mLCPolSchema = new LCPolSchema();
  private String tEdorNo = "";
  private String tEdorType = "";
  public GrpEdorLRDetailBL() {
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

    //进行业务处理
    if (!dealData()) {
      return false;
    }

    //准备往后台的数据
    if (!prepareData()) {
      return false;
    }
    PubSubmit tSubmit = new PubSubmit();

    if (!tSubmit.submitData(mResult, "")) { //数据提交
      // @@错误处理
      this.mErrors.copyAllErrors(tSubmit.mErrors);
      CError tError = new CError();
      tError.moduleName = "GrpEdorLRDetailBL";
      tError.functionName = "submitData";
      tError.errorMessage = "数据提交失败!";
      this.mErrors.addOneError(tError);
      return false;
    }

    logger.debug("GrpEdorLRDetailBL End PubSubmit");
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
      mLPGrpEdorItemSchema_input = (LPGrpEdorItemSchema) mInputData.
                                   getObjectByObjectName(
          "LPGrpEdorItemSchema",
          0);

    } catch (Exception e) {
      // @@错误处理
      e.printStackTrace();
      CError tError = new CError();
      tError.moduleName = "GrpEdorLRDetailBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "接收数据失败!!";
      this.mErrors.addOneError(tError);
      return false;
    }

    if (mGlobalInput == null || mLPGrpEdorItemSchema == null) {
      CError tError = new CError();
      tError.moduleName = "GrpEdorLRDetailBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "输入数据有误!";
      this.mErrors.addOneError(tError);
      return false;
    }
    return true;
  }


  /**
   * 根据前面的输入数据，进行逻辑处理
   * @return 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData() {
    LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
    tLPGrpEdorMainDB.setEdorNo(mLPGrpEdorItemSchema_input.getEdorNo());
    LPGrpEdorMainSet tLPGrpEdorMainSet = tLPGrpEdorMainDB.query();
    if (tLPGrpEdorMainSet == null || tLPGrpEdorMainSet.size() != 1) {
      // @@错误处理
      mErrors.copyAllErrors(tLPGrpEdorMainDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "GrpEdorLRDetailBL";
      tError.functionName = "checkData";
      tError.errorMessage = "无保全申请数据!";
      this.mErrors.addOneError(tError);
      return false;
    }

    mLPGrpEdorMainSchema.setSchema(tLPGrpEdorMainSet.get(1));
    mLPGrpEdorMainSchema.setGetMoney(mLPGrpEdorMainSchema.getGetMoney() +
                                     mLPGrpEdorItemSchema_input.getGetMoney());
    if (mLPGrpEdorMainSchema.getEdorState().trim().equals("2")) {
      // @@错误处理
      mErrors.copyAllErrors(tLPGrpEdorMainDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "GrpEdorLRDetailBL";
      tError.functionName = "checkData";
      tError.errorMessage = "该保全已经申请确认不能修改!";
      this.mErrors.addOneError(tError);
      return false;
    }

    LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
    tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema_input.getEdorNo());
    tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema_input.getGrpContNo());
    tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema_input.getEdorType());
    LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
    if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() != 1) {
      // @@错误处理
      mErrors.copyAllErrors(tLPGrpEdorItemDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "GrpEdorLRDetailBL";
      tError.functionName = "checkData";
      tError.errorMessage = "无保全申请数据!";
      this.mErrors.addOneError(tError);
      return false;
    }
    mLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);
    mLPGrpEdorItemSchema.setGetMoney(mLPGrpEdorItemSchema_input.getGetMoney());
    //modified by sunsx 增加补发原因
    mLPGrpEdorItemSchema.setEdorReasonCode(mLPGrpEdorItemSchema_input.getEdorReasonCode());
    mLPGrpEdorItemSchema.setEdorReason(mLPGrpEdorItemSchema_input.getEdorReason());
    mLPGrpEdorItemSchema.setEdorTypeCal(mLPGrpEdorItemSchema_input.getEdorTypeCal());
    tEdorNo = tLPGrpEdorItemDB.getEdorNo();
    tEdorType = tLPGrpEdorItemDB.getEdorType();
    
    
    //add by sunsx 2008-09-24 增加PGgrpCont
    //---------------Start grpcont表处理------------------
    //C表
    String tGrpContNo = mLPGrpEdorItemSchema.getGrpContNo();
    LCGrpContDB tLCGrpContDB = new LCGrpContDB();
    LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
    tLCGrpContDB.setGrpContNo(tGrpContNo);
	if (!tLCGrpContDB.getInfo()) {
		mErrors.addOneError("查询团体保单信息失败!");
		return false;
	}
	tLCGrpContSchema = tLCGrpContDB.getSchema();
	//P表
	LPGrpContSchema tLPGrpContSchema = new LPGrpContSchema();
	mReflections.transFields(tLPGrpContSchema, tLCGrpContSchema);
	logger.debug("新生成的LPCONT表的保单号:"+tLPGrpContSchema.getGrpContNo());
	//补发次数加1
	tLPGrpContSchema.setLostTimes(tLCGrpContSchema.getLostTimes()+1);
	tLPGrpContSchema.setPrintCount(10);
	tLPGrpContSchema.setEdorNo(tEdorNo);
	tLPGrpContSchema.setEdorType(tEdorType);
	tLPGrpContSchema.setModifyDate(PubFun.getCurrentDate());
	tLPGrpContSchema.setModifyTime(PubFun.getCurrentTime());
	tLPGrpContSchema.setOperator(mGlobalInput.Operator);
	mMap.put(tLPGrpContSchema, "DELETE&INSERT");
	//---------end grpcont-------------------------------
	
    logger.debug(mLPGrpEdorItemSchema.getGetMoney());
    double tGetMoney = mLPGrpEdorItemSchema.getGetMoney();
    //modified by sunsx 收费为0不生成暂交费记录 2008-09-22
	// 删除 LJSGetEndorse
	String DeleteSQL = "delete from LJSGetEndorse " + "where 1 = 1 "
			+ "and GetNoticeNo = '"
			+ mLPGrpEdorItemSchema.getEdorNo() + "' "
			+ "and EndorsementNo = '" + mLPGrpEdorItemSchema.getEdorNo()
			+ "' " + "and FeeOperationType = '"
			+ mLPGrpEdorItemSchema.getEdorType() + "' ";
			
	// logger.debug(DeleteSQL);
	mMap.put(DeleteSQL, "DELETE");
    if(tGetMoney > 0){
    	LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
    	tLJSGetEndorseSchema = this.initLJSGetEndorse("GB");
    	tLJSGetEndorseSchema.setGetMoney(mLPGrpEdorItemSchema.getGetMoney());
    	mMap.put(tLJSGetEndorseSchema, "DELETE&INSERT");
    }

    return true;
  }

  private LJSGetEndorseSchema initLJSGetEndorse(String strfinType) {
    LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
    LCGrpContDB tLCGrpContDB = new LCGrpContDB();
    tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema_input.getGrpContNo());
    LCGrpContSchema mLCGrpContSchema = tLCGrpContDB.query().get(1);
    mReflections.transFields(mLJSGetEndorseSchema, mLCGrpContSchema);
    String finType = BqCalBL.getFinType(mLPGrpEdorItemSchema.getEdorType(),
			strfinType, "000000");
	if (finType.equals("")) {
		// @@错误处理
		CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码!");
		return null;
	}
    mLJSGetEndorseSchema.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo());
    mLJSGetEndorseSchema.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
    mLJSGetEndorseSchema.setFeeOperationType(mLPGrpEdorItemSchema.getEdorType());
    mLJSGetEndorseSchema.setFeeFinaType(finType); //工本费
    mLJSGetEndorseSchema.setDutyCode("0");
    mLJSGetEndorseSchema.setPayPlanCode("0");
    mLJSGetEndorseSchema.setOtherNo(mLPGrpEdorItemSchema.getEdorNo()); //保全给付用批单号
    mLJSGetEndorseSchema.setOtherNoType("3"); //保全给付
    mLJSGetEndorseSchema.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
    mLJSGetEndorseSchema.setGrpPolNo("00000000000000000000");
    mLJSGetEndorseSchema.setPolNo("00000000000000000000");
    mLJSGetEndorseSchema.setContNo("00000000000000000000");
    mLJSGetEndorseSchema.setGetDate(mLPGrpEdorItemSchema.getEdorValiDate());
    mLJSGetEndorseSchema.setOtherNo(mLPGrpEdorItemSchema.getEdorNo()); //无作用
    mLJSGetEndorseSchema.setGetFlag("0");
    mLJSGetEndorseSchema.setCurrency("01"); //默认为 01- 人民币
    mLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_WorkNoteFee);
    mLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
    mLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
    mLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
    mLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
    mLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
    //mLJSGetEndorseSchema.setSerialNo(mLJSPaySchema.getSerialNo());
    mLJSGetEndorseSchema.setGetMoney(mLPGrpEdorItemSchema.getGetMoney());

    tLJSGetEndorseSchema.setSchema(mLJSGetEndorseSchema);

    return tLJSGetEndorseSchema;
  }


  /**
   * 准备往后层输出所需要的数据
   * @return 如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean prepareData() {
    mMap.put(mLPGrpEdorMainSchema, "UPDATE");
    mMap.put(mLPGrpEdorItemSchema, "UPDATE");
    mMap.put("update LPGrpEdorItem set EdorState='1' where EdorNo='" + tEdorNo +
             "' and EdorType='" + tEdorType + "'", "UPDATE");
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
