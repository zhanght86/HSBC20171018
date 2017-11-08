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
import com.sinosoft.lis.db.LCGrpContHangUpStateDB;

/*
 * <p>ClassName: LCGrpContHangUpStateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCGrpContHangUpStateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCGrpContHangUpStateSchema.class);

	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
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

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCGrpContHangUpStateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "GrpContNo";
		pk[1] = "GrpPolNo";
		pk[2] = "HangUpType";

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
		LCGrpContHangUpStateSchema cloned = (LCGrpContHangUpStateSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
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
		RNFlag = aRNFlag;
	}
	public String getStandFlag1()
	{
		return StandFlag1;
	}
	public void setStandFlag1(String aStandFlag1)
	{
		StandFlag1 = aStandFlag1;
	}
	public String getStandFlag2()
	{
		return StandFlag2;
	}
	public void setStandFlag2(String aStandFlag2)
	{
		StandFlag2 = aStandFlag2;
	}
	public String getStandFlag3()
	{
		return StandFlag3;
	}
	public void setStandFlag3(String aStandFlag3)
	{
		StandFlag3 = aStandFlag3;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
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

	/**
	* 使用另外一个 LCGrpContHangUpStateSchema 对象给 Schema 赋值
	* @param: aLCGrpContHangUpStateSchema LCGrpContHangUpStateSchema
	**/
	public void setSchema(LCGrpContHangUpStateSchema aLCGrpContHangUpStateSchema)
	{
		this.GrpContNo = aLCGrpContHangUpStateSchema.getGrpContNo();
		this.GrpPolNo = aLCGrpContHangUpStateSchema.getGrpPolNo();
		this.HangUpType = aLCGrpContHangUpStateSchema.getHangUpType();
		this.HangUpNo = aLCGrpContHangUpStateSchema.getHangUpNo();
		this.NBFlag = aLCGrpContHangUpStateSchema.getNBFlag();
		this.PosFlag = aLCGrpContHangUpStateSchema.getPosFlag();
		this.ClaimFlag = aLCGrpContHangUpStateSchema.getClaimFlag();
		this.AgentFlag = aLCGrpContHangUpStateSchema.getAgentFlag();
		this.RNFlag = aLCGrpContHangUpStateSchema.getRNFlag();
		this.StandFlag1 = aLCGrpContHangUpStateSchema.getStandFlag1();
		this.StandFlag2 = aLCGrpContHangUpStateSchema.getStandFlag2();
		this.StandFlag3 = aLCGrpContHangUpStateSchema.getStandFlag3();
		this.Remark = aLCGrpContHangUpStateSchema.getRemark();
		this.Operator = aLCGrpContHangUpStateSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCGrpContHangUpStateSchema.getMakeDate());
		this.MakeTime = aLCGrpContHangUpStateSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCGrpContHangUpStateSchema.getModifyDate());
		this.ModifyTime = aLCGrpContHangUpStateSchema.getModifyTime();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCGrpContHangUpState表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpContHangUpStateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCGrpContHangUpStateSchema getSchema()
	{
		LCGrpContHangUpStateSchema aLCGrpContHangUpStateSchema = new LCGrpContHangUpStateSchema();
		aLCGrpContHangUpStateSchema.setSchema(this);
		return aLCGrpContHangUpStateSchema;
	}

	public LCGrpContHangUpStateDB getDB()
	{
		LCGrpContHangUpStateDB aDBOper = new LCGrpContHangUpStateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpContHangUpState描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpContHangUpState>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			HangUpType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			HangUpNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			NBFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PosFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ClaimFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AgentFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RNFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			StandFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			StandFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			StandFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpContHangUpStateSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(HangUpType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(HangUpNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(NBFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PosFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ClaimFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AgentFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RNFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(StandFlag1);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(StandFlag2);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(StandFlag3);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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

		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCGrpContHangUpStateSchema other = (LCGrpContHangUpStateSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 1;
		}
		if( strFieldName.equals("HangUpType") ) {
			return 2;
		}
		if( strFieldName.equals("HangUpNo") ) {
			return 3;
		}
		if( strFieldName.equals("NBFlag") ) {
			return 4;
		}
		if( strFieldName.equals("PosFlag") ) {
			return 5;
		}
		if( strFieldName.equals("ClaimFlag") ) {
			return 6;
		}
		if( strFieldName.equals("AgentFlag") ) {
			return 7;
		}
		if( strFieldName.equals("RNFlag") ) {
			return 8;
		}
		if( strFieldName.equals("StandFlag1") ) {
			return 9;
		}
		if( strFieldName.equals("StandFlag2") ) {
			return 10;
		}
		if( strFieldName.equals("StandFlag3") ) {
			return 11;
		}
		if( strFieldName.equals("Remark") ) {
			return 12;
		}
		if( strFieldName.equals("Operator") ) {
			return 13;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 14;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "GrpPolNo";
				break;
			case 2:
				strFieldName = "HangUpType";
				break;
			case 3:
				strFieldName = "HangUpNo";
				break;
			case 4:
				strFieldName = "NBFlag";
				break;
			case 5:
				strFieldName = "PosFlag";
				break;
			case 6:
				strFieldName = "ClaimFlag";
				break;
			case 7:
				strFieldName = "AgentFlag";
				break;
			case 8:
				strFieldName = "RNFlag";
				break;
			case 9:
				strFieldName = "StandFlag1";
				break;
			case 10:
				strFieldName = "StandFlag2";
				break;
			case 11:
				strFieldName = "StandFlag3";
				break;
			case 12:
				strFieldName = "Remark";
				break;
			case 13:
				strFieldName = "Operator";
				break;
			case 14:
				strFieldName = "MakeDate";
				break;
			case 15:
				strFieldName = "MakeTime";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
