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
import com.sinosoft.lis.db.LMRiskSortDB;

/*
 * <p>ClassName: LMRiskSortSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRiskSortSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMRiskSortSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种分类类型 */
	private String RiskSortType;
	/** 险种分类值 */
	private String RiskSortValue;
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskSortSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "RiskSortType";
		pk[2] = "RiskSortValue";

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
		LMRiskSortSchema cloned = (LMRiskSortSchema)super.clone();
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
	/**
	* 00.险种界面分类<p>
	* 1.再保累积类型<p>
	* 2.自动核保（风险累计分类）<p>
	* 3.核保查询（风险累计分类）<p>
	* 4.理赔率统计报表（NB)<p>
	* 5.核保权限分类<p>
	* 6 准许进行案件合并的险种,适用于费用补偿型的险种<p>
	* 7.理赔保额冲减<p>
	* 8.理赔医疗类附加险单独理算,<p>
	* 9. 责任下多个给付责任共享险种保额,理算时给付责任的相互冲减<p>
	* 10 责任下多个给付责任分类共享某个保额,理算时给付责任的相互冲减<p>
	* 18 豁免险的对象豁免投保人;<p>
	* 19 豁免险的对象豁免被保人<p>
	* 20 主险销户附加险必须销户
	*/
	public String getRiskSortType()
	{
		return RiskSortType;
	}
	public void setRiskSortType(String aRiskSortType)
	{
		RiskSortType = aRiskSortType;
	}
	/**
	* 对于每种不同的险种分类类型有不同的可选值<p>
	* 再保累积类型 0 跟此项无关 1.寿险 2.重疾 3.意外<p>
	* 自动核保（风险累计分类）0.跟此项无关 1.寿险 2.重疾 3. 意外医疗 4.住院医疗 5.住院补贴 6.高额医疗 7.意外伤害 8.投连<p>
	* 核保查询（风险累计分类）0.跟此项无关 1.寿险 2.重疾 3. 短期健康险 4.意外伤害 5.投连 6.年金<p>
	* 核保权限分类            0.跟此项无关 1.寿险 2.重疾 3. 短期健康险 4.意外伤害 5.投连 6.年金<p>
	* 理赔率统计报表（NB)     0.跟此项无关 1.长险 2.短期意外险 3. 短期健康险<p>
	* <p>
	* 7理赔保额冲减     0代表冲减<p>
	* 8理赔医疗类附加险单独理算  0代表单独理算<p>
	* 9.责任下多个给付责任共享保额相互冲减：值为各个给付责任的给付责任编码<p>
	* 10 责任下多个给付责任分类共享某个保额,理算时给付责任的相互冲减,值为各个给付责任的给付责任编码，但是共享同一类保额的多个给付责任需要在Remark字段描述相同的数值,如0,1,2....<p>
	* <p>
	* 18  RiskSortValue(险种分类值) 写定执行准许执行豁免险的理赔类型的后两位,如身故就描述02,不区分疾病和意外;<p>
	* 19  RiskSortValue(险种分类值) 写定执行准许执行豁免险的理赔类型的后两位,如重大疾病就描述04,不区分疾病和意外;
	*/
	public String getRiskSortValue()
	{
		return RiskSortValue;
	}
	public void setRiskSortValue(String aRiskSortValue)
	{
		RiskSortValue = aRiskSortValue;
	}
	/**
	* RiskSortType=10 共享同一类保额的多个给付责任需要在Remark字段描述相同的数值,如0,1,2....
	*/
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LMRiskSortSchema 对象给 Schema 赋值
	* @param: aLMRiskSortSchema LMRiskSortSchema
	**/
	public void setSchema(LMRiskSortSchema aLMRiskSortSchema)
	{
		this.RiskCode = aLMRiskSortSchema.getRiskCode();
		this.RiskSortType = aLMRiskSortSchema.getRiskSortType();
		this.RiskSortValue = aLMRiskSortSchema.getRiskSortValue();
		this.Remark = aLMRiskSortSchema.getRemark();
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

			if( rs.getString("RiskSortType") == null )
				this.RiskSortType = null;
			else
				this.RiskSortType = rs.getString("RiskSortType").trim();

			if( rs.getString("RiskSortValue") == null )
				this.RiskSortValue = null;
			else
				this.RiskSortValue = rs.getString("RiskSortValue").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMRiskSort表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskSortSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskSortSchema getSchema()
	{
		LMRiskSortSchema aLMRiskSortSchema = new LMRiskSortSchema();
		aLMRiskSortSchema.setSchema(this);
		return aLMRiskSortSchema;
	}

	public LMRiskSortDB getDB()
	{
		LMRiskSortDB aDBOper = new LMRiskSortDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskSort描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskSortType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskSortValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskSort>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskSortType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskSortValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskSortSchema";
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
		if (FCode.equalsIgnoreCase("RiskSortType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskSortType));
		}
		if (FCode.equalsIgnoreCase("RiskSortValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskSortValue));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskSortType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskSortValue);
				break;
			case 3:
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("RiskSortType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskSortType = FValue.trim();
			}
			else
				RiskSortType = null;
		}
		if (FCode.equalsIgnoreCase("RiskSortValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskSortValue = FValue.trim();
			}
			else
				RiskSortValue = null;
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
		LMRiskSortSchema other = (LMRiskSortSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskSortType.equals(other.getRiskSortType())
			&& RiskSortValue.equals(other.getRiskSortValue())
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("RiskSortType") ) {
			return 1;
		}
		if( strFieldName.equals("RiskSortValue") ) {
			return 2;
		}
		if( strFieldName.equals("Remark") ) {
			return 3;
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
				strFieldName = "RiskSortType";
				break;
			case 2:
				strFieldName = "RiskSortValue";
				break;
			case 3:
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskSortType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskSortValue") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
