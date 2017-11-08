

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.PD_LMRiskEdorItemDB;

/*
 * <p>ClassName: PD_LMRiskEdorItemSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMRiskEdorItemSchema implements Schema, Cloneable
{
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 保全项目编码 */
	private String EdorCode;
	/** 保全申请对象 */
	private String AppObj;
	/** 险种版本 */
	private String RiskVer;
	/** 险种名称 */
	private String RiskName;
	/** 保全项目名称 */
	private String EdorName;
	/** 冲减保费标记 */
	private String CutPremFlag;
	/** 变动标记 */
	private String ChgFlag;
	/** 改金额标记 */
	private String ChgValueFlag;
	/** 涉及计算 */
	private String CalFlag;
	/** 界面中是否显示项目明细 */
	private String NeedDetail;
	/** 保全计算时参考的时间间隔 */
	private String IntvType;
	/** 集体保全批单中是否需要打印保全清单 */
	private String GrpNeedList;
	/** 保全权限 */
	private String EdorPopedom;
	/** 财务处理类型 */
	private String FinType;
	/** 保全项目属性 */
	private String EdorProperty;
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
	/** Standbyflag1 */
	private String Standbyflag1;
	/** Standbyflag2 */
	private String Standbyflag2;
	/** Standbyflag3 */
	private int Standbyflag3;
	/** Standbyflag4 */
	private int Standbyflag4;
	/** Standbyflag5 */
	private double Standbyflag5;
	/** Standbyflag6 */
	private double Standbyflag6;

	public static final int FIELDNUM = 27;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMRiskEdorItemSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "RiskCode";
		pk[1] = "EdorCode";

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
		PD_LMRiskEdorItemSchema cloned = (PD_LMRiskEdorItemSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getEdorCode()
	{
		return EdorCode;
	}
	public void setEdorCode(String aEdorCode)
	{
		EdorCode = aEdorCode;
	}
	/**
	* C--总单(Contract)、G--分单(Group)、I--个单(Individual)
	*/
	public String getAppObj()
	{
		return AppObj;
	}
	public void setAppObj(String aAppObj)
	{
		AppObj = aAppObj;
	}
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	public String getRiskName()
	{
		return RiskName;
	}
	public void setRiskName(String aRiskName)
	{
		RiskName = aRiskName;
	}
	public String getEdorName()
	{
		return EdorName;
	}
	public void setEdorName(String aEdorName)
	{
		EdorName = aEdorName;
	}
	/**
	* Y-- 冲减保费 N-- 不冲减保费
	*/
	public String getCutPremFlag()
	{
		return CutPremFlag;
	}
	public void setCutPremFlag(String aCutPremFlag)
	{
		CutPremFlag = aCutPremFlag;
	}
	/**
	* 1-- 只允许改小 2-- 只允许改大
	*/
	public String getChgFlag()
	{
		return ChgFlag;
	}
	public void setChgFlag(String aChgFlag)
	{
		ChgFlag = aChgFlag;
	}
	/**
	* N--不允许改金额 Y--允许改金额
	*/
	public String getChgValueFlag()
	{
		return ChgValueFlag;
	}
	public void setChgValueFlag(String aChgValueFlag)
	{
		ChgValueFlag = aChgValueFlag;
	}
	/**
	* Y-- 涉及；N-- 不涉及
	*/
	public String getCalFlag()
	{
		return CalFlag;
	}
	public void setCalFlag(String aCalFlag)
	{
		CalFlag = aCalFlag;
	}
	/**
	* 0 －－ 不需要项目明细 1 －－ 需要项目明细
	*/
	public String getNeedDetail()
	{
		return NeedDetail;
	}
	public void setNeedDetail(String aNeedDetail)
	{
		NeedDetail = aNeedDetail;
	}
	/**
	* Y －－ Year M －－ Month D －－ Day
	*/
	public String getIntvType()
	{
		return IntvType;
	}
	public void setIntvType(String aIntvType)
	{
		IntvType = aIntvType;
	}
	/**
	* Y －－ 需要打印 N －－ 不需要打印
	*/
	public String getGrpNeedList()
	{
		return GrpNeedList;
	}
	public void setGrpNeedList(String aGrpNeedList)
	{
		GrpNeedList = aGrpNeedList;
	}
	/**
	* EdorPopedom A A级核保员 EdorPopedom B B级核保员 EdorPopedom C C级核保员 EdorPopedom D D级核保员 EdorPopedom E E级核保员 EdorPopedom F F级核保员
	*/
	public String getEdorPopedom()
	{
		return EdorPopedom;
	}
	public void setEdorPopedom(String aEdorPopedom)
	{
		EdorPopedom = aEdorPopedom;
	}
	/**
	* BF--补费 TF--退费 TB--退保金 JK--借款 HK--还款 LX--利息 GB--工本费
	*/
	public String getFinType()
	{
		return FinType;
	}
	public void setFinType(String aFinType)
	{
		FinType = aFinType;
	}
	public String getEdorProperty()
	{
		return EdorProperty;
	}
	public void setEdorProperty(String aEdorProperty)
	{
		EdorProperty = aEdorProperty;
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
	public String getStandbyflag1()
	{
		return Standbyflag1;
	}
	public void setStandbyflag1(String aStandbyflag1)
	{
		Standbyflag1 = aStandbyflag1;
	}
	public String getStandbyflag2()
	{
		return Standbyflag2;
	}
	public void setStandbyflag2(String aStandbyflag2)
	{
		Standbyflag2 = aStandbyflag2;
	}
	public int getStandbyflag3()
	{
		return Standbyflag3;
	}
	public void setStandbyflag3(int aStandbyflag3)
	{
		Standbyflag3 = aStandbyflag3;
	}
	public void setStandbyflag3(String aStandbyflag3)
	{
		if (aStandbyflag3 != null && !aStandbyflag3.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag3);
			int i = tInteger.intValue();
			Standbyflag3 = i;
		}
	}

	public int getStandbyflag4()
	{
		return Standbyflag4;
	}
	public void setStandbyflag4(int aStandbyflag4)
	{
		Standbyflag4 = aStandbyflag4;
	}
	public void setStandbyflag4(String aStandbyflag4)
	{
		if (aStandbyflag4 != null && !aStandbyflag4.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag4);
			int i = tInteger.intValue();
			Standbyflag4 = i;
		}
	}

	public double getStandbyflag5()
	{
		return Standbyflag5;
	}
	public void setStandbyflag5(double aStandbyflag5)
	{
		Standbyflag5 = aStandbyflag5;
	}
	public void setStandbyflag5(String aStandbyflag5)
	{
		if (aStandbyflag5 != null && !aStandbyflag5.equals(""))
		{
			Double tDouble = new Double(aStandbyflag5);
			double d = tDouble.doubleValue();
			Standbyflag5 = d;
		}
	}

	public double getStandbyflag6()
	{
		return Standbyflag6;
	}
	public void setStandbyflag6(double aStandbyflag6)
	{
		Standbyflag6 = aStandbyflag6;
	}
	public void setStandbyflag6(String aStandbyflag6)
	{
		if (aStandbyflag6 != null && !aStandbyflag6.equals(""))
		{
			Double tDouble = new Double(aStandbyflag6);
			double d = tDouble.doubleValue();
			Standbyflag6 = d;
		}
	}


	/**
	* 使用另外一个 PD_LMRiskEdorItemSchema 对象给 Schema 赋值
	* @param: aPD_LMRiskEdorItemSchema PD_LMRiskEdorItemSchema
	**/
	public void setSchema(PD_LMRiskEdorItemSchema aPD_LMRiskEdorItemSchema)
	{
		this.RiskCode = aPD_LMRiskEdorItemSchema.getRiskCode();
		this.EdorCode = aPD_LMRiskEdorItemSchema.getEdorCode();
		this.AppObj = aPD_LMRiskEdorItemSchema.getAppObj();
		this.RiskVer = aPD_LMRiskEdorItemSchema.getRiskVer();
		this.RiskName = aPD_LMRiskEdorItemSchema.getRiskName();
		this.EdorName = aPD_LMRiskEdorItemSchema.getEdorName();
		this.CutPremFlag = aPD_LMRiskEdorItemSchema.getCutPremFlag();
		this.ChgFlag = aPD_LMRiskEdorItemSchema.getChgFlag();
		this.ChgValueFlag = aPD_LMRiskEdorItemSchema.getChgValueFlag();
		this.CalFlag = aPD_LMRiskEdorItemSchema.getCalFlag();
		this.NeedDetail = aPD_LMRiskEdorItemSchema.getNeedDetail();
		this.IntvType = aPD_LMRiskEdorItemSchema.getIntvType();
		this.GrpNeedList = aPD_LMRiskEdorItemSchema.getGrpNeedList();
		this.EdorPopedom = aPD_LMRiskEdorItemSchema.getEdorPopedom();
		this.FinType = aPD_LMRiskEdorItemSchema.getFinType();
		this.EdorProperty = aPD_LMRiskEdorItemSchema.getEdorProperty();
		this.Operator = aPD_LMRiskEdorItemSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMRiskEdorItemSchema.getMakeDate());
		this.MakeTime = aPD_LMRiskEdorItemSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMRiskEdorItemSchema.getModifyDate());
		this.ModifyTime = aPD_LMRiskEdorItemSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMRiskEdorItemSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMRiskEdorItemSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMRiskEdorItemSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMRiskEdorItemSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMRiskEdorItemSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMRiskEdorItemSchema.getStandbyflag6();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("EdorCode") == null )
				this.EdorCode = null;
			else
				this.EdorCode = rs.getString("EdorCode").trim();

			if( rs.getString("AppObj") == null )
				this.AppObj = null;
			else
				this.AppObj = rs.getString("AppObj").trim();

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("RiskName") == null )
				this.RiskName = null;
			else
				this.RiskName = rs.getString("RiskName").trim();

			if( rs.getString("EdorName") == null )
				this.EdorName = null;
			else
				this.EdorName = rs.getString("EdorName").trim();

			if( rs.getString("CutPremFlag") == null )
				this.CutPremFlag = null;
			else
				this.CutPremFlag = rs.getString("CutPremFlag").trim();

			if( rs.getString("ChgFlag") == null )
				this.ChgFlag = null;
			else
				this.ChgFlag = rs.getString("ChgFlag").trim();

			if( rs.getString("ChgValueFlag") == null )
				this.ChgValueFlag = null;
			else
				this.ChgValueFlag = rs.getString("ChgValueFlag").trim();

			if( rs.getString("CalFlag") == null )
				this.CalFlag = null;
			else
				this.CalFlag = rs.getString("CalFlag").trim();

			if( rs.getString("NeedDetail") == null )
				this.NeedDetail = null;
			else
				this.NeedDetail = rs.getString("NeedDetail").trim();

			if( rs.getString("IntvType") == null )
				this.IntvType = null;
			else
				this.IntvType = rs.getString("IntvType").trim();

			if( rs.getString("GrpNeedList") == null )
				this.GrpNeedList = null;
			else
				this.GrpNeedList = rs.getString("GrpNeedList").trim();

			if( rs.getString("EdorPopedom") == null )
				this.EdorPopedom = null;
			else
				this.EdorPopedom = rs.getString("EdorPopedom").trim();

			if( rs.getString("FinType") == null )
				this.FinType = null;
			else
				this.FinType = rs.getString("FinType").trim();

			if( rs.getString("EdorProperty") == null )
				this.EdorProperty = null;
			else
				this.EdorProperty = rs.getString("EdorProperty").trim();

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

			if( rs.getString("Standbyflag1") == null )
				this.Standbyflag1 = null;
			else
				this.Standbyflag1 = rs.getString("Standbyflag1").trim();

			if( rs.getString("Standbyflag2") == null )
				this.Standbyflag2 = null;
			else
				this.Standbyflag2 = rs.getString("Standbyflag2").trim();

			this.Standbyflag3 = rs.getInt("Standbyflag3");
			this.Standbyflag4 = rs.getInt("Standbyflag4");
			this.Standbyflag5 = rs.getDouble("Standbyflag5");
			this.Standbyflag6 = rs.getDouble("Standbyflag6");
		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的PD_LMRiskEdorItem表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskEdorItemSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMRiskEdorItemSchema getSchema()
	{
		PD_LMRiskEdorItemSchema aPD_LMRiskEdorItemSchema = new PD_LMRiskEdorItemSchema();
		aPD_LMRiskEdorItemSchema.setSchema(this);
		return aPD_LMRiskEdorItemSchema;
	}

	public PD_LMRiskEdorItemDB getDB()
	{
		PD_LMRiskEdorItemDB aDBOper = new PD_LMRiskEdorItemDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskEdorItem描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppObj)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CutPremFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChgFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChgValueFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedDetail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IntvType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNeedList)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FinType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorProperty)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag6));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskEdorItem>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AppObj = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			EdorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CutPremFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ChgFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ChgValueFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			NeedDetail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			IntvType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			GrpNeedList = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			EdorPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			FinType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			EdorProperty = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskEdorItemSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("EdorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorCode));
		}
		if (FCode.equalsIgnoreCase("AppObj"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppObj));
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskName));
		}
		if (FCode.equalsIgnoreCase("EdorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorName));
		}
		if (FCode.equalsIgnoreCase("CutPremFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CutPremFlag));
		}
		if (FCode.equalsIgnoreCase("ChgFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgFlag));
		}
		if (FCode.equalsIgnoreCase("ChgValueFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgValueFlag));
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFlag));
		}
		if (FCode.equalsIgnoreCase("NeedDetail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedDetail));
		}
		if (FCode.equalsIgnoreCase("IntvType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IntvType));
		}
		if (FCode.equalsIgnoreCase("GrpNeedList"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNeedList));
		}
		if (FCode.equalsIgnoreCase("EdorPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorPopedom));
		}
		if (FCode.equalsIgnoreCase("FinType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinType));
		}
		if (FCode.equalsIgnoreCase("EdorProperty"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorProperty));
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag1));
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag2));
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag3));
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag4));
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag5));
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag6));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AppObj);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(EdorName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CutPremFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ChgFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ChgValueFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CalFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(NeedDetail);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(IntvType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(GrpNeedList);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(EdorPopedom);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(FinType);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(EdorProperty);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 23:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 24:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 25:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 26:
				strFieldValue = String.valueOf(Standbyflag6);
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("EdorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorCode = FValue.trim();
			}
			else
				EdorCode = null;
		}
		if (FCode.equalsIgnoreCase("AppObj"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppObj = FValue.trim();
			}
			else
				AppObj = null;
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskName = FValue.trim();
			}
			else
				RiskName = null;
		}
		if (FCode.equalsIgnoreCase("EdorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorName = FValue.trim();
			}
			else
				EdorName = null;
		}
		if (FCode.equalsIgnoreCase("CutPremFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CutPremFlag = FValue.trim();
			}
			else
				CutPremFlag = null;
		}
		if (FCode.equalsIgnoreCase("ChgFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChgFlag = FValue.trim();
			}
			else
				ChgFlag = null;
		}
		if (FCode.equalsIgnoreCase("ChgValueFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChgValueFlag = FValue.trim();
			}
			else
				ChgValueFlag = null;
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFlag = FValue.trim();
			}
			else
				CalFlag = null;
		}
		if (FCode.equalsIgnoreCase("NeedDetail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedDetail = FValue.trim();
			}
			else
				NeedDetail = null;
		}
		if (FCode.equalsIgnoreCase("IntvType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IntvType = FValue.trim();
			}
			else
				IntvType = null;
		}
		if (FCode.equalsIgnoreCase("GrpNeedList"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpNeedList = FValue.trim();
			}
			else
				GrpNeedList = null;
		}
		if (FCode.equalsIgnoreCase("EdorPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorPopedom = FValue.trim();
			}
			else
				EdorPopedom = null;
		}
		if (FCode.equalsIgnoreCase("FinType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinType = FValue.trim();
			}
			else
				FinType = null;
		}
		if (FCode.equalsIgnoreCase("EdorProperty"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorProperty = FValue.trim();
			}
			else
				EdorProperty = null;
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag1 = FValue.trim();
			}
			else
				Standbyflag1 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag2 = FValue.trim();
			}
			else
				Standbyflag2 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag3 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag4 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag6 = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMRiskEdorItemSchema other = (PD_LMRiskEdorItemSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& EdorCode.equals(other.getEdorCode())
			&& AppObj.equals(other.getAppObj())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskName.equals(other.getRiskName())
			&& EdorName.equals(other.getEdorName())
			&& CutPremFlag.equals(other.getCutPremFlag())
			&& ChgFlag.equals(other.getChgFlag())
			&& ChgValueFlag.equals(other.getChgValueFlag())
			&& CalFlag.equals(other.getCalFlag())
			&& NeedDetail.equals(other.getNeedDetail())
			&& IntvType.equals(other.getIntvType())
			&& GrpNeedList.equals(other.getGrpNeedList())
			&& EdorPopedom.equals(other.getEdorPopedom())
			&& FinType.equals(other.getFinType())
			&& EdorProperty.equals(other.getEdorProperty())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Standbyflag1.equals(other.getStandbyflag1())
			&& Standbyflag2.equals(other.getStandbyflag2())
			&& Standbyflag3 == other.getStandbyflag3()
			&& Standbyflag4 == other.getStandbyflag4()
			&& Standbyflag5 == other.getStandbyflag5()
			&& Standbyflag6 == other.getStandbyflag6();
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("EdorCode") ) {
			return 1;
		}
		if( strFieldName.equals("AppObj") ) {
			return 2;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 3;
		}
		if( strFieldName.equals("RiskName") ) {
			return 4;
		}
		if( strFieldName.equals("EdorName") ) {
			return 5;
		}
		if( strFieldName.equals("CutPremFlag") ) {
			return 6;
		}
		if( strFieldName.equals("ChgFlag") ) {
			return 7;
		}
		if( strFieldName.equals("ChgValueFlag") ) {
			return 8;
		}
		if( strFieldName.equals("CalFlag") ) {
			return 9;
		}
		if( strFieldName.equals("NeedDetail") ) {
			return 10;
		}
		if( strFieldName.equals("IntvType") ) {
			return 11;
		}
		if( strFieldName.equals("GrpNeedList") ) {
			return 12;
		}
		if( strFieldName.equals("EdorPopedom") ) {
			return 13;
		}
		if( strFieldName.equals("FinType") ) {
			return 14;
		}
		if( strFieldName.equals("EdorProperty") ) {
			return 15;
		}
		if( strFieldName.equals("Operator") ) {
			return 16;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 17;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 20;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 21;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 22;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 23;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 24;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 25;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 26;
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "EdorCode";
				break;
			case 2:
				strFieldName = "AppObj";
				break;
			case 3:
				strFieldName = "RiskVer";
				break;
			case 4:
				strFieldName = "RiskName";
				break;
			case 5:
				strFieldName = "EdorName";
				break;
			case 6:
				strFieldName = "CutPremFlag";
				break;
			case 7:
				strFieldName = "ChgFlag";
				break;
			case 8:
				strFieldName = "ChgValueFlag";
				break;
			case 9:
				strFieldName = "CalFlag";
				break;
			case 10:
				strFieldName = "NeedDetail";
				break;
			case 11:
				strFieldName = "IntvType";
				break;
			case 12:
				strFieldName = "GrpNeedList";
				break;
			case 13:
				strFieldName = "EdorPopedom";
				break;
			case 14:
				strFieldName = "FinType";
				break;
			case 15:
				strFieldName = "EdorProperty";
				break;
			case 16:
				strFieldName = "Operator";
				break;
			case 17:
				strFieldName = "MakeDate";
				break;
			case 18:
				strFieldName = "MakeTime";
				break;
			case 19:
				strFieldName = "ModifyDate";
				break;
			case 20:
				strFieldName = "ModifyTime";
				break;
			case 21:
				strFieldName = "Standbyflag1";
				break;
			case 22:
				strFieldName = "Standbyflag2";
				break;
			case 23:
				strFieldName = "Standbyflag3";
				break;
			case 24:
				strFieldName = "Standbyflag4";
				break;
			case 25:
				strFieldName = "Standbyflag5";
				break;
			case 26:
				strFieldName = "Standbyflag6";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppObj") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CutPremFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChgFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChgValueFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedDetail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IntvType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNeedList") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorProperty") ) {
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
		if( strFieldName.equals("Standbyflag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 24:
				nFieldType = Schema.TYPE_INT;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 26:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
