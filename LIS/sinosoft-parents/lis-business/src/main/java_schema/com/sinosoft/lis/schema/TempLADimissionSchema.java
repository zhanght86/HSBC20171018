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
import com.sinosoft.lis.db.TempLADimissionDB;

/*
 * <p>ClassName: TempLADimissionSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class TempLADimissionSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(TempLADimissionSchema.class);

	// @Field
	/** 代理人编码 */
	private String AgentCode;
	/** 离职次数 */
	private int DepartTimes;
	/** 离职时间 */
	private Date DepartDate;
	/** 离职原因 */
	private String DepartRsn;
	/** 《保险代理合同书》回收标志 */
	private String ContractFlag;
	/** 工作证回收标志 */
	private String WorkFlag;
	/** 展业证回收标志 */
	private String PbcFlag;
	/** 保险费暂收据回收标志 */
	private String ReceiptFlag;
	/** 丢失标志 */
	private String LostFlag;
	/** 结清标志 */
	private String CheckFlag;
	/** 停止佣金发放标志 */
	private String WageFlag;
	/** 业务人员转任内勤人员标志 */
	private String TurnFlag;
	/** 备注 */
	private String Noti;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 展业类型 */
	private String BranchType;
	/** 展业机构外部编码 */
	private String BranchAttr;
	/** 审核标志 */
	private String ConFFlag;
	/** 申请次数 */
	private int ApplyTimes;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public TempLADimissionSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "AgentCode";
		pk[1] = "DepartTimes";

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
		TempLADimissionSchema cloned = (TempLADimissionSchema)super.clone();
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
	* 通过该字段对应银行专有属性表(可能是银行信息表）
	*/
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public int getDepartTimes()
	{
		return DepartTimes;
	}
	public void setDepartTimes(int aDepartTimes)
	{
		DepartTimes = aDepartTimes;
	}
	public void setDepartTimes(String aDepartTimes)
	{
		if (aDepartTimes != null && !aDepartTimes.equals(""))
		{
			Integer tInteger = new Integer(aDepartTimes);
			int i = tInteger.intValue();
			DepartTimes = i;
		}
	}

	public String getDepartDate()
	{
		if( DepartDate != null )
			return fDate.getString(DepartDate);
		else
			return null;
	}
	public void setDepartDate(Date aDepartDate)
	{
		DepartDate = aDepartDate;
	}
	public void setDepartDate(String aDepartDate)
	{
		if (aDepartDate != null && !aDepartDate.equals("") )
		{
			DepartDate = fDate.getDate( aDepartDate );
		}
		else
			DepartDate = null;
	}

	/**
	* 离职原因(0-其它、1-违反公司《业务人员品质管理办法》)
	*/
	public String getDepartRsn()
	{
		return DepartRsn;
	}
	public void setDepartRsn(String aDepartRsn)
	{
		DepartRsn = aDepartRsn;
	}
	public String getContractFlag()
	{
		return ContractFlag;
	}
	public void setContractFlag(String aContractFlag)
	{
		ContractFlag = aContractFlag;
	}
	public String getWorkFlag()
	{
		return WorkFlag;
	}
	public void setWorkFlag(String aWorkFlag)
	{
		WorkFlag = aWorkFlag;
	}
	public String getPbcFlag()
	{
		return PbcFlag;
	}
	public void setPbcFlag(String aPbcFlag)
	{
		PbcFlag = aPbcFlag;
	}
	public String getReceiptFlag()
	{
		return ReceiptFlag;
	}
	public void setReceiptFlag(String aReceiptFlag)
	{
		ReceiptFlag = aReceiptFlag;
	}
	public String getLostFlag()
	{
		return LostFlag;
	}
	public void setLostFlag(String aLostFlag)
	{
		LostFlag = aLostFlag;
	}
	public String getCheckFlag()
	{
		return CheckFlag;
	}
	public void setCheckFlag(String aCheckFlag)
	{
		CheckFlag = aCheckFlag;
	}
	public String getWageFlag()
	{
		return WageFlag;
	}
	public void setWageFlag(String aWageFlag)
	{
		WageFlag = aWageFlag;
	}
	public String getTurnFlag()
	{
		return TurnFlag;
	}
	public void setTurnFlag(String aTurnFlag)
	{
		TurnFlag = aTurnFlag;
	}
	public String getNoti()
	{
		return Noti;
	}
	public void setNoti(String aNoti)
	{
		Noti = aNoti;
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
	* 展业类型(1-个人营销，2-团险，3－银行保险，9－其他)
	*/
	public String getBranchType()
	{
		return BranchType;
	}
	public void setBranchType(String aBranchType)
	{
		BranchType = aBranchType;
	}
	public String getBranchAttr()
	{
		return BranchAttr;
	}
	public void setBranchAttr(String aBranchAttr)
	{
		BranchAttr = aBranchAttr;
	}
	public String getConFFlag()
	{
		return ConFFlag;
	}
	public void setConFFlag(String aConFFlag)
	{
		ConFFlag = aConFFlag;
	}
	public int getApplyTimes()
	{
		return ApplyTimes;
	}
	public void setApplyTimes(int aApplyTimes)
	{
		ApplyTimes = aApplyTimes;
	}
	public void setApplyTimes(String aApplyTimes)
	{
		if (aApplyTimes != null && !aApplyTimes.equals(""))
		{
			Integer tInteger = new Integer(aApplyTimes);
			int i = tInteger.intValue();
			ApplyTimes = i;
		}
	}


	/**
	* 使用另外一个 TempLADimissionSchema 对象给 Schema 赋值
	* @param: aTempLADimissionSchema TempLADimissionSchema
	**/
	public void setSchema(TempLADimissionSchema aTempLADimissionSchema)
	{
		this.AgentCode = aTempLADimissionSchema.getAgentCode();
		this.DepartTimes = aTempLADimissionSchema.getDepartTimes();
		this.DepartDate = fDate.getDate( aTempLADimissionSchema.getDepartDate());
		this.DepartRsn = aTempLADimissionSchema.getDepartRsn();
		this.ContractFlag = aTempLADimissionSchema.getContractFlag();
		this.WorkFlag = aTempLADimissionSchema.getWorkFlag();
		this.PbcFlag = aTempLADimissionSchema.getPbcFlag();
		this.ReceiptFlag = aTempLADimissionSchema.getReceiptFlag();
		this.LostFlag = aTempLADimissionSchema.getLostFlag();
		this.CheckFlag = aTempLADimissionSchema.getCheckFlag();
		this.WageFlag = aTempLADimissionSchema.getWageFlag();
		this.TurnFlag = aTempLADimissionSchema.getTurnFlag();
		this.Noti = aTempLADimissionSchema.getNoti();
		this.Operator = aTempLADimissionSchema.getOperator();
		this.MakeDate = fDate.getDate( aTempLADimissionSchema.getMakeDate());
		this.MakeTime = aTempLADimissionSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aTempLADimissionSchema.getModifyDate());
		this.ModifyTime = aTempLADimissionSchema.getModifyTime();
		this.BranchType = aTempLADimissionSchema.getBranchType();
		this.BranchAttr = aTempLADimissionSchema.getBranchAttr();
		this.ConFFlag = aTempLADimissionSchema.getConFFlag();
		this.ApplyTimes = aTempLADimissionSchema.getApplyTimes();
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
			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			this.DepartTimes = rs.getInt("DepartTimes");
			this.DepartDate = rs.getDate("DepartDate");
			if( rs.getString("DepartRsn") == null )
				this.DepartRsn = null;
			else
				this.DepartRsn = rs.getString("DepartRsn").trim();

			if( rs.getString("ContractFlag") == null )
				this.ContractFlag = null;
			else
				this.ContractFlag = rs.getString("ContractFlag").trim();

			if( rs.getString("WorkFlag") == null )
				this.WorkFlag = null;
			else
				this.WorkFlag = rs.getString("WorkFlag").trim();

			if( rs.getString("PbcFlag") == null )
				this.PbcFlag = null;
			else
				this.PbcFlag = rs.getString("PbcFlag").trim();

			if( rs.getString("ReceiptFlag") == null )
				this.ReceiptFlag = null;
			else
				this.ReceiptFlag = rs.getString("ReceiptFlag").trim();

			if( rs.getString("LostFlag") == null )
				this.LostFlag = null;
			else
				this.LostFlag = rs.getString("LostFlag").trim();

			if( rs.getString("CheckFlag") == null )
				this.CheckFlag = null;
			else
				this.CheckFlag = rs.getString("CheckFlag").trim();

			if( rs.getString("WageFlag") == null )
				this.WageFlag = null;
			else
				this.WageFlag = rs.getString("WageFlag").trim();

			if( rs.getString("TurnFlag") == null )
				this.TurnFlag = null;
			else
				this.TurnFlag = rs.getString("TurnFlag").trim();

			if( rs.getString("Noti") == null )
				this.Noti = null;
			else
				this.Noti = rs.getString("Noti").trim();

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

			if( rs.getString("BranchType") == null )
				this.BranchType = null;
			else
				this.BranchType = rs.getString("BranchType").trim();

			if( rs.getString("BranchAttr") == null )
				this.BranchAttr = null;
			else
				this.BranchAttr = rs.getString("BranchAttr").trim();

			if( rs.getString("ConFFlag") == null )
				this.ConFFlag = null;
			else
				this.ConFFlag = rs.getString("ConFFlag").trim();

			this.ApplyTimes = rs.getInt("ApplyTimes");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的TempLADimission表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TempLADimissionSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public TempLADimissionSchema getSchema()
	{
		TempLADimissionSchema aTempLADimissionSchema = new TempLADimissionSchema();
		aTempLADimissionSchema.setSchema(this);
		return aTempLADimissionSchema;
	}

	public TempLADimissionDB getDB()
	{
		TempLADimissionDB aDBOper = new TempLADimissionDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpTempLADimission描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DepartTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DepartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DepartRsn)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContractFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PbcFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiptFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LostFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WageFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TurnFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Noti)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchAttr)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConFFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ApplyTimes));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpTempLADimission>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DepartTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			DepartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			DepartRsn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ContractFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			WorkFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PbcFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ReceiptFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			LostFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CheckFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			WageFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			TurnFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Noti = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			BranchAttr = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ConFFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ApplyTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TempLADimissionSchema";
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
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("DepartTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DepartTimes));
		}
		if (FCode.equalsIgnoreCase("DepartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDepartDate()));
		}
		if (FCode.equalsIgnoreCase("DepartRsn"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DepartRsn));
		}
		if (FCode.equalsIgnoreCase("ContractFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContractFlag));
		}
		if (FCode.equalsIgnoreCase("WorkFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkFlag));
		}
		if (FCode.equalsIgnoreCase("PbcFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PbcFlag));
		}
		if (FCode.equalsIgnoreCase("ReceiptFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiptFlag));
		}
		if (FCode.equalsIgnoreCase("LostFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LostFlag));
		}
		if (FCode.equalsIgnoreCase("CheckFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckFlag));
		}
		if (FCode.equalsIgnoreCase("WageFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WageFlag));
		}
		if (FCode.equalsIgnoreCase("TurnFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TurnFlag));
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Noti));
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
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchType));
		}
		if (FCode.equalsIgnoreCase("BranchAttr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchAttr));
		}
		if (FCode.equalsIgnoreCase("ConFFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConFFlag));
		}
		if (FCode.equalsIgnoreCase("ApplyTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyTimes));
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
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 1:
				strFieldValue = String.valueOf(DepartTimes);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDepartDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DepartRsn);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ContractFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(WorkFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PbcFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ReceiptFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(LostFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CheckFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(WageFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(TurnFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Noti);
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
				strFieldValue = StrTool.GBKToUnicode(BranchType);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(BranchAttr);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ConFFlag);
				break;
			case 21:
				strFieldValue = String.valueOf(ApplyTimes);
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

		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("DepartTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				DepartTimes = i;
			}
		}
		if (FCode.equalsIgnoreCase("DepartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DepartDate = fDate.getDate( FValue );
			}
			else
				DepartDate = null;
		}
		if (FCode.equalsIgnoreCase("DepartRsn"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DepartRsn = FValue.trim();
			}
			else
				DepartRsn = null;
		}
		if (FCode.equalsIgnoreCase("ContractFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContractFlag = FValue.trim();
			}
			else
				ContractFlag = null;
		}
		if (FCode.equalsIgnoreCase("WorkFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkFlag = FValue.trim();
			}
			else
				WorkFlag = null;
		}
		if (FCode.equalsIgnoreCase("PbcFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PbcFlag = FValue.trim();
			}
			else
				PbcFlag = null;
		}
		if (FCode.equalsIgnoreCase("ReceiptFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiptFlag = FValue.trim();
			}
			else
				ReceiptFlag = null;
		}
		if (FCode.equalsIgnoreCase("LostFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LostFlag = FValue.trim();
			}
			else
				LostFlag = null;
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
		if (FCode.equalsIgnoreCase("WageFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WageFlag = FValue.trim();
			}
			else
				WageFlag = null;
		}
		if (FCode.equalsIgnoreCase("TurnFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TurnFlag = FValue.trim();
			}
			else
				TurnFlag = null;
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Noti = FValue.trim();
			}
			else
				Noti = null;
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
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchType = FValue.trim();
			}
			else
				BranchType = null;
		}
		if (FCode.equalsIgnoreCase("BranchAttr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchAttr = FValue.trim();
			}
			else
				BranchAttr = null;
		}
		if (FCode.equalsIgnoreCase("ConFFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConFFlag = FValue.trim();
			}
			else
				ConFFlag = null;
		}
		if (FCode.equalsIgnoreCase("ApplyTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ApplyTimes = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		TempLADimissionSchema other = (TempLADimissionSchema)otherObject;
		return
			AgentCode.equals(other.getAgentCode())
			&& DepartTimes == other.getDepartTimes()
			&& fDate.getString(DepartDate).equals(other.getDepartDate())
			&& DepartRsn.equals(other.getDepartRsn())
			&& ContractFlag.equals(other.getContractFlag())
			&& WorkFlag.equals(other.getWorkFlag())
			&& PbcFlag.equals(other.getPbcFlag())
			&& ReceiptFlag.equals(other.getReceiptFlag())
			&& LostFlag.equals(other.getLostFlag())
			&& CheckFlag.equals(other.getCheckFlag())
			&& WageFlag.equals(other.getWageFlag())
			&& TurnFlag.equals(other.getTurnFlag())
			&& Noti.equals(other.getNoti())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BranchType.equals(other.getBranchType())
			&& BranchAttr.equals(other.getBranchAttr())
			&& ConFFlag.equals(other.getConFFlag())
			&& ApplyTimes == other.getApplyTimes();
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
		if( strFieldName.equals("AgentCode") ) {
			return 0;
		}
		if( strFieldName.equals("DepartTimes") ) {
			return 1;
		}
		if( strFieldName.equals("DepartDate") ) {
			return 2;
		}
		if( strFieldName.equals("DepartRsn") ) {
			return 3;
		}
		if( strFieldName.equals("ContractFlag") ) {
			return 4;
		}
		if( strFieldName.equals("WorkFlag") ) {
			return 5;
		}
		if( strFieldName.equals("PbcFlag") ) {
			return 6;
		}
		if( strFieldName.equals("ReceiptFlag") ) {
			return 7;
		}
		if( strFieldName.equals("LostFlag") ) {
			return 8;
		}
		if( strFieldName.equals("CheckFlag") ) {
			return 9;
		}
		if( strFieldName.equals("WageFlag") ) {
			return 10;
		}
		if( strFieldName.equals("TurnFlag") ) {
			return 11;
		}
		if( strFieldName.equals("Noti") ) {
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
		if( strFieldName.equals("BranchType") ) {
			return 18;
		}
		if( strFieldName.equals("BranchAttr") ) {
			return 19;
		}
		if( strFieldName.equals("ConFFlag") ) {
			return 20;
		}
		if( strFieldName.equals("ApplyTimes") ) {
			return 21;
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
				strFieldName = "AgentCode";
				break;
			case 1:
				strFieldName = "DepartTimes";
				break;
			case 2:
				strFieldName = "DepartDate";
				break;
			case 3:
				strFieldName = "DepartRsn";
				break;
			case 4:
				strFieldName = "ContractFlag";
				break;
			case 5:
				strFieldName = "WorkFlag";
				break;
			case 6:
				strFieldName = "PbcFlag";
				break;
			case 7:
				strFieldName = "ReceiptFlag";
				break;
			case 8:
				strFieldName = "LostFlag";
				break;
			case 9:
				strFieldName = "CheckFlag";
				break;
			case 10:
				strFieldName = "WageFlag";
				break;
			case 11:
				strFieldName = "TurnFlag";
				break;
			case 12:
				strFieldName = "Noti";
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
				strFieldName = "BranchType";
				break;
			case 19:
				strFieldName = "BranchAttr";
				break;
			case 20:
				strFieldName = "ConFFlag";
				break;
			case 21:
				strFieldName = "ApplyTimes";
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
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DepartTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DepartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DepartRsn") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContractFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WorkFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PbcFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiptFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LostFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WageFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TurnFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Noti") ) {
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
		if( strFieldName.equals("BranchType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchAttr") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConFFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyTimes") ) {
			return Schema.TYPE_INT;
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
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
