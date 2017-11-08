package com.sinosoft.lis.otof;
import org.apache.log4j.Logger;

import java.sql.*;

import com.sinosoft.utility.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: Web业务系统投保暂交费功能部分 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002 </p>
 * <p>Company: Sinosoft< /p>
 * @author YT
 * @version 1.0
 */

public class OtoFBLS
{
private static Logger logger = Logger.getLogger(OtoFBLS.class);

    //传输数据类
    private VData mInputData ;
    //错误处理类，每个需要错误处理的类中都放置该类
    public  CErrors mErrors = new CErrors();

    public OtoFBLS() {}

    public static void main(String[] args)
    {
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData,String cOperate)
    {
        boolean tReturn = false;
        //首先将数据在本类中做一个备份
        mInputData=(VData)cInputData.clone() ;
        logger.debug("Start OtoF BLS Submit...");

        if (!this.saveData())
            return false;

        logger.debug("End OtoF BLS Submit...");
        mInputData=null;
        return true;
    }

    private boolean saveData()
    {
        //增加并发控制
        int tVoucherID = Integer.parseInt((String)mInputData.get(0));
        String tManageCom = (String)mInputData.get(1);
        String tBussDate = (String)mInputData.get(2);

        LITranInfoSet mLITranInfoSet = new LITranInfoSet();
        mLITranInfoSet = (LITranInfoSet)mInputData.getObjectByObjectName("LITranInfoSet",0);
        LITranErrSet mLITranErrSet = new LITranErrSet();
        mLITranErrSet = (LITranErrSet)mInputData.getObjectByObjectName("LITranErrSet",0);
        OFInterfaceSet mOFInterfaceSet = new OFInterfaceSet();
        mOFInterfaceSet = (OFInterfaceSet)mInputData.getObjectByObjectName("OFInterfaceSet",0);
        Connection conn = DBConnPool.getConnection();

        if (conn==null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "OtoFBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "数据库连接失败!";
            this.mErrors .addOneError(tError) ;
            return false;
        }

        try
        {
            conn.setAutoCommit(false);
            //2008-8-18 排除业务员押金的控制

            if(tVoucherID >= 10&& tVoucherID!=13 && tVoucherID!=82 && tVoucherID!=83)                       //控制销售系统凭证并发操作
            {
                String sql = "select comcode from ldcom "
                           + "where comcode like concat('?tManageCom?','%') for update nowait";
                SQLwithBindVariables sqlbv=new SQLwithBindVariables();
                sqlbv.sql(sql);
                sqlbv.put("tManageCom", tManageCom);
                logger.debug("-----控制并发操作-----");
                ExeSQL tExeSQL = new ExeSQL(conn);
                SSRS ssrs = (SSRS)tExeSQL.execSQL(sqlbv);
                if(tExeSQL.mErrors.needDealError() || ssrs.getMaxRow() <= 0)
                {
                    CError.buildErr(this,"资源忙，并发操作，请重新操作！");
                    conn.rollback() ;
                    conn.close();
                    return false;
                }

                //校验此时当月的凭证是否已经有插入数据
                sql = "select batchno from litraninfo "
                    + "where voucherid = ?tVoucherID? and managecom like concat('?tManageCom?','%') "
                    + "and bussdate = '?tBussDate?' ";
                SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
                sqlbv1.sql(sql);
                sqlbv1.put("tVoucherID", tVoucherID);
                sqlbv1.put("tManageCom", tManageCom);
                sqlbv1.put("tBussDate", tBussDate);
                logger.debug("校验是否已生成数据：" + sql);
                ExeSQL ttExeSQL = new ExeSQL(conn);
                SSRS tssrs = (SSRS)ttExeSQL.execSQL(sqlbv1);
                if(ttExeSQL.mErrors.needDealError() || tssrs.getMaxRow() > 0)
                {
                    CError.buildErr(this,"当月数据已经生成，请慎重操作！");
                    conn.rollback() ;
                    conn.close();
                    return false;
                }
            }

            LITranInfoDBSet mLITranInfoDBSet = new LITranInfoDBSet(conn);
            mLITranInfoDBSet.set(mLITranInfoSet);
            if (mLITranInfoDBSet.insert() == false)
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mLITranInfoDBSet.mErrors);
                CError tError = new CError();
                tError.moduleName = "OtoFBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "保存数据失败!";
                this.mErrors .addOneError(tError) ;
                conn.rollback() ;
                conn.close();
                return false;
            }

            LITranErrDBSet mLITranErrDBSet = new LITranErrDBSet(conn);
            mLITranErrDBSet.set(mLITranErrSet);
            if (mLITranErrDBSet.insert() == false)
            {
                // @@错误处理
                this.mErrors.copyAllErrors(mLITranErrDBSet.mErrors);
                CError tError = new CError();
                tError.moduleName = "OtoFBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "保存数据失败!";
                this.mErrors .addOneError(tError) ;
                conn.rollback() ;
                conn.close();
                return false;
            }

            if(mOFInterfaceSet != null)
            {
                OFInterfaceDBSet mOFInterfaceDBSet = new OFInterfaceDBSet(conn);
                mOFInterfaceDBSet.set(mOFInterfaceSet);
                if(mOFInterfaceDBSet.insert() == false)
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(mOFInterfaceDBSet.mErrors);
                    CError tError = new CError();
                    tError.moduleName = "OtoFBLS";
                    tError.functionName = "saveData";
                    tError.errorMessage = "保存数据失败!";
                    this.mErrors .addOneError(tError) ;
                    conn.rollback() ;
                    conn.close();
                    return false;
                }
            }

            conn.commit() ;
            conn.close();
        } // end of try
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ReportBLS";
            tError.functionName = "submitData";
            tError.errorMessage = ex.toString();
            this.mErrors .addOneError(tError);
            try{conn.rollback() ;
            conn.close();
            }

            catch(Exception e){}
            return false;
        }
        return true;
    }
}
