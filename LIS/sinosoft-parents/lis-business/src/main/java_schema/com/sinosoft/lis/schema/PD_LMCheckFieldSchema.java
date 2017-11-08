

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
import com.sinosoft.lis.db.PD_LMCheckFieldDB;

/*
 * <p>ClassName: PD_LMCheckFieldSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMCheckFieldSchema implements Schema, Cloneable
{
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 控制字段名称 */
	private String FieldName;
	/** 序号 */
	private String SerialNo;
	/** 险种版本 */
	private String RiskVer;
	/** 算法 */
	private String CalCode;
	/** 页面位置 */
	private String PageLocation;
	/** 事件位置 */
	private String Location;
	/** 提示信息 */
	private String Msg;
	/** 提示标记 */
	private String MsgFlag;
	/** 修改变量标记 */
	private String UpdFlag;
	/** 有效结果标记 */
	private String ValiFlag;
	/** 返回值有效标记 */
	private String ReturnValiFlag;
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

	public static final int FIELDNUM = 23;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMCheckFieldSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "FieldName";
		pk[2] = "SerialNo";

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
		PD_LMCheckFieldSchema cloned = (PD_LMCheckFieldSchema)super.clone();
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
	/**
	* 该字段为需要校验的字段的英文名称。该字段在一个界面中必须唯一。
	*/
	public String getFieldName()
	{
		return FieldName;
	}
	public void setFieldName(String aFieldName)
	{
		FieldName = aFieldName;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	/**
	* 保额算保费
	*/
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	/**
	* 如果该字段为空或null，则表示处理所有页面，否则只是处理本页面的校验。 TBINPUT#TBTYPEGC 团单合同自动复核规则 TBINPUT#TBTYPEIC 个单合同自动复核规则 TBINPUT#TBTYPEG 团单险种自动复核规则 TBINPUT#TBTYPEI 个单险种自动复核规则
	*/
	public String getPageLocation()
	{
		return PageLocation;
	}
	public void setPageLocation(String aPageLocation)
	{
		PageLocation = aPageLocation;
	}
	/**
	* B--Before Field 、A--After Field
	*/
	public String getLocation()
	{
		return Location;
	}
	public void setLocation(String aLocation)
	{
		Location = aLocation;
	}
	public String getMsg()
	{
		return Msg;
	}
	public void setMsg(String aMsg)
	{
		Msg = aMsg;
	}
	/**
	* Y--是,表示需要将提示信息显示出来 N--否，表示不显示提示信息。 1--执行标志，表示需要执行返回的相应函数。
	*/
	public String getMsgFlag()
	{
		return MsgFlag;
	}
	public void setMsgFlag(String aMsgFlag)
	{
		MsgFlag = aMsgFlag;
	}
	/**
	* Y--是，表示需要将屏幕上的相关信息进行修改。 N--否
	*/
	public String getUpdFlag()
	{
		return UpdFlag;
	}
	public void setUpdFlag(String aUpdFlag)
	{
		UpdFlag = aUpdFlag;
	}
	/**
	* 该字段描述哪些结果对于该控制是有效的，如果有多个有效值，则通过“;”将多个有效值分开。
	*/
	public String getValiFlag()
	{
		return ValiFlag;
	}
	public void setValiFlag(String aValiFlag)
	{
		ValiFlag = aValiFlag;
	}
	/**
	* Y--是，表示执行成功，并且需要进一步处理 N--否，表示不成功，不进行其他任何处理。
	*/
	public String getReturnValiFlag()
	{
		return ReturnValiFlag;
	}
	public void setReturnValiFlag(String aReturnValiFlag)
	{
		ReturnValiFlag = aReturnValiFlag;
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
	* 使用另外一个 PD_LMCheckFieldSchema 对象给 Schema 赋值
	* @param: aPD_LMCheckFieldSchema PD_LMCheckFieldSchema
	**/
	public void setSchema(PD_LMCheckFieldSchema aPD_LMCheckFieldSchema)
	{
		this.RiskCode = aPD_LMCheckFieldSchema.getRiskCode();
		this.FieldName = aPD_LMCheckFieldSchema.getFieldName();
		this.SerialNo = aPD_LMCheckFieldSchema.getSerialNo();
		this.RiskVer = aPD_LMCheckFieldSchema.getRiskVer();
		this.CalCode = aPD_LMCheckFieldSchema.getCalCode();
		this.PageLocation = aPD_LMCheckFieldSchema.getPageLocation();
		this.Location = aPD_LMCheckFieldSchema.getLocation();
		this.Msg = aPD_LMCheckFieldSchema.getMsg();
		this.MsgFlag = aPD_LMCheckFieldSchema.getMsgFlag();
		this.UpdFlag = aPD_LMCheckFieldSchema.getUpdFlag();
		this.ValiFlag = aPD_LMCheckFieldSchema.getValiFlag();
		this.ReturnValiFlag = aPD_LMCheckFieldSchema.getReturnValiFlag();
		this.Operator = aPD_LMCheckFieldSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMCheckFieldSchema.getMakeDate());
		this.MakeTime = aPD_LMCheckFieldSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMCheckFieldSchema.getModifyDate());
		this.ModifyTime = aPD_LMCheckFieldSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMCheckFieldSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMCheckFieldSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMCheckFieldSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMCheckFieldSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMCheckFieldSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMCheckFieldSchema.getStandbyflag6();
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

			if( rs.getString("FieldName") == null )
				this.FieldName = null;
			else
				this.FieldName = rs.getString("FieldName").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("PageLocation") == null )
				this.PageLocation = null;
			else
				this.PageLocation = rs.getString("PageLocation").trim();

			if( rs.getString("Location") == null )
				this.Location = null;
			else
				this.Location = rs.getString("Location").trim();

			if( rs.getString("Msg") == null )
				this.Msg = null;
			else
				this.Msg = rs.getString("Msg").trim();

			if( rs.getString("MsgFlag") == null )
				this.MsgFlag = null;
			else
				this.MsgFlag = rs.getString("MsgFlag").trim();

			if( rs.getString("UpdFlag") == null )
				this.UpdFlag = null;
			else
				this.UpdFlag = rs.getString("UpdFlag").trim();

			if( rs.getString("ValiFlag") == null )
				this.ValiFlag = null;
			else
				this.ValiFlag = rs.getString("ValiFlag").trim();

			if( rs.getString("ReturnValiFlag") == null )
				this.ReturnValiFlag = null;
			else
				this.ReturnValiFlag = rs.getString("ReturnValiFlag").trim();

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
			System.out.println("数据库中的PD_LMCheckField表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMCheckFieldSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMCheckFieldSchema getSchema()
	{
		PD_LMCheckFieldSchema aPD_LMCheckFieldSchema = new PD_LMCheckFieldSchema();
		aPD_LMCheckFieldSchema.setSchema(this);
		return aPD_LMCheckFieldSchema;
	}

	public PD_LMCheckFieldDB getDB()
	{
		PD_LMCheckFieldDB aDBOper = new PD_LMCheckFieldDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMCheckField描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FieldName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PageLocation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Location)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Msg)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MsgFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpdFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValiFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReturnValiFlag)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMCheckField>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FieldName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PageLocation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Location = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Msg = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MsgFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			UpdFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ValiFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ReturnValiFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMCheckFieldSchema";
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
		if (FCode.equalsIgnoreCase("FieldName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldName));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("PageLocation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PageLocation));
		}
		if (FCode.equalsIgnoreCase("Location"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Location));
		}
		if (FCode.equalsIgnoreCase("Msg"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Msg));
		}
		if (FCode.equalsIgnoreCase("MsgFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MsgFlag));
		}
		if (FCode.equalsIgnoreCase("UpdFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpdFlag));
		}
		if (FCode.equalsIgnoreCase("ValiFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValiFlag));
		}
		if (FCode.equalsIgnoreCase("ReturnValiFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReturnValiFlag));
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
				strFieldValue = StrTool.GBKToUnicode(FieldName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PageLocation);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Location);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Msg);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MsgFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(UpdFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ValiFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ReturnValiFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 19:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 20:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 21:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 22:
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
		if (FCode.equalsIgnoreCase("FieldName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FieldName = FValue.trim();
			}
			else
				FieldName = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
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
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("PageLocation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PageLocation = FValue.trim();
			}
			else
				PageLocation = null;
		}
		if (FCode.equalsIgnoreCase("Location"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Location = FValue.trim();
			}
			else
				Location = null;
		}
		if (FCode.equalsIgnoreCase("Msg"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Msg = FValue.trim();
			}
			else
				Msg = null;
		}
		if (FCode.equalsIgnoreCase("MsgFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MsgFlag = FValue.trim();
			}
			else
				MsgFlag = null;
		}
		if (FCode.equalsIgnoreCase("UpdFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpdFlag = FValue.trim();
			}
			else
				UpdFlag = null;
		}
		if (FCode.equalsIgnoreCase("ValiFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValiFlag = FValue.trim();
			}
			else
				ValiFlag = null;
		}
		if (FCode.equalsIgnoreCase("ReturnValiFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReturnValiFlag = FValue.trim();
			}
			else
				ReturnValiFlag = null;
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
		PD_LMCheckFieldSchema other = (PD_LMCheckFieldSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& FieldName.equals(other.getFieldName())
			&& SerialNo.equals(other.getSerialNo())
			&& RiskVer.equals(other.getRiskVer())
			&& CalCode.equals(other.getCalCode())
			&& PageLocation.equals(other.getPageLocation())
			&& Location.equals(other.getLocation())
			&& Msg.equals(other.getMsg())
			&& MsgFlag.equals(other.getMsgFlag())
			&& UpdFlag.equals(other.getUpdFlag())
			&& ValiFlag.equals(other.getValiFlag())
			&& ReturnValiFlag.equals(other.getReturnValiFlag())
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
		if( strFieldName.equals("FieldName") ) {
			return 1;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 2;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 3;
		}
		if( strFieldName.equals("CalCode") ) {
			return 4;
		}
		if( strFieldName.equals("PageLocation") ) {
			return 5;
		}
		if( strFieldName.equals("Location") ) {
			return 6;
		}
		if( strFieldName.equals("Msg") ) {
			return 7;
		}
		if( strFieldName.equals("MsgFlag") ) {
			return 8;
		}
		if( strFieldName.equals("UpdFlag") ) {
			return 9;
		}
		if( strFieldName.equals("ValiFlag") ) {
			return 10;
		}
		if( strFieldName.equals("ReturnValiFlag") ) {
			return 11;
		}
		if( strFieldName.equals("Operator") ) {
			return 12;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 13;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 16;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 17;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 18;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 19;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 20;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 21;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 22;
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
				strFieldName = "FieldName";
				break;
			case 2:
				strFieldName = "SerialNo";
				break;
			case 3:
				strFieldName = "RiskVer";
				break;
			case 4:
				strFieldName = "CalCode";
				break;
			case 5:
				strFieldName = "PageLocation";
				break;
			case 6:
				strFieldName = "Location";
				break;
			case 7:
				strFieldName = "Msg";
				break;
			case 8:
				strFieldName = "MsgFlag";
				break;
			case 9:
				strFieldName = "UpdFlag";
				break;
			case 10:
				strFieldName = "ValiFlag";
				break;
			case 11:
				strFieldName = "ReturnValiFlag";
				break;
			case 12:
				strFieldName = "Operator";
				break;
			case 13:
				strFieldName = "MakeDate";
				break;
			case 14:
				strFieldName = "MakeTime";
				break;
			case 15:
				strFieldName = "ModifyDate";
				break;
			case 16:
				strFieldName = "ModifyTime";
				break;
			case 17:
				strFieldName = "Standbyflag1";
				break;
			case 18:
				strFieldName = "Standbyflag2";
				break;
			case 19:
				strFieldName = "Standbyflag3";
				break;
			case 20:
				strFieldName = "Standbyflag4";
				break;
			case 21:
				strFieldName = "Standbyflag5";
				break;
			case 22:
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
		if( strFieldName.equals("FieldName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PageLocation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Location") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Msg") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MsgFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpdFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValiFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReturnValiFlag") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_INT;
				break;
			case 20:
				nFieldType = Schema.TYPE_INT;
				break;
			case 21:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
