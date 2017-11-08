/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.PD_ProceedsExpressDB;

/*
 * <p>ClassName: PD_ProceedsExpressSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 中银产品定义平台
 * @CreateDate：2011-11-11
 */
public class PD_ProceedsExpressSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_ProceedsExpressSchema.class);

	// @Field
	/** 险种代码 */
	private String RiskCode;
	/** 收益项目 */
	private String ProceedsCode;
	/** 计算公式 */
	private String CalExpress;
	/** 循环条件 */
	private String CalcuteCircleGene;
	/** 计算顺序 */
	private int CalOrderNo;
	/** 算式属性 */
	private String ExpressProperty;
	/** 递归元素初始值 */
	private String RecursionInitValue;
	/** 备用字段 */
	private String Back;
	/** 结果精度 */
	private int ResultPrecision;
	/** 备注 */
	private String Remark;
	/** 表头展示标志 */
	private String ShowFlag;
	/** 条件 */
	private String Terms;
	/** 新增日期 */
	private Date MakeDate;
	/** 新增时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_ProceedsExpressSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "ProceedsCode";
		pk[2] = "Terms";

		PK = pk;
	}

            /**
             * Schema克隆
             * @return Object
             * @throws CloneNotSupportedException
             */
            public Object clone()
                    throws CloneNotSupportedException
            {
                PD_ProceedsExpressSchema cloned = (PD_ProceedsExpressSchema)super.clone();
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
	public String getProceedsCode()
	{
		return ProceedsCode;
	}
	public void setProceedsCode(String aProceedsCode)
	{
		ProceedsCode = aProceedsCode;
	}
	public String getCalExpress()
	{
		return CalExpress;
	}
	public void setCalExpress(String aCalExpress)
	{
		CalExpress = aCalExpress;
	}
	public String getCalcuteCircleGene()
	{
		return CalcuteCircleGene;
	}
	public void setCalcuteCircleGene(String aCalcuteCircleGene)
	{
		CalcuteCircleGene = aCalcuteCircleGene;
	}
	public int getCalOrderNo()
	{
		return CalOrderNo;
	}
	public void setCalOrderNo(int aCalOrderNo)
	{
		CalOrderNo = aCalOrderNo;
	}
	public void setCalOrderNo(String aCalOrderNo)
	{
		if (aCalOrderNo != null && !aCalOrderNo.equals(""))
		{
			Integer tInteger = new Integer(aCalOrderNo);
			int i = tInteger.intValue();
			CalOrderNo = i;
		}
	}

	public String getExpressProperty()
	{
		return ExpressProperty;
	}
	public void setExpressProperty(String aExpressProperty)
	{
		ExpressProperty = aExpressProperty;
	}
	public String getRecursionInitValue()
	{
		return RecursionInitValue;
	}
	public void setRecursionInitValue(String aRecursionInitValue)
	{
		RecursionInitValue = aRecursionInitValue;
	}
	public String getBack()
	{
		return Back;
	}
	public void setBack(String aBack)
	{
		Back = aBack;
	}
	public int getResultPrecision()
	{
		return ResultPrecision;
	}
	public void setResultPrecision(int aResultPrecision)
	{
		ResultPrecision = aResultPrecision;
	}
	public void setResultPrecision(String aResultPrecision)
	{
		if (aResultPrecision != null && !aResultPrecision.equals(""))
		{
			Integer tInteger = new Integer(aResultPrecision);
			int i = tInteger.intValue();
			ResultPrecision = i;
		}
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getShowFlag()
	{
		return ShowFlag;
	}
	public void setShowFlag(String aShowFlag)
	{
		ShowFlag = aShowFlag;
	}
	public String getTerms()
	{
		return Terms;
	}
	public void setTerms(String aTerms)
	{
		Terms = aTerms;
	}
	public String getMakeDate()
	{
		if( MakeDate != null )
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
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
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
		if( ModifyDate != null )
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
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
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

	/**
	* 使用另外一个 PD_ProceedsExpressSchema 对象给 Schema 赋值
	* @param: aPD_ProceedsExpressSchema PD_ProceedsExpressSchema
	**/
	public void setSchema(PD_ProceedsExpressSchema aPD_ProceedsExpressSchema)
	{
		this.RiskCode = aPD_ProceedsExpressSchema.getRiskCode();
		this.ProceedsCode = aPD_ProceedsExpressSchema.getProceedsCode();
		this.CalExpress = aPD_ProceedsExpressSchema.getCalExpress();
		this.CalcuteCircleGene = aPD_ProceedsExpressSchema.getCalcuteCircleGene();
		this.CalOrderNo = aPD_ProceedsExpressSchema.getCalOrderNo();
		this.ExpressProperty = aPD_ProceedsExpressSchema.getExpressProperty();
		this.RecursionInitValue = aPD_ProceedsExpressSchema.getRecursionInitValue();
		this.Back = aPD_ProceedsExpressSchema.getBack();
		this.ResultPrecision = aPD_ProceedsExpressSchema.getResultPrecision();
		this.Remark = aPD_ProceedsExpressSchema.getRemark();
		this.ShowFlag = aPD_ProceedsExpressSchema.getShowFlag();
		this.Terms = aPD_ProceedsExpressSchema.getTerms();
		this.MakeDate = fDate.getDate( aPD_ProceedsExpressSchema.getMakeDate());
		this.MakeTime = aPD_ProceedsExpressSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_ProceedsExpressSchema.getModifyDate());
		this.ModifyTime = aPD_ProceedsExpressSchema.getModifyTime();
	}

	/**
	* 使用 ResultSet 中的第 i 行给 Schema 赋值
	* @param: rs ResultSet
	* @param: i int
	* @return: boolean
	**/
	public boolean setSchema(ResultSet rs,int i)
	{
		try
		{
			//rs.absolute(i);		// 非滚动游标
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("ProceedsCode") == null )
				this.ProceedsCode = null;
			else
				this.ProceedsCode = rs.getString("ProceedsCode").trim();

			if( rs.getString("CalExpress") == null )
				this.CalExpress = null;
			else
				this.CalExpress = rs.getString("CalExpress").trim();

			if( rs.getString("CalcuteCircleGene") == null )
				this.CalcuteCircleGene = null;
			else
				this.CalcuteCircleGene = rs.getString("CalcuteCircleGene").trim();

			this.CalOrderNo = rs.getInt("CalOrderNo");
			if( rs.getString("ExpressProperty") == null )
				this.ExpressProperty = null;
			else
				this.ExpressProperty = rs.getString("ExpressProperty").trim();

			if( rs.getString("RecursionInitValue") == null )
				this.RecursionInitValue = null;
			else
				this.RecursionInitValue = rs.getString("RecursionInitValue").trim();

			if( rs.getString("Back") == null )
				this.Back = null;
			else
				this.Back = rs.getString("Back").trim();

			this.ResultPrecision = rs.getInt("ResultPrecision");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("ShowFlag") == null )
				this.ShowFlag = null;
			else
				this.ShowFlag = rs.getString("ShowFlag").trim();

			if( rs.getString("Terms") == null )
				this.Terms = null;
			else
				this.Terms = rs.getString("Terms").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_ProceedsExpress表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_ProceedsExpressSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_ProceedsExpressSchema getSchema()
	{
		PD_ProceedsExpressSchema aPD_ProceedsExpressSchema = new PD_ProceedsExpressSchema();
		aPD_ProceedsExpressSchema.setSchema(this);
		return aPD_ProceedsExpressSchema;
	}

	public PD_ProceedsExpressDB getDB()
	{
		PD_ProceedsExpressDB aDBOper = new PD_ProceedsExpressDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_ProceedsExpress描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ProceedsCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CalExpress)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CalcuteCircleGene)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(CalOrderNo));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ExpressProperty)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RecursionInitValue)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Back)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(ResultPrecision));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ShowFlag)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Terms)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_ProceedsExpress>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ProceedsCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CalExpress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CalcuteCircleGene = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CalOrderNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			ExpressProperty = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RecursionInitValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Back = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ResultPrecision= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ShowFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Terms = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_ProceedsExpressSchema";
			tError.functionName = "decode";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

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
		if (FCode.equalsIgnoreCase("ProceedsCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProceedsCode));
		}
		if (FCode.equalsIgnoreCase("CalExpress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalExpress));
		}
		if (FCode.equalsIgnoreCase("CalcuteCircleGene"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalcuteCircleGene));
		}
		if (FCode.equalsIgnoreCase("CalOrderNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalOrderNo));
		}
		if (FCode.equalsIgnoreCase("ExpressProperty"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpressProperty));
		}
		if (FCode.equalsIgnoreCase("RecursionInitValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RecursionInitValue));
		}
		if (FCode.equalsIgnoreCase("Back"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Back));
		}
		if (FCode.equalsIgnoreCase("ResultPrecision"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ResultPrecision));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("ShowFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShowFlag));
		}
		if (FCode.equalsIgnoreCase("Terms"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Terms));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
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
		switch(nFieldIndex) {
			case 0:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ProceedsCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CalExpress);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CalcuteCircleGene);
				break;
			case 4:
				strFieldValue = String.valueOf(CalOrderNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ExpressProperty);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RecursionInitValue);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Back);
				break;
			case 8:
				strFieldValue = String.valueOf(ResultPrecision);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ShowFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Terms);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			default:
				strFieldValue = "";
		};
		if( strFieldValue.equals("") ) {
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
	public boolean setV(String FCode ,String FValue)
	{
		if( StrTool.cTrim( FCode ).equals( "" ))
			return false;

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("ProceedsCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProceedsCode = FValue.trim();
			}
			else
				ProceedsCode = null;
		}
		if (FCode.equalsIgnoreCase("CalExpress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalExpress = FValue.trim();
			}
			else
				CalExpress = null;
		}
		if (FCode.equalsIgnoreCase("CalcuteCircleGene"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalcuteCircleGene = FValue.trim();
			}
			else
				CalcuteCircleGene = null;
		}
		if (FCode.equalsIgnoreCase("CalOrderNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CalOrderNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("ExpressProperty"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpressProperty = FValue.trim();
			}
			else
				ExpressProperty = null;
		}
		if (FCode.equalsIgnoreCase("RecursionInitValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RecursionInitValue = FValue.trim();
			}
			else
				RecursionInitValue = null;
		}
		if (FCode.equalsIgnoreCase("Back"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Back = FValue.trim();
			}
			else
				Back = null;
		}
		if (FCode.equalsIgnoreCase("ResultPrecision"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ResultPrecision = i;
			}
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equalsIgnoreCase("ShowFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ShowFlag = FValue.trim();
			}
			else
				ShowFlag = null;
		}
		if (FCode.equalsIgnoreCase("Terms"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Terms = FValue.trim();
			}
			else
				Terms = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_ProceedsExpressSchema other = (PD_ProceedsExpressSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& ProceedsCode.equals(other.getProceedsCode())
			&& CalExpress.equals(other.getCalExpress())
			&& CalcuteCircleGene.equals(other.getCalcuteCircleGene())
			&& CalOrderNo == other.getCalOrderNo()
			&& ExpressProperty.equals(other.getExpressProperty())
			&& RecursionInitValue.equals(other.getRecursionInitValue())
			&& Back.equals(other.getBack())
			&& ResultPrecision == other.getResultPrecision()
			&& Remark.equals(other.getRemark())
			&& ShowFlag.equals(other.getShowFlag())
			&& Terms.equals(other.getTerms())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("ProceedsCode") ) {
			return 1;
		}
		if( strFieldName.equals("CalExpress") ) {
			return 2;
		}
		if( strFieldName.equals("CalcuteCircleGene") ) {
			return 3;
		}
		if( strFieldName.equals("CalOrderNo") ) {
			return 4;
		}
		if( strFieldName.equals("ExpressProperty") ) {
			return 5;
		}
		if( strFieldName.equals("RecursionInitValue") ) {
			return 6;
		}
		if( strFieldName.equals("Back") ) {
			return 7;
		}
		if( strFieldName.equals("ResultPrecision") ) {
			return 8;
		}
		if( strFieldName.equals("Remark") ) {
			return 9;
		}
		if( strFieldName.equals("ShowFlag") ) {
			return 10;
		}
		if( strFieldName.equals("Terms") ) {
			return 11;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 12;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 15;
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
		switch(nFieldIndex) {
			case 0:
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "ProceedsCode";
				break;
			case 2:
				strFieldName = "CalExpress";
				break;
			case 3:
				strFieldName = "CalcuteCircleGene";
				break;
			case 4:
				strFieldName = "CalOrderNo";
				break;
			case 5:
				strFieldName = "ExpressProperty";
				break;
			case 6:
				strFieldName = "RecursionInitValue";
				break;
			case 7:
				strFieldName = "Back";
				break;
			case 8:
				strFieldName = "ResultPrecision";
				break;
			case 9:
				strFieldName = "Remark";
				break;
			case 10:
				strFieldName = "ShowFlag";
				break;
			case 11:
				strFieldName = "Terms";
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
			default:
				strFieldName = "";
		};
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProceedsCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalExpress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalcuteCircleGene") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalOrderNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ExpressProperty") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RecursionInitValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Back") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ResultPrecision") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShowFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Terms") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
		switch(nFieldIndex) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
