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
import com.sinosoft.lis.db.LMRiskRelaDB;

/*
 * <p>ClassName: LMRiskRelaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_11
 */
public class LMRiskRelaSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMRiskRelaSchema.class);
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 关联险种编码 */
	private String RelaRiskCode;
	/** 险种之间的关系 */
	private String RelaCode;
	/** 管理机构 */
	private String ManageComGrp;
	/** Column_5 */
	private String DKSubPrem;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskRelaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RiskCode";
		pk[1] = "RelaRiskCode";
		pk[2] = "RelaCode";
		pk[3] = "ManageComGrp";

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
		LMRiskRelaSchema cloned = (LMRiskRelaSchema)super.clone();
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
		if(aRiskCode!=null && aRiskCode.length()>8)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值8");
		RiskCode = aRiskCode;
	}
	public String getRelaRiskCode()
	{
		return RelaRiskCode;
	}
	public void setRelaRiskCode(String aRelaRiskCode)
	{
		if(aRelaRiskCode!=null && aRelaRiskCode.length()>8)
			throw new IllegalArgumentException("关联险种编码RelaRiskCode值"+aRelaRiskCode+"的长度"+aRelaRiskCode.length()+"大于最大值8");
		RelaRiskCode = aRelaRiskCode;
	}
	/**
	* 01-主附 02-主主 03-产品组合 04-附附
	*/
	public String getRelaCode()
	{
		return RelaCode;
	}
	public void setRelaCode(String aRelaCode)
	{
		if(aRelaCode!=null && aRelaCode.length()>2)
			throw new IllegalArgumentException("险种之间的关系RelaCode值"+aRelaCode+"的长度"+aRelaCode.length()+"大于最大值2");
		RelaCode = aRelaCode;
	}
	/**
	* 填写该险种关系允许在哪些区站出现
	*/
	public String getManageComGrp()
	{
		return ManageComGrp;
	}
	public void setManageComGrp(String aManageComGrp)
	{
		if(aManageComGrp!=null && aManageComGrp.length()>300)
			throw new IllegalArgumentException("管理机构ManageComGrp值"+aManageComGrp+"的长度"+aManageComGrp.length()+"大于最大值300");
		ManageComGrp = aManageComGrp;
	}
	public String getDKSubPrem()
	{
		return DKSubPrem;
	}
	public void setDKSubPrem(String aDKSubPrem)
	{
		if(aDKSubPrem!=null && aDKSubPrem.length()>1)
			throw new IllegalArgumentException("Column_5DKSubPrem值"+aDKSubPrem+"的长度"+aDKSubPrem.length()+"大于最大值1");
		DKSubPrem = aDKSubPrem;
	}

	/**
	* 使用另外一个 LMRiskRelaSchema 对象给 Schema 赋值
	* @param: aLMRiskRelaSchema LMRiskRelaSchema
	**/
	public void setSchema(LMRiskRelaSchema aLMRiskRelaSchema)
	{
		this.RiskCode = aLMRiskRelaSchema.getRiskCode();
		this.RelaRiskCode = aLMRiskRelaSchema.getRelaRiskCode();
		this.RelaCode = aLMRiskRelaSchema.getRelaCode();
		this.ManageComGrp = aLMRiskRelaSchema.getManageComGrp();
		this.DKSubPrem = aLMRiskRelaSchema.getDKSubPrem();
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

			if( rs.getString("RelaRiskCode") == null )
				this.RelaRiskCode = null;
			else
				this.RelaRiskCode = rs.getString("RelaRiskCode").trim();

			if( rs.getString("RelaCode") == null )
				this.RelaCode = null;
			else
				this.RelaCode = rs.getString("RelaCode").trim();

			if( rs.getString("ManageComGrp") == null )
				this.ManageComGrp = null;
			else
				this.ManageComGrp = rs.getString("ManageComGrp").trim();

			if( rs.getString("DKSubPrem") == null )
				this.DKSubPrem = null;
			else
				this.DKSubPrem = rs.getString("DKSubPrem").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMRiskRela表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskRelaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskRelaSchema getSchema()
	{
		LMRiskRelaSchema aLMRiskRelaSchema = new LMRiskRelaSchema();
		aLMRiskRelaSchema.setSchema(this);
		return aLMRiskRelaSchema;
	}

	public LMRiskRelaDB getDB()
	{
		LMRiskRelaDB aDBOper = new LMRiskRelaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaRiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageComGrp)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DKSubPrem));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskRela>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RelaRiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RelaCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ManageComGrp = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DKSubPrem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskRelaSchema";
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
		if (FCode.equalsIgnoreCase("RelaRiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaRiskCode));
		}
		if (FCode.equalsIgnoreCase("RelaCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaCode));
		}
		if (FCode.equalsIgnoreCase("ManageComGrp"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageComGrp));
		}
		if (FCode.equalsIgnoreCase("DKSubPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DKSubPrem));
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
				strFieldValue = StrTool.GBKToUnicode(RelaRiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RelaCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ManageComGrp);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DKSubPrem);
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
		if (FCode.equalsIgnoreCase("RelaRiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaRiskCode = FValue.trim();
			}
			else
				RelaRiskCode = null;
		}
		if (FCode.equalsIgnoreCase("RelaCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaCode = FValue.trim();
			}
			else
				RelaCode = null;
		}
		if (FCode.equalsIgnoreCase("ManageComGrp"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageComGrp = FValue.trim();
			}
			else
				ManageComGrp = null;
		}
		if (FCode.equalsIgnoreCase("DKSubPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DKSubPrem = FValue.trim();
			}
			else
				DKSubPrem = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskRelaSchema other = (LMRiskRelaSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RelaRiskCode.equals(other.getRelaRiskCode())
			&& RelaCode.equals(other.getRelaCode())
			&& ManageComGrp.equals(other.getManageComGrp())
			&& DKSubPrem.equals(other.getDKSubPrem());
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
		if( strFieldName.equals("RelaRiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("RelaCode") ) {
			return 2;
		}
		if( strFieldName.equals("ManageComGrp") ) {
			return 3;
		}
		if( strFieldName.equals("DKSubPrem") ) {
			return 4;
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
				strFieldName = "RelaRiskCode";
				break;
			case 2:
				strFieldName = "RelaCode";
				break;
			case 3:
				strFieldName = "ManageComGrp";
				break;
			case 4:
				strFieldName = "DKSubPrem";
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
		if( strFieldName.equals("RelaRiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageComGrp") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DKSubPrem") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
