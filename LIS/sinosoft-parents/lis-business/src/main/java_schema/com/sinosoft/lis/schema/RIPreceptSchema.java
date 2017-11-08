

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RIPreceptDB;

/*
 * <p>ClassName: RIPreceptSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIPreceptSchema implements Schema, Cloneable
{
	// @Field
	/** 再保合同号码 */
	private String RIContNo;
	/** 再保方案号码 */
	private String RIPreceptNo;
	/** 再保方案名称 */
	private String RIPreceptName;
	/** 主险再保方案号码 */
	private String RIMainPreceptNo;
	/** 方案类型 */
	private String PreceptType;
	/** 分保类型 */
	private String ReinsuranceType;
	/** 分保方式 */
	private String ReinsuranceType1;
	/** 累计方案编码 */
	private String AccumulateDefNO;
	/** 分保公司数目 */
	private int CompanyNum;
	/** 层次数值类型 */
	private String HierarchyNumType;
	/** 溢额层数 */
	private int DivisionNum;
	/** 算法编码 */
	private String ArithmeticID;
	/** 状态 */
	private String State;
	/** 币别 */
	private String Currency;
	/** 备用字符串属性1 */
	private String StandbyString1;
	/** 备用字符串属性2 */
	private String StandbyString2;
	/** 备用字符串属性3 */
	private String StandbyString3;
	/** 依附主险标志 */
	private String AttachFalg;
	/** 红利标志 */
	private String Bonus;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIPreceptSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RIPreceptNo";

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
		RIPreceptSchema cloned = (RIPreceptSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRIContNo()
	{
		return RIContNo;
	}
	public void setRIContNo(String aRIContNo)
	{
		RIContNo = aRIContNo;
	}
	public String getRIPreceptNo()
	{
		return RIPreceptNo;
	}
	public void setRIPreceptNo(String aRIPreceptNo)
	{
		RIPreceptNo = aRIPreceptNo;
	}
	public String getRIPreceptName()
	{
		return RIPreceptName;
	}
	public void setRIPreceptName(String aRIPreceptName)
	{
		RIPreceptName = aRIPreceptName;
	}
	public String getRIMainPreceptNo()
	{
		return RIMainPreceptNo;
	}
	public void setRIMainPreceptNo(String aRIMainPreceptNo)
	{
		RIMainPreceptNo = aRIMainPreceptNo;
	}
	/**
	* 01-正常合同方案 <p>
	* 02-临分合同方案
	*/
	public String getPreceptType()
	{
		return PreceptType;
	}
	public void setPreceptType(String aPreceptType)
	{
		PreceptType = aPreceptType;
	}
	/**
	* 01-比例分保<p>
	* 02-非比例分保
	*/
	public String getReinsuranceType()
	{
		return ReinsuranceType;
	}
	public void setReinsuranceType(String aReinsuranceType)
	{
		ReinsuranceType = aReinsuranceType;
	}
	/**
	* 01-成数分保<p>
	* 02-溢额分保<p>
	* 03-成数溢额混合分保<p>
	* 04-险位超赔<p>
	* 05-事故超赔<p>
	* 06-赔付率超赔
	*/
	public String getReinsuranceType1()
	{
		return ReinsuranceType1;
	}
	public void setReinsuranceType1(String aReinsuranceType1)
	{
		ReinsuranceType1 = aReinsuranceType1;
	}
	/**
	* 1-代表险种级别 2-代表责任级别
	*/
	public String getAccumulateDefNO()
	{
		return AccumulateDefNO;
	}
	public void setAccumulateDefNO(String aAccumulateDefNO)
	{
		AccumulateDefNO = aAccumulateDefNO;
	}
	public int getCompanyNum()
	{
		return CompanyNum;
	}
	public void setCompanyNum(int aCompanyNum)
	{
		CompanyNum = aCompanyNum;
	}
	public void setCompanyNum(String aCompanyNum)
	{
		if (aCompanyNum != null && !aCompanyNum.equals(""))
		{
			Integer tInteger = new Integer(aCompanyNum);
			int i = tInteger.intValue();
			CompanyNum = i;
		}
	}

	/**
	* 01-风险保额<p>
	* 02-赔付额<p>
	* 03-赔付率
	*/
	public String getHierarchyNumType()
	{
		return HierarchyNumType;
	}
	public void setHierarchyNumType(String aHierarchyNumType)
	{
		HierarchyNumType = aHierarchyNumType;
	}
	public int getDivisionNum()
	{
		return DivisionNum;
	}
	public void setDivisionNum(int aDivisionNum)
	{
		DivisionNum = aDivisionNum;
	}
	public void setDivisionNum(String aDivisionNum)
	{
		if (aDivisionNum != null && !aDivisionNum.equals(""))
		{
			Integer tInteger = new Integer(aDivisionNum);
			int i = tInteger.intValue();
			DivisionNum = i;
		}
	}

	public String getArithmeticID()
	{
		return ArithmeticID;
	}
	public void setArithmeticID(String aArithmeticID)
	{
		ArithmeticID = aArithmeticID;
	}
	/**
	* 01:有效，02：无效，99：样例
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	public String getStandbyString1()
	{
		return StandbyString1;
	}
	public void setStandbyString1(String aStandbyString1)
	{
		StandbyString1 = aStandbyString1;
	}
	public String getStandbyString2()
	{
		return StandbyString2;
	}
	public void setStandbyString2(String aStandbyString2)
	{
		StandbyString2 = aStandbyString2;
	}
	public String getStandbyString3()
	{
		return StandbyString3;
	}
	public void setStandbyString3(String aStandbyString3)
	{
		StandbyString3 = aStandbyString3;
	}
	/**
	* 02-不依附主险<p>
	* 01-依附主险
	*/
	public String getAttachFalg()
	{
		return AttachFalg;
	}
	public void setAttachFalg(String aAttachFalg)
	{
		AttachFalg = aAttachFalg;
	}
	public String getBonus()
	{
		return Bonus;
	}
	public void setBonus(String aBonus)
	{
		Bonus = aBonus;
	}

	/**
	* 使用另外一个 RIPreceptSchema 对象给 Schema 赋值
	* @param: aRIPreceptSchema RIPreceptSchema
	**/
	public void setSchema(RIPreceptSchema aRIPreceptSchema)
	{
		this.RIContNo = aRIPreceptSchema.getRIContNo();
		this.RIPreceptNo = aRIPreceptSchema.getRIPreceptNo();
		this.RIPreceptName = aRIPreceptSchema.getRIPreceptName();
		this.RIMainPreceptNo = aRIPreceptSchema.getRIMainPreceptNo();
		this.PreceptType = aRIPreceptSchema.getPreceptType();
		this.ReinsuranceType = aRIPreceptSchema.getReinsuranceType();
		this.ReinsuranceType1 = aRIPreceptSchema.getReinsuranceType1();
		this.AccumulateDefNO = aRIPreceptSchema.getAccumulateDefNO();
		this.CompanyNum = aRIPreceptSchema.getCompanyNum();
		this.HierarchyNumType = aRIPreceptSchema.getHierarchyNumType();
		this.DivisionNum = aRIPreceptSchema.getDivisionNum();
		this.ArithmeticID = aRIPreceptSchema.getArithmeticID();
		this.State = aRIPreceptSchema.getState();
		this.Currency = aRIPreceptSchema.getCurrency();
		this.StandbyString1 = aRIPreceptSchema.getStandbyString1();
		this.StandbyString2 = aRIPreceptSchema.getStandbyString2();
		this.StandbyString3 = aRIPreceptSchema.getStandbyString3();
		this.AttachFalg = aRIPreceptSchema.getAttachFalg();
		this.Bonus = aRIPreceptSchema.getBonus();
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
			if( rs.getString("RIContNo") == null )
				this.RIContNo = null;
			else
				this.RIContNo = rs.getString("RIContNo").trim();

			if( rs.getString("RIPreceptNo") == null )
				this.RIPreceptNo = null;
			else
				this.RIPreceptNo = rs.getString("RIPreceptNo").trim();

			if( rs.getString("RIPreceptName") == null )
				this.RIPreceptName = null;
			else
				this.RIPreceptName = rs.getString("RIPreceptName").trim();

			if( rs.getString("RIMainPreceptNo") == null )
				this.RIMainPreceptNo = null;
			else
				this.RIMainPreceptNo = rs.getString("RIMainPreceptNo").trim();

			if( rs.getString("PreceptType") == null )
				this.PreceptType = null;
			else
				this.PreceptType = rs.getString("PreceptType").trim();

			if( rs.getString("ReinsuranceType") == null )
				this.ReinsuranceType = null;
			else
				this.ReinsuranceType = rs.getString("ReinsuranceType").trim();

			if( rs.getString("ReinsuranceType1") == null )
				this.ReinsuranceType1 = null;
			else
				this.ReinsuranceType1 = rs.getString("ReinsuranceType1").trim();

			if( rs.getString("AccumulateDefNO") == null )
				this.AccumulateDefNO = null;
			else
				this.AccumulateDefNO = rs.getString("AccumulateDefNO").trim();

			this.CompanyNum = rs.getInt("CompanyNum");
			if( rs.getString("HierarchyNumType") == null )
				this.HierarchyNumType = null;
			else
				this.HierarchyNumType = rs.getString("HierarchyNumType").trim();

			this.DivisionNum = rs.getInt("DivisionNum");
			if( rs.getString("ArithmeticID") == null )
				this.ArithmeticID = null;
			else
				this.ArithmeticID = rs.getString("ArithmeticID").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("StandbyString1") == null )
				this.StandbyString1 = null;
			else
				this.StandbyString1 = rs.getString("StandbyString1").trim();

			if( rs.getString("StandbyString2") == null )
				this.StandbyString2 = null;
			else
				this.StandbyString2 = rs.getString("StandbyString2").trim();

			if( rs.getString("StandbyString3") == null )
				this.StandbyString3 = null;
			else
				this.StandbyString3 = rs.getString("StandbyString3").trim();

			if( rs.getString("AttachFalg") == null )
				this.AttachFalg = null;
			else
				this.AttachFalg = rs.getString("AttachFalg").trim();

			if( rs.getString("Bonus") == null )
				this.Bonus = null;
			else
				this.Bonus = rs.getString("Bonus").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIPrecept表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIPreceptSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIPreceptSchema getSchema()
	{
		RIPreceptSchema aRIPreceptSchema = new RIPreceptSchema();
		aRIPreceptSchema.setSchema(this);
		return aRIPreceptSchema;
	}

	public RIPreceptDB getDB()
	{
		RIPreceptDB aDBOper = new RIPreceptDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIPrecept描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RIContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIPreceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIPreceptName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIMainPreceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PreceptType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsuranceType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsuranceType1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccumulateDefNO)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CompanyNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HierarchyNumType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DivisionNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ArithmeticID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AttachFalg)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Bonus));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIPrecept>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RIContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RIPreceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RIPreceptName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RIMainPreceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PreceptType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ReinsuranceType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ReinsuranceType1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AccumulateDefNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CompanyNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			HierarchyNumType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			DivisionNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			ArithmeticID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			StandbyString1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			StandbyString2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			StandbyString3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AttachFalg = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Bonus = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIPreceptSchema";
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
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIContNo));
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIPreceptNo));
		}
		if (FCode.equalsIgnoreCase("RIPreceptName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIPreceptName));
		}
		if (FCode.equalsIgnoreCase("RIMainPreceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIMainPreceptNo));
		}
		if (FCode.equalsIgnoreCase("PreceptType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreceptType));
		}
		if (FCode.equalsIgnoreCase("ReinsuranceType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsuranceType));
		}
		if (FCode.equalsIgnoreCase("ReinsuranceType1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsuranceType1));
		}
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccumulateDefNO));
		}
		if (FCode.equalsIgnoreCase("CompanyNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyNum));
		}
		if (FCode.equalsIgnoreCase("HierarchyNumType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HierarchyNumType));
		}
		if (FCode.equalsIgnoreCase("DivisionNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DivisionNum));
		}
		if (FCode.equalsIgnoreCase("ArithmeticID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ArithmeticID));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("StandbyString1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString1));
		}
		if (FCode.equalsIgnoreCase("StandbyString2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString2));
		}
		if (FCode.equalsIgnoreCase("StandbyString3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString3));
		}
		if (FCode.equalsIgnoreCase("AttachFalg"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AttachFalg));
		}
		if (FCode.equalsIgnoreCase("Bonus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bonus));
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
				strFieldValue = StrTool.GBKToUnicode(RIContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RIPreceptNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RIPreceptName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RIMainPreceptNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PreceptType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ReinsuranceType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ReinsuranceType1);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AccumulateDefNO);
				break;
			case 8:
				strFieldValue = String.valueOf(CompanyNum);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(HierarchyNumType);
				break;
			case 10:
				strFieldValue = String.valueOf(DivisionNum);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ArithmeticID);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(StandbyString1);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(StandbyString2);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(StandbyString3);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AttachFalg);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Bonus);
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

		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIContNo = FValue.trim();
			}
			else
				RIContNo = null;
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIPreceptNo = FValue.trim();
			}
			else
				RIPreceptNo = null;
		}
		if (FCode.equalsIgnoreCase("RIPreceptName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIPreceptName = FValue.trim();
			}
			else
				RIPreceptName = null;
		}
		if (FCode.equalsIgnoreCase("RIMainPreceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIMainPreceptNo = FValue.trim();
			}
			else
				RIMainPreceptNo = null;
		}
		if (FCode.equalsIgnoreCase("PreceptType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PreceptType = FValue.trim();
			}
			else
				PreceptType = null;
		}
		if (FCode.equalsIgnoreCase("ReinsuranceType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsuranceType = FValue.trim();
			}
			else
				ReinsuranceType = null;
		}
		if (FCode.equalsIgnoreCase("ReinsuranceType1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsuranceType1 = FValue.trim();
			}
			else
				ReinsuranceType1 = null;
		}
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccumulateDefNO = FValue.trim();
			}
			else
				AccumulateDefNO = null;
		}
		if (FCode.equalsIgnoreCase("CompanyNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CompanyNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("HierarchyNumType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HierarchyNumType = FValue.trim();
			}
			else
				HierarchyNumType = null;
		}
		if (FCode.equalsIgnoreCase("DivisionNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				DivisionNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("ArithmeticID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ArithmeticID = FValue.trim();
			}
			else
				ArithmeticID = null;
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
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString1 = FValue.trim();
			}
			else
				StandbyString1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString2 = FValue.trim();
			}
			else
				StandbyString2 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString3 = FValue.trim();
			}
			else
				StandbyString3 = null;
		}
		if (FCode.equalsIgnoreCase("AttachFalg"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AttachFalg = FValue.trim();
			}
			else
				AttachFalg = null;
		}
		if (FCode.equalsIgnoreCase("Bonus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bonus = FValue.trim();
			}
			else
				Bonus = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIPreceptSchema other = (RIPreceptSchema)otherObject;
		return
			RIContNo.equals(other.getRIContNo())
			&& RIPreceptNo.equals(other.getRIPreceptNo())
			&& RIPreceptName.equals(other.getRIPreceptName())
			&& RIMainPreceptNo.equals(other.getRIMainPreceptNo())
			&& PreceptType.equals(other.getPreceptType())
			&& ReinsuranceType.equals(other.getReinsuranceType())
			&& ReinsuranceType1.equals(other.getReinsuranceType1())
			&& AccumulateDefNO.equals(other.getAccumulateDefNO())
			&& CompanyNum == other.getCompanyNum()
			&& HierarchyNumType.equals(other.getHierarchyNumType())
			&& DivisionNum == other.getDivisionNum()
			&& ArithmeticID.equals(other.getArithmeticID())
			&& State.equals(other.getState())
			&& Currency.equals(other.getCurrency())
			&& StandbyString1.equals(other.getStandbyString1())
			&& StandbyString2.equals(other.getStandbyString2())
			&& StandbyString3.equals(other.getStandbyString3())
			&& AttachFalg.equals(other.getAttachFalg())
			&& Bonus.equals(other.getBonus());
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
		if( strFieldName.equals("RIContNo") ) {
			return 0;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return 1;
		}
		if( strFieldName.equals("RIPreceptName") ) {
			return 2;
		}
		if( strFieldName.equals("RIMainPreceptNo") ) {
			return 3;
		}
		if( strFieldName.equals("PreceptType") ) {
			return 4;
		}
		if( strFieldName.equals("ReinsuranceType") ) {
			return 5;
		}
		if( strFieldName.equals("ReinsuranceType1") ) {
			return 6;
		}
		if( strFieldName.equals("AccumulateDefNO") ) {
			return 7;
		}
		if( strFieldName.equals("CompanyNum") ) {
			return 8;
		}
		if( strFieldName.equals("HierarchyNumType") ) {
			return 9;
		}
		if( strFieldName.equals("DivisionNum") ) {
			return 10;
		}
		if( strFieldName.equals("ArithmeticID") ) {
			return 11;
		}
		if( strFieldName.equals("State") ) {
			return 12;
		}
		if( strFieldName.equals("Currency") ) {
			return 13;
		}
		if( strFieldName.equals("StandbyString1") ) {
			return 14;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return 15;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return 16;
		}
		if( strFieldName.equals("AttachFalg") ) {
			return 17;
		}
		if( strFieldName.equals("Bonus") ) {
			return 18;
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
				strFieldName = "RIContNo";
				break;
			case 1:
				strFieldName = "RIPreceptNo";
				break;
			case 2:
				strFieldName = "RIPreceptName";
				break;
			case 3:
				strFieldName = "RIMainPreceptNo";
				break;
			case 4:
				strFieldName = "PreceptType";
				break;
			case 5:
				strFieldName = "ReinsuranceType";
				break;
			case 6:
				strFieldName = "ReinsuranceType1";
				break;
			case 7:
				strFieldName = "AccumulateDefNO";
				break;
			case 8:
				strFieldName = "CompanyNum";
				break;
			case 9:
				strFieldName = "HierarchyNumType";
				break;
			case 10:
				strFieldName = "DivisionNum";
				break;
			case 11:
				strFieldName = "ArithmeticID";
				break;
			case 12:
				strFieldName = "State";
				break;
			case 13:
				strFieldName = "Currency";
				break;
			case 14:
				strFieldName = "StandbyString1";
				break;
			case 15:
				strFieldName = "StandbyString2";
				break;
			case 16:
				strFieldName = "StandbyString3";
				break;
			case 17:
				strFieldName = "AttachFalg";
				break;
			case 18:
				strFieldName = "Bonus";
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
		if( strFieldName.equals("RIContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIPreceptName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIMainPreceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PreceptType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsuranceType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsuranceType1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccumulateDefNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CompanyNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("HierarchyNumType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DivisionNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ArithmeticID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AttachFalg") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bonus") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
