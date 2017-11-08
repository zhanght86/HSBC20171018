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
import com.sinosoft.lis.db.LZSysCertifyDB;

/*
 * <p>ClassName: LZSysCertifySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LZSysCertifySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LZSysCertifySchema.class);

	// @Field
	/** 单证编码 */
	private String CertifyCode;
	/** 单证号码 */
	private String CertifyNo;
	/** 有效期 */
	private Date ValidDate;
	/** 发放机构 */
	private String SendOutCom;
	/** 接受机构 */
	private String ReceiveCom;
	/** 经办人 */
	private String Handler;
	/** 经办日期 */
	private Date HandleDate;
	/** 发放批次号 */
	private String SendNo;
	/** 回收日期 */
	private Date TakeBackDate;
	/** 回收清算单号 */
	private String TakeBackNo;
	/** 回收操作员 */
	private String TakeBackOperator;
	/** 回收操作日期 */
	private Date TakeBackMakeDate;
	/** 回收操作时间 */
	private String TakeBackMakeTime;
	/** 状态标志 */
	private String StateFlag;
	/** 状态 */
	private String State;
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

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LZSysCertifySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CertifyCode";
		pk[1] = "CertifyNo";

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
		LZSysCertifySchema cloned = (LZSysCertifySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCertifyCode()
	{
		return CertifyCode;
	}
	public void setCertifyCode(String aCertifyCode)
	{
		CertifyCode = aCertifyCode;
	}
	public String getCertifyNo()
	{
		return CertifyNo;
	}
	public void setCertifyNo(String aCertifyNo)
	{
		CertifyNo = aCertifyNo;
	}
	public String getValidDate()
	{
		if( ValidDate != null )
			return fDate.getString(ValidDate);
		else
			return null;
	}
	public void setValidDate(Date aValidDate)
	{
		ValidDate = aValidDate;
	}
	public void setValidDate(String aValidDate)
	{
		if (aValidDate != null && !aValidDate.equals("") )
		{
			ValidDate = fDate.getDate( aValidDate );
		}
		else
			ValidDate = null;
	}

	public String getSendOutCom()
	{
		return SendOutCom;
	}
	public void setSendOutCom(String aSendOutCom)
	{
		SendOutCom = aSendOutCom;
	}
	public String getReceiveCom()
	{
		return ReceiveCom;
	}
	public void setReceiveCom(String aReceiveCom)
	{
		ReceiveCom = aReceiveCom;
	}
	public String getHandler()
	{
		return Handler;
	}
	public void setHandler(String aHandler)
	{
		Handler = aHandler;
	}
	public String getHandleDate()
	{
		if( HandleDate != null )
			return fDate.getString(HandleDate);
		else
			return null;
	}
	public void setHandleDate(Date aHandleDate)
	{
		HandleDate = aHandleDate;
	}
	public void setHandleDate(String aHandleDate)
	{
		if (aHandleDate != null && !aHandleDate.equals("") )
		{
			HandleDate = fDate.getDate( aHandleDate );
		}
		else
			HandleDate = null;
	}

	public String getSendNo()
	{
		return SendNo;
	}
	public void setSendNo(String aSendNo)
	{
		SendNo = aSendNo;
	}
	public String getTakeBackDate()
	{
		if( TakeBackDate != null )
			return fDate.getString(TakeBackDate);
		else
			return null;
	}
	public void setTakeBackDate(Date aTakeBackDate)
	{
		TakeBackDate = aTakeBackDate;
	}
	public void setTakeBackDate(String aTakeBackDate)
	{
		if (aTakeBackDate != null && !aTakeBackDate.equals("") )
		{
			TakeBackDate = fDate.getDate( aTakeBackDate );
		}
		else
			TakeBackDate = null;
	}

	/**
	* 回收清算单号
	*/
	public String getTakeBackNo()
	{
		return TakeBackNo;
	}
	public void setTakeBackNo(String aTakeBackNo)
	{
		TakeBackNo = aTakeBackNo;
	}
	public String getTakeBackOperator()
	{
		return TakeBackOperator;
	}
	public void setTakeBackOperator(String aTakeBackOperator)
	{
		TakeBackOperator = aTakeBackOperator;
	}
	public String getTakeBackMakeDate()
	{
		if( TakeBackMakeDate != null )
			return fDate.getString(TakeBackMakeDate);
		else
			return null;
	}
	public void setTakeBackMakeDate(Date aTakeBackMakeDate)
	{
		TakeBackMakeDate = aTakeBackMakeDate;
	}
	public void setTakeBackMakeDate(String aTakeBackMakeDate)
	{
		if (aTakeBackMakeDate != null && !aTakeBackMakeDate.equals("") )
		{
			TakeBackMakeDate = fDate.getDate( aTakeBackMakeDate );
		}
		else
			TakeBackMakeDate = null;
	}

	public String getTakeBackMakeTime()
	{
		return TakeBackMakeTime;
	}
	public void setTakeBackMakeTime(String aTakeBackMakeTime)
	{
		TakeBackMakeTime = aTakeBackMakeTime;
	}
	/**
	* 0：  未用<p>
	* 1：  正常回收<p>
	* 2：  作废<p>
	* 3：  挂失<p>
	* 4：  销毁（新增）
	*/
	public String getStateFlag()
	{
		return StateFlag;
	}
	public void setStateFlag(String aStateFlag)
	{
		StateFlag = aStateFlag;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
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
	* 使用另外一个 LZSysCertifySchema 对象给 Schema 赋值
	* @param: aLZSysCertifySchema LZSysCertifySchema
	**/
	public void setSchema(LZSysCertifySchema aLZSysCertifySchema)
	{
		this.CertifyCode = aLZSysCertifySchema.getCertifyCode();
		this.CertifyNo = aLZSysCertifySchema.getCertifyNo();
		this.ValidDate = fDate.getDate( aLZSysCertifySchema.getValidDate());
		this.SendOutCom = aLZSysCertifySchema.getSendOutCom();
		this.ReceiveCom = aLZSysCertifySchema.getReceiveCom();
		this.Handler = aLZSysCertifySchema.getHandler();
		this.HandleDate = fDate.getDate( aLZSysCertifySchema.getHandleDate());
		this.SendNo = aLZSysCertifySchema.getSendNo();
		this.TakeBackDate = fDate.getDate( aLZSysCertifySchema.getTakeBackDate());
		this.TakeBackNo = aLZSysCertifySchema.getTakeBackNo();
		this.TakeBackOperator = aLZSysCertifySchema.getTakeBackOperator();
		this.TakeBackMakeDate = fDate.getDate( aLZSysCertifySchema.getTakeBackMakeDate());
		this.TakeBackMakeTime = aLZSysCertifySchema.getTakeBackMakeTime();
		this.StateFlag = aLZSysCertifySchema.getStateFlag();
		this.State = aLZSysCertifySchema.getState();
		this.Operator = aLZSysCertifySchema.getOperator();
		this.MakeDate = fDate.getDate( aLZSysCertifySchema.getMakeDate());
		this.MakeTime = aLZSysCertifySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLZSysCertifySchema.getModifyDate());
		this.ModifyTime = aLZSysCertifySchema.getModifyTime();
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
			if( rs.getString("CertifyCode") == null )
				this.CertifyCode = null;
			else
				this.CertifyCode = rs.getString("CertifyCode").trim();

			if( rs.getString("CertifyNo") == null )
				this.CertifyNo = null;
			else
				this.CertifyNo = rs.getString("CertifyNo").trim();

			this.ValidDate = rs.getDate("ValidDate");
			if( rs.getString("SendOutCom") == null )
				this.SendOutCom = null;
			else
				this.SendOutCom = rs.getString("SendOutCom").trim();

			if( rs.getString("ReceiveCom") == null )
				this.ReceiveCom = null;
			else
				this.ReceiveCom = rs.getString("ReceiveCom").trim();

			if( rs.getString("Handler") == null )
				this.Handler = null;
			else
				this.Handler = rs.getString("Handler").trim();

			this.HandleDate = rs.getDate("HandleDate");
			if( rs.getString("SendNo") == null )
				this.SendNo = null;
			else
				this.SendNo = rs.getString("SendNo").trim();

			this.TakeBackDate = rs.getDate("TakeBackDate");
			if( rs.getString("TakeBackNo") == null )
				this.TakeBackNo = null;
			else
				this.TakeBackNo = rs.getString("TakeBackNo").trim();

			if( rs.getString("TakeBackOperator") == null )
				this.TakeBackOperator = null;
			else
				this.TakeBackOperator = rs.getString("TakeBackOperator").trim();

			this.TakeBackMakeDate = rs.getDate("TakeBackMakeDate");
			if( rs.getString("TakeBackMakeTime") == null )
				this.TakeBackMakeTime = null;
			else
				this.TakeBackMakeTime = rs.getString("TakeBackMakeTime").trim();

			if( rs.getString("StateFlag") == null )
				this.StateFlag = null;
			else
				this.StateFlag = rs.getString("StateFlag").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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
			logger.debug("数据库中的LZSysCertify表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZSysCertifySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LZSysCertifySchema getSchema()
	{
		LZSysCertifySchema aLZSysCertifySchema = new LZSysCertifySchema();
		aLZSysCertifySchema.setSchema(this);
		return aLZSysCertifySchema;
	}

	public LZSysCertifyDB getDB()
	{
		LZSysCertifyDB aDBOper = new LZSysCertifyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZSysCertify描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CertifyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ValidDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendOutCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiveCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Handler)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( HandleDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TakeBackDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TakeBackNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TakeBackOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TakeBackMakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TakeBackMakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZSysCertify>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CertifyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ValidDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			SendOutCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ReceiveCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Handler = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			HandleDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			SendNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			TakeBackDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			TakeBackNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			TakeBackOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			TakeBackMakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			TakeBackMakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZSysCertifySchema";
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
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyCode));
		}
		if (FCode.equalsIgnoreCase("CertifyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyNo));
		}
		if (FCode.equalsIgnoreCase("ValidDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getValidDate()));
		}
		if (FCode.equalsIgnoreCase("SendOutCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendOutCom));
		}
		if (FCode.equalsIgnoreCase("ReceiveCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiveCom));
		}
		if (FCode.equalsIgnoreCase("Handler"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Handler));
		}
		if (FCode.equalsIgnoreCase("HandleDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getHandleDate()));
		}
		if (FCode.equalsIgnoreCase("SendNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendNo));
		}
		if (FCode.equalsIgnoreCase("TakeBackDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTakeBackDate()));
		}
		if (FCode.equalsIgnoreCase("TakeBackNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TakeBackNo));
		}
		if (FCode.equalsIgnoreCase("TakeBackOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TakeBackOperator));
		}
		if (FCode.equalsIgnoreCase("TakeBackMakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTakeBackMakeDate()));
		}
		if (FCode.equalsIgnoreCase("TakeBackMakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TakeBackMakeTime));
		}
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StateFlag));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CertifyNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getValidDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SendOutCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ReceiveCom);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Handler);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getHandleDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SendNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTakeBackDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(TakeBackNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(TakeBackOperator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTakeBackMakeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(TakeBackMakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StateFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Operator);
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

		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyCode = FValue.trim();
			}
			else
				CertifyCode = null;
		}
		if (FCode.equalsIgnoreCase("CertifyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyNo = FValue.trim();
			}
			else
				CertifyNo = null;
		}
		if (FCode.equalsIgnoreCase("ValidDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ValidDate = fDate.getDate( FValue );
			}
			else
				ValidDate = null;
		}
		if (FCode.equalsIgnoreCase("SendOutCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendOutCom = FValue.trim();
			}
			else
				SendOutCom = null;
		}
		if (FCode.equalsIgnoreCase("ReceiveCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiveCom = FValue.trim();
			}
			else
				ReceiveCom = null;
		}
		if (FCode.equalsIgnoreCase("Handler"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Handler = FValue.trim();
			}
			else
				Handler = null;
		}
		if (FCode.equalsIgnoreCase("HandleDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				HandleDate = fDate.getDate( FValue );
			}
			else
				HandleDate = null;
		}
		if (FCode.equalsIgnoreCase("SendNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendNo = FValue.trim();
			}
			else
				SendNo = null;
		}
		if (FCode.equalsIgnoreCase("TakeBackDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TakeBackDate = fDate.getDate( FValue );
			}
			else
				TakeBackDate = null;
		}
		if (FCode.equalsIgnoreCase("TakeBackNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TakeBackNo = FValue.trim();
			}
			else
				TakeBackNo = null;
		}
		if (FCode.equalsIgnoreCase("TakeBackOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TakeBackOperator = FValue.trim();
			}
			else
				TakeBackOperator = null;
		}
		if (FCode.equalsIgnoreCase("TakeBackMakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TakeBackMakeDate = fDate.getDate( FValue );
			}
			else
				TakeBackMakeDate = null;
		}
		if (FCode.equalsIgnoreCase("TakeBackMakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TakeBackMakeTime = FValue.trim();
			}
			else
				TakeBackMakeTime = null;
		}
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StateFlag = FValue.trim();
			}
			else
				StateFlag = null;
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
		LZSysCertifySchema other = (LZSysCertifySchema)otherObject;
		return
			CertifyCode.equals(other.getCertifyCode())
			&& CertifyNo.equals(other.getCertifyNo())
			&& fDate.getString(ValidDate).equals(other.getValidDate())
			&& SendOutCom.equals(other.getSendOutCom())
			&& ReceiveCom.equals(other.getReceiveCom())
			&& Handler.equals(other.getHandler())
			&& fDate.getString(HandleDate).equals(other.getHandleDate())
			&& SendNo.equals(other.getSendNo())
			&& fDate.getString(TakeBackDate).equals(other.getTakeBackDate())
			&& TakeBackNo.equals(other.getTakeBackNo())
			&& TakeBackOperator.equals(other.getTakeBackOperator())
			&& fDate.getString(TakeBackMakeDate).equals(other.getTakeBackMakeDate())
			&& TakeBackMakeTime.equals(other.getTakeBackMakeTime())
			&& StateFlag.equals(other.getStateFlag())
			&& State.equals(other.getState())
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
		if( strFieldName.equals("CertifyCode") ) {
			return 0;
		}
		if( strFieldName.equals("CertifyNo") ) {
			return 1;
		}
		if( strFieldName.equals("ValidDate") ) {
			return 2;
		}
		if( strFieldName.equals("SendOutCom") ) {
			return 3;
		}
		if( strFieldName.equals("ReceiveCom") ) {
			return 4;
		}
		if( strFieldName.equals("Handler") ) {
			return 5;
		}
		if( strFieldName.equals("HandleDate") ) {
			return 6;
		}
		if( strFieldName.equals("SendNo") ) {
			return 7;
		}
		if( strFieldName.equals("TakeBackDate") ) {
			return 8;
		}
		if( strFieldName.equals("TakeBackNo") ) {
			return 9;
		}
		if( strFieldName.equals("TakeBackOperator") ) {
			return 10;
		}
		if( strFieldName.equals("TakeBackMakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("TakeBackMakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("StateFlag") ) {
			return 13;
		}
		if( strFieldName.equals("State") ) {
			return 14;
		}
		if( strFieldName.equals("Operator") ) {
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
				strFieldName = "CertifyCode";
				break;
			case 1:
				strFieldName = "CertifyNo";
				break;
			case 2:
				strFieldName = "ValidDate";
				break;
			case 3:
				strFieldName = "SendOutCom";
				break;
			case 4:
				strFieldName = "ReceiveCom";
				break;
			case 5:
				strFieldName = "Handler";
				break;
			case 6:
				strFieldName = "HandleDate";
				break;
			case 7:
				strFieldName = "SendNo";
				break;
			case 8:
				strFieldName = "TakeBackDate";
				break;
			case 9:
				strFieldName = "TakeBackNo";
				break;
			case 10:
				strFieldName = "TakeBackOperator";
				break;
			case 11:
				strFieldName = "TakeBackMakeDate";
				break;
			case 12:
				strFieldName = "TakeBackMakeTime";
				break;
			case 13:
				strFieldName = "StateFlag";
				break;
			case 14:
				strFieldName = "State";
				break;
			case 15:
				strFieldName = "Operator";
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
		if( strFieldName.equals("CertifyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValidDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SendOutCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiveCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Handler") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HandleDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SendNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TakeBackDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TakeBackNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TakeBackOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TakeBackMakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TakeBackMakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
