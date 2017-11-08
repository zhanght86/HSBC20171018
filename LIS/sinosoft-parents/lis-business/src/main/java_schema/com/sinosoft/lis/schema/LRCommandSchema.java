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
import com.sinosoft.lis.db.LRCommandDB;

/*
 * <p>ClassName: LRCommandSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 规则引擎
 */
public class LRCommandSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRCommandSchema.class);

	// @Field
	/** 符号的名称 */
	private String Name;
	/** 符号的界面展现 */
	private String Display;
	/** 技术实现 */
	private String Implenmation;
	/** 有效性 */
	private String Valid;
	/** 运算数据类型 */
	private String CommandType;
	/** 参数个数 */
	private int ParaNum;
	/** 参数类型 */
	private String ParaType;
	/** 运算结果类型 */
	private String ResultType;
	/** 符号的描述信息 */
	private String Description;
	/** 函数类型 */
	private String CommType;
	/** 函数参数明细 */
	private String CommDetail;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRCommandSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "Name";

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
		LRCommandSchema cloned = (LRCommandSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		if(aName!=null && aName.length()>20)
			throw new IllegalArgumentException("符号的名称Name值"+aName+"的长度"+aName.length()+"大于最大值20");
		Name = aName;
	}
	/**
	* 符号在界面上的展示样式
	*/
	public String getDisplay()
	{
		return Display;
	}
	public void setDisplay(String aDisplay)
	{
		if(aDisplay!=null && aDisplay.length()>100)
			throw new IllegalArgumentException("符号的界面展现Display值"+aDisplay+"的长度"+aDisplay.length()+"大于最大值100");
		Display = aDisplay;
	}
	/**
	* 生成SQL时符号的运算法则
	*/
	public String getImplenmation()
	{
		return Implenmation;
	}
	public void setImplenmation(String aImplenmation)
	{
		if(aImplenmation!=null && aImplenmation.length()>100)
			throw new IllegalArgumentException("技术实现Implenmation值"+aImplenmation+"的长度"+aImplenmation.length()+"大于最大值100");
		Implenmation = aImplenmation;
	}
	public String getValid()
	{
		return Valid;
	}
	public void setValid(String aValid)
	{
		if(aValid!=null && aValid.length()>1)
			throw new IllegalArgumentException("有效性Valid值"+aValid+"的长度"+aValid.length()+"大于最大值1");
		Valid = aValid;
	}
	/**
	* String-字符型<p>
	* Number-数字型<p>
	* Date-日期型<p>
	* Time-时间型 HH24:MI:SS
	*/
	public String getCommandType()
	{
		return CommandType;
	}
	public void setCommandType(String aCommandType)
	{
		if(aCommandType!=null && aCommandType.length()>10)
			throw new IllegalArgumentException("运算数据类型CommandType值"+aCommandType+"的长度"+aCommandType.length()+"大于最大值10");
		CommandType = aCommandType;
	}
	public int getParaNum()
	{
		return ParaNum;
	}
	public void setParaNum(int aParaNum)
	{
		ParaNum = aParaNum;
	}
	public void setParaNum(String aParaNum)
	{
		if (aParaNum != null && !aParaNum.equals(""))
		{
			Integer tInteger = new Integer(aParaNum);
			int i = tInteger.intValue();
			ParaNum = i;
		}
	}

	public String getParaType()
	{
		return ParaType;
	}
	public void setParaType(String aParaType)
	{
		if(aParaType!=null && aParaType.length()>10)
			throw new IllegalArgumentException("参数类型ParaType值"+aParaType+"的长度"+aParaType.length()+"大于最大值10");
		ParaType = aParaType;
	}
	/**
	* String-字符型<p>
	* Number-数字型<p>
	* Date-日期型<p>
	* Time-时间型 HH24:MI:SS<p>
	* Boolean-逻辑值
	*/
	public String getResultType()
	{
		return ResultType;
	}
	public void setResultType(String aResultType)
	{
		if(aResultType!=null && aResultType.length()>10)
			throw new IllegalArgumentException("运算结果类型ResultType值"+aResultType+"的长度"+aResultType.length()+"大于最大值10");
		ResultType = aResultType;
	}
	public String getDescription()
	{
		return Description;
	}
	public void setDescription(String aDescription)
	{
		if(aDescription!=null && aDescription.length()>300)
			throw new IllegalArgumentException("符号的描述信息Description值"+aDescription+"的长度"+aDescription.length()+"大于最大值300");
		Description = aDescription;
	}
	public String getCommType()
	{
		return CommType;
	}
	public void setCommType(String aCommType)
	{
		if(aCommType!=null && aCommType.length()>1)
			throw new IllegalArgumentException("函数类型CommType值"+aCommType+"的长度"+aCommType.length()+"大于最大值1");
		CommType = aCommType;
	}
	public String getCommDetail()
	{
		return CommDetail;
	}
	public void setCommDetail(String aCommDetail)
	{
		if(aCommDetail!=null && aCommDetail.length()>300)
			throw new IllegalArgumentException("函数参数明细CommDetail值"+aCommDetail+"的长度"+aCommDetail.length()+"大于最大值300");
		CommDetail = aCommDetail;
	}

	/**
	* 使用另外一个 LRCommandSchema 对象给 Schema 赋值
	* @param: aLRCommandSchema LRCommandSchema
	**/
	public void setSchema(LRCommandSchema aLRCommandSchema)
	{
		this.Name = aLRCommandSchema.getName();
		this.Display = aLRCommandSchema.getDisplay();
		this.Implenmation = aLRCommandSchema.getImplenmation();
		this.Valid = aLRCommandSchema.getValid();
		this.CommandType = aLRCommandSchema.getCommandType();
		this.ParaNum = aLRCommandSchema.getParaNum();
		this.ParaType = aLRCommandSchema.getParaType();
		this.ResultType = aLRCommandSchema.getResultType();
		this.Description = aLRCommandSchema.getDescription();
		this.CommType = aLRCommandSchema.getCommType();
		this.CommDetail = aLRCommandSchema.getCommDetail();
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
			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("Display") == null )
				this.Display = null;
			else
				this.Display = rs.getString("Display").trim();

			if( rs.getString("Implenmation") == null )
				this.Implenmation = null;
			else
				this.Implenmation = rs.getString("Implenmation").trim();

			if( rs.getString("Valid") == null )
				this.Valid = null;
			else
				this.Valid = rs.getString("Valid").trim();

			if( rs.getString("CommandType") == null )
				this.CommandType = null;
			else
				this.CommandType = rs.getString("CommandType").trim();

			this.ParaNum = rs.getInt("ParaNum");
			if( rs.getString("ParaType") == null )
				this.ParaType = null;
			else
				this.ParaType = rs.getString("ParaType").trim();

			if( rs.getString("ResultType") == null )
				this.ResultType = null;
			else
				this.ResultType = rs.getString("ResultType").trim();

			if( rs.getString("Description") == null )
				this.Description = null;
			else
				this.Description = rs.getString("Description").trim();

			if( rs.getString("CommType") == null )
				this.CommType = null;
			else
				this.CommType = rs.getString("CommType").trim();

			if( rs.getString("CommDetail") == null )
				this.CommDetail = null;
			else
				this.CommDetail = rs.getString("CommDetail").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRCommand表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRCommandSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRCommandSchema getSchema()
	{
		LRCommandSchema aLRCommandSchema = new LRCommandSchema();
		aLRCommandSchema.setSchema(this);
		return aLRCommandSchema;
	}

	public LRCommandDB getDB()
	{
		LRCommandDB aDBOper = new LRCommandDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRCommand描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Display)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Implenmation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Valid)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CommandType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParaNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ResultType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Description)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CommType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CommDetail));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRCommand>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Display = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Implenmation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Valid = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CommandType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ParaNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			ParaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ResultType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Description = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CommType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CommDetail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRCommandSchema";
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
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("Display"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Display));
		}
		if (FCode.equalsIgnoreCase("Implenmation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Implenmation));
		}
		if (FCode.equalsIgnoreCase("Valid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Valid));
		}
		if (FCode.equalsIgnoreCase("CommandType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommandType));
		}
		if (FCode.equalsIgnoreCase("ParaNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParaNum));
		}
		if (FCode.equalsIgnoreCase("ParaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParaType));
		}
		if (FCode.equalsIgnoreCase("ResultType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ResultType));
		}
		if (FCode.equalsIgnoreCase("Description"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Description));
		}
		if (FCode.equalsIgnoreCase("CommType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommType));
		}
		if (FCode.equalsIgnoreCase("CommDetail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommDetail));
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
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Display);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Implenmation);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Valid);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CommandType);
				break;
			case 5:
				strFieldValue = String.valueOf(ParaNum);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ParaType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ResultType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Description);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CommType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CommDetail);
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

		if (FCode.equalsIgnoreCase("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
		}
		if (FCode.equalsIgnoreCase("Display"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Display = FValue.trim();
			}
			else
				Display = null;
		}
		if (FCode.equalsIgnoreCase("Implenmation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Implenmation = FValue.trim();
			}
			else
				Implenmation = null;
		}
		if (FCode.equalsIgnoreCase("Valid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Valid = FValue.trim();
			}
			else
				Valid = null;
		}
		if (FCode.equalsIgnoreCase("CommandType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CommandType = FValue.trim();
			}
			else
				CommandType = null;
		}
		if (FCode.equalsIgnoreCase("ParaNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ParaNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("ParaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParaType = FValue.trim();
			}
			else
				ParaType = null;
		}
		if (FCode.equalsIgnoreCase("ResultType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ResultType = FValue.trim();
			}
			else
				ResultType = null;
		}
		if (FCode.equalsIgnoreCase("Description"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Description = FValue.trim();
			}
			else
				Description = null;
		}
		if (FCode.equalsIgnoreCase("CommType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CommType = FValue.trim();
			}
			else
				CommType = null;
		}
		if (FCode.equalsIgnoreCase("CommDetail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CommDetail = FValue.trim();
			}
			else
				CommDetail = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LRCommandSchema other = (LRCommandSchema)otherObject;
		return
			Name.equals(other.getName())
			&& Display.equals(other.getDisplay())
			&& Implenmation.equals(other.getImplenmation())
			&& Valid.equals(other.getValid())
			&& CommandType.equals(other.getCommandType())
			&& ParaNum == other.getParaNum()
			&& ParaType.equals(other.getParaType())
			&& ResultType.equals(other.getResultType())
			&& Description.equals(other.getDescription())
			&& CommType.equals(other.getCommType())
			&& CommDetail.equals(other.getCommDetail());
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
		if( strFieldName.equals("Name") ) {
			return 0;
		}
		if( strFieldName.equals("Display") ) {
			return 1;
		}
		if( strFieldName.equals("Implenmation") ) {
			return 2;
		}
		if( strFieldName.equals("Valid") ) {
			return 3;
		}
		if( strFieldName.equals("CommandType") ) {
			return 4;
		}
		if( strFieldName.equals("ParaNum") ) {
			return 5;
		}
		if( strFieldName.equals("ParaType") ) {
			return 6;
		}
		if( strFieldName.equals("ResultType") ) {
			return 7;
		}
		if( strFieldName.equals("Description") ) {
			return 8;
		}
		if( strFieldName.equals("CommType") ) {
			return 9;
		}
		if( strFieldName.equals("CommDetail") ) {
			return 10;
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
				strFieldName = "Name";
				break;
			case 1:
				strFieldName = "Display";
				break;
			case 2:
				strFieldName = "Implenmation";
				break;
			case 3:
				strFieldName = "Valid";
				break;
			case 4:
				strFieldName = "CommandType";
				break;
			case 5:
				strFieldName = "ParaNum";
				break;
			case 6:
				strFieldName = "ParaType";
				break;
			case 7:
				strFieldName = "ResultType";
				break;
			case 8:
				strFieldName = "Description";
				break;
			case 9:
				strFieldName = "CommType";
				break;
			case 10:
				strFieldName = "CommDetail";
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
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Display") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Implenmation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Valid") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CommandType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParaNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ParaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ResultType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Description") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CommType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CommDetail") ) {
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
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
