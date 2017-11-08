package com.sinosoft.lis.certify;
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

public class CertifyBatchBackUI 
{
private static Logger logger = Logger.getLogger(CertifyBatchBackUI.class);


  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public CErrors mErrors=new CErrors();
  private VData mResult = new VData();

  //业务处理相关变量
  /** 全局数据 */
  private GlobalInput mGlobalInput =new GlobalInput() ;
  private TransferData mTransferData = new TransferData();
  private String receivecom="";//收单机构
  private String sendoutcom="";//发单机构
  private String CertifyCode="";//单证编码

  public CertifyBatchBackUI() 
  {
	  
  }

  /**
    传输数据的公共方法
  */
  public boolean submitData(VData cInputData, String cOperate) 
  {
    logger.debug(cOperate);
    if( !cOperate.equals("INSERT")) 
    {
      buildError("submitData", "不支持的操作字符串");
      return false;
    }

    // 得到外部传入的数据，将数据备份到本类中
    if( !getInputData(cInputData) ) 
    {
      return false;
    }

    // 进行业务处理
    if( !dealData() ) 
    {
    	return false;
    }

    // 准备传往后台的数据
    //VData vData = new VData();

    //if( !prepareOutputData(vData) ) 
    //{
      //return false;
    //}

    CertifyBatchBackBL tCertifyBatchBackBL = new CertifyBatchBackBL();
    logger.debug("Start CertifyBatchBackBL Submit ...");

    if( !tCertifyBatchBackBL.submitData(cInputData, cOperate) ) 
    {
      if( tCertifyBatchBackBL.mErrors.needDealError() ) 
      {
        mErrors.copyAllErrors(tCertifyBatchBackBL.mErrors);
        return false;
      }
      else 
      {
        buildError("submitData", "CertifyBatchBackUI发生错误，但是没有提供详细的出错信息");
        return false;
      }
    } 
    else 
    {
      mResult = tCertifyBatchBackBL.getResult();
      return true;
    }
  }

  /**
   * 准备往后层输出所需要的数据
   * 输出：如果准备数据时发生错误则返回false,否则返回true
   */
  /*
  private boolean prepareOutputData(VData vData) 
  {
    try 
    {
      mTransferData.setNameAndValue("FiscalYear",mFiscalYear);

      vData.clear();
      vData.add(mGlobalInput);
      vData.add(mTransferData);
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
      buildError("prepareOutputData", "发生异常");
      return false;
    }
    return true;
  }
  */

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
      mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
      mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);

      if (mGlobalInput == null)
      {
          buildError("getInputData", "没有得到足够的信息！");

          return false;
      }
      if(mTransferData != null)
      {
        logger.debug("mTransferData != null");
        sendoutcom=(String)mTransferData.getValueByName("SendOutCom");
        logger.debug("页面输入的发送机构是"+sendoutcom);
        receivecom=(String)mTransferData.getValueByName("ReceiveCom");
        logger.debug("页面输入的接收机构是"+receivecom);
        CertifyCode=(String)mTransferData.getValueByName("CertifyCode");
        logger.debug("页面输入的单证编码是"+CertifyCode);
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

    cError.moduleName = "CertifyBatchBackUI";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }

  public CErrors getErrors() 
  {
    return mErrors;
  }

  public static void main(String[] args) 
  {
	/*
    LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

    tLOPRTManagerSchema.setPrtSeq("86110020040810001117");
    //tLOPRTManagerSchema.setOtherNo("86110020030210000549");

    GlobalInput tG = new GlobalInput();
    tG.Operator = "001";
    tG.ComCode  = "86";

    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("FiscalYear","");

    VData tVData = new VData();

    tVData.addElement(tG);
    tVData.addElement(tLOPRTManagerSchema);
    tVData.addElement(tTransferData);

    CertifyBatchBackUI xCertifyBatchBackUI = new CertifyBatchBackUI();
    if( !xCertifyBatchBackUI.submitData(tVData,"PRINT") ) {
      if( xCertifyBatchBackUI.mErrors.needDealError() ) {
        logger.debug(xCertifyBatchBackUI.mErrors.getFirstError());
      } else {
        logger.debug("BonusNoticeUI发生错误，但是没有提供详细的出错信息");
      }
    }


//    else if( !BonusNoticeUI.submitData(tVData,"PRINT") ) {
//      if( BonusNoticeUI.mErrors.needDealError() ) {
//        logger.debug(BonusNoticeUI.mErrors.getFirstError());
//      } else {
//        logger.debug("BonusNoticeUI发生错误，但是没有提供详细的出错信息");
//      }
//    }

    else {
      VData vData = xCertifyBatchBackUI.getResult();
      XmlExport xe = (XmlExport)vData.get(0);

      try {
        InputStream ins = xe.getInputStream();
        FileOutputStream fos = new FileOutputStream("LCPolData.xml");
        int n = 0;

        while( ( n = ins.read() ) != -1 ) {
          fos.write(n);
        }

        fos.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    */
  }
}
