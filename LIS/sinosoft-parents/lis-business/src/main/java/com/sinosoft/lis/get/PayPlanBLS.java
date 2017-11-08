package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import java.sql.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author TJJ
 * @version 1.0
 */
public class PayPlanBLS  {
private static Logger logger = Logger.getLogger(PayPlanBLS.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors=new CErrors();
  /** 数据操作字符串 */
  private String mOperate;

  public  PayPlanBLS(){
  }
  public static void main(String[] args) {
    PayPlanBLS tPayPlanBLS = new PayPlanBLS();
  }

  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData,String cOperate)
  {
    boolean tReturn =true;
    //将操作数据拷贝到本类中
    this.mOperate =cOperate;

    logger.debug("Start LFGetPay BLS Submit...");
    //if(this.mOperate.equals("INSERT||PERSON"))
   // {
      if (!savePayPlan(cInputData))
        return false;
   // }
    return tReturn;
  }

  /**
   * 生存领取的数据的保存函数
   */
  private boolean savePayPlan(VData mInputData)
  {
    boolean tReturn =true;
    logger.debug("Start PayPlanBLS Save...");
    Connection conn = null;
    conn = DBConnPool.getConnection();

    if (conn==null)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "PayPlanBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    try
    {
      conn.setAutoCommit(false);
      logger.debug("Start 开始生成计划...");
/**
      LCGetDBSet tLCGetDBSet=new LCGetDBSet(conn);
      tLCGetDBSet.set((LCGetSet)mInputData.getObjectByObjectName("LCGetSet",0));

      if (!tLCGetDBSet.update())
      {
        // @@错误处理
        logger.debug("======ERROR=11=="+tLCGetDBSet.mErrors);
        this.mErrors.copyAllErrors(tLCGetDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "PayPlanBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
*/
      LJSGetDBSet tLJSGetDBSet=new LJSGetDBSet(conn);
      tLJSGetDBSet.set((LJSGetSet)mInputData.getObjectByObjectName("LJSGetSet",0));
      logger.debug("Get LJSGet");
      logger.debug("LJSGetDBSet size :" + tLJSGetDBSet.size());
      if (!tLJSGetDBSet.insert())
      {
        // @@错误处理
        logger.debug("======ERROR==22="+tLJSGetDBSet.mErrors);
        this.mErrors.copyAllErrors(tLJSGetDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "PayPlanBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }

      LJSGetDrawDBSet tLJSGetDrawDBSet=new LJSGetDrawDBSet(conn);
      tLJSGetDrawDBSet.set((LJSGetDrawSet)mInputData.getObjectByObjectName("LJSGetDrawSet",0));
      logger.debug("LJSGetDrawDBSet size :" + tLJSGetDrawDBSet.size());
      if (!tLJSGetDrawDBSet.insert())
      {
        // @@错误处理
        logger.debug("======ERROR==33="+tLJSGetDrawDBSet.mErrors);
        this.mErrors.copyAllErrors(tLJSGetDrawDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "PayPlanBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付明细数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      conn.commit() ;
      conn.close();
      logger.debug("8888");
    }
    catch (Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="PayPlanBLS";
      tError.functionName="submitData";
      tError.errorMessage=ex.toString();
      this.mErrors .addOneError(tError);
      tReturn=false;
      try{conn.rollback() ;
      conn.close();} catch(Exception e){}
    }
    return tReturn;
  }
}
