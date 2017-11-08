package com.sinosoft.lis.reagent;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
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

public class SpePolBankTagBLS {
private static Logger logger = Logger.getLogger(SpePolBankTagBLS.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors=new CErrors();

    public SpePolBankTagBLS() {
    }
    public static void main(String[] args) {

    }
    public boolean submitData(VData cInputData,String cOperate)
    {
        boolean tReturn=false;
        logger.debug("begin SpePolBankTagBLS ");
        tReturn=updata(cInputData);
        if (tReturn)
            logger.debug(" sucessful");
        else
            logger.debug("Save failed") ;
        return tReturn;
    }
    private boolean updata(VData cInputData)
    {
        boolean tReturn =true;
        logger.debug("start save.....");
        Connection conn;
        conn=null;
        conn=DBConnPool.getConnection();
        if(conn==null)
        {
            CError tError = new CError();
            tError.moduleName = "SpePolBankTagBLS";
            tError.functionName = "update";
            tError.errorMessage = "数据库连接失败!";
            this.mErrors .addOneError(tError) ;
            return false;
        }
        try
        {
            conn.setAutoCommit(false);
                 
            logger.debug("update LJSPay");
            LJSPayDBSet tLJSPayDBSet=new LJSPayDBSet(conn);
            LJSPaySet tLJSPaySet=new LJSPaySet();
            tLJSPaySet=(LJSPaySet)cInputData.get(0);
            if(tLJSPaySet.size()>0)
            {
              tLJSPayDBSet.set(tLJSPaySet);
              if(!tLJSPayDBSet.update())
              {
                CError tCError =new CError();
                tCError.moduleName="SpePolBankTagBLS";
                tCError.functionName="Updata";
                tCError.errorMessage="更新LJSPay表失败";
                this.mErrors.addOneError(tCError);
                conn.rollback();
                conn.close();
              }
            }


        conn.commit();
        conn.close();
        }
        catch(Exception e)
        {
            // @@错误处理
            CError tError =new CError();
            tError.moduleName="SpePolBankTagBLS";
            tError.functionName="submitData";
            tError.errorMessage=e.toString();
            logger.debug("e.toString()"+e.toString());
            this.mErrors .addOneError(tError);
            tReturn=false;
            try
            {
                conn.rollback() ;
                conn.close();
            }
             catch(Exception ex){}
        }
        return tReturn;
    }
}
