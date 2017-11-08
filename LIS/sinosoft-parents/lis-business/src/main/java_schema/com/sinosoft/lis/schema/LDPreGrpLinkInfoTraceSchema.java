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
import com.sinosoft.lis.db.LDPreGrpLinkInfoTraceDB;

/*
 * <p>ClassName: LDPreGrpLinkInfoTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LDPreGrpLinkInfoTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDPreGrpLinkInfoTraceSchema.class);
	// @Field
	/** 轨迹号 */
	private String TraceID;
	/** 准客户号码 */
	private String PreCustomerNo;
	/** 联系人顺序号 */
	private int LinkOrderNo;
	/** 联系人类型 */
	private String LinkType;
	/** 姓名 */
	private String LinkMan;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 证件号码有效止期 */
	private Date IDExpiryDate;
	/** 手机号 */
	private String Mobile;
	/** 联系电话 */
	private String Phone;
	/** 传真 */
	private String Fax;
	/** Email */
	private String Email;
	/** 部门 */
	private String Depart;
	/** 职务 */
	private String Post;
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

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDPreGrpLinkInfoTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "TraceID";
		pk[1] = "PreCustomerNo";
		pk[2] = "LinkOrderNo";

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
		LDPreGrpLinkInfoTraceSchema cloned = (LDPreGrpLinkInfoTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTraceID()
	{
		return TraceID;
	}
	public void setTraceID(String aTraceID)
	{
		if(aTraceID!=null && aTraceID.length()>10)
			throw new IllegalArgumentException("轨迹号TraceID值"+aTraceID+"的长度"+aTraceID.length()+"大于最大值10");
		TraceID = aTraceID;
	}
	public String getPreCustomerNo()
	{
		return PreCustomerNo;
	}
	public void setPreCustomerNo(String aPreCustomerNo)
	{
		if(aPreCustomerNo!=null && aPreCustomerNo.length()>10)
			throw new IllegalArgumentException("准客户号码PreCustomerNo值"+aPreCustomerNo+"的长度"+aPreCustomerNo.length()+"大于最大值10");
		PreCustomerNo = aPreCustomerNo;
	}
	public int getLinkOrderNo()
	{
		return LinkOrderNo;
	}
	public void setLinkOrderNo(int aLinkOrderNo)
	{
		LinkOrderNo = aLinkOrderNo;
	}
	public void setLinkOrderNo(String aLinkOrderNo)
	{
		if (aLinkOrderNo != null && !aLinkOrderNo.equals(""))
		{
			Integer tInteger = new Integer(aLinkOrderNo);
			int i = tInteger.intValue();
			LinkOrderNo = i;
		}
	}

	/**
	* 00-经办人<p>
	* 01-股东<p>
	* 02-实际控制人<p>
	* 03-法定代表人<p>
	* 04-负责人
	*/
	public String getLinkType()
	{
		return LinkType;
	}
	public void setLinkType(String aLinkType)
	{
		if(aLinkType!=null && aLinkType.length()>2)
			throw new IllegalArgumentException("联系人类型LinkType值"+aLinkType+"的长度"+aLinkType.length()+"大于最大值2");
		LinkType = aLinkType;
	}
	public String getLinkMan()
	{
		return LinkMan;
	}
	public void setLinkMan(String aLinkMan)
	{
		if(aLinkMan!=null && aLinkMan.length()>60)
			throw new IllegalArgumentException("姓名LinkMan值"+aLinkMan+"的长度"+aLinkMan.length()+"大于最大值60");
		LinkMan = aLinkMan;
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
		if(aIDNo!=null && aIDNo.length()>2)
			throw new IllegalArgumentException("证件号码IDNo值"+aIDNo+"的长度"+aIDNo.length()+"大于最大值2");
		IDNo = aIDNo;
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

	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String aMobile)
	{
		if(aMobile!=null && aMobile.length()>15)
			throw new IllegalArgumentException("手机号Mobile值"+aMobile+"的长度"+aMobile.length()+"大于最大值15");
		Mobile = aMobile;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		if(aPhone!=null && aPhone.length()>20)
			throw new IllegalArgumentException("联系电话Phone值"+aPhone+"的长度"+aPhone.length()+"大于最大值20");
		Phone = aPhone;
	}
	public String getFax()
	{
		return Fax;
	}
	public void setFax(String aFax)
	{
		if(aFax!=null && aFax.length()>20)
			throw new IllegalArgumentException("传真Fax值"+aFax+"的长度"+aFax.length()+"大于最大值20");
		Fax = aFax;
	}
	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String aEmail)
	{
		if(aEmail!=null && aEmail.length()>50)
			throw new IllegalArgumentException("EmailEmail值"+aEmail+"的长度"+aEmail.length()+"大于最大值50");
		Email = aEmail;
	}
	public String getDepart()
	{
		return Depart;
	}
	public void setDepart(String aDepart)
	{
		if(aDepart!=null && aDepart.length()>50)
			throw new IllegalArgumentException("部门Depart值"+aDepart+"的长度"+aDepart.length()+"大于最大值50");
		Depart = aDepart;
	}
	public String getPost()
	{
		return Post;
	}
	public void setPost(String aPost)
	{
		if(aPost!=null && aPost.length()>50)
			throw new IllegalArgumentException("职务Post值"+aPost+"的长度"+aPost.length()+"大于最大值50");
		Post = aPost;
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
	* 使用另外一个 LDPreGrpLinkInfoTraceSchema 对象给 Schema 赋值
	* @param: aLDPreGrpLinkInfoTraceSchema LDPreGrpLinkInfoTraceSchema
	**/
	public void setSchema(LDPreGrpLinkInfoTraceSchema aLDPreGrpLinkInfoTraceSchema)
	{
		this.TraceID = aLDPreGrpLinkInfoTraceSchema.getTraceID();
		this.PreCustomerNo = aLDPreGrpLinkInfoTraceSchema.getPreCustomerNo();
		this.LinkOrderNo = aLDPreGrpLinkInfoTraceSchema.getLinkOrderNo();
		this.LinkType = aLDPreGrpLinkInfoTraceSchema.getLinkType();
		this.LinkMan = aLDPreGrpLinkInfoTraceSchema.getLinkMan();
		this.IDType = aLDPreGrpLinkInfoTraceSchema.getIDType();
		this.IDNo = aLDPreGrpLinkInfoTraceSchema.getIDNo();
		this.IDExpiryDate = fDate.getDate( aLDPreGrpLinkInfoTraceSchema.getIDExpiryDate());
		this.Mobile = aLDPreGrpLinkInfoTraceSchema.getMobile();
		this.Phone = aLDPreGrpLinkInfoTraceSchema.getPhone();
		this.Fax = aLDPreGrpLinkInfoTraceSchema.getFax();
		this.Email = aLDPreGrpLinkInfoTraceSchema.getEmail();
		this.Depart = aLDPreGrpLinkInfoTraceSchema.getDepart();
		this.Post = aLDPreGrpLinkInfoTraceSchema.getPost();
		this.MakeOperator = aLDPreGrpLinkInfoTraceSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLDPreGrpLinkInfoTraceSchema.getMakeDate());
		this.MakeTime = aLDPreGrpLinkInfoTraceSchema.getMakeTime();
		this.ModifyOperator = aLDPreGrpLinkInfoTraceSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLDPreGrpLinkInfoTraceSchema.getModifyDate());
		this.ModifyTime = aLDPreGrpLinkInfoTraceSchema.getModifyTime();
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
			if( rs.getString("TraceID") == null )
				this.TraceID = null;
			else
				this.TraceID = rs.getString("TraceID").trim();

			if( rs.getString("PreCustomerNo") == null )
				this.PreCustomerNo = null;
			else
				this.PreCustomerNo = rs.getString("PreCustomerNo").trim();

			this.LinkOrderNo = rs.getInt("LinkOrderNo");
			if( rs.getString("LinkType") == null )
				this.LinkType = null;
			else
				this.LinkType = rs.getString("LinkType").trim();

			if( rs.getString("LinkMan") == null )
				this.LinkMan = null;
			else
				this.LinkMan = rs.getString("LinkMan").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			this.IDExpiryDate = rs.getDate("IDExpiryDate");
			if( rs.getString("Mobile") == null )
				this.Mobile = null;
			else
				this.Mobile = rs.getString("Mobile").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("Fax") == null )
				this.Fax = null;
			else
				this.Fax = rs.getString("Fax").trim();

			if( rs.getString("Email") == null )
				this.Email = null;
			else
				this.Email = rs.getString("Email").trim();

			if( rs.getString("Depart") == null )
				this.Depart = null;
			else
				this.Depart = rs.getString("Depart").trim();

			if( rs.getString("Post") == null )
				this.Post = null;
			else
				this.Post = rs.getString("Post").trim();

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
			logger.debug("数据库中的LDPreGrpLinkInfoTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPreGrpLinkInfoTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDPreGrpLinkInfoTraceSchema getSchema()
	{
		LDPreGrpLinkInfoTraceSchema aLDPreGrpLinkInfoTraceSchema = new LDPreGrpLinkInfoTraceSchema();
		aLDPreGrpLinkInfoTraceSchema.setSchema(this);
		return aLDPreGrpLinkInfoTraceSchema;
	}

	public LDPreGrpLinkInfoTraceDB getDB()
	{
		LDPreGrpLinkInfoTraceDB aDBOper = new LDPreGrpLinkInfoTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPreGrpLinkInfoTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TraceID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PreCustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LinkOrderNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IDExpiryDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Mobile)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Email)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Depart)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Post)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPreGrpLinkInfoTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TraceID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PreCustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			LinkOrderNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			LinkType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			LinkMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			IDExpiryDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			Mobile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Email = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Depart = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Post = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPreGrpLinkInfoTraceSchema";
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
		if (FCode.equalsIgnoreCase("TraceID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TraceID));
		}
		if (FCode.equalsIgnoreCase("PreCustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreCustomerNo));
		}
		if (FCode.equalsIgnoreCase("LinkOrderNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkOrderNo));
		}
		if (FCode.equalsIgnoreCase("LinkType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkType));
		}
		if (FCode.equalsIgnoreCase("LinkMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkMan));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("IDExpiryDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIDExpiryDate()));
		}
		if (FCode.equalsIgnoreCase("Mobile"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mobile));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("Fax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax));
		}
		if (FCode.equalsIgnoreCase("Email"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Email));
		}
		if (FCode.equalsIgnoreCase("Depart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Depart));
		}
		if (FCode.equalsIgnoreCase("Post"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Post));
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
				strFieldValue = StrTool.GBKToUnicode(TraceID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PreCustomerNo);
				break;
			case 2:
				strFieldValue = String.valueOf(LinkOrderNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(LinkType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(LinkMan);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIDExpiryDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Mobile);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Email);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Depart);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Post);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 19:
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

		if (FCode.equalsIgnoreCase("TraceID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TraceID = FValue.trim();
			}
			else
				TraceID = null;
		}
		if (FCode.equalsIgnoreCase("PreCustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PreCustomerNo = FValue.trim();
			}
			else
				PreCustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("LinkOrderNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				LinkOrderNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("LinkType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkType = FValue.trim();
			}
			else
				LinkType = null;
		}
		if (FCode.equalsIgnoreCase("LinkMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkMan = FValue.trim();
			}
			else
				LinkMan = null;
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
		if (FCode.equalsIgnoreCase("IDExpiryDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				IDExpiryDate = fDate.getDate( FValue );
			}
			else
				IDExpiryDate = null;
		}
		if (FCode.equalsIgnoreCase("Mobile"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Mobile = FValue.trim();
			}
			else
				Mobile = null;
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone = FValue.trim();
			}
			else
				Phone = null;
		}
		if (FCode.equalsIgnoreCase("Fax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Fax = FValue.trim();
			}
			else
				Fax = null;
		}
		if (FCode.equalsIgnoreCase("Email"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Email = FValue.trim();
			}
			else
				Email = null;
		}
		if (FCode.equalsIgnoreCase("Depart"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Depart = FValue.trim();
			}
			else
				Depart = null;
		}
		if (FCode.equalsIgnoreCase("Post"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Post = FValue.trim();
			}
			else
				Post = null;
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
		LDPreGrpLinkInfoTraceSchema other = (LDPreGrpLinkInfoTraceSchema)otherObject;
		return
			TraceID.equals(other.getTraceID())
			&& PreCustomerNo.equals(other.getPreCustomerNo())
			&& LinkOrderNo == other.getLinkOrderNo()
			&& LinkType.equals(other.getLinkType())
			&& LinkMan.equals(other.getLinkMan())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& fDate.getString(IDExpiryDate).equals(other.getIDExpiryDate())
			&& Mobile.equals(other.getMobile())
			&& Phone.equals(other.getPhone())
			&& Fax.equals(other.getFax())
			&& Email.equals(other.getEmail())
			&& Depart.equals(other.getDepart())
			&& Post.equals(other.getPost())
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
		if( strFieldName.equals("TraceID") ) {
			return 0;
		}
		if( strFieldName.equals("PreCustomerNo") ) {
			return 1;
		}
		if( strFieldName.equals("LinkOrderNo") ) {
			return 2;
		}
		if( strFieldName.equals("LinkType") ) {
			return 3;
		}
		if( strFieldName.equals("LinkMan") ) {
			return 4;
		}
		if( strFieldName.equals("IDType") ) {
			return 5;
		}
		if( strFieldName.equals("IDNo") ) {
			return 6;
		}
		if( strFieldName.equals("IDExpiryDate") ) {
			return 7;
		}
		if( strFieldName.equals("Mobile") ) {
			return 8;
		}
		if( strFieldName.equals("Phone") ) {
			return 9;
		}
		if( strFieldName.equals("Fax") ) {
			return 10;
		}
		if( strFieldName.equals("Email") ) {
			return 11;
		}
		if( strFieldName.equals("Depart") ) {
			return 12;
		}
		if( strFieldName.equals("Post") ) {
			return 13;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 19;
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
				strFieldName = "TraceID";
				break;
			case 1:
				strFieldName = "PreCustomerNo";
				break;
			case 2:
				strFieldName = "LinkOrderNo";
				break;
			case 3:
				strFieldName = "LinkType";
				break;
			case 4:
				strFieldName = "LinkMan";
				break;
			case 5:
				strFieldName = "IDType";
				break;
			case 6:
				strFieldName = "IDNo";
				break;
			case 7:
				strFieldName = "IDExpiryDate";
				break;
			case 8:
				strFieldName = "Mobile";
				break;
			case 9:
				strFieldName = "Phone";
				break;
			case 10:
				strFieldName = "Fax";
				break;
			case 11:
				strFieldName = "Email";
				break;
			case 12:
				strFieldName = "Depart";
				break;
			case 13:
				strFieldName = "Post";
				break;
			case 14:
				strFieldName = "MakeOperator";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyOperator";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
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
		if( strFieldName.equals("TraceID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PreCustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkOrderNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("LinkType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDExpiryDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Mobile") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Email") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Depart") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Post") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
