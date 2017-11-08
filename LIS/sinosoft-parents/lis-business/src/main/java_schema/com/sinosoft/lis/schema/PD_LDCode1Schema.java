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
import com.sinosoft.lis.db.PD_LDCode1DB;

/**
 * <p>ClassName: PD_LDCode1Schema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: 产品定义平台_PDM
 * @author：Makerx
 * @CreateDate：2009-03-04
 */
public class PD_LDCode1Schema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LDCode1Schema.class);

    // @Field
    /** 编码类型 */
    private String CodeType;
    /** 编码 */
    private String Code;
    /** 子编码 */
    private String Code1;
    /** 编码名称 */
    private String CodeName;
    /** 编码别名 */
    private String CodeAlias;
    /** 机构代码 */
    private String ComCode;
    /** 其它标志 */
    private String OtherSign;
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

    public static final int FIELDNUM = 18; // 数据库表的字段个数

    private static String[] PK; // 主键

    private FDate fDate = new FDate(); // 处理日期

    public CErrors mErrors; // 错误信息

    // @Constructor
    public PD_LDCode1Schema()
    {
        mErrors = new CErrors();
        String[] pk = new String[2];
        pk[0] = "CodeType";
        pk[1] = "Code";
        PK = pk;
    }

    /**
     * Schema克隆
     * @return Object
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException
    {
        PD_LDCode1Schema cloned = (PD_LDCode1Schema)super.clone();
        cloned.fDate = (FDate) fDate.clone();
        cloned.mErrors = (CErrors) mErrors.clone();
        return cloned;
    }

    // @Method
    public String[] getPK()
    {
        return PK;
    }

    public String getCodeType()
    {
        return CodeType;
    }

    public void setCodeType(String aCodeType)
    {
        CodeType = aCodeType;
    }

    public String getCode()
    {
        return Code;
    }

    public void setCode(String aCode)
    {
        Code = aCode;
    }

    public String getCode1()
    {
        return Code1;
    }

    public void setCode1(String aCode1)
    {
        Code1 = aCode1;
    }

    public String getCodeName()
    {
        return CodeName;
    }

    public void setCodeName(String aCodeName)
    {
        CodeName = aCodeName;
    }

    public String getCodeAlias()
    {
        return CodeAlias;
    }

    public void setCodeAlias(String aCodeAlias)
    {
        CodeAlias = aCodeAlias;
    }

    public String getComCode()
    {
        return ComCode;
    }

    public void setComCode(String aComCode)
    {
        ComCode = aComCode;
    }

    public String getOtherSign()
    {
        return OtherSign;
    }

    public void setOtherSign(String aOtherSign)
    {
        OtherSign = aOtherSign;
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
     * 使用另外一个 PD_LDCode1Schema 对象给 Schema 赋值
     * @param: aPD_LDCode1Schema PD_LDCode1Schema
     **/
    public void setSchema(PD_LDCode1Schema aPD_LDCode1Schema)
    {
        this.CodeType = aPD_LDCode1Schema.getCodeType();
        this.Code = aPD_LDCode1Schema.getCode();
        this.Code1 = aPD_LDCode1Schema.getCode1();
        this.CodeName = aPD_LDCode1Schema.getCodeName();
        this.CodeAlias = aPD_LDCode1Schema.getCodeAlias();
        this.ComCode = aPD_LDCode1Schema.getComCode();
        this.OtherSign = aPD_LDCode1Schema.getOtherSign();
        this.Operator = aPD_LDCode1Schema.getOperator();
        this.MakeDate = fDate.getDate(aPD_LDCode1Schema.getMakeDate());
        this.MakeTime = aPD_LDCode1Schema.getMakeTime();
        this.ModifyDate = fDate.getDate(aPD_LDCode1Schema.getModifyDate());
        this.ModifyTime = aPD_LDCode1Schema.getModifyTime();
        this.Standbyflag1 = aPD_LDCode1Schema.getStandbyflag1();
        this.Standbyflag2 = aPD_LDCode1Schema.getStandbyflag2();
        this.Standbyflag3 = aPD_LDCode1Schema.getStandbyflag3();
        this.Standbyflag4 = aPD_LDCode1Schema.getStandbyflag4();
        this.Standbyflag5 = aPD_LDCode1Schema.getStandbyflag5();
        this.Standbyflag6 = aPD_LDCode1Schema.getStandbyflag6();
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
                this.CodeType = null;
            else
                this.CodeType = rs.getString(1).trim();
            if (rs.getString(2) == null)
                this.Code = null;
            else
                this.Code = rs.getString(2).trim();
            if (rs.getString(3) == null)
                this.Code1 = null;
            else
                this.Code1 = rs.getString(3).trim();
            if (rs.getString(4) == null)
                this.CodeName = null;
            else
                this.CodeName = rs.getString(4).trim();
            if (rs.getString(5) == null)
                this.CodeAlias = null;
            else
                this.CodeAlias = rs.getString(5).trim();
            if (rs.getString(6) == null)
                this.ComCode = null;
            else
                this.ComCode = rs.getString(6).trim();
            if (rs.getString(7) == null)
                this.OtherSign = null;
            else
                this.OtherSign = rs.getString(7).trim();
            if (rs.getString(8) == null)
                this.Operator = null;
            else
                this.Operator = rs.getString(8).trim();
            this.MakeDate = rs.getDate(9);
            if (rs.getString(10) == null)
                this.MakeTime = null;
            else
                this.MakeTime = rs.getString(10).trim();
            this.ModifyDate = rs.getDate(11);
            if (rs.getString(12) == null)
                this.ModifyTime = null;
            else
                this.ModifyTime = rs.getString(12).trim();
            if (rs.getString(13) == null)
                this.Standbyflag1 = null;
            else
                this.Standbyflag1 = rs.getString(13).trim();
            if (rs.getString(14) == null)
                this.Standbyflag2 = null;
            else
                this.Standbyflag2 = rs.getString(14).trim();
            this.Standbyflag3 = rs.getInt(15);
            this.Standbyflag4 = rs.getInt(16);
            this.Standbyflag5 = rs.getDouble(17);
            this.Standbyflag6 = rs.getDouble(18);
        }
        catch (SQLException sqle)
        {
            logger.debug("数据库中表PD_LDCode1字段个数和Schema中的字段个数不一致，或执行db.executeQuery查询时未使用select * from tables");
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_LDCode1Schema";
            tError.functionName = "setSchema";
            tError.errorMessage = sqle.toString();
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public PD_LDCode1Schema getSchema()
    {
        PD_LDCode1Schema aPD_LDCode1Schema = new PD_LDCode1Schema();
        aPD_LDCode1Schema.setSchema(this);
        return aPD_LDCode1Schema;
    }

    public PD_LDCode1DB getDB()
    {
        PD_LDCode1DB aDBOper = new PD_LDCode1DB();
        aDBOper.setSchema(this);
        return aDBOper;
    }

    /**
     * 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LDCode1描述/A>表字段
     * @return: String 返回打包后字符串
     **/
    public String encode()
    {
        StringBuffer strReturn = new StringBuffer(256);
        strReturn.append(StrTool.cTrim(CodeType));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(Code));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(Code1));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(CodeName));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(CodeAlias));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(ComCode));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(OtherSign));
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
     * 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LDCode1>历史记账凭证主表信息</A>表字段
     * @param: strMessage String 包含一条纪录数据的字符串
     * @return: boolean
     **/
    public boolean decode(String strMessage)
    {
        try
        {
            CodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER);
            Code = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER);
            Code1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER);
            CodeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER);
            CodeAlias = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER);
            ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER);
            OtherSign = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER);
            Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER);
            MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER));
            MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER);
            ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER));
            ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER);
            Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER);
            Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER);
            Standbyflag3 = new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage, 15, SysConst.PACKAGESPILTER))).intValue();
            Standbyflag4 = new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage, 16, SysConst.PACKAGESPILTER))).intValue();
            Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 17, SysConst.PACKAGESPILTER))).doubleValue();
            Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 18, SysConst.PACKAGESPILTER))).doubleValue();
        }
        catch (NumberFormatException ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_LDCode1Schema";
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
        if (FCode.equals("CodeType"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(CodeType));
        }
        if (FCode.equals("Code"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Code));
        }
        if (FCode.equals("Code1"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Code1));
        }
        if (FCode.equals("CodeName"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(CodeName));
        }
        if (FCode.equals("CodeAlias"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(CodeAlias));
        }
        if (FCode.equals("ComCode"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
        }
        if (FCode.equals("OtherSign"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(OtherSign));
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
                strFieldValue = StrTool.GBKToUnicode(CodeType);
                break;
            case 1:
                strFieldValue = StrTool.GBKToUnicode(Code);
                break;
            case 2:
                strFieldValue = StrTool.GBKToUnicode(Code1);
                break;
            case 3:
                strFieldValue = StrTool.GBKToUnicode(CodeName);
                break;
            case 4:
                strFieldValue = StrTool.GBKToUnicode(CodeAlias);
                break;
            case 5:
                strFieldValue = StrTool.GBKToUnicode(ComCode);
                break;
            case 6:
                strFieldValue = StrTool.GBKToUnicode(OtherSign);
                break;
            case 7:
                strFieldValue = StrTool.GBKToUnicode(Operator);
                break;
            case 8:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getMakeDate()));
                break;
            case 9:
                strFieldValue = StrTool.GBKToUnicode(MakeTime);
                break;
            case 10:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getModifyDate()));
                break;
            case 11:
                strFieldValue = StrTool.GBKToUnicode(ModifyTime);
                break;
            case 12:
                strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
                break;
            case 13:
                strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
                break;
            case 14:
                strFieldValue = String.valueOf(Standbyflag3);
                break;
            case 15:
                strFieldValue = String.valueOf(Standbyflag4);
                break;
            case 16:
                strFieldValue = String.valueOf(Standbyflag5);
                break;
            case 17:
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

        if (FCode.equalsIgnoreCase("CodeType"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                CodeType = FValue.trim();
            }
            else
                CodeType = null;
        }
        if (FCode.equalsIgnoreCase("Code"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Code = FValue.trim();
            }
            else
                Code = null;
        }
        if (FCode.equalsIgnoreCase("Code1"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Code1 = FValue.trim();
            }
            else
                Code1 = null;
        }
        if (FCode.equalsIgnoreCase("CodeName"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                CodeName = FValue.trim();
            }
            else
                CodeName = null;
        }
        if (FCode.equalsIgnoreCase("CodeAlias"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                CodeAlias = FValue.trim();
            }
            else
                CodeAlias = null;
        }
        if (FCode.equalsIgnoreCase("ComCode"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                ComCode = FValue.trim();
            }
            else
                ComCode = null;
        }
        if (FCode.equalsIgnoreCase("OtherSign"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                OtherSign = FValue.trim();
            }
            else
                OtherSign = null;
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
        PD_LDCode1Schema other = (PD_LDCode1Schema) otherObject;
        return
                (CodeType == null ? other.getCodeType() == null : CodeType.equals(other.getCodeType()))
                && (Code == null ? other.getCode() == null : Code.equals(other.getCode()))
                && (Code1 == null ? other.getCode1() == null : Code1.equals(other.getCode1()))
                && (CodeName == null ? other.getCodeName() == null : CodeName.equals(other.getCodeName()))
                && (CodeAlias == null ? other.getCodeAlias() == null : CodeAlias.equals(other.getCodeAlias()))
                && (ComCode == null ? other.getComCode() == null : ComCode.equals(other.getComCode()))
                && (OtherSign == null ? other.getOtherSign() == null : OtherSign.equals(other.getOtherSign()))
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
        if (strFieldName.equals("CodeType"))
        {
            return 0;
        }
        if (strFieldName.equals("Code"))
        {
            return 1;
        }
        if (strFieldName.equals("Code1"))
        {
            return 2;
        }
        if (strFieldName.equals("CodeName"))
        {
            return 3;
        }
        if (strFieldName.equals("CodeAlias"))
        {
            return 4;
        }
        if (strFieldName.equals("ComCode"))
        {
            return 5;
        }
        if (strFieldName.equals("OtherSign"))
        {
            return 6;
        }
        if (strFieldName.equals("Operator"))
        {
            return 7;
        }
        if (strFieldName.equals("MakeDate"))
        {
            return 8;
        }
        if (strFieldName.equals("MakeTime"))
        {
            return 9;
        }
        if (strFieldName.equals("ModifyDate"))
        {
            return 10;
        }
        if (strFieldName.equals("ModifyTime"))
        {
            return 11;
        }
        if (strFieldName.equals("Standbyflag1"))
        {
            return 12;
        }
        if (strFieldName.equals("Standbyflag2"))
        {
            return 13;
        }
        if (strFieldName.equals("Standbyflag3"))
        {
            return 14;
        }
        if (strFieldName.equals("Standbyflag4"))
        {
            return 15;
        }
        if (strFieldName.equals("Standbyflag5"))
        {
            return 16;
        }
        if (strFieldName.equals("Standbyflag6"))
        {
            return 17;
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
                strFieldName = "CodeType";
                break;
            case 1:
                strFieldName = "Code";
                break;
            case 2:
                strFieldName = "Code1";
                break;
            case 3:
                strFieldName = "CodeName";
                break;
            case 4:
                strFieldName = "CodeAlias";
                break;
            case 5:
                strFieldName = "ComCode";
                break;
            case 6:
                strFieldName = "OtherSign";
                break;
            case 7:
                strFieldName = "Operator";
                break;
            case 8:
                strFieldName = "MakeDate";
                break;
            case 9:
                strFieldName = "MakeTime";
                break;
            case 10:
                strFieldName = "ModifyDate";
                break;
            case 11:
                strFieldName = "ModifyTime";
                break;
            case 12:
                strFieldName = "Standbyflag1";
                break;
            case 13:
                strFieldName = "Standbyflag2";
                break;
            case 14:
                strFieldName = "Standbyflag3";
                break;
            case 15:
                strFieldName = "Standbyflag4";
                break;
            case 16:
                strFieldName = "Standbyflag5";
                break;
            case 17:
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
        if (strFieldName.equals("CodeType"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("Code"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("Code1"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("CodeName"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("CodeAlias"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("ComCode"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("OtherSign"))
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
                nFieldType = Schema.TYPE_DATE;
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
                nFieldType = Schema.TYPE_STRING;
                break;
            case 13:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 14:
                nFieldType = Schema.TYPE_INT;
                break;
            case 15:
                nFieldType = Schema.TYPE_INT;
                break;
            case 16:
                nFieldType = Schema.TYPE_DOUBLE;
                break;
            case 17:
                nFieldType = Schema.TYPE_DOUBLE;
                break;
            default:
                nFieldType = Schema.TYPE_NOFOUND;
        }
        return nFieldType;
    }
}
