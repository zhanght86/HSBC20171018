/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.io.StringReader;
import java.sql.*;
import com.sinosoft.lis.schema.LCGRPCONTRENEWTRACESchema;
import com.sinosoft.lis.vschema.LCGRPCONTRENEWTRACESet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: LCGRPCONTRENEWTRACEDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Company: Sinosoft Co.,LTD</p>
 * @Database: temp
 * @author：Makerx
 * @CreateDate：2011-09-13
 */
public class LCGRPCONTRENEWTRACEDBSet extends LCGRPCONTRENEWTRACESet
{
    // @Field
    private Connection con;
    private DBOper db;
    /**
     * flag = true: 传入Connection
     * flag = false: 不传入Connection
     */
    private boolean mflag = false;

    // @Constructor
    public LCGRPCONTRENEWTRACEDBSet(Connection cConnection)
    {
        con = cConnection;
        db = new DBOper(con, "LCGRPCONTRENEWTRACE");
        mflag = true;
    }

    public LCGRPCONTRENEWTRACEDBSet()
    {
        db = new DBOper("LCGRPCONTRENEWTRACE");
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
            tError.moduleName = "LCGRPCONTRENEWTRACEDBSet";
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
            pstmt = con.prepareStatement("INSERT INTO LCGRPCONTRENEWTRACE VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getGRPCONTNO() == null || this.get(i).getGRPCONTNO().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getGRPCONTNO());
                }
                if (this.get(i).getPROPOSALGRPCONTNO() == null || this.get(i).getPROPOSALGRPCONTNO().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getPROPOSALGRPCONTNO());
                }
                if (this.get(i).getPRTNO() == null || this.get(i).getPRTNO().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getPRTNO());
                }
                if (this.get(i).getOLDGRPCONTNO() == null || this.get(i).getOLDGRPCONTNO().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getOLDGRPCONTNO());
                }
                pstmt.setDouble(5, this.get(i).getRENEWTIMES());
                if (this.get(i).getSTANDBYFLAG1() == null || this.get(i).getSTANDBYFLAG1().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getSTANDBYFLAG1());
                }
                if (this.get(i).getSTANDBYFLAG2() == null || this.get(i).getSTANDBYFLAG2().equals("null"))
                {
                    pstmt.setString(7, null);
                }
                else
                {
                    pstmt.setString(7, this.get(i).getSTANDBYFLAG2());
                }
                if (this.get(i).getSTANDBYFLAG3() == null || this.get(i).getSTANDBYFLAG3().equals("null"))
                {
                    pstmt.setString(8, null);
                }
                else
                {
                    pstmt.setString(8, this.get(i).getSTANDBYFLAG3());
                }
                if (this.get(i).getOPERATOR() == null || this.get(i).getOPERATOR().equals("null"))
                {
                    pstmt.setString(9, null);
                }
                else
                {
                    pstmt.setString(9, this.get(i).getOPERATOR());
                }
                if (this.get(i).getMAKEDATE() == null || this.get(i).getMAKEDATE().equals("null"))
                {
                    pstmt.setDate(10, null);
                }
                else
                {
                    pstmt.setDate(10, Date.valueOf(this.get(i).getMAKEDATE()));
                }
                if (this.get(i).getMAKETIME() == null || this.get(i).getMAKETIME().equals("null"))
                {
                    pstmt.setString(11, null);
                }
                else
                {
                    pstmt.setString(11, this.get(i).getMAKETIME());
                }
                if (this.get(i).getMODIFYDATE() == null || this.get(i).getMODIFYDATE().equals("null"))
                {
                    pstmt.setDate(12, null);
                }
                else
                {
                    pstmt.setDate(12, Date.valueOf(this.get(i).getMODIFYDATE()));
                }
                if (this.get(i).getMODIFYTIME() == null || this.get(i).getMODIFYTIME().equals("null"))
                {
                    pstmt.setString(13, null);
                }
                else
                {
                    pstmt.setString(13, this.get(i).getMODIFYTIME());
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
            tError.moduleName = "LCGRPCONTRENEWTRACEDBSet";
            tError.functionName = "insert()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("LCGRPCONTRENEWTRACE");
            for (int i = 1; i <= tCount; i++)
            {
                // 输出出错Sql语句
                sqlObj.setSQL(1, this.get(i));
                System.out.println("出错Sql为：" + sqlObj.getSQL());
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
            pstmt = con.prepareStatement("DELETE FROM LCGRPCONTRENEWTRACE WHERE  GRPCONTNO = ?");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getGRPCONTNO() == null || this.get(i).getGRPCONTNO().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getGRPCONTNO());
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
            tError.moduleName = "LCGRPCONTRENEWTRACEDBSet";
            tError.functionName = "delete()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("LCGRPCONTRENEWTRACE");
            for (int i = 1; i <= tCount; i++)
            {
                // 输出出错Sql语句
                sqlObj.setSQL(4, this.get(i));
                System.out.println("出错Sql为：" + sqlObj.getSQL());
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
            pstmt = con.prepareStatement("UPDATE LCGRPCONTRENEWTRACE SET  GRPCONTNO = ? , PROPOSALGRPCONTNO = ? , PRTNO = ? , OLDGRPCONTNO = ? , RENEWTIMES = ? , STANDBYFLAG1 = ? , STANDBYFLAG2 = ? , STANDBYFLAG3 = ? , OPERATOR = ? , MAKEDATE = ? , MAKETIME = ? , MODIFYDATE = ? , MODIFYTIME = ? WHERE  GRPCONTNO = ?");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getGRPCONTNO() == null || this.get(i).getGRPCONTNO().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getGRPCONTNO());
                }
                if (this.get(i).getPROPOSALGRPCONTNO() == null || this.get(i).getPROPOSALGRPCONTNO().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getPROPOSALGRPCONTNO());
                }
                if (this.get(i).getPRTNO() == null || this.get(i).getPRTNO().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getPRTNO());
                }
                if (this.get(i).getOLDGRPCONTNO() == null || this.get(i).getOLDGRPCONTNO().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getOLDGRPCONTNO());
                }
                pstmt.setDouble(5, this.get(i).getRENEWTIMES());
                if (this.get(i).getSTANDBYFLAG1() == null || this.get(i).getSTANDBYFLAG1().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getSTANDBYFLAG1());
                }
                if (this.get(i).getSTANDBYFLAG2() == null || this.get(i).getSTANDBYFLAG2().equals("null"))
                {
                    pstmt.setString(7, null);
                }
                else
                {
                    pstmt.setString(7, this.get(i).getSTANDBYFLAG2());
                }
                if (this.get(i).getSTANDBYFLAG3() == null || this.get(i).getSTANDBYFLAG3().equals("null"))
                {
                    pstmt.setString(8, null);
                }
                else
                {
                    pstmt.setString(8, this.get(i).getSTANDBYFLAG3());
                }
                if (this.get(i).getOPERATOR() == null || this.get(i).getOPERATOR().equals("null"))
                {
                    pstmt.setString(9, null);
                }
                else
                {
                    pstmt.setString(9, this.get(i).getOPERATOR());
                }
                if (this.get(i).getMAKEDATE() == null || this.get(i).getMAKEDATE().equals("null"))
                {
                    pstmt.setDate(10, null);
                }
                else
                {
                    pstmt.setDate(10, Date.valueOf(this.get(i).getMAKEDATE()));
                }
                if (this.get(i).getMAKETIME() == null || this.get(i).getMAKETIME().equals("null"))
                {
                    pstmt.setString(11, null);
                }
                else
                {
                    pstmt.setString(11, this.get(i).getMAKETIME());
                }
                if (this.get(i).getMODIFYDATE() == null || this.get(i).getMODIFYDATE().equals("null"))
                {
                    pstmt.setDate(12, null);
                }
                else
                {
                    pstmt.setDate(12, Date.valueOf(this.get(i).getMODIFYDATE()));
                }
                if (this.get(i).getMODIFYTIME() == null || this.get(i).getMODIFYTIME().equals("null"))
                {
                    pstmt.setString(13, null);
                }
                else
                {
                    pstmt.setString(13, this.get(i).getMODIFYTIME());
                }
                if (this.get(i).getGRPCONTNO() == null || this.get(i).getGRPCONTNO().equals("null"))
                {
                    pstmt.setString(14, null);
                }
                else
                {
                    pstmt.setString(14, this.get(i).getGRPCONTNO());
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
            tError.moduleName = "LCGRPCONTRENEWTRACEDBSet";
            tError.functionName = "update()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("LCGRPCONTRENEWTRACE");
            for (int i = 1; i <= tCount; i++)
            {
                // 输出出错Sql语句
                sqlObj.setSQL(2, this.get(i));
                System.out.println("出错Sql为：" + sqlObj.getSQL());
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
