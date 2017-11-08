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
import com.sinosoft.lis.db.LLHospitalDB;

/*
 * <p>ClassName: LLHospitalSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLHospitalSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLHospitalSchema.class);
	// @Field
	/** 住院序号 */
	private String InHosNo;
	/** 立案号 */
	private String RgtNo;
	/** 分案号 */
	private String CaseNo;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 受理事故号 */
	private String CaseRelaNo;
	/** 出险人名称 */
	private String CustomerName;
	/** 赔付标志 */
	private String ClmFlag;
	/** 病例号码 */
	private String No;
	/** 医院代码 */
	private String HospitalCode;
	/** 医院名称 */
	private String HospitalName;
	/** 医生代码 */
	private String DoctorCode;
	/** 医生名称 */
	private String DoctorName;
	/** 住院日期 */
	private Date InHosDate;
	/** 出院日期 */
	private Date OutHosDate;
	/** 实际住院天数 */
	private int InHosDays;
	/** 住院原因 */
	private String InHosType;
	/** 管理机构 */
	private String MngCom;
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

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLHospitalSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "InHosNo";
		pk[1] = "CaseNo";

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
		LLHospitalSchema cloned = (LLHospitalSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getInHosNo()
	{
		return InHosNo;
	}
	public void setInHosNo(String aInHosNo)
	{
		if(aInHosNo!=null && aInHosNo.length()>20)
			throw new IllegalArgumentException("住院序号InHosNo值"+aInHosNo+"的长度"+aInHosNo.length()+"大于最大值20");
		InHosNo = aInHosNo;
	}
	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		if(aRgtNo!=null && aRgtNo.length()>20)
			throw new IllegalArgumentException("立案号RgtNo值"+aRgtNo+"的长度"+aRgtNo.length()+"大于最大值20");
		RgtNo = aRgtNo;
	}
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		if(aCaseNo!=null && aCaseNo.length()>20)
			throw new IllegalArgumentException("分案号CaseNo值"+aCaseNo+"的长度"+aCaseNo.length()+"大于最大值20");
		CaseNo = aCaseNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>20)
			throw new IllegalArgumentException("出险人客户号CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值20");
		CustomerNo = aCustomerNo;
	}
	/**
	* 受理事故号
	*/
	public String getCaseRelaNo()
	{
		return CaseRelaNo;
	}
	public void setCaseRelaNo(String aCaseRelaNo)
	{
		if(aCaseRelaNo!=null && aCaseRelaNo.length()>20)
			throw new IllegalArgumentException("受理事故号CaseRelaNo值"+aCaseRelaNo+"的长度"+aCaseRelaNo.length()+"大于最大值20");
		CaseRelaNo = aCaseRelaNo;
	}
	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String aCustomerName)
	{
		if(aCustomerName!=null && aCustomerName.length()>20)
			throw new IllegalArgumentException("出险人名称CustomerName值"+aCustomerName+"的长度"+aCustomerName.length()+"大于最大值20");
		CustomerName = aCustomerName;
	}
	/**
	* 0-无效 1-有效
	*/
	public String getClmFlag()
	{
		return ClmFlag;
	}
	public void setClmFlag(String aClmFlag)
	{
		if(aClmFlag!=null && aClmFlag.length()>1)
			throw new IllegalArgumentException("赔付标志ClmFlag值"+aClmFlag+"的长度"+aClmFlag.length()+"大于最大值1");
		ClmFlag = aClmFlag;
	}
	public String getNo()
	{
		return No;
	}
	public void setNo(String aNo)
	{
		if(aNo!=null && aNo.length()>40)
			throw new IllegalArgumentException("病例号码No值"+aNo+"的长度"+aNo.length()+"大于最大值40");
		No = aNo;
	}
	public String getHospitalCode()
	{
		return HospitalCode;
	}
	public void setHospitalCode(String aHospitalCode)
	{
		if(aHospitalCode!=null && aHospitalCode.length()>10)
			throw new IllegalArgumentException("医院代码HospitalCode值"+aHospitalCode+"的长度"+aHospitalCode.length()+"大于最大值10");
		HospitalCode = aHospitalCode;
	}
	public String getHospitalName()
	{
		return HospitalName;
	}
	public void setHospitalName(String aHospitalName)
	{
		if(aHospitalName!=null && aHospitalName.length()>60)
			throw new IllegalArgumentException("医院名称HospitalName值"+aHospitalName+"的长度"+aHospitalName.length()+"大于最大值60");
		HospitalName = aHospitalName;
	}
	public String getDoctorCode()
	{
		return DoctorCode;
	}
	public void setDoctorCode(String aDoctorCode)
	{
		if(aDoctorCode!=null && aDoctorCode.length()>10)
			throw new IllegalArgumentException("医生代码DoctorCode值"+aDoctorCode+"的长度"+aDoctorCode.length()+"大于最大值10");
		DoctorCode = aDoctorCode;
	}
	public String getDoctorName()
	{
		return DoctorName;
	}
	public void setDoctorName(String aDoctorName)
	{
		if(aDoctorName!=null && aDoctorName.length()>60)
			throw new IllegalArgumentException("医生名称DoctorName值"+aDoctorName+"的长度"+aDoctorName.length()+"大于最大值60");
		DoctorName = aDoctorName;
	}
	public String getInHosDate()
	{
		if( InHosDate != null )
			return fDate.getString(InHosDate);
		else
			return null;
	}
	public void setInHosDate(Date aInHosDate)
	{
		InHosDate = aInHosDate;
	}
	public void setInHosDate(String aInHosDate)
	{
		if (aInHosDate != null && !aInHosDate.equals("") )
		{
			InHosDate = fDate.getDate( aInHosDate );
		}
		else
			InHosDate = null;
	}

	public String getOutHosDate()
	{
		if( OutHosDate != null )
			return fDate.getString(OutHosDate);
		else
			return null;
	}
	public void setOutHosDate(Date aOutHosDate)
	{
		OutHosDate = aOutHosDate;
	}
	public void setOutHosDate(String aOutHosDate)
	{
		if (aOutHosDate != null && !aOutHosDate.equals("") )
		{
			OutHosDate = fDate.getDate( aOutHosDate );
		}
		else
			OutHosDate = null;
	}

	public int getInHosDays()
	{
		return InHosDays;
	}
	public void setInHosDays(int aInHosDays)
	{
		InHosDays = aInHosDays;
	}
	public void setInHosDays(String aInHosDays)
	{
		if (aInHosDays != null && !aInHosDays.equals(""))
		{
			Integer tInteger = new Integer(aInHosDays);
			int i = tInteger.intValue();
			InHosDays = i;
		}
	}

	/**
	* 0-疾病住院 1-意外住院
	*/
	public String getInHosType()
	{
		return InHosType;
	}
	public void setInHosType(String aInHosType)
	{
		if(aInHosType!=null && aInHosType.length()>3)
			throw new IllegalArgumentException("住院原因InHosType值"+aInHosType+"的长度"+aInHosType.length()+"大于最大值3");
		InHosType = aInHosType;
	}
	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		if(aMngCom!=null && aMngCom.length()>10)
			throw new IllegalArgumentException("管理机构MngCom值"+aMngCom+"的长度"+aMngCom.length()+"大于最大值10");
		MngCom = aMngCom;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
	* 使用另外一个 LLHospitalSchema 对象给 Schema 赋值
	* @param: aLLHospitalSchema LLHospitalSchema
	**/
	public void setSchema(LLHospitalSchema aLLHospitalSchema)
	{
		this.InHosNo = aLLHospitalSchema.getInHosNo();
		this.RgtNo = aLLHospitalSchema.getRgtNo();
		this.CaseNo = aLLHospitalSchema.getCaseNo();
		this.CustomerNo = aLLHospitalSchema.getCustomerNo();
		this.CaseRelaNo = aLLHospitalSchema.getCaseRelaNo();
		this.CustomerName = aLLHospitalSchema.getCustomerName();
		this.ClmFlag = aLLHospitalSchema.getClmFlag();
		this.No = aLLHospitalSchema.getNo();
		this.HospitalCode = aLLHospitalSchema.getHospitalCode();
		this.HospitalName = aLLHospitalSchema.getHospitalName();
		this.DoctorCode = aLLHospitalSchema.getDoctorCode();
		this.DoctorName = aLLHospitalSchema.getDoctorName();
		this.InHosDate = fDate.getDate( aLLHospitalSchema.getInHosDate());
		this.OutHosDate = fDate.getDate( aLLHospitalSchema.getOutHosDate());
		this.InHosDays = aLLHospitalSchema.getInHosDays();
		this.InHosType = aLLHospitalSchema.getInHosType();
		this.MngCom = aLLHospitalSchema.getMngCom();
		this.Operator = aLLHospitalSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLHospitalSchema.getMakeDate());
		this.MakeTime = aLLHospitalSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLHospitalSchema.getModifyDate());
		this.ModifyTime = aLLHospitalSchema.getModifyTime();
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
			if( rs.getString("InHosNo") == null )
				this.InHosNo = null;
			else
				this.InHosNo = rs.getString("InHosNo").trim();

			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CaseRelaNo") == null )
				this.CaseRelaNo = null;
			else
				this.CaseRelaNo = rs.getString("CaseRelaNo").trim();

			if( rs.getString("CustomerName") == null )
				this.CustomerName = null;
			else
				this.CustomerName = rs.getString("CustomerName").trim();

			if( rs.getString("ClmFlag") == null )
				this.ClmFlag = null;
			else
				this.ClmFlag = rs.getString("ClmFlag").trim();

			if( rs.getString("No") == null )
				this.No = null;
			else
				this.No = rs.getString("No").trim();

			if( rs.getString("HospitalCode") == null )
				this.HospitalCode = null;
			else
				this.HospitalCode = rs.getString("HospitalCode").trim();

			if( rs.getString("HospitalName") == null )
				this.HospitalName = null;
			else
				this.HospitalName = rs.getString("HospitalName").trim();

			if( rs.getString("DoctorCode") == null )
				this.DoctorCode = null;
			else
				this.DoctorCode = rs.getString("DoctorCode").trim();

			if( rs.getString("DoctorName") == null )
				this.DoctorName = null;
			else
				this.DoctorName = rs.getString("DoctorName").trim();

			this.InHosDate = rs.getDate("InHosDate");
			this.OutHosDate = rs.getDate("OutHosDate");
			this.InHosDays = rs.getInt("InHosDays");
			if( rs.getString("InHosType") == null )
				this.InHosType = null;
			else
				this.InHosType = rs.getString("InHosType").trim();

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

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
			logger.debug("数据库中的LLHospital表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLHospitalSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLHospitalSchema getSchema()
	{
		LLHospitalSchema aLLHospitalSchema = new LLHospitalSchema();
		aLLHospitalSchema.setSchema(this);
		return aLLHospitalSchema;
	}

	public LLHospitalDB getDB()
	{
		LLHospitalDB aDBOper = new LLHospitalDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLHospital描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(InHosNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(No)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DoctorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DoctorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InHosDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OutHosDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InHosDays));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InHosType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLHospital>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			InHosNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CaseRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ClmFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			No = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			HospitalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			HospitalName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			DoctorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			DoctorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			InHosDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			OutHosDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			InHosDays= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			InHosType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLHospitalSchema";
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
		if (FCode.equalsIgnoreCase("InHosNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InHosNo));
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseRelaNo));
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerName));
		}
		if (FCode.equalsIgnoreCase("ClmFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFlag));
		}
		if (FCode.equalsIgnoreCase("No"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(No));
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalCode));
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalName));
		}
		if (FCode.equalsIgnoreCase("DoctorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoctorCode));
		}
		if (FCode.equalsIgnoreCase("DoctorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoctorName));
		}
		if (FCode.equalsIgnoreCase("InHosDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInHosDate()));
		}
		if (FCode.equalsIgnoreCase("OutHosDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOutHosDate()));
		}
		if (FCode.equalsIgnoreCase("InHosDays"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InHosDays));
		}
		if (FCode.equalsIgnoreCase("InHosType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InHosType));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
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
				strFieldValue = StrTool.GBKToUnicode(InHosNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CaseRelaNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ClmFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(No);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(HospitalCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(HospitalName);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(DoctorCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(DoctorName);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInHosDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOutHosDate()));
				break;
			case 14:
				strFieldValue = String.valueOf(InHosDays);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(InHosType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 21:
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

		if (FCode.equalsIgnoreCase("InHosNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InHosNo = FValue.trim();
			}
			else
				InHosNo = null;
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtNo = FValue.trim();
			}
			else
				RgtNo = null;
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseRelaNo = FValue.trim();
			}
			else
				CaseRelaNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerName = FValue.trim();
			}
			else
				CustomerName = null;
		}
		if (FCode.equalsIgnoreCase("ClmFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFlag = FValue.trim();
			}
			else
				ClmFlag = null;
		}
		if (FCode.equalsIgnoreCase("No"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				No = FValue.trim();
			}
			else
				No = null;
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitalCode = FValue.trim();
			}
			else
				HospitalCode = null;
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitalName = FValue.trim();
			}
			else
				HospitalName = null;
		}
		if (FCode.equalsIgnoreCase("DoctorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DoctorCode = FValue.trim();
			}
			else
				DoctorCode = null;
		}
		if (FCode.equalsIgnoreCase("DoctorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DoctorName = FValue.trim();
			}
			else
				DoctorName = null;
		}
		if (FCode.equalsIgnoreCase("InHosDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InHosDate = fDate.getDate( FValue );
			}
			else
				InHosDate = null;
		}
		if (FCode.equalsIgnoreCase("OutHosDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OutHosDate = fDate.getDate( FValue );
			}
			else
				OutHosDate = null;
		}
		if (FCode.equalsIgnoreCase("InHosDays"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InHosDays = i;
			}
		}
		if (FCode.equalsIgnoreCase("InHosType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InHosType = FValue.trim();
			}
			else
				InHosType = null;
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
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
		LLHospitalSchema other = (LLHospitalSchema)otherObject;
		return
			InHosNo.equals(other.getInHosNo())
			&& RgtNo.equals(other.getRgtNo())
			&& CaseNo.equals(other.getCaseNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CaseRelaNo.equals(other.getCaseRelaNo())
			&& CustomerName.equals(other.getCustomerName())
			&& ClmFlag.equals(other.getClmFlag())
			&& No.equals(other.getNo())
			&& HospitalCode.equals(other.getHospitalCode())
			&& HospitalName.equals(other.getHospitalName())
			&& DoctorCode.equals(other.getDoctorCode())
			&& DoctorName.equals(other.getDoctorName())
			&& fDate.getString(InHosDate).equals(other.getInHosDate())
			&& fDate.getString(OutHosDate).equals(other.getOutHosDate())
			&& InHosDays == other.getInHosDays()
			&& InHosType.equals(other.getInHosType())
			&& MngCom.equals(other.getMngCom())
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
		if( strFieldName.equals("InHosNo") ) {
			return 0;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 1;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 2;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 3;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return 4;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 5;
		}
		if( strFieldName.equals("ClmFlag") ) {
			return 6;
		}
		if( strFieldName.equals("No") ) {
			return 7;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return 8;
		}
		if( strFieldName.equals("HospitalName") ) {
			return 9;
		}
		if( strFieldName.equals("DoctorCode") ) {
			return 10;
		}
		if( strFieldName.equals("DoctorName") ) {
			return 11;
		}
		if( strFieldName.equals("InHosDate") ) {
			return 12;
		}
		if( strFieldName.equals("OutHosDate") ) {
			return 13;
		}
		if( strFieldName.equals("InHosDays") ) {
			return 14;
		}
		if( strFieldName.equals("InHosType") ) {
			return 15;
		}
		if( strFieldName.equals("MngCom") ) {
			return 16;
		}
		if( strFieldName.equals("Operator") ) {
			return 17;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 18;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "InHosNo";
				break;
			case 1:
				strFieldName = "RgtNo";
				break;
			case 2:
				strFieldName = "CaseNo";
				break;
			case 3:
				strFieldName = "CustomerNo";
				break;
			case 4:
				strFieldName = "CaseRelaNo";
				break;
			case 5:
				strFieldName = "CustomerName";
				break;
			case 6:
				strFieldName = "ClmFlag";
				break;
			case 7:
				strFieldName = "No";
				break;
			case 8:
				strFieldName = "HospitalCode";
				break;
			case 9:
				strFieldName = "HospitalName";
				break;
			case 10:
				strFieldName = "DoctorCode";
				break;
			case 11:
				strFieldName = "DoctorName";
				break;
			case 12:
				strFieldName = "InHosDate";
				break;
			case 13:
				strFieldName = "OutHosDate";
				break;
			case 14:
				strFieldName = "InHosDays";
				break;
			case 15:
				strFieldName = "InHosType";
				break;
			case 16:
				strFieldName = "MngCom";
				break;
			case 17:
				strFieldName = "Operator";
				break;
			case 18:
				strFieldName = "MakeDate";
				break;
			case 19:
				strFieldName = "MakeTime";
				break;
			case 20:
				strFieldName = "ModifyDate";
				break;
			case 21:
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
		if( strFieldName.equals("InHosNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("No") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoctorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoctorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InHosDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OutHosDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InHosDays") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InHosType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MngCom") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
