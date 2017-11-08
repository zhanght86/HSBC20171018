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
import com.sinosoft.lis.db.PRINTDB;

/*
 * <p>ClassName: PRINTSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class PRINTSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PRINTSchema.class);
	// @Field
	/** 关键号码 */
	private String OtherNo;
	/** 打印类型 */
	private String PrtType;
	/** 打印内容的标题 */
	private String Title;
	/** 打印次数 */
	private int PrtTimes;
	/** 管理机构 */
	private String ManageCom;
	/** 起始日期 */
	private Date StartDate;
	/** 终止日期 */
	private Date EBDDate;
	/** 客户端机器ip */
	private String ClientIP;
	/** 文件服务器ip */
	private String ServerIP;
	/** 文件路径 */
	private String StrPath;
	/** 文件名 */
	private String FileName;
	/** 备用字段 */
	private String OtherFlag;
	/** 备用字段2 */
	private String OtherContent;
	/** 操作员 */
	private String Operator;
	/** 日期时间 */
	private Date MakeDate;

	public static final int FIELDNUM = 15;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PRINTSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "OtherNo";
		pk[1] = "PrtTimes";

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
		PRINTSchema cloned = (PRINTSchema)super.clone();
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
	* 保单号、批单号等
	*/
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		if(aOtherNo!=null && aOtherNo.length()>30)
			throw new IllegalArgumentException("关键号码OtherNo值"+aOtherNo+"的长度"+aOtherNo.length()+"大于最大值30");
		OtherNo = aOtherNo;
	}
	public String getPrtType()
	{
		return PrtType;
	}
	public void setPrtType(String aPrtType)
	{
		if(aPrtType!=null && aPrtType.length()>2)
			throw new IllegalArgumentException("打印类型PrtType值"+aPrtType+"的长度"+aPrtType.length()+"大于最大值2");
		PrtType = aPrtType;
	}
	public String getTitle()
	{
		return Title;
	}
	public void setTitle(String aTitle)
	{
		if(aTitle!=null && aTitle.length()>100)
			throw new IllegalArgumentException("打印内容的标题Title值"+aTitle+"的长度"+aTitle.length()+"大于最大值100");
		Title = aTitle;
	}
	public int getPrtTimes()
	{
		return PrtTimes;
	}
	public void setPrtTimes(int aPrtTimes)
	{
		PrtTimes = aPrtTimes;
	}
	public void setPrtTimes(String aPrtTimes)
	{
		if (aPrtTimes != null && !aPrtTimes.equals(""))
		{
			Integer tInteger = new Integer(aPrtTimes);
			int i = tInteger.intValue();
			PrtTimes = i;
		}
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
	/**
	* 暂不用
	*/
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

	/**
	* 暂不用
	*/
	public String getEBDDate()
	{
		if( EBDDate != null )
			return fDate.getString(EBDDate);
		else
			return null;
	}
	public void setEBDDate(Date aEBDDate)
	{
		EBDDate = aEBDDate;
	}
	public void setEBDDate(String aEBDDate)
	{
		if (aEBDDate != null && !aEBDDate.equals("") )
		{
			EBDDate = fDate.getDate( aEBDDate );
		}
		else
			EBDDate = null;
	}

	public String getClientIP()
	{
		return ClientIP;
	}
	public void setClientIP(String aClientIP)
	{
		if(aClientIP!=null && aClientIP.length()>15)
			throw new IllegalArgumentException("客户端机器ipClientIP值"+aClientIP+"的长度"+aClientIP.length()+"大于最大值15");
		ClientIP = aClientIP;
	}
	public String getServerIP()
	{
		return ServerIP;
	}
	public void setServerIP(String aServerIP)
	{
		if(aServerIP!=null && aServerIP.length()>15)
			throw new IllegalArgumentException("文件服务器ipServerIP值"+aServerIP+"的长度"+aServerIP.length()+"大于最大值15");
		ServerIP = aServerIP;
	}
	public String getStrPath()
	{
		return StrPath;
	}
	public void setStrPath(String aStrPath)
	{
		if(aStrPath!=null && aStrPath.length()>200)
			throw new IllegalArgumentException("文件路径StrPath值"+aStrPath+"的长度"+aStrPath.length()+"大于最大值200");
		StrPath = aStrPath;
	}
	public String getFileName()
	{
		return FileName;
	}
	public void setFileName(String aFileName)
	{
		if(aFileName!=null && aFileName.length()>200)
			throw new IllegalArgumentException("文件名FileName值"+aFileName+"的长度"+aFileName.length()+"大于最大值200");
		FileName = aFileName;
	}
	public String getOtherFlag()
	{
		return OtherFlag;
	}
	public void setOtherFlag(String aOtherFlag)
	{
		if(aOtherFlag!=null && aOtherFlag.length()>2)
			throw new IllegalArgumentException("备用字段OtherFlag值"+aOtherFlag+"的长度"+aOtherFlag.length()+"大于最大值2");
		OtherFlag = aOtherFlag;
	}
	public String getOtherContent()
	{
		return OtherContent;
	}
	public void setOtherContent(String aOtherContent)
	{
		if(aOtherContent!=null && aOtherContent.length()>2)
			throw new IllegalArgumentException("备用字段2OtherContent值"+aOtherContent+"的长度"+aOtherContent.length()+"大于最大值2");
		OtherContent = aOtherContent;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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


	/**
	* 使用另外一个 PRINTSchema 对象给 Schema 赋值
	* @param: aPRINTSchema PRINTSchema
	**/
	public void setSchema(PRINTSchema aPRINTSchema)
	{
		this.OtherNo = aPRINTSchema.getOtherNo();
		this.PrtType = aPRINTSchema.getPrtType();
		this.Title = aPRINTSchema.getTitle();
		this.PrtTimes = aPRINTSchema.getPrtTimes();
		this.ManageCom = aPRINTSchema.getManageCom();
		this.StartDate = fDate.getDate( aPRINTSchema.getStartDate());
		this.EBDDate = fDate.getDate( aPRINTSchema.getEBDDate());
		this.ClientIP = aPRINTSchema.getClientIP();
		this.ServerIP = aPRINTSchema.getServerIP();
		this.StrPath = aPRINTSchema.getStrPath();
		this.FileName = aPRINTSchema.getFileName();
		this.OtherFlag = aPRINTSchema.getOtherFlag();
		this.OtherContent = aPRINTSchema.getOtherContent();
		this.Operator = aPRINTSchema.getOperator();
		this.MakeDate = fDate.getDate( aPRINTSchema.getMakeDate());
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
			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("PrtType") == null )
				this.PrtType = null;
			else
				this.PrtType = rs.getString("PrtType").trim();

			if( rs.getString("Title") == null )
				this.Title = null;
			else
				this.Title = rs.getString("Title").trim();

			this.PrtTimes = rs.getInt("PrtTimes");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EBDDate = rs.getDate("EBDDate");
			if( rs.getString("ClientIP") == null )
				this.ClientIP = null;
			else
				this.ClientIP = rs.getString("ClientIP").trim();

			if( rs.getString("ServerIP") == null )
				this.ServerIP = null;
			else
				this.ServerIP = rs.getString("ServerIP").trim();

			if( rs.getString("StrPath") == null )
				this.StrPath = null;
			else
				this.StrPath = rs.getString("StrPath").trim();

			if( rs.getString("FileName") == null )
				this.FileName = null;
			else
				this.FileName = rs.getString("FileName").trim();

			if( rs.getString("OtherFlag") == null )
				this.OtherFlag = null;
			else
				this.OtherFlag = rs.getString("OtherFlag").trim();

			if( rs.getString("OtherContent") == null )
				this.OtherContent = null;
			else
				this.OtherContent = rs.getString("OtherContent").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PRINT表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PRINTSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PRINTSchema getSchema()
	{
		PRINTSchema aPRINTSchema = new PRINTSchema();
		aPRINTSchema.setSchema(this);
		return aPRINTSchema;
	}

	public PRINTDB getDB()
	{
		PRINTDB aDBOper = new PRINTDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPRINT描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Title)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrtTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EBDDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClientIP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerIP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StrPath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FileName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPRINT>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PrtType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Title = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PrtTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			EBDDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			ClientIP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ServerIP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			StrPath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			FileName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			OtherFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			OtherContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PRINTSchema";
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
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("PrtType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtType));
		}
		if (FCode.equalsIgnoreCase("Title"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Title));
		}
		if (FCode.equalsIgnoreCase("PrtTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtTimes));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EBDDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEBDDate()));
		}
		if (FCode.equalsIgnoreCase("ClientIP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClientIP));
		}
		if (FCode.equalsIgnoreCase("ServerIP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerIP));
		}
		if (FCode.equalsIgnoreCase("StrPath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StrPath));
		}
		if (FCode.equalsIgnoreCase("FileName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FileName));
		}
		if (FCode.equalsIgnoreCase("OtherFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherFlag));
		}
		if (FCode.equalsIgnoreCase("OtherContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherContent));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
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
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PrtType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Title);
				break;
			case 3:
				strFieldValue = String.valueOf(PrtTimes);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEBDDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ClientIP);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ServerIP);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(StrPath);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(FileName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(OtherFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(OtherContent);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
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

		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("PrtType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtType = FValue.trim();
			}
			else
				PrtType = null;
		}
		if (FCode.equalsIgnoreCase("Title"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Title = FValue.trim();
			}
			else
				Title = null;
		}
		if (FCode.equalsIgnoreCase("PrtTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PrtTimes = i;
			}
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
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("EBDDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EBDDate = fDate.getDate( FValue );
			}
			else
				EBDDate = null;
		}
		if (FCode.equalsIgnoreCase("ClientIP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClientIP = FValue.trim();
			}
			else
				ClientIP = null;
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
		if (FCode.equalsIgnoreCase("StrPath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StrPath = FValue.trim();
			}
			else
				StrPath = null;
		}
		if (FCode.equalsIgnoreCase("FileName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FileName = FValue.trim();
			}
			else
				FileName = null;
		}
		if (FCode.equalsIgnoreCase("OtherFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherFlag = FValue.trim();
			}
			else
				OtherFlag = null;
		}
		if (FCode.equalsIgnoreCase("OtherContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherContent = FValue.trim();
			}
			else
				OtherContent = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PRINTSchema other = (PRINTSchema)otherObject;
		return
			OtherNo.equals(other.getOtherNo())
			&& PrtType.equals(other.getPrtType())
			&& Title.equals(other.getTitle())
			&& PrtTimes == other.getPrtTimes()
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EBDDate).equals(other.getEBDDate())
			&& ClientIP.equals(other.getClientIP())
			&& ServerIP.equals(other.getServerIP())
			&& StrPath.equals(other.getStrPath())
			&& FileName.equals(other.getFileName())
			&& OtherFlag.equals(other.getOtherFlag())
			&& OtherContent.equals(other.getOtherContent())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate());
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
		if( strFieldName.equals("OtherNo") ) {
			return 0;
		}
		if( strFieldName.equals("PrtType") ) {
			return 1;
		}
		if( strFieldName.equals("Title") ) {
			return 2;
		}
		if( strFieldName.equals("PrtTimes") ) {
			return 3;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 4;
		}
		if( strFieldName.equals("StartDate") ) {
			return 5;
		}
		if( strFieldName.equals("EBDDate") ) {
			return 6;
		}
		if( strFieldName.equals("ClientIP") ) {
			return 7;
		}
		if( strFieldName.equals("ServerIP") ) {
			return 8;
		}
		if( strFieldName.equals("StrPath") ) {
			return 9;
		}
		if( strFieldName.equals("FileName") ) {
			return 10;
		}
		if( strFieldName.equals("OtherFlag") ) {
			return 11;
		}
		if( strFieldName.equals("OtherContent") ) {
			return 12;
		}
		if( strFieldName.equals("Operator") ) {
			return 13;
		}
		if( strFieldName.equals("MakeDate") ) {
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
				strFieldName = "OtherNo";
				break;
			case 1:
				strFieldName = "PrtType";
				break;
			case 2:
				strFieldName = "Title";
				break;
			case 3:
				strFieldName = "PrtTimes";
				break;
			case 4:
				strFieldName = "ManageCom";
				break;
			case 5:
				strFieldName = "StartDate";
				break;
			case 6:
				strFieldName = "EBDDate";
				break;
			case 7:
				strFieldName = "ClientIP";
				break;
			case 8:
				strFieldName = "ServerIP";
				break;
			case 9:
				strFieldName = "StrPath";
				break;
			case 10:
				strFieldName = "FileName";
				break;
			case 11:
				strFieldName = "OtherFlag";
				break;
			case 12:
				strFieldName = "OtherContent";
				break;
			case 13:
				strFieldName = "Operator";
				break;
			case 14:
				strFieldName = "MakeDate";
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
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Title") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EBDDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ClientIP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerIP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StrPath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FileName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
