/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.io.StringReader;
import java.sql.*;
import com.sinosoft.lis.schema.PD_CheckRule_LibSchema;
import com.sinosoft.lis.vschema.PD_CheckRule_LibSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: PD_CheckRule_LibDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Company: Sinosoft Co.,LTD</p>
 * @Database: 产品定义平台_PDM
 * @author：Makerx
 * @CreateDate：2009-03-04
 */
public class PD_CheckRule_LibDBSet extends PD_CheckRule_LibSet
{
private static Logger logger = Logger.getLogger(PD_CheckRule_LibDBSet.class);

    // @Field
    private Connection con;
    private DBOper db;
    /**
     * flag = true: 传入Connection
     * flag = false: 不传入Connection
     */
    private boolean mflag = false;

    // @Constructor
    public PD_CheckRule_LibDBSet(Connection cConnection)
    {
        con = cConnection;
        db = new DBOper(con, "PD_CheckRule_Lib");
        mflag = true;
    }

    public PD_CheckRule_LibDBSet()
    {
        db = new DBOper("PD_CheckRule_Lib");
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
            tError.moduleName = "PD_CheckRule_LibDBSet";
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
            pstmt = con.prepareStatement("INSERT INTO PD_CheckRule_Lib VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getCode() == null || this.get(i).getCode().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getCode());
                }
                if (this.get(i).getName() == null || this.get(i).getName().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getName());
                }
                if (this.get(i).getAlgo() == null || this.get(i).getAlgo().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getAlgo());
                }
                if (this.get(i).getDescription() == null || this.get(i).getDescription().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getDescription());
                }
                if (this.get(i).getType() == null || this.get(i).getType().equals("null"))
                {
                    pstmt.setString(5, null);
                }
                else
                {
                    pstmt.setString(5, this.get(i).getType());
                }
                if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getOperator());
                }
                if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
                {
                    pstmt.setDate(7, null);
                }
                else
                {
                    pstmt.setDate(7, Date.valueOf(this.get(i).getMakeDate()));
                }
                if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
                {
                    pstmt.setString(8, null);
                }
                else
                {
                    pstmt.setString(8, this.get(i).getMakeTime());
                }
                if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
                {
                    pstmt.setDate(9, null);
                }
                else
                {
                    pstmt.setDate(9, Date.valueOf(this.get(i).getModifyDate()));
                }
                if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
                {
                    pstmt.setString(10, null);
                }
                else
                {
                    pstmt.setString(10, this.get(i).getModifyTime());
                }
                if (this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null"))
                {
                    pstmt.setString(11, null);
                }
                else
                {
                    pstmt.setString(11, this.get(i).getStandbyflag1());
                }
                if (this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null"))
                {
                    pstmt.setString(12, null);
                }
                else
                {
                    pstmt.setString(12, this.get(i).getStandbyflag2());
                }
                pstmt.setDouble(13, this.get(i).getStandbyflag3());
                pstmt.setDouble(14, this.get(i).getStandbyflag4());
                pstmt.setDouble(15, this.get(i).getStandbyflag5());
                pstmt.setDouble(16, this.get(i).getStandbyflag6());
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
            tError.moduleName = "PD_CheckRule_LibDBSet";
            tError.functionName = "insert()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_CheckRule_Lib");
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
            pstmt = con.prepareStatement("DELETE FROM PD_CheckRule_Lib WHERE  Code = ?");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getCode() == null || this.get(i).getCode().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getCode());
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
            tError.moduleName = "PD_CheckRule_LibDBSet";
            tError.functionName = "delete()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_CheckRule_Lib");
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
            pstmt = con.prepareStatement("UPDATE PD_CheckRule_Lib SET  Code = ? , Name = ? , Algo = ? , Description = ? , Type = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? WHERE  Code = ?");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getCode() == null || this.get(i).getCode().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getCode());
                }
                if (this.get(i).getName() == null || this.get(i).getName().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getName());
                }
                if (this.get(i).getAlgo() == null || this.get(i).getAlgo().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getAlgo());
                }
                if (this.get(i).getDescription() == null || this.get(i).getDescription().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getDescription());
                }
                if (this.get(i).getType() == null || this.get(i).getType().equals("null"))
                {
                    pstmt.setString(5, null);
                }
                else
                {
                    pstmt.setString(5, this.get(i).getType());
                }
                if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getOperator());
                }
                if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
                {
                    pstmt.setDate(7, null);
                }
                else
                {
                    pstmt.setDate(7, Date.valueOf(this.get(i).getMakeDate()));
                }
                if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
                {
                    pstmt.setString(8, null);
                }
                else
                {
                    pstmt.setString(8, this.get(i).getMakeTime());
                }
                if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
                {
                    pstmt.setDate(9, null);
                }
                else
                {
                    pstmt.setDate(9, Date.valueOf(this.get(i).getModifyDate()));
                }
                if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
                {
                    pstmt.setString(10, null);
                }
                else
                {
                    pstmt.setString(10, this.get(i).getModifyTime());
                }
                if (this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null"))
                {
                    pstmt.setString(11, null);
                }
                else
                {
                    pstmt.setString(11, this.get(i).getStandbyflag1());
                }
                if (this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null"))
                {
                    pstmt.setString(12, null);
                }
                else
                {
                    pstmt.setString(12, this.get(i).getStandbyflag2());
                }
                pstmt.setDouble(13, this.get(i).getStandbyflag3());
                pstmt.setDouble(14, this.get(i).getStandbyflag4());
                pstmt.setDouble(15, this.get(i).getStandbyflag5());
                pstmt.setDouble(16, this.get(i).getStandbyflag6());
                if (this.get(i).getCode() == null || this.get(i).getCode().equals("null"))
                {
                    pstmt.setString(17, null);
                }
                else
                {
                    pstmt.setString(17, this.get(i).getCode());
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
            tError.moduleName = "PD_CheckRule_LibDBSet";
            tError.functionName = "update()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_CheckRule_Lib");
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
