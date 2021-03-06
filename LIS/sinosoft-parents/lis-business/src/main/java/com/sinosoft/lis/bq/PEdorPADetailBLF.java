package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;

public class PEdorPADetailBLF implements EdorDetail{
private static Logger logger = Logger.getLogger(PEdorPADetailBLF.class);
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
      private TransferData mTransferData;

    public PEdorPADetailBLF() {
    }
    public boolean submitData(VData cInputData, String cOperate)
 {
   //将操作数据拷贝到本类中
   mInputData = (VData) cInputData.clone();
   mOperate = cOperate;

   //得到外部传入的数据
   if (!getInputData())
   {
       return false;
   }
   logger.debug("PEdorPADetailBLF after getInputData...");

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
  * 数据操作业务处理
  * @return: boolean
  */
 private boolean dealData()
 {

     PEdorPADetailBL tPEdorPADetailBL = new PEdorPADetailBL();

     if (!tPEdorPADetailBL.submitData(mInputData, mOperate))
     {
         mErrors.copyAllErrors(tPEdorPADetailBL.mErrors);
         return false;
     }
     mResult = tPEdorPADetailBL.getResult();
     map.add((MMap) mResult.getObjectByObjectName("MMap", 0));

     return true;
 }

 /**
  * 得到外部传入的数据
  * @return: boolean
  */
 private boolean getInputData()
 {

     return true;
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
         tError.moduleName = "PEdorPGDetailBLF";
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
