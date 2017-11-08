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
import com.sinosoft.lis.db.LNewAssessRadixDB;

/*
 * <p>ClassName: LNewAssessRadixSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LNewAssessRadixSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LNewAssessRadixSchema.class);

	// @Field
	/** 流水号 */
	private String RadixNo;
	/** 代理人职级 */
	private String AgentGrade;
	/** 考核类型 */
	private String AssessType;
	/** 地区类型 */
	private String AreaType;
	/** 展业类型 */
	private String BranchType;
	/** 是否降级属性 */
	private String Flag1;
	/** 是否同业衔接人员 */
	private String Flag2;
	/** 是否由同业衔接人员转正 */
	private String Flag3;
	/** 第几次考核 */
	private String AssessTime;
	/** 考核期限 */
	private int AssessPeriod;
	/** 比较期限 */
	private int ComPeriod;
	/** 考核检察期 */
	private String CheckPeriod;
	/** 下限 */
	private int MinLimit;
	/** 上限 */
	private int MaxLimit;
	/** 备注 */
	private String Noti;

	public static final int FIELDNUM = 15;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LNewAssessRadixSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "RadixNo";
		pk[1] = "AgentGrade";
		pk[2] = "AssessType";
		pk[3] = "AreaType";
		pk[4] = "BranchType";

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
		LNewAssessRadixSchema cloned = (LNewAssessRadixSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRadixNo()
	{
		return RadixNo;
	}
	public void setRadixNo(String aRadixNo)
	{
		RadixNo = aRadixNo;
	}
	public String getAgentGrade()
	{
		return AgentGrade;
	}
	public void setAgentGrade(String aAgentGrade)
	{
		AgentGrade = aAgentGrade;
	}
	/**
	* 01-维持考核<p>
	* 02-晋升考核
	*/
	public String getAssessType()
	{
		return AssessType;
	}
	public void setAssessType(String aAssessType)
	{
		AssessType = aAssessType;
	}
	public String getAreaType()
	{
		return AreaType;
	}
	public void setAreaType(String aAreaType)
	{
		AreaType = aAreaType;
	}
	/**
	* 展业类型(1-个人营销，2-团险，3－银行保险，9－其他)
	*/
	public String getBranchType()
	{
		return BranchType;
	}
	public void setBranchType(String aBranchType)
	{
		BranchType = aBranchType;
	}
	/**
	* 0-非降级人员<p>
	* <p>
	* 1-降级人员
	*/
	public String getFlag1()
	{
		return Flag1;
	}
	public void setFlag1(String aFlag1)
	{
		Flag1 = aFlag1;
	}
	/**
	* 1-是同业衔接人员<p>
	* <p>
	* 0-非同业衔接人员
	*/
	public String getFlag2()
	{
		return Flag2;
	}
	public void setFlag2(String aFlag2)
	{
		Flag2 = aFlag2;
	}
	public String getFlag3()
	{
		return Flag3;
	}
	public void setFlag3(String aFlag3)
	{
		Flag3 = aFlag3;
	}
	/**
	* 0-不是第一次考核<p>
	* 1-是第一次考核
	*/
	public String getAssessTime()
	{
		return AssessTime;
	}
	public void setAssessTime(String aAssessTime)
	{
		AssessTime = aAssessTime;
	}
	/**
	* 在此存放计算统计起期时前推的期限
	*/
	public int getAssessPeriod()
	{
		return AssessPeriod;
	}
	public void setAssessPeriod(int aAssessPeriod)
	{
		AssessPeriod = aAssessPeriod;
	}
	public void setAssessPeriod(String aAssessPeriod)
	{
		if (aAssessPeriod != null && !aAssessPeriod.equals(""))
		{
			Integer tInteger = new Integer(aAssessPeriod);
			int i = tInteger.intValue();
			AssessPeriod = i;
		}
	}

	public int getComPeriod()
	{
		return ComPeriod;
	}
	public void setComPeriod(int aComPeriod)
	{
		ComPeriod = aComPeriod;
	}
	public void setComPeriod(String aComPeriod)
	{
		if (aComPeriod != null && !aComPeriod.equals(""))
		{
			Integer tInteger = new Integer(aComPeriod);
			int i = tInteger.intValue();
			ComPeriod = i;
		}
	}

	public String getCheckPeriod()
	{
		return CheckPeriod;
	}
	public void setCheckPeriod(String aCheckPeriod)
	{
		CheckPeriod = aCheckPeriod;
	}
	public int getMinLimit()
	{
		return MinLimit;
	}
	public void setMinLimit(int aMinLimit)
	{
		MinLimit = aMinLimit;
	}
	public void setMinLimit(String aMinLimit)
	{
		if (aMinLimit != null && !aMinLimit.equals(""))
		{
			Integer tInteger = new Integer(aMinLimit);
			int i = tInteger.intValue();
			MinLimit = i;
		}
	}

	public int getMaxLimit()
	{
		return MaxLimit;
	}
	public void setMaxLimit(int aMaxLimit)
	{
		MaxLimit = aMaxLimit;
	}
	public void setMaxLimit(String aMaxLimit)
	{
		if (aMaxLimit != null && !aMaxLimit.equals(""))
		{
			Integer tInteger = new Integer(aMaxLimit);
			int i = tInteger.intValue();
			MaxLimit = i;
		}
	}

	public String getNoti()
	{
		return Noti;
	}
	public void setNoti(String aNoti)
	{
		Noti = aNoti;
	}

	/**
	* 使用另外一个 LNewAssessRadixSchema 对象给 Schema 赋值
	* @param: aLNewAssessRadixSchema LNewAssessRadixSchema
	**/
	public void setSchema(LNewAssessRadixSchema aLNewAssessRadixSchema)
	{
		this.RadixNo = aLNewAssessRadixSchema.getRadixNo();
		this.AgentGrade = aLNewAssessRadixSchema.getAgentGrade();
		this.AssessType = aLNewAssessRadixSchema.getAssessType();
		this.AreaType = aLNewAssessRadixSchema.getAreaType();
		this.BranchType = aLNewAssessRadixSchema.getBranchType();
		this.Flag1 = aLNewAssessRadixSchema.getFlag1();
		this.Flag2 = aLNewAssessRadixSchema.getFlag2();
		this.Flag3 = aLNewAssessRadixSchema.getFlag3();
		this.AssessTime = aLNewAssessRadixSchema.getAssessTime();
		this.AssessPeriod = aLNewAssessRadixSchema.getAssessPeriod();
		this.ComPeriod = aLNewAssessRadixSchema.getComPeriod();
		this.CheckPeriod = aLNewAssessRadixSchema.getCheckPeriod();
		this.MinLimit = aLNewAssessRadixSchema.getMinLimit();
		this.MaxLimit = aLNewAssessRadixSchema.getMaxLimit();
		this.Noti = aLNewAssessRadixSchema.getNoti();
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
			if( rs.getString("RadixNo") == null )
				this.RadixNo = null;
			else
				this.RadixNo = rs.getString("RadixNo").trim();

			if( rs.getString("AgentGrade") == null )
				this.AgentGrade = null;
			else
				this.AgentGrade = rs.getString("AgentGrade").trim();

			if( rs.getString("AssessType") == null )
				this.AssessType = null;
			else
				this.AssessType = rs.getString("AssessType").trim();

			if( rs.getString("AreaType") == null )
				this.AreaType = null;
			else
				this.AreaType = rs.getString("AreaType").trim();

			if( rs.getString("BranchType") == null )
				this.BranchType = null;
			else
				this.BranchType = rs.getString("BranchType").trim();

			if( rs.getString("Flag1") == null )
				this.Flag1 = null;
			else
				this.Flag1 = rs.getString("Flag1").trim();

			if( rs.getString("Flag2") == null )
				this.Flag2 = null;
			else
				this.Flag2 = rs.getString("Flag2").trim();

			if( rs.getString("Flag3") == null )
				this.Flag3 = null;
			else
				this.Flag3 = rs.getString("Flag3").trim();

			if( rs.getString("AssessTime") == null )
				this.AssessTime = null;
			else
				this.AssessTime = rs.getString("AssessTime").trim();

			this.AssessPeriod = rs.getInt("AssessPeriod");
			this.ComPeriod = rs.getInt("ComPeriod");
			if( rs.getString("CheckPeriod") == null )
				this.CheckPeriod = null;
			else
				this.CheckPeriod = rs.getString("CheckPeriod").trim();

			this.MinLimit = rs.getInt("MinLimit");
			this.MaxLimit = rs.getInt("MaxLimit");
			if( rs.getString("Noti") == null )
				this.Noti = null;
			else
				this.Noti = rs.getString("Noti").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LNewAssessRadix表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LNewAssessRadixSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LNewAssessRadixSchema getSchema()
	{
		LNewAssessRadixSchema aLNewAssessRadixSchema = new LNewAssessRadixSchema();
		aLNewAssessRadixSchema.setSchema(this);
		return aLNewAssessRadixSchema;
	}

	public LNewAssessRadixDB getDB()
	{
		LNewAssessRadixDB aDBOper = new LNewAssessRadixDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLNewAssessRadix描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RadixNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssessType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AreaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Flag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Flag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Flag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssessTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AssessPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ComPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckPeriod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MinLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Noti));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLNewAssessRadix>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RadixNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AgentGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AssessType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AreaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Flag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Flag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Flag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AssessTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AssessPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			ComPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			CheckPeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MinLimit= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			MaxLimit= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			Noti = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LNewAssessRadixSchema";
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
		if (FCode.equalsIgnoreCase("RadixNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RadixNo));
		}
		if (FCode.equalsIgnoreCase("AgentGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGrade));
		}
		if (FCode.equalsIgnoreCase("AssessType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssessType));
		}
		if (FCode.equalsIgnoreCase("AreaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AreaType));
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchType));
		}
		if (FCode.equalsIgnoreCase("Flag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Flag1));
		}
		if (FCode.equalsIgnoreCase("Flag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Flag2));
		}
		if (FCode.equalsIgnoreCase("Flag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Flag3));
		}
		if (FCode.equalsIgnoreCase("AssessTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssessTime));
		}
		if (FCode.equalsIgnoreCase("AssessPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssessPeriod));
		}
		if (FCode.equalsIgnoreCase("ComPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComPeriod));
		}
		if (FCode.equalsIgnoreCase("CheckPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckPeriod));
		}
		if (FCode.equalsIgnoreCase("MinLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinLimit));
		}
		if (FCode.equalsIgnoreCase("MaxLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxLimit));
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Noti));
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
				strFieldValue = StrTool.GBKToUnicode(RadixNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AgentGrade);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AssessType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AreaType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BranchType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Flag1);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Flag2);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Flag3);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AssessTime);
				break;
			case 9:
				strFieldValue = String.valueOf(AssessPeriod);
				break;
			case 10:
				strFieldValue = String.valueOf(ComPeriod);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CheckPeriod);
				break;
			case 12:
				strFieldValue = String.valueOf(MinLimit);
				break;
			case 13:
				strFieldValue = String.valueOf(MaxLimit);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Noti);
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

		if (FCode.equalsIgnoreCase("RadixNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RadixNo = FValue.trim();
			}
			else
				RadixNo = null;
		}
		if (FCode.equalsIgnoreCase("AgentGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGrade = FValue.trim();
			}
			else
				AgentGrade = null;
		}
		if (FCode.equalsIgnoreCase("AssessType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssessType = FValue.trim();
			}
			else
				AssessType = null;
		}
		if (FCode.equalsIgnoreCase("AreaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AreaType = FValue.trim();
			}
			else
				AreaType = null;
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchType = FValue.trim();
			}
			else
				BranchType = null;
		}
		if (FCode.equalsIgnoreCase("Flag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Flag1 = FValue.trim();
			}
			else
				Flag1 = null;
		}
		if (FCode.equalsIgnoreCase("Flag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Flag2 = FValue.trim();
			}
			else
				Flag2 = null;
		}
		if (FCode.equalsIgnoreCase("Flag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Flag3 = FValue.trim();
			}
			else
				Flag3 = null;
		}
		if (FCode.equalsIgnoreCase("AssessTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssessTime = FValue.trim();
			}
			else
				AssessTime = null;
		}
		if (FCode.equalsIgnoreCase("AssessPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AssessPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("ComPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ComPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("CheckPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckPeriod = FValue.trim();
			}
			else
				CheckPeriod = null;
		}
		if (FCode.equalsIgnoreCase("MinLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MinLimit = i;
			}
		}
		if (FCode.equalsIgnoreCase("MaxLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaxLimit = i;
			}
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Noti = FValue.trim();
			}
			else
				Noti = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LNewAssessRadixSchema other = (LNewAssessRadixSchema)otherObject;
		return
			RadixNo.equals(other.getRadixNo())
			&& AgentGrade.equals(other.getAgentGrade())
			&& AssessType.equals(other.getAssessType())
			&& AreaType.equals(other.getAreaType())
			&& BranchType.equals(other.getBranchType())
			&& Flag1.equals(other.getFlag1())
			&& Flag2.equals(other.getFlag2())
			&& Flag3.equals(other.getFlag3())
			&& AssessTime.equals(other.getAssessTime())
			&& AssessPeriod == other.getAssessPeriod()
			&& ComPeriod == other.getComPeriod()
			&& CheckPeriod.equals(other.getCheckPeriod())
			&& MinLimit == other.getMinLimit()
			&& MaxLimit == other.getMaxLimit()
			&& Noti.equals(other.getNoti());
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
		if( strFieldName.equals("RadixNo") ) {
			return 0;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return 1;
		}
		if( strFieldName.equals("AssessType") ) {
			return 2;
		}
		if( strFieldName.equals("AreaType") ) {
			return 3;
		}
		if( strFieldName.equals("BranchType") ) {
			return 4;
		}
		if( strFieldName.equals("Flag1") ) {
			return 5;
		}
		if( strFieldName.equals("Flag2") ) {
			return 6;
		}
		if( strFieldName.equals("Flag3") ) {
			return 7;
		}
		if( strFieldName.equals("AssessTime") ) {
			return 8;
		}
		if( strFieldName.equals("AssessPeriod") ) {
			return 9;
		}
		if( strFieldName.equals("ComPeriod") ) {
			return 10;
		}
		if( strFieldName.equals("CheckPeriod") ) {
			return 11;
		}
		if( strFieldName.equals("MinLimit") ) {
			return 12;
		}
		if( strFieldName.equals("MaxLimit") ) {
			return 13;
		}
		if( strFieldName.equals("Noti") ) {
			return 14;
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
				strFieldName = "RadixNo";
				break;
			case 1:
				strFieldName = "AgentGrade";
				break;
			case 2:
				strFieldName = "AssessType";
				break;
			case 3:
				strFieldName = "AreaType";
				break;
			case 4:
				strFieldName = "BranchType";
				break;
			case 5:
				strFieldName = "Flag1";
				break;
			case 6:
				strFieldName = "Flag2";
				break;
			case 7:
				strFieldName = "Flag3";
				break;
			case 8:
				strFieldName = "AssessTime";
				break;
			case 9:
				strFieldName = "AssessPeriod";
				break;
			case 10:
				strFieldName = "ComPeriod";
				break;
			case 11:
				strFieldName = "CheckPeriod";
				break;
			case 12:
				strFieldName = "MinLimit";
				break;
			case 13:
				strFieldName = "MaxLimit";
				break;
			case 14:
				strFieldName = "Noti";
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
		if( strFieldName.equals("RadixNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssessType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AreaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Flag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Flag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Flag3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssessTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssessPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ComPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CheckPeriod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MinLimit") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MaxLimit") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Noti") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_INT;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
