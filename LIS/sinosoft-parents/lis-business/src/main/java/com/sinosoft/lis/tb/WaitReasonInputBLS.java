package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author YT
 * @version 1.0
 */
public class WaitReasonInputBLS
{
private static Logger logger = Logger.getLogger(WaitReasonInputBLS.class);

    /** 传入的数据 */
    private VData mInputData;

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 数据操作字符串 */
    private String mOperate;

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    
    private LWMissionSchema mLWMissionSchema = new LWMissionSchema();
    private LBMissionSchema mLBMissionSchema = new LBMissionSchema();

    public WaitReasonInputBLS()
    {
    }

    /**
      传输数据的公共方法
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;

        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();
        
        if (!checkData())
        {
            return false;
        }          

        //更新lwmission missionprop18为6状态;
        //备份原来的lwmission
        Connection conn = null;
        conn = DBConnPool.getConnection();
        if (conn == null)
        {
            // @@错误处理
            CError.buildErr(this, "数据库连接失败!");
            return false;
        }
        try
        {
            logger.debug("Start Save...");
            conn.setAutoCommit(false);

            // 投保单系列表插库动作
            if (this.dealData(conn) == false)
            {
                conn.rollback();
                conn.close();
                return false;
            }          
            
            conn.commit();
            conn.close();
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError.buildErr(this, ex.toString());           
            try
            {
                conn.rollback();
                conn.close();
            }
            catch (Exception e)
            {}
            return false;
        }

        return true;
    }
    
    /**
     * 校验数据
     **/
    private boolean checkData()
    {
        //更新
    	mLWMissionSchema = (LWMissionSchema) mInputData.getObjectByObjectName(
                "LWMissionSchema", 0);
    	mLBMissionSchema = (LBMissionSchema) mInputData.getObjectByObjectName(
                "LBMissionSchema", 0);
        if ( mLWMissionSchema == null)
        {
            // @@错误处理
            CError.buildErr(this, "mLWMissionSchema传入失败!");
            return false;
        }
        
        if ( mLBMissionSchema == null)
        {
            // @@错误处理
            CError.buildErr(this, "mLBMissionSchema传入失败!");
            return false;
        }
       
        return true;
    }   

    /**
     * 数据存储
     **/
    private boolean dealData(Connection conn)
    {
        //更新
        LWMissionDB tLWMissionDB = new LWMissionDB(conn);
        tLWMissionDB.setSchema(mLWMissionSchema);
        if (!tLWMissionDB.update())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLWMissionDB.mErrors);
            CError.buildErr(this, "核保等待数据保存失败!");
            return false;
        }

        // 备份
        logger.debug("Start 备份...");
        LBMissionDB tLBMissionDB = new LBMissionDB(conn);
        tLBMissionDB.setSchema(mLBMissionSchema);
        if (!tLBMissionDB.insert())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLBMissionDB.mErrors);
            CError.buildErr(this, "数据备份失败!");
            return false;
        }
       
        return true;
    }   

}
