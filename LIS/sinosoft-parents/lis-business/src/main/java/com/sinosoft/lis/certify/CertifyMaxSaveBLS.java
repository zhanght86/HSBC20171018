package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;
import java.sql.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;
/**
 * <p>Title: 立案模块基本功能的修改操作</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002 </p>
 * <p>Company: Sinosoft< /p>
 * @author 刘岩松
 * @version 1.0
 */
public class CertifyMaxSaveBLS
{
private static Logger logger = Logger.getLogger(CertifyMaxSaveBLS.class);

  private VData mInputData ;
  private LDAgentCardCountSet mLDAgentCardCountSet = new LDAgentCardCountSet();
  public  CErrors mErrors = new CErrors();
  public String mOperate;
  private String mUpdateType = "";
  public CertifyMaxSaveBLS() {}

  public static void main(String[] args)
  {
  }

  public boolean submitData(VData cInputData,String cOperate)
  {
    mInputData=(VData)cInputData.clone();
    String mOperate;
    mOperate=cOperate;
    logger.debug("开始接收数据！！！");
    mLDAgentCardCountSet=(LDAgentCardCountSet)mInputData.getObjectByObjectName("LDAgentCardCountSet",0);
    if (cOperate.equals("UPDATE"))
    {
      try
      {
        mUpdateType = (String)cInputData.get(0);
        logger.debug("修改的类型是"+mUpdateType);
        if (!this.saveData())
          return false;
      }
      catch(Exception ex)
      {
      }
      return true;
    }
    logger.debug("Start CertifyMaxSave BLS Submit...");
    mInputData=null;
    return true;
  }

  private boolean saveData()
  {
    logger.debug("开始执行save操作");
    Connection conn = DBConnPool.getConnection();
    logger.debug("save save save");
    if (conn==null)
    {
      CError tError = new CError();
      tError.moduleName = "CertifyMaxSaveBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    try
    {
      conn.setAutoCommit(false);
      logger.debug("开始执行删除操作！！！");
      int n = mLDAgentCardCountSet.size();
      logger.debug("共有"+n+"条记录！！！");
      for (int i = 1; i <= n; i++ )
      {
        LDAgentCardCountSchema tLDAgentCardCountSchema = new LDAgentCardCountSchema();
        tLDAgentCardCountSchema.setSchema(mLDAgentCardCountSet.get(i));
        LDAgentCardCountDB tLDAgentCardCountDB = new LDAgentCardCountDB( conn );
        if(mUpdateType.equals("A"))
        {
          tLDAgentCardCountDB.setManageCom("*");
          tLDAgentCardCountDB.setAgentGrade("*");
          logger.debug("代理人代码是"+tLDAgentCardCountSchema.getAgentCode());
          tLDAgentCardCountDB.setAgentCode(tLDAgentCardCountSchema.getAgentCode());
          tLDAgentCardCountDB.setCertifyCode(tLDAgentCardCountSchema.getCertifyCode());
          tLDAgentCardCountDB.delete();
        }
        if(mUpdateType.equals("M"))
        {
          tLDAgentCardCountDB.setManageCom(tLDAgentCardCountSchema.getManageCom());
          tLDAgentCardCountDB.setAgentGrade(tLDAgentCardCountSchema.getAgentGrade());
          tLDAgentCardCountDB.setCertifyCode(tLDAgentCardCountSchema.getCertifyCode());
          tLDAgentCardCountDB.setAgentCode("*");
          tLDAgentCardCountDB.delete();
        }
        //        tLDAgentCardCountDB.setAgentGrade(tLDAgentCardCountSchema.getAgentGrade());
        //        tLDAgentCardCountDB.setAgentCode(tLDAgentCardCountSchema.getAgentCode());
        //        tLDAgentCardCountDB.deleteSQL();
      }
      LDAgentCardCountDBSet tLDAgentCardCountDBSet = new LDAgentCardCountDBSet( conn );
      tLDAgentCardCountDBSet.set(mLDAgentCardCountSet);
      if(tLDAgentCardCountDBSet.insert() == false)
      {
        logger.debug("开始执行save操作4");
       // this.mErrors.copyAllErrors(tLDAgentCardCountDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "RegisterBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "出现重复信息，请仔细查看相应的记录！！！";
        this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
        return false;
      }
      conn.commit() ;
      conn.close();
      logger.debug("save7");
    }
    catch (Exception ex)
    {
      CError tError = new CError();
      tError.moduleName = "RegisterUpdateBLS";
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
    logger.debug("执行结束！！");
    return true;
  }
}
