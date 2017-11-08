/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.io.StringReader;
import java.sql.*;
import com.sinosoft.lis.schema.PD_LMCalFactorSortSchema;
import com.sinosoft.lis.vschema.PD_LMCalFactorSortSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: PD_LMCalFactorSortDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Company: Sinosoft Co.,LTD</p>
 * @Database: PhysicalDataModel_1
 * @author：Makerx
 * @CreateDate：2009-05-08
 */
public class PD_LMCalFactorSortDBSet extends PD_LMCalFactorSortSet
{
private static Logger logger = Logger.getLogger(PD_LMCalFactorSortDBSet.class);

    // @Field
    private Connection con;
    private DBOper db;
    /**
     * flag = true: 传入Connection
     * flag = false: 不传入Connection
     */
    private boolean mflag = false;

    // @Constructor
    public PD_LMCalFactorSortDBSet(Connection cConnection)
    {
        con = cConnection;
        db = new DBOper(con, "PD_LMCalFactorSort");
        mflag = true;
    }

    public PD_LMCalFactorSortDBSet()
    {
        db = new DBOper("PD_LMCalFactorSort");
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
            tError.moduleName = "PD_LMCalFactorSortDBSet";
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
            pstmt = con.prepareStatement("INSERT INTO PD_LMCalFactorSort VALUES( ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getRiskCode());
                }
                if (this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getGetDutyCode());
                }
                if (this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getDutyCode());
                }
                if (this.get(i).getFactorType() == null || this.get(i).getFactorType().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getFactorType());
                }
                if (this.get(i).getFactorSubType() == null || this.get(i).getFactorSubType().equals("null"))
                {
                    pstmt.setString(5, null);
                }
                else
                {
                    pstmt.setString(5, this.get(i).getFactorSubType());
                }
                if (this.get(i).getFactorName() == null || this.get(i).getFactorName().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getFactorName());
                }
                if (this.get(i).getFactorDesc() == null || this.get(i).getFactorDesc().equals("null"))
                {
                    pstmt.setString(7, null);
                }
                else
                {
                    pstmt.setString(7, this.get(i).getFactorDesc());
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
            tError.moduleName = "PD_LMCalFactorSortDBSet";
            tError.functionName = "insert()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_LMCalFactorSort");
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
            pstmt = con.prepareStatement("DELETE FROM PD_LMCalFactorSort WHERE  RiskCode = ? AND GetDutyCode = ? AND DutyCode = ? AND FactorType = ? AND FactorSubType = ? AND FactorName = ?");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getRiskCode());
                }
                if (this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getGetDutyCode());
                }
                if (this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getDutyCode());
                }
                if (this.get(i).getFactorType() == null || this.get(i).getFactorType().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getFactorType());
                }
                if (this.get(i).getFactorSubType() == null || this.get(i).getFactorSubType().equals("null"))
                {
                    pstmt.setString(5, null);
                }
                else
                {
                    pstmt.setString(5, this.get(i).getFactorSubType());
                }
                if (this.get(i).getFactorName() == null || this.get(i).getFactorName().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getFactorName());
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
            tError.moduleName = "PD_LMCalFactorSortDBSet";
            tError.functionName = "delete()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_LMCalFactorSort");
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
            pstmt = con.prepareStatement("UPDATE PD_LMCalFactorSort SET  RiskCode = ? , GetDutyCode = ? , DutyCode = ? , FactorType = ? , FactorSubType = ? , FactorName = ? , FactorDesc = ? WHERE  RiskCode = ? AND GetDutyCode = ? AND DutyCode = ? AND FactorType = ? AND FactorSubType = ? AND FactorName = ?");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getRiskCode());
                }
                if (this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getGetDutyCode());
                }
                if (this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getDutyCode());
                }
                if (this.get(i).getFactorType() == null || this.get(i).getFactorType().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getFactorType());
                }
                if (this.get(i).getFactorSubType() == null || this.get(i).getFactorSubType().equals("null"))
                {
                    pstmt.setString(5, null);
                }
                else
                {
                    pstmt.setString(5, this.get(i).getFactorSubType());
                }
                if (this.get(i).getFactorName() == null || this.get(i).getFactorName().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getFactorName());
                }
                if (this.get(i).getFactorDesc() == null || this.get(i).getFactorDesc().equals("null"))
                {
                    pstmt.setString(7, null);
                }
                else
                {
                    pstmt.setString(7, this.get(i).getFactorDesc());
                }
                if (this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null"))
                {
                    pstmt.setString(8, null);
                }
                else
                {
                    pstmt.setString(8, this.get(i).getRiskCode());
                }
                if (this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null"))
                {
                    pstmt.setString(9, null);
                }
                else
                {
                    pstmt.setString(9, this.get(i).getGetDutyCode());
                }
                if (this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null"))
                {
                    pstmt.setString(10, null);
                }
                else
                {
                    pstmt.setString(10, this.get(i).getDutyCode());
                }
                if (this.get(i).getFactorType() == null || this.get(i).getFactorType().equals("null"))
                {
                    pstmt.setString(11, null);
                }
                else
                {
                    pstmt.setString(11, this.get(i).getFactorType());
                }
                if (this.get(i).getFactorSubType() == null || this.get(i).getFactorSubType().equals("null"))
                {
                    pstmt.setString(12, null);
                }
                else
                {
                    pstmt.setString(12, this.get(i).getFactorSubType());
                }
                if (this.get(i).getFactorName() == null || this.get(i).getFactorName().equals("null"))
                {
                    pstmt.setString(13, null);
                }
                else
                {
                    pstmt.setString(13, this.get(i).getFactorName());
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
            tError.moduleName = "PD_LMCalFactorSortDBSet";
            tError.functionName = "update()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_LMCalFactorSort");
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
