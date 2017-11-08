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
import com.sinosoft.lis.db.LJPremMatchConfirmDB;

/*
 * <p>ClassName: LJPremMatchConfirmSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJPremMatchConfirmSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJPremMatchConfirmSchema.class);
	// @Field
	/** 保费匹配流水号 */
	private String MatchSerialNo;
	/** 申请操作人 */
	private String AppOperator;
	/** 申请日期 */
	private Date AppDate;
	/** 申请时间 */
	private String AppTime;
	/** 录入操作人 */
	private String InputOperator;
	/** 录入日期 */
	private Date InputDate;
	/** 录入时间 */
	private String InputTime;
	/** 录入结论 */
	private String InputConclusion;
	/** 录入结论描述 */
	private String InputDesc;
	/** 审核操作人 */
	private String ConfirmOperator;
	/** 审核日期 */
	private Date ConfirmDate;
	/** 审核结论 */
	private String ConfirmConclusion;
	/** 审核时间 */
	private String ConfirmTime;
	/** 审核结论描述 */
	private String ConfirmDesc;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJPremMatchConfirmSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "MatchSerialNo";

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
		LJPremMatchConfirmSchema cloned = (LJPremMatchConfirmSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getMatchSerialNo()
	{
		return MatchSerialNo;
	}
	public void setMatchSerialNo(String aMatchSerialNo)
	{
		if(aMatchSerialNo!=null && aMatchSerialNo.length()>20)
			throw new IllegalArgumentException("保费匹配流水号MatchSerialNo值"+aMatchSerialNo+"的长度"+aMatchSerialNo.length()+"大于最大值20");
		MatchSerialNo = aMatchSerialNo;
	}
	public String getAppOperator()
	{
		return AppOperator;
	}
	public void setAppOperator(String aAppOperator)
	{
		if(aAppOperator!=null && aAppOperator.length()>30)
			throw new IllegalArgumentException("申请操作人AppOperator值"+aAppOperator+"的长度"+aAppOperator.length()+"大于最大值30");
		AppOperator = aAppOperator;
	}
	public String getAppDate()
	{
		if( AppDate != null )
			return fDate.getString(AppDate);
		else
			return null;
	}
	public void setAppDate(Date aAppDate)
	{
		AppDate = aAppDate;
	}
	public void setAppDate(String aAppDate)
	{
		if (aAppDate != null && !aAppDate.equals("") )
		{
			AppDate = fDate.getDate( aAppDate );
		}
		else
			AppDate = null;
	}

	public String getAppTime()
	{
		return AppTime;
	}
	public void setAppTime(String aAppTime)
	{
		if(aAppTime!=null && aAppTime.length()>8)
			throw new IllegalArgumentException("申请时间AppTime值"+aAppTime+"的长度"+aAppTime.length()+"大于最大值8");
		AppTime = aAppTime;
	}
	public String getInputOperator()
	{
		return InputOperator;
	}
	public void setInputOperator(String aInputOperator)
	{
		if(aInputOperator!=null && aInputOperator.length()>30)
			throw new IllegalArgumentException("录入操作人InputOperator值"+aInputOperator+"的长度"+aInputOperator.length()+"大于最大值30");
		InputOperator = aInputOperator;
	}
	public String getInputDate()
	{
		if( InputDate != null )
			return fDate.getString(InputDate);
		else
			return null;
	}
	public void setInputDate(Date aInputDate)
	{
		InputDate = aInputDate;
	}
	public void setInputDate(String aInputDate)
	{
		if (aInputDate != null && !aInputDate.equals("") )
		{
			InputDate = fDate.getDate( aInputDate );
		}
		else
			InputDate = null;
	}

	public String getInputTime()
	{
		return InputTime;
	}
	public void setInputTime(String aInputTime)
	{
		if(aInputTime!=null && aInputTime.length()>8)
			throw new IllegalArgumentException("录入时间InputTime值"+aInputTime+"的长度"+aInputTime.length()+"大于最大值8");
		InputTime = aInputTime;
	}
	public String getInputConclusion()
	{
		return InputConclusion;
	}
	public void setInputConclusion(String aInputConclusion)
	{
		if(aInputConclusion!=null && aInputConclusion.length()>2)
			throw new IllegalArgumentException("录入结论InputConclusion值"+aInputConclusion+"的长度"+aInputConclusion.length()+"大于最大值2");
		InputConclusion = aInputConclusion;
	}
	public String getInputDesc()
	{
		return InputDesc;
	}
	public void setInputDesc(String aInputDesc)
	{
		if(aInputDesc!=null && aInputDesc.length()>3000)
			throw new IllegalArgumentException("录入结论描述InputDesc值"+aInputDesc+"的长度"+aInputDesc.length()+"大于最大值3000");
		InputDesc = aInputDesc;
	}
	public String getConfirmOperator()
	{
		return ConfirmOperator;
	}
	public void setConfirmOperator(String aConfirmOperator)
	{
		if(aConfirmOperator!=null && aConfirmOperator.length()>30)
			throw new IllegalArgumentException("审核操作人ConfirmOperator值"+aConfirmOperator+"的长度"+aConfirmOperator.length()+"大于最大值30");
		ConfirmOperator = aConfirmOperator;
	}
	public String getConfirmDate()
	{
		if( ConfirmDate != null )
			return fDate.getString(ConfirmDate);
		else
			return null;
	}
	public void setConfirmDate(Date aConfirmDate)
	{
		ConfirmDate = aConfirmDate;
	}
	public void setConfirmDate(String aConfirmDate)
	{
		if (aConfirmDate != null && !aConfirmDate.equals("") )
		{
			ConfirmDate = fDate.getDate( aConfirmDate );
		}
		else
			ConfirmDate = null;
	}

	public String getConfirmConclusion()
	{
		return ConfirmConclusion;
	}
	public void setConfirmConclusion(String aConfirmConclusion)
	{
		if(aConfirmConclusion!=null && aConfirmConclusion.length()>2)
			throw new IllegalArgumentException("审核结论ConfirmConclusion值"+aConfirmConclusion+"的长度"+aConfirmConclusion.length()+"大于最大值2");
		ConfirmConclusion = aConfirmConclusion;
	}
	public String getConfirmTime()
	{
		return ConfirmTime;
	}
	public void setConfirmTime(String aConfirmTime)
	{
		if(aConfirmTime!=null && aConfirmTime.length()>8)
			throw new IllegalArgumentException("审核时间ConfirmTime值"+aConfirmTime+"的长度"+aConfirmTime.length()+"大于最大值8");
		ConfirmTime = aConfirmTime;
	}
	public String getConfirmDesc()
	{
		return ConfirmDesc;
	}
	public void setConfirmDesc(String aConfirmDesc)
	{
		if(aConfirmDesc!=null && aConfirmDesc.length()>3000)
			throw new IllegalArgumentException("审核结论描述ConfirmDesc值"+aConfirmDesc+"的长度"+aConfirmDesc.length()+"大于最大值3000");
		ConfirmDesc = aConfirmDesc;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
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

	/**
	* 使用另外一个 LJPremMatchConfirmSchema 对象给 Schema 赋值
	* @param: aLJPremMatchConfirmSchema LJPremMatchConfirmSchema
	**/
	public void setSchema(LJPremMatchConfirmSchema aLJPremMatchConfirmSchema)
	{
		this.MatchSerialNo = aLJPremMatchConfirmSchema.getMatchSerialNo();
		this.AppOperator = aLJPremMatchConfirmSchema.getAppOperator();
		this.AppDate = fDate.getDate( aLJPremMatchConfirmSchema.getAppDate());
		this.AppTime = aLJPremMatchConfirmSchema.getAppTime();
		this.InputOperator = aLJPremMatchConfirmSchema.getInputOperator();
		this.InputDate = fDate.getDate( aLJPremMatchConfirmSchema.getInputDate());
		this.InputTime = aLJPremMatchConfirmSchema.getInputTime();
		this.InputConclusion = aLJPremMatchConfirmSchema.getInputConclusion();
		this.InputDesc = aLJPremMatchConfirmSchema.getInputDesc();
		this.ConfirmOperator = aLJPremMatchConfirmSchema.getConfirmOperator();
		this.ConfirmDate = fDate.getDate( aLJPremMatchConfirmSchema.getConfirmDate());
		this.ConfirmConclusion = aLJPremMatchConfirmSchema.getConfirmConclusion();
		this.ConfirmTime = aLJPremMatchConfirmSchema.getConfirmTime();
		this.ConfirmDesc = aLJPremMatchConfirmSchema.getConfirmDesc();
		this.ManageCom = aLJPremMatchConfirmSchema.getManageCom();
		this.ComCode = aLJPremMatchConfirmSchema.getComCode();
		this.MakeOperator = aLJPremMatchConfirmSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLJPremMatchConfirmSchema.getMakeDate());
		this.MakeTime = aLJPremMatchConfirmSchema.getMakeTime();
		this.ModifyOperator = aLJPremMatchConfirmSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLJPremMatchConfirmSchema.getModifyDate());
		this.ModifyTime = aLJPremMatchConfirmSchema.getModifyTime();
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
			if( rs.getString("MatchSerialNo") == null )
				this.MatchSerialNo = null;
			else
				this.MatchSerialNo = rs.getString("MatchSerialNo").trim();

			if( rs.getString("AppOperator") == null )
				this.AppOperator = null;
			else
				this.AppOperator = rs.getString("AppOperator").trim();

			this.AppDate = rs.getDate("AppDate");
			if( rs.getString("AppTime") == null )
				this.AppTime = null;
			else
				this.AppTime = rs.getString("AppTime").trim();

			if( rs.getString("InputOperator") == null )
				this.InputOperator = null;
			else
				this.InputOperator = rs.getString("InputOperator").trim();

			this.InputDate = rs.getDate("InputDate");
			if( rs.getString("InputTime") == null )
				this.InputTime = null;
			else
				this.InputTime = rs.getString("InputTime").trim();

			if( rs.getString("InputConclusion") == null )
				this.InputConclusion = null;
			else
				this.InputConclusion = rs.getString("InputConclusion").trim();

			if( rs.getString("InputDesc") == null )
				this.InputDesc = null;
			else
				this.InputDesc = rs.getString("InputDesc").trim();

			if( rs.getString("ConfirmOperator") == null )
				this.ConfirmOperator = null;
			else
				this.ConfirmOperator = rs.getString("ConfirmOperator").trim();

			this.ConfirmDate = rs.getDate("ConfirmDate");
			if( rs.getString("ConfirmConclusion") == null )
				this.ConfirmConclusion = null;
			else
				this.ConfirmConclusion = rs.getString("ConfirmConclusion").trim();

			if( rs.getString("ConfirmTime") == null )
				this.ConfirmTime = null;
			else
				this.ConfirmTime = rs.getString("ConfirmTime").trim();

			if( rs.getString("ConfirmDesc") == null )
				this.ConfirmDesc = null;
			else
				this.ConfirmDesc = rs.getString("ConfirmDesc").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJPremMatchConfirm表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJPremMatchConfirmSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJPremMatchConfirmSchema getSchema()
	{
		LJPremMatchConfirmSchema aLJPremMatchConfirmSchema = new LJPremMatchConfirmSchema();
		aLJPremMatchConfirmSchema.setSchema(this);
		return aLJPremMatchConfirmSchema;
	}

	public LJPremMatchConfirmDB getDB()
	{
		LJPremMatchConfirmDB aDBOper = new LJPremMatchConfirmDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJPremMatchConfirm描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(MatchSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AppDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InputDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfirmDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJPremMatchConfirm>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			MatchSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AppOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AppDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			AppTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			InputOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InputDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			InputTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InputConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			InputDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ConfirmOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ConfirmDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ConfirmConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ConfirmTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ConfirmDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJPremMatchConfirmSchema";
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
		if (FCode.equalsIgnoreCase("MatchSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MatchSerialNo));
		}
		if (FCode.equalsIgnoreCase("AppOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppOperator));
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
		}
		if (FCode.equalsIgnoreCase("AppTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppTime));
		}
		if (FCode.equalsIgnoreCase("InputOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputOperator));
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
		}
		if (FCode.equalsIgnoreCase("InputTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputTime));
		}
		if (FCode.equalsIgnoreCase("InputConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputConclusion));
		}
		if (FCode.equalsIgnoreCase("InputDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputDesc));
		}
		if (FCode.equalsIgnoreCase("ConfirmOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmOperator));
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
		}
		if (FCode.equalsIgnoreCase("ConfirmConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmConclusion));
		}
		if (FCode.equalsIgnoreCase("ConfirmTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmTime));
		}
		if (FCode.equalsIgnoreCase("ConfirmDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmDesc));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(MatchSerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AppOperator);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AppTime);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(InputOperator);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InputTime);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InputConclusion);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(InputDesc);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ConfirmOperator);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ConfirmConclusion);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ConfirmTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ConfirmDesc);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 21:
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

		if (FCode.equalsIgnoreCase("MatchSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MatchSerialNo = FValue.trim();
			}
			else
				MatchSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("AppOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppOperator = FValue.trim();
			}
			else
				AppOperator = null;
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppDate = fDate.getDate( FValue );
			}
			else
				AppDate = null;
		}
		if (FCode.equalsIgnoreCase("AppTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppTime = FValue.trim();
			}
			else
				AppTime = null;
		}
		if (FCode.equalsIgnoreCase("InputOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputOperator = FValue.trim();
			}
			else
				InputOperator = null;
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InputDate = fDate.getDate( FValue );
			}
			else
				InputDate = null;
		}
		if (FCode.equalsIgnoreCase("InputTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputTime = FValue.trim();
			}
			else
				InputTime = null;
		}
		if (FCode.equalsIgnoreCase("InputConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputConclusion = FValue.trim();
			}
			else
				InputConclusion = null;
		}
		if (FCode.equalsIgnoreCase("InputDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputDesc = FValue.trim();
			}
			else
				InputDesc = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmOperator = FValue.trim();
			}
			else
				ConfirmOperator = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfirmDate = fDate.getDate( FValue );
			}
			else
				ConfirmDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmConclusion = FValue.trim();
			}
			else
				ConfirmConclusion = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmTime = FValue.trim();
			}
			else
				ConfirmTime = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmDesc = FValue.trim();
			}
			else
				ConfirmDesc = null;
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		LJPremMatchConfirmSchema other = (LJPremMatchConfirmSchema)otherObject;
		return
			MatchSerialNo.equals(other.getMatchSerialNo())
			&& AppOperator.equals(other.getAppOperator())
			&& fDate.getString(AppDate).equals(other.getAppDate())
			&& AppTime.equals(other.getAppTime())
			&& InputOperator.equals(other.getInputOperator())
			&& fDate.getString(InputDate).equals(other.getInputDate())
			&& InputTime.equals(other.getInputTime())
			&& InputConclusion.equals(other.getInputConclusion())
			&& InputDesc.equals(other.getInputDesc())
			&& ConfirmOperator.equals(other.getConfirmOperator())
			&& fDate.getString(ConfirmDate).equals(other.getConfirmDate())
			&& ConfirmConclusion.equals(other.getConfirmConclusion())
			&& ConfirmTime.equals(other.getConfirmTime())
			&& ConfirmDesc.equals(other.getConfirmDesc())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
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
		if( strFieldName.equals("MatchSerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("AppOperator") ) {
			return 1;
		}
		if( strFieldName.equals("AppDate") ) {
			return 2;
		}
		if( strFieldName.equals("AppTime") ) {
			return 3;
		}
		if( strFieldName.equals("InputOperator") ) {
			return 4;
		}
		if( strFieldName.equals("InputDate") ) {
			return 5;
		}
		if( strFieldName.equals("InputTime") ) {
			return 6;
		}
		if( strFieldName.equals("InputConclusion") ) {
			return 7;
		}
		if( strFieldName.equals("InputDesc") ) {
			return 8;
		}
		if( strFieldName.equals("ConfirmOperator") ) {
			return 9;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return 10;
		}
		if( strFieldName.equals("ConfirmConclusion") ) {
			return 11;
		}
		if( strFieldName.equals("ConfirmTime") ) {
			return 12;
		}
		if( strFieldName.equals("ConfirmDesc") ) {
			return 13;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 14;
		}
		if( strFieldName.equals("ComCode") ) {
			return 15;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 16;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 17;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 21;
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
				strFieldName = "MatchSerialNo";
				break;
			case 1:
				strFieldName = "AppOperator";
				break;
			case 2:
				strFieldName = "AppDate";
				break;
			case 3:
				strFieldName = "AppTime";
				break;
			case 4:
				strFieldName = "InputOperator";
				break;
			case 5:
				strFieldName = "InputDate";
				break;
			case 6:
				strFieldName = "InputTime";
				break;
			case 7:
				strFieldName = "InputConclusion";
				break;
			case 8:
				strFieldName = "InputDesc";
				break;
			case 9:
				strFieldName = "ConfirmOperator";
				break;
			case 10:
				strFieldName = "ConfirmDate";
				break;
			case 11:
				strFieldName = "ConfirmConclusion";
				break;
			case 12:
				strFieldName = "ConfirmTime";
				break;
			case 13:
				strFieldName = "ConfirmDesc";
				break;
			case 14:
				strFieldName = "ManageCom";
				break;
			case 15:
				strFieldName = "ComCode";
				break;
			case 16:
				strFieldName = "MakeOperator";
				break;
			case 17:
				strFieldName = "MakeDate";
				break;
			case 18:
				strFieldName = "MakeTime";
				break;
			case 19:
				strFieldName = "ModifyOperator";
				break;
			case 20:
				strFieldName = "ModifyDate";
				break;
			case 21:
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
		if( strFieldName.equals("MatchSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InputTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfirmConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
