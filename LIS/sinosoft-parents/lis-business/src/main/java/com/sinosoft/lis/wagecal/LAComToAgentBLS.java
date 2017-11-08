/*
 * <p>ClassName: LAComToAgentBLS </p>
 * <p>Description: LAContBLS类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 销售管理
 * @CreateDate：2003-02-27
 */
package com.sinosoft.lis.wagecal;
import org.apache.log4j.Logger;

import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import java.sql.*;
import com.sinosoft.lis.pubfun.*;

 public class LAComToAgentBLS {
private static Logger logger = Logger.getLogger(LAComToAgentBLS.class);
/** 错误处理类，每个需要错误处理的类中都放置该类 */
public  CErrors mErrors=new CErrors();
 /** 数据操作字符串 */
private String mOperate;
public LAComToAgentBLS() {
	}
public static void main(String[] args) {
}
 /**
 传输数据的公共方法
*/
public boolean submitData(VData cInputData,String cOperate) throws Exception
{
     boolean tReturn =false;
     //将操作数据拷贝到本类中
     this.mOperate =cOperate;

    logger.debug("Start LAComToAgentBLS Submit...");
    logger.debug("操作符："+this.mOperate);
    if(this.mOperate.equals("INSERT||MAIN"))
    {tReturn=saveLACont(cInputData);}
    if (this.mOperate.equals("DELETE||MAIN"))
    {tReturn=deleteLACont(cInputData);}
    if (this.mOperate.equals("UPDATE||MAIN"))
    {tReturn=updateLACont(cInputData);}
    if (tReturn)
        logger.debug(" sucessful");
    else
        logger.debug("Save failed") ;
        logger.debug("End LAComToAgentBLS Submit...");
  return tReturn;
}
 /**
* 保存函数
*/
private boolean saveLACont(VData mInputData)
{
  boolean tReturn =true;
  logger.debug("Start Save Now...");
  Connection conn;
  conn=null;
  conn=DBConnPool.getConnection();
  if (conn==null)
  {
    // @@错误处理
    CError tError = new CError();
    tError.moduleName = "LAComToAgentBLS";
    tError.functionName = "saveData";
    tError.errorMessage = "数据库连接失败!";
    this.mErrors .addOneError(tError) ;
    return false;
  }
  try{
    conn.setAutoCommit(false);
    logger.debug("Start 保存...");
    LAComToAgentSet mLAComToAgentSet = (LAComToAgentSet)mInputData.getObjectByObjectName("LAComToAgentSet",0);
//    mLAComToAgentDBSet.set((LAComToAgentSet)mInputData.getObjectByObjectName("LAComToAgentSet",0));
    LAComToAgentDB aLAComToAgentDB = new LAComToAgentDB(conn);
//    LAComToAgentDB bLAComToAgentDB = new LAComToAgentDB(conn);
    aLAComToAgentDB.setSchema(mLAComToAgentSet.get(1));
//    bLAComToAgentDB.setSchema(mLAComToAgentSet.get(2));
//           tLAComToAgentDB.set((LAContSet)mInputData.getObjectByObjectName("LAContSet",0));
//    if (!bLAComToAgentDB.delete())
//    {
//      // @@错误处理
//      this.mErrors.copyAllErrors(bLAComToAgentDB.mErrors);
//      CError tError = new CError();
//      tError.moduleName = "LAComToAgentBLS";
//      tError.functionName = "saveData";
//      tError.errorMessage = "数据保存失败!";
//      this.mErrors .addOneError(tError) ;
//      conn.rollback();
//      conn.close();
//      return false;
//    }
    if (!aLAComToAgentDB.insert())
    {
      // @@错误处理
      this.mErrors.copyAllErrors(aLAComToAgentDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "LAComToAgentBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据保存失败!";
      this.mErrors .addOneError(tError) ;
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
    tError.moduleName="LAComToAgentBLS";
    tError.functionName="submitData";
    tError.errorMessage=ex.toString();
    this.mErrors .addOneError(tError);
    tReturn=false;
    try{conn.rollback() ;conn.close();} catch(Exception e){}
  }
  return tReturn;
}
    /**
    * 保存函数
    */
    private boolean deleteLACont(VData mInputData)
    {
        boolean tReturn =true;
        logger.debug("Start Save...");
        Connection conn;
        conn=null;
        conn=DBConnPool.getConnection();
        if (conn==null)
        {
		// @@错误处理
		CError tError = new CError();
           tError.moduleName = "LAComToAgentBLS";
           tError.functionName = "saveData";
           tError.errorMessage = "数据库连接失败!";
           this.mErrors .addOneError(tError) ;
           return false;
        }
        try{
           conn.setAutoCommit(false);
           logger.debug("Start 保存...");
           LAComToAgentBDBSet tLAComToAgentBDBSet=new LAComToAgentBDBSet(conn);
           tLAComToAgentBDBSet.set((LAComToAgentBSet)mInputData.getObjectByObjectName("LAComToAgentBSet",2));
           if (!tLAComToAgentBDBSet.insert())
           {
             // @@错误处理
                 this.mErrors.copyAllErrors(tLAComToAgentBDBSet.mErrors);
                 CError tError = new CError();
                 tError.moduleName = "LAComToAgentBLS";
                 tError.functionName = "saveData";
            tError.errorMessage = "数据保存失败!";
            this.mErrors .addOneError(tError) ;
            conn.rollback();
            conn.close();
            return false;
           }
//           LAComToAgentDBSet tLAComToAgentDBSet=new LAComToAgentDBSet(conn);
//           tLAComToAgentDBSet.set((LAComToAgentSet)mInputData.getObjectByObjectName("LAComToAgentSet",1));
//           if (!tLAComToAgentDBSet.delete())
//           {
//		// @@错误处理
//		    this.mErrors.copyAllErrors(tLAComToAgentDBSet.mErrors);
// 		    CError tError = new CError();
//		    tError.moduleName = "LAComToAgentBLS";
//		    tError.functionName = "saveData";
//		    tError.errorMessage = "数据删除失败!";
//		    this.mErrors .addOneError(tError) ;
//               conn.rollback();
//               conn.close();
//               return false;
//           }
           LAComToAgentSet mLAComToAgentSet = (LAComToAgentSet)mInputData.getObjectByObjectName("LAComToAgentSet",0);
           LAComToAgentDB bLAComToAgentDB = new LAComToAgentDB(conn);
           bLAComToAgentDB.setSchema(mLAComToAgentSet.get(2));
           if (!bLAComToAgentDB.delete())
           {
             // @@错误处理
             this.mErrors.copyAllErrors(bLAComToAgentDB.mErrors);
             CError tError = new CError();
             tError.moduleName = "LAComToAgentBLS";
             tError.functionName = "saveData";
             tError.errorMessage = "数据保存失败!";
             this.mErrors .addOneError(tError) ;
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
          tError.moduleName="LAComToAgentBLS";
          tError.functionName="submitData";
          tError.errorMessage=ex.toString();
          this.mErrors .addOneError(tError);
          tReturn=false;
          try{conn.rollback() ;conn.close();} catch(Exception e){}
         }
         return tReturn;
}
/**
  * 保存函数
*/
private boolean updateLACont(VData mInputData)
{
  boolean tReturn =true;
  logger.debug("Start Save...");
  Connection conn;
  conn=null;
  conn=DBConnPool.getConnection();
  if (conn==null)
  {
// @@错误处理
    CError tError = new CError();
    tError.moduleName = "LAComToAgentBLS";
    tError.functionName = "saveData";
    tError.errorMessage = "数据库连接失败!";
    this.mErrors .addOneError(tError) ;
    return false;
  }
  try{
    conn.setAutoCommit(false);
    logger.debug("Start 保存...");
    LAComToAgentBDBSet tLAComToAgentBDBSet=new LAComToAgentBDBSet(conn);
    tLAComToAgentBDBSet.set((LAComToAgentBSet)mInputData.getObjectByObjectName("LAComToAgentBSet",2));
    if (!tLAComToAgentBDBSet.insert())
    {
      // @@错误处理
      this.mErrors.copyAllErrors(tLAComToAgentBDBSet.mErrors);
      CError tError = new CError();
      tError.moduleName = "LAComToAgentBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据保存失败!";
      this.mErrors .addOneError(tError) ;
      conn.rollback();
      conn.close();
      return false;
    }
    LAComToAgentSet mLAComToAgentSet = (LAComToAgentSet)mInputData.getObjectByObjectName("LAComToAgentSet",0);
    LAComToAgentDB aLAComToAgentDB = new LAComToAgentDB(conn);
    LAComToAgentDB bLAComToAgentDB = new LAComToAgentDB(conn);
    aLAComToAgentDB.setSchema(mLAComToAgentSet.get(1));
    bLAComToAgentDB.setSchema(mLAComToAgentSet.get(2));
    if (!bLAComToAgentDB.delete())
    {
      // @@错误处理
      this.mErrors.copyAllErrors(bLAComToAgentDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "LAComToAgentBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据保存失败!";
      this.mErrors .addOneError(tError) ;
      conn.rollback();
      conn.close();
      return false;
    }
    if (!aLAComToAgentDB.insert())
    {
      // @@错误处理
      this.mErrors.copyAllErrors(aLAComToAgentDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "LAComToAgentBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据保存失败!";
      this.mErrors .addOneError(tError) ;
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
    tError.moduleName="LAComToAgentBLS";
    tError.functionName="submitData";
    tError.errorMessage=ex.toString();
    this.mErrors .addOneError(tError);
    tReturn=false;
    try{conn.rollback() ;conn.close();} catch(Exception e){}
  }
  return tReturn;
  }
}
