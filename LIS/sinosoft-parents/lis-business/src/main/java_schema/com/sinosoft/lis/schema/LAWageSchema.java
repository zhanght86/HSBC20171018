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
import com.sinosoft.lis.db.LAWageDB;

/*
 * <p>ClassName: LAWageSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAWageSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LAWageSchema.class);

	// @Field
	/** 指标计算编码 */
	private String IndexCalNo;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 管理机构 */
	private String ManageCom;
	/** 实发日期 */
	private Date GetDate;
	/** 实发金额 */
	private double SumMoney;
	/** 上期余额 */
	private double LastMoney;
	/** 本期应发金额 */
	private double CurrMoney;
	/** 创业扶持金/续收员岗位津贴 */
	private double F01;
	/** 创业金/续收员服务补贴 */
	private double F02;
	/** 转正奖/续收员续期收费服务奖金 */
	private double F03;
	/** 初年度佣金/续收员首年度营销展业服务奖金 */
	private double F04;
	/** 续年度佣金/续收员续年度营销服务奖金 */
	private double F05;
	/** 伯乐奖/续收员营销展业服务奖金 */
	private double F06;
	/** 个人年终奖/续收员复效奖金 */
	private double F07;
	/** 育才奖金/续收员个人年终奖金 */
	private double F08;
	/** 增员年终奖/续收员主任岗位月奖金 */
	private double F09;
	/** 见习业务经理特别津贴/续收员主任年终奖金 */
	private double F10;
	/** 职务津贴/临时续收员每单10元 */
	private double F11;
	/** 直辖组管理津贴/续收员当月达成服务奖金 */
	private double F12;
	/** 组育成津贴/续收员上上月累计达成服务奖金 */
	private double F13;
	/** 组年终奖金 */
	private double F14;
	/** 部直辖管理津贴 */
	private double F15;
	/** 增部津贴 */
	private double F16;
	/** 营销部年终奖金 */
	private double F17;
	/** 督导长职务底薪 */
	private double F18;
	/** 督导长育成津贴 */
	private double F19;
	/** 区域督导长职务底薪 */
	private double F20;
	/** 补发初年度佣金 */
	private double F21;
	/** 基薪/支助薪资 */
	private double F22;
	/** 客户经理绩效奖金/招募资金 */
	private double F23;
	/** 超额绩效奖金/创业奖 */
	private double F24;
	/** 本期余额 */
	private double F25;
	/** 差勤加扣款 */
	private double F26;
	/** 税前基薪加扣款 */
	private double F27;
	/** 税后基薪加扣款 */
	private double F28;
	/** 税前绩效加扣款 */
	private double F29;
	/** 加扣款 */
	private double F30;
	/** 营业税及附加 */
	private double K01;
	/** 个人所得税 */
	private double K02;
	/** 差勤扣款应扣 */
	private double K03;
	/** 个人养老保险 */
	private double K04;
	/** 个人失业保险 */
	private double K05;
	/** 个人医疗保险 */
	private double K06;
	/** 个人工伤保险 */
	private double K07;
	/** 个人住房公积金 */
	private double K08;
	/** 个人生育保险 */
	private double K09;
	/** 个人退休金 */
	private double K10;
	/** 奖惩款项 */
	private double K11;
	/** 衔接资金加扣款 */
	private double K12;
	/** 员工制基薪 */
	private double K13;
	/** 社保费用支持金 */
	private double K14;
	/** 员工制基本养老保险个人部分 */
	private double K15;
	/** 员工制基本养老保险公司部分 */
	private double K16;
	/** 员工制基本医疗保险个人部分 */
	private double K17;
	/** 员工制基本医疗保险公司部分 */
	private double K18;
	/** 员工制失业保险个人部分 */
	private double K19;
	/** 销费费用 */
	private double K20;
	/** 财务确认日期 */
	private Date ConfirmDate;
	/** 发放状态 */
	private String State;
	/** 发放方法 */
	private String PayMode;
	/** 操作员 */
	private String Operator;
	/** 复核人 */
	private String Operator2;
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
	/** 税前工资 */
	private double ShouldMoney;
	/** 展业机构外部编码 */
	private String BranchAttr;
	/** 代理人级别 */
	private String AgentGrade;
	/** 员工制失业保险公司部分 */
	private double W01;
	/** 员工制工伤保险个人部分 */
	private double W02;
	/** 员工制工伤保险公司部分 */
	private double W03;
	/** 员工制住房公积金个人部分 */
	private double W04;
	/** 员工制住房公积金公司部分 */
	private double W05;
	/** 员工制补充商业医疗保险 */
	private double W06;
	/** 员工制中介费 */
	private double W07;
	/** 员工制四险一金扶持金 */
	private double W08;
	/** 员工制加扣款 */
	private double W09;
	/** 备用佣金项10 */
	private double W10;
	/** 差勤扣款实扣 */
	private double K21;

	public static final int FIELDNUM = 82;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LAWageSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "IndexCalNo";
		pk[1] = "AgentCode";

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
		LAWageSchema cloned = (LAWageSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getIndexCalNo()
	{
		return IndexCalNo;
	}
	public void setIndexCalNo(String aIndexCalNo)
	{
		IndexCalNo = aIndexCalNo;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		AgentGroup = aAgentGroup;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getGetDate()
	{
		if( GetDate != null )
			return fDate.getString(GetDate);
		else
			return null;
	}
	public void setGetDate(Date aGetDate)
	{
		GetDate = aGetDate;
	}
	public void setGetDate(String aGetDate)
	{
		if (aGetDate != null && !aGetDate.equals("") )
		{
			GetDate = fDate.getDate( aGetDate );
		}
		else
			GetDate = null;
	}

	/**
	* 含上期节余的实际发放金额
	*/
	public double getSumMoney()
	{
		return SumMoney;
	}
	public void setSumMoney(double aSumMoney)
	{
		SumMoney = aSumMoney;
	}
	public void setSumMoney(String aSumMoney)
	{
		if (aSumMoney != null && !aSumMoney.equals(""))
		{
			Double tDouble = new Double(aSumMoney);
			double d = tDouble.doubleValue();
			SumMoney = d;
		}
	}

	/**
	* ＝上期的SumMoney
	*/
	public double getLastMoney()
	{
		return LastMoney;
	}
	public void setLastMoney(double aLastMoney)
	{
		LastMoney = aLastMoney;
	}
	public void setLastMoney(String aLastMoney)
	{
		if (aLastMoney != null && !aLastMoney.equals(""))
		{
			Double tDouble = new Double(aLastMoney);
			double d = tDouble.doubleValue();
			LastMoney = d;
		}
	}

	/**
	* 不含上期节余的本期合计<p>
	* <p>
	* <p>
	* //comment renhl<p>
	* //税前薪资
	*/
	public double getCurrMoney()
	{
		return CurrMoney;
	}
	public void setCurrMoney(double aCurrMoney)
	{
		CurrMoney = aCurrMoney;
	}
	public void setCurrMoney(String aCurrMoney)
	{
		if (aCurrMoney != null && !aCurrMoney.equals(""))
		{
			Double tDouble = new Double(aCurrMoney);
			double d = tDouble.doubleValue();
			CurrMoney = d;
		}
	}

	/**
	* 创业扶持金/续收员岗位津贴
	*/
	public double getF01()
	{
		return F01;
	}
	public void setF01(double aF01)
	{
		F01 = aF01;
	}
	public void setF01(String aF01)
	{
		if (aF01 != null && !aF01.equals(""))
		{
			Double tDouble = new Double(aF01);
			double d = tDouble.doubleValue();
			F01 = d;
		}
	}

	/**
	* 创业金/续收员服务补贴
	*/
	public double getF02()
	{
		return F02;
	}
	public void setF02(double aF02)
	{
		F02 = aF02;
	}
	public void setF02(String aF02)
	{
		if (aF02 != null && !aF02.equals(""))
		{
			Double tDouble = new Double(aF02);
			double d = tDouble.doubleValue();
			F02 = d;
		}
	}

	/**
	* 转正奖/续收员续期收费服务奖金
	*/
	public double getF03()
	{
		return F03;
	}
	public void setF03(double aF03)
	{
		F03 = aF03;
	}
	public void setF03(String aF03)
	{
		if (aF03 != null && !aF03.equals(""))
		{
			Double tDouble = new Double(aF03);
			double d = tDouble.doubleValue();
			F03 = d;
		}
	}

	/**
	* 初年度佣金/续收员首年度营销展业服务奖金
	*/
	public double getF04()
	{
		return F04;
	}
	public void setF04(double aF04)
	{
		F04 = aF04;
	}
	public void setF04(String aF04)
	{
		if (aF04 != null && !aF04.equals(""))
		{
			Double tDouble = new Double(aF04);
			double d = tDouble.doubleValue();
			F04 = d;
		}
	}

	/**
	* 续年度佣金/续收员续年度营销服务奖金
	*/
	public double getF05()
	{
		return F05;
	}
	public void setF05(double aF05)
	{
		F05 = aF05;
	}
	public void setF05(String aF05)
	{
		if (aF05 != null && !aF05.equals(""))
		{
			Double tDouble = new Double(aF05);
			double d = tDouble.doubleValue();
			F05 = d;
		}
	}

	/**
	* 伯乐奖/续收员营销展业服务奖金<p>
	* <p>
	* //comment renhl<p>
	* //全勤奖
	*/
	public double getF06()
	{
		return F06;
	}
	public void setF06(double aF06)
	{
		F06 = aF06;
	}
	public void setF06(String aF06)
	{
		if (aF06 != null && !aF06.equals(""))
		{
			Double tDouble = new Double(aF06);
			double d = tDouble.doubleValue();
			F06 = d;
		}
	}

	/**
	* 个人年终奖/续收员复效奖金
	*/
	public double getF07()
	{
		return F07;
	}
	public void setF07(double aF07)
	{
		F07 = aF07;
	}
	public void setF07(String aF07)
	{
		if (aF07 != null && !aF07.equals(""))
		{
			Double tDouble = new Double(aF07);
			double d = tDouble.doubleValue();
			F07 = d;
		}
	}

	/**
	* 育才奖金/续收员个人年终奖金<p>
	* <p>
	* <p>
	* //comment renhl<p>
	* //月度销售奖
	*/
	public double getF08()
	{
		return F08;
	}
	public void setF08(double aF08)
	{
		F08 = aF08;
	}
	public void setF08(String aF08)
	{
		if (aF08 != null && !aF08.equals(""))
		{
			Double tDouble = new Double(aF08);
			double d = tDouble.doubleValue();
			F08 = d;
		}
	}

	/**
	* 增员年终奖/续收员主任岗位月奖金<p>
	* <p>
	* //comment renhl<p>
	* //增员辅导津贴
	*/
	public double getF09()
	{
		return F09;
	}
	public void setF09(double aF09)
	{
		F09 = aF09;
	}
	public void setF09(String aF09)
	{
		if (aF09 != null && !aF09.equals(""))
		{
			Double tDouble = new Double(aF09);
			double d = tDouble.doubleValue();
			F09 = d;
		}
	}

	/**
	* 见习业务经理特别津贴/续收员主任年终奖金<p>
	* <p>
	* <p>
	* //comment renhl<p>
	* //继续率奖
	*/
	public double getF10()
	{
		return F10;
	}
	public void setF10(double aF10)
	{
		F10 = aF10;
	}
	public void setF10(String aF10)
	{
		if (aF10 != null && !aF10.equals(""))
		{
			Double tDouble = new Double(aF10);
			double d = tDouble.doubleValue();
			F10 = d;
		}
	}

	/**
	* 职务津贴/临时续收员每单10元<p>
	* <p>
	* //comment renhl<p>
	* //个人突出贡献奖
	*/
	public double getF11()
	{
		return F11;
	}
	public void setF11(double aF11)
	{
		F11 = aF11;
	}
	public void setF11(String aF11)
	{
		if (aF11 != null && !aF11.equals(""))
		{
			Double tDouble = new Double(aF11);
			double d = tDouble.doubleValue();
			F11 = d;
		}
	}

	/**
	* 直辖组管理津贴/续收员当月达成服务奖金<p>
	* <p>
	* //comment renhl<p>
	* //永续经营奖
	*/
	public double getF12()
	{
		return F12;
	}
	public void setF12(double aF12)
	{
		F12 = aF12;
	}
	public void setF12(String aF12)
	{
		if (aF12 != null && !aF12.equals(""))
		{
			Double tDouble = new Double(aF12);
			double d = tDouble.doubleValue();
			F12 = d;
		}
	}

	/**
	* 组育成津贴/续收员上上月累计达成服务奖金<p>
	* <p>
	* //comment renhl<p>
	* //组职务底薪
	*/
	public double getF13()
	{
		return F13;
	}
	public void setF13(double aF13)
	{
		F13 = aF13;
	}
	public void setF13(String aF13)
	{
		if (aF13 != null && !aF13.equals(""))
		{
			Double tDouble = new Double(aF13);
			double d = tDouble.doubleValue();
			F13 = d;
		}
	}

	/**
	* 组年终奖金<p>
	* <p>
	* //comment renhl<p>
	* //部职务底薪
	*/
	public double getF14()
	{
		return F14;
	}
	public void setF14(double aF14)
	{
		F14 = aF14;
	}
	public void setF14(String aF14)
	{
		if (aF14 != null && !aF14.equals(""))
		{
			Double tDouble = new Double(aF14);
			double d = tDouble.doubleValue();
			F14 = d;
		}
	}

	/**
	* 部直辖管理津贴<p>
	* <p>
	* <p>
	* //comment renhl<p>
	* //区职务底薪
	*/
	public double getF15()
	{
		return F15;
	}
	public void setF15(double aF15)
	{
		F15 = aF15;
	}
	public void setF15(String aF15)
	{
		if (aF15 != null && !aF15.equals(""))
		{
			Double tDouble = new Double(aF15);
			double d = tDouble.doubleValue();
			F15 = d;
		}
	}

	/**
	* 增部津贴<p>
	* <p>
	* <p>
	* //comment renhl<p>
	* //总监职务底薪
	*/
	public double getF16()
	{
		return F16;
	}
	public void setF16(double aF16)
	{
		F16 = aF16;
	}
	public void setF16(String aF16)
	{
		if (aF16 != null && !aF16.equals(""))
		{
			Double tDouble = new Double(aF16);
			double d = tDouble.doubleValue();
			F16 = d;
		}
	}

	/**
	* 营销部年终奖金<p>
	* <p>
	* //comment renhl<p>
	* //客户服务奖
	*/
	public double getF17()
	{
		return F17;
	}
	public void setF17(double aF17)
	{
		F17 = aF17;
	}
	public void setF17(String aF17)
	{
		if (aF17 != null && !aF17.equals(""))
		{
			Double tDouble = new Double(aF17);
			double d = tDouble.doubleValue();
			F17 = d;
		}
	}

	/**
	* 督导长职务底薪<p>
	* <p>
	* //comment renhl<p>
	* //加扣款5
	*/
	public double getF18()
	{
		return F18;
	}
	public void setF18(double aF18)
	{
		F18 = aF18;
	}
	public void setF18(String aF18)
	{
		if (aF18 != null && !aF18.equals(""))
		{
			Double tDouble = new Double(aF18);
			double d = tDouble.doubleValue();
			F18 = d;
		}
	}

	/**
	* 督导长育成津贴
	*/
	public double getF19()
	{
		return F19;
	}
	public void setF19(double aF19)
	{
		F19 = aF19;
	}
	public void setF19(String aF19)
	{
		if (aF19 != null && !aF19.equals(""))
		{
			Double tDouble = new Double(aF19);
			double d = tDouble.doubleValue();
			F19 = d;
		}
	}

	/**
	* 区域督导长职务底薪
	*/
	public double getF20()
	{
		return F20;
	}
	public void setF20(double aF20)
	{
		F20 = aF20;
	}
	public void setF20(String aF20)
	{
		if (aF20 != null && !aF20.equals(""))
		{
			Double tDouble = new Double(aF20);
			double d = tDouble.doubleValue();
			F20 = d;
		}
	}

	/**
	* 补发初年度佣金
	*/
	public double getF21()
	{
		return F21;
	}
	public void setF21(double aF21)
	{
		F21 = aF21;
	}
	public void setF21(String aF21)
	{
		if (aF21 != null && !aF21.equals(""))
		{
			Double tDouble = new Double(aF21);
			double d = tDouble.doubleValue();
			F21 = d;
		}
	}

	/**
	* 基薪/支助薪资
	*/
	public double getF22()
	{
		return F22;
	}
	public void setF22(double aF22)
	{
		F22 = aF22;
	}
	public void setF22(String aF22)
	{
		if (aF22 != null && !aF22.equals(""))
		{
			Double tDouble = new Double(aF22);
			double d = tDouble.doubleValue();
			F22 = d;
		}
	}

	/**
	* 客户经理绩效奖金/招募资金
	*/
	public double getF23()
	{
		return F23;
	}
	public void setF23(double aF23)
	{
		F23 = aF23;
	}
	public void setF23(String aF23)
	{
		if (aF23 != null && !aF23.equals(""))
		{
			Double tDouble = new Double(aF23);
			double d = tDouble.doubleValue();
			F23 = d;
		}
	}

	/**
	* 超额绩效奖金/创业奖
	*/
	public double getF24()
	{
		return F24;
	}
	public void setF24(double aF24)
	{
		F24 = aF24;
	}
	public void setF24(String aF24)
	{
		if (aF24 != null && !aF24.equals(""))
		{
			Double tDouble = new Double(aF24);
			double d = tDouble.doubleValue();
			F24 = d;
		}
	}

	/**
	* 本期余额
	*/
	public double getF25()
	{
		return F25;
	}
	public void setF25(double aF25)
	{
		F25 = aF25;
	}
	public void setF25(String aF25)
	{
		if (aF25 != null && !aF25.equals(""))
		{
			Double tDouble = new Double(aF25);
			double d = tDouble.doubleValue();
			F25 = d;
		}
	}

	/**
	* 差勤加扣款
	*/
	public double getF26()
	{
		return F26;
	}
	public void setF26(double aF26)
	{
		F26 = aF26;
	}
	public void setF26(String aF26)
	{
		if (aF26 != null && !aF26.equals(""))
		{
			Double tDouble = new Double(aF26);
			double d = tDouble.doubleValue();
			F26 = d;
		}
	}

	/**
	* 税前基薪加扣款
	*/
	public double getF27()
	{
		return F27;
	}
	public void setF27(double aF27)
	{
		F27 = aF27;
	}
	public void setF27(String aF27)
	{
		if (aF27 != null && !aF27.equals(""))
		{
			Double tDouble = new Double(aF27);
			double d = tDouble.doubleValue();
			F27 = d;
		}
	}

	/**
	* 税后基薪加扣款
	*/
	public double getF28()
	{
		return F28;
	}
	public void setF28(double aF28)
	{
		F28 = aF28;
	}
	public void setF28(String aF28)
	{
		if (aF28 != null && !aF28.equals(""))
		{
			Double tDouble = new Double(aF28);
			double d = tDouble.doubleValue();
			F28 = d;
		}
	}

	/**
	* 税前绩效加扣款
	*/
	public double getF29()
	{
		return F29;
	}
	public void setF29(double aF29)
	{
		F29 = aF29;
	}
	public void setF29(String aF29)
	{
		if (aF29 != null && !aF29.equals(""))
		{
			Double tDouble = new Double(aF29);
			double d = tDouble.doubleValue();
			F29 = d;
		}
	}

	/**
	* 加扣款<p>
	* <p>
	* <p>
	* //comment renhl<p>
	* //加扣款１
	*/
	public double getF30()
	{
		return F30;
	}
	public void setF30(double aF30)
	{
		F30 = aF30;
	}
	public void setF30(String aF30)
	{
		if (aF30 != null && !aF30.equals(""))
		{
			Double tDouble = new Double(aF30);
			double d = tDouble.doubleValue();
			F30 = d;
		}
	}

	/**
	* 营业税及附加
	*/
	public double getK01()
	{
		return K01;
	}
	public void setK01(double aK01)
	{
		K01 = aK01;
	}
	public void setK01(String aK01)
	{
		if (aK01 != null && !aK01.equals(""))
		{
			Double tDouble = new Double(aK01);
			double d = tDouble.doubleValue();
			K01 = d;
		}
	}

	/**
	* 个人所得税
	*/
	public double getK02()
	{
		return K02;
	}
	public void setK02(double aK02)
	{
		K02 = aK02;
	}
	public void setK02(String aK02)
	{
		if (aK02 != null && !aK02.equals(""))
		{
			Double tDouble = new Double(aK02);
			double d = tDouble.doubleValue();
			K02 = d;
		}
	}

	/**
	* 差勤扣款
	*/
	public double getK03()
	{
		return K03;
	}
	public void setK03(double aK03)
	{
		K03 = aK03;
	}
	public void setK03(String aK03)
	{
		if (aK03 != null && !aK03.equals(""))
		{
			Double tDouble = new Double(aK03);
			double d = tDouble.doubleValue();
			K03 = d;
		}
	}

	/**
	* 个人养老保险
	*/
	public double getK04()
	{
		return K04;
	}
	public void setK04(double aK04)
	{
		K04 = aK04;
	}
	public void setK04(String aK04)
	{
		if (aK04 != null && !aK04.equals(""))
		{
			Double tDouble = new Double(aK04);
			double d = tDouble.doubleValue();
			K04 = d;
		}
	}

	/**
	* 个人失业保险
	*/
	public double getK05()
	{
		return K05;
	}
	public void setK05(double aK05)
	{
		K05 = aK05;
	}
	public void setK05(String aK05)
	{
		if (aK05 != null && !aK05.equals(""))
		{
			Double tDouble = new Double(aK05);
			double d = tDouble.doubleValue();
			K05 = d;
		}
	}

	/**
	* 个人医疗保险
	*/
	public double getK06()
	{
		return K06;
	}
	public void setK06(double aK06)
	{
		K06 = aK06;
	}
	public void setK06(String aK06)
	{
		if (aK06 != null && !aK06.equals(""))
		{
			Double tDouble = new Double(aK06);
			double d = tDouble.doubleValue();
			K06 = d;
		}
	}

	/**
	* 个人工伤保险
	*/
	public double getK07()
	{
		return K07;
	}
	public void setK07(double aK07)
	{
		K07 = aK07;
	}
	public void setK07(String aK07)
	{
		if (aK07 != null && !aK07.equals(""))
		{
			Double tDouble = new Double(aK07);
			double d = tDouble.doubleValue();
			K07 = d;
		}
	}

	/**
	* 个人住房公积金
	*/
	public double getK08()
	{
		return K08;
	}
	public void setK08(double aK08)
	{
		K08 = aK08;
	}
	public void setK08(String aK08)
	{
		if (aK08 != null && !aK08.equals(""))
		{
			Double tDouble = new Double(aK08);
			double d = tDouble.doubleValue();
			K08 = d;
		}
	}

	/**
	* 个人生育保险
	*/
	public double getK09()
	{
		return K09;
	}
	public void setK09(double aK09)
	{
		K09 = aK09;
	}
	public void setK09(String aK09)
	{
		if (aK09 != null && !aK09.equals(""))
		{
			Double tDouble = new Double(aK09);
			double d = tDouble.doubleValue();
			K09 = d;
		}
	}

	/**
	* 个人退休金
	*/
	public double getK10()
	{
		return K10;
	}
	public void setK10(double aK10)
	{
		K10 = aK10;
	}
	public void setK10(String aK10)
	{
		if (aK10 != null && !aK10.equals(""))
		{
			Double tDouble = new Double(aK10);
			double d = tDouble.doubleValue();
			K10 = d;
		}
	}

	/**
	* 奖惩款项
	*/
	public double getK11()
	{
		return K11;
	}
	public void setK11(double aK11)
	{
		K11 = aK11;
	}
	public void setK11(String aK11)
	{
		if (aK11 != null && !aK11.equals(""))
		{
			Double tDouble = new Double(aK11);
			double d = tDouble.doubleValue();
			K11 = d;
		}
	}

	/**
	* 衔接资金加扣款<p>
	* <p>
	* <p>
	* //comment renhl<p>
	* //同业衔接加扣款或加扣款２
	*/
	public double getK12()
	{
		return K12;
	}
	public void setK12(double aK12)
	{
		K12 = aK12;
	}
	public void setK12(String aK12)
	{
		if (aK12 != null && !aK12.equals(""))
		{
			Double tDouble = new Double(aK12);
			double d = tDouble.doubleValue();
			K12 = d;
		}
	}

	/**
	* 员工制基薪
	*/
	public double getK13()
	{
		return K13;
	}
	public void setK13(double aK13)
	{
		K13 = aK13;
	}
	public void setK13(String aK13)
	{
		if (aK13 != null && !aK13.equals(""))
		{
			Double tDouble = new Double(aK13);
			double d = tDouble.doubleValue();
			K13 = d;
		}
	}

	/**
	* 社保费用支持金
	*/
	public double getK14()
	{
		return K14;
	}
	public void setK14(double aK14)
	{
		K14 = aK14;
	}
	public void setK14(String aK14)
	{
		if (aK14 != null && !aK14.equals(""))
		{
			Double tDouble = new Double(aK14);
			double d = tDouble.doubleValue();
			K14 = d;
		}
	}

	/**
	* 员工制基本养老保险个人部分
	*/
	public double getK15()
	{
		return K15;
	}
	public void setK15(double aK15)
	{
		K15 = aK15;
	}
	public void setK15(String aK15)
	{
		if (aK15 != null && !aK15.equals(""))
		{
			Double tDouble = new Double(aK15);
			double d = tDouble.doubleValue();
			K15 = d;
		}
	}

	/**
	* 员工制基本养老保险公司部分
	*/
	public double getK16()
	{
		return K16;
	}
	public void setK16(double aK16)
	{
		K16 = aK16;
	}
	public void setK16(String aK16)
	{
		if (aK16 != null && !aK16.equals(""))
		{
			Double tDouble = new Double(aK16);
			double d = tDouble.doubleValue();
			K16 = d;
		}
	}

	/**
	* 员工制基本医疗保险个人部分
	*/
	public double getK17()
	{
		return K17;
	}
	public void setK17(double aK17)
	{
		K17 = aK17;
	}
	public void setK17(String aK17)
	{
		if (aK17 != null && !aK17.equals(""))
		{
			Double tDouble = new Double(aK17);
			double d = tDouble.doubleValue();
			K17 = d;
		}
	}

	/**
	* 员工制基本医疗保险公司部分
	*/
	public double getK18()
	{
		return K18;
	}
	public void setK18(double aK18)
	{
		K18 = aK18;
	}
	public void setK18(String aK18)
	{
		if (aK18 != null && !aK18.equals(""))
		{
			Double tDouble = new Double(aK18);
			double d = tDouble.doubleValue();
			K18 = d;
		}
	}

	/**
	* 员工制失业保险个人部分
	*/
	public double getK19()
	{
		return K19;
	}
	public void setK19(double aK19)
	{
		K19 = aK19;
	}
	public void setK19(String aK19)
	{
		if (aK19 != null && !aK19.equals(""))
		{
			Double tDouble = new Double(aK19);
			double d = tDouble.doubleValue();
			K19 = d;
		}
	}

	/**
	* 销费费用
	*/
	public double getK20()
	{
		return K20;
	}
	public void setK20(double aK20)
	{
		K20 = aK20;
	}
	public void setK20(String aK20)
	{
		if (aK20 != null && !aK20.equals(""))
		{
			Double tDouble = new Double(aK20);
			double d = tDouble.doubleValue();
			K20 = d;
		}
	}

	public String getConfirmDate()
	{
		if( ConfirmDate != null )
			return fDate.getString(ConfirmDate);
		else
			return null;
	}
	public void setConfirmDate(Date aConfirmDate)
	{
		ConfirmDate = aConfirmDate;
	}
	public void setConfirmDate(String aConfirmDate)
	{
		if (aConfirmDate != null && !aConfirmDate.equals("") )
		{
			ConfirmDate = fDate.getDate( aConfirmDate );
		}
		else
			ConfirmDate = null;
	}

	/**
	* 0-未发放；1-已发放 2-财务已确认
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getPayMode()
	{
		return PayMode;
	}
	public void setPayMode(String aPayMode)
	{
		PayMode = aPayMode;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getOperator2()
	{
		return Operator2;
	}
	public void setOperator2(String aOperator2)
	{
		Operator2 = aOperator2;
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
	public double getShouldMoney()
	{
		return ShouldMoney;
	}
	public void setShouldMoney(double aShouldMoney)
	{
		ShouldMoney = aShouldMoney;
	}
	public void setShouldMoney(String aShouldMoney)
	{
		if (aShouldMoney != null && !aShouldMoney.equals(""))
		{
			Double tDouble = new Double(aShouldMoney);
			double d = tDouble.doubleValue();
			ShouldMoney = d;
		}
	}

	public String getBranchAttr()
	{
		return BranchAttr;
	}
	public void setBranchAttr(String aBranchAttr)
	{
		BranchAttr = aBranchAttr;
	}
	/**
	* A01:理财专员<p>
	* A02:理财主任<p>
	* A03：见习业务经理<p>
	* <p>
	* A04：业务经理一级<p>
	* <p>
	* A05:业务经理二级<p>
	* A06:高级业务经理一级<p>
	* <p>
	* A07:高级业务经理二级<p>
	* A08:督导长<p>
	* <p>
	* A09:区域督导长<p>
	* <p>
	* "
	*/
	public String getAgentGrade()
	{
		return AgentGrade;
	}
	public void setAgentGrade(String aAgentGrade)
	{
		AgentGrade = aAgentGrade;
	}
	/**
	* 员工制失业保险公司部分
	*/
	public double getW01()
	{
		return W01;
	}
	public void setW01(double aW01)
	{
		W01 = aW01;
	}
	public void setW01(String aW01)
	{
		if (aW01 != null && !aW01.equals(""))
		{
			Double tDouble = new Double(aW01);
			double d = tDouble.doubleValue();
			W01 = d;
		}
	}

	/**
	* 员工制工伤保险个人部分
	*/
	public double getW02()
	{
		return W02;
	}
	public void setW02(double aW02)
	{
		W02 = aW02;
	}
	public void setW02(String aW02)
	{
		if (aW02 != null && !aW02.equals(""))
		{
			Double tDouble = new Double(aW02);
			double d = tDouble.doubleValue();
			W02 = d;
		}
	}

	/**
	* 员工制工伤保险公司部分
	*/
	public double getW03()
	{
		return W03;
	}
	public void setW03(double aW03)
	{
		W03 = aW03;
	}
	public void setW03(String aW03)
	{
		if (aW03 != null && !aW03.equals(""))
		{
			Double tDouble = new Double(aW03);
			double d = tDouble.doubleValue();
			W03 = d;
		}
	}

	/**
	* 员工制住房公积金个人部分
	*/
	public double getW04()
	{
		return W04;
	}
	public void setW04(double aW04)
	{
		W04 = aW04;
	}
	public void setW04(String aW04)
	{
		if (aW04 != null && !aW04.equals(""))
		{
			Double tDouble = new Double(aW04);
			double d = tDouble.doubleValue();
			W04 = d;
		}
	}

	/**
	* 员工制住房公积金公司部分
	*/
	public double getW05()
	{
		return W05;
	}
	public void setW05(double aW05)
	{
		W05 = aW05;
	}
	public void setW05(String aW05)
	{
		if (aW05 != null && !aW05.equals(""))
		{
			Double tDouble = new Double(aW05);
			double d = tDouble.doubleValue();
			W05 = d;
		}
	}

	/**
	* 员工制补充商业医疗保险
	*/
	public double getW06()
	{
		return W06;
	}
	public void setW06(double aW06)
	{
		W06 = aW06;
	}
	public void setW06(String aW06)
	{
		if (aW06 != null && !aW06.equals(""))
		{
			Double tDouble = new Double(aW06);
			double d = tDouble.doubleValue();
			W06 = d;
		}
	}

	/**
	* 员工制中介费
	*/
	public double getW07()
	{
		return W07;
	}
	public void setW07(double aW07)
	{
		W07 = aW07;
	}
	public void setW07(String aW07)
	{
		if (aW07 != null && !aW07.equals(""))
		{
			Double tDouble = new Double(aW07);
			double d = tDouble.doubleValue();
			W07 = d;
		}
	}

	/**
	* 员工制四险一金扶持金
	*/
	public double getW08()
	{
		return W08;
	}
	public void setW08(double aW08)
	{
		W08 = aW08;
	}
	public void setW08(String aW08)
	{
		if (aW08 != null && !aW08.equals(""))
		{
			Double tDouble = new Double(aW08);
			double d = tDouble.doubleValue();
			W08 = d;
		}
	}

	/**
	* 员工制加扣款
	*/
	public double getW09()
	{
		return W09;
	}
	public void setW09(double aW09)
	{
		W09 = aW09;
	}
	public void setW09(String aW09)
	{
		if (aW09 != null && !aW09.equals(""))
		{
			Double tDouble = new Double(aW09);
			double d = tDouble.doubleValue();
			W09 = d;
		}
	}

	/**
	* 业务拓展津贴
	*/
	public double getW10()
	{
		return W10;
	}
	public void setW10(double aW10)
	{
		W10 = aW10;
	}
	public void setW10(String aW10)
	{
		if (aW10 != null && !aW10.equals(""))
		{
			Double tDouble = new Double(aW10);
			double d = tDouble.doubleValue();
			W10 = d;
		}
	}

	public double getK21()
	{
		return K21;
	}
	public void setK21(double aK21)
	{
		K21 = aK21;
	}
	public void setK21(String aK21)
	{
		if (aK21 != null && !aK21.equals(""))
		{
			Double tDouble = new Double(aK21);
			double d = tDouble.doubleValue();
			K21 = d;
		}
	}


	/**
	* 使用另外一个 LAWageSchema 对象给 Schema 赋值
	* @param: aLAWageSchema LAWageSchema
	**/
	public void setSchema(LAWageSchema aLAWageSchema)
	{
		this.IndexCalNo = aLAWageSchema.getIndexCalNo();
		this.AgentCode = aLAWageSchema.getAgentCode();
		this.AgentGroup = aLAWageSchema.getAgentGroup();
		this.ManageCom = aLAWageSchema.getManageCom();
		this.GetDate = fDate.getDate( aLAWageSchema.getGetDate());
		this.SumMoney = aLAWageSchema.getSumMoney();
		this.LastMoney = aLAWageSchema.getLastMoney();
		this.CurrMoney = aLAWageSchema.getCurrMoney();
		this.F01 = aLAWageSchema.getF01();
		this.F02 = aLAWageSchema.getF02();
		this.F03 = aLAWageSchema.getF03();
		this.F04 = aLAWageSchema.getF04();
		this.F05 = aLAWageSchema.getF05();
		this.F06 = aLAWageSchema.getF06();
		this.F07 = aLAWageSchema.getF07();
		this.F08 = aLAWageSchema.getF08();
		this.F09 = aLAWageSchema.getF09();
		this.F10 = aLAWageSchema.getF10();
		this.F11 = aLAWageSchema.getF11();
		this.F12 = aLAWageSchema.getF12();
		this.F13 = aLAWageSchema.getF13();
		this.F14 = aLAWageSchema.getF14();
		this.F15 = aLAWageSchema.getF15();
		this.F16 = aLAWageSchema.getF16();
		this.F17 = aLAWageSchema.getF17();
		this.F18 = aLAWageSchema.getF18();
		this.F19 = aLAWageSchema.getF19();
		this.F20 = aLAWageSchema.getF20();
		this.F21 = aLAWageSchema.getF21();
		this.F22 = aLAWageSchema.getF22();
		this.F23 = aLAWageSchema.getF23();
		this.F24 = aLAWageSchema.getF24();
		this.F25 = aLAWageSchema.getF25();
		this.F26 = aLAWageSchema.getF26();
		this.F27 = aLAWageSchema.getF27();
		this.F28 = aLAWageSchema.getF28();
		this.F29 = aLAWageSchema.getF29();
		this.F30 = aLAWageSchema.getF30();
		this.K01 = aLAWageSchema.getK01();
		this.K02 = aLAWageSchema.getK02();
		this.K03 = aLAWageSchema.getK03();
		this.K04 = aLAWageSchema.getK04();
		this.K05 = aLAWageSchema.getK05();
		this.K06 = aLAWageSchema.getK06();
		this.K07 = aLAWageSchema.getK07();
		this.K08 = aLAWageSchema.getK08();
		this.K09 = aLAWageSchema.getK09();
		this.K10 = aLAWageSchema.getK10();
		this.K11 = aLAWageSchema.getK11();
		this.K12 = aLAWageSchema.getK12();
		this.K13 = aLAWageSchema.getK13();
		this.K14 = aLAWageSchema.getK14();
		this.K15 = aLAWageSchema.getK15();
		this.K16 = aLAWageSchema.getK16();
		this.K17 = aLAWageSchema.getK17();
		this.K18 = aLAWageSchema.getK18();
		this.K19 = aLAWageSchema.getK19();
		this.K20 = aLAWageSchema.getK20();
		this.ConfirmDate = fDate.getDate( aLAWageSchema.getConfirmDate());
		this.State = aLAWageSchema.getState();
		this.PayMode = aLAWageSchema.getPayMode();
		this.Operator = aLAWageSchema.getOperator();
		this.Operator2 = aLAWageSchema.getOperator2();
		this.MakeDate = fDate.getDate( aLAWageSchema.getMakeDate());
		this.MakeTime = aLAWageSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLAWageSchema.getModifyDate());
		this.ModifyTime = aLAWageSchema.getModifyTime();
		this.BranchType = aLAWageSchema.getBranchType();
		this.ShouldMoney = aLAWageSchema.getShouldMoney();
		this.BranchAttr = aLAWageSchema.getBranchAttr();
		this.AgentGrade = aLAWageSchema.getAgentGrade();
		this.W01 = aLAWageSchema.getW01();
		this.W02 = aLAWageSchema.getW02();
		this.W03 = aLAWageSchema.getW03();
		this.W04 = aLAWageSchema.getW04();
		this.W05 = aLAWageSchema.getW05();
		this.W06 = aLAWageSchema.getW06();
		this.W07 = aLAWageSchema.getW07();
		this.W08 = aLAWageSchema.getW08();
		this.W09 = aLAWageSchema.getW09();
		this.W10 = aLAWageSchema.getW10();
		this.K21 = aLAWageSchema.getK21();
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
			if( rs.getString("IndexCalNo") == null )
				this.IndexCalNo = null;
			else
				this.IndexCalNo = rs.getString("IndexCalNo").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.GetDate = rs.getDate("GetDate");
			this.SumMoney = rs.getDouble("SumMoney");
			this.LastMoney = rs.getDouble("LastMoney");
			this.CurrMoney = rs.getDouble("CurrMoney");
			this.F01 = rs.getDouble("F01");
			this.F02 = rs.getDouble("F02");
			this.F03 = rs.getDouble("F03");
			this.F04 = rs.getDouble("F04");
			this.F05 = rs.getDouble("F05");
			this.F06 = rs.getDouble("F06");
			this.F07 = rs.getDouble("F07");
			this.F08 = rs.getDouble("F08");
			this.F09 = rs.getDouble("F09");
			this.F10 = rs.getDouble("F10");
			this.F11 = rs.getDouble("F11");
			this.F12 = rs.getDouble("F12");
			this.F13 = rs.getDouble("F13");
			this.F14 = rs.getDouble("F14");
			this.F15 = rs.getDouble("F15");
			this.F16 = rs.getDouble("F16");
			this.F17 = rs.getDouble("F17");
			this.F18 = rs.getDouble("F18");
			this.F19 = rs.getDouble("F19");
			this.F20 = rs.getDouble("F20");
			this.F21 = rs.getDouble("F21");
			this.F22 = rs.getDouble("F22");
			this.F23 = rs.getDouble("F23");
			this.F24 = rs.getDouble("F24");
			this.F25 = rs.getDouble("F25");
			this.F26 = rs.getDouble("F26");
			this.F27 = rs.getDouble("F27");
			this.F28 = rs.getDouble("F28");
			this.F29 = rs.getDouble("F29");
			this.F30 = rs.getDouble("F30");
			this.K01 = rs.getDouble("K01");
			this.K02 = rs.getDouble("K02");
			this.K03 = rs.getDouble("K03");
			this.K04 = rs.getDouble("K04");
			this.K05 = rs.getDouble("K05");
			this.K06 = rs.getDouble("K06");
			this.K07 = rs.getDouble("K07");
			this.K08 = rs.getDouble("K08");
			this.K09 = rs.getDouble("K09");
			this.K10 = rs.getDouble("K10");
			this.K11 = rs.getDouble("K11");
			this.K12 = rs.getDouble("K12");
			this.K13 = rs.getDouble("K13");
			this.K14 = rs.getDouble("K14");
			this.K15 = rs.getDouble("K15");
			this.K16 = rs.getDouble("K16");
			this.K17 = rs.getDouble("K17");
			this.K18 = rs.getDouble("K18");
			this.K19 = rs.getDouble("K19");
			this.K20 = rs.getDouble("K20");
			this.ConfirmDate = rs.getDate("ConfirmDate");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("Operator2") == null )
				this.Operator2 = null;
			else
				this.Operator2 = rs.getString("Operator2").trim();

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

			this.ShouldMoney = rs.getDouble("ShouldMoney");
			if( rs.getString("BranchAttr") == null )
				this.BranchAttr = null;
			else
				this.BranchAttr = rs.getString("BranchAttr").trim();

			if( rs.getString("AgentGrade") == null )
				this.AgentGrade = null;
			else
				this.AgentGrade = rs.getString("AgentGrade").trim();

			this.W01 = rs.getDouble("W01");
			this.W02 = rs.getDouble("W02");
			this.W03 = rs.getDouble("W03");
			this.W04 = rs.getDouble("W04");
			this.W05 = rs.getDouble("W05");
			this.W06 = rs.getDouble("W06");
			this.W07 = rs.getDouble("W07");
			this.W08 = rs.getDouble("W08");
			this.W09 = rs.getDouble("W09");
			this.W10 = rs.getDouble("W10");
			this.K21 = rs.getDouble("K21");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LAWage表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAWageSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LAWageSchema getSchema()
	{
		LAWageSchema aLAWageSchema = new LAWageSchema();
		aLAWageSchema.setSchema(this);
		return aLAWageSchema;
	}

	public LAWageDB getDB()
	{
		LAWageDB aDBOper = new LAWageDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAWage描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(IndexCalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LastMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurrMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F01));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F02));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F03));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F04));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F05));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F06));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F07));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F08));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F09));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F10));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F11));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F12));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F13));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F14));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F15));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F16));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F17));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F18));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F19));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F20));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F21));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F22));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F23));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F24));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F25));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F26));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F27));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F28));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F29));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(F30));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K01));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K02));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K03));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K04));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K05));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K06));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K07));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K08));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K09));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K10));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K11));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K12));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K13));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K14));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K15));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K16));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K17));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K18));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K19));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K20));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfirmDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ShouldMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchAttr)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(W01));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(W02));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(W03));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(W04));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(W05));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(W06));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(W07));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(W08));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(W09));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(W10));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K21));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAWage>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			IndexCalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			SumMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			LastMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			CurrMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			F01 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			F02 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			F03 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			F04 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			F05 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			F06 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			F07 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			F08 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			F09 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			F10 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			F11 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			F12 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			F13 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			F14 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			F15 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			F16 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			F17 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			F18 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			F19 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			F20 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).doubleValue();
			F21 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			F22 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
			F23 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).doubleValue();
			F24 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).doubleValue();
			F25 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).doubleValue();
			F26 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).doubleValue();
			F27 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			F28 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
			F29 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			F30 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
			K01 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).doubleValue();
			K02 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).doubleValue();
			K03 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).doubleValue();
			K04 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,42,SysConst.PACKAGESPILTER))).doubleValue();
			K05 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,43,SysConst.PACKAGESPILTER))).doubleValue();
			K06 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,44,SysConst.PACKAGESPILTER))).doubleValue();
			K07 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,45,SysConst.PACKAGESPILTER))).doubleValue();
			K08 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,46,SysConst.PACKAGESPILTER))).doubleValue();
			K09 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,47,SysConst.PACKAGESPILTER))).doubleValue();
			K10 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,48,SysConst.PACKAGESPILTER))).doubleValue();
			K11 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,49,SysConst.PACKAGESPILTER))).doubleValue();
			K12 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,50,SysConst.PACKAGESPILTER))).doubleValue();
			K13 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,51,SysConst.PACKAGESPILTER))).doubleValue();
			K14 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).doubleValue();
			K15 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,53,SysConst.PACKAGESPILTER))).doubleValue();
			K16 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,54,SysConst.PACKAGESPILTER))).doubleValue();
			K17 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,55,SysConst.PACKAGESPILTER))).doubleValue();
			K18 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,56,SysConst.PACKAGESPILTER))).doubleValue();
			K19 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,57,SysConst.PACKAGESPILTER))).doubleValue();
			K20 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,58,SysConst.PACKAGESPILTER))).doubleValue();
			ConfirmDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59,SysConst.PACKAGESPILTER));
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			Operator2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			ShouldMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,69,SysConst.PACKAGESPILTER))).doubleValue();
			BranchAttr = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			AgentGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
			W01 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,72,SysConst.PACKAGESPILTER))).doubleValue();
			W02 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,73,SysConst.PACKAGESPILTER))).doubleValue();
			W03 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,74,SysConst.PACKAGESPILTER))).doubleValue();
			W04 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,75,SysConst.PACKAGESPILTER))).doubleValue();
			W05 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,76,SysConst.PACKAGESPILTER))).doubleValue();
			W06 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,77,SysConst.PACKAGESPILTER))).doubleValue();
			W07 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,78,SysConst.PACKAGESPILTER))).doubleValue();
			W08 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,79,SysConst.PACKAGESPILTER))).doubleValue();
			W09 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,80,SysConst.PACKAGESPILTER))).doubleValue();
			W10 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,81,SysConst.PACKAGESPILTER))).doubleValue();
			K21 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,82,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAWageSchema";
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
		if (FCode.equalsIgnoreCase("IndexCalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IndexCalNo));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("GetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetDate()));
		}
		if (FCode.equalsIgnoreCase("SumMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumMoney));
		}
		if (FCode.equalsIgnoreCase("LastMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastMoney));
		}
		if (FCode.equalsIgnoreCase("CurrMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrMoney));
		}
		if (FCode.equalsIgnoreCase("F01"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F01));
		}
		if (FCode.equalsIgnoreCase("F02"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F02));
		}
		if (FCode.equalsIgnoreCase("F03"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F03));
		}
		if (FCode.equalsIgnoreCase("F04"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F04));
		}
		if (FCode.equalsIgnoreCase("F05"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F05));
		}
		if (FCode.equalsIgnoreCase("F06"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F06));
		}
		if (FCode.equalsIgnoreCase("F07"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F07));
		}
		if (FCode.equalsIgnoreCase("F08"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F08));
		}
		if (FCode.equalsIgnoreCase("F09"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F09));
		}
		if (FCode.equalsIgnoreCase("F10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F10));
		}
		if (FCode.equalsIgnoreCase("F11"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F11));
		}
		if (FCode.equalsIgnoreCase("F12"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F12));
		}
		if (FCode.equalsIgnoreCase("F13"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F13));
		}
		if (FCode.equalsIgnoreCase("F14"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F14));
		}
		if (FCode.equalsIgnoreCase("F15"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F15));
		}
		if (FCode.equalsIgnoreCase("F16"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F16));
		}
		if (FCode.equalsIgnoreCase("F17"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F17));
		}
		if (FCode.equalsIgnoreCase("F18"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F18));
		}
		if (FCode.equalsIgnoreCase("F19"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F19));
		}
		if (FCode.equalsIgnoreCase("F20"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F20));
		}
		if (FCode.equalsIgnoreCase("F21"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F21));
		}
		if (FCode.equalsIgnoreCase("F22"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F22));
		}
		if (FCode.equalsIgnoreCase("F23"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F23));
		}
		if (FCode.equalsIgnoreCase("F24"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F24));
		}
		if (FCode.equalsIgnoreCase("F25"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F25));
		}
		if (FCode.equalsIgnoreCase("F26"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F26));
		}
		if (FCode.equalsIgnoreCase("F27"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F27));
		}
		if (FCode.equalsIgnoreCase("F28"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F28));
		}
		if (FCode.equalsIgnoreCase("F29"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F29));
		}
		if (FCode.equalsIgnoreCase("F30"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F30));
		}
		if (FCode.equalsIgnoreCase("K01"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K01));
		}
		if (FCode.equalsIgnoreCase("K02"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K02));
		}
		if (FCode.equalsIgnoreCase("K03"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K03));
		}
		if (FCode.equalsIgnoreCase("K04"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K04));
		}
		if (FCode.equalsIgnoreCase("K05"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K05));
		}
		if (FCode.equalsIgnoreCase("K06"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K06));
		}
		if (FCode.equalsIgnoreCase("K07"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K07));
		}
		if (FCode.equalsIgnoreCase("K08"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K08));
		}
		if (FCode.equalsIgnoreCase("K09"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K09));
		}
		if (FCode.equalsIgnoreCase("K10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K10));
		}
		if (FCode.equalsIgnoreCase("K11"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K11));
		}
		if (FCode.equalsIgnoreCase("K12"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K12));
		}
		if (FCode.equalsIgnoreCase("K13"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K13));
		}
		if (FCode.equalsIgnoreCase("K14"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K14));
		}
		if (FCode.equalsIgnoreCase("K15"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K15));
		}
		if (FCode.equalsIgnoreCase("K16"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K16));
		}
		if (FCode.equalsIgnoreCase("K17"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K17));
		}
		if (FCode.equalsIgnoreCase("K18"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K18));
		}
		if (FCode.equalsIgnoreCase("K19"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K19));
		}
		if (FCode.equalsIgnoreCase("K20"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K20));
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("Operator2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator2));
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
		if (FCode.equalsIgnoreCase("ShouldMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShouldMoney));
		}
		if (FCode.equalsIgnoreCase("BranchAttr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchAttr));
		}
		if (FCode.equalsIgnoreCase("AgentGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGrade));
		}
		if (FCode.equalsIgnoreCase("W01"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(W01));
		}
		if (FCode.equalsIgnoreCase("W02"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(W02));
		}
		if (FCode.equalsIgnoreCase("W03"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(W03));
		}
		if (FCode.equalsIgnoreCase("W04"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(W04));
		}
		if (FCode.equalsIgnoreCase("W05"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(W05));
		}
		if (FCode.equalsIgnoreCase("W06"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(W06));
		}
		if (FCode.equalsIgnoreCase("W07"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(W07));
		}
		if (FCode.equalsIgnoreCase("W08"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(W08));
		}
		if (FCode.equalsIgnoreCase("W09"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(W09));
		}
		if (FCode.equalsIgnoreCase("W10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(W10));
		}
		if (FCode.equalsIgnoreCase("K21"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K21));
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
				strFieldValue = StrTool.GBKToUnicode(IndexCalNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetDate()));
				break;
			case 5:
				strFieldValue = String.valueOf(SumMoney);
				break;
			case 6:
				strFieldValue = String.valueOf(LastMoney);
				break;
			case 7:
				strFieldValue = String.valueOf(CurrMoney);
				break;
			case 8:
				strFieldValue = String.valueOf(F01);
				break;
			case 9:
				strFieldValue = String.valueOf(F02);
				break;
			case 10:
				strFieldValue = String.valueOf(F03);
				break;
			case 11:
				strFieldValue = String.valueOf(F04);
				break;
			case 12:
				strFieldValue = String.valueOf(F05);
				break;
			case 13:
				strFieldValue = String.valueOf(F06);
				break;
			case 14:
				strFieldValue = String.valueOf(F07);
				break;
			case 15:
				strFieldValue = String.valueOf(F08);
				break;
			case 16:
				strFieldValue = String.valueOf(F09);
				break;
			case 17:
				strFieldValue = String.valueOf(F10);
				break;
			case 18:
				strFieldValue = String.valueOf(F11);
				break;
			case 19:
				strFieldValue = String.valueOf(F12);
				break;
			case 20:
				strFieldValue = String.valueOf(F13);
				break;
			case 21:
				strFieldValue = String.valueOf(F14);
				break;
			case 22:
				strFieldValue = String.valueOf(F15);
				break;
			case 23:
				strFieldValue = String.valueOf(F16);
				break;
			case 24:
				strFieldValue = String.valueOf(F17);
				break;
			case 25:
				strFieldValue = String.valueOf(F18);
				break;
			case 26:
				strFieldValue = String.valueOf(F19);
				break;
			case 27:
				strFieldValue = String.valueOf(F20);
				break;
			case 28:
				strFieldValue = String.valueOf(F21);
				break;
			case 29:
				strFieldValue = String.valueOf(F22);
				break;
			case 30:
				strFieldValue = String.valueOf(F23);
				break;
			case 31:
				strFieldValue = String.valueOf(F24);
				break;
			case 32:
				strFieldValue = String.valueOf(F25);
				break;
			case 33:
				strFieldValue = String.valueOf(F26);
				break;
			case 34:
				strFieldValue = String.valueOf(F27);
				break;
			case 35:
				strFieldValue = String.valueOf(F28);
				break;
			case 36:
				strFieldValue = String.valueOf(F29);
				break;
			case 37:
				strFieldValue = String.valueOf(F30);
				break;
			case 38:
				strFieldValue = String.valueOf(K01);
				break;
			case 39:
				strFieldValue = String.valueOf(K02);
				break;
			case 40:
				strFieldValue = String.valueOf(K03);
				break;
			case 41:
				strFieldValue = String.valueOf(K04);
				break;
			case 42:
				strFieldValue = String.valueOf(K05);
				break;
			case 43:
				strFieldValue = String.valueOf(K06);
				break;
			case 44:
				strFieldValue = String.valueOf(K07);
				break;
			case 45:
				strFieldValue = String.valueOf(K08);
				break;
			case 46:
				strFieldValue = String.valueOf(K09);
				break;
			case 47:
				strFieldValue = String.valueOf(K10);
				break;
			case 48:
				strFieldValue = String.valueOf(K11);
				break;
			case 49:
				strFieldValue = String.valueOf(K12);
				break;
			case 50:
				strFieldValue = String.valueOf(K13);
				break;
			case 51:
				strFieldValue = String.valueOf(K14);
				break;
			case 52:
				strFieldValue = String.valueOf(K15);
				break;
			case 53:
				strFieldValue = String.valueOf(K16);
				break;
			case 54:
				strFieldValue = String.valueOf(K17);
				break;
			case 55:
				strFieldValue = String.valueOf(K18);
				break;
			case 56:
				strFieldValue = String.valueOf(K19);
				break;
			case 57:
				strFieldValue = String.valueOf(K20);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(Operator2);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(BranchType);
				break;
			case 68:
				strFieldValue = String.valueOf(ShouldMoney);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(BranchAttr);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(AgentGrade);
				break;
			case 71:
				strFieldValue = String.valueOf(W01);
				break;
			case 72:
				strFieldValue = String.valueOf(W02);
				break;
			case 73:
				strFieldValue = String.valueOf(W03);
				break;
			case 74:
				strFieldValue = String.valueOf(W04);
				break;
			case 75:
				strFieldValue = String.valueOf(W05);
				break;
			case 76:
				strFieldValue = String.valueOf(W06);
				break;
			case 77:
				strFieldValue = String.valueOf(W07);
				break;
			case 78:
				strFieldValue = String.valueOf(W08);
				break;
			case 79:
				strFieldValue = String.valueOf(W09);
				break;
			case 80:
				strFieldValue = String.valueOf(W10);
				break;
			case 81:
				strFieldValue = String.valueOf(K21);
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

		if (FCode.equalsIgnoreCase("IndexCalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IndexCalNo = FValue.trim();
			}
			else
				IndexCalNo = null;
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup = FValue.trim();
			}
			else
				AgentGroup = null;
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
		if (FCode.equalsIgnoreCase("GetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetDate = fDate.getDate( FValue );
			}
			else
				GetDate = null;
		}
		if (FCode.equalsIgnoreCase("SumMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("LastMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LastMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("CurrMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurrMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("F01"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F01 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F02"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F02 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F03"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F03 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F04"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F04 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F05"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F05 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F06"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F06 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F07"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F07 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F08"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F08 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F09"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F09 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F10 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F11"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F11 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F12"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F12 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F13"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F13 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F14"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F14 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F15"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F15 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F16"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F16 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F17"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F17 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F18"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F18 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F19"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F19 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F20"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F20 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F21"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F21 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F22"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F22 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F23"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F23 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F24"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F24 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F25"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F25 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F26"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F26 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F27"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F27 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F28"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F28 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F29"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F29 = d;
			}
		}
		if (FCode.equalsIgnoreCase("F30"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				F30 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K01"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K01 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K02"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K02 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K03"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K03 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K04"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K04 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K05"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K05 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K06"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K06 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K07"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K07 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K08"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K08 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K09"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K09 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K10 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K11"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K11 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K12"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K12 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K13"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K13 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K14"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K14 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K15"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K15 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K16"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K16 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K17"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K17 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K18"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K18 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K19"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K19 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K20"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K20 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfirmDate = fDate.getDate( FValue );
			}
			else
				ConfirmDate = null;
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
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayMode = FValue.trim();
			}
			else
				PayMode = null;
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
		if (FCode.equalsIgnoreCase("Operator2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator2 = FValue.trim();
			}
			else
				Operator2 = null;
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
		if (FCode.equalsIgnoreCase("ShouldMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ShouldMoney = d;
			}
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
		if (FCode.equalsIgnoreCase("AgentGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGrade = FValue.trim();
			}
			else
				AgentGrade = null;
		}
		if (FCode.equalsIgnoreCase("W01"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				W01 = d;
			}
		}
		if (FCode.equalsIgnoreCase("W02"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				W02 = d;
			}
		}
		if (FCode.equalsIgnoreCase("W03"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				W03 = d;
			}
		}
		if (FCode.equalsIgnoreCase("W04"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				W04 = d;
			}
		}
		if (FCode.equalsIgnoreCase("W05"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				W05 = d;
			}
		}
		if (FCode.equalsIgnoreCase("W06"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				W06 = d;
			}
		}
		if (FCode.equalsIgnoreCase("W07"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				W07 = d;
			}
		}
		if (FCode.equalsIgnoreCase("W08"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				W08 = d;
			}
		}
		if (FCode.equalsIgnoreCase("W09"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				W09 = d;
			}
		}
		if (FCode.equalsIgnoreCase("W10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				W10 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K21"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K21 = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LAWageSchema other = (LAWageSchema)otherObject;
		return
			IndexCalNo.equals(other.getIndexCalNo())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(GetDate).equals(other.getGetDate())
			&& SumMoney == other.getSumMoney()
			&& LastMoney == other.getLastMoney()
			&& CurrMoney == other.getCurrMoney()
			&& F01 == other.getF01()
			&& F02 == other.getF02()
			&& F03 == other.getF03()
			&& F04 == other.getF04()
			&& F05 == other.getF05()
			&& F06 == other.getF06()
			&& F07 == other.getF07()
			&& F08 == other.getF08()
			&& F09 == other.getF09()
			&& F10 == other.getF10()
			&& F11 == other.getF11()
			&& F12 == other.getF12()
			&& F13 == other.getF13()
			&& F14 == other.getF14()
			&& F15 == other.getF15()
			&& F16 == other.getF16()
			&& F17 == other.getF17()
			&& F18 == other.getF18()
			&& F19 == other.getF19()
			&& F20 == other.getF20()
			&& F21 == other.getF21()
			&& F22 == other.getF22()
			&& F23 == other.getF23()
			&& F24 == other.getF24()
			&& F25 == other.getF25()
			&& F26 == other.getF26()
			&& F27 == other.getF27()
			&& F28 == other.getF28()
			&& F29 == other.getF29()
			&& F30 == other.getF30()
			&& K01 == other.getK01()
			&& K02 == other.getK02()
			&& K03 == other.getK03()
			&& K04 == other.getK04()
			&& K05 == other.getK05()
			&& K06 == other.getK06()
			&& K07 == other.getK07()
			&& K08 == other.getK08()
			&& K09 == other.getK09()
			&& K10 == other.getK10()
			&& K11 == other.getK11()
			&& K12 == other.getK12()
			&& K13 == other.getK13()
			&& K14 == other.getK14()
			&& K15 == other.getK15()
			&& K16 == other.getK16()
			&& K17 == other.getK17()
			&& K18 == other.getK18()
			&& K19 == other.getK19()
			&& K20 == other.getK20()
			&& fDate.getString(ConfirmDate).equals(other.getConfirmDate())
			&& State.equals(other.getState())
			&& PayMode.equals(other.getPayMode())
			&& Operator.equals(other.getOperator())
			&& Operator2.equals(other.getOperator2())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BranchType.equals(other.getBranchType())
			&& ShouldMoney == other.getShouldMoney()
			&& BranchAttr.equals(other.getBranchAttr())
			&& AgentGrade.equals(other.getAgentGrade())
			&& W01 == other.getW01()
			&& W02 == other.getW02()
			&& W03 == other.getW03()
			&& W04 == other.getW04()
			&& W05 == other.getW05()
			&& W06 == other.getW06()
			&& W07 == other.getW07()
			&& W08 == other.getW08()
			&& W09 == other.getW09()
			&& W10 == other.getW10()
			&& K21 == other.getK21();
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
		if( strFieldName.equals("IndexCalNo") ) {
			return 0;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 1;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 2;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 3;
		}
		if( strFieldName.equals("GetDate") ) {
			return 4;
		}
		if( strFieldName.equals("SumMoney") ) {
			return 5;
		}
		if( strFieldName.equals("LastMoney") ) {
			return 6;
		}
		if( strFieldName.equals("CurrMoney") ) {
			return 7;
		}
		if( strFieldName.equals("F01") ) {
			return 8;
		}
		if( strFieldName.equals("F02") ) {
			return 9;
		}
		if( strFieldName.equals("F03") ) {
			return 10;
		}
		if( strFieldName.equals("F04") ) {
			return 11;
		}
		if( strFieldName.equals("F05") ) {
			return 12;
		}
		if( strFieldName.equals("F06") ) {
			return 13;
		}
		if( strFieldName.equals("F07") ) {
			return 14;
		}
		if( strFieldName.equals("F08") ) {
			return 15;
		}
		if( strFieldName.equals("F09") ) {
			return 16;
		}
		if( strFieldName.equals("F10") ) {
			return 17;
		}
		if( strFieldName.equals("F11") ) {
			return 18;
		}
		if( strFieldName.equals("F12") ) {
			return 19;
		}
		if( strFieldName.equals("F13") ) {
			return 20;
		}
		if( strFieldName.equals("F14") ) {
			return 21;
		}
		if( strFieldName.equals("F15") ) {
			return 22;
		}
		if( strFieldName.equals("F16") ) {
			return 23;
		}
		if( strFieldName.equals("F17") ) {
			return 24;
		}
		if( strFieldName.equals("F18") ) {
			return 25;
		}
		if( strFieldName.equals("F19") ) {
			return 26;
		}
		if( strFieldName.equals("F20") ) {
			return 27;
		}
		if( strFieldName.equals("F21") ) {
			return 28;
		}
		if( strFieldName.equals("F22") ) {
			return 29;
		}
		if( strFieldName.equals("F23") ) {
			return 30;
		}
		if( strFieldName.equals("F24") ) {
			return 31;
		}
		if( strFieldName.equals("F25") ) {
			return 32;
		}
		if( strFieldName.equals("F26") ) {
			return 33;
		}
		if( strFieldName.equals("F27") ) {
			return 34;
		}
		if( strFieldName.equals("F28") ) {
			return 35;
		}
		if( strFieldName.equals("F29") ) {
			return 36;
		}
		if( strFieldName.equals("F30") ) {
			return 37;
		}
		if( strFieldName.equals("K01") ) {
			return 38;
		}
		if( strFieldName.equals("K02") ) {
			return 39;
		}
		if( strFieldName.equals("K03") ) {
			return 40;
		}
		if( strFieldName.equals("K04") ) {
			return 41;
		}
		if( strFieldName.equals("K05") ) {
			return 42;
		}
		if( strFieldName.equals("K06") ) {
			return 43;
		}
		if( strFieldName.equals("K07") ) {
			return 44;
		}
		if( strFieldName.equals("K08") ) {
			return 45;
		}
		if( strFieldName.equals("K09") ) {
			return 46;
		}
		if( strFieldName.equals("K10") ) {
			return 47;
		}
		if( strFieldName.equals("K11") ) {
			return 48;
		}
		if( strFieldName.equals("K12") ) {
			return 49;
		}
		if( strFieldName.equals("K13") ) {
			return 50;
		}
		if( strFieldName.equals("K14") ) {
			return 51;
		}
		if( strFieldName.equals("K15") ) {
			return 52;
		}
		if( strFieldName.equals("K16") ) {
			return 53;
		}
		if( strFieldName.equals("K17") ) {
			return 54;
		}
		if( strFieldName.equals("K18") ) {
			return 55;
		}
		if( strFieldName.equals("K19") ) {
			return 56;
		}
		if( strFieldName.equals("K20") ) {
			return 57;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return 58;
		}
		if( strFieldName.equals("State") ) {
			return 59;
		}
		if( strFieldName.equals("PayMode") ) {
			return 60;
		}
		if( strFieldName.equals("Operator") ) {
			return 61;
		}
		if( strFieldName.equals("Operator2") ) {
			return 62;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 63;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 64;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 65;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 66;
		}
		if( strFieldName.equals("BranchType") ) {
			return 67;
		}
		if( strFieldName.equals("ShouldMoney") ) {
			return 68;
		}
		if( strFieldName.equals("BranchAttr") ) {
			return 69;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return 70;
		}
		if( strFieldName.equals("W01") ) {
			return 71;
		}
		if( strFieldName.equals("W02") ) {
			return 72;
		}
		if( strFieldName.equals("W03") ) {
			return 73;
		}
		if( strFieldName.equals("W04") ) {
			return 74;
		}
		if( strFieldName.equals("W05") ) {
			return 75;
		}
		if( strFieldName.equals("W06") ) {
			return 76;
		}
		if( strFieldName.equals("W07") ) {
			return 77;
		}
		if( strFieldName.equals("W08") ) {
			return 78;
		}
		if( strFieldName.equals("W09") ) {
			return 79;
		}
		if( strFieldName.equals("W10") ) {
			return 80;
		}
		if( strFieldName.equals("K21") ) {
			return 81;
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
				strFieldName = "IndexCalNo";
				break;
			case 1:
				strFieldName = "AgentCode";
				break;
			case 2:
				strFieldName = "AgentGroup";
				break;
			case 3:
				strFieldName = "ManageCom";
				break;
			case 4:
				strFieldName = "GetDate";
				break;
			case 5:
				strFieldName = "SumMoney";
				break;
			case 6:
				strFieldName = "LastMoney";
				break;
			case 7:
				strFieldName = "CurrMoney";
				break;
			case 8:
				strFieldName = "F01";
				break;
			case 9:
				strFieldName = "F02";
				break;
			case 10:
				strFieldName = "F03";
				break;
			case 11:
				strFieldName = "F04";
				break;
			case 12:
				strFieldName = "F05";
				break;
			case 13:
				strFieldName = "F06";
				break;
			case 14:
				strFieldName = "F07";
				break;
			case 15:
				strFieldName = "F08";
				break;
			case 16:
				strFieldName = "F09";
				break;
			case 17:
				strFieldName = "F10";
				break;
			case 18:
				strFieldName = "F11";
				break;
			case 19:
				strFieldName = "F12";
				break;
			case 20:
				strFieldName = "F13";
				break;
			case 21:
				strFieldName = "F14";
				break;
			case 22:
				strFieldName = "F15";
				break;
			case 23:
				strFieldName = "F16";
				break;
			case 24:
				strFieldName = "F17";
				break;
			case 25:
				strFieldName = "F18";
				break;
			case 26:
				strFieldName = "F19";
				break;
			case 27:
				strFieldName = "F20";
				break;
			case 28:
				strFieldName = "F21";
				break;
			case 29:
				strFieldName = "F22";
				break;
			case 30:
				strFieldName = "F23";
				break;
			case 31:
				strFieldName = "F24";
				break;
			case 32:
				strFieldName = "F25";
				break;
			case 33:
				strFieldName = "F26";
				break;
			case 34:
				strFieldName = "F27";
				break;
			case 35:
				strFieldName = "F28";
				break;
			case 36:
				strFieldName = "F29";
				break;
			case 37:
				strFieldName = "F30";
				break;
			case 38:
				strFieldName = "K01";
				break;
			case 39:
				strFieldName = "K02";
				break;
			case 40:
				strFieldName = "K03";
				break;
			case 41:
				strFieldName = "K04";
				break;
			case 42:
				strFieldName = "K05";
				break;
			case 43:
				strFieldName = "K06";
				break;
			case 44:
				strFieldName = "K07";
				break;
			case 45:
				strFieldName = "K08";
				break;
			case 46:
				strFieldName = "K09";
				break;
			case 47:
				strFieldName = "K10";
				break;
			case 48:
				strFieldName = "K11";
				break;
			case 49:
				strFieldName = "K12";
				break;
			case 50:
				strFieldName = "K13";
				break;
			case 51:
				strFieldName = "K14";
				break;
			case 52:
				strFieldName = "K15";
				break;
			case 53:
				strFieldName = "K16";
				break;
			case 54:
				strFieldName = "K17";
				break;
			case 55:
				strFieldName = "K18";
				break;
			case 56:
				strFieldName = "K19";
				break;
			case 57:
				strFieldName = "K20";
				break;
			case 58:
				strFieldName = "ConfirmDate";
				break;
			case 59:
				strFieldName = "State";
				break;
			case 60:
				strFieldName = "PayMode";
				break;
			case 61:
				strFieldName = "Operator";
				break;
			case 62:
				strFieldName = "Operator2";
				break;
			case 63:
				strFieldName = "MakeDate";
				break;
			case 64:
				strFieldName = "MakeTime";
				break;
			case 65:
				strFieldName = "ModifyDate";
				break;
			case 66:
				strFieldName = "ModifyTime";
				break;
			case 67:
				strFieldName = "BranchType";
				break;
			case 68:
				strFieldName = "ShouldMoney";
				break;
			case 69:
				strFieldName = "BranchAttr";
				break;
			case 70:
				strFieldName = "AgentGrade";
				break;
			case 71:
				strFieldName = "W01";
				break;
			case 72:
				strFieldName = "W02";
				break;
			case 73:
				strFieldName = "W03";
				break;
			case 74:
				strFieldName = "W04";
				break;
			case 75:
				strFieldName = "W05";
				break;
			case 76:
				strFieldName = "W06";
				break;
			case 77:
				strFieldName = "W07";
				break;
			case 78:
				strFieldName = "W08";
				break;
			case 79:
				strFieldName = "W09";
				break;
			case 80:
				strFieldName = "W10";
				break;
			case 81:
				strFieldName = "K21";
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
		if( strFieldName.equals("IndexCalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SumMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LastMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CurrMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F01") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F02") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F03") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F04") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F05") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F06") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F07") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F08") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F09") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F10") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F11") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F12") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F13") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F14") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F15") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F16") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F17") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F18") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F19") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F20") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F21") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F22") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F23") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F24") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F25") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F26") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F27") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F28") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F29") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F30") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K01") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K02") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K03") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K04") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K05") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K06") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K07") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K08") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K09") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K10") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K11") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K12") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K13") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K14") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K15") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K16") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K17") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K18") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K19") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K20") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator2") ) {
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
		if( strFieldName.equals("ShouldMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BranchAttr") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("W01") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("W02") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("W03") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("W04") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("W05") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("W06") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("W07") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("W08") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("W09") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("W10") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K21") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 23:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 26:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 27:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 28:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 29:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 30:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 31:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 32:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 33:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 36:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 37:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 38:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 39:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 40:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 41:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 42:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 43:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 44:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 45:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 46:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 47:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 48:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 49:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 50:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 51:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 52:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 53:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 54:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 55:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 56:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 57:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 58:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 59:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 60:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 61:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 62:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 63:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 64:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 65:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 66:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 67:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 68:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 69:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 70:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 71:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 72:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 73:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 74:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 75:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 76:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 77:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 78:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 79:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 80:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 81:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
