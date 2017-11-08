/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.io.StringReader;
import java.sql.*;
import com.sinosoft.lis.schema.PD_BaseFieldSchema;
import com.sinosoft.lis.vschema.PD_BaseFieldSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: PD_BaseFieldDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Company: Sinosoft Co.,LTD</p>
 * @Database: PhysicalDataModel_1
 * @author：Makerx
 * @CreateDate：2009-10-13
 */
public class PD_BaseFieldDBSet extends PD_BaseFieldSet
{
private static Logger logger = Logger.getLogger(PD_BaseFieldDBSet.class);

    // @Field
    private Connection con;
    private DBOper db;
    /**
     * flag = true: 传入Connection
     * flag = false: 不传入Connection
     */
    private boolean mflag = false;

    // @Constructor
    public PD_BaseFieldDBSet(Connection cConnection)
    {
        con = cConnection;
        db = new DBOper(con, "PD_BaseField");
        mflag = true;
    }

    public PD_BaseFieldDBSet()
    {
        db = new DBOper("PD_BaseField");
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
            tError.moduleName = "PD_BaseFieldDBSet";
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
            pstmt = con.prepareStatement("INSERT INTO PD_BaseField VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getTableCode() == null || this.get(i).getTableCode().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getTableCode());
                }
                if (this.get(i).getFieldCode() == null || this.get(i).getFieldCode().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getFieldCode());
                }
                if (this.get(i).getFieldName() == null || this.get(i).getFieldName().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getFieldName());
                }
                if (this.get(i).getFieldType() == null || this.get(i).getFieldType().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getFieldType());
                }
                if (this.get(i).getOfficialDesc() == null || this.get(i).getOfficialDesc().equals("null"))
                {
                    pstmt.setString(5, null);
                }
                else
                {
                    pstmt.setString(5, this.get(i).getOfficialDesc());
                }
                if (this.get(i).getBusiDesc() == null || this.get(i).getBusiDesc().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getBusiDesc());
                }
                if (this.get(i).getIsDisplay() == null || this.get(i).getIsDisplay().equals("null"))
                {
                    pstmt.setString(7, null);
                }
                else
                {
                    pstmt.setString(7, this.get(i).getIsDisplay());
                }
                pstmt.setDouble(8, this.get(i).getDisplayOrder());
                if (this.get(i).getIsPrimary() == null || this.get(i).getIsPrimary().equals("null"))
                {
                    pstmt.setString(9, null);
                }
                else
                {
                    pstmt.setString(9, this.get(i).getIsPrimary());
                }
                if (this.get(i).getSelectCode() == null || this.get(i).getSelectCode().equals("null"))
                {
                    pstmt.setString(10, null);
                }
                else
                {
                    pstmt.setString(10, this.get(i).getSelectCode());
                }
                if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
                {
                    pstmt.setString(11, null);
                }
                else
                {
                    pstmt.setString(11, this.get(i).getOperator());
                }
                if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
                {
                    pstmt.setDate(12, null);
                }
                else
                {
                    pstmt.setDate(12, Date.valueOf(this.get(i).getMakeDate()));
                }
                if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
                {
                    pstmt.setString(13, null);
                }
                else
                {
                    pstmt.setString(13, this.get(i).getMakeTime());
                }
                if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
                {
                    pstmt.setDate(14, null);
                }
                else
                {
                    pstmt.setDate(14, Date.valueOf(this.get(i).getModifyDate()));
                }
                if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
                {
                    pstmt.setString(15, null);
                }
                else
                {
                    pstmt.setString(15, this.get(i).getModifyTime());
                }
                if (this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null"))
                {
                    pstmt.setString(16, null);
                }
                else
                {
                    pstmt.setString(16, this.get(i).getStandbyflag1());
                }
                if (this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null"))
                {
                    pstmt.setString(17, null);
                }
                else
                {
                    pstmt.setString(17, this.get(i).getStandbyflag2());
                }
                pstmt.setDouble(18, this.get(i).getStandbyflag3());
                pstmt.setDouble(19, this.get(i).getStandbyflag4());
                pstmt.setDouble(20, this.get(i).getStandbyflag5());
                pstmt.setDouble(21, this.get(i).getStandbyflag6());
                if (this.get(i).getFieldTypeName() == null || this.get(i).getFieldTypeName().equals("null"))
                {
                    pstmt.setString(22, null);
                }
                else
                {
                    pstmt.setString(22, this.get(i).getFieldTypeName());
                }
                if (this.get(i).getFieldInitValue() == null || this.get(i).getFieldInitValue().equals("null"))
                {
                    pstmt.setString(23, null);
                }
                else
                {
                    pstmt.setString(23, this.get(i).getFieldInitValue());
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
            tError.moduleName = "PD_BaseFieldDBSet";
            tError.functionName = "insert()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_BaseField");
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
            pstmt = con.prepareStatement("DELETE FROM PD_BaseField WHERE  TableCode = ? AND FieldCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getTableCode() == null || this.get(i).getTableCode().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getTableCode());
                }
                if (this.get(i).getFieldCode() == null || this.get(i).getFieldCode().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getFieldCode());
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
            tError.moduleName = "PD_BaseFieldDBSet";
            tError.functionName = "delete()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_BaseField");
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
            pstmt = con.prepareStatement("UPDATE PD_BaseField SET  TableCode = ? , FieldCode = ? , FieldName = ? , FieldType = ? , OfficialDesc = ? , BusiDesc = ? , IsDisplay = ? , DisplayOrder = ? , IsPrimary = ? , SelectCode = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? , FieldTypeName = ? , FieldInitValue = ? WHERE  TableCode = ? AND FieldCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getTableCode() == null || this.get(i).getTableCode().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getTableCode());
                }
                if (this.get(i).getFieldCode() == null || this.get(i).getFieldCode().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getFieldCode());
                }
                if (this.get(i).getFieldName() == null || this.get(i).getFieldName().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getFieldName());
                }
                if (this.get(i).getFieldType() == null || this.get(i).getFieldType().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getFieldType());
                }
                if (this.get(i).getOfficialDesc() == null || this.get(i).getOfficialDesc().equals("null"))
                {
                    pstmt.setString(5, null);
                }
                else
                {
                    pstmt.setString(5, this.get(i).getOfficialDesc());
                }
                if (this.get(i).getBusiDesc() == null || this.get(i).getBusiDesc().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getBusiDesc());
                }
                if (this.get(i).getIsDisplay() == null || this.get(i).getIsDisplay().equals("null"))
                {
                    pstmt.setString(7, null);
                }
                else
                {
                    pstmt.setString(7, this.get(i).getIsDisplay());
                }
                pstmt.setDouble(8, this.get(i).getDisplayOrder());
                if (this.get(i).getIsPrimary() == null || this.get(i).getIsPrimary().equals("null"))
                {
                    pstmt.setString(9, null);
                }
                else
                {
                    pstmt.setString(9, this.get(i).getIsPrimary());
                }
                if (this.get(i).getSelectCode() == null || this.get(i).getSelectCode().equals("null"))
                {
                    pstmt.setString(10, null);
                }
                else
                {
                    pstmt.setString(10, this.get(i).getSelectCode());
                }
                if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
                {
                    pstmt.setString(11, null);
                }
                else
                {
                    pstmt.setString(11, this.get(i).getOperator());
                }
                if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
                {
                    pstmt.setDate(12, null);
                }
                else
                {
                    pstmt.setDate(12, Date.valueOf(this.get(i).getMakeDate()));
                }
                if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
                {
                    pstmt.setString(13, null);
                }
                else
                {
                    pstmt.setString(13, this.get(i).getMakeTime());
                }
                if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
                {
                    pstmt.setDate(14, null);
                }
                else
                {
                    pstmt.setDate(14, Date.valueOf(this.get(i).getModifyDate()));
                }
                if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
                {
                    pstmt.setString(15, null);
                }
                else
                {
                    pstmt.setString(15, this.get(i).getModifyTime());
                }
                if (this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null"))
                {
                    pstmt.setString(16, null);
                }
                else
                {
                    pstmt.setString(16, this.get(i).getStandbyflag1());
                }
                if (this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null"))
                {
                    pstmt.setString(17, null);
                }
                else
                {
                    pstmt.setString(17, this.get(i).getStandbyflag2());
                }
                pstmt.setDouble(18, this.get(i).getStandbyflag3());
                pstmt.setDouble(19, this.get(i).getStandbyflag4());
                pstmt.setDouble(20, this.get(i).getStandbyflag5());
                pstmt.setDouble(21, this.get(i).getStandbyflag6());
                if (this.get(i).getFieldTypeName() == null || this.get(i).getFieldTypeName().equals("null"))
                {
                    pstmt.setString(22, null);
                }
                else
                {
                    pstmt.setString(22, this.get(i).getFieldTypeName());
                }
                if (this.get(i).getFieldInitValue() == null || this.get(i).getFieldInitValue().equals("null"))
                {
                    pstmt.setString(23, null);
                }
                else
                {
                    pstmt.setString(23, this.get(i).getFieldInitValue());
                }
                if (this.get(i).getTableCode() == null || this.get(i).getTableCode().equals("null"))
                {
                    pstmt.setString(24, null);
                }
                else
                {
                    pstmt.setString(24, this.get(i).getTableCode());
                }
                if (this.get(i).getFieldCode() == null || this.get(i).getFieldCode().equals("null"))
                {
                    pstmt.setString(25, null);
                }
                else
                {
                    pstmt.setString(25, this.get(i).getFieldCode());
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
            tError.moduleName = "PD_BaseFieldDBSet";
            tError.functionName = "update()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_BaseField");
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
