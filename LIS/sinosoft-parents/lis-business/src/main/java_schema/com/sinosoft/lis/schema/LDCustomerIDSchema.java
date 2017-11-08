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
import com.sinosoft.lis.db.LDCustomerIDDB;

/*
 * <p>ClassName: LDCustomerIDSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 客户管理
 */
public class LDCustomerIDSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDCustomerIDSchema.class);
	// @Field
	/** 投保人编码 */
	private String CustomerNo;
	/** 联系号 */
	private int ContactNo;
	/** 证件类型标识 */
	private String IDFlag;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 证件是否长期 */
	private String IDValFlag;
	/** 证件有效起期 */
	private Date IDInitiateDate;
	/** 证件有效止期 */
	private Date IDExpiryDate;
	/** 状态 */
	private String State;
	/** 备注 */
	private String Remark;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDCustomerIDSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "CustomerNo";
		pk[1] = "ContactNo";
		pk[2] = "IDType";

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
		LDCustomerIDSchema cloned = (LDCustomerIDSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>20)
			throw new IllegalArgumentException("投保人编码CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值20");
		CustomerNo = aCustomerNo;
	}
	public int getContactNo()
	{
		return ContactNo;
	}
	public void setContactNo(int aContactNo)
	{
		ContactNo = aContactNo;
	}
	public void setContactNo(String aContactNo)
	{
		if (aContactNo != null && !aContactNo.equals(""))
		{
			Integer tInteger = new Integer(aContactNo);
			int i = tInteger.intValue();
			ContactNo = i;
		}
	}

	/**
	* 00-团体主证件<p>
	* 01-团体附属证件
	*/
	public String getIDFlag()
	{
		return IDFlag;
	}
	public void setIDFlag(String aIDFlag)
	{
		if(aIDFlag!=null && aIDFlag.length()>2)
			throw new IllegalArgumentException("证件类型标识IDFlag值"+aIDFlag+"的长度"+aIDFlag.length()+"大于最大值2");
		IDFlag = aIDFlag;
	}
	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		if(aIDType!=null && aIDType.length()>2)
			throw new IllegalArgumentException("证件类型IDType值"+aIDType+"的长度"+aIDType.length()+"大于最大值2");
		IDType = aIDType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		if(aIDNo!=null && aIDNo.length()>30)
			throw new IllegalArgumentException("证件号码IDNo值"+aIDNo+"的长度"+aIDNo.length()+"大于最大值30");
		IDNo = aIDNo;
	}
	public String getIDValFlag()
	{
		return IDValFlag;
	}
	public void setIDValFlag(String aIDValFlag)
	{
		if(aIDValFlag!=null && aIDValFlag.length()>2)
			throw new IllegalArgumentException("证件是否长期IDValFlag值"+aIDValFlag+"的长度"+aIDValFlag.length()+"大于最大值2");
		IDValFlag = aIDValFlag;
	}
	public String getIDInitiateDate()
	{
		if( IDInitiateDate != null )
			return fDate.getString(IDInitiateDate);
		else
			return null;
	}
	public void setIDInitiateDate(Date aIDInitiateDate)
	{
		IDInitiateDate = aIDInitiateDate;
	}
	public void setIDInitiateDate(String aIDInitiateDate)
	{
		if (aIDInitiateDate != null && !aIDInitiateDate.equals("") )
		{
			IDInitiateDate = fDate.getDate( aIDInitiateDate );
		}
		else
			IDInitiateDate = null;
	}

	public String getIDExpiryDate()
	{
		if( IDExpiryDate != null )
			return fDate.getString(IDExpiryDate);
		else
			return null;
	}
	public void setIDExpiryDate(Date aIDExpiryDate)
	{
		IDExpiryDate = aIDExpiryDate;
	}
	public void setIDExpiryDate(String aIDExpiryDate)
	{
		if (aIDExpiryDate != null && !aIDExpiryDate.equals("") )
		{
			IDExpiryDate = fDate.getDate( aIDExpiryDate );
		}
		else
			IDExpiryDate = null;
	}

	/**
	* 00-正常
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>2)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值2");
		State = aState;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>1000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值1000");
		Remark = aRemark;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
		MakeTime = aMakeTime;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LDCustomerIDSchema 对象给 Schema 赋值
	* @param: aLDCustomerIDSchema LDCustomerIDSchema
	**/
	public void setSchema(LDCustomerIDSchema aLDCustomerIDSchema)
	{
		this.CustomerNo = aLDCustomerIDSchema.getCustomerNo();
		this.ContactNo = aLDCustomerIDSchema.getContactNo();
		this.IDFlag = aLDCustomerIDSchema.getIDFlag();
		this.IDType = aLDCustomerIDSchema.getIDType();
		this.IDNo = aLDCustomerIDSchema.getIDNo();
		this.IDValFlag = aLDCustomerIDSchema.getIDValFlag();
		this.IDInitiateDate = fDate.getDate( aLDCustomerIDSchema.getIDInitiateDate());
		this.IDExpiryDate = fDate.getDate( aLDCustomerIDSchema.getIDExpiryDate());
		this.State = aLDCustomerIDSchema.getState();
		this.Remark = aLDCustomerIDSchema.getRemark();
		this.ManageCom = aLDCustomerIDSchema.getManageCom();
		this.ComCode = aLDCustomerIDSchema.getComCode();
		this.MakeOperator = aLDCustomerIDSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLDCustomerIDSchema.getMakeDate());
		this.MakeTime = aLDCustomerIDSchema.getMakeTime();
		this.ModifyOperator = aLDCustomerIDSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLDCustomerIDSchema.getModifyDate());
		this.ModifyTime = aLDCustomerIDSchema.getModifyTime();
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
			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			this.ContactNo = rs.getInt("ContactNo");
			if( rs.getString("IDFlag") == null )
				this.IDFlag = null;
			else
				this.IDFlag = rs.getString("IDFlag").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("IDValFlag") == null )
				this.IDValFlag = null;
			else
				this.IDValFlag = rs.getString("IDValFlag").trim();

			this.IDInitiateDate = rs.getDate("IDInitiateDate");
			this.IDExpiryDate = rs.getDate("IDExpiryDate");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDCustomerID表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCustomerIDSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDCustomerIDSchema getSchema()
	{
		LDCustomerIDSchema aLDCustomerIDSchema = new LDCustomerIDSchema();
		aLDCustomerIDSchema.setSchema(this);
		return aLDCustomerIDSchema;
	}

	public LDCustomerIDDB getDB()
	{
		LDCustomerIDDB aDBOper = new LDCustomerIDDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCustomerID描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ContactNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDValFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IDInitiateDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IDExpiryDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCustomerID>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContactNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			IDFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			IDValFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			IDInitiateDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			IDExpiryDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCustomerIDSchema";
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
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("ContactNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContactNo));
		}
		if (FCode.equalsIgnoreCase("IDFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDFlag));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("IDValFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDValFlag));
		}
		if (FCode.equalsIgnoreCase("IDInitiateDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIDInitiateDate()));
		}
		if (FCode.equalsIgnoreCase("IDExpiryDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIDExpiryDate()));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 1:
				strFieldValue = String.valueOf(ContactNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(IDFlag);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(IDValFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIDInitiateDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIDExpiryDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 17:
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

		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("ContactNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ContactNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("IDFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDFlag = FValue.trim();
			}
			else
				IDFlag = null;
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDType = FValue.trim();
			}
			else
				IDType = null;
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDNo = FValue.trim();
			}
			else
				IDNo = null;
		}
		if (FCode.equalsIgnoreCase("IDValFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDValFlag = FValue.trim();
			}
			else
				IDValFlag = null;
		}
		if (FCode.equalsIgnoreCase("IDInitiateDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				IDInitiateDate = fDate.getDate( FValue );
			}
			else
				IDInitiateDate = null;
		}
		if (FCode.equalsIgnoreCase("IDExpiryDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				IDExpiryDate = fDate.getDate( FValue );
			}
			else
				IDExpiryDate = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		LDCustomerIDSchema other = (LDCustomerIDSchema)otherObject;
		return
			CustomerNo.equals(other.getCustomerNo())
			&& ContactNo == other.getContactNo()
			&& IDFlag.equals(other.getIDFlag())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& IDValFlag.equals(other.getIDValFlag())
			&& fDate.getString(IDInitiateDate).equals(other.getIDInitiateDate())
			&& fDate.getString(IDExpiryDate).equals(other.getIDExpiryDate())
			&& State.equals(other.getState())
			&& Remark.equals(other.getRemark())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
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
		if( strFieldName.equals("CustomerNo") ) {
			return 0;
		}
		if( strFieldName.equals("ContactNo") ) {
			return 1;
		}
		if( strFieldName.equals("IDFlag") ) {
			return 2;
		}
		if( strFieldName.equals("IDType") ) {
			return 3;
		}
		if( strFieldName.equals("IDNo") ) {
			return 4;
		}
		if( strFieldName.equals("IDValFlag") ) {
			return 5;
		}
		if( strFieldName.equals("IDInitiateDate") ) {
			return 6;
		}
		if( strFieldName.equals("IDExpiryDate") ) {
			return 7;
		}
		if( strFieldName.equals("State") ) {
			return 8;
		}
		if( strFieldName.equals("Remark") ) {
			return 9;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 10;
		}
		if( strFieldName.equals("ComCode") ) {
			return 11;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 12;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 13;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 17;
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
				strFieldName = "CustomerNo";
				break;
			case 1:
				strFieldName = "ContactNo";
				break;
			case 2:
				strFieldName = "IDFlag";
				break;
			case 3:
				strFieldName = "IDType";
				break;
			case 4:
				strFieldName = "IDNo";
				break;
			case 5:
				strFieldName = "IDValFlag";
				break;
			case 6:
				strFieldName = "IDInitiateDate";
				break;
			case 7:
				strFieldName = "IDExpiryDate";
				break;
			case 8:
				strFieldName = "State";
				break;
			case 9:
				strFieldName = "Remark";
				break;
			case 10:
				strFieldName = "ManageCom";
				break;
			case 11:
				strFieldName = "ComCode";
				break;
			case 12:
				strFieldName = "MakeOperator";
				break;
			case 13:
				strFieldName = "MakeDate";
				break;
			case 14:
				strFieldName = "MakeTime";
				break;
			case 15:
				strFieldName = "ModifyOperator";
				break;
			case 16:
				strFieldName = "ModifyDate";
				break;
			case 17:
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
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContactNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("IDFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDValFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDInitiateDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IDExpiryDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
