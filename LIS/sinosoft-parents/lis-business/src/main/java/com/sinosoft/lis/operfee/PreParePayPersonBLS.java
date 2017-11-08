 package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.JdbcUrl;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import java.sql.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author HZM
 * @version 1.0
 */

public class PreParePayPersonBLS  {
private static Logger logger = Logger.getLogger(PreParePayPersonBLS.class);
  //错误处理类，每个需要错误处理的类中都放置该类
  public  CErrors mErrors=new CErrors();
  /** 数据操作字符串 */
  private String mOperate;  

  public PreParePayPersonBLS() {
  }
  public static void main(String[] args) {
    PreParePayPersonBLS mPreParePayPersonBLS1 = new PreParePayPersonBLS();
  }

  //传输数据的公共方法
  public boolean submitData(VData cInputData,String cOperate)
  {
    boolean tReturn =false;
    //将操作数据拷贝到本类中
    this.mOperate =cOperate;
    logger.debug("Start PreParePayPerson BLS Submit...");
    //信息保存
    if(this.mOperate.equals("INSERT"))
    {tReturn=insertData(cInputData);}

    if (tReturn)
      logger.debug("INSERT sucessful");
    else
      logger.debug("INSERT failed") ;
      
      logger.debug("End PreParePayPerson BLS Submit...");

    return tReturn;
  }
  
//核销事务操作  
  private boolean insertData(VData mInputData)
  {
    boolean tReturn =true;
    logger.debug("Start Insert...");
    Connection conn = DBConnPool.getConnection();
    if (conn==null)
    {
    		// @@错误处理
    		CError tError = new CError();
		tError.moduleName = "PreParePayPersonBLS";
                tError.functionName = "InsertData";
	        tError.errorMessage = "数据库连接失败!";
		this.mErrors .addOneError(tError) ;
 		return false;
    }
    try{
      conn.setAutoCommit(false);
          
// 个人交费表添加
      logger.debug("Start 个人交费表添加...");
      LJSPayPersonDBSet tLJSPayPersonInsertDBSet=new LJSPayPersonDBSet(conn);
      tLJSPayPersonInsertDBSet.set((LJSPayPersonSet)mInputData.getObject(0));
      if (!tLJSPayPersonInsertDBSet.insert())
      {
    		// @@错误处理
		this.mErrors.copyAllErrors(tLJSPayPersonInsertDBSet.mErrors);
    		CError tError = new CError();
	        tError.moduleName = "PreParePayPersonBLS";
	        tError.functionName = "deleteData";
	        tError.errorMessage = "应收个人交费表数据添加失败!";
	        this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
 	        return false;
      }        
logger.debug("个人交费表添加");      

//更新应收个人交费表
      logger.debug("Start 更新个人交费表...");
      LJSPayPersonDBSet tLJSPayPersonUpdateDBSet=new LJSPayPersonDBSet(conn);
      tLJSPayPersonUpdateDBSet.set((LJSPayPersonSet)mInputData.getObject(1));
      if (!tLJSPayPersonUpdateDBSet.update())
      {
    		// @@错误处理
		this.mErrors.copyAllErrors(tLJSPayPersonUpdateDBSet.mErrors);
    		CError tError = new CError();
	        tError.moduleName = "PreParePayPersonBLS";
	        tError.functionName = "deleteData";
	        tError.errorMessage = "应收个人交费表数据更新失败!";
	        this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
 	        return false;
      }      
logger.debug("更新应收个人交费表");
      
      conn.commit() ;
      conn.close();

    }
    catch (Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="PreParePayPersonBLS";
      tError.functionName="submitData";
      tError.errorMessage=ex.toString();
      this.mErrors .addOneError(tError);
      try{conn.rollback() ;} catch(Exception e){}
      tReturn=false;
    }
    return tReturn;
  }
} 
