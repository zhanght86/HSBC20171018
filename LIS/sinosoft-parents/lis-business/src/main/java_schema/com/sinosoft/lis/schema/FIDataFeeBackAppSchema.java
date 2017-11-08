/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.lis.db.FIDataFeeBackAppDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIDataFeeBackAppSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIDataFeeBackAppSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIDataFeeBackAppSchema.class);

	// @Field
	/** 红冲申请号码 */
	private String AppNo;
	/** 凭证类型编号 */
	private String CertificateID;
	/** 业务类型编号 */
	private String BusinessCode;
	/** 明细索引代码 */
	private String DetailIndexID;
	/** 明细索引名称 */
	private String DetailIndexName;
	/** 业务号码 */
	private String BusinessNo;
	/** 红冲原因类型 */
	private String ReasonType;
	/** 细节描述 */
	private String DetailReMark;
	/** 申请日期 */
	private Date AppDate;
	/** 申请人 */
	private String Applicant;
	/** 申请状态 */
	private String AppState;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIDataFeeBackAppSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "AppNo";

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
                FIDataFeeBackAppSchema cloned = (FIDataFeeBackAppSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAppNo()
	{
		return AppNo;
	}
	public void setAppNo(String aAppNo)
	{
		AppNo = aAppNo;
	}
	public String getCertificateID()
	{
		return CertificateID;
	}
	public void setCertificateID(String aCertificateID)
	{
		CertificateID = aCertificateID;
	}
	public String getBusinessCode()
	{
		return BusinessCode;
	}
	public void setBusinessCode(String aBusinessCode)
	{
		BusinessCode = aBusinessCode;
	}
	public String getDetailIndexID()
	{
		return DetailIndexID;
	}
	public void setDetailIndexID(String aDetailIndexID)
	{
		DetailIndexID = aDetailIndexID;
	}
	public String getDetailIndexName()
	{
		return DetailIndexName;
	}
	public void setDetailIndexName(String aDetailIndexName)
	{
		DetailIndexName = aDetailIndexName;
	}
	public String getBusinessNo()
	{
		return BusinessNo;
	}
	public void setBusinessNo(String aBusinessNo)
	{
		BusinessNo = aBusinessNo;
	}
	public String getReasonType()
	{
		return ReasonType;
	}
	public void setReasonType(String aReasonType)
	{
		ReasonType = aReasonType;
	}
	public String getDetailReMark()
	{
		return DetailReMark;
	}
	public void setDetailReMark(String aDetailReMark)
	{
		DetailReMark = aDetailReMark;
	}
	public String getAppDate()
	{
		if( AppDate != null )
			return fDate.getString(AppDate);
		else
			return null;
	}
	public void setAppDate(Date aAppDate)
	{
		AppDate = aAppDate;
	}
	public void setAppDate(String aAppDate)
	{
		if (aAppDate != null && !aAppDate.equals("") )
		{
			AppDate = fDate.getDate( aAppDate );
		}
		else
			AppDate = null;
	}

	public String getApplicant()
	{
		return Applicant;
	}
	public void setApplicant(String aApplicant)
	{
		Applicant = aApplicant;
	}
	public String getAppState()
	{
		return AppState;
	}
	public void setAppState(String aAppState)
	{
		AppState = aAppState;
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
	* 使用另外一个 FIDataFeeBackAppSchema 对象给 Schema 赋值
	* @param: aFIDataFeeBackAppSchema FIDataFeeBackAppSchema
	**/
	public void setSchema(FIDataFeeBackAppSchema aFIDataFeeBackAppSchema)
	{
		this.AppNo = aFIDataFeeBackAppSchema.getAppNo();
		this.CertificateID = aFIDataFeeBackAppSchema.getCertificateID();
		this.BusinessCode = aFIDataFeeBackAppSchema.getBusinessCode();
		this.DetailIndexID = aFIDataFeeBackAppSchema.getDetailIndexID();
		this.DetailIndexName = aFIDataFeeBackAppSchema.getDetailIndexName();
		this.BusinessNo = aFIDataFeeBackAppSchema.getBusinessNo();
		this.ReasonType = aFIDataFeeBackAppSchema.getReasonType();
		this.DetailReMark = aFIDataFeeBackAppSchema.getDetailReMark();
		this.AppDate = fDate.getDate( aFIDataFeeBackAppSchema.getAppDate());
		this.Applicant = aFIDataFeeBackAppSchema.getApplicant();
		this.AppState = aFIDataFeeBackAppSchema.getAppState();
		this.MakeDate = fDate.getDate( aFIDataFeeBackAppSchema.getMakeDate());
		this.MakeTime = aFIDataFeeBackAppSchema.getMakeTime();
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
			if( rs.getString("AppNo") == null )
				this.AppNo = null;
			else
				this.AppNo = rs.getString("AppNo").trim();

			if( rs.getString("CertificateID") == null )
				this.CertificateID = null;
			else
				this.CertificateID = rs.getString("CertificateID").trim();

			if( rs.getString("BusinessCode") == null )
				this.BusinessCode = null;
			else
				this.BusinessCode = rs.getString("BusinessCode").trim();

			if( rs.getString("DetailIndexID") == null )
				this.DetailIndexID = null;
			else
				this.DetailIndexID = rs.getString("DetailIndexID").trim();

			if( rs.getString("DetailIndexName") == null )
				this.DetailIndexName = null;
			else
				this.DetailIndexName = rs.getString("DetailIndexName").trim();

			if( rs.getString("BusinessNo") == null )
				this.BusinessNo = null;
			else
				this.BusinessNo = rs.getString("BusinessNo").trim();

			if( rs.getString("ReasonType") == null )
				this.ReasonType = null;
			else
				this.ReasonType = rs.getString("ReasonType").trim();

			if( rs.getString("DetailReMark") == null )
				this.DetailReMark = null;
			else
				this.DetailReMark = rs.getString("DetailReMark").trim();

			this.AppDate = rs.getDate("AppDate");
			if( rs.getString("Applicant") == null )
				this.Applicant = null;
			else
				this.Applicant = rs.getString("Applicant").trim();

			if( rs.getString("AppState") == null )
				this.AppState = null;
			else
				this.AppState = rs.getString("AppState").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIDataFeeBackApp表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataFeeBackAppSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIDataFeeBackAppSchema getSchema()
	{
		FIDataFeeBackAppSchema aFIDataFeeBackAppSchema = new FIDataFeeBackAppSchema();
		aFIDataFeeBackAppSchema.setSchema(this);
		return aFIDataFeeBackAppSchema;
	}

	public FIDataFeeBackAppDB getDB()
	{
		FIDataFeeBackAppDB aDBOper = new FIDataFeeBackAppDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataFeeBackApp描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(AppNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CertificateID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DetailIndexID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DetailIndexName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ReasonType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DetailReMark)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( AppDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Applicant)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AppState)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataFeeBackApp>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AppNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CertificateID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BusinessCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DetailIndexID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DetailIndexName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BusinessNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ReasonType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DetailReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AppDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			Applicant = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AppState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataFeeBackAppSchema";
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
		if (FCode.equalsIgnoreCase("AppNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppNo));
		}
		if (FCode.equalsIgnoreCase("CertificateID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertificateID));
		}
		if (FCode.equalsIgnoreCase("BusinessCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessCode));
		}
		if (FCode.equalsIgnoreCase("DetailIndexID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DetailIndexID));
		}
		if (FCode.equalsIgnoreCase("DetailIndexName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DetailIndexName));
		}
		if (FCode.equalsIgnoreCase("BusinessNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessNo));
		}
		if (FCode.equalsIgnoreCase("ReasonType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReasonType));
		}
		if (FCode.equalsIgnoreCase("DetailReMark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DetailReMark));
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
		}
		if (FCode.equalsIgnoreCase("Applicant"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Applicant));
		}
		if (FCode.equalsIgnoreCase("AppState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppState));
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
				strFieldValue = StrTool.GBKToUnicode(AppNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CertificateID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BusinessCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DetailIndexID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DetailIndexName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BusinessNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ReasonType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DetailReMark);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Applicant);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AppState);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 12:
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

		if (FCode.equalsIgnoreCase("AppNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppNo = FValue.trim();
			}
			else
				AppNo = null;
		}
		if (FCode.equalsIgnoreCase("CertificateID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertificateID = FValue.trim();
			}
			else
				CertificateID = null;
		}
		if (FCode.equalsIgnoreCase("BusinessCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessCode = FValue.trim();
			}
			else
				BusinessCode = null;
		}
		if (FCode.equalsIgnoreCase("DetailIndexID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DetailIndexID = FValue.trim();
			}
			else
				DetailIndexID = null;
		}
		if (FCode.equalsIgnoreCase("DetailIndexName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DetailIndexName = FValue.trim();
			}
			else
				DetailIndexName = null;
		}
		if (FCode.equalsIgnoreCase("BusinessNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessNo = FValue.trim();
			}
			else
				BusinessNo = null;
		}
		if (FCode.equalsIgnoreCase("ReasonType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReasonType = FValue.trim();
			}
			else
				ReasonType = null;
		}
		if (FCode.equalsIgnoreCase("DetailReMark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DetailReMark = FValue.trim();
			}
			else
				DetailReMark = null;
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppDate = fDate.getDate( FValue );
			}
			else
				AppDate = null;
		}
		if (FCode.equalsIgnoreCase("Applicant"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Applicant = FValue.trim();
			}
			else
				Applicant = null;
		}
		if (FCode.equalsIgnoreCase("AppState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppState = FValue.trim();
			}
			else
				AppState = null;
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
		FIDataFeeBackAppSchema other = (FIDataFeeBackAppSchema)otherObject;
		return
			AppNo.equals(other.getAppNo())
			&& CertificateID.equals(other.getCertificateID())
			&& BusinessCode.equals(other.getBusinessCode())
			&& DetailIndexID.equals(other.getDetailIndexID())
			&& DetailIndexName.equals(other.getDetailIndexName())
			&& BusinessNo.equals(other.getBusinessNo())
			&& ReasonType.equals(other.getReasonType())
			&& DetailReMark.equals(other.getDetailReMark())
			&& fDate.getString(AppDate).equals(other.getAppDate())
			&& Applicant.equals(other.getApplicant())
			&& AppState.equals(other.getAppState())
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
		if( strFieldName.equals("AppNo") ) {
			return 0;
		}
		if( strFieldName.equals("CertificateID") ) {
			return 1;
		}
		if( strFieldName.equals("BusinessCode") ) {
			return 2;
		}
		if( strFieldName.equals("DetailIndexID") ) {
			return 3;
		}
		if( strFieldName.equals("DetailIndexName") ) {
			return 4;
		}
		if( strFieldName.equals("BusinessNo") ) {
			return 5;
		}
		if( strFieldName.equals("ReasonType") ) {
			return 6;
		}
		if( strFieldName.equals("DetailReMark") ) {
			return 7;
		}
		if( strFieldName.equals("AppDate") ) {
			return 8;
		}
		if( strFieldName.equals("Applicant") ) {
			return 9;
		}
		if( strFieldName.equals("AppState") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				strFieldName = "AppNo";
				break;
			case 1:
				strFieldName = "CertificateID";
				break;
			case 2:
				strFieldName = "BusinessCode";
				break;
			case 3:
				strFieldName = "DetailIndexID";
				break;
			case 4:
				strFieldName = "DetailIndexName";
				break;
			case 5:
				strFieldName = "BusinessNo";
				break;
			case 6:
				strFieldName = "ReasonType";
				break;
			case 7:
				strFieldName = "DetailReMark";
				break;
			case 8:
				strFieldName = "AppDate";
				break;
			case 9:
				strFieldName = "Applicant";
				break;
			case 10:
				strFieldName = "AppState";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
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
		if( strFieldName.equals("AppNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertificateID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DetailIndexID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DetailIndexName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReasonType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DetailReMark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Applicant") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppState") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
