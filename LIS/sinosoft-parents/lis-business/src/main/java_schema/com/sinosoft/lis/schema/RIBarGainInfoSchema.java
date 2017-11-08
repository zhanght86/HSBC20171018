

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RIBarGainInfoDB;

/*
 * <p>ClassName: RIBarGainInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIBarGainInfoSchema implements Schema, Cloneable
{
	// @Field
	/** 再保合同号码 */
	private String RIContNo;
	/** 匹配方式 */
	private String MatchMode;
	/** 再保合同名称 */
	private String RIContName;
	/** 合同类型 */
	private String ContType;
	/** 团个标志 */
	private String GIType;
	/** 险类类型 */
	private String RiskType;
	/** 合同生效日期 */
	private Date CValiDate;
	/** 合同终止日期 */
	private Date EndDate;
	/** 合同签定日期 */
	private Date RISignDate;
	/** 合同描述 */
	private String RIContDes;
	/** 状态 */
	private String State;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIBarGainInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RIContNo";

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
		RIBarGainInfoSchema cloned = (RIBarGainInfoSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRIContNo()
	{
		return RIContNo;
	}
	public void setRIContNo(String aRIContNo)
	{
		RIContNo = aRIContNo;
	}
	/**
	* 01-业务发生日<p>
	* <p>
	* 02-保单生效日
	*/
	public String getMatchMode()
	{
		return MatchMode;
	}
	public void setMatchMode(String aMatchMode)
	{
		MatchMode = aMatchMode;
	}
	public String getRIContName()
	{
		return RIContName;
	}
	public void setRIContName(String aRIContName)
	{
		RIContName = aRIContName;
	}
	/**
	* 01-共保合同<p>
	* 02-合同分保<p>
	* 03-临分合同
	*/
	public String getContType()
	{
		return ContType;
	}
	public void setContType(String aContType)
	{
		ContType = aContType;
	}
	/**
	* 1-个人总投保单<p>
	* 2-集体总单
	*/
	public String getGIType()
	{
		return GIType;
	}
	public void setGIType(String aGIType)
	{
		GIType = aGIType;
	}
	public String getRiskType()
	{
		return RiskType;
	}
	public void setRiskType(String aRiskType)
	{
		RiskType = aRiskType;
	}
	public String getCValiDate()
	{
		if( CValiDate != null )
			return fDate.getString(CValiDate);
		else
			return null;
	}
	public void setCValiDate(Date aCValiDate)
	{
		CValiDate = aCValiDate;
	}
	public void setCValiDate(String aCValiDate)
	{
		if (aCValiDate != null && !aCValiDate.equals("") )
		{
			CValiDate = fDate.getDate( aCValiDate );
		}
		else
			CValiDate = null;
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

	public String getRISignDate()
	{
		if( RISignDate != null )
			return fDate.getString(RISignDate);
		else
			return null;
	}
	public void setRISignDate(Date aRISignDate)
	{
		RISignDate = aRISignDate;
	}
	public void setRISignDate(String aRISignDate)
	{
		if (aRISignDate != null && !aRISignDate.equals("") )
		{
			RISignDate = fDate.getDate( aRISignDate );
		}
		else
			RISignDate = null;
	}

	public String getRIContDes()
	{
		return RIContDes;
	}
	public void setRIContDes(String aRIContDes)
	{
		RIContDes = aRIContDes;
	}
	/**
	* 0：失效合同<p>
	* <p>
	* 1：有效合同
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
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

	/**
	* 使用另外一个 RIBarGainInfoSchema 对象给 Schema 赋值
	* @param: aRIBarGainInfoSchema RIBarGainInfoSchema
	**/
	public void setSchema(RIBarGainInfoSchema aRIBarGainInfoSchema)
	{
		this.RIContNo = aRIBarGainInfoSchema.getRIContNo();
		this.MatchMode = aRIBarGainInfoSchema.getMatchMode();
		this.RIContName = aRIBarGainInfoSchema.getRIContName();
		this.ContType = aRIBarGainInfoSchema.getContType();
		this.GIType = aRIBarGainInfoSchema.getGIType();
		this.RiskType = aRIBarGainInfoSchema.getRiskType();
		this.CValiDate = fDate.getDate( aRIBarGainInfoSchema.getCValiDate());
		this.EndDate = fDate.getDate( aRIBarGainInfoSchema.getEndDate());
		this.RISignDate = fDate.getDate( aRIBarGainInfoSchema.getRISignDate());
		this.RIContDes = aRIBarGainInfoSchema.getRIContDes();
		this.State = aRIBarGainInfoSchema.getState();
		this.Operator = aRIBarGainInfoSchema.getOperator();
		this.MakeDate = fDate.getDate( aRIBarGainInfoSchema.getMakeDate());
		this.MakeTime = aRIBarGainInfoSchema.getMakeTime();
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
			if( rs.getString("RIContNo") == null )
				this.RIContNo = null;
			else
				this.RIContNo = rs.getString("RIContNo").trim();

			if( rs.getString("MatchMode") == null )
				this.MatchMode = null;
			else
				this.MatchMode = rs.getString("MatchMode").trim();

			if( rs.getString("RIContName") == null )
				this.RIContName = null;
			else
				this.RIContName = rs.getString("RIContName").trim();

			if( rs.getString("ContType") == null )
				this.ContType = null;
			else
				this.ContType = rs.getString("ContType").trim();

			if( rs.getString("GIType") == null )
				this.GIType = null;
			else
				this.GIType = rs.getString("GIType").trim();

			if( rs.getString("RiskType") == null )
				this.RiskType = null;
			else
				this.RiskType = rs.getString("RiskType").trim();

			this.CValiDate = rs.getDate("CValiDate");
			this.EndDate = rs.getDate("EndDate");
			this.RISignDate = rs.getDate("RISignDate");
			if( rs.getString("RIContDes") == null )
				this.RIContDes = null;
			else
				this.RIContDes = rs.getString("RIContDes").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIBarGainInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBarGainInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIBarGainInfoSchema getSchema()
	{
		RIBarGainInfoSchema aRIBarGainInfoSchema = new RIBarGainInfoSchema();
		aRIBarGainInfoSchema.setSchema(this);
		return aRIBarGainInfoSchema;
	}

	public RIBarGainInfoDB getDB()
	{
		RIBarGainInfoDB aDBOper = new RIBarGainInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIBarGainInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RIContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MatchMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIContName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GIType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RISignDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIContDes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIBarGainInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RIContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MatchMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RIContName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ContType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GIType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			RISignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			RIContDes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBarGainInfoSchema";
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
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIContNo));
		}
		if (FCode.equalsIgnoreCase("MatchMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MatchMode));
		}
		if (FCode.equalsIgnoreCase("RIContName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIContName));
		}
		if (FCode.equalsIgnoreCase("ContType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContType));
		}
		if (FCode.equalsIgnoreCase("GIType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GIType));
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("RISignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRISignDate()));
		}
		if (FCode.equalsIgnoreCase("RIContDes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIContDes));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(RIContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(MatchMode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RIContName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ContType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GIType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRISignDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RIContDes);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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

		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIContNo = FValue.trim();
			}
			else
				RIContNo = null;
		}
		if (FCode.equalsIgnoreCase("MatchMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MatchMode = FValue.trim();
			}
			else
				MatchMode = null;
		}
		if (FCode.equalsIgnoreCase("RIContName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIContName = FValue.trim();
			}
			else
				RIContName = null;
		}
		if (FCode.equalsIgnoreCase("ContType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContType = FValue.trim();
			}
			else
				ContType = null;
		}
		if (FCode.equalsIgnoreCase("GIType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GIType = FValue.trim();
			}
			else
				GIType = null;
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType = FValue.trim();
			}
			else
				RiskType = null;
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CValiDate = fDate.getDate( FValue );
			}
			else
				CValiDate = null;
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
		if (FCode.equalsIgnoreCase("RISignDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RISignDate = fDate.getDate( FValue );
			}
			else
				RISignDate = null;
		}
		if (FCode.equalsIgnoreCase("RIContDes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIContDes = FValue.trim();
			}
			else
				RIContDes = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIBarGainInfoSchema other = (RIBarGainInfoSchema)otherObject;
		return
			RIContNo.equals(other.getRIContNo())
			&& MatchMode.equals(other.getMatchMode())
			&& RIContName.equals(other.getRIContName())
			&& ContType.equals(other.getContType())
			&& GIType.equals(other.getGIType())
			&& RiskType.equals(other.getRiskType())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& fDate.getString(RISignDate).equals(other.getRISignDate())
			&& RIContDes.equals(other.getRIContDes())
			&& State.equals(other.getState())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime());
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
		if( strFieldName.equals("RIContNo") ) {
			return 0;
		}
		if( strFieldName.equals("MatchMode") ) {
			return 1;
		}
		if( strFieldName.equals("RIContName") ) {
			return 2;
		}
		if( strFieldName.equals("ContType") ) {
			return 3;
		}
		if( strFieldName.equals("GIType") ) {
			return 4;
		}
		if( strFieldName.equals("RiskType") ) {
			return 5;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 6;
		}
		if( strFieldName.equals("EndDate") ) {
			return 7;
		}
		if( strFieldName.equals("RISignDate") ) {
			return 8;
		}
		if( strFieldName.equals("RIContDes") ) {
			return 9;
		}
		if( strFieldName.equals("State") ) {
			return 10;
		}
		if( strFieldName.equals("Operator") ) {
			return 11;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 12;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				strFieldName = "RIContNo";
				break;
			case 1:
				strFieldName = "MatchMode";
				break;
			case 2:
				strFieldName = "RIContName";
				break;
			case 3:
				strFieldName = "ContType";
				break;
			case 4:
				strFieldName = "GIType";
				break;
			case 5:
				strFieldName = "RiskType";
				break;
			case 6:
				strFieldName = "CValiDate";
				break;
			case 7:
				strFieldName = "EndDate";
				break;
			case 8:
				strFieldName = "RISignDate";
				break;
			case 9:
				strFieldName = "RIContDes";
				break;
			case 10:
				strFieldName = "State";
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
		if( strFieldName.equals("RIContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MatchMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIContName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GIType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RISignDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RIContDes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
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
