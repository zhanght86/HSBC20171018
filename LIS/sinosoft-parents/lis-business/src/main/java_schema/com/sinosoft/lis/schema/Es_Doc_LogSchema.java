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
import com.sinosoft.lis.db.Es_Doc_LogDB;

/*
 * <p>ClassName: Es_Doc_LogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: bug4
 */
public class Es_Doc_LogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(Es_Doc_LogSchema.class);
	// @Field
	/** 日志id */
	private int LogID;
	/** 业务类型 */
	private String BussType;
	/** 单证细类 */
	private String SubType;
	/** 业务关联号码 */
	private String BussNo;
	/** 业务关联号码类型 */
	private String BussNoType;
	/** 页编号 */
	private int PageID;
	/** 单证编号 */
	private int DocID;
	/** 管理机构 */
	private String ManageCom;
	/** 扫描操作员 */
	private String ScanOperator;
	/** 页号码 */
	private int PageCode;
	/** 影像服务器名 */
	private String HostName;
	/** 图像文件名 */
	private String PageName;
	/** 文件后缀 */
	private String PageSuffix;
	/** 文件存放相对路径ftp */
	private String PicPathFTP;
	/** 文件存放相对路径http */
	private String PicPath;
	/** 批次号 */
	private String ScanNo;
	/** 扫描日期 */
	private Date MakeDate;
	/** 扫描时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 修改操作描述 */
	private String ModifyDesc;
	/** 修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public Es_Doc_LogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "LogID";

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
		Es_Doc_LogSchema cloned = (Es_Doc_LogSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public int getLogID()
	{
		return LogID;
	}
	public void setLogID(int aLogID)
	{
		LogID = aLogID;
	}
	public void setLogID(String aLogID)
	{
		if (aLogID != null && !aLogID.equals(""))
		{
			Integer tInteger = new Integer(aLogID);
			int i = tInteger.intValue();
			LogID = i;
		}
	}

	/**
	* 如果不知道就为空,系统会查找出与业务号码相关的全部单证
	*/
	public String getBussType()
	{
		return BussType;
	}
	public void setBussType(String aBussType)
	{
		BussType = aBussType;
	}
	/**
	* 如果不���道就为空,系统会查找出与业务号码相关的全部单证
	*/
	public String getSubType()
	{
		return SubType;
	}
	public void setSubType(String aSubType)
	{
		SubType = aSubType;
	}
	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		BussNo = aBussNo;
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
	* 索引
	*/
	public int getPageID()
	{
		return PageID;
	}
	public void setPageID(int aPageID)
	{
		PageID = aPageID;
	}
	public void setPageID(String aPageID)
	{
		if (aPageID != null && !aPageID.equals(""))
		{
			Integer tInteger = new Integer(aPageID);
			int i = tInteger.intValue();
			PageID = i;
		}
	}

	/**
	* 单据ID<p>
	* 与MAIN表外键
	*/
	public int getDocID()
	{
		return DocID;
	}
	public void setDocID(int aDocID)
	{
		DocID = aDocID;
	}
	public void setDocID(String aDocID)
	{
		if (aDocID != null && !aDocID.equals(""))
		{
			Integer tInteger = new Integer(aDocID);
			int i = tInteger.intValue();
			DocID = i;
		}
	}

	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getScanOperator()
	{
		return ScanOperator;
	}
	public void setScanOperator(String aScanOperator)
	{
		ScanOperator = aScanOperator;
	}
	/**
	* 页码
	*/
	public int getPageCode()
	{
		return PageCode;
	}
	public void setPageCode(int aPageCode)
	{
		PageCode = aPageCode;
	}
	public void setPageCode(String aPageCode)
	{
		if (aPageCode != null && !aPageCode.equals(""))
		{
			Integer tInteger = new Integer(aPageCode);
			int i = tInteger.intValue();
			PageCode = i;
		}
	}

	/**
	* 服务器名称
	*/
	public String getHostName()
	{
		return HostName;
	}
	public void setHostName(String aHostName)
	{
		HostName = aHostName;
	}
	/**
	* 文件名<p>
	* 不带后缀名，自己添加（JPG，TIF）
	*/
	public String getPageName()
	{
		return PageName;
	}
	public void setPageName(String aPageName)
	{
		PageName = aPageName;
	}
	public String getPageSuffix()
	{
		return PageSuffix;
	}
	public void setPageSuffix(String aPageSuffix)
	{
		PageSuffix = aPageSuffix;
	}
	/**
	* 相对路径，相对于FTP服务器
	*/
	public String getPicPathFTP()
	{
		return PicPathFTP;
	}
	public void setPicPathFTP(String aPicPathFTP)
	{
		PicPathFTP = aPicPathFTP;
	}
	/**
	* 相对路径，相对于HTTP服务器
	*/
	public String getPicPath()
	{
		return PicPath;
	}
	public void setPicPath(String aPicPath)
	{
		PicPath = aPicPath;
	}
	public String getScanNo()
	{
		return ScanNo;
	}
	public void setScanNo(String aScanNo)
	{
		ScanNo = aScanNo;
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
	public String getModifyDesc()
	{
		return ModifyDesc;
	}
	public void setModifyDesc(String aModifyDesc)
	{
		ModifyDesc = aModifyDesc;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		ModifyOperator = aModifyOperator;
	}

	/**
	* 使用另外一个 Es_Doc_LogSchema 对象给 Schema 赋值
	* @param: aEs_Doc_LogSchema Es_Doc_LogSchema
	**/
	public void setSchema(Es_Doc_LogSchema aEs_Doc_LogSchema)
	{
		this.LogID = aEs_Doc_LogSchema.getLogID();
		this.BussType = aEs_Doc_LogSchema.getBussType();
		this.SubType = aEs_Doc_LogSchema.getSubType();
		this.BussNo = aEs_Doc_LogSchema.getBussNo();
		this.BussNoType = aEs_Doc_LogSchema.getBussNoType();
		this.PageID = aEs_Doc_LogSchema.getPageID();
		this.DocID = aEs_Doc_LogSchema.getDocID();
		this.ManageCom = aEs_Doc_LogSchema.getManageCom();
		this.ScanOperator = aEs_Doc_LogSchema.getScanOperator();
		this.PageCode = aEs_Doc_LogSchema.getPageCode();
		this.HostName = aEs_Doc_LogSchema.getHostName();
		this.PageName = aEs_Doc_LogSchema.getPageName();
		this.PageSuffix = aEs_Doc_LogSchema.getPageSuffix();
		this.PicPathFTP = aEs_Doc_LogSchema.getPicPathFTP();
		this.PicPath = aEs_Doc_LogSchema.getPicPath();
		this.ScanNo = aEs_Doc_LogSchema.getScanNo();
		this.MakeDate = fDate.getDate( aEs_Doc_LogSchema.getMakeDate());
		this.MakeTime = aEs_Doc_LogSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aEs_Doc_LogSchema.getModifyDate());
		this.ModifyTime = aEs_Doc_LogSchema.getModifyTime();
		this.ModifyDesc = aEs_Doc_LogSchema.getModifyDesc();
		this.ModifyOperator = aEs_Doc_LogSchema.getModifyOperator();
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
			this.LogID = rs.getInt("LogID");
			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("SubType") == null )
				this.SubType = null;
			else
				this.SubType = rs.getString("SubType").trim();

			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			if( rs.getString("BussNoType") == null )
				this.BussNoType = null;
			else
				this.BussNoType = rs.getString("BussNoType").trim();

			this.PageID = rs.getInt("PageID");
			this.DocID = rs.getInt("DocID");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ScanOperator") == null )
				this.ScanOperator = null;
			else
				this.ScanOperator = rs.getString("ScanOperator").trim();

			this.PageCode = rs.getInt("PageCode");
			if( rs.getString("HostName") == null )
				this.HostName = null;
			else
				this.HostName = rs.getString("HostName").trim();

			if( rs.getString("PageName") == null )
				this.PageName = null;
			else
				this.PageName = rs.getString("PageName").trim();

			if( rs.getString("PageSuffix") == null )
				this.PageSuffix = null;
			else
				this.PageSuffix = rs.getString("PageSuffix").trim();

			if( rs.getString("PicPathFTP") == null )
				this.PicPathFTP = null;
			else
				this.PicPathFTP = rs.getString("PicPathFTP").trim();

			if( rs.getString("PicPath") == null )
				this.PicPath = null;
			else
				this.PicPath = rs.getString("PicPath").trim();

			if( rs.getString("ScanNo") == null )
				this.ScanNo = null;
			else
				this.ScanNo = rs.getString("ScanNo").trim();

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

			if( rs.getString("ModifyDesc") == null )
				this.ModifyDesc = null;
			else
				this.ModifyDesc = rs.getString("ModifyDesc").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的Es_Doc_Log表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "Es_Doc_LogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public Es_Doc_LogSchema getSchema()
	{
		Es_Doc_LogSchema aEs_Doc_LogSchema = new Es_Doc_LogSchema();
		aEs_Doc_LogSchema.setSchema(this);
		return aEs_Doc_LogSchema;
	}

	public Es_Doc_LogDB getDB()
	{
		Es_Doc_LogDB aDBOper = new Es_Doc_LogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpEs_Doc_Log描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(LogID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PageID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DocID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ScanOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PageCode));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HostName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PageName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PageSuffix)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PicPathFTP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PicPath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ScanNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpEs_Doc_Log>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			LogID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SubType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BussNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PageID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			DocID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ScanOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PageCode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			HostName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PageName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PageSuffix = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PicPathFTP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			PicPath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ScanNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "Es_Doc_LogSchema";
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
		if (FCode.equalsIgnoreCase("LogID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogID));
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubType));
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("BussNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNoType));
		}
		if (FCode.equalsIgnoreCase("PageID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PageID));
		}
		if (FCode.equalsIgnoreCase("DocID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DocID));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ScanOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ScanOperator));
		}
		if (FCode.equalsIgnoreCase("PageCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PageCode));
		}
		if (FCode.equalsIgnoreCase("HostName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HostName));
		}
		if (FCode.equalsIgnoreCase("PageName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PageName));
		}
		if (FCode.equalsIgnoreCase("PageSuffix"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PageSuffix));
		}
		if (FCode.equalsIgnoreCase("PicPathFTP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PicPathFTP));
		}
		if (FCode.equalsIgnoreCase("PicPath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PicPath));
		}
		if (FCode.equalsIgnoreCase("ScanNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ScanNo));
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
		if (FCode.equalsIgnoreCase("ModifyDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyDesc));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = String.valueOf(LogID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SubType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BussNoType);
				break;
			case 5:
				strFieldValue = String.valueOf(PageID);
				break;
			case 6:
				strFieldValue = String.valueOf(DocID);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ScanOperator);
				break;
			case 9:
				strFieldValue = String.valueOf(PageCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(HostName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PageName);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(PageSuffix);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(PicPathFTP);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(PicPath);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ScanNo);
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
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ModifyDesc);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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

		if (FCode.equalsIgnoreCase("LogID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				LogID = i;
			}
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussType = FValue.trim();
			}
			else
				BussType = null;
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubType = FValue.trim();
			}
			else
				SubType = null;
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNo = FValue.trim();
			}
			else
				BussNo = null;
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
		if (FCode.equalsIgnoreCase("PageID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PageID = i;
			}
		}
		if (FCode.equalsIgnoreCase("DocID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				DocID = i;
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
		if (FCode.equalsIgnoreCase("ScanOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ScanOperator = FValue.trim();
			}
			else
				ScanOperator = null;
		}
		if (FCode.equalsIgnoreCase("PageCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PageCode = i;
			}
		}
		if (FCode.equalsIgnoreCase("HostName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HostName = FValue.trim();
			}
			else
				HostName = null;
		}
		if (FCode.equalsIgnoreCase("PageName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PageName = FValue.trim();
			}
			else
				PageName = null;
		}
		if (FCode.equalsIgnoreCase("PageSuffix"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PageSuffix = FValue.trim();
			}
			else
				PageSuffix = null;
		}
		if (FCode.equalsIgnoreCase("PicPathFTP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PicPathFTP = FValue.trim();
			}
			else
				PicPathFTP = null;
		}
		if (FCode.equalsIgnoreCase("PicPath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PicPath = FValue.trim();
			}
			else
				PicPath = null;
		}
		if (FCode.equalsIgnoreCase("ScanNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ScanNo = FValue.trim();
			}
			else
				ScanNo = null;
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
		if (FCode.equalsIgnoreCase("ModifyDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyDesc = FValue.trim();
			}
			else
				ModifyDesc = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		Es_Doc_LogSchema other = (Es_Doc_LogSchema)otherObject;
		return
			LogID == other.getLogID()
			&& BussType.equals(other.getBussType())
			&& SubType.equals(other.getSubType())
			&& BussNo.equals(other.getBussNo())
			&& BussNoType.equals(other.getBussNoType())
			&& PageID == other.getPageID()
			&& DocID == other.getDocID()
			&& ManageCom.equals(other.getManageCom())
			&& ScanOperator.equals(other.getScanOperator())
			&& PageCode == other.getPageCode()
			&& HostName.equals(other.getHostName())
			&& PageName.equals(other.getPageName())
			&& PageSuffix.equals(other.getPageSuffix())
			&& PicPathFTP.equals(other.getPicPathFTP())
			&& PicPath.equals(other.getPicPath())
			&& ScanNo.equals(other.getScanNo())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ModifyDesc.equals(other.getModifyDesc())
			&& ModifyOperator.equals(other.getModifyOperator());
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
		if( strFieldName.equals("LogID") ) {
			return 0;
		}
		if( strFieldName.equals("BussType") ) {
			return 1;
		}
		if( strFieldName.equals("SubType") ) {
			return 2;
		}
		if( strFieldName.equals("BussNo") ) {
			return 3;
		}
		if( strFieldName.equals("BussNoType") ) {
			return 4;
		}
		if( strFieldName.equals("PageID") ) {
			return 5;
		}
		if( strFieldName.equals("DocID") ) {
			return 6;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 7;
		}
		if( strFieldName.equals("ScanOperator") ) {
			return 8;
		}
		if( strFieldName.equals("PageCode") ) {
			return 9;
		}
		if( strFieldName.equals("HostName") ) {
			return 10;
		}
		if( strFieldName.equals("PageName") ) {
			return 11;
		}
		if( strFieldName.equals("PageSuffix") ) {
			return 12;
		}
		if( strFieldName.equals("PicPathFTP") ) {
			return 13;
		}
		if( strFieldName.equals("PicPath") ) {
			return 14;
		}
		if( strFieldName.equals("ScanNo") ) {
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
		if( strFieldName.equals("ModifyDesc") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				strFieldName = "LogID";
				break;
			case 1:
				strFieldName = "BussType";
				break;
			case 2:
				strFieldName = "SubType";
				break;
			case 3:
				strFieldName = "BussNo";
				break;
			case 4:
				strFieldName = "BussNoType";
				break;
			case 5:
				strFieldName = "PageID";
				break;
			case 6:
				strFieldName = "DocID";
				break;
			case 7:
				strFieldName = "ManageCom";
				break;
			case 8:
				strFieldName = "ScanOperator";
				break;
			case 9:
				strFieldName = "PageCode";
				break;
			case 10:
				strFieldName = "HostName";
				break;
			case 11:
				strFieldName = "PageName";
				break;
			case 12:
				strFieldName = "PageSuffix";
				break;
			case 13:
				strFieldName = "PicPathFTP";
				break;
			case 14:
				strFieldName = "PicPath";
				break;
			case 15:
				strFieldName = "ScanNo";
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
			case 20:
				strFieldName = "ModifyDesc";
				break;
			case 21:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("LogID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PageID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DocID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ScanOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PageCode") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("HostName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PageName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PageSuffix") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PicPathFTP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PicPath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ScanNo") ) {
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
		if( strFieldName.equals("ModifyDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 6:
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_INT;
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
			case 20:
				nFieldType = Schema.TYPE_STRING;
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
