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
import com.sinosoft.lis.db.LLCaseSpotTraceDB;

/*
 * <p>ClassName: LLCaseSpotTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLCaseSpotTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLCaseSpotTraceSchema.class);
	// @Field
	/** 案件号 */
	private String CaseNo;
	/** 抽检机构 */
	private String SpotMngCom;
	/** 抽检人 */
	private String Spoter;
	/** 抽检时间 */
	private Date SpotDate;
	/** 抽检结果 */
	private String SpotResult;
	/** 审查通过日期 */
	private Date PassDate;
	/** 管理机构 */
	private String ManageCom;
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

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLCaseSpotTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CaseNo";
		pk[1] = "SpotMngCom";

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
		LLCaseSpotTraceSchema cloned = (LLCaseSpotTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		if(aCaseNo!=null && aCaseNo.length()>20)
			throw new IllegalArgumentException("案件号CaseNo值"+aCaseNo+"的长度"+aCaseNo.length()+"大于最大值20");
		CaseNo = aCaseNo;
	}
	public String getSpotMngCom()
	{
		return SpotMngCom;
	}
	public void setSpotMngCom(String aSpotMngCom)
	{
		if(aSpotMngCom!=null && aSpotMngCom.length()>10)
			throw new IllegalArgumentException("抽检机构SpotMngCom值"+aSpotMngCom+"的长度"+aSpotMngCom.length()+"大于最大值10");
		SpotMngCom = aSpotMngCom;
	}
	public String getSpoter()
	{
		return Spoter;
	}
	public void setSpoter(String aSpoter)
	{
		if(aSpoter!=null && aSpoter.length()>10)
			throw new IllegalArgumentException("抽检人Spoter值"+aSpoter+"的长度"+aSpoter.length()+"大于最大值10");
		Spoter = aSpoter;
	}
	public String getSpotDate()
	{
		if( SpotDate != null )
			return fDate.getString(SpotDate);
		else
			return null;
	}
	public void setSpotDate(Date aSpotDate)
	{
		SpotDate = aSpotDate;
	}
	public void setSpotDate(String aSpotDate)
	{
		if (aSpotDate != null && !aSpotDate.equals("") )
		{
			SpotDate = fDate.getDate( aSpotDate );
		}
		else
			SpotDate = null;
	}

	public String getSpotResult()
	{
		return SpotResult;
	}
	public void setSpotResult(String aSpotResult)
	{
		if(aSpotResult!=null && aSpotResult.length()>1)
			throw new IllegalArgumentException("抽检结果SpotResult值"+aSpotResult+"的长度"+aSpotResult.length()+"大于最大值1");
		SpotResult = aSpotResult;
	}
	public String getPassDate()
	{
		if( PassDate != null )
			return fDate.getString(PassDate);
		else
			return null;
	}
	public void setPassDate(Date aPassDate)
	{
		PassDate = aPassDate;
	}
	public void setPassDate(String aPassDate)
	{
		if (aPassDate != null && !aPassDate.equals("") )
		{
			PassDate = fDate.getDate( aPassDate );
		}
		else
			PassDate = null;
	}

	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>10)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值10");
		ManageCom = aManageCom;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
		Operator = aOperator;
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
	* 使用另外一个 LLCaseSpotTraceSchema 对象给 Schema 赋值
	* @param: aLLCaseSpotTraceSchema LLCaseSpotTraceSchema
	**/
	public void setSchema(LLCaseSpotTraceSchema aLLCaseSpotTraceSchema)
	{
		this.CaseNo = aLLCaseSpotTraceSchema.getCaseNo();
		this.SpotMngCom = aLLCaseSpotTraceSchema.getSpotMngCom();
		this.Spoter = aLLCaseSpotTraceSchema.getSpoter();
		this.SpotDate = fDate.getDate( aLLCaseSpotTraceSchema.getSpotDate());
		this.SpotResult = aLLCaseSpotTraceSchema.getSpotResult();
		this.PassDate = fDate.getDate( aLLCaseSpotTraceSchema.getPassDate());
		this.ManageCom = aLLCaseSpotTraceSchema.getManageCom();
		this.Operator = aLLCaseSpotTraceSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLCaseSpotTraceSchema.getMakeDate());
		this.MakeTime = aLLCaseSpotTraceSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLCaseSpotTraceSchema.getModifyDate());
		this.ModifyTime = aLLCaseSpotTraceSchema.getModifyTime();
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
			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("SpotMngCom") == null )
				this.SpotMngCom = null;
			else
				this.SpotMngCom = rs.getString("SpotMngCom").trim();

			if( rs.getString("Spoter") == null )
				this.Spoter = null;
			else
				this.Spoter = rs.getString("Spoter").trim();

			this.SpotDate = rs.getDate("SpotDate");
			if( rs.getString("SpotResult") == null )
				this.SpotResult = null;
			else
				this.SpotResult = rs.getString("SpotResult").trim();

			this.PassDate = rs.getDate("PassDate");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

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
			logger.debug("数据库中的LLCaseSpotTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseSpotTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLCaseSpotTraceSchema getSchema()
	{
		LLCaseSpotTraceSchema aLLCaseSpotTraceSchema = new LLCaseSpotTraceSchema();
		aLLCaseSpotTraceSchema.setSchema(this);
		return aLLCaseSpotTraceSchema;
	}

	public LLCaseSpotTraceDB getDB()
	{
		LLCaseSpotTraceDB aDBOper = new LLCaseSpotTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseSpotTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpotMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Spoter)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SpotDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpotResult)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PassDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseSpotTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SpotMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Spoter = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SpotDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			SpotResult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PassDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseSpotTraceSchema";
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("SpotMngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpotMngCom));
		}
		if (FCode.equalsIgnoreCase("Spoter"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Spoter));
		}
		if (FCode.equalsIgnoreCase("SpotDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSpotDate()));
		}
		if (FCode.equalsIgnoreCase("SpotResult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpotResult));
		}
		if (FCode.equalsIgnoreCase("PassDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPassDate()));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
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
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SpotMngCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Spoter);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSpotDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SpotResult);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPassDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 11:
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

		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
		}
		if (FCode.equalsIgnoreCase("SpotMngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpotMngCom = FValue.trim();
			}
			else
				SpotMngCom = null;
		}
		if (FCode.equalsIgnoreCase("Spoter"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Spoter = FValue.trim();
			}
			else
				Spoter = null;
		}
		if (FCode.equalsIgnoreCase("SpotDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SpotDate = fDate.getDate( FValue );
			}
			else
				SpotDate = null;
		}
		if (FCode.equalsIgnoreCase("SpotResult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpotResult = FValue.trim();
			}
			else
				SpotResult = null;
		}
		if (FCode.equalsIgnoreCase("PassDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PassDate = fDate.getDate( FValue );
			}
			else
				PassDate = null;
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
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
		LLCaseSpotTraceSchema other = (LLCaseSpotTraceSchema)otherObject;
		return
			CaseNo.equals(other.getCaseNo())
			&& SpotMngCom.equals(other.getSpotMngCom())
			&& Spoter.equals(other.getSpoter())
			&& fDate.getString(SpotDate).equals(other.getSpotDate())
			&& SpotResult.equals(other.getSpotResult())
			&& fDate.getString(PassDate).equals(other.getPassDate())
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
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
		if( strFieldName.equals("CaseNo") ) {
			return 0;
		}
		if( strFieldName.equals("SpotMngCom") ) {
			return 1;
		}
		if( strFieldName.equals("Spoter") ) {
			return 2;
		}
		if( strFieldName.equals("SpotDate") ) {
			return 3;
		}
		if( strFieldName.equals("SpotResult") ) {
			return 4;
		}
		if( strFieldName.equals("PassDate") ) {
			return 5;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 6;
		}
		if( strFieldName.equals("Operator") ) {
			return 7;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 11;
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
				strFieldName = "CaseNo";
				break;
			case 1:
				strFieldName = "SpotMngCom";
				break;
			case 2:
				strFieldName = "Spoter";
				break;
			case 3:
				strFieldName = "SpotDate";
				break;
			case 4:
				strFieldName = "SpotResult";
				break;
			case 5:
				strFieldName = "PassDate";
				break;
			case 6:
				strFieldName = "ManageCom";
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
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpotMngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Spoter") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpotDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SpotResult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PassDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
