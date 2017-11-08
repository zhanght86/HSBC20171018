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
import com.sinosoft.lis.db.PD_LMRiskFeeParamDB;

/**
 * <p>ClassName: PD_LMRiskFeeParamSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: 产品定义平台_PDM
 * @author：Makerx
 * @CreateDate：2009-03-04
 */
public class PD_LMRiskFeeParamSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMRiskFeeParamSchema.class);

    // @Field
    /** 管理费编码 */
    private String FeeCode;
    /** 交费项编码 */
    private String PayPlanCode;
    /** 保险帐户号码 */
    private String InsuAccNo;
    /** 管理费计算方式 */
    private String FeeCalMode;
    /** FeeID */
    private int FeeID;
    /** 费用下限 */
    private double FeeMin;
    /** 费用上限 */
    private double FeeMax;
    /** 管理费比例 */
    private double FeeRate;
    /** 操作员 */
    private String Operator;
    /** 入机时间 */
    private String MakeTime;
    /** 入机日期 */
    private Date MakeDate;
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

    public static final int FIELDNUM = 19; // 数据库表的字段个数

    private static String[] PK; // 主键

    private FDate fDate = new FDate(); // 处理日期

    public CErrors mErrors; // 错误信息

    // @Constructor
    public PD_LMRiskFeeParamSchema()
    {
        mErrors = new CErrors();
        String[] pk = new String[5];
        pk[0] = "FeeCode";
        pk[1] = "PayPlanCode";
        pk[2] = "InsuAccNo";
        pk[3] = "FeeCalMode";
        pk[4] = "FeeID";
        PK = pk;
    }

    /**
     * Schema克隆
     * @return Object
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException
    {
        PD_LMRiskFeeParamSchema cloned = (PD_LMRiskFeeParamSchema)super.clone();
        cloned.fDate = (FDate) fDate.clone();
        cloned.mErrors = (CErrors) mErrors.clone();
        return cloned;
    }

    // @Method
    public String[] getPK()
    {
        return PK;
    }

    public String getFeeCode()
    {
        return FeeCode;
    }

    public void setFeeCode(String aFeeCode)
    {
        FeeCode = aFeeCode;
    }

    public String getPayPlanCode()
    {
        return PayPlanCode;
    }

    public void setPayPlanCode(String aPayPlanCode)
    {
        PayPlanCode = aPayPlanCode;
    }

    public String getInsuAccNo()
    {
        return InsuAccNo;
    }

    public void setInsuAccNo(String aInsuAccNo)
    {
        InsuAccNo = aInsuAccNo;
    }

    public String getFeeCalMode()
    {
        return FeeCalMode;
    }

    public void setFeeCalMode(String aFeeCalMode)
    {
        FeeCalMode = aFeeCalMode;
    }

    public int getFeeID()
    {
        return FeeID;
    }

    public void setFeeID(int aFeeID)
    {
        FeeID = aFeeID;
    }

    public void setFeeID(String aFeeID)
    {
        if (aFeeID != null && !aFeeID.equals(""))
        {
            Integer tInteger = new Integer(aFeeID);
            int i = tInteger.intValue();
            FeeID = i;
        }
    }

    public double getFeeMin()
    {
        return FeeMin;
    }

    public void setFeeMin(double aFeeMin)
    {
        FeeMin = aFeeMin;
    }

    public void setFeeMin(String aFeeMin)
    {
        if (aFeeMin != null && !aFeeMin.equals(""))
        {
            Double tDouble = new Double(aFeeMin);
            FeeMin = tDouble.doubleValue();
        }
    }

    public double getFeeMax()
    {
        return FeeMax;
    }

    public void setFeeMax(double aFeeMax)
    {
        FeeMax = aFeeMax;
    }

    public void setFeeMax(String aFeeMax)
    {
        if (aFeeMax != null && !aFeeMax.equals(""))
        {
            Double tDouble = new Double(aFeeMax);
            FeeMax = tDouble.doubleValue();
        }
    }

    public double getFeeRate()
    {
        return FeeRate;
    }

    public void setFeeRate(double aFeeRate)
    {
        FeeRate = aFeeRate;
    }

    public void setFeeRate(String aFeeRate)
    {
        if (aFeeRate != null && !aFeeRate.equals(""))
        {
            Double tDouble = new Double(aFeeRate);
            FeeRate = tDouble.doubleValue();
        }
    }

    public String getOperator()
    {
        return Operator;
    }

    public void setOperator(String aOperator)
    {
        Operator = aOperator;
    }

    public String getMakeTime()
    {
        return MakeTime;
    }

    public void setMakeTime(String aMakeTime)
    {
        MakeTime = aMakeTime;
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
     * 使用另外一个 PD_LMRiskFeeParamSchema 对象给 Schema 赋值
     * @param: aPD_LMRiskFeeParamSchema PD_LMRiskFeeParamSchema
     **/
    public void setSchema(PD_LMRiskFeeParamSchema aPD_LMRiskFeeParamSchema)
    {
        this.FeeCode = aPD_LMRiskFeeParamSchema.getFeeCode();
        this.PayPlanCode = aPD_LMRiskFeeParamSchema.getPayPlanCode();
        this.InsuAccNo = aPD_LMRiskFeeParamSchema.getInsuAccNo();
        this.FeeCalMode = aPD_LMRiskFeeParamSchema.getFeeCalMode();
        this.FeeID = aPD_LMRiskFeeParamSchema.getFeeID();
        this.FeeMin = aPD_LMRiskFeeParamSchema.getFeeMin();
        this.FeeMax = aPD_LMRiskFeeParamSchema.getFeeMax();
        this.FeeRate = aPD_LMRiskFeeParamSchema.getFeeRate();
        this.Operator = aPD_LMRiskFeeParamSchema.getOperator();
        this.MakeTime = aPD_LMRiskFeeParamSchema.getMakeTime();
        this.MakeDate = fDate.getDate(aPD_LMRiskFeeParamSchema.getMakeDate());
        this.ModifyDate = fDate.getDate(aPD_LMRiskFeeParamSchema.getModifyDate());
        this.ModifyTime = aPD_LMRiskFeeParamSchema.getModifyTime();
        this.Standbyflag1 = aPD_LMRiskFeeParamSchema.getStandbyflag1();
        this.Standbyflag2 = aPD_LMRiskFeeParamSchema.getStandbyflag2();
        this.Standbyflag3 = aPD_LMRiskFeeParamSchema.getStandbyflag3();
        this.Standbyflag4 = aPD_LMRiskFeeParamSchema.getStandbyflag4();
        this.Standbyflag5 = aPD_LMRiskFeeParamSchema.getStandbyflag5();
        this.Standbyflag6 = aPD_LMRiskFeeParamSchema.getStandbyflag6();
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
                this.FeeCode = null;
            else
                this.FeeCode = rs.getString(1).trim();
            if (rs.getString(2) == null)
                this.PayPlanCode = null;
            else
                this.PayPlanCode = rs.getString(2).trim();
            if (rs.getString(3) == null)
                this.InsuAccNo = null;
            else
                this.InsuAccNo = rs.getString(3).trim();
            if (rs.getString(4) == null)
                this.FeeCalMode = null;
            else
                this.FeeCalMode = rs.getString(4).trim();
            this.FeeID = rs.getInt(5);
            this.FeeMin = rs.getDouble(6);
            this.FeeMax = rs.getDouble(7);
            this.FeeRate = rs.getDouble(8);
            if (rs.getString(9) == null)
                this.Operator = null;
            else
                this.Operator = rs.getString(9).trim();
            if (rs.getString(10) == null)
                this.MakeTime = null;
            else
                this.MakeTime = rs.getString(10).trim();
            this.MakeDate = rs.getDate(11);
            this.ModifyDate = rs.getDate(12);
            if (rs.getString(13) == null)
                this.ModifyTime = null;
            else
                this.ModifyTime = rs.getString(13).trim();
            if (rs.getString(14) == null)
                this.Standbyflag1 = null;
            else
                this.Standbyflag1 = rs.getString(14).trim();
            if (rs.getString(15) == null)
                this.Standbyflag2 = null;
            else
                this.Standbyflag2 = rs.getString(15).trim();
            this.Standbyflag3 = rs.getInt(16);
            this.Standbyflag4 = rs.getInt(17);
            this.Standbyflag5 = rs.getDouble(18);
            this.Standbyflag6 = rs.getDouble(19);
        }
        catch (SQLException sqle)
        {
            logger.debug("数据库中表PD_LMRiskFeeParam字段个数和Schema中的字段个数不一致，或执行db.executeQuery查询时未使用select * from tables");
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_LMRiskFeeParamSchema";
            tError.functionName = "setSchema";
            tError.errorMessage = sqle.toString();
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public PD_LMRiskFeeParamSchema getSchema()
    {
        PD_LMRiskFeeParamSchema aPD_LMRiskFeeParamSchema = new PD_LMRiskFeeParamSchema();
        aPD_LMRiskFeeParamSchema.setSchema(this);
        return aPD_LMRiskFeeParamSchema;
    }

    public PD_LMRiskFeeParamDB getDB()
    {
        PD_LMRiskFeeParamDB aDBOper = new PD_LMRiskFeeParamDB();
        aDBOper.setSchema(this);
        return aDBOper;
    }

    /**
     * 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskFeeParam描述/A>表字段
     * @return: String 返回打包后字符串
     **/
    public String encode()
    {
        StringBuffer strReturn = new StringBuffer(256);
        strReturn.append(StrTool.cTrim(FeeCode));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(PayPlanCode));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(InsuAccNo));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(FeeCalMode));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(ChgData.chgData(FeeID));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(ChgData.chgData(FeeMin));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(ChgData.chgData(FeeMax));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(ChgData.chgData(FeeRate));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(Operator));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(MakeTime));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(fDate.getString(MakeDate)));
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
     * 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskFeeParam>历史记账凭证主表信息</A>表字段
     * @param: strMessage String 包含一条纪录数据的字符串
     * @return: boolean
     **/
    public boolean decode(String strMessage)
    {
        try
        {
            FeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER);
            PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER);
            InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER);
            FeeCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER);
            FeeID = new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage, 5, SysConst.PACKAGESPILTER))).intValue();
            FeeMin = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 6, SysConst.PACKAGESPILTER))).doubleValue();
            FeeMax = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 7, SysConst.PACKAGESPILTER))).doubleValue();
            FeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 8, SysConst.PACKAGESPILTER))).doubleValue();
            Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER);
            MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER);
            MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER));
            ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER));
            ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER);
            Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER);
            Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER);
            Standbyflag3 = new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage, 16, SysConst.PACKAGESPILTER))).intValue();
            Standbyflag4 = new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage, 17, SysConst.PACKAGESPILTER))).intValue();
            Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 18, SysConst.PACKAGESPILTER))).doubleValue();
            Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 19, SysConst.PACKAGESPILTER))).doubleValue();
        }
        catch (NumberFormatException ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_LMRiskFeeParamSchema";
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
        if (FCode.equalsIgnoreCase("FeeCode"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCode));
        }
        if (FCode.equalsIgnoreCase("PayPlanCode"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
        }
        if (FCode.equalsIgnoreCase("InsuAccNo"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
        }
        if (FCode.equalsIgnoreCase("FeeCalMode"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCalMode));
        }
        if (FCode.equalsIgnoreCase("FeeID"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(FeeID));
        }
        if (FCode.equalsIgnoreCase("FeeMin"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(FeeMin));
        }
        if (FCode.equalsIgnoreCase("FeeMax"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(FeeMax));
        }
        if (FCode.equalsIgnoreCase("FeeRate"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(FeeRate));
        }
        if (FCode.equalsIgnoreCase("Operator"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
        }
        if (FCode.equalsIgnoreCase("MakeTime"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
        }
        if (FCode.equalsIgnoreCase("MakeDate"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(this.getMakeDate()));
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
                strFieldValue = StrTool.GBKToUnicode(FeeCode);
                break;
            case 1:
                strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
                break;
            case 2:
                strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
                break;
            case 3:
                strFieldValue = StrTool.GBKToUnicode(FeeCalMode);
                break;
            case 4:
                strFieldValue = String.valueOf(FeeID);
                break;
            case 5:
                strFieldValue = String.valueOf(FeeMin);
                break;
            case 6:
                strFieldValue = String.valueOf(FeeMax);
                break;
            case 7:
                strFieldValue = String.valueOf(FeeRate);
                break;
            case 8:
                strFieldValue = StrTool.GBKToUnicode(Operator);
                break;
            case 9:
                strFieldValue = StrTool.GBKToUnicode(MakeTime);
                break;
            case 10:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getMakeDate()));
                break;
            case 11:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getModifyDate()));
                break;
            case 12:
                strFieldValue = StrTool.GBKToUnicode(ModifyTime);
                break;
            case 13:
                strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
                break;
            case 14:
                strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
                break;
            case 15:
                strFieldValue = String.valueOf(Standbyflag3);
                break;
            case 16:
                strFieldValue = String.valueOf(Standbyflag4);
                break;
            case 17:
                strFieldValue = String.valueOf(Standbyflag5);
                break;
            case 18:
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

        if (FCode.equalsIgnoreCase("FeeCode"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                FeeCode = FValue.trim();
            }
            else
                FeeCode = null;
        }
        if (FCode.equalsIgnoreCase("PayPlanCode"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                PayPlanCode = FValue.trim();
            }
            else
                PayPlanCode = null;
        }
        if (FCode.equalsIgnoreCase("InsuAccNo"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                InsuAccNo = FValue.trim();
            }
            else
                InsuAccNo = null;
        }
        if (FCode.equalsIgnoreCase("FeeCalMode"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                FeeCalMode = FValue.trim();
            }
            else
                FeeCalMode = null;
        }
        if (FCode.equalsIgnoreCase("FeeID"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Integer tInteger = new Integer(FValue);
                int i = tInteger.intValue();
                FeeID = i;
            }
        }
        if (FCode.equalsIgnoreCase("FeeMin"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Double tDouble = new Double(FValue);
                double d = tDouble.doubleValue();
                FeeMin = d;
            }
        }
        if (FCode.equalsIgnoreCase("FeeMax"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Double tDouble = new Double(FValue);
                double d = tDouble.doubleValue();
                FeeMax = d;
            }
        }
        if (FCode.equalsIgnoreCase("FeeRate"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Double tDouble = new Double(FValue);
                double d = tDouble.doubleValue();
                FeeRate = d;
            }
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
        if (FCode.equalsIgnoreCase("MakeTime"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                MakeTime = FValue.trim();
            }
            else
                MakeTime = null;
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
        PD_LMRiskFeeParamSchema other = (PD_LMRiskFeeParamSchema) otherObject;
        return
                (FeeCode == null ? other.getFeeCode() == null : FeeCode.equals(other.getFeeCode()))
                && (PayPlanCode == null ? other.getPayPlanCode() == null : PayPlanCode.equals(other.getPayPlanCode()))
                && (InsuAccNo == null ? other.getInsuAccNo() == null : InsuAccNo.equals(other.getInsuAccNo()))
                && (FeeCalMode == null ? other.getFeeCalMode() == null : FeeCalMode.equals(other.getFeeCalMode()))
                && FeeID == other.getFeeID()
                && FeeMin == other.getFeeMin()
                && FeeMax == other.getFeeMax()
                && FeeRate == other.getFeeRate()
                && (Operator == null ? other.getOperator() == null : Operator.equals(other.getOperator()))
                && (MakeTime == null ? other.getMakeTime() == null : MakeTime.equals(other.getMakeTime()))
                && (MakeDate == null ? other.getMakeDate() == null : fDate.getString(MakeDate).equals(other.getMakeDate()))
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
        if (strFieldName.equals("FeeCode"))
        {
            return 0;
        }
        if (strFieldName.equals("PayPlanCode"))
        {
            return 1;
        }
        if (strFieldName.equals("InsuAccNo"))
        {
            return 2;
        }
        if (strFieldName.equals("FeeCalMode"))
        {
            return 3;
        }
        if (strFieldName.equals("FeeID"))
        {
            return 4;
        }
        if (strFieldName.equals("FeeMin"))
        {
            return 5;
        }
        if (strFieldName.equals("FeeMax"))
        {
            return 6;
        }
        if (strFieldName.equals("FeeRate"))
        {
            return 7;
        }
        if (strFieldName.equals("Operator"))
        {
            return 8;
        }
        if (strFieldName.equals("MakeTime"))
        {
            return 9;
        }
        if (strFieldName.equals("MakeDate"))
        {
            return 10;
        }
        if (strFieldName.equals("ModifyDate"))
        {
            return 11;
        }
        if (strFieldName.equals("ModifyTime"))
        {
            return 12;
        }
        if (strFieldName.equals("Standbyflag1"))
        {
            return 13;
        }
        if (strFieldName.equals("Standbyflag2"))
        {
            return 14;
        }
        if (strFieldName.equals("Standbyflag3"))
        {
            return 15;
        }
        if (strFieldName.equals("Standbyflag4"))
        {
            return 16;
        }
        if (strFieldName.equals("Standbyflag5"))
        {
            return 17;
        }
        if (strFieldName.equals("Standbyflag6"))
        {
            return 18;
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
                strFieldName = "FeeCode";
                break;
            case 1:
                strFieldName = "PayPlanCode";
                break;
            case 2:
                strFieldName = "InsuAccNo";
                break;
            case 3:
                strFieldName = "FeeCalMode";
                break;
            case 4:
                strFieldName = "FeeID";
                break;
            case 5:
                strFieldName = "FeeMin";
                break;
            case 6:
                strFieldName = "FeeMax";
                break;
            case 7:
                strFieldName = "FeeRate";
                break;
            case 8:
                strFieldName = "Operator";
                break;
            case 9:
                strFieldName = "MakeTime";
                break;
            case 10:
                strFieldName = "MakeDate";
                break;
            case 11:
                strFieldName = "ModifyDate";
                break;
            case 12:
                strFieldName = "ModifyTime";
                break;
            case 13:
                strFieldName = "Standbyflag1";
                break;
            case 14:
                strFieldName = "Standbyflag2";
                break;
            case 15:
                strFieldName = "Standbyflag3";
                break;
            case 16:
                strFieldName = "Standbyflag4";
                break;
            case 17:
                strFieldName = "Standbyflag5";
                break;
            case 18:
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
        if (strFieldName.equals("FeeCode"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("PayPlanCode"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("InsuAccNo"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("FeeCalMode"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("FeeID"))
        {
            return Schema.TYPE_INT;
        }
        if (strFieldName.equals("FeeMin"))
        {
            return Schema.TYPE_DOUBLE;
        }
        if (strFieldName.equals("FeeMax"))
        {
            return Schema.TYPE_DOUBLE;
        }
        if (strFieldName.equals("FeeRate"))
        {
            return Schema.TYPE_DOUBLE;
        }
        if (strFieldName.equals("Operator"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("MakeTime"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("MakeDate"))
        {
            return Schema.TYPE_DATE;
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
                nFieldType = Schema.TYPE_INT;
                break;
            case 5:
                nFieldType = Schema.TYPE_DOUBLE;
                break;
            case 6:
                nFieldType = Schema.TYPE_DOUBLE;
                break;
            case 7:
                nFieldType = Schema.TYPE_DOUBLE;
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
                nFieldType = Schema.TYPE_DATE;
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
                nFieldType = Schema.TYPE_INT;
                break;
            case 16:
                nFieldType = Schema.TYPE_INT;
                break;
            case 17:
                nFieldType = Schema.TYPE_DOUBLE;
                break;
            case 18:
                nFieldType = Schema.TYPE_DOUBLE;
                break;
            default:
                nFieldType = Schema.TYPE_NOFOUND;
        }
        return nFieldType;
    }
}
