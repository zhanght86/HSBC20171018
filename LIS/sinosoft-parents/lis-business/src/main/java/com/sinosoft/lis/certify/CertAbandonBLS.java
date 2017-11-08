package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.utility.*;
import java.sql.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description : 单证管理废除、遗失操作</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Sinosoft</p>
 * @author wentao
 * @version 1.0
 */

public class CertAbandonBLS  {
private static Logger logger = Logger.getLogger(CertAbandonBLS.class);
    //错误处理类，每个需要错误处理的类中都放置该类
    public  CErrors mErrors = new CErrors();

    public CertAbandonBLS() {}

    public static void main(String[] args)
    {
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        boolean bReturn = true;

        Connection conn = DBConnPool.getConnection();
        if( conn == null )
        {
            buildError("saveData", "连接数据库失败");
            return false;
        }

        try
        {
            // 开始事务
            conn.setAutoCommit(false);

            if( !saveData(cInputData, conn) )
                bReturn = false;

            if( bReturn )
                conn.commit();
            else
                conn.rollback();

            conn.close();
        } catch (Exception ex)
        {
            try
            {
                if( conn != null )
                {
                    conn.rollback();
                    conn.close();
                }
            } catch (Exception e) {
                // do nothing
            }
            bReturn = false;
        }

        return bReturn;
    }

    /**
     * 保存数据。在传入的VData中。
     * 第一个元素是LZCardSet，是要修改的数据。
     * 第二个元素是要插入到LZCardTrack表中的数据集合
     * @param vData
     * @return
     */
    private boolean saveData(VData vData, Connection conn)
    {
        try
        {
            //因目前只有总部有权限进行该功能操作，所以不需要进行锁表操作；如果以后下放到分公司，需要对记录进行锁定

            LZCardSet tLZCardSet = null;
            tLZCardSet = (LZCardSet)vData.getObjectByObjectName("LZCardSet",0);
            LZCardDBSet tLZCardDBSet = new LZCardDBSet(conn);
            if( tLZCardSet != null )
            {
                tLZCardDBSet.set(tLZCardSet);
                if( !tLZCardDBSet.update() )
                {
                    mErrors.copyAllErrors(tLZCardDBSet.mErrors);
                    throw new Exception("修改原有的LZCard数据时出错！");
                }
            }

            LZCardTrackSet tLZCardTrackSet = null;
            tLZCardTrackSet = (LZCardTrackSet)vData.getObjectByObjectName("LZCardTrackSet",0);
            if( tLZCardTrackSet != null )
            {
                LZCardTrackDBSet tLZCardTrackDBSet = new LZCardTrackDBSet(conn);
                tLZCardTrackDBSet.set(tLZCardTrackSet);
                if( !tLZCardTrackDBSet.insert() )
                {
                    mErrors.copyAllErrors(tLZCardTrackDBSet.mErrors);
                    throw new Exception("插入轨迹表信息出错！");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError( );

        cError.moduleName = "CertAbandonBLS";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
}

