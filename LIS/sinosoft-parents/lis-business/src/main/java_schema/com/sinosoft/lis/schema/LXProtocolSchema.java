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
import com.sinosoft.lis.db.LXProtocolDB;

/*
 * <p>ClassName: LXProtocolSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 兼业系统
 */
public class LXProtocolSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LXProtocolSchema.class);

	// @Field
	/** 协议号 */
	private String ProtocolNo;
	/** 协议类型 */
	private String ProtocolType;
	/** 代理机构 */
	private String AgentCom;
	/** 启用日期 */
	private Date StartDate;
	/** 终止日期 */
	private Date EndDate;
	/** 有效标记 */
	private String AvailFlag;
	/** 状态原因 */
	private String AvailReason;
	/** 协议描述 */
	private String ProtocolDec;
	/** 审核人 */
	private String Auditer;
	/** 审核日期 */
	private Date AuditDate;
	/** 审核结论 */
	private String AuditConclusion;
	/** 审核意见 */
	private String AuditRmark;
	/** 管理机构 */
	private String ManageCom;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 协议名称 */
	private String ProtocolName;

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LXProtocolSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ProtocolNo";

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
		LXProtocolSchema cloned = (LXProtocolSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getProtocolNo()
	{
		return ProtocolNo;
	}
	public void setProtocolNo(String aProtocolNo)
	{
		ProtocolNo = aProtocolNo;
	}
	/**
	* 0 -- 航意险类<p>
	* 1 -- 旅游险类
	*/
	public String getProtocolType()
	{
		return ProtocolType;
	}
	public void setProtocolType(String aProtocolType)
	{
		ProtocolType = aProtocolType;
	}
	public String getAgentCom()
	{
		return AgentCom;
	}
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	/**
	* 0 -- 申请<p>
	* 1 -- 复核<p>
	* 2 -- 有效<p>
	* 3 -- 终止<p>
	* 4 -- 特权终止
	*/
	public String getAvailFlag()
	{
		return AvailFlag;
	}
	public void setAvailFlag(String aAvailFlag)
	{
		AvailFlag = aAvailFlag;
	}
	public String getAvailReason()
	{
		return AvailReason;
	}
	public void setAvailReason(String aAvailReason)
	{
		AvailReason = aAvailReason;
	}
	public String getProtocolDec()
	{
		return ProtocolDec;
	}
	public void setProtocolDec(String aProtocolDec)
	{
		ProtocolDec = aProtocolDec;
	}
	public String getAuditer()
	{
		return Auditer;
	}
	public void setAuditer(String aAuditer)
	{
		Auditer = aAuditer;
	}
	public String getAuditDate()
	{
		if( AuditDate != null )
			return fDate.getString(AuditDate);
		else
			return null;
	}
	public void setAuditDate(Date aAuditDate)
	{
		AuditDate = aAuditDate;
	}
	public void setAuditDate(String aAuditDate)
	{
		if (aAuditDate != null && !aAuditDate.equals("") )
		{
			AuditDate = fDate.getDate( aAuditDate );
		}
		else
			AuditDate = null;
	}

	public String getAuditConclusion()
	{
		return AuditConclusion;
	}
	public void setAuditConclusion(String aAuditConclusion)
	{
		AuditConclusion = aAuditConclusion;
	}
	public String getAuditRmark()
	{
		return AuditRmark;
	}
	public void setAuditRmark(String aAuditRmark)
	{
		AuditRmark = aAuditRmark;
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
	* 存协议简称
	*/
	public String getProtocolName()
	{
		return ProtocolName;
	}
	public void setProtocolName(String aProtocolName)
	{
		ProtocolName = aProtocolName;
	}

	/**
	* 使用另外一个 LXProtocolSchema 对象给 Schema 赋值
	* @param: aLXProtocolSchema LXProtocolSchema
	**/
	public void setSchema(LXProtocolSchema aLXProtocolSchema)
	{
		this.ProtocolNo = aLXProtocolSchema.getProtocolNo();
		this.ProtocolType = aLXProtocolSchema.getProtocolType();
		this.AgentCom = aLXProtocolSchema.getAgentCom();
		this.StartDate = fDate.getDate( aLXProtocolSchema.getStartDate());
		this.EndDate = fDate.getDate( aLXProtocolSchema.getEndDate());
		this.AvailFlag = aLXProtocolSchema.getAvailFlag();
		this.AvailReason = aLXProtocolSchema.getAvailReason();
		this.ProtocolDec = aLXProtocolSchema.getProtocolDec();
		this.Auditer = aLXProtocolSchema.getAuditer();
		this.AuditDate = fDate.getDate( aLXProtocolSchema.getAuditDate());
		this.AuditConclusion = aLXProtocolSchema.getAuditConclusion();
		this.AuditRmark = aLXProtocolSchema.getAuditRmark();
		this.ManageCom = aLXProtocolSchema.getManageCom();
		this.Operator = aLXProtocolSchema.getOperator();
		this.MakeDate = fDate.getDate( aLXProtocolSchema.getMakeDate());
		this.ModifyDate = fDate.getDate( aLXProtocolSchema.getModifyDate());
		this.ModifyTime = aLXProtocolSchema.getModifyTime();
		this.ProtocolName = aLXProtocolSchema.getProtocolName();
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
			if( rs.getString("ProtocolNo") == null )
				this.ProtocolNo = null;
			else
				this.ProtocolNo = rs.getString("ProtocolNo").trim();

			if( rs.getString("ProtocolType") == null )
				this.ProtocolType = null;
			else
				this.ProtocolType = rs.getString("ProtocolType").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("AvailFlag") == null )
				this.AvailFlag = null;
			else
				this.AvailFlag = rs.getString("AvailFlag").trim();

			if( rs.getString("AvailReason") == null )
				this.AvailReason = null;
			else
				this.AvailReason = rs.getString("AvailReason").trim();

			if( rs.getString("ProtocolDec") == null )
				this.ProtocolDec = null;
			else
				this.ProtocolDec = rs.getString("ProtocolDec").trim();

			if( rs.getString("Auditer") == null )
				this.Auditer = null;
			else
				this.Auditer = rs.getString("Auditer").trim();

			this.AuditDate = rs.getDate("AuditDate");
			if( rs.getString("AuditConclusion") == null )
				this.AuditConclusion = null;
			else
				this.AuditConclusion = rs.getString("AuditConclusion").trim();

			if( rs.getString("AuditRmark") == null )
				this.AuditRmark = null;
			else
				this.AuditRmark = rs.getString("AuditRmark").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("ProtocolName") == null )
				this.ProtocolName = null;
			else
				this.ProtocolName = rs.getString("ProtocolName").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LXProtocol表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LXProtocolSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LXProtocolSchema getSchema()
	{
		LXProtocolSchema aLXProtocolSchema = new LXProtocolSchema();
		aLXProtocolSchema.setSchema(this);
		return aLXProtocolSchema;
	}

	public LXProtocolDB getDB()
	{
		LXProtocolDB aDBOper = new LXProtocolDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLXProtocol描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ProtocolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProtocolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AvailFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AvailReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProtocolDec)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Auditer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AuditDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditRmark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProtocolName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLXProtocol>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ProtocolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ProtocolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			AvailFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AvailReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ProtocolDec = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Auditer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AuditDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			AuditConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AuditRmark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ProtocolName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LXProtocolSchema";
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
		if (FCode.equalsIgnoreCase("ProtocolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProtocolNo));
		}
		if (FCode.equalsIgnoreCase("ProtocolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProtocolType));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("AvailFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvailFlag));
		}
		if (FCode.equalsIgnoreCase("AvailReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvailReason));
		}
		if (FCode.equalsIgnoreCase("ProtocolDec"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProtocolDec));
		}
		if (FCode.equalsIgnoreCase("Auditer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Auditer));
		}
		if (FCode.equalsIgnoreCase("AuditDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAuditDate()));
		}
		if (FCode.equalsIgnoreCase("AuditConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditConclusion));
		}
		if (FCode.equalsIgnoreCase("AuditRmark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditRmark));
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
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("ProtocolName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProtocolName));
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
				strFieldValue = StrTool.GBKToUnicode(ProtocolNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ProtocolType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AvailFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AvailReason);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ProtocolDec);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Auditer);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAuditDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AuditConclusion);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AuditRmark);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ProtocolName);
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

		if (FCode.equalsIgnoreCase("ProtocolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProtocolNo = FValue.trim();
			}
			else
				ProtocolNo = null;
		}
		if (FCode.equalsIgnoreCase("ProtocolType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProtocolType = FValue.trim();
			}
			else
				ProtocolType = null;
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCom = FValue.trim();
			}
			else
				AgentCom = null;
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("AvailFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AvailFlag = FValue.trim();
			}
			else
				AvailFlag = null;
		}
		if (FCode.equalsIgnoreCase("AvailReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AvailReason = FValue.trim();
			}
			else
				AvailReason = null;
		}
		if (FCode.equalsIgnoreCase("ProtocolDec"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProtocolDec = FValue.trim();
			}
			else
				ProtocolDec = null;
		}
		if (FCode.equalsIgnoreCase("Auditer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Auditer = FValue.trim();
			}
			else
				Auditer = null;
		}
		if (FCode.equalsIgnoreCase("AuditDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AuditDate = fDate.getDate( FValue );
			}
			else
				AuditDate = null;
		}
		if (FCode.equalsIgnoreCase("AuditConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditConclusion = FValue.trim();
			}
			else
				AuditConclusion = null;
		}
		if (FCode.equalsIgnoreCase("AuditRmark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditRmark = FValue.trim();
			}
			else
				AuditRmark = null;
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
		if (FCode.equalsIgnoreCase("ProtocolName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProtocolName = FValue.trim();
			}
			else
				ProtocolName = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LXProtocolSchema other = (LXProtocolSchema)otherObject;
		return
			ProtocolNo.equals(other.getProtocolNo())
			&& ProtocolType.equals(other.getProtocolType())
			&& AgentCom.equals(other.getAgentCom())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& AvailFlag.equals(other.getAvailFlag())
			&& AvailReason.equals(other.getAvailReason())
			&& ProtocolDec.equals(other.getProtocolDec())
			&& Auditer.equals(other.getAuditer())
			&& fDate.getString(AuditDate).equals(other.getAuditDate())
			&& AuditConclusion.equals(other.getAuditConclusion())
			&& AuditRmark.equals(other.getAuditRmark())
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ProtocolName.equals(other.getProtocolName());
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
		if( strFieldName.equals("ProtocolNo") ) {
			return 0;
		}
		if( strFieldName.equals("ProtocolType") ) {
			return 1;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 2;
		}
		if( strFieldName.equals("StartDate") ) {
			return 3;
		}
		if( strFieldName.equals("EndDate") ) {
			return 4;
		}
		if( strFieldName.equals("AvailFlag") ) {
			return 5;
		}
		if( strFieldName.equals("AvailReason") ) {
			return 6;
		}
		if( strFieldName.equals("ProtocolDec") ) {
			return 7;
		}
		if( strFieldName.equals("Auditer") ) {
			return 8;
		}
		if( strFieldName.equals("AuditDate") ) {
			return 9;
		}
		if( strFieldName.equals("AuditConclusion") ) {
			return 10;
		}
		if( strFieldName.equals("AuditRmark") ) {
			return 11;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 12;
		}
		if( strFieldName.equals("Operator") ) {
			return 13;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 16;
		}
		if( strFieldName.equals("ProtocolName") ) {
			return 17;
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
				strFieldName = "ProtocolNo";
				break;
			case 1:
				strFieldName = "ProtocolType";
				break;
			case 2:
				strFieldName = "AgentCom";
				break;
			case 3:
				strFieldName = "StartDate";
				break;
			case 4:
				strFieldName = "EndDate";
				break;
			case 5:
				strFieldName = "AvailFlag";
				break;
			case 6:
				strFieldName = "AvailReason";
				break;
			case 7:
				strFieldName = "ProtocolDec";
				break;
			case 8:
				strFieldName = "Auditer";
				break;
			case 9:
				strFieldName = "AuditDate";
				break;
			case 10:
				strFieldName = "AuditConclusion";
				break;
			case 11:
				strFieldName = "AuditRmark";
				break;
			case 12:
				strFieldName = "ManageCom";
				break;
			case 13:
				strFieldName = "Operator";
				break;
			case 14:
				strFieldName = "MakeDate";
				break;
			case 15:
				strFieldName = "ModifyDate";
				break;
			case 16:
				strFieldName = "ModifyTime";
				break;
			case 17:
				strFieldName = "ProtocolName";
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
		if( strFieldName.equals("ProtocolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProtocolType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AvailFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AvailReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProtocolDec") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Auditer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AuditConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditRmark") ) {
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
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProtocolName") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
