package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;


/**
 * <p>Title: Web业务系统案件－报案保存功能部分 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002 </p>
 * <p>Company: Sinosoft< /p>
 * @author 张征
 * @version 1.0
 */

public class CertifyFeeOperateBLS
{
private static Logger logger = Logger.getLogger(CertifyFeeOperateBLS.class);

  //传输数据类
  private VData mInputData ;
  private String mOperateType = "";
  private LZCardFeeOperSet mLZCardFeeOperSet = new LZCardFeeOperSet();
  //错误处理类，每个需要错误处理的类中都放置该类
  public  CErrors mErrors = new CErrors();
  public CertifyFeeOperateBLS() {}

  public static void main(String[] args)
  {
  }
  //传输数据的公共方法
  public boolean submitData(VData cInputData,String cOperate)
  {
    boolean tReturn = false;
    //首先将数据在本类中做一个备份
    mInputData=(VData)cInputData.clone() ;
    logger.debug("Start Report BLS Submit...");
    mOperateType=cOperate;
    logger.debug("BLS中接收的操作类型是"+mOperateType);
    mLZCardFeeOperSet = ((LZCardFeeOperSet)cInputData.getObjectByObjectName("LZCardFeeOperSet",0));
    logger.debug("BLS 中接收的数据的记录是"+mLZCardFeeOperSet.size());

    if(mOperateType.equals("INSERT"))
    {
      if (!this.saveData())
        return false;
    }

//    logger.debug("End Report BLS Submit...");
    mInputData=null;
    return true;
  }

  private boolean saveData()
  {
    Connection conn = DBConnPool.getConnection();
    if (conn==null)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "CertifyFeeOperateBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    try
    {
      conn.setAutoCommit(false);
      LZCardFeeOperDBSet tLZCardFeeOperDBSet = new LZCardFeeOperDBSet(conn);
      tLZCardFeeOperDBSet.set(mLZCardFeeOperSet);
      if (tLZCardFeeOperDBSet.insert() == false)
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLZCardFeeOperDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "CertifyFeeOperateBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "保存数据失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
        return false;
      }
      
      conn.commit() ;
      conn.close();
    } 
    
    catch (Exception ex)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "CertifyFeeOperateBLS";
      tError.functionName = "submitData";
      tError.errorMessage = ex.toString();
      this.mErrors .addOneError(tError);
      try
      {
        conn.rollback() ;
        conn.close();
      }
      catch(Exception e)
      {
      }
      return false;
    }
    return true;
  }
  

}

