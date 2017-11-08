/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LCGRPCONTRENEWTRACESchema;
import com.sinosoft.lis.vschema.LCGRPCONTRENEWTRACESet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: LCGRPCONTRENEWTRACEDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: temp
 * @author：Makerx
 * @CreateDate：2011-09-13
 */
public class LCGRPCONTRENEWTRACEDB extends LCGRPCONTRENEWTRACESchema
{
    // @Field
    private Connection con;
    private DBOper db;
    /**
     * flag = true: 传入Connection
     * flag = false: 不传入Connection
     **/
    private boolean mflag = false;
    /**
     * 为批量操作而准备的语句和游标对象
     */
    private ResultSet mResultSet = null;
    private Statement mStatement = null;
    private Logger m_log = Logger.getLogger( LCGRPCONTRENEWTRACEDB.class);

    // @Constructor
    public LCGRPCONTRENEWTRACEDB(Connection cConnection)
    {
        con = cConnection;
        db = new DBOper(con, "LCGRPCONTRENEWTRACE");
        mflag = true;
    }

    public LCGRPCONTRENEWTRACEDB()
    {
        con = null;
        db = new DBOper("LCGRPCONTRENEWTRACE");
        mflag = false;
    }

    // @Method
    public boolean deleteSQL()
    {
        LCGRPCONTRENEWTRACESchema tSchema = this.getSchema();
        if (db.deleteSQL(tSchema))
        {
            return true;
        }
        else
        {
            // @@错误处理
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "deleteSQL";
            tError.errorMessage = "操作失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
    }

    public int getCount()
    {
        LCGRPCONTRENEWTRACESchema tSchema = this.getSchema();
        int tCount = db.getCount(tSchema);
        if (tCount < 0)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "getCount";
            tError.errorMessage = "操作失败!";
            this.mErrors.addOneError(tError);
            return -1;
        }
        return tCount;
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
            pstmt = con.prepareStatement("INSERT INTO LCGRPCONTRENEWTRACE VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            if (this.getGRPCONTNO() == null || this.getGRPCONTNO().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getGRPCONTNO());
            }
            if (this.getPROPOSALGRPCONTNO() == null || this.getPROPOSALGRPCONTNO().equals("null"))
            {
                pstmt.setNull(2, 12);
            }
            else
            {
                pstmt.setString(2, this.getPROPOSALGRPCONTNO());
            }
            if (this.getPRTNO() == null || this.getPRTNO().equals("null"))
            {
                pstmt.setNull(3, 12);
            }
            else
            {
                pstmt.setString(3, this.getPRTNO());
            }
            if (this.getOLDGRPCONTNO() == null || this.getOLDGRPCONTNO().equals("null"))
            {
                pstmt.setNull(4, 12);
            }
            else
            {
                pstmt.setString(4, this.getOLDGRPCONTNO());
            }
            pstmt.setDouble(5, this.getRENEWTIMES());
            if (this.getSTANDBYFLAG1() == null || this.getSTANDBYFLAG1().equals("null"))
            {
                pstmt.setNull(6, 12);
            }
            else
            {
                pstmt.setString(6, this.getSTANDBYFLAG1());
            }
            if (this.getSTANDBYFLAG2() == null || this.getSTANDBYFLAG2().equals("null"))
            {
                pstmt.setNull(7, 12);
            }
            else
            {
                pstmt.setString(7, this.getSTANDBYFLAG2());
            }
            if (this.getSTANDBYFLAG3() == null || this.getSTANDBYFLAG3().equals("null"))
            {
                pstmt.setNull(8, 12);
            }
            else
            {
                pstmt.setString(8, this.getSTANDBYFLAG3());
            }
            if (this.getOPERATOR() == null || this.getOPERATOR().equals("null"))
            {
                pstmt.setNull(9, 12);
            }
            else
            {
                pstmt.setString(9, this.getOPERATOR());
            }
            if (this.getMAKEDATE() == null || this.getMAKEDATE().equals("null"))
            {
                pstmt.setNull(10, 91);
            }
            else
            {
                pstmt.setDate(10, Date.valueOf(this.getMAKEDATE()));
            }
            if (this.getMAKETIME() == null || this.getMAKETIME().equals("null"))
            {
                pstmt.setNull(11, 12);
            }
            else
            {
                pstmt.setString(11, this.getMAKETIME());
            }
            if (this.getMODIFYDATE() == null || this.getMODIFYDATE().equals("null"))
            {
                pstmt.setNull(12, 91);
            }
            else
            {
                pstmt.setDate(12, Date.valueOf(this.getMODIFYDATE()));
            }
            if (this.getMODIFYTIME() == null || this.getMODIFYTIME().equals("null"))
            {
                pstmt.setNull(13, 12);
            }
            else
            {
                pstmt.setString(13, this.getMODIFYTIME());
            }
            // execute sql
            pstmt.executeUpdate();
            pstmt.close();
            if (!mflag)
            {
                con.close();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            // @@错误处理
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "insert()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            // 输出出错Sql语句
            SQLString sqlObj = new SQLString("LCGRPCONTRENEWTRACE");
            sqlObj.setSQL(1, this.getSchema());
            System.out.println("出错Sql为：" + sqlObj.getSQL());
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
                try
                {
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
     **/
    public boolean delete()
    {
        PreparedStatement pstmt = null;
        if (!mflag)
        {
            con = DBConnPool.getConnection();
        }
        try
        {
            pstmt = con.prepareStatement("DELETE FROM LCGRPCONTRENEWTRACE WHERE  GRPCONTNO = ?");
            if (this.getGRPCONTNO() == null || this.getGRPCONTNO().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getGRPCONTNO());
            }
            pstmt.executeUpdate();
            pstmt.close();
            if (!mflag)
            {
                con.close();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            // @@错误处理
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "delete()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            // 输出出错Sql语句
            SQLString sqlObj = new SQLString("LCGRPCONTRENEWTRACE");
            sqlObj.setSQL(4, this.getSchema());
            System.out.println("出错Sql为：" + sqlObj.getSQL());
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
                try
                {
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
                    con.close();
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
            pstmt = con.prepareStatement("UPDATE LCGRPCONTRENEWTRACE SET  GRPCONTNO = ? , PROPOSALGRPCONTNO = ? , PRTNO = ? , OLDGRPCONTNO = ? , RENEWTIMES = ? , STANDBYFLAG1 = ? , STANDBYFLAG2 = ? , STANDBYFLAG3 = ? , OPERATOR = ? , MAKEDATE = ? , MAKETIME = ? , MODIFYDATE = ? , MODIFYTIME = ? WHERE  GRPCONTNO = ?");
            if (this.getGRPCONTNO() == null || this.getGRPCONTNO().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getGRPCONTNO());
            }
            if (this.getPROPOSALGRPCONTNO() == null || this.getPROPOSALGRPCONTNO().equals("null"))
            {
                pstmt.setNull(2, 12);
            }
            else
            {
                pstmt.setString(2, this.getPROPOSALGRPCONTNO());
            }
            if (this.getPRTNO() == null || this.getPRTNO().equals("null"))
            {
                pstmt.setNull(3, 12);
            }
            else
            {
                pstmt.setString(3, this.getPRTNO());
            }
            if (this.getOLDGRPCONTNO() == null || this.getOLDGRPCONTNO().equals("null"))
            {
                pstmt.setNull(4, 12);
            }
            else
            {
                pstmt.setString(4, this.getOLDGRPCONTNO());
            }
            pstmt.setDouble(5, this.getRENEWTIMES());
            if (this.getSTANDBYFLAG1() == null || this.getSTANDBYFLAG1().equals("null"))
            {
                pstmt.setNull(6, 12);
            }
            else
            {
                pstmt.setString(6, this.getSTANDBYFLAG1());
            }
            if (this.getSTANDBYFLAG2() == null || this.getSTANDBYFLAG2().equals("null"))
            {
                pstmt.setNull(7, 12);
            }
            else
            {
                pstmt.setString(7, this.getSTANDBYFLAG2());
            }
            if (this.getSTANDBYFLAG3() == null || this.getSTANDBYFLAG3().equals("null"))
            {
                pstmt.setNull(8, 12);
            }
            else
            {
                pstmt.setString(8, this.getSTANDBYFLAG3());
            }
            if (this.getOPERATOR() == null || this.getOPERATOR().equals("null"))
            {
                pstmt.setNull(9, 12);
            }
            else
            {
                pstmt.setString(9, this.getOPERATOR());
            }
            if (this.getMAKEDATE() == null || this.getMAKEDATE().equals("null"))
            {
                pstmt.setNull(10, 91);
            }
            else
            {
                pstmt.setDate(10, Date.valueOf(this.getMAKEDATE()));
            }
            if (this.getMAKETIME() == null || this.getMAKETIME().equals("null"))
            {
                pstmt.setNull(11, 12);
            }
            else
            {
                pstmt.setString(11, this.getMAKETIME());
            }
            if (this.getMODIFYDATE() == null || this.getMODIFYDATE().equals("null"))
            {
                pstmt.setNull(12, 91);
            }
            else
            {
                pstmt.setDate(12, Date.valueOf(this.getMODIFYDATE()));
            }
            if (this.getMODIFYTIME() == null || this.getMODIFYTIME().equals("null"))
            {
                pstmt.setNull(13, 12);
            }
            else
            {
                pstmt.setString(13, this.getMODIFYTIME());
            }
            if (this.getGRPCONTNO() == null || this.getGRPCONTNO().equals("null"))
            {
                pstmt.setNull(14, 12);
            }
            else
            {
                pstmt.setString(14, this.getGRPCONTNO());
            }
            pstmt.executeUpdate();
            pstmt.close();
            if (!mflag)
            {
                con.close();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            // @@错误处理
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "update()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            // 输出出错Sql语句
            SQLString sqlObj = new SQLString("LCGRPCONTRENEWTRACE");
            sqlObj.setSQL(2, this.getSchema());
            System.out.println("出错Sql为：" + sqlObj.getSQL());
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
                try
                {
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
     * 查询操作
     * 查询条件：主键
     * @return boolean
     */
    public boolean getInfo()
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (!mflag)
        {
            con = DBConnPool.getConnection();
        }
        try
        {
            pstmt = con.prepareStatement("SELECT * FROM LCGRPCONTRENEWTRACE WHERE  GRPCONTNO = ?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            if (this.getGRPCONTNO() == null || this.getGRPCONTNO().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getGRPCONTNO());
            }
            rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next())
            {
                i++;
                if (!this.setSchema(rs, i))
                {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "LCGRPCONTRENEWTRACEDB";
                    tError.functionName = "getInfo";
                    tError.errorMessage = "取数失败!";
                    this.mErrors.addOneError(tError);
                    try
                    {
                        rs.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
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
                        try
                        {
                            con.close();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    return false;
                }
                break;
            }
            try
            {
                rs.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            try
            {
                pstmt.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (i == 0)
            {
                if (!mflag)
                {
                    try
                    {
                        con.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "getInfo";
            tError.errorMessage = e.toString();
            this.mErrors.addOneError(tError);
            // 输出出错Sql语句
            SQLString sqlObj = new SQLString("LCGRPCONTRENEWTRACE");
            sqlObj.setSQL(6, this.getSchema());
            System.out.println("出错Sql为：" + sqlObj.getSQL());
            try
            {
                rs.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            try
            {
                pstmt.close();
            }
            catch (Exception ex1)
            {
                ex1.printStackTrace();
            }
            if (!mflag)
            {
                try
                {
                    con.close();
                }
                catch (Exception et)
                {
                    et.printStackTrace();
                }
            }
            return false;
        }
        finally
        {
            // 断开数据库连接
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
     * 查询操作
     * 查询条件：传入的schema中有值的字段
     * @return boolean
     */
    public LCGRPCONTRENEWTRACESet query()
    {
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LCGRPCONTRENEWTRACESet aLCGRPCONTRENEWTRACESet = new LCGRPCONTRENEWTRACESet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCGRPCONTRENEWTRACE");
			LCGRPCONTRENEWTRACESchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				LCGRPCONTRENEWTRACESchema s1 = new LCGRPCONTRENEWTRACESchema();
				s1.setSchema(rs,i);
				aLCGRPCONTRENEWTRACESet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGRPCONTRENEWTRACEDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLCGRPCONTRENEWTRACESet;

    }

    public LCGRPCONTRENEWTRACESet executeQuery(String sql)
    {
//            if (sql.toLowerCase().indexOf("where") == -1) {
//                m_log.error("传入的SQL没有WHERE条件，请检查程序。");
//                //  记录堆栈信息
//                try {
//                throw new Exception("传入的SQL没有WHERE条件，请检查程序。");
//            } catch (Exception ex) {
//                m_log.error(ex.getMessage(), ex);
//             }
//            if (SysConst.IfNullQueryError == 1) {
//                CError tError = new CError();
//                tError.moduleName = "LCGRPCONTRENEWTRACEDB";
//                tError.functionName = "executeQuery";
//                tError.errorMessage = "传入的SQL没有WHERE条件，请检查程序。";
//                this.mErrors.addOneError(tError);
//                return null;
//                }
//                }
        Statement stmt = null;
        ResultSet rs = null;
        LCGRPCONTRENEWTRACESet aLCGRPCONTRENEWTRACESet = new LCGRPCONTRENEWTRACESet();
        if (!mflag)
        {
            con = DBConnPool.getConnection();
        }
        try
        {
            stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
            int i = 0;
            while (rs.next())
            {
                i++;
//                if(i>SysConst.MaxRecordSize)
//                {
//                    m_log.error("SQL语句返回结果集太大，请换用其他方式");
//                    try {
//                        throw new Exception("SQL语句返回结果集太大，请换用其他方式");
//                    } catch (Exception ex) {
//                    m_log.error(ex.getMessage(), ex);
//                }
//                if (SysConst.IfLargeQueryError == 1)
//                    {
//                    CError tError = new CError();
//                    tError.moduleName = "LCGRPCONTRENEWTRACEDB";
//                    tError.functionName = "executeQuery";
//                    tError.errorMessage = "SQL语句返回结果集太多，请换用其他方式。";
//                    this.mErrors.addOneError(tError);
//                    break;
//                }
//            }
                LCGRPCONTRENEWTRACESchema s1 = new LCGRPCONTRENEWTRACESchema();
                if (!s1.setSchema(rs, i))
                {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "LCGRPCONTRENEWTRACEDB";
                    tError.functionName = "executeQuery";
                    tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
                    this.mErrors.addOneError(tError);
                }
                aLCGRPCONTRENEWTRACESet.add(s1);
            }
            try
            {
                rs.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            try
            {
                stmt.close();
            }
            catch (Exception ex1)
            {
                ex1.printStackTrace();
            }
            if (!mflag)
            {
                con.close();
            }
        }
        catch (Exception e)
        {
            System.out.println("##### Error Sql in LCGRPCONTRENEWTRACEDB at query(): " + sql);
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "query";
            tError.errorMessage = e.toString();
            this.mErrors.addOneError(tError);
            try
            {
                rs.close();
            }
            catch (Exception ex2)
            {
                ex2.printStackTrace();
            }
            try
            {
                stmt.close();
            }
            catch (Exception ex3)
            {
                ex3.printStackTrace();
            }
            if (!mflag)
            {
                try
                {
                    con.close();
                }
                catch (Exception et)
                {
                    et.printStackTrace();
                }
            }
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
        return aLCGRPCONTRENEWTRACESet;
    }

    public LCGRPCONTRENEWTRACESet query(int nStart, int nCount)
    {
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LCGRPCONTRENEWTRACESet aLCGRPCONTRENEWTRACESet = new LCGRPCONTRENEWTRACESet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCGRPCONTRENEWTRACE");
			LCGRPCONTRENEWTRACESchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				LCGRPCONTRENEWTRACESchema s1 = new LCGRPCONTRENEWTRACESchema();
				s1.setSchema(rs,i);
				aLCGRPCONTRENEWTRACESet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGRPCONTRENEWTRACEDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLCGRPCONTRENEWTRACESet;

    }

    public LCGRPCONTRENEWTRACESet executeQuery(String sql, int nStart, int nCount)
    {
        Statement stmt = null;
        ResultSet rs = null;
        LCGRPCONTRENEWTRACESet aLCGRPCONTRENEWTRACESet = new LCGRPCONTRENEWTRACESet();
        if (!mflag)
        {
            con = DBConnPool.getConnection();
        }
        try
        {
            stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            int i = 0;
            while (rs.next())
            {
                i++;
                if (i < nStart)
                {
                    continue;
                }
                if (i >= nStart + nCount)
                {
                    break;
                }
                LCGRPCONTRENEWTRACESchema s1 = new LCGRPCONTRENEWTRACESchema();
                if (!s1.setSchema(rs, i))
                {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "LCGRPCONTRENEWTRACEDB";
                    tError.functionName = "executeQuery";
                    tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
                    this.mErrors.addOneError(tError);
                }
                aLCGRPCONTRENEWTRACESet.add(s1);
            }
            try
            {
                rs.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            try
            {
                stmt.close();
            }
            catch (Exception ex1)
            {
                ex1.printStackTrace();
            }
            if (!mflag)
            {
                con.close();
            }
        }
        catch (Exception e)
        {
            System.out.println("##### Error Sql in LCGRPCONTRENEWTRACEDB at query(): " + sql);
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "query";
            tError.errorMessage = e.toString();
            this.mErrors.addOneError(tError);
            try
            {
                rs.close();
            }
            catch (Exception ex2)
            {
                ex2.printStackTrace();
            }
            try
            {
                stmt.close();
            }
            catch (Exception ex3)
            {
                ex3.printStackTrace();
            }
            if (!mflag)
            {
                try
                {
                    con.close();
                }
                catch (Exception et)
                {
                    et.printStackTrace();
                }
            }
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
        return aLCGRPCONTRENEWTRACESet;
    }

    public boolean update(String strWherePart)
    {
        Statement stmt = null;
        if (!mflag)
        {
            con = DBConnPool.getConnection();
        }
        SQLString sqlObj = new SQLString("LCGRPCONTRENEWTRACE");
        sqlObj.setSQL(2, this.getSchema());
        String sql = "update LCGRPCONTRENEWTRACE " + sqlObj.getUpdPart() + " where " + strWherePart;
        try
        {
            stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            int operCount = stmt.executeUpdate(sql);
            if (operCount == 0)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LCGRPCONTRENEWTRACEDB";
                tError.functionName = "update";
                tError.errorMessage = "更新数据失败!";
                this.mErrors.addOneError(tError);
                if (!mflag)
                {
                    try
                    {
                        con.close();
                    }
                    catch (Exception et)
                    {
                        et.printStackTrace();
                    }
                }
                return false;
            }
            if (!mflag)
            {
                con.close();
            }
        }
        catch (Exception e)
        {
            System.out.println("##### Error Sql in LCGRPCONTRENEWTRACEDB at update(String strWherePart): " + sql);
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "update";
            tError.errorMessage = e.toString();
            this.mErrors.addOneError(tError);
            try
            {
                stmt.close();
            }
            catch (Exception ex1)
            {
                ex1.printStackTrace();
            }
            if (!mflag)
            {
                try
                {
                    con.close();
                }
                catch (Exception et)
                {
                    et.printStackTrace();
                }
            }
            return false;
        }
        finally
        {
            // 断开数据库连接
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
     * 准备数据查询条件
     * @param strSQL String
     * @return boolean
     */
    public boolean prepareData(String strSQL)
    {
        if (mResultSet != null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "prepareData";
            tError.errorMessage = "数据集非空，程序在准备数据集之后，没有关闭！";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (!mflag)
        {
            con = DBConnPool.getConnection();
        }
        try
        {
            mStatement = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            mResultSet = mStatement.executeQuery(StrTool.GBKToUnicode(strSQL));
            if (!mflag)
            {
                con.close();
            }
        }
        catch (Exception e)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "prepareData";
            tError.errorMessage = e.toString();
            this.mErrors.addOneError(tError);
            try
            {
                mResultSet.close();
            }
            catch (Exception ex2)
            {
                ex2.printStackTrace();
            }
            try
            {
                mStatement.close();
            }
            catch (Exception ex3)
            {
                ex3.printStackTrace();
            }
            if (!mflag)
            {
                try
                {
                    con.close();
                }
                catch (Exception et)
                {
                    et.printStackTrace();
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
                    con.close();
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
     * 获取数据集
     * @return boolean
     */
    public boolean hasMoreData()
    {
        boolean flag = true;
        if (null == mResultSet)
        {
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "hasMoreData";
            tError.errorMessage = "数据集为空，请先准备数据集！";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            flag = mResultSet.next();
        }
        catch (Exception ex)
        {
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "hasMoreData";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            try
            {
                mResultSet.close();
                mResultSet = null;
            }
            catch (Exception ex2)
            {
                ex2.printStackTrace();
            }
            try
            {
                mStatement.close();
                mStatement = null;
            }
            catch (Exception ex3)
            {
                ex3.printStackTrace();
            }
            if (!mflag)
            {
                try
                {
                    con.close();
                }
                catch (Exception et)
                {
                    et.printStackTrace();
                }
            }
            return false;
        }
        return flag;
    }

    /**
     * 获取定量数据
     * @return LCGRPCONTRENEWTRACESet
     */
    public LCGRPCONTRENEWTRACESet getData()
    {
        int tCount = 0;
        LCGRPCONTRENEWTRACESet tLCGRPCONTRENEWTRACESet = new LCGRPCONTRENEWTRACESet();
        LCGRPCONTRENEWTRACESchema tLCGRPCONTRENEWTRACESchema = null;
        if (null == mResultSet)
        {
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "getData";
            tError.errorMessage = "数据集为空，请先准备数据集！";
            this.mErrors.addOneError(tError);
            return null;
        }
        try
        {
            tCount = 1;
            tLCGRPCONTRENEWTRACESchema = new LCGRPCONTRENEWTRACESchema();
            tLCGRPCONTRENEWTRACESchema.setSchema(mResultSet, 1);
            tLCGRPCONTRENEWTRACESet.add(tLCGRPCONTRENEWTRACESchema);
            //注意mResultSet.next()的作用
            while (tCount++ < SysConst.FETCHCOUNT)
            {
                if (mResultSet.next())
                {
                    tLCGRPCONTRENEWTRACESchema = new LCGRPCONTRENEWTRACESchema();
                    tLCGRPCONTRENEWTRACESchema.setSchema(mResultSet, 1);
                    tLCGRPCONTRENEWTRACESet.add(tLCGRPCONTRENEWTRACESchema);
                }
            }
        }
        catch (Exception ex)
        {
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "getData";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            try
            {
                mResultSet.close();
                mResultSet = null;
            }
            catch (Exception ex2)
            {
                ex2.printStackTrace();
            }
            try
            {
                mStatement.close();
                mStatement = null;
            }
            catch (Exception ex3)
            {
                ex3.printStackTrace();
            }
            if (!mflag)
            {
                try
                {
                    con.close();
                }
                catch (Exception et)
                {
                    et.printStackTrace();
                }
            }
            return null;
        }
        return tLCGRPCONTRENEWTRACESet;
    }

    /**
     * 关闭数据集
     * @return boolean
     */
    public boolean closeData()
    {
        boolean flag = true;
        try
        {
            if (null == mResultSet)
            {
                CError tError = new CError();
                tError.moduleName = "LCGRPCONTRENEWTRACEDB";
                tError.functionName = "closeData";
                tError.errorMessage = "数据集已经关闭了！";
                this.mErrors.addOneError(tError);
                flag = false;
            }
            else
            {
                mResultSet.close();
                mResultSet = null;
            }
        }
        catch (Exception ex2)
        {
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "closeData";
            tError.errorMessage = ex2.toString();
            this.mErrors.addOneError(tError);
            flag = false;
        }
        try
        {
            if (null == mStatement)
            {
                CError tError = new CError();
                tError.moduleName = "LCGRPCONTRENEWTRACEDB";
                tError.functionName = "closeData";
                tError.errorMessage = "语句已经关闭了！";
                this.mErrors.addOneError(tError);
                flag = false;
            }
            else
            {
                mStatement.close();
                mStatement = null;
            }
        }
        catch (Exception ex3)
        {
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACEDB";
            tError.functionName = "closeData";
            tError.errorMessage = ex3.toString();
            this.mErrors.addOneError(tError);
            flag = false;
        }
        return flag;
    }

	public LCGRPCONTRENEWTRACESet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCGRPCONTRENEWTRACESet aLCGRPCONTRENEWTRACESet = new LCGRPCONTRENEWTRACESet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
				LCGRPCONTRENEWTRACESchema s1 = new LCGRPCONTRENEWTRACESchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGRPCONTRENEWTRACEDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGRPCONTRENEWTRACESet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGRPCONTRENEWTRACEDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch(Exception ex2) {}
			try{ pstmt.close(); } catch(Exception ex3) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e) {}
		}

		return aLCGRPCONTRENEWTRACESet;
	}

	public LCGRPCONTRENEWTRACESet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCGRPCONTRENEWTRACESet aLCGRPCONTRENEWTRACESet = new LCGRPCONTRENEWTRACESet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break; 
				}

				LCGRPCONTRENEWTRACESchema s1 = new LCGRPCONTRENEWTRACESchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGRPCONTRENEWTRACEDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGRPCONTRENEWTRACESet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGRPCONTRENEWTRACEDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e){}
		}

		return aLCGRPCONTRENEWTRACESet; 
	}

}
