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
import com.sinosoft.lis.db.ES_DOC_MAIN_OLDDB;

/*
 * <p>ClassName: ES_DOC_MAIN_OLDSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: EasyScanV4
 */
public class ES_DOC_MAIN_OLDSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ES_DOC_MAIN_OLDSchema.class);

	// @Field
	/** 单证编号 */
	private int DocID;
	/** 单证号码 */
	private String DocCode;
	/** 业务类型 */
	private String BussType;
	/** 单证细类 */
	private String SubType;
	/** 单证页数 */
	private int NumPages;
	/** 单证状态 */
	private String DocFlag;
	/** 备注 */
	private String DocRemark;
	/** 扫描操作员 */
	private String ScanOperator;
	/** 管理机构 */
	private String ManageCom;
	/** 录入状态 */
	private String InputState;
	/** 操作员 */
	private String Operator;
	/** 录入开始日期 */
	private Date InputStartDate;
	/** 录入开始时间 */
	private String InputStartTime;
	/** 录入结束日期 */
	private Date InputEndDate;
	/** 录入结束时间 */
	private String InputEndTime;
	/** 生成日期 */
	private Date MakeDate;
	/** 生成时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 单证版本 */
	private String Version;
	/** 批次号 */
	private String ScanNo;
	/** 单证印刷号码 */
	private String PrintCode;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ES_DOC_MAIN_OLDSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "DocID";

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
		ES_DOC_MAIN_OLDSchema cloned = (ES_DOC_MAIN_OLDSchema)super.clone();
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
	* 唯一标示一份单证的编号，系统生成
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
	* 一份单证的号码，如投保单印刷号等
	*/
	public String getDocCode()
	{
		return DocCode;
	}
	public void setDocCode(String aDocCode)
	{
		DocCode = aDocCode;
	}
	/**
	* 承保<p>
	* 理赔<p>
	* 保全<p>
	* 未清分
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
	* 投保单<p>
	* 体检资料<p>
	* 生调资料<p>
	* ......
	*/
	public String getSubType()
	{
		return SubType;
	}
	public void setSubType(String aSubType)
	{
		SubType = aSubType;
	}
	/**
	* 该单据页数
	*/
	public int getNumPages()
	{
		return NumPages;
	}
	public void setNumPages(int aNumPages)
	{
		NumPages = aNumPages;
	}
	public void setNumPages(String aNumPages)
	{
		if (aNumPages != null && !aNumPages.equals(""))
		{
			Integer tInteger = new Integer(aNumPages);
			int i = tInteger.intValue();
			NumPages = i;
		}
	}

	/**
	* 0--本地  1--上载中心
	*/
	public String getDocFlag()
	{
		return DocFlag;
	}
	public void setDocFlag(String aDocFlag)
	{
		DocFlag = aDocFlag;
	}
	/**
	* 备注
	*/
	public String getDocRemark()
	{
		return DocRemark;
	}
	public void setDocRemark(String aDocRemark)
	{
		DocRemark = aDocRemark;
	}
	public String getScanOperator()
	{
		return ScanOperator;
	}
	public void setScanOperator(String aScanOperator)
	{
		ScanOperator = aScanOperator;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/**
	* null :新上载，未处理<p>
	* 0    :正在录单<p>
	* 1    :已录单<p>
	* 2    :无效扫描件（如录单时如果代理人离职该扫描件无效）
	*/
	public String getInputState()
	{
		return InputState;
	}
	public void setInputState(String aInputState)
	{
		InputState = aInputState;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getInputStartDate()
	{
		if( InputStartDate != null )
			return fDate.getString(InputStartDate);
		else
			return null;
	}
	public void setInputStartDate(Date aInputStartDate)
	{
		InputStartDate = aInputStartDate;
	}
	public void setInputStartDate(String aInputStartDate)
	{
		if (aInputStartDate != null && !aInputStartDate.equals("") )
		{
			InputStartDate = fDate.getDate( aInputStartDate );
		}
		else
			InputStartDate = null;
	}

	public String getInputStartTime()
	{
		return InputStartTime;
	}
	public void setInputStartTime(String aInputStartTime)
	{
		InputStartTime = aInputStartTime;
	}
	public String getInputEndDate()
	{
		if( InputEndDate != null )
			return fDate.getString(InputEndDate);
		else
			return null;
	}
	public void setInputEndDate(Date aInputEndDate)
	{
		InputEndDate = aInputEndDate;
	}
	public void setInputEndDate(String aInputEndDate)
	{
		if (aInputEndDate != null && !aInputEndDate.equals("") )
		{
			InputEndDate = fDate.getDate( aInputEndDate );
		}
		else
			InputEndDate = null;
	}

	public String getInputEndTime()
	{
		return InputEndTime;
	}
	public void setInputEndTime(String aInputEndTime)
	{
		InputEndTime = aInputEndTime;
	}
	/**
	* 上载日期
	*/
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
	* 上载时间
	*/
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
	* 不能为空，默认为0
	*/
	public String getVersion()
	{
		return Version;
	}
	public void setVersion(String aVersion)
	{
		Version = aVersion;
	}
	/**
	* 不能为空，默认为0
	*/
	public String getScanNo()
	{
		return ScanNo;
	}
	public void setScanNo(String aScanNo)
	{
		ScanNo = aScanNo;
	}
	public String getPrintCode()
	{
		return PrintCode;
	}
	public void setPrintCode(String aPrintCode)
	{
		PrintCode = aPrintCode;
	}

	/**
	* 使用另外一个 ES_DOC_MAIN_OLDSchema 对象给 Schema 赋值
	* @param: aES_DOC_MAIN_OLDSchema ES_DOC_MAIN_OLDSchema
	**/
	public void setSchema(ES_DOC_MAIN_OLDSchema aES_DOC_MAIN_OLDSchema)
	{
		this.DocID = aES_DOC_MAIN_OLDSchema.getDocID();
		this.DocCode = aES_DOC_MAIN_OLDSchema.getDocCode();
		this.BussType = aES_DOC_MAIN_OLDSchema.getBussType();
		this.SubType = aES_DOC_MAIN_OLDSchema.getSubType();
		this.NumPages = aES_DOC_MAIN_OLDSchema.getNumPages();
		this.DocFlag = aES_DOC_MAIN_OLDSchema.getDocFlag();
		this.DocRemark = aES_DOC_MAIN_OLDSchema.getDocRemark();
		this.ScanOperator = aES_DOC_MAIN_OLDSchema.getScanOperator();
		this.ManageCom = aES_DOC_MAIN_OLDSchema.getManageCom();
		this.InputState = aES_DOC_MAIN_OLDSchema.getInputState();
		this.Operator = aES_DOC_MAIN_OLDSchema.getOperator();
		this.InputStartDate = fDate.getDate( aES_DOC_MAIN_OLDSchema.getInputStartDate());
		this.InputStartTime = aES_DOC_MAIN_OLDSchema.getInputStartTime();
		this.InputEndDate = fDate.getDate( aES_DOC_MAIN_OLDSchema.getInputEndDate());
		this.InputEndTime = aES_DOC_MAIN_OLDSchema.getInputEndTime();
		this.MakeDate = fDate.getDate( aES_DOC_MAIN_OLDSchema.getMakeDate());
		this.MakeTime = aES_DOC_MAIN_OLDSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aES_DOC_MAIN_OLDSchema.getModifyDate());
		this.ModifyTime = aES_DOC_MAIN_OLDSchema.getModifyTime();
		this.Version = aES_DOC_MAIN_OLDSchema.getVersion();
		this.ScanNo = aES_DOC_MAIN_OLDSchema.getScanNo();
		this.PrintCode = aES_DOC_MAIN_OLDSchema.getPrintCode();
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
			this.DocID = rs.getInt("DocID");
			if( rs.getString("DocCode") == null )
				this.DocCode = null;
			else
				this.DocCode = rs.getString("DocCode").trim();

			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("SubType") == null )
				this.SubType = null;
			else
				this.SubType = rs.getString("SubType").trim();

			this.NumPages = rs.getInt("NumPages");
			if( rs.getString("DocFlag") == null )
				this.DocFlag = null;
			else
				this.DocFlag = rs.getString("DocFlag").trim();

			if( rs.getString("DocRemark") == null )
				this.DocRemark = null;
			else
				this.DocRemark = rs.getString("DocRemark").trim();

			if( rs.getString("ScanOperator") == null )
				this.ScanOperator = null;
			else
				this.ScanOperator = rs.getString("ScanOperator").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("InputState") == null )
				this.InputState = null;
			else
				this.InputState = rs.getString("InputState").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.InputStartDate = rs.getDate("InputStartDate");
			if( rs.getString("InputStartTime") == null )
				this.InputStartTime = null;
			else
				this.InputStartTime = rs.getString("InputStartTime").trim();

			this.InputEndDate = rs.getDate("InputEndDate");
			if( rs.getString("InputEndTime") == null )
				this.InputEndTime = null;
			else
				this.InputEndTime = rs.getString("InputEndTime").trim();

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

			if( rs.getString("Version") == null )
				this.Version = null;
			else
				this.Version = rs.getString("Version").trim();

			if( rs.getString("ScanNo") == null )
				this.ScanNo = null;
			else
				this.ScanNo = rs.getString("ScanNo").trim();

			if( rs.getString("PrintCode") == null )
				this.PrintCode = null;
			else
				this.PrintCode = rs.getString("PrintCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的ES_DOC_MAIN_OLD表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_DOC_MAIN_OLDSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ES_DOC_MAIN_OLDSchema getSchema()
	{
		ES_DOC_MAIN_OLDSchema aES_DOC_MAIN_OLDSchema = new ES_DOC_MAIN_OLDSchema();
		aES_DOC_MAIN_OLDSchema.setSchema(this);
		return aES_DOC_MAIN_OLDSchema;
	}

	public ES_DOC_MAIN_OLDDB getDB()
	{
		ES_DOC_MAIN_OLDDB aDBOper = new ES_DOC_MAIN_OLDDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_DOC_MAIN_OLD描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(DocID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DocCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NumPages));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DocFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DocRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ScanOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InputStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputStartTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InputEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputEndTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Version)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ScanNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_DOC_MAIN_OLD>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			DocID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			DocCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SubType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			NumPages= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			DocFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DocRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ScanOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			InputState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			InputStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			InputStartTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InputEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			InputEndTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Version = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ScanNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			PrintCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_DOC_MAIN_OLDSchema";
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
		if (FCode.equalsIgnoreCase("DocID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DocID));
		}
		if (FCode.equalsIgnoreCase("DocCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DocCode));
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubType));
		}
		if (FCode.equalsIgnoreCase("NumPages"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumPages));
		}
		if (FCode.equalsIgnoreCase("DocFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DocFlag));
		}
		if (FCode.equalsIgnoreCase("DocRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DocRemark));
		}
		if (FCode.equalsIgnoreCase("ScanOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ScanOperator));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("InputState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputState));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("InputStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInputStartDate()));
		}
		if (FCode.equalsIgnoreCase("InputStartTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputStartTime));
		}
		if (FCode.equalsIgnoreCase("InputEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInputEndDate()));
		}
		if (FCode.equalsIgnoreCase("InputEndTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputEndTime));
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
		if (FCode.equalsIgnoreCase("Version"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Version));
		}
		if (FCode.equalsIgnoreCase("ScanNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ScanNo));
		}
		if (FCode.equalsIgnoreCase("PrintCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintCode));
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
				strFieldValue = String.valueOf(DocID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(DocCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SubType);
				break;
			case 4:
				strFieldValue = String.valueOf(NumPages);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DocFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DocRemark);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ScanOperator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(InputState);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInputStartDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(InputStartTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInputEndDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(InputEndTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Version);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ScanNo);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(PrintCode);
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

		if (FCode.equalsIgnoreCase("DocID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				DocID = i;
			}
		}
		if (FCode.equalsIgnoreCase("DocCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DocCode = FValue.trim();
			}
			else
				DocCode = null;
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
		if (FCode.equalsIgnoreCase("NumPages"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				NumPages = i;
			}
		}
		if (FCode.equalsIgnoreCase("DocFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DocFlag = FValue.trim();
			}
			else
				DocFlag = null;
		}
		if (FCode.equalsIgnoreCase("DocRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DocRemark = FValue.trim();
			}
			else
				DocRemark = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		if (FCode.equalsIgnoreCase("InputStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InputStartDate = fDate.getDate( FValue );
			}
			else
				InputStartDate = null;
		}
		if (FCode.equalsIgnoreCase("InputStartTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputStartTime = FValue.trim();
			}
			else
				InputStartTime = null;
		}
		if (FCode.equalsIgnoreCase("InputEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InputEndDate = fDate.getDate( FValue );
			}
			else
				InputEndDate = null;
		}
		if (FCode.equalsIgnoreCase("InputEndTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputEndTime = FValue.trim();
			}
			else
				InputEndTime = null;
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
		if (FCode.equalsIgnoreCase("Version"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Version = FValue.trim();
			}
			else
				Version = null;
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
		if (FCode.equalsIgnoreCase("PrintCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrintCode = FValue.trim();
			}
			else
				PrintCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ES_DOC_MAIN_OLDSchema other = (ES_DOC_MAIN_OLDSchema)otherObject;
		return
			DocID == other.getDocID()
			&& DocCode.equals(other.getDocCode())
			&& BussType.equals(other.getBussType())
			&& SubType.equals(other.getSubType())
			&& NumPages == other.getNumPages()
			&& DocFlag.equals(other.getDocFlag())
			&& DocRemark.equals(other.getDocRemark())
			&& ScanOperator.equals(other.getScanOperator())
			&& ManageCom.equals(other.getManageCom())
			&& InputState.equals(other.getInputState())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(InputStartDate).equals(other.getInputStartDate())
			&& InputStartTime.equals(other.getInputStartTime())
			&& fDate.getString(InputEndDate).equals(other.getInputEndDate())
			&& InputEndTime.equals(other.getInputEndTime())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Version.equals(other.getVersion())
			&& ScanNo.equals(other.getScanNo())
			&& PrintCode.equals(other.getPrintCode());
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
		if( strFieldName.equals("DocID") ) {
			return 0;
		}
		if( strFieldName.equals("DocCode") ) {
			return 1;
		}
		if( strFieldName.equals("BussType") ) {
			return 2;
		}
		if( strFieldName.equals("SubType") ) {
			return 3;
		}
		if( strFieldName.equals("NumPages") ) {
			return 4;
		}
		if( strFieldName.equals("DocFlag") ) {
			return 5;
		}
		if( strFieldName.equals("DocRemark") ) {
			return 6;
		}
		if( strFieldName.equals("ScanOperator") ) {
			return 7;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 8;
		}
		if( strFieldName.equals("InputState") ) {
			return 9;
		}
		if( strFieldName.equals("Operator") ) {
			return 10;
		}
		if( strFieldName.equals("InputStartDate") ) {
			return 11;
		}
		if( strFieldName.equals("InputStartTime") ) {
			return 12;
		}
		if( strFieldName.equals("InputEndDate") ) {
			return 13;
		}
		if( strFieldName.equals("InputEndTime") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 18;
		}
		if( strFieldName.equals("Version") ) {
			return 19;
		}
		if( strFieldName.equals("ScanNo") ) {
			return 20;
		}
		if( strFieldName.equals("PrintCode") ) {
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
				strFieldName = "DocID";
				break;
			case 1:
				strFieldName = "DocCode";
				break;
			case 2:
				strFieldName = "BussType";
				break;
			case 3:
				strFieldName = "SubType";
				break;
			case 4:
				strFieldName = "NumPages";
				break;
			case 5:
				strFieldName = "DocFlag";
				break;
			case 6:
				strFieldName = "DocRemark";
				break;
			case 7:
				strFieldName = "ScanOperator";
				break;
			case 8:
				strFieldName = "ManageCom";
				break;
			case 9:
				strFieldName = "InputState";
				break;
			case 10:
				strFieldName = "Operator";
				break;
			case 11:
				strFieldName = "InputStartDate";
				break;
			case 12:
				strFieldName = "InputStartTime";
				break;
			case 13:
				strFieldName = "InputEndDate";
				break;
			case 14:
				strFieldName = "InputEndTime";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyDate";
				break;
			case 18:
				strFieldName = "ModifyTime";
				break;
			case 19:
				strFieldName = "Version";
				break;
			case 20:
				strFieldName = "ScanNo";
				break;
			case 21:
				strFieldName = "PrintCode";
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
		if( strFieldName.equals("DocID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DocCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NumPages") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DocFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DocRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ScanOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InputStartTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InputEndTime") ) {
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
		if( strFieldName.equals("Version") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ScanNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintCode") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
