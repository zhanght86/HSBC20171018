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
import com.sinosoft.lis.db.LPStopReStartTraceDB;

/*
 * <p>ClassName: LPStopReStartTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LPStopReStartTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPStopReStartTraceSchema.class);
	// @Field
	/** 团体保单号 */
	private String GrpContNo;
	/** 中止批单号 */
	private String StopEdorNo;
	/** 复效批单号 */
	private String ReStartEdorNo;
	/** 中止日期 */
	private Date StopDate;
	/** 复效日期 */
	private Date ReStartDate;
	/** 生效日期 */
	private Date ValDate;
	/** 终止日期 */
	private Date EndDate;
	/** 前一次终止日期 */
	private Date LastEndDate;
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

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPStopReStartTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "GrpContNo";
		pk[1] = "StopEdorNo";

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
		LPStopReStartTraceSchema cloned = (LPStopReStartTraceSchema)super.clone();
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
	* SGP+两位年份+4位省市行政代码(保监）+9位流水
	*/
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("团体保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getStopEdorNo()
	{
		return StopEdorNo;
	}
	public void setStopEdorNo(String aStopEdorNo)
	{
		if(aStopEdorNo!=null && aStopEdorNo.length()>20)
			throw new IllegalArgumentException("中止批单号StopEdorNo值"+aStopEdorNo+"的长度"+aStopEdorNo.length()+"大于最大值20");
		StopEdorNo = aStopEdorNo;
	}
	public String getReStartEdorNo()
	{
		return ReStartEdorNo;
	}
	public void setReStartEdorNo(String aReStartEdorNo)
	{
		if(aReStartEdorNo!=null && aReStartEdorNo.length()>20)
			throw new IllegalArgumentException("复效批单号ReStartEdorNo值"+aReStartEdorNo+"的长度"+aReStartEdorNo.length()+"大于最大值20");
		ReStartEdorNo = aReStartEdorNo;
	}
	public String getStopDate()
	{
		if( StopDate != null )
			return fDate.getString(StopDate);
		else
			return null;
	}
	public void setStopDate(Date aStopDate)
	{
		StopDate = aStopDate;
	}
	public void setStopDate(String aStopDate)
	{
		if (aStopDate != null && !aStopDate.equals("") )
		{
			StopDate = fDate.getDate( aStopDate );
		}
		else
			StopDate = null;
	}

	public String getReStartDate()
	{
		if( ReStartDate != null )
			return fDate.getString(ReStartDate);
		else
			return null;
	}
	public void setReStartDate(Date aReStartDate)
	{
		ReStartDate = aReStartDate;
	}
	public void setReStartDate(String aReStartDate)
	{
		if (aReStartDate != null && !aReStartDate.equals("") )
		{
			ReStartDate = fDate.getDate( aReStartDate );
		}
		else
			ReStartDate = null;
	}

	public String getValDate()
	{
		if( ValDate != null )
			return fDate.getString(ValDate);
		else
			return null;
	}
	public void setValDate(Date aValDate)
	{
		ValDate = aValDate;
	}
	public void setValDate(String aValDate)
	{
		if (aValDate != null && !aValDate.equals("") )
		{
			ValDate = fDate.getDate( aValDate );
		}
		else
			ValDate = null;
	}

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	public String getLastEndDate()
	{
		if( LastEndDate != null )
			return fDate.getString(LastEndDate);
		else
			return null;
	}
	public void setLastEndDate(Date aLastEndDate)
	{
		LastEndDate = aLastEndDate;
	}
	public void setLastEndDate(String aLastEndDate)
	{
		if (aLastEndDate != null && !aLastEndDate.equals("") )
		{
			LastEndDate = fDate.getDate( aLastEndDate );
		}
		else
			LastEndDate = null;
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
	* 使用另外一个 LPStopReStartTraceSchema 对象给 Schema 赋值
	* @param: aLPStopReStartTraceSchema LPStopReStartTraceSchema
	**/
	public void setSchema(LPStopReStartTraceSchema aLPStopReStartTraceSchema)
	{
		this.GrpContNo = aLPStopReStartTraceSchema.getGrpContNo();
		this.StopEdorNo = aLPStopReStartTraceSchema.getStopEdorNo();
		this.ReStartEdorNo = aLPStopReStartTraceSchema.getReStartEdorNo();
		this.StopDate = fDate.getDate( aLPStopReStartTraceSchema.getStopDate());
		this.ReStartDate = fDate.getDate( aLPStopReStartTraceSchema.getReStartDate());
		this.ValDate = fDate.getDate( aLPStopReStartTraceSchema.getValDate());
		this.EndDate = fDate.getDate( aLPStopReStartTraceSchema.getEndDate());
		this.LastEndDate = fDate.getDate( aLPStopReStartTraceSchema.getLastEndDate());
		this.MakeOperator = aLPStopReStartTraceSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLPStopReStartTraceSchema.getMakeDate());
		this.MakeTime = aLPStopReStartTraceSchema.getMakeTime();
		this.ModifyOperator = aLPStopReStartTraceSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLPStopReStartTraceSchema.getModifyDate());
		this.ModifyTime = aLPStopReStartTraceSchema.getModifyTime();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("StopEdorNo") == null )
				this.StopEdorNo = null;
			else
				this.StopEdorNo = rs.getString("StopEdorNo").trim();

			if( rs.getString("ReStartEdorNo") == null )
				this.ReStartEdorNo = null;
			else
				this.ReStartEdorNo = rs.getString("ReStartEdorNo").trim();

			this.StopDate = rs.getDate("StopDate");
			this.ReStartDate = rs.getDate("ReStartDate");
			this.ValDate = rs.getDate("ValDate");
			this.EndDate = rs.getDate("EndDate");
			this.LastEndDate = rs.getDate("LastEndDate");
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
			logger.debug("数据库中的LPStopReStartTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPStopReStartTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPStopReStartTraceSchema getSchema()
	{
		LPStopReStartTraceSchema aLPStopReStartTraceSchema = new LPStopReStartTraceSchema();
		aLPStopReStartTraceSchema.setSchema(this);
		return aLPStopReStartTraceSchema;
	}

	public LPStopReStartTraceDB getDB()
	{
		LPStopReStartTraceDB aDBOper = new LPStopReStartTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPStopReStartTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StopEdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReStartEdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StopDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ValDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPStopReStartTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			StopEdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ReStartEdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			StopDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			ReStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			ValDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			LastEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPStopReStartTraceSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("StopEdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StopEdorNo));
		}
		if (FCode.equalsIgnoreCase("ReStartEdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReStartEdorNo));
		}
		if (FCode.equalsIgnoreCase("StopDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStopDate()));
		}
		if (FCode.equalsIgnoreCase("ReStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReStartDate()));
		}
		if (FCode.equalsIgnoreCase("ValDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getValDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("LastEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastEndDate()));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(StopEdorNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ReStartEdorNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStopDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReStartDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getValDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastEndDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 13:
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

		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("StopEdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StopEdorNo = FValue.trim();
			}
			else
				StopEdorNo = null;
		}
		if (FCode.equalsIgnoreCase("ReStartEdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReStartEdorNo = FValue.trim();
			}
			else
				ReStartEdorNo = null;
		}
		if (FCode.equalsIgnoreCase("StopDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StopDate = fDate.getDate( FValue );
			}
			else
				StopDate = null;
		}
		if (FCode.equalsIgnoreCase("ReStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReStartDate = fDate.getDate( FValue );
			}
			else
				ReStartDate = null;
		}
		if (FCode.equalsIgnoreCase("ValDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ValDate = fDate.getDate( FValue );
			}
			else
				ValDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("LastEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastEndDate = fDate.getDate( FValue );
			}
			else
				LastEndDate = null;
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
		LPStopReStartTraceSchema other = (LPStopReStartTraceSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& StopEdorNo.equals(other.getStopEdorNo())
			&& ReStartEdorNo.equals(other.getReStartEdorNo())
			&& fDate.getString(StopDate).equals(other.getStopDate())
			&& fDate.getString(ReStartDate).equals(other.getReStartDate())
			&& fDate.getString(ValDate).equals(other.getValDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& fDate.getString(LastEndDate).equals(other.getLastEndDate())
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("StopEdorNo") ) {
			return 1;
		}
		if( strFieldName.equals("ReStartEdorNo") ) {
			return 2;
		}
		if( strFieldName.equals("StopDate") ) {
			return 3;
		}
		if( strFieldName.equals("ReStartDate") ) {
			return 4;
		}
		if( strFieldName.equals("ValDate") ) {
			return 5;
		}
		if( strFieldName.equals("EndDate") ) {
			return 6;
		}
		if( strFieldName.equals("LastEndDate") ) {
			return 7;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
		switch(nFieldIndex) {
			case 0:
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "StopEdorNo";
				break;
			case 2:
				strFieldName = "ReStartEdorNo";
				break;
			case 3:
				strFieldName = "StopDate";
				break;
			case 4:
				strFieldName = "ReStartDate";
				break;
			case 5:
				strFieldName = "ValDate";
				break;
			case 6:
				strFieldName = "EndDate";
				break;
			case 7:
				strFieldName = "LastEndDate";
				break;
			case 8:
				strFieldName = "MakeOperator";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
				strFieldName = "MakeTime";
				break;
			case 11:
				strFieldName = "ModifyOperator";
				break;
			case 12:
				strFieldName = "ModifyDate";
				break;
			case 13:
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StopEdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReStartEdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StopDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ReStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ValDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastEndDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
