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
import com.sinosoft.lis.db.LPSubmitApplyTraceDB;

/*
 * <p>ClassName: LPSubmitApplyTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPSubmitApplyTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPSubmitApplyTraceSchema.class);

	// @Field
	/** 序号 */
	private String SerialNo;
	/** 呈报号 */
	private String SubNo;
	/** 团体合同号码 */
	private String GrpContNo;
	/** 保全类型 */
	private String EdorType;
	/** 呈报描述 */
	private String SubDesc;
	/** 呈报人 */
	private String SubPer;
	/** 呈报日期 */
	private Date SubDate;
	/** 呈报机构 */
	private String ManageCom;
	/** 呈报次数 */
	private int SubTimes;
	/** 呈报状态 */
	private String SubState;
	/** 承接机构代码 */
	private String DispDept;
	/** 承接人员编号 */
	private String DispPer;
	/** 处理日期 */
	private Date DealDate;
	/** 处理类型 */
	private String DealType;
	/** 处理意见 */
	private String DealIdea;
	/** 操作员 */
	private String Operator;
	/** 处理完毕标志 */
	private String HasDealed;
	/** 附件内容简述 */
	private String AppText1;
	/** 附件路径 */
	private String AppText2;
	/** 附件3 */
	private String AppText3;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPSubmitApplyTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "SerialNo";
		pk[1] = "SubNo";

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
		LPSubmitApplyTraceSchema cloned = (LPSubmitApplyTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getSubNo()
	{
		return SubNo;
	}
	public void setSubNo(String aSubNo)
	{
		SubNo = aSubNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	/**
	* 0中心到分公司<p>
	* 1分公司到总公司<p>
	* 2总公司到分公司
	*/
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		EdorType = aEdorType;
	}
	/**
	* 是否是本地调查
	*/
	public String getSubDesc()
	{
		return SubDesc;
	}
	public void setSubDesc(String aSubDesc)
	{
		SubDesc = aSubDesc;
	}
	public String getSubPer()
	{
		return SubPer;
	}
	public void setSubPer(String aSubPer)
	{
		SubPer = aSubPer;
	}
	public String getSubDate()
	{
		if( SubDate != null )
			return fDate.getString(SubDate);
		else
			return null;
	}
	public void setSubDate(Date aSubDate)
	{
		SubDate = aSubDate;
	}
	public void setSubDate(String aSubDate)
	{
		if (aSubDate != null && !aSubDate.equals("") )
		{
			SubDate = fDate.getDate( aSubDate );
		}
		else
			SubDate = null;
	}

	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public int getSubTimes()
	{
		return SubTimes;
	}
	public void setSubTimes(int aSubTimes)
	{
		SubTimes = aSubTimes;
	}
	public void setSubTimes(String aSubTimes)
	{
		if (aSubTimes != null && !aSubTimes.equals(""))
		{
			Integer tInteger = new Integer(aSubTimes);
			int i = tInteger.intValue();
			SubTimes = i;
		}
	}

	/**
	* 0-处理确认完成<p>
	* 1-呈报申请<p>
	* 2-处理完成<p>
	* 3-接受处理<p>
	* 4-不予处理
	*/
	public String getSubState()
	{
		return SubState;
	}
	public void setSubState(String aSubState)
	{
		SubState = aSubState;
	}
	public String getDispDept()
	{
		return DispDept;
	}
	public void setDispDept(String aDispDept)
	{
		DispDept = aDispDept;
	}
	public String getDispPer()
	{
		return DispPer;
	}
	public void setDispPer(String aDispPer)
	{
		DispPer = aDispPer;
	}
	public String getDealDate()
	{
		if( DealDate != null )
			return fDate.getString(DealDate);
		else
			return null;
	}
	public void setDealDate(Date aDealDate)
	{
		DealDate = aDealDate;
	}
	public void setDealDate(String aDealDate)
	{
		if (aDealDate != null && !aDealDate.equals("") )
		{
			DealDate = fDate.getDate( aDealDate );
		}
		else
			DealDate = null;
	}

	/**
	* 2给出呈报处理意见<p>
	* 0提起调查<p>
	* 1呈报总公司
	*/
	public String getDealType()
	{
		return DealType;
	}
	public void setDealType(String aDealType)
	{
		DealType = aDealType;
	}
	public String getDealIdea()
	{
		return DealIdea;
	}
	public void setDealIdea(String aDealIdea)
	{
		DealIdea = aDealIdea;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	/**
	* 2:处理完毕  1:有人批复 0：申请
	*/
	public String getHasDealed()
	{
		return HasDealed;
	}
	public void setHasDealed(String aHasDealed)
	{
		HasDealed = aHasDealed;
	}
	/**
	* 附件内容简述
	*/
	public String getAppText1()
	{
		return AppText1;
	}
	public void setAppText1(String aAppText1)
	{
		AppText1 = aAppText1;
	}
	/**
	* 附件的存放路径
	*/
	public String getAppText2()
	{
		return AppText2;
	}
	public void setAppText2(String aAppText2)
	{
		AppText2 = aAppText2;
	}
	public String getAppText3()
	{
		return AppText3;
	}
	public void setAppText3(String aAppText3)
	{
		AppText3 = aAppText3;
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
	* 使用另外一个 LPSubmitApplyTraceSchema 对象给 Schema 赋值
	* @param: aLPSubmitApplyTraceSchema LPSubmitApplyTraceSchema
	**/
	public void setSchema(LPSubmitApplyTraceSchema aLPSubmitApplyTraceSchema)
	{
		this.SerialNo = aLPSubmitApplyTraceSchema.getSerialNo();
		this.SubNo = aLPSubmitApplyTraceSchema.getSubNo();
		this.GrpContNo = aLPSubmitApplyTraceSchema.getGrpContNo();
		this.EdorType = aLPSubmitApplyTraceSchema.getEdorType();
		this.SubDesc = aLPSubmitApplyTraceSchema.getSubDesc();
		this.SubPer = aLPSubmitApplyTraceSchema.getSubPer();
		this.SubDate = fDate.getDate( aLPSubmitApplyTraceSchema.getSubDate());
		this.ManageCom = aLPSubmitApplyTraceSchema.getManageCom();
		this.SubTimes = aLPSubmitApplyTraceSchema.getSubTimes();
		this.SubState = aLPSubmitApplyTraceSchema.getSubState();
		this.DispDept = aLPSubmitApplyTraceSchema.getDispDept();
		this.DispPer = aLPSubmitApplyTraceSchema.getDispPer();
		this.DealDate = fDate.getDate( aLPSubmitApplyTraceSchema.getDealDate());
		this.DealType = aLPSubmitApplyTraceSchema.getDealType();
		this.DealIdea = aLPSubmitApplyTraceSchema.getDealIdea();
		this.Operator = aLPSubmitApplyTraceSchema.getOperator();
		this.HasDealed = aLPSubmitApplyTraceSchema.getHasDealed();
		this.AppText1 = aLPSubmitApplyTraceSchema.getAppText1();
		this.AppText2 = aLPSubmitApplyTraceSchema.getAppText2();
		this.AppText3 = aLPSubmitApplyTraceSchema.getAppText3();
		this.MakeDate = fDate.getDate( aLPSubmitApplyTraceSchema.getMakeDate());
		this.MakeTime = aLPSubmitApplyTraceSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPSubmitApplyTraceSchema.getModifyDate());
		this.ModifyTime = aLPSubmitApplyTraceSchema.getModifyTime();
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

			if( rs.getString("SubNo") == null )
				this.SubNo = null;
			else
				this.SubNo = rs.getString("SubNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("SubDesc") == null )
				this.SubDesc = null;
			else
				this.SubDesc = rs.getString("SubDesc").trim();

			if( rs.getString("SubPer") == null )
				this.SubPer = null;
			else
				this.SubPer = rs.getString("SubPer").trim();

			this.SubDate = rs.getDate("SubDate");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.SubTimes = rs.getInt("SubTimes");
			if( rs.getString("SubState") == null )
				this.SubState = null;
			else
				this.SubState = rs.getString("SubState").trim();

			if( rs.getString("DispDept") == null )
				this.DispDept = null;
			else
				this.DispDept = rs.getString("DispDept").trim();

			if( rs.getString("DispPer") == null )
				this.DispPer = null;
			else
				this.DispPer = rs.getString("DispPer").trim();

			this.DealDate = rs.getDate("DealDate");
			if( rs.getString("DealType") == null )
				this.DealType = null;
			else
				this.DealType = rs.getString("DealType").trim();

			if( rs.getString("DealIdea") == null )
				this.DealIdea = null;
			else
				this.DealIdea = rs.getString("DealIdea").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("HasDealed") == null )
				this.HasDealed = null;
			else
				this.HasDealed = rs.getString("HasDealed").trim();

			if( rs.getString("AppText1") == null )
				this.AppText1 = null;
			else
				this.AppText1 = rs.getString("AppText1").trim();

			if( rs.getString("AppText2") == null )
				this.AppText2 = null;
			else
				this.AppText2 = rs.getString("AppText2").trim();

			if( rs.getString("AppText3") == null )
				this.AppText3 = null;
			else
				this.AppText3 = rs.getString("AppText3").trim();

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
			logger.debug("数据库中的LPSubmitApplyTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPSubmitApplyTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPSubmitApplyTraceSchema getSchema()
	{
		LPSubmitApplyTraceSchema aLPSubmitApplyTraceSchema = new LPSubmitApplyTraceSchema();
		aLPSubmitApplyTraceSchema.setSchema(this);
		return aLPSubmitApplyTraceSchema;
	}

	public LPSubmitApplyTraceDB getDB()
	{
		LPSubmitApplyTraceDB aDBOper = new LPSubmitApplyTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPSubmitApplyTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubPer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SubDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SubTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DispDept)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DispPer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DealDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DealType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DealIdea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HasDealed)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppText1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppText2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppText3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPSubmitApplyTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SubNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SubDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SubPer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SubDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SubTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			SubState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			DispDept = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			DispPer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			DealDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			DealType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			DealIdea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			HasDealed = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AppText1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AppText2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			AppText3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPSubmitApplyTraceSchema";
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
		if (FCode.equalsIgnoreCase("SubNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("SubDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubDesc));
		}
		if (FCode.equalsIgnoreCase("SubPer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubPer));
		}
		if (FCode.equalsIgnoreCase("SubDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSubDate()));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("SubTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubTimes));
		}
		if (FCode.equalsIgnoreCase("SubState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubState));
		}
		if (FCode.equalsIgnoreCase("DispDept"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DispDept));
		}
		if (FCode.equalsIgnoreCase("DispPer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DispPer));
		}
		if (FCode.equalsIgnoreCase("DealDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDealDate()));
		}
		if (FCode.equalsIgnoreCase("DealType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealType));
		}
		if (FCode.equalsIgnoreCase("DealIdea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealIdea));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("HasDealed"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HasDealed));
		}
		if (FCode.equalsIgnoreCase("AppText1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppText1));
		}
		if (FCode.equalsIgnoreCase("AppText2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppText2));
		}
		if (FCode.equalsIgnoreCase("AppText3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppText3));
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
				strFieldValue = StrTool.GBKToUnicode(SubNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SubDesc);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SubPer);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSubDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 8:
				strFieldValue = String.valueOf(SubTimes);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(SubState);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(DispDept);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(DispPer);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDealDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(DealType);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(DealIdea);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(HasDealed);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AppText1);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AppText2);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AppText3);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 23:
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
		if (FCode.equalsIgnoreCase("SubNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubNo = FValue.trim();
			}
			else
				SubNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorType = FValue.trim();
			}
			else
				EdorType = null;
		}
		if (FCode.equalsIgnoreCase("SubDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubDesc = FValue.trim();
			}
			else
				SubDesc = null;
		}
		if (FCode.equalsIgnoreCase("SubPer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubPer = FValue.trim();
			}
			else
				SubPer = null;
		}
		if (FCode.equalsIgnoreCase("SubDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SubDate = fDate.getDate( FValue );
			}
			else
				SubDate = null;
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
		if (FCode.equalsIgnoreCase("SubTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SubTimes = i;
			}
		}
		if (FCode.equalsIgnoreCase("SubState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubState = FValue.trim();
			}
			else
				SubState = null;
		}
		if (FCode.equalsIgnoreCase("DispDept"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DispDept = FValue.trim();
			}
			else
				DispDept = null;
		}
		if (FCode.equalsIgnoreCase("DispPer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DispPer = FValue.trim();
			}
			else
				DispPer = null;
		}
		if (FCode.equalsIgnoreCase("DealDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DealDate = fDate.getDate( FValue );
			}
			else
				DealDate = null;
		}
		if (FCode.equalsIgnoreCase("DealType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealType = FValue.trim();
			}
			else
				DealType = null;
		}
		if (FCode.equalsIgnoreCase("DealIdea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealIdea = FValue.trim();
			}
			else
				DealIdea = null;
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
		if (FCode.equalsIgnoreCase("HasDealed"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HasDealed = FValue.trim();
			}
			else
				HasDealed = null;
		}
		if (FCode.equalsIgnoreCase("AppText1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppText1 = FValue.trim();
			}
			else
				AppText1 = null;
		}
		if (FCode.equalsIgnoreCase("AppText2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppText2 = FValue.trim();
			}
			else
				AppText2 = null;
		}
		if (FCode.equalsIgnoreCase("AppText3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppText3 = FValue.trim();
			}
			else
				AppText3 = null;
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
		LPSubmitApplyTraceSchema other = (LPSubmitApplyTraceSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& SubNo.equals(other.getSubNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& EdorType.equals(other.getEdorType())
			&& SubDesc.equals(other.getSubDesc())
			&& SubPer.equals(other.getSubPer())
			&& fDate.getString(SubDate).equals(other.getSubDate())
			&& ManageCom.equals(other.getManageCom())
			&& SubTimes == other.getSubTimes()
			&& SubState.equals(other.getSubState())
			&& DispDept.equals(other.getDispDept())
			&& DispPer.equals(other.getDispPer())
			&& fDate.getString(DealDate).equals(other.getDealDate())
			&& DealType.equals(other.getDealType())
			&& DealIdea.equals(other.getDealIdea())
			&& Operator.equals(other.getOperator())
			&& HasDealed.equals(other.getHasDealed())
			&& AppText1.equals(other.getAppText1())
			&& AppText2.equals(other.getAppText2())
			&& AppText3.equals(other.getAppText3())
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
		if( strFieldName.equals("SubNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("EdorType") ) {
			return 3;
		}
		if( strFieldName.equals("SubDesc") ) {
			return 4;
		}
		if( strFieldName.equals("SubPer") ) {
			return 5;
		}
		if( strFieldName.equals("SubDate") ) {
			return 6;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 7;
		}
		if( strFieldName.equals("SubTimes") ) {
			return 8;
		}
		if( strFieldName.equals("SubState") ) {
			return 9;
		}
		if( strFieldName.equals("DispDept") ) {
			return 10;
		}
		if( strFieldName.equals("DispPer") ) {
			return 11;
		}
		if( strFieldName.equals("DealDate") ) {
			return 12;
		}
		if( strFieldName.equals("DealType") ) {
			return 13;
		}
		if( strFieldName.equals("DealIdea") ) {
			return 14;
		}
		if( strFieldName.equals("Operator") ) {
			return 15;
		}
		if( strFieldName.equals("HasDealed") ) {
			return 16;
		}
		if( strFieldName.equals("AppText1") ) {
			return 17;
		}
		if( strFieldName.equals("AppText2") ) {
			return 18;
		}
		if( strFieldName.equals("AppText3") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 20;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 23;
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
				strFieldName = "SubNo";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "EdorType";
				break;
			case 4:
				strFieldName = "SubDesc";
				break;
			case 5:
				strFieldName = "SubPer";
				break;
			case 6:
				strFieldName = "SubDate";
				break;
			case 7:
				strFieldName = "ManageCom";
				break;
			case 8:
				strFieldName = "SubTimes";
				break;
			case 9:
				strFieldName = "SubState";
				break;
			case 10:
				strFieldName = "DispDept";
				break;
			case 11:
				strFieldName = "DispPer";
				break;
			case 12:
				strFieldName = "DealDate";
				break;
			case 13:
				strFieldName = "DealType";
				break;
			case 14:
				strFieldName = "DealIdea";
				break;
			case 15:
				strFieldName = "Operator";
				break;
			case 16:
				strFieldName = "HasDealed";
				break;
			case 17:
				strFieldName = "AppText1";
				break;
			case 18:
				strFieldName = "AppText2";
				break;
			case 19:
				strFieldName = "AppText3";
				break;
			case 20:
				strFieldName = "MakeDate";
				break;
			case 21:
				strFieldName = "MakeTime";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
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
		if( strFieldName.equals("SubNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubPer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SubState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DispDept") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DispPer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DealType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealIdea") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HasDealed") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppText1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppText2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppText3") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
