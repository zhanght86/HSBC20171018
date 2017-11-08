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
import com.sinosoft.lis.db.LPPayGetChangeTrackDB;

/*
 * <p>ClassName: LPPayGetChangeTrackSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPPayGetChangeTrackSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPPayGetChangeTrackSchema.class);

	// @Field
	/** 通知书号码 */
	private String GetNoticeNo;
	/** 保全受理号 */
	private String EdorAcceptNo;
	/** 变更次数 */
	private int ChangeTimes;
	/** 变更类型 */
	private String ChangeType;
	/** 管理机构 */
	private String ManageCom;
	/** 申请号码 */
	private String OtherNo;
	/** 申请号码类型 */
	private String OtherNoType;
	/** 原收付费方式 */
	private String OldPayGetForm;
	/** 原银行编码 */
	private String OldBankCode;
	/** 原银行帐号 */
	private String OldBankAccNo;
	/** 原帐户名 */
	private String OldAccName;
	/** 原领取人 */
	private String OldDrawer;
	/** 原领取人身份证号 */
	private String OldDrawerID;
	/** 新收付费方式 */
	private String NewPayGetForm;
	/** 新银行编码 */
	private String NewBankCode;
	/** 新银行帐号 */
	private String NewBankAccNo;
	/** 新帐户名 */
	private String NewAccName;
	/** 新领取人 */
	private String NewDrawer;
	/** 新领取人身份证号 */
	private String NewDrawerID;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最近一次修改日期 */
	private Date ModifyDate;
	/** 最近一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPPayGetChangeTrackSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "GetNoticeNo";
		pk[1] = "EdorAcceptNo";
		pk[2] = "ChangeTimes";

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
		LPPayGetChangeTrackSchema cloned = (LPPayGetChangeTrackSchema)super.clone();
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
	* 如果没有通知书通知客户交费，则<p>
	* 该字段就是交费收据号。
	*/
	public String getGetNoticeNo()
	{
		return GetNoticeNo;
	}
	public void setGetNoticeNo(String aGetNoticeNo)
	{
		GetNoticeNo = aGetNoticeNo;
	}
	/**
	* 合同号分为：<p>
	* 个单合同号<p>
	* 集体合同号
	*/
	public String getEdorAcceptNo()
	{
		return EdorAcceptNo;
	}
	public void setEdorAcceptNo(String aEdorAcceptNo)
	{
		EdorAcceptNo = aEdorAcceptNo;
	}
	public int getChangeTimes()
	{
		return ChangeTimes;
	}
	public void setChangeTimes(int aChangeTimes)
	{
		ChangeTimes = aChangeTimes;
	}
	public void setChangeTimes(String aChangeTimes)
	{
		if (aChangeTimes != null && !aChangeTimes.equals(""))
		{
			Integer tInteger = new Integer(aChangeTimes);
			int i = tInteger.intValue();
			ChangeTimes = i;
		}
	}

	/**
	* 1	收费<p>
	* 2	付费
	*/
	public String getChangeType()
	{
		return ChangeType;
	}
	public void setChangeType(String aChangeType)
	{
		ChangeType = aChangeType;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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
	* 1－个人客户号<p>
	* 2－团体客户号<p>
	* 3－个险保单号<p>
	* 4－团险保单号
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
	* 1	现金<p>
	* 3	支票<p>
	* 4	银行划款<p>
	* 7	网上支付<p>
	* ----------------<p>
	* select * from LDCode where CodeType = 'edorgetpayform'
	*/
	public String getOldPayGetForm()
	{
		return OldPayGetForm;
	}
	public void setOldPayGetForm(String aOldPayGetForm)
	{
		OldPayGetForm = aOldPayGetForm;
	}
	public String getOldBankCode()
	{
		return OldBankCode;
	}
	public void setOldBankCode(String aOldBankCode)
	{
		OldBankCode = aOldBankCode;
	}
	public String getOldBankAccNo()
	{
		return OldBankAccNo;
	}
	public void setOldBankAccNo(String aOldBankAccNo)
	{
		OldBankAccNo = aOldBankAccNo;
	}
	public String getOldAccName()
	{
		return OldAccName;
	}
	public void setOldAccName(String aOldAccName)
	{
		OldAccName = aOldAccName;
	}
	public String getOldDrawer()
	{
		return OldDrawer;
	}
	public void setOldDrawer(String aOldDrawer)
	{
		OldDrawer = aOldDrawer;
	}
	public String getOldDrawerID()
	{
		return OldDrawerID;
	}
	public void setOldDrawerID(String aOldDrawerID)
	{
		OldDrawerID = aOldDrawerID;
	}
	/**
	* 1	现金<p>
	* 3	支票<p>
	* 4	银行划款<p>
	* 7	网上支付<p>
	* ----------------<p>
	* select * from LDCode where CodeType = 'edorgetpayform'
	*/
	public String getNewPayGetForm()
	{
		return NewPayGetForm;
	}
	public void setNewPayGetForm(String aNewPayGetForm)
	{
		NewPayGetForm = aNewPayGetForm;
	}
	public String getNewBankCode()
	{
		return NewBankCode;
	}
	public void setNewBankCode(String aNewBankCode)
	{
		NewBankCode = aNewBankCode;
	}
	public String getNewBankAccNo()
	{
		return NewBankAccNo;
	}
	public void setNewBankAccNo(String aNewBankAccNo)
	{
		NewBankAccNo = aNewBankAccNo;
	}
	public String getNewAccName()
	{
		return NewAccName;
	}
	public void setNewAccName(String aNewAccName)
	{
		NewAccName = aNewAccName;
	}
	public String getNewDrawer()
	{
		return NewDrawer;
	}
	public void setNewDrawer(String aNewDrawer)
	{
		NewDrawer = aNewDrawer;
	}
	public String getNewDrawerID()
	{
		return NewDrawerID;
	}
	public void setNewDrawerID(String aNewDrawerID)
	{
		NewDrawerID = aNewDrawerID;
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
	* 使用另外一个 LPPayGetChangeTrackSchema 对象给 Schema 赋值
	* @param: aLPPayGetChangeTrackSchema LPPayGetChangeTrackSchema
	**/
	public void setSchema(LPPayGetChangeTrackSchema aLPPayGetChangeTrackSchema)
	{
		this.GetNoticeNo = aLPPayGetChangeTrackSchema.getGetNoticeNo();
		this.EdorAcceptNo = aLPPayGetChangeTrackSchema.getEdorAcceptNo();
		this.ChangeTimes = aLPPayGetChangeTrackSchema.getChangeTimes();
		this.ChangeType = aLPPayGetChangeTrackSchema.getChangeType();
		this.ManageCom = aLPPayGetChangeTrackSchema.getManageCom();
		this.OtherNo = aLPPayGetChangeTrackSchema.getOtherNo();
		this.OtherNoType = aLPPayGetChangeTrackSchema.getOtherNoType();
		this.OldPayGetForm = aLPPayGetChangeTrackSchema.getOldPayGetForm();
		this.OldBankCode = aLPPayGetChangeTrackSchema.getOldBankCode();
		this.OldBankAccNo = aLPPayGetChangeTrackSchema.getOldBankAccNo();
		this.OldAccName = aLPPayGetChangeTrackSchema.getOldAccName();
		this.OldDrawer = aLPPayGetChangeTrackSchema.getOldDrawer();
		this.OldDrawerID = aLPPayGetChangeTrackSchema.getOldDrawerID();
		this.NewPayGetForm = aLPPayGetChangeTrackSchema.getNewPayGetForm();
		this.NewBankCode = aLPPayGetChangeTrackSchema.getNewBankCode();
		this.NewBankAccNo = aLPPayGetChangeTrackSchema.getNewBankAccNo();
		this.NewAccName = aLPPayGetChangeTrackSchema.getNewAccName();
		this.NewDrawer = aLPPayGetChangeTrackSchema.getNewDrawer();
		this.NewDrawerID = aLPPayGetChangeTrackSchema.getNewDrawerID();
		this.Operator = aLPPayGetChangeTrackSchema.getOperator();
		this.MakeDate = fDate.getDate( aLPPayGetChangeTrackSchema.getMakeDate());
		this.MakeTime = aLPPayGetChangeTrackSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPPayGetChangeTrackSchema.getModifyDate());
		this.ModifyTime = aLPPayGetChangeTrackSchema.getModifyTime();
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
			if( rs.getString("GetNoticeNo") == null )
				this.GetNoticeNo = null;
			else
				this.GetNoticeNo = rs.getString("GetNoticeNo").trim();

			if( rs.getString("EdorAcceptNo") == null )
				this.EdorAcceptNo = null;
			else
				this.EdorAcceptNo = rs.getString("EdorAcceptNo").trim();

			this.ChangeTimes = rs.getInt("ChangeTimes");
			if( rs.getString("ChangeType") == null )
				this.ChangeType = null;
			else
				this.ChangeType = rs.getString("ChangeType").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("OldPayGetForm") == null )
				this.OldPayGetForm = null;
			else
				this.OldPayGetForm = rs.getString("OldPayGetForm").trim();

			if( rs.getString("OldBankCode") == null )
				this.OldBankCode = null;
			else
				this.OldBankCode = rs.getString("OldBankCode").trim();

			if( rs.getString("OldBankAccNo") == null )
				this.OldBankAccNo = null;
			else
				this.OldBankAccNo = rs.getString("OldBankAccNo").trim();

			if( rs.getString("OldAccName") == null )
				this.OldAccName = null;
			else
				this.OldAccName = rs.getString("OldAccName").trim();

			if( rs.getString("OldDrawer") == null )
				this.OldDrawer = null;
			else
				this.OldDrawer = rs.getString("OldDrawer").trim();

			if( rs.getString("OldDrawerID") == null )
				this.OldDrawerID = null;
			else
				this.OldDrawerID = rs.getString("OldDrawerID").trim();

			if( rs.getString("NewPayGetForm") == null )
				this.NewPayGetForm = null;
			else
				this.NewPayGetForm = rs.getString("NewPayGetForm").trim();

			if( rs.getString("NewBankCode") == null )
				this.NewBankCode = null;
			else
				this.NewBankCode = rs.getString("NewBankCode").trim();

			if( rs.getString("NewBankAccNo") == null )
				this.NewBankAccNo = null;
			else
				this.NewBankAccNo = rs.getString("NewBankAccNo").trim();

			if( rs.getString("NewAccName") == null )
				this.NewAccName = null;
			else
				this.NewAccName = rs.getString("NewAccName").trim();

			if( rs.getString("NewDrawer") == null )
				this.NewDrawer = null;
			else
				this.NewDrawer = rs.getString("NewDrawer").trim();

			if( rs.getString("NewDrawerID") == null )
				this.NewDrawerID = null;
			else
				this.NewDrawerID = rs.getString("NewDrawerID").trim();

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
			logger.debug("数据库中的LPPayGetChangeTrack表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPayGetChangeTrackSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPPayGetChangeTrackSchema getSchema()
	{
		LPPayGetChangeTrackSchema aLPPayGetChangeTrackSchema = new LPPayGetChangeTrackSchema();
		aLPPayGetChangeTrackSchema.setSchema(this);
		return aLPPayGetChangeTrackSchema;
	}

	public LPPayGetChangeTrackDB getDB()
	{
		LPPayGetChangeTrackDB aDBOper = new LPPayGetChangeTrackDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPPayGetChangeTrack描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GetNoticeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorAcceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChangeTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChangeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldPayGetForm)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldDrawer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldDrawerID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewPayGetForm)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewDrawer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewDrawerID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPPayGetChangeTrack>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GetNoticeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorAcceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ChangeTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			ChangeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			OldPayGetForm = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			OldBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			OldBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			OldAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			OldDrawer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			OldDrawerID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			NewPayGetForm = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			NewBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			NewBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			NewAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			NewDrawer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			NewDrawerID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPayGetChangeTrackSchema";
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
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetNoticeNo));
		}
		if (FCode.equalsIgnoreCase("EdorAcceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorAcceptNo));
		}
		if (FCode.equalsIgnoreCase("ChangeTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChangeTimes));
		}
		if (FCode.equalsIgnoreCase("ChangeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChangeType));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("OldPayGetForm"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldPayGetForm));
		}
		if (FCode.equalsIgnoreCase("OldBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldBankCode));
		}
		if (FCode.equalsIgnoreCase("OldBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldBankAccNo));
		}
		if (FCode.equalsIgnoreCase("OldAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldAccName));
		}
		if (FCode.equalsIgnoreCase("OldDrawer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldDrawer));
		}
		if (FCode.equalsIgnoreCase("OldDrawerID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldDrawerID));
		}
		if (FCode.equalsIgnoreCase("NewPayGetForm"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewPayGetForm));
		}
		if (FCode.equalsIgnoreCase("NewBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewBankCode));
		}
		if (FCode.equalsIgnoreCase("NewBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewBankAccNo));
		}
		if (FCode.equalsIgnoreCase("NewAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewAccName));
		}
		if (FCode.equalsIgnoreCase("NewDrawer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewDrawer));
		}
		if (FCode.equalsIgnoreCase("NewDrawerID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewDrawerID));
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
				strFieldValue = StrTool.GBKToUnicode(GetNoticeNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorAcceptNo);
				break;
			case 2:
				strFieldValue = String.valueOf(ChangeTimes);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ChangeType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(OldPayGetForm);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(OldBankCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(OldBankAccNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(OldAccName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(OldDrawer);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(OldDrawerID);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(NewPayGetForm);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(NewBankCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(NewBankAccNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(NewAccName);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(NewDrawer);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(NewDrawerID);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Operator);
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

		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetNoticeNo = FValue.trim();
			}
			else
				GetNoticeNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorAcceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorAcceptNo = FValue.trim();
			}
			else
				EdorAcceptNo = null;
		}
		if (FCode.equalsIgnoreCase("ChangeTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ChangeTimes = i;
			}
		}
		if (FCode.equalsIgnoreCase("ChangeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChangeType = FValue.trim();
			}
			else
				ChangeType = null;
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
		if (FCode.equalsIgnoreCase("OldPayGetForm"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldPayGetForm = FValue.trim();
			}
			else
				OldPayGetForm = null;
		}
		if (FCode.equalsIgnoreCase("OldBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldBankCode = FValue.trim();
			}
			else
				OldBankCode = null;
		}
		if (FCode.equalsIgnoreCase("OldBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldBankAccNo = FValue.trim();
			}
			else
				OldBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("OldAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldAccName = FValue.trim();
			}
			else
				OldAccName = null;
		}
		if (FCode.equalsIgnoreCase("OldDrawer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldDrawer = FValue.trim();
			}
			else
				OldDrawer = null;
		}
		if (FCode.equalsIgnoreCase("OldDrawerID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldDrawerID = FValue.trim();
			}
			else
				OldDrawerID = null;
		}
		if (FCode.equalsIgnoreCase("NewPayGetForm"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewPayGetForm = FValue.trim();
			}
			else
				NewPayGetForm = null;
		}
		if (FCode.equalsIgnoreCase("NewBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewBankCode = FValue.trim();
			}
			else
				NewBankCode = null;
		}
		if (FCode.equalsIgnoreCase("NewBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewBankAccNo = FValue.trim();
			}
			else
				NewBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("NewAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewAccName = FValue.trim();
			}
			else
				NewAccName = null;
		}
		if (FCode.equalsIgnoreCase("NewDrawer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewDrawer = FValue.trim();
			}
			else
				NewDrawer = null;
		}
		if (FCode.equalsIgnoreCase("NewDrawerID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewDrawerID = FValue.trim();
			}
			else
				NewDrawerID = null;
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
		LPPayGetChangeTrackSchema other = (LPPayGetChangeTrackSchema)otherObject;
		return
			GetNoticeNo.equals(other.getGetNoticeNo())
			&& EdorAcceptNo.equals(other.getEdorAcceptNo())
			&& ChangeTimes == other.getChangeTimes()
			&& ChangeType.equals(other.getChangeType())
			&& ManageCom.equals(other.getManageCom())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& OldPayGetForm.equals(other.getOldPayGetForm())
			&& OldBankCode.equals(other.getOldBankCode())
			&& OldBankAccNo.equals(other.getOldBankAccNo())
			&& OldAccName.equals(other.getOldAccName())
			&& OldDrawer.equals(other.getOldDrawer())
			&& OldDrawerID.equals(other.getOldDrawerID())
			&& NewPayGetForm.equals(other.getNewPayGetForm())
			&& NewBankCode.equals(other.getNewBankCode())
			&& NewBankAccNo.equals(other.getNewBankAccNo())
			&& NewAccName.equals(other.getNewAccName())
			&& NewDrawer.equals(other.getNewDrawer())
			&& NewDrawerID.equals(other.getNewDrawerID())
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
		if( strFieldName.equals("GetNoticeNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorAcceptNo") ) {
			return 1;
		}
		if( strFieldName.equals("ChangeTimes") ) {
			return 2;
		}
		if( strFieldName.equals("ChangeType") ) {
			return 3;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 4;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 5;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 6;
		}
		if( strFieldName.equals("OldPayGetForm") ) {
			return 7;
		}
		if( strFieldName.equals("OldBankCode") ) {
			return 8;
		}
		if( strFieldName.equals("OldBankAccNo") ) {
			return 9;
		}
		if( strFieldName.equals("OldAccName") ) {
			return 10;
		}
		if( strFieldName.equals("OldDrawer") ) {
			return 11;
		}
		if( strFieldName.equals("OldDrawerID") ) {
			return 12;
		}
		if( strFieldName.equals("NewPayGetForm") ) {
			return 13;
		}
		if( strFieldName.equals("NewBankCode") ) {
			return 14;
		}
		if( strFieldName.equals("NewBankAccNo") ) {
			return 15;
		}
		if( strFieldName.equals("NewAccName") ) {
			return 16;
		}
		if( strFieldName.equals("NewDrawer") ) {
			return 17;
		}
		if( strFieldName.equals("NewDrawerID") ) {
			return 18;
		}
		if( strFieldName.equals("Operator") ) {
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
				strFieldName = "GetNoticeNo";
				break;
			case 1:
				strFieldName = "EdorAcceptNo";
				break;
			case 2:
				strFieldName = "ChangeTimes";
				break;
			case 3:
				strFieldName = "ChangeType";
				break;
			case 4:
				strFieldName = "ManageCom";
				break;
			case 5:
				strFieldName = "OtherNo";
				break;
			case 6:
				strFieldName = "OtherNoType";
				break;
			case 7:
				strFieldName = "OldPayGetForm";
				break;
			case 8:
				strFieldName = "OldBankCode";
				break;
			case 9:
				strFieldName = "OldBankAccNo";
				break;
			case 10:
				strFieldName = "OldAccName";
				break;
			case 11:
				strFieldName = "OldDrawer";
				break;
			case 12:
				strFieldName = "OldDrawerID";
				break;
			case 13:
				strFieldName = "NewPayGetForm";
				break;
			case 14:
				strFieldName = "NewBankCode";
				break;
			case 15:
				strFieldName = "NewBankAccNo";
				break;
			case 16:
				strFieldName = "NewAccName";
				break;
			case 17:
				strFieldName = "NewDrawer";
				break;
			case 18:
				strFieldName = "NewDrawerID";
				break;
			case 19:
				strFieldName = "Operator";
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
		if( strFieldName.equals("GetNoticeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorAcceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChangeTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ChangeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldPayGetForm") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldDrawer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldDrawerID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewPayGetForm") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewDrawer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewDrawerID") ) {
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
				nFieldType = Schema.TYPE_INT;
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
