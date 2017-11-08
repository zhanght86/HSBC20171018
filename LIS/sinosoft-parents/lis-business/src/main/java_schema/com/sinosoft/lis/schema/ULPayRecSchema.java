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
import com.sinosoft.lis.db.ULPayRecDB;

/*
 * <p>ClassName: ULPayRecSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class ULPayRecSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ULPayRecSchema.class);

	// @Field
	/** 机构代码 */
	private String ManageCom;
	/** 合同号 */
	private String ContNo;
	/** 保单号 */
	private String PolNo;
	/** 险种代码 */
	private String RiskCode;
	/** 个团险标志 */
	private String GP_Type;
	/** 销售渠道 */
	private String SaleChnl;
	/** 账户标志 */
	private int Av_Id;
	/** 发生金额 */
	private double Money;
	/** 发生日期 */
	private Date OccurDate;
	/** 增减标志 */
	private int PolStatus;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ULPayRecSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[0];

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
		ULPayRecSchema cloned = (ULPayRecSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getGP_Type()
	{
		return GP_Type;
	}
	public void setGP_Type(String aGP_Type)
	{
		GP_Type = aGP_Type;
	}
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
	}
	public int getAv_Id()
	{
		return Av_Id;
	}
	public void setAv_Id(int aAv_Id)
	{
		Av_Id = aAv_Id;
	}
	public void setAv_Id(String aAv_Id)
	{
		if (aAv_Id != null && !aAv_Id.equals(""))
		{
			Integer tInteger = new Integer(aAv_Id);
			int i = tInteger.intValue();
			Av_Id = i;
		}
	}

	public double getMoney()
	{
		return Money;
	}
	public void setMoney(double aMoney)
	{
		Money = aMoney;
	}
	public void setMoney(String aMoney)
	{
		if (aMoney != null && !aMoney.equals(""))
		{
			Double tDouble = new Double(aMoney);
			double d = tDouble.doubleValue();
			Money = d;
		}
	}

	public String getOccurDate()
	{
		if( OccurDate != null )
			return fDate.getString(OccurDate);
		else
			return null;
	}
	public void setOccurDate(Date aOccurDate)
	{
		OccurDate = aOccurDate;
	}
	public void setOccurDate(String aOccurDate)
	{
		if (aOccurDate != null && !aOccurDate.equals("") )
		{
			OccurDate = fDate.getDate( aOccurDate );
		}
		else
			OccurDate = null;
	}

	public int getPolStatus()
	{
		return PolStatus;
	}
	public void setPolStatus(int aPolStatus)
	{
		PolStatus = aPolStatus;
	}
	public void setPolStatus(String aPolStatus)
	{
		if (aPolStatus != null && !aPolStatus.equals(""))
		{
			Integer tInteger = new Integer(aPolStatus);
			int i = tInteger.intValue();
			PolStatus = i;
		}
	}


	/**
	* 使用另外一个 ULPayRecSchema 对象给 Schema 赋值
	* @param: aULPayRecSchema ULPayRecSchema
	**/
	public void setSchema(ULPayRecSchema aULPayRecSchema)
	{
		this.ManageCom = aULPayRecSchema.getManageCom();
		this.ContNo = aULPayRecSchema.getContNo();
		this.PolNo = aULPayRecSchema.getPolNo();
		this.RiskCode = aULPayRecSchema.getRiskCode();
		this.GP_Type = aULPayRecSchema.getGP_Type();
		this.SaleChnl = aULPayRecSchema.getSaleChnl();
		this.Av_Id = aULPayRecSchema.getAv_Id();
		this.Money = aULPayRecSchema.getMoney();
		this.OccurDate = fDate.getDate( aULPayRecSchema.getOccurDate());
		this.PolStatus = aULPayRecSchema.getPolStatus();
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
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("GP_Type") == null )
				this.GP_Type = null;
			else
				this.GP_Type = rs.getString("GP_Type").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			this.Av_Id = rs.getInt("Av_Id");
			this.Money = rs.getDouble("Money");
			this.OccurDate = rs.getDate("OccurDate");
			this.PolStatus = rs.getInt("PolStatus");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的ULPayRec表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ULPayRecSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ULPayRecSchema getSchema()
	{
		ULPayRecSchema aULPayRecSchema = new ULPayRecSchema();
		aULPayRecSchema.setSchema(this);
		return aULPayRecSchema;
	}

	public ULPayRecDB getDB()
	{
		ULPayRecDB aDBOper = new ULPayRecDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpULPayRec描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GP_Type)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Av_Id));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Money));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OccurDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PolStatus));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpULPayRec>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GP_Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Av_Id= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			Money = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			OccurDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			PolStatus= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ULPayRecSchema";
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("GP_Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GP_Type));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("Av_Id"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Av_Id));
		}
		if (FCode.equalsIgnoreCase("Money"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Money));
		}
		if (FCode.equalsIgnoreCase("OccurDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOccurDate()));
		}
		if (FCode.equalsIgnoreCase("PolStatus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolStatus));
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
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GP_Type);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 6:
				strFieldValue = String.valueOf(Av_Id);
				break;
			case 7:
				strFieldValue = String.valueOf(Money);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOccurDate()));
				break;
			case 9:
				strFieldValue = String.valueOf(PolStatus);
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

		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("GP_Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GP_Type = FValue.trim();
			}
			else
				GP_Type = null;
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
		}
		if (FCode.equalsIgnoreCase("Av_Id"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Av_Id = i;
			}
		}
		if (FCode.equalsIgnoreCase("Money"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Money = d;
			}
		}
		if (FCode.equalsIgnoreCase("OccurDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OccurDate = fDate.getDate( FValue );
			}
			else
				OccurDate = null;
		}
		if (FCode.equalsIgnoreCase("PolStatus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PolStatus = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ULPayRecSchema other = (ULPayRecSchema)otherObject;
		return
			ManageCom.equals(other.getManageCom())
			&& ContNo.equals(other.getContNo())
			&& PolNo.equals(other.getPolNo())
			&& RiskCode.equals(other.getRiskCode())
			&& GP_Type.equals(other.getGP_Type())
			&& SaleChnl.equals(other.getSaleChnl())
			&& Av_Id == other.getAv_Id()
			&& Money == other.getMoney()
			&& fDate.getString(OccurDate).equals(other.getOccurDate())
			&& PolStatus == other.getPolStatus();
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
		if( strFieldName.equals("ManageCom") ) {
			return 0;
		}
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("PolNo") ) {
			return 2;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 3;
		}
		if( strFieldName.equals("GP_Type") ) {
			return 4;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 5;
		}
		if( strFieldName.equals("Av_Id") ) {
			return 6;
		}
		if( strFieldName.equals("Money") ) {
			return 7;
		}
		if( strFieldName.equals("OccurDate") ) {
			return 8;
		}
		if( strFieldName.equals("PolStatus") ) {
			return 9;
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
				strFieldName = "ManageCom";
				break;
			case 1:
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "PolNo";
				break;
			case 3:
				strFieldName = "RiskCode";
				break;
			case 4:
				strFieldName = "GP_Type";
				break;
			case 5:
				strFieldName = "SaleChnl";
				break;
			case 6:
				strFieldName = "Av_Id";
				break;
			case 7:
				strFieldName = "Money";
				break;
			case 8:
				strFieldName = "OccurDate";
				break;
			case 9:
				strFieldName = "PolStatus";
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
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GP_Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Av_Id") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Money") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OccurDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PolStatus") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
