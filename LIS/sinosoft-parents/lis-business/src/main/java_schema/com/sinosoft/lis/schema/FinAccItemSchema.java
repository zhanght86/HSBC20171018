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
import com.sinosoft.lis.db.FinAccItemDB;

/*
 * <p>ClassName: FinAccItemSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class FinAccItemSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FinAccItemSchema.class);
	// @Field
	/** 分支科目编码 */
	private String AccItemCode;
	/** 分支科目编码1 */
	private String AccItemCode1;
	/** 分支科目编码2 */
	private String AccItemCode2;
	/** 分支科目编码3 */
	private String AccItemCode3;
	/** 分支科目编码4 */
	private String AccItemCode4;
	/** 分支科目编码5 */
	private String AccItemCode5;
	/** 分支科目编码6 */
	private String AccItemCode6;
	/** 分支科目名称 */
	private String AccItemName;
	/** 会计科目编码 */
	private String FinCode;
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
	public FinAccItemSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "AccItemCode";

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
		FinAccItemSchema cloned = (FinAccItemSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAccItemCode()
	{
		return AccItemCode;
	}
	public void setAccItemCode(String aAccItemCode)
	{
		if(aAccItemCode!=null && aAccItemCode.length()>30)
			throw new IllegalArgumentException("分支科目编码AccItemCode值"+aAccItemCode+"的长度"+aAccItemCode.length()+"大于最大值30");
		AccItemCode = aAccItemCode;
	}
	public String getAccItemCode1()
	{
		return AccItemCode1;
	}
	public void setAccItemCode1(String aAccItemCode1)
	{
		if(aAccItemCode1!=null && aAccItemCode1.length()>30)
			throw new IllegalArgumentException("分支科目编码1AccItemCode1值"+aAccItemCode1+"的长度"+aAccItemCode1.length()+"大于最大值30");
		AccItemCode1 = aAccItemCode1;
	}
	public String getAccItemCode2()
	{
		return AccItemCode2;
	}
	public void setAccItemCode2(String aAccItemCode2)
	{
		if(aAccItemCode2!=null && aAccItemCode2.length()>30)
			throw new IllegalArgumentException("分支科目编码2AccItemCode2值"+aAccItemCode2+"的长度"+aAccItemCode2.length()+"大于最大值30");
		AccItemCode2 = aAccItemCode2;
	}
	public String getAccItemCode3()
	{
		return AccItemCode3;
	}
	public void setAccItemCode3(String aAccItemCode3)
	{
		if(aAccItemCode3!=null && aAccItemCode3.length()>30)
			throw new IllegalArgumentException("分支科目编码3AccItemCode3值"+aAccItemCode3+"的长度"+aAccItemCode3.length()+"大于最大值30");
		AccItemCode3 = aAccItemCode3;
	}
	public String getAccItemCode4()
	{
		return AccItemCode4;
	}
	public void setAccItemCode4(String aAccItemCode4)
	{
		if(aAccItemCode4!=null && aAccItemCode4.length()>30)
			throw new IllegalArgumentException("分支科目编码4AccItemCode4值"+aAccItemCode4+"的长度"+aAccItemCode4.length()+"大于最大值30");
		AccItemCode4 = aAccItemCode4;
	}
	public String getAccItemCode5()
	{
		return AccItemCode5;
	}
	public void setAccItemCode5(String aAccItemCode5)
	{
		if(aAccItemCode5!=null && aAccItemCode5.length()>30)
			throw new IllegalArgumentException("分支科目编码5AccItemCode5值"+aAccItemCode5+"的长度"+aAccItemCode5.length()+"大于最大值30");
		AccItemCode5 = aAccItemCode5;
	}
	public String getAccItemCode6()
	{
		return AccItemCode6;
	}
	public void setAccItemCode6(String aAccItemCode6)
	{
		if(aAccItemCode6!=null && aAccItemCode6.length()>30)
			throw new IllegalArgumentException("分支科目编码6AccItemCode6值"+aAccItemCode6+"的长度"+aAccItemCode6.length()+"大于最大值30");
		AccItemCode6 = aAccItemCode6;
	}
	public String getAccItemName()
	{
		return AccItemName;
	}
	public void setAccItemName(String aAccItemName)
	{
		if(aAccItemName!=null && aAccItemName.length()>200)
			throw new IllegalArgumentException("分支科目名称AccItemName值"+aAccItemName+"的长度"+aAccItemName.length()+"大于最大值200");
		AccItemName = aAccItemName;
	}
	public String getFinCode()
	{
		return FinCode;
	}
	public void setFinCode(String aFinCode)
	{
		if(aFinCode!=null && aFinCode.length()>30)
			throw new IllegalArgumentException("会计科目编码FinCode值"+aFinCode+"的长度"+aFinCode.length()+"大于最大值30");
		FinCode = aFinCode;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>200)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值200");
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
	* 使用另外一个 FinAccItemSchema 对象给 Schema 赋值
	* @param: aFinAccItemSchema FinAccItemSchema
	**/
	public void setSchema(FinAccItemSchema aFinAccItemSchema)
	{
		this.AccItemCode = aFinAccItemSchema.getAccItemCode();
		this.AccItemCode1 = aFinAccItemSchema.getAccItemCode1();
		this.AccItemCode2 = aFinAccItemSchema.getAccItemCode2();
		this.AccItemCode3 = aFinAccItemSchema.getAccItemCode3();
		this.AccItemCode4 = aFinAccItemSchema.getAccItemCode4();
		this.AccItemCode5 = aFinAccItemSchema.getAccItemCode5();
		this.AccItemCode6 = aFinAccItemSchema.getAccItemCode6();
		this.AccItemName = aFinAccItemSchema.getAccItemName();
		this.FinCode = aFinAccItemSchema.getFinCode();
		this.Remark = aFinAccItemSchema.getRemark();
		this.ManageCom = aFinAccItemSchema.getManageCom();
		this.ComCode = aFinAccItemSchema.getComCode();
		this.MakeOperator = aFinAccItemSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aFinAccItemSchema.getMakeDate());
		this.MakeTime = aFinAccItemSchema.getMakeTime();
		this.ModifyOperator = aFinAccItemSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aFinAccItemSchema.getModifyDate());
		this.ModifyTime = aFinAccItemSchema.getModifyTime();
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
			if( rs.getString("AccItemCode") == null )
				this.AccItemCode = null;
			else
				this.AccItemCode = rs.getString("AccItemCode").trim();

			if( rs.getString("AccItemCode1") == null )
				this.AccItemCode1 = null;
			else
				this.AccItemCode1 = rs.getString("AccItemCode1").trim();

			if( rs.getString("AccItemCode2") == null )
				this.AccItemCode2 = null;
			else
				this.AccItemCode2 = rs.getString("AccItemCode2").trim();

			if( rs.getString("AccItemCode3") == null )
				this.AccItemCode3 = null;
			else
				this.AccItemCode3 = rs.getString("AccItemCode3").trim();

			if( rs.getString("AccItemCode4") == null )
				this.AccItemCode4 = null;
			else
				this.AccItemCode4 = rs.getString("AccItemCode4").trim();

			if( rs.getString("AccItemCode5") == null )
				this.AccItemCode5 = null;
			else
				this.AccItemCode5 = rs.getString("AccItemCode5").trim();

			if( rs.getString("AccItemCode6") == null )
				this.AccItemCode6 = null;
			else
				this.AccItemCode6 = rs.getString("AccItemCode6").trim();

			if( rs.getString("AccItemName") == null )
				this.AccItemName = null;
			else
				this.AccItemName = rs.getString("AccItemName").trim();

			if( rs.getString("FinCode") == null )
				this.FinCode = null;
			else
				this.FinCode = rs.getString("FinCode").trim();

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
			logger.debug("数据库中的FinAccItem表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FinAccItemSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FinAccItemSchema getSchema()
	{
		FinAccItemSchema aFinAccItemSchema = new FinAccItemSchema();
		aFinAccItemSchema.setSchema(this);
		return aFinAccItemSchema;
	}

	public FinAccItemDB getDB()
	{
		FinAccItemDB aDBOper = new FinAccItemDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFinAccItem描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AccItemCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemCode1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemCode2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemCode3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemCode4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemCode5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemCode6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FinCode)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFinAccItem>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AccItemCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AccItemCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AccItemCode2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AccItemCode3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AccItemCode4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AccItemCode5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AccItemCode6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AccItemName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			FinCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
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
			tError.moduleName = "FinAccItemSchema";
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
		if (FCode.equalsIgnoreCase("AccItemCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemCode));
		}
		if (FCode.equalsIgnoreCase("AccItemCode1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemCode1));
		}
		if (FCode.equalsIgnoreCase("AccItemCode2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemCode2));
		}
		if (FCode.equalsIgnoreCase("AccItemCode3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemCode3));
		}
		if (FCode.equalsIgnoreCase("AccItemCode4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemCode4));
		}
		if (FCode.equalsIgnoreCase("AccItemCode5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemCode5));
		}
		if (FCode.equalsIgnoreCase("AccItemCode6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemCode6));
		}
		if (FCode.equalsIgnoreCase("AccItemName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemName));
		}
		if (FCode.equalsIgnoreCase("FinCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinCode));
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
				strFieldValue = StrTool.GBKToUnicode(AccItemCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AccItemCode1);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AccItemCode2);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AccItemCode3);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AccItemCode4);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AccItemCode5);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AccItemCode6);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AccItemName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(FinCode);
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

		if (FCode.equalsIgnoreCase("AccItemCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemCode = FValue.trim();
			}
			else
				AccItemCode = null;
		}
		if (FCode.equalsIgnoreCase("AccItemCode1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemCode1 = FValue.trim();
			}
			else
				AccItemCode1 = null;
		}
		if (FCode.equalsIgnoreCase("AccItemCode2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemCode2 = FValue.trim();
			}
			else
				AccItemCode2 = null;
		}
		if (FCode.equalsIgnoreCase("AccItemCode3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemCode3 = FValue.trim();
			}
			else
				AccItemCode3 = null;
		}
		if (FCode.equalsIgnoreCase("AccItemCode4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemCode4 = FValue.trim();
			}
			else
				AccItemCode4 = null;
		}
		if (FCode.equalsIgnoreCase("AccItemCode5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemCode5 = FValue.trim();
			}
			else
				AccItemCode5 = null;
		}
		if (FCode.equalsIgnoreCase("AccItemCode6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemCode6 = FValue.trim();
			}
			else
				AccItemCode6 = null;
		}
		if (FCode.equalsIgnoreCase("AccItemName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemName = FValue.trim();
			}
			else
				AccItemName = null;
		}
		if (FCode.equalsIgnoreCase("FinCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinCode = FValue.trim();
			}
			else
				FinCode = null;
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
		FinAccItemSchema other = (FinAccItemSchema)otherObject;
		return
			AccItemCode.equals(other.getAccItemCode())
			&& AccItemCode1.equals(other.getAccItemCode1())
			&& AccItemCode2.equals(other.getAccItemCode2())
			&& AccItemCode3.equals(other.getAccItemCode3())
			&& AccItemCode4.equals(other.getAccItemCode4())
			&& AccItemCode5.equals(other.getAccItemCode5())
			&& AccItemCode6.equals(other.getAccItemCode6())
			&& AccItemName.equals(other.getAccItemName())
			&& FinCode.equals(other.getFinCode())
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
		if( strFieldName.equals("AccItemCode") ) {
			return 0;
		}
		if( strFieldName.equals("AccItemCode1") ) {
			return 1;
		}
		if( strFieldName.equals("AccItemCode2") ) {
			return 2;
		}
		if( strFieldName.equals("AccItemCode3") ) {
			return 3;
		}
		if( strFieldName.equals("AccItemCode4") ) {
			return 4;
		}
		if( strFieldName.equals("AccItemCode5") ) {
			return 5;
		}
		if( strFieldName.equals("AccItemCode6") ) {
			return 6;
		}
		if( strFieldName.equals("AccItemName") ) {
			return 7;
		}
		if( strFieldName.equals("FinCode") ) {
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
				strFieldName = "AccItemCode";
				break;
			case 1:
				strFieldName = "AccItemCode1";
				break;
			case 2:
				strFieldName = "AccItemCode2";
				break;
			case 3:
				strFieldName = "AccItemCode3";
				break;
			case 4:
				strFieldName = "AccItemCode4";
				break;
			case 5:
				strFieldName = "AccItemCode5";
				break;
			case 6:
				strFieldName = "AccItemCode6";
				break;
			case 7:
				strFieldName = "AccItemName";
				break;
			case 8:
				strFieldName = "FinCode";
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
		if( strFieldName.equals("AccItemCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItemCode1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItemCode2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItemCode3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItemCode4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItemCode5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItemCode6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItemName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinCode") ) {
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
