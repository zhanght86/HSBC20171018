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
import java.io.*;

public class GetCredenceF1PUI implements PrintService{
private static Logger logger = Logger.getLogger(GetCredenceF1PUI.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
          public CErrors mErrors=new CErrors();

          private VData mResult = new VData();
          //private VData mInputData = new VData();
          //业务处理相关变量
           /** 全局数据 */
          private GlobalInput mGlobalInput =new GlobalInput() ;
          private LJAGetSchema mLJAGetSchema=new LJAGetSchema();
          public GetCredenceF1PUI() {
          }
          /**
           传输数据的公共方法
           */
          public boolean submitData(VData cInputData, String cOperate)
          {
            if( !cOperate.equals("PRINT") ) {
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

            GetCredenceF1PBL tGetCredenceF1PBL = new GetCredenceF1PBL();
            logger.debug("Start GetCredenceF1P UI Submit ...");

            if( !tGetCredenceF1PBL.submitData(vData, cOperate) ) {
              if( tGetCredenceF1PBL.mErrors.needDealError() ) {
                mErrors.copyAllErrors(tGetCredenceF1PBL.mErrors);
                return false;
              } else {
                buildError("submitData", "GetCredenceF1PBL发生错误，但是没有提供详细的出错信息");
                return false;
              }
            } else {
              mResult = tGetCredenceF1PBL.getResult();
              return true;
            }
          }



          /**
           * 准备往后层输出所需要的数据
           * 输出：如果准备数据时发生错误则返回false,否则返回true
           */
          private boolean prepareOutputData(VData vData)
          {
            try {
              vData.clear();
              vData.add(mGlobalInput);
              vData.add(mLJAGetSchema);
            } catch (Exception ex) {
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
            //全局变量
            mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
            mLJAGetSchema.setSchema((LJAGetSchema)cInputData.getObjectByObjectName("LJAGetSchema",0));

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

          private void buildError(String szFunc, String szErrMsg)
          {
            CError cError = new CError( );

            cError.moduleName = "GetCredenceF1PUI";
            cError.functionName = szFunc;
            cError.errorMessage = szErrMsg;
            this.mErrors.addOneError(cError);
          }

          public CErrors getErrors() {
            return mErrors;
          }

          public static void main(String[] args) {
            LJAGetSchema a = new LJAGetSchema();
            a.setActuGetNo("86110020030370000005");
            GlobalInput g = new GlobalInput();
            g.ManageCom = "001";
            g.Operator = "001";
            VData tVData = new VData();
            tVData.addElement(a);
            tVData.addElement(g);
            GetCredenceF1PUI u = new GetCredenceF1PUI();
            u.submitData(tVData,"PRINT");
            VData result = new VData();
            result= u.getResult();
          }
}
