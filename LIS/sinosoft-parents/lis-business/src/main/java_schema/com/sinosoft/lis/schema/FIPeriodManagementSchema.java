/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.lis.db.FIPeriodManagementDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIPeriodManagementSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIPeriodManagementSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIPeriodManagementSchema.class);

	// @Field
	/** 年度 */
	private String year;
	/** 月度 */
	private String month;
	/** 月度起期 */
	private Date startdate;
	/** 月度止期 */
	private Date enddate;
	/** 状态标志 */
	private String state;
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

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIPeriodManagementSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "year";
		pk[1] = "month";

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
                FIPeriodManagementSchema cloned = (FIPeriodManagementSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getyear()
	{
		return year;
	}
	public void setyear(String ayear)
	{
		year = ayear;
	}
	public String getmonth()
	{
		return month;
	}
	public void setmonth(String amonth)
	{
		month = amonth;
	}
	public String getstartdate()
	{
		if( startdate != null )
			return fDate.getString(startdate);
		else
			return null;
	}
	public void setstartdate(Date astartdate)
	{
		startdate = astartdate;
	}
	public void setstartdate(String astartdate)
	{
		if (astartdate != null && !astartdate.equals("") )
		{
			startdate = fDate.getDate( astartdate );
		}
		else
			startdate = null;
	}

	public String getenddate()
	{
		if( enddate != null )
			return fDate.getString(enddate);
		else
			return null;
	}
	public void setenddate(Date aenddate)
	{
		enddate = aenddate;
	}
	public void setenddate(String aenddate)
	{
		if (aenddate != null && !aenddate.equals("") )
		{
			enddate = fDate.getDate( aenddate );
		}
		else
			enddate = null;
	}

	public String getstate()
	{
		return state;
	}
	public void setstate(String astate)
	{
		state = astate;
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
	* 使用另外一个 FIPeriodManagementSchema 对象给 Schema 赋值
	* @param: aFIPeriodManagementSchema FIPeriodManagementSchema
	**/
	public void setSchema(FIPeriodManagementSchema aFIPeriodManagementSchema)
	{
		this.year = aFIPeriodManagementSchema.getyear();
		this.month = aFIPeriodManagementSchema.getmonth();
		this.startdate = fDate.getDate( aFIPeriodManagementSchema.getstartdate());
		this.enddate = fDate.getDate( aFIPeriodManagementSchema.getenddate());
		this.state = aFIPeriodManagementSchema.getstate();
		this.ManageCom = aFIPeriodManagementSchema.getManageCom();
		this.Operator = aFIPeriodManagementSchema.getOperator();
		this.MakeDate = fDate.getDate( aFIPeriodManagementSchema.getMakeDate());
		this.MakeTime = aFIPeriodManagementSchema.getMakeTime();
		this.ModifyTime = aFIPeriodManagementSchema.getModifyTime();
		this.ModifyDate = fDate.getDate( aFIPeriodManagementSchema.getModifyDate());
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
			if( rs.getString("year") == null )
				this.year = null;
			else
				this.year = rs.getString("year").trim();

			if( rs.getString("month") == null )
				this.month = null;
			else
				this.month = rs.getString("month").trim();

			this.startdate = rs.getDate("startdate");
			this.enddate = rs.getDate("enddate");
			if( rs.getString("state") == null )
				this.state = null;
			else
				this.state = rs.getString("state").trim();

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
			logger.debug("数据库中的FIPeriodManagement表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIPeriodManagementSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIPeriodManagementSchema getSchema()
	{
		FIPeriodManagementSchema aFIPeriodManagementSchema = new FIPeriodManagementSchema();
		aFIPeriodManagementSchema.setSchema(this);
		return aFIPeriodManagementSchema;
	}

	public FIPeriodManagementDB getDB()
	{
		FIPeriodManagementDB aDBOper = new FIPeriodManagementDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIPeriodManagement描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(year)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(month)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( startdate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( enddate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(state)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIPeriodManagement>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			year = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			month = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			startdate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			enddate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			state = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIPeriodManagementSchema";
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
		if (FCode.equalsIgnoreCase("year"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(year));
		}
		if (FCode.equalsIgnoreCase("month"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(month));
		}
		if (FCode.equalsIgnoreCase("startdate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getstartdate()));
		}
		if (FCode.equalsIgnoreCase("enddate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getenddate()));
		}
		if (FCode.equalsIgnoreCase("state"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(state));
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
				strFieldValue = StrTool.GBKToUnicode(year);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(month);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getstartdate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getenddate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(state);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 10:
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

		if (FCode.equalsIgnoreCase("year"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				year = FValue.trim();
			}
			else
				year = null;
		}
		if (FCode.equalsIgnoreCase("month"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				month = FValue.trim();
			}
			else
				month = null;
		}
		if (FCode.equalsIgnoreCase("startdate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				startdate = fDate.getDate( FValue );
			}
			else
				startdate = null;
		}
		if (FCode.equalsIgnoreCase("enddate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				enddate = fDate.getDate( FValue );
			}
			else
				enddate = null;
		}
		if (FCode.equalsIgnoreCase("state"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				state = FValue.trim();
			}
			else
				state = null;
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
		FIPeriodManagementSchema other = (FIPeriodManagementSchema)otherObject;
		return
			year.equals(other.getyear())
			&& month.equals(other.getmonth())
			&& fDate.getString(startdate).equals(other.getstartdate())
			&& fDate.getString(enddate).equals(other.getenddate())
			&& state.equals(other.getstate())
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
		if( strFieldName.equals("year") ) {
			return 0;
		}
		if( strFieldName.equals("month") ) {
			return 1;
		}
		if( strFieldName.equals("startdate") ) {
			return 2;
		}
		if( strFieldName.equals("enddate") ) {
			return 3;
		}
		if( strFieldName.equals("state") ) {
			return 4;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 5;
		}
		if( strFieldName.equals("Operator") ) {
			return 6;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 7;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 8;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 10;
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
				strFieldName = "year";
				break;
			case 1:
				strFieldName = "month";
				break;
			case 2:
				strFieldName = "startdate";
				break;
			case 3:
				strFieldName = "enddate";
				break;
			case 4:
				strFieldName = "state";
				break;
			case 5:
				strFieldName = "ManageCom";
				break;
			case 6:
				strFieldName = "Operator";
				break;
			case 7:
				strFieldName = "MakeDate";
				break;
			case 8:
				strFieldName = "MakeTime";
				break;
			case 9:
				strFieldName = "ModifyTime";
				break;
			case 10:
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
		if( strFieldName.equals("year") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("month") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("startdate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("enddate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("state") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 3:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
