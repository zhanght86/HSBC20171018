/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.io.StringReader;
import java.sql.*;
import com.sinosoft.lis.schema.PD_LCCardSetMealPlanDutyParamSchema;
import com.sinosoft.lis.vschema.PD_LCCardSetMealPlanDutyParamSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: PD_LCCardSetMealPlanDutyParamDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Company: Sinosoft Co.,LTD</p>
 * @Database: 卡单
 * @author：Makerx
 * @CreateDate：2009-04-21
 */
public class PD_LCCardSetMealPlanDutyParamDBSet extends PD_LCCardSetMealPlanDutyParamSet
{
private static Logger logger = Logger.getLogger(PD_LCCardSetMealPlanDutyParamDBSet.class);

    // @Field
    private Connection con;
    private DBOper db;
    /**
     * flag = true: 传入Connection
     * flag = false: 不传入Connection
     */
    private boolean mflag = false;

    // @Constructor
    public PD_LCCardSetMealPlanDutyParamDBSet(Connection cConnection)
    {
        con = cConnection;
        db = new DBOper(con, "PD_LCCardSetMealPlanDutyParam");
        mflag = true;
    }

    public PD_LCCardSetMealPlanDutyParamDBSet()
    {
        db = new DBOper("PD_LCCardSetMealPlanDutyParam");
    }

    // @Method
    public boolean deleteSQL()
    {
        if (db.deleteSQL(this))
        {
            return true;
        }
        else
        {
            // @@错误处理
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LCCardSetMealPlanDutyParamDBSet";
            tError.functionName = "deleteSQL";
            tError.errorMessage = "操作失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
    }

    /**
     * 新增操作
     * @return boolean
     */
    public boolean insert()
    {
        PreparedStatement pstmt = null;
        if (!mflag)
        {
            con = DBConnPool.getConnection();
        }
        try
        {
            if (!mflag)
            {
                // 如果是内部创建的连接，需要设置Commit模式
                con.setAutoCommit(false);
            }
            int tCount = this.size();
            pstmt = con.prepareStatement("INSERT INTO PD_LCCardSetMealPlanDutyParam VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getSetMealCode() == null || this.get(i).getSetMealCode().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getSetMealCode());
                }
                if (this.get(i).getSetMealName() == null || this.get(i).getSetMealName().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getSetMealName());
                }
                if (this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getRiskCode());
                }
                if (this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getDutyCode());
                }
                if (this.get(i).getCalFactor() == null || this.get(i).getCalFactor().equals("null"))
                {
                    pstmt.setString(5, null);
                }
                else
                {
                    pstmt.setString(5, this.get(i).getCalFactor());
                }
                if (this.get(i).getCalFactorValue() == null || this.get(i).getCalFactorValue().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getCalFactorValue());
                }
                if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
                {
                    pstmt.setString(7, null);
                }
                else
                {
                    pstmt.setString(7, this.get(i).getOperator());
                }
                if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
                {
                    pstmt.setDate(8, null);
                }
                else
                {
                    pstmt.setDate(8, Date.valueOf(this.get(i).getMakeDate()));
                }
                if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
                {
                    pstmt.setString(9, null);
                }
                else
                {
                    pstmt.setString(9, this.get(i).getMakeTime());
                }
                if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
                {
                    pstmt.setDate(10, null);
                }
                else
                {
                    pstmt.setDate(10, Date.valueOf(this.get(i).getModifyDate()));
                }
                if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
                {
                    pstmt.setString(11, null);
                }
                else
                {
                    pstmt.setString(11, this.get(i).getModifyTime());
                }
                if (this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null"))
                {
                    pstmt.setString(12, null);
                }
                else
                {
                    pstmt.setString(12, this.get(i).getStandbyFlag1());
                }
                if (this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null"))
                {
                    pstmt.setString(13, null);
                }
                else
                {
                    pstmt.setString(13, this.get(i).getStandbyFlag2());
                }
                if (this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null"))
                {
                    pstmt.setString(14, null);
                }
                else
                {
                    pstmt.setString(14, this.get(i).getStandbyFlag3());
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            pstmt.close();
            if (!mflag)
            {
                // 如果是内部创建的连接，执行成功后需要执行Commit
                con.commit();
                con.close();
            }
        }
        catch (Exception ex)
        {
            // @@错误处理
            ex.printStackTrace();
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LCCardSetMealPlanDutyParamDBSet";
            tError.functionName = "insert()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_LCCardSetMealPlanDutyParam");
            for (int i = 1; i <= tCount; i++)
            {
                // 输出出错Sql语句
                sqlObj.setSQL(1, this.get(i));
                logger.debug("出错Sql为：" + sqlObj.getSQL());
            }
            try
            {
                pstmt.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (!mflag)
            {
                //如果是内部创建的连接，出错后需要执行RollBack
                try
                {
                    con.rollback();
                    con.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return false;
        }
        finally
        {
            if (!mflag)
            {
                try
                {
                    if(con.isClosed() == false)
                    {
                        con.close();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 删除操作
     * 删除条件：主键
     * @return boolean
     */
    public boolean delete()
    {
        PreparedStatement pstmt = null;
        if (!mflag)
        {
            con = DBConnPool.getConnection();
        }
        try
        {
            if (!mflag)
            {
                // 如果是内部创建的连接，需要设置Commit模式
                con.setAutoCommit(false);
            }
            int tCount = this.size();
            pstmt = con.prepareStatement("DELETE FROM PD_LCCardSetMealPlanDutyParam WHERE  SetMealCode = ? AND SetMealName = ? AND RiskCode = ? AND DutyCode = ? AND CalFactor = ?");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getSetMealCode() == null || this.get(i).getSetMealCode().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getSetMealCode());
                }
                if (this.get(i).getSetMealName() == null || this.get(i).getSetMealName().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getSetMealName());
                }
                if (this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getRiskCode());
                }
                if (this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getDutyCode());
                }
                if (this.get(i).getCalFactor() == null || this.get(i).getCalFactor().equals("null"))
                {
                    pstmt.setString(5, null);
                }
                else
                {
                    pstmt.setString(5, this.get(i).getCalFactor());
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            pstmt.close();
            if (!mflag)
            {
                // 如果是内部创建的连接，执行成功后需要执行Commit
                con.commit();
                con.close();
            }
        }
        catch (Exception ex)
        {
            // @@错误处理
            ex.printStackTrace();
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LCCardSetMealPlanDutyParamDBSet";
            tError.functionName = "delete()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_LCCardSetMealPlanDutyParam");
            for (int i = 1; i <= tCount; i++)
            {
                // 输出出错Sql语句
                sqlObj.setSQL(4, this.get(i));
                logger.debug("出错Sql为：" + sqlObj.getSQL());
            }
            try
            {
                pstmt.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (!mflag)
            {
                //如果是内部创建的连接，出错后需要执行RollBack
                try
                {
                    con.rollback();
                    con.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return false;
        }
        finally
        {
            if (!mflag)
            {
                try
                {
                    if(con.isClosed() == false)
                    {
                        con.close();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 更新操作
     * 更新条件：主键
     * @return boolean
     */
    public boolean update()
    {
        PreparedStatement pstmt = null;
        if (!mflag)
        {
            con = DBConnPool.getConnection();
        }
        try
        {
            if (!mflag)
            {
                // 如果是内部创建的连接，需要设置Commit模式
                con.setAutoCommit(false);
            }
            int tCount = this.size();
            pstmt = con.prepareStatement("UPDATE PD_LCCardSetMealPlanDutyParam SET  SetMealCode = ? , SetMealName = ? , RiskCode = ? , DutyCode = ? , CalFactor = ? , CalFactorValue = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? WHERE  SetMealCode = ? AND SetMealName = ? AND RiskCode = ? AND DutyCode = ? AND CalFactor = ?");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getSetMealCode() == null || this.get(i).getSetMealCode().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getSetMealCode());
                }
                if (this.get(i).getSetMealName() == null || this.get(i).getSetMealName().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getSetMealName());
                }
                if (this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getRiskCode());
                }
                if (this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getDutyCode());
                }
                if (this.get(i).getCalFactor() == null || this.get(i).getCalFactor().equals("null"))
                {
                    pstmt.setString(5, null);
                }
                else
                {
                    pstmt.setString(5, this.get(i).getCalFactor());
                }
                if (this.get(i).getCalFactorValue() == null || this.get(i).getCalFactorValue().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getCalFactorValue());
                }
                if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
                {
                    pstmt.setString(7, null);
                }
                else
                {
                    pstmt.setString(7, this.get(i).getOperator());
                }
                if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
                {
                    pstmt.setDate(8, null);
                }
                else
                {
                    pstmt.setDate(8, Date.valueOf(this.get(i).getMakeDate()));
                }
                if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
                {
                    pstmt.setString(9, null);
                }
                else
                {
                    pstmt.setString(9, this.get(i).getMakeTime());
                }
                if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
                {
                    pstmt.setDate(10, null);
                }
                else
                {
                    pstmt.setDate(10, Date.valueOf(this.get(i).getModifyDate()));
                }
                if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
                {
                    pstmt.setString(11, null);
                }
                else
                {
                    pstmt.setString(11, this.get(i).getModifyTime());
                }
                if (this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null"))
                {
                    pstmt.setString(12, null);
                }
                else
                {
                    pstmt.setString(12, this.get(i).getStandbyFlag1());
                }
                if (this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null"))
                {
                    pstmt.setString(13, null);
                }
                else
                {
                    pstmt.setString(13, this.get(i).getStandbyFlag2());
                }
                if (this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null"))
                {
                    pstmt.setString(14, null);
                }
                else
                {
                    pstmt.setString(14, this.get(i).getStandbyFlag3());
                }
                if (this.get(i).getSetMealCode() == null || this.get(i).getSetMealCode().equals("null"))
                {
                    pstmt.setString(15, null);
                }
                else
                {
                    pstmt.setString(15, this.get(i).getSetMealCode());
                }
                if (this.get(i).getSetMealName() == null || this.get(i).getSetMealName().equals("null"))
                {
                    pstmt.setString(16, null);
                }
                else
                {
                    pstmt.setString(16, this.get(i).getSetMealName());
                }
                if (this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null"))
                {
                    pstmt.setString(17, null);
                }
                else
                {
                    pstmt.setString(17, this.get(i).getRiskCode());
                }
                if (this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null"))
                {
                    pstmt.setString(18, null);
                }
                else
                {
                    pstmt.setString(18, this.get(i).getDutyCode());
                }
                if (this.get(i).getCalFactor() == null || this.get(i).getCalFactor().equals("null"))
                {
                    pstmt.setString(19, null);
                }
                else
                {
                    pstmt.setString(19, this.get(i).getCalFactor());
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            pstmt.close();
            if (!mflag)
            {
                // 如果是内部创建的连接，执行成功后需要执行Commit
                con.commit();
                con.close();
            }
        }
        catch (Exception ex)
        {
            // @@错误处理
            ex.printStackTrace();
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LCCardSetMealPlanDutyParamDBSet";
            tError.functionName = "update()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_LCCardSetMealPlanDutyParam");
            for (int i = 1; i <= tCount; i++)
            {
                // 输出出错Sql语句
                sqlObj.setSQL(2, this.get(i));
                logger.debug("出错Sql为：" + sqlObj.getSQL());
            }
            try
            {
                pstmt.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (!mflag)
            {
                //如果是内部创建的连接，出错后需要执行RollBack
                try
                {
                    con.rollback();
                    con.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return false;
        }
        finally
        {
            if (!mflag)
            {
                try
                {
                    if(con.isClosed() == false)
                    {
                        con.close();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
