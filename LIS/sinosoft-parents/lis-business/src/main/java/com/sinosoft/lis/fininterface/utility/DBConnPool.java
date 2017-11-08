/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.fininterface.utility;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.FIDataBaseLinkSchema;
import com.sinosoft.utility.JdbcUrl;


/*
 * <p>ClassName: dbConnsPool </p>
 * <p>Description: 数据库连接池 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-10-04
 */
public class DBConnPool
{
private static Logger logger = Logger.getLogger(DBConnPool.class);



    // 构建函数
    private DBConnPool() {}


    /**
     * 获取连接
     * @return DBConn
     */
    static public DBConn getConnection(FIDataBaseLinkSchema tFIDataBaseLinkSchema) throws Exception
    {
       try
       {
           DBConn dbConn = new DBConn();
           if (tFIDataBaseLinkSchema.getDBType().toUpperCase().equals("WEBLOGICPOOL")
                           || tFIDataBaseLinkSchema.getDBType().toUpperCase().equals("COMMONSDBCP")
                                   || tFIDataBaseLinkSchema.getDBType().toUpperCase().equals("WEBSPHERE"))
           {

               if (dbConn.createConnection())
               {
                   return dbConn;
               }
               else
               {
                   return null;
               }
           }
           else
           {
               if (!dbConn.createConnection()) {
                   // 如果创建连接失败
                   DBSemaphore.UnLock();
                   return null;
               }
               try {
                   // 特殊处理连接的AutoCommit是否已经被设置
                   dbConn.setAutoCommit(true);
                   return dbConn;
               }
               catch (Exception ex)
               {
                   ex.printStackTrace();
                   return null;
               }
           }
       }
       catch (Exception ex)
       {
           throw ex;
       }


    }


}
