/*
 * <p>ClassName: OLZCardBugetBLS </p>
 * <p>Description: OLZCardBugetBLS类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2004-04-05 16:08:27
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import java.sql.*;
import com.sinosoft.lis.pubfun.*;
 public class OLZCardBugetBLS {
private static Logger logger = Logger.getLogger(OLZCardBugetBLS.class);
/** 错误处理类，每个需要错误处理的类中都放置该类 */
public  CErrors mErrors=new CErrors();
//传输数据类
  private VData mInputData ;
 /** 数据操作字符串 */
private String mOperate;
public OLZCardBugetBLS() {
	}
public static void main(String[] args) {
}
 /**
 传输数据的公共方法
*/
public boolean submitData(VData cInputData,String cOperate)
{
    //将操作数据拷贝到本类中
    this.mOperate =cOperate;
	  mInputData=(VData)cInputData.clone();
    if(this.mOperate.equals("INSERT||MAIN"))
    {if (!saveLZCardBuget())
        return false;
    }
    if (this.mOperate.equals("DELETE||MAIN"))
    {if (!deleteLZCardBuget())
        return false;
    }
    if (this.mOperate.equals("UPDATE||MAIN"))
    {if (!updateLZCardBuget())
        return false;
    }
  return true;
}
 /**
* 保存函数
*/
private boolean saveLZCardBuget()
{
  LZCardBugetSchema tLZCardBugetSchema = new LZCardBugetSchema();
  tLZCardBugetSchema = (LZCardBugetSchema)mInputData.getObjectByObjectName("LZCardBugetSchema",0);
  Connection conn;
  conn=null;
  conn=DBConnPool.getConnection();
  if (conn==null)
  {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "OLZCardBugetBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
   }
	try{
 	  conn.setAutoCommit(false);
    LZCardBugetDB tLZCardBugetDB=new LZCardBugetDB(conn);
    tLZCardBugetSchema.setMakeDate(PubFun.getCurrentDate());
    tLZCardBugetSchema.setMakeTime(PubFun.getCurrentTime());
    tLZCardBugetSchema.setModifyDate(PubFun.getCurrentDate());
    tLZCardBugetSchema.setModifyTime(PubFun.getCurrentTime());
    tLZCardBugetDB.setSchema(tLZCardBugetSchema);
    if (!tLZCardBugetDB.insert())
    {
      // @@错误处理
      this.mErrors.copyAllErrors(tLZCardBugetDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "OLZCardBugetBLS";
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
    tError.moduleName="OLZCardBugetBLS";
    tError.functionName="submitData";
    tError.errorMessage=ex.toString();
    this.mErrors .addOneError(tError);
      try{
      conn.rollback() ;
      conn.close();
      }
      catch(Exception e){}
    return false;
	}
  return true;
}
    /**
    * 保存函数
    */
    private boolean deleteLZCardBuget()
    {
        LZCardBugetSchema tLZCardBugetSchema = new LZCardBugetSchema();
        tLZCardBugetSchema = (LZCardBugetSchema)mInputData.getObjectByObjectName("LZCardBugetSchema",0);
        logger.debug("Start Save...");
        Connection conn;
        conn=null;
        conn=DBConnPool.getConnection();
        if (conn==null)
        {
		// @@错误处理
		CError tError = new CError();
           tError.moduleName = "OLZCardBugetBLS";
           tError.functionName = "saveData";
           tError.errorMessage = "数据库连接失败!";
           this.mErrors .addOneError(tError) ;
           return false;
        }
        try{
           conn.setAutoCommit(false);
           logger.debug("Start 保存...");
           LZCardBugetDB tLZCardBugetDB=new LZCardBugetDB(conn);
           tLZCardBugetDB.setSchema(tLZCardBugetSchema);
           if (!tLZCardBugetDB.delete())
           {
		// @@错误处理
		    this.mErrors.copyAllErrors(tLZCardBugetDB.mErrors);
 		    CError tError = new CError();
		    tError.moduleName = "OLZCardBugetBLS";
		    tError.functionName = "saveData";
		    tError.errorMessage = "数据删除失败!";
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
          tError.moduleName="OLZCardBugetBLS";
          tError.functionName="submitData";
          tError.errorMessage=ex.toString();
          this.mErrors .addOneError(tError);
          try{conn.rollback() ;
          conn.close();} catch(Exception e){}
         return false;
         }
         return true;
}
/**
  * 保存函数
*/
private boolean updateLZCardBuget()
{
     LZCardBugetSchema tLZCardBugetSchema = new LZCardBugetSchema();
     tLZCardBugetSchema = (LZCardBugetSchema)mInputData.getObjectByObjectName("LZCardBugetSchema",0);
     logger.debug("Start Save...");
     Connection conn;
     conn=null;
     conn=DBConnPool.getConnection();
     if (conn==null)
     {
	     CError tError = new CError();
        tError.moduleName = "OLZCardBugetBLS";
        tError.functionName = "updateData";
        tError.errorMessage = "数据库连接失败!";
        this.mErrors .addOneError(tError) ;
        return false;
     }
     try{
           conn.setAutoCommit(false);
           logger.debug("Start 保存...");
           LZCardBugetDB tLZCardBugetDB=new LZCardBugetDB(conn);
           tLZCardBugetSchema.setModifyDate(PubFun.getCurrentDate());
           tLZCardBugetSchema.setModifyTime(PubFun.getCurrentTime());
           tLZCardBugetDB.setSchema(tLZCardBugetSchema);
           if (!tLZCardBugetDB.update())
           {
	          // @@错误处理
	         this.mErrors.copyAllErrors(tLZCardBugetDB.mErrors);
	         CError tError = new CError();
	         tError.moduleName = "OLZCardBugetBLS";
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
               tError.moduleName="OLZCardBugetBLS";
               tError.functionName="submitData";
               tError.errorMessage=ex.toString();
               this.mErrors .addOneError(tError);
               try{conn.rollback() ;
               conn.close();} catch(Exception e){}
               return false;
     }
               return true;
     }
}
