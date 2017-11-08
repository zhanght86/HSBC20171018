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
import com.sinosoft.lis.db.LLRepColligateDB;

/*
 * <p>ClassName: LLRepColligateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLRepColligateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLRepColligateSchema.class);

	// @Field
	/** 报表编号 */
	private String RepID;
	/** 报表名称 */
	private String RepName;
	/** 机构编号 */
	private String MngCom;
	/** 机构名称 */
	private String MngComName;
	/** 年度 */
	private String Year;
	/** 半年 */
	private String HalfYear;
	/** 季度 */
	private String Quarter;
	/** 开始时间 */
	private String StartDate;
	/** 终止时间 */
	private String EndDate;
	/** 序号 */
	private String ColID;
	/** 栏目 */
	private String ColName;
	/** C1 */
	private String C1;
	/** C2 */
	private String C2;
	/** C3 */
	private String C3;
	/** C4 */
	private String C4;
	/** C5 */
	private String C5;
	/** C6 */
	private String C6;
	/** C7 */
	private String C7;
	/** C8 */
	private String C8;
	/** C9 */
	private String C9;
	/** C10 */
	private String C10;
	/** C11 */
	private String C11;
	/** C12 */
	private String C12;
	/** C13 */
	private String C13;
	/** C14 */
	private String C14;
	/** C15 */
	private String C15;
	/** C16 */
	private String C16;
	/** C17 */
	private String C17;
	/** C18 */
	private String C18;
	/** C19 */
	private String C19;
	/** C20 */
	private String C20;
	/** C21 */
	private String C21;
	/** C22 */
	private String C22;
	/** C23 */
	private String C23;
	/** C24 */
	private String C24;
	/** C25 */
	private String C25;
	/** C26 */
	private String C26;
	/** C27 */
	private String C27;
	/** C28 */
	private String C28;
	/** C29 */
	private String C29;
	/** C30 */
	private String C30;
	/** C31 */
	private String C31;
	/** C32 */
	private String C32;
	/** C33 */
	private String C33;
	/** C34 */
	private String C34;
	/** C35 */
	private String C35;
	/** C36 */
	private String C36;
	/** C37 */
	private String C37;
	/** C38 */
	private String C38;
	/** C39 */
	private String C39;
	/** C40 */
	private String C40;
	/** C41 */
	private String C41;
	/** C42 */
	private String C42;
	/** C43 */
	private String C43;
	/** C44 */
	private String C44;
	/** C45 */
	private String C45;
	/** C46 */
	private String C46;
	/** C47 */
	private String C47;
	/** C48 */
	private String C48;
	/** C49 */
	private String C49;
	/** C50 */
	private String C50;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 操作员 */
	private String Operator;
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 67;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLRepColligateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "RepID";
		pk[1] = "MngCom";
		pk[2] = "StartDate";
		pk[3] = "EndDate";
		pk[4] = "ColID";

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
		LLRepColligateSchema cloned = (LLRepColligateSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRepID()
	{
		return RepID;
	}
	public void setRepID(String aRepID)
	{
		RepID = aRepID;
	}
	public String getRepName()
	{
		return RepName;
	}
	public void setRepName(String aRepName)
	{
		RepName = aRepName;
	}
	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		MngCom = aMngCom;
	}
	public String getMngComName()
	{
		return MngComName;
	}
	public void setMngComName(String aMngComName)
	{
		MngComName = aMngComName;
	}
	public String getYear()
	{
		return Year;
	}
	public void setYear(String aYear)
	{
		Year = aYear;
	}
	public String getHalfYear()
	{
		return HalfYear;
	}
	public void setHalfYear(String aHalfYear)
	{
		HalfYear = aHalfYear;
	}
	public String getQuarter()
	{
		return Quarter;
	}
	public void setQuarter(String aQuarter)
	{
		Quarter = aQuarter;
	}
	public String getStartDate()
	{
		return StartDate;
	}
	public void setStartDate(String aStartDate)
	{
		StartDate = aStartDate;
	}
	public String getEndDate()
	{
		return EndDate;
	}
	public void setEndDate(String aEndDate)
	{
		EndDate = aEndDate;
	}
	public String getColID()
	{
		return ColID;
	}
	public void setColID(String aColID)
	{
		ColID = aColID;
	}
	public String getColName()
	{
		return ColName;
	}
	public void setColName(String aColName)
	{
		ColName = aColName;
	}
	public String getC1()
	{
		return C1;
	}
	public void setC1(String aC1)
	{
		C1 = aC1;
	}
	public String getC2()
	{
		return C2;
	}
	public void setC2(String aC2)
	{
		C2 = aC2;
	}
	public String getC3()
	{
		return C3;
	}
	public void setC3(String aC3)
	{
		C3 = aC3;
	}
	public String getC4()
	{
		return C4;
	}
	public void setC4(String aC4)
	{
		C4 = aC4;
	}
	public String getC5()
	{
		return C5;
	}
	public void setC5(String aC5)
	{
		C5 = aC5;
	}
	public String getC6()
	{
		return C6;
	}
	public void setC6(String aC6)
	{
		C6 = aC6;
	}
	public String getC7()
	{
		return C7;
	}
	public void setC7(String aC7)
	{
		C7 = aC7;
	}
	public String getC8()
	{
		return C8;
	}
	public void setC8(String aC8)
	{
		C8 = aC8;
	}
	public String getC9()
	{
		return C9;
	}
	public void setC9(String aC9)
	{
		C9 = aC9;
	}
	public String getC10()
	{
		return C10;
	}
	public void setC10(String aC10)
	{
		C10 = aC10;
	}
	public String getC11()
	{
		return C11;
	}
	public void setC11(String aC11)
	{
		C11 = aC11;
	}
	public String getC12()
	{
		return C12;
	}
	public void setC12(String aC12)
	{
		C12 = aC12;
	}
	public String getC13()
	{
		return C13;
	}
	public void setC13(String aC13)
	{
		C13 = aC13;
	}
	public String getC14()
	{
		return C14;
	}
	public void setC14(String aC14)
	{
		C14 = aC14;
	}
	public String getC15()
	{
		return C15;
	}
	public void setC15(String aC15)
	{
		C15 = aC15;
	}
	public String getC16()
	{
		return C16;
	}
	public void setC16(String aC16)
	{
		C16 = aC16;
	}
	public String getC17()
	{
		return C17;
	}
	public void setC17(String aC17)
	{
		C17 = aC17;
	}
	public String getC18()
	{
		return C18;
	}
	public void setC18(String aC18)
	{
		C18 = aC18;
	}
	public String getC19()
	{
		return C19;
	}
	public void setC19(String aC19)
	{
		C19 = aC19;
	}
	public String getC20()
	{
		return C20;
	}
	public void setC20(String aC20)
	{
		C20 = aC20;
	}
	public String getC21()
	{
		return C21;
	}
	public void setC21(String aC21)
	{
		C21 = aC21;
	}
	public String getC22()
	{
		return C22;
	}
	public void setC22(String aC22)
	{
		C22 = aC22;
	}
	public String getC23()
	{
		return C23;
	}
	public void setC23(String aC23)
	{
		C23 = aC23;
	}
	public String getC24()
	{
		return C24;
	}
	public void setC24(String aC24)
	{
		C24 = aC24;
	}
	public String getC25()
	{
		return C25;
	}
	public void setC25(String aC25)
	{
		C25 = aC25;
	}
	public String getC26()
	{
		return C26;
	}
	public void setC26(String aC26)
	{
		C26 = aC26;
	}
	public String getC27()
	{
		return C27;
	}
	public void setC27(String aC27)
	{
		C27 = aC27;
	}
	public String getC28()
	{
		return C28;
	}
	public void setC28(String aC28)
	{
		C28 = aC28;
	}
	public String getC29()
	{
		return C29;
	}
	public void setC29(String aC29)
	{
		C29 = aC29;
	}
	public String getC30()
	{
		return C30;
	}
	public void setC30(String aC30)
	{
		C30 = aC30;
	}
	public String getC31()
	{
		return C31;
	}
	public void setC31(String aC31)
	{
		C31 = aC31;
	}
	public String getC32()
	{
		return C32;
	}
	public void setC32(String aC32)
	{
		C32 = aC32;
	}
	public String getC33()
	{
		return C33;
	}
	public void setC33(String aC33)
	{
		C33 = aC33;
	}
	public String getC34()
	{
		return C34;
	}
	public void setC34(String aC34)
	{
		C34 = aC34;
	}
	public String getC35()
	{
		return C35;
	}
	public void setC35(String aC35)
	{
		C35 = aC35;
	}
	public String getC36()
	{
		return C36;
	}
	public void setC36(String aC36)
	{
		C36 = aC36;
	}
	public String getC37()
	{
		return C37;
	}
	public void setC37(String aC37)
	{
		C37 = aC37;
	}
	public String getC38()
	{
		return C38;
	}
	public void setC38(String aC38)
	{
		C38 = aC38;
	}
	public String getC39()
	{
		return C39;
	}
	public void setC39(String aC39)
	{
		C39 = aC39;
	}
	public String getC40()
	{
		return C40;
	}
	public void setC40(String aC40)
	{
		C40 = aC40;
	}
	public String getC41()
	{
		return C41;
	}
	public void setC41(String aC41)
	{
		C41 = aC41;
	}
	public String getC42()
	{
		return C42;
	}
	public void setC42(String aC42)
	{
		C42 = aC42;
	}
	public String getC43()
	{
		return C43;
	}
	public void setC43(String aC43)
	{
		C43 = aC43;
	}
	public String getC44()
	{
		return C44;
	}
	public void setC44(String aC44)
	{
		C44 = aC44;
	}
	public String getC45()
	{
		return C45;
	}
	public void setC45(String aC45)
	{
		C45 = aC45;
	}
	public String getC46()
	{
		return C46;
	}
	public void setC46(String aC46)
	{
		C46 = aC46;
	}
	public String getC47()
	{
		return C47;
	}
	public void setC47(String aC47)
	{
		C47 = aC47;
	}
	public String getC48()
	{
		return C48;
	}
	public void setC48(String aC48)
	{
		C48 = aC48;
	}
	public String getC49()
	{
		return C49;
	}
	public void setC49(String aC49)
	{
		C49 = aC49;
	}
	public String getC50()
	{
		return C50;
	}
	public void setC50(String aC50)
	{
		C50 = aC50;
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
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LLRepColligateSchema 对象给 Schema 赋值
	* @param: aLLRepColligateSchema LLRepColligateSchema
	**/
	public void setSchema(LLRepColligateSchema aLLRepColligateSchema)
	{
		this.RepID = aLLRepColligateSchema.getRepID();
		this.RepName = aLLRepColligateSchema.getRepName();
		this.MngCom = aLLRepColligateSchema.getMngCom();
		this.MngComName = aLLRepColligateSchema.getMngComName();
		this.Year = aLLRepColligateSchema.getYear();
		this.HalfYear = aLLRepColligateSchema.getHalfYear();
		this.Quarter = aLLRepColligateSchema.getQuarter();
		this.StartDate = aLLRepColligateSchema.getStartDate();
		this.EndDate = aLLRepColligateSchema.getEndDate();
		this.ColID = aLLRepColligateSchema.getColID();
		this.ColName = aLLRepColligateSchema.getColName();
		this.C1 = aLLRepColligateSchema.getC1();
		this.C2 = aLLRepColligateSchema.getC2();
		this.C3 = aLLRepColligateSchema.getC3();
		this.C4 = aLLRepColligateSchema.getC4();
		this.C5 = aLLRepColligateSchema.getC5();
		this.C6 = aLLRepColligateSchema.getC6();
		this.C7 = aLLRepColligateSchema.getC7();
		this.C8 = aLLRepColligateSchema.getC8();
		this.C9 = aLLRepColligateSchema.getC9();
		this.C10 = aLLRepColligateSchema.getC10();
		this.C11 = aLLRepColligateSchema.getC11();
		this.C12 = aLLRepColligateSchema.getC12();
		this.C13 = aLLRepColligateSchema.getC13();
		this.C14 = aLLRepColligateSchema.getC14();
		this.C15 = aLLRepColligateSchema.getC15();
		this.C16 = aLLRepColligateSchema.getC16();
		this.C17 = aLLRepColligateSchema.getC17();
		this.C18 = aLLRepColligateSchema.getC18();
		this.C19 = aLLRepColligateSchema.getC19();
		this.C20 = aLLRepColligateSchema.getC20();
		this.C21 = aLLRepColligateSchema.getC21();
		this.C22 = aLLRepColligateSchema.getC22();
		this.C23 = aLLRepColligateSchema.getC23();
		this.C24 = aLLRepColligateSchema.getC24();
		this.C25 = aLLRepColligateSchema.getC25();
		this.C26 = aLLRepColligateSchema.getC26();
		this.C27 = aLLRepColligateSchema.getC27();
		this.C28 = aLLRepColligateSchema.getC28();
		this.C29 = aLLRepColligateSchema.getC29();
		this.C30 = aLLRepColligateSchema.getC30();
		this.C31 = aLLRepColligateSchema.getC31();
		this.C32 = aLLRepColligateSchema.getC32();
		this.C33 = aLLRepColligateSchema.getC33();
		this.C34 = aLLRepColligateSchema.getC34();
		this.C35 = aLLRepColligateSchema.getC35();
		this.C36 = aLLRepColligateSchema.getC36();
		this.C37 = aLLRepColligateSchema.getC37();
		this.C38 = aLLRepColligateSchema.getC38();
		this.C39 = aLLRepColligateSchema.getC39();
		this.C40 = aLLRepColligateSchema.getC40();
		this.C41 = aLLRepColligateSchema.getC41();
		this.C42 = aLLRepColligateSchema.getC42();
		this.C43 = aLLRepColligateSchema.getC43();
		this.C44 = aLLRepColligateSchema.getC44();
		this.C45 = aLLRepColligateSchema.getC45();
		this.C46 = aLLRepColligateSchema.getC46();
		this.C47 = aLLRepColligateSchema.getC47();
		this.C48 = aLLRepColligateSchema.getC48();
		this.C49 = aLLRepColligateSchema.getC49();
		this.C50 = aLLRepColligateSchema.getC50();
		this.MakeDate = fDate.getDate( aLLRepColligateSchema.getMakeDate());
		this.MakeTime = aLLRepColligateSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLRepColligateSchema.getModifyDate());
		this.ModifyTime = aLLRepColligateSchema.getModifyTime();
		this.Operator = aLLRepColligateSchema.getOperator();
		this.Remark = aLLRepColligateSchema.getRemark();
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
			if( rs.getString("RepID") == null )
				this.RepID = null;
			else
				this.RepID = rs.getString("RepID").trim();

			if( rs.getString("RepName") == null )
				this.RepName = null;
			else
				this.RepName = rs.getString("RepName").trim();

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

			if( rs.getString("MngComName") == null )
				this.MngComName = null;
			else
				this.MngComName = rs.getString("MngComName").trim();

			if( rs.getString("Year") == null )
				this.Year = null;
			else
				this.Year = rs.getString("Year").trim();

			if( rs.getString("HalfYear") == null )
				this.HalfYear = null;
			else
				this.HalfYear = rs.getString("HalfYear").trim();

			if( rs.getString("Quarter") == null )
				this.Quarter = null;
			else
				this.Quarter = rs.getString("Quarter").trim();

			if( rs.getString("StartDate") == null )
				this.StartDate = null;
			else
				this.StartDate = rs.getString("StartDate").trim();

			if( rs.getString("EndDate") == null )
				this.EndDate = null;
			else
				this.EndDate = rs.getString("EndDate").trim();

			if( rs.getString("ColID") == null )
				this.ColID = null;
			else
				this.ColID = rs.getString("ColID").trim();

			if( rs.getString("ColName") == null )
				this.ColName = null;
			else
				this.ColName = rs.getString("ColName").trim();

			if( rs.getString("C1") == null )
				this.C1 = null;
			else
				this.C1 = rs.getString("C1").trim();

			if( rs.getString("C2") == null )
				this.C2 = null;
			else
				this.C2 = rs.getString("C2").trim();

			if( rs.getString("C3") == null )
				this.C3 = null;
			else
				this.C3 = rs.getString("C3").trim();

			if( rs.getString("C4") == null )
				this.C4 = null;
			else
				this.C4 = rs.getString("C4").trim();

			if( rs.getString("C5") == null )
				this.C5 = null;
			else
				this.C5 = rs.getString("C5").trim();

			if( rs.getString("C6") == null )
				this.C6 = null;
			else
				this.C6 = rs.getString("C6").trim();

			if( rs.getString("C7") == null )
				this.C7 = null;
			else
				this.C7 = rs.getString("C7").trim();

			if( rs.getString("C8") == null )
				this.C8 = null;
			else
				this.C8 = rs.getString("C8").trim();

			if( rs.getString("C9") == null )
				this.C9 = null;
			else
				this.C9 = rs.getString("C9").trim();

			if( rs.getString("C10") == null )
				this.C10 = null;
			else
				this.C10 = rs.getString("C10").trim();

			if( rs.getString("C11") == null )
				this.C11 = null;
			else
				this.C11 = rs.getString("C11").trim();

			if( rs.getString("C12") == null )
				this.C12 = null;
			else
				this.C12 = rs.getString("C12").trim();

			if( rs.getString("C13") == null )
				this.C13 = null;
			else
				this.C13 = rs.getString("C13").trim();

			if( rs.getString("C14") == null )
				this.C14 = null;
			else
				this.C14 = rs.getString("C14").trim();

			if( rs.getString("C15") == null )
				this.C15 = null;
			else
				this.C15 = rs.getString("C15").trim();

			if( rs.getString("C16") == null )
				this.C16 = null;
			else
				this.C16 = rs.getString("C16").trim();

			if( rs.getString("C17") == null )
				this.C17 = null;
			else
				this.C17 = rs.getString("C17").trim();

			if( rs.getString("C18") == null )
				this.C18 = null;
			else
				this.C18 = rs.getString("C18").trim();

			if( rs.getString("C19") == null )
				this.C19 = null;
			else
				this.C19 = rs.getString("C19").trim();

			if( rs.getString("C20") == null )
				this.C20 = null;
			else
				this.C20 = rs.getString("C20").trim();

			if( rs.getString("C21") == null )
				this.C21 = null;
			else
				this.C21 = rs.getString("C21").trim();

			if( rs.getString("C22") == null )
				this.C22 = null;
			else
				this.C22 = rs.getString("C22").trim();

			if( rs.getString("C23") == null )
				this.C23 = null;
			else
				this.C23 = rs.getString("C23").trim();

			if( rs.getString("C24") == null )
				this.C24 = null;
			else
				this.C24 = rs.getString("C24").trim();

			if( rs.getString("C25") == null )
				this.C25 = null;
			else
				this.C25 = rs.getString("C25").trim();

			if( rs.getString("C26") == null )
				this.C26 = null;
			else
				this.C26 = rs.getString("C26").trim();

			if( rs.getString("C27") == null )
				this.C27 = null;
			else
				this.C27 = rs.getString("C27").trim();

			if( rs.getString("C28") == null )
				this.C28 = null;
			else
				this.C28 = rs.getString("C28").trim();

			if( rs.getString("C29") == null )
				this.C29 = null;
			else
				this.C29 = rs.getString("C29").trim();

			if( rs.getString("C30") == null )
				this.C30 = null;
			else
				this.C30 = rs.getString("C30").trim();

			if( rs.getString("C31") == null )
				this.C31 = null;
			else
				this.C31 = rs.getString("C31").trim();

			if( rs.getString("C32") == null )
				this.C32 = null;
			else
				this.C32 = rs.getString("C32").trim();

			if( rs.getString("C33") == null )
				this.C33 = null;
			else
				this.C33 = rs.getString("C33").trim();

			if( rs.getString("C34") == null )
				this.C34 = null;
			else
				this.C34 = rs.getString("C34").trim();

			if( rs.getString("C35") == null )
				this.C35 = null;
			else
				this.C35 = rs.getString("C35").trim();

			if( rs.getString("C36") == null )
				this.C36 = null;
			else
				this.C36 = rs.getString("C36").trim();

			if( rs.getString("C37") == null )
				this.C37 = null;
			else
				this.C37 = rs.getString("C37").trim();

			if( rs.getString("C38") == null )
				this.C38 = null;
			else
				this.C38 = rs.getString("C38").trim();

			if( rs.getString("C39") == null )
				this.C39 = null;
			else
				this.C39 = rs.getString("C39").trim();

			if( rs.getString("C40") == null )
				this.C40 = null;
			else
				this.C40 = rs.getString("C40").trim();

			if( rs.getString("C41") == null )
				this.C41 = null;
			else
				this.C41 = rs.getString("C41").trim();

			if( rs.getString("C42") == null )
				this.C42 = null;
			else
				this.C42 = rs.getString("C42").trim();

			if( rs.getString("C43") == null )
				this.C43 = null;
			else
				this.C43 = rs.getString("C43").trim();

			if( rs.getString("C44") == null )
				this.C44 = null;
			else
				this.C44 = rs.getString("C44").trim();

			if( rs.getString("C45") == null )
				this.C45 = null;
			else
				this.C45 = rs.getString("C45").trim();

			if( rs.getString("C46") == null )
				this.C46 = null;
			else
				this.C46 = rs.getString("C46").trim();

			if( rs.getString("C47") == null )
				this.C47 = null;
			else
				this.C47 = rs.getString("C47").trim();

			if( rs.getString("C48") == null )
				this.C48 = null;
			else
				this.C48 = rs.getString("C48").trim();

			if( rs.getString("C49") == null )
				this.C49 = null;
			else
				this.C49 = rs.getString("C49").trim();

			if( rs.getString("C50") == null )
				this.C50 = null;
			else
				this.C50 = rs.getString("C50").trim();

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

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLRepColligate表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRepColligateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLRepColligateSchema getSchema()
	{
		LLRepColligateSchema aLLRepColligateSchema = new LLRepColligateSchema();
		aLLRepColligateSchema.setSchema(this);
		return aLLRepColligateSchema;
	}

	public LLRepColligateDB getDB()
	{
		LLRepColligateDB aDBOper = new LLRepColligateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLRepColligate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RepID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RepName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngComName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Year)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HalfYear)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Quarter)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ColID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ColName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C10)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C11)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C12)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C13)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C14)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C15)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C16)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C17)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C18)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C19)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C20)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C21)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C22)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C23)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C24)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C25)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C26)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C27)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C28)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C29)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C30)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C31)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C32)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C33)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C34)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C35)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C36)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C37)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C38)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C39)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C40)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C41)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C42)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C43)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C44)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C45)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C46)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C47)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C48)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C49)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(C50)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLRepColligate>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RepID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RepName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MngComName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Year = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			HalfYear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Quarter = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			StartDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			EndDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ColID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ColName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			C1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			C2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			C3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			C4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			C5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			C6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			C7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			C8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			C9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			C10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			C11 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			C12 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			C13 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			C14 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			C15 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			C16 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			C17 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			C18 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			C19 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			C20 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			C21 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			C22 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			C23 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			C24 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			C25 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			C26 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			C27 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			C28 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			C29 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			C30 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			C31 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			C32 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			C33 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			C34 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			C35 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			C36 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			C37 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			C38 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			C39 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			C40 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			C41 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			C42 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			C43 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			C44 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			C45 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			C46 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			C47 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			C48 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			C49 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			C50 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRepColligateSchema";
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
		if (FCode.equalsIgnoreCase("RepID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RepID));
		}
		if (FCode.equalsIgnoreCase("RepName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RepName));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
		}
		if (FCode.equalsIgnoreCase("MngComName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngComName));
		}
		if (FCode.equalsIgnoreCase("Year"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Year));
		}
		if (FCode.equalsIgnoreCase("HalfYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HalfYear));
		}
		if (FCode.equalsIgnoreCase("Quarter"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Quarter));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartDate));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndDate));
		}
		if (FCode.equalsIgnoreCase("ColID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColID));
		}
		if (FCode.equalsIgnoreCase("ColName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColName));
		}
		if (FCode.equalsIgnoreCase("C1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C1));
		}
		if (FCode.equalsIgnoreCase("C2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C2));
		}
		if (FCode.equalsIgnoreCase("C3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C3));
		}
		if (FCode.equalsIgnoreCase("C4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C4));
		}
		if (FCode.equalsIgnoreCase("C5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C5));
		}
		if (FCode.equalsIgnoreCase("C6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C6));
		}
		if (FCode.equalsIgnoreCase("C7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C7));
		}
		if (FCode.equalsIgnoreCase("C8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C8));
		}
		if (FCode.equalsIgnoreCase("C9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C9));
		}
		if (FCode.equalsIgnoreCase("C10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C10));
		}
		if (FCode.equalsIgnoreCase("C11"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C11));
		}
		if (FCode.equalsIgnoreCase("C12"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C12));
		}
		if (FCode.equalsIgnoreCase("C13"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C13));
		}
		if (FCode.equalsIgnoreCase("C14"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C14));
		}
		if (FCode.equalsIgnoreCase("C15"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C15));
		}
		if (FCode.equalsIgnoreCase("C16"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C16));
		}
		if (FCode.equalsIgnoreCase("C17"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C17));
		}
		if (FCode.equalsIgnoreCase("C18"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C18));
		}
		if (FCode.equalsIgnoreCase("C19"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C19));
		}
		if (FCode.equalsIgnoreCase("C20"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C20));
		}
		if (FCode.equalsIgnoreCase("C21"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C21));
		}
		if (FCode.equalsIgnoreCase("C22"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C22));
		}
		if (FCode.equalsIgnoreCase("C23"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C23));
		}
		if (FCode.equalsIgnoreCase("C24"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C24));
		}
		if (FCode.equalsIgnoreCase("C25"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C25));
		}
		if (FCode.equalsIgnoreCase("C26"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C26));
		}
		if (FCode.equalsIgnoreCase("C27"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C27));
		}
		if (FCode.equalsIgnoreCase("C28"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C28));
		}
		if (FCode.equalsIgnoreCase("C29"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C29));
		}
		if (FCode.equalsIgnoreCase("C30"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C30));
		}
		if (FCode.equalsIgnoreCase("C31"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C31));
		}
		if (FCode.equalsIgnoreCase("C32"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C32));
		}
		if (FCode.equalsIgnoreCase("C33"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C33));
		}
		if (FCode.equalsIgnoreCase("C34"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C34));
		}
		if (FCode.equalsIgnoreCase("C35"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C35));
		}
		if (FCode.equalsIgnoreCase("C36"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C36));
		}
		if (FCode.equalsIgnoreCase("C37"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C37));
		}
		if (FCode.equalsIgnoreCase("C38"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C38));
		}
		if (FCode.equalsIgnoreCase("C39"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C39));
		}
		if (FCode.equalsIgnoreCase("C40"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C40));
		}
		if (FCode.equalsIgnoreCase("C41"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C41));
		}
		if (FCode.equalsIgnoreCase("C42"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C42));
		}
		if (FCode.equalsIgnoreCase("C43"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C43));
		}
		if (FCode.equalsIgnoreCase("C44"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C44));
		}
		if (FCode.equalsIgnoreCase("C45"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C45));
		}
		if (FCode.equalsIgnoreCase("C46"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C46));
		}
		if (FCode.equalsIgnoreCase("C47"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C47));
		}
		if (FCode.equalsIgnoreCase("C48"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C48));
		}
		if (FCode.equalsIgnoreCase("C49"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C49));
		}
		if (FCode.equalsIgnoreCase("C50"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(C50));
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(RepID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RepName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MngComName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Year);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(HalfYear);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Quarter);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(StartDate);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(EndDate);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ColID);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ColName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(C1);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(C2);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(C3);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(C4);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(C5);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(C6);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(C7);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(C8);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(C9);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(C10);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(C11);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(C12);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(C13);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(C14);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(C15);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(C16);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(C17);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(C18);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(C19);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(C20);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(C21);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(C22);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(C23);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(C24);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(C25);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(C26);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(C27);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(C28);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(C29);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(C30);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(C31);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(C32);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(C33);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(C34);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(C35);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(C36);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(C37);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(C38);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(C39);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(C40);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(C41);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(C42);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(C43);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(C44);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(C45);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(C46);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(C47);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(C48);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(C49);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(C50);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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

		if (FCode.equalsIgnoreCase("RepID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RepID = FValue.trim();
			}
			else
				RepID = null;
		}
		if (FCode.equalsIgnoreCase("RepName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RepName = FValue.trim();
			}
			else
				RepName = null;
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
		if (FCode.equalsIgnoreCase("MngComName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngComName = FValue.trim();
			}
			else
				MngComName = null;
		}
		if (FCode.equalsIgnoreCase("Year"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Year = FValue.trim();
			}
			else
				Year = null;
		}
		if (FCode.equalsIgnoreCase("HalfYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HalfYear = FValue.trim();
			}
			else
				HalfYear = null;
		}
		if (FCode.equalsIgnoreCase("Quarter"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Quarter = FValue.trim();
			}
			else
				Quarter = null;
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartDate = FValue.trim();
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndDate = FValue.trim();
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("ColID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ColID = FValue.trim();
			}
			else
				ColID = null;
		}
		if (FCode.equalsIgnoreCase("ColName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ColName = FValue.trim();
			}
			else
				ColName = null;
		}
		if (FCode.equalsIgnoreCase("C1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C1 = FValue.trim();
			}
			else
				C1 = null;
		}
		if (FCode.equalsIgnoreCase("C2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C2 = FValue.trim();
			}
			else
				C2 = null;
		}
		if (FCode.equalsIgnoreCase("C3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C3 = FValue.trim();
			}
			else
				C3 = null;
		}
		if (FCode.equalsIgnoreCase("C4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C4 = FValue.trim();
			}
			else
				C4 = null;
		}
		if (FCode.equalsIgnoreCase("C5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C5 = FValue.trim();
			}
			else
				C5 = null;
		}
		if (FCode.equalsIgnoreCase("C6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C6 = FValue.trim();
			}
			else
				C6 = null;
		}
		if (FCode.equalsIgnoreCase("C7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C7 = FValue.trim();
			}
			else
				C7 = null;
		}
		if (FCode.equalsIgnoreCase("C8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C8 = FValue.trim();
			}
			else
				C8 = null;
		}
		if (FCode.equalsIgnoreCase("C9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C9 = FValue.trim();
			}
			else
				C9 = null;
		}
		if (FCode.equalsIgnoreCase("C10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C10 = FValue.trim();
			}
			else
				C10 = null;
		}
		if (FCode.equalsIgnoreCase("C11"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C11 = FValue.trim();
			}
			else
				C11 = null;
		}
		if (FCode.equalsIgnoreCase("C12"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C12 = FValue.trim();
			}
			else
				C12 = null;
		}
		if (FCode.equalsIgnoreCase("C13"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C13 = FValue.trim();
			}
			else
				C13 = null;
		}
		if (FCode.equalsIgnoreCase("C14"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C14 = FValue.trim();
			}
			else
				C14 = null;
		}
		if (FCode.equalsIgnoreCase("C15"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C15 = FValue.trim();
			}
			else
				C15 = null;
		}
		if (FCode.equalsIgnoreCase("C16"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C16 = FValue.trim();
			}
			else
				C16 = null;
		}
		if (FCode.equalsIgnoreCase("C17"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C17 = FValue.trim();
			}
			else
				C17 = null;
		}
		if (FCode.equalsIgnoreCase("C18"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C18 = FValue.trim();
			}
			else
				C18 = null;
		}
		if (FCode.equalsIgnoreCase("C19"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C19 = FValue.trim();
			}
			else
				C19 = null;
		}
		if (FCode.equalsIgnoreCase("C20"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C20 = FValue.trim();
			}
			else
				C20 = null;
		}
		if (FCode.equalsIgnoreCase("C21"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C21 = FValue.trim();
			}
			else
				C21 = null;
		}
		if (FCode.equalsIgnoreCase("C22"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C22 = FValue.trim();
			}
			else
				C22 = null;
		}
		if (FCode.equalsIgnoreCase("C23"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C23 = FValue.trim();
			}
			else
				C23 = null;
		}
		if (FCode.equalsIgnoreCase("C24"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C24 = FValue.trim();
			}
			else
				C24 = null;
		}
		if (FCode.equalsIgnoreCase("C25"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C25 = FValue.trim();
			}
			else
				C25 = null;
		}
		if (FCode.equalsIgnoreCase("C26"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C26 = FValue.trim();
			}
			else
				C26 = null;
		}
		if (FCode.equalsIgnoreCase("C27"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C27 = FValue.trim();
			}
			else
				C27 = null;
		}
		if (FCode.equalsIgnoreCase("C28"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C28 = FValue.trim();
			}
			else
				C28 = null;
		}
		if (FCode.equalsIgnoreCase("C29"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C29 = FValue.trim();
			}
			else
				C29 = null;
		}
		if (FCode.equalsIgnoreCase("C30"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C30 = FValue.trim();
			}
			else
				C30 = null;
		}
		if (FCode.equalsIgnoreCase("C31"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C31 = FValue.trim();
			}
			else
				C31 = null;
		}
		if (FCode.equalsIgnoreCase("C32"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C32 = FValue.trim();
			}
			else
				C32 = null;
		}
		if (FCode.equalsIgnoreCase("C33"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C33 = FValue.trim();
			}
			else
				C33 = null;
		}
		if (FCode.equalsIgnoreCase("C34"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C34 = FValue.trim();
			}
			else
				C34 = null;
		}
		if (FCode.equalsIgnoreCase("C35"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C35 = FValue.trim();
			}
			else
				C35 = null;
		}
		if (FCode.equalsIgnoreCase("C36"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C36 = FValue.trim();
			}
			else
				C36 = null;
		}
		if (FCode.equalsIgnoreCase("C37"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C37 = FValue.trim();
			}
			else
				C37 = null;
		}
		if (FCode.equalsIgnoreCase("C38"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C38 = FValue.trim();
			}
			else
				C38 = null;
		}
		if (FCode.equalsIgnoreCase("C39"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C39 = FValue.trim();
			}
			else
				C39 = null;
		}
		if (FCode.equalsIgnoreCase("C40"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C40 = FValue.trim();
			}
			else
				C40 = null;
		}
		if (FCode.equalsIgnoreCase("C41"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C41 = FValue.trim();
			}
			else
				C41 = null;
		}
		if (FCode.equalsIgnoreCase("C42"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C42 = FValue.trim();
			}
			else
				C42 = null;
		}
		if (FCode.equalsIgnoreCase("C43"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C43 = FValue.trim();
			}
			else
				C43 = null;
		}
		if (FCode.equalsIgnoreCase("C44"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C44 = FValue.trim();
			}
			else
				C44 = null;
		}
		if (FCode.equalsIgnoreCase("C45"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C45 = FValue.trim();
			}
			else
				C45 = null;
		}
		if (FCode.equalsIgnoreCase("C46"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C46 = FValue.trim();
			}
			else
				C46 = null;
		}
		if (FCode.equalsIgnoreCase("C47"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C47 = FValue.trim();
			}
			else
				C47 = null;
		}
		if (FCode.equalsIgnoreCase("C48"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C48 = FValue.trim();
			}
			else
				C48 = null;
		}
		if (FCode.equalsIgnoreCase("C49"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C49 = FValue.trim();
			}
			else
				C49 = null;
		}
		if (FCode.equalsIgnoreCase("C50"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				C50 = FValue.trim();
			}
			else
				C50 = null;
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLRepColligateSchema other = (LLRepColligateSchema)otherObject;
		return
			RepID.equals(other.getRepID())
			&& RepName.equals(other.getRepName())
			&& MngCom.equals(other.getMngCom())
			&& MngComName.equals(other.getMngComName())
			&& Year.equals(other.getYear())
			&& HalfYear.equals(other.getHalfYear())
			&& Quarter.equals(other.getQuarter())
			&& StartDate.equals(other.getStartDate())
			&& EndDate.equals(other.getEndDate())
			&& ColID.equals(other.getColID())
			&& ColName.equals(other.getColName())
			&& C1.equals(other.getC1())
			&& C2.equals(other.getC2())
			&& C3.equals(other.getC3())
			&& C4.equals(other.getC4())
			&& C5.equals(other.getC5())
			&& C6.equals(other.getC6())
			&& C7.equals(other.getC7())
			&& C8.equals(other.getC8())
			&& C9.equals(other.getC9())
			&& C10.equals(other.getC10())
			&& C11.equals(other.getC11())
			&& C12.equals(other.getC12())
			&& C13.equals(other.getC13())
			&& C14.equals(other.getC14())
			&& C15.equals(other.getC15())
			&& C16.equals(other.getC16())
			&& C17.equals(other.getC17())
			&& C18.equals(other.getC18())
			&& C19.equals(other.getC19())
			&& C20.equals(other.getC20())
			&& C21.equals(other.getC21())
			&& C22.equals(other.getC22())
			&& C23.equals(other.getC23())
			&& C24.equals(other.getC24())
			&& C25.equals(other.getC25())
			&& C26.equals(other.getC26())
			&& C27.equals(other.getC27())
			&& C28.equals(other.getC28())
			&& C29.equals(other.getC29())
			&& C30.equals(other.getC30())
			&& C31.equals(other.getC31())
			&& C32.equals(other.getC32())
			&& C33.equals(other.getC33())
			&& C34.equals(other.getC34())
			&& C35.equals(other.getC35())
			&& C36.equals(other.getC36())
			&& C37.equals(other.getC37())
			&& C38.equals(other.getC38())
			&& C39.equals(other.getC39())
			&& C40.equals(other.getC40())
			&& C41.equals(other.getC41())
			&& C42.equals(other.getC42())
			&& C43.equals(other.getC43())
			&& C44.equals(other.getC44())
			&& C45.equals(other.getC45())
			&& C46.equals(other.getC46())
			&& C47.equals(other.getC47())
			&& C48.equals(other.getC48())
			&& C49.equals(other.getC49())
			&& C50.equals(other.getC50())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Operator.equals(other.getOperator())
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("RepID") ) {
			return 0;
		}
		if( strFieldName.equals("RepName") ) {
			return 1;
		}
		if( strFieldName.equals("MngCom") ) {
			return 2;
		}
		if( strFieldName.equals("MngComName") ) {
			return 3;
		}
		if( strFieldName.equals("Year") ) {
			return 4;
		}
		if( strFieldName.equals("HalfYear") ) {
			return 5;
		}
		if( strFieldName.equals("Quarter") ) {
			return 6;
		}
		if( strFieldName.equals("StartDate") ) {
			return 7;
		}
		if( strFieldName.equals("EndDate") ) {
			return 8;
		}
		if( strFieldName.equals("ColID") ) {
			return 9;
		}
		if( strFieldName.equals("ColName") ) {
			return 10;
		}
		if( strFieldName.equals("C1") ) {
			return 11;
		}
		if( strFieldName.equals("C2") ) {
			return 12;
		}
		if( strFieldName.equals("C3") ) {
			return 13;
		}
		if( strFieldName.equals("C4") ) {
			return 14;
		}
		if( strFieldName.equals("C5") ) {
			return 15;
		}
		if( strFieldName.equals("C6") ) {
			return 16;
		}
		if( strFieldName.equals("C7") ) {
			return 17;
		}
		if( strFieldName.equals("C8") ) {
			return 18;
		}
		if( strFieldName.equals("C9") ) {
			return 19;
		}
		if( strFieldName.equals("C10") ) {
			return 20;
		}
		if( strFieldName.equals("C11") ) {
			return 21;
		}
		if( strFieldName.equals("C12") ) {
			return 22;
		}
		if( strFieldName.equals("C13") ) {
			return 23;
		}
		if( strFieldName.equals("C14") ) {
			return 24;
		}
		if( strFieldName.equals("C15") ) {
			return 25;
		}
		if( strFieldName.equals("C16") ) {
			return 26;
		}
		if( strFieldName.equals("C17") ) {
			return 27;
		}
		if( strFieldName.equals("C18") ) {
			return 28;
		}
		if( strFieldName.equals("C19") ) {
			return 29;
		}
		if( strFieldName.equals("C20") ) {
			return 30;
		}
		if( strFieldName.equals("C21") ) {
			return 31;
		}
		if( strFieldName.equals("C22") ) {
			return 32;
		}
		if( strFieldName.equals("C23") ) {
			return 33;
		}
		if( strFieldName.equals("C24") ) {
			return 34;
		}
		if( strFieldName.equals("C25") ) {
			return 35;
		}
		if( strFieldName.equals("C26") ) {
			return 36;
		}
		if( strFieldName.equals("C27") ) {
			return 37;
		}
		if( strFieldName.equals("C28") ) {
			return 38;
		}
		if( strFieldName.equals("C29") ) {
			return 39;
		}
		if( strFieldName.equals("C30") ) {
			return 40;
		}
		if( strFieldName.equals("C31") ) {
			return 41;
		}
		if( strFieldName.equals("C32") ) {
			return 42;
		}
		if( strFieldName.equals("C33") ) {
			return 43;
		}
		if( strFieldName.equals("C34") ) {
			return 44;
		}
		if( strFieldName.equals("C35") ) {
			return 45;
		}
		if( strFieldName.equals("C36") ) {
			return 46;
		}
		if( strFieldName.equals("C37") ) {
			return 47;
		}
		if( strFieldName.equals("C38") ) {
			return 48;
		}
		if( strFieldName.equals("C39") ) {
			return 49;
		}
		if( strFieldName.equals("C40") ) {
			return 50;
		}
		if( strFieldName.equals("C41") ) {
			return 51;
		}
		if( strFieldName.equals("C42") ) {
			return 52;
		}
		if( strFieldName.equals("C43") ) {
			return 53;
		}
		if( strFieldName.equals("C44") ) {
			return 54;
		}
		if( strFieldName.equals("C45") ) {
			return 55;
		}
		if( strFieldName.equals("C46") ) {
			return 56;
		}
		if( strFieldName.equals("C47") ) {
			return 57;
		}
		if( strFieldName.equals("C48") ) {
			return 58;
		}
		if( strFieldName.equals("C49") ) {
			return 59;
		}
		if( strFieldName.equals("C50") ) {
			return 60;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 61;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 62;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 63;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 64;
		}
		if( strFieldName.equals("Operator") ) {
			return 65;
		}
		if( strFieldName.equals("Remark") ) {
			return 66;
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
				strFieldName = "RepID";
				break;
			case 1:
				strFieldName = "RepName";
				break;
			case 2:
				strFieldName = "MngCom";
				break;
			case 3:
				strFieldName = "MngComName";
				break;
			case 4:
				strFieldName = "Year";
				break;
			case 5:
				strFieldName = "HalfYear";
				break;
			case 6:
				strFieldName = "Quarter";
				break;
			case 7:
				strFieldName = "StartDate";
				break;
			case 8:
				strFieldName = "EndDate";
				break;
			case 9:
				strFieldName = "ColID";
				break;
			case 10:
				strFieldName = "ColName";
				break;
			case 11:
				strFieldName = "C1";
				break;
			case 12:
				strFieldName = "C2";
				break;
			case 13:
				strFieldName = "C3";
				break;
			case 14:
				strFieldName = "C4";
				break;
			case 15:
				strFieldName = "C5";
				break;
			case 16:
				strFieldName = "C6";
				break;
			case 17:
				strFieldName = "C7";
				break;
			case 18:
				strFieldName = "C8";
				break;
			case 19:
				strFieldName = "C9";
				break;
			case 20:
				strFieldName = "C10";
				break;
			case 21:
				strFieldName = "C11";
				break;
			case 22:
				strFieldName = "C12";
				break;
			case 23:
				strFieldName = "C13";
				break;
			case 24:
				strFieldName = "C14";
				break;
			case 25:
				strFieldName = "C15";
				break;
			case 26:
				strFieldName = "C16";
				break;
			case 27:
				strFieldName = "C17";
				break;
			case 28:
				strFieldName = "C18";
				break;
			case 29:
				strFieldName = "C19";
				break;
			case 30:
				strFieldName = "C20";
				break;
			case 31:
				strFieldName = "C21";
				break;
			case 32:
				strFieldName = "C22";
				break;
			case 33:
				strFieldName = "C23";
				break;
			case 34:
				strFieldName = "C24";
				break;
			case 35:
				strFieldName = "C25";
				break;
			case 36:
				strFieldName = "C26";
				break;
			case 37:
				strFieldName = "C27";
				break;
			case 38:
				strFieldName = "C28";
				break;
			case 39:
				strFieldName = "C29";
				break;
			case 40:
				strFieldName = "C30";
				break;
			case 41:
				strFieldName = "C31";
				break;
			case 42:
				strFieldName = "C32";
				break;
			case 43:
				strFieldName = "C33";
				break;
			case 44:
				strFieldName = "C34";
				break;
			case 45:
				strFieldName = "C35";
				break;
			case 46:
				strFieldName = "C36";
				break;
			case 47:
				strFieldName = "C37";
				break;
			case 48:
				strFieldName = "C38";
				break;
			case 49:
				strFieldName = "C39";
				break;
			case 50:
				strFieldName = "C40";
				break;
			case 51:
				strFieldName = "C41";
				break;
			case 52:
				strFieldName = "C42";
				break;
			case 53:
				strFieldName = "C43";
				break;
			case 54:
				strFieldName = "C44";
				break;
			case 55:
				strFieldName = "C45";
				break;
			case 56:
				strFieldName = "C46";
				break;
			case 57:
				strFieldName = "C47";
				break;
			case 58:
				strFieldName = "C48";
				break;
			case 59:
				strFieldName = "C49";
				break;
			case 60:
				strFieldName = "C50";
				break;
			case 61:
				strFieldName = "MakeDate";
				break;
			case 62:
				strFieldName = "MakeTime";
				break;
			case 63:
				strFieldName = "ModifyDate";
				break;
			case 64:
				strFieldName = "ModifyTime";
				break;
			case 65:
				strFieldName = "Operator";
				break;
			case 66:
				strFieldName = "Remark";
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
		if( strFieldName.equals("RepID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RepName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MngComName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Year") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HalfYear") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Quarter") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ColID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ColName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C11") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C12") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C13") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C14") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C15") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C16") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C17") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C18") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C19") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C20") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C21") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C22") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C23") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C24") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C25") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C26") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C27") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C28") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C29") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C30") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C31") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C32") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C33") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C34") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C35") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C36") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C37") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C38") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C39") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C40") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C41") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C42") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C43") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C44") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C45") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C46") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C47") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C48") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C49") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("C50") ) {
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
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 52:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 53:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 54:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 55:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 56:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 57:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 58:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 59:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 60:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 61:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 66:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
