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
import com.sinosoft.lis.db.ES_DOC_PAGES_BDB;

/*
 * <p>ClassName: ES_DOC_PAGES_BSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: EasyScanV4
 */
public class ES_DOC_PAGES_BSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ES_DOC_PAGES_BSchema.class);

	// @Field
	/** 迁移批次号 */
	private String MoveID;
	/** 页编号 */
	private int PageID;
	/** 单证编号 */
	private int DocID;
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
	/** 页状态 */
	private String PageFlag;
	/** 文件存放相对路径http */
	private String PicPath;
	/** 页面类型 */
	private String PageType;
	/** 管理机构 */
	private String ManageCom;
	/** 扫描操作员 */
	private String Operator;
	/** 扫描日期 */
	private Date MakeDate;
	/** 扫描时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 扫描批次号 */
	private String ScanNo;
	/** 迁移类型 */
	private String MoveType;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ES_DOC_PAGES_BSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "MoveID";
		pk[1] = "PageID";

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
		ES_DOC_PAGES_BSchema cloned = (ES_DOC_PAGES_BSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getMoveID()
	{
		return MoveID;
	}
	public void setMoveID(String aMoveID)
	{
		MoveID = aMoveID;
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
	* 0--本地<p>
	* 1--中心
	*/
	public String getPageFlag()
	{
		return PageFlag;
	}
	public void setPageFlag(String aPageFlag)
	{
		PageFlag = aPageFlag;
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
	public String getPageType()
	{
		return PageType;
	}
	public void setPageType(String aPageType)
	{
		PageType = aPageType;
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
	public String getScanNo()
	{
		return ScanNo;
	}
	public void setScanNo(String aScanNo)
	{
		ScanNo = aScanNo;
	}
	/**
	* 0 - 单证迁移转储<p>
	* 1 - 单证删除<p>
	* 2 - 单证修改
	*/
	public String getMoveType()
	{
		return MoveType;
	}
	public void setMoveType(String aMoveType)
	{
		MoveType = aMoveType;
	}

	/**
	* 使用另外一个 ES_DOC_PAGES_BSchema 对象给 Schema 赋值
	* @param: aES_DOC_PAGES_BSchema ES_DOC_PAGES_BSchema
	**/
	public void setSchema(ES_DOC_PAGES_BSchema aES_DOC_PAGES_BSchema)
	{
		this.MoveID = aES_DOC_PAGES_BSchema.getMoveID();
		this.PageID = aES_DOC_PAGES_BSchema.getPageID();
		this.DocID = aES_DOC_PAGES_BSchema.getDocID();
		this.PageCode = aES_DOC_PAGES_BSchema.getPageCode();
		this.HostName = aES_DOC_PAGES_BSchema.getHostName();
		this.PageName = aES_DOC_PAGES_BSchema.getPageName();
		this.PageSuffix = aES_DOC_PAGES_BSchema.getPageSuffix();
		this.PicPathFTP = aES_DOC_PAGES_BSchema.getPicPathFTP();
		this.PageFlag = aES_DOC_PAGES_BSchema.getPageFlag();
		this.PicPath = aES_DOC_PAGES_BSchema.getPicPath();
		this.PageType = aES_DOC_PAGES_BSchema.getPageType();
		this.ManageCom = aES_DOC_PAGES_BSchema.getManageCom();
		this.Operator = aES_DOC_PAGES_BSchema.getOperator();
		this.MakeDate = fDate.getDate( aES_DOC_PAGES_BSchema.getMakeDate());
		this.MakeTime = aES_DOC_PAGES_BSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aES_DOC_PAGES_BSchema.getModifyDate());
		this.ModifyTime = aES_DOC_PAGES_BSchema.getModifyTime();
		this.ScanNo = aES_DOC_PAGES_BSchema.getScanNo();
		this.MoveType = aES_DOC_PAGES_BSchema.getMoveType();
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
			if( rs.getString("MoveID") == null )
				this.MoveID = null;
			else
				this.MoveID = rs.getString("MoveID").trim();

			this.PageID = rs.getInt("PageID");
			this.DocID = rs.getInt("DocID");
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

			if( rs.getString("PageFlag") == null )
				this.PageFlag = null;
			else
				this.PageFlag = rs.getString("PageFlag").trim();

			if( rs.getString("PicPath") == null )
				this.PicPath = null;
			else
				this.PicPath = rs.getString("PicPath").trim();

			if( rs.getString("PageType") == null )
				this.PageType = null;
			else
				this.PageType = rs.getString("PageType").trim();

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

			if( rs.getString("ScanNo") == null )
				this.ScanNo = null;
			else
				this.ScanNo = rs.getString("ScanNo").trim();

			if( rs.getString("MoveType") == null )
				this.MoveType = null;
			else
				this.MoveType = rs.getString("MoveType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的ES_DOC_PAGES_B表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_DOC_PAGES_BSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ES_DOC_PAGES_BSchema getSchema()
	{
		ES_DOC_PAGES_BSchema aES_DOC_PAGES_BSchema = new ES_DOC_PAGES_BSchema();
		aES_DOC_PAGES_BSchema.setSchema(this);
		return aES_DOC_PAGES_BSchema;
	}

	public ES_DOC_PAGES_BDB getDB()
	{
		ES_DOC_PAGES_BDB aDBOper = new ES_DOC_PAGES_BDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_DOC_PAGES_B描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(MoveID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PageID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DocID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PageCode));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HostName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PageName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PageSuffix)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PicPathFTP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PageFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PicPath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PageType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ScanNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MoveType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_DOC_PAGES_B>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			MoveID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PageID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			DocID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			PageCode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			HostName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PageName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PageSuffix = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PicPathFTP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PageFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PicPath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PageType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ScanNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MoveType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_DOC_PAGES_BSchema";
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
		if (FCode.equalsIgnoreCase("MoveID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MoveID));
		}
		if (FCode.equalsIgnoreCase("PageID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PageID));
		}
		if (FCode.equalsIgnoreCase("DocID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DocID));
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
		if (FCode.equalsIgnoreCase("PageFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PageFlag));
		}
		if (FCode.equalsIgnoreCase("PicPath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PicPath));
		}
		if (FCode.equalsIgnoreCase("PageType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PageType));
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
		if (FCode.equalsIgnoreCase("ScanNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ScanNo));
		}
		if (FCode.equalsIgnoreCase("MoveType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MoveType));
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
				strFieldValue = StrTool.GBKToUnicode(MoveID);
				break;
			case 1:
				strFieldValue = String.valueOf(PageID);
				break;
			case 2:
				strFieldValue = String.valueOf(DocID);
				break;
			case 3:
				strFieldValue = String.valueOf(PageCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(HostName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PageName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PageSuffix);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PicPathFTP);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PageFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PicPath);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PageType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ScanNo);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MoveType);
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

		if (FCode.equalsIgnoreCase("MoveID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MoveID = FValue.trim();
			}
			else
				MoveID = null;
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
		if (FCode.equalsIgnoreCase("PageFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PageFlag = FValue.trim();
			}
			else
				PageFlag = null;
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
		if (FCode.equalsIgnoreCase("PageType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PageType = FValue.trim();
			}
			else
				PageType = null;
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
		if (FCode.equalsIgnoreCase("ScanNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ScanNo = FValue.trim();
			}
			else
				ScanNo = null;
		}
		if (FCode.equalsIgnoreCase("MoveType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MoveType = FValue.trim();
			}
			else
				MoveType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ES_DOC_PAGES_BSchema other = (ES_DOC_PAGES_BSchema)otherObject;
		return
			MoveID.equals(other.getMoveID())
			&& PageID == other.getPageID()
			&& DocID == other.getDocID()
			&& PageCode == other.getPageCode()
			&& HostName.equals(other.getHostName())
			&& PageName.equals(other.getPageName())
			&& PageSuffix.equals(other.getPageSuffix())
			&& PicPathFTP.equals(other.getPicPathFTP())
			&& PageFlag.equals(other.getPageFlag())
			&& PicPath.equals(other.getPicPath())
			&& PageType.equals(other.getPageType())
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ScanNo.equals(other.getScanNo())
			&& MoveType.equals(other.getMoveType());
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
		if( strFieldName.equals("MoveID") ) {
			return 0;
		}
		if( strFieldName.equals("PageID") ) {
			return 1;
		}
		if( strFieldName.equals("DocID") ) {
			return 2;
		}
		if( strFieldName.equals("PageCode") ) {
			return 3;
		}
		if( strFieldName.equals("HostName") ) {
			return 4;
		}
		if( strFieldName.equals("PageName") ) {
			return 5;
		}
		if( strFieldName.equals("PageSuffix") ) {
			return 6;
		}
		if( strFieldName.equals("PicPathFTP") ) {
			return 7;
		}
		if( strFieldName.equals("PageFlag") ) {
			return 8;
		}
		if( strFieldName.equals("PicPath") ) {
			return 9;
		}
		if( strFieldName.equals("PageType") ) {
			return 10;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 11;
		}
		if( strFieldName.equals("Operator") ) {
			return 12;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 13;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 16;
		}
		if( strFieldName.equals("ScanNo") ) {
			return 17;
		}
		if( strFieldName.equals("MoveType") ) {
			return 18;
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
				strFieldName = "MoveID";
				break;
			case 1:
				strFieldName = "PageID";
				break;
			case 2:
				strFieldName = "DocID";
				break;
			case 3:
				strFieldName = "PageCode";
				break;
			case 4:
				strFieldName = "HostName";
				break;
			case 5:
				strFieldName = "PageName";
				break;
			case 6:
				strFieldName = "PageSuffix";
				break;
			case 7:
				strFieldName = "PicPathFTP";
				break;
			case 8:
				strFieldName = "PageFlag";
				break;
			case 9:
				strFieldName = "PicPath";
				break;
			case 10:
				strFieldName = "PageType";
				break;
			case 11:
				strFieldName = "ManageCom";
				break;
			case 12:
				strFieldName = "Operator";
				break;
			case 13:
				strFieldName = "MakeDate";
				break;
			case 14:
				strFieldName = "MakeTime";
				break;
			case 15:
				strFieldName = "ModifyDate";
				break;
			case 16:
				strFieldName = "ModifyTime";
				break;
			case 17:
				strFieldName = "ScanNo";
				break;
			case 18:
				strFieldName = "MoveType";
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
		if( strFieldName.equals("MoveID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PageID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DocID") ) {
			return Schema.TYPE_INT;
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
		if( strFieldName.equals("PageFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PicPath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PageType") ) {
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
		if( strFieldName.equals("ScanNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MoveType") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_INT;
				break;
			case 3:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
