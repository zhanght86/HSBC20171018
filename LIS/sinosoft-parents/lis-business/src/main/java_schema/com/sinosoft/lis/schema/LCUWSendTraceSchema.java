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
import com.sinosoft.lis.db.LCUWSendTraceDB;

/*
 * <p>ClassName: LCUWSendTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保流程
 */
public class LCUWSendTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCUWSendTraceSchema.class);

	// @Field
	/** 其他编码 */
	private String OtherNo;
	/** 其他编码类型 */
	private String OtherNoType;
	/** 上报流水号 */
	private int UWNo;
	/** 上报类型 */
	private String SendType;
	/** 上报标志 */
	private String SendFlag;
	/** 上报核保师编码 */
	private String UpUserCode;
	/** 上报核保师级别 */
	private String UpUWPopedom;
	/** 下级核保师编码 */
	private String DownUWCode;
	/** 下级核保师级别 */
	private String DownUWPopedom;
	/** 原核保师编码 */
	private String UWCode;
	/** 原核保师级别 */
	private String UWPopedom;
	/** 核保结论 */
	private String UWFlag;
	/** 核保意见 */
	private String UWIdea;
	/** 是否同意 */
	private String YesOrNo;
	/** 操作员 */
	private String Operator;
	/** 管理机构 */
	private String ManageCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 上报原因 */
	private String UpReason;

	public static final int FIELDNUM = 21;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCUWSendTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "OtherNo";
		pk[1] = "OtherNoType";
		pk[2] = "UWNo";

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
		LCUWSendTraceSchema cloned = (LCUWSendTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	* 1－个单合同号<p>
	* 2－团单合同号
	*/
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		OtherNoType = aOtherNoType;
	}
	public int getUWNo()
	{
		return UWNo;
	}
	public void setUWNo(int aUWNo)
	{
		UWNo = aUWNo;
	}
	public void setUWNo(String aUWNo)
	{
		if (aUWNo != null && !aUWNo.equals(""))
		{
			Integer tInteger = new Integer(aUWNo);
			int i = tInteger.intValue();
			UWNo = i;
		}
	}

	/**
	* 1 - 疑难案例<p>
	* 2 - 超权限
	*/
	public String getSendType()
	{
		return SendType;
	}
	public void setSendType(String aSendType)
	{
		SendType = aSendType;
	}
	/**
	* 1－上报
	*/
	public String getSendFlag()
	{
		return SendFlag;
	}
	public void setSendFlag(String aSendFlag)
	{
		SendFlag = aSendFlag;
	}
	public String getUpUserCode()
	{
		return UpUserCode;
	}
	public void setUpUserCode(String aUpUserCode)
	{
		UpUserCode = aUpUserCode;
	}
	public String getUpUWPopedom()
	{
		return UpUWPopedom;
	}
	public void setUpUWPopedom(String aUpUWPopedom)
	{
		UpUWPopedom = aUpUWPopedom;
	}
	public String getDownUWCode()
	{
		return DownUWCode;
	}
	public void setDownUWCode(String aDownUWCode)
	{
		DownUWCode = aDownUWCode;
	}
	public String getDownUWPopedom()
	{
		return DownUWPopedom;
	}
	public void setDownUWPopedom(String aDownUWPopedom)
	{
		DownUWPopedom = aDownUWPopedom;
	}
	public String getUWCode()
	{
		return UWCode;
	}
	public void setUWCode(String aUWCode)
	{
		UWCode = aUWCode;
	}
	public String getUWPopedom()
	{
		return UWPopedom;
	}
	public void setUWPopedom(String aUWPopedom)
	{
		UWPopedom = aUWPopedom;
	}
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		UWFlag = aUWFlag;
	}
	public String getUWIdea()
	{
		return UWIdea;
	}
	public void setUWIdea(String aUWIdea)
	{
		UWIdea = aUWIdea;
	}
	public String getYesOrNo()
	{
		return YesOrNo;
	}
	public void setYesOrNo(String aYesOrNo)
	{
		YesOrNo = aYesOrNo;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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
	public String getUpReason()
	{
		return UpReason;
	}
	public void setUpReason(String aUpReason)
	{
		UpReason = aUpReason;
	}

	/**
	* 使用另外一个 LCUWSendTraceSchema 对象给 Schema 赋值
	* @param: aLCUWSendTraceSchema LCUWSendTraceSchema
	**/
	public void setSchema(LCUWSendTraceSchema aLCUWSendTraceSchema)
	{
		this.OtherNo = aLCUWSendTraceSchema.getOtherNo();
		this.OtherNoType = aLCUWSendTraceSchema.getOtherNoType();
		this.UWNo = aLCUWSendTraceSchema.getUWNo();
		this.SendType = aLCUWSendTraceSchema.getSendType();
		this.SendFlag = aLCUWSendTraceSchema.getSendFlag();
		this.UpUserCode = aLCUWSendTraceSchema.getUpUserCode();
		this.UpUWPopedom = aLCUWSendTraceSchema.getUpUWPopedom();
		this.DownUWCode = aLCUWSendTraceSchema.getDownUWCode();
		this.DownUWPopedom = aLCUWSendTraceSchema.getDownUWPopedom();
		this.UWCode = aLCUWSendTraceSchema.getUWCode();
		this.UWPopedom = aLCUWSendTraceSchema.getUWPopedom();
		this.UWFlag = aLCUWSendTraceSchema.getUWFlag();
		this.UWIdea = aLCUWSendTraceSchema.getUWIdea();
		this.YesOrNo = aLCUWSendTraceSchema.getYesOrNo();
		this.Operator = aLCUWSendTraceSchema.getOperator();
		this.ManageCom = aLCUWSendTraceSchema.getManageCom();
		this.MakeDate = fDate.getDate( aLCUWSendTraceSchema.getMakeDate());
		this.MakeTime = aLCUWSendTraceSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCUWSendTraceSchema.getModifyDate());
		this.ModifyTime = aLCUWSendTraceSchema.getModifyTime();
		this.UpReason = aLCUWSendTraceSchema.getUpReason();
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
			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			this.UWNo = rs.getInt("UWNo");
			if( rs.getString("SendType") == null )
				this.SendType = null;
			else
				this.SendType = rs.getString("SendType").trim();

			if( rs.getString("SendFlag") == null )
				this.SendFlag = null;
			else
				this.SendFlag = rs.getString("SendFlag").trim();

			if( rs.getString("UpUserCode") == null )
				this.UpUserCode = null;
			else
				this.UpUserCode = rs.getString("UpUserCode").trim();

			if( rs.getString("UpUWPopedom") == null )
				this.UpUWPopedom = null;
			else
				this.UpUWPopedom = rs.getString("UpUWPopedom").trim();

			if( rs.getString("DownUWCode") == null )
				this.DownUWCode = null;
			else
				this.DownUWCode = rs.getString("DownUWCode").trim();

			if( rs.getString("DownUWPopedom") == null )
				this.DownUWPopedom = null;
			else
				this.DownUWPopedom = rs.getString("DownUWPopedom").trim();

			if( rs.getString("UWCode") == null )
				this.UWCode = null;
			else
				this.UWCode = rs.getString("UWCode").trim();

			if( rs.getString("UWPopedom") == null )
				this.UWPopedom = null;
			else
				this.UWPopedom = rs.getString("UWPopedom").trim();

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("UWIdea") == null )
				this.UWIdea = null;
			else
				this.UWIdea = rs.getString("UWIdea").trim();

			if( rs.getString("YesOrNo") == null )
				this.YesOrNo = null;
			else
				this.YesOrNo = rs.getString("YesOrNo").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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

			if( rs.getString("UpReason") == null )
				this.UpReason = null;
			else
				this.UpReason = rs.getString("UpReason").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCUWSendTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCUWSendTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCUWSendTraceSchema getSchema()
	{
		LCUWSendTraceSchema aLCUWSendTraceSchema = new LCUWSendTraceSchema();
		aLCUWSendTraceSchema.setSchema(this);
		return aLCUWSendTraceSchema;
	}

	public LCUWSendTraceDB getDB()
	{
		LCUWSendTraceDB aDBOper = new LCUWSendTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCUWSendTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UWNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpUserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpUWPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DownUWCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DownUWPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWIdea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(YesOrNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpReason));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCUWSendTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			UWNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			SendType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SendFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			UpUserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			UpUWPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DownUWCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DownUWPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			UWCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			UWPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			UWIdea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			YesOrNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			UpReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCUWSendTraceSchema";
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
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("UWNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWNo));
		}
		if (FCode.equalsIgnoreCase("SendType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendType));
		}
		if (FCode.equalsIgnoreCase("SendFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendFlag));
		}
		if (FCode.equalsIgnoreCase("UpUserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpUserCode));
		}
		if (FCode.equalsIgnoreCase("UpUWPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpUWPopedom));
		}
		if (FCode.equalsIgnoreCase("DownUWCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DownUWCode));
		}
		if (FCode.equalsIgnoreCase("DownUWPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DownUWPopedom));
		}
		if (FCode.equalsIgnoreCase("UWCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWCode));
		}
		if (FCode.equalsIgnoreCase("UWPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWPopedom));
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equalsIgnoreCase("UWIdea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWIdea));
		}
		if (FCode.equalsIgnoreCase("YesOrNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YesOrNo));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
		if (FCode.equalsIgnoreCase("UpReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpReason));
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
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 2:
				strFieldValue = String.valueOf(UWNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SendType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SendFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(UpUserCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(UpUWPopedom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DownUWCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DownUWPopedom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(UWCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(UWPopedom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(UWIdea);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(YesOrNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(UpReason);
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
		if (FCode.equalsIgnoreCase("UWNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				UWNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("SendType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendType = FValue.trim();
			}
			else
				SendType = null;
		}
		if (FCode.equalsIgnoreCase("SendFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendFlag = FValue.trim();
			}
			else
				SendFlag = null;
		}
		if (FCode.equalsIgnoreCase("UpUserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpUserCode = FValue.trim();
			}
			else
				UpUserCode = null;
		}
		if (FCode.equalsIgnoreCase("UpUWPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpUWPopedom = FValue.trim();
			}
			else
				UpUWPopedom = null;
		}
		if (FCode.equalsIgnoreCase("DownUWCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DownUWCode = FValue.trim();
			}
			else
				DownUWCode = null;
		}
		if (FCode.equalsIgnoreCase("DownUWPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DownUWPopedom = FValue.trim();
			}
			else
				DownUWPopedom = null;
		}
		if (FCode.equalsIgnoreCase("UWCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWCode = FValue.trim();
			}
			else
				UWCode = null;
		}
		if (FCode.equalsIgnoreCase("UWPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWPopedom = FValue.trim();
			}
			else
				UWPopedom = null;
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
		}
		if (FCode.equalsIgnoreCase("UWIdea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWIdea = FValue.trim();
			}
			else
				UWIdea = null;
		}
		if (FCode.equalsIgnoreCase("YesOrNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				YesOrNo = FValue.trim();
			}
			else
				YesOrNo = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equalsIgnoreCase("UpReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpReason = FValue.trim();
			}
			else
				UpReason = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCUWSendTraceSchema other = (LCUWSendTraceSchema)otherObject;
		return
			OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& UWNo == other.getUWNo()
			&& SendType.equals(other.getSendType())
			&& SendFlag.equals(other.getSendFlag())
			&& UpUserCode.equals(other.getUpUserCode())
			&& UpUWPopedom.equals(other.getUpUWPopedom())
			&& DownUWCode.equals(other.getDownUWCode())
			&& DownUWPopedom.equals(other.getDownUWPopedom())
			&& UWCode.equals(other.getUWCode())
			&& UWPopedom.equals(other.getUWPopedom())
			&& UWFlag.equals(other.getUWFlag())
			&& UWIdea.equals(other.getUWIdea())
			&& YesOrNo.equals(other.getYesOrNo())
			&& Operator.equals(other.getOperator())
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& UpReason.equals(other.getUpReason());
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
		if( strFieldName.equals("OtherNo") ) {
			return 0;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 1;
		}
		if( strFieldName.equals("UWNo") ) {
			return 2;
		}
		if( strFieldName.equals("SendType") ) {
			return 3;
		}
		if( strFieldName.equals("SendFlag") ) {
			return 4;
		}
		if( strFieldName.equals("UpUserCode") ) {
			return 5;
		}
		if( strFieldName.equals("UpUWPopedom") ) {
			return 6;
		}
		if( strFieldName.equals("DownUWCode") ) {
			return 7;
		}
		if( strFieldName.equals("DownUWPopedom") ) {
			return 8;
		}
		if( strFieldName.equals("UWCode") ) {
			return 9;
		}
		if( strFieldName.equals("UWPopedom") ) {
			return 10;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 11;
		}
		if( strFieldName.equals("UWIdea") ) {
			return 12;
		}
		if( strFieldName.equals("YesOrNo") ) {
			return 13;
		}
		if( strFieldName.equals("Operator") ) {
			return 14;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 15;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 16;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 19;
		}
		if( strFieldName.equals("UpReason") ) {
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
				strFieldName = "OtherNo";
				break;
			case 1:
				strFieldName = "OtherNoType";
				break;
			case 2:
				strFieldName = "UWNo";
				break;
			case 3:
				strFieldName = "SendType";
				break;
			case 4:
				strFieldName = "SendFlag";
				break;
			case 5:
				strFieldName = "UpUserCode";
				break;
			case 6:
				strFieldName = "UpUWPopedom";
				break;
			case 7:
				strFieldName = "DownUWCode";
				break;
			case 8:
				strFieldName = "DownUWPopedom";
				break;
			case 9:
				strFieldName = "UWCode";
				break;
			case 10:
				strFieldName = "UWPopedom";
				break;
			case 11:
				strFieldName = "UWFlag";
				break;
			case 12:
				strFieldName = "UWIdea";
				break;
			case 13:
				strFieldName = "YesOrNo";
				break;
			case 14:
				strFieldName = "Operator";
				break;
			case 15:
				strFieldName = "ManageCom";
				break;
			case 16:
				strFieldName = "MakeDate";
				break;
			case 17:
				strFieldName = "MakeTime";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
				strFieldName = "ModifyTime";
				break;
			case 20:
				strFieldName = "UpReason";
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
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SendType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpUserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpUWPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DownUWCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DownUWPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWIdea") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YesOrNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
		if( strFieldName.equals("UpReason") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
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
