package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

/**
 * <p>Title: lis</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @author lh
 * @version 1.0
 */

import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import java.sql.*;
import com.sinosoft.lis.pubfun.*;

public class SysOperatorNoticeBLS {
private static Logger logger = Logger.getLogger(SysOperatorNoticeBLS.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors=new CErrors();
//传输数据类
  private VData mInputData ;
  /** 数据操作字符串 */
  private String mOperate;

  public SysOperatorNoticeBLS() {
  }
  public static void main(String[] args) {
    SysOperatorNoticeBLS sysOperatorNoticeBLS1 = new SysOperatorNoticeBLS();
  }

  /**
传输数据的公共方法
*/
  public boolean submitData(VData cInputData,String cOperate)
  {
    //将操作数据拷贝到本类中
    this.mOperate =cOperate;
    mInputData=(VData)cInputData.clone();
    logger.debug("Start SysOperatorNoticeBLS Submit...");
    if (this.mOperate.equals("TakeBack"))
    {
      if (!saveData())
        logger.debug("update failed") ;
      logger.debug("End SysOperatorNoticeBLS Submit...");
      return false;
    }
    logger.debug(" sucessful");
    return true;
  }

  /**
   * 保存函数
   */
  private boolean saveData()
  {
    LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
    tLCUWMasterSet = (LCUWMasterSet)mInputData.getObjectByObjectName("LCUWMasterSet",0);
    LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
    tLCIssuePolSet = (LCIssuePolSet)mInputData.getObjectByObjectName("LCIssuePolSet",0);
    logger.debug("Start Save...");
    Connection conn;
    conn=DBConnPool.getConnection();
    if (conn==null)
    {
      CError tError = new CError();
      tError.moduleName = "SysOperatorNoticeBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors.addOneError(tError) ;
      return false;
    }
    try{
      conn.setAutoCommit(false);
      logger.debug("Start 保存...");
      LCUWMasterDBSet tLCUWMasterDBSet = new LCUWMasterDBSet(conn);
      tLCUWMasterDBSet.set(tLCUWMasterSet);

      if (!tLCUWMasterDBSet.update())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLCUWMasterDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "SysOperatorNoticeBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors.addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }

      LCIssuePolDBSet tLCIssuePolDBSet = new LCIssuePolDBSet(conn);
      tLCIssuePolDBSet.set(tLCIssuePolSet);

      if (!tLCIssuePolDBSet.update())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLCIssuePolDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "SysOperatorNoticeBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors.addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      conn.commit() ;
      conn.close();
    }
    catch (Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="SysOperatorNoticeBLS";
      tError.functionName="saveData";
      tError.errorMessage=ex.toString();
      this.mErrors.addOneError(tError);
      try{
        conn.rollback() ;
        conn.close();
      }
      catch(Exception e){}
      return false;
    }
    return true;
  }
}
