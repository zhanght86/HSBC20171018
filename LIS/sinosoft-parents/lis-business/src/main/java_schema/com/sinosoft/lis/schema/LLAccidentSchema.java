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
import com.sinosoft.lis.db.LLAccidentDB;

/*
 * <p>ClassName: LLAccidentSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLAccidentSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLAccidentSchema.class);
	// @Field
	/** 事件号 */
	private String AccNo;
	/** 事故发生日期 */
	private Date AccDate;
	/** 事故类型 */
	private String AccType;
	/** 事件主题 */
	private String AccSubject;
	/** 事故描述 */
	private String AccDesc;
	/** 事故地点 */
	private String AccPlace;
	/** 重大事件标志 */
	private String AccGrade;
	/** 操作员 */
	private String Operator;
	/** 管理机构 */
	private String MngCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 备注 */
	private String Remark;
	/** 报案号 */
	private String RptNo;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 出险原因 */
	private String AccReason;
	/** 身故日期 */
	private Date DeathDate;
	/** 伤残日期 */
	private Date DeformityDate;
	/** 出险结果1 */
	private String AccResult1;
	/** 出险结果2 */
	private String AccResult2;
	/** 医院编码 */
	private String HospitalCode;
	/** 医院名称 */
	private String HospitalName;
	/** 索赔金额 */
	private double ClaimPay;
	/** 出险地点（省） */
	private String AccProvince;
	/** 出险地点（市） */
	private String AccCity;
	/** 出险地点（区/县） */
	private String AccCounty;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 29;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLAccidentSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "AccNo";

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
		LLAccidentSchema cloned = (LLAccidentSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAccNo()
	{
		return AccNo;
	}
	public void setAccNo(String aAccNo)
	{
		if(aAccNo!=null && aAccNo.length()>20)
			throw new IllegalArgumentException("事件号AccNo值"+aAccNo+"的长度"+aAccNo.length()+"大于最大值20");
		AccNo = aAccNo;
	}
	public String getAccDate()
	{
		if( AccDate != null )
			return fDate.getString(AccDate);
		else
			return null;
	}
	public void setAccDate(Date aAccDate)
	{
		AccDate = aAccDate;
	}
	public void setAccDate(String aAccDate)
	{
		if (aAccDate != null && !aAccDate.equals("") )
		{
			AccDate = fDate.getDate( aAccDate );
		}
		else
			AccDate = null;
	}

	/**
	* 1 －－ 意外<p>
	* 2 －－ 疾病
	*/
	public String getAccType()
	{
		return AccType;
	}
	public void setAccType(String aAccType)
	{
		if(aAccType!=null && aAccType.length()>100)
			throw new IllegalArgumentException("事故类型AccType值"+aAccType+"的长度"+aAccType.length()+"大于最大值100");
		AccType = aAccType;
	}
	public String getAccSubject()
	{
		return AccSubject;
	}
	public void setAccSubject(String aAccSubject)
	{
		if(aAccSubject!=null && aAccSubject.length()>600)
			throw new IllegalArgumentException("事件主题AccSubject值"+aAccSubject+"的长度"+aAccSubject.length()+"大于最大值600");
		AccSubject = aAccSubject;
	}
	public String getAccDesc()
	{
		return AccDesc;
	}
	public void setAccDesc(String aAccDesc)
	{
		if(aAccDesc!=null && aAccDesc.length()>1000)
			throw new IllegalArgumentException("事故描述AccDesc值"+aAccDesc+"的长度"+aAccDesc.length()+"大于最大值1000");
		AccDesc = aAccDesc;
	}
	public String getAccPlace()
	{
		return AccPlace;
	}
	public void setAccPlace(String aAccPlace)
	{
		if(aAccPlace!=null && aAccPlace.length()>100)
			throw new IllegalArgumentException("事故地点AccPlace值"+aAccPlace+"的长度"+aAccPlace.length()+"大于最大值100");
		AccPlace = aAccPlace;
	}
	/**
	* 0 一般事件<p>
	* 1 重大事件
	*/
	public String getAccGrade()
	{
		return AccGrade;
	}
	public void setAccGrade(String aAccGrade)
	{
		if(aAccGrade!=null && aAccGrade.length()>6)
			throw new IllegalArgumentException("重大事件标志AccGrade值"+aAccGrade+"的长度"+aAccGrade.length()+"大于最大值6");
		AccGrade = aAccGrade;
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
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>1000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值1000");
		Remark = aRemark;
	}
	public String getRptNo()
	{
		return RptNo;
	}
	public void setRptNo(String aRptNo)
	{
		if(aRptNo!=null && aRptNo.length()>20)
			throw new IllegalArgumentException("报案号RptNo值"+aRptNo+"的长度"+aRptNo.length()+"大于最大值20");
		RptNo = aRptNo;
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
	* 1-疾病，2-意外，3-生育，4-体检，9-其它
	*/
	public String getAccReason()
	{
		return AccReason;
	}
	public void setAccReason(String aAccReason)
	{
		if(aAccReason!=null && aAccReason.length()>6)
			throw new IllegalArgumentException("出险原因AccReason值"+aAccReason+"的长度"+aAccReason.length()+"大于最大值6");
		AccReason = aAccReason;
	}
	public String getDeathDate()
	{
		if( DeathDate != null )
			return fDate.getString(DeathDate);
		else
			return null;
	}
	public void setDeathDate(Date aDeathDate)
	{
		DeathDate = aDeathDate;
	}
	public void setDeathDate(String aDeathDate)
	{
		if (aDeathDate != null && !aDeathDate.equals("") )
		{
			DeathDate = fDate.getDate( aDeathDate );
		}
		else
			DeathDate = null;
	}

	public String getDeformityDate()
	{
		if( DeformityDate != null )
			return fDate.getString(DeformityDate);
		else
			return null;
	}
	public void setDeformityDate(Date aDeformityDate)
	{
		DeformityDate = aDeformityDate;
	}
	public void setDeformityDate(String aDeformityDate)
	{
		if (aDeformityDate != null && !aDeformityDate.equals("") )
		{
			DeformityDate = fDate.getDate( aDeformityDate );
		}
		else
			DeformityDate = null;
	}

	public String getAccResult1()
	{
		return AccResult1;
	}
	public void setAccResult1(String aAccResult1)
	{
		if(aAccResult1!=null && aAccResult1.length()>10)
			throw new IllegalArgumentException("出险结果1AccResult1值"+aAccResult1+"的长度"+aAccResult1.length()+"大于最大值10");
		AccResult1 = aAccResult1;
	}
	public String getAccResult2()
	{
		return AccResult2;
	}
	public void setAccResult2(String aAccResult2)
	{
		if(aAccResult2!=null && aAccResult2.length()>10)
			throw new IllegalArgumentException("出险结果2AccResult2值"+aAccResult2+"的长度"+aAccResult2.length()+"大于最大值10");
		AccResult2 = aAccResult2;
	}
	public String getHospitalCode()
	{
		return HospitalCode;
	}
	public void setHospitalCode(String aHospitalCode)
	{
		if(aHospitalCode!=null && aHospitalCode.length()>20)
			throw new IllegalArgumentException("医院编码HospitalCode值"+aHospitalCode+"的长度"+aHospitalCode.length()+"大于最大值20");
		HospitalCode = aHospitalCode;
	}
	public String getHospitalName()
	{
		return HospitalName;
	}
	public void setHospitalName(String aHospitalName)
	{
		if(aHospitalName!=null && aHospitalName.length()>200)
			throw new IllegalArgumentException("医院名称HospitalName值"+aHospitalName+"的长度"+aHospitalName.length()+"大于最大值200");
		HospitalName = aHospitalName;
	}
	public double getClaimPay()
	{
		return ClaimPay;
	}
	public void setClaimPay(double aClaimPay)
	{
		ClaimPay = aClaimPay;
	}
	public void setClaimPay(String aClaimPay)
	{
		if (aClaimPay != null && !aClaimPay.equals(""))
		{
			Double tDouble = new Double(aClaimPay);
			double d = tDouble.doubleValue();
			ClaimPay = d;
		}
	}

	public String getAccProvince()
	{
		return AccProvince;
	}
	public void setAccProvince(String aAccProvince)
	{
		if(aAccProvince!=null && aAccProvince.length()>30)
			throw new IllegalArgumentException("出险地点（省）AccProvince值"+aAccProvince+"的长度"+aAccProvince.length()+"大于最大值30");
		AccProvince = aAccProvince;
	}
	public String getAccCity()
	{
		return AccCity;
	}
	public void setAccCity(String aAccCity)
	{
		if(aAccCity!=null && aAccCity.length()>30)
			throw new IllegalArgumentException("出险地点（市）AccCity值"+aAccCity+"的长度"+aAccCity.length()+"大于最大值30");
		AccCity = aAccCity;
	}
	public String getAccCounty()
	{
		return AccCounty;
	}
	public void setAccCounty(String aAccCounty)
	{
		if(aAccCounty!=null && aAccCounty.length()>30)
			throw new IllegalArgumentException("出险地点（区/县）AccCounty值"+aAccCounty+"的长度"+aAccCounty.length()+"大于最大值30");
		AccCounty = aAccCounty;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
	}

	/**
	* 使用另外一个 LLAccidentSchema 对象给 Schema 赋值
	* @param: aLLAccidentSchema LLAccidentSchema
	**/
	public void setSchema(LLAccidentSchema aLLAccidentSchema)
	{
		this.AccNo = aLLAccidentSchema.getAccNo();
		this.AccDate = fDate.getDate( aLLAccidentSchema.getAccDate());
		this.AccType = aLLAccidentSchema.getAccType();
		this.AccSubject = aLLAccidentSchema.getAccSubject();
		this.AccDesc = aLLAccidentSchema.getAccDesc();
		this.AccPlace = aLLAccidentSchema.getAccPlace();
		this.AccGrade = aLLAccidentSchema.getAccGrade();
		this.Operator = aLLAccidentSchema.getOperator();
		this.MngCom = aLLAccidentSchema.getMngCom();
		this.MakeDate = fDate.getDate( aLLAccidentSchema.getMakeDate());
		this.MakeTime = aLLAccidentSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLAccidentSchema.getModifyDate());
		this.ModifyTime = aLLAccidentSchema.getModifyTime();
		this.Remark = aLLAccidentSchema.getRemark();
		this.RptNo = aLLAccidentSchema.getRptNo();
		this.CustomerNo = aLLAccidentSchema.getCustomerNo();
		this.AccReason = aLLAccidentSchema.getAccReason();
		this.DeathDate = fDate.getDate( aLLAccidentSchema.getDeathDate());
		this.DeformityDate = fDate.getDate( aLLAccidentSchema.getDeformityDate());
		this.AccResult1 = aLLAccidentSchema.getAccResult1();
		this.AccResult2 = aLLAccidentSchema.getAccResult2();
		this.HospitalCode = aLLAccidentSchema.getHospitalCode();
		this.HospitalName = aLLAccidentSchema.getHospitalName();
		this.ClaimPay = aLLAccidentSchema.getClaimPay();
		this.AccProvince = aLLAccidentSchema.getAccProvince();
		this.AccCity = aLLAccidentSchema.getAccCity();
		this.AccCounty = aLLAccidentSchema.getAccCounty();
		this.ComCode = aLLAccidentSchema.getComCode();
		this.ModifyOperator = aLLAccidentSchema.getModifyOperator();
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
			if( rs.getString("AccNo") == null )
				this.AccNo = null;
			else
				this.AccNo = rs.getString("AccNo").trim();

			this.AccDate = rs.getDate("AccDate");
			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("AccSubject") == null )
				this.AccSubject = null;
			else
				this.AccSubject = rs.getString("AccSubject").trim();

			if( rs.getString("AccDesc") == null )
				this.AccDesc = null;
			else
				this.AccDesc = rs.getString("AccDesc").trim();

			if( rs.getString("AccPlace") == null )
				this.AccPlace = null;
			else
				this.AccPlace = rs.getString("AccPlace").trim();

			if( rs.getString("AccGrade") == null )
				this.AccGrade = null;
			else
				this.AccGrade = rs.getString("AccGrade").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

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

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("RptNo") == null )
				this.RptNo = null;
			else
				this.RptNo = rs.getString("RptNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("AccReason") == null )
				this.AccReason = null;
			else
				this.AccReason = rs.getString("AccReason").trim();

			this.DeathDate = rs.getDate("DeathDate");
			this.DeformityDate = rs.getDate("DeformityDate");
			if( rs.getString("AccResult1") == null )
				this.AccResult1 = null;
			else
				this.AccResult1 = rs.getString("AccResult1").trim();

			if( rs.getString("AccResult2") == null )
				this.AccResult2 = null;
			else
				this.AccResult2 = rs.getString("AccResult2").trim();

			if( rs.getString("HospitalCode") == null )
				this.HospitalCode = null;
			else
				this.HospitalCode = rs.getString("HospitalCode").trim();

			if( rs.getString("HospitalName") == null )
				this.HospitalName = null;
			else
				this.HospitalName = rs.getString("HospitalName").trim();

			this.ClaimPay = rs.getDouble("ClaimPay");
			if( rs.getString("AccProvince") == null )
				this.AccProvince = null;
			else
				this.AccProvince = rs.getString("AccProvince").trim();

			if( rs.getString("AccCity") == null )
				this.AccCity = null;
			else
				this.AccCity = rs.getString("AccCity").trim();

			if( rs.getString("AccCounty") == null )
				this.AccCounty = null;
			else
				this.AccCounty = rs.getString("AccCounty").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLAccident表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAccidentSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLAccidentSchema getSchema()
	{
		LLAccidentSchema aLLAccidentSchema = new LLAccidentSchema();
		aLLAccidentSchema.setSchema(this);
		return aLLAccidentSchema;
	}

	public LLAccidentDB getDB()
	{
		LLAccidentDB aDBOper = new LLAccidentDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAccident描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccSubject)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccPlace)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DeathDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DeformityDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccResult1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccResult2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClaimPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccProvince)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCity)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCounty)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAccident>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2,SysConst.PACKAGESPILTER));
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AccSubject = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AccDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AccPlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AccGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			RptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AccReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			DeathDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			DeformityDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			AccResult1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			AccResult2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			HospitalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			HospitalName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ClaimPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			AccProvince = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AccCity = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			AccCounty = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAccidentSchema";
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
		if (FCode.equalsIgnoreCase("AccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccNo));
		}
		if (FCode.equalsIgnoreCase("AccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccDate()));
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("AccSubject"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccSubject));
		}
		if (FCode.equalsIgnoreCase("AccDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccDesc));
		}
		if (FCode.equalsIgnoreCase("AccPlace"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccPlace));
		}
		if (FCode.equalsIgnoreCase("AccGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccGrade));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("RptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RptNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("AccReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccReason));
		}
		if (FCode.equalsIgnoreCase("DeathDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDeathDate()));
		}
		if (FCode.equalsIgnoreCase("DeformityDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDeformityDate()));
		}
		if (FCode.equalsIgnoreCase("AccResult1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccResult1));
		}
		if (FCode.equalsIgnoreCase("AccResult2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccResult2));
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalCode));
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalName));
		}
		if (FCode.equalsIgnoreCase("ClaimPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimPay));
		}
		if (FCode.equalsIgnoreCase("AccProvince"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccProvince));
		}
		if (FCode.equalsIgnoreCase("AccCity"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCity));
		}
		if (FCode.equalsIgnoreCase("AccCounty"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCounty));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(AccNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccDate()));
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AccSubject);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AccDesc);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AccPlace);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AccGrade);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RptNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AccReason);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDeathDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDeformityDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AccResult1);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(AccResult2);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(HospitalCode);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(HospitalName);
				break;
			case 23:
				strFieldValue = String.valueOf(ClaimPay);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AccProvince);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(AccCity);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(AccCounty);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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

		if (FCode.equalsIgnoreCase("AccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccNo = FValue.trim();
			}
			else
				AccNo = null;
		}
		if (FCode.equalsIgnoreCase("AccDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccDate = fDate.getDate( FValue );
			}
			else
				AccDate = null;
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccType = FValue.trim();
			}
			else
				AccType = null;
		}
		if (FCode.equalsIgnoreCase("AccSubject"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccSubject = FValue.trim();
			}
			else
				AccSubject = null;
		}
		if (FCode.equalsIgnoreCase("AccDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccDesc = FValue.trim();
			}
			else
				AccDesc = null;
		}
		if (FCode.equalsIgnoreCase("AccPlace"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccPlace = FValue.trim();
			}
			else
				AccPlace = null;
		}
		if (FCode.equalsIgnoreCase("AccGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccGrade = FValue.trim();
			}
			else
				AccGrade = null;
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
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equalsIgnoreCase("RptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RptNo = FValue.trim();
			}
			else
				RptNo = null;
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
		if (FCode.equalsIgnoreCase("AccReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccReason = FValue.trim();
			}
			else
				AccReason = null;
		}
		if (FCode.equalsIgnoreCase("DeathDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DeathDate = fDate.getDate( FValue );
			}
			else
				DeathDate = null;
		}
		if (FCode.equalsIgnoreCase("DeformityDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DeformityDate = fDate.getDate( FValue );
			}
			else
				DeformityDate = null;
		}
		if (FCode.equalsIgnoreCase("AccResult1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccResult1 = FValue.trim();
			}
			else
				AccResult1 = null;
		}
		if (FCode.equalsIgnoreCase("AccResult2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccResult2 = FValue.trim();
			}
			else
				AccResult2 = null;
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
		if (FCode.equalsIgnoreCase("ClaimPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ClaimPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccProvince"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccProvince = FValue.trim();
			}
			else
				AccProvince = null;
		}
		if (FCode.equalsIgnoreCase("AccCity"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCity = FValue.trim();
			}
			else
				AccCity = null;
		}
		if (FCode.equalsIgnoreCase("AccCounty"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCounty = FValue.trim();
			}
			else
				AccCounty = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLAccidentSchema other = (LLAccidentSchema)otherObject;
		return
			AccNo.equals(other.getAccNo())
			&& fDate.getString(AccDate).equals(other.getAccDate())
			&& AccType.equals(other.getAccType())
			&& AccSubject.equals(other.getAccSubject())
			&& AccDesc.equals(other.getAccDesc())
			&& AccPlace.equals(other.getAccPlace())
			&& AccGrade.equals(other.getAccGrade())
			&& Operator.equals(other.getOperator())
			&& MngCom.equals(other.getMngCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Remark.equals(other.getRemark())
			&& RptNo.equals(other.getRptNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& AccReason.equals(other.getAccReason())
			&& fDate.getString(DeathDate).equals(other.getDeathDate())
			&& fDate.getString(DeformityDate).equals(other.getDeformityDate())
			&& AccResult1.equals(other.getAccResult1())
			&& AccResult2.equals(other.getAccResult2())
			&& HospitalCode.equals(other.getHospitalCode())
			&& HospitalName.equals(other.getHospitalName())
			&& ClaimPay == other.getClaimPay()
			&& AccProvince.equals(other.getAccProvince())
			&& AccCity.equals(other.getAccCity())
			&& AccCounty.equals(other.getAccCounty())
			&& ComCode.equals(other.getComCode())
			&& ModifyOperator.equals(other.getModifyOperator());
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
		if( strFieldName.equals("AccNo") ) {
			return 0;
		}
		if( strFieldName.equals("AccDate") ) {
			return 1;
		}
		if( strFieldName.equals("AccType") ) {
			return 2;
		}
		if( strFieldName.equals("AccSubject") ) {
			return 3;
		}
		if( strFieldName.equals("AccDesc") ) {
			return 4;
		}
		if( strFieldName.equals("AccPlace") ) {
			return 5;
		}
		if( strFieldName.equals("AccGrade") ) {
			return 6;
		}
		if( strFieldName.equals("Operator") ) {
			return 7;
		}
		if( strFieldName.equals("MngCom") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 12;
		}
		if( strFieldName.equals("Remark") ) {
			return 13;
		}
		if( strFieldName.equals("RptNo") ) {
			return 14;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 15;
		}
		if( strFieldName.equals("AccReason") ) {
			return 16;
		}
		if( strFieldName.equals("DeathDate") ) {
			return 17;
		}
		if( strFieldName.equals("DeformityDate") ) {
			return 18;
		}
		if( strFieldName.equals("AccResult1") ) {
			return 19;
		}
		if( strFieldName.equals("AccResult2") ) {
			return 20;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return 21;
		}
		if( strFieldName.equals("HospitalName") ) {
			return 22;
		}
		if( strFieldName.equals("ClaimPay") ) {
			return 23;
		}
		if( strFieldName.equals("AccProvince") ) {
			return 24;
		}
		if( strFieldName.equals("AccCity") ) {
			return 25;
		}
		if( strFieldName.equals("AccCounty") ) {
			return 26;
		}
		if( strFieldName.equals("ComCode") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 28;
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
				strFieldName = "AccNo";
				break;
			case 1:
				strFieldName = "AccDate";
				break;
			case 2:
				strFieldName = "AccType";
				break;
			case 3:
				strFieldName = "AccSubject";
				break;
			case 4:
				strFieldName = "AccDesc";
				break;
			case 5:
				strFieldName = "AccPlace";
				break;
			case 6:
				strFieldName = "AccGrade";
				break;
			case 7:
				strFieldName = "Operator";
				break;
			case 8:
				strFieldName = "MngCom";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
				strFieldName = "MakeTime";
				break;
			case 11:
				strFieldName = "ModifyDate";
				break;
			case 12:
				strFieldName = "ModifyTime";
				break;
			case 13:
				strFieldName = "Remark";
				break;
			case 14:
				strFieldName = "RptNo";
				break;
			case 15:
				strFieldName = "CustomerNo";
				break;
			case 16:
				strFieldName = "AccReason";
				break;
			case 17:
				strFieldName = "DeathDate";
				break;
			case 18:
				strFieldName = "DeformityDate";
				break;
			case 19:
				strFieldName = "AccResult1";
				break;
			case 20:
				strFieldName = "AccResult2";
				break;
			case 21:
				strFieldName = "HospitalCode";
				break;
			case 22:
				strFieldName = "HospitalName";
				break;
			case 23:
				strFieldName = "ClaimPay";
				break;
			case 24:
				strFieldName = "AccProvince";
				break;
			case 25:
				strFieldName = "AccCity";
				break;
			case 26:
				strFieldName = "AccCounty";
				break;
			case 27:
				strFieldName = "ComCode";
				break;
			case 28:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("AccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccSubject") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccPlace") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MngCom") ) {
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
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeathDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DeformityDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccResult1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccResult2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccProvince") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccCity") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccCounty") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
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
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
