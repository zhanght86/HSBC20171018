/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FIBusinessRuleDealErrLogDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIBusinessRuleDealErrLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIBusinessRuleDealErrLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIBusinessRuleDealErrLogSchema.class);

	// @Field
	/** 日志错误流水号码 */
	private String ErrSerialNo;
	/** 关联的流水号码 */
	private String Aserialno;
	/** 校验规则 */
	private String RuleID;
	/** 错误信息 */
	private String ErrInfo;
	/** 处理状态 */
	private String DealState;
	/** 凭证类型 */
	private String CertificateID;
	/** 业务号码 */
	private String businessno;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIBusinessRuleDealErrLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ErrSerialNo";

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
                FIBusinessRuleDealErrLogSchema cloned = (FIBusinessRuleDealErrLogSchema)super.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getErrSerialNo()
	{
		return ErrSerialNo;
	}
	public void setErrSerialNo(String aErrSerialNo)
	{
		ErrSerialNo = aErrSerialNo;
	}
	public String getAserialno()
	{
		return Aserialno;
	}
	public void setAserialno(String aAserialno)
	{
		Aserialno = aAserialno;
	}
	public String getRuleID()
	{
		return RuleID;
	}
	public void setRuleID(String aRuleID)
	{
		RuleID = aRuleID;
	}
	public String getErrInfo()
	{
		return ErrInfo;
	}
	public void setErrInfo(String aErrInfo)
	{
		ErrInfo = aErrInfo;
	}
	public String getDealState()
	{
		return DealState;
	}
	public void setDealState(String aDealState)
	{
		DealState = aDealState;
	}
	public String getCertificateID()
	{
		return CertificateID;
	}
	public void setCertificateID(String aCertificateID)
	{
		CertificateID = aCertificateID;
	}
	public String getbusinessno()
	{
		return businessno;
	}
	public void setbusinessno(String abusinessno)
	{
		businessno = abusinessno;
	}

	/**
	* 使用另外一个 FIBusinessRuleDealErrLogSchema 对象给 Schema 赋值
	* @param: aFIBusinessRuleDealErrLogSchema FIBusinessRuleDealErrLogSchema
	**/
	public void setSchema(FIBusinessRuleDealErrLogSchema aFIBusinessRuleDealErrLogSchema)
	{
		this.ErrSerialNo = aFIBusinessRuleDealErrLogSchema.getErrSerialNo();
		this.Aserialno = aFIBusinessRuleDealErrLogSchema.getAserialno();
		this.RuleID = aFIBusinessRuleDealErrLogSchema.getRuleID();
		this.ErrInfo = aFIBusinessRuleDealErrLogSchema.getErrInfo();
		this.DealState = aFIBusinessRuleDealErrLogSchema.getDealState();
		this.CertificateID = aFIBusinessRuleDealErrLogSchema.getCertificateID();
		this.businessno = aFIBusinessRuleDealErrLogSchema.getbusinessno();
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
			if( rs.getString("ErrSerialNo") == null )
				this.ErrSerialNo = null;
			else
				this.ErrSerialNo = rs.getString("ErrSerialNo").trim();

			if( rs.getString("Aserialno") == null )
				this.Aserialno = null;
			else
				this.Aserialno = rs.getString("Aserialno").trim();

			if( rs.getString("RuleID") == null )
				this.RuleID = null;
			else
				this.RuleID = rs.getString("RuleID").trim();

			if( rs.getString("ErrInfo") == null )
				this.ErrInfo = null;
			else
				this.ErrInfo = rs.getString("ErrInfo").trim();

			if( rs.getString("DealState") == null )
				this.DealState = null;
			else
				this.DealState = rs.getString("DealState").trim();

			if( rs.getString("CertificateID") == null )
				this.CertificateID = null;
			else
				this.CertificateID = rs.getString("CertificateID").trim();

			if( rs.getString("businessno") == null )
				this.businessno = null;
			else
				this.businessno = rs.getString("businessno").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIBusinessRuleDealErrLog表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIBusinessRuleDealErrLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIBusinessRuleDealErrLogSchema getSchema()
	{
		FIBusinessRuleDealErrLogSchema aFIBusinessRuleDealErrLogSchema = new FIBusinessRuleDealErrLogSchema();
		aFIBusinessRuleDealErrLogSchema.setSchema(this);
		return aFIBusinessRuleDealErrLogSchema;
	}

	public FIBusinessRuleDealErrLogDB getDB()
	{
		FIBusinessRuleDealErrLogDB aDBOper = new FIBusinessRuleDealErrLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIBusinessRuleDealErrLog描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(ErrSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Aserialno)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RuleID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ErrInfo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DealState)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CertificateID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(businessno));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIBusinessRuleDealErrLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ErrSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Aserialno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RuleID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ErrInfo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DealState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CertificateID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			businessno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIBusinessRuleDealErrLogSchema";
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
		if (FCode.equalsIgnoreCase("ErrSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrSerialNo));
		}
		if (FCode.equalsIgnoreCase("Aserialno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Aserialno));
		}
		if (FCode.equalsIgnoreCase("RuleID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleID));
		}
		if (FCode.equalsIgnoreCase("ErrInfo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrInfo));
		}
		if (FCode.equalsIgnoreCase("DealState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealState));
		}
		if (FCode.equalsIgnoreCase("CertificateID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertificateID));
		}
		if (FCode.equalsIgnoreCase("businessno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(businessno));
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
				strFieldValue = StrTool.GBKToUnicode(ErrSerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Aserialno);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RuleID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ErrInfo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DealState);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CertificateID);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(businessno);
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

		if (FCode.equalsIgnoreCase("ErrSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrSerialNo = FValue.trim();
			}
			else
				ErrSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("Aserialno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Aserialno = FValue.trim();
			}
			else
				Aserialno = null;
		}
		if (FCode.equalsIgnoreCase("RuleID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleID = FValue.trim();
			}
			else
				RuleID = null;
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
		if (FCode.equalsIgnoreCase("DealState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealState = FValue.trim();
			}
			else
				DealState = null;
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
		if (FCode.equalsIgnoreCase("businessno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				businessno = FValue.trim();
			}
			else
				businessno = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIBusinessRuleDealErrLogSchema other = (FIBusinessRuleDealErrLogSchema)otherObject;
		return
			ErrSerialNo.equals(other.getErrSerialNo())
			&& Aserialno.equals(other.getAserialno())
			&& RuleID.equals(other.getRuleID())
			&& ErrInfo.equals(other.getErrInfo())
			&& DealState.equals(other.getDealState())
			&& CertificateID.equals(other.getCertificateID())
			&& businessno.equals(other.getbusinessno());
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
		if( strFieldName.equals("ErrSerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("Aserialno") ) {
			return 1;
		}
		if( strFieldName.equals("RuleID") ) {
			return 2;
		}
		if( strFieldName.equals("ErrInfo") ) {
			return 3;
		}
		if( strFieldName.equals("DealState") ) {
			return 4;
		}
		if( strFieldName.equals("CertificateID") ) {
			return 5;
		}
		if( strFieldName.equals("businessno") ) {
			return 6;
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
				strFieldName = "ErrSerialNo";
				break;
			case 1:
				strFieldName = "Aserialno";
				break;
			case 2:
				strFieldName = "RuleID";
				break;
			case 3:
				strFieldName = "ErrInfo";
				break;
			case 4:
				strFieldName = "DealState";
				break;
			case 5:
				strFieldName = "CertificateID";
				break;
			case 6:
				strFieldName = "businessno";
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
		if( strFieldName.equals("ErrSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Aserialno") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrInfo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertificateID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("businessno") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
