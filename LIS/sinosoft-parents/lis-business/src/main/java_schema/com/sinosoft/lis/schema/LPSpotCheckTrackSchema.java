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
import com.sinosoft.lis.db.LPSpotCheckTrackDB;

/*
 * <p>ClassName: LPSpotCheckTrackSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPSpotCheckTrackSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPSpotCheckTrackSchema.class);

	// @Field
	/** 保全受理号 */
	private String EdorAcceptNo;
	/** 抽检次数 */
	private int CheckTimes;
	/** 抽检开始日期 */
	private Date CheckStartDate;
	/** 抽检完毕日期 */
	private Date CheckEndDate;
	/** 抽检人 */
	private String CheckOperator;
	/** 抽检结论 */
	private String CheckFlag;
	/** 不合格原因 */
	private String DisqualifyReason;
	/** 抽检备注 */
	private String CheckRemark;
	/** 修改开始日期 */
	private Date ModifyStartDate;
	/** 修改完毕日期 */
	private Date ModifyEndDate;
	/** 修改员 */
	private String ModifyOperator;
	/** 修改结论 */
	private String ModifyFlag;
	/** 修改备注 */
	private String ModifyRemark;
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
	public LPSpotCheckTrackSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "EdorAcceptNo";
		pk[1] = "CheckTimes";

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
		LPSpotCheckTrackSchema cloned = (LPSpotCheckTrackSchema)super.clone();
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
	public int getCheckTimes()
	{
		return CheckTimes;
	}
	public void setCheckTimes(int aCheckTimes)
	{
		CheckTimes = aCheckTimes;
	}
	public void setCheckTimes(String aCheckTimes)
	{
		if (aCheckTimes != null && !aCheckTimes.equals(""))
		{
			Integer tInteger = new Integer(aCheckTimes);
			int i = tInteger.intValue();
			CheckTimes = i;
		}
	}

	public String getCheckStartDate()
	{
		if( CheckStartDate != null )
			return fDate.getString(CheckStartDate);
		else
			return null;
	}
	public void setCheckStartDate(Date aCheckStartDate)
	{
		CheckStartDate = aCheckStartDate;
	}
	public void setCheckStartDate(String aCheckStartDate)
	{
		if (aCheckStartDate != null && !aCheckStartDate.equals("") )
		{
			CheckStartDate = fDate.getDate( aCheckStartDate );
		}
		else
			CheckStartDate = null;
	}

	public String getCheckEndDate()
	{
		if( CheckEndDate != null )
			return fDate.getString(CheckEndDate);
		else
			return null;
	}
	public void setCheckEndDate(Date aCheckEndDate)
	{
		CheckEndDate = aCheckEndDate;
	}
	public void setCheckEndDate(String aCheckEndDate)
	{
		if (aCheckEndDate != null && !aCheckEndDate.equals("") )
		{
			CheckEndDate = fDate.getDate( aCheckEndDate );
		}
		else
			CheckEndDate = null;
	}

	public String getCheckOperator()
	{
		return CheckOperator;
	}
	public void setCheckOperator(String aCheckOperator)
	{
		CheckOperator = aCheckOperator;
	}
	/**
	* 0-抽检中<p>
	* 1-抽检通过<p>
	* 2-抽检不通过
	*/
	public String getCheckFlag()
	{
		return CheckFlag;
	}
	public void setCheckFlag(String aCheckFlag)
	{
		CheckFlag = aCheckFlag;
	}
	public String getDisqualifyReason()
	{
		return DisqualifyReason;
	}
	public void setDisqualifyReason(String aDisqualifyReason)
	{
		DisqualifyReason = aDisqualifyReason;
	}
	public String getCheckRemark()
	{
		return CheckRemark;
	}
	public void setCheckRemark(String aCheckRemark)
	{
		CheckRemark = aCheckRemark;
	}
	public String getModifyStartDate()
	{
		if( ModifyStartDate != null )
			return fDate.getString(ModifyStartDate);
		else
			return null;
	}
	public void setModifyStartDate(Date aModifyStartDate)
	{
		ModifyStartDate = aModifyStartDate;
	}
	public void setModifyStartDate(String aModifyStartDate)
	{
		if (aModifyStartDate != null && !aModifyStartDate.equals("") )
		{
			ModifyStartDate = fDate.getDate( aModifyStartDate );
		}
		else
			ModifyStartDate = null;
	}

	public String getModifyEndDate()
	{
		if( ModifyEndDate != null )
			return fDate.getString(ModifyEndDate);
		else
			return null;
	}
	public void setModifyEndDate(Date aModifyEndDate)
	{
		ModifyEndDate = aModifyEndDate;
	}
	public void setModifyEndDate(String aModifyEndDate)
	{
		if (aModifyEndDate != null && !aModifyEndDate.equals("") )
		{
			ModifyEndDate = fDate.getDate( aModifyEndDate );
		}
		else
			ModifyEndDate = null;
	}

	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		ModifyOperator = aModifyOperator;
	}
	public String getModifyFlag()
	{
		return ModifyFlag;
	}
	public void setModifyFlag(String aModifyFlag)
	{
		ModifyFlag = aModifyFlag;
	}
	public String getModifyRemark()
	{
		return ModifyRemark;
	}
	public void setModifyRemark(String aModifyRemark)
	{
		ModifyRemark = aModifyRemark;
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
	* 使用另外一个 LPSpotCheckTrackSchema 对象给 Schema 赋值
	* @param: aLPSpotCheckTrackSchema LPSpotCheckTrackSchema
	**/
	public void setSchema(LPSpotCheckTrackSchema aLPSpotCheckTrackSchema)
	{
		this.EdorAcceptNo = aLPSpotCheckTrackSchema.getEdorAcceptNo();
		this.CheckTimes = aLPSpotCheckTrackSchema.getCheckTimes();
		this.CheckStartDate = fDate.getDate( aLPSpotCheckTrackSchema.getCheckStartDate());
		this.CheckEndDate = fDate.getDate( aLPSpotCheckTrackSchema.getCheckEndDate());
		this.CheckOperator = aLPSpotCheckTrackSchema.getCheckOperator();
		this.CheckFlag = aLPSpotCheckTrackSchema.getCheckFlag();
		this.DisqualifyReason = aLPSpotCheckTrackSchema.getDisqualifyReason();
		this.CheckRemark = aLPSpotCheckTrackSchema.getCheckRemark();
		this.ModifyStartDate = fDate.getDate( aLPSpotCheckTrackSchema.getModifyStartDate());
		this.ModifyEndDate = fDate.getDate( aLPSpotCheckTrackSchema.getModifyEndDate());
		this.ModifyOperator = aLPSpotCheckTrackSchema.getModifyOperator();
		this.ModifyFlag = aLPSpotCheckTrackSchema.getModifyFlag();
		this.ModifyRemark = aLPSpotCheckTrackSchema.getModifyRemark();
		this.Operator = aLPSpotCheckTrackSchema.getOperator();
		this.MakeDate = fDate.getDate( aLPSpotCheckTrackSchema.getMakeDate());
		this.MakeTime = aLPSpotCheckTrackSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPSpotCheckTrackSchema.getModifyDate());
		this.ModifyTime = aLPSpotCheckTrackSchema.getModifyTime();
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

			this.CheckTimes = rs.getInt("CheckTimes");
			this.CheckStartDate = rs.getDate("CheckStartDate");
			this.CheckEndDate = rs.getDate("CheckEndDate");
			if( rs.getString("CheckOperator") == null )
				this.CheckOperator = null;
			else
				this.CheckOperator = rs.getString("CheckOperator").trim();

			if( rs.getString("CheckFlag") == null )
				this.CheckFlag = null;
			else
				this.CheckFlag = rs.getString("CheckFlag").trim();

			if( rs.getString("DisqualifyReason") == null )
				this.DisqualifyReason = null;
			else
				this.DisqualifyReason = rs.getString("DisqualifyReason").trim();

			if( rs.getString("CheckRemark") == null )
				this.CheckRemark = null;
			else
				this.CheckRemark = rs.getString("CheckRemark").trim();

			this.ModifyStartDate = rs.getDate("ModifyStartDate");
			this.ModifyEndDate = rs.getDate("ModifyEndDate");
			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			if( rs.getString("ModifyFlag") == null )
				this.ModifyFlag = null;
			else
				this.ModifyFlag = rs.getString("ModifyFlag").trim();

			if( rs.getString("ModifyRemark") == null )
				this.ModifyRemark = null;
			else
				this.ModifyRemark = rs.getString("ModifyRemark").trim();

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
			logger.debug("数据库中的LPSpotCheckTrack表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPSpotCheckTrackSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPSpotCheckTrackSchema getSchema()
	{
		LPSpotCheckTrackSchema aLPSpotCheckTrackSchema = new LPSpotCheckTrackSchema();
		aLPSpotCheckTrackSchema.setSchema(this);
		return aLPSpotCheckTrackSchema;
	}

	public LPSpotCheckTrackDB getDB()
	{
		LPSpotCheckTrackDB aDBOper = new LPSpotCheckTrackDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPSpotCheckTrack描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorAcceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CheckTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CheckStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CheckEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DisqualifyReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPSpotCheckTrack>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorAcceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CheckTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			CheckStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			CheckEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			CheckOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CheckFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DisqualifyReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CheckRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ModifyStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			ModifyEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ModifyRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
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
			tError.moduleName = "LPSpotCheckTrackSchema";
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
		if (FCode.equalsIgnoreCase("CheckTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckTimes));
		}
		if (FCode.equalsIgnoreCase("CheckStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCheckStartDate()));
		}
		if (FCode.equalsIgnoreCase("CheckEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCheckEndDate()));
		}
		if (FCode.equalsIgnoreCase("CheckOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckOperator));
		}
		if (FCode.equalsIgnoreCase("CheckFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckFlag));
		}
		if (FCode.equalsIgnoreCase("DisqualifyReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DisqualifyReason));
		}
		if (FCode.equalsIgnoreCase("CheckRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckRemark));
		}
		if (FCode.equalsIgnoreCase("ModifyStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyStartDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyEndDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
		}
		if (FCode.equalsIgnoreCase("ModifyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyFlag));
		}
		if (FCode.equalsIgnoreCase("ModifyRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyRemark));
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
				strFieldValue = StrTool.GBKToUnicode(EdorAcceptNo);
				break;
			case 1:
				strFieldValue = String.valueOf(CheckTimes);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCheckStartDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCheckEndDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CheckOperator);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CheckFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DisqualifyReason);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CheckRemark);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyStartDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyEndDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ModifyFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ModifyRemark);
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

		if (FCode.equalsIgnoreCase("EdorAcceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorAcceptNo = FValue.trim();
			}
			else
				EdorAcceptNo = null;
		}
		if (FCode.equalsIgnoreCase("CheckTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CheckTimes = i;
			}
		}
		if (FCode.equalsIgnoreCase("CheckStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CheckStartDate = fDate.getDate( FValue );
			}
			else
				CheckStartDate = null;
		}
		if (FCode.equalsIgnoreCase("CheckEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CheckEndDate = fDate.getDate( FValue );
			}
			else
				CheckEndDate = null;
		}
		if (FCode.equalsIgnoreCase("CheckOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckOperator = FValue.trim();
			}
			else
				CheckOperator = null;
		}
		if (FCode.equalsIgnoreCase("CheckFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckFlag = FValue.trim();
			}
			else
				CheckFlag = null;
		}
		if (FCode.equalsIgnoreCase("DisqualifyReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DisqualifyReason = FValue.trim();
			}
			else
				DisqualifyReason = null;
		}
		if (FCode.equalsIgnoreCase("CheckRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckRemark = FValue.trim();
			}
			else
				CheckRemark = null;
		}
		if (FCode.equalsIgnoreCase("ModifyStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyStartDate = fDate.getDate( FValue );
			}
			else
				ModifyStartDate = null;
		}
		if (FCode.equalsIgnoreCase("ModifyEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyEndDate = fDate.getDate( FValue );
			}
			else
				ModifyEndDate = null;
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
		}
		if (FCode.equalsIgnoreCase("ModifyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyFlag = FValue.trim();
			}
			else
				ModifyFlag = null;
		}
		if (FCode.equalsIgnoreCase("ModifyRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyRemark = FValue.trim();
			}
			else
				ModifyRemark = null;
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
		LPSpotCheckTrackSchema other = (LPSpotCheckTrackSchema)otherObject;
		return
			EdorAcceptNo.equals(other.getEdorAcceptNo())
			&& CheckTimes == other.getCheckTimes()
			&& fDate.getString(CheckStartDate).equals(other.getCheckStartDate())
			&& fDate.getString(CheckEndDate).equals(other.getCheckEndDate())
			&& CheckOperator.equals(other.getCheckOperator())
			&& CheckFlag.equals(other.getCheckFlag())
			&& DisqualifyReason.equals(other.getDisqualifyReason())
			&& CheckRemark.equals(other.getCheckRemark())
			&& fDate.getString(ModifyStartDate).equals(other.getModifyStartDate())
			&& fDate.getString(ModifyEndDate).equals(other.getModifyEndDate())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& ModifyFlag.equals(other.getModifyFlag())
			&& ModifyRemark.equals(other.getModifyRemark())
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
		if( strFieldName.equals("EdorAcceptNo") ) {
			return 0;
		}
		if( strFieldName.equals("CheckTimes") ) {
			return 1;
		}
		if( strFieldName.equals("CheckStartDate") ) {
			return 2;
		}
		if( strFieldName.equals("CheckEndDate") ) {
			return 3;
		}
		if( strFieldName.equals("CheckOperator") ) {
			return 4;
		}
		if( strFieldName.equals("CheckFlag") ) {
			return 5;
		}
		if( strFieldName.equals("DisqualifyReason") ) {
			return 6;
		}
		if( strFieldName.equals("CheckRemark") ) {
			return 7;
		}
		if( strFieldName.equals("ModifyStartDate") ) {
			return 8;
		}
		if( strFieldName.equals("ModifyEndDate") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyFlag") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyRemark") ) {
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
				strFieldName = "EdorAcceptNo";
				break;
			case 1:
				strFieldName = "CheckTimes";
				break;
			case 2:
				strFieldName = "CheckStartDate";
				break;
			case 3:
				strFieldName = "CheckEndDate";
				break;
			case 4:
				strFieldName = "CheckOperator";
				break;
			case 5:
				strFieldName = "CheckFlag";
				break;
			case 6:
				strFieldName = "DisqualifyReason";
				break;
			case 7:
				strFieldName = "CheckRemark";
				break;
			case 8:
				strFieldName = "ModifyStartDate";
				break;
			case 9:
				strFieldName = "ModifyEndDate";
				break;
			case 10:
				strFieldName = "ModifyOperator";
				break;
			case 11:
				strFieldName = "ModifyFlag";
				break;
			case 12:
				strFieldName = "ModifyRemark";
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
		if( strFieldName.equals("EdorAcceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CheckStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CheckEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CheckOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DisqualifyReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyRemark") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 3:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
