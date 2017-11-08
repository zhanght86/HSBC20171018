/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;
import java.sql.Connection;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import java.text.SimpleDateFormat;
import com.sinosoft.lis.vschema.LLSubmitApplySet;
import com.sinosoft.lis.tb.LCSignLogBL;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;

/**
 * <p>Title: 呈报处理</p>
 * <p>Description:呈报处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author
 * @version 1.0
 */
public class LLSubmitApplyDealBL
{
private static Logger logger = Logger.getLogger(LLSubmitApplyDealBL.class);

       /** 错误处理类，每个需要错误处理的类中都放置该类 */
       public CErrors mErrors = new CErrors();
       /** 往后面传输数据的容器 */
       private VData mInputData;
       /** 往界面传输数据的容器 */
       private VData mResult = new VData();
       /** 数据操作字符串 */
       private String mOperate;
       private MMap map = new MMap();

        private String CurrentDate = PubFun.getCurrentDate();
        private String CurrentTime = PubFun.getCurrentTime();
        private LLSubmitApplySchema mLLSubmitApplySchema= new LLSubmitApplySchema();
        private GlobalInput mG = new GlobalInput();
        private TransferData mTransferData = new TransferData();
        private boolean mIsReportExist;
        private String updateType_Sql;
        //定义变量
        private String mClmNO;
        private String mSubNO;
        private String mSubCount;
        private String mDispType;
        private String mDispIdea;

        public LLSubmitApplyDealBL()
        {
        }


        public static void main(String[] args)
        {
        }
        /**
        * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
        * @param cInputData 传入的数据,VData对象
        * @param cOperate 数据操作字符串
        * @return 布尔值（true--提交成功, false--提交失败）
       */
      public boolean submitData(VData cInputData,String cOperate)
      {
          logger.debug("----------LLSubmitApplyDealBL begin submitData----------");
          //将操作数据拷贝到本类中
          mInputData = (VData)cInputData.clone() ;
          this.mOperate = cOperate;
          //得到外部传入的数据,将数据备份到本类中
          if (!getInputData())
          {
              return false;
          }
          logger.debug("----------LLSubmitApplyDealBL after getInputData----------");

          //检查数据合法性
          if (!checkInputData())
          {
              return false;
          }
          logger.debug("----------LLSubmitApplyDealBL after checkInputData----------");
          //进行业务处理
          if (!dealData(cOperate))
          {
              return false;
          }
          logger.debug("----------LLSubmitApplyDealBL after dealData----------");
          //准备往后台的数据
          if (!prepareOutputData())
          {
              return false;
          }
          logger.debug("----------LLSubmitApplyDealBL after prepareOutputData----------");
          mInputData = null;
          return true;
      }

     public VData getResult()
      {
          return mResult;
      }
      /**
       * 从输入数据中得到所有对象
       * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
       */
      private boolean getInputData()
      {
          logger.debug("--start getInputData()");
          //获取页面信息
          mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);//按类取值
          //mIsReportExist =(String) mInputData.getObjectByObjectName("IsReportExist", 0);
          mLLSubmitApplySchema = (LLSubmitApplySchema) mInputData.getObjectByObjectName( "LLSubmitApplySchema", 0);
          mTransferData = (TransferData) mInputData.getObjectByObjectName("mTransferData",0);
          if (mLLSubmitApplySchema == null)
          {
              // @@错误处理
              CError tError = new CError();
              tError.moduleName = "LLSubmitApplyBL";
              tError.functionName = "getInputData";
              tError.errorMessage = "接受的信息全部为空!";
              this.mErrors.addOneError(tError);
              return false;
          }
         mClmNO=mLLSubmitApplySchema.getClmNo();
         mSubNO=mLLSubmitApplySchema.getSubNo();
         mSubCount=mLLSubmitApplySchema.getSubCount();
         mDispType=mLLSubmitApplySchema.getDispType();
         mDispIdea=mLLSubmitApplySchema.getDispIdea();
          return true;
      }

      /**
       * 校验传入的报案信息是否合法
       * 输出：如果发生错误则返回false,否则返回true
       */
      private boolean checkInputData()
      {
          logger.debug("----------LLSubmitApplyDealBL begin checkInputData----------");
          return true;
      }

  /**
  * 数据操作类业务处理
  * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
  * 用于处理各种操作，本页包含三个操作：提起调查（修改）、呈报（添加）、回复（修改）
  */
 private boolean dealData(String cOperate)
 {
     logger.debug("------LLSubmitApplyDealBL start dealData-----"+cOperate);
     boolean tReturn = false;
     //呈报总公司，插入记录
     if (cOperate.equals("INSERT||Reporthead"))
       {
           logger.debug("------LLSubmitApplyDealBL start dealData-----"+cOperate);
         //插入新记录
         LLSubmitApplyDB tLLSubmitApplyDB = new LLSubmitApplyDB();
         tLLSubmitApplyDB.setClmNo(mLLSubmitApplySchema.getClmNo());
         tLLSubmitApplyDB.setSubCount(mLLSubmitApplySchema.getSubCount());
         tLLSubmitApplyDB.setSubNo(mLLSubmitApplySchema.getSubNo());
         tLLSubmitApplyDB.getInfo();
         LLSubmitApplySchema tLLSubmitApplySchema= new LLSubmitApplySchema();
         tLLSubmitApplySchema.setSchema(tLLSubmitApplyDB.getSchema());
         tLLSubmitApplySchema.setSubCount("1"); //呈报次数
         tLLSubmitApplySchema.setSubType("1"); //呈报类型(置“1--分公司向总公司”)
         tLLSubmitApplySchema.setSubDesc(mLLSubmitApplySchema.getSubDesc());
//         tLLSubmitApplySchema.setDispType(mLLSubmitApplySchema.getDispType());
//         tLLSubmitApplySchema.setDispIdea(mLLSubmitApplySchema.getDispIdea());
         //处理类型和处理意见通过页面传递
//         tLLSubmitApplySchema.setDispDate(CurrentDate);//处理日期
         tLLSubmitApplySchema.setOperator(mG.Operator); //操作员
         tLLSubmitApplySchema.setMakeDate(CurrentDate); //入机日期
         tLLSubmitApplySchema.setMakeTime(CurrentTime); //入机时间
         tLLSubmitApplySchema.setModifyDate(CurrentDate); //最后修改日期
         tLLSubmitApplySchema.setModifyTime(CurrentTime); //最后修改时间

         if(mLLSubmitApplySchema.getSubCount().equals("0"))
         {
             //初次向总公司承包
             map.put(tLLSubmitApplySchema, "INSERT");  //提交数据添加新记录
             //更新原记录DispType的SQL语句
             String sSql = "update LLSubmitApply set DispType='" + mDispType +"'"
                      +" ,DispPer='"+mG.Operator+"'"
                      +" ,DispDept='"+mG.ComCode+"'"
                      +" ,DispDate='"+CurrentDate+"'"
                      +" where ClmNO='" + mClmNO +"'"
                      +" and SubNO='" + mSubNO + "' "
                      +" and SubCount='" + mSubCount + "'";
             map.put(sSql, "UPDATE"); //提交语句修改原记录

         }
         else
         {
             map.put(tLLSubmitApplySchema, "DELETE&INSERT");  //提交数据添加新记录
         }


         tReturn = true;
         }

     if (cOperate.equals("UPDATE||Investgate")) //提起调查处理模块
        {
            logger.debug("------LLSubmitApplyDealBL start dealData-----"+cOperate);
              //用来查找需要的修改记录
               LLSubmitApplyDB tLLSubmitApplyDB = new LLSubmitApplyDB();
               tLLSubmitApplyDB.setClmNo(mLLSubmitApplySchema.getClmNo());
               tLLSubmitApplyDB.setSubCount(mLLSubmitApplySchema.getSubCount());
               tLLSubmitApplyDB.setSubNo(mLLSubmitApplySchema.getSubNo());
               tLLSubmitApplyDB.getInfo();
               LLSubmitApplySchema tLLSubmitApplySchema= new LLSubmitApplySchema();
               tLLSubmitApplySchema.setSchema(tLLSubmitApplyDB.getSchema());
               //修改字段
               tLLSubmitApplySchema.setSubState("1"); //呈报状态（置“1--完成”）
               tLLSubmitApplySchema.setDispPer(mG.Operator);///** 承接人员编号 */
               tLLSubmitApplySchema.setDispDept(mG.ComCode);///** 承接机构代码 */
               tLLSubmitApplySchema.setDispDate(CurrentDate);//处理日期
               tLLSubmitApplySchema.setDispType(mLLSubmitApplySchema.getDispType());
               tLLSubmitApplySchema.setDispIdea(mLLSubmitApplySchema.getDispIdea());
                //处理类型和处理意见通过页面传递
               tLLSubmitApplySchema.setDispDate(CurrentDate);//处理日期
               tLLSubmitApplySchema.setOperator(mG.Operator);//操作员
               tLLSubmitApplySchema.setModifyDate(CurrentDate);//最后修改日期
               tLLSubmitApplySchema.setModifyTime(CurrentTime);//最后修改时间
               map.put( tLLSubmitApplySchema, "DELETE&INSERT");
               tReturn = true;
         }

     if (cOperate.equals("UPDATE||Replyport"))     //回复意见处理模块
      {
          logger.debug("------LLSubmitApplyDealBL start dealData-----" +
                             cOperate);

          //用来查找需要的修改记录
          LLSubmitApplyDB tLLSubmitApplyDB = new LLSubmitApplyDB();
          tLLSubmitApplyDB.setClmNo(mLLSubmitApplySchema.getClmNo());
          tLLSubmitApplyDB.setSubCount(mLLSubmitApplySchema.getSubCount());
          tLLSubmitApplyDB.setSubNo(mLLSubmitApplySchema.getSubNo());
          //修改分公司 记录--呈报状态（置“1--完成”）、处理类型和处理意见
          if (tLLSubmitApplyDB.getSubCount().equals("1"))
          {
              logger.debug("------修改 总公司记录,呈报状态（置“1--完成”）----");
              //修改 总公司记录的 呈报状态（置“1--完成”）
              LLSubmitApplyDB ttLLSubmitApplyDB = new LLSubmitApplyDB();
              ttLLSubmitApplyDB.setClmNo(mLLSubmitApplySchema.getClmNo());
              ttLLSubmitApplyDB.setSubCount(mLLSubmitApplySchema.getSubCount());
              ttLLSubmitApplyDB.setSubNo(mLLSubmitApplySchema.getSubNo());
              ttLLSubmitApplyDB.getInfo();
              LLSubmitApplySchema ttLLSubmitApplySchema = new
                      LLSubmitApplySchema();
              ttLLSubmitApplySchema.setSchema(ttLLSubmitApplyDB.getSchema());
              ttLLSubmitApplySchema.setSubState("1"); //呈报状态（置“1--完成”）
              map.put(ttLLSubmitApplySchema, "DELETE&INSERT");
              //
              tLLSubmitApplyDB.setSubCount("0");
          }
          tLLSubmitApplyDB.getInfo();
          LLSubmitApplySchema tLLSubmitApplySchema = new LLSubmitApplySchema();
          tLLSubmitApplySchema.setSchema(tLLSubmitApplyDB.getSchema());
          tLLSubmitApplySchema.setSubState("1"); //呈报状态（置“1--完成”）
          tLLSubmitApplySchema.setDispPer(mG.Operator);///** 承接人员编号 */
          tLLSubmitApplySchema.setDispDept(mG.ComCode);///** 承接机构代码 */
          tLLSubmitApplySchema.setDispDate(CurrentDate);//处理日期
          //处理类型和处理意见通过页面传递
          tLLSubmitApplySchema.setDispType(mLLSubmitApplySchema.getDispType());
          tLLSubmitApplySchema.setDispIdea(mLLSubmitApplySchema.getDispIdea());
          tLLSubmitApplySchema.setDispDate(CurrentDate); //处理日期
          tLLSubmitApplySchema.setOperator(mG.Operator); //操作员
          tLLSubmitApplySchema.setModifyDate(CurrentDate); //最后一次修改日期
          tLLSubmitApplySchema.setModifyTime(CurrentTime); //最后一次修改时间
          map.put(tLLSubmitApplySchema, "DELETE&INSERT");

          tReturn = true;
         }
         //更新mTransferData中的值
        if(!perpareMissionProp())
        {
            //@@错误处理
            CError tError = new CError();
            tError.moduleName = "LLReportBL";
            tError.functionName = "perpareMissionProp";
            tError.errorMessage = "为工作流准备数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

     return tReturn;
   }
   /**
    * 更新mTransferData中的值，为工作流准备数据
    * @return boolean
    */
   private boolean perpareMissionProp()
   {
       return true;
    }
   /**
 * 准备需要保存的数据
 * @return boolean
 */
private boolean prepareOutputData()
{
    try
    {
        mResult.clear();
        mResult.add(map);
        mResult.add(mG);
        mResult.add(mTransferData);
        mResult.add(mLLSubmitApplySchema);

    }
    catch (Exception ex)
    {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "LLLLSubmitApplyBL";
        tError.functionName = "prepareOutputData";
        tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
        this.mErrors.addOneError(tError);
        return false;
    }
    return true;
}


}
