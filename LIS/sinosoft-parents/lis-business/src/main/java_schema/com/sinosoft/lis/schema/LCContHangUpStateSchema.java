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
import com.sinosoft.lis.db.LCContHangUpStateDB;

/*
 * <p>ClassName: LCContHangUpStateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCContHangUpStateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCContHangUpStateSchema.class);
	// @Field
	/** 合同号 */
	private String ContNo;
	/** 被保险人号码 */
	private String InsuredNo;
	/** 保单险种号 */
	private String PolNo;
	/** 挂起类型 */
	private String HangUpType;
	/** 挂起号码 */
	private String HangUpNo;
	/** 承保挂起标志 */
	private String NBFlag;
	/** 保全挂起标志 */
	private String PosFlag;
	/** 理赔挂起标志 */
	private String ClaimFlag;
	/** 渠道挂起标志 */
	private String AgentFlag;
	/** 续期挂起标志 */
	private String RNFlag;
	/** 备用标志1 */
	private String StandFlag1;
	/** 备用标志2 */
	private String StandFlag2;
	/** 备用标志3 */
	private String StandFlag3;
	/** 备注 */
	private String Remark;
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
	/** 备用标志4 */
	private String StandFlag4;
	/** 备用标志5 */
	private String StandFlag5;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCContHangUpStateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "ContNo";
		pk[1] = "InsuredNo";
		pk[2] = "PolNo";
		pk[3] = "HangUpType";

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
		LCContHangUpStateSchema cloned = (LCContHangUpStateSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("合同号ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	/**
	* 如果不挂起险种，则，该字段置为6个0
	*/
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>24)
			throw new IllegalArgumentException("被保险人号码InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值24");
		InsuredNo = aInsuredNo;
	}
	/**
	* 如果不挂起险种，则，该字段置为6个0
	*/
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		if(aPolNo!=null && aPolNo.length()>20)
			throw new IllegalArgumentException("保单险种号PolNo值"+aPolNo+"的长度"+aPolNo.length()+"大于最大值20");
		PolNo = aPolNo;
	}
	/**
	* 1：新契约<p>
	* 2：保全<p>
	* 3：续期<p>
	* 4：理赔<p>
	* 5：渠道
	*/
	public String getHangUpType()
	{
		return HangUpType;
	}
	public void setHangUpType(String aHangUpType)
	{
		if(aHangUpType!=null && aHangUpType.length()>2)
			throw new IllegalArgumentException("挂起类型HangUpType值"+aHangUpType+"的长度"+aHangUpType.length()+"大于最大值2");
		HangUpType = aHangUpType;
	}
	/**
	* 保全挂起：保全受理号<p>
	* 理赔挂起：赔案号
	*/
	public String getHangUpNo()
	{
		return HangUpNo;
	}
	public void setHangUpNo(String aHangUpNo)
	{
		if(aHangUpNo!=null && aHangUpNo.length()>20)
			throw new IllegalArgumentException("挂起号码HangUpNo值"+aHangUpNo+"的长度"+aHangUpNo.length()+"大于最大值20");
		HangUpNo = aHangUpNo;
	}
	/**
	* 0未挂起<p>
	* 1已挂起
	*/
	public String getNBFlag()
	{
		return NBFlag;
	}
	public void setNBFlag(String aNBFlag)
	{
		if(aNBFlag!=null && aNBFlag.length()>6)
			throw new IllegalArgumentException("承保挂起标志NBFlag值"+aNBFlag+"的长度"+aNBFlag.length()+"大于最大值6");
		NBFlag = aNBFlag;
	}
	/**
	* 0未挂起<p>
	* 1已挂起
	*/
	public String getPosFlag()
	{
		return PosFlag;
	}
	public void setPosFlag(String aPosFlag)
	{
		if(aPosFlag!=null && aPosFlag.length()>6)
			throw new IllegalArgumentException("保全挂起标志PosFlag值"+aPosFlag+"的长度"+aPosFlag.length()+"大于最大值6");
		PosFlag = aPosFlag;
	}
	/**
	* 0未挂起<p>
	* 1已挂起
	*/
	public String getClaimFlag()
	{
		return ClaimFlag;
	}
	public void setClaimFlag(String aClaimFlag)
	{
		if(aClaimFlag!=null && aClaimFlag.length()>6)
			throw new IllegalArgumentException("理赔挂起标志ClaimFlag值"+aClaimFlag+"的长度"+aClaimFlag.length()+"大于最大值6");
		ClaimFlag = aClaimFlag;
	}
	/**
	* 0未挂起<p>
	* 1已挂起
	*/
	public String getAgentFlag()
	{
		return AgentFlag;
	}
	public void setAgentFlag(String aAgentFlag)
	{
		if(aAgentFlag!=null && aAgentFlag.length()>6)
			throw new IllegalArgumentException("渠道挂起标志AgentFlag值"+aAgentFlag+"的长度"+aAgentFlag.length()+"大于最大值6");
		AgentFlag = aAgentFlag;
	}
	/**
	* 0未挂起<p>
	* 1已挂起
	*/
	public String getRNFlag()
	{
		return RNFlag;
	}
	public void setRNFlag(String aRNFlag)
	{
		if(aRNFlag!=null && aRNFlag.length()>6)
			throw new IllegalArgumentException("续期挂起标志RNFlag值"+aRNFlag+"的长度"+aRNFlag.length()+"大于最大值6");
		RNFlag = aRNFlag;
	}
	public String getStandFlag1()
	{
		return StandFlag1;
	}
	public void setStandFlag1(String aStandFlag1)
	{
		if(aStandFlag1!=null && aStandFlag1.length()>24)
			throw new IllegalArgumentException("备用标志1StandFlag1值"+aStandFlag1+"的长度"+aStandFlag1.length()+"大于最大值24");
		StandFlag1 = aStandFlag1;
	}
	public String getStandFlag2()
	{
		return StandFlag2;
	}
	public void setStandFlag2(String aStandFlag2)
	{
		if(aStandFlag2!=null && aStandFlag2.length()>24)
			throw new IllegalArgumentException("备用标志2StandFlag2值"+aStandFlag2+"的长度"+aStandFlag2.length()+"大于最大值24");
		StandFlag2 = aStandFlag2;
	}
	public String getStandFlag3()
	{
		return StandFlag3;
	}
	public void setStandFlag3(String aStandFlag3)
	{
		if(aStandFlag3!=null && aStandFlag3.length()>24)
			throw new IllegalArgumentException("备用标志3StandFlag3值"+aStandFlag3+"的长度"+aStandFlag3.length()+"大于最大值24");
		StandFlag3 = aStandFlag3;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>255)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值255");
		Remark = aRemark;
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
	public String getStandFlag4()
	{
		return StandFlag4;
	}
	public void setStandFlag4(String aStandFlag4)
	{
		if(aStandFlag4!=null && aStandFlag4.length()>24)
			throw new IllegalArgumentException("备用标志4StandFlag4值"+aStandFlag4+"的长度"+aStandFlag4.length()+"大于最大值24");
		StandFlag4 = aStandFlag4;
	}
	public String getStandFlag5()
	{
		return StandFlag5;
	}
	public void setStandFlag5(String aStandFlag5)
	{
		if(aStandFlag5!=null && aStandFlag5.length()>24)
			throw new IllegalArgumentException("备用标志5StandFlag5值"+aStandFlag5+"的长度"+aStandFlag5.length()+"大于最大值24");
		StandFlag5 = aStandFlag5;
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

	/**
	* 使用另外一个 LCContHangUpStateSchema 对象给 Schema 赋值
	* @param: aLCContHangUpStateSchema LCContHangUpStateSchema
	**/
	public void setSchema(LCContHangUpStateSchema aLCContHangUpStateSchema)
	{
		this.ContNo = aLCContHangUpStateSchema.getContNo();
		this.InsuredNo = aLCContHangUpStateSchema.getInsuredNo();
		this.PolNo = aLCContHangUpStateSchema.getPolNo();
		this.HangUpType = aLCContHangUpStateSchema.getHangUpType();
		this.HangUpNo = aLCContHangUpStateSchema.getHangUpNo();
		this.NBFlag = aLCContHangUpStateSchema.getNBFlag();
		this.PosFlag = aLCContHangUpStateSchema.getPosFlag();
		this.ClaimFlag = aLCContHangUpStateSchema.getClaimFlag();
		this.AgentFlag = aLCContHangUpStateSchema.getAgentFlag();
		this.RNFlag = aLCContHangUpStateSchema.getRNFlag();
		this.StandFlag1 = aLCContHangUpStateSchema.getStandFlag1();
		this.StandFlag2 = aLCContHangUpStateSchema.getStandFlag2();
		this.StandFlag3 = aLCContHangUpStateSchema.getStandFlag3();
		this.Remark = aLCContHangUpStateSchema.getRemark();
		this.Operator = aLCContHangUpStateSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCContHangUpStateSchema.getMakeDate());
		this.MakeTime = aLCContHangUpStateSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCContHangUpStateSchema.getModifyDate());
		this.ModifyTime = aLCContHangUpStateSchema.getModifyTime();
		this.StandFlag4 = aLCContHangUpStateSchema.getStandFlag4();
		this.StandFlag5 = aLCContHangUpStateSchema.getStandFlag5();
		this.ModifyOperator = aLCContHangUpStateSchema.getModifyOperator();
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
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("HangUpType") == null )
				this.HangUpType = null;
			else
				this.HangUpType = rs.getString("HangUpType").trim();

			if( rs.getString("HangUpNo") == null )
				this.HangUpNo = null;
			else
				this.HangUpNo = rs.getString("HangUpNo").trim();

			if( rs.getString("NBFlag") == null )
				this.NBFlag = null;
			else
				this.NBFlag = rs.getString("NBFlag").trim();

			if( rs.getString("PosFlag") == null )
				this.PosFlag = null;
			else
				this.PosFlag = rs.getString("PosFlag").trim();

			if( rs.getString("ClaimFlag") == null )
				this.ClaimFlag = null;
			else
				this.ClaimFlag = rs.getString("ClaimFlag").trim();

			if( rs.getString("AgentFlag") == null )
				this.AgentFlag = null;
			else
				this.AgentFlag = rs.getString("AgentFlag").trim();

			if( rs.getString("RNFlag") == null )
				this.RNFlag = null;
			else
				this.RNFlag = rs.getString("RNFlag").trim();

			if( rs.getString("StandFlag1") == null )
				this.StandFlag1 = null;
			else
				this.StandFlag1 = rs.getString("StandFlag1").trim();

			if( rs.getString("StandFlag2") == null )
				this.StandFlag2 = null;
			else
				this.StandFlag2 = rs.getString("StandFlag2").trim();

			if( rs.getString("StandFlag3") == null )
				this.StandFlag3 = null;
			else
				this.StandFlag3 = rs.getString("StandFlag3").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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

			if( rs.getString("StandFlag4") == null )
				this.StandFlag4 = null;
			else
				this.StandFlag4 = rs.getString("StandFlag4").trim();

			if( rs.getString("StandFlag5") == null )
				this.StandFlag5 = null;
			else
				this.StandFlag5 = rs.getString("StandFlag5").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCContHangUpState表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContHangUpStateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCContHangUpStateSchema getSchema()
	{
		LCContHangUpStateSchema aLCContHangUpStateSchema = new LCContHangUpStateSchema();
		aLCContHangUpStateSchema.setSchema(this);
		return aLCContHangUpStateSchema;
	}

	public LCContHangUpStateDB getDB()
	{
		LCContHangUpStateDB aDBOper = new LCContHangUpStateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCContHangUpState描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HangUpType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HangUpNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NBFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PosFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RNFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandFlag4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandFlag5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCContHangUpState>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			HangUpType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			HangUpNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			NBFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PosFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ClaimFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AgentFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RNFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			StandFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			StandFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			StandFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			StandFlag4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			StandFlag5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContHangUpStateSchema";
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("HangUpType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HangUpType));
		}
		if (FCode.equalsIgnoreCase("HangUpNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HangUpNo));
		}
		if (FCode.equalsIgnoreCase("NBFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NBFlag));
		}
		if (FCode.equalsIgnoreCase("PosFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PosFlag));
		}
		if (FCode.equalsIgnoreCase("ClaimFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimFlag));
		}
		if (FCode.equalsIgnoreCase("AgentFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentFlag));
		}
		if (FCode.equalsIgnoreCase("RNFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RNFlag));
		}
		if (FCode.equalsIgnoreCase("StandFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandFlag1));
		}
		if (FCode.equalsIgnoreCase("StandFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandFlag2));
		}
		if (FCode.equalsIgnoreCase("StandFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandFlag3));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
		if (FCode.equalsIgnoreCase("StandFlag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandFlag4));
		}
		if (FCode.equalsIgnoreCase("StandFlag5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandFlag5));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(HangUpType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(HangUpNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(NBFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PosFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ClaimFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AgentFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RNFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(StandFlag1);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(StandFlag2);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(StandFlag3);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(StandFlag4);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(StandFlag5);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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

		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("HangUpType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HangUpType = FValue.trim();
			}
			else
				HangUpType = null;
		}
		if (FCode.equalsIgnoreCase("HangUpNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HangUpNo = FValue.trim();
			}
			else
				HangUpNo = null;
		}
		if (FCode.equalsIgnoreCase("NBFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NBFlag = FValue.trim();
			}
			else
				NBFlag = null;
		}
		if (FCode.equalsIgnoreCase("PosFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PosFlag = FValue.trim();
			}
			else
				PosFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClaimFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimFlag = FValue.trim();
			}
			else
				ClaimFlag = null;
		}
		if (FCode.equalsIgnoreCase("AgentFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentFlag = FValue.trim();
			}
			else
				AgentFlag = null;
		}
		if (FCode.equalsIgnoreCase("RNFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RNFlag = FValue.trim();
			}
			else
				RNFlag = null;
		}
		if (FCode.equalsIgnoreCase("StandFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandFlag1 = FValue.trim();
			}
			else
				StandFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandFlag2 = FValue.trim();
			}
			else
				StandFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("StandFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandFlag3 = FValue.trim();
			}
			else
				StandFlag3 = null;
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
		if (FCode.equalsIgnoreCase("StandFlag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandFlag4 = FValue.trim();
			}
			else
				StandFlag4 = null;
		}
		if (FCode.equalsIgnoreCase("StandFlag5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandFlag5 = FValue.trim();
			}
			else
				StandFlag5 = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCContHangUpStateSchema other = (LCContHangUpStateSchema)otherObject;
		return
			ContNo.equals(other.getContNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& PolNo.equals(other.getPolNo())
			&& HangUpType.equals(other.getHangUpType())
			&& HangUpNo.equals(other.getHangUpNo())
			&& NBFlag.equals(other.getNBFlag())
			&& PosFlag.equals(other.getPosFlag())
			&& ClaimFlag.equals(other.getClaimFlag())
			&& AgentFlag.equals(other.getAgentFlag())
			&& RNFlag.equals(other.getRNFlag())
			&& StandFlag1.equals(other.getStandFlag1())
			&& StandFlag2.equals(other.getStandFlag2())
			&& StandFlag3.equals(other.getStandFlag3())
			&& Remark.equals(other.getRemark())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& StandFlag4.equals(other.getStandFlag4())
			&& StandFlag5.equals(other.getStandFlag5())
			&& ModifyOperator.equals(other.getModifyOperator());
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
		if( strFieldName.equals("ContNo") ) {
			return 0;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 1;
		}
		if( strFieldName.equals("PolNo") ) {
			return 2;
		}
		if( strFieldName.equals("HangUpType") ) {
			return 3;
		}
		if( strFieldName.equals("HangUpNo") ) {
			return 4;
		}
		if( strFieldName.equals("NBFlag") ) {
			return 5;
		}
		if( strFieldName.equals("PosFlag") ) {
			return 6;
		}
		if( strFieldName.equals("ClaimFlag") ) {
			return 7;
		}
		if( strFieldName.equals("AgentFlag") ) {
			return 8;
		}
		if( strFieldName.equals("RNFlag") ) {
			return 9;
		}
		if( strFieldName.equals("StandFlag1") ) {
			return 10;
		}
		if( strFieldName.equals("StandFlag2") ) {
			return 11;
		}
		if( strFieldName.equals("StandFlag3") ) {
			return 12;
		}
		if( strFieldName.equals("Remark") ) {
			return 13;
		}
		if( strFieldName.equals("Operator") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 18;
		}
		if( strFieldName.equals("StandFlag4") ) {
			return 19;
		}
		if( strFieldName.equals("StandFlag5") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 21;
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
				strFieldName = "ContNo";
				break;
			case 1:
				strFieldName = "InsuredNo";
				break;
			case 2:
				strFieldName = "PolNo";
				break;
			case 3:
				strFieldName = "HangUpType";
				break;
			case 4:
				strFieldName = "HangUpNo";
				break;
			case 5:
				strFieldName = "NBFlag";
				break;
			case 6:
				strFieldName = "PosFlag";
				break;
			case 7:
				strFieldName = "ClaimFlag";
				break;
			case 8:
				strFieldName = "AgentFlag";
				break;
			case 9:
				strFieldName = "RNFlag";
				break;
			case 10:
				strFieldName = "StandFlag1";
				break;
			case 11:
				strFieldName = "StandFlag2";
				break;
			case 12:
				strFieldName = "StandFlag3";
				break;
			case 13:
				strFieldName = "Remark";
				break;
			case 14:
				strFieldName = "Operator";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyDate";
				break;
			case 18:
				strFieldName = "ModifyTime";
				break;
			case 19:
				strFieldName = "StandFlag4";
				break;
			case 20:
				strFieldName = "StandFlag5";
				break;
			case 21:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HangUpType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HangUpNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NBFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PosFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RNFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandFlag3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
		if( strFieldName.equals("StandFlag4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandFlag5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
