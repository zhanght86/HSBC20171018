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
import com.sinosoft.lis.db.LLGrpClaimPopedomDB;

/*
 * <p>ClassName: LLGrpClaimPopedomSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLGrpClaimPopedomSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLGrpClaimPopedomSchema.class);

	// @Field
	/** 权限级别 */
	private String JobCategory;
	/** 案件类型 */
	private String CaseCategory;
	/** 权限名称 */
	private String PopedomName;
	/** 理赔类型 */
	private String ClaimType;
	/** 审核审批分类 */
	private String ClaimCheckUwFlag;
	/** 最小金额 */
	private double BaseMin;
	/** 最大金额 */
	private double BaseMax;
	/** 启用日期 */
	private Date StartDate;
	/** 结束日期 */
	private Date EndDate;
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
	/** 通融、协议赔付最小金额 */
	private double appendmin;
	/** 通融、协议赔付最大金额 */
	private double appendmax;
	/** 双审标识 */
	private String DSFlag;

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLGrpClaimPopedomSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "JobCategory";
		pk[1] = "CaseCategory";

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
		LLGrpClaimPopedomSchema cloned = (LLGrpClaimPopedomSchema)super.clone();
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
	* TA：首席核赔师<p>
	* TB：总公司团险签批人<p>
	* TC：分公司团险签批人<p>
	* TD：分公司团险审核人<p>
	* TE：分公司团险辅助岗<p>
	* TF：三级机构团险审核人<p>
	* TG：三级机构团险辅助岗<p>
	* TH：总公司团险理赔内勤<p>
	* TI：分公司团险理赔内勤
	*/
	public String getJobCategory()
	{
		return JobCategory;
	}
	public void setJobCategory(String aJobCategory)
	{
		JobCategory = aJobCategory;
	}
	/**
	* 0 ---  一般给付件	<p>
	* 1 ---- 短期给付件	<p>
	* 2 ---  批次案件 	<p>
	* 3 ---  通融/协议给付案件 	<p>
	* 4 ---  责任免除件
	*/
	public String getCaseCategory()
	{
		return CaseCategory;
	}
	public void setCaseCategory(String aCaseCategory)
	{
		CaseCategory = aCaseCategory;
	}
	public String getPopedomName()
	{
		return PopedomName;
	}
	public void setPopedomName(String aPopedomName)
	{
		PopedomName = aPopedomName;
	}
	/**
	* 00   医疗<p>
	* 01  伤残<p>
	* 02  死亡<p>
	* 03  高残<p>
	* 04  重大疾病<p>
	* 09  豁免<p>
	* 07  特种疾病<p>
	* 08  失业失能
	*/
	public String getClaimType()
	{
		return ClaimType;
	}
	public void setClaimType(String aClaimType)
	{
		ClaimType = aClaimType;
	}
	/**
	* A审核<p>
	* B审批
	*/
	public String getClaimCheckUwFlag()
	{
		return ClaimCheckUwFlag;
	}
	public void setClaimCheckUwFlag(String aClaimCheckUwFlag)
	{
		ClaimCheckUwFlag = aClaimCheckUwFlag;
	}
	public double getBaseMin()
	{
		return BaseMin;
	}
	public void setBaseMin(double aBaseMin)
	{
		BaseMin = aBaseMin;
	}
	public void setBaseMin(String aBaseMin)
	{
		if (aBaseMin != null && !aBaseMin.equals(""))
		{
			Double tDouble = new Double(aBaseMin);
			double d = tDouble.doubleValue();
			BaseMin = d;
		}
	}

	public double getBaseMax()
	{
		return BaseMax;
	}
	public void setBaseMax(double aBaseMax)
	{
		BaseMax = aBaseMax;
	}
	public void setBaseMax(String aBaseMax)
	{
		if (aBaseMax != null && !aBaseMax.equals(""))
		{
			Double tDouble = new Double(aBaseMax);
			double d = tDouble.doubleValue();
			BaseMax = d;
		}
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

	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		MngCom = aMngCom;
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
	public double getappendmin()
	{
		return appendmin;
	}
	public void setappendmin(double aappendmin)
	{
		appendmin = aappendmin;
	}
	public void setappendmin(String aappendmin)
	{
		if (aappendmin != null && !aappendmin.equals(""))
		{
			Double tDouble = new Double(aappendmin);
			double d = tDouble.doubleValue();
			appendmin = d;
		}
	}

	public double getappendmax()
	{
		return appendmax;
	}
	public void setappendmax(double aappendmax)
	{
		appendmax = aappendmax;
	}
	public void setappendmax(String aappendmax)
	{
		if (aappendmax != null && !aappendmax.equals(""))
		{
			Double tDouble = new Double(aappendmax);
			double d = tDouble.doubleValue();
			appendmax = d;
		}
	}

	public String getDSFlag()
	{
		return DSFlag;
	}
	public void setDSFlag(String aDSFlag)
	{
		DSFlag = aDSFlag;
	}

	/**
	* 使用另外一个 LLGrpClaimPopedomSchema 对象给 Schema 赋值
	* @param: aLLGrpClaimPopedomSchema LLGrpClaimPopedomSchema
	**/
	public void setSchema(LLGrpClaimPopedomSchema aLLGrpClaimPopedomSchema)
	{
		this.JobCategory = aLLGrpClaimPopedomSchema.getJobCategory();
		this.CaseCategory = aLLGrpClaimPopedomSchema.getCaseCategory();
		this.PopedomName = aLLGrpClaimPopedomSchema.getPopedomName();
		this.ClaimType = aLLGrpClaimPopedomSchema.getClaimType();
		this.ClaimCheckUwFlag = aLLGrpClaimPopedomSchema.getClaimCheckUwFlag();
		this.BaseMin = aLLGrpClaimPopedomSchema.getBaseMin();
		this.BaseMax = aLLGrpClaimPopedomSchema.getBaseMax();
		this.StartDate = fDate.getDate( aLLGrpClaimPopedomSchema.getStartDate());
		this.EndDate = fDate.getDate( aLLGrpClaimPopedomSchema.getEndDate());
		this.MngCom = aLLGrpClaimPopedomSchema.getMngCom();
		this.Operator = aLLGrpClaimPopedomSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLGrpClaimPopedomSchema.getMakeDate());
		this.MakeTime = aLLGrpClaimPopedomSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLGrpClaimPopedomSchema.getModifyDate());
		this.ModifyTime = aLLGrpClaimPopedomSchema.getModifyTime();
		this.appendmin = aLLGrpClaimPopedomSchema.getappendmin();
		this.appendmax = aLLGrpClaimPopedomSchema.getappendmax();
		this.DSFlag = aLLGrpClaimPopedomSchema.getDSFlag();
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
			if( rs.getString("JobCategory") == null )
				this.JobCategory = null;
			else
				this.JobCategory = rs.getString("JobCategory").trim();

			if( rs.getString("CaseCategory") == null )
				this.CaseCategory = null;
			else
				this.CaseCategory = rs.getString("CaseCategory").trim();

			if( rs.getString("PopedomName") == null )
				this.PopedomName = null;
			else
				this.PopedomName = rs.getString("PopedomName").trim();

			if( rs.getString("ClaimType") == null )
				this.ClaimType = null;
			else
				this.ClaimType = rs.getString("ClaimType").trim();

			if( rs.getString("ClaimCheckUwFlag") == null )
				this.ClaimCheckUwFlag = null;
			else
				this.ClaimCheckUwFlag = rs.getString("ClaimCheckUwFlag").trim();

			this.BaseMin = rs.getDouble("BaseMin");
			this.BaseMax = rs.getDouble("BaseMax");
			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
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

			this.appendmin = rs.getDouble("appendmin");
			this.appendmax = rs.getDouble("appendmax");
			if( rs.getString("DSFlag") == null )
				this.DSFlag = null;
			else
				this.DSFlag = rs.getString("DSFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLGrpClaimPopedom表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpClaimPopedomSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLGrpClaimPopedomSchema getSchema()
	{
		LLGrpClaimPopedomSchema aLLGrpClaimPopedomSchema = new LLGrpClaimPopedomSchema();
		aLLGrpClaimPopedomSchema.setSchema(this);
		return aLLGrpClaimPopedomSchema;
	}

	public LLGrpClaimPopedomDB getDB()
	{
		LLGrpClaimPopedomDB aDBOper = new LLGrpClaimPopedomDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLGrpClaimPopedom描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(JobCategory)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseCategory)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PopedomName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimCheckUwFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BaseMin));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BaseMax));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(appendmin));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(appendmax));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DSFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLGrpClaimPopedom>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			JobCategory = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseCategory = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PopedomName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ClaimType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ClaimCheckUwFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BaseMin = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			BaseMax = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			appendmin = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			appendmax = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			DSFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpClaimPopedomSchema";
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
		if (FCode.equalsIgnoreCase("JobCategory"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(JobCategory));
		}
		if (FCode.equalsIgnoreCase("CaseCategory"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseCategory));
		}
		if (FCode.equalsIgnoreCase("PopedomName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PopedomName));
		}
		if (FCode.equalsIgnoreCase("ClaimType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimType));
		}
		if (FCode.equalsIgnoreCase("ClaimCheckUwFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimCheckUwFlag));
		}
		if (FCode.equalsIgnoreCase("BaseMin"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BaseMin));
		}
		if (FCode.equalsIgnoreCase("BaseMax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BaseMax));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
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
		if (FCode.equalsIgnoreCase("appendmin"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(appendmin));
		}
		if (FCode.equalsIgnoreCase("appendmax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(appendmax));
		}
		if (FCode.equalsIgnoreCase("DSFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DSFlag));
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
				strFieldValue = StrTool.GBKToUnicode(JobCategory);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CaseCategory);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PopedomName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ClaimType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ClaimCheckUwFlag);
				break;
			case 5:
				strFieldValue = String.valueOf(BaseMin);
				break;
			case 6:
				strFieldValue = String.valueOf(BaseMax);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 15:
				strFieldValue = String.valueOf(appendmin);
				break;
			case 16:
				strFieldValue = String.valueOf(appendmax);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(DSFlag);
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

		if (FCode.equalsIgnoreCase("JobCategory"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				JobCategory = FValue.trim();
			}
			else
				JobCategory = null;
		}
		if (FCode.equalsIgnoreCase("CaseCategory"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseCategory = FValue.trim();
			}
			else
				CaseCategory = null;
		}
		if (FCode.equalsIgnoreCase("PopedomName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PopedomName = FValue.trim();
			}
			else
				PopedomName = null;
		}
		if (FCode.equalsIgnoreCase("ClaimType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimType = FValue.trim();
			}
			else
				ClaimType = null;
		}
		if (FCode.equalsIgnoreCase("ClaimCheckUwFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimCheckUwFlag = FValue.trim();
			}
			else
				ClaimCheckUwFlag = null;
		}
		if (FCode.equalsIgnoreCase("BaseMin"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BaseMin = d;
			}
		}
		if (FCode.equalsIgnoreCase("BaseMax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BaseMax = d;
			}
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
		if (FCode.equalsIgnoreCase("appendmin"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				appendmin = d;
			}
		}
		if (FCode.equalsIgnoreCase("appendmax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				appendmax = d;
			}
		}
		if (FCode.equalsIgnoreCase("DSFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DSFlag = FValue.trim();
			}
			else
				DSFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLGrpClaimPopedomSchema other = (LLGrpClaimPopedomSchema)otherObject;
		return
			JobCategory.equals(other.getJobCategory())
			&& CaseCategory.equals(other.getCaseCategory())
			&& PopedomName.equals(other.getPopedomName())
			&& ClaimType.equals(other.getClaimType())
			&& ClaimCheckUwFlag.equals(other.getClaimCheckUwFlag())
			&& BaseMin == other.getBaseMin()
			&& BaseMax == other.getBaseMax()
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& appendmin == other.getappendmin()
			&& appendmax == other.getappendmax()
			&& DSFlag.equals(other.getDSFlag());
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
		if( strFieldName.equals("JobCategory") ) {
			return 0;
		}
		if( strFieldName.equals("CaseCategory") ) {
			return 1;
		}
		if( strFieldName.equals("PopedomName") ) {
			return 2;
		}
		if( strFieldName.equals("ClaimType") ) {
			return 3;
		}
		if( strFieldName.equals("ClaimCheckUwFlag") ) {
			return 4;
		}
		if( strFieldName.equals("BaseMin") ) {
			return 5;
		}
		if( strFieldName.equals("BaseMax") ) {
			return 6;
		}
		if( strFieldName.equals("StartDate") ) {
			return 7;
		}
		if( strFieldName.equals("EndDate") ) {
			return 8;
		}
		if( strFieldName.equals("MngCom") ) {
			return 9;
		}
		if( strFieldName.equals("Operator") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 14;
		}
		if( strFieldName.equals("appendmin") ) {
			return 15;
		}
		if( strFieldName.equals("appendmax") ) {
			return 16;
		}
		if( strFieldName.equals("DSFlag") ) {
			return 17;
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
				strFieldName = "JobCategory";
				break;
			case 1:
				strFieldName = "CaseCategory";
				break;
			case 2:
				strFieldName = "PopedomName";
				break;
			case 3:
				strFieldName = "ClaimType";
				break;
			case 4:
				strFieldName = "ClaimCheckUwFlag";
				break;
			case 5:
				strFieldName = "BaseMin";
				break;
			case 6:
				strFieldName = "BaseMax";
				break;
			case 7:
				strFieldName = "StartDate";
				break;
			case 8:
				strFieldName = "EndDate";
				break;
			case 9:
				strFieldName = "MngCom";
				break;
			case 10:
				strFieldName = "Operator";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "ModifyDate";
				break;
			case 14:
				strFieldName = "ModifyTime";
				break;
			case 15:
				strFieldName = "appendmin";
				break;
			case 16:
				strFieldName = "appendmax";
				break;
			case 17:
				strFieldName = "DSFlag";
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
		if( strFieldName.equals("JobCategory") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseCategory") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PopedomName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimCheckUwFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BaseMin") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BaseMax") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("appendmin") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("appendmax") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DSFlag") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
