/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.io.StringReader;
import java.sql.*;
import com.sinosoft.lis.schema.PD_LFRiskSchema;
import com.sinosoft.lis.vschema.PD_LFRiskSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: PD_LFRiskDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Company: Sinosoft Co.,LTD</p>
 * @Database: 产品定义平台_PDM
 * @author：Makerx
 * @CreateDate：2009-03-04
 */
public class PD_LFRiskDBSet extends PD_LFRiskSet
{
private static Logger logger = Logger.getLogger(PD_LFRiskDBSet.class);

    // @Field
    private Connection con;
    private DBOper db;
    /**
     * flag = true: 传入Connection
     * flag = false: 不传入Connection
     */
    private boolean mflag = false;

    // @Constructor
    public PD_LFRiskDBSet(Connection cConnection)
    {
        con = cConnection;
        db = new DBOper(con, "PD_LFRisk");
        mflag = true;
    }

    public PD_LFRiskDBSet()
    {
        db = new DBOper("PD_LFRisk");
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
            tError.moduleName = "PD_LFRiskDBSet";
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
            pstmt = con.prepareStatement("INSERT INTO PD_LFRisk VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
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
                if (this.get(i).getRiskVer() == null || this.get(i).getRiskVer().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getRiskVer());
                }
                if (this.get(i).getRiskName() == null || this.get(i).getRiskName().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getRiskName());
                }
                if (this.get(i).getRiskType() == null || this.get(i).getRiskType().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getRiskType());
                }
                if (this.get(i).getRiskPeriod() == null || this.get(i).getRiskPeriod().equals("null"))
                {
                    pstmt.setString(5, null);
                }
                else
                {
                    pstmt.setString(5, this.get(i).getRiskPeriod());
                }
                if (this.get(i).getSubRiskFlag() == null || this.get(i).getSubRiskFlag().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getSubRiskFlag());
                }
                if (this.get(i).getLifeType() == null || this.get(i).getLifeType().equals("null"))
                {
                    pstmt.setString(7, null);
                }
                else
                {
                    pstmt.setString(7, this.get(i).getLifeType());
                }
                if (this.get(i).getRiskDutyType() == null || this.get(i).getRiskDutyType().equals("null"))
                {
                    pstmt.setString(8, null);
                }
                else
                {
                    pstmt.setString(8, this.get(i).getRiskDutyType());
                }
                if (this.get(i).getRiskYearType() == null || this.get(i).getRiskYearType().equals("null"))
                {
                    pstmt.setString(9, null);
                }
                else
                {
                    pstmt.setString(9, this.get(i).getRiskYearType());
                }
                if (this.get(i).getHealthType() == null || this.get(i).getHealthType().equals("null"))
                {
                    pstmt.setString(10, null);
                }
                else
                {
                    pstmt.setString(10, this.get(i).getHealthType());
                }
                if (this.get(i).getAccidentType() == null || this.get(i).getAccidentType().equals("null"))
                {
                    pstmt.setString(11, null);
                }
                else
                {
                    pstmt.setString(11, this.get(i).getAccidentType());
                }
                if (this.get(i).getEndowmentFlag() == null || this.get(i).getEndowmentFlag().equals("null"))
                {
                    pstmt.setString(12, null);
                }
                else
                {
                    pstmt.setString(12, this.get(i).getEndowmentFlag());
                }
                if (this.get(i).getEndowmentType1() == null || this.get(i).getEndowmentType1().equals("null"))
                {
                    pstmt.setString(13, null);
                }
                else
                {
                    pstmt.setString(13, this.get(i).getEndowmentType1());
                }
                if (this.get(i).getEndowmentType2() == null || this.get(i).getEndowmentType2().equals("null"))
                {
                    pstmt.setString(14, null);
                }
                else
                {
                    pstmt.setString(14, this.get(i).getEndowmentType2());
                }
                if (this.get(i).getRiskSaleChnl() == null || this.get(i).getRiskSaleChnl().equals("null"))
                {
                    pstmt.setString(15, null);
                }
                else
                {
                    pstmt.setString(15, this.get(i).getRiskSaleChnl());
                }
                if (this.get(i).getRiskGrpFlag() == null || this.get(i).getRiskGrpFlag().equals("null"))
                {
                    pstmt.setString(16, null);
                }
                else
                {
                    pstmt.setString(16, this.get(i).getRiskGrpFlag());
                }
                if (this.get(i).getChargeSaleChnl() == null || this.get(i).getChargeSaleChnl().equals("null"))
                {
                    pstmt.setString(17, null);
                }
                else
                {
                    pstmt.setString(17, this.get(i).getChargeSaleChnl());
                }
                if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
                {
                    pstmt.setString(18, null);
                }
                else
                {
                    pstmt.setString(18, this.get(i).getOperator());
                }
                if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
                {
                    pstmt.setDate(19, null);
                }
                else
                {
                    pstmt.setDate(19, Date.valueOf(this.get(i).getMakeDate()));
                }
                if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
                {
                    pstmt.setString(20, null);
                }
                else
                {
                    pstmt.setString(20, this.get(i).getMakeTime());
                }
                if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
                {
                    pstmt.setDate(21, null);
                }
                else
                {
                    pstmt.setDate(21, Date.valueOf(this.get(i).getModifyDate()));
                }
                if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
                {
                    pstmt.setString(22, null);
                }
                else
                {
                    pstmt.setString(22, this.get(i).getModifyTime());
                }
                if (this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null"))
                {
                    pstmt.setString(23, null);
                }
                else
                {
                    pstmt.setString(23, this.get(i).getStandbyflag1());
                }
                if (this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null"))
                {
                    pstmt.setString(24, null);
                }
                else
                {
                    pstmt.setString(24, this.get(i).getStandbyflag2());
                }
                pstmt.setDouble(25, this.get(i).getStandbyflag3());
                pstmt.setDouble(26, this.get(i).getStandbyflag4());
                pstmt.setDouble(27, this.get(i).getStandbyflag5());
                pstmt.setDouble(28, this.get(i).getStandbyflag6());
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
            tError.moduleName = "PD_LFRiskDBSet";
            tError.functionName = "insert()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_LFRisk");
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
            pstmt = con.prepareStatement("DELETE FROM PD_LFRisk WHERE  RiskCode = ?");
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
            tError.moduleName = "PD_LFRiskDBSet";
            tError.functionName = "delete()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_LFRisk");
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
            pstmt = con.prepareStatement("UPDATE PD_LFRisk SET  RiskCode = ? , RiskVer = ? , RiskName = ? , RiskType = ? , RiskPeriod = ? , SubRiskFlag = ? , LifeType = ? , RiskDutyType = ? , RiskYearType = ? , HealthType = ? , AccidentType = ? , EndowmentFlag = ? , EndowmentType1 = ? , EndowmentType2 = ? , RiskSaleChnl = ? , RiskGrpFlag = ? , ChargeSaleChnl = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? WHERE  RiskCode = ?");
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
                if (this.get(i).getRiskVer() == null || this.get(i).getRiskVer().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getRiskVer());
                }
                if (this.get(i).getRiskName() == null || this.get(i).getRiskName().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getRiskName());
                }
                if (this.get(i).getRiskType() == null || this.get(i).getRiskType().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getRiskType());
                }
                if (this.get(i).getRiskPeriod() == null || this.get(i).getRiskPeriod().equals("null"))
                {
                    pstmt.setString(5, null);
                }
                else
                {
                    pstmt.setString(5, this.get(i).getRiskPeriod());
                }
                if (this.get(i).getSubRiskFlag() == null || this.get(i).getSubRiskFlag().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getSubRiskFlag());
                }
                if (this.get(i).getLifeType() == null || this.get(i).getLifeType().equals("null"))
                {
                    pstmt.setString(7, null);
                }
                else
                {
                    pstmt.setString(7, this.get(i).getLifeType());
                }
                if (this.get(i).getRiskDutyType() == null || this.get(i).getRiskDutyType().equals("null"))
                {
                    pstmt.setString(8, null);
                }
                else
                {
                    pstmt.setString(8, this.get(i).getRiskDutyType());
                }
                if (this.get(i).getRiskYearType() == null || this.get(i).getRiskYearType().equals("null"))
                {
                    pstmt.setString(9, null);
                }
                else
                {
                    pstmt.setString(9, this.get(i).getRiskYearType());
                }
                if (this.get(i).getHealthType() == null || this.get(i).getHealthType().equals("null"))
                {
                    pstmt.setString(10, null);
                }
                else
                {
                    pstmt.setString(10, this.get(i).getHealthType());
                }
                if (this.get(i).getAccidentType() == null || this.get(i).getAccidentType().equals("null"))
                {
                    pstmt.setString(11, null);
                }
                else
                {
                    pstmt.setString(11, this.get(i).getAccidentType());
                }
                if (this.get(i).getEndowmentFlag() == null || this.get(i).getEndowmentFlag().equals("null"))
                {
                    pstmt.setString(12, null);
                }
                else
                {
                    pstmt.setString(12, this.get(i).getEndowmentFlag());
                }
                if (this.get(i).getEndowmentType1() == null || this.get(i).getEndowmentType1().equals("null"))
                {
                    pstmt.setString(13, null);
                }
                else
                {
                    pstmt.setString(13, this.get(i).getEndowmentType1());
                }
                if (this.get(i).getEndowmentType2() == null || this.get(i).getEndowmentType2().equals("null"))
                {
                    pstmt.setString(14, null);
                }
                else
                {
                    pstmt.setString(14, this.get(i).getEndowmentType2());
                }
                if (this.get(i).getRiskSaleChnl() == null || this.get(i).getRiskSaleChnl().equals("null"))
                {
                    pstmt.setString(15, null);
                }
                else
                {
                    pstmt.setString(15, this.get(i).getRiskSaleChnl());
                }
                if (this.get(i).getRiskGrpFlag() == null || this.get(i).getRiskGrpFlag().equals("null"))
                {
                    pstmt.setString(16, null);
                }
                else
                {
                    pstmt.setString(16, this.get(i).getRiskGrpFlag());
                }
                if (this.get(i).getChargeSaleChnl() == null || this.get(i).getChargeSaleChnl().equals("null"))
                {
                    pstmt.setString(17, null);
                }
                else
                {
                    pstmt.setString(17, this.get(i).getChargeSaleChnl());
                }
                if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
                {
                    pstmt.setString(18, null);
                }
                else
                {
                    pstmt.setString(18, this.get(i).getOperator());
                }
                if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
                {
                    pstmt.setDate(19, null);
                }
                else
                {
                    pstmt.setDate(19, Date.valueOf(this.get(i).getMakeDate()));
                }
                if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
                {
                    pstmt.setString(20, null);
                }
                else
                {
                    pstmt.setString(20, this.get(i).getMakeTime());
                }
                if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
                {
                    pstmt.setDate(21, null);
                }
                else
                {
                    pstmt.setDate(21, Date.valueOf(this.get(i).getModifyDate()));
                }
                if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
                {
                    pstmt.setString(22, null);
                }
                else
                {
                    pstmt.setString(22, this.get(i).getModifyTime());
                }
                if (this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null"))
                {
                    pstmt.setString(23, null);
                }
                else
                {
                    pstmt.setString(23, this.get(i).getStandbyflag1());
                }
                if (this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null"))
                {
                    pstmt.setString(24, null);
                }
                else
                {
                    pstmt.setString(24, this.get(i).getStandbyflag2());
                }
                pstmt.setDouble(25, this.get(i).getStandbyflag3());
                pstmt.setDouble(26, this.get(i).getStandbyflag4());
                pstmt.setDouble(27, this.get(i).getStandbyflag5());
                pstmt.setDouble(28, this.get(i).getStandbyflag6());
                if (this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null"))
                {
                    pstmt.setString(29, null);
                }
                else
                {
                    pstmt.setString(29, this.get(i).getRiskCode());
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
            tError.moduleName = "PD_LFRiskDBSet";
            tError.functionName = "update()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_LFRisk");
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
