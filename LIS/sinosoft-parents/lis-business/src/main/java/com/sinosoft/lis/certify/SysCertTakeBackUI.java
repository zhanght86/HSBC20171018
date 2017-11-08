/*
 * <p>ClassName: SysCertTakeBackUI </p>
 * <p>Description: SysCertTakeBackUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-10-29
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.service.BusinessService;

public class SysCertTakeBackUI implements BusinessService {
private static Logger logger = Logger.getLogger(SysCertTakeBackUI.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors=new CErrors();
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperation;

    //业务处理相关变量
     /** 全局数据 */
    private GlobalInput globalInput = new GlobalInput() ;
    private LZSysCertifySchema mLZSysCertifySchema = new LZSysCertifySchema();
  private String m_strWhere = "";

    public SysCertTakeBackUI ()
    {
    }

    /**
     * 传输数据的公共方法
     */
    public boolean submitData(VData cInputData,String cOperate)
    {
        mOperation = verifyOperate(cOperate);

        if( mOperation.equals("") ) {
            buildError("submitData", "不支持的操作字符串");
            return false;
        }

        //得到外部传入的数据,将数据备份到本类中
       if( !getInputData(cInputData) )
            return false;

      //进行业务处理
       if( !dealData() )
           return false;

      //准备往后台的数据
      VData vData = new VData();

        if( !prepareOutputData(vData) )
          return false;

       SysCertTakeBackBL sysCertTakeBackBL = new SysCertTakeBackBL();

       logger.debug("Start SysCertTakeBack BL Submit...");

         if( !sysCertTakeBackBL.submitData(vData, mOperation) ) {
             if( sysCertTakeBackBL.mErrors.needDealError() ) {
                 mErrors.copyAllErrors(sysCertTakeBackBL.mErrors);
                 buildError("submitData", "数据提交失败");
             } else {
                 buildError("submitData", "操作失败：SysCertTakeBackBL-->submitData，但是没有详细信息");
             }
             return false;
         }

         if(sysCertTakeBackBL.mErrors.needDealError())
         {
             mErrors.copyAllErrors(sysCertTakeBackBL.mErrors);
         }

       logger.debug("End SysCertTakeBack BL Submit...");

      if( mOperation.equals("QUERY||MAIN") ) {
          mResult = sysCertTakeBackBL.getResult();
      }

        return true;
  }

  public static void main(String[] args)
  {
    SysCertTakeBackUI tSysCertTakeBackUI = new SysCertTakeBackUI();

    LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();

    tLZSysCertifySchema.setCertifyCode("9995");
    tLZSysCertifySchema.setCertifyNo("86110020040110000063");
    tLZSysCertifySchema.setSendOutCom("D8611000211");
    tLZSysCertifySchema.setReceiveCom("A86");

    GlobalInput globalInput = new GlobalInput();

    globalInput.ComCode = "86";
    globalInput.ManageCom = "86";
    globalInput.Operator = "001";

    VData tVData = new VData();

    tVData.add(globalInput);
    tVData.add(tLZSysCertifySchema);

    tSysCertTakeBackUI.submitData(tVData, "INSERT||MAIN");

    logger.debug(tSysCertTakeBackUI.mErrors.getFirstError());
  }

  /**
  * 准备往后层输出所需要的数据
  * 输出：如果准备数据时发生错误则返回false,否则返回true
  */
     private boolean prepareOutputData(VData vData)
     {
      try {
            vData.add(globalInput);
            vData.add(mLZSysCertifySchema);
      vData.add(m_strWhere);
            return true;

        } catch (Exception ex) {
            buildError("prepareOutputData", "在准备传输数据时出错");
            return false;
        }
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
         try {
            globalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
        mLZSysCertifySchema.setSchema((LZSysCertifySchema)cInputData.getObjectByObjectName("LZSysCertifySchema",0));
      m_strWhere = (String)cInputData.getObjectByObjectName("String", 0);

        return true;

    } catch (Exception ex) {
        ex.printStackTrace();
        buildError("getInputData", "在获取输入信息的时候出错");
        return false;
    }
    }

    public VData getResult()
    {
      return mResult;
    }

  /*
   * add by kevin, 2002-09-23
   */
  private void buildError(String szFunc, String szErrMsg)
  {
    CError cError = new CError( );

    cError.moduleName = "SysCertTakeBackUI";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }

  private String verifyOperate(String szOperate)
  {
      String szReturn = "";
      String szOperates[] = {"INSERT||MAIN", "DELETE||MAIN", "UPDATE||MAIN", "QUERY||MAIN"};

      for(int nIndex = 0; nIndex < szOperates.length; nIndex ++) {
            if( szOperate.equals(szOperates[nIndex]) ) {
                szReturn = szOperate;
            }
      }

      return szReturn;
  }
  
  public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
