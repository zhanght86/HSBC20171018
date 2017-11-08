/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.PD_LBRiskInfoDB;

/**
 * <p>ClassName: PD_LBRiskInfoSchema </p>
 * <p>Description: DB灞? Schema 绫绘枃浠? </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: PD_LBRiskInfo
 * @author锛歁akerx
 * @CreateDate锛?2009-09-07
 */
public class PD_LBRiskInfoSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LBRiskInfoSchema.class);

    // @Field
    /** 鍗曡瘉缂栫爜 */
    private String SerialNo;
    /** 涓婚櫓闄╃缂栫爜 */
    private String RiskCode;
    /** 宸ヤ綔娴丮issionID */
    private String MissionID;
    /** 宸ヤ綔娴丼ubMissID */
    private String SubMissionID;
    /** 宸ヤ綔娴丄ctivityID */
    private String ActivityID;
    /** 琚浠借〃缂栫爜 */
    private String TableCode;
    /** 澶囦唤瀛楁1 */
    private String StandByFlag1;
    /** 澶囦唤瀛楁2 */
    private String StandByFlag2;
    /** 澶囦唤瀛楁3 */
    private String StandByFlag3;
    /** 澶囦唤瀛楁4 */
    private String StandByFlag4;
    /** 澶囦唤瀛楁5 */
    private String StandByFlag5;
    /** 澶囦唤瀛楁6 */
    private String StandByFlag6;
    /** 澶囦唤瀛楁7 */
    private String StandByFlag7;
    /** 澶囦唤瀛楁8 */
    private String StandByFlag8;
    /** 澶囦唤瀛楁9 */
    private String StandByFlag9;
    /** 澶囦唤瀛楁10 */
    private String StandByFlag10;
    /** 澶囦唤瀛楁11 */
    private String StandByFlag11;
    /** 澶囦唤瀛楁12 */
    private String StandByFlag12;
    /** 澶囦唤瀛楁13 */
    private String StandByFlag13;
    /** 澶囦唤瀛楁14 */
    private String StandByFlag14;
    /** 澶囦唤瀛楁15 */
    private String StandByFlag15;
    /** 澶囦唤瀛楁16 */
    private String StandByFlag16;
    /** 澶囦唤瀛楁17 */
    private String StandByFlag17;
    /** 澶囦唤瀛楁18 */
    private String StandByFlag18;
    /** 澶囦唤瀛楁19 */
    private String StandByFlag19;
    /** 澶囦唤瀛楁20 */
    private String StandByFlag20;
    /** 澶囦唤瀛楁21 */
    private String StandByFlag21;
    /** 澶囦唤瀛楁22 */
    private String StandByFlag22;
    /** 澶囦唤瀛楁23 */
    private String StandByFlag23;
    /** 澶囦唤瀛楁24 */
    private String StandByFlag24;
    /** 澶囦唤瀛楁25 */
    private String StandByFlag25;
    /** 澶囦唤瀛楁26 */
    private String StandByFlag26;
    /** 澶囦唤瀛楁27 */
    private String StandByFlag27;
    /** 澶囦唤瀛楁28 */
    private String StandByFlag28;
    /** 澶囦唤瀛楁29 */
    private String StandByFlag29;
    /** 澶囦唤瀛楁30 */
    private String StandByFlag30;
    /** 澶囦唤瀛楁31 */
    private String StandByFlag31;
    /** 澶囦唤瀛楁32 */
    private String StandByFlag32;
    /** 澶囦唤瀛楁33 */
    private String StandByFlag33;
    /** 澶囦唤瀛楁34 */
    private String StandByFlag34;
    /** 澶囦唤瀛楁35 */
    private String StandByFlag35;
    /** 澶囦唤瀛楁36 */
    private String StandByFlag36;
    /** 澶囦唤瀛楁37 */
    private String StandByFlag37;
    /** 澶囦唤瀛楁38 */
    private String StandByFlag38;
    /** 澶囦唤瀛楁39 */
    private String StandByFlag39;
    /** 澶囦唤瀛楁40 */
    private String StandByFlag40;
    /** 澶囦唤瀛楁41 */
    private String StandByFlag41;
    /** 澶囦唤瀛楁42 */
    private String StandByFlag42;
    /** 澶囦唤瀛楁43 */
    private String StandByFlag43;
    /** 澶囦唤瀛楁44 */
    private String StandByFlag44;
    /** 澶囦唤瀛楁45 */
    private String StandByFlag45;
    /** 澶囦唤瀛楁46 */
    private String StandByFlag46;
    /** 澶囦唤瀛楁47 */
    private String StandByFlag47;
    /** 澶囦唤瀛楁48 */
    private String StandByFlag48;
    /** 澶囦唤瀛楁49 */
    private String StandByFlag49;
    /** 澶囦唤瀛楁50 */
    private String StandByFlag50;
    /** 鍘熸搷浣滃憳 */
    private String LastOperator;
    /** 鎿嶄綔鍛? */
    private String CreateOperator;
    /** 鍒涘缓鏃ユ湡 */
    private Date MakeDate;
    /** 鍒涘缓鏃堕棿 */
    private String MakeTime;

    public static final int FIELDNUM = 60; // 鏁版嵁搴撹〃鐨勫瓧娈典釜鏁?


    private static String[] PK; // 涓婚敭

    private FDate fDate = new FDate(); // 澶勭悊鏃ユ湡

    public CErrors mErrors; // 閿欒淇℃伅

    // @Constructor
    public PD_LBRiskInfoSchema()
    {
        mErrors = new CErrors();
        String[] pk = new String[2];
        pk[0] = "SerialNo";
        pk[1] = "TableCode";
        PK = pk;
    }

    /**
     * Schema鍏嬮殕
     * @return Object
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException
    {
        PD_LBRiskInfoSchema cloned = (PD_LBRiskInfoSchema)super.clone();
        cloned.fDate = (FDate) fDate.clone();
        cloned.mErrors = (CErrors) mErrors.clone();
        return cloned;
    }

    // @Method
    public String[] getPK()
    {
        return PK;
    }

    public String getSerialNo()
    {
        return SerialNo;
    }

    public void setSerialNo(String aSerialNo)
    {
        SerialNo = aSerialNo;
    }

    public String getRiskCode()
    {
        return RiskCode;
    }

    public void setRiskCode(String aRiskCode)
    {
        RiskCode = aRiskCode;
    }

    public String getMissionID()
    {
        return MissionID;
    }

    public void setMissionID(String aMissionID)
    {
        MissionID = aMissionID;
    }

    public String getSubMissionID()
    {
        return SubMissionID;
    }

    public void setSubMissionID(String aSubMissionID)
    {
        SubMissionID = aSubMissionID;
    }

    public String getActivityID()
    {
        return ActivityID;
    }

    public void setActivityID(String aActivityID)
    {
        ActivityID = aActivityID;
    }

    public String getTableCode()
    {
        return TableCode;
    }

    public void setTableCode(String aTableCode)
    {
        TableCode = aTableCode;
    }

    public String getStandByFlag1()
    {
        return StandByFlag1;
    }

    public void setStandByFlag1(String aStandByFlag1)
    {
        StandByFlag1 = aStandByFlag1;
    }

    public String getStandByFlag2()
    {
        return StandByFlag2;
    }

    public void setStandByFlag2(String aStandByFlag2)
    {
        StandByFlag2 = aStandByFlag2;
    }

    public String getStandByFlag3()
    {
        return StandByFlag3;
    }

    public void setStandByFlag3(String aStandByFlag3)
    {
        StandByFlag3 = aStandByFlag3;
    }

    public String getStandByFlag4()
    {
        return StandByFlag4;
    }

    public void setStandByFlag4(String aStandByFlag4)
    {
        StandByFlag4 = aStandByFlag4;
    }

    public String getStandByFlag5()
    {
        return StandByFlag5;
    }

    public void setStandByFlag5(String aStandByFlag5)
    {
        StandByFlag5 = aStandByFlag5;
    }

    public String getStandByFlag6()
    {
        return StandByFlag6;
    }

    public void setStandByFlag6(String aStandByFlag6)
    {
        StandByFlag6 = aStandByFlag6;
    }

    public String getStandByFlag7()
    {
        return StandByFlag7;
    }

    public void setStandByFlag7(String aStandByFlag7)
    {
        StandByFlag7 = aStandByFlag7;
    }

    public String getStandByFlag8()
    {
        return StandByFlag8;
    }

    public void setStandByFlag8(String aStandByFlag8)
    {
        StandByFlag8 = aStandByFlag8;
    }

    public String getStandByFlag9()
    {
        return StandByFlag9;
    }

    public void setStandByFlag9(String aStandByFlag9)
    {
        StandByFlag9 = aStandByFlag9;
    }

    public String getStandByFlag10()
    {
        return StandByFlag10;
    }

    public void setStandByFlag10(String aStandByFlag10)
    {
        StandByFlag10 = aStandByFlag10;
    }

    public String getStandByFlag11()
    {
        return StandByFlag11;
    }

    public void setStandByFlag11(String aStandByFlag11)
    {
        StandByFlag11 = aStandByFlag11;
    }

    public String getStandByFlag12()
    {
        return StandByFlag12;
    }

    public void setStandByFlag12(String aStandByFlag12)
    {
        StandByFlag12 = aStandByFlag12;
    }

    public String getStandByFlag13()
    {
        return StandByFlag13;
    }

    public void setStandByFlag13(String aStandByFlag13)
    {
        StandByFlag13 = aStandByFlag13;
    }

    public String getStandByFlag14()
    {
        return StandByFlag14;
    }

    public void setStandByFlag14(String aStandByFlag14)
    {
        StandByFlag14 = aStandByFlag14;
    }

    public String getStandByFlag15()
    {
        return StandByFlag15;
    }

    public void setStandByFlag15(String aStandByFlag15)
    {
        StandByFlag15 = aStandByFlag15;
    }

    public String getStandByFlag16()
    {
        return StandByFlag16;
    }

    public void setStandByFlag16(String aStandByFlag16)
    {
        StandByFlag16 = aStandByFlag16;
    }

    public String getStandByFlag17()
    {
        return StandByFlag17;
    }

    public void setStandByFlag17(String aStandByFlag17)
    {
        StandByFlag17 = aStandByFlag17;
    }

    public String getStandByFlag18()
    {
        return StandByFlag18;
    }

    public void setStandByFlag18(String aStandByFlag18)
    {
        StandByFlag18 = aStandByFlag18;
    }

    public String getStandByFlag19()
    {
        return StandByFlag19;
    }

    public void setStandByFlag19(String aStandByFlag19)
    {
        StandByFlag19 = aStandByFlag19;
    }

    public String getStandByFlag20()
    {
        return StandByFlag20;
    }

    public void setStandByFlag20(String aStandByFlag20)
    {
        StandByFlag20 = aStandByFlag20;
    }

    public String getStandByFlag21()
    {
        return StandByFlag21;
    }

    public void setStandByFlag21(String aStandByFlag21)
    {
        StandByFlag21 = aStandByFlag21;
    }

    public String getStandByFlag22()
    {
        return StandByFlag22;
    }

    public void setStandByFlag22(String aStandByFlag22)
    {
        StandByFlag22 = aStandByFlag22;
    }

    public String getStandByFlag23()
    {
        return StandByFlag23;
    }

    public void setStandByFlag23(String aStandByFlag23)
    {
        StandByFlag23 = aStandByFlag23;
    }

    public String getStandByFlag24()
    {
        return StandByFlag24;
    }

    public void setStandByFlag24(String aStandByFlag24)
    {
        StandByFlag24 = aStandByFlag24;
    }

    public String getStandByFlag25()
    {
        return StandByFlag25;
    }

    public void setStandByFlag25(String aStandByFlag25)
    {
        StandByFlag25 = aStandByFlag25;
    }

    public String getStandByFlag26()
    {
        return StandByFlag26;
    }

    public void setStandByFlag26(String aStandByFlag26)
    {
        StandByFlag26 = aStandByFlag26;
    }

    public String getStandByFlag27()
    {
        return StandByFlag27;
    }

    public void setStandByFlag27(String aStandByFlag27)
    {
        StandByFlag27 = aStandByFlag27;
    }

    public String getStandByFlag28()
    {
        return StandByFlag28;
    }

    public void setStandByFlag28(String aStandByFlag28)
    {
        StandByFlag28 = aStandByFlag28;
    }

    public String getStandByFlag29()
    {
        return StandByFlag29;
    }

    public void setStandByFlag29(String aStandByFlag29)
    {
        StandByFlag29 = aStandByFlag29;
    }

    public String getStandByFlag30()
    {
        return StandByFlag30;
    }

    public void setStandByFlag30(String aStandByFlag30)
    {
        StandByFlag30 = aStandByFlag30;
    }

    public String getStandByFlag31()
    {
        return StandByFlag31;
    }

    public void setStandByFlag31(String aStandByFlag31)
    {
        StandByFlag31 = aStandByFlag31;
    }

    public String getStandByFlag32()
    {
        return StandByFlag32;
    }

    public void setStandByFlag32(String aStandByFlag32)
    {
        StandByFlag32 = aStandByFlag32;
    }

    public String getStandByFlag33()
    {
        return StandByFlag33;
    }

    public void setStandByFlag33(String aStandByFlag33)
    {
        StandByFlag33 = aStandByFlag33;
    }

    public String getStandByFlag34()
    {
        return StandByFlag34;
    }

    public void setStandByFlag34(String aStandByFlag34)
    {
        StandByFlag34 = aStandByFlag34;
    }

    public String getStandByFlag35()
    {
        return StandByFlag35;
    }

    public void setStandByFlag35(String aStandByFlag35)
    {
        StandByFlag35 = aStandByFlag35;
    }

    public String getStandByFlag36()
    {
        return StandByFlag36;
    }

    public void setStandByFlag36(String aStandByFlag36)
    {
        StandByFlag36 = aStandByFlag36;
    }

    public String getStandByFlag37()
    {
        return StandByFlag37;
    }

    public void setStandByFlag37(String aStandByFlag37)
    {
        StandByFlag37 = aStandByFlag37;
    }

    public String getStandByFlag38()
    {
        return StandByFlag38;
    }

    public void setStandByFlag38(String aStandByFlag38)
    {
        StandByFlag38 = aStandByFlag38;
    }

    public String getStandByFlag39()
    {
        return StandByFlag39;
    }

    public void setStandByFlag39(String aStandByFlag39)
    {
        StandByFlag39 = aStandByFlag39;
    }

    public String getStandByFlag40()
    {
        return StandByFlag40;
    }

    public void setStandByFlag40(String aStandByFlag40)
    {
        StandByFlag40 = aStandByFlag40;
    }

    public String getStandByFlag41()
    {
        return StandByFlag41;
    }

    public void setStandByFlag41(String aStandByFlag41)
    {
        StandByFlag41 = aStandByFlag41;
    }

    public String getStandByFlag42()
    {
        return StandByFlag42;
    }

    public void setStandByFlag42(String aStandByFlag42)
    {
        StandByFlag42 = aStandByFlag42;
    }

    public String getStandByFlag43()
    {
        return StandByFlag43;
    }

    public void setStandByFlag43(String aStandByFlag43)
    {
        StandByFlag43 = aStandByFlag43;
    }

    public String getStandByFlag44()
    {
        return StandByFlag44;
    }

    public void setStandByFlag44(String aStandByFlag44)
    {
        StandByFlag44 = aStandByFlag44;
    }

    public String getStandByFlag45()
    {
        return StandByFlag45;
    }

    public void setStandByFlag45(String aStandByFlag45)
    {
        StandByFlag45 = aStandByFlag45;
    }

    public String getStandByFlag46()
    {
        return StandByFlag46;
    }

    public void setStandByFlag46(String aStandByFlag46)
    {
        StandByFlag46 = aStandByFlag46;
    }

    public String getStandByFlag47()
    {
        return StandByFlag47;
    }

    public void setStandByFlag47(String aStandByFlag47)
    {
        StandByFlag47 = aStandByFlag47;
    }

    public String getStandByFlag48()
    {
        return StandByFlag48;
    }

    public void setStandByFlag48(String aStandByFlag48)
    {
        StandByFlag48 = aStandByFlag48;
    }

    public String getStandByFlag49()
    {
        return StandByFlag49;
    }

    public void setStandByFlag49(String aStandByFlag49)
    {
        StandByFlag49 = aStandByFlag49;
    }

    public String getStandByFlag50()
    {
        return StandByFlag50;
    }

    public void setStandByFlag50(String aStandByFlag50)
    {
        StandByFlag50 = aStandByFlag50;
    }

    public String getLastOperator()
    {
        return LastOperator;
    }

    public void setLastOperator(String aLastOperator)
    {
        LastOperator = aLastOperator;
    }

    public String getCreateOperator()
    {
        return CreateOperator;
    }

    public void setCreateOperator(String aCreateOperator)
    {
        CreateOperator = aCreateOperator;
    }

    public String getMakeDate()
    {
        if (MakeDate != null)
            return fDate.getString(MakeDate);
        else
            return null;
    }

    public void setMakeDate(Date aMakeDate)
    {
        MakeDate = aMakeDate;
    }

    public void setMakeDate(String aMakeDate)
    {
        if (aMakeDate != null && !aMakeDate.equals(""))
        {
            MakeDate = fDate.getDate(aMakeDate);
        }
        else
            MakeDate = null;
    }

    public String getMakeTime()
    {
        return MakeTime;
    }

    public void setMakeTime(String aMakeTime)
    {
        MakeTime = aMakeTime;
    }

    /**
     * 浣跨敤鍙﹀涓?涓? PD_LBRiskInfoSchema 瀵硅薄缁? Schema 璧嬪??

     * @param: aPD_LBRiskInfoSchema PD_LBRiskInfoSchema
     **/
    public void setSchema(PD_LBRiskInfoSchema aPD_LBRiskInfoSchema)
    {
        this.SerialNo = aPD_LBRiskInfoSchema.getSerialNo();
        this.RiskCode = aPD_LBRiskInfoSchema.getRiskCode();
        this.MissionID = aPD_LBRiskInfoSchema.getMissionID();
        this.SubMissionID = aPD_LBRiskInfoSchema.getSubMissionID();
        this.ActivityID = aPD_LBRiskInfoSchema.getActivityID();
        this.TableCode = aPD_LBRiskInfoSchema.getTableCode();
        this.StandByFlag1 = aPD_LBRiskInfoSchema.getStandByFlag1();
        this.StandByFlag2 = aPD_LBRiskInfoSchema.getStandByFlag2();
        this.StandByFlag3 = aPD_LBRiskInfoSchema.getStandByFlag3();
        this.StandByFlag4 = aPD_LBRiskInfoSchema.getStandByFlag4();
        this.StandByFlag5 = aPD_LBRiskInfoSchema.getStandByFlag5();
        this.StandByFlag6 = aPD_LBRiskInfoSchema.getStandByFlag6();
        this.StandByFlag7 = aPD_LBRiskInfoSchema.getStandByFlag7();
        this.StandByFlag8 = aPD_LBRiskInfoSchema.getStandByFlag8();
        this.StandByFlag9 = aPD_LBRiskInfoSchema.getStandByFlag9();
        this.StandByFlag10 = aPD_LBRiskInfoSchema.getStandByFlag10();
        this.StandByFlag11 = aPD_LBRiskInfoSchema.getStandByFlag11();
        this.StandByFlag12 = aPD_LBRiskInfoSchema.getStandByFlag12();
        this.StandByFlag13 = aPD_LBRiskInfoSchema.getStandByFlag13();
        this.StandByFlag14 = aPD_LBRiskInfoSchema.getStandByFlag14();
        this.StandByFlag15 = aPD_LBRiskInfoSchema.getStandByFlag15();
        this.StandByFlag16 = aPD_LBRiskInfoSchema.getStandByFlag16();
        this.StandByFlag17 = aPD_LBRiskInfoSchema.getStandByFlag17();
        this.StandByFlag18 = aPD_LBRiskInfoSchema.getStandByFlag18();
        this.StandByFlag19 = aPD_LBRiskInfoSchema.getStandByFlag19();
        this.StandByFlag20 = aPD_LBRiskInfoSchema.getStandByFlag20();
        this.StandByFlag21 = aPD_LBRiskInfoSchema.getStandByFlag21();
        this.StandByFlag22 = aPD_LBRiskInfoSchema.getStandByFlag22();
        this.StandByFlag23 = aPD_LBRiskInfoSchema.getStandByFlag23();
        this.StandByFlag24 = aPD_LBRiskInfoSchema.getStandByFlag24();
        this.StandByFlag25 = aPD_LBRiskInfoSchema.getStandByFlag25();
        this.StandByFlag26 = aPD_LBRiskInfoSchema.getStandByFlag26();
        this.StandByFlag27 = aPD_LBRiskInfoSchema.getStandByFlag27();
        this.StandByFlag28 = aPD_LBRiskInfoSchema.getStandByFlag28();
        this.StandByFlag29 = aPD_LBRiskInfoSchema.getStandByFlag29();
        this.StandByFlag30 = aPD_LBRiskInfoSchema.getStandByFlag30();
        this.StandByFlag31 = aPD_LBRiskInfoSchema.getStandByFlag31();
        this.StandByFlag32 = aPD_LBRiskInfoSchema.getStandByFlag32();
        this.StandByFlag33 = aPD_LBRiskInfoSchema.getStandByFlag33();
        this.StandByFlag34 = aPD_LBRiskInfoSchema.getStandByFlag34();
        this.StandByFlag35 = aPD_LBRiskInfoSchema.getStandByFlag35();
        this.StandByFlag36 = aPD_LBRiskInfoSchema.getStandByFlag36();
        this.StandByFlag37 = aPD_LBRiskInfoSchema.getStandByFlag37();
        this.StandByFlag38 = aPD_LBRiskInfoSchema.getStandByFlag38();
        this.StandByFlag39 = aPD_LBRiskInfoSchema.getStandByFlag39();
        this.StandByFlag40 = aPD_LBRiskInfoSchema.getStandByFlag40();
        this.StandByFlag41 = aPD_LBRiskInfoSchema.getStandByFlag41();
        this.StandByFlag42 = aPD_LBRiskInfoSchema.getStandByFlag42();
        this.StandByFlag43 = aPD_LBRiskInfoSchema.getStandByFlag43();
        this.StandByFlag44 = aPD_LBRiskInfoSchema.getStandByFlag44();
        this.StandByFlag45 = aPD_LBRiskInfoSchema.getStandByFlag45();
        this.StandByFlag46 = aPD_LBRiskInfoSchema.getStandByFlag46();
        this.StandByFlag47 = aPD_LBRiskInfoSchema.getStandByFlag47();
        this.StandByFlag48 = aPD_LBRiskInfoSchema.getStandByFlag48();
        this.StandByFlag49 = aPD_LBRiskInfoSchema.getStandByFlag49();
        this.StandByFlag50 = aPD_LBRiskInfoSchema.getStandByFlag50();
        this.LastOperator = aPD_LBRiskInfoSchema.getLastOperator();
        this.CreateOperator = aPD_LBRiskInfoSchema.getCreateOperator();
        this.MakeDate = fDate.getDate(aPD_LBRiskInfoSchema.getMakeDate());
        this.MakeTime = aPD_LBRiskInfoSchema.getMakeTime();
    }

    /**
     * 浣跨敤 ResultSet 涓殑绗? i 琛岀粰 Schema 璧嬪??

     * @param: rs ResultSet
     * @param: i int
     * @return: boolean
     **/
    public boolean setSchema(ResultSet rs, int i)
    {
        try
        {
            //rs.absolute(i); // 闈炴粴鍔ㄦ父鏍?

            if (rs.getString(1) == null)
                this.SerialNo = null;
            else
                this.SerialNo = rs.getString(1).trim();
            if (rs.getString(2) == null)
                this.RiskCode = null;
            else
                this.RiskCode = rs.getString(2).trim();
            if (rs.getString(3) == null)
                this.MissionID = null;
            else
                this.MissionID = rs.getString(3).trim();
            if (rs.getString(4) == null)
                this.SubMissionID = null;
            else
                this.SubMissionID = rs.getString(4).trim();
            if (rs.getString(5) == null)
                this.ActivityID = null;
            else
                this.ActivityID = rs.getString(5).trim();
            if (rs.getString(6) == null)
                this.TableCode = null;
            else
                this.TableCode = rs.getString(6).trim();
            if (rs.getString(7) == null)
                this.StandByFlag1 = null;
            else
                this.StandByFlag1 = rs.getString(7).trim();
            if (rs.getString(8) == null)
                this.StandByFlag2 = null;
            else
                this.StandByFlag2 = rs.getString(8).trim();
            if (rs.getString(9) == null)
                this.StandByFlag3 = null;
            else
                this.StandByFlag3 = rs.getString(9).trim();
            if (rs.getString(10) == null)
                this.StandByFlag4 = null;
            else
                this.StandByFlag4 = rs.getString(10).trim();
            if (rs.getString(11) == null)
                this.StandByFlag5 = null;
            else
                this.StandByFlag5 = rs.getString(11).trim();
            if (rs.getString(12) == null)
                this.StandByFlag6 = null;
            else
                this.StandByFlag6 = rs.getString(12).trim();
            if (rs.getString(13) == null)
                this.StandByFlag7 = null;
            else
                this.StandByFlag7 = rs.getString(13).trim();
            if (rs.getString(14) == null)
                this.StandByFlag8 = null;
            else
                this.StandByFlag8 = rs.getString(14).trim();
            if (rs.getString(15) == null)
                this.StandByFlag9 = null;
            else
                this.StandByFlag9 = rs.getString(15).trim();
            if (rs.getString(16) == null)
                this.StandByFlag10 = null;
            else
                this.StandByFlag10 = rs.getString(16).trim();
            if (rs.getString(17) == null)
                this.StandByFlag11 = null;
            else
                this.StandByFlag11 = rs.getString(17).trim();
            if (rs.getString(18) == null)
                this.StandByFlag12 = null;
            else
                this.StandByFlag12 = rs.getString(18).trim();
            if (rs.getString(19) == null)
                this.StandByFlag13 = null;
            else
                this.StandByFlag13 = rs.getString(19).trim();
            if (rs.getString(20) == null)
                this.StandByFlag14 = null;
            else
                this.StandByFlag14 = rs.getString(20).trim();
            if (rs.getString(21) == null)
                this.StandByFlag15 = null;
            else
                this.StandByFlag15 = rs.getString(21).trim();
            if (rs.getString(22) == null)
                this.StandByFlag16 = null;
            else
                this.StandByFlag16 = rs.getString(22).trim();
            if (rs.getString(23) == null)
                this.StandByFlag17 = null;
            else
                this.StandByFlag17 = rs.getString(23).trim();
            if (rs.getString(24) == null)
                this.StandByFlag18 = null;
            else
                this.StandByFlag18 = rs.getString(24).trim();
            if (rs.getString(25) == null)
                this.StandByFlag19 = null;
            else
                this.StandByFlag19 = rs.getString(25).trim();
            if (rs.getString(26) == null)
                this.StandByFlag20 = null;
            else
                this.StandByFlag20 = rs.getString(26).trim();
            if (rs.getString(27) == null)
                this.StandByFlag21 = null;
            else
                this.StandByFlag21 = rs.getString(27).trim();
            if (rs.getString(28) == null)
                this.StandByFlag22 = null;
            else
                this.StandByFlag22 = rs.getString(28).trim();
            if (rs.getString(29) == null)
                this.StandByFlag23 = null;
            else
                this.StandByFlag23 = rs.getString(29).trim();
            if (rs.getString(30) == null)
                this.StandByFlag24 = null;
            else
                this.StandByFlag24 = rs.getString(30).trim();
            if (rs.getString(31) == null)
                this.StandByFlag25 = null;
            else
                this.StandByFlag25 = rs.getString(31).trim();
            if (rs.getString(32) == null)
                this.StandByFlag26 = null;
            else
                this.StandByFlag26 = rs.getString(32).trim();
            if (rs.getString(33) == null)
                this.StandByFlag27 = null;
            else
                this.StandByFlag27 = rs.getString(33).trim();
            if (rs.getString(34) == null)
                this.StandByFlag28 = null;
            else
                this.StandByFlag28 = rs.getString(34).trim();
            if (rs.getString(35) == null)
                this.StandByFlag29 = null;
            else
                this.StandByFlag29 = rs.getString(35).trim();
            if (rs.getString(36) == null)
                this.StandByFlag30 = null;
            else
                this.StandByFlag30 = rs.getString(36).trim();
            if (rs.getString(37) == null)
                this.StandByFlag31 = null;
            else
                this.StandByFlag31 = rs.getString(37).trim();
            if (rs.getString(38) == null)
                this.StandByFlag32 = null;
            else
                this.StandByFlag32 = rs.getString(38).trim();
            if (rs.getString(39) == null)
                this.StandByFlag33 = null;
            else
                this.StandByFlag33 = rs.getString(39).trim();
            if (rs.getString(40) == null)
                this.StandByFlag34 = null;
            else
                this.StandByFlag34 = rs.getString(40).trim();
            if (rs.getString(41) == null)
                this.StandByFlag35 = null;
            else
                this.StandByFlag35 = rs.getString(41).trim();
            if (rs.getString(42) == null)
                this.StandByFlag36 = null;
            else
                this.StandByFlag36 = rs.getString(42).trim();
            if (rs.getString(43) == null)
                this.StandByFlag37 = null;
            else
                this.StandByFlag37 = rs.getString(43).trim();
            if (rs.getString(44) == null)
                this.StandByFlag38 = null;
            else
                this.StandByFlag38 = rs.getString(44).trim();
            if (rs.getString(45) == null)
                this.StandByFlag39 = null;
            else
                this.StandByFlag39 = rs.getString(45).trim();
            if (rs.getString(46) == null)
                this.StandByFlag40 = null;
            else
                this.StandByFlag40 = rs.getString(46).trim();
            if (rs.getString(47) == null)
                this.StandByFlag41 = null;
            else
                this.StandByFlag41 = rs.getString(47).trim();
            if (rs.getString(48) == null)
                this.StandByFlag42 = null;
            else
                this.StandByFlag42 = rs.getString(48).trim();
            if (rs.getString(49) == null)
                this.StandByFlag43 = null;
            else
                this.StandByFlag43 = rs.getString(49).trim();
            if (rs.getString(50) == null)
                this.StandByFlag44 = null;
            else
                this.StandByFlag44 = rs.getString(50).trim();
            if (rs.getString(51) == null)
                this.StandByFlag45 = null;
            else
                this.StandByFlag45 = rs.getString(51).trim();
            if (rs.getString(52) == null)
                this.StandByFlag46 = null;
            else
                this.StandByFlag46 = rs.getString(52).trim();
            if (rs.getString(53) == null)
                this.StandByFlag47 = null;
            else
                this.StandByFlag47 = rs.getString(53).trim();
            if (rs.getString(54) == null)
                this.StandByFlag48 = null;
            else
                this.StandByFlag48 = rs.getString(54).trim();
            if (rs.getString(55) == null)
                this.StandByFlag49 = null;
            else
                this.StandByFlag49 = rs.getString(55).trim();
            if (rs.getString(56) == null)
                this.StandByFlag50 = null;
            else
                this.StandByFlag50 = rs.getString(56).trim();
            if (rs.getString(57) == null)
                this.LastOperator = null;
            else
                this.LastOperator = rs.getString(57).trim();
            if (rs.getString(58) == null)
                this.CreateOperator = null;
            else
                this.CreateOperator = rs.getString(58).trim();
            this.MakeDate = rs.getDate(59);
            if (rs.getString(60) == null)
                this.MakeTime = null;
            else
                this.MakeTime = rs.getString(60).trim();
        }
        catch (SQLException sqle)
        {
            logger.debug("鏁版嵁搴撲腑琛≒D_LBRiskInfo瀛楁涓暟鍜孲chema涓殑瀛楁涓暟涓嶄竴鑷达紝鎴栨墽琛宒b.executeQuery鏌ヨ鏃舵湭浣跨敤select * from tables");
            // @@閿欒澶勭悊
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoSchema";
            tError.functionName = "setSchema";
            tError.errorMessage = sqle.toString();
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public PD_LBRiskInfoSchema getSchema()
    {
        PD_LBRiskInfoSchema aPD_LBRiskInfoSchema = new PD_LBRiskInfoSchema();
        aPD_LBRiskInfoSchema.setSchema(this);
        return aPD_LBRiskInfoSchema;
    }

    public PD_LBRiskInfoDB getDB()
    {
        PD_LBRiskInfoDB aDBOper = new PD_LBRiskInfoDB();
        aDBOper.setSchema(this);
        return aDBOper;
    }

    /**
     * 鏁版嵁鎵撳寘锛屾寜 XML 鏍煎紡鎵撳寘锛岄『搴忓弬瑙?<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LBRiskInfo鎻忚堪/A>琛ㄥ瓧娈?

     * @return: String 杩斿洖鎵撳寘鍚庡瓧绗︿覆
     **/
    public String encode()
    {
        StringBuffer strReturn = new StringBuffer(256);
        strReturn.append(StrTool.cTrim(SerialNo));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(RiskCode));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(MissionID));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(SubMissionID));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(ActivityID));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(TableCode));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag1));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag2));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag3));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag4));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag5));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag6));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag7));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag8));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag9));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag10));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag11));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag12));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag13));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag14));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag15));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag16));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag17));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag18));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag19));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag20));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag21));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag22));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag23));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag24));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag25));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag26));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag27));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag28));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag29));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag30));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag31));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag32));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag33));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag34));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag35));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag36));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag37));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag38));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag39));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag40));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag41));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag42));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag43));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag44));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag45));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag46));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag47));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag48));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag49));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandByFlag50));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(LastOperator));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(CreateOperator));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(fDate.getString(MakeDate)));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(MakeTime));
        return strReturn.toString();
    }

    /**
     * 鏁版嵁瑙ｅ寘锛岃В鍖呴『搴忓弬瑙?<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LBRiskInfo>鍘嗗彶璁拌处鍑瘉涓昏〃淇℃伅</A>琛ㄥ瓧娈?

     * @param: strMessage String 鍖呭惈涓?鏉＄邯褰曟暟鎹殑瀛楃涓?

     * @return: boolean
     **/
    public boolean decode(String strMessage)
    {
        try
        {
            SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER);
            RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER);
            MissionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER);
            SubMissionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER);
            ActivityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER);
            TableCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER);
            StandByFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER);
            StandByFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER);
            StandByFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER);
            StandByFlag4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER);
            StandByFlag5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER);
            StandByFlag6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER);
            StandByFlag7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER);
            StandByFlag8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER);
            StandByFlag9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER);
            StandByFlag10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER);
            StandByFlag11 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER);
            StandByFlag12 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER);
            StandByFlag13 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER);
            StandByFlag14 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER);
            StandByFlag15 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER);
            StandByFlag16 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER);
            StandByFlag17 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER);
            StandByFlag18 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER);
            StandByFlag19 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER);
            StandByFlag20 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER);
            StandByFlag21 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER);
            StandByFlag22 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER);
            StandByFlag23 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER);
            StandByFlag24 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER);
            StandByFlag25 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER);
            StandByFlag26 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER);
            StandByFlag27 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER);
            StandByFlag28 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER);
            StandByFlag29 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER);
            StandByFlag30 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER);
            StandByFlag31 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER);
            StandByFlag32 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER);
            StandByFlag33 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER);
            StandByFlag34 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER);
            StandByFlag35 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER);
            StandByFlag36 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER);
            StandByFlag37 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER);
            StandByFlag38 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER);
            StandByFlag39 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER);
            StandByFlag40 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER);
            StandByFlag41 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER);
            StandByFlag42 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER);
            StandByFlag43 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER);
            StandByFlag44 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER);
            StandByFlag45 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER);
            StandByFlag46 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER);
            StandByFlag47 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER);
            StandByFlag48 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER);
            StandByFlag49 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER);
            StandByFlag50 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER);
            LastOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER);
            CreateOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER);
            MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER));
            MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER);
        }
        catch (NumberFormatException ex)
        {
            // @@閿欒澶勭悊
            CError tError = new CError();
            tError.moduleName = "PD_LBRiskInfoSchema";
            tError.functionName = "decode";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 鍙栧緱瀵瑰簲浼犲叆鍙傛暟鐨凷tring褰㈠紡鐨勫瓧娈靛??

     * @param: FCode String 甯屾湜鍙栧緱鐨勫瓧娈靛悕
     * @return: String
     * 濡傛灉娌℃湁瀵瑰簲鐨勫瓧娈碉紝杩斿洖""
     * 濡傛灉瀛楁鍊间负绌猴紝杩斿洖"null"
     **/
    public String getV(String FCode)
    {
        String strReturn = "";
        if (FCode.equalsIgnoreCase("SerialNo"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
        }
        if (FCode.equalsIgnoreCase("RiskCode"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
        }
        if (FCode.equalsIgnoreCase("MissionID"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(MissionID));
        }
        if (FCode.equalsIgnoreCase("SubMissionID"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(SubMissionID));
        }
        if (FCode.equalsIgnoreCase("ActivityID"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(ActivityID));
        }
        if (FCode.equalsIgnoreCase("TableCode"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(TableCode));
        }
        if (FCode.equalsIgnoreCase("StandByFlag1"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag1));
        }
        if (FCode.equalsIgnoreCase("StandByFlag2"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag2));
        }
        if (FCode.equalsIgnoreCase("StandByFlag3"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag3));
        }
        if (FCode.equalsIgnoreCase("StandByFlag4"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag4));
        }
        if (FCode.equalsIgnoreCase("StandByFlag5"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag5));
        }
        if (FCode.equalsIgnoreCase("StandByFlag6"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag6));
        }
        if (FCode.equalsIgnoreCase("StandByFlag7"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag7));
        }
        if (FCode.equalsIgnoreCase("StandByFlag8"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag8));
        }
        if (FCode.equalsIgnoreCase("StandByFlag9"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag9));
        }
        if (FCode.equalsIgnoreCase("StandByFlag10"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag10));
        }
        if (FCode.equalsIgnoreCase("StandByFlag11"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag11));
        }
        if (FCode.equalsIgnoreCase("StandByFlag12"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag12));
        }
        if (FCode.equalsIgnoreCase("StandByFlag13"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag13));
        }
        if (FCode.equalsIgnoreCase("StandByFlag14"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag14));
        }
        if (FCode.equalsIgnoreCase("StandByFlag15"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag15));
        }
        if (FCode.equalsIgnoreCase("StandByFlag16"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag16));
        }
        if (FCode.equalsIgnoreCase("StandByFlag17"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag17));
        }
        if (FCode.equalsIgnoreCase("StandByFlag18"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag18));
        }
        if (FCode.equalsIgnoreCase("StandByFlag19"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag19));
        }
        if (FCode.equalsIgnoreCase("StandByFlag20"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag20));
        }
        if (FCode.equalsIgnoreCase("StandByFlag21"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag21));
        }
        if (FCode.equalsIgnoreCase("StandByFlag22"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag22));
        }
        if (FCode.equalsIgnoreCase("StandByFlag23"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag23));
        }
        if (FCode.equalsIgnoreCase("StandByFlag24"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag24));
        }
        if (FCode.equalsIgnoreCase("StandByFlag25"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag25));
        }
        if (FCode.equalsIgnoreCase("StandByFlag26"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag26));
        }
        if (FCode.equalsIgnoreCase("StandByFlag27"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag27));
        }
        if (FCode.equalsIgnoreCase("StandByFlag28"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag28));
        }
        if (FCode.equalsIgnoreCase("StandByFlag29"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag29));
        }
        if (FCode.equalsIgnoreCase("StandByFlag30"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag30));
        }
        if (FCode.equalsIgnoreCase("StandByFlag31"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag31));
        }
        if (FCode.equalsIgnoreCase("StandByFlag32"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag32));
        }
        if (FCode.equalsIgnoreCase("StandByFlag33"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag33));
        }
        if (FCode.equalsIgnoreCase("StandByFlag34"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag34));
        }
        if (FCode.equalsIgnoreCase("StandByFlag35"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag35));
        }
        if (FCode.equalsIgnoreCase("StandByFlag36"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag36));
        }
        if (FCode.equalsIgnoreCase("StandByFlag37"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag37));
        }
        if (FCode.equalsIgnoreCase("StandByFlag38"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag38));
        }
        if (FCode.equalsIgnoreCase("StandByFlag39"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag39));
        }
        if (FCode.equalsIgnoreCase("StandByFlag40"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag40));
        }
        if (FCode.equalsIgnoreCase("StandByFlag41"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag41));
        }
        if (FCode.equalsIgnoreCase("StandByFlag42"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag42));
        }
        if (FCode.equalsIgnoreCase("StandByFlag43"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag43));
        }
        if (FCode.equalsIgnoreCase("StandByFlag44"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag44));
        }
        if (FCode.equalsIgnoreCase("StandByFlag45"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag45));
        }
        if (FCode.equalsIgnoreCase("StandByFlag46"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag46));
        }
        if (FCode.equalsIgnoreCase("StandByFlag47"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag47));
        }
        if (FCode.equalsIgnoreCase("StandByFlag48"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag48));
        }
        if (FCode.equalsIgnoreCase("StandByFlag49"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag49));
        }
        if (FCode.equalsIgnoreCase("StandByFlag50"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag50));
        }
        if (FCode.equalsIgnoreCase("LastOperator"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(LastOperator));
        }
        if (FCode.equalsIgnoreCase("CreateOperator"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(CreateOperator));
        }
        if (FCode.equalsIgnoreCase("MakeDate"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(this.getMakeDate()));
        }
        if (FCode.equalsIgnoreCase("MakeTime"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
        }
        if (strReturn.equals(""))
        {
            strReturn = "null";
        }
        return strReturn;
    }

    /**
     * 鍙栧緱Schema涓寚瀹氱储寮曞?兼墍瀵瑰簲鐨勫瓧娈靛??

     * @param: nFieldIndex int 鎸囧畾鐨勫瓧娈电储寮曞??

     * @return: String
     * 濡傛灉娌℃湁瀵瑰簲鐨勫瓧娈碉紝杩斿洖""
     * 濡傛灉瀛楁鍊间负绌猴紝杩斿洖"null"
     **/
    public String getV(int nFieldIndex)
    {
        String strFieldValue = "";
        switch (nFieldIndex)
        {
            case 0:
                strFieldValue = StrTool.GBKToUnicode(SerialNo);
                break;
            case 1:
                strFieldValue = StrTool.GBKToUnicode(RiskCode);
                break;
            case 2:
                strFieldValue = StrTool.GBKToUnicode(MissionID);
                break;
            case 3:
                strFieldValue = StrTool.GBKToUnicode(SubMissionID);
                break;
            case 4:
                strFieldValue = StrTool.GBKToUnicode(ActivityID);
                break;
            case 5:
                strFieldValue = StrTool.GBKToUnicode(TableCode);
                break;
            case 6:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag1);
                break;
            case 7:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag2);
                break;
            case 8:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag3);
                break;
            case 9:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag4);
                break;
            case 10:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag5);
                break;
            case 11:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag6);
                break;
            case 12:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag7);
                break;
            case 13:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag8);
                break;
            case 14:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag9);
                break;
            case 15:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag10);
                break;
            case 16:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag11);
                break;
            case 17:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag12);
                break;
            case 18:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag13);
                break;
            case 19:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag14);
                break;
            case 20:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag15);
                break;
            case 21:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag16);
                break;
            case 22:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag17);
                break;
            case 23:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag18);
                break;
            case 24:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag19);
                break;
            case 25:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag20);
                break;
            case 26:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag21);
                break;
            case 27:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag22);
                break;
            case 28:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag23);
                break;
            case 29:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag24);
                break;
            case 30:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag25);
                break;
            case 31:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag26);
                break;
            case 32:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag27);
                break;
            case 33:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag28);
                break;
            case 34:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag29);
                break;
            case 35:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag30);
                break;
            case 36:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag31);
                break;
            case 37:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag32);
                break;
            case 38:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag33);
                break;
            case 39:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag34);
                break;
            case 40:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag35);
                break;
            case 41:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag36);
                break;
            case 42:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag37);
                break;
            case 43:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag38);
                break;
            case 44:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag39);
                break;
            case 45:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag40);
                break;
            case 46:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag41);
                break;
            case 47:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag42);
                break;
            case 48:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag43);
                break;
            case 49:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag44);
                break;
            case 50:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag45);
                break;
            case 51:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag46);
                break;
            case 52:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag47);
                break;
            case 53:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag48);
                break;
            case 54:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag49);
                break;
            case 55:
                strFieldValue = StrTool.GBKToUnicode(StandByFlag50);
                break;
            case 56:
                strFieldValue = StrTool.GBKToUnicode(LastOperator);
                break;
            case 57:
                strFieldValue = StrTool.GBKToUnicode(CreateOperator);
                break;
            case 58:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getMakeDate()));
                break;
            case 59:
                strFieldValue = StrTool.GBKToUnicode(MakeTime);
                break;
            default:
                strFieldValue = "";
        }
        if (strFieldValue.equals(""))
        {
            strFieldValue = "null";
        }
        return strFieldValue;
    }

    /**
     * 璁剧疆瀵瑰簲浼犲叆鍙傛暟鐨凷tring褰㈠紡鐨勫瓧娈靛??

     * @param: FCode String 闇?瑕佽祴鍊肩殑瀵硅薄
     * @param: FValue String 瑕佽祴鐨勫??

     * @return: boolean
     **/
    public boolean setV(String FCode, String FValue)
    {
        if (StrTool.cTrim(FCode).equals(""))
            return false;

        if (FCode.equalsIgnoreCase("SerialNo"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                SerialNo = FValue.trim();
            }
            else
                SerialNo = null;
        }
        if (FCode.equalsIgnoreCase("RiskCode"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                RiskCode = FValue.trim();
            }
            else
                RiskCode = null;
        }
        if (FCode.equalsIgnoreCase("MissionID"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                MissionID = FValue.trim();
            }
            else
                MissionID = null;
        }
        if (FCode.equalsIgnoreCase("SubMissionID"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                SubMissionID = FValue.trim();
            }
            else
                SubMissionID = null;
        }
        if (FCode.equalsIgnoreCase("ActivityID"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                ActivityID = FValue.trim();
            }
            else
                ActivityID = null;
        }
        if (FCode.equalsIgnoreCase("TableCode"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                TableCode = FValue.trim();
            }
            else
                TableCode = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag1"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag1 = FValue.trim();
            }
            else
                StandByFlag1 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag2"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag2 = FValue.trim();
            }
            else
                StandByFlag2 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag3"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag3 = FValue.trim();
            }
            else
                StandByFlag3 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag4"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag4 = FValue.trim();
            }
            else
                StandByFlag4 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag5"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag5 = FValue.trim();
            }
            else
                StandByFlag5 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag6"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag6 = FValue.trim();
            }
            else
                StandByFlag6 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag7"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag7 = FValue.trim();
            }
            else
                StandByFlag7 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag8"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag8 = FValue.trim();
            }
            else
                StandByFlag8 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag9"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag9 = FValue.trim();
            }
            else
                StandByFlag9 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag10"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag10 = FValue.trim();
            }
            else
                StandByFlag10 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag11"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag11 = FValue.trim();
            }
            else
                StandByFlag11 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag12"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag12 = FValue.trim();
            }
            else
                StandByFlag12 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag13"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag13 = FValue.trim();
            }
            else
                StandByFlag13 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag14"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag14 = FValue.trim();
            }
            else
                StandByFlag14 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag15"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag15 = FValue.trim();
            }
            else
                StandByFlag15 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag16"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag16 = FValue.trim();
            }
            else
                StandByFlag16 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag17"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag17 = FValue.trim();
            }
            else
                StandByFlag17 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag18"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag18 = FValue.trim();
            }
            else
                StandByFlag18 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag19"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag19 = FValue.trim();
            }
            else
                StandByFlag19 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag20"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag20 = FValue.trim();
            }
            else
                StandByFlag20 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag21"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag21 = FValue.trim();
            }
            else
                StandByFlag21 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag22"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag22 = FValue.trim();
            }
            else
                StandByFlag22 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag23"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag23 = FValue.trim();
            }
            else
                StandByFlag23 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag24"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag24 = FValue.trim();
            }
            else
                StandByFlag24 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag25"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag25 = FValue.trim();
            }
            else
                StandByFlag25 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag26"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag26 = FValue.trim();
            }
            else
                StandByFlag26 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag27"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag27 = FValue.trim();
            }
            else
                StandByFlag27 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag28"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag28 = FValue.trim();
            }
            else
                StandByFlag28 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag29"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag29 = FValue.trim();
            }
            else
                StandByFlag29 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag30"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag30 = FValue.trim();
            }
            else
                StandByFlag30 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag31"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag31 = FValue.trim();
            }
            else
                StandByFlag31 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag32"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag32 = FValue.trim();
            }
            else
                StandByFlag32 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag33"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag33 = FValue.trim();
            }
            else
                StandByFlag33 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag34"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag34 = FValue.trim();
            }
            else
                StandByFlag34 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag35"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag35 = FValue.trim();
            }
            else
                StandByFlag35 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag36"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag36 = FValue.trim();
            }
            else
                StandByFlag36 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag37"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag37 = FValue.trim();
            }
            else
                StandByFlag37 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag38"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag38 = FValue.trim();
            }
            else
                StandByFlag38 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag39"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag39 = FValue.trim();
            }
            else
                StandByFlag39 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag40"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag40 = FValue.trim();
            }
            else
                StandByFlag40 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag41"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag41 = FValue.trim();
            }
            else
                StandByFlag41 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag42"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag42 = FValue.trim();
            }
            else
                StandByFlag42 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag43"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag43 = FValue.trim();
            }
            else
                StandByFlag43 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag44"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag44 = FValue.trim();
            }
            else
                StandByFlag44 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag45"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag45 = FValue.trim();
            }
            else
                StandByFlag45 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag46"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag46 = FValue.trim();
            }
            else
                StandByFlag46 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag47"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag47 = FValue.trim();
            }
            else
                StandByFlag47 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag48"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag48 = FValue.trim();
            }
            else
                StandByFlag48 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag49"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag49 = FValue.trim();
            }
            else
                StandByFlag49 = null;
        }
        if (FCode.equalsIgnoreCase("StandByFlag50"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandByFlag50 = FValue.trim();
            }
            else
                StandByFlag50 = null;
        }
        if (FCode.equalsIgnoreCase("LastOperator"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                LastOperator = FValue.trim();
            }
            else
                LastOperator = null;
        }
        if (FCode.equalsIgnoreCase("CreateOperator"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                CreateOperator = FValue.trim();
            }
            else
                CreateOperator = null;
        }
        if (FCode.equalsIgnoreCase("MakeDate"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                MakeDate = fDate.getDate(FValue);
            }
            else
                MakeDate = null;
        }
        if (FCode.equalsIgnoreCase("MakeTime"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                MakeTime = FValue.trim();
            }
            else
                MakeTime = null;
        }
        return true;
    }

    public boolean equals(Object otherObject)
    {
        if (this == otherObject)
            return true;
        if (otherObject == null)
            return false;
        if (getClass() != otherObject.getClass())
            return false;
        PD_LBRiskInfoSchema other = (PD_LBRiskInfoSchema) otherObject;
        return
                (SerialNo == null ? other.getSerialNo() == null : SerialNo.equals(other.getSerialNo()))
                && (RiskCode == null ? other.getRiskCode() == null : RiskCode.equals(other.getRiskCode()))
                && (MissionID == null ? other.getMissionID() == null : MissionID.equals(other.getMissionID()))
                && (SubMissionID == null ? other.getSubMissionID() == null : SubMissionID.equals(other.getSubMissionID()))
                && (ActivityID == null ? other.getActivityID() == null : ActivityID.equals(other.getActivityID()))
                && (TableCode == null ? other.getTableCode() == null : TableCode.equals(other.getTableCode()))
                && (StandByFlag1 == null ? other.getStandByFlag1() == null : StandByFlag1.equals(other.getStandByFlag1()))
                && (StandByFlag2 == null ? other.getStandByFlag2() == null : StandByFlag2.equals(other.getStandByFlag2()))
                && (StandByFlag3 == null ? other.getStandByFlag3() == null : StandByFlag3.equals(other.getStandByFlag3()))
                && (StandByFlag4 == null ? other.getStandByFlag4() == null : StandByFlag4.equals(other.getStandByFlag4()))
                && (StandByFlag5 == null ? other.getStandByFlag5() == null : StandByFlag5.equals(other.getStandByFlag5()))
                && (StandByFlag6 == null ? other.getStandByFlag6() == null : StandByFlag6.equals(other.getStandByFlag6()))
                && (StandByFlag7 == null ? other.getStandByFlag7() == null : StandByFlag7.equals(other.getStandByFlag7()))
                && (StandByFlag8 == null ? other.getStandByFlag8() == null : StandByFlag8.equals(other.getStandByFlag8()))
                && (StandByFlag9 == null ? other.getStandByFlag9() == null : StandByFlag9.equals(other.getStandByFlag9()))
                && (StandByFlag10 == null ? other.getStandByFlag10() == null : StandByFlag10.equals(other.getStandByFlag10()))
                && (StandByFlag11 == null ? other.getStandByFlag11() == null : StandByFlag11.equals(other.getStandByFlag11()))
                && (StandByFlag12 == null ? other.getStandByFlag12() == null : StandByFlag12.equals(other.getStandByFlag12()))
                && (StandByFlag13 == null ? other.getStandByFlag13() == null : StandByFlag13.equals(other.getStandByFlag13()))
                && (StandByFlag14 == null ? other.getStandByFlag14() == null : StandByFlag14.equals(other.getStandByFlag14()))
                && (StandByFlag15 == null ? other.getStandByFlag15() == null : StandByFlag15.equals(other.getStandByFlag15()))
                && (StandByFlag16 == null ? other.getStandByFlag16() == null : StandByFlag16.equals(other.getStandByFlag16()))
                && (StandByFlag17 == null ? other.getStandByFlag17() == null : StandByFlag17.equals(other.getStandByFlag17()))
                && (StandByFlag18 == null ? other.getStandByFlag18() == null : StandByFlag18.equals(other.getStandByFlag18()))
                && (StandByFlag19 == null ? other.getStandByFlag19() == null : StandByFlag19.equals(other.getStandByFlag19()))
                && (StandByFlag20 == null ? other.getStandByFlag20() == null : StandByFlag20.equals(other.getStandByFlag20()))
                && (StandByFlag21 == null ? other.getStandByFlag21() == null : StandByFlag21.equals(other.getStandByFlag21()))
                && (StandByFlag22 == null ? other.getStandByFlag22() == null : StandByFlag22.equals(other.getStandByFlag22()))
                && (StandByFlag23 == null ? other.getStandByFlag23() == null : StandByFlag23.equals(other.getStandByFlag23()))
                && (StandByFlag24 == null ? other.getStandByFlag24() == null : StandByFlag24.equals(other.getStandByFlag24()))
                && (StandByFlag25 == null ? other.getStandByFlag25() == null : StandByFlag25.equals(other.getStandByFlag25()))
                && (StandByFlag26 == null ? other.getStandByFlag26() == null : StandByFlag26.equals(other.getStandByFlag26()))
                && (StandByFlag27 == null ? other.getStandByFlag27() == null : StandByFlag27.equals(other.getStandByFlag27()))
                && (StandByFlag28 == null ? other.getStandByFlag28() == null : StandByFlag28.equals(other.getStandByFlag28()))
                && (StandByFlag29 == null ? other.getStandByFlag29() == null : StandByFlag29.equals(other.getStandByFlag29()))
                && (StandByFlag30 == null ? other.getStandByFlag30() == null : StandByFlag30.equals(other.getStandByFlag30()))
                && (StandByFlag31 == null ? other.getStandByFlag31() == null : StandByFlag31.equals(other.getStandByFlag31()))
                && (StandByFlag32 == null ? other.getStandByFlag32() == null : StandByFlag32.equals(other.getStandByFlag32()))
                && (StandByFlag33 == null ? other.getStandByFlag33() == null : StandByFlag33.equals(other.getStandByFlag33()))
                && (StandByFlag34 == null ? other.getStandByFlag34() == null : StandByFlag34.equals(other.getStandByFlag34()))
                && (StandByFlag35 == null ? other.getStandByFlag35() == null : StandByFlag35.equals(other.getStandByFlag35()))
                && (StandByFlag36 == null ? other.getStandByFlag36() == null : StandByFlag36.equals(other.getStandByFlag36()))
                && (StandByFlag37 == null ? other.getStandByFlag37() == null : StandByFlag37.equals(other.getStandByFlag37()))
                && (StandByFlag38 == null ? other.getStandByFlag38() == null : StandByFlag38.equals(other.getStandByFlag38()))
                && (StandByFlag39 == null ? other.getStandByFlag39() == null : StandByFlag39.equals(other.getStandByFlag39()))
                && (StandByFlag40 == null ? other.getStandByFlag40() == null : StandByFlag40.equals(other.getStandByFlag40()))
                && (StandByFlag41 == null ? other.getStandByFlag41() == null : StandByFlag41.equals(other.getStandByFlag41()))
                && (StandByFlag42 == null ? other.getStandByFlag42() == null : StandByFlag42.equals(other.getStandByFlag42()))
                && (StandByFlag43 == null ? other.getStandByFlag43() == null : StandByFlag43.equals(other.getStandByFlag43()))
                && (StandByFlag44 == null ? other.getStandByFlag44() == null : StandByFlag44.equals(other.getStandByFlag44()))
                && (StandByFlag45 == null ? other.getStandByFlag45() == null : StandByFlag45.equals(other.getStandByFlag45()))
                && (StandByFlag46 == null ? other.getStandByFlag46() == null : StandByFlag46.equals(other.getStandByFlag46()))
                && (StandByFlag47 == null ? other.getStandByFlag47() == null : StandByFlag47.equals(other.getStandByFlag47()))
                && (StandByFlag48 == null ? other.getStandByFlag48() == null : StandByFlag48.equals(other.getStandByFlag48()))
                && (StandByFlag49 == null ? other.getStandByFlag49() == null : StandByFlag49.equals(other.getStandByFlag49()))
                && (StandByFlag50 == null ? other.getStandByFlag50() == null : StandByFlag50.equals(other.getStandByFlag50()))
                && (LastOperator == null ? other.getLastOperator() == null : LastOperator.equals(other.getLastOperator()))
                && (CreateOperator == null ? other.getCreateOperator() == null : CreateOperator.equals(other.getCreateOperator()))
                && (MakeDate == null ? other.getMakeDate() == null : fDate.getString(MakeDate).equals(other.getMakeDate()))
                && (MakeTime == null ? other.getMakeTime() == null : MakeTime.equals(other.getMakeTime()));
    }

    /**
     * 鍙栧緱Schema鎷ユ湁瀛楁鐨勬暟閲?

     * @return: int
     **/
    public int getFieldCount()
    {
        return FIELDNUM;
    }

    /**
     * 鍙栧緱Schema涓寚瀹氬瓧娈靛悕鎵?瀵瑰簲鐨勭储寮曞??

     * 濡傛灉娌℃湁瀵瑰簲鐨勫瓧娈碉紝杩斿洖-1
     * @param: strFieldName String
     * @return: int
     **/
    public int getFieldIndex(String strFieldName)
    {
        if (strFieldName.equals("SerialNo"))
        {
            return 0;
        }
        if (strFieldName.equals("RiskCode"))
        {
            return 1;
        }
        if (strFieldName.equals("MissionID"))
        {
            return 2;
        }
        if (strFieldName.equals("SubMissionID"))
        {
            return 3;
        }
        if (strFieldName.equals("ActivityID"))
        {
            return 4;
        }
        if (strFieldName.equals("TableCode"))
        {
            return 5;
        }
        if (strFieldName.equals("StandByFlag1"))
        {
            return 6;
        }
        if (strFieldName.equals("StandByFlag2"))
        {
            return 7;
        }
        if (strFieldName.equals("StandByFlag3"))
        {
            return 8;
        }
        if (strFieldName.equals("StandByFlag4"))
        {
            return 9;
        }
        if (strFieldName.equals("StandByFlag5"))
        {
            return 10;
        }
        if (strFieldName.equals("StandByFlag6"))
        {
            return 11;
        }
        if (strFieldName.equals("StandByFlag7"))
        {
            return 12;
        }
        if (strFieldName.equals("StandByFlag8"))
        {
            return 13;
        }
        if (strFieldName.equals("StandByFlag9"))
        {
            return 14;
        }
        if (strFieldName.equals("StandByFlag10"))
        {
            return 15;
        }
        if (strFieldName.equals("StandByFlag11"))
        {
            return 16;
        }
        if (strFieldName.equals("StandByFlag12"))
        {
            return 17;
        }
        if (strFieldName.equals("StandByFlag13"))
        {
            return 18;
        }
        if (strFieldName.equals("StandByFlag14"))
        {
            return 19;
        }
        if (strFieldName.equals("StandByFlag15"))
        {
            return 20;
        }
        if (strFieldName.equals("StandByFlag16"))
        {
            return 21;
        }
        if (strFieldName.equals("StandByFlag17"))
        {
            return 22;
        }
        if (strFieldName.equals("StandByFlag18"))
        {
            return 23;
        }
        if (strFieldName.equals("StandByFlag19"))
        {
            return 24;
        }
        if (strFieldName.equals("StandByFlag20"))
        {
            return 25;
        }
        if (strFieldName.equals("StandByFlag21"))
        {
            return 26;
        }
        if (strFieldName.equals("StandByFlag22"))
        {
            return 27;
        }
        if (strFieldName.equals("StandByFlag23"))
        {
            return 28;
        }
        if (strFieldName.equals("StandByFlag24"))
        {
            return 29;
        }
        if (strFieldName.equals("StandByFlag25"))
        {
            return 30;
        }
        if (strFieldName.equals("StandByFlag26"))
        {
            return 31;
        }
        if (strFieldName.equals("StandByFlag27"))
        {
            return 32;
        }
        if (strFieldName.equals("StandByFlag28"))
        {
            return 33;
        }
        if (strFieldName.equals("StandByFlag29"))
        {
            return 34;
        }
        if (strFieldName.equals("StandByFlag30"))
        {
            return 35;
        }
        if (strFieldName.equals("StandByFlag31"))
        {
            return 36;
        }
        if (strFieldName.equals("StandByFlag32"))
        {
            return 37;
        }
        if (strFieldName.equals("StandByFlag33"))
        {
            return 38;
        }
        if (strFieldName.equals("StandByFlag34"))
        {
            return 39;
        }
        if (strFieldName.equals("StandByFlag35"))
        {
            return 40;
        }
        if (strFieldName.equals("StandByFlag36"))
        {
            return 41;
        }
        if (strFieldName.equals("StandByFlag37"))
        {
            return 42;
        }
        if (strFieldName.equals("StandByFlag38"))
        {
            return 43;
        }
        if (strFieldName.equals("StandByFlag39"))
        {
            return 44;
        }
        if (strFieldName.equals("StandByFlag40"))
        {
            return 45;
        }
        if (strFieldName.equals("StandByFlag41"))
        {
            return 46;
        }
        if (strFieldName.equals("StandByFlag42"))
        {
            return 47;
        }
        if (strFieldName.equals("StandByFlag43"))
        {
            return 48;
        }
        if (strFieldName.equals("StandByFlag44"))
        {
            return 49;
        }
        if (strFieldName.equals("StandByFlag45"))
        {
            return 50;
        }
        if (strFieldName.equals("StandByFlag46"))
        {
            return 51;
        }
        if (strFieldName.equals("StandByFlag47"))
        {
            return 52;
        }
        if (strFieldName.equals("StandByFlag48"))
        {
            return 53;
        }
        if (strFieldName.equals("StandByFlag49"))
        {
            return 54;
        }
        if (strFieldName.equals("StandByFlag50"))
        {
            return 55;
        }
        if (strFieldName.equals("LastOperator"))
        {
            return 56;
        }
        if (strFieldName.equals("CreateOperator"))
        {
            return 57;
        }
        if (strFieldName.equals("MakeDate"))
        {
            return 58;
        }
        if (strFieldName.equals("MakeTime"))
        {
            return 59;
        }
        return -1;
    }

    /**
     * 鍙栧緱Schema涓寚瀹氱储寮曞?兼墍瀵瑰簲鐨勫瓧娈靛悕
     * 濡傛灉娌℃湁瀵瑰簲鐨勫瓧娈碉紝杩斿洖""
     * @param: nFieldIndex int
     * @return: String
     **/
    public String getFieldName(int nFieldIndex)
    {
        String strFieldName = "";
        switch (nFieldIndex)
        {
            case 0:
                strFieldName = "SerialNo";
                break;
            case 1:
                strFieldName = "RiskCode";
                break;
            case 2:
                strFieldName = "MissionID";
                break;
            case 3:
                strFieldName = "SubMissionID";
                break;
            case 4:
                strFieldName = "ActivityID";
                break;
            case 5:
                strFieldName = "TableCode";
                break;
            case 6:
                strFieldName = "StandByFlag1";
                break;
            case 7:
                strFieldName = "StandByFlag2";
                break;
            case 8:
                strFieldName = "StandByFlag3";
                break;
            case 9:
                strFieldName = "StandByFlag4";
                break;
            case 10:
                strFieldName = "StandByFlag5";
                break;
            case 11:
                strFieldName = "StandByFlag6";
                break;
            case 12:
                strFieldName = "StandByFlag7";
                break;
            case 13:
                strFieldName = "StandByFlag8";
                break;
            case 14:
                strFieldName = "StandByFlag9";
                break;
            case 15:
                strFieldName = "StandByFlag10";
                break;
            case 16:
                strFieldName = "StandByFlag11";
                break;
            case 17:
                strFieldName = "StandByFlag12";
                break;
            case 18:
                strFieldName = "StandByFlag13";
                break;
            case 19:
                strFieldName = "StandByFlag14";
                break;
            case 20:
                strFieldName = "StandByFlag15";
                break;
            case 21:
                strFieldName = "StandByFlag16";
                break;
            case 22:
                strFieldName = "StandByFlag17";
                break;
            case 23:
                strFieldName = "StandByFlag18";
                break;
            case 24:
                strFieldName = "StandByFlag19";
                break;
            case 25:
                strFieldName = "StandByFlag20";
                break;
            case 26:
                strFieldName = "StandByFlag21";
                break;
            case 27:
                strFieldName = "StandByFlag22";
                break;
            case 28:
                strFieldName = "StandByFlag23";
                break;
            case 29:
                strFieldName = "StandByFlag24";
                break;
            case 30:
                strFieldName = "StandByFlag25";
                break;
            case 31:
                strFieldName = "StandByFlag26";
                break;
            case 32:
                strFieldName = "StandByFlag27";
                break;
            case 33:
                strFieldName = "StandByFlag28";
                break;
            case 34:
                strFieldName = "StandByFlag29";
                break;
            case 35:
                strFieldName = "StandByFlag30";
                break;
            case 36:
                strFieldName = "StandByFlag31";
                break;
            case 37:
                strFieldName = "StandByFlag32";
                break;
            case 38:
                strFieldName = "StandByFlag33";
                break;
            case 39:
                strFieldName = "StandByFlag34";
                break;
            case 40:
                strFieldName = "StandByFlag35";
                break;
            case 41:
                strFieldName = "StandByFlag36";
                break;
            case 42:
                strFieldName = "StandByFlag37";
                break;
            case 43:
                strFieldName = "StandByFlag38";
                break;
            case 44:
                strFieldName = "StandByFlag39";
                break;
            case 45:
                strFieldName = "StandByFlag40";
                break;
            case 46:
                strFieldName = "StandByFlag41";
                break;
            case 47:
                strFieldName = "StandByFlag42";
                break;
            case 48:
                strFieldName = "StandByFlag43";
                break;
            case 49:
                strFieldName = "StandByFlag44";
                break;
            case 50:
                strFieldName = "StandByFlag45";
                break;
            case 51:
                strFieldName = "StandByFlag46";
                break;
            case 52:
                strFieldName = "StandByFlag47";
                break;
            case 53:
                strFieldName = "StandByFlag48";
                break;
            case 54:
                strFieldName = "StandByFlag49";
                break;
            case 55:
                strFieldName = "StandByFlag50";
                break;
            case 56:
                strFieldName = "LastOperator";
                break;
            case 57:
                strFieldName = "CreateOperator";
                break;
            case 58:
                strFieldName = "MakeDate";
                break;
            case 59:
                strFieldName = "MakeTime";
                break;
            default:
                strFieldName = "";
        }
        return strFieldName;
    }

    /**
     * 鍙栧緱Schema涓寚瀹氬瓧娈靛悕鎵?瀵瑰簲鐨勫瓧娈电被鍨?

     * 濡傛灉娌℃湁瀵瑰簲鐨勫瓧娈碉紝杩斿洖Schema.TYPE_NOFOUND
     * @param: strFieldName String
     * @return: int
     **/
    public int getFieldType(String strFieldName)
    {
        if (strFieldName.equals("SerialNo"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("RiskCode"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("MissionID"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("SubMissionID"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("ActivityID"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("TableCode"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag1"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag2"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag3"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag4"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag5"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag6"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag7"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag8"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag9"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag10"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag11"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag12"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag13"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag14"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag15"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag16"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag17"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag18"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag19"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag20"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag21"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag22"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag23"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag24"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag25"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag26"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag27"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag28"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag29"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag30"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag31"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag32"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag33"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag34"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag35"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag36"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag37"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag38"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag39"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag40"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag41"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag42"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag43"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag44"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag45"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag46"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag47"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag48"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag49"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandByFlag50"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("LastOperator"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("CreateOperator"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("MakeDate"))
        {
            return Schema.TYPE_DATE;
        }
        if (strFieldName.equals("MakeTime"))
        {
            return Schema.TYPE_STRING;
        }
        return Schema.TYPE_NOFOUND;
    }

    /**
     * 鍙栧緱Schema涓寚瀹氱储寮曞?兼墍瀵瑰簲鐨勫瓧娈电被鍨?

     * 濡傛灉娌℃湁瀵瑰簲鐨勫瓧娈碉紝杩斿洖Schema.TYPE_NOFOUND
     * @param: nFieldIndex int
     * @return: int
     **/
    public int getFieldType(int nFieldIndex)
    {
        int nFieldType = Schema.TYPE_NOFOUND;
        switch (nFieldIndex)
        {
            case 0:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 1:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 2:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 3:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 4:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 5:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 6:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 7:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 8:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 9:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 10:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 11:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 12:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 13:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 14:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 15:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 16:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 17:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 18:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 19:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 20:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 21:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 22:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 23:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 24:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 25:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 26:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 27:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 28:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 29:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 30:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 31:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 32:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 33:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 34:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 35:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 36:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 37:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 38:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 39:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 40:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 41:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 42:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 43:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 44:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 45:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 46:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 47:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 48:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 49:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 50:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 51:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 52:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 53:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 54:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 55:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 56:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 57:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 58:
                nFieldType = Schema.TYPE_DATE;
                break;
            case 59:
                nFieldType = Schema.TYPE_STRING;
                break;
            default:
                nFieldType = Schema.TYPE_NOFOUND;
        }
        return nFieldType;
    }
}
