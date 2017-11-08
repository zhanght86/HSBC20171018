

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
import com.sinosoft.lis.db.LMCheckFieldDB;

/*
 * <p>ClassName: LMCheckFieldSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMCheckFieldSchema implements Schema, Cloneable
{
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 控制字段名称 */
	private String FieldName;
	/** 序号 */
	private String SerialNo;
	/** 算法 */
	private String CalCode;
	/** 页面位置 */
	private String PageLocation;
	/** 事件位置 */
	private String Location;
	/** 提示信息 */
	private String Msg;
	/** 提示标记 */
	private String MsgFlag;
	/** 修改变量标记 */
	private String UpdFlag;
	/** 有效结果标记 */
	private String ValiFlag;
	/** 返回值有效标记 */
	private String ReturnValiFlag;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMCheckFieldSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RiskCode";
		pk[1] = "RiskVer";
		pk[2] = "FieldName";
		pk[3] = "SerialNo";

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
		LMCheckFieldSchema cloned = (LMCheckFieldSchema)super.clone();
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
	* 该字段为需要校验的字段的英文名称。该字段在一个界面中必须唯一。
	*/
	public String getFieldName()
	{
		return FieldName;
	}
	public void setFieldName(String aFieldName)
	{
		FieldName = aFieldName;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	/**
	* 保额算保费
	*/
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	/**
	* 如果该字段为空或null，则表示处理所有页面，否则只是处理本页面的校验。<p>
	* <p>
	* TBINPUT#TBTYPEGC 团单合同自动复核规则<p>
	* TBINPUT#TBTYPEIC 个单合同自动复核规则<p>
	* TBINPUT#TBTYPEG 团单险种自动复核规则<p>
	* TBINPUT#TBTYPEI 个单险种自动复核规则
	*/
	public String getPageLocation()
	{
		return PageLocation;
	}
	public void setPageLocation(String aPageLocation)
	{
		PageLocation = aPageLocation;
	}
	/**
	* B--Before Field 、A--After Field
	*/
	public String getLocation()
	{
		return Location;
	}
	public void setLocation(String aLocation)
	{
		Location = aLocation;
	}
	public String getMsg()
	{
		return Msg;
	}
	public void setMsg(String aMsg)
	{
		Msg = aMsg;
	}
	/**
	* Y--是,表示需要将提示信息显示出来<p>
	* N--否，表示不显示提示信息。<p>
	* 1--执行标志，表示需要执行返回的相应函数。
	*/
	public String getMsgFlag()
	{
		return MsgFlag;
	}
	public void setMsgFlag(String aMsgFlag)
	{
		MsgFlag = aMsgFlag;
	}
	/**
	* Y--是，表示需要将屏幕上的相关信息进行修改。<p>
	* N--否
	*/
	public String getUpdFlag()
	{
		return UpdFlag;
	}
	public void setUpdFlag(String aUpdFlag)
	{
		UpdFlag = aUpdFlag;
	}
	/**
	* 该字段描述哪些结果对于该控制是有效的，如果有多个有效值，则通过“;”将多个有效值分开。
	*/
	public String getValiFlag()
	{
		return ValiFlag;
	}
	public void setValiFlag(String aValiFlag)
	{
		ValiFlag = aValiFlag;
	}
	/**
	* Y--是，表示执行成功，并且需要进一步处理<p>
	* N--否，表示不成功，不进行其他任何处理。
	*/
	public String getReturnValiFlag()
	{
		return ReturnValiFlag;
	}
	public void setReturnValiFlag(String aReturnValiFlag)
	{
		ReturnValiFlag = aReturnValiFlag;
	}

	/**
	* 使用另外一个 LMCheckFieldSchema 对象给 Schema 赋值
	* @param: aLMCheckFieldSchema LMCheckFieldSchema
	**/
	public void setSchema(LMCheckFieldSchema aLMCheckFieldSchema)
	{
		this.RiskCode = aLMCheckFieldSchema.getRiskCode();
		this.RiskVer = aLMCheckFieldSchema.getRiskVer();
		this.FieldName = aLMCheckFieldSchema.getFieldName();
		this.SerialNo = aLMCheckFieldSchema.getSerialNo();
		this.CalCode = aLMCheckFieldSchema.getCalCode();
		this.PageLocation = aLMCheckFieldSchema.getPageLocation();
		this.Location = aLMCheckFieldSchema.getLocation();
		this.Msg = aLMCheckFieldSchema.getMsg();
		this.MsgFlag = aLMCheckFieldSchema.getMsgFlag();
		this.UpdFlag = aLMCheckFieldSchema.getUpdFlag();
		this.ValiFlag = aLMCheckFieldSchema.getValiFlag();
		this.ReturnValiFlag = aLMCheckFieldSchema.getReturnValiFlag();
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

			if( rs.getString("FieldName") == null )
				this.FieldName = null;
			else
				this.FieldName = rs.getString("FieldName").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("PageLocation") == null )
				this.PageLocation = null;
			else
				this.PageLocation = rs.getString("PageLocation").trim();

			if( rs.getString("Location") == null )
				this.Location = null;
			else
				this.Location = rs.getString("Location").trim();

			if( rs.getString("Msg") == null )
				this.Msg = null;
			else
				this.Msg = rs.getString("Msg").trim();

			if( rs.getString("MsgFlag") == null )
				this.MsgFlag = null;
			else
				this.MsgFlag = rs.getString("MsgFlag").trim();

			if( rs.getString("UpdFlag") == null )
				this.UpdFlag = null;
			else
				this.UpdFlag = rs.getString("UpdFlag").trim();

			if( rs.getString("ValiFlag") == null )
				this.ValiFlag = null;
			else
				this.ValiFlag = rs.getString("ValiFlag").trim();

			if( rs.getString("ReturnValiFlag") == null )
				this.ReturnValiFlag = null;
			else
				this.ReturnValiFlag = rs.getString("ReturnValiFlag").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的LMCheckField表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCheckFieldSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMCheckFieldSchema getSchema()
	{
		LMCheckFieldSchema aLMCheckFieldSchema = new LMCheckFieldSchema();
		aLMCheckFieldSchema.setSchema(this);
		return aLMCheckFieldSchema;
	}

	public LMCheckFieldDB getDB()
	{
		LMCheckFieldDB aDBOper = new LMCheckFieldDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCheckField描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FieldName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PageLocation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Location)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Msg)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MsgFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpdFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValiFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReturnValiFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCheckField>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FieldName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PageLocation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Location = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Msg = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MsgFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			UpdFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ValiFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ReturnValiFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCheckFieldSchema";
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
		if (FCode.equalsIgnoreCase("FieldName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldName));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("PageLocation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PageLocation));
		}
		if (FCode.equalsIgnoreCase("Location"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Location));
		}
		if (FCode.equalsIgnoreCase("Msg"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Msg));
		}
		if (FCode.equalsIgnoreCase("MsgFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MsgFlag));
		}
		if (FCode.equalsIgnoreCase("UpdFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpdFlag));
		}
		if (FCode.equalsIgnoreCase("ValiFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValiFlag));
		}
		if (FCode.equalsIgnoreCase("ReturnValiFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReturnValiFlag));
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
				strFieldValue = StrTool.GBKToUnicode(FieldName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PageLocation);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Location);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Msg);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MsgFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(UpdFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ValiFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ReturnValiFlag);
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
		if (FCode.equalsIgnoreCase("FieldName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FieldName = FValue.trim();
			}
			else
				FieldName = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("PageLocation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PageLocation = FValue.trim();
			}
			else
				PageLocation = null;
		}
		if (FCode.equalsIgnoreCase("Location"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Location = FValue.trim();
			}
			else
				Location = null;
		}
		if (FCode.equalsIgnoreCase("Msg"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Msg = FValue.trim();
			}
			else
				Msg = null;
		}
		if (FCode.equalsIgnoreCase("MsgFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MsgFlag = FValue.trim();
			}
			else
				MsgFlag = null;
		}
		if (FCode.equalsIgnoreCase("UpdFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpdFlag = FValue.trim();
			}
			else
				UpdFlag = null;
		}
		if (FCode.equalsIgnoreCase("ValiFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValiFlag = FValue.trim();
			}
			else
				ValiFlag = null;
		}
		if (FCode.equalsIgnoreCase("ReturnValiFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReturnValiFlag = FValue.trim();
			}
			else
				ReturnValiFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMCheckFieldSchema other = (LMCheckFieldSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& FieldName.equals(other.getFieldName())
			&& SerialNo.equals(other.getSerialNo())
			&& CalCode.equals(other.getCalCode())
			&& PageLocation.equals(other.getPageLocation())
			&& Location.equals(other.getLocation())
			&& Msg.equals(other.getMsg())
			&& MsgFlag.equals(other.getMsgFlag())
			&& UpdFlag.equals(other.getUpdFlag())
			&& ValiFlag.equals(other.getValiFlag())
			&& ReturnValiFlag.equals(other.getReturnValiFlag());
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
		if( strFieldName.equals("FieldName") ) {
			return 2;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 3;
		}
		if( strFieldName.equals("CalCode") ) {
			return 4;
		}
		if( strFieldName.equals("PageLocation") ) {
			return 5;
		}
		if( strFieldName.equals("Location") ) {
			return 6;
		}
		if( strFieldName.equals("Msg") ) {
			return 7;
		}
		if( strFieldName.equals("MsgFlag") ) {
			return 8;
		}
		if( strFieldName.equals("UpdFlag") ) {
			return 9;
		}
		if( strFieldName.equals("ValiFlag") ) {
			return 10;
		}
		if( strFieldName.equals("ReturnValiFlag") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "RiskVer";
				break;
			case 2:
				strFieldName = "FieldName";
				break;
			case 3:
				strFieldName = "SerialNo";
				break;
			case 4:
				strFieldName = "CalCode";
				break;
			case 5:
				strFieldName = "PageLocation";
				break;
			case 6:
				strFieldName = "Location";
				break;
			case 7:
				strFieldName = "Msg";
				break;
			case 8:
				strFieldName = "MsgFlag";
				break;
			case 9:
				strFieldName = "UpdFlag";
				break;
			case 10:
				strFieldName = "ValiFlag";
				break;
			case 11:
				strFieldName = "ReturnValiFlag";
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
		if( strFieldName.equals("FieldName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PageLocation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Location") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Msg") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MsgFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpdFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValiFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReturnValiFlag") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
