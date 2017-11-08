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
import com.sinosoft.lis.db.LMRiskRoleDB;

/*
 * <p>ClassName: LMRiskRoleSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRiskRoleSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMRiskRoleSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 角色 */
	private String RiskRole;
	/** 性别 */
	private String RiskRoleSex;
	/** 序号（级别） */
	private String RiskRoleNo;
	/** 最小年龄单位 */
	private String MinAppAgeFlag;
	/** 最小年龄 */
	private int MinAppAge;
	/** 最大年龄单位 */
	private String MAXAppAgeFlag;
	/** 最大年龄 */
	private int MAXAppAge;
	/** 续保最小年龄单位 */
	private String MinRnewAgeFlag;
	/** 续保最小年龄 */
	private int MinRnewAge;
	/** 续保最大年龄单位 */
	private String MAXRnewAgeFlag;
	/** 续保最大年龄 */
	private int MAXRnewAge;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskRoleSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RiskCode";
		pk[1] = "RiskRole";
		pk[2] = "RiskRoleSex";
		pk[3] = "RiskRoleNo";

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
		LMRiskRoleSchema cloned = (LMRiskRoleSchema)super.clone();
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
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	/**
	* 00-	投保人<p>
	* 01-	被保人<p>
	* 02-	受益人
	*/
	public String getRiskRole()
	{
		return RiskRole;
	}
	public void setRiskRole(String aRiskRole)
	{
		RiskRole = aRiskRole;
	}
	/**
	* 0-	男<p>
	* 1-	女<p>
	* 2-	不限
	*/
	public String getRiskRoleSex()
	{
		return RiskRoleSex;
	}
	public void setRiskRoleSex(String aRiskRoleSex)
	{
		RiskRoleSex = aRiskRoleSex;
	}
	public String getRiskRoleNo()
	{
		return RiskRoleNo;
	}
	public void setRiskRoleNo(String aRiskRoleNo)
	{
		RiskRoleNo = aRiskRoleNo;
	}
	/**
	* Y-年<p>
	* M-月<p>
	* D-日
	*/
	public String getMinAppAgeFlag()
	{
		return MinAppAgeFlag;
	}
	public void setMinAppAgeFlag(String aMinAppAgeFlag)
	{
		MinAppAgeFlag = aMinAppAgeFlag;
	}
	public int getMinAppAge()
	{
		return MinAppAge;
	}
	public void setMinAppAge(int aMinAppAge)
	{
		MinAppAge = aMinAppAge;
	}
	public void setMinAppAge(String aMinAppAge)
	{
		if (aMinAppAge != null && !aMinAppAge.equals(""))
		{
			Integer tInteger = new Integer(aMinAppAge);
			int i = tInteger.intValue();
			MinAppAge = i;
		}
	}

	/**
	* Y-年<p>
	* M-月<p>
	* D-日
	*/
	public String getMAXAppAgeFlag()
	{
		return MAXAppAgeFlag;
	}
	public void setMAXAppAgeFlag(String aMAXAppAgeFlag)
	{
		MAXAppAgeFlag = aMAXAppAgeFlag;
	}
	public int getMAXAppAge()
	{
		return MAXAppAge;
	}
	public void setMAXAppAge(int aMAXAppAge)
	{
		MAXAppAge = aMAXAppAge;
	}
	public void setMAXAppAge(String aMAXAppAge)
	{
		if (aMAXAppAge != null && !aMAXAppAge.equals(""))
		{
			Integer tInteger = new Integer(aMAXAppAge);
			int i = tInteger.intValue();
			MAXAppAge = i;
		}
	}

	public String getMinRnewAgeFlag()
	{
		return MinRnewAgeFlag;
	}
	public void setMinRnewAgeFlag(String aMinRnewAgeFlag)
	{
		MinRnewAgeFlag = aMinRnewAgeFlag;
	}
	public int getMinRnewAge()
	{
		return MinRnewAge;
	}
	public void setMinRnewAge(int aMinRnewAge)
	{
		MinRnewAge = aMinRnewAge;
	}
	public void setMinRnewAge(String aMinRnewAge)
	{
		if (aMinRnewAge != null && !aMinRnewAge.equals(""))
		{
			Integer tInteger = new Integer(aMinRnewAge);
			int i = tInteger.intValue();
			MinRnewAge = i;
		}
	}

	public String getMAXRnewAgeFlag()
	{
		return MAXRnewAgeFlag;
	}
	public void setMAXRnewAgeFlag(String aMAXRnewAgeFlag)
	{
		MAXRnewAgeFlag = aMAXRnewAgeFlag;
	}
	public int getMAXRnewAge()
	{
		return MAXRnewAge;
	}
	public void setMAXRnewAge(int aMAXRnewAge)
	{
		MAXRnewAge = aMAXRnewAge;
	}
	public void setMAXRnewAge(String aMAXRnewAge)
	{
		if (aMAXRnewAge != null && !aMAXRnewAge.equals(""))
		{
			Integer tInteger = new Integer(aMAXRnewAge);
			int i = tInteger.intValue();
			MAXRnewAge = i;
		}
	}


	/**
	* 使用另外一个 LMRiskRoleSchema 对象给 Schema 赋值
	* @param: aLMRiskRoleSchema LMRiskRoleSchema
	**/
	public void setSchema(LMRiskRoleSchema aLMRiskRoleSchema)
	{
		this.RiskCode = aLMRiskRoleSchema.getRiskCode();
		this.RiskVer = aLMRiskRoleSchema.getRiskVer();
		this.RiskRole = aLMRiskRoleSchema.getRiskRole();
		this.RiskRoleSex = aLMRiskRoleSchema.getRiskRoleSex();
		this.RiskRoleNo = aLMRiskRoleSchema.getRiskRoleNo();
		this.MinAppAgeFlag = aLMRiskRoleSchema.getMinAppAgeFlag();
		this.MinAppAge = aLMRiskRoleSchema.getMinAppAge();
		this.MAXAppAgeFlag = aLMRiskRoleSchema.getMAXAppAgeFlag();
		this.MAXAppAge = aLMRiskRoleSchema.getMAXAppAge();
		this.MinRnewAgeFlag = aLMRiskRoleSchema.getMinRnewAgeFlag();
		this.MinRnewAge = aLMRiskRoleSchema.getMinRnewAge();
		this.MAXRnewAgeFlag = aLMRiskRoleSchema.getMAXRnewAgeFlag();
		this.MAXRnewAge = aLMRiskRoleSchema.getMAXRnewAge();
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

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("RiskRole") == null )
				this.RiskRole = null;
			else
				this.RiskRole = rs.getString("RiskRole").trim();

			if( rs.getString("RiskRoleSex") == null )
				this.RiskRoleSex = null;
			else
				this.RiskRoleSex = rs.getString("RiskRoleSex").trim();

			if( rs.getString("RiskRoleNo") == null )
				this.RiskRoleNo = null;
			else
				this.RiskRoleNo = rs.getString("RiskRoleNo").trim();

			if( rs.getString("MinAppAgeFlag") == null )
				this.MinAppAgeFlag = null;
			else
				this.MinAppAgeFlag = rs.getString("MinAppAgeFlag").trim();

			this.MinAppAge = rs.getInt("MinAppAge");
			if( rs.getString("MAXAppAgeFlag") == null )
				this.MAXAppAgeFlag = null;
			else
				this.MAXAppAgeFlag = rs.getString("MAXAppAgeFlag").trim();

			this.MAXAppAge = rs.getInt("MAXAppAge");
			if( rs.getString("MinRnewAgeFlag") == null )
				this.MinRnewAgeFlag = null;
			else
				this.MinRnewAgeFlag = rs.getString("MinRnewAgeFlag").trim();

			this.MinRnewAge = rs.getInt("MinRnewAge");
			if( rs.getString("MAXRnewAgeFlag") == null )
				this.MAXRnewAgeFlag = null;
			else
				this.MAXRnewAgeFlag = rs.getString("MAXRnewAgeFlag").trim();

			this.MAXRnewAge = rs.getInt("MAXRnewAge");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMRiskRole表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskRoleSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskRoleSchema getSchema()
	{
		LMRiskRoleSchema aLMRiskRoleSchema = new LMRiskRoleSchema();
		aLMRiskRoleSchema.setSchema(this);
		return aLMRiskRoleSchema;
	}

	public LMRiskRoleDB getDB()
	{
		LMRiskRoleDB aDBOper = new LMRiskRoleDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskRole描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskRole)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskRoleSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskRoleNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MinAppAgeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MinAppAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MAXAppAgeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MAXAppAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MinRnewAgeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MinRnewAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MAXRnewAgeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MAXRnewAge));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskRole>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskRole = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskRoleSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskRoleNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MinAppAgeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MinAppAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			MAXAppAgeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MAXAppAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			MinRnewAgeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MinRnewAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			MAXRnewAgeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MAXRnewAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskRoleSchema";
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("RiskRole"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskRole));
		}
		if (FCode.equalsIgnoreCase("RiskRoleSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskRoleSex));
		}
		if (FCode.equalsIgnoreCase("RiskRoleNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskRoleNo));
		}
		if (FCode.equalsIgnoreCase("MinAppAgeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinAppAgeFlag));
		}
		if (FCode.equalsIgnoreCase("MinAppAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinAppAge));
		}
		if (FCode.equalsIgnoreCase("MAXAppAgeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MAXAppAgeFlag));
		}
		if (FCode.equalsIgnoreCase("MAXAppAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MAXAppAge));
		}
		if (FCode.equalsIgnoreCase("MinRnewAgeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinRnewAgeFlag));
		}
		if (FCode.equalsIgnoreCase("MinRnewAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinRnewAge));
		}
		if (FCode.equalsIgnoreCase("MAXRnewAgeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MAXRnewAgeFlag));
		}
		if (FCode.equalsIgnoreCase("MAXRnewAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MAXRnewAge));
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
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskRole);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskRoleSex);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskRoleNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(MinAppAgeFlag);
				break;
			case 6:
				strFieldValue = String.valueOf(MinAppAge);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(MAXAppAgeFlag);
				break;
			case 8:
				strFieldValue = String.valueOf(MAXAppAge);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MinRnewAgeFlag);
				break;
			case 10:
				strFieldValue = String.valueOf(MinRnewAge);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MAXRnewAgeFlag);
				break;
			case 12:
				strFieldValue = String.valueOf(MAXRnewAge);
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
		}
		if (FCode.equalsIgnoreCase("RiskRole"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskRole = FValue.trim();
			}
			else
				RiskRole = null;
		}
		if (FCode.equalsIgnoreCase("RiskRoleSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskRoleSex = FValue.trim();
			}
			else
				RiskRoleSex = null;
		}
		if (FCode.equalsIgnoreCase("RiskRoleNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskRoleNo = FValue.trim();
			}
			else
				RiskRoleNo = null;
		}
		if (FCode.equalsIgnoreCase("MinAppAgeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MinAppAgeFlag = FValue.trim();
			}
			else
				MinAppAgeFlag = null;
		}
		if (FCode.equalsIgnoreCase("MinAppAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MinAppAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("MAXAppAgeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MAXAppAgeFlag = FValue.trim();
			}
			else
				MAXAppAgeFlag = null;
		}
		if (FCode.equalsIgnoreCase("MAXAppAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MAXAppAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("MinRnewAgeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MinRnewAgeFlag = FValue.trim();
			}
			else
				MinRnewAgeFlag = null;
		}
		if (FCode.equalsIgnoreCase("MinRnewAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MinRnewAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("MAXRnewAgeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MAXRnewAgeFlag = FValue.trim();
			}
			else
				MAXRnewAgeFlag = null;
		}
		if (FCode.equalsIgnoreCase("MAXRnewAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MAXRnewAge = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskRoleSchema other = (LMRiskRoleSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskRole.equals(other.getRiskRole())
			&& RiskRoleSex.equals(other.getRiskRoleSex())
			&& RiskRoleNo.equals(other.getRiskRoleNo())
			&& MinAppAgeFlag.equals(other.getMinAppAgeFlag())
			&& MinAppAge == other.getMinAppAge()
			&& MAXAppAgeFlag.equals(other.getMAXAppAgeFlag())
			&& MAXAppAge == other.getMAXAppAge()
			&& MinRnewAgeFlag.equals(other.getMinRnewAgeFlag())
			&& MinRnewAge == other.getMinRnewAge()
			&& MAXRnewAgeFlag.equals(other.getMAXRnewAgeFlag())
			&& MAXRnewAge == other.getMAXRnewAge();
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
		if( strFieldName.equals("RiskVer") ) {
			return 1;
		}
		if( strFieldName.equals("RiskRole") ) {
			return 2;
		}
		if( strFieldName.equals("RiskRoleSex") ) {
			return 3;
		}
		if( strFieldName.equals("RiskRoleNo") ) {
			return 4;
		}
		if( strFieldName.equals("MinAppAgeFlag") ) {
			return 5;
		}
		if( strFieldName.equals("MinAppAge") ) {
			return 6;
		}
		if( strFieldName.equals("MAXAppAgeFlag") ) {
			return 7;
		}
		if( strFieldName.equals("MAXAppAge") ) {
			return 8;
		}
		if( strFieldName.equals("MinRnewAgeFlag") ) {
			return 9;
		}
		if( strFieldName.equals("MinRnewAge") ) {
			return 10;
		}
		if( strFieldName.equals("MAXRnewAgeFlag") ) {
			return 11;
		}
		if( strFieldName.equals("MAXRnewAge") ) {
			return 12;
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
				strFieldName = "RiskVer";
				break;
			case 2:
				strFieldName = "RiskRole";
				break;
			case 3:
				strFieldName = "RiskRoleSex";
				break;
			case 4:
				strFieldName = "RiskRoleNo";
				break;
			case 5:
				strFieldName = "MinAppAgeFlag";
				break;
			case 6:
				strFieldName = "MinAppAge";
				break;
			case 7:
				strFieldName = "MAXAppAgeFlag";
				break;
			case 8:
				strFieldName = "MAXAppAge";
				break;
			case 9:
				strFieldName = "MinRnewAgeFlag";
				break;
			case 10:
				strFieldName = "MinRnewAge";
				break;
			case 11:
				strFieldName = "MAXRnewAgeFlag";
				break;
			case 12:
				strFieldName = "MAXRnewAge";
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
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskRole") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskRoleSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskRoleNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MinAppAgeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MinAppAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MAXAppAgeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MAXAppAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MinRnewAgeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MinRnewAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MAXRnewAgeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MAXRnewAge") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
