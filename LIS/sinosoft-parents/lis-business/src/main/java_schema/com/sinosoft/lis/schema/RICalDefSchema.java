

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
import com.sinosoft.lis.db.RICalDefDB;

/*
 * <p>ClassName: RICalDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RICalDefSchema implements Schema, Cloneable
{
	// @Field
	/** 算法定义编码 */
	private String ArithmeticDefID;
	/** 算法定义名称 */
	private String ArithmeticDefName;
	/** 方案类型 */
	private String PreceptType;
	/** 算法类型 */
	private String ArithType;
	/** 算法子类型 */
	private String ArithSubType;
	/** 计算期间 */
	private String CalFeeTerm;
	/** 计算方式 */
	private String CalFeeType;
	/** 累计风险编码 */
	private String AccumulateDefNO;
	/** 再保方案编码 */
	private String RIPreceptNo;
	/** 描述 */
	private String ReMark;
	/** 状态 */
	private String State;
	/** 备用字符串属性1 */
	private String StandbyString1;
	/** 备用字符串属性2 */
	private String StandbyString2;
	/** 备用字符串属性3 */
	private String StandbyString3;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RICalDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ArithmeticDefID";

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
		RICalDefSchema cloned = (RICalDefSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getArithmeticDefID()
	{
		return ArithmeticDefID;
	}
	public void setArithmeticDefID(String aArithmeticDefID)
	{
		ArithmeticDefID = aArithmeticDefID;
	}
	public String getArithmeticDefName()
	{
		return ArithmeticDefName;
	}
	public void setArithmeticDefName(String aArithmeticDefName)
	{
		ArithmeticDefName = aArithmeticDefName;
	}
	/**
	* 01-共保<p>
	* 02-合同<p>
	* 03-临分
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
	* 01-通用算法<p>
	* 02-特殊算法
	*/
	public String getArithType()
	{
		return ArithType;
	}
	public void setArithType(String aArithType)
	{
		ArithType = aArithType;
	}
	/**
	* L：分出责任算法:<p>
	* 01-数据提取算法<p>
	* 02-数据校验算法<p>
	* 03-风险保额算法<p>
	* -----------------------------------------<p>
	* P：方案算法:<p>
	* 11-方案分配算法<p>
	* 12-分保参数算法<p>
	* 13-计算项算法<p>
	* <p>
	* 14-计算处理类算法<p>
	* <p>
	* ------------------------------------------<p>
	* F：临分核保规则(未启用)<p>
	* ------------------------------------------<p>
	* C：再保核赔规则(未启用)
	*/
	public String getArithSubType()
	{
		return ArithSubType;
	}
	public void setArithSubType(String aArithSubType)
	{
		ArithSubType = aArithSubType;
	}
	/**
	* 01：按月计算<p>
	* <p>
	* 02：按年计算
	*/
	public String getCalFeeTerm()
	{
		return CalFeeTerm;
	}
	public void setCalFeeTerm(String aCalFeeTerm)
	{
		CalFeeTerm = aCalFeeTerm;
	}
	/**
	* 01：按保费计算方式<p>
	* 02：按保额计算方式
	*/
	public String getCalFeeType()
	{
		return CalFeeType;
	}
	public void setCalFeeType(String aCalFeeType)
	{
		CalFeeType = aCalFeeType;
	}
	public String getAccumulateDefNO()
	{
		return AccumulateDefNO;
	}
	public void setAccumulateDefNO(String aAccumulateDefNO)
	{
		AccumulateDefNO = aAccumulateDefNO;
	}
	public String getRIPreceptNo()
	{
		return RIPreceptNo;
	}
	public void setRIPreceptNo(String aRIPreceptNo)
	{
		RIPreceptNo = aRIPreceptNo;
	}
	/**
	* 如果数据保全计算的计算项，此字段保存参与此计算项计算的关联的保全项目名称，关联多个用逗号分隔
	*/
	public String getReMark()
	{
		return ReMark;
	}
	public void setReMark(String aReMark)
	{
		ReMark = aReMark;
	}
	/**
	* 01 -- 有效<p>
	* 02 --未生效
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	/**
	* L-累计风险算法<p>
	* P-再保方案算法
	*/
	public String getStandbyString1()
	{
		return StandbyString1;
	}
	public void setStandbyString1(String aStandbyString1)
	{
		StandbyString1 = aStandbyString1;
	}
	/**
	* 累计风险编码
	*/
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
	* 使用另外一个 RICalDefSchema 对象给 Schema 赋值
	* @param: aRICalDefSchema RICalDefSchema
	**/
	public void setSchema(RICalDefSchema aRICalDefSchema)
	{
		this.ArithmeticDefID = aRICalDefSchema.getArithmeticDefID();
		this.ArithmeticDefName = aRICalDefSchema.getArithmeticDefName();
		this.PreceptType = aRICalDefSchema.getPreceptType();
		this.ArithType = aRICalDefSchema.getArithType();
		this.ArithSubType = aRICalDefSchema.getArithSubType();
		this.CalFeeTerm = aRICalDefSchema.getCalFeeTerm();
		this.CalFeeType = aRICalDefSchema.getCalFeeType();
		this.AccumulateDefNO = aRICalDefSchema.getAccumulateDefNO();
		this.RIPreceptNo = aRICalDefSchema.getRIPreceptNo();
		this.ReMark = aRICalDefSchema.getReMark();
		this.State = aRICalDefSchema.getState();
		this.StandbyString1 = aRICalDefSchema.getStandbyString1();
		this.StandbyString2 = aRICalDefSchema.getStandbyString2();
		this.StandbyString3 = aRICalDefSchema.getStandbyString3();
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
			if( rs.getString("ArithmeticDefID") == null )
				this.ArithmeticDefID = null;
			else
				this.ArithmeticDefID = rs.getString("ArithmeticDefID").trim();

			if( rs.getString("ArithmeticDefName") == null )
				this.ArithmeticDefName = null;
			else
				this.ArithmeticDefName = rs.getString("ArithmeticDefName").trim();

			if( rs.getString("PreceptType") == null )
				this.PreceptType = null;
			else
				this.PreceptType = rs.getString("PreceptType").trim();

			if( rs.getString("ArithType") == null )
				this.ArithType = null;
			else
				this.ArithType = rs.getString("ArithType").trim();

			if( rs.getString("ArithSubType") == null )
				this.ArithSubType = null;
			else
				this.ArithSubType = rs.getString("ArithSubType").trim();

			if( rs.getString("CalFeeTerm") == null )
				this.CalFeeTerm = null;
			else
				this.CalFeeTerm = rs.getString("CalFeeTerm").trim();

			if( rs.getString("CalFeeType") == null )
				this.CalFeeType = null;
			else
				this.CalFeeType = rs.getString("CalFeeType").trim();

			if( rs.getString("AccumulateDefNO") == null )
				this.AccumulateDefNO = null;
			else
				this.AccumulateDefNO = rs.getString("AccumulateDefNO").trim();

			if( rs.getString("RIPreceptNo") == null )
				this.RIPreceptNo = null;
			else
				this.RIPreceptNo = rs.getString("RIPreceptNo").trim();

			if( rs.getString("ReMark") == null )
				this.ReMark = null;
			else
				this.ReMark = rs.getString("ReMark").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RICalDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RICalDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RICalDefSchema getSchema()
	{
		RICalDefSchema aRICalDefSchema = new RICalDefSchema();
		aRICalDefSchema.setSchema(this);
		return aRICalDefSchema;
	}

	public RICalDefDB getDB()
	{
		RICalDefDB aDBOper = new RICalDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRICalDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ArithmeticDefID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ArithmeticDefName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PreceptType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ArithType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ArithSubType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFeeTerm)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccumulateDefNO)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIPreceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReMark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString3));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRICalDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ArithmeticDefID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ArithmeticDefName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PreceptType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ArithType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ArithSubType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CalFeeTerm = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CalFeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AccumulateDefNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RIPreceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			StandbyString1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			StandbyString2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StandbyString3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RICalDefSchema";
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
		if (FCode.equalsIgnoreCase("ArithmeticDefID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ArithmeticDefID));
		}
		if (FCode.equalsIgnoreCase("ArithmeticDefName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ArithmeticDefName));
		}
		if (FCode.equalsIgnoreCase("PreceptType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreceptType));
		}
		if (FCode.equalsIgnoreCase("ArithType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ArithType));
		}
		if (FCode.equalsIgnoreCase("ArithSubType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ArithSubType));
		}
		if (FCode.equalsIgnoreCase("CalFeeTerm"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFeeTerm));
		}
		if (FCode.equalsIgnoreCase("CalFeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFeeType));
		}
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccumulateDefNO));
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIPreceptNo));
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReMark));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(ArithmeticDefID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ArithmeticDefName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PreceptType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ArithType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ArithSubType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CalFeeTerm);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CalFeeType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AccumulateDefNO);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RIPreceptNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ReMark);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(StandbyString1);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(StandbyString2);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StandbyString3);
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

		if (FCode.equalsIgnoreCase("ArithmeticDefID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ArithmeticDefID = FValue.trim();
			}
			else
				ArithmeticDefID = null;
		}
		if (FCode.equalsIgnoreCase("ArithmeticDefName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ArithmeticDefName = FValue.trim();
			}
			else
				ArithmeticDefName = null;
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
		if (FCode.equalsIgnoreCase("ArithType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ArithType = FValue.trim();
			}
			else
				ArithType = null;
		}
		if (FCode.equalsIgnoreCase("ArithSubType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ArithSubType = FValue.trim();
			}
			else
				ArithSubType = null;
		}
		if (FCode.equalsIgnoreCase("CalFeeTerm"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFeeTerm = FValue.trim();
			}
			else
				CalFeeTerm = null;
		}
		if (FCode.equalsIgnoreCase("CalFeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFeeType = FValue.trim();
			}
			else
				CalFeeType = null;
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
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIPreceptNo = FValue.trim();
			}
			else
				RIPreceptNo = null;
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReMark = FValue.trim();
			}
			else
				ReMark = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RICalDefSchema other = (RICalDefSchema)otherObject;
		return
			ArithmeticDefID.equals(other.getArithmeticDefID())
			&& ArithmeticDefName.equals(other.getArithmeticDefName())
			&& PreceptType.equals(other.getPreceptType())
			&& ArithType.equals(other.getArithType())
			&& ArithSubType.equals(other.getArithSubType())
			&& CalFeeTerm.equals(other.getCalFeeTerm())
			&& CalFeeType.equals(other.getCalFeeType())
			&& AccumulateDefNO.equals(other.getAccumulateDefNO())
			&& RIPreceptNo.equals(other.getRIPreceptNo())
			&& ReMark.equals(other.getReMark())
			&& State.equals(other.getState())
			&& StandbyString1.equals(other.getStandbyString1())
			&& StandbyString2.equals(other.getStandbyString2())
			&& StandbyString3.equals(other.getStandbyString3());
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
		if( strFieldName.equals("ArithmeticDefID") ) {
			return 0;
		}
		if( strFieldName.equals("ArithmeticDefName") ) {
			return 1;
		}
		if( strFieldName.equals("PreceptType") ) {
			return 2;
		}
		if( strFieldName.equals("ArithType") ) {
			return 3;
		}
		if( strFieldName.equals("ArithSubType") ) {
			return 4;
		}
		if( strFieldName.equals("CalFeeTerm") ) {
			return 5;
		}
		if( strFieldName.equals("CalFeeType") ) {
			return 6;
		}
		if( strFieldName.equals("AccumulateDefNO") ) {
			return 7;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return 8;
		}
		if( strFieldName.equals("ReMark") ) {
			return 9;
		}
		if( strFieldName.equals("State") ) {
			return 10;
		}
		if( strFieldName.equals("StandbyString1") ) {
			return 11;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return 12;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return 13;
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
				strFieldName = "ArithmeticDefID";
				break;
			case 1:
				strFieldName = "ArithmeticDefName";
				break;
			case 2:
				strFieldName = "PreceptType";
				break;
			case 3:
				strFieldName = "ArithType";
				break;
			case 4:
				strFieldName = "ArithSubType";
				break;
			case 5:
				strFieldName = "CalFeeTerm";
				break;
			case 6:
				strFieldName = "CalFeeType";
				break;
			case 7:
				strFieldName = "AccumulateDefNO";
				break;
			case 8:
				strFieldName = "RIPreceptNo";
				break;
			case 9:
				strFieldName = "ReMark";
				break;
			case 10:
				strFieldName = "State";
				break;
			case 11:
				strFieldName = "StandbyString1";
				break;
			case 12:
				strFieldName = "StandbyString2";
				break;
			case 13:
				strFieldName = "StandbyString3";
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
		if( strFieldName.equals("ArithmeticDefID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ArithmeticDefName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PreceptType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ArithType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ArithSubType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFeeTerm") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccumulateDefNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReMark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
