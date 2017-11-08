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
import com.sinosoft.lis.db.LLParaDeformityDB;

/*
 * <p>ClassName: LLParaDeformitySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLParaDeformitySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLParaDeformitySchema.class);
	// @Field
	/** 伤残类型 */
	private String DefoType;
	/** 伤残级别 */
	private String DefoGrade;
	/** 伤残级别名称 */
	private String DefoGradeName;
	/** 伤残代码 */
	private String DefoCode;
	/** 伤残代码名称 */
	private String DefoName;
	/** 残疾给付比例 */
	private double DefoRate;
	/** 伤残分类 */
	private String DefoClass;
	/** 伤残分类名称 */
	private String DefoClassName;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLParaDeformitySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "DefoType";
		pk[1] = "DefoCode";

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
		LLParaDeformitySchema cloned = (LLParaDeformitySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 1--普通伤残(使用7级34项残疾给付表)<p>
	* 2--普通伤残(使用老的残疾给付表)
	*/
	public String getDefoType()
	{
		return DefoType;
	}
	public void setDefoType(String aDefoType)
	{
		if(aDefoType!=null && aDefoType.length()>10)
			throw new IllegalArgumentException("伤残类型DefoType值"+aDefoType+"的长度"+aDefoType.length()+"大于最大值10");
		DefoType = aDefoType;
	}
	/**
	* 对于LF-伤残,LZ-骨折等设定级别或类别会根据分类或分级情况进行设置，如第1级等；<p>
	* 对于其他的不需要分类或分级的则置默认值1(第1级),界面上也不会出现伤残级别的选择框;
	*/
	public String getDefoGrade()
	{
		return DefoGrade;
	}
	public void setDefoGrade(String aDefoGrade)
	{
		if(aDefoGrade!=null && aDefoGrade.length()>10)
			throw new IllegalArgumentException("伤残级别DefoGrade值"+aDefoGrade+"的长度"+aDefoGrade.length()+"大于最大值10");
		DefoGrade = aDefoGrade;
	}
	public String getDefoGradeName()
	{
		return DefoGradeName;
	}
	public void setDefoGradeName(String aDefoGradeName)
	{
		if(aDefoGradeName!=null && aDefoGradeName.length()>100)
			throw new IllegalArgumentException("伤残级别名称DefoGradeName值"+aDefoGradeName+"的长度"+aDefoGradeName.length()+"大于最大值100");
		DefoGradeName = aDefoGradeName;
	}
	public String getDefoCode()
	{
		return DefoCode;
	}
	public void setDefoCode(String aDefoCode)
	{
		if(aDefoCode!=null && aDefoCode.length()>600)
			throw new IllegalArgumentException("伤残代码DefoCode值"+aDefoCode+"的长度"+aDefoCode.length()+"大于最大值600");
		DefoCode = aDefoCode;
	}
	public String getDefoName()
	{
		return DefoName;
	}
	public void setDefoName(String aDefoName)
	{
		if(aDefoName!=null && aDefoName.length()>600)
			throw new IllegalArgumentException("伤残代码名称DefoName值"+aDefoName+"的长度"+aDefoName.length()+"大于最大值600");
		DefoName = aDefoName;
	}
	public double getDefoRate()
	{
		return DefoRate;
	}
	public void setDefoRate(double aDefoRate)
	{
		DefoRate = aDefoRate;
	}
	public void setDefoRate(String aDefoRate)
	{
		if (aDefoRate != null && !aDefoRate.equals(""))
		{
			Double tDouble = new Double(aDefoRate);
			double d = tDouble.doubleValue();
			DefoRate = d;
		}
	}

	public String getDefoClass()
	{
		return DefoClass;
	}
	public void setDefoClass(String aDefoClass)
	{
		if(aDefoClass!=null && aDefoClass.length()>10)
			throw new IllegalArgumentException("伤残分类DefoClass值"+aDefoClass+"的长度"+aDefoClass.length()+"大于最大值10");
		DefoClass = aDefoClass;
	}
	public String getDefoClassName()
	{
		return DefoClassName;
	}
	public void setDefoClassName(String aDefoClassName)
	{
		if(aDefoClassName!=null && aDefoClassName.length()>100)
			throw new IllegalArgumentException("伤残分类名称DefoClassName值"+aDefoClassName+"的长度"+aDefoClassName.length()+"大于最大值100");
		DefoClassName = aDefoClassName;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
		MakeTime = aMakeTime;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LLParaDeformitySchema 对象给 Schema 赋值
	* @param: aLLParaDeformitySchema LLParaDeformitySchema
	**/
	public void setSchema(LLParaDeformitySchema aLLParaDeformitySchema)
	{
		this.DefoType = aLLParaDeformitySchema.getDefoType();
		this.DefoGrade = aLLParaDeformitySchema.getDefoGrade();
		this.DefoGradeName = aLLParaDeformitySchema.getDefoGradeName();
		this.DefoCode = aLLParaDeformitySchema.getDefoCode();
		this.DefoName = aLLParaDeformitySchema.getDefoName();
		this.DefoRate = aLLParaDeformitySchema.getDefoRate();
		this.DefoClass = aLLParaDeformitySchema.getDefoClass();
		this.DefoClassName = aLLParaDeformitySchema.getDefoClassName();
		this.ManageCom = aLLParaDeformitySchema.getManageCom();
		this.ComCode = aLLParaDeformitySchema.getComCode();
		this.MakeOperator = aLLParaDeformitySchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLLParaDeformitySchema.getMakeDate());
		this.MakeTime = aLLParaDeformitySchema.getMakeTime();
		this.ModifyOperator = aLLParaDeformitySchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLLParaDeformitySchema.getModifyDate());
		this.ModifyTime = aLLParaDeformitySchema.getModifyTime();
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
			if( rs.getString("DefoType") == null )
				this.DefoType = null;
			else
				this.DefoType = rs.getString("DefoType").trim();

			if( rs.getString("DefoGrade") == null )
				this.DefoGrade = null;
			else
				this.DefoGrade = rs.getString("DefoGrade").trim();

			if( rs.getString("DefoGradeName") == null )
				this.DefoGradeName = null;
			else
				this.DefoGradeName = rs.getString("DefoGradeName").trim();

			if( rs.getString("DefoCode") == null )
				this.DefoCode = null;
			else
				this.DefoCode = rs.getString("DefoCode").trim();

			if( rs.getString("DefoName") == null )
				this.DefoName = null;
			else
				this.DefoName = rs.getString("DefoName").trim();

			this.DefoRate = rs.getDouble("DefoRate");
			if( rs.getString("DefoClass") == null )
				this.DefoClass = null;
			else
				this.DefoClass = rs.getString("DefoClass").trim();

			if( rs.getString("DefoClassName") == null )
				this.DefoClassName = null;
			else
				this.DefoClassName = rs.getString("DefoClassName").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLParaDeformity表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaDeformitySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLParaDeformitySchema getSchema()
	{
		LLParaDeformitySchema aLLParaDeformitySchema = new LLParaDeformitySchema();
		aLLParaDeformitySchema.setSchema(this);
		return aLLParaDeformitySchema;
	}

	public LLParaDeformityDB getDB()
	{
		LLParaDeformityDB aDBOper = new LLParaDeformityDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLParaDeformity描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(DefoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoGradeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefoRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoClassName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLParaDeformity>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			DefoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DefoGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DefoGradeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DefoCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DefoName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DefoRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			DefoClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DefoClassName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaDeformitySchema";
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
		if (FCode.equalsIgnoreCase("DefoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoType));
		}
		if (FCode.equalsIgnoreCase("DefoGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoGrade));
		}
		if (FCode.equalsIgnoreCase("DefoGradeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoGradeName));
		}
		if (FCode.equalsIgnoreCase("DefoCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoCode));
		}
		if (FCode.equalsIgnoreCase("DefoName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoName));
		}
		if (FCode.equalsIgnoreCase("DefoRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoRate));
		}
		if (FCode.equalsIgnoreCase("DefoClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoClass));
		}
		if (FCode.equalsIgnoreCase("DefoClassName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoClassName));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(DefoType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(DefoGrade);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DefoGradeName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DefoCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DefoName);
				break;
			case 5:
				strFieldValue = String.valueOf(DefoRate);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DefoClass);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DefoClassName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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

		if (FCode.equalsIgnoreCase("DefoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoType = FValue.trim();
			}
			else
				DefoType = null;
		}
		if (FCode.equalsIgnoreCase("DefoGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoGrade = FValue.trim();
			}
			else
				DefoGrade = null;
		}
		if (FCode.equalsIgnoreCase("DefoGradeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoGradeName = FValue.trim();
			}
			else
				DefoGradeName = null;
		}
		if (FCode.equalsIgnoreCase("DefoCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoCode = FValue.trim();
			}
			else
				DefoCode = null;
		}
		if (FCode.equalsIgnoreCase("DefoName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoName = FValue.trim();
			}
			else
				DefoName = null;
		}
		if (FCode.equalsIgnoreCase("DefoRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DefoRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("DefoClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoClass = FValue.trim();
			}
			else
				DefoClass = null;
		}
		if (FCode.equalsIgnoreCase("DefoClassName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoClassName = FValue.trim();
			}
			else
				DefoClassName = null;
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		LLParaDeformitySchema other = (LLParaDeformitySchema)otherObject;
		return
			DefoType.equals(other.getDefoType())
			&& DefoGrade.equals(other.getDefoGrade())
			&& DefoGradeName.equals(other.getDefoGradeName())
			&& DefoCode.equals(other.getDefoCode())
			&& DefoName.equals(other.getDefoName())
			&& DefoRate == other.getDefoRate()
			&& DefoClass.equals(other.getDefoClass())
			&& DefoClassName.equals(other.getDefoClassName())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
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
		if( strFieldName.equals("DefoType") ) {
			return 0;
		}
		if( strFieldName.equals("DefoGrade") ) {
			return 1;
		}
		if( strFieldName.equals("DefoGradeName") ) {
			return 2;
		}
		if( strFieldName.equals("DefoCode") ) {
			return 3;
		}
		if( strFieldName.equals("DefoName") ) {
			return 4;
		}
		if( strFieldName.equals("DefoRate") ) {
			return 5;
		}
		if( strFieldName.equals("DefoClass") ) {
			return 6;
		}
		if( strFieldName.equals("DefoClassName") ) {
			return 7;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 8;
		}
		if( strFieldName.equals("ComCode") ) {
			return 9;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				strFieldName = "DefoType";
				break;
			case 1:
				strFieldName = "DefoGrade";
				break;
			case 2:
				strFieldName = "DefoGradeName";
				break;
			case 3:
				strFieldName = "DefoCode";
				break;
			case 4:
				strFieldName = "DefoName";
				break;
			case 5:
				strFieldName = "DefoRate";
				break;
			case 6:
				strFieldName = "DefoClass";
				break;
			case 7:
				strFieldName = "DefoClassName";
				break;
			case 8:
				strFieldName = "ManageCom";
				break;
			case 9:
				strFieldName = "ComCode";
				break;
			case 10:
				strFieldName = "MakeOperator";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("DefoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoGradeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DefoClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoClassName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
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
