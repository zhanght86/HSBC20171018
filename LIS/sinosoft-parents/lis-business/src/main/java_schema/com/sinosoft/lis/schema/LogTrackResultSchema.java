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
import com.sinosoft.lis.db.LogTrackResultDB;

/*
 * <p>ClassName: LogTrackResultSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 日志
 */
public class LogTrackResultSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LogTrackResultSchema.class);

	// @Field
	/** 日志号 */
	private String LogNo;
	/** 日志主题id */
	private String SubjectID;
	/** 日志监控点id */
	private String ItemID;
	/** 任务批次号码 */
	private int SerialNo;
	/** 控制点关键号码 */
	private String KeyNo;
	/** 控制点日志信息 */
	private String ItemDes;
	/** 日志辅助信息 */
	private String Remark;
	/** 日志产生日期 */
	private Date MakeDate;
	/** 日志产生时间 */
	private String MakeTime;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LogTrackResultSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "LogNo";

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
		LogTrackResultSchema cloned = (LogTrackResultSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getLogNo()
	{
		return LogNo;
	}
	public void setLogNo(String aLogNo)
	{
		if(aLogNo!=null && aLogNo.length()>30)
			throw new IllegalArgumentException("日志号LogNo值"+aLogNo+"的长度"+aLogNo.length()+"大于最大值30");
		LogNo = aLogNo;
	}
	public String getSubjectID()
	{
		return SubjectID;
	}
	public void setSubjectID(String aSubjectID)
	{
		if(aSubjectID!=null && aSubjectID.length()>20)
			throw new IllegalArgumentException("日志主题idSubjectID值"+aSubjectID+"的长度"+aSubjectID.length()+"大于最大值20");
		SubjectID = aSubjectID;
	}
	public String getItemID()
	{
		return ItemID;
	}
	public void setItemID(String aItemID)
	{
		if(aItemID!=null && aItemID.length()>20)
			throw new IllegalArgumentException("日志监控点idItemID值"+aItemID+"的长度"+aItemID.length()+"大于最大值20");
		ItemID = aItemID;
	}
	/**
	* 对应ldtaskrunlog中的serialno
	*/
	public int getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(int aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if (aSerialNo != null && !aSerialNo.equals(""))
		{
			Integer tInteger = new Integer(aSerialNo);
			int i = tInteger.intValue();
			SerialNo = i;
		}
	}

	public String getKeyNo()
	{
		return KeyNo;
	}
	public void setKeyNo(String aKeyNo)
	{
		if(aKeyNo!=null && aKeyNo.length()>20)
			throw new IllegalArgumentException("控制点关键号码KeyNo值"+aKeyNo+"的长度"+aKeyNo.length()+"大于最大值20");
		KeyNo = aKeyNo;
	}
	/**
	* NB,POS,CLM等
	*/
	public String getItemDes()
	{
		return ItemDes;
	}
	public void setItemDes(String aItemDes)
	{
		if(aItemDes!=null && aItemDes.length()>100)
			throw new IllegalArgumentException("控制点日志信息ItemDes值"+aItemDes+"的长度"+aItemDes.length()+"大于最大值100");
		ItemDes = aItemDes;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>100)
			throw new IllegalArgumentException("日志辅助信息Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值100");
		Remark = aRemark;
	}
	/**
	* NB,POS,CLM等
	*/
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
			throw new IllegalArgumentException("日志产生时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
		MakeTime = aMakeTime;
	}

	/**
	* 使用另外一个 LogTrackResultSchema 对象给 Schema 赋值
	* @param: aLogTrackResultSchema LogTrackResultSchema
	**/
	public void setSchema(LogTrackResultSchema aLogTrackResultSchema)
	{
		this.LogNo = aLogTrackResultSchema.getLogNo();
		this.SubjectID = aLogTrackResultSchema.getSubjectID();
		this.ItemID = aLogTrackResultSchema.getItemID();
		this.SerialNo = aLogTrackResultSchema.getSerialNo();
		this.KeyNo = aLogTrackResultSchema.getKeyNo();
		this.ItemDes = aLogTrackResultSchema.getItemDes();
		this.Remark = aLogTrackResultSchema.getRemark();
		this.MakeDate = fDate.getDate( aLogTrackResultSchema.getMakeDate());
		this.MakeTime = aLogTrackResultSchema.getMakeTime();
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
			if( rs.getString("LogNo") == null )
				this.LogNo = null;
			else
				this.LogNo = rs.getString("LogNo").trim();

			if( rs.getString("SubjectID") == null )
				this.SubjectID = null;
			else
				this.SubjectID = rs.getString("SubjectID").trim();

			if( rs.getString("ItemID") == null )
				this.ItemID = null;
			else
				this.ItemID = rs.getString("ItemID").trim();

			this.SerialNo = rs.getInt("SerialNo");
			if( rs.getString("KeyNo") == null )
				this.KeyNo = null;
			else
				this.KeyNo = rs.getString("KeyNo").trim();

			if( rs.getString("ItemDes") == null )
				this.ItemDes = null;
			else
				this.ItemDes = rs.getString("ItemDes").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LogTrackResult表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogTrackResultSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LogTrackResultSchema getSchema()
	{
		LogTrackResultSchema aLogTrackResultSchema = new LogTrackResultSchema();
		aLogTrackResultSchema.setSchema(this);
		return aLogTrackResultSchema;
	}

	public LogTrackResultDB getDB()
	{
		LogTrackResultDB aDBOper = new LogTrackResultDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLogTrackResult描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(LogNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubjectID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ItemID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SerialNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KeyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ItemDes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLogTrackResult>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			LogNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SubjectID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ItemID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			KeyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ItemDes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogTrackResultSchema";
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
		if (FCode.equalsIgnoreCase("LogNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogNo));
		}
		if (FCode.equalsIgnoreCase("SubjectID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubjectID));
		}
		if (FCode.equalsIgnoreCase("ItemID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemID));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("KeyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KeyNo));
		}
		if (FCode.equalsIgnoreCase("ItemDes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemDes));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(LogNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SubjectID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ItemID);
				break;
			case 3:
				strFieldValue = String.valueOf(SerialNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(KeyNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ItemDes);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 8:
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

		if (FCode.equalsIgnoreCase("LogNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogNo = FValue.trim();
			}
			else
				LogNo = null;
		}
		if (FCode.equalsIgnoreCase("SubjectID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubjectID = FValue.trim();
			}
			else
				SubjectID = null;
		}
		if (FCode.equalsIgnoreCase("ItemID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemID = FValue.trim();
			}
			else
				ItemID = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SerialNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("KeyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KeyNo = FValue.trim();
			}
			else
				KeyNo = null;
		}
		if (FCode.equalsIgnoreCase("ItemDes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemDes = FValue.trim();
			}
			else
				ItemDes = null;
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
		LogTrackResultSchema other = (LogTrackResultSchema)otherObject;
		return
			LogNo.equals(other.getLogNo())
			&& SubjectID.equals(other.getSubjectID())
			&& ItemID.equals(other.getItemID())
			&& SerialNo == other.getSerialNo()
			&& KeyNo.equals(other.getKeyNo())
			&& ItemDes.equals(other.getItemDes())
			&& Remark.equals(other.getRemark())
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
		if( strFieldName.equals("LogNo") ) {
			return 0;
		}
		if( strFieldName.equals("SubjectID") ) {
			return 1;
		}
		if( strFieldName.equals("ItemID") ) {
			return 2;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 3;
		}
		if( strFieldName.equals("KeyNo") ) {
			return 4;
		}
		if( strFieldName.equals("ItemDes") ) {
			return 5;
		}
		if( strFieldName.equals("Remark") ) {
			return 6;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 7;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 8;
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
				strFieldName = "LogNo";
				break;
			case 1:
				strFieldName = "SubjectID";
				break;
			case 2:
				strFieldName = "ItemID";
				break;
			case 3:
				strFieldName = "SerialNo";
				break;
			case 4:
				strFieldName = "KeyNo";
				break;
			case 5:
				strFieldName = "ItemDes";
				break;
			case 6:
				strFieldName = "Remark";
				break;
			case 7:
				strFieldName = "MakeDate";
				break;
			case 8:
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
		if( strFieldName.equals("LogNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubjectID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("KeyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemDes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
