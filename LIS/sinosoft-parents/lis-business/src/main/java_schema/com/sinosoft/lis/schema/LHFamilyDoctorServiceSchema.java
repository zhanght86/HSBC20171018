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
import com.sinosoft.lis.db.LHFamilyDoctorServiceDB;

/*
 * <p>ClassName: LHFamilyDoctorServiceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LHFamilyDoctorServiceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LHFamilyDoctorServiceSchema.class);

	// @Field
	/** 家庭医生服务登记号 */
	private String FDSNo;
	/** 客户号 */
	private String CustomerNo;
	/** 客户姓名 */
	private String Name;
	/** 家庭医生代码 */
	private String FamilyDoctorNo;
	/** 家庭医生姓名 */
	private String FamilyDoctorName;
	/** 服务项目 */
	private String FDSCode;
	/** 服务主题 */
	private String FDSSubject;
	/** 服务内容 */
	private String FDSContent;
	/** 状态 */
	private String State;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 服务日期 */
	private Date FDSDate;

	public static final int FIELDNUM = 15;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LHFamilyDoctorServiceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "FDSNo";

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
		LHFamilyDoctorServiceSchema cloned = (LHFamilyDoctorServiceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getFDSNo()
	{
		return FDSNo;
	}
	public void setFDSNo(String aFDSNo)
	{
		FDSNo = aFDSNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		Name = aName;
	}
	/**
	* 家庭医师取自LDCode中的家庭医生代码
	*/
	public String getFamilyDoctorNo()
	{
		return FamilyDoctorNo;
	}
	public void setFamilyDoctorNo(String aFamilyDoctorNo)
	{
		FamilyDoctorNo = aFamilyDoctorNo;
	}
	public String getFamilyDoctorName()
	{
		return FamilyDoctorName;
	}
	public void setFamilyDoctorName(String aFamilyDoctorName)
	{
		FamilyDoctorName = aFamilyDoctorName;
	}
	/**
	* 电子邮件<p>
	* 组织体检<p>
	* 组织会谈等
	*/
	public String getFDSCode()
	{
		return FDSCode;
	}
	public void setFDSCode(String aFDSCode)
	{
		FDSCode = aFDSCode;
	}
	public String getFDSSubject()
	{
		return FDSSubject;
	}
	public void setFDSSubject(String aFDSSubject)
	{
		FDSSubject = aFDSSubject;
	}
	public String getFDSContent()
	{
		return FDSContent;
	}
	public void setFDSContent(String aFDSContent)
	{
		FDSContent = aFDSContent;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
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
	public String getFDSDate()
	{
		if( FDSDate != null )
			return fDate.getString(FDSDate);
		else
			return null;
	}
	public void setFDSDate(Date aFDSDate)
	{
		FDSDate = aFDSDate;
	}
	public void setFDSDate(String aFDSDate)
	{
		if (aFDSDate != null && !aFDSDate.equals("") )
		{
			FDSDate = fDate.getDate( aFDSDate );
		}
		else
			FDSDate = null;
	}


	/**
	* 使用另外一个 LHFamilyDoctorServiceSchema 对象给 Schema 赋值
	* @param: aLHFamilyDoctorServiceSchema LHFamilyDoctorServiceSchema
	**/
	public void setSchema(LHFamilyDoctorServiceSchema aLHFamilyDoctorServiceSchema)
	{
		this.FDSNo = aLHFamilyDoctorServiceSchema.getFDSNo();
		this.CustomerNo = aLHFamilyDoctorServiceSchema.getCustomerNo();
		this.Name = aLHFamilyDoctorServiceSchema.getName();
		this.FamilyDoctorNo = aLHFamilyDoctorServiceSchema.getFamilyDoctorNo();
		this.FamilyDoctorName = aLHFamilyDoctorServiceSchema.getFamilyDoctorName();
		this.FDSCode = aLHFamilyDoctorServiceSchema.getFDSCode();
		this.FDSSubject = aLHFamilyDoctorServiceSchema.getFDSSubject();
		this.FDSContent = aLHFamilyDoctorServiceSchema.getFDSContent();
		this.State = aLHFamilyDoctorServiceSchema.getState();
		this.Operator = aLHFamilyDoctorServiceSchema.getOperator();
		this.MakeDate = fDate.getDate( aLHFamilyDoctorServiceSchema.getMakeDate());
		this.MakeTime = aLHFamilyDoctorServiceSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLHFamilyDoctorServiceSchema.getModifyDate());
		this.ModifyTime = aLHFamilyDoctorServiceSchema.getModifyTime();
		this.FDSDate = fDate.getDate( aLHFamilyDoctorServiceSchema.getFDSDate());
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
			if( rs.getString("FDSNo") == null )
				this.FDSNo = null;
			else
				this.FDSNo = rs.getString("FDSNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("FamilyDoctorNo") == null )
				this.FamilyDoctorNo = null;
			else
				this.FamilyDoctorNo = rs.getString("FamilyDoctorNo").trim();

			if( rs.getString("FamilyDoctorName") == null )
				this.FamilyDoctorName = null;
			else
				this.FamilyDoctorName = rs.getString("FamilyDoctorName").trim();

			if( rs.getString("FDSCode") == null )
				this.FDSCode = null;
			else
				this.FDSCode = rs.getString("FDSCode").trim();

			if( rs.getString("FDSSubject") == null )
				this.FDSSubject = null;
			else
				this.FDSSubject = rs.getString("FDSSubject").trim();

			if( rs.getString("FDSContent") == null )
				this.FDSContent = null;
			else
				this.FDSContent = rs.getString("FDSContent").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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

			this.FDSDate = rs.getDate("FDSDate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LHFamilyDoctorService表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LHFamilyDoctorServiceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LHFamilyDoctorServiceSchema getSchema()
	{
		LHFamilyDoctorServiceSchema aLHFamilyDoctorServiceSchema = new LHFamilyDoctorServiceSchema();
		aLHFamilyDoctorServiceSchema.setSchema(this);
		return aLHFamilyDoctorServiceSchema;
	}

	public LHFamilyDoctorServiceDB getDB()
	{
		LHFamilyDoctorServiceDB aDBOper = new LHFamilyDoctorServiceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLHFamilyDoctorService描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(FDSNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FamilyDoctorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FamilyDoctorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FDSCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FDSSubject)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FDSContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FDSDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLHFamilyDoctorService>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FDSNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FamilyDoctorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FamilyDoctorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FDSCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FDSSubject = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FDSContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			FDSDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LHFamilyDoctorServiceSchema";
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
		if (FCode.equalsIgnoreCase("FDSNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FDSNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("FamilyDoctorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyDoctorNo));
		}
		if (FCode.equalsIgnoreCase("FamilyDoctorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyDoctorName));
		}
		if (FCode.equalsIgnoreCase("FDSCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FDSCode));
		}
		if (FCode.equalsIgnoreCase("FDSSubject"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FDSSubject));
		}
		if (FCode.equalsIgnoreCase("FDSContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FDSContent));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
		if (FCode.equalsIgnoreCase("FDSDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFDSDate()));
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
				strFieldValue = StrTool.GBKToUnicode(FDSNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FamilyDoctorNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FamilyDoctorName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FDSCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FDSSubject);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FDSContent);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(State);
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
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFDSDate()));
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

		if (FCode.equalsIgnoreCase("FDSNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FDSNo = FValue.trim();
			}
			else
				FDSNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
		}
		if (FCode.equalsIgnoreCase("FamilyDoctorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyDoctorNo = FValue.trim();
			}
			else
				FamilyDoctorNo = null;
		}
		if (FCode.equalsIgnoreCase("FamilyDoctorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyDoctorName = FValue.trim();
			}
			else
				FamilyDoctorName = null;
		}
		if (FCode.equalsIgnoreCase("FDSCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FDSCode = FValue.trim();
			}
			else
				FDSCode = null;
		}
		if (FCode.equalsIgnoreCase("FDSSubject"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FDSSubject = FValue.trim();
			}
			else
				FDSSubject = null;
		}
		if (FCode.equalsIgnoreCase("FDSContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FDSContent = FValue.trim();
			}
			else
				FDSContent = null;
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
		if (FCode.equalsIgnoreCase("FDSDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FDSDate = fDate.getDate( FValue );
			}
			else
				FDSDate = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LHFamilyDoctorServiceSchema other = (LHFamilyDoctorServiceSchema)otherObject;
		return
			FDSNo.equals(other.getFDSNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& Name.equals(other.getName())
			&& FamilyDoctorNo.equals(other.getFamilyDoctorNo())
			&& FamilyDoctorName.equals(other.getFamilyDoctorName())
			&& FDSCode.equals(other.getFDSCode())
			&& FDSSubject.equals(other.getFDSSubject())
			&& FDSContent.equals(other.getFDSContent())
			&& State.equals(other.getState())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(FDSDate).equals(other.getFDSDate());
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
		if( strFieldName.equals("FDSNo") ) {
			return 0;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 1;
		}
		if( strFieldName.equals("Name") ) {
			return 2;
		}
		if( strFieldName.equals("FamilyDoctorNo") ) {
			return 3;
		}
		if( strFieldName.equals("FamilyDoctorName") ) {
			return 4;
		}
		if( strFieldName.equals("FDSCode") ) {
			return 5;
		}
		if( strFieldName.equals("FDSSubject") ) {
			return 6;
		}
		if( strFieldName.equals("FDSContent") ) {
			return 7;
		}
		if( strFieldName.equals("State") ) {
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
		if( strFieldName.equals("FDSDate") ) {
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
				strFieldName = "FDSNo";
				break;
			case 1:
				strFieldName = "CustomerNo";
				break;
			case 2:
				strFieldName = "Name";
				break;
			case 3:
				strFieldName = "FamilyDoctorNo";
				break;
			case 4:
				strFieldName = "FamilyDoctorName";
				break;
			case 5:
				strFieldName = "FDSCode";
				break;
			case 6:
				strFieldName = "FDSSubject";
				break;
			case 7:
				strFieldName = "FDSContent";
				break;
			case 8:
				strFieldName = "State";
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
				strFieldName = "FDSDate";
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
		if( strFieldName.equals("FDSNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FamilyDoctorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FamilyDoctorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FDSCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FDSSubject") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FDSContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
		if( strFieldName.equals("FDSDate") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
