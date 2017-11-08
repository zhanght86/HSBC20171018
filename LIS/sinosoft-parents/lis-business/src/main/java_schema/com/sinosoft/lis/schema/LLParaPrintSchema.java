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
import com.sinosoft.lis.db.LLParaPrintDB;

/*
 * <p>ClassName: LLParaPrintSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLParaPrintSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLParaPrintSchema.class);

	// @Field
	/** 单证类型 */
	private String PrtType;
	/** 单证子类型 */
	private String PrtSubType;
	/** 单证代码 */
	private String PrtCode;
	/** 单证名称 */
	private String PrtName;
	/** 打印方式 */
	private String PrtMode;
	/** 打印阶段 */
	private String PrtPhase;
	/** 打印地域 */
	private String PrtZone;
	/** 补打标志 */
	private String PatchFlag;
	/** 补打原因录入标志 */
	private String PatchReaInpFlag;
	/** 对应bl */
	private String PrtBL;
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLParaPrintSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "PrtCode";

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
		LLParaPrintSchema cloned = (LLParaPrintSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 1--单证<p>
	* 2--报表
	*/
	public String getPrtType()
	{
		return PrtType;
	}
	public void setPrtType(String aPrtType)
	{
		PrtType = aPrtType;
	}
	/**
	* 01--
	*/
	public String getPrtSubType()
	{
		return PrtSubType;
	}
	public void setPrtSubType(String aPrtSubType)
	{
		PrtSubType = aPrtSubType;
	}
	public String getPrtCode()
	{
		return PrtCode;
	}
	public void setPrtCode(String aPrtCode)
	{
		PrtCode = aPrtCode;
	}
	public String getPrtName()
	{
		return PrtName;
	}
	public void setPrtName(String aPrtName)
	{
		PrtName = aPrtName;
	}
	/**
	* 0 -- 前台打印[在线]<p>
	* 1 -- 后台打印[批处理]<p>
	* 2 -- 前后台打印[在线和批处理]
	*/
	public String getPrtMode()
	{
		return PrtMode;
	}
	public void setPrtMode(String aPrtMode)
	{
		PrtMode = aPrtMode;
	}
	/**
	* 50结案<p>
	* 0任何阶段
	*/
	public String getPrtPhase()
	{
		return PrtPhase;
	}
	public void setPrtPhase(String aPrtPhase)
	{
		PrtPhase = aPrtPhase;
	}
	/**
	* 0--操作员地域<p>
	* 1--立案地<p>
	* 2--不控制
	*/
	public String getPrtZone()
	{
		return PrtZone;
	}
	public void setPrtZone(String aPrtZone)
	{
		PrtZone = aPrtZone;
	}
	/**
	* 0－－ 可以补打<p>
	* 1－－ 不能补打
	*/
	public String getPatchFlag()
	{
		return PatchFlag;
	}
	public void setPatchFlag(String aPatchFlag)
	{
		PatchFlag = aPatchFlag;
	}
	/**
	* 0－－ 不录入原因<p>
	* 1－－ 录入原因
	*/
	public String getPatchReaInpFlag()
	{
		return PatchReaInpFlag;
	}
	public void setPatchReaInpFlag(String aPatchReaInpFlag)
	{
		PatchReaInpFlag = aPatchReaInpFlag;
	}
	public String getPrtBL()
	{
		return PrtBL;
	}
	public void setPrtBL(String aPrtBL)
	{
		PrtBL = aPrtBL;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LLParaPrintSchema 对象给 Schema 赋值
	* @param: aLLParaPrintSchema LLParaPrintSchema
	**/
	public void setSchema(LLParaPrintSchema aLLParaPrintSchema)
	{
		this.PrtType = aLLParaPrintSchema.getPrtType();
		this.PrtSubType = aLLParaPrintSchema.getPrtSubType();
		this.PrtCode = aLLParaPrintSchema.getPrtCode();
		this.PrtName = aLLParaPrintSchema.getPrtName();
		this.PrtMode = aLLParaPrintSchema.getPrtMode();
		this.PrtPhase = aLLParaPrintSchema.getPrtPhase();
		this.PrtZone = aLLParaPrintSchema.getPrtZone();
		this.PatchFlag = aLLParaPrintSchema.getPatchFlag();
		this.PatchReaInpFlag = aLLParaPrintSchema.getPatchReaInpFlag();
		this.PrtBL = aLLParaPrintSchema.getPrtBL();
		this.Remark = aLLParaPrintSchema.getRemark();
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
			if( rs.getString("PrtType") == null )
				this.PrtType = null;
			else
				this.PrtType = rs.getString("PrtType").trim();

			if( rs.getString("PrtSubType") == null )
				this.PrtSubType = null;
			else
				this.PrtSubType = rs.getString("PrtSubType").trim();

			if( rs.getString("PrtCode") == null )
				this.PrtCode = null;
			else
				this.PrtCode = rs.getString("PrtCode").trim();

			if( rs.getString("PrtName") == null )
				this.PrtName = null;
			else
				this.PrtName = rs.getString("PrtName").trim();

			if( rs.getString("PrtMode") == null )
				this.PrtMode = null;
			else
				this.PrtMode = rs.getString("PrtMode").trim();

			if( rs.getString("PrtPhase") == null )
				this.PrtPhase = null;
			else
				this.PrtPhase = rs.getString("PrtPhase").trim();

			if( rs.getString("PrtZone") == null )
				this.PrtZone = null;
			else
				this.PrtZone = rs.getString("PrtZone").trim();

			if( rs.getString("PatchFlag") == null )
				this.PatchFlag = null;
			else
				this.PatchFlag = rs.getString("PatchFlag").trim();

			if( rs.getString("PatchReaInpFlag") == null )
				this.PatchReaInpFlag = null;
			else
				this.PatchReaInpFlag = rs.getString("PatchReaInpFlag").trim();

			if( rs.getString("PrtBL") == null )
				this.PrtBL = null;
			else
				this.PrtBL = rs.getString("PrtBL").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLParaPrint表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaPrintSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLParaPrintSchema getSchema()
	{
		LLParaPrintSchema aLLParaPrintSchema = new LLParaPrintSchema();
		aLLParaPrintSchema.setSchema(this);
		return aLLParaPrintSchema;
	}

	public LLParaPrintDB getDB()
	{
		LLParaPrintDB aDBOper = new LLParaPrintDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLParaPrint描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PrtType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtSubType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtPhase)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtZone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PatchFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PatchReaInpFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtBL)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLParaPrint>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PrtType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PrtSubType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PrtCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PrtName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PrtMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PrtPhase = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PrtZone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PatchFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PatchReaInpFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PrtBL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaPrintSchema";
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
		if (FCode.equalsIgnoreCase("PrtType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtType));
		}
		if (FCode.equalsIgnoreCase("PrtSubType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtSubType));
		}
		if (FCode.equalsIgnoreCase("PrtCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtCode));
		}
		if (FCode.equalsIgnoreCase("PrtName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtName));
		}
		if (FCode.equalsIgnoreCase("PrtMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtMode));
		}
		if (FCode.equalsIgnoreCase("PrtPhase"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtPhase));
		}
		if (FCode.equalsIgnoreCase("PrtZone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtZone));
		}
		if (FCode.equalsIgnoreCase("PatchFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PatchFlag));
		}
		if (FCode.equalsIgnoreCase("PatchReaInpFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PatchReaInpFlag));
		}
		if (FCode.equalsIgnoreCase("PrtBL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtBL));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(PrtType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PrtSubType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PrtCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PrtName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PrtMode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PrtPhase);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PrtZone);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PatchFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PatchReaInpFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PrtBL);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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

		if (FCode.equalsIgnoreCase("PrtType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtType = FValue.trim();
			}
			else
				PrtType = null;
		}
		if (FCode.equalsIgnoreCase("PrtSubType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtSubType = FValue.trim();
			}
			else
				PrtSubType = null;
		}
		if (FCode.equalsIgnoreCase("PrtCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtCode = FValue.trim();
			}
			else
				PrtCode = null;
		}
		if (FCode.equalsIgnoreCase("PrtName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtName = FValue.trim();
			}
			else
				PrtName = null;
		}
		if (FCode.equalsIgnoreCase("PrtMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtMode = FValue.trim();
			}
			else
				PrtMode = null;
		}
		if (FCode.equalsIgnoreCase("PrtPhase"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtPhase = FValue.trim();
			}
			else
				PrtPhase = null;
		}
		if (FCode.equalsIgnoreCase("PrtZone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtZone = FValue.trim();
			}
			else
				PrtZone = null;
		}
		if (FCode.equalsIgnoreCase("PatchFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PatchFlag = FValue.trim();
			}
			else
				PatchFlag = null;
		}
		if (FCode.equalsIgnoreCase("PatchReaInpFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PatchReaInpFlag = FValue.trim();
			}
			else
				PatchReaInpFlag = null;
		}
		if (FCode.equalsIgnoreCase("PrtBL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtBL = FValue.trim();
			}
			else
				PrtBL = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLParaPrintSchema other = (LLParaPrintSchema)otherObject;
		return
			PrtType.equals(other.getPrtType())
			&& PrtSubType.equals(other.getPrtSubType())
			&& PrtCode.equals(other.getPrtCode())
			&& PrtName.equals(other.getPrtName())
			&& PrtMode.equals(other.getPrtMode())
			&& PrtPhase.equals(other.getPrtPhase())
			&& PrtZone.equals(other.getPrtZone())
			&& PatchFlag.equals(other.getPatchFlag())
			&& PatchReaInpFlag.equals(other.getPatchReaInpFlag())
			&& PrtBL.equals(other.getPrtBL())
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("PrtType") ) {
			return 0;
		}
		if( strFieldName.equals("PrtSubType") ) {
			return 1;
		}
		if( strFieldName.equals("PrtCode") ) {
			return 2;
		}
		if( strFieldName.equals("PrtName") ) {
			return 3;
		}
		if( strFieldName.equals("PrtMode") ) {
			return 4;
		}
		if( strFieldName.equals("PrtPhase") ) {
			return 5;
		}
		if( strFieldName.equals("PrtZone") ) {
			return 6;
		}
		if( strFieldName.equals("PatchFlag") ) {
			return 7;
		}
		if( strFieldName.equals("PatchReaInpFlag") ) {
			return 8;
		}
		if( strFieldName.equals("PrtBL") ) {
			return 9;
		}
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "PrtType";
				break;
			case 1:
				strFieldName = "PrtSubType";
				break;
			case 2:
				strFieldName = "PrtCode";
				break;
			case 3:
				strFieldName = "PrtName";
				break;
			case 4:
				strFieldName = "PrtMode";
				break;
			case 5:
				strFieldName = "PrtPhase";
				break;
			case 6:
				strFieldName = "PrtZone";
				break;
			case 7:
				strFieldName = "PatchFlag";
				break;
			case 8:
				strFieldName = "PatchReaInpFlag";
				break;
			case 9:
				strFieldName = "PrtBL";
				break;
			case 10:
				strFieldName = "Remark";
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
		if( strFieldName.equals("PrtType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtSubType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtPhase") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtZone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PatchFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PatchReaInpFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtBL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
