

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
import com.sinosoft.lis.db.PD_IssueDB;

/**
 * <p>ClassName: PD_IssueSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Company: Sinosoft Co.,LTD </p>
 * @Database: PhysicalDataModel_1
 * @author：Makerx
 * @CreateDate：2009-04-07
 */
public class PD_IssueSchema implements Schema, Cloneable
{
    // @Field
    /** 序列号 */
    private String SerialNO;
    /** 险种代码 */
    private String RiskCode;
    /** 问题件类型 */
    private String IssueType;
    /** 提出岗 */
    private String OperPost;
    /** 提出人员 */
    private String OperPostMan;
    /** 问题件内容 */
    private String IssueCont;
    /** 回复人员 */
    private String ReplyMan;
    /** 回复内容 */
    private String ReplyCont;
    /** 返回岗 */
    private String BackPost;
    /** 问题件状态 */
    private String IssueState;
    /** 问题件附件路径 */
    private String IssueFilePath;
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

    public static final int FIELDNUM = 18; // 数据库表的字段个数

    private static String[] PK; // 主键

    private FDate fDate = new FDate(); // 处理日期

    public CErrors mErrors; // 错误信息

    // @Constructor
    public PD_IssueSchema()
    {
        mErrors = new CErrors();
        String[] pk = new String[1];
        pk[0] = "SerialNO";
        PK = pk;
    }

    /**
     * Schema克隆
     * @return Object
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException
    {
        PD_IssueSchema cloned = (PD_IssueSchema)super.clone();
        cloned.fDate = (FDate) fDate.clone();
        cloned.mErrors = (CErrors) mErrors.clone();
        return cloned;
    }

    // @Method
    public String[] getPK()
    {
        return PK;
    }

    public String getSerialNO()
    {
        return SerialNO;
    }

    public void setSerialNO(String aSerialNO)
    {
        SerialNO = aSerialNO;
    }

    public String getRiskCode()
    {
        return RiskCode;
    }

    public void setRiskCode(String aRiskCode)
    {
        RiskCode = aRiskCode;
    }

    public String getIssueType()
    {
        return IssueType;
    }

    public void setIssueType(String aIssueType)
    {
        IssueType = aIssueType;
    }

    public String getOperPost()
    {
        return OperPost;
    }

    public void setOperPost(String aOperPost)
    {
        OperPost = aOperPost;
    }

    public String getOperPostMan()
    {
        return OperPostMan;
    }

    public void setOperPostMan(String aOperPostMan)
    {
        OperPostMan = aOperPostMan;
    }

    public String getIssueCont()
    {
        return IssueCont;
    }

    public void setIssueCont(String aIssueCont)
    {
        IssueCont = aIssueCont;
    }

    public String getReplyMan()
    {
        return ReplyMan;
    }

    public void setReplyMan(String aReplyMan)
    {
        ReplyMan = aReplyMan;
    }

    public String getReplyCont()
    {
        return ReplyCont;
    }

    public void setReplyCont(String aReplyCont)
    {
        ReplyCont = aReplyCont;
    }

    public String getBackPost()
    {
        return BackPost;
    }

    public void setBackPost(String aBackPost)
    {
        BackPost = aBackPost;
    }

    public String getIssueState()
    {
        return IssueState;
    }

    public void setIssueState(String aIssueState)
    {
        IssueState = aIssueState;
    }

    public String getIssueFilePath()
    {
        return IssueFilePath;
    }

    public void setIssueFilePath(String aIssueFilePath)
    {
        IssueFilePath = aIssueFilePath;
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

    /**
     * 使用另外一个 PD_IssueSchema 对象给 Schema 赋值
     * @param: aPD_IssueSchema PD_IssueSchema
     **/
    public void setSchema(PD_IssueSchema aPD_IssueSchema)
    {
        this.SerialNO = aPD_IssueSchema.getSerialNO();
        this.RiskCode = aPD_IssueSchema.getRiskCode();
        this.IssueType = aPD_IssueSchema.getIssueType();
        this.OperPost = aPD_IssueSchema.getOperPost();
        this.OperPostMan = aPD_IssueSchema.getOperPostMan();
        this.IssueCont = aPD_IssueSchema.getIssueCont();
        this.ReplyMan = aPD_IssueSchema.getReplyMan();
        this.ReplyCont = aPD_IssueSchema.getReplyCont();
        this.BackPost = aPD_IssueSchema.getBackPost();
        this.IssueState = aPD_IssueSchema.getIssueState();
        this.IssueFilePath = aPD_IssueSchema.getIssueFilePath();
        this.Operator = aPD_IssueSchema.getOperator();
        this.MakeDate = fDate.getDate(aPD_IssueSchema.getMakeDate());
        this.MakeTime = aPD_IssueSchema.getMakeTime();
        this.ModifyDate = fDate.getDate(aPD_IssueSchema.getModifyDate());
        this.ModifyTime = aPD_IssueSchema.getModifyTime();
        this.Standbyflag1 = aPD_IssueSchema.getStandbyflag1();
        this.Standbyflag2 = aPD_IssueSchema.getStandbyflag2();
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
                this.SerialNO = null;
            else
                this.SerialNO = rs.getString(1).trim();
            if (rs.getString(2) == null)
                this.RiskCode = null;
            else
                this.RiskCode = rs.getString(2).trim();
            if (rs.getString(3) == null)
                this.IssueType = null;
            else
                this.IssueType = rs.getString(3).trim();
            if (rs.getString(4) == null)
                this.OperPost = null;
            else
                this.OperPost = rs.getString(4).trim();
            if (rs.getString(5) == null)
                this.OperPostMan = null;
            else
                this.OperPostMan = rs.getString(5).trim();
            if (rs.getString(6) == null)
                this.IssueCont = null;
            else
                this.IssueCont = rs.getString(6).trim();
            if (rs.getString(7) == null)
                this.ReplyMan = null;
            else
                this.ReplyMan = rs.getString(7).trim();
            if (rs.getString(8) == null)
                this.ReplyCont = null;
            else
                this.ReplyCont = rs.getString(8).trim();
            if (rs.getString(9) == null)
                this.BackPost = null;
            else
                this.BackPost = rs.getString(9).trim();
            if (rs.getString(10) == null)
                this.IssueState = null;
            else
                this.IssueState = rs.getString(10).trim();
            if (rs.getString(11) == null)
                this.IssueFilePath = null;
            else
                this.IssueFilePath = rs.getString(11).trim();
            if (rs.getString(12) == null)
                this.Operator = null;
            else
                this.Operator = rs.getString(12).trim();
            this.MakeDate = rs.getDate(13);
            if (rs.getString(14) == null)
                this.MakeTime = null;
            else
                this.MakeTime = rs.getString(14).trim();
            this.ModifyDate = rs.getDate(15);
            if (rs.getString(16) == null)
                this.ModifyTime = null;
            else
                this.ModifyTime = rs.getString(16).trim();
            if (rs.getString(17) == null)
                this.Standbyflag1 = null;
            else
                this.Standbyflag1 = rs.getString(17).trim();
            if (rs.getString(18) == null)
                this.Standbyflag2 = null;
            else
                this.Standbyflag2 = rs.getString(18).trim();
        }
        catch (SQLException sqle)
        {
            System.out.println("数据库中表PD_Issue字段个数和Schema中的字段个数不一致，或执行db.executeQuery查询时未使用select * from tables");
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_IssueSchema";
            tError.functionName = "setSchema";
            tError.errorMessage = sqle.toString();
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public PD_IssueSchema getSchema()
    {
        PD_IssueSchema aPD_IssueSchema = new PD_IssueSchema();
        aPD_IssueSchema.setSchema(this);
        return aPD_IssueSchema;
    }

    public PD_IssueDB getDB()
    {
        PD_IssueDB aDBOper = new PD_IssueDB();
        aDBOper.setSchema(this);
        return aDBOper;
    }

    /**
     * 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_Issue描述/A>表字段
     * @return: String 返回打包后字符串
     **/
    public String encode()
    {
        StringBuffer strReturn = new StringBuffer(256);
        strReturn.append(StrTool.cTrim(SerialNO));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(RiskCode));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(IssueType));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(OperPost));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(OperPostMan));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(IssueCont));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(ReplyMan));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(ReplyCont));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(BackPost));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(IssueState));
        strReturn.append(SysConst.PACKAGESPILTER);
        strReturn.append(StrTool.cTrim(IssueFilePath));
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
        return strReturn.toString();
    }

    /**
     * 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_Issue>历史记账凭证主表信息</A>表字段
     * @param: strMessage String 包含一条纪录数据的字符串
     * @return: boolean
     **/
    public boolean decode(String strMessage)
    {
        try
        {
            SerialNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER);
            RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER);
            IssueType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER);
            OperPost = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER);
            OperPostMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER);
            IssueCont = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER);
            ReplyMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER);
            ReplyCont = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER);
            BackPost = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER);
            IssueState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER);
            IssueFilePath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER);
            Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER);
            MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER));
            MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER);
            ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER));
            ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER);
            Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER);
            Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER);
        }
        catch (NumberFormatException ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PD_IssueSchema";
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
        if (FCode.equals("SerialNO"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNO));
        }
        if (FCode.equals("RiskCode"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
        }
        if (FCode.equals("IssueType"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(IssueType));
        }
        if (FCode.equals("OperPost"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(OperPost));
        }
        if (FCode.equals("OperPostMan"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(OperPostMan));
        }
        if (FCode.equals("IssueCont"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(IssueCont));
        }
        if (FCode.equals("ReplyMan"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyMan));
        }
        if (FCode.equals("ReplyCont"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyCont));
        }
        if (FCode.equals("BackPost"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(BackPost));
        }
        if (FCode.equals("IssueState"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(IssueState));
        }
        if (FCode.equals("IssueFilePath"))
        {
            strReturn = StrTool.GBKToUnicode(String.valueOf(IssueFilePath));
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
                strFieldValue = StrTool.GBKToUnicode(SerialNO);
                break;
            case 1:
                strFieldValue = StrTool.GBKToUnicode(RiskCode);
                break;
            case 2:
                strFieldValue = StrTool.GBKToUnicode(IssueType);
                break;
            case 3:
                strFieldValue = StrTool.GBKToUnicode(OperPost);
                break;
            case 4:
                strFieldValue = StrTool.GBKToUnicode(OperPostMan);
                break;
            case 5:
                strFieldValue = StrTool.GBKToUnicode(IssueCont);
                break;
            case 6:
                strFieldValue = StrTool.GBKToUnicode(ReplyMan);
                break;
            case 7:
                strFieldValue = StrTool.GBKToUnicode(ReplyCont);
                break;
            case 8:
                strFieldValue = StrTool.GBKToUnicode(BackPost);
                break;
            case 9:
                strFieldValue = StrTool.GBKToUnicode(IssueState);
                break;
            case 10:
                strFieldValue = StrTool.GBKToUnicode(IssueFilePath);
                break;
            case 11:
                strFieldValue = StrTool.GBKToUnicode(Operator);
                break;
            case 12:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getMakeDate()));
                break;
            case 13:
                strFieldValue = StrTool.GBKToUnicode(MakeTime);
                break;
            case 14:
                strFieldValue = StrTool.GBKToUnicode(String.valueOf(this.getModifyDate()));
                break;
            case 15:
                strFieldValue = StrTool.GBKToUnicode(ModifyTime);
                break;
            case 16:
                strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
                break;
            case 17:
                strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
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

        if (FCode.equalsIgnoreCase("SerialNO"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                SerialNO = FValue.trim();
            }
            else
                SerialNO = null;
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
        if (FCode.equalsIgnoreCase("IssueType"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                IssueType = FValue.trim();
            }
            else
                IssueType = null;
        }
        if (FCode.equalsIgnoreCase("OperPost"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                OperPost = FValue.trim();
            }
            else
                OperPost = null;
        }
        if (FCode.equalsIgnoreCase("OperPostMan"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                OperPostMan = FValue.trim();
            }
            else
                OperPostMan = null;
        }
        if (FCode.equalsIgnoreCase("IssueCont"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                IssueCont = FValue.trim();
            }
            else
                IssueCont = null;
        }
        if (FCode.equalsIgnoreCase("ReplyMan"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                ReplyMan = FValue.trim();
            }
            else
                ReplyMan = null;
        }
        if (FCode.equalsIgnoreCase("ReplyCont"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                ReplyCont = FValue.trim();
            }
            else
                ReplyCont = null;
        }
        if (FCode.equalsIgnoreCase("BackPost"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                BackPost = FValue.trim();
            }
            else
                BackPost = null;
        }
        if (FCode.equalsIgnoreCase("IssueState"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                IssueState = FValue.trim();
            }
            else
                IssueState = null;
        }
        if (FCode.equalsIgnoreCase("IssueFilePath"))
        {
            if (FValue != null && !FValue.equals(""))
            {
                IssueFilePath = FValue.trim();
            }
            else
                IssueFilePath = null;
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
        PD_IssueSchema other = (PD_IssueSchema) otherObject;
        return
                (SerialNO == null ? other.getSerialNO() == null : SerialNO.equals(other.getSerialNO()))
                && (RiskCode == null ? other.getRiskCode() == null : RiskCode.equals(other.getRiskCode()))
                && (IssueType == null ? other.getIssueType() == null : IssueType.equals(other.getIssueType()))
                && (OperPost == null ? other.getOperPost() == null : OperPost.equals(other.getOperPost()))
                && (OperPostMan == null ? other.getOperPostMan() == null : OperPostMan.equals(other.getOperPostMan()))
                && (IssueCont == null ? other.getIssueCont() == null : IssueCont.equals(other.getIssueCont()))
                && (ReplyMan == null ? other.getReplyMan() == null : ReplyMan.equals(other.getReplyMan()))
                && (ReplyCont == null ? other.getReplyCont() == null : ReplyCont.equals(other.getReplyCont()))
                && (BackPost == null ? other.getBackPost() == null : BackPost.equals(other.getBackPost()))
                && (IssueState == null ? other.getIssueState() == null : IssueState.equals(other.getIssueState()))
                && (IssueFilePath == null ? other.getIssueFilePath() == null : IssueFilePath.equals(other.getIssueFilePath()))
                && (Operator == null ? other.getOperator() == null : Operator.equals(other.getOperator()))
                && (MakeDate == null ? other.getMakeDate() == null : fDate.getString(MakeDate).equals(other.getMakeDate()))
                && (MakeTime == null ? other.getMakeTime() == null : MakeTime.equals(other.getMakeTime()))
                && (ModifyDate == null ? other.getModifyDate() == null : fDate.getString(ModifyDate).equals(other.getModifyDate()))
                && (ModifyTime == null ? other.getModifyTime() == null : ModifyTime.equals(other.getModifyTime()))
                && (Standbyflag1 == null ? other.getStandbyflag1() == null : Standbyflag1.equals(other.getStandbyflag1()))
                && (Standbyflag2 == null ? other.getStandbyflag2() == null : Standbyflag2.equals(other.getStandbyflag2()));
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
        if (strFieldName.equals("SerialNO"))
        {
            return 0;
        }
        if (strFieldName.equals("RiskCode"))
        {
            return 1;
        }
        if (strFieldName.equals("IssueType"))
        {
            return 2;
        }
        if (strFieldName.equals("OperPost"))
        {
            return 3;
        }
        if (strFieldName.equals("OperPostMan"))
        {
            return 4;
        }
        if (strFieldName.equals("IssueCont"))
        {
            return 5;
        }
        if (strFieldName.equals("ReplyMan"))
        {
            return 6;
        }
        if (strFieldName.equals("ReplyCont"))
        {
            return 7;
        }
        if (strFieldName.equals("BackPost"))
        {
            return 8;
        }
        if (strFieldName.equals("IssueState"))
        {
            return 9;
        }
        if (strFieldName.equals("IssueFilePath"))
        {
            return 10;
        }
        if (strFieldName.equals("Operator"))
        {
            return 11;
        }
        if (strFieldName.equals("MakeDate"))
        {
            return 12;
        }
        if (strFieldName.equals("MakeTime"))
        {
            return 13;
        }
        if (strFieldName.equals("ModifyDate"))
        {
            return 14;
        }
        if (strFieldName.equals("ModifyTime"))
        {
            return 15;
        }
        if (strFieldName.equals("Standbyflag1"))
        {
            return 16;
        }
        if (strFieldName.equals("Standbyflag2"))
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
                strFieldName = "SerialNO";
                break;
            case 1:
                strFieldName = "RiskCode";
                break;
            case 2:
                strFieldName = "IssueType";
                break;
            case 3:
                strFieldName = "OperPost";
                break;
            case 4:
                strFieldName = "OperPostMan";
                break;
            case 5:
                strFieldName = "IssueCont";
                break;
            case 6:
                strFieldName = "ReplyMan";
                break;
            case 7:
                strFieldName = "ReplyCont";
                break;
            case 8:
                strFieldName = "BackPost";
                break;
            case 9:
                strFieldName = "IssueState";
                break;
            case 10:
                strFieldName = "IssueFilePath";
                break;
            case 11:
                strFieldName = "Operator";
                break;
            case 12:
                strFieldName = "MakeDate";
                break;
            case 13:
                strFieldName = "MakeTime";
                break;
            case 14:
                strFieldName = "ModifyDate";
                break;
            case 15:
                strFieldName = "ModifyTime";
                break;
            case 16:
                strFieldName = "Standbyflag1";
                break;
            case 17:
                strFieldName = "Standbyflag2";
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
        if (strFieldName.equals("SerialNO"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("RiskCode"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("IssueType"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("OperPost"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("OperPostMan"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("IssueCont"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("ReplyMan"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("ReplyCont"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("BackPost"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("IssueState"))
        {
            return Schema.TYPE_STRING;
        }
        if (strFieldName.equals("IssueFilePath"))
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
                nFieldType = Schema.TYPE_DATE;
                break;
            case 13:
                nFieldType = Schema.TYPE_STRING;
                break;
            case 14:
                nFieldType = Schema.TYPE_DATE;
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
            default:
                nFieldType = Schema.TYPE_NOFOUND;
        }
        return nFieldType;
    }
}
