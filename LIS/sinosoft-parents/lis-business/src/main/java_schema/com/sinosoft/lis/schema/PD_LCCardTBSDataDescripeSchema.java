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
import com.sinosoft.lis.db.PD_LCCardTBSDataDescripeDB;

/**
 * <p>ClassName: PD_LCCardTBSDataDescripeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: 卡单
 * @author：Makerx
 * @CreateDate：2009-04-21
 */
public class PD_LCCardTBSDataDescripeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LCCardTBSDataDescripeSchema.class);

    // @Field
    /** 套餐名称 */
    private String SetMealName;
    /** 套餐代码 */
    private String SetMealCode;
    /** 投保受区分 */
    private String TBSFlag;
    /** 对应中间表字段 */
    private String TMPColumns;
    /** 对应数据库表字段名 */
    private String TableColumsName;
    /** 对应数据库表字段中文名 */
    private String TableColumsCName;
    /** 入机日期 */
    private Date MakeDate;
    /** 入机时间 */
    private String MakeTime;
    /** 最后一次修改日期 */
    private Date ModifyDate;
    /** 最后一次修改时间 */
    private String ModifyTime;
    /** 操作员 */
    private String Operator;
    /** 备用字段1 */
    private String StandbyFlag1;
    /** 备用字段2 */
    private String StandbyFlag2;
    /** 备用字段3 */
    private String StandbyFlag3;

    public static final int FIELDNUM = 14; // 数据库表的字段个数

    private static String[] PK; // 主键

    private FDate fDate = new FDate(); // 处理日期

    public CErrors mErrors; // 错误信息

    // @Constructor
    public PD_LCCardTBSDataDescripeSchema()
    {
        mErrors = new CErrors();
        String[] pk = new String[3];
        pk[0] = "SetMealCode";
        pk[1] = "TBSFlag";
        pk[2] = "TMPColumns";
        PK = pk;
    }

    /**
     * Schema克隆
     * @return Object
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException
    {
        PD_LCCardTBSDataDescripeSchema cloned = (PD_LCCardTBSDataDescripeSchema)super.clone();
        cloned.fDate = (FDate) fDate.clone();
        cloned.mErrors = (CErrors) mErrors.clone();
        return cloned;
    }

    // @Method
    public String[] getPK()
    {
        return PK;
    }

    public String getSetMealName()
    {
        return SetMealName;
    }

    public void setSetMealName(String aSetMealName)
    {
        SetMealName = aSetMealName;
    }

    public String getSetMealCode()
    {
        return SetMealCode;
    }

    public void setSetMealCode(String aSetMealCode)
    {
        SetMealCode = aSetMealCode;
    }

    public String getTBSFlag()
    {
        return TBSFlag;
    }

    public void setTBSFlag(String aTBSFlag)
    {
        TBSFlag = aTBSFlag;
    }

    public String getTMPColumns()
    {
        return TMPColumns;
    }

    public void setTMPColumns(String aTMPColumns)
    {
        TMPColumns = aTMPColumns;
    }

    public String getTableColumsName()
    {
        return TableColumsName;
    }

    public void setTableColumsName(String aTableColumsName)
    {
        TableColumsName = aTableColumsName;
    }

    public String getTableColumsCName()
    {
        return TableColumsCName;
    }

    public void setTableColumsCName(String aTableColumsCName)
    {
        TableColumsCName = aTableColumsCName;
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

    public String getOperator()
    {
        return Operator;
    }

    public void setOperator(String aOperator)
    {
        Operator = aOperator;
    }

    public String getStandbyFlag1()
    {
        return StandbyFlag1;
    }

    public void setStandbyFlag1(String aStandbyFlag1)
    {
        StandbyFlag1 = aStandbyFlag1;
    }

    public String getStandbyFlag2()
    {
        return StandbyFlag2;
    }

    public void setStandbyFlag2(String aStandbyFlag2)
    {
        StandbyFlag2 = aStandbyFlag2;
    }

    public String getStandbyFlag3()
    {
        return StandbyFlag3;
    }

    public void setStandbyFlag3(String aStandbyFlag3)
    {
        StandbyFlag3 = aStandbyFlag3;
    }

    /**
     * 使用另外一个 PD_LCCardTBSDataDescripeSchema 对象给 Schema 赋值
     * @param: aPD_LCCardTBSDataDescripeSchema PD_LCCardTBSDataDescripeSchema
     **/
    public void setSchema(PD_LCCardTBSDataDescripeSchema aPD_LCCardTBSDataDescripeSchema)
    {
        this.SetMealName = aPD_LCCardTBSDataDescripeSchema.getSetMealName();
        this.SetMealCode = aPD_LCCardTBSDataDescripeSchema.getSetMealCode();
        this.TBSFlag = aPD_LCCardTBSDataDescripeSchema.getTBSFlag();
        this.TMPColumns = aPD_LCCardTBSDataDescripeSchema.getTMPColumns();
        this.TableColumsName = aPD_LCCardTBSDataDescripeSchema.getTableColumsName();
        this.TableColumsCName = aPD_LCCardTBSDataDescripeSchema.getTableColumsCName();
        this.MakeDate = fDate.getDate(aPD_LCCardTBSDataDescripeSchema.getMakeDate());
        this.MakeTime = aPD_LCCardTBSDataDescripeSchema.getMakeTime();
        this.ModifyDate = fDate.getDate(aPD_LCCardTBSDataDescripeSchema.getModifyDate());
        this.ModifyTime = aPD_LCCardTBSDataDescripeSchema.getModifyTime();
        this.Operator = aPD_LCCardTBSDataDescripeSchema.getOperator();
        this.StandbyFlag1 = aPD_LCCardTBSDataDescripeSchema.getStandbyFlag1();
        this.StandbyFlag2 = aPD_LCCardTBSDataDescripeSchema.getStandbyFlag2();
        this.StandbyFlag3 = aPD_LCCardTBSDataDescripeSchema.getStandbyFlag3();
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
                this.SetMealName = null;
            else
                this.SetMealName = rs.getString(1).trim();
            if (rs.getString(2) == null)
                this.SetMealCode = null;
            else
                this.SetMealCode = rs.getString(2).trim();
            if (rs.getString(3) == null)
                this.TBSFlag = null;
            else
                this.TBSFlag = rs.getString(3).trim();
            if (rs.getString(4) == null)
                this.TMPColumns = null;
            else
                this.TMPColumns = rs.getString(4).trim();
            if (rs.getString(5) == null)
                this.TableColumsName = null;
            else
                this.TableColumsName = rs.getString(5).trim();
            if (rs.getString(6) == null)
                this.TableColumsCName = null;
            else
                this.TableColumsCName = rs.getString(6).trim();
            this.MakeDate = rs.getDate(7);
            if (rs.getString(8) == null)
                this.MakeTime = null;
            else
                this.MakeTime = rs.getString(8).trim();
            this.ModifyDate = rs.getDate(9);
            if (rs.getString(10) == null)
                this.ModifyTime = null;
            else
                this.ModifyTime = rs.getString(10).trim();
            if (rs.getString(11) == null)
                this.Operator = null;
            else
                this.Operator = rs.getString(11).trim();
            if (rs.getString(12) == null)
                this.StandbyFlag1 = null;
            else
                this.StandbyFlag1 = rs.getString(12).trim();
            if (rs.getString(13) == null)
                this.StandbyFlag2 = null;
            else
                this.StandbyFlag2 = rs.getString(13).trim();
            if (rs.getString(14) == null)
                this.StandbyFlag3 = null;
            else
                this.StandbyFlag3 = rs.getString(14).trim();
        }
        catch (SQLException sqle)
        {
            logger.debug("数据库中表PD_LCCardTBSDataDescripe字段个数和Schema中的字段个数不一致，或执行db.executeQuery查询时未使用select * from tables");
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_LCCardTBSDataDescripeSchema";
            tError.functionName = "setSchema";
            tError.errorMessage = sqle.toString();
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public PD_LCCardTBSDataDescripeSchema getSchema()
    {
        PD_LCCardTBSDataDescripeSchema aPD_LCCardTBSDataDescripeSchema = new PD_LCCardTBSDataDescripeSchema();
        aPD_LCCardTBSDataDescripeSchema.setSchema(this);
        return aPD_LCCardTBSDataDescripeSchema;
    }

    public PD_LCCardTBSDataDescripeDB getDB()
    {
        PD_LCCardTBSDataDescripeDB aDBOper = new PD_LCCardTBSDataDescripeDB();
        aDBOper.setSchema(this);
        return aDBOper;
    }

    /**
     * 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LCCardTBSDataDescripe描述/A>表字段
     * @return: String 返回打包后字符串
     **/
    public String encode()
    {
        StringBuffer strReturn = new StringBuffer(256);
        strReturn.append(StrTool.cTrim(SetMealName));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(SetMealCode));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(TBSFlag));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(TMPColumns));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(TableColumsName));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(TableColumsCName));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(fDate.getString(MakeDate)));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(MakeTime));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(fDate.getString(ModifyDate)));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(ModifyTime));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(Operator));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandbyFlag1));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandbyFlag2));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(StandbyFlag3));
        return strReturn.toString();
    }

    /**
     * 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LCCardTBSDataDescripe>历史记账凭证主表信息</A>表字段
     * @param: strMessage String 包含一条纪录数据的字符串
     * @return: boolean
     **/
    public boolean decode(String strMessage)
    {
        try
        {
            SetMealName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER);
            SetMealCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER);
            TBSFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER);
            TMPColumns = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER);
            TableColumsName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER);
            TableColumsCName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER);
            MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER));
            MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER);
            ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER));
            ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER);
            Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER);
            StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER);
            StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER);
            StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER);
        }
        catch (NumberFormatException ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_LCCardTBSDataDescripeSchema";
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
        if (FCode.equals("SetMealName"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(SetMealName));
        }
        if (FCode.equals("SetMealCode"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(SetMealCode));
        }
        if (FCode.equals("TBSFlag"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(TBSFlag));
        }
        if (FCode.equals("TMPColumns"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(TMPColumns));
        }
        if (FCode.equals("TableColumsName"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(TableColumsName));
        }
        if (FCode.equals("TableColumsCName"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(TableColumsCName));
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
        if (FCode.equals("Operator"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
        }
        if (FCode.equals("StandbyFlag1"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
        }
        if (FCode.equals("StandbyFlag2"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
        }
        if (FCode.equals("StandbyFlag3"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag3));
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
                strFieldValue = StrTool.GBKToUnicode(SetMealName);
                break;
            case 1:
                strFieldValue = StrTool.GBKToUnicode(SetMealCode);
                break;
            case 2:
                strFieldValue = StrTool.GBKToUnicode(TBSFlag);
                break;
            case 3:
                strFieldValue = StrTool.GBKToUnicode(TMPColumns);
                break;
            case 4:
                strFieldValue = StrTool.GBKToUnicode(TableColumsName);
                break;
            case 5:
                strFieldValue = StrTool.GBKToUnicode(TableColumsCName);
                break;
            case 6:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getMakeDate()));
                break;
            case 7:
                strFieldValue = StrTool.GBKToUnicode(MakeTime);
                break;
            case 8:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getModifyDate()));
                break;
            case 9:
                strFieldValue = StrTool.GBKToUnicode(ModifyTime);
                break;
            case 10:
                strFieldValue = StrTool.GBKToUnicode(Operator);
                break;
            case 11:
                strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
                break;
            case 12:
                strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
                break;
            case 13:
                strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
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

        if (FCode.equalsIgnoreCase("SetMealName"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                SetMealName = FValue.trim();
            }
            else
                SetMealName = null;
        }
        if (FCode.equalsIgnoreCase("SetMealCode"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                SetMealCode = FValue.trim();
            }
            else
                SetMealCode = null;
        }
        if (FCode.equalsIgnoreCase("TBSFlag"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                TBSFlag = FValue.trim();
            }
            else
                TBSFlag = null;
        }
        if (FCode.equalsIgnoreCase("TMPColumns"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                TMPColumns = FValue.trim();
            }
            else
                TMPColumns = null;
        }
        if (FCode.equalsIgnoreCase("TableColumsName"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                TableColumsName = FValue.trim();
            }
            else
                TableColumsName = null;
        }
        if (FCode.equalsIgnoreCase("TableColumsCName"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                TableColumsCName = FValue.trim();
            }
            else
                TableColumsCName = null;
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
        if (FCode.equalsIgnoreCase("Operator"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                Operator = FValue.trim();
            }
            else
                Operator = null;
        }
        if (FCode.equalsIgnoreCase("StandbyFlag1"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandbyFlag1 = FValue.trim();
            }
            else
                StandbyFlag1 = null;
        }
        if (FCode.equalsIgnoreCase("StandbyFlag2"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandbyFlag2 = FValue.trim();
            }
            else
                StandbyFlag2 = null;
        }
        if (FCode.equalsIgnoreCase("StandbyFlag3"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                StandbyFlag3 = FValue.trim();
            }
            else
                StandbyFlag3 = null;
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
        PD_LCCardTBSDataDescripeSchema other = (PD_LCCardTBSDataDescripeSchema) otherObject;
        return
                (SetMealName == null ? other.getSetMealName() == null : SetMealName.equals(other.getSetMealName()))
                && (SetMealCode == null ? other.getSetMealCode() == null : SetMealCode.equals(other.getSetMealCode()))
                && (TBSFlag == null ? other.getTBSFlag() == null : TBSFlag.equals(other.getTBSFlag()))
                && (TMPColumns == null ? other.getTMPColumns() == null : TMPColumns.equals(other.getTMPColumns()))
                && (TableColumsName == null ? other.getTableColumsName() == null : TableColumsName.equals(other.getTableColumsName()))
                && (TableColumsCName == null ? other.getTableColumsCName() == null : TableColumsCName.equals(other.getTableColumsCName()))
                && (MakeDate == null ? other.getMakeDate() == null : fDate.getString(MakeDate).equals(other.getMakeDate()))
                && (MakeTime == null ? other.getMakeTime() == null : MakeTime.equals(other.getMakeTime()))
                && (ModifyDate == null ? other.getModifyDate() == null : fDate.getString(ModifyDate).equals(other.getModifyDate()))
                && (ModifyTime == null ? other.getModifyTime() == null : ModifyTime.equals(other.getModifyTime()))
                && (Operator == null ? other.getOperator() == null : Operator.equals(other.getOperator()))
                && (StandbyFlag1 == null ? other.getStandbyFlag1() == null : StandbyFlag1.equals(other.getStandbyFlag1()))
                && (StandbyFlag2 == null ? other.getStandbyFlag2() == null : StandbyFlag2.equals(other.getStandbyFlag2()))
                && (StandbyFlag3 == null ? other.getStandbyFlag3() == null : StandbyFlag3.equals(other.getStandbyFlag3()));
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
        if (strFieldName.equals("SetMealName"))
        {
            return 0;
        }
        if (strFieldName.equals("SetMealCode"))
        {
            return 1;
        }
        if (strFieldName.equals("TBSFlag"))
        {
            return 2;
        }
        if (strFieldName.equals("TMPColumns"))
        {
            return 3;
        }
        if (strFieldName.equals("TableColumsName"))
        {
            return 4;
        }
        if (strFieldName.equals("TableColumsCName"))
        {
            return 5;
        }
        if (strFieldName.equals("MakeDate"))
        {
            return 6;
        }
        if (strFieldName.equals("MakeTime"))
        {
            return 7;
        }
        if (strFieldName.equals("ModifyDate"))
        {
            return 8;
        }
        if (strFieldName.equals("ModifyTime"))
        {
            return 9;
        }
        if (strFieldName.equals("Operator"))
        {
            return 10;
        }
        if (strFieldName.equals("StandbyFlag1"))
        {
            return 11;
        }
        if (strFieldName.equals("StandbyFlag2"))
        {
            return 12;
        }
        if (strFieldName.equals("StandbyFlag3"))
        {
            return 13;
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
                strFieldName = "SetMealName";
                break;
            case 1:
                strFieldName = "SetMealCode";
                break;
            case 2:
                strFieldName = "TBSFlag";
                break;
            case 3:
                strFieldName = "TMPColumns";
                break;
            case 4:
                strFieldName = "TableColumsName";
                break;
            case 5:
                strFieldName = "TableColumsCName";
                break;
            case 6:
                strFieldName = "MakeDate";
                break;
            case 7:
                strFieldName = "MakeTime";
                break;
            case 8:
                strFieldName = "ModifyDate";
                break;
            case 9:
                strFieldName = "ModifyTime";
                break;
            case 10:
                strFieldName = "Operator";
                break;
            case 11:
                strFieldName = "StandbyFlag1";
                break;
            case 12:
                strFieldName = "StandbyFlag2";
                break;
            case 13:
                strFieldName = "StandbyFlag3";
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
        if (strFieldName.equals("SetMealName"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("SetMealCode"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("TBSFlag"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("TMPColumns"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("TableColumsName"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("TableColumsCName"))
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
        if (strFieldName.equals("Operator"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandbyFlag1"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandbyFlag2"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("StandbyFlag3"))
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
                nFieldType = Schema.TYPE_DATE;
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
            default:
                nFieldType = Schema.TYPE_NOFOUND;
        }
        return nFieldType;
    }
}
