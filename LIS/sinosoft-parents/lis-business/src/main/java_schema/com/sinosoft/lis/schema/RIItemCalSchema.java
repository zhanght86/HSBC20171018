

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
import com.sinosoft.lis.db.RIItemCalDB;

/*
 * <p>ClassName: RIItemCalSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIItemCalSchema implements Schema, Cloneable
{
	// @Field
	/** 算法定义编码 */
	private String ArithmeticDefID;
	/** 算法编码 */
	private String ArithmeticID;
	/** 算法名称 */
	private String ArithmeticName;
	/** 算法类型 */
	private String ArithmeticType;
	/** 计算项编码 */
	private String CalItemID;
	/** 计算项名称 */
	private String CalItemName;
	/** 计算项次序 */
	private int CalItemOrder;
	/** 计算项类别 */
	private String CalItemType;
	/** 计算项计算类型 */
	private String ItemCalType;
	/** 固定数字值 */
	private double DoubleValue;
	/** 计算处理类 */
	private String CalClass;
	/** 计算sql算法 */
	private String CalSQL;
	/** 计算结果存储字段 */
	private String TarGetClumn;
	/** 测试算法 */
	private String TestCal;
	/** 描述 */
	private String ReMark;
	/** 备用字符串属性1 */
	private String StandbyString1;
	/** 备用字符串属性2 */
	private String StandbyString2;
	/** 备用字符串属性3 */
	private String StandbyString3;
	/** 计算sql算法2 */
	private String CalSQL2;
	/** 计算sql算法3 */
	private String CalSQL3;

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIItemCalSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ArithmeticID";
		pk[1] = "ArithmeticType";
		pk[2] = "CalItemOrder";

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
		RIItemCalSchema cloned = (RIItemCalSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getArithmeticDefID()
	{
		return ArithmeticDefID;
	}
	public void setArithmeticDefID(String aArithmeticDefID)
	{
		ArithmeticDefID = aArithmeticDefID;
	}
	public String getArithmeticID()
	{
		return ArithmeticID;
	}
	public void setArithmeticID(String aArithmeticID)
	{
		ArithmeticID = aArithmeticID;
	}
	public String getArithmeticName()
	{
		return ArithmeticName;
	}
	public void setArithmeticName(String aArithmeticName)
	{
		ArithmeticName = aArithmeticName;
	}
	/**
	* L：分出责任算法:<p>
	* 01-数据提取算法<p>
	* 02-数据校验算法<p>
	* 03-风险保额算法<p>
	* -----------------------------------------<p>
	* P：方案算法:<p>
	* 11-方案分配算法<p>
	* 12-分保参数算法<p>
	* 13-计算项算法<p>
	* <p>
	* 14-计算处理类算法<p>
	* <p>
	* ------------------------------------------<p>
	* F：临分核保规则(未启用)<p>
	* ------------------------------------------<p>
	* C：再保核赔规则(未启用)
	*/
	public String getArithmeticType()
	{
		return ArithmeticType;
	}
	public void setArithmeticType(String aArithmeticType)
	{
		ArithmeticType = aArithmeticType;
	}
	/**
	* -----------------------方案分配--------------------<p>
	* PreDec  方案分配<p>
	* -----------------------参数准备---------------------<p>
	* CessAmnt  分保保额<p>
	* CessRate  分保比例<p>
	* FeeRate      分保费率<p>
	* CommRate  分保佣金率<p>
	* <p>
	* 理赔金额<p>
	* ClmRate    赔付率  <p>
	* ---------------------分保区域处理类---------------------<p>
	* policy 新单<p>
	* continuous 续期<p>
	* edor   保全<p>
	* claim  理赔<p>
	* ----------------------分保结果计算----------------------<p>
	* CessFee     分保保费<p>
	* CessComm 分保佣金<p>
	* ReternPay  理赔摊回
	*/
	public String getCalItemID()
	{
		return CalItemID;
	}
	public void setCalItemID(String aCalItemID)
	{
		CalItemID = aCalItemID;
	}
	public String getCalItemName()
	{
		return CalItemName;
	}
	public void setCalItemName(String aCalItemName)
	{
		CalItemName = aCalItemName;
	}
	public int getCalItemOrder()
	{
		return CalItemOrder;
	}
	public void setCalItemOrder(int aCalItemOrder)
	{
		CalItemOrder = aCalItemOrder;
	}
	public void setCalItemOrder(String aCalItemOrder)
	{
		if (aCalItemOrder != null && !aCalItemOrder.equals(""))
		{
			Integer tInteger = new Integer(aCalItemOrder);
			int i = tInteger.intValue();
			CalItemOrder = i;
		}
	}

	/**
	* -----------------------------数据提取----------------<p>
	* 有效保单<p>
	* 事件驱动<p>
	* -----------------------------方案分配-------------------<p>
	* 生效日<p>
	* <p>
	* 险种<p>
	* 责任<p>
	* 币种<p>
	* ------------------------------其他---------------------------<p>
	* 01-新单计算<p>
	* 02-续期计算 <p>
	* 03-保全调整 <p>
	* 04-理赔摊回 <p>
	* 05-其他
	*/
	public String getCalItemType()
	{
		return CalItemType;
	}
	public void setCalItemType(String aCalItemType)
	{
		CalItemType = aCalItemType;
	}
	/**
	* 1-固定数值,<p>
	* 2-Sql计算,<p>
	* 3-类计算
	*/
	public String getItemCalType()
	{
		return ItemCalType;
	}
	public void setItemCalType(String aItemCalType)
	{
		ItemCalType = aItemCalType;
	}
	public double getDoubleValue()
	{
		return DoubleValue;
	}
	public void setDoubleValue(double aDoubleValue)
	{
		DoubleValue = aDoubleValue;
	}
	public void setDoubleValue(String aDoubleValue)
	{
		if (aDoubleValue != null && !aDoubleValue.equals(""))
		{
			Double tDouble = new Double(aDoubleValue);
			double d = tDouble.doubleValue();
			DoubleValue = d;
		}
	}

	public String getCalClass()
	{
		return CalClass;
	}
	public void setCalClass(String aCalClass)
	{
		CalClass = aCalClass;
	}
	public String getCalSQL()
	{
		return CalSQL;
	}
	public void setCalSQL(String aCalSQL)
	{
		CalSQL = aCalSQL;
	}
	public String getTarGetClumn()
	{
		return TarGetClumn;
	}
	public void setTarGetClumn(String aTarGetClumn)
	{
		TarGetClumn = aTarGetClumn;
	}
	public String getTestCal()
	{
		return TestCal;
	}
	public void setTestCal(String aTestCal)
	{
		TestCal = aTestCal;
	}
	public String getReMark()
	{
		return ReMark;
	}
	public void setReMark(String aReMark)
	{
		ReMark = aReMark;
	}
	/**
	* 对于03-参数算法，用于保存feerate,commrate的适用区域ID号<p>
	* <p>
	* all:为所有区域都使用同一个参数<p>
	* <p>
	* 01，02，03...表示区域ID号
	*/
	public String getStandbyString1()
	{
		return StandbyString1;
	}
	public void setStandbyString1(String aStandbyString1)
	{
		StandbyString1 = aStandbyString1;
	}
	public String getStandbyString2()
	{
		return StandbyString2;
	}
	public void setStandbyString2(String aStandbyString2)
	{
		StandbyString2 = aStandbyString2;
	}
	/**
	* 算法调试sql
	*/
	public String getStandbyString3()
	{
		return StandbyString3;
	}
	public void setStandbyString3(String aStandbyString3)
	{
		StandbyString3 = aStandbyString3;
	}
	public String getCalSQL2()
	{
		return CalSQL2;
	}
	public void setCalSQL2(String aCalSQL2)
	{
		CalSQL2 = aCalSQL2;
	}
	public String getCalSQL3()
	{
		return CalSQL3;
	}
	public void setCalSQL3(String aCalSQL3)
	{
		CalSQL3 = aCalSQL3;
	}

	/**
	* 使用另外一个 RIItemCalSchema 对象给 Schema 赋值
	* @param: aRIItemCalSchema RIItemCalSchema
	**/
	public void setSchema(RIItemCalSchema aRIItemCalSchema)
	{
		this.ArithmeticDefID = aRIItemCalSchema.getArithmeticDefID();
		this.ArithmeticID = aRIItemCalSchema.getArithmeticID();
		this.ArithmeticName = aRIItemCalSchema.getArithmeticName();
		this.ArithmeticType = aRIItemCalSchema.getArithmeticType();
		this.CalItemID = aRIItemCalSchema.getCalItemID();
		this.CalItemName = aRIItemCalSchema.getCalItemName();
		this.CalItemOrder = aRIItemCalSchema.getCalItemOrder();
		this.CalItemType = aRIItemCalSchema.getCalItemType();
		this.ItemCalType = aRIItemCalSchema.getItemCalType();
		this.DoubleValue = aRIItemCalSchema.getDoubleValue();
		this.CalClass = aRIItemCalSchema.getCalClass();
		this.CalSQL = aRIItemCalSchema.getCalSQL();
		this.TarGetClumn = aRIItemCalSchema.getTarGetClumn();
		this.TestCal = aRIItemCalSchema.getTestCal();
		this.ReMark = aRIItemCalSchema.getReMark();
		this.StandbyString1 = aRIItemCalSchema.getStandbyString1();
		this.StandbyString2 = aRIItemCalSchema.getStandbyString2();
		this.StandbyString3 = aRIItemCalSchema.getStandbyString3();
		this.CalSQL2 = aRIItemCalSchema.getCalSQL2();
		this.CalSQL3 = aRIItemCalSchema.getCalSQL3();
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
			if( rs.getString("ArithmeticDefID") == null )
				this.ArithmeticDefID = null;
			else
				this.ArithmeticDefID = rs.getString("ArithmeticDefID").trim();

			if( rs.getString("ArithmeticID") == null )
				this.ArithmeticID = null;
			else
				this.ArithmeticID = rs.getString("ArithmeticID").trim();

			if( rs.getString("ArithmeticName") == null )
				this.ArithmeticName = null;
			else
				this.ArithmeticName = rs.getString("ArithmeticName").trim();

			if( rs.getString("ArithmeticType") == null )
				this.ArithmeticType = null;
			else
				this.ArithmeticType = rs.getString("ArithmeticType").trim();

			if( rs.getString("CalItemID") == null )
				this.CalItemID = null;
			else
				this.CalItemID = rs.getString("CalItemID").trim();

			if( rs.getString("CalItemName") == null )
				this.CalItemName = null;
			else
				this.CalItemName = rs.getString("CalItemName").trim();

			this.CalItemOrder = rs.getInt("CalItemOrder");
			if( rs.getString("CalItemType") == null )
				this.CalItemType = null;
			else
				this.CalItemType = rs.getString("CalItemType").trim();

			if( rs.getString("ItemCalType") == null )
				this.ItemCalType = null;
			else
				this.ItemCalType = rs.getString("ItemCalType").trim();

			this.DoubleValue = rs.getDouble("DoubleValue");
			if( rs.getString("CalClass") == null )
				this.CalClass = null;
			else
				this.CalClass = rs.getString("CalClass").trim();

			if( rs.getString("CalSQL") == null )
				this.CalSQL = null;
			else
				this.CalSQL = rs.getString("CalSQL").trim();

			if( rs.getString("TarGetClumn") == null )
				this.TarGetClumn = null;
			else
				this.TarGetClumn = rs.getString("TarGetClumn").trim();

			if( rs.getString("TestCal") == null )
				this.TestCal = null;
			else
				this.TestCal = rs.getString("TestCal").trim();

			if( rs.getString("ReMark") == null )
				this.ReMark = null;
			else
				this.ReMark = rs.getString("ReMark").trim();

			if( rs.getString("StandbyString1") == null )
				this.StandbyString1 = null;
			else
				this.StandbyString1 = rs.getString("StandbyString1").trim();

			if( rs.getString("StandbyString2") == null )
				this.StandbyString2 = null;
			else
				this.StandbyString2 = rs.getString("StandbyString2").trim();

			if( rs.getString("StandbyString3") == null )
				this.StandbyString3 = null;
			else
				this.StandbyString3 = rs.getString("StandbyString3").trim();

			if( rs.getString("CalSQL2") == null )
				this.CalSQL2 = null;
			else
				this.CalSQL2 = rs.getString("CalSQL2").trim();

			if( rs.getString("CalSQL3") == null )
				this.CalSQL3 = null;
			else
				this.CalSQL3 = rs.getString("CalSQL3").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIItemCal表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIItemCalSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIItemCalSchema getSchema()
	{
		RIItemCalSchema aRIItemCalSchema = new RIItemCalSchema();
		aRIItemCalSchema.setSchema(this);
		return aRIItemCalSchema;
	}

	public RIItemCalDB getDB()
	{
		RIItemCalDB aDBOper = new RIItemCalDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIItemCal描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ArithmeticDefID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ArithmeticID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ArithmeticName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ArithmeticType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalItemID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalItemName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CalItemOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalItemType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ItemCalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DoubleValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSQL)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TarGetClumn)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TestCal)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReMark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSQL2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSQL3));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIItemCal>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ArithmeticDefID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ArithmeticID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ArithmeticName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ArithmeticType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CalItemID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CalItemName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CalItemOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			CalItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ItemCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DoubleValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			CalClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CalSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			TarGetClumn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			TestCal = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			StandbyString1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			StandbyString2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			StandbyString3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			CalSQL2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			CalSQL3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIItemCalSchema";
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
		if (FCode.equalsIgnoreCase("ArithmeticDefID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ArithmeticDefID));
		}
		if (FCode.equalsIgnoreCase("ArithmeticID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ArithmeticID));
		}
		if (FCode.equalsIgnoreCase("ArithmeticName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ArithmeticName));
		}
		if (FCode.equalsIgnoreCase("ArithmeticType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ArithmeticType));
		}
		if (FCode.equalsIgnoreCase("CalItemID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalItemID));
		}
		if (FCode.equalsIgnoreCase("CalItemName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalItemName));
		}
		if (FCode.equalsIgnoreCase("CalItemOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalItemOrder));
		}
		if (FCode.equalsIgnoreCase("CalItemType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalItemType));
		}
		if (FCode.equalsIgnoreCase("ItemCalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemCalType));
		}
		if (FCode.equalsIgnoreCase("DoubleValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoubleValue));
		}
		if (FCode.equalsIgnoreCase("CalClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalClass));
		}
		if (FCode.equalsIgnoreCase("CalSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSQL));
		}
		if (FCode.equalsIgnoreCase("TarGetClumn"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TarGetClumn));
		}
		if (FCode.equalsIgnoreCase("TestCal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TestCal));
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReMark));
		}
		if (FCode.equalsIgnoreCase("StandbyString1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString1));
		}
		if (FCode.equalsIgnoreCase("StandbyString2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString2));
		}
		if (FCode.equalsIgnoreCase("StandbyString3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString3));
		}
		if (FCode.equalsIgnoreCase("CalSQL2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSQL2));
		}
		if (FCode.equalsIgnoreCase("CalSQL3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSQL3));
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
				strFieldValue = StrTool.GBKToUnicode(ArithmeticDefID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ArithmeticID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ArithmeticName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ArithmeticType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CalItemID);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CalItemName);
				break;
			case 6:
				strFieldValue = String.valueOf(CalItemOrder);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CalItemType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ItemCalType);
				break;
			case 9:
				strFieldValue = String.valueOf(DoubleValue);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CalClass);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CalSQL);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(TarGetClumn);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(TestCal);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ReMark);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(StandbyString1);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(StandbyString2);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(StandbyString3);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(CalSQL2);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(CalSQL3);
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

		if (FCode.equalsIgnoreCase("ArithmeticDefID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ArithmeticDefID = FValue.trim();
			}
			else
				ArithmeticDefID = null;
		}
		if (FCode.equalsIgnoreCase("ArithmeticID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ArithmeticID = FValue.trim();
			}
			else
				ArithmeticID = null;
		}
		if (FCode.equalsIgnoreCase("ArithmeticName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ArithmeticName = FValue.trim();
			}
			else
				ArithmeticName = null;
		}
		if (FCode.equalsIgnoreCase("ArithmeticType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ArithmeticType = FValue.trim();
			}
			else
				ArithmeticType = null;
		}
		if (FCode.equalsIgnoreCase("CalItemID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalItemID = FValue.trim();
			}
			else
				CalItemID = null;
		}
		if (FCode.equalsIgnoreCase("CalItemName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalItemName = FValue.trim();
			}
			else
				CalItemName = null;
		}
		if (FCode.equalsIgnoreCase("CalItemOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CalItemOrder = i;
			}
		}
		if (FCode.equalsIgnoreCase("CalItemType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalItemType = FValue.trim();
			}
			else
				CalItemType = null;
		}
		if (FCode.equalsIgnoreCase("ItemCalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemCalType = FValue.trim();
			}
			else
				ItemCalType = null;
		}
		if (FCode.equalsIgnoreCase("DoubleValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DoubleValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("CalClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalClass = FValue.trim();
			}
			else
				CalClass = null;
		}
		if (FCode.equalsIgnoreCase("CalSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSQL = FValue.trim();
			}
			else
				CalSQL = null;
		}
		if (FCode.equalsIgnoreCase("TarGetClumn"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TarGetClumn = FValue.trim();
			}
			else
				TarGetClumn = null;
		}
		if (FCode.equalsIgnoreCase("TestCal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TestCal = FValue.trim();
			}
			else
				TestCal = null;
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReMark = FValue.trim();
			}
			else
				ReMark = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString1 = FValue.trim();
			}
			else
				StandbyString1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString2 = FValue.trim();
			}
			else
				StandbyString2 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString3 = FValue.trim();
			}
			else
				StandbyString3 = null;
		}
		if (FCode.equalsIgnoreCase("CalSQL2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSQL2 = FValue.trim();
			}
			else
				CalSQL2 = null;
		}
		if (FCode.equalsIgnoreCase("CalSQL3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSQL3 = FValue.trim();
			}
			else
				CalSQL3 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIItemCalSchema other = (RIItemCalSchema)otherObject;
		return
			ArithmeticDefID.equals(other.getArithmeticDefID())
			&& ArithmeticID.equals(other.getArithmeticID())
			&& ArithmeticName.equals(other.getArithmeticName())
			&& ArithmeticType.equals(other.getArithmeticType())
			&& CalItemID.equals(other.getCalItemID())
			&& CalItemName.equals(other.getCalItemName())
			&& CalItemOrder == other.getCalItemOrder()
			&& CalItemType.equals(other.getCalItemType())
			&& ItemCalType.equals(other.getItemCalType())
			&& DoubleValue == other.getDoubleValue()
			&& CalClass.equals(other.getCalClass())
			&& CalSQL.equals(other.getCalSQL())
			&& TarGetClumn.equals(other.getTarGetClumn())
			&& TestCal.equals(other.getTestCal())
			&& ReMark.equals(other.getReMark())
			&& StandbyString1.equals(other.getStandbyString1())
			&& StandbyString2.equals(other.getStandbyString2())
			&& StandbyString3.equals(other.getStandbyString3())
			&& CalSQL2.equals(other.getCalSQL2())
			&& CalSQL3.equals(other.getCalSQL3());
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
		if( strFieldName.equals("ArithmeticDefID") ) {
			return 0;
		}
		if( strFieldName.equals("ArithmeticID") ) {
			return 1;
		}
		if( strFieldName.equals("ArithmeticName") ) {
			return 2;
		}
		if( strFieldName.equals("ArithmeticType") ) {
			return 3;
		}
		if( strFieldName.equals("CalItemID") ) {
			return 4;
		}
		if( strFieldName.equals("CalItemName") ) {
			return 5;
		}
		if( strFieldName.equals("CalItemOrder") ) {
			return 6;
		}
		if( strFieldName.equals("CalItemType") ) {
			return 7;
		}
		if( strFieldName.equals("ItemCalType") ) {
			return 8;
		}
		if( strFieldName.equals("DoubleValue") ) {
			return 9;
		}
		if( strFieldName.equals("CalClass") ) {
			return 10;
		}
		if( strFieldName.equals("CalSQL") ) {
			return 11;
		}
		if( strFieldName.equals("TarGetClumn") ) {
			return 12;
		}
		if( strFieldName.equals("TestCal") ) {
			return 13;
		}
		if( strFieldName.equals("ReMark") ) {
			return 14;
		}
		if( strFieldName.equals("StandbyString1") ) {
			return 15;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return 16;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return 17;
		}
		if( strFieldName.equals("CalSQL2") ) {
			return 18;
		}
		if( strFieldName.equals("CalSQL3") ) {
			return 19;
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
				strFieldName = "ArithmeticDefID";
				break;
			case 1:
				strFieldName = "ArithmeticID";
				break;
			case 2:
				strFieldName = "ArithmeticName";
				break;
			case 3:
				strFieldName = "ArithmeticType";
				break;
			case 4:
				strFieldName = "CalItemID";
				break;
			case 5:
				strFieldName = "CalItemName";
				break;
			case 6:
				strFieldName = "CalItemOrder";
				break;
			case 7:
				strFieldName = "CalItemType";
				break;
			case 8:
				strFieldName = "ItemCalType";
				break;
			case 9:
				strFieldName = "DoubleValue";
				break;
			case 10:
				strFieldName = "CalClass";
				break;
			case 11:
				strFieldName = "CalSQL";
				break;
			case 12:
				strFieldName = "TarGetClumn";
				break;
			case 13:
				strFieldName = "TestCal";
				break;
			case 14:
				strFieldName = "ReMark";
				break;
			case 15:
				strFieldName = "StandbyString1";
				break;
			case 16:
				strFieldName = "StandbyString2";
				break;
			case 17:
				strFieldName = "StandbyString3";
				break;
			case 18:
				strFieldName = "CalSQL2";
				break;
			case 19:
				strFieldName = "CalSQL3";
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
		if( strFieldName.equals("ArithmeticDefID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ArithmeticID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ArithmeticName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ArithmeticType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalItemID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalItemName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalItemOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CalItemType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemCalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoubleValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSQL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TarGetClumn") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TestCal") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReMark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSQL2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSQL3") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
