/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LCGRPCONTRENEWTRACEDB;

/**
 * <p>ClassName: LCGRPCONTRENEWTRACESchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: temp
 * @author：Makerx
 * @CreateDate：2011-09-13
 */
public class LCGRPCONTRENEWTRACESchema implements Schema, Cloneable
{
    // @Field
    /** GRPCONTNO */
    private String GRPCONTNO;
    /** PROPOSALGRPCONTNO */
    private String PROPOSALGRPCONTNO;
    /** PRTNO */
    private String PRTNO;
    /** OLDGRPCONTNO */
    private String OLDGRPCONTNO;
    /** RENEWTIMES */
    private double RENEWTIMES;
    /** STANDBYFLAG1 */
    private String STANDBYFLAG1;
    /** STANDBYFLAG2 */
    private String STANDBYFLAG2;
    /** STANDBYFLAG3 */
    private String STANDBYFLAG3;
    /** OPERATOR */
    private String OPERATOR;
    /** MAKEDATE */
    private Date MAKEDATE;
    /** MAKETIME */
    private String MAKETIME;
    /** MODIFYDATE */
    private Date MODIFYDATE;
    /** MODIFYTIME */
    private String MODIFYTIME;

    public static final int FIELDNUM = 13; // 数据库表的字段个数

    private static String[] PK; // 主键

    private FDate fDate = new FDate(); // 处理日期

    public CErrors mErrors; // 错误信息

    // @Constructor
    public LCGRPCONTRENEWTRACESchema()
    {
        mErrors = new CErrors();
        String[] pk = new String[1];
        pk[0] = "GRPCONTNO";
        PK = pk;
    }

    /**
     * Schema克隆
     * @return Object
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException
    {
        LCGRPCONTRENEWTRACESchema cloned = (LCGRPCONTRENEWTRACESchema)super.clone();
        cloned.fDate = (FDate) fDate.clone();
        cloned.mErrors = (CErrors) mErrors.clone();
        return cloned;
    }

    // @Method
    public String[] getPK()
    {
        return PK;
    }

    public String getGRPCONTNO()
    {
        return GRPCONTNO;
    }

    public void setGRPCONTNO(String aGRPCONTNO)
    {
        GRPCONTNO = aGRPCONTNO;
    }

    public String getPROPOSALGRPCONTNO()
    {
        return PROPOSALGRPCONTNO;
    }

    public void setPROPOSALGRPCONTNO(String aPROPOSALGRPCONTNO)
    {
        PROPOSALGRPCONTNO = aPROPOSALGRPCONTNO;
    }

    public String getPRTNO()
    {
        return PRTNO;
    }

    public void setPRTNO(String aPRTNO)
    {
        PRTNO = aPRTNO;
    }

    public String getOLDGRPCONTNO()
    {
        return OLDGRPCONTNO;
    }

    public void setOLDGRPCONTNO(String aOLDGRPCONTNO)
    {
        OLDGRPCONTNO = aOLDGRPCONTNO;
    }

    public double getRENEWTIMES()
    {
        return RENEWTIMES;
    }

    public void setRENEWTIMES(double aRENEWTIMES)
    {
        RENEWTIMES = aRENEWTIMES;
    }

    public void setRENEWTIMES(String aRENEWTIMES)
    {
        if (aRENEWTIMES != null && !aRENEWTIMES.equals(""))
        {
            Double tDouble = new Double(aRENEWTIMES);
            RENEWTIMES = tDouble.doubleValue();
        }
    }

    public String getSTANDBYFLAG1()
    {
        return STANDBYFLAG1;
    }

    public void setSTANDBYFLAG1(String aSTANDBYFLAG1)
    {
        STANDBYFLAG1 = aSTANDBYFLAG1;
    }

    public String getSTANDBYFLAG2()
    {
        return STANDBYFLAG2;
    }

    public void setSTANDBYFLAG2(String aSTANDBYFLAG2)
    {
        STANDBYFLAG2 = aSTANDBYFLAG2;
    }

    public String getSTANDBYFLAG3()
    {
        return STANDBYFLAG3;
    }

    public void setSTANDBYFLAG3(String aSTANDBYFLAG3)
    {
        STANDBYFLAG3 = aSTANDBYFLAG3;
    }

    public String getOPERATOR()
    {
        return OPERATOR;
    }

    public void setOPERATOR(String aOPERATOR)
    {
        OPERATOR = aOPERATOR;
    }

    public String getMAKEDATE()
    {
        if (MAKEDATE != null)
            return fDate.getString(MAKEDATE);
        else
            return null;
    }

    public void setMAKEDATE(Date aMAKEDATE)
    {
        MAKEDATE = aMAKEDATE;
    }

    public void setMAKEDATE(String aMAKEDATE)
    {
        if (aMAKEDATE != null && !aMAKEDATE.equals(""))
        {
            MAKEDATE = fDate.getDate(aMAKEDATE);
        }
        else
            MAKEDATE = null;
    }

    public String getMAKETIME()
    {
        return MAKETIME;
    }

    public void setMAKETIME(String aMAKETIME)
    {
        MAKETIME = aMAKETIME;
    }

    public String getMODIFYDATE()
    {
        if (MODIFYDATE != null)
            return fDate.getString(MODIFYDATE);
        else
            return null;
    }

    public void setMODIFYDATE(Date aMODIFYDATE)
    {
        MODIFYDATE = aMODIFYDATE;
    }

    public void setMODIFYDATE(String aMODIFYDATE)
    {
        if (aMODIFYDATE != null && !aMODIFYDATE.equals(""))
        {
            MODIFYDATE = fDate.getDate(aMODIFYDATE);
        }
        else
            MODIFYDATE = null;
    }

    public String getMODIFYTIME()
    {
        return MODIFYTIME;
    }

    public void setMODIFYTIME(String aMODIFYTIME)
    {
        MODIFYTIME = aMODIFYTIME;
    }

    /**
     * 使用另外一个 LCGRPCONTRENEWTRACESchema 对象给 Schema 赋值
     * @param: aLCGRPCONTRENEWTRACESchema LCGRPCONTRENEWTRACESchema
     **/
    public void setSchema(LCGRPCONTRENEWTRACESchema aLCGRPCONTRENEWTRACESchema)
    {
        this.GRPCONTNO = aLCGRPCONTRENEWTRACESchema.getGRPCONTNO();
        this.PROPOSALGRPCONTNO = aLCGRPCONTRENEWTRACESchema.getPROPOSALGRPCONTNO();
        this.PRTNO = aLCGRPCONTRENEWTRACESchema.getPRTNO();
        this.OLDGRPCONTNO = aLCGRPCONTRENEWTRACESchema.getOLDGRPCONTNO();
        this.RENEWTIMES = aLCGRPCONTRENEWTRACESchema.getRENEWTIMES();
        this.STANDBYFLAG1 = aLCGRPCONTRENEWTRACESchema.getSTANDBYFLAG1();
        this.STANDBYFLAG2 = aLCGRPCONTRENEWTRACESchema.getSTANDBYFLAG2();
        this.STANDBYFLAG3 = aLCGRPCONTRENEWTRACESchema.getSTANDBYFLAG3();
        this.OPERATOR = aLCGRPCONTRENEWTRACESchema.getOPERATOR();
        this.MAKEDATE = fDate.getDate(aLCGRPCONTRENEWTRACESchema.getMAKEDATE());
        this.MAKETIME = aLCGRPCONTRENEWTRACESchema.getMAKETIME();
        this.MODIFYDATE = fDate.getDate(aLCGRPCONTRENEWTRACESchema.getMODIFYDATE());
        this.MODIFYTIME = aLCGRPCONTRENEWTRACESchema.getMODIFYTIME();
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
                this.GRPCONTNO = null;
            else
                this.GRPCONTNO = rs.getString(1).trim();
            if (rs.getString(2) == null)
                this.PROPOSALGRPCONTNO = null;
            else
                this.PROPOSALGRPCONTNO = rs.getString(2).trim();
            if (rs.getString(3) == null)
                this.PRTNO = null;
            else
                this.PRTNO = rs.getString(3).trim();
            if (rs.getString(4) == null)
                this.OLDGRPCONTNO = null;
            else
                this.OLDGRPCONTNO = rs.getString(4).trim();
            this.RENEWTIMES = rs.getDouble(5);
            if (rs.getString(6) == null)
                this.STANDBYFLAG1 = null;
            else
                this.STANDBYFLAG1 = rs.getString(6).trim();
            if (rs.getString(7) == null)
                this.STANDBYFLAG2 = null;
            else
                this.STANDBYFLAG2 = rs.getString(7).trim();
            if (rs.getString(8) == null)
                this.STANDBYFLAG3 = null;
            else
                this.STANDBYFLAG3 = rs.getString(8).trim();
            if (rs.getString(9) == null)
                this.OPERATOR = null;
            else
                this.OPERATOR = rs.getString(9).trim();
            this.MAKEDATE = rs.getDate(10);
            if (rs.getString(11) == null)
                this.MAKETIME = null;
            else
                this.MAKETIME = rs.getString(11).trim();
            this.MODIFYDATE = rs.getDate(12);
            if (rs.getString(13) == null)
                this.MODIFYTIME = null;
            else
                this.MODIFYTIME = rs.getString(13).trim();
        }
        catch (SQLException sqle)
        {
            System.out.println("数据库中表LCGRPCONTRENEWTRACE字段个数和Schema中的字段个数不一致，或执行db.executeQuery查询时未使用select * from tables");
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACESchema";
            tError.functionName = "setSchema";
            tError.errorMessage = sqle.toString();
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public LCGRPCONTRENEWTRACESchema getSchema()
    {
        LCGRPCONTRENEWTRACESchema aLCGRPCONTRENEWTRACESchema = new LCGRPCONTRENEWTRACESchema();
        aLCGRPCONTRENEWTRACESchema.setSchema(this);
        return aLCGRPCONTRENEWTRACESchema;
    }

    public LCGRPCONTRENEWTRACEDB getDB()
    {
        LCGRPCONTRENEWTRACEDB aDBOper = new LCGRPCONTRENEWTRACEDB();
        aDBOper.setSchema(this);
        return aDBOper;
    }

    /**
     * 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGRPCONTRENEWTRACE描述/A>表字段
     * @return: String 返回打包后字符串
     **/
    public String encode()
    {
        StringBuffer strReturn = new StringBuffer(256);
        strReturn.append(StrTool.cTrim(GRPCONTNO));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(PROPOSALGRPCONTNO));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(PRTNO));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(OLDGRPCONTNO));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(ChgData.chgData(RENEWTIMES));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(STANDBYFLAG1));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(STANDBYFLAG2));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(STANDBYFLAG3));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(OPERATOR));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(fDate.getString(MAKEDATE)));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(MAKETIME));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(fDate.getString(MODIFYDATE)));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(MODIFYTIME));
        return strReturn.toString();
    }

    /**
     * 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGRPCONTRENEWTRACE>历史记账凭证主表信息</A>表字段
     * @param: strMessage String 包含一条纪录数据的字符串
     * @return: boolean
     **/
    public boolean decode(String strMessage)
    {
        try
        {
            GRPCONTNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER);
            PROPOSALGRPCONTNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER);
            PRTNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER);
            OLDGRPCONTNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER);
            RENEWTIMES = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 5, SysConst.PACKAGESPILTER))).doubleValue();
            STANDBYFLAG1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER);
            STANDBYFLAG2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER);
            STANDBYFLAG3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER);
            OPERATOR = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER);
            MAKEDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER));
            MAKETIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER);
            MODIFYDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER));
            MODIFYTIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER);
        }
        catch (NumberFormatException ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LCGRPCONTRENEWTRACESchema";
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
        if (FCode.equals("GRPCONTNO"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(GRPCONTNO));
        }
        if (FCode.equals("PROPOSALGRPCONTNO"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(PROPOSALGRPCONTNO));
        }
        if (FCode.equals("PRTNO"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(PRTNO));
        }
        if (FCode.equals("OLDGRPCONTNO"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(OLDGRPCONTNO));
        }
        if (FCode.equals("RENEWTIMES"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(RENEWTIMES));
        }
        if (FCode.equals("STANDBYFLAG1"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(STANDBYFLAG1));
        }
        if (FCode.equals("STANDBYFLAG2"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(STANDBYFLAG2));
        }
        if (FCode.equals("STANDBYFLAG3"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(STANDBYFLAG3));
        }
        if (FCode.equals("OPERATOR"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(OPERATOR));
        }
        if (FCode.equals("MAKEDATE"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(this.getMAKEDATE()));
        }
        if (FCode.equals("MAKETIME"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(MAKETIME));
        }
        if (FCode.equals("MODIFYDATE"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(this.getMODIFYDATE()));
        }
        if (FCode.equals("MODIFYTIME"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(MODIFYTIME));
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
                strFieldValue = StrTool.GBKToUnicode(GRPCONTNO);
                break;
            case 1:
                strFieldValue = StrTool.GBKToUnicode(PROPOSALGRPCONTNO);
                break;
            case 2:
                strFieldValue = StrTool.GBKToUnicode(PRTNO);
                break;
            case 3:
                strFieldValue = StrTool.GBKToUnicode(OLDGRPCONTNO);
                break;
            case 4:
                strFieldValue = String.valueOf(RENEWTIMES);
                break;
            case 5:
                strFieldValue = StrTool.GBKToUnicode(STANDBYFLAG1);
                break;
            case 6:
                strFieldValue = StrTool.GBKToUnicode(STANDBYFLAG2);
                break;
            case 7:
                strFieldValue = StrTool.GBKToUnicode(STANDBYFLAG3);
                break;
            case 8:
                strFieldValue = StrTool.GBKToUnicode(OPERATOR);
                break;
            case 9:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getMAKEDATE()));
                break;
            case 10:
                strFieldValue = StrTool.GBKToUnicode(MAKETIME);
                break;
            case 11:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getMODIFYDATE()));
                break;
            case 12:
                strFieldValue = StrTool.GBKToUnicode(MODIFYTIME);
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

        if (FCode.equalsIgnoreCase("GRPCONTNO"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                GRPCONTNO = FValue.trim();
            }
            else
                GRPCONTNO = null;
        }
        if (FCode.equalsIgnoreCase("PROPOSALGRPCONTNO"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                PROPOSALGRPCONTNO = FValue.trim();
            }
            else
                PROPOSALGRPCONTNO = null;
        }
        if (FCode.equalsIgnoreCase("PRTNO"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                PRTNO = FValue.trim();
            }
            else
                PRTNO = null;
        }
        if (FCode.equalsIgnoreCase("OLDGRPCONTNO"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                OLDGRPCONTNO = FValue.trim();
            }
            else
                OLDGRPCONTNO = null;
        }
        if (FCode.equalsIgnoreCase("RENEWTIMES"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Double tDouble = new Double(FValue);
                double d = tDouble.doubleValue();
                RENEWTIMES = d;
            }
        }
        if (FCode.equalsIgnoreCase("STANDBYFLAG1"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                STANDBYFLAG1 = FValue.trim();
            }
            else
                STANDBYFLAG1 = null;
        }
        if (FCode.equalsIgnoreCase("STANDBYFLAG2"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                STANDBYFLAG2 = FValue.trim();
            }
            else
                STANDBYFLAG2 = null;
        }
        if (FCode.equalsIgnoreCase("STANDBYFLAG3"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                STANDBYFLAG3 = FValue.trim();
            }
            else
                STANDBYFLAG3 = null;
        }
        if (FCode.equalsIgnoreCase("OPERATOR"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                OPERATOR = FValue.trim();
            }
            else
                OPERATOR = null;
        }
        if (FCode.equalsIgnoreCase("MAKEDATE"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                MAKEDATE = fDate.getDate(FValue);
            }
            else
                MAKEDATE = null;
        }
        if (FCode.equalsIgnoreCase("MAKETIME"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                MAKETIME = FValue.trim();
            }
            else
                MAKETIME = null;
        }
        if (FCode.equalsIgnoreCase("MODIFYDATE"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                MODIFYDATE = fDate.getDate(FValue);
            }
            else
                MODIFYDATE = null;
        }
        if (FCode.equalsIgnoreCase("MODIFYTIME"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                MODIFYTIME = FValue.trim();
            }
            else
                MODIFYTIME = null;
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
        LCGRPCONTRENEWTRACESchema other = (LCGRPCONTRENEWTRACESchema) otherObject;
        return
                (GRPCONTNO == null ? other.getGRPCONTNO() == null : GRPCONTNO.equals(other.getGRPCONTNO()))
                && (PROPOSALGRPCONTNO == null ? other.getPROPOSALGRPCONTNO() == null : PROPOSALGRPCONTNO.equals(other.getPROPOSALGRPCONTNO()))
                && (PRTNO == null ? other.getPRTNO() == null : PRTNO.equals(other.getPRTNO()))
                && (OLDGRPCONTNO == null ? other.getOLDGRPCONTNO() == null : OLDGRPCONTNO.equals(other.getOLDGRPCONTNO()))
                && RENEWTIMES == other.getRENEWTIMES()
                && (STANDBYFLAG1 == null ? other.getSTANDBYFLAG1() == null : STANDBYFLAG1.equals(other.getSTANDBYFLAG1()))
                && (STANDBYFLAG2 == null ? other.getSTANDBYFLAG2() == null : STANDBYFLAG2.equals(other.getSTANDBYFLAG2()))
                && (STANDBYFLAG3 == null ? other.getSTANDBYFLAG3() == null : STANDBYFLAG3.equals(other.getSTANDBYFLAG3()))
                && (OPERATOR == null ? other.getOPERATOR() == null : OPERATOR.equals(other.getOPERATOR()))
                && (MAKEDATE == null ? other.getMAKEDATE() == null : fDate.getString(MAKEDATE).equals(other.getMAKEDATE()))
                && (MAKETIME == null ? other.getMAKETIME() == null : MAKETIME.equals(other.getMAKETIME()))
                && (MODIFYDATE == null ? other.getMODIFYDATE() == null : fDate.getString(MODIFYDATE).equals(other.getMODIFYDATE()))
                && (MODIFYTIME == null ? other.getMODIFYTIME() == null : MODIFYTIME.equals(other.getMODIFYTIME()));
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
        if (strFieldName.equals("GRPCONTNO"))
        {
            return 0;
        }
        if (strFieldName.equals("PROPOSALGRPCONTNO"))
        {
            return 1;
        }
        if (strFieldName.equals("PRTNO"))
        {
            return 2;
        }
        if (strFieldName.equals("OLDGRPCONTNO"))
        {
            return 3;
        }
        if (strFieldName.equals("RENEWTIMES"))
        {
            return 4;
        }
        if (strFieldName.equals("STANDBYFLAG1"))
        {
            return 5;
        }
        if (strFieldName.equals("STANDBYFLAG2"))
        {
            return 6;
        }
        if (strFieldName.equals("STANDBYFLAG3"))
        {
            return 7;
        }
        if (strFieldName.equals("OPERATOR"))
        {
            return 8;
        }
        if (strFieldName.equals("MAKEDATE"))
        {
            return 9;
        }
        if (strFieldName.equals("MAKETIME"))
        {
            return 10;
        }
        if (strFieldName.equals("MODIFYDATE"))
        {
            return 11;
        }
        if (strFieldName.equals("MODIFYTIME"))
        {
            return 12;
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
                strFieldName = "GRPCONTNO";
                break;
            case 1:
                strFieldName = "PROPOSALGRPCONTNO";
                break;
            case 2:
                strFieldName = "PRTNO";
                break;
            case 3:
                strFieldName = "OLDGRPCONTNO";
                break;
            case 4:
                strFieldName = "RENEWTIMES";
                break;
            case 5:
                strFieldName = "STANDBYFLAG1";
                break;
            case 6:
                strFieldName = "STANDBYFLAG2";
                break;
            case 7:
                strFieldName = "STANDBYFLAG3";
                break;
            case 8:
                strFieldName = "OPERATOR";
                break;
            case 9:
                strFieldName = "MAKEDATE";
                break;
            case 10:
                strFieldName = "MAKETIME";
                break;
            case 11:
                strFieldName = "MODIFYDATE";
                break;
            case 12:
                strFieldName = "MODIFYTIME";
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
        if (strFieldName.equals("GRPCONTNO"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("PROPOSALGRPCONTNO"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("PRTNO"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("OLDGRPCONTNO"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("RENEWTIMES"))
        {
            return Schema.TYPE_DOUBLE;
        }
        if (strFieldName.equals("STANDBYFLAG1"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("STANDBYFLAG2"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("STANDBYFLAG3"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("OPERATOR"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("MAKEDATE"))
        {
            return Schema.TYPE_DATE;
        }
        if (strFieldName.equals("MAKETIME"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("MODIFYDATE"))
        {
            return Schema.TYPE_DATE;
        }
        if (strFieldName.equals("MODIFYTIME"))
        {
            return Schema.TYPE_STRING;
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
                nFieldType = Schema.TYPE_DOUBLE;
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
                nFieldType = Schema.TYPE_DATE;
                break;
            case 10:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 11:
                nFieldType = Schema.TYPE_DATE;
                break;
            case 12:
                nFieldType = Schema.TYPE_STRING;
                break;
            default:
                nFieldType = Schema.TYPE_NOFOUND;
        }
        return nFieldType;
    }
}
