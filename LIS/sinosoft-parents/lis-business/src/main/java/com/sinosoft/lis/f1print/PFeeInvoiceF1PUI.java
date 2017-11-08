package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class PFeeInvoiceF1PUI implements PrintService{
private static Logger logger = Logger.getLogger(PFeeInvoiceF1PUI.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
          public CErrors mErrors=new CErrors();
          private VData mResult = new VData();
          //private VData mInputData = new VData();
          //业务处理相关变量
           /** 全局数据 */
          private GlobalInput mGlobalInput =new GlobalInput() ;
          private LJAPaySchema mLJAPaySchema=new LJAPaySchema();
          private LJSPaySchema mLJSPaySchema=new LJSPaySchema();
          private String mOperate="";
          private LOPRTManager2Schema mLOPRTManager2Schema = new LOPRTManager2Schema();
          public PFeeInvoiceF1PUI() {
          }
          /**
       传输数据的公共方法
       */
          public boolean submitData(VData cInputData, String cOperate)
          {
              mOperate=cOperate;
              if(  !cOperate.equals("FORECONFIRM") && !cOperate.equals("CONFIRM") &&!cOperate.equals("PRINT")&&!cOperate.equals("REPRINT"))
              {
                  buildError("submitData", "不支持的操作字符串");
                  return false;
              }

            // 得到外部传入的数据，将数据备份到本类中
            if( !getInputData(cInputData) ) {
              return false;
            }

            // 进行业务处理
            if( !dealData() ) {
              return false;
            }

            // 准备传往后台的数据
            VData vData = new VData();

            if( !prepareOutputData(vData) ) {
              return false;
            }

            PFeeInvoiceF1PBL tPFeeInvoiceF1PBL = new PFeeInvoiceF1PBL();
            logger.debug("Start PFeeInvoiceF1P UI Submit ...");

            if( !tPFeeInvoiceF1PBL.submitData(vData, cOperate) ) {
              if( tPFeeInvoiceF1PBL.mErrors.needDealError() ) {
                mErrors.copyAllErrors(tPFeeInvoiceF1PBL.mErrors);
                return false;
              } else {
                buildError("submitData", "PFeeInvoiceF1PBL发生错误，但是没有提供详细的出错信息");
                return false;
              }
            } else {
              mResult = tPFeeInvoiceF1PBL.getResult();
              return true;
            }
          }



          /**
           * 准备往后层输出所需要的数据
           * 输出：如果准备数据时发生错误则返回false,否则返回true
           */
          private boolean prepareOutputData(VData vData)
          {
            try
            {
                vData.clear();
                if (mOperate.equals("PRINT")||mOperate.equals("REPRINT"))
                {
                    vData.add(mGlobalInput);
                    vData.add(mLOPRTManager2Schema);
                }
                if (mOperate.equals("CONFIRM"))
                {
                    vData.add(mGlobalInput);
                    vData.add(mLJAPaySchema);
                }
                if (mOperate.equals("FORECONFIRM"))
                {
              //全局变量
                    vData.add(mGlobalInput);
                    vData.add(mLJSPaySchema);
                }
            }
            catch (Exception ex) {
              ex.printStackTrace();
              buildError("prepareOutputData", "发生异常");
              return false;
            }
            return true;
          }

          /**
           * 根据前面的输入数据，进行UI逻辑处理
           * 如果在处理过程中出错，则返回false,否则返回true
           */
          private boolean dealData()
          {
            return true;
          }

          /**
           * 从输入数据中得到所有对象
           * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
           */
          private boolean getInputData(VData cInputData)
          {
             if (mOperate.equals("PRINT")||mOperate.equals("REPRINT"))
             {
                 mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
                 mLOPRTManager2Schema.setSchema((LOPRTManager2Schema)cInputData.getObjectByObjectName("LOPRTManager2Schema",0));
             }

            if (mOperate.equals("CONFIRM"))
            {
                //全局变量
                mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
                mLJAPaySchema.setSchema((LJAPaySchema)cInputData.getObjectByObjectName("LJAPaySchema",0));
            }
            if (mOperate.equals("FORECONFIRM"))
           {
               //全局变量
               mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
               mLJSPaySchema.setSchema((LJSPaySchema)cInputData.getObjectByObjectName("LJSPaySchema",0));
            }

            if( mGlobalInput==null ) {
              buildError("getInputData", "没有得到足够的信息！");
              return false;
            }

            return true;
          }

          public VData getResult()
          {
            return this.mResult;
          }

          public CErrors getErrors()
          {
              return mErrors;
          }

          private void buildError(String szFunc, String szErrMsg)
          {
            CError cError = new CError( );

            cError.moduleName = "PFeeInvoiceF1PUI";
            cError.functionName = szFunc;
            cError.errorMessage = szErrMsg;
            this.mErrors.addOneError(cError);
          }
          public static void main(String[] args) {
            LJAPaySchema a = new LJAPaySchema();
            a.setPayNo("86110020040320000024");
            GlobalInput g = new GlobalInput();
            VData tVData = new VData();
            tVData.addElement(a);
            tVData.addElement(g);
            PFeeInvoiceF1PUI u = new PFeeInvoiceF1PUI();
            u.submitData(tVData,"CONFIRM");
            VData result = new VData();
            result= u.getResult();
          }
}
