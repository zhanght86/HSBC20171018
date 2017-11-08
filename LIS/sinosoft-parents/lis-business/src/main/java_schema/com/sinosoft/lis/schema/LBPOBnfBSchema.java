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
import com.sinosoft.lis.db.LBPOBnfBDB;

/*
 * <p>ClassName: LBPOBnfBSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LBPOBnfBSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBPOBnfBSchema.class);

	// @Field
	/** 转储号 */
	private String IDX;
	/** 合同号码 */
	private String ContNo;
	/** 录入次数 */
	private int InputNo;
	/** 添空号 */
	private String FillNo;
	/** 保单号码 */
	private String PolNo;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 受益人类别 */
	private String BnfType;
	/** 受益人序号 */
	private String BnfNo;
	/** 受益顺序 */
	private String BnfGrade;
	/** 与被保人关系 */
	private String RelationToInsured;
	/** 受益份额 */
	private String BnfLot;
	/** 客户号码 */
	private String CustomerNo;
	/** 客户姓名 */
	private String Name;
	/** 客户性别 */
	private String Sex;
	/** 客户出生日期 */
	private String Birthday;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private String MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private String ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 受益人地址序号 */
	private String BnfAddress;
	/** 转储日期 */
	private Date TransDate;
	/** 转储时间 */
	private String TransTime;
	/** 转储操作员 */
	private String TransOperator;

	public static final int FIELDNUM = 26;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBPOBnfBSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "IDX";
		pk[1] = "InputNo";
		pk[2] = "FillNo";
		pk[3] = "PolNo";
		pk[4] = "InsuredNo";

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
		LBPOBnfBSchema cloned = (LBPOBnfBSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getIDX()
	{
		return IDX;
	}
	public void setIDX(String aIDX)
	{
		IDX = aIDX;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public int getInputNo()
	{
		return InputNo;
	}
	public void setInputNo(int aInputNo)
	{
		InputNo = aInputNo;
	}
	public void setInputNo(String aInputNo)
	{
		if (aInputNo != null && !aInputNo.equals(""))
		{
			Integer tInteger = new Integer(aInputNo);
			int i = tInteger.intValue();
			InputNo = i;
		}
	}

	public String getFillNo()
	{
		return FillNo;
	}
	public void setFillNo(String aFillNo)
	{
		FillNo = aFillNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
	}
	/**
	* 0 -- 生存受益人<p>
	* 1 -- 死亡受益人<p>
	* 2 -- 红利受益人
	*/
	public String getBnfType()
	{
		return BnfType;
	}
	public void setBnfType(String aBnfType)
	{
		BnfType = aBnfType;
	}
	/**
	* 对受益人进行唯一编码。
	*/
	public String getBnfNo()
	{
		return BnfNo;
	}
	public void setBnfNo(String aBnfNo)
	{
		BnfNo = aBnfNo;
	}
	/**
	* 指对受益人进行分组。<p>
	* 如第一受益人，第二受益人。
	*/
	public String getBnfGrade()
	{
		return BnfGrade;
	}
	public void setBnfGrade(String aBnfGrade)
	{
		BnfGrade = aBnfGrade;
	}
	/**
	* 00                   本人<p>
	* 01                   父子<p>
	* 02                   父女<p>
	* 03                   母子<p>
	* 04                   母女<p>
	* 05                   祖孙<p>
	* 07                   夫妻<p>
	* 08                   兄弟<p>
	* 09                   兄妹<p>
	* 10                   姐弟<p>
	* 11                   姐妹<p>
	* 12                   叔侄<p>
	* 13                   姑侄<p>
	* 14                   外甥<p>
	* 15                   媳<p>
	* 16                   婿<p>
	* 17                   姐夫<p>
	* 18                   朋友<p>
	* 19                   同事<p>
	* 20                   师生<p>
	* 21                   雇佣<p>
	* 22                   其他<p>
	* 23                   法定
	*/
	public String getRelationToInsured()
	{
		return RelationToInsured;
	}
	public void setRelationToInsured(String aRelationToInsured)
	{
		RelationToInsured = aRelationToInsured;
	}
	public String getBnfLot()
	{
		return BnfLot;
	}
	public void setBnfLot(String aBnfLot)
	{
		BnfLot = aBnfLot;
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
	public String getSex()
	{
		return Sex;
	}
	public void setSex(String aSex)
	{
		Sex = aSex;
	}
	public String getBirthday()
	{
		return Birthday;
	}
	public void setBirthday(String aBirthday)
	{
		Birthday = aBirthday;
	}
	/**
	* 0 -- 身份证<p>
	* 1 -- 护照<p>
	* 2 -- 军官证<p>
	* 3 -- 驾照<p>
	* 4 -- 出生证明<p>
	* 5 -- 户口簿<p>
	* 8 -- 其他<p>
	* 9 -- 数据转换证件
	*/
	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		IDType = aIDType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		IDNo = aIDNo;
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
		return MakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		MakeDate = aMakeDate;
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
		return ModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	public String getBnfAddress()
	{
		return BnfAddress;
	}
	public void setBnfAddress(String aBnfAddress)
	{
		BnfAddress = aBnfAddress;
	}
	public String getTransDate()
	{
		if( TransDate != null )
			return fDate.getString(TransDate);
		else
			return null;
	}
	public void setTransDate(Date aTransDate)
	{
		TransDate = aTransDate;
	}
	public void setTransDate(String aTransDate)
	{
		if (aTransDate != null && !aTransDate.equals("") )
		{
			TransDate = fDate.getDate( aTransDate );
		}
		else
			TransDate = null;
	}

	public String getTransTime()
	{
		return TransTime;
	}
	public void setTransTime(String aTransTime)
	{
		TransTime = aTransTime;
	}
	public String getTransOperator()
	{
		return TransOperator;
	}
	public void setTransOperator(String aTransOperator)
	{
		TransOperator = aTransOperator;
	}

	/**
	* 使用另外一个 LBPOBnfBSchema 对象给 Schema 赋值
	* @param: aLBPOBnfBSchema LBPOBnfBSchema
	**/
	public void setSchema(LBPOBnfBSchema aLBPOBnfBSchema)
	{
		this.IDX = aLBPOBnfBSchema.getIDX();
		this.ContNo = aLBPOBnfBSchema.getContNo();
		this.InputNo = aLBPOBnfBSchema.getInputNo();
		this.FillNo = aLBPOBnfBSchema.getFillNo();
		this.PolNo = aLBPOBnfBSchema.getPolNo();
		this.InsuredNo = aLBPOBnfBSchema.getInsuredNo();
		this.BnfType = aLBPOBnfBSchema.getBnfType();
		this.BnfNo = aLBPOBnfBSchema.getBnfNo();
		this.BnfGrade = aLBPOBnfBSchema.getBnfGrade();
		this.RelationToInsured = aLBPOBnfBSchema.getRelationToInsured();
		this.BnfLot = aLBPOBnfBSchema.getBnfLot();
		this.CustomerNo = aLBPOBnfBSchema.getCustomerNo();
		this.Name = aLBPOBnfBSchema.getName();
		this.Sex = aLBPOBnfBSchema.getSex();
		this.Birthday = aLBPOBnfBSchema.getBirthday();
		this.IDType = aLBPOBnfBSchema.getIDType();
		this.IDNo = aLBPOBnfBSchema.getIDNo();
		this.Operator = aLBPOBnfBSchema.getOperator();
		this.MakeDate = aLBPOBnfBSchema.getMakeDate();
		this.MakeTime = aLBPOBnfBSchema.getMakeTime();
		this.ModifyDate = aLBPOBnfBSchema.getModifyDate();
		this.ModifyTime = aLBPOBnfBSchema.getModifyTime();
		this.BnfAddress = aLBPOBnfBSchema.getBnfAddress();
		this.TransDate = fDate.getDate( aLBPOBnfBSchema.getTransDate());
		this.TransTime = aLBPOBnfBSchema.getTransTime();
		this.TransOperator = aLBPOBnfBSchema.getTransOperator();
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
			if( rs.getString("IDX") == null )
				this.IDX = null;
			else
				this.IDX = rs.getString("IDX").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			this.InputNo = rs.getInt("InputNo");
			if( rs.getString("FillNo") == null )
				this.FillNo = null;
			else
				this.FillNo = rs.getString("FillNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("BnfType") == null )
				this.BnfType = null;
			else
				this.BnfType = rs.getString("BnfType").trim();

			if( rs.getString("BnfNo") == null )
				this.BnfNo = null;
			else
				this.BnfNo = rs.getString("BnfNo").trim();

			if( rs.getString("BnfGrade") == null )
				this.BnfGrade = null;
			else
				this.BnfGrade = rs.getString("BnfGrade").trim();

			if( rs.getString("RelationToInsured") == null )
				this.RelationToInsured = null;
			else
				this.RelationToInsured = rs.getString("RelationToInsured").trim();

			if( rs.getString("BnfLot") == null )
				this.BnfLot = null;
			else
				this.BnfLot = rs.getString("BnfLot").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("Sex") == null )
				this.Sex = null;
			else
				this.Sex = rs.getString("Sex").trim();

			if( rs.getString("Birthday") == null )
				this.Birthday = null;
			else
				this.Birthday = rs.getString("Birthday").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("MakeDate") == null )
				this.MakeDate = null;
			else
				this.MakeDate = rs.getString("MakeDate").trim();

			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyDate") == null )
				this.ModifyDate = null;
			else
				this.ModifyDate = rs.getString("ModifyDate").trim();

			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("BnfAddress") == null )
				this.BnfAddress = null;
			else
				this.BnfAddress = rs.getString("BnfAddress").trim();

			this.TransDate = rs.getDate("TransDate");
			if( rs.getString("TransTime") == null )
				this.TransTime = null;
			else
				this.TransTime = rs.getString("TransTime").trim();

			if( rs.getString("TransOperator") == null )
				this.TransOperator = null;
			else
				this.TransOperator = rs.getString("TransOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBPOBnfB表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOBnfBSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBPOBnfBSchema getSchema()
	{
		LBPOBnfBSchema aLBPOBnfBSchema = new LBPOBnfBSchema();
		aLBPOBnfBSchema.setSchema(this);
		return aLBPOBnfBSchema;
	}

	public LBPOBnfBDB getDB()
	{
		LBPOBnfBDB aDBOper = new LBPOBnfBDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOBnfB描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(IDX)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InputNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FillNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToInsured)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfLot)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Sex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Birthday)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TransDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOBnfB>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			IDX = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InputNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			FillNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BnfType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			BnfNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			BnfGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RelationToInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			BnfLot = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Birthday = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			BnfAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			TransDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			TransTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			TransOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOBnfBSchema";
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
		if (FCode.equalsIgnoreCase("IDX"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDX));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("InputNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputNo));
		}
		if (FCode.equalsIgnoreCase("FillNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FillNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("BnfType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfType));
		}
		if (FCode.equalsIgnoreCase("BnfNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfNo));
		}
		if (FCode.equalsIgnoreCase("BnfGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfGrade));
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToInsured));
		}
		if (FCode.equalsIgnoreCase("BnfLot"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfLot));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("Sex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Sex));
		}
		if (FCode.equalsIgnoreCase("Birthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Birthday));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeDate));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyDate));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("BnfAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfAddress));
		}
		if (FCode.equalsIgnoreCase("TransDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
		}
		if (FCode.equalsIgnoreCase("TransTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransTime));
		}
		if (FCode.equalsIgnoreCase("TransOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransOperator));
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
				strFieldValue = StrTool.GBKToUnicode(IDX);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = String.valueOf(InputNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FillNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BnfType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(BnfNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BnfGrade);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RelationToInsured);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(BnfLot);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Birthday);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeDate);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ModifyDate);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(BnfAddress);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(TransTime);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(TransOperator);
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

		if (FCode.equalsIgnoreCase("IDX"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDX = FValue.trim();
			}
			else
				IDX = null;
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("InputNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InputNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("FillNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FillNo = FValue.trim();
			}
			else
				FillNo = null;
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("BnfType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfType = FValue.trim();
			}
			else
				BnfType = null;
		}
		if (FCode.equalsIgnoreCase("BnfNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfNo = FValue.trim();
			}
			else
				BnfNo = null;
		}
		if (FCode.equalsIgnoreCase("BnfGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfGrade = FValue.trim();
			}
			else
				BnfGrade = null;
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToInsured = FValue.trim();
			}
			else
				RelationToInsured = null;
		}
		if (FCode.equalsIgnoreCase("BnfLot"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfLot = FValue.trim();
			}
			else
				BnfLot = null;
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
		if (FCode.equalsIgnoreCase("Sex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Sex = FValue.trim();
			}
			else
				Sex = null;
		}
		if (FCode.equalsIgnoreCase("Birthday"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Birthday = FValue.trim();
			}
			else
				Birthday = null;
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
			if( FValue != null && !FValue.equals(""))
			{
				MakeDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				ModifyDate = FValue.trim();
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
		if (FCode.equalsIgnoreCase("BnfAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfAddress = FValue.trim();
			}
			else
				BnfAddress = null;
		}
		if (FCode.equalsIgnoreCase("TransDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TransDate = fDate.getDate( FValue );
			}
			else
				TransDate = null;
		}
		if (FCode.equalsIgnoreCase("TransTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransTime = FValue.trim();
			}
			else
				TransTime = null;
		}
		if (FCode.equalsIgnoreCase("TransOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransOperator = FValue.trim();
			}
			else
				TransOperator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBPOBnfBSchema other = (LBPOBnfBSchema)otherObject;
		return
			IDX.equals(other.getIDX())
			&& ContNo.equals(other.getContNo())
			&& InputNo == other.getInputNo()
			&& FillNo.equals(other.getFillNo())
			&& PolNo.equals(other.getPolNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& BnfType.equals(other.getBnfType())
			&& BnfNo.equals(other.getBnfNo())
			&& BnfGrade.equals(other.getBnfGrade())
			&& RelationToInsured.equals(other.getRelationToInsured())
			&& BnfLot.equals(other.getBnfLot())
			&& CustomerNo.equals(other.getCustomerNo())
			&& Name.equals(other.getName())
			&& Sex.equals(other.getSex())
			&& Birthday.equals(other.getBirthday())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& Operator.equals(other.getOperator())
			&& MakeDate.equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyDate.equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BnfAddress.equals(other.getBnfAddress())
			&& fDate.getString(TransDate).equals(other.getTransDate())
			&& TransTime.equals(other.getTransTime())
			&& TransOperator.equals(other.getTransOperator());
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
		if( strFieldName.equals("IDX") ) {
			return 0;
		}
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("InputNo") ) {
			return 2;
		}
		if( strFieldName.equals("FillNo") ) {
			return 3;
		}
		if( strFieldName.equals("PolNo") ) {
			return 4;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 5;
		}
		if( strFieldName.equals("BnfType") ) {
			return 6;
		}
		if( strFieldName.equals("BnfNo") ) {
			return 7;
		}
		if( strFieldName.equals("BnfGrade") ) {
			return 8;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return 9;
		}
		if( strFieldName.equals("BnfLot") ) {
			return 10;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 11;
		}
		if( strFieldName.equals("Name") ) {
			return 12;
		}
		if( strFieldName.equals("Sex") ) {
			return 13;
		}
		if( strFieldName.equals("Birthday") ) {
			return 14;
		}
		if( strFieldName.equals("IDType") ) {
			return 15;
		}
		if( strFieldName.equals("IDNo") ) {
			return 16;
		}
		if( strFieldName.equals("Operator") ) {
			return 17;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 18;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 21;
		}
		if( strFieldName.equals("BnfAddress") ) {
			return 22;
		}
		if( strFieldName.equals("TransDate") ) {
			return 23;
		}
		if( strFieldName.equals("TransTime") ) {
			return 24;
		}
		if( strFieldName.equals("TransOperator") ) {
			return 25;
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
				strFieldName = "IDX";
				break;
			case 1:
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "InputNo";
				break;
			case 3:
				strFieldName = "FillNo";
				break;
			case 4:
				strFieldName = "PolNo";
				break;
			case 5:
				strFieldName = "InsuredNo";
				break;
			case 6:
				strFieldName = "BnfType";
				break;
			case 7:
				strFieldName = "BnfNo";
				break;
			case 8:
				strFieldName = "BnfGrade";
				break;
			case 9:
				strFieldName = "RelationToInsured";
				break;
			case 10:
				strFieldName = "BnfLot";
				break;
			case 11:
				strFieldName = "CustomerNo";
				break;
			case 12:
				strFieldName = "Name";
				break;
			case 13:
				strFieldName = "Sex";
				break;
			case 14:
				strFieldName = "Birthday";
				break;
			case 15:
				strFieldName = "IDType";
				break;
			case 16:
				strFieldName = "IDNo";
				break;
			case 17:
				strFieldName = "Operator";
				break;
			case 18:
				strFieldName = "MakeDate";
				break;
			case 19:
				strFieldName = "MakeTime";
				break;
			case 20:
				strFieldName = "ModifyDate";
				break;
			case 21:
				strFieldName = "ModifyTime";
				break;
			case 22:
				strFieldName = "BnfAddress";
				break;
			case 23:
				strFieldName = "TransDate";
				break;
			case 24:
				strFieldName = "TransTime";
				break;
			case 25:
				strFieldName = "TransOperator";
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
		if( strFieldName.equals("IDX") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FillNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfLot") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Sex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Birthday") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TransTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransOperator") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
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
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
