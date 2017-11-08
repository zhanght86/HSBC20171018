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
import com.sinosoft.lis.db.LOBonusAssignErrLogDB;

/*
 * <p>ClassName: LOBonusAssignErrLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 分红管理
 */
public class LOBonusAssignErrLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOBonusAssignErrLogSchema.class);

	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 保单号 */
	private String PolNo;
	/** 领取类型 */
	private String GetMode;
	/** 错误原因 */
	private String errMsg;
	/** 入机日期 */
	private Date makedate;
	/** 入机时间 */
	private String maketime;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 个人合同号码 */
	private String ContNo;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOBonusAssignErrLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "SerialNo";
		pk[1] = "PolNo";
		pk[2] = "GrpContNo";
		pk[3] = "ContNo";

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
		LOBonusAssignErrLogSchema cloned = (LOBonusAssignErrLogSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getGetMode()
	{
		return GetMode;
	}
	public void setGetMode(String aGetMode)
	{
		GetMode = aGetMode;
	}
	/**
	* 描述该险种的每个保单的红利系数。<p>
	* 对于现在的情况，需要描述子计算编码，分别计算分开的值。
	*/
	public String geterrMsg()
	{
		return errMsg;
	}
	public void seterrMsg(String aerrMsg)
	{
		errMsg = aerrMsg;
	}
	public String getmakedate()
	{
		if( makedate != null )
			return fDate.getString(makedate);
		else
			return null;
	}
	public void setmakedate(Date amakedate)
	{
		makedate = amakedate;
	}
	public void setmakedate(String amakedate)
	{
		if (amakedate != null && !amakedate.equals("") )
		{
			makedate = fDate.getDate( amakedate );
		}
		else
			makedate = null;
	}

	public String getmaketime()
	{
		return maketime;
	}
	public void setmaketime(String amaketime)
	{
		maketime = amaketime;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}

	/**
	* 使用另外一个 LOBonusAssignErrLogSchema 对象给 Schema 赋值
	* @param: aLOBonusAssignErrLogSchema LOBonusAssignErrLogSchema
	**/
	public void setSchema(LOBonusAssignErrLogSchema aLOBonusAssignErrLogSchema)
	{
		this.SerialNo = aLOBonusAssignErrLogSchema.getSerialNo();
		this.PolNo = aLOBonusAssignErrLogSchema.getPolNo();
		this.GetMode = aLOBonusAssignErrLogSchema.getGetMode();
		this.errMsg = aLOBonusAssignErrLogSchema.geterrMsg();
		this.makedate = fDate.getDate( aLOBonusAssignErrLogSchema.getmakedate());
		this.maketime = aLOBonusAssignErrLogSchema.getmaketime();
		this.GrpContNo = aLOBonusAssignErrLogSchema.getGrpContNo();
		this.ContNo = aLOBonusAssignErrLogSchema.getContNo();
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("GetMode") == null )
				this.GetMode = null;
			else
				this.GetMode = rs.getString("GetMode").trim();

			if( rs.getString("errMsg") == null )
				this.errMsg = null;
			else
				this.errMsg = rs.getString("errMsg").trim();

			this.makedate = rs.getDate("makedate");
			if( rs.getString("maketime") == null )
				this.maketime = null;
			else
				this.maketime = rs.getString("maketime").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOBonusAssignErrLog表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBonusAssignErrLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOBonusAssignErrLogSchema getSchema()
	{
		LOBonusAssignErrLogSchema aLOBonusAssignErrLogSchema = new LOBonusAssignErrLogSchema();
		aLOBonusAssignErrLogSchema.setSchema(this);
		return aLOBonusAssignErrLogSchema;
	}

	public LOBonusAssignErrLogDB getDB()
	{
		LOBonusAssignErrLogDB aDBOper = new LOBonusAssignErrLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBonusAssignErrLog描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(errMsg)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( makedate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(maketime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBonusAssignErrLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			errMsg = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			makedate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			maketime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBonusAssignErrLogSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("GetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMode));
		}
		if (FCode.equalsIgnoreCase("errMsg"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(errMsg));
		}
		if (FCode.equalsIgnoreCase("makedate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getmakedate()));
		}
		if (FCode.equalsIgnoreCase("maketime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(maketime));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GetMode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(errMsg);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getmakedate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(maketime);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("GetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetMode = FValue.trim();
			}
			else
				GetMode = null;
		}
		if (FCode.equalsIgnoreCase("errMsg"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				errMsg = FValue.trim();
			}
			else
				errMsg = null;
		}
		if (FCode.equalsIgnoreCase("makedate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				makedate = fDate.getDate( FValue );
			}
			else
				makedate = null;
		}
		if (FCode.equalsIgnoreCase("maketime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				maketime = FValue.trim();
			}
			else
				maketime = null;
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LOBonusAssignErrLogSchema other = (LOBonusAssignErrLogSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& PolNo.equals(other.getPolNo())
			&& GetMode.equals(other.getGetMode())
			&& errMsg.equals(other.geterrMsg())
			&& fDate.getString(makedate).equals(other.getmakedate())
			&& maketime.equals(other.getmaketime())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo());
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("PolNo") ) {
			return 1;
		}
		if( strFieldName.equals("GetMode") ) {
			return 2;
		}
		if( strFieldName.equals("errMsg") ) {
			return 3;
		}
		if( strFieldName.equals("makedate") ) {
			return 4;
		}
		if( strFieldName.equals("maketime") ) {
			return 5;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 6;
		}
		if( strFieldName.equals("ContNo") ) {
			return 7;
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "PolNo";
				break;
			case 2:
				strFieldName = "GetMode";
				break;
			case 3:
				strFieldName = "errMsg";
				break;
			case 4:
				strFieldName = "makedate";
				break;
			case 5:
				strFieldName = "maketime";
				break;
			case 6:
				strFieldName = "GrpContNo";
				break;
			case 7:
				strFieldName = "ContNo";
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("errMsg") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("makedate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("maketime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
