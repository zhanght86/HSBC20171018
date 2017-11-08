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
import com.sinosoft.lis.db.LOPRTManagerDB;

/*
 * <p>ClassName: LOPRTManagerSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LOPRTManagerSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOPRTManagerSchema.class);

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
	/** 预计催办日期 */
	private Date ForMakeDate;
	/** 预计催办时间 */
	private String ForMakeTime;
	/** 实际催办日期 */
	private Date ActMakeDate;
	/** 实际催办时间 */
	private String ActMakeTime;
	/** 备注 */
	private String Remark;
	/** 备用属性字段5 */
	private String StandbyFlag5;
	/** 备用属性字段6 */
	private String StandbyFlag6;
	/** 备用属性字段7 */
	private String StandbyFlag7;

	public static final int FIELDNUM = 30;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOPRTManagerSchema()
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
		LOPRTManagerSchema cloned = (LOPRTManagerSchema)super.clone();
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
	* 在PrintManagerBL类中定义常量<p>
	* <p>
	* 00 --- 个单合同号<p>
	* 01 --- 集体合同号<p>
	* 02 --- 合同号<p>
	* 03 --- 批单号<p>
	* 04 --- 实付收据号<p>
	* 05 -- 保单印刷号
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
	* 26 -----保全延期承保通知书<p>
	* <p>
	* 30 --- 个人红利派发通知书<p>
	* 31 --- 续期缴费通知书<p>
	* 32 --- 个人发票<p>
	* 33 --- 集体发票<p>
	* 34 －－ 团体红利派发通知书<p>
	* <p>
	* 40 -- 续保拒保通知书<p>
	* 41 -- 自动垫交通知书<p>
	* 42 -- 效力中止通知书<p>
	* 43 -- 续保体检通知书<p>
	* 44 -- 续保面见通知书<p>
	* 45 -- 续保核保通知书<p>
	* 46 -- 续保延期承保通知书<p>
	* 47 -- 续保催收通知书<p>
	* 48 -- 续期划帐成功通知书<p>
	* 49 -- 续保确认通知书<p>
	* <p>
	* <p>
	* 50 －－ 团体核保通知书<p>
	* 51 －－ 拒保通知书<p>
	* 52 －－ 撤单通知书<p>
	* 53 －－ 延期通知书<p>
	* <p>
	* <p>
	* <p>
	* 60 －－ 团体询价拒报通知书<p>
	* 61 －－ 团体询价成功通知书<p>
	* 62 －－ 团体询价撤单通知书<p>
	* 63 －－ 团体询价延期通知书<p>
	* 64 －－ 团体询价补充材料通知书
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
	* 如果单据类型为03（体检通知书），该字段存放需要体检的客户号码。<p>
	* <p>
	* 如果单据类型为<p>
	* 00 －－ 拒保通知书<p>
	* 06 －－ 延期承保通知书<p>
	* 08 －－ 新契约退费通知书（就是首期溢交保费通知书）<p>
	* 09 －－ 撤单通知书（一般指在催办通知书发后1个月还没有返回的投保单，需要打印该通知书，通知客户来退保）<p>
	* 则该字段存放保单号。<p>
	* 32（续期发票） -- 该字段存放交费通知书号
	*/
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		StandbyFlag1 = aStandbyFlag1;
	}
	/**
	* 续期续保：存放交费收据号（即发票号）
	*/
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
	/**
	* code == 32 （续期发票） 此字段存  交费对应日
	*/
	public String getStandbyFlag3()
	{
		return StandbyFlag3;
	}
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		StandbyFlag3 = aStandbyFlag3;
	}
	/**
	* code == 32 （续期发票） 此字段存  支票金额
	*/
	public String getStandbyFlag4()
	{
		return StandbyFlag4;
	}
	public void setStandbyFlag4(String aStandbyFlag4)
	{
		StandbyFlag4 = aStandbyFlag4;
	}
	public String getForMakeDate()
	{
		if( ForMakeDate != null )
			return fDate.getString(ForMakeDate);
		else
			return null;
	}
	public void setForMakeDate(Date aForMakeDate)
	{
		ForMakeDate = aForMakeDate;
	}
	public void setForMakeDate(String aForMakeDate)
	{
		if (aForMakeDate != null && !aForMakeDate.equals("") )
		{
			ForMakeDate = fDate.getDate( aForMakeDate );
		}
		else
			ForMakeDate = null;
	}

	public String getForMakeTime()
	{
		return ForMakeTime;
	}
	public void setForMakeTime(String aForMakeTime)
	{
		ForMakeTime = aForMakeTime;
	}
	public String getActMakeDate()
	{
		if( ActMakeDate != null )
			return fDate.getString(ActMakeDate);
		else
			return null;
	}
	public void setActMakeDate(Date aActMakeDate)
	{
		ActMakeDate = aActMakeDate;
	}
	public void setActMakeDate(String aActMakeDate)
	{
		if (aActMakeDate != null && !aActMakeDate.equals("") )
		{
			ActMakeDate = fDate.getDate( aActMakeDate );
		}
		else
			ActMakeDate = null;
	}

	public String getActMakeTime()
	{
		return ActMakeTime;
	}
	public void setActMakeTime(String aActMakeTime)
	{
		ActMakeTime = aActMakeTime;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getStandbyFlag5()
	{
		return StandbyFlag5;
	}
	public void setStandbyFlag5(String aStandbyFlag5)
	{
		StandbyFlag5 = aStandbyFlag5;
	}
	public String getStandbyFlag6()
	{
		return StandbyFlag6;
	}
	public void setStandbyFlag6(String aStandbyFlag6)
	{
		StandbyFlag6 = aStandbyFlag6;
	}
	public String getStandbyFlag7()
	{
		return StandbyFlag7;
	}
	public void setStandbyFlag7(String aStandbyFlag7)
	{
		StandbyFlag7 = aStandbyFlag7;
	}

	/**
	* 使用另外一个 LOPRTManagerSchema 对象给 Schema 赋值
	* @param: aLOPRTManagerSchema LOPRTManagerSchema
	**/
	public void setSchema(LOPRTManagerSchema aLOPRTManagerSchema)
	{
		this.PrtSeq = aLOPRTManagerSchema.getPrtSeq();
		this.OtherNo = aLOPRTManagerSchema.getOtherNo();
		this.OtherNoType = aLOPRTManagerSchema.getOtherNoType();
		this.Code = aLOPRTManagerSchema.getCode();
		this.ManageCom = aLOPRTManagerSchema.getManageCom();
		this.AgentCode = aLOPRTManagerSchema.getAgentCode();
		this.ReqCom = aLOPRTManagerSchema.getReqCom();
		this.ReqOperator = aLOPRTManagerSchema.getReqOperator();
		this.ExeCom = aLOPRTManagerSchema.getExeCom();
		this.ExeOperator = aLOPRTManagerSchema.getExeOperator();
		this.PrtType = aLOPRTManagerSchema.getPrtType();
		this.StateFlag = aLOPRTManagerSchema.getStateFlag();
		this.MakeDate = fDate.getDate( aLOPRTManagerSchema.getMakeDate());
		this.MakeTime = aLOPRTManagerSchema.getMakeTime();
		this.DoneDate = fDate.getDate( aLOPRTManagerSchema.getDoneDate());
		this.DoneTime = aLOPRTManagerSchema.getDoneTime();
		this.StandbyFlag1 = aLOPRTManagerSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLOPRTManagerSchema.getStandbyFlag2();
		this.OldPrtSeq = aLOPRTManagerSchema.getOldPrtSeq();
		this.PatchFlag = aLOPRTManagerSchema.getPatchFlag();
		this.StandbyFlag3 = aLOPRTManagerSchema.getStandbyFlag3();
		this.StandbyFlag4 = aLOPRTManagerSchema.getStandbyFlag4();
		this.ForMakeDate = fDate.getDate( aLOPRTManagerSchema.getForMakeDate());
		this.ForMakeTime = aLOPRTManagerSchema.getForMakeTime();
		this.ActMakeDate = fDate.getDate( aLOPRTManagerSchema.getActMakeDate());
		this.ActMakeTime = aLOPRTManagerSchema.getActMakeTime();
		this.Remark = aLOPRTManagerSchema.getRemark();
		this.StandbyFlag5 = aLOPRTManagerSchema.getStandbyFlag5();
		this.StandbyFlag6 = aLOPRTManagerSchema.getStandbyFlag6();
		this.StandbyFlag7 = aLOPRTManagerSchema.getStandbyFlag7();
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

			this.ForMakeDate = rs.getDate("ForMakeDate");
			if( rs.getString("ForMakeTime") == null )
				this.ForMakeTime = null;
			else
				this.ForMakeTime = rs.getString("ForMakeTime").trim();

			this.ActMakeDate = rs.getDate("ActMakeDate");
			if( rs.getString("ActMakeTime") == null )
				this.ActMakeTime = null;
			else
				this.ActMakeTime = rs.getString("ActMakeTime").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("StandbyFlag5") == null )
				this.StandbyFlag5 = null;
			else
				this.StandbyFlag5 = rs.getString("StandbyFlag5").trim();

			if( rs.getString("StandbyFlag6") == null )
				this.StandbyFlag6 = null;
			else
				this.StandbyFlag6 = rs.getString("StandbyFlag6").trim();

			if( rs.getString("StandbyFlag7") == null )
				this.StandbyFlag7 = null;
			else
				this.StandbyFlag7 = rs.getString("StandbyFlag7").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOPRTManager表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOPRTManagerSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOPRTManagerSchema getSchema()
	{
		LOPRTManagerSchema aLOPRTManagerSchema = new LOPRTManagerSchema();
		aLOPRTManagerSchema.setSchema(this);
		return aLOPRTManagerSchema;
	}

	public LOPRTManagerDB getDB()
	{
		LOPRTManagerDB aDBOper = new LOPRTManagerDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOPRTManager描述/A>表字段
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
		strReturn.append(StrTool.cTrim(StandbyFlag4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ForMakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ForMakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ActMakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActMakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag7));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOPRTManager>历史记账凭证主表信息</A>表字段
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
			ForMakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ForMakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ActMakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			ActMakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			StandbyFlag5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			StandbyFlag6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			StandbyFlag7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOPRTManagerSchema";
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
		if (FCode.equalsIgnoreCase("ForMakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getForMakeDate()));
		}
		if (FCode.equalsIgnoreCase("ForMakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ForMakeTime));
		}
		if (FCode.equalsIgnoreCase("ActMakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getActMakeDate()));
		}
		if (FCode.equalsIgnoreCase("ActMakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActMakeTime));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag5));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag6));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag7));
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
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getForMakeDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ForMakeTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getActMakeDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ActMakeTime);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag5);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag6);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag7);
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
		if (FCode.equalsIgnoreCase("ForMakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ForMakeDate = fDate.getDate( FValue );
			}
			else
				ForMakeDate = null;
		}
		if (FCode.equalsIgnoreCase("ForMakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ForMakeTime = FValue.trim();
			}
			else
				ForMakeTime = null;
		}
		if (FCode.equalsIgnoreCase("ActMakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ActMakeDate = fDate.getDate( FValue );
			}
			else
				ActMakeDate = null;
		}
		if (FCode.equalsIgnoreCase("ActMakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActMakeTime = FValue.trim();
			}
			else
				ActMakeTime = null;
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
		if (FCode.equalsIgnoreCase("StandbyFlag5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag5 = FValue.trim();
			}
			else
				StandbyFlag5 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag6 = FValue.trim();
			}
			else
				StandbyFlag6 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag7 = FValue.trim();
			}
			else
				StandbyFlag7 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LOPRTManagerSchema other = (LOPRTManagerSchema)otherObject;
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
			&& StandbyFlag4.equals(other.getStandbyFlag4())
			&& fDate.getString(ForMakeDate).equals(other.getForMakeDate())
			&& ForMakeTime.equals(other.getForMakeTime())
			&& fDate.getString(ActMakeDate).equals(other.getActMakeDate())
			&& ActMakeTime.equals(other.getActMakeTime())
			&& Remark.equals(other.getRemark())
			&& StandbyFlag5.equals(other.getStandbyFlag5())
			&& StandbyFlag6.equals(other.getStandbyFlag6())
			&& StandbyFlag7.equals(other.getStandbyFlag7());
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
		if( strFieldName.equals("ForMakeDate") ) {
			return 22;
		}
		if( strFieldName.equals("ForMakeTime") ) {
			return 23;
		}
		if( strFieldName.equals("ActMakeDate") ) {
			return 24;
		}
		if( strFieldName.equals("ActMakeTime") ) {
			return 25;
		}
		if( strFieldName.equals("Remark") ) {
			return 26;
		}
		if( strFieldName.equals("StandbyFlag5") ) {
			return 27;
		}
		if( strFieldName.equals("StandbyFlag6") ) {
			return 28;
		}
		if( strFieldName.equals("StandbyFlag7") ) {
			return 29;
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
			case 22:
				strFieldName = "ForMakeDate";
				break;
			case 23:
				strFieldName = "ForMakeTime";
				break;
			case 24:
				strFieldName = "ActMakeDate";
				break;
			case 25:
				strFieldName = "ActMakeTime";
				break;
			case 26:
				strFieldName = "Remark";
				break;
			case 27:
				strFieldName = "StandbyFlag5";
				break;
			case 28:
				strFieldName = "StandbyFlag6";
				break;
			case 29:
				strFieldName = "StandbyFlag7";
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
		if( strFieldName.equals("ForMakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ForMakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActMakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ActMakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag7") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
