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
import com.sinosoft.lis.db.LPContTempInfoDB;

/*
 * <p>ClassName: LPContTempInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPContTempInfoSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPContTempInfoSchema.class);

	// @Field
	/** 保全受理号 */
	private String EdorAcceptNo;
	/** 批单号 */
	private String EdorNo;
	/** 批改类型 */
	private String EdorType;
	/** 合同号码 */
	private String ContNo;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 被保人客户号 */
	private String InsuredNo;
	/** 状态 */
	private String State;
	/** 需要重算标记 */
	private String IsNeedReCal;
	/** 保全确认人 */
	private String ConfOperator;
	/** 确认日期 */
	private Date ConfDate;
	/** 申请人 */
	private String ApplyOperator;
	/** 申请日期 */
	private Date ApplyDate;
	/** 管理机构 */
	private String ManageCom;
	/** 备注 */
	private String Remark;
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
	/** 保全受理号(ic) */
	private String ICEdorAcceptNo;
	/** 批单号(ic) */
	private String ICEdorNo;

	public static final int FIELDNUM = 21;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPContTempInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "EdorAcceptNo";
		pk[1] = "EdorNo";
		pk[2] = "EdorType";
		pk[3] = "ContNo";
		pk[4] = "AppntNo";

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
		LPContTempInfoSchema cloned = (LPContTempInfoSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorAcceptNo()
	{
		return EdorAcceptNo;
	}
	public void setEdorAcceptNo(String aEdorAcceptNo)
	{
		EdorAcceptNo = aEdorAcceptNo;
	}
	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
	}
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		EdorType = aEdorType;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	/**
	* 家庭单时需要反写
	*/
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
	}
	/**
	* 变更的状态，如果客户资料变更完直至重算前状态为 0，重算保全确认后为 1
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	/**
	* 本次保单客户资料变更是否需要重算标记<p>
	* 0--不需要<p>
	* 1--需要
	*/
	public String getIsNeedReCal()
	{
		return IsNeedReCal;
	}
	public void setIsNeedReCal(String aIsNeedReCal)
	{
		IsNeedReCal = aIsNeedReCal;
	}
	public String getConfOperator()
	{
		return ConfOperator;
	}
	public void setConfOperator(String aConfOperator)
	{
		ConfOperator = aConfOperator;
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

	public String getApplyOperator()
	{
		return ApplyOperator;
	}
	public void setApplyOperator(String aApplyOperator)
	{
		ApplyOperator = aApplyOperator;
	}
	public String getApplyDate()
	{
		if( ApplyDate != null )
			return fDate.getString(ApplyDate);
		else
			return null;
	}
	public void setApplyDate(Date aApplyDate)
	{
		ApplyDate = aApplyDate;
	}
	public void setApplyDate(String aApplyDate)
	{
		if (aApplyDate != null && !aApplyDate.equals("") )
		{
			ApplyDate = fDate.getDate( aApplyDate );
		}
		else
			ApplyDate = null;
	}

	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
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
	public String getICEdorAcceptNo()
	{
		return ICEdorAcceptNo;
	}
	public void setICEdorAcceptNo(String aICEdorAcceptNo)
	{
		ICEdorAcceptNo = aICEdorAcceptNo;
	}
	public String getICEdorNo()
	{
		return ICEdorNo;
	}
	public void setICEdorNo(String aICEdorNo)
	{
		ICEdorNo = aICEdorNo;
	}

	/**
	* 使用另外一个 LPContTempInfoSchema 对象给 Schema 赋值
	* @param: aLPContTempInfoSchema LPContTempInfoSchema
	**/
	public void setSchema(LPContTempInfoSchema aLPContTempInfoSchema)
	{
		this.EdorAcceptNo = aLPContTempInfoSchema.getEdorAcceptNo();
		this.EdorNo = aLPContTempInfoSchema.getEdorNo();
		this.EdorType = aLPContTempInfoSchema.getEdorType();
		this.ContNo = aLPContTempInfoSchema.getContNo();
		this.AppntNo = aLPContTempInfoSchema.getAppntNo();
		this.InsuredNo = aLPContTempInfoSchema.getInsuredNo();
		this.State = aLPContTempInfoSchema.getState();
		this.IsNeedReCal = aLPContTempInfoSchema.getIsNeedReCal();
		this.ConfOperator = aLPContTempInfoSchema.getConfOperator();
		this.ConfDate = fDate.getDate( aLPContTempInfoSchema.getConfDate());
		this.ApplyOperator = aLPContTempInfoSchema.getApplyOperator();
		this.ApplyDate = fDate.getDate( aLPContTempInfoSchema.getApplyDate());
		this.ManageCom = aLPContTempInfoSchema.getManageCom();
		this.Remark = aLPContTempInfoSchema.getRemark();
		this.Operator = aLPContTempInfoSchema.getOperator();
		this.MakeDate = fDate.getDate( aLPContTempInfoSchema.getMakeDate());
		this.MakeTime = aLPContTempInfoSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPContTempInfoSchema.getModifyDate());
		this.ModifyTime = aLPContTempInfoSchema.getModifyTime();
		this.ICEdorAcceptNo = aLPContTempInfoSchema.getICEdorAcceptNo();
		this.ICEdorNo = aLPContTempInfoSchema.getICEdorNo();
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
			if( rs.getString("EdorAcceptNo") == null )
				this.EdorAcceptNo = null;
			else
				this.EdorAcceptNo = rs.getString("EdorAcceptNo").trim();

			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("IsNeedReCal") == null )
				this.IsNeedReCal = null;
			else
				this.IsNeedReCal = rs.getString("IsNeedReCal").trim();

			if( rs.getString("ConfOperator") == null )
				this.ConfOperator = null;
			else
				this.ConfOperator = rs.getString("ConfOperator").trim();

			this.ConfDate = rs.getDate("ConfDate");
			if( rs.getString("ApplyOperator") == null )
				this.ApplyOperator = null;
			else
				this.ApplyOperator = rs.getString("ApplyOperator").trim();

			this.ApplyDate = rs.getDate("ApplyDate");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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

			if( rs.getString("ICEdorAcceptNo") == null )
				this.ICEdorAcceptNo = null;
			else
				this.ICEdorAcceptNo = rs.getString("ICEdorAcceptNo").trim();

			if( rs.getString("ICEdorNo") == null )
				this.ICEdorNo = null;
			else
				this.ICEdorNo = rs.getString("ICEdorNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LPContTempInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPContTempInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPContTempInfoSchema getSchema()
	{
		LPContTempInfoSchema aLPContTempInfoSchema = new LPContTempInfoSchema();
		aLPContTempInfoSchema.setSchema(this);
		return aLPContTempInfoSchema;
	}

	public LPContTempInfoDB getDB()
	{
		LPContTempInfoDB aDBOper = new LPContTempInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPContTempInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorAcceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsNeedReCal)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ICEdorAcceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ICEdorNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPContTempInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorAcceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			IsNeedReCal = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ConfOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			ApplyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ICEdorAcceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ICEdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPContTempInfoSchema";
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
		if (FCode.equalsIgnoreCase("EdorAcceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorAcceptNo));
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("IsNeedReCal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsNeedReCal));
		}
		if (FCode.equalsIgnoreCase("ConfOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfOperator));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("ApplyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyOperator));
		}
		if (FCode.equalsIgnoreCase("ApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApplyDate()));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
		if (FCode.equalsIgnoreCase("ICEdorAcceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ICEdorAcceptNo));
		}
		if (FCode.equalsIgnoreCase("ICEdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ICEdorNo));
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
				strFieldValue = StrTool.GBKToUnicode(EdorAcceptNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(IsNeedReCal);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ConfOperator);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ApplyOperator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApplyDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ICEdorAcceptNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ICEdorNo);
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

		if (FCode.equalsIgnoreCase("EdorAcceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorAcceptNo = FValue.trim();
			}
			else
				EdorAcceptNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
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
		if (FCode.equalsIgnoreCase("IsNeedReCal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsNeedReCal = FValue.trim();
			}
			else
				IsNeedReCal = null;
		}
		if (FCode.equalsIgnoreCase("ConfOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfOperator = FValue.trim();
			}
			else
				ConfOperator = null;
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
		if (FCode.equalsIgnoreCase("ApplyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyOperator = FValue.trim();
			}
			else
				ApplyOperator = null;
		}
		if (FCode.equalsIgnoreCase("ApplyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ApplyDate = fDate.getDate( FValue );
			}
			else
				ApplyDate = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		if (FCode.equalsIgnoreCase("ICEdorAcceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ICEdorAcceptNo = FValue.trim();
			}
			else
				ICEdorAcceptNo = null;
		}
		if (FCode.equalsIgnoreCase("ICEdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ICEdorNo = FValue.trim();
			}
			else
				ICEdorNo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LPContTempInfoSchema other = (LPContTempInfoSchema)otherObject;
		return
			EdorAcceptNo.equals(other.getEdorAcceptNo())
			&& EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& ContNo.equals(other.getContNo())
			&& AppntNo.equals(other.getAppntNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& State.equals(other.getState())
			&& IsNeedReCal.equals(other.getIsNeedReCal())
			&& ConfOperator.equals(other.getConfOperator())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& ApplyOperator.equals(other.getApplyOperator())
			&& fDate.getString(ApplyDate).equals(other.getApplyDate())
			&& ManageCom.equals(other.getManageCom())
			&& Remark.equals(other.getRemark())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ICEdorAcceptNo.equals(other.getICEdorAcceptNo())
			&& ICEdorNo.equals(other.getICEdorNo());
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
		if( strFieldName.equals("EdorAcceptNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorNo") ) {
			return 1;
		}
		if( strFieldName.equals("EdorType") ) {
			return 2;
		}
		if( strFieldName.equals("ContNo") ) {
			return 3;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 4;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 5;
		}
		if( strFieldName.equals("State") ) {
			return 6;
		}
		if( strFieldName.equals("IsNeedReCal") ) {
			return 7;
		}
		if( strFieldName.equals("ConfOperator") ) {
			return 8;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 9;
		}
		if( strFieldName.equals("ApplyOperator") ) {
			return 10;
		}
		if( strFieldName.equals("ApplyDate") ) {
			return 11;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 12;
		}
		if( strFieldName.equals("Remark") ) {
			return 13;
		}
		if( strFieldName.equals("Operator") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 18;
		}
		if( strFieldName.equals("ICEdorAcceptNo") ) {
			return 19;
		}
		if( strFieldName.equals("ICEdorNo") ) {
			return 20;
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
				strFieldName = "EdorAcceptNo";
				break;
			case 1:
				strFieldName = "EdorNo";
				break;
			case 2:
				strFieldName = "EdorType";
				break;
			case 3:
				strFieldName = "ContNo";
				break;
			case 4:
				strFieldName = "AppntNo";
				break;
			case 5:
				strFieldName = "InsuredNo";
				break;
			case 6:
				strFieldName = "State";
				break;
			case 7:
				strFieldName = "IsNeedReCal";
				break;
			case 8:
				strFieldName = "ConfOperator";
				break;
			case 9:
				strFieldName = "ConfDate";
				break;
			case 10:
				strFieldName = "ApplyOperator";
				break;
			case 11:
				strFieldName = "ApplyDate";
				break;
			case 12:
				strFieldName = "ManageCom";
				break;
			case 13:
				strFieldName = "Remark";
				break;
			case 14:
				strFieldName = "Operator";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyDate";
				break;
			case 18:
				strFieldName = "ModifyTime";
				break;
			case 19:
				strFieldName = "ICEdorAcceptNo";
				break;
			case 20:
				strFieldName = "ICEdorNo";
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
		if( strFieldName.equals("EdorAcceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsNeedReCal") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ApplyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
		if( strFieldName.equals("ICEdorAcceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ICEdorNo") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
