/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.io.StringReader;
import java.sql.*;
import com.sinosoft.lis.schema.PD_CheckRule_LibSchema;
import com.sinosoft.lis.vschema.PD_CheckRule_LibSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: PD_CheckRule_LibDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: 产品定义平台_PDM
 * @author：Makerx
 * @CreateDate：2009-03-04
 */
public class PD_CheckRule_LibDB extends PD_CheckRule_LibSchema
{
private static Logger logger = Logger.getLogger(PD_CheckRule_LibDB.class);

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

    // @Constructor
    public PD_CheckRule_LibDB(Connection cConnection)
    {
        con = cConnection;
        db = new DBOper(con, "PD_CheckRule_Lib");
        mflag = true;
    }

    public PD_CheckRule_LibDB()
    {
        con = null;
        db = new DBOper("PD_CheckRule_Lib");
        mflag = false;
    }

    // @Method
    public boolean deleteSQL()
    {
        PD_CheckRule_LibSchema tSchema = this.getSchema();
        if (db.deleteSQL(tSchema))
        {
            return true;
        }
        else
        {
            // @@错误处理
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_CheckRule_LibDB";
            tError.functionName = "deleteSQL";
            tError.errorMessage = "操作失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
    }

    public int getCount()
    {
        PD_CheckRule_LibSchema tSchema = this.getSchema();
        int tCount = db.getCount(tSchema);
        if (tCount < 0)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_CheckRule_LibDB";
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
            pstmt = con.prepareStatement("INSERT INTO PD_CheckRule_Lib VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            if (this.getCode() == null || this.getCode().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getCode());
            }
            if (this.getName() == null || this.getName().equals("null"))
            {
                pstmt.setNull(2, 12);
            }
            else
            {
                pstmt.setString(2, this.getName());
            }
            if (this.getAlgo() == null || this.getAlgo().equals("null"))
            {
                pstmt.setNull(3, 12);
            }
            else
            {
                pstmt.setString(3, this.getAlgo());
            }
            if (this.getDescription() == null || this.getDescription().equals("null"))
            {
                pstmt.setNull(4, 12);
            }
            else
            {
                pstmt.setString(4, this.getDescription());
            }
            if (this.getType() == null || this.getType().equals("null"))
            {
                pstmt.setNull(5, 12);
            }
            else
            {
                pstmt.setString(5, this.getType());
            }
            if (this.getOperator() == null || this.getOperator().equals("null"))
            {
                pstmt.setNull(6, 12);
            }
            else
            {
                pstmt.setString(6, this.getOperator());
            }
            if (this.getMakeDate() == null || this.getMakeDate().equals("null"))
            {
                pstmt.setNull(7, 91);
            }
            else
            {
                pstmt.setDate(7, Date.valueOf(this.getMakeDate()));
            }
            if (this.getMakeTime() == null || this.getMakeTime().equals("null"))
            {
                pstmt.setNull(8, 12);
            }
            else
            {
                pstmt.setString(8, this.getMakeTime());
            }
            if (this.getModifyDate() == null || this.getModifyDate().equals("null"))
            {
                pstmt.setNull(9, 91);
            }
            else
            {
                pstmt.setDate(9, Date.valueOf(this.getModifyDate()));
            }
            if (this.getModifyTime() == null || this.getModifyTime().equals("null"))
            {
                pstmt.setNull(10, 12);
            }
            else
            {
                pstmt.setString(10, this.getModifyTime());
            }
            if (this.getStandbyflag1() == null || this.getStandbyflag1().equals("null"))
            {
                pstmt.setNull(11, 12);
            }
            else
            {
                pstmt.setString(11, this.getStandbyflag1());
            }
            if (this.getStandbyflag2() == null || this.getStandbyflag2().equals("null"))
            {
                pstmt.setNull(12, 12);
            }
            else
            {
                pstmt.setString(12, this.getStandbyflag2());
            }
            pstmt.setDouble(13, this.getStandbyflag3());
            pstmt.setDouble(14, this.getStandbyflag4());
            pstmt.setDouble(15, this.getStandbyflag5());
            pstmt.setDouble(16, this.getStandbyflag6());
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
            tError.moduleName = "PD_CheckRule_LibDB";
            tError.functionName = "insert()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            // 输出出错Sql语句
            SQLString sqlObj = new SQLString("PD_CheckRule_Lib");
            sqlObj.setSQL(1, this.getSchema());
            logger.debug("出错Sql为：" + sqlObj.getSQL());
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
            pstmt = con.prepareStatement("DELETE FROM PD_CheckRule_Lib WHERE  Code = ?");
            if (this.getCode() == null || this.getCode().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getCode());
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
            tError.moduleName = "PD_CheckRule_LibDB";
            tError.functionName = "delete()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            // 输出出错Sql语句
            SQLString sqlObj = new SQLString("PD_CheckRule_Lib");
            sqlObj.setSQL(4, this.getSchema());
            logger.debug("出错Sql为：" + sqlObj.getSQL());
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
            pstmt = con.prepareStatement("UPDATE PD_CheckRule_Lib SET  Code = ? , Name = ? , Algo = ? , Description = ? , Type = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? WHERE  Code = ?");
            if (this.getCode() == null || this.getCode().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getCode());
            }
            if (this.getName() == null || this.getName().equals("null"))
            {
                pstmt.setNull(2, 12);
            }
            else
            {
                pstmt.setString(2, this.getName());
            }
            if (this.getAlgo() == null || this.getAlgo().equals("null"))
            {
                pstmt.setNull(3, 12);
            }
            else
            {
                pstmt.setString(3, this.getAlgo());
            }
            if (this.getDescription() == null || this.getDescription().equals("null"))
            {
                pstmt.setNull(4, 12);
            }
            else
            {
                pstmt.setString(4, this.getDescription());
            }
            if (this.getType() == null || this.getType().equals("null"))
            {
                pstmt.setNull(5, 12);
            }
            else
            {
                pstmt.setString(5, this.getType());
            }
            if (this.getOperator() == null || this.getOperator().equals("null"))
            {
                pstmt.setNull(6, 12);
            }
            else
            {
                pstmt.setString(6, this.getOperator());
            }
            if (this.getMakeDate() == null || this.getMakeDate().equals("null"))
            {
                pstmt.setNull(7, 91);
            }
            else
            {
                pstmt.setDate(7, Date.valueOf(this.getMakeDate()));
            }
            if (this.getMakeTime() == null || this.getMakeTime().equals("null"))
            {
                pstmt.setNull(8, 12);
            }
            else
            {
                pstmt.setString(8, this.getMakeTime());
            }
            if (this.getModifyDate() == null || this.getModifyDate().equals("null"))
            {
                pstmt.setNull(9, 91);
            }
            else
            {
                pstmt.setDate(9, Date.valueOf(this.getModifyDate()));
            }
            if (this.getModifyTime() == null || this.getModifyTime().equals("null"))
            {
                pstmt.setNull(10, 12);
            }
            else
            {
                pstmt.setString(10, this.getModifyTime());
            }
            if (this.getStandbyflag1() == null || this.getStandbyflag1().equals("null"))
            {
                pstmt.setNull(11, 12);
            }
            else
            {
                pstmt.setString(11, this.getStandbyflag1());
            }
            if (this.getStandbyflag2() == null || this.getStandbyflag2().equals("null"))
            {
                pstmt.setNull(12, 12);
            }
            else
            {
                pstmt.setString(12, this.getStandbyflag2());
            }
            pstmt.setDouble(13, this.getStandbyflag3());
            pstmt.setDouble(14, this.getStandbyflag4());
            pstmt.setDouble(15, this.getStandbyflag5());
            pstmt.setDouble(16, this.getStandbyflag6());
            if (this.getCode() == null || this.getCode().equals("null"))
            {
                pstmt.setNull(17, 12);
            }
            else
            {
                pstmt.setString(17, this.getCode());
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
            tError.moduleName = "PD_CheckRule_LibDB";
            tError.functionName = "update()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            // 输出出错Sql语句
            SQLString sqlObj = new SQLString("PD_CheckRule_Lib");
            sqlObj.setSQL(2, this.getSchema());
            logger.debug("出错Sql为：" + sqlObj.getSQL());
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
            pstmt = con.prepareStatement("SELECT * FROM PD_CheckRule_Lib WHERE  Code = ?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            if (this.getCode() == null || this.getCode().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getCode());
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
                    tError.moduleName = "PD_CheckRule_LibDB";
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
            tError.moduleName = "PD_CheckRule_LibDB";
            tError.functionName = "getInfo";
            tError.errorMessage = e.toString();
            this.mErrors.addOneError(tError);
            // 输出出错Sql语句
            SQLString sqlObj = new SQLString("PD_CheckRule_Lib");
            sqlObj.setSQL(6, this.getSchema());
            logger.debug("出错Sql为：" + sqlObj.getSQL());
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
    public PD_CheckRule_LibSet query()
    {
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		PD_CheckRule_LibSet aPD_CheckRule_LibSet = new PD_CheckRule_LibSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("PD_CheckRule_Lib");
			PD_CheckRule_LibSchema aSchema = this.getSchema();
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
				PD_CheckRule_LibSchema s1 = new PD_CheckRule_LibSchema();
				s1.setSchema(rs,i);
				aPD_CheckRule_LibSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_CheckRule_LibDB";
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

		return aPD_CheckRule_LibSet;

    }

    public PD_CheckRule_LibSet executeQuery(String sql)
    {
        Statement stmt = null;
        ResultSet rs = null;
        PD_CheckRule_LibSet aPD_CheckRule_LibSet = new PD_CheckRule_LibSet();
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
                PD_CheckRule_LibSchema s1 = new PD_CheckRule_LibSchema();
                if (!s1.setSchema(rs, i))
                {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "PD_CheckRule_LibDB";
                    tError.functionName = "executeQuery";
                    tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
                    this.mErrors.addOneError(tError);
                }
                aPD_CheckRule_LibSet.add(s1);
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
            logger.debug("##### Error Sql in PD_CheckRule_LibDB at query(): " + sql);
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_CheckRule_LibDB";
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
        return aPD_CheckRule_LibSet;
    }

    public PD_CheckRule_LibSet query(int nStart, int nCount)
    {
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		PD_CheckRule_LibSet aPD_CheckRule_LibSet = new PD_CheckRule_LibSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("PD_CheckRule_Lib");
			PD_CheckRule_LibSchema aSchema = this.getSchema();
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

				PD_CheckRule_LibSchema s1 = new PD_CheckRule_LibSchema();
				s1.setSchema(rs,i);
				aPD_CheckRule_LibSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_CheckRule_LibDB";
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

		return aPD_CheckRule_LibSet;

    }

    public PD_CheckRule_LibSet executeQuery(String sql, int nStart, int nCount)
    {
        Statement stmt = null;
        ResultSet rs = null;
        PD_CheckRule_LibSet aPD_CheckRule_LibSet = new PD_CheckRule_LibSet();
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
                PD_CheckRule_LibSchema s1 = new PD_CheckRule_LibSchema();
                if (!s1.setSchema(rs, i))
                {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "PD_CheckRule_LibDB";
                    tError.functionName = "executeQuery";
                    tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
                    this.mErrors.addOneError(tError);
                }
                aPD_CheckRule_LibSet.add(s1);
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
            logger.debug("##### Error Sql in PD_CheckRule_LibDB at query(): " + sql);
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_CheckRule_LibDB";
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
        return aPD_CheckRule_LibSet;
    }

    public boolean update(String strWherePart)
    {
        Statement stmt = null;
        if (!mflag)
        {
            con = DBConnPool.getConnection();
        }
        SQLString sqlObj = new SQLString("PD_CheckRule_Lib");
        sqlObj.setSQL(2, this.getSchema());
        String sql = "update PD_CheckRule_Lib " + sqlObj.getUpdPart() + " where " + strWherePart;
        try
        {
            stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            int operCount = stmt.executeUpdate(sql);
            if (operCount == 0)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "PD_CheckRule_LibDB";
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
            logger.debug("##### Error Sql in PD_CheckRule_LibDB at update(String strWherePart): " + sql);
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_CheckRule_LibDB";
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
            tError.moduleName = "PD_CheckRule_LibDB";
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
            tError.moduleName = "PD_CheckRule_LibDB";
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
            tError.moduleName = "PD_CheckRule_LibDB";
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
            tError.moduleName = "PD_CheckRule_LibDB";
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
     * @return PD_CheckRule_LibSet
     */
    public PD_CheckRule_LibSet getData()
    {
        int tCount = 0;
        PD_CheckRule_LibSet tPD_CheckRule_LibSet = new PD_CheckRule_LibSet();
        PD_CheckRule_LibSchema tPD_CheckRule_LibSchema = null;
        if (null == mResultSet)
        {
            CError tError = new CError();
            tError.moduleName = "PD_CheckRule_LibDB";
            tError.functionName = "getData";
            tError.errorMessage = "数据集为空，请先准备数据集！";
            this.mErrors.addOneError(tError);
            return null;
        }
        try
        {
            tCount = 1;
            tPD_CheckRule_LibSchema = new PD_CheckRule_LibSchema();
            tPD_CheckRule_LibSchema.setSchema(mResultSet, 1);
            tPD_CheckRule_LibSet.add(tPD_CheckRule_LibSchema);
            //注意mResultSet.next()的作用
            while (tCount++ < SysConst.FETCHCOUNT)
            {
                if (mResultSet.next())
                {
                    tPD_CheckRule_LibSchema = new PD_CheckRule_LibSchema();
                    tPD_CheckRule_LibSchema.setSchema(mResultSet, 1);
                    tPD_CheckRule_LibSet.add(tPD_CheckRule_LibSchema);
                }
            }
        }
        catch (Exception ex)
        {
            CError tError = new CError();
            tError.moduleName = "PD_CheckRule_LibDB";
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
        return tPD_CheckRule_LibSet;
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
                tError.moduleName = "PD_CheckRule_LibDB";
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
            tError.moduleName = "PD_CheckRule_LibDB";
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
                tError.moduleName = "PD_CheckRule_LibDB";
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
            tError.moduleName = "PD_CheckRule_LibDB";
            tError.functionName = "closeData";
            tError.errorMessage = ex3.toString();
            this.mErrors.addOneError(tError);
            flag = false;
        }
        return flag;
    }

	public PD_CheckRule_LibSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_CheckRule_LibSet aPD_CheckRule_LibSet = new PD_CheckRule_LibSet();

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
				PD_CheckRule_LibSchema s1 = new PD_CheckRule_LibSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_CheckRule_LibDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_CheckRule_LibSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_CheckRule_LibDB";
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

		return aPD_CheckRule_LibSet;
	}

	public PD_CheckRule_LibSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_CheckRule_LibSet aPD_CheckRule_LibSet = new PD_CheckRule_LibSet();

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

				PD_CheckRule_LibSchema s1 = new PD_CheckRule_LibSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_CheckRule_LibDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_CheckRule_LibSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_CheckRule_LibDB";
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

		return aPD_CheckRule_LibSet; 
	}

}
