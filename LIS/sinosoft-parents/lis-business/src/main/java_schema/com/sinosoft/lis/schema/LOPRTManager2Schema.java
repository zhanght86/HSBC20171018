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
import com.sinosoft.lis.db.LOPRTManager2DB;

/*
 * <p>ClassName: LOPRTManager2Schema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LOPRTManager2Schema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOPRTManager2Schema.class);

	// @Field
	/** 印刷流水号 */
	private String PrtSeq;
	/** 对应其它号码 */
	private String OtherNo;
	/** 其它号码类型 */
	private String OtherNoType;
	/** 单据类型 */
	private String Code;
	/** 管理机构 */
	private String ManageCom;
	/** 代理人编码 */
	private String AgentCode;
	/** 发起机构 */
	private String ReqCom;
	/** 发起人 */
	private String ReqOperator;
	/** 执行机构 */
	private String ExeCom;
	/** 执行人 */
	private String ExeOperator;
	/** 打印方式 */
	private String PrtType;
	/** 打印状态 */
	private String StateFlag;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 完成日期 */
	private Date DoneDate;
	/** 完成时间 */
	private String DoneTime;
	/** 备用属性字段1 */
	private String StandbyFlag1;
	/** 备用属性字段2 */
	private String StandbyFlag2;
	/** 旧印刷流水号 */
	private String OldPrtSeq;
	/** 补打标志 */
	private String PatchFlag;
	/** 备用属性字段3 */
	private String StandbyFlag3;
	/** 备用属性字段4 */
	private String StandbyFlag4;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOPRTManager2Schema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "PrtSeq";

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
		LOPRTManager2Schema cloned = (LOPRTManager2Schema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPrtSeq()
	{
		return PrtSeq;
	}
	public void setPrtSeq(String aPrtSeq)
	{
		PrtSeq = aPrtSeq;
	}
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	/**
	* 00 --- 个单保单号<p>
	* 01 --- 集体保单号<p>
	* 02 --- 合同号<p>
	* 03 --- 批单号<p>
	* 04 --- 实付收据号<p>
	* 05 --- 交费收据号码
	*/
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		OtherNoType = aOtherNoType;
	}
	/**
	* 00 －－ 拒保通知书<p>
	* 01 －－ 预留<p>
	* 02 －－ 退余额的打印格式，（指在业务进行退余额操作后打印的凭据，客户拿该凭据到财务领款）(取消）<p>
	* 03 －－ 体检通知书<p>
	* 04 －－ 面见通知书<p>
	* 05 －－ 核保通知书<p>
	* 06 －－ 延期承保通知书<p>
	* 07 －－ 首期交费通知书<p>
	* 08 －－ 新契约退费通知书（就是首期溢交保费通知书）<p>
	* 09 －－ 撤单通知书（一般指在催办通知书发后1个月还没有返回的投保单，需要打印该通知书，通知客户来退保）<p>
	* 10 －－ 首期交费通知书催办通知书<p>
	* 11 －－ 体检通知书催办通知书<p>
	* 12 －－ 核保通知书催办通知书<p>
	* 13 －－ 收款收据打印<p>
	* 14 －－ 业务员问题件通知书<p>
	* 15 ---- 缴费催办通知书<p>
	* <p>
	* 23 －－ 保全体检通知书<p>
	* 24 －－ 保全面见通知书<p>
	* 25 －－ 保全核保通知书<p>
	* <p>
	* 30 --- 红利派发通知书<p>
	* 31 --- 续期缴费通知书<p>
	* 32 --- 个人发票<p>
	* 33 --- 集体发票<p>
	* 34 －－ 团体红利派发通知书<p>
	* <p>
	* 50 －－ 团体核保通知书<p>
	* 51 －－ 拒保通知书<p>
	* 52 －－ 撤单通知书<p>
	* 53 －－ 延期通知书
	*/
	public String getCode()
	{
		return Code;
	}
	public void setCode(String aCode)
	{
		Code = aCode;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getReqCom()
	{
		return ReqCom;
	}
	public void setReqCom(String aReqCom)
	{
		ReqCom = aReqCom;
	}
	public String getReqOperator()
	{
		return ReqOperator;
	}
	public void setReqOperator(String aReqOperator)
	{
		ReqOperator = aReqOperator;
	}
	public String getExeCom()
	{
		return ExeCom;
	}
	public void setExeCom(String aExeCom)
	{
		ExeCom = aExeCom;
	}
	public String getExeOperator()
	{
		return ExeOperator;
	}
	public void setExeOperator(String aExeOperator)
	{
		ExeOperator = aExeOperator;
	}
	/**
	* 0 -- 前台打印；1 -- 后台打印
	*/
	public String getPrtType()
	{
		return PrtType;
	}
	public void setPrtType(String aPrtType)
	{
		PrtType = aPrtType;
	}
	/**
	* 0 -- 提交<p>
	* 1 -- 完成<p>
	* 2 -- 打印的单据已经回复<p>
	* 3 -- 表示已经发催办通知书
	*/
	public String getStateFlag()
	{
		return StateFlag;
	}
	public void setStateFlag(String aStateFlag)
	{
		StateFlag = aStateFlag;
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
	public String getDoneDate()
	{
		if( DoneDate != null )
			return fDate.getString(DoneDate);
		else
			return null;
	}
	public void setDoneDate(Date aDoneDate)
	{
		DoneDate = aDoneDate;
	}
	public void setDoneDate(String aDoneDate)
	{
		if (aDoneDate != null && !aDoneDate.equals("") )
		{
			DoneDate = fDate.getDate( aDoneDate );
		}
		else
			DoneDate = null;
	}

	public String getDoneTime()
	{
		return DoneTime;
	}
	public void setDoneTime(String aDoneTime)
	{
		DoneTime = aDoneTime;
	}
	/**
	* 如果单据类型为03（体检通知书），该字段存放需要体检的客户号码。
	*/
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		StandbyFlag2 = aStandbyFlag2;
	}
	public String getOldPrtSeq()
	{
		return OldPrtSeq;
	}
	public void setOldPrtSeq(String aOldPrtSeq)
	{
		OldPrtSeq = aOldPrtSeq;
	}
	/**
	* 0或Null －－ 非补打单证<p>
	* 1       －－ 补打单证
	*/
	public String getPatchFlag()
	{
		return PatchFlag;
	}
	public void setPatchFlag(String aPatchFlag)
	{
		PatchFlag = aPatchFlag;
	}
	public String getStandbyFlag3()
	{
		return StandbyFlag3;
	}
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		StandbyFlag3 = aStandbyFlag3;
	}
	public String getStandbyFlag4()
	{
		return StandbyFlag4;
	}
	public void setStandbyFlag4(String aStandbyFlag4)
	{
		StandbyFlag4 = aStandbyFlag4;
	}

	/**
	* 使用另外一个 LOPRTManager2Schema 对象给 Schema 赋值
	* @param: aLOPRTManager2Schema LOPRTManager2Schema
	**/
	public void setSchema(LOPRTManager2Schema aLOPRTManager2Schema)
	{
		this.PrtSeq = aLOPRTManager2Schema.getPrtSeq();
		this.OtherNo = aLOPRTManager2Schema.getOtherNo();
		this.OtherNoType = aLOPRTManager2Schema.getOtherNoType();
		this.Code = aLOPRTManager2Schema.getCode();
		this.ManageCom = aLOPRTManager2Schema.getManageCom();
		this.AgentCode = aLOPRTManager2Schema.getAgentCode();
		this.ReqCom = aLOPRTManager2Schema.getReqCom();
		this.ReqOperator = aLOPRTManager2Schema.getReqOperator();
		this.ExeCom = aLOPRTManager2Schema.getExeCom();
		this.ExeOperator = aLOPRTManager2Schema.getExeOperator();
		this.PrtType = aLOPRTManager2Schema.getPrtType();
		this.StateFlag = aLOPRTManager2Schema.getStateFlag();
		this.MakeDate = fDate.getDate( aLOPRTManager2Schema.getMakeDate());
		this.MakeTime = aLOPRTManager2Schema.getMakeTime();
		this.DoneDate = fDate.getDate( aLOPRTManager2Schema.getDoneDate());
		this.DoneTime = aLOPRTManager2Schema.getDoneTime();
		this.StandbyFlag1 = aLOPRTManager2Schema.getStandbyFlag1();
		this.StandbyFlag2 = aLOPRTManager2Schema.getStandbyFlag2();
		this.OldPrtSeq = aLOPRTManager2Schema.getOldPrtSeq();
		this.PatchFlag = aLOPRTManager2Schema.getPatchFlag();
		this.StandbyFlag3 = aLOPRTManager2Schema.getStandbyFlag3();
		this.StandbyFlag4 = aLOPRTManager2Schema.getStandbyFlag4();
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
			if( rs.getString("PrtSeq") == null )
				this.PrtSeq = null;
			else
				this.PrtSeq = rs.getString("PrtSeq").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("Code") == null )
				this.Code = null;
			else
				this.Code = rs.getString("Code").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("ReqCom") == null )
				this.ReqCom = null;
			else
				this.ReqCom = rs.getString("ReqCom").trim();

			if( rs.getString("ReqOperator") == null )
				this.ReqOperator = null;
			else
				this.ReqOperator = rs.getString("ReqOperator").trim();

			if( rs.getString("ExeCom") == null )
				this.ExeCom = null;
			else
				this.ExeCom = rs.getString("ExeCom").trim();

			if( rs.getString("ExeOperator") == null )
				this.ExeOperator = null;
			else
				this.ExeOperator = rs.getString("ExeOperator").trim();

			if( rs.getString("PrtType") == null )
				this.PrtType = null;
			else
				this.PrtType = rs.getString("PrtType").trim();

			if( rs.getString("StateFlag") == null )
				this.StateFlag = null;
			else
				this.StateFlag = rs.getString("StateFlag").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.DoneDate = rs.getDate("DoneDate");
			if( rs.getString("DoneTime") == null )
				this.DoneTime = null;
			else
				this.DoneTime = rs.getString("DoneTime").trim();

			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

			if( rs.getString("OldPrtSeq") == null )
				this.OldPrtSeq = null;
			else
				this.OldPrtSeq = rs.getString("OldPrtSeq").trim();

			if( rs.getString("PatchFlag") == null )
				this.PatchFlag = null;
			else
				this.PatchFlag = rs.getString("PatchFlag").trim();

			if( rs.getString("StandbyFlag3") == null )
				this.StandbyFlag3 = null;
			else
				this.StandbyFlag3 = rs.getString("StandbyFlag3").trim();

			if( rs.getString("StandbyFlag4") == null )
				this.StandbyFlag4 = null;
			else
				this.StandbyFlag4 = rs.getString("StandbyFlag4").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOPRTManager2表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOPRTManager2Schema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOPRTManager2Schema getSchema()
	{
		LOPRTManager2Schema aLOPRTManager2Schema = new LOPRTManager2Schema();
		aLOPRTManager2Schema.setSchema(this);
		return aLOPRTManager2Schema;
	}

	public LOPRTManager2DB getDB()
	{
		LOPRTManager2DB aDBOper = new LOPRTManager2DB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOPRTManager2描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PrtSeq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReqCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReqOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExeCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DoneDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DoneTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldPrtSeq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PatchFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag4));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOPRTManager2>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PrtSeq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Code = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ReqCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ReqOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ExeCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ExeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PrtType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			StateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			DoneDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			DoneTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			OldPrtSeq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			PatchFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			StandbyFlag4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOPRTManager2Schema";
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
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtSeq));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("Code"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("ReqCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReqCom));
		}
		if (FCode.equalsIgnoreCase("ReqOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReqOperator));
		}
		if (FCode.equalsIgnoreCase("ExeCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExeCom));
		}
		if (FCode.equalsIgnoreCase("ExeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExeOperator));
		}
		if (FCode.equalsIgnoreCase("PrtType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtType));
		}
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StateFlag));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("DoneDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDoneDate()));
		}
		if (FCode.equalsIgnoreCase("DoneTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoneTime));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
		}
		if (FCode.equalsIgnoreCase("OldPrtSeq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldPrtSeq));
		}
		if (FCode.equalsIgnoreCase("PatchFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PatchFlag));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag3));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag4));
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
				strFieldValue = StrTool.GBKToUnicode(PrtSeq);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Code);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ReqCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ReqOperator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ExeCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ExeOperator);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PrtType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(StateFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDoneDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(DoneTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(OldPrtSeq);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(PatchFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag4);
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

		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtSeq = FValue.trim();
			}
			else
				PrtSeq = null;
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNoType = FValue.trim();
			}
			else
				OtherNoType = null;
		}
		if (FCode.equalsIgnoreCase("Code"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code = FValue.trim();
			}
			else
				Code = null;
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
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("ReqCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReqCom = FValue.trim();
			}
			else
				ReqCom = null;
		}
		if (FCode.equalsIgnoreCase("ReqOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReqOperator = FValue.trim();
			}
			else
				ReqOperator = null;
		}
		if (FCode.equalsIgnoreCase("ExeCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExeCom = FValue.trim();
			}
			else
				ExeCom = null;
		}
		if (FCode.equalsIgnoreCase("ExeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExeOperator = FValue.trim();
			}
			else
				ExeOperator = null;
		}
		if (FCode.equalsIgnoreCase("PrtType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtType = FValue.trim();
			}
			else
				PrtType = null;
		}
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StateFlag = FValue.trim();
			}
			else
				StateFlag = null;
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
		if (FCode.equalsIgnoreCase("DoneDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DoneDate = fDate.getDate( FValue );
			}
			else
				DoneDate = null;
		}
		if (FCode.equalsIgnoreCase("DoneTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DoneTime = FValue.trim();
			}
			else
				DoneTime = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("OldPrtSeq"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldPrtSeq = FValue.trim();
			}
			else
				OldPrtSeq = null;
		}
		if (FCode.equalsIgnoreCase("PatchFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PatchFlag = FValue.trim();
			}
			else
				PatchFlag = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag3 = FValue.trim();
			}
			else
				StandbyFlag3 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag4 = FValue.trim();
			}
			else
				StandbyFlag4 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LOPRTManager2Schema other = (LOPRTManager2Schema)otherObject;
		return
			PrtSeq.equals(other.getPrtSeq())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& Code.equals(other.getCode())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCode.equals(other.getAgentCode())
			&& ReqCom.equals(other.getReqCom())
			&& ReqOperator.equals(other.getReqOperator())
			&& ExeCom.equals(other.getExeCom())
			&& ExeOperator.equals(other.getExeOperator())
			&& PrtType.equals(other.getPrtType())
			&& StateFlag.equals(other.getStateFlag())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(DoneDate).equals(other.getDoneDate())
			&& DoneTime.equals(other.getDoneTime())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& OldPrtSeq.equals(other.getOldPrtSeq())
			&& PatchFlag.equals(other.getPatchFlag())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
			&& StandbyFlag4.equals(other.getStandbyFlag4());
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
		if( strFieldName.equals("PrtSeq") ) {
			return 0;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 1;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 2;
		}
		if( strFieldName.equals("Code") ) {
			return 3;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 4;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 5;
		}
		if( strFieldName.equals("ReqCom") ) {
			return 6;
		}
		if( strFieldName.equals("ReqOperator") ) {
			return 7;
		}
		if( strFieldName.equals("ExeCom") ) {
			return 8;
		}
		if( strFieldName.equals("ExeOperator") ) {
			return 9;
		}
		if( strFieldName.equals("PrtType") ) {
			return 10;
		}
		if( strFieldName.equals("StateFlag") ) {
			return 11;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 12;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 13;
		}
		if( strFieldName.equals("DoneDate") ) {
			return 14;
		}
		if( strFieldName.equals("DoneTime") ) {
			return 15;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 16;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 17;
		}
		if( strFieldName.equals("OldPrtSeq") ) {
			return 18;
		}
		if( strFieldName.equals("PatchFlag") ) {
			return 19;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 20;
		}
		if( strFieldName.equals("StandbyFlag4") ) {
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
				strFieldName = "PrtSeq";
				break;
			case 1:
				strFieldName = "OtherNo";
				break;
			case 2:
				strFieldName = "OtherNoType";
				break;
			case 3:
				strFieldName = "Code";
				break;
			case 4:
				strFieldName = "ManageCom";
				break;
			case 5:
				strFieldName = "AgentCode";
				break;
			case 6:
				strFieldName = "ReqCom";
				break;
			case 7:
				strFieldName = "ReqOperator";
				break;
			case 8:
				strFieldName = "ExeCom";
				break;
			case 9:
				strFieldName = "ExeOperator";
				break;
			case 10:
				strFieldName = "PrtType";
				break;
			case 11:
				strFieldName = "StateFlag";
				break;
			case 12:
				strFieldName = "MakeDate";
				break;
			case 13:
				strFieldName = "MakeTime";
				break;
			case 14:
				strFieldName = "DoneDate";
				break;
			case 15:
				strFieldName = "DoneTime";
				break;
			case 16:
				strFieldName = "StandbyFlag1";
				break;
			case 17:
				strFieldName = "StandbyFlag2";
				break;
			case 18:
				strFieldName = "OldPrtSeq";
				break;
			case 19:
				strFieldName = "PatchFlag";
				break;
			case 20:
				strFieldName = "StandbyFlag3";
				break;
			case 21:
				strFieldName = "StandbyFlag4";
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
		if( strFieldName.equals("PrtSeq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReqCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReqOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExeCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoneDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DoneTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldPrtSeq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PatchFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag4") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
