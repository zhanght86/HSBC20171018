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
import com.sinosoft.lis.db.LOBonusMainDB;

/*
 * <p>ClassName: LOBonusMainSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 分红管理
 */
public class LOBonusMainSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOBonusMainSchema.class);

	// @Field
	/** 红利分配组号 */
	private int GroupID;
	/** 红利分配会计年度 */
	private int FiscalYear;
	/** 该组的可分配盈余 */
	private double DistributeValue;
	/** 该组的红利分配比例 */
	private double DistributeRate;
	/** 计算状态 */
	private String ComputeState;
	/** 红利结算开始日期 */
	private Date BonusStartDate;
	/** 红利结算开始时间 */
	private String BonusStartTime;
	/** 红利结算结束日期 */
	private Date BonusEndDate;
	/** 红利结算结束时间 */
	private String BonusEndTime;
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
	/** 红利结算时刻的红利系数和 */
	private double BonusCoefSum;
	/** 红利分配组类型 */
	private String BonusGroupType;
	/** 红利计算的限制条件 */
	private String BonusCondition;
	/** 状态 */
	private String State;

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOBonusMainSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "GroupID";
		pk[1] = "FiscalYear";

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
		LOBonusMainSchema cloned = (LOBonusMainSchema)super.clone();
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
	* 每个分配的组都有一个组号
	*/
	public int getGroupID()
	{
		return GroupID;
	}
	public void setGroupID(int aGroupID)
	{
		GroupID = aGroupID;
	}
	public void setGroupID(String aGroupID)
	{
		if (aGroupID != null && !aGroupID.equals(""))
		{
			Integer tInteger = new Integer(aGroupID);
			int i = tInteger.intValue();
			GroupID = i;
		}
	}

	/**
	* 导入红利分配时的会计年度。（该字段在导入每年的可分配盈余时同时导入）
	*/
	public int getFiscalYear()
	{
		return FiscalYear;
	}
	public void setFiscalYear(int aFiscalYear)
	{
		FiscalYear = aFiscalYear;
	}
	public void setFiscalYear(String aFiscalYear)
	{
		if (aFiscalYear != null && !aFiscalYear.equals(""))
		{
			Integer tInteger = new Integer(aFiscalYear);
			int i = tInteger.intValue();
			FiscalYear = i;
		}
	}

	/**
	* 可分配盈余由精算提供。
	*/
	public double getDistributeValue()
	{
		return DistributeValue;
	}
	public void setDistributeValue(double aDistributeValue)
	{
		DistributeValue = aDistributeValue;
	}
	public void setDistributeValue(String aDistributeValue)
	{
		if (aDistributeValue != null && !aDistributeValue.equals(""))
		{
			Double tDouble = new Double(aDistributeValue);
			double d = tDouble.doubleValue();
			DistributeValue = d;
		}
	}

	/**
	* 精算提供。<p>
	* 为公司确定的红利分配比例，一般不低于70%；
	*/
	public double getDistributeRate()
	{
		return DistributeRate;
	}
	public void setDistributeRate(double aDistributeRate)
	{
		DistributeRate = aDistributeRate;
	}
	public void setDistributeRate(String aDistributeRate)
	{
		if (aDistributeRate != null && !aDistributeRate.equals(""))
		{
			Double tDouble = new Double(aDistributeRate);
			double d = tDouble.doubleValue();
			DistributeRate = d;
		}
	}

	/**
	* 表示该红利是否已经完成结算。<p>
	* 0  开始校验计算可行性(条件是否具备)<p>
	* 1  校验结束<p>
	* 2 C 值计算开始<p>
	* 3 C 值计算结束<p>
	* 4。 红利计算开始<p>
	* 5  红利计算结束
	*/
	public String getComputeState()
	{
		return ComputeState;
	}
	public void setComputeState(String aComputeState)
	{
		ComputeState = aComputeState;
	}
	public String getBonusStartDate()
	{
		if( BonusStartDate != null )
			return fDate.getString(BonusStartDate);
		else
			return null;
	}
	public void setBonusStartDate(Date aBonusStartDate)
	{
		BonusStartDate = aBonusStartDate;
	}
	public void setBonusStartDate(String aBonusStartDate)
	{
		if (aBonusStartDate != null && !aBonusStartDate.equals("") )
		{
			BonusStartDate = fDate.getDate( aBonusStartDate );
		}
		else
			BonusStartDate = null;
	}

	public String getBonusStartTime()
	{
		return BonusStartTime;
	}
	public void setBonusStartTime(String aBonusStartTime)
	{
		BonusStartTime = aBonusStartTime;
	}
	public String getBonusEndDate()
	{
		if( BonusEndDate != null )
			return fDate.getString(BonusEndDate);
		else
			return null;
	}
	public void setBonusEndDate(Date aBonusEndDate)
	{
		BonusEndDate = aBonusEndDate;
	}
	public void setBonusEndDate(String aBonusEndDate)
	{
		if (aBonusEndDate != null && !aBonusEndDate.equals("") )
		{
			BonusEndDate = fDate.getDate( aBonusEndDate );
		}
		else
			BonusEndDate = null;
	}

	public String getBonusEndTime()
	{
		return BonusEndTime;
	}
	public void setBonusEndTime(String aBonusEndTime)
	{
		BonusEndTime = aBonusEndTime;
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
	* 红利结算时刻的红利系数和，将每个会计年度末的红利系数和保存在该字段中。
	*/
	public double getBonusCoefSum()
	{
		return BonusCoefSum;
	}
	public void setBonusCoefSum(double aBonusCoefSum)
	{
		BonusCoefSum = aBonusCoefSum;
	}
	public void setBonusCoefSum(String aBonusCoefSum)
	{
		if (aBonusCoefSum != null && !aBonusCoefSum.equals(""))
		{
			Double tDouble = new Double(aBonusCoefSum);
			double d = tDouble.doubleValue();
			BonusCoefSum = d;
		}
	}

	/**
	* 1 －－ 对所有的可分配红利的险种进行分红<p>
	* 2 －－ 某些销售渠道的红利<p>
	* 3 －－ 某些险种的红利<p>
	* 4 －－ 某些团体保单的红利（主要针对某个团体的大单，通过协议来进行红利分配的情况。）<p>
	* <p>
	* 该字段仅仅是描述作用，没有太多的实际意义。
	*/
	public String getBonusGroupType()
	{
		return BonusGroupType;
	}
	public void setBonusGroupType(String aBonusGroupType)
	{
		BonusGroupType = aBonusGroupType;
	}
	/**
	* 在这里定义一个Where子句，描述该红利分配组需要什么样的条件限制。<p>
	* （整个Where都需要描述，程序中只要简单的拼接起来就可以了。）<p>
	* 通过该限制条件来描述该红利分配组的需要取的有效保单。
	*/
	public String getBonusCondition()
	{
		return BonusCondition;
	}
	public void setBonusCondition(String aBonusCondition)
	{
		BonusCondition = aBonusCondition;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}

	/**
	* 使用另外一个 LOBonusMainSchema 对象给 Schema 赋值
	* @param: aLOBonusMainSchema LOBonusMainSchema
	**/
	public void setSchema(LOBonusMainSchema aLOBonusMainSchema)
	{
		this.GroupID = aLOBonusMainSchema.getGroupID();
		this.FiscalYear = aLOBonusMainSchema.getFiscalYear();
		this.DistributeValue = aLOBonusMainSchema.getDistributeValue();
		this.DistributeRate = aLOBonusMainSchema.getDistributeRate();
		this.ComputeState = aLOBonusMainSchema.getComputeState();
		this.BonusStartDate = fDate.getDate( aLOBonusMainSchema.getBonusStartDate());
		this.BonusStartTime = aLOBonusMainSchema.getBonusStartTime();
		this.BonusEndDate = fDate.getDate( aLOBonusMainSchema.getBonusEndDate());
		this.BonusEndTime = aLOBonusMainSchema.getBonusEndTime();
		this.Operator = aLOBonusMainSchema.getOperator();
		this.MakeDate = fDate.getDate( aLOBonusMainSchema.getMakeDate());
		this.MakeTime = aLOBonusMainSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLOBonusMainSchema.getModifyDate());
		this.ModifyTime = aLOBonusMainSchema.getModifyTime();
		this.BonusCoefSum = aLOBonusMainSchema.getBonusCoefSum();
		this.BonusGroupType = aLOBonusMainSchema.getBonusGroupType();
		this.BonusCondition = aLOBonusMainSchema.getBonusCondition();
		this.State = aLOBonusMainSchema.getState();
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
			this.GroupID = rs.getInt("GroupID");
			this.FiscalYear = rs.getInt("FiscalYear");
			this.DistributeValue = rs.getDouble("DistributeValue");
			this.DistributeRate = rs.getDouble("DistributeRate");
			if( rs.getString("ComputeState") == null )
				this.ComputeState = null;
			else
				this.ComputeState = rs.getString("ComputeState").trim();

			this.BonusStartDate = rs.getDate("BonusStartDate");
			if( rs.getString("BonusStartTime") == null )
				this.BonusStartTime = null;
			else
				this.BonusStartTime = rs.getString("BonusStartTime").trim();

			this.BonusEndDate = rs.getDate("BonusEndDate");
			if( rs.getString("BonusEndTime") == null )
				this.BonusEndTime = null;
			else
				this.BonusEndTime = rs.getString("BonusEndTime").trim();

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

			this.BonusCoefSum = rs.getDouble("BonusCoefSum");
			if( rs.getString("BonusGroupType") == null )
				this.BonusGroupType = null;
			else
				this.BonusGroupType = rs.getString("BonusGroupType").trim();

			if( rs.getString("BonusCondition") == null )
				this.BonusCondition = null;
			else
				this.BonusCondition = rs.getString("BonusCondition").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOBonusMain表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBonusMainSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOBonusMainSchema getSchema()
	{
		LOBonusMainSchema aLOBonusMainSchema = new LOBonusMainSchema();
		aLOBonusMainSchema.setSchema(this);
		return aLOBonusMainSchema;
	}

	public LOBonusMainDB getDB()
	{
		LOBonusMainDB aDBOper = new LOBonusMainDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBonusMain描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(GroupID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FiscalYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DistributeValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DistributeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComputeState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BonusStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusStartTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BonusEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusEndTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BonusCoefSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusGroupType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusCondition)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBonusMain>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GroupID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			FiscalYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			DistributeValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).doubleValue();
			DistributeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			ComputeState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BonusStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			BonusStartTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			BonusEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			BonusEndTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			BonusCoefSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			BonusGroupType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			BonusCondition = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBonusMainSchema";
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
		if (FCode.equalsIgnoreCase("GroupID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GroupID));
		}
		if (FCode.equalsIgnoreCase("FiscalYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FiscalYear));
		}
		if (FCode.equalsIgnoreCase("DistributeValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DistributeValue));
		}
		if (FCode.equalsIgnoreCase("DistributeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DistributeRate));
		}
		if (FCode.equalsIgnoreCase("ComputeState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComputeState));
		}
		if (FCode.equalsIgnoreCase("BonusStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBonusStartDate()));
		}
		if (FCode.equalsIgnoreCase("BonusStartTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusStartTime));
		}
		if (FCode.equalsIgnoreCase("BonusEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBonusEndDate()));
		}
		if (FCode.equalsIgnoreCase("BonusEndTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusEndTime));
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
		if (FCode.equalsIgnoreCase("BonusCoefSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusCoefSum));
		}
		if (FCode.equalsIgnoreCase("BonusGroupType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusGroupType));
		}
		if (FCode.equalsIgnoreCase("BonusCondition"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusCondition));
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
				strFieldValue = String.valueOf(GroupID);
				break;
			case 1:
				strFieldValue = String.valueOf(FiscalYear);
				break;
			case 2:
				strFieldValue = String.valueOf(DistributeValue);
				break;
			case 3:
				strFieldValue = String.valueOf(DistributeRate);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ComputeState);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBonusStartDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BonusStartTime);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBonusEndDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BonusEndTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 14:
				strFieldValue = String.valueOf(BonusCoefSum);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(BonusGroupType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(BonusCondition);
				break;
			case 17:
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

		if (FCode.equalsIgnoreCase("GroupID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GroupID = i;
			}
		}
		if (FCode.equalsIgnoreCase("FiscalYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FiscalYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("DistributeValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DistributeValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("DistributeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DistributeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ComputeState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComputeState = FValue.trim();
			}
			else
				ComputeState = null;
		}
		if (FCode.equalsIgnoreCase("BonusStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BonusStartDate = fDate.getDate( FValue );
			}
			else
				BonusStartDate = null;
		}
		if (FCode.equalsIgnoreCase("BonusStartTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusStartTime = FValue.trim();
			}
			else
				BonusStartTime = null;
		}
		if (FCode.equalsIgnoreCase("BonusEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BonusEndDate = fDate.getDate( FValue );
			}
			else
				BonusEndDate = null;
		}
		if (FCode.equalsIgnoreCase("BonusEndTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusEndTime = FValue.trim();
			}
			else
				BonusEndTime = null;
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
		if (FCode.equalsIgnoreCase("BonusCoefSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BonusCoefSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("BonusGroupType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusGroupType = FValue.trim();
			}
			else
				BonusGroupType = null;
		}
		if (FCode.equalsIgnoreCase("BonusCondition"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusCondition = FValue.trim();
			}
			else
				BonusCondition = null;
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
		LOBonusMainSchema other = (LOBonusMainSchema)otherObject;
		return
			GroupID == other.getGroupID()
			&& FiscalYear == other.getFiscalYear()
			&& DistributeValue == other.getDistributeValue()
			&& DistributeRate == other.getDistributeRate()
			&& ComputeState.equals(other.getComputeState())
			&& fDate.getString(BonusStartDate).equals(other.getBonusStartDate())
			&& BonusStartTime.equals(other.getBonusStartTime())
			&& fDate.getString(BonusEndDate).equals(other.getBonusEndDate())
			&& BonusEndTime.equals(other.getBonusEndTime())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BonusCoefSum == other.getBonusCoefSum()
			&& BonusGroupType.equals(other.getBonusGroupType())
			&& BonusCondition.equals(other.getBonusCondition())
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
		if( strFieldName.equals("GroupID") ) {
			return 0;
		}
		if( strFieldName.equals("FiscalYear") ) {
			return 1;
		}
		if( strFieldName.equals("DistributeValue") ) {
			return 2;
		}
		if( strFieldName.equals("DistributeRate") ) {
			return 3;
		}
		if( strFieldName.equals("ComputeState") ) {
			return 4;
		}
		if( strFieldName.equals("BonusStartDate") ) {
			return 5;
		}
		if( strFieldName.equals("BonusStartTime") ) {
			return 6;
		}
		if( strFieldName.equals("BonusEndDate") ) {
			return 7;
		}
		if( strFieldName.equals("BonusEndTime") ) {
			return 8;
		}
		if( strFieldName.equals("Operator") ) {
			return 9;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 10;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 13;
		}
		if( strFieldName.equals("BonusCoefSum") ) {
			return 14;
		}
		if( strFieldName.equals("BonusGroupType") ) {
			return 15;
		}
		if( strFieldName.equals("BonusCondition") ) {
			return 16;
		}
		if( strFieldName.equals("State") ) {
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
				strFieldName = "GroupID";
				break;
			case 1:
				strFieldName = "FiscalYear";
				break;
			case 2:
				strFieldName = "DistributeValue";
				break;
			case 3:
				strFieldName = "DistributeRate";
				break;
			case 4:
				strFieldName = "ComputeState";
				break;
			case 5:
				strFieldName = "BonusStartDate";
				break;
			case 6:
				strFieldName = "BonusStartTime";
				break;
			case 7:
				strFieldName = "BonusEndDate";
				break;
			case 8:
				strFieldName = "BonusEndTime";
				break;
			case 9:
				strFieldName = "Operator";
				break;
			case 10:
				strFieldName = "MakeDate";
				break;
			case 11:
				strFieldName = "MakeTime";
				break;
			case 12:
				strFieldName = "ModifyDate";
				break;
			case 13:
				strFieldName = "ModifyTime";
				break;
			case 14:
				strFieldName = "BonusCoefSum";
				break;
			case 15:
				strFieldName = "BonusGroupType";
				break;
			case 16:
				strFieldName = "BonusCondition";
				break;
			case 17:
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
		if( strFieldName.equals("GroupID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FiscalYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DistributeValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DistributeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ComputeState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BonusStartTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BonusEndTime") ) {
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
		if( strFieldName.equals("BonusCoefSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BonusGroupType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusCondition") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 1:
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 3:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
