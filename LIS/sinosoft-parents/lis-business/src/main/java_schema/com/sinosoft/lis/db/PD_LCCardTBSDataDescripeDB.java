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
import com.sinosoft.lis.schema.PD_LCCardTBSDataDescripeSchema;
import com.sinosoft.lis.vschema.PD_LCCardTBSDataDescripeSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: PD_LCCardTBSDataDescripeDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: 卡单
 * @author：Makerx
 * @CreateDate：2009-04-21
 */
public class PD_LCCardTBSDataDescripeDB extends PD_LCCardTBSDataDescripeSchema
{
private static Logger logger = Logger.getLogger(PD_LCCardTBSDataDescripeDB.class);

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
    public PD_LCCardTBSDataDescripeDB(Connection cConnection)
    {
        con = cConnection;
        db = new DBOper(con, "PD_LCCardTBSDataDescripe");
        mflag = true;
    }

    public PD_LCCardTBSDataDescripeDB()
    {
        con = null;
        db = new DBOper("PD_LCCardTBSDataDescripe");
        mflag = false;
    }

    // @Method
    public boolean deleteSQL()
    {
        PD_LCCardTBSDataDescripeSchema tSchema = this.getSchema();
        if (db.deleteSQL(tSchema))
        {
            return true;
        }
        else
        {
            // @@错误处理
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
            tError.functionName = "deleteSQL";
            tError.errorMessage = "操作失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
    }

    public int getCount()
    {
        PD_LCCardTBSDataDescripeSchema tSchema = this.getSchema();
        int tCount = db.getCount(tSchema);
        if (tCount < 0)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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
            pstmt = con.prepareStatement("INSERT INTO PD_LCCardTBSDataDescripe VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            if (this.getSetMealName() == null || this.getSetMealName().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getSetMealName());
            }
            if (this.getSetMealCode() == null || this.getSetMealCode().equals("null"))
            {
                pstmt.setNull(2, 12);
            }
            else
            {
                pstmt.setString(2, this.getSetMealCode());
            }
            if (this.getTBSFlag() == null || this.getTBSFlag().equals("null"))
            {
                pstmt.setNull(3, 12);
            }
            else
            {
                pstmt.setString(3, this.getTBSFlag());
            }
            if (this.getTMPColumns() == null || this.getTMPColumns().equals("null"))
            {
                pstmt.setNull(4, 12);
            }
            else
            {
                pstmt.setString(4, this.getTMPColumns());
            }
            if (this.getTableColumsName() == null || this.getTableColumsName().equals("null"))
            {
                pstmt.setNull(5, 12);
            }
            else
            {
                pstmt.setString(5, this.getTableColumsName());
            }
            if (this.getTableColumsCName() == null || this.getTableColumsCName().equals("null"))
            {
                pstmt.setNull(6, 12);
            }
            else
            {
                pstmt.setString(6, this.getTableColumsCName());
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
            if (this.getOperator() == null || this.getOperator().equals("null"))
            {
                pstmt.setNull(11, 12);
            }
            else
            {
                pstmt.setString(11, this.getOperator());
            }
            if (this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null"))
            {
                pstmt.setNull(12, 12);
            }
            else
            {
                pstmt.setString(12, this.getStandbyFlag1());
            }
            if (this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null"))
            {
                pstmt.setNull(13, 12);
            }
            else
            {
                pstmt.setString(13, this.getStandbyFlag2());
            }
            if (this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null"))
            {
                pstmt.setNull(14, 12);
            }
            else
            {
                pstmt.setString(14, this.getStandbyFlag3());
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
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
            tError.functionName = "insert()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            // 输出出错Sql语句
            SQLString sqlObj = new SQLString("PD_LCCardTBSDataDescripe");
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
            pstmt = con.prepareStatement("DELETE FROM PD_LCCardTBSDataDescripe WHERE  SetMealCode = ? AND TBSFlag = ? AND TMPColumns = ?");
            if (this.getSetMealCode() == null || this.getSetMealCode().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getSetMealCode());
            }
            if (this.getTBSFlag() == null || this.getTBSFlag().equals("null"))
            {
                pstmt.setNull(2, 12);
            }
            else
            {
                pstmt.setString(2, this.getTBSFlag());
            }
            if (this.getTMPColumns() == null || this.getTMPColumns().equals("null"))
            {
                pstmt.setNull(3, 12);
            }
            else
            {
                pstmt.setString(3, this.getTMPColumns());
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
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
            tError.functionName = "delete()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            // 输出出错Sql语句
            SQLString sqlObj = new SQLString("PD_LCCardTBSDataDescripe");
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
            pstmt = con.prepareStatement("UPDATE PD_LCCardTBSDataDescripe SET  SetMealName = ? , SetMealCode = ? , TBSFlag = ? , TMPColumns = ? , TableColumsName = ? , TableColumsCName = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Operator = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? WHERE  SetMealCode = ? AND TBSFlag = ? AND TMPColumns = ?");
            if (this.getSetMealName() == null || this.getSetMealName().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getSetMealName());
            }
            if (this.getSetMealCode() == null || this.getSetMealCode().equals("null"))
            {
                pstmt.setNull(2, 12);
            }
            else
            {
                pstmt.setString(2, this.getSetMealCode());
            }
            if (this.getTBSFlag() == null || this.getTBSFlag().equals("null"))
            {
                pstmt.setNull(3, 12);
            }
            else
            {
                pstmt.setString(3, this.getTBSFlag());
            }
            if (this.getTMPColumns() == null || this.getTMPColumns().equals("null"))
            {
                pstmt.setNull(4, 12);
            }
            else
            {
                pstmt.setString(4, this.getTMPColumns());
            }
            if (this.getTableColumsName() == null || this.getTableColumsName().equals("null"))
            {
                pstmt.setNull(5, 12);
            }
            else
            {
                pstmt.setString(5, this.getTableColumsName());
            }
            if (this.getTableColumsCName() == null || this.getTableColumsCName().equals("null"))
            {
                pstmt.setNull(6, 12);
            }
            else
            {
                pstmt.setString(6, this.getTableColumsCName());
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
            if (this.getOperator() == null || this.getOperator().equals("null"))
            {
                pstmt.setNull(11, 12);
            }
            else
            {
                pstmt.setString(11, this.getOperator());
            }
            if (this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null"))
            {
                pstmt.setNull(12, 12);
            }
            else
            {
                pstmt.setString(12, this.getStandbyFlag1());
            }
            if (this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null"))
            {
                pstmt.setNull(13, 12);
            }
            else
            {
                pstmt.setString(13, this.getStandbyFlag2());
            }
            if (this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null"))
            {
                pstmt.setNull(14, 12);
            }
            else
            {
                pstmt.setString(14, this.getStandbyFlag3());
            }
            if (this.getSetMealCode() == null || this.getSetMealCode().equals("null"))
            {
                pstmt.setNull(15, 12);
            }
            else
            {
                pstmt.setString(15, this.getSetMealCode());
            }
            if (this.getTBSFlag() == null || this.getTBSFlag().equals("null"))
            {
                pstmt.setNull(16, 12);
            }
            else
            {
                pstmt.setString(16, this.getTBSFlag());
            }
            if (this.getTMPColumns() == null || this.getTMPColumns().equals("null"))
            {
                pstmt.setNull(17, 12);
            }
            else
            {
                pstmt.setString(17, this.getTMPColumns());
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
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
            tError.functionName = "update()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            // 输出出错Sql语句
            SQLString sqlObj = new SQLString("PD_LCCardTBSDataDescripe");
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
            pstmt = con.prepareStatement("SELECT * FROM PD_LCCardTBSDataDescripe WHERE  SetMealCode = ? AND TBSFlag = ? AND TMPColumns = ?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            if (this.getSetMealCode() == null || this.getSetMealCode().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getSetMealCode());
            }
            if (this.getTBSFlag() == null || this.getTBSFlag().equals("null"))
            {
                pstmt.setNull(2, 12);
            }
            else
            {
                pstmt.setString(2, this.getTBSFlag());
            }
            if (this.getTMPColumns() == null || this.getTMPColumns().equals("null"))
            {
                pstmt.setNull(3, 12);
            }
            else
            {
                pstmt.setString(3, this.getTMPColumns());
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
                    tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
            tError.functionName = "getInfo";
            tError.errorMessage = e.toString();
            this.mErrors.addOneError(tError);
            // 输出出错Sql语句
            SQLString sqlObj = new SQLString("PD_LCCardTBSDataDescripe");
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
    public PD_LCCardTBSDataDescripeSet query()
    {
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		PD_LCCardTBSDataDescripeSet aPD_LCCardTBSDataDescripeSet = new PD_LCCardTBSDataDescripeSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("PD_LCCardTBSDataDescripe");
			PD_LCCardTBSDataDescripeSchema aSchema = this.getSchema();
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
				PD_LCCardTBSDataDescripeSchema s1 = new PD_LCCardTBSDataDescripeSchema();
				s1.setSchema(rs,i);
				aPD_LCCardTBSDataDescripeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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

		return aPD_LCCardTBSDataDescripeSet;

    }

    public PD_LCCardTBSDataDescripeSet executeQuery(String sql)
    {
        Statement stmt = null;
        ResultSet rs = null;
        PD_LCCardTBSDataDescripeSet aPD_LCCardTBSDataDescripeSet = new PD_LCCardTBSDataDescripeSet();
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
                PD_LCCardTBSDataDescripeSchema s1 = new PD_LCCardTBSDataDescripeSchema();
                if (!s1.setSchema(rs, i))
                {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "PD_LCCardTBSDataDescripeDB";
                    tError.functionName = "executeQuery";
                    tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
                    this.mErrors.addOneError(tError);
                }
                aPD_LCCardTBSDataDescripeSet.add(s1);
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
            logger.debug("##### Error Sql in PD_LCCardTBSDataDescripeDB at query(): " + sql);
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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
        return aPD_LCCardTBSDataDescripeSet;
    }

    public PD_LCCardTBSDataDescripeSet query(int nStart, int nCount)
    {
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		PD_LCCardTBSDataDescripeSet aPD_LCCardTBSDataDescripeSet = new PD_LCCardTBSDataDescripeSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("PD_LCCardTBSDataDescripe");
			PD_LCCardTBSDataDescripeSchema aSchema = this.getSchema();
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

				PD_LCCardTBSDataDescripeSchema s1 = new PD_LCCardTBSDataDescripeSchema();
				s1.setSchema(rs,i);
				aPD_LCCardTBSDataDescripeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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

		return aPD_LCCardTBSDataDescripeSet;

    }

    public PD_LCCardTBSDataDescripeSet executeQuery(String sql, int nStart, int nCount)
    {
        Statement stmt = null;
        ResultSet rs = null;
        PD_LCCardTBSDataDescripeSet aPD_LCCardTBSDataDescripeSet = new PD_LCCardTBSDataDescripeSet();
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
                PD_LCCardTBSDataDescripeSchema s1 = new PD_LCCardTBSDataDescripeSchema();
                if (!s1.setSchema(rs, i))
                {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "PD_LCCardTBSDataDescripeDB";
                    tError.functionName = "executeQuery";
                    tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
                    this.mErrors.addOneError(tError);
                }
                aPD_LCCardTBSDataDescripeSet.add(s1);
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
            logger.debug("##### Error Sql in PD_LCCardTBSDataDescripeDB at query(): " + sql);
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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
        return aPD_LCCardTBSDataDescripeSet;
    }

    public boolean update(String strWherePart)
    {
        Statement stmt = null;
        if (!mflag)
        {
            con = DBConnPool.getConnection();
        }
        SQLString sqlObj = new SQLString("PD_LCCardTBSDataDescripe");
        sqlObj.setSQL(2, this.getSchema());
        String sql = "update PD_LCCardTBSDataDescripe " + sqlObj.getUpdPart() + " where " + strWherePart;
        try
        {
            stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            int operCount = stmt.executeUpdate(sql);
            if (operCount == 0)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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
            logger.debug("##### Error Sql in PD_LCCardTBSDataDescripeDB at update(String strWherePart): " + sql);
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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
     * @return PD_LCCardTBSDataDescripeSet
     */
    public PD_LCCardTBSDataDescripeSet getData()
    {
        int tCount = 0;
        PD_LCCardTBSDataDescripeSet tPD_LCCardTBSDataDescripeSet = new PD_LCCardTBSDataDescripeSet();
        PD_LCCardTBSDataDescripeSchema tPD_LCCardTBSDataDescripeSchema = null;
        if (null == mResultSet)
        {
            CError tError = new CError();
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
            tError.functionName = "getData";
            tError.errorMessage = "数据集为空，请先准备数据集！";
            this.mErrors.addOneError(tError);
            return null;
        }
        try
        {
            tCount = 1;
            tPD_LCCardTBSDataDescripeSchema = new PD_LCCardTBSDataDescripeSchema();
            tPD_LCCardTBSDataDescripeSchema.setSchema(mResultSet, 1);
            tPD_LCCardTBSDataDescripeSet.add(tPD_LCCardTBSDataDescripeSchema);
            //注意mResultSet.next()的作用
            while (tCount++ < SysConst.FETCHCOUNT)
            {
                if (mResultSet.next())
                {
                    tPD_LCCardTBSDataDescripeSchema = new PD_LCCardTBSDataDescripeSchema();
                    tPD_LCCardTBSDataDescripeSchema.setSchema(mResultSet, 1);
                    tPD_LCCardTBSDataDescripeSet.add(tPD_LCCardTBSDataDescripeSchema);
                }
            }
        }
        catch (Exception ex)
        {
            CError tError = new CError();
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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
        return tPD_LCCardTBSDataDescripeSet;
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
                tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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
                tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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
            tError.moduleName = "PD_LCCardTBSDataDescripeDB";
            tError.functionName = "closeData";
            tError.errorMessage = ex3.toString();
            this.mErrors.addOneError(tError);
            flag = false;
        }
        return flag;
    }

	public PD_LCCardTBSDataDescripeSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_LCCardTBSDataDescripeSet aPD_LCCardTBSDataDescripeSet = new PD_LCCardTBSDataDescripeSet();

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
				PD_LCCardTBSDataDescripeSchema s1 = new PD_LCCardTBSDataDescripeSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LCCardTBSDataDescripeDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LCCardTBSDataDescripeSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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

		return aPD_LCCardTBSDataDescripeSet;
	}

	public PD_LCCardTBSDataDescripeSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_LCCardTBSDataDescripeSet aPD_LCCardTBSDataDescripeSet = new PD_LCCardTBSDataDescripeSet();

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

				PD_LCCardTBSDataDescripeSchema s1 = new PD_LCCardTBSDataDescripeSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LCCardTBSDataDescripeDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LCCardTBSDataDescripeSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LCCardTBSDataDescripeDB";
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

		return aPD_LCCardTBSDataDescripeSet; 
	}

}
