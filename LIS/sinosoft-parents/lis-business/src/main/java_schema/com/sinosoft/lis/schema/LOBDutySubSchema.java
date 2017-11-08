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
import com.sinosoft.lis.db.LOBDutySubDB;

/*
 * <p>ClassName: LOBDutySubSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LOBDutySubSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOBDutySubSchema.class);
	// @Field
	/** 业务类型 */
	private String BussType;
	/** 业务号码 */
	private String BussNo;
	/** 个人保单号 */
	private String ContNo;
	/** 个人险种号 */
	private String PolNo;
	/** 其它号码 */
	private String OtherNo;
	/** 系统保险方案编码 */
	private String SysPlanCode;
	/** 保险方案 */
	private String PlanCode;
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** P1 */
	private String P1;
	/** P2 */
	private String P2;
	/** P3 */
	private String P3;
	/** P4 */
	private String P4;
	/** P5 */
	private String P5;
	/** P6 */
	private String P6;
	/** P7 */
	private String P7;
	/** P8 */
	private String P8;
	/** P9 */
	private String P9;
	/** P10 */
	private String P10;
	/** P11 */
	private String P11;
	/** P12 */
	private String P12;
	/** P13 */
	private String P13;
	/** P14 */
	private String P14;
	/** P15 */
	private String P15;
	/** P16 */
	private String P16;
	/** P17 */
	private String P17;
	/** P18 */
	private String P18;
	/** P19 */
	private String P19;
	/** P20 */
	private String P20;
	/** P21 */
	private String P21;
	/** P22 */
	private String P22;
	/** P23 */
	private String P23;
	/** P24 */
	private String P24;
	/** P25 */
	private String P25;
	/** P26 */
	private String P26;
	/** P27 */
	private String P27;
	/** P28 */
	private String P28;
	/** P29 */
	private String P29;
	/** P30 */
	private String P30;

	public static final int FIELDNUM = 39;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOBDutySubSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "BussType";
		pk[1] = "BussNo";
		pk[2] = "PolNo";
		pk[3] = "DutyCode";

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
		LOBDutySubSchema cloned = (LOBDutySubSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBussType()
	{
		return BussType;
	}
	public void setBussType(String aBussType)
	{
		if(aBussType!=null && aBussType.length()>10)
			throw new IllegalArgumentException("业务类型BussType值"+aBussType+"的长度"+aBussType.length()+"大于最大值10");
		BussType = aBussType;
	}
	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		if(aBussNo!=null && aBussNo.length()>20)
			throw new IllegalArgumentException("业务号码BussNo值"+aBussNo+"的长度"+aBussNo.length()+"大于最大值20");
		BussNo = aBussNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("个人保单号ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		if(aPolNo!=null && aPolNo.length()>20)
			throw new IllegalArgumentException("个人险种号PolNo值"+aPolNo+"的长度"+aPolNo.length()+"大于最大值20");
		PolNo = aPolNo;
	}
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		if(aOtherNo!=null && aOtherNo.length()>20)
			throw new IllegalArgumentException("其它号码OtherNo值"+aOtherNo+"的长度"+aOtherNo.length()+"大于最大值20");
		OtherNo = aOtherNo;
	}
	public String getSysPlanCode()
	{
		return SysPlanCode;
	}
	public void setSysPlanCode(String aSysPlanCode)
	{
		if(aSysPlanCode!=null && aSysPlanCode.length()>10)
			throw new IllegalArgumentException("系统保险方案编码SysPlanCode值"+aSysPlanCode+"的长度"+aSysPlanCode.length()+"大于最大值10");
		SysPlanCode = aSysPlanCode;
	}
	public String getPlanCode()
	{
		return PlanCode;
	}
	public void setPlanCode(String aPlanCode)
	{
		if(aPlanCode!=null && aPlanCode.length()>10)
			throw new IllegalArgumentException("保险方案PlanCode值"+aPlanCode+"的长度"+aPlanCode.length()+"大于最大值10");
		PlanCode = aPlanCode;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>20)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值20");
		RiskCode = aRiskCode;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		if(aDutyCode!=null && aDutyCode.length()>10)
			throw new IllegalArgumentException("责任编码DutyCode值"+aDutyCode+"的长度"+aDutyCode.length()+"大于最大值10");
		DutyCode = aDutyCode;
	}
	public String getP1()
	{
		return P1;
	}
	public void setP1(String aP1)
	{
		if(aP1!=null && aP1.length()>20)
			throw new IllegalArgumentException("P1P1值"+aP1+"的长度"+aP1.length()+"大于最大值20");
		P1 = aP1;
	}
	public String getP2()
	{
		return P2;
	}
	public void setP2(String aP2)
	{
		if(aP2!=null && aP2.length()>20)
			throw new IllegalArgumentException("P2P2值"+aP2+"的长度"+aP2.length()+"大于最大值20");
		P2 = aP2;
	}
	public String getP3()
	{
		return P3;
	}
	public void setP3(String aP3)
	{
		if(aP3!=null && aP3.length()>20)
			throw new IllegalArgumentException("P3P3值"+aP3+"的长度"+aP3.length()+"大于最大值20");
		P3 = aP3;
	}
	public String getP4()
	{
		return P4;
	}
	public void setP4(String aP4)
	{
		if(aP4!=null && aP4.length()>20)
			throw new IllegalArgumentException("P4P4值"+aP4+"的长度"+aP4.length()+"大于最大值20");
		P4 = aP4;
	}
	public String getP5()
	{
		return P5;
	}
	public void setP5(String aP5)
	{
		if(aP5!=null && aP5.length()>20)
			throw new IllegalArgumentException("P5P5值"+aP5+"的长度"+aP5.length()+"大于最大值20");
		P5 = aP5;
	}
	public String getP6()
	{
		return P6;
	}
	public void setP6(String aP6)
	{
		if(aP6!=null && aP6.length()>20)
			throw new IllegalArgumentException("P6P6值"+aP6+"的长度"+aP6.length()+"大于最大值20");
		P6 = aP6;
	}
	public String getP7()
	{
		return P7;
	}
	public void setP7(String aP7)
	{
		if(aP7!=null && aP7.length()>20)
			throw new IllegalArgumentException("P7P7值"+aP7+"的长度"+aP7.length()+"大于最大值20");
		P7 = aP7;
	}
	public String getP8()
	{
		return P8;
	}
	public void setP8(String aP8)
	{
		if(aP8!=null && aP8.length()>20)
			throw new IllegalArgumentException("P8P8值"+aP8+"的长度"+aP8.length()+"大于最大值20");
		P8 = aP8;
	}
	public String getP9()
	{
		return P9;
	}
	public void setP9(String aP9)
	{
		if(aP9!=null && aP9.length()>20)
			throw new IllegalArgumentException("P9P9值"+aP9+"的长度"+aP9.length()+"大于最大值20");
		P9 = aP9;
	}
	public String getP10()
	{
		return P10;
	}
	public void setP10(String aP10)
	{
		if(aP10!=null && aP10.length()>20)
			throw new IllegalArgumentException("P10P10值"+aP10+"的长度"+aP10.length()+"大于最大值20");
		P10 = aP10;
	}
	public String getP11()
	{
		return P11;
	}
	public void setP11(String aP11)
	{
		if(aP11!=null && aP11.length()>20)
			throw new IllegalArgumentException("P11P11值"+aP11+"的长度"+aP11.length()+"大于最大值20");
		P11 = aP11;
	}
	public String getP12()
	{
		return P12;
	}
	public void setP12(String aP12)
	{
		if(aP12!=null && aP12.length()>20)
			throw new IllegalArgumentException("P12P12值"+aP12+"的长度"+aP12.length()+"大于最大值20");
		P12 = aP12;
	}
	public String getP13()
	{
		return P13;
	}
	public void setP13(String aP13)
	{
		if(aP13!=null && aP13.length()>20)
			throw new IllegalArgumentException("P13P13值"+aP13+"的长度"+aP13.length()+"大于最大值20");
		P13 = aP13;
	}
	public String getP14()
	{
		return P14;
	}
	public void setP14(String aP14)
	{
		if(aP14!=null && aP14.length()>20)
			throw new IllegalArgumentException("P14P14值"+aP14+"的长度"+aP14.length()+"大于最大值20");
		P14 = aP14;
	}
	public String getP15()
	{
		return P15;
	}
	public void setP15(String aP15)
	{
		if(aP15!=null && aP15.length()>20)
			throw new IllegalArgumentException("P15P15值"+aP15+"的长度"+aP15.length()+"大于最大值20");
		P15 = aP15;
	}
	public String getP16()
	{
		return P16;
	}
	public void setP16(String aP16)
	{
		if(aP16!=null && aP16.length()>20)
			throw new IllegalArgumentException("P16P16值"+aP16+"的长度"+aP16.length()+"大于最大值20");
		P16 = aP16;
	}
	public String getP17()
	{
		return P17;
	}
	public void setP17(String aP17)
	{
		if(aP17!=null && aP17.length()>20)
			throw new IllegalArgumentException("P17P17值"+aP17+"的长度"+aP17.length()+"大于最大值20");
		P17 = aP17;
	}
	public String getP18()
	{
		return P18;
	}
	public void setP18(String aP18)
	{
		if(aP18!=null && aP18.length()>20)
			throw new IllegalArgumentException("P18P18值"+aP18+"的长度"+aP18.length()+"大于最大值20");
		P18 = aP18;
	}
	public String getP19()
	{
		return P19;
	}
	public void setP19(String aP19)
	{
		if(aP19!=null && aP19.length()>20)
			throw new IllegalArgumentException("P19P19值"+aP19+"的长度"+aP19.length()+"大于最大值20");
		P19 = aP19;
	}
	public String getP20()
	{
		return P20;
	}
	public void setP20(String aP20)
	{
		if(aP20!=null && aP20.length()>20)
			throw new IllegalArgumentException("P20P20值"+aP20+"的长度"+aP20.length()+"大于最大值20");
		P20 = aP20;
	}
	public String getP21()
	{
		return P21;
	}
	public void setP21(String aP21)
	{
		if(aP21!=null && aP21.length()>20)
			throw new IllegalArgumentException("P21P21值"+aP21+"的长度"+aP21.length()+"大于最大值20");
		P21 = aP21;
	}
	public String getP22()
	{
		return P22;
	}
	public void setP22(String aP22)
	{
		if(aP22!=null && aP22.length()>20)
			throw new IllegalArgumentException("P22P22值"+aP22+"的长度"+aP22.length()+"大于最大值20");
		P22 = aP22;
	}
	public String getP23()
	{
		return P23;
	}
	public void setP23(String aP23)
	{
		if(aP23!=null && aP23.length()>20)
			throw new IllegalArgumentException("P23P23值"+aP23+"的长度"+aP23.length()+"大于最大值20");
		P23 = aP23;
	}
	public String getP24()
	{
		return P24;
	}
	public void setP24(String aP24)
	{
		if(aP24!=null && aP24.length()>20)
			throw new IllegalArgumentException("P24P24值"+aP24+"的长度"+aP24.length()+"大于最大值20");
		P24 = aP24;
	}
	public String getP25()
	{
		return P25;
	}
	public void setP25(String aP25)
	{
		if(aP25!=null && aP25.length()>20)
			throw new IllegalArgumentException("P25P25值"+aP25+"的长度"+aP25.length()+"大于最大值20");
		P25 = aP25;
	}
	public String getP26()
	{
		return P26;
	}
	public void setP26(String aP26)
	{
		if(aP26!=null && aP26.length()>20)
			throw new IllegalArgumentException("P26P26值"+aP26+"的长度"+aP26.length()+"大于最大值20");
		P26 = aP26;
	}
	public String getP27()
	{
		return P27;
	}
	public void setP27(String aP27)
	{
		if(aP27!=null && aP27.length()>20)
			throw new IllegalArgumentException("P27P27值"+aP27+"的长度"+aP27.length()+"大于最大值20");
		P27 = aP27;
	}
	public String getP28()
	{
		return P28;
	}
	public void setP28(String aP28)
	{
		if(aP28!=null && aP28.length()>20)
			throw new IllegalArgumentException("P28P28值"+aP28+"的长度"+aP28.length()+"大于最大值20");
		P28 = aP28;
	}
	public String getP29()
	{
		return P29;
	}
	public void setP29(String aP29)
	{
		if(aP29!=null && aP29.length()>20)
			throw new IllegalArgumentException("P29P29值"+aP29+"的长度"+aP29.length()+"大于最大值20");
		P29 = aP29;
	}
	public String getP30()
	{
		return P30;
	}
	public void setP30(String aP30)
	{
		if(aP30!=null && aP30.length()>20)
			throw new IllegalArgumentException("P30P30值"+aP30+"的长度"+aP30.length()+"大于最大值20");
		P30 = aP30;
	}

	/**
	* 使用另外一个 LOBDutySubSchema 对象给 Schema 赋值
	* @param: aLOBDutySubSchema LOBDutySubSchema
	**/
	public void setSchema(LOBDutySubSchema aLOBDutySubSchema)
	{
		this.BussType = aLOBDutySubSchema.getBussType();
		this.BussNo = aLOBDutySubSchema.getBussNo();
		this.ContNo = aLOBDutySubSchema.getContNo();
		this.PolNo = aLOBDutySubSchema.getPolNo();
		this.OtherNo = aLOBDutySubSchema.getOtherNo();
		this.SysPlanCode = aLOBDutySubSchema.getSysPlanCode();
		this.PlanCode = aLOBDutySubSchema.getPlanCode();
		this.RiskCode = aLOBDutySubSchema.getRiskCode();
		this.DutyCode = aLOBDutySubSchema.getDutyCode();
		this.P1 = aLOBDutySubSchema.getP1();
		this.P2 = aLOBDutySubSchema.getP2();
		this.P3 = aLOBDutySubSchema.getP3();
		this.P4 = aLOBDutySubSchema.getP4();
		this.P5 = aLOBDutySubSchema.getP5();
		this.P6 = aLOBDutySubSchema.getP6();
		this.P7 = aLOBDutySubSchema.getP7();
		this.P8 = aLOBDutySubSchema.getP8();
		this.P9 = aLOBDutySubSchema.getP9();
		this.P10 = aLOBDutySubSchema.getP10();
		this.P11 = aLOBDutySubSchema.getP11();
		this.P12 = aLOBDutySubSchema.getP12();
		this.P13 = aLOBDutySubSchema.getP13();
		this.P14 = aLOBDutySubSchema.getP14();
		this.P15 = aLOBDutySubSchema.getP15();
		this.P16 = aLOBDutySubSchema.getP16();
		this.P17 = aLOBDutySubSchema.getP17();
		this.P18 = aLOBDutySubSchema.getP18();
		this.P19 = aLOBDutySubSchema.getP19();
		this.P20 = aLOBDutySubSchema.getP20();
		this.P21 = aLOBDutySubSchema.getP21();
		this.P22 = aLOBDutySubSchema.getP22();
		this.P23 = aLOBDutySubSchema.getP23();
		this.P24 = aLOBDutySubSchema.getP24();
		this.P25 = aLOBDutySubSchema.getP25();
		this.P26 = aLOBDutySubSchema.getP26();
		this.P27 = aLOBDutySubSchema.getP27();
		this.P28 = aLOBDutySubSchema.getP28();
		this.P29 = aLOBDutySubSchema.getP29();
		this.P30 = aLOBDutySubSchema.getP30();
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
			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("SysPlanCode") == null )
				this.SysPlanCode = null;
			else
				this.SysPlanCode = rs.getString("SysPlanCode").trim();

			if( rs.getString("PlanCode") == null )
				this.PlanCode = null;
			else
				this.PlanCode = rs.getString("PlanCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("P1") == null )
				this.P1 = null;
			else
				this.P1 = rs.getString("P1").trim();

			if( rs.getString("P2") == null )
				this.P2 = null;
			else
				this.P2 = rs.getString("P2").trim();

			if( rs.getString("P3") == null )
				this.P3 = null;
			else
				this.P3 = rs.getString("P3").trim();

			if( rs.getString("P4") == null )
				this.P4 = null;
			else
				this.P4 = rs.getString("P4").trim();

			if( rs.getString("P5") == null )
				this.P5 = null;
			else
				this.P5 = rs.getString("P5").trim();

			if( rs.getString("P6") == null )
				this.P6 = null;
			else
				this.P6 = rs.getString("P6").trim();

			if( rs.getString("P7") == null )
				this.P7 = null;
			else
				this.P7 = rs.getString("P7").trim();

			if( rs.getString("P8") == null )
				this.P8 = null;
			else
				this.P8 = rs.getString("P8").trim();

			if( rs.getString("P9") == null )
				this.P9 = null;
			else
				this.P9 = rs.getString("P9").trim();

			if( rs.getString("P10") == null )
				this.P10 = null;
			else
				this.P10 = rs.getString("P10").trim();

			if( rs.getString("P11") == null )
				this.P11 = null;
			else
				this.P11 = rs.getString("P11").trim();

			if( rs.getString("P12") == null )
				this.P12 = null;
			else
				this.P12 = rs.getString("P12").trim();

			if( rs.getString("P13") == null )
				this.P13 = null;
			else
				this.P13 = rs.getString("P13").trim();

			if( rs.getString("P14") == null )
				this.P14 = null;
			else
				this.P14 = rs.getString("P14").trim();

			if( rs.getString("P15") == null )
				this.P15 = null;
			else
				this.P15 = rs.getString("P15").trim();

			if( rs.getString("P16") == null )
				this.P16 = null;
			else
				this.P16 = rs.getString("P16").trim();

			if( rs.getString("P17") == null )
				this.P17 = null;
			else
				this.P17 = rs.getString("P17").trim();

			if( rs.getString("P18") == null )
				this.P18 = null;
			else
				this.P18 = rs.getString("P18").trim();

			if( rs.getString("P19") == null )
				this.P19 = null;
			else
				this.P19 = rs.getString("P19").trim();

			if( rs.getString("P20") == null )
				this.P20 = null;
			else
				this.P20 = rs.getString("P20").trim();

			if( rs.getString("P21") == null )
				this.P21 = null;
			else
				this.P21 = rs.getString("P21").trim();

			if( rs.getString("P22") == null )
				this.P22 = null;
			else
				this.P22 = rs.getString("P22").trim();

			if( rs.getString("P23") == null )
				this.P23 = null;
			else
				this.P23 = rs.getString("P23").trim();

			if( rs.getString("P24") == null )
				this.P24 = null;
			else
				this.P24 = rs.getString("P24").trim();

			if( rs.getString("P25") == null )
				this.P25 = null;
			else
				this.P25 = rs.getString("P25").trim();

			if( rs.getString("P26") == null )
				this.P26 = null;
			else
				this.P26 = rs.getString("P26").trim();

			if( rs.getString("P27") == null )
				this.P27 = null;
			else
				this.P27 = rs.getString("P27").trim();

			if( rs.getString("P28") == null )
				this.P28 = null;
			else
				this.P28 = rs.getString("P28").trim();

			if( rs.getString("P29") == null )
				this.P29 = null;
			else
				this.P29 = rs.getString("P29").trim();

			if( rs.getString("P30") == null )
				this.P30 = null;
			else
				this.P30 = rs.getString("P30").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOBDutySub表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBDutySubSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOBDutySubSchema getSchema()
	{
		LOBDutySubSchema aLOBDutySubSchema = new LOBDutySubSchema();
		aLOBDutySubSchema.setSchema(this);
		return aLOBDutySubSchema;
	}

	public LOBDutySubDB getDB()
	{
		LOBDutySubDB aDBOper = new LOBDutySubDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBDutySub描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SysPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P10)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P11)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P12)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P13)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P14)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P15)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P16)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P17)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P18)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P19)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P20)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P21)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P22)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P23)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P24)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P25)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P26)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P27)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P28)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P29)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P30));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBDutySub>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SysPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			P1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			P2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			P3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			P4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			P5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			P6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			P7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			P8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			P9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			P10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			P11 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			P12 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			P13 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			P14 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			P15 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			P16 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			P17 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			P18 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			P19 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			P20 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			P21 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			P22 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			P23 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			P24 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			P25 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			P26 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			P27 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			P28 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			P29 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			P30 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBDutySubSchema";
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
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("SysPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SysPlanCode));
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("P1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P1));
		}
		if (FCode.equalsIgnoreCase("P2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P2));
		}
		if (FCode.equalsIgnoreCase("P3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P3));
		}
		if (FCode.equalsIgnoreCase("P4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P4));
		}
		if (FCode.equalsIgnoreCase("P5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P5));
		}
		if (FCode.equalsIgnoreCase("P6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P6));
		}
		if (FCode.equalsIgnoreCase("P7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P7));
		}
		if (FCode.equalsIgnoreCase("P8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P8));
		}
		if (FCode.equalsIgnoreCase("P9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P9));
		}
		if (FCode.equalsIgnoreCase("P10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P10));
		}
		if (FCode.equalsIgnoreCase("P11"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P11));
		}
		if (FCode.equalsIgnoreCase("P12"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P12));
		}
		if (FCode.equalsIgnoreCase("P13"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P13));
		}
		if (FCode.equalsIgnoreCase("P14"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P14));
		}
		if (FCode.equalsIgnoreCase("P15"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P15));
		}
		if (FCode.equalsIgnoreCase("P16"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P16));
		}
		if (FCode.equalsIgnoreCase("P17"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P17));
		}
		if (FCode.equalsIgnoreCase("P18"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P18));
		}
		if (FCode.equalsIgnoreCase("P19"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P19));
		}
		if (FCode.equalsIgnoreCase("P20"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P20));
		}
		if (FCode.equalsIgnoreCase("P21"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P21));
		}
		if (FCode.equalsIgnoreCase("P22"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P22));
		}
		if (FCode.equalsIgnoreCase("P23"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P23));
		}
		if (FCode.equalsIgnoreCase("P24"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P24));
		}
		if (FCode.equalsIgnoreCase("P25"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P25));
		}
		if (FCode.equalsIgnoreCase("P26"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P26));
		}
		if (FCode.equalsIgnoreCase("P27"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P27));
		}
		if (FCode.equalsIgnoreCase("P28"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P28));
		}
		if (FCode.equalsIgnoreCase("P29"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P29));
		}
		if (FCode.equalsIgnoreCase("P30"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P30));
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
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SysPlanCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(P1);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(P2);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(P3);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(P4);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(P5);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(P6);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(P7);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(P8);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(P9);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(P10);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(P11);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(P12);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(P13);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(P14);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(P15);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(P16);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(P17);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(P18);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(P19);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(P20);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(P21);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(P22);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(P23);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(P24);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(P25);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(P26);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(P27);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(P28);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(P29);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(P30);
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

		if (FCode.equalsIgnoreCase("BussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussType = FValue.trim();
			}
			else
				BussType = null;
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNo = FValue.trim();
			}
			else
				BussNo = null;
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("SysPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SysPlanCode = FValue.trim();
			}
			else
				SysPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanCode = FValue.trim();
			}
			else
				PlanCode = null;
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("P1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P1 = FValue.trim();
			}
			else
				P1 = null;
		}
		if (FCode.equalsIgnoreCase("P2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P2 = FValue.trim();
			}
			else
				P2 = null;
		}
		if (FCode.equalsIgnoreCase("P3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P3 = FValue.trim();
			}
			else
				P3 = null;
		}
		if (FCode.equalsIgnoreCase("P4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P4 = FValue.trim();
			}
			else
				P4 = null;
		}
		if (FCode.equalsIgnoreCase("P5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P5 = FValue.trim();
			}
			else
				P5 = null;
		}
		if (FCode.equalsIgnoreCase("P6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P6 = FValue.trim();
			}
			else
				P6 = null;
		}
		if (FCode.equalsIgnoreCase("P7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P7 = FValue.trim();
			}
			else
				P7 = null;
		}
		if (FCode.equalsIgnoreCase("P8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P8 = FValue.trim();
			}
			else
				P8 = null;
		}
		if (FCode.equalsIgnoreCase("P9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P9 = FValue.trim();
			}
			else
				P9 = null;
		}
		if (FCode.equalsIgnoreCase("P10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P10 = FValue.trim();
			}
			else
				P10 = null;
		}
		if (FCode.equalsIgnoreCase("P11"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P11 = FValue.trim();
			}
			else
				P11 = null;
		}
		if (FCode.equalsIgnoreCase("P12"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P12 = FValue.trim();
			}
			else
				P12 = null;
		}
		if (FCode.equalsIgnoreCase("P13"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P13 = FValue.trim();
			}
			else
				P13 = null;
		}
		if (FCode.equalsIgnoreCase("P14"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P14 = FValue.trim();
			}
			else
				P14 = null;
		}
		if (FCode.equalsIgnoreCase("P15"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P15 = FValue.trim();
			}
			else
				P15 = null;
		}
		if (FCode.equalsIgnoreCase("P16"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P16 = FValue.trim();
			}
			else
				P16 = null;
		}
		if (FCode.equalsIgnoreCase("P17"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P17 = FValue.trim();
			}
			else
				P17 = null;
		}
		if (FCode.equalsIgnoreCase("P18"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P18 = FValue.trim();
			}
			else
				P18 = null;
		}
		if (FCode.equalsIgnoreCase("P19"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P19 = FValue.trim();
			}
			else
				P19 = null;
		}
		if (FCode.equalsIgnoreCase("P20"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P20 = FValue.trim();
			}
			else
				P20 = null;
		}
		if (FCode.equalsIgnoreCase("P21"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P21 = FValue.trim();
			}
			else
				P21 = null;
		}
		if (FCode.equalsIgnoreCase("P22"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P22 = FValue.trim();
			}
			else
				P22 = null;
		}
		if (FCode.equalsIgnoreCase("P23"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P23 = FValue.trim();
			}
			else
				P23 = null;
		}
		if (FCode.equalsIgnoreCase("P24"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P24 = FValue.trim();
			}
			else
				P24 = null;
		}
		if (FCode.equalsIgnoreCase("P25"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P25 = FValue.trim();
			}
			else
				P25 = null;
		}
		if (FCode.equalsIgnoreCase("P26"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P26 = FValue.trim();
			}
			else
				P26 = null;
		}
		if (FCode.equalsIgnoreCase("P27"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P27 = FValue.trim();
			}
			else
				P27 = null;
		}
		if (FCode.equalsIgnoreCase("P28"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P28 = FValue.trim();
			}
			else
				P28 = null;
		}
		if (FCode.equalsIgnoreCase("P29"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P29 = FValue.trim();
			}
			else
				P29 = null;
		}
		if (FCode.equalsIgnoreCase("P30"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P30 = FValue.trim();
			}
			else
				P30 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LOBDutySubSchema other = (LOBDutySubSchema)otherObject;
		return
			BussType.equals(other.getBussType())
			&& BussNo.equals(other.getBussNo())
			&& ContNo.equals(other.getContNo())
			&& PolNo.equals(other.getPolNo())
			&& OtherNo.equals(other.getOtherNo())
			&& SysPlanCode.equals(other.getSysPlanCode())
			&& PlanCode.equals(other.getPlanCode())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& P1.equals(other.getP1())
			&& P2.equals(other.getP2())
			&& P3.equals(other.getP3())
			&& P4.equals(other.getP4())
			&& P5.equals(other.getP5())
			&& P6.equals(other.getP6())
			&& P7.equals(other.getP7())
			&& P8.equals(other.getP8())
			&& P9.equals(other.getP9())
			&& P10.equals(other.getP10())
			&& P11.equals(other.getP11())
			&& P12.equals(other.getP12())
			&& P13.equals(other.getP13())
			&& P14.equals(other.getP14())
			&& P15.equals(other.getP15())
			&& P16.equals(other.getP16())
			&& P17.equals(other.getP17())
			&& P18.equals(other.getP18())
			&& P19.equals(other.getP19())
			&& P20.equals(other.getP20())
			&& P21.equals(other.getP21())
			&& P22.equals(other.getP22())
			&& P23.equals(other.getP23())
			&& P24.equals(other.getP24())
			&& P25.equals(other.getP25())
			&& P26.equals(other.getP26())
			&& P27.equals(other.getP27())
			&& P28.equals(other.getP28())
			&& P29.equals(other.getP29())
			&& P30.equals(other.getP30());
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
		if( strFieldName.equals("BussType") ) {
			return 0;
		}
		if( strFieldName.equals("BussNo") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("PolNo") ) {
			return 3;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 4;
		}
		if( strFieldName.equals("SysPlanCode") ) {
			return 5;
		}
		if( strFieldName.equals("PlanCode") ) {
			return 6;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 7;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 8;
		}
		if( strFieldName.equals("P1") ) {
			return 9;
		}
		if( strFieldName.equals("P2") ) {
			return 10;
		}
		if( strFieldName.equals("P3") ) {
			return 11;
		}
		if( strFieldName.equals("P4") ) {
			return 12;
		}
		if( strFieldName.equals("P5") ) {
			return 13;
		}
		if( strFieldName.equals("P6") ) {
			return 14;
		}
		if( strFieldName.equals("P7") ) {
			return 15;
		}
		if( strFieldName.equals("P8") ) {
			return 16;
		}
		if( strFieldName.equals("P9") ) {
			return 17;
		}
		if( strFieldName.equals("P10") ) {
			return 18;
		}
		if( strFieldName.equals("P11") ) {
			return 19;
		}
		if( strFieldName.equals("P12") ) {
			return 20;
		}
		if( strFieldName.equals("P13") ) {
			return 21;
		}
		if( strFieldName.equals("P14") ) {
			return 22;
		}
		if( strFieldName.equals("P15") ) {
			return 23;
		}
		if( strFieldName.equals("P16") ) {
			return 24;
		}
		if( strFieldName.equals("P17") ) {
			return 25;
		}
		if( strFieldName.equals("P18") ) {
			return 26;
		}
		if( strFieldName.equals("P19") ) {
			return 27;
		}
		if( strFieldName.equals("P20") ) {
			return 28;
		}
		if( strFieldName.equals("P21") ) {
			return 29;
		}
		if( strFieldName.equals("P22") ) {
			return 30;
		}
		if( strFieldName.equals("P23") ) {
			return 31;
		}
		if( strFieldName.equals("P24") ) {
			return 32;
		}
		if( strFieldName.equals("P25") ) {
			return 33;
		}
		if( strFieldName.equals("P26") ) {
			return 34;
		}
		if( strFieldName.equals("P27") ) {
			return 35;
		}
		if( strFieldName.equals("P28") ) {
			return 36;
		}
		if( strFieldName.equals("P29") ) {
			return 37;
		}
		if( strFieldName.equals("P30") ) {
			return 38;
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
				strFieldName = "BussType";
				break;
			case 1:
				strFieldName = "BussNo";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "PolNo";
				break;
			case 4:
				strFieldName = "OtherNo";
				break;
			case 5:
				strFieldName = "SysPlanCode";
				break;
			case 6:
				strFieldName = "PlanCode";
				break;
			case 7:
				strFieldName = "RiskCode";
				break;
			case 8:
				strFieldName = "DutyCode";
				break;
			case 9:
				strFieldName = "P1";
				break;
			case 10:
				strFieldName = "P2";
				break;
			case 11:
				strFieldName = "P3";
				break;
			case 12:
				strFieldName = "P4";
				break;
			case 13:
				strFieldName = "P5";
				break;
			case 14:
				strFieldName = "P6";
				break;
			case 15:
				strFieldName = "P7";
				break;
			case 16:
				strFieldName = "P8";
				break;
			case 17:
				strFieldName = "P9";
				break;
			case 18:
				strFieldName = "P10";
				break;
			case 19:
				strFieldName = "P11";
				break;
			case 20:
				strFieldName = "P12";
				break;
			case 21:
				strFieldName = "P13";
				break;
			case 22:
				strFieldName = "P14";
				break;
			case 23:
				strFieldName = "P15";
				break;
			case 24:
				strFieldName = "P16";
				break;
			case 25:
				strFieldName = "P17";
				break;
			case 26:
				strFieldName = "P18";
				break;
			case 27:
				strFieldName = "P19";
				break;
			case 28:
				strFieldName = "P20";
				break;
			case 29:
				strFieldName = "P21";
				break;
			case 30:
				strFieldName = "P22";
				break;
			case 31:
				strFieldName = "P23";
				break;
			case 32:
				strFieldName = "P24";
				break;
			case 33:
				strFieldName = "P25";
				break;
			case 34:
				strFieldName = "P26";
				break;
			case 35:
				strFieldName = "P27";
				break;
			case 36:
				strFieldName = "P28";
				break;
			case 37:
				strFieldName = "P29";
				break;
			case 38:
				strFieldName = "P30";
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
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SysPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P11") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P12") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P13") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P14") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P15") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P16") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P17") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P18") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P19") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P20") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P21") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P22") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P23") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P24") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P25") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P26") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P27") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P28") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P29") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P30") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
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
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
