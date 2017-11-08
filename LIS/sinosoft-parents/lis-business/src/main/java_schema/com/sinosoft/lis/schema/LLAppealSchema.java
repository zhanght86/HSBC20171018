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
import com.sinosoft.lis.db.LLAppealDB;

/*
 * <p>ClassName: LLAppealSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLAppealSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLAppealSchema.class);

	// @Field
	/** 申诉/错误号码 */
	private String AppealNo;
	/** 分案号(个人理赔号) */
	private String CaseNo;
	/** 申诉/错误类型 */
	private String AppealType;
	/** 申诉状态 */
	private String AppealState;
	/** 申诉原因代码 */
	private String AppeanRCode;
	/** 申诉原因描述 */
	private String AppealReason;
	/** 提起申诉原因描述 */
	private String AppealRDesc;
	/** 期待完成日期 */
	private Date WaitDate;
	/** 申诉提起人 */
	private String AppealName;
	/** 申诉人性别 */
	private String AppealSex;
	/** 申诉人通讯地址 */
	private String Address;
	/** 申诉人电话 */
	private String Phone;
	/** 申诉人手机 */
	private String Mobile;
	/** 申诉通知方式方式 */
	private String AppealMode;
	/** 申诉人与被保人关系 */
	private String Relation;
	/** 申诉人邮编 */
	private String PostCode;
	/** 申请人证件类型 */
	private String IDType;
	/** 申请人证件号码 */
	private String IDNo;
	/** 回执发送方式 */
	private String ReturnMode;
	/** 管理机构 */
	private String ManageCom;
	/** 操作人员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLAppealSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "AppealNo";

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
		LLAppealSchema cloned = (LLAppealSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 相当于一个新的赔案号
	*/
	public String getAppealNo()
	{
		return AppealNo;
	}
	public void setAppealNo(String aAppealNo)
	{
		AppealNo = aAppealNo;
	}
	/**
	* 准备申诉的赔案号
	*/
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		CaseNo = aCaseNo;
	}
	/**
	* 1-客户要求提起(申诉)<p>
	* 2-公司内部要求提起(错误)
	*/
	public String getAppealType()
	{
		return AppealType;
	}
	public void setAppealType(String aAppealType)
	{
		AppealType = aAppealType;
	}
	/**
	* 0-提起,但未回复<p>
	* 1-完成,已经进行了回复。
	*/
	public String getAppealState()
	{
		return AppealState;
	}
	public void setAppealState(String aAppealState)
	{
		AppealState = aAppealState;
	}
	public String getAppeanRCode()
	{
		return AppeanRCode;
	}
	public void setAppeanRCode(String aAppeanRCode)
	{
		AppeanRCode = aAppeanRCode;
	}
	public String getAppealReason()
	{
		return AppealReason;
	}
	public void setAppealReason(String aAppealReason)
	{
		AppealReason = aAppealReason;
	}
	public String getAppealRDesc()
	{
		return AppealRDesc;
	}
	public void setAppealRDesc(String aAppealRDesc)
	{
		AppealRDesc = aAppealRDesc;
	}
	public String getWaitDate()
	{
		if( WaitDate != null )
			return fDate.getString(WaitDate);
		else
			return null;
	}
	public void setWaitDate(Date aWaitDate)
	{
		WaitDate = aWaitDate;
	}
	public void setWaitDate(String aWaitDate)
	{
		if (aWaitDate != null && !aWaitDate.equals("") )
		{
			WaitDate = fDate.getDate( aWaitDate );
		}
		else
			WaitDate = null;
	}

	/**
	* 提起该申诉的人员的名称
	*/
	public String getAppealName()
	{
		return AppealName;
	}
	public void setAppealName(String aAppealName)
	{
		AppealName = aAppealName;
	}
	public String getAppealSex()
	{
		return AppealSex;
	}
	public void setAppealSex(String aAppealSex)
	{
		AppealSex = aAppealSex;
	}
	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String aAddress)
	{
		Address = aAddress;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		Phone = aPhone;
	}
	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String aMobile)
	{
		Mobile = aMobile;
	}
	public String getAppealMode()
	{
		return AppealMode;
	}
	public void setAppealMode(String aAppealMode)
	{
		AppealMode = aAppealMode;
	}
	/**
	* 包括被保险人、身故受益人、监护人、委托人、其他
	*/
	public String getRelation()
	{
		return Relation;
	}
	public void setRelation(String aRelation)
	{
		Relation = aRelation;
	}
	public String getPostCode()
	{
		return PostCode;
	}
	public void setPostCode(String aPostCode)
	{
		PostCode = aPostCode;
	}
	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		IDType = aIDType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		IDNo = aIDNo;
	}
	public String getReturnMode()
	{
		return ReturnMode;
	}
	public void setReturnMode(String aReturnMode)
	{
		ReturnMode = aReturnMode;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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

	/**
	* 使用另外一个 LLAppealSchema 对象给 Schema 赋值
	* @param: aLLAppealSchema LLAppealSchema
	**/
	public void setSchema(LLAppealSchema aLLAppealSchema)
	{
		this.AppealNo = aLLAppealSchema.getAppealNo();
		this.CaseNo = aLLAppealSchema.getCaseNo();
		this.AppealType = aLLAppealSchema.getAppealType();
		this.AppealState = aLLAppealSchema.getAppealState();
		this.AppeanRCode = aLLAppealSchema.getAppeanRCode();
		this.AppealReason = aLLAppealSchema.getAppealReason();
		this.AppealRDesc = aLLAppealSchema.getAppealRDesc();
		this.WaitDate = fDate.getDate( aLLAppealSchema.getWaitDate());
		this.AppealName = aLLAppealSchema.getAppealName();
		this.AppealSex = aLLAppealSchema.getAppealSex();
		this.Address = aLLAppealSchema.getAddress();
		this.Phone = aLLAppealSchema.getPhone();
		this.Mobile = aLLAppealSchema.getMobile();
		this.AppealMode = aLLAppealSchema.getAppealMode();
		this.Relation = aLLAppealSchema.getRelation();
		this.PostCode = aLLAppealSchema.getPostCode();
		this.IDType = aLLAppealSchema.getIDType();
		this.IDNo = aLLAppealSchema.getIDNo();
		this.ReturnMode = aLLAppealSchema.getReturnMode();
		this.ManageCom = aLLAppealSchema.getManageCom();
		this.Operator = aLLAppealSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLAppealSchema.getMakeDate());
		this.MakeTime = aLLAppealSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLAppealSchema.getModifyDate());
		this.ModifyTime = aLLAppealSchema.getModifyTime();
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
			if( rs.getString("AppealNo") == null )
				this.AppealNo = null;
			else
				this.AppealNo = rs.getString("AppealNo").trim();

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("AppealType") == null )
				this.AppealType = null;
			else
				this.AppealType = rs.getString("AppealType").trim();

			if( rs.getString("AppealState") == null )
				this.AppealState = null;
			else
				this.AppealState = rs.getString("AppealState").trim();

			if( rs.getString("AppeanRCode") == null )
				this.AppeanRCode = null;
			else
				this.AppeanRCode = rs.getString("AppeanRCode").trim();

			if( rs.getString("AppealReason") == null )
				this.AppealReason = null;
			else
				this.AppealReason = rs.getString("AppealReason").trim();

			if( rs.getString("AppealRDesc") == null )
				this.AppealRDesc = null;
			else
				this.AppealRDesc = rs.getString("AppealRDesc").trim();

			this.WaitDate = rs.getDate("WaitDate");
			if( rs.getString("AppealName") == null )
				this.AppealName = null;
			else
				this.AppealName = rs.getString("AppealName").trim();

			if( rs.getString("AppealSex") == null )
				this.AppealSex = null;
			else
				this.AppealSex = rs.getString("AppealSex").trim();

			if( rs.getString("Address") == null )
				this.Address = null;
			else
				this.Address = rs.getString("Address").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("Mobile") == null )
				this.Mobile = null;
			else
				this.Mobile = rs.getString("Mobile").trim();

			if( rs.getString("AppealMode") == null )
				this.AppealMode = null;
			else
				this.AppealMode = rs.getString("AppealMode").trim();

			if( rs.getString("Relation") == null )
				this.Relation = null;
			else
				this.Relation = rs.getString("Relation").trim();

			if( rs.getString("PostCode") == null )
				this.PostCode = null;
			else
				this.PostCode = rs.getString("PostCode").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("ReturnMode") == null )
				this.ReturnMode = null;
			else
				this.ReturnMode = rs.getString("ReturnMode").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLAppeal表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLAppealSchema getSchema()
	{
		LLAppealSchema aLLAppealSchema = new LLAppealSchema();
		aLLAppealSchema.setSchema(this);
		return aLLAppealSchema;
	}

	public LLAppealDB getDB()
	{
		LLAppealDB aDBOper = new LLAppealDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAppeal描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AppealNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppealType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppealState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppeanRCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppealReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppealRDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( WaitDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppealName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppealSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Address)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Mobile)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppealMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Relation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PostCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReturnMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAppeal>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AppealNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AppealType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AppealState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AppeanRCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AppealReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AppealRDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			WaitDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			AppealName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AppealSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Address = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Mobile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AppealMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Relation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			PostCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ReturnMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealSchema";
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
		if (FCode.equalsIgnoreCase("AppealNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppealNo));
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("AppealType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppealType));
		}
		if (FCode.equalsIgnoreCase("AppealState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppealState));
		}
		if (FCode.equalsIgnoreCase("AppeanRCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppeanRCode));
		}
		if (FCode.equalsIgnoreCase("AppealReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppealReason));
		}
		if (FCode.equalsIgnoreCase("AppealRDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppealRDesc));
		}
		if (FCode.equalsIgnoreCase("WaitDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getWaitDate()));
		}
		if (FCode.equalsIgnoreCase("AppealName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppealName));
		}
		if (FCode.equalsIgnoreCase("AppealSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppealSex));
		}
		if (FCode.equalsIgnoreCase("Address"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Address));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("Mobile"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mobile));
		}
		if (FCode.equalsIgnoreCase("AppealMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppealMode));
		}
		if (FCode.equalsIgnoreCase("Relation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Relation));
		}
		if (FCode.equalsIgnoreCase("PostCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PostCode));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("ReturnMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReturnMode));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
				strFieldValue = StrTool.GBKToUnicode(AppealNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AppealType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AppealState);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AppeanRCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AppealReason);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AppealRDesc);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getWaitDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AppealName);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AppealSex);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Address);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Mobile);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AppealMode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Relation);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(PostCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ReturnMode);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 24:
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

		if (FCode.equalsIgnoreCase("AppealNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppealNo = FValue.trim();
			}
			else
				AppealNo = null;
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
		}
		if (FCode.equalsIgnoreCase("AppealType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppealType = FValue.trim();
			}
			else
				AppealType = null;
		}
		if (FCode.equalsIgnoreCase("AppealState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppealState = FValue.trim();
			}
			else
				AppealState = null;
		}
		if (FCode.equalsIgnoreCase("AppeanRCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppeanRCode = FValue.trim();
			}
			else
				AppeanRCode = null;
		}
		if (FCode.equalsIgnoreCase("AppealReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppealReason = FValue.trim();
			}
			else
				AppealReason = null;
		}
		if (FCode.equalsIgnoreCase("AppealRDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppealRDesc = FValue.trim();
			}
			else
				AppealRDesc = null;
		}
		if (FCode.equalsIgnoreCase("WaitDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				WaitDate = fDate.getDate( FValue );
			}
			else
				WaitDate = null;
		}
		if (FCode.equalsIgnoreCase("AppealName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppealName = FValue.trim();
			}
			else
				AppealName = null;
		}
		if (FCode.equalsIgnoreCase("AppealSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppealSex = FValue.trim();
			}
			else
				AppealSex = null;
		}
		if (FCode.equalsIgnoreCase("Address"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Address = FValue.trim();
			}
			else
				Address = null;
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone = FValue.trim();
			}
			else
				Phone = null;
		}
		if (FCode.equalsIgnoreCase("Mobile"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Mobile = FValue.trim();
			}
			else
				Mobile = null;
		}
		if (FCode.equalsIgnoreCase("AppealMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppealMode = FValue.trim();
			}
			else
				AppealMode = null;
		}
		if (FCode.equalsIgnoreCase("Relation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Relation = FValue.trim();
			}
			else
				Relation = null;
		}
		if (FCode.equalsIgnoreCase("PostCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PostCode = FValue.trim();
			}
			else
				PostCode = null;
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDType = FValue.trim();
			}
			else
				IDType = null;
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDNo = FValue.trim();
			}
			else
				IDNo = null;
		}
		if (FCode.equalsIgnoreCase("ReturnMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReturnMode = FValue.trim();
			}
			else
				ReturnMode = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLAppealSchema other = (LLAppealSchema)otherObject;
		return
			AppealNo.equals(other.getAppealNo())
			&& CaseNo.equals(other.getCaseNo())
			&& AppealType.equals(other.getAppealType())
			&& AppealState.equals(other.getAppealState())
			&& AppeanRCode.equals(other.getAppeanRCode())
			&& AppealReason.equals(other.getAppealReason())
			&& AppealRDesc.equals(other.getAppealRDesc())
			&& fDate.getString(WaitDate).equals(other.getWaitDate())
			&& AppealName.equals(other.getAppealName())
			&& AppealSex.equals(other.getAppealSex())
			&& Address.equals(other.getAddress())
			&& Phone.equals(other.getPhone())
			&& Mobile.equals(other.getMobile())
			&& AppealMode.equals(other.getAppealMode())
			&& Relation.equals(other.getRelation())
			&& PostCode.equals(other.getPostCode())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& ReturnMode.equals(other.getReturnMode())
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
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
		if( strFieldName.equals("AppealNo") ) {
			return 0;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 1;
		}
		if( strFieldName.equals("AppealType") ) {
			return 2;
		}
		if( strFieldName.equals("AppealState") ) {
			return 3;
		}
		if( strFieldName.equals("AppeanRCode") ) {
			return 4;
		}
		if( strFieldName.equals("AppealReason") ) {
			return 5;
		}
		if( strFieldName.equals("AppealRDesc") ) {
			return 6;
		}
		if( strFieldName.equals("WaitDate") ) {
			return 7;
		}
		if( strFieldName.equals("AppealName") ) {
			return 8;
		}
		if( strFieldName.equals("AppealSex") ) {
			return 9;
		}
		if( strFieldName.equals("Address") ) {
			return 10;
		}
		if( strFieldName.equals("Phone") ) {
			return 11;
		}
		if( strFieldName.equals("Mobile") ) {
			return 12;
		}
		if( strFieldName.equals("AppealMode") ) {
			return 13;
		}
		if( strFieldName.equals("Relation") ) {
			return 14;
		}
		if( strFieldName.equals("PostCode") ) {
			return 15;
		}
		if( strFieldName.equals("IDType") ) {
			return 16;
		}
		if( strFieldName.equals("IDNo") ) {
			return 17;
		}
		if( strFieldName.equals("ReturnMode") ) {
			return 18;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 19;
		}
		if( strFieldName.equals("Operator") ) {
			return 20;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 21;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 24;
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
				strFieldName = "AppealNo";
				break;
			case 1:
				strFieldName = "CaseNo";
				break;
			case 2:
				strFieldName = "AppealType";
				break;
			case 3:
				strFieldName = "AppealState";
				break;
			case 4:
				strFieldName = "AppeanRCode";
				break;
			case 5:
				strFieldName = "AppealReason";
				break;
			case 6:
				strFieldName = "AppealRDesc";
				break;
			case 7:
				strFieldName = "WaitDate";
				break;
			case 8:
				strFieldName = "AppealName";
				break;
			case 9:
				strFieldName = "AppealSex";
				break;
			case 10:
				strFieldName = "Address";
				break;
			case 11:
				strFieldName = "Phone";
				break;
			case 12:
				strFieldName = "Mobile";
				break;
			case 13:
				strFieldName = "AppealMode";
				break;
			case 14:
				strFieldName = "Relation";
				break;
			case 15:
				strFieldName = "PostCode";
				break;
			case 16:
				strFieldName = "IDType";
				break;
			case 17:
				strFieldName = "IDNo";
				break;
			case 18:
				strFieldName = "ReturnMode";
				break;
			case 19:
				strFieldName = "ManageCom";
				break;
			case 20:
				strFieldName = "Operator";
				break;
			case 21:
				strFieldName = "MakeDate";
				break;
			case 22:
				strFieldName = "MakeTime";
				break;
			case 23:
				strFieldName = "ModifyDate";
				break;
			case 24:
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
		if( strFieldName.equals("AppealNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppealType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppealState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppeanRCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppealReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppealRDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WaitDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppealName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppealSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Address") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mobile") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppealMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Relation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PostCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReturnMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
