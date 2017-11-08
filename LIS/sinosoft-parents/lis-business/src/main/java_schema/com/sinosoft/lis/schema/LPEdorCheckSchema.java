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
import com.sinosoft.lis.db.LPEdorCheckDB;

/*
 * <p>ClassName: LPEdorCheckSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPEdorCheckSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPEdorCheckSchema.class);

	// @Field
	/** 保全受理号 */
	private String EdorAcceptNo;
	/** 抽检次数 */
	private int CheckTimes;
	/** 复核人 */
	private String ApproveOperator;
	/** 复核状态 */
	private String ApproveFlag;
	/** 复核意见 */
	private String ApproveContent;
	/** 复核日期 */
	private Date ApproveDate;
	/** 复核时间 */
	private String ApproveTime;
	/** 抽检人 */
	private String CheckOperator;
	/** 抽检状态 */
	private String CheckFlag;
	/** 抽检意见 */
	private String CheckContent;
	/** 抽检日期 */
	private Date CheckDate;
	/** 抽检时间 */
	private String CheckTime;
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

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPEdorCheckSchema()
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
		LPEdorCheckSchema cloned = (LPEdorCheckSchema)super.clone();
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

	public String getApproveOperator()
	{
		return ApproveOperator;
	}
	public void setApproveOperator(String aApproveOperator)
	{
		ApproveOperator = aApproveOperator;
	}
	/**
	* 1-复核通过<p>
	* 2-复核修改<p>
	* 3-复核终止
	*/
	public String getApproveFlag()
	{
		return ApproveFlag;
	}
	public void setApproveFlag(String aApproveFlag)
	{
		ApproveFlag = aApproveFlag;
	}
	public String getApproveContent()
	{
		return ApproveContent;
	}
	public void setApproveContent(String aApproveContent)
	{
		ApproveContent = aApproveContent;
	}
	public String getApproveDate()
	{
		if( ApproveDate != null )
			return fDate.getString(ApproveDate);
		else
			return null;
	}
	public void setApproveDate(Date aApproveDate)
	{
		ApproveDate = aApproveDate;
	}
	public void setApproveDate(String aApproveDate)
	{
		if (aApproveDate != null && !aApproveDate.equals("") )
		{
			ApproveDate = fDate.getDate( aApproveDate );
		}
		else
			ApproveDate = null;
	}

	public String getApproveTime()
	{
		return ApproveTime;
	}
	public void setApproveTime(String aApproveTime)
	{
		ApproveTime = aApproveTime;
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
	public String getCheckContent()
	{
		return CheckContent;
	}
	public void setCheckContent(String aCheckContent)
	{
		CheckContent = aCheckContent;
	}
	public String getCheckDate()
	{
		if( CheckDate != null )
			return fDate.getString(CheckDate);
		else
			return null;
	}
	public void setCheckDate(Date aCheckDate)
	{
		CheckDate = aCheckDate;
	}
	public void setCheckDate(String aCheckDate)
	{
		if (aCheckDate != null && !aCheckDate.equals("") )
		{
			CheckDate = fDate.getDate( aCheckDate );
		}
		else
			CheckDate = null;
	}

	public String getCheckTime()
	{
		return CheckTime;
	}
	public void setCheckTime(String aCheckTime)
	{
		CheckTime = aCheckTime;
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
	* 使用另外一个 LPEdorCheckSchema 对象给 Schema 赋值
	* @param: aLPEdorCheckSchema LPEdorCheckSchema
	**/
	public void setSchema(LPEdorCheckSchema aLPEdorCheckSchema)
	{
		this.EdorAcceptNo = aLPEdorCheckSchema.getEdorAcceptNo();
		this.CheckTimes = aLPEdorCheckSchema.getCheckTimes();
		this.ApproveOperator = aLPEdorCheckSchema.getApproveOperator();
		this.ApproveFlag = aLPEdorCheckSchema.getApproveFlag();
		this.ApproveContent = aLPEdorCheckSchema.getApproveContent();
		this.ApproveDate = fDate.getDate( aLPEdorCheckSchema.getApproveDate());
		this.ApproveTime = aLPEdorCheckSchema.getApproveTime();
		this.CheckOperator = aLPEdorCheckSchema.getCheckOperator();
		this.CheckFlag = aLPEdorCheckSchema.getCheckFlag();
		this.CheckContent = aLPEdorCheckSchema.getCheckContent();
		this.CheckDate = fDate.getDate( aLPEdorCheckSchema.getCheckDate());
		this.CheckTime = aLPEdorCheckSchema.getCheckTime();
		this.Operator = aLPEdorCheckSchema.getOperator();
		this.MakeDate = fDate.getDate( aLPEdorCheckSchema.getMakeDate());
		this.MakeTime = aLPEdorCheckSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPEdorCheckSchema.getModifyDate());
		this.ModifyTime = aLPEdorCheckSchema.getModifyTime();
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
			if( rs.getString("ApproveOperator") == null )
				this.ApproveOperator = null;
			else
				this.ApproveOperator = rs.getString("ApproveOperator").trim();

			if( rs.getString("ApproveFlag") == null )
				this.ApproveFlag = null;
			else
				this.ApproveFlag = rs.getString("ApproveFlag").trim();

			if( rs.getString("ApproveContent") == null )
				this.ApproveContent = null;
			else
				this.ApproveContent = rs.getString("ApproveContent").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
			if( rs.getString("ApproveTime") == null )
				this.ApproveTime = null;
			else
				this.ApproveTime = rs.getString("ApproveTime").trim();

			if( rs.getString("CheckOperator") == null )
				this.CheckOperator = null;
			else
				this.CheckOperator = rs.getString("CheckOperator").trim();

			if( rs.getString("CheckFlag") == null )
				this.CheckFlag = null;
			else
				this.CheckFlag = rs.getString("CheckFlag").trim();

			if( rs.getString("CheckContent") == null )
				this.CheckContent = null;
			else
				this.CheckContent = rs.getString("CheckContent").trim();

			this.CheckDate = rs.getDate("CheckDate");
			if( rs.getString("CheckTime") == null )
				this.CheckTime = null;
			else
				this.CheckTime = rs.getString("CheckTime").trim();

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
			logger.debug("数据库中的LPEdorCheck表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPEdorCheckSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPEdorCheckSchema getSchema()
	{
		LPEdorCheckSchema aLPEdorCheckSchema = new LPEdorCheckSchema();
		aLPEdorCheckSchema.setSchema(this);
		return aLPEdorCheckSchema;
	}

	public LPEdorCheckDB getDB()
	{
		LPEdorCheckDB aDBOper = new LPEdorCheckDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPEdorCheck描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorAcceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CheckTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CheckDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPEdorCheck>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorAcceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CheckTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ApproveOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ApproveContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CheckOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CheckFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CheckContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CheckDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			CheckTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPEdorCheckSchema";
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
		if (FCode.equalsIgnoreCase("ApproveOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveOperator));
		}
		if (FCode.equalsIgnoreCase("ApproveFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveFlag));
		}
		if (FCode.equalsIgnoreCase("ApproveContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveContent));
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
		}
		if (FCode.equalsIgnoreCase("ApproveTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveTime));
		}
		if (FCode.equalsIgnoreCase("CheckOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckOperator));
		}
		if (FCode.equalsIgnoreCase("CheckFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckFlag));
		}
		if (FCode.equalsIgnoreCase("CheckContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckContent));
		}
		if (FCode.equalsIgnoreCase("CheckDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCheckDate()));
		}
		if (FCode.equalsIgnoreCase("CheckTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckTime));
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
				strFieldValue = StrTool.GBKToUnicode(ApproveOperator);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ApproveContent);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CheckOperator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CheckFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CheckContent);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCheckDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CheckTime);
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
		if (FCode.equalsIgnoreCase("ApproveOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveOperator = FValue.trim();
			}
			else
				ApproveOperator = null;
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
		if (FCode.equalsIgnoreCase("ApproveContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveContent = FValue.trim();
			}
			else
				ApproveContent = null;
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ApproveDate = fDate.getDate( FValue );
			}
			else
				ApproveDate = null;
		}
		if (FCode.equalsIgnoreCase("ApproveTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveTime = FValue.trim();
			}
			else
				ApproveTime = null;
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
		if (FCode.equalsIgnoreCase("CheckContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckContent = FValue.trim();
			}
			else
				CheckContent = null;
		}
		if (FCode.equalsIgnoreCase("CheckDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CheckDate = fDate.getDate( FValue );
			}
			else
				CheckDate = null;
		}
		if (FCode.equalsIgnoreCase("CheckTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckTime = FValue.trim();
			}
			else
				CheckTime = null;
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
		LPEdorCheckSchema other = (LPEdorCheckSchema)otherObject;
		return
			EdorAcceptNo.equals(other.getEdorAcceptNo())
			&& CheckTimes == other.getCheckTimes()
			&& ApproveOperator.equals(other.getApproveOperator())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& ApproveContent.equals(other.getApproveContent())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& CheckOperator.equals(other.getCheckOperator())
			&& CheckFlag.equals(other.getCheckFlag())
			&& CheckContent.equals(other.getCheckContent())
			&& fDate.getString(CheckDate).equals(other.getCheckDate())
			&& CheckTime.equals(other.getCheckTime())
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
		if( strFieldName.equals("ApproveOperator") ) {
			return 2;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 3;
		}
		if( strFieldName.equals("ApproveContent") ) {
			return 4;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 5;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 6;
		}
		if( strFieldName.equals("CheckOperator") ) {
			return 7;
		}
		if( strFieldName.equals("CheckFlag") ) {
			return 8;
		}
		if( strFieldName.equals("CheckContent") ) {
			return 9;
		}
		if( strFieldName.equals("CheckDate") ) {
			return 10;
		}
		if( strFieldName.equals("CheckTime") ) {
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
				strFieldName = "ApproveOperator";
				break;
			case 3:
				strFieldName = "ApproveFlag";
				break;
			case 4:
				strFieldName = "ApproveContent";
				break;
			case 5:
				strFieldName = "ApproveDate";
				break;
			case 6:
				strFieldName = "ApproveTime";
				break;
			case 7:
				strFieldName = "CheckOperator";
				break;
			case 8:
				strFieldName = "CheckFlag";
				break;
			case 9:
				strFieldName = "CheckContent";
				break;
			case 10:
				strFieldName = "CheckDate";
				break;
			case 11:
				strFieldName = "CheckTime";
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
		if( strFieldName.equals("ApproveOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CheckTime") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
