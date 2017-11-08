

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
import com.sinosoft.lis.db.PROCopyCodeDB;

/*
 * <p>ClassName: PROCopyCodeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品复制算法编码配置表
 */
public class PROCopyCodeSchema implements Schema, Cloneable
{
	// @Field
	/** 原算法编码 */
	private String Calcode;
	/** 复制后的算法编码 */
	private String CopyToCalcodeSeq;
	/** 备用字段1 */
	private String Standy1;
	/** 备用字段2
备用字段2 */
	private String Standy2;
	/** 备用字段3 */
	private String Standy3;
	/** 创建date */
	private Date MakeDate;
	/** 创建时间 */
	private String MakeTime;
	/** 操作人 */
	private String Opereator;
	/** 修改date */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 原险种编码 */
	private String OldRiskCode;
	/** 复制后的险种编码 */
	private String CopyToRiskCode;
	/** Id */
	private String Serialno;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PROCopyCodeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "Serialno";

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
		PROCopyCodeSchema cloned = (PROCopyCodeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCalcode()
	{
		return Calcode;
	}
	public void setCalcode(String aCalcode)
	{
		Calcode = aCalcode;
	}
	public String getCopyToCalcodeSeq()
	{
		return CopyToCalcodeSeq;
	}
	public void setCopyToCalcodeSeq(String aCopyToCalcodeSeq)
	{
		CopyToCalcodeSeq = aCopyToCalcodeSeq;
	}
	public String getStandy1()
	{
		return Standy1;
	}
	public void setStandy1(String aStandy1)
	{
		Standy1 = aStandy1;
	}
	public String getStandy2()
	{
		return Standy2;
	}
	public void setStandy2(String aStandy2)
	{
		Standy2 = aStandy2;
	}
	public String getStandy3()
	{
		return Standy3;
	}
	public void setStandy3(String aStandy3)
	{
		Standy3 = aStandy3;
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
	public String getOpereator()
	{
		return Opereator;
	}
	public void setOpereator(String aOpereator)
	{
		Opereator = aOpereator;
	}
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	public String getOldRiskCode()
	{
		return OldRiskCode;
	}
	public void setOldRiskCode(String aOldRiskCode)
	{
		OldRiskCode = aOldRiskCode;
	}
	public String getCopyToRiskCode()
	{
		return CopyToRiskCode;
	}
	public void setCopyToRiskCode(String aCopyToRiskCode)
	{
		CopyToRiskCode = aCopyToRiskCode;
	}
	public String getSerialno()
	{
		return Serialno;
	}
	public void setSerialno(String aSerialno)
	{
		Serialno = aSerialno;
	}

	/**
	* 使用另外一个 PROCopyCodeSchema 对象给 Schema 赋值
	* @param: aPROCopyCodeSchema PROCopyCodeSchema
	**/
	public void setSchema(PROCopyCodeSchema aPROCopyCodeSchema)
	{
		this.Calcode = aPROCopyCodeSchema.getCalcode();
		this.CopyToCalcodeSeq = aPROCopyCodeSchema.getCopyToCalcodeSeq();
		this.Standy1 = aPROCopyCodeSchema.getStandy1();
		this.Standy2 = aPROCopyCodeSchema.getStandy2();
		this.Standy3 = aPROCopyCodeSchema.getStandy3();
		this.MakeDate = fDate.getDate( aPROCopyCodeSchema.getMakeDate());
		this.MakeTime = aPROCopyCodeSchema.getMakeTime();
		this.Opereator = aPROCopyCodeSchema.getOpereator();
		this.ModifyDate = fDate.getDate( aPROCopyCodeSchema.getModifyDate());
		this.ModifyTime = aPROCopyCodeSchema.getModifyTime();
		this.OldRiskCode = aPROCopyCodeSchema.getOldRiskCode();
		this.CopyToRiskCode = aPROCopyCodeSchema.getCopyToRiskCode();
		this.Serialno = aPROCopyCodeSchema.getSerialno();
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
			if( rs.getString("Calcode") == null )
				this.Calcode = null;
			else
				this.Calcode = rs.getString("Calcode").trim();

			if( rs.getString("CopyToCalcodeSeq") == null )
				this.CopyToCalcodeSeq = null;
			else
				this.CopyToCalcodeSeq = rs.getString("CopyToCalcodeSeq").trim();

			if( rs.getString("Standy1") == null )
				this.Standy1 = null;
			else
				this.Standy1 = rs.getString("Standy1").trim();

			if( rs.getString("Standy2") == null )
				this.Standy2 = null;
			else
				this.Standy2 = rs.getString("Standy2").trim();

			if( rs.getString("Standy3") == null )
				this.Standy3 = null;
			else
				this.Standy3 = rs.getString("Standy3").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("Opereator") == null )
				this.Opereator = null;
			else
				this.Opereator = rs.getString("Opereator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("OldRiskCode") == null )
				this.OldRiskCode = null;
			else
				this.OldRiskCode = rs.getString("OldRiskCode").trim();

			if( rs.getString("CopyToRiskCode") == null )
				this.CopyToRiskCode = null;
			else
				this.CopyToRiskCode = rs.getString("CopyToRiskCode").trim();

			if( rs.getString("Serialno") == null )
				this.Serialno = null;
			else
				this.Serialno = rs.getString("Serialno").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的PROCopyCode表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			sqle.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PROCopyCodeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PROCopyCodeSchema getSchema()
	{
		PROCopyCodeSchema aPROCopyCodeSchema = new PROCopyCodeSchema();
		aPROCopyCodeSchema.setSchema(this);
		return aPROCopyCodeSchema;
	}

	public PROCopyCodeDB getDB()
	{
		PROCopyCodeDB aDBOper = new PROCopyCodeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPROCopyCode描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Calcode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CopyToCalcodeSeq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standy1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standy2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standy3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Opereator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldRiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CopyToRiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Serialno));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPROCopyCode>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Calcode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CopyToCalcodeSeq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Standy1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Standy2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Standy3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Opereator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			OldRiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CopyToRiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Serialno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PROCopyCodeSchema";
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
		if (FCode.equalsIgnoreCase("Calcode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Calcode));
		}
		if (FCode.equalsIgnoreCase("CopyToCalcodeSeq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CopyToCalcodeSeq));
		}
		if (FCode.equalsIgnoreCase("Standy1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standy1));
		}
		if (FCode.equalsIgnoreCase("Standy2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standy2));
		}
		if (FCode.equalsIgnoreCase("Standy3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standy3));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("Opereator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Opereator));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("OldRiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldRiskCode));
		}
		if (FCode.equalsIgnoreCase("CopyToRiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CopyToRiskCode));
		}
		if (FCode.equalsIgnoreCase("Serialno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Serialno));
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
				strFieldValue = StrTool.GBKToUnicode(Calcode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CopyToCalcodeSeq);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Standy1);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Standy2);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Standy3);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Opereator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(OldRiskCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CopyToRiskCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Serialno);
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

		if (FCode.equalsIgnoreCase("Calcode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Calcode = FValue.trim();
			}
			else
				Calcode = null;
		}
		if (FCode.equalsIgnoreCase("CopyToCalcodeSeq"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CopyToCalcodeSeq = FValue.trim();
			}
			else
				CopyToCalcodeSeq = null;
		}
		if (FCode.equalsIgnoreCase("Standy1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standy1 = FValue.trim();
			}
			else
				Standy1 = null;
		}
		if (FCode.equalsIgnoreCase("Standy2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standy2 = FValue.trim();
			}
			else
				Standy2 = null;
		}
		if (FCode.equalsIgnoreCase("Standy3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standy3 = FValue.trim();
			}
			else
				Standy3 = null;
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
		if (FCode.equalsIgnoreCase("Opereator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Opereator = FValue.trim();
			}
			else
				Opereator = null;
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
		}
		if (FCode.equalsIgnoreCase("OldRiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldRiskCode = FValue.trim();
			}
			else
				OldRiskCode = null;
		}
		if (FCode.equalsIgnoreCase("CopyToRiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CopyToRiskCode = FValue.trim();
			}
			else
				CopyToRiskCode = null;
		}
		if (FCode.equalsIgnoreCase("Serialno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Serialno = FValue.trim();
			}
			else
				Serialno = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PROCopyCodeSchema other = (PROCopyCodeSchema)otherObject;
		return
			Calcode.equals(other.getCalcode())
			&& CopyToCalcodeSeq.equals(other.getCopyToCalcodeSeq())
			&& Standy1.equals(other.getStandy1())
			&& Standy2.equals(other.getStandy2())
			&& Standy3.equals(other.getStandy3())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& Opereator.equals(other.getOpereator())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& OldRiskCode.equals(other.getOldRiskCode())
			&& CopyToRiskCode.equals(other.getCopyToRiskCode())
			&& Serialno.equals(other.getSerialno());
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
		if( strFieldName.equals("Calcode") ) {
			return 0;
		}
		if( strFieldName.equals("CopyToCalcodeSeq") ) {
			return 1;
		}
		if( strFieldName.equals("Standy1") ) {
			return 2;
		}
		if( strFieldName.equals("Standy2") ) {
			return 3;
		}
		if( strFieldName.equals("Standy3") ) {
			return 4;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 5;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 6;
		}
		if( strFieldName.equals("Opereator") ) {
			return 7;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 8;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 9;
		}
		if( strFieldName.equals("OldRiskCode") ) {
			return 10;
		}
		if( strFieldName.equals("CopyToRiskCode") ) {
			return 11;
		}
		if( strFieldName.equals("Serialno") ) {
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
				strFieldName = "Calcode";
				break;
			case 1:
				strFieldName = "CopyToCalcodeSeq";
				break;
			case 2:
				strFieldName = "Standy1";
				break;
			case 3:
				strFieldName = "Standy2";
				break;
			case 4:
				strFieldName = "Standy3";
				break;
			case 5:
				strFieldName = "MakeDate";
				break;
			case 6:
				strFieldName = "MakeTime";
				break;
			case 7:
				strFieldName = "Opereator";
				break;
			case 8:
				strFieldName = "ModifyDate";
				break;
			case 9:
				strFieldName = "ModifyTime";
				break;
			case 10:
				strFieldName = "OldRiskCode";
				break;
			case 11:
				strFieldName = "CopyToRiskCode";
				break;
			case 12:
				strFieldName = "Serialno";
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
		if( strFieldName.equals("Calcode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CopyToCalcodeSeq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standy1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standy2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standy3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Opereator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldRiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CopyToRiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Serialno") ) {
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
				nFieldType = Schema.TYPE_DATE;
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

