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
import com.sinosoft.lis.db.LOPolAfterDealDB;

/*
 * <p>ClassName: LOPolAfterDealSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 投连改造
 */
public class LOPolAfterDealSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOPolAfterDealSchema.class);

	// @Field
	/** 账户批单号码 */
	private String AccAlterNo;
	/** 账户批单号码类型 */
	private String AccAlterType;
	/** 业务批改类型 */
	private String BusyType;
	/** 管理机构 */
	private String ManageCom;
	/** 业务生效日期 */
	private Date ConfDate;
	/** 保单险种号码 */
	private String PolNo;
	/** 险种编码 */
	private String RiskCode;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 合同号码 */
	private String ContNo;
	/** 处理状态 */
	private String State;
	/** 后续处理日期 */
	private Date DealDate;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOPolAfterDealSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "AccAlterNo";
		pk[1] = "AccAlterType";
		pk[2] = "BusyType";

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
		LOPolAfterDealSchema cloned = (LOPolAfterDealSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAccAlterNo()
	{
		return AccAlterNo;
	}
	public void setAccAlterNo(String aAccAlterNo)
	{
		if(aAccAlterNo!=null && aAccAlterNo.length()>20)
			throw new IllegalArgumentException("账户批单号码AccAlterNo值"+aAccAlterNo+"的长度"+aAccAlterNo.length()+"大于最大值20");
		AccAlterNo = aAccAlterNo;
	}
	/**
	* 1－新契约<p>
	* 2－续期<p>
	* 3－保全<p>
	* 4－理赔<p>
	* 5－领取
	*/
	public String getAccAlterType()
	{
		return AccAlterType;
	}
	public void setAccAlterType(String aAccAlterType)
	{
		if(aAccAlterType!=null && aAccAlterType.length()>1)
			throw new IllegalArgumentException("账户批单号码类型AccAlterType值"+aAccAlterType+"的长度"+aAccAlterType.length()+"大于最大值1");
		AccAlterType = aAccAlterType;
	}
	/**
	* 记录具体的业务批改类型（保全项目代码）<p>
	* 如果不是保全后续处理，则同AccAlterType
	*/
	public String getBusyType()
	{
		return BusyType;
	}
	public void setBusyType(String aBusyType)
	{
		if(aBusyType!=null && aBusyType.length()>10)
			throw new IllegalArgumentException("业务批改类型BusyType值"+aBusyType+"的长度"+aBusyType.length()+"大于最大值10");
		BusyType = aBusyType;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>10)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值10");
		ManageCom = aManageCom;
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

	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		if(aPolNo!=null && aPolNo.length()>20)
			throw new IllegalArgumentException("保单险种号码PolNo值"+aPolNo+"的长度"+aPolNo.length()+"大于最大值20");
		PolNo = aPolNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>10)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值10");
		RiskCode = aRiskCode;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("集体合同号码GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		if(aGrpPolNo!=null && aGrpPolNo.length()>20)
			throw new IllegalArgumentException("集体保单险种号码GrpPolNo值"+aGrpPolNo+"的长度"+aGrpPolNo.length()+"大于最大值20");
		GrpPolNo = aGrpPolNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("合同号码ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	/**
	* 0-待处理<p>
	* 1-处理中<p>
	* 2-处理完毕
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>2)
			throw new IllegalArgumentException("处理状态State值"+aState+"的长度"+aState.length()+"大于最大值2");
		State = aState;
	}
	public String getDealDate()
	{
		if( DealDate != null )
			return fDate.getString(DealDate);
		else
			return null;
	}
	public void setDealDate(Date aDealDate)
	{
		DealDate = aDealDate;
	}
	public void setDealDate(String aDealDate)
	{
		if (aDealDate != null && !aDealDate.equals("") )
		{
			DealDate = fDate.getDate( aDealDate );
		}
		else
			DealDate = null;
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LOPolAfterDealSchema 对象给 Schema 赋值
	* @param: aLOPolAfterDealSchema LOPolAfterDealSchema
	**/
	public void setSchema(LOPolAfterDealSchema aLOPolAfterDealSchema)
	{
		this.AccAlterNo = aLOPolAfterDealSchema.getAccAlterNo();
		this.AccAlterType = aLOPolAfterDealSchema.getAccAlterType();
		this.BusyType = aLOPolAfterDealSchema.getBusyType();
		this.ManageCom = aLOPolAfterDealSchema.getManageCom();
		this.ConfDate = fDate.getDate( aLOPolAfterDealSchema.getConfDate());
		this.PolNo = aLOPolAfterDealSchema.getPolNo();
		this.RiskCode = aLOPolAfterDealSchema.getRiskCode();
		this.GrpContNo = aLOPolAfterDealSchema.getGrpContNo();
		this.GrpPolNo = aLOPolAfterDealSchema.getGrpPolNo();
		this.ContNo = aLOPolAfterDealSchema.getContNo();
		this.State = aLOPolAfterDealSchema.getState();
		this.DealDate = fDate.getDate( aLOPolAfterDealSchema.getDealDate());
		this.MakeDate = fDate.getDate( aLOPolAfterDealSchema.getMakeDate());
		this.MakeTime = aLOPolAfterDealSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLOPolAfterDealSchema.getModifyDate());
		this.ModifyTime = aLOPolAfterDealSchema.getModifyTime();
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
			if( rs.getString("AccAlterNo") == null )
				this.AccAlterNo = null;
			else
				this.AccAlterNo = rs.getString("AccAlterNo").trim();

			if( rs.getString("AccAlterType") == null )
				this.AccAlterType = null;
			else
				this.AccAlterType = rs.getString("AccAlterType").trim();

			if( rs.getString("BusyType") == null )
				this.BusyType = null;
			else
				this.BusyType = rs.getString("BusyType").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.ConfDate = rs.getDate("ConfDate");
			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			this.DealDate = rs.getDate("DealDate");
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
			logger.debug("数据库中的LOPolAfterDeal表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOPolAfterDealSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOPolAfterDealSchema getSchema()
	{
		LOPolAfterDealSchema aLOPolAfterDealSchema = new LOPolAfterDealSchema();
		aLOPolAfterDealSchema.setSchema(this);
		return aLOPolAfterDealSchema;
	}

	public LOPolAfterDealDB getDB()
	{
		LOPolAfterDealDB aDBOper = new LOPolAfterDealDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOPolAfterDeal描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AccAlterNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccAlterType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusyType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DealDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOPolAfterDeal>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AccAlterNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AccAlterType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BusyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			DealDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOPolAfterDealSchema";
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
		if (FCode.equalsIgnoreCase("AccAlterNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccAlterNo));
		}
		if (FCode.equalsIgnoreCase("AccAlterType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccAlterType));
		}
		if (FCode.equalsIgnoreCase("BusyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusyType));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("DealDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDealDate()));
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
				strFieldValue = StrTool.GBKToUnicode(AccAlterNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AccAlterType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BusyType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDealDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 15:
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

		if (FCode.equalsIgnoreCase("AccAlterNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccAlterNo = FValue.trim();
			}
			else
				AccAlterNo = null;
		}
		if (FCode.equalsIgnoreCase("AccAlterType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccAlterType = FValue.trim();
			}
			else
				AccAlterType = null;
		}
		if (FCode.equalsIgnoreCase("BusyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusyType = FValue.trim();
			}
			else
				BusyType = null;
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
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfDate = fDate.getDate( FValue );
			}
			else
				ConfDate = null;
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equalsIgnoreCase("DealDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DealDate = fDate.getDate( FValue );
			}
			else
				DealDate = null;
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
		LOPolAfterDealSchema other = (LOPolAfterDealSchema)otherObject;
		return
			AccAlterNo.equals(other.getAccAlterNo())
			&& AccAlterType.equals(other.getAccAlterType())
			&& BusyType.equals(other.getBusyType())
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& PolNo.equals(other.getPolNo())
			&& RiskCode.equals(other.getRiskCode())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& State.equals(other.getState())
			&& fDate.getString(DealDate).equals(other.getDealDate())
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
		if( strFieldName.equals("AccAlterNo") ) {
			return 0;
		}
		if( strFieldName.equals("AccAlterType") ) {
			return 1;
		}
		if( strFieldName.equals("BusyType") ) {
			return 2;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 3;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 4;
		}
		if( strFieldName.equals("PolNo") ) {
			return 5;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 6;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 7;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 8;
		}
		if( strFieldName.equals("ContNo") ) {
			return 9;
		}
		if( strFieldName.equals("State") ) {
			return 10;
		}
		if( strFieldName.equals("DealDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 12;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "AccAlterNo";
				break;
			case 1:
				strFieldName = "AccAlterType";
				break;
			case 2:
				strFieldName = "BusyType";
				break;
			case 3:
				strFieldName = "ManageCom";
				break;
			case 4:
				strFieldName = "ConfDate";
				break;
			case 5:
				strFieldName = "PolNo";
				break;
			case 6:
				strFieldName = "RiskCode";
				break;
			case 7:
				strFieldName = "GrpContNo";
				break;
			case 8:
				strFieldName = "GrpPolNo";
				break;
			case 9:
				strFieldName = "ContNo";
				break;
			case 10:
				strFieldName = "State";
				break;
			case 11:
				strFieldName = "DealDate";
				break;
			case 12:
				strFieldName = "MakeDate";
				break;
			case 13:
				strFieldName = "MakeTime";
				break;
			case 14:
				strFieldName = "ModifyDate";
				break;
			case 15:
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
		if( strFieldName.equals("AccAlterNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccAlterType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusyType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
