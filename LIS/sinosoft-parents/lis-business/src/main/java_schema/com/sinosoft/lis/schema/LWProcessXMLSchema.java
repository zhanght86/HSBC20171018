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
import com.sinosoft.lis.db.LWProcessXMLDB;

/*
 * <p>ClassName: LWProcessXMLSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 工作流模型
 */
public class LWProcessXMLSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LWProcessXMLSchema.class);
	// @Field
	/** 过程id */
	private String ProcessID;
	/** 时效id */
	private String TimeID;
	/** 过程名 */
	private String ProcessName;
	/** 过程说明 */
	private String ProcessDesc;
	/** 平均等待时间 */
	private int WatingTime;
	/** 平均执行时间 */
	private int WorkingTime;
	/** 业务类型 */
	private String BusiType;
	/** 生效标志 */
	private String ValuedFlag;
	/** 系统标志 */
	private String SysFlag;
	/** 管理机构 */
	private String ManageCom;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** Xml */
	private InputStream WFXML;
	/** 过程版本号 */
	private String Version;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LWProcessXMLSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ProcessID";
		pk[1] = "Version";

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
		LWProcessXMLSchema cloned = (LWProcessXMLSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getProcessID()
	{
		return ProcessID;
	}
	public void setProcessID(String aProcessID)
	{
		if(aProcessID!=null && aProcessID.length()>10)
			throw new IllegalArgumentException("过程idProcessID值"+aProcessID+"的长度"+aProcessID.length()+"大于最大值10");
		ProcessID = aProcessID;
	}
	public String getTimeID()
	{
		return TimeID;
	}
	public void setTimeID(String aTimeID)
	{
		if(aTimeID!=null && aTimeID.length()>20)
			throw new IllegalArgumentException("时效idTimeID值"+aTimeID+"的长度"+aTimeID.length()+"大于最大值20");
		TimeID = aTimeID;
	}
	public String getProcessName()
	{
		return ProcessName;
	}
	public void setProcessName(String aProcessName)
	{
		if(aProcessName!=null && aProcessName.length()>90)
			throw new IllegalArgumentException("过程名ProcessName值"+aProcessName+"的长度"+aProcessName.length()+"大于最大值90");
		ProcessName = aProcessName;
	}
	public String getProcessDesc()
	{
		return ProcessDesc;
	}
	public void setProcessDesc(String aProcessDesc)
	{
		if(aProcessDesc!=null && aProcessDesc.length()>100)
			throw new IllegalArgumentException("过程说明ProcessDesc值"+aProcessDesc+"的长度"+aProcessDesc.length()+"大于最大值100");
		ProcessDesc = aProcessDesc;
	}
	public int getWatingTime()
	{
		return WatingTime;
	}
	public void setWatingTime(int aWatingTime)
	{
		WatingTime = aWatingTime;
	}
	public void setWatingTime(String aWatingTime)
	{
		if (aWatingTime != null && !aWatingTime.equals(""))
		{
			Integer tInteger = new Integer(aWatingTime);
			int i = tInteger.intValue();
			WatingTime = i;
		}
	}

	public int getWorkingTime()
	{
		return WorkingTime;
	}
	public void setWorkingTime(int aWorkingTime)
	{
		WorkingTime = aWorkingTime;
	}
	public void setWorkingTime(String aWorkingTime)
	{
		if (aWorkingTime != null && !aWorkingTime.equals(""))
		{
			Integer tInteger = new Integer(aWorkingTime);
			int i = tInteger.intValue();
			WorkingTime = i;
		}
	}

	public String getBusiType()
	{
		return BusiType;
	}
	public void setBusiType(String aBusiType)
	{
		if(aBusiType!=null && aBusiType.length()>4)
			throw new IllegalArgumentException("业务类型BusiType值"+aBusiType+"的长度"+aBusiType.length()+"大于最大值4");
		BusiType = aBusiType;
	}
	/**
	* 0--无效<p>
	* 1--有效
	*/
	public String getValuedFlag()
	{
		return ValuedFlag;
	}
	public void setValuedFlag(String aValuedFlag)
	{
		if(aValuedFlag!=null && aValuedFlag.length()>2)
			throw new IllegalArgumentException("生效标志ValuedFlag值"+aValuedFlag+"的长度"+aValuedFlag.length()+"大于最大值2");
		ValuedFlag = aValuedFlag;
	}
	/**
	* P--账管系统<p>
	* T--受托系统<p>
	* L-寿险系统
	*/
	public String getSysFlag()
	{
		return SysFlag;
	}
	public void setSysFlag(String aSysFlag)
	{
		if(aSysFlag!=null && aSysFlag.length()>2)
			throw new IllegalArgumentException("系统标志SysFlag值"+aSysFlag+"的长度"+aSysFlag.length()+"大于最大值2");
		SysFlag = aSysFlag;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>10)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值10");
		ManageCom = aManageCom;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员代码Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
	public InputStream getWFXML()
	{
		return WFXML;
	}
	public void setWFXML(InputStream aWFXML)
	{
		WFXML = aWFXML;
	}
	public void setWFXML(String aWFXML)
	{
	}

	public String getVersion()
	{
		return Version;
	}
	public void setVersion(String aVersion)
	{
		if(aVersion!=null && aVersion.length()>20)
			throw new IllegalArgumentException("过程版本号Version值"+aVersion+"的长度"+aVersion.length()+"大于最大值20");
		Version = aVersion;
	}

	/**
	* 使用另外一个 LWProcessXMLSchema 对象给 Schema 赋值
	* @param: aLWProcessXMLSchema LWProcessXMLSchema
	**/
	public void setSchema(LWProcessXMLSchema aLWProcessXMLSchema)
	{
		this.ProcessID = aLWProcessXMLSchema.getProcessID();
		this.TimeID = aLWProcessXMLSchema.getTimeID();
		this.ProcessName = aLWProcessXMLSchema.getProcessName();
		this.ProcessDesc = aLWProcessXMLSchema.getProcessDesc();
		this.WatingTime = aLWProcessXMLSchema.getWatingTime();
		this.WorkingTime = aLWProcessXMLSchema.getWorkingTime();
		this.BusiType = aLWProcessXMLSchema.getBusiType();
		this.ValuedFlag = aLWProcessXMLSchema.getValuedFlag();
		this.SysFlag = aLWProcessXMLSchema.getSysFlag();
		this.ManageCom = aLWProcessXMLSchema.getManageCom();
		this.Operator = aLWProcessXMLSchema.getOperator();
		this.MakeDate = fDate.getDate( aLWProcessXMLSchema.getMakeDate());
		this.MakeTime = aLWProcessXMLSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLWProcessXMLSchema.getModifyDate());
		this.ModifyTime = aLWProcessXMLSchema.getModifyTime();
		this.WFXML = aLWProcessXMLSchema.getWFXML();
		this.Version = aLWProcessXMLSchema.getVersion();
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
			if( rs.getString("ProcessID") == null )
				this.ProcessID = null;
			else
				this.ProcessID = rs.getString("ProcessID").trim();

			if( rs.getString("TimeID") == null )
				this.TimeID = null;
			else
				this.TimeID = rs.getString("TimeID").trim();

			if( rs.getString("ProcessName") == null )
				this.ProcessName = null;
			else
				this.ProcessName = rs.getString("ProcessName").trim();

			if( rs.getString("ProcessDesc") == null )
				this.ProcessDesc = null;
			else
				this.ProcessDesc = rs.getString("ProcessDesc").trim();

			this.WatingTime = rs.getInt("WatingTime");
			this.WorkingTime = rs.getInt("WorkingTime");
			if( rs.getString("BusiType") == null )
				this.BusiType = null;
			else
				this.BusiType = rs.getString("BusiType").trim();

			if( rs.getString("ValuedFlag") == null )
				this.ValuedFlag = null;
			else
				this.ValuedFlag = rs.getString("ValuedFlag").trim();

			if( rs.getString("SysFlag") == null )
				this.SysFlag = null;
			else
				this.SysFlag = rs.getString("SysFlag").trim();

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

			this.WFXML = rs.getBinaryStream("WFXML");
			if( rs.getString("Version") == null )
				this.Version = null;
			else
				this.Version = rs.getString("Version").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LWProcessXML表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWProcessXMLSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LWProcessXMLSchema getSchema()
	{
		LWProcessXMLSchema aLWProcessXMLSchema = new LWProcessXMLSchema();
		aLWProcessXMLSchema.setSchema(this);
		return aLWProcessXMLSchema;
	}

	public LWProcessXMLDB getDB()
	{
		LWProcessXMLDB aDBOper = new LWProcessXMLDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWProcessXML描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ProcessID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TimeID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProcessName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProcessDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(WatingTime));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(WorkingTime));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusiType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValuedFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SysFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( 1 );strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Version));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWProcessXML>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ProcessID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TimeID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ProcessName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ProcessDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			WatingTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			WorkingTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			BusiType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ValuedFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SysFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			
			Version = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWProcessXMLSchema";
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
		if (FCode.equalsIgnoreCase("ProcessID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessID));
		}
		if (FCode.equalsIgnoreCase("TimeID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TimeID));
		}
		if (FCode.equalsIgnoreCase("ProcessName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessName));
		}
		if (FCode.equalsIgnoreCase("ProcessDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessDesc));
		}
		if (FCode.equalsIgnoreCase("WatingTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WatingTime));
		}
		if (FCode.equalsIgnoreCase("WorkingTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkingTime));
		}
		if (FCode.equalsIgnoreCase("BusiType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusiType));
		}
		if (FCode.equalsIgnoreCase("ValuedFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValuedFlag));
		}
		if (FCode.equalsIgnoreCase("SysFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SysFlag));
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
		if (FCode.equalsIgnoreCase("WFXML"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WFXML));
		}
		if (FCode.equalsIgnoreCase("Version"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Version));
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
				strFieldValue = StrTool.GBKToUnicode(ProcessID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TimeID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ProcessName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ProcessDesc);
				break;
			case 4:
				strFieldValue = String.valueOf(WatingTime);
				break;
			case 5:
				strFieldValue = String.valueOf(WorkingTime);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BusiType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ValuedFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(SysFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
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
				strFieldValue = String.valueOf(WFXML);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Version);
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

		if (FCode.equalsIgnoreCase("ProcessID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProcessID = FValue.trim();
			}
			else
				ProcessID = null;
		}
		if (FCode.equalsIgnoreCase("TimeID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TimeID = FValue.trim();
			}
			else
				TimeID = null;
		}
		if (FCode.equalsIgnoreCase("ProcessName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProcessName = FValue.trim();
			}
			else
				ProcessName = null;
		}
		if (FCode.equalsIgnoreCase("ProcessDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProcessDesc = FValue.trim();
			}
			else
				ProcessDesc = null;
		}
		if (FCode.equalsIgnoreCase("WatingTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				WatingTime = i;
			}
		}
		if (FCode.equalsIgnoreCase("WorkingTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				WorkingTime = i;
			}
		}
		if (FCode.equalsIgnoreCase("BusiType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusiType = FValue.trim();
			}
			else
				BusiType = null;
		}
		if (FCode.equalsIgnoreCase("ValuedFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValuedFlag = FValue.trim();
			}
			else
				ValuedFlag = null;
		}
		if (FCode.equalsIgnoreCase("SysFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SysFlag = FValue.trim();
			}
			else
				SysFlag = null;
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
		if (FCode.equalsIgnoreCase("WFXML"))
		{
		}
		if (FCode.equalsIgnoreCase("Version"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Version = FValue.trim();
			}
			else
				Version = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LWProcessXMLSchema other = (LWProcessXMLSchema)otherObject;
		return
			ProcessID.equals(other.getProcessID())
			&& TimeID.equals(other.getTimeID())
			&& ProcessName.equals(other.getProcessName())
			&& ProcessDesc.equals(other.getProcessDesc())
			&& WatingTime == other.getWatingTime()
			&& WorkingTime == other.getWorkingTime()
			&& BusiType.equals(other.getBusiType())
			&& ValuedFlag.equals(other.getValuedFlag())
			&& SysFlag.equals(other.getSysFlag())
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			
			&& Version.equals(other.getVersion());
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
		if( strFieldName.equals("ProcessID") ) {
			return 0;
		}
		if( strFieldName.equals("TimeID") ) {
			return 1;
		}
		if( strFieldName.equals("ProcessName") ) {
			return 2;
		}
		if( strFieldName.equals("ProcessDesc") ) {
			return 3;
		}
		if( strFieldName.equals("WatingTime") ) {
			return 4;
		}
		if( strFieldName.equals("WorkingTime") ) {
			return 5;
		}
		if( strFieldName.equals("BusiType") ) {
			return 6;
		}
		if( strFieldName.equals("ValuedFlag") ) {
			return 7;
		}
		if( strFieldName.equals("SysFlag") ) {
			return 8;
		}
		if( strFieldName.equals("ManageCom") ) {
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
		if( strFieldName.equals("WFXML") ) {
			return 15;
		}
		if( strFieldName.equals("Version") ) {
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
				strFieldName = "ProcessID";
				break;
			case 1:
				strFieldName = "TimeID";
				break;
			case 2:
				strFieldName = "ProcessName";
				break;
			case 3:
				strFieldName = "ProcessDesc";
				break;
			case 4:
				strFieldName = "WatingTime";
				break;
			case 5:
				strFieldName = "WorkingTime";
				break;
			case 6:
				strFieldName = "BusiType";
				break;
			case 7:
				strFieldName = "ValuedFlag";
				break;
			case 8:
				strFieldName = "SysFlag";
				break;
			case 9:
				strFieldName = "ManageCom";
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
				strFieldName = "WFXML";
				break;
			case 16:
				strFieldName = "Version";
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
		if( strFieldName.equals("ProcessID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TimeID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProcessName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProcessDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WatingTime") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("WorkingTime") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BusiType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValuedFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SysFlag") ) {
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
		if( strFieldName.equals("WFXML") ) {
			return Schema.TYPE_BLOB;
		}
		if( strFieldName.equals("Version") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 5:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_BLOB;
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
