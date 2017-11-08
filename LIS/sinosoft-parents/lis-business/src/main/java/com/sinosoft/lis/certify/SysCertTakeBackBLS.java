/*
 * <p>ClassName: SysCertTakeBackBLS </p>
 * <p>Description: SysCertTakeBackBLS类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-10-29
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

public class SysCertTakeBackBLS {
private static Logger logger = Logger.getLogger(SysCertTakeBackBLS.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();

     /** 数据操作字符串 */
    private String mOperation;

    public SysCertTakeBackBLS() {
    }

    public static void main(String[] args) {
    }

     /**
      * 传输数据的公共方法
     */
    public boolean submitData(VData cInputData,String cOperate)
    {
        boolean bReturn = false;

        mOperation = verifyOperate(cOperate);

        if( mOperation.equals("") )
        {
            buildError("submitData", "不支持的操作字符串");
            return false;
        }

        logger.debug("Start SysCertTakeBackBLS Submit...");

        try
        {
          if( mOperation.equals("INSERT||MAIN") )
          {
            bReturn = saveLZSysCertify(cInputData);
          }
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
          buildError("submitData", "发生异常");
          return false;
        }

        if( bReturn ) {
          logger.debug("Success to deal data");
        } else {
          logger.debug("Fail to deal data") ;
        }

        logger.debug("End LZSysCertTakeBackBLS Submit...");
        return bReturn;
    }

    /**
     * 保存函数
     */
    private boolean saveLZSysCertify(VData mInputData)
    {
      logger.debug("Start Save...");
      Connection conn = null;

      try
      {
        LZSysCertifySchema tLZSysCertifySchema = null;
        GlobalInput globalInput = new GlobalInput();

        globalInput.setSchema((GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0));

        String szCurDate = PubFun.getCurrentDate();
        String szCurTime = PubFun.getCurrentTime();

        boolean bFireEvent = false;  // 是否要执行系统单证回收的描述

        conn = DBConnPool.getConnection();
        if( conn == null ) {
          buildError("saveLZSysCertify", "连接数据库失败");
          return false;
        }
        conn.setAutoCommit(false);                   //禁止事务自动提交

        // 需要删除的数据
        tLZSysCertifySchema = (LZSysCertifySchema)mInputData.get(1);

        LZSysCertifyDB tLZSysCertifyDB = new LZSysCertifyDB(conn);
        tLZSysCertifyDB.setSchema(tLZSysCertifySchema);

        if( !tLZSysCertifyDB.delete() ) {
          mErrors.copyAllErrors( tLZSysCertifyDB.mErrors );
          buildError("saveLZSysCertify", "数据删除失败");
          conn.rollback();
          conn.close();
          return false;
        }

        // 在单证由发放状态转变到回收状态时触发
        if( tLZSysCertifyDB.getStateFlag().equals("0") )
        {
          bFireEvent = true;
        }
        else
        {
          bFireEvent = false;
        }

        // 需要增加的数据
        tLZSysCertifySchema = (LZSysCertifySchema)mInputData.get(2);
        tLZSysCertifyDB.setSchema(tLZSysCertifySchema);

        if( !tLZSysCertifyDB.insert() )
        {
          mErrors.copyAllErrors( tLZSysCertifyDB.mErrors );
          buildError("saveLZSysCertify", "数据保存失败");
          conn.rollback();
          conn.close();
          return false;
        }

        // 执行系统单证描述表中的内容
        if( bFireEvent )
        {
          //捕获处理失败的情况，进行事务的回滚
          if(!CertifyFunc.handleSysCertifyDesc(tLZSysCertifyDB,conn,1,new StringBuffer()))
          {
            mErrors.copyAllErrors( tLZSysCertifyDB.mErrors );
            buildError("saveLZSysCertify", "执行描述算法出错！");
            conn.rollback();
            conn.close();
            return false;
          }
        }

        conn.commit();
        conn.close();

        } catch (Exception ex)
        {
          ex.printStackTrace();
          buildError("saveLZSysCertify", "发生异常");

          try {
            conn.rollback();
            conn.close();
            } catch (Exception e) { }

            return false;
        }

        logger.debug("End Save...");
        return true;
  }

  /*
   * add by kevin, 2002-09-23
   */
  private void buildError(String szFunc, String szErrMsg)
  {
    CError cError = new CError( );

    cError.moduleName = "SysCertTakeBackBLS";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }

  private String verifyOperate(String szOperate)
  {
      String szReturn = "";
      String szOperates[] = {"INSERT||MAIN", "DELETE||MAIN", "UPDATE||MAIN", "QUERY||MAIN"};

      for(int nIndex = 0; nIndex < szOperates.length; nIndex ++) {
            if( szOperate.equals(szOperates[nIndex]) ) {
                szReturn = szOperate;
            }
      }

      return szReturn;
  }
}
