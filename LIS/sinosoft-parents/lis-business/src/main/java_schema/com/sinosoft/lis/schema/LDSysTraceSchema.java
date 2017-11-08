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
import com.sinosoft.lis.db.LDSysTraceDB;

/*
 * <p>ClassName: LDSysTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LDSysTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDSysTraceSchema.class);

	// @Field
	/** 保单号码 */
	private String PolNo;
	/** 产生操作员 */
	private String Operator;
	/** 产生入机日期 */
	private Date MakeDate;
	/** 产生入机时间 */
	private String MakeTime;
	/** 产生管理机构 */
	private String ManageCom;
	/** 产生操作位置 */
	private String CreatePos;
	/** 操作后的保单状态 */
	private int PolState;
	/** 修改操作员 */
	private String Operator2;
	/** 修改管理机构 */
	private String ManageCom2;
	/** 修改操作位置 */
	private String CreatePos2;
	/** 作用开始日期 */
	private Date StartDate;
	/** 作用结束日期 */
	private Date EndDate;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 轨迹有效标志 */
	private String ValiFlag;
	/** 不能操作时的提示信息 */
	private String Remark;

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDSysTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "PolNo";
		pk[1] = "Operator";
		pk[2] = "MakeDate";
		pk[3] = "MakeTime";
		pk[4] = "PolState";

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
		LDSysTraceSchema cloned = (LDSysTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
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
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/**
	* 个人投保录入<p>
	* 立案录入
	*/
	public String getCreatePos()
	{
		return CreatePos;
	}
	public void setCreatePos(String aCreatePos)
	{
		CreatePos = aCreatePos;
	}
	/**
	* 1000 -- 1999 承保<p>
	* 2000 －－ 2999 收付费<p>
	* 3000 －－ 3999 保全<p>
	* 4000 －－ 4999 案件<p>
	* 9000 －－ 9999 其他<p>
	* <p>
	* <p>
	* <p>
	* <p>
	* <p>
	* <p>
	* 1 －－ 借款状态<p>
	* 2 －－ 被保人出险<p>
	* 3 －－ 已经生存领取<p>
	* 4 －－ 已经案件领取<p>
	* <p>
	* <p>
	* 保单的失效状态通过编写函数来完成。
	*/
	public int getPolState()
	{
		return PolState;
	}
	public void setPolState(int aPolState)
	{
		PolState = aPolState;
	}
	public void setPolState(String aPolState)
	{
		if (aPolState != null && !aPolState.equals(""))
		{
			Integer tInteger = new Integer(aPolState);
			int i = tInteger.intValue();
			PolState = i;
		}
	}

	public String getOperator2()
	{
		return Operator2;
	}
	public void setOperator2(String aOperator2)
	{
		Operator2 = aOperator2;
	}
	public String getManageCom2()
	{
		return ManageCom2;
	}
	public void setManageCom2(String aManageCom2)
	{
		ManageCom2 = aManageCom2;
	}
	public String getCreatePos2()
	{
		return CreatePos2;
	}
	public void setCreatePos2(String aCreatePos2)
	{
		CreatePos2 = aCreatePos2;
	}
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
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
	* 0 －－ 无效状态<p>
	* 1 －－ 有效状态
	*/
	public String getValiFlag()
	{
		return ValiFlag;
	}
	public void setValiFlag(String aValiFlag)
	{
		ValiFlag = aValiFlag;
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
	* 使用另外一个 LDSysTraceSchema 对象给 Schema 赋值
	* @param: aLDSysTraceSchema LDSysTraceSchema
	**/
	public void setSchema(LDSysTraceSchema aLDSysTraceSchema)
	{
		this.PolNo = aLDSysTraceSchema.getPolNo();
		this.Operator = aLDSysTraceSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDSysTraceSchema.getMakeDate());
		this.MakeTime = aLDSysTraceSchema.getMakeTime();
		this.ManageCom = aLDSysTraceSchema.getManageCom();
		this.CreatePos = aLDSysTraceSchema.getCreatePos();
		this.PolState = aLDSysTraceSchema.getPolState();
		this.Operator2 = aLDSysTraceSchema.getOperator2();
		this.ManageCom2 = aLDSysTraceSchema.getManageCom2();
		this.CreatePos2 = aLDSysTraceSchema.getCreatePos2();
		this.StartDate = fDate.getDate( aLDSysTraceSchema.getStartDate());
		this.EndDate = fDate.getDate( aLDSysTraceSchema.getEndDate());
		this.ModifyDate = fDate.getDate( aLDSysTraceSchema.getModifyDate());
		this.ModifyTime = aLDSysTraceSchema.getModifyTime();
		this.ValiFlag = aLDSysTraceSchema.getValiFlag();
		this.Remark = aLDSysTraceSchema.getRemark();
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
			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("CreatePos") == null )
				this.CreatePos = null;
			else
				this.CreatePos = rs.getString("CreatePos").trim();

			this.PolState = rs.getInt("PolState");
			if( rs.getString("Operator2") == null )
				this.Operator2 = null;
			else
				this.Operator2 = rs.getString("Operator2").trim();

			if( rs.getString("ManageCom2") == null )
				this.ManageCom2 = null;
			else
				this.ManageCom2 = rs.getString("ManageCom2").trim();

			if( rs.getString("CreatePos2") == null )
				this.CreatePos2 = null;
			else
				this.CreatePos2 = rs.getString("CreatePos2").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("ValiFlag") == null )
				this.ValiFlag = null;
			else
				this.ValiFlag = rs.getString("ValiFlag").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDSysTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDSysTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDSysTraceSchema getSchema()
	{
		LDSysTraceSchema aLDSysTraceSchema = new LDSysTraceSchema();
		aLDSysTraceSchema.setSchema(this);
		return aLDSysTraceSchema;
	}

	public LDSysTraceDB getDB()
	{
		LDSysTraceDB aDBOper = new LDSysTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDSysTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CreatePos)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PolState));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CreatePos2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValiFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDSysTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CreatePos = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PolState= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			Operator2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ManageCom2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CreatePos2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ValiFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDSysTraceSchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("CreatePos"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CreatePos));
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolState));
		}
		if (FCode.equalsIgnoreCase("Operator2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator2));
		}
		if (FCode.equalsIgnoreCase("ManageCom2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom2));
		}
		if (FCode.equalsIgnoreCase("CreatePos2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CreatePos2));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("ValiFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValiFlag));
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
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CreatePos);
				break;
			case 6:
				strFieldValue = String.valueOf(PolState);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Operator2);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ManageCom2);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CreatePos2);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ValiFlag);
				break;
			case 15:
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

		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("CreatePos"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CreatePos = FValue.trim();
			}
			else
				CreatePos = null;
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PolState = i;
			}
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
		if (FCode.equalsIgnoreCase("ManageCom2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom2 = FValue.trim();
			}
			else
				ManageCom2 = null;
		}
		if (FCode.equalsIgnoreCase("CreatePos2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CreatePos2 = FValue.trim();
			}
			else
				CreatePos2 = null;
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
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
		if (FCode.equalsIgnoreCase("ValiFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValiFlag = FValue.trim();
			}
			else
				ValiFlag = null;
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
		LDSysTraceSchema other = (LDSysTraceSchema)otherObject;
		return
			PolNo.equals(other.getPolNo())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ManageCom.equals(other.getManageCom())
			&& CreatePos.equals(other.getCreatePos())
			&& PolState == other.getPolState()
			&& Operator2.equals(other.getOperator2())
			&& ManageCom2.equals(other.getManageCom2())
			&& CreatePos2.equals(other.getCreatePos2())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ValiFlag.equals(other.getValiFlag())
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
		if( strFieldName.equals("PolNo") ) {
			return 0;
		}
		if( strFieldName.equals("Operator") ) {
			return 1;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 2;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 3;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 4;
		}
		if( strFieldName.equals("CreatePos") ) {
			return 5;
		}
		if( strFieldName.equals("PolState") ) {
			return 6;
		}
		if( strFieldName.equals("Operator2") ) {
			return 7;
		}
		if( strFieldName.equals("ManageCom2") ) {
			return 8;
		}
		if( strFieldName.equals("CreatePos2") ) {
			return 9;
		}
		if( strFieldName.equals("StartDate") ) {
			return 10;
		}
		if( strFieldName.equals("EndDate") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 13;
		}
		if( strFieldName.equals("ValiFlag") ) {
			return 14;
		}
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "PolNo";
				break;
			case 1:
				strFieldName = "Operator";
				break;
			case 2:
				strFieldName = "MakeDate";
				break;
			case 3:
				strFieldName = "MakeTime";
				break;
			case 4:
				strFieldName = "ManageCom";
				break;
			case 5:
				strFieldName = "CreatePos";
				break;
			case 6:
				strFieldName = "PolState";
				break;
			case 7:
				strFieldName = "Operator2";
				break;
			case 8:
				strFieldName = "ManageCom2";
				break;
			case 9:
				strFieldName = "CreatePos2";
				break;
			case 10:
				strFieldName = "StartDate";
				break;
			case 11:
				strFieldName = "EndDate";
				break;
			case 12:
				strFieldName = "ModifyDate";
				break;
			case 13:
				strFieldName = "ModifyTime";
				break;
			case 14:
				strFieldName = "ValiFlag";
				break;
			case 15:
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
		if( strFieldName.equals("PolNo") ) {
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
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CreatePos") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolState") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Operator2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CreatePos2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValiFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
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
