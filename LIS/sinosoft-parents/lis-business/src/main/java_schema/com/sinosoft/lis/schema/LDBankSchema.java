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
import com.sinosoft.lis.db.LDBankDB;

/*
 * <p>ClassName: LDBankSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDBankSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDBankSchema.class);
	// @Field
	/** 银行编码 */
	private String BankCode;
	/** 银行名称 */
	private String BankName;
	/** 操作员代码 */
	private String Operator;
	/** 机构编码 */
	private String ComCode;
	/** 代理人编码 */
	private String AgentCode;
	/** 银行端代码 */
	private String BankSelfCode;
	/** 户名 */
	private String AccName;
	/** 保险公司帐号 */
	private String AccNo;
	/** 代收awk发送解码文件 */
	private String AgentPaySendF;
	/** 代收发送路径 */
	private String AgentPaySendPath;
	/** 代收awk接收解码文件 */
	private String AgentPayReceiveF;
	/** 代收文件存储路径 */
	private String AgentPaySavePath;
	/** 代收银行端约定的正确返回码 */
	private String AgentPaySuccFlag;
	/** 代收银行端约定的错误返回码 */
	private String AgentPayFailFlag;
	/** 代付awk发送解码文件 */
	private String AgentGetSendF;
	/** 代付发送路径 */
	private String AgentGetSendPath;
	/** 代付awk接收解码文件 */
	private String AgentGetReceiveF;
	/** 代付文件存储路径 */
	private String AgentGetSavePath;
	/** 代付银行端约定的正确返回码 */
	private String AgentGetSuccFlag;
	/** 代付银行端约定的错误返回码 */
	private String AgentGetFailFlag;
	/** 校验awk发送文件 */
	private String ChkSendF;
	/** 校验发送awk路径 */
	private String ChkSendPath;
	/** 校验awk接收文件 */
	private String ChkReceiveF;
	/** 校验接收awk路径 */
	private String ChkReceivePath;
	/** 校验成功标志 */
	private String ChkSuccFlag;
	/** 校验失败标志 */
	private String ChkFailFlag;
	/** 银行回单awk文件 */
	private String BankBackF;
	/** 银行回单awk路径 */
	private String BankBackPath;
	/** 代收文件返回类型 */
	private String AgentPayRFType;
	/** 代付文件返回类型 */
	private String AgentGetRFType;
	/** 银行类型 */
	private String BankType;
	/** 银行总行编码 */
	private String HeadBankCode;
	/** 所在省编码 */
	private String Province;
	/** 所在市编码 */
	private String City;
	/** 所在县编码 */
	private String County;
	/** 是否有效 */
	private String State;

	public static final int FIELDNUM = 36;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDBankSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "BankCode";

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
		LDBankSchema cloned = (LDBankSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		if(aBankCode!=null && aBankCode.length()>10)
			throw new IllegalArgumentException("银行编码BankCode值"+aBankCode+"的长度"+aBankCode.length()+"大于最大值10");
		BankCode = aBankCode;
	}
	public String getBankName()
	{
		return BankName;
	}
	public void setBankName(String aBankName)
	{
		if(aBankName!=null && aBankName.length()>40)
			throw new IllegalArgumentException("银行名称BankName值"+aBankName+"的长度"+aBankName.length()+"大于最大值40");
		BankName = aBankName;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员代码Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
		Operator = aOperator;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>10)
			throw new IllegalArgumentException("机构编码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值10");
		ComCode = aComCode;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		if(aAgentCode!=null && aAgentCode.length()>10)
			throw new IllegalArgumentException("代理人编码AgentCode值"+aAgentCode+"的长度"+aAgentCode.length()+"大于最大值10");
		AgentCode = aAgentCode;
	}
	public String getBankSelfCode()
	{
		return BankSelfCode;
	}
	public void setBankSelfCode(String aBankSelfCode)
	{
		if(aBankSelfCode!=null && aBankSelfCode.length()>10)
			throw new IllegalArgumentException("银行端代码BankSelfCode值"+aBankSelfCode+"的长度"+aBankSelfCode.length()+"大于最大值10");
		BankSelfCode = aBankSelfCode;
	}
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		if(aAccName!=null && aAccName.length()>60)
			throw new IllegalArgumentException("户名AccName值"+aAccName+"的长度"+aAccName.length()+"大于最大值60");
		AccName = aAccName;
	}
	public String getAccNo()
	{
		return AccNo;
	}
	public void setAccNo(String aAccNo)
	{
		if(aAccNo!=null && aAccNo.length()>40)
			throw new IllegalArgumentException("保险公司帐号AccNo值"+aAccNo+"的长度"+aAccNo.length()+"大于最大值40");
		AccNo = aAccNo;
	}
	public String getAgentPaySendF()
	{
		return AgentPaySendF;
	}
	public void setAgentPaySendF(String aAgentPaySendF)
	{
		if(aAgentPaySendF!=null && aAgentPaySendF.length()>200)
			throw new IllegalArgumentException("代收awk发送解码文件AgentPaySendF值"+aAgentPaySendF+"的长度"+aAgentPaySendF.length()+"大于最大值200");
		AgentPaySendF = aAgentPaySendF;
	}
	public String getAgentPaySendPath()
	{
		return AgentPaySendPath;
	}
	public void setAgentPaySendPath(String aAgentPaySendPath)
	{
		if(aAgentPaySendPath!=null && aAgentPaySendPath.length()>200)
			throw new IllegalArgumentException("代收发送路径AgentPaySendPath值"+aAgentPaySendPath+"的长度"+aAgentPaySendPath.length()+"大于最大值200");
		AgentPaySendPath = aAgentPaySendPath;
	}
	public String getAgentPayReceiveF()
	{
		return AgentPayReceiveF;
	}
	public void setAgentPayReceiveF(String aAgentPayReceiveF)
	{
		if(aAgentPayReceiveF!=null && aAgentPayReceiveF.length()>200)
			throw new IllegalArgumentException("代收awk接收解码文件AgentPayReceiveF值"+aAgentPayReceiveF+"的长度"+aAgentPayReceiveF.length()+"大于最大值200");
		AgentPayReceiveF = aAgentPayReceiveF;
	}
	public String getAgentPaySavePath()
	{
		return AgentPaySavePath;
	}
	public void setAgentPaySavePath(String aAgentPaySavePath)
	{
		if(aAgentPaySavePath!=null && aAgentPaySavePath.length()>200)
			throw new IllegalArgumentException("代收文件存储路径AgentPaySavePath值"+aAgentPaySavePath+"的长度"+aAgentPaySavePath.length()+"大于最大值200");
		AgentPaySavePath = aAgentPaySavePath;
	}
	public String getAgentPaySuccFlag()
	{
		return AgentPaySuccFlag;
	}
	public void setAgentPaySuccFlag(String aAgentPaySuccFlag)
	{
		if(aAgentPaySuccFlag!=null && aAgentPaySuccFlag.length()>200)
			throw new IllegalArgumentException("代收银行端约定的正确返回码AgentPaySuccFlag值"+aAgentPaySuccFlag+"的长度"+aAgentPaySuccFlag.length()+"大于最大值200");
		AgentPaySuccFlag = aAgentPaySuccFlag;
	}
	public String getAgentPayFailFlag()
	{
		return AgentPayFailFlag;
	}
	public void setAgentPayFailFlag(String aAgentPayFailFlag)
	{
		if(aAgentPayFailFlag!=null && aAgentPayFailFlag.length()>200)
			throw new IllegalArgumentException("代收银行端约定的错误返回码AgentPayFailFlag值"+aAgentPayFailFlag+"的长度"+aAgentPayFailFlag.length()+"大于最大值200");
		AgentPayFailFlag = aAgentPayFailFlag;
	}
	public String getAgentGetSendF()
	{
		return AgentGetSendF;
	}
	public void setAgentGetSendF(String aAgentGetSendF)
	{
		if(aAgentGetSendF!=null && aAgentGetSendF.length()>200)
			throw new IllegalArgumentException("代付awk发送解码文件AgentGetSendF值"+aAgentGetSendF+"的长度"+aAgentGetSendF.length()+"大于最大值200");
		AgentGetSendF = aAgentGetSendF;
	}
	public String getAgentGetSendPath()
	{
		return AgentGetSendPath;
	}
	public void setAgentGetSendPath(String aAgentGetSendPath)
	{
		if(aAgentGetSendPath!=null && aAgentGetSendPath.length()>200)
			throw new IllegalArgumentException("代付发送路径AgentGetSendPath值"+aAgentGetSendPath+"的长度"+aAgentGetSendPath.length()+"大于最大值200");
		AgentGetSendPath = aAgentGetSendPath;
	}
	public String getAgentGetReceiveF()
	{
		return AgentGetReceiveF;
	}
	public void setAgentGetReceiveF(String aAgentGetReceiveF)
	{
		if(aAgentGetReceiveF!=null && aAgentGetReceiveF.length()>200)
			throw new IllegalArgumentException("代付awk接收解码文件AgentGetReceiveF值"+aAgentGetReceiveF+"的长度"+aAgentGetReceiveF.length()+"大于最大值200");
		AgentGetReceiveF = aAgentGetReceiveF;
	}
	public String getAgentGetSavePath()
	{
		return AgentGetSavePath;
	}
	public void setAgentGetSavePath(String aAgentGetSavePath)
	{
		if(aAgentGetSavePath!=null && aAgentGetSavePath.length()>200)
			throw new IllegalArgumentException("代付文件存储路径AgentGetSavePath值"+aAgentGetSavePath+"的长度"+aAgentGetSavePath.length()+"大于最大值200");
		AgentGetSavePath = aAgentGetSavePath;
	}
	public String getAgentGetSuccFlag()
	{
		return AgentGetSuccFlag;
	}
	public void setAgentGetSuccFlag(String aAgentGetSuccFlag)
	{
		if(aAgentGetSuccFlag!=null && aAgentGetSuccFlag.length()>200)
			throw new IllegalArgumentException("代付银行端约定的正确返回码AgentGetSuccFlag值"+aAgentGetSuccFlag+"的长度"+aAgentGetSuccFlag.length()+"大于最大值200");
		AgentGetSuccFlag = aAgentGetSuccFlag;
	}
	public String getAgentGetFailFlag()
	{
		return AgentGetFailFlag;
	}
	public void setAgentGetFailFlag(String aAgentGetFailFlag)
	{
		if(aAgentGetFailFlag!=null && aAgentGetFailFlag.length()>200)
			throw new IllegalArgumentException("代付银行端约定的错误返回码AgentGetFailFlag值"+aAgentGetFailFlag+"的长度"+aAgentGetFailFlag.length()+"大于最大值200");
		AgentGetFailFlag = aAgentGetFailFlag;
	}
	public String getChkSendF()
	{
		return ChkSendF;
	}
	public void setChkSendF(String aChkSendF)
	{
		if(aChkSendF!=null && aChkSendF.length()>200)
			throw new IllegalArgumentException("校验awk发送文件ChkSendF值"+aChkSendF+"的长度"+aChkSendF.length()+"大于最大值200");
		ChkSendF = aChkSendF;
	}
	public String getChkSendPath()
	{
		return ChkSendPath;
	}
	public void setChkSendPath(String aChkSendPath)
	{
		if(aChkSendPath!=null && aChkSendPath.length()>200)
			throw new IllegalArgumentException("校验发送awk路径ChkSendPath值"+aChkSendPath+"的长度"+aChkSendPath.length()+"大于最大值200");
		ChkSendPath = aChkSendPath;
	}
	public String getChkReceiveF()
	{
		return ChkReceiveF;
	}
	public void setChkReceiveF(String aChkReceiveF)
	{
		if(aChkReceiveF!=null && aChkReceiveF.length()>200)
			throw new IllegalArgumentException("校验awk接收文件ChkReceiveF值"+aChkReceiveF+"的长度"+aChkReceiveF.length()+"大于最大值200");
		ChkReceiveF = aChkReceiveF;
	}
	public String getChkReceivePath()
	{
		return ChkReceivePath;
	}
	public void setChkReceivePath(String aChkReceivePath)
	{
		if(aChkReceivePath!=null && aChkReceivePath.length()>200)
			throw new IllegalArgumentException("校验接收awk路径ChkReceivePath值"+aChkReceivePath+"的长度"+aChkReceivePath.length()+"大于最大值200");
		ChkReceivePath = aChkReceivePath;
	}
	public String getChkSuccFlag()
	{
		return ChkSuccFlag;
	}
	public void setChkSuccFlag(String aChkSuccFlag)
	{
		if(aChkSuccFlag!=null && aChkSuccFlag.length()>200)
			throw new IllegalArgumentException("校验成功标志ChkSuccFlag值"+aChkSuccFlag+"的长度"+aChkSuccFlag.length()+"大于最大值200");
		ChkSuccFlag = aChkSuccFlag;
	}
	public String getChkFailFlag()
	{
		return ChkFailFlag;
	}
	public void setChkFailFlag(String aChkFailFlag)
	{
		if(aChkFailFlag!=null && aChkFailFlag.length()>200)
			throw new IllegalArgumentException("校验失败标志ChkFailFlag值"+aChkFailFlag+"的长度"+aChkFailFlag.length()+"大于最大值200");
		ChkFailFlag = aChkFailFlag;
	}
	public String getBankBackF()
	{
		return BankBackF;
	}
	public void setBankBackF(String aBankBackF)
	{
		if(aBankBackF!=null && aBankBackF.length()>200)
			throw new IllegalArgumentException("银行回单awk文件BankBackF值"+aBankBackF+"的长度"+aBankBackF.length()+"大于最大值200");
		BankBackF = aBankBackF;
	}
	public String getBankBackPath()
	{
		return BankBackPath;
	}
	public void setBankBackPath(String aBankBackPath)
	{
		if(aBankBackPath!=null && aBankBackPath.length()>200)
			throw new IllegalArgumentException("银行回单awk路径BankBackPath值"+aBankBackPath+"的长度"+aBankBackPath.length()+"大于最大值200");
		BankBackPath = aBankBackPath;
	}
	public String getAgentPayRFType()
	{
		return AgentPayRFType;
	}
	public void setAgentPayRFType(String aAgentPayRFType)
	{
		if(aAgentPayRFType!=null && aAgentPayRFType.length()>1)
			throw new IllegalArgumentException("代收文件返回类型AgentPayRFType值"+aAgentPayRFType+"的长度"+aAgentPayRFType.length()+"大于最大值1");
		AgentPayRFType = aAgentPayRFType;
	}
	public String getAgentGetRFType()
	{
		return AgentGetRFType;
	}
	public void setAgentGetRFType(String aAgentGetRFType)
	{
		if(aAgentGetRFType!=null && aAgentGetRFType.length()>1)
			throw new IllegalArgumentException("代付文件返回类型AgentGetRFType值"+aAgentGetRFType+"的长度"+aAgentGetRFType.length()+"大于最大值1");
		AgentGetRFType = aAgentGetRFType;
	}
	public String getBankType()
	{
		return BankType;
	}
	public void setBankType(String aBankType)
	{
		if(aBankType!=null && aBankType.length()>4)
			throw new IllegalArgumentException("银行类型BankType值"+aBankType+"的长度"+aBankType.length()+"大于最大值4");
		BankType = aBankType;
	}
	public String getHeadBankCode()
	{
		return HeadBankCode;
	}
	public void setHeadBankCode(String aHeadBankCode)
	{
		if(aHeadBankCode!=null && aHeadBankCode.length()>30)
			throw new IllegalArgumentException("银行总行编码HeadBankCode值"+aHeadBankCode+"的长度"+aHeadBankCode.length()+"大于最大值30");
		HeadBankCode = aHeadBankCode;
	}
	public String getProvince()
	{
		return Province;
	}
	public void setProvince(String aProvince)
	{
		if(aProvince!=null && aProvince.length()>30)
			throw new IllegalArgumentException("所在省编码Province值"+aProvince+"的长度"+aProvince.length()+"大于最大值30");
		Province = aProvince;
	}
	public String getCity()
	{
		return City;
	}
	public void setCity(String aCity)
	{
		if(aCity!=null && aCity.length()>30)
			throw new IllegalArgumentException("所在市编码City值"+aCity+"的长度"+aCity.length()+"大于最大值30");
		City = aCity;
	}
	public String getCounty()
	{
		return County;
	}
	public void setCounty(String aCounty)
	{
		if(aCounty!=null && aCounty.length()>30)
			throw new IllegalArgumentException("所在县编码County值"+aCounty+"的长度"+aCounty.length()+"大于最大值30");
		County = aCounty;
	}
	/**
	* 0-否，1-是
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>2)
			throw new IllegalArgumentException("是否有效State值"+aState+"的长度"+aState.length()+"大于最大值2");
		State = aState;
	}

	/**
	* 使用另外一个 LDBankSchema 对象给 Schema 赋值
	* @param: aLDBankSchema LDBankSchema
	**/
	public void setSchema(LDBankSchema aLDBankSchema)
	{
		this.BankCode = aLDBankSchema.getBankCode();
		this.BankName = aLDBankSchema.getBankName();
		this.Operator = aLDBankSchema.getOperator();
		this.ComCode = aLDBankSchema.getComCode();
		this.AgentCode = aLDBankSchema.getAgentCode();
		this.BankSelfCode = aLDBankSchema.getBankSelfCode();
		this.AccName = aLDBankSchema.getAccName();
		this.AccNo = aLDBankSchema.getAccNo();
		this.AgentPaySendF = aLDBankSchema.getAgentPaySendF();
		this.AgentPaySendPath = aLDBankSchema.getAgentPaySendPath();
		this.AgentPayReceiveF = aLDBankSchema.getAgentPayReceiveF();
		this.AgentPaySavePath = aLDBankSchema.getAgentPaySavePath();
		this.AgentPaySuccFlag = aLDBankSchema.getAgentPaySuccFlag();
		this.AgentPayFailFlag = aLDBankSchema.getAgentPayFailFlag();
		this.AgentGetSendF = aLDBankSchema.getAgentGetSendF();
		this.AgentGetSendPath = aLDBankSchema.getAgentGetSendPath();
		this.AgentGetReceiveF = aLDBankSchema.getAgentGetReceiveF();
		this.AgentGetSavePath = aLDBankSchema.getAgentGetSavePath();
		this.AgentGetSuccFlag = aLDBankSchema.getAgentGetSuccFlag();
		this.AgentGetFailFlag = aLDBankSchema.getAgentGetFailFlag();
		this.ChkSendF = aLDBankSchema.getChkSendF();
		this.ChkSendPath = aLDBankSchema.getChkSendPath();
		this.ChkReceiveF = aLDBankSchema.getChkReceiveF();
		this.ChkReceivePath = aLDBankSchema.getChkReceivePath();
		this.ChkSuccFlag = aLDBankSchema.getChkSuccFlag();
		this.ChkFailFlag = aLDBankSchema.getChkFailFlag();
		this.BankBackF = aLDBankSchema.getBankBackF();
		this.BankBackPath = aLDBankSchema.getBankBackPath();
		this.AgentPayRFType = aLDBankSchema.getAgentPayRFType();
		this.AgentGetRFType = aLDBankSchema.getAgentGetRFType();
		this.BankType = aLDBankSchema.getBankType();
		this.HeadBankCode = aLDBankSchema.getHeadBankCode();
		this.Province = aLDBankSchema.getProvince();
		this.City = aLDBankSchema.getCity();
		this.County = aLDBankSchema.getCounty();
		this.State = aLDBankSchema.getState();
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

			if( rs.getString("BankName") == null )
				this.BankName = null;
			else
				this.BankName = rs.getString("BankName").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("BankSelfCode") == null )
				this.BankSelfCode = null;
			else
				this.BankSelfCode = rs.getString("BankSelfCode").trim();

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			if( rs.getString("AccNo") == null )
				this.AccNo = null;
			else
				this.AccNo = rs.getString("AccNo").trim();

			if( rs.getString("AgentPaySendF") == null )
				this.AgentPaySendF = null;
			else
				this.AgentPaySendF = rs.getString("AgentPaySendF").trim();

			if( rs.getString("AgentPaySendPath") == null )
				this.AgentPaySendPath = null;
			else
				this.AgentPaySendPath = rs.getString("AgentPaySendPath").trim();

			if( rs.getString("AgentPayReceiveF") == null )
				this.AgentPayReceiveF = null;
			else
				this.AgentPayReceiveF = rs.getString("AgentPayReceiveF").trim();

			if( rs.getString("AgentPaySavePath") == null )
				this.AgentPaySavePath = null;
			else
				this.AgentPaySavePath = rs.getString("AgentPaySavePath").trim();

			if( rs.getString("AgentPaySuccFlag") == null )
				this.AgentPaySuccFlag = null;
			else
				this.AgentPaySuccFlag = rs.getString("AgentPaySuccFlag").trim();

			if( rs.getString("AgentPayFailFlag") == null )
				this.AgentPayFailFlag = null;
			else
				this.AgentPayFailFlag = rs.getString("AgentPayFailFlag").trim();

			if( rs.getString("AgentGetSendF") == null )
				this.AgentGetSendF = null;
			else
				this.AgentGetSendF = rs.getString("AgentGetSendF").trim();

			if( rs.getString("AgentGetSendPath") == null )
				this.AgentGetSendPath = null;
			else
				this.AgentGetSendPath = rs.getString("AgentGetSendPath").trim();

			if( rs.getString("AgentGetReceiveF") == null )
				this.AgentGetReceiveF = null;
			else
				this.AgentGetReceiveF = rs.getString("AgentGetReceiveF").trim();

			if( rs.getString("AgentGetSavePath") == null )
				this.AgentGetSavePath = null;
			else
				this.AgentGetSavePath = rs.getString("AgentGetSavePath").trim();

			if( rs.getString("AgentGetSuccFlag") == null )
				this.AgentGetSuccFlag = null;
			else
				this.AgentGetSuccFlag = rs.getString("AgentGetSuccFlag").trim();

			if( rs.getString("AgentGetFailFlag") == null )
				this.AgentGetFailFlag = null;
			else
				this.AgentGetFailFlag = rs.getString("AgentGetFailFlag").trim();

			if( rs.getString("ChkSendF") == null )
				this.ChkSendF = null;
			else
				this.ChkSendF = rs.getString("ChkSendF").trim();

			if( rs.getString("ChkSendPath") == null )
				this.ChkSendPath = null;
			else
				this.ChkSendPath = rs.getString("ChkSendPath").trim();

			if( rs.getString("ChkReceiveF") == null )
				this.ChkReceiveF = null;
			else
				this.ChkReceiveF = rs.getString("ChkReceiveF").trim();

			if( rs.getString("ChkReceivePath") == null )
				this.ChkReceivePath = null;
			else
				this.ChkReceivePath = rs.getString("ChkReceivePath").trim();

			if( rs.getString("ChkSuccFlag") == null )
				this.ChkSuccFlag = null;
			else
				this.ChkSuccFlag = rs.getString("ChkSuccFlag").trim();

			if( rs.getString("ChkFailFlag") == null )
				this.ChkFailFlag = null;
			else
				this.ChkFailFlag = rs.getString("ChkFailFlag").trim();

			if( rs.getString("BankBackF") == null )
				this.BankBackF = null;
			else
				this.BankBackF = rs.getString("BankBackF").trim();

			if( rs.getString("BankBackPath") == null )
				this.BankBackPath = null;
			else
				this.BankBackPath = rs.getString("BankBackPath").trim();

			if( rs.getString("AgentPayRFType") == null )
				this.AgentPayRFType = null;
			else
				this.AgentPayRFType = rs.getString("AgentPayRFType").trim();

			if( rs.getString("AgentGetRFType") == null )
				this.AgentGetRFType = null;
			else
				this.AgentGetRFType = rs.getString("AgentGetRFType").trim();

			if( rs.getString("BankType") == null )
				this.BankType = null;
			else
				this.BankType = rs.getString("BankType").trim();

			if( rs.getString("HeadBankCode") == null )
				this.HeadBankCode = null;
			else
				this.HeadBankCode = rs.getString("HeadBankCode").trim();

			if( rs.getString("Province") == null )
				this.Province = null;
			else
				this.Province = rs.getString("Province").trim();

			if( rs.getString("City") == null )
				this.City = null;
			else
				this.City = rs.getString("City").trim();

			if( rs.getString("County") == null )
				this.County = null;
			else
				this.County = rs.getString("County").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDBank表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDBankSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDBankSchema getSchema()
	{
		LDBankSchema aLDBankSchema = new LDBankSchema();
		aLDBankSchema.setSchema(this);
		return aLDBankSchema;
	}

	public LDBankDB getDB()
	{
		LDBankDB aDBOper = new LDBankDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDBank描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankSelfCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentPaySendF)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentPaySendPath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentPayReceiveF)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentPaySavePath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentPaySuccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentPayFailFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGetSendF)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGetSendPath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGetReceiveF)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGetSavePath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGetSuccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGetFailFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChkSendF)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChkSendPath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChkReceiveF)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChkReceivePath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChkSuccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChkFailFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankBackF)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankBackPath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentPayRFType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGetRFType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HeadBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Province)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(City)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(County)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDBank>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BankName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BankSelfCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AgentPaySendF = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AgentPaySendPath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AgentPayReceiveF = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AgentPaySavePath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AgentPaySuccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AgentPayFailFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AgentGetSendF = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AgentGetSendPath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AgentGetReceiveF = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AgentGetSavePath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AgentGetSuccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			AgentGetFailFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ChkSendF = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ChkSendPath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ChkReceiveF = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ChkReceivePath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ChkSuccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ChkFailFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			BankBackF = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			BankBackPath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			AgentPayRFType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			AgentGetRFType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			BankType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			HeadBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Province = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			City = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			County = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDBankSchema";
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
		if (FCode.equalsIgnoreCase("BankName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankName));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("BankSelfCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankSelfCode));
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equalsIgnoreCase("AccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccNo));
		}
		if (FCode.equalsIgnoreCase("AgentPaySendF"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentPaySendF));
		}
		if (FCode.equalsIgnoreCase("AgentPaySendPath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentPaySendPath));
		}
		if (FCode.equalsIgnoreCase("AgentPayReceiveF"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentPayReceiveF));
		}
		if (FCode.equalsIgnoreCase("AgentPaySavePath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentPaySavePath));
		}
		if (FCode.equalsIgnoreCase("AgentPaySuccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentPaySuccFlag));
		}
		if (FCode.equalsIgnoreCase("AgentPayFailFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentPayFailFlag));
		}
		if (FCode.equalsIgnoreCase("AgentGetSendF"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGetSendF));
		}
		if (FCode.equalsIgnoreCase("AgentGetSendPath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGetSendPath));
		}
		if (FCode.equalsIgnoreCase("AgentGetReceiveF"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGetReceiveF));
		}
		if (FCode.equalsIgnoreCase("AgentGetSavePath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGetSavePath));
		}
		if (FCode.equalsIgnoreCase("AgentGetSuccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGetSuccFlag));
		}
		if (FCode.equalsIgnoreCase("AgentGetFailFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGetFailFlag));
		}
		if (FCode.equalsIgnoreCase("ChkSendF"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChkSendF));
		}
		if (FCode.equalsIgnoreCase("ChkSendPath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChkSendPath));
		}
		if (FCode.equalsIgnoreCase("ChkReceiveF"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChkReceiveF));
		}
		if (FCode.equalsIgnoreCase("ChkReceivePath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChkReceivePath));
		}
		if (FCode.equalsIgnoreCase("ChkSuccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChkSuccFlag));
		}
		if (FCode.equalsIgnoreCase("ChkFailFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChkFailFlag));
		}
		if (FCode.equalsIgnoreCase("BankBackF"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankBackF));
		}
		if (FCode.equalsIgnoreCase("BankBackPath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankBackPath));
		}
		if (FCode.equalsIgnoreCase("AgentPayRFType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentPayRFType));
		}
		if (FCode.equalsIgnoreCase("AgentGetRFType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGetRFType));
		}
		if (FCode.equalsIgnoreCase("BankType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankType));
		}
		if (FCode.equalsIgnoreCase("HeadBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HeadBankCode));
		}
		if (FCode.equalsIgnoreCase("Province"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Province));
		}
		if (FCode.equalsIgnoreCase("City"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(City));
		}
		if (FCode.equalsIgnoreCase("County"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(County));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(BankName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BankSelfCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AccNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AgentPaySendF);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AgentPaySendPath);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AgentPayReceiveF);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AgentPaySavePath);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AgentPaySuccFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AgentPayFailFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AgentGetSendF);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AgentGetSendPath);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AgentGetReceiveF);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AgentGetSavePath);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AgentGetSuccFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AgentGetFailFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ChkSendF);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ChkSendPath);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ChkReceiveF);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ChkReceivePath);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ChkSuccFlag);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ChkFailFlag);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(BankBackF);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(BankBackPath);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(AgentPayRFType);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(AgentGetRFType);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(BankType);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(HeadBankCode);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Province);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(City);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(County);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(State);
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
		if (FCode.equalsIgnoreCase("BankName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankName = FValue.trim();
			}
			else
				BankName = null;
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
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("BankSelfCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankSelfCode = FValue.trim();
			}
			else
				BankSelfCode = null;
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccName = FValue.trim();
			}
			else
				AccName = null;
		}
		if (FCode.equalsIgnoreCase("AccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccNo = FValue.trim();
			}
			else
				AccNo = null;
		}
		if (FCode.equalsIgnoreCase("AgentPaySendF"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentPaySendF = FValue.trim();
			}
			else
				AgentPaySendF = null;
		}
		if (FCode.equalsIgnoreCase("AgentPaySendPath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentPaySendPath = FValue.trim();
			}
			else
				AgentPaySendPath = null;
		}
		if (FCode.equalsIgnoreCase("AgentPayReceiveF"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentPayReceiveF = FValue.trim();
			}
			else
				AgentPayReceiveF = null;
		}
		if (FCode.equalsIgnoreCase("AgentPaySavePath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentPaySavePath = FValue.trim();
			}
			else
				AgentPaySavePath = null;
		}
		if (FCode.equalsIgnoreCase("AgentPaySuccFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentPaySuccFlag = FValue.trim();
			}
			else
				AgentPaySuccFlag = null;
		}
		if (FCode.equalsIgnoreCase("AgentPayFailFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentPayFailFlag = FValue.trim();
			}
			else
				AgentPayFailFlag = null;
		}
		if (FCode.equalsIgnoreCase("AgentGetSendF"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGetSendF = FValue.trim();
			}
			else
				AgentGetSendF = null;
		}
		if (FCode.equalsIgnoreCase("AgentGetSendPath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGetSendPath = FValue.trim();
			}
			else
				AgentGetSendPath = null;
		}
		if (FCode.equalsIgnoreCase("AgentGetReceiveF"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGetReceiveF = FValue.trim();
			}
			else
				AgentGetReceiveF = null;
		}
		if (FCode.equalsIgnoreCase("AgentGetSavePath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGetSavePath = FValue.trim();
			}
			else
				AgentGetSavePath = null;
		}
		if (FCode.equalsIgnoreCase("AgentGetSuccFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGetSuccFlag = FValue.trim();
			}
			else
				AgentGetSuccFlag = null;
		}
		if (FCode.equalsIgnoreCase("AgentGetFailFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGetFailFlag = FValue.trim();
			}
			else
				AgentGetFailFlag = null;
		}
		if (FCode.equalsIgnoreCase("ChkSendF"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChkSendF = FValue.trim();
			}
			else
				ChkSendF = null;
		}
		if (FCode.equalsIgnoreCase("ChkSendPath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChkSendPath = FValue.trim();
			}
			else
				ChkSendPath = null;
		}
		if (FCode.equalsIgnoreCase("ChkReceiveF"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChkReceiveF = FValue.trim();
			}
			else
				ChkReceiveF = null;
		}
		if (FCode.equalsIgnoreCase("ChkReceivePath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChkReceivePath = FValue.trim();
			}
			else
				ChkReceivePath = null;
		}
		if (FCode.equalsIgnoreCase("ChkSuccFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChkSuccFlag = FValue.trim();
			}
			else
				ChkSuccFlag = null;
		}
		if (FCode.equalsIgnoreCase("ChkFailFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChkFailFlag = FValue.trim();
			}
			else
				ChkFailFlag = null;
		}
		if (FCode.equalsIgnoreCase("BankBackF"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankBackF = FValue.trim();
			}
			else
				BankBackF = null;
		}
		if (FCode.equalsIgnoreCase("BankBackPath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankBackPath = FValue.trim();
			}
			else
				BankBackPath = null;
		}
		if (FCode.equalsIgnoreCase("AgentPayRFType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentPayRFType = FValue.trim();
			}
			else
				AgentPayRFType = null;
		}
		if (FCode.equalsIgnoreCase("AgentGetRFType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGetRFType = FValue.trim();
			}
			else
				AgentGetRFType = null;
		}
		if (FCode.equalsIgnoreCase("BankType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankType = FValue.trim();
			}
			else
				BankType = null;
		}
		if (FCode.equalsIgnoreCase("HeadBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HeadBankCode = FValue.trim();
			}
			else
				HeadBankCode = null;
		}
		if (FCode.equalsIgnoreCase("Province"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Province = FValue.trim();
			}
			else
				Province = null;
		}
		if (FCode.equalsIgnoreCase("City"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				City = FValue.trim();
			}
			else
				City = null;
		}
		if (FCode.equalsIgnoreCase("County"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				County = FValue.trim();
			}
			else
				County = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDBankSchema other = (LDBankSchema)otherObject;
		return
			BankCode.equals(other.getBankCode())
			&& BankName.equals(other.getBankName())
			&& Operator.equals(other.getOperator())
			&& ComCode.equals(other.getComCode())
			&& AgentCode.equals(other.getAgentCode())
			&& BankSelfCode.equals(other.getBankSelfCode())
			&& AccName.equals(other.getAccName())
			&& AccNo.equals(other.getAccNo())
			&& AgentPaySendF.equals(other.getAgentPaySendF())
			&& AgentPaySendPath.equals(other.getAgentPaySendPath())
			&& AgentPayReceiveF.equals(other.getAgentPayReceiveF())
			&& AgentPaySavePath.equals(other.getAgentPaySavePath())
			&& AgentPaySuccFlag.equals(other.getAgentPaySuccFlag())
			&& AgentPayFailFlag.equals(other.getAgentPayFailFlag())
			&& AgentGetSendF.equals(other.getAgentGetSendF())
			&& AgentGetSendPath.equals(other.getAgentGetSendPath())
			&& AgentGetReceiveF.equals(other.getAgentGetReceiveF())
			&& AgentGetSavePath.equals(other.getAgentGetSavePath())
			&& AgentGetSuccFlag.equals(other.getAgentGetSuccFlag())
			&& AgentGetFailFlag.equals(other.getAgentGetFailFlag())
			&& ChkSendF.equals(other.getChkSendF())
			&& ChkSendPath.equals(other.getChkSendPath())
			&& ChkReceiveF.equals(other.getChkReceiveF())
			&& ChkReceivePath.equals(other.getChkReceivePath())
			&& ChkSuccFlag.equals(other.getChkSuccFlag())
			&& ChkFailFlag.equals(other.getChkFailFlag())
			&& BankBackF.equals(other.getBankBackF())
			&& BankBackPath.equals(other.getBankBackPath())
			&& AgentPayRFType.equals(other.getAgentPayRFType())
			&& AgentGetRFType.equals(other.getAgentGetRFType())
			&& BankType.equals(other.getBankType())
			&& HeadBankCode.equals(other.getHeadBankCode())
			&& Province.equals(other.getProvince())
			&& City.equals(other.getCity())
			&& County.equals(other.getCounty())
			&& State.equals(other.getState());
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
		if( strFieldName.equals("BankName") ) {
			return 1;
		}
		if( strFieldName.equals("Operator") ) {
			return 2;
		}
		if( strFieldName.equals("ComCode") ) {
			return 3;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 4;
		}
		if( strFieldName.equals("BankSelfCode") ) {
			return 5;
		}
		if( strFieldName.equals("AccName") ) {
			return 6;
		}
		if( strFieldName.equals("AccNo") ) {
			return 7;
		}
		if( strFieldName.equals("AgentPaySendF") ) {
			return 8;
		}
		if( strFieldName.equals("AgentPaySendPath") ) {
			return 9;
		}
		if( strFieldName.equals("AgentPayReceiveF") ) {
			return 10;
		}
		if( strFieldName.equals("AgentPaySavePath") ) {
			return 11;
		}
		if( strFieldName.equals("AgentPaySuccFlag") ) {
			return 12;
		}
		if( strFieldName.equals("AgentPayFailFlag") ) {
			return 13;
		}
		if( strFieldName.equals("AgentGetSendF") ) {
			return 14;
		}
		if( strFieldName.equals("AgentGetSendPath") ) {
			return 15;
		}
		if( strFieldName.equals("AgentGetReceiveF") ) {
			return 16;
		}
		if( strFieldName.equals("AgentGetSavePath") ) {
			return 17;
		}
		if( strFieldName.equals("AgentGetSuccFlag") ) {
			return 18;
		}
		if( strFieldName.equals("AgentGetFailFlag") ) {
			return 19;
		}
		if( strFieldName.equals("ChkSendF") ) {
			return 20;
		}
		if( strFieldName.equals("ChkSendPath") ) {
			return 21;
		}
		if( strFieldName.equals("ChkReceiveF") ) {
			return 22;
		}
		if( strFieldName.equals("ChkReceivePath") ) {
			return 23;
		}
		if( strFieldName.equals("ChkSuccFlag") ) {
			return 24;
		}
		if( strFieldName.equals("ChkFailFlag") ) {
			return 25;
		}
		if( strFieldName.equals("BankBackF") ) {
			return 26;
		}
		if( strFieldName.equals("BankBackPath") ) {
			return 27;
		}
		if( strFieldName.equals("AgentPayRFType") ) {
			return 28;
		}
		if( strFieldName.equals("AgentGetRFType") ) {
			return 29;
		}
		if( strFieldName.equals("BankType") ) {
			return 30;
		}
		if( strFieldName.equals("HeadBankCode") ) {
			return 31;
		}
		if( strFieldName.equals("Province") ) {
			return 32;
		}
		if( strFieldName.equals("City") ) {
			return 33;
		}
		if( strFieldName.equals("County") ) {
			return 34;
		}
		if( strFieldName.equals("State") ) {
			return 35;
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
				strFieldName = "BankName";
				break;
			case 2:
				strFieldName = "Operator";
				break;
			case 3:
				strFieldName = "ComCode";
				break;
			case 4:
				strFieldName = "AgentCode";
				break;
			case 5:
				strFieldName = "BankSelfCode";
				break;
			case 6:
				strFieldName = "AccName";
				break;
			case 7:
				strFieldName = "AccNo";
				break;
			case 8:
				strFieldName = "AgentPaySendF";
				break;
			case 9:
				strFieldName = "AgentPaySendPath";
				break;
			case 10:
				strFieldName = "AgentPayReceiveF";
				break;
			case 11:
				strFieldName = "AgentPaySavePath";
				break;
			case 12:
				strFieldName = "AgentPaySuccFlag";
				break;
			case 13:
				strFieldName = "AgentPayFailFlag";
				break;
			case 14:
				strFieldName = "AgentGetSendF";
				break;
			case 15:
				strFieldName = "AgentGetSendPath";
				break;
			case 16:
				strFieldName = "AgentGetReceiveF";
				break;
			case 17:
				strFieldName = "AgentGetSavePath";
				break;
			case 18:
				strFieldName = "AgentGetSuccFlag";
				break;
			case 19:
				strFieldName = "AgentGetFailFlag";
				break;
			case 20:
				strFieldName = "ChkSendF";
				break;
			case 21:
				strFieldName = "ChkSendPath";
				break;
			case 22:
				strFieldName = "ChkReceiveF";
				break;
			case 23:
				strFieldName = "ChkReceivePath";
				break;
			case 24:
				strFieldName = "ChkSuccFlag";
				break;
			case 25:
				strFieldName = "ChkFailFlag";
				break;
			case 26:
				strFieldName = "BankBackF";
				break;
			case 27:
				strFieldName = "BankBackPath";
				break;
			case 28:
				strFieldName = "AgentPayRFType";
				break;
			case 29:
				strFieldName = "AgentGetRFType";
				break;
			case 30:
				strFieldName = "BankType";
				break;
			case 31:
				strFieldName = "HeadBankCode";
				break;
			case 32:
				strFieldName = "Province";
				break;
			case 33:
				strFieldName = "City";
				break;
			case 34:
				strFieldName = "County";
				break;
			case 35:
				strFieldName = "State";
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
		if( strFieldName.equals("BankName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankSelfCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentPaySendF") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentPaySendPath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentPayReceiveF") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentPaySavePath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentPaySuccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentPayFailFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGetSendF") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGetSendPath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGetReceiveF") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGetSavePath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGetSuccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGetFailFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChkSendF") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChkSendPath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChkReceiveF") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChkReceivePath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChkSuccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChkFailFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankBackF") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankBackPath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentPayRFType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGetRFType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HeadBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Province") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("City") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("County") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
