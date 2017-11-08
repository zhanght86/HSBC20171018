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
import com.sinosoft.lis.db.PD_LFRiskDB;

/**
 * <p>ClassName: PD_LFRiskSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: 产品定义平台_PDM
 * @author：Makerx
 * @CreateDate：2009-03-04
 */
public class PD_LFRiskSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LFRiskSchema.class);

    // @Field
    /** 险种编码 */
    private String RiskCode;
    /** 险种版本 */
    private String RiskVer;
    /** 险种名称 */
    private String RiskName;
    /** 险种分类 */
    private String RiskType;
    /** 险种类别 */
    private String RiskPeriod;
    /** 主附险标记 */
    private String SubRiskFlag;
    /** 寿险分类 */
    private String LifeType;
    /** 险种事故责任分类（寿险） */
    private String RiskDutyType;
    /** 健康险和意外险期限分类 */
    private String RiskYearType;
    /** 健康险细分 */
    private String HealthType;
    /** 意外险细分 */
    private String AccidentType;
    /** 养老险细分 */
    private String EndowmentFlag;
    /** 养老险细分1 */
    private String EndowmentType1;
    /** 养老险细分2 */
    private String EndowmentType2;
    /** 险种的销售渠道 */
    private String RiskSaleChnl;
    /** 险种的团个单标志 */
    private String RiskGrpFlag;
    /** 手续费销售渠道 */
    private String ChargeSaleChnl;
    /** 操作员 */
    private String Operator;
    /** 入机日期 */
    private Date MakeDate;
    /** 入机时间 */
    private String MakeTime;
    /** 最后一次修改日期 */
    private Date ModifyDate;
    /** 最后一次修改时间 */
    private String ModifyTime;
    /** Standbyflag1 */
    private String Standbyflag1;
    /** Standbyflag2 */
    private String Standbyflag2;
    /** Standbyflag3 */
    private int Standbyflag3;
    /** Standbyflag4 */
    private int Standbyflag4;
    /** Standbyflag5 */
    private double Standbyflag5;
    /** Standbyflag6 */
    private double Standbyflag6;

    public static final int FIELDNUM = 28; // 数据库表的字段个数

    private static String[] PK; // 主键

    private FDate fDate = new FDate(); // 处理日期

    public CErrors mErrors; // 错误信息

    // @Constructor
    public PD_LFRiskSchema()
    {
        mErrors = new CErrors();
        String[] pk = new String[1];
        pk[0] = "RiskCode";
        PK = pk;
    }

    /**
     * Schema克隆
     * @return Object
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException
    {
        PD_LFRiskSchema cloned = (PD_LFRiskSchema)super.clone();
        cloned.fDate = (FDate) fDate.clone();
        cloned.mErrors = (CErrors) mErrors.clone();
        return cloned;
    }

    // @Method
    public String[] getPK()
    {
        return PK;
    }

    public String getRiskCode()
    {
        return RiskCode;
    }

    public void setRiskCode(String aRiskCode)
    {
        RiskCode = aRiskCode;
    }

    public String getRiskVer()
    {
        return RiskVer;
    }

    public void setRiskVer(String aRiskVer)
    {
        RiskVer = aRiskVer;
    }

    public String getRiskName()
    {
        return RiskName;
    }

    public void setRiskName(String aRiskName)
    {
        RiskName = aRiskName;
    }

    public String getRiskType()
    {
        return RiskType;
    }

    public void setRiskType(String aRiskType)
    {
        RiskType = aRiskType;
    }

    public String getRiskPeriod()
    {
        return RiskPeriod;
    }

    public void setRiskPeriod(String aRiskPeriod)
    {
        RiskPeriod = aRiskPeriod;
    }

    public String getSubRiskFlag()
    {
        return SubRiskFlag;
    }

    public void setSubRiskFlag(String aSubRiskFlag)
    {
        SubRiskFlag = aSubRiskFlag;
    }

    public String getLifeType()
    {
        return LifeType;
    }

    public void setLifeType(String aLifeType)
    {
        LifeType = aLifeType;
    }

    public String getRiskDutyType()
    {
        return RiskDutyType;
    }

    public void setRiskDutyType(String aRiskDutyType)
    {
        RiskDutyType = aRiskDutyType;
    }

    public String getRiskYearType()
    {
        return RiskYearType;
    }

    public void setRiskYearType(String aRiskYearType)
    {
        RiskYearType = aRiskYearType;
    }

    public String getHealthType()
    {
        return HealthType;
    }

    public void setHealthType(String aHealthType)
    {
        HealthType = aHealthType;
    }

    public String getAccidentType()
    {
        return AccidentType;
    }

    public void setAccidentType(String aAccidentType)
    {
        AccidentType = aAccidentType;
    }

    public String getEndowmentFlag()
    {
        return EndowmentFlag;
    }

    public void setEndowmentFlag(String aEndowmentFlag)
    {
        EndowmentFlag = aEndowmentFlag;
    }

    public String getEndowmentType1()
    {
        return EndowmentType1;
    }

    public void setEndowmentType1(String aEndowmentType1)
    {
        EndowmentType1 = aEndowmentType1;
    }

    public String getEndowmentType2()
    {
        return EndowmentType2;
    }

    public void setEndowmentType2(String aEndowmentType2)
    {
        EndowmentType2 = aEndowmentType2;
    }

    public String getRiskSaleChnl()
    {
        return RiskSaleChnl;
    }

    public void setRiskSaleChnl(String aRiskSaleChnl)
    {
        RiskSaleChnl = aRiskSaleChnl;
    }

    public String getRiskGrpFlag()
    {
        return RiskGrpFlag;
    }

    public void setRiskGrpFlag(String aRiskGrpFlag)
    {
        RiskGrpFlag = aRiskGrpFlag;
    }

    public String getChargeSaleChnl()
    {
        return ChargeSaleChnl;
    }

    public void setChargeSaleChnl(String aChargeSaleChnl)
    {
        ChargeSaleChnl = aChargeSaleChnl;
    }

    public String getOperator()
    {
        return Operator;
    }

    public void setOperator(String aOperator)
    {
        Operator = aOperator;
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

    public String getModifyDate()
    {
        if (ModifyDate != null)
            return fDate.getString(ModifyDate);
        else
            return null;
    }

    public void setModifyDate(Date aModifyDate)
    {
        ModifyDate = aModifyDate;
    }

    public void setModifyDate(String aModifyDate)
    {
        if (aModifyDate != null && !aModifyDate.equals(""))
        {
            ModifyDate = fDate.getDate(aModifyDate);
        }
        else
            ModifyDate = null;
    }

    public String getModifyTime()
    {
        return ModifyTime;
    }

    public void setModifyTime(String aModifyTime)
    {
        ModifyTime = aModifyTime;
    }

    public String getStandbyflag1()
    {
        return Standbyflag1;
    }

    public void setStandbyflag1(String aStandbyflag1)
    {
        Standbyflag1 = aStandbyflag1;
    }

    public String getStandbyflag2()
    {
        return Standbyflag2;
    }

    public void setStandbyflag2(String aStandbyflag2)
    {
        Standbyflag2 = aStandbyflag2;
    }

    public int getStandbyflag3()
    {
        return Standbyflag3;
    }

    public void setStandbyflag3(int aStandbyflag3)
    {
        Standbyflag3 = aStandbyflag3;
    }

    public void setStandbyflag3(String aStandbyflag3)
    {
        if (aStandbyflag3 != null && !aStandbyflag3.equals(""))
        {
            Integer tInteger = new Integer(aStandbyflag3);
            int i = tInteger.intValue();
            Standbyflag3 = i;
        }
    }

    public int getStandbyflag4()
    {
        return Standbyflag4;
    }

    public void setStandbyflag4(int aStandbyflag4)
    {
        Standbyflag4 = aStandbyflag4;
    }

    public void setStandbyflag4(String aStandbyflag4)
    {
        if (aStandbyflag4 != null && !aStandbyflag4.equals(""))
        {
            Integer tInteger = new Integer(aStandbyflag4);
            int i = tInteger.intValue();
            Standbyflag4 = i;
        }
    }

    public double getStandbyflag5()
    {
        return Standbyflag5;
    }

    public void setStandbyflag5(double aStandbyflag5)
    {
        Standbyflag5 = aStandbyflag5;
    }

    public void setStandbyflag5(String aStandbyflag5)
    {
        if (aStandbyflag5 != null && !aStandbyflag5.equals(""))
        {
            Double tDouble = new Double(aStandbyflag5);
            Standbyflag5 = tDouble.doubleValue();
        }
    }

    public double getStandbyflag6()
    {
        return Standbyflag6;
    }

    public void setStandbyflag6(double aStandbyflag6)
    {
        Standbyflag6 = aStandbyflag6;
    }

    public void setStandbyflag6(String aStandbyflag6)
    {
        if (aStandbyflag6 != null && !aStandbyflag6.equals(""))
        {
            Double tDouble = new Double(aStandbyflag6);
            Standbyflag6 = tDouble.doubleValue();
        }
    }

    /**
     * 使用另外一个 PD_LFRiskSchema 对象给 Schema 赋值
     * @param: aPD_LFRiskSchema PD_LFRiskSchema
     **/
    public void setSchema(PD_LFRiskSchema aPD_LFRiskSchema)
    {
        this.RiskCode = aPD_LFRiskSchema.getRiskCode();
        this.RiskVer = aPD_LFRiskSchema.getRiskVer();
        this.RiskName = aPD_LFRiskSchema.getRiskName();
        this.RiskType = aPD_LFRiskSchema.getRiskType();
        this.RiskPeriod = aPD_LFRiskSchema.getRiskPeriod();
        this.SubRiskFlag = aPD_LFRiskSchema.getSubRiskFlag();
        this.LifeType = aPD_LFRiskSchema.getLifeType();
        this.RiskDutyType = aPD_LFRiskSchema.getRiskDutyType();
        this.RiskYearType = aPD_LFRiskSchema.getRiskYearType();
        this.HealthType = aPD_LFRiskSchema.getHealthType();
        this.AccidentType = aPD_LFRiskSchema.getAccidentType();
        this.EndowmentFlag = aPD_LFRiskSchema.getEndowmentFlag();
        this.EndowmentType1 = aPD_LFRiskSchema.getEndowmentType1();
        this.EndowmentType2 = aPD_LFRiskSchema.getEndowmentType2();
        this.RiskSaleChnl = aPD_LFRiskSchema.getRiskSaleChnl();
        this.RiskGrpFlag = aPD_LFRiskSchema.getRiskGrpFlag();
        this.ChargeSaleChnl = aPD_LFRiskSchema.getChargeSaleChnl();
        this.Operator = aPD_LFRiskSchema.getOperator();
        this.MakeDate = fDate.getDate(aPD_LFRiskSchema.getMakeDate());
        this.MakeTime = aPD_LFRiskSchema.getMakeTime();
        this.ModifyDate = fDate.getDate(aPD_LFRiskSchema.getModifyDate());
        this.ModifyTime = aPD_LFRiskSchema.getModifyTime();
        this.Standbyflag1 = aPD_LFRiskSchema.getStandbyflag1();
        this.Standbyflag2 = aPD_LFRiskSchema.getStandbyflag2();
        this.Standbyflag3 = aPD_LFRiskSchema.getStandbyflag3();
        this.Standbyflag4 = aPD_LFRiskSchema.getStandbyflag4();
        this.Standbyflag5 = aPD_LFRiskSchema.getStandbyflag5();
        this.Standbyflag6 = aPD_LFRiskSchema.getStandbyflag6();
    }

    /**
     * 使用 ResultSet 中的第 i 行给 Schema 赋值
     * @param: rs ResultSet
     * @param: i int
     * @return: boolean
     **/
    public boolean setSchema(ResultSet rs, int i)
    {
        try
        {
            //rs.absolute(i); // 非滚动游标
            if (rs.getString(1) == null)
                this.RiskCode = null;
            else
                this.RiskCode = rs.getString(1).trim();
            if (rs.getString(2) == null)
                this.RiskVer = null;
            else
                this.RiskVer = rs.getString(2).trim();
            if (rs.getString(3) == null)
                this.RiskName = null;
            else
                this.RiskName = rs.getString(3).trim();
            if (rs.getString(4) == null)
                this.RiskType = null;
            else
                this.RiskType = rs.getString(4).trim();
            if (rs.getString(5) == null)
                this.RiskPeriod = null;
            else
                this.RiskPeriod = rs.getString(5).trim();
            if (rs.getString(6) == null)
                this.SubRiskFlag = null;
            else
                this.SubRiskFlag = rs.getString(6).trim();
            if (rs.getString(7) == null)
                this.LifeType = null;
            else
                this.LifeType = rs.getString(7).trim();
            if (rs.getString(8) == null)
                this.RiskDutyType = null;
            else
                this.RiskDutyType = rs.getString(8).trim();
            if (rs.getString(9) == null)
                this.RiskYearType = null;
            else
                this.RiskYearType = rs.getString(9).trim();
            if (rs.getString(10) == null)
                this.HealthType = null;
            else
                this.HealthType = rs.getString(10).trim();
            if (rs.getString(11) == null)
                this.AccidentType = null;
            else
                this.AccidentType = rs.getString(11).trim();
            if (rs.getString(12) == null)
                this.EndowmentFlag = null;
            else
                this.EndowmentFlag = rs.getString(12).trim();
            if (rs.getString(13) == null)
                this.EndowmentType1 = null;
            else
                this.EndowmentType1 = rs.getString(13).trim();
            if (rs.getString(14) == null)
                this.EndowmentType2 = null;
            else
                this.EndowmentType2 = rs.getString(14).trim();
            if (rs.getString(15) == null)
                this.RiskSaleChnl = null;
            else
                this.RiskSaleChnl = rs.getString(15).trim();
            if (rs.getString(16) == null)
                this.RiskGrpFlag = null;
            else
                this.RiskGrpFlag = rs.getString(16).trim();
            if (rs.getString(17) == null)
                this.ChargeSaleChnl = null;
            else
                this.ChargeSaleChnl = rs.getString(17).trim();
            if (rs.getString(18) == null)
                this.Operator = null;
            else
                this.Operator = rs.getString(18).trim();
            this.MakeDate = rs.getDate(19);
            if (rs.getString(20) == null)
                this.MakeTime = null;
            else
                this.MakeTime = rs.getString(20).trim();
            this.ModifyDate = rs.getDate(21);
            if (rs.getString(22) == null)
                this.ModifyTime = null;
            else
                this.ModifyTime = rs.getString(22).trim();
            if (rs.getString(23) == null)
                this.Standbyflag1 = null;
            else
                this.Standbyflag1 = rs.getString(23).trim();
            if (rs.getString(24) == null)
                this.Standbyflag2 = null;
            else
                this.Standbyflag2 = rs.getString(24).trim();
            this.Standbyflag3 = rs.getInt(25);
            this.Standbyflag4 = rs.getInt(26);
            this.Standbyflag5 = rs.getDouble(27);
            this.Standbyflag6 = rs.getDouble(28);
        }
        catch (SQLException sqle)
        {
            logger.debug("数据库中表PD_LFRisk字段个数和Schema中的字段个数不一致，或执行db.executeQuery查询时未使用select * from tables");
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_LFRiskSchema";
            tError.functionName = "setSchema";
            tError.errorMessage = sqle.toString();
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public PD_LFRiskSchema getSchema()
    {
        PD_LFRiskSchema aPD_LFRiskSchema = new PD_LFRiskSchema();
        aPD_LFRiskSchema.setSchema(this);
        return aPD_LFRiskSchema;
    }

    public PD_LFRiskDB getDB()
    {
        PD_LFRiskDB aDBOper = new PD_LFRiskDB();
        aDBOper.setSchema(this);
        return aDBOper;
    }

    /**
     * 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LFRisk描述/A>表字段
     * @return: String 返回打包后字符串
     **/
    public String encode()
    {
        StringBuffer strReturn = new StringBuffer(256);
        strReturn.append(StrTool.cTrim(RiskCode));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(RiskVer));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(RiskName));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(RiskType));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(RiskPeriod));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(SubRiskFlag));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(LifeType));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(RiskDutyType));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(RiskYearType));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(HealthType));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(AccidentType));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(EndowmentFlag));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(EndowmentType1));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(EndowmentType2));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(RiskSaleChnl));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(RiskGrpFlag));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(ChargeSaleChnl));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(Operator));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(fDate.getString(MakeDate)));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(MakeTime));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(fDate.getString(ModifyDate)));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(ModifyTime));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(Standbyflag1));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(Standbyflag2));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(ChgData.chgData(Standbyflag3));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(ChgData.chgData(Standbyflag4));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(ChgData.chgData(Standbyflag5));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(ChgData.chgData(Standbyflag6));
        return strReturn.toString();
    }

    /**
     * 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LFRisk>历史记账凭证主表信息</A>表字段
     * @param: strMessage String 包含一条纪录数据的字符串
     * @return: boolean
     **/
    public boolean decode(String strMessage)
    {
        try
        {
            RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER);
            RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER);
            RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER);
            RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER);
            RiskPeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER);
            SubRiskFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER);
            LifeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER);
            RiskDutyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER);
            RiskYearType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER);
            HealthType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER);
            AccidentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER);
            EndowmentFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER);
            EndowmentType1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER);
            EndowmentType2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER);
            RiskSaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER);
            RiskGrpFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER);
            ChargeSaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER);
            Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER);
            MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER));
            MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER);
            ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER));
            ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER);
            Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER);
            Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER);
            Standbyflag3 = new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage, 25, SysConst.PACKAGESPILTER))).intValue();
            Standbyflag4 = new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage, 26, SysConst.PACKAGESPILTER))).intValue();
            Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 27, SysConst.PACKAGESPILTER))).doubleValue();
            Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 28, SysConst.PACKAGESPILTER))).doubleValue();
        }
        catch (NumberFormatException ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_LFRiskSchema";
            tError.functionName = "decode";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 取得对应传入参数的String形式的字段值
     * @param: FCode String 希望取得的字段名
     * @return: String
     * 如果没有对应的字段，返回""
     * 如果字段值为空，返回"null"
     **/
    public String getV(String FCode)
    {
        String strReturn = "";
        if (FCode.equalsIgnoreCase("RiskCode"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
        }
        if (FCode.equalsIgnoreCase("RiskVer"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
        }
        if (FCode.equalsIgnoreCase("RiskName"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(RiskName));
        }
        if (FCode.equalsIgnoreCase("RiskType"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType));
        }
        if (FCode.equalsIgnoreCase("RiskPeriod"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(RiskPeriod));
        }
        if (FCode.equalsIgnoreCase("SubRiskFlag"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(SubRiskFlag));
        }
        if (FCode.equalsIgnoreCase("LifeType"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(LifeType));
        }
        if (FCode.equalsIgnoreCase("RiskDutyType"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(RiskDutyType));
        }
        if (FCode.equalsIgnoreCase("RiskYearType"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(RiskYearType));
        }
        if (FCode.equalsIgnoreCase("HealthType"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(HealthType));
        }
        if (FCode.equalsIgnoreCase("AccidentType"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(AccidentType));
        }
        if (FCode.equalsIgnoreCase("EndowmentFlag"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(EndowmentFlag));
        }
        if (FCode.equalsIgnoreCase("EndowmentType1"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(EndowmentType1));
        }
        if (FCode.equalsIgnoreCase("EndowmentType2"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(EndowmentType2));
        }
        if (FCode.equalsIgnoreCase("RiskSaleChnl"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(RiskSaleChnl));
        }
        if (FCode.equalsIgnoreCase("RiskGrpFlag"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(RiskGrpFlag));
        }
        if (FCode.equalsIgnoreCase("ChargeSaleChnl"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(ChargeSaleChnl));
        }
        if (FCode.equalsIgnoreCase("Operator"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
        }
        if (FCode.equalsIgnoreCase("MakeDate"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(this.getMakeDate()));
        }
        if (FCode.equalsIgnoreCase("MakeTime"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
        }
        if (FCode.equalsIgnoreCase("ModifyDate"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(this.getModifyDate()));
        }
        if (FCode.equalsIgnoreCase("ModifyTime"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
        }
        if (FCode.equalsIgnoreCase("Standbyflag1"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag1));
        }
        if (FCode.equalsIgnoreCase("Standbyflag2"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag2));
        }
        if (FCode.equalsIgnoreCase("Standbyflag3"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag3));
        }
        if (FCode.equalsIgnoreCase("Standbyflag4"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag4));
        }
        if (FCode.equalsIgnoreCase("Standbyflag5"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag5));
        }
        if (FCode.equalsIgnoreCase("Standbyflag6"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag6));
        }
        if (strReturn.equals(""))
        {
            strReturn = "null";
        }
        return strReturn;
    }

    /**
     * 取得Schema中指定索引值所对应的字段值
     * @param: nFieldIndex int 指定的字段索引值
     * @return: String
     * 如果没有对应的字段，返回""
     * 如果字段值为空，返回"null"
     **/
    public String getV(int nFieldIndex)
    {
        String strFieldValue = "";
        switch (nFieldIndex)
        {
            case 0:
                strFieldValue = StrTool.GBKToUnicode(RiskCode);
                break;
            case 1:
                strFieldValue = StrTool.GBKToUnicode(RiskVer);
                break;
            case 2:
                strFieldValue = StrTool.GBKToUnicode(RiskName);
                break;
            case 3:
                strFieldValue = StrTool.GBKToUnicode(RiskType);
                break;
            case 4:
                strFieldValue = StrTool.GBKToUnicode(RiskPeriod);
                break;
            case 5:
                strFieldValue = StrTool.GBKToUnicode(SubRiskFlag);
                break;
            case 6:
                strFieldValue = StrTool.GBKToUnicode(LifeType);
                break;
            case 7:
                strFieldValue = StrTool.GBKToUnicode(RiskDutyType);
                break;
            case 8:
                strFieldValue = StrTool.GBKToUnicode(RiskYearType);
                break;
            case 9:
                strFieldValue = StrTool.GBKToUnicode(HealthType);
                break;
            case 10:
                strFieldValue = StrTool.GBKToUnicode(AccidentType);
                break;
            case 11:
                strFieldValue = StrTool.GBKToUnicode(EndowmentFlag);
                break;
            case 12:
                strFieldValue = StrTool.GBKToUnicode(EndowmentType1);
                break;
            case 13:
                strFieldValue = StrTool.GBKToUnicode(EndowmentType2);
                break;
            case 14:
                strFieldValue = StrTool.GBKToUnicode(RiskSaleChnl);
                break;
            case 15:
                strFieldValue = StrTool.GBKToUnicode(RiskGrpFlag);
                break;
            case 16:
                strFieldValue = StrTool.GBKToUnicode(ChargeSaleChnl);
                break;
            case 17:
                strFieldValue = StrTool.GBKToUnicode(Operator);
                break;
            case 18:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getMakeDate()));
                break;
            case 19:
                strFieldValue = StrTool.GBKToUnicode(MakeTime);
                break;
            case 20:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getModifyDate()));
                break;
            case 21:
                strFieldValue = StrTool.GBKToUnicode(ModifyTime);
                break;
            case 22:
                strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
                break;
            case 23:
                strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
                break;
            case 24:
                strFieldValue = String.valueOf(Standbyflag3);
                break;
            case 25:
                strFieldValue = String.valueOf(Standbyflag4);
                break;
            case 26:
                strFieldValue = String.valueOf(Standbyflag5);
                break;
            case 27:
                strFieldValue = String.valueOf(Standbyflag6);
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
     * 设置对应传入参数的String形式的字段值
     * @param: FCode String 需要赋值的对象
     * @param: FValue String 要赋的值
     * @return: boolean
     **/
    public boolean setV(String FCode, String FValue)
    {
        if (StrTool.cTrim(FCode).equals(""))
            return false;

        if (FCode.equalsIgnoreCase("RiskCode"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                RiskCode = FValue.trim();
            }
            else
                RiskCode = null;
        }
        if (FCode.equalsIgnoreCase("RiskVer"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                RiskVer = FValue.trim();
            }
            else
                RiskVer = null;
        }
        if (FCode.equalsIgnoreCase("RiskName"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                RiskName = FValue.trim();
            }
            else
                RiskName = null;
        }
        if (FCode.equalsIgnoreCase("RiskType"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                RiskType = FValue.trim();
            }
            else
                RiskType = null;
        }
        if (FCode.equalsIgnoreCase("RiskPeriod"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                RiskPeriod = FValue.trim();
            }
            else
                RiskPeriod = null;
        }
        if (FCode.equalsIgnoreCase("SubRiskFlag"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                SubRiskFlag = FValue.trim();
            }
            else
                SubRiskFlag = null;
        }
        if (FCode.equalsIgnoreCase("LifeType"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                LifeType = FValue.trim();
            }
            else
                LifeType = null;
        }
        if (FCode.equalsIgnoreCase("RiskDutyType"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                RiskDutyType = FValue.trim();
            }
            else
                RiskDutyType = null;
        }
        if (FCode.equalsIgnoreCase("RiskYearType"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                RiskYearType = FValue.trim();
            }
            else
                RiskYearType = null;
        }
        if (FCode.equalsIgnoreCase("HealthType"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                HealthType = FValue.trim();
            }
            else
                HealthType = null;
        }
        if (FCode.equalsIgnoreCase("AccidentType"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                AccidentType = FValue.trim();
            }
            else
                AccidentType = null;
        }
        if (FCode.equalsIgnoreCase("EndowmentFlag"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                EndowmentFlag = FValue.trim();
            }
            else
                EndowmentFlag = null;
        }
        if (FCode.equalsIgnoreCase("EndowmentType1"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                EndowmentType1 = FValue.trim();
            }
            else
                EndowmentType1 = null;
        }
        if (FCode.equalsIgnoreCase("EndowmentType2"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                EndowmentType2 = FValue.trim();
            }
            else
                EndowmentType2 = null;
        }
        if (FCode.equalsIgnoreCase("RiskSaleChnl"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                RiskSaleChnl = FValue.trim();
            }
            else
                RiskSaleChnl = null;
        }
        if (FCode.equalsIgnoreCase("RiskGrpFlag"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                RiskGrpFlag = FValue.trim();
            }
            else
                RiskGrpFlag = null;
        }
        if (FCode.equalsIgnoreCase("ChargeSaleChnl"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                ChargeSaleChnl = FValue.trim();
            }
            else
                ChargeSaleChnl = null;
        }
        if (FCode.equalsIgnoreCase("Operator"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Operator = FValue.trim();
            }
            else
                Operator = null;
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
        if (FCode.equalsIgnoreCase("ModifyDate"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                ModifyDate = fDate.getDate(FValue);
            }
            else
                ModifyDate = null;
        }
        if (FCode.equalsIgnoreCase("ModifyTime"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                ModifyTime = FValue.trim();
            }
            else
                ModifyTime = null;
        }
        if (FCode.equalsIgnoreCase("Standbyflag1"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Standbyflag1 = FValue.trim();
            }
            else
                Standbyflag1 = null;
        }
        if (FCode.equalsIgnoreCase("Standbyflag2"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Standbyflag2 = FValue.trim();
            }
            else
                Standbyflag2 = null;
        }
        if (FCode.equalsIgnoreCase("Standbyflag3"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Integer tInteger = new Integer(FValue);
                int i = tInteger.intValue();
                Standbyflag3 = i;
            }
        }
        if (FCode.equalsIgnoreCase("Standbyflag4"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Integer tInteger = new Integer(FValue);
                int i = tInteger.intValue();
                Standbyflag4 = i;
            }
        }
        if (FCode.equalsIgnoreCase("Standbyflag5"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Double tDouble = new Double(FValue);
                double d = tDouble.doubleValue();
                Standbyflag5 = d;
            }
        }
        if (FCode.equalsIgnoreCase("Standbyflag6"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Double tDouble = new Double(FValue);
                double d = tDouble.doubleValue();
                Standbyflag6 = d;
            }
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
        PD_LFRiskSchema other = (PD_LFRiskSchema) otherObject;
        return
                (RiskCode == null ? other.getRiskCode() == null : RiskCode.equals(other.getRiskCode()))
                && (RiskVer == null ? other.getRiskVer() == null : RiskVer.equals(other.getRiskVer()))
                && (RiskName == null ? other.getRiskName() == null : RiskName.equals(other.getRiskName()))
                && (RiskType == null ? other.getRiskType() == null : RiskType.equals(other.getRiskType()))
                && (RiskPeriod == null ? other.getRiskPeriod() == null : RiskPeriod.equals(other.getRiskPeriod()))
                && (SubRiskFlag == null ? other.getSubRiskFlag() == null : SubRiskFlag.equals(other.getSubRiskFlag()))
                && (LifeType == null ? other.getLifeType() == null : LifeType.equals(other.getLifeType()))
                && (RiskDutyType == null ? other.getRiskDutyType() == null : RiskDutyType.equals(other.getRiskDutyType()))
                && (RiskYearType == null ? other.getRiskYearType() == null : RiskYearType.equals(other.getRiskYearType()))
                && (HealthType == null ? other.getHealthType() == null : HealthType.equals(other.getHealthType()))
                && (AccidentType == null ? other.getAccidentType() == null : AccidentType.equals(other.getAccidentType()))
                && (EndowmentFlag == null ? other.getEndowmentFlag() == null : EndowmentFlag.equals(other.getEndowmentFlag()))
                && (EndowmentType1 == null ? other.getEndowmentType1() == null : EndowmentType1.equals(other.getEndowmentType1()))
                && (EndowmentType2 == null ? other.getEndowmentType2() == null : EndowmentType2.equals(other.getEndowmentType2()))
                && (RiskSaleChnl == null ? other.getRiskSaleChnl() == null : RiskSaleChnl.equals(other.getRiskSaleChnl()))
                && (RiskGrpFlag == null ? other.getRiskGrpFlag() == null : RiskGrpFlag.equals(other.getRiskGrpFlag()))
                && (ChargeSaleChnl == null ? other.getChargeSaleChnl() == null : ChargeSaleChnl.equals(other.getChargeSaleChnl()))
                && (Operator == null ? other.getOperator() == null : Operator.equals(other.getOperator()))
                && (MakeDate == null ? other.getMakeDate() == null : fDate.getString(MakeDate).equals(other.getMakeDate()))
                && (MakeTime == null ? other.getMakeTime() == null : MakeTime.equals(other.getMakeTime()))
                && (ModifyDate == null ? other.getModifyDate() == null : fDate.getString(ModifyDate).equals(other.getModifyDate()))
                && (ModifyTime == null ? other.getModifyTime() == null : ModifyTime.equals(other.getModifyTime()))
                && (Standbyflag1 == null ? other.getStandbyflag1() == null : Standbyflag1.equals(other.getStandbyflag1()))
                && (Standbyflag2 == null ? other.getStandbyflag2() == null : Standbyflag2.equals(other.getStandbyflag2()))
                && Standbyflag3 == other.getStandbyflag3()
                && Standbyflag4 == other.getStandbyflag4()
                && Standbyflag5 == other.getStandbyflag5()
                && Standbyflag6 == other.getStandbyflag6();
    }

    /**
     * 取得Schema拥有字段的数量
     * @return: int
     **/
    public int getFieldCount()
    {
        return FIELDNUM;
    }

    /**
     * 取得Schema中指定字段名所对应的索引值
     * 如果没有对应的字段，返回-1
     * @param: strFieldName String
     * @return: int
     **/
    public int getFieldIndex(String strFieldName)
    {
        if (strFieldName.equals("RiskCode"))
        {
            return 0;
        }
        if (strFieldName.equals("RiskVer"))
        {
            return 1;
        }
        if (strFieldName.equals("RiskName"))
        {
            return 2;
        }
        if (strFieldName.equals("RiskType"))
        {
            return 3;
        }
        if (strFieldName.equals("RiskPeriod"))
        {
            return 4;
        }
        if (strFieldName.equals("SubRiskFlag"))
        {
            return 5;
        }
        if (strFieldName.equals("LifeType"))
        {
            return 6;
        }
        if (strFieldName.equals("RiskDutyType"))
        {
            return 7;
        }
        if (strFieldName.equals("RiskYearType"))
        {
            return 8;
        }
        if (strFieldName.equals("HealthType"))
        {
            return 9;
        }
        if (strFieldName.equals("AccidentType"))
        {
            return 10;
        }
        if (strFieldName.equals("EndowmentFlag"))
        {
            return 11;
        }
        if (strFieldName.equals("EndowmentType1"))
        {
            return 12;
        }
        if (strFieldName.equals("EndowmentType2"))
        {
            return 13;
        }
        if (strFieldName.equals("RiskSaleChnl"))
        {
            return 14;
        }
        if (strFieldName.equals("RiskGrpFlag"))
        {
            return 15;
        }
        if (strFieldName.equals("ChargeSaleChnl"))
        {
            return 16;
        }
        if (strFieldName.equals("Operator"))
        {
            return 17;
        }
        if (strFieldName.equals("MakeDate"))
        {
            return 18;
        }
        if (strFieldName.equals("MakeTime"))
        {
            return 19;
        }
        if (strFieldName.equals("ModifyDate"))
        {
            return 20;
        }
        if (strFieldName.equals("ModifyTime"))
        {
            return 21;
        }
        if (strFieldName.equals("Standbyflag1"))
        {
            return 22;
        }
        if (strFieldName.equals("Standbyflag2"))
        {
            return 23;
        }
        if (strFieldName.equals("Standbyflag3"))
        {
            return 24;
        }
        if (strFieldName.equals("Standbyflag4"))
        {
            return 25;
        }
        if (strFieldName.equals("Standbyflag5"))
        {
            return 26;
        }
        if (strFieldName.equals("Standbyflag6"))
        {
            return 27;
        }
        return -1;
    }

    /**
     * 取得Schema中指定索引值所对应的字段名
     * 如果没有对应的字段，返回""
     * @param: nFieldIndex int
     * @return: String
     **/
    public String getFieldName(int nFieldIndex)
    {
        String strFieldName = "";
        switch (nFieldIndex)
        {
            case 0:
                strFieldName = "RiskCode";
                break;
            case 1:
                strFieldName = "RiskVer";
                break;
            case 2:
                strFieldName = "RiskName";
                break;
            case 3:
                strFieldName = "RiskType";
                break;
            case 4:
                strFieldName = "RiskPeriod";
                break;
            case 5:
                strFieldName = "SubRiskFlag";
                break;
            case 6:
                strFieldName = "LifeType";
                break;
            case 7:
                strFieldName = "RiskDutyType";
                break;
            case 8:
                strFieldName = "RiskYearType";
                break;
            case 9:
                strFieldName = "HealthType";
                break;
            case 10:
                strFieldName = "AccidentType";
                break;
            case 11:
                strFieldName = "EndowmentFlag";
                break;
            case 12:
                strFieldName = "EndowmentType1";
                break;
            case 13:
                strFieldName = "EndowmentType2";
                break;
            case 14:
                strFieldName = "RiskSaleChnl";
                break;
            case 15:
                strFieldName = "RiskGrpFlag";
                break;
            case 16:
                strFieldName = "ChargeSaleChnl";
                break;
            case 17:
                strFieldName = "Operator";
                break;
            case 18:
                strFieldName = "MakeDate";
                break;
            case 19:
                strFieldName = "MakeTime";
                break;
            case 20:
                strFieldName = "ModifyDate";
                break;
            case 21:
                strFieldName = "ModifyTime";
                break;
            case 22:
                strFieldName = "Standbyflag1";
                break;
            case 23:
                strFieldName = "Standbyflag2";
                break;
            case 24:
                strFieldName = "Standbyflag3";
                break;
            case 25:
                strFieldName = "Standbyflag4";
                break;
            case 26:
                strFieldName = "Standbyflag5";
                break;
            case 27:
                strFieldName = "Standbyflag6";
                break;
            default:
                strFieldName = "";
        }
        return strFieldName;
    }

    /**
     * 取得Schema中指定字段名所对应的字段类型
     * 如果没有对应的字段，返回Schema.TYPE_NOFOUND
     * @param: strFieldName String
     * @return: int
     **/
    public int getFieldType(String strFieldName)
    {
        if (strFieldName.equals("RiskCode"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("RiskVer"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("RiskName"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("RiskType"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("RiskPeriod"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("SubRiskFlag"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("LifeType"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("RiskDutyType"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("RiskYearType"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("HealthType"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("AccidentType"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("EndowmentFlag"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("EndowmentType1"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("EndowmentType2"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("RiskSaleChnl"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("RiskGrpFlag"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("ChargeSaleChnl"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("Operator"))
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
        if (strFieldName.equals("ModifyDate"))
        {
            return Schema.TYPE_DATE;
        }
        if (strFieldName.equals("ModifyTime"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("Standbyflag1"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("Standbyflag2"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("Standbyflag3"))
        {
            return Schema.TYPE_INT;
        }
        if (strFieldName.equals("Standbyflag4"))
        {
            return Schema.TYPE_INT;
        }
        if (strFieldName.equals("Standbyflag5"))
        {
            return Schema.TYPE_DOUBLE;
        }
        if (strFieldName.equals("Standbyflag6"))
        {
            return Schema.TYPE_DOUBLE;
        }
        return Schema.TYPE_NOFOUND;
    }

    /**
     * 取得Schema中指定索引值所对应的字段类型
     * 如果没有对应的字段，返回Schema.TYPE_NOFOUND
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
                nFieldType = Schema.TYPE_DATE;
                break;
            case 19:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 20:
                nFieldType = Schema.TYPE_DATE;
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
                nFieldType = Schema.TYPE_INT;
                break;
            case 25:
                nFieldType = Schema.TYPE_INT;
                break;
            case 26:
                nFieldType = Schema.TYPE_DOUBLE;
                break;
            case 27:
                nFieldType = Schema.TYPE_DOUBLE;
                break;
            default:
                nFieldType = Schema.TYPE_NOFOUND;
        }
        return nFieldType;
    }
}
