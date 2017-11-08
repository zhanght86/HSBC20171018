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
import com.sinosoft.lis.db.LLRegisterIssueDB;

/*
 * <p>ClassName: LLRegisterIssueSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLRegisterIssueSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLRegisterIssueSchema.class);

	// @Field
	/** 个人立案号 */
	private String RgtNo;
	/** 客户号 */
	private String CustomerNo;
	/** 初审序号 */
	private String AutditNo;
	/** 初审结论 */
	private String IssueConclusion;
	/** 退回说明 */
	private String IssueReason;
	/** 问题件退回阶段 */
	private String IssueStage;
	/** 备注 */
	private String IssueRemark;
	/** 问题件退回机构 */
	private String IssueBackCom;
	/** 问题件退回人 */
	private String IssueBacker;
	/** 问题件退回日期 */
	private Date IssueBackDate;
	/** 问题件退回时间 */
	private String IssueBackTime;
	/** 问题件回销人 */
	private String IssueReplyer;
	/** 问题件回销日期 */
	private Date IssueReplyDate;
	/** 问题件回销时间 */
	private String IssueReplyTime;
	/** 问题件回销机构 */
	private String IssueReplyCom;
	/** 问题件回销结论 */
	private String IssueReplyConclusion;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLRegisterIssueSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "RgtNo";
		pk[1] = "AutditNo";

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
		LLRegisterIssueSchema cloned = (LLRegisterIssueSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		RgtNo = aRgtNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public String getAutditNo()
	{
		return AutditNo;
	}
	public void setAutditNo(String aAutditNo)
	{
		AutditNo = aAutditNo;
	}
	public String getIssueConclusion()
	{
		return IssueConclusion;
	}
	public void setIssueConclusion(String aIssueConclusion)
	{
		IssueConclusion = aIssueConclusion;
	}
	public String getIssueReason()
	{
		return IssueReason;
	}
	public void setIssueReason(String aIssueReason)
	{
		IssueReason = aIssueReason;
	}
	public String getIssueStage()
	{
		return IssueStage;
	}
	public void setIssueStage(String aIssueStage)
	{
		IssueStage = aIssueStage;
	}
	public String getIssueRemark()
	{
		return IssueRemark;
	}
	public void setIssueRemark(String aIssueRemark)
	{
		IssueRemark = aIssueRemark;
	}
	public String getIssueBackCom()
	{
		return IssueBackCom;
	}
	public void setIssueBackCom(String aIssueBackCom)
	{
		IssueBackCom = aIssueBackCom;
	}
	public String getIssueBacker()
	{
		return IssueBacker;
	}
	public void setIssueBacker(String aIssueBacker)
	{
		IssueBacker = aIssueBacker;
	}
	public String getIssueBackDate()
	{
		if( IssueBackDate != null )
			return fDate.getString(IssueBackDate);
		else
			return null;
	}
	public void setIssueBackDate(Date aIssueBackDate)
	{
		IssueBackDate = aIssueBackDate;
	}
	public void setIssueBackDate(String aIssueBackDate)
	{
		if (aIssueBackDate != null && !aIssueBackDate.equals("") )
		{
			IssueBackDate = fDate.getDate( aIssueBackDate );
		}
		else
			IssueBackDate = null;
	}

	public String getIssueBackTime()
	{
		return IssueBackTime;
	}
	public void setIssueBackTime(String aIssueBackTime)
	{
		IssueBackTime = aIssueBackTime;
	}
	public String getIssueReplyer()
	{
		return IssueReplyer;
	}
	public void setIssueReplyer(String aIssueReplyer)
	{
		IssueReplyer = aIssueReplyer;
	}
	public String getIssueReplyDate()
	{
		if( IssueReplyDate != null )
			return fDate.getString(IssueReplyDate);
		else
			return null;
	}
	public void setIssueReplyDate(Date aIssueReplyDate)
	{
		IssueReplyDate = aIssueReplyDate;
	}
	public void setIssueReplyDate(String aIssueReplyDate)
	{
		if (aIssueReplyDate != null && !aIssueReplyDate.equals("") )
		{
			IssueReplyDate = fDate.getDate( aIssueReplyDate );
		}
		else
			IssueReplyDate = null;
	}

	public String getIssueReplyTime()
	{
		return IssueReplyTime;
	}
	public void setIssueReplyTime(String aIssueReplyTime)
	{
		IssueReplyTime = aIssueReplyTime;
	}
	public String getIssueReplyCom()
	{
		return IssueReplyCom;
	}
	public void setIssueReplyCom(String aIssueReplyCom)
	{
		IssueReplyCom = aIssueReplyCom;
	}
	public String getIssueReplyConclusion()
	{
		return IssueReplyConclusion;
	}
	public void setIssueReplyConclusion(String aIssueReplyConclusion)
	{
		IssueReplyConclusion = aIssueReplyConclusion;
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

	/**
	* 使用另外一个 LLRegisterIssueSchema 对象给 Schema 赋值
	* @param: aLLRegisterIssueSchema LLRegisterIssueSchema
	**/
	public void setSchema(LLRegisterIssueSchema aLLRegisterIssueSchema)
	{
		this.RgtNo = aLLRegisterIssueSchema.getRgtNo();
		this.CustomerNo = aLLRegisterIssueSchema.getCustomerNo();
		this.AutditNo = aLLRegisterIssueSchema.getAutditNo();
		this.IssueConclusion = aLLRegisterIssueSchema.getIssueConclusion();
		this.IssueReason = aLLRegisterIssueSchema.getIssueReason();
		this.IssueStage = aLLRegisterIssueSchema.getIssueStage();
		this.IssueRemark = aLLRegisterIssueSchema.getIssueRemark();
		this.IssueBackCom = aLLRegisterIssueSchema.getIssueBackCom();
		this.IssueBacker = aLLRegisterIssueSchema.getIssueBacker();
		this.IssueBackDate = fDate.getDate( aLLRegisterIssueSchema.getIssueBackDate());
		this.IssueBackTime = aLLRegisterIssueSchema.getIssueBackTime();
		this.IssueReplyer = aLLRegisterIssueSchema.getIssueReplyer();
		this.IssueReplyDate = fDate.getDate( aLLRegisterIssueSchema.getIssueReplyDate());
		this.IssueReplyTime = aLLRegisterIssueSchema.getIssueReplyTime();
		this.IssueReplyCom = aLLRegisterIssueSchema.getIssueReplyCom();
		this.IssueReplyConclusion = aLLRegisterIssueSchema.getIssueReplyConclusion();
		this.MakeDate = fDate.getDate( aLLRegisterIssueSchema.getMakeDate());
		this.MakeTime = aLLRegisterIssueSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLRegisterIssueSchema.getModifyDate());
		this.ModifyTime = aLLRegisterIssueSchema.getModifyTime();
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
			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("AutditNo") == null )
				this.AutditNo = null;
			else
				this.AutditNo = rs.getString("AutditNo").trim();

			if( rs.getString("IssueConclusion") == null )
				this.IssueConclusion = null;
			else
				this.IssueConclusion = rs.getString("IssueConclusion").trim();

			if( rs.getString("IssueReason") == null )
				this.IssueReason = null;
			else
				this.IssueReason = rs.getString("IssueReason").trim();

			if( rs.getString("IssueStage") == null )
				this.IssueStage = null;
			else
				this.IssueStage = rs.getString("IssueStage").trim();

			if( rs.getString("IssueRemark") == null )
				this.IssueRemark = null;
			else
				this.IssueRemark = rs.getString("IssueRemark").trim();

			if( rs.getString("IssueBackCom") == null )
				this.IssueBackCom = null;
			else
				this.IssueBackCom = rs.getString("IssueBackCom").trim();

			if( rs.getString("IssueBacker") == null )
				this.IssueBacker = null;
			else
				this.IssueBacker = rs.getString("IssueBacker").trim();

			this.IssueBackDate = rs.getDate("IssueBackDate");
			if( rs.getString("IssueBackTime") == null )
				this.IssueBackTime = null;
			else
				this.IssueBackTime = rs.getString("IssueBackTime").trim();

			if( rs.getString("IssueReplyer") == null )
				this.IssueReplyer = null;
			else
				this.IssueReplyer = rs.getString("IssueReplyer").trim();

			this.IssueReplyDate = rs.getDate("IssueReplyDate");
			if( rs.getString("IssueReplyTime") == null )
				this.IssueReplyTime = null;
			else
				this.IssueReplyTime = rs.getString("IssueReplyTime").trim();

			if( rs.getString("IssueReplyCom") == null )
				this.IssueReplyCom = null;
			else
				this.IssueReplyCom = rs.getString("IssueReplyCom").trim();

			if( rs.getString("IssueReplyConclusion") == null )
				this.IssueReplyConclusion = null;
			else
				this.IssueReplyConclusion = rs.getString("IssueReplyConclusion").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLRegisterIssue表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRegisterIssueSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLRegisterIssueSchema getSchema()
	{
		LLRegisterIssueSchema aLLRegisterIssueSchema = new LLRegisterIssueSchema();
		aLLRegisterIssueSchema.setSchema(this);
		return aLLRegisterIssueSchema;
	}

	public LLRegisterIssueDB getDB()
	{
		LLRegisterIssueDB aDBOper = new LLRegisterIssueDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLRegisterIssue描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutditNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueStage)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueBackCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueBacker)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IssueBackDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueBackTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueReplyer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IssueReplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueReplyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueReplyCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueReplyConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLRegisterIssue>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AutditNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			IssueConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			IssueReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			IssueStage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			IssueRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			IssueBackCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			IssueBacker = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			IssueBackDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			IssueBackTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			IssueReplyer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			IssueReplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			IssueReplyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			IssueReplyCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			IssueReplyConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRegisterIssueSchema";
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
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("AutditNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutditNo));
		}
		if (FCode.equalsIgnoreCase("IssueConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueConclusion));
		}
		if (FCode.equalsIgnoreCase("IssueReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueReason));
		}
		if (FCode.equalsIgnoreCase("IssueStage"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueStage));
		}
		if (FCode.equalsIgnoreCase("IssueRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueRemark));
		}
		if (FCode.equalsIgnoreCase("IssueBackCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueBackCom));
		}
		if (FCode.equalsIgnoreCase("IssueBacker"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueBacker));
		}
		if (FCode.equalsIgnoreCase("IssueBackDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIssueBackDate()));
		}
		if (FCode.equalsIgnoreCase("IssueBackTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueBackTime));
		}
		if (FCode.equalsIgnoreCase("IssueReplyer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueReplyer));
		}
		if (FCode.equalsIgnoreCase("IssueReplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIssueReplyDate()));
		}
		if (FCode.equalsIgnoreCase("IssueReplyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueReplyTime));
		}
		if (FCode.equalsIgnoreCase("IssueReplyCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueReplyCom));
		}
		if (FCode.equalsIgnoreCase("IssueReplyConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueReplyConclusion));
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
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AutditNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(IssueConclusion);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(IssueReason);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(IssueStage);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(IssueRemark);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(IssueBackCom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(IssueBacker);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIssueBackDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(IssueBackTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(IssueReplyer);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIssueReplyDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(IssueReplyTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(IssueReplyCom);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(IssueReplyConclusion);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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

		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtNo = FValue.trim();
			}
			else
				RgtNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("AutditNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutditNo = FValue.trim();
			}
			else
				AutditNo = null;
		}
		if (FCode.equalsIgnoreCase("IssueConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueConclusion = FValue.trim();
			}
			else
				IssueConclusion = null;
		}
		if (FCode.equalsIgnoreCase("IssueReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueReason = FValue.trim();
			}
			else
				IssueReason = null;
		}
		if (FCode.equalsIgnoreCase("IssueStage"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueStage = FValue.trim();
			}
			else
				IssueStage = null;
		}
		if (FCode.equalsIgnoreCase("IssueRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueRemark = FValue.trim();
			}
			else
				IssueRemark = null;
		}
		if (FCode.equalsIgnoreCase("IssueBackCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueBackCom = FValue.trim();
			}
			else
				IssueBackCom = null;
		}
		if (FCode.equalsIgnoreCase("IssueBacker"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueBacker = FValue.trim();
			}
			else
				IssueBacker = null;
		}
		if (FCode.equalsIgnoreCase("IssueBackDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				IssueBackDate = fDate.getDate( FValue );
			}
			else
				IssueBackDate = null;
		}
		if (FCode.equalsIgnoreCase("IssueBackTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueBackTime = FValue.trim();
			}
			else
				IssueBackTime = null;
		}
		if (FCode.equalsIgnoreCase("IssueReplyer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueReplyer = FValue.trim();
			}
			else
				IssueReplyer = null;
		}
		if (FCode.equalsIgnoreCase("IssueReplyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				IssueReplyDate = fDate.getDate( FValue );
			}
			else
				IssueReplyDate = null;
		}
		if (FCode.equalsIgnoreCase("IssueReplyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueReplyTime = FValue.trim();
			}
			else
				IssueReplyTime = null;
		}
		if (FCode.equalsIgnoreCase("IssueReplyCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueReplyCom = FValue.trim();
			}
			else
				IssueReplyCom = null;
		}
		if (FCode.equalsIgnoreCase("IssueReplyConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueReplyConclusion = FValue.trim();
			}
			else
				IssueReplyConclusion = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLRegisterIssueSchema other = (LLRegisterIssueSchema)otherObject;
		return
			RgtNo.equals(other.getRgtNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& AutditNo.equals(other.getAutditNo())
			&& IssueConclusion.equals(other.getIssueConclusion())
			&& IssueReason.equals(other.getIssueReason())
			&& IssueStage.equals(other.getIssueStage())
			&& IssueRemark.equals(other.getIssueRemark())
			&& IssueBackCom.equals(other.getIssueBackCom())
			&& IssueBacker.equals(other.getIssueBacker())
			&& fDate.getString(IssueBackDate).equals(other.getIssueBackDate())
			&& IssueBackTime.equals(other.getIssueBackTime())
			&& IssueReplyer.equals(other.getIssueReplyer())
			&& fDate.getString(IssueReplyDate).equals(other.getIssueReplyDate())
			&& IssueReplyTime.equals(other.getIssueReplyTime())
			&& IssueReplyCom.equals(other.getIssueReplyCom())
			&& IssueReplyConclusion.equals(other.getIssueReplyConclusion())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("RgtNo") ) {
			return 0;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 1;
		}
		if( strFieldName.equals("AutditNo") ) {
			return 2;
		}
		if( strFieldName.equals("IssueConclusion") ) {
			return 3;
		}
		if( strFieldName.equals("IssueReason") ) {
			return 4;
		}
		if( strFieldName.equals("IssueStage") ) {
			return 5;
		}
		if( strFieldName.equals("IssueRemark") ) {
			return 6;
		}
		if( strFieldName.equals("IssueBackCom") ) {
			return 7;
		}
		if( strFieldName.equals("IssueBacker") ) {
			return 8;
		}
		if( strFieldName.equals("IssueBackDate") ) {
			return 9;
		}
		if( strFieldName.equals("IssueBackTime") ) {
			return 10;
		}
		if( strFieldName.equals("IssueReplyer") ) {
			return 11;
		}
		if( strFieldName.equals("IssueReplyDate") ) {
			return 12;
		}
		if( strFieldName.equals("IssueReplyTime") ) {
			return 13;
		}
		if( strFieldName.equals("IssueReplyCom") ) {
			return 14;
		}
		if( strFieldName.equals("IssueReplyConclusion") ) {
			return 15;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 16;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 19;
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
				strFieldName = "RgtNo";
				break;
			case 1:
				strFieldName = "CustomerNo";
				break;
			case 2:
				strFieldName = "AutditNo";
				break;
			case 3:
				strFieldName = "IssueConclusion";
				break;
			case 4:
				strFieldName = "IssueReason";
				break;
			case 5:
				strFieldName = "IssueStage";
				break;
			case 6:
				strFieldName = "IssueRemark";
				break;
			case 7:
				strFieldName = "IssueBackCom";
				break;
			case 8:
				strFieldName = "IssueBacker";
				break;
			case 9:
				strFieldName = "IssueBackDate";
				break;
			case 10:
				strFieldName = "IssueBackTime";
				break;
			case 11:
				strFieldName = "IssueReplyer";
				break;
			case 12:
				strFieldName = "IssueReplyDate";
				break;
			case 13:
				strFieldName = "IssueReplyTime";
				break;
			case 14:
				strFieldName = "IssueReplyCom";
				break;
			case 15:
				strFieldName = "IssueReplyConclusion";
				break;
			case 16:
				strFieldName = "MakeDate";
				break;
			case 17:
				strFieldName = "MakeTime";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutditNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IssueConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IssueReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IssueStage") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IssueRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IssueBackCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IssueBacker") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IssueBackDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IssueBackTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IssueReplyer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IssueReplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IssueReplyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IssueReplyCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IssueReplyConclusion") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
