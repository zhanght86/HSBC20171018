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
import com.sinosoft.lis.db.LJCertifyGetDB;

/*
 * <p>ClassName: LJCertifyGetSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LJCertifyGetSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJCertifyGetSchema.class);

	// @Field
	/** 实付号码 */
	private String ActuGetNo;
	/** 付费方式 */
	private String GetMode;
	/** 管理机构 */
	private String PolicyCom;
	/** 付费机构 */
	private String ManageCom;
	/** 总给付金额 */
	private double SumGetMoney;
	/** 业务结束日期 */
	private Date ConfDate;
	/** 工号 */
	private String AgentCode;
	/** 应付日期 */
	private Date ShouldDate;
	/** 财务到帐日期 */
	private Date EnterAccDate;
	/** 领取人 */
	private String Drawer;
	/** 领取人身份证号 */
	private String DrawerID;
	/** 实际领取人 */
	private String ActualDrawer;
	/** 实际领取人身份证号 */
	private String ActualDrawerID;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 最后一次操作员 */
	private String LastOperator;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJCertifyGetSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ActuGetNo";

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
		LJCertifyGetSchema cloned = (LJCertifyGetSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getActuGetNo()
	{
		return ActuGetNo;
	}
	public void setActuGetNo(String aActuGetNo)
	{
		ActuGetNo = aActuGetNo;
	}
	public String getGetMode()
	{
		return GetMode;
	}
	public void setGetMode(String aGetMode)
	{
		GetMode = aGetMode;
	}
	public String getPolicyCom()
	{
		return PolicyCom;
	}
	public void setPolicyCom(String aPolicyCom)
	{
		PolicyCom = aPolicyCom;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public double getSumGetMoney()
	{
		return SumGetMoney;
	}
	public void setSumGetMoney(double aSumGetMoney)
	{
		SumGetMoney = aSumGetMoney;
	}
	public void setSumGetMoney(String aSumGetMoney)
	{
		if (aSumGetMoney != null && !aSumGetMoney.equals(""))
		{
			Double tDouble = new Double(aSumGetMoney);
			double d = tDouble.doubleValue();
			SumGetMoney = d;
		}
	}

	public String getConfDate()
	{
		if( ConfDate != null )
			return fDate.getString(ConfDate);
		else
			return null;
	}
	public void setConfDate(Date aConfDate)
	{
		ConfDate = aConfDate;
	}
	public void setConfDate(String aConfDate)
	{
		if (aConfDate != null && !aConfDate.equals("") )
		{
			ConfDate = fDate.getDate( aConfDate );
		}
		else
			ConfDate = null;
	}

	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getShouldDate()
	{
		if( ShouldDate != null )
			return fDate.getString(ShouldDate);
		else
			return null;
	}
	public void setShouldDate(Date aShouldDate)
	{
		ShouldDate = aShouldDate;
	}
	public void setShouldDate(String aShouldDate)
	{
		if (aShouldDate != null && !aShouldDate.equals("") )
		{
			ShouldDate = fDate.getDate( aShouldDate );
		}
		else
			ShouldDate = null;
	}

	public String getEnterAccDate()
	{
		if( EnterAccDate != null )
			return fDate.getString(EnterAccDate);
		else
			return null;
	}
	public void setEnterAccDate(Date aEnterAccDate)
	{
		EnterAccDate = aEnterAccDate;
	}
	public void setEnterAccDate(String aEnterAccDate)
	{
		if (aEnterAccDate != null && !aEnterAccDate.equals("") )
		{
			EnterAccDate = fDate.getDate( aEnterAccDate );
		}
		else
			EnterAccDate = null;
	}

	public String getDrawer()
	{
		return Drawer;
	}
	public void setDrawer(String aDrawer)
	{
		Drawer = aDrawer;
	}
	public String getDrawerID()
	{
		return DrawerID;
	}
	public void setDrawerID(String aDrawerID)
	{
		DrawerID = aDrawerID;
	}
	public String getActualDrawer()
	{
		return ActualDrawer;
	}
	public void setActualDrawer(String aActualDrawer)
	{
		ActualDrawer = aActualDrawer;
	}
	public String getActualDrawerID()
	{
		return ActualDrawerID;
	}
	public void setActualDrawerID(String aActualDrawerID)
	{
		ActualDrawerID = aActualDrawerID;
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
	public String getLastOperator()
	{
		return LastOperator;
	}
	public void setLastOperator(String aLastOperator)
	{
		LastOperator = aLastOperator;
	}

	/**
	* 使用另外一个 LJCertifyGetSchema 对象给 Schema 赋值
	* @param: aLJCertifyGetSchema LJCertifyGetSchema
	**/
	public void setSchema(LJCertifyGetSchema aLJCertifyGetSchema)
	{
		this.ActuGetNo = aLJCertifyGetSchema.getActuGetNo();
		this.GetMode = aLJCertifyGetSchema.getGetMode();
		this.PolicyCom = aLJCertifyGetSchema.getPolicyCom();
		this.ManageCom = aLJCertifyGetSchema.getManageCom();
		this.SumGetMoney = aLJCertifyGetSchema.getSumGetMoney();
		this.ConfDate = fDate.getDate( aLJCertifyGetSchema.getConfDate());
		this.AgentCode = aLJCertifyGetSchema.getAgentCode();
		this.ShouldDate = fDate.getDate( aLJCertifyGetSchema.getShouldDate());
		this.EnterAccDate = fDate.getDate( aLJCertifyGetSchema.getEnterAccDate());
		this.Drawer = aLJCertifyGetSchema.getDrawer();
		this.DrawerID = aLJCertifyGetSchema.getDrawerID();
		this.ActualDrawer = aLJCertifyGetSchema.getActualDrawer();
		this.ActualDrawerID = aLJCertifyGetSchema.getActualDrawerID();
		this.Operator = aLJCertifyGetSchema.getOperator();
		this.MakeDate = fDate.getDate( aLJCertifyGetSchema.getMakeDate());
		this.MakeTime = aLJCertifyGetSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLJCertifyGetSchema.getModifyDate());
		this.ModifyTime = aLJCertifyGetSchema.getModifyTime();
		this.LastOperator = aLJCertifyGetSchema.getLastOperator();
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
			if( rs.getString("ActuGetNo") == null )
				this.ActuGetNo = null;
			else
				this.ActuGetNo = rs.getString("ActuGetNo").trim();

			if( rs.getString("GetMode") == null )
				this.GetMode = null;
			else
				this.GetMode = rs.getString("GetMode").trim();

			if( rs.getString("PolicyCom") == null )
				this.PolicyCom = null;
			else
				this.PolicyCom = rs.getString("PolicyCom").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.SumGetMoney = rs.getDouble("SumGetMoney");
			this.ConfDate = rs.getDate("ConfDate");
			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			this.ShouldDate = rs.getDate("ShouldDate");
			this.EnterAccDate = rs.getDate("EnterAccDate");
			if( rs.getString("Drawer") == null )
				this.Drawer = null;
			else
				this.Drawer = rs.getString("Drawer").trim();

			if( rs.getString("DrawerID") == null )
				this.DrawerID = null;
			else
				this.DrawerID = rs.getString("DrawerID").trim();

			if( rs.getString("ActualDrawer") == null )
				this.ActualDrawer = null;
			else
				this.ActualDrawer = rs.getString("ActualDrawer").trim();

			if( rs.getString("ActualDrawerID") == null )
				this.ActualDrawerID = null;
			else
				this.ActualDrawerID = rs.getString("ActualDrawerID").trim();

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

			if( rs.getString("LastOperator") == null )
				this.LastOperator = null;
			else
				this.LastOperator = rs.getString("LastOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJCertifyGet表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJCertifyGetSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJCertifyGetSchema getSchema()
	{
		LJCertifyGetSchema aLJCertifyGetSchema = new LJCertifyGetSchema();
		aLJCertifyGetSchema.setSchema(this);
		return aLJCertifyGetSchema;
	}

	public LJCertifyGetDB getDB()
	{
		LJCertifyGetDB aDBOper = new LJCertifyGetDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJCertifyGet描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ActuGetNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolicyCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumGetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ShouldDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EnterAccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Drawer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DrawerID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActualDrawer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActualDrawerID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJCertifyGet>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ActuGetNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PolicyCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SumGetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ShouldDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			EnterAccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			Drawer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			DrawerID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ActualDrawer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ActualDrawerID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			LastOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJCertifyGetSchema";
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
		if (FCode.equalsIgnoreCase("ActuGetNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActuGetNo));
		}
		if (FCode.equalsIgnoreCase("GetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMode));
		}
		if (FCode.equalsIgnoreCase("PolicyCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyCom));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("SumGetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumGetMoney));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("ShouldDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getShouldDate()));
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
		}
		if (FCode.equalsIgnoreCase("Drawer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Drawer));
		}
		if (FCode.equalsIgnoreCase("DrawerID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DrawerID));
		}
		if (FCode.equalsIgnoreCase("ActualDrawer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActualDrawer));
		}
		if (FCode.equalsIgnoreCase("ActualDrawerID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActualDrawerID));
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
		if (FCode.equalsIgnoreCase("LastOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastOperator));
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
				strFieldValue = StrTool.GBKToUnicode(ActuGetNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GetMode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolicyCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 4:
				strFieldValue = String.valueOf(SumGetMoney);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getShouldDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Drawer);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(DrawerID);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ActualDrawer);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ActualDrawerID);
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
			case 18:
				strFieldValue = StrTool.GBKToUnicode(LastOperator);
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

		if (FCode.equalsIgnoreCase("ActuGetNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActuGetNo = FValue.trim();
			}
			else
				ActuGetNo = null;
		}
		if (FCode.equalsIgnoreCase("GetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetMode = FValue.trim();
			}
			else
				GetMode = null;
		}
		if (FCode.equalsIgnoreCase("PolicyCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyCom = FValue.trim();
			}
			else
				PolicyCom = null;
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
		if (FCode.equalsIgnoreCase("SumGetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumGetMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfDate = fDate.getDate( FValue );
			}
			else
				ConfDate = null;
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
		if (FCode.equalsIgnoreCase("ShouldDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ShouldDate = fDate.getDate( FValue );
			}
			else
				ShouldDate = null;
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EnterAccDate = fDate.getDate( FValue );
			}
			else
				EnterAccDate = null;
		}
		if (FCode.equalsIgnoreCase("Drawer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Drawer = FValue.trim();
			}
			else
				Drawer = null;
		}
		if (FCode.equalsIgnoreCase("DrawerID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DrawerID = FValue.trim();
			}
			else
				DrawerID = null;
		}
		if (FCode.equalsIgnoreCase("ActualDrawer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActualDrawer = FValue.trim();
			}
			else
				ActualDrawer = null;
		}
		if (FCode.equalsIgnoreCase("ActualDrawerID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActualDrawerID = FValue.trim();
			}
			else
				ActualDrawerID = null;
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
		if (FCode.equalsIgnoreCase("LastOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastOperator = FValue.trim();
			}
			else
				LastOperator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LJCertifyGetSchema other = (LJCertifyGetSchema)otherObject;
		return
			ActuGetNo.equals(other.getActuGetNo())
			&& GetMode.equals(other.getGetMode())
			&& PolicyCom.equals(other.getPolicyCom())
			&& ManageCom.equals(other.getManageCom())
			&& SumGetMoney == other.getSumGetMoney()
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& AgentCode.equals(other.getAgentCode())
			&& fDate.getString(ShouldDate).equals(other.getShouldDate())
			&& fDate.getString(EnterAccDate).equals(other.getEnterAccDate())
			&& Drawer.equals(other.getDrawer())
			&& DrawerID.equals(other.getDrawerID())
			&& ActualDrawer.equals(other.getActualDrawer())
			&& ActualDrawerID.equals(other.getActualDrawerID())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& LastOperator.equals(other.getLastOperator());
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
		if( strFieldName.equals("ActuGetNo") ) {
			return 0;
		}
		if( strFieldName.equals("GetMode") ) {
			return 1;
		}
		if( strFieldName.equals("PolicyCom") ) {
			return 2;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 3;
		}
		if( strFieldName.equals("SumGetMoney") ) {
			return 4;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 5;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 6;
		}
		if( strFieldName.equals("ShouldDate") ) {
			return 7;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return 8;
		}
		if( strFieldName.equals("Drawer") ) {
			return 9;
		}
		if( strFieldName.equals("DrawerID") ) {
			return 10;
		}
		if( strFieldName.equals("ActualDrawer") ) {
			return 11;
		}
		if( strFieldName.equals("ActualDrawerID") ) {
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
		if( strFieldName.equals("LastOperator") ) {
			return 18;
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
				strFieldName = "ActuGetNo";
				break;
			case 1:
				strFieldName = "GetMode";
				break;
			case 2:
				strFieldName = "PolicyCom";
				break;
			case 3:
				strFieldName = "ManageCom";
				break;
			case 4:
				strFieldName = "SumGetMoney";
				break;
			case 5:
				strFieldName = "ConfDate";
				break;
			case 6:
				strFieldName = "AgentCode";
				break;
			case 7:
				strFieldName = "ShouldDate";
				break;
			case 8:
				strFieldName = "EnterAccDate";
				break;
			case 9:
				strFieldName = "Drawer";
				break;
			case 10:
				strFieldName = "DrawerID";
				break;
			case 11:
				strFieldName = "ActualDrawer";
				break;
			case 12:
				strFieldName = "ActualDrawerID";
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
			case 18:
				strFieldName = "LastOperator";
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
		if( strFieldName.equals("ActuGetNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumGetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShouldDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Drawer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DrawerID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActualDrawer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActualDrawerID") ) {
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
		if( strFieldName.equals("LastOperator") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
