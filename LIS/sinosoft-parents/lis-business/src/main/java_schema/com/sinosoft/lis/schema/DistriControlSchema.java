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
import com.sinosoft.lis.db.DistriControlDB;

/*
 * <p>ClassName: DistriControlSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class DistriControlSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(DistriControlSchema.class);
	// @Field
	/** Grppolno */
	private String GrpPolno;
	/** Grpcontno */
	private String GrpContno;
	/** Riskcode */
	private String Riskcode;
	/** Distrirate */
	private double DistriRate;
	/** Feerate */
	private double FeeRate;
	/** Managecom */
	private String Managecom;
	/** Fee */
	private double Fee;
	/** Standbyflag1 */
	private String StandbyFlag1;
	/** Standbyflag2 */
	private String StandbyFlag2;
	/** Remark */
	private String Remark;
	/** Standbyflag3 */
	private String StandbyFlag3;
	/** Standbyflag4 */
	private String StandbyFlag4;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public DistriControlSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "GrpPolno";
		pk[1] = "GrpContno";

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
		DistriControlSchema cloned = (DistriControlSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 集体险种号
	*/
	public String getGrpPolno()
	{
		return GrpPolno;
	}
	public void setGrpPolno(String aGrpPolno)
	{
		if(aGrpPolno!=null && aGrpPolno.length()>20)
			throw new IllegalArgumentException("GrppolnoGrpPolno值"+aGrpPolno+"的长度"+aGrpPolno.length()+"大于最大值20");
		GrpPolno = aGrpPolno;
	}
	/**
	* 集体合同号
	*/
	public String getGrpContno()
	{
		return GrpContno;
	}
	public void setGrpContno(String aGrpContno)
	{
		if(aGrpContno!=null && aGrpContno.length()>20)
			throw new IllegalArgumentException("GrpcontnoGrpContno值"+aGrpContno+"的长度"+aGrpContno.length()+"大于最大值20");
		GrpContno = aGrpContno;
	}
	/**
	* 险种编码
	*/
	public String getRiskcode()
	{
		return Riskcode;
	}
	public void setRiskcode(String aRiskcode)
	{
		if(aRiskcode!=null && aRiskcode.length()>10)
			throw new IllegalArgumentException("RiskcodeRiskcode值"+aRiskcode+"的长度"+aRiskcode.length()+"大于最大值10");
		Riskcode = aRiskcode;
	}
	/**
	* 分保比例
	*/
	public double getDistriRate()
	{
		return DistriRate;
	}
	public void setDistriRate(double aDistriRate)
	{
		DistriRate = aDistriRate;
	}
	public void setDistriRate(String aDistriRate)
	{
		if (aDistriRate != null && !aDistriRate.equals(""))
		{
			Double tDouble = new Double(aDistriRate);
			double d = tDouble.doubleValue();
			DistriRate = d;
		}
	}

	/**
	* 摊回比例
	*/
	public double getFeeRate()
	{
		return FeeRate;
	}
	public void setFeeRate(double aFeeRate)
	{
		FeeRate = aFeeRate;
	}
	public void setFeeRate(String aFeeRate)
	{
		if (aFeeRate != null && !aFeeRate.equals(""))
		{
			Double tDouble = new Double(aFeeRate);
			double d = tDouble.doubleValue();
			FeeRate = d;
		}
	}

	/**
	* 再保机构
	*/
	public String getManagecom()
	{
		return Managecom;
	}
	public void setManagecom(String aManagecom)
	{
		if(aManagecom!=null && aManagecom.length()>20)
			throw new IllegalArgumentException("ManagecomManagecom值"+aManagecom+"的长度"+aManagecom.length()+"大于最大值20");
		Managecom = aManagecom;
	}
	/**
	* 临分手续费比例
	*/
	public double getFee()
	{
		return Fee;
	}
	public void setFee(double aFee)
	{
		Fee = aFee;
	}
	public void setFee(String aFee)
	{
		if (aFee != null && !aFee.equals(""))
		{
			Double tDouble = new Double(aFee);
			double d = tDouble.doubleValue();
			Fee = d;
		}
	}

	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		if(aStandbyFlag1!=null && aStandbyFlag1.length()>10)
			throw new IllegalArgumentException("Standbyflag1StandbyFlag1值"+aStandbyFlag1+"的长度"+aStandbyFlag1.length()+"大于最大值10");
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		if(aStandbyFlag2!=null && aStandbyFlag2.length()>10)
			throw new IllegalArgumentException("Standbyflag2StandbyFlag2值"+aStandbyFlag2+"的长度"+aStandbyFlag2.length()+"大于最大值10");
		StandbyFlag2 = aStandbyFlag2;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>200)
			throw new IllegalArgumentException("RemarkRemark值"+aRemark+"的长度"+aRemark.length()+"大于最大值200");
		Remark = aRemark;
	}
	public String getStandbyFlag3()
	{
		return StandbyFlag3;
	}
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		if(aStandbyFlag3!=null && aStandbyFlag3.length()>10)
			throw new IllegalArgumentException("Standbyflag3StandbyFlag3值"+aStandbyFlag3+"的长度"+aStandbyFlag3.length()+"大于最大值10");
		StandbyFlag3 = aStandbyFlag3;
	}
	public String getStandbyFlag4()
	{
		return StandbyFlag4;
	}
	public void setStandbyFlag4(String aStandbyFlag4)
	{
		if(aStandbyFlag4!=null && aStandbyFlag4.length()>10)
			throw new IllegalArgumentException("Standbyflag4StandbyFlag4值"+aStandbyFlag4+"的长度"+aStandbyFlag4.length()+"大于最大值10");
		StandbyFlag4 = aStandbyFlag4;
	}

	/**
	* 使用另外一个 DistriControlSchema 对象给 Schema 赋值
	* @param: aDistriControlSchema DistriControlSchema
	**/
	public void setSchema(DistriControlSchema aDistriControlSchema)
	{
		this.GrpPolno = aDistriControlSchema.getGrpPolno();
		this.GrpContno = aDistriControlSchema.getGrpContno();
		this.Riskcode = aDistriControlSchema.getRiskcode();
		this.DistriRate = aDistriControlSchema.getDistriRate();
		this.FeeRate = aDistriControlSchema.getFeeRate();
		this.Managecom = aDistriControlSchema.getManagecom();
		this.Fee = aDistriControlSchema.getFee();
		this.StandbyFlag1 = aDistriControlSchema.getStandbyFlag1();
		this.StandbyFlag2 = aDistriControlSchema.getStandbyFlag2();
		this.Remark = aDistriControlSchema.getRemark();
		this.StandbyFlag3 = aDistriControlSchema.getStandbyFlag3();
		this.StandbyFlag4 = aDistriControlSchema.getStandbyFlag4();
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
			if( rs.getString("GrpPolno") == null )
				this.GrpPolno = null;
			else
				this.GrpPolno = rs.getString("GrpPolno").trim();

			if( rs.getString("GrpContno") == null )
				this.GrpContno = null;
			else
				this.GrpContno = rs.getString("GrpContno").trim();

			if( rs.getString("Riskcode") == null )
				this.Riskcode = null;
			else
				this.Riskcode = rs.getString("Riskcode").trim();

			this.DistriRate = rs.getDouble("DistriRate");
			this.FeeRate = rs.getDouble("FeeRate");
			if( rs.getString("Managecom") == null )
				this.Managecom = null;
			else
				this.Managecom = rs.getString("Managecom").trim();

			this.Fee = rs.getDouble("Fee");
			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("StandbyFlag3") == null )
				this.StandbyFlag3 = null;
			else
				this.StandbyFlag3 = rs.getString("StandbyFlag3").trim();

			if( rs.getString("StandbyFlag4") == null )
				this.StandbyFlag4 = null;
			else
				this.StandbyFlag4 = rs.getString("StandbyFlag4").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的DistriControl表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DistriControlSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public DistriControlSchema getSchema()
	{
		DistriControlSchema aDistriControlSchema = new DistriControlSchema();
		aDistriControlSchema.setSchema(this);
		return aDistriControlSchema;
	}

	public DistriControlDB getDB()
	{
		DistriControlDB aDBOper = new DistriControlDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpDistriControl描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpPolno)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContno)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Riskcode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DistriRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Managecom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Fee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag4));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpDistriControl>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpPolno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Riskcode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DistriRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			FeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			Managecom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Fee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			StandbyFlag4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DistriControlSchema";
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
		if (FCode.equalsIgnoreCase("GrpPolno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolno));
		}
		if (FCode.equalsIgnoreCase("GrpContno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContno));
		}
		if (FCode.equalsIgnoreCase("Riskcode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Riskcode));
		}
		if (FCode.equalsIgnoreCase("DistriRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DistriRate));
		}
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeRate));
		}
		if (FCode.equalsIgnoreCase("Managecom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Managecom));
		}
		if (FCode.equalsIgnoreCase("Fee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fee));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag3));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag4));
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
				strFieldValue = StrTool.GBKToUnicode(GrpPolno);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContno);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Riskcode);
				break;
			case 3:
				strFieldValue = String.valueOf(DistriRate);
				break;
			case 4:
				strFieldValue = String.valueOf(FeeRate);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Managecom);
				break;
			case 6:
				strFieldValue = String.valueOf(Fee);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag4);
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

		if (FCode.equalsIgnoreCase("GrpPolno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolno = FValue.trim();
			}
			else
				GrpPolno = null;
		}
		if (FCode.equalsIgnoreCase("GrpContno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContno = FValue.trim();
			}
			else
				GrpContno = null;
		}
		if (FCode.equalsIgnoreCase("Riskcode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Riskcode = FValue.trim();
			}
			else
				Riskcode = null;
		}
		if (FCode.equalsIgnoreCase("DistriRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DistriRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("Managecom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Managecom = FValue.trim();
			}
			else
				Managecom = null;
		}
		if (FCode.equalsIgnoreCase("Fee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Fee = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
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
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag3 = FValue.trim();
			}
			else
				StandbyFlag3 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag4 = FValue.trim();
			}
			else
				StandbyFlag4 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		DistriControlSchema other = (DistriControlSchema)otherObject;
		return
			GrpPolno.equals(other.getGrpPolno())
			&& GrpContno.equals(other.getGrpContno())
			&& Riskcode.equals(other.getRiskcode())
			&& DistriRate == other.getDistriRate()
			&& FeeRate == other.getFeeRate()
			&& Managecom.equals(other.getManagecom())
			&& Fee == other.getFee()
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& Remark.equals(other.getRemark())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
			&& StandbyFlag4.equals(other.getStandbyFlag4());
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
		if( strFieldName.equals("GrpPolno") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContno") ) {
			return 1;
		}
		if( strFieldName.equals("Riskcode") ) {
			return 2;
		}
		if( strFieldName.equals("DistriRate") ) {
			return 3;
		}
		if( strFieldName.equals("FeeRate") ) {
			return 4;
		}
		if( strFieldName.equals("Managecom") ) {
			return 5;
		}
		if( strFieldName.equals("Fee") ) {
			return 6;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 7;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 8;
		}
		if( strFieldName.equals("Remark") ) {
			return 9;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 10;
		}
		if( strFieldName.equals("StandbyFlag4") ) {
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
				strFieldName = "GrpPolno";
				break;
			case 1:
				strFieldName = "GrpContno";
				break;
			case 2:
				strFieldName = "Riskcode";
				break;
			case 3:
				strFieldName = "DistriRate";
				break;
			case 4:
				strFieldName = "FeeRate";
				break;
			case 5:
				strFieldName = "Managecom";
				break;
			case 6:
				strFieldName = "Fee";
				break;
			case 7:
				strFieldName = "StandbyFlag1";
				break;
			case 8:
				strFieldName = "StandbyFlag2";
				break;
			case 9:
				strFieldName = "Remark";
				break;
			case 10:
				strFieldName = "StandbyFlag3";
				break;
			case 11:
				strFieldName = "StandbyFlag4";
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
		if( strFieldName.equals("GrpPolno") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContno") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Riskcode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DistriRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Managecom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag4") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
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
