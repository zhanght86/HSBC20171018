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
import com.sinosoft.lis.db.LLCondoleDB;

/*
 * <p>ClassName: LLCondoleSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLCondoleSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLCondoleSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 慰问序号 */
	private String ConNo;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 出险人名称 */
	private String CustomerName;
	/** Vip客户 */
	private String VIPFlag;
	/** 慰问类型 */
	private String ConType;
	/** 慰问描述 */
	private String ConDesc;
	/** 慰问人 */
	private String ConPer;
	/** 慰问日期 */
	private Date ConDate;
	/** 慰问机构 */
	private String ConDept;
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
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLCondoleSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ClmNo";
		pk[1] = "ConNo";
		pk[2] = "CustomerNo";

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
		LLCondoleSchema cloned = (LLCondoleSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		if(aClmNo!=null && aClmNo.length()>20)
			throw new IllegalArgumentException("赔案号ClmNo值"+aClmNo+"的长度"+aClmNo.length()+"大于最大值20");
		ClmNo = aClmNo;
	}
	public String getConNo()
	{
		return ConNo;
	}
	public void setConNo(String aConNo)
	{
		if(aConNo!=null && aConNo.length()>20)
			throw new IllegalArgumentException("慰问序号ConNo值"+aConNo+"的长度"+aConNo.length()+"大于最大值20");
		ConNo = aConNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>20)
			throw new IllegalArgumentException("出险人客户号CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值20");
		CustomerNo = aCustomerNo;
	}
	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String aCustomerName)
	{
		if(aCustomerName!=null && aCustomerName.length()>20)
			throw new IllegalArgumentException("出险人名称CustomerName值"+aCustomerName+"的长度"+aCustomerName.length()+"大于最大值20");
		CustomerName = aCustomerName;
	}
	public String getVIPFlag()
	{
		return VIPFlag;
	}
	public void setVIPFlag(String aVIPFlag)
	{
		if(aVIPFlag!=null && aVIPFlag.length()>6)
			throw new IllegalArgumentException("Vip客户VIPFlag值"+aVIPFlag+"的长度"+aVIPFlag.length()+"大于最大值6");
		VIPFlag = aVIPFlag;
	}
	/**
	* 中心到分公司 分公司到总公司
	*/
	public String getConType()
	{
		return ConType;
	}
	public void setConType(String aConType)
	{
		if(aConType!=null && aConType.length()>20)
			throw new IllegalArgumentException("慰问类型ConType值"+aConType+"的长度"+aConType.length()+"大于最大值20");
		ConType = aConType;
	}
	/**
	* 是否是本地调查
	*/
	public String getConDesc()
	{
		return ConDesc;
	}
	public void setConDesc(String aConDesc)
	{
		if(aConDesc!=null && aConDesc.length()>2000)
			throw new IllegalArgumentException("慰问描述ConDesc值"+aConDesc+"的长度"+aConDesc.length()+"大于最大值2000");
		ConDesc = aConDesc;
	}
	public String getConPer()
	{
		return ConPer;
	}
	public void setConPer(String aConPer)
	{
		if(aConPer!=null && aConPer.length()>20)
			throw new IllegalArgumentException("慰问人ConPer值"+aConPer+"的长度"+aConPer.length()+"大于最大值20");
		ConPer = aConPer;
	}
	public String getConDate()
	{
		if( ConDate != null )
			return fDate.getString(ConDate);
		else
			return null;
	}
	public void setConDate(Date aConDate)
	{
		ConDate = aConDate;
	}
	public void setConDate(String aConDate)
	{
		if (aConDate != null && !aConDate.equals("") )
		{
			ConDate = fDate.getDate( aConDate );
		}
		else
			ConDate = null;
	}

	public String getConDept()
	{
		return ConDept;
	}
	public void setConDept(String aConDept)
	{
		if(aConDept!=null && aConDept.length()>20)
			throw new IllegalArgumentException("慰问机构ConDept值"+aConDept+"的长度"+aConDept.length()+"大于最大值20");
		ConDept = aConDept;
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

	/**
	* 使用另外一个 LLCondoleSchema 对象给 Schema 赋值
	* @param: aLLCondoleSchema LLCondoleSchema
	**/
	public void setSchema(LLCondoleSchema aLLCondoleSchema)
	{
		this.ClmNo = aLLCondoleSchema.getClmNo();
		this.ConNo = aLLCondoleSchema.getConNo();
		this.CustomerNo = aLLCondoleSchema.getCustomerNo();
		this.CustomerName = aLLCondoleSchema.getCustomerName();
		this.VIPFlag = aLLCondoleSchema.getVIPFlag();
		this.ConType = aLLCondoleSchema.getConType();
		this.ConDesc = aLLCondoleSchema.getConDesc();
		this.ConPer = aLLCondoleSchema.getConPer();
		this.ConDate = fDate.getDate( aLLCondoleSchema.getConDate());
		this.ConDept = aLLCondoleSchema.getConDept();
		this.Operator = aLLCondoleSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLCondoleSchema.getMakeDate());
		this.MakeTime = aLLCondoleSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLCondoleSchema.getModifyDate());
		this.ModifyTime = aLLCondoleSchema.getModifyTime();
		this.Remark = aLLCondoleSchema.getRemark();
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
			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("ConNo") == null )
				this.ConNo = null;
			else
				this.ConNo = rs.getString("ConNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerName") == null )
				this.CustomerName = null;
			else
				this.CustomerName = rs.getString("CustomerName").trim();

			if( rs.getString("VIPFlag") == null )
				this.VIPFlag = null;
			else
				this.VIPFlag = rs.getString("VIPFlag").trim();

			if( rs.getString("ConType") == null )
				this.ConType = null;
			else
				this.ConType = rs.getString("ConType").trim();

			if( rs.getString("ConDesc") == null )
				this.ConDesc = null;
			else
				this.ConDesc = rs.getString("ConDesc").trim();

			if( rs.getString("ConPer") == null )
				this.ConPer = null;
			else
				this.ConPer = rs.getString("ConPer").trim();

			this.ConDate = rs.getDate("ConDate");
			if( rs.getString("ConDept") == null )
				this.ConDept = null;
			else
				this.ConDept = rs.getString("ConDept").trim();

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

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLCondole表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCondoleSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLCondoleSchema getSchema()
	{
		LLCondoleSchema aLLCondoleSchema = new LLCondoleSchema();
		aLLCondoleSchema.setSchema(this);
		return aLLCondoleSchema;
	}

	public LLCondoleDB getDB()
	{
		LLCondoleDB aDBOper = new LLCondoleDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCondole描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VIPFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConPer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConDept)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCondole>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ConNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			VIPFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ConType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ConDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ConPer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ConDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			ConDept = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCondoleSchema";
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
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("ConNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerName));
		}
		if (FCode.equalsIgnoreCase("VIPFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VIPFlag));
		}
		if (FCode.equalsIgnoreCase("ConType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConType));
		}
		if (FCode.equalsIgnoreCase("ConDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConDesc));
		}
		if (FCode.equalsIgnoreCase("ConPer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConPer));
		}
		if (FCode.equalsIgnoreCase("ConDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConDate()));
		}
		if (FCode.equalsIgnoreCase("ConDept"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConDept));
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ConNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(VIPFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ConType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ConDesc);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ConPer);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ConDept);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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

		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("ConNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConNo = FValue.trim();
			}
			else
				ConNo = null;
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
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerName = FValue.trim();
			}
			else
				CustomerName = null;
		}
		if (FCode.equalsIgnoreCase("VIPFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VIPFlag = FValue.trim();
			}
			else
				VIPFlag = null;
		}
		if (FCode.equalsIgnoreCase("ConType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConType = FValue.trim();
			}
			else
				ConType = null;
		}
		if (FCode.equalsIgnoreCase("ConDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConDesc = FValue.trim();
			}
			else
				ConDesc = null;
		}
		if (FCode.equalsIgnoreCase("ConPer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConPer = FValue.trim();
			}
			else
				ConPer = null;
		}
		if (FCode.equalsIgnoreCase("ConDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConDate = fDate.getDate( FValue );
			}
			else
				ConDate = null;
		}
		if (FCode.equalsIgnoreCase("ConDept"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConDept = FValue.trim();
			}
			else
				ConDept = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLCondoleSchema other = (LLCondoleSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& ConNo.equals(other.getConNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerName.equals(other.getCustomerName())
			&& VIPFlag.equals(other.getVIPFlag())
			&& ConType.equals(other.getConType())
			&& ConDesc.equals(other.getConDesc())
			&& ConPer.equals(other.getConPer())
			&& fDate.getString(ConDate).equals(other.getConDate())
			&& ConDept.equals(other.getConDept())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("ClmNo") ) {
			return 0;
		}
		if( strFieldName.equals("ConNo") ) {
			return 1;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 2;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 3;
		}
		if( strFieldName.equals("VIPFlag") ) {
			return 4;
		}
		if( strFieldName.equals("ConType") ) {
			return 5;
		}
		if( strFieldName.equals("ConDesc") ) {
			return 6;
		}
		if( strFieldName.equals("ConPer") ) {
			return 7;
		}
		if( strFieldName.equals("ConDate") ) {
			return 8;
		}
		if( strFieldName.equals("ConDept") ) {
			return 9;
		}
		if( strFieldName.equals("Operator") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 14;
		}
		if( strFieldName.equals("Remark") ) {
			return 15;
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "ConNo";
				break;
			case 2:
				strFieldName = "CustomerNo";
				break;
			case 3:
				strFieldName = "CustomerName";
				break;
			case 4:
				strFieldName = "VIPFlag";
				break;
			case 5:
				strFieldName = "ConType";
				break;
			case 6:
				strFieldName = "ConDesc";
				break;
			case 7:
				strFieldName = "ConPer";
				break;
			case 8:
				strFieldName = "ConDate";
				break;
			case 9:
				strFieldName = "ConDept";
				break;
			case 10:
				strFieldName = "Operator";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "ModifyDate";
				break;
			case 14:
				strFieldName = "ModifyTime";
				break;
			case 15:
				strFieldName = "Remark";
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
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VIPFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConPer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConDept") ) {
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
		if( strFieldName.equals("Remark") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
