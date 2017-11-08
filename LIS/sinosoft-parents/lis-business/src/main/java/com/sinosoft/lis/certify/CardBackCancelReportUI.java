/*
* <p>ClassName: CertifySearchUI </p>
* <p>Description: 单证查询打印的实现文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: lis
* @CreateDate：2003-06-16
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

import java.util.*;

public class CardBackCancelReportUI {
private static Logger logger = Logger.getLogger(CardBackCancelReportUI.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  private VData mResult = new VData();

  /** 往后面传输数据的容器 */
  private VData mInputData = new VData();

  /** 数据操作字符串 */
  private String m_strOperate;

  //业务处理相关变量
  /** 全局数据 */
  private GlobalInput mGlobalInput = new GlobalInput() ;
  private LZCardSet mLZCardSet = new LZCardSet();
  private Hashtable m_hashParams = null;

  public CardBackCancelReportUI ()
  {
  }

  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData, String cOperate)
  {
    m_strOperate = cOperate;
    mInputData = cInputData;

    //得到外部传入的数据,将数据备份到本类中
    if (!getInputData(cInputData))
      return false;

    //进行业务处理
    if (!dealData())
      return false;

    //准备往后台的数据
    if (!prepareOutputData())
      return false;

    CardBackCancelReportBL tCardBackCancelReportBL = new CardBackCancelReportBL();

    if( !tCardBackCancelReportBL.submitData(mInputData, m_strOperate) ) {
      if( tCardBackCancelReportBL.mErrors.needDealError() ) {
        this.mErrors.copyAllErrors(tCardBackCancelReportBL.mErrors);
        return false;
      } else {
        buildError("submitData", "数据提交失败，但是没有详细的信息！");
        return false;
      }
    }

    this.mResult = tCardBackCancelReportBL.getResult();

    return true;
  }

  public static void main(String[] args)
  {
	  
	//模拟数据
    LZCardTrackSet tLZCardTrackSet = new LZCardTrackSet();

    LZCardTrackSchema tLZCardTrackSchema = new LZCardTrackSchema();

    tLZCardTrackSchema.setCertifyCode( "1101" );
    tLZCardTrackSchema.setSendOutCom( "86110000" );
    tLZCardTrackSchema.setReceiveCom( "" );
    
    //将存有数据的Schema加入到Set中
    tLZCardTrackSet.add( tLZCardTrackSchema );
    
    String operFlag="0";
    String tCalculationDateB="2006-06-01";
    String tCalculationDateE="2006-09-01";
    
    TransferData tTransferData=new TransferData();
	tTransferData.setNameAndValue("CalculationDateB",tCalculationDateB);
	tTransferData.setNameAndValue("CalculationDateE",tCalculationDateE);
	tTransferData.setNameAndValue("operFlag",operFlag);
    
    GlobalInput globalInput = new GlobalInput();

    globalInput.Operator = "001";
    globalInput.ComCode = "86";

    // 准备传输数据 VData
    VData tVData = new VData();

    tVData.add( globalInput );
    tVData.add( tLZCardTrackSet );
    tVData.add(tTransferData);

    // 数据传输
    CardBackCancelReportUI tCardBackCancelReportUI = new CardBackCancelReportUI();

    if( !tCardBackCancelReportUI.submitData(tVData, "SEARCH||BACK") ) 
    {
      if( tCardBackCancelReportUI.mErrors.needDealError() ) 
      {
        logger.debug( tCardBackCancelReportUI.mErrors.getFirstError() );
      } 
      else 
      {
        logger.debug("CardBackCancelReportUI查询失败，但是没有提供详细的信息");
      }
    } 
    else 
    {
      tVData.clear();
      tVData = tCardBackCancelReportUI.getResult();
    }
  }

  /**
   * 准备往后层输出所需要的数据
   * 输出：如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean prepareOutputData()
  {
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
   *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
    return true;
  }

  public VData getResult()
  {
    return this.mResult;
  }

  /*
  * add by kevin, 2002-10-14
   */
  private void buildError(String szFunc, String szErrMsg)
  {
    CError cError = new CError( );

    cError.moduleName = "CardBackCancelReportUI";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }
}
