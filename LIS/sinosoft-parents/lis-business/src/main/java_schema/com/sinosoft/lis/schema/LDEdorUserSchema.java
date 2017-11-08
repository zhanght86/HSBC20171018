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
import com.sinosoft.lis.db.LDEdorUserDB;

/*
 * <p>ClassName: LDEdorUserSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LDEdorUserSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDEdorUserSchema.class);

	// @Field
	/** 保全员编码 */
	private String UserCode;
	/** 保全员类别 */
	private String UserType;
	/** 上级保全员 */
	private String UpUserCode;
	/** 上级保全员级别 */
	private String UpEdorPopedom;
	/** 其他上级保全员 */
	private String OtherUserCode;
	/** 其他上级保全员级别 */
	private String OtherUpEdorPopedom;
	/** 保全员组别 */
	private String UWBranchCode;
	/** 保全权限级别 */
	private String EdorPopedom;
	/** 保全权限名称描述 */
	private String UserDescription;
	/** 保全员状态 */
	private String UserState;
	/** 有效开始日期 */
	private Date ValidStartDate;
	/** 有效结束日期 */
	private Date ValidEndDate;
	/** 是否暂停 */
	private String IsPendFlag;
	/** 暂停原因 */
	private String PendReason;
	/** 备注 */
	private String Remark;
	/** 操作员代码 */
	private String Operator;
	/** 管理机构 */
	private String ManageCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 21;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDEdorUserSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "UserCode";
		pk[1] = "UserType";

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
		LDEdorUserSchema cloned = (LDEdorUserSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getUserCode()
	{
		return UserCode;
	}
	public void setUserCode(String aUserCode)
	{
		UserCode = aUserCode;
	}
	/**
	* 1--个人保全用户<p>
	* 2--团体保全用户
	*/
	public String getUserType()
	{
		return UserType;
	}
	public void setUserType(String aUserType)
	{
		UserType = aUserType;
	}
	public String getUpUserCode()
	{
		return UpUserCode;
	}
	public void setUpUserCode(String aUpUserCode)
	{
		UpUserCode = aUpUserCode;
	}
	/**
	* 0--电话中心保全权限<p>
	* A--A级保全权限<p>
	* .......<p>
	* P--P级保全权限
	*/
	public String getUpEdorPopedom()
	{
		return UpEdorPopedom;
	}
	public void setUpEdorPopedom(String aUpEdorPopedom)
	{
		UpEdorPopedom = aUpEdorPopedom;
	}
	/**
	* 01-银代经理<p>
	* 02-银代协理<p>
	* 03-渠道经理<p>
	* 04-银代客户经理<p>
	* 05-培训岗<p>
	* 06-企划策划岗<p>
	* 07-销售支援岗<p>
	* 08-综合岗<p>
	* <p>
	* <p>
	* 10-普通代理人<p>
	* 11-组主管<p>
	* 12-部主管<p>
	* 13-督导长<p>
	* 14-区域督导长<p>
	* <p>
	* <p>
	* 20-法人客户经理<p>
	* 21-法人组经理<p>
	* 22-法人协理<p>
	* 23－法人部经理
	*/
	public String getOtherUserCode()
	{
		return OtherUserCode;
	}
	public void setOtherUserCode(String aOtherUserCode)
	{
		OtherUserCode = aOtherUserCode;
	}
	/**
	* 0--电话中心保全权限<p>
	* A--A级保全权限<p>
	* .......<p>
	* P--P级保全权限
	*/
	public String getOtherUpEdorPopedom()
	{
		return OtherUpEdorPopedom;
	}
	public void setOtherUpEdorPopedom(String aOtherUpEdorPopedom)
	{
		OtherUpEdorPopedom = aOtherUpEdorPopedom;
	}
	public String getUWBranchCode()
	{
		return UWBranchCode;
	}
	public void setUWBranchCode(String aUWBranchCode)
	{
		UWBranchCode = aUWBranchCode;
	}
	/**
	* 0--电话中心保全权限<p>
	* A--A级保全权限<p>
	* .......<p>
	* P--P级保全权限
	*/
	public String getEdorPopedom()
	{
		return EdorPopedom;
	}
	public void setEdorPopedom(String aEdorPopedom)
	{
		EdorPopedom = aEdorPopedom;
	}
	public String getUserDescription()
	{
		return UserDescription;
	}
	public void setUserDescription(String aUserDescription)
	{
		UserDescription = aUserDescription;
	}
	/**
	* userstate	0	有效<p>
	* userstate	1	失效
	*/
	public String getUserState()
	{
		return UserState;
	}
	public void setUserState(String aUserState)
	{
		UserState = aUserState;
	}
	public String getValidStartDate()
	{
		if( ValidStartDate != null )
			return fDate.getString(ValidStartDate);
		else
			return null;
	}
	public void setValidStartDate(Date aValidStartDate)
	{
		ValidStartDate = aValidStartDate;
	}
	public void setValidStartDate(String aValidStartDate)
	{
		if (aValidStartDate != null && !aValidStartDate.equals("") )
		{
			ValidStartDate = fDate.getDate( aValidStartDate );
		}
		else
			ValidStartDate = null;
	}

	public String getValidEndDate()
	{
		if( ValidEndDate != null )
			return fDate.getString(ValidEndDate);
		else
			return null;
	}
	public void setValidEndDate(Date aValidEndDate)
	{
		ValidEndDate = aValidEndDate;
	}
	public void setValidEndDate(String aValidEndDate)
	{
		if (aValidEndDate != null && !aValidEndDate.equals("") )
		{
			ValidEndDate = fDate.getDate( aValidEndDate );
		}
		else
			ValidEndDate = null;
	}

	public String getIsPendFlag()
	{
		return IsPendFlag;
	}
	public void setIsPendFlag(String aIsPendFlag)
	{
		IsPendFlag = aIsPendFlag;
	}
	public String getPendReason()
	{
		return PendReason;
	}
	public void setPendReason(String aPendReason)
	{
		PendReason = aPendReason;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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
	* 使用另外一个 LDEdorUserSchema 对象给 Schema 赋值
	* @param: aLDEdorUserSchema LDEdorUserSchema
	**/
	public void setSchema(LDEdorUserSchema aLDEdorUserSchema)
	{
		this.UserCode = aLDEdorUserSchema.getUserCode();
		this.UserType = aLDEdorUserSchema.getUserType();
		this.UpUserCode = aLDEdorUserSchema.getUpUserCode();
		this.UpEdorPopedom = aLDEdorUserSchema.getUpEdorPopedom();
		this.OtherUserCode = aLDEdorUserSchema.getOtherUserCode();
		this.OtherUpEdorPopedom = aLDEdorUserSchema.getOtherUpEdorPopedom();
		this.UWBranchCode = aLDEdorUserSchema.getUWBranchCode();
		this.EdorPopedom = aLDEdorUserSchema.getEdorPopedom();
		this.UserDescription = aLDEdorUserSchema.getUserDescription();
		this.UserState = aLDEdorUserSchema.getUserState();
		this.ValidStartDate = fDate.getDate( aLDEdorUserSchema.getValidStartDate());
		this.ValidEndDate = fDate.getDate( aLDEdorUserSchema.getValidEndDate());
		this.IsPendFlag = aLDEdorUserSchema.getIsPendFlag();
		this.PendReason = aLDEdorUserSchema.getPendReason();
		this.Remark = aLDEdorUserSchema.getRemark();
		this.Operator = aLDEdorUserSchema.getOperator();
		this.ManageCom = aLDEdorUserSchema.getManageCom();
		this.MakeDate = fDate.getDate( aLDEdorUserSchema.getMakeDate());
		this.MakeTime = aLDEdorUserSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDEdorUserSchema.getModifyDate());
		this.ModifyTime = aLDEdorUserSchema.getModifyTime();
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
			if( rs.getString("UserCode") == null )
				this.UserCode = null;
			else
				this.UserCode = rs.getString("UserCode").trim();

			if( rs.getString("UserType") == null )
				this.UserType = null;
			else
				this.UserType = rs.getString("UserType").trim();

			if( rs.getString("UpUserCode") == null )
				this.UpUserCode = null;
			else
				this.UpUserCode = rs.getString("UpUserCode").trim();

			if( rs.getString("UpEdorPopedom") == null )
				this.UpEdorPopedom = null;
			else
				this.UpEdorPopedom = rs.getString("UpEdorPopedom").trim();

			if( rs.getString("OtherUserCode") == null )
				this.OtherUserCode = null;
			else
				this.OtherUserCode = rs.getString("OtherUserCode").trim();

			if( rs.getString("OtherUpEdorPopedom") == null )
				this.OtherUpEdorPopedom = null;
			else
				this.OtherUpEdorPopedom = rs.getString("OtherUpEdorPopedom").trim();

			if( rs.getString("UWBranchCode") == null )
				this.UWBranchCode = null;
			else
				this.UWBranchCode = rs.getString("UWBranchCode").trim();

			if( rs.getString("EdorPopedom") == null )
				this.EdorPopedom = null;
			else
				this.EdorPopedom = rs.getString("EdorPopedom").trim();

			if( rs.getString("UserDescription") == null )
				this.UserDescription = null;
			else
				this.UserDescription = rs.getString("UserDescription").trim();

			if( rs.getString("UserState") == null )
				this.UserState = null;
			else
				this.UserState = rs.getString("UserState").trim();

			this.ValidStartDate = rs.getDate("ValidStartDate");
			this.ValidEndDate = rs.getDate("ValidEndDate");
			if( rs.getString("IsPendFlag") == null )
				this.IsPendFlag = null;
			else
				this.IsPendFlag = rs.getString("IsPendFlag").trim();

			if( rs.getString("PendReason") == null )
				this.PendReason = null;
			else
				this.PendReason = rs.getString("PendReason").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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
			logger.debug("数据库中的LDEdorUser表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDEdorUserSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDEdorUserSchema getSchema()
	{
		LDEdorUserSchema aLDEdorUserSchema = new LDEdorUserSchema();
		aLDEdorUserSchema.setSchema(this);
		return aLDEdorUserSchema;
	}

	public LDEdorUserDB getDB()
	{
		LDEdorUserDB aDBOper = new LDEdorUserDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDEdorUser描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(UserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UserType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpUserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpEdorPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherUserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherUpEdorPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWBranchCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UserDescription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UserState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ValidStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ValidEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsPendFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PendReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDEdorUser>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			UserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			UserType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			UpUserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			UpEdorPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OtherUserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OtherUpEdorPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			UWBranchCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			EdorPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			UserDescription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			UserState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ValidStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ValidEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			IsPendFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PendReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDEdorUserSchema";
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
		if (FCode.equalsIgnoreCase("UserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserCode));
		}
		if (FCode.equalsIgnoreCase("UserType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserType));
		}
		if (FCode.equalsIgnoreCase("UpUserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpUserCode));
		}
		if (FCode.equalsIgnoreCase("UpEdorPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpEdorPopedom));
		}
		if (FCode.equalsIgnoreCase("OtherUserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherUserCode));
		}
		if (FCode.equalsIgnoreCase("OtherUpEdorPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherUpEdorPopedom));
		}
		if (FCode.equalsIgnoreCase("UWBranchCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWBranchCode));
		}
		if (FCode.equalsIgnoreCase("EdorPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorPopedom));
		}
		if (FCode.equalsIgnoreCase("UserDescription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserDescription));
		}
		if (FCode.equalsIgnoreCase("UserState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserState));
		}
		if (FCode.equalsIgnoreCase("ValidStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getValidStartDate()));
		}
		if (FCode.equalsIgnoreCase("ValidEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getValidEndDate()));
		}
		if (FCode.equalsIgnoreCase("IsPendFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsPendFlag));
		}
		if (FCode.equalsIgnoreCase("PendReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PendReason));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
				strFieldValue = StrTool.GBKToUnicode(UserCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(UserType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(UpUserCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(UpEdorPopedom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OtherUserCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OtherUpEdorPopedom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(UWBranchCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(EdorPopedom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(UserDescription);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(UserState);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getValidStartDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getValidEndDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(IsPendFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(PendReason);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 20:
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

		if (FCode.equalsIgnoreCase("UserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserCode = FValue.trim();
			}
			else
				UserCode = null;
		}
		if (FCode.equalsIgnoreCase("UserType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserType = FValue.trim();
			}
			else
				UserType = null;
		}
		if (FCode.equalsIgnoreCase("UpUserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpUserCode = FValue.trim();
			}
			else
				UpUserCode = null;
		}
		if (FCode.equalsIgnoreCase("UpEdorPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpEdorPopedom = FValue.trim();
			}
			else
				UpEdorPopedom = null;
		}
		if (FCode.equalsIgnoreCase("OtherUserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherUserCode = FValue.trim();
			}
			else
				OtherUserCode = null;
		}
		if (FCode.equalsIgnoreCase("OtherUpEdorPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherUpEdorPopedom = FValue.trim();
			}
			else
				OtherUpEdorPopedom = null;
		}
		if (FCode.equalsIgnoreCase("UWBranchCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWBranchCode = FValue.trim();
			}
			else
				UWBranchCode = null;
		}
		if (FCode.equalsIgnoreCase("EdorPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorPopedom = FValue.trim();
			}
			else
				EdorPopedom = null;
		}
		if (FCode.equalsIgnoreCase("UserDescription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserDescription = FValue.trim();
			}
			else
				UserDescription = null;
		}
		if (FCode.equalsIgnoreCase("UserState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserState = FValue.trim();
			}
			else
				UserState = null;
		}
		if (FCode.equalsIgnoreCase("ValidStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ValidStartDate = fDate.getDate( FValue );
			}
			else
				ValidStartDate = null;
		}
		if (FCode.equalsIgnoreCase("ValidEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ValidEndDate = fDate.getDate( FValue );
			}
			else
				ValidEndDate = null;
		}
		if (FCode.equalsIgnoreCase("IsPendFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsPendFlag = FValue.trim();
			}
			else
				IsPendFlag = null;
		}
		if (FCode.equalsIgnoreCase("PendReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PendReason = FValue.trim();
			}
			else
				PendReason = null;
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
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
		LDEdorUserSchema other = (LDEdorUserSchema)otherObject;
		return
			UserCode.equals(other.getUserCode())
			&& UserType.equals(other.getUserType())
			&& UpUserCode.equals(other.getUpUserCode())
			&& UpEdorPopedom.equals(other.getUpEdorPopedom())
			&& OtherUserCode.equals(other.getOtherUserCode())
			&& OtherUpEdorPopedom.equals(other.getOtherUpEdorPopedom())
			&& UWBranchCode.equals(other.getUWBranchCode())
			&& EdorPopedom.equals(other.getEdorPopedom())
			&& UserDescription.equals(other.getUserDescription())
			&& UserState.equals(other.getUserState())
			&& fDate.getString(ValidStartDate).equals(other.getValidStartDate())
			&& fDate.getString(ValidEndDate).equals(other.getValidEndDate())
			&& IsPendFlag.equals(other.getIsPendFlag())
			&& PendReason.equals(other.getPendReason())
			&& Remark.equals(other.getRemark())
			&& Operator.equals(other.getOperator())
			&& ManageCom.equals(other.getManageCom())
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
		if( strFieldName.equals("UserCode") ) {
			return 0;
		}
		if( strFieldName.equals("UserType") ) {
			return 1;
		}
		if( strFieldName.equals("UpUserCode") ) {
			return 2;
		}
		if( strFieldName.equals("UpEdorPopedom") ) {
			return 3;
		}
		if( strFieldName.equals("OtherUserCode") ) {
			return 4;
		}
		if( strFieldName.equals("OtherUpEdorPopedom") ) {
			return 5;
		}
		if( strFieldName.equals("UWBranchCode") ) {
			return 6;
		}
		if( strFieldName.equals("EdorPopedom") ) {
			return 7;
		}
		if( strFieldName.equals("UserDescription") ) {
			return 8;
		}
		if( strFieldName.equals("UserState") ) {
			return 9;
		}
		if( strFieldName.equals("ValidStartDate") ) {
			return 10;
		}
		if( strFieldName.equals("ValidEndDate") ) {
			return 11;
		}
		if( strFieldName.equals("IsPendFlag") ) {
			return 12;
		}
		if( strFieldName.equals("PendReason") ) {
			return 13;
		}
		if( strFieldName.equals("Remark") ) {
			return 14;
		}
		if( strFieldName.equals("Operator") ) {
			return 15;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 16;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 17;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 20;
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
				strFieldName = "UserCode";
				break;
			case 1:
				strFieldName = "UserType";
				break;
			case 2:
				strFieldName = "UpUserCode";
				break;
			case 3:
				strFieldName = "UpEdorPopedom";
				break;
			case 4:
				strFieldName = "OtherUserCode";
				break;
			case 5:
				strFieldName = "OtherUpEdorPopedom";
				break;
			case 6:
				strFieldName = "UWBranchCode";
				break;
			case 7:
				strFieldName = "EdorPopedom";
				break;
			case 8:
				strFieldName = "UserDescription";
				break;
			case 9:
				strFieldName = "UserState";
				break;
			case 10:
				strFieldName = "ValidStartDate";
				break;
			case 11:
				strFieldName = "ValidEndDate";
				break;
			case 12:
				strFieldName = "IsPendFlag";
				break;
			case 13:
				strFieldName = "PendReason";
				break;
			case 14:
				strFieldName = "Remark";
				break;
			case 15:
				strFieldName = "Operator";
				break;
			case 16:
				strFieldName = "ManageCom";
				break;
			case 17:
				strFieldName = "MakeDate";
				break;
			case 18:
				strFieldName = "MakeTime";
				break;
			case 19:
				strFieldName = "ModifyDate";
				break;
			case 20:
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
		if( strFieldName.equals("UserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UserType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpUserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpEdorPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherUserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherUpEdorPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWBranchCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UserDescription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UserState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValidStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ValidEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IsPendFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PendReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
