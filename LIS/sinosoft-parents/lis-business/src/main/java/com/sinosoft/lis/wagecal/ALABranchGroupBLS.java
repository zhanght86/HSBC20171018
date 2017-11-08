/*
* <p>ClassName: ALABranchGroupBLS </p>
* <p>Description: ALABranchGroupBLS类文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: 销售管理
* @CreateDate：2003-01-09
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

public class ALABranchGroupBLS {
private static Logger logger = Logger.getLogger(ALABranchGroupBLS.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors=new CErrors();
  /** 数据操作字符串 */
  private String mOperate;
  public ALABranchGroupBLS() {
  }
  public static void main(String[] args) {
  }
  /**
 传输数据的公共方法
 */
  public boolean submitData(VData cInputData,String cOperate)
  {
    boolean tReturn =false;
    //将操作数据拷贝到本类中
    this.mOperate =cOperate;

    logger.debug("Start ALABranchGroupBLS Submit...");
    if(this.mOperate.equals("INSERT||MAIN"))
        {tReturn=saveLABranchGroup(cInputData);}
    if (this.mOperate.equals("DELETE||MAIN"))
        {tReturn=deleteLABranchGroup(cInputData);}
    if (this.mOperate.equals("UPDATE||MAIN"))
        {tReturn=updateLABranchGroup(cInputData);}
    if (tReturn)
      logger.debug(" sucessful");
    else
      logger.debug("Save failed") ;
    logger.debug("End ALABranchGroupBLS Submit...");
    return tReturn;
  }
  /**
   * 保存函数
   */
  private boolean saveLABranchGroup(VData mInputData)
  {
    boolean tReturn =true;
    logger.debug("Start Save...");
    Connection conn;
    conn=null;
    //conn=PubFun1.getDefaultConnection();
    conn=DBConnPool.getConnection();
    if (conn==null)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "ALABranchGroupBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors.addOneError(tError) ;
      return false;
    }
    try{
      conn.setAutoCommit(false);
      logger.debug("Start 保存...");
      LABranchGroupDB tLABranchGroupDB=new LABranchGroupDB(conn);
      tLABranchGroupDB.setSchema((LABranchGroupSchema)mInputData.getObjectByObjectName("LABranchGroupSchema",0));
      if (!tLABranchGroupDB.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
           /*
           logger.debug("Start 保存 LATree,LATreeB,LAAgent...");
           LATreeDB tLATreeDB=new LATreeDB(conn);
           tLATreeDB.setSchema((LATreeSchema)mInputData.getObjectByObjectName("LATreeSchema",0));
           LATreeBDB tLATreeBDB=new LATreeBDB(conn);
           tLATreeBDB.setSchema((LATreeBSchema)mInputData.getObjectByObjectName("LATreeBSchema",0));
           String tTree= tLATreeDB.getAgentCode();
           String tTreeB= tLATreeBDB.getAgentCode();
           if (tTree==null) tTree="";
           if (tTreeB==null) tTreeB="";
           logger.debug("tree");
           if ((!tTree.equals(""))&&(tTreeB.equals("")))
           {
             if (!tLATreeDB.insert())
             {
      // @@错误处理
                this.mErrors.copyAllErrors(tLATreeDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "ALABranchGroupBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors .addOneError(tError) ;
                conn.rollback();
                conn.close();
                return false;
             }
           }
           logger.debug("treeb");
           if ((!tTree.equals(""))&&(!tTreeB.equals("")))
           {
             if (!tLATreeDB.update())
             {
      // @@错误处理
                this.mErrors.copyAllErrors(tLATreeDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "ALABranchGroupBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors .addOneError(tError) ;
                conn.rollback();
                conn.close();
                return false;
             }
             if (!tLATreeBDB.insert())
             {
      // @@错误处理
                this.mErrors.copyAllErrors(tLATreeBDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "ALABranchGroupBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors .addOneError(tError) ;
                conn.rollback();
                conn.close();
                return false;
             }
           }
           logger.debug("agent");
           if (!tTree.equals(""))
           {
             LAAgentDB tLAAgentDB = new LAAgentDB(conn);
             tLAAgentDB.setSchema((LAAgentSchema)mInputData.getObjectByObjectName("LAAgentSchema",0));
             if (!tLAAgentDB.update())
             {
      // @@错误处理
                this.mErrors.copyAllErrors(tLAAgentDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "ALABranchGroupBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors .addOneError(tError) ;
                conn.rollback();
                conn.close();
                return false;
             }
           }*/
      conn.commit() ;
      conn.close();
    }
    catch (Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="ALABranchGroupBLS";
      tError.functionName="submitData";
      tError.errorMessage=ex.toString();
      this.mErrors .addOneError(tError);
      tReturn=false;
      try{conn.rollback() ;
      conn.close();} catch(Exception e){}
    }
    return tReturn;
  }
  /**
   * 保存函数
   */
  private boolean deleteLABranchGroup(VData mInputData)
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
      tError.moduleName = "ALABranchGroupBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    try{
      conn.setAutoCommit(false);
      logger.debug("Start 保存...");
      //先备份
      LABranchGroupBDB tLABranchGroupBDB=new LABranchGroupBDB(conn);
      tLABranchGroupBDB.setSchema((LABranchGroupBSchema)mInputData.getObjectByObjectName("LABranchGroupBSchema",0));
      if (!tLABranchGroupBDB.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLABranchGroupBDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBLS";
        tError.functionName = "updateData";
        tError.errorMessage = "备份行政信息失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
//再删除 操作
      LABranchGroupDB tLABranchGroupDB=new LABranchGroupDB(conn);
      tLABranchGroupDB.setSchema((LABranchGroupSchema)mInputData.getObjectByObjectName("LABranchGroupSchema",0));
      if (!tLABranchGroupDB.delete())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBLS";
        tError.functionName = "deleteData";
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
      tError.moduleName="ALABranchGroupBLS";
      tError.functionName="submitData";
      tError.errorMessage=ex.toString();
      this.mErrors .addOneError(tError);
      tReturn=false;
      try{conn.rollback() ;
      conn.close();} catch(Exception e){}
    }
    return tReturn;
  }
  /**
   * 保存函数
   */
  private boolean updateLABranchGroup(VData mInputData)
  {
    boolean tReturn =true;
    logger.debug("Start Save...");
    Connection conn;
    conn=null;
    conn=DBConnPool.getConnection();
    if (conn==null)
    {
      CError tError = new CError();
      tError.moduleName = "ALABranchGroupBLS";
      tError.functionName = "updateData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    try{
      conn.setAutoCommit(false);
      logger.debug("Start 保存...");
      //备份操作
      LABranchGroupBDB tLABranchGroupBDB=new LABranchGroupBDB(conn);
      tLABranchGroupBDB.setSchema((LABranchGroupBSchema)mInputData.getObjectByObjectName("LABranchGroupBSchema",0));
      if (!tLABranchGroupBDB.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLABranchGroupBDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBLS";
        tError.functionName = "updateData";
        tError.errorMessage = "备份行政信息失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }

      LABranchGroupDB tLABranchGroupDB=new LABranchGroupDB(conn);
      tLABranchGroupDB.setSchema((LABranchGroupSchema)mInputData.getObjectByObjectName("LABranchGroupSchema",0));
      if (!tLABranchGroupDB.update())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBLS";
        tError.functionName = "updateData";
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
      tError.moduleName="ALABranchGroupBLS";
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
