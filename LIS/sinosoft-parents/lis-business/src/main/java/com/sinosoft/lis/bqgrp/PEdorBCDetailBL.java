package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import java.util.*;

public class PEdorBCDetailBL {
private static Logger logger = Logger.getLogger(PEdorBCDetailBL.class);

  public CErrors mErrors = new CErrors();
  private VData mInputData;
  private VData mResult = new VData();
  private String mOperate;

  /** 全局数据 */
  private GlobalInput mGlobalInput = new GlobalInput();
  private LPEdorItemSet outLPEdorItemSet = new LPEdorItemSet();
  private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
  private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
  private LPBnfSet mLPBnfSet = new LPBnfSet();

  /**
   * Consturctor
   **/
  public PEdorBCDetailBL() {}

  /**
   *
   **/
  public void setOperate(String cOperate) {
    this.mOperate = cOperate;
  }

  /**
   *
   **/
  public String getOperate() {
    return this.mOperate;
  }

  /**
   *
   **/
  public boolean submitData(VData cInputData, String cOperate) {

    logger.debug("=====This is PEdorBCDetailBL->submitData=====\n");

    //将操作数据拷贝到本类中
    mInputData = (VData) cInputData.clone();
    this.setOperate(cOperate);

    //得到外部传入的数据,将数据备份到本类中
    getInputData(cInputData);

    //数据查询业务处理(queryData())
    if (cOperate.equals("QUERY||MAIN") ||
        cOperate.equals("QUERY||FIRST") ||
        cOperate.equals("QUERY||PERSON")) {
      if (!queryData()) {
        return false;
      }
      else {
        return true;
      }
    }
    //数据校验操作（checkdata)
    if (!checkData()) {
      return false;
    }
    //数据准备操作（preparedata())
    if (!prepareData()) {
      return false;
    }
    cOperate = this.getOperate();
    logger.debug("   ----- oper" + cOperate);
    // 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
    if (cOperate.equals("INSERT||MAIN") ||
        cOperate.equals("INSERT||GRPMAIN")||
        cOperate.equals("INSERT||PERSON")) {
      PubSubmit tPEdorBCBLS = new PubSubmit();
      logger.debug("-----------------------01");
      if (tPEdorBCBLS.submitData(mResult, "") == false) {
        //@@错误处理
        this.mErrors.copyAllErrors(tPEdorBCBLS.mErrors);
        return false;
      }
      logger.debug("-----------------------02");
    }
    return true;
  }
  public VData getResult() {
    return mResult;
  }

  /**
   *
   **/
  private boolean queryData(){

    logger.debug("=====This is PEdorBCDetailBL->queryData=====\n");
    if (this.getOperate().equals("QUERY||MAIN") ||
        this.getOperate().equals("QUERY||FIRST")) {
      LPBnfBL tLPBnfBL = new LPBnfBL();
      LPBnfSet tLPBnfSet = new LPBnfSet();

      //定义LPEdorMain表操作变量
      LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
      LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
      LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

      /*从LPEdorMain表中查找当前批改记录。通过CalCode字段判断是否保存过申请
       *依此来分别第一次查询和保存后的查询（包括又一次进入项目明细）。*/
      tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
      tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
      tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
      tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
      tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
      tLPEdorItemSet = tLPEdorItemDB.query();

      if (tLPEdorItemSet.size() != 1) {
          //报错:当前批改信息记录个数有误！
          CError.buildErr(this, "个人批改表（LPEdorMain）:批改信息记录个数有误!");
          return false;
      }
      //得到当前保全申请的BC项目批改记录。
      tLPEdorItemSchema = tLPEdorItemSet.get(1);
      logger.debug("   ----- Now Query");
      tLPBnfSet = tLPBnfBL.queryLPBnf(tLPEdorItemSchema);

      if (tLPBnfSet.size() < 1) {
        logger.debug("   ----- There is no Data.");
        mInputData.clear();
        mResult.clear();
        return true;
      }
      String tReturn;
      tReturn = tLPBnfSet.encode();
      tReturn = "0|" + tLPBnfSet.size() + "^" + tReturn;
      logger.debug("tReturn: " + tReturn);
      mInputData.clear();
      mInputData.add(tLPBnfSet);
      mResult.clear();
      mResult.add(tReturn);
    }
    else if (this.getOperate().equals("QUERY||PERSON")) {
        String tag = "";
      logger.debug("   -----Start person query.");
      //先查p表
      LPBnfDB tLPBnfDB = new LPBnfDB();
      LPBnfSet tLPBnfSet = new LPBnfSet();
      String sql ="select * from lpbnf where  edorno='"+
                  mLPEdorItemSchema.getEdorNo()+"' and edortype='"+
                  mLPEdorItemSchema.getEdorType()+"' and insuredno='"+
                  mLPEdorItemSchema.getInsuredNo()+"'";
      tLPBnfSet = tLPBnfDB.executeQuery(sql);
      logger.debug("sql = "+sql);
      int size = tLPBnfSet.size();
      if(size>0)
          tag = sql;
      //查p表有没有其它项目做过修改
      if (size==0){

        sql = "select * from lpbnf where  insuredno='" +
            mLPEdorItemSchema.getInsuredNo() + "' and ContNo = '"+
            mLPEdorItemSchema.getContNo()+"' and (MakeDate<'" +
            mLPEdorItemSchema.getMakeDate() + "' or (MakeDate='" +
            mLPEdorItemSchema.getMakeDate() + "' and MakeTime<='" +
            mLPEdorItemSchema.getMakeTime() + "')) order by MakeDate,MakeTime desc";
        logger.debug("SQL:  ====   "+sql);
        tLPBnfSet = tLPBnfDB.executeQuery(sql);
        size = tLPBnfSet.size();
        if(size > 0)
            tag = sql;
        //再查c表
        if (size == 0) {
          LCBnfDB tLCBnfDB = new LCBnfDB();
          LCBnfSet tLCBnfSet = new LCBnfSet();
          tLCBnfDB.setPolNo(mLPEdorItemSchema.getPolNo());
          tLCBnfDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
          tLCBnfSet = tLCBnfDB.query();
          size = tLCBnfSet.size();
          if (size != 0) {
              tag = "get Data from LCBnf.......................";
            for (int i = 1; i <= tLCBnfSet.size(); i++) {
              LPBnfSchema tempSchema = new LPBnfSchema();
              Reflections ref = new Reflections();
              ref.transFields(tempSchema, tLCBnfSet.get(i));
              tempSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
              tempSchema.setEdorType(mLPEdorItemSchema.getEdorType());
              tLPBnfSet.add(tempSchema);
            }
          }
        }
      }

      if (tLPBnfSet.size() < 1) {
        logger.debug("   ----- There is no Data.");
        mInputData.clear();
        mResult.clear();
        return true;
      }
      String tReturn;
      tReturn = tLPBnfSet.encode();
      tReturn = "0|" + tLPBnfSet.size() + "^" + tReturn;
      logger.debug("tReturn: " + tReturn);
      mInputData.clear();
      mInputData.add(tLPBnfSet);
      mResult.clear();
      mResult.add(tReturn);
      logger.debug("tag -=-=-=-=-=-=-=-=-=-=-=-= "+tag);
    }
    return true;
  }

  /**
   *
   **/
  private void getInputData(VData cInputData) {

    logger.debug("=====This is PEdorBCDetailBL->getInputData=====\n");
    mLPBnfSet = (LPBnfSet) cInputData.getObjectByObjectName("LPBnfSet", 0);
    mLPEdorItemSchema = (LPEdorItemSchema)
                        cInputData.getObjectByObjectName("LPEdorItemSchema", 0);
    mGlobalInput = (GlobalInput)
                   cInputData.getObjectByObjectName("GlobalInput", 0);
  }

  /**
   *
   **/
  private boolean checkData() {
    boolean flag = true;
    if (this.getOperate().equals("INSERT||MAIN")) {
      LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
      LPEdorItemSchema tempLPEdorItemSchema = new LPEdorItemSchema();
      tempLPEdorItemSchema.setSchema(mLPEdorItemSchema);
//      tempLPEdorItemSchema.setPolNo("000000");
      tLPEdorItemDB.setSchema(tempLPEdorItemSchema);
      if (!tLPEdorItemDB.getInfo()) {
        CError tError = new CError();
        tError.moduleName = "PBnfBL";
        tError.functionName = "Preparedata";
        tError.errorMessage = "无保全申请数据!";
        logger.debug("!! " + tError);
        this.mErrors.addOneError(tError);
        return false;
      }
      mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
      if (tLPEdorItemDB.getEdorState().trim().equals("2")) {
        CError tError = new CError();
        tError.moduleName = "PBnfBL";
        tError.functionName = "Preparedata";
        tError.errorMessage = "该保全已经申请确认不能修改!";
        logger.debug("!! " + tError);
        this.mErrors.addOneError(tError);
        return false;
      }
    }
    else if(this.getOperate().equals("INSERT||PERSON")){
      LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
      //      tLPEdorItemDB.setSchema(mLPEdorItemSchema);
      tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
      tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
      tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
      tLPEdorItemDB.setPolNo("000000");
      tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
      LPEdorItemSet testLPEdorItemSet = new LPEdorItemSet();
      testLPEdorItemSet = tLPEdorItemDB.query();
      //logger.debug("ttttttttt");
      if (testLPEdorItemSet.size() < 1) {
        CError tError = new CError();
        tError.moduleName = "PBnfBL";
        tError.functionName = "Preparedata";
        tError.errorMessage = "无保全申请数据!";
        logger.debug("!! " + tError);
        this.mErrors.addOneError(tError);
        return false;
      }

      mLPEdorItemSchema.setSchema(testLPEdorItemSet.get(1));
      if (mLPEdorItemSchema.getEdorState().trim().equals("2")) {
        CError tError = new CError();
        tError.moduleName = "PBnfBL";
        tError.functionName = "Preparedata";
        tError.errorMessage = "该保全已经申请确认不能修改!";
        logger.debug("!! " + tError);
        this.mErrors.addOneError(tError);
        return false;
      }

    }
    if (mLPBnfSet.size() > 0) {
      LPBnfSet tLPBnfSet = new LPBnfSet();
      for (int i = 1; i <= mLPBnfSet.size(); i++) {
        LDPersonSchema tLDPersonSchema = new LDPersonSchema();
        if ( (mLPBnfSet.get(i).getName() == null) ||
            mLPBnfSet.get(i).getName().trim().equals("")) {
          CError tError = new CError();
          tError.moduleName = "PBnfBL";
          tError.functionName = "checkdata";
          tError.errorMessage = "客户姓名不能为空!";
          this.mErrors.addOneError(tError);
          return false;
        }
        if ( (mLPBnfSet.get(i).getCustomerNo() != null) &&
            !mLPBnfSet.get(i).getCustomerNo().trim().equals("")) {
          LDPersonDB tLDPersonDB = new LDPersonDB();
          tLDPersonDB.setCustomerNo(mLPBnfSet.get(i).getCustomerNo());
          if (!tLDPersonDB.getInfo()) {
            CError tError = new CError();
            tError.moduleName = "PBnfBL";
            tError.functionName = "checkdata";
            tError.errorMessage = "客户号有误,请重新输入!";
            this.mErrors.addOneError(tError);
            return false;
          }
          else {
            tLDPersonSchema = tLDPersonDB.getSchema();
          }
        }
        if (i == 1) {
          tLPBnfSet.add(mLPBnfSet.get(i).getSchema());
        }
        else {
          boolean tBnfFlag = false;
          for (int j = 1; j <= tLPBnfSet.size(); j++) {
//            logger.debug(mLPBnfSet.get(i).getBnfType());
//            logger.debug(tLPBnfSet.get(j).getBnfType());
//            logger.debug(mLPBnfSet.get(i).getBnfGrade());
//            logger.debug(tLPBnfSet.get(j).getBnfGrade());
            if (mLPBnfSet.get(i).getBnfType().equals(tLPBnfSet.get(
                j).getBnfType()) &&
                mLPBnfSet.get(i).getBnfGrade().equals(tLPBnfSet.get(
                j).getBnfGrade())) {
              tLPBnfSet.get(j).setBnfLot(mLPBnfSet.get(i)
                                         .getBnfLot() +
                                         tLPBnfSet.get(j).getBnfLot());
              tBnfFlag = true;
            }
          }
          if (!tBnfFlag) {
            tLPBnfSet.add(mLPBnfSet.get(i).getSchema());
          }
        }
      }
      for (int i = 1; i <= tLPBnfSet.size(); i++) {
        if (tLPBnfSet.get(i).getBnfLot() != 1) {
          CError tError = new CError();
          tError.moduleName = "PBnfBL";
          tError.functionName = "checkdata";
          tError.errorMessage = "受益级别"+tLPBnfSet.get(i).getBnfGrade() +"的受益份额之和不等于一!";
          this.mErrors.addOneError(tError);
          return false;
        }
      }
    }
    return flag;
  }
  private boolean prepareData() {
    logger.debug("   ----- start prepare Data....");
    String cOperate;
    LPBnfSet aLPBnfSet = new LPBnfSet();
    //准备个人批改受益人的信息
    for (int i = 1; i <= mLPBnfSet.size(); i++) {
      LPBnfSchema tLPBnfSchema = new LPBnfSchema();
      tLPBnfSchema.setSchema(mLPBnfSet.get(i));
      tLPBnfSchema.setBnfNo(i);
      tLPBnfSchema.setContNo(mLPEdorItemSchema.getContNo());
      tLPBnfSchema.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
      //tLPBnfSchema.setPolNo(mLPEdorItemSchema.getPolNo());
      tLPBnfSchema.setOperator(mGlobalInput.Operator);
      tLPBnfSchema.setMakeDate(PubFun.getCurrentDate());
      tLPBnfSchema.setMakeTime(PubFun.getCurrentTime());
      tLPBnfSchema.setModifyDate(PubFun.getCurrentDate());
      tLPBnfSchema.setModifyTime(PubFun.getCurrentTime());
      aLPBnfSet.add(tLPBnfSchema);
      logger.debug("Bnflots : " + tLPBnfSchema.getBnfLot());
    }
    //设置个儿保全主表到保全申请状态
    setLPEdorItem();
    LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
    tLPGrpEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
    tLPGrpEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
    tLPGrpEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
    tLPGrpEdorItemDB.setGrpContNo(mLPEdorItemSchema.getGrpContNo());
    MMap map = new MMap();
    if(tLPGrpEdorItemDB.getInfo()){
        LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
        tLPGrpEdorItemSchema = tLPGrpEdorItemDB.getSchema();
        tLPGrpEdorItemSchema.setEdorState("1");
        map.put(tLPGrpEdorItemSchema,"DELETE&INSERT");
    }
    LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
    tLPEdorMainDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
    tLPEdorMainDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
    tLPEdorMainDB.setContNo(mLPEdorItemSchema.getContNo());
    if(tLPEdorMainDB.getInfo()){
        LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
        tLPEdorMainSchema = tLPEdorMainDB.getSchema();
        tLPEdorMainSchema.setEdorState("1");
        map.put(tLPEdorMainSchema,"DELETE&INSERT");
    }
    mInputData.clear();

    map.put(outLPEdorItemSet, "DELETE&INSERT");
    map.put("delete from lpbnf where edorno='"+mLPEdorItemSchema.getEdorNo()+"' and contno='"+mLPEdorItemSchema.getContNo()+"' and InsuredNo='"+mLPEdorItemSchema.getInsuredNo()+"'","DELETE");
    map.put(aLPBnfSet, "DELETE&INSERT");
    mResult.add(map);
    return true;
  }
  private void setLPEdorItem() {
    String strEdorType = "";
    mLPEdorItemSchema.setEdorState("1"); //批改申请
    mLPEdorItemSchema.setEdorAppDate(PubFun.getCurrentDate());
    mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
    mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
    mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
    //设置修改标记(保存申请)
    strEdorType = mLPEdorItemSchema.getEdorType();
    logger.debug("==> Change Flag of Chenged, Flag = " + strEdorType + "YESC");
    outLPEdorItemSet.add(mLPEdorItemSchema);
  }
}
