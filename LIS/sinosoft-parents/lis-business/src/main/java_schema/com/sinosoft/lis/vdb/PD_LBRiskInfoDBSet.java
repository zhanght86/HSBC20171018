/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.io.StringReader;
import java.sql.*;
import com.sinosoft.lis.schema.PD_LBRiskInfoSchema;
import com.sinosoft.lis.vschema.PD_LBRiskInfoSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: PD_LBRiskInfoDBSet </p>
 * <p>Description: DB灞傚璁板綍鏁版嵁搴撴搷浣滅被鏂囦欢 </p>
 * <p>Company: Sinosoft Co.,LTD</p>
 * @Database: PD_LBRiskInfo
 * @author锛歁akerx
 * @CreateDate锛?2009-09-07
 */
public class PD_LBRiskInfoDBSet extends PD_LBRiskInfoSet
{
private static Logger logger = Logger.getLogger(PD_LBRiskInfoDBSet.class);

    // @Field
    private Connection con;
    private DBOper db;
    /**
     * flag = true: 浼犲叆Connection
     * flag = false: 涓嶄紶鍏onnection
     */
    private boolean mflag = false;

    // @Constructor
    public PD_LBRiskInfoDBSet(Connection cConnection)
    {
        con = cConnection;
        db = new DBOper(con, "PD_LBRiskInfo");
        mflag = true;
    }

    public PD_LBRiskInfoDBSet()
    {
        db = new DBOper("PD_LBRiskInfo");
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
            // @@閿欒澶勭悊
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDBSet";
            tError.functionName = "deleteSQL";
            tError.errorMessage = "M0000060026";
            this.mErrors.addOneError(tError);
            return false;
        }
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
            if (!mflag)
            {
                // 濡傛灉鏄唴閮ㄥ垱寤虹殑杩炴帴锛岄渶瑕佽缃瓹ommit妯″紡
                con.setAutoCommit(false);
            }
            int tCount = this.size();
            pstmt = con.prepareStatement("INSERT INTO PD_LBRiskInfo VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getSerialNo());
                }
                if (this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getRiskCode());
                }
                if (this.get(i).getMissionID() == null || this.get(i).getMissionID().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getMissionID());
                }
                if (this.get(i).getSubMissionID() == null || this.get(i).getSubMissionID().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getSubMissionID());
                }
                if (this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null"))
                {
                    pstmt.setString(5, null);
                }
                else
                {
                    pstmt.setString(5, this.get(i).getActivityID());
                }
                if (this.get(i).getTableCode() == null || this.get(i).getTableCode().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getTableCode());
                }
                if (this.get(i).getStandByFlag1() == null || this.get(i).getStandByFlag1().equals("null"))
                {
                    pstmt.setString(7, null);
                }
                else
                {
                    pstmt.setString(7, this.get(i).getStandByFlag1());
                }
                if (this.get(i).getStandByFlag2() == null || this.get(i).getStandByFlag2().equals("null"))
                {
                    pstmt.setString(8, null);
                }
                else
                {
                    pstmt.setString(8, this.get(i).getStandByFlag2());
                }
                if (this.get(i).getStandByFlag3() == null || this.get(i).getStandByFlag3().equals("null"))
                {
                    pstmt.setString(9, null);
                }
                else
                {
                    pstmt.setString(9, this.get(i).getStandByFlag3());
                }
                if (this.get(i).getStandByFlag4() == null || this.get(i).getStandByFlag4().equals("null"))
                {
                    pstmt.setString(10, null);
                }
                else
                {
                    pstmt.setString(10, this.get(i).getStandByFlag4());
                }
                if (this.get(i).getStandByFlag5() == null || this.get(i).getStandByFlag5().equals("null"))
                {
                    pstmt.setString(11, null);
                }
                else
                {
                    pstmt.setString(11, this.get(i).getStandByFlag5());
                }
                if (this.get(i).getStandByFlag6() == null || this.get(i).getStandByFlag6().equals("null"))
                {
                    pstmt.setString(12, null);
                }
                else
                {
                    pstmt.setString(12, this.get(i).getStandByFlag6());
                }
                if (this.get(i).getStandByFlag7() == null || this.get(i).getStandByFlag7().equals("null"))
                {
                    pstmt.setString(13, null);
                }
                else
                {
                    pstmt.setString(13, this.get(i).getStandByFlag7());
                }
                if (this.get(i).getStandByFlag8() == null || this.get(i).getStandByFlag8().equals("null"))
                {
                    pstmt.setString(14, null);
                }
                else
                {
                    pstmt.setString(14, this.get(i).getStandByFlag8());
                }
                if (this.get(i).getStandByFlag9() == null || this.get(i).getStandByFlag9().equals("null"))
                {
                    pstmt.setString(15, null);
                }
                else
                {
                    pstmt.setString(15, this.get(i).getStandByFlag9());
                }
                if (this.get(i).getStandByFlag10() == null || this.get(i).getStandByFlag10().equals("null"))
                {
                    pstmt.setString(16, null);
                }
                else
                {
                    pstmt.setString(16, this.get(i).getStandByFlag10());
                }
                if (this.get(i).getStandByFlag11() == null || this.get(i).getStandByFlag11().equals("null"))
                {
                    pstmt.setString(17, null);
                }
                else
                {
                    pstmt.setString(17, this.get(i).getStandByFlag11());
                }
                if (this.get(i).getStandByFlag12() == null || this.get(i).getStandByFlag12().equals("null"))
                {
                    pstmt.setString(18, null);
                }
                else
                {
                    pstmt.setString(18, this.get(i).getStandByFlag12());
                }
                if (this.get(i).getStandByFlag13() == null || this.get(i).getStandByFlag13().equals("null"))
                {
                    pstmt.setString(19, null);
                }
                else
                {
                    pstmt.setString(19, this.get(i).getStandByFlag13());
                }
                if (this.get(i).getStandByFlag14() == null || this.get(i).getStandByFlag14().equals("null"))
                {
                    pstmt.setString(20, null);
                }
                else
                {
                    pstmt.setString(20, this.get(i).getStandByFlag14());
                }
                if (this.get(i).getStandByFlag15() == null || this.get(i).getStandByFlag15().equals("null"))
                {
                    pstmt.setString(21, null);
                }
                else
                {
                    pstmt.setString(21, this.get(i).getStandByFlag15());
                }
                if (this.get(i).getStandByFlag16() == null || this.get(i).getStandByFlag16().equals("null"))
                {
                    pstmt.setString(22, null);
                }
                else
                {
                    pstmt.setString(22, this.get(i).getStandByFlag16());
                }
                if (this.get(i).getStandByFlag17() == null || this.get(i).getStandByFlag17().equals("null"))
                {
                    pstmt.setString(23, null);
                }
                else
                {
                    pstmt.setString(23, this.get(i).getStandByFlag17());
                }
                if (this.get(i).getStandByFlag18() == null || this.get(i).getStandByFlag18().equals("null"))
                {
                    pstmt.setString(24, null);
                }
                else
                {
                    pstmt.setString(24, this.get(i).getStandByFlag18());
                }
                if (this.get(i).getStandByFlag19() == null || this.get(i).getStandByFlag19().equals("null"))
                {
                    pstmt.setString(25, null);
                }
                else
                {
                    pstmt.setString(25, this.get(i).getStandByFlag19());
                }
                if (this.get(i).getStandByFlag20() == null || this.get(i).getStandByFlag20().equals("null"))
                {
                    pstmt.setString(26, null);
                }
                else
                {
                    pstmt.setString(26, this.get(i).getStandByFlag20());
                }
                if (this.get(i).getStandByFlag21() == null || this.get(i).getStandByFlag21().equals("null"))
                {
                    pstmt.setString(27, null);
                }
                else
                {
                    pstmt.setString(27, this.get(i).getStandByFlag21());
                }
                if (this.get(i).getStandByFlag22() == null || this.get(i).getStandByFlag22().equals("null"))
                {
                    pstmt.setString(28, null);
                }
                else
                {
                    pstmt.setString(28, this.get(i).getStandByFlag22());
                }
                if (this.get(i).getStandByFlag23() == null || this.get(i).getStandByFlag23().equals("null"))
                {
                    pstmt.setString(29, null);
                }
                else
                {
                    pstmt.setString(29, this.get(i).getStandByFlag23());
                }
                if (this.get(i).getStandByFlag24() == null || this.get(i).getStandByFlag24().equals("null"))
                {
                    pstmt.setString(30, null);
                }
                else
                {
                    pstmt.setString(30, this.get(i).getStandByFlag24());
                }
                if (this.get(i).getStandByFlag25() == null || this.get(i).getStandByFlag25().equals("null"))
                {
                    pstmt.setString(31, null);
                }
                else
                {
                    pstmt.setString(31, this.get(i).getStandByFlag25());
                }
                if (this.get(i).getStandByFlag26() == null || this.get(i).getStandByFlag26().equals("null"))
                {
                    pstmt.setString(32, null);
                }
                else
                {
                    pstmt.setString(32, this.get(i).getStandByFlag26());
                }
                if (this.get(i).getStandByFlag27() == null || this.get(i).getStandByFlag27().equals("null"))
                {
                    pstmt.setString(33, null);
                }
                else
                {
                    pstmt.setString(33, this.get(i).getStandByFlag27());
                }
                if (this.get(i).getStandByFlag28() == null || this.get(i).getStandByFlag28().equals("null"))
                {
                    pstmt.setString(34, null);
                }
                else
                {
                    pstmt.setString(34, this.get(i).getStandByFlag28());
                }
                if (this.get(i).getStandByFlag29() == null || this.get(i).getStandByFlag29().equals("null"))
                {
                    pstmt.setString(35, null);
                }
                else
                {
                    pstmt.setString(35, this.get(i).getStandByFlag29());
                }
                if (this.get(i).getStandByFlag30() == null || this.get(i).getStandByFlag30().equals("null"))
                {
                    pstmt.setString(36, null);
                }
                else
                {
                    pstmt.setString(36, this.get(i).getStandByFlag30());
                }
                if (this.get(i).getStandByFlag31() == null || this.get(i).getStandByFlag31().equals("null"))
                {
                    pstmt.setString(37, null);
                }
                else
                {
                    pstmt.setString(37, this.get(i).getStandByFlag31());
                }
                if (this.get(i).getStandByFlag32() == null || this.get(i).getStandByFlag32().equals("null"))
                {
                    pstmt.setString(38, null);
                }
                else
                {
                    pstmt.setString(38, this.get(i).getStandByFlag32());
                }
                if (this.get(i).getStandByFlag33() == null || this.get(i).getStandByFlag33().equals("null"))
                {
                    pstmt.setString(39, null);
                }
                else
                {
                    pstmt.setString(39, this.get(i).getStandByFlag33());
                }
                if (this.get(i).getStandByFlag34() == null || this.get(i).getStandByFlag34().equals("null"))
                {
                    pstmt.setString(40, null);
                }
                else
                {
                    pstmt.setString(40, this.get(i).getStandByFlag34());
                }
                if (this.get(i).getStandByFlag35() == null || this.get(i).getStandByFlag35().equals("null"))
                {
                    pstmt.setString(41, null);
                }
                else
                {
                    pstmt.setString(41, this.get(i).getStandByFlag35());
                }
                if (this.get(i).getStandByFlag36() == null || this.get(i).getStandByFlag36().equals("null"))
                {
                    pstmt.setString(42, null);
                }
                else
                {
                    pstmt.setString(42, this.get(i).getStandByFlag36());
                }
                if (this.get(i).getStandByFlag37() == null || this.get(i).getStandByFlag37().equals("null"))
                {
                    pstmt.setString(43, null);
                }
                else
                {
                    pstmt.setString(43, this.get(i).getStandByFlag37());
                }
                if (this.get(i).getStandByFlag38() == null || this.get(i).getStandByFlag38().equals("null"))
                {
                    pstmt.setString(44, null);
                }
                else
                {
                    pstmt.setString(44, this.get(i).getStandByFlag38());
                }
                if (this.get(i).getStandByFlag39() == null || this.get(i).getStandByFlag39().equals("null"))
                {
                    pstmt.setString(45, null);
                }
                else
                {
                    pstmt.setString(45, this.get(i).getStandByFlag39());
                }
                if (this.get(i).getStandByFlag40() == null || this.get(i).getStandByFlag40().equals("null"))
                {
                    pstmt.setString(46, null);
                }
                else
                {
                    pstmt.setString(46, this.get(i).getStandByFlag40());
                }
                if (this.get(i).getStandByFlag41() == null || this.get(i).getStandByFlag41().equals("null"))
                {
                    pstmt.setString(47, null);
                }
                else
                {
                    pstmt.setString(47, this.get(i).getStandByFlag41());
                }
                if (this.get(i).getStandByFlag42() == null || this.get(i).getStandByFlag42().equals("null"))
                {
                    pstmt.setString(48, null);
                }
                else
                {
                    pstmt.setString(48, this.get(i).getStandByFlag42());
                }
                if (this.get(i).getStandByFlag43() == null || this.get(i).getStandByFlag43().equals("null"))
                {
                    pstmt.setString(49, null);
                }
                else
                {
                    pstmt.setString(49, this.get(i).getStandByFlag43());
                }
                if (this.get(i).getStandByFlag44() == null || this.get(i).getStandByFlag44().equals("null"))
                {
                    pstmt.setString(50, null);
                }
                else
                {
                    pstmt.setString(50, this.get(i).getStandByFlag44());
                }
                if (this.get(i).getStandByFlag45() == null || this.get(i).getStandByFlag45().equals("null"))
                {
                    pstmt.setString(51, null);
                }
                else
                {
                    pstmt.setString(51, this.get(i).getStandByFlag45());
                }
                if (this.get(i).getStandByFlag46() == null || this.get(i).getStandByFlag46().equals("null"))
                {
                    pstmt.setString(52, null);
                }
                else
                {
                    pstmt.setString(52, this.get(i).getStandByFlag46());
                }
                if (this.get(i).getStandByFlag47() == null || this.get(i).getStandByFlag47().equals("null"))
                {
                    pstmt.setString(53, null);
                }
                else
                {
                    pstmt.setString(53, this.get(i).getStandByFlag47());
                }
                if (this.get(i).getStandByFlag48() == null || this.get(i).getStandByFlag48().equals("null"))
                {
                    pstmt.setString(54, null);
                }
                else
                {
                    pstmt.setString(54, this.get(i).getStandByFlag48());
                }
                if (this.get(i).getStandByFlag49() == null || this.get(i).getStandByFlag49().equals("null"))
                {
                    pstmt.setString(55, null);
                }
                else
                {
                    pstmt.setString(55, this.get(i).getStandByFlag49());
                }
                if (this.get(i).getStandByFlag50() == null || this.get(i).getStandByFlag50().equals("null"))
                {
                    pstmt.setString(56, null);
                }
                else
                {
                    pstmt.setString(56, this.get(i).getStandByFlag50());
                }
                if (this.get(i).getLastOperator() == null || this.get(i).getLastOperator().equals("null"))
                {
                    pstmt.setString(57, null);
                }
                else
                {
                    pstmt.setString(57, this.get(i).getLastOperator());
                }
                if (this.get(i).getCreateOperator() == null || this.get(i).getCreateOperator().equals("null"))
                {
                    pstmt.setString(58, null);
                }
                else
                {
                    pstmt.setString(58, this.get(i).getCreateOperator());
                }
                if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
                {
                    pstmt.setDate(59, null);
                }
                else
                {
                    pstmt.setDate(59, Date.valueOf(this.get(i).getMakeDate()));
                }
                if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
                {
                    pstmt.setString(60, null);
                }
                else
                {
                    pstmt.setString(60, this.get(i).getMakeTime());
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            pstmt.close();
            if (!mflag)
            {
                // 濡傛灉鏄唴閮ㄥ垱寤虹殑杩炴帴锛屾墽琛屾垚鍔熷悗闇?瑕佹墽琛孋ommit
                con.commit();
                con.close();
            }
        }
        catch (Exception ex)
        {
            // @@閿欒澶勭悊
            ex.printStackTrace();
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDBSet";
            tError.functionName = "insert()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_LBRiskInfo");
            for (int i = 1; i <= tCount; i++)
            {
                // 杈撳嚭鍑洪敊Sql璇彞
                sqlObj.setSQL(1, this.get(i));
                logger.debug("鍑洪敊Sql涓猴細" + sqlObj.getSQL());
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
                //濡傛灉鏄唴閮ㄥ垱寤虹殑杩炴帴锛屽嚭閿欏悗闇?瑕佹墽琛孯ollBack
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
     * 鍒犻櫎鎿嶄綔
     * 鍒犻櫎鏉′欢锛氫富閿?

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
                // 濡傛灉鏄唴閮ㄥ垱寤虹殑杩炴帴锛岄渶瑕佽缃瓹ommit妯″紡
                con.setAutoCommit(false);
            }
            int tCount = this.size();
            pstmt = con.prepareStatement("DELETE FROM PD_LBRiskInfo WHERE  SerialNo = ? AND TableCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getSerialNo());
                }
                if (this.get(i).getTableCode() == null || this.get(i).getTableCode().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getTableCode());
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            pstmt.close();
            if (!mflag)
            {
                // 濡傛灉鏄唴閮ㄥ垱寤虹殑杩炴帴锛屾墽琛屾垚鍔熷悗闇?瑕佹墽琛孋ommit
                con.commit();
                con.close();
            }
        }
        catch (Exception ex)
        {
            // @@閿欒澶勭悊
            ex.printStackTrace();
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDBSet";
            tError.functionName = "delete()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_LBRiskInfo");
            for (int i = 1; i <= tCount; i++)
            {
                // 杈撳嚭鍑洪敊Sql璇彞
                sqlObj.setSQL(4, this.get(i));
                logger.debug("鍑洪敊Sql涓猴細" + sqlObj.getSQL());
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
                //濡傛灉鏄唴閮ㄥ垱寤虹殑杩炴帴锛屽嚭閿欏悗闇?瑕佹墽琛孯ollBack
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
            if (!mflag)
            {
                // 濡傛灉鏄唴閮ㄥ垱寤虹殑杩炴帴锛岄渶瑕佽缃瓹ommit妯″紡
                con.setAutoCommit(false);
            }
            int tCount = this.size();
            pstmt = con.prepareStatement("UPDATE PD_LBRiskInfo SET  SerialNo = ? , RiskCode = ? , MissionID = ? , SubMissionID = ? , ActivityID = ? , TableCode = ? , StandByFlag1 = ? , StandByFlag2 = ? , StandByFlag3 = ? , StandByFlag4 = ? , StandByFlag5 = ? , StandByFlag6 = ? , StandByFlag7 = ? , StandByFlag8 = ? , StandByFlag9 = ? , StandByFlag10 = ? , StandByFlag11 = ? , StandByFlag12 = ? , StandByFlag13 = ? , StandByFlag14 = ? , StandByFlag15 = ? , StandByFlag16 = ? , StandByFlag17 = ? , StandByFlag18 = ? , StandByFlag19 = ? , StandByFlag20 = ? , StandByFlag21 = ? , StandByFlag22 = ? , StandByFlag23 = ? , StandByFlag24 = ? , StandByFlag25 = ? , StandByFlag26 = ? , StandByFlag27 = ? , StandByFlag28 = ? , StandByFlag29 = ? , StandByFlag30 = ? , StandByFlag31 = ? , StandByFlag32 = ? , StandByFlag33 = ? , StandByFlag34 = ? , StandByFlag35 = ? , StandByFlag36 = ? , StandByFlag37 = ? , StandByFlag38 = ? , StandByFlag39 = ? , StandByFlag40 = ? , StandByFlag41 = ? , StandByFlag42 = ? , StandByFlag43 = ? , StandByFlag44 = ? , StandByFlag45 = ? , StandByFlag46 = ? , StandByFlag47 = ? , StandByFlag48 = ? , StandByFlag49 = ? , StandByFlag50 = ? , LastOperator = ? , CreateOperator = ? , MakeDate = ? , MakeTime = ? WHERE  SerialNo = ? AND TableCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
                if (this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null"))
                {
                    pstmt.setString(1, null);
                }
                else
                {
                    pstmt.setString(1, this.get(i).getSerialNo());
                }
                if (this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null"))
                {
                    pstmt.setString(2, null);
                }
                else
                {
                    pstmt.setString(2, this.get(i).getRiskCode());
                }
                if (this.get(i).getMissionID() == null || this.get(i).getMissionID().equals("null"))
                {
                    pstmt.setString(3, null);
                }
                else
                {
                    pstmt.setString(3, this.get(i).getMissionID());
                }
                if (this.get(i).getSubMissionID() == null || this.get(i).getSubMissionID().equals("null"))
                {
                    pstmt.setString(4, null);
                }
                else
                {
                    pstmt.setString(4, this.get(i).getSubMissionID());
                }
                if (this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null"))
                {
                    pstmt.setString(5, null);
                }
                else
                {
                    pstmt.setString(5, this.get(i).getActivityID());
                }
                if (this.get(i).getTableCode() == null || this.get(i).getTableCode().equals("null"))
                {
                    pstmt.setString(6, null);
                }
                else
                {
                    pstmt.setString(6, this.get(i).getTableCode());
                }
                if (this.get(i).getStandByFlag1() == null || this.get(i).getStandByFlag1().equals("null"))
                {
                    pstmt.setString(7, null);
                }
                else
                {
                    pstmt.setString(7, this.get(i).getStandByFlag1());
                }
                if (this.get(i).getStandByFlag2() == null || this.get(i).getStandByFlag2().equals("null"))
                {
                    pstmt.setString(8, null);
                }
                else
                {
                    pstmt.setString(8, this.get(i).getStandByFlag2());
                }
                if (this.get(i).getStandByFlag3() == null || this.get(i).getStandByFlag3().equals("null"))
                {
                    pstmt.setString(9, null);
                }
                else
                {
                    pstmt.setString(9, this.get(i).getStandByFlag3());
                }
                if (this.get(i).getStandByFlag4() == null || this.get(i).getStandByFlag4().equals("null"))
                {
                    pstmt.setString(10, null);
                }
                else
                {
                    pstmt.setString(10, this.get(i).getStandByFlag4());
                }
                if (this.get(i).getStandByFlag5() == null || this.get(i).getStandByFlag5().equals("null"))
                {
                    pstmt.setString(11, null);
                }
                else
                {
                    pstmt.setString(11, this.get(i).getStandByFlag5());
                }
                if (this.get(i).getStandByFlag6() == null || this.get(i).getStandByFlag6().equals("null"))
                {
                    pstmt.setString(12, null);
                }
                else
                {
                    pstmt.setString(12, this.get(i).getStandByFlag6());
                }
                if (this.get(i).getStandByFlag7() == null || this.get(i).getStandByFlag7().equals("null"))
                {
                    pstmt.setString(13, null);
                }
                else
                {
                    pstmt.setString(13, this.get(i).getStandByFlag7());
                }
                if (this.get(i).getStandByFlag8() == null || this.get(i).getStandByFlag8().equals("null"))
                {
                    pstmt.setString(14, null);
                }
                else
                {
                    pstmt.setString(14, this.get(i).getStandByFlag8());
                }
                if (this.get(i).getStandByFlag9() == null || this.get(i).getStandByFlag9().equals("null"))
                {
                    pstmt.setString(15, null);
                }
                else
                {
                    pstmt.setString(15, this.get(i).getStandByFlag9());
                }
                if (this.get(i).getStandByFlag10() == null || this.get(i).getStandByFlag10().equals("null"))
                {
                    pstmt.setString(16, null);
                }
                else
                {
                    pstmt.setString(16, this.get(i).getStandByFlag10());
                }
                if (this.get(i).getStandByFlag11() == null || this.get(i).getStandByFlag11().equals("null"))
                {
                    pstmt.setString(17, null);
                }
                else
                {
                    pstmt.setString(17, this.get(i).getStandByFlag11());
                }
                if (this.get(i).getStandByFlag12() == null || this.get(i).getStandByFlag12().equals("null"))
                {
                    pstmt.setString(18, null);
                }
                else
                {
                    pstmt.setString(18, this.get(i).getStandByFlag12());
                }
                if (this.get(i).getStandByFlag13() == null || this.get(i).getStandByFlag13().equals("null"))
                {
                    pstmt.setString(19, null);
                }
                else
                {
                    pstmt.setString(19, this.get(i).getStandByFlag13());
                }
                if (this.get(i).getStandByFlag14() == null || this.get(i).getStandByFlag14().equals("null"))
                {
                    pstmt.setString(20, null);
                }
                else
                {
                    pstmt.setString(20, this.get(i).getStandByFlag14());
                }
                if (this.get(i).getStandByFlag15() == null || this.get(i).getStandByFlag15().equals("null"))
                {
                    pstmt.setString(21, null);
                }
                else
                {
                    pstmt.setString(21, this.get(i).getStandByFlag15());
                }
                if (this.get(i).getStandByFlag16() == null || this.get(i).getStandByFlag16().equals("null"))
                {
                    pstmt.setString(22, null);
                }
                else
                {
                    pstmt.setString(22, this.get(i).getStandByFlag16());
                }
                if (this.get(i).getStandByFlag17() == null || this.get(i).getStandByFlag17().equals("null"))
                {
                    pstmt.setString(23, null);
                }
                else
                {
                    pstmt.setString(23, this.get(i).getStandByFlag17());
                }
                if (this.get(i).getStandByFlag18() == null || this.get(i).getStandByFlag18().equals("null"))
                {
                    pstmt.setString(24, null);
                }
                else
                {
                    pstmt.setString(24, this.get(i).getStandByFlag18());
                }
                if (this.get(i).getStandByFlag19() == null || this.get(i).getStandByFlag19().equals("null"))
                {
                    pstmt.setString(25, null);
                }
                else
                {
                    pstmt.setString(25, this.get(i).getStandByFlag19());
                }
                if (this.get(i).getStandByFlag20() == null || this.get(i).getStandByFlag20().equals("null"))
                {
                    pstmt.setString(26, null);
                }
                else
                {
                    pstmt.setString(26, this.get(i).getStandByFlag20());
                }
                if (this.get(i).getStandByFlag21() == null || this.get(i).getStandByFlag21().equals("null"))
                {
                    pstmt.setString(27, null);
                }
                else
                {
                    pstmt.setString(27, this.get(i).getStandByFlag21());
                }
                if (this.get(i).getStandByFlag22() == null || this.get(i).getStandByFlag22().equals("null"))
                {
                    pstmt.setString(28, null);
                }
                else
                {
                    pstmt.setString(28, this.get(i).getStandByFlag22());
                }
                if (this.get(i).getStandByFlag23() == null || this.get(i).getStandByFlag23().equals("null"))
                {
                    pstmt.setString(29, null);
                }
                else
                {
                    pstmt.setString(29, this.get(i).getStandByFlag23());
                }
                if (this.get(i).getStandByFlag24() == null || this.get(i).getStandByFlag24().equals("null"))
                {
                    pstmt.setString(30, null);
                }
                else
                {
                    pstmt.setString(30, this.get(i).getStandByFlag24());
                }
                if (this.get(i).getStandByFlag25() == null || this.get(i).getStandByFlag25().equals("null"))
                {
                    pstmt.setString(31, null);
                }
                else
                {
                    pstmt.setString(31, this.get(i).getStandByFlag25());
                }
                if (this.get(i).getStandByFlag26() == null || this.get(i).getStandByFlag26().equals("null"))
                {
                    pstmt.setString(32, null);
                }
                else
                {
                    pstmt.setString(32, this.get(i).getStandByFlag26());
                }
                if (this.get(i).getStandByFlag27() == null || this.get(i).getStandByFlag27().equals("null"))
                {
                    pstmt.setString(33, null);
                }
                else
                {
                    pstmt.setString(33, this.get(i).getStandByFlag27());
                }
                if (this.get(i).getStandByFlag28() == null || this.get(i).getStandByFlag28().equals("null"))
                {
                    pstmt.setString(34, null);
                }
                else
                {
                    pstmt.setString(34, this.get(i).getStandByFlag28());
                }
                if (this.get(i).getStandByFlag29() == null || this.get(i).getStandByFlag29().equals("null"))
                {
                    pstmt.setString(35, null);
                }
                else
                {
                    pstmt.setString(35, this.get(i).getStandByFlag29());
                }
                if (this.get(i).getStandByFlag30() == null || this.get(i).getStandByFlag30().equals("null"))
                {
                    pstmt.setString(36, null);
                }
                else
                {
                    pstmt.setString(36, this.get(i).getStandByFlag30());
                }
                if (this.get(i).getStandByFlag31() == null || this.get(i).getStandByFlag31().equals("null"))
                {
                    pstmt.setString(37, null);
                }
                else
                {
                    pstmt.setString(37, this.get(i).getStandByFlag31());
                }
                if (this.get(i).getStandByFlag32() == null || this.get(i).getStandByFlag32().equals("null"))
                {
                    pstmt.setString(38, null);
                }
                else
                {
                    pstmt.setString(38, this.get(i).getStandByFlag32());
                }
                if (this.get(i).getStandByFlag33() == null || this.get(i).getStandByFlag33().equals("null"))
                {
                    pstmt.setString(39, null);
                }
                else
                {
                    pstmt.setString(39, this.get(i).getStandByFlag33());
                }
                if (this.get(i).getStandByFlag34() == null || this.get(i).getStandByFlag34().equals("null"))
                {
                    pstmt.setString(40, null);
                }
                else
                {
                    pstmt.setString(40, this.get(i).getStandByFlag34());
                }
                if (this.get(i).getStandByFlag35() == null || this.get(i).getStandByFlag35().equals("null"))
                {
                    pstmt.setString(41, null);
                }
                else
                {
                    pstmt.setString(41, this.get(i).getStandByFlag35());
                }
                if (this.get(i).getStandByFlag36() == null || this.get(i).getStandByFlag36().equals("null"))
                {
                    pstmt.setString(42, null);
                }
                else
                {
                    pstmt.setString(42, this.get(i).getStandByFlag36());
                }
                if (this.get(i).getStandByFlag37() == null || this.get(i).getStandByFlag37().equals("null"))
                {
                    pstmt.setString(43, null);
                }
                else
                {
                    pstmt.setString(43, this.get(i).getStandByFlag37());
                }
                if (this.get(i).getStandByFlag38() == null || this.get(i).getStandByFlag38().equals("null"))
                {
                    pstmt.setString(44, null);
                }
                else
                {
                    pstmt.setString(44, this.get(i).getStandByFlag38());
                }
                if (this.get(i).getStandByFlag39() == null || this.get(i).getStandByFlag39().equals("null"))
                {
                    pstmt.setString(45, null);
                }
                else
                {
                    pstmt.setString(45, this.get(i).getStandByFlag39());
                }
                if (this.get(i).getStandByFlag40() == null || this.get(i).getStandByFlag40().equals("null"))
                {
                    pstmt.setString(46, null);
                }
                else
                {
                    pstmt.setString(46, this.get(i).getStandByFlag40());
                }
                if (this.get(i).getStandByFlag41() == null || this.get(i).getStandByFlag41().equals("null"))
                {
                    pstmt.setString(47, null);
                }
                else
                {
                    pstmt.setString(47, this.get(i).getStandByFlag41());
                }
                if (this.get(i).getStandByFlag42() == null || this.get(i).getStandByFlag42().equals("null"))
                {
                    pstmt.setString(48, null);
                }
                else
                {
                    pstmt.setString(48, this.get(i).getStandByFlag42());
                }
                if (this.get(i).getStandByFlag43() == null || this.get(i).getStandByFlag43().equals("null"))
                {
                    pstmt.setString(49, null);
                }
                else
                {
                    pstmt.setString(49, this.get(i).getStandByFlag43());
                }
                if (this.get(i).getStandByFlag44() == null || this.get(i).getStandByFlag44().equals("null"))
                {
                    pstmt.setString(50, null);
                }
                else
                {
                    pstmt.setString(50, this.get(i).getStandByFlag44());
                }
                if (this.get(i).getStandByFlag45() == null || this.get(i).getStandByFlag45().equals("null"))
                {
                    pstmt.setString(51, null);
                }
                else
                {
                    pstmt.setString(51, this.get(i).getStandByFlag45());
                }
                if (this.get(i).getStandByFlag46() == null || this.get(i).getStandByFlag46().equals("null"))
                {
                    pstmt.setString(52, null);
                }
                else
                {
                    pstmt.setString(52, this.get(i).getStandByFlag46());
                }
                if (this.get(i).getStandByFlag47() == null || this.get(i).getStandByFlag47().equals("null"))
                {
                    pstmt.setString(53, null);
                }
                else
                {
                    pstmt.setString(53, this.get(i).getStandByFlag47());
                }
                if (this.get(i).getStandByFlag48() == null || this.get(i).getStandByFlag48().equals("null"))
                {
                    pstmt.setString(54, null);
                }
                else
                {
                    pstmt.setString(54, this.get(i).getStandByFlag48());
                }
                if (this.get(i).getStandByFlag49() == null || this.get(i).getStandByFlag49().equals("null"))
                {
                    pstmt.setString(55, null);
                }
                else
                {
                    pstmt.setString(55, this.get(i).getStandByFlag49());
                }
                if (this.get(i).getStandByFlag50() == null || this.get(i).getStandByFlag50().equals("null"))
                {
                    pstmt.setString(56, null);
                }
                else
                {
                    pstmt.setString(56, this.get(i).getStandByFlag50());
                }
                if (this.get(i).getLastOperator() == null || this.get(i).getLastOperator().equals("null"))
                {
                    pstmt.setString(57, null);
                }
                else
                {
                    pstmt.setString(57, this.get(i).getLastOperator());
                }
                if (this.get(i).getCreateOperator() == null || this.get(i).getCreateOperator().equals("null"))
                {
                    pstmt.setString(58, null);
                }
                else
                {
                    pstmt.setString(58, this.get(i).getCreateOperator());
                }
                if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
                {
                    pstmt.setDate(59, null);
                }
                else
                {
                    pstmt.setDate(59, Date.valueOf(this.get(i).getMakeDate()));
                }
                if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
                {
                    pstmt.setString(60, null);
                }
                else
                {
                    pstmt.setString(60, this.get(i).getMakeTime());
                }
                if (this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null"))
                {
                    pstmt.setString(61, null);
                }
                else
                {
                    pstmt.setString(61, this.get(i).getSerialNo());
                }
                if (this.get(i).getTableCode() == null || this.get(i).getTableCode().equals("null"))
                {
                    pstmt.setString(62, null);
                }
                else
                {
                    pstmt.setString(62, this.get(i).getTableCode());
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            pstmt.close();
            if (!mflag)
            {
                // 濡傛灉鏄唴閮ㄥ垱寤虹殑杩炴帴锛屾墽琛屾垚鍔熷悗闇?瑕佹墽琛孋ommit
                con.commit();
                con.close();
            }
        }
        catch (Exception ex)
        {
            // @@閿欒澶勭悊
            ex.printStackTrace();
            this.mErrors.copyAllErrors(db.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoDBSet";
            tError.functionName = "update()";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            int tCount = this.size();
            SQLString sqlObj = new SQLString("PD_LBRiskInfo");
            for (int i = 1; i <= tCount; i++)
            {
                // 杈撳嚭鍑洪敊Sql璇彞
                sqlObj.setSQL(2, this.get(i));
                logger.debug("鍑洪敊Sql涓猴細" + sqlObj.getSQL());
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
                //濡傛灉鏄唴閮ㄥ垱寤虹殑杩炴帴锛屽嚭閿欏悗闇?瑕佹墽琛孯ollBack
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
