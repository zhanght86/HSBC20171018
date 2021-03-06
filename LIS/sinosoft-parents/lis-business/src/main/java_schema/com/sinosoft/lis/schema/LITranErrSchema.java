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
import com.sinosoft.lis.db.LITranErrDB;

/*
 * <p>ClassName: LITranErrSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口
 */
public class LITranErrSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LITranErrSchema.class);

	// @Field
	/** 批次号 */
	private String BatchNo;
	/** 单证号码 */
	private String BillId;
	/** 业务号1(收据号) */
	private String BussNo;
	/** 业务号2(保单号) */
	private String PolicyNo;
	/** 业务日期 */
	private Date BussDate;
	/** 数据表名 */
	private String TableName;
	/** 错误代码 */
	private String ErrFlag;
	/** 管理机构 */
	private String ManageCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 操作员 */
	private String Operator;
	/** 错误描述 */
	private String ErrDes;
	/** 流水号 */
	private String SerialNo;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LITranErrSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

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
		LITranErrSchema cloned = (LITranErrSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	/**
	* 001 保单信息查找失败<p>
	* 002 对应的财务表查询失败<p>
	* 003 险种表查询失败<p>
	* 004 金额为非正数<p>
	* 005 不支持的收付费类型<p>
	* 006 暂收费与暂收分类金额不等<p>
	* 007 管理机构为2位，不予提取<p>
	* 008 暂收的银行编码为空<p>
	* 009 银行信息描述问题<p>
	* 010 科目代码为空<p>
	* 011 科目已经提取过<p>
	* 012 接口表赋值错误<p>
	* 013 健康委托产品不进行提取<p>
	* 014 佣金记录为空<p>
	* 015 佣金审核状态有问题
	*/
	public String getBillId()
	{
		return BillId;
	}
	public void setBillId(String aBillId)
	{
		BillId = aBillId;
	}
	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		BussNo = aBussNo;
	}
	public String getPolicyNo()
	{
		return PolicyNo;
	}
	public void setPolicyNo(String aPolicyNo)
	{
		PolicyNo = aPolicyNo;
	}
	public String getBussDate()
	{
		if( BussDate != null )
			return fDate.getString(BussDate);
		else
			return null;
	}
	public void setBussDate(Date aBussDate)
	{
		BussDate = aBussDate;
	}
	public void setBussDate(String aBussDate)
	{
		if (aBussDate != null && !aBussDate.equals("") )
		{
			BussDate = fDate.getDate( aBussDate );
		}
		else
			BussDate = null;
	}

	public String getTableName()
	{
		return TableName;
	}
	public void setTableName(String aTableName)
	{
		TableName = aTableName;
	}
	/**
	* 在Bill_code表中定义
	*/
	public String getErrFlag()
	{
		return ErrFlag;
	}
	public void setErrFlag(String aErrFlag)
	{
		ErrFlag = aErrFlag;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getErrDes()
	{
		return ErrDes;
	}
	public void setErrDes(String aErrDes)
	{
		ErrDes = aErrDes;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}

	/**
	* 使用另外一个 LITranErrSchema 对象给 Schema 赋值
	* @param: aLITranErrSchema LITranErrSchema
	**/
	public void setSchema(LITranErrSchema aLITranErrSchema)
	{
		this.BatchNo = aLITranErrSchema.getBatchNo();
		this.BillId = aLITranErrSchema.getBillId();
		this.BussNo = aLITranErrSchema.getBussNo();
		this.PolicyNo = aLITranErrSchema.getPolicyNo();
		this.BussDate = fDate.getDate( aLITranErrSchema.getBussDate());
		this.TableName = aLITranErrSchema.getTableName();
		this.ErrFlag = aLITranErrSchema.getErrFlag();
		this.ManageCom = aLITranErrSchema.getManageCom();
		this.MakeDate = fDate.getDate( aLITranErrSchema.getMakeDate());
		this.MakeTime = aLITranErrSchema.getMakeTime();
		this.Operator = aLITranErrSchema.getOperator();
		this.ErrDes = aLITranErrSchema.getErrDes();
		this.SerialNo = aLITranErrSchema.getSerialNo();
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
			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("BillId") == null )
				this.BillId = null;
			else
				this.BillId = rs.getString("BillId").trim();

			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			if( rs.getString("PolicyNo") == null )
				this.PolicyNo = null;
			else
				this.PolicyNo = rs.getString("PolicyNo").trim();

			this.BussDate = rs.getDate("BussDate");
			if( rs.getString("TableName") == null )
				this.TableName = null;
			else
				this.TableName = rs.getString("TableName").trim();

			if( rs.getString("ErrFlag") == null )
				this.ErrFlag = null;
			else
				this.ErrFlag = rs.getString("ErrFlag").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("ErrDes") == null )
				this.ErrDes = null;
			else
				this.ErrDes = rs.getString("ErrDes").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LITranErr表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LITranErrSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LITranErrSchema getSchema()
	{
		LITranErrSchema aLITranErrSchema = new LITranErrSchema();
		aLITranErrSchema.setSchema(this);
		return aLITranErrSchema;
	}

	public LITranErrDB getDB()
	{
		LITranErrDB aDBOper = new LITranErrDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLITranErr描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BillId)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolicyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BussDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrDes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLITranErr>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BillId = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PolicyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BussDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			TableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ErrFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ErrDes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LITranErrSchema";
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
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("BillId"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillId));
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("PolicyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyNo));
		}
		if (FCode.equalsIgnoreCase("BussDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBussDate()));
		}
		if (FCode.equalsIgnoreCase("TableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TableName));
		}
		if (FCode.equalsIgnoreCase("ErrFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrFlag));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("ErrDes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrDes));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
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
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BillId);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PolicyNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBussDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(TableName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ErrFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ErrDes);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
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

		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("BillId"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BillId = FValue.trim();
			}
			else
				BillId = null;
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
		if (FCode.equalsIgnoreCase("PolicyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyNo = FValue.trim();
			}
			else
				PolicyNo = null;
		}
		if (FCode.equalsIgnoreCase("BussDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BussDate = fDate.getDate( FValue );
			}
			else
				BussDate = null;
		}
		if (FCode.equalsIgnoreCase("TableName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TableName = FValue.trim();
			}
			else
				TableName = null;
		}
		if (FCode.equalsIgnoreCase("ErrFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrFlag = FValue.trim();
			}
			else
				ErrFlag = null;
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		if (FCode.equalsIgnoreCase("ErrDes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrDes = FValue.trim();
			}
			else
				ErrDes = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LITranErrSchema other = (LITranErrSchema)otherObject;
		return
			BatchNo.equals(other.getBatchNo())
			&& BillId.equals(other.getBillId())
			&& BussNo.equals(other.getBussNo())
			&& PolicyNo.equals(other.getPolicyNo())
			&& fDate.getString(BussDate).equals(other.getBussDate())
			&& TableName.equals(other.getTableName())
			&& ErrFlag.equals(other.getErrFlag())
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& Operator.equals(other.getOperator())
			&& ErrDes.equals(other.getErrDes())
			&& SerialNo.equals(other.getSerialNo());
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
		if( strFieldName.equals("BatchNo") ) {
			return 0;
		}
		if( strFieldName.equals("BillId") ) {
			return 1;
		}
		if( strFieldName.equals("BussNo") ) {
			return 2;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return 3;
		}
		if( strFieldName.equals("BussDate") ) {
			return 4;
		}
		if( strFieldName.equals("TableName") ) {
			return 5;
		}
		if( strFieldName.equals("ErrFlag") ) {
			return 6;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 7;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 9;
		}
		if( strFieldName.equals("Operator") ) {
			return 10;
		}
		if( strFieldName.equals("ErrDes") ) {
			return 11;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 12;
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
				strFieldName = "BatchNo";
				break;
			case 1:
				strFieldName = "BillId";
				break;
			case 2:
				strFieldName = "BussNo";
				break;
			case 3:
				strFieldName = "PolicyNo";
				break;
			case 4:
				strFieldName = "BussDate";
				break;
			case 5:
				strFieldName = "TableName";
				break;
			case 6:
				strFieldName = "ErrFlag";
				break;
			case 7:
				strFieldName = "ManageCom";
				break;
			case 8:
				strFieldName = "MakeDate";
				break;
			case 9:
				strFieldName = "MakeTime";
				break;
			case 10:
				strFieldName = "Operator";
				break;
			case 11:
				strFieldName = "ErrDes";
				break;
			case 12:
				strFieldName = "SerialNo";
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
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillId") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrDes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
