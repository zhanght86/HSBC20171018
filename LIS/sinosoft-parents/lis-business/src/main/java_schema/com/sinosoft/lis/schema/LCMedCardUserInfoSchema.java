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
import com.sinosoft.lis.db.LCMedCardUserInfoDB;

/*
 * <p>ClassName: LCMedCardUserInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LCMedCardUserInfoSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCMedCardUserInfoSchema.class);
	// @Field
	/** 医疗卡号 */
	private String MedCardNo;
	/** 客户号 */
	private String InsuredNo;
	/** 保单号 */
	private String GrpContNo;
	/** 姓名 */
	private String Name;
	/** 密码 */
	private String Password;
	/** 状态 */
	private String CardState;
	/** 发卡日期 */
	private Date ProvideDate;
	/** 发卡时间 */
	private String ProvideTime;
	/** 丢卡日期 */
	private Date LostDate;
	/** 丢卡时间 */
	private String LostTime;
	/** 个人合同号 */
	private String ContNo;
	/** 补卡批次 */
	private String ProvideNo;
	/** 备注 */
	private String Memo;
	/** 个人积分 */
	private double Bonus;
	/** 管理机构 */
	private String ManageCom;
	/** 操作员 */
	private String Operator;

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCMedCardUserInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "MedCardNo";

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
		LCMedCardUserInfoSchema cloned = (LCMedCardUserInfoSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getMedCardNo()
	{
		return MedCardNo;
	}
	public void setMedCardNo(String aMedCardNo)
	{
		if(aMedCardNo!=null && aMedCardNo.length()>40)
			throw new IllegalArgumentException("医疗卡号MedCardNo值"+aMedCardNo+"的长度"+aMedCardNo.length()+"大于最大值40");
		MedCardNo = aMedCardNo;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>40)
			throw new IllegalArgumentException("客户号InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值40");
		InsuredNo = aInsuredNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>40)
			throw new IllegalArgumentException("保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值40");
		GrpContNo = aGrpContNo;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		if(aName!=null && aName.length()>40)
			throw new IllegalArgumentException("姓名Name值"+aName+"的长度"+aName.length()+"大于最大值40");
		Name = aName;
	}
	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String aPassword)
	{
		if(aPassword!=null && aPassword.length()>40)
			throw new IllegalArgumentException("密码Password值"+aPassword+"的长度"+aPassword.length()+"大于最大值40");
		Password = aPassword;
	}
	/**
	* 0--失效 1--有效
	*/
	public String getCardState()
	{
		return CardState;
	}
	public void setCardState(String aCardState)
	{
		if(aCardState!=null && aCardState.length()>1)
			throw new IllegalArgumentException("状态CardState值"+aCardState+"的长度"+aCardState.length()+"大于最大值1");
		CardState = aCardState;
	}
	public String getProvideDate()
	{
		if( ProvideDate != null )
			return fDate.getString(ProvideDate);
		else
			return null;
	}
	public void setProvideDate(Date aProvideDate)
	{
		ProvideDate = aProvideDate;
	}
	public void setProvideDate(String aProvideDate)
	{
		if (aProvideDate != null && !aProvideDate.equals("") )
		{
			ProvideDate = fDate.getDate( aProvideDate );
		}
		else
			ProvideDate = null;
	}

	public String getProvideTime()
	{
		return ProvideTime;
	}
	public void setProvideTime(String aProvideTime)
	{
		if(aProvideTime!=null && aProvideTime.length()>20)
			throw new IllegalArgumentException("发卡时间ProvideTime值"+aProvideTime+"的长度"+aProvideTime.length()+"大于最大值20");
		ProvideTime = aProvideTime;
	}
	public String getLostDate()
	{
		if( LostDate != null )
			return fDate.getString(LostDate);
		else
			return null;
	}
	public void setLostDate(Date aLostDate)
	{
		LostDate = aLostDate;
	}
	public void setLostDate(String aLostDate)
	{
		if (aLostDate != null && !aLostDate.equals("") )
		{
			LostDate = fDate.getDate( aLostDate );
		}
		else
			LostDate = null;
	}

	public String getLostTime()
	{
		return LostTime;
	}
	public void setLostTime(String aLostTime)
	{
		if(aLostTime!=null && aLostTime.length()>20)
			throw new IllegalArgumentException("丢卡时间LostTime值"+aLostTime+"的长度"+aLostTime.length()+"大于最大值20");
		LostTime = aLostTime;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("个人合同号ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getProvideNo()
	{
		return ProvideNo;
	}
	public void setProvideNo(String aProvideNo)
	{
		if(aProvideNo!=null && aProvideNo.length()>20)
			throw new IllegalArgumentException("补卡批次ProvideNo值"+aProvideNo+"的长度"+aProvideNo.length()+"大于最大值20");
		ProvideNo = aProvideNo;
	}
	public String getMemo()
	{
		return Memo;
	}
	public void setMemo(String aMemo)
	{
		if(aMemo!=null && aMemo.length()>100)
			throw new IllegalArgumentException("备注Memo值"+aMemo+"的长度"+aMemo.length()+"大于最大值100");
		Memo = aMemo;
	}
	public double getBonus()
	{
		return Bonus;
	}
	public void setBonus(double aBonus)
	{
		Bonus = aBonus;
	}
	public void setBonus(String aBonus)
	{
		if (aBonus != null && !aBonus.equals(""))
		{
			Double tDouble = new Double(aBonus);
			double d = tDouble.doubleValue();
			Bonus = d;
		}
	}

	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>20)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值20");
		Operator = aOperator;
	}

	/**
	* 使用另外一个 LCMedCardUserInfoSchema 对象给 Schema 赋值
	* @param: aLCMedCardUserInfoSchema LCMedCardUserInfoSchema
	**/
	public void setSchema(LCMedCardUserInfoSchema aLCMedCardUserInfoSchema)
	{
		this.MedCardNo = aLCMedCardUserInfoSchema.getMedCardNo();
		this.InsuredNo = aLCMedCardUserInfoSchema.getInsuredNo();
		this.GrpContNo = aLCMedCardUserInfoSchema.getGrpContNo();
		this.Name = aLCMedCardUserInfoSchema.getName();
		this.Password = aLCMedCardUserInfoSchema.getPassword();
		this.CardState = aLCMedCardUserInfoSchema.getCardState();
		this.ProvideDate = fDate.getDate( aLCMedCardUserInfoSchema.getProvideDate());
		this.ProvideTime = aLCMedCardUserInfoSchema.getProvideTime();
		this.LostDate = fDate.getDate( aLCMedCardUserInfoSchema.getLostDate());
		this.LostTime = aLCMedCardUserInfoSchema.getLostTime();
		this.ContNo = aLCMedCardUserInfoSchema.getContNo();
		this.ProvideNo = aLCMedCardUserInfoSchema.getProvideNo();
		this.Memo = aLCMedCardUserInfoSchema.getMemo();
		this.Bonus = aLCMedCardUserInfoSchema.getBonus();
		this.ManageCom = aLCMedCardUserInfoSchema.getManageCom();
		this.Operator = aLCMedCardUserInfoSchema.getOperator();
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
			if( rs.getString("MedCardNo") == null )
				this.MedCardNo = null;
			else
				this.MedCardNo = rs.getString("MedCardNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

			if( rs.getString("CardState") == null )
				this.CardState = null;
			else
				this.CardState = rs.getString("CardState").trim();

			this.ProvideDate = rs.getDate("ProvideDate");
			if( rs.getString("ProvideTime") == null )
				this.ProvideTime = null;
			else
				this.ProvideTime = rs.getString("ProvideTime").trim();

			this.LostDate = rs.getDate("LostDate");
			if( rs.getString("LostTime") == null )
				this.LostTime = null;
			else
				this.LostTime = rs.getString("LostTime").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ProvideNo") == null )
				this.ProvideNo = null;
			else
				this.ProvideNo = rs.getString("ProvideNo").trim();

			if( rs.getString("Memo") == null )
				this.Memo = null;
			else
				this.Memo = rs.getString("Memo").trim();

			this.Bonus = rs.getDouble("Bonus");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCMedCardUserInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCMedCardUserInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCMedCardUserInfoSchema getSchema()
	{
		LCMedCardUserInfoSchema aLCMedCardUserInfoSchema = new LCMedCardUserInfoSchema();
		aLCMedCardUserInfoSchema.setSchema(this);
		return aLCMedCardUserInfoSchema;
	}

	public LCMedCardUserInfoDB getDB()
	{
		LCMedCardUserInfoDB aDBOper = new LCMedCardUserInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCMedCardUserInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(MedCardNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CardState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ProvideDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProvideTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LostDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LostTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProvideNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Memo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Bonus));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCMedCardUserInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			MedCardNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CardState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ProvideDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			ProvideTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			LostDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			LostTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ProvideNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Memo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Bonus = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCMedCardUserInfoSchema";
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
		if (FCode.equalsIgnoreCase("MedCardNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MedCardNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
		}
		if (FCode.equalsIgnoreCase("CardState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CardState));
		}
		if (FCode.equalsIgnoreCase("ProvideDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getProvideDate()));
		}
		if (FCode.equalsIgnoreCase("ProvideTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProvideTime));
		}
		if (FCode.equalsIgnoreCase("LostDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLostDate()));
		}
		if (FCode.equalsIgnoreCase("LostTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LostTime));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("ProvideNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProvideNo));
		}
		if (FCode.equalsIgnoreCase("Memo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Memo));
		}
		if (FCode.equalsIgnoreCase("Bonus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bonus));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
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
				strFieldValue = StrTool.GBKToUnicode(MedCardNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CardState);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getProvideDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ProvideTime);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLostDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(LostTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ProvideNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Memo);
				break;
			case 13:
				strFieldValue = String.valueOf(Bonus);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Operator);
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

		if (FCode.equalsIgnoreCase("MedCardNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MedCardNo = FValue.trim();
			}
			else
				MedCardNo = null;
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Password = FValue.trim();
			}
			else
				Password = null;
		}
		if (FCode.equalsIgnoreCase("CardState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CardState = FValue.trim();
			}
			else
				CardState = null;
		}
		if (FCode.equalsIgnoreCase("ProvideDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ProvideDate = fDate.getDate( FValue );
			}
			else
				ProvideDate = null;
		}
		if (FCode.equalsIgnoreCase("ProvideTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProvideTime = FValue.trim();
			}
			else
				ProvideTime = null;
		}
		if (FCode.equalsIgnoreCase("LostDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LostDate = fDate.getDate( FValue );
			}
			else
				LostDate = null;
		}
		if (FCode.equalsIgnoreCase("LostTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LostTime = FValue.trim();
			}
			else
				LostTime = null;
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
		if (FCode.equalsIgnoreCase("ProvideNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProvideNo = FValue.trim();
			}
			else
				ProvideNo = null;
		}
		if (FCode.equalsIgnoreCase("Memo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Memo = FValue.trim();
			}
			else
				Memo = null;
		}
		if (FCode.equalsIgnoreCase("Bonus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Bonus = d;
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCMedCardUserInfoSchema other = (LCMedCardUserInfoSchema)otherObject;
		return
			MedCardNo.equals(other.getMedCardNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& Name.equals(other.getName())
			&& Password.equals(other.getPassword())
			&& CardState.equals(other.getCardState())
			&& fDate.getString(ProvideDate).equals(other.getProvideDate())
			&& ProvideTime.equals(other.getProvideTime())
			&& fDate.getString(LostDate).equals(other.getLostDate())
			&& LostTime.equals(other.getLostTime())
			&& ContNo.equals(other.getContNo())
			&& ProvideNo.equals(other.getProvideNo())
			&& Memo.equals(other.getMemo())
			&& Bonus == other.getBonus()
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator());
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
		if( strFieldName.equals("MedCardNo") ) {
			return 0;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("Name") ) {
			return 3;
		}
		if( strFieldName.equals("Password") ) {
			return 4;
		}
		if( strFieldName.equals("CardState") ) {
			return 5;
		}
		if( strFieldName.equals("ProvideDate") ) {
			return 6;
		}
		if( strFieldName.equals("ProvideTime") ) {
			return 7;
		}
		if( strFieldName.equals("LostDate") ) {
			return 8;
		}
		if( strFieldName.equals("LostTime") ) {
			return 9;
		}
		if( strFieldName.equals("ContNo") ) {
			return 10;
		}
		if( strFieldName.equals("ProvideNo") ) {
			return 11;
		}
		if( strFieldName.equals("Memo") ) {
			return 12;
		}
		if( strFieldName.equals("Bonus") ) {
			return 13;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 14;
		}
		if( strFieldName.equals("Operator") ) {
			return 15;
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
				strFieldName = "MedCardNo";
				break;
			case 1:
				strFieldName = "InsuredNo";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "Name";
				break;
			case 4:
				strFieldName = "Password";
				break;
			case 5:
				strFieldName = "CardState";
				break;
			case 6:
				strFieldName = "ProvideDate";
				break;
			case 7:
				strFieldName = "ProvideTime";
				break;
			case 8:
				strFieldName = "LostDate";
				break;
			case 9:
				strFieldName = "LostTime";
				break;
			case 10:
				strFieldName = "ContNo";
				break;
			case 11:
				strFieldName = "ProvideNo";
				break;
			case 12:
				strFieldName = "Memo";
				break;
			case 13:
				strFieldName = "Bonus";
				break;
			case 14:
				strFieldName = "ManageCom";
				break;
			case 15:
				strFieldName = "Operator";
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
		if( strFieldName.equals("MedCardNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CardState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProvideDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ProvideTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LostDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LostTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProvideNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Memo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bonus") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
