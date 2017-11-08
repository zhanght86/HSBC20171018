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
import com.sinosoft.lis.db.PD_BaseFieldDB;

/**
 * <p>ClassName: PD_BaseFieldSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: PhysicalDataModel_1
 * @author：Makerx
 * @CreateDate：2009-10-13
 */
public class PD_BaseFieldSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_BaseFieldSchema.class);

    // @Field
    /** 表名代码 */
    private String TableCode;
    /** 字段编码 */
    private String FieldCode;
    /** 字段名称 */
    private String FieldName;
    /** 字段类型 */
    private String FieldType;
    /** 官方描述 */
    private String OfficialDesc;
    /** 业务描述 */
    private String BusiDesc;
    /** 是否显示 */
    private String IsDisplay;
    /** 显示顺序 */
    private double DisplayOrder;
    /** 是否主键 */
    private String IsPrimary;
    /** 下拉标识码 */
    private String SelectCode;
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
    /** 字段类型名称 */
    private String FieldTypeName;
    /** 字段默认值 */
    private String FieldInitValue;

    public static final int FIELDNUM = 23; // 数据库表的字段个数

    private static String[] PK; // 主键

    private FDate fDate = new FDate(); // 处理日期

    public CErrors mErrors; // 错误信息

    // @Constructor
    public PD_BaseFieldSchema()
    {
        mErrors = new CErrors();
        String[] pk = new String[2];
        pk[0] = "TableCode";
        pk[1] = "FieldCode";
        PK = pk;
    }

    /**
     * Schema克隆
     * @return Object
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException
    {
        PD_BaseFieldSchema cloned = (PD_BaseFieldSchema)super.clone();
        cloned.fDate = (FDate) fDate.clone();
        cloned.mErrors = (CErrors) mErrors.clone();
        return cloned;
    }

    // @Method
    public String[] getPK()
    {
        return PK;
    }

    public String getTableCode()
    {
        return TableCode;
    }

    public void setTableCode(String aTableCode)
    {
        TableCode = aTableCode;
    }

    public String getFieldCode()
    {
        return FieldCode;
    }

    public void setFieldCode(String aFieldCode)
    {
        FieldCode = aFieldCode;
    }

    public String getFieldName()
    {
        return FieldName;
    }

    public void setFieldName(String aFieldName)
    {
        FieldName = aFieldName;
    }

    public String getFieldType()
    {
        return FieldType;
    }

    public void setFieldType(String aFieldType)
    {
        FieldType = aFieldType;
    }

    public String getOfficialDesc()
    {
        return OfficialDesc;
    }

    public void setOfficialDesc(String aOfficialDesc)
    {
        OfficialDesc = aOfficialDesc;
    }

    public String getBusiDesc()
    {
        return BusiDesc;
    }

    public void setBusiDesc(String aBusiDesc)
    {
        BusiDesc = aBusiDesc;
    }

    public String getIsDisplay()
    {
        return IsDisplay;
    }

    public void setIsDisplay(String aIsDisplay)
    {
        IsDisplay = aIsDisplay;
    }

    public double getDisplayOrder()
    {
        return DisplayOrder;
    }

    public void setDisplayOrder(double aDisplayOrder)
    {
        DisplayOrder = aDisplayOrder;
    }

    public void setDisplayOrder(String aDisplayOrder)
    {
        if (aDisplayOrder != null && !aDisplayOrder.equals(""))
        {
            Double tDouble = new Double(aDisplayOrder);
            DisplayOrder = tDouble.doubleValue();
        }
    }

    public String getIsPrimary()
    {
        return IsPrimary;
    }

    public void setIsPrimary(String aIsPrimary)
    {
        IsPrimary = aIsPrimary;
    }

    public String getSelectCode()
    {
        return SelectCode;
    }

    public void setSelectCode(String aSelectCode)
    {
        SelectCode = aSelectCode;
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

    public String getFieldTypeName()
    {
        return FieldTypeName;
    }

    public void setFieldTypeName(String aFieldTypeName)
    {
        FieldTypeName = aFieldTypeName;
    }

    public String getFieldInitValue()
    {
        return FieldInitValue;
    }

    public void setFieldInitValue(String aFieldInitValue)
    {
        FieldInitValue = aFieldInitValue;
    }

    /**
     * 使用另外一个 PD_BaseFieldSchema 对象给 Schema 赋值
     * @param: aPD_BaseFieldSchema PD_BaseFieldSchema
     **/
    public void setSchema(PD_BaseFieldSchema aPD_BaseFieldSchema)
    {
        this.TableCode = aPD_BaseFieldSchema.getTableCode();
        this.FieldCode = aPD_BaseFieldSchema.getFieldCode();
        this.FieldName = aPD_BaseFieldSchema.getFieldName();
        this.FieldType = aPD_BaseFieldSchema.getFieldType();
        this.OfficialDesc = aPD_BaseFieldSchema.getOfficialDesc();
        this.BusiDesc = aPD_BaseFieldSchema.getBusiDesc();
        this.IsDisplay = aPD_BaseFieldSchema.getIsDisplay();
        this.DisplayOrder = aPD_BaseFieldSchema.getDisplayOrder();
        this.IsPrimary = aPD_BaseFieldSchema.getIsPrimary();
        this.SelectCode = aPD_BaseFieldSchema.getSelectCode();
        this.Operator = aPD_BaseFieldSchema.getOperator();
        this.MakeDate = fDate.getDate(aPD_BaseFieldSchema.getMakeDate());
        this.MakeTime = aPD_BaseFieldSchema.getMakeTime();
        this.ModifyDate = fDate.getDate(aPD_BaseFieldSchema.getModifyDate());
        this.ModifyTime = aPD_BaseFieldSchema.getModifyTime();
        this.Standbyflag1 = aPD_BaseFieldSchema.getStandbyflag1();
        this.Standbyflag2 = aPD_BaseFieldSchema.getStandbyflag2();
        this.Standbyflag3 = aPD_BaseFieldSchema.getStandbyflag3();
        this.Standbyflag4 = aPD_BaseFieldSchema.getStandbyflag4();
        this.Standbyflag5 = aPD_BaseFieldSchema.getStandbyflag5();
        this.Standbyflag6 = aPD_BaseFieldSchema.getStandbyflag6();
        this.FieldTypeName = aPD_BaseFieldSchema.getFieldTypeName();
        this.FieldInitValue = aPD_BaseFieldSchema.getFieldInitValue();
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
                this.TableCode = null;
            else
                this.TableCode = rs.getString(1).trim();
            if (rs.getString(2) == null)
                this.FieldCode = null;
            else
                this.FieldCode = rs.getString(2).trim();
            if (rs.getString(3) == null)
                this.FieldName = null;
            else
                this.FieldName = rs.getString(3).trim();
            if (rs.getString(4) == null)
                this.FieldType = null;
            else
                this.FieldType = rs.getString(4).trim();
            if (rs.getString(5) == null)
                this.OfficialDesc = null;
            else
                this.OfficialDesc = rs.getString(5).trim();
            if (rs.getString(6) == null)
                this.BusiDesc = null;
            else
                this.BusiDesc = rs.getString(6).trim();
            if (rs.getString(7) == null)
                this.IsDisplay = null;
            else
                this.IsDisplay = rs.getString(7).trim();
            this.DisplayOrder = rs.getDouble(8);
            if (rs.getString(9) == null)
                this.IsPrimary = null;
            else
                this.IsPrimary = rs.getString(9).trim();
            if (rs.getString(10) == null)
                this.SelectCode = null;
            else
                this.SelectCode = rs.getString(10).trim();
            if (rs.getString(11) == null)
                this.Operator = null;
            else
                this.Operator = rs.getString(11).trim();
            this.MakeDate = rs.getDate(12);
            if (rs.getString(13) == null)
                this.MakeTime = null;
            else
                this.MakeTime = rs.getString(13).trim();
            this.ModifyDate = rs.getDate(14);
            if (rs.getString(15) == null)
                this.ModifyTime = null;
            else
                this.ModifyTime = rs.getString(15).trim();
            if (rs.getString(16) == null)
                this.Standbyflag1 = null;
            else
                this.Standbyflag1 = rs.getString(16).trim();
            if (rs.getString(17) == null)
                this.Standbyflag2 = null;
            else
                this.Standbyflag2 = rs.getString(17).trim();
            this.Standbyflag3 = rs.getInt(18);
            this.Standbyflag4 = rs.getInt(19);
            this.Standbyflag5 = rs.getDouble(20);
            this.Standbyflag6 = rs.getDouble(21);
            if (rs.getString(22) == null)
                this.FieldTypeName = null;
            else
                this.FieldTypeName = rs.getString(22).trim();
            if (rs.getString(23) == null)
                this.FieldInitValue = null;
            else
                this.FieldInitValue = rs.getString(23).trim();
        }
        catch (SQLException sqle)
        {
            logger.debug("数据库中表PD_BaseField字段个数和Schema中的字段个数不一致，或执行db.executeQuery查询时未使用select * from tables");
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_BaseFieldSchema";
            tError.functionName = "setSchema";
            tError.errorMessage = sqle.toString();
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public PD_BaseFieldSchema getSchema()
    {
        PD_BaseFieldSchema aPD_BaseFieldSchema = new PD_BaseFieldSchema();
        aPD_BaseFieldSchema.setSchema(this);
        return aPD_BaseFieldSchema;
    }

    public PD_BaseFieldDB getDB()
    {
        PD_BaseFieldDB aDBOper = new PD_BaseFieldDB();
        aDBOper.setSchema(this);
        return aDBOper;
    }

    /**
     * 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_BaseField描述/A>表字段
     * @return: String 返回打包后字符串
     **/
    public String encode()
    {
        StringBuffer strReturn = new StringBuffer(256);
        strReturn.append(StrTool.cTrim(TableCode));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(FieldCode));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(FieldName));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(FieldType));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(OfficialDesc));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(BusiDesc));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(IsDisplay));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(ChgData.chgData(DisplayOrder));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(IsPrimary));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(SelectCode));
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
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(FieldTypeName));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(FieldInitValue));
        return strReturn.toString();
    }

    /**
     * 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_BaseField>历史记账凭证主表信息</A>表字段
     * @param: strMessage String 包含一条纪录数据的字符串
     * @return: boolean
     **/
    public boolean decode(String strMessage)
    {
        try
        {
            TableCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER);
            FieldCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER);
            FieldName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER);
            FieldType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER);
            OfficialDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER);
            BusiDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER);
            IsDisplay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER);
            DisplayOrder = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 8, SysConst.PACKAGESPILTER))).doubleValue();
            IsPrimary = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER);
            SelectCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER);
            Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER);
            MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER));
            MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER);
            ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER));
            ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER);
            Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER);
            Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER);
            Standbyflag3 = new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage, 18, SysConst.PACKAGESPILTER))).intValue();
            Standbyflag4 = new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage, 19, SysConst.PACKAGESPILTER))).intValue();
            Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 20, SysConst.PACKAGESPILTER))).doubleValue();
            Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 21, SysConst.PACKAGESPILTER))).doubleValue();
            FieldTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER);
            FieldInitValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER);
        }
        catch (NumberFormatException ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_BaseFieldSchema";
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
        if (FCode.equalsIgnoreCase("TableCode"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(TableCode));
        }
        if (FCode.equalsIgnoreCase("FieldCode"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(FieldCode));
        }
        if (FCode.equalsIgnoreCase("FieldName"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(FieldName));
        }
        if (FCode.equalsIgnoreCase("FieldType"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(FieldType));
        }
        if (FCode.equalsIgnoreCase("OfficialDesc"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(OfficialDesc));
        }
        if (FCode.equalsIgnoreCase("BusiDesc"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(BusiDesc));
        }
        if (FCode.equalsIgnoreCase("IsDisplay"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(IsDisplay));
        }
        if (FCode.equalsIgnoreCase("DisplayOrder"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(DisplayOrder));
        }
        if (FCode.equalsIgnoreCase("IsPrimary"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(IsPrimary));
        }
        if (FCode.equalsIgnoreCase("SelectCode"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(SelectCode));
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
        if (FCode.equalsIgnoreCase("FieldTypeName"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(FieldTypeName));
        }
        if (FCode.equalsIgnoreCase("FieldInitValue"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(FieldInitValue));
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
                strFieldValue = StrTool.GBKToUnicode(TableCode);
                break;
            case 1:
                strFieldValue = StrTool.GBKToUnicode(FieldCode);
                break;
            case 2:
                strFieldValue = StrTool.GBKToUnicode(FieldName);
                break;
            case 3:
                strFieldValue = StrTool.GBKToUnicode(FieldType);
                break;
            case 4:
                strFieldValue = StrTool.GBKToUnicode(OfficialDesc);
                break;
            case 5:
                strFieldValue = StrTool.GBKToUnicode(BusiDesc);
                break;
            case 6:
                strFieldValue = StrTool.GBKToUnicode(IsDisplay);
                break;
            case 7:
                strFieldValue = String.valueOf(DisplayOrder);
                break;
            case 8:
                strFieldValue = StrTool.GBKToUnicode(IsPrimary);
                break;
            case 9:
                strFieldValue = StrTool.GBKToUnicode(SelectCode);
                break;
            case 10:
                strFieldValue = StrTool.GBKToUnicode(Operator);
                break;
            case 11:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getMakeDate()));
                break;
            case 12:
                strFieldValue = StrTool.GBKToUnicode(MakeTime);
                break;
            case 13:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getModifyDate()));
                break;
            case 14:
                strFieldValue = StrTool.GBKToUnicode(ModifyTime);
                break;
            case 15:
                strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
                break;
            case 16:
                strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
                break;
            case 17:
                strFieldValue = String.valueOf(Standbyflag3);
                break;
            case 18:
                strFieldValue = String.valueOf(Standbyflag4);
                break;
            case 19:
                strFieldValue = String.valueOf(Standbyflag5);
                break;
            case 20:
                strFieldValue = String.valueOf(Standbyflag6);
                break;
            case 21:
                strFieldValue = StrTool.GBKToUnicode(FieldTypeName);
                break;
            case 22:
                strFieldValue = StrTool.GBKToUnicode(FieldInitValue);
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

        if (FCode.equalsIgnoreCase("TableCode"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                TableCode = FValue.trim();
            }
            else
                TableCode = null;
        }
        if (FCode.equalsIgnoreCase("FieldCode"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                FieldCode = FValue.trim();
            }
            else
                FieldCode = null;
        }
        if (FCode.equalsIgnoreCase("FieldName"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                FieldName = FValue.trim();
            }
            else
                FieldName = null;
        }
        if (FCode.equalsIgnoreCase("FieldType"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                FieldType = FValue.trim();
            }
            else
                FieldType = null;
        }
        if (FCode.equalsIgnoreCase("OfficialDesc"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                OfficialDesc = FValue.trim();
            }
            else
                OfficialDesc = null;
        }
        if (FCode.equalsIgnoreCase("BusiDesc"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                BusiDesc = FValue.trim();
            }
            else
                BusiDesc = null;
        }
        if (FCode.equalsIgnoreCase("IsDisplay"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                IsDisplay = FValue.trim();
            }
            else
                IsDisplay = null;
        }
        if (FCode.equalsIgnoreCase("DisplayOrder"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Double tDouble = new Double(FValue);
                double d = tDouble.doubleValue();
                DisplayOrder = d;
            }
        }
        if (FCode.equalsIgnoreCase("IsPrimary"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                IsPrimary = FValue.trim();
            }
            else
                IsPrimary = null;
        }
        if (FCode.equalsIgnoreCase("SelectCode"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                SelectCode = FValue.trim();
            }
            else
                SelectCode = null;
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
        if (FCode.equalsIgnoreCase("FieldTypeName"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                FieldTypeName = FValue.trim();
            }
            else
                FieldTypeName = null;
        }
        if (FCode.equalsIgnoreCase("FieldInitValue"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                FieldInitValue = FValue.trim();
            }
            else
                FieldInitValue = null;
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
        PD_BaseFieldSchema other = (PD_BaseFieldSchema) otherObject;
        return
                (TableCode == null ? other.getTableCode() == null : TableCode.equals(other.getTableCode()))
                && (FieldCode == null ? other.getFieldCode() == null : FieldCode.equals(other.getFieldCode()))
                && (FieldName == null ? other.getFieldName() == null : FieldName.equals(other.getFieldName()))
                && (FieldType == null ? other.getFieldType() == null : FieldType.equals(other.getFieldType()))
                && (OfficialDesc == null ? other.getOfficialDesc() == null : OfficialDesc.equals(other.getOfficialDesc()))
                && (BusiDesc == null ? other.getBusiDesc() == null : BusiDesc.equals(other.getBusiDesc()))
                && (IsDisplay == null ? other.getIsDisplay() == null : IsDisplay.equals(other.getIsDisplay()))
                && DisplayOrder == other.getDisplayOrder()
                && (IsPrimary == null ? other.getIsPrimary() == null : IsPrimary.equals(other.getIsPrimary()))
                && (SelectCode == null ? other.getSelectCode() == null : SelectCode.equals(other.getSelectCode()))
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
                && Standbyflag6 == other.getStandbyflag6()
                && (FieldTypeName == null ? other.getFieldTypeName() == null : FieldTypeName.equals(other.getFieldTypeName()))
                && (FieldInitValue == null ? other.getFieldInitValue() == null : FieldInitValue.equals(other.getFieldInitValue()));
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
        if (strFieldName.equals("TableCode"))
        {
            return 0;
        }
        if (strFieldName.equals("FieldCode"))
        {
            return 1;
        }
        if (strFieldName.equals("FieldName"))
        {
            return 2;
        }
        if (strFieldName.equals("FieldType"))
        {
            return 3;
        }
        if (strFieldName.equals("OfficialDesc"))
        {
            return 4;
        }
        if (strFieldName.equals("BusiDesc"))
        {
            return 5;
        }
        if (strFieldName.equals("IsDisplay"))
        {
            return 6;
        }
        if (strFieldName.equals("DisplayOrder"))
        {
            return 7;
        }
        if (strFieldName.equals("IsPrimary"))
        {
            return 8;
        }
        if (strFieldName.equals("SelectCode"))
        {
            return 9;
        }
        if (strFieldName.equals("Operator"))
        {
            return 10;
        }
        if (strFieldName.equals("MakeDate"))
        {
            return 11;
        }
        if (strFieldName.equals("MakeTime"))
        {
            return 12;
        }
        if (strFieldName.equals("ModifyDate"))
        {
            return 13;
        }
        if (strFieldName.equals("ModifyTime"))
        {
            return 14;
        }
        if (strFieldName.equals("Standbyflag1"))
        {
            return 15;
        }
        if (strFieldName.equals("Standbyflag2"))
        {
            return 16;
        }
        if (strFieldName.equals("Standbyflag3"))
        {
            return 17;
        }
        if (strFieldName.equals("Standbyflag4"))
        {
            return 18;
        }
        if (strFieldName.equals("Standbyflag5"))
        {
            return 19;
        }
        if (strFieldName.equals("Standbyflag6"))
        {
            return 20;
        }
        if (strFieldName.equals("FieldTypeName"))
        {
            return 21;
        }
        if (strFieldName.equals("FieldInitValue"))
        {
            return 22;
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
                strFieldName = "TableCode";
                break;
            case 1:
                strFieldName = "FieldCode";
                break;
            case 2:
                strFieldName = "FieldName";
                break;
            case 3:
                strFieldName = "FieldType";
                break;
            case 4:
                strFieldName = "OfficialDesc";
                break;
            case 5:
                strFieldName = "BusiDesc";
                break;
            case 6:
                strFieldName = "IsDisplay";
                break;
            case 7:
                strFieldName = "DisplayOrder";
                break;
            case 8:
                strFieldName = "IsPrimary";
                break;
            case 9:
                strFieldName = "SelectCode";
                break;
            case 10:
                strFieldName = "Operator";
                break;
            case 11:
                strFieldName = "MakeDate";
                break;
            case 12:
                strFieldName = "MakeTime";
                break;
            case 13:
                strFieldName = "ModifyDate";
                break;
            case 14:
                strFieldName = "ModifyTime";
                break;
            case 15:
                strFieldName = "Standbyflag1";
                break;
            case 16:
                strFieldName = "Standbyflag2";
                break;
            case 17:
                strFieldName = "Standbyflag3";
                break;
            case 18:
                strFieldName = "Standbyflag4";
                break;
            case 19:
                strFieldName = "Standbyflag5";
                break;
            case 20:
                strFieldName = "Standbyflag6";
                break;
            case 21:
                strFieldName = "FieldTypeName";
                break;
            case 22:
                strFieldName = "FieldInitValue";
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
        if (strFieldName.equals("TableCode"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("FieldCode"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("FieldName"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("FieldType"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("OfficialDesc"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("BusiDesc"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("IsDisplay"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("DisplayOrder"))
        {
            return Schema.TYPE_DOUBLE;
        }
        if (strFieldName.equals("IsPrimary"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("SelectCode"))
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
        if (strFieldName.equals("FieldTypeName"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("FieldInitValue"))
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
                nFieldType = Schema.TYPE_STRING;
                break;
            case 5:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 6:
                nFieldType = Schema.TYPE_STRING;
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
                nFieldType = Schema.TYPE_STRING;
                break;
            case 11:
                nFieldType = Schema.TYPE_DATE;
                break;
            case 12:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 13:
                nFieldType = Schema.TYPE_DATE;
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
                nFieldType = Schema.TYPE_INT;
                break;
            case 18:
                nFieldType = Schema.TYPE_INT;
                break;
            case 19:
                nFieldType = Schema.TYPE_DOUBLE;
                break;
            case 20:
                nFieldType = Schema.TYPE_DOUBLE;
                break;
            case 21:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 22:
                nFieldType = Schema.TYPE_STRING;
                break;
            default:
                nFieldType = Schema.TYPE_NOFOUND;
        }
        return nFieldType;
    }
}
