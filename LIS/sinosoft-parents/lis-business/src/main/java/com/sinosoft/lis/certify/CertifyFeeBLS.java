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

public class CertifyFeeBLS
{
private static Logger logger = Logger.getLogger(CertifyFeeBLS.class);

  //传输数据类
  private VData mInputData ;
  private String mCertifyCode = "";//单证编码
  private String mOperateType = "";
  private String mOperator="";//操作员
  private String mDefineDate="";//单证定义日期
  private LZCardFeeSet mLZCardFeeSet = new LZCardFeeSet();
  //错误处理类，每个需要错误处理的类中都放置该类
  public  CErrors mErrors = new CErrors();
  public CertifyFeeBLS() {}

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
    mLZCardFeeSet = ((LZCardFeeSet)cInputData.getObjectByObjectName("LZCardFeeSet",0));
    logger.debug("BLS 中接收的数据的记录是"+mLZCardFeeSet.size());

    if(mOperateType.equals("INSERT"))
    {
      if (!this.saveData())
        return false;
    }
    if(mOperateType.equals("UPDATE"))
    {
      if(!updateData())
        return false;
    }
    if(mOperateType.equals("DELETE"))
    {
      if(!deleteData())
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
      tError.moduleName = "CertifyFeeBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    try
    {
      conn.setAutoCommit(false);
      LZCardFeeDBSet tLZCardFeeDBSet = new LZCardFeeDBSet(conn);
      tLZCardFeeDBSet.set(mLZCardFeeSet);
      if (tLZCardFeeDBSet.insert() == false)
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLZCardFeeDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "CertifyFeeBLS";
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
      tError.moduleName = "CertifyFeeBLS";
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

  private boolean deleteData()
  {
    Connection conn = DBConnPool.getConnection();
    if (conn==null)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "CertifyDescBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    try
    {
      conn.setAutoCommit(false);
      LZCardFeeDB tLZCardFeeDB = new LZCardFeeDB(conn);
      LZCardFeeSchema tLZCardFeeSchema = new LZCardFeeSchema();
      tLZCardFeeSchema.setSchema(mLZCardFeeSet.get(1));
      tLZCardFeeDB.setCertifyCode(tLZCardFeeSchema.getCertifyCode());
      if( !tLZCardFeeDB.delete())
      {
        // @@错误处理
    	logger.debug("删除数据出错");
        this.mErrors.copyAllErrors(tLZCardFeeDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "CertifyFeeBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "删除失败！！！";
        this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
        return false;
      }
      conn.commit() ;
      conn.close();
    } // end of try
    catch (Exception ex)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "CertifyDescBLS";
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
  
  
  //更新操作：先进行删除，在进行新增操作
  private boolean updateData()
  {
	//建立连接池，获得的Connection作为参数建立一个DB的实例
    Connection conn = DBConnPool.getConnection();
    if (conn==null)
    {
	      CError tError = new CError();
	      tError.moduleName = "CertifyFeeBLS";
	      tError.functionName = "saveData";
	      tError.errorMessage = "数据库连接失败!";
	      this.mErrors .addOneError(tError) ;
	      return false;
    }
    
    try
    {
      conn.setAutoCommit(false);
      LZCardFeeDB tLZCardFeeDB = new LZCardFeeDB(conn);
      LZCardFeeSchema tLZCardFeeSchema = new LZCardFeeSchema();
      //获得第一个Schema，也就是当前的Schema
      tLZCardFeeSchema.setSchema(mLZCardFeeSet.get(1));
      tLZCardFeeDB.setCertifyCode(tLZCardFeeSchema.getCertifyCode());
      tLZCardFeeDB.setManageCom(tLZCardFeeSchema.getManageCom());
      logger.debug("*********************************1");
      //根据查询到的单证编码先将所在记录删除
      if(!tLZCardFeeDB.delete())
      {
	        this.mErrors.copyAllErrors(tLZCardFeeDB.mErrors);
	        CError tError = new CError();
	        tError.moduleName = "CertifyFeeBLS";
	        tError.functionName = "DeleteData";
	        tError.errorMessage = "删除失败!";
	        this.mErrors.addOneError(tError);
	        conn.rollback() ;
	        conn.close();
	        return false;
      }
      logger.debug("*********************************2");
      //conn.close();
      //Connection conn = DBConnPool.getConnection();
    
      //再进行插入操作
      LZCardFeeDBSet tLZCardFeeDBSet = new LZCardFeeDBSet(conn);
      //晴空Set，以便将新的带有数据的Schema放入到Set中
      tLZCardFeeDBSet.add(mLZCardFeeSet);
      logger.debug("****************在BLS中所得到的单证代码是"+tLZCardFeeSchema.getCertifyCode());

      if (!tLZCardFeeDBSet.insert())
      {
        logger.debug("执行Update中的insert操作出现错误");
        this.mErrors.copyAllErrors(tLZCardFeeDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "ReportBLS";
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
    catch(Exception ex)
	    {
	      CError tError = new CError();
	      tError.moduleName = "CaseCureBLS";
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

