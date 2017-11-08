/*
 * <p>ClassName: SysCertSendOutUI </p>
 * <p>Description: SysCertSendOutUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-10-29
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.service.BusinessService;

public class SysCertSendOutUI implements BusinessService {
private static Logger logger = Logger.getLogger(SysCertSendOutUI.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors=new CErrors();
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperation;

    //业务处理相关变量
     /** 全局数据 */
    private GlobalInput globalInput = new GlobalInput() ;
    private LZSysCertifySet mLZSysCertifySet = new LZSysCertifySet();

    public SysCertSendOutUI ()
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

       SysCertSendOutBL sysCertSendOutBL = new SysCertSendOutBL();

       logger.debug("Start SysCertSendOut BL Submit...");

       if( !sysCertSendOutBL.submitData(vData, mOperation) ) {
           if( sysCertSendOutBL.mErrors.needDealError() ) {
               mErrors.copyAllErrors(sysCertSendOutBL.mErrors);
               buildError("submitData", "数据提交失败");
           } else {
               buildError("submitData", "操作失败：SysCertSendOutBL-->submitData，但是没有详细信息");
           }
           return false;
       }
       //捕获产生的警告信息但并不返回false
         if( sysCertSendOutBL.mErrors.needDealError() )
         {
             mErrors.copyAllErrors(sysCertSendOutBL.mErrors);
         }

       logger.debug("End SysCertSendOut BL Submit...");

       if( mOperation.equals("QUERY||MAIN") ) {
           mResult = sysCertSendOutBL.getResult();
       }

        return true;
  }

  public static void main(String[] args)
  {
      SysCertSendOutUI tSysCertSendOutUI = new SysCertSendOutUI();

      LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();

      tLZSysCertifySchema.setCertifyCode("1113");
      tLZSysCertifySchema.setCertifyNo("1113");
      tLZSysCertifySchema.setSendOutCom("A86");
      tLZSysCertifySchema.setReceiveCom("A8611");

      LZSysCertifySet tLZSysCertifySet = new LZSysCertifySet();

      tLZSysCertifySet.add(tLZSysCertifySchema);

      GlobalInput globalInput = new GlobalInput();

      globalInput.ComCode = "86";
      globalInput.ManageCom = "86";
      globalInput.Operator = "001";

      VData tVData = new VData();

      tVData.add(globalInput);
      tVData.add(tLZSysCertifySet);

      tSysCertSendOutUI.submitData(tVData, "INSERT||MAIN");

      logger.debug(tSysCertSendOutUI.mErrors.getFirstError());
  }

  /**
  * 准备往后层输出所需要的数据
  * 输出：如果准备数据时发生错误则返回false,否则返回true
  */
     private boolean prepareOutputData(VData vData)
     {
      try {
            vData.add( globalInput );
            vData.add( mLZSysCertifySet);
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
        mLZSysCertifySet.set((LZSysCertifySet)cInputData.getObjectByObjectName("LZSysCertifySet",0));

            logger.debug("SysCertSendOutUI : size is " + String.valueOf(mLZSysCertifySet.size()));

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

    cError.moduleName = "SysCertSendOutUI";
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
