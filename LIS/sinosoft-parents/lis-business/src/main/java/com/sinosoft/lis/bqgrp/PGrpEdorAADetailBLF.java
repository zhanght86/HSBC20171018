package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.bq.*;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Sinosoft</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class PGrpEdorAADetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PGrpEdorAADetailBLF.class);
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
  LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
  GlobalInput mGlobalInput = new GlobalInput();
  LPPolSchema mLPPolSchema = new LPPolSchema();
  LPDutySet mLPDutySet = new LPDutySet();
  private Reflections mRef = new Reflections();

  public PGrpEdorAADetailBLF() {
  }

  public VData getResult() {
    return mResult;
  }


  /**
   * 数据提交的公共方法
   * @param: cInputData 传入的数据
   * @param: cOperate 数据操作字符串
   * @return:
   */

  public boolean submitData(VData cInputData, String cOperate) {
    //将操作数据拷贝到本类中
    mInputData = (VData) cInputData.clone();
    mOperate = cOperate;

    if (mOperate != null && mOperate.equals("OnlyCheck")) {
      BqCalBase tBqCalBase = new BqCalBase();
      mResult.add(tBqCalBase);
      return true;
    }
    //得到外部传入的数据,将数据备份到本类中
    if (!getInputData()) {
      return false;
    }

    //数据校验
    if (!checkData()) {
      return false;
    }

    //数据准备操作
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
   * @return
   */
  private boolean getInputData() {
    try {
      mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData.
                             getObjectByObjectName(
          "LPGrpEdorItemSchema", 0);
      mLPEdorItemSchema = (LPEdorItemSchema) mInputData.
                          getObjectByObjectName(
          "LPEdorItemSchema", 0);
      mLPPolSchema = (LPPolSchema) mInputData.getObjectByObjectName(
          "LPPolSchema", 0);
      mLPDutySet = (LPDutySet) mInputData.getObjectByObjectName(
          "LPDutySet", 0);
      mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
          "GlobalInput", 0);
    } catch (Exception e) {
      mErrors.addOneError("接收数据失败");
      return false;
    }
    logger.debug("mLPDutySet.encode:" + mLPDutySet.encode());
    if (mLPGrpEdorItemSchema == null || mLPDutySet == null || mLPDutySet.size()==0) {
      CError.buildErr(this, "输入数据有误!");
      return false;
    }

    return true;
  }


  /**
   * 数据校验
   * @return boolean
   */
  private boolean checkData() {
    return true;
  }


  /**
   * 准备需要保存的数据
   */
  private boolean dealData() {
    PGrpEdorAADetailBL tPGrpEdorAADetailBL = new PGrpEdorAADetailBL();
    
    //查询团险合同PGrpCont
    LPGrpContDB tLPGrpContDB = new LPGrpContDB();
    tLPGrpContDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    tLPGrpContDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
    tLPGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
    LPGrpContSchema tLPGrpContSchema = null;
    if(!tLPGrpContDB.getInfo()){
    	
		if(tLPGrpContDB.mErrors.needDealError()){
			CError.buildErr(this, "查询团险险种信息失败!");
	        return false;
		}
		
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if(!tLCGrpContDB.getInfo()){
			CError.buildErr(this, "查询团体信息合同保单失败!");
			return false;
		} 
		tLPGrpContSchema = new LPGrpContSchema();
		mRef.transFields(tLPGrpContSchema, tLCGrpContDB.getSchema());
		tLPGrpContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		
		
    }else {
    	
    	tLPGrpContSchema = tLPGrpContDB.getSchema();
    	
    }
    
    //查询正在处理的增额业务的团险险种PGrpPol
    LCPolDB tLCPolDB = new LCPolDB();
    tLCPolDB.setPolNo(mLPPolSchema.getPolNo());
    if(!tLCPolDB.getInfo()){
    	CError.buildErr(this, "查询团险下个人险种信息失败!");
        return false;
    }
    
    String tGrpPolNo = tLCPolDB.getGrpPolNo();
    String tContNo = tLCPolDB.getContNo();
    
    LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
    tLPGrpPolDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    tLPGrpPolDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
    tLPGrpPolDB.setGrpPolNo(tGrpPolNo);
    LPGrpPolSchema tLPGrpPolSchema = null;
	if (!tLPGrpPolDB.getInfo()) {
		
		if(tLPGrpPolDB.mErrors.needDealError()){
			CError.buildErr(this, "查询团险险种信息失败!");
	        return false;
		}
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpPolNo(tGrpPolNo);
		if (!tLCGrpPolDB.getInfo()) {
			CError.buildErr(this, "查询团险险种信息失败!");
			return false;
		}
		tLPGrpPolSchema = new LPGrpPolSchema();
		mRef.transFields(tLPGrpPolSchema, tLCGrpPolDB.getSchema());
		tLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		
	} else {
		
		tLPGrpPolSchema = tLPGrpPolDB.getSchema();
	} 
	
	//查询正在处理的团险下个人保单PCont();
	
	LPContDB tLPContDB= new LPContDB();
	tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
	tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
	tLPContDB.setContNo(tContNo);
	LPContSchema tLPContSchema = null;
	if (!tLPContDB.getInfo()) {
		
		if(tLPContDB.mErrors.needDealError()){
			CError.buildErr(this, "查询团险险种信息失败!");
	        return false;
		}
		
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tContNo);
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "查询团险险种信息失败!");
			return false;
		}
		tLPContSchema = new LPContSchema();
		mRef.transFields(tLPContSchema, tLCContDB.getSchema());
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
	} else {
		
		tLPContSchema = tLPContDB.getSchema();
	}
	logger.debug("开始处理团体保单号为"+tLPGrpContSchema.getGrpContNo()+",团体险种号为"+tLPGrpPolSchema.getGrpPolNo()+",个人保单号为"+tLPContSchema.getContNo()+"的保全增额业务");
    if (!tPGrpEdorAADetailBL.submitData(mInputData, mOperate)) {
      this.mErrors.copyAllErrors(tPGrpEdorAADetailBL.mErrors);
      CError tError = new CError();
      tError.moduleName = "PEdorPTDetailBLF";
      tError.functionName = "dealData";
      tError.errorMessage = "数据处理失败!";
      this.mErrors.addOneError(tError);
      return false;
    }
    mResult.clear();
    mResult = tPGrpEdorAADetailBL.getResult();
    return true;
  }



  public CErrors getErrors() {
    return mErrors;
  }
}
