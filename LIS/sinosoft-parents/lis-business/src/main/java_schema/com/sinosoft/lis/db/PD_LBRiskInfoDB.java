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
import com.sinosoft.lis.schema.PD_LBRiskInfoSchema;
import com.sinosoft.lis.vschema.PD_LBRiskInfoSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: PD_LBRiskInfoDB </p>
 * <p>Description: DB灞傛暟鎹簱鎿嶄綔绫绘枃浠? </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: PD_LBRiskInfo
 * @author锛歁akerx
 * @CreateDate锛?2009-09-07
 */
public class PD_LBRiskInfoDB extends PD_LBRiskInfoSchema
{
private static Logger logger = Logger.getLogger(PD_LBRiskInfoDB.class);

    // @Field
    private Connection con;
    private DBOper db;
    /**
     * flag = true: 浼犲叆Connection
     * flag = false: 涓嶄紶鍏onnection
     **/
    private boolean mflag = false;
    /**
     * 涓烘壒閲忔搷浣滆?屽噯澶囩殑璇彞鍜屾父鏍囧璞?

     */
    private ResultSet mResultSet = null;
    private Statement mStatement = null;

    // @Constructor
    public PD_LBRiskInfoDB(Connection cConnection)
    {
        con = cConnection;
        db = new DBOper(con, "PD_LBRiskInfo");
        mflag = true;
    }

    public PD_LBRiskInfoDB()
    {
        con = null;
        db = new DBOper("PD_LBRiskInfo");
        mflag = false;
    }

    // @Method
    public boolean deleteSQL()
    {
        PD_LBRiskInfoSchema tSchema = this.getSchema();
        if (db.deleteSQL(tSchema))
        {
            return true;
        }
        else
        {
            // @@閿欒澶勭悊
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDB";
            tError.functionName = "deleteSQL";
            tError.errorMessage = "M0000060026";
            this.mErrors.addOneError(tError);
            return false;
        }
    }

    public int getCount()
    {
        PD_LBRiskInfoSchema tSchema = this.getSchema();
        int tCount = db.getCount(tSchema);
        if (tCount < 0)
        {
            // @@閿欒澶勭悊
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDB";
            tError.functionName = "getCount";
            tError.errorMessage = "M0000060026";
            this.mErrors.addOneError(tError);
            return -1;
        }
        return tCount;
    }

    /**
     * 鏂板鎿嶄綔
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
            pstmt = con.prepareStatement("INSERT INTO PD_LBRiskInfo VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            if (this.getSerialNo() == null || this.getSerialNo().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getSerialNo());
            }
            if (this.getRiskCode() == null || this.getRiskCode().equals("null"))
            {
                pstmt.setNull(2, 12);
            }
            else
            {
                pstmt.setString(2, this.getRiskCode());
            }
            if (this.getMissionID() == null || this.getMissionID().equals("null"))
            {
                pstmt.setNull(3, 12);
            }
            else
            {
                pstmt.setString(3, this.getMissionID());
            }
            if (this.getSubMissionID() == null || this.getSubMissionID().equals("null"))
            {
                pstmt.setNull(4, 12);
            }
            else
            {
                pstmt.setString(4, this.getSubMissionID());
            }
            if (this.getActivityID() == null || this.getActivityID().equals("null"))
            {
                pstmt.setNull(5, 12);
            }
            else
            {
                pstmt.setString(5, this.getActivityID());
            }
            if (this.getTableCode() == null || this.getTableCode().equals("null"))
            {
                pstmt.setNull(6, 12);
            }
            else
            {
                pstmt.setString(6, this.getTableCode());
            }
            if (this.getStandByFlag1() == null || this.getStandByFlag1().equals("null"))
            {
                pstmt.setNull(7, 12);
            }
            else
            {
                pstmt.setString(7, this.getStandByFlag1());
            }
            if (this.getStandByFlag2() == null || this.getStandByFlag2().equals("null"))
            {
                pstmt.setNull(8, 12);
            }
            else
            {
                pstmt.setString(8, this.getStandByFlag2());
            }
            if (this.getStandByFlag3() == null || this.getStandByFlag3().equals("null"))
            {
                pstmt.setNull(9, 12);
            }
            else
            {
                pstmt.setString(9, this.getStandByFlag3());
            }
            if (this.getStandByFlag4() == null || this.getStandByFlag4().equals("null"))
            {
                pstmt.setNull(10, 12);
            }
            else
            {
                pstmt.setString(10, this.getStandByFlag4());
            }
            if (this.getStandByFlag5() == null || this.getStandByFlag5().equals("null"))
            {
                pstmt.setNull(11, 12);
            }
            else
            {
                pstmt.setString(11, this.getStandByFlag5());
            }
            if (this.getStandByFlag6() == null || this.getStandByFlag6().equals("null"))
            {
                pstmt.setNull(12, 12);
            }
            else
            {
                pstmt.setString(12, this.getStandByFlag6());
            }
            if (this.getStandByFlag7() == null || this.getStandByFlag7().equals("null"))
            {
                pstmt.setNull(13, 12);
            }
            else
            {
                pstmt.setString(13, this.getStandByFlag7());
            }
            if (this.getStandByFlag8() == null || this.getStandByFlag8().equals("null"))
            {
                pstmt.setNull(14, 12);
            }
            else
            {
                pstmt.setString(14, this.getStandByFlag8());
            }
            if (this.getStandByFlag9() == null || this.getStandByFlag9().equals("null"))
            {
                pstmt.setNull(15, 12);
            }
            else
            {
                pstmt.setString(15, this.getStandByFlag9());
            }
            if (this.getStandByFlag10() == null || this.getStandByFlag10().equals("null"))
            {
                pstmt.setNull(16, 12);
            }
            else
            {
                pstmt.setString(16, this.getStandByFlag10());
            }
            if (this.getStandByFlag11() == null || this.getStandByFlag11().equals("null"))
            {
                pstmt.setNull(17, 12);
            }
            else
            {
                pstmt.setString(17, this.getStandByFlag11());
            }
            if (this.getStandByFlag12() == null || this.getStandByFlag12().equals("null"))
            {
                pstmt.setNull(18, 12);
            }
            else
            {
                pstmt.setString(18, this.getStandByFlag12());
            }
            if (this.getStandByFlag13() == null || this.getStandByFlag13().equals("null"))
            {
                pstmt.setNull(19, 12);
            }
            else
            {
                pstmt.setString(19, this.getStandByFlag13());
            }
            if (this.getStandByFlag14() == null || this.getStandByFlag14().equals("null"))
            {
                pstmt.setNull(20, 12);
            }
            else
            {
                pstmt.setString(20, this.getStandByFlag14());
            }
            if (this.getStandByFlag15() == null || this.getStandByFlag15().equals("null"))
            {
                pstmt.setNull(21, 12);
            }
            else
            {
                pstmt.setString(21, this.getStandByFlag15());
            }
            if (this.getStandByFlag16() == null || this.getStandByFlag16().equals("null"))
            {
                pstmt.setNull(22, 12);
            }
            else
            {
                pstmt.setString(22, this.getStandByFlag16());
            }
            if (this.getStandByFlag17() == null || this.getStandByFlag17().equals("null"))
            {
                pstmt.setNull(23, 12);
            }
            else
            {
                pstmt.setString(23, this.getStandByFlag17());
            }
            if (this.getStandByFlag18() == null || this.getStandByFlag18().equals("null"))
            {
                pstmt.setNull(24, 12);
            }
            else
            {
                pstmt.setString(24, this.getStandByFlag18());
            }
            if (this.getStandByFlag19() == null || this.getStandByFlag19().equals("null"))
            {
                pstmt.setNull(25, 12);
            }
            else
            {
                pstmt.setString(25, this.getStandByFlag19());
            }
            if (this.getStandByFlag20() == null || this.getStandByFlag20().equals("null"))
            {
                pstmt.setNull(26, 12);
            }
            else
            {
                pstmt.setString(26, this.getStandByFlag20());
            }
            if (this.getStandByFlag21() == null || this.getStandByFlag21().equals("null"))
            {
                pstmt.setNull(27, 12);
            }
            else
            {
                pstmt.setString(27, this.getStandByFlag21());
            }
            if (this.getStandByFlag22() == null || this.getStandByFlag22().equals("null"))
            {
                pstmt.setNull(28, 12);
            }
            else
            {
                pstmt.setString(28, this.getStandByFlag22());
            }
            if (this.getStandByFlag23() == null || this.getStandByFlag23().equals("null"))
            {
                pstmt.setNull(29, 12);
            }
            else
            {
                pstmt.setString(29, this.getStandByFlag23());
            }
            if (this.getStandByFlag24() == null || this.getStandByFlag24().equals("null"))
            {
                pstmt.setNull(30, 12);
            }
            else
            {
                pstmt.setString(30, this.getStandByFlag24());
            }
            if (this.getStandByFlag25() == null || this.getStandByFlag25().equals("null"))
            {
                pstmt.setNull(31, 12);
            }
            else
            {
                pstmt.setString(31, this.getStandByFlag25());
            }
            if (this.getStandByFlag26() == null || this.getStandByFlag26().equals("null"))
            {
                pstmt.setNull(32, 12);
            }
            else
            {
                pstmt.setString(32, this.getStandByFlag26());
            }
            if (this.getStandByFlag27() == null || this.getStandByFlag27().equals("null"))
            {
                pstmt.setNull(33, 12);
            }
            else
            {
                pstmt.setString(33, this.getStandByFlag27());
            }
            if (this.getStandByFlag28() == null || this.getStandByFlag28().equals("null"))
            {
                pstmt.setNull(34, 12);
            }
            else
            {
                pstmt.setString(34, this.getStandByFlag28());
            }
            if (this.getStandByFlag29() == null || this.getStandByFlag29().equals("null"))
            {
                pstmt.setNull(35, 12);
            }
            else
            {
                pstmt.setString(35, this.getStandByFlag29());
            }
            if (this.getStandByFlag30() == null || this.getStandByFlag30().equals("null"))
            {
                pstmt.setNull(36, 12);
            }
            else
            {
                pstmt.setString(36, this.getStandByFlag30());
            }
            if (this.getStandByFlag31() == null || this.getStandByFlag31().equals("null"))
            {
                pstmt.setNull(37, 12);
            }
            else
            {
                pstmt.setString(37, this.getStandByFlag31());
            }
            if (this.getStandByFlag32() == null || this.getStandByFlag32().equals("null"))
            {
                pstmt.setNull(38, 12);
            }
            else
            {
                pstmt.setString(38, this.getStandByFlag32());
            }
            if (this.getStandByFlag33() == null || this.getStandByFlag33().equals("null"))
            {
                pstmt.setNull(39, 12);
            }
            else
            {
                pstmt.setString(39, this.getStandByFlag33());
            }
            if (this.getStandByFlag34() == null || this.getStandByFlag34().equals("null"))
            {
                pstmt.setNull(40, 12);
            }
            else
            {
                pstmt.setString(40, this.getStandByFlag34());
            }
            if (this.getStandByFlag35() == null || this.getStandByFlag35().equals("null"))
            {
                pstmt.setNull(41, 12);
            }
            else
            {
                pstmt.setString(41, this.getStandByFlag35());
            }
            if (this.getStandByFlag36() == null || this.getStandByFlag36().equals("null"))
            {
                pstmt.setNull(42, 12);
            }
            else
            {
                pstmt.setString(42, this.getStandByFlag36());
            }
            if (this.getStandByFlag37() == null || this.getStandByFlag37().equals("null"))
            {
                pstmt.setNull(43, 12);
            }
            else
            {
                pstmt.setString(43, this.getStandByFlag37());
            }
            if (this.getStandByFlag38() == null || this.getStandByFlag38().equals("null"))
            {
                pstmt.setNull(44, 12);
            }
            else
            {
                pstmt.setString(44, this.getStandByFlag38());
            }
            if (this.getStandByFlag39() == null || this.getStandByFlag39().equals("null"))
            {
                pstmt.setNull(45, 12);
            }
            else
            {
                pstmt.setString(45, this.getStandByFlag39());
            }
            if (this.getStandByFlag40() == null || this.getStandByFlag40().equals("null"))
            {
                pstmt.setNull(46, 12);
            }
            else
            {
                pstmt.setString(46, this.getStandByFlag40());
            }
            if (this.getStandByFlag41() == null || this.getStandByFlag41().equals("null"))
            {
                pstmt.setNull(47, 12);
            }
            else
            {
                pstmt.setString(47, this.getStandByFlag41());
            }
            if (this.getStandByFlag42() == null || this.getStandByFlag42().equals("null"))
            {
                pstmt.setNull(48, 12);
            }
            else
            {
                pstmt.setString(48, this.getStandByFlag42());
            }
            if (this.getStandByFlag43() == null || this.getStandByFlag43().equals("null"))
            {
                pstmt.setNull(49, 12);
            }
            else
            {
                pstmt.setString(49, this.getStandByFlag43());
            }
            if (this.getStandByFlag44() == null || this.getStandByFlag44().equals("null"))
            {
                pstmt.setNull(50, 12);
            }
            else
            {
                pstmt.setString(50, this.getStandByFlag44());
            }
            if (this.getStandByFlag45() == null || this.getStandByFlag45().equals("null"))
            {
                pstmt.setNull(51, 12);
            }
            else
            {
                pstmt.setString(51, this.getStandByFlag45());
            }
            if (this.getStandByFlag46() == null || this.getStandByFlag46().equals("null"))
            {
                pstmt.setNull(52, 12);
            }
            else
            {
                pstmt.setString(52, this.getStandByFlag46());
            }
            if (this.getStandByFlag47() == null || this.getStandByFlag47().equals("null"))
            {
                pstmt.setNull(53, 12);
            }
            else
            {
                pstmt.setString(53, this.getStandByFlag47());
            }
            if (this.getStandByFlag48() == null || this.getStandByFlag48().equals("null"))
            {
                pstmt.setNull(54, 12);
            }
            else
            {
                pstmt.setString(54, this.getStandByFlag48());
            }
            if (this.getStandByFlag49() == null || this.getStandByFlag49().equals("null"))
            {
                pstmt.setNull(55, 12);
            }
            else
            {
                pstmt.setString(55, this.getStandByFlag49());
            }
            if (this.getStandByFlag50() == null || this.getStandByFlag50().equals("null"))
            {
                pstmt.setNull(56, 12);
            }
            else
            {
                pstmt.setString(56, this.getStandByFlag50());
            }
            if (this.getLastOperator() == null || this.getLastOperator().equals("null"))
            {
                pstmt.setNull(57, 12);
            }
            else
            {
                pstmt.setString(57, this.getLastOperator());
            }
            if (this.getCreateOperator() == null || this.getCreateOperator().equals("null"))
            {
                pstmt.setNull(58, 12);
            }
            else
            {
                pstmt.setString(58, this.getCreateOperator());
            }
            if (this.getMakeDate() == null || this.getMakeDate().equals("null"))
            {
                pstmt.setNull(59, 91);
            }
            else
            {
                pstmt.setDate(59, Date.valueOf(this.getMakeDate()));
            }
            if (this.getMakeTime() == null || this.getMakeTime().equals("null"))
            {
                pstmt.setNull(60, 12);
            }
            else
            {
                pstmt.setString(60, this.getMakeTime());
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
            // @@閿欒澶勭悊
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDB";
            tError.functionName = "insert()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            // 杈撳嚭鍑洪敊Sql璇彞
            SQLString sqlObj = new SQLString("PD_LBRiskInfo");
            sqlObj.setSQL(1, this.getSchema());
            logger.debug("鍑洪敊Sql涓猴細" + sqlObj.getSQL());
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
     * 鍒犻櫎鎿嶄綔
     * 鍒犻櫎鏉′欢锛氫富閿?

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
            pstmt = con.prepareStatement("DELETE FROM PD_LBRiskInfo WHERE  SerialNo = ? AND TableCode = ?");
            if (this.getSerialNo() == null || this.getSerialNo().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getSerialNo());
            }
            if (this.getTableCode() == null || this.getTableCode().equals("null"))
            {
                pstmt.setNull(2, 12);
            }
            else
            {
                pstmt.setString(2, this.getTableCode());
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
            // @@閿欒澶勭悊
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDB";
            tError.functionName = "delete()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            // 杈撳嚭鍑洪敊Sql璇彞
            SQLString sqlObj = new SQLString("PD_LBRiskInfo");
            sqlObj.setSQL(4, this.getSchema());
            logger.debug("鍑洪敊Sql涓猴細" + sqlObj.getSQL());
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
     * 鏇存柊鎿嶄綔
     * 鏇存柊鏉′欢锛氫富閿?

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
            pstmt = con.prepareStatement("UPDATE PD_LBRiskInfo SET  SerialNo = ? , RiskCode = ? , MissionID = ? , SubMissionID = ? , ActivityID = ? , TableCode = ? , StandByFlag1 = ? , StandByFlag2 = ? , StandByFlag3 = ? , StandByFlag4 = ? , StandByFlag5 = ? , StandByFlag6 = ? , StandByFlag7 = ? , StandByFlag8 = ? , StandByFlag9 = ? , StandByFlag10 = ? , StandByFlag11 = ? , StandByFlag12 = ? , StandByFlag13 = ? , StandByFlag14 = ? , StandByFlag15 = ? , StandByFlag16 = ? , StandByFlag17 = ? , StandByFlag18 = ? , StandByFlag19 = ? , StandByFlag20 = ? , StandByFlag21 = ? , StandByFlag22 = ? , StandByFlag23 = ? , StandByFlag24 = ? , StandByFlag25 = ? , StandByFlag26 = ? , StandByFlag27 = ? , StandByFlag28 = ? , StandByFlag29 = ? , StandByFlag30 = ? , StandByFlag31 = ? , StandByFlag32 = ? , StandByFlag33 = ? , StandByFlag34 = ? , StandByFlag35 = ? , StandByFlag36 = ? , StandByFlag37 = ? , StandByFlag38 = ? , StandByFlag39 = ? , StandByFlag40 = ? , StandByFlag41 = ? , StandByFlag42 = ? , StandByFlag43 = ? , StandByFlag44 = ? , StandByFlag45 = ? , StandByFlag46 = ? , StandByFlag47 = ? , StandByFlag48 = ? , StandByFlag49 = ? , StandByFlag50 = ? , LastOperator = ? , CreateOperator = ? , MakeDate = ? , MakeTime = ? WHERE  SerialNo = ? AND TableCode = ?");
            if (this.getSerialNo() == null || this.getSerialNo().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getSerialNo());
            }
            if (this.getRiskCode() == null || this.getRiskCode().equals("null"))
            {
                pstmt.setNull(2, 12);
            }
            else
            {
                pstmt.setString(2, this.getRiskCode());
            }
            if (this.getMissionID() == null || this.getMissionID().equals("null"))
            {
                pstmt.setNull(3, 12);
            }
            else
            {
                pstmt.setString(3, this.getMissionID());
            }
            if (this.getSubMissionID() == null || this.getSubMissionID().equals("null"))
            {
                pstmt.setNull(4, 12);
            }
            else
            {
                pstmt.setString(4, this.getSubMissionID());
            }
            if (this.getActivityID() == null || this.getActivityID().equals("null"))
            {
                pstmt.setNull(5, 12);
            }
            else
            {
                pstmt.setString(5, this.getActivityID());
            }
            if (this.getTableCode() == null || this.getTableCode().equals("null"))
            {
                pstmt.setNull(6, 12);
            }
            else
            {
                pstmt.setString(6, this.getTableCode());
            }
            if (this.getStandByFlag1() == null || this.getStandByFlag1().equals("null"))
            {
                pstmt.setNull(7, 12);
            }
            else
            {
                pstmt.setString(7, this.getStandByFlag1());
            }
            if (this.getStandByFlag2() == null || this.getStandByFlag2().equals("null"))
            {
                pstmt.setNull(8, 12);
            }
            else
            {
                pstmt.setString(8, this.getStandByFlag2());
            }
            if (this.getStandByFlag3() == null || this.getStandByFlag3().equals("null"))
            {
                pstmt.setNull(9, 12);
            }
            else
            {
                pstmt.setString(9, this.getStandByFlag3());
            }
            if (this.getStandByFlag4() == null || this.getStandByFlag4().equals("null"))
            {
                pstmt.setNull(10, 12);
            }
            else
            {
                pstmt.setString(10, this.getStandByFlag4());
            }
            if (this.getStandByFlag5() == null || this.getStandByFlag5().equals("null"))
            {
                pstmt.setNull(11, 12);
            }
            else
            {
                pstmt.setString(11, this.getStandByFlag5());
            }
            if (this.getStandByFlag6() == null || this.getStandByFlag6().equals("null"))
            {
                pstmt.setNull(12, 12);
            }
            else
            {
                pstmt.setString(12, this.getStandByFlag6());
            }
            if (this.getStandByFlag7() == null || this.getStandByFlag7().equals("null"))
            {
                pstmt.setNull(13, 12);
            }
            else
            {
                pstmt.setString(13, this.getStandByFlag7());
            }
            if (this.getStandByFlag8() == null || this.getStandByFlag8().equals("null"))
            {
                pstmt.setNull(14, 12);
            }
            else
            {
                pstmt.setString(14, this.getStandByFlag8());
            }
            if (this.getStandByFlag9() == null || this.getStandByFlag9().equals("null"))
            {
                pstmt.setNull(15, 12);
            }
            else
            {
                pstmt.setString(15, this.getStandByFlag9());
            }
            if (this.getStandByFlag10() == null || this.getStandByFlag10().equals("null"))
            {
                pstmt.setNull(16, 12);
            }
            else
            {
                pstmt.setString(16, this.getStandByFlag10());
            }
            if (this.getStandByFlag11() == null || this.getStandByFlag11().equals("null"))
            {
                pstmt.setNull(17, 12);
            }
            else
            {
                pstmt.setString(17, this.getStandByFlag11());
            }
            if (this.getStandByFlag12() == null || this.getStandByFlag12().equals("null"))
            {
                pstmt.setNull(18, 12);
            }
            else
            {
                pstmt.setString(18, this.getStandByFlag12());
            }
            if (this.getStandByFlag13() == null || this.getStandByFlag13().equals("null"))
            {
                pstmt.setNull(19, 12);
            }
            else
            {
                pstmt.setString(19, this.getStandByFlag13());
            }
            if (this.getStandByFlag14() == null || this.getStandByFlag14().equals("null"))
            {
                pstmt.setNull(20, 12);
            }
            else
            {
                pstmt.setString(20, this.getStandByFlag14());
            }
            if (this.getStandByFlag15() == null || this.getStandByFlag15().equals("null"))
            {
                pstmt.setNull(21, 12);
            }
            else
            {
                pstmt.setString(21, this.getStandByFlag15());
            }
            if (this.getStandByFlag16() == null || this.getStandByFlag16().equals("null"))
            {
                pstmt.setNull(22, 12);
            }
            else
            {
                pstmt.setString(22, this.getStandByFlag16());
            }
            if (this.getStandByFlag17() == null || this.getStandByFlag17().equals("null"))
            {
                pstmt.setNull(23, 12);
            }
            else
            {
                pstmt.setString(23, this.getStandByFlag17());
            }
            if (this.getStandByFlag18() == null || this.getStandByFlag18().equals("null"))
            {
                pstmt.setNull(24, 12);
            }
            else
            {
                pstmt.setString(24, this.getStandByFlag18());
            }
            if (this.getStandByFlag19() == null || this.getStandByFlag19().equals("null"))
            {
                pstmt.setNull(25, 12);
            }
            else
            {
                pstmt.setString(25, this.getStandByFlag19());
            }
            if (this.getStandByFlag20() == null || this.getStandByFlag20().equals("null"))
            {
                pstmt.setNull(26, 12);
            }
            else
            {
                pstmt.setString(26, this.getStandByFlag20());
            }
            if (this.getStandByFlag21() == null || this.getStandByFlag21().equals("null"))
            {
                pstmt.setNull(27, 12);
            }
            else
            {
                pstmt.setString(27, this.getStandByFlag21());
            }
            if (this.getStandByFlag22() == null || this.getStandByFlag22().equals("null"))
            {
                pstmt.setNull(28, 12);
            }
            else
            {
                pstmt.setString(28, this.getStandByFlag22());
            }
            if (this.getStandByFlag23() == null || this.getStandByFlag23().equals("null"))
            {
                pstmt.setNull(29, 12);
            }
            else
            {
                pstmt.setString(29, this.getStandByFlag23());
            }
            if (this.getStandByFlag24() == null || this.getStandByFlag24().equals("null"))
            {
                pstmt.setNull(30, 12);
            }
            else
            {
                pstmt.setString(30, this.getStandByFlag24());
            }
            if (this.getStandByFlag25() == null || this.getStandByFlag25().equals("null"))
            {
                pstmt.setNull(31, 12);
            }
            else
            {
                pstmt.setString(31, this.getStandByFlag25());
            }
            if (this.getStandByFlag26() == null || this.getStandByFlag26().equals("null"))
            {
                pstmt.setNull(32, 12);
            }
            else
            {
                pstmt.setString(32, this.getStandByFlag26());
            }
            if (this.getStandByFlag27() == null || this.getStandByFlag27().equals("null"))
            {
                pstmt.setNull(33, 12);
            }
            else
            {
                pstmt.setString(33, this.getStandByFlag27());
            }
            if (this.getStandByFlag28() == null || this.getStandByFlag28().equals("null"))
            {
                pstmt.setNull(34, 12);
            }
            else
            {
                pstmt.setString(34, this.getStandByFlag28());
            }
            if (this.getStandByFlag29() == null || this.getStandByFlag29().equals("null"))
            {
                pstmt.setNull(35, 12);
            }
            else
            {
                pstmt.setString(35, this.getStandByFlag29());
            }
            if (this.getStandByFlag30() == null || this.getStandByFlag30().equals("null"))
            {
                pstmt.setNull(36, 12);
            }
            else
            {
                pstmt.setString(36, this.getStandByFlag30());
            }
            if (this.getStandByFlag31() == null || this.getStandByFlag31().equals("null"))
            {
                pstmt.setNull(37, 12);
            }
            else
            {
                pstmt.setString(37, this.getStandByFlag31());
            }
            if (this.getStandByFlag32() == null || this.getStandByFlag32().equals("null"))
            {
                pstmt.setNull(38, 12);
            }
            else
            {
                pstmt.setString(38, this.getStandByFlag32());
            }
            if (this.getStandByFlag33() == null || this.getStandByFlag33().equals("null"))
            {
                pstmt.setNull(39, 12);
            }
            else
            {
                pstmt.setString(39, this.getStandByFlag33());
            }
            if (this.getStandByFlag34() == null || this.getStandByFlag34().equals("null"))
            {
                pstmt.setNull(40, 12);
            }
            else
            {
                pstmt.setString(40, this.getStandByFlag34());
            }
            if (this.getStandByFlag35() == null || this.getStandByFlag35().equals("null"))
            {
                pstmt.setNull(41, 12);
            }
            else
            {
                pstmt.setString(41, this.getStandByFlag35());
            }
            if (this.getStandByFlag36() == null || this.getStandByFlag36().equals("null"))
            {
                pstmt.setNull(42, 12);
            }
            else
            {
                pstmt.setString(42, this.getStandByFlag36());
            }
            if (this.getStandByFlag37() == null || this.getStandByFlag37().equals("null"))
            {
                pstmt.setNull(43, 12);
            }
            else
            {
                pstmt.setString(43, this.getStandByFlag37());
            }
            if (this.getStandByFlag38() == null || this.getStandByFlag38().equals("null"))
            {
                pstmt.setNull(44, 12);
            }
            else
            {
                pstmt.setString(44, this.getStandByFlag38());
            }
            if (this.getStandByFlag39() == null || this.getStandByFlag39().equals("null"))
            {
                pstmt.setNull(45, 12);
            }
            else
            {
                pstmt.setString(45, this.getStandByFlag39());
            }
            if (this.getStandByFlag40() == null || this.getStandByFlag40().equals("null"))
            {
                pstmt.setNull(46, 12);
            }
            else
            {
                pstmt.setString(46, this.getStandByFlag40());
            }
            if (this.getStandByFlag41() == null || this.getStandByFlag41().equals("null"))
            {
                pstmt.setNull(47, 12);
            }
            else
            {
                pstmt.setString(47, this.getStandByFlag41());
            }
            if (this.getStandByFlag42() == null || this.getStandByFlag42().equals("null"))
            {
                pstmt.setNull(48, 12);
            }
            else
            {
                pstmt.setString(48, this.getStandByFlag42());
            }
            if (this.getStandByFlag43() == null || this.getStandByFlag43().equals("null"))
            {
                pstmt.setNull(49, 12);
            }
            else
            {
                pstmt.setString(49, this.getStandByFlag43());
            }
            if (this.getStandByFlag44() == null || this.getStandByFlag44().equals("null"))
            {
                pstmt.setNull(50, 12);
            }
            else
            {
                pstmt.setString(50, this.getStandByFlag44());
            }
            if (this.getStandByFlag45() == null || this.getStandByFlag45().equals("null"))
            {
                pstmt.setNull(51, 12);
            }
            else
            {
                pstmt.setString(51, this.getStandByFlag45());
            }
            if (this.getStandByFlag46() == null || this.getStandByFlag46().equals("null"))
            {
                pstmt.setNull(52, 12);
            }
            else
            {
                pstmt.setString(52, this.getStandByFlag46());
            }
            if (this.getStandByFlag47() == null || this.getStandByFlag47().equals("null"))
            {
                pstmt.setNull(53, 12);
            }
            else
            {
                pstmt.setString(53, this.getStandByFlag47());
            }
            if (this.getStandByFlag48() == null || this.getStandByFlag48().equals("null"))
            {
                pstmt.setNull(54, 12);
            }
            else
            {
                pstmt.setString(54, this.getStandByFlag48());
            }
            if (this.getStandByFlag49() == null || this.getStandByFlag49().equals("null"))
            {
                pstmt.setNull(55, 12);
            }
            else
            {
                pstmt.setString(55, this.getStandByFlag49());
            }
            if (this.getStandByFlag50() == null || this.getStandByFlag50().equals("null"))
            {
                pstmt.setNull(56, 12);
            }
            else
            {
                pstmt.setString(56, this.getStandByFlag50());
            }
            if (this.getLastOperator() == null || this.getLastOperator().equals("null"))
            {
                pstmt.setNull(57, 12);
            }
            else
            {
                pstmt.setString(57, this.getLastOperator());
            }
            if (this.getCreateOperator() == null || this.getCreateOperator().equals("null"))
            {
                pstmt.setNull(58, 12);
            }
            else
            {
                pstmt.setString(58, this.getCreateOperator());
            }
            if (this.getMakeDate() == null || this.getMakeDate().equals("null"))
            {
                pstmt.setNull(59, 91);
            }
            else
            {
                pstmt.setDate(59, Date.valueOf(this.getMakeDate()));
            }
            if (this.getMakeTime() == null || this.getMakeTime().equals("null"))
            {
                pstmt.setNull(60, 12);
            }
            else
            {
                pstmt.setString(60, this.getMakeTime());
            }
            if (this.getSerialNo() == null || this.getSerialNo().equals("null"))
            {
                pstmt.setNull(61, 12);
            }
            else
            {
                pstmt.setString(61, this.getSerialNo());
            }
            if (this.getTableCode() == null || this.getTableCode().equals("null"))
            {
                pstmt.setNull(62, 12);
            }
            else
            {
                pstmt.setString(62, this.getTableCode());
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
            // @@閿欒澶勭悊
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDB";
            tError.functionName = "update()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            // 杈撳嚭鍑洪敊Sql璇彞
            SQLString sqlObj = new SQLString("PD_LBRiskInfo");
            sqlObj.setSQL(2, this.getSchema());
            logger.debug("鍑洪敊Sql涓猴細" + sqlObj.getSQL());
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
     * 鏌ヨ鎿嶄綔
     * 鏌ヨ鏉′欢锛氫富閿?

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
            pstmt = con.prepareStatement("SELECT * FROM PD_LBRiskInfo WHERE  SerialNo = ? AND TableCode = ?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            if (this.getSerialNo() == null || this.getSerialNo().equals("null"))
            {
                pstmt.setNull(1, 12);
            }
            else
            {
                pstmt.setString(1, this.getSerialNo());
            }
            if (this.getTableCode() == null || this.getTableCode().equals("null"))
            {
                pstmt.setNull(2, 12);
            }
            else
            {
                pstmt.setString(2, this.getTableCode());
            }
            rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next())
            {
                i++;
                if (!this.setSchema(rs, i))
                {
                    // @@閿欒澶勭悊
                    CError tError = new CError();
                    tError.moduleName = "PD_LBRiskInfoDB";
                    tError.functionName = "getInfo";
                    tError.errorMessage = "M0000060027";
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
            tError.moduleName = "PD_LBRiskInfoDB";
            tError.functionName = "getInfo";
            tError.errorMessage = e.toString();
            this.mErrors.addOneError(tError);
            // 杈撳嚭鍑洪敊Sql璇彞
            SQLString sqlObj = new SQLString("PD_LBRiskInfo");
            sqlObj.setSQL(6, this.getSchema());
            logger.debug("鍑洪敊Sql涓猴細" + sqlObj.getSQL());
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
            // 鏂紑鏁版嵁搴撹繛鎺?

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
     * 鏌ヨ鎿嶄綔
     * 鏌ヨ鏉′欢锛氫紶鍏ョ殑schema涓湁鍊肩殑瀛楁
     * @return boolean
     */
    public PD_LBRiskInfoSet query()
    {
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		PD_LBRiskInfoSet aPD_LBRiskInfoSet = new PD_LBRiskInfoSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("PD_LBRiskInfo");
			PD_LBRiskInfoSchema aSchema = this.getSchema();
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
				PD_LBRiskInfoSchema s1 = new PD_LBRiskInfoSchema();
				s1.setSchema(rs,i);
				aPD_LBRiskInfoSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LBRiskInfoDB";
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

		return aPD_LBRiskInfoSet;

    }

    public PD_LBRiskInfoSet executeQuery(String sql)
    {
        Statement stmt = null;
        ResultSet rs = null;
        PD_LBRiskInfoSet aPD_LBRiskInfoSet = new PD_LBRiskInfoSet();
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
                PD_LBRiskInfoSchema s1 = new PD_LBRiskInfoSchema();
                if (!s1.setSchema(rs, i))
                {
                    // @@閿欒澶勭悊
                    CError tError = new CError();
                    tError.moduleName = "PD_LBRiskInfoDB";
                    tError.functionName = "executeQuery";
                    tError.errorMessage = "M0000060028";
                    this.mErrors.addOneError(tError);
                }
                aPD_LBRiskInfoSet.add(s1);
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
            logger.debug("##### Error Sql in PD_LBRiskInfoDB at query(): " + sql);
            // @@閿欒澶勭悊
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDB";
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
        return aPD_LBRiskInfoSet;
    }

    public PD_LBRiskInfoSet query(int nStart, int nCount)
    {
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		PD_LBRiskInfoSet aPD_LBRiskInfoSet = new PD_LBRiskInfoSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("PD_LBRiskInfo");
			PD_LBRiskInfoSchema aSchema = this.getSchema();
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

				PD_LBRiskInfoSchema s1 = new PD_LBRiskInfoSchema();
				s1.setSchema(rs,i);
				aPD_LBRiskInfoSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LBRiskInfoDB";
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

		return aPD_LBRiskInfoSet;

    }

    public PD_LBRiskInfoSet executeQuery(String sql, int nStart, int nCount)
    {
        Statement stmt = null;
        ResultSet rs = null;
        PD_LBRiskInfoSet aPD_LBRiskInfoSet = new PD_LBRiskInfoSet();
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
                PD_LBRiskInfoSchema s1 = new PD_LBRiskInfoSchema();
                if (!s1.setSchema(rs, i))
                {
                    // @@閿欒澶勭悊
                    CError tError = new CError();
                    tError.moduleName = "PD_LBRiskInfoDB";
                    tError.functionName = "executeQuery";
                    tError.errorMessage = "M0000060028";
                    this.mErrors.addOneError(tError);
                }
                aPD_LBRiskInfoSet.add(s1);
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
            logger.debug("##### Error Sql in PD_LBRiskInfoDB at query(): " + sql);
            // @@閿欒澶勭悊
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDB";
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
        return aPD_LBRiskInfoSet;
    }

    public boolean update(String strWherePart)
    {
        Statement stmt = null;
        if (!mflag)
        {
            con = DBConnPool.getConnection();
        }
        SQLString sqlObj = new SQLString("PD_LBRiskInfo");
        sqlObj.setSQL(2, this.getSchema());
        String sql = "update PD_LBRiskInfo " + sqlObj.getUpdPart() + " where " + strWherePart;
        try
        {
            stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            int operCount = stmt.executeUpdate(sql);
            if (operCount == 0)
            {
                // @@閿欒澶勭悊
                CError tError = new CError();
                tError.moduleName = "PD_LBRiskInfoDB";
                tError.functionName = "update";
                tError.errorMessage = "M0000060029";
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
            logger.debug("##### Error Sql in PD_LBRiskInfoDB at update(String strWherePart): " + sql);
            // @@閿欒澶勭悊
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDB";
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
            // 鏂紑鏁版嵁搴撹繛鎺?

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
     * 鍑嗗鏁版嵁鏌ヨ鏉′欢
     * @param strSQL String
     * @return boolean
     */
    public boolean prepareData(String strSQL)
    {
        if (mResultSet != null)
        {
            // @@閿欒澶勭悊
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDB";
            tError.functionName = "prepareData";
            tError.errorMessage = "M0000060030";
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
            // @@閿欒澶勭悊
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDB";
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
     * 鑾峰彇鏁版嵁闆?

     * @return boolean
     */
    public boolean hasMoreData()
    {
        boolean flag = true;
        if (null == mResultSet)
        {
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDB";
            tError.functionName = "hasMoreData";
            tError.errorMessage = "M0000060031";
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
            tError.moduleName = "PD_LBRiskInfoDB";
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
     * 鑾峰彇瀹氶噺鏁版嵁
     * @return PD_LBRiskInfoSet
     */
    public PD_LBRiskInfoSet getData()
    {
        int tCount = 0;
        PD_LBRiskInfoSet tPD_LBRiskInfoSet = new PD_LBRiskInfoSet();
        PD_LBRiskInfoSchema tPD_LBRiskInfoSchema = null;
        if (null == mResultSet)
        {
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDB";
            tError.functionName = "getData";
            tError.errorMessage = "M0000060031";
            this.mErrors.addOneError(tError);
            return null;
        }
        try
        {
            tCount = 1;
            tPD_LBRiskInfoSchema = new PD_LBRiskInfoSchema();
            tPD_LBRiskInfoSchema.setSchema(mResultSet, 1);
            tPD_LBRiskInfoSet.add(tPD_LBRiskInfoSchema);
            //娉ㄦ剰mResultSet.next()鐨勪綔鐢?

            while (tCount++ < SysConst.FETCHCOUNT)
            {
                if (mResultSet.next())
                {
                    tPD_LBRiskInfoSchema = new PD_LBRiskInfoSchema();
                    tPD_LBRiskInfoSchema.setSchema(mResultSet, 1);
                    tPD_LBRiskInfoSet.add(tPD_LBRiskInfoSchema);
                }
            }
        }
        catch (Exception ex)
        {
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDB";
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
        return tPD_LBRiskInfoSet;
    }

    /**
     * 鍏抽棴鏁版嵁闆?

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
                tError.moduleName = "PD_LBRiskInfoDB";
                tError.functionName = "closeData";
                tError.errorMessage = "M0000060032";
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
            tError.moduleName = "PD_LBRiskInfoDB";
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
                tError.moduleName = "PD_LBRiskInfoDB";
                tError.functionName = "closeData";
                tError.errorMessage = "M0000060033";
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
            tError.moduleName = "PD_LBRiskInfoDB";
            tError.functionName = "closeData";
            tError.errorMessage = ex3.toString();
            this.mErrors.addOneError(tError);
            flag = false;
        }
        return flag;
    }

	public PD_LBRiskInfoSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_LBRiskInfoSet aPD_LBRiskInfoSet = new PD_LBRiskInfoSet();

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
				PD_LBRiskInfoSchema s1 = new PD_LBRiskInfoSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LBRiskInfoDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LBRiskInfoSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LBRiskInfoDB";
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

		return aPD_LBRiskInfoSet;
	}

	public PD_LBRiskInfoSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_LBRiskInfoSet aPD_LBRiskInfoSet = new PD_LBRiskInfoSet();

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

				PD_LBRiskInfoSchema s1 = new PD_LBRiskInfoSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LBRiskInfoDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LBRiskInfoSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LBRiskInfoDB";
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

		return aPD_LBRiskInfoSet; 
	}

}
