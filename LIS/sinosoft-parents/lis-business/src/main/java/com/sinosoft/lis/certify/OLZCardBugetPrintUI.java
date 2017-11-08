/*
 * <p>ClassName: OLZCardBugetPrintUI </p>
 * <p>Description: OLZCardBugetPrintUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2004-04-05 16:08:27
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.pubfun.*;


public class OLZCardBugetPrintUI {
private static Logger logger = Logger.getLogger(OLZCardBugetPrintUI.class);
  public OLZCardBugetPrintUI() {
  }

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 数据操作字符串 */
    private String m_strOperate;
    /** 全局数据 */
    private GlobalInput m_GlobalInput = new GlobalInput() ;
    /** 业务处理相关变量 */
    private String  m_sql ="";
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput() ;

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

      OLZCardBugetPrintBL tOLZCardBugetPrintBL = new OLZCardBugetPrintBL();

      if( !tOLZCardBugetPrintBL.submitData(mInputData, m_strOperate) ) {
        if( tOLZCardBugetPrintBL.mErrors.needDealError() ) {
          this.mErrors.copyAllErrors(tOLZCardBugetPrintBL.mErrors);
          return false;
        } else {
          buildError("submitData", "数据提交失败，但是没有详细的信息！");
          return false;
        }
      }

      this.mResult = tOLZCardBugetPrintBL.getResult();
      return true;
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

      try {
        this.m_GlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
        this.m_sql=(String)cInputData.get(1);
        if( m_sql == null||m_sql.equals("") ) {
          buildError("getInputData", "没有查询语句");
          return false;
        }
        if( m_GlobalInput== null || m_GlobalInput.equals("") ) {
          buildError("getInputData", "没有登陆信息");
          return false;
        }

      } catch(Exception ex) {
        ex.printStackTrace();
        buildError("getInputData", ex.getMessage());
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

      cError.moduleName = "CertPlanUI";
      cError.functionName = szFunc;
      cError.errorMessage = szErrMsg;
      this.mErrors.addOneError(cError);
    }
    public static void main(String[] args)
    {

//      String sql="SELECT * FROM LZCardPlan";
//      GlobalInput globalInput = new GlobalInput();
//
//      // 准备传输数据 VData
//      VData tVData = new VData();
//
//      tVData.add( globalInput );
//      tVData.add(sql);
//
//      // 数据传输
//      CertPlanUI tCertStatUI = new CertPlanUI();
//
//      if( !tCertStatUI.submitData(tVData, "PRINT") ) {
//        if( tCertStatUI.mErrors.needDealError() ) {
//          logger.debug( tCertStatUI.mErrors.getFirstError() );
//        } else {
//          logger.debug("CertPlanUI查询失败，但是没有提供详细的信息");
//        }
//      } else {
//        tVData.clear();
//        tVData = tCertStatUI.getResult();
//
//        // tLZCardSet = (LZCardSet)tVData.getObjectByObjectName("LZCardSet", 0);
//        XmlExport xe = (XmlExport)tVData.getObjectByObjectName("XmlExport", 0);
//        //xe.outputDocumentToFile("e:\\","testCertify");
//        //
//      }
  }
}
