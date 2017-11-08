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
import com.sinosoft.lis.db.Es_IssueDocDB;

/*
 * <p>ClassName: Es_IssueDocSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_12
 */
public class Es_IssueDocSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(Es_IssueDocSchema.class);
	// @Field
	/** 业务号码 */
	private String BussNo;
	/** 业务号码类型 */
	private String BussNoType;
	/** 业务类型 */
	private String BussType;
	/** 单证细类 */
	private String SubType;
	/** 问题描述 */
	private String IssueDesc;
	/** 处理状态 */
	private String Status;
	/** 处理结果 */
	private String Result;
	/** 回复操作员 */
	private String ReplyOperator;
	/** 提出操作员 */
	private String PromptOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 问题流水号 */
	private String IssueDocID;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public Es_IssueDocSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "BussNo";
		pk[1] = "BussNoType";
		pk[2] = "SubType";
		pk[3] = "IssueDocID";

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
		Es_IssueDocSchema cloned = (Es_IssueDocSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		if(aBussNo!=null && aBussNo.length()>20)
			throw new IllegalArgumentException("业务号码BussNo值"+aBussNo+"的长度"+aBussNo.length()+"大于最大值20");
		BussNo = aBussNo;
	}
	/**
	* 如果不知道就为空,系统会查找出与业务号码相关的全部单证
	*/
	public String getBussNoType()
	{
		return BussNoType;
	}
	public void setBussNoType(String aBussNoType)
	{
		if(aBussNoType!=null && aBussNoType.length()>2)
			throw new IllegalArgumentException("业务号码类型BussNoType值"+aBussNoType+"的长度"+aBussNoType.length()+"大于最大值2");
		BussNoType = aBussNoType;
	}
	public String getBussType()
	{
		return BussType;
	}
	public void setBussType(String aBussType)
	{
		if(aBussType!=null && aBussType.length()>4)
			throw new IllegalArgumentException("业务类型BussType值"+aBussType+"的长度"+aBussType.length()+"大于最大值4");
		BussType = aBussType;
	}
	public String getSubType()
	{
		return SubType;
	}
	public void setSubType(String aSubType)
	{
		if(aSubType!=null && aSubType.length()>8)
			throw new IllegalArgumentException("单证细类SubType值"+aSubType+"的长度"+aSubType.length()+"大于最大值8");
		SubType = aSubType;
	}
	public String getIssueDesc()
	{
		return IssueDesc;
	}
	public void setIssueDesc(String aIssueDesc)
	{
		if(aIssueDesc!=null && aIssueDesc.length()>200)
			throw new IllegalArgumentException("问题描述IssueDesc值"+aIssueDesc+"的长度"+aIssueDesc.length()+"大于最大值200");
		IssueDesc = aIssueDesc;
	}
	/**
	* 0-提出<p>
	* 1-回复
	*/
	public String getStatus()
	{
		return Status;
	}
	public void setStatus(String aStatus)
	{
		if(aStatus!=null && aStatus.length()>1)
			throw new IllegalArgumentException("处理状态Status值"+aStatus+"的长度"+aStatus.length()+"大于最大值1");
		Status = aStatus;
	}
	public String getResult()
	{
		return Result;
	}
	public void setResult(String aResult)
	{
		if(aResult!=null && aResult.length()>100)
			throw new IllegalArgumentException("处理结果Result值"+aResult+"的长度"+aResult.length()+"大于最大值100");
		Result = aResult;
	}
	public String getReplyOperator()
	{
		return ReplyOperator;
	}
	public void setReplyOperator(String aReplyOperator)
	{
		if(aReplyOperator!=null && aReplyOperator.length()>80)
			throw new IllegalArgumentException("回复操作员ReplyOperator值"+aReplyOperator+"的长度"+aReplyOperator.length()+"大于最大值80");
		ReplyOperator = aReplyOperator;
	}
	public String getPromptOperator()
	{
		return PromptOperator;
	}
	public void setPromptOperator(String aPromptOperator)
	{
		if(aPromptOperator!=null && aPromptOperator.length()>80)
			throw new IllegalArgumentException("提出操作员PromptOperator值"+aPromptOperator+"的长度"+aPromptOperator.length()+"大于最大值80");
		PromptOperator = aPromptOperator;
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
			throw new IllegalArgumentException("修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}
	public String getIssueDocID()
	{
		return IssueDocID;
	}
	public void setIssueDocID(String aIssueDocID)
	{
		if(aIssueDocID!=null && aIssueDocID.length()>20)
			throw new IllegalArgumentException("问题流水号IssueDocID值"+aIssueDocID+"的长度"+aIssueDocID.length()+"大于最大值20");
		IssueDocID = aIssueDocID;
	}

	/**
	* 使用另外一个 Es_IssueDocSchema 对象给 Schema 赋值
	* @param: aEs_IssueDocSchema Es_IssueDocSchema
	**/
	public void setSchema(Es_IssueDocSchema aEs_IssueDocSchema)
	{
		this.BussNo = aEs_IssueDocSchema.getBussNo();
		this.BussNoType = aEs_IssueDocSchema.getBussNoType();
		this.BussType = aEs_IssueDocSchema.getBussType();
		this.SubType = aEs_IssueDocSchema.getSubType();
		this.IssueDesc = aEs_IssueDocSchema.getIssueDesc();
		this.Status = aEs_IssueDocSchema.getStatus();
		this.Result = aEs_IssueDocSchema.getResult();
		this.ReplyOperator = aEs_IssueDocSchema.getReplyOperator();
		this.PromptOperator = aEs_IssueDocSchema.getPromptOperator();
		this.MakeDate = fDate.getDate( aEs_IssueDocSchema.getMakeDate());
		this.MakeTime = aEs_IssueDocSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aEs_IssueDocSchema.getModifyDate());
		this.ModifyTime = aEs_IssueDocSchema.getModifyTime();
		this.IssueDocID = aEs_IssueDocSchema.getIssueDocID();
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
			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			if( rs.getString("BussNoType") == null )
				this.BussNoType = null;
			else
				this.BussNoType = rs.getString("BussNoType").trim();

			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("SubType") == null )
				this.SubType = null;
			else
				this.SubType = rs.getString("SubType").trim();

			if( rs.getString("IssueDesc") == null )
				this.IssueDesc = null;
			else
				this.IssueDesc = rs.getString("IssueDesc").trim();

			if( rs.getString("Status") == null )
				this.Status = null;
			else
				this.Status = rs.getString("Status").trim();

			if( rs.getString("Result") == null )
				this.Result = null;
			else
				this.Result = rs.getString("Result").trim();

			if( rs.getString("ReplyOperator") == null )
				this.ReplyOperator = null;
			else
				this.ReplyOperator = rs.getString("ReplyOperator").trim();

			if( rs.getString("PromptOperator") == null )
				this.PromptOperator = null;
			else
				this.PromptOperator = rs.getString("PromptOperator").trim();

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

			if( rs.getString("IssueDocID") == null )
				this.IssueDocID = null;
			else
				this.IssueDocID = rs.getString("IssueDocID").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的Es_IssueDoc表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "Es_IssueDocSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public Es_IssueDocSchema getSchema()
	{
		Es_IssueDocSchema aEs_IssueDocSchema = new Es_IssueDocSchema();
		aEs_IssueDocSchema.setSchema(this);
		return aEs_IssueDocSchema;
	}

	public Es_IssueDocDB getDB()
	{
		Es_IssueDocDB aDBOper = new Es_IssueDocDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpEs_IssueDoc描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Status)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Result)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReplyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PromptOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueDocID));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpEs_IssueDoc>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BussNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SubType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			IssueDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Status = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Result = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ReplyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PromptOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			IssueDocID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "Es_IssueDocSchema";
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
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("BussNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNoType));
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubType));
		}
		if (FCode.equalsIgnoreCase("IssueDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueDesc));
		}
		if (FCode.equalsIgnoreCase("Status"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Status));
		}
		if (FCode.equalsIgnoreCase("Result"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Result));
		}
		if (FCode.equalsIgnoreCase("ReplyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyOperator));
		}
		if (FCode.equalsIgnoreCase("PromptOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PromptOperator));
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
		if (FCode.equalsIgnoreCase("IssueDocID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueDocID));
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
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BussNoType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SubType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(IssueDesc);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Status);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Result);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ReplyOperator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PromptOperator);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(IssueDocID);
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

		if (FCode.equalsIgnoreCase("BussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNo = FValue.trim();
			}
			else
				BussNo = null;
		}
		if (FCode.equalsIgnoreCase("BussNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNoType = FValue.trim();
			}
			else
				BussNoType = null;
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussType = FValue.trim();
			}
			else
				BussType = null;
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubType = FValue.trim();
			}
			else
				SubType = null;
		}
		if (FCode.equalsIgnoreCase("IssueDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueDesc = FValue.trim();
			}
			else
				IssueDesc = null;
		}
		if (FCode.equalsIgnoreCase("Status"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Status = FValue.trim();
			}
			else
				Status = null;
		}
		if (FCode.equalsIgnoreCase("Result"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Result = FValue.trim();
			}
			else
				Result = null;
		}
		if (FCode.equalsIgnoreCase("ReplyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReplyOperator = FValue.trim();
			}
			else
				ReplyOperator = null;
		}
		if (FCode.equalsIgnoreCase("PromptOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PromptOperator = FValue.trim();
			}
			else
				PromptOperator = null;
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
		if (FCode.equalsIgnoreCase("IssueDocID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueDocID = FValue.trim();
			}
			else
				IssueDocID = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		Es_IssueDocSchema other = (Es_IssueDocSchema)otherObject;
		return
			BussNo.equals(other.getBussNo())
			&& BussNoType.equals(other.getBussNoType())
			&& BussType.equals(other.getBussType())
			&& SubType.equals(other.getSubType())
			&& IssueDesc.equals(other.getIssueDesc())
			&& Status.equals(other.getStatus())
			&& Result.equals(other.getResult())
			&& ReplyOperator.equals(other.getReplyOperator())
			&& PromptOperator.equals(other.getPromptOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& IssueDocID.equals(other.getIssueDocID());
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
		if( strFieldName.equals("BussNo") ) {
			return 0;
		}
		if( strFieldName.equals("BussNoType") ) {
			return 1;
		}
		if( strFieldName.equals("BussType") ) {
			return 2;
		}
		if( strFieldName.equals("SubType") ) {
			return 3;
		}
		if( strFieldName.equals("IssueDesc") ) {
			return 4;
		}
		if( strFieldName.equals("Status") ) {
			return 5;
		}
		if( strFieldName.equals("Result") ) {
			return 6;
		}
		if( strFieldName.equals("ReplyOperator") ) {
			return 7;
		}
		if( strFieldName.equals("PromptOperator") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 12;
		}
		if( strFieldName.equals("IssueDocID") ) {
			return 13;
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
				strFieldName = "BussNo";
				break;
			case 1:
				strFieldName = "BussNoType";
				break;
			case 2:
				strFieldName = "BussType";
				break;
			case 3:
				strFieldName = "SubType";
				break;
			case 4:
				strFieldName = "IssueDesc";
				break;
			case 5:
				strFieldName = "Status";
				break;
			case 6:
				strFieldName = "Result";
				break;
			case 7:
				strFieldName = "ReplyOperator";
				break;
			case 8:
				strFieldName = "PromptOperator";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
				strFieldName = "MakeTime";
				break;
			case 11:
				strFieldName = "ModifyDate";
				break;
			case 12:
				strFieldName = "ModifyTime";
				break;
			case 13:
				strFieldName = "IssueDocID";
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
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IssueDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Status") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Result") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PromptOperator") ) {
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
		if( strFieldName.equals("IssueDocID") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
