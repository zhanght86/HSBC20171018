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
import com.sinosoft.lis.db.LDOccupationDB;

/*
 * <p>ClassName: LDOccupationSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDOccupationSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDOccupationSchema.class);
	// @Field
	/** 工种代码 */
	private String OccupationCode;
	/** 工种名称 */
	private String OccupationName;
	/** 职业类别 */
	private String OccupationType;
	/** 行业代码 */
	private String WorkType;
	/** 行业名称 */
	private String WorkName;
	/** 医疗险加费比例 */
	private double MedRate;
	/** 意外风险 */
	private String SuddRisk;
	/** 重疾风险 */
	private String DiseaRisk;
	/** 住院风险 */
	private String HosipRisk;
	/** 职业代码版本 */
	private String OccupationVer;
	/** 职业大类编码 */
	private String OccupBigCode;
	/** 职业中类编码 */
	private String OccupMidCode;
	/** 职业层级 */
	private String OccupLevel;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDOccupationSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "OccupationCode";

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
		LDOccupationSchema cloned = (LDOccupationSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getOccupationCode()
	{
		return OccupationCode;
	}
	public void setOccupationCode(String aOccupationCode)
	{
		if(aOccupationCode!=null && aOccupationCode.length()>10)
			throw new IllegalArgumentException("工种代码OccupationCode值"+aOccupationCode+"的长度"+aOccupationCode.length()+"大于最大值10");
		OccupationCode = aOccupationCode;
	}
	public String getOccupationName()
	{
		return OccupationName;
	}
	public void setOccupationName(String aOccupationName)
	{
		if(aOccupationName!=null && aOccupationName.length()>300)
			throw new IllegalArgumentException("工种名称OccupationName值"+aOccupationName+"的长度"+aOccupationName.length()+"大于最大值300");
		OccupationName = aOccupationName;
	}
	/**
	* 对于正常的职业类别，都采用大于0的数字。<p>
	* 对于非正常的职业类别，都采用小于0的数字。<p>
	* -1 －－ 拒保<p>
	* -2 －－ 特殊费率
	*/
	public String getOccupationType()
	{
		return OccupationType;
	}
	public void setOccupationType(String aOccupationType)
	{
		if(aOccupationType!=null && aOccupationType.length()>10)
			throw new IllegalArgumentException("职业类别OccupationType值"+aOccupationType+"的长度"+aOccupationType.length()+"大于最大值10");
		OccupationType = aOccupationType;
	}
	public String getWorkType()
	{
		return WorkType;
	}
	public void setWorkType(String aWorkType)
	{
		if(aWorkType!=null && aWorkType.length()>10)
			throw new IllegalArgumentException("行业代码WorkType值"+aWorkType+"的长度"+aWorkType.length()+"大于最大值10");
		WorkType = aWorkType;
	}
	public String getWorkName()
	{
		return WorkName;
	}
	public void setWorkName(String aWorkName)
	{
		if(aWorkName!=null && aWorkName.length()>300)
			throw new IllegalArgumentException("行业名称WorkName值"+aWorkName+"的长度"+aWorkName.length()+"大于最大值300");
		WorkName = aWorkName;
	}
	/**
	* 对于正常的比例，都采用大于0的数字。<p>
	* 标准费用使用1<p>
	* 对于非正常的比例，都采用小于0的数字。<p>
	* 1 －－ 标准费率<p>
	* -1－－ 拒保<p>
	* -2－－ 未定义
	*/
	public double getMedRate()
	{
		return MedRate;
	}
	public void setMedRate(double aMedRate)
	{
		MedRate = aMedRate;
	}
	public void setMedRate(String aMedRate)
	{
		if (aMedRate != null && !aMedRate.equals(""))
		{
			Double tDouble = new Double(aMedRate);
			double d = tDouble.doubleValue();
			MedRate = d;
		}
	}

	public String getSuddRisk()
	{
		return SuddRisk;
	}
	public void setSuddRisk(String aSuddRisk)
	{
		if(aSuddRisk!=null && aSuddRisk.length()>30)
			throw new IllegalArgumentException("意外风险SuddRisk值"+aSuddRisk+"的长度"+aSuddRisk.length()+"大于最大值30");
		SuddRisk = aSuddRisk;
	}
	public String getDiseaRisk()
	{
		return DiseaRisk;
	}
	public void setDiseaRisk(String aDiseaRisk)
	{
		if(aDiseaRisk!=null && aDiseaRisk.length()>30)
			throw new IllegalArgumentException("重疾风险DiseaRisk值"+aDiseaRisk+"的长度"+aDiseaRisk.length()+"大于最大值30");
		DiseaRisk = aDiseaRisk;
	}
	public String getHosipRisk()
	{
		return HosipRisk;
	}
	public void setHosipRisk(String aHosipRisk)
	{
		if(aHosipRisk!=null && aHosipRisk.length()>30)
			throw new IllegalArgumentException("住院风险HosipRisk值"+aHosipRisk+"的长度"+aHosipRisk.length()+"大于最大值30");
		HosipRisk = aHosipRisk;
	}
	public String getOccupationVer()
	{
		return OccupationVer;
	}
	public void setOccupationVer(String aOccupationVer)
	{
		if(aOccupationVer!=null && aOccupationVer.length()>3)
			throw new IllegalArgumentException("职业代码版本OccupationVer值"+aOccupationVer+"的长度"+aOccupationVer.length()+"大于最大值3");
		OccupationVer = aOccupationVer;
	}
	public String getOccupBigCode()
	{
		return OccupBigCode;
	}
	public void setOccupBigCode(String aOccupBigCode)
	{
		if(aOccupBigCode!=null && aOccupBigCode.length()>10)
			throw new IllegalArgumentException("职业大类编码OccupBigCode值"+aOccupBigCode+"的长度"+aOccupBigCode.length()+"大于最大值10");
		OccupBigCode = aOccupBigCode;
	}
	public String getOccupMidCode()
	{
		return OccupMidCode;
	}
	public void setOccupMidCode(String aOccupMidCode)
	{
		if(aOccupMidCode!=null && aOccupMidCode.length()>10)
			throw new IllegalArgumentException("职业中类编码OccupMidCode值"+aOccupMidCode+"的长度"+aOccupMidCode.length()+"大于最大值10");
		OccupMidCode = aOccupMidCode;
	}
	/**
	* 职业层级：1-大类，2-中类，3-工种
	*/
	public String getOccupLevel()
	{
		return OccupLevel;
	}
	public void setOccupLevel(String aOccupLevel)
	{
		if(aOccupLevel!=null && aOccupLevel.length()>2)
			throw new IllegalArgumentException("职业层级OccupLevel值"+aOccupLevel+"的长度"+aOccupLevel.length()+"大于最大值2");
		OccupLevel = aOccupLevel;
	}

	/**
	* 使用另外一个 LDOccupationSchema 对象给 Schema 赋值
	* @param: aLDOccupationSchema LDOccupationSchema
	**/
	public void setSchema(LDOccupationSchema aLDOccupationSchema)
	{
		this.OccupationCode = aLDOccupationSchema.getOccupationCode();
		this.OccupationName = aLDOccupationSchema.getOccupationName();
		this.OccupationType = aLDOccupationSchema.getOccupationType();
		this.WorkType = aLDOccupationSchema.getWorkType();
		this.WorkName = aLDOccupationSchema.getWorkName();
		this.MedRate = aLDOccupationSchema.getMedRate();
		this.SuddRisk = aLDOccupationSchema.getSuddRisk();
		this.DiseaRisk = aLDOccupationSchema.getDiseaRisk();
		this.HosipRisk = aLDOccupationSchema.getHosipRisk();
		this.OccupationVer = aLDOccupationSchema.getOccupationVer();
		this.OccupBigCode = aLDOccupationSchema.getOccupBigCode();
		this.OccupMidCode = aLDOccupationSchema.getOccupMidCode();
		this.OccupLevel = aLDOccupationSchema.getOccupLevel();
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
			if( rs.getString("OccupationCode") == null )
				this.OccupationCode = null;
			else
				this.OccupationCode = rs.getString("OccupationCode").trim();

			if( rs.getString("OccupationName") == null )
				this.OccupationName = null;
			else
				this.OccupationName = rs.getString("OccupationName").trim();

			if( rs.getString("OccupationType") == null )
				this.OccupationType = null;
			else
				this.OccupationType = rs.getString("OccupationType").trim();

			if( rs.getString("WorkType") == null )
				this.WorkType = null;
			else
				this.WorkType = rs.getString("WorkType").trim();

			if( rs.getString("WorkName") == null )
				this.WorkName = null;
			else
				this.WorkName = rs.getString("WorkName").trim();

			this.MedRate = rs.getDouble("MedRate");
			if( rs.getString("SuddRisk") == null )
				this.SuddRisk = null;
			else
				this.SuddRisk = rs.getString("SuddRisk").trim();

			if( rs.getString("DiseaRisk") == null )
				this.DiseaRisk = null;
			else
				this.DiseaRisk = rs.getString("DiseaRisk").trim();

			if( rs.getString("HosipRisk") == null )
				this.HosipRisk = null;
			else
				this.HosipRisk = rs.getString("HosipRisk").trim();

			if( rs.getString("OccupationVer") == null )
				this.OccupationVer = null;
			else
				this.OccupationVer = rs.getString("OccupationVer").trim();

			if( rs.getString("OccupBigCode") == null )
				this.OccupBigCode = null;
			else
				this.OccupBigCode = rs.getString("OccupBigCode").trim();

			if( rs.getString("OccupMidCode") == null )
				this.OccupMidCode = null;
			else
				this.OccupMidCode = rs.getString("OccupMidCode").trim();

			if( rs.getString("OccupLevel") == null )
				this.OccupLevel = null;
			else
				this.OccupLevel = rs.getString("OccupLevel").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDOccupation表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDOccupationSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDOccupationSchema getSchema()
	{
		LDOccupationSchema aLDOccupationSchema = new LDOccupationSchema();
		aLDOccupationSchema.setSchema(this);
		return aLDOccupationSchema;
	}

	public LDOccupationDB getDB()
	{
		LDOccupationDB aDBOper = new LDOccupationDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDOccupation描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(OccupationCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MedRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SuddRisk)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseaRisk)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HosipRisk)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupBigCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupMidCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupLevel));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDOccupation>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			OccupationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			OccupationName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			WorkType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			WorkName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MedRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			SuddRisk = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DiseaRisk = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			HosipRisk = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			OccupationVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			OccupBigCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			OccupMidCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			OccupLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDOccupationSchema";
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
		if (FCode.equalsIgnoreCase("OccupationCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationCode));
		}
		if (FCode.equalsIgnoreCase("OccupationName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationName));
		}
		if (FCode.equalsIgnoreCase("OccupationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationType));
		}
		if (FCode.equalsIgnoreCase("WorkType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkType));
		}
		if (FCode.equalsIgnoreCase("WorkName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkName));
		}
		if (FCode.equalsIgnoreCase("MedRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MedRate));
		}
		if (FCode.equalsIgnoreCase("SuddRisk"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SuddRisk));
		}
		if (FCode.equalsIgnoreCase("DiseaRisk"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiseaRisk));
		}
		if (FCode.equalsIgnoreCase("HosipRisk"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HosipRisk));
		}
		if (FCode.equalsIgnoreCase("OccupationVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationVer));
		}
		if (FCode.equalsIgnoreCase("OccupBigCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupBigCode));
		}
		if (FCode.equalsIgnoreCase("OccupMidCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupMidCode));
		}
		if (FCode.equalsIgnoreCase("OccupLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupLevel));
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
				strFieldValue = StrTool.GBKToUnicode(OccupationCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(OccupationName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(WorkType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(WorkName);
				break;
			case 5:
				strFieldValue = String.valueOf(MedRate);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SuddRisk);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DiseaRisk);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(HosipRisk);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(OccupationVer);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(OccupBigCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(OccupMidCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(OccupLevel);
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

		if (FCode.equalsIgnoreCase("OccupationCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupationCode = FValue.trim();
			}
			else
				OccupationCode = null;
		}
		if (FCode.equalsIgnoreCase("OccupationName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupationName = FValue.trim();
			}
			else
				OccupationName = null;
		}
		if (FCode.equalsIgnoreCase("OccupationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupationType = FValue.trim();
			}
			else
				OccupationType = null;
		}
		if (FCode.equalsIgnoreCase("WorkType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkType = FValue.trim();
			}
			else
				WorkType = null;
		}
		if (FCode.equalsIgnoreCase("WorkName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkName = FValue.trim();
			}
			else
				WorkName = null;
		}
		if (FCode.equalsIgnoreCase("MedRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MedRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("SuddRisk"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SuddRisk = FValue.trim();
			}
			else
				SuddRisk = null;
		}
		if (FCode.equalsIgnoreCase("DiseaRisk"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiseaRisk = FValue.trim();
			}
			else
				DiseaRisk = null;
		}
		if (FCode.equalsIgnoreCase("HosipRisk"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HosipRisk = FValue.trim();
			}
			else
				HosipRisk = null;
		}
		if (FCode.equalsIgnoreCase("OccupationVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupationVer = FValue.trim();
			}
			else
				OccupationVer = null;
		}
		if (FCode.equalsIgnoreCase("OccupBigCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupBigCode = FValue.trim();
			}
			else
				OccupBigCode = null;
		}
		if (FCode.equalsIgnoreCase("OccupMidCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupMidCode = FValue.trim();
			}
			else
				OccupMidCode = null;
		}
		if (FCode.equalsIgnoreCase("OccupLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupLevel = FValue.trim();
			}
			else
				OccupLevel = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDOccupationSchema other = (LDOccupationSchema)otherObject;
		return
			OccupationCode.equals(other.getOccupationCode())
			&& OccupationName.equals(other.getOccupationName())
			&& OccupationType.equals(other.getOccupationType())
			&& WorkType.equals(other.getWorkType())
			&& WorkName.equals(other.getWorkName())
			&& MedRate == other.getMedRate()
			&& SuddRisk.equals(other.getSuddRisk())
			&& DiseaRisk.equals(other.getDiseaRisk())
			&& HosipRisk.equals(other.getHosipRisk())
			&& OccupationVer.equals(other.getOccupationVer())
			&& OccupBigCode.equals(other.getOccupBigCode())
			&& OccupMidCode.equals(other.getOccupMidCode())
			&& OccupLevel.equals(other.getOccupLevel());
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
		if( strFieldName.equals("OccupationCode") ) {
			return 0;
		}
		if( strFieldName.equals("OccupationName") ) {
			return 1;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 2;
		}
		if( strFieldName.equals("WorkType") ) {
			return 3;
		}
		if( strFieldName.equals("WorkName") ) {
			return 4;
		}
		if( strFieldName.equals("MedRate") ) {
			return 5;
		}
		if( strFieldName.equals("SuddRisk") ) {
			return 6;
		}
		if( strFieldName.equals("DiseaRisk") ) {
			return 7;
		}
		if( strFieldName.equals("HosipRisk") ) {
			return 8;
		}
		if( strFieldName.equals("OccupationVer") ) {
			return 9;
		}
		if( strFieldName.equals("OccupBigCode") ) {
			return 10;
		}
		if( strFieldName.equals("OccupMidCode") ) {
			return 11;
		}
		if( strFieldName.equals("OccupLevel") ) {
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
				strFieldName = "OccupationCode";
				break;
			case 1:
				strFieldName = "OccupationName";
				break;
			case 2:
				strFieldName = "OccupationType";
				break;
			case 3:
				strFieldName = "WorkType";
				break;
			case 4:
				strFieldName = "WorkName";
				break;
			case 5:
				strFieldName = "MedRate";
				break;
			case 6:
				strFieldName = "SuddRisk";
				break;
			case 7:
				strFieldName = "DiseaRisk";
				break;
			case 8:
				strFieldName = "HosipRisk";
				break;
			case 9:
				strFieldName = "OccupationVer";
				break;
			case 10:
				strFieldName = "OccupBigCode";
				break;
			case 11:
				strFieldName = "OccupMidCode";
				break;
			case 12:
				strFieldName = "OccupLevel";
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
		if( strFieldName.equals("OccupationCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupationName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WorkType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WorkName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MedRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SuddRisk") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiseaRisk") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HosipRisk") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupationVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupBigCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupMidCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupLevel") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
