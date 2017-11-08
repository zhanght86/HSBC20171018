/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.FIDataDealErrDB;

/**
 * <p>ClassName: FIDataDealErrSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIDataDealErrSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIDataDealErrSchema.class);

	// @Field
	/** 错误流水号 */
	private String EeeSerialNo;
	/** 出错处理阶段 */
	private String ErrStage;
	/** 错误类型 */
	private String ErrType;
	/** 凭证类型 */
	private String Certificateid;
	/** 数据批次号 */
	private String BatchNo;
	/** 数据流水号 */
	private String AFSerialNo;
	/** 业务日期 */
	private Date BussDate;
	/** 错误信息 */
	private String ErrInfo;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 错误处理状态 */
	private String ErrDealState;
	/** 错误处理说明 */
	private String DealDescription;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIDataDealErrSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "EeeSerialNo";

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
                FIDataDealErrSchema cloned = (FIDataDealErrSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEeeSerialNo()
	{
		return EeeSerialNo;
	}
	public void setEeeSerialNo(String aEeeSerialNo)
	{
		EeeSerialNo = aEeeSerialNo;
	}
	public String getErrStage()
	{
		return ErrStage;
	}
	public void setErrStage(String aErrStage)
	{
		ErrStage = aErrStage;
	}
	public String getErrType()
	{
		return ErrType;
	}
	public void setErrType(String aErrType)
	{
		ErrType = aErrType;
	}
	public String getCertificateid()
	{
		return Certificateid;
	}
	public void setCertificateid(String aCertificateid)
	{
		Certificateid = aCertificateid;
	}
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	public String getAFSerialNo()
	{
		return AFSerialNo;
	}
	public void setAFSerialNo(String aAFSerialNo)
	{
		AFSerialNo = aAFSerialNo;
	}
	public String getBussDate()
	{
		if( BussDate != null )
			return fDate.getString(BussDate);
		else
			return null;
	}
	public void setBussDate(Date aBussDate)
	{
		BussDate = aBussDate;
	}
	public void setBussDate(String aBussDate)
	{
		if (aBussDate != null && !aBussDate.equals("") )
		{
			BussDate = fDate.getDate( aBussDate );
		}
		else
			BussDate = null;
	}

	public String getErrInfo()
	{
		return ErrInfo;
	}
	public void setErrInfo(String aErrInfo)
	{
		ErrInfo = aErrInfo;
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
	public String getErrDealState()
	{
		return ErrDealState;
	}
	public void setErrDealState(String aErrDealState)
	{
		ErrDealState = aErrDealState;
	}
	public String getDealDescription()
	{
		return DealDescription;
	}
	public void setDealDescription(String aDealDescription)
	{
		DealDescription = aDealDescription;
	}

	/**
	* 使用另外一个 FIDataDealErrSchema 对象给 Schema 赋值
	* @param: aFIDataDealErrSchema FIDataDealErrSchema
	**/
	public void setSchema(FIDataDealErrSchema aFIDataDealErrSchema)
	{
		this.EeeSerialNo = aFIDataDealErrSchema.getEeeSerialNo();
		this.ErrStage = aFIDataDealErrSchema.getErrStage();
		this.ErrType = aFIDataDealErrSchema.getErrType();
		this.Certificateid = aFIDataDealErrSchema.getCertificateid();
		this.BatchNo = aFIDataDealErrSchema.getBatchNo();
		this.AFSerialNo = aFIDataDealErrSchema.getAFSerialNo();
		this.BussDate = fDate.getDate( aFIDataDealErrSchema.getBussDate());
		this.ErrInfo = aFIDataDealErrSchema.getErrInfo();
		this.MakeDate = fDate.getDate( aFIDataDealErrSchema.getMakeDate());
		this.MakeTime = aFIDataDealErrSchema.getMakeTime();
		this.ErrDealState = aFIDataDealErrSchema.getErrDealState();
		this.DealDescription = aFIDataDealErrSchema.getDealDescription();
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
			if( rs.getString("EeeSerialNo") == null )
				this.EeeSerialNo = null;
			else
				this.EeeSerialNo = rs.getString("EeeSerialNo").trim();

			if( rs.getString("ErrStage") == null )
				this.ErrStage = null;
			else
				this.ErrStage = rs.getString("ErrStage").trim();

			if( rs.getString("ErrType") == null )
				this.ErrType = null;
			else
				this.ErrType = rs.getString("ErrType").trim();

			if( rs.getString("Certificateid") == null )
				this.Certificateid = null;
			else
				this.Certificateid = rs.getString("Certificateid").trim();

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("AFSerialNo") == null )
				this.AFSerialNo = null;
			else
				this.AFSerialNo = rs.getString("AFSerialNo").trim();

			this.BussDate = rs.getDate("BussDate");
			if( rs.getString("ErrInfo") == null )
				this.ErrInfo = null;
			else
				this.ErrInfo = rs.getString("ErrInfo").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ErrDealState") == null )
				this.ErrDealState = null;
			else
				this.ErrDealState = rs.getString("ErrDealState").trim();

			if( rs.getString("DealDescription") == null )
				this.DealDescription = null;
			else
				this.DealDescription = rs.getString("DealDescription").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIDataDealErr表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataDealErrSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIDataDealErrSchema getSchema()
	{
		FIDataDealErrSchema aFIDataDealErrSchema = new FIDataDealErrSchema();
		aFIDataDealErrSchema.setSchema(this);
		return aFIDataDealErrSchema;
	}

	public FIDataDealErrDB getDB()
	{
		FIDataDealErrDB aDBOper = new FIDataDealErrDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataDealErr描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(EeeSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ErrStage)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ErrType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Certificateid)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AFSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( BussDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ErrInfo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ErrDealState)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DealDescription));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataDealErr>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EeeSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ErrStage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ErrType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Certificateid = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AFSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BussDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			ErrInfo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ErrDealState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			DealDescription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataDealErrSchema";
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
		if (FCode.equalsIgnoreCase("EeeSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EeeSerialNo));
		}
		if (FCode.equalsIgnoreCase("ErrStage"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrStage));
		}
		if (FCode.equalsIgnoreCase("ErrType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrType));
		}
		if (FCode.equalsIgnoreCase("Certificateid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Certificateid));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("AFSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AFSerialNo));
		}
		if (FCode.equalsIgnoreCase("BussDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBussDate()));
		}
		if (FCode.equalsIgnoreCase("ErrInfo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrInfo));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ErrDealState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrDealState));
		}
		if (FCode.equalsIgnoreCase("DealDescription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealDescription));
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
				strFieldValue = StrTool.GBKToUnicode(EeeSerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ErrStage);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ErrType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Certificateid);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AFSerialNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBussDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ErrInfo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ErrDealState);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(DealDescription);
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

		if (FCode.equalsIgnoreCase("EeeSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EeeSerialNo = FValue.trim();
			}
			else
				EeeSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("ErrStage"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrStage = FValue.trim();
			}
			else
				ErrStage = null;
		}
		if (FCode.equalsIgnoreCase("ErrType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrType = FValue.trim();
			}
			else
				ErrType = null;
		}
		if (FCode.equalsIgnoreCase("Certificateid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Certificateid = FValue.trim();
			}
			else
				Certificateid = null;
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("AFSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AFSerialNo = FValue.trim();
			}
			else
				AFSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("BussDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BussDate = fDate.getDate( FValue );
			}
			else
				BussDate = null;
		}
		if (FCode.equalsIgnoreCase("ErrInfo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrInfo = FValue.trim();
			}
			else
				ErrInfo = null;
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
		if (FCode.equalsIgnoreCase("ErrDealState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrDealState = FValue.trim();
			}
			else
				ErrDealState = null;
		}
		if (FCode.equalsIgnoreCase("DealDescription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealDescription = FValue.trim();
			}
			else
				DealDescription = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIDataDealErrSchema other = (FIDataDealErrSchema)otherObject;
		return
			EeeSerialNo.equals(other.getEeeSerialNo())
			&& ErrStage.equals(other.getErrStage())
			&& ErrType.equals(other.getErrType())
			&& Certificateid.equals(other.getCertificateid())
			&& BatchNo.equals(other.getBatchNo())
			&& AFSerialNo.equals(other.getAFSerialNo())
			&& fDate.getString(BussDate).equals(other.getBussDate())
			&& ErrInfo.equals(other.getErrInfo())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ErrDealState.equals(other.getErrDealState())
			&& DealDescription.equals(other.getDealDescription());
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
		if( strFieldName.equals("EeeSerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("ErrStage") ) {
			return 1;
		}
		if( strFieldName.equals("ErrType") ) {
			return 2;
		}
		if( strFieldName.equals("Certificateid") ) {
			return 3;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 4;
		}
		if( strFieldName.equals("AFSerialNo") ) {
			return 5;
		}
		if( strFieldName.equals("BussDate") ) {
			return 6;
		}
		if( strFieldName.equals("ErrInfo") ) {
			return 7;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 9;
		}
		if( strFieldName.equals("ErrDealState") ) {
			return 10;
		}
		if( strFieldName.equals("DealDescription") ) {
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
				strFieldName = "EeeSerialNo";
				break;
			case 1:
				strFieldName = "ErrStage";
				break;
			case 2:
				strFieldName = "ErrType";
				break;
			case 3:
				strFieldName = "Certificateid";
				break;
			case 4:
				strFieldName = "BatchNo";
				break;
			case 5:
				strFieldName = "AFSerialNo";
				break;
			case 6:
				strFieldName = "BussDate";
				break;
			case 7:
				strFieldName = "ErrInfo";
				break;
			case 8:
				strFieldName = "MakeDate";
				break;
			case 9:
				strFieldName = "MakeTime";
				break;
			case 10:
				strFieldName = "ErrDealState";
				break;
			case 11:
				strFieldName = "DealDescription";
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
		if( strFieldName.equals("EeeSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrStage") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Certificateid") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AFSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ErrInfo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrDealState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealDescription") ) {
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
				nFieldType = Schema.TYPE_STRING;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
