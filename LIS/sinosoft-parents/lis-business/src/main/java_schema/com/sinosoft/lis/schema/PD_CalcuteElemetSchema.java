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
import com.sinosoft.lis.db.PD_CalcuteElemetDB;

/*
 * <p>ClassName: PD_CalcuteElemetSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 中银产品定义平台
 * @CreateDate：2011-11-11
 */
public class PD_CalcuteElemetSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_CalcuteElemetSchema.class);

	// @Field
	/** 险种代码 */
	private String RiskCode;
	/** 收益项目 */
	private String ItemCode;
	/** 计算序号 */
	private int CalOrderNo;
	/** 计算因子 */
	private String CalElement;
	/** 因子属性 */
	private String ElementProperty;
	/** 数据位置调整 */
	private String AdjustPosition;
	/** 变量代码 */
	private String VariableCode;
	/** 计算sql */
	private String CalSql;
	/** 执行sql方式 */
	private String SqlExcuteType;
	/** 结果开始点 */
	private String StartPoint;
	/** 结果开始点单位 */
	private String StartPointFlag;
	/** 结果结束点 */
	private String EndPoint;
	/** 结果结束点单位 */
	private String EndPointFlag;
	/** 初始值[循环因子] */
	private String InitValue;
	/** 循环步长，间隔 */
	private String StepValue;
	/** Sql循环因子 */
	private String SqlCircleGene;
	/** 结果精度 */
	private int ResultPrecision;
	/** 备注 */
	private String Remark;
	/** 修改时间 */
	private String ModifyTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 新增时间 */
	private String MakeTime;
	/** 新增日期 */
	private Date MakeDate;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_CalcuteElemetSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "ItemCode";
		pk[2] = "CalElement";

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
                PD_CalcuteElemetSchema cloned = (PD_CalcuteElemetSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getItemCode()
	{
		return ItemCode;
	}
	public void setItemCode(String aItemCode)
	{
		ItemCode = aItemCode;
	}
	public int getCalOrderNo()
	{
		return CalOrderNo;
	}
	public void setCalOrderNo(int aCalOrderNo)
	{
		CalOrderNo = aCalOrderNo;
	}
	public void setCalOrderNo(String aCalOrderNo)
	{
		if (aCalOrderNo != null && !aCalOrderNo.equals(""))
		{
			Integer tInteger = new Integer(aCalOrderNo);
			int i = tInteger.intValue();
			CalOrderNo = i;
		}
	}

	public String getCalElement()
	{
		return CalElement;
	}
	public void setCalElement(String aCalElement)
	{
		CalElement = aCalElement;
	}
	public String getElementProperty()
	{
		return ElementProperty;
	}
	public void setElementProperty(String aElementProperty)
	{
		ElementProperty = aElementProperty;
	}
	public String getAdjustPosition()
	{
		return AdjustPosition;
	}
	public void setAdjustPosition(String aAdjustPosition)
	{
		AdjustPosition = aAdjustPosition;
	}
	public String getVariableCode()
	{
		return VariableCode;
	}
	public void setVariableCode(String aVariableCode)
	{
		VariableCode = aVariableCode;
	}
	public String getCalSql()
	{
		return CalSql;
	}
	public void setCalSql(String aCalSql)
	{
		CalSql = aCalSql;
	}
	public String getSqlExcuteType()
	{
		return SqlExcuteType;
	}
	public void setSqlExcuteType(String aSqlExcuteType)
	{
		SqlExcuteType = aSqlExcuteType;
	}
	public String getStartPoint()
	{
		return StartPoint;
	}
	public void setStartPoint(String aStartPoint)
	{
		StartPoint = aStartPoint;
	}
	public String getStartPointFlag()
	{
		return StartPointFlag;
	}
	public void setStartPointFlag(String aStartPointFlag)
	{
		StartPointFlag = aStartPointFlag;
	}
	public String getEndPoint()
	{
		return EndPoint;
	}
	public void setEndPoint(String aEndPoint)
	{
		EndPoint = aEndPoint;
	}
	public String getEndPointFlag()
	{
		return EndPointFlag;
	}
	public void setEndPointFlag(String aEndPointFlag)
	{
		EndPointFlag = aEndPointFlag;
	}
	public String getInitValue()
	{
		return InitValue;
	}
	public void setInitValue(String aInitValue)
	{
		InitValue = aInitValue;
	}
	public String getStepValue()
	{
		return StepValue;
	}
	public void setStepValue(String aStepValue)
	{
		StepValue = aStepValue;
	}
	public String getSqlCircleGene()
	{
		return SqlCircleGene;
	}
	public void setSqlCircleGene(String aSqlCircleGene)
	{
		SqlCircleGene = aSqlCircleGene;
	}
	public int getResultPrecision()
	{
		return ResultPrecision;
	}
	public void setResultPrecision(int aResultPrecision)
	{
		ResultPrecision = aResultPrecision;
	}
	public void setResultPrecision(String aResultPrecision)
	{
		if (aResultPrecision != null && !aResultPrecision.equals(""))
		{
			Integer tInteger = new Integer(aResultPrecision);
			int i = tInteger.intValue();
			ResultPrecision = i;
		}
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
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

	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
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


	/**
	* 使用另外一个 PD_CalcuteElemetSchema 对象给 Schema 赋值
	* @param: aPD_CalcuteElemetSchema PD_CalcuteElemetSchema
	**/
	public void setSchema(PD_CalcuteElemetSchema aPD_CalcuteElemetSchema)
	{
		this.RiskCode = aPD_CalcuteElemetSchema.getRiskCode();
		this.ItemCode = aPD_CalcuteElemetSchema.getItemCode();
		this.CalOrderNo = aPD_CalcuteElemetSchema.getCalOrderNo();
		this.CalElement = aPD_CalcuteElemetSchema.getCalElement();
		this.ElementProperty = aPD_CalcuteElemetSchema.getElementProperty();
		this.AdjustPosition = aPD_CalcuteElemetSchema.getAdjustPosition();
		this.VariableCode = aPD_CalcuteElemetSchema.getVariableCode();
		this.CalSql = aPD_CalcuteElemetSchema.getCalSql();
		this.SqlExcuteType = aPD_CalcuteElemetSchema.getSqlExcuteType();
		this.StartPoint = aPD_CalcuteElemetSchema.getStartPoint();
		this.StartPointFlag = aPD_CalcuteElemetSchema.getStartPointFlag();
		this.EndPoint = aPD_CalcuteElemetSchema.getEndPoint();
		this.EndPointFlag = aPD_CalcuteElemetSchema.getEndPointFlag();
		this.InitValue = aPD_CalcuteElemetSchema.getInitValue();
		this.StepValue = aPD_CalcuteElemetSchema.getStepValue();
		this.SqlCircleGene = aPD_CalcuteElemetSchema.getSqlCircleGene();
		this.ResultPrecision = aPD_CalcuteElemetSchema.getResultPrecision();
		this.Remark = aPD_CalcuteElemetSchema.getRemark();
		this.ModifyTime = aPD_CalcuteElemetSchema.getModifyTime();
		this.ModifyDate = fDate.getDate( aPD_CalcuteElemetSchema.getModifyDate());
		this.MakeTime = aPD_CalcuteElemetSchema.getMakeTime();
		this.MakeDate = fDate.getDate( aPD_CalcuteElemetSchema.getMakeDate());
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("ItemCode") == null )
				this.ItemCode = null;
			else
				this.ItemCode = rs.getString("ItemCode").trim();

			this.CalOrderNo = rs.getInt("CalOrderNo");
			if( rs.getString("CalElement") == null )
				this.CalElement = null;
			else
				this.CalElement = rs.getString("CalElement").trim();

			if( rs.getString("ElementProperty") == null )
				this.ElementProperty = null;
			else
				this.ElementProperty = rs.getString("ElementProperty").trim();

			if( rs.getString("AdjustPosition") == null )
				this.AdjustPosition = null;
			else
				this.AdjustPosition = rs.getString("AdjustPosition").trim();

			if( rs.getString("VariableCode") == null )
				this.VariableCode = null;
			else
				this.VariableCode = rs.getString("VariableCode").trim();

			if( rs.getString("CalSql") == null )
				this.CalSql = null;
			else
				this.CalSql = rs.getString("CalSql").trim();

			if( rs.getString("SqlExcuteType") == null )
				this.SqlExcuteType = null;
			else
				this.SqlExcuteType = rs.getString("SqlExcuteType").trim();

			if( rs.getString("StartPoint") == null )
				this.StartPoint = null;
			else
				this.StartPoint = rs.getString("StartPoint").trim();

			if( rs.getString("StartPointFlag") == null )
				this.StartPointFlag = null;
			else
				this.StartPointFlag = rs.getString("StartPointFlag").trim();

			if( rs.getString("EndPoint") == null )
				this.EndPoint = null;
			else
				this.EndPoint = rs.getString("EndPoint").trim();

			if( rs.getString("EndPointFlag") == null )
				this.EndPointFlag = null;
			else
				this.EndPointFlag = rs.getString("EndPointFlag").trim();

			if( rs.getString("InitValue") == null )
				this.InitValue = null;
			else
				this.InitValue = rs.getString("InitValue").trim();

			if( rs.getString("StepValue") == null )
				this.StepValue = null;
			else
				this.StepValue = rs.getString("StepValue").trim();

			if( rs.getString("SqlCircleGene") == null )
				this.SqlCircleGene = null;
			else
				this.SqlCircleGene = rs.getString("SqlCircleGene").trim();

			this.ResultPrecision = rs.getInt("ResultPrecision");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.MakeDate = rs.getDate("MakeDate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_CalcuteElemet表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_CalcuteElemetSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_CalcuteElemetSchema getSchema()
	{
		PD_CalcuteElemetSchema aPD_CalcuteElemetSchema = new PD_CalcuteElemetSchema();
		aPD_CalcuteElemetSchema.setSchema(this);
		return aPD_CalcuteElemetSchema;
	}

	public PD_CalcuteElemetDB getDB()
	{
		PD_CalcuteElemetDB aDBOper = new PD_CalcuteElemetDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_CalcuteElemet描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ItemCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(CalOrderNo));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CalElement)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ElementProperty)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AdjustPosition)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(VariableCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CalSql)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(SqlExcuteType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StartPoint)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StartPointFlag)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(EndPoint)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(EndPointFlag)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(InitValue)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StepValue)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(SqlCircleGene)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(ResultPrecision));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_CalcuteElemet>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ItemCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CalOrderNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			CalElement = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ElementProperty = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AdjustPosition = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			VariableCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CalSql = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SqlExcuteType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			StartPoint = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			StartPointFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			EndPoint = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			EndPointFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InitValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			StepValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			SqlCircleGene = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ResultPrecision= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).intValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_CalcuteElemetSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("ItemCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemCode));
		}
		if (FCode.equalsIgnoreCase("CalOrderNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalOrderNo));
		}
		if (FCode.equalsIgnoreCase("CalElement"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalElement));
		}
		if (FCode.equalsIgnoreCase("ElementProperty"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ElementProperty));
		}
		if (FCode.equalsIgnoreCase("AdjustPosition"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjustPosition));
		}
		if (FCode.equalsIgnoreCase("VariableCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VariableCode));
		}
		if (FCode.equalsIgnoreCase("CalSql"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSql));
		}
		if (FCode.equalsIgnoreCase("SqlExcuteType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SqlExcuteType));
		}
		if (FCode.equalsIgnoreCase("StartPoint"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartPoint));
		}
		if (FCode.equalsIgnoreCase("StartPointFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartPointFlag));
		}
		if (FCode.equalsIgnoreCase("EndPoint"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndPoint));
		}
		if (FCode.equalsIgnoreCase("EndPointFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndPointFlag));
		}
		if (FCode.equalsIgnoreCase("InitValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitValue));
		}
		if (FCode.equalsIgnoreCase("StepValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StepValue));
		}
		if (FCode.equalsIgnoreCase("SqlCircleGene"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SqlCircleGene));
		}
		if (FCode.equalsIgnoreCase("ResultPrecision"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ResultPrecision));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ItemCode);
				break;
			case 2:
				strFieldValue = String.valueOf(CalOrderNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CalElement);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ElementProperty);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AdjustPosition);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(VariableCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CalSql);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(SqlExcuteType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(StartPoint);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(StartPointFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(EndPoint);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(EndPointFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InitValue);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(StepValue);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(SqlCircleGene);
				break;
			case 16:
				strFieldValue = String.valueOf(ResultPrecision);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("ItemCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemCode = FValue.trim();
			}
			else
				ItemCode = null;
		}
		if (FCode.equalsIgnoreCase("CalOrderNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CalOrderNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("CalElement"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalElement = FValue.trim();
			}
			else
				CalElement = null;
		}
		if (FCode.equalsIgnoreCase("ElementProperty"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ElementProperty = FValue.trim();
			}
			else
				ElementProperty = null;
		}
		if (FCode.equalsIgnoreCase("AdjustPosition"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AdjustPosition = FValue.trim();
			}
			else
				AdjustPosition = null;
		}
		if (FCode.equalsIgnoreCase("VariableCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VariableCode = FValue.trim();
			}
			else
				VariableCode = null;
		}
		if (FCode.equalsIgnoreCase("CalSql"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSql = FValue.trim();
			}
			else
				CalSql = null;
		}
		if (FCode.equalsIgnoreCase("SqlExcuteType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SqlExcuteType = FValue.trim();
			}
			else
				SqlExcuteType = null;
		}
		if (FCode.equalsIgnoreCase("StartPoint"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartPoint = FValue.trim();
			}
			else
				StartPoint = null;
		}
		if (FCode.equalsIgnoreCase("StartPointFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartPointFlag = FValue.trim();
			}
			else
				StartPointFlag = null;
		}
		if (FCode.equalsIgnoreCase("EndPoint"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndPoint = FValue.trim();
			}
			else
				EndPoint = null;
		}
		if (FCode.equalsIgnoreCase("EndPointFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndPointFlag = FValue.trim();
			}
			else
				EndPointFlag = null;
		}
		if (FCode.equalsIgnoreCase("InitValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InitValue = FValue.trim();
			}
			else
				InitValue = null;
		}
		if (FCode.equalsIgnoreCase("StepValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StepValue = FValue.trim();
			}
			else
				StepValue = null;
		}
		if (FCode.equalsIgnoreCase("SqlCircleGene"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SqlCircleGene = FValue.trim();
			}
			else
				SqlCircleGene = null;
		}
		if (FCode.equalsIgnoreCase("ResultPrecision"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ResultPrecision = i;
			}
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
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
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
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_CalcuteElemetSchema other = (PD_CalcuteElemetSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& ItemCode.equals(other.getItemCode())
			&& CalOrderNo == other.getCalOrderNo()
			&& CalElement.equals(other.getCalElement())
			&& ElementProperty.equals(other.getElementProperty())
			&& AdjustPosition.equals(other.getAdjustPosition())
			&& VariableCode.equals(other.getVariableCode())
			&& CalSql.equals(other.getCalSql())
			&& SqlExcuteType.equals(other.getSqlExcuteType())
			&& StartPoint.equals(other.getStartPoint())
			&& StartPointFlag.equals(other.getStartPointFlag())
			&& EndPoint.equals(other.getEndPoint())
			&& EndPointFlag.equals(other.getEndPointFlag())
			&& InitValue.equals(other.getInitValue())
			&& StepValue.equals(other.getStepValue())
			&& SqlCircleGene.equals(other.getSqlCircleGene())
			&& ResultPrecision == other.getResultPrecision()
			&& Remark.equals(other.getRemark())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(MakeDate).equals(other.getMakeDate());
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("ItemCode") ) {
			return 1;
		}
		if( strFieldName.equals("CalOrderNo") ) {
			return 2;
		}
		if( strFieldName.equals("CalElement") ) {
			return 3;
		}
		if( strFieldName.equals("ElementProperty") ) {
			return 4;
		}
		if( strFieldName.equals("AdjustPosition") ) {
			return 5;
		}
		if( strFieldName.equals("VariableCode") ) {
			return 6;
		}
		if( strFieldName.equals("CalSql") ) {
			return 7;
		}
		if( strFieldName.equals("SqlExcuteType") ) {
			return 8;
		}
		if( strFieldName.equals("StartPoint") ) {
			return 9;
		}
		if( strFieldName.equals("StartPointFlag") ) {
			return 10;
		}
		if( strFieldName.equals("EndPoint") ) {
			return 11;
		}
		if( strFieldName.equals("EndPointFlag") ) {
			return 12;
		}
		if( strFieldName.equals("InitValue") ) {
			return 13;
		}
		if( strFieldName.equals("StepValue") ) {
			return 14;
		}
		if( strFieldName.equals("SqlCircleGene") ) {
			return 15;
		}
		if( strFieldName.equals("ResultPrecision") ) {
			return 16;
		}
		if( strFieldName.equals("Remark") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 19;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		if( strFieldName.equals("MakeDate") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "ItemCode";
				break;
			case 2:
				strFieldName = "CalOrderNo";
				break;
			case 3:
				strFieldName = "CalElement";
				break;
			case 4:
				strFieldName = "ElementProperty";
				break;
			case 5:
				strFieldName = "AdjustPosition";
				break;
			case 6:
				strFieldName = "VariableCode";
				break;
			case 7:
				strFieldName = "CalSql";
				break;
			case 8:
				strFieldName = "SqlExcuteType";
				break;
			case 9:
				strFieldName = "StartPoint";
				break;
			case 10:
				strFieldName = "StartPointFlag";
				break;
			case 11:
				strFieldName = "EndPoint";
				break;
			case 12:
				strFieldName = "EndPointFlag";
				break;
			case 13:
				strFieldName = "InitValue";
				break;
			case 14:
				strFieldName = "StepValue";
				break;
			case 15:
				strFieldName = "SqlCircleGene";
				break;
			case 16:
				strFieldName = "ResultPrecision";
				break;
			case 17:
				strFieldName = "Remark";
				break;
			case 18:
				strFieldName = "ModifyTime";
				break;
			case 19:
				strFieldName = "ModifyDate";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "MakeDate";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalOrderNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CalElement") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ElementProperty") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AdjustPosition") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VariableCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSql") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SqlExcuteType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartPoint") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartPointFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndPoint") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndPointFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InitValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StepValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SqlCircleGene") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ResultPrecision") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
