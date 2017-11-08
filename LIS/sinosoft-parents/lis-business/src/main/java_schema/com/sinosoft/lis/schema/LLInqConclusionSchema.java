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
import com.sinosoft.lis.db.LLInqConclusionDB;

/*
 * <p>ClassName: LLInqConclusionSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLInqConclusionSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLInqConclusionSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 结论序号 */
	private String ConNo;
	/** 调查批次 */
	private String BatNo;
	/** 发起机构 */
	private String InitDept;
	/** 调查机构 */
	private String InqDept;
	/** 调查结论 */
	private String InqConclusion;
	/** 完成标志 */
	private String FiniFlag;
	/** 本地标志 */
	private String LocFlag;
	/** 汇总标志 */
	private String ColFlag;
	/** 阳性结论 */
	private String MasFlag;
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

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLInqConclusionSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ClmNo";
		pk[1] = "ConNo";

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
		LLInqConclusionSchema cloned = (LLInqConclusionSchema)super.clone();
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
		if(aClmNo!=null && aClmNo.length()>20)
			throw new IllegalArgumentException("赔案号ClmNo值"+aClmNo+"的长度"+aClmNo.length()+"大于最大值20");
		ClmNo = aClmNo;
	}
	public String getConNo()
	{
		return ConNo;
	}
	public void setConNo(String aConNo)
	{
		if(aConNo!=null && aConNo.length()>20)
			throw new IllegalArgumentException("结论序号ConNo值"+aConNo+"的长度"+aConNo.length()+"大于最大值20");
		ConNo = aConNo;
	}
	public String getBatNo()
	{
		return BatNo;
	}
	public void setBatNo(String aBatNo)
	{
		if(aBatNo!=null && aBatNo.length()>20)
			throw new IllegalArgumentException("调查批次BatNo值"+aBatNo+"的长度"+aBatNo.length()+"大于最大值20");
		BatNo = aBatNo;
	}
	public String getInitDept()
	{
		return InitDept;
	}
	public void setInitDept(String aInitDept)
	{
		if(aInitDept!=null && aInitDept.length()>20)
			throw new IllegalArgumentException("发起机构InitDept值"+aInitDept+"的长度"+aInitDept.length()+"大于最大值20");
		InitDept = aInitDept;
	}
	public String getInqDept()
	{
		return InqDept;
	}
	public void setInqDept(String aInqDept)
	{
		if(aInqDept!=null && aInqDept.length()>20)
			throw new IllegalArgumentException("调查机构InqDept值"+aInqDept+"的长度"+aInqDept.length()+"大于最大值20");
		InqDept = aInqDept;
	}
	public String getInqConclusion()
	{
		return InqConclusion;
	}
	public void setInqConclusion(String aInqConclusion)
	{
		if(aInqConclusion!=null && aInqConclusion.length()>2000)
			throw new IllegalArgumentException("调查结论InqConclusion值"+aInqConclusion+"的长度"+aInqConclusion.length()+"大于最大值2000");
		InqConclusion = aInqConclusion;
	}
	/**
	* 0未完成<p>
	* 1已完成
	*/
	public String getFiniFlag()
	{
		return FiniFlag;
	}
	public void setFiniFlag(String aFiniFlag)
	{
		if(aFiniFlag!=null && aFiniFlag.length()>6)
			throw new IllegalArgumentException("完成标志FiniFlag值"+aFiniFlag+"的长度"+aFiniFlag.length()+"大于最大值6");
		FiniFlag = aFiniFlag;
	}
	/**
	* 是否是本地调查<p>
	* 0本地<p>
	* 1异地
	*/
	public String getLocFlag()
	{
		return LocFlag;
	}
	public void setLocFlag(String aLocFlag)
	{
		if(aLocFlag!=null && aLocFlag.length()>6)
			throw new IllegalArgumentException("本地标志LocFlag值"+aLocFlag+"的长度"+aLocFlag.length()+"大于最大值6");
		LocFlag = aLocFlag;
	}
	/**
	* 确定是否是最后的汇总结论<p>
	* 0-总结论<p>
	* 1-批次结论<p>
	* 2-调查机构结论
	*/
	public String getColFlag()
	{
		return ColFlag;
	}
	public void setColFlag(String aColFlag)
	{
		if(aColFlag!=null && aColFlag.length()>6)
			throw new IllegalArgumentException("汇总标志ColFlag值"+aColFlag+"的长度"+aColFlag.length()+"大于最大值6");
		ColFlag = aColFlag;
	}
	public String getMasFlag()
	{
		return MasFlag;
	}
	public void setMasFlag(String aMasFlag)
	{
		if(aMasFlag!=null && aMasFlag.length()>6)
			throw new IllegalArgumentException("阳性结论MasFlag值"+aMasFlag+"的长度"+aMasFlag.length()+"大于最大值6");
		MasFlag = aMasFlag;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>30)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值30");
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>1000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值1000");
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LLInqConclusionSchema 对象给 Schema 赋值
	* @param: aLLInqConclusionSchema LLInqConclusionSchema
	**/
	public void setSchema(LLInqConclusionSchema aLLInqConclusionSchema)
	{
		this.ClmNo = aLLInqConclusionSchema.getClmNo();
		this.ConNo = aLLInqConclusionSchema.getConNo();
		this.BatNo = aLLInqConclusionSchema.getBatNo();
		this.InitDept = aLLInqConclusionSchema.getInitDept();
		this.InqDept = aLLInqConclusionSchema.getInqDept();
		this.InqConclusion = aLLInqConclusionSchema.getInqConclusion();
		this.FiniFlag = aLLInqConclusionSchema.getFiniFlag();
		this.LocFlag = aLLInqConclusionSchema.getLocFlag();
		this.ColFlag = aLLInqConclusionSchema.getColFlag();
		this.MasFlag = aLLInqConclusionSchema.getMasFlag();
		this.Operator = aLLInqConclusionSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLInqConclusionSchema.getMakeDate());
		this.MakeTime = aLLInqConclusionSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLInqConclusionSchema.getModifyDate());
		this.ModifyTime = aLLInqConclusionSchema.getModifyTime();
		this.Remark = aLLInqConclusionSchema.getRemark();
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

			if( rs.getString("ConNo") == null )
				this.ConNo = null;
			else
				this.ConNo = rs.getString("ConNo").trim();

			if( rs.getString("BatNo") == null )
				this.BatNo = null;
			else
				this.BatNo = rs.getString("BatNo").trim();

			if( rs.getString("InitDept") == null )
				this.InitDept = null;
			else
				this.InitDept = rs.getString("InitDept").trim();

			if( rs.getString("InqDept") == null )
				this.InqDept = null;
			else
				this.InqDept = rs.getString("InqDept").trim();

			if( rs.getString("InqConclusion") == null )
				this.InqConclusion = null;
			else
				this.InqConclusion = rs.getString("InqConclusion").trim();

			if( rs.getString("FiniFlag") == null )
				this.FiniFlag = null;
			else
				this.FiniFlag = rs.getString("FiniFlag").trim();

			if( rs.getString("LocFlag") == null )
				this.LocFlag = null;
			else
				this.LocFlag = rs.getString("LocFlag").trim();

			if( rs.getString("ColFlag") == null )
				this.ColFlag = null;
			else
				this.ColFlag = rs.getString("ColFlag").trim();

			if( rs.getString("MasFlag") == null )
				this.MasFlag = null;
			else
				this.MasFlag = rs.getString("MasFlag").trim();

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
			logger.debug("数据库中的LLInqConclusion表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqConclusionSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLInqConclusionSchema getSchema()
	{
		LLInqConclusionSchema aLLInqConclusionSchema = new LLInqConclusionSchema();
		aLLInqConclusionSchema.setSchema(this);
		return aLLInqConclusionSchema;
	}

	public LLInqConclusionDB getDB()
	{
		LLInqConclusionDB aDBOper = new LLInqConclusionDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLInqConclusion描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InitDept)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqDept)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FiniFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LocFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ColFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MasFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLInqConclusion>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ConNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BatNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InitDept = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			InqDept = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InqConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FiniFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			LocFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ColFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MasFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqConclusionSchema";
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
		if (FCode.equalsIgnoreCase("ConNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConNo));
		}
		if (FCode.equalsIgnoreCase("BatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatNo));
		}
		if (FCode.equalsIgnoreCase("InitDept"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitDept));
		}
		if (FCode.equalsIgnoreCase("InqDept"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqDept));
		}
		if (FCode.equalsIgnoreCase("InqConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqConclusion));
		}
		if (FCode.equalsIgnoreCase("FiniFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FiniFlag));
		}
		if (FCode.equalsIgnoreCase("LocFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LocFlag));
		}
		if (FCode.equalsIgnoreCase("ColFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColFlag));
		}
		if (FCode.equalsIgnoreCase("MasFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MasFlag));
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
				strFieldValue = StrTool.GBKToUnicode(ConNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BatNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InitDept);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(InqDept);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InqConclusion);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FiniFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(LocFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ColFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MasFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 15:
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
		if (FCode.equalsIgnoreCase("ConNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConNo = FValue.trim();
			}
			else
				ConNo = null;
		}
		if (FCode.equalsIgnoreCase("BatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatNo = FValue.trim();
			}
			else
				BatNo = null;
		}
		if (FCode.equalsIgnoreCase("InitDept"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InitDept = FValue.trim();
			}
			else
				InitDept = null;
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
		if (FCode.equalsIgnoreCase("InqConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqConclusion = FValue.trim();
			}
			else
				InqConclusion = null;
		}
		if (FCode.equalsIgnoreCase("FiniFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FiniFlag = FValue.trim();
			}
			else
				FiniFlag = null;
		}
		if (FCode.equalsIgnoreCase("LocFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LocFlag = FValue.trim();
			}
			else
				LocFlag = null;
		}
		if (FCode.equalsIgnoreCase("ColFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ColFlag = FValue.trim();
			}
			else
				ColFlag = null;
		}
		if (FCode.equalsIgnoreCase("MasFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MasFlag = FValue.trim();
			}
			else
				MasFlag = null;
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
		LLInqConclusionSchema other = (LLInqConclusionSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& ConNo.equals(other.getConNo())
			&& BatNo.equals(other.getBatNo())
			&& InitDept.equals(other.getInitDept())
			&& InqDept.equals(other.getInqDept())
			&& InqConclusion.equals(other.getInqConclusion())
			&& FiniFlag.equals(other.getFiniFlag())
			&& LocFlag.equals(other.getLocFlag())
			&& ColFlag.equals(other.getColFlag())
			&& MasFlag.equals(other.getMasFlag())
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
		if( strFieldName.equals("ConNo") ) {
			return 1;
		}
		if( strFieldName.equals("BatNo") ) {
			return 2;
		}
		if( strFieldName.equals("InitDept") ) {
			return 3;
		}
		if( strFieldName.equals("InqDept") ) {
			return 4;
		}
		if( strFieldName.equals("InqConclusion") ) {
			return 5;
		}
		if( strFieldName.equals("FiniFlag") ) {
			return 6;
		}
		if( strFieldName.equals("LocFlag") ) {
			return 7;
		}
		if( strFieldName.equals("ColFlag") ) {
			return 8;
		}
		if( strFieldName.equals("MasFlag") ) {
			return 9;
		}
		if( strFieldName.equals("Operator") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 14;
		}
		if( strFieldName.equals("Remark") ) {
			return 15;
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
				strFieldName = "ConNo";
				break;
			case 2:
				strFieldName = "BatNo";
				break;
			case 3:
				strFieldName = "InitDept";
				break;
			case 4:
				strFieldName = "InqDept";
				break;
			case 5:
				strFieldName = "InqConclusion";
				break;
			case 6:
				strFieldName = "FiniFlag";
				break;
			case 7:
				strFieldName = "LocFlag";
				break;
			case 8:
				strFieldName = "ColFlag";
				break;
			case 9:
				strFieldName = "MasFlag";
				break;
			case 10:
				strFieldName = "Operator";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "ModifyDate";
				break;
			case 14:
				strFieldName = "ModifyTime";
				break;
			case 15:
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
		if( strFieldName.equals("ConNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InitDept") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqDept") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FiniFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LocFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ColFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MasFlag") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
