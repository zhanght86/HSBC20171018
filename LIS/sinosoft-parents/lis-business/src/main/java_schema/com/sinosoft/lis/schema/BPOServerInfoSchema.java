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
import com.sinosoft.lis.db.BPOServerInfoDB;

/*
 * <p>ClassName: BPOServerInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 录入外包（lis6.0）
 */
public class BPOServerInfoSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(BPOServerInfoSchema.class);

	// @Field
	/** 外包商标识 */
	private String BPOID;
	/** 外包商名称 */
	private String BPOName;
	/** 外包状态 */
	private String InputState;
	/** 对应核心业务系统操作员 */
	private String LisOperator;
	/** 扫描件输出路径 */
	private String ScanPicPath;
	/** 返回数据文件路径 */
	private String BackDataPath;
	/** 数据导入后数据备份路径 */
	private String BackDataBackupPath;
	/** 开始合作日期 */
	private Date CoInputStartDate;
	/** 开始合作时间 */
	private String CoInputStartTime;
	/** 终止合作日期 */
	private Date CoInputEndDate;
	/** 终止合作时间 */
	private String CoInputEndTime;
	/** 备注 */
	private String Remark;
	/** 前置服务器地址 */
	private String ServerIP;
	/** 前置服务器端口 */
	private String ServerPort;
	/** 登录用户名 */
	private String LogInUser;
	/** 登录密码 */
	private String LogInPwd;
	/** 图片后缀 */
	private String Suffix;
	/** 业务类型 */
	private String BussNoType;

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public BPOServerInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "BPOID";

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
		BPOServerInfoSchema cloned = (BPOServerInfoSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBPOID()
	{
		return BPOID;
	}
	public void setBPOID(String aBPOID)
	{
		BPOID = aBPOID;
	}
	public String getBPOName()
	{
		return BPOName;
	}
	public void setBPOName(String aBPOName)
	{
		BPOName = aBPOName;
	}
	/**
	* 0 --  与保险公司有外包合作关系<p>
	* 1 --  与保险公司无外包合作关系
	*/
	public String getInputState()
	{
		return InputState;
	}
	public void setInputState(String aInputState)
	{
		InputState = aInputState;
	}
	public String getLisOperator()
	{
		return LisOperator;
	}
	public void setLisOperator(String aLisOperator)
	{
		LisOperator = aLisOperator;
	}
	public String getScanPicPath()
	{
		return ScanPicPath;
	}
	public void setScanPicPath(String aScanPicPath)
	{
		ScanPicPath = aScanPicPath;
	}
	public String getBackDataPath()
	{
		return BackDataPath;
	}
	public void setBackDataPath(String aBackDataPath)
	{
		BackDataPath = aBackDataPath;
	}
	/**
	* 数据导入核心业务系统后，外包数据的备份路径
	*/
	public String getBackDataBackupPath()
	{
		return BackDataBackupPath;
	}
	public void setBackDataBackupPath(String aBackDataBackupPath)
	{
		BackDataBackupPath = aBackDataBackupPath;
	}
	public String getCoInputStartDate()
	{
		if( CoInputStartDate != null )
			return fDate.getString(CoInputStartDate);
		else
			return null;
	}
	public void setCoInputStartDate(Date aCoInputStartDate)
	{
		CoInputStartDate = aCoInputStartDate;
	}
	public void setCoInputStartDate(String aCoInputStartDate)
	{
		if (aCoInputStartDate != null && !aCoInputStartDate.equals("") )
		{
			CoInputStartDate = fDate.getDate( aCoInputStartDate );
		}
		else
			CoInputStartDate = null;
	}

	public String getCoInputStartTime()
	{
		return CoInputStartTime;
	}
	public void setCoInputStartTime(String aCoInputStartTime)
	{
		CoInputStartTime = aCoInputStartTime;
	}
	public String getCoInputEndDate()
	{
		if( CoInputEndDate != null )
			return fDate.getString(CoInputEndDate);
		else
			return null;
	}
	public void setCoInputEndDate(Date aCoInputEndDate)
	{
		CoInputEndDate = aCoInputEndDate;
	}
	public void setCoInputEndDate(String aCoInputEndDate)
	{
		if (aCoInputEndDate != null && !aCoInputEndDate.equals("") )
		{
			CoInputEndDate = fDate.getDate( aCoInputEndDate );
		}
		else
			CoInputEndDate = null;
	}

	public String getCoInputEndTime()
	{
		return CoInputEndTime;
	}
	public void setCoInputEndTime(String aCoInputEndTime)
	{
		CoInputEndTime = aCoInputEndTime;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getServerIP()
	{
		return ServerIP;
	}
	public void setServerIP(String aServerIP)
	{
		ServerIP = aServerIP;
	}
	public String getServerPort()
	{
		return ServerPort;
	}
	public void setServerPort(String aServerPort)
	{
		ServerPort = aServerPort;
	}
	public String getLogInUser()
	{
		return LogInUser;
	}
	public void setLogInUser(String aLogInUser)
	{
		LogInUser = aLogInUser;
	}
	public String getLogInPwd()
	{
		return LogInPwd;
	}
	public void setLogInPwd(String aLogInPwd)
	{
		LogInPwd = aLogInPwd;
	}
	/**
	* 1 下载tif<p>
	* 2 下载gif<p>
	* 3 下载tif和gif
	*/
	public String getSuffix()
	{
		return Suffix;
	}
	public void setSuffix(String aSuffix)
	{
		Suffix = aSuffix;
	}
	public String getBussNoType()
	{
		return BussNoType;
	}
	public void setBussNoType(String aBussNoType)
	{
		BussNoType = aBussNoType;
	}

	/**
	* 使用另外一个 BPOServerInfoSchema 对象给 Schema 赋值
	* @param: aBPOServerInfoSchema BPOServerInfoSchema
	**/
	public void setSchema(BPOServerInfoSchema aBPOServerInfoSchema)
	{
		this.BPOID = aBPOServerInfoSchema.getBPOID();
		this.BPOName = aBPOServerInfoSchema.getBPOName();
		this.InputState = aBPOServerInfoSchema.getInputState();
		this.LisOperator = aBPOServerInfoSchema.getLisOperator();
		this.ScanPicPath = aBPOServerInfoSchema.getScanPicPath();
		this.BackDataPath = aBPOServerInfoSchema.getBackDataPath();
		this.BackDataBackupPath = aBPOServerInfoSchema.getBackDataBackupPath();
		this.CoInputStartDate = fDate.getDate( aBPOServerInfoSchema.getCoInputStartDate());
		this.CoInputStartTime = aBPOServerInfoSchema.getCoInputStartTime();
		this.CoInputEndDate = fDate.getDate( aBPOServerInfoSchema.getCoInputEndDate());
		this.CoInputEndTime = aBPOServerInfoSchema.getCoInputEndTime();
		this.Remark = aBPOServerInfoSchema.getRemark();
		this.ServerIP = aBPOServerInfoSchema.getServerIP();
		this.ServerPort = aBPOServerInfoSchema.getServerPort();
		this.LogInUser = aBPOServerInfoSchema.getLogInUser();
		this.LogInPwd = aBPOServerInfoSchema.getLogInPwd();
		this.Suffix = aBPOServerInfoSchema.getSuffix();
		this.BussNoType = aBPOServerInfoSchema.getBussNoType();
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
			if( rs.getString("BPOID") == null )
				this.BPOID = null;
			else
				this.BPOID = rs.getString("BPOID").trim();

			if( rs.getString("BPOName") == null )
				this.BPOName = null;
			else
				this.BPOName = rs.getString("BPOName").trim();

			if( rs.getString("InputState") == null )
				this.InputState = null;
			else
				this.InputState = rs.getString("InputState").trim();

			if( rs.getString("LisOperator") == null )
				this.LisOperator = null;
			else
				this.LisOperator = rs.getString("LisOperator").trim();

			if( rs.getString("ScanPicPath") == null )
				this.ScanPicPath = null;
			else
				this.ScanPicPath = rs.getString("ScanPicPath").trim();

			if( rs.getString("BackDataPath") == null )
				this.BackDataPath = null;
			else
				this.BackDataPath = rs.getString("BackDataPath").trim();

			if( rs.getString("BackDataBackupPath") == null )
				this.BackDataBackupPath = null;
			else
				this.BackDataBackupPath = rs.getString("BackDataBackupPath").trim();

			this.CoInputStartDate = rs.getDate("CoInputStartDate");
			if( rs.getString("CoInputStartTime") == null )
				this.CoInputStartTime = null;
			else
				this.CoInputStartTime = rs.getString("CoInputStartTime").trim();

			this.CoInputEndDate = rs.getDate("CoInputEndDate");
			if( rs.getString("CoInputEndTime") == null )
				this.CoInputEndTime = null;
			else
				this.CoInputEndTime = rs.getString("CoInputEndTime").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("ServerIP") == null )
				this.ServerIP = null;
			else
				this.ServerIP = rs.getString("ServerIP").trim();

			if( rs.getString("ServerPort") == null )
				this.ServerPort = null;
			else
				this.ServerPort = rs.getString("ServerPort").trim();

			if( rs.getString("LogInUser") == null )
				this.LogInUser = null;
			else
				this.LogInUser = rs.getString("LogInUser").trim();

			if( rs.getString("LogInPwd") == null )
				this.LogInPwd = null;
			else
				this.LogInPwd = rs.getString("LogInPwd").trim();

			if( rs.getString("Suffix") == null )
				this.Suffix = null;
			else
				this.Suffix = rs.getString("Suffix").trim();

			if( rs.getString("BussNoType") == null )
				this.BussNoType = null;
			else
				this.BussNoType = rs.getString("BussNoType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的BPOServerInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BPOServerInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public BPOServerInfoSchema getSchema()
	{
		BPOServerInfoSchema aBPOServerInfoSchema = new BPOServerInfoSchema();
		aBPOServerInfoSchema.setSchema(this);
		return aBPOServerInfoSchema;
	}

	public BPOServerInfoDB getDB()
	{
		BPOServerInfoDB aDBOper = new BPOServerInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpBPOServerInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BPOID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BPOName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LisOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ScanPicPath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackDataPath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackDataBackupPath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CoInputStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CoInputStartTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CoInputEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CoInputEndTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerIP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerPort)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LogInUser)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LogInPwd)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Suffix)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNoType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpBPOServerInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BPOID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BPOName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InputState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			LisOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ScanPicPath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BackDataPath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BackDataBackupPath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CoInputStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			CoInputStartTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CoInputEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			CoInputEndTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ServerIP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ServerPort = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			LogInUser = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			LogInPwd = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Suffix = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			BussNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BPOServerInfoSchema";
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
		if (FCode.equalsIgnoreCase("BPOID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BPOID));
		}
		if (FCode.equalsIgnoreCase("BPOName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BPOName));
		}
		if (FCode.equalsIgnoreCase("InputState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputState));
		}
		if (FCode.equalsIgnoreCase("LisOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LisOperator));
		}
		if (FCode.equalsIgnoreCase("ScanPicPath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ScanPicPath));
		}
		if (FCode.equalsIgnoreCase("BackDataPath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackDataPath));
		}
		if (FCode.equalsIgnoreCase("BackDataBackupPath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackDataBackupPath));
		}
		if (FCode.equalsIgnoreCase("CoInputStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCoInputStartDate()));
		}
		if (FCode.equalsIgnoreCase("CoInputStartTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CoInputStartTime));
		}
		if (FCode.equalsIgnoreCase("CoInputEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCoInputEndDate()));
		}
		if (FCode.equalsIgnoreCase("CoInputEndTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CoInputEndTime));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("ServerIP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerIP));
		}
		if (FCode.equalsIgnoreCase("ServerPort"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerPort));
		}
		if (FCode.equalsIgnoreCase("LogInUser"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogInUser));
		}
		if (FCode.equalsIgnoreCase("LogInPwd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogInPwd));
		}
		if (FCode.equalsIgnoreCase("Suffix"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Suffix));
		}
		if (FCode.equalsIgnoreCase("BussNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNoType));
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
				strFieldValue = StrTool.GBKToUnicode(BPOID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BPOName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(InputState);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(LisOperator);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ScanPicPath);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BackDataPath);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BackDataBackupPath);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCoInputStartDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CoInputStartTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCoInputEndDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CoInputEndTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ServerIP);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ServerPort);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(LogInUser);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(LogInPwd);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Suffix);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(BussNoType);
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

		if (FCode.equalsIgnoreCase("BPOID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BPOID = FValue.trim();
			}
			else
				BPOID = null;
		}
		if (FCode.equalsIgnoreCase("BPOName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BPOName = FValue.trim();
			}
			else
				BPOName = null;
		}
		if (FCode.equalsIgnoreCase("InputState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputState = FValue.trim();
			}
			else
				InputState = null;
		}
		if (FCode.equalsIgnoreCase("LisOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LisOperator = FValue.trim();
			}
			else
				LisOperator = null;
		}
		if (FCode.equalsIgnoreCase("ScanPicPath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ScanPicPath = FValue.trim();
			}
			else
				ScanPicPath = null;
		}
		if (FCode.equalsIgnoreCase("BackDataPath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackDataPath = FValue.trim();
			}
			else
				BackDataPath = null;
		}
		if (FCode.equalsIgnoreCase("BackDataBackupPath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackDataBackupPath = FValue.trim();
			}
			else
				BackDataBackupPath = null;
		}
		if (FCode.equalsIgnoreCase("CoInputStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CoInputStartDate = fDate.getDate( FValue );
			}
			else
				CoInputStartDate = null;
		}
		if (FCode.equalsIgnoreCase("CoInputStartTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CoInputStartTime = FValue.trim();
			}
			else
				CoInputStartTime = null;
		}
		if (FCode.equalsIgnoreCase("CoInputEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CoInputEndDate = fDate.getDate( FValue );
			}
			else
				CoInputEndDate = null;
		}
		if (FCode.equalsIgnoreCase("CoInputEndTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CoInputEndTime = FValue.trim();
			}
			else
				CoInputEndTime = null;
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
		if (FCode.equalsIgnoreCase("ServerIP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServerIP = FValue.trim();
			}
			else
				ServerIP = null;
		}
		if (FCode.equalsIgnoreCase("ServerPort"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServerPort = FValue.trim();
			}
			else
				ServerPort = null;
		}
		if (FCode.equalsIgnoreCase("LogInUser"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogInUser = FValue.trim();
			}
			else
				LogInUser = null;
		}
		if (FCode.equalsIgnoreCase("LogInPwd"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogInPwd = FValue.trim();
			}
			else
				LogInPwd = null;
		}
		if (FCode.equalsIgnoreCase("Suffix"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Suffix = FValue.trim();
			}
			else
				Suffix = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		BPOServerInfoSchema other = (BPOServerInfoSchema)otherObject;
		return
			BPOID.equals(other.getBPOID())
			&& BPOName.equals(other.getBPOName())
			&& InputState.equals(other.getInputState())
			&& LisOperator.equals(other.getLisOperator())
			&& ScanPicPath.equals(other.getScanPicPath())
			&& BackDataPath.equals(other.getBackDataPath())
			&& BackDataBackupPath.equals(other.getBackDataBackupPath())
			&& fDate.getString(CoInputStartDate).equals(other.getCoInputStartDate())
			&& CoInputStartTime.equals(other.getCoInputStartTime())
			&& fDate.getString(CoInputEndDate).equals(other.getCoInputEndDate())
			&& CoInputEndTime.equals(other.getCoInputEndTime())
			&& Remark.equals(other.getRemark())
			&& ServerIP.equals(other.getServerIP())
			&& ServerPort.equals(other.getServerPort())
			&& LogInUser.equals(other.getLogInUser())
			&& LogInPwd.equals(other.getLogInPwd())
			&& Suffix.equals(other.getSuffix())
			&& BussNoType.equals(other.getBussNoType());
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
		if( strFieldName.equals("BPOID") ) {
			return 0;
		}
		if( strFieldName.equals("BPOName") ) {
			return 1;
		}
		if( strFieldName.equals("InputState") ) {
			return 2;
		}
		if( strFieldName.equals("LisOperator") ) {
			return 3;
		}
		if( strFieldName.equals("ScanPicPath") ) {
			return 4;
		}
		if( strFieldName.equals("BackDataPath") ) {
			return 5;
		}
		if( strFieldName.equals("BackDataBackupPath") ) {
			return 6;
		}
		if( strFieldName.equals("CoInputStartDate") ) {
			return 7;
		}
		if( strFieldName.equals("CoInputStartTime") ) {
			return 8;
		}
		if( strFieldName.equals("CoInputEndDate") ) {
			return 9;
		}
		if( strFieldName.equals("CoInputEndTime") ) {
			return 10;
		}
		if( strFieldName.equals("Remark") ) {
			return 11;
		}
		if( strFieldName.equals("ServerIP") ) {
			return 12;
		}
		if( strFieldName.equals("ServerPort") ) {
			return 13;
		}
		if( strFieldName.equals("LogInUser") ) {
			return 14;
		}
		if( strFieldName.equals("LogInPwd") ) {
			return 15;
		}
		if( strFieldName.equals("Suffix") ) {
			return 16;
		}
		if( strFieldName.equals("BussNoType") ) {
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
				strFieldName = "BPOID";
				break;
			case 1:
				strFieldName = "BPOName";
				break;
			case 2:
				strFieldName = "InputState";
				break;
			case 3:
				strFieldName = "LisOperator";
				break;
			case 4:
				strFieldName = "ScanPicPath";
				break;
			case 5:
				strFieldName = "BackDataPath";
				break;
			case 6:
				strFieldName = "BackDataBackupPath";
				break;
			case 7:
				strFieldName = "CoInputStartDate";
				break;
			case 8:
				strFieldName = "CoInputStartTime";
				break;
			case 9:
				strFieldName = "CoInputEndDate";
				break;
			case 10:
				strFieldName = "CoInputEndTime";
				break;
			case 11:
				strFieldName = "Remark";
				break;
			case 12:
				strFieldName = "ServerIP";
				break;
			case 13:
				strFieldName = "ServerPort";
				break;
			case 14:
				strFieldName = "LogInUser";
				break;
			case 15:
				strFieldName = "LogInPwd";
				break;
			case 16:
				strFieldName = "Suffix";
				break;
			case 17:
				strFieldName = "BussNoType";
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
		if( strFieldName.equals("BPOID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BPOName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LisOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ScanPicPath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackDataPath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackDataBackupPath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CoInputStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CoInputStartTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CoInputEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CoInputEndTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerIP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerPort") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogInUser") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogInPwd") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Suffix") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNoType") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
