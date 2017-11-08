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
import com.sinosoft.lis.db.PD_LAWageCalElementDB;

/**
 * <p>ClassName: PD_LAWageCalElementSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: 产品定义平台_PDM
 * @author：Makerx
 * @CreateDate：2009-03-04
 */
public class PD_LAWageCalElementSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LAWageCalElementSchema.class);

    // @Field
    /** 险种编码 */
    private String RiskCode;
    /** 计算类型 */
    private String CalType;
    /** 要素1 */
    private String F1;
    /** 要素2 */
    private String F2;
    /** 要素3 */
    private String F3;
    /** 要素4 */
    private String F4;
    /** 要素5 */
    private String F5;
    /** 佣金计算编码 */
    private String CalCode;
    /** 展业类型 */
    private String BranchType;
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

    public static final int FIELDNUM = 20; // 数据库表的字段个数

    private static String[] PK; // 主键

    private FDate fDate = new FDate(); // 处理日期

    public CErrors mErrors; // 错误信息

    // @Constructor
    public PD_LAWageCalElementSchema()
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
        PD_LAWageCalElementSchema cloned = (PD_LAWageCalElementSchema)super.clone();
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

    public String getCalType()
    {
        return CalType;
    }

    public void setCalType(String aCalType)
    {
        CalType = aCalType;
    }

    public String getF1()
    {
        return F1;
    }

    public void setF1(String aF1)
    {
        F1 = aF1;
    }

    public String getF2()
    {
        return F2;
    }

    public void setF2(String aF2)
    {
        F2 = aF2;
    }

    public String getF3()
    {
        return F3;
    }

    public void setF3(String aF3)
    {
        F3 = aF3;
    }

    public String getF4()
    {
        return F4;
    }

    public void setF4(String aF4)
    {
        F4 = aF4;
    }

    public String getF5()
    {
        return F5;
    }

    public void setF5(String aF5)
    {
        F5 = aF5;
    }

    public String getCalCode()
    {
        return CalCode;
    }

    public void setCalCode(String aCalCode)
    {
        CalCode = aCalCode;
    }

    public String getBranchType()
    {
        return BranchType;
    }

    public void setBranchType(String aBranchType)
    {
        BranchType = aBranchType;
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
     * 使用另外一个 PD_LAWageCalElementSchema 对象给 Schema 赋值
     * @param: aPD_LAWageCalElementSchema PD_LAWageCalElementSchema
     **/
    public void setSchema(PD_LAWageCalElementSchema aPD_LAWageCalElementSchema)
    {
        this.RiskCode = aPD_LAWageCalElementSchema.getRiskCode();
        this.CalType = aPD_LAWageCalElementSchema.getCalType();
        this.F1 = aPD_LAWageCalElementSchema.getF1();
        this.F2 = aPD_LAWageCalElementSchema.getF2();
        this.F3 = aPD_LAWageCalElementSchema.getF3();
        this.F4 = aPD_LAWageCalElementSchema.getF4();
        this.F5 = aPD_LAWageCalElementSchema.getF5();
        this.CalCode = aPD_LAWageCalElementSchema.getCalCode();
        this.BranchType = aPD_LAWageCalElementSchema.getBranchType();
        this.Operator = aPD_LAWageCalElementSchema.getOperator();
        this.MakeDate = fDate.getDate(aPD_LAWageCalElementSchema.getMakeDate());
        this.MakeTime = aPD_LAWageCalElementSchema.getMakeTime();
        this.ModifyDate = fDate.getDate(aPD_LAWageCalElementSchema.getModifyDate());
        this.ModifyTime = aPD_LAWageCalElementSchema.getModifyTime();
        this.Standbyflag1 = aPD_LAWageCalElementSchema.getStandbyflag1();
        this.Standbyflag2 = aPD_LAWageCalElementSchema.getStandbyflag2();
        this.Standbyflag3 = aPD_LAWageCalElementSchema.getStandbyflag3();
        this.Standbyflag4 = aPD_LAWageCalElementSchema.getStandbyflag4();
        this.Standbyflag5 = aPD_LAWageCalElementSchema.getStandbyflag5();
        this.Standbyflag6 = aPD_LAWageCalElementSchema.getStandbyflag6();
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
                this.CalType = null;
            else
                this.CalType = rs.getString(2).trim();
            if (rs.getString(3) == null)
                this.F1 = null;
            else
                this.F1 = rs.getString(3).trim();
            if (rs.getString(4) == null)
                this.F2 = null;
            else
                this.F2 = rs.getString(4).trim();
            if (rs.getString(5) == null)
                this.F3 = null;
            else
                this.F3 = rs.getString(5).trim();
            if (rs.getString(6) == null)
                this.F4 = null;
            else
                this.F4 = rs.getString(6).trim();
            if (rs.getString(7) == null)
                this.F5 = null;
            else
                this.F5 = rs.getString(7).trim();
            if (rs.getString(8) == null)
                this.CalCode = null;
            else
                this.CalCode = rs.getString(8).trim();
            if (rs.getString(9) == null)
                this.BranchType = null;
            else
                this.BranchType = rs.getString(9).trim();
            if (rs.getString(10) == null)
                this.Operator = null;
            else
                this.Operator = rs.getString(10).trim();
            this.MakeDate = rs.getDate(11);
            if (rs.getString(12) == null)
                this.MakeTime = null;
            else
                this.MakeTime = rs.getString(12).trim();
            this.ModifyDate = rs.getDate(13);
            if (rs.getString(14) == null)
                this.ModifyTime = null;
            else
                this.ModifyTime = rs.getString(14).trim();
            if (rs.getString(15) == null)
                this.Standbyflag1 = null;
            else
                this.Standbyflag1 = rs.getString(15).trim();
            if (rs.getString(16) == null)
                this.Standbyflag2 = null;
            else
                this.Standbyflag2 = rs.getString(16).trim();
            this.Standbyflag3 = rs.getInt(17);
            this.Standbyflag4 = rs.getInt(18);
            this.Standbyflag5 = rs.getDouble(19);
            this.Standbyflag6 = rs.getDouble(20);
        }
        catch (SQLException sqle)
        {
            logger.debug("数据库中表PD_LAWageCalElement字段个数和Schema中的字段个数不一致，或执行db.executeQuery查询时未使用select * from tables");
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_LAWageCalElementSchema";
            tError.functionName = "setSchema";
            tError.errorMessage = sqle.toString();
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public PD_LAWageCalElementSchema getSchema()
    {
        PD_LAWageCalElementSchema aPD_LAWageCalElementSchema = new PD_LAWageCalElementSchema();
        aPD_LAWageCalElementSchema.setSchema(this);
        return aPD_LAWageCalElementSchema;
    }

    public PD_LAWageCalElementDB getDB()
    {
        PD_LAWageCalElementDB aDBOper = new PD_LAWageCalElementDB();
        aDBOper.setSchema(this);
        return aDBOper;
    }

    /**
     * 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LAWageCalElement描述/A>表字段
     * @return: String 返回打包后字符串
     **/
    public String encode()
    {
        StringBuffer strReturn = new StringBuffer(256);
        strReturn.append(StrTool.cTrim(RiskCode));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(CalType));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(F1));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(F2));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(F3));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(F4));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(F5));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(CalCode));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(BranchType));
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
     * 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LAWageCalElement>历史记账凭证主表信息</A>表字段
     * @param: strMessage String 包含一条纪录数据的字符串
     * @return: boolean
     **/
    public boolean decode(String strMessage)
    {
        try
        {
            RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER);
            CalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER);
            F1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER);
            F2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER);
            F3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER);
            F4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER);
            F5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER);
            CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER);
            BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER);
            Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER);
            MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER));
            MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER);
            ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER));
            ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER);
            Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER);
            Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER);
            Standbyflag3 = new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage, 17, SysConst.PACKAGESPILTER))).intValue();
            Standbyflag4 = new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage, 18, SysConst.PACKAGESPILTER))).intValue();
            Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 19, SysConst.PACKAGESPILTER))).doubleValue();
            Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 20, SysConst.PACKAGESPILTER))).doubleValue();
        }
        catch (NumberFormatException ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_LAWageCalElementSchema";
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
        if (FCode.equals("RiskCode"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
        }
        if (FCode.equals("CalType"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(CalType));
        }
        if (FCode.equals("F1"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(F1));
        }
        if (FCode.equals("F2"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(F2));
        }
        if (FCode.equals("F3"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(F3));
        }
        if (FCode.equals("F4"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(F4));
        }
        if (FCode.equals("F5"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(F5));
        }
        if (FCode.equals("CalCode"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
        }
        if (FCode.equals("BranchType"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(BranchType));
        }
        if (FCode.equals("Operator"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
        }
        if (FCode.equals("MakeDate"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(this.getMakeDate()));
        }
        if (FCode.equals("MakeTime"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
        }
        if (FCode.equals("ModifyDate"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(this.getModifyDate()));
        }
        if (FCode.equals("ModifyTime"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
        }
        if (FCode.equals("Standbyflag1"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag1));
        }
        if (FCode.equals("Standbyflag2"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag2));
        }
        if (FCode.equals("Standbyflag3"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag3));
        }
        if (FCode.equals("Standbyflag4"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag4));
        }
        if (FCode.equals("Standbyflag5"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag5));
        }
        if (FCode.equals("Standbyflag6"))
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
                strFieldValue = StrTool.GBKToUnicode(CalType);
                break;
            case 2:
                strFieldValue = StrTool.GBKToUnicode(F1);
                break;
            case 3:
                strFieldValue = StrTool.GBKToUnicode(F2);
                break;
            case 4:
                strFieldValue = StrTool.GBKToUnicode(F3);
                break;
            case 5:
                strFieldValue = StrTool.GBKToUnicode(F4);
                break;
            case 6:
                strFieldValue = StrTool.GBKToUnicode(F5);
                break;
            case 7:
                strFieldValue = StrTool.GBKToUnicode(CalCode);
                break;
            case 8:
                strFieldValue = StrTool.GBKToUnicode(BranchType);
                break;
            case 9:
                strFieldValue = StrTool.GBKToUnicode(Operator);
                break;
            case 10:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getMakeDate()));
                break;
            case 11:
                strFieldValue = StrTool.GBKToUnicode(MakeTime);
                break;
            case 12:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getModifyDate()));
                break;
            case 13:
                strFieldValue = StrTool.GBKToUnicode(ModifyTime);
                break;
            case 14:
                strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
                break;
            case 15:
                strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
                break;
            case 16:
                strFieldValue = String.valueOf(Standbyflag3);
                break;
            case 17:
                strFieldValue = String.valueOf(Standbyflag4);
                break;
            case 18:
                strFieldValue = String.valueOf(Standbyflag5);
                break;
            case 19:
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
        if (FCode.equalsIgnoreCase("CalType"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                CalType = FValue.trim();
            }
            else
                CalType = null;
        }
        if (FCode.equalsIgnoreCase("F1"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                F1 = FValue.trim();
            }
            else
                F1 = null;
        }
        if (FCode.equalsIgnoreCase("F2"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                F2 = FValue.trim();
            }
            else
                F2 = null;
        }
        if (FCode.equalsIgnoreCase("F3"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                F3 = FValue.trim();
            }
            else
                F3 = null;
        }
        if (FCode.equalsIgnoreCase("F4"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                F4 = FValue.trim();
            }
            else
                F4 = null;
        }
        if (FCode.equalsIgnoreCase("F5"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                F5 = FValue.trim();
            }
            else
                F5 = null;
        }
        if (FCode.equalsIgnoreCase("CalCode"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                CalCode = FValue.trim();
            }
            else
                CalCode = null;
        }
        if (FCode.equalsIgnoreCase("BranchType"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                BranchType = FValue.trim();
            }
            else
                BranchType = null;
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
        PD_LAWageCalElementSchema other = (PD_LAWageCalElementSchema) otherObject;
        return
                (RiskCode == null ? other.getRiskCode() == null : RiskCode.equals(other.getRiskCode()))
                && (CalType == null ? other.getCalType() == null : CalType.equals(other.getCalType()))
                && (F1 == null ? other.getF1() == null : F1.equals(other.getF1()))
                && (F2 == null ? other.getF2() == null : F2.equals(other.getF2()))
                && (F3 == null ? other.getF3() == null : F3.equals(other.getF3()))
                && (F4 == null ? other.getF4() == null : F4.equals(other.getF4()))
                && (F5 == null ? other.getF5() == null : F5.equals(other.getF5()))
                && (CalCode == null ? other.getCalCode() == null : CalCode.equals(other.getCalCode()))
                && (BranchType == null ? other.getBranchType() == null : BranchType.equals(other.getBranchType()))
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
        if (strFieldName.equals("CalType"))
        {
            return 1;
        }
        if (strFieldName.equals("F1"))
        {
            return 2;
        }
        if (strFieldName.equals("F2"))
        {
            return 3;
        }
        if (strFieldName.equals("F3"))
        {
            return 4;
        }
        if (strFieldName.equals("F4"))
        {
            return 5;
        }
        if (strFieldName.equals("F5"))
        {
            return 6;
        }
        if (strFieldName.equals("CalCode"))
        {
            return 7;
        }
        if (strFieldName.equals("BranchType"))
        {
            return 8;
        }
        if (strFieldName.equals("Operator"))
        {
            return 9;
        }
        if (strFieldName.equals("MakeDate"))
        {
            return 10;
        }
        if (strFieldName.equals("MakeTime"))
        {
            return 11;
        }
        if (strFieldName.equals("ModifyDate"))
        {
            return 12;
        }
        if (strFieldName.equals("ModifyTime"))
        {
            return 13;
        }
        if (strFieldName.equals("Standbyflag1"))
        {
            return 14;
        }
        if (strFieldName.equals("Standbyflag2"))
        {
            return 15;
        }
        if (strFieldName.equals("Standbyflag3"))
        {
            return 16;
        }
        if (strFieldName.equals("Standbyflag4"))
        {
            return 17;
        }
        if (strFieldName.equals("Standbyflag5"))
        {
            return 18;
        }
        if (strFieldName.equals("Standbyflag6"))
        {
            return 19;
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
                strFieldName = "CalType";
                break;
            case 2:
                strFieldName = "F1";
                break;
            case 3:
                strFieldName = "F2";
                break;
            case 4:
                strFieldName = "F3";
                break;
            case 5:
                strFieldName = "F4";
                break;
            case 6:
                strFieldName = "F5";
                break;
            case 7:
                strFieldName = "CalCode";
                break;
            case 8:
                strFieldName = "BranchType";
                break;
            case 9:
                strFieldName = "Operator";
                break;
            case 10:
                strFieldName = "MakeDate";
                break;
            case 11:
                strFieldName = "MakeTime";
                break;
            case 12:
                strFieldName = "ModifyDate";
                break;
            case 13:
                strFieldName = "ModifyTime";
                break;
            case 14:
                strFieldName = "Standbyflag1";
                break;
            case 15:
                strFieldName = "Standbyflag2";
                break;
            case 16:
                strFieldName = "Standbyflag3";
                break;
            case 17:
                strFieldName = "Standbyflag4";
                break;
            case 18:
                strFieldName = "Standbyflag5";
                break;
            case 19:
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
        if (strFieldName.equals("CalType"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("F1"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("F2"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("F3"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("F4"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("F5"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("CalCode"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("BranchType"))
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
                nFieldType = Schema.TYPE_DATE;
                break;
            case 11:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 12:
                nFieldType = Schema.TYPE_DATE;
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
                nFieldType = Schema.TYPE_INT;
                break;
            case 17:
                nFieldType = Schema.TYPE_INT;
                break;
            case 18:
                nFieldType = Schema.TYPE_DOUBLE;
                break;
            case 19:
                nFieldType = Schema.TYPE_DOUBLE;
                break;
            default:
                nFieldType = Schema.TYPE_NOFOUND;
        }
        return nFieldType;
    }
}
