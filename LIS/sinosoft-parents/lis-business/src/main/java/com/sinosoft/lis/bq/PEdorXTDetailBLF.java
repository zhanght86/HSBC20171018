/*
 * @(#)PEdorXTDetailBLF.java    2005-11-17
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.vschema.LPEdorItemSet;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全- 协议退保变更明细保存提交类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author：zhangtao
 * @version：1.0
 * @CreateDate：2005-11-17
 */
public class PEdorXTDetailBLF implements EdorDetail
{
private static Logger logger = Logger.getLogger(PEdorXTDetailBLF.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData ;
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;
  /** 往后面传输的数据库操作 */
  private MMap map = new MMap();

  private GlobalInput mGlobalInput = new GlobalInput();
  private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
  private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();//批改补退费记录

  private String mCurrentDate = PubFun.getCurrentDate();
  private String mCurrentTime = PubFun.getCurrentTime();

  public PEdorXTDetailBLF()
  {
  }

  /**
   * 传输数据的公共方法
   * @param: cInputData 输入的数据
   * @param: cOperate 数据操作符
   * @return: boolean
   */
  public boolean submitData(VData cInputData, String cOperate)
  {
    //将操作数据拷贝到本类中
    mInputData = (VData) cInputData.clone();
    mOperate = cOperate;

    //*******************************************************************
    // mOperate 操作字符串说明：
    // EdorCal  - 退保费用计算
    // EdorSave - 调整金额后保存结果
    //*******************************************************************

    //得到外部传入的数据
    if (!getInputData())
    {
        return false;
    }
    logger.debug("after getInputData...");

    //数据操作业务处理
    if (!dealData())
    {
        return false;
    }
    logger.debug("after dealData...");

    //准备提交后台的数据
    if (!prepareOutputData())
    {
       return false;
    }
    logger.debug("after prepareOutputData...");

    //数据提交
    PubSubmit tPubSubmit = new PubSubmit();
    if (!tPubSubmit.submitData(mInputData, ""))
    {
        mErrors.copyAllErrors(tPubSubmit.mErrors);
        CError.buildErr(this,"数据提交失败!");
        return false;
    }
    mInputData = null;

    return true;
  }

  /**
   * 得到外部传入的数据
   * @return: boolean
   */
  private boolean getInputData()
  {
      mGlobalInput =
              (GlobalInput)
              mInputData.getObjectByObjectName("GlobalInput", 0);
      mLPEdorItemSchema =
              (LPEdorItemSchema)
                    mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
      if (mOperate.equals("EdorSave"))
      {
          //调整后的金额
          mLJSGetEndorseSet =
                  (LJSGetEndorseSet)
                  mInputData.getObjectByObjectName("LJSGetEndorseSet", 0);
      }
      return true;
  }

  /**
   * 数据操作业务处理
   * @return: boolean
   */
  private boolean dealData()
  {
      if (mOperate.equals("EdorCal")||mOperate.equals("EDORITEM|UPDATE")) //退保计算
      {
          //直接调用CT进行处理
          PEdorCTDetailBL tPEdorCTDetailBL = new PEdorCTDetailBL();
          if (!tPEdorCTDetailBL.submitData(mInputData, mOperate))
          {
              mErrors.copyAllErrors(tPEdorCTDetailBL.mErrors);
              return false;
          }
          mResult = tPEdorCTDetailBL.getResult();
          map.add((MMap) mResult.getObjectByObjectName("MMap", 0));
          
          //modify by jiaqiangli 2009-10-28 只有在计算保存时将两值置成相同
          //修改后getmoney保存实退金额 serialno保存应退现价 修改实际领取人信息不能再次修改
          if ("EdorCal".equals(mOperate)){
        	SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        	sqlbv.sql("update LJSGetEndorse set SerialNo=getmoney where   EndorsementNo = '?EndorsementNo?' " + "and FeeOperationType = '?FeeOperationType?' ");
        	sqlbv.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
        	sqlbv.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
        	  map.put(sqlbv ,"UPDATE");  
          }
				
      }
      double tSumIntvMoeny=0;
      if (mOperate.equals("EdorSave")) //保存调整后的金额
      {
  		   Reflections tRef = new Reflections();
          if (!queryLPEdorItem())
          {
              return false;
          }

          //XinYQ modified on 2007-06-04 : 不再采用直接等比例调整, 而是采用插入负值对冲
//          String DeleteSQL = new String("");
//          DeleteSQL = "delete from LJSGetEndorse "
//                    + " where 1 = 1 "
//                    +    "and EndorsementNo = '" + mLPEdorItemSchema.getEdorNo() + "' "
//                    +    "and FeeOperationType = '" + mLPEdorItemSchema.getEdorType() + "' "
//                    +    "and OtherNo = '000000'";
//          //logger.debug(UpdateSQL);
//          map.put(DeleteSQL, "DELETE");

          LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
          LJSGetEndorseSet tLJSGetEndorseSetNew = new LJSGetEndorseSet();

          double dEdorItemGetMoney = 0.0; //保全项目补退费金额合计

          for (int i = 1; i <= mLJSGetEndorseSet.size(); i++)
          {
              double dNewSumGetMoney = 0.0;
              double dSumGetMoney = 0.0;

              String QuerySQL = new String("");
              QuerySQL = "select * from LJSGetEndorse "
                    +     "where 1 = 1 "
                    +      " and getnoticeno='?getnoticeno?'"
                    +       "and EndorsementNo = '?EndorsementNo?' "
                    +       "and FeeOperationType = '?FeeOperationType?' "
                    +       "and PolNo = '?PolNo?' "
                    +       "and FeeFinaType = '?FeeFinaType?' "
                    +       "and SubFeeOperationType = '?SubFeeOperationType?' "
                    +       "and OtherNo <> '000000' and dutycode='?dutycode?'   and payplancode='?payplancode?'";
              SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
              sqlbv1.sql(QuerySQL);
              sqlbv1.put("getnoticeno", mLJSGetEndorseSet.get(i).getGetNoticeNo());
              sqlbv1.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
              sqlbv1.put("PolNo", mLJSGetEndorseSet.get(i).getPolNo());
              sqlbv1.put("FeeFinaType", mLJSGetEndorseSet.get(i).getFeeFinaType());
              sqlbv1.put("SubFeeOperationType", mLJSGetEndorseSet.get(i).getSubFeeOperationType());
              sqlbv1.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
              sqlbv1.put("dutycode", mLJSGetEndorseSet.get(i).getDutyCode());
              sqlbv1.put("payplancode", mLJSGetEndorseSet.get(i).getPayPlanCode());
              //logger.debug(UpdateSQL);

              LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
//              LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
              LJSGetEndorseSchema tLJSGetEndorseSchemaNew = new LJSGetEndorseSchema();
              tLJSGetEndorseSet = tLJSGetEndorseDB.executeQuery(sqlbv1);
              if (tLJSGetEndorseDB.mErrors.needDealError())
              {
                  CError.buildErr(this, "退保费用查询出现异常！");
                  return false;
              }
              if (tLJSGetEndorseSet == null || tLJSGetEndorseSet.size() < 1)
              {
                  CError.buildErr(this, "退保费用查询失败！");
                  return false;
              }

              //调整前金额
              dSumGetMoney = tLJSGetEndorseSet.get(1).getGetMoney();
//              for (int j = 1; j <= tLJSGetEndorseSet.size(); j++)
//              {

//              }
              //
              tRef.transFields(tLJSGetEndorseSchemaNew, tLJSGetEndorseSet.get(1));
              tLJSGetEndorseSchemaNew.setGetMoney(mLJSGetEndorseSet.get(i).getGetMoney());
//              if(!"1".equals(mLPEdorItemSchema.getEdorTypeCal()))
//              {
//                  //字段盗用，用SeriaNo 存放原来的金额
//                  tLJSGetEndorseSchemaNew.setSerialNo(String.valueOf(dSumGetMoney)); 
//              }


              //调整后金额
              dNewSumGetMoney = mLJSGetEndorseSet.get(i).getGetMoney();

              //判断该费用是否允许调整
              //针对基本保额应退金额根据调整后的金额确定费用财务科目是退保还是退费
              
              
              //MS都允许调整
//              String sFeeFinaType =
//                      canChange(mLJSGetEndorseSet.get(i).getPolNo(),
//                                mLJSGetEndorseSet.get(i).getFeeFinaType(),
//                                mLJSGetEndorseSet.get(i).getSubFeeOperationType(),
//                                mLJSGetEndorseSet.get(i).getGrpName(),
//                                dSumGetMoney, dNewSumGetMoney);
//              if (sFeeFinaType == null)
//              {
//                  return false;
//              }

//              for (int j = 1; j <= tLJSGetEndorseSet.size(); j++)
//              {
//                  //XinYQ modified on 2007-06-04 : 不再采用直接等比例调整, 而是采用插入负值对冲
//                  double dChangedMoney = mLJSGetEndorseSet.get(i).getGetMoney() - tLJSGetEndorseSet.get(j).getGetMoney();
//                  if (dChangedMoney != 0)
//                  {
//               	    不再保留协议差值明细，将其累计值保存在LPEodrItem中StandbyFLaf3中
//                      LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
//                      tLJSGetEndorseSchema = tLJSGetEndorseSet.get(j);
//                      LJSGetEndorseSchema tLJSGetEndorseSchemaNew = new LJSGetEndorseSchema();
//                      PubFun.copySchema(tLJSGetEndorseSchemaNew, tLJSGetEndorseSchema);
//                      tLJSGetEndorseSchemaNew.setGetMoney(dChangedMoney);
//                      tLJSGetEndorseSchemaNew.setOtherNo("000000"); // 一般为批单号, 这里标记为 000000 表示对冲
//                      tLJSGetEndorseSchemaNew.setOperator(mGlobalInput.Operator);
//                      tLJSGetEndorseSchemaNew.setMakeDate(mCurrentDate);
//                      tLJSGetEndorseSchemaNew.setMakeTime(mCurrentTime);
//                      tLJSGetEndorseSchemaNew.setModifyDate(mCurrentDate);
//                      tLJSGetEndorseSchemaNew.setModifyTime(mCurrentTime);
//                      tLJSGetEndorseSetNew.add(tLJSGetEndorseSchemaNew);
//                      tLJSGetEndorseSchemaNew = null;
//                      tLJSGetEndorseSchema = null;
//                  }
//              }
              //差值累计
              tSumIntvMoeny +=dSumGetMoney-dNewSumGetMoney;
              //保全部退费累计
              dEdorItemGetMoney += dNewSumGetMoney;
              //营改增 add zhangyingfeng 2016-07-14
              //价税分离 计算器
              TaxCalculator.calBySchema(tLJSGetEndorseSchemaNew);
              //end zhangyingfeng 2016-07-14
              tLJSGetEndorseSetNew.add(tLJSGetEndorseSchemaNew);
          }

          mLPEdorItemSchema.setGetMoney(dEdorItemGetMoney);
          mLPEdorItemSchema.setEdorState("3");
          
          mLPEdorItemSchema.setStandbyFlag1(String.valueOf(tSumIntvMoeny));
          //字段盗用
          //mLPEdorItemSchema.setEdorTypeCal("1");
          mLPEdorItemSchema.setModifyDate(mCurrentDate);
          mLPEdorItemSchema.setModifyTime(mCurrentTime);

          map.put(mLPEdorItemSchema, "UPDATE");
          map.put(tLJSGetEndorseSetNew, "UPDATE");
      }
      return true;
  }

  /**
   * 查询批改项目信息以及保单信息
   * @return boolean
   */
  private boolean queryLPEdorItem()
  {
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
      if (tLPEdorItemDB.mErrors.needDealError())
      {
          mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
          CError.buildErr(this, "查询批改项目信息失败！");
          return false;
      }
      mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
      mLPEdorItemSchema.setEdorReasonCode(sEdorReasonCode); ;
      mLPEdorItemSchema.setEdorReason(sEdorReason);
      mLPEdorItemSchema.setStandbyFlag2(sRelationToAppnt);

      return true;
  }

  /**
   * 判断该费用是否允许调整
   * @param sPolNo
   * @param sFeeFinatype
   * @param sSubFeeOperationType
   * @param sSubFeeOperationTypeName
   * @param dGetMoney
   * @param dNewGetMoney
   * @return String  如果允许调整，返回该费用财务科目，不允许调整或是出错，返回null
   */
  private String canChange(String sPolNo,
                           String sFeeFinatype,
                           String sSubFeeOperationType,
                           String sSubFeeOperationTypeName,
                           double dGetMoney, double dNewGetMoney)
  {
	  //浮点数比较必须用数学定义
      if (dGetMoney - dNewGetMoney==0) //没做调整
      {
          return sFeeFinatype;
      }
      else
      {
          if (sSubFeeOperationType.equals(BqCode.Get_BaseCashValue)) //基本保额应退金额允许调整
          {
//              if (dNewGetMoney > 0)
//              {
//                  CError.buildErr(this, "输入错误，退费金额不能为正!");
//                  return null;
//              }

              //计算该险种累计保费
              double dSumPrem = getSumPrem(sPolNo);
              if (dSumPrem == -1)
              {
                  return null;
              }

              dSumPrem = Math.max(Math.abs(dSumPrem), Math.abs(dGetMoney));

              //调整金额不允许超过累计保费
              if (Math.abs(dNewGetMoney) > Math.abs(dSumPrem))
              {
                  if (Math.abs(dGetMoney) == Math.abs(dSumPrem))
                  {
                      CError.buildErr(this, sSubFeeOperationTypeName +
                                      "调整不允许超过现金价值! " +
                                      "险种号：" + sPolNo +
                                      "现金价值：" + dGetMoney);
                  }
                  else
                  {
                      CError.buildErr(this, sSubFeeOperationTypeName +
                                      "调整不允许超过累计保费! " +
                                      "险种号：" + sPolNo +
                                      "累计保费：" + dSumPrem);
                  }
                  return null;
              }
              else if (Math.abs(dNewGetMoney) == Math.abs(dSumPrem)) //如果调整金额等于累计保费，财务科目算退费
              {
                  //return "TF";
                  return "TB"; //需求变动：犹豫期外的协议退保一律记入退保金科目 2005-12-02
              }
              else //如果调整金额小于累计保费，财务科目算退保金
              {
                  return "TB";
              }

          }
          else if (sSubFeeOperationType.equals(BqCode.Get_BonusCashValue) || //累计红利保额应退金额
                   sSubFeeOperationType.equals(BqCode.Get_FinaBonus))    //终了红利
          {
              //累计红利保额应退金额与终了红利两部分：不能随意予以修改，如果必需进行调整，只能修改为0
              if (dNewGetMoney != 0)
              {
                  CError.buildErr(this, sSubFeeOperationTypeName +
                                  "不能随意予以修改，如果必需进行调整，只能修改为0!");
                  return null;
              }
              return sFeeFinatype;
          }
          //那些不准调整，待和姚确认而定
          else  //其他各项欠款：不能予以调整
          {
              return sFeeFinatype;
//              CError.buildErr(this, sSubFeeOperationTypeName + "不允许调整!");
//              return null;
          }
      }
  }

  /**
   * 计算累计保费
   * @param sPolNo
   * @return double
   */
  private double getSumPrem(String sPolNo)
  {
      double dSumPrem = 0.0;

      BqPolBalBL tBqPolBalBL = new BqPolBalBL();
      if (!tBqPolBalBL.getSumPremPerPol(sPolNo, mLPEdorItemSchema.getEdorAppDate()))
      {
          CError.buildErr(this, "累计保费计算失败!");
          return -1;
      }
      dSumPrem = tBqPolBalBL.getCalResult();
      return dSumPrem;
    }

  /**
   * 准备提交后台的数据
   * @return: boolean
   */
  private boolean prepareOutputData()
  {
      try
      {
          mInputData.clear();
          mInputData.add(map);
      }
      catch (Exception ex)
      {
          // @@错误处理
          CError tError = new CError();
          tError.moduleName = "PEdorFMDetailBLF";
          tError.functionName = "prepareOutputData";
          tError.errorMessage = "在准备往后层处理所需要的数据时出错:" +
                                ex.toString();
          mErrors.addOneError(tError);
          return false;
      }

      return true;
  }

  /**
   * 返回处理结果
   * @return: VData
   */
  public VData getResult()
  {
      return mResult;
  }

  /**
   * 返回处理结果
   * @return: VData
   */
  public CErrors getErrors()
  {
      return mErrors;
  }

  public static void main(String[] args)
  {

  }
}
