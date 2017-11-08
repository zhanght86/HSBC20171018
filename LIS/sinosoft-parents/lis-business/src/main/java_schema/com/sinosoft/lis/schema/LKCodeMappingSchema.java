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
import com.sinosoft.lis.db.LKCodeMappingDB;

/*
 * <p>ClassName: LKCodeMappingSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 银保通
 */
public class LKCodeMappingSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LKCodeMappingSchema.class);

	// @Field
	/** 银行编码 */
	private String BankCode;
	/** 地区代码 */
	private String ZoneNo;
	/** 银行网点 */
	private String BankNode;
	/** 代理机构 */
	private String AgentCom;
	/** 登陆机构 */
	private String ComCode;
	/** 管理机构 */
	private String ManageCom;
	/** 专管员 */
	private String Operator;
	/** 备注 */
	private String Remark;
	/** 备用1 */
	private String Remark1;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** Ftp地址 */
	private String FTPAddress;
	/** Ftp目录 */
	private String FTPDir;
	/** Ftp用户 */
	private String FTPUser;
	/** Ftp密码 */
	private String FTPPassWord;
	/** 备用2 */
	private String Remark2;
	/** 结算地区码 */
	private String Remark3;
	/** 服务器文件目录 */
	private String ServerDir;
	/** 服务器ip地址 */
	private String ServerAddress;
	/** 服务器端口 */
	private int ServerPort;
	/** 服务器用户 */
	private String ServerUser;
	/** 服务器密码 */
	private String ServerPassWord;
	/** 备用4 */
	private String Remark4;
	/** 备用5 */
	private String Remark5;

	public static final int FIELDNUM = 26;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LKCodeMappingSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "BankCode";
		pk[1] = "ZoneNo";
		pk[2] = "BankNode";

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
		LKCodeMappingSchema cloned = (LKCodeMappingSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	public String getZoneNo()
	{
		return ZoneNo;
	}
	public void setZoneNo(String aZoneNo)
	{
		ZoneNo = aZoneNo;
	}
	public String getBankNode()
	{
		return BankNode;
	}
	public void setBankNode(String aBankNode)
	{
		BankNode = aBankNode;
	}
	public String getAgentCom()
	{
		return AgentCom;
	}
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		ComCode = aComCode;
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
	/**
	* 业务网点：此值可为空<p>
	* 对账网点：对帐明细文件本地备份路径（一般备份在后置机）
	*/
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getRemark1()
	{
		return Remark1;
	}
	public void setRemark1(String aRemark1)
	{
		Remark1 = aRemark1;
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
	* 对账网点：对账文件所在的FTP地址
	*/
	public String getFTPAddress()
	{
		return FTPAddress;
	}
	public void setFTPAddress(String aFTPAddress)
	{
		FTPAddress = aFTPAddress;
	}
	/**
	* 对账网点：对账文件所在目录
	*/
	public String getFTPDir()
	{
		return FTPDir;
	}
	public void setFTPDir(String aFTPDir)
	{
		FTPDir = aFTPDir;
	}
	public String getFTPUser()
	{
		return FTPUser;
	}
	public void setFTPUser(String aFTPUser)
	{
		FTPUser = aFTPUser;
	}
	public String getFTPPassWord()
	{
		return FTPPassWord;
	}
	public void setFTPPassWord(String aFTPPassWord)
	{
		FTPPassWord = aFTPPassWord;
	}
	/**
	* 网点名称
	*/
	public String getRemark2()
	{
		return Remark2;
	}
	public void setRemark2(String aRemark2)
	{
		Remark2 = aRemark2;
	}
	public String getRemark3()
	{
		return Remark3;
	}
	public void setRemark3(String aRemark3)
	{
		Remark3 = aRemark3;
	}
	public String getServerDir()
	{
		return ServerDir;
	}
	public void setServerDir(String aServerDir)
	{
		ServerDir = aServerDir;
	}
	public String getServerAddress()
	{
		return ServerAddress;
	}
	public void setServerAddress(String aServerAddress)
	{
		ServerAddress = aServerAddress;
	}
	public int getServerPort()
	{
		return ServerPort;
	}
	public void setServerPort(int aServerPort)
	{
		ServerPort = aServerPort;
	}
	public void setServerPort(String aServerPort)
	{
		if (aServerPort != null && !aServerPort.equals(""))
		{
			Integer tInteger = new Integer(aServerPort);
			int i = tInteger.intValue();
			ServerPort = i;
		}
	}

	public String getServerUser()
	{
		return ServerUser;
	}
	public void setServerUser(String aServerUser)
	{
		ServerUser = aServerUser;
	}
	public String getServerPassWord()
	{
		return ServerPassWord;
	}
	public void setServerPassWord(String aServerPassWord)
	{
		ServerPassWord = aServerPassWord;
	}
	public String getRemark4()
	{
		return Remark4;
	}
	public void setRemark4(String aRemark4)
	{
		Remark4 = aRemark4;
	}
	/**
	* 0 -- 业务网点<p>
	* 1 -- 对账网点
	*/
	public String getRemark5()
	{
		return Remark5;
	}
	public void setRemark5(String aRemark5)
	{
		Remark5 = aRemark5;
	}

	/**
	* 使用另外一个 LKCodeMappingSchema 对象给 Schema 赋值
	* @param: aLKCodeMappingSchema LKCodeMappingSchema
	**/
	public void setSchema(LKCodeMappingSchema aLKCodeMappingSchema)
	{
		this.BankCode = aLKCodeMappingSchema.getBankCode();
		this.ZoneNo = aLKCodeMappingSchema.getZoneNo();
		this.BankNode = aLKCodeMappingSchema.getBankNode();
		this.AgentCom = aLKCodeMappingSchema.getAgentCom();
		this.ComCode = aLKCodeMappingSchema.getComCode();
		this.ManageCom = aLKCodeMappingSchema.getManageCom();
		this.Operator = aLKCodeMappingSchema.getOperator();
		this.Remark = aLKCodeMappingSchema.getRemark();
		this.Remark1 = aLKCodeMappingSchema.getRemark1();
		this.MakeDate = fDate.getDate( aLKCodeMappingSchema.getMakeDate());
		this.MakeTime = aLKCodeMappingSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLKCodeMappingSchema.getModifyDate());
		this.ModifyTime = aLKCodeMappingSchema.getModifyTime();
		this.FTPAddress = aLKCodeMappingSchema.getFTPAddress();
		this.FTPDir = aLKCodeMappingSchema.getFTPDir();
		this.FTPUser = aLKCodeMappingSchema.getFTPUser();
		this.FTPPassWord = aLKCodeMappingSchema.getFTPPassWord();
		this.Remark2 = aLKCodeMappingSchema.getRemark2();
		this.Remark3 = aLKCodeMappingSchema.getRemark3();
		this.ServerDir = aLKCodeMappingSchema.getServerDir();
		this.ServerAddress = aLKCodeMappingSchema.getServerAddress();
		this.ServerPort = aLKCodeMappingSchema.getServerPort();
		this.ServerUser = aLKCodeMappingSchema.getServerUser();
		this.ServerPassWord = aLKCodeMappingSchema.getServerPassWord();
		this.Remark4 = aLKCodeMappingSchema.getRemark4();
		this.Remark5 = aLKCodeMappingSchema.getRemark5();
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
			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("ZoneNo") == null )
				this.ZoneNo = null;
			else
				this.ZoneNo = rs.getString("ZoneNo").trim();

			if( rs.getString("BankNode") == null )
				this.BankNode = null;
			else
				this.BankNode = rs.getString("BankNode").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("Remark1") == null )
				this.Remark1 = null;
			else
				this.Remark1 = rs.getString("Remark1").trim();

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

			if( rs.getString("FTPAddress") == null )
				this.FTPAddress = null;
			else
				this.FTPAddress = rs.getString("FTPAddress").trim();

			if( rs.getString("FTPDir") == null )
				this.FTPDir = null;
			else
				this.FTPDir = rs.getString("FTPDir").trim();

			if( rs.getString("FTPUser") == null )
				this.FTPUser = null;
			else
				this.FTPUser = rs.getString("FTPUser").trim();

			if( rs.getString("FTPPassWord") == null )
				this.FTPPassWord = null;
			else
				this.FTPPassWord = rs.getString("FTPPassWord").trim();

			if( rs.getString("Remark2") == null )
				this.Remark2 = null;
			else
				this.Remark2 = rs.getString("Remark2").trim();

			if( rs.getString("Remark3") == null )
				this.Remark3 = null;
			else
				this.Remark3 = rs.getString("Remark3").trim();

			if( rs.getString("ServerDir") == null )
				this.ServerDir = null;
			else
				this.ServerDir = rs.getString("ServerDir").trim();

			if( rs.getString("ServerAddress") == null )
				this.ServerAddress = null;
			else
				this.ServerAddress = rs.getString("ServerAddress").trim();

			this.ServerPort = rs.getInt("ServerPort");
			if( rs.getString("ServerUser") == null )
				this.ServerUser = null;
			else
				this.ServerUser = rs.getString("ServerUser").trim();

			if( rs.getString("ServerPassWord") == null )
				this.ServerPassWord = null;
			else
				this.ServerPassWord = rs.getString("ServerPassWord").trim();

			if( rs.getString("Remark4") == null )
				this.Remark4 = null;
			else
				this.Remark4 = rs.getString("Remark4").trim();

			if( rs.getString("Remark5") == null )
				this.Remark5 = null;
			else
				this.Remark5 = rs.getString("Remark5").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LKCodeMapping表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKCodeMappingSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LKCodeMappingSchema getSchema()
	{
		LKCodeMappingSchema aLKCodeMappingSchema = new LKCodeMappingSchema();
		aLKCodeMappingSchema.setSchema(this);
		return aLKCodeMappingSchema;
	}

	public LKCodeMappingDB getDB()
	{
		LKCodeMappingDB aDBOper = new LKCodeMappingDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKCodeMapping描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZoneNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankNode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FTPAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FTPDir)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FTPUser)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FTPPassWord)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerDir)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ServerPort));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerUser)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerPassWord)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark5));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKCodeMapping>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ZoneNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BankNode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Remark1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			FTPAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			FTPDir = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			FTPUser = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			FTPPassWord = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Remark2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Remark3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ServerDir = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ServerAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ServerPort= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			ServerUser = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ServerPassWord = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Remark4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Remark5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKCodeMappingSchema";
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
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("ZoneNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZoneNo));
		}
		if (FCode.equalsIgnoreCase("BankNode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankNode));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("Remark1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark1));
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
		if (FCode.equalsIgnoreCase("FTPAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FTPAddress));
		}
		if (FCode.equalsIgnoreCase("FTPDir"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FTPDir));
		}
		if (FCode.equalsIgnoreCase("FTPUser"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FTPUser));
		}
		if (FCode.equalsIgnoreCase("FTPPassWord"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FTPPassWord));
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark2));
		}
		if (FCode.equalsIgnoreCase("Remark3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark3));
		}
		if (FCode.equalsIgnoreCase("ServerDir"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerDir));
		}
		if (FCode.equalsIgnoreCase("ServerAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerAddress));
		}
		if (FCode.equalsIgnoreCase("ServerPort"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerPort));
		}
		if (FCode.equalsIgnoreCase("ServerUser"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerUser));
		}
		if (FCode.equalsIgnoreCase("ServerPassWord"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerPassWord));
		}
		if (FCode.equalsIgnoreCase("Remark4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark4));
		}
		if (FCode.equalsIgnoreCase("Remark5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark5));
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
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ZoneNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BankNode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Remark1);
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
				strFieldValue = StrTool.GBKToUnicode(FTPAddress);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(FTPDir);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(FTPUser);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(FTPPassWord);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Remark2);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Remark3);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ServerDir);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ServerAddress);
				break;
			case 21:
				strFieldValue = String.valueOf(ServerPort);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ServerUser);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ServerPassWord);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Remark4);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Remark5);
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

		if (FCode.equalsIgnoreCase("BankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCode = FValue.trim();
			}
			else
				BankCode = null;
		}
		if (FCode.equalsIgnoreCase("ZoneNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZoneNo = FValue.trim();
			}
			else
				ZoneNo = null;
		}
		if (FCode.equalsIgnoreCase("BankNode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankNode = FValue.trim();
			}
			else
				BankNode = null;
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
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equalsIgnoreCase("Remark1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark1 = FValue.trim();
			}
			else
				Remark1 = null;
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
		if (FCode.equalsIgnoreCase("FTPAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FTPAddress = FValue.trim();
			}
			else
				FTPAddress = null;
		}
		if (FCode.equalsIgnoreCase("FTPDir"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FTPDir = FValue.trim();
			}
			else
				FTPDir = null;
		}
		if (FCode.equalsIgnoreCase("FTPUser"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FTPUser = FValue.trim();
			}
			else
				FTPUser = null;
		}
		if (FCode.equalsIgnoreCase("FTPPassWord"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FTPPassWord = FValue.trim();
			}
			else
				FTPPassWord = null;
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark2 = FValue.trim();
			}
			else
				Remark2 = null;
		}
		if (FCode.equalsIgnoreCase("Remark3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark3 = FValue.trim();
			}
			else
				Remark3 = null;
		}
		if (FCode.equalsIgnoreCase("ServerDir"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServerDir = FValue.trim();
			}
			else
				ServerDir = null;
		}
		if (FCode.equalsIgnoreCase("ServerAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServerAddress = FValue.trim();
			}
			else
				ServerAddress = null;
		}
		if (FCode.equalsIgnoreCase("ServerPort"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ServerPort = i;
			}
		}
		if (FCode.equalsIgnoreCase("ServerUser"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServerUser = FValue.trim();
			}
			else
				ServerUser = null;
		}
		if (FCode.equalsIgnoreCase("ServerPassWord"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServerPassWord = FValue.trim();
			}
			else
				ServerPassWord = null;
		}
		if (FCode.equalsIgnoreCase("Remark4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark4 = FValue.trim();
			}
			else
				Remark4 = null;
		}
		if (FCode.equalsIgnoreCase("Remark5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark5 = FValue.trim();
			}
			else
				Remark5 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LKCodeMappingSchema other = (LKCodeMappingSchema)otherObject;
		return
			BankCode.equals(other.getBankCode())
			&& ZoneNo.equals(other.getZoneNo())
			&& BankNode.equals(other.getBankNode())
			&& AgentCom.equals(other.getAgentCom())
			&& ComCode.equals(other.getComCode())
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
			&& Remark.equals(other.getRemark())
			&& Remark1.equals(other.getRemark1())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& FTPAddress.equals(other.getFTPAddress())
			&& FTPDir.equals(other.getFTPDir())
			&& FTPUser.equals(other.getFTPUser())
			&& FTPPassWord.equals(other.getFTPPassWord())
			&& Remark2.equals(other.getRemark2())
			&& Remark3.equals(other.getRemark3())
			&& ServerDir.equals(other.getServerDir())
			&& ServerAddress.equals(other.getServerAddress())
			&& ServerPort == other.getServerPort()
			&& ServerUser.equals(other.getServerUser())
			&& ServerPassWord.equals(other.getServerPassWord())
			&& Remark4.equals(other.getRemark4())
			&& Remark5.equals(other.getRemark5());
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
		if( strFieldName.equals("BankCode") ) {
			return 0;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return 1;
		}
		if( strFieldName.equals("BankNode") ) {
			return 2;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 3;
		}
		if( strFieldName.equals("ComCode") ) {
			return 4;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 5;
		}
		if( strFieldName.equals("Operator") ) {
			return 6;
		}
		if( strFieldName.equals("Remark") ) {
			return 7;
		}
		if( strFieldName.equals("Remark1") ) {
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
		if( strFieldName.equals("FTPAddress") ) {
			return 13;
		}
		if( strFieldName.equals("FTPDir") ) {
			return 14;
		}
		if( strFieldName.equals("FTPUser") ) {
			return 15;
		}
		if( strFieldName.equals("FTPPassWord") ) {
			return 16;
		}
		if( strFieldName.equals("Remark2") ) {
			return 17;
		}
		if( strFieldName.equals("Remark3") ) {
			return 18;
		}
		if( strFieldName.equals("ServerDir") ) {
			return 19;
		}
		if( strFieldName.equals("ServerAddress") ) {
			return 20;
		}
		if( strFieldName.equals("ServerPort") ) {
			return 21;
		}
		if( strFieldName.equals("ServerUser") ) {
			return 22;
		}
		if( strFieldName.equals("ServerPassWord") ) {
			return 23;
		}
		if( strFieldName.equals("Remark4") ) {
			return 24;
		}
		if( strFieldName.equals("Remark5") ) {
			return 25;
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
				strFieldName = "BankCode";
				break;
			case 1:
				strFieldName = "ZoneNo";
				break;
			case 2:
				strFieldName = "BankNode";
				break;
			case 3:
				strFieldName = "AgentCom";
				break;
			case 4:
				strFieldName = "ComCode";
				break;
			case 5:
				strFieldName = "ManageCom";
				break;
			case 6:
				strFieldName = "Operator";
				break;
			case 7:
				strFieldName = "Remark";
				break;
			case 8:
				strFieldName = "Remark1";
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
				strFieldName = "FTPAddress";
				break;
			case 14:
				strFieldName = "FTPDir";
				break;
			case 15:
				strFieldName = "FTPUser";
				break;
			case 16:
				strFieldName = "FTPPassWord";
				break;
			case 17:
				strFieldName = "Remark2";
				break;
			case 18:
				strFieldName = "Remark3";
				break;
			case 19:
				strFieldName = "ServerDir";
				break;
			case 20:
				strFieldName = "ServerAddress";
				break;
			case 21:
				strFieldName = "ServerPort";
				break;
			case 22:
				strFieldName = "ServerUser";
				break;
			case 23:
				strFieldName = "ServerPassWord";
				break;
			case 24:
				strFieldName = "Remark4";
				break;
			case 25:
				strFieldName = "Remark5";
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
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankNode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark1") ) {
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
		if( strFieldName.equals("FTPAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FTPDir") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FTPUser") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FTPPassWord") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerDir") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerPort") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ServerUser") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerPassWord") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark5") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
