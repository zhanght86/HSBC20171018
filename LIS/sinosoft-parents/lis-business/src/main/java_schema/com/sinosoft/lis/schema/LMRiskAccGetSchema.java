

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
import com.sinosoft.lis.db.LMRiskAccGetDB;

/*
 * <p>ClassName: LMRiskAccGetSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRiskAccGetSchema implements Schema, Cloneable
{
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 默认比例 */
	private double DefaultRate;
	/** 是否需要录入 */
	private String NeedInput;
	/** 转出账户时的算法编码(现金) */
	private String CalCodeMoney;
	/** 处理方向 */
	private String DealDirection;
	/** 账户转出计算标志 */
	private String CalFlag;
	/** 账户产生位置 */
	private String AccCreatePos;
	/** 给付名称 */
	private String GetDutyName;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskAccGetSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "InsuAccNo";
		pk[2] = "GetDutyCode";

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
		LMRiskAccGetSchema cloned = (LMRiskAccGetSchema)super.clone();
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
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		InsuAccNo = aInsuAccNo;
	}
	/**
	* 对于现金领取的，该编码为000000，<p>
	* 对于生存金领取的，该代码为对应的生存金代码。
	*/
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		GetDutyCode = aGetDutyCode;
	}
	/**
	* 在录入界面中的默认显示比例。
	*/
	public double getDefaultRate()
	{
		return DefaultRate;
	}
	public void setDefaultRate(double aDefaultRate)
	{
		DefaultRate = aDefaultRate;
	}
	public void setDefaultRate(String aDefaultRate)
	{
		if (aDefaultRate != null && !aDefaultRate.equals(""))
		{
			Double tDouble = new Double(aDefaultRate);
			double d = tDouble.doubleValue();
			DefaultRate = d;
		}
	}

	/**
	* 0 －－ 和前台录入无关。<p>
	* 1 －－ 必须从前台录入，如果不录，报错。
	*/
	public String getNeedInput()
	{
		return NeedInput;
	}
	public void setNeedInput(String aNeedInput)
	{
		NeedInput = aNeedInput;
	}
	public String getCalCodeMoney()
	{
		return CalCodeMoney;
	}
	public void setCalCodeMoney(String aCalCodeMoney)
	{
		CalCodeMoney = aCalCodeMoney;
	}
	/**
	* 0 －－ 从给付到账户转数据<p>
	* 1 －－ 从账户到给付转数据
	*/
	public String getDealDirection()
	{
		return DealDirection;
	}
	public void setDealDirection(String aDealDirection)
	{
		DealDirection = aDealDirection;
	}
	/**
	* 判断使用那种算法进行计算。<p>
	* 0 －－ 完全转出账户中的所有金额<p>
	* 1 －－ 安现金计算转出账户金额
	*/
	public String getCalFlag()
	{
		return CalFlag;
	}
	public void setCalFlag(String aCalFlag)
	{
		CalFlag = aCalFlag;
	}
	/**
	* 1 －－投保单录入时产生<p>
	* 2 －－缴费时产生<p>
	* 3 －－领取时产生<p>
	* 4 －－第一次账户缴费时产生
	*/
	public String getAccCreatePos()
	{
		return AccCreatePos;
	}
	public void setAccCreatePos(String aAccCreatePos)
	{
		AccCreatePos = aAccCreatePos;
	}
	public String getGetDutyName()
	{
		return GetDutyName;
	}
	public void setGetDutyName(String aGetDutyName)
	{
		GetDutyName = aGetDutyName;
	}

	/**
	* 使用另外一个 LMRiskAccGetSchema 对象给 Schema 赋值
	* @param: aLMRiskAccGetSchema LMRiskAccGetSchema
	**/
	public void setSchema(LMRiskAccGetSchema aLMRiskAccGetSchema)
	{
		this.RiskCode = aLMRiskAccGetSchema.getRiskCode();
		this.RiskVer = aLMRiskAccGetSchema.getRiskVer();
		this.InsuAccNo = aLMRiskAccGetSchema.getInsuAccNo();
		this.GetDutyCode = aLMRiskAccGetSchema.getGetDutyCode();
		this.DefaultRate = aLMRiskAccGetSchema.getDefaultRate();
		this.NeedInput = aLMRiskAccGetSchema.getNeedInput();
		this.CalCodeMoney = aLMRiskAccGetSchema.getCalCodeMoney();
		this.DealDirection = aLMRiskAccGetSchema.getDealDirection();
		this.CalFlag = aLMRiskAccGetSchema.getCalFlag();
		this.AccCreatePos = aLMRiskAccGetSchema.getAccCreatePos();
		this.GetDutyName = aLMRiskAccGetSchema.getGetDutyName();
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

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			this.DefaultRate = rs.getDouble("DefaultRate");
			if( rs.getString("NeedInput") == null )
				this.NeedInput = null;
			else
				this.NeedInput = rs.getString("NeedInput").trim();

			if( rs.getString("CalCodeMoney") == null )
				this.CalCodeMoney = null;
			else
				this.CalCodeMoney = rs.getString("CalCodeMoney").trim();

			if( rs.getString("DealDirection") == null )
				this.DealDirection = null;
			else
				this.DealDirection = rs.getString("DealDirection").trim();

			if( rs.getString("CalFlag") == null )
				this.CalFlag = null;
			else
				this.CalFlag = rs.getString("CalFlag").trim();

			if( rs.getString("AccCreatePos") == null )
				this.AccCreatePos = null;
			else
				this.AccCreatePos = rs.getString("AccCreatePos").trim();

			if( rs.getString("GetDutyName") == null )
				this.GetDutyName = null;
			else
				this.GetDutyName = rs.getString("GetDutyName").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的LMRiskAccGet表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskAccGetSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskAccGetSchema getSchema()
	{
		LMRiskAccGetSchema aLMRiskAccGetSchema = new LMRiskAccGetSchema();
		aLMRiskAccGetSchema.setSchema(this);
		return aLMRiskAccGetSchema;
	}

	public LMRiskAccGetDB getDB()
	{
		LMRiskAccGetDB aDBOper = new LMRiskAccGetDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskAccGet描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefaultRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedInput)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCodeMoney)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DealDirection)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCreatePos)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskAccGet>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DefaultRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			NeedInput = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CalCodeMoney = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DealDirection = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AccCreatePos = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			GetDutyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskAccGetSchema";
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("DefaultRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultRate));
		}
		if (FCode.equalsIgnoreCase("NeedInput"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedInput));
		}
		if (FCode.equalsIgnoreCase("CalCodeMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCodeMoney));
		}
		if (FCode.equalsIgnoreCase("DealDirection"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealDirection));
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFlag));
		}
		if (FCode.equalsIgnoreCase("AccCreatePos"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCreatePos));
		}
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyName));
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
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 4:
				strFieldValue = String.valueOf(DefaultRate);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(NeedInput);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CalCodeMoney);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DealDirection);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AccCreatePos);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(GetDutyName);
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
		}
		if (FCode.equalsIgnoreCase("DefaultRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DefaultRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("NeedInput"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedInput = FValue.trim();
			}
			else
				NeedInput = null;
		}
		if (FCode.equalsIgnoreCase("CalCodeMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCodeMoney = FValue.trim();
			}
			else
				CalCodeMoney = null;
		}
		if (FCode.equalsIgnoreCase("DealDirection"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealDirection = FValue.trim();
			}
			else
				DealDirection = null;
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFlag = FValue.trim();
			}
			else
				CalFlag = null;
		}
		if (FCode.equalsIgnoreCase("AccCreatePos"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCreatePos = FValue.trim();
			}
			else
				AccCreatePos = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyName = FValue.trim();
			}
			else
				GetDutyName = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskAccGetSchema other = (LMRiskAccGetSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& DefaultRate == other.getDefaultRate()
			&& NeedInput.equals(other.getNeedInput())
			&& CalCodeMoney.equals(other.getCalCodeMoney())
			&& DealDirection.equals(other.getDealDirection())
			&& CalFlag.equals(other.getCalFlag())
			&& AccCreatePos.equals(other.getAccCreatePos())
			&& GetDutyName.equals(other.getGetDutyName());
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
		if( strFieldName.equals("InsuAccNo") ) {
			return 2;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 3;
		}
		if( strFieldName.equals("DefaultRate") ) {
			return 4;
		}
		if( strFieldName.equals("NeedInput") ) {
			return 5;
		}
		if( strFieldName.equals("CalCodeMoney") ) {
			return 6;
		}
		if( strFieldName.equals("DealDirection") ) {
			return 7;
		}
		if( strFieldName.equals("CalFlag") ) {
			return 8;
		}
		if( strFieldName.equals("AccCreatePos") ) {
			return 9;
		}
		if( strFieldName.equals("GetDutyName") ) {
			return 10;
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
				strFieldName = "InsuAccNo";
				break;
			case 3:
				strFieldName = "GetDutyCode";
				break;
			case 4:
				strFieldName = "DefaultRate";
				break;
			case 5:
				strFieldName = "NeedInput";
				break;
			case 6:
				strFieldName = "CalCodeMoney";
				break;
			case 7:
				strFieldName = "DealDirection";
				break;
			case 8:
				strFieldName = "CalFlag";
				break;
			case 9:
				strFieldName = "AccCreatePos";
				break;
			case 10:
				strFieldName = "GetDutyName";
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
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NeedInput") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCodeMoney") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealDirection") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccCreatePos") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyName") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
