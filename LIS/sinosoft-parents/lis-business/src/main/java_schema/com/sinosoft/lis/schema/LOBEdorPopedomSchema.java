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
import com.sinosoft.lis.db.LOBEdorPopedomDB;

/*
 * <p>ClassName: LOBEdorPopedomSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LOBEdorPopedomSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOBEdorPopedomSchema.class);

	// @Field
	/** 流水号 */
	private int SerialNo;
	/** 管理机构 */
	private String ManageCom;
	/** 权限级别 */
	private String EdorPopedom;
	/** 保全项目 */
	private String EdorCode;
	/** 险种性质 */
	private String RiskProp;
	/** 险种类别 */
	private String RiskPeriod;
	/** 申请权限 */
	private String ApplyFlag;
	/** 复核权限 */
	private String ApproveFlag;
	/** 申请日期修改权限 */
	private int AppDateModifyFlag;
	/** 收费限额 */
	private double LimitPayMoney;
	/** 付费限额 */
	private double LimitGetMoney;
	/** 起用时间 */
	private Date StartDate;
	/** 停用时间 */
	private Date EndDate;
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
	public LOBEdorPopedomSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "SerialNo";
		pk[1] = "ManageCom";
		pk[2] = "EdorPopedom";
		pk[3] = "EdorCode";
		pk[4] = "RiskProp";
		pk[5] = "RiskPeriod";

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
		LOBEdorPopedomSchema cloned = (LOBEdorPopedomSchema)super.clone();
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
	* 每修改一次保全权限,都进行一次备份.
	*/
	public int getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(int aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if (aSerialNo != null && !aSerialNo.equals(""))
		{
			Integer tInteger = new Integer(aSerialNo);
			int i = tInteger.intValue();
			SerialNo = i;
		}
	}

	/**
	* 可以支持分机构设定保全权限
	*/
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/**
	* 0--电话中心保全权限<p>
	* A--A级保全权限<p>
	* .......<p>
	* P--P级保全权限
	*/
	public String getEdorPopedom()
	{
		return EdorPopedom;
	}
	public void setEdorPopedom(String aEdorPopedom)
	{
		EdorPopedom = aEdorPopedom;
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
	* G--团体险<p>
	* I--个人险<p>
	* Y--银代险
	*/
	public String getRiskProp()
	{
		return RiskProp;
	}
	public void setRiskProp(String aRiskProp)
	{
		RiskProp = aRiskProp;
	}
	/**
	* L--长险(Long)<p>
	* M--一年期险(Middle)<p>
	* S--极短期险(Short)<p>
	* (团体保全权限需要根据险种类别指定不同的给付限额)
	*/
	public String getRiskPeriod()
	{
		return RiskPeriod;
	}
	public void setRiskPeriod(String aRiskPeriod)
	{
		RiskPeriod = aRiskPeriod;
	}
	/**
	* 0--无权限<p>
	* 1--有权限
	*/
	public String getApplyFlag()
	{
		return ApplyFlag;
	}
	public void setApplyFlag(String aApplyFlag)
	{
		ApplyFlag = aApplyFlag;
	}
	/**
	* 0--无权限<p>
	* 1--有权限
	*/
	public String getApproveFlag()
	{
		return ApproveFlag;
	}
	public void setApproveFlag(String aApproveFlag)
	{
		ApproveFlag = aApproveFlag;
	}
	/**
	* 保全申请时申请日期允许早于系统日期,但是需要有一个权限限制,不能无限制的提前申请日期,该权限制定用户最大可以提前申请日期的天数
	*/
	public int getAppDateModifyFlag()
	{
		return AppDateModifyFlag;
	}
	public void setAppDateModifyFlag(int aAppDateModifyFlag)
	{
		AppDateModifyFlag = aAppDateModifyFlag;
	}
	public void setAppDateModifyFlag(String aAppDateModifyFlag)
	{
		if (aAppDateModifyFlag != null && !aAppDateModifyFlag.equals(""))
		{
			Integer tInteger = new Integer(aAppDateModifyFlag);
			int i = tInteger.intValue();
			AppDateModifyFlag = i;
		}
	}

	public double getLimitPayMoney()
	{
		return LimitPayMoney;
	}
	public void setLimitPayMoney(double aLimitPayMoney)
	{
		LimitPayMoney = aLimitPayMoney;
	}
	public void setLimitPayMoney(String aLimitPayMoney)
	{
		if (aLimitPayMoney != null && !aLimitPayMoney.equals(""))
		{
			Double tDouble = new Double(aLimitPayMoney);
			double d = tDouble.doubleValue();
			LimitPayMoney = d;
		}
	}

	public double getLimitGetMoney()
	{
		return LimitGetMoney;
	}
	public void setLimitGetMoney(double aLimitGetMoney)
	{
		LimitGetMoney = aLimitGetMoney;
	}
	public void setLimitGetMoney(String aLimitGetMoney)
	{
		if (aLimitGetMoney != null && !aLimitGetMoney.equals(""))
		{
			Double tDouble = new Double(aLimitGetMoney);
			double d = tDouble.doubleValue();
			LimitGetMoney = d;
		}
	}

	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
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
	* 使用另外一个 LOBEdorPopedomSchema 对象给 Schema 赋值
	* @param: aLOBEdorPopedomSchema LOBEdorPopedomSchema
	**/
	public void setSchema(LOBEdorPopedomSchema aLOBEdorPopedomSchema)
	{
		this.SerialNo = aLOBEdorPopedomSchema.getSerialNo();
		this.ManageCom = aLOBEdorPopedomSchema.getManageCom();
		this.EdorPopedom = aLOBEdorPopedomSchema.getEdorPopedom();
		this.EdorCode = aLOBEdorPopedomSchema.getEdorCode();
		this.RiskProp = aLOBEdorPopedomSchema.getRiskProp();
		this.RiskPeriod = aLOBEdorPopedomSchema.getRiskPeriod();
		this.ApplyFlag = aLOBEdorPopedomSchema.getApplyFlag();
		this.ApproveFlag = aLOBEdorPopedomSchema.getApproveFlag();
		this.AppDateModifyFlag = aLOBEdorPopedomSchema.getAppDateModifyFlag();
		this.LimitPayMoney = aLOBEdorPopedomSchema.getLimitPayMoney();
		this.LimitGetMoney = aLOBEdorPopedomSchema.getLimitGetMoney();
		this.StartDate = fDate.getDate( aLOBEdorPopedomSchema.getStartDate());
		this.EndDate = fDate.getDate( aLOBEdorPopedomSchema.getEndDate());
		this.Operator = aLOBEdorPopedomSchema.getOperator();
		this.MakeDate = fDate.getDate( aLOBEdorPopedomSchema.getMakeDate());
		this.MakeTime = aLOBEdorPopedomSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLOBEdorPopedomSchema.getModifyDate());
		this.ModifyTime = aLOBEdorPopedomSchema.getModifyTime();
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
			this.SerialNo = rs.getInt("SerialNo");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("EdorPopedom") == null )
				this.EdorPopedom = null;
			else
				this.EdorPopedom = rs.getString("EdorPopedom").trim();

			if( rs.getString("EdorCode") == null )
				this.EdorCode = null;
			else
				this.EdorCode = rs.getString("EdorCode").trim();

			if( rs.getString("RiskProp") == null )
				this.RiskProp = null;
			else
				this.RiskProp = rs.getString("RiskProp").trim();

			if( rs.getString("RiskPeriod") == null )
				this.RiskPeriod = null;
			else
				this.RiskPeriod = rs.getString("RiskPeriod").trim();

			if( rs.getString("ApplyFlag") == null )
				this.ApplyFlag = null;
			else
				this.ApplyFlag = rs.getString("ApplyFlag").trim();

			if( rs.getString("ApproveFlag") == null )
				this.ApproveFlag = null;
			else
				this.ApproveFlag = rs.getString("ApproveFlag").trim();

			this.AppDateModifyFlag = rs.getInt("AppDateModifyFlag");
			this.LimitPayMoney = rs.getDouble("LimitPayMoney");
			this.LimitGetMoney = rs.getDouble("LimitGetMoney");
			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
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
			logger.debug("数据库中的LOBEdorPopedom表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBEdorPopedomSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOBEdorPopedomSchema getSchema()
	{
		LOBEdorPopedomSchema aLOBEdorPopedomSchema = new LOBEdorPopedomSchema();
		aLOBEdorPopedomSchema.setSchema(this);
		return aLOBEdorPopedomSchema;
	}

	public LOBEdorPopedomDB getDB()
	{
		LOBEdorPopedomDB aDBOper = new LOBEdorPopedomDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBEdorPopedom描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(SerialNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskProp)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskPeriod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AppDateModifyFlag));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LimitPayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LimitGetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBEdorPopedom>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			EdorPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			EdorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskProp = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskPeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ApplyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AppDateModifyFlag= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			LimitPayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			LimitGetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
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
			tError.moduleName = "LOBEdorPopedomSchema";
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("EdorPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorPopedom));
		}
		if (FCode.equalsIgnoreCase("EdorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorCode));
		}
		if (FCode.equalsIgnoreCase("RiskProp"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskProp));
		}
		if (FCode.equalsIgnoreCase("RiskPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskPeriod));
		}
		if (FCode.equalsIgnoreCase("ApplyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyFlag));
		}
		if (FCode.equalsIgnoreCase("ApproveFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveFlag));
		}
		if (FCode.equalsIgnoreCase("AppDateModifyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppDateModifyFlag));
		}
		if (FCode.equalsIgnoreCase("LimitPayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LimitPayMoney));
		}
		if (FCode.equalsIgnoreCase("LimitGetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LimitGetMoney));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
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
				strFieldValue = String.valueOf(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(EdorPopedom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(EdorCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskProp);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskPeriod);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ApplyFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 8:
				strFieldValue = String.valueOf(AppDateModifyFlag);
				break;
			case 9:
				strFieldValue = String.valueOf(LimitPayMoney);
				break;
			case 10:
				strFieldValue = String.valueOf(LimitGetMoney);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SerialNo = i;
			}
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
		if (FCode.equalsIgnoreCase("EdorPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorPopedom = FValue.trim();
			}
			else
				EdorPopedom = null;
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
		if (FCode.equalsIgnoreCase("RiskProp"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskProp = FValue.trim();
			}
			else
				RiskProp = null;
		}
		if (FCode.equalsIgnoreCase("RiskPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskPeriod = FValue.trim();
			}
			else
				RiskPeriod = null;
		}
		if (FCode.equalsIgnoreCase("ApplyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyFlag = FValue.trim();
			}
			else
				ApplyFlag = null;
		}
		if (FCode.equalsIgnoreCase("ApproveFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveFlag = FValue.trim();
			}
			else
				ApproveFlag = null;
		}
		if (FCode.equalsIgnoreCase("AppDateModifyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AppDateModifyFlag = i;
			}
		}
		if (FCode.equalsIgnoreCase("LimitPayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LimitPayMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("LimitGetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LimitGetMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
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
		LOBEdorPopedomSchema other = (LOBEdorPopedomSchema)otherObject;
		return
			SerialNo == other.getSerialNo()
			&& ManageCom.equals(other.getManageCom())
			&& EdorPopedom.equals(other.getEdorPopedom())
			&& EdorCode.equals(other.getEdorCode())
			&& RiskProp.equals(other.getRiskProp())
			&& RiskPeriod.equals(other.getRiskPeriod())
			&& ApplyFlag.equals(other.getApplyFlag())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& AppDateModifyFlag == other.getAppDateModifyFlag()
			&& LimitPayMoney == other.getLimitPayMoney()
			&& LimitGetMoney == other.getLimitGetMoney()
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
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
		if( strFieldName.equals("ManageCom") ) {
			return 1;
		}
		if( strFieldName.equals("EdorPopedom") ) {
			return 2;
		}
		if( strFieldName.equals("EdorCode") ) {
			return 3;
		}
		if( strFieldName.equals("RiskProp") ) {
			return 4;
		}
		if( strFieldName.equals("RiskPeriod") ) {
			return 5;
		}
		if( strFieldName.equals("ApplyFlag") ) {
			return 6;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 7;
		}
		if( strFieldName.equals("AppDateModifyFlag") ) {
			return 8;
		}
		if( strFieldName.equals("LimitPayMoney") ) {
			return 9;
		}
		if( strFieldName.equals("LimitGetMoney") ) {
			return 10;
		}
		if( strFieldName.equals("StartDate") ) {
			return 11;
		}
		if( strFieldName.equals("EndDate") ) {
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "ManageCom";
				break;
			case 2:
				strFieldName = "EdorPopedom";
				break;
			case 3:
				strFieldName = "EdorCode";
				break;
			case 4:
				strFieldName = "RiskProp";
				break;
			case 5:
				strFieldName = "RiskPeriod";
				break;
			case 6:
				strFieldName = "ApplyFlag";
				break;
			case 7:
				strFieldName = "ApproveFlag";
				break;
			case 8:
				strFieldName = "AppDateModifyFlag";
				break;
			case 9:
				strFieldName = "LimitPayMoney";
				break;
			case 10:
				strFieldName = "LimitGetMoney";
				break;
			case 11:
				strFieldName = "StartDate";
				break;
			case 12:
				strFieldName = "EndDate";
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskProp") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskPeriod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppDateModifyFlag") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("LimitPayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LimitGetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
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
