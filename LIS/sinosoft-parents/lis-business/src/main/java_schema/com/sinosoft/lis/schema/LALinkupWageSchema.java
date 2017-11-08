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
import com.sinosoft.lis.db.LALinkupWageDB;

/*
 * <p>ClassName: LALinkupWageSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LALinkupWageSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LALinkupWageSchema.class);

	// @Field
	/** 序号 */
	private String SerialNo;
	/** 三级机构代码 */
	private String Com3Code;
	/** 三级机构名称 */
	private String Com3Name;
	/** 四级机构代码 */
	private String Com4Code;
	/** 四级机构名称 */
	private String Com4Name;
	/** 发放佣金月 */
	private String FWageNo;
	/** 操作佣金月 */
	private String DWageNo;
	/** 代理人姓名 */
	private String AgentName;
	/** 代理人编码 */
	private String AgentCode;
	/** 职级 */
	private String AgentGrade;
	/** 证件号码 */
	private String IDNo;
	/** 存折帐号 */
	private String BankAccNo;
	/** 同业衔接加扣款 */
	private double T1;
	/** 奖惩款 */
	private double T2;
	/** 本次应发金额 */
	private double T3;
	/** 营业税及附加 */
	private double T4;
	/** 个人所得税 */
	private double T5;
	/** 本次实发金额 */
	private double T6;
	/** 差勤扣款实扣 */
	private double T7;
	/** 财务实发 */
	private double T8;
	/** 审核确认状态 */
	private String ConfState;
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

	public static final int FIELDNUM = 26;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LALinkupWageSchema()
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
		LALinkupWageSchema cloned = (LALinkupWageSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 采用备份表的流水号<p>
	* <p>
	* EdorNo = PubFun1.CreateMaxNo("EdorNo", 20)
	*/
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getCom3Code()
	{
		return Com3Code;
	}
	public void setCom3Code(String aCom3Code)
	{
		Com3Code = aCom3Code;
	}
	public String getCom3Name()
	{
		return Com3Name;
	}
	public void setCom3Name(String aCom3Name)
	{
		Com3Name = aCom3Name;
	}
	public String getCom4Code()
	{
		return Com4Code;
	}
	public void setCom4Code(String aCom4Code)
	{
		Com4Code = aCom4Code;
	}
	/**
	* 四级机构代码可以为空，但名称不为空
	*/
	public String getCom4Name()
	{
		return Com4Name;
	}
	public void setCom4Name(String aCom4Name)
	{
		Com4Name = aCom4Name;
	}
	/**
	* 客户填写值<p>
	* <p>
	* 对应模板中的佣金月
	*/
	public String getFWageNo()
	{
		return FWageNo;
	}
	public void setFWageNo(String aFWageNo)
	{
		FWageNo = aFWageNo;
	}
	/**
	* 根据入机时间所在的佣金月stattype='5'置<p>
	* <p>
	* 方便业务做审核确认和财务提取凭证
	*/
	public String getDWageNo()
	{
		return DWageNo;
	}
	public void setDWageNo(String aDWageNo)
	{
		DWageNo = aDWageNo;
	}
	public String getAgentName()
	{
		return AgentName;
	}
	public void setAgentName(String aAgentName)
	{
		AgentName = aAgentName;
	}
	/**
	* 可以为空
	*/
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getAgentGrade()
	{
		return AgentGrade;
	}
	public void setAgentGrade(String aAgentGrade)
	{
		AgentGrade = aAgentGrade;
	}
	/**
	* 长度为最长的身份证18位
	*/
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		IDNo = aIDNo;
	}
	public String getBankAccNo()
	{
		return BankAccNo;
	}
	public void setBankAccNo(String aBankAccNo)
	{
		BankAccNo = aBankAccNo;
	}
	public double getT1()
	{
		return T1;
	}
	public void setT1(double aT1)
	{
		T1 = aT1;
	}
	public void setT1(String aT1)
	{
		if (aT1 != null && !aT1.equals(""))
		{
			Double tDouble = new Double(aT1);
			double d = tDouble.doubleValue();
			T1 = d;
		}
	}

	public double getT2()
	{
		return T2;
	}
	public void setT2(double aT2)
	{
		T2 = aT2;
	}
	public void setT2(String aT2)
	{
		if (aT2 != null && !aT2.equals(""))
		{
			Double tDouble = new Double(aT2);
			double d = tDouble.doubleValue();
			T2 = d;
		}
	}

	public double getT3()
	{
		return T3;
	}
	public void setT3(double aT3)
	{
		T3 = aT3;
	}
	public void setT3(String aT3)
	{
		if (aT3 != null && !aT3.equals(""))
		{
			Double tDouble = new Double(aT3);
			double d = tDouble.doubleValue();
			T3 = d;
		}
	}

	public double getT4()
	{
		return T4;
	}
	public void setT4(double aT4)
	{
		T4 = aT4;
	}
	public void setT4(String aT4)
	{
		if (aT4 != null && !aT4.equals(""))
		{
			Double tDouble = new Double(aT4);
			double d = tDouble.doubleValue();
			T4 = d;
		}
	}

	public double getT5()
	{
		return T5;
	}
	public void setT5(double aT5)
	{
		T5 = aT5;
	}
	public void setT5(String aT5)
	{
		if (aT5 != null && !aT5.equals(""))
		{
			Double tDouble = new Double(aT5);
			double d = tDouble.doubleValue();
			T5 = d;
		}
	}

	public double getT6()
	{
		return T6;
	}
	public void setT6(double aT6)
	{
		T6 = aT6;
	}
	public void setT6(String aT6)
	{
		if (aT6 != null && !aT6.equals(""))
		{
			Double tDouble = new Double(aT6);
			double d = tDouble.doubleValue();
			T6 = d;
		}
	}

	public double getT7()
	{
		return T7;
	}
	public void setT7(double aT7)
	{
		T7 = aT7;
	}
	public void setT7(String aT7)
	{
		if (aT7 != null && !aT7.equals(""))
		{
			Double tDouble = new Double(aT7);
			double d = tDouble.doubleValue();
			T7 = d;
		}
	}

	public double getT8()
	{
		return T8;
	}
	public void setT8(double aT8)
	{
		T8 = aT8;
	}
	public void setT8(String aT8)
	{
		if (aT8 != null && !aT8.equals(""))
		{
			Double tDouble = new Double(aT8);
			double d = tDouble.doubleValue();
			T8 = d;
		}
	}

	/**
	* 0-未 导入时置<p>
	* 1-已 审核确认时置
	*/
	public String getConfState()
	{
		return ConfState;
	}
	public void setConfState(String aConfState)
	{
		ConfState = aConfState;
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
	* 使用另外一个 LALinkupWageSchema 对象给 Schema 赋值
	* @param: aLALinkupWageSchema LALinkupWageSchema
	**/
	public void setSchema(LALinkupWageSchema aLALinkupWageSchema)
	{
		this.SerialNo = aLALinkupWageSchema.getSerialNo();
		this.Com3Code = aLALinkupWageSchema.getCom3Code();
		this.Com3Name = aLALinkupWageSchema.getCom3Name();
		this.Com4Code = aLALinkupWageSchema.getCom4Code();
		this.Com4Name = aLALinkupWageSchema.getCom4Name();
		this.FWageNo = aLALinkupWageSchema.getFWageNo();
		this.DWageNo = aLALinkupWageSchema.getDWageNo();
		this.AgentName = aLALinkupWageSchema.getAgentName();
		this.AgentCode = aLALinkupWageSchema.getAgentCode();
		this.AgentGrade = aLALinkupWageSchema.getAgentGrade();
		this.IDNo = aLALinkupWageSchema.getIDNo();
		this.BankAccNo = aLALinkupWageSchema.getBankAccNo();
		this.T1 = aLALinkupWageSchema.getT1();
		this.T2 = aLALinkupWageSchema.getT2();
		this.T3 = aLALinkupWageSchema.getT3();
		this.T4 = aLALinkupWageSchema.getT4();
		this.T5 = aLALinkupWageSchema.getT5();
		this.T6 = aLALinkupWageSchema.getT6();
		this.T7 = aLALinkupWageSchema.getT7();
		this.T8 = aLALinkupWageSchema.getT8();
		this.ConfState = aLALinkupWageSchema.getConfState();
		this.Operator = aLALinkupWageSchema.getOperator();
		this.MakeDate = fDate.getDate( aLALinkupWageSchema.getMakeDate());
		this.MakeTime = aLALinkupWageSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLALinkupWageSchema.getModifyDate());
		this.ModifyTime = aLALinkupWageSchema.getModifyTime();
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("Com3Code") == null )
				this.Com3Code = null;
			else
				this.Com3Code = rs.getString("Com3Code").trim();

			if( rs.getString("Com3Name") == null )
				this.Com3Name = null;
			else
				this.Com3Name = rs.getString("Com3Name").trim();

			if( rs.getString("Com4Code") == null )
				this.Com4Code = null;
			else
				this.Com4Code = rs.getString("Com4Code").trim();

			if( rs.getString("Com4Name") == null )
				this.Com4Name = null;
			else
				this.Com4Name = rs.getString("Com4Name").trim();

			if( rs.getString("FWageNo") == null )
				this.FWageNo = null;
			else
				this.FWageNo = rs.getString("FWageNo").trim();

			if( rs.getString("DWageNo") == null )
				this.DWageNo = null;
			else
				this.DWageNo = rs.getString("DWageNo").trim();

			if( rs.getString("AgentName") == null )
				this.AgentName = null;
			else
				this.AgentName = rs.getString("AgentName").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGrade") == null )
				this.AgentGrade = null;
			else
				this.AgentGrade = rs.getString("AgentGrade").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			this.T1 = rs.getDouble("T1");
			this.T2 = rs.getDouble("T2");
			this.T3 = rs.getDouble("T3");
			this.T4 = rs.getDouble("T4");
			this.T5 = rs.getDouble("T5");
			this.T6 = rs.getDouble("T6");
			this.T7 = rs.getDouble("T7");
			this.T8 = rs.getDouble("T8");
			if( rs.getString("ConfState") == null )
				this.ConfState = null;
			else
				this.ConfState = rs.getString("ConfState").trim();

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
			logger.debug("数据库中的LALinkupWage表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LALinkupWageSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LALinkupWageSchema getSchema()
	{
		LALinkupWageSchema aLALinkupWageSchema = new LALinkupWageSchema();
		aLALinkupWageSchema.setSchema(this);
		return aLALinkupWageSchema;
	}

	public LALinkupWageDB getDB()
	{
		LALinkupWageDB aDBOper = new LALinkupWageDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLALinkupWage描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Com3Code)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Com3Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Com4Code)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Com4Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FWageNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DWageNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(T1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(T2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(T3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(T4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(T5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(T6));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(T7));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(T8));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLALinkupWage>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Com3Code = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Com3Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Com4Code = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Com4Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FWageNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DWageNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AgentName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AgentGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			T1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			T2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			T3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			T4 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			T5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			T6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			T7 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			T8 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			ConfState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LALinkupWageSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("Com3Code"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Com3Code));
		}
		if (FCode.equalsIgnoreCase("Com3Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Com3Name));
		}
		if (FCode.equalsIgnoreCase("Com4Code"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Com4Code));
		}
		if (FCode.equalsIgnoreCase("Com4Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Com4Name));
		}
		if (FCode.equalsIgnoreCase("FWageNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FWageNo));
		}
		if (FCode.equalsIgnoreCase("DWageNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DWageNo));
		}
		if (FCode.equalsIgnoreCase("AgentName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentName));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGrade));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("T1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(T1));
		}
		if (FCode.equalsIgnoreCase("T2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(T2));
		}
		if (FCode.equalsIgnoreCase("T3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(T3));
		}
		if (FCode.equalsIgnoreCase("T4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(T4));
		}
		if (FCode.equalsIgnoreCase("T5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(T5));
		}
		if (FCode.equalsIgnoreCase("T6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(T6));
		}
		if (FCode.equalsIgnoreCase("T7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(T7));
		}
		if (FCode.equalsIgnoreCase("T8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(T8));
		}
		if (FCode.equalsIgnoreCase("ConfState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfState));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Com3Code);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Com3Name);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Com4Code);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Com4Name);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FWageNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DWageNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AgentName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AgentGrade);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 12:
				strFieldValue = String.valueOf(T1);
				break;
			case 13:
				strFieldValue = String.valueOf(T2);
				break;
			case 14:
				strFieldValue = String.valueOf(T3);
				break;
			case 15:
				strFieldValue = String.valueOf(T4);
				break;
			case 16:
				strFieldValue = String.valueOf(T5);
				break;
			case 17:
				strFieldValue = String.valueOf(T6);
				break;
			case 18:
				strFieldValue = String.valueOf(T7);
				break;
			case 19:
				strFieldValue = String.valueOf(T8);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ConfState);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 25:
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("Com3Code"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Com3Code = FValue.trim();
			}
			else
				Com3Code = null;
		}
		if (FCode.equalsIgnoreCase("Com3Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Com3Name = FValue.trim();
			}
			else
				Com3Name = null;
		}
		if (FCode.equalsIgnoreCase("Com4Code"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Com4Code = FValue.trim();
			}
			else
				Com4Code = null;
		}
		if (FCode.equalsIgnoreCase("Com4Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Com4Name = FValue.trim();
			}
			else
				Com4Name = null;
		}
		if (FCode.equalsIgnoreCase("FWageNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FWageNo = FValue.trim();
			}
			else
				FWageNo = null;
		}
		if (FCode.equalsIgnoreCase("DWageNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DWageNo = FValue.trim();
			}
			else
				DWageNo = null;
		}
		if (FCode.equalsIgnoreCase("AgentName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentName = FValue.trim();
			}
			else
				AgentName = null;
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
		if (FCode.equalsIgnoreCase("AgentGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGrade = FValue.trim();
			}
			else
				AgentGrade = null;
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
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankAccNo = FValue.trim();
			}
			else
				BankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("T1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				T1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("T2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				T2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("T3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				T3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("T4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				T4 = d;
			}
		}
		if (FCode.equalsIgnoreCase("T5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				T5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("T6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				T6 = d;
			}
		}
		if (FCode.equalsIgnoreCase("T7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				T7 = d;
			}
		}
		if (FCode.equalsIgnoreCase("T8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				T8 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ConfState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfState = FValue.trim();
			}
			else
				ConfState = null;
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
		LALinkupWageSchema other = (LALinkupWageSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& Com3Code.equals(other.getCom3Code())
			&& Com3Name.equals(other.getCom3Name())
			&& Com4Code.equals(other.getCom4Code())
			&& Com4Name.equals(other.getCom4Name())
			&& FWageNo.equals(other.getFWageNo())
			&& DWageNo.equals(other.getDWageNo())
			&& AgentName.equals(other.getAgentName())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGrade.equals(other.getAgentGrade())
			&& IDNo.equals(other.getIDNo())
			&& BankAccNo.equals(other.getBankAccNo())
			&& T1 == other.getT1()
			&& T2 == other.getT2()
			&& T3 == other.getT3()
			&& T4 == other.getT4()
			&& T5 == other.getT5()
			&& T6 == other.getT6()
			&& T7 == other.getT7()
			&& T8 == other.getT8()
			&& ConfState.equals(other.getConfState())
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("Com3Code") ) {
			return 1;
		}
		if( strFieldName.equals("Com3Name") ) {
			return 2;
		}
		if( strFieldName.equals("Com4Code") ) {
			return 3;
		}
		if( strFieldName.equals("Com4Name") ) {
			return 4;
		}
		if( strFieldName.equals("FWageNo") ) {
			return 5;
		}
		if( strFieldName.equals("DWageNo") ) {
			return 6;
		}
		if( strFieldName.equals("AgentName") ) {
			return 7;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 8;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return 9;
		}
		if( strFieldName.equals("IDNo") ) {
			return 10;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 11;
		}
		if( strFieldName.equals("T1") ) {
			return 12;
		}
		if( strFieldName.equals("T2") ) {
			return 13;
		}
		if( strFieldName.equals("T3") ) {
			return 14;
		}
		if( strFieldName.equals("T4") ) {
			return 15;
		}
		if( strFieldName.equals("T5") ) {
			return 16;
		}
		if( strFieldName.equals("T6") ) {
			return 17;
		}
		if( strFieldName.equals("T7") ) {
			return 18;
		}
		if( strFieldName.equals("T8") ) {
			return 19;
		}
		if( strFieldName.equals("ConfState") ) {
			return 20;
		}
		if( strFieldName.equals("Operator") ) {
			return 21;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 22;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "Com3Code";
				break;
			case 2:
				strFieldName = "Com3Name";
				break;
			case 3:
				strFieldName = "Com4Code";
				break;
			case 4:
				strFieldName = "Com4Name";
				break;
			case 5:
				strFieldName = "FWageNo";
				break;
			case 6:
				strFieldName = "DWageNo";
				break;
			case 7:
				strFieldName = "AgentName";
				break;
			case 8:
				strFieldName = "AgentCode";
				break;
			case 9:
				strFieldName = "AgentGrade";
				break;
			case 10:
				strFieldName = "IDNo";
				break;
			case 11:
				strFieldName = "BankAccNo";
				break;
			case 12:
				strFieldName = "T1";
				break;
			case 13:
				strFieldName = "T2";
				break;
			case 14:
				strFieldName = "T3";
				break;
			case 15:
				strFieldName = "T4";
				break;
			case 16:
				strFieldName = "T5";
				break;
			case 17:
				strFieldName = "T6";
				break;
			case 18:
				strFieldName = "T7";
				break;
			case 19:
				strFieldName = "T8";
				break;
			case 20:
				strFieldName = "ConfState";
				break;
			case 21:
				strFieldName = "Operator";
				break;
			case 22:
				strFieldName = "MakeDate";
				break;
			case 23:
				strFieldName = "MakeTime";
				break;
			case 24:
				strFieldName = "ModifyDate";
				break;
			case 25:
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Com3Code") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Com3Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Com4Code") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Com4Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FWageNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DWageNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("T1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("T2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("T3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("T4") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("T5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("T6") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("T7") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("T8") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ConfState") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_DATE;
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
