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
import com.sinosoft.lis.db.LCScoreRReportDB;

/*
 * <p>ClassName: LCScoreRReportSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保流程
 */
public class LCScoreRReportSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCScoreRReportSchema.class);

	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 总单投保单号码 */
	private String ProposalContNo;
	/** 生调员工号 */
	private String CustomerNo;
	/** 生调员姓名 */
	private String Name;
	/** 管理机构 */
	private String ManageCom;
	/** 合计扣分 */
	private int SScore;
	/** 合计加分 */
	private int AScore;
	/** 得分 */
	private int Score;
	/** 评估结论 */
	private String Conclusion;
	/** 评估人 */
	private String AssessOperator;
	/** 评估日期 */
	private Date AssessDay;
	/** 录入日期 */
	private Date MakeDate;
	/** 录入时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 录入人 */
	private String Operator;
	/** 评估时间 */
	private String AssessTime;
	/** 打印流水号 */
	private String PrtSeq;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCScoreRReportSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ContNo";
		pk[1] = "CustomerNo";

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
		LCScoreRReportSchema cloned = (LCScoreRReportSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getProposalContNo()
	{
		return ProposalContNo;
	}
	public void setProposalContNo(String aProposalContNo)
	{
		ProposalContNo = aProposalContNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		Name = aName;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public int getSScore()
	{
		return SScore;
	}
	public void setSScore(int aSScore)
	{
		SScore = aSScore;
	}
	public void setSScore(String aSScore)
	{
		if (aSScore != null && !aSScore.equals(""))
		{
			Integer tInteger = new Integer(aSScore);
			int i = tInteger.intValue();
			SScore = i;
		}
	}

	public int getAScore()
	{
		return AScore;
	}
	public void setAScore(int aAScore)
	{
		AScore = aAScore;
	}
	public void setAScore(String aAScore)
	{
		if (aAScore != null && !aAScore.equals(""))
		{
			Integer tInteger = new Integer(aAScore);
			int i = tInteger.intValue();
			AScore = i;
		}
	}

	/**
	* 得分=100-合计扣分+合计加分
	*/
	public int getScore()
	{
		return Score;
	}
	public void setScore(int aScore)
	{
		Score = aScore;
	}
	public void setScore(String aScore)
	{
		if (aScore != null && !aScore.equals(""))
		{
			Integer tInteger = new Integer(aScore);
			int i = tInteger.intValue();
			Score = i;
		}
	}

	public String getConclusion()
	{
		return Conclusion;
	}
	public void setConclusion(String aConclusion)
	{
		Conclusion = aConclusion;
	}
	public String getAssessOperator()
	{
		return AssessOperator;
	}
	public void setAssessOperator(String aAssessOperator)
	{
		AssessOperator = aAssessOperator;
	}
	public String getAssessDay()
	{
		if( AssessDay != null )
			return fDate.getString(AssessDay);
		else
			return null;
	}
	public void setAssessDay(Date aAssessDay)
	{
		AssessDay = aAssessDay;
	}
	public void setAssessDay(String aAssessDay)
	{
		if (aAssessDay != null && !aAssessDay.equals("") )
		{
			AssessDay = fDate.getDate( aAssessDay );
		}
		else
			AssessDay = null;
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
	public String getAssessTime()
	{
		return AssessTime;
	}
	public void setAssessTime(String aAssessTime)
	{
		AssessTime = aAssessTime;
	}
	public String getPrtSeq()
	{
		return PrtSeq;
	}
	public void setPrtSeq(String aPrtSeq)
	{
		PrtSeq = aPrtSeq;
	}

	/**
	* 使用另外一个 LCScoreRReportSchema 对象给 Schema 赋值
	* @param: aLCScoreRReportSchema LCScoreRReportSchema
	**/
	public void setSchema(LCScoreRReportSchema aLCScoreRReportSchema)
	{
		this.GrpContNo = aLCScoreRReportSchema.getGrpContNo();
		this.ContNo = aLCScoreRReportSchema.getContNo();
		this.ProposalContNo = aLCScoreRReportSchema.getProposalContNo();
		this.CustomerNo = aLCScoreRReportSchema.getCustomerNo();
		this.Name = aLCScoreRReportSchema.getName();
		this.ManageCom = aLCScoreRReportSchema.getManageCom();
		this.SScore = aLCScoreRReportSchema.getSScore();
		this.AScore = aLCScoreRReportSchema.getAScore();
		this.Score = aLCScoreRReportSchema.getScore();
		this.Conclusion = aLCScoreRReportSchema.getConclusion();
		this.AssessOperator = aLCScoreRReportSchema.getAssessOperator();
		this.AssessDay = fDate.getDate( aLCScoreRReportSchema.getAssessDay());
		this.MakeDate = fDate.getDate( aLCScoreRReportSchema.getMakeDate());
		this.MakeTime = aLCScoreRReportSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCScoreRReportSchema.getModifyDate());
		this.ModifyTime = aLCScoreRReportSchema.getModifyTime();
		this.Operator = aLCScoreRReportSchema.getOperator();
		this.AssessTime = aLCScoreRReportSchema.getAssessTime();
		this.PrtSeq = aLCScoreRReportSchema.getPrtSeq();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.SScore = rs.getInt("SScore");
			this.AScore = rs.getInt("AScore");
			this.Score = rs.getInt("Score");
			if( rs.getString("Conclusion") == null )
				this.Conclusion = null;
			else
				this.Conclusion = rs.getString("Conclusion").trim();

			if( rs.getString("AssessOperator") == null )
				this.AssessOperator = null;
			else
				this.AssessOperator = rs.getString("AssessOperator").trim();

			this.AssessDay = rs.getDate("AssessDay");
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

			if( rs.getString("AssessTime") == null )
				this.AssessTime = null;
			else
				this.AssessTime = rs.getString("AssessTime").trim();

			if( rs.getString("PrtSeq") == null )
				this.PrtSeq = null;
			else
				this.PrtSeq = rs.getString("PrtSeq").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCScoreRReport表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCScoreRReportSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCScoreRReportSchema getSchema()
	{
		LCScoreRReportSchema aLCScoreRReportSchema = new LCScoreRReportSchema();
		aLCScoreRReportSchema.setSchema(this);
		return aLCScoreRReportSchema;
	}

	public LCScoreRReportDB getDB()
	{
		LCScoreRReportDB aDBOper = new LCScoreRReportDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCScoreRReport描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SScore));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AScore));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Score));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Conclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssessOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AssessDay ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssessTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtSeq));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCScoreRReport>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SScore= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			AScore= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			Score= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			Conclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AssessOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AssessDay = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AssessTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			PrtSeq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCScoreRReportSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("SScore"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SScore));
		}
		if (FCode.equalsIgnoreCase("AScore"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AScore));
		}
		if (FCode.equalsIgnoreCase("Score"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Score));
		}
		if (FCode.equalsIgnoreCase("Conclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Conclusion));
		}
		if (FCode.equalsIgnoreCase("AssessOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssessOperator));
		}
		if (FCode.equalsIgnoreCase("AssessDay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAssessDay()));
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
		if (FCode.equalsIgnoreCase("AssessTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssessTime));
		}
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtSeq));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 6:
				strFieldValue = String.valueOf(SScore);
				break;
			case 7:
				strFieldValue = String.valueOf(AScore);
				break;
			case 8:
				strFieldValue = String.valueOf(Score);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Conclusion);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AssessOperator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAssessDay()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AssessTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(PrtSeq);
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

		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
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
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalContNo = FValue.trim();
			}
			else
				ProposalContNo = null;
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
		if (FCode.equalsIgnoreCase("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
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
		if (FCode.equalsIgnoreCase("SScore"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SScore = i;
			}
		}
		if (FCode.equalsIgnoreCase("AScore"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AScore = i;
			}
		}
		if (FCode.equalsIgnoreCase("Score"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Score = i;
			}
		}
		if (FCode.equalsIgnoreCase("Conclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Conclusion = FValue.trim();
			}
			else
				Conclusion = null;
		}
		if (FCode.equalsIgnoreCase("AssessOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssessOperator = FValue.trim();
			}
			else
				AssessOperator = null;
		}
		if (FCode.equalsIgnoreCase("AssessDay"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AssessDay = fDate.getDate( FValue );
			}
			else
				AssessDay = null;
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
		if (FCode.equalsIgnoreCase("AssessTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssessTime = FValue.trim();
			}
			else
				AssessTime = null;
		}
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtSeq = FValue.trim();
			}
			else
				PrtSeq = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCScoreRReportSchema other = (LCScoreRReportSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& Name.equals(other.getName())
			&& ManageCom.equals(other.getManageCom())
			&& SScore == other.getSScore()
			&& AScore == other.getAScore()
			&& Score == other.getScore()
			&& Conclusion.equals(other.getConclusion())
			&& AssessOperator.equals(other.getAssessOperator())
			&& fDate.getString(AssessDay).equals(other.getAssessDay())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Operator.equals(other.getOperator())
			&& AssessTime.equals(other.getAssessTime())
			&& PrtSeq.equals(other.getPrtSeq());
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 2;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 3;
		}
		if( strFieldName.equals("Name") ) {
			return 4;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 5;
		}
		if( strFieldName.equals("SScore") ) {
			return 6;
		}
		if( strFieldName.equals("AScore") ) {
			return 7;
		}
		if( strFieldName.equals("Score") ) {
			return 8;
		}
		if( strFieldName.equals("Conclusion") ) {
			return 9;
		}
		if( strFieldName.equals("AssessOperator") ) {
			return 10;
		}
		if( strFieldName.equals("AssessDay") ) {
			return 11;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 12;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 15;
		}
		if( strFieldName.equals("Operator") ) {
			return 16;
		}
		if( strFieldName.equals("AssessTime") ) {
			return 17;
		}
		if( strFieldName.equals("PrtSeq") ) {
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "ProposalContNo";
				break;
			case 3:
				strFieldName = "CustomerNo";
				break;
			case 4:
				strFieldName = "Name";
				break;
			case 5:
				strFieldName = "ManageCom";
				break;
			case 6:
				strFieldName = "SScore";
				break;
			case 7:
				strFieldName = "AScore";
				break;
			case 8:
				strFieldName = "Score";
				break;
			case 9:
				strFieldName = "Conclusion";
				break;
			case 10:
				strFieldName = "AssessOperator";
				break;
			case 11:
				strFieldName = "AssessDay";
				break;
			case 12:
				strFieldName = "MakeDate";
				break;
			case 13:
				strFieldName = "MakeTime";
				break;
			case 14:
				strFieldName = "ModifyDate";
				break;
			case 15:
				strFieldName = "ModifyTime";
				break;
			case 16:
				strFieldName = "Operator";
				break;
			case 17:
				strFieldName = "AssessTime";
				break;
			case 18:
				strFieldName = "PrtSeq";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SScore") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AScore") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Score") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Conclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssessOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssessDay") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("AssessTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtSeq") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_INT;
				break;
			case 8:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
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
