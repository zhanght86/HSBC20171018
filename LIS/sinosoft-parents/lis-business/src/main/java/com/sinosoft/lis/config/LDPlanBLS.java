package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

import java.sql.Connection;

import com.sinosoft.lis.db.LDPlanDB;
import com.sinosoft.lis.vdb.LDPlanDBSet;
import com.sinosoft.lis.vdb.LDPlanDutyParamDBSet;
import com.sinosoft.lis.vdb.LDPlanRiskDBSet;
import com.sinosoft.lis.vschema.LDPlanDutyParamSet;
import com.sinosoft.lis.vschema.LDPlanRiskSet;
import com.sinosoft.lis.vschema.LDPlanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;


/**
 * 保障计划数据提交类
 * <p>Title: </p>
 * <p>Description: 根据传入的操作类型，进行新增、删除、修改操作 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author 朱向峰
 * @version 1.0
 */
public class LDPlanBLS
{
private static Logger logger = Logger.getLogger(LDPlanBLS.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();


    /** 数据操作字符串 */
    private String mOperate;
    public LDPlanBLS()
    {
    }

    public static void main(String[] args)
    {
    }


    /**
     * 传输数据的公共方法
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        boolean tReturn = false;
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;

        logger.debug("Start LDPlanBLS Submit...");
        if (this.mOperate.equals("INSERT||MAIN"))
        {
            tReturn = saveLDPlan(cInputData);
        }
        if (this.mOperate.equals("DELETE||MAIN"))
        {
            tReturn = deleteLDPlan(cInputData);
        }
        if (this.mOperate.equals("UPDATE||MAIN"))
        {
            tReturn = updateLDPlan(cInputData);
        }
        if (tReturn)
        {
            logger.debug(" sucessful");
        }
        else
        {
            logger.debug("Save failed");
        }
        logger.debug("End LDPlanBLS Submit...");
        return tReturn;
    }


    /**
     * 新增处理
     * @param mInputData VData
     * @return boolean
     */
    private boolean saveLDPlan(VData mInputData)
    {
        boolean tReturn = true;
        logger.debug("Start Save...");
        Connection conn;
        conn = null;
        conn = DBConnPool.getConnection();
        if (conn == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDPlanBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "数据库连接失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);
            logger.debug("Start 保存...");
            LDPlanDBSet tLDPlanDBSet = new LDPlanDBSet(conn);
            tLDPlanDBSet.set((LDPlanSet) mInputData.
                             getObjectByObjectName(
                                     "LDPlanSet", 0));

            LDPlanDB tLDPlanDB = new LDPlanDB(conn);
            tLDPlanDB.setContPlanCode(tLDPlanDBSet.get(1).
                                      getContPlanCode());
            LDPlanSet tLDPlanSet = tLDPlanDB.query();
            if (tLDPlanSet.size() == 0)
            {
                if (!tLDPlanDBSet.insert())
                {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "LDPlanBLS";
                    tError.functionName = "saveData";
                    tError.errorMessage = "数据保存失败!";
                    this.mErrors.addOneError(tError);
                    conn.rollback();
                    conn.close();
                    return false;
                }
            }

            LDPlanRiskDBSet tLDPlanRiskDBSet = new LDPlanRiskDBSet(
                    conn);
            tLDPlanRiskDBSet.set((LDPlanRiskSet) mInputData.
                                 getObjectByObjectName(
                                         "LDPlanRiskSet", 0));
            if (!tLDPlanRiskDBSet.insert())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDPlanBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            LDPlanDutyParamDBSet tLDPlanDutyParamDBSet = new
                    LDPlanDutyParamDBSet(conn);
            tLDPlanDutyParamDBSet.set((LDPlanDutyParamSet) mInputData.
                                      getObjectByObjectName(
                                              "LDPlanDutyParamSet", 0));
            if (!tLDPlanDutyParamDBSet.insert())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDPlanBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors.addOneError(tError);
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
            CError tError = new CError();
            tError.moduleName = "LDPlanBLS";
            tError.functionName = "submitData";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            tReturn = false;
            try
            {
                conn.rollback();
                conn.close();
            }
            catch (Exception e)
            {}
        }
        return tReturn;
    }


    /**
     * 删除处理
     * @param mInputData VData
     * @return boolean
     */
    private boolean deleteLDPlan(VData mInputData)
    {
        boolean tReturn = true;
        logger.debug("Start Save...");
        Connection conn;
        conn = null;
        conn = DBConnPool.getConnection();
        if (conn == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDPlanBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "数据库连接失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);
            logger.debug("Start 保存...");
            LDPlanDBSet tLDPlanDBSet = new LDPlanDBSet(conn);
            tLDPlanDBSet.set((LDPlanSet) mInputData.
                             getObjectByObjectName(
                                     "LDPlanSet", 0));
            if (!tLDPlanDBSet.delete())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDPlanBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            LDPlanRiskDBSet tLDPlanRiskDBSet = new LDPlanRiskDBSet(
                    conn);
            tLDPlanRiskDBSet.set((LDPlanRiskSet) mInputData.
                                 getObjectByObjectName(
                                         "LDPlanRiskSet", 0));
            if (!tLDPlanRiskDBSet.delete())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDPlanBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            LDPlanDutyParamDBSet tLDPlanDutyParamDBSet = new
                    LDPlanDutyParamDBSet(conn);
            tLDPlanDutyParamDBSet.set((LDPlanDutyParamSet) mInputData.
                                      getObjectByObjectName(
                                              "LDPlanDutyParamSet", 0));
            if (!tLDPlanDutyParamDBSet.delete())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDPlanBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors.addOneError(tError);
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
            CError tError = new CError();
            tError.moduleName = "LDPlanBLS";
            tError.functionName = "submitData";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            tReturn = false;
            try
            {
                conn.rollback();
                conn.close();
            }
            catch (Exception e)
            {}
        }
        return tReturn;
    }


    /**
     * 修改处理
     * @param mInputData VData
     * @return boolean
     */
    private boolean updateLDPlan(VData mInputData)
    {
        boolean tReturn = true;
        logger.debug("Start Save...");
        Connection conn;
        conn = null;
        conn = DBConnPool.getConnection();
        if (conn == null)
        {
            CError tError = new CError();
            tError.moduleName = "LDPlanBLS";
            tError.functionName = "updateData";
            tError.errorMessage = "数据库连接失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);
            logger.debug("Start 修改...");
            LDPlanDBSet tLDPlanDBSet = new LDPlanDBSet(conn);
            tLDPlanDBSet.set((LDPlanSet) mInputData.
                             getObjectByObjectName(
                                     "LDPlanSet", 0));
            //先执行删除操作
            ExeSQL tExeSQL = new ExeSQL(conn);
            String tSql = "delete from LDPlan where ContPlanCode='" +
                           "?ContPlanCode?"+ "'";
            SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
            sqlbv1.sql(tSql);
            sqlbv1.put("ContPlanCode",tLDPlanDBSet.get(1).getContPlanCode());
            if (!tExeSQL.execUpdateSQL(sqlbv1))
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDPlanBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }
            tSql = "delete from LDPlanRisk where  ContPlanCode='" +
                   "?ContPlanCode?" + "'";
            SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
            sqlbv2.sql(tSql);
            sqlbv2.put("ContPlanCode",tLDPlanDBSet.get(1).getContPlanCode());
            if (!tExeSQL.execUpdateSQL(sqlbv2))
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDPlanBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }
            tSql = "delete from LDPlanDutyParam where  ContPlanCode='" +
                   "?ContPlanCode?" + "'";
            SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
            sqlbv3.sql(tSql);
            sqlbv3.put("ContPlanCode",tLDPlanDBSet.get(1).getContPlanCode());
            if (!tExeSQL.execUpdateSQL(sqlbv3))
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDPlanBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            //删除成功，才执行新增操作，实现修改目的
            if (!tLDPlanDBSet.insert())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDPlanBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            LDPlanRiskDBSet tLDPlanRiskDBSet = new LDPlanRiskDBSet(
                    conn);
            tLDPlanRiskDBSet.set((LDPlanRiskSet) mInputData.
                                 getObjectByObjectName(
                                         "LDPlanRiskSet", 0));
            if (!tLDPlanRiskDBSet.insert())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDPlanBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            LDPlanDutyParamDBSet tLDPlanDutyParamDBSet = new
                    LDPlanDutyParamDBSet(conn);
            tLDPlanDutyParamDBSet.set((LDPlanDutyParamSet) mInputData.
                                      getObjectByObjectName(
                                              "LDPlanDutyParamSet", 0));
            if (!tLDPlanDutyParamDBSet.insert())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDPlanBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "数据保存失败!";
                this.mErrors.addOneError(tError);
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
            CError tError = new CError();
            tError.moduleName = "LDPlanBLS";
            tError.functionName = "submitData";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            tReturn = false;
            try
            {
                conn.rollback();
                conn.close();
            }
            catch (Exception e)
            {}
        }
        return tReturn;
    }
}
