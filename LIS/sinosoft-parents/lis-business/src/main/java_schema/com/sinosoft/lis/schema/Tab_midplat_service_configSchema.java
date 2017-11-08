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
import com.sinosoft.lis.db.Tab_midplat_service_configDB;

/*
 * <p>ClassName: Tab_midplat_service_configSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 银保通
 */
public class Tab_midplat_service_configSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(Tab_midplat_service_configSchema.class);

	// @Field
	/** 银行编码 */
	private String BankCode;
	/** 渠道类型 */
	private String ISSUE_WAY;
	/** 渠道服务代码（银行方的交易代码） */
	private String service_code;
	/** 服务id */
	private String service_id;
	/** 服务程序 */
	private String service_provider;
	/** 备用字段 */
	private String bak1;
	/** 备用字段2 */
	private String bak2;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后修改日期 */
	private Date ModifyDate;
	/** 最后修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public Tab_midplat_service_configSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "BankCode";
		pk[1] = "ISSUE_WAY";
		pk[2] = "service_code";

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
		Tab_midplat_service_configSchema cloned = (Tab_midplat_service_configSchema)super.clone();
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
	* 011    -- 工行<p>
	* 1200  -- 邮储总局<p>
	* 1201  -- 河南邮政<p>
	* 0322 -- 建分行（江苏、安徽等）<p>
	* 03 -- 建总行（河北、湖南）
	*/
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	/**
	* 渠道类型
	*/
	public String getISSUE_WAY()
	{
		return ISSUE_WAY;
	}
	public void setISSUE_WAY(String aISSUE_WAY)
	{
		ISSUE_WAY = aISSUE_WAY;
	}
	/**
	* 渠道服务代码（银行方的交易代码）<p>
	* 00 --- 确认取消交易<p>
	* 01 --- 核保交易<p>
	* 02 --- 重打交易<p>
	* 04 --- 撤单交易<p>
	* 16 --- 汇总对账<p>
	* 17 --- 明细对账
	*/
	public String getservice_code()
	{
		return service_code;
	}
	public void setservice_code(String aservice_code)
	{
		service_code = aservice_code;
	}
	/**
	* 暂不使用，现用来存放银行端的操作代码<p>
	* 工行：<p>
	* 1013 -- 新单承保<p>
	* 1015 -- 当日撤单<p>
	* 1011 -- 单证重打<p>
	* 邮储总局：<p>
	* 1021 -- 新保核保<p>
	* 1022 -- 新保缴费<p>
	* 1013 -- 单证重打<p>
	* 1002 -- 当日冲正<p>
	* 1003 -- 日结对帐
	*/
	public String getservice_id()
	{
		return service_id;
	}
	public void setservice_id(String aservice_id)
	{
		service_id = aservice_id;
	}
	/**
	* 服务程序
	*/
	public String getservice_provider()
	{
		return service_provider;
	}
	public void setservice_provider(String aservice_provider)
	{
		service_provider = aservice_provider;
	}
	public String getbak1()
	{
		return bak1;
	}
	public void setbak1(String abak1)
	{
		bak1 = abak1;
	}
	public String getbak2()
	{
		return bak2;
	}
	public void setbak2(String abak2)
	{
		bak2 = abak2;
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
	* 使用另外一个 Tab_midplat_service_configSchema 对象给 Schema 赋值
	* @param: aTab_midplat_service_configSchema Tab_midplat_service_configSchema
	**/
	public void setSchema(Tab_midplat_service_configSchema aTab_midplat_service_configSchema)
	{
		this.BankCode = aTab_midplat_service_configSchema.getBankCode();
		this.ISSUE_WAY = aTab_midplat_service_configSchema.getISSUE_WAY();
		this.service_code = aTab_midplat_service_configSchema.getservice_code();
		this.service_id = aTab_midplat_service_configSchema.getservice_id();
		this.service_provider = aTab_midplat_service_configSchema.getservice_provider();
		this.bak1 = aTab_midplat_service_configSchema.getbak1();
		this.bak2 = aTab_midplat_service_configSchema.getbak2();
		this.MakeDate = fDate.getDate( aTab_midplat_service_configSchema.getMakeDate());
		this.MakeTime = aTab_midplat_service_configSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aTab_midplat_service_configSchema.getModifyDate());
		this.ModifyTime = aTab_midplat_service_configSchema.getModifyTime();
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

			if( rs.getString("ISSUE_WAY") == null )
				this.ISSUE_WAY = null;
			else
				this.ISSUE_WAY = rs.getString("ISSUE_WAY").trim();

			if( rs.getString("service_code") == null )
				this.service_code = null;
			else
				this.service_code = rs.getString("service_code").trim();

			if( rs.getString("service_id") == null )
				this.service_id = null;
			else
				this.service_id = rs.getString("service_id").trim();

			if( rs.getString("service_provider") == null )
				this.service_provider = null;
			else
				this.service_provider = rs.getString("service_provider").trim();

			if( rs.getString("bak1") == null )
				this.bak1 = null;
			else
				this.bak1 = rs.getString("bak1").trim();

			if( rs.getString("bak2") == null )
				this.bak2 = null;
			else
				this.bak2 = rs.getString("bak2").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的Tab_midplat_service_config表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "Tab_midplat_service_configSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public Tab_midplat_service_configSchema getSchema()
	{
		Tab_midplat_service_configSchema aTab_midplat_service_configSchema = new Tab_midplat_service_configSchema();
		aTab_midplat_service_configSchema.setSchema(this);
		return aTab_midplat_service_configSchema;
	}

	public Tab_midplat_service_configDB getDB()
	{
		Tab_midplat_service_configDB aDBOper = new Tab_midplat_service_configDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpTab_midplat_service_config描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ISSUE_WAY)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(service_code)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(service_id)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(service_provider)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(bak1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(bak2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpTab_midplat_service_config>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ISSUE_WAY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			service_code = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			service_id = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			service_provider = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "Tab_midplat_service_configSchema";
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
		if (FCode.equalsIgnoreCase("ISSUE_WAY"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ISSUE_WAY));
		}
		if (FCode.equalsIgnoreCase("service_code"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(service_code));
		}
		if (FCode.equalsIgnoreCase("service_id"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(service_id));
		}
		if (FCode.equalsIgnoreCase("service_provider"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(service_provider));
		}
		if (FCode.equalsIgnoreCase("bak1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(bak1));
		}
		if (FCode.equalsIgnoreCase("bak2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(bak2));
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
				strFieldValue = StrTool.GBKToUnicode(ISSUE_WAY);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(service_code);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(service_id);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(service_provider);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(bak1);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(bak2);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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
		if (FCode.equalsIgnoreCase("ISSUE_WAY"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ISSUE_WAY = FValue.trim();
			}
			else
				ISSUE_WAY = null;
		}
		if (FCode.equalsIgnoreCase("service_code"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				service_code = FValue.trim();
			}
			else
				service_code = null;
		}
		if (FCode.equalsIgnoreCase("service_id"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				service_id = FValue.trim();
			}
			else
				service_id = null;
		}
		if (FCode.equalsIgnoreCase("service_provider"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				service_provider = FValue.trim();
			}
			else
				service_provider = null;
		}
		if (FCode.equalsIgnoreCase("bak1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				bak1 = FValue.trim();
			}
			else
				bak1 = null;
		}
		if (FCode.equalsIgnoreCase("bak2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				bak2 = FValue.trim();
			}
			else
				bak2 = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		Tab_midplat_service_configSchema other = (Tab_midplat_service_configSchema)otherObject;
		return
			BankCode.equals(other.getBankCode())
			&& ISSUE_WAY.equals(other.getISSUE_WAY())
			&& service_code.equals(other.getservice_code())
			&& service_id.equals(other.getservice_id())
			&& service_provider.equals(other.getservice_provider())
			&& bak1.equals(other.getbak1())
			&& bak2.equals(other.getbak2())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("ISSUE_WAY") ) {
			return 1;
		}
		if( strFieldName.equals("service_code") ) {
			return 2;
		}
		if( strFieldName.equals("service_id") ) {
			return 3;
		}
		if( strFieldName.equals("service_provider") ) {
			return 4;
		}
		if( strFieldName.equals("bak1") ) {
			return 5;
		}
		if( strFieldName.equals("bak2") ) {
			return 6;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 7;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 8;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "BankCode";
				break;
			case 1:
				strFieldName = "ISSUE_WAY";
				break;
			case 2:
				strFieldName = "service_code";
				break;
			case 3:
				strFieldName = "service_id";
				break;
			case 4:
				strFieldName = "service_provider";
				break;
			case 5:
				strFieldName = "bak1";
				break;
			case 6:
				strFieldName = "bak2";
				break;
			case 7:
				strFieldName = "MakeDate";
				break;
			case 8:
				strFieldName = "MakeTime";
				break;
			case 9:
				strFieldName = "ModifyDate";
				break;
			case 10:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("ISSUE_WAY") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("service_code") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("service_id") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("service_provider") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("bak1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("bak2") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
