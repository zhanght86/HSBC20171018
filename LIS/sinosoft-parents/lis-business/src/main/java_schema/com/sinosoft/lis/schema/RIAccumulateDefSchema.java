

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
import com.sinosoft.lis.db.RIAccumulateDefDB;

/*
 * <p>ClassName: RIAccumulateDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIAccumulateDefSchema implements Schema, Cloneable
{
	// @Field
	/** 累计方案编码 */
	private String AccumulateDefNO;
	/** 累计方案名称 */
	private String AccumulateDefName;
	/** 累计明细标志 */
	private String DeTailFlag;
	/** 累计方式 */
	private String AccumulateMode;
	/** 给付责任类型 */
	private String GetDutyType;
	/** 风险保额转换标记 */
	private String RiskAmntFlag;
	/** 算法编码 */
	private String ArithmeticID;
	/** 团个标志 */
	private String GIType;
	/** 备用字段 */
	private String StandbyFlag;
	/** 状态 */
	private String State;
	/** 币种 */
	private String Currency;
	/** 数据采集周期 */
	private String DIntv;
	/** 业财标记 */
	private String BFFlag;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIAccumulateDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "AccumulateDefNO";

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
		RIAccumulateDefSchema cloned = (RIAccumulateDefSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAccumulateDefNO()
	{
		return AccumulateDefNO;
	}
	public void setAccumulateDefNO(String aAccumulateDefNO)
	{
		AccumulateDefNO = aAccumulateDefNO;
	}
	public String getAccumulateDefName()
	{
		return AccumulateDefName;
	}
	public void setAccumulateDefName(String aAccumulateDefName)
	{
		AccumulateDefName = aAccumulateDefName;
	}
	/**
	* 01-代表险种级别 02-代表责任级别
	*/
	public String getDeTailFlag()
	{
		return DeTailFlag;
	}
	public void setDeTailFlag(String aDeTailFlag)
	{
		DeTailFlag = aDeTailFlag;
	}
	/**
	* 01-不累计 02-个人多合同累计 03-重新累计
	*/
	public String getAccumulateMode()
	{
		return AccumulateMode;
	}
	public void setAccumulateMode(String aAccumulateMode)
	{
		AccumulateMode = aAccumulateMode;
	}
	/**
	* 01-不累计 02-个人多合同累计
	*/
	public String getGetDutyType()
	{
		return GetDutyType;
	}
	public void setGetDutyType(String aGetDutyType)
	{
		GetDutyType = aGetDutyType;
	}
	/**
	* 参与累计分保时是否将基本保额转换为风险保额<p>
	* <p>
	* 01-不需要转换<p>
	* <p>
	* 02-需要转换
	*/
	public String getRiskAmntFlag()
	{
		return RiskAmntFlag;
	}
	public void setRiskAmntFlag(String aRiskAmntFlag)
	{
		RiskAmntFlag = aRiskAmntFlag;
	}
	public String getArithmeticID()
	{
		return ArithmeticID;
	}
	public void setArithmeticID(String aArithmeticID)
	{
		ArithmeticID = aArithmeticID;
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
	/**
	* 累计标志<p>
	* 01-累计；02-不累计
	*/
	public String getStandbyFlag()
	{
		return StandbyFlag;
	}
	public void setStandbyFlag(String aStandbyFlag)
	{
		StandbyFlag = aStandbyFlag;
	}
	/**
	* 01:有效，02：无效，99：样例
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	/**
	* D-天，M-月度，Q-季度，Y年度
	*/
	public String getDIntv()
	{
		return DIntv;
	}
	public void setDIntv(String aDIntv)
	{
		DIntv = aDIntv;
	}
	/**
	* 01-业务标记，02-财务标记
	*/
	public String getBFFlag()
	{
		return BFFlag;
	}
	public void setBFFlag(String aBFFlag)
	{
		BFFlag = aBFFlag;
	}

	/**
	* 使用另外一个 RIAccumulateDefSchema 对象给 Schema 赋值
	* @param: aRIAccumulateDefSchema RIAccumulateDefSchema
	**/
	public void setSchema(RIAccumulateDefSchema aRIAccumulateDefSchema)
	{
		this.AccumulateDefNO = aRIAccumulateDefSchema.getAccumulateDefNO();
		this.AccumulateDefName = aRIAccumulateDefSchema.getAccumulateDefName();
		this.DeTailFlag = aRIAccumulateDefSchema.getDeTailFlag();
		this.AccumulateMode = aRIAccumulateDefSchema.getAccumulateMode();
		this.GetDutyType = aRIAccumulateDefSchema.getGetDutyType();
		this.RiskAmntFlag = aRIAccumulateDefSchema.getRiskAmntFlag();
		this.ArithmeticID = aRIAccumulateDefSchema.getArithmeticID();
		this.GIType = aRIAccumulateDefSchema.getGIType();
		this.StandbyFlag = aRIAccumulateDefSchema.getStandbyFlag();
		this.State = aRIAccumulateDefSchema.getState();
		this.Currency = aRIAccumulateDefSchema.getCurrency();
		this.DIntv = aRIAccumulateDefSchema.getDIntv();
		this.BFFlag = aRIAccumulateDefSchema.getBFFlag();
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
			if( rs.getString("AccumulateDefNO") == null )
				this.AccumulateDefNO = null;
			else
				this.AccumulateDefNO = rs.getString("AccumulateDefNO").trim();

			if( rs.getString("AccumulateDefName") == null )
				this.AccumulateDefName = null;
			else
				this.AccumulateDefName = rs.getString("AccumulateDefName").trim();

			if( rs.getString("DeTailFlag") == null )
				this.DeTailFlag = null;
			else
				this.DeTailFlag = rs.getString("DeTailFlag").trim();

			if( rs.getString("AccumulateMode") == null )
				this.AccumulateMode = null;
			else
				this.AccumulateMode = rs.getString("AccumulateMode").trim();

			if( rs.getString("GetDutyType") == null )
				this.GetDutyType = null;
			else
				this.GetDutyType = rs.getString("GetDutyType").trim();

			if( rs.getString("RiskAmntFlag") == null )
				this.RiskAmntFlag = null;
			else
				this.RiskAmntFlag = rs.getString("RiskAmntFlag").trim();

			if( rs.getString("ArithmeticID") == null )
				this.ArithmeticID = null;
			else
				this.ArithmeticID = rs.getString("ArithmeticID").trim();

			if( rs.getString("GIType") == null )
				this.GIType = null;
			else
				this.GIType = rs.getString("GIType").trim();

			if( rs.getString("StandbyFlag") == null )
				this.StandbyFlag = null;
			else
				this.StandbyFlag = rs.getString("StandbyFlag").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("DIntv") == null )
				this.DIntv = null;
			else
				this.DIntv = rs.getString("DIntv").trim();

			if( rs.getString("BFFlag") == null )
				this.BFFlag = null;
			else
				this.BFFlag = rs.getString("BFFlag").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIAccumulateDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIAccumulateDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIAccumulateDefSchema getSchema()
	{
		RIAccumulateDefSchema aRIAccumulateDefSchema = new RIAccumulateDefSchema();
		aRIAccumulateDefSchema.setSchema(this);
		return aRIAccumulateDefSchema;
	}

	public RIAccumulateDefDB getDB()
	{
		RIAccumulateDefDB aDBOper = new RIAccumulateDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIAccumulateDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AccumulateDefNO)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccumulateDefName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeTailFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccumulateMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskAmntFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ArithmeticID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GIType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DIntv)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BFFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIAccumulateDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AccumulateDefNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AccumulateDefName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DeTailFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AccumulateMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GetDutyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskAmntFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ArithmeticID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			GIType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			StandbyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			DIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			BFFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIAccumulateDefSchema";
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
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccumulateDefNO));
		}
		if (FCode.equalsIgnoreCase("AccumulateDefName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccumulateDefName));
		}
		if (FCode.equalsIgnoreCase("DeTailFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeTailFlag));
		}
		if (FCode.equalsIgnoreCase("AccumulateMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccumulateMode));
		}
		if (FCode.equalsIgnoreCase("GetDutyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyType));
		}
		if (FCode.equalsIgnoreCase("RiskAmntFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskAmntFlag));
		}
		if (FCode.equalsIgnoreCase("ArithmeticID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ArithmeticID));
		}
		if (FCode.equalsIgnoreCase("GIType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GIType));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("DIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DIntv));
		}
		if (FCode.equalsIgnoreCase("BFFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BFFlag));
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
				strFieldValue = StrTool.GBKToUnicode(AccumulateDefNO);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AccumulateDefName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DeTailFlag);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AccumulateMode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GetDutyType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskAmntFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ArithmeticID);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(GIType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(DIntv);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(BFFlag);
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

		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccumulateDefNO = FValue.trim();
			}
			else
				AccumulateDefNO = null;
		}
		if (FCode.equalsIgnoreCase("AccumulateDefName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccumulateDefName = FValue.trim();
			}
			else
				AccumulateDefName = null;
		}
		if (FCode.equalsIgnoreCase("DeTailFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeTailFlag = FValue.trim();
			}
			else
				DeTailFlag = null;
		}
		if (FCode.equalsIgnoreCase("AccumulateMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccumulateMode = FValue.trim();
			}
			else
				AccumulateMode = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyType = FValue.trim();
			}
			else
				GetDutyType = null;
		}
		if (FCode.equalsIgnoreCase("RiskAmntFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskAmntFlag = FValue.trim();
			}
			else
				RiskAmntFlag = null;
		}
		if (FCode.equalsIgnoreCase("ArithmeticID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ArithmeticID = FValue.trim();
			}
			else
				ArithmeticID = null;
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
		if (FCode.equalsIgnoreCase("StandbyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag = FValue.trim();
			}
			else
				StandbyFlag = null;
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
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equalsIgnoreCase("DIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DIntv = FValue.trim();
			}
			else
				DIntv = null;
		}
		if (FCode.equalsIgnoreCase("BFFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BFFlag = FValue.trim();
			}
			else
				BFFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIAccumulateDefSchema other = (RIAccumulateDefSchema)otherObject;
		return
			AccumulateDefNO.equals(other.getAccumulateDefNO())
			&& AccumulateDefName.equals(other.getAccumulateDefName())
			&& DeTailFlag.equals(other.getDeTailFlag())
			&& AccumulateMode.equals(other.getAccumulateMode())
			&& GetDutyType.equals(other.getGetDutyType())
			&& RiskAmntFlag.equals(other.getRiskAmntFlag())
			&& ArithmeticID.equals(other.getArithmeticID())
			&& GIType.equals(other.getGIType())
			&& StandbyFlag.equals(other.getStandbyFlag())
			&& State.equals(other.getState())
			&& Currency.equals(other.getCurrency())
			&& DIntv.equals(other.getDIntv())
			&& BFFlag.equals(other.getBFFlag());
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
		if( strFieldName.equals("AccumulateDefNO") ) {
			return 0;
		}
		if( strFieldName.equals("AccumulateDefName") ) {
			return 1;
		}
		if( strFieldName.equals("DeTailFlag") ) {
			return 2;
		}
		if( strFieldName.equals("AccumulateMode") ) {
			return 3;
		}
		if( strFieldName.equals("GetDutyType") ) {
			return 4;
		}
		if( strFieldName.equals("RiskAmntFlag") ) {
			return 5;
		}
		if( strFieldName.equals("ArithmeticID") ) {
			return 6;
		}
		if( strFieldName.equals("GIType") ) {
			return 7;
		}
		if( strFieldName.equals("StandbyFlag") ) {
			return 8;
		}
		if( strFieldName.equals("State") ) {
			return 9;
		}
		if( strFieldName.equals("Currency") ) {
			return 10;
		}
		if( strFieldName.equals("DIntv") ) {
			return 11;
		}
		if( strFieldName.equals("BFFlag") ) {
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
				strFieldName = "AccumulateDefNO";
				break;
			case 1:
				strFieldName = "AccumulateDefName";
				break;
			case 2:
				strFieldName = "DeTailFlag";
				break;
			case 3:
				strFieldName = "AccumulateMode";
				break;
			case 4:
				strFieldName = "GetDutyType";
				break;
			case 5:
				strFieldName = "RiskAmntFlag";
				break;
			case 6:
				strFieldName = "ArithmeticID";
				break;
			case 7:
				strFieldName = "GIType";
				break;
			case 8:
				strFieldName = "StandbyFlag";
				break;
			case 9:
				strFieldName = "State";
				break;
			case 10:
				strFieldName = "Currency";
				break;
			case 11:
				strFieldName = "DIntv";
				break;
			case 12:
				strFieldName = "BFFlag";
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
		if( strFieldName.equals("AccumulateDefNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccumulateDefName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeTailFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccumulateMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskAmntFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ArithmeticID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GIType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DIntv") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BFFlag") ) {
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
