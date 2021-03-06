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
import com.sinosoft.lis.db.LOReportManagerDB;

/*
 * <p>ClassName: LOReportManagerSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 后台任务服务
 */
public class LOReportManagerSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOReportManagerSchema.class);

	// @Field
	/** 印刷流水号 */
	private String PrtSeq;
	/** 报表类型 */
	private String ReportType;
	/** 报表编码 */
	private String ReportCode;
	/** 打印标志 */
	private String PrtFlag;
	/** 打印次数 */
	private int PrtTimes;
	/** 统计条件 */
	private String Filters;
	/** 报表生成管理机构 */
	private String CreateManageCom;
	/** 报表数据管理机构 */
	private String ManageCom;
	/** 报表模板名 */
	private String Template;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 报表内容 */
	private InputStream ReportInfo;

	public static final int FIELDNUM = 15;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOReportManagerSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "PrtSeq";

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
		LOReportManagerSchema cloned = (LOReportManagerSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPrtSeq()
	{
		return PrtSeq;
	}
	public void setPrtSeq(String aPrtSeq)
	{
		PrtSeq = aPrtSeq;
	}
	public String getReportType()
	{
		return ReportType;
	}
	public void setReportType(String aReportType)
	{
		ReportType = aReportType;
	}
	public String getReportCode()
	{
		return ReportCode;
	}
	public void setReportCode(String aReportCode)
	{
		ReportCode = aReportCode;
	}
	/**
	* Y - 已打印<p>
	* N - 未打印
	*/
	public String getPrtFlag()
	{
		return PrtFlag;
	}
	public void setPrtFlag(String aPrtFlag)
	{
		PrtFlag = aPrtFlag;
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

	/**
	* 相同统计条件的报表不允许再次打印，直接用之前的数据即可
	*/
	public String getFilters()
	{
		return Filters;
	}
	public void setFilters(String aFilters)
	{
		Filters = aFilters;
	}
	public String getCreateManageCom()
	{
		return CreateManageCom;
	}
	public void setCreateManageCom(String aCreateManageCom)
	{
		CreateManageCom = aCreateManageCom;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getTemplate()
	{
		return Template;
	}
	public void setTemplate(String aTemplate)
	{
		Template = aTemplate;
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
	public InputStream getReportInfo()
	{
		return ReportInfo;
	}
	public void setReportInfo(InputStream aReportInfo)
	{
		ReportInfo = aReportInfo;
	}
	public void setReportInfo(String aReportInfo)
	{
	}


	/**
	* 使用另外一个 LOReportManagerSchema 对象给 Schema 赋值
	* @param: aLOReportManagerSchema LOReportManagerSchema
	**/
	public void setSchema(LOReportManagerSchema aLOReportManagerSchema)
	{
		this.PrtSeq = aLOReportManagerSchema.getPrtSeq();
		this.ReportType = aLOReportManagerSchema.getReportType();
		this.ReportCode = aLOReportManagerSchema.getReportCode();
		this.PrtFlag = aLOReportManagerSchema.getPrtFlag();
		this.PrtTimes = aLOReportManagerSchema.getPrtTimes();
		this.Filters = aLOReportManagerSchema.getFilters();
		this.CreateManageCom = aLOReportManagerSchema.getCreateManageCom();
		this.ManageCom = aLOReportManagerSchema.getManageCom();
		this.Template = aLOReportManagerSchema.getTemplate();
		this.Operator = aLOReportManagerSchema.getOperator();
		this.MakeDate = fDate.getDate( aLOReportManagerSchema.getMakeDate());
		this.MakeTime = aLOReportManagerSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLOReportManagerSchema.getModifyDate());
		this.ModifyTime = aLOReportManagerSchema.getModifyTime();
		this.ReportInfo = aLOReportManagerSchema.getReportInfo();
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
			if( rs.getString("PrtSeq") == null )
				this.PrtSeq = null;
			else
				this.PrtSeq = rs.getString("PrtSeq").trim();

			if( rs.getString("ReportType") == null )
				this.ReportType = null;
			else
				this.ReportType = rs.getString("ReportType").trim();

			if( rs.getString("ReportCode") == null )
				this.ReportCode = null;
			else
				this.ReportCode = rs.getString("ReportCode").trim();

			if( rs.getString("PrtFlag") == null )
				this.PrtFlag = null;
			else
				this.PrtFlag = rs.getString("PrtFlag").trim();

			this.PrtTimes = rs.getInt("PrtTimes");
			if( rs.getString("Filters") == null )
				this.Filters = null;
			else
				this.Filters = rs.getString("Filters").trim();

			if( rs.getString("CreateManageCom") == null )
				this.CreateManageCom = null;
			else
				this.CreateManageCom = rs.getString("CreateManageCom").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("Template") == null )
				this.Template = null;
			else
				this.Template = rs.getString("Template").trim();

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

			this.ReportInfo = rs.getBinaryStream("ReportInfo");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOReportManager表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOReportManagerSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOReportManagerSchema getSchema()
	{
		LOReportManagerSchema aLOReportManagerSchema = new LOReportManagerSchema();
		aLOReportManagerSchema.setSchema(this);
		return aLOReportManagerSchema;
	}

	public LOReportManagerDB getDB()
	{
		LOReportManagerDB aDBOper = new LOReportManagerDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOReportManager描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PrtSeq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReportType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReportCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrtTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Filters)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CreateManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Template)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( 1 );
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOReportManager>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PrtSeq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ReportType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ReportCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PrtFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PrtTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			Filters = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CreateManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Template = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOReportManagerSchema";
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
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtSeq));
		}
		if (FCode.equalsIgnoreCase("ReportType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReportType));
		}
		if (FCode.equalsIgnoreCase("ReportCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReportCode));
		}
		if (FCode.equalsIgnoreCase("PrtFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtFlag));
		}
		if (FCode.equalsIgnoreCase("PrtTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtTimes));
		}
		if (FCode.equalsIgnoreCase("Filters"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Filters));
		}
		if (FCode.equalsIgnoreCase("CreateManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CreateManageCom));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("Template"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Template));
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
		if (FCode.equalsIgnoreCase("ReportInfo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReportInfo));
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
				strFieldValue = StrTool.GBKToUnicode(PrtSeq);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ReportType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ReportCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PrtFlag);
				break;
			case 4:
				strFieldValue = String.valueOf(PrtTimes);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Filters);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CreateManageCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Template);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 14:
				strFieldValue = String.valueOf(ReportInfo);
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

		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtSeq = FValue.trim();
			}
			else
				PrtSeq = null;
		}
		if (FCode.equalsIgnoreCase("ReportType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReportType = FValue.trim();
			}
			else
				ReportType = null;
		}
		if (FCode.equalsIgnoreCase("ReportCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReportCode = FValue.trim();
			}
			else
				ReportCode = null;
		}
		if (FCode.equalsIgnoreCase("PrtFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtFlag = FValue.trim();
			}
			else
				PrtFlag = null;
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
		if (FCode.equalsIgnoreCase("Filters"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Filters = FValue.trim();
			}
			else
				Filters = null;
		}
		if (FCode.equalsIgnoreCase("CreateManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CreateManageCom = FValue.trim();
			}
			else
				CreateManageCom = null;
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
		if (FCode.equalsIgnoreCase("Template"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Template = FValue.trim();
			}
			else
				Template = null;
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
		if (FCode.equalsIgnoreCase("ReportInfo"))
		{
				ReportInfo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LOReportManagerSchema other = (LOReportManagerSchema)otherObject;
		return
			PrtSeq.equals(other.getPrtSeq())
			&& ReportType.equals(other.getReportType())
			&& ReportCode.equals(other.getReportCode())
			&& PrtFlag.equals(other.getPrtFlag())
			&& PrtTimes == other.getPrtTimes()
			&& Filters.equals(other.getFilters())
			&& CreateManageCom.equals(other.getCreateManageCom())
			&& ManageCom.equals(other.getManageCom())
			&& Template.equals(other.getTemplate())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			;
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
		if( strFieldName.equals("PrtSeq") ) {
			return 0;
		}
		if( strFieldName.equals("ReportType") ) {
			return 1;
		}
		if( strFieldName.equals("ReportCode") ) {
			return 2;
		}
		if( strFieldName.equals("PrtFlag") ) {
			return 3;
		}
		if( strFieldName.equals("PrtTimes") ) {
			return 4;
		}
		if( strFieldName.equals("Filters") ) {
			return 5;
		}
		if( strFieldName.equals("CreateManageCom") ) {
			return 6;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 7;
		}
		if( strFieldName.equals("Template") ) {
			return 8;
		}
		if( strFieldName.equals("Operator") ) {
			return 9;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 10;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 13;
		}
		if( strFieldName.equals("ReportInfo") ) {
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
				strFieldName = "PrtSeq";
				break;
			case 1:
				strFieldName = "ReportType";
				break;
			case 2:
				strFieldName = "ReportCode";
				break;
			case 3:
				strFieldName = "PrtFlag";
				break;
			case 4:
				strFieldName = "PrtTimes";
				break;
			case 5:
				strFieldName = "Filters";
				break;
			case 6:
				strFieldName = "CreateManageCom";
				break;
			case 7:
				strFieldName = "ManageCom";
				break;
			case 8:
				strFieldName = "Template";
				break;
			case 9:
				strFieldName = "Operator";
				break;
			case 10:
				strFieldName = "MakeDate";
				break;
			case 11:
				strFieldName = "MakeTime";
				break;
			case 12:
				strFieldName = "ModifyDate";
				break;
			case 13:
				strFieldName = "ModifyTime";
				break;
			case 14:
				strFieldName = "ReportInfo";
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
		if( strFieldName.equals("PrtSeq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReportType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReportCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Filters") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CreateManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Template") ) {
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
		if( strFieldName.equals("ReportInfo") ) {
			return Schema.TYPE_BLOB;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_BLOB;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
