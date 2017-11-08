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

public class CertifySearchUI {
private static Logger logger = Logger.getLogger(CertifySearchUI.class);

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

  public CertifySearchUI ()
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

    CertifySearchBL tCertifySearchBL = new CertifySearchBL();

    if( !tCertifySearchBL.submitData(mInputData, m_strOperate) ) {
      if( tCertifySearchBL.mErrors.needDealError() ) {
        this.mErrors.copyAllErrors(tCertifySearchBL.mErrors);
        return false;
      } else {
        buildError("submitData", "数据提交失败，但是没有详细的信息！");
        return false;
      }
    }

    this.mResult = tCertifySearchBL.getResult();

    return true;
  }

  public static void main(String[] args)
  {
    LZCardSet tLZCardSet = new LZCardSet();

    LZCardSchema tLZCardSchema = new LZCardSchema();

    tLZCardSchema.setCertifyCode( "01" );
    tLZCardSchema.setState( CertifySearchBL.PRT_STATE );

    tLZCardSchema.setSendOutCom( "A86" );
    tLZCardSchema.setReceiveCom( "A8611" );

//    tLZCardSchema.setOperator( "Operator" );
//    tLZCardSchema.setHandler( "Handler" );
//
    tLZCardSchema.setMakeDate("2003-05-01");

    tLZCardSet.add( tLZCardSchema );
    tLZCardSchema = new LZCardSchema();
    tLZCardSchema.setInvaliDate( "InvaliDate" );
    tLZCardSchema.setHandleDate( "HandleDate" );

    tLZCardSchema.setMakeDate("2003-05-20");
    // 加入第二个Schema
    tLZCardSet.add( tLZCardSchema );

    GlobalInput globalInput = new GlobalInput();

    globalInput.Operator = "001";
    globalInput.ComCode = "86";

    // 准备传输数据 VData
    VData tVData = new VData();

    tVData.add( globalInput );
    tVData.add( tLZCardSet );
    tVData.add( new Hashtable() );

    // 数据传输
    CertifySearchUI tCertifySearchUI = new CertifySearchUI();

    if( !tCertifySearchUI.submitData(tVData, "SEARCH||MAIN") ) {
      if( tCertifySearchUI.mErrors.needDealError() ) {
        logger.debug( tCertifySearchUI.mErrors.getFirstError() );
      } else {
        logger.debug("CertifySearchUI查询失败，但是没有提供详细的信息");
      }
    } else {
      tVData.clear();
      tVData = tCertifySearchUI.getResult();

      // tLZCardSet = (LZCardSet)tVData.getObjectByObjectName("LZCardSet", 0);
      //
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

    cError.moduleName = "CertifySearchUI";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }
}
