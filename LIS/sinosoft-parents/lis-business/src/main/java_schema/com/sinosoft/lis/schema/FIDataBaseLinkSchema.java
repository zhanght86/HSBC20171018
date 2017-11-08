/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.FIDataBaseLinkDB;

/**
 * <p>ClassName: FIDataBaseLinkSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIDataBaseLinkSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIDataBaseLinkSchema.class);

	// @Field
	/** 接口编码 */
	private String InterfaceCode;
	/** 接口名称 */
	private String InterfaceName;
	/** 数据库类型 */
	private String DBType;
	/** Ip */
	private String IP;
	/** 端口号 */
	private String Port;
	/** 数据库名称 */
	private String DBName;
	/** 服务名称 */
	private String ServerName;
	/** 用户名 */
	private String UserName;
	/** 密码 */
	private String PassWord;
	/** 管理机构 */
	private String ManageCom;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;

	public static final int FIELDNUM = 15;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIDataBaseLinkSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "InterfaceCode";

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
                FIDataBaseLinkSchema cloned = (FIDataBaseLinkSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getInterfaceCode()
	{
		return InterfaceCode;
	}
	public void setInterfaceCode(String aInterfaceCode)
	{
		InterfaceCode = aInterfaceCode;
	}
	public String getInterfaceName()
	{
		return InterfaceName;
	}
	public void setInterfaceName(String aInterfaceName)
	{
		InterfaceName = aInterfaceName;
	}
	public String getDBType()
	{
		return DBType;
	}
	public void setDBType(String aDBType)
	{
		DBType = aDBType;
	}
	public String getIP()
	{
		return IP;
	}
	public void setIP(String aIP)
	{
		IP = aIP;
	}
	public String getPort()
	{
		return Port;
	}
	public void setPort(String aPort)
	{
		Port = aPort;
	}
	public String getDBName()
	{
		return DBName;
	}
	public void setDBName(String aDBName)
	{
		DBName = aDBName;
	}
	public String getServerName()
	{
		return ServerName;
	}
	public void setServerName(String aServerName)
	{
		ServerName = aServerName;
	}
	public String getUserName()
	{
		return UserName;
	}
	public void setUserName(String aUserName)
	{
		UserName = aUserName;
	}
	public String getPassWord()
	{
		return PassWord;
	}
	public void setPassWord(String aPassWord)
	{
		PassWord = aPassWord;
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
	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
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


	/**
	* 使用另外一个 FIDataBaseLinkSchema 对象给 Schema 赋值
	* @param: aFIDataBaseLinkSchema FIDataBaseLinkSchema
	**/
	public void setSchema(FIDataBaseLinkSchema aFIDataBaseLinkSchema)
	{
		this.InterfaceCode = aFIDataBaseLinkSchema.getInterfaceCode();
		this.InterfaceName = aFIDataBaseLinkSchema.getInterfaceName();
		this.DBType = aFIDataBaseLinkSchema.getDBType();
		this.IP = aFIDataBaseLinkSchema.getIP();
		this.Port = aFIDataBaseLinkSchema.getPort();
		this.DBName = aFIDataBaseLinkSchema.getDBName();
		this.ServerName = aFIDataBaseLinkSchema.getServerName();
		this.UserName = aFIDataBaseLinkSchema.getUserName();
		this.PassWord = aFIDataBaseLinkSchema.getPassWord();
		this.ManageCom = aFIDataBaseLinkSchema.getManageCom();
		this.Operator = aFIDataBaseLinkSchema.getOperator();
		this.MakeDate = fDate.getDate( aFIDataBaseLinkSchema.getMakeDate());
		this.MakeTime = aFIDataBaseLinkSchema.getMakeTime();
		this.ModifyTime = aFIDataBaseLinkSchema.getModifyTime();
		this.ModifyDate = fDate.getDate( aFIDataBaseLinkSchema.getModifyDate());
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
			if( rs.getString("InterfaceCode") == null )
				this.InterfaceCode = null;
			else
				this.InterfaceCode = rs.getString("InterfaceCode").trim();

			if( rs.getString("InterfaceName") == null )
				this.InterfaceName = null;
			else
				this.InterfaceName = rs.getString("InterfaceName").trim();

			if( rs.getString("DBType") == null )
				this.DBType = null;
			else
				this.DBType = rs.getString("DBType").trim();

			if( rs.getString("IP") == null )
				this.IP = null;
			else
				this.IP = rs.getString("IP").trim();

			if( rs.getString("Port") == null )
				this.Port = null;
			else
				this.Port = rs.getString("Port").trim();

			if( rs.getString("DBName") == null )
				this.DBName = null;
			else
				this.DBName = rs.getString("DBName").trim();

			if( rs.getString("ServerName") == null )
				this.ServerName = null;
			else
				this.ServerName = rs.getString("ServerName").trim();

			if( rs.getString("UserName") == null )
				this.UserName = null;
			else
				this.UserName = rs.getString("UserName").trim();

			if( rs.getString("PassWord") == null )
				this.PassWord = null;
			else
				this.PassWord = rs.getString("PassWord").trim();

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

			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIDataBaseLink表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataBaseLinkSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIDataBaseLinkSchema getSchema()
	{
		FIDataBaseLinkSchema aFIDataBaseLinkSchema = new FIDataBaseLinkSchema();
		aFIDataBaseLinkSchema.setSchema(this);
		return aFIDataBaseLinkSchema;
	}

	public FIDataBaseLinkDB getDB()
	{
		FIDataBaseLinkDB aDBOper = new FIDataBaseLinkDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataBaseLink描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(InterfaceCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(InterfaceName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DBType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(IP)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Port)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DBName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ServerName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(UserName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(PassWord)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataBaseLink>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			InterfaceCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InterfaceName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DBType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			IP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Port = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DBName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ServerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			UserName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PassWord = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataBaseLinkSchema";
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
		if (FCode.equalsIgnoreCase("InterfaceCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterfaceCode));
		}
		if (FCode.equalsIgnoreCase("InterfaceName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterfaceName));
		}
		if (FCode.equalsIgnoreCase("DBType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DBType));
		}
		if (FCode.equalsIgnoreCase("IP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IP));
		}
		if (FCode.equalsIgnoreCase("Port"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Port));
		}
		if (FCode.equalsIgnoreCase("DBName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DBName));
		}
		if (FCode.equalsIgnoreCase("ServerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerName));
		}
		if (FCode.equalsIgnoreCase("UserName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserName));
		}
		if (FCode.equalsIgnoreCase("PassWord"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PassWord));
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
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
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
				strFieldValue = StrTool.GBKToUnicode(InterfaceCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(InterfaceName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DBType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(IP);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Port);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DBName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ServerName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(UserName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PassWord);
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
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
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

		if (FCode.equalsIgnoreCase("InterfaceCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterfaceCode = FValue.trim();
			}
			else
				InterfaceCode = null;
		}
		if (FCode.equalsIgnoreCase("InterfaceName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterfaceName = FValue.trim();
			}
			else
				InterfaceName = null;
		}
		if (FCode.equalsIgnoreCase("DBType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DBType = FValue.trim();
			}
			else
				DBType = null;
		}
		if (FCode.equalsIgnoreCase("IP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IP = FValue.trim();
			}
			else
				IP = null;
		}
		if (FCode.equalsIgnoreCase("Port"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Port = FValue.trim();
			}
			else
				Port = null;
		}
		if (FCode.equalsIgnoreCase("DBName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DBName = FValue.trim();
			}
			else
				DBName = null;
		}
		if (FCode.equalsIgnoreCase("ServerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServerName = FValue.trim();
			}
			else
				ServerName = null;
		}
		if (FCode.equalsIgnoreCase("UserName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserName = FValue.trim();
			}
			else
				UserName = null;
		}
		if (FCode.equalsIgnoreCase("PassWord"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PassWord = FValue.trim();
			}
			else
				PassWord = null;
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
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIDataBaseLinkSchema other = (FIDataBaseLinkSchema)otherObject;
		return
			InterfaceCode.equals(other.getInterfaceCode())
			&& InterfaceName.equals(other.getInterfaceName())
			&& DBType.equals(other.getDBType())
			&& IP.equals(other.getIP())
			&& Port.equals(other.getPort())
			&& DBName.equals(other.getDBName())
			&& ServerName.equals(other.getServerName())
			&& UserName.equals(other.getUserName())
			&& PassWord.equals(other.getPassWord())
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate());
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
		if( strFieldName.equals("InterfaceCode") ) {
			return 0;
		}
		if( strFieldName.equals("InterfaceName") ) {
			return 1;
		}
		if( strFieldName.equals("DBType") ) {
			return 2;
		}
		if( strFieldName.equals("IP") ) {
			return 3;
		}
		if( strFieldName.equals("Port") ) {
			return 4;
		}
		if( strFieldName.equals("DBName") ) {
			return 5;
		}
		if( strFieldName.equals("ServerName") ) {
			return 6;
		}
		if( strFieldName.equals("UserName") ) {
			return 7;
		}
		if( strFieldName.equals("PassWord") ) {
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
		if( strFieldName.equals("ModifyTime") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 14;
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
				strFieldName = "InterfaceCode";
				break;
			case 1:
				strFieldName = "InterfaceName";
				break;
			case 2:
				strFieldName = "DBType";
				break;
			case 3:
				strFieldName = "IP";
				break;
			case 4:
				strFieldName = "Port";
				break;
			case 5:
				strFieldName = "DBName";
				break;
			case 6:
				strFieldName = "ServerName";
				break;
			case 7:
				strFieldName = "UserName";
				break;
			case 8:
				strFieldName = "PassWord";
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
				strFieldName = "ModifyTime";
				break;
			case 14:
				strFieldName = "ModifyDate";
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
		if( strFieldName.equals("InterfaceCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InterfaceName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DBType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Port") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DBName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UserName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PassWord") ) {
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
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
