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
import com.sinosoft.lis.db.LLInqCourseDB;

/*
 * <p>ClassName: LLInqCourseSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLInqCourseSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLInqCourseSchema.class);

	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 调查序号 */
	private String InqNo;
	/** 过程序号 */
	private String CouNo;
	/** 调查日期 */
	private Date InqDate;
	/** 调查方式 */
	private String InqMode;
	/** 调查地点 */
	private String InqSite;
	/** 被调查人 */
	private String InqByPer;
	/** 调查过程 */
	private String InqCourse;
	/** 调查机构 */
	private String InqDept;
	/** 第一调查人 */
	private String InqPer1;
	/** 第二调查人 */
	private String InqPer2;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLInqCourseSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ClmNo";
		pk[1] = "InqNo";
		pk[2] = "CouNo";

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
		LLInqCourseSchema cloned = (LLInqCourseSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		ClmNo = aClmNo;
	}
	public String getInqNo()
	{
		return InqNo;
	}
	public void setInqNo(String aInqNo)
	{
		InqNo = aInqNo;
	}
	public String getCouNo()
	{
		return CouNo;
	}
	public void setCouNo(String aCouNo)
	{
		CouNo = aCouNo;
	}
	public String getInqDate()
	{
		if( InqDate != null )
			return fDate.getString(InqDate);
		else
			return null;
	}
	public void setInqDate(Date aInqDate)
	{
		InqDate = aInqDate;
	}
	public void setInqDate(String aInqDate)
	{
		if (aInqDate != null && !aInqDate.equals("") )
		{
			InqDate = fDate.getDate( aInqDate );
		}
		else
			InqDate = null;
	}

	/**
	* 01 	现场查勘<p>
	* 02 	面谈<p>
	* 03 	电话<p>
	* 04 	信函<p>
	* 05 	邮件
	*/
	public String getInqMode()
	{
		return InqMode;
	}
	public void setInqMode(String aInqMode)
	{
		InqMode = aInqMode;
	}
	public String getInqSite()
	{
		return InqSite;
	}
	public void setInqSite(String aInqSite)
	{
		InqSite = aInqSite;
	}
	public String getInqByPer()
	{
		return InqByPer;
	}
	public void setInqByPer(String aInqByPer)
	{
		InqByPer = aInqByPer;
	}
	public String getInqCourse()
	{
		return InqCourse;
	}
	public void setInqCourse(String aInqCourse)
	{
		InqCourse = aInqCourse;
	}
	public String getInqDept()
	{
		return InqDept;
	}
	public void setInqDept(String aInqDept)
	{
		InqDept = aInqDept;
	}
	public String getInqPer1()
	{
		return InqPer1;
	}
	public void setInqPer1(String aInqPer1)
	{
		InqPer1 = aInqPer1;
	}
	public String getInqPer2()
	{
		return InqPer2;
	}
	public void setInqPer2(String aInqPer2)
	{
		InqPer2 = aInqPer2;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
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
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LLInqCourseSchema 对象给 Schema 赋值
	* @param: aLLInqCourseSchema LLInqCourseSchema
	**/
	public void setSchema(LLInqCourseSchema aLLInqCourseSchema)
	{
		this.ClmNo = aLLInqCourseSchema.getClmNo();
		this.InqNo = aLLInqCourseSchema.getInqNo();
		this.CouNo = aLLInqCourseSchema.getCouNo();
		this.InqDate = fDate.getDate( aLLInqCourseSchema.getInqDate());
		this.InqMode = aLLInqCourseSchema.getInqMode();
		this.InqSite = aLLInqCourseSchema.getInqSite();
		this.InqByPer = aLLInqCourseSchema.getInqByPer();
		this.InqCourse = aLLInqCourseSchema.getInqCourse();
		this.InqDept = aLLInqCourseSchema.getInqDept();
		this.InqPer1 = aLLInqCourseSchema.getInqPer1();
		this.InqPer2 = aLLInqCourseSchema.getInqPer2();
		this.Operator = aLLInqCourseSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLInqCourseSchema.getMakeDate());
		this.MakeTime = aLLInqCourseSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLInqCourseSchema.getModifyDate());
		this.ModifyTime = aLLInqCourseSchema.getModifyTime();
		this.Remark = aLLInqCourseSchema.getRemark();
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
			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("InqNo") == null )
				this.InqNo = null;
			else
				this.InqNo = rs.getString("InqNo").trim();

			if( rs.getString("CouNo") == null )
				this.CouNo = null;
			else
				this.CouNo = rs.getString("CouNo").trim();

			this.InqDate = rs.getDate("InqDate");
			if( rs.getString("InqMode") == null )
				this.InqMode = null;
			else
				this.InqMode = rs.getString("InqMode").trim();

			if( rs.getString("InqSite") == null )
				this.InqSite = null;
			else
				this.InqSite = rs.getString("InqSite").trim();

			if( rs.getString("InqByPer") == null )
				this.InqByPer = null;
			else
				this.InqByPer = rs.getString("InqByPer").trim();

			if( rs.getString("InqCourse") == null )
				this.InqCourse = null;
			else
				this.InqCourse = rs.getString("InqCourse").trim();

			if( rs.getString("InqDept") == null )
				this.InqDept = null;
			else
				this.InqDept = rs.getString("InqDept").trim();

			if( rs.getString("InqPer1") == null )
				this.InqPer1 = null;
			else
				this.InqPer1 = rs.getString("InqPer1").trim();

			if( rs.getString("InqPer2") == null )
				this.InqPer2 = null;
			else
				this.InqPer2 = rs.getString("InqPer2").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLInqCourse表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqCourseSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLInqCourseSchema getSchema()
	{
		LLInqCourseSchema aLLInqCourseSchema = new LLInqCourseSchema();
		aLLInqCourseSchema.setSchema(this);
		return aLLInqCourseSchema;
	}

	public LLInqCourseDB getDB()
	{
		LLInqCourseDB aDBOper = new LLInqCourseDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLInqCourse描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CouNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InqDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqSite)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqByPer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqCourse)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqDept)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqPer1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqPer2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLInqCourse>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InqNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CouNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InqDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			InqMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InqSite = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InqByPer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InqCourse = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			InqDept = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			InqPer1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			InqPer2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqCourseSchema";
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
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("InqNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqNo));
		}
		if (FCode.equalsIgnoreCase("CouNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CouNo));
		}
		if (FCode.equalsIgnoreCase("InqDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInqDate()));
		}
		if (FCode.equalsIgnoreCase("InqMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqMode));
		}
		if (FCode.equalsIgnoreCase("InqSite"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqSite));
		}
		if (FCode.equalsIgnoreCase("InqByPer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqByPer));
		}
		if (FCode.equalsIgnoreCase("InqCourse"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqCourse));
		}
		if (FCode.equalsIgnoreCase("InqDept"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqDept));
		}
		if (FCode.equalsIgnoreCase("InqPer1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqPer1));
		}
		if (FCode.equalsIgnoreCase("InqPer2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqPer2));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
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
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(InqNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CouNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInqDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(InqMode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InqSite);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InqByPer);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InqCourse);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(InqDept);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(InqPer1);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(InqPer2);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 16:
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

		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("InqNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqNo = FValue.trim();
			}
			else
				InqNo = null;
		}
		if (FCode.equalsIgnoreCase("CouNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CouNo = FValue.trim();
			}
			else
				CouNo = null;
		}
		if (FCode.equalsIgnoreCase("InqDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InqDate = fDate.getDate( FValue );
			}
			else
				InqDate = null;
		}
		if (FCode.equalsIgnoreCase("InqMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqMode = FValue.trim();
			}
			else
				InqMode = null;
		}
		if (FCode.equalsIgnoreCase("InqSite"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqSite = FValue.trim();
			}
			else
				InqSite = null;
		}
		if (FCode.equalsIgnoreCase("InqByPer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqByPer = FValue.trim();
			}
			else
				InqByPer = null;
		}
		if (FCode.equalsIgnoreCase("InqCourse"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqCourse = FValue.trim();
			}
			else
				InqCourse = null;
		}
		if (FCode.equalsIgnoreCase("InqDept"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqDept = FValue.trim();
			}
			else
				InqDept = null;
		}
		if (FCode.equalsIgnoreCase("InqPer1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqPer1 = FValue.trim();
			}
			else
				InqPer1 = null;
		}
		if (FCode.equalsIgnoreCase("InqPer2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqPer2 = FValue.trim();
			}
			else
				InqPer2 = null;
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
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
		LLInqCourseSchema other = (LLInqCourseSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& InqNo.equals(other.getInqNo())
			&& CouNo.equals(other.getCouNo())
			&& fDate.getString(InqDate).equals(other.getInqDate())
			&& InqMode.equals(other.getInqMode())
			&& InqSite.equals(other.getInqSite())
			&& InqByPer.equals(other.getInqByPer())
			&& InqCourse.equals(other.getInqCourse())
			&& InqDept.equals(other.getInqDept())
			&& InqPer1.equals(other.getInqPer1())
			&& InqPer2.equals(other.getInqPer2())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
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
		if( strFieldName.equals("ClmNo") ) {
			return 0;
		}
		if( strFieldName.equals("InqNo") ) {
			return 1;
		}
		if( strFieldName.equals("CouNo") ) {
			return 2;
		}
		if( strFieldName.equals("InqDate") ) {
			return 3;
		}
		if( strFieldName.equals("InqMode") ) {
			return 4;
		}
		if( strFieldName.equals("InqSite") ) {
			return 5;
		}
		if( strFieldName.equals("InqByPer") ) {
			return 6;
		}
		if( strFieldName.equals("InqCourse") ) {
			return 7;
		}
		if( strFieldName.equals("InqDept") ) {
			return 8;
		}
		if( strFieldName.equals("InqPer1") ) {
			return 9;
		}
		if( strFieldName.equals("InqPer2") ) {
			return 10;
		}
		if( strFieldName.equals("Operator") ) {
			return 11;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 12;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 15;
		}
		if( strFieldName.equals("Remark") ) {
			return 16;
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "InqNo";
				break;
			case 2:
				strFieldName = "CouNo";
				break;
			case 3:
				strFieldName = "InqDate";
				break;
			case 4:
				strFieldName = "InqMode";
				break;
			case 5:
				strFieldName = "InqSite";
				break;
			case 6:
				strFieldName = "InqByPer";
				break;
			case 7:
				strFieldName = "InqCourse";
				break;
			case 8:
				strFieldName = "InqDept";
				break;
			case 9:
				strFieldName = "InqPer1";
				break;
			case 10:
				strFieldName = "InqPer2";
				break;
			case 11:
				strFieldName = "Operator";
				break;
			case 12:
				strFieldName = "MakeDate";
				break;
			case 13:
				strFieldName = "MakeTime";
				break;
			case 14:
				strFieldName = "ModifyDate";
				break;
			case 15:
				strFieldName = "ModifyTime";
				break;
			case 16:
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
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CouNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InqMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqSite") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqByPer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqCourse") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqDept") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqPer1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqPer2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
